/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/02/2013
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * org.openup.process - RComFactSumProd
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 20/02/2013
 * @see
 */
public class RComFactSumProd extends SvrProcess {

	private String idReporte = ""; 
	private static final String TABLA_MOLDE = "UY_Molde_FactSumProd";

	private int mProductID = 0;
	private int cBPartnerID = 0;
	private int adUserID = 0;
	private int adClientID = 0;
	private Timestamp fecDesde = null;
	private Timestamp fecHasta = null;
	
	
	/**
	 * Constructor.
	 */
	public RComFactSumProd() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 20/02/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("DateInvoiced")){
					this.fecDesde = (Timestamp)para[i].getParameter();
					this.fecHasta = (Timestamp)para[i].getParameter_To();
				}
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if(para[i].getParameter()!=null){
						this.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Product_ID")){
					if(para[i].getParameter()!=null){
						this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
			}
		}

		// Empresa - Usuario
		this.adClientID = getAD_Client_ID();
		this.adUserID = getAD_User_ID();
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 20/02/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		this.deleteData();
		this.loadData();
		
		return "OK";
	}

	private void loadData() {

		String insert = "", sql = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, idreporte, " +
					" fecreporte, m_product_id, name, c_uom_id, qtyentered, qtyinvoiced, qtyquintales, linenetamt) ";
			
			// Filtros seleccionados por el usuario
			String filtros = " WHERE fp.ad_client_id =" + this.adClientID;
			if (this.fecDesde != null){
				filtros += " and fp.dateinvoiced >='" + this.fecDesde + "' ";
			}
			if (this.fecHasta != null){
				filtros += " and fp.dateinvoiced <='" + this.fecHasta + "' ";
			}
			
			if (this.mProductID > 0) filtros += " AND fp.m_product_id =" + this.mProductID;
			if (this.cBPartnerID > 0) filtros += " AND fp.c_bpartner_id =" + this.cBPartnerID;
			
			sql = " SELECT fp.ad_client_id, 0," + this.adUserID + ",'" + this.idReporte + "', current_date," +
				  " fp.m_product_id, prod.name, fp.c_uom_id, coalesce(sum(qtyentered),0) as cantuv, coalesce(sum(qtyinvoiced),0) as cantinv, " +
				  " coalesce(sum(quintales),0) as cantqui, coalesce(sum(linenetamtfinal),0) as neto " + 
				  " FROM vuy_fact_prodquintales fp " +
				  " INNER JOIN m_product prod on fp.m_product_id = prod.m_product_id " + filtros +
				  " GROUP BY fp.ad_client_id, fp.m_product_id, prod.name, fp.c_uom_id " +
				  " ORDER BY fp.m_product_id ";
			
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Elimina informacion anterior en la tabla del reporte para este usuario.
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 20/02/2013
	 * @see
	 */
	private void deleteData() {

		try{
			String action = " DELETE FROM " + TABLA_MOLDE + 
							" WHERE ad_user_id =" + this.adUserID;
			
			DB.executeUpdateEx(action, null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		
	}

}
