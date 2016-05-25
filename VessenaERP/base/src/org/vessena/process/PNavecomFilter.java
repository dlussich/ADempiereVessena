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
package org.openup.process;

import java.sql.PreparedStatement;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MNavecomFilter;

/**
 *  Populate uy_navecom_invoices wich is the base of all navecom tabs 
 *
 *	@author OpenUP FL 09/03/2011 issue #368
 */
public class PNavecomFilter extends SvrProcess {
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare() {

	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception {
	
		// Get the current id
		int UY_Navecom_Filter_ID = getRecord_ID();

		log.info("Populating navecom with the filter id " + UY_Navecom_Filter_ID);

		// The id cannot be 0  
		if (UY_Navecom_Filter_ID==0) {
			throw new IllegalArgumentException("No se puede generar, el id del filtro es 0");										// TODO: translate
		}
		
		// Get the object
		MNavecomFilter navecomFilter= new MNavecomFilter(getCtx(),UY_Navecom_Filter_ID,null);
		
		if (navecomFilter.get_ID() <= 0) {
			throw new IllegalArgumentException("No se puede generar, lo se pudo leer el filtro con el id "+ UY_Navecom_Filter_ID); 	// TODO: transalte
		}

		String SQL= "DELETE FROM uy_navecom_invoices WHERE uy_navecom_invoices.uy_navecom_filter_id="+UY_Navecom_Filter_ID+";" +
					"INSERT INTO uy_navecom_invoices(uy_navecom_filter_id, ad_client_id, ad_org_id, ad_client, ad_org, isactive, created, updated, createdby, updatedby, c_invoice_id, documentno, dateinvoiced, m_product_id, codprod, nomprod, m_product_category_id, m_product_category,  uy_familia_id, uy_familia, uy_subfamilia_id, uy_subfamilia, uy_linea_negocio_id, uy_linea_negocio, c_bpartner_id, codbp, nombp, salesrep_id, name ,uy_canalventas_id, uy_canalventas,  c_currency_id, moneda, linenetamt, qtyinvoiced, linenetamtbase, linecostamt) " + //OpenUp M.R. 14-06-2011 Issue #651 se agregan campos a la tabla para poder cargar datos ya definitivos a exportar
					"SELECT "+UY_Navecom_Filter_ID+" as uy_navecom_filter_id, " +
					"c_invoiceline.ad_client_id, "+
					"c_invoiceline.ad_org_id, "+
					"g.name as compania, "+
					"h.name as organizacion, "+
					"'Y'::bpchar as isactive, "+
					"now() AS created, "+
					"now() AS updated, "+
					"100 AS createdby, "+
					"100 AS updatedby, "+
					"c_invoice.c_invoice_id, "+
					"c_invoice.documentno,  "+
					"c_invoice.dateinvoiced,  "+
			 		"m_product.m_product_id, "+
					"m_product.value as codprod, "+ 
					"m_product.name as nomprod,  "+
					"m_product.m_product_category_id, " +
					"a.name as categoria,  "+
					"m_product.uy_familia_id, " +
					"b.name as familia,  "+
					"m_product.uy_subfamilia_id, " +
					"c.name as subfamilia, "+ 
					"m_product.uy_linea_negocio_id, " +
					"d.name as lineanegocio, "+
					"c_bpartner.c_bpartner_id, "+
					"c_bpartner.value codbp, "+
					"c_bpartner.name as nombp, "+ 
					"c_invoice.salesrep_id,  "+
					"i.name, "+
					"c_bpartner.uy_canalventas_id, " +
					"e.nombre as canalventas,  "+
					"c_invoice.c_currency_id, "+
					"f.description as moneda,  "+
					"c_invoiceline.linenetamt,  "+
					"c_invoiceline.qtyinvoiced,  "+
					"currencybase(c_invoiceline.linenetamt, c_invoice.c_currency_id, c_invoice.dateacct::timestamp with time zone, c_invoice.ad_client_id, c_invoice.ad_org_id) AS linenetamtbase, " +
					"c_invoiceline.linenetamt "+					
					"FROM c_invoiceline "+
					"LEFT JOIN m_product ON m_product.m_product_id = c_invoiceline.m_product_id "+ 
					"LEFT JOIN c_invoice ON c_invoice.c_invoice_id = c_invoiceline.c_invoice_id "+ 
					"LEFT JOIN c_bpartner ON c_bpartner.c_bpartner_id = c_invoice.c_bpartner_id "+
					"LEFT JOIN m_product_category a on m_product.m_product_category_id = a.m_product_category_id "+
					"LEFT JOIN UY_Familia b on m_product.uy_familia_id = b.uy_familia_id "+
					"LEFT JOIN UY_Subfamilia c on m_product.uy_subfamilia_id = c.uy_subfamilia_id "+
					"LEFT JOIN uy_linea_negocio d on m_product.uy_linea_negocio_id = d.uy_linea_negocio_id "+
					"LEFT JOIN uy_canalventas e on c_bpartner.uy_canalventas_id = e.uy_canalventas_id "+
					"LEFT JOIN c_currency f on c_invoice.c_currency_id = f.c_currency_id "+
					"LEFT JOIN ad_client g on c_invoiceline.ad_client_id = g.ad_client_id "+
					"LEFT JOIN ad_org h on c_invoiceline.ad_org_id = h.ad_org_id "+
					"LEFT JOIN ad_user i on c_invoice.salesrep_id = i.ad_user_id " +
					"WHERE c_invoice.issotrx = 'Y'::bpchar "+
					"AND c_invoiceline.isactive = 'Y'::bpchar "+
					"AND (c_invoice.docstatus = 'CO'::bpchar OR c_invoice.docstatus = 'CL'::bpchar) ";

		String whereClause="";
		
		// Filter by date from
		if (navecomFilter.getDateFrom()!=null) {
			whereClause+="AND c_invoice.dateinvoiced>='"+navecomFilter.getDateFrom()+"' ";
		}
		
		// Filter by date to
		if (navecomFilter.getDateTo()!=null) {
			whereClause+="AND c_invoice.dateinvoiced<='"+navecomFilter.getDateTo()+"' ";
		}
		// Filter by product family
		if (navecomFilter.getUY_Familia_ID()!=0) {
			whereClause+="AND m_product.uy_familia_id="+navecomFilter.getUY_Familia_ID()+" ";
		}
		// Filter by product subfamily
		if (navecomFilter.getUY_SubFamilia_ID()!=0) {
			whereClause+="AND m_product.uy_subfamilia_id="+navecomFilter.getUY_SubFamilia_ID()+" ";
		}
		// Filter by product category
		if (navecomFilter.getM_Product_Category_ID()!=0) {
			whereClause+="AND m_product.m_product_category_id="+navecomFilter.getM_Product_Category_ID()+" ";
		}
		// Filter by product
		if (navecomFilter.getM_Product_ID()!=0) {
			whereClause+="AND c_invoiceline.m_product_id="+navecomFilter.getM_Product_ID()+" ";
		}
		// Filter by sales representative
		if (navecomFilter.getSalesRep_ID()!=0) {
			whereClause+="AND c_invoice.salesrep_id="+navecomFilter.getSalesRep_ID()+" ";
		}
		
		PreparedStatement pstmt=null;
		
		try {
			pstmt=DB.prepareStatement(SQL+whereClause+";", null);
			
			// Just run de query, 
			pstmt.executeUpdate();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			pstmt= null;
		}
		

		return("Generado");			// TODO: translate
	}	//	doIt

}
