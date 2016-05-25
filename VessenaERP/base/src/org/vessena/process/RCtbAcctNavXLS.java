package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author OpenUp. Matias Carbajal. 28/11/2014. Reporte Navegador Contable
 *         Excel. Issue #2978.
 * 
 */
public class RCtbAcctNavXLS extends SvrProcess {

	private int adClientID = 0;
	private int adOrgID = 0;
	
	
	private int cPeriodFromID = 0;
	private int cPeriodToID = 0;
	
	private int C_Activity_ID = 0;
	private int M_Product_ID = 0;
	private int C_ElementValue_ID = 0;
	private int C_BPartner_ID = 0;

	private int adUserID = 0;


	private String TABLA_MOLDE_ACCTNAV_EXCEL = "UY_MOLDE_AcctNav_EXCEL";
	private String TABLA_MOLDE_ACCTNAV_RV = "UY_MOLDE_AcctNav_RV";
	
	public HashMap<Integer, Integer> hashPosicionXPeriodo = new HashMap<Integer, Integer>();
	public HashMap<Integer, BigDecimal> hashTasaCambioXPeriodo = new HashMap<Integer, BigDecimal>();

	/**
	 * Constructor
	 */
	public RCtbAcctNavXLS() {
		// TODO Auto-generated constructor stub
	}

	protected void prepare() {

	
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name!= null && para[i].getParameter()!=null){
				if (name.equalsIgnoreCase("C_Period_ID")){
					this.cPeriodFromID = ((BigDecimal)para[i].getParameter()).intValueExact();
					this.cPeriodToID = ((BigDecimal)para[i].getParameter_To()).intValueExact();
				} else if (name.equals("C_Activity_ID")) {
					C_Activity_ID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equals("M_Product_ID")) {
					M_Product_ID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equals("C_ElementValue_ID")) {
					C_ElementValue_ID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equals("C_BPartner_ID")) {
					C_BPartner_ID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equalsIgnoreCase("AD_User_ID")) {
					adUserID = ((BigDecimal) para[i].getParameter()).intValue();
				} else if (name.equalsIgnoreCase("Ad_Client_ID")) {
					adClientID = ((BigDecimal) para[i].getParameter()).intValueExact();
				}else if (name.equalsIgnoreCase("Ad_Org_ID")) {
					adOrgID = ((BigDecimal) para[i].getParameter()).intValueExact();
				}
			}
		}

	}

	@Override
	protected String doIt() throws Exception {
		
		this.deleteData();
		
		this.setPeriodos();
		
		this.loadData();
		
		this.updateData();
		
		this.deletDataSoloAux();
		
		this.loadDataJournal();
		
		this.updateDataJournal();

		return "OK";
	}



	private void deletDataSoloAux() {
		
		try {

			String action = "";

			action = " DELETE FROM " + TABLA_MOLDE_ACCTNAV_EXCEL
					+ " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);


		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	private void deleteData() {

		try {

			String action = "";

			action = " DELETE FROM " + TABLA_MOLDE_ACCTNAV_EXCEL
					+ " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);

			action = " DELETE FROM " + TABLA_MOLDE_ACCTNAV_RV
					+ " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);


		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	private void loadDataJournal() {


		String insert = "", sql = "";
		String condicionCuentaContable = "";
		String condicionCentroCosto = "";

		
		try {
			
			if(this.C_ElementValue_ID > 0){
				condicionCuentaContable = " AND fa.account_id = " + this.C_ElementValue_ID;
			}
			if (this.C_Activity_ID > 0){
				condicionCentroCosto = " AND fa.c_activity_id_1 = " + this.C_Activity_ID;
			}

			insert = "INSERT INTO "
					+ TABLA_MOLDE_ACCTNAV_EXCEL
					+ " (c_activity_id, c_elementvalue_id, gl_journal_id, c_period_id,  "
					+ " ad_user_id, ad_client_id, ad_org_id, saldomn)";

			
			sql = " SELECT fa.c_activity_id_1, fa.account_id, journal.gl_journal_id, fa.c_period_id,  "
					+ this.adUserID + ", " + this.adClientID + ", " + this.adOrgID + ", "
					+ " (SUM(fa.amtacctcr) - SUM(amtacctdr)) as saldomn "
					+ " FROM fact_acct fa "
					+ " INNER join gl_journal journal ON fa.record_id = journal.gl_journal_id "
					+ " INNER join c_elementvalue ev ON fa.account_id = ev.c_elementvalue_id "
					+ " WHERE fa.c_period_id >="
					+ this.cPeriodFromID
					+ " AND fa.c_period_id <="
					+ this.cPeriodToID
					+ " AND fa.ad_table_id = 224 "
					+ " AND ev.c_element_id IN (SELECT c_element_id FROM c_element WHERE isnaturalaccount='Y' AND ad_client_id ="
					+ this.getAD_Client_ID()
					+ ") "
					+ " AND fa.ad_table_id not in(1000663,1000662,1000664,1000665) " 
					+ " AND ev.accounttype='E' " 
					+ condicionCuentaContable
					+ condicionCentroCosto
					+ " GROUP BY fa.c_activity_id_1, fa.account_id, journal.gl_journal_id, fa.c_period_id  "
					+ " ORDER BY fa.c_activity_id_1, fa.account_id, journal.gl_journal_id, fa.c_period_id  ASC ";
			
			
			DB.executeUpdateEx(insert + sql, null);

		} catch (Exception e) {
			throw new AdempiereException(e);
		}


		
		
	}


	/***
	 * Setea posiciones por periodo. OpenUp Ltda. Issue #2978
	 * 
	 * @author Matias Carbajal - 14/11/2014
	 * @see
	 */
	private void setPeriodos() {
	
		int posicion = 0;
		for (int i = this.cPeriodFromID; i <= this.cPeriodToID; i++){
			posicion++;
			hashPosicionXPeriodo.put(i, posicion);
/*
			// Si el reporte no es moneda nacional
			if (!this.navCC.getCurrencyType().equalsIgnoreCase("MN")) {
				// Guardo Tasa de cambio de ultimo dia del periodo
				MPeriod period = new MPeriod(Env.getCtx(), i, null);
				BigDecimal rate = MConversionRate.getRate(100, 142,
						period.getEndDate(), 0, this.getAD_Client_ID(), 0);
				if (rate == null) {
					throw new AdempiereException(
							"No se pudo obtener Tasa de Cambio para la fecha : "
									+ period.getEndDate());
				}
				hashTasaCambioXPeriodo.put(i, rate);
			}
*/
		}

		// Si tengo mas de 13 periodos aviso con exception
		if (posicion > 13) {
			throw new AdempiereException(
					"El período posible a considerar NO puede ser mayor a un año");
		}

	}
	
	
	private void loadData() {
		

		String insert = "", sql = "";
		String condicionCuentaContable = "";
		String condicionCentroCosto = "";
		String condicionSocioNegocio = "";
		String condicionProducto = "";
		
		try {
			
			if(this.C_ElementValue_ID > 0){
				condicionCuentaContable = " AND fa.account_id = " + this.C_ElementValue_ID;
			}
			if (this.C_Activity_ID > 0){
				condicionCentroCosto = " AND fa.c_activity_id_1 = " + this.C_Activity_ID;
			}
			if (this.C_BPartner_ID > 0){
				condicionSocioNegocio = " AND fa.c_bpartner_id = " + this.C_BPartner_ID; 
			}
			if (this.M_Product_ID > 0){
				condicionProducto = " AND fa.m_product_id = " + this.M_Product_ID;
			}
			insert = "INSERT INTO "
					+ TABLA_MOLDE_ACCTNAV_EXCEL
					+ " (c_activity_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id, gl_journal_id, "
					+ " ad_user_id, ad_client_id, ad_org_id, saldomn)";

			sql = " SELECT fa.c_activity_id_1, fa.account_id, fa.c_bpartner_id , fa.m_product_id, fa.c_period_id, 0, "
					+ this.adUserID + ", " + this.adClientID + ", " + this.adOrgID + ", "
					+ " (SUM(fa.amtacctcr) - SUM(amtacctdr)) AS saldomn "
					+ " FROM fact_acct fa "
					+ " INNER join c_elementvalue ev ON fa.account_id = ev.c_elementvalue_id "
					+ " WHERE fa.c_period_id >="
					+ this.cPeriodFromID
					+ " AND fa.c_period_id <="
					+ this.cPeriodToID
					+ " AND fa.ad_table_id NOT IN (SELECT ad_table_id FROM ad_table WHERE name IN ('UY_YearEndIntegral','UY_YearEndOpen','UY_YearEndAcumulate','UY_YearEndResult')) "
					+ " AND fa.c_bpartner_id IS NOT NULL "
					+ " AND ev.c_element_id IN (SELECT c_element_id FROM c_element WHERE isnaturalaccount='Y' AND ad_client_id ="
					+ this.getAD_Client_ID()
					+ ") "
					+ " AND fa.ad_table_id not in(1000663,1000662,1000664,1000665) " 
					+ " AND ev.accounttype='E' " 
					+ condicionCuentaContable
					+ condicionCentroCosto
					+ condicionSocioNegocio 
					+ condicionProducto
					+ " GROUP BY fa.c_activity_id_1, fa.account_id, fa.c_bpartner_id , fa.m_product_id, fa.m_product_id, fa.c_period_id "
					+ " ORDER BY fa.c_activity_id_1, fa.account_id, fa.c_activity_id_1, fa.c_bpartner_id , fa.m_product_id, fa.c_period_id ASC ";

			DB.executeUpdateEx(insert + sql, null);

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}
	
	private void updateData() {
	
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_ACCTNAV_EXCEL + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY c_activity_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			int rowCount = 0;

			// Corte por centro de costo, cuenta socio de negocio, producto y periodo
			int cActivityID = 0, cElementValueID = 0, cBPartnerID = 0, mProductID = 0, cPeriodID = 0, glJournalID = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				rowCount++;
								
				if ((rs.getInt("c_activity_id") != cActivityID) || (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("c_bpartner_id") != cBPartnerID) || (rs.getInt("m_product_id") != mProductID)){

					// Si no estoy en primer registro actualizo cuenta anterior
					if (rowCount > 1){
						this.updateRow(cActivityID, cElementValueID, cBPartnerID, mProductID, cPeriodID, glJournalID, amt);
					}

					// Inserto nuevo registro en temporal final
					this.insertRow(rs.getInt("c_activity_id"), rs.getInt("c_elementvalue_id"), 
									   rs.getInt("c_bpartner_id"), rs.getInt("m_product_id"), rs.getInt("c_period_id"), rs.getInt("gl_journal_id"));
					
					cActivityID = rs.getInt("c_activity_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					cBPartnerID = rs.getInt("c_bpartner_id");
					mProductID = rs.getInt("m_product_id");
					cPeriodID = rs.getInt("c_period_id");
					
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						this.updateRow(cActivityID, cElementValueID, cBPartnerID, mProductID, cPeriodID, glJournalID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			if (cPeriodID > 0){
				this.updateRow(cActivityID, cElementValueID, cBPartnerID, mProductID, cPeriodID, glJournalID, amt);	
			}

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	
	private void updateDataJournal() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_ACCTNAV_EXCEL + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY c_activity_id, c_elementvalue_id, gl_journal_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			int rowCount = 0;

			// Corte por centro de costo, cuenta socio de negocio, producto y periodo
			int cActivityID = 0, cElementValueID = 0, cPeriodID = 0, glJournalID = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				rowCount++;
								
				if ((rs.getInt("c_activity_id") != cActivityID) || (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("gl_journal_id") != glJournalID)) {

					// Si no estoy en primer registro actualizo cuenta anterior
					if (rowCount > 1){
						this.updateRow(cActivityID, cElementValueID, 0, 0, cPeriodID, glJournalID, amt);
					}

					// Inserto nuevo registro en temporal final
					this.insertRow(rs.getInt("c_activity_id"), rs.getInt("c_elementvalue_id"), 
									   0, 0, rs.getInt("c_period_id"), rs.getInt("gl_journal_id"));
					
					cActivityID = rs.getInt("c_activity_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					cPeriodID = rs.getInt("c_period_id");
					glJournalID = rs.getInt("gl_journal_id");
					
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						this.updateRow(cActivityID, cElementValueID, 0, 0, cPeriodID, glJournalID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			if (cPeriodID > 0){
				this.updateRow(cActivityID, cElementValueID, 0, 0, cPeriodID, glJournalID, amt);	
			}

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	
	
	private void updateRow(int cActivityID, int cElementValueID, int cBPartnerID, int mProductID, int cPeriodID, int glJournalID, BigDecimal amt) {

		try{
			
					String condicionBPGL = null;
					// Posicion del periodo
					int posicion = this.hashPosicionXPeriodo.get(cPeriodID);
					
					String nameCampo ="";					
					if (posicion == 1) nameCampo = "Amt1"; 
					else if (posicion == 2) nameCampo = "Amt2";  
					else if (posicion == 3) nameCampo = "Amt3"; 
					else if (posicion == 4) nameCampo = "Amt4"; 
					else if (posicion == 5) nameCampo = "Amt5"; 
					else if (posicion == 6) nameCampo = "Amt6"; 
					else if (posicion == 7) nameCampo = "Amt7"; 
					else if (posicion == 8) nameCampo = "Amt8"; 
					else if (posicion == 9) nameCampo = "Amt9"; 
					else if (posicion == 10) nameCampo = "Amt10"; 
					else if (posicion == 11) nameCampo = "Amt11"; 
					else if (posicion == 12) nameCampo = "Amt12"; 
					else if (posicion == 13) nameCampo = "Amt12"; 
					
					
					if (cBPartnerID == 0)
						condicionBPGL = " AND gl_Journal_Id = " + glJournalID;
					else
						condicionBPGL = " AND c_BPartner_Id = " + cBPartnerID + " AND m_Product_Id = " + mProductID ;
						
					String action = "UPDATE  "+ TABLA_MOLDE_ACCTNAV_RV  + " SET " + nameCampo + " = " + amt  + 
									" WHERE c_Activity_Id = " + cActivityID +
									" AND c_ElementValue_Id = " + cElementValueID +
									condicionBPGL; 
									
					
					DB.executeUpdateEx(action, null);
					
			
				}
				catch (Exception e){
					throw new AdempiereException(e);
				}
				
				
				
			}
	private void insertRow(int cActivityID, int cElementValueID, int cBPartnerID, int mProductID, int cPeriodID, int glJournalID) {

		
		try{
			
			
			String action = " INSERT INTO " + TABLA_MOLDE_ACCTNAV_RV + " (ad_client_id,  ad_org_id , ad_user_id, c_Activity_Id, c_ElementValue_Id, c_BPartner_Id, m_Product_Id, gl_journal_Id)"  //, c_Period_Id)"
					+ "VALUES(" +  this.adClientID + ", "+ this.adOrgID + "," + this.adUserID + ", "  + cActivityID + ", " + cElementValueID + ", " + cBPartnerID + "," + mProductID + "," + glJournalID + ")"; //", " + cPeriodID + ")";
					
			DB.executeUpdateEx(action, null);
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	
	
}
