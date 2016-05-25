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
import java.math.BigDecimal;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp Guillermo Brust. 15/04/2013 ISSUE #697
 * Se modifica el reporte para obtner los datos de los documentos junto con su correlatividad.
 */
public class RInvoiceCorrelativity extends SvrProcess
{
	private static String TABLA_MOLDE = "UY_InvoiceCorrelative";
	
	private int c_DocType_ID;	
	private int documentNoStart;	
	private int documentNoEnd;
	
	private int adClientID = 0;
	private int adUserID = 0;	
	private String idReporte = "";

	
	protected void prepare()
	{		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){					
				if (name.equalsIgnoreCase("C_DocType_ID")){
					if (para[i].getParameter()!=null){
						this.c_DocType_ID = para[i].getParameterAsInt();
					}
				} 				
				if (name.equalsIgnoreCase("DocumentNoStart")){
					if (para[i].getParameter()!=null){
						this.documentNoStart = para[i].getParameterAsInt();
					}
				}
				if (name.equalsIgnoreCase("DocumentNoEnd")){
					if (para[i].getParameter()!=null){
						this.documentNoEnd = para[i].getParameterAsInt();
					}
				}
				if (name.equalsIgnoreCase("AD_User_ID")){
					if (para[i].getParameter()!=null){
						this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					if (para[i].getParameter()!=null){
						this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
			}
		}		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));	
	} 
	
	@Override
	protected String doIt() throws Exception {
		
		//Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
								
		//Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.		
		this.loadMovimientos();	
						
		return "ok";
	}
	
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE AD_User_ID =" + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}	
	
	/* Carga movimientos en la tabla molde. */
	private void loadMovimientos(){
		
		String insert = "", sql = "", whereNros = "";
		
		if(this.documentNoEnd > 0){
			whereNros = " and inv.documentno between '" + this.documentNoStart + "' and '" + this.documentNoEnd + "'";
		}
		
		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE;
								
			sql = " select inv.c_doctype_id, inv.documentno, org.AD_Org_ID, inv.dateinvoiced, cb.C_BPartner_ID, inv.description, cur.C_Currency_ID," +
				  " pt.C_PaymentTerm_ID, inv.totallines, inv.grandtotal, " + this.adClientID + ", " + this.adUserID + ", '" + this.idReporte + "'" +
				  " from c_invoice inv" +
				  " inner join ad_org org on inv.ad_org_id = org.ad_org_id" +
				  " inner join c_bpartner cb on inv.c_bpartner_id = cb.c_bpartner_id" +
				  " inner join c_currency cur on inv.c_currency_id = cur.c_currency_id" +
				  " inner join c_paymentterm pt on inv.c_paymentterm_id = pt.c_paymentterm_id" +
				  " where inv.c_doctype_id = " + this.c_DocType_ID + 
				  " and inv.docstatus = 'CO'" +
				  whereNros;
							
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
			
		}
	}
} 
