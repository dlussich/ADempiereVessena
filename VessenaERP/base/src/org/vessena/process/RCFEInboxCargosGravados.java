/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Feb 10, 2016
*/
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

/**
 * org.openup.process - RCFEInboxCargosGravados
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Feb 10, 2016
*/
public class RCFEInboxCargosGravados extends SvrProcess {

	private static final String TABLA_MOLDE = "UY_Molde_CFE_Cargos";
	
	private Timestamp dateFrom = null;
	private Timestamp dateTo = null;
	private int mProductID = 0;
	private int cBPartnerID = 0;
	
	/***
	 * Constructor.
	*/

	public RCFEInboxCargosGravados() {
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
				
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter() != null) this.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				
				if (name.equalsIgnoreCase("M_Product_ID")){
					if (para[i].getParameter() != null) this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
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
		
		return "OK";

	}

	
	/***
	 * Obtiene datos iniciales y los inserta en tabla molde.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Feb 5, 2016
	 */
	private void getData() {
		
		String insert ="", sql = "", whereClause = "";

		try
		{
		
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, "
					+ " c_period_id, m_product_id, c_bpartner_id, c_currency_id, qtyentered, "
					+ " SubTotalAmtMT, TaxAmtMT, TotalAmtMT, SubTotalAmtMN, TaxAmtMN, TotalAmtMN)";

			dateFrom = TimeUtil.trunc(this.dateFrom, TimeUtil.TRUNC_DAY);
			dateTo = TimeUtil.trunc(this.dateTo, TimeUtil.TRUNC_DAY);

			if (this.cBPartnerID > 0){
				whereClause = " and inv.c_bpartner_id =" + this.cBPartnerID; 
			}

			if (this.mProductID > 0){
				whereClause = " and l.m_product_id =" + this.mProductID; 
			}
			
			sql = " select inv.ad_client_id, inv.ad_org_id, " + this.getAD_User_ID() + ", inv.c_period_id, "
					+ " inv.m_product_id, inv.c_bpartner_id, inv.c_currency_id, "
					+ " sum(inv.qtyinvoiced) as qtyinvoiced,  sum(inv.linenetamt) as subtotalmt, sum(inv.taxamt) as taxamtmt, " 
					+ " sum(inv.linetotalamt) as totalmt,  sum(inv.subtotalmn) as subtotalmn, sum(inv.taxamtmn) as taxamtmn, "
					+ " sum(inv.totalmn) as totalmn "
					+ " from vuy_cfe_invoice inv   "
					+ " where inv.dateinvoiced between '" +  this.dateFrom +  "' and '" + this.dateTo + "' "
					+ whereClause
					+ " group by inv.ad_client_id, inv.ad_org_id, c_period_id, inv.m_product_id, inv.c_bpartner_id, inv.c_currency_id "
					+ " order by c_period_id, m_product_id, c_bpartner_id ";
			
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
