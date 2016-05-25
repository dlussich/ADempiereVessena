package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.APanel;
import org.compiere.apps.AWindow;
import org.compiere.model.MConversionRate;
import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MAcctNavCC;
import org.openup.model.MAcctNavCCAccount;
import org.openup.model.MAcctNavCCBP;
import org.openup.model.MAcctNavCCMain;
import org.openup.model.MAcctNavCCProd;
import org.openup.model.MGerencialProd;


public class PAcctNavProd extends SvrProcess {
	
	private MAcctNavCCAccount navAccount = null;
	private MAcctNavCCMain navMain = null;
	private MAcctNavCC navCC = null;
	private MAcctNavCCBP navBP = null;
	private MAcctNavCCProd navProd = null;

	private String idReporte = null;

	private static final String TABLA_MOLDE_NAV_PROD = "UY_MOLDE_AcctNavProd";
	
	public HashMap<Integer, Integer> hashPosicionXPeriodo = new HashMap<Integer, Integer>();
	public HashMap<Integer, BigDecimal> hashTasaCambioXPeriodo = new HashMap<Integer, BigDecimal>();


	@Override
	protected void prepare() {

		int recordID = 0;

		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("Record_ID")) {
					if (para[i].getParameter() != null)
						recordID = ((BigDecimal) para[i].getParameter())
								.intValueExact();
				}
			}
		}
		try {
			
			this.navBP = new MAcctNavCCBP(getCtx(), recordID, null); 
			this.navAccount = new MAcctNavCCAccount(getCtx(), this.navBP.getUY_AcctNavCC_Account_ID(), null);
			this.navMain = new MAcctNavCCMain(getCtx(),this.navAccount.getUY_AcctNavCC_Main_ID(), null);
			this.navCC = new MAcctNavCC(getCtx(),this.navMain.getUY_AcctNavCC_ID(), null);
		
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	@Override
	protected String doIt() throws Exception {

		this.deleteData();
		
		this.setPeriodos();
		
		this.loadData();
		
		this.updateData();
		
		try {
			DB.executeUpdateEx(" update uy_acctnavcc_prod set record_id = uy_acctnavcc_prod_id " +
					           " where uy_acctnavcc_main_id =" + this.navMain.get_ID(), null);
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		try {
			for (int i = 0; i < AWindow.getWindows().length; i++){
				if (AWindow.getWindows()[i] != null){
					if (AWindow.getWindows()[i] instanceof AWindow){
						AWindow awin = (AWindow)AWindow.getWindows()[i];
						if (awin.getAD_Window_ID() == 1000217){
							if (awin.getWindowNo() > 0){
								APanel apan = awin.getAPanel();
								if (apan != null){
									apan.setSelectedTabIndex(5); // select parent first	
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			// No hago nada;
		}
		
		
		return "OK";
	}

	

	

	

	/***
	 * Elimina informacion anterior. OpenUp Ltda. Issue #2978
	 * 
	 * @author Matias Carbajal - 14/11/2014
	 * @see
	 */
	private void deleteData() {
		try {

			String action = " DELETE FROM " + TABLA_MOLDE_NAV_PROD
					+ " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);

			action = " DELETE FROM uy_acctnavcc_prod  "
					+ " WHERE uy_acctnavcc_main_id =" + this.navMain.get_ID();
			DB.executeUpdateEx(action, null);

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
		for (int i = this.navCC.getC_Period_ID_From(); i <= this.navCC
				.getC_Period_ID_To(); i++) {
			posicion++;
			hashPosicionXPeriodo.put(i, posicion);

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

		}

		// Si tengo mas de 13 periodos aviso con exception
		if (posicion > 13) {
			throw new AdempiereException(
					"El período posible a considerar NO puede ser mayor a un año");
		}

	}

	/***
	 * Para una determinada cuenta de informe carga el detalle de saldos segun
	 * cuentas del plan. OpenUp Ltda. Issue #2978
	 * 
	 * @author Matias Carbajal - 14/11/2014
	 * @see
	 */
	private void loadData() {

		String insert = "", sql = "";
		
		try {
			
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_PROD + " (parent_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";

			this.showHelp("Totales Producto...");
			
			sql = " select ev.c_element_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + this.getAD_User_ID() + ", " +			
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.navCC.getC_Period_ID_From() + " and fa.c_period_id <=" + this.navCC.getC_Period_ID_To() +
				  " and fa.ad_table_id not in (select ad_table_id from ad_table where name in('UY_YearEndIntegral','UY_YearEndOpen','UY_YearEndAcumulate','UY_YearEndResult')) " +			  
				  " and fa.c_bpartner_id is not null " +
				  " and fa.m_product_id is not null " +
				  " and ev.c_element_id in (select c_element_id from c_element where isnaturalaccount='Y' and ad_client_id =" + this.getAD_Client_ID() + ")" + 
				  " and fa.c_activity_id_1 =" + this.navAccount.getC_Activity_ID_1() +
				  " and fa.account_id =" + this.navAccount.getC_ElementValue_ID() +
				  " and fa.c_bpartner_id =" + this.navBP.getC_BPartner_ID() +
				  " group by ev.c_element_id, fa.account_id, fa.c_bpartner_id , fa.m_product_id, fa.c_period_id " +
				  " order by fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}

		
	}
	
	/***
	 * Actualiza información de detalle de cuentas ahora abriendola en periodos horizontales.
	 * OpenUp Ltda. Issue #2978 
	 * @author Matias Carbajal - 14/11/2014
	 * @see
	 */
	private void updateData() {
	
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_PROD + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY c_bpartner_id, m_product_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta y periodo
			int cBPartnerID = 0, mProductID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Procesando " + rowCount++ + " de " + totalRowCount);
				
				if ((rs.getInt("c_bpartner_id") != cBPartnerID) || (rs.getInt("m_product_id") != mProductID)){

					// Si no estoy en primer registro actualizo cuenta anterior
					if (rowCount > 1){
						this.updateProduct(cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					seqNo++;
					this.insertProduct(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), 
									   rs.getInt("c_bpartner_id"), rs.getInt("m_product_id"), seqNo);
					
					cBPartnerID = rs.getInt("c_bpartner_id");
					mProductID = rs.getInt("m_product_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						this.updateProduct(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			if (cPeriodID > 0){
				this.updateProduct(cPeriodID, amt);	
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
	
	/***
	 * Inserta nuevo registro en tabla de cuentas del plan por cuenta de informe.
	 * Segundo Nivel.
	 * OpenUp Ltda. Issue #2978 
	 * @author Matias Carbajal - 14/11/2014
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType
	 * @param seqNo
	 */
	private void insertProduct(int parentID, int cElementValueID, int cBPartnerID, int mProductID, int seqNo) {

		try{
			
			int processID = MProcess.getProcess_ID("UY_RCtb_AcctNavCCProdDet", null);
			
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			
			int seqNoParent = 0;

			this.navProd = new MAcctNavCCProd(getCtx(), 0, null);
			this.navProd.setUY_AcctNavCC_ID(this.navCC.get_ID());
			this.navProd.setUY_AcctNavCC_Main_ID(this.navMain.get_ID());
			this.navProd.setUY_AcctNavCC_Account_ID(this.navAccount.get_ID());
			this.navProd.setParent_ID(parentID);
			this.navProd.setC_ElementValue_ID(cElementValueID);
			this.navProd.setparentname(parentName);
			this.navProd.setC_BPartner_ID(cBPartnerID);
			this.navProd.setM_Product_ID(mProductID);
			this.navProd.setUY_AcctNavCC_BP_ID(this.navBP.get_ID());
			this.navProd.setAD_Process_ID(processID);
			
			this.navProd.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	/***
	 * Actualiza datos de una cuenta del plan asociada a una cuenta informe.
	 * Segundo Nivel.
	 * OpenUp Ltda. Issue #2978 
	 * @author Gabriel Vila - 14/11/2014
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateProduct(int cPeriodID, BigDecimal amt) {

try{
			
			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.navCC.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(this.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.navCC.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(this.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) this.navProd.setamt1(amt); 
			else if (posicion == 2) this.navProd.setamt2(amt);
			else if (posicion == 3) this.navProd.setamt3(amt);
			else if (posicion == 4) this.navProd.setamt4(amt);
			else if (posicion == 5) this.navProd.setamt5(amt);
			else if (posicion == 6) this.navProd.setamt6(amt);
			else if (posicion == 7) this.navProd.setamt7(amt);
			else if (posicion == 8) this.navProd.setamt8(amt);
			else if (posicion == 9) this.navProd.setamt9(amt);
			else if (posicion == 10) this.navProd.setamt10(amt);
			else if (posicion == 11) this.navProd.setamt11(amt);
			else if (posicion == 12) this.navProd.setamt12(amt);
			else if (posicion == 13) this.navProd.setamt13(amt);
			
			this.navProd.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		

		
	}

	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #2978 
	 * @author Matias Carbajal - 14/11/2014
	 * @see
	 * @param text
	 */
	private void showHelp(String text){

		if (this.getProcessInfo().getWaiting() != null){
			this.getProcessInfo().getWaiting().setText(text);
		}
		
	}
}
