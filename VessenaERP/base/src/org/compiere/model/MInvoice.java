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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.BPartnerNoAddressException;
import org.adempiere.exceptions.DBException;
import org.compiere.acct.Doc;
import org.compiere.acct.FactLine;
import org.compiere.apps.ADialog;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.openup.beans.InvoiceLineAmtByProdAct;
import org.openup.cfe.CFEManager;
import org.openup.cfe.CFEResponse;
import org.openup.cfe.InterfaceCFEDTO;
import org.openup.dgi.CfeConverter;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.model.I_UY_InvoiceCashPayment;
import org.openup.model.I_UY_Invoice_Flete;
import org.openup.model.I_UY_Invoice_Provision;
import org.openup.model.I_UY_Order_Flete;
import org.openup.model.I_UY_TR_InvoiceFreightAmt;
import org.openup.model.I_UY_TR_InvoiceTire;
import org.openup.model.I_UY_TR_PaymentFlete;
import org.openup.model.MAllocDirectPayment;
import org.openup.model.MAllocation;
import org.openup.model.MAllocationInvoice;
import org.openup.model.MAllocationPayment;
import org.openup.model.MBudget;
import org.openup.model.MBudgetLine;
import org.openup.model.MCFEInvoice;
import org.openup.model.MExportInvTracking;
import org.openup.model.MFFCashOut;
import org.openup.model.MFFCashOutLine;
import org.openup.model.MFFReplenish;
import org.openup.model.MFFReplenishLine;
import org.openup.model.MFFTracking;
import org.openup.model.MInvoiceCashPayment;
import org.openup.model.MInvoiceFlete;
import org.openup.model.MInvoicePrintConfig;
import org.openup.model.MInvoiceProvision;
import org.openup.model.MLinePayment;
import org.openup.model.MManufLine;
import org.openup.model.MManufOrder;
import org.openup.model.MMediosPago;
import org.openup.model.MOrderFlete;
import org.compiere.model.MPriceListVersion;
import org.openup.model.MAllocDirectCreditNote;
import org.openup.model.MCFEDataEnvelope;
import org.openup.model.MProvisionLine;
import org.openup.model.MReservaPedidoHdr;
import org.openup.model.MSUMAccountStatus;
import org.openup.model.MStockStatus;
import org.openup.model.MStockTransaction;
import org.openup.model.MSubCategoriaProducto;
import org.openup.model.MTRBorder;
import org.openup.model.MTRBudget;
import org.openup.model.MTRBudgetDetail;
import org.openup.model.MTRBudgetLine;
import org.openup.model.MTRConfig;
import org.openup.model.MTRConfigCrt;
import org.openup.model.MTRConfigRound;
import org.openup.model.MTRConfigVFlete;
import org.openup.model.MTRCrt;
import org.openup.model.MTRInvoiceFreightAmt;
import org.openup.model.MTRInvoiceTire;
import org.openup.model.MTRMic;
import org.openup.model.MTRMicCont;
import org.openup.model.MTRPaymentFlete;
import org.openup.model.MTRServiceOrder;
import org.openup.model.MTRTire;
import org.openup.model.MTRTransOrder;
import org.openup.model.MTRTrip;
import org.openup.model.MTRTruckRepair;
import org.openup.model.X_UY_AllocDirectCreditNote;
import org.openup.model.X_UY_AllocDirectPayment;
import org.openup.model.X_UY_AllocationInvoice;
import org.openup.model.X_UY_AllocationPayment;
import org.openup.model.X_UY_CFE_Invoice;
import org.openup.model.X_UY_FF_CashOut;
import org.openup.model.X_UY_InvoiceCashPayment;
import org.openup.model.X_UY_Invoice_Flete;
import org.openup.model.X_UY_Invoice_Provision;
import org.openup.model.X_UY_MediosPago;
import org.openup.model.X_UY_Order_Flete;
import org.openup.model.X_UY_PaymentRule;
import org.openup.model.X_UY_TR_InvoiceFreightAmt;
import org.openup.model.X_UY_TR_InvoiceTire;
import org.openup.model.X_UY_TR_PaymentFlete;
import org.openup.model.X_UY_TR_Tire;
import org.openup.util.ChargeEInvoice;
import org.openup.util.Converter;
import org.openup.util.OpenUpUtils;

/**
 * Invoice Model. Please do not set DocStatus and C_DocType_ID directly. They
 * are set in the process() method. Use DocAction and C_DocTypeTarget_ID
 * instead.
 * 
 * @author Jorg Janke
 * @version $Id: MInvoice.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * @see http 
 *      ://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id
 *      =176962 <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http 
 *      ://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id
 *      =176962 Modifications: Added RMA functionality (Ashley Ramdass)
 */
public class MInvoice extends X_C_Invoice implements DocAction, InterfaceCFEDTO {

	// OpenUp. Gabriel Vila. 23/01/2014. Issue #1588
	public boolean LinesCreatedAutoFromPO = false;
	// Fin OpenUp. Issue #1588

	private static final long serialVersionUID = 816227083897031327L;

	/**
	 * Get Payments Of BPartner
	 * 
	 * @param ctx
	 *            context
	 * @param C_BPartner_ID
	 *            id
	 * @param trxName
	 *            transaction
	 * @return array
	 */
	public static MInvoice[] getOfBPartner(Properties ctx, int C_BPartner_ID,
			String trxName) {
		List<MInvoice> list = new Query(ctx, Table_Name,
				COLUMNNAME_C_BPartner_ID + "=?", trxName).setParameters(
				C_BPartner_ID).list();
		return list.toArray(new MInvoice[list.size()]);
	} // getOfBPartner

	/**
	 * Create new Invoice by copying
	 * 
	 * @param from
	 *            invoice
	 * @param dateDoc
	 *            date of the document date
	 * @param acctDate
	 *            original account date
	 * @param C_DocTypeTarget_ID
	 *            target doc type
	 * @param isSOTrx
	 *            sales order
	 * @param counter
	 *            create counter links
	 * @param trxName
	 *            trx
	 * @param setOrder
	 *            set Order links
	 * @return Invoice
	 */
	public static MInvoice copyFrom(MInvoice from, Timestamp dateDoc,
			Timestamp dateAcct, int C_DocTypeTarget_ID, boolean isSOTrx,
			boolean counter, String trxName, boolean setOrder) {
		MInvoice to = new MInvoice(from.getCtx(), 0, trxName);
		PO.copyValues(from, to, from.getAD_Client_ID(), from.getAD_Org_ID());
		to.set_ValueNoCheck("C_Invoice_ID", I_ZERO);
		to.set_ValueNoCheck("DocumentNo", null);
		//
		to.setDocStatus(DOCSTATUS_Drafted); // Draft
		to.setDocAction(DOCACTION_Complete);
		//
		to.setC_DocType_ID(0);
		to.setC_DocTypeTarget_ID(C_DocTypeTarget_ID);
		to.setIsSOTrx(isSOTrx);
		//
		to.setDateInvoiced(dateDoc);
		to.setDateAcct(dateAcct);
		to.setDatePrinted(null);
		to.setIsPrinted(false);
		//
		to.setIsApproved(false);
		to.setC_Payment_ID(0);
		to.setC_CashLine_ID(0);
		to.setIsPaid(false);
		to.setIsInDispute(false);
		//
		// Amounts are updated by trigger when adding lines
		to.setGrandTotal(Env.ZERO);
		to.setTotalLines(Env.ZERO);
		//
		to.setIsTransferred(false);
		to.setPosted(false);
		to.setProcessed(false);
		// [ 1633721 ] Reverse Documents- Processing=Y
		to.setProcessing(false);
		// delete references
		to.setIsSelfService(false);
		if (!setOrder)
			to.setC_Order_ID(0);
		if (counter) {
			to.setRef_Invoice_ID(from.getC_Invoice_ID());
			// Try to find Order link
			if (from.getC_Order_ID() != 0) {
				MOrder peer = new MOrder(from.getCtx(), from.getC_Order_ID(),
						from.get_TrxName());
				if (peer.getRef_Order_ID() != 0)
					to.setC_Order_ID(peer.getRef_Order_ID());
			}
			// Try to find RMA link
			if (from.getM_RMA_ID() != 0) {
				MRMA peer = new MRMA(from.getCtx(), from.getM_RMA_ID(),
						from.get_TrxName());
				if (peer.getRef_RMA_ID() > 0)
					to.setM_RMA_ID(peer.getRef_RMA_ID());
			}
			//
		} else
			to.setRef_Invoice_ID(0);

		to.saveEx(trxName);
		if (counter)
			from.setRef_Invoice_ID(to.getC_Invoice_ID());

		// Lines
		// Check lines exist before copy
		if (from.getLines(true).length > 0) {
			if (to.copyLinesFrom(from, counter, setOrder) == 0)
				throw new IllegalStateException(
						"Could not create Invoice Lines");
		}

		return to;
	}

	/**
	 * @deprecated Create new Invoice by copying
	 * @param from
	 *            invoice
	 * @param dateDoc
	 *            date of the document date
	 * @param C_DocTypeTarget_ID
	 *            target doc type
	 * @param isSOTrx
	 *            sales order
	 * @param counter
	 *            create counter links
	 * @param trxName
	 *            trx
	 * @param setOrder
	 *            set Order links
	 * @return Invoice
	 */
	public static MInvoice copyFrom(MInvoice from, Timestamp dateDoc,
			int C_DocTypeTarget_ID, boolean isSOTrx, boolean counter,
			String trxName, boolean setOrder) {
		MInvoice to = copyFrom(from, dateDoc, dateDoc, C_DocTypeTarget_ID,
				isSOTrx, counter, trxName, setOrder);
		return to;
	} // copyFrom

	/**
	 * Get PDF File Name
	 * 
	 * @param documentDir
	 *            directory
	 * @param C_Invoice_ID
	 *            invoice
	 * @return file name
	 */
	public static String getPDFFileName(String documentDir, int C_Invoice_ID) {
		StringBuffer sb = new StringBuffer(documentDir);
		if (sb.length() == 0)
			sb.append(".");
		if (!sb.toString().endsWith(File.separator))
			sb.append(File.separator);
		sb.append("C_Invoice_ID_").append(C_Invoice_ID).append(".pdf");
		return sb.toString();
	} // getPDFFileName

	/**
	 * Get MInvoice from Cache
	 * 
	 * @param ctx
	 *            context
	 * @param C_Invoice_ID
	 *            id
	 * @return MInvoice
	 */
	public static MInvoice get(Properties ctx, int C_Invoice_ID) {
		Integer key = new Integer(C_Invoice_ID);
		MInvoice retValue = (MInvoice) s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MInvoice(ctx, C_Invoice_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	} // get

	/** Cache */
	private static CCache<Integer, MInvoice> s_cache = new CCache<Integer, MInvoice>(
			"C_Invoice", 20, 2); // 2 minutes

	/**************************************************************************
	 * Invoice Constructor
	 * 
	 * @param ctx
	 *            context
	 * @param C_Invoice_ID
	 *            invoice or 0 for new
	 * @param trxName
	 *            trx name
	 */
	public MInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
		if (C_Invoice_ID == 0) {
			setDocStatus(DOCSTATUS_Drafted); // Draft
			setDocAction(DOCACTION_Complete);
			//
			setPaymentRule(PAYMENTRULE_OnCredit); // Payment Terms

			setDateInvoiced(new Timestamp(System.currentTimeMillis()));
			setDateAcct(new Timestamp(System.currentTimeMillis()));
			//
			setChargeAmt(Env.ZERO);
			setTotalLines(Env.ZERO);
			setGrandTotal(Env.ZERO);
			//
			setIsSOTrx(true);
			setIsTaxIncluded(false);
			setIsApproved(false);
			setIsDiscountPrinted(false);
			setIsPaid(false);
			setSendEMail(false);
			setIsPrinted(false);
			setIsTransferred(false);
			setIsSelfService(false);
			setIsPayScheduleValid(false);
			setIsInDispute(false);
			setPosted(false);
			super.setProcessed(false);
			setProcessing(false);
		}
	} // MInvoice

	/**
	 * Load Constructor
	 * 
	 * @param ctx
	 *            context
	 * @param rs
	 *            result set record
	 * @param trxName
	 *            transaction
	 */
	public MInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	} // MInvoice

	/**
	 * Create Invoice from Order
	 * 
	 * @param order
	 *            order
	 * @param C_DocTypeTarget_ID
	 *            target document type
	 * @param invoiceDate
	 *            date or null
	 */
	public MInvoice(MOrder order, int C_DocTypeTarget_ID, Timestamp invoiceDate) {
		this(order.getCtx(), 0, order.get_TrxName());
		setClientOrg(order);
		setOrder(order); // set base settings
		//
		if (C_DocTypeTarget_ID <= 0) {
			MDocType odt = MDocType
					.get(order.getCtx(), order.getC_DocType_ID());
			if (odt != null) {
				C_DocTypeTarget_ID = odt.getC_DocTypeInvoice_ID();
				if (C_DocTypeTarget_ID <= 0)
					throw new AdempiereException(
							"@NotFound@ @C_DocTypeInvoice_ID@ - @C_DocType_ID@:"
									+ odt.get_Translation(MDocType.COLUMNNAME_Name));
			}
		}
		setC_DocTypeTarget_ID(C_DocTypeTarget_ID);
		if (invoiceDate != null)
			setDateInvoiced(invoiceDate);
		setDateAcct(getDateInvoiced());
		//
		setSalesRep_ID(order.getSalesRep_ID());
		//
		setC_BPartner_ID(order.getBill_BPartner_ID());
		setC_BPartner_Location_ID(order.getBill_Location_ID());
		setAD_User_ID(order.getBill_User_ID());
	} // MInvoice

	/**
	 * Create Invoice from Shipment
	 * 
	 * @param ship
	 *            shipment
	 * @param invoiceDate
	 *            date or null
	 */
	public MInvoice(MInOut ship, Timestamp invoiceDate) {
		this(ship.getCtx(), 0, ship.get_TrxName());
		setClientOrg(ship);
		setShipment(ship); // set base settings
		//
		setC_DocTypeTarget_ID();
		if (invoiceDate != null)
			setDateInvoiced(invoiceDate);
		setDateAcct(getDateInvoiced());
		//
		setSalesRep_ID(ship.getSalesRep_ID());
	} // MInvoice

	/**
	 * Create Invoice from Batch Line
	 * 
	 * @param batch
	 *            batch
	 * @param line
	 *            batch line
	 */
	public MInvoice(MInvoiceBatch batch, MInvoiceBatchLine line) {
		this(line.getCtx(), 0, line.get_TrxName());
		setClientOrg(line);
		setDocumentNo(line.getDocumentNo());
		//
		setIsSOTrx(batch.isSOTrx());
		MBPartner bp = new MBPartner(line.getCtx(), line.getC_BPartner_ID(),
				line.get_TrxName());
		setBPartner(bp); // defaults
		//
		setIsTaxIncluded(line.isTaxIncluded());
		// May conflict with default price list
		setC_Currency_ID(batch.getC_Currency_ID());
		setC_ConversionType_ID(batch.getC_ConversionType_ID());
		//
		// setPaymentRule(order.getPaymentRule());
		// setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
		// setPOReference("");
		setDescription(batch.getDescription());
		// setDateOrdered(order.getDateOrdered());
		//
		setAD_OrgTrx_ID(line.getAD_OrgTrx_ID());
		setC_Project_ID(line.getC_Project_ID());
		// setC_Campaign_ID(line.getC_Campaign_ID());
		setC_Activity_ID(line.getC_Activity_ID());
		setUser1_ID(line.getUser1_ID());
		setUser2_ID(line.getUser2_ID());
		//
		setC_DocTypeTarget_ID(line.getC_DocType_ID());
		setDateInvoiced(line.getDateInvoiced());
		setDateAcct(line.getDateAcct());
		//
		setSalesRep_ID(batch.getSalesRep_ID());
		//
		setC_BPartner_ID(line.getC_BPartner_ID());
		setC_BPartner_Location_ID(line.getC_BPartner_Location_ID());
		setAD_User_ID(line.getAD_User_ID());
	} // MInvoice

	/** Open Amount */
	private BigDecimal m_openAmt = null;

	/** Invoice Lines */
	private MInvoiceLine[] m_lines;
	/** Invoice Taxes */
	private MInvoiceTax[] m_taxes;
	/** Logger */
	private static CLogger s_log = CLogger.getCLogger(MInvoice.class);

	/**
	 * Overwrite Client/Org if required
	 * 
	 * @param AD_Client_ID
	 *            client
	 * @param AD_Org_ID
	 *            org
	 */
	public void setClientOrg(int AD_Client_ID, int AD_Org_ID) {
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	} // setClientOrg

	/**
	 * Set Business Partner Defaults & Details
	 * 
	 * @param bp
	 *            business partner
	 */
	public void setBPartner(MBPartner bp) {
		if (bp == null)
			return;

		setC_BPartner_ID(bp.getC_BPartner_ID());
		// Set Defaults
		int ii = 0;
		if (isSOTrx())
			ii = bp.getC_PaymentTerm_ID();
		else
			ii = bp.getPO_PaymentTerm_ID();
		if (ii != 0)
			setC_PaymentTerm_ID(ii);
		//
		if (isSOTrx())
			ii = bp.getM_PriceList_ID();
		else
			ii = bp.getPO_PriceList_ID();
		if (ii != 0)
			setM_PriceList_ID(ii);
		//
		String ss = bp.getPaymentRule();
		if (ss != null)
			setPaymentRule(ss);

		// Set Locations
		MBPartnerLocation[] locs = bp.getLocations(false);
		if (locs != null) {
			for (int i = 0; i < locs.length; i++) {
				if ((locs[i].isBillTo() && isSOTrx())
						|| (locs[i].isPayFrom() && !isSOTrx()))
					setC_BPartner_Location_ID(locs[i]
							.getC_BPartner_Location_ID());
			}
			// set to first
			if (getC_BPartner_Location_ID() == 0 && locs.length > 0)
				setC_BPartner_Location_ID(locs[0].getC_BPartner_Location_ID());
		}
		if (getC_BPartner_Location_ID() == 0) {

			// OpenUp. Gabriel Vila. 25/04/2014. Issue #2109.
			// No es necesario para documentos de compra que haya localizacion
			// de socio de negocio.
			// Comento y cambio codigo

			// log.log(Level.SEVERE, new
			// BPartnerNoAddressException(bp).getLocalizedMessage());

			if (this.isSOTrx()) {
				log.log(Level.SEVERE, new BPartnerNoAddressException(bp)
						.getLocalizedMessage());
			} else {
				// Me creo una location nueva para este socio de negocio y le
				// asocio la misma a este documento de compra
				MLocation location = new MLocation(getCtx(), 0, get_TrxName());
				location.setC_Country_ID(336); // Uruguay
				location.setCity("NO Departamento");
				location.setRegionName("OR");
				location.setAddress4("NO Localidad");
				location.saveEx();
				MBPartnerLocation bploc = new MBPartnerLocation(getCtx(), 0,
						get_TrxName());
				bploc.setC_BPartner_ID(this.getC_BPartner_ID());
				bploc.setName("NO Departamento");
				bploc.setC_Location_ID(location.get_ID());
				bploc.setC_Country_ID(location.getC_Country_ID());
				bploc.setIsBillTo(true);
				bploc.setIsShipTo(true);
				bploc.setIsRemitTo(true);
				bploc.setIsPayFrom(true);
				bploc.saveEx();

				this.setC_BPartner_Location_ID(bploc.get_ID());
			}

			// Fin OpenUp. Issue #2109.

		}

		// Set Contact
		MUser[] contacts = bp.getContacts(false);
		if (contacts != null && contacts.length > 0) // get first User
			setAD_User_ID(contacts[0].getAD_User_ID());
	} // setBPartner

	/**
	 * Set Order References
	 * 
	 * @param order
	 *            order
	 */
	public void setOrder(MOrder order) {
		if (order == null)
			return;

		setC_Order_ID(order.getC_Order_ID());
		setIsSOTrx(order.isSOTrx());
		setIsDiscountPrinted(order.isDiscountPrinted());
		setIsSelfService(order.isSelfService());
		setSendEMail(order.isSendEMail());
		//
		setM_PriceList_ID(order.getM_PriceList_ID());
		setIsTaxIncluded(order.isTaxIncluded());
		setC_Currency_ID(order.getC_Currency_ID());
		setC_ConversionType_ID(order.getC_ConversionType_ID());
		//
		setPaymentRule(order.getPaymentRule());
		setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
		setPOReference(order.getPOReference());
		setDescription(order.getDescription());
		setDateOrdered(order.getDateOrdered());
		//
		setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		setC_Project_ID(order.getC_Project_ID());
		setC_Campaign_ID(order.getC_Campaign_ID());
		setC_Activity_ID(order.getC_Activity_ID());
		setUser1_ID(order.getUser1_ID());
		setUser2_ID(order.getUser2_ID());
	} // setOrder

	/**
	 * Set Shipment References
	 * 
	 * @param ship
	 *            shipment
	 */
	public void setShipment(MInOut ship) {
		if (ship == null)
			return;

		// OpenUp. Gabriel Vila. 17/02/2012.
		setM_InOut_ID(ship.getM_InOut_ID());
		// Fin OpenUp.

		setIsSOTrx(ship.isSOTrx());
		//
		MBPartner bp = new MBPartner(getCtx(), ship.getC_BPartner_ID(), null);
		setBPartner(bp);
		//
		setAD_User_ID(ship.getAD_User_ID());
		//
		setSendEMail(ship.isSendEMail());
		//
		setPOReference(ship.getPOReference());
		setDescription(ship.getDescription());
		setDateOrdered(ship.getDateOrdered());
		//
		setAD_OrgTrx_ID(ship.getAD_OrgTrx_ID());
		setC_Project_ID(ship.getC_Project_ID());
		setC_Campaign_ID(ship.getC_Campaign_ID());
		setC_Activity_ID(ship.getC_Activity_ID());
		setUser1_ID(ship.getUser1_ID());
		setUser2_ID(ship.getUser2_ID());
		//
		if (ship.getC_Order_ID() != 0) {
			setC_Order_ID(ship.getC_Order_ID());
			MOrder order = new MOrder(getCtx(), ship.getC_Order_ID(),
					get_TrxName());
			setIsDiscountPrinted(order.isDiscountPrinted());
			setM_PriceList_ID(order.getM_PriceList_ID());
			setIsTaxIncluded(order.isTaxIncluded());
			setC_Currency_ID(order.getC_Currency_ID());
			setC_ConversionType_ID(order.getC_ConversionType_ID());
			setPaymentRule(order.getPaymentRule());
			setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
			//
			MDocType dt = MDocType.get(getCtx(), order.getC_DocType_ID());
			if (dt.getC_DocTypeInvoice_ID() != 0)
				setC_DocTypeTarget_ID(dt.getC_DocTypeInvoice_ID());
			// Overwrite Invoice BPartner
			setC_BPartner_ID(order.getBill_BPartner_ID());
			// Overwrite Invoice Address
			setC_BPartner_Location_ID(order.getBill_Location_ID());
			// Overwrite Contact
			setAD_User_ID(order.getBill_User_ID());
			//
		}
		// Check if Shipment/Receipt is based on RMA
		if (ship.getM_RMA_ID() != 0) {
			setM_RMA_ID(ship.getM_RMA_ID());

			MRMA rma = new MRMA(getCtx(), ship.getM_RMA_ID(), get_TrxName());
			// Retrieves the invoice DocType
			MDocType dt = MDocType.get(getCtx(), rma.getC_DocType_ID());
			if (dt.getC_DocTypeInvoice_ID() != 0) {
				setC_DocTypeTarget_ID(dt.getC_DocTypeInvoice_ID());
			}
			setIsSOTrx(rma.isSOTrx());

			MOrder rmaOrder = rma.getOriginalOrder();
			if (rmaOrder != null) {
				setM_PriceList_ID(rmaOrder.getM_PriceList_ID());
				setIsTaxIncluded(rmaOrder.isTaxIncluded());
				setC_Currency_ID(rmaOrder.getC_Currency_ID());
				setC_ConversionType_ID(rmaOrder.getC_ConversionType_ID());
				setPaymentRule(rmaOrder.getPaymentRule());
				setC_PaymentTerm_ID(rmaOrder.getC_PaymentTerm_ID());
				setC_BPartner_Location_ID(rmaOrder.getBill_Location_ID());
			}
		}

	} // setShipment

	/**
	 * Set Target Document Type
	 * 
	 * @param DocBaseType
	 *            doc type MDocType.DOCBASETYPE_
	 */
	public void setC_DocTypeTarget_ID(String DocBaseType) {
		String sql = "SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE AD_Client_ID=? AND AD_Org_ID in (0,?) AND DocBaseType=?"
				+ " AND IsActive='Y' "
				+ "ORDER BY IsDefault DESC, AD_Org_ID DESC";
		int C_DocType_ID = DB.getSQLValueEx(null, sql, getAD_Client_ID(),
				getAD_Org_ID(), DocBaseType);
		if (C_DocType_ID <= 0)
			log.log(Level.SEVERE, "Not found for AD_Client_ID="
					+ getAD_Client_ID() + " - " + DocBaseType);
		else {
			log.fine(DocBaseType);
			setC_DocTypeTarget_ID(C_DocType_ID);
			boolean isSOTrx = MDocType.DOCBASETYPE_ARInvoice
					.equals(DocBaseType)
					|| MDocType.DOCBASETYPE_ARCreditMemo.equals(DocBaseType);
			setIsSOTrx(isSOTrx);
		}
	} // setC_DocTypeTarget_ID

	/**
	 * Set Target Document Type. Based on SO flag AP/AP Invoice
	 */
	public void setC_DocTypeTarget_ID() {
		if (getC_DocTypeTarget_ID() > 0)
			return;
		if (isSOTrx())
			setC_DocTypeTarget_ID(MDocType.DOCBASETYPE_ARInvoice);
		else
			setC_DocTypeTarget_ID(MDocType.DOCBASETYPE_APInvoice);
	} // setC_DocTypeTarget_ID

	/**
	 * Get Grand Total
	 * 
	 * @param creditMemoAdjusted
	 *            adjusted for CM (negative)
	 * @return grand total
	 */
	public BigDecimal getGrandTotal(boolean creditMemoAdjusted) {
		if (!creditMemoAdjusted)
			return super.getGrandTotal();
		//
		BigDecimal amt = getGrandTotal();
		if (isCreditMemo())
			return amt.negate();
		return amt;
	} // getGrandTotal

	/**
	 * Get Invoice Lines of Invoice
	 * 
	 * @param whereClause
	 *            starting with AND
	 * @return lines
	 */
	private MInvoiceLine[] getLines(String whereClause) {
		String whereClauseFinal = "C_Invoice_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MInvoiceLine> list = new Query(getCtx(),
				I_C_InvoiceLine.Table_Name, whereClauseFinal, get_TrxName())
				.setParameters(getC_Invoice_ID())
				.setOrderBy(I_C_InvoiceLine.COLUMNNAME_Line).list();
		return list.toArray(new MInvoiceLine[list.size()]);
	} // getLines

	/**
	 * Get Invoice Lines
	 * 
	 * @param requery
	 * @return lines
	 */
	public MInvoiceLine[] getLines(boolean requery) {
		if (m_lines == null || m_lines.length == 0 || requery)
			m_lines = getLines(null);
		set_TrxName(m_lines, get_TrxName());
		return m_lines;
	} // getLines

	/**
	 * Get Lines of Invoice
	 * 
	 * @return lines
	 */
	public MInvoiceLine[] getLines() {
		return getLines(false);
	} // getLines

	/**
	 * Renumber Lines
	 * 
	 * @param step
	 *            start and step
	 */
	public void renumberLines(int step) {
		int number = step;
		MInvoiceLine[] lines = getLines(false);
		for (int i = 0; i < lines.length; i++) {
			MInvoiceLine line = lines[i];
			line.setLine(number);
			line.saveEx();
			number += step;
		}
		m_lines = null;
	} // renumberLines

	/**
	 * Copy Lines From other Invoice.
	 * 
	 * @param otherInvoice
	 *            invoice
	 * @param counter
	 *            create counter links
	 * @param setOrder
	 *            set order links
	 * @return number of lines copied
	 */
	public int copyLinesFrom(MInvoice otherInvoice, boolean counter,
			boolean setOrder) {
		if (isProcessed() || isPosted() || otherInvoice == null)
			return 0;
		MInvoiceLine[] fromLines = otherInvoice.getLines(false);
		int count = 0;
		for (int i = 0; i < fromLines.length; i++) {
			MInvoiceLine line = new MInvoiceLine(getCtx(), 0, get_TrxName());
			MInvoiceLine fromLine = fromLines[i];
			if (counter) // header
				PO.copyValues(fromLine, line, getAD_Client_ID(), getAD_Org_ID());
			else
				PO.copyValues(fromLine, line, fromLine.getAD_Client_ID(),
						fromLine.getAD_Org_ID());
			line.setC_Invoice_ID(getC_Invoice_ID());
			line.setInvoice(this);
			line.set_ValueNoCheck("C_InvoiceLine_ID", I_ZERO); // new
			// Reset
			if (!setOrder)
				line.setC_OrderLine_ID(0);
			line.setRef_InvoiceLine_ID(0);
			line.setM_InOutLine_ID(0);
			line.setA_Asset_ID(0);
			line.setM_AttributeSetInstance_ID(0);
			line.setS_ResourceAssignment_ID(0);
			// New Tax
			if (getC_BPartner_ID() != otherInvoice.getC_BPartner_ID())
				line.setTax(); // recalculate
			//
			if (counter) {
				line.setRef_InvoiceLine_ID(fromLine.getC_InvoiceLine_ID());
				if (fromLine.getC_OrderLine_ID() != 0) {
					MOrderLine peer = new MOrderLine(getCtx(),
							fromLine.getC_OrderLine_ID(), get_TrxName());
					if (peer.getRef_OrderLine_ID() != 0)
						line.setC_OrderLine_ID(peer.getRef_OrderLine_ID());
				}
				line.setM_InOutLine_ID(0);
				if (fromLine.getM_InOutLine_ID() != 0) {
					MInOutLine peer = new MInOutLine(getCtx(),
							fromLine.getM_InOutLine_ID(), get_TrxName());
					if (peer.getRef_InOutLine_ID() != 0)
						line.setM_InOutLine_ID(peer.getRef_InOutLine_ID());
				}
			}
			//
			line.setProcessed(false);
			if (line.save(get_TrxName()))
				count++;
			// Cross Link
			if (counter) {
				fromLine.setRef_InvoiceLine_ID(line.getC_InvoiceLine_ID());
				fromLine.save(get_TrxName());
			}

			// MZ Goodwill
			// copy the landed cost
			line.copyLandedCostFrom(fromLine);
			line.allocateLandedCosts();
			// end MZ
		}
		if (fromLines.length != count)
			log.log(Level.SEVERE, "Line difference - From=" + fromLines.length
					+ " <> Saved=" + count);
		return count;
	} // copyLinesFrom

	/** Reversal Flag */
	private boolean m_reversal = false;

	/**
	 * Set Reversal
	 * 
	 * @param reversal
	 *            reversal
	 */
	private void setReversal(boolean reversal) {
		m_reversal = reversal;
	} // setReversal

	/**
	 * Is Reversal
	 * 
	 * @return reversal
	 */
	public boolean isReversal() {
		return m_reversal;
	} // isReversal

	/**
	 * Get Taxes
	 * 
	 * @param requery
	 *            requery
	 * @return array of taxes
	 */
	public MInvoiceTax[] getTaxes(boolean requery) {
		if (m_taxes != null && !requery)
			return m_taxes;

		final String whereClause = MInvoiceTax.COLUMNNAME_C_Invoice_ID + "=?";
		List<MInvoiceTax> list = new Query(getCtx(), I_C_InvoiceTax.Table_Name,
				whereClause, get_TrxName()).setParameters(get_ID()).list();
		m_taxes = list.toArray(new MInvoiceTax[list.size()]);
		return m_taxes;
	} // getTaxes

	/**
	 * Add to Description
	 * 
	 * @param description
	 *            text
	 */
	public void addDescription(String description) {
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	/**
	 * Is it a Credit Memo?
	 * 
	 * @return true if CM
	 */
	public boolean isCreditMemo() {
		MDocType dt = MDocType.get(getCtx(),
				getC_DocType_ID() == 0 ? getC_DocTypeTarget_ID()
						: getC_DocType_ID());
		return MDocType.DOCBASETYPE_APCreditMemo.equals(dt.getDocBaseType())
				|| MDocType.DOCBASETYPE_ARCreditMemo
						.equals(dt.getDocBaseType());
	} // isCreditMemo

	/**
	 * Set Processed. Propergate to Lines/Taxes
	 * 
	 * @param processed
	 *            processed
	 */
	public void setProcessed(boolean processed) {
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		String set = "SET Processed='" + (processed ? "Y" : "N")
				+ "' WHERE C_Invoice_ID=" + getC_Invoice_ID();
		int noLine = DB.executeUpdate("UPDATE C_InvoiceLine " + set,
				get_TrxName());
		int noTax = DB.executeUpdate("UPDATE C_InvoiceTax " + set,
				get_TrxName());
		m_lines = null;
		m_taxes = null;
		log.fine(processed + " - Lines=" + noLine + ", Tax=" + noTax);
	} // setProcessed

	/**
	 * Validate Invoice Pay Schedule
	 * 
	 * @return pay schedule is valid
	 */
	public boolean validatePaySchedule() {
		MInvoicePaySchedule[] schedule = MInvoicePaySchedule
				.getInvoicePaySchedule(getCtx(), getC_Invoice_ID(), 0,
						get_TrxName());
		log.fine("#" + schedule.length);
		if (schedule.length == 0) {
			setIsPayScheduleValid(false);
			return false;
		}
		// Add up due amounts
		BigDecimal total = Env.ZERO;
		for (int i = 0; i < schedule.length; i++) {
			schedule[i].setParent(this);
			BigDecimal due = schedule[i].getDueAmt();
			if (due != null)
				total = total.add(due);
		}
		boolean valid = getGrandTotal().compareTo(total) == 0;
		setIsPayScheduleValid(valid);

		// Update Schedule Lines
		for (int i = 0; i < schedule.length; i++) {
			if (schedule[i].isValid() != valid) {
				schedule[i].setIsValid(valid);
				schedule[i].saveEx(get_TrxName());
			}
		}
		return valid;
	} // validatePaySchedule

	/**************************************************************************
	 * Before Save
	 * 
	 * @param newRecord
	 *            new
	 * @return true
	 */
	protected boolean beforeSave(boolean newRecord) {

		
		// OpenUp. Gabriel Vila. 18/03/2015. Issue #3798
		// Me aseguro que la fecha contable sea igual a la fecha factura
		this.setDateAcct(this.getDateInvoiced());
		
		// OpenUp. Gabriel Vila. 19/09/2011. Issue #820.
		// Instancio tipo de documento para utilizarlo en este metodo
		MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(),
				null);
		// Fin Issue #820.
		
		//OpenUp SBT 23/02/2016 Issue #5471
		//No permito se modifiquen datos si es NC Cliente desde una Factura
		if(!newRecord){
			if(doc.getValue().equalsIgnoreCase("customernc") && this.getUY_Invoice_ID()>0){
				if(is_ValueChanged("C_Currency_ID") || is_ValueChanged("C_BPartner_Location_ID")
					|| is_ValueChanged("paymentruletype") || is_ValueChanged("C_PaymentTerm_ID")){
						throw new AdempiereException("No se pueden realizar cambios en la Nota de Credito si la misma está realacionada a una Factura");
					}	
			}
			if(doc.getValue().equalsIgnoreCase("customernc") && this.getUY_Invoice_ID()==0 	
					&& ((this.get_ValueOldAsInt("UY_Invoice_ID"))>0)){
				for (MInvoiceLine line : this.getLines()) {
					line.delete(true);//Si se pasa de una factura a ninguna factura se deben eliminar todas las líneas que se habían cargado anterioremente
				}
			}
		}//FIN OpenUp SBT 23/02/2016 Issue #5471
		
		// OpenUp M.R. 08-09-2011. Issue #753.
		// Valido numero de documento repetido para documentos de compra
		if (!doc.isSOTrx()) {
			if (newRecord || is_ValueChanged(COLUMNNAME_DocumentNoAux)
					|| is_ValueChanged(COLUMNNAME_C_BPartner_ID)) {
				validateDocumentNo();
			}
		}
		// Fin Issue #753.

		
		
		// OpenUp. Gabriel Vila. 18/10/2012. Issue #76
		// Si es nota de credito proveedor me aseguro tipo de forma de pago y
		// termino de pago = credito
		if (!doc.isSOTrx()) {
			if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APCredit)) {
				this.setpaymentruletype(X_UY_PaymentRule.PAYMENTRULETYPE_CREDITO);
				this.setC_PaymentTerm_ID(MPaymentTerm.getCredito(getCtx(),
						get_TrxName()).get_ID());
			}

			// Para documentos de compra puedo setear fecha de documento del
			// proveedor.
			if (this.getDateVendor() != null) {
				if (this.getDateVendor().compareTo(this.getDateInvoiced()) > 0) {
					throw new AdempiereException(
							"La fecha proveedor no puede ser mayor a la fecha de transaccion");
				}
			}
			// OpenUp. Nicolas Sarlabos. 07/10/2013. #1201
			if (doc.getValue() != null) {
				if (doc.getValue().equalsIgnoreCase("vendornc")
						|| doc.getValue().equalsIgnoreCase("invoicevendor")) { // OpenUp.
																				// Nicolas
																				// Sarlabos.
																				// 26/11/2013.
																				// #1657.
																				// Agrego
																				// Factura
																				// Proveedor.
					// Me aseguro traer la tasa de cambio para nota de credito
					// proveedor
					if (newRecord) {
						BigDecimal dividerate = Env.ZERO;
						if (this.getC_Currency_ID() != 142) {
							
							Timestamp date = TimeUtil.trunc(this.getDateInvoiced(), TimeUtil.TRUNC_DAY);
							
							dividerate = MConversionRate.getDivideRate(142, this.getC_Currency_ID(), date, 0, this.getAD_Client_ID(), 0);
							//OpenUp. Nicolas Sarlabos. 24/11/2015. #4930.
							if (dividerate == Env.ZERO) throw new AdempiereException("No se obtuvo tasa de cambio para fecha de documento");
							//Fin OpenUp.
						}
						this.setCurrencyRate(dividerate);
					}
				}
				// OpenUp. Nicolas Sarlabos. 12/12/2013. #1588. Valido que la
				// orden y proveedor se correspondan.
				else if (doc.getValue().equalsIgnoreCase("invoicevendor")) {

					if (this.getC_Order_ID() > 0 && this.getC_BPartner_ID() > 0) {

						MOrder order = new MOrder(getCtx(),
								this.getC_Order_ID(), null);

						if (order.getC_BPartner_ID() != this.getC_BPartner_ID())
							throw new AdempiereException(
									"La orden seleccionada no corresponde al proveedor");

					}

				}
				// Fin OpenUp. #1588.
				
				// OpenUp. Gabriel Vila. 13/11/2014. Issue #3205
				// Factura de vale flete se debe ingresar subtotal y total de manera manual por el tema de iva segun territorialidad 
				else if (doc.getValue().equalsIgnoreCase("inv_valeflete")) {
					if ((this.getTotalLines() == null) || (this.getTotalLines().compareTo(Env.ZERO) <= 0)){
						throw new AdempiereException("Debe indicar Subtotal del documento.");
					}
					if ((this.getGrandTotal() == null) || (this.getGrandTotal().compareTo(Env.ZERO) <= 0)){
						throw new AdempiereException("Debe indicar Total del documento.");
					}
				}
			}
			// Fin openUp. #1201.

		}

		// OpenUp Nicolas Sarlabos issue #922 1/12/2011
		// se controla el cambio de vendedor en la factura/nota de credito de
		// clientes, para que solo pueda realizarse con un rol autorizado
		if (this.isSOTrx()) {

			if (this.getC_BPartner_ID() > 0)
				checkSalesRep();

			// OpenUp Nicolas Sarlabos 31/10/2013 issue #1476
			if (doc.getValue() != null) {
				if (doc.getValue().equalsIgnoreCase("customernc") && this.getUY_Invoice_ID()<=0) {//Se agrega condición que la NC no tenga asociada una Factura
					this.setpaymentruletype(X_UY_PaymentRule.PAYMENTRULETYPE_CREDITO);
					
					// OpenUp Nicolas Sarlabos 25/11/2015 #5006
					if(this.getC_PaymentTerm_ID()<=0) 
						this.setC_PaymentTerm_ID(MPaymentTerm.getCredito(getCtx(), get_TrxName()).get_ID());
					// Fin OpenUp.
								
				}
				else if (doc.getValue().equalsIgnoreCase("facturaflete")){
					this.set_Value("C_Currency_ID_2", this.getC_Currency_ID());
				} 
				else if(doc.getValue().equalsIgnoreCase("customerncflete") || doc.getValue().equalsIgnoreCase("et-nc")){
					
					//OpenUp. Nicolas Sarlabos. 26/03/2015. #
					//para documento NC Cliente, si se selecciona factura de flete, se cargan datos de la misma.
					if(this.getUY_Invoice_ID()>0) this.loadFromFreightInvoice(newRecord);
					//Fin OpenUp.					
					
				}
				//Ini SBT 23-02-2016 Issue # 5471 NC Cliente dese Factura
				//Si es Nota de Cedito Cliente y tiene asociada una factura se deben cargar los datos correspondientes y las líneas correspondientes
				else if(doc.getValue().equalsIgnoreCase("customernc") && this.getUY_Invoice_ID()>0){
					MInvoice fact = new MInvoice(getCtx(),this.getUY_Invoice_ID(), this.get_TrxName());
					if(null!=fact && fact.get_ID()>0){
						this.loadNCFromInvoice(newRecord,fact);
					}
				}//FIN SBT 23-02-2016 Issue # 5471 
			}
			// Fin openUp.

		}
		// Fin OpenUp Nicolas Sarlabos issue #922 1/12/2011

		// Si vengo con tipo de forma de pago contado
		if (this.getpaymentruletype() != null) {
			if (this.getpaymentruletype().equalsIgnoreCase(
					X_UY_PaymentRule.PAYMENTRULETYPE_CONTADO)) {
				// Por defecto pongo termino de pago contado
				this.setC_PaymentTerm_ID(MPaymentTerm.getContado(getCtx(),
						get_TrxName()).get_ID());
			} else {
				// Me cubro que no me deje tipo credito con termino de pago
				// contado
				MPaymentTerm contado = MPaymentTerm.getContado(getCtx(), null);
				if (contado != null){
					if (this.getC_PaymentTerm_ID() == contado.get_ID()) {
						throw new AdempiereException("Termino de Pago no valido para Credito.");
					}
				}
			}
		}
		// Fin OpenUp

		// OpenUp. Gabriel Vila. 24/10/2012. Issue #82
		// Verificacion de devengamientos en comprobantes de compra
		if (!this.isSOTrx() && this.isDevengable()) {
			if (this.getC_Period_ID_To() < this.getC_Period_ID_From()) {
				throw new AdempiereException(
						"En Devengamiento el Periodo Hasta no puede ser menor que el Periodo Desde");
			}
			this.setQtyQuote(this.getC_Period_ID_To()
					- this.getC_Period_ID_From() + 1);
		}
		// Fin OpenUp Issue #82

		log.fine("");
		// No Partner Info - set Template
		if (getC_BPartner_ID() == 0)
			setBPartner(MBPartner.getTemplate(getCtx(), getAD_Client_ID()));
		if (getC_BPartner_Location_ID() == 0)
			setBPartner(new MBPartner(getCtx(), getC_BPartner_ID(), null));

		// OpenUp. Gabriel Vila. 02/20/1015. Issue #4861
		// Si es nuevo registro y es comprobante de factura de compra
		if ((newRecord) && (!this.isSOTrx()) && (doc.getDocBaseType().equalsIgnoreCase("API"))){
			// Si tengo flag de no usar lista por defecto
			if (MSysConfig.getBooleanValue("UY_PO_INVOICE_USE_NOT_DEFAULT_PRICELIST", false, this.getAD_Client_ID())){
				String idList = MSysConfig.getValue("UY_PO_INVOICE_NOT_DEFAULT_PRICELIST", "",  this.getAD_Client_ID());
				if (!idList.equalsIgnoreCase("")){
					MPriceList priceModel = new MPriceList(getCtx(), Integer.valueOf(idList), null);
					this.setM_PriceList_ID(priceModel.get_ID());
					this.setIsTaxIncluded(priceModel.isTaxIncluded());
				}
			}
		}
		// Fin OpenUp Issue #4861
		
		// Price List
		if (getM_PriceList_ID() == 0) {
			int ii = Env.getContextAsInt(getCtx(), "#M_PriceList_ID");
			if (ii != 0)
				setM_PriceList_ID(ii);
			else {
				String sql = "SELECT M_PriceList_ID FROM M_PriceList WHERE AD_Client_ID=? AND IsDefault='Y'";
				ii = DB.getSQLValue(null, sql, getAD_Client_ID());
				if (ii != 0)
					setM_PriceList_ID(ii);
			}
		}

		// Currency
		if (getC_Currency_ID() == 0) {
			String sql = "SELECT C_Currency_ID FROM M_PriceList WHERE M_PriceList_ID=?";
			int ii = DB.getSQLValue(null, sql, getM_PriceList_ID());
			if (ii != 0)
				setC_Currency_ID(ii);
			else
				setC_Currency_ID(Env
						.getContextAsInt(getCtx(), "#C_Currency_ID"));
		}

		// Sales Rep
		if (getSalesRep_ID() == 0) {
			int ii = Env.getContextAsInt(getCtx(), "#SalesRep_ID");
			if (ii != 0)
				setSalesRep_ID(ii);
		}

		// OpenUP FL 21/03/2011, issue #536, set sales representative, if its
		// not set yet, to the partner default sales representative
		if (getSalesRep_ID() == 0) {
			MBPartner bPartner = new MBPartner(getCtx(),
					this.getC_BPartner_ID(), null); // Get
			// the
			// partner
			if (bPartner != null) { // Check for retrival. Defensive
				this.setSalesRep_ID(bPartner.getSalesRep_ID()); // Set it
			}
		}

		// Document Type
		if (getC_DocType_ID() == 0)
			setC_DocType_ID(0); // make sure it's set to 0
		if (getC_DocTypeTarget_ID() == 0)
			setC_DocTypeTarget_ID(isSOTrx() ? MDocType.DOCBASETYPE_ARInvoice
					: MDocType.DOCBASETYPE_APInvoice);

		// Payment Term
		if (getC_PaymentTerm_ID() == 0) {
			int ii = Env.getContextAsInt(getCtx(), "#C_PaymentTerm_ID");
			if (ii != 0)
				setC_PaymentTerm_ID(ii);
			else {
				String sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE AD_Client_ID=? AND IsDefault='Y'";
				ii = DB.getSQLValue(null, sql, getAD_Client_ID());
				if (ii != 0)
					setC_PaymentTerm_ID(ii);
			}
		}

		// OpenUp. Gabriel Vila. 17/02/2012.
		// Si es un remito
		if (doc.getDocSubTypeSO() != null) {
			if (doc.getDocSubTypeSO().equalsIgnoreCase("RR")) {
				this.setTotalLines(Env.ZERO);
				this.setGrandTotal(Env.ZERO);
			}
		}
		// Fin OpenUp.

		// OpenUp. Gabriel Vila. 03/10/2012. Issue #
		// Para documento : Factura Proveedor sin OC, se debe ingresar el numero
		// de documento de manera manual y no sugerirlo.
		// No permito numero de documento en blanco y para recibos el usuario
		// debe ingresarlo manualmente.
		if ((doc.getValue() != null)
				&& (doc.getValue().equalsIgnoreCase("factprovsinoc")
						|| doc.getValue().equalsIgnoreCase("invoicevendor") || doc
						.getValue().equalsIgnoreCase("retencionnodoc"))) { // OpenUp.
																			// Nicolas
																			// Sarlabos.
																			// 31/01/2013.
																			// Agrego
																			// value
																			// para
																			// documento
																			// factura
																			// proveedor

			if (!MSysConfig.getBooleanValue("UY_ALLOW_NULL_PO_DOCUMENTNO",
					false, this.getAD_Client_ID())) {
				if (this.getDocumentNoAux() == null)
					throw new AdempiereException(
							"Debe ingresar manualmente un Numero para este Documento.");

				// Trim del numero ingresado por las dudas que metan blancos
				this.setDocumentNo(this.getDocumentNoAux().trim());

				if (this.getDocumentNo().equalsIgnoreCase(""))
					throw new AdempiereException(
							"Debe ingresar manualmente un Numero para este Documento.");

			}
			// Valido que no se repita el numero de documento cuando doy de alta
			// o cuando lo modifico
			if ((newRecord)
					|| ((!newRecord) && (is_ValueChanged(COLUMNNAME_DocumentNo)))) {
				this.validateDocumentNo();
			}

			// Me aseguro traer la tasa de cambio para factura proveedor
			if (newRecord) {
				BigDecimal dividerate = Env.ONE;
				if (this.getC_Currency_ID() != 142) {
					dividerate = MConversionRate.getDivideRate(142,
							this.getC_Currency_ID(), this.getDateInvoiced(), 0,
							this.getAD_Client_ID(), 0);
					if (dividerate == null)
						dividerate = Env.ONE;
				}
				this.setCurrencyRate(dividerate);
			}

		}
		// Fin OpenUp

		// OpenUp. Nicolas Sarlabos. 27/12/2012, seteo porcentaje de descuento
		// al pie si la factura proviene de una orden o devolucion
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false,
				this.getAD_Client_ID())) {

			if (isSOTrx()) {
				if (this.getC_Order_ID() > 0 || this.getM_InOut_ID() > 0) {

					BigDecimal Discount = Env.ZERO;

					if (this.getC_Order_ID() > 0) { // si proviene de una orden

						MOrder order = new MOrder(getCtx(),
								this.getC_Order_ID(), get_TrxName());
						Discount = order.getDiscount();

					}

					if (Discount.compareTo(Env.ZERO) == 0) {

						if (this.getM_InOut_ID() > 0) { // si proviene de una
														// devolucion

							MInOut inout = new MInOut(getCtx(),
									this.getM_InOut_ID(), get_TrxName());
							Discount = inout.getDiscount();

						}
					}

					this.setDiscount(Discount);
				}

			}
		}
		// Fin OpenUp

		// OpenUp. Nicolas Sarlabos. 28/05/2013. #884
		// OpenUp. Guillermo Brust. 24/05/2013. ISSUE #870. Si es factura con
		// prespuesto, respeto la lista de precio del presupuesto.
		if (this.getUY_Budget_ID() > 0) {

			MBudget b = new MBudget(getCtx(), this.getUY_Budget_ID(),
					this.get_TrxName()); // instancio nuevo presupuesto y su
											// lista de precios
			int priceListID = b.getM_PriceList_ID();

			// Obtengo las ultima linea para esta factura
			MInvoiceLine ultimaLinea = MInvoiceLine.getInvoiceLineForInovice(
					this.getCtx(), this.get_ID());

			if (ultimaLinea != null) { // si existen lineas anteriores...

				if (priceListID > 0) { // si el nuevo presupuesto tiene lista de
										// precios
					if (priceListID != this.getM_PriceList_ID()) // si la lista
																	// del
																	// presupuesto
																	// nuevo es
																	// diferente
																	// a la del
																	// cabezal
																	// muestro
																	// error
						throw new AdempiereException(
								"El presupuesto "
										+ b.getDocumentNo()
										+ " no tiene la misma lista de precios que el anterior.");
				}

			} else if (priceListID > 0)
				this.setM_PriceList_ID(priceListID); // si es la primer linea de
														// factura y el
														// presupuesto no tiene
														// lista de precios dejo
														// por defecto la del
														// cabezal
														// si tiene lista
														// entonces la seteo al
														// cabezal

		}
		// Fin OpenUp.
		// Fin OpenUp. #884

		this.setC_BPartner_ID_Aux(this.getC_BPartner_ID()); // OpenUp Nicolas
															// Sarlabos
															// 21/11/2013 issue
															// #1322

		// OpenUp. Guillermo Brust. 16/01/2014. ISSUE #1633
		// Cargo el concepto de la mercaderia
		// OpenUp. Nicolas Sarlabos. 27/07/2014. Se modifica IF para considerar
		// documento PROFORMA.
		if ((doc.getValue() != null)) {

			if (doc.getValue().equalsIgnoreCase("freightinvoice") || doc.getValue().equalsIgnoreCase("et-ticket")) {
				
				//OpenUp. Nicolas Sarlabos. 31/03/2016. #5697. Controlo tipo de documento.
				MBPartner partner = (MBPartner)this.getC_BPartner();
				
				if(doc.getValue().equalsIgnoreCase("freightinvoice")){
					
					if(!partner.getDocumentType().equalsIgnoreCase("RUT")) throw new AdempiereException("El cliente NO ES CONTRIBUYENTE. Debe seleccionar documento 'E-Ticket Flete'.");					
					
				} else if (doc.getValue().equalsIgnoreCase("et-ticket")){
					
					if(partner.getDocumentType().equalsIgnoreCase("RUT")) throw new AdempiereException("El cliente ES CONTRIBUYENTE. Debe seleccionar documento 'Factura Flete'.");					
					
				}
				//Fin OpenUp.

				if (this.get_ValueAsInt("C_Currency3_ID") > 0)
					this.setC_Currency_ID(this.get_ValueAsInt("C_Currency3_ID"));
				
				//OpenUp. Nicolas Sarlabos. 26/05/2015. #4260. Se realizan controles en base al expediente.
				if(newRecord || is_ValueChanged("C_BPartner_ID")) this.validatePartnerFromTrip();					
				//Fin #4260.

				int tripID = this.get_ValueAsInt("UY_TR_Trip_ID");

				if (newRecord || is_ValueChanged("UY_TR_Crt_ID")) {

					if (tripID > 0) {

						MTRTrip trip = new MTRTrip(this.getCtx(), tripID,
								this.get_TrxName());

						String value = "";

						int crtID = this.get_ValueAsInt("UY_TR_Crt_ID");

						if (crtID > 0) {

							MTRCrt crt = new MTRCrt(getCtx(), crtID,
									get_TrxName());

							value = "Cto. " + crt.getNumero() + " FACTURA N° "
									+ trip.getReferenceNo();

						} else
							value = "FACTURA N° " + trip.getReferenceNo();

						this.setobservaciones(value);
					}

				}

				this.loadFromProforma(newRecord); // se cargan campos desde la
													// proforma del CRT

				if (newRecord || is_ValueChanged("C_PaymentTerm_ID")) {

					// obtengo fecha de vencimiento a partir del termino de pago
					// y fecha de facturacion
					String sql = "select paymenttermduedate("
							+ this.getC_PaymentTerm_ID() + ",'"
							+ this.getDateInvoiced() + "')";

					Timestamp dueDate = DB.getSQLValueTSEx(get_TrxName(), sql);

					if (dueDate != null)
						this.setDueDate(dueDate);

				}

			} else if (doc.getValue().equalsIgnoreCase("fileprofinvoice")) {

				if (this.get_ValueAsInt("C_Currency3_ID") > 0)
					this.setC_Currency_ID(this.get_ValueAsInt("C_Currency3_ID"));
				
				//OpenUp. Nicolas Sarlabos. 26/05/2015. #4260. Se realizan controles en base al expediente.
				if(newRecord || is_ValueChanged("C_BPartner_ID")) this.validatePartnerFromTrip();					
				//Fin #4260.
				
				/*
				MTRTrip trip = null;
				int tripID = this.get_ValueAsInt("UY_TR_Trip_ID");
				
				//controlo saldos del expediente
				if(tripID > 0) {
								
					trip = new MTRTrip(this.getCtx(), tripID, this.get_TrxName());

					String sql = "SELECT COALESCE(sum(inv.uy_cantbultos), 0)" +
							" FROM c_invoice inv" +
							" WHERE uy_tr_trip_id = " + trip.get_ID() +
							" AND docstatus <> 'VO'" +
							" AND c_invoice_id <> " + this.get_ID();

					BigDecimal qtyUsed = DB.getSQLValueBDEx(null, sql);

					if(qtyUsed==null) qtyUsed = Env.ZERO;

					BigDecimal qtyDisponible = trip.getQtyPackage().subtract(qtyUsed); //obtengo bultos disponibles

					if(this.getuy_cantbultos().compareTo(qtyDisponible)>0) throw new AdempiereException("Cantidad de bultos ingresada supera los " + qtyDisponible.setScale(0, RoundingMode.HALF_UP) + " bultos disponibles para este Expediente");
					
				}
				*/
			} else if (doc.getValue().equalsIgnoreCase("customerncflete") || doc.getValue().equalsIgnoreCase("et-nc")) {
				
				//OpenUp. Nicolas Sarlabos. 31/03/2016. #5697. Controlo tipo de documento.
				MBPartner partner = (MBPartner)this.getC_BPartner();
				
				if(doc.getValue().equalsIgnoreCase("customerncflete")){
					
					if(!partner.getDocumentType().equalsIgnoreCase("RUT")) throw new AdempiereException("El cliente NO ES CONTRIBUYENTE. Debe seleccionar documento 'E-Ticket NC Flete'.");					
					
				} else if (doc.getValue().equalsIgnoreCase("et-nc")){
					
					if(partner.getDocumentType().equalsIgnoreCase("RUT")) throw new AdempiereException("El cliente ES CONTRIBUYENTE. Debe seleccionar documento 'Nota Credito'.");					
					
				}
				//Fin OpenUp.

				if (this.get_ValueAsInt("C_Currency3_ID") > 0)
					this.setC_Currency_ID(this.get_ValueAsInt("C_Currency3_ID"));
				
				if (newRecord || is_ValueChanged("C_PaymentTerm_ID")) {

					// obtengo fecha de vencimiento a partir del termino de pago
					// y fecha de facturacion
					String sql = "select paymenttermduedate("
							+ this.getC_PaymentTerm_ID() + ",'"
							+ this.getDateInvoiced() + "')";

					Timestamp dueDate = DB.getSQLValueTSEx(get_TrxName(), sql);

					if (dueDate != null)
						this.setDueDate(dueDate);

				}
				
			}	
		}
		//SbT
		if(!isSOTrx() && doc.getValue().equalsIgnoreCase("vendorncdesc")){
			this.updateTotalInvoices(false);
			this.updateAllocDirect(false);
		}
		// Fin OpenUp.
		// Fin OpenUp. 27/07/2014.

		return true;
	} // beforeSave
	
	/**
	 * Metodo que carga datos desde la factura en documento NC cliente.
	 * @author OpenUp SBT Issue #5471  23/2/2016 15:31:14
	 * @param newRecord
	 */
	private void loadNCFromInvoice(boolean newRecord, MInvoice fact) {
		
		if(newRecord || is_ValueChanged("UY_Invoice_ID")){
			
			this.setC_BPartner_Location_ID(fact.getC_BPartner_Location_ID());
			this.setC_Currency_ID(fact.getC_Currency_ID());
			this.set_Value("C_Currency2_ID", fact.get_Value("C_Currency2_ID"));
			
			this.setAmtOriginal(fact.getAmtOriginal());//?¿
			this.setPaymentRule(fact.getPaymentRule());//¿?

			this.setpaymentruletype(fact.getpaymentruletype());
			this.setC_PaymentTerm_ID(fact.getC_PaymentTerm_ID());
			this.setTotalLines(fact.getTotalLines());
			this.setGrandTotal(fact.getGrandTotal());

			/*
			this.setDescription(inv.getDescription()); ?? 
			this.setobservaciones(inv.getobservaciones()); ??
			this.set_Value("ReferenceNo", inv.get_Value("ReferenceNo")); ??
			this.setincoterms(inv.getincoterms()); ??
			this.set_Value("C_Currency3_ID", inv.get_Value("C_Currency3_ID"));
			inv.saveEx();*/
		}
		
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 26/05/2015. #4260.
	 * Metodo que valida el socio de negocio en proforma y factura de flete, segun lo indicado en el expediente.
	 */
	private void validatePartnerFromTrip() {

		MBPartner partner = null;

		MTRTrip trip = new MTRTrip(getCtx(), this.get_ValueAsInt("UY_TR_Trip_ID"), get_TrxName());

		if(trip!=null && trip.get_ID()>0){

			if(trip.getC_BPartner_ID_Aux()>0){ //si se factura a un tercero...

				partner = new MBPartner(getCtx(), trip.getC_BPartner_ID_Aux(), get_TrxName());

				if(this.getC_BPartner_ID()!=trip.getC_BPartner_ID_Aux()) 
					throw new AdempiereException("Este expediente se debe facturar a '" + partner.getName() + "'");			


			} else if(trip.getReceiptMode().equalsIgnoreCase("ORIGEN")){ //si se factura al exportador...

				partner = new MBPartner(getCtx(), trip.getC_BPartner_ID_To(), get_TrxName());

				if(this.getC_BPartner_ID()!=trip.getC_BPartner_ID_To()) 
					throw new AdempiereException("Este expediente se debe facturar al EXPORTADOR (" + partner.getName() + ")");


			} else if (trip.getReceiptMode().equalsIgnoreCase("DESTINO")){ //si se factura al importador...

				partner = new MBPartner(getCtx(), trip.getC_BPartner_ID_From(), get_TrxName());

				if(this.getC_BPartner_ID()!=trip.getC_BPartner_ID_From()) 
					throw new AdempiereException("Este expediente se debe facturar al IMPORTADOR (" + partner.getName() + ")");						

			} else if (trip.getReceiptMode().equalsIgnoreCase("AMBOS")){ //si se factura al exportador e importador...

				if(this.getC_BPartner_ID()!=trip.getC_BPartner_ID_To() && this.getC_BPartner_ID()!=trip.getC_BPartner_ID_From()) 
					throw new AdempiereException("El cliente seleccionado no pertenece al expediente");					

			}		
			
		} else throw new AdempiereException("Error al obtener el expediente");	

	}	

	/**
	 * OpenUp. Nicolas Sarlabos. 26/03/2015. #
	 * Metodo que carga datos desde la factura de flete en documento de NC cliente.
	 * repetido.
	 * @param newRecord 
	 */
	private void loadFromFreightInvoice(boolean newRecord) {

		if(newRecord || is_ValueChanged("UY_Invoice_ID")){

			MInvoice inv = new MInvoice(getCtx(), this.getUY_Invoice_ID(), get_TrxName());
			
			//se cargan datos del cabezal
			this.setC_BPartner_Location_ID(inv.getC_BPartner_Location_ID());
			this.setDescription(inv.getDescription());
			this.setobservaciones(inv.getobservaciones());
			this.set_Value("UY_TR_Trip_ID", inv.get_Value("UY_TR_Trip_ID"));
			this.set_Value("UY_TR_Crt_ID", inv.get_Value("UY_TR_Crt_ID"));
			this.set_Value("ReferenceNo", inv.get_Value("ReferenceNo"));
			this.setuy_cantbultos(inv.getuy_cantbultos());
			this.set_Value("UY_TR_PackageType_ID", inv.get_Value("UY_TR_PackageType_ID"));
			this.set_Value("Weight", inv.get_Value("Weight"));
			this.set_Value("Weight2", inv.get_Value("Weight2"));
			this.setAmtOriginal(inv.getAmtOriginal());
			this.setC_Currency_ID(inv.getC_Currency_ID());
			this.set_Value("C_Currency2_ID", inv.get_Value("C_Currency2_ID"));
			this.setincoterms(inv.getincoterms());
			this.setPaymentRule(inv.getPaymentRule());
			this.setpaymentruletype(inv.getpaymentruletype());
			this.setC_PaymentTerm_ID(inv.getC_PaymentTerm_ID());
			this.set_Value("C_Currency3_ID", inv.get_Value("C_Currency3_ID"));
			this.setTotalLines(inv.getTotalLines());
			this.setGrandTotal(inv.getGrandTotal());
			
			inv.saveEx();
	
		}
		
	}

	/**
	 * OpenUp. Mario Reyes. 09/09/2011. Issue #753. Valido numero de documento
	 * repetido.
	 */
	private void validateDocumentNo() {

		if (isSOTrx())
			return;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {

			String nroDoc = this.getDocumentNo();
			if (nroDoc == null)
				nroDoc = this.getDocumentNoAux();

			String whereID = "";
			if (this.get_ID() > 0)
				whereID = " and c_invoice_id !=" + this.get_ID();

			sql = " select * " + " from c_invoice " + " where (documentno = '"
					+ nroDoc + "' " + " or documentnoaux = '" + nroDoc + "') "
					+ whereID + " and issotrx = 'N' " + " and c_bpartner_id = "
					+ this.getC_BPartner_ID() + " and docstatus <>'VO'";

			pstmt = DB.prepareStatement(sql, null); // Create the statement
			rs = pstmt.executeQuery();

			// Read the first record and get a new object
			if (rs.next()) {
				throw new AdempiereException(
						"Ya existe un comprobante para este proveedor con el mismo número.");
			}

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	/**
	 * Before Delete
	 * 
	 * @return true if it can be deleted
	 */
	protected boolean beforeDelete() {
		// OpenUp. Gabriel Vila. 22/10/2010.
		// Para factura de compra no tiene sentido trabar la eliminacion cuando
		// la factura no esta completada
		// OpenUp. Nicolas Sarlabos. 10/09/2013. Agrego el estado INVALIDO.
		if (!isSOTrx()
				&& (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Drafted)
						|| this.getDocStatus().equalsIgnoreCase(
								DOCSTATUS_InProgress) || this.getDocStatus()
						.equalsIgnoreCase(DOCSTATUS_Invalid)))
			return true;
		// Fin OpenUp.

		if (getC_Order_ID() != 0) {
			log.saveError("Error", Msg.getMsg(getCtx(), "CannotDelete"));
			return false;
		}
		
		
		// OpenUp. Gabriel Vila. 13/01/2015. Issue #1405.
		// No dejo eliminar documentos aplicados.
		if (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Applied)){
			log.saveError(null, "No es posible borrar un Documento en Estado Aplicado.");
			return false;
		}
		// Fin Openup. Issue #1405.
		
		return true;
	} // beforeDelete

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("MInvoice[").append(get_ID())
				.append("-").append(getDocumentNo()).append(",GrandTotal=")
				.append(getGrandTotal());
		if (m_lines != null)
			sb.append(" (#").append(m_lines.length).append(")");
		sb.append("]");
		return sb.toString();
	} // toString

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	} // getDocumentInfo

	/**
	 * After Save
	 * 
	 * @param newRecord
	 *            new
	 * @param success
	 *            success
	 * @return success
	 */
	protected boolean afterSave(boolean newRecord, boolean success) {
		// OpenUp. Nicolas Sarlabos. 12/11/2012
		if (!success)
			return success;
		// Fin OpenUp.
		
		
		
		if (is_ValueChanged("AD_Org_ID")) {
			String sql = "UPDATE C_InvoiceLine ol"
					+ " SET AD_Org_ID ="
					+ "(SELECT AD_Org_ID"
					+ " FROM C_Invoice o WHERE ol.C_Invoice_ID=o.C_Invoice_ID) "
					+ "WHERE C_Invoice_ID=" + getC_Invoice_ID();
			int no = DB.executeUpdate(sql, get_TrxName());
			log.fine("Lines -> #" + no);
		}

		// OpenUp. Gabriel Vila. 18/10/2012. Issue #76
		// Ante cambio de forma de pago me aseguro de integridad de la
		// informacion
		if (is_ValueChanged("UY_PaymentRule_ID")) {
			// Si es forma de pago a credito, me aseguro de no dejar registros
			// de pago directo
			if (this.getpaymentruletype().equalsIgnoreCase(
					X_UY_PaymentRule.PAYMENTRULETYPE_CREDITO)) {
				String action = " DELETE FROM uy_invoicecashpayment "
						+ " WHERE c_invoice_id =" + this.get_ID();
				DB.executeUpdate(action, get_TrxName());
			}
		}
		// Fin OpenUp

		// OpenUp. Nicolas Sarlabos. 12/11/2012
		// si es una Factura Venta y se selecciono una orden de fabricacion
		// entonces cargo las lineas de la OF
		MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(), null);
		if (doc.getValue() != null) {
			if (doc.getValue().equalsIgnoreCase("custinvoice")
					|| doc.getValue().equalsIgnoreCase("custinvoicectr")) { // OpenUp.
																			// Nicolas
																			// Sarlabos.
																			// 07/03/2013.
																			// #488.
				if (this.getUY_ManufOrder_ID() > 0) {
					loadManufLines(newRecord);
				}
			}

			// OpenUp. Nicolas Sarlabos. 12/12/2013. #1588.
			// Se cargan las lineas de la orden de compra directa siempre y
			// cuando el usuario no lo este haciendo desde
			// el boton de crear desde en la factura proveedor
			else if (doc.getValue().equalsIgnoreCase("invoicevendor")
					&& !this.LinesCreatedAutoFromPO)
				this.loadFromPO(newRecord);
			// Fin OpenUp. Issue #1588

		}
		// Fin OpenUp.

		// OpenUp. Nicolas Sarlabos. 16/04/2013. #720
		if (doc.getValue() != null) {
			if (doc.getValue().equalsIgnoreCase("devcontado")) {
				loadInvoiceLines(newRecord); // si es una devolucion contado se
												// procede a cargar lineas de
												// factura
			} else if (doc.getValue().equalsIgnoreCase("proform")) { // OpenUp.
																		// Nicolas
																		// Sarlabos.
																		// 29/07/2013.
																		// si es
																		// una
																		// proforma
																		// se
																		// procede
																		// a
																		// cargar
																		// lineas
																		// de
																		// presupuesto
				loadBudgetLines(newRecord);
				
			} else if(doc.getValue().equalsIgnoreCase("customerncflete") || doc.getValue().equalsIgnoreCase("et-nc")) {
				
				if(this.getUY_Invoice_ID()>0) this.loadLinesFreightInvoice(newRecord);
			}
			//SBT 23-02-2016 Issue # 5471 si es NC Cliente (venta) se deben crear líneas desde Factura
			else if(this.isSOTrx() && doc.getValue().equalsIgnoreCase("customernc") && this.getUY_Invoice_ID()>0){  
				// se crean lineas desde las líneas de la factura
				this.loadLinesFromInvoice(newRecord);
			}
		}
		// Fin OpenUp. #720

		// OpenUp. Guillermo Brust. 16/01/2014. ISSUE #1633.
		if ((doc.getValue() != null)
				&& (doc.getValue().equalsIgnoreCase("freightinvoice") || doc.getValue().equalsIgnoreCase("et-ticket"))) {

			MTRTrip trip = null;

			int tripID = this.get_ValueAsInt("UY_TR_Trip_ID");

			if (tripID > 0) {

				trip = new MTRTrip(this.getCtx(), tripID, this.get_TrxName());

				MProduct prod = (MProduct) trip.getM_Product();

				String prodName = "", borderName = "", value = "";

				if (prod.get_ID() > 0)
					prodName = prod.getName();

				if (newRecord || is_ValueChanged("UY_TR_Trip_ID")
						|| is_ValueChanged("weight")) {

					String sql = "";
					
					MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
					
					if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
				
					MTRConfigRound round = MTRConfigRound.forConfig(getCtx(), param.get_ID(), get_TrxName());
					
					if (round==null) throw new AdempiereException("No se obtuvieron parametros de precision decimal para la empresa actual");

					sql = "select c.name as ciudad"
							+ " from uy_tr_trip tr"
							+ " inner join uy_ciudad c on tr.uy_ciudad_id = c.uy_ciudad_id"
							+ " where tr.uy_tr_trip_id = " + trip.get_ID();
					String cityFrom = DB
							.getSQLValueStringEx(get_TrxName(), sql);

					sql = "select c.name as ciudad"
							+ " from uy_tr_trip tr"
							+ " inner join uy_ciudad c on tr.uy_ciudad_id_1::int = c.uy_ciudad_id"
							+ " where tr.uy_tr_trip_id = " + trip.get_ID();
					String cityTo = DB.getSQLValueStringEx(get_TrxName(), sql);

					BigDecimal weight = new BigDecimal(
							this.get_ValueAsString("weight")).setScale(round.getWeight(), RoundingMode.HALF_UP);

					MTRBorder border = new MTRBorder(getCtx(),
							trip.getUY_TR_Border_ID_1(), get_TrxName());

					if (border.get_ID() > 0)
						borderName = border.getName();

					value = "Transporte de " + weight + " Kg de " + prodName
							+ " de " + cityFrom + " hasta " + cityTo + " por "
							+ borderName + " segun expediente Nro. "
							+ trip.getDocumentNo();

					DB.executeUpdateEx("update c_invoice set description = '"
							+ value.toUpperCase() + "' where c_invoice_id = "
							+ this.get_ID(), get_TrxName());

				}

				this.loadFreightLines(newRecord);
				this.setConceptFreightInv(newRecord);
			}
		}
		// Fin OpenUp.

		// OpenUp. Nicolas Sarlabos. 21/02/2014. ISSUE #1623
		if ((doc.getValue() != null)
				&& (doc.getValue().equalsIgnoreCase("fileprofinvoice"))) {

			MTRTrip trip = new MTRTrip(getCtx(),this.get_ValueAsInt("UY_TR_Trip_ID"),get_TrxName());			

			if (newRecord) {

				if(trip.getUY_TR_Budget_ID()>0){ //si el expediente tiene una cotizacion, creo las lineas desde la misma

					MTRBudget budget = (MTRBudget)trip.getUY_TR_Budget();
					
					this.setC_PaymentTerm_ID(budget.getC_PaymentTerm_ID());
					this.setC_Currency_ID(budget.getC_Currency_ID());
					this.set_Value("c_currency3_id", budget.getC_Currency_ID());
					
					MTRBudgetLine bLine = new MTRBudgetLine(getCtx(),trip.getUY_TR_BudgetLine_ID(),get_TrxName());
					
					if(bLine!=null && bLine.get_ID()>0){
						
						List<MTRBudgetDetail> detLines = bLine.getLines();//obtengo lineas de detalle de cotizacion
						
						for (MTRBudgetDetail det: detLines){
							
							MInvoiceLine invLine = new MInvoiceLine(getCtx(),0,get_TrxName());
							invLine.setC_Invoice_ID(this.get_ID());
							invLine.IsFromProforma = true;
							invLine.setM_Product_ID(det.getM_Product_ID());
							invLine.setPriceEntered(det.getPriceEntered());
							invLine.setPriceList(det.getPriceList());
							invLine.setPriceLimit(det.getPriceLimit());
							invLine.setPriceActual(det.getPriceActual());
							invLine.set_Value("NationalPercentage", det.getNationalPercentage());
							invLine.set_Value("InterPercentage", det.getInterPercentage());
							invLine.set_Value("AplicaValorem", det.isAplicaValorem());
							invLine.set_Value("ValoremPercentage", det.getValoremPercentage());
							invLine.set_Value("NationalAmt", det.getNationalAmt());
							invLine.set_Value("InternationalAmt", det.getInternationalAmt());
							invLine.setC_Tax_ID(det.getC_Tax_ID());
							invLine.setLineNetAmt(det.getLineNetAmt());
							invLine.setTaxAmt(det.getTaxAmt());
							invLine.setLineTotalAmt(det.getLineTotalAmt());							
							invLine.saveEx(get_TrxName());							
						}						
					}

				} else {
					
					MProduct prod = null;

					// creo nueva linea para Flete
					// obtengo producto FLETE segun el tipo de expediente
					if (trip.getTripType().equalsIgnoreCase("IMPORTACION")) {

						prod = MProduct.forValueAllClients(this.getCtx(), "fleteimp", this.get_TrxName());

					} else if (trip.getTripType().equalsIgnoreCase(
							"EXPORTACION")) {

						prod = MProduct.forValueAllClients(this.getCtx(), "fleteexp", this.get_TrxName());

					} else if (trip.getTripType().equalsIgnoreCase("NACIONAL")) {

						prod = MProduct.forValueAllClients(this.getCtx(), "fletenac", this.get_TrxName());
					}

					MInvoiceLine line = new MInvoiceLine(this.getCtx(), 0,
							this.get_TrxName());
					line.setM_Product_ID(prod.get_ID());
					line.setC_Invoice_ID(this.get_ID());
					line.saveEx();

				}
			}
			// Fin OpenUp. #1623.
		}
		return true;
	} // afterSave

	/**
	 * Se crean lineas a la NC Cliente desde la Factura asociada
	 * @author OpenUp SBT Issue#5471  23/2/2016 15:44:39
	 * @param newRecord
	 */
	private void loadLinesFromInvoice(boolean newRecord) {
			
		if ((newRecord) || (is_ValueChanged(COLUMNNAME_UY_Invoice_ID))) {
				
			// Primero borro todas las lineas, para evitar basura en las lineas
			for (MInvoiceLine line : this.getLines()) {
				line.delete(true);
			}

			MInvoice hdr = new MInvoice(getCtx(), this.getUY_Invoice_ID(), get_TrxName());
							
			for (MInvoiceLine line : hdr.getLines(null)) {

				MInvoiceLine invline = new MInvoiceLine(getCtx(), 0, get_TrxName());
				invline.setC_Invoice_ID(this.get_ID());
				invline.setLine(line.getLine());
				invline.setM_Product_ID(line.getM_Product_ID());
				invline.setPriceEntered(line.getPriceEntered().setScale(2, RoundingMode.HALF_UP));
				invline.setPriceList(line.getPriceList().setScale(2, RoundingMode.HALF_UP));
				invline.setPriceActual(line.getPriceEntered().setScale(2, RoundingMode.HALF_UP));
				invline.setPriceLimit(line.getPriceLimit().setScale(2, RoundingMode.HALF_UP));
				invline.setQtyInvoiced(line.getQtyInvoiced());
				invline.setC_UOM_ID(line.getC_UOM_ID());
				invline.setC_Tax_ID(line.getC_Tax_ID());
				invline.setTaxAmt(line.getTaxAmt().setScale(2, RoundingMode.HALF_UP));
				invline.setQtyEntered(line.getQtyEntered());
				invline.setLineNetAmt(line.getLineNetAmt().setScale(2, RoundingMode.HALF_UP));
				invline.setLineTotalAmt(line.getLineTotalAmt().setScale(2, RoundingMode.HALF_UP));
				invline.set_Value("C_Currency2_ID", line.get_Value("C_Currency2_ID"));
				invline.set_Value("CurrencyRate", line.get_Value("CurrencyRate"));			
				invline.set_Value("IsManual",false);

				invline.saveEx();			
			}
		}	
	}
	
	/**
	 * Metodo que carga las lineas desde la factura de flete en la NC cliente.
	 * @author Nicolas Sarlabos - 26/03/2015. #
	 * @see
	 * @return
	 */
	private void loadLinesFreightInvoice(boolean newRecord) {

		if ((newRecord) || (is_ValueChanged(COLUMNNAME_UY_Invoice_ID))) {

			// Primero borro todas las lineas, para evitar basura en las
			// lineas
			for (MInvoiceLine line : this.getLines()) {
				line.delete(true);
			}

			MInvoice hdr = new MInvoice(getCtx(), this.getUY_Invoice_ID(), get_TrxName());
						
			for (MInvoiceLine line : hdr.getLines(null)) {

				MInvoiceLine invline = new MInvoiceLine(getCtx(), 0, get_TrxName());
				invline.setC_Invoice_ID(this.get_ID());
				invline.setLine(line.getLine());
				invline.setM_Product_ID(line.getM_Product_ID());
				invline.setPriceEntered(line.getPriceEntered().setScale(2, RoundingMode.HALF_UP));
				invline.setPriceList(line.getPriceList().setScale(2, RoundingMode.HALF_UP));
				invline.setPriceActual(line.getPriceEntered().setScale(2, RoundingMode.HALF_UP));
				invline.setPriceLimit(line.getPriceLimit().setScale(2, RoundingMode.HALF_UP));
				invline.setQtyInvoiced(line.getQtyInvoiced());
				invline.setC_UOM_ID(line.getC_UOM_ID());
				invline.setC_Tax_ID(line.getC_Tax_ID());
				invline.setTaxAmt(line.getTaxAmt().setScale(2, RoundingMode.HALF_UP));
				invline.setQtyEntered(line.getQtyEntered());
				invline.setLineNetAmt(line.getLineNetAmt().setScale(2, RoundingMode.HALF_UP));
				invline.set_Value("AplicaValorem", line.get_Value("AplicaValorem"));
				invline.set_Value("ValoremPercentage", line.get_Value("ValoremPercentage"));
				invline.setLineTotalAmt(line.getLineTotalAmt().setScale(2, RoundingMode.HALF_UP));
				invline.set_Value("NationalPercentage", line.get_Value("NationalPercentage"));
				invline.set_Value("InterPercentage", line.get_Value("InterPercentage"));
				invline.set_Value("NationalAmt", ((BigDecimal)line.get_Value("NationalAmt")).setScale(2, RoundingMode.HALF_UP));
				invline.set_Value("InternationalAmt", ((BigDecimal)line.get_Value("InternationalAmt")).setScale(2, RoundingMode.HALF_UP));
				invline.set_Value("C_Currency2_ID", line.get_Value("C_Currency2_ID"));
				invline.set_Value("CurrencyRate", line.get_Value("CurrencyRate"));			
				invline.IsFromFreightInv = true;

				invline.saveEx();			

			}

		}
		
		
	}

	/**
	 * Metodo que carga datos en el cabezal de la factura flete desde la
	 * proforma asociada al CRT. Si no se selecciono CRT, los campos se cargan
	 * desde el expediente.
	 * 
	 * @author Nicolas Sarlabos - 18/03/2014. #1633.
	 * @see
	 * @return
	 */
	private void loadFromProforma(boolean newRecord) {

		int crtID = this.get_ValueAsInt("UY_TR_Crt_ID");
		int tripID = this.get_ValueAsInt("UY_TR_Trip_ID");
		
		MTRTrip trip = new MTRTrip(getCtx(), tripID, get_TrxName()); //obtengo expediente

		if (crtID > 0) { // si se selecciono CRT

			MTRCrt crt = new MTRCrt(getCtx(), crtID, get_TrxName()); // obtengo
																		// CRT						
			MInvoice prof = new MInvoice(getCtx(), crt.getC_Invoice_ID(),
					get_TrxName()); // obtengo proforma del CRT

			this.set_ValueOfColumn("ReferenceNo",
					prof.get_ValueAsString("ReferenceNo"));

			if (newRecord || is_ValueChanged("UY_TR_Crt_ID")) {

				if (prof != null) {									
					
					this.set_ValueOfColumn("UY_TR_PackageType_ID",
							prof.get_Value("UY_TR_PackageType_ID"));
					this.setuy_cantbultos(prof.getuy_cantbultos());
					this.set_ValueOfColumn("Weight", prof.get_Value("Weight"));
					this.set_ValueOfColumn("Weight2", prof.get_Value("Weight2"));
					this.setAmtOriginal(prof.getAmtOriginal());
					this.setincoterms(prof.getincoterms());
					this.set_ValueOfColumn("C_Currency2_ID",
							prof.get_Value("C_Currency2_ID"));
					//OpenUp. Nicolas Sarlabos. 20/10/2015. #4924. Se comenta codigo.
					//this.setC_Currency_ID(prof.getC_Currency_ID());
					//this.set_ValueOfColumn("C_Currency3_ID",
							//prof.get_Value("C_Currency3_ID"));
					//fin #4924.
					this.setC_PaymentTerm_ID(prof.getC_PaymentTerm_ID());

				}

			}

		} else if (tripID > 0) {

			this.set_Value("ReferenceNo", trip.getReferenceNo());

			if (newRecord || is_ValueChanged("UY_TR_Trip_ID")) {

				if (trip != null) {

					this.set_ValueOfColumn("UY_TR_PackageType_ID",
							trip.get_Value("UY_TR_PackageType_ID"));
					this.setuy_cantbultos(trip.getQtyPackage());
					this.set_ValueOfColumn("Weight", trip.get_Value("Weight"));
					this.set_ValueOfColumn("Weight2", trip.get_Value("Weight2"));
					this.setAmtOriginal(trip.getProductAmt());
					this.setincoterms(trip.getIncotermType());
					this.set_ValueOfColumn("C_Currency2_ID",
							trip.getC_Currency_ID());

				}

			}

		}
		
		//seteo direccion del cliente
		if(this.getC_BPartner_ID()==trip.getC_BPartner_ID_To()){
									
			this.setC_BPartner_Location_ID(trip.getC_BPartner_Location_ID_2());
			
		} else if (this.getC_BPartner_ID()==trip.getC_BPartner_ID_From()){
			
			this.setC_BPartner_Location_ID(trip.getC_BPartner_Location_ID());
			
		}

	}

	/**
	 * Metodo que carga las lineas de la orden de fabricacion en la Factura de
	 * Venta OpenUp Ltda.
	 * 
	 * @author Nicolas Sarlabos - 13/11/2012
	 * @see
	 * @return
	 */

	private void loadManufLines(boolean newRecord) {

		if ((newRecord) || (is_ValueChanged(COLUMNNAME_UY_ManufOrder_ID))) {

			MManufOrder order = new MManufOrder(getCtx(),
					this.getUY_ManufOrder_ID(), get_TrxName());

			List<MManufLine> manuflines = order.getLines();

			for (MManufLine manufline : manuflines) {
				// si la linea esta pendiente de facturacion entonces se carga
				if (manufline.isPending()) {
					MInvoiceLine invline = new MInvoiceLine(getCtx(), 0,
							get_TrxName());
					BigDecimal qtyPending = (manufline.getQty()
							.subtract(manufline.getQtyInvoiced())).setScale(2,
							RoundingMode.HALF_UP); // obtengo cantidad pendiente
													// de facturacion

					invline.setC_Invoice_ID(this.get_ID());
					invline.setQtyInvoiced(qtyPending);
					invline.setQtyEntered(qtyPending);
					invline.setM_Product_ID(manufline.getM_Product_ID());
					invline.setUY_ManufLine_ID(manufline.get_ID());
					invline.setIsSelected(true);

					MBudgetLine budgetline = new MBudgetLine(getCtx(),
							manufline.getUY_BudgetLine_ID(), get_TrxName());

					String sql = "select coalesce(price1,price2,price3) from uy_budgetline where uy_budgetline_id="
							+ budgetline.get_ID();
					BigDecimal priceList = DB.getSQLValueBD(get_TrxName(), sql);

					// OpenUp. Guillermo Brust. 09/09/2013. ISSUE #
					// Si el descuento aplicado es mayor a cero, se debe obtener
					// el precio unitario con descuento
					// ya que el descuento se aplica al precio unitario y no al
					// total
					BigDecimal precioConDescuento = new BigDecimal(0);
					if (budgetline.getDiscount().compareTo(new BigDecimal(0)) > 0) {
						BigDecimal descuento = priceList.multiply(
								budgetline.getDiscount().divide(
										new BigDecimal(100),
										RoundingMode.HALF_UP)).setScale(2,
								RoundingMode.HALF_UP);
						precioConDescuento = priceList.subtract(descuento);
					} else
						precioConDescuento = priceList;

					invline.setPriceList(priceList);
					invline.setPriceEntered(priceList);
					invline.setPriceActual(precioConDescuento);
					invline.setPriceLimit(priceList);
					invline.setLineNetAmt(qtyPending
							.multiply(precioConDescuento));
					invline.setLineTotalAmt(qtyPending
							.multiply(precioConDescuento));
					invline.setC_Tax_ID(1000004);
					invline.setFlatDiscount(budgetline.getDiscount());
					invline.setAD_Org_ID(this.getAD_Org_ID());

					// Fin OpenUp.

					// OpenUp. Nicolas Sarlabos. 20/03/2013. #595. Se carga nro.
					// de presupuesto para imprimir en factura
					MBudget budget = new MBudget(getCtx(),
							budgetline.getUY_Budget_ID(), get_TrxName());
					invline.setnum_budget(budget.getDocumentNo());
					// Fin OpenUp.

					invline.saveEx();
				}
			}

		}

	}

	/**
	 * OpenUp. Nicolas Sarlabos. 17/03/2014. ISSUE #1633. Se cargan las lineas
	 * de la factura flete a partir de los datos del expediente y CRT
	 * 
	 */

	private void loadFreightLines(boolean newRecord) {

		String sql = "";
		MInvoiceLine invline = null;
		BigDecimal amtAvailable = Env.ZERO, amtAllocated = Env.ZERO; 

		if ((newRecord) || (is_ValueChanged("UY_TR_Crt_ID"))) {

			int crtID = this.get_ValueAsInt("UY_TR_Crt_ID");

			if (crtID > 0) {

				// Primero borro todas las lineas, para evitar basura en las
				// lineas
				for (MInvoiceLine line : this.getLines()) {
					line.delete(true);
				}

				MTRCrt crt = new MTRCrt(this.getCtx(), crtID,
						this.get_TrxName());

				MInvoice proforma = new MInvoice(getCtx(),
						crt.getC_Invoice_ID(), get_TrxName()); // instancio
																// proforma
																// asociada

				// seteo moneda de factura desde la proforma
				// this.setC_Currency_ID(proforma.getC_Currency_ID());
				// this.set_ValueOfColumn("C_Currency3_ID",
				// proforma.get_ValueAsInt("C_Currency3_ID"));

				int tripID = this.get_ValueAsInt("UY_TR_Trip_ID");

				MTRTrip trip = new MTRTrip(getCtx(), tripID, get_TrxName()); // instancio
																				// expediente

				// Se cargan las lineas de la factura a partir de los datos de
				// la proforma asociada al CRT seleccionado
				for (MInvoiceLine line : proforma.getLines()) {

					MProduct prod = new MProduct(getCtx(),
							line.getM_Product_ID(), get_TrxName());
					
					//si el expediente se cobra a ambos, se deben realizar calculos para cargar solo los importes
					//asociados al cliente de esta proforma
					if(trip.getReceiptMode().equalsIgnoreCase("AMBOS")){
						
						// obtengo importe facturado en documentos de Factura Flete
						// en estado <> VO
						sql = "select coalesce(amtallocated,0)"
								+ " from alloc_freightinvoiceline"
								+ " where m_product_id = " + prod.get_ID()
								+ " and uy_tr_trip_id = "
								+ this.get_ValueAsInt("uy_tr_trip_id")
								+ " and uy_tr_crt_id = "
								+ this.get_ValueAsInt("uy_tr_crt_id")
								+ " and c_bpartner_id = "
								+ this.getC_BPartner_ID()
								+ " and docClientID = "
								+ this.getAD_Client_ID();
						amtAllocated = DB.getSQLValueBDEx(get_TrxName(),
								sql);

						if (amtAllocated == null)
							amtAllocated = Env.ZERO;						
						
						//si el cliente elegido es el EXPORTADOR, debo setear montos solo para territorio EXTRANJERO
						if(this.getC_BPartner_ID()==trip.getC_BPartner_ID_To()){
							
							amtAvailable = ((BigDecimal) line.get_Value("internationalamt")).subtract(
									amtAllocated); // obtengo el saldo disponible para
													// facturar
							//valorem siempre va el total
							/*if(prod.getValue().equalsIgnoreCase("valorem")) {
								
								sql = "select coalesce(amtallocated,0)"
										+ " from alloc_freightinvoiceline"
										+ " where m_product_id = " + prod.get_ID()
										+ " and uy_tr_trip_id = "
										+ this.get_ValueAsInt("uy_tr_trip_id")
										+ " and uy_tr_crt_id = "
										+ this.get_ValueAsInt("uy_tr_crt_id")
										+ " and m_product_id = "
										+ prod.getM_Product_ID()
										+ " and c_bpartner_id = "
										+ this.getC_BPartner_ID()
										+ " and docClientID = "
										+ this.getAD_Client_ID();
								amtAllocated = DB.getSQLValueBDEx(get_TrxName(),
										sql);

								if (amtAllocated == null)
									amtAllocated = Env.ZERO;
								
								if((amtAllocated.compareTo(Env.ZERO)==0) || (amtAllocated.compareTo(line.getPriceEntered())==0)){
									
									amtAvailable = line.getPriceEntered();
									
								} else amtAvailable = Env.ZERO;		
										
							}*/
															
							if (amtAvailable.compareTo(Env.ZERO) > 0) {
								
								invline = new MInvoiceLine(getCtx(), 0, get_TrxName());
								invline.setC_Invoice_ID(this.get_ID());
								invline.setLine(line.getLine());
								invline.setM_Product_ID(line.getM_Product_ID());
								invline.setPriceEntered(amtAvailable);
								invline.setPriceList(amtAvailable);
								invline.setPriceActual(amtAvailable);
								invline.setPriceLimit(amtAvailable);
								invline.setQtyInvoiced(line.getQtyInvoiced());
								invline.setC_UOM_ID(line.getC_UOM_ID());
								invline.setC_Tax_ID(line.getC_Tax_ID());
								invline.setTaxAmt(line.getTaxAmt());
								invline.setQtyEntered(line.getQtyEntered());
								invline.setLineNetAmt(amtAvailable);
								invline.IsFromFreightInv = true;
								
								if (trip.getTripType().equalsIgnoreCase("EXPORTACION")) {

									invline.set_Value("NationalPercentage", Env.ONEHUNDRED);
									invline.set_Value("InterPercentage", Env.ZERO);
									invline.set_Value("nationalamt", amtAvailable);
									invline.set_Value("internationalamt", Env.ZERO);											

								} else {

									invline.set_Value("NationalPercentage", Env.ZERO);
									invline.set_Value("InterPercentage", Env.ONEHUNDRED);
									invline.set_Value("nationalamt", Env.ZERO);
									invline.set_Value("internationalamt", amtAvailable);
								}
								
								invline.saveEx();	
								
							} else ADialog.info(0,null,"El concepto " + prod.getName() + " no tiene saldo. Verifique otros documentos en estado COMPLETO o BORRADOR.");							
							
						//si el cliente elegido es el IMPORTADOR, debo setear montos solo para territorio NACIONAL	
						} else if (this.getC_BPartner_ID()==trip.getC_BPartner_ID_From()){ 
							
							amtAvailable = ((BigDecimal) line.get_Value("nationalamt")).subtract(
									amtAllocated); // obtengo el saldo disponible para
													// facturar
							//valorem siempre va el total
							/*if(prod.getValue().equalsIgnoreCase("valorem")){
								
								if(prod.getValue().equalsIgnoreCase("valorem")) {
									
									sql = "select coalesce(amtallocated,0)"
											+ " from alloc_freightinvoiceline"
											+ " where m_product_id = " + prod.get_ID()
											+ " and uy_tr_trip_id = "
											+ this.get_ValueAsInt("uy_tr_trip_id")
											+ " and uy_tr_crt_id = "
											+ this.get_ValueAsInt("uy_tr_crt_id")
											+ " and m_product_id = "
											+ prod.getM_Product_ID()
											+ " and c_bpartner_id = "
											+ this.getC_BPartner_ID()
											+ " and docClientID = "
											+ this.getAD_Client_ID();
									amtAllocated = DB.getSQLValueBDEx(get_TrxName(),
											sql);

									if (amtAllocated == null)
										amtAllocated = Env.ZERO;
									
									if((amtAllocated.compareTo(Env.ZERO)==0) || (amtAllocated.compareTo(line.getPriceEntered())==0)){
										
										amtAvailable = line.getPriceEntered();
										
									} else amtAvailable = Env.ZERO;		
											
								}
									
							}*/
							
							if (amtAvailable.compareTo(Env.ZERO) > 0) {
								
								invline = new MInvoiceLine(getCtx(), 0, get_TrxName());
								invline.setC_Invoice_ID(this.get_ID());
								invline.setLine(line.getLine());
								invline.setM_Product_ID(line.getM_Product_ID());
								invline.setPriceEntered(amtAvailable);
								invline.setPriceList(amtAvailable);
								invline.setPriceActual(amtAvailable);
								invline.setPriceLimit(amtAvailable);
								invline.setQtyInvoiced(line.getQtyInvoiced());
								invline.setC_UOM_ID(line.getC_UOM_ID());
								invline.setC_Tax_ID(line.getC_Tax_ID());
								invline.setTaxAmt(line.getTaxAmt());
								invline.setQtyEntered(line.getQtyEntered());
								invline.setLineNetAmt(amtAvailable);
								invline.IsFromFreightInv = true;
								
								if (trip.getTripType().equalsIgnoreCase("EXPORTACION")) {

									invline.set_Value("InterPercentage", Env.ONEHUNDRED);
									invline.set_Value("NationalPercentage", Env.ZERO);
									invline.set_Value("internationalamt", amtAvailable);
									invline.set_Value("nationalamt", Env.ZERO);											

								} else {

									invline.set_Value("NationalPercentage", Env.ONEHUNDRED);
									invline.set_Value("InterPercentage", Env.ZERO);
									invline.set_Value("nationalamt", amtAvailable);
									invline.set_Value("internationalamt", Env.ZERO);
								}
								
								invline.saveEx();	
								
							} else ADialog.info(0,null,"El concepto " + prod.getName() + " no tiene saldo. Verifique otros documentos en estado COMPLETO o BORRADOR.");								
							
						}						
						
					} else {
						
						// obtengo importe facturado en documentos de Factura Flete
						// en estado <> VO
						sql = "select coalesce(amtallocated,0)"
								+ " from alloc_freightinvoiceline"
								+ " where m_product_id = " + prod.get_ID()
								+ " and uy_tr_trip_id = "
								+ this.get_ValueAsInt("uy_tr_trip_id")
								+ " and uy_tr_crt_id = "
								+ this.get_ValueAsInt("uy_tr_crt_id")
								+ " and c_bpartner_id = "
								+ this.getC_BPartner_ID()
								+ " and docClientID = "
								+ this.getAD_Client_ID();
						amtAllocated = DB.getSQLValueBDEx(get_TrxName(),
								sql);

						if (amtAllocated == null)
							amtAllocated = Env.ZERO;

						amtAvailable = line.getPriceEntered().subtract(
								amtAllocated); // obtengo el saldo disponible para
												// facturar

						// si el producto tiene saldo disponible entonces creo la
						// linea
						if (amtAvailable.compareTo(Env.ZERO) > 0) {

							invline = new MInvoiceLine(getCtx(), 0,
									get_TrxName());
							invline.setC_Invoice_ID(this.get_ID());
							invline.setLine(line.getLine());
							invline.setM_Product_ID(line.getM_Product_ID());
							invline.setPriceEntered(amtAvailable);
							invline.setPriceList(amtAvailable);
							invline.setPriceActual(amtAvailable);
							invline.setPriceLimit(amtAvailable);
							invline.setQtyInvoiced(line.getQtyInvoiced());
							invline.setC_UOM_ID(line.getC_UOM_ID());
							invline.setC_Tax_ID(line.getC_Tax_ID());
							invline.setTaxAmt(line.getTaxAmt());
							invline.setQtyEntered(line.getQtyEntered());

							invline.setLineNetAmt(amtAvailable);

							if (trip.getTripType().equalsIgnoreCase("EXPORTACION")) {

								invline.set_Value("NationalPercentage",
										(BigDecimal) line
										.get_Value("InterPercentage"));
								invline.set_Value("InterPercentage",
										(BigDecimal) line
										.get_Value("NationalPercentage"));
								invline.set_Value("nationalamt",
										line.get_Value("internationalamt"));
								invline.set_Value("internationalamt",
										line.get_Value("nationalamt"));

							} else {

								invline.set_Value("NationalPercentage",
										(BigDecimal) line
										.get_Value("NationalPercentage"));
								invline.set_Value("InterPercentage",
										(BigDecimal) line
										.get_Value("InterPercentage"));
								invline.set_Value("nationalamt",
										line.get_Value("nationalamt"));
								invline.set_Value("internationalamt",
										line.get_Value("internationalamt"));
							}

							invline.set_Value("AplicaValorem",
									line.get_Value("AplicaValorem"));
							invline.set_Value("ValoremPercentage",
									line.get_Value("ValoremPercentage"));
							invline.IsFromProforma = true;
							invline.saveEx();
							
						} else ADialog.info(0,null,"El concepto " + prod.getName() + " no tiene saldo. Verifique otros documentos en estado COMPLETO o BORRADOR.");		
						
					}	
				}
				// }
			}
		}
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 26/06/2014. ISSUE #2228. Se carga el campo de
	 * texto del detalle de la factura
	 * 
	 */

	private void setConceptFreightInv(boolean newRecord) {

		if ((newRecord) || (is_ValueChanged("UY_TR_Crt_ID"))) {

			//CAROS
			String sql = ""; String value = "01 EQUIPO DICE CONTENER: \n";

			int tripID = this.get_ValueAsInt("UY_TR_Trip_ID");

			MTRTrip trip = new MTRTrip(getCtx(),tripID,get_TrxName());
			//instancio expediente

			sql = "select (p.name || ' - ' || round(i.uy_cantbultos,0) || ' BULTOS - ' || round(i.weight,2) || ' KB')"
					+ " from c_invoice i" +
					" inner join uy_tr_trip tr on i.uy_tr_trip_id = tr.uy_tr_trip_id"
					+ " left join m_product p on tr.m_product_id = p.m_product_id" +
					" where i.c_invoice_id = " + this.get_ID(); 

			String product = DB.getSQLValueStringEx(get_TrxName(), sql);

			if(product!=null) value += product + "\n";

			//obtengo referencia de factura desde el expediente
			if(trip.getReferenceNo()!=null && !trip.getReferenceNo().equalsIgnoreCase("")){

				value += "REF.: FACTURA N° " + trip.getReferenceNo() + "\n";

			}
			
			if(this.get_ValueAsInt("UY_TR_Crt_ID")>0){
				
				MTRCrt crt = new MTRCrt(getCtx(),this.get_ValueAsInt("UY_TR_Crt_ID"),get_TrxName());
				
				if(crt!=null && crt.get_ID()>0){
					
					value += "CRT N° " + crt.getNumero() + "\n";					
				}				
			}

			DB.executeUpdateEx("update c_invoice set concepto = '" + value +
					"' where c_invoice_id = " + this.get_ID(), get_TrxName());


			value = "";

			//ARDOINO
			// obtengo referencia de factura desde el expediente
			if (trip.getReferenceNo() != null
					&& !trip.getReferenceNo().equalsIgnoreCase(""))
				value += "REF.: FACTURA Exp. N° " + trip.getReferenceNo();

			DB.executeUpdateEx("update c_invoice set observaciones2 = '"
					+ value + "' where c_invoice_id = " + this.get_ID(),
					get_TrxName());

		}
	}

	/**
	 * Metodo que carga las lineas del presupuesto en el documento Proforma
	 * OpenUp Ltda. #1155
	 * 
	 * @author Nicolas Sarlabos - 29/07/2013
	 * @see
	 * @return
	 */

	private void loadBudgetLines(boolean newRecord) {

		if ((newRecord) || (is_ValueChanged(COLUMNNAME_UY_Budget_ID))) {

			MBudget budget = new MBudget(getCtx(), this.getUY_Budget_ID(),
					get_TrxName());

			List<MBudgetLine> budgetlines = budget.getLines();

			BigDecimal qty = Env.ZERO;
			BigDecimal amt = Env.ZERO;
			BigDecimal price = Env.ZERO;

			for (MBudgetLine bline : budgetlines) {

				MInvoiceLine invline = new MInvoiceLine(getCtx(), 0,
						get_TrxName());

				// OpenUp. Guillermo Brust. 15/08/2013. ISSUE #
				// Se necesita guardar el nombre del diseño1 del presupuesto en
				// el campo de description en la linea de la factura

				invline.setDescription(budget.getDescription());

				// FIn OpenUp.

				invline.setC_Invoice_ID(this.get_ID());
				invline.setM_Product_ID(bline.getM_Product_ID());
				invline.setFlatDiscount(bline.getDiscount());
				invline.setAD_Org_ID(this.getAD_Org_ID());
				invline.setnum_budget(budget.getDocumentNo());

				if (bline.getqty1().compareTo(Env.ZERO) > 0) {
					qty = bline.getqty1();
				} else if (bline.getqty2().compareTo(Env.ZERO) > 0) {
					qty = bline.getqty2();
				} else if (bline.getqty3().compareTo(Env.ZERO) > 0) {
					qty = bline.getqty3();
				}

				if (bline.getprice1().compareTo(Env.ZERO) > 0) {
					price = bline.getprice1();
				} else if (bline.getprice2().compareTo(Env.ZERO) > 0) {
					price = bline.getprice2();
				} else if (bline.getprice3().compareTo(Env.ZERO) > 0) {
					price = bline.getprice3();
				}

				if (bline.getamt1().compareTo(Env.ZERO) > 0) {
					amt = bline.getamt1();
				} else if (bline.getamt2().compareTo(Env.ZERO) > 0) {
					amt = bline.getamt2();
				} else if (bline.getamt3().compareTo(Env.ZERO) > 0) {
					amt = bline.getamt3();
				}

				invline.setQtyInvoiced(qty);
				invline.setQtyEntered(qty);
				invline.setPriceList(price);
				invline.setPriceEntered(price);
				invline.setPriceActual(price);
				invline.setPriceLimit(price);
				invline.setLineNetAmt(amt);
				invline.setLineTotalAmt(amt);
				invline.setC_Tax_ID(1000005);

				invline.saveEx();

			}

		}

	}

	/**
	 * Metodo que carga las lineas de la factura seleccionada en el cabezal para
	 * documentos de Devolucion Contado OpenUp Ltda. #720
	 * 
	 * @author Nicolas Sarlabos - 16/04/2013
	 * @see
	 * @return
	 */

	private void loadInvoiceLines(boolean newRecord) {

		if ((newRecord) || (is_ValueChanged(COLUMNNAME_UY_Invoice_ID))) {

			MInvoice inv = new MInvoice(getCtx(), this.getUY_Invoice_ID(),
					get_TrxName());

			DB.executeUpdateEx("DELETE FROM c_invoiceline WHERE c_invoice_id="
					+ this.get_ID(), get_TrxName());

			MInvoiceLine[] invlines = inv.getLines();

			for (MInvoiceLine line : invlines) {

				MInvoiceLine l = new MInvoiceLine(getCtx(), 0, get_TrxName()); // instancio
																				// nueva
																				// linea

				// seteo campos
				l.setC_Invoice_ID(this.get_ID());
				l.setQtyInvoiced(line.getQtyInvoiced());
				l.setQtyEntered(line.getQtyEntered());
				l.setM_Product_ID(line.getM_Product_ID());
				l.setUY_ManufLine_ID(line.getUY_ManufLine_ID());
				l.setIsSelected(true);
				l.setPriceList(line.getPriceList());
				l.setPriceEntered(line.getPriceEntered());
				l.setPriceActual(line.getPriceActual());
				l.setPriceLimit(line.getPriceLimit());
				l.setLineNetAmt(line.getLineNetAmt());
				l.setC_UOM_ID(line.getC_UOM_ID());
				l.setTaxAmt(line.getTaxAmt());
				l.setC_Tax_ID(line.getC_Tax_ID());
				l.setLineTotalAmt(line.getLineTotalAmt());
				l.setFlatDiscount(line.getFlatDiscount());
				l.setnum_budget(line.getnum_budget());

				l.saveEx(); // guardo linea

			}

		}

	}

	/**
	 * Metodo que carga las lineas de la orden de compra seleccionada en el
	 * cabezal para documentos Orden de Compra Directa OpenUp Ltda. #1588
	 * 
	 * @author Nicolas Sarlabos - 12/12/2013
	 * @see
	 * @return
	 */

	private void loadFromPO(boolean newRecord) {

		if ((newRecord) || (is_ValueChanged(COLUMNNAME_C_Order_ID))) {

			MInvoiceLine[] lines = this.getLines();

			if (lines.length <= 0 && this.getC_Order_ID() > 0) {

				MOrder order = new MOrder(getCtx(), this.getC_Order_ID(),
						get_TrxName());

				DB.executeUpdateEx(
						"DELETE FROM c_invoiceline WHERE c_invoice_id="
								+ this.get_ID(), get_TrxName());

				MOrderLine[] ordlines = order.getLines();

				for (MOrderLine line : ordlines) {

					MInvoiceLine l = new MInvoiceLine(getCtx(), 0,
							get_TrxName()); // instancio nueva linea

					// seteo campos
					l.setC_Invoice_ID(this.get_ID());
					l.setQty(line.getQtyEntered());

					MProduct product = MProduct.get(Env.getCtx(),
							line.getM_Product_ID());

					BigDecimal QtyInvoiced = null;
					if (line.getM_Product_ID() > 0
							&& product.getC_UOM_ID() != line.getC_UOM_ID()) {
						QtyInvoiced = MUOMConversion.convertProductFrom(
								Env.getCtx(), line.getM_Product_ID(),
								line.getC_UOM_ID(), line.getQtyEntered());
					}

					if (QtyInvoiced == null)
						QtyInvoiced = line.getQtyEntered();

					l.setQtyInvoiced(QtyInvoiced);
					l.setQtyEntered(line.getQtyEntered());
					l.setM_Product_ID(line.getM_Product_ID());
					l.setPriceList(line.getPriceList());
					l.setPriceEntered(line.getPriceEntered());
					l.setPriceActual(line.getPriceActual());
					l.setPriceLimit(line.getPriceLimit());
					l.setLineNetAmt(line.getLineNetAmt());
					l.setC_UOM_ID(line.getC_UOM_ID());
					l.setTaxAmt(line.getTaxAmt());
					l.setC_Tax_ID(line.getC_Tax_ID());
					l.setLineTotalAmt(line.getLineTotalAmt());
					l.setFlatDiscount(line.getFlatDiscount());
					l.setC_OrderLine_ID(line.getC_OrderLine_ID());

					l.saveEx(); // guardo linea

				}

			}

		}

	}

	/**
	 * Set Price List (and Currency) when valid
	 * 
	 * @param M_PriceList_ID
	 *            price list
	 */
	@Override
	public void setM_PriceList_ID(int M_PriceList_ID) {
		MPriceList pl = MPriceList.get(getCtx(), M_PriceList_ID, null);
		if (pl != null) {
			setC_Currency_ID(pl.getC_Currency_ID());
			super.setM_PriceList_ID(M_PriceList_ID);
		}
	} // setM_PriceList_ID

	/**
	 * Get Allocated Amt in Invoice Currency
	 * 
	 * @return pos/neg amount or null
	 */
	public BigDecimal getAllocatedAmt() {
		BigDecimal retValue = null;
		String sql = "SELECT SUM(currencyConvert(al.Amount+al.DiscountAmt+al.WriteOffAmt,"
				+ "ah.C_Currency_ID, i.C_Currency_ID,ah.DateTrx,COALESCE(i.C_ConversionType_ID,0), al.AD_Client_ID,al.AD_Org_ID)) "
				+ "FROM C_AllocationLine al"
				+ " INNER JOIN C_AllocationHdr ah ON (al.C_AllocationHdr_ID=ah.C_AllocationHdr_ID)"
				+ " INNER JOIN C_Invoice i ON (al.C_Invoice_ID=i.C_Invoice_ID) "
				+ "WHERE al.C_Invoice_ID=?"
				+ " AND ah.IsActive='Y' AND al.IsActive='Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_Invoice_ID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				retValue = rs.getBigDecimal(1);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException e) {
			throw new DBException(e, sql);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// log.fine("getAllocatedAmt - " + retValue);
		// ? ROUND(NVL(v_AllocatedAmt,0), 2);
		return retValue;
	} // getAllocatedAmt

	/**
	 * Test Allocation (and set paid flag)
	 * 
	 * @return true if updated
	 */
	public boolean testAllocation() {
		boolean change = false;

		if (isProcessed()) {
			BigDecimal alloc = getAllocatedAmt(); // absolute
			if (alloc == null)
				alloc = Env.ZERO;
			BigDecimal total = getGrandTotal();
			if (!isSOTrx())
				total = total.negate();
			if (isCreditMemo())
				total = total.negate();
			boolean test = total.compareTo(alloc) == 0;
			change = test != isPaid();
			if (change)
				setIsPaid(test);
			log.fine("Paid=" + test + " (" + alloc + "=" + total + ")");
		}

		return change;
	} // testAllocation

	/**
	 * Set Paid Flag for invoices
	 * 
	 * @param ctx
	 *            context
	 * @param C_BPartner_ID
	 *            if 0 all
	 * @param trxName
	 *            transaction
	 */
	public static void setIsPaid(Properties ctx, int C_BPartner_ID,
			String trxName) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer(
				"IsPaid='N' AND DocStatus IN ('CO','CL')");
		if (C_BPartner_ID > 1) {
			whereClause.append(" AND C_BPartner_ID=?");
			params.add(C_BPartner_ID);
		} else {
			whereClause.append(" AND AD_Client_ID=?");
			params.add(Env.getAD_Client_ID(ctx));
		}

		POResultSet<MInvoice> rs = new Query(ctx, MInvoice.Table_Name,
				whereClause.toString(), trxName).setParameters(params).scroll();
		int counter = 0;
		try {
			while (rs.hasNext()) {
				MInvoice invoice = rs.next();
				if (invoice.testAllocation())
					if (invoice.save())
						counter++;
			}
		} finally {
			DB.close(rs);
		}
		s_log.config("#" + counter);
		/**/
	} // setIsPaid

	/**
	 * Get Open Amount. Used by web interface
	 * 
	 * @return Open Amt
	 */
	public BigDecimal getOpenAmt() {
		return getOpenAmt(true, null);
	} // getOpenAmt

	/**
	 * Get Open Amount
	 * 
	 * @param creditMemoAdjusted
	 *            adjusted for CM (negative)
	 * @param paymentDate
	 *            ignored Payment Date
	 * @return Open Amt
	 */
	public BigDecimal getOpenAmt(boolean creditMemoAdjusted,
			Timestamp paymentDate) {
		if (isPaid())
			return Env.ZERO;
		//
		if (m_openAmt == null) {
			m_openAmt = getGrandTotal();
			if (paymentDate != null) {
				// Payment Discount
				// Payment Schedule
			}
			BigDecimal allocated = getAllocatedAmt();
			if (allocated != null) {
				allocated = allocated.abs(); // is absolute
				m_openAmt = m_openAmt.subtract(allocated);
			}
		}
		//
		if (!creditMemoAdjusted)
			return m_openAmt;
		if (isCreditMemo())
			return m_openAmt.negate();
		return m_openAmt;
	} // getOpenAmt

	/**
	 * Get Document Status
	 * 
	 * @return Document Status Clear Text
	 */
	public String getDocStatusName() {
		return MRefList.getListName(getCtx(), 131, getDocStatus());
	} // getDocStatusName

	/**************************************************************************
	 * Create PDF
	 * 
	 * @return File or null
	 */
	public File createPDF() {
		try {
			File temp = File.createTempFile(get_TableName() + get_ID() + "_",
					".pdf");
			return createPDF(temp);
		} catch (Exception e) {
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	} // getPDF

	/**
	 * Create PDF file
	 * 
	 * @param file
	 *            output file
	 * @return file if success
	 */
	public File createPDF(File file) {
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.INVOICE,
				getC_Invoice_ID(), get_TrxName());
		if (re == null)
			return null;
		return re.getPDF(file);
	} // createPDF

	/**
	 * Get PDF File Name
	 * 
	 * @param documentDir
	 *            directory
	 * @return file name
	 */
	public String getPDFFileName(String documentDir) {
		return getPDFFileName(documentDir, getC_Invoice_ID());
	} // getPDFFileName

	/**
	 * Get ISO Code of Currency
	 * 
	 * @return Currency ISO
	 */
	public String getCurrencyISO() {
		return MCurrency.getISO_Code(getCtx(), getC_Currency_ID());
	} // getCurrencyISO

	/**
	 * Get Currency Precision
	 * 
	 * @return precision
	 */
	public int getPrecision() {
		return MCurrency.getStdPrecision(getCtx(), getC_Currency_ID());
	} // getPrecision

	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction
	 *            document action
	 * @return true if performed
	 */
	public boolean processIt(String processAction) {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // process

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	} // unlockIt

	/**
	 * Invalidate Document
	 * 
	 * @return true if success
	 */
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	/**
	 * Prepare Document
	 * 
	 * @return new status (In Progress or Invalid)
	 */
	public String prepareIt() {
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(),
				getC_DocTypeTarget_ID(), getAD_Org_ID());

		// OpenUp. Gabriel Vila. 11/11/2014. Issue #3205
		MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(), null);
		// Fin OpenUp. Issue #3205
		
		// Lines
		// si tengo ordenid borro las lines no seleccionadas con getttrxname
		if (this.getUY_ManufOrder_ID() > 0) {

			deleteLinesNotSelected();
		}
		MInvoiceLine[] lines = getLines(true);
		if (lines.length == 0) {
			
			// OpenUp. Gabriel Vila. 11/11/2014. Issue #3205
			// Para factura vale flete en transporte no controlo lineas.
			// Comento y sustituyo

			/*
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
			*/
			
			boolean notValid = true;
			if (doc.getValue() != null){
				if (doc.getValue().equalsIgnoreCase("inv_valeflete")){
					notValid = false;
				}
			}
			
			if (notValid){
				m_processMsg = "@NoLines@";
				return DocAction.STATUS_Invalid;
			}

			// Fin OpenUp. Issue #3205
			
		}
		// No Cash Book
		if (PAYMENTRULE_Cash.equals(getPaymentRule())
				&& MCashBook.get(getCtx(), getAD_Org_ID(), getC_Currency_ID()) == null) {
			m_processMsg = "@NoCashBook@";
			return DocAction.STATUS_Invalid;
		}

		// Convert/Check DocType
		if (getC_DocType_ID() != getC_DocTypeTarget_ID())
			setC_DocType_ID(getC_DocTypeTarget_ID());
		if (getC_DocType_ID() == 0) {
			m_processMsg = "No Document Type";
			return DocAction.STATUS_Invalid;
		}

		explodeBOM();
		
		// OpenUp. Gabriel Vila. 13/11/2014. Issue #3205
		// Para factura de vale fletes en empresas de transporte el iva se ingresa manual en el cabezal y por lo tanto no se considera las lineas
		// Condiciono el calculo de impuestos original
		boolean calculateTaxesFromLines = true;
		if (doc.getValue() != null){
			if (doc.getValue().equalsIgnoreCase("inv_valeflete")){
				calculateTaxesFromLines = false;				
				MTax basico = MTax.forValue(getCtx(), "basico", null);
				BigDecimal taxBaseAmount = this.getTotalLines();
				BigDecimal taxAmount = this.getGrandTotal().subtract(this.getTotalLines());
				
				MInvoiceTax taxInv = new MInvoiceTax(getCtx(), 0, get_TrxName());
				taxInv.setC_Invoice_ID(this.get_ID());
				taxInv.setC_Tax_ID(basico.get_ID());
				taxInv.setTaxBaseAmt(taxBaseAmount);
				taxInv.setTaxAmt(taxAmount);
				taxInv.setIsTaxIncluded(false);
				taxInv.saveEx();
			}
		}
		
		if (calculateTaxesFromLines){ // Fin OpenUp. Issue #3205

			if (!calculateTaxTotal()) // setTotals
			{
				m_processMsg = "Error calculating Tax";
				return DocAction.STATUS_Invalid;
			}

		}
		
		createPaySchedule();

		// Credit Status
		if (isSOTrx() && !isReversal()) {
			MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(), null);
			if (MBPartner.SOCREDITSTATUS_CreditStop.equals(bp
					.getSOCreditStatus())) {
				m_processMsg = "@BPartnerCreditStop@ - @TotalOpenBalance@="
						+ bp.getTotalOpenBalance() + ", @SO_CreditLimit@="
						+ bp.getSO_CreditLimit();
				return DocAction.STATUS_Invalid;
			}
		}

		// Landed Costs
		if (!isSOTrx()) {
			for (int i = 0; i < lines.length; i++) {
				MInvoiceLine line = lines[i];
				String error = line.allocateLandedCosts();
				if (error != null && error.length() > 0) {
					m_processMsg = error;
					return DocAction.STATUS_Invalid;
				}
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Add up Amounts
		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	} // prepareIt

	/**
	 * 
	 * OpenUp. Descripcion : Metodo que elimina las lineas no seleccionadas para
	 * facturar cuando es Factura Venta
	 * 
	 * @author Nicolas Sarlabos Fecha : 20/11/2012
	 */
	private void deleteLinesNotSelected() {

		try {
			String action = "delete from c_invoiceline "
					+ " where c_invoice_id =" + this.get_ID()
					+ " and isselected = 'N' ";
			DB.executeUpdateEx(action, get_TrxName());

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/**
	 * Explode non stocked BOM.
	 */
	private void explodeBOM() {
		String where = "AND IsActive='Y' AND EXISTS "
				+ "(SELECT * FROM M_Product p WHERE C_InvoiceLine.M_Product_ID=p.M_Product_ID"
				+ " AND	p.IsBOM='Y' AND p.IsVerified='Y' AND p.IsStocked='N')";
		//
		String sql = "SELECT COUNT(*) FROM C_InvoiceLine "
				+ "WHERE C_Invoice_ID=? " + where;
		int count = DB.getSQLValueEx(get_TrxName(), sql, getC_Invoice_ID());
		while (count != 0) {
			renumberLines(100);

			// Order Lines with non-stocked BOMs
			MInvoiceLine[] lines = getLines(where);
			for (int i = 0; i < lines.length; i++) {
				MInvoiceLine line = lines[i];
				MProduct product = MProduct.get(getCtx(),
						line.getM_Product_ID());
				log.fine(product.getName());
				// New Lines
				int lineNo = line.getLine();

				// find default BOM with valid dates and to this product
				MPPProductBOM bom = MPPProductBOM.get(product, getAD_Org_ID(),
						getDateInvoiced(), get_TrxName());
				if (bom != null) {
					MPPProductBOMLine[] bomlines = bom
							.getLines(getDateInvoiced());
					for (int j = 0; j < bomlines.length; j++) {
						MPPProductBOMLine bomline = bomlines[j];
						MInvoiceLine newLine = new MInvoiceLine(this);
						newLine.setLine(++lineNo);
						newLine.setM_Product_ID(bomline.getM_Product_ID());
						newLine.setC_UOM_ID(bomline.getC_UOM_ID());
						newLine.setQty(line.getQtyInvoiced().multiply(
								bomline.getQtyBOM())); // Invoiced/Entered
						if (bomline.getDescription() != null)
							newLine.setDescription(bomline.getDescription());
						//
						newLine.setPrice();
						newLine.saveEx(get_TrxName());
					}
				}

				/*
				 * MProductBOM[] boms = MProductBOM.getBOMLines (product); for
				 * (int j = 0; j < boms.length; j++) { MProductBOM bom =
				 * boms[j]; MInvoiceLine newLine = new MInvoiceLine (this);
				 * newLine.setLine (++lineNo); newLine.setM_Product_ID
				 * (bom.getProduct().getM_Product_ID(),
				 * bom.getProduct().getC_UOM_ID()); newLine.setQty
				 * (line.getQtyInvoiced().multiply( bom.getBOMQty ())); //
				 * Invoiced/Entered if (bom.getDescription () != null)
				 * newLine.setDescription (bom.getDescription ()); //
				 * newLine.setPrice (); newLine.save (get_TrxName()); }
				 */

				// Convert into Comment Line
				line.setM_Product_ID(0);
				line.setM_AttributeSetInstance_ID(0);
				line.setPriceEntered(Env.ZERO);
				line.setPriceActual(Env.ZERO);
				line.setPriceLimit(Env.ZERO);
				line.setPriceList(Env.ZERO);
				line.setLineNetAmt(Env.ZERO);
				//
				String description = product.getName();
				if (product.getDescription() != null)
					description += " " + product.getDescription();
				if (line.getDescription() != null)
					description += " " + line.getDescription();
				line.setDescription(description);
				line.saveEx(get_TrxName());
			} // for all lines with BOM

			m_lines = null;
			count = DB.getSQLValue(get_TrxName(), sql, getC_Invoice_ID());
			renumberLines(10);
		} // while count != 0
	} // explodeBOM

	/**
	 * Calculate Tax and Total
	 * 
	 * @return true if calculated
	 */
	public boolean calculateTaxTotal() {
		log.fine("");
		// Delete Taxes
		DB.executeUpdateEx("DELETE C_InvoiceTax WHERE C_Invoice_ID="
				+ getC_Invoice_ID(), get_TrxName());
		m_taxes = null;

		// Lines
		BigDecimal totalLines = Env.ZERO;
		ArrayList<Integer> taxList = new ArrayList<Integer>();
		MInvoiceLine[] lines = getLines(false);
		for (int i = 0; i < lines.length; i++) {
			MInvoiceLine line = lines[i];
			if (!taxList.contains(line.getC_Tax_ID())) {
				MInvoiceTax iTax = MInvoiceTax.get(line, getPrecision(), false,
						get_TrxName()); // current Tax
				if (iTax != null) {
					iTax.setIsTaxIncluded(isTaxIncluded());
					if (!iTax.calculateTaxFromLines())
						return false;
					iTax.saveEx();
					taxList.add(line.getC_Tax_ID());
				}
			}
			totalLines = totalLines.add(line.getLineNetAmt());
		}

		// Taxes
		BigDecimal grandTotal = totalLines;
		MInvoiceTax[] taxes = getTaxes(true);
		for (int i = 0; i < taxes.length; i++) {
			MInvoiceTax iTax = taxes[i];
			MTax tax = iTax.getTax();
			if (tax.isSummary()) {
				MTax[] cTaxes = tax.getChildTaxes(false); // Multiple taxes
				for (int j = 0; j < cTaxes.length; j++) {
					MTax cTax = cTaxes[j];
					BigDecimal taxAmt = cTax.calculateTax(iTax.getTaxBaseAmt(),
							isTaxIncluded(), getPrecision());
					//
					MInvoiceTax newITax = new MInvoiceTax(getCtx(), 0,
							get_TrxName());
					newITax.setClientOrg(this);
					newITax.setC_Invoice_ID(getC_Invoice_ID());
					newITax.setC_Tax_ID(cTax.getC_Tax_ID());
					newITax.setPrecision(getPrecision());
					newITax.setIsTaxIncluded(isTaxIncluded());
					newITax.setTaxBaseAmt(iTax.getTaxBaseAmt());
					newITax.setTaxAmt(taxAmt);
					newITax.saveEx(get_TrxName());
					//
					if (!isTaxIncluded())
						grandTotal = grandTotal.add(taxAmt);
				}
				iTax.deleteEx(true, get_TrxName());
			} else {
				if (!isTaxIncluded())
					grandTotal = grandTotal.add(iTax.getTaxAmt());
			}
		}
		//

		// OpenUp. Nicolas Sarlabos. 26/12/2012. Si hay porcentaje de descuento
		// global me aseguro de setear bien el subtotal y total del comprobante
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false,
				this.getAD_Client_ID())) {

			if (isSOTrx()) {

				if (this.getDiscount().compareTo(Env.ZERO) > 0) {

					int StdPrecision = getPrecision();
					BigDecimal discount = this.getDiscount().divide(
							Env.ONEHUNDRED);
					discount = (totalLines.multiply(discount)).setScale(
							StdPrecision, BigDecimal.ROUND_HALF_UP);
					// aplico descuento global al total de lineas y actualizo
					BigDecimal totLines = (totalLines.subtract(discount))
							.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					// DB.executeUpdateEx("UPDATE c_invoice set totallines=" +
					// totalLines + " WHERE c_invoice_id=" + hdr.get_ID() ,
					// get_TrxName());
					// obtengo nuevo importe de impuesto, aplico y actualizo en
					// TOTAL
					for (int i = 0; i < taxes.length; i++) {

						if (!isTaxIncluded()) {

							MTax tax = MTax.get(getCtx(),
									taxes[i].getC_Tax_ID());
							BigDecimal TaxAmt = (tax.calculateTax(totLines,
									isTaxIncluded(), getPrecision())).setScale(
									StdPrecision, BigDecimal.ROUND_HALF_UP);
							grandTotal = (totLines.add(TaxAmt)).setScale(
									StdPrecision, BigDecimal.ROUND_HALF_UP);
							// DB.executeUpdateEx("UPDATE c_invoice set grandtotal="
							// + grandTotal + " WHERE c_invoice_id=" +
							// hdr.get_ID() , get_TrxName());
							BigDecimal finalBaseAmt = (totalLines
									.subtract(discount)).setScale(StdPrecision,
									BigDecimal.ROUND_HALF_UP); // OpenUp.
																// Nicolas
																// Sarlabos.
																// 28/02/2013.
																// Obtengo
																// importe base
																// luego del
																// descuento al
																// pie.
							// actualizo valor del impuesto
							DB.executeUpdateEx(
									"UPDATE c_invoicetax set taxamt=" + TaxAmt
											+ " ,taxbaseamt=" + finalBaseAmt
											+ " WHERE c_invoice_id="
											+ this.get_ID(), get_TrxName());

						}

					}

				}
			}
		}
		// Fin OpenUp.

		setTotalLines(totalLines);
		setGrandTotal(grandTotal);
		
		// OpenUp. Gabriel Vila. 29/10/2015. Issue #4998
		// Si tengo system configurator que determina precisión decimal del total para facturas de venta
		if (MSysConfig.getBooleanValue("UY_ARI_ROUND_GRANDTOTAL", false, this.getAD_Client_ID())){
			if (this.isSOTrx()){
				setGrandTotal(grandTotal.setScale(0, RoundingMode.HALF_UP));					
			}
		}
		// Fin OpenUp. Issue #4998

		return true;
	} // calculateTaxTotal

	/**
	 * (Re) Create Pay Schedule
	 * 
	 * @return true if valid schedule
	 */
	private boolean createPaySchedule() {
		if (getC_PaymentTerm_ID() == 0)
			return false;
		MPaymentTerm pt = new MPaymentTerm(getCtx(), getC_PaymentTerm_ID(),
				null);
		log.fine(pt.toString());
		return pt.apply(this); // calls validate pay schedule
	} // createPaySchedule

	
	

	/* 
	 * OpenUp Ltda. Issue # 2301
	 * @author Matías Pérez - 28/11/2014
	 * @see
	 * @return
	 */
	// OpenUp M.R. Issue#755 Se agrega linea que deja la accion del
	// documento en completar despues de aprobar el documento.
	
	public boolean approveIt() {
		log.info(toString());
		setIsApproved(true);
		
		this.set_Value("DateApproved", new Timestamp(System.currentTimeMillis()));
		
		this.set_Value("Approvedby", Env.getAD_User_ID(Env.getCtx()));
		
		this.set_Value("ApprovedType", "AUTORIZADO");
	
		this.setDocStatus(DocumentEngine.STATUS_Approved);
		this.setDocAction(DocAction.ACTION_Complete);
		
		return true;
	}//Fin OpenUp

	/* 
	 * OpenUp Ltda. Issue # 2301
	 * @author Matías Pérez - 28/11/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {
		
		log.info(toString());
		setIsApproved(false);
		
		this.set_Value("DateApproved", new Timestamp(System.currentTimeMillis()));
		this.set_Value("Approvedby", Env.getAD_User_ID(Env.getCtx()));
		this.set_Value("ApprovedType", "RECHAZADO");
		this.setDocStatus(DocumentEngine.STATUS_NotApproved);
		this.setDocAction(DocAction.ACTION_Request);

		return true;
			
	
	} // rejectIt
	//Fin OpenUp

	/**
	 * Complete Document
	 * 
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt() {

		// OpenUp. Gabriel Vila. 22/02/2012. Migracion
		// Completo segun modelo de OpenUp. Comento lineas originales.
		return this.completeItOpenUp();

		/*
		 * 
		 * // Re-Check if (!m_justPrepared) { String status = prepareIt(); if
		 * (!DocAction.STATUS_InProgress.equals(status)) return status; }
		 * 
		 * m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
		 * ModelValidator.TIMING_BEFORE_COMPLETE); if (m_processMsg != null)
		 * return DocAction.STATUS_Invalid;
		 * 
		 * // Implicit Approval if (!isApproved()) approveIt();
		 * log.info(toString()); StringBuffer info = new StringBuffer();
		 * 
		 * // POS supports multiple payments boolean fromPOS = false; if (
		 * getC_Order_ID() > 0 ) { fromPOS = getC_Order().getC_POS_ID() > 0; }
		 * 
		 * // Create Cash if (PAYMENTRULE_Cash.equals(getPaymentRule()) &&
		 * !fromPOS ) { // Modifications for POSterita // // MCash cash =
		 * MCash.get (getCtx(), getAD_Org_ID(), // getDateInvoiced(),
		 * getC_Currency_ID(), get_TrxName());
		 * 
		 * MCash cash;
		 * 
		 * int posId = Env.getContextAsInt(getCtx(),Env.POS_ID);
		 * 
		 * if (posId != 0) { MPOS pos = new MPOS(getCtx(),posId,get_TrxName());
		 * int cashBookId = pos.getC_CashBook_ID(); cash =
		 * MCash.get(getCtx(),cashBookId,getDateInvoiced(),get_TrxName()); }
		 * else { cash = MCash.get (getCtx(), getAD_Org_ID(), getDateInvoiced(),
		 * getC_Currency_ID(), get_TrxName()); }
		 * 
		 * // End Posterita Modifications
		 * 
		 * if (cash == null || cash.get_ID() == 0) { m_processMsg =
		 * "@NoCashBook@"; return DocAction.STATUS_Invalid; } MCashLine cl = new
		 * MCashLine (cash); cl.setInvoice(this); if (!cl.save(get_TrxName())) {
		 * m_processMsg = "Could not save Cash Journal Line"; return
		 * DocAction.STATUS_Invalid; } info.append("@C_Cash_ID@: " +
		 * cash.getName() + " #" + cl.getLine());
		 * setC_CashLine_ID(cl.getC_CashLine_ID()); } // CashBook
		 * 
		 * // Update Order & Match int matchInv = 0; int matchPO = 0;
		 * MInvoiceLine[] lines = getLines(false); for (int i = 0; i <
		 * lines.length; i++) { MInvoiceLine line = lines[i];
		 * 
		 * // Update Order Line MOrderLine ol = null; if
		 * (line.getC_OrderLine_ID() != 0) { if (isSOTrx() ||
		 * line.getM_Product_ID() == 0) { ol = new MOrderLine (getCtx(),
		 * line.getC_OrderLine_ID(), get_TrxName()); if (line.getQtyInvoiced()
		 * != null)
		 * ol.setQtyInvoiced(ol.getQtyInvoiced().add(line.getQtyInvoiced())); if
		 * (!ol.save(get_TrxName())) { m_processMsg =
		 * "Could not update Order Line"; return DocAction.STATUS_Invalid; } }
		 * // Order Invoiced Qty updated via Matching Inv-PO else if (!isSOTrx()
		 * && line.getM_Product_ID() != 0 && !isReversal()) { // MatchPO is
		 * created also from MInOut when Invoice exists before Shipment
		 * BigDecimal matchQty = line.getQtyInvoiced(); MMatchPO po =
		 * MMatchPO.create (line, null, getDateInvoiced(), matchQty); boolean
		 * isNewMatchPO = false; if (po.get_ID() == 0) isNewMatchPO = true; if
		 * (!po.save(get_TrxName())) { m_processMsg =
		 * "Could not create PO Matching"; return DocAction.STATUS_Invalid; }
		 * matchPO++; if (isNewMatchPO) addDocsPostProcess(po); } }
		 * 
		 * //Update QtyInvoiced RMA Line if (line.getM_RMALine_ID() != 0) {
		 * MRMALine rmaLine = new MRMALine (getCtx(),line.getM_RMALine_ID(),
		 * get_TrxName()); if (rmaLine.getQtyInvoiced() != null)
		 * rmaLine.setQtyInvoiced
		 * (rmaLine.getQtyInvoiced().add(line.getQtyInvoiced())); else
		 * rmaLine.setQtyInvoiced(line.getQtyInvoiced()); if
		 * (!rmaLine.save(get_TrxName())) { m_processMsg =
		 * "Could not update RMA Line"; return DocAction.STATUS_Invalid; } } //
		 * 
		 * // Matching - Inv-Shipment if (!isSOTrx() && line.getM_InOutLine_ID()
		 * != 0 && line.getM_Product_ID() != 0 && !isReversal()) { MInOutLine
		 * receiptLine = new MInOutLine (getCtx(),line.getM_InOutLine_ID(),
		 * get_TrxName()); BigDecimal matchQty = line.getQtyInvoiced();
		 * 
		 * if (receiptLine.getMovementQty().compareTo(matchQty) < 0) matchQty =
		 * receiptLine.getMovementQty();
		 * 
		 * MMatchInv inv = new MMatchInv(line, getDateInvoiced(), matchQty);
		 * boolean isNewMatchInv = false; if (inv.get_ID() == 0) isNewMatchInv =
		 * true; if (!inv.save(get_TrxName())) { m_processMsg =
		 * CLogger.retrieveErrorString("Could not create Invoice Matching");
		 * return DocAction.STATUS_Invalid; } matchInv++; if (isNewMatchInv)
		 * addDocsPostProcess(inv); } } // for all lines if (matchInv > 0)
		 * info.append(" @M_MatchInv_ID@#").append(matchInv).append(" "); if
		 * (matchPO > 0)
		 * info.append(" @M_MatchPO_ID@#").append(matchPO).append(" ");
		 * 
		 * 
		 * 
		 * // Update BP Statistics MBPartner bp = new MBPartner (getCtx(),
		 * getC_BPartner_ID(), get_TrxName()); // Update total revenue and
		 * balance / credit limit (reversed on AllocationLine.processIt)
		 * BigDecimal invAmt = MConversionRate.convertBase(getCtx(),
		 * getGrandTotal(true), // CM adjusted getC_Currency_ID(),
		 * getDateAcct(), getC_ConversionType_ID(), getAD_Client_ID(),
		 * getAD_Org_ID()); if (invAmt == null) { m_processMsg =
		 * "Could not convert C_Currency_ID=" + getC_Currency_ID() +
		 * " to base C_Currency_ID=" +
		 * MClient.get(Env.getCtx()).getC_Currency_ID(); return
		 * DocAction.STATUS_Invalid; } // Total Balance BigDecimal newBalance =
		 * bp.getTotalOpenBalance(false); if (newBalance == null) newBalance =
		 * Env.ZERO; if (isSOTrx()) { newBalance = newBalance.add(invAmt); // if
		 * (bp.getFirstSale() == null) bp.setFirstSale(getDateInvoiced());
		 * BigDecimal newLifeAmt = bp.getActualLifeTimeValue(); if (newLifeAmt
		 * == null) newLifeAmt = invAmt; else newLifeAmt =
		 * newLifeAmt.add(invAmt); BigDecimal newCreditAmt =
		 * bp.getSO_CreditUsed(); if (newCreditAmt == null) newCreditAmt =
		 * invAmt; else newCreditAmt = newCreditAmt.add(invAmt); //
		 * log.fine("GrandTotal=" + getGrandTotal(true) + "(" + invAmt +
		 * ") BP Life=" + bp.getActualLifeTimeValue() + "->" + newLifeAmt +
		 * ", Credit=" + bp.getSO_CreditUsed() + "->" + newCreditAmt +
		 * ", Balance=" + bp.getTotalOpenBalance(false) + " -> " + newBalance);
		 * bp.setActualLifeTimeValue(newLifeAmt);
		 * bp.setSO_CreditUsed(newCreditAmt); } // SO else { newBalance =
		 * newBalance.subtract(invAmt); log.fine("GrandTotal=" +
		 * getGrandTotal(true) + "(" + invAmt + ") Balance=" +
		 * bp.getTotalOpenBalance(false) + " -> " + newBalance); }
		 * bp.setTotalOpenBalance(newBalance); bp.setSOCreditStatus(); if
		 * (!bp.save(get_TrxName())) { m_processMsg =
		 * "Could not update Business Partner"; return DocAction.STATUS_Invalid;
		 * }
		 * 
		 * // User - Last Result/Contact if (getAD_User_ID() != 0) { MUser user
		 * = new MUser (getCtx(), getAD_User_ID(), get_TrxName());
		 * user.setLastContact(new Timestamp(System.currentTimeMillis()));
		 * user.setLastResult(Msg.translate(getCtx(), "C_Invoice_ID") + ": " +
		 * getDocumentNo()); if (!user.save(get_TrxName())) { m_processMsg =
		 * "Could not update Business Partner User"; return
		 * DocAction.STATUS_Invalid; } } // user
		 * 
		 * // Update Project if (isSOTrx() && getC_Project_ID() != 0) { MProject
		 * project = new MProject (getCtx(), getC_Project_ID(), get_TrxName());
		 * BigDecimal amt = getGrandTotal(true); int C_CurrencyTo_ID =
		 * project.getC_Currency_ID(); if (C_CurrencyTo_ID !=
		 * getC_Currency_ID()) amt = MConversionRate.convert(getCtx(), amt,
		 * getC_Currency_ID(), C_CurrencyTo_ID, getDateAcct(), 0,
		 * getAD_Client_ID(), getAD_Org_ID()); if (amt == null) { m_processMsg =
		 * "Could not convert C_Currency_ID=" + getC_Currency_ID() +
		 * " to Project C_Currency_ID=" + C_CurrencyTo_ID; return
		 * DocAction.STATUS_Invalid; } BigDecimal newAmt =
		 * project.getInvoicedAmt(); if (newAmt == null) newAmt = amt; else
		 * newAmt = newAmt.add(amt); log.fine("GrandTotal=" +
		 * getGrandTotal(true) + "(" + amt + ") Project " + project.getName() +
		 * " - Invoiced=" + project.getInvoicedAmt() + "->" + newAmt);
		 * project.setInvoicedAmt(newAmt); if (!project.save(get_TrxName())) {
		 * m_processMsg = "Could not update Project"; return
		 * DocAction.STATUS_Invalid; } } // project
		 * 
		 * // User Validation String valid =
		 * ModelValidationEngine.get().fireDocValidate(this,
		 * ModelValidator.TIMING_AFTER_COMPLETE); if (valid != null) {
		 * m_processMsg = valid; return DocAction.STATUS_Invalid; }
		 * 
		 * // Set the definite document number after completed (if needed)
		 * setDefiniteDocumentNo();
		 * 
		 * // Counter Documents MInvoice counter = createCounterDoc(); if
		 * (counter != null)
		 * info.append(" - @CounterDoc@: @C_Invoice_ID@=").append
		 * (counter.getDocumentNo());
		 * 
		 * m_processMsg = info.toString().trim(); setProcessed(true);
		 * setDocAction(DOCACTION_Close); return DocAction.STATUS_Completed;
		 */
		// Fin OpenUp.

	} // completeIt

	/**
	 * Completo documento segun modelo de OpenUp. OpenUp Ltda. Issue #
	 * migracion.
	 * 
	 * @author Gabriel Vila - 22/02/2012
	 * @see
	 * @return String. Nuevo estado del documento.
	 */
	private String completeItOpenUp() {

		// Re-Check
		if (!m_justPrepared) {
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Elimina los uy_sum_accountstatus para que no se repitan en los reportes
//		MSUMAccountStatus.deleteDocumentNo(this.get_ID(), this.getDocumentNo(), get_TrxName());
		
		MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(),null);

		// Verifico periodo contable para documento
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(),
				this.getC_DocTypeTarget_ID(), this.getAD_Org_ID());

		// Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());

		// OpenUp. Gabriel Vila. 26/12/2012.
		// Controlo numero de documento no duplicado para compras
		if (!this.isSOTrx()) {
			this.validateDocumentNo();
		}
		// Fin OpenUp.
		
		// OpenUp. Matias Carbajal. 04/11/2014. Issue #3105
		// Para documentos de Facturas de Vale Flete debo hacer algunas
		// validaciones.
		if ((doc.getValue() != null)
				&& (doc.getValue().equalsIgnoreCase("inv_valeflete"))) {
			String message = this.validateInvValeFlete();
			if (message != null) {
				this.m_processMsg = message;
				return DocAction.STATUS_Invalid;
			}
		}
		// Fin OpenUp. Issue #3105

		// OpenUp. Gabriel Vila. 26/10/2012. Issue #49.
		// Gestion de Provision de Gastos.

		boolean hasProvLines = true;
		boolean handleProvision = MSysConfig.getBooleanValue(
				"UY_HANDLE_PROVISION", false, this.getAD_Client_ID());

		if (handleProvision) {

			// Elimino provisiones que no fueron afectadas en este comprobante.
			this.deleteProvisionNotAllocated();
			// Verifico que no haya variado el saldo pendiente de las
			// provisiones
			this.verifyProvisionesAmtOpen();

			// Me cubro del error de que las lineas de factura quedan marcadas
			// como provisionadas cuando no hay lineas de provisiones
			// Obtengo lineas de provision de este comprobante
			String whereClause = "c_invoice_id =" + this.get_ID();
			List<MInvoiceProvision> invprovs = new Query(getCtx(),
					I_UY_Invoice_Provision.Table_Name, whereClause,
					get_TrxName()).list();
			if ((invprovs == null) || (invprovs.size() <= 0)) {
				hasProvLines = false;
			}

		}
		// Fin OpenUp. Issue #49.

		// Si tengo seteado generar recepcion automaticamente
		if (MSysConfig.getBooleanValue("UY_PO_INVOICE_GENERATE_RECEPTION",
				false, this.getAD_Client_ID())) {
			// Segun tipo de documento
			if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)) {
				this.generateMInOut(MDocType.forValue(getCtx(), "poreception",
						null));
			} else if (doc.getDocBaseType().equalsIgnoreCase(
					Doc.DOCTYPE_ARInvoice)) {
				this.generateMInOut(MDocType.forValue(getCtx(),
						"deliveryorder", null));
			} else if (doc.getDocBaseType().equalsIgnoreCase(
					Doc.DOCTYPE_ARCredit)) {
				// Solo para devoluciones contado
				if (doc.getValue() != null) {
					if (doc.getValue().equalsIgnoreCase("devcontado")) {
						this.generateMInOut(MDocType.forValue(getCtx(),
								"devol", null));
					}
				}
			}
		}

		// OpenUp. Gabriel Vila. 07/07/2014. Issue #1405.
		// Para facturas de compra en modulo de transporte.
		// Si estoy en una factura proveedor
		if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)) {

			// Si estoy en modulo de transporte
			if (MSysConfig.getBooleanValue("UY_IS_MODULO_TRANSPORTE", false,
					this.getAD_Client_ID())) {

				List<MTRInvoiceTire> lines = this.getInvoiceTires();
				if (lines != null) {
					for (MTRInvoiceTire line : lines) {

						MInvoiceLine invline = (MInvoiceLine) line
								.getC_InvoiceLine();

						MTRTire tire = new MTRTire(getCtx(), 0, get_TrxName());
						tire.setValue(line.getValue());
						tire.setPurchaseDate(this.getDateInvoiced());
						tire.setUY_TR_TireMark_ID(line.getUY_TR_TireMark_ID());
						tire.setC_BPartner_ID(this.getC_BPartner_ID());
						tire.setQtyKm(line.getQtyKmNew());
						tire.setQtyKm2(0);
						tire.setQtyKm3(line.getQtyKmRecauchu());
						tire.setQtyKm4(0);
						tire.setQtyKm5(0);
						tire.setQtyKm6(0);
						tire.setEstadoActual(X_UY_TR_Tire.ESTADOACTUAL_NUEVO);
						tire.setM_Warehouse_ID(this.getM_Warehouse_ID());
						tire.setUY_TR_TireMeasure_ID(line
								.getUY_TR_TireMeasure_ID());
						tire.setobservaciones(line.getTireModel());
						tire.setPrice(invline.getPriceActual());
						tire.setIsActive(false);
						tire.setC_Currency_ID(this.getC_Currency_ID());
						tire.setC_Invoice_ID(this.get_ID());
						tire.setC_InvoiceLine_ID(invline.get_ID());
						tire.setIsActive(true);
						tire.saveEx();
					}
				}
			}
		}
		// Fin OpenUp. Issue #1405.

		// Update Order
		MInvoiceLine[] lines = getLines(false);
		for (int i = 0; i < lines.length; i++) {
			MInvoiceLine line = lines[i];

			// OpenUp. Gabriel Vila. 26/10/2012. Issue #49.
			// Gestion de Provision de Gastos.
			if (handleProvision) {
				// Verifico que para esta linea el neto sea igual o mayor al
				// monto total de sus provisiones
				if ((hasProvLines) && (line.isProvisioned())) {
					if (line.getLineNetAmt().compareTo(
							line.getLineProvisionsSumAllocated()) < 0) {
						// throw new
						// AdempiereException("El neto de la linea no puede ser inferior a la suma de afectaciones de sus provisiones.");
					}
				}
			}
			// Fin OpenUp. Issue #49.

			// Update Order Line
			MOrderLine ol = null;
			if (line.getC_OrderLine_ID() != 0) {
				if (isSOTrx() || line.getM_Product_ID() == 0) {
					ol = new MOrderLine(getCtx(), line.getC_OrderLine_ID(),
							get_TrxName());
					if (line.getQtyInvoiced() != null)
						ol.setQtyInvoiced(ol.getQtyInvoiced().add(
								line.getQtyInvoiced()));
					if (!ol.save(get_TrxName())) {
						m_processMsg = "No se pudo actualizar linea de orden de venta al completar Factura.";
						return DocAction.STATUS_Invalid;
					}
				}
				// Order Invoiced Qty updated via Matching Inv-PO
				else if (!isSOTrx() && line.getM_Product_ID() != 0
						&& !isReversal()) {
					// MatchPO is created also from MInOut when Invoice exists
					// before Shipment
					BigDecimal matchQty = line.getQtyInvoiced();
					MMatchPO po = MMatchPO.create(line, null,
							getDateInvoiced(), matchQty);
					boolean isNewMatchPO = false;
					if (po.get_ID() == 0)
						isNewMatchPO = true;
					if (!po.save(get_TrxName())) {
						m_processMsg = "No fue posible crear nueva asociacion de compra (PO Matching) al completar Factura.";
						return DocAction.STATUS_Invalid;
					}
					if (isNewMatchPO)
						addDocsPostProcess(po);
				}
			}

			// Matching - Inv-Shipment
			if (!isSOTrx() && line.getM_InOutLine_ID() != 0
					&& line.getM_Product_ID() != 0 && !isReversal()) {
				MInOutLine receiptLine = new MInOutLine(getCtx(),
						line.getM_InOutLine_ID(), get_TrxName());
				BigDecimal matchQty = line.getQtyInvoiced();

				if (receiptLine.getMovementQty().compareTo(matchQty) < 0)
					matchQty = receiptLine.getMovementQty();

				MMatchInv inv = new MMatchInv(line, getDateInvoiced(), matchQty);
				boolean isNewMatchInv = false;
				if (inv.get_ID() == 0)
					isNewMatchInv = true;
				if (!inv.save(get_TrxName())) {
					m_processMsg = "No fue posible guardar asociacion de factura (INV Matching) al completar Factura.";
					return DocAction.STATUS_Invalid;
				}
				if (isNewMatchInv)
					addDocsPostProcess(inv);
			}

			// OpenUp. Nicolas Sarlabos. 20/11/2012
			// si es una Factura Venta llamo a metodo para actualizar la linea
			// de la orden de fabricacion
			if (this.getUY_ManufOrder_ID() > 0)
				updateManufLine(line);
			// Fin OpenUp.

			// OpenUp. Guillermo Brust. 30/04/2013. ISSUE #787.
			// Si es una Nota de credito que tiene una linea con orden de
			// fabricacion asociada, se debe aumentar la cantidad
			// en las lineas de la orden de fabricacion y dejar pendiente la
			// misma. De esta manera se podra volver a utilizar en otra
			// factura pero solo por la cantidad que resta por facturar. Para
			// esto se utiliza el metodo processNCManufOrder.
			if (doc.getValue() != null) {
				if (doc.getValue().equalsIgnoreCase("customernc")) {
					if (line.getUY_ManufOrder_ID() > 0) {
						this.processNCManufOrder(line);
					}
				} else if (doc.getValue().equalsIgnoreCase("freightinvoice") || doc.getValue().equalsIgnoreCase("et-ticket")){// OpenUp. Nicolas Sarlabos. 16/11/2015. ISSUE #5054.
					
					MProduct prod = (MProduct)line.getM_Product();
					
					BigDecimal natAmt = (BigDecimal)line.get_Value("NationalAmt");
					BigDecimal intAmt = (BigDecimal)line.get_Value("InterNationalAmt");
					
					if(natAmt == null) natAmt = Env.ZERO;
					if(intAmt == null) intAmt = Env.ZERO;
					
					if(natAmt.compareTo(Env.ZERO)<=0 && intAmt.compareTo(Env.ZERO)<=0) 
						throw new AdempiereException("Debe indicar monto nacional o internacional para el producto '" + prod.getName() + "'");			
					
				}
				//Fin OpenUp.
			}
			
			
			// OpenUp. Gabriel Vila. 25/02/2016. Issue #5519
			// Para compras, actualizo datos de ultima factura en ficha de producto
			if (!this.isSOTrx()){
				if(line.getM_Product_ID()!=0){//SBT Issue #5750 Se controla para los casos que se permiten facturas solo con cargo
					MBPartnerProduct bpp = MBPartnerProduct.forBPProduct(getCtx(), this.getC_BPartner_ID(), line.getM_Product_ID(), get_TrxName());
					if (bpp == null){
						bpp = new MBPartnerProduct(getCtx(), 0, get_TrxName());
						bpp.setC_BPartner_ID(this.getC_BPartner_ID());
						bpp.setM_Product_ID(line.getM_Product_ID());
					}
					bpp.setC_Invoice_ID(this.get_ID());
					bpp.setDateInvoiced(this.getDateVendor());
					bpp.setPriceInvoiced(line.getPriceActual());
					if(line.get_Value("VendorProductNo") != null){
						bpp.set_Value("VendorProductNo", line.get_Value("VendorProductNo"));
					}
					bpp.saveEx();
				}	
			}
			// Fin OpenUp. Issue #5519
			

		} // for all lines

		
		
		// OpenUp. Gabriel Vila. 21/12/2014. Issue #3414
		// Comento actualizacion de estadisticas de socio de negocio ya que no es correcto el calculo.
		
		/*
		// Update BP Statistics
		MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(),
				get_TrxName());
		// Update total revenue and balance / credit limit (reversed on
		// AllocationLine.processIt)
		BigDecimal invAmt = MConversionRate.convertBase(
				getCtx(),
				getGrandTotal(true), // CM adjusted
				getC_Currency_ID(), getDateAcct(), getC_ConversionType_ID(),
				getAD_Client_ID(), getAD_Org_ID());
		if (invAmt == null) {
			m_processMsg = "Could not convert C_Currency_ID="
					+ getC_Currency_ID() + " to base C_Currency_ID="
					+ MClient.get(Env.getCtx()).getC_Currency_ID();
			return DocAction.STATUS_Invalid;
		}
		// Total Balance
		BigDecimal newBalance = bp.getTotalOpenBalance(false);
		if (newBalance == null)
			newBalance = Env.ZERO;
		if (isSOTrx()) {
			newBalance = newBalance.add(invAmt);
			//
			if (bp.getFirstSale() == null)
				bp.setFirstSale(getDateInvoiced());
			BigDecimal newLifeAmt = bp.getActualLifeTimeValue();
			if (newLifeAmt == null)
				newLifeAmt = invAmt;
			else
				newLifeAmt = newLifeAmt.add(invAmt);
			BigDecimal newCreditAmt = bp.getSO_CreditUsed();
			if (newCreditAmt == null)
				newCreditAmt = invAmt;
			else
				newCreditAmt = newCreditAmt.add(invAmt);
			//
			log.fine("GrandTotal=" + getGrandTotal(true) + "(" + invAmt
					+ ") BP Life=" + bp.getActualLifeTimeValue() + "->"
					+ newLifeAmt + ", Credit=" + bp.getSO_CreditUsed() + "->"
					+ newCreditAmt + ", Balance="
					+ bp.getTotalOpenBalance(false) + " -> " + newBalance);
			bp.setActualLifeTimeValue(newLifeAmt);
			bp.setSO_CreditUsed(newCreditAmt);
		} // SO
		else {
			newBalance = newBalance.subtract(invAmt);
			log.fine("GrandTotal=" + getGrandTotal(true) + "(" + invAmt
					+ ") Balance=" + bp.getTotalOpenBalance(false) + " -> "
					+ newBalance);
		}
		bp.setTotalOpenBalance(newBalance);
		bp.setSOCreditStatus();
		if (!bp.save(get_TrxName())) {
			m_processMsg = "Could not update Business Partner";
			return DocAction.STATUS_Invalid;
		}
	
		*/
		// Fin OpenUp.

		// User - Last Result/Contact
		if (getAD_User_ID() != 0) {
			MUser user = new MUser(getCtx(), getAD_User_ID(), get_TrxName());
			user.setLastContact(new Timestamp(System.currentTimeMillis()));
			user.setLastResult(Msg.translate(getCtx(), "C_Invoice_ID") + ": "
					+ getDocumentNo());
			if (!user.save(get_TrxName())) {
				m_processMsg = "Could not update Business Partner User";
				return DocAction.STATUS_Invalid;
			}
		} // user

		// OpenUp. Gabriel Vila. 21/08/2013. Issue #1237
		// Me aseguro que el comprobante tenga tasa de cambio seteado
		if (this.getCurrencyRate() == null) {
			BigDecimal dividerate = Env.ONE;
			if (this.getC_Currency_ID() != 142) {
				dividerate = MConversionRate.getDivideRate(142,
						this.getC_Currency_ID(), this.getDateInvoiced(), 0,
						this.getAD_Client_ID(), 0);
				if (dividerate == null) {
					m_processMsg = "No hay Tasa de Cambio cargada en el Sistema para Moneda y Fecha de este Documento.";
					return DocAction.STATUS_Invalid;
				}
			}
			this.setCurrencyRate(dividerate);
		}
		// Fin OpenUp. Issue #1237.

		// OpenUp. Gabriel Vila. 18/10/2012. Issue #76.
		// Si el comprobante tiene forma de pago del tipo contado y tengo medios
		// de pago contado
		// entonces debo generar recibo y hacer la afectacion contra este
		// comprobante.
		if (this.getpaymentruletype() == null)
			this.setpaymentruletype(X_UY_PaymentRule.PAYMENTRULETYPE_CREDITO);
		if (this.getpaymentruletype().equalsIgnoreCase(
				X_UY_PaymentRule.PAYMENTRULETYPE_CONTADO)) {
			if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)
					|| doc.getDocBaseType().equalsIgnoreCase(
							Doc.DOCTYPE_ARInvoice)) {

				// this.generatePayment();
				
				//OpenUp. Nicolas Sarlabos. 03/03/2015. Evito el control cuando el documento es Proforma Expediente y Factura Flete
				if (doc.getValue() != null) {
					if (!doc.getValue().equalsIgnoreCase("fileprofinvoice") && !doc.getValue().equalsIgnoreCase("freightinvoice")
							&& !doc.getValue().equalsIgnoreCase("et-ticket") && !doc.getValue().equalsIgnoreCase("factgastoviaje")) this.processCashInvoice();					
				}	
				//Fin OpenUp.
			}
		}
		// Fin OpenUp. Issue #76.

		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null) {
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		// OpenUp. Nicolas Sarlabos. 30/07/2013. #1157
		if (doc.getValue() != null) {
			if (doc.getValue().equalsIgnoreCase("proform")
					|| doc.getValue().equalsIgnoreCase("expinv"))
				setDefiniteDocumentNoInvExport();

			// OpenUp. Guillermo. 05/08/2013. ISSUE #1188
			if (doc.getValue().equalsIgnoreCase("expinv")) {
				this.setNewDescription();
				this.setLiteralAmt();
			}
			// Fin OpenUp.
		}
		// Fin OpenUp #1157

		// OpenUp. Gabriel Vila. 16/06/2011. Issue #719.
		// Como la actualizacion del numero de documento se hace despues
		// de los impactos de stock, me aseguro que se actulize el numero
		// en la tabla de movimientos de stock.
		MStockTransaction.updateDocumentNo(this, this.getDocumentNo(),
				get_TrxName());
		// Fin OpenUP.

		// OpenUp. Gabriel Vila. 26/06/2013. Issue #1341.
		// Idem anterior actualizo documento en la sumarizadora de movimientos
		// bancarios.
		
		
		
		MSUMAccountStatus.updateDocumentNo(this, this.getDocumentNo(),
				get_TrxName());
		// Fin OpenUP.

		setProcessed(true);
		setDocAction(DOCACTION_None); // Openup. Gabriel Vila. 17/02/2012.

		// OpenUp Nicolas Garcia 22/03/2010, issue #544, reset invoiced date
		// when completed
		if (this.isSOTrx()) {

			// Get the system base date invoice reset flag
			// Check if the system base price list its defined. Defensive
			// Se detecto error al hacer issue #811
			if (MSysConfig.getBooleanValue("UY_DATEINVOICED_RESET", true,
					getAD_Client_ID())) {

				// Solo para documentos que no son de ajuste de cta cte y que no
				// es factura por diferencia de precio y apoyo (NP)
				if (doc.getDocSubTypeSO() != null) {
					if ((!doc.getDocSubTypeSO().equalsIgnoreCase("FA"))
							&& (!doc.getDocSubTypeSO().equalsIgnoreCase("NA"))
							&& (!doc.getDocSubTypeSO().equalsIgnoreCase("NP"))) {
						setDateInvoiced(new Timestamp(
								System.currentTimeMillis()));
						setDateAcct(getDateInvoiced());
					}
				} else {
					setDateInvoiced(new Timestamp(System.currentTimeMillis()));
					setDateAcct(getDateInvoiced());
				}
			}

			// Si es un remito
			if (doc.getDocSubTypeSO() != null) {
				if (doc.getDocSubTypeSO().equalsIgnoreCase("RR")) {
					this.setTotalLines(Env.ZERO);
					this.setGrandTotal(Env.ZERO);
				}
			}

		}
		// Fin OpenUp

		// OpenUp. Nicolas Sarlabos. 14/03/2013. #544. La nota de debito NO debe
		// mover stock
		// OpenUp. Nicolas Sarlabos. 17/01/2013. Si es un remito debo crear la
		// orden de entrega para mover el stock
		if (doc.getValue() != null) {
			if (doc.getValue().equalsIgnoreCase("remito")) {
				if (this.getM_InOut_ID() <= 0) {
					createDeliveryOrder();
				}
				// OpenUp. Nicolas Sarlabos. 26/12/2013. #1420. Valido proveedor
				// en factura de fondo fijo.
			} else if (doc.getValue().equalsIgnoreCase("factfondofijo")) {

				if (this.getDateVendor() == null)
					throw new AdempiereException(
							"Debe ingresar Fecha Proveedor");

				MBPartner partnerFF = MBPartner.forValue(getCtx(), "fondofijo",
						get_TrxName()); // obtengo proveedor Fondo Fijo

				if (partnerFF != null) {

					if (this.getC_BPartner_ID() == partnerFF.get_ID())
						throw new AdempiereException(
								"El proveedor no puede ser 'Fondo Fijo', cambie el proveedor en este documento");

				} else
					throw new AdempiereException(
							"Error al obtener proveedor Fondo Fijo");

				// valido el importe
				this.validateAmtInvoiceFF();

				MTax tax = MTax.forValue(getCtx(), "consfinal", get_TrxName()); // obtengo
																				// impuesto
																				// de
																				// consumidor
																				// final

				if (tax != null) {

					String sql = "select count(c_invoiceline_id)"
							+ " from c_invoiceline" + " where c_tax_id = "
							+ tax.get_ID() + " and c_invoice_id = "
							+ this.get_ID();

					int qtyLines = DB.getSQLValueEx(get_TrxName(), sql);

					// si todas las lineas tienen impuesto de contribuyente
					// final, entonces marco el check del cabezal
					if (qtyLines == lines.length)
						this.setContribuyenteFinal(true);

				} else
					throw new AdempiereException(
							"Error al obtener impuesto Contribuyente Final");

				this.updateReplenish(true); // se actualiza documento de
											// reposicion de FF

				// registro trazabilidad
				MFFCashOut cashRepay = new MFFCashOut(getCtx(),
						this.getUY_FF_CashOut_ID(), get_TrxName()); // instancio
																	// rendicion
																	// de FF
				MFFCashOut cashOut = new MFFCashOut(getCtx(),
						cashRepay.getUY_FF_CashOut_ID_1(), get_TrxName()); // instancio
																			// salida
																			// de
																			// FF

				MFFTracking track = new MFFTracking(getCtx(), 0, get_TrxName());
				track.setUY_FF_CashOut_ID(cashOut.get_ID());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se completa la factura de fondo fijo N° "
						+ this.getDocumentNo());
				track.saveEx();

			} else if (doc.getValue().equalsIgnoreCase("ingresofondofijo")) {

				this.updateReplenish(false); // se actualiza documento de
												// reposicion de FF

			} else if (doc.getValue().equalsIgnoreCase("fileprofinvoice")) { // OpenUp.
																				// Nicolas
																				// Sarlabos.
																				// 13/03/2014.
																				// #1623.

				this.verfyFileProforma();
				this.updateCrtMic();
								

			} else if (doc.getValue().equalsIgnoreCase("freightinvoice") || doc.getValue().equalsIgnoreCase("et-ticket")) { // OpenUp.
																			// Nicolas
																			// Sarlabos.
																			// 19/03/2014.
																			// #1633.

				//this.validateLineAmount(); // valido antes de completar que los
											// importes de lineas sigan teniendo
											// saldo
				this.setLiteral(); // seteo literal del importe de factura
				this.setRateFreightInvoice(); // seteo la tasa de cambio del
												// dia, si la moneda no es pesos

			} else if (doc.getValue().equalsIgnoreCase("factprovsinoc")) { // OpenUp.
																			// Nicolas
																			// Sarlabos.
																			// 14/05/2014.
																			// #1620.

				// si la factura proviene de una orden de servicio
				if (this.get_ValueAsInt("UY_TR_ServiceOrder_ID") > 0)
					this.updateTruckRepair(true); // se impacta en historial de
													// reparaciones del vehiculo

			} else if (doc.getValue().equalsIgnoreCase("valeflete")) { // OpenUp.
																		// Nicolas
																		// Sarlabos.
																		// 03/06/2014.
																		// #1630.

				this.setLiteral(); // seteo literal del importe de factura
			} // Fin #1630.
			
			// Facturas flete en modulo de transporte
			else if (doc.getValue().equalsIgnoreCase("facturaflete")) {
				// Verifico si en parametrizacion de transporte tengo seteado que imprime footer de factura
				MTRConfig config = MTRConfig.forClientID(getCtx(), this.getAD_Client_ID(), null);
				if (config.get_ValueAsBoolean("PrintInvoiceFooter")){
					
					// Instancio y guardo datos de impresion de footer para esta invoice
					MInvoicePrintConfig pconf = new MInvoicePrintConfig(getCtx(), 0, get_TrxName());
					
					// Obtengo la subcategoria del primer producto en la grilla de lineas de factura
					int lineID = DB.getSQLValueEx(get_TrxName(), " select min(c_invoiceline_id) from c_invoiceline where c_invoice_id=" + this.get_ID());
					int prodID = DB.getSQLValueEx(get_TrxName(), " select m_product_id from c_invoiceline where c_invoiceline_id=" + lineID);
					MProduct prod1 = new MProduct(getCtx(), prodID, null);
					// Obtengo subcategoria 1
					MSubCategoriaProducto sc1 = new MSubCategoriaProducto(getCtx(), prod1.getUY_SubCategoria_Producto_ID(), null);
					pconf.setLegend1(sc1.getName() + ": ");
					
					// Obtengo suma de importes de productos de esta subcategoria
					String sql = " select round(sum(coalesce(l.linenetamt,0)),2) as monto " +
							     " from c_invoiceline l " +
							     " inner join m_product prod on l.m_product_id = prod.m_product_id " +
							     " where l.c_invoice_id =" + this.get_ID() +
							     " and prod.uy_subcategoria_producto_id =" + prod1.getUY_SubCategoria_Producto_ID();
					
					BigDecimal amt1 = DB.getSQLValueBDEx(get_TrxName(), sql);
					pconf.setamt1(amt1);
					
					// Obtengo segunda leyenda para impresion que es una segunda categoria de producto entre las lineas.
					// Puede no haber.
					sql = " select max(coalesce(prod.uy_subcategoria_producto_id,0))::numeric(10,0) as uy_subcategoria_producto_id " +
						  " from c_invoiceline l " +
						  " inner join m_product prod on l.m_product_id = prod.m_product_id " +
						  " where l.c_invoice_id =" + this.get_ID() +
						  " and prod.uy_subcategoria_producto_id !=" + prod1.getUY_SubCategoria_Producto_ID();
					int sc2ID = DB.getSQLValueEx(get_TrxName(), sql);
					if (sc2ID > 0){
						MSubCategoriaProducto sc2 = new MSubCategoriaProducto(getCtx(), sc2ID, null);
						pconf.setLegend2(sc2.getName() + ": ");

						// Obtengo suma de importes de productos de esta subcategoria 2
						sql = " select round(sum(coalesce(l.linenetamt,0)),2) as monto " +
							  " from c_invoiceline l " +
							  " inner join m_product prod on l.m_product_id = prod.m_product_id " +
							  " where l.c_invoice_id =" + this.get_ID() +
							   " and prod.uy_subcategoria_producto_id =" + sc2ID;
						
						BigDecimal amt2 = DB.getSQLValueBDEx(get_TrxName(), sql);
						pconf.setamt2(amt2);
						
					}
					pconf.setC_Invoice_ID(this.get_ID());
					pconf.saveEx();
				}
				
				// OpenUp. Raul Capecce. 14/01/2015. #3450.
				// Se pasa el literal de la moneda a Portugues
				DB.executeUpdateEx("UPDATE C_Invoice SET literalnumber = '" + Converter.convertirPortuguese(getGrandTotal()) + "' WHERE C_Invoice_ID = " + getC_Invoice_ID(), get_TrxName());
				
				// Fin #3450
			} else if (doc.getValue().equalsIgnoreCase("customerncflete") || doc.getValue().equalsIgnoreCase("et-nc")) {
				
				this.setLiteral(); // seteo literal del importe de factura
				this.setRateFreightInvoice(); // seteo la tasa de cambio del dia si la moneda no es pesos				
				this.allocateNCFlete();
			}//Ini SBT 24-02-2016 Issue #5471 Su es una NC cliente asociada a una Factura se debe crear Afectación 
			else if (doc.getValue().equalsIgnoreCase("customernc") && this.getUY_Invoice_ID()>0){
				this.setLiteral(); // seteo literal del importe de factura 
				this.setRateFreightInvoice(); // seteo la tasa de cambio del dia si la moneda no es pesos				
				this.allocateNCInvoice();
			}//FIN SBT 24-02-2016 Issue #5471
			// Ini DBT 29/02/2016 Issue #5516 Creo afectación para la NC de credi por descuento
			else if (doc.getValue().equalsIgnoreCase("vendorncdesc") && !(this.isSOTrx())){
				//Si los monto no son iguales no permito completar
				if(!(this.getGrandTotal().compareTo((BigDecimal)this.get_Value("amtinvallocated"))==0))throw new AdempiereException("Saldo de afectación directa debe ser 0");
				if(!((Env.ZERO.compareTo((BigDecimal)this.get_Value("AmtToAllocate"))==0)))throw new AdempiereException("Saldo de afectación directa debe ser 0");
				this.setLiteral();
				this.setRateFreightInvoice();
				try {
					this.allocateItOpenUp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// FIN DBT 29/02/2016 Issue #5516 

		}//Fin si doc no es null
		// Fin OpenUp.

		// OpenUp. Nicolas Sarlabos. 29/07/2011. #715
		// Se setea la fecha y hora actual en el nuevo campo "datetimeinvoiced"
		setdatetimeinvoiced(new Timestamp(System.currentTimeMillis())); // OpenUp.
																		// Nicolas
																		// Sarlabos.
																		// 04/01/2013.
																		// #309
		// Fin OpenUp

		if (this.isSOTrx()) {

			if (MSysConfig.getBooleanValue("UY_PrintDirectInvoice", false,
					this.getAD_Client_ID())) {

				ReportCtl.startDocumentPrint(ReportEngine.INVOICE,
						this.get_ID(), true);

			}

		}

		// OpenUp. Leonardo Boccone. 12/09/2014. ISSUE#2933
		// Cuando el impuesto esta incluido dentro del precio del producto en la
		// lista de precio debemos editar las lineas para que calculen
		// correctamente los subtotales.
		if (this.isSOTrx()) {
			if (isTaxIncluded()) {
				MInvoiceTax[] stdTax = this.getTaxes(true);
				BigDecimal subtotal = Env.ZERO;
				for (int i = 0; i < stdTax.length; i++) {
					subtotal = subtotal.add(stdTax[i].getTaxBaseAmt());
				}
				this.setTotalLines(subtotal);

				MInvoiceLine[] lineas = this.getLines();
				for (int i = 0; i < lineas.length; i++) {

					MTax tax = new MTax(getCtx(), ((MTaxCategory) lineas[i]
							.getProduct().getC_TaxCategory()).getDefaultTax()
							.getC_Tax_ID(), get_TrxName());
					BigDecimal taxAmt = Env.ZERO;
					taxAmt = taxAmt.add(tax.calculateTax(
							lineas[i].getPriceEntered(), true, getPrecision()));
					BigDecimal price = lineas[i].getPriceEntered().subtract(
							taxAmt);
					BigDecimal taxnetAmt = Env.ZERO;
					taxnetAmt = taxnetAmt.add(tax.calculateTax(
							lineas[i].getLineNetAmt(), true, getPrecision()));
					BigDecimal bd = lineas[i].getLineNetAmt().subtract(
							taxnetAmt);
					// bd = bd.multiply(lineas[i].getQtyInvoiced());

					DB.executeUpdateEx("UPDATE c_invoiceline set  linenetamt="
							+ bd + ", priceentered=" + price
							+ " WHERE c_invoiceline_id=" + lineas[i].get_ID(),
							get_TrxName());
				}
			}
		} else {
			//OpenUp. Nicolas Sarlabos. 21/04/2015. #3871.
			if(doc.getValue()!=null && !doc.getValue().equalsIgnoreCase("")){				
				if(doc.getValue().equalsIgnoreCase("invoicevendor") || doc.getValue().equalsIgnoreCase("factprovsinoc")){
					
					if (MSysConfig.getBooleanValue("UY_INV_GENERATE_PRICELIST_VERSION", false, getAD_Client_ID())) {
						
						this.checkPriceListVersion();					
						
					}					
				}				
			}//Fin OpenUp.		
		}
		// Fin OpenUp.ISSUE#2933

		
		/*
		 * OpenUp. Raul Capecce. 22/01/2016. ISSUE #5270
		 * Indica si la factura se tiene que pasar al módulo que envia la facturación electrónica
		 */
		if(MSysConfig.getBooleanValue("UY_CFE_ENABLE_SEND", false, this.getAD_Client_ID())){
			CFEManager cfeManager = new CFEManager(getAD_Client_ID(), getCtx(), get_TrxName());
			cfeManager.addCFE(this);
			cfeManager.SendCFE();
		}
		// FIN OpenUp. Raul Capecce. 22/01/2016. ISSUE #5270
		
		return DocAction.STATUS_Completed;

	}

	/***
	 * OpenUp. Nicolas Sarlabos. 21/04/2015. ISSUE#3871.
	 * Metodo que verifica precios en factura proveedor y genera nueva version de lista si algun precio fue modificado. 
	 * 
	 * */
	private void checkPriceListVersion() {
		
		MPriceListVersion actualVersion = null, newVersion = null;
		MInvoiceLine invLine = null;
		MProductPrice actualPrice = null, newPrice = null;
		boolean generatePLV = false;
		boolean createPrice = false;
		
		//obtengo ultima version de lista de precios activa para la lista actual
		actualVersion = MPriceListVersion.forCurrencyType(getCtx(), this.getC_Currency_ID(), false, get_TrxName());
		
		if(actualVersion==null) throw new AdempiereException("No se obtuvo version vigente de lista de precios de compra para la moneda indicada");
	
		MInvoiceLine[] lines = this.getLines(true);
		
		for (int i = 0; i < lines.length; i++) {
			
			invLine = lines[i];

			if (invLine.getM_Product_ID() > 0){

				//obtengo precio del producto en esta linea de factura en la version de lista actual
				actualPrice = MProductPrice.get(getCtx(), actualVersion.get_ID(), invLine.getM_Product_ID(), get_TrxName());

				if(actualPrice != null){
					
					if(invLine.getPriceEntered().compareTo(actualPrice.getPriceList())!=0) {

						generatePLV = true;

						break;
					}		
				} else {
					
					generatePLV = true;
					createPrice = true;
					
					break;				
					
				}
				
			}
			
			
		}	
		
		//si se debe generar nueva version de lista de precios
		if(generatePLV){
			
			MPriceList list = (MPriceList)actualVersion.getM_PriceList();//instancio cabezal de lista de precios de version actual 
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			//creo nueva version de lista de precios
			newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName());
			newVersion.setAD_Client_ID(actualVersion.getAD_Client_ID());
			newVersion.setAD_Org_ID(actualVersion.getAD_Org_ID());
			newVersion.setIsActive(true);
			newVersion.setName(list.getName() + " " + today.toString());
			newVersion.setM_PriceList_ID(list.get_ID());
			newVersion.setM_DiscountSchema_ID(actualVersion.getM_DiscountSchema_ID());
			newVersion.setValidFrom(actualVersion.getValidFrom());
			newVersion.saveEx();
			
			MProductPrice[] prices = actualVersion.getProductPrice("");//obtengo precios de productos para la version actual
			
			for (int i = 0; i < prices.length; i++) {//recorro precios de version de lista actual y voy creando en la nueva version
				
				actualPrice = prices[i];
				
				newPrice = new MProductPrice(getCtx(), 0, get_TrxName());
				newPrice.setM_PriceList_Version_ID(newVersion.get_ID());
				newPrice.setAD_Client_ID(newVersion.getAD_Client_ID());
				newPrice.setAD_Org_ID(newVersion.getAD_Org_ID());
				newPrice.setIsActive(true);
				newPrice.setM_Product_ID(actualPrice.getM_Product_ID());
				newPrice.setPriceList(actualPrice.getPriceList());
				newPrice.setPriceLimit(actualPrice.getPriceLimit());
				newPrice.setPriceStd(actualPrice.getPriceStd());
				newPrice.saveEx();			
				
			}
			
			//recorro lineas de factura para actualizar precios
			for (int i = 0; i < lines.length; i++) {
				
				invLine = lines[i];
				
				if(createPrice){//si debo crear nuevo precio
					
					//obtengo precio del producto en esta linea de factura en la version nueva, para saber si existe
					actualPrice = MProductPrice.get(getCtx(), newVersion.get_ID(), invLine.getM_Product_ID(), get_TrxName());
					
					if(actualPrice == null){ //agrego nuevo precio de producto
						
						MProductPrice pprice = new MProductPrice(getCtx(), 0, get_TrxName());
						pprice.setM_PriceList_Version_ID(newVersion.get_ID());
						pprice.setAD_Client_ID(newVersion.getAD_Client_ID());
						pprice.setAD_Org_ID(newVersion.getAD_Org_ID());
						pprice.setIsActive(true);
						pprice.setM_Product_ID(invLine.getM_Product_ID());
						pprice.setPriceList(invLine.getPriceEntered());
						pprice.setPriceLimit(invLine.getPriceEntered());
						pprice.setPriceStd(invLine.getPriceEntered());
						pprice.saveEx();						
						
					} else {
						
						DB.executeUpdateEx("update m_productprice set pricelist = " + invLine.getPriceEntered() + ", pricelimit = " + invLine.getPriceEntered() +
								", pricestd = " + invLine.getPriceEntered() + " where m_product_id = " + invLine.getM_Product_ID() + 
								" and m_pricelist_version_id = " + newVersion.get_ID(), get_TrxName());		
						
					}				
					
				} else {
					
					DB.executeUpdateEx("update m_productprice set pricelist = " + invLine.getPriceEntered() + ", pricelimit = " + invLine.getPriceEntered() +
							", pricestd = " + invLine.getPriceEntered() + " where m_product_id = " + invLine.getM_Product_ID() + 
							" and m_pricelist_version_id = " + newVersion.get_ID(), get_TrxName());						
					
				}
				
			}
			
			//dejo inactiva la version anterior
			DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id = " + actualVersion.get_ID(), get_TrxName());			
		}		
		
		//si debe agregar producto nuevo a la lista
		
	}

	/***
	 * OpenUp. Nicolas Sarlabos. 29/12/2014. ISSUE#
	 * Metodo que actualiza el CRT y MIC al completarse la proforma 
	 * 
	 * */
	private void updateCrtMic() {
		
		MTRCrt crt = MTRCrt.forInvoice(getCtx(), this.get_ID(), get_TrxName()); //obtengo CRT, si lo hay
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		MTRConfigCrt configCrt = MTRConfigCrt.forConfig(getCtx(), param.get_ID(), get_TrxName());
		
		if (configCrt==null) throw new AdempiereException("No se obtuvieron parametros de CRT para la empresa actual");
				
		if(crt!=null && crt.get_ID()>0){
			
			crt.setamt1(Env.ZERO);
			crt.setamt2(Env.ZERO);
			crt.setamt3(Env.ZERO);
			crt.setamt4(Env.ZERO);
			crt.setamt5(Env.ZERO);
			crt.setamt6(Env.ZERO);
			
			MTRTrip trip = (MTRTrip)crt.getUY_TR_Trip();
			String sql = "", value = "";
			
			BigDecimal nationalAmt = Env.ZERO;
			BigDecimal interAmt = Env.ZERO;
			BigDecimal subTotalAmt = this.getTotalLines();
			
			if(trip.getReceiptMode().equalsIgnoreCase("AMBOS")){

				sql = "select coalesce(sum(nationalamt),0)" +
						" from c_invoiceline" +
						" where c_invoice_id = " + this.get_ID();
				nationalAmt = DB.getSQLValueBDEx(get_TrxName(), sql);

				sql = "select coalesce(sum(internationalamt),0)" +
						" from c_invoiceline" +
						" where c_invoice_id = " + this.get_ID();
				interAmt = DB.getSQLValueBDEx(get_TrxName(), sql);

				//EL TERRITORIO EXTRANJERO SIEMPRE ES DE ORIGEN A FRONTERA
				crt.setamt1(interAmt); //seteo campo de importe de flete del remitente, con el importe del terr. internacional
				crt.setamt2(nationalAmt); //seteo campo de importe de flete del destinatario, con el importe del terr. nacional			

			} else if (trip.getReceiptMode().equalsIgnoreCase("ORIGEN")){

				crt.setamt1(subTotalAmt); //seteo campo de importe de flete del remitente, con el importe subtotal de la factura			

			} else if (trip.getReceiptMode().equalsIgnoreCase("DESTINO")){

				crt.setamt2(subTotalAmt); //seteo campo de importe de flete del destinatario, con el importe subtotal de la factura		

			}		
			
			//se carga monto de flete externo
			//obtengo suma de importes para terr. internacional
			sql = "select coalesce(sum(internationalamt),0)" +
					" from c_invoiceline" +
					" where c_invoice_id = " + this.get_ID();
			BigDecimal amt = DB.getSQLValueBD(get_TrxName(), sql);

			MCurrency cur = new MCurrency(getCtx(),this.getC_Currency_ID(),get_TrxName()); //instancio moneda del cabezal
			
			crt.setC_Currency2_ID(this.getC_Currency_ID());
			
			if(amt.compareTo(Env.ZERO)>0){
				
				// Importe a texto con formato . para miles y , para decimales
				BigDecimal impaux = amt.setScale(3, RoundingMode.HALF_UP);
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(3);
				df.setMinimumFractionDigits(3);
				df.setGroupingUsed(true);
				String result = df.format(impaux);

				if (result != null){
					result = result.replace(".", ";");
					result = result.replace(",", ".");
					result = result.replace(";", ",");
				}
				
				if(configCrt.isValueIncluded()){
					
					value = cur.getCurSymbol() + " " + result + "   VALOR INCLUIDO EN FLETE TOTAL";					
				
				} else value = cur.getCurSymbol() + " " + result;					

				crt.setValorFleteExt(value);

			} else {				
				
				if(configCrt.isValueIncluded()) crt.setValorFleteExt("VALOR INCLUIDO EN FLETE TOTAL");
			}
		
			crt.saveEx();		
			
			// Actualizo valores de flete en ordenes de transporte y movimientos
			crt.refreshTransOrders();
			crt.refreshTRStock();
			
			//actualizo MIC
			this.refreshMicHdr(crt,param);
			this.refreshMicCont(crt,true,param);
			this.refreshMicCont(crt,false,param);
				
		}	
		
	}
	
	private void refreshMicHdr(MTRCrt crt, MTRConfig param) {
		
		String sql = "";
		MTRMic mic = null;
		BigDecimal amt = Env.ZERO;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			sql = "select uy_tr_mic_id" + 
                  " from uy_tr_mic" +
                  " where uy_tr_crt_id = " + crt.get_ID();
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while(rs.next()){
				
				mic = new MTRMic(getCtx(),rs.getInt("uy_tr_mic_id"),get_TrxName());
				
				//segun parametros de transporte, si moneda del flete es distinto a U$S, debo hacer la conversion
				if(param.isConvertedAmt()){
					
					amt = this.getAmountMic(this.getTotalLines());
					mic.setAmount(amt);	
					
				} else mic.setAmount(this.getTotalLines()); 
													
				mic.saveEx();
					
			}		
			
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
	
	}
	
	private void refreshMicCont(MTRCrt crt, boolean isCRT1, MTRConfig param) {
		
		String sql = "";
		MTRMicCont cont = null;
		BigDecimal amt = Env.ZERO;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			if(isCRT1){ //si es el CRT1 de la continuacion
				
				sql = "select uy_tr_miccont_id" + 
		              " from uy_tr_miccont" +
		              " where uy_tr_crt_id = " + crt.get_ID();
								
			} else { //si es el CRT2 de la continuacion
				
				sql = "select uy_tr_miccont_id" + 
			          " from uy_tr_miccont" +
			          " where uy_tr_crt_id_1 = " + crt.get_ID();		
				
			}
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while(rs.next()){
				
				cont = new MTRMicCont(getCtx(),rs.getInt("uy_tr_miccont_id"),get_TrxName());
								
				if(isCRT1){

					//segun parametros de transporte, si moneda del flete es distinto a U$S, debo hacer la conversion
					if(param.isConvertedAmt()){
						
						amt = this.getAmountMic(this.getTotalLines());
						cont.setAmount(amt);	
						
					} else cont.setAmount(this.getTotalLines()); 
					
					cont.saveEx();

				} else {

					//segun parametros de transporte, si moneda del flete es distinto a U$S, debo hacer la conversion
					if(param.isConvertedAmt()){
						
						amt = this.getAmountMic(this.getTotalLines());
						cont.setAmount2(amt);	
						
					} else cont.setAmount2(this.getTotalLines());
					
					cont.saveEx();					
				}	
							
			}		
			
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
	
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 21/01/2015.
	 * Metodo que devuelve el importe de flete a setear en MIC, convirtiendo a U$ si es necesario.
	 * 
	 * */
	private BigDecimal getAmountMic(BigDecimal amt){
		
		BigDecimal amount = Env.ZERO;
		
		if(this.getC_Currency_ID()!=100){
			
			BigDecimal dividerate = Env.ZERO;
			
			int curFromID = this.getC_Currency_ID();
			int curToID = 100;
			
			if (curFromID == 142){
				dividerate = MConversionRate.getDivideRate(curFromID, curToID, this.getDateInvoiced(), 0, this.getAD_Client_ID(), this.getAD_Org_ID());	
			}
			else{
				BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, this.getDateInvoiced(), 0, this.getAD_Client_ID(), 0);
				BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, this.getDateInvoiced(), 0, this.getAD_Client_ID(), 0);
				
				if ((rateFrom != null) && (rateTo != null)){
					if (rateTo.compareTo(Env.ZERO) > 0) {
						dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
					}
				}
			}
			
			if (dividerate == null || dividerate.compareTo(Env.ZERO)==0) throw new AdempiereException("No se obtuvo tasa de cambio para fecha de documento");
			
			amount = amt.divide(dividerate, 2, RoundingMode.HALF_UP);						
			
		} else amount = amt;
		
		return amount;	
	}

	/***
	 * OpenUp Ltda. Issue #3205
	 * 
	 * @author Matias Carbajal - 04/11/2014 Metodo que
	 */
	private String validateInvValeFlete() {

		String msg = null, sql = "";
		BigDecimal saldo = Env.ZERO;

		for (MInvoiceFlete line : this.getLinesInvFlete()) {

			sql = "select coalesce(amtopen,0)" + " from alloc_invflete_amtopen"
					+ " where c_invoice_id = " + line.getC_Invoice_ID_Flete();
			saldo = DB.getSQLValueBDEx(get_TrxName(), sql);

			if (line.getamtopen().compareTo(saldo) != 0) {
				msg = "El saldo de la linea no es correcto.";
				return msg;
			}

		}

		return msg;
	}

	/***
	 * OpenUp Ltda. Issue #1620
	 * 
	 * @author Nicolas Sarlabos - 14/05/2014 Metodo que impacta en grilla de
	 *         historial de reparaciones del vehiculo, creando o eliminando una
	 *         linea.
	 */
	private void updateTruckRepair(boolean isNew) {

		try {

			int orderID = this.get_ValueAsInt("UY_TR_ServiceOrder_ID");

			MTRServiceOrder order = new MTRServiceOrder(getCtx(), orderID,
					get_TrxName()); // obtengo orden de servicio

			if (isNew) {

				MTRTruckRepair line = new MTRTruckRepair(getCtx(), 0,
						get_TrxName());
				line.setUY_TR_Truck_ID(order.getUY_TR_Truck_ID());
				line.setDateTrx(this.getDateInvoiced());
				line.setDateOrdered(order.getDateTrx());
				line.setKilometros(order.getKilometros());
				line.setC_BPartner_ID(order.getC_BPartner_ID());
				line.setC_Invoice_ID(this.get_ID());
				line.setC_Currency_ID(this.getC_Currency_ID());
				line.setUY_TR_ServiceOrder_ID(order.get_ID());
				line.setDescription(this.getDescription());
				line.setIsOwn(false);
				line.saveEx();

			} else {

				DB.executeUpdateEx(
						"delete from uy_tr_truckrepair where c_invoice_id = "
								+ this.get_ID() + " and uy_tr_truck_id = "
								+ order.getUY_TR_Truck_ID()
								+ " and uy_tr_serviceorder_id = "
								+ order.get_ID(), get_TrxName());
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/***
	 * 
	 * OpenUp Ltda. Issue #1987
	 * 
	 * @author Nicolas Sarlabos - 24/04/2014 Metodo que verifica que los
	 *         importes de los productos de las lineas, en la Factura Flete,
	 *         sigan teniendo saldo.
	 */
	/*private void validateLineAmount() {

		String sql = "";

		int crtID = this.get_ValueAsInt("UY_TR_Crt_ID");

		if (crtID > 0) {

			MTRCrt crt = new MTRCrt(this.getCtx(), crtID, this.get_TrxName());

			MInvoice proforma = new MInvoice(getCtx(), crt.getC_Invoice_ID(),
					get_TrxName()); // instancio proforma asociada

			MInvoiceLine[] iLines = this.getLines();

			for (int i = 0; i < iLines.length; i++) { // recorro lineas de
														// factura

				MProduct prod = new MProduct(getCtx(),
						iLines[i].getM_Product_ID(), get_TrxName()); // instancio
																		// producto

				// obtengo importe original del producto en la proforma
				sql = "select coalesce(priceentered,0)"
						+ " from c_invoiceline l" + " where m_product_id = "
						+ prod.get_ID() + " and c_invoice_id = "
						+ proforma.get_ID();
				BigDecimal amtOriginal = DB.getSQLValueBDEx(get_TrxName(), sql);

				if (amtOriginal == null)
					amtOriginal = Env.ZERO;

				// obtengo importe facturado en documentos de Factura Flete
				// completos
				sql = "select coalesce(amtallocated,0)"
						+ " from alloc_freightinvoiceline"
						+ " where m_product_id = " + prod.get_ID()
						+ " and uy_tr_trip_id = "
						+ this.get_ValueAsInt("uy_tr_trip_id")
						+ " and uy_tr_crt_id = "
						+ this.get_ValueAsInt("uy_tr_crt_id");
				BigDecimal amtAllocated = DB
						.getSQLValueBDEx(get_TrxName(), sql);

				if (amtAllocated == null)
					amtAllocated = Env.ZERO;

				if (amtOriginal.compareTo(Env.ZERO) > 0) {

					BigDecimal amtAvailable = amtOriginal
							.subtract(amtAllocated); // obtengo el saldo
														// disponible para
														// facturar

					if (amtAvailable.compareTo(Env.ZERO) <= 0)
						throw new AdempiereException(
								"El producto '"
										+ prod.getName()
										+ "' no tiene saldo disponible, verifique otros documentos completados con anterioridad");

				}

			}

		}

	}*/

	/***
	 * 
	 * OpenUp Ltda. Issue #1633
	 * 
	 * @author Nicolas Sarlabos - 20/03/2014 Metodo que setea la tasa de cambio
	 *         verifica el peso y cantidad de bultos en la Proforma.
	 */
	private void setRateFreightInvoice() {

		BigDecimal rate = Env.ZERO;

		// si factura no es
		if (this.getC_Currency_ID() != 142) {
			
			Timestamp dateInv = TimeUtil.trunc(this.getDateInvoiced(), TimeUtil.TRUNC_DAY);

			BigDecimal dividerate1 = MConversionRate.getDivideRate(142,
					this.getC_Currency_ID(), dateInv, 0,
					this.getAD_Client_ID(), this.getAD_Org_ID());

			BigDecimal dividerate2 = MConversionRate.getDivideRate(
					this.getC_Currency_ID(), 142, dateInv, 0,
					this.getAD_Client_ID(), this.getAD_Org_ID());

			if (dividerate1.compareTo(dividerate2) >= 0) {

				rate = dividerate1;

			} else
				rate = dividerate2;

		}

		this.setCurrencyRate(rate);
	}

	/***
	 * 
	 * OpenUp Ltda. Issue #1623
	 * 
	 * @author Nicolas Sarlabos - 13/03/2014 Metodo que verifica el peso y
	 *         cantidad de bultos en la Proforma.
	 */
	private void verfyFileProforma() {

		String sql = "";

		MDocType doc = MDocType.forValue(getCtx(), "fileprofinvoice",
				get_TrxName()); // obtengo documento Proforma

		if (doc == null || doc.get_ID() <= 0)
			throw new AdempiereException(
					"No se pudo obtener documento PROFORMA");

		MTRTrip trip = new MTRTrip(getCtx(),
				this.get_ValueAsInt("UY_TR_Trip_ID"), get_TrxName()); // obtengo
																		// el
																		// expediente
																		// asociado

		BigDecimal tripWeight = trip.getWeight(); // obtengo peso bruto del exp.
		BigDecimal tripQty = trip.getQtyPackage(); // obtengo cantidad de bultos
													// del exp.

		// si el cobro se realiza a AMBOS, se duplican las cantidades de peso y
		// bultos
		if (trip.getReceiptMode().equalsIgnoreCase("AMBOS")) {

			tripWeight = tripWeight.add(tripWeight);
			tripQty = tripQty.add(tripQty);
		}

		BigDecimal invWeight = (BigDecimal) this.get_Value("Weight"); // obtengo
																		// peso
																		// bruto
																		// de la
																		// factura
		BigDecimal invQty = (BigDecimal) this.get_Value("UY_CantBultos"); // obtengo
																			// cantidad
																			// de
																			// bultos
																			// de
																			// la
																			// factura

		// obtengo suma de peso bruto de todas las proformas en estado <> VO
		sql = "select coalesce(sum(weight),0)" + " from c_invoice"
				+ " where c_doctypetarget_id = " + doc.get_ID()
				+ " and uy_tr_trip_id = " + trip.get_ID()
				+ " and docstatus <> 'VO'" + " and c_invoice_id <> "
				+ this.get_ID();
		BigDecimal sumWeight = DB.getSQLValueBDEx(get_TrxName(), sql);

		// obtengo suma de bultos de todas las proformas en estado <> VO
		sql = "select coalesce(sum(uy_cantbultos),0)" + " from c_invoice"
				+ " where c_doctypetarget_id = " + doc.get_ID()
				+ " and uy_tr_trip_id = " + trip.get_ID()
				+ " and docstatus <> 'VO'" + " and c_invoice_id <> "
				+ this.get_ID();
		BigDecimal sumQty = DB.getSQLValueBDEx(get_TrxName(), sql);

		if ((sumWeight.add(invWeight)).compareTo(tripWeight) > 0)
			throw new AdempiereException(
					"Imposible completar, el total de documentos en estado BORRADOR o COMPLETO superan el peso bruto del expediente");

		if ((sumQty.add(invQty)).compareTo(tripQty) > 0)
			throw new AdempiereException(
					"Imposible completar, el total de documentos en estado BORRADOR o COMPLETO superan la cantidad de bultos del expediente");

		// Actualizo campo proforma en el expediente para visualizazion.
		trip.setC_Invoice_ID(this.get_ID());
		trip.saveEx();
		
	}

	/**
	 * Valida el importe de la factura de fondo fijo. OpenUp. Nicolas Sarlabos.
	 * 09/01/2014 issue #1766
	 * 
	 * @param cash
	 * @param doc
	 */
	private void validateAmtInvoiceFF() {

		MFFCashOut cash = new MFFCashOut(getCtx(), this.getUY_FF_CashOut_ID(),
				get_TrxName()); // instancio la rendicion

		if (cash.get_ID() <= 0)
			throw new AdempiereException(
					"Error al obtener rendicion asociada a esta factura");

		List<MFFCashOutLine> clines = cash.getLines();

		MInvoiceLine[] ilines = this.getLines();

		for (int i = 0; i < ilines.length; i++) { // recorro lineas de factura

			MInvoiceLine invLine = ilines[i];

			for (MFFCashOutLine coLine : clines) { // recorro lineas de la
													// rendicion de FF

				if (invLine.getM_Product_ID() == coLine.getM_Product_ID()
						&& invLine.getC_Activity_ID_1() == coLine
								.getC_Activity_ID_1()) {

					MProduct prod = new MProduct(getCtx(),
							invLine.getM_Product_ID(), null);

					if (invLine.getLineTotalAmt().compareTo(
							coLine.getLineTotalAmt()) != 0)
						throw new AdempiereException(
								"El importe del producto '" + prod.getName()
										+ "' debe ser igual al de la rendicion");

				}
			}
		}
	}

	/**
	 * Crea la orden de entrega a partir del remito OpenUp. Nicolas Sarlabos.
	 * 17/01/2013 issue #244
	 * 
	 * @param cash
	 * @param doc
	 */
	public void createDeliveryOrder() {

		MInOut hdr = new MInOut(getCtx(), 0, get_TrxName());
		MDocType doc = MDocType.forValue(getCtx(), "deliveryorder",
				get_TrxName());

		try {
			if (doc.get_ID() > 0) {

				// creo cabezal de la orden
				hdr.setC_DocType_ID(doc.get_ID());
				hdr.setIsSOTrx(true);
				hdr.setDocStatus(DocumentEngine.STATUS_Drafted);
				hdr.setDocAction(DocumentEngine.ACTION_Complete);
				hdr.setPosted(true);
				hdr.setProcessing(false);
				hdr.setProcessed(false);
				hdr.setMovementDate(this.getDateInvoiced());
				hdr.setDateAcct(this.getDateInvoiced());
				hdr.setC_BPartner_ID(this.getC_BPartner_ID());
				hdr.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
				hdr.setM_Warehouse_ID(this.getM_Warehouse_ID());
				hdr.setDeliveryRule("F");
				hdr.setFreightCostRule("I");
				hdr.setDeliveryViaRule("D");
				hdr.setPriorityRule("5");
				hdr.setMovementType("C-");
				hdr.setAD_User_ID(this.getAD_User_ID());
				hdr.setSalesRep_ID(this.getSalesRep_ID());
				hdr.setC_Invoice_ID(this.get_ID());
				hdr.saveEx();

				if (hdr.get_ID() > 0) {

					// creo las lineas de la orden
					MInvoiceLine[] lines = getLines(false);
					int cont = 0;
					for (int i = 0; i < lines.length; i++) {

						MInvoiceLine invline = lines[i];

						if (invline.getM_Product_ID() > 0) { // si la linea
																// tiene
																// producto
							MProduct prod = (MProduct) invline.getM_Product();

							// Solo muevo stock de productos marcados como
							// almacenables y que no estan discontinuados.
							if (prod.isStocked() && !prod.isDiscontinued()) {

								cont += 10;
								MInOutLine ioLine = new MInOutLine(getCtx(), 0,
										get_TrxName());

								ioLine.setLine(cont);
								ioLine.setM_InOut_ID(hdr.get_ID());
								ioLine.setM_Product_ID(invline
										.getM_Product_ID());

								MWarehouse w = new MWarehouse(getCtx(),
										this.getM_Warehouse_ID(), get_TrxName()); // instancio
																					// almacen
								MLocator loc = w.getDefaultLocator(); // obtengo
																		// ubicacion
																		// por
																		// defecto
																		// del
																		// almacen

								if (loc.get_ID() > 0)
									ioLine.setM_Locator_ID(loc.get_ID());
								ioLine.setM_Warehouse_ID(this
										.getM_Warehouse_ID());
								ioLine.setC_UOM_ID(invline.getC_UOM_ID());
								ioLine.setIsInvoiced(true);
								ioLine.setQtyEntered(invline.getQtyEntered());
								ioLine.setMovementQty(invline.getQtyInvoiced());
								ioLine.saveEx();

							}
						}
					}
				}

				// si no se creo ninguna linea entonces borro el cabezal
				if (hdr.getLines().length == 0) {

					String sql = "delete from m_inout where m_inout_id="
							+ hdr.get_ID();
					DB.executeUpdateEx(sql, get_TrxName());

				} else {

					if (!hdr.processIt(DocumentEngine.ACTION_Complete)) {

						throw new AdempiereException(hdr.getProcessMsg());

					} else
						ADialog.info(0, null,
								"Entrega N° " + hdr.getDocumentNo()
										+ " se genero con exito");

				}
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/**
	 * Actualiza la descripcion de la factura de exportacion a partir de la
	 * descripcion mas los numeros de presupuestos concatenados OpenUp. Nicolas
	 * Sarlabos. 14/08/2013 #1188
	 */
	private void setNewDescription() {

		String cadena = "", sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			sql = "select distinct num_budget from c_invoiceline where c_invoice_id = "
					+ this.get_ID();

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				MBudget budget = MBudget.forDocumentNo(getCtx(),
						rs.getString("num_budget"), get_TrxName());

				if (rs.isFirst()) { // si es la primer linea de factura

					cadena = "Presupuesto: ";
				}

				if (rs.isLast()) { // si es la ultima linea no pongo ","

					cadena += budget.getserie() + " "
							+ rs.getString("num_budget");

				} else
					cadena += budget.getserie() + " "
							+ rs.getString("num_budget") + ", ";
			}

			if (this.getDescription() == null) {
				this.setDescription(cadena);
			} else
				this.setDescription(this.getDescription() + " " + cadena);

		} catch (Exception e) {
			throw new AdempiereException(e.toString());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	/**
	 * Setea el importe del documento en forma literal OpenUp. Nicolas Sarlabos.
	 * 15/08/2013 #1188
	 */
	private void setLiteralAmt() {

		String cadena = "";
		Converter conv = new Converter();
		MCurrency cur = new MCurrency(getCtx(), this.getC_Currency_ID(),
				get_TrxName());

		String literal = conv.getStringOfBigDecimal(this.getGrandTotal());

		cadena = cur.getDescription() + " " + literal;

		this.setobservaciones(cadena);

	}

	/**
	 * Crea la linea de movimiento de caja OpenUp. Nicolas Sarlabos. 19/12/2012
	 * 
	 * @param cash
	 * @param doc
	 */
	/*
	 * private void createCashLine(MCash cash,MDocType doc) {
	 * 
	 * if(cash!=null){
	 * 
	 * if(doc!=null){
	 * 
	 * MCashLine line = new MCashLine(getCtx(),0,get_TrxName());
	 * 
	 * line.setC_Cash_ID(cash.get_ID()); line.setCashType("I");
	 * line.setC_Invoice_ID(this.get_ID());
	 * line.setC_Currency_ID(this.getC_Currency_ID());
	 * 
	 * String description = doc.getName() + " - N° " + this.getDocumentNo();
	 * line.setDescription(description);
	 * 
	 * //segun el tipo de documento el monto sera + o -
	 * if(doc.getValue().equalsIgnoreCase("customernc") ||
	 * doc.getValue().equalsIgnoreCase("factprovsinoc") ||
	 * doc.getValue().equalsIgnoreCase("invoicevendor")){
	 * 
	 * line.setAmount(this.getGrandTotal().negate()); line.saveEx();
	 * 
	 * } else if (doc.getValue().equalsIgnoreCase("custinvoicectr") ||
	 * doc.getValue().equalsIgnoreCase("custinvoice") ||
	 * doc.getValue().equalsIgnoreCase("vendornc")){
	 * 
	 * line.setAmount(this.getGrandTotal()); line.saveEx(); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 */

	/**
	 * Devuelve el movimiento de caja para la fecha de factura o null si no
	 * existe OpenUp. Nicolas Sarlabos. 18/12/2012
	 * 
	 * @return
	 */
	/*
	 * private MCash getCash(int cbookID) {
	 * 
	 * MCash cash = null; String sql = ""; int cashID = 0; String dateInv =
	 * this.getDateInvoiced().toString(); dateInv = dateInv.replace("-","");
	 * String year = dateInv.substring(0,4); String month =
	 * dateInv.substring(4,6); String day = dateInv.substring(6,8); String date
	 * = year + "-" + month + "-" + day;
	 * 
	 * sql = "select c_cash_id from c_cash where statementdate=" + "'" + date +
	 * "'" + " and c_cashbook_id=" + cbookID; cashID =
	 * DB.getSQLValueEx(get_TrxName(), sql);
	 * 
	 * if(cashID>0) cash = new MCash (getCtx(), cashID, get_TrxName());
	 * 
	 * return cash; }
	 */

	/***
	 * Verifica si las lineas de la orden de fabricacion van a seguir pendientes
	 * de facturacion y setea la cantidad facturada OpenUp Ltda.
	 * 
	 * @author Nicolas Sarlabos - 20/11/2012
	 * @param line
	 * @see
	 */
	private void updateManufLine(MInvoiceLine line) {

		if (line != null) {

			MManufLine manufLine = new MManufLine(getCtx(),
					line.getUY_ManufLine_ID(), get_TrxName()); // obtengo linea
																// de orden

			manufLine.setQtyInvoiced(line.getQtyInvoiced().add(
					manufLine.getQtyInvoiced())); // seteo total de cantidad
													// facturada

			if (manufLine.getQty().compareTo(manufLine.getQtyInvoiced()) == 0)
				manufLine.setIsPending(false);
			manufLine.saveEx();

		}

	}

	/***
	 * Se baja la cantidad facturada de las lineas de fabricacion al anularse la
	 * factura OpenUp Ltda. #600
	 * 
	 * @author Nicolas Sarlabos - 21/03/2013
	 * @param line
	 * @see
	 */
	private void updateManufLineVoid() {

		MInvoiceLine[] invlines = getLines(false);

		for (MInvoiceLine line : invlines) {

			MManufLine manufLine = new MManufLine(getCtx(),
					line.getUY_ManufLine_ID(), get_TrxName()); // obtengo linea
																// de orden

			manufLine.setQtyInvoiced(manufLine.getQtyInvoiced().subtract(
					line.getQtyInvoiced())); // seteo total de cantidad
												// facturada

			if (manufLine.getQty().compareTo(manufLine.getQtyInvoiced()) > 0)
				manufLine.setIsPending(true);
			manufLine.saveEx();

		}

	}

	/***
	 * Verifica que las provisiones utilizadas en este comprobantes tengan saldo
	 * pendiente para su afectacion. OpenUp Ltda. Issue #49
	 * 
	 * @author Gabriel Vila - 26/10/2012
	 * @see
	 */
	private void verifyProvisionesAmtOpen() {

		try {

			MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(),
					null);

			// Obtengo lineas de provision de este comprobante
			String whereClause = "c_invoice_id =" + this.get_ID();
			List<MInvoiceProvision> invprovs = new Query(getCtx(),
					I_UY_Invoice_Provision.Table_Name, whereClause,
					get_TrxName()).list();
			// Verifico que el saldo pendiente ACTUAL no sea CERO. Recordar que
			// puedo querer afectar mas que el pendiente en una factura.
			for (MInvoiceProvision invprov : invprovs) {
				MProvisionLine provline = new MProvisionLine(getCtx(),
						invprov.getUY_ProvisionLine_ID(), get_TrxName());

				BigDecimal amtOpen = provline.getAmtOpen(null);

				String message = " Provision No tiene Saldo pendiente.\n"
						+ " Producto : "
						+ new MProduct(getCtx(), invprov.getM_Product_ID(),
								null).getValue()
						+ "\n"
						+ " CCosto Departamento : "
						+ new MActivity(getCtx(), invprov.getC_Activity_ID_1(),
								null).getName();

				// Para facturas compra
				if (doc.getDocBaseType()
						.equalsIgnoreCase(Doc.DOCTYPE_APInvoice)) {
					if ((amtOpen.compareTo(Env.ZERO) < 0)) {
						if (invprov.getAmt().compareTo(Env.ZERO) > 0) {
							throw new AdempiereException(message);
						}
					}
				}

				// Para notas de credito compras
				else if (doc.getDocBaseType().equalsIgnoreCase(
						Doc.DOCTYPE_APCredit)) {
					if ((amtOpen.compareTo(Env.ZERO) > 0)) {
						throw new AdempiereException(message);
					}
				}

			}

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

	}

	/***
	 * Elimina provisiones incluidas en este comprobante y que finalmente no
	 * fueron afectadas. OpenUp Ltda. Issue #49
	 * 
	 * @author Gabriel Vila - 26/10/2012
	 * @see
	 */
	private void deleteProvisionNotAllocated() {
		try {
			String action = "delete from uy_invoice_provision "
					+ " where c_invoice_id =" + this.get_ID()
					+ " and amtallocated = 0 ";
			DB.executeUpdateEx(action, get_TrxName());

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/* Save array of documents to process AFTER completing this one */
	ArrayList<PO> docsPostProcess = new ArrayList<PO>();

	private void addDocsPostProcess(PO doc) {
		docsPostProcess.add(doc);
	}

	public ArrayList<PO> getDocsPostProcess() {
		return docsPostProcess;
	}

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateInvoiced(new Timestamp(System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(),
					true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}

	/***
	 * Setea el numero definitivo para documento Proforma y Factura Exportacion
	 * ye inserta en tabla de tracking. OpenUp Ltda. Issue #1155
	 * 
	 * @author Nicolas Sarlabos - 30/07/2013
	 * @see
	 * @return
	 */
	private void setDefiniteDocumentNoInvExport() {

		String sql = "";
		MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(),
				get_TrxName());

		int posFinal = 0;
		String sequence = "";

		for (int i = 0; i < this.getDocumentNo().length(); i++) { // recorro nro
																	// de
																	// documento
																	// actual

			if (this.getDocumentNo().charAt(i) == '_') {

				posFinal = i;
				i = this.getDocumentNo().length();

			}
		}

		sequence = this.getDocumentNo().substring(0, posFinal);
		sequence = sequence.substring(1); // me quedo con la secuencia del
											// documento (Ej: 15)

		if (doc.getValue().equalsIgnoreCase("proform")) {

			sql = "select max(proforminvoice) from uy_exportinvtracking where doctype_proform = "
					+ doc.get_ID()
					+ " and docstatus_proform in ('CO','VO') and sequence_proform = '"
					+ sequence + "'";
			int invID = DB.getSQLValueEx(get_TrxName(), sql);

			if (invID > 0) {

				MInvoice inv = new MInvoice(getCtx(), invID, get_TrxName());
				String docNo = inv.getDocumentNo();

				int posicion = 0;

				for (int i = 0; i < docNo.length(); i++) {

					if (docNo.charAt(i) == '_') {

						posicion = i + 1;
						i = docNo.length();

					}

				}

				String value = docNo.substring(posicion);
				int nro = Integer.parseInt(value) + 1;
				setDocumentNo(this.getDocumentNo() + nro);

				// inserto en tabla de tracking
				MExportInvTracking track = new MExportInvTracking(getCtx(), 0,
						get_TrxName());
				track.setproforminvoice(this.get_ID());
				track.setdoctype_proform(doc.getC_DocType_ID());
				track.setsequence_proform(sequence);
				track.setversion_proform(Integer.toString(nro));
				track.setdocstatus_proform("CO");
				track.saveEx();

			} else {

				String docNo = this.getDocumentNo();
				docNo = docNo + "1";
				setDocumentNo(docNo);

				// inserto en tabla de tracking
				MExportInvTracking track = new MExportInvTracking(getCtx(), 0,
						get_TrxName());
				track.setproforminvoice(this.get_ID());
				track.setdoctype_proform(doc.getC_DocType_ID());
				track.setsequence_proform(sequence);
				track.setversion_proform("1");
				track.setdocstatus_proform("CO");
				track.saveEx();

			}

		} else if (doc.getValue().equalsIgnoreCase("expinv")) {

			sql = "select max(exportinvoice) from uy_exportinvtracking where doctype_export = "
					+ doc.get_ID()
					+ " and docstatus_export in ('CO','VO') and sequence_export = '"
					+ sequence + "'";
			int invID = DB.getSQLValueEx(get_TrxName(), sql);

			if (invID > 0) {

				MInvoice inv = new MInvoice(getCtx(), invID, get_TrxName());
				String docNo = inv.getDocumentNo();

				int posicion = 0;

				for (int i = 0; i < docNo.length(); i++) {

					if (docNo.charAt(i) == '_') {

						posicion = i + 1;
						i = docNo.length();

					}

				}

				String value = docNo.substring(posicion);
				int nro = Integer.parseInt(value) + 1;
				setDocumentNo(this.getDocumentNo() + nro);

				// inserto en tabla de tracking
				sql = "select uy_exportinvtracking_id from uy_exportinvtracking where exportinvoice = "
						+ this.get_ID();
				int trackID = DB.getSQLValueEx(get_TrxName(), sql);

				MExportInvTracking track = new MExportInvTracking(getCtx(),
						trackID, get_TrxName());
				track.setsequence_export(sequence);
				track.setversion_export(Integer.toString(nro));
				track.setdocstatus_export("CO");
				track.saveEx();

			} else {

				String docNo = this.getDocumentNo();
				docNo = docNo + "1";
				setDocumentNo(docNo);

				// inserto en tabla de tracking
				sql = "select uy_exportinvtracking_id from uy_exportinvtracking where exportinvoice = "
						+ this.get_ID();
				int trackID = DB.getSQLValueEx(get_TrxName(), sql);

				MExportInvTracking track = new MExportInvTracking(getCtx(),
						trackID, get_TrxName());
				track.setsequence_export(sequence);
				track.setversion_export("1");
				track.setdocstatus_export("CO");
				track.saveEx();

			}

		}

		MDocType docProform = MDocType.forValue(getCtx(), "proform",
				get_TrxName());

		sql = "select max(sequence_proform) from uy_exportinvtracking where doctype_proform = "
				+ docProform.get_ID();
		int maxSeq = DB.getSQLValueEx(get_TrxName(), sql);

		maxSeq = maxSeq + 1;

		sql = "update ad_sequence set currentnext = " + maxSeq
				+ " where lower(name) like 'uy_proforma'";
		DB.executeUpdateEx(sql, get_TrxName());
	}

	/**
	 * Create Counter Document
	 * 
	 * @return counter invoice
	 */
	@SuppressWarnings("unused")
	private MInvoice createCounterDoc() {
		// Is this a counter doc ?
		if (getRef_Invoice_ID() != 0)
			return null;

		// Org Must be linked to BPartner
		MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
		if (counterC_BPartner_ID == 0)
			return null;
		// Business Partner needs to be linked to Org
		MBPartner bp = new MBPartner(getCtx(), getC_BPartner_ID(),
				get_TrxName());
		int counterAD_Org_ID = bp.getAD_OrgBP_ID_Int();
		if (counterAD_Org_ID == 0)
			return null;

		MBPartner counterBP = new MBPartner(getCtx(), counterC_BPartner_ID,
				get_TrxName());
		// MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID);
		log.info("Counter BP=" + counterBP.getName());

		// Document Type
		int C_DocTypeTarget_ID = 0;
		MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(),
				getC_DocType_ID());
		if (counterDT != null) {
			log.fine(counterDT.toString());
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
				return null;
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		} else // indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(),
					getC_DocType_ID());
			log.fine("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
				return null;
		}

		// Deep Copy
		MInvoice counter = copyFrom(this, getDateInvoiced(), getDateAcct(),
				C_DocTypeTarget_ID, !isSOTrx(), true, get_TrxName(), true);
		//
		counter.setAD_Org_ID(counterAD_Org_ID);
		// counter.setM_Warehouse_ID(counterOrgInfo.getM_Warehouse_ID());
		//
		counter.setBPartner(counterBP);
		// Refernces (Should not be required
		counter.setSalesRep_ID(getSalesRep_ID());
		counter.save(get_TrxName());

		// Update copied lines
		MInvoiceLine[] counterLines = counter.getLines(true);
		for (int i = 0; i < counterLines.length; i++) {
			MInvoiceLine counterLine = counterLines[i];
			counterLine.setClientOrg(counter);
			counterLine.setInvoice(counter); // copies header values (BP, etc.)
			counterLine.setPrice();
			counterLine.setTax();
			//
			counterLine.save(get_TrxName());
		}

		log.fine(counter.toString());

		// Document Action
		if (counterDT != null) {
			if (counterDT.getDocAction() != null) {
				counter.setDocAction(counterDT.getDocAction());
				counter.processIt(counterDT.getDocAction());
				counter.save(get_TrxName());
			}
		}
		return counter;
	} // createCounterDoc

	/**
	 * Void Document.
	 * 
	 * @return true if success
	 */
	public boolean voidIt() {

		// OpenUp. Gabriel Vila. 17/02/2012.
		// Hago la anulacion segun modelo de OpenUp.
		return this.voidItOpenUp();
		// Fin OpenUp

		/*
		 * log.info(toString()); // Before Void m_processMsg =
		 * ModelValidationEngine
		 * .get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID); if
		 * (m_processMsg != null) return false;
		 * 
		 * if (DOCSTATUS_Closed.equals(getDocStatus()) ||
		 * DOCSTATUS_Reversed.equals(getDocStatus()) ||
		 * DOCSTATUS_Voided.equals(getDocStatus())) { m_processMsg =
		 * "Document Closed: " + getDocStatus(); setDocAction(DOCACTION_None);
		 * return false; }
		 * 
		 * // Not Processed if (DOCSTATUS_Drafted.equals(getDocStatus()) ||
		 * DOCSTATUS_Invalid.equals(getDocStatus()) ||
		 * DOCSTATUS_InProgress.equals(getDocStatus()) ||
		 * DOCSTATUS_Approved.equals(getDocStatus()) ||
		 * DOCSTATUS_NotApproved.equals(getDocStatus()) ) { // Set lines to 0
		 * MInvoiceLine[] lines = getLines(false); for (int i = 0; i <
		 * lines.length; i++) { MInvoiceLine line = lines[i]; BigDecimal old =
		 * line.getQtyInvoiced(); if (old.compareTo(Env.ZERO) != 0) {
		 * line.setQty(Env.ZERO); line.setTaxAmt(Env.ZERO);
		 * line.setLineNetAmt(Env.ZERO); line.setLineTotalAmt(Env.ZERO);
		 * line.addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + old +
		 * ")"); // Unlink Shipment if (line.getM_InOutLine_ID() != 0) {
		 * MInOutLine ioLine = new MInOutLine(getCtx(),
		 * line.getM_InOutLine_ID(), get_TrxName());
		 * ioLine.setIsInvoiced(false); ioLine.save(get_TrxName());
		 * line.setM_InOutLine_ID(0); } line.save(get_TrxName()); } }
		 * addDescription(Msg.getMsg(getCtx(), "Voided")); setIsPaid(true);
		 * setC_Payment_ID(0); } else { return reverseCorrectIt(); }
		 * 
		 * // After Void m_processMsg =
		 * ModelValidationEngine.get().fireDocValidate
		 * (this,ModelValidator.TIMING_AFTER_VOID); if (m_processMsg != null)
		 * return false;
		 * 
		 * setProcessed(true); setDocAction(DOCACTION_None); return true;
		 */

	} // voidIt

	/**
	 * Anulacion segun modelo de OpenUp. OpenUp Ltda. Issue # (Migracion).
	 * 
	 * @author Gabriel Vila - 17/02/2012
	 * @see
	 * @return
	 */
	private boolean voidItOpenUp() {

		log.info(toString());

		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(),
				getC_DocTypeTarget_ID(), getAD_Org_ID());

		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		MDocType doc = new MDocType(getCtx(), this.getC_DocType_ID(), null);

		// Validacion de afectaciones
		MAllocation[] allocations = this.getInvoiceAllocations(get_TrxName());

		if (this.getpaymentruletype() == null)
			this.setpaymentruletype("CR");

		if (allocations.length > 0
				&& this.getpaymentruletype().equalsIgnoreCase("CR")) { // OpenUp.
																		// Nicolas
																		// Sarlabos.
																		// 19/08/2013.
																		// #
			String afectaciones = "";
			for (int i = 0; i < allocations.length; i++) {
				afectaciones += allocations[i].getDocumentNo() + ", ";
			}
			afectaciones = afectaciones.substring(0, afectaciones.length() - 1);
			m_processMsg = "No es posible anular este documento ya que tiene las siguientes afectaciones : "
					+ afectaciones;
			return false;
		}

		int mInOutID = this.getM_InOut_ID();

		// Seteo lineas de entrega y lineas de ordenes de venta asociadas a las
		// lineas de esta invoice
		MInvoiceLine[] iLines = getLines(false);
		for (int i = 0; i < iLines.length; i++) {
			MInvoiceLine iLine = iLines[i];
			// Si tengo linea de entrega asociada
			if (iLine.getM_InOutLine_ID() != 0) {

				// Seteo linea de entrega
				MInOutLine ioLine = new MInOutLine(getCtx(),
						iLine.getM_InOutLine_ID(), get_TrxName());
				if (ioLine.get_ID() > 0) {
					if (mInOutID <= 0)
						mInOutID = ioLine.getM_InOut_ID();
					ioLine.setIsInvoiced(false);
					ioLine.saveEx();
				}

				// Seteo linea de orden de venta asociada a la linea de entrega
				MOrderLine ordLine = new MOrderLine(getCtx(),
						ioLine.getC_OrderLine_ID(), get_TrxName());
				if (ordLine.get_ID() > 0) {
					ordLine.setQtyInvoiced(ordLine.getQtyInvoiced().subtract(
							iLine.getQtyInvoiced()));
					if (ordLine.getQtyInvoiced().compareTo(Env.ZERO) < 0)
						ordLine.setQtyInvoiced(Env.ZERO);
					ordLine.saveEx();
				}
			}

			// Si es documento de venta, limpio precios e importes de esta
			// linea.
			if (this.isSOTrx()) {
				iLine.setPrice(Env.ZERO);
				iLine.setPriceActual(Env.ZERO);
				iLine.setPriceEntered(Env.ZERO);
				iLine.setPriceLimit(Env.ZERO);
				iLine.setPriceList(Env.ZERO);
				iLine.setLineNetAmt(Env.ZERO);
				iLine.setLineTotalAmt(Env.ZERO);
				iLine.setTaxAmt(Env.ZERO);
				iLine.saveEx();
			}
		}

		// Reverse/Delete Matching
		if (!isSOTrx()) {
			MMatchInv[] mInv = MMatchInv.getInvoice(getCtx(),
					getC_Invoice_ID(), get_TrxName());
			for (int i = 0; i < mInv.length; i++)
				mInv[i].delete(true);
			MMatchPO[] mPO = MMatchPO.getInvoice(getCtx(), getC_Invoice_ID(),
					get_TrxName());
			for (int i = 0; i < mPO.length; i++) {
				if (mPO[i].getM_InOutLine_ID() == 0)
					mPO[i].delete(true);
				else {
					mPO[i].setC_InvoiceLine_ID(null);
					mPO[i].save(get_TrxName());
				}
			}
		}

		// OpenUp. Nicolas Sarlabos. 09/04/2013. #633. Modifico sql para no
		// setear m_inoutline_id en null.
		// Elimino asociacion de lineas con ordenes
		DB.executeUpdateEx(
				"UPDATE c_invoiceline SET c_orderline_id = null WHERE c_invoice_id="
						+ this.getC_Invoice_ID(), get_TrxName());
		// Fin OpenUp.

		// Elimino impuestos
		DB.executeUpdateEx("DELETE FROM c_invoicetax WHERE c_invoice_id="
				+ this.getC_Invoice_ID(), get_TrxName());

		// Elimino asientos contables de este docuemnto
		FactLine.deleteFact(X_C_Invoice.Table_ID, this.get_ID(), get_TrxName());

		// Anulo entrega/devolucion asociada a documento de venta
		if ((this.isSOTrx()) && (mInOutID > 0)) {

			MInOut inOut = new MInOut(getCtx(), mInOutID, get_TrxName());

			// Seteo motivo de anulacion segun tipo de documento
			if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_ARCredit))
				inOut.setDocStatusReason(X_M_InOut.DOCSTATUSREASON_AnulacionDeNotaDeCredito);
			else
				inOut.setDocStatusReason(X_M_InOut.DOCSTATUSREASON_Otros);

			inOut.setDescription("** ANULADA Automaticamente al anular Factura/NC asociada.***");

			// Anulo entrega/devolucion
			if (!inOut.voidIt()) {
				this.m_processMsg = "No se pudo anular entrega/devolucion : "
						+ inOut.getDocumentNo() + " - " + inOut.getProcessMsg();
				return false;
			}
			inOut.saveEx();

			// Si esta entrega tiene asociada una Reserva, la anulo y elimino
			// sus asociaciones
			if (inOut.getUY_ReservaPedidoHdr_ID() > 0) {

				MReservaPedidoHdr resHdr = new MReservaPedidoHdr(getCtx(),
						inOut.getUY_ReservaPedidoHdr_ID(), get_TrxName());
				if (resHdr.get_ID() <= 0) {
					this.m_processMsg = "No se pudo obtener informacion de Reserva con ID : "
							+ inOut.getUY_ReservaPedidoHdr_ID();
					return false;
				}
				// Elimino linea de proceso de factura para esta reserva
				DB.executeUpdateEx(
						"DELETE FROM uy_procesofactmasline WHERE uy_reservapedidohdr_id ="
								+ resHdr.get_ID(), get_TrxName());

				// Elimino cabezal de reserva del transporte al cual esta
				// asignada
				DB.executeUpdateEx(
						"DELETE FROM uy_asignatransporteline where uy_reservapedidohdr_id ="
								+ resHdr.get_ID(), get_TrxName());

				if (!resHdr.voidIt()) {
					this.m_processMsg = "No se pudo anular la Reserva : "
							+ resHdr.getDocumentNo() + " - "
							+ resHdr.getProcessMsg();
					return false;
				}
				inOut.saveEx();
			}

		}

		// Seteos para documento de venta
		if (this.isSOTrx()) {

			// Limpio precios e importes del mismo
			this.setTotalLines(Env.ZERO);
			this.setGrandTotal(Env.ZERO);

			// Elimino factura de Asignacion de Transporte
			DB.executeUpdateEx(
					"DELETE FROM uy_asignatransportefact where c_invoice_id="
							+ this.getC_Invoice_ID(), get_TrxName());
		//OpenUp. Nicolas Sarlabos. 18/05/2015. #4229.	
		} else {
			
			boolean handleProvision = MSysConfig.getBooleanValue("UY_HANDLE_PROVISION", false, this.getAD_Client_ID());

			if (handleProvision) // Elimino lineas de provision al anular.
				this.deleteProvisionLines();		
			
		}
		//Fin #4229.

		// Proceso anulacion de documentos Contado.
		if (this.getpaymentruletype() != null) {
			if (this.getpaymentruletype().equalsIgnoreCase("CO")) {
				this.voidInvoiceCash();
			}
		}

		// OpenUp. Nicolas Sarlabos. 21/03/2013. #600. Si es una factura con
		// presupuesto debo bajar las cantidades facturadas de las lineas
		if (this.getUY_ManufOrder_ID() > 0)
			updateManufLineVoid();
		// Fin OpenUp.

		// OpenUp. Nicolas Sarlabos. 08/08/2013. #1155. Actualizo estado de
		// documento en tabla de tracking.
		if (doc.getValue() != null) {
			if (doc.getValue().equalsIgnoreCase("expinv")) {

				// actualizo estado en tabla de tracking
				String sql = "update uy_exportinvtracking set docstatus_export = 'VO' where exportinvoice = "
						+ this.get_ID();
				DB.executeUpdateEx(sql, get_TrxName());

			} else if (doc.getValue().equalsIgnoreCase("proform")) {

				// actualizo estado en tabla de tracking
				String sql = "update uy_exportinvtracking set docstatus_proform = 'VO' where proforminvoice = "
						+ this.get_ID();
				DB.executeUpdateEx(sql, get_TrxName());

				// OpenUp. Nicolas Sarlabos. 09/01/2014. #1766. Elimino linea
				// del documento de reposicion
			} else if (doc.getValue().equalsIgnoreCase("factfondofijo")) {

				// obtengo linea de reposicion
				MFFReplenishLine rline = MFFReplenishLine
						.forTableReplenishLine(getCtx(),
								this.getC_DocType_ID(), X_C_Invoice.Table_ID,
								this.get_ID(), 0, get_TrxName());

				if (rline != null)
					rline.deleteEx(true);

				// registro trazabilidad
				MFFCashOut cashRepay = new MFFCashOut(getCtx(),
						this.getUY_FF_CashOut_ID(), get_TrxName()); // instancio
																	// rendicion
																	// de FF
				MFFCashOut cashOut = new MFFCashOut(getCtx(),
						cashRepay.getUY_FF_CashOut_ID_1(), get_TrxName()); // instancio
																			// salida
																			// de
																			// FF

				MFFTracking track = new MFFTracking(getCtx(), 0, get_TrxName());
				track.setUY_FF_CashOut_ID(cashOut.get_ID());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se anula la factura de fondo fijo N° "
						+ this.getDocumentNo());
				track.saveEx();

				// Fin #1766.
			} else if (doc.getValue().equalsIgnoreCase("ingresofondofijo")) {

				this.validateVoidIngresoFF();

				// OpenUp. Nicolas Sarlabos. 17/10/2014. #3131.
			} else if (doc.getValue().equalsIgnoreCase("factprovsinoc")) {

				// si la factura proviene de una orden de servicio
				if (this.get_ValueAsInt("UY_TR_ServiceOrder_ID") > 0)
					this.updateTruckRepair(false); // se impacta en historial de
													// reparaciones del vehiculo
			}
			// Fin OpenUp #3131.
			// OpenUp. Nicolas Sarlabos. 09/03/2016. #5526..
			else if (doc.getValue().equalsIgnoreCase("fileprofinvoice")) 
				this.validateVoidProforma();			
			//Fin OpenUp. #5526.

		}
		// Fin OpenUp #1155.

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setPosted(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);

		// Se crea aviso para el vendedor. OpenUp Nicolas Garcia issue #526 -
		MUser user = new MUser(getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
		String mensaje = "Factura Anulada, " + this.getDocStatusReason()
				+ "\n Anulado por: ";
		if (user.get_ID() > 0) {
			mensaje += user.getName();
		} else {
			mensaje += "--";
		}

		MNote note = new MNote(getCtx(), 339, this.getSalesRep_ID(),
				this.get_Table_ID(), this.getC_Order_ID(), this.toString(),
				mensaje, get_TrxName());
		note.save();
		// Fin creacion de aviso

		return true;
	}

	/***
	 * Elimina lineas de provision en este comprobante al anular.
	 * fueron afectadas. OpenUp Ltda. Issue #4229
	 * 
	 * @author Nicolas Sarlabos - 18/05/2015
	 * @see
	 */
	private void deleteProvisionLines() {
		try {
			String action = "delete from uy_invoice_provision "
					+ " where c_invoice_id = " + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Anula afectacion y recibo asociados a una factura CONTADO OpenUp #
	 * 
	 * @author Nicolas Sarlabos - 19/08/2013
	 * @see
	 * @return
	 */
	@SuppressWarnings("unused")
	private void voidFromInvoiceCash() {

		String sql = "";

		try {

			// se obtiene la afectacion
			sql = "SELECT hdr.uy_allocation_id "
					+ " FROM uy_allocation hdr "
					+ " INNER JOIN uy_allocationinvoice line ON hdr.uy_allocation_id = line.uy_allocation_id "
					+ " WHERE line.c_invoice_id = " + this.get_ID();
			int allocID = DB.getSQLValueEx(get_TrxName(), sql);

			if (allocID > 0) {

				MAllocation alloc = new MAllocation(getCtx(), allocID, null);

				if (alloc.getDocStatus().equalsIgnoreCase("CO")) {
					// Anulo la afectacion
					if (!alloc.processIt(DocumentEngine.ACTION_Void)) {
						throw new AdempiereException(alloc.getProcessMsg());
					}

					alloc.saveEx();
				}
			}

			// se obtiene el recibo de cobro
			sql = "SELECT line.c_payment_id"
					+ " from uy_allocation hdr"
					+ " INNER JOIN uy_allocationpayment line ON hdr.uy_allocation_id = line.uy_allocation_id"
					+ " WHERE hdr.uy_allocation_id = " + allocID;
			int payID = DB.getSQLValueEx(get_TrxName(), sql);

			if (payID > 0) {

				MPayment payment = new MPayment(getCtx(), payID, null);

				if (payment.getDocStatus().equalsIgnoreCase("CO")) {
					// Anulo el recibo
					if (!payment.processIt(DocumentEngine.ACTION_Void)) {
						throw new AdempiereException(payment.getProcessMsg());
					}

					payment.saveEx();

				}

			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/**
	 * Close Document.
	 * 
	 * @return true if success
	 */
	public boolean closeIt() {
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	} // closeIt

	/**
	 * Reverse Correction - same date
	 * 
	 * @return true if success
	 */
	public boolean reverseCorrectIt() {
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(),
				getAD_Org_ID());
		//
		MAllocationHdr[] allocations = MAllocationHdr.getOfInvoice(getCtx(),
				getC_Invoice_ID(), get_TrxName());
		for (int i = 0; i < allocations.length; i++) {
			allocations[i].setDocAction(DocAction.ACTION_Reverse_Correct);
			allocations[i].reverseCorrectIt();
			allocations[i].save(get_TrxName());
		}
		// Reverse/Delete Matching
		if (!isSOTrx()) {
			MMatchInv[] mInv = MMatchInv.getInvoice(getCtx(),
					getC_Invoice_ID(), get_TrxName());
			for (int i = 0; i < mInv.length; i++)
				mInv[i].delete(true);
			MMatchPO[] mPO = MMatchPO.getInvoice(getCtx(), getC_Invoice_ID(),
					get_TrxName());
			for (int i = 0; i < mPO.length; i++) {
				if (mPO[i].getM_InOutLine_ID() == 0)
					mPO[i].delete(true);
				else {
					mPO[i].setC_InvoiceLine_ID(null);
					mPO[i].save(get_TrxName());
				}
			}
		}
		//
		load(get_TrxName()); // reload allocation reversal info

		// Deep Copy
		MInvoice reversal = copyFrom(this, getDateInvoiced(), getDateAcct(),
				getC_DocType_ID(), isSOTrx(), false, get_TrxName(), true);
		if (reversal == null) {
			m_processMsg = "Could not create Invoice Reversal";
			return false;
		}
		reversal.setReversal(true);

		// Reverse Line Qty
		MInvoiceLine[] rLines = reversal.getLines(false);
		for (int i = 0; i < rLines.length; i++) {
			MInvoiceLine rLine = rLines[i];
			rLine.setQtyEntered(rLine.getQtyEntered().negate());
			rLine.setQtyInvoiced(rLine.getQtyInvoiced().negate());
			rLine.setLineNetAmt(rLine.getLineNetAmt().negate());
			if (rLine.getTaxAmt() != null
					&& rLine.getTaxAmt().compareTo(Env.ZERO) != 0)
				rLine.setTaxAmt(rLine.getTaxAmt().negate());
			if (rLine.getLineTotalAmt() != null
					&& rLine.getLineTotalAmt().compareTo(Env.ZERO) != 0)
				rLine.setLineTotalAmt(rLine.getLineTotalAmt().negate());
			if (!rLine.save(get_TrxName())) {
				m_processMsg = "Could not correct Invoice Reversal Line";
				return false;
			}
		}
		reversal.setC_Order_ID(getC_Order_ID());
		reversal.addDescription("{->" + getDocumentNo() + ")");
		// FR1948157
		reversal.setReversal_ID(getC_Invoice_ID());
		reversal.saveEx(get_TrxName());
		//
		if (!reversal.processIt(DocAction.ACTION_Complete)) {
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return false;
		}
		reversal.setC_Payment_ID(0);
		reversal.setIsPaid(true);
		reversal.closeIt();
		reversal.setProcessing(false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx(get_TrxName());
		m_processMsg = reversal.getDocumentNo();
		//
		addDescription("(" + reversal.getDocumentNo() + "<-)");

		// Clean up Reversed (this)
		MInvoiceLine[] iLines = getLines(false);
		for (int i = 0; i < iLines.length; i++) {
			MInvoiceLine iLine = iLines[i];
			if (iLine.getM_InOutLine_ID() != 0) {
				MInOutLine ioLine = new MInOutLine(getCtx(),
						iLine.getM_InOutLine_ID(), get_TrxName());
				ioLine.setIsInvoiced(false);
				ioLine.save(get_TrxName());
				// Reconsiliation
				iLine.setM_InOutLine_ID(0);
				iLine.save(get_TrxName());
			}
		}
		setProcessed(true);
		// FR1948157
		setReversal_ID(reversal.getC_Invoice_ID());
		setDocStatus(DOCSTATUS_Reversed); // may come from void
		setDocAction(DOCACTION_None);
		setC_Payment_ID(0);
		setIsPaid(true);

		// Create Allocation
		MAllocationHdr alloc = new MAllocationHdr(getCtx(), false,
				getDateAcct(), getC_Currency_ID(), Msg.translate(getCtx(),
						"C_Invoice_ID")
						+ ": "
						+ getDocumentNo()
						+ "/"
						+ reversal.getDocumentNo(), get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		if (alloc.save()) {
			// Amount
			BigDecimal gt = getGrandTotal(true);
			if (!isSOTrx())
				gt = gt.negate();
			// Orig Line
			MAllocationLine aLine = new MAllocationLine(alloc, gt, Env.ZERO,
					Env.ZERO, Env.ZERO);
			aLine.setC_Invoice_ID(getC_Invoice_ID());
			aLine.save();
			// Reversal Line
			MAllocationLine rLine = new MAllocationLine(alloc, gt.negate(),
					Env.ZERO, Env.ZERO, Env.ZERO);
			rLine.setC_Invoice_ID(reversal.getC_Invoice_ID());
			rLine.save();
			// Process It
			if (alloc.processIt(DocAction.ACTION_Complete))
				alloc.save();
		}

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return true;
	} // reverseCorrectIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return false
	 */
	public boolean reverseAccrualIt() {
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	} // reverseAccrualIt

	/**
	 * Re-activate
	 * 
	 * @return false
	 */
	public boolean reActivateIt() {

		// OpenUp. Gabriel Vila. 19/10/2012. Issue #78
		// Permito reactivar comprobantes de compras
		return reActivateItOpenUp();

		/*
		 * log.info(toString()); // Before reActivate m_processMsg =
		 * ModelValidationEngine
		 * .get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		 * if (m_processMsg != null) return false;
		 * 
		 * // After reActivate m_processMsg =
		 * ModelValidationEngine.get().fireDocValidate
		 * (this,ModelValidator.TIMING_AFTER_REACTIVATE); if (m_processMsg !=
		 * null) return false;
		 * 
		 * 
		 * return false;
		 */
		// Fin OpenUp. Issue #78.

	} // reActivateIt

	/***
	 * Accion de Reactivate de OpenUp. OpenUp Ltda. Issue #78
	 * 
	 * @author Gabriel Vila - 19/10/2012
	 * @see
	 * @return
	 */
	private boolean reActivateItOpenUp() {

		// OpenUp. Nicolas Sarlabos. 08/01/2014. #1766.
		MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(),
				get_TrxName());

		// Si este comprobante es de venta no hago nada por ahora y no permito
		// reactivar.
		// OpenUp. Nicolas Sarlabos. 02/06/2014. #1623. Modifico para poder
		// reactivar documento Proforma Expediente.
		if (this.isSOTrx()) {
			if (doc.getValue() != null) {
				if (!doc.getValue().equalsIgnoreCase("fileprofinvoice")) {

					this.m_processMsg = "No esta permitido Reactivar Comprobantes de Venta";
					return false;
				}
			}
		}
		// fin #1623.

		// Si este comprobante de compra esta afectado en cuenta corriente no
		// hago nada y aviso
		if (this.getInvoiceAllocations(null).length > 0) {
			this.m_processMsg = "No esta permitido Reactivar el Comprobante ya que tiene Afectaciones en Cuenta Corriente.";
			return false;
		}

		// Verificacion de periodo abierto
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(),
				getC_DocTypeTarget_ID(), getAD_Org_ID());

		if (doc.getValue() != null) {
			if (doc.getValue().equalsIgnoreCase("factfondofijo")
					|| doc.getValue().equalsIgnoreCase("ingresofondofijo")) {

				this.validateReactivateFF(doc);

				String msg = "";

				if (doc.getValue().equalsIgnoreCase("factfondofijo")) {

					msg = "la factura";

				} else if (doc.getValue().equalsIgnoreCase("ingresofondofijo")) {

					msg = "el ingreso";
				}

				// registro trazabilidad
				MFFCashOut cashRepay = new MFFCashOut(getCtx(),
						this.getUY_FF_CashOut_ID(), get_TrxName()); // instancio
																	// rendicion
																	// de FF
				MFFCashOut cashOut = new MFFCashOut(getCtx(),
						cashRepay.getUY_FF_CashOut_ID_1(), get_TrxName()); // instancio
																			// salida
																			// de
																			// FF

				MFFTracking track = new MFFTracking(getCtx(), 0, get_TrxName());
				track.setUY_FF_CashOut_ID(cashOut.get_ID());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se reactiva " + msg
						+ " de fondo fijo N° " + this.getDocumentNo());
				track.saveEx();

				// OpenUp. Nicolas Sarlabos. 17/10/2014. #3131.
			} else if (doc.getValue().equalsIgnoreCase("factprovsinoc")) {							

				// si la factura proviene de una orden de servicio
				if (this.get_ValueAsInt("UY_TR_ServiceOrder_ID") > 0)
					this.updateTruckRepair(false); // se impacta en historial de
													// reparaciones del vehiculo

			}
			// Fin OpenUp #3131.
		}
		// Fin OpenUp.
		
		//OpenUp. Nicolas Sarlabos. 07/07/2015. #4298.
		// Proceso reactivacion de documentos Contado.
		if (this.getpaymentruletype() != null) {
			if (this.getpaymentruletype().equalsIgnoreCase("CO")) {
				this.voidInvoiceCash();
			}
		}
		//Fin OpenUp.

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Elimino asientos contables
		FactLine.deleteFact(I_C_Invoice.Table_ID, this.get_ID(), get_TrxName());

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		this.setPosted(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

		return true;
	}

	/***
	 * Metodo que valida la reactivacion para factura e ingreso de fondo fijo.
	 * Solo se puede reactivar siempre y cuando el documento no este presente en
	 * una reposicion en estado completo. OpenUp Ltda. Issue #1766.
	 * 
	 * @author Nicolas Sarlabos - 08/01/2014
	 * @param doc
	 * @see
	 * @return
	 */
	private void validateReactivateFF(MDocType doc) {

		String sql = "", msg = "";
		int lineID = 0;

		if (doc.getValue() != null) {
			if (doc.getValue().equalsIgnoreCase("factfondofijo")) {

				msg = "La factura";

			} else if (doc.getValue().equalsIgnoreCase("ingresofondofijo")) {

				msg = "El ingreso";
			}
		} else
			throw new AdempiereException("Error al obtener tipo de documento");

		sql = "select uy_ff_replenishline_id" + " from uy_ff_replenishline"
				+ " where ad_table_id = " + X_C_Invoice.Table_ID
				+ " and record_id = " + this.get_ID() + " and c_doctype_id = "
				+ this.getC_DocTypeTarget_ID();

		lineID = DB.getSQLValueEx(get_TrxName(), sql);

		if (lineID > 0) {

			MFFReplenishLine rline = new MFFReplenishLine(getCtx(), lineID,
					get_TrxName()); // instancio linea de reposicion
			MFFReplenish hdr = new MFFReplenish(getCtx(),
					rline.getUY_FF_Replenish_ID(), get_TrxName()); // instancio
																	// cabezal
																	// de
																	// reposicion

			if (hdr.getDocStatus().equalsIgnoreCase("CO"))
				throw new AdempiereException("Imposible reactivar. " + msg
						+ " pertenece al documento de reposicion N° "
						+ hdr.getDocumentNo() + " en estado completo");

		}

	}

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "GrandTotal"))
				.append("=").append(getGrandTotal()).append(" (#")
				.append(getLines(false).length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	} // getSummary

	/**
	 * Get Process Message
	 * 
	 * @return clear text error message
	 */
	public String getProcessMsg() {
		return m_processMsg;
	} // getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 * 
	 * @return AD_User_ID
	 */
	public int getDoc_User_ID() {
		return getSalesRep_ID();
	} // getDoc_User_ID

	/**
	 * Get Document Approval Amount
	 * 
	 * @return amount
	 */
	public BigDecimal getApprovalAmt() {
		return getGrandTotal();
	} // getApprovalAmt

	/**
	 * 
	 * @param rma
	 */
	public void setRMA(MRMA rma) {
		setM_RMA_ID(rma.getM_RMA_ID());
		setAD_Org_ID(rma.getAD_Org_ID());
		setDescription(rma.getDescription());
		setC_BPartner_ID(rma.getC_BPartner_ID());
		setSalesRep_ID(rma.getSalesRep_ID());

		setGrandTotal(rma.getAmt());
		setIsSOTrx(rma.isSOTrx());
		setTotalLines(rma.getAmt());

		MInvoice originalInvoice = rma.getOriginalInvoice();

		if (originalInvoice == null) {
			throw new IllegalStateException("Not invoiced - RMA: "
					+ rma.getDocumentNo());
		}

		setC_BPartner_Location_ID(originalInvoice.getC_BPartner_Location_ID());
		setAD_User_ID(originalInvoice.getAD_User_ID());
		setC_Currency_ID(originalInvoice.getC_Currency_ID());
		setIsTaxIncluded(originalInvoice.isTaxIncluded());
		setM_PriceList_ID(originalInvoice.getM_PriceList_ID());
		setC_Project_ID(originalInvoice.getC_Project_ID());
		setC_Activity_ID(originalInvoice.getC_Activity_ID());
		setC_Campaign_ID(originalInvoice.getC_Campaign_ID());
		setUser1_ID(originalInvoice.getUser1_ID());
		setUser2_ID(originalInvoice.getUser2_ID());
	}

	/**
	 * Document Status is Complete or Closed
	 * 
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete() {
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds) || DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	} // isComplete

	/**
	 * Obtengo y retorno array con Afectaciones completas en las cuales
	 * participa esta invoice. OpenUp Ltda. Issue # (migracion)
	 * 
	 * @author Gabriel Vila - 17/02/2012
	 * @see
	 * @param trxName
	 * @return
	 */
	public MAllocation[] getInvoiceAllocations(String trxName) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MAllocation> list = new ArrayList<MAllocation>();

		try {

			// Tabla desde la cual obtengo afectaciones depende de
			// parametrizacion del documento de esta invoice
			// OpenUp. Nicolas Sarlabos. 21/12/2012. Se controla que el campo
			// allocationBehavior no sea null
			String tableName = X_UY_AllocationPayment.Table_Name;
			MDocType doc = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(),
					trxName);
			if (doc.getAllocationBehaviour() != null) {
				if (doc.getAllocationBehaviour().equalsIgnoreCase("INV"))
					tableName = X_UY_AllocationInvoice.Table_Name;
			}
			// Fin OpenUp.

			sql = "SELECT DISTINCT hdr.uy_allocation_id "
					+ " FROM uy_allocation hdr " + " INNER JOIN " + tableName
					+ " line "
					+ " ON hdr.uy_allocation_id = line.uy_allocation_id "
					+ " WHERE line.c_invoice_id =? "
					+ " AND hdr.docstatus='CO'";

			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, this.get_ID());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MAllocation value = new MAllocation(Env.getCtx(), rs.getInt(1),
						trxName);
				list.add(value);
			}
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return list.toArray(new MAllocation[list.size()]);
	}

	/**
	 * OpenUP FL, 21/02/2011. Request a Document
	 * 
	 * @return true if success
	*/
	/* 
	 * OpenUp Ltda. Issue # 2301
	 * @author Matías Pérez - 28/11/2014
	 * @see
	 * @return
	 */
	public boolean requestIt() {
		log.info(toString());
		
		this.setDocStatus(DocumentEngine.STATUS_Requested);
		this.setDocAction(ACTION_Approve);
	
		return true;
		

		
	} // requestIt

	// Fin OpenUp
	// OpenUp M.R. 29-06-2011 Issue#697 se crea metod para traer la cantidad
	// total de facturas de un cliente
	public static BigDecimal getTotalInvoiced(int C_BPartner,
			Timestamp dateinvoiced) throws Exception {

		BigDecimal salida = Env.ZERO;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			sql = "Select  "
					+ "CASE WHEN c_doctype.docbasetype = ('ARC') then c_invoice.grandtotal * -1 "
					+ "else grandtotal "
					+ "end as Totalinvoiced "
					+ "from c_invoice "
					+ "Inner join c_doctype on c_doctype.c_doctype_id =  c_invoice.c_doctype_id "
					+ "where c_invoice.issotrx = 'Y' and c_invoice.DocStatus in ('CO','CL') and c_invoice.c_bpartner_id = ? and "
					+ "c_invoice.dateinvoiced <= ?";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner);
			pstmt.setTimestamp(2, dateinvoiced);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				salida = salida.add(rs.getBigDecimal("Totalinvoiced"));
			}
		} catch (Exception e) {
			throw new Exception("No se pudo calcular el total facturado");
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return salida;
	}

	// Fin OpenUp

	public BigDecimal getCantidadBultos() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal salida = Env.ZERO;

		try {
			sql = " select coalesce(sum(qtyentered),0) as cantidad "
					+ " from c_invoiceline " + " where c_invoice_id =?"
					+ " and c_uom_id<>100";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.get_ID());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				salida = rs.getBigDecimal(1);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return salida;
	}

	/**
	 * OpenUp. Gabriel Vila. 24/10/2011. Obtengo y retorno saldo pendiente de
	 * esta invoice segun nuevas estructuras.
	 * 
	 * @return
	 */
	public BigDecimal getAmtOpen() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);

		try {
			sql = "SELECT amtopen " + " FROM alloc_invoiceamtopen "
					+ " WHERE c_invoice_id =?";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.getC_Invoice_ID());
			rs = pstmt.executeQuery();

			if (rs.next())
				value = rs.getBigDecimal(1);
			else
				value = this.getGrandTotal();

			// Si no es una factura, pruebo como nota de credito (recibo de una
			// afectacion).
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;

			sql = "SELECT amtopen " + " FROM alloc_creditnoteamtopen "
					+ " WHERE c_invoice_id =?";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.getC_Invoice_ID());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = rs.getBigDecimal(1);
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return value;
	}

	/**
	 * 
	 * OpenUp. issue #922 Descripcion : Metodo que permite la modificacion del
	 * vendedor segun el rol actual
	 * 
	 * @author Nicolas Sarlabos Fecha : 01/12/2011
	 */

	private void checkSalesRep() {

		// se obtiene el Id del vendedor del cliente actual
		String sql = "SELECT salesrep_id FROM c_bpartner WHERE c_bpartner_id="
				+ this.getC_BPartner_ID();
		int id = DB.getSQLValue(get_TrxName(), sql);
		// se compara ID obtenido con el actualmente ingresado
		if (this.getSalesRep_ID() > 0 && id > 0 && this.getSalesRep_ID() != id) {

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql2 = "";

			try {

				sql2 = "SELECT isactive,isreadwrite" + " FROM ad_window_access"
						+ " WHERE ad_role_id =" + Env.getAD_Role_ID(getCtx())
						+ " AND ad_window_id = 1000000";

				pstmt = DB.prepareStatement(sql2, null);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					// si alguno de los dos campos es igual a "N" entonces no
					// tiene permiso de modificacion
					if (rs.getString("isactive").equalsIgnoreCase("N")
							|| rs.getString("isreadwrite")
									.equalsIgnoreCase("N"))
						throw new AdempiereException(
								"No es posible modificar el vendedor con su rol actual");

				}

			} catch (Exception e) {
				throw new AdempiereException(
						"No es posible modificar el vendedor con su rol actual");

			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;

			}

		}

	}

	@Override
	public boolean applyIt() {

		// Openup. Gabriel Vila. 12/01/2015. Issue #1405.
		// Vale Flete.
		MDocType doc = (MDocType)this.getC_DocTypeTarget();
		
		if (doc.getValue() != null){
			if (doc.getValue().equalsIgnoreCase("valeflete")){
				
				MTRConfig trConfig = MTRConfig.forClientID(getCtx(), this.getAD_Client_ID(), null);
				MTRConfigVFlete configVF = MTRConfigVFlete.forConfig(getCtx(), trConfig.get_ID(), get_TrxName());

				int uyTRTransOrderID = this.get_ValueAsInt("UY_TR_TransOrder_ID");
				if (uyTRTransOrderID <= 0){
					throw new AdempiereException("Debe indicar Numero de Orden de Transporte.");
				}
				
				// Cargo producto para el concepto de pago de flete
				MTRTransOrder torder = new MTRTransOrder(getCtx(), uyTRTransOrderID, null);

				if(!configVF.isAmtManualVF()){
					
					// Si la orden de transporte no tiene monto flete tercero, aviso.
					if ((torder.getPayAmt() == null) || (torder.getPayAmt().compareTo(Env.ZERO) == 0)){
						throw new AdempiereException("La orden de transporte seleccionada NO tiene Monto para Vale Flete.\n" +
													 "Actualice el monto de la orden antes de hacer el vale flete.");
					}
					
					if (torder.getC_Currency_ID_2() <= 0){
						throw new AdempiereException("La orden de transporte seleccionada NO tiene Moneda para Vale Flete.\n" +
													 "Actualice la moneda de la orden antes de hacer el vale flete.");
					}	
					
					int productFleteID = trConfig.getProductValeFlete(torder);
					
					if (productFleteID > 0){
						
						MProduct prod = new MProduct(getCtx(), productFleteID, null);
						
						// Genero nueva linea para el producto vale flete obtenido
						MInvoiceLine iline = new MInvoiceLine(getCtx(), 0, get_TrxName());
						iline.setC_Invoice_ID(this.get_ID());
						iline.setM_Product_ID(productFleteID);
						iline.setPriceEntered(torder.getPayAmt());
						iline.setPriceActual(torder.getPayAmt());
						iline.setQtyEntered(Env.ONE);
						iline.setQtyInvoiced(Env.ONE);
						iline.set_Value("UY_TR_PrintFlete_ID", prod.get_ValueAsInt("UY_TR_PrintFlete_ID"));
						iline.set_Value("IsNeeded", true);
						iline.saveEx();
					}
					
				}			
				
				// Obtengo y despliego Adelantos para este Socio de Negocio 
				this.setAdelantosValeFlete();
			}
		}
		
		// Fin OpenUp. Issue #1405.
		
		return true;
	}

	/***
	 * Obtiene y carga anticipio para conductor de este vale flete.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/01/2015
	 * @see
	 */
	public void setAdelantosValeFlete() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = " select * from alloc_paymentamtopen " +
				  " where c_bpartner_id =? " +
				  " and c_currency_id =? " +
				  " and coalesce(amtopen,0) > 0 ";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.getC_BPartner_ID());
			pstmt.setInt(2, this.getC_Currency_ID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MTRPaymentFlete payf = new MTRPaymentFlete(getCtx(), 0, get_TrxName());
				payf.setC_Invoice_ID(this.get_ID());
				payf.setC_Payment_ID(rs.getInt("c_payment_id"));
				payf.setDateTrx(rs.getTimestamp("datetrx"));
				payf.setC_Currency_ID(rs.getInt("c_currency_id"));
				payf.setamtdocument(rs.getBigDecimal("amtpay"));
				payf.setamtallocated(rs.getBigDecimal("amtallocated"));
				payf.setamtopen(rs.getBigDecimal("amtopen"));
				payf.setIsSelected(true);
				payf.saveEx();
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}

	/***
	 * Obtiene y retorna array de lineas de pago contado para esta invoice.
	 * OpenUp Ltda. Issue #76
	 * 
	 * @author Gabriel Vila - 18/10/2012
	 * @see
	 * @param trxName
	 * @return
	 */
	public List<MInvoiceCashPayment> getCashPaymentLines(String trxName) {

		String whereClause = X_UY_InvoiceCashPayment.COLUMNNAME_C_Invoice_ID
				+ "=" + this.get_ID();

		List<MInvoiceCashPayment> lines = new Query(getCtx(),
				I_UY_InvoiceCashPayment.Table_Name, whereClause, trxName)
				.list();

		return lines;
	}

	/***
	 * Para comprobante con forma de pago del tipo contado, se genera recibo y
	 * afectacion. OpenUp Ltda. Issue #76
	 * 
	 * @author Gabriel Vila - 18/10/2012
	 * @see
	 */
	/**
	 * OpenUp Ltda. Issue #
	 * 
	 * @author Hp - 19/10/2012
	 * @see
	 */
	@SuppressWarnings("unused")
	private void generatePayment() {

		try {
			List<MInvoiceCashPayment> cashlines = this
					.getCashPaymentLines(get_TrxName());

			if ((cashlines == null) || (cashlines.size() <= 0))
				return;

			// Save cabezal recibo
			MPayment payment = new MPayment(getCtx(), 0, get_TrxName());
			// OpenUp v2.5.1 Guillermo Brust. 22/03/2013
			// Se agrega la linea para guardar la organizacion de la factura y
			// no la del contexto, osea que no toma la organizacion en la que se
			// loguea el usuario
			// sino la de la factura, dado el usuario se puede loguear en * y
			// querer hacer una factura para cualquier otra organizacion.
			payment.setAD_Org_ID(this.getAD_Org_ID());
			// Fin openUp
			payment.setDocumentNo(this.getDocumentNo());
			payment.setDateTrx(this.getDateInvoiced());
			payment.setDateAcct(this.getDateAcct());
			payment.setIsReceipt(this.isSOTrx());
			payment.setDefaultDocTypeID();
			payment.setTrxType("S");
			payment.setC_BPartner_ID(this.getC_BPartner_ID());
			payment.setTenderType(X_UY_MediosPago.TENDERTYPE_Check);
			payment.setC_Currency_ID(this.getC_Currency_ID());
			payment.setPayAmt(this.getGrandTotal());
			payment.setIsApproved(true);
			payment.setC_ConversionType_ID(this.getC_ConversionType_ID());
			payment.setDescription("Generado desde Comprobante :"
					+ this.getDocumentNo());
			payment.setDateAcct(this.getDateAcct());
			payment.setUY_SubTotal(this.getGrandTotal());
			payment.saveEx();

			// Save lineas recibo
			for (MInvoiceCashPayment cashline : cashlines) {

				MLinePayment linepay = new MLinePayment(getCtx(), 0,
						get_TrxName());
				linepay.setUY_PaymentRule_ID(cashline.getUY_PaymentRule_ID());
				linepay.setDocumentNo((cashline.getnrodoc() == null) ? payment
						.getDocumentNo() : cashline.getnrodoc());
				linepay.setTenderType(cashline.getTenderType());
				linepay.setDateTrx(payment.getDateTrx());
				linepay.setDueDate(payment.getDateTrx());
				linepay.setPayAmt(cashline.getPayAmt());
				linepay.setC_BankAccount_ID(cashline.getC_BankAccount_ID());
				linepay.setC_Payment_ID(payment.get_ID());
				if (cashline.getUY_MediosPago_ID() > 0) {
					linepay.setUY_MediosPago_ID(cashline.getUY_MediosPago_ID());
					linepay.setTenderType(MPayment.TENDERTYPE_Check);
				}
				linepay.saveEx();
			}

			// Afectacion del recibo con esta factura
			MAllocDirectPayment alloc = new MAllocDirectPayment(getCtx(), 0,
					get_TrxName());
			alloc.setC_Payment_ID(payment.get_ID());
			alloc.setC_DocType_ID(this.getC_DocType_ID());
			alloc.setDocumentNo(this.getDocumentNo());
			alloc.setC_Currency_ID(this.getC_Currency_ID());
			alloc.setC_Invoice_ID(this.get_ID());
			alloc.setamtdocument(this.getGrandTotal());
			alloc.setamtopen(this.getGrandTotal());
			alloc.setamtallocated(this.getGrandTotal());
			alloc.setsign(new BigDecimal(-1));
			alloc.setdatedocument(this.getDateInvoiced());
			alloc.saveEx();

			// Completo recibo
			if (!payment.processIt(DocumentEngine.ACTION_Complete)) {
				throw new AdempiereException(payment.getProcessMsg());
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/***
	 * Obtiene suma de lineas agrupadas por producto y centro de costo 1. OpenUp
	 * Ltda. Issue #49
	 * 
	 * @author Gabriel Vila - 26/12/2012
	 * @see
	 * @return
	 */
	public List<InvoiceLineAmtByProdAct> getLinesSumByProdAct() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<InvoiceLineAmtByProdAct> lines = new ArrayList<InvoiceLineAmtByProdAct>();

		try {

			sql = " SELECT m_product_id, c_activity_id_1, COALESCE(SUM(linenetamt),0) as saldo,"
					+ " COALESCE(SUM(qtyinvoiced),0) as cantidad "
					+ " FROM c_invoiceline "
					+ " WHERE c_invoice_id =? "
					+ " GROUP BY m_product_id, c_activity_id_1";
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.get_ID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				InvoiceLineAmtByProdAct value = new InvoiceLineAmtByProdAct();
				value.cInvoiceID = this.get_ID();
				value.sumAmt = rs.getBigDecimal("saldo");
				value.sumQty = rs.getBigDecimal("cantidad");
				value.mProductID = rs.getInt("m_product_id");
				value.cActivityID = rs.getInt("c_activity_id_1");
				lines.add(value);
			}
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return lines;

	}

	/***
	 * Obtiene y retorna lineas de provision para esta factura y determinado
	 * producto - centro de costo. OpenUp Ltda. Issue #49
	 * 
	 * @author Gabriel Vila - 26/12/2012
	 * @see
	 * @param mProductID
	 * @param cActivityID1
	 * @return
	 */
	public List<MInvoiceProvision> getProvisionLinesByProdAct(int mProductID,
			int cActivityID1) {

		String whereClause = X_UY_Invoice_Provision.COLUMNNAME_C_Invoice_ID
				+ "=" + this.get_ID() + " AND "
				+ X_UY_Invoice_Provision.COLUMNNAME_M_Product_ID + "="
				+ mProductID + " AND "
				+ X_UY_Invoice_Provision.COLUMNNAME_C_Activity_ID_1 + "="
				+ cActivityID1;

		List<MInvoiceProvision> lines = new Query(getCtx(),
				I_UY_Invoice_Provision.Table_Name, whereClause, get_TrxName())
				.list();

		return lines;
	}

	/***
	 * Obtiene suma de cantidades facturadas para un determinado producto en una
	 * determinada factura. OpenUp Ltda. Issue #116
	 * 
	 * @author Gabriel Vila - 03/03/2013
	 * @see
	 * @param ctx
	 * @param record_ID
	 * @param m_Product_ID
	 * @param object
	 * @return
	 */
	public static BigDecimal getProductTotalQty(Properties ctx, int cInvoiceID,
			int mProductID, String trxName) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO;

		try {
			sql = " SELECT COALESCE(SUM(QtyInvoiced),0) as cantidad "
					+ " FROM c_invoiceline " + " WHERE c_invoice_id =? "
					+ " and m_product_id =?";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, cInvoiceID);
			pstmt.setInt(2, mProductID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = rs.getBigDecimal(1);
			}
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return value;

	}

	/***
	 * Incrementa la cantidad pendiente en la línea de la órden de fabricación y
	 * la deja como pendiente todavia. OpenUp Ltda.
	 * 
	 * @author Guillermo Brust - 30/04/2013
	 * @param line
	 * @see
	 */
	private void processNCManufOrder(MInvoiceLine line) {

		if (line != null) {

			// obtengo linea de orden
			MManufLine manufLine = MManufLine.getMManufLineForOrderAndProduct(
					this.getCtx(), line.getUY_ManufOrder_ID(),
					line.getM_Product_ID());

			// seteo total de cantidad facturada
			manufLine.setQtyInvoiced(manufLine.getQtyInvoiced().subtract(
					line.getQtyInvoiced()));

			// lo dejo como pendiente
			if (manufLine.getQty().compareTo(manufLine.getQtyInvoiced()) > 0)
				manufLine.setIsPending(true);
			manufLine.saveEx();

		}

	}

	/***
	 * Genera factura de exportacion a partir de proforma. OpenUp Ltda. Issue
	 * #1155
	 * 
	 * @author Nicolas Sarlabos - 30/07/2013
	 * @see
	 * @return
	 */
	public void generateInvExport() {

		MExportInvTracking track = null;
		String sql = "";
		int trackID = 0;

		try {

			sql = "select uy_exportinvtracking_id from uy_exportinvtracking where proforminvoice = "
					+ this.get_ID();
			trackID = DB.getSQLValueEx(get_TrxName(), sql);

			if (trackID <= 0)
				throw new AdempiereException(
						"No se pudo obtener registro en tabla de tracking");

			track = new MExportInvTracking(getCtx(), trackID, get_TrxName());

			if (track.getexportinvoice() > 0
					&& track.getdocstatus_export().equalsIgnoreCase("CO"))
				throw new AdempiereException(
						"Existe una factura de exportacion en estado completo para la proforma actual");

			MInvoice newInv = new MInvoice(getCtx(), 0, get_TrxName()); // instancio
																		// nuevo
																		// documento
																		// a
																		// crear

			MDocType doc = MDocType.forValue(getCtx(), "expinv", get_TrxName());

			if (doc.get_ID() == 0)
				throw new AdempiereException(
						"No se encontro documento Factura Exportacion");

			// comienzo a setear atributos...
			newInv.setAD_Client_ID(this.getAD_Client_ID());
			newInv.setAD_Org_ID(this.getAD_Org_ID());
			newInv.setAD_User_ID(this.getAD_User_ID());
			newInv.setC_DocType_ID(doc.getC_DocType_ID());
			newInv.setC_DocTypeTarget_ID(doc.getC_DocType_ID());
			newInv.setDescription(this.getDescription());
			newInv.setSalesRep_ID(this.getSalesRep_ID());
			newInv.setDateInvoiced(this.getDateInvoiced());
			newInv.setDueDate(this.getDueDate());
			newInv.setDateAcct(this.getDateAcct());
			newInv.setC_BPartner_ID(this.getC_BPartner_ID());
			newInv.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			newInv.setC_Activity_ID(this.getC_Activity_ID());
			newInv.setC_Currency_ID(this.getC_Currency_ID());
			newInv.setUY_PaymentRule_ID(this.getUY_PaymentRule_ID());
			newInv.setpaymentruletype(this.getpaymentruletype());
			newInv.setC_PaymentTerm_ID(this.getC_PaymentTerm_ID());
			newInv.setM_PriceList_ID(this.getM_PriceList_ID());
			newInv.setTotalLines(this.getTotalLines());
			newInv.setGrandTotal(this.getGrandTotal());
			newInv.setUY_Budget_ID(this.getUY_Budget_ID());
			newInv.setM_Warehouse_ID(this.getM_Warehouse_ID());
			newInv.setproforminvoice_text(this.getproforminvoice_text());
			newInv.setembalaje(this.getembalaje());
			newInv.setnumeracion(this.getnumeracion());
			newInv.setuy_cantbultos_manual(this.getuy_cantbultos_manual());
			newInv.setmarcas(this.getmarcas());
			newInv.setpesoNeto(this.getpesoNeto());
			newInv.setpesoBruto(this.getpesoBruto());
			newInv.setVolume(this.getVolume());
			newInv.setincoterms(this.getincoterms());
			newInv.settransporte(this.gettransporte());
			newInv.setC_Country_ID(this.getC_Country_ID());
			newInv.setUY_Departamentos_ID(this.getUY_Departamentos_ID());
			newInv.setC_Country_ID_1(this.getC_Country_ID_1());
			newInv.setUY_Departamentos_ID_1(this.getUY_Departamentos_ID_1());
			newInv.setProcessing(false);
			newInv.setProcessed(false);
			newInv.setDocStatus(DocumentEngine.STATUS_Drafted);
			newInv.setDocAction(DocumentEngine.ACTION_Complete);
			newInv.saveEx(); // guardo el nuevo cabezal

			// si se grabo el cabezal procedo a crear las lineas
			if (newInv.get_ID() > 0) {

				MInvoiceLine[] invlines = this.getLines();

				for (int i = 0; i < invlines.length; i++) {

					MInvoiceLine line = new MInvoiceLine(getCtx(), 0,
							get_TrxName()); // instancio nueva linea
					MInvoiceLine actual = new MInvoiceLine(getCtx(),
							invlines[i].get_ID(), get_TrxName()); // instancio
																	// linea de
																	// la
																	// recorrida

					line.setC_Invoice_ID(newInv.get_ID());
					line.setAD_Org_ID(actual.getAD_Org_ID());
					line.setAD_Client_ID(actual.getAD_Client_ID());
					line.setLine(line.getLine());
					line.setM_Product_ID(actual.getM_Product_ID());
					line.setQtyInvoiced(actual.getQtyInvoiced());
					line.setPriceList(actual.getPriceList());
					line.setPriceActual(actual.getPriceActual());
					line.setPriceLimit(actual.getPriceLimit());
					line.setLineNetAmt(actual.getLineNetAmt());
					line.setC_UOM_ID(actual.getC_UOM_ID());
					line.setC_Tax_ID(actual.getC_Tax_ID());
					line.setLineTotalAmt(actual.getLineTotalAmt());
					line.setQtyEntered(actual.getQtyEntered());
					line.setPriceEntered(actual.getPriceEntered());
					line.setFlatDiscount(actual.getFlatDiscount());
					line.setnum_budget(actual.getnum_budget());
					line.setDescription(actual.getDescription());

					line.saveEx();
				}

			}

			// seteo el Id de la factura exportacion a la proforma
			this.setUY_Invoice_ID(newInv.get_ID());
			this.saveEx();

			// inserto en tabla de tracking
			track.setexportinvoice(newInv.get_ID());
			track.setdoctype_export(doc.getC_DocType_ID());
			track.saveEx();

			// Completo factura exportacion
			if (!newInv.processIt(DocumentEngine.ACTION_Complete)) {
				throw new AdempiereException(newInv.getProcessMsg());
			}

			ADialog.info(0, null,
					"Factura de exportacion N° " + newInv.getDocumentNo()
							+ " generada con exito");

		} catch (Exception e) {
			throw new AdempiereException(e);

		}

	}

	/***
	 * Metodo que clona una proforma. OpenUp Ltda. Issue #1155
	 * 
	 * @author Nicolas Sarlabos - 01/08/2013
	 * @see
	 * @return
	 */
	public void cloneProform() {

		MExportInvTracking track = null;
		String sql = "";
		int trackID = 0;

		try {

			sql = "select uy_exportinvtracking_id from uy_exportinvtracking where proforminvoice = "
					+ this.get_ID();
			trackID = DB.getSQLValueEx(get_TrxName(), sql);

			if (trackID <= 0)
				throw new AdempiereException(
						"No se pudo obtener registro en tabla de tracking");

			track = new MExportInvTracking(getCtx(), trackID, get_TrxName());

			if (track.getexportinvoice() > 0
					&& track.getdocstatus_export().equalsIgnoreCase("CO"))
				throw new AdempiereException(
						"Existe una factura de exportacion en estado completo para la proforma actual");

			MInvoice newInv = new MInvoice(getCtx(), 0, get_TrxName()); // instancio
																		// nuevo
																		// documento
																		// a
																		// crear

			// comienzo a setear atributos...
			newInv.setAD_Client_ID(this.getAD_Client_ID());
			newInv.setAD_Org_ID(this.getAD_Org_ID());
			newInv.setAD_User_ID(this.getAD_User_ID());
			newInv.setC_DocType_ID(this.getC_DocType_ID());
			newInv.setC_DocTypeTarget_ID(this.getC_DocType_ID());
			newInv.setDescription(this.getDescription());
			newInv.setSalesRep_ID(this.getSalesRep_ID());
			newInv.setDateInvoiced(this.getDateInvoiced());
			newInv.setDateAcct(this.getDateAcct());
			newInv.setC_BPartner_ID(this.getC_BPartner_ID());
			newInv.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			newInv.setC_Activity_ID(this.getC_Activity_ID());
			newInv.setUY_Budget_ID(this.getUY_Budget_ID());
			newInv.setC_Currency_ID(this.getC_Currency_ID());
			newInv.setPaymentRule(this.getPaymentRule());
			newInv.setUY_PaymentRule_ID(this.getUY_PaymentRule_ID());
			newInv.setpaymentruletype(this.getpaymentruletype());
			newInv.setC_PaymentTerm_ID(this.getC_PaymentTerm_ID());
			newInv.setM_PriceList_ID(this.getM_PriceList_ID());
			newInv.setTotalLines(this.getTotalLines());
			newInv.setGrandTotal(this.getGrandTotal());
			newInv.setM_Warehouse_ID(this.getM_Warehouse_ID());
			newInv.setproforminvoice_text(this.getproforminvoice_text());
			newInv.setembalaje(this.getembalaje());
			newInv.setuy_cantbultos_manual(this.getuy_cantbultos_manual());
			newInv.setnumeracion(this.getnumeracion());
			newInv.setmarcas(this.getmarcas());
			newInv.setpesoNeto(this.getpesoNeto());
			newInv.setpesoBruto(this.getpesoBruto());
			newInv.setVolume(this.getVolume());
			newInv.setincoterms(this.getincoterms());
			newInv.settransporte(this.gettransporte());
			newInv.setC_Country_ID(this.getC_Country_ID());
			newInv.setUY_Departamentos_ID(this.getUY_Departamentos_ID());
			newInv.setC_Country_ID_1(this.getC_Country_ID_1());
			newInv.setUY_Departamentos_ID_1(this.getUY_Departamentos_ID_1());
			newInv.setProcessing(false);
			newInv.setProcessed(false);
			newInv.setDocStatus(DocumentEngine.STATUS_Drafted);
			newInv.setDocAction(DocumentEngine.ACTION_Complete);
			newInv.saveEx(); // guardo el nuevo cabezal

			// si se grabo el cabezal procedo a crear las lineas
			if (newInv.get_ID() > 0) {

				// elimino lineas para evitar duplicadas, ya que al guardar el
				// cabezal se han cargado las lineas del presupuesto seteado
				sql = "delete from c_invoiceline where c_invoice_id = "
						+ newInv.get_ID();
				DB.executeUpdateEx(sql, get_TrxName());

				MInvoiceLine[] invlines = this.getLines();

				for (int i = 0; i < invlines.length; i++) {

					MInvoiceLine line = new MInvoiceLine(getCtx(), 0,
							get_TrxName()); // instancio nueva linea
					MInvoiceLine actual = new MInvoiceLine(getCtx(),
							invlines[i].get_ID(), get_TrxName()); // instancio
																	// linea de
																	// la
																	// recorrida

					line.setC_Invoice_ID(newInv.get_ID());
					line.setAD_Org_ID(actual.getAD_Org_ID());
					line.setAD_Client_ID(actual.getAD_Client_ID());
					line.setLine(line.getLine());
					line.setM_Product_ID(actual.getM_Product_ID());
					line.setQtyInvoiced(actual.getQtyInvoiced());
					line.setPriceList(actual.getPriceList());
					line.setPriceActual(actual.getPriceActual());
					line.setPriceLimit(actual.getPriceLimit());
					line.setLineNetAmt(actual.getLineNetAmt());
					line.setC_UOM_ID(actual.getC_UOM_ID());
					line.setC_Tax_ID(actual.getC_Tax_ID());
					line.setLineTotalAmt(actual.getLineTotalAmt());
					line.setQtyEntered(actual.getQtyEntered());
					line.setPriceEntered(actual.getPriceEntered());
					line.setFlatDiscount(actual.getFlatDiscount());
					line.setnum_budget(actual.getnum_budget());
					line.setDescription(actual.getDescription());

					line.saveEx();
				}
			}

			String value = this.getDocumentNo().substring(1);

			int posFinal = 0;

			for (int i = 0; i < value.length(); i++) {

				if (value.charAt(i) == '_') {

					posFinal = i;
					i = value.length();

				}
			}

			String sequence = value.substring(0, posFinal);
			int nro = Integer.parseInt(sequence);

			// seteo como secuencia siguiente del documento la del documento
			// actual, de modo de solamente incrementar su version
			sql = "update ad_sequence set currentnext = " + nro
					+ " where lower (name) like 'uy_proforma'";
			DB.executeUpdateEx(sql, get_TrxName());

			// Anulo proforma anterior
			this.setDocStatusReason("Se genera nuevo documento");

			if (!this.processIt(DocumentEngine.ACTION_Void)) {
				throw new AdempiereException(this.getProcessMsg());
			}

			this.saveEx();

			ADialog.info(0, null, "Proforma N° " + newInv.getDocumentNo()
					+ " clonada con exito");

		} catch (Exception e) {
			throw new AdempiereException(e);

		}

	}

	/***
	 * Para facturas contado (compra y venta) se generan los medios de pago
	 * directo y ya no se hace recibo y afectacion. OpenUp Ltda. Issue #1261
	 * 
	 * @author Gabriel Vila - 27/08/2013
	 * @see
	 */
	private void processCashInvoice() {

		String action = "";
		BigDecimal sumContados = Env.ZERO;

		try {
							
			// Obtengo y recorro medios de pago de este comprobante contado
			List<MInvoiceCashPayment> cashlines = this
					.getCashPaymentLines(get_TrxName());
			if ((cashlines == null) || (cashlines.size() <= 0)) {
				throw new AdempiereException(
						"Debe indicar medios de Pago para Comprobante de tipo Contado.");
			}

			for (MInvoiceCashPayment cashline : cashlines) {

				sumContados = sumContados.add(cashline.getPayAmt());

				MBankAccount ba = (MBankAccount) cashline.getC_BankAccount();
				MBank bank = (MBank) ba.getC_Bank();

				// Si es comprobante de Venta
				if (this.isSOTrx()) {

					// Si este banco se maneja como banco (o sea maneja medios
					// de pago como cheques)
					// y la cuenta bancaria es de terceros
					if (bank.isBankHandler() && !ba.isPublic()) {

						MMediosPago mediopago = new MMediosPago(getCtx(), 0,
								get_TrxName());
						mediopago.setDateTrx(cashline.getDateFrom());
						mediopago.setDateAcct(cashline.getDateFrom());
						mediopago.setDateFrom(cashline.getDateFrom());
						mediopago.setDueDate(cashline.getDueDate());
						mediopago.setC_DocType_ID(MDocType
								.getDocType(Doc.DOCTYPE_Cheque));
						mediopago.setC_BankAccount_ID(ba.get_ID());
						mediopago.setC_BPartner_ID(this.getC_BPartner_ID());
						mediopago
								.setCheckNo((cashline.getnrodoc() == null) ? this
										.getDocumentNo() : cashline.getnrodoc());
						mediopago.setC_Currency_ID(cashline.getC_Currency_ID());
						mediopago.setPayAmt(cashline.getAmount());
						mediopago.settipomp(X_UY_MediosPago.TIPOMP_Terceros);
						mediopago.setestado(X_UY_MediosPago.ESTADO_Cartera);
						mediopago.setoldstatus(X_UY_MediosPago.ESTADO_Cartera);
						mediopago.setPosted(true);
						mediopago.setProcessed(true);
						mediopago.setProcessing(false);
						mediopago.setDocAction(DocumentEngine.ACTION_None);
						mediopago.setDocStatus(DocumentEngine.STATUS_Completed);
						mediopago.setUY_MovBancariosHdr_ID(0);
						mediopago.setUY_MovBancariosLine_ID(0);
						mediopago
								.setUY_InvoiceCashPayment_ID(cashline.get_ID());
						mediopago.saveEx();

						// Actualizo asociacion de linea contado del comprobante
						// con medio de pago creado
						action = " update uy_invoicecashpayment set uy_mediospago_id ="
								+ mediopago.get_ID()
								+ " where uy_invoicecashpayment_id ="
								+ cashline.get_ID();
						DB.executeUpdateEx(action, get_TrxName());
					}
				} else {
					// Es un comprobante de Compra

					// Si este banco se maneja como banco (o sea maneja medios
					// de pago como cheques)
					// y la cuenta bancaria es propia
					if (bank.isBankHandler()) {

						MMediosPago mediopago = (MMediosPago) cashline
								.getUY_MediosPago();

						// Valido que este medio de pago no haya sido entregado
						// en otro comprobante
						if (!mediopago.getestado().equalsIgnoreCase(
								X_UY_MediosPago.ESTADO_Emitido)
								&& !mediopago.getestado().equalsIgnoreCase(
										X_UY_MediosPago.ESTADO_Cartera)) {
							throw new AdempiereException(
									"El Medio de Pago Numero : "
											+ mediopago.getCheckNo()
											+ " ya fue entregado en otro comprobante.");
						}

						mediopago
								.setUY_InvoiceCashPayment_ID(cashline.get_ID());
						mediopago.setestado(X_UY_MediosPago.ESTADO_Entregado);
						mediopago.setoldstatus(mediopago.getestado());
						mediopago.setUY_MovBancariosHdr_ID(0);
						mediopago.setUY_MovBancariosLine_ID(0);
						mediopago.saveEx();
					}
				}
			}

			if (!this.verifyAmounts(sumContados)) { // OpenUp. Nicolas Sarlabos.
													// 19/05/2014. #2097.
				throw new AdempiereException(
						"La suma de pagos contado debe ser igual al Total del comprobante.");
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Verifica que la suma de pagos contado sea igual al total del documento.
	 * OpenUp Ltda. Issue #2097
	 * 
	 * @author Nicolas Sarlabos - 19/05/2014
	 * @see
	 * @return
	 */
	private boolean verifyAmounts(BigDecimal sumContados) {

		String sql = "";
		BigDecimal bordeInferior = new BigDecimal(-0.99);
		BigDecimal bordeSuperior = new BigDecimal(0.99);

		// obtengo cantidad de lineas de pago con diferente moneda a la del
		// cabezal
		sql = "select count(uy_invoicecashpayment_id)"
				+ " from uy_invoicecashpayment" + " where c_invoice_id = "
				+ this.get_ID() + " and c_currency_id <> "
				+ this.getC_Currency_ID();
		int count = DB.getSQLValueEx(get_TrxName(), sql);

		if (count > 0) {

			BigDecimal diff = sumContados.subtract(this.getGrandTotal()); // obtengo
																			// la
																			// diferencia
																			// entre
																			// importes

			if (diff.compareTo(bordeInferior) < 0
					|| diff.compareTo(bordeSuperior) > 0)
				return false;

		} else if (sumContados.compareTo(this.getGrandTotal()) != 0)
			return false;

		return true;
	}

	/***
	 * Al anularse un comprobante contado, se actualizan los medios de pago
	 * asociados al mismo. OpenUp Ltda. Issue #1261
	 * 
	 * @author Gabriel Vila - 27/08/2013
	 * @see
	 */
	private void voidInvoiceCash() {

		String action = "";

		try {
			// Si es contado venta
			if (this.isSOTrx()) {
				action = " delete from uy_mediospago "
						+ " where uy_mediospago_id in "
						+ " (select uy_mediospago_id from uy_invoicecashpayment "
						+ " where c_invoice_id=" + this.get_ID() + ")";
				DB.executeUpdateEx(action, get_TrxName());
			} else {
				// Es contado compra

				// Actualizo cheques propios
				action = " update uy_mediospago set estado='"
						+ X_UY_MediosPago.ESTADO_Emitido
						+ "'"
						+ ", oldstatus='"
						+ X_UY_MediosPago.ESTADO_Emitido
						+ "'"
						+ ", uy_invoicecashpayment_id = null "
						+ " from uy_invoicecashpayment lp "
						+ " where uy_mediospago.uy_invoicecashpayment_id = lp.uy_invoicecashpayment_id "
						+ " and lp.c_invoice_id = " + this.get_ID()
						+ " and uy_mediospago.tipomp='"
						+ X_UY_MediosPago.TIPOMP_Propio + "'";

				DB.executeUpdateEx(action, get_TrxName());

				// Actualizo cheques de terceros utilizados en este recibo a
				// proveedor
				action = " update uy_mediospago set estado='"
						+ X_UY_MediosPago.ESTADO_Cartera
						+ "'"
						+ ", oldstatus='"
						+ X_UY_MediosPago.ESTADO_Cartera
						+ "'"
						+ ", uy_invoicecashpayment_id = null "
						+ " from uy_invoicecashpayment lp "
						+ " where uy_mediospago.uy_invoicecashpayment_id = lp.uy_invoicecashpayment_id "
						+ " and lp.c_invoice_id = " + this.get_ID()
						+ " and uy_mediospago.tipomp='"
						+ X_UY_MediosPago.TIPOMP_Terceros + "'";

				DB.executeUpdateEx(action, get_TrxName());
			}

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

	}

	/***
	 * Metodo que actualiza el documento de reposicion de FF insertando o
	 * actualizando una linea para este documento. Recibe como parametro un
	 * boolean que indica si el monto sera positivo (factura) o no (ingreso).
	 * Para facturas: la primer factura que se complete sustituira a la
	 * rendicion que la genero, para las siguientes facturas se registran lineas
	 * nuevas en el documento de reposicion. OpenUp Ltda. Issue #1426
	 * 
	 * @author Nicolas Sarlabos - 30/12/2013
	 * @see
	 * @return
	 */
	private void updateReplenish(boolean positive) {

		MFFReplenishLine rline = null;
		MFFCashOut cashRepay = null;
		MFFReplenish replenish = null;

		// si es factura de FF
		if (positive) {

			cashRepay = new MFFCashOut(getCtx(), this.getUY_FF_CashOut_ID(),
					get_TrxName()); // obtengo la rendicion asociada a la
			// factura actual

			if (cashRepay.get_ID() <= 0)
				throw new AdempiereException(
						"Error al obtener rendicion de fondo fijo asociada a la factura");

			// obtengo documento de reposicion en curso para la sucursal y
			// moneda actual
			replenish = MFFReplenish.forBranchCurrency(getCtx(),
					cashRepay.getUY_FF_Branch_ID(),
					cashRepay.getC_Currency_ID(), get_TrxName());

			if (replenish == null)
				throw new AdempiereException(
						"No se encontro documento de reposicion de fondo fijo en curso, por favor verifique");

			// obtengo la linea de reposicion asociada a la rendicion de FF,
			// para poder actualizarla
			rline = MFFReplenishLine.forTableReplenishLine(getCtx(),
					cashRepay.getC_DocType_ID(), X_UY_FF_CashOut.Table_ID,
					cashRepay.get_ID(), replenish.get_ID(), get_TrxName());

			if (rline != null) { // actualizo linea existente

				rline.setDateTrx(this.getDateInvoiced());
				rline.setC_BPartner_ID(this.getC_BPartner_ID());
				rline.setRecord_ID(this.get_ID());
				rline.setAD_Table_ID(I_C_Invoice.Table_ID);
				rline.setC_DocType_ID(this.getC_DocTypeTarget_ID());
				rline.setDocumentNo(this.getDocumentNo());
				rline.setChargeName(getChargeNames(this.get_ID()));
				rline.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, 0,
						"AD_User_ID"));
				rline.setAmount(this.getGrandTotal());
				rline.setDescription(cashRepay.getDescription());
				rline.saveEx();

			} else {

				// PARA CASO DE REACTIVACION!!!
				// obtengo la linea de factura ya existente para poder
				// actualizarla
				rline = MFFReplenishLine.forTableReplenishLine(getCtx(),
						this.getC_DocTypeTarget_ID(), X_C_Invoice.Table_ID,
						this.get_ID(), 0, get_TrxName());

				if (rline != null) { // actualizo linea existente

					rline.setDateTrx(this.getDateInvoiced());
					rline.setC_BPartner_ID(this.getC_BPartner_ID());
					rline.setRecord_ID(this.get_ID());
					rline.setAD_Table_ID(I_C_Invoice.Table_ID);
					rline.setC_DocType_ID(this.getC_DocTypeTarget_ID());
					rline.setDocumentNo(this.getDocumentNo());
					rline.setChargeName(getChargeNames(this.get_ID()));
					rline.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, 0,
							"AD_User_ID"));
					rline.setAmount(this.getGrandTotal());
					rline.setDescription(cashRepay.getDescription());
					rline.saveEx();

				} else {

					// inserto nueva linea
					rline = new MFFReplenishLine(getCtx(), 0, get_TrxName());
					rline.setUY_FF_Replenish_ID(replenish.get_ID());
					rline.setDateTrx(this.getDateInvoiced());
					rline.setC_BPartner_ID(this.getC_BPartner_ID());
					rline.setRecord_ID(this.get_ID());
					rline.setAD_Table_ID(I_C_Invoice.Table_ID);
					rline.setC_DocType_ID(this.getC_DocTypeTarget_ID());
					rline.setDocumentNo(this.getDocumentNo());
					rline.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, 0,
							"AD_User_ID"));
					rline.setAmount(this.getGrandTotal());
					rline.setDescription(cashRepay.getDescription());
					rline.setChargeName(getChargeNames(this.get_ID()));
					// rline.setChargeName(MFFCashOut.getChargeNames(cashRepay.get_ID()));
					rline.setApprovedBy(cashRepay.getApprovedBy());
					rline.saveEx();

				}

			}

		} else { // si es ingreso de FF

			// obtengo documento de reposicion en curso para la sucursal y
			// moneda actual
			replenish = MFFReplenish.forBranchCurrency(getCtx(),
					this.getUY_FF_Branch_ID(), this.getC_Currency_ID(),
					get_TrxName());

			if (replenish == null)
				throw new AdempiereException(
						"No se encontro documento de reposicion de fondo fijo en curso, por favor verifique");

			// obtengo la linea de reposicion, si existe, para poder
			// actualizarla
			rline = MFFReplenishLine.forTableReplenishLine(getCtx(),
					this.getC_DocTypeTarget_ID(), X_C_Invoice.Table_ID,
					this.get_ID(), replenish.get_ID(), get_TrxName());

			if (rline != null) { // actualizo linea existente

				rline.setUY_FF_Replenish_ID(replenish.get_ID());
				rline.setDateTrx(this.getDateInvoiced());
				rline.setC_BPartner_ID(this.getC_BPartner_ID());
				rline.setRecord_ID(this.get_ID());
				rline.setAD_Table_ID(I_C_Invoice.Table_ID);
				rline.setC_DocType_ID(this.getC_DocTypeTarget_ID());
				rline.setDocumentNo(this.getDocumentNo());
				rline.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, 0,
						"AD_User_ID"));
				rline.setAmount(this.getGrandTotal().negate());

				if (this.getDescription() != null
						&& !this.getDescription().equalsIgnoreCase("")) {
					rline.setDescription(this.getDescription());
				} else
					rline.setDescription("--");

				rline.setChargeName(getChargeNames(this.get_ID()));
				rline.setApprovedBy("--");
				rline.saveEx();

			} else {

				// inserto nueva linea
				rline = new MFFReplenishLine(getCtx(), 0, get_TrxName());
				rline.setUY_FF_Replenish_ID(replenish.get_ID());
				rline.setDateTrx(this.getDateInvoiced());
				rline.setC_BPartner_ID(this.getC_BPartner_ID());
				rline.setRecord_ID(this.get_ID());
				rline.setAD_Table_ID(I_C_Invoice.Table_ID);
				rline.setC_DocType_ID(this.getC_DocTypeTarget_ID());
				rline.setDocumentNo(this.getDocumentNo());
				rline.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, 0,
						"AD_User_ID"));
				rline.setAmount(this.getGrandTotal().negate());

				if (this.getDescription() != null
						&& !this.getDescription().equalsIgnoreCase("")) {
					rline.setDescription(this.getDescription());
				} else
					rline.setDescription("--");

				rline.setChargeName(getChargeNames(this.get_ID()));
				rline.setApprovedBy("--");
				rline.saveEx();

			}
		}

	}

	/***
	 * Metodo que devuelve un string con el total de centros de costo de este
	 * documento. OpenUp Ltda. Issue #1420
	 * 
	 * @author Nicolas Sarlabos - 06/01/2014
	 * @see
	 * @return
	 */
	public static String getChargeNames(int invoiceID) {

		String value = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "select distinct name"
					+ " from c_activity"
					+ " where c_activity_id in (select c_activity_id_1::integer from c_invoiceline where c_invoice_id = "
					+ invoiceID + ")";

			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				if (value == null) {

					value = rs.getString("name");

				} else {

					value += "/" + rs.getString("name");

				}

			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return value;
	}

	/***
	 * Metodo que verifica que el ingreso de FF a anular no se encuentre en un
	 * documento de reposicion en curso. OpenUp Ltda. Issue #1422
	 * 
	 * @author Nicolas Sarlabos - 09/01/2014
	 * @see
	 * @return
	 */
	private void validateVoidIngresoFF() {

		// obtengo linea de reposicion
		MFFReplenishLine rline = MFFReplenishLine.forTableReplenishLine(
				getCtx(), this.getC_DocType_ID(), X_C_Invoice.Table_ID,
				this.get_ID(), 0, get_TrxName());

		if (rline != null) {

			MFFReplenish replenish = new MFFReplenish(getCtx(),
					rline.getUY_FF_Replenish_ID(), get_TrxName()); // instancio
																	// cabezal
																	// de doc de
																	// reposicion

			if (!replenish.getDocStatus().equalsIgnoreCase("CO")) { // solo si
																	// la
																	// reposicion
																	// no esta
																	// completa
																	// puedo
																	// anular y
																	// actualizar

				rline.deleteEx(true);

			} else
				throw new AdempiereException(
						"Imposible anular por estar el ingreso en el documento de reposicion N° "
								+ replenish.getDocumentNo()
								+ " en estado completo");
		}

	}

	/***
	 * Setea literal de factura en dos lineas. OpenUp Ltda. Issue #1633
	 * 
	 * @author Nicolas Sarlabos - 19/03/2014
	 * @see
	 */
	public void setLiteral() {

		try {

			this.setLiteralNumber(null);
			this.setLiteralNumber2(null);

			MCurrency cur = new MCurrency(getCtx(), this.getC_Currency_ID(),
					get_TrxName());

			String curDescription = cur.getDescription(); // obtengo la
															// descripcion de la
															// moneda

			Converter conv = new Converter();

			int top1 = MSysConfig.getIntValue("UY_CHECK_LITERAL_LENGTH_1", 70,
					getAD_Client_ID());
			// int top2 = MSysConfig.getIntValue("UY_CHECK_LITERAL_LENGTH_2",
			// 40, getAD_Client_ID());

			String literal = conv.getStringOfBigDecimal(this.getGrandTotal());

			System.out.println(literal + " : " + this.getGrandTotal());

			literal = curDescription + ":" + " " + literal;

			if (literal.length() <= top1) {
				this.setLiteralNumber(literal);
				return;
			}

			int posHasta1 = literal.indexOf(" ", top1);
			if (posHasta1 < 0)
				posHasta1 = top1;
			int posDesde1 = literal.lastIndexOf(" ", top1);
			if (posDesde1 < 0)
				posDesde1 = top1;

			int posSep1 = ((posHasta1 - top1) < ((posDesde1 - top1) * -1)) ? posHasta1
					: posDesde1;

			this.setLiteralNumber(literal.substring(0, posSep1));
			this.setLiteralNumber2(literal.substring(posSep1 + 1));

		} catch (Exception e) {
			throw new AdempiereException();
		}
	}

	// OpenUp. Leonardo Boccone. 29/05/2014. Issue #2208
	// Traer todas las invoice dentro de un rango de fechas para generar la CFE
	public static List<MInvoice> getInvoceforDates(Properties ctx,
			Timestamp fechaInicio, Timestamp fechaFin, String get_TrxName) {

		final String whereClause = MInvoice.COLUMNNAME_IsSOTrx + "='Y' AND "
				+ MInvoice.COLUMNNAME_DateInvoiced + " between '" + fechaInicio
				+ "' AND '" + fechaFin + "' AND "
				+ MInvoice.COLUMNNAME_DocStatus + "='CO' ";
		;

		List<MInvoice> invoice = new Query(ctx, I_C_Invoice.Table_Name,
				whereClause, get_TrxName).list();

		return invoice;
	}

	// Fin OpenUp Issue#2208

	/***
	 * Obtiene lineas de detalle de neumaticos para esta documento cuando se
	 * trata de modulo de transporte. OpenUp Ltda. Issue #1405
	 * 
	 * @author Gabriel Vila - 07/07/2014
	 * @see
	 * @return
	 */
	public List<MTRInvoiceTire> getInvoiceTires() {

		String whereClause = X_UY_TR_InvoiceTire.COLUMNNAME_C_InvoiceLine_ID
				+ " IN "
				+ " (select c_invoiceline_id from c_invoiceline where c_invoice_id ="
				+ this.get_ID() + ")";

		List<MTRInvoiceTire> lines = new Query(getCtx(),
				I_UY_TR_InvoiceTire.Table_Name, whereClause, get_TrxName())
				.list();

		return lines;

	}

	/***
	 * Genera documento de entrega para esta invoice segun parametros. OpenUp
	 * Ltda. Issue #1405
	 * 
	 * @author Gabriel Vila - 08/07/2014
	 * @see
	 */
	private void generateMInOut(MDocType doc) {

		try {

			int warehouseID = this.getM_Warehouse_ID();

			if (warehouseID == 0)
				warehouseID = Env.getContextAsInt(getCtx(), "M_Warehouse_ID");

			MInOut hdr = new MInOut(getCtx(), 0, get_TrxName());
			hdr.set_Value("IsManual", false); //OpenUp. Nicolas Sarlabos. 12/04/2015. #3869.
			hdr.setC_DocType_ID(doc.get_ID());
			hdr.setIsSOTrx(doc.isSOTrx());
			hdr.setDocStatus(DocumentEngine.STATUS_Drafted);
			hdr.setDocAction(DocumentEngine.ACTION_Complete);
			hdr.setPosted(true);
			hdr.setProcessing(false);
			hdr.setProcessed(false);
			hdr.setMovementDate(this.getDateInvoiced());
			hdr.setDateAcct(this.getDateInvoiced());
			hdr.setC_BPartner_ID(this.getC_BPartner_ID());
			hdr.setC_BPartner_Location_ID(this.getC_BPartner_Location_ID());
			hdr.setM_Warehouse_ID(warehouseID);
			hdr.setDeliveryRule("F");
			hdr.setFreightCostRule("I");
			hdr.setDeliveryViaRule("D");
			hdr.setPriorityRule("5");

			hdr.setMovementType("V+");
			if (doc.getDocBaseType() != null) {
				if (doc.getDocBaseType().equalsIgnoreCase("MMR")) {
					if (doc.isSOTrx()) {
						hdr.setMovementType("C+");
					} else {
						hdr.setMovementType("V+");
					}

				} else if (doc.getDocBaseType().equalsIgnoreCase("MMS")) {
					if (doc.isSOTrx()) {
						hdr.setMovementType("C-");
					} else {
						hdr.setMovementType("V-");
					}

				}
			}

			if (this.getAD_User_ID() > 0) {

				hdr.setAD_User_ID(this.getAD_User_ID());

			} else
				hdr.setAD_User_ID(Env.getContextAsInt(getCtx(), "AD_User_ID"));

			hdr.setSalesRep_ID(this.getSalesRep_ID());
			hdr.setC_Invoice_ID(this.get_ID());
			hdr.saveEx();

			// Lineas
			MInvoiceLine[] lines = getLines(false);
			int cont = 0;
			for (int i = 0; i < lines.length; i++) {

				MInvoiceLine invline = lines[i];

				//OpenUp. Nicolas Sarlabos. 10/04/2015. #3869. Se evita repetir recepcion de mercaderia.
				if(!this.isReceived(invline)){ //si no se hizo recepcion para este producto

					if (invline.getM_Product_ID() > 0) {

						MProduct prod = (MProduct) invline.getM_Product();

						// Solo muevo stock de productos marcados como almacenables
						// y que no estan discontinuados.
						if (prod.isStocked() && !prod.isDiscontinued()) {
							cont += 10;

							MInOutLine ioLine = new MInOutLine(getCtx(), 0,
									get_TrxName());
							ioLine.setLine(cont);
							ioLine.setM_InOut_ID(hdr.get_ID());
							ioLine.setM_Product_ID(invline.getM_Product_ID());

							MWarehouse w = new MWarehouse(getCtx(), warehouseID,
									null);
							MLocator loc = w.getDefaultLocator();
							if (loc.get_ID() > 0)
								ioLine.setM_Locator_ID(loc.get_ID());
							ioLine.setM_Warehouse_ID(this.getM_Warehouse_ID());
							ioLine.setC_UOM_ID(invline.getC_UOM_ID());
							ioLine.setIsInvoiced(true);

							MStockStatus sts = MStockStatus.forValue(getCtx(),
									"aprobado", null);
							if (sts != null) {
								ioLine.setUY_StockStatus_ID(sts.get_ID());
							}

							ioLine.setQtyEntered(invline.getQtyEntered());
							ioLine.setMovementQty(invline.getQtyInvoiced());

							if (invline.getC_OrderLine_ID() > 0) {
								ioLine.setC_OrderLine_ID(invline
										.getC_OrderLine_ID());
							}

							ioLine.saveEx();

							invline.setM_InOutLine_ID(ioLine.get_ID());
							invline.saveEx();

							/*
							DB.executeUpdateEx(
									" update c_invoiceline set m_inoutline_id ="
											+ ioLine.get_ID()
											+ " where c_invoiceline_id ="
											+ invline.get_ID(), get_TrxName());
							*/				
						}
					}
				}//Fin OpenUp.
			}

			// si no se creo ninguna linea entonces borro el cabezal
			if (cont <= 0) {
				hdr.deleteEx(true);
			} else {
				if (!hdr.processIt(DocumentEngine.ACTION_Complete)) {
					throw new AdempiereException(hdr.getProcessMsg());
				}
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/***
	 * Determina si la la linea de factura tiene una recepcion asociada. 
	 * OpenUp Ltda. Issue #3869
	 * @author Nicolas Sarlabos - 10/04/2015
	 * @see
	 * @return
	 */
	private boolean isReceived(MInvoiceLine invline) {
		
		if(invline.getM_InOutLine_ID()==0){
			
			return false;
			
		} else {
			
			MInOutLine mLine = (MInOutLine)invline.getM_InOutLine();
			MInOut hdr = (MInOut)mLine.getM_InOut();
			
			if(hdr.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Voided)) return false;		
			
		}
		
		return true;	

	}

	/***
	 * Devuelve un MInvoice por su documentno OpenUp Ltda. Issue #2208
	 * 
	 * @author Leonardo Boccone - 28/07/2014
	 * @see
	 */
	public static MInvoice getforNDocument(Properties ctx, String documentno,
			String get_TrxName) {

		final String whereClause = MInvoice.COLUMNNAME_DocumentNo + "= "
				+ documentno;

		MInvoice invoice = new Query(ctx, I_C_Invoice.Table_Name, whereClause,
				get_TrxName)
		.setClient_ID()
		.first();

		return invoice;
	}

	/**-  Devuelve un MInvoice por su documentno --> OJO  NO MULTIEMPRESA!!!! 
	 * OpenUp Ltda Issue# 4847
	 * @author SBouissa 7/10/2015
	 * @param ctx
	 * @param documentno
	 * @param get_TrxName
	 * @return
	 */
	public static MInvoice getforNDocumentSystem(Properties ctx, String documentno,
			String get_TrxName) {

		final String whereClause = MInvoice.COLUMNNAME_DocumentNo + "= '"
				+ documentno +"'";

		MInvoice invoice = new Query(ctx, I_C_Invoice.Table_Name, whereClause,
				get_TrxName)
		.first();

		return invoice;
	}
	
	/***
	 * Obtiene y retorna lineas de factura flete para el documento actual.
	 * OpenUp Ltda. Issue #3205
	 * 
	 * @author Matias Carbajal - 04/11/2014
	 * @see
	 * @return
	 */
	public List<MInvoiceFlete> getLinesInvFlete() {

		String whereClause = X_UY_Invoice_Flete.COLUMNNAME_C_Invoice_ID + "="
				+ this.get_ID();

		List<MInvoiceFlete> lines = new Query(getCtx(),
				I_UY_Invoice_Flete.Table_Name, whereClause, get_TrxName())
				.list();

		return lines;
	}
	

	/***
	 * Obtiene y retorna anticipos de este vale flete.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 13/01/2015
	 * @see
	 * @return
	 */
	public List<MTRPaymentFlete> getLinesAnticipoFlete() {

		String whereClause = X_UY_TR_PaymentFlete.COLUMNNAME_C_Invoice_ID + "=" + this.get_ID();

		List<MTRPaymentFlete> lines = new Query(getCtx(), I_UY_TR_PaymentFlete.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	/***
	 * Obtiene y retorna anticipos de este vale flete.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 13/01/2015
	 * @see
	 * @return
	 */
	public List<MTRPaymentFlete> getLinesAnticipoFleteNotProcessed() {

		String whereClause = X_UY_TR_PaymentFlete.COLUMNNAME_C_Invoice_ID + "=" + this.get_ID() +
				" AND " + X_UY_TR_PaymentFlete.COLUMNNAME_IsExecuted + "='N'" +
				" AND " + X_UY_TR_PaymentFlete.COLUMNNAME_IsSelected + "='Y'";
		

		List<MTRPaymentFlete> lines = new Query(getCtx(), I_UY_TR_PaymentFlete.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	
	/***
	 * Obtiene y retorna lineas de ordenes de vale flete asociadas a una invoice.
	 * OpenUp Ltda. Issue #3205 
	 * @author Gabriel Vila - 09/12/2014
	 * @see
	 * @return
	 */
	public List<MOrderFlete> getLinesInvOrdFlete() {

		String whereClause = X_UY_Order_Flete.COLUMNNAME_C_Invoice_ID + "="
				+ this.get_ID();

		List<MOrderFlete> lines = new Query(getCtx(),
				I_UY_Order_Flete.Table_Name, whereClause, get_TrxName())
				.list();

		return lines;
	}


	/***
	 * Afectacion automatica de anticipos contra este vale flete.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 13/01/2015
	 * @see
	 * @return
	 */
	public String allocateValeFlete() {

		String message = null;
		
		try {

			// Obtengo lineas de anticipos seleccionados y no procesados
			List<MTRPaymentFlete> anticipos = this.getLinesAnticipoFleteNotProcessed();

			if (anticipos.size() <= 0) return null;
			
			// Creo cabezal de afectacion
			MAllocation allocation = new MAllocation(getCtx(), 0, get_TrxName());
			allocation.setDocTypeFromPayment(false);
			allocation.setC_BPartner_ID(this.getC_BPartner_ID());
			allocation.setDateTrx(this.getDateInvoiced());
			allocation.setDateAcct(this.getDateInvoiced());
			allocation.setIsSOTrx(false);
			allocation.setIsManual(false);
			allocation.setC_Currency_ID(this.getC_Currency_ID());
			allocation.setDivideRate(Env.ONE);
			//allocation.setC_Payment_ID(this.getC_Payment_ID());
			allocation.saveEx();

			
			BigDecimal amtAnticipos = Env.ZERO;
			
			// Creo lineas de recibos para cada anticipo
			for (MTRPaymentFlete pflete: anticipos){

				amtAnticipos = amtAnticipos.add(pflete.getamtopen());
				
				MPayment pay = (MPayment)pflete.getC_Payment();
				
				MAllocationPayment paymentHdr = new MAllocationPayment(getCtx(), 0, get_TrxName());
				paymentHdr.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
				paymentHdr.setC_DocType_ID(pay.getC_DocType_ID());
				paymentHdr.setDocumentNo(pay.getDocumentNo());
				paymentHdr.setC_Currency_ID(pay.getC_Currency_ID());
				paymentHdr.setC_Payment_ID(pay.get_ID());
				paymentHdr.setamtdocument(pay.getPayAmt());
				paymentHdr.setamtallocated(pflete.getamtopen());
				paymentHdr.setamtopen(pay.getPayAmt());
				paymentHdr.setdatedocument(pay.getDateTrx());
				paymentHdr.saveEx();

				// Marco este anticipo como utilizado o ejecutado, 
				// de esta manera evita que vuelva a ser considerado en una impresion o reimpresion de vale flete.
				pflete.setIsExecuted(true);
				pflete.saveEx();
				
			}
			
			// Linea de Afectacion por el Vale Flete por monto total de anticipos
			MAllocationInvoice allocInv = new MAllocationInvoice(getCtx(), 0, get_TrxName());
			allocInv.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
			allocInv.setC_DocType_ID(this.getC_DocTypeTarget_ID());
			allocInv.setDocumentNo(this.getDocumentNo());
			allocInv.setC_Currency_ID(this.getC_Currency_ID());
			allocInv.setC_Invoice_ID(this.get_ID());
			allocInv.setamtdocument(this.getGrandTotal());
			allocInv.setamtallocated(amtAnticipos);
			allocInv.setamtopen(this.getGrandTotal());
			allocInv.setdatedocument(this.getDateInvoiced());
			
			allocInv.saveEx();

			
			// Actualizo totales de la afectacion
			allocation.updateTotalPayments();
			allocation.updateTotalInvoices();
			
			// Si no puedo salgo con el texto del error.
			if(!allocation.processIt(ACTION_Complete)){
				return allocation.getProcessMsg();
			}
			
			allocation.saveEx();
			
			// Posteo Inmediato de la afectacion directa
			if (!MFactAcct.alreadyPosted(allocation.get_Table_ID(), allocation.get_ID(), get_TrxName())){
				DocumentEngine.postImmediate(getCtx(),	allocation.getAD_Client_ID(), allocation.get_Table_ID(), allocation.get_ID(), true, get_TrxName());
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return message;
	}

	/***
	 * Afectacion automatica de Nota de Credito contra Factura Flete.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 26/03/2015
	 * @see
	 * @return
	 */
	public String allocateNCFlete() {

		String message = null;

		try {

			MInvoice inv = new MInvoice(getCtx(), this.getUY_Invoice_ID(), get_TrxName());

			if(this.getC_Currency_ID()==inv.getC_Currency_ID()){

				// Creo cabezal de afectacion
				MAllocation allocation = new MAllocation(getCtx(), 0, get_TrxName());
				allocation.setDocTypeFromPayment(true);
				allocation.setC_BPartner_ID(this.getC_BPartner_ID());
				allocation.setDateTrx(this.getDateInvoiced());
				allocation.setDateAcct(this.getDateInvoiced());
				allocation.setIsSOTrx(false);
				allocation.setIsManual(false);
				allocation.setC_Currency_ID(this.getC_Currency_ID());
				allocation.setDivideRate(Env.ONE);
				allocation.saveEx();

				//creo linea recibo para la nota de credito
				MAllocationPayment paymentHdr = new MAllocationPayment(getCtx(), 0, get_TrxName());
				paymentHdr.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
				paymentHdr.setC_DocType_ID(this.getC_DocTypeTarget_ID());
				paymentHdr.setDocumentNo(this.getDocumentNo());
				paymentHdr.setC_Currency_ID(this.getC_Currency_ID());
				paymentHdr.setC_Invoice_ID(this.get_ID());
				paymentHdr.setamtdocument(this.getGrandTotal());
				paymentHdr.setamtopen(this.getGrandTotal());
				paymentHdr.setdatedocument(this.getDateInvoiced());
				
				if(this.getGrandTotal().compareTo(inv.getGrandTotal())==0){ //si los importes son iguales
					
					paymentHdr.setamtallocated(this.getGrandTotal()); //afecto los totales de ambos					
					
				} else if (this.getGrandTotal().compareTo(inv.getGrandTotal()) < 0){ //si importe de NC < al importe de factura flete
					
					paymentHdr.setamtallocated(this.getGrandTotal()); //afecto el total de la NC
					
				} else if (this.getGrandTotal().compareTo(inv.getGrandTotal()) > 0){ //si importe de NC > al importe de factura flete
					
					paymentHdr.setamtallocated(inv.getGrandTotal()); //afecto el mismo importe que la factura flete
					
				}			
				
				paymentHdr.saveEx();

				// creo linea de afectacion para la factura de flete seleccionada
				MAllocationInvoice allocInv = new MAllocationInvoice(getCtx(), 0, get_TrxName());
				allocInv.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
				allocInv.setC_DocType_ID(inv.getC_DocTypeTarget_ID());
				allocInv.setDocumentNo(inv.getDocumentNo());
				allocInv.setC_Currency_ID(inv.getC_Currency_ID());
				allocInv.setC_Invoice_ID(inv.get_ID());
				allocInv.setamtdocument(inv.getGrandTotal());
				allocInv.setamtopen(inv.getGrandTotal());
				allocInv.setdatedocument(inv.getDateInvoiced());
				
				if(this.getGrandTotal().compareTo(inv.getGrandTotal())==0){
					
					allocInv.setamtallocated(inv.getGrandTotal());
					
				} else if (this.getGrandTotal().compareTo(inv.getGrandTotal()) < 0){
					
					allocInv.setamtallocated(this.getGrandTotal());
					
				} else if (this.getGrandTotal().compareTo(inv.getGrandTotal()) > 0){
					
					allocInv.setamtallocated(inv.getGrandTotal());
					
				}
							
				allocInv.saveEx();


				// Actualizo totales de la afectacion
				allocation.updateTotalPayments();
				allocation.updateTotalInvoices();

				// Si no puedo salgo con el texto del error.
				if(!allocation.processIt(ACTION_Complete)){
					return allocation.getProcessMsg();
				}

				allocation.saveEx();

				// Posteo Inmediato de la afectacion directa
				if (!MFactAcct.alreadyPosted(allocation.get_Table_ID(), allocation.get_ID(), get_TrxName())){
					DocumentEngine.postImmediate(getCtx(),	allocation.getAD_Client_ID(), allocation.get_Table_ID(), allocation.get_ID(), true, get_TrxName());
				}

			} else ADialog.info(0,null,"No se realizo afectacion automatica, se debe hacer afectacion manual para documentos en diferentes monedas");

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return message;
	}
	
	/*public void refreshFromProforma() {

		String sql = "";
		MTRTrip trip = null;

		try{

			if(this.getFreightAmtLines().size()>0) this.verifyAmount();//verifico que la suma de importes en lineas de distribucion sean iguales al total de la proforma  
			
			MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
			
			if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");

			MTRCrt crt = MTRCrt.forInvoice(getCtx(), this.get_ID(), get_TrxName()); //obtengo el CRT
	
			if(crt!=null && crt.get_ID()>0){
				
				trip = new MTRTrip(getCtx(), crt.getUY_TR_Trip_ID(), get_TrxName());
				
				crt.setamt1(Env.ZERO);
				crt.setamt2(Env.ZERO);
				
				//se cargan los importes
				BigDecimal nationalAmt = Env.ZERO;
				BigDecimal interAmt = Env.ZERO;
				BigDecimal subTotalAmt = this.getTotalLines(); //obtengo el subtotal de la factura

				crt.setC_Currency2_ID(this.getC_Currency_ID()); //seteo moneda de flete

				if(trip.getReceiptMode().equalsIgnoreCase("AMBOS")){

					sql = "select coalesce(sum(nationalamt),0)" +
							" from c_invoiceline" +
							" where c_invoice_id = " + this.get_ID();
					nationalAmt = DB.getSQLValueBDEx(get_TrxName(), sql);

					sql = "select coalesce(sum(internationalamt),0)" +
							" from c_invoiceline" +
							" where c_invoice_id = " + this.get_ID();
					interAmt = DB.getSQLValueBDEx(get_TrxName(), sql);

					//EL TERRITORIO EXTRANJERO SIEMPRE ES DE ORIGEN A FRONTERA
					crt.setamt1(interAmt); //seteo campo de importe de flete del remitente, con el importe del terr. internacional
					crt.setamt2(nationalAmt); //seteo campo de importe de flete del destinatario, con el importe del terr. nacional			

				} else if (trip.getReceiptMode().equalsIgnoreCase("ORIGEN")){

					crt.setamt1(subTotalAmt); //seteo campo de importe de flete del remitente, con el importe subtotal de la factura			

				} else if (trip.getReceiptMode().equalsIgnoreCase("DESTINO")){

					crt.setamt2(subTotalAmt); //seteo campo de importe de flete del destinatario, con el importe subtotal de la factura	
				}		

				//se carga monto de flete externo
				//obtengo suma de importes para terr. internacional
				sql = "select coalesce(sum(internationalamt),0)" +
						" from c_invoiceline" +
						" where c_invoice_id = " + this.get_ID();
				BigDecimal amt = DB.getSQLValueBD(get_TrxName(), sql);

				if(amt.compareTo(Env.ZERO)>0){

					// Importe a texto con formato . para miles y , para decimales
					BigDecimal impaux = amt.setScale(param.getQtyDecimal(), RoundingMode.HALF_UP);
					DecimalFormat df = new DecimalFormat();
					df.setMaximumFractionDigits(param.getQtyDecimal());
					df.setMinimumFractionDigits(param.getQtyDecimal());
					df.setGroupingUsed(true);
					String result = df.format(impaux);

					if (result != null){
						result = result.replace(".", ";");
						result = result.replace(",", ".");
						result = result.replace(";", ",");
					}

					MCurrency cur = new MCurrency(getCtx(),this.getC_Currency_ID(),get_TrxName()); //instancio moneda del cabezal

					String value = cur.getCurSymbol() + " " + result + "   VALOR INCLUIDO EN FLETE TOTAL";

					crt.setValorFleteExt(value);

				} else crt.setValorFleteExt("VALOR INCLUIDO EN FLETE TOTAL");
				
				crt.saveEx();
				
				// Actualizo OT y movimientos
				if(this.getFreightAmtLines().size()>0) this.refreshMovements();
				

			} 
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}		

	}*/
	
	/*private void verifyAmount() {

		String sql = "";
		BigDecimal amt = Env.ZERO;

		sql = "select coalesce(sum(amount),0)" +
				" from uy_tr_invoicefreightamt" + 
				" where c_invoice_id = " + this.get_ID();

		amt = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		if(this.getTotalLines().compareTo(amt)!=0) throw new AdempiereException("Importe subtotal de proforma debe ser igual a la suma de importes distribuidos");

	}*/
	
	/***
	 * Obtiene y retorna todas las lineas de distribucion de importes de flete para esta proforma.
	 * OpenUp Ltda. Issue # 
	 * @author Nicolas Sarlabos - 15/04/2015
	 * @see
	 * @return
	 */
	public List<MTRInvoiceFreightAmt> getFreightAmtLines(){

		String whereClause = X_UY_TR_InvoiceFreightAmt.COLUMNNAME_C_Invoice_ID + "=" + this.get_ID();

		List<MTRInvoiceFreightAmt> lines = new Query(getCtx(), I_UY_TR_InvoiceFreightAmt.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	/**
	 * Afectación automática 
	 * @author OpenUp SBT Issue#5471  24/2/2016 10:40:14
	 * @return
	 */
	public String allocateNCInvoice(){
		String message = null;

		try {

			MInvoice inv = new MInvoice(getCtx(), this.getUY_Invoice_ID(), get_TrxName());

			if(this.getC_Currency_ID()==inv.getC_Currency_ID()){

				// Creo cabezal de afectacion
				MAllocation allocation = new MAllocation(getCtx(), 0, get_TrxName());
				allocation.setDocTypeFromPayment(true);
				allocation.setC_BPartner_ID(this.getC_BPartner_ID());
				allocation.setDateTrx(this.getDateInvoiced());
				allocation.setDateAcct(this.getDateInvoiced());
				allocation.setIsSOTrx(false);
				allocation.setIsManual(false);
				allocation.setC_Currency_ID(this.getC_Currency_ID());
				allocation.setDivideRate(Env.ONE);
				allocation.saveEx();

				//creo linea recibo para la nota de credito
				MAllocationPayment paymentHdr = new MAllocationPayment(getCtx(), 0, get_TrxName());
				paymentHdr.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
				paymentHdr.setC_DocType_ID(this.getC_DocTypeTarget_ID());
				paymentHdr.setDocumentNo(this.getDocumentNo());
				paymentHdr.setC_Currency_ID(this.getC_Currency_ID());
				paymentHdr.setC_Invoice_ID(this.get_ID());
				paymentHdr.setamtdocument(this.getGrandTotal());
				paymentHdr.setamtopen(this.getGrandTotal());
				paymentHdr.setdatedocument(this.getDateInvoiced());
				
				if(this.getGrandTotal().compareTo(inv.getGrandTotal())==0){ //si los importes son iguales
					
					paymentHdr.setamtallocated(this.getGrandTotal()); //afecto los totales de ambos					
					
				} else if (this.getGrandTotal().compareTo(inv.getGrandTotal()) < 0){ //si importe de NC < al importe de factura flete
					
					paymentHdr.setamtallocated(this.getGrandTotal()); //afecto el total de la NC
					
				} else if (this.getGrandTotal().compareTo(inv.getGrandTotal()) > 0){ //si importe de NC > al importe de factura flete
					
					paymentHdr.setamtallocated(inv.getGrandTotal()); //afecto el mismo importe que la factura flete
					
				}			
				
				paymentHdr.saveEx();

				// creo linea de afectacion para la factura de flete seleccionada
				MAllocationInvoice allocInv = new MAllocationInvoice(getCtx(), 0, get_TrxName());
				allocInv.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
				allocInv.setC_DocType_ID(inv.getC_DocTypeTarget_ID());
				allocInv.setDocumentNo(inv.getDocumentNo());
				allocInv.setC_Currency_ID(inv.getC_Currency_ID());
				allocInv.setC_Invoice_ID(inv.get_ID());
				allocInv.setamtdocument(inv.getGrandTotal());
				allocInv.setamtopen(inv.getGrandTotal());//CONSULTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				allocInv.setdatedocument(inv.getDateInvoiced());
				
				if(this.getGrandTotal().compareTo(inv.getGrandTotal())==0){
					
					allocInv.setamtallocated(inv.getGrandTotal());
					
				} else if (this.getGrandTotal().compareTo(inv.getGrandTotal()) < 0){
					
					allocInv.setamtallocated(this.getGrandTotal());
					
				} else if (this.getGrandTotal().compareTo(inv.getGrandTotal()) > 0){
					
					allocInv.setamtallocated(inv.getGrandTotal());
					
				}
							
				allocInv.saveEx();


				// Actualizo totales de la afectacion
				allocation.updateTotalPayments();
				allocation.updateTotalInvoices();

				// Si no puedo salgo con el texto del error.
				if(!allocation.processIt(ACTION_Complete)){
					return (allocation.getProcessMsg());
				}

				allocation.saveEx();

				// Posteo Inmediato de la afectacion directa
				if (!MFactAcct.alreadyPosted(allocation.get_Table_ID(), allocation.get_ID(), get_TrxName())){
					DocumentEngine.postImmediate(getCtx(),	allocation.getAD_Client_ID(), allocation.get_Table_ID(), allocation.get_ID(), true, get_TrxName());
				}

			} else ADialog.info(0,null,"No se realizo afectacion automatica, se debe hacer afectacion manual para documentos en diferentes monedas");

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return message;
	}
	
	
	/**
	 * En caso que este documento tenga afectacion directa, debo generar esta 
	 * afectacion en modelo final y completarla.
	 * @author OpenUp SBT Issue #5516  26/2/2016 17:27:49
	 * @return
	 * @throws Exception
	 */
	private boolean allocateItOpenUp() throws Exception {
		
		// Si no tengo lineas de afectacion salgo sin hacer nada
		MAllocDirectCreditNote[] allocDirLines = this.getAllocDirectLines(get_TrxName());
		if (allocDirLines.length <= 0) return true;
		
		// Creo cabezal de afectacion
		MAllocation allocation = new MAllocation(getCtx(), 0, get_TrxName());
		allocation.setDocTypeFromPayment(this.isSOTrx());//allocation.setDocTypeFromPayment(this.isReceipt()); //CONSULTARRRRRR SI ESTA OK
		allocation.setC_BPartner_ID(this.getC_BPartner_ID());	
		allocation.setDateTrx(this.getDateInvoiced());
		allocation.setDateAcct(this.getDateAcct());
		allocation.setIsSOTrx(this.isSOTrx());
		allocation.setIsManual(false);
		allocation.setC_Currency_ID(this.getC_Currency_ID());
		//SBT 17-02-2016 Issue #5413
		if(this.get_Value("C_Currency2_ID")!=null){
			allocation.setC_Currency2_ID(this.get_ValueAsInt("C_Currency2_ID"));
			allocation.setDivideRate(OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateInvoiced(), allocation.getC_Currency_ID(), allocation.getC_Currency2_ID(), this.getAD_Client_ID(),this.getAD_Org_ID()));
		}else{
			allocation.setDivideRate(Env.ONE);
		}		
		allocation.saveEx();

		
		//Creo linea de afectación de recibo para la nota de credito
		MAllocationPayment paymentHdr = new MAllocationPayment(getCtx(), 0, get_TrxName());
		paymentHdr.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
		paymentHdr.setC_DocType_ID(this.getC_DocTypeTarget_ID());
		paymentHdr.setDocumentNo(this.getDocumentNo());
		paymentHdr.setC_Currency_ID(this.getC_Currency_ID());
		paymentHdr.setC_Invoice_ID(this.get_ID());
		paymentHdr.setamtdocument(this.getGrandTotal());
		paymentHdr.setamtopen(this.getGrandTotal());
		paymentHdr.setdatedocument(this.getDateInvoiced());
		//COnsultar
		
		paymentHdr.setamtallocated(this.getGrandTotal().subtract((BigDecimal)this.get_Value("AmtToAllocate")));
		paymentHdr.saveEx();
		
		// Creo lineas de recibo y lineas de factura
		// Las notas de credito se agregan como recibos 
		for (int i = 0; i < allocDirLines.length; i++){
			
			if (allocDirLines[i].getsign().compareTo(Env.ONE) < 0){
				// Es una factura (resta del recibo)
				MAllocationInvoice invoice = new MAllocationInvoice(getCtx(), 0, get_TrxName());
				invoice.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
				invoice.setC_DocType_ID(allocDirLines[i].getC_DocType_ID());
				invoice.setDocumentNo(allocDirLines[i].getDocumentNo());
				invoice.setC_Currency_ID(allocDirLines[i].getC_Currency_ID());
				invoice.setC_Invoice_ID(allocDirLines[i].getC_Invoice2_ID()); //-->Tengo que asociar el docuemnto que corresponde a la línea no el cabezal de la línea. 
				invoice.setamtdocument(allocDirLines[i].getamtdocument());
				invoice.setamtallocated(allocDirLines[i].getamtallocated());
				invoice.setamtopen(allocDirLines[i].getamtopen());//Se setea el monto pendiente 
				invoice.setdatedocument(allocDirLines[i].getdatedocument());
				
				invoice.saveEx();
				allocDirLines[i].setProcessed(true);
				allocDirLines[i].saveEx();
			}
		}

		// Actualizo totales de la afectacion
		allocation.updateTotalPayments();
		allocation.updateTotalInvoices();
		
		// Si no puedo salgo con el texto del error.
		if(!allocation.processIt(ACTION_Complete)){
			m_processMsg = allocation.getProcessMsg();
			return false;
		}
		
		allocation.saveEx();
		
		// Posteo Inmediato de la afectacion directa
		if (!MFactAcct.alreadyPosted(allocation.get_Table_ID(), allocation.get_ID(), get_TrxName())){
			DocumentEngine.postImmediate(getCtx(),	allocation.getAD_Client_ID(), allocation.get_Table_ID(), allocation.get_ID(), true, get_TrxName());
		}
		//this.setAmtToAllocate
		// Me guardo el numero de afectacion asociado a la afectacion directa
		//this.setUY_Allocation_ID(allocation.getUY_Allocation_ID());
		
		return true;
	}
	
	
	// INICIO - INTEFACE CON MÓDULO DE FACTURACIÓN ELECTRÓNICA
	
	@Override
	public CFEDefType getCFEDTO() {
		CfeConverter cfeConverter = new CfeConverter(getCtx(), this, get_TrxName());
		cfeConverter.loadCFE();
		return cfeConverter.getObjCfe();
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
		
		MDocType docType = (MDocType) this.getC_DocTypeTarget();
		boolean isCfe = docType.get_ValueAsBoolean("isCFE");
		
		return isCfe;
	}
	
	@Override
	public void setDataEnvelope(MCFEDataEnvelope mCfeDataEnvelope) {
		
		set_Value("UY_CFE_DataEnvelope_ID", mCfeDataEnvelope.get_ID());
		this.saveEx();
		
	}
	
	/* Actualizo campos de total modificados por la afectacion. 
	 */
	 
	 
	 /**
	  * Actualizo campos de total modificados por la afectacion. 
	  * @author OpenUp SBT Issue#  26/2/2016 13:48:40
	  * @param save
	  */
	public void updateAllocDirect(boolean save){
		
		BigDecimal totalFacturas = Env.ZERO, totalNotasCredito = Env.ZERO;
		//BigDecimal totalRetenciones = Env.ZERO;
		
		// Obtengo y recorro lineas de afectacion directa de este recibo
		MAllocDirectCreditNote[] lines = this.getAllocDirectLines(get_TrxName());
		for (int i=0; i < lines.length; i++){
			
			if (lines[i].getsign().compareTo(Env.ONE) == 0)
				//Si la moneda de la línea es igual a la moneda del recibo, sumo (#5413 SBT 11/02/16)
				if(lines[i].getC_Currency_ID() == this.getC_Currency_ID()){
					totalNotasCredito = totalNotasCredito.add(lines[i].getamtallocated());
				//Sino la moneda tiene que ser igual a la "segunda" moneda y es necesario calcular con el tipo de cambio del recibo	
				}else{
					if(this.get_Value("C_Currency2_ID")==null) throw new AdempiereException("Debe indicar segunda moneda de afectación");
					if(this.getCurrencyRate().compareTo(Env.ZERO)<=0) throw new AdempiereException("Debe indicar Tasa de cambio");
					totalNotasCredito = totalNotasCredito.add(lines[i].getamtallocated().multiply(this.getCurrencyRate()));
				}	
			else
				if(lines[i].getC_Currency_ID() == this.getC_Currency_ID()){
					totalFacturas = totalFacturas.add(lines[i].getamtallocated());
				}else{
					if(this.get_Value("C_Currency2_ID")==null) throw new AdempiereException("Debe indicar segunda moneda de afectación");
					if(this.getCurrencyRate().compareTo(Env.ZERO)<=0) throw new AdempiereException("Debe indicar Tasa de cambio");
					totalFacturas = totalFacturas.add(lines[i].getamtallocated().multiply(this.getCurrencyRate()));
				}	
				
//			if(lines[i].get_Value("amtRetention") != null){
//				if(lines[i].getC_Currency_ID() == this.getC_Currency_ID()){
//					totalRetenciones = totalRetenciones.add((BigDecimal) lines[i].get_Value("amtRetention"));
//				}else{
//					if(this.get_Value("C_Currency2_ID")==null) throw new AdempiereException("Debe indicar segunda moneda de afectación");
//					if(this.getCurrencyRate().compareTo(Env.ZERO)<=0) throw new AdempiereException("Debe indicar Tasa de cambio");
//					totalRetenciones = totalRetenciones.add(((BigDecimal) lines[i].get_Value("amtRetention")).multiply(this.getCurrencyRate()));
//				}
//			}
		}
		BigDecimal totalLineas = Env.ZERO;
		MInvoiceLine[] linesNC = this.getLines();
		for(int a = 0;a<linesNC.length;a++){
			MInvoiceLine line = linesNC[a];
			totalLineas = totalLineas.add(line.getLineTotalAmt());
		}
		BigDecimal mtToAllocate = (totalLineas.add(totalNotasCredito)).subtract(totalFacturas);
		//this.setAmtToAllocate((this.getPayAmt().add(totalNotasCredito)).subtract(totalFacturas));
		this.set_Value("AmtToAllocate", (mtToAllocate.setScale(2, RoundingMode.HALF_UP)));//Se agrega línea para redondear
		MDocType doc = (MDocType) this.getC_DocType();
		//Ini OpenUp SBT 16-12-2015 Issue #5413
		//Si el tipo de documento es multimoneda, se calcula el saldo de afect. directa en la moneda 2
		if((doc.get_ID()>0) && (doc.getValue().equalsIgnoreCase("receiptmulticur")||doc.getValue().equalsIgnoreCase("paymentmulticur"))){
			//BigDecimal amtToCur2 = mtToAllocate.negate();
			BigDecimal amtToCur2= mtToAllocate.multiply(OpenUpUtils.getCurrencyRateForCur1Cur2(this.getDateInvoiced(),
					this.get_ValueAsInt("C_Currency2_ID"),this.getC_Currency_ID(),this.getAD_Client_ID(), this.getAD_Org_ID()));
			this.set_Value("AmtToAllocateCur2", amtToCur2.setScale(2,  RoundingMode.HALF_UP));
		}//Fin OpenUp SBT 16-12-2015 Issue #5413
			
	//	this.set_ValueOfColumn("amtRetention", totalRetenciones);
		
		if (save) this.saveEx(get_TrxName());
	}
	

	/**
	 * Obtengo y retorno array de lineas de afectacion directa de este docuemnto.
	 * @author OpenUp SBT Issue #5516  26/2/2016 13:50:39
	 * @param trxName
	 * @return
	 */
	public MAllocDirectCreditNote[] getAllocDirectLines(String trxName) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MAllocDirectCreditNote> list = new ArrayList<MAllocDirectCreditNote>();
		
		try{
			sql =" SELECT " + X_UY_AllocDirectCreditNote.COLUMNNAME_UY_AllocDirectCreditNote_ID + 
 		  	     " FROM " + X_UY_AllocDirectCreditNote.Table_Name + 
		  	     " WHERE " + X_UY_AllocDirectCreditNote.COLUMNNAME_C_Invoice_ID + "=?";
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, this.get_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MAllocDirectCreditNote value = new MAllocDirectCreditNote(Env.getCtx(), rs.getInt(1), trxName);
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
		
		return list.toArray(new MAllocDirectCreditNote[list.size()]);		
	}
	
	
	/**
	 * 
	 * @author OpenUp SBT Issue #5516  26/2/2016 14:25:44
	 * @param save
	 */
	public void updateTotalInvoices(Boolean save){
		
		BigDecimal total = Env.ZERO;
		
		// Obtengo y recorro lineas de facturas de esta afectacion
		MAllocDirectCreditNote[] invoices = this.getAllocDirectLines(get_TrxName());
		for (int i=0; i < invoices.length; i++){
			
			MAllocDirectCreditNote invoice = invoices[i];
			
			// Si la moneda de la linea es igual a la moneda de afectacion 1
			if (invoice.getC_Currency_ID() == this.getC_Currency_ID())
				total = total.add(invoice.getamtallocated());	// Sumo afectacion al total, sin necesidad de conversion
			else{
				// Necesito convertir el monto afectado de la linea a la moneda de afectacion 1.
				total = total.add(invoice.getamtallocated().multiply(this.getCurrencyRate()));
			}
		}
		
		this.set_Value("amtinvallocated", total.setScale(2, RoundingMode.HALF_UP));
		if(save){
			this.saveEx();
		}
	}
	// FIN - INTEFACE CON MÓDULO DE FACTURACIÓN ELECTRÓNICA
	
	/***
	 * Verifica que no exista un CRT completo para esta Proforma.
	 * OpenUp #5526
	 * @author Nicolas Sarlabos - 09/03/2016
	 * @see
	 * @return
	 */
	private void validateVoidProforma() {
			
		String sql = "select uy_tr_crt_id" +
				" from uy_tr_crt" +
				" where c_invoice_id = " + this.get_ID() +
				" and docstatus <> 'VO'";
		
		int crtID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(crtID > 0){
			
			MTRCrt crt = new MTRCrt(getCtx(), crtID, get_TrxName());
			
			throw new AdempiereException("Imposible anular. Existe el CRT Nº " + crt.getNumero() + " en estado distinto a ANULADO.");		
			
		}	
		
	}
	
	/*private void refreshMovements() {

		MTRTransOrderLine otLine = null;
		MTRLoadMonitorLine mLine = null;

		List<MTRInvoiceFreightAmt> amtLines = this.getFreightAmtLines(); //obtengo lineas de distribucion

		for(MTRInvoiceFreightAmt line : amtLines){

			//actualizo movimientos y stock
			if(line.getUY_TR_Stock_ID()>0 && line.getM_Warehouse_ID()>0){

				//actualizo movimiento
				if(line.getQtyPackage().compareTo(Env.ZERO)>0){
					
					mLine = new MTRLoadMonitorLine(getCtx(), line.getUY_TR_LoadMonitorLine_ID(), get_TrxName());
					
					if(mLine!=null && mLine.get_ID()>0){
						mLine.setAmount(line.getAmount());
						mLine.saveEx();						
					}					
				}
				//actualizo stock para este CRT
				if(line.getAmount().compareTo(Env.ZERO)>0){

					MTRStock stock = new MTRStock(getCtx(), line.getUY_TR_Stock_ID(), get_TrxName()); //instancio linea de stock
					
					if(stock!=null && stock.get_ID()>0){
						stock.setAmount(line.getAmount());
						stock.saveEx();
					}		
				} 

			} else { //actualizo ordenes y movimientos
				//actualizo OT
				otLine = new MTRTransOrderLine(getCtx(), line.getUY_TR_TransOrderLine_ID(), get_TrxName());
				if(otLine!=null && otLine.get_ID()>0){
					otLine.setAmount(line.getAmount());
					otLine.saveEx();					
				}		
				//actualizo movimiento
				mLine = new MTRLoadMonitorLine(getCtx(), otLine.getUY_TR_LoadMonitorLine_ID(), get_TrxName());
				if(mLine!=null && mLine.get_ID()>0){
					mLine.setAmount(line.getAmount());
					mLine.saveEx();					
				}
			}

			//actualizo MIC
			this.refreshMicHdr(line.getUY_TR_Crt_ID(), line);
			this.refreshMicCont(line.getUY_TR_Crt_ID(), true, line);
			this.refreshMicCont(line.getUY_TR_Crt_ID(), false, line);			

		}	

	}*/
	
	/*private void refreshMicHdr(int crtID, MTRInvoiceFreightAmt line) {

		String sql = "";
		MTRMic mic = null;
		BigDecimal amt = Env.ZERO;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = "select uy_tr_mic_id" + 
					" from uy_tr_mic" +
					" where uy_tr_crt_id = " + crtID +
					" and uy_tr_transorder_id = " + line.getUY_TR_TransOrder_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while(rs.next()){

				mic = new MTRMic(getCtx(),rs.getInt("uy_tr_mic_id"),get_TrxName());
				
				//si moneda del flete es distinto a U$S, debo hacer la conversion
				amt = this.getAmountMic(line.getAmount());				
				
				mic.setAmount(amt);
				mic.saveEx();

				MTRMic.updateTotals(mic);				

			}		

		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	

	}*/
	
	/*private void refreshMicCont(int crtID, boolean isCRT1, MTRInvoiceFreightAmt line) {

		String sql = "";
		MTRMic mic = null;
		BigDecimal amt = Env.ZERO;
		MTRMicCont cont = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			if(isCRT1){ //si es el CRT1 de la continuacion

				sql = "select c.uy_tr_miccont_id" + 
						" from uy_tr_miccont c" +
						" inner join uy_tr_mic m on c.uy_tr_mic_id = m.uy_tr_mic_id" +	
						" where c.uy_tr_crt_id = " + crtID +
						" and m.uy_tr_transorder_id = " + line.getUY_TR_TransOrder_ID();

			} else { //si es el CRT2 de la continuacion

				sql = "select c.uy_tr_miccont_id" + 
						" from uy_tr_miccont c" +
						" inner join uy_tr_mic m on c.uy_tr_mic_id = m.uy_tr_mic_id" +	
						" where c.uy_tr_crt_id_1 = " + crtID +
						" and m.uy_tr_transorder_id = " + line.getUY_TR_TransOrder_ID();		

			}

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while(rs.next()){

				cont = new MTRMicCont(getCtx(),rs.getInt("uy_tr_miccont_id"),get_TrxName());
				mic = new MTRMic(getCtx(),cont.getUY_TR_Mic_ID(),get_TrxName());
				
				//si moneda del flete es distinto a U$S, debo hacer la conversion
				amt = this.getAmountMic(line.getAmount());	

				if(isCRT1){

					cont.setAmount(amt);
					cont.saveEx();

				} else {

					cont.setAmount2(amt);	
					cont.saveEx();

				}	

				MTRMic.updateTotals(mic);				

			}		

		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	

	}*/


	
} // MInvoice
