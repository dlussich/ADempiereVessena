/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 17/04/2014
 */
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
import org.openup.model.MGerencial;
import org.openup.model.MGerencialAccount;
import org.openup.model.MGerencialBP;
import org.openup.model.MGerencialMain;
import org.openup.model.MGerencialProd;

/**
 * org.openup.process - InformeGerencialProd
 * OpenUp Ltda. Issue #2081 
 * Description: Dado un socio de negocio, cargo detalle por producto.
 * @author Gabriel Vila - 17/04/2014
 * @see
 */
public class InformeGerencialProd extends SvrProcess {

	private MGerencialMain gerMain = null;
	private MGerencial gerencial = null;
	private MGerencialAccount gerAccount = null;
	private MGerencialBP gerBP = null;
	private MGerencialProd gerProd = null;
	
	
	private String idReporte = null;
	
	private static final String TABLA_MOLDE_NAV_PROD = "UY_MOLDE_InformeGerencial_6";
	
	public HashMap<Integer, Integer> hashPosicionXPeriodo = new HashMap<Integer, Integer>();
	public HashMap<Integer, BigDecimal> hashTasaCambioXPeriodo = new HashMap<Integer, BigDecimal>();

	
	/**
	 * Constructor.
	 */
	public InformeGerencialProd() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 17/04/2014
	 * @see
	 */
	@Override
	protected void prepare() {

		int recordID = 0;
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("Record_ID")){
					if (para[i].getParameter()!=null)
						recordID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}

		
		try {
		
			this.gerBP = new MGerencialBP(getCtx(), recordID, null);
			this.gerAccount = (MGerencialAccount)this.gerBP.getUY_Gerencial_Account(); 
			this.gerMain = (MGerencialMain)this.gerAccount.getUY_Gerencial_Main();
			this.gerencial = (MGerencial)this.gerMain.getUY_Gerencial();
			this.idReporte = UtilReportes.getReportID(new Long(this.getAD_User_ID()));
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 17/04/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		this.deleteData();
		
		this.setPeriodos();
		
		this.loadData();
		
		this.updateData();
		
		try {
			DB.executeUpdateEx(" update uy_gerencial_prod set record_id = uy_gerencial_prod_id " +
					           " where uy_gerencial_main_id =" + this.gerMain.get_ID(), null);
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		try {
			for (int i = 0; i < AWindow.getWindows().length; i++){
				if (AWindow.getWindows()[i] != null){
					if (AWindow.getWindows()[i] instanceof AWindow){
						AWindow awin = (AWindow)AWindow.getWindows()[i];
						if (awin.getAD_Window_ID() == 1000219){
							if (awin.getWindowNo() > 0){
								APanel apan = awin.getAPanel();
								if (apan != null){
									apan.setSelectedTabIndex(4); // select parent first	
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
	 * Elimina informacion anterior.
	 * OpenUp Ltda. Issue #2081 
	 * @author Gabriel Vila - 15/04/2014
	 * @see
	 */
	private void deleteData() {

		try{

			String action = " DELETE FROM " + TABLA_MOLDE_NAV_PROD +
					 		" WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM uy_gerencial_prod " + 
					 " WHERE uy_gerencial_main_id =" + this.gerMain.get_ID();
			DB.executeUpdateEx(action,null);

			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Setea posiciones por periodo.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 */
	private void setPeriodos(){
		
		int posicion = 0;
		for (int i = this.gerencial.getC_Period_ID_From(); i <= this.gerencial.getC_Period_ID_To(); i++){
			posicion++;
			hashPosicionXPeriodo.put(i, posicion);
			
			// Si el reporte no es moneda nacional
			if (!this.gerencial.getCurrencyType().equalsIgnoreCase("MN")){
				// Guardo Tasa de cambio de ultimo dia del periodo
				MPeriod period = new MPeriod(Env.getCtx(), i, null);
				BigDecimal rate = MConversionRate.getRate(100, 142, period.getEndDate(), 0, this.getAD_Client_ID(), 0);	
				if (rate == null){
					throw new AdempiereException("No se pudo obtener Tasa de Cambio para la fecha : " + period.getEndDate());
				}
				hashTasaCambioXPeriodo.put(i, rate);
			}
			
		}
		
		// Si tengo mas de 13 periodos aviso con exception
		if (posicion > 13){
			throw new AdempiereException("El per�odo posible a considerar NO puede ser mayor a un a�o");
		}
	}

	
	/***
	 * Para una determinada cuenta de informe carga el detalle de saldos segun cuentas del plan.
	 * OpenUp Ltda. Issue #2081 
	 * @author Gabriel Vila - 15/04/2014
	 * @see
	 */
	private void loadData(){

		String insert = "", sql = "";
		
		try {
			
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_PROD + " (parent_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";

			this.showHelp("Totales Producto...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + this.getAD_User_ID() + ", " +			
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.gerencial.getC_Period_ID_From() + " and fa.c_period_id <=" + this.gerencial.getC_Period_ID_To() +
				  " and fa.ad_table_id not in(1000663,1000662,1000664,1000665) " +				  
				  " and fa.c_bpartner_id is not null " +
				  " and fa.m_product_id is not null " +
				// " and evig.c_element_id = 1000008 " + ---ORIGINAL SBT 20022015
				  " and evig.c_element_id = " + this.gerencial.get_ValueAsInt("C_Element_ID")+ // reemplazo ORIGINAL 20022015
				  " and evig.c_elementvalue_id = " + this.gerMain.getC_ElementValue_ID() +				  
				  " and fa.account_id =" + this.gerAccount.getC_ElementValue_ID() +
				  " and fa.c_bpartner_id =" + this.gerBP.getC_BPartner_ID() +
				  " group by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id , fa.m_product_id, fa.c_period_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Actualiza informaci�n de detalle de cuentas ahora abriendola en periodos horizontales.
	 * OpenUp Ltda. Issue #2081 
	 * @author Gabriel Vila - 15/04/2014
	 * @see
	 */
	private void updateData(){
		
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
	 * OpenUp Ltda. Issue #2081 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType
	 * @param seqNo
	 */
	private void insertProduct(int parentID, int cElementValueID, int cBPartnerID, int mProductID, int seqNo) {
		
		try{
		
			int processID = MProcess.getProcess_ID("UY_RCtb_InfGerProdDet", null);
			
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			
			int seqNoParent = 0;

			this.gerProd = new MGerencialProd(getCtx(), 0, null);
			this.gerProd.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerProd.setUY_Gerencial_Main_ID(this.gerMain.get_ID());
			this.gerProd.setUY_Gerencial_Account_ID(this.gerAccount.get_ID());
			this.gerProd.setParent_ID(parentID);
			this.gerProd.setC_ElementValue_ID(cElementValueID);
			this.gerProd.setparentname(parentName);
			this.gerProd.setC_BPartner_ID(cBPartnerID);
			this.gerProd.setM_Product_ID(mProductID);
			this.gerProd.setUY_Gerencial_BP_ID(this.gerBP.get_ID());
			this.gerProd.setseqnoparent(seqNoParent);
			this.gerProd.setSeqNo(seqNo);
			this.gerProd.setAD_Process_ID(processID);
			
			this.gerProd.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}


	/***
	 * Actualiza datos de una cuenta del plan asociada a una cuenta informe.
	 * Segundo Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateProduct(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.gerencial.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(this.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.gerencial.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(this.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) this.gerProd.setamt1(amt); 
			else if (posicion == 2) this.gerProd.setamt2(amt);
			else if (posicion == 3) this.gerProd.setamt3(amt);
			else if (posicion == 4) this.gerProd.setamt4(amt);
			else if (posicion == 5) this.gerProd.setamt5(amt);
			else if (posicion == 6) this.gerProd.setamt6(amt);
			else if (posicion == 7) this.gerProd.setamt7(amt);
			else if (posicion == 8) this.gerProd.setamt8(amt);
			else if (posicion == 9) this.gerProd.setamt9(amt);
			else if (posicion == 10) this.gerProd.setamt10(amt);
			else if (posicion == 11) this.gerProd.setamt11(amt);
			else if (posicion == 12) this.gerProd.setamt12(amt);
			else if (posicion == 13) this.gerProd.setamt13(amt);
			
			this.gerProd.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text){

		if (this.getProcessInfo().getWaiting() != null){
			this.getProcessInfo().getWaiting().setText(text);
		}
		
	}
	
	
}
