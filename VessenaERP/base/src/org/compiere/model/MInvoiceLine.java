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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.apps.ADialog;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.model.MDocType;
import org.openup.model.I_UY_Invoice_Provision;
import org.openup.model.MInvoiceProvision;
import org.openup.model.MManufLine;
import org.openup.model.MProvision;
import org.openup.model.MProvisionLine;
import org.openup.model.MTRInvPrintFlete;
import org.openup.model.MTRInvoiceTire;
import org.openup.model.MTRTrip;
import org.openup.model.X_UY_Invoice_Provision;


/**
 *	Invoice Line Model
 *
 *  @author Jorg Janke
 *  @version $Id: MInvoiceLine.java,v 1.5 2006/07/30 00:51:03 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 2804142 ] MInvoice.setRMALine should work only for CreditMemo invoices
 * 				https://sourceforge.net/tracker/?func=detail&aid=2804142&group_id=176962&atid=879332
 * @author Michael Judd, www.akunagroup.com
 * 			<li>BF [ 1733602 ] Price List including Tax Error - when a user changes the orderline or
 * 				invoice line for a product on a price list that includes tax, the net amount is
 * 				incorrectly calculated.
 * @author red1 FR: [ 2214883 ] Remove SQL code and Replace for Query
 */
public class MInvoiceLine extends X_C_InvoiceLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5113860437274708398L;
	// OpenUp M.R. 18-02-2011 issue #355 Agrego Variables para setear impuestos exento en lineas desde el boton crear desde 
	private static final int Idexento = 1000002; 
	// Fin OpenUp
	
	// OpenUp. Nicolas Sarlabos. 18/03/2014. Issue #1633
	public boolean IsFromProforma = false;
	// Fin OpenUp. Issue #1633.

	public boolean IsFromFreightInv = false;
	/**
	 * 	Get Invoice Line referencing InOut Line
	 *	@param sLine shipment line
	 *	@return (first) invoice line
	 */
	public static MInvoiceLine getOfInOutLine (MInOutLine sLine)
	{
		if (sLine == null)
			return null;
		final String whereClause = I_M_InOutLine.COLUMNNAME_M_InOutLine_ID+"=?";
		List<MInvoiceLine> list = new Query(sLine.getCtx(),I_C_InvoiceLine.Table_Name,whereClause,sLine.get_TrxName())
		.setParameters(sLine.getM_InOutLine_ID())
		.list();
		
		MInvoiceLine retValue = null;
		if (list.size() > 0) {
			retValue = list.get(0);
			if (list.size() > 1)
				s_log.warning("More than one C_InvoiceLine of " + sLine);
		}

		return retValue;
	}	//	getOfInOutLine

	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MInvoiceLine.class);

	/** Tax							*/
	private MTax 		m_tax = null;
	
	
	/**************************************************************************
	 * 	Invoice Line Constructor
	 * 	@param ctx context
	 * 	@param C_InvoiceLine_ID invoice line or 0
	 * 	@param trxName transaction name
	 */
	public MInvoiceLine (Properties ctx, int C_InvoiceLine_ID, String trxName)
	{
		super (ctx, C_InvoiceLine_ID, trxName);
		if (C_InvoiceLine_ID == 0)
		{
			setIsDescription(false);
			setIsPrinted (true);
			setLineNetAmt (Env.ZERO);
			setPriceEntered (Env.ZERO);
			setPriceActual (Env.ZERO);
			setPriceLimit (Env.ZERO);
			setPriceList (Env.ZERO);
			setM_AttributeSetInstance_ID(0);
			setTaxAmt(Env.ZERO);
			//
			setQtyEntered(Env.ZERO);
			setQtyInvoiced(Env.ZERO);
		}
	}	//	MInvoiceLine

	/**
	 * 	Parent Constructor
	 * 	@param invoice parent
	 */
	public MInvoiceLine (MInvoice invoice)
	{
		this (invoice.getCtx(), 0, invoice.get_TrxName());
		if (invoice.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setClientOrg(invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
		setC_Invoice_ID (invoice.getC_Invoice_ID());
		setInvoice(invoice);
	}	//	MInvoiceLine


	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public MInvoiceLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInvoiceLine

	private int			m_M_PriceList_ID = 0;
	private Timestamp	m_DateInvoiced = null;
	private int			m_C_BPartner_ID = 0;
	private int			m_C_BPartner_Location_ID = 0;
	private boolean		m_IsSOTrx = true;
	private boolean		m_priceSet = false;
	private MProduct	m_product = null;
	/**	Charge					*/
	private MCharge 		m_charge = null;
	
	/**	Cached Name of the line		*/
	private String		m_name = null;
	/** Cached Precision			*/
	private Integer		m_precision = null;
	/** Product Pricing				*/
	private MProductPricing	m_productPricing = null;
	/** Parent						*/
	private MInvoice	m_parent = null;

	/**
	 * 	Set Defaults from Order.
	 * 	Called also from copy lines from invoice
	 * 	Does not set Parent !!
	 * 	@param invoice invoice
	 */
	public void setInvoice (MInvoice invoice)
	{
		m_parent = invoice;
		m_M_PriceList_ID = invoice.getM_PriceList_ID();
		m_DateInvoiced = invoice.getDateInvoiced();
		m_C_BPartner_ID = invoice.getC_BPartner_ID();
		m_C_BPartner_Location_ID = invoice.getC_BPartner_Location_ID();
		m_IsSOTrx = invoice.isSOTrx();
		m_precision = new Integer(invoice.getPrecision());
	}	//	setOrder

	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MInvoice getParent()
	{
		if (m_parent == null)
			m_parent = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		return m_parent;
	}	//	getParent

	/**
	 * 	Set values from Order Line.
	 * 	Does not set quantity!
	 *	@param oLine line
	 */
	public void setOrderLine (MOrderLine oLine)
	{
		setC_OrderLine_ID(oLine.getC_OrderLine_ID());
		//
		setLine(oLine.getLine());
		setIsDescription(oLine.isDescription());
		setDescription(oLine.getDescription());
		//
		if(oLine.getM_Product_ID() == 0)
		setC_Charge_ID(oLine.getC_Charge_ID());
		//
		setM_Product_ID(oLine.getM_Product_ID());
		setM_AttributeSetInstance_ID(oLine.getM_AttributeSetInstance_ID());
		setS_ResourceAssignment_ID(oLine.getS_ResourceAssignment_ID());
		setC_UOM_ID(oLine.getC_UOM_ID());
		//
		setPriceEntered(oLine.getPriceEntered());
		setPriceActual(oLine.getPriceActual());
		setPriceLimit(oLine.getPriceLimit());
		setPriceList(oLine.getPriceList());
		//
		setC_Tax_ID(oLine.getC_Tax_ID());
		setLineNetAmt(oLine.getLineNetAmt());
		//
		setC_Project_ID(oLine.getC_Project_ID());
		setC_ProjectPhase_ID(oLine.getC_ProjectPhase_ID());
		setC_ProjectTask_ID(oLine.getC_ProjectTask_ID());
		setC_Activity_ID(oLine.getC_Activity_ID());
		setC_Campaign_ID(oLine.getC_Campaign_ID());
		setAD_OrgTrx_ID(oLine.getAD_OrgTrx_ID());
		setUser1_ID(oLine.getUser1_ID());
		setUser2_ID(oLine.getUser2_ID());
		//
		setRRAmt(oLine.getRRAmt());
		setRRStartDate(oLine.getRRStartDate());
		
		/**
		 * 	OpenUp
		 * 	Emiliano Bentancor
		 *	Issue #5680
		 */
		if(oLine.get_Value("m_pricelist_id") != null){
			this.set_ValueOfColumn("m_pricelist_id", oLine.get_ValueAsInt("m_pricelist_id"));
			this.set_ValueOfColumn("m_pricelist_version_id", oLine.get_ValueAsInt("m_pricelist_version_id"));
			this.set_ValueOfColumn("upc", oLine.get_ValueAsString("upc"));
			this.set_ValueOfColumn("vendorproductno", oLine.get_ValueAsString("vendorproductno"));
		}
		//FIN Issue #5680
		
		
	}	//	setOrderLine

	/**
	 * 	Set values from Shipment Line.
	 * 	Does not set quantity!
	 *	@param sLine ship line
	 */
	public void setShipLine (MInOutLine sLine)
	{
		setM_InOutLine_ID(sLine.getM_InOutLine_ID());
		setC_OrderLine_ID(sLine.getC_OrderLine_ID());
		// Set RMALine ID if shipment/receipt is based on RMA Doc
        setM_RMALine_ID(sLine.getM_RMALine_ID());

		//
		setLine(sLine.getLine());
		setIsDescription(sLine.isDescription());
		setDescription(sLine.getDescription());
		//
		setM_Product_ID(sLine.getM_Product_ID());
		
		// OpenUp. Gabriel Vila. 02/05/2012. 
		// Si no tengo orden asociada guardo UOM de la linea de entrega, else hago lo que viene en adempiere
		if (getC_OrderLine_ID() <= 0){
			setC_UOM_ID(sLine.getC_UOM_ID());
		}
		else{ 
			if (sLine.sameOrderLineUOM()){
				setC_UOM_ID(sLine.getC_UOM_ID());
			}				
			else{
				// use product UOM if the shipment hasn't the same uom than the order
				setC_UOM_ID(getProduct().getC_UOM_ID());
			}
		}
		// Fin OpenUp
				
		setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
	//	setS_ResourceAssignment_ID(sLine.getS_ResourceAssignment_ID());
		if(getM_Product_ID() == 0)
		    setC_Charge_ID(sLine.getC_Charge_ID());
		//
		int C_OrderLine_ID = sLine.getC_OrderLine_ID();
		if (C_OrderLine_ID != 0)
		{
			MOrderLine oLine = new MOrderLine (getCtx(), C_OrderLine_ID, get_TrxName());
			setS_ResourceAssignment_ID(oLine.getS_ResourceAssignment_ID());
			//
			if (sLine.sameOrderLineUOM())
				setPriceEntered(oLine.getPriceEntered());
			else
				setPriceEntered(oLine.getPriceActual());
			setPriceActual(oLine.getPriceActual());
			setPriceLimit(oLine.getPriceLimit());
			setPriceList(oLine.getPriceList());
			//
			setC_Tax_ID(oLine.getC_Tax_ID());
			setLineNetAmt(oLine.getLineNetAmt());
			setC_Project_ID(oLine.getC_Project_ID());
		}
		// Check if shipment line is based on RMA
        else if (sLine.getM_RMALine_ID() != 0)
        {
        	// Set Pricing details from the RMA Line on which it is based
            MRMALine rmaLine = new MRMALine(getCtx(), sLine.getM_RMALine_ID(), get_TrxName());

            setPrice();
            setPrice(rmaLine.getAmt());
            setC_Tax_ID(rmaLine.getC_Tax_ID());
            setLineNetAmt(rmaLine.getLineNetAmt());
        }
		else
		{
			setPrice();
			setTax();
		}
		//
		setC_Project_ID(sLine.getC_Project_ID());
		setC_ProjectPhase_ID(sLine.getC_ProjectPhase_ID());
		setC_ProjectTask_ID(sLine.getC_ProjectTask_ID());
		setC_Activity_ID(sLine.getC_Activity_ID());
		setC_Campaign_ID(sLine.getC_Campaign_ID());
		setAD_OrgTrx_ID(sLine.getAD_OrgTrx_ID());
		setUser1_ID(sLine.getUser1_ID());
		setUser2_ID(sLine.getUser2_ID());
	}	//	setShipLine

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
	 * 	Set M_AttributeSetInstance_ID
	 *	@param M_AttributeSetInstance_ID id
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID == 0)		//	 0 is valid ID
			set_Value("M_AttributeSetInstance_ID", new Integer(0));
		else
			super.setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
	}	//	setM_AttributeSetInstance_ID


	/**************************************************************************
	 * 	Set Price for Product and PriceList.
	 * 	Uses standard SO price list of not set by invoice constructor
	 */
	public void setPrice()
	{
		if (getM_Product_ID() == 0 || isDescription())
			return;
		if (m_M_PriceList_ID == 0 || m_C_BPartner_ID == 0)
			setInvoice(getParent());
		if (m_M_PriceList_ID == 0 || m_C_BPartner_ID == 0)
			throw new IllegalStateException("setPrice - PriceList unknown!");
		setPrice (m_M_PriceList_ID, m_C_BPartner_ID);
	}	//	setPrice

	/**
	 * 	Set Price for Product and PriceList
	 * 	@param M_PriceList_ID price list
	 * 	@param C_BPartner_ID business partner
	 */
	public void setPrice (int M_PriceList_ID, int C_BPartner_ID)
	{
		if (getM_Product_ID() == 0 || isDescription())
			return;
		//
		log.fine("M_PriceList_ID=" + M_PriceList_ID);
		m_productPricing = new MProductPricing (getM_Product_ID(),
			C_BPartner_ID, getQtyInvoiced(), m_IsSOTrx);
		m_productPricing.setM_PriceList_ID(M_PriceList_ID);

		//OpenUp #867 Nicolas Sarlabos 06/09/2011
		//si es una operacion de COMPRA y existe al menos 1 version de la lista de precios asociada,
		//entonces se obtiene la version de la lista con el ID mayor
		if(!this.m_IsSOTrx){
			int priceListVersion =DB.getSQLValue(null,"select MAX(m_pricelist_version_id) from m_pricelist_version" +
					" where m_pricelist_id=" + M_PriceList_ID + " and isactive='Y'");   
			if(priceListVersion>0) m_productPricing.setM_PriceList_Version_ID(priceListVersion);
		}
		//fin OpenUp #867
		
		m_productPricing.setPriceDate(m_DateInvoiced);
		//
		setPriceActual (m_productPricing.getPriceStd());
		setPriceList (m_productPricing.getPriceList());
		setPriceLimit (m_productPricing.getPriceLimit());
		//
		if (getQtyEntered().compareTo(getQtyInvoiced()) == 0)
			setPriceEntered(getPriceActual());
		else
			setPriceEntered(getPriceActual().multiply(getQtyInvoiced()
				.divide(getQtyEntered(), 6, BigDecimal.ROUND_HALF_UP)));	//	precision
		//
		if (getC_UOM_ID() == 0)
			setC_UOM_ID(m_productPricing.getC_UOM_ID());
		//
		m_priceSet = true;
	}	//	setPrice

	/**
	 * 	Set Price Entered/Actual.
	 * 	Use this Method if the Line UOM is the Product UOM
	 *	@param PriceActual price
	 */
	public void setPrice (BigDecimal PriceActual)
	{
		setPriceEntered(PriceActual);
		setPriceActual (PriceActual);
	}	//	setPrice

	/**
	 * 	Set Price Actual.
	 * 	(actual price is not updateable)
	 *	@param PriceActual actual price
	 */
	public void setPriceActual (BigDecimal PriceActual)
	{
		if (PriceActual == null)
			throw new IllegalArgumentException ("PriceActual is mandatory");
		set_ValueNoCheck("PriceActual", PriceActual);
	}	//	setPriceActual


	/**
	 *	Set Tax - requires Warehouse
	 *	@return true if found
	 */
	public boolean setTax()
	{
		if (isDescription())
			return true;
		//
		int M_Warehouse_ID = Env.getContextAsInt(getCtx(), "#M_Warehouse_ID");
		//
		int C_Tax_ID = Tax.get(getCtx(), getM_Product_ID(), getC_Charge_ID() , m_DateInvoiced, m_DateInvoiced,
			getAD_Org_ID(), M_Warehouse_ID,
			m_C_BPartner_Location_ID,		//	should be bill to
			m_C_BPartner_Location_ID, m_IsSOTrx);
		
		// OpenUp. M.R 18-02-2011 issue#355 Agrego esta validacion para setear los impuestos en los productos y que sean exentos
		int C_DocTypeTarget_ID = 0;
		MInvoice invoice = new MInvoice(getCtx(), this.getC_Invoice_ID(), get_TrxName());
		if (invoice!=null){
			C_DocTypeTarget_ID = invoice.getC_DocTypeTarget_ID();
		}
		
		if (C_Tax_ID == 0)
		{
			log.log(Level.SEVERE, "No Tax found");
			return false;
		}
		// OpenUp M.R. Traigo DocBaseType para tipo de documento Factura Importaciones
		MDocType doc = new MDocType(Env.getCtx(), C_DocTypeTarget_ID, null);
		String DocBaseType = doc.getDocBaseType();

		if (DocBaseType.equalsIgnoreCase(Doc.DOCTYPE_FacturaImportaciones)){
			setC_Tax_ID (Idexento);
			return true;
		}
		if (doc.getDocSubTypeSO()!=null){
			if (doc.getDocSubTypeSO().equalsIgnoreCase("NI") || doc.getDocSubTypeSO().equalsIgnoreCase("FA") || doc.getDocSubTypeSO().equalsIgnoreCase("NA")){
				setC_Tax_ID (Idexento);
				return true;
			}
		}
		// Fin OpenUp.
		
		setC_Tax_ID (C_Tax_ID);
		return true;
	}	//	setTax


	/**
	 * 	Calculate Tax Amt.
	 * 	Assumes Line Net is calculated
	 */
	public void setTaxAmt ()
	{
		BigDecimal TaxAmt = Env.ZERO;
		// OpenUp. Nicolas Sarlabos. 06/03/2014. Issue #1623.
		BigDecimal amount = getLineNetAmt();
		MDocType doc = new MDocType(getCtx(), this.getParent().getC_DocTypeTarget_ID(), null);
		if (getC_Tax_ID() == 0)
			return;
	//	setLineNetAmt();
		MTax tax = MTax.get (getCtx(), getC_Tax_ID());
		if (tax.isDocumentLevel() && m_IsSOTrx) {  //	AR Inv Tax

			// OpenUp. Gabriel Vila. 04/10/2012. Issue #
			// Para comprobantes manuales de ingreso, debo calcular monto de impuesto
			// Por ahora pregunto por el value del documento
			//return;			
			if (doc.getValue() != null){
				if ((!doc.getValue().equalsIgnoreCase("ctinvpublicity"))
						&& (!doc.getValue().equalsIgnoreCase("custinvoice"))
						&& (!doc.getValue().equalsIgnoreCase("customernc"))
						&& (!doc.getValue().equalsIgnoreCase("customerinv"))
						&& (!doc.getValue().equalsIgnoreCase("custinvoicectr"))
						&& (!doc.getValue().equalsIgnoreCase("fileprofinvoice"))
						&& (!doc.getValue().equalsIgnoreCase("freightinvoice")) //OpenUp. Nicolas Sarlabos. 18/03/2013. Agrego documento "Contado"
					    && (!doc.getValue().equalsIgnoreCase("ctinvpublicityconov"))
					    && (!doc.getValue().equalsIgnoreCase("facturaflete"))
					    && (!doc.getValue().equalsIgnoreCase("et-ticket"))
					    && (!doc.getValue().equalsIgnoreCase("et-nc"))
					    && (!doc.getValue().equalsIgnoreCase("customerncflete"))){ //OpenUp. Leonardo Boccone. 28/08/2014. Agrego documento ""
					return;
				}
			}
			// Fin OpenUp
		}
			
		//
		if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("fileprofinvoice") || doc.getValue().equalsIgnoreCase("freightinvoice"))
				|| (doc.getValue().equalsIgnoreCase("customerncflete")) 
				|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc")){
			
			MInvoice inv = new MInvoice(getCtx(),getC_Invoice_ID(),get_TrxName()); //instancio cabezal de documento
			MTRTrip trip = new MTRTrip(getCtx(),inv.get_ValueAsInt("UY_TR_Trip_ID"),get_TrxName()); //instancio el expediente
			
			//si es importacion o nacional, y a su vez NO es en transito, se cobra IVA basico al terr. nacional
			if((trip.getTripType().equalsIgnoreCase("IMPORTACION") || trip.getTripType().equalsIgnoreCase("NACIONAL")) && !trip.isInTransit()){
				
				amount = (BigDecimal)this.get_Value("NationalAmt"); 
				
			} else amount = Env.ZERO;
			
			if(amount==null) amount = Env.ZERO;
			
			if(amount.compareTo(Env.ZERO)==0) {
				
				tax = MTax.forValue(getCtx(), "exento", get_TrxName());
				this.setC_Tax_ID(tax.get_ID());
				
			} else {
				
				tax = MTax.forValue(getCtx(), "basico", get_TrxName());
				this.setC_Tax_ID(tax.get_ID());
				
			}
		}
				
		TaxAmt = tax.calculateTax(amount, isTaxIncluded(), getPrecision());
					
		if (isTaxIncluded()){
		
			if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("fileprofinvoice") || doc.getValue().equalsIgnoreCase("freightinvoice")
					|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc") 
					|| (doc.getValue().equalsIgnoreCase("customerncflete")))){
				
				setLineTotalAmt(this.getPriceEntered());
				
			} else setLineTotalAmt(amount);
			
		} else{
			
			if(doc.getValue()!=null && (doc.getValue().equalsIgnoreCase("fileprofinvoice") || doc.getValue().equalsIgnoreCase("freightinvoice")
					|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc") 
					|| (doc.getValue().equalsIgnoreCase("customerncflete")))){
				
				setLineTotalAmt(this.getPriceEntered().add(TaxAmt));
				
			} else setLineTotalAmt(amount.add(TaxAmt));
			
		}
		//Fin OpenUp #1623.	
		super.setTaxAmt (TaxAmt);
	}	//	setTaxAmt

	/**
	 * 	Calculate Extended Amt.
	 * 	May or may not include tax
	 */
	public void setLineNetAmt ()
	{
		//	Calculations & Rounding
		BigDecimal bd = getPriceActual().multiply(getQtyInvoiced());
		
		MInvoice invHdr = new MInvoice(getCtx(), this.getC_Invoice_ID(), get_TrxName());
		if (invHdr.isSOTrx()) {
			//OpenUp. Nicolas Sarlabos. 16/10/2012
			//quito redondeo por venir OK al generarse desde el pedido de venta
			bd = getPriceActual().multiply(getQtyInvoiced());
			//Fin OpenUp
		} else
			bd = getPriceEntered().multiply(getQtyEntered()); //OpenUp Nicolas Sarlabos 18/04/2012
		
		
		boolean documentLevel = getTax().isDocumentLevel();

		//	juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		//  http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded() && !documentLevel)	{
			BigDecimal taxStdAmt = Env.ZERO, taxThisAmt = Env.ZERO;
			
			MTax invoiceTax = getTax();
			MTax stdTax = null;
			
			if (getProduct() == null)
			{
				if (getCharge() != null)	// Charge 
				{
					stdTax = new MTax (getCtx(), 
							((MTaxCategory) getCharge().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(),
							get_TrxName());
				}
					
			}
			else	// Product
				stdTax = new MTax (getCtx(), 
							((MTaxCategory) getProduct().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(), 
							get_TrxName());

			if (stdTax != null)
			{
				
				log.fine("stdTax rate is " + stdTax.getRate());
				log.fine("invoiceTax rate is " + invoiceTax.getRate());
				
				taxThisAmt = taxThisAmt.add(invoiceTax.calculateTax(bd, isTaxIncluded(), getPrecision()));
				taxStdAmt = taxStdAmt.add(stdTax.calculateTax(bd, isTaxIncluded(), getPrecision()));
				
				bd = bd.subtract(taxStdAmt).add(taxThisAmt);
				
				log.fine("Price List includes Tax and Tax Changed on Invoice Line: New Tax Amt: " 
						+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + bd);	
			}
		}
		
		if (bd.scale() > getPrecision())
			bd = bd.setScale(getPrecision(), BigDecimal.ROUND_HALF_UP);

		// OpenUp. Gabriel Vila. 07/10/2015. Issue #4861
		// Proceso porcentaje de descuento manual en linea
		if (this.get_Value("DiscountPerc") != null){
			BigDecimal percDisc = (BigDecimal)this.get_Value("DiscountPerc");
			if (percDisc != null){
				if (percDisc.compareTo(Env.ZERO) > 0){
					bd = bd.multiply(Env.ONE.subtract(percDisc.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP)));
				}
			}
			
		}
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

		if (this.get_Value("Discount") != null){
			BigDecimal disc1 = (BigDecimal)this.get_Value("Discount");
			if (disc1 != null){
				if (disc1.compareTo(Env.ZERO) > 0){
					bd = bd.multiply(Env.ONE.subtract(disc1.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP)));
				}
			}
			
		}
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

		if (this.get_Value("Discount2") != null){
			BigDecimal disc = (BigDecimal)this.get_Value("Discount2");
			if (disc != null){
				if (disc.compareTo(Env.ZERO) > 0){
					bd = bd.multiply(Env.ONE.subtract(disc.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP)));
				}
			}
			
		}
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

		if (this.get_Value("Discount3") != null){
			BigDecimal disc = (BigDecimal)this.get_Value("Discount3");
			if (disc != null){
				if (disc.compareTo(Env.ZERO) > 0){
					bd = bd.multiply(Env.ONE.subtract(disc.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP)));
				}
			}
			
		}
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

		if (this.get_Value("Discount4") != null){
			BigDecimal disc = (BigDecimal)this.get_Value("Discount4");
			if (disc != null){
				if (disc.compareTo(Env.ZERO) > 0){
					bd = bd.multiply(Env.ONE.subtract(disc.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP)));
				}
			}
			
		}
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		// Fin OpenUp. Issue #4861
				
		super.setLineNetAmt (bd);
	}	//	setLineNetAmt
	/**
	 * 	Get Charge
	 *	@return product or null
	 */
	public MCharge getCharge()
	{
		if (m_charge == null && getC_Charge_ID() != 0)
			m_charge =  MCharge.get (getCtx(), getC_Charge_ID());
		return m_charge;
	}
	/**
	 * 	Get Tax
	 *	@return tax
	 */
	protected MTax getTax()
	{
		if (m_tax == null)
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		return m_tax;
	}	//	getTax

	/**
	 * 	Set Qty Invoiced/Entered.
	 *	@param Qty Invoiced/Ordered
	 */
	public void setQty (int Qty)
	{
		setQty(new BigDecimal(Qty));
	}	//	setQtyInvoiced

	/**
	 * 	Set Qty Invoiced
	 *	@param Qty Invoiced/Entered
	 */
	public void setQty (BigDecimal Qty)
	{
		setQtyEntered(Qty);
		setQtyInvoiced(getQtyEntered());
	}	//	setQtyInvoiced

	/**
	 * 	Set Qty Entered - enforce entered UOM
	 *	@param QtyEntered
	 */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		if (QtyEntered != null && getC_UOM_ID() != 0)
		{
			int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
			QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyEntered (QtyEntered);
	}	//	setQtyEntered

	/**
	 * 	Set Qty Invoiced - enforce Product UOM
	 *	@param QtyInvoiced
	 */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		MProduct product = getProduct();
		if (QtyInvoiced != null && product != null)
		{
			int precision = product.getUOMPrecision();
			QtyInvoiced = QtyInvoiced.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyInvoiced(QtyInvoiced);
	}	//	setQtyInvoiced

	/**
	 * 	Set Product
	 *	@param product product
	 */
	public void setProduct (MProduct product)
	{
		m_product = product;
		if (m_product != null)
		{
			setM_Product_ID(m_product.getM_Product_ID());
			setC_UOM_ID (m_product.getC_UOM_ID());
		}
		else
		{
			setM_Product_ID(0);
			setC_UOM_ID (0);
		}
		setM_AttributeSetInstance_ID(0);
	}	//	setProduct


	/**
	 * 	Set M_Product_ID
	 *	@param M_Product_ID product
	 *	@param setUOM set UOM from product
	 */
	public void setM_Product_ID (int M_Product_ID, boolean setUOM)
	{
		if (setUOM)
			setProduct(MProduct.get(getCtx(), M_Product_ID));
		else
			super.setM_Product_ID (M_Product_ID);
		setM_AttributeSetInstance_ID(0);
	}	//	setM_Product_ID

	/**
	 * 	Set Product and UOM
	 *	@param M_Product_ID product
	 *	@param C_UOM_ID uom
	 */
	public void setM_Product_ID (int M_Product_ID, int C_UOM_ID)
	{
		super.setM_Product_ID (M_Product_ID);
		super.setC_UOM_ID(C_UOM_ID);
		setM_AttributeSetInstance_ID(0);
	}	//	setM_Product_ID

	/**
	 * 	Get Product
	 *	@return product or null
	 */
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
			m_product =  MProduct.get (getCtx(), getM_Product_ID());
		return m_product;
	}	//	getProduct

	/**
	 * 	Get C_Project_ID
	 *	@return project
	 */
	public int getC_Project_ID()
	{
		int ii = super.getC_Project_ID ();
		if (ii == 0)
			ii = getParent().getC_Project_ID();
		return ii;
	}	//	getC_Project_ID

	/**
	 * 	Get C_Activity_ID
	 *	@return Activity
	 */
	public int getC_Activity_ID()
	{
		int ii = super.getC_Activity_ID ();
		if (ii == 0)
			ii = getParent().getC_Activity_ID();
		return ii;
	}	//	getC_Activity_ID

	/**
	 * 	Get C_Campaign_ID
	 *	@return Campaign
	 */
	public int getC_Campaign_ID()
	{
		int ii = super.getC_Campaign_ID ();
		if (ii == 0)
			ii = getParent().getC_Campaign_ID();
		return ii;
	}	//	getC_Campaign_ID

	/**
	 * 	Get User2_ID
	 *	@return User2
	 */
	public int getUser1_ID ()
	{
		int ii = super.getUser1_ID ();
		if (ii == 0)
			ii = getParent().getUser1_ID();
		return ii;
	}	//	getUser1_ID

	/**
	 * 	Get User2_ID
	 *	@return User2
	 */
	public int getUser2_ID ()
	{
		int ii = super.getUser2_ID ();
		if (ii == 0)
			ii = getParent().getUser2_ID();
		return ii;
	}	//	getUser2_ID

	/**
	 * 	Get AD_OrgTrx_ID
	 *	@return trx org
	 */
	public int getAD_OrgTrx_ID()
	{
		int ii = super.getAD_OrgTrx_ID();
		if (ii == 0)
			ii = getParent().getAD_OrgTrx_ID();
		return ii;
	}	//	getAD_OrgTrx_ID

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MInvoiceLine[")
			.append(get_ID()).append(",").append(getLine())
			.append(",QtyInvoiced=").append(getQtyInvoiced())
			.append(",LineNetAmt=").append(getLineNetAmt())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Get (Product/Charge) Name
	 * 	@return name
	 */
	public String getName ()
	{
		if (m_name == null)
		{
			String sql = "SELECT COALESCE (p.Name, c.Name) "
				+ "FROM C_InvoiceLine il"
				+ " LEFT OUTER JOIN M_Product p ON (il.M_Product_ID=p.M_Product_ID)"
				+ " LEFT OUTER JOIN C_Charge C ON (il.C_Charge_ID=c.C_Charge_ID) "
				+ "WHERE C_InvoiceLine_ID=?";
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getC_InvoiceLine_ID());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
					m_name = rs.getString(1);
				rs.close();
				pstmt.close();
				pstmt = null;
				if (m_name == null)
					m_name = "??";
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "getName", e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e)
				{}
				pstmt = null;
			}
		}
		return m_name;
	}	//	getName

	/**
	 * 	Set Temporary (cached) Name
	 * 	@param tempName Cached Name
	 */
	public void setName (String tempName)
	{
		m_name = tempName;
	}	//	setName

	/**
	 * 	Get Description Text.
	 * 	For jsp access (vs. isDescription)
	 *	@return description
	 */
	public String getDescriptionText()
	{
		return super.getDescription();
	}	//	getDescriptionText

	/**
	 * 	Get Currency Precision
	 *	@return precision
	 */
	public int getPrecision()
	{
		if (m_precision != null)
			return m_precision.intValue();

		String sql = "SELECT c.StdPrecision "
			+ "FROM C_Currency c INNER JOIN C_Invoice x ON (x.C_Currency_ID=c.C_Currency_ID) "
			+ "WHERE x.C_Invoice_ID=?";
		int i = DB.getSQLValue(get_TrxName(), sql, getC_Invoice_ID());
		if (i < 0)
		{
			log.warning("getPrecision = " + i + " - set to 2");
			i = 2;
		}
		m_precision = new Integer(i);
		return m_precision.intValue();
	}	//	getPrecision

	/**
	 *	Is Tax Included in Amount
	 *	@return true if tax is included
	 */
	public boolean isTaxIncluded()
	{
		if (m_M_PriceList_ID == 0)
		{
			m_M_PriceList_ID = DB.getSQLValue(get_TrxName(),
				"SELECT M_PriceList_ID FROM C_Invoice WHERE C_Invoice_ID=?",
				getC_Invoice_ID());
		}
		MPriceList pl = MPriceList.get(getCtx(), m_M_PriceList_ID, get_TrxName());
		return pl.isTaxIncluded();
	}	//	isTaxIncluded


	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord
	 *	@return true if save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		log.fine("New=" + newRecord);
		if (newRecord && getParent().isComplete()) {
			log.saveError("ParentComplete", Msg.translate(getCtx(), "C_InvoiceLine"));
			return false;
		}
		
		MDocType doc = new MDocType(Env.getCtx(), getParent().getC_DocTypeTarget_ID(), null);
		boolean esDocumentoImportacion = false;
		
		//Ini OpenUp SBT 23/02/2016 Issue #5471
		//No se permiten modificar datos de la línea en una nota de credito asociada a una factura
		if(!newRecord && doc.getValue().equalsIgnoreCase("customernc")){
			if(this.getC_Invoice().getUY_Invoice_ID()>0){
				throw new AdempiereException("No se permite realizar cambios en la línea, ya que la Nota de Crédito está asociada a una Factura");
			}
		}
		//FIN OpenUp SBT 23/02/2016 Issue #5471
		
		
		// OpenUp. Mario Reyes. 13/09/2011. Issue #754.
		// Me aseguro que los documentos de compra que no tienen impuestos queden exentos.
		if ((newRecord) && (!getParent().isSOTrx())){
			String DocBaseType = doc.getDocBaseType();
			if (DocBaseType.equalsIgnoreCase(Doc.DOCTYPE_FacturaImportaciones)) setC_Tax_ID (Idexento); 
			if (doc.getDocSubTypeSO()!=null){
				if (doc.getDocSubTypeSO().equalsIgnoreCase("NI")) setC_Tax_ID (Idexento);
			}
			
		}
		// Fin Issue #754
		
		
		// OpenUp. Gabriel Vila. 15/09/2011. Issue #820
		// Documentos de ajuste de cuenta corriente.
		if ((newRecord) && (getParent().isSOTrx())){
			if (doc.getDocSubTypeSO() != null){
				if (doc.getDocSubTypeSO().equalsIgnoreCase("FA") || doc.getDocSubTypeSO().equalsIgnoreCase("NA")){
					setC_Tax_ID (Idexento);
				}
			}
		}
		// Fin Issue #820
		
		// ----------------Definicion modelos Necesarios----------------.
		MInvoice invHdr = new MInvoice(getCtx(), this.getC_Invoice_ID(), get_TrxName());
		MOrderLine orderLine = (MOrderLine) this.getC_OrderLine();
		// ----------------Fin------------------------------------------.

		// Re-set invoice header (need to update m_IsSOTrx flag) - phib [ 1686773 ]
		setInvoice(getParent());
		//	Charge
		if (getC_Charge_ID() != 0)
		{
			if (getM_Product_ID() != 0)
				setM_Product_ID(0);
		}
		else	//	Set Product Price
		{
			// OpenUp. Gabriel Vila. 27/05/2011. Issue # (ver generacion e impresion de remitos)
			// No se deben recalcular los precios e importes en documentos de remito.
			// Puse el IF.
			if ( (doc.getDocSubTypeSO() == null)  
					|| ((doc.getDocSubTypeSO() != null) && (!doc.getDocSubTypeSO().equalsIgnoreCase("RR"))) )  {

				//OpenUp Nicolas Sarlabos issue #867
				//no se permite ingresar precio <= 0 salvo en documentos de importacion
				
				if (!m_IsSOTrx){

					// OpenUp. Gabriel Vila. 18/09/2011.
					// Para documentos de importacion no debo controlar precios cero.					
					if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_FacturaImportaciones)) esDocumentoImportacion = true; 
					if (doc.getDocSubTypeSO()!=null){
						if (doc.getDocSubTypeSO().equalsIgnoreCase("NI")){
							esDocumentoImportacion = true;
						}
					}
					if (!esDocumentoImportacion){
						if (this.getPriceEntered().compareTo(Env.ZERO) == 0){
							throw new AdempiereException("Debe ingresar Precio");
						}
					}
				}
				//fin OpenUp #867
				
				if (!m_priceSet
						&&  Env.ZERO.compareTo(getPriceActual()) == 0
						&&  Env.ZERO.compareTo(getPriceList()) == 0)
						setPrice();
					
				// OpenUp. Gabriel Vila. 18/09/2011.
				// Para documentos de importacion no debo controlar precios cero.
				if (!esDocumentoImportacion){ 
					// Check zero prices against the base price list. OpenUP FL 11/03/2011, issue #480
					if ((!invHdr.getDocAction().equalsIgnoreCase(DocumentEngine.ACTION_Void)) && (!this.checkZeroPrices())) {
						throw new AdempiereException("El precio del producto debe ser mayor a cero. Verifique los precios en la lista de precios del pedido, del cliente y la base.");		// TODO: Translate
					}
				}
				
				

			}
			// Fin OpenUp.
		}

		// OpenUp. Gabriel Vila. 27/05/2011. Issue # (ver generacion e impresion de remitos)
		// No se deben recalcular los precios e importes en documentos de remito.
		// Puse el IF.
		if ( (doc.getDocSubTypeSO() == null)  
				|| ((doc.getDocSubTypeSO() != null) && (!doc.getDocSubTypeSO().equalsIgnoreCase("RR"))) )  {

			//	Set Tax
			if (getC_Tax_ID() == 0)
				setTax();

		}
		// Fin OpenUp.

		//	Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_InvoiceLine WHERE C_Invoice_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getC_Invoice_ID());
			setLine (ii);
		}
		//	UOM
		if (getC_UOM_ID() == 0)
		{
			int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
			if (C_UOM_ID > 0)
				setC_UOM_ID (C_UOM_ID);
		}
		//	Qty Precision
		if (newRecord || is_ValueChanged("QtyEntered"))
			setQtyEntered(getQtyEntered());
		if (newRecord || is_ValueChanged("QtyInvoiced"))
			setQtyInvoiced(getQtyInvoiced());
		
		// OpenUp. Gabriel Vila. 27/05/2011. Issue # (ver generacion e impresion de remitos)
		// No se deben recalcular los precios e importes en documentos de remito.
		// Puse el IF.
		if ( (doc.getDocSubTypeSO() == null)  
				|| ((doc.getDocSubTypeSO() != null) && (!doc.getDocSubTypeSO().equalsIgnoreCase("RR"))) )  {

			
			//OpenUp. Nicolas Sarlabos. 12/11/2012
			//me aseguro de no setear neto de linea si es una Factura Venta
			//	Calculations & Rounding
			//if(doc.getValue()!=null && !doc.getValue().equalsIgnoreCase("custinvoice")){
				setLineNetAmt();
			//}
			//Fin OpenUp.

			// TaxAmt recalculations should be done if the TaxAmt is zero
			// or this is an Invoice(Customer) - teo_sarca, globalqss [ 1686773 ]
			if (m_IsSOTrx || getTaxAmt().compareTo(Env.ZERO) == 0)
				setTaxAmt();
			
		}
		// Fin OpenUp.

		// OpenUp. Nicolas Sarlabos. 14/06/2011. Issue #676
		// Valido que una nota de credito por diferencia de precio
		// ingresada manualmente, no super el limite maximo de lineas
		// por invoice.
		String docSubTypeSO = (doc.getDocSubTypeSO() == null) ? "" : doc.getDocSubTypeSO();
		if (((doc.getDocBaseType().equalsIgnoreCase("ARC")) && (docSubTypeSO.equalsIgnoreCase("NP")))
		 || ((doc.getDocBaseType().equalsIgnoreCase("ARI")) && (doc.isSOTrx()))){
			int tope = MSysConfig.getIntValue("UY_MAX_LINEAS_FACTURA", 30, invHdr.getAD_Client_ID());
			MInvoiceLine[] lines = invHdr.getLines(true);
			
			//OpenUp Nicolas Sarlabos 30/08/2012, se agrega IF para no controlar tope al anular  
			if ((!invHdr.getDocAction().equalsIgnoreCase(DocumentEngine.ACTION_Void))){
				if (lines.length > tope){
					throw new AdempiereException("No se puede superar el limite de " + tope + " lineas por Documento.");	
				}
			}
		}
		// Fin OpenUp Nicolas Sarlabos 30/08/2012
		
		// Fin OpenUp
		
		// Si es remito me aseguro que los totales de la linea y los precios queden en cero
		if (doc.getDocSubTypeSO() != null){
			if (doc.getDocSubTypeSO().equalsIgnoreCase("RR")){
				this.setPriceActual(Env.ZERO);
				this.setPriceEntered(Env.ZERO);
				this.setPriceList(Env.ZERO);
				this.setPriceLimit(Env.ZERO);
				this.setLineNetAmt(Env.ZERO);
				this.setLineTotalAmt(Env.ZERO);
			}
		}
		
		// OpenUp. Nicolas Garcia. 28/10/2011. Issue #821 Bonificacion Cruzada.
		this.setUY_EsBonificCruzada(orderLine.isUY_EsBonificCruzada());
		this.setUY_TieneBonificCruzada(orderLine.isUY_TieneBonificCruzada());

		// Si es una bonificacion Cruzada entonces precios cero
		if (this.isUY_EsBonificCruzada()) {
			this.setPriceActual(Env.ZERO);
			this.setPriceEntered(Env.ZERO);
			this.setPriceList(Env.ZERO);
			this.setPriceLimit(Env.ZERO);
			this.setLineNetAmt(Env.ZERO);
			this.setLineTotalAmt(Env.ZERO);
		}
		// Fin Issue #821

		//setTax();//OpenUp M.R. Issue#820 Agrego linea para traer impuestos segun el documneto del cabezal
		//OpenUp M.R. 16-08-2011 se comenta la linea agregada en el issue#820 por pedido de marianela, dado que quiere cambiar manual el cargo con sus impuestos. 
	
		//OpenUp. Nicolas Sarlabos. 09/11/2012, aplico porcentaje de descuento si la linea de factura no proviene de una orden o devolucion
		//OpenUp. Nicolas Sarlabos. 06/03/2013. Cambio system configurator
		if (MSysConfig.getBooleanValue("UY_DESCUENTO_LINEA_FACTURA", false, this.getAD_Client_ID())){

			if (invHdr.isSOTrx()){
				//OpenUp. Nicolas Sarlabos. 08/03/2013. #492. Modifico IF para que no calcule el descuento si hay seleccionada una OF. 
				if(invHdr.getUY_ManufOrder_ID() <= 0 && this.getC_OrderLine_ID()<=0 && this.getM_InOutLine_ID()<=0 && this.getFlatDiscount().compareTo(Env.ZERO)>0){
					int StdPrecision = getPrecision();
					BigDecimal discount = this.getFlatDiscount().divide(Env.ONEHUNDRED);
					discount = this.getLineNetAmt().multiply(discount);

					this.setLineNetAmt((this.getLineNetAmt().subtract(discount)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP));
					
				}	
				//Fin OpenUp.
			}			
		}
		//Fin OpenUp
		//Fin OpenUp
		//OpenUp. Nicolas Sarlabos. 14/03/2013. #543. Controlo en que documentos se aplica el descuento al grabar la linea
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){

			if (invHdr.isSOTrx()){
				if(doc.get_ID()==1000749 || doc.get_ID()==1000759 || doc.get_ID()==1000760 || doc.get_ID()==1000761 || doc.get_ID()==1000755 || doc.get_ID()==1000756){
					int StdPrecision = getPrecision();
					BigDecimal discount = this.getFlatDiscount().divide(Env.ONEHUNDRED);
					discount = this.getLineNetAmt().multiply(discount);

					this.setLineNetAmt((this.getLineNetAmt().subtract(discount)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP));

				}	
				
			}			
		}
		//Fin OpenUp.
		//OpenUp. Nicolas Sarlabos. 20/11/2012
		//si es una factura con orden de fabricacion tengo que controlar que la cantidad en la linea no supere la cantidad pendiente de facturar
		//en la orden de fabricacion
		if(invHdr.getDocStatus().equalsIgnoreCase("DR")){
			if(invHdr.getUY_ManufOrder_ID()>0){

				MManufLine mLine = new MManufLine (getCtx(), this.getUY_ManufLine_ID(), get_TrxName());

				if(mLine!=null){

					BigDecimal qtyPending = mLine.getQty().subtract(mLine.getQtyInvoiced());

					if(this.getQtyEntered().compareTo(qtyPending) > 0){

						throw new AdempiereException ("La cantidad ingresada no puede ser mayor a " + qtyPending.setScale(2, RoundingMode.HALF_UP));
					}
				}

			}
		}
		//Fin OpenUp.
	
		// OpenUp. Guillermo Brust. 30/04/2013. ISSUE #787.
		// Controlo que la cantidad digitada no supere la cantidad pendiente de la linea de factura asociada a esta linea de NC
		if(doc.getValue() != null){
			if(doc.getValue().equalsIgnoreCase("customernc")){
				if(this.getUY_ManufOrder_ID() > 0){
					BigDecimal cantAux = this.getuy_cantidadpedido(); //OpenUp. Nicolas Sarlabos. 07/06/2013. #958
					if (cantAux != null){
						if (this.getQtyEntered().compareTo(cantAux) > 0){
							throw new AdempiereException ("La cantidad ingresada no puede ser mayor a la cantidad de la linea de Factura.");
						}
					}
				}					
			} else if(doc.getValue().equalsIgnoreCase("fileprofinvoice") || doc.getValue().equalsIgnoreCase("freightinvoice") || doc.getValue().equalsIgnoreCase("customerncflete")
					|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc")){
				
				BigDecimal natPercentage = (BigDecimal)this.get_Value("NationalPercentage");
				BigDecimal interPercentage = (BigDecimal)this.get_Value("interPercentage");
	
				if(natPercentage == null) natPercentage = Env.ZERO;
				if(interPercentage == null) interPercentage = Env.ZERO;
						
				if(natPercentage.compareTo(Env.ZERO) < 0) throw new AdempiereException ("El porcentaje nacional no puede ser menor a cero");
				if(natPercentage.compareTo(Env.ONEHUNDRED) > 0) throw new AdempiereException ("El porcentaje nacional no puede ser mayor a 100%");
				if(interPercentage.compareTo(Env.ZERO) < 0) throw new AdempiereException ("El porcentaje internacional no puede ser menor a cero");
				if(interPercentage.compareTo(Env.ONEHUNDRED) > 0) throw new AdempiereException ("El porcentaje internacional no puede ser mayor a 100%");
				
				BigDecimal interAmt = (BigDecimal)this.get_Value("InternationalAmt");
				BigDecimal natAmt = (BigDecimal)this.get_Value("NationalAmt");
				
				if(interAmt == null) interAmt = Env.ZERO;
				if(natAmt == null) natAmt = Env.ZERO;

				if(invHdr.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Drafted) || invHdr.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_InProgress) || invHdr.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Invalid)){
					if(interAmt.compareTo(Env.ZERO)>0 || natAmt.compareTo(Env.ZERO)>0){

						BigDecimal amtNational = (BigDecimal) this.get_Value("nationalamt");

						if(amtNational==null) amtNational = Env.ZERO;

						BigDecimal amtInterNational = (BigDecimal) this.get_Value("internationalamt");

						if(amtInterNational==null) amtInterNational = Env.ZERO;

						BigDecimal totalAmt = amtNational.add(amtInterNational);

						this.setPriceEntered(totalAmt);

					}

					this.setDataFileProform(invHdr, newRecord); //OpenUp. Nicolas Sarlabos. 21/02/2014. #1623.
				}
				
			//OpenUp. Nicolas Sarlabos. 13/10/2014. #3057.	
			} else if (doc.getValue().equalsIgnoreCase("valeflete")){
				
				this.verifyVFLine(); //OpenUp. Nicolas Sarlabos. 31/03/2015. #3880.
				
				MTax tax = MTax.forValue(getCtx(), "exento", get_TrxName());
				
				if(tax != null) this.setC_Tax_ID(tax.get_ID());	
				
				this.setTaxAmt();
				
			} 
			//Fin OpenUp #3057.
			// OpenUp. Gabriel Vila. 18/11/2014. #3205	
			// Para facturas de vale flete debo recalcular neto, total de linea y monto de impuesto en cero.
			else if (doc.getValue().equalsIgnoreCase("in_vvaleflete")){
				
				MTax tax = MTax.forValue(getCtx(), "exento", get_TrxName());
				
				if(tax != null) this.setC_Tax_ID(tax.get_ID());	
				
				this.setTaxAmt();
					
			}
			//Fin OpenUp #3205.
			
			
		}
		// Fin OpenUp. Issue #787.
		
		return true;
	}	//	beforeSave

	/***
	 * 
	 * OpenUp Ltda. Issue #3880
	 * @author Nicolas Sarlabos - 31/03/2015
	 * Metodo que impide modificar el importe de un concepto impreso en lineas de Vale Flete.
	 */
	private void verifyVFLine() {
		
		//obtengo, si hay, la linea de concepto impreso para el concepto de la linea actual
		MTRInvPrintFlete print = MTRInvPrintFlete.forInvoicePrintFlete(getCtx(), this.getC_Invoice_ID(), this.get_ValueAsInt("UY_TR_PrintFlete_ID"), get_TrxName());
		
		if(print!=null && print.get_ID()>0) {
			
			if(print.isPrinted()) throw new AdempiereException("No es posible modificar importes de conceptos impresos");
		}
		
	}

	/***
	 * 
	 * OpenUp Ltda. Issue #1623
	 * @author Nicolas Sarlabos - 27/02/2014
	 * Metodo que realiza calculos y setea campos en la linea de Proforma.
	 */
	private void setDataFileProform(MInvoice hdr, boolean newRecord) {

		if(!this.IsFromProforma && !this.IsFromFreightInv){

			MTRTrip trip = new MTRTrip(getCtx(),hdr.get_ValueAsInt("UY_TR_Trip_ID"),get_TrxName());
			MProduct prod = (MProduct) this.getM_Product();
			BigDecimal internationalAmt = Env.ZERO;
			BigDecimal nationalAmt = Env.ZERO;
			BigDecimal interPercentage = Env.ZERO;
			BigDecimal nationalPercentage = Env.ZERO;
			BigDecimal productAmt = Env.ZERO;

			MTax tax = MTax.forValue(getCtx(), "basico", get_TrxName());

			if(tax==null) throw new AdempiereException("No se pudo obtener tipo de impuesto Basico");

			if(trip.getTripType().equalsIgnoreCase("IMPORTACION") && !trip.isInTransit()) this.setC_Tax_ID(tax.get_ID());				

			if(prod != null && prod.getM_Product_ID() > 0){
				if(prod.getValue().equalsIgnoreCase("fleteimp") || prod.getValue().equalsIgnoreCase("fleteexp")){

					if(this.get_ValueAsBoolean("AplicaValorem") && !this.IsFromProforma && !this.IsFromFreightInv){

						if(this.forThisTripIDAndProductIDValorem(hdr,trip) == null){

							MInvoiceLine l = new MInvoiceLine(this.getCtx(), 0, this.get_TrxName());		
							MProduct valorem = MProduct.forValueAllClients(this.getCtx(), "valorem", this.get_TrxName());
							l.setM_Product_ID(valorem.get_ID());
							l.setC_Invoice_ID(this.getC_Invoice_ID());	
							l.set_Value("InterPercentage",(BigDecimal)this.get_Value("InterPercentage"));
							l.set_Value("NationalPercentage",(BigDecimal)this.get_Value("NationalPercentage"));
							l.set_Value("ValoremPercentage", new BigDecimal(0.3));
							l.saveEx();	
						}					
					}else{
						MInvoiceLine l = this.forThisTripIDAndProductIDValorem(hdr,trip);
						if( l != null && l.get_ID() > 0) l.delete(true);
					}
					productAmt = this.getPriceEntered();

					if(productAmt.compareTo(new BigDecimal(0)) > 0){			

						interPercentage = (BigDecimal)this.get_Value("InterPercentage");
						nationalPercentage = (BigDecimal)this.get_Value("NationalPercentage");

						if(interPercentage==null) interPercentage = Env.ZERO;
						if(nationalPercentage==null) nationalPercentage = Env.ZERO;

						if((this.get_Value("InternationalAmt")) == null || ((BigDecimal)this.get_Value("InternationalAmt")).compareTo(Env.ZERO)==0 || is_ValueChanged("InterPercentage") && interPercentage.compareTo(Env.ZERO)>0){
							internationalAmt = (interPercentage.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(productAmt).setScale(2, RoundingMode.HALF_UP));
							this.set_Value("InternationalAmt", internationalAmt);
						} else {

							interPercentage = (((BigDecimal)this.get_Value("InternationalAmt")).multiply(Env.ONEHUNDRED)).divide(productAmt, 2, RoundingMode.HALF_UP);
							this.set_Value("InterPercentage", interPercentage);

						}

						if((this.get_Value("NationalAmt")) == null || ((BigDecimal)this.get_Value("NationalAmt")).compareTo(Env.ZERO)==0 || is_ValueChanged("NationalPercentage") && nationalPercentage.compareTo(Env.ZERO)>0){
							nationalAmt = (nationalPercentage.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(productAmt).setScale(2, RoundingMode.HALF_UP));
							this.set_Value("NationalAmt", nationalAmt);
						} else {

							nationalPercentage = (((BigDecimal)this.get_Value("NationalAmt")).multiply(Env.ONEHUNDRED)).divide(productAmt, 2, RoundingMode.HALF_UP);
							this.set_Value("NationalPercentage", nationalPercentage);


						}

						this.setLineNetAmt(this.getPriceEntered());
						this.setTaxAmt();

					}		

				} else if((newRecord || is_ValueChanged("valorempercentage")) && prod.getValue().equalsIgnoreCase("valorem")){

					productAmt = hdr.getAmtOriginal();

					int curFromID = hdr.get_ValueAsInt("C_Currency2_ID");
					int curToID = hdr.getC_Currency_ID();

					//si moneda de carga y de factura son distintas, realizo conversion de valor de la carga a moneda de factura
					if(curFromID != curToID){

						BigDecimal rate = Env.ZERO;

						MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
						MAcctSchema schema = client.getAcctSchema();

						int curSchemaID = schema.getC_Currency_ID();

						MCurrency curFrom = new MCurrency(getCtx(),curFromID,get_TrxName());
						MCurrency curTo = new MCurrency(getCtx(),curToID,get_TrxName());
						MCurrency curSchema = new MCurrency(getCtx(),curSchemaID,get_TrxName());

						//obtengo tasa de conversion para moneda origen - moneda del esquema
						BigDecimal dividerate = MConversionRate.getDivideRate(curSchemaID, curFromID, hdr.getDateInvoiced(), 0, this.getAD_Client_ID(), this.getAD_Org_ID());

						//obtengo tasa de conversion para moneda destino - moneda del esquema
						BigDecimal dividerate2 = MConversionRate.getDivideRate(curSchemaID, curToID, hdr.getDateInvoiced(), 0, this.getAD_Client_ID(), this.getAD_Org_ID());

						if (dividerate == null || dividerate.compareTo(Env.ZERO)==0){

							ADialog.warn(0,null,"No se obtuvo tasa de cambio para las monedas " + curFrom.getCurSymbol() + " y " + curSchema.getCurSymbol());
							dividerate = Env.ONE;
						}

						if (dividerate2 == null || dividerate2.compareTo(Env.ZERO)==0){

							ADialog.warn(0,null,"No se obtuvo tasa de cambio para las monedas " + curTo.getCurSymbol() + " y " + curSchema.getCurSymbol());
							dividerate2 = Env.ONE;
						}

						rate = dividerate.divide(dividerate2, 3, RoundingMode.HALF_UP);

						productAmt = (productAmt.multiply(rate)).setScale(2, RoundingMode.HALF_UP);

					} else productAmt = hdr.getAmtOriginal();	

					if(productAmt.compareTo(new BigDecimal(0)) > 0){

						BigDecimal valoremPercentage = (BigDecimal)this.get_Value("ValoremPercentage");

						if(valoremPercentage==null) valoremPercentage = Env.ZERO;

						BigDecimal mercaderiaValorem = (valoremPercentage.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(productAmt).setScale(2, RoundingMode.HALF_UP));

						interPercentage = (BigDecimal)this.get_Value("InterPercentage");
						nationalPercentage = (BigDecimal)this.get_Value("NationalPercentage");

						if(interPercentage==null) interPercentage = Env.ZERO;
						if(nationalPercentage==null || nationalPercentage.compareTo(Env.ZERO)==0) nationalPercentage = Env.ONEHUNDRED;					

						internationalAmt = (interPercentage.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(mercaderiaValorem).setScale(2, RoundingMode.HALF_UP));
						nationalAmt = (nationalPercentage.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(mercaderiaValorem).setScale(2, RoundingMode.HALF_UP));

						this.set_Value("InternationalAmt", internationalAmt);
						this.set_Value("NationalAmt", nationalAmt);
						this.setPriceEntered(nationalAmt.add(internationalAmt));
						this.setPriceActual(nationalAmt.add(internationalAmt));
						this.setLineNetAmt(nationalAmt.add(internationalAmt));
						this.setTaxAmt();

					}		 
				} else {

					productAmt = this.getPriceEntered();

					if(productAmt.compareTo(new BigDecimal(0)) > 0){				

						if((this.get_Value("InternationalAmt")) == null || ((BigDecimal)this.get_Value("InternationalAmt")).compareTo(Env.ZERO)==0){
							internationalAmt = ((BigDecimal)this.get_Value("InterPercentage")).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(productAmt).setScale(2, RoundingMode.HALF_UP);
							this.set_Value("InternationalAmt", internationalAmt);
						}

						if((this.get_Value("NationalAmt")) == null || ((BigDecimal)this.get_Value("NationalAmt")).compareTo(Env.ZERO)==0){
							nationalAmt = ((BigDecimal)this.get_Value("NationalPercentage")).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP).multiply(productAmt).setScale(2, RoundingMode.HALF_UP);
							this.set_Value("NationalAmt", nationalAmt);
						}

						this.setLineNetAmt(this.getPriceEntered());
						this.setTaxAmt();

					}					
				}
			}else{
				if(newRecord){	
					/*
				MProduct valorem = MProduct.forValue(this.getCtx(), "servicios", this.get_TrxName());
				this.setM_Product_ID(valorem.get_ID());

				this.set_Value("InterPercentage",null);
				this.set_Value("NationalPercentage",null);
				this.set_Value("InternationalAmt",null);
				this.set_Value("NationalAmt",null);
				this.set_Value("ValoremPercentage",null);
				this.set_Value("PriceEntered",null);
					 */

				}else{
					if(this.get_ValueAsBoolean("AplicaValorem")){
						throw new AdempiereException("Los Servicios no aplican AD_VALOREM");
					}
				}				

			}	

			this.setPriceList(this.getPriceEntered());
			this.setPriceLimit(this.getPriceEntered());
			this.setQtyEntered(Env.ONE);
			this.setQtyInvoiced(Env.ONE);		
		}
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue #1623
	 * @author Nicolas Sarlabos - 21/02/2014
	 * Metodo que realiza los calculos en las lineas de la factura proforma.
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	private MInvoiceLine forThisTripIDAndProductIDValorem(MInvoice inv, MTRTrip trip){
		
		MInvoiceLine value = null;
		
		MProduct valorem = MProduct.forValueAllClients(this.getCtx(), "valorem", this.get_TrxName());
				
		String sql = "select line.c_invoiceline_id" +
		             " from c_invoiceline line" +
				     " inner join c_invoice hdr on line.c_invoice_id = hdr.c_invoice_id" +
		             " where line.c_invoice_id = " + inv.get_ID() +
		             " and hdr.uy_tr_trip_id = " + trip.get_ID() +
		             " and line.m_product_id = " + valorem.get_ID();
		
		int ID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(ID > 0) value = new MInvoiceLine(getCtx(),ID,get_TrxName());
		
		return value;				
	}
	
	
	/*private MTRTripProduct forThisTripIDAndProductIDFlete(MInvoice inv, MTRTrip trip){
		
		MProduct flete = MProduct.forValue(this.getCtx(), "flete", this.get_TrxName());
		
		String whereClause = X_UY_TR_TripProduct.COLUMNNAME_UY_TR_Trip_ID + " = " + trip.get_ID() + 
						" and " + X_UY_TR_TripProduct.COLUMNNAME_M_Product_ID + " = " + flete.get_ID();
		
		return new Query(this.getCtx(), I_UY_TR_TripProduct.Table_Name, whereClause, this.get_TrxName()).first();				
	}*/

	/**
	 * Check zero prices against the base price list. OpenUP FL 11/03/2011, issue #480
	 * @return true if success, false otherwise
	 * 
	 * @author OpenUP FL
	 */
	private boolean checkZeroPrices() {
		
		
		// If an error hapend in this method, it should return true to avoid block. Defensive
		try	{
		
			// First check if the base price list is requiered, cheking if any price is zero
			if (m_IsSOTrx) {	
				// TODO: m_IsSOTrx should be get from the parent, now this is set by setInvoice
				//OpenUp Nicolas Sarlabos issue #873 07/10/2011 
				if ((this.getPriceEntered().compareTo(BigDecimal.ZERO)<=0)||(this.getPriceActual().compareTo(BigDecimal.ZERO)<=0)||(this.getPriceList().compareTo(BigDecimal.ZERO)<=0)) {
					
					// Get the system base price list value
					int basePriceListId = MSysConfig.getIntValue("UY_BASE_PRICE_LIST",1000002,getAD_Client_ID(),getAD_Org_ID());
										
					// Check if the system base price list its defined. Defensive
					if (basePriceListId > 0) {
													
						// Get the product pricing for the base price list
						MProductPricing baseProductPricing=new MProductPricing(this.getM_Product_ID(),m_C_BPartner_ID, getQtyInvoiced(), m_IsSOTrx);		// TODO: m_C_BPartner_ID and m_IsSOTrx should be get from the parent, now this is set by setInvoice 
						baseProductPricing.setM_PriceList_ID(basePriceListId);
		
						//si el precio es <=0 y el precio de la lista base es > 0 retorno false 				
						/*if ((this.getPriceEntered().compareTo(BigDecimal.ZERO)<=0)&&(!(baseProductPricing.getPriceStd().compareTo(BigDecimal.ZERO)<=0))) {
							return(false); 
						}*/
					}
				}
			}
		}
		catch (Exception e) {
			s_log.log(Level.SEVERE, "checkZeroPrices", e);		// Do nothing, just log the problem and continue without errors. Defencive
		}
		return(true);
	}

	/**
	 * Recalculate invoice tax
	 * @param oldTax true if the old C_Tax_ID should be used
	 * @return true if success, false otherwise
	 *
	 * @author teo_sarca [ 1583825 ]
	 */
	private boolean updateInvoiceTax(boolean oldTax) {
		MInvoiceTax tax = MInvoiceTax.get (this, getPrecision(), oldTax, get_TrxName());
		if (tax != null) {
			if (!tax.calculateTaxFromLines())
				return false;
		
			// red1 - solving BUGS #[ 1701331 ] , #[ 1786103 ]
			if (tax.getTaxAmt().signum() != 0) {
				if (!tax.save(get_TrxName()))
					return false;
			}
			else {
				if (!tax.is_new() && !tax.delete(false, get_TrxName()))
					return false;
			}
		}
		return true;
	}

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;

		// OpenUp. Gabriel Vila. 27/05/2011.
		// Los necesito
		MInvoice invhdr = new MInvoice(getCtx(), this.getC_Invoice_ID(), get_TrxName());
		MDocType doc = new MDocType(getCtx(), invhdr.getC_DocTypeTarget_ID(), null);
		// Fin OpenUp.
		
		
		if (!newRecord && is_ValueChanged("C_Tax_ID"))
		{
			//	Recalculate Tax for old Tax
			if (!updateInvoiceTax(true))
				return false;
		}
		
		// OpenUp. Gabriel Vila. 12/10/2012. Issue #49.
		// Gestion de Provision de Gastos.
		if (!invhdr.isSOTrx()){
			if ((doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)) || (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APCredit))){
				if ((newRecord) || ((!newRecord) && (is_ValueChanged("PriceEntered")))){
					if (MSysConfig.getBooleanValue("UY_HANDLE_PROVISION", false, this.getAD_Client_ID())){
						this.setIsProvisioned(false);
						this.setProvisiones(false);
						
						// Actualizo linea provisionada sin un SaveEx ya que como estoy en el AfterSave me queda en loop.
						String isprovisioned = (this.isProvisioned()) ? "Y" : "N";
						DB.executeUpdateEx("UPDATE c_invoiceline set isprovisioned='" + isprovisioned + "'" + " WHERE c_invoiceline_id=" + this.get_ID() , get_TrxName());
					}
				}
			}
			
			// OpenUp. Gabriel Vila. 07/07/2014. Issue #1405.
			// Compra de neumaticos en modulo de transporte
			// Si es linea nueva o me modifican la cantidad
			if (newRecord || (is_ValueChanged(COLUMNNAME_QtyEntered) || is_ValueChanged(COLUMNNAME_M_Product_ID))){
				// Si estoy en una factura proveedor 
				if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){
					// Si estoy en modulo de transporte
					if (MSysConfig.getBooleanValue("UY_IS_MODULO_TRANSPORTE", false, this.getAD_Client_ID())){
						
						// Si me cambian producto o cantidad, elimino detalle anterior de neumaticos
						if (is_ValueChanged(COLUMNNAME_QtyEntered) || is_ValueChanged(COLUMNNAME_M_Product_ID)){
							DB.executeUpdateEx(" delete from uy_tr_invoicetire where c_invoiceline_id =" + this.get_ID(), get_TrxName());
						}
					
						// Instancio producto para ver si es de categoria "Neumaticos"
						MProduct prod = (MProduct)this.getM_Product();
						MProductCategory prodcat = (MProductCategory)prod.getM_Product_Category();
						if ((prodcat != null) && (prodcat.get_ID() > 0)){
							if (prodcat.getValue().equalsIgnoreCase("neumaticos")){
								// Seteo header de factura indicando que tiene al menos un producto de categoria neumaticos
								DB.executeUpdateEx(" update c_invoice set IsPOTire='Y' where c_invoice_id =" + invhdr.get_ID(), get_TrxName());
								
								// Genero tantos registros en detalle de neumaticos para esta linea como cantidad indicada por el usuario
								if (this.getQtyEntered() != null){
									if (this.getQtyEntered().compareTo(Env.ZERO) > 0){
										for (int i = 1; i <= this.getQtyEntered().intValueExact(); i++){
											MTRInvoiceTire invTire = new MTRInvoiceTire(getCtx(),0, get_TrxName());
											invTire.setC_InvoiceLine_ID(this.get_ID());
											invTire.setM_Product_ID(this.getM_Product_ID());
											invTire.setUY_TR_TireMeasure_ID(prod.get_ValueAsInt("UY_TR_TireMeasure_ID"));
											invTire.setUY_TR_TireMark_ID(prod.get_ValueAsInt("UY_TR_TireMark_ID"));
											invTire.setTireModel(prod.get_ValueAsString("TireModel"));
											invTire.setQtyKmNew(prod.get_ValueAsInt("QtyKmNew"));
											invTire.setQtyKmRecauchu(prod.get_ValueAsInt("QtyKmRecauchu"));
											invTire.saveEx();
										}
									}
								}
							}
						}
					}
				}
			}
			// Fin OpenUp. Issue #1405.
		//OpenUp. Nicolas Sarlabos. 13/10/2015. #4849.	
		} else {
			
			if(doc.getValue()!=null){
				
				if(doc.getValue().equalsIgnoreCase("freightinvoice") || doc.getValue().equalsIgnoreCase("customerncflete")
						|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc")) this.setAmtTerritorial(invhdr.get_ID());			
				
			}	
			
		}
		//Fin #4849.
		// Fin OpenUp.
		
		//OpenUp. Nicolas Sarlabos. 17/12/2012. SOLUCION TEMPORAL. 
		// Debido a un error con el generador de entrega/factura, se eliminan las lineas que no tienen producto ni cargo
		if(this.getM_Product_ID()<=0 && this.getC_Charge_ID() <=0) DB.executeUpdateEx("DELETE FROM c_invoiceline WHERE c_invoiceline_id=" + this.get_ID() , get_TrxName());
		//Fin OpenUp.

		//OpenUp SBT 26/02/2016 Issue #5516 
		if(!invhdr.isSOTrx() && doc.getValue().equalsIgnoreCase("vendorncdesc")){
			this.getParent().updateTotalInvoices(false);
			this.getParent().updateAllocDirect(true);
		}
				// Fin OpenUp.
		
		// OpenUp. Gabriel Vila. 27/05/2011. Issue # (ver generacion e impresion de remitos)
		// No se deben recalcular los precios e importes en documentos de remito.
		// Puse el IF.
		if ( (doc.getDocSubTypeSO() == null)  
				|| ((doc.getDocSubTypeSO() != null) && (!doc.getDocSubTypeSO().equalsIgnoreCase("RR"))) )  {
			
			// Openup. Gabriel Vila. 11/11/2014. Issue #3205
			// Para factura de flete no actualizo totales del cabezal y actualizo el monto afectaciones
			if ((doc.getValue() != null) && (doc.getValue().equalsIgnoreCase("inv_valeflete"))){
				this.updateHeaderInvValeFlete();
				return true;
			}
			
			return updateHeaderTax();	
			
		}
		
		return true;
		
	}	//	afterSave

	
	/***
	 * Actualiza monto afectaciones del cabezal de documento de factura de vale flete.
	 * OpenUp Ltda. Issue #3205 
	 * @author Gabriel Vila - 18/11/2014
	 * @see
	 */
	private void updateHeaderInvValeFlete() {

		try {
			
			String sql = "UPDATE C_Invoice i"
					+ " SET AmtAux="
					+ "((SELECT COALESCE(SUM(round(l.amtallocated / coalesce(l.currencyrate,1),2)),0) FROM uy_invoice_flete l WHERE i.C_Invoice_ID=l.C_Invoice_ID) + "
					+ " (SELECT COALESCE(SUM(round(l.amtallocated / coalesce(l.currencyrate,1),2)),0) FROM uy_order_flete l WHERE i.C_Invoice_ID=l.C_Invoice_ID) + "
					+ " (SELECT COALESCE(SUM(round(il.linenetamt / coalesce(il.currencyrate,1),2)),0) FROM c_invoiceline il WHERE i.C_Invoice_ID=il.C_Invoice_ID)) "
					+ " WHERE C_Invoice_ID=?";
			
			DB.executeUpdateEx(sql, new Object[]{this.getC_Invoice_ID()}, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/**
	 * 	After Delete
	 *	@param success success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;

		// reset shipment line invoiced flag
		if ( getM_InOutLine_ID() > 0 )
		{
			MInOutLine sLine = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName());
			sLine.setIsInvoiced(false);
			sLine.saveEx();
		}

		// OpenUp. Gabriel Vila. 12/10/2012. Issue #49.
		// Gestion de Provision de Gastos.
		if (MSysConfig.getBooleanValue("UY_HANDLE_PROVISION", false, this.getAD_Client_ID())){
			this.setProvisiones(true);
		}
		// Fin OpenUp.
		
		// OpenUp. Gabriel Vila. 18/11/2014. Issue #3025
		// Si es factura de vale flete actualizo monto afectar del cabezal y salgo
		MDocType doc = (MDocType)this.getParent().getC_DocTypeTarget();
		if (doc != null){
			if (doc.getValue() != null){
				if (doc.getValue().equalsIgnoreCase("inv_valeflete")){
					this.updateHeaderInvValeFlete();
					return true;
					
				} else if(doc.getValue().equalsIgnoreCase("freightinvoice") || doc.getValue().equalsIgnoreCase("customerncflete")
						|| doc.getValue().equalsIgnoreCase("et-ticket") || doc.getValue().equalsIgnoreCase("et-nc")) this.setAmtTerritorial(this.getC_Invoice_ID()); //OpenUp. Nicolas Sarlabos. 13/10/2015. #4849.		
			}
		}

		// Fin OpenUp. Issue #3025
		
		if(doc.getValue().equalsIgnoreCase("vendorncdesc") && !this.getParent().isSOTrx()){
			this.getParent().updateTotalInvoices(false);
			this.getParent().updateAllocDirect(true);
		}
		
		
		return updateHeaderTax();
	}	//	afterDelete

	/**
	 *	Update Tax & Header
	 *	@return true if header updated with tax
	 */
	private boolean updateHeaderTax()
	{
		// Update header only if the document is not processed - teo_sarca BF [ 2317305 ]
		if (isProcessed() && !is_ValueChanged(COLUMNNAME_Processed))
			return true;
		
		//	Recalculate Tax for this Tax
		if (!updateInvoiceTax(false))
			return false;	
		
		// OpenUp. Gabriel Vila. 29/10/2015. Issue #4998
		// Si tengo system configurator que determina precisión decimal del total para facturas de venta
		if (MSysConfig.getBooleanValue("UY_ARI_ROUND_GRANDTOTAL", false, this.getAD_Client_ID())){
			if (this.getParent().isSOTrx()){
				return this.updateHeaderRounded();	
			}
		}
		// Fin OpenUp. Issue #4998
		
		//	Update Invoice Header
		String sql = "UPDATE C_Invoice i"
			+ " SET TotalLines="
				+ "(SELECT COALESCE(SUM(LineNetAmt),0) FROM C_InvoiceLine il WHERE i.C_Invoice_ID=il.C_Invoice_ID) "
			+ "WHERE C_Invoice_ID=?";
		int no = DB.executeUpdateEx(sql, new Object[]{getC_Invoice_ID()}, get_TrxName());
		if (no != 1)
			log.warning("(1) #" + no);
		
		
		if (isTaxIncluded())
			sql = "UPDATE C_Invoice i "
				+ " SET GrandTotal=TotalLines "
				+ "WHERE C_Invoice_ID=?";		
		else		
			sql = "UPDATE C_Invoice i "
				+ " SET GrandTotal=TotalLines+"
					+ "(SELECT COALESCE(SUM(TaxAmt),0) FROM C_InvoiceTax it WHERE i.C_Invoice_ID=it.C_Invoice_ID) "
					+ "WHERE C_Invoice_ID=?";
		no = DB.executeUpdateEx(sql, new Object[]{getC_Invoice_ID()}, get_TrxName());
		if (no != 1)
			log.warning("(2) #" + no);
		m_parent = null;
		
		
				
		//OpenUp. Nicolas Sarlabos 22/12/2012. Se actualizan los montos cuando se utiliza descuento al pie de factura
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){

			MInvoice hdr = new MInvoice(getCtx(),this.getC_Invoice_ID(),get_TrxName());

			if(hdr.isSOTrx()){

				if(hdr.getDiscount().compareTo(Env.ZERO)>0){

					int StdPrecision = getPrecision();
					BigDecimal discount = hdr.getDiscount().divide(Env.ONEHUNDRED);
					discount = (hdr.getTotalLines().multiply(discount)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					//aplico descuento global al total de lineas y actualizo
					BigDecimal totalLines = (hdr.getTotalLines().subtract(discount)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					//DB.executeUpdateEx("UPDATE c_invoice set totallines=" + totalLines + " WHERE c_invoice_id=" + hdr.get_ID() , get_TrxName());
					//obtengo nuevo importe de impuesto, aplico y actualizo el TOTAL
					MTax tax = MTax.get (getCtx(), getC_Tax_ID());
					BigDecimal TaxAmt = (tax.calculateTax(totalLines, isTaxIncluded(), getPrecision())).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					BigDecimal grandTotal = (totalLines.add(TaxAmt)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					DB.executeUpdateEx("UPDATE c_invoice set grandtotal=" + grandTotal + " WHERE c_invoice_id=" + hdr.get_ID() , get_TrxName());
					//actualizo valor del impuesto
					DB.executeUpdateEx("UPDATE c_invoicetax set taxamt=" + TaxAmt + " ,taxbaseamt=" + totalLines + " WHERE c_invoice_id=" + hdr.get_ID() , get_TrxName());

				}

			}
		}
		//Fin OpenUp.

		return no == 1;
	}	//	updateHeaderTax

	/***
	 * Actualiza total de factura redondeado sin decimales.
	 * OpenUp Ltda. Issue #4998
	 * @author gabriel - Oct 29, 2015
	 * @return
	 */
	private boolean updateHeaderRounded() {

		try {

			BigDecimal lineNetAmt = DB.getSQLValueBDEx(get_TrxName(), "SELECT COALESCE(SUM(LineNetAmt),0) FROM C_InvoiceLine WHERE C_Invoice_ID=" + this.getC_Invoice_ID());
			BigDecimal taxAmt = DB.getSQLValueBDEx(get_TrxName(), "SELECT COALESCE(SUM(TaxAmt),0) FROM C_InvoiceTax WHERE C_Invoice_ID=" + this.getC_Invoice_ID());
			
			// Redondeo total
			BigDecimal grandTotal = lineNetAmt.setScale(0, RoundingMode.HALF_UP);
			
			//	Update Invoice Header
			String sql = " UPDATE C_Invoice i"
				+ " SET TotalLines=" + lineNetAmt
				+ " WHERE C_Invoice_ID=?";
			int no = DB.executeUpdateEx(sql, new Object[]{getC_Invoice_ID()}, get_TrxName());
			if (no != 1)
				log.warning("(1) #" + no);

			if (isTaxIncluded())
				sql = "UPDATE C_Invoice i "
					+ " SET GrandTotal= " + grandTotal
					+ " WHERE C_Invoice_ID=?";		
			else{
				grandTotal =  lineNetAmt.add(taxAmt).setScale(0, RoundingMode.HALF_UP);
				sql = "UPDATE C_Invoice i "
						+ " SET GrandTotal=" + grandTotal
							+ "WHERE C_Invoice_ID=?";
			}
			no = DB.executeUpdateEx(sql, new Object[]{getC_Invoice_ID()}, get_TrxName());
			if (no != 1)
				log.warning("(2) #" + no);
			m_parent = null;
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return true;
	}
	

	/**************************************************************************
	 * 	Allocate Landed Costs
	 *	@return error message or ""
	 */
	public String allocateLandedCosts()
	{
		if (isProcessed())
			return "Processed";
		MLandedCost[] lcs = MLandedCost.getLandedCosts(this);
		if (lcs.length == 0)
			return "";
		String sql = "DELETE C_LandedCostAllocation WHERE C_InvoiceLine_ID=" + getC_InvoiceLine_ID();
		int no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.info("Deleted #" + no);

		int inserted = 0;
		//	*** Single Criteria ***
		if (lcs.length == 1)
		{
			MLandedCost lc = lcs[0];
			if (lc.getM_InOut_ID() != 0 && lc.getM_InOutLine_ID() == 0)
			{
				//	Create List
				ArrayList<MInOutLine> list = new ArrayList<MInOutLine>();
				MInOut ship = new MInOut (getCtx(), lc.getM_InOut_ID(), get_TrxName());
				MInOutLine[] lines = ship.getLines();
				for (int i = 0; i < lines.length; i++)
				{
					if (lines[i].isDescription() || lines[i].getM_Product_ID() == 0)
						continue;
					if (lc.getM_Product_ID() == 0
						|| lc.getM_Product_ID() == lines[i].getM_Product_ID())
						list.add(lines[i]);
				}
				if (list.size() == 0)
					return "No Matching Lines (with Product) in Shipment";
				//	Calculate total & base
				BigDecimal total = Env.ZERO;
				for (int i = 0; i < list.size(); i++)
				{
					MInOutLine iol = (MInOutLine)list.get(i);
					total = total.add(iol.getBase(lc.getLandedCostDistribution()));
				}
				if (total.signum() == 0)
					return "Total of Base values is 0 - " + lc.getLandedCostDistribution();
				//	Create Allocations
				for (int i = 0; i < list.size(); i++)
				{
					MInOutLine iol = (MInOutLine)list.get(i);
					MLandedCostAllocation lca = new MLandedCostAllocation (this, lc.getM_CostElement_ID());
					lca.setM_Product_ID(iol.getM_Product_ID());
					lca.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
					BigDecimal base = iol.getBase(lc.getLandedCostDistribution());
					lca.setBase(base);
					// MZ Goodwill
					// add set Qty from InOutLine
					lca.setQty(iol.getMovementQty());
					// end MZ
					if (base.signum() != 0)
					{
						double result = getLineNetAmt().multiply(base).doubleValue();
						result /= total.doubleValue();
						lca.setAmt(result, getPrecision());
					}
					if (!lca.save())
						return "Cannot save line Allocation = " + lca;
					inserted++;
				}
				log.info("Inserted " + inserted);
				allocateLandedCostRounding();
				return "";
			}
			//	Single Line
			else if (lc.getM_InOutLine_ID() != 0)
			{
				MInOutLine iol = new MInOutLine (getCtx(), lc.getM_InOutLine_ID(), get_TrxName());
				if (iol.isDescription() || iol.getM_Product_ID() == 0)
					return "Invalid Receipt Line - " + iol;
				MLandedCostAllocation lca = new MLandedCostAllocation (this, lc.getM_CostElement_ID());
				lca.setM_Product_ID(iol.getM_Product_ID());
				lca.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
				BigDecimal base = iol.getBase(lc.getLandedCostDistribution()); 
				lca.setBase(base);
				lca.setAmt(getLineNetAmt());
				// MZ Goodwill
				// add set Qty from InOutLine
				lca.setQty(iol.getMovementQty());
				// end MZ
				if (lca.save())
					return "";
				return "Cannot save single line Allocation = " + lc;
			}
			//	Single Product
			else if (lc.getM_Product_ID() != 0)
			{
				MLandedCostAllocation lca = new MLandedCostAllocation (this, lc.getM_CostElement_ID());
				lca.setM_Product_ID(lc.getM_Product_ID());	//	No ASI
				lca.setAmt(getLineNetAmt());
				if (lca.save())
					return "";
				return "Cannot save Product Allocation = " + lc;
			}
			else
				return "No Reference for " + lc;
		}

		//	*** Multiple Criteria ***
		String LandedCostDistribution = lcs[0].getLandedCostDistribution();
		int M_CostElement_ID = lcs[0].getM_CostElement_ID();
		for (int i = 0; i < lcs.length; i++)
		{
			MLandedCost lc = lcs[i];
			if (!LandedCostDistribution.equals(lc.getLandedCostDistribution()))
				return "Multiple Landed Cost Rules must have consistent Landed Cost Distribution";
			if (lc.getM_Product_ID() != 0 && lc.getM_InOut_ID() == 0 && lc.getM_InOutLine_ID() == 0)
				return "Multiple Landed Cost Rules cannot directly allocate to a Product";
			if (M_CostElement_ID != lc.getM_CostElement_ID())
				return "Multiple Landed Cost Rules cannot different Cost Elements";
		}
		//	Create List
		ArrayList<MInOutLine> list = new ArrayList<MInOutLine>();
		for (int ii = 0; ii < lcs.length; ii++)
		{
			MLandedCost lc = lcs[ii];
			if (lc.getM_InOut_ID() != 0 && lc.getM_InOutLine_ID() == 0)		//	entire receipt
			{
				MInOut ship = new MInOut (getCtx(), lc.getM_InOut_ID(), get_TrxName());
				MInOutLine[] lines = ship.getLines();
				for (int i = 0; i < lines.length; i++)
				{
					if (lines[i].isDescription()		//	decription or no product
						|| lines[i].getM_Product_ID() == 0)
						continue;
					if (lc.getM_Product_ID() == 0		//	no restriction or product match
						|| lc.getM_Product_ID() == lines[i].getM_Product_ID())
						list.add(lines[i]);
				}
			}
			else if (lc.getM_InOutLine_ID() != 0)	//	receipt line
			{
				MInOutLine iol = new MInOutLine (getCtx(), lc.getM_InOutLine_ID(), get_TrxName());
				if (!iol.isDescription() && iol.getM_Product_ID() != 0)
					list.add(iol);
			}
		}
		if (list.size() == 0)
			return "No Matching Lines (with Product)";
		//	Calculate total & base
		BigDecimal total = Env.ZERO;
		for (int i = 0; i < list.size(); i++)
		{
			MInOutLine iol = (MInOutLine)list.get(i);
			total = total.add(iol.getBase(LandedCostDistribution));
		}
		if (total.signum() == 0)
			return "Total of Base values is 0 - " + LandedCostDistribution;
		//	Create Allocations
		for (int i = 0; i < list.size(); i++)
		{
			MInOutLine iol = (MInOutLine)list.get(i);
			MLandedCostAllocation lca = new MLandedCostAllocation (this, lcs[0].getM_CostElement_ID());
			lca.setM_Product_ID(iol.getM_Product_ID());
			lca.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
			BigDecimal base = iol.getBase(LandedCostDistribution);
			lca.setBase(base);
			// MZ Goodwill
			// add set Qty from InOutLine
			lca.setQty(iol.getMovementQty());
			// end MZ
			if (base.signum() != 0)
			{
				double result = getLineNetAmt().multiply(base).doubleValue();
				result /= total.doubleValue();
				lca.setAmt(result, getPrecision());
			}
			if (!lca.save())
				return "Cannot save line Allocation = " + lca;
			inserted++;
		}

		log.info("Inserted " + inserted);
		allocateLandedCostRounding();
		return "";
	}	//	allocate Costs

	/**
	 * 	Allocate Landed Cost - Enforce Rounding
	 */
	private void allocateLandedCostRounding()
	{
		MLandedCostAllocation[] allocations = MLandedCostAllocation.getOfInvoiceLine(
			getCtx(), getC_InvoiceLine_ID(), get_TrxName());
		MLandedCostAllocation largestAmtAllocation = null;
		BigDecimal allocationAmt = Env.ZERO;
		for (int i = 0; i < allocations.length; i++)
		{
			MLandedCostAllocation allocation = allocations[i];
			if (largestAmtAllocation == null
				|| allocation.getAmt().compareTo(largestAmtAllocation.getAmt()) > 0)
				largestAmtAllocation = allocation;
			allocationAmt = allocationAmt.add(allocation.getAmt());
		}
		BigDecimal difference = getLineNetAmt().subtract(allocationAmt);
		if (difference.signum() != 0)
		{
			largestAmtAllocation.setAmt(largestAmtAllocation.getAmt().add(difference));
			largestAmtAllocation.save();
			log.config("Difference=" + difference
				+ ", C_LandedCostAllocation_ID=" + largestAmtAllocation.getC_LandedCostAllocation_ID()
				+ ", Amt" + largestAmtAllocation.getAmt());
		}
	}	//	allocateLandedCostRounding

	// MZ Goodwill
	/**
	 * 	Get LandedCost of InvoiceLine
	 * 	@param whereClause starting with AND
	 * 	@return landedCost
	 */
	public MLandedCost[] getLandedCost (String whereClause)
	{
		ArrayList<MLandedCost> list = new ArrayList<MLandedCost>();
		String sql = "SELECT * FROM C_LandedCost WHERE C_InvoiceLine_ID=? ";
		if (whereClause != null)
			sql += whereClause;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_InvoiceLine_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MLandedCost lc = new MLandedCost(getCtx(), rs, get_TrxName());
				list.add(lc);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "getLandedCost", e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close ();
			}
			catch (Exception e)
			{}
			pstmt = null;
		}

		//
		MLandedCost[] landedCost = new MLandedCost[list.size()];
		list.toArray(landedCost);
		return landedCost;
	}	//	getLandedCost

	/**
	 * 	Copy LandedCost From other InvoiceLine.
	 *	@param otherInvoiceLine invoiceline
	 *	@return number of lines copied
	 */
	public int copyLandedCostFrom (MInvoiceLine otherInvoiceLine)
	{
		if (otherInvoiceLine == null)
			return 0;
		MLandedCost[] fromLandedCosts = otherInvoiceLine.getLandedCost(null);
		int count = 0;
		for (int i = 0; i < fromLandedCosts.length; i++)
		{
			MLandedCost landedCost = new MLandedCost (getCtx(), 0, get_TrxName());
			MLandedCost fromLandedCost = fromLandedCosts[i];
			PO.copyValues (fromLandedCost, landedCost, fromLandedCost.getAD_Client_ID(), fromLandedCost.getAD_Org_ID());
			landedCost.setC_InvoiceLine_ID(getC_InvoiceLine_ID());
			landedCost.set_ValueNoCheck ("C_LandedCost_ID", I_ZERO);	// new
			if (landedCost.save(get_TrxName()))
				count++;
		}
		if (fromLandedCosts.length != count)
			log.log(Level.SEVERE, "LandedCost difference - From=" + fromLandedCosts.length + " <> Saved=" + count);
		return count;
	}	//	copyLinesFrom
	// end MZ

	/**
	 * @param rmaline
	 */
	public void setRMALine(MRMALine rmaLine)
	{
		// Check if this invoice is CreditMemo - teo_sarca [ 2804142 ]
		if (!getParent().isCreditMemo())
		{
			throw new AdempiereException("InvoiceNotCreditMemo");
		}
		setAD_Org_ID(rmaLine.getAD_Org_ID());
        setM_RMALine_ID(rmaLine.getM_RMALine_ID());
        setDescription(rmaLine.getDescription());
        setLine(rmaLine.getLine());
        setC_Charge_ID(rmaLine.getC_Charge_ID());
        setM_Product_ID(rmaLine.getM_Product_ID());
        setC_UOM_ID(rmaLine.getC_UOM_ID());
        setC_Tax_ID(rmaLine.getC_Tax_ID());
        setPrice(rmaLine.getAmt());
        BigDecimal qty = rmaLine.getQty();
        if (rmaLine.getQtyInvoiced() != null)
        	qty = qty.subtract(rmaLine.getQtyInvoiced());
        setQty(qty);
        setLineNetAmt();
        setTaxAmt();
        setLineTotalAmt(rmaLine.getLineNetAmt());
        setC_Project_ID(rmaLine.getC_Project_ID());
        setC_Activity_ID(rmaLine.getC_Activity_ID());
        setC_Campaign_ID(rmaLine.getC_Campaign_ID());
	}

	/**
	 * @return matched qty
	 */
	public BigDecimal getMatchedQty()
	{
		String sql = "SELECT COALESCE(SUM("+MMatchInv.COLUMNNAME_Qty+"),0)"
						+" FROM "+MMatchInv.Table_Name
						+" WHERE "+MMatchInv.COLUMNNAME_C_InvoiceLine_ID+"=?"
							+" AND "+MMatchInv.COLUMNNAME_Processed+"=?";
		return DB.getSQLValueBDEx(get_TrxName(), sql, getC_InvoiceLine_ID(), true);
	}

	/***
	 * Manejo de Provisiones. Si este producto tiene asociado una cuenta provisionable y el proveedor
	 * es recurrente, debo ver si hay provisiones pendientes. Si es asi debo mostrarlas para su 
	 * afectacion. 
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 10/10/2012
	 * @see
	 * @param isDelete
	 */
	private void setProvisiones(boolean isDelete){
		
		try{
			
			// Si se esta queriendo eliminar esta linea (viene del beforeDelete)
			if (isDelete){
				// Elimino (si es que las hay) lineas de provision creadas para este producto
				String action = " DELETE FROM UY_Invoice_Provision " +
							    " WHERE C_Invoice_ID =" + this.getC_Invoice_ID() +
							    " AND M_Product_ID =" + this.getM_Product_ID() +
							    " AND C_Activity_ID_1 =" + this.getC_Activity_ID_1();
				DB.executeUpdateEx(action, get_TrxName());
				return; // salgo
			}
			
			// Si esta linea no tiene producto no hago nada
			if (this.getM_Product_ID() <= 0) return;
			
			// Si el socio de negocio de esta factura no es proveedor recurrente no hago nada
			MBPartner proveedor = (MBPartner) this.getParent().getC_BPartner();
			if (!proveedor.isRecurrent()) return;
			
			// Obtengo cuenta de compra del producto de esta linea, si no tiene no hago nada
			MProduct prod = new MProduct(getCtx(), this.getM_Product_ID(), null);
			X_M_Product_Acct productAcct = prod.getProductAccounting();
			if (productAcct == null) {
				throw new AdempiereException("El producto " + prod.getValue() + " no tiene Contabilidad asignada.");
			}
			if (productAcct.getP_InventoryClearing_Acct() <= 0) {
				throw new AdempiereException("El producto " + prod.getValue() + " no tiene Cuenta de Compra asignada.");
			}

			MAccount combinationProdPO = MAccount.get(getCtx(), productAcct.getP_InventoryClearing_Acct());
			if (combinationProdPO == null) return;
			MElementValue cuentaProdPO = combinationProdPO.getAccount();
			if ((cuentaProdPO == null) || (cuentaProdPO.get_ID() <= 0)) return;
			
			// Si la cuenta no es provisionable salgo
			if (!cuentaProdPO.isProvisionable()) return;

			// Obtengo provisiones PENDIENTES para este proveedor, articulo
			List<MProvisionLine> provlines = MProvision.getOpenAmtLines(getCtx(), this.getAD_Client_ID(), this.getAD_Org_ID(), 
											 proveedor.get_ID(), this.getM_Product_ID(), get_TrxName()); 
			
			BigDecimal montoAfectacion = this.getLineNetAmt();
			boolean generaLineaProvision = false;

			// Marco linea de factura como provisionada
			if (provlines.size() > 0) 
				this.setIsProvisioned(true);
			
			// Si hay provisiones las despliego
			for (MProvisionLine line: provlines){
				
				// Solo para lineas de provision del mismo centro de costo 1 que esta linea de factura
				if (line.getC_Activity_ID_1() == this.getC_Activity_ID_1()){
					// Si ya tengo esta linea de provision en la grilla, le actualizo la cantidad a afectar
					// Si no esta, la doy de alta.
					MInvoiceProvision invprov = MInvoiceProvision.forProvisionLine(getCtx(), line.get_ID(), this.getC_Invoice_ID(), get_TrxName());
					if (invprov != null){
											
						//invprov.setamtallocated(montoAfectacion);
						invprov.setamtallocated(invprov.getamtallocated().add(montoAfectacion));

					}
					else{
						invprov = new MInvoiceProvision(getCtx(), 0, get_TrxName());
						invprov.setC_Invoice_ID(this.getC_Invoice_ID());
						invprov.setC_InvoiceLine_ID(this.get_ID());
						invprov.setUY_Provision_ID(line.getUY_Provision_ID());
						invprov.setUY_ProvisionLine_ID(line.get_ID());
						invprov.setC_ElementValue_ID(line.getC_ElementValue_ID());
						invprov.setM_Product_ID(line.getM_Product_ID());
						invprov.setC_Activity_ID_1(line.getC_Activity_ID_1());
						invprov.setC_Currency_ID(line.getC_Currency_ID());
						invprov.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(), line.getParent().getDateAcct(), this.getAD_Org_ID()));
						invprov.setAmt(line.getAmtSourceAverage());
						invprov.setamtopen(line.getAmtOpen(get_TrxName()));
						invprov.setamtallocated(montoAfectacion);
					}
					
					invprov.saveEx();
					montoAfectacion = Env.ZERO;
					generaLineaProvision = true;
				}
			}
			
			if (!generaLineaProvision) this.setIsProvisioned(false);
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	
	/***
	 * Obtiene y retorna lines de provision asociadas a esta linea de factura.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 16/10/2012
	 * @see
	 * @return
	 */
	public List<MInvoiceProvision> getInvoiceLineProvisions(){
		
		String whereClause = X_UY_Invoice_Provision.COLUMNNAME_C_InvoiceLine_ID + "=" + this.get_ID();
		
		List<MInvoiceProvision> lines = new Query(getCtx(), I_UY_Invoice_Provision.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}

	/***
	 * Obtiene suma de montos afectados de las lineas de provision asociadas a esta linea de comprobante.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 30/10/2012
	 * @see
	 * @return
	 */
	public BigDecimal getLineProvisionsSumAllocated(){
		
		BigDecimal sumAllocated = Env.ZERO;
		
		List<MInvoiceProvision> lines = this.getInvoiceLineProvisions();
		for (MInvoiceProvision line: lines){
			if (line.getC_Activity_ID_1() == this.getC_Activity_ID_1()){
				sumAllocated = sumAllocated.add(line.getamtallocated());	
			}
		}
		
		return sumAllocated;
	}
	

	/***
	 * OpenUp. Guillermo Brust. 24/05/2013. 
	 * Obtiene y retorno la primer linea creada de determinada factura.
	 * 
	 */
	public static MInvoiceLine getInvoiceLineForInovice(Properties ctx, int invoice_id){
		
		String whereClause = X_C_InvoiceLine.COLUMNNAME_C_Invoice_ID + "=" + invoice_id;
		
		MInvoiceLine line = new Query(ctx, I_C_InvoiceLine.Table_Name, whereClause, null)
		.first();
		
		return line;
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 13/10/2015. #4849.
	 * Obtiene y setea en cabezal de factura de flete, los totales de importe nacional e internacional.
	 * 
	 */
	public void setAmtTerritorial(int headerID){
		
		String sql = "";
		BigDecimal amt = Env.ZERO;
		
		//seteo total importe nacional
		sql = "select coalesce(sum(nationalamt),0)" +
		      " from c_invoiceline" +
			  " where c_invoice_id = " + this.getC_Invoice_ID();
		
		amt = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		DB.executeUpdateEx("update c_invoice set amt1 = " + amt + " where c_invoice_id = " + headerID, get_TrxName());

		//seteo total importe internacional
		sql = "select coalesce(sum(internationalamt),0)" +
				" from c_invoiceline" +
				" where c_invoice_id = " + this.getC_Invoice_ID();

		amt = DB.getSQLValueBDEx(get_TrxName(), sql);

		DB.executeUpdateEx("update c_invoice set amt2 = " + amt + " where c_invoice_id = " + headerID, get_TrxName());			
		
	}
			
}	//	MInvoiceLine
