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
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.BPartnerNoBillToAddressException;
import org.adempiere.exceptions.BPartnerNoShipToAddressException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.apps.ProcessCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWFResponsible;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.openup.model.IDynamicWF;
import org.openup.model.I_UY_OrderSection;
import org.openup.model.MManufOrderLine;
import org.openup.model.MOrderSection;
import org.openup.model.MOrderSectionLine;
import org.openup.model.MPOPolicy;
import org.openup.model.MPaymentRule;
import org.openup.model.MPurchaseTracking;
import org.openup.model.MQuoteVendor;
import org.openup.model.MRCause;
import org.openup.model.MRFQRequisition;
import org.openup.model.MRInbox;
import org.openup.model.MRType;
import org.openup.model.MReservaPedidoHdr;
import org.openup.model.X_UY_R_Inbox;
import org.openup.model.X_UY_ReservaPedidoHdr;


/**
 *  Order Model.
 * 	Please do not set DocStatus and C_DocType_ID directly. 
 * 	They are set in the process() method. 
 * 	Use DocAction and C_DocTypeTarget_ID instead.
 *
 *  @author Jorg Janke
 *
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org 
 *			@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *  @version $Id: MOrder.java,v 1.5 2006/10/06 00:42:24 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 2419978 ] Voiding PO, requisition don't set on NULL
 * 			<li>BF [ 2892578 ] Order should autoset only active price lists
 * 				https://sourceforge.net/tracker/?func=detail&aid=2892578&group_id=176962&atid=879335
 * @author Michael Judd, www.akunagroup.com
 *          <li>BF [ 2804888 ] Incorrect reservation of products with attributes
 *
 * OpenUp. 03/02/2013. 
 * Flujo de aprobaciones para ordenes de compra.          
 */
public class MOrder extends X_C_Order implements DocAction, IDynamicWF
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1575104995897726572L;

	// OpenUp. Gabriel Vila. 03/02/2013. Issue #198. 
	// Aprobaciones en mas de un nivel.
	private boolean isParcialApproved;

	
	/**
	 * 	Create new Order by copying
	 * 	@param from order
	 * 	@param dateDoc date of the document date
	 * 	@param C_DocTypeTarget_ID target document type
	 * 	@param isSOTrx sales order 
	 * 	@param counter create counter links
	 *	@param copyASI copy line attributes Attribute Set Instance, Resaouce Assignment
	 * 	@param trxName trx
	 *	@return Order
	 */
	public static MOrder copyFrom (MOrder from, Timestamp dateDoc, 
		int C_DocTypeTarget_ID, boolean isSOTrx, boolean counter, boolean copyASI, 
		String trxName)
	{
		MOrder to = new MOrder (from.getCtx(), 0, trxName);
		to.set_TrxName(trxName);
		PO.copyValues(from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck ("C_Order_ID", I_ZERO);
		to.set_ValueNoCheck ("DocumentNo", null);
		//
		to.setDocStatus (DOCSTATUS_Drafted);		//	Draft
		to.setDocAction(DOCACTION_Complete);
		//
		to.setC_DocType_ID(0);
		to.setC_DocTypeTarget_ID (C_DocTypeTarget_ID);
		to.setIsSOTrx(isSOTrx);
		//
		to.setIsSelected (false);
		to.setDateOrdered (dateDoc);
		to.setDateAcct (dateDoc);
		to.setDatePromised (dateDoc);	//	assumption
		to.setDatePrinted(null);
		to.setIsPrinted (false);
		//
		to.setIsApproved (false);
		to.setIsCreditApproved(false);
		to.setC_Payment_ID(0);
		to.setC_CashLine_ID(0);
		//	Amounts are updated  when adding lines
		to.setGrandTotal(Env.ZERO);
		to.setTotalLines(Env.ZERO);
		//
		to.setIsDelivered(false);
		to.setIsInvoiced(false);
		to.setIsSelfService(false);
		to.setIsTransferred (false);
		to.setPosted (false);
		to.setProcessed (false);
		if (counter)
			to.setRef_Order_ID(from.getC_Order_ID());
		else
			to.setRef_Order_ID(0);
		//
		if (!to.save(trxName))
			throw new IllegalStateException("Could not create Order");
		if (counter)
			from.setRef_Order_ID(to.getC_Order_ID());

		if (to.copyLinesFrom(from, counter, copyASI) == 0)
			throw new IllegalStateException("Could not create Order Lines");
		
		// don't copy linked PO/SO
		to.setLink_Order_ID(0);
		
		return to;
	}	//	copyFrom
	
	
	/**************************************************************************
	 *  Default Constructor
	 *  @param ctx context
	 *  @param  C_Order_ID    order to load, (0 create new order)
	 *  @param trxName trx name
	 */
	public MOrder(Properties ctx, int C_Order_ID, String trxName)
	{
		super (ctx, C_Order_ID, trxName);
		//  New
		if (C_Order_ID == 0)
		{
			setDocStatus(DOCSTATUS_Drafted);
			setDocAction (DOCACTION_Prepare);
			//
			setDeliveryRule (DELIVERYRULE_Availability);
			setFreightCostRule (FREIGHTCOSTRULE_FreightIncluded);
			setInvoiceRule (INVOICERULE_Immediate);
			setPaymentRule(PAYMENTRULE_OnCredit);
			setPriorityRule (PRIORITYRULE_Medium);
			setDeliveryViaRule (DELIVERYVIARULE_Pickup);
			//
			setIsDiscountPrinted (false);
			setIsSelected (false);
			setIsTaxIncluded (false);
			setIsSOTrx (true);
			setIsDropShip(false);
			setSendEMail (false);
			//
			setIsApproved(false);
			setIsPrinted(false);
			setIsCreditApproved(false);
			setIsDelivered(false);
			setIsInvoiced(false);
			setIsTransferred(false);
			setIsSelfService(false);
			//
			super.setProcessed(false);
			setProcessing(false);
			setPosted(false);

			setDateAcct (new Timestamp(System.currentTimeMillis()));
			setDatePromised (new Timestamp(System.currentTimeMillis()));
			setDateOrdered (new Timestamp(System.currentTimeMillis()));

			setFreightAmt (Env.ZERO);
			setChargeAmt (Env.ZERO);
			setTotalLines (Env.ZERO);
			setGrandTotal (Env.ZERO);
		}
	}	//	MOrder

	/**************************************************************************
	 *  Project Constructor
	 *  @param  project Project to create Order from
	 *  @param IsSOTrx sales order
	 * 	@param	DocSubTypeSO if SO DocType Target (default DocSubTypeSO_OnCredit)
	 */
	public MOrder (MProject project, boolean IsSOTrx, String DocSubTypeSO)
	{
		this (project.getCtx(), 0, project.get_TrxName());
		setAD_Client_ID(project.getAD_Client_ID());
		setAD_Org_ID(project.getAD_Org_ID());
		setC_Campaign_ID(project.getC_Campaign_ID());
		setSalesRep_ID(project.getSalesRep_ID());
		//
		setC_Project_ID(project.getC_Project_ID());
		setDescription(project.getName());
		Timestamp ts = project.getDateContract();
		if (ts != null)
			setDateOrdered (ts);
		ts = project.getDateFinish();
		if (ts != null)
			setDatePromised (ts);
		//
		setC_BPartner_ID(project.getC_BPartner_ID());
		setC_BPartner_Location_ID(project.getC_BPartner_Location_ID());
		setAD_User_ID(project.getAD_User_ID());
		//
		setM_Warehouse_ID(project.getM_Warehouse_ID());
		setM_PriceList_ID(project.getM_PriceList_ID());
		setC_PaymentTerm_ID(project.getC_PaymentTerm_ID());
		//
		setIsSOTrx(IsSOTrx);
		if (IsSOTrx)
		{
			if (DocSubTypeSO == null || DocSubTypeSO.length() == 0)
				setC_DocTypeTarget_ID(DocSubTypeSO_OnCredit);
			else
				setC_DocTypeTarget_ID(DocSubTypeSO);
		}
		else
			setC_DocTypeTarget_ID();
	}	//	MOrder

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public MOrder (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrder

	/**	Order Lines					*/
	private MOrderLine[] 	m_lines = null;
	/**	Tax Lines					*/
	private MOrderTax[] 	m_taxes = null;
	/** Force Creation of order		*/
	//private boolean			m_forceCreation = false;
	
	/**
	 * 	Overwrite Client/Org if required
	 * 	@param AD_Client_ID client
	 * 	@param AD_Org_ID org
	 */
	public void setClientOrg (int AD_Client_ID, int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	//	setClientOrg


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
	 * 	Set Business Partner (Ship+Bill)
	 *	@param C_BPartner_ID bpartner
	 */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		super.setC_BPartner_ID (C_BPartner_ID);
		super.setBill_BPartner_ID (C_BPartner_ID);
	}	//	setC_BPartner_ID
	
	/**
	 * 	Set Business Partner Location (Ship+Bill)
	 *	@param C_BPartner_Location_ID bp location
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		super.setC_BPartner_Location_ID (C_BPartner_Location_ID);
		super.setBill_Location_ID(C_BPartner_Location_ID);
	}	//	setC_BPartner_Location_ID

	/**
	 * 	Set Business Partner Contact (Ship+Bill)
	 *	@param AD_User_ID user
	 */
	public void setAD_User_ID (int AD_User_ID)
	{
		super.setAD_User_ID (AD_User_ID);
		super.setBill_User_ID (AD_User_ID);
	}	//	setAD_User_ID

	/**
	 * 	Set Ship Business Partner
	 *	@param C_BPartner_ID bpartner
	 */
	public void setShip_BPartner_ID (int C_BPartner_ID)
	{
		super.setC_BPartner_ID (C_BPartner_ID);
	}	//	setShip_BPartner_ID
	
	/**
	 * 	Set Ship Business Partner Location
	 *	@param C_BPartner_Location_ID bp location
	 */
	public void setShip_Location_ID (int C_BPartner_Location_ID)
	{
		super.setC_BPartner_Location_ID (C_BPartner_Location_ID);
	}	//	setShip_Location_ID

	/**
	 * 	Set Ship Business Partner Contact
	 *	@param AD_User_ID user
	 */
	public void setShip_User_ID (int AD_User_ID)
	{
		super.setAD_User_ID (AD_User_ID);
	}	//	setShip_User_ID
	
	
	/**
	 * 	Set Warehouse
	 *	@param M_Warehouse_ID warehouse
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		super.setM_Warehouse_ID (M_Warehouse_ID);
	}	//	setM_Warehouse_ID
	
	/**
	 * 	Set Drop Ship
	 *	@param IsDropShip drop ship
	 */
	public void setIsDropShip (boolean IsDropShip)
	{
		super.setIsDropShip (IsDropShip);
	}	//	setIsDropShip
	
	/*************************************************************************/

	/** Sales Order Sub Type - SO	*/
	public static final String		DocSubTypeSO_Standard = "SO";
	/** Sales Order Sub Type - OB	*/
	public static final String		DocSubTypeSO_Quotation = "OB";
	/** Sales Order Sub Type - ON	*/
	public static final String		DocSubTypeSO_Proposal = "ON";
	/** Sales Order Sub Type - PR	*/
	public static final String		DocSubTypeSO_Prepay = "PR";
	/** Sales Order Sub Type - WR	*/
	public static final String		DocSubTypeSO_POS = "WR";
	/** Sales Order Sub Type - WP	*/
	public static final String		DocSubTypeSO_Warehouse = "WP";
	/** Sales Order Sub Type - WI	*/
	public static final String		DocSubTypeSO_OnCredit = "WI";
	/** Sales Order Sub Type - RM	*/
	public static final String		DocSubTypeSO_RMA = "RM";

	/**
	 * 	Set Target Sales Document Type
	 * 	@param DocSubTypeSO_x SO sub type - see DocSubTypeSO_*
	 */
	public void setC_DocTypeTarget_ID (String DocSubTypeSO_x)
	{
		String sql = "SELECT C_DocType_ID FROM C_DocType "
			+ "WHERE AD_Client_ID=? AND AD_Org_ID IN (0," + getAD_Org_ID()
			+ ") AND DocSubTypeSO=? "
			+ " AND IsActive='Y' "
			+ "ORDER BY AD_Org_ID DESC, IsDefault DESC";
		int C_DocType_ID = DB.getSQLValue(null, sql, getAD_Client_ID(), DocSubTypeSO_x);
		if (C_DocType_ID <= 0)
			log.severe ("Not found for AD_Client_ID=" + getAD_Client_ID () + ", SubType=" + DocSubTypeSO_x);
		else
		{
			log.fine("(SO) - " + DocSubTypeSO_x);
			setC_DocTypeTarget_ID (C_DocType_ID);
			setIsSOTrx(true);
		}
	}	//	setC_DocTypeTarget_ID

	/**
	 * 	Set Target Document Type.
	 * 	Standard Order or PO
	 */
	public void setC_DocTypeTarget_ID ()
	{
		if (isSOTrx())		//	SO = Std Order
		{
			setC_DocTypeTarget_ID(DocSubTypeSO_Standard);
			return;
		}
		//	PO
		String sql = "SELECT C_DocType_ID FROM C_DocType "
			+ "WHERE AD_Client_ID=? AND AD_Org_ID IN (0," + getAD_Org_ID()
			+ ") AND DocBaseType='POO' "
			+ "ORDER BY AD_Org_ID DESC, IsDefault DESC";
		int C_DocType_ID = DB.getSQLValue(null, sql, getAD_Client_ID());
		if (C_DocType_ID <= 0)
			log.severe ("No POO found for AD_Client_ID=" + getAD_Client_ID ());
		else
		{
			log.fine("(PO) - " + C_DocType_ID);
			setC_DocTypeTarget_ID (C_DocType_ID);
		}
	}	//	setC_DocTypeTarget_ID


	/**
	 * 	Set Business Partner Defaults & Details.
	 * 	SOTrx should be set.
	 * 	@param bp business partner
	 */
	public void setBPartner (MBPartner bp)
	{
		if (bp == null)
			return;

		setC_BPartner_ID(bp.getC_BPartner_ID());
		//	Defaults Payment Term
		int ii = 0;
		if (isSOTrx())
			ii = bp.getC_PaymentTerm_ID();
		else
			ii = bp.getPO_PaymentTerm_ID();
		if (ii != 0)
			setC_PaymentTerm_ID(ii);
		//	Default Price List
		if (isSOTrx())
			ii = bp.getM_PriceList_ID();
		else
			ii = bp.getPO_PriceList_ID();
		if (ii != 0)
			setM_PriceList_ID(ii);
		//	Default Delivery/Via Rule
		String ss = bp.getDeliveryRule();
		if (ss != null)
			setDeliveryRule(ss);
		ss = bp.getDeliveryViaRule();
		if (ss != null)
			setDeliveryViaRule(ss);
		//	Default Invoice/Payment Rule
		ss = bp.getInvoiceRule();
		if (ss != null)
			setInvoiceRule(ss);
		ss = bp.getPaymentRule();
		if (ss != null)
			setPaymentRule(ss);
		//	Sales Rep
		ii = bp.getSalesRep_ID();
		if (ii != 0)
			setSalesRep_ID(ii);


		//	Set Locations
		MBPartnerLocation[] locs = bp.getLocations(false);
		if (locs != null)
		{
			for (int i = 0; i < locs.length; i++)
			{
				if (locs[i].isShipTo())
					super.setC_BPartner_Location_ID(locs[i].getC_BPartner_Location_ID());
				if (locs[i].isBillTo())
					setBill_Location_ID(locs[i].getC_BPartner_Location_ID());
			}
			//	set to first
			if (getC_BPartner_Location_ID() == 0 && locs.length > 0)
				super.setC_BPartner_Location_ID(locs[0].getC_BPartner_Location_ID());
			if (getBill_Location_ID() == 0 && locs.length > 0)
				setBill_Location_ID(locs[0].getC_BPartner_Location_ID());
		}
		if (getC_BPartner_Location_ID() == 0)
		{	
			// OpenUp. Gabriel Vila. 25/04/2014. Issue #2109.
			// No es necesario para documentos de compra que haya localizacion de socio de negocio.
			// Comento y cambio codigo
		
			//throw new BPartnerNoShipToAddressException(bp);

			if (this.isSOTrx()){
				throw new BPartnerNoShipToAddressException(bp);
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
			
		if (getBill_Location_ID() == 0)
		{
			throw new BPartnerNoBillToAddressException(bp);
		}	

		//	Set Contact
		MUser[] contacts = bp.getContacts(false);
		if (contacts != null && contacts.length == 1)
			setAD_User_ID(contacts[0].getAD_User_ID());
	}	//	setBPartner


	/**
	 * 	Copy Lines From other Order
	 *	@param otherOrder order
	 *	@param counter set counter info
	 *	@param copyASI copy line attributes Attribute Set Instance, Resaouce Assignment
	 *	@return number of lines copied
	 */
	public int copyLinesFrom (MOrder otherOrder, boolean counter, boolean copyASI)
	{
		if (isProcessed() || isPosted() || otherOrder == null)
			return 0;
		MOrderLine[] fromLines = otherOrder.getLines(false, null);
		int count = 0;
		for (int i = 0; i < fromLines.length; i++)
		{
			MOrderLine line = new MOrderLine (this);
			PO.copyValues(fromLines[i], line, getAD_Client_ID(), getAD_Org_ID());
			line.setC_Order_ID(getC_Order_ID());
			//
			line.setQtyDelivered(Env.ZERO);
			line.setQtyInvoiced(Env.ZERO);
			line.setQtyReserved(Env.ZERO);
			line.setDateDelivered(null);
			line.setDateInvoiced(null);
			//
			line.setOrder(this);
			line.set_ValueNoCheck ("C_OrderLine_ID", I_ZERO);	//	new
			//	References
			if (!copyASI)
			{
				line.setM_AttributeSetInstance_ID(0);
				line.setS_ResourceAssignment_ID(0);
			}
			if (counter)
				line.setRef_OrderLine_ID(fromLines[i].getC_OrderLine_ID());
			else
				line.setRef_OrderLine_ID(0);

			// don't copy linked lines
			line.setLink_OrderLine_ID(0);
			//	Tax
			if (getC_BPartner_ID() != otherOrder.getC_BPartner_ID())
				line.setTax();		//	recalculate
			//
			//
			line.setProcessed(false);
			if (line.save(get_TrxName()))
				count++;
			//	Cross Link
			if (counter)
			{
				fromLines[i].setRef_OrderLine_ID(line.getC_OrderLine_ID());
				fromLines[i].save(get_TrxName());
			}
		}
		if (fromLines.length != count)
			log.log(Level.SEVERE, "Line difference - From=" + fromLines.length + " <> Saved=" + count);
		return count;
	}	//	copyLinesFrom

	
	/**************************************************************************
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MOrder[")
			.append(get_ID()).append("-").append(getDocumentNo())
			.append(",IsSOTrx=").append(isSOTrx())
			.append(",C_DocType_ID=").append(getC_DocType_ID())
			.append(", GrandTotal=").append(getGrandTotal())
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
		ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.ORDER, getC_Order_ID(), get_TrxName());
		if (re == null)
			return null;
		return re.getPDF(file);
	}	//	createPDF
	
	/**
	 * 	Set Price List (and Currency, TaxIncluded) when valid
	 * 	@param M_PriceList_ID price list
	 */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		MPriceList pl = MPriceList.get(getCtx(), M_PriceList_ID, null);
		if (pl.get_ID() == M_PriceList_ID)
		{
			super.setM_PriceList_ID(M_PriceList_ID);
			setC_Currency_ID(pl.getC_Currency_ID());
			setIsTaxIncluded(pl.isTaxIncluded());
		}
	}	//	setM_PriceList_ID

	
	/**************************************************************************
	 * 	Get Lines of Order
	 * 	@param whereClause where clause or null (starting with AND)
	 * 	@param orderClause order clause
	 * 	@return lines
	 */
	public MOrderLine[] getLines (String whereClause, String orderClause)
	{
		//red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuffer whereClauseFinal = new StringBuffer(MOrderLine.COLUMNNAME_C_Order_ID+"=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MOrderLine.COLUMNNAME_Line;
		//
		List<MOrderLine> list = new Query(getCtx(), I_C_OrderLine.Table_Name, whereClauseFinal.toString(), get_TrxName())
										.setParameters(get_ID())
										.setOrderBy(orderClause)
										.list();
		for (MOrderLine ol : list) {
			ol.setHeaderInfo(this);
		}
		//
		return list.toArray(new MOrderLine[list.size()]);		
	}	//	getLines

	/**
	 * 	Get Lines of Order
	 * 	@param requery requery
	 * 	@param orderBy optional order by column
	 * 	@return lines
	 */
	public MOrderLine[] getLines (boolean requery, String orderBy)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	//	getLines

	/**
	 * 	Get Lines of Order.
	 * 	(used by web store)
	 * 	@return lines
	 */
	public MOrderLine[] getLines()
	{
		return getLines(false, null);
	}	//	getLines
	
	/**
	 * 	Renumber Lines
	 *	@param step start and step
	 */
	public void renumberLines (int step)
	{
		int number = step;
		MOrderLine[] lines = getLines(true, null);	//	Line is default
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			line.setLine(number);
			line.save(get_TrxName());
			number += step;
		}
		m_lines = null;
	}	//	renumberLines
	
	/**
	 * 	Does the Order Line belong to this Order
	 *	@param C_OrderLine_ID line
	 *	@return true if part of the order
	 */
	public boolean isOrderLine(int C_OrderLine_ID)
	{
		if (m_lines == null)
			getLines();
		for (int i = 0; i < m_lines.length; i++)
			if (m_lines[i].getC_OrderLine_ID() == C_OrderLine_ID)
				return true;
		return false;
	}	//	isOrderLine

	/**
	 * 	Get Taxes of Order
	 *	@param requery requery
	 *	@return array of taxes
	 */
	public MOrderTax[] getTaxes(boolean requery)
	{
		if (m_taxes != null && !requery)
			return m_taxes;
		//
		List<MOrderTax> list = new Query(getCtx(), I_C_OrderTax.Table_Name, "C_Order_ID=?", get_TrxName())
									.setParameters(get_ID())
									.list();
		m_taxes = list.toArray(new MOrderTax[list.size()]);
		return m_taxes;
	}	//	getTaxes
	
	
	/**
	 * 	Get Invoices of Order
	 * 	@return invoices
	 */
	public MInvoice[] getInvoices()
	{
		final String whereClause = "EXISTS (SELECT 1 FROM C_InvoiceLine il, C_OrderLine ol"
							        +" WHERE il.C_Invoice_ID=C_Invoice.C_Invoice_ID"
							        		+" AND il.C_OrderLine_ID=ol.C_OrderLine_ID"
							        		+" AND ol.C_Order_ID=?)";
		List<MInvoice> list = new Query(getCtx(), I_C_Invoice.Table_Name, whereClause, get_TrxName())
									.setParameters(get_ID())
									.setOrderBy("C_Invoice_ID DESC")
									.list();
		return list.toArray(new MInvoice[list.size()]);
	}	//	getInvoices

	/**
	 * 	Get latest Invoice of Order
	 * 	@return invoice id or 0
	 */
	public int getC_Invoice_ID()
	{
 		String sql = "SELECT C_Invoice_ID FROM C_Invoice "
			+ "WHERE C_Order_ID=? AND DocStatus IN ('CO','CL') "
			+ "ORDER BY C_Invoice_ID DESC";
		int C_Invoice_ID = DB.getSQLValue(get_TrxName(), sql, get_ID());
		return C_Invoice_ID;
	}	//	getC_Invoice_ID


	/**
	 * 	Get Shipments of Order
	 * 	@return shipments
	 */
	public MInOut[] getShipments()
	{
		final String whereClause = "EXISTS (SELECT 1 FROM M_InOutLine iol, C_OrderLine ol"
			+" WHERE iol.M_InOut_ID=M_InOut.M_InOut_ID"
			+" AND iol.C_OrderLine_ID=ol.C_OrderLine_ID"
			+" AND ol.C_Order_ID=?)";
		List<MInvoice> list = new Query(getCtx(), I_M_InOut.Table_Name, whereClause, get_TrxName())
									.setParameters(get_ID())
									.setOrderBy("M_InOut_ID DESC")
									.list();
		return list.toArray(new MInOut[list.size()]);
	}	//	getShipments

	/**
	 *	Get ISO Code of Currency
	 *	@return Currency ISO
	 */
	public String getCurrencyISO()
	{
		return MCurrency.getISO_Code (getCtx(), getC_Currency_ID());
	}	//	getCurrencyISO
	
	/**
	 * 	Get Currency Precision
	 *	@return precision
	 */
	public int getPrecision()
	{
		return MCurrency.getStdPrecision(getCtx(), getC_Currency_ID());
	}	//	getPrecision

	/**
	 * 	Get Document Status
	 *	@return Document Status Clear Text
	 */
	public String getDocStatusName()
	{
		return MRefList.getListName(getCtx(), 131, getDocStatus());
	}	//	getDocStatusName

	/**
	 * 	Set DocAction
	 *	@param DocAction doc action
	 */
	public void setDocAction (String DocAction)
	{
		setDocAction (DocAction, false);
	}	//	setDocAction

	/**
	 * 	Set DocAction
	 *	@param DocAction doc action
	 *	@param forceCreation force creation
	 */
	public void setDocAction (String DocAction, boolean forceCreation)
	{
		super.setDocAction (DocAction);
		//m_forceCreation = forceCreation;
	}	//	setDocAction
	
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
		String set = "SET Processed='"
			+ (processed ? "Y" : "N")
			+ "' WHERE C_Order_ID=" + getC_Order_ID();
		int noLine = DB.executeUpdateEx("UPDATE C_OrderLine " + set, get_TrxName());
		int noTax = DB.executeUpdateEx("UPDATE C_OrderTax " + set, get_TrxName());
		m_lines = null;
		m_taxes = null;
		log.fine("setProcessed - " + processed + " - Lines=" + noLine + ", Tax=" + noTax);
	}	//	setProcessed
	
	
	
	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord new
	 *	@return save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		//	Client/Org Check
		if (getAD_Org_ID() == 0)
		{
			int context_AD_Org_ID = Env.getAD_Org_ID(getCtx());
			if (context_AD_Org_ID != 0)
			{
				setAD_Org_ID(context_AD_Org_ID);
				log.warning("Changed Org to Context=" + context_AD_Org_ID);
			}
		}
		if (getAD_Client_ID() == 0)
		{
			m_processMsg = "AD_Client_ID = 0";
			return false;
		}
		
		//	New Record Doc Type - make sure DocType set to 0
		if (newRecord && getC_DocType_ID() == 0)
			setC_DocType_ID (0);

		//	Default Warehouse
		if (getM_Warehouse_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#M_Warehouse_ID");
			if (ii != 0)
				setM_Warehouse_ID(ii);
			else
			{
				throw new FillMandatoryException(COLUMNNAME_M_Warehouse_ID);
			}
		}
		//	Warehouse Org
		if (newRecord 
			|| is_ValueChanged("AD_Org_ID") || is_ValueChanged("M_Warehouse_ID"))
		{
			MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			if (wh.getAD_Org_ID() != getAD_Org_ID())
				log.saveWarning("WarehouseOrgConflict", "");
		}
		//	Reservations in Warehouse
		if (!newRecord && is_ValueChanged("M_Warehouse_ID"))
		{
			MOrderLine[] lines = getLines(false,null);
			for (int i = 0; i < lines.length; i++)
			{
				if (!lines[i].canChangeWarehouse())
					return false;
			}
		}
		
		// OpenUp. Gabriel Vila. 09/04/2013. Issue #655
		// Seteo socio de negocio por defecto para venta destacado y electrodomesticos.
		MDocType doc = (MDocType)this.getC_DocTypeTarget();
		if (doc.getValue() != null){
			if (doc.getValue().equalsIgnoreCase("peddest")){ 
				this.setC_BPartner_ID(MSysConfig.getIntValue("UY_ITAL_PARTNER_DESTACADO", 1000675, this.getAD_Client_ID()));
			}
			else if (doc.getValue().equalsIgnoreCase("pedelec")){
				this.setC_BPartner_ID(MSysConfig.getIntValue("UY_ITAL_PARTNER_ELECTRODOMESTICO", 1000691, this.getAD_Client_ID()));
			}
		}
		// Fin OpenUp. Issue #655
		
		//	No Partner Info - set Template
		if (getC_BPartner_ID() == 0)
			setBPartner(MBPartner.getTemplate(getCtx(), getAD_Client_ID()));
		if (getC_BPartner_Location_ID() == 0)
			setBPartner(new MBPartner(getCtx(), getC_BPartner_ID(), null));
		//	No Bill - get from Ship
		if (getBill_BPartner_ID() == 0)
		{
			setBill_BPartner_ID(getC_BPartner_ID());
			setBill_Location_ID(getC_BPartner_Location_ID());
		}
		if (getBill_Location_ID() == 0)
			setBill_Location_ID(getC_BPartner_Location_ID());

		//	Default Price List
		if (getM_PriceList_ID() == 0)
		{
			int ii = DB.getSQLValueEx(null,
				"SELECT M_PriceList_ID FROM M_PriceList "
				+ "WHERE AD_Client_ID=? AND IsSOPriceList=? AND IsActive=?"
				+ "ORDER BY IsDefault DESC", getAD_Client_ID(), isSOTrx(), true);
			if (ii != 0)
				setM_PriceList_ID (ii);
		}
		//	Default Currency
		if (getC_Currency_ID() == 0)
		{
			String sql = "SELECT C_Currency_ID FROM M_PriceList WHERE M_PriceList_ID=?";
			int ii = DB.getSQLValue (null, sql, getM_PriceList_ID());
			if (ii != 0)
				setC_Currency_ID (ii);
			else
				setC_Currency_ID(Env.getContextAsInt(getCtx(), "#C_Currency_ID"));
		}

		// OpenUp. Gabriel Vila. 02/05/2012. 
		// Si no tengo vendedor asociado, intento obtenerlo desde el socio de negocio.
		if (getSalesRep_ID() <= 0) {
			if (this.getC_BPartner_ID() > 0){
				MBPartner bp = new MBPartner(getCtx(), this.getC_BPartner_ID(), null);
				this.setSalesRep_ID(bp.getSalesRep_ID());
			}
		}
		// Fin OpenUp.		
		
		//	Default Sales Rep
		if (getSalesRep_ID() == 0)
		{		
			int ii = Env.getContextAsInt(getCtx(), "#SalesRep_ID");
			if (ii != 0)
				setSalesRep_ID (ii);
		}

		// OpenUp. Gabriel Vila. 02/05/2012. 
		// Si no tengo AD_USER_ID , entonces igual al SALESREP_ID
		if (this.getAD_User_ID() <= 0)
			this.setAD_User_ID(this.getSalesRep_ID());
		// Fin OpenUp.
		
		//	Default Document Type
		if (getC_DocTypeTarget_ID() == 0)
			setC_DocTypeTarget_ID(DocSubTypeSO_Standard);

		//	Default Payment Term
		if (getC_PaymentTerm_ID() == 0)
		{
			int ii = Env.getContextAsInt(getCtx(), "#C_PaymentTerm_ID");
			if (ii != 0)
				setC_PaymentTerm_ID(ii);
			else
			{
				String sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE AD_Client_ID=? AND IsDefault='Y'";
				ii = DB.getSQLValue(null, sql, getAD_Client_ID());
				if (ii != 0)
					setC_PaymentTerm_ID (ii);
			}
		}
	
		return true;
	}	//	beforeSave
	
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return true if can be saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success || newRecord)
			return success;
		
		// TODO: The changes here with UPDATE are not being saved on change log - audit problem  
		
		//	Propagate Description changes
		if (is_ValueChanged("Description") || is_ValueChanged("POReference"))
		{
			String sql = "UPDATE C_Invoice i"
				+ " SET (Description,POReference)="
					+ "(SELECT Description,POReference "
					+ "FROM C_Order o WHERE i.C_Order_ID=o.C_Order_ID) "
				+ "WHERE DocStatus NOT IN ('RE','CL') AND C_Order_ID=" + getC_Order_ID();
			int no = DB.executeUpdateEx(sql, get_TrxName());
			log.fine("Description -> #" + no);
		}

		//	Propagate Changes of Payment Info to existing (not reversed/closed) invoices
		if (is_ValueChanged("PaymentRule") || is_ValueChanged("C_PaymentTerm_ID")
			|| is_ValueChanged("C_Payment_ID")
			|| is_ValueChanged("C_CashLine_ID"))
		{
			String sql = "UPDATE C_Invoice i "
				+ "SET (PaymentRule,C_PaymentTerm_ID,C_Payment_ID,C_CashLine_ID)="
					+ "(SELECT PaymentRule,C_PaymentTerm_ID,C_Payment_ID,C_CashLine_ID "
					+ "FROM C_Order o WHERE i.C_Order_ID=o.C_Order_ID)"
				+ "WHERE DocStatus NOT IN ('RE','CL') AND C_Order_ID=" + getC_Order_ID();
			//	Don't touch Closed/Reversed entries
			int no = DB.executeUpdate(sql, get_TrxName());
			log.fine("Payment -> #" + no);
		}
	      
		//	Propagate Changes of Date Account to existing (not completed/reversed/closed) invoices
		if (is_ValueChanged("DateAcct"))
		{
			String sql = "UPDATE C_Invoice i "
				+ "SET (DateAcct)="
					+ "(SELECT DateAcct "
					+ "FROM C_Order o WHERE i.C_Order_ID=o.C_Order_ID)"
				+ "WHERE DocStatus NOT IN ('CO','RE','CL') AND Processed='N' AND C_Order_ID=" + getC_Order_ID();
			//	Don't touch Completed/Closed/Reversed entries
			int no = DB.executeUpdate(sql, get_TrxName());
			log.fine("DateAcct -> #" + no);
		}
	      
		//	Sync Lines
		if (   is_ValueChanged("AD_Org_ID")
		    || is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_ID)
		    || is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_Location_ID)
		    || is_ValueChanged(MOrder.COLUMNNAME_DateOrdered)
		    || is_ValueChanged(MOrder.COLUMNNAME_DatePromised)
		    || is_ValueChanged(MOrder.COLUMNNAME_M_Warehouse_ID)
		    || is_ValueChanged(MOrder.COLUMNNAME_M_Shipper_ID)
		    || is_ValueChanged(MOrder.COLUMNNAME_C_Currency_ID)) {
			MOrderLine[] lines = getLines();
			for (MOrderLine line : lines) {
				if (is_ValueChanged("AD_Org_ID"))
					line.setAD_Org_ID(getAD_Org_ID());
				if (is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_ID))
					line.setC_BPartner_ID(getC_BPartner_ID());
				if (is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_Location_ID))
					line.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
				if (is_ValueChanged(MOrder.COLUMNNAME_DateOrdered))
					line.setDateOrdered(getDateOrdered());
				if (is_ValueChanged(MOrder.COLUMNNAME_DatePromised))
					line.setDatePromised(getDatePromised());
				if (is_ValueChanged(MOrder.COLUMNNAME_M_Warehouse_ID))
					line.setM_Warehouse_ID(getM_Warehouse_ID());
				if (is_ValueChanged(MOrder.COLUMNNAME_M_Shipper_ID))
					line.setM_Shipper_ID(getM_Shipper_ID());
				if (is_ValueChanged(MOrder.COLUMNNAME_C_Currency_ID))
					line.setC_Currency_ID(getC_Currency_ID());
				line.saveEx();
			}
		}
				
		//
		return true;
	}	//	afterSave
	
	/**
	 * 	Before Delete
	 *	@return true of it can be deleted
	 */
	protected boolean beforeDelete ()
	{
		if (isProcessed())
			return false;
		
		for (MOrderLine line : getLines()) {
			line.deleteEx(true);
		}
		return true;
	}	//	beforeDelete
	
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
	}	//	processIt
	
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
		log.info("unlockIt - " + toString());
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
	
	
	/**************************************************************************
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		
		// OpenUp. Gabriel Vila. 02/12/2011. Issue #938.
		// Accion de preparar segun modelo de OpenUp. Comento original.
		return this.prepareItOpenUp();

		/*
		
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		MDocType dt = MDocType.get(getCtx(), getC_DocTypeTarget_ID());

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		
		//	Lines
		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		// Bug 1564431
		if (getDeliveryRule() != null && getDeliveryRule().equals(MOrder.DELIVERYRULE_CompleteOrder)) 
		{
			for (int i = 0; i < lines.length; i++) 
			{
				MOrderLine line = lines[i];
				MProduct product = line.getProduct();
				if (product != null && product.isExcludeAutoDelivery())
				{
					m_processMsg = "@M_Product_ID@ "+product.getValue()+" @IsExcludeAutoDelivery@";
					return DocAction.STATUS_Invalid;
				}
			}
		}
		
		//	Convert DocType to Target
		if (getC_DocType_ID() != getC_DocTypeTarget_ID() )
		{
			//	Cannot change Std to anything else if different warehouses
			if (getC_DocType_ID() != 0)
			{
				MDocType dtOld = MDocType.get(getCtx(), getC_DocType_ID());
				if (MDocType.DOCSUBTYPESO_StandardOrder.equals(dtOld.getDocSubTypeSO())		//	From SO
					&& !MDocType.DOCSUBTYPESO_StandardOrder.equals(dt.getDocSubTypeSO()))	//	To !SO
				{
					for (int i = 0; i < lines.length; i++)
					{
						if (lines[i].getM_Warehouse_ID() != getM_Warehouse_ID())
						{
							log.warning("different Warehouse " + lines[i]);
							m_processMsg = "@CannotChangeDocType@";
							return DocAction.STATUS_Invalid;
						}
					}
				}
			}
			
			//	New or in Progress/Invalid
			if (DOCSTATUS_Drafted.equals(getDocStatus()) 
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| getC_DocType_ID() == 0)
			{
				setC_DocType_ID(getC_DocTypeTarget_ID());
			}
			else	//	convert only if offer
			{
				if (dt.isOffer())
					setC_DocType_ID(getC_DocTypeTarget_ID());
				else
				{
					m_processMsg = "@CannotChangeDocType@";
					return DocAction.STATUS_Invalid;
				}
			}
		}	//	convert DocType

		//	Mandatory Product Attribute Set Instance
		String mandatoryType = "='Y'";	//	IN ('Y','S')
		String sql = "SELECT COUNT(*) "
			+ "FROM C_OrderLine ol"
			+ " INNER JOIN M_Product p ON (ol.M_Product_ID=p.M_Product_ID)" 
			+ " INNER JOIN M_AttributeSet pas ON (p.M_AttributeSet_ID=pas.M_AttributeSet_ID) "
			+ "WHERE pas.MandatoryType" + mandatoryType		
			+ " AND (ol.M_AttributeSetInstance_ID is NULL OR ol.M_AttributeSetInstance_ID = 0)"
			+ " AND ol.C_Order_ID=?";
		int no = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
		if (no != 0)
		{
			m_processMsg = "@LinesWithoutProductAttribute@ (" + no + ")";
			return DocAction.STATUS_Invalid;
		}

		//	Lines
		if (explodeBOM())
			lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		if (!reserveStock(dt, lines))
		{
			m_processMsg = "Cannot reserve Stock";
			return DocAction.STATUS_Invalid;
		}
		if (!calculateTaxTotal())
		{
			m_processMsg = "Error calculating tax";
			return DocAction.STATUS_Invalid;
		}
		
		//	Credit Check
		if (isSOTrx())
		{
			if (   MDocType.DOCSUBTYPESO_POSOrder.equals(dt.getDocSubTypeSO())
					&& PAYMENTRULE_Cash.equals(getPaymentRule())
					&& !MSysConfig.getBooleanValue("CHECK_CREDIT_ON_CASH_POS_ORDER", true, getAD_Client_ID(), getAD_Org_ID())) {
				// ignore -- don't validate for Cash POS Orders depending on sysconfig parameter
			} else if (MDocType.DOCSUBTYPESO_PrepayOrder.equals(dt.getDocSubTypeSO())
					&& !MSysConfig.getBooleanValue("CHECK_CREDIT_ON_PREPAY_ORDER", true, getAD_Client_ID(), getAD_Org_ID())) {
				// ignore -- don't validate Prepay Orders depending on sysconfig parameter
			} else {
				MBPartner bp = new MBPartner (getCtx(), getBill_BPartner_ID(), get_TrxName()); // bill bp is guaranteed on beforeSave

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
				BigDecimal grandTotal = MConversionRate.convertBase(getCtx(), 
						getGrandTotal(), getC_Currency_ID(), getDateOrdered(), 
						getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
				if (MBPartner.SOCREDITSTATUS_CreditHold.equals(bp.getSOCreditStatus(grandTotal)))
				{
					m_processMsg = "@BPartnerOverOCreditHold@ - @TotalOpenBalance@=" 
						+ bp.getTotalOpenBalance() + ", @GrandTotal@=" + grandTotal
						+ ", @SO_CreditLimit@=" + bp.getSO_CreditLimit();
					return DocAction.STATUS_Invalid;
				}
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
	//	if (!DOCACTION_Complete.equals(getDocAction()))		don't set for just prepare 
	//		setDocAction(DOCACTION_Complete);
		
		for(final MOrderLine ol:getLines())
		{
				Util.assume(ol.getQtyReserved().compareTo(ol.getQtyOrdered()) == 0, 
						"After prepareIt, reservations have been made");
		}
		return DocAction.STATUS_InProgress;

		*/
		// Fin OpenUp.

	}	//	prepareIt
	
	/**
	 * 	Explode non stocked BOM.
	 * 	@return true if bom exploded
	 */
	@SuppressWarnings("unused")
	private boolean explodeBOM()
	{
		boolean retValue = false;
		String where = "AND IsActive='Y' AND EXISTS "
			+ "(SELECT * FROM M_Product p WHERE C_OrderLine.M_Product_ID=p.M_Product_ID"
			+ " AND	p.IsBOM='Y' AND p.IsVerified='Y' AND p.IsStocked='N')";
		//
		String sql = "SELECT COUNT(*) FROM C_OrderLine "
			+ "WHERE C_Order_ID=? " + where; 
		int count = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
		while (count != 0)
		{
			retValue = true;
			renumberLines (1000);		//	max 999 bom items	

			//	Order Lines with non-stocked BOMs
			MOrderLine[] lines = getLines (where, MOrderLine.COLUMNNAME_Line);
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine line = lines[i];
				MProduct product = MProduct.get (getCtx(), line.getM_Product_ID());
				log.fine(product.getName());
				//	New Lines
				int lineNo = line.getLine ();
				//find default BOM with valid dates and to this product
				MPPProductBOM bom = MPPProductBOM.get(product, getAD_Org_ID(),getDatePromised(), get_TrxName());
				if(bom != null)
				{	
					MPPProductBOMLine[] bomlines = bom.getLines(getDatePromised());
					for (int j = 0; j < bomlines.length; j++)
					{
						MPPProductBOMLine bomline = bomlines[j];
						MOrderLine newLine = new MOrderLine (this);
						newLine.setLine (++lineNo);
						newLine.setM_Product_ID (bomline.getM_Product_ID ());
						newLine.setC_UOM_ID (bomline.getC_UOM_ID ());
						newLine.setQty (line.getQtyOrdered ().multiply (
							bomline.getQtyBOM()));
						if (bomline.getDescription () != null)
							newLine.setDescription (bomline.getDescription ());
						//
						newLine.setPrice ();
						newLine.save (get_TrxName());
					}
				}	
				
				/*MProductBOM[] boms = MProductBOM.getBOMLines (product);
				for (int j = 0; j < boms.length; j++)
				{
					//MProductBOM bom = boms[j];
					MPPProductBOMLine bom = boms[j];
					MOrderLine newLine = new MOrderLine (this);
					newLine.setLine (++lineNo);
					//newLine.setM_Product_ID (bom.getProduct ()
					//	.getM_Product_ID ());
					newLine.setM_Product_ID (bom.getM_Product_ID ());
					//newLine.setC_UOM_ID (bom.getProduct ().getC_UOM_ID ());
					newLine.setC_UOM_ID (bom.getC_UOM_ID ());
					//newLine.setQty (line.getQtyOrdered ().multiply (
					//		bom.getBOMQty ()));
					newLine.setQty (line.getQtyOrdered ().multiply (
						bom.getQtyBOM()));
					if (bom.getDescription () != null)
						newLine.setDescription (bom.getDescription ());
					//
					newLine.setPrice ();
					newLine.save (get_TrxName());
				}*/
				
				//	Convert into Comment Line
				line.setM_Product_ID (0);
				line.setM_AttributeSetInstance_ID (0);
				line.setPrice (Env.ZERO);
				line.setPriceLimit (Env.ZERO);
				line.setPriceList (Env.ZERO);
				line.setLineNetAmt (Env.ZERO);
				line.setFreightAmt (Env.ZERO);
				//
				String description = product.getName ();
				if (product.getDescription () != null)
					description += " " + product.getDescription ();
				if (line.getDescription () != null)
					description += " " + line.getDescription ();
				line.setDescription (description);
				line.save (get_TrxName());
			}	//	for all lines with BOM

			m_lines = null;		//	force requery
			count = DB.getSQLValue (get_TrxName(), sql, getC_Invoice_ID ());
			renumberLines (10);
		}	//	while count != 0
		return retValue;
	}	//	explodeBOM


	/**
	 * 	Reserve Inventory.
	 * 	Counterpart: MInOut.completeIt()
	 * 	@param dt document type or null
	 * 	@param lines order lines (ordered by M_Product_ID for deadlock prevention)
	 * 	@return true if (un) reserved
	 */
	private boolean reserveStock (MDocType dt, MOrderLine[] lines)
	{
		if (dt == null)
			dt = MDocType.get(getCtx(), getC_DocType_ID());

		// OpenUp. Gabriel Vila. 18/10/2010.
		// Reservo stock segun parametrizacion de transacciones de venta
		if (isSOTrx() && (Boolean)this.get_Value("UY_ReservaStock")==false) return true;
		// Fin OpenUp
		
		//	Binding
		boolean binding = !dt.isProposal();
		//	Not binding - i.e. Target=0
		if (DOCACTION_Void.equals(getDocAction())
			//	Closing Binding Quotation
			|| (MDocType.DOCSUBTYPESO_Quotation.equals(dt.getDocSubTypeSO()) 
				&& DOCACTION_Close.equals(getDocAction())) 
			) // || isDropShip() )
			binding = false;
		boolean isSOTrx = isSOTrx();
		log.fine("Binding=" + binding + " - IsSOTrx=" + isSOTrx);
		//	Force same WH for all but SO/PO
		int header_M_Warehouse_ID = getM_Warehouse_ID();
		if (MDocType.DOCSUBTYPESO_StandardOrder.equals(dt.getDocSubTypeSO())
			|| MDocType.DOCBASETYPE_PurchaseOrder.equals(dt.getDocBaseType()))
			header_M_Warehouse_ID = 0;		//	don't enforce
		
		BigDecimal Volume = Env.ZERO;
		BigDecimal Weight = Env.ZERO;
		
		//	Always check and (un) Reserve Inventory		
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			//	Check/set WH/Org
			if (header_M_Warehouse_ID != 0)	//	enforce WH
			{
				if (header_M_Warehouse_ID != line.getM_Warehouse_ID())
					line.setM_Warehouse_ID(header_M_Warehouse_ID);
				if (getAD_Org_ID() != line.getAD_Org_ID())
					line.setAD_Org_ID(getAD_Org_ID());
			}
			//	Binding
			BigDecimal target = binding ? line.getQtyOrdered() : Env.ZERO; 
			BigDecimal difference = target
				.subtract(line.getQtyReserved())
				.subtract(line.getQtyDelivered()); 
			if (difference.signum() == 0)
			{
				MProduct product = line.getProduct();
				if (product != null)
				{
					Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
					Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
				}
				continue;
			}
			
			log.fine("Line=" + line.getLine() 
				+ " - Target=" + target + ",Difference=" + difference
				+ " - Ordered=" + line.getQtyOrdered() 
				+ ",Reserved=" + line.getQtyReserved() + ",Delivered=" + line.getQtyDelivered());

			//	Check Product - Stocked and Item
			MProduct product = line.getProduct();
			if (product != null) 
			{
				if (product.isStocked())
				{
					BigDecimal ordered = isSOTrx ? Env.ZERO : difference;
					BigDecimal reserved = isSOTrx ? difference : Env.ZERO;
					int M_Locator_ID = 0; 
					//	Get Locator to reserve
					if (line.getM_AttributeSetInstance_ID() != 0)	//	Get existing Location
						M_Locator_ID = MStorage.getM_Locator_ID (line.getM_Warehouse_ID(), 
							line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), 
							ordered, get_TrxName());
					//	Get default Location
					if (M_Locator_ID == 0)
					{
						// try to take default locator for product first
						// if it is from the selected warehouse
						MWarehouse wh = MWarehouse.get(getCtx(), line.getM_Warehouse_ID());
						M_Locator_ID = product.getM_Locator_ID();
						if (M_Locator_ID!=0) {
							MLocator locator = new MLocator(getCtx(), product.getM_Locator_ID(), get_TrxName());
							//product has default locator defined but is not from the order warehouse
							if(locator.getM_Warehouse_ID()!=wh.get_ID()) {
								M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
							}
						} else {
							M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
						}
					}
					//	Update Storage
					if (!MStorage.add(getCtx(), line.getM_Warehouse_ID(), M_Locator_ID, 
						line.getM_Product_ID(), 
						line.getM_AttributeSetInstance_ID(), line.getM_AttributeSetInstance_ID(),
						Env.ZERO, reserved, ordered, get_TrxName()))
						return false;
				}	//	stockec
				//	update line
				line.setQtyReserved(line.getQtyReserved().add(difference));
				if (!line.save(get_TrxName()))
					return false;
				//
				Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
				Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
			}	//	product
		}	//	reverse inventory
		
		setVolume(Volume);
		setWeight(Weight);
		return true;
	}	//	reserveStock

	/**
	 * 	Calculate Tax and Total
	 * 	@return true if tax total calculated
	 */
	public boolean calculateTaxTotal()
	{
		log.fine("");
		//	Delete Taxes
		DB.executeUpdateEx("DELETE C_OrderTax WHERE C_Order_ID=" + getC_Order_ID(), get_TrxName());
		m_taxes = null;
		
		//	Lines
		BigDecimal totalLines = Env.ZERO;
		ArrayList<Integer> taxList = new ArrayList<Integer>();
		MOrderLine[] lines = getLines();
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			Integer taxID = new Integer(line.getC_Tax_ID());
			if (!taxList.contains(taxID))
			{
				MOrderTax oTax = MOrderTax.get (line, getPrecision(), 
					false, get_TrxName());	//	current Tax
				oTax.setIsTaxIncluded(isTaxIncluded());
				if (!oTax.calculateTaxFromLines())
					return false;
				if (!oTax.save(get_TrxName()))
					return false;
				taxList.add(taxID);
			}
			totalLines = totalLines.add(line.getLineNetAmt());
		}
		
		//	Taxes
		BigDecimal grandTotal = totalLines;
		MOrderTax[] taxes = getTaxes(true);
		for (int i = 0; i < taxes.length; i++)
		{
			MOrderTax oTax = taxes[i];
			MTax tax = oTax.getTax();
			if (tax.isSummary())
			{
				MTax[] cTaxes = tax.getChildTaxes(false);
				for (int j = 0; j < cTaxes.length; j++)
				{
					MTax cTax = cTaxes[j];
					BigDecimal taxAmt = cTax.calculateTax(oTax.getTaxBaseAmt(), isTaxIncluded(), getPrecision());
					//
					MOrderTax newOTax = new MOrderTax(getCtx(), 0, get_TrxName());
					newOTax.setClientOrg(this);
					newOTax.setC_Order_ID(getC_Order_ID());
					newOTax.setC_Tax_ID(cTax.getC_Tax_ID());
					newOTax.setPrecision(getPrecision());
					newOTax.setIsTaxIncluded(isTaxIncluded());
					newOTax.setTaxBaseAmt(oTax.getTaxBaseAmt());
					newOTax.setTaxAmt(taxAmt);
					if (!newOTax.save(get_TrxName()))
						return false;
					//
					if (!isTaxIncluded())
						grandTotal = grandTotal.add(taxAmt);
				}
				if (!oTax.delete(true, get_TrxName()))
					return false;
				if (!oTax.save(get_TrxName()))
					return false;
			}
			else
			{
				if (!isTaxIncluded())
					grandTotal = grandTotal.add(oTax.getTaxAmt());
			}
		}		
		//
		setTotalLines(totalLines);
		setGrandTotal(grandTotal);
		return true;
	}	//	calculateTaxTotal
	
	
	/**
	 * 	Approve Document
	 * 	@return true if success 
	 */
	public boolean  approveIt()
	{
		// OpenUp. Gabriel Vila. 26/02/2013. Issue #198.
		// Aprobacion de ordenes de compra 
		// Comento codigo original y sustituyo.
		
		/*
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
		*/
		
		//OpenUp. Nicolas Sarlabos. 11/12/2013. #1588. Al aprobarse una orden de compra directa se setean atributos.
		MDocType doc = new MDocType(Env.getCtx(), this.getC_DocTypeTarget_ID(), null);
		if (doc.getValue() != null){
			if(doc.getValue().equalsIgnoreCase("podirect")){
				
				setIsApproved(true);
				this.setDateApproved(new Timestamp(System.currentTimeMillis()));
				this.setApprovedBy(Env.getAD_User_ID(Env.getCtx()));
				this.setApprovedType(APPROVEDTYPE_AUTORIZADO);
				
				this.setDocStatus(DocumentEngine.STATUS_Approved);
				this.setDocAction(DocAction.ACTION_Complete);
					
			} else {
				
				if ((this.isSOTrx()) || (this.getUY_QuoteVendor_ID() <= 0)) {
					log.info("approveIt - " + toString());
					setIsApproved(true);
					return true;
				}
				
				
				// Valido que usuario actual tenga permiso para aprobar esta solicitud
				if (Env.getAD_User_ID(Env.getCtx()) != this.getApprovalUser_ID()){

					// Verifico que no sea el usuario delegado
					MUser user = new MUser(getCtx(), this.getApprovalUser_ID(), null);
					if (user.get_ValueAsBoolean("DelegateTasks")){
						if (user.get_ValueAsInt("DelegateUser_ID") <= 0){
							throw new AdempiereException("Usted No tiene permisos para Aprobar este Documento.");		
						}
					}
					else{
						throw new AdempiereException("Usted No tiene permisos para Aprobar este Documento.");
					}

				}

				
				this.isParcialApproved = false;
				String valueDocApproved ="", parcialApprovedDescription = "";
				
				if (this.isApproved()) return true;
				
				// Si aprobado nivel 1
				if (this.isApproved1()){
					this.setNeedApprove1(false);
					
					// Si aprobado nivel 2
					if (this.isApproved2()){
						this.setNeedApprove2(false);
						this.setApprovalStatus(APPROVALSTATUS_AprobadoNivel2);
						valueDocApproved="approved2";
					}
					else{
						// No aprobado nivel 2
						// Si requiere aprobacion nivel 2
						if (this.isNeedApprove2()){
							this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionNivel2);
							this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
							//this.setApprovalUser_ID(((MPOSection)this.getUY_POSection()).getWFResponsible(X_UY_POPolicyUser.NIVEL_GerenteGeneral, this.getUY_POPolicyRange_ID()));
							this.setApprovalUser_ID(1000005); // Por ahora Sergio Pereira
							parcialApprovedDescription = this.getApprovalDescription();
							this.setApprovalDescription(null);
							this.isParcialApproved = true;
						}
						else{
							// No requiere aprobacion nivel 2
							this.setApprovalStatus(APPROVALSTATUS_AprobadoNivel1);
							this.setIsApproved2(true);
						}
						valueDocApproved="approved1";
					}
				}
				
				this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
				
				if (this.isApproved1() && this.isApproved2()){
					this.setIsApproved(true);

					MWFResponsible responsible = new MWFResponsible(getCtx(), this.getApprovalUser_ID(), null);
					
					// Tracking
					MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
					track.setC_DocType_ID(MDocType.forValue(getCtx(), valueDocApproved, null).get_ID());
					track.setDocumentNo(MSequence.getDocumentNo(track.getC_DocType_ID(), null, false));
					track.setDateTrx(this.getDateOrdered());
					track.setDocStatus(DocumentEngine.STATUS_Approved);
					track.setC_Order_ID(this.get_ID());
					if (responsible.get_ID() > 0)
						if (responsible.getAD_User_ID() > 0)
							track.setAD_User_ID(responsible.getAD_User_ID());
					track.setDescription(this.getApprovalDescription());
					track.saveEx();
				}
				else{
					if (this.isParcialApproved){

						/*
						int adWfResponsible = ((MPOSection)this.getUY_POSection()).getWFResponsible(X_UY_POPolicyUser.NIVEL_Supervisor, this.getUY_POPolicyRange_ID());
						MWFResponsible responsible = new MWFResponsible(getCtx(), adWfResponsible, null); 
						*/
						
						MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
						track.setC_DocType_ID(MDocType.forValue(getCtx(), valueDocApproved, null).get_ID());
						track.setDocumentNo(MSequence.getDocumentNo(track.getC_DocType_ID(), null, false));
						track.setDateTrx(this.getDateOrdered());
						track.setDocStatus(this.getDocStatus());
						track.setC_Order_ID(this.get_ID());
						track.setDescription(parcialApprovedDescription);
						track.saveEx();
					}
				}				
			}
		}
		//Fin OpenUp. #1588.
		
		return true;
		
		// Fin OpenUp. Issue #198.
		
	}	//	approveIt
	
	/**
	 * 	Reject Approval
	 * 	@return true if success 
	 */
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		//OpenUp. Nicolas Sarlabos. 11/12/2013. #1588. 
		MDocType doc = new MDocType(Env.getCtx(), this.getC_DocTypeTarget_ID(), null);
		if (doc.getValue() != null){
			if(doc.getValue().equalsIgnoreCase("podirect")){
				this.setDateApproved(new Timestamp(System.currentTimeMillis()));
				this.setApprovedBy(Env.getAD_User_ID(Env.getCtx()));
				this.setApprovedType(APPROVEDTYPE_RECHAZADO);
			}
		}

		this.setDocStatus(DocumentEngine.STATUS_NotApproved);
		this.setDocAction(DocAction.ACTION_Void);
		//Fin OpenUp.

		return true;
	}	//	rejectIt
	
	
	/**************************************************************************
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

		
		// OpenUp. Gabriel Vila. 02/12/2011. Issue #938.
		// Accion de completar segun modelo de OpenUp. Comento original.
		if (!this.completeItOpenUp()) return DocAction.STATUS_Invalid;

		/*

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		String DocSubTypeSO = dt.getDocSubTypeSO();
		
		//	Just prepare
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}
		//	Offers
		if (MDocType.DOCSUBTYPESO_Proposal.equals(DocSubTypeSO)
			|| MDocType.DOCSUBTYPESO_Quotation.equals(DocSubTypeSO)) 
		{
			//	Binding
			if (MDocType.DOCSUBTYPESO_Quotation.equals(DocSubTypeSO))
				reserveStock(dt, getLines(true, MOrderLine.COLUMNNAME_M_Product_ID));
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
			if (m_processMsg != null)
				return DocAction.STATUS_Invalid;
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
			if (m_processMsg != null)
				return DocAction.STATUS_Invalid;
			// Set the definite document number after completed (if needed)
			setDefiniteDocumentNo();
			setProcessed(true);
			return DocAction.STATUS_Completed;
		}
		//	Waiting Payment - until we have a payment
		if (!m_forceCreation 
			&& MDocType.DOCSUBTYPESO_PrepayOrder.equals(DocSubTypeSO) 
			&& getC_Payment_ID() == 0 && getC_CashLine_ID() == 0)
		{
			setProcessed(true);
			return DocAction.STATUS_WaitingPayment;
		}
		
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		getLines(true,null);
		log.info(toString());
		StringBuffer info = new StringBuffer();
		
		boolean realTimePOS = MSysConfig.getBooleanValue("REAL_TIME_POS", false , getAD_Client_ID());
		
		//	Create SO Shipment - Force Shipment
		MInOut shipment = null;
		if (MDocType.DOCSUBTYPESO_OnCreditOrder.equals(DocSubTypeSO)		//	(W)illCall(I)nvoice
			|| MDocType.DOCSUBTYPESO_WarehouseOrder.equals(DocSubTypeSO)	//	(W)illCall(P)ickup	
			|| MDocType.DOCSUBTYPESO_POSOrder.equals(DocSubTypeSO)			//	(W)alkIn(R)eceipt
			|| MDocType.DOCSUBTYPESO_PrepayOrder.equals(DocSubTypeSO)) 
		{
			if (!DELIVERYRULE_Force.equals(getDeliveryRule()))
				setDeliveryRule(DELIVERYRULE_Force);
			//
			shipment = createShipment (dt, realTimePOS ? null : getDateOrdered());
			if (shipment == null)
				return DocAction.STATUS_Invalid;
			info.append("@M_InOut_ID@: ").append(shipment.getDocumentNo());
			String msg = shipment.getProcessMsg();
			if (msg != null && msg.length() > 0)
				info.append(" (").append(msg).append(")");
		}	//	Shipment
		

		//	Create SO Invoice - Always invoice complete Order
		if ( MDocType.DOCSUBTYPESO_POSOrder.equals(DocSubTypeSO)
			|| MDocType.DOCSUBTYPESO_OnCreditOrder.equals(DocSubTypeSO) 	
			|| MDocType.DOCSUBTYPESO_PrepayOrder.equals(DocSubTypeSO)) 
		{
			MInvoice invoice = createInvoice (dt, shipment, realTimePOS ? null : getDateOrdered());
			if (invoice == null)
				return DocAction.STATUS_Invalid;
			info.append(" - @C_Invoice_ID@: ").append(invoice.getDocumentNo());
			String msg = invoice.getProcessMsg();
			if (msg != null && msg.length() > 0)
				info.append(" (").append(msg).append(")");
		}	//	Invoice
		
		//	Counter Documents
		MOrder counter = createCounterDoc();
		if (counter != null)
			info.append(" - @CounterDoc@: @Order@=").append(counter.getDocumentNo());
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			if (info.length() > 0)
				info.append(" - ");
			info.append(valid);
			m_processMsg = info.toString();
			return DocAction.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		setProcessed(true);	
		m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		
		*/
		// Fin Openup.
		
		return DocAction.STATUS_Completed;
		
	}	//	completeIt
	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateOrdered(new Timestamp (System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}

	/**
	 * 	Create Shipment
	 *	@param dt order document type
	 *	@param movementDate optional movement date (default today)
	 *	@return shipment or null
	 */
	@SuppressWarnings("unused")
	private MInOut createShipment(MDocType dt, Timestamp movementDate)
	{
		log.info("For " + dt);
		MInOut shipment = new MInOut (this, dt.getC_DocTypeShipment_ID(), movementDate);
	//	shipment.setDateAcct(getDateAcct());
		if (!shipment.save(get_TrxName()))
		{
			m_processMsg = "Could not create Shipment";
			return null;
		}
		//
		MOrderLine[] oLines = getLines(true, null);
		for (int i = 0; i < oLines.length; i++)
		{
			MOrderLine oLine = oLines[i];
			//
			MInOutLine ioLine = new MInOutLine(shipment);
			//	Qty = Ordered - Delivered
			BigDecimal MovementQty = oLine.getQtyOrdered().subtract(oLine.getQtyDelivered()); 
			//	Location
			int M_Locator_ID = MStorage.getM_Locator_ID (oLine.getM_Warehouse_ID(), 
					oLine.getM_Product_ID(), oLine.getM_AttributeSetInstance_ID(), 
					MovementQty, get_TrxName());
			if (M_Locator_ID == 0)		//	Get default Location
			{
				MWarehouse wh = MWarehouse.get(getCtx(), oLine.getM_Warehouse_ID());
				M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
			}
			//
			ioLine.setOrderLine(oLine, M_Locator_ID, MovementQty);
			ioLine.setQty(MovementQty);
			if (oLine.getQtyEntered().compareTo(oLine.getQtyOrdered()) != 0)
				ioLine.setQtyEntered(MovementQty
					.multiply(oLine.getQtyEntered())
					.divide(oLine.getQtyOrdered(), 6, BigDecimal.ROUND_HALF_UP));
			if (!ioLine.save(get_TrxName()))
			{
				m_processMsg = "Could not create Shipment Line";
				return null;
			}
		}
		//	Manually Process Shipment
		shipment.processIt(DocAction.ACTION_Complete);
		shipment.saveEx(get_TrxName());
		if (!DOCSTATUS_Completed.equals(shipment.getDocStatus()))
		{
			m_processMsg = "@M_InOut_ID@: " + shipment.getProcessMsg();
			return null;
		}
		return shipment;
	}	//	createShipment

	/**
	 * 	Create Invoice
	 *	@param dt order document type
	 *	@param shipment optional shipment
	 *	@param invoiceDate invoice date
	 *	@return invoice or null
	 */
	@SuppressWarnings("unused")
	private MInvoice createInvoice (MDocType dt, MInOut shipment, Timestamp invoiceDate)
	{
		log.info(dt.toString());
		MInvoice invoice = new MInvoice (this, dt.getC_DocTypeInvoice_ID(), invoiceDate);
		if (!invoice.save(get_TrxName()))
		{
			m_processMsg = "Could not create Invoice";
			return null;
		}
		
		//	If we have a Shipment - use that as a base
		if (shipment != null)
		{
			if (!INVOICERULE_AfterDelivery.equals(getInvoiceRule()))
				setInvoiceRule(INVOICERULE_AfterDelivery);
			//
			MInOutLine[] sLines = shipment.getLines(false);
			for (int i = 0; i < sLines.length; i++)
			{
				MInOutLine sLine = sLines[i];
				//
				MInvoiceLine iLine = new MInvoiceLine(invoice);
				iLine.setShipLine(sLine);
				//	Qty = Delivered	
				if (sLine.sameOrderLineUOM())
					iLine.setQtyEntered(sLine.getQtyEntered());
				else
					iLine.setQtyEntered(sLine.getMovementQty());
				iLine.setQtyInvoiced(sLine.getMovementQty());
				if (!iLine.save(get_TrxName()))
				{
					m_processMsg = "Could not create Invoice Line from Shipment Line";
					return null;
				}
				//
				sLine.setIsInvoiced(true);
				if (!sLine.save(get_TrxName()))
				{
					log.warning("Could not update Shipment line: " + sLine);
				}
			}
		}
		else	//	Create Invoice from Order
		{
			if (!INVOICERULE_Immediate.equals(getInvoiceRule()))
				setInvoiceRule(INVOICERULE_Immediate);
			//
			MOrderLine[] oLines = getLines();
			for (int i = 0; i < oLines.length; i++)
			{
				MOrderLine oLine = oLines[i];
				//
				MInvoiceLine iLine = new MInvoiceLine(invoice);
				iLine.setOrderLine(oLine);
				//	Qty = Ordered - Invoiced	
				iLine.setQtyInvoiced(oLine.getQtyOrdered().subtract(oLine.getQtyInvoiced()));
				if (oLine.getQtyOrdered().compareTo(oLine.getQtyEntered()) == 0)
					iLine.setQtyEntered(iLine.getQtyInvoiced());
				else
					iLine.setQtyEntered(iLine.getQtyInvoiced().multiply(oLine.getQtyEntered())
						.divide(oLine.getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP));
				if (!iLine.save(get_TrxName()))
				{
					m_processMsg = "Could not create Invoice Line from Order Line";
					return null;
				}
			}
		}
		//	Manually Process Invoice
		invoice.processIt(DocAction.ACTION_Complete);
		invoice.saveEx(get_TrxName());
		setC_CashLine_ID(invoice.getC_CashLine_ID());
		if (!DOCSTATUS_Completed.equals(invoice.getDocStatus()))
		{
			m_processMsg = "@C_Invoice_ID@: " + invoice.getProcessMsg();
			return null;
		}
		return invoice;
	}	//	createInvoice
	
	/**
	 * 	Create Counter Document
	 * 	@return counter order
	 */
	@SuppressWarnings("unused")
	private MOrder createCounterDoc()
	{
		//	Is this itself a counter doc ?
		if (getRef_Order_ID() != 0)
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
		MOrder counter = copyFrom (this, getDateOrdered(), 
			C_DocTypeTarget_ID, !isSOTrx(), true, false, get_TrxName());
		//
		counter.setAD_Org_ID(counterAD_Org_ID);
		counter.setM_Warehouse_ID(counterOrgInfo.getM_Warehouse_ID());
		//
		counter.setBPartner(counterBP);
		counter.setDatePromised(getDatePromised());		// default is date ordered 
		//	Refernces (Should not be required
		counter.setSalesRep_ID(getSalesRep_ID());
		counter.save(get_TrxName());
		
		//	Update copied lines
		MOrderLine[] counterLines = counter.getLines(true, null);
		for (int i = 0; i < counterLines.length; i++)
		{
			MOrderLine counterLine = counterLines[i];
			counterLine.setOrder(counter);	//	copies header values (BP, etc.)
			counterLine.setPrice();
			counterLine.setTax();
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
	 * 	Set Qtys to 0 - Sales: reverse all documents
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		// OpenUp. Gabriel Vila. 17/02/2012.
		// Anulacion segun modelo de OpenUp. Comento lineas originales.
		return this.voidItOpenUp();

		/*
		
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			BigDecimal old = line.getQtyOrdered();
			if (old.signum() != 0)
			{
				line.addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + old + ")");
				line.setQty(Env.ZERO);
				line.setLineNetAmt(Env.ZERO);
				line.save(get_TrxName());
			}
			//AZ Goodwill	
			if (!isSOTrx())
			{
				deleteMatchPOCostDetail(line);
			}
		}
		
		// update taxes
		MOrderTax[] taxes = getTaxes(true);
		for (MOrderTax tax : taxes )
		{
			if ( !(tax.calculateTaxFromLines() && tax.save()) )
				return false;
		}
		
		addDescription(Msg.getMsg(getCtx(), "Voided"));
		//	Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (void)";
			return false;
		}
		
		// UnLink All Requisitions
		MRequisitionLine.unlinkC_Order_ID(getCtx(), get_ID(), get_TrxName());
		
		if (!createReversals())
			return false;
		
		// globalqss - 2317928 - Reactivating/Voiding order must reset posted
		MFactAcct.deleteEx(MOrder.Table_ID, getC_Order_ID(), get_TrxName());
		setPosted(false);
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
		
		*/
		// Fin OpenUp.
		
		
	}	//	voidIt

	
	/**
	 * Anulacion segun modelo de OpenUp.
	 * OpenUp Ltda. Issue # migracion.
	 * @author Gabriel Vila - 21/02/2012
	 * @see
	 * @return
	 */
	private boolean voidItOpenUp() {

		// OpenUP. Gabriel Vila. 17/01/2011.
		// Cancelacion de pedidos pendientes.
		// Al anular un pedido pendiente puede pasar que todo el pedido este pendiente
		// en cuyo caso dejo el proceso VoidIT normal (anulacion del pedido completo),
		// o puede ser que parte del pedido este reservado y/o entregado y/o facturado.
		// En este ultimo caso no tengo que anular el pedido, solamente tengo que 
		// marcas la cantidad pendiente de las lineas como cantidad cancelada.
		if (this.isSOTrx() && this.pendienteParcial()){
			// Cancelo pendiente parcial de cada linea
			MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine line = lines[i];
				line.cancelarLinea();
			}
			this.setuy_cancelado(true);
			setProcessed(true);
			setDocStatus(DocumentEngine.STATUS_Voided);
			setDocAction(DocumentEngine.ACTION_None);
			return true;
		}
		// Fin OpenUp
		
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			
			// Si tengo documento asociado con esta linea de orden de compra no puedo eliminarla
			MMatchPO[] matches = MMatchPO.getOrderLine(getCtx(), line.get_ID(), get_TrxName());
			if (matches.length > 0){
				m_processMsg = "No se puede anular este documento ya que tiene movimientos posteriores en el flujo de compra.";
				return false;
			}
			
			BigDecimal old = line.getQtyOrdered();
			if (old.signum() != 0)
			{
				line.addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + old + ")");
				line.setQty(Env.ZERO);
				line.setLineNetAmt(Env.ZERO);

				// OpenUp. Gabriel Vila. 07/04/2011. Issue #115.
				// Al anular orden de venta me aseguro que no quede asociacion con posible Orden de Proceso
				line.setPP_Order_ID(0);
				// Fin OpenUp

				line.saveEx(get_TrxName());
			}
			//AZ Goodwill	
			if (!isSOTrx())
			{
				deleteMatchPOCostDetail(line);
			}
		}
		
		// update taxes
		MOrderTax[] taxes = getTaxes(true);
		for (MOrderTax tax : taxes )
		{
			if ( !(tax.calculateTaxFromLines() && tax.save()) )
				return false;
		}
		
		addDescription(Msg.getMsg(getCtx(), "Voided"));
		
		// UnLink All Requisitions
		MRequisitionLine.unlinkC_Order_ID(getCtx(), get_ID(), get_TrxName());
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setProcessed(true);
		setDocStatus(DocumentEngine.STATUS_Voided);
		setDocAction(DocumentEngine.ACTION_None);

		return true;
	}


	/**
	 * 	Create Shipment/Invoice Reversals
	 * 	@return true if success
	 */
	@SuppressWarnings("unused")
	private boolean createReversals()
	{
		//	Cancel only Sales 
		if (!isSOTrx())
			return true;
		
		log.info("createReversals");
		StringBuffer info = new StringBuffer();
		
		//	Reverse All *Shipments*
		info.append("@M_InOut_ID@:");
		MInOut[] shipments = getShipments();
		for (int i = 0; i < shipments.length; i++)
		{
			MInOut ship = shipments[i];
			//	if closed - ignore
			if (MInOut.DOCSTATUS_Closed.equals(ship.getDocStatus())
				|| MInOut.DOCSTATUS_Reversed.equals(ship.getDocStatus())
				|| MInOut.DOCSTATUS_Voided.equals(ship.getDocStatus()) )
				continue;
			ship.set_TrxName(get_TrxName());
		
			//	If not completed - void - otherwise reverse it
			if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus()))
			{
				if (ship.voidIt())
					ship.setDocStatus(MInOut.DOCSTATUS_Voided);
			}
			else if (ship.reverseCorrectIt())	//	completed shipment
			{
				ship.setDocStatus(MInOut.DOCSTATUS_Reversed);
				info.append(" ").append(ship.getDocumentNo());
			}
			else
			{
				m_processMsg = "Could not reverse Shipment " + ship;
				return false;
			}
			ship.setDocAction(MInOut.DOCACTION_None);
			ship.save(get_TrxName());
		}	//	for all shipments
			
		//	Reverse All *Invoices*
		info.append(" - @C_Invoice_ID@:");
		MInvoice[] invoices = getInvoices();
		for (int i = 0; i < invoices.length; i++)
		{
			MInvoice invoice = invoices[i];
			//	if closed - ignore
			if (MInvoice.DOCSTATUS_Closed.equals(invoice.getDocStatus())
				|| MInvoice.DOCSTATUS_Reversed.equals(invoice.getDocStatus())
				|| MInvoice.DOCSTATUS_Voided.equals(invoice.getDocStatus()) )
				continue;			
			invoice.set_TrxName(get_TrxName());
			
			//	If not completed - void - otherwise reverse it
			if (!MInvoice.DOCSTATUS_Completed.equals(invoice.getDocStatus()))
			{
				if (invoice.voidIt())
					invoice.setDocStatus(MInvoice.DOCSTATUS_Voided);
			}
			else if (invoice.reverseCorrectIt())	//	completed invoice
			{
				invoice.setDocStatus(MInvoice.DOCSTATUS_Reversed);
				info.append(" ").append(invoice.getDocumentNo());
			}
			else
			{
				m_processMsg = "Could not reverse Invoice " + invoice;
				return false;
			}
			invoice.setDocAction(MInvoice.DOCACTION_None);
			invoice.save(get_TrxName());
		}	//	for all shipments
		
		m_processMsg = info.toString();
		return true;
	}	//	createReversals
	
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		//OpenUp Nicolas Sarlabos issue #901 17/10/2011,se comenta bloque de codigo
		/*
		//	Close Not delivered Qty - SO/PO
		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			BigDecimal old = line.getQtyOrdered();
			if (old.compareTo(line.getQtyDelivered()) != 0)
			{
				line.setQtyLostSales(line.getQtyOrdered().subtract(line.getQtyDelivered()));
				line.setQtyOrdered(line.getQtyDelivered());
				//	QtyEntered unchanged
				line.addDescription("Close (" + old + ")");
				line.save(get_TrxName());
			}
		}
		//	Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (close)";
			return false;
		}
	
		*/
		//fin OpenUp Nicolas Sarlabos issue #901 17/10/2011

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		
		//INI OpenUp SBT 11/11/2015 Issue # 5028 Se debe controlar que no tenga lineas con cant pendiente de la cant bonificada
		MOrderLine[] lines = this.getLines();
		for(MOrderLine l : lines){
			if(l.get_ValueAsBoolean("UY_BonificaSimple")){
				BigDecimal total = l.getQtyEntered().add((l.get_Value("QtyReward")!=null)? (BigDecimal)l.get_Value("QtyReward"):Env.ZERO);
				if((total.subtract(l.getQtyDelivered()).compareTo(Env.ZERO))!=0){//La cantidad reservada tiene que ser 
					return false;
				}
			}
		}//FIN OpenUp SBT 11/11/2015 Issue # 5028

		setDocStatus(DOCSTATUS_Closed);
		setProcessed(true);
		setDocAction(DOCACTION_None);

		return true;
	}	//	closeIt
	
	/**
	 * @author: phib
	 * re-open a closed order
	 * (reverse steps of close())
	 */
	public String reopenIt() {
		log.info(toString());
		if (!MOrder.DOCSTATUS_Closed.equals(getDocStatus()))
		{
			return "Not closed - can't reopen";
		}
		
		//	
		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		for (int i = 0; i < lines.length; i++)
		{
			MOrderLine line = lines[i];
			if (Env.ZERO.compareTo(line.getQtyLostSales()) != 0)
			{
				line.setQtyOrdered(line.getQtyLostSales().add(line.getQtyDelivered()));
				line.setQtyLostSales(Env.ZERO);
				//	QtyEntered unchanged
				
				// Strip Close() tags from description
				String desc = line.getDescription();
				if (desc == null)
					desc = "";
				Pattern pattern = Pattern.compile("( \\| )?Close \\(.*\\)");
				String[] parts = pattern.split(desc);
				desc = "";
				for (String s : parts) {
					desc = desc.concat(s);
				}
				line.setDescription(desc);
				if (!line.save(get_TrxName()))
					return "Couldn't save orderline";
			}
		}
		//	Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (close)";
			return "Failed to update reservations";
		}

		setDocStatus(MOrder.DOCSTATUS_Completed);
		setDocAction(DOCACTION_Close);
		if (!this.save(get_TrxName()))
			return "Couldn't save reopened order";
		else
			return "";
	}	//	reopenIt
	/**
	 * 	Reverse Correction - same void
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		return voidIt();
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
	 * 	Re-activate.
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		
		// OpenUp. Gabriel Vila. 02/12/2011. Issue #938.
		// Accion de completar segun modelo de OpenUp. Comento original.
		return this.reActivateItOpenUp();

		/*
		
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;	
				
		
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		String DocSubTypeSO = dt.getDocSubTypeSO();
		
		//	Replace Prepay with POS to revert all doc
		if (MDocType.DOCSUBTYPESO_PrepayOrder.equals (DocSubTypeSO))
		{
			MDocType newDT = null;
			MDocType[] dts = MDocType.getOfClient (getCtx());
			for (int i = 0; i < dts.length; i++)
			{
				MDocType type = dts[i];
				if (MDocType.DOCSUBTYPESO_PrepayOrder.equals(type.getDocSubTypeSO()))
				{
					if (type.isDefault() || newDT == null)
						newDT = type;
				}
			}
			if (newDT == null)
				return false;
			else
				setC_DocType_ID (newDT.getC_DocType_ID());
		}

		//	PO - just re-open
		if (!isSOTrx())
			log.info("Existing documents not modified - " + dt);
		//	Reverse Direct Documents
		else if (MDocType.DOCSUBTYPESO_OnCreditOrder.equals(DocSubTypeSO)	//	(W)illCall(I)nvoice
			|| MDocType.DOCSUBTYPESO_WarehouseOrder.equals(DocSubTypeSO)	//	(W)illCall(P)ickup	
			|| MDocType.DOCSUBTYPESO_POSOrder.equals(DocSubTypeSO))			//	(W)alkIn(R)eceipt
		{
			if (!createReversals())
				return false;
		}
		else
		{
			log.info("Existing documents not modified - SubType=" + DocSubTypeSO);
		}

		// globalqss - 2317928 - Reactivating/Voiding order must reset posted
		MFactAcct.deleteEx(MOrder.Table_ID, getC_Order_ID(), get_TrxName());
		setPosted(false);
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		
		for(final MOrderLine ol: getLines())
		{
			Util.assume(ol.getQtyInvoiced().signum() == 0, 
					"After reactivateIt, QtyInvoiced is zero");
			Util.assume(ol.getQtyReserved().compareTo(ol.getQtyOrdered()) == 0, 
					"After reactivateIt, reservations are still in place");
		}
		
		return true;
		
		*/
		// Fin OpenUp.
		
	}	//	reActivateIt
	
	
	/**
	 * Accion de reactivar segun modelo de OpenUp.
	 * OpenUp Ltda. Issue # migracion.
	 * @author Gabriel Vila - 21/02/2012
	 * @see
	 * @return
	 */
	private boolean reActivateItOpenUp(){

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;	
		
		if (isSOTrx()){
			// OpenUp. Gabriel Vila. 10/03/2011. Issue #424.
			// Al reactivar la orden debe quedar como pendiente de verificacion crediticia
			setuy_credit_approved(false);
			setIsApproved(false);
			setIsCreditApproved(false);
			setuy_credit_message("");
			// Fin OpenUp.
		}

		else{
			// Para compras no dejo reactivar si tiene movimientos posteriores en alguna de sus lineas
			MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
			for (int i = 0; i < lines.length; i++)
			{
				MOrderLine line = lines[i];
				// Si tengo documento asociado con esta linea de orden de compra no puedo eliminarla
				MMatchPO[] matches = MMatchPO.getOrderLine(getCtx(), line.get_ID(), get_TrxName());
				if (matches.length > 0){
					m_processMsg = "No se puede anular este documento ya que tiene movimientos posteriores en el flujo de compra.";
					return false;
				}
			}
			
		}
		
		/* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
		MFactAcct.deleteEx(MOrder.Table_ID, getC_Order_ID(), get_TrxName());
		setPosted(false);
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setDocStatus(DOCSTATUS_InProgress);
		setProcessed(false);
		
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
		//	: Grand Total = 123.00 (#1)
		sb.append(": ").
			append(Msg.translate(getCtx(),"GrandTotal")).append("=").append(getGrandTotal());
		if (m_lines != null)
			sb.append(" (#").append(m_lines.length).append(")");
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
		return getGrandTotal();
	}	//	getApprovalAmt
	
	//AZ Goodwill
	private String deleteMatchPOCostDetail(MOrderLine line)
	{
		// Get Account Schemas to delete MCostDetail
		MAcctSchema[] acctschemas = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
		for(int asn = 0; asn < acctschemas.length; asn++)
		{
			MAcctSchema as = acctschemas[asn];
			
			if (as.isSkipOrg(getAD_Org_ID()))
			{
				continue;
			}
			
			// update/delete Cost Detail and recalculate Current Cost
			MMatchPO[] mPO = MMatchPO.getOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName()); 
			// delete Cost Detail if the Matched PO has been deleted
			if (mPO.length == 0)
			{
				MCostDetail cd = MCostDetail.get(getCtx(), "C_OrderLine_ID=?", 
						line.getC_OrderLine_ID(), line.getM_AttributeSetInstance_ID(), 
						as.getC_AcctSchema_ID(), get_TrxName());
				if (cd !=  null)
				{
					cd.setProcessed(false);
					cd.delete(true);
				}
			}
		}
		
		return "";
	}

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
	 * Checks if the order is fully delivered and if it is
	 * the IsDelivered flag will be updated.
	 * 
	 * @param C_Order_ID
	 */
	public void updateIsDelivered() throws SQLException {
		
		if (isDelivered()) return;
		
		String query = "SELECT SUM(QtyOrdered-QtyDelivered) FROM C_OrderLine WHERE C_Order_ID=?";
		PreparedStatement ps = DB.prepareStatement(query, get_TrxName());
		ps.setInt(1, get_ID());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int delta = rs.getInt(1);
			if (delta==0) {
				setIsDelivered(true);
			}
		}
		rs.close();
		ps.close();
		
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Verifica si el pedido esta parcialmente pendiente.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 17/01/2011
	 */
	private boolean pendienteParcial(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean value = false;
		
		try{
			sql ="SELECT m_product_id, qtyordered, (qtyordered - qtyreserved - qtyinvoiced) as pendiente " + 
 		  	" FROM " + X_C_OrderLine.Table_Name + 
		  	" WHERE c_order_id =?";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getC_Order_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				if (rs.getBigDecimal(1).compareTo(rs.getBigDecimal(2))!=0){
					value = true;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			value = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;		
	}

	/**
	 * OpenUp.	
	 * Descripcion : Verifica y valida credito de cliente al momento de completar este pedido.
	 * @return String. null si todo bien, o un mensaje de credito invalido.
	 * @author  Gabriel Vila 
	 * Fecha : 27/01/2011
	 */
	private String validateCredit(){

		// OpenUp. Gabriel Vila. 19/08/2011. Issue #835.
		// Si el total del pedido es CERO no debo verficar credito
		if (this.getGrandTotal().compareTo(Env.ZERO) == 0) return null;
		// Fin Issue #835.
		
		// Obtengo Modelo de Cliente para saber limite de credito abierto y documentado
		MBPartner bp = new MBPartner(getCtx(), this.getC_BPartner_ID(), null);
		if (bp.get_ID() <= 0) return "No se pudo obtener informacion del Cliente del Pedido.";

		// Verifico que el cliente no tenga saldos vencidos
		BigDecimal saldoVencido = bp.getDueAmt();
		if (saldoVencido.compareTo(Env.ZERO) > 0) {
			return "El cliente tiene Saldo Vencido : " + saldoVencido.setScale (2, BigDecimal.ROUND_HALF_UP);
		}
		
		//BigDecimal saldoAbierto = bp.getuy_credit_openamt().add(this.getGrandTotal());
		
		//OpenUp Nicolas Garcia Issue#677 
		BigDecimal saldoAbierto = bp.getuy_credit_openamt().add(this.calculateNetAllLinesPending());	
		//Fin Nicolas Garcia 
		
		//Si el saldo abierto supera el limite de credito del cliente, aviso y salgo
		if (saldoAbierto.compareTo(bp.getSO_CreditLimit()) > 0) {
			return "El cliente supera el Limite de Credito para Cuenta Abierta." + "Limite Credito Abierto : " + bp.getSO_CreditLimit() + "\n" + "Saldo Abierto incluyendo Pedido : " + saldoAbierto;
		}
			
		BigDecimal saldoDocumentada = bp.getuy_credit_openamt_doc().add(this.getGrandTotal());

		// Si el saldo documentado supera el limite de credito del cliente, aviso y salgo
		if (saldoAbierto.compareTo(bp.getuy_creditlimit_doc()) > 0) {
			return "El cliente supera el Limite de Credito para Cuenta Documentada.\n" + "Limite Credito Documentado : " + bp.getuy_creditlimit_doc() + "\n" + "Saldo Documentado incluyendo Pedido : " + saldoDocumentada;
		}
		
		return null;
	}


	/**
	 * OpenUp.	Issue#677
	 * Descripcion :Se genera metodo para calcular el saldo pendiente  para luego usarlo en la validacion crediticia.
	 * @return BigDecimal Salida
	 * @author  Nicolas Garcia
	 * Fecha : 10/06/2011
	 */
		private BigDecimal calculateNetAllLinesPending(){
			
			BigDecimal salida=Env.ZERO;// OpenUp Nicolas Garcia ISSUE #770 05/07/2011
			String sql = "";
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			try{
				//Se selecciona la suma del saldo de la vista pedidos_pendiente_lineas
				sql ="SELECT COALESCE (SUM(saldo),0) as saldo FROM vuy_pedidos_pendientes_lineas"+ 
			  			" WHERE c_order_id =?";
				
				pstmt = DB.prepareStatement (sql, null);
				pstmt.setInt(1, this.getC_Order_ID());
				
				rs = pstmt.executeQuery ();
			
				if(rs.next()){
					salida= (rs.getBigDecimal("saldo"));
					
				}
				
				// OpenUp Nicolas Garcia ISSUE #770 05/07/2011
				if(salida.compareTo(Env.ZERO)==0){
					//Si no encuentro saldo pendiente el monto es el total
					salida=this.getTotalLines();
				}
				// Fin Nicolas Garcia ISSUE #770 05/07/2011
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
			
			return salida;
		}
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna la suma de bultos de las lineas de este pedido.
	 * Se consideran aquellos productos que tienen unidad de venta distinto de la unidad.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 01/03/2011
	 */
	public BigDecimal getCantidadBultos(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO;
		
		try{
			sql ="SELECT coalesce(SUM(pendiente),0) as cantidad " + 
 		  	" FROM vuy_pedidos_pendientes_lineas " + 
		  	" WHERE c_order_id =?" +
		  	" AND c_uom_id<>100";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getC_Order_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal(1);
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
		return value;		
	}

	/**
	 * OpenUp.	
	 * Descripcion : Verifica si esta orden tiene facturas asociadas.
	 * @return boolean. True si tiene, false sino.
	 * @author  Gabriel Vila 
	 * Fecha : 10/03/2011
	 */
	public boolean haveInvoices() {
		return this.getInvoices().length>=1;
	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Verifica si esta orden tiene entregas asociadas.
	 * @return boolean. True si tiene, false sino.
	 * @author  Gabriel Vila 
	 * Fecha : 10/03/2011
	 */
	public boolean haveShipments() {
		return this.getShipments().length>=1;
	}


	/**
	 * OpenUp.	
	 * Descripcion : Verifica si esta orden tiene reservas asociadas.
	 * @return boolean. True si tiene, false sino.
	 * @author  Gabriel Vila 
	 * Fecha : 10/03/2011
	 */
	public boolean haveReserves() {
		return this.getReserves().length>=1;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene Reservas asociadas a esta Orden.
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 10/03/2011
	 */
	public MReservaPedidoHdr[] getReserves(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MReservaPedidoHdr> list = new ArrayList<MReservaPedidoHdr>();
		
		try{
			sql ="SELECT uy_reservapedidohdr_id " + 
 		  	" FROM " + X_UY_ReservaPedidoHdr.Table_Name + 
		  	" WHERE c_order_id =?";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getC_Order_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MReservaPedidoHdr value = new MReservaPedidoHdr(Env.getCtx(), rs.getInt(1), null);
				list.add(value);
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
		
		return list.toArray(new MReservaPedidoHdr[list.size()]);		
	}

	/**
	 * OpenUp.	
	 * Descripcion : Validacion de una orden reactivada.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 11/03/2011
	 */
	private String validateReactivatedOrder(){
		String result = null;
		
		// Recorro lineas del pedido
		MOrderLine[] lines = getLines();
		for (int i=0; i<lines.length; i++){
			MOrderLine line = lines[i];
			
			
			if (line.getQtyOrdered().compareTo(Env.ZERO) > 0){
				// Si la cantidad del pedido-reservas-facturas es menor a cero, dejo cantidad
				// del pedido original y lanzo mensaje de invalidez.
				BigDecimal pendienteAux = line.getQtyOrdered().subtract(line.getQtyReserved()).subtract(line.getQtyInvoiced());
				if (pendienteAux.compareTo(BigDecimal.ZERO)<0){
					
					MProduct prod = new MProduct(getCtx(), line.getM_Product_ID(), null);
					result = "La nueva cantidad ingresada para el producto supera la cantidad pendiente.\nProducto : " + 
							 prod.getValue() + " - " + prod.getName();
					
					line.setQtyEntered(line.getuy_qtyentered_original());
					BigDecimal qtyOrdered = MUOMConversion.convertProductFrom (getCtx(), line.getM_Product_ID(), line.getC_UOM_ID(), line.getuy_qtyentered_original());
					if (qtyOrdered == null) qtyOrdered = line.getuy_qtyentered_original();
					line.setQtyOrdered(qtyOrdered);
					line.saveEx(get_TrxName());
					
					break;
				}				
			}
		}
		
		return result;
	}
	/**
	 * 
	 * OpenUp.	issue#527
	 * Descripcion : Se crea para luego ser llamado desde todo error que se peude dar al completar un pedido.
	 * @param Mensaje
	 * @author  Nicolas Garcia
	 * Fecha : 06/05/2011
	 */
	private void sendMessageToSalesRep(String mensaje){
		//Valido cuando voy a mostrar los mensajes
		if ( (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Drafted)) 
				|| (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_InProgress))
				|| (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Invalid))){
			
			String mens ="No se pudo completar el Pedido.\n Error: "+mensaje;			
			MNote note = new MNote(getCtx(), 339,this.getSalesRep_ID(), this.get_Table_ID(),this.getC_Order_ID(),this.toString(),mens, null);
			note.save();
			
		}
		
		
	
	}
	
	//OpenUp Nicolas Garcia 16/06/2011 Metodo para concatenar la descripcion
	public void setDescriptionAdd(String description){
		if(this.getDescription()==null){
			this.setDescription(description);
		}else{
			this.setDescription(description+" | "+this.getDescription());
		}		
	}
	
	/**
	 * 
	 * OpenUp.	issue#801
	 * Descripcion : Valida que el total de las lineas no supere lo producido.
	 * @param OrdHdr
	 * @return Boolean
	 * @author  Nicolas Garcia
	 * Fecha : 29/06/2011
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public boolean controlOrderSalesProduction() {

		// Obtengo lineas
		MOrderLine[] lines = this.getLines();
		MManufOrderLine[] manufLines = null;
		try {
			// Obtengo ManufOrderLines
			manufLines = MManufOrderLine.getManufOrderLines(this.get_ID(), "", get_TrxName());
		} catch (Exception e) {
			this.m_processMsg = "Error al traer lineas intermedias, Pongase en contacto con un administrador";
			return false;
		}

		HashMap<Integer, BigDecimal> disponible = new HashMap<Integer, BigDecimal>();

		// HashMap<String,Boolean> unidadMedida= new HashMap<String,Boolean>();

		ArrayList<Integer> ppOrderIDs = new ArrayList<Integer>();

		// Recorro manufLineOrder para gargar el disponible
		for (int i = 0; i < manufLines.length; i++) {

			MManufOrderLine manufLine = manufLines[i];
			int productID = manufLine.getM_Product_ID();

			if (!(disponible.containsKey(productID))) {

				// Acumulo
				disponible.put(productID, MPPOrder.getQtyAvailableToSale(manufLine.getPP_Order_ID()));

				// Guardo el id de la orden como usado
				ppOrderIDs.add(manufLine.getPP_Order_ID());

			} else {
				// Si se repite el producto pero no la orden
				if (!ppOrderIDs.contains(manufLine.getPP_Order_ID())) {

					disponible.put(productID, disponible.get(productID).add(MPPOrder.getQtyAvailableToSale(manufLine.getPP_Order_ID())));

					// Guardo el id de la orden como usado
					ppOrderIDs.add(manufLine.getPP_Order_ID());
				}

			}
		}

		// Recorro las lineas C_orderLine para validar

		for (int i = 0; i < lines.length; i++) {

			MOrderLine line = lines[i];
			int productID = line.getM_Product_ID();
			// Si la cantidad ordenada es mayor a la disponible para ventas

			if (line.getQtyOrdered().compareTo(disponible.get(line.getM_Product_ID())) > 0) {

				// Solo creado para enviar el mensaje
				MProduct product = new MProduct(getCtx(), productID, null);

				this.m_processMsg = "Solo esta disponible " + disponible.get(productID) + " unidades del producto " + product.getValue()
						+ " en la orden de proceso\n Posiblemente esta orden ya fue agregada a un pedido de venta";
				throw new AdempiereException(this.m_processMsg);

			} else {

				// Resto
				disponible.put(productID, disponible.get(productID).subtract(line.getQtyOrdered()));
			}
		}// for

		return true;
	}
	
	/**
	 * 
	 * OpenUp. issue #780	
	 * Descripcion : Método que cierra la órden si todas sus líneas fueron marcadas con el check
	 * Cierra Línea = Y en la ventana de recepción de material ó si ya no hay cantidad pendiente de recepción
	 * @author  Nicolas Sarlabos 
	 * Fecha : 26/08/2011
	 */
	public void checkCloseOrder(){
		//OpenUp Nicolas Sarlabos issue #916 03/11/2011 
		boolean close = true;
		BigDecimal qty = Env.ZERO;
		MOrderLine[] lines = this.getLines();
			
		for (int i = 0; i < lines.length; i++){
						
				if(!lines[i].isuy_polineclosed()&& lines[i].getQtyReserved().compareTo(Env.ZERO)>0){
					close=false;
				}
								
				//OpenUp SBT Issue #5028 11/11/2015 Se debe sumar si corresponde la cantidad binificada para realizar la comparacion
				//sino se suma 0 y queda igual
				BigDecimal binifica = Env.ZERO;
				if(lines[i].get_ValueAsBoolean("Uy_BonificaSimple")){
					binifica = ((lines[i].get_Value("QtyReward")!=null)? (BigDecimal)lines[i].get_Value("QtyReward"):Env.ZERO);
				}
				//OpenUp Nicolas Sarlabos issue #901 17/10/2011
				if(lines[i].getQtyDelivered().compareTo((lines[i].getQtyOrdered().add(binifica)))>0){
					MOrderLine line = lines[i];
					line.setQtyReserved(Env.ZERO);
					line.saveEx();
				}
					//fin OpenUp Nicolas Sarlabos issue #901 17/10/2011	
				
					
				
				
				qty = qty.add(lines[i].getQtyReserved());  //acumula cantidad pendiente de recepción
					
		}
					
		if(close || qty.compareTo(Env.ZERO)==0){  
			//fin OpenUp Nicolas Sarlabos issue #916 03/11/2011	
			if(!this.processIt(DocumentEngine.ACTION_Close)){
				throw new AdempiereException("Imposible cerrar la órden: " + this.getDocumentNo());
			}
			this.saveEx();
					
		}
		
	}
	
	/**
	 * OpenUp. issue #897	
	 * Descripcion : Determina si la orden tiene recepcion asociada
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 12/10/2011
	 */
	public String haveReceptions(){
		String res = "";
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
			
		try{
			sql = " SELECT c_orderline_id,documentno,docstatus " +
				  " FROM m_inoutline line " +
				  " inner join m_inout hdr on line.m_inout_id = hdr.m_inout_id " +
			      " WHERE hdr.docstatus != 'VO' " +
			      " AND c_orderline_id IN " +
			      " (SELECT c_orderline_id FROM c_orderline where c_order_id=?)";
	
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.get_ID());
	
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				res += "No se puede Anular la orden por estar asociada a la recepcion Nº " + 
				rs.getString("documentno") + " en estado " + rs.getString("docstatus");
			}
			
		
		}catch (Exception e){
			log.log(Level.SEVERE, sql, e);
		}finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return res;
	}

	/**
	 * 
	 * OpenUp.	issue #922
	 * Descripcion : Metodo que permite la modificacion del vendedor segun el rol actual
	 * @author  Nicolas Sarlabos 
	 * Fecha : 01/12/2011
	 */
	
	@SuppressWarnings("unused")
	private void checkSalesRep(){
		
		//se obtiene el Id del vendedor del cliente actual
		String sql = "SELECT salesrep_id FROM c_bpartner WHERE c_bpartner_id=" + this.getC_BPartner_ID();
		int id = DB.getSQLValue(get_TrxName(), sql);
		//se compara ID obtenido con el actualmente ingresado
		if(this.getSalesRep_ID()>0 && id>0 && this.getSalesRep_ID()!=id){
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql2 = "";
			
			try { 
						
				sql2 = "SELECT isactive,isreadwrite" +
				       " FROM ad_window_access" + 
                       " WHERE ad_role_id =" + Env.getAD_Role_ID(getCtx())+
                       " AND ad_window_id = 1000000";
				
				pstmt = DB.prepareStatement(sql2, null);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					//si alguno de los dos campos es igual a "N" entonces no tiene permiso de modificacion
					if(rs.getString("isactive").equalsIgnoreCase("N") || rs.getString("isreadwrite").equalsIgnoreCase("N"))
						throw new AdempiereException("No es posible modificar el vendedor con su rol actual");
																
				}
				
			} catch(Exception e) {
				throw new AdempiereException("No es posible modificar el vendedor con su rol actual");
				
			} finally{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
				
			}
				
		}
		
	}

	/**
	 * Accion de completar segun modelo de OpenUp.
	 * OpenUp Ltda. Issue # Migracion 
	 * @author Gabriel Vila - 21/02/2012
	 * @see
	 * @return
	 */
	private boolean completeItOpenUp(){
		
		// OpenUp. Gabriel Vila. 11/03/2011. Issue #424.
		// Si estoy en DocStatus = IP (Un pedido reactivado para ser modificado), hago las validaciones necesarias.
		if (isSOTrx() && DOCSTATUS_InProgress.equals(getDocStatus())){
			m_processMsg = this.validateReactivatedOrder();
			if (m_processMsg != null){				
				this.sendMessageToSalesRep(m_processMsg.toString());				
				return false;
			}
		}
		// Fin OpenUp.
		
		// Before Complete
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null){
			this.sendMessageToSalesRep(m_processMsg.toString());
			return false;
		}
		
		// OpenUp. Gabriel Vila. 27/08/2014. Issue #2631.
		// En algunos casos cuando es venta de servicios quiero generar una factura en borrador asociada a 
		// esta orden de venta al momento de completarse la misma. No paso por entregas.
		// Si estoy en ventas
		if (this.isSOTrx()){

			// Si tengo seteado generar factura en borrador automatica asociada a esta orden de venta
			if (MSysConfig.getBooleanValue("UY_SALES_ORDER_GENERATE_INVOICE", false, this.getAD_Client_ID())){
				if (this.get_ValueAsInt("C_DocType_ID_To") > 0){
					MDocType doc = new MDocType(getCtx(), this.get_ValueAsInt("C_DocType_ID_To"), null);
					this.generateInvoice(doc);
				}
			}
		}
		// Fin OpenUp. Issue #2631
		
		getLines(true,null);
		
		// Proceso lineas
		for (int i = 0; i < m_lines.length; i++)
		{
			MOrderLine line = m_lines[i];
			//	Check/set WH/Org
			if (this.getM_Warehouse_ID() != 0)	//	enforce WH
			{
				if (this.getM_Warehouse_ID() != line.getM_Warehouse_ID())
					line.setM_Warehouse_ID(this.getM_Warehouse_ID());
				if (this.getAD_Org_ID() != line.getAD_Org_ID())
					line.setAD_Org_ID(this.getAD_Org_ID());
			}

			// Si las ordenes de venta deben reservar stock al completarse
			if ((this.isSOTrx()) && (MSysConfig.getBooleanValue("UY_SALEORDER_RESERVE_STOCK", false, this.getAD_Client_ID()))){
				BigDecimal target = line.getQtyOrdered(); 
				BigDecimal difference = target.subtract(line.getQtyReserved()).subtract(line.getQtyInvoiced()); 
				
				//	Check Product - Stocked and Item
				MProduct product = line.getProduct();
				if (product != null) 
				{
					if (product.isStocked())
					{
						line.setQtyReserved(line.getQtyReserved().add(difference));
						line.saveEx(get_TrxName());
					}
				}
			}
		}
		
		// OpenUp Nicolas Garcia 29/06/2011 Issue #752 issue#811
		// Caso Ordenes de "PEDIDO DE VENTA PRODUCCION"
		MDocType docTypeTarget = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(), null);
		if (docTypeTarget.getDocBaseType().equals("SOO") && docTypeTarget.getDocSubTypeSO() != null) {
			if (docTypeTarget.getDocSubTypeSO().equals("PP")) {
				// Actualizado issue #801
				if (!this.controlOrderSalesProduction()) {
					return false;
				}
			}
		}
		// fin OpenUp Nicolas Garcia 29/06/2011
		
		//	After Complete
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null){
			this.sendMessageToSalesRep(m_processMsg.toString());
			return false;
		}
		
		//OpenUp Nicolas Sarlabos 3/10/2012, seteo la regla de entrega en Forzado y la regla de factura en Inmediato
		MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(),get_TrxName());
		if(doc.getDocBaseType().equalsIgnoreCase("SOO") && doc.isSOTrx() && doc.getDocSubTypeSO()!=null){
			if(doc.getDocSubTypeSO().equalsIgnoreCase("SO")){
				this.setDeliveryRule("F");
				this.setInvoiceRule("D");
			}
		}
		//Fin OpenUp Nicolas Sarlabos 3/10/2012

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		// Si estoy en orden de compra y tengo el sysconfig de envio de email automatico a proveeedor.
		if (!this.isSOTrx()){
			if (MSysConfig.getBooleanValue("UY_PO_SEND_EMAIL_AUTO", false, this.getAD_Client_ID())){
				this.sendEmailToVendor(false);
			}
		}
		
		setProcessed(true);
		setDocStatus(DocumentEngine.STATUS_Completed);
		setDocAction(DocumentEngine.ACTION_None);

		return true;
	}

	/***
	 * Genera factura de venta en borrador para esta orden.
	 * OpenUp Ltda. Issue #2631 
	 * @author Gabriel Vila - 27/08/2014
	 * @see
	 * @param doc
	 */
	private void generateInvoice(MDocType doc) {

		try{
			// Cabezal
			MInvoice invoice = new MInvoice(this, doc.get_ID(), this.getDateOrdered());
			MBPartner partner = (MBPartner)this.getC_BPartner();

			invoice.setC_DocType_ID(doc.get_ID());
			invoice.setC_DocTypeTarget_ID(doc.get_ID());
			invoice.setDescription(this.getDescription());
			invoice.setPOReference(this.getPOReference());
			invoice.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			invoice.setM_Warehouse_ID(this.getM_Warehouse_ID());
			invoice.setpaymentruletype(this.getpaymentruletype());
			invoice.setC_PaymentTerm_ID(this.getC_PaymentTerm_ID());
			
			if (this.isSOTrx() && this.getSalesRep_ID() <= 0) invoice.setSalesRep_ID(partner.getSalesRep_ID());

			invoice.setC_Order_ID(this.get_ID());
			invoice.setC_Currency_ID(this.getC_Currency_ID());
			if (this.getM_PriceList_ID() > 0) invoice.setM_PriceList_ID(this.getM_PriceList_ID());

			MPaymentTerm pterm = (MPaymentTerm)invoice.getC_PaymentTerm();
			MPaymentRule prule = (MPaymentRule)pterm.getUY_PaymentRule();
			if ((prule != null) && (prule.get_ID() > 0)){
				invoice.setpaymentruletype(prule.getpaymentruletype());
				invoice.setUY_PaymentRule_ID(prule.get_ID());
			}
			
			invoice.saveEx();
			
			
			// Lineas 
			MOrderLine[] lines = this.getLines();
			
			
			for (int i = 0; i < lines.length; i++) {

				MOrderLine line = lines[i];
				
				if (line.getQtyEntered().compareTo(Env.ZERO) > 0){
					MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
					
					invoiceLine.setLine(line.getLine());																		
					invoiceLine.setIsDescription(line.isDescription());
					invoiceLine.setDescription(line.getDescription());
					invoiceLine.setM_Product_ID(line.getM_Product_ID());								
					invoiceLine.setC_UOM_ID(line.getC_UOM_ID());										
					invoiceLine.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
					invoiceLine.setQtyEntered(line.getQtyEntered());																	
					//invoiceLine.setQtyInvoiced(line.getQtyEntered());
					BigDecimal flatDiscount = Env.ZERO;
										
					invoiceLine.setC_OrderLine_ID(line.get_ID());
					invoiceLine.setPriceActual(line.getPriceActual());
					invoiceLine.setPriceEntered(line.getPriceEntered());
					invoiceLine.setPriceLimit(line.getPriceLimit());
					invoiceLine.setPriceList(line.getPriceList());
					flatDiscount = line.getFlatDiscount();
					if (flatDiscount == null) flatDiscount = Env.ZERO;
					
					invoiceLine.setFlatDiscount(flatDiscount);					
					invoiceLine.setTax();				
					invoiceLine.setLineNetAmt();
					invoiceLine.setTaxAmt();					
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
			
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}


	/**
	 * Accion de preparar segun modelo de OpenUp.
	 * OpenUp Ltda. Issue # migracion
	 * @author Gabriel Vila - 21/02/2012
	 * @see
	 * @return
	 */
	public String prepareItOpenUp()
	{
		// Before Prepare
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);

		if (m_processMsg != null){			
			this.sendMessageToSalesRep(m_processMsg.toString());			
			return DocAction.STATUS_Invalid;
		}
		
		MDocType dt = MDocType.get(getCtx(), getC_DocTypeTarget_ID());

		// OpenUP-Sylvie 06102014 
		if((MPeriod.getC_Calendar_ID(getCtx(), getAD_Org_ID()))>0){

			// Std Period open?
			if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
			{
				m_processMsg = "@PeriodClosed@";
				this.sendMessageToSalesRep(m_processMsg.toString());
				return DocAction.STATUS_Invalid;
			}
			

		}
		
		//	Lines
		MOrderLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			this.sendMessageToSalesRep(m_processMsg.toString());
			return DocAction.STATUS_Invalid;
		}
		

		
		//	Convert DocType to Target
		if (getC_DocType_ID() != getC_DocTypeTarget_ID() )
		{
			//	New or in Progress/Invalid
			if (DOCSTATUS_Drafted.equals(getDocStatus()) 
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| getC_DocType_ID() == 0)
			{
				setC_DocType_ID(getC_DocTypeTarget_ID());
			}
			else	//	convert only if offer
			{
				if (dt.isOffer())
					setC_DocType_ID(getC_DocTypeTarget_ID());
				else
				{
					m_processMsg = "@CannotChangeDocType@";
					this.sendMessageToSalesRep(m_processMsg.toString());
					return DocAction.STATUS_Invalid;
				}
			}
		}	//	convert DocType

		if (!calculateTaxTotal())
		{
			m_processMsg = "Error calculating tax";
			this.sendMessageToSalesRep(m_processMsg.toString());
			return DocAction.STATUS_Invalid;
		}
		
		//	Credit Check
		if (isSOTrx())
		{
			// OpenUp. Gabriel Vila. 07/02/2011. Issue #282.
			// Verificacion de credito segun modelo de openup.
			
			// Si este pedido ya fue aprobado manualmente no hago nada
			if (!this.isuy_credit_approved()){
				MBPartner bp = new MBPartner (getCtx(), getC_BPartner_ID(), get_TrxName());
				if (MBPartner.UY_CREDIT_ACTION_Suspendido.equalsIgnoreCase(bp.getuy_credit_action())){				
					m_processMsg = "El Cliente de este Pedido de Venta se encuentra crediticiamente SUSPENDIDO.";
				}
				else if (MBPartner.UY_CREDIT_ACTION_Verificar.equalsIgnoreCase(bp.getuy_credit_action())){
					m_processMsg = this.validateCredit();
				}
				if (m_processMsg != null){
					this.setIsApproved(false);
					this.setIsCreditApproved(false);
					this.setuy_credit_approved(false);
					this.setuy_credit_message(m_processMsg);
					this.sendMessageToSalesRep(m_processMsg.toString());
					return DocAction.STATUS_Invalid;
				}
				else{
					this.setIsApproved(true);
					this.setIsCreditApproved(true);
					this.setuy_credit_approved(true);
					this.setuy_credit_message("Aprobado Automaticamente");				
				}
				this.saveEx();
			}
			// Fin OpenUp.
		}
		
		// After Prepare
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null){
			this.sendMessageToSalesRep(m_processMsg.toString());
			return DocAction.STATUS_Invalid;
		}
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}


	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}


	/***
	 * OpenUp. Gabriel Vila. 03/02/2013. Issue #198
	 */
	@Override
	public int getDynamicWFResponsibleID(MWFNode node) {

		int value = 0;

		try{
		
			if ((node == null) || (node.getColumn() == null)) return value;
			
			//MPOSection section = new MPOSection(getCtx(), this.getUY_POSection_ID(), null);
			
			if (node.getColumn().getName().equalsIgnoreCase(COLUMNNAME_IsApproved1)){

				return 1000005; // Por ahora Sergio Pereyra
						
			}
			else if (node.getColumn().getName().equalsIgnoreCase(COLUMNNAME_IsApproved2)){
				
				return 1000005; // Por ahora Sergio Pereyra

			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	
		return value;
	}


	/***
	 * OpenUp. Gabriel Vila. 03/02/2013. Issue #198
	 */
	@Override
	public void setApprovalInfo(int AD_WF_Responsible_ID, String textMsg) {
		this.setApprovalUser_ID(AD_WF_Responsible_ID);
		this.setApprovalDescription(textMsg);
	}

	/***
	 * OpenUp. Gabriel Vila. 03/02/2013. Issue #198
	 */
	@Override
	public String getWFActivityDescription() {
	
		MQuoteVendor quote = (MQuoteVendor)this.getUY_QuoteVendor();
		if (quote != null){
			return "Cotización a proveedor numero : " + quote.getDocumentNo();	
		}
		else {
			return "";
		}
		

	}


	/***
	 * OpenUp. Gabriel Vila. 03/02/2013. Issue #198
	 */
	@Override
	public String getWFActivityHelp() {
		return this.getHelp();
	}


	/***
	 * OpenUp. Gabriel Vila. 03/02/2013. Issue #198
	 */
	@Override
	public boolean IsParcialApproved() {
		return this.isParcialApproved;
	}

	/***
	 * Metodo que se ejecuta en la accion Solicitar.
	 * OpenUp Ltda. Issue #198 
	 * @author Gabriel Vila - 04/02/2013.
	 * @see
	 * @return
	 */
	public boolean requestIt() {

		try{

			// Si NO es orden de Compra salgo
			if (this.isSOTrx()) return true;

			//OpenUp. Nicolas Sarlabos. 11/12/2013. #1588. Al solicitarse una order de compra directa seteo atributos.
			MDocType doc = new MDocType(Env.getCtx(), this.getC_DocTypeTarget_ID(), null);

			if (doc.getValue() != null){
				if(doc.getValue().equalsIgnoreCase("podirect")){

					this.setDateTrx(new Timestamp(System.currentTimeMillis()));	//seteo fecha solicitada
					this.setDocStatus(DocumentEngine.STATUS_Requested);
					this.setDocAction(DocAction.ACTION_Approve);

				}else if (doc.getValue().equalsIgnoreCase("poquote")){


					List<MOrderSection> ordsections = this.getPOParcials();
					if (ordsections.size() > 0){

						// Requiero autorizaciones 1 seguro
						this.setIsApproved(false);
						this.setNeedApprove1(true);
						this.setNeedApprove2(false);
						this.setIsApproved1(false);
					
						// Estado documento
						this.setDocAction(DOCACTION_Approve);

						
						MPOPolicy policy = MPOPolicy.forValue(getCtx(), "100", null);

						// Actualizo informacion de estado de aprobacion
						this.setApprovalDate(null);
						this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionNivel2);
						this.setApprovalDescription(null);
						this.setApprovalUser_ID(policy.getApprovalUser_ID());

						
						// Tracking
						MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
						track.setDateTrx(this.getDateOrdered());
						track.setC_Order_ID(this.get_ID());
						track.setAD_User_ID(this.getAD_User_ID());
						track.setDescription("Orden de Compra creada");
						track.setobservaciones("Pendiente de Autorización Nivel 2");
						track.saveEx();

						// Ingreso en bandeja de entrada
						Timestamp today = new Timestamp(System.currentTimeMillis());
						MRType rType = MRType.forValue(getCtx(), "compras", null);
						if ((rType == null) || (rType.get_ID() <= 0)){
							throw new AdempiereException("Falta parametrizar el Tipo de Incidencia : compras");
						}
						MRCause cause = MRCause.forTypeAndValue(getCtx(), rType.get_ID(), "ordcompra", null);
						if ((cause == null) || (cause.get_ID() <= 0)){
							throw new AdempiereException("Falta parametrizar el Tema de Inciencia : ordcompra");
						}
						
						MRInbox inbox = new MRInbox(getCtx(),0, get_TrxName());						
						inbox.setUY_R_Type_ID(rType.get_ID());
						inbox.setUY_R_Cause_ID(cause.get_ID());
						inbox.setSign();
						inbox.setDateTrx(today);
						inbox.setDateAssign(today);
						inbox.setAD_User_ID(this.getAD_User_ID());
						inbox.setStatusReclamo(X_UY_R_Inbox.STATUSRECLAMO_PendienteAprobacionNivel2);
						inbox.setPriority(cause.getPriorityBase());
						inbox.setPriorityManual(this.getPriorityRule());
						inbox.setAssignTo_ID(this.getApprovalUser_ID());
						inbox.setDueDate(TimeUtil.addDays(inbox.getDateTrx(), cause.getDeadLine()));
						inbox.setAD_Table_ID(I_C_Order.Table_ID);
						inbox.setRecord_ID(this.get_ID());
						inbox.setC_DocType_ID(this.getC_DocTypeTarget_ID());
						inbox.setUY_R_PtoResolucion_ID(cause.getUY_R_PtoResolucion_ID());
						inbox.setMediumTerm(cause.getMediumTerm());
						inbox.saveEx();

						// Envio email a usuario responsable
						MUser user = new MUser(getCtx(), this.getApprovalUser_ID(), null);
						String subject = "Aprobación Pendiente de Orden de Compra.";
						String message = "Estimado/a: " + user.getDescription() + "\n\n";
						message +="La Orden de Compra Número : " + this.getDocumentNo() + " esta en espera de su Aprobación.\n\n";
						message +="Atentamente.";
						MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
						
						EMail email = new EMail (client, "compras@italcred.com.uy", user.getEMailUser(), subject, message.toString(), false);
						email.createAuthenticator("compras@italcred.com.uy", "c0MPras");
						
						if (email != null){
							String msg = email.send();
							if (EMail.SENT_OK.equals (msg)) log.info("Sent EMail " + subject + " to " + user.getEMailUser());
							else log.warning("Could NOT Send Email: " + subject + " to " + user.getEMailUser() + ": " + msg);
						}
					}
					else{
						// Si no hay ordenes parciales, no necesito aprobacion de nada.
						this.setNeedApprove1(false);
						this.setIsApproved1(true);
						this.setNeedApprove2(false);
						this.setIsApproved2(true);
						this.setIsApproved(true);
					}

					// Refresco atributos
					//this.setDocAction(DocAction.ACTION_None);
					this.setDocStatus(DocumentEngine.STATUS_Requested);
				}
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return true;

		/*
		try{
		
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			
			// Obtengo y me seteo el rango de politica de compra segun mi total
			MPOSection section = new MPOSection(getCtx(), this.getUY_POSection_ID(), null);
			MPOPolicyRange rango = section.getPolicyRange(this.getDateOrdered(), this.getTotalLines(), 
								schema.getC_Currency_ID(), null);
			if (rango == null){
				throw new AdempiereException("No se pudo obtener rango de politica de compra segun Total de la Orden.");
			}
			this.setUY_POPolicyRange_ID(rango.get_ID());

			// Seteo si es necesaria una segunda aprobacion para esta solicitud
			this.setNeedApprove2(section.needSecondApprove(this.getUY_POPolicyRange_ID()));
			
			// Actualizo informacion de estado de aprobacion
			this.setApprovalDate(new Timestamp(System.currentTimeMillis()));
			this.setApprovalStatus(APPROVALSTATUS_PendienteAprobacionGerenteDeArea);
			this.setApprovalUser_ID(section.getWFResponsible(X_UY_POPolicyUser.NIVEL_Supervisor, this.getUY_POPolicyRange_ID()));
			this.setApprovalDescription(null);
			
			// Tracking
			MPurchaseTracking track = new MPurchaseTracking(getCtx(), 0, get_TrxName());
			track.setC_DocType_ID(this.getC_DocType_ID());
			track.setDocumentNo(this.getDocumentNo());
			track.setDateTrx(this.getDateOrdered());
			track.setDocStatus(DocumentEngine.STATUS_Requested);
			track.setC_Order_ID(this.get_ID());
			track.setAD_User_ID(this.getAD_User_ID());
			track.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		return true;
		
		*/
		
		
	}


	/***
	 * Obtiene y retorna lista de ordenes de compra parciales asociadas a 
	 * esta orden de compra.
	 * OpenUp Ltda. Issue #381 
	 * @author Gabriel Vila - 22/02/2013
	 * @see
	 * @return
	 */
	private List<MOrderSection> getPOParcials() {

		String whereClause = "uy_ordersection.c_order_id=" + this.get_ID();
		
		List<MOrderSection> values = new Query(getCtx(), I_UY_OrderSection.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
		
	}


	/***
	 * Genera ordenes de compra parciales para su aprobacion por sector.
	 * Se utiliza como base de informacion las lineas de sectores asociadas
	 * a la solicitud de cotizacion.
	 * OpenUp Ltda. Issue #381 
	 * @author Gabriel Vila - 22/02/2013
	 * @see
	 */
	public void generatePOParcials() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			MQuoteVendor quote = (MQuoteVendor)this.getUY_QuoteVendor();
			MRFQRequisition rfqreq = (MRFQRequisition)quote.getUY_RFQ_Requisition();
			
			sql =" select uy_posection_id, m_product_id, c_uom_id, " +
				 " sum(qtyrequired) as qty " +
				 " from uy_rfq_requisitionsection " +
				 " where uy_rfq_requisition_id =? " +
				 " group by uy_posection_id, m_product_id, c_uom_id " +
				 " order by uy_posection_id, m_product_id";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, rfqreq.get_ID());
			
			rs = pstmt.executeQuery ();
		
			// Corte por section
			int uyPOSectionID = 0;
			BigDecimal amtTotal = Env.ZERO;
			MOrderSection ordsect = null;
			
			while (rs.next()){
				
				// Si cambia el sector de compra
				if (rs.getInt("uy_posection_id") != uyPOSectionID){
					// Si tengo cabezal de orden por sector, lo proceso
					if (ordsect != null){
						ordsect.setTotalLines(amtTotal);
						ordsect.saveEx();
						//ordsect.executeDocActionProcess();
						if (!ordsect.processIt(ACTION_Request)){
							throw new AdempiereException(ordsect.getProcessMsg());
						}
					}

					uyPOSectionID = rs.getInt("uy_posection_id");
					amtTotal = Env.ZERO;
					ordsect = new MOrderSection(getCtx(), 0, get_TrxName());
					ordsect.setDefaultDocType();
					ordsect.setDateTrx(this.getDateOrdered());
					ordsect.setDescription(this.getDescription());
					ordsect.setC_Order_ID(this.get_ID());
					ordsect.setUY_POSection_ID(uyPOSectionID);
					ordsect.setUY_POPolicy_ID(quote.getUY_POPolicy_ID());
					ordsect.setUY_QuoteVendor_ID(quote.get_ID());
					ordsect.setC_BPartner_ID(this.getC_BPartner_ID());
					ordsect.setC_Currency_ID(this.getC_Currency_ID());
					ordsect.setC_PaymentTerm_ID(this.getC_PaymentTerm_ID());
					ordsect.setPriorityRule(this.getPriorityRule());
					ordsect.setIsApproved(false);
					ordsect.setDatePromised(this.getDatePromised());
					ordsect.setTotalLines(Env.ZERO);
					ordsect.setDocStatus(STATUS_Drafted);
					ordsect.setDocAction(ACTION_Request);
					ordsect.setProcessing(false);
					ordsect.setProcessed(false);
					ordsect.setIsApproved1(false);
					ordsect.setIsApproved2(true);
					ordsect.setNeedApprove1(true);
					ordsect.setNeedApprove2(false);
					ordsect.saveEx();
				}
				
				// Nueva linea parcial para producto
				MOrderSectionLine osline = new MOrderSectionLine(getCtx(), 0, get_TrxName());
				osline.setUY_OrderSection_ID(ordsect.get_ID());
				osline.setM_Product_ID(rs.getInt("m_product_id"));
				osline.setC_UOM_ID(rs.getInt("c_uom_id"));
				osline.setQty(rs.getBigDecimal("qty"));
				
				MOrderLine line = this.getLineForProdUOM(osline.getM_Product_ID(), osline.getC_UOM_ID());
				if (line == null){
					throw new AdempiereException("No se pudo obtener linea de compra para producto - unidad de medida.");
				}
				osline.setPrice(line.getPriceEntered());
				osline.setDiscount(Env.ZERO);
				osline.setLineNetAmt(osline.getQty().multiply(line.getPriceEntered()).setScale(2, RoundingMode.HALF_UP));
				osline.saveEx();
				
				amtTotal = amtTotal.add(osline.getLineNetAmt());
			}

			// Ultima linea
			if (ordsect != null){
				ordsect.setTotalLines(amtTotal);
				ordsect.saveEx();
				//ordsect.executeDocActionProcess();
				if (!ordsect.processIt(ACTION_Request)){
					throw new AdempiereException(ordsect.getProcessMsg());
				}
			}

			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}

	
	/***
	 * Obtiene y retorna linea de compra para producto-unidad de medida recibidos.
	 * OpenUp Ltda. Issue #381 
	 * @author Gabriel Vila - 22/02/2013
	 * @see
	 * @param mProductID
	 * @param cUOMID
	 * @return
	 */
	private MOrderLine getLineForProdUOM(int mProductID, int cUOMID){
		
		String whereClause = "c_orderline.c_order_id=" + this.get_ID() + 
					" and m_product_id =" + mProductID +
					" and c_uom_id = " + cUOMID;
		
		MOrderLine value = new Query(getCtx(), I_C_OrderLine.Table_Name, whereClause, get_TrxName())
		.first();
		
		return value;
	}

	
	/***
	 * Si quiero solicitar una orden de compra desde el codigo, tengo que hacerlo de tal manera que
	 * se ejecute en el flujo de trabajo. Eso lo logro poniendo la accion que quiero hacer (docstatus='RQ') y 
	 * luego invocando el proceso del boton DocAction que contiene el flujo de trabajo.
	 * OpenUp Ltda. Issue #381 
	 * @author Gabriel Vila - 26/02/2013
	 * @see
	 */
	public void executeDocActionProcess(){

		int adProcessID = MProcess.getProcess_ID("C_Order Process", null);

		MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
		instance.saveEx();

		ProcessInfo pi = new ProcessInfo("PurchaseOrder", adProcessID);
		pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());
		
		pi.setRecord_ID(this.get_ID());

		/*
		String trxNameAux = Trx.createTrxName();
		Trx trxName = Trx.get(trxNameAux, true);
		ProcessCtl worker = new ProcessCtl(null, 0, pi, trxName);
		*/

		ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
		
		worker.start();

	}
	
	/***
	 * Verifica el estado de aprobacion de sus ordenes parciales. Si todas estan aprobadas, entonces
	 * esta orden quedara aprobada en su primer nivel.
	 * OpenUp Ltda. Issue #381 
	 * @author Gabriel Vila - 26/02/2013
	 * @see
	 */
	public void checkPOParcialApproved(){
		
		try{
			
			boolean aprobado = true;
			boolean needSecondApprove = false;
			
			List<MOrderSection> poparciales = this.getPOParcials();
			for (MOrderSection poparcial: poparciales){
				if (!poparcial.isApproved()){
					aprobado = false;
				}
				if (poparcial.needSecondApprove())
					needSecondApprove = true;
			}
			
			// Si todas las parciales estan aprobadas
			if (aprobado){
				if (needSecondApprove){
					this.setIsApproved(false);
					this.setIsApproved1(true);
					this.setNeedApprove2(true);
					this.setIsApproved2(false);
					this.setDocStatus(STATUS_Drafted);
					this.setDocAction(DOCACTION_Request);
					this.saveEx();
					if (!this.processIt(DOCACTION_Request)){
						throw new AdempiereException(this.m_processMsg);
					}
				}
				else{
					this.setIsApproved(true);
					this.setIsApproved1(true);
					this.setIsApproved2(true);
					this.setApprovalStatus(X_C_Order.APPROVALSTATUS_AprobadoNivel1);
					this.setDocStatus(STATUS_Approved);
					this.setDocAction(DOCACTION_Complete);					
					this.saveEx();
					if (!this.processIt(ACTION_Complete)){
						throw new AdempiereException(this.m_processMsg);
					}
				}
				
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}


	@Override
	public void processAutomaticApproval() {
		
	}


	@Override
	public void processAutomaticComplete() {
		// TODO Auto-generated method stub
		
	}


	/***
	 * Envia email al proveedor al momento de completar esta orden de compra.
	 * OpenUp Ltda. Issue #583 
	 * @author Gabriel Vila - 28/05/2014
	 * @see
	 */
	public void sendEmailToVendor(boolean html){

		try{
			
			String quoteNo = "";
			if (this.getUY_QuoteVendor_ID() > 0){
				quoteNo = ((MQuoteVendor)this.getUY_QuoteVendor()).getDocumentNo();
			}			
			
			MBPartner partner = (MBPartner)this.getC_BPartner();
			
			// Si el proveedor no tiene direccion de email asociada, no hago nada.
			if (partner.get_ValueAsString("EMail") == null) return;
			if (partner.get_ValueAsString("EMail").trim().equalsIgnoreCase("")) return;
			
			String emailAddress = partner.get_ValueAsString("EMail").trim();
			
			if (quoteNo == null) quoteNo ="";
			
			MClient client = MClient.get(getCtx(), this.getAD_Client_ID());
			
			String subject = "Italcred. Adjudicación de Compra.";
			StringBuilder message = new StringBuilder();

			message.append("Estimado Proveedor, " + "\n\n\n");
			
			message.append("Italcred tiene el agrado de informarle que se le ha adjudicado la compra correspondiente a la solicitud de cotización Nº " + quoteNo + "\n");
			message.append("Adjunto encontrara la Orden de Compra en la que se establece las condiciones de la operación." + "\n");
			message.append("Por favor adjuntar la Orden de Compra a la factura."+ "\n\n\n");

			message.append("Desde ya muchas gracias.");
			
			EMail email = new EMail (client, "compras@italcred.com.uy", emailAddress, subject, message.toString(), html);
			email.createAuthenticator("compras@italcred.com.uy", "c0MPras");
			
			// Obtengo y envio pdf de la orden de compra como archivo adjunto a este email.
			File pdfOC = this.getPDFOC();
			email.addAttachment(pdfOC);
			
			if (email != null){
				String msg = email.send();
				if (EMail.SENT_OK.equals (msg))
				{
					log.info("Sent EMail " + subject + " to " + emailAddress);
				}
				else
				{
					log.warning("Could NOT Send Email: " + subject 
						+ " to " + emailAddress + ": " + msg);
				}
			}
			
		}

		catch (Exception e){
			log.warning("Could NOT Send Email");
		}
	}

	
	/***
	 * Obtiene y retorna PDF para esta orden de compra. Para ello se ejecuta un proceso para obtener dicho PDF. 
	 * OpenUp Ltda. Issue #583 
	 * @author Gabriel Vila - 17/05/2013
	 * @see
	 * @return
	 */
	private File getPDFOC() {

		try{

			Env.getCtx().put("C_Order_ID", this.get_ID());					

			
			MPInstance instance = new MPInstance(Env.getCtx(), 1000338, this.get_ID());
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo ("PrintCheck1", 1000338);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("C_Order_ID", this.get_ID());
			para.saveEx();
			
			ReportStarter starter = new ReportStarter();
			//starter.startProcess(getCtx(), pi, Trx.get(this.get_TrxName(), false));
			starter.startProcess(getCtx(), pi, null);
			
			//ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
			//worker.start();     
			
			java.lang.Thread.sleep(3000);

			return pi.getPDFReport();
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

/**
 * 
 * OpenUp Ltda. Issue # 2816
 * @author Leonardo Boccone - 02/09/2014
 * @see Devuelvo la Orden segun UY_MB_Orden para cargar en las lineas 
 * @param ctx
 * @param uy_MB_Order_ID
 * @param get_TrxName
 * @return
 */
	
	public static MOrder getforMBOrder(Properties ctx, int uy_MB_Order_ID,String get_TrxName) {
		
		String whereClause = "c_order.uy_mb_order_id=" + uy_MB_Order_ID;
				
	
		MOrder o = new Query(ctx, I_C_Order.Table_Name, whereClause, get_TrxName).first();
	
		return o;
	}
	
	
	
	
	/***
	 * Retorna todas las ordenes de compra por sector, asociadas a  
	 * esta orden de compra, order by dateTrx desc.
	 * OpenUp Ltda. Issue #3629
	 * @author INes Fernandez - 24/02/2015
	 * @see
	 * @return
	 */
	public List<MOrderSection> getAllPOSection(){
		
		String whereClause= I_UY_OrderSection.COLUMNNAME_C_Order_ID + "=" + get_ID() ;
		
		List<MOrderSection> lines = new Query(getCtx(), I_UY_OrderSection.Table_Name, whereClause, get_TrxName())
		.setOrderBy(I_UY_OrderSection.COLUMNNAME_DateTrx + " desc").list(); 
		return lines;
	}

	
}	//	MOrder
