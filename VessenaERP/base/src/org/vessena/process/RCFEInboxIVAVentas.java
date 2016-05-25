/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Feb 5, 2016
*/
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConversionRate;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.process - RCFEInboxIVAVentas
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Feb 5, 2016
*/
public class RCFEInboxIVAVentas extends SvrProcess {

	private static final String TABLA_MOLDE = "UY_Molde_CFE_IVA";
	
	private Timestamp dateFrom = null;
	private Timestamp dateTo = null;
	
	/***
	 * Constructor.
	*/

	public RCFEInboxIVAVentas() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("DateTrx")){
					this.dateFrom = (Timestamp)para[i].getParameter();
					this.dateTo = (Timestamp)para[i].getParameter_To();
				}
			}
		}


	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		this.deleteData();
		
		this.getData();
		
		this.updateData();
		
		return "OK";

	}

	/***
	 * Actualiza información restante en la tabla molde.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Feb 5, 2016
	 */
	private void updateData() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
		
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =?" +
				  " ORDER BY dateinvoiced";
			
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.getAD_User_ID());
			
			rs = pstmt.executeQuery ();
			
			String action = ""; 
			
			// Corte de control por socio de negocio
			while (rs.next()){

				Timestamp dateInvoice = TimeUtil.trunc(rs.getTimestamp("dateinvoiced"), TimeUtil.TRUNC_DAY);
				
				MPeriod period = MPeriod.get(getCtx(), dateInvoice, 0);
						
				BigDecimal amtTaxMT = DB.getSQLValueBDEx(null, " select coalesce(sum(taxamt),0) as amt "
						+ " from c_invoicetax "
						+ " where c_invoice_id =" + rs.getInt("c_invoice_id"));
				
				BigDecimal amtTaxMN = amtTaxMT;
				if (rs.getInt("c_currency_id") != 142){
					BigDecimal divideRate = MConversionRate.getRate(rs.getInt("c_currency_id"), 142, dateInvoice, 0, this.getAD_Client_ID(), 0);
					if ((divideRate == null) || (divideRate.compareTo(Env.ZERO) == 0)){
						divideRate = Env.ONE;
					}
					amtTaxMN = amtTaxMT.multiply(divideRate).setScale(5, RoundingMode.HALF_UP);
					//amtTaxMN = amtTaxMT.divide(divideRate, 5, RoundingMode.HALF_UP);
				}
				
				// Si es nota de credito doy vuelta el signo
				String sql2 = " select doc.docbasetype "
						+ " from c_doctype doc "
						+ " inner join c_invoice i on doc.c_doctype_id = i.c_doctypetarget_id "
						+ " where i.c_invoice_id =" + rs.getInt("c_invoice_id"); 
				String docBaseType = DB.getSQLValueStringEx(null, sql2);
				if (docBaseType != null){
					if (docBaseType.equalsIgnoreCase("ARC")){
						amtTaxMN = amtTaxMN.negate();
						amtTaxMT = amtTaxMT.negate();
					}
				}
			
				action = " UPDATE " + TABLA_MOLDE  
		 		 		+ " SET c_period_id =" + period.get_ID() + ", "
		 		 		+ " taxamtmt =" + amtTaxMT + ", "
		 		 		+ " taxamtmn =" + amtTaxMN
		 		 		+ " WHERE c_invoice_id =" + rs.getInt("c_invoice_id");
				DB.executeUpdateEx(action, null);
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
	 * Obtiene datos iniciales y los inserta en tabla molde.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Feb 5, 2016
	 */
	private void getData() {
		
		String insert ="", sql = "";

		try
		{
		
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, c_invoice_id, "
					+ "dateinvoiced, c_currency_id, nrocfe)";

			dateFrom = TimeUtil.trunc(this.dateFrom, TimeUtil.TRUNC_DAY);
			dateTo = TimeUtil.trunc(this.dateTo, TimeUtil.TRUNC_DAY);
		
			sql = " select inv.ad_client_id, inv.ad_org_id, " + this.getAD_User_ID() + ", "
				+ " inv.c_invoice_id, inv.dateinvoiced, inv.c_currency_id, inv.documentno "
				+ " from c_invoice inv "
				+ " where inv.issotrx ='Y' "
				+ " and inv.docstatus ='CO' "
				+ " and inv.dateinvoiced between '" +  this.dateFrom +  "' and '" + this.dateTo + "' "; 
			
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Elimina datos anteriores para este usuario en la table molde.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Feb 5, 2016
	 */
	private void deleteData() {
	
		try {
			
			String action = " delete from " + TABLA_MOLDE + " where ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

}
