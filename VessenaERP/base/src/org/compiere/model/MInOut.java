/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.apps.ADialog;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.cfe.CFEConverter;
import org.openup.cfe.CFEManager;
import org.openup.cfe.CFEResponse;
import org.openup.cfe.InterfaceCFEDTO;
import org.openup.cfe.coverter.MInOutCFEConverter;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.model.MCFEDataEnvelope;
import org.openup.model.MInOutType;
import org.openup.model.MPaymentRule;

/**
 *  Shipment Model
 *
 *  @author Jorg Janke
 *  @version $Id: MInOut.java,v 1.4 2006/07/30 00:51:03 jjanke Exp $
 *
 *  Modifications: Added the RMA functionality (Ashley Ramdass)
 *  @author Karsten Thiemann, Schaeffer AG
 * 			<li>Bug [ 1759431 ] Problems with VCreateFrom
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li>FR [ 1948157  ]  Is necessary the reference for document reverse
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org
 *			@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *  @author Armen Rizal, Goodwill Consulting
 * 			<li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 *  @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962
 *  @author Teo Sarca, teo.sarca@gmail.com
 * 			<li>BF [ 2993853 ] Voiding/Reversing Receipt should void confirmations
 * 				https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2993853&group_id=176962
 */
public class MInOut extends X_M_InOut implements DocAction, InterfaceCFEDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -239302197968535277L;

	/**
	 * 	Create Shipment From Order
	 *	@param order order
	 *	@param movementDate optional movement date
	 *	@param forceDelivery ignore order delivery rule
	 *	@param allAttributeInstances if true, all attribute set instances
	 *	@param minGuaranteeDate optional minimum guarantee date if all attribute instances
	 *	@param complete complete document (Process if false, Complete if true)
	 *	@param trxName transaction
	 *	@return Shipment or null
	 */
	public static MInOut createFrom (MOrder order, Timestamp movementDate,
		boolean forceDelivery, boolean allAttributeInstances, Timestamp minGuaranteeDate,
		boolean complete, String trxName)
	{
		if (order == null)
			throw new IllegalArgumentException("No Order");
		//
		if (!forceDelivery && DELIVERYRULE_CompleteLine.equals(order.getDeliveryRule()))
		{
			return null;
		}

		//	Create Header
		MInOut retValue = new MInOut (order, 0, movementDate);
		retValue.setDocAction(complete ? DOCACTION_Complete : DOCACTION_Prepare);

		//	Check if we can create the lines
		MOrderLine[] oLines = order.getLines(true, "M_Product_ID");
		for (int i = 0; i < oLines.length; i++)
		{
			BigDecimal qty = oLines[i].getQtyOrdered().subtract(oLines[i].getQtyDelivered());
			//	Nothing to deliver
			if (qty.signum() == 0)
				continue;
			//	Stock Info
			MStorage[] storages = null;
			MProduct product = oLines[i].getProduct();
			if (product != null && product.get_ID() != 0 && product.isStocked())
			{
				String MMPolicy = product.getMMPolicy();
				storages = MStorage.getWarehouse (order.getCtx(), order.getM_Warehouse_ID(),
					oLines[i].getM_Product_ID(), oLines[i].getM_AttributeSetInstance_ID(),
					minGuaranteeDate, MClient.MMPOLICY_FiFo.equals(MMPolicy), true, 0, trxName);
			} else {
				continue;
			}

			if (!forceDelivery)
			{
				BigDecimal maxQty = Env.ZERO;
				for (int ll = 0; ll < storages.length; ll++)
					maxQty = maxQty.add(storages[ll].getQtyOnHand());
				if (DELIVERYRULE_Availability.equals(order.getDeliveryRule()))
				{
					if (maxQty.compareTo(qty) < 0)
						qty = maxQty;
				}
				else if (DELIVERYRULE_CompleteLine.equals(order.getDeliveryRule()))
				{
					if (maxQty.compareTo(qty) < 0)
						continue;
				}
			}
			//	Create Line
			if (retValue.get_ID() == 0)	//	not saved yet
				retValue.save(trxName);
			//	Create a line until qty is reached
			for (int ll = 0; ll < storages.length; ll++)
			{
				BigDecimal lineQty = storages[ll].getQtyOnHand();
				if (lineQty.compareTo(qty) > 0)
					lineQty = qty;
				MInOutLine line = new MInOutLine (retValue);
				line.setOrderLine(oLines[i], storages[ll].getM_Locator_ID(),
					order.isSOTrx() ? lineQty : Env.ZERO);
				line.setQty(lineQty);	//	Correct UOM for QtyEntered
				if (oLines[i].getQtyEntered().compareTo(oLines[i].getQtyOrdered()) != 0)
					line.setQtyEntered(lineQty
						.multiply(oLines[i].getQtyEntered())
						.divide(oLines[i].getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP));
				line.setC_Project_ID(oLines[i].getC_Project_ID());
				line.save(trxName);
				//	Delivered everything ?
				qty = qty.subtract(lineQty);
			//	storage[ll].changeQtyOnHand(lineQty, !order.isSOTrx());	// Credit Memo not considered
			//	storage[ll].save(get_TrxName());
				if (qty.signum() == 0)
					break;
			}
		}	//	for all order lines

		//	No Lines saved
		if (retValue.get_ID() == 0)
			return null;

		return retValue;
	}	//	createFrom

	/**
	 * 	Create new Shipment by copying
	 * 	@param from shipment
	 * 	@param dateDoc date of the document date
	 * 	@param C_DocType_ID doc type
	 * 	@param isSOTrx sales order
	 * 	@param counter create counter links
	 * 	@param trxName trx
	 * 	@param setOrder set the order link
	 *	@return Shipment
	 */
	public static MInOut copyFrom (MInOut from, Timestamp dateDoc, Timestamp dateAcct,
		int C_DocType_ID, boolean isSOTrx, boolean counter, String trxName, boolean setOrder)
	{
		MInOut to = new MInOut (from.getCtx(), 0, null);
		to.set_TrxName(trxName);
		copyValues(from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck ("M_InOut_ID", I_ZERO);
		to.set_ValueNoCheck ("DocumentNo", null);
		//
		to.setDocStatus (DOCSTATUS_Drafted);		//	Draft
		to.setDocAction(DOCACTION_Complete);
		//
		to.setC_DocType_ID (C_DocType_ID);
		to.setIsSOTrx(isSOTrx);
		if (counter)
		{
			MDocType docType = MDocType.get(from.getCtx(), C_DocType_ID);
			if (MDocType.DOCBASETYPE_MaterialDelivery.equals(docType.getDocBaseType()))
			{
				to.setMovementType (isSOTrx ? MOVEMENTTYPE_CustomerShipment : MOVEMENTTYPE_VendorReturns);
			}
			else if (MDocType.DOCBASETYPE_MaterialReceipt.equals(docType.getDocBaseType()))
			{
				to.setMovementType (isSOTrx ? MOVEMENTTYPE_CustomerReturns : MOVEMENTTYPE_VendorReceipts);
			}
		}

		//
		to.setDateOrdered (dateDoc);
		to.setDateAcct (dateAcct);
		to.setMovementDate(dateDoc);
		to.setDatePrinted(null);
		to.setIsPrinted (false);
		to.setDateReceived(null);
		to.setNoPackages(0);
		to.setShipDate(null);
		to.setPickDate(null);
		to.setIsInTransit(false);
		//
		to.setIsApproved (false);
		to.setC_Invoice_ID(0);
		to.setTrackingNo(null);
		to.setIsInDispute(false);
		//
		to.setPosted (false);
		to.setProcessed (false);
		//[ 1633721 ] Reverse Documents- Processing=Y
		to.setProcessing(false);
		to.setC_Order_ID(0);	//	Overwritten by setOrder
		to.setM_RMA_ID(0);      //  Overwritten by setOrder
		if (counter)
		{
			to.setC_Order_ID(0);
			to.setRef_InOut_ID(from.getM_InOut_ID());
			//	Try to find Order/Invoice link
			if (from.getC_Order_ID() != 0)
			{
				MOrder peer = new MOrder (from.getCtx(), from.getC_Order_ID(), from.get_TrxName());
				if (peer.getRef_Order_ID() != 0)
					to.setC_Order_ID(peer.getRef_Order_ID());
			}
			if (from.getC_Invoice_ID() != 0)
			{
				MInvoice peer = new MInvoice (from.getCtx(), from.getC_Invoice_ID(), from.get_TrxName());
				if (peer.getRef_Invoice_ID() != 0)
					to.setC_Invoice_ID(peer.getRef_Invoice_ID());
			}
			//find RMA link
			if (from.getM_RMA_ID() != 0)
			{
				MRMA peer = new MRMA (from.getCtx(), from.getM_RMA_ID(), from.get_TrxName());
				if (peer.getRef_RMA_ID() > 0)
					to.setM_RMA_ID(peer.getRef_RMA_ID());
			}
		}
		else
		{
			to.setRef_InOut_ID(0);
			if (setOrder)
			{
				to.setC_Order_ID(from.getC_Order_ID());
				to.setM_RMA_ID(from.getM_RMA_ID()); // Copy also RMA
			}
		}
		//
		if (!to.save(trxName))
			throw new IllegalStateException("Could not create Shipment");
		if (counter)
			from.setRef_InOut_ID(to.getM_InOut_ID());

		if (to.copyLinesFrom(from, counter, setOrder) <= 0)
			throw new IllegalStateException("Could not create Shipment Lines");

		return to;
	}	//	copyFrom

	/**
	 *  @deprecated
	 * 	Create new Shipment by copying
	 * 	@param from shipment
	 * 	@param dateDoc date of the document date
	 * 	@param C_DocType_ID doc type
	 * 	@param isSOTrx sales order
	 * 	@param counter create counter links
	 * 	@param trxName trx
	 * 	@param setOrder set the order link
	 *	@return Shipment
	 */
	public static MInOut copyFrom (MInOut from, Timestamp dateDoc,
		int C_DocType_ID, boolean isSOTrx, boolean counter, String trxName, boolean setOrder)
	{
		MInOut to = copyFrom ( from, dateDoc, dateDoc,
				C_DocType_ID, isSOTrx, counter,
				trxName, setOrder);
		return to;

	}

	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_InOut_ID
	 *	@param trxName rx name
	 */
	public MInOut (Properties ctx, int M_InOut_ID, String trxName)
	{
		super (ctx, M_InOut_ID, trxName);
		if (M_InOut_ID == 0)
		{
		//	setDocumentNo (null);
		//	setC_BPartner_ID (0);
		//	setC_BPartner_Location_ID (0);
		//	setM_Warehouse_ID (0);
		//	setC_DocType_ID (0);
			setIsSOTrx (false);
			setMovementDate (new Timestamp (System.currentTimeMillis ()));
			setDateAcct (getMovementDate());
		//	setMovementType (MOVEMENTTYPE_CustomerShipment);
			setDeliveryRule (DELIVERYRULE_Availability);
			setDeliveryViaRule (DELIVERYVIARULE_Pickup);
			setFreightCostRule (FREIGHTCOSTRULE_FreightIncluded);
			setDocStatus (DOCSTATUS_Drafted);
			setDocAction (DOCACTION_Complete);
			setPriorityRule (PRIORITYRULE_Medium);
			setNoPackages(0);
			setIsInTransit(false);
			setIsPrinted (false);
			setSendEMail (false);
			setIsInDispute(false);
			//
			setIsApproved(false);
			super.setProcessed (false);
			setProcessing(false);
			setPosted(true); //OpenUp M.R. 17-02-2011 seteo a posted = true para dejar aplicado el registro y q no de error de posteo
		}
	}	//	MInOut

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *	@param trxName transaction
	 */
	public MInOut (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInOut

	/**
	 * 	Order Constructor - create header only
	 *	@param order order
	 *	@param movementDate optional movement date (default today)
	 *	@param C_DocTypeShipment_ID document type or 0
	 */
	public MInOut (MOrder order, int C_DocTypeShipment_ID, Timestamp movementDate)
	{
		this (order.getCtx(), 0, order.get_TrxName());
		setClientOrg(order);
		setC_BPartner_ID (order.getC_BPartner_ID());
		setC_BPartner_Location_ID (order.getC_BPartner_Location_ID());	//	shipment address
		setAD_User_ID(order.getAD_User_ID());
		//
		setM_Warehouse_ID (order.getM_Warehouse_ID());
		setIsSOTrx (order.isSOTrx());
		if (C_DocTypeShipment_ID == 0)
			C_DocTypeShipment_ID = DB.getSQLValue(null,
				"SELECT C_DocTypeShipment_ID FROM C_DocType WHERE C_DocType_ID=?",
				order.getC_DocType_ID());
		setC_DocType_ID (C_DocTypeShipment_ID);

		// patch suggested by Armen
		// setMovementType (order.isSOTrx() ? MOVEMENTTYPE_CustomerShipment : MOVEMENTTYPE_VendorReceipts);
		String movementTypeShipment = null;
		MDocType dtShipment = new MDocType(order.getCtx(), C_DocTypeShipment_ID, order.get_TrxName()); 
		if (dtShipment.getDocBaseType().equals(MDocType.DOCBASETYPE_MaterialDelivery)) 
			movementTypeShipment = dtShipment.isSOTrx() ? MOVEMENTTYPE_CustomerShipment : MOVEMENTTYPE_VendorReturns; 
		else if (dtShipment.getDocBaseType().equals(MDocType.DOCBASETYPE_MaterialReceipt)) 
			movementTypeShipment = dtShipment.isSOTrx() ? MOVEMENTTYPE_CustomerReturns : MOVEMENTTYPE_VendorReceipts;  
		setMovementType (movementTypeShipment); 
		
		//	Default - Today
		if (movementDate != null)
			setMovementDate (movementDate);
		setDateAcct (getMovementDate());

		//	Copy from Order
		setC_Order_ID(order.getC_Order_ID());
		setDeliveryRule (order.getDeliveryRule());
		setDeliveryViaRule (order.getDeliveryViaRule());
		setM_Shipper_ID(order.getM_Shipper_ID());
		setFreightCostRule (order.getFreightCostRule());
		setFreightAmt(order.getFreightAmt());
		setSalesRep_ID(order.getSalesRep_ID());
		//
		setC_Activity_ID(order.getC_Activity_ID());
		setC_Campaign_ID(order.getC_Campaign_ID());
		setC_Charge_ID(order.getC_Charge_ID());
		setChargeAmt(order.getChargeAmt());
		//
		setC_Project_ID(order.getC_Project_ID());
		setDateOrdered(order.getDateOrdered());
		setDescription(order.getDescription());
		setPOReference(order.getPOReference());
		setSalesRep_ID(order.getSalesRep_ID());
		setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		setUser1_ID(order.getUser1_ID());
		setUser2_ID(order.getUser2_ID());
		setPriorityRule(order.getPriorityRule());
		// Drop shipment
		setIsDropShip(order.isDropShip());
		setDropShip_BPartner_ID(order.getDropShip_BPartner_ID());
		setDropShip_Location_ID(order.getDropShip_Location_ID());
		setDropShip_User_ID(order.getDropShip_User_ID());
	}	//	MInOut

	/**
	 * 	Invoice Constructor - create header only
	 *	@param invoice invoice
	 *	@param C_DocTypeShipment_ID document type or 0
	 *	@param movementDate optional movement date (default today)
	 *	@param M_Warehouse_ID warehouse
	 */
	public MInOut (MInvoice invoice, int C_DocTypeShipment_ID, Timestamp movementDate, int M_Warehouse_ID)
	{
		this (invoice.getCtx(), 0, invoice.get_TrxName());
		setClientOrg(invoice);
		setC_BPartner_ID (invoice.getC_BPartner_ID());
		setC_BPartner_Location_ID (invoice.getC_BPartner_Location_ID());	//	shipment address
		setAD_User_ID(invoice.getAD_User_ID());
		//
		setM_Warehouse_ID (M_Warehouse_ID);
		setIsSOTrx (invoice.isSOTrx());
		setMovementType (invoice.isSOTrx() ? MOVEMENTTYPE_CustomerShipment : MOVEMENTTYPE_VendorReceipts);
		MOrder order = null;
		if (invoice.getC_Order_ID() != 0)
			order = new MOrder (invoice.getCtx(), invoice.getC_Order_ID(), invoice.get_TrxName());
		if (C_DocTypeShipment_ID == 0 && order != null)
			C_DocTypeShipment_ID = DB.getSQLValue(null,
				"SELECT C_DocTypeShipment_ID FROM C_DocType WHERE C_DocType_ID=?",
				order.getC_DocType_ID());
		if (C_DocTypeShipment_ID != 0)
			setC_DocType_ID (C_DocTypeShipment_ID);
		else
			setC_DocType_ID();

		//	Default - Today
		if (movementDate != null)
			setMovementDate (movementDate);
		setDateAcct (getMovementDate());

		//	Copy from Invoice
		setC_Order_ID(invoice.getC_Order_ID());
		setSalesRep_ID(invoice.getSalesRep_ID());
		//
		setC_Activity_ID(invoice.getC_Activity_ID());
		setC_Campaign_ID(invoice.getC_Campaign_ID());
		setC_Charge_ID(invoice.getC_Charge_ID());
		setChargeAmt(invoice.getChargeAmt());
		//
		setC_Project_ID(invoice.getC_Project_ID());
		setDateOrdered(invoice.getDateOrdered());
		setDescription(invoice.getDescription());
		setPOReference(invoice.getPOReference());
		setAD_OrgTrx_ID(invoice.getAD_OrgTrx_ID());
		setUser1_ID(invoice.getUser1_ID());
		setUser2_ID(invoice.getUser2_ID());

		if (order != null)
		{
			setDeliveryRule (order.getDeliveryRule());
			setDeliveryViaRule (order.getDeliveryViaRule());
			setM_Shipper_ID(order.getM_Shipper_ID());
			setFreightCostRule (order.getFreightCostRule());
			setFreightAmt(order.getFreightAmt());

			// Drop Shipment
			setIsDropShip(order.isDropShip());
			setDropShip_BPartner_ID(order.getDropShip_BPartner_ID());
			setDropShip_Location_ID(order.getDropShip_Location_ID());
			setDropShip_User_ID(order.getDropShip_User_ID());
		}
	}	//	MInOut

	/**
	 * 	Copy Constructor - create header only
	 *	@param original original
	 *	@param movementDate optional movement date (default today)
	 *	@param C_DocTypeShipment_ID document type or 0
	 */
	public MInOut (MInOut original, int C_DocTypeShipment_ID, Timestamp movementDate)
	{
		this (original.getCtx(), 0, original.get_TrxName());
		setClientOrg(original);
		setC_BPartner_ID (original.getC_BPartner_ID());
		setC_BPartner_Location_ID (original.getC_BPartner_Location_ID());	//	shipment address
		setAD_User_ID(original.getAD_User_ID());
		//
		setM_Warehouse_ID (original.getM_Warehouse_ID());
		setIsSOTrx (original.isSOTrx());
		setMovementType (original.getMovementType());
		if (C_DocTypeShipment_ID == 0)
			setC_DocType_ID(original.getC_DocType_ID());
		else
			setC_DocType_ID (C_DocTypeShipment_ID);

		//	Default - Today
		if (movementDate != null)
			setMovementDate (movementDate);
		setDateAcct (getMovementDate());

		//	Copy from Order
		setC_Order_ID(original.getC_Order_ID());
		setDeliveryRule (original.getDeliveryRule());
		setDeliveryViaRule (original.getDeliveryViaRule());
		setM_Shipper_ID(original.getM_Shipper_ID());
		setFreightCostRule (original.getFreightCostRule());
		setFreightAmt(original.getFreightAmt());
		setSalesRep_ID(original.getSalesRep_ID());
		//
		setC_Activity_ID(original.getC_Activity_ID());
		setC_Campaign_ID(original.getC_Campaign_ID());
		setC_Charge_ID(original.getC_Charge_ID());
		setChargeAmt(original.getChargeAmt());
		//
		setC_Project_ID(original.getC_Project_ID());
		setDateOrdered(original.getDateOrdered());
		setDescription(original.getDescription());
		setPOReference(original.getPOReference());
		setSalesRep_ID(original.getSalesRep_ID());
		setAD_OrgTrx_ID(original.getAD_OrgTrx_ID());
		setUser1_ID(original.getUser1_ID());
		setUser2_ID(original.getUser2_ID());

		// DropShipment
		setIsDropShip(original.isDropShip());
		setDropShip_BPartner_ID(original.getDropShip_BPartner_ID());
		setDropShip_Location_ID(original.getDropShip_Location_ID());
		setDropShip_User_ID(original.getDropShip_User_ID());

	}	//	MInOut


	/**	Lines					*/
	private MInOutLine[]	m_lines = null;
	/** Confirmations			*/
	private MInOutConfirm[]	m_confirms = null;
	/** BPartner				*/
	private MBPartner		m_partner = null;


	/**
	 * 	Get Document Status
	 *	@return Document Status Clear Text
	 */
	public String getDocStatusName()
	{
		return MRefList.getListName(getCtx(), 131, getDocStatus());
	}	//	getDocStatusName

	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription

	/**
	 *	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MInOut[")
			.append (get_ID()).append("-").append(getDocumentNo())
			.append(",DocStatus=").append(getDocStatus())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	//	getDocumentInfo

	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
		ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.SHIPMENT, getM_InOut_ID(), get_TrxName());
		if (re == null)
			return null;
		return re.getPDF(file);
	}	//	createPDF

	/**
	 * 	Get Lines of Shipment
	 * 	@param requery refresh from db
	 * 	@return lines
	 */
	public MInOutLine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		List<MInOutLine> list = new Query(getCtx(), I_M_InOutLine.Table_Name, "M_InOut_ID=?", get_TrxName())
		.setParameters(getM_InOut_ID())
		.setOrderBy(MInOutLine.COLUMNNAME_Line)
		.list();
		//
		m_lines = new MInOutLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}	//	getMInOutLines

	/**
	 * 	Get Lines of Shipment
	 * 	@return lines
	 */
	public MInOutLine[] getLines()
	{
		return getLines(false);
	}	//	getLines


	/**
	 * 	Get Confirmations
	 * 	@param requery requery
	 *	@return array of Confirmations
	 */
	public MInOutConfirm[] getConfirmations(boolean requery)
	{
		if (m_confirms != null && !requery)
		{
			set_TrxName(m_confirms, get_TrxName());
			return m_confirms;
		}
		List<MInOutConfirm> list = new Query(getCtx(), I_M_InOutConfirm.Table_Name, "M_InOut_ID=?", get_TrxName())
		.setParameters(getM_InOut_ID())
		.list();
		m_confirms = new MInOutConfirm[list.size ()];
		list.toArray (m_confirms);
		return m_confirms;
	}	//	getConfirmations


	/**
	 * 	Copy Lines From other Shipment
	 *	@param otherShipment shipment
	 *	@param counter set counter info
	 *	@param setOrder set order link
	 *	@return number of lines copied
	 */
	public int copyLinesFrom (MInOut otherShipment, boolean counter, boolean setOrder)
	{
		if (isProcessed() || isPosted() || otherShipment == null)
			return 0;
		MInOutLine[] fromLines = otherShipment.getLines(false);
		int count = 0;
		for (int i = 0; i < fromLines.length; i++)
		{
			MInOutLine line = new MInOutLine (this);
			MInOutLine fromLine = fromLines[i];
			line.set_TrxName(get_TrxName());
			if (counter)	//	header
				PO.copyValues(fromLine, line, getAD_Client_ID(), getAD_Org_ID());
			else
				PO.copyValues(fromLine, line, fromLine.getAD_Client_ID(), fromLine.getAD_Org_ID());
			line.setM_InOut_ID(getM_InOut_ID());
			line.set_ValueNoCheck ("M_InOutLine_ID", I_ZERO);	//	new
			//	Reset
			if (!setOrder)
			{
				line.setC_OrderLine_ID(0);
				line.setM_RMALine_ID(0);  // Reset RMA Line
			}
			if (!counter)
				line.setM_AttributeSetInstance_ID(0);
		//	line.setS_ResourceAssignment_ID(0);
			line.setRef_InOutLine_ID(0);
			line.setIsInvoiced(false);
			//
			line.setConfirmedQty(Env.ZERO);
			line.setPickedQty(Env.ZERO);
			line.setScrappedQty(Env.ZERO);
			line.setTargetQty(Env.ZERO);
			//	Set Locator based on header Warehouse
			if (getM_Warehouse_ID() != otherShipment.getM_Warehouse_ID())
			{
				line.setM_Locator_ID(0);
				line.setM_Locator_ID(Env.ZERO);
			}
			//
			if (counter)
			{
				line.setRef_InOutLine_ID(fromLine.getM_InOutLine_ID());
				if (fromLine.getC_OrderLine_ID() != 0)
				{
					MOrderLine peer = new MOrderLine (getCtx(), fromLine.getC_OrderLine_ID(), get_TrxName());
					if (peer.getRef_OrderLine_ID() != 0)
						line.setC_OrderLine_ID(peer.getRef_OrderLine_ID());
				}
				//RMALine link
				if (fromLine.getM_RMALine_ID() != 0)
				{
					MRMALine peer = new MRMALine (getCtx(), fromLine.getM_RMALine_ID(), get_TrxName());
					if (peer.getRef_RMALine_ID() > 0)
						line.setM_RMALine_ID(peer.getRef_RMALine_ID());
				}
			}
			//
			line.setProcessed(false);
			if (line.save(get_TrxName()))
				count++;
			//	Cross Link
			if (counter)
			{
				fromLine.setRef_InOutLine_ID(line.getM_InOutLine_ID());
				fromLine.save(get_TrxName());
			}
		}
		if (fromLines.length != count) {
			log.log(Level.SEVERE, "Line difference - From=" + fromLines.length + " <> Saved=" + count);
			count = -1; // caller must validate error in count and rollback accordingly - BF [3160928]
		}
		return count;
	}	//	copyLinesFrom

	/** Reversal Flag		*/
	private boolean m_reversal = false;

	/**
	 * 	Set Reversal
	 *	@param reversal reversal
	 */
	private void setReversal(boolean reversal)
	{
		m_reversal = reversal;
	}	//	setReversal
	/**
	 * 	Is Reversal
	 *	@return reversal
	 */
	public boolean isReversal()
	{
		return m_reversal;
	}	//	isReversal

	/**
	 * 	Set Processed.
	 * 	Propagate to Lines/Taxes
	 *	@param processed processed
	 */
	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		String sql = "UPDATE M_InOutLine SET Processed='"
			+ (processed ? "Y" : "N")
			+ "' WHERE M_InOut_ID=" + getM_InOut_ID();
		int noLine = DB.executeUpdate(sql, get_TrxName());
		m_lines = null;
		log.fine(processed + " - Lines=" + noLine);
	}	//	setProcessed

	/**
	 * 	Get BPartner
	 *	@return partner
	 */
	public MBPartner getBPartner()
	{
		if (m_partner == null)
			m_partner = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
		return m_partner;
	}	//	getPartner

	/**
	 * 	Set Document Type
	 * 	@param DocBaseType doc type MDocType.DOCBASETYPE_
	 */
	public void setC_DocType_ID (String DocBaseType)
	{
		String sql = "SELECT C_DocType_ID FROM C_DocType "
			+ "WHERE AD_Client_ID=? AND DocBaseType=?"
			+ " AND IsActive='Y'"
			+ " AND IsSOTrx='" + (isSOTrx() ? "Y" : "N") + "' "
			+ "ORDER BY IsDefault DESC";
		int C_DocType_ID = DB.getSQLValue(null, sql, getAD_Client_ID(), DocBaseType);
		if (C_DocType_ID <= 0)
			log.log(Level.SEVERE, "Not found for AC_Client_ID="
				+ getAD_Client_ID() + " - " + DocBaseType);
		else
		{
			log.fine("DocBaseType=" + DocBaseType + " - C_DocType_ID=" + C_DocType_ID);
			setC_DocType_ID (C_DocType_ID);
			boolean isSOTrx = MDocType.DOCBASETYPE_MaterialDelivery.equals(DocBaseType);
			setIsSOTrx (isSOTrx);
		}
	}	//	setC_DocType_ID

	/**
	 * 	Set Default C_DocType_ID.
	 * 	Based on SO flag
	 */
	public void setC_DocType_ID()
	{
		if (isSOTrx())
			setC_DocType_ID(MDocType.DOCBASETYPE_MaterialDelivery);
		else
			setC_DocType_ID(MDocType.DOCBASETYPE_MaterialReceipt);
	}	//	setC_DocType_ID

	/**
	 * 	Set Business Partner Defaults & Details
	 * 	@param bp business partner
	 */
	public void setBPartner (MBPartner bp)
	{
		if (bp == null)
			return;

		setC_BPartner_ID(bp.getC_BPartner_ID());

		//	Set Locations
		MBPartnerLocation[] locs = bp.getLocations(false);
		if (locs != null)
		{
			for (int i = 0; i < locs.length; i++)
			{
				if (locs[i].isShipTo())
					setC_BPartner_Location_ID(locs[i].getC_BPartner_Location_ID());
			}
			//	set to first if not set
			if (getC_BPartner_Location_ID() == 0 && locs.length > 0)
				setC_BPartner_Location_ID(locs[0].getC_BPartner_Location_ID());
		}
		
		
		
		if (getC_BPartner_Location_ID() == 0){
			
			// OpenUp. Gabriel Vila. 25/04/2014. Issue #2109.
			// No es necesario para documentos de compra que haya localizacion de socio de negocio.
			// Comento y cambio codigo
		
			//log.log(Level.SEVERE, "Has no To Address: " + bp);

			if (this.isSOTrx()){
				log.log(Level.SEVERE, "Has no To Address: " + bp);
			}
			else{
				// Me creo una location nueva para este socio de negocio y le asocio la misma a este documento de compra
				MLocation location = new MLocation(getCtx(), 0, get_TrxName());
				location.setC_Country_ID(336); // Uruguay
				location.setCity("NO Departamento");
				location.setRegionName("OR");
				location.setAddress4("NO Localidad");
				location.saveEx();
				MBPartnerLocation bploc = new MBPartnerLocation(getCtx(), 0, get_TrxName());
				bploc.setC_BPartner_ID(this.getC_BPartner_ID());
				bploc.setName("NO Departamento");
				bploc.setC_Location_ID(location.get_ID());
				bploc.setIsBillTo(true);
				bploc.setIsShipTo(true);
				bploc.setIsRemitTo(true);
				bploc.setIsPayFrom(true);
				bploc.saveEx();
				
				this.setC_BPartner_Location_ID(bploc.get_ID());
			}
			
			// Fin OpenUp. Issue #2109.

		}
			

		//	Set Contact
		MUser[] contacts = bp.getContacts(false);
		if (contacts != null && contacts.length > 0)	//	get first User
			setAD_User_ID(contacts[0].getAD_User_ID());
	}	//	setBPartner

	/**
	 * 	Create the missing next Confirmation
	 */
	public void createConfirmation()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		boolean pick = dt.isPickQAConfirm();
		boolean ship = dt.isShipConfirm();
		//	Nothing to do
		if (!pick && !ship)
		{
			log.fine("No need");
			return;
		}

		//	Create Both .. after each other
		if (pick && ship)
		{
			boolean havePick = false;
			boolean haveShip = false;
			MInOutConfirm[] confirmations = getConfirmations(false);
			for (int i = 0; i < confirmations.length; i++)
			{
				MInOutConfirm confirm = confirmations[i];
				if (MInOutConfirm.CONFIRMTYPE_PickQAConfirm.equals(confirm.getConfirmType()))
				{
					if (!confirm.isProcessed())		//	wait intil done
					{
						log.fine("Unprocessed: " + confirm);
						return;
					}
					havePick = true;
				}
				else if (MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm.equals(confirm.getConfirmType()))
					haveShip = true;
			}
			//	Create Pick
			if (!havePick)
			{
				MInOutConfirm.create (this, MInOutConfirm.CONFIRMTYPE_PickQAConfirm, false);
				return;
			}
			//	Create Ship
			if (!haveShip)
			{
				MInOutConfirm.create (this, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, false);
				return;
			}
			return;
		}
		//	Create just one
		if (pick)
			MInOutConfirm.create (this, MInOutConfirm.CONFIRMTYPE_PickQAConfirm, true);
		else if (ship)
			MInOutConfirm.create (this, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, true);
	}	//	createConfirmation
	
	private void voidConfirmations()
	{
		for(MInOutConfirm confirm : getConfirmations(true))
		{
			if (!confirm.isProcessed())
			{
				if (!confirm.processIt(MInOutConfirm.DOCACTION_Void))
					throw new AdempiereException(confirm.getProcessMsg());
				confirm.saveEx();
			}
		}
	}


	/**
	 * 	Set Warehouse and check/set Organization
	 *	@param M_Warehouse_ID id
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID == 0)
		{
			log.severe("Ignored - Cannot set AD_Warehouse_ID to 0");
			return;
		}
		super.setM_Warehouse_ID (M_Warehouse_ID);
		//
		MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
		if (wh.getAD_Org_ID() != getAD_Org_ID())
		{
			log.warning("M_Warehouse_ID=" + M_Warehouse_ID
				+ ", Overwritten AD_Org_ID=" + getAD_Org_ID() + "->" + wh.getAD_Org_ID());
			setAD_Org_ID(wh.getAD_Org_ID());
		}
	}	//	setM_Warehouse_ID


	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true or false
	 */
	protected boolean beforeSave(boolean newRecord) {

		//OpenUp. M.R. 08-09-2011. Issue #753.
		// Valido numeros de documentos repetidos
		if (!this.isSOTrx())
			if (newRecord) this.validateDocumentNo();
		// Fin Issue #753.		

		// OpenUp. Gabriel Vila. 01/04/2013. Issue #617.
		// En documento de compra si no indico una location, 
		// intento setear una por defecto.
		// Seteo tambien almacen y ubicacion
		if (!this.isSOTrx()){
			if (this.getC_BPartner_Location_ID() <= 0){
				MBPartner partner = (MBPartner)this.getC_BPartner();
				if (partner != null){
					MBPartnerLocation[] locations = partner.getLocations(true);
					if (locations.length > 0){
						this.setC_BPartner_Location_ID(locations[0].get_ID());
					}
				}
			}
		}
		// Fin Issue #617
		
		
		// Specific before seve controls form customers returns 
		if (!(beforeSaveCustomerReturns(newRecord))) {
			return(false);
		}
		
		// Warehouse Org
		if (newRecord) {
			MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			if (wh.getAD_Org_ID() != getAD_Org_ID())
			{
				log.saveError("WarehouseOrgConflict", "");
				
				// OpenUp. Gabriel Vila. 04/06/2013. Issue #2174
				// Comento return ya que molesta para manejo de franquiciados
				
				//return false;
				
				// Fin OpenUp. Issue #2174.
				
				
			}
		}

        // Shipment/Receipt can have either Order/RMA (For Movement type)
        if (getC_Order_ID() != 0 && getM_RMA_ID() != 0)
        {
            log.saveError("OrderOrRMA", "");
            return false;
        }

        //OpenUp. Nicolas Sarlabos. 18/01/2013. issue #244
        //comento codigo para evitar este error al generar entregas desde remitos
		//	Shipment - Needs Order/RMA
		/*if (!getMovementType().contentEquals(MInOut.MOVEMENTTYPE_CustomerReturns) && isSOTrx() && getC_Order_ID() == 0 && getM_RMA_ID() == 0)
		{
			log.saveError("FillMandatory", Msg.translate(getCtx(), "C_Order_ID"));
			return false;
		}*/
        //Fin OpenUp.

        if (isSOTrx() && getM_RMA_ID() != 0)
        {
            // Set Document and Movement type for this Receipt
            MRMA rma = new MRMA(getCtx(), getM_RMA_ID(), get_TrxName());
            MDocType docType = MDocType.get(getCtx(), rma.getC_DocType_ID());
            setC_DocType_ID(docType.getC_DocTypeShipment_ID());
        }

		return true;
	}	//	beforeSave

	/**
	 * OpenUp. Mario Reyes. 09/09/2011. Issue #753.
	 * Valido numeros de documentos.	
	 */
	private void validateDocumentNo() {

		if (this.isSOTrx()) return;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			sql = " Select * " +
				  " from m_inout " +
				  " where documentno = '" + this.getDocumentNo() + "' " +
				  " and issotrx = 'N' " +
				  " and c_bpartner_id = " + this.getC_BPartner_ID();

			pstmt = DB.prepareStatement(sql, null); // Create the statement
			rs = pstmt.executeQuery();

			// Read the first record and get a new object
			if (rs.next()) {
				throw new AdempiereException("Ya existe una recepcion para este proveedor con el mismo nï¿½mero");
			}

		} catch (Exception e) {
			throw new AdempiereException(e.toString());
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Before save for customer returns
	 * @return
	 * @author  FL
	 * Fecha : 14/04/2011
	 */
	protected boolean beforeSaveCustomerReturns(boolean newRecord) {

		// Check if its a customer return without order or rma
		if ((this.getMovementType().equals(MInOut.MOVEMENTTYPE_CustomerReturns))&&(this.getC_Order_ID()==0)&&(this.getM_RMA_ID()==0)) {
			
			// If the in/out is not set at lines get it from the header 
			if (this.getUY_InOutType_ID()==0) {
				throw new IllegalArgumentException("El tipo de devolucion es requerido en el cabezal, este es el valor por defecto para las lineas");
			}
		
			// OpenUp. Gabriel Vila. 22/02/2013. Issue #373
			// Esto esta mal parametrizado, sirve solo para alianzur y deberia ser grilla.
			// El mantenimiento se llama : Motivo de Entrega o Recepcion.
			// Comento.
			
			/*

			// Get the in/out type to check the warehouse
			MInOutType inOutType=new MInOutType(getCtx(),this.getUY_InOutType_ID(),null);

			// If the in/out type warehouse is diferent from the warehouse, reset it
			if (this.getM_Warehouse_ID()!=inOutType.getM_Warehouse_ID()) {
				this.setM_Warehouse_ID(inOutType.getM_Warehouse_ID());
			}
			*/
			// Fin OpenUp.
		}
	
		return(true);
	}	
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		// OpenUp. Gabriel Vila. 02/04/2013. Issue #617.
		// Ajusto retorno ante exito.
		if (!success) return success;
		// Fin Issue #617.
		
		if (is_ValueChanged("AD_Org_ID"))
		{
			String sql = "UPDATE M_InOutLine ol"
				+ " SET AD_Org_ID ="
					+ "(SELECT AD_Org_ID"
					+ " FROM M_InOut o WHERE ol.M_InOut_ID=o.M_InOut_ID) "
				+ "WHERE M_InOut_ID=" + getC_Order_ID();
			int no = DB.executeUpdate(sql, get_TrxName());
			log.fine("Lines -> #" + no);
		}
		
		// OpenUp. Gabriel Vila. 02/04/2013. Issue #617.
		// Para recepciones de compra que solo admiten una orden de compra,
		// se deben generar las lineas a partir de las lineas de la orden de compra.
		if (MSysConfig.getBooleanValue("UY_ONE_RECEPTION_BY_ORDER", false, this.getAD_Client_ID())){
			MDocType doc = (MDocType)this.getC_DocType();
			if (doc.getValue() != null){
				if (doc.getValue().equalsIgnoreCase("poreception")){
					if (newRecord || is_ValueChanged(COLUMNNAME_C_Order_ID)){
						this.generateLinesFromPOrder();	
					}
				}
			}
		}
		// Fin Issue #617.
		
		return true;
	}	//	afterSave


	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	process

	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success
	 */
	public boolean unlockIt()
	{
		log.info(toString());
		setProcessing(false);
		return true;
	}	//	unlockIt

	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	public boolean invalidateIt()
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt

	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid)
	 */
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// OpenUp. Gabriel Vila. 02/12/2011. Issue #938.
		// Invoco metodo de preparacion de OpenUp y comento lo que hace por defecto adempiere.
		if (!this.prepareItOpenUp()) return DocAction.STATUS_Invalid;
		
		/*
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		//  Order OR RMA can be processed on a shipment/receipt
		if (getC_Order_ID() != 0 && getM_RMA_ID() != 0)
		{
		    m_processMsg = "@OrderOrRMA@";
		    return DocAction.STATUS_Invalid;
		}
		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}

		//	Credit Check
		if (isSOTrx() && !isReversal())
		{
			I_C_Order order = getC_Order();
			if (order != null && MDocType.DOCSUBTYPESO_PrepayOrder.equals(order.getC_DocType().getDocSubTypeSO())
					&& !MSysConfig.getBooleanValue("CHECK_CREDIT_ON_PREPAY_ORDER", true, getAD_Client_ID(), getAD_Org_ID())) {
				// ignore -- don't validate Prepay Orders depending on sysconfig parameter
			} else {
				MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
				if (MBPartner.SOCREDITSTATUS_CreditStop.equals(bp.getSOCreditStatus()))
				{
					m_processMsg = "@BPartnerCreditStop@ - @TotalOpenBalance@="
						+ bp.getTotalOpenBalance()
						+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
					return DocAction.STATUS_Invalid;
				}
				if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus()))
				{
					m_processMsg = "@BPartnerCreditHold@ - @TotalOpenBalance@="
						+ bp.getTotalOpenBalance()
						+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
					return DocAction.STATUS_Invalid;
				}
				BigDecimal notInvoicedAmt = MBPartner.getNotInvoicedAmt(getC_BPartner_ID());
				if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus(notInvoicedAmt)))
				{
					m_processMsg = "@BPartnerOverSCreditHold@ - @TotalOpenBalance@="
						+ bp.getTotalOpenBalance() + ", @NotInvoicedAmt@=" + notInvoicedAmt
						+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
					return DocAction.STATUS_Invalid;
				}
			}
		}

		//	Lines
		MInOutLine[] lines = getLines(true);
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		BigDecimal Volume = Env.ZERO;
		BigDecimal Weight = Env.ZERO;

		//	Mandatory Attributes
		for (int i = 0; i < lines.length; i++)
		{
			MInOutLine line = lines[i];
			MProduct product = line.getProduct();
			if (product != null)
			{
				Volume = Volume.add(product.getVolume().multiply(line.getMovementQty()));
				Weight = Weight.add(product.getWeight().multiply(line.getMovementQty()));
			}
			//
			if (line.getM_AttributeSetInstance_ID() != 0)
				continue;
			if (product != null && product.isASIMandatory(isSOTrx()))
			{
				m_processMsg = "@M_AttributeSet_ID@ @IsMandatory@ (@Line@ #" + lines[i].getLine() +
								", @M_Product_ID@=" + product.getValue() + ")";
				return DocAction.STATUS_Invalid;
			}
		}
		setVolume(Volume);
		setWeight(Weight);

		if (!isReversal())	//	don't change reversal
		{
			createConfirmation();
		}

		*/
		// Fin Issue #938

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	//	prepareIt

	/**
	 * 	Approve Document
	 * 	@return true if success
	 */
	public boolean  approveIt()
	{
		log.info(toString());
		setIsApproved(true);
		MDocType doc = new MDocType(getCtx(), this.getC_DocType_ID(), null);
		// OpenUp Nicolas Sarlabos 03/10/2011. Issue #859
		// Para devoluciones coordinadas de clientes, luego de aprobar viene completar
		if ((doc.getDocBaseType().equalsIgnoreCase("MMR")) && (doc.isSOTrx())){
			if(doc.getDocSubTypeSO() != null){
				if(doc.getDocSubTypeSO().equalsIgnoreCase("RM")){ // OpenUp Nicolas Sarlabos 09/04/2012. Issue #996
				
					setProcessed(true); // OpenUp. Gabriel Vila. 17/11/2011. Issue #927. Una vez aprobado lo marco como procesado
									    // para que no se puede modificar.
					
					setDocStatus(STATUS_Approved);
					setDocAction(ACTION_Complete);
				}
						
			}
		}
		//Fin Issue #859
		
		return true;
	}	//	approveIt

	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	public boolean rejectIt()
	{
		log.info(toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	/**
	 * Invoice Document
	 *   Create an invoice document for customers returns, complete it and print it
	 *   
	 * @author OpenUp FL 24/01/2011
	 * @return true if success
	 */
	public boolean InvoiceIt() {
		log.info(toString());

		// Get the invoice or create it
		MInvoice invoice;
		int C_Invoice_ID = getC_Invoice_ID();
		
		MBPartner partner = new MBPartner(getCtx(), this.getC_BPartner_ID(), null);//OpenUp. Nicolas Sarlabos. 25/11/2015. #5006.
		
		if (C_Invoice_ID != 0) {
			// Get the invoice
			invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
		} else {
			// Create the invoice form this shipment
			invoice = new MInvoice(this, this.getDateAcct());
			
			// Reset the partner location
			invoice.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			
			// Si no tengo vendedor, guardo el vendedor asociado al partner
			if (this.isSOTrx() && this.getSalesRep_ID() <= 0){
				//OpenUp. Nicolas Sarlabos. 25/11/2015. #5006. Se cambia para obtener socio mas arriba.
				invoice.setSalesRep_ID(partner.getSalesRep_ID());
			}
			
			// Reset the document type to credit notes. TODO: it must be change to C_DocTypeinvoice_ID from document type, also this should be include in the creator, MInvoice(MInOut ship, Timestamp invoiceDate) 
			//invoice.setC_DocType_ID(1000004);
			//invoice.setC_DocTypeTarget_ID(1000004);
			//OpenUp. Nicolas Sarlabos. 24/10/2012, se corrigen ID quemados
			MDocType doc = MDocType.forValue(getCtx(), "customernc", get_TrxName());
			invoice.setC_DocType_ID(doc.get_ID());
			invoice.setC_DocTypeTarget_ID(doc.get_ID());
			//Fin OpenUp
			//OpenUp M.R. 28-09-2011 Issue#808 Agrego lineas para qua al momento de generar factura se generen los campos de descripcion , POReference, en las notas de credito, para que salgan impresas. 
			invoice.setDescription(this.getDescription());
			invoice.setPOReference(this.getPOReference());
			//Fin OpenUp
			invoice.setM_Warehouse_ID(this.getM_Warehouse_ID());
			//OpenUp. Nicolas Sarlabos. 07/11/2012 - Obtengo y seteo ID de termino de pago CREDITO
			//OpenUp. Nicolas Sarlabos. 25/11/2015. #5006. Obtengo y seteo ID de termino de pago asociado al cliente
			//MPaymentTerm term = MPaymentTerm.getCredito(getCtx(), get_TrxName());
			//if(term!=null) invoice.setC_PaymentTerm_ID(term.get_ID());
			int termID = partner.getC_PaymentTerm_ID();
			
			if(termID > 0){
				
				invoice.setC_PaymentTerm_ID(termID);
				
			} else {	
				
				MPaymentTerm term = MPaymentTerm.getCredito(getCtx(), get_TrxName());
				if(term!=null) invoice.setC_PaymentTerm_ID(term.get_ID());	
				
			}		
			//Fin OpenUp.
			//Fin OpenUp.
			// Save the new invoice to add lines
			if (!invoice.save(get_TrxName())) {
				return (false);
			}

			// Create the invoice lines
			MInOutLine[] lines = this.getLines();
			for (int i = 0; i < lines.length; i++) {
				MInOutLine line = lines[i];
				
				// OpenUp. Gabriel Vila. 15/07/2011. Issue #792.
				// Si tengo cantidad distinta de cero, creo la linea de invoice
				if (line.getQtyEntered().compareTo(Env.ZERO) > 0){
					MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
					
					// Copy shipment lines to invoice line. TODO: this should be included in a new line creator, it like MInvoiceLine (MInvoice invoice, MInOutLine line)
					invoiceLine.setLine(line.getLine());												// Same line number of the shipment
					invoiceLine.setM_InOutLine_ID(line.getM_InOutLine_ID());							// Reference to the shipment
					invoiceLine.setIsDescription(line.isDescription());
					invoiceLine.setDescription(line.getDescription());
					invoiceLine.setM_Product_ID(line.getM_Product_ID());								// Set the product
					invoiceLine.setC_UOM_ID(line.getC_UOM_ID());										// Set the unit of measure
					invoiceLine.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
					
					// Set quantities
					invoiceLine.setQtyEntered(line.getQtyEntered());									// Set quantity entred
					invoiceLine.setQtyInvoiced(line.getMovementQty());									// Set quantity invoiced, shipment movement quantity was already converted acording to the unit of measure and quantity entred
					
					// Set prices, this is done before adding the special discount
					invoiceLine.setPrice();																// Set price, based on buisness patner price list and discounts, it should consider unit of measure and quiantity entred

					// Get the special discount to be used after the prices were set
					BigDecimal flatDiscount=(BigDecimal)line.get_Value("FlatDiscount");					// TODO: These lines should be replaced then the InOutLine model getter

					//OpenUp. Nicolas Sarlabos. 09/01/2013. Obtengo porcentaje de descuento desde cliente o grupo
					if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){
						//OpenUp. Nicolas Sarlabos. 25/11/2015. #5006. Se elimina linea innecesaria.
						flatDiscount = partner.getDiscountFromPartner();

					}
					//Fin OpenUp.
		
					if (flatDiscount==null) {
						flatDiscount=BigDecimal.ZERO;
					}
					
					// Apply the flat discount if not zero 
					if (flatDiscount.compareTo(BigDecimal.ZERO)!=0) {
						
						// Calculate a multiplier, copied from discount schema
						BigDecimal onehundred = new BigDecimal(100);
						BigDecimal multiplier = (onehundred).subtract(flatDiscount);		
						multiplier = multiplier.divide(onehundred, 6, BigDecimal.ROUND_HALF_UP);
						
						// Apply dthe multiplier to the prices
						invoiceLine.setPriceEntered(invoiceLine.getPriceEntered().multiply(multiplier));
						invoiceLine.setPriceActual(invoiceLine.getPriceActual().multiply(multiplier));
					}

					// Tax and other calculations will be called before save
					
					// Set other properties
					invoiceLine.setC_Project_ID(line.getC_Project_ID());
					invoiceLine.setC_ProjectPhase_ID(line.getC_ProjectPhase_ID());
					invoiceLine.setC_ProjectTask_ID(line.getC_ProjectTask_ID());
					invoiceLine.setC_Activity_ID(line.getC_Activity_ID());
					invoiceLine.setC_Campaign_ID(line.getC_Campaign_ID());
					invoiceLine.setAD_OrgTrx_ID(line.getAD_OrgTrx_ID());
					invoiceLine.setUser1_ID(line.getUser1_ID());
					invoiceLine.setUser2_ID(line.getUser2_ID());
					//OpenUp. Nicolas Sarlabos. 09/11/2012, seteo porccentaje de descuento
					invoiceLine.setFlatDiscount(flatDiscount);
					//Fin OpenUp.
					
					// Set the print mark, get first the type of shipment id 
					int UY_InOutType_ID=line.get_ValueAsInt("UY_InOutType_ID");				// TODO: Replace with a getter, the models need updated
					if (UY_InOutType_ID!=0) {

						// Get the type of shipment object
						MInOutType inOutType=new MInOutType(getCtx(),UY_InOutType_ID,null);	
						if (inOutType!=null) {
							
							// Get the print mark
							String printMark=inOutType.get_ValueAsString("PrintMark");
							if (printMark!=null) {
								invoiceLine.setPrintMark(printMark); // OpenUp M.R. 28-02-2011 Issue #306 se crea un campo nuevo y se cambia
							}
						}
					}
					
					// Save the invoice line
					if (!invoiceLine.save(get_TrxName())) {
						return (false);
					}
				}
			}

			// Set the invoice to the shipments
			this.setC_Invoice_ID(invoice.getC_Invoice_ID());
		}

		
		//OpenUp. Nicolas Sarlabos. 09/11/2012. Si la empresa es SOLUTION no se completa la nota de credito
		// Complete the invoice if not completed
		if (!invoice.isComplete()) {
			
			if (!MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){
				
				if (!invoice.processIt(MInvoice.DOCACTION_Complete)) {
					return(false);
				}
				
				// Save the new invoice after complete it
				if (!invoice.save(get_TrxName())) {
					return (false);
				}

				// Get the transaction and commit to print the invoice
				Trx trx=Trx.get(get_TrxName(), false);
				trx.commit();
				
				// Print the invoice, copy from MProcesoFacturasHdr
				/*try{
					Env.getCtx().put("parmInvoice", invoice.getC_Invoice_ID());					
					ReportCtl.startDocumentPrint(ReportEngine.INVOICE, invoice.getC_Invoice_ID(), true);

					// Pausa de 3 segundos preventiva para impresion masiva
					java.lang.Thread.sleep(3000);
				}
				catch (Exception e){
					log.log(Level.SEVERE, "Error al imprimir factura : " + invoice.getC_Invoice_ID(), e);
					return(false);
				}*/
								
			}
			//Fin OpenUp.
			
			// Print the invoice, copy from MProcesoFacturasHdr
			//OpenUp. Nicolas Sarlabos. 08/11/2012. Si la empresa es SOLUTION pregunto antes de imprimir nota de credito
			if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){
				if(!ADialog.ask(Env.getWindowNo(null), null, null, "Desea imprimir la Nota de Credito?"))
					return(true);
				try{
					Env.getCtx().put("parmInvoice", invoice.getC_Invoice_ID());					
					ReportCtl.startDocumentPrint(ReportEngine.INVOICE, invoice.getC_Invoice_ID(), true);

					// Pausa de 3 segundos preventiva para impresion masiva
					java.lang.Thread.sleep(3000);
				}
				catch (Exception e){
					log.log(Level.SEVERE, "Error al imprimir factura : " + invoice.getC_Invoice_ID(), e);
					return(false);
				}
			} else {

				try{
					Env.getCtx().put("parmInvoice", invoice.getC_Invoice_ID());					
					ReportCtl.startDocumentPrint(ReportEngine.INVOICE, invoice.getC_Invoice_ID(), true);

					// Pausa de 3 segundos preventiva para impresion masiva
					java.lang.Thread.sleep(3000);
				}
				catch (Exception e){
					log.log(Level.SEVERE, "Error al imprimir factura : " + invoice.getC_Invoice_ID(), e);
					return(false);
				}
		
				
			}
		}

		return(true);
	} // InvoiceIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		// OpenUp. Gabriel Vila. 16/02/2012. 
		// Verifico periodo contable para este documento
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());
		// Fin OpenUp.
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// OpenUp. Gabriel Vila. 02/12/2011. Issue #938.
		// Invoco metodo de completar documento de openup y no uso lo que trae adempiere por defecto
		if (!this.completeItOpenUp()) return DocAction.STATUS_Invalid;
		
		/*
		//	Outstanding (not processed) Incoming Confirmations ?
		MInOutConfirm[] confirmations = getConfirmations(true);
		for (int i = 0; i < confirmations.length; i++)
		{
			MInOutConfirm confirm = confirmations[i];
			if (!confirm.isProcessed())
			{
				if (MInOutConfirm.CONFIRMTYPE_CustomerConfirmation.equals(confirm.getConfirmType()))
					continue;
				//
				m_processMsg = "Open @M_InOutConfirm_ID@: " +
					confirm.getConfirmTypeName() + " - " + confirm.getDocumentNo();
				return DocAction.STATUS_InProgress;
			}
		}


		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		StringBuffer info = new StringBuffer();

		// Set of shipped orders
		// Use this set to determine what orders are shipped on
		// this inout record.
		Set<Integer> inOutOrders = new TreeSet<Integer>();
		
		//	For all lines
		MInOutLine[] lines = getLines(false);
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
		{
			MInOutLine sLine = lines[lineIndex];
			MProduct product = sLine.getProduct();

			//	Qty & Type
			String MovementType = getMovementType();
			BigDecimal Qty = sLine.getMovementQty();
			if (MovementType.charAt(1) == '-')	//	C- Customer Shipment - V- Vendor Return
				Qty = Qty.negate();
			BigDecimal QtySO = Env.ZERO;
			BigDecimal QtyPO = Env.ZERO;

			//	Update Order Line
			MOrderLine oLine = null;
			if (sLine.getC_OrderLine_ID() != 0)
			{
				oLine = new MOrderLine (getCtx(), sLine.getC_OrderLine_ID(), get_TrxName());
				// Add order id to set of orders
				inOutOrders.add(oLine.getC_Order_ID());
				log.fine("OrderLine - Reserved=" + oLine.getQtyReserved()
					+ ", Delivered=" + oLine.getQtyDelivered());
				if (isSOTrx())
					QtySO = sLine.getMovementQty();
				else
					QtyPO = sLine.getMovementQty();
			}


            // Load RMA Line
            MRMALine rmaLine = null;

            if (sLine.getM_RMALine_ID() != 0)
            {
                rmaLine = new MRMALine(getCtx(), sLine.getM_RMALine_ID(), get_TrxName());
            }

			log.info("Line=" + sLine.getLine() + " - Qty=" + sLine.getMovementQty());

			//	Stock Movement - Counterpart MOrder.reserveStock
			if (product != null
				&& product.isStocked() )
			{
				//Ignore the Material Policy when is Reverse Correction
				if(!isReversal())
				{
					checkMaterialPolicy(sLine);
				}

				log.fine("Material Transaction");
				MTransaction mtrx = null;
				//same warehouse in order and receipt?
				boolean sameWarehouse = true;
				//	Reservation ASI - assume none
				int reservationAttributeSetInstance_ID = 0; // sLine.getM_AttributeSetInstance_ID();
				if (oLine != null) {
					reservationAttributeSetInstance_ID = oLine.getM_AttributeSetInstance_ID();
					sameWarehouse = oLine.getM_Warehouse_ID()==getM_Warehouse_ID();
				}
				//
				if (sLine.getM_AttributeSetInstance_ID() == 0)
				{
					MInOutLineMA mas[] = MInOutLineMA.get(getCtx(),
						sLine.getM_InOutLine_ID(), get_TrxName());
					for (int j = 0; j < mas.length; j++)
					{
						MInOutLineMA ma = mas[j];
						BigDecimal QtyMA = ma.getMovementQty();
						if (MovementType.charAt(1) == '-')	//	C- Customer Shipment - V- Vendor Return
							QtyMA = QtyMA.negate();
						BigDecimal reservedDiff = Env.ZERO;
						BigDecimal orderedDiff = Env.ZERO;
						if (sLine.getC_OrderLine_ID() != 0)
						{
							if (isSOTrx())
								reservedDiff = ma.getMovementQty().negate();
							else
								orderedDiff = ma.getMovementQty().negate();
						}


						//	Update Storage - see also VMatch.createMatchRecord
						if (!MStorage.add(getCtx(), getM_Warehouse_ID(),
							sLine.getM_Locator_ID(),
							sLine.getM_Product_ID(),
							ma.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
							QtyMA,
							sameWarehouse ? reservedDiff : Env.ZERO,
							sameWarehouse ? orderedDiff : Env.ZERO,
							get_TrxName()))
						{
							m_processMsg = "Cannot correct Inventory (MA)";
							return DocAction.STATUS_Invalid;
						}
						if (!sameWarehouse) {
							//correct qtyOrdered in warehouse of order
							MWarehouse wh = MWarehouse.get(getCtx(), oLine.getM_Warehouse_ID());
							if (!MStorage.add(getCtx(), oLine.getM_Warehouse_ID(),
									wh.getDefaultLocator().getM_Locator_ID(),
									sLine.getM_Product_ID(),
									ma.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
									Env.ZERO, reservedDiff, orderedDiff, get_TrxName()))
								{
									m_processMsg = "Cannot correct Inventory (MA) in order warehouse";
									return DocAction.STATUS_Invalid;
								}
						}
						//	Create Transaction
						mtrx = new MTransaction (getCtx(), sLine.getAD_Org_ID(),
							MovementType, sLine.getM_Locator_ID(),
							sLine.getM_Product_ID(), ma.getM_AttributeSetInstance_ID(),
							QtyMA, getMovementDate(), get_TrxName());
						mtrx.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
						if (!mtrx.save())
						{
							m_processMsg = "Could not create Material Transaction (MA)";
							return DocAction.STATUS_Invalid;
						}
					}
				}
				//	sLine.getM_AttributeSetInstance_ID() != 0
				if (mtrx == null)
				{
					BigDecimal reservedDiff = sameWarehouse ? QtySO.negate() : Env.ZERO;
					BigDecimal orderedDiff = sameWarehouse ? QtyPO.negate(): Env.ZERO;

					//	Fallback: Update Storage - see also VMatch.createMatchRecord
					if (!MStorage.add(getCtx(), getM_Warehouse_ID(),
						sLine.getM_Locator_ID(),
						sLine.getM_Product_ID(),
						sLine.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
						Qty, reservedDiff, orderedDiff, get_TrxName()))
					{
						m_processMsg = "Cannot correct Inventory";
						return DocAction.STATUS_Invalid;
					}
					if (!sameWarehouse) {
						//correct qtyOrdered in warehouse of order
						MWarehouse wh = MWarehouse.get(getCtx(), oLine.getM_Warehouse_ID());
						if (!MStorage.add(getCtx(), oLine.getM_Warehouse_ID(),
								wh.getDefaultLocator().getM_Locator_ID(),
								sLine.getM_Product_ID(),
								sLine.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
								Env.ZERO, QtySO.negate(), QtyPO.negate(), get_TrxName()))
							{
								m_processMsg = "Cannot correct Inventory";
								return DocAction.STATUS_Invalid;
							}
					}
					//	FallBack: Create Transaction
					mtrx = new MTransaction (getCtx(), sLine.getAD_Org_ID(),
						MovementType, sLine.getM_Locator_ID(),
						sLine.getM_Product_ID(), sLine.getM_AttributeSetInstance_ID(),
						Qty, getMovementDate(), get_TrxName());
					mtrx.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
					if (!mtrx.save())
					{
						m_processMsg = CLogger.retrieveErrorString("Could not create Material Transaction");
						return DocAction.STATUS_Invalid;
					}
				}
			}	//	stock movement

			//	Correct Order Line
			if (product != null && oLine != null)		//	other in VMatch.createMatchRecord
				oLine.setQtyReserved(oLine.getQtyReserved().subtract(sLine.getMovementQty()));

			//	Update Sales Order Line
			if (oLine != null)
			{
				if (isSOTrx()							//	PO is done by Matching
					|| sLine.getM_Product_ID() == 0)	//	PO Charges, empty lines
				{
					if (isSOTrx())
						oLine.setQtyDelivered(oLine.getQtyDelivered().subtract(Qty));
					else
						oLine.setQtyDelivered(oLine.getQtyDelivered().add(Qty));
					oLine.setDateDelivered(getMovementDate());	//	overwrite=last
				}
				if (!oLine.save())
				{
					m_processMsg = "Could not update Order Line";
					return DocAction.STATUS_Invalid;
				}
				else
					log.fine("OrderLine -> Reserved=" + oLine.getQtyReserved()
						+ ", Delivered=" + oLine.getQtyReserved());
			}
            //  Update RMA Line Qty Delivered
            else if (rmaLine != null)
            {
                if (isSOTrx())
                {
                    rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().add(Qty));
                }
                else
                {
                    rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().subtract(Qty));
                }
                if (!rmaLine.save())
                {
                    m_processMsg = "Could not update RMA Line";
                    return DocAction.STATUS_Invalid;
                }
            }

			//	Create Asset for SO
			if (product != null
				&& isSOTrx()
				&& product.isCreateAsset()
				&& sLine.getMovementQty().signum() > 0
				&& !isReversal())
			{
				log.fine("Asset");
				info.append("@A_Asset_ID@: ");
				int noAssets = sLine.getMovementQty().intValue();
				if (!product.isOneAssetPerUOM())
					noAssets = 1;
				for (int i = 0; i < noAssets; i++)
				{
					if (i > 0)
						info.append(" - ");
					int deliveryCount = i+1;
					if (!product.isOneAssetPerUOM())
						deliveryCount = 0;
					MAsset asset = new MAsset (this, sLine, deliveryCount);
					if (!asset.save(get_TrxName()))
					{
						m_processMsg = "Could not create Asset";
						return DocAction.STATUS_Invalid;
					}
					info.append(asset.getValue());
				}
			}	//	Asset


			//	Matching
			if (!isSOTrx()
				&& sLine.getM_Product_ID() != 0
				&& !isReversal())
			{
				BigDecimal matchQty = sLine.getMovementQty();
				//	Invoice - Receipt Match (requires Product)
				MInvoiceLine iLine = MInvoiceLine.getOfInOutLine (sLine);
				if (iLine != null && iLine.getM_Product_ID() != 0)
				{
					if (matchQty.compareTo(iLine.getQtyInvoiced())>0)
						matchQty = iLine.getQtyInvoiced();

					MMatchInv[] matches = MMatchInv.get(getCtx(),
						sLine.getM_InOutLine_ID(), iLine.getC_InvoiceLine_ID(), get_TrxName());
					if (matches == null || matches.length == 0)
					{
						MMatchInv inv = new MMatchInv (iLine, getMovementDate(), matchQty);
						if (sLine.getM_AttributeSetInstance_ID() != iLine.getM_AttributeSetInstance_ID())
						{
							iLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
							iLine.save();	//	update matched invoice with ASI
							inv.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
						}
						boolean isNewMatchInv = false;
						if (inv.get_ID() == 0)
							isNewMatchInv = true;
						if (!inv.save(get_TrxName()))
						{
							m_processMsg = CLogger.retrieveErrorString("Could not create Inv Matching");
							return DocAction.STATUS_Invalid;
						}
						if (isNewMatchInv)
							addDocsPostProcess(inv);
					}
				}

				//	Link to Order
				if (sLine.getC_OrderLine_ID() != 0)
				{
					log.fine("PO Matching");
					//	Ship - PO
					MMatchPO po = MMatchPO.create (null, sLine, getMovementDate(), matchQty);
					boolean isNewMatchPO = false;
					if (po.get_ID() == 0)
						isNewMatchPO = true;
					if (!po.save(get_TrxName()))
					{
						m_processMsg = "Could not create PO Matching";
						return DocAction.STATUS_Invalid;
					}
					if (isNewMatchPO)
						addDocsPostProcess(po);
					//	Update PO with ASI
					if (   oLine != null && oLine.getM_AttributeSetInstance_ID() == 0
						&& sLine.getMovementQty().compareTo(oLine.getQtyOrdered()) == 0) //  just if full match [ 1876965 ]
					{
						oLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
						oLine.save(get_TrxName());
					}
				}
				else	//	No Order - Try finding links via Invoice
				{
					//	Invoice has an Order Link
					if (iLine != null && iLine.getC_OrderLine_ID() != 0)
					{
						//	Invoice is created before  Shipment
						log.fine("PO(Inv) Matching");
						//	Ship - Invoice
						MMatchPO po = MMatchPO.create (iLine, sLine,
							getMovementDate(), matchQty);
						boolean isNewMatchPO = false;
						if (po.get_ID() == 0)
							isNewMatchPO = true;
						if (!po.save(get_TrxName()))
						{
							m_processMsg = "Could not create PO(Inv) Matching";
							return DocAction.STATUS_Invalid;
						}
						if (isNewMatchPO)
							addDocsPostProcess(po);
						//	Update PO with ASI
						oLine = new MOrderLine (getCtx(), po.getC_OrderLine_ID(), get_TrxName());
						if (   oLine != null && oLine.getM_AttributeSetInstance_ID() == 0
							&& sLine.getMovementQty().compareTo(oLine.getQtyOrdered()) == 0) //  just if full match [ 1876965 ]
						{
							oLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
							oLine.save(get_TrxName());
						}
					}
				}	//	No Order
			}	//	PO Matching

		}	//	for all lines

		//	Counter Documents
		MInOut counter = createCounterDoc();
		if (counter != null)
			info.append(" - @CounterDoc@: @M_InOut_ID@=").append(counter.getDocumentNo());

		//  Drop Shipments
		MInOut dropShipment = createDropShipment();
		if (dropShipment != null)
			info.append(" - @DropShipment@: @M_InOut_ID@=").append(dropShipment.getDocumentNo());
		//	User Validation
		 
		*/
		// Fin OpenUp.
		
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();
		
		// OpenUp. Gabriel Vila. 16/02/2012. Comento lineas siguientes ya que lo hago en el metodo completeit de OpenUp.
		// Update IsDelivered on orders
		/*
		if (inOutOrders.size()>0) {
			MOrder order;
			for (Iterator<Integer> it = inOutOrders.iterator(); it.hasNext(); ) {
				order = new MOrder(getCtx(), it.next().intValue(), get_TrxName());
				try {
					order.updateIsDelivered();
				} catch (SQLException ee) {
					log.warning("Could not update isDelivered flag on order " + order.getDocumentNo() + " : " + ee.getMessage());
				}
				order.saveEx(get_TrxName());
			}
		}
		m_processMsg = info.toString();
		*/
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 16/02/2012. Cambio seteos finales al completar.
		//setProcessed(true);
		//setDocAction(DOCACTION_Close);
		setDateAcct(getMovementDate());
		setProcessed(true);
		setPosted(true);//OpenUp M.R. 22-02-2011 Seteo la entrega Posted para evitar asiento contable
		setDocAction(DOCACTION_None);
		// Fin OpenUp.

		
		/*
		 * OpenUp. Raul Capecce. 22/01/2016. ISSUE #5560
		 * Modulo de Facturacion Electronica
		 */
		CFEManager cfeManager = new CFEManager(getAD_Client_ID(), getCtx(), get_TrxName());
		cfeManager.addCFE(this);
		cfeManager.SendCFE();
		// FIN OpenUp. Raul Capecce. 22/01/2016. ISSUE #5560
		
		
		return DocAction.STATUS_Completed;

	}	//	completeIt

	/**
	 * 
	 * OpenUp. issue #780	
	 * Descripcion : Mï¿½todo que recorre las ï¿½rdenes involucradas en ï¿½sta recepciï¿½n e invoca al 
	 * mï¿½todo checkCloseOrder de la clase MOrder, para cerrar automï¿½ticamente las ï¿½rdenes segï¿½n
	 * el estado del campo uy_polineclosed
	 * @author  Nicolas Sarlabos 
	 * Fecha : 26/08/2011
	 */
	public void closeOrders(){
		
		String sql = "SELECT DISTINCT c_order_id FROM c_orderline " +
		             "WHERE c_orderline_id in(SELECT c_orderline_id FROM m_inoutline WHERE m_inout_id = " + this.get_ID() + ") " +
		             "GROUP BY c_order_id";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
						
			pstmt = DB.prepareStatement (sql, null);			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				new MOrder(getCtx(), rs.getInt("c_order_id"), get_TrxName()).checkCloseOrder();
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	/**
	 * 
	 * OpenUp.	issue #897
	 * Descripcion : Al anularse la recepcion se suman las cantidades recibidas a la reservada, para 
	 * cada una de las lineas de la orden de compra y, si corresponde, se deja la orden en estado CO 
	 * @author  Nicolas Sarlabos 
	 * Fecha : 12/10/2011
	 */
	public void changeOrderQtyStatus(){
		
		String sql = " SELECT oline.c_order_id, oline.c_orderline_id, ioline.m_inoutline_id " + 
					 " FROM c_orderline oline " +
					 " INNER JOIN m_inoutline ioline on oline.c_orderline_id = ioline.c_orderline_id " +
					 " WHERE ioline.m_inout_id = " + this.get_ID();
 
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
						
			pstmt = DB.prepareStatement (sql, null);			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				
				MOrder order = new MOrder(getCtx(), rs.getInt("c_order_id"), get_TrxName());
				MOrderLine oLine = new MOrderLine(getCtx(),rs.getInt("c_orderline_id"),get_TrxName());
				MInOutLine ioLine = new MInOutLine(getCtx(),rs.getInt("m_inoutline_id"),get_TrxName());
				// OpenUp Nicolas Sarlabos issue #948 04/01/2012
				//OpenUp Nicolas Sarlabos issue #901 13/10/2011
				BigDecimal qtyOrdered = oLine.getQtyOrdered();
				BigDecimal qtyReserved = oLine.getQtyReserved();  //cantidad disponible para recibir en la linea de orden
				BigDecimal qtyMovement = ioLine.getMovementQty(); // cantidad
				// entregada
				BigDecimal sum = qtyReserved.add(qtyMovement);
				BigDecimal deliveries = totalQtyDelivered(oLine.getC_OrderLine_ID());
				//si la cantidad ordenada es menor al total de las cantidades entregadas
				if(qtyOrdered.compareTo(deliveries)<0){
					//la cantidad entregada sera igual al total de las entregas menos la cantidad actual entregada
					oLine.setQtyDelivered(deliveries.subtract(ioLine.getMovementQty()));
					oLine.setQtyReserved(oLine.getQtyOrdered().subtract(oLine.getQtyDelivered()));
										
				}
				//si cantidad ordenada >= (disponible para recibir + cantidad recibida) entonces se 
				//le asigna esa suma a la cantidad reservada
				else if(oLine.getQtyOrdered().compareTo(sum)>=0){ 
					oLine.setQtyReserved(sum);
					oLine.setQtyDelivered(deliveries.subtract(ioLine.getMovementQty())); // OpenUp
																							// Nicolas
																							// Sarlabos
																							// 21/12/2011
				}else oLine.setQtyReserved(qtyOrdered); //si no lo es entonces se asigna a la cantidad reservada el total de la ordenada
							
				oLine.setuy_polineclosed(false);
				oLine.saveEx();
				//fin OpenUp Nicolas Sarlabos issue #901 13/10/2011
				// fin OpenUp Nicolas Sarlabos issue #948 04/01/2012
				if(order.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Closed)){
					order.setDocStatus(DocumentEngine.STATUS_Completed);
					order.setDocAction(DocumentEngine.ACTION_None);
					order.setProcessed(true);
					order.saveEx();
				              
				}
				
								
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	/**
	 * 
	 * OpenUp. issue #901	
	 * Descripcion : Metodo que devuelve la suma de las cantidades entregadas de todas
	 * las lineas para todas las recepciones de la orden
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 17/10/2011
	 */

	public BigDecimal totalQtyDelivered(int oLineID) {
		
		BigDecimal qty = Env.ZERO;
		
		String sql = " SELECT SUM(movementqty)" + // OpenUp Nicolas Sarlabos #948 04/01/2012
		 			 " FROM m_inoutline l" +
		 			 " INNER JOIN m_inout m ON l.m_inout_id=m.m_inout_id" +
		 			 " WHERE c_orderline_id = " + oLineID +
		 			 " AND m.docstatus ='CO'";
		
		qty = DB.getSQLValueBD(get_TrxName(), sql);

		return qty;
	}
	
	/**
	 * OpenUP FL, 21/02/2011. 
	 * Request a Document
	 * 
	 * @return true if success
	 */
	public boolean requestIt() {
		log.info(toString());
		
		// Set the document to no action
		setDateOrdered(new Timestamp(System.currentTimeMillis()));
		//setDocAction(ACTION_Asign);
		
		// OpenUp.Gabriel Vila. 07/07/2011. Issue #646.
		// Para notas de credito proveedor, luego de solicitar viene completar.
		MDocType doc = new MDocType(getCtx(), this.getC_DocType_ID(), null);
		if ((doc.getDocBaseType().equalsIgnoreCase("MMS")) && (!doc.isSOTrx())){
			setDocAction(ACTION_Complete);
		}
		// Fin Issue #646.
		
		// OpenUp Nicolas Sarlabos 14/09/2011. Issue #800
		// Para devoluciones coordinadas de clientes, luego de solicitar viene aprobar
		if ((doc.getDocBaseType().equalsIgnoreCase("MMR")) && (doc.isSOTrx())){
			if(doc.getDocSubTypeSO() != null){
				if(doc.getDocSubTypeSO().equalsIgnoreCase("RM")){ // OpenUp Nicolas Sarlabos 09/04/2012. Issue #996
					setDocStatus(STATUS_Requested);
					setDocAction(ACTION_Approve);  //OpenUp Nicolas Sarlabos #859, se modifica flujo
				}
						
			}
		}
		//Fin Issue #800
				
		return true;
	} // requestIt
	
	
	/**
	 * OpenUP FL, 21/02/2011. 
	 * Asign a Document
	 * 
	 * @return true if success
	 */
	public boolean asignIt() {
		log.info(toString());
		
		// Set the document to no action
		setShipDate(new Timestamp(System.currentTimeMillis()));
		setDocAction(ACTION_Recive);
		return true;
	} // asignIt
	
	/**
	 * OpenUP FL, 21/02/2011. 
	 * Recive a Document
	 * 
	 * @return true if success
	 */
	public boolean reciveIt() {
		log.info(toString());
		
		// Set the document to no action
		setDateReceived(new Timestamp(System.currentTimeMillis()));
		setDocAction(ACTION_Pick);
		return true;
	} // asignIt


	/**
	 * OpenUP FL, 21/02/2011. 
	 * Pick a Document
	 * 
	 * @return true if success
	 */
	public boolean pickIt() {
		log.info(toString());
		
		// Set the document to no action
		setPickDate(new Timestamp(System.currentTimeMillis()));
		setDocAction(ACTION_Complete);
		return true;
	} // pickIt
	
	/* Save array of documents to process AFTER completing this one */
	ArrayList<PO> docsPostProcess = new ArrayList<PO>();

	private void addDocsPostProcess(PO doc) {
		docsPostProcess.add(doc);
	}

	public ArrayList<PO> getDocsPostProcess() {
		return docsPostProcess;
	}

	/**
	 * Automatically creates a customer shipment for any
	 * drop shipment material receipt
	 * Based on createCounterDoc() by JJ
	 * @return shipment if created else null
	 */
	@SuppressWarnings("unused")
	private MInOut createDropShipment() {

		if ( isSOTrx() || !isDropShip() || getC_Order_ID() == 0 )
			return null;

		//	Document Type
		int C_DocTypeTarget_ID = 0;
		MDocType[] shipmentTypes = MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_MaterialDelivery);

		for (int i = 0; i < shipmentTypes.length; i++ )
		{
			if (shipmentTypes[i].isSOTrx() && ( C_DocTypeTarget_ID == 0 || shipmentTypes[i].isDefault() ) )
				C_DocTypeTarget_ID = shipmentTypes[i].getC_DocType_ID();
		}

		//	Deep Copy
		MInOut dropShipment = copyFrom(this, getMovementDate(), getDateAcct(),
			C_DocTypeTarget_ID, !isSOTrx(), false, get_TrxName(), true);

		int linkedOrderID = new MOrder (getCtx(), getC_Order_ID(), get_TrxName()).getLink_Order_ID();
		if (linkedOrderID != 0)
		{
			dropShipment.setC_Order_ID(linkedOrderID);

			// get invoice id from linked order
			int invID = new MOrder (getCtx(), linkedOrderID, get_TrxName()).getC_Invoice_ID();
			if ( invID != 0 )
				dropShipment.setC_Invoice_ID(invID);
		}
		else
			return null;

		dropShipment.setC_BPartner_ID(getDropShip_BPartner_ID());
		dropShipment.setC_BPartner_Location_ID(getDropShip_Location_ID());
		dropShipment.setAD_User_ID(getDropShip_User_ID());
		dropShipment.setIsDropShip(false);
		dropShipment.setDropShip_BPartner_ID(0);
		dropShipment.setDropShip_Location_ID(0);
		dropShipment.setDropShip_User_ID(0);
		dropShipment.setMovementType(MOVEMENTTYPE_CustomerShipment);

		//	References (Should not be required
		dropShipment.setSalesRep_ID(getSalesRep_ID());
		dropShipment.save(get_TrxName());

		//		Update line order references to linked sales order lines
		MInOutLine[] lines = dropShipment.getLines(true);
		for (int i = 0; i < lines.length; i++)
		{
			MInOutLine dropLine = lines[i];
			MOrderLine ol = new MOrderLine(getCtx(), dropLine.getC_OrderLine_ID(), null);
			if ( ol.getC_OrderLine_ID() != 0 ) {
				dropLine.setC_OrderLine_ID(ol.getLink_OrderLine_ID());
				dropLine.saveEx();
			}
		}

		log.fine(dropShipment.toString());

		dropShipment.setDocAction(DocAction.ACTION_Complete);
		dropShipment.processIt(DocAction.ACTION_Complete);
		dropShipment.saveEx();

		return dropShipment;
	}

	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setMovementDate(new Timestamp (System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}

	/**
	 * 	Check Material Policy
	 * 	Sets line ASI
	 */
	@SuppressWarnings("unused")
	private void checkMaterialPolicy(MInOutLine line)
	{
		int no = MInOutLineMA.deleteInOutLineMA(line.getM_InOutLine_ID(), get_TrxName());
		if (no > 0)
			log.config("Delete old #" + no);

		//	Incoming Trx
		String MovementType = getMovementType();
		boolean inTrx = MovementType.charAt(1) == '+';	//	V+ Vendor Receipt


		boolean needSave = false;

		MProduct product = line.getProduct();

		//	Need to have Location
		if (product != null
				&& line.getM_Locator_ID() == 0)
		{
			//MWarehouse w = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			line.setM_Warehouse_ID(getM_Warehouse_ID());
			line.setM_Locator_ID(inTrx ? Env.ZERO : line.getMovementQty());	//	default Locator
			needSave = true;
		}

		// OpenUp. Gabriel Vila. 08/06/2010. Issue #717
		// Antes de generar un nuevo attributesetinstance, consulto
		// si manejo o no instancias para stock. Pongo IF.
		if (MSysConfig.getBooleanValue("UY_HANDLE_STOCK_SETINSTANCE", false, getAD_Client_ID(), getAD_Org_ID())){
		
			//	Attribute Set Instance
			//  Create an  Attribute Set Instance to any receipt FIFO/LIFO
			if (product != null && line.getM_AttributeSetInstance_ID() == 0)
			{
				//Validate Transaction
				if (getMovementType().compareTo(MInOut.MOVEMENTTYPE_CustomerReturns) == 0 
						|| getMovementType().compareTo(MInOut.MOVEMENTTYPE_VendorReceipts) == 0 )
				{
					MAttributeSetInstance asi = null;
					//auto balance negative on hand
					MStorage[] storages = MStorage.getWarehouse(getCtx(), getM_Warehouse_ID(), line.getM_Product_ID(), 0,
							null, MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()), false, line.getM_Locator_ID(), get_TrxName());
					for (MStorage storage : storages)
					{
						if (storage.getQtyOnHand().signum() < 0)
						{
							asi = new MAttributeSetInstance(getCtx(), storage.getM_AttributeSetInstance_ID(), get_TrxName());
							break;
						}
					}
					//always create asi so fifo/lifo work.
					if (asi == null)
					{
						asi = MAttributeSetInstance.create(getCtx(), product, get_TrxName());
					}
					line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
					log.config("New ASI=" + line);
					needSave = true;
				}
				// Create consume the Attribute Set Instance using policy FIFO/LIFO
				else if(getMovementType().compareTo(MInOut.MOVEMENTTYPE_VendorReturns) == 0 || getMovementType().compareTo(MInOut.MOVEMENTTYPE_CustomerShipment) == 0)
				{
					String MMPolicy = product.getMMPolicy();
					Timestamp minGuaranteeDate = getMovementDate();
					MStorage[] storages = MStorage.getWarehouse(getCtx(), getM_Warehouse_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
							minGuaranteeDate, MClient.MMPOLICY_FiFo.equals(MMPolicy), true, line.getM_Locator_ID(), get_TrxName());
					BigDecimal qtyToDeliver = line.getMovementQty();
					for (MStorage storage: storages)
					{
						if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0)
						{
							MInOutLineMA ma = new MInOutLineMA (line,
									storage.getM_AttributeSetInstance_ID(),
									qtyToDeliver);
							ma.saveEx();
							qtyToDeliver = Env.ZERO;
						}
						else
						{
							MInOutLineMA ma = new MInOutLineMA (line,
									storage.getM_AttributeSetInstance_ID(),
									storage.getQtyOnHand());
							ma.saveEx();
							qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
							log.fine( ma + ", QtyToDeliver=" + qtyToDeliver);
						}
	
						if (qtyToDeliver.signum() == 0)
							break;
					}
	
					if (qtyToDeliver.signum() != 0)
					{
						//deliver using new asi
						MAttributeSetInstance asi = MAttributeSetInstance.create(getCtx(), product, get_TrxName());
						int M_AttributeSetInstance_ID = asi.getM_AttributeSetInstance_ID();
						MInOutLineMA ma = new MInOutLineMA (line, M_AttributeSetInstance_ID, qtyToDeliver);
						ma.saveEx();
						log.fine("##: " + ma);
					}
				}	//	outgoing Trx
			}	//	attributeSetInstance
		} 
		else{
			// Else de OpenUp. Issue #717
			line.setM_AttributeSetInstance_ID(0);
			needSave = true;
		}
		// Fin OpenUp

		
		if (needSave)
		{
			line.saveEx();
		}
	}	//	checkMaterialPolicy


	/**************************************************************************
	 * 	Create Counter Document
	 * 	@return InOut
	 */
	@SuppressWarnings("unused")
	private MInOut createCounterDoc()
	{
		//	Is this a counter doc ?
		if (getRef_InOut_ID() != 0)
			return null;

		//	Org Must be linked to BPartner
		MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
		if (counterC_BPartner_ID == 0)
			return null;
		//	Business Partner needs to be linked to Org
		MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
		int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int();
		if (counterAD_Org_ID == 0)
			return null;

		MBPartner counterBP = new MBPartner (getCtx(), counterC_BPartner_ID, get_TrxName());
		MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID, get_TrxName());
		log.info("Counter BP=" + counterBP.getName());

		//	Document Type
		int C_DocTypeTarget_ID = 0;
		MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			log.fine(counterDT.toString());
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
				return null;
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else	//	indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			log.fine("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
				return null;
		}

		//	Deep Copy
		MInOut counter = copyFrom(this, getMovementDate(), getDateAcct(),
			C_DocTypeTarget_ID, !isSOTrx(), true, get_TrxName(), true);

		//
		counter.setAD_Org_ID(counterAD_Org_ID);
		counter.setM_Warehouse_ID(counterOrgInfo.getM_Warehouse_ID());
		//
		counter.setBPartner(counterBP);

		if ( isDropShip() )
		{
			counter.setIsDropShip(true );
			counter.setDropShip_BPartner_ID(getDropShip_BPartner_ID());
			counter.setDropShip_Location_ID(getDropShip_Location_ID());
			counter.setDropShip_User_ID(getDropShip_User_ID());
		}

		//	Refernces (Should not be required
		counter.setSalesRep_ID(getSalesRep_ID());
		counter.save(get_TrxName());

		String MovementType = counter.getMovementType();
		boolean inTrx = MovementType.charAt(1) == '+';	//	V+ Vendor Receipt

		//	Update copied lines
		MInOutLine[] counterLines = counter.getLines(true);
		for (int i = 0; i < counterLines.length; i++)
		{
			MInOutLine counterLine = counterLines[i];
			counterLine.setClientOrg(counter);
			counterLine.setM_Warehouse_ID(counter.getM_Warehouse_ID());
			counterLine.setM_Locator_ID(0);
			counterLine.setM_Locator_ID(inTrx ? Env.ZERO : counterLine.getMovementQty());
			//
			counterLine.save(get_TrxName());
		}

		log.fine(counter.toString());

		//	Document Action
		if (counterDT != null)
		{
			if (counterDT.getDocAction() != null)
			{
				counter.setDocAction(counterDT.getDocAction());
				counter.processIt(counterDT.getDocAction());
				counter.save(get_TrxName());
			}
		}
		return counter;
	}	//	createCounterDoc

	/**
	 * 	Void Document.
	 * 	@return true if success
	 */
	public boolean voidIt()
	{
		
		// OpenUp. Gabriel Vila. 17/02/2012.
		// Hago la anulacion segun modelo de OpenUp.
		// Comento anulacion original en el codigo.
		return this.voidItOpenUp();
		// Fin OpenUp

		
		/*
		 * 
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		//	Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
			|| DOCSTATUS_Invalid.equals(getDocStatus())
			|| DOCSTATUS_InProgress.equals(getDocStatus())
			|| DOCSTATUS_Approved.equals(getDocStatus())
			|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
		{
			//	Set lines to 0
			MInOutLine[] lines = getLines(false);
			for (int i = 0; i < lines.length; i++)
			{
				MInOutLine line = lines[i];
				BigDecimal old = line.getMovementQty();
				if (old.signum() != 0)
				{
					line.setQty(Env.ZERO);
					line.addDescription("Void (" + old + ")");
					line.save(get_TrxName());
				}
			}
			//
			// Void Confirmations
			setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
			saveEx();
			voidConfirmations();
		}
		else
		{
			return reverseCorrectIt();
		}

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
		
		*/
		// Fin OpenUp
		


	}	//	voidIt

	/**
	 * Anulacion segun modelo de OpenUp.
	 * OpenUp Ltda. Issue # Migracion 
	 * @author Gabriel Vila - 16/03/2012
	 * @see
	 * @return
	 */
	private boolean voidItOpenUp() {

		log.info(toString());

		// Verifico periodo contable para este documento
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());
		
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		if (!DOCSTATUS_Completed.equalsIgnoreCase(getDocStatus())){
			m_processMsg = "No puede anularse el documento de entrega/salida de material : " + this.getDocumentNo() + " ya que el mismo no esta en Estado Completado.";
			return false;
		}
		
		//	Reverse/Delete Matching
		if (!isSOTrx())
		{
			MMatchInv[] mInv = MMatchInv.getInOut(getCtx(), getM_InOut_ID(), get_TrxName());
			for (int i = 0; i < mInv.length; i++)
				mInv[i].deleteEx(true);
			MMatchPO[] mPO = MMatchPO.getInOut(getCtx(), getM_InOut_ID(), get_TrxName());
			for (int i = 0; i < mPO.length; i++)
			{
				if (mPO[i].getC_InvoiceLine_ID() == 0)
					mPO[i].deleteEx(true);
				else
				{
					mPO[i].setM_InOutLine_ID(0);
					mPO[i].saveEx();

				}
			}
		}
		
		MDocType docType = new MDocType(getCtx(),this.getC_DocType_ID(), null);

		// Si es una recepcion de materiales se deben dejar las ordenes en estado CO
		if ((!this.isSOTrx()) && (docType.getDocBaseType().equals("MMR"))) changeOrderQtyStatus();
		
		//OpenUp. Nicolas Sarlabos. 04/04/2016. #5720. Elimino numeros de trazabilidad.
		if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID())){
			
			if ((!this.isSOTrx()) && (docType.getDocBaseType().equals("MMR"))) 
				DB.executeUpdateEx("delete from uy_inoutlabel where m_inoutline_id in (select m_inoutline_id from" +
						" m_inoutline where m_inout_id = " + this.get_ID() + ")", get_TrxName());
			
			
		}
		//Fin OpenUp.
		
		//OpenUp Nicolas Sarlabos #996 30/03/2012, no se permite anular devolucion si existe factura asociada
		/*if ((this.isSOTrx()) && (docType.getDocBaseType().equals("MMR"))) {
			
			if (this.isInvoiced()) throw new AdempiereException("No es posible anular la devolución ya que existe nota de crédito asociada");
		}*/
		//OpenUp Nicolas Sarlabos #996 30/03/201
				
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided); 
		setDocAction(DOCACTION_None);
		return true;
		
	}
	
	/**
	 * Metodo que devuelve true si la devolucion esta asociada a una nota de credito
	 * OpenUp Ltda. Issue #996 
	 * @author Nicolas Sarlabos - 30/03/2012
	 * @see
	 * @return
	 */
	
	/*public boolean isInvoiced() {
		
		String sql = "SELECT count(m_inout_id) FROM c_invoice WHERE m_inout_id=" + this.get_ID();
		int salida = DB.getSQLValueEx(get_TrxName(), sql);
		
		if (salida > 0) return true;
		else return false;
		
		
	}*/
	

	/**
	 * 	Close Document.
	 * 	@return true if success
	 */
	public boolean closeIt()
	{
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}	//	closeIt

	/**
	 * 	Reverse Correction - same date
	 * 	@return true if success
	 */
	public boolean reverseCorrectIt()
	{
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return false;
		}

		//	Reverse/Delete Matching
		if (!isSOTrx())
		{
			MMatchInv[] mInv = MMatchInv.getInOut(getCtx(), getM_InOut_ID(), get_TrxName());
			for (int i = 0; i < mInv.length; i++)
				mInv[i].deleteEx(true);
			MMatchPO[] mPO = MMatchPO.getInOut(getCtx(), getM_InOut_ID(), get_TrxName());
			for (int i = 0; i < mPO.length; i++)
			{
				if (mPO[i].getC_InvoiceLine_ID() == 0)
					mPO[i].deleteEx(true);
				else
				{
					mPO[i].setM_InOutLine_ID(0);
					mPO[i].saveEx();

				}
			}
		}

		//	Deep Copy
		MInOut reversal = copyFrom (this, getMovementDate(), getDateAcct(),
			getC_DocType_ID(), isSOTrx(), false, get_TrxName(), true);
		if (reversal == null)
		{
			m_processMsg = "Could not create Ship Reversal";
			return false;
		}
		reversal.setReversal(true);

		//	Reverse Line Qty
		MInOutLine[] sLines = getLines(false);
		MInOutLine[] rLines = reversal.getLines(false);
		for (int i = 0; i < rLines.length; i++)
		{
			MInOutLine rLine = rLines[i];
			rLine.setQtyEntered(rLine.getQtyEntered().negate());
			rLine.setMovementQty(rLine.getMovementQty().negate());
			rLine.setM_AttributeSetInstance_ID(sLines[i].getM_AttributeSetInstance_ID());
			// Goodwill: store original (voided/reversed) document line
			rLine.setReversalLine_ID(sLines[i].getM_InOutLine_ID());
			if (!rLine.save(get_TrxName()))
			{
				m_processMsg = "Could not correct Ship Reversal Line";
				return false;
			}
			//	We need to copy MA
			if (rLine.getM_AttributeSetInstance_ID() == 0)
			{
				MInOutLineMA mas[] = MInOutLineMA.get(getCtx(),
					sLines[i].getM_InOutLine_ID(), get_TrxName());
				for (int j = 0; j < mas.length; j++)
				{
					MInOutLineMA ma = new MInOutLineMA (rLine,
						mas[j].getM_AttributeSetInstance_ID(),
						mas[j].getMovementQty().negate());
					ma.saveEx();
				}
			}
			//	De-Activate Asset
			MAsset asset = MAsset.getFromShipment(getCtx(), sLines[i].getM_InOutLine_ID(), get_TrxName());
			if (asset != null)
			{
				asset.setIsActive(false);
				asset.addDescription("(" + reversal.getDocumentNo() + " #" + rLine.getLine() + "<-)");
				asset.saveEx();
			}
		}
		reversal.setC_Order_ID(getC_Order_ID());
		// Set M_RMA_ID
		reversal.setM_RMA_ID(getM_RMA_ID());
		reversal.addDescription("{->" + getDocumentNo() + ")");
		//FR1948157
		reversal.setReversal_ID(getM_InOut_ID());
		reversal.saveEx(get_TrxName());
		//
		if (!reversal.processIt(DocAction.ACTION_Complete)
			|| !reversal.getDocStatus().equals(DocAction.STATUS_Completed))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return false;
		}
		reversal.closeIt();
		reversal.setProcessing (false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx(get_TrxName());
		//
		addDescription("(" + reversal.getDocumentNo() + "<-)");
		
		//
		// Void Confirmations
		setDocStatus(DOCSTATUS_Reversed); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
		saveEx();
		voidConfirmations();

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		m_processMsg = reversal.getDocumentNo();
		//FR1948157
		this.setReversal_ID(reversal.getM_InOut_ID());
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);		//	 may come from void
		setDocAction(DOCACTION_None);
		return true;
	}	//	reverseCorrectionIt

	/**
	 * 	Reverse Accrual - none
	 * 	@return false
	 */
	public boolean reverseAccrualIt()
	{
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseAccrualIt

	/**
	 * 	Re-activate
	 * 	@return false
	 */
	public boolean reActivateIt()
	{
		
		// OpenUp. Gabriel Vila. 05/03/2013. Issue #462.
		// Reactivacion segun modelo de openup.
		return this.reActivateItOpenUp();

		/*
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return false;
		*/

		// Fin Issue #462.
		
	}	//	reActivateIt


	/***
	 * Accion de Reactivate de OpenUp.
	 * OpenUp Ltda. Issue #462 
	 * @author Gabriel Vila - 05/03/2013.
	 * @see
	 * @return
	 */
	private boolean reActivateItOpenUp() {

		// Si este documento es de venta no hago nada por ahora y no permito reactivar.
		if (this.isSOTrx()) {
			this.m_processMsg = "No esta permitido Reactivar Comprobantes de Venta";
			return false;
		}

		// Si este documento ya tiene una invoice asociada y la misma existe y no esta anulada,
		// aviso y salgo
		if (this.getC_Invoice_ID() > 0){
			MInvoice invoice = new MInvoice(getCtx(), this.getC_Invoice_ID(), get_TrxName());
			if (invoice.get_ID() > 0){
				if (!invoice.getDocStatus().equalsIgnoreCase(STATUS_Voided)){
					MDocType doc = (MDocType)invoice.getC_DocTypeTarget();
					this.m_processMsg = " No esta permitido Reactivar Documento ya que tiene un Comprobante asociado." + "\n" +
							" Documento : " + doc.getName() + "\n" +
							" Numero : " + invoice.getDocumentNo();
					return false;
				}
			}
		}
		
		// Verificacion de periodo abierto
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Elimino asientos contables
		FactLine.deleteFact(I_M_InOut.Table_ID, this.get_ID(), get_TrxName());
	
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		this.setPosted(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);
		
		return true;
	}
	
	
	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(":")
		//	.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
			.append(" (#").append(getLines(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

	/**
	 * 	Get Document Owner (Responsible)
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
		return getSalesRep_ID();
	}	//	getDoc_User_ID

	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	public BigDecimal getApprovalAmt()
	{
		return Env.ZERO;
	}	//	getApprovalAmt

	/**
	 * 	Get C_Currency_ID
	 *	@return Accounting Currency
	 */
	public int getC_Currency_ID ()
	{
		return Env.getContextAsInt(getCtx(),"$C_Currency_ID");
	}	//	getC_Currency_ID

	/**
	 * 	Document Status is Complete or Closed
	 *	@return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
			|| DOCSTATUS_Closed.equals(ds)
			|| DOCSTATUS_Reversed.equals(ds);
	}	//	isComplete

	/**
	 * OpenUp. Gabriel Vila. 02/12/2011. Issue #938.
	 * Agilizo el proceso de completar una entrega/recepcion.
	 * @return
	 */
	private boolean completeItOpenUp(){

		// For all lines
		MInOutLine[] lines = getLines(false);
		for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
			MInOutLine sLine = lines[lineIndex];
			MProduct product = sLine.getProduct();

			// Load Order Line
			MOrderLine oLine = null;
			if (sLine.getC_OrderLine_ID() != 0) 
				oLine = new MOrderLine(getCtx(), sLine.getC_OrderLine_ID(), get_TrxName());
			
			// Load RMA Line
			MRMALine rmaLine = null;
			if (sLine.getM_RMALine_ID() != 0) 
				rmaLine = new MRMALine(getCtx(), sLine.getM_RMALine_ID(), get_TrxName());
			
			if (product != null && product.isStocked()) {
				if (sLine.getM_Locator_ID() <= 0){
					sLine.setM_Locator_ID(product.getM_Product_ID());
					sLine.saveEx();
				}					
			}

			BigDecimal oLineQtyReserved = Env.ZERO; 

			if (oLine != null) {
				if (sLine.getM_Product_ID() != 0) // PO Charges, empty
				{
					//La cant reservada ya viene con la cant bonificada (bonificsimple)
					oLineQtyReserved = oLine.getQtyReserved().subtract(sLine.getMovementQty());
					oLine.setDateDelivered(getMovementDate()); // overwrite=last
				
					String action = " update c_orderline set qtyreserved=" + oLineQtyReserved + "," +
							" datedelivered='" + getMovementDate() + "' " +
							" where c_orderline_id =" + oLine.getC_OrderLine_ID();
			
					try{
						DB.executeUpdateEx(action, get_TrxName());	
					}
					catch (Exception ex){
						m_processMsg = ex.getMessage();
						return false;
					}
				}
			}
			// Update RMA Line Qty Delivered
			else if (rmaLine != null) {
				if (isSOTrx()) {
					rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().add(sLine.getMovementQty()));
				} else {
					rmaLine.setQtyDelivered(rmaLine.getQtyDelivered().subtract(sLine.getMovementQty()));
				}
				if (!rmaLine.save()) {
					m_processMsg = "Could not update RMA Line";
					return false;
				}
			}
			
			
			//	Matching
			if (!isSOTrx()
				&& sLine.getM_Product_ID() != 0
				&& !isReversal())
			{
				BigDecimal matchQty = sLine.getMovementQty();
				//	Invoice - Receipt Match (requires Product)
				MInvoiceLine iLine = MInvoiceLine.getOfInOutLine (sLine);
				if (iLine != null && iLine.getM_Product_ID() != 0)
				{
					if (matchQty.compareTo(iLine.getQtyInvoiced())>0)
						matchQty = iLine.getQtyInvoiced();

					MMatchInv[] matches = MMatchInv.get(getCtx(),
						sLine.getM_InOutLine_ID(), iLine.getC_InvoiceLine_ID(), get_TrxName());
					if (matches == null || matches.length == 0)
					{
						MMatchInv inv = new MMatchInv (iLine, getMovementDate(), matchQty);
						if (sLine.getM_AttributeSetInstance_ID() != iLine.getM_AttributeSetInstance_ID())
						{
							iLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
							iLine.save();	//	update matched invoice with ASI
							inv.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
						}
						boolean isNewMatchInv = false;
						if (inv.get_ID() == 0)
							isNewMatchInv = true;
						if (!inv.save(get_TrxName()))
						{
							m_processMsg = "No fue posible guardar asociacion de factura (INV Matching) al completar Entrega.";
							return false;
							
						}
						if (isNewMatchInv)
							addDocsPostProcess(inv);
					}
				}

				//	Link to Order
				if (sLine.getC_OrderLine_ID() != 0)
				{
					log.fine("PO Matching");
					//	Ship - PO
					MMatchPO po = MMatchPO.create (null, sLine, getMovementDate(), matchQty);
					boolean isNewMatchPO = false;
					if (po.get_ID() == 0)
						isNewMatchPO = true;
					if (!po.save(get_TrxName()))
					{					
						if (po.getProcessMsg()!=null) 
							m_processMsg = po.getProcessMsg();
						else 
							m_processMsg = "No fue posible crear nueva asociacion de compra (PO Matching) al completar Entrega.";
						
						return false;
					}
					if (isNewMatchPO)
						addDocsPostProcess(po);
					//	Update PO with ASI
					if (   oLine != null && oLine.getM_AttributeSetInstance_ID() == 0
						&& sLine.getMovementQty().compareTo(oLine.getQtyOrdered()) == 0) //  just if full match [ 1876965 ]
					{
						oLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
						oLine.save(get_TrxName());
					}
				}
				else	//	No Order - Try finding links via Invoice
				{
					//	Invoice has an Order Link
					if (iLine != null && iLine.getC_OrderLine_ID() != 0)
					{
						//	Invoice is created before  Shipment
						log.fine("PO(Inv) Matching");
						//	Ship - Invoice
						MMatchPO po = MMatchPO.create (iLine, sLine,
							getMovementDate(), matchQty);
						boolean isNewMatchPO = false;
						if (po.get_ID() == 0)
							isNewMatchPO = true;
						if (!po.save(get_TrxName()))
						{
							m_processMsg = "No fue posible guardar asociacion de compra (PO Matching) al completar Entrega.";
							return false;
						}
						if (isNewMatchPO)
							addDocsPostProcess(po);
						//	Update PO with ASI
						oLine = new MOrderLine (getCtx(), po.getC_OrderLine_ID(), get_TrxName());
						if (   oLine != null && oLine.getM_AttributeSetInstance_ID() == 0
							&& sLine.getMovementQty().compareTo(oLine.getQtyOrdered()) == 0) //  just if full match [ 1876965 ]
						{
							oLine.setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
							oLine.save(get_TrxName());
						}
					}
				}	//	No Order
			}	//	PO Matching

			
			
		} // for all lines

		// OpenUp FL 24/01/2011
		// Invoice the shipment for Customer Returns when there is no order and no RMA. TODO: this must be improved  
		if ((this.getMovementType().equals(MInOut.MOVEMENTTYPE_CustomerReturns))&&(this.getC_Order_ID()==0)&&(this.getM_RMA_ID()==0)) {
			// Dont invoice the shipment for reversals 
			if (!this.isReversal()) {
				
				if (MSysConfig.getBooleanValue("UY_SO_RETURN_GENERATE_INVOICE", true, this.getAD_Client_ID())){
					if (!this.InvoiceIt()) {
						m_processMsg = "Could not invoice the shipment/reciept";
						return false;
					}
				}
			}
		}

		//OpenUp Nicolas Sarlabos issue #780 26/08/2011
		//Necesito los datos de docType para preguntar por tipos
		MDocType docType = new MDocType(getCtx(),this.getC_DocType_ID(), null);
		//Para el caso que sea una orden de compra
		if ( (!this.isSOTrx()) && (docType.getDocBaseType().equals("MMR"))) closeOrders();
		// Fin OpenUp #780
		
		// OpenUp. Gabriel Vila. 02/04/2013. Issue #617.
		// Para el caso de Recepciones de Compra, si esta el parametro de generacion 
		// automatica de factura proveedor, la genero en este momento.
		if (docType.getValue() != null){
			if (docType.getValue().equalsIgnoreCase("poreception")){
				if (MSysConfig.getBooleanValue("UY_PO_RECEPTION_GENERATE_INVOICE", false, this.getAD_Client_ID())){
					// Documento segun tenga o no orden de compra
					MDocType invoiceDoc = MDocType.forValue(getCtx(), "invoicevendor", null);
					if (this.getC_Order_ID() <= 0){
						invoiceDoc = MDocType.forValue(getCtx(), "factprovsinoc", null);
					}
					this.generateInvoice(invoiceDoc, false);
				}
			}
		}
		// Fin OpenUp. Issue #617.
		
		
 		// Actualizo atributo del cabezal de la orden para indicarle que fue entregado
		if (this.isSOTrx()){
			String action = " update c_order set isdelivered='Y' " +
					        " where c_order_id =" + this.getC_Order_ID();
			try{
				DB.executeUpdateEx(action, get_TrxName());	
			}
			catch (Exception ex){
				m_processMsg = ex.getMessage();
				return false;
			}
		}
		
		return true;
	}

	
	/***
	 * Generacion de una Invoice partiendo desde este modelo y una posible orden asociada.
	 * OpenUp Ltda. Issue #617 
	 * @author Gabriel Vila - 03/04/2013
	 * @see
	 * @param invoiceDoc
	 * @param completeIt
	 */
	private void generateInvoice(MDocType invoiceDoc, boolean completeIt) {
		
		try{

			// Cabezal
			MInvoice invoice = new MInvoice(this, this.getMovementDate());
			MBPartner partner = (MBPartner)this.getC_BPartner();
			MOrder order = (MOrder)this.getC_Order();

			invoice.setDocumentNoAux(this.get_ValueAsString("InvoiceReference"));
			invoice.setC_DocType_ID(invoiceDoc.get_ID());
			invoice.setC_DocTypeTarget_ID(invoiceDoc.get_ID());
			invoice.setDescription(this.getDescription());
			invoice.setPOReference(this.getPOReference());
			invoice.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			invoice.setM_Warehouse_ID(this.getM_Warehouse_ID());
			invoice.setpaymentruletype("CR");
			invoice.setC_PaymentTerm_ID(MPaymentTerm.forValue(getCtx(), "credito", null).get_ID());
			
			if (this.isSOTrx() && this.getSalesRep_ID() <= 0) invoice.setSalesRep_ID(partner.getSalesRep_ID());
			
			// Datos que obtengo desde la orden
			if (order != null){
				invoice.setC_Order_ID(order.get_ID());
				invoice.setC_Currency_ID(order.getC_Currency_ID());
				if (order.getM_PriceList_ID() > 0)
					invoice.setM_PriceList_ID(order.getM_PriceList_ID());
				if (order.getC_PaymentTerm_ID() > 0){
					invoice.setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
					MPaymentTerm pterm = (MPaymentTerm)invoice.getC_PaymentTerm();
					MPaymentRule prule = (MPaymentRule)pterm.getUY_PaymentRule();
					if ((prule != null) && (prule.get_ID() > 0)){
						invoice.setpaymentruletype(prule.getpaymentruletype());
						invoice.setUY_PaymentRule_ID(prule.get_ID());
					}
						
				}
			}
			if (invoice.getC_PaymentTerm_ID() <= 0){
				MPaymentTerm term = MPaymentTerm.getCredito(getCtx(), null);
				if(term != null) invoice.setC_PaymentTerm_ID(term.get_ID());
			}
			
			invoice.saveEx();
			
			/*
			// Lineas 
			MInOutLine[] lines = this.getLines();
			for (int i = 0; i < lines.length; i++) {

				MInOutLine line = lines[i];
				
				if (line.getQtyEntered().compareTo(Env.ZERO) > 0){
					MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
					
					invoiceLine.setLine(line.getLine());												
					invoiceLine.setM_InOutLine_ID(line.getM_InOutLine_ID());							
					invoiceLine.setIsDescription(line.isDescription());
					invoiceLine.setDescription(line.getDescription());
					invoiceLine.setM_Product_ID(line.getM_Product_ID());								
					invoiceLine.setC_UOM_ID(line.getC_UOM_ID());										
					invoiceLine.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
					invoiceLine.setQtyEntered(line.getQtyEntered());									
					invoiceLine.setQtyInvoiced(line.getMovementQty());									
					
					BigDecimal flatDiscount = Env.ZERO;
					
					MOrderLine oline = (MOrderLine)line.getC_OrderLine();
					if (oline != null){
						invoiceLine.setC_OrderLine_ID(oline.get_ID());
						invoiceLine.setPriceActual(oline.getPriceActual());
						invoiceLine.setPriceEntered(oline.getPriceEntered());
						invoiceLine.setPriceLimit(oline.getPriceLimit());
						invoiceLine.setPriceList(oline.getPriceList());
						flatDiscount = oline.getFlatDiscount();
						if (flatDiscount == null) flatDiscount = Env.ZERO;
					}
					else{
						invoiceLine.setPrice();	
					}
					invoiceLine.setFlatDiscount(flatDiscount);
					invoiceLine.setTax();
					invoiceLine.setLineNetAmt();
					
					invoiceLine.setC_Project_ID(line.getC_Project_ID());
					invoiceLine.setC_ProjectPhase_ID(line.getC_ProjectPhase_ID());
					invoiceLine.setC_ProjectTask_ID(line.getC_ProjectTask_ID());
					invoiceLine.setC_Activity_ID(line.getC_Activity_ID());
					invoiceLine.setC_Campaign_ID(line.getC_Campaign_ID());
					invoiceLine.setAD_OrgTrx_ID(line.getAD_OrgTrx_ID());
					invoiceLine.setUser1_ID(line.getUser1_ID());
					invoiceLine.setUser2_ID(line.getUser2_ID());
					invoiceLine.saveEx();
				}
			}
			*/

			// Asocio este modelo con la invoice generada
			this.setC_Invoice_ID(invoice.getC_Invoice_ID());
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/**
	 * OpenUp. Gabriel Vila. 02/12/2011. Issue #938.
	 * Agilizacion del proceso de preparado de entrega/recepcion.
	 * @return
	 */
	private boolean prepareItOpenUp(){
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		// Order OR RMA can be processed on a shipment/receipt
		if (getC_Order_ID() != 0 && getM_RMA_ID() != 0) {
			m_processMsg = "@OrderOrRMA@";
			return false;
		}

		// Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(),
				getAD_Org_ID())) {
			m_processMsg = "@PeriodClosed@";
			return false;
		}

		MInOutLine[] lines = getLines(true);
		
		// OpenUp. Gabriel Vila. 02/04/2013. Issue #617.
		// Si dentro de una recepcion de compra, no tengo orden de compra y se permite completar
		// la misma sin tener lineas, no valido lineas.
		boolean validateLines = true;
		if (dt.getValue() != null){
			if (dt.getValue().equalsIgnoreCase("poreception")){
				if (this.getC_Order_ID() <= 0){
					if (MSysConfig.getBooleanValue("UY_PO_RECEPTION_ALLOW_NO_LINES", false, this.getAD_Client_ID())){
						validateLines = false;
					}
				}
			}
		}
		// Lines
		if (validateLines){
			if (lines == null || lines.length == 0) {
				m_processMsg = "@NoLines@";
				return false;
			}
		}
		// Fin OpenUp. Issue #617.
		
		// OpenUP FL 21/02/2011. Max lines for Customer Returns when there is no order and no RMA. TODO: this must be improved 
		if ((this.getMovementType().equals(MInOut.MOVEMENTTYPE_CustomerReturns))&&(this.getC_Order_ID()==0)&&(this.getM_RMA_ID()==0)) {
			int tope = MSysConfig.getIntValue("UY_MAX_LINEAS_FACTURA", 30, this.getAD_Client_ID());
			if (lines.length >tope) {
				m_processMsg = "Las devoluciones pueden tener hasta " + tope + " lineas, debe partir esta devolucion por que tiene "+lines.length+" lineas";			// OpenUP FL 21/02/2011. TODO: translate 
				return false;
			}
		}

		return true;
		
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/***
	 * Genera lineas de una determinada recepcion a partir de las lineas de una orden de compra.
	 * OpenUp Ltda. Issue #617 
	 * @author Gabriel Vila - 02/04/2013
	 * @see
	 */
	public void generateLinesFromPOrder(){
		
		try{
			// Primero elimino lineas actuales de esta recepcion
			this.deleteLines();
			
			MOrder order = (MOrder)this.getC_Order();
			if (order == null) return;
			
			// Obtengo lineas de orden de compra
			MOrderLine[] olines = order.getLines();
			for (int i = 0; i < olines.length; i++){
				MOrderLine oline = olines[i];
				MInOutLine ioline = new MInOutLine(getCtx(), 0, get_TrxName());
				ioline.setM_InOut_ID(this.get_ID());
				ioline.setLine(i);
				ioline.setC_OrderLine_ID(oline.get_ID());
				ioline.setDescription(oline.getDescription());
				ioline.setM_Product_ID(oline.getM_Product_ID());
				ioline.setC_UOM_ID(oline.getC_UOM_ID());
				ioline.setIsInvoiced(false);
				
				BigDecimal qty = MUOMConversion.convertProductTo(getCtx(), oline.getM_Product_ID(), oline.getC_UOM_ID(), oline.getQtyReserved());
				if (qty == null) qty = oline.getQtyEntered();
				else if (qty.compareTo(Env.ZERO) == 0) qty = oline.getQtyEntered();

				ioline.setQtyEntered(qty);
				
				ioline.setM_Warehouse_ID(this.getM_Warehouse_ID());
				ioline.setUY_StockStatus_ID(this.getUY_StockStatus_ID());
				ioline.setuy_polineclosed(false);
				
				MWarehouse wh = (MWarehouse)this.getM_Warehouse();
				MLocator locator = wh.getDefaultLocator();
				if ((locator == null) || (locator.get_ID() <= 0)){
					 throw new AdempiereException("El Almacen " + wh.getName() + " no tiene ubicaciones definidas.");
				}
				
				ioline.setM_Locator_ID(locator.get_ID());
				
				ioline.saveEx();
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
	}
	
	/***
	 * Elimina lineas de este modelo.
	 * OpenUp Ltda. Issue #617 
	 * @author Gabriel Vila - 02/04/2013
	 * @see
	 */
	public void deleteLines(){
		
		try{
			
			String action = " delete from m_inoutline where m_inout_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	
	// OpenUp Ltda - INICIO - INTEFACE CON MÓDULO DE FACTURACIÓN ELECTRÓNICA
	
	@Override
	public CFEDefType getCFEDTO() {
		CFEConverter cfeConverter = new MInOutCFEConverter(getCtx(), this, get_TrxName());
		cfeConverter.loadCFE();
		return cfeConverter.getObjCFE();
	}

	@Override
	public void onSendCFEResponse(CFEResponse cfeResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVoidCFEResponse(CFEResponse cfeResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueryCFEResponse(CFEResponse cfeResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDocumentoElectronico() {
		
		MDocType docType = (MDocType) this.getC_DocType();
		boolean isCfe = docType.get_ValueAsBoolean("isCFE");
		
		return isCfe;
	}

	@Override
	public void setDataEnvelope(MCFEDataEnvelope mCfeDataEnvelope) {
		
		set_Value("UY_CFE_DataEnvelope_ID", mCfeDataEnvelope.get_ID());
		this.saveEx();
		
	}
	
	// OpenUp Ltda - FIN - INTEFACE CON MÓDULO DE FACTURACIÓN ELECTRÓNICA
	
	
	
} // MInOut
