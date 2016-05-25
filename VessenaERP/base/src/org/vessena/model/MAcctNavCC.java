/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 31/01/2013
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.Waiting;
import org.compiere.model.MConversionRate;
import org.compiere.model.MPeriod;
import org.compiere.model.MProcess;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MAcctNavCC
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 31/01/2013
 * @see
 */
public class MAcctNavCC extends X_UY_AcctNavCC {

	private static final long serialVersionUID = 8221068033504959512L;

	private Waiting waiting = null;
	public HashMap<Integer, Integer> hashPosicionXPeriodo = new HashMap<Integer, Integer>();
	public HashMap<Integer, BigDecimal> hashTasaCambioXPeriodo = new HashMap<Integer, BigDecimal>();

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_AcctNavCC_ID
	 * @param trxName
	 */
	public MAcctNavCC(Properties ctx, int UY_AcctNavCC_ID, String trxName) {
		super(ctx, UY_AcctNavCC_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAcctNavCC(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}

	
	/***
	 * Ejecuta consulta del Navegador Contable, considerando filtros seleccionados
	 * por el usuario.
	 * OpenUp Ltda. Issue #273 
	 * @author Gabriel Vila - 31/01/2013
	 * @see
	 */
	public void execute() {

		try{
			
			// Elimina datos anteriores
			this.deleteData();
			
			// Seteo posicion de periodos
			this.setPeriodos();
			
			// Cargo totales por centro de costo
			this.loadCCTotals();
			
			// Por cada centro de costo cargo detalle por cuenta contable
			List<MAcctNavCCMain> ccmains = this.getLines();
			for (MAcctNavCCMain ccmain: ccmains){
				this.loadCCAccounts(ccmain);	
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	
	/***
	 * Carga totales por cuenta segun centro de costo recibido.
	 * OpenUp Ltda. Issue #273 
	 * @author Gabriel Vila - 01/02/2013
	 * @see
	 */
	private void loadCCAccounts(MAcctNavCCMain ccMain) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			String whereCCosto = "";
			
			if (ccMain.getC_Activity_ID_1() > 0){
				whereCCosto = " and f.c_activity_id_1 = " + ccMain.getC_Activity_ID_1(); 
			}
			else{
				whereCCosto = " and f.c_activity_id_1 is null ";
			}
			
			MPeriod periodoDesde = new MPeriod(getCtx(), this.getC_Period_ID_From(), null);
			MPeriod periodoHasta = new MPeriod(getCtx(), this.getC_Period_ID_To(), null);
			
			sql = " select f.account_id, f.c_period_id, " +
					//" coalesce(SUM(coalesce(f.amtacctdr,0) - coalesce(f.amtacctcr,0)),0) as saldomn " +
					// Cambio Matias Carbajal #2978
				  " coalesce(SUM(coalesce(f.amtacctcr,0) - coalesce(f.amtacctdr,0)),0) as saldomn " +
					// Fin Cambio
				  " from fact_acct f " +
				  " inner join c_elementvalue ev on f.account_id = ev.c_elementvalue_id " +
				  " where f.ad_client_id =? " +
				  " and f.dateacct>=? and f.dateacct<=? " +
				  " and f.ad_table_id not in(1000663,1000662,1000664,1000665) " +				  
				  whereCCosto +
				  " and ev.accounttype='E' " +
				  " group by f.account_id, f.c_period_id " +
				  " order by f.account_id, f.c_period_id ";

			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setTimestamp(2, periodoDesde.getStartDate());
			pstmt.setTimestamp(3, periodoHasta.getEndDate());
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			BigDecimal amt = Env.ZERO;
			
			// Corte por cuenta y periodo
			int accountID = 0, cPeriodID = 0;
			MAcctNavCCAccount ccAccount = null;
			
			// Recorro registros y voy actualizando
			while (rs.next()){

				this.showHelp("Procesando " + rowCount++ + " de " + totalRowCount);
				
				if (rs.getInt("account_id") != accountID){
					
					// Si no estoy en primer registro actualizo ccosto anterior
					if (rowCount > 1){
						this.updateCCAccount(ccAccount, cPeriodID, amt);
					}
					
					// Nuevo modelo y guardo en tabla
					ccAccount = new MAcctNavCCAccount(getCtx(), 0, null);
					ccAccount.setUY_AcctNavCC_ID(this.get_ID());
					ccAccount.setUY_AcctNavCC_Main_ID(ccMain.get_ID());
					ccAccount.setC_Activity_ID_1(ccMain.getC_Activity_ID_1());
					ccAccount.setC_ElementValue_ID(rs.getInt("account_id"));
					ccAccount.setSeqNo(rowCount);
					ccAccount.setAD_Process_ID(MProcess.getProcess_ID("UY_RCtb_AcctNavBPGL", null));
					ccAccount.saveEx();

					ccAccount.setRecord_ID(ccAccount.get_ID());
					ccAccount.saveEx();
					
					accountID = rs.getInt("account_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateCCAccount(ccAccount, cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}

			// Ultimo registro
			if (ccAccount != null){
				this.updateCCAccount(ccAccount, cPeriodID, amt);	
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
	 * Carga totales por centro de costo.
	 * OpenUp Ltda. Issue #273 
	 * @author Gabriel Vila - 01/02/2013
	 * @see
	 */
	private void loadCCTotals() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			// Filtro de centros de costo
			String whereCCostos = "";
			List<MAcctNavCCCCFil> ccfils = this.geFiltrosCC();
			if(ccfils.size() > 0){
				whereCCostos += " AND f.c_activity_id_1 in (";
				
				whereCCostos += ")";
			}
			
			MPeriod periodoDesde = new MPeriod(getCtx(), this.getC_Period_ID_From(), null);
			MPeriod periodoHasta = new MPeriod(getCtx(), this.getC_Period_ID_To(), null);
			
			
			sql = " select coalesce(f.c_activity_id_1,0) as c_activity_id_1, f.c_period_id, " +
				  //" coalesce(SUM(coalesce(f.amtacctdr,0) - coalesce(f.amtacctcr,0)),0) as saldomn " +
				  //cambio Matias Carbajal #2978 - 18/12/2014
				  " coalesce(SUM(coalesce(f.amtacctcr,0) - coalesce(f.amtacctdr,0)),0) as saldomn " +
				  //fin cambio - 18/12/2014
				  " from fact_acct f " +
				  " inner join c_elementvalue ev on f.account_id = ev.c_elementvalue_id " +
				  " where f.ad_client_id =? " +
				  " and f.dateacct>=? and f.dateacct<=? " +
				  " and f.ad_table_id not in(1000663,1000662,1000664,1000665) " +				  
				  whereCCostos +				  
				  " and ev.accounttype='E' " +
				  " group by f.c_activity_id_1, f.c_period_id " +
				  " order by f.c_activity_id_1, f.c_period_id ";

			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setTimestamp(2, periodoDesde.getStartDate());
			pstmt.setTimestamp(3, periodoHasta.getEndDate());
			
			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			BigDecimal amt = Env.ZERO;
			
			// Corte por centro de costo y periodo
			int cCostoID = 0, cPeriodID = 0;
			MAcctNavCCMain ccMain = null;
			
			// Recorro registros y voy actualizando
			while (rs.next()){

				this.showHelp("Procesando " + rowCount++ + " de " + totalRowCount);
				
				if (rs.getInt("c_activity_id_1") != cCostoID){
					
					// Si no estoy en primer registro actualizo ccosto anterior
					if (rowCount > 1){
						this.updateCCMain(ccMain, cPeriodID, amt);
					}
					
					// Nuevo modelo y guardo en tabla
					ccMain = new MAcctNavCCMain(getCtx(), 0, null);
					ccMain.setUY_AcctNavCC_ID(this.get_ID());
					ccMain.setC_Activity_ID_1(rs.getInt("c_activity_id_1"));
					ccMain.setSeqNo(rowCount);
					ccMain.saveEx();
					
					cCostoID = rs.getInt("c_activity_id_1");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateCCMain(ccMain, cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				
				
				BigDecimal saldoLinea = rs.getBigDecimal("saldomn");
				if (saldoLinea == null) saldoLinea = Env.ZERO;
				
				amt = amt.add(saldoLinea);
				
			}

			// Ultimo registro
			if (ccMain != null){
				this.updateCCMain(ccMain, cPeriodID, amt);	
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
	 * Actualiza monto de un determinado centro de costo - periodo.
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 01/02/2013
	 * @see
	 * @param ccMain
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateCCMain(MAcctNavCCMain ccMain, int cPeriodID, BigDecimal amt) {

		try{
			
			// Posicion del periodo
			int posicion = hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) ccMain.setamt1(amt);
			else if (posicion == 2) ccMain.setamt2(amt);	
			else if (posicion == 3) ccMain.setamt3(amt);
			else if (posicion == 4) ccMain.setamt4(amt);
			else if (posicion == 5) ccMain.setamt5(amt);
			else if (posicion == 6) ccMain.setamt6(amt);
			else if (posicion == 7) ccMain.setamt7(amt);
			else if (posicion == 8) ccMain.setamt8(amt);
			else if (posicion == 9) ccMain.setamt9(amt);
			else if (posicion == 10) ccMain.setamt10(amt);
			else if (posicion == 11) ccMain.setamt11(amt);
			else if (posicion == 12) ccMain.setamt12(amt);
			else if (posicion == 13) ccMain.setamt13(amt);
			
			ccMain.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	
	}

	/**
	 * Actualiza monto de un determinada cuenta de un determinado centro de costo - periodo.
	 * OpenUp Ltda. Issue #273 
	 * @author Gabriel Vila - 01/02/2013
	 * @see
	 * @param ccAccount
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateCCAccount(MAcctNavCCAccount ccAccount, int cPeriodID, BigDecimal amt) {

		try{
			
			// Posicion del periodo
			int posicion = hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) ccAccount.setamt1(amt);
			else if (posicion == 2) ccAccount.setamt2(amt);	
			else if (posicion == 3) ccAccount.setamt3(amt);
			else if (posicion == 4) ccAccount.setamt4(amt);
			else if (posicion == 5) ccAccount.setamt5(amt);
			else if (posicion == 6) ccAccount.setamt6(amt);
			else if (posicion == 7) ccAccount.setamt7(amt);
			else if (posicion == 8) ccAccount.setamt8(amt);
			else if (posicion == 9) ccAccount.setamt9(amt);
			else if (posicion == 10) ccAccount.setamt10(amt);
			else if (posicion == 11) ccAccount.setamt11(amt);
			else if (posicion == 12) ccAccount.setamt12(amt);
			else if (posicion == 13) ccAccount.setamt13(amt);
			
			ccAccount.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	
	/***
	 * Obtiene y retorna lineas de totales por centros de costo.
	 * OpenUp Ltda. Issue #273 
	 * @author Gabriel Vila - 31/01/2013
	 * @see
	 * @return
	 */
	public List<MAcctNavCCMain> getLines(){
		
		String whereClause = X_UY_AcctNavCC_Main.COLUMNNAME_UY_AcctNavCC_ID + "=" + this.get_ID();
		
		List<MAcctNavCCMain> values = new Query(getCtx(), I_UY_AcctNavCC_Main.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
	}
	
	
	/***
	 * Obtiene y retorna filtros de centros de costo.
	 * OpenUp Ltda. Issue #273 
	 * @author Gabriel Vila - 01/02/2013
	 * @see
	 * @return
	 */
	public List<MAcctNavCCCCFil> geFiltrosCC(){
		
		String whereClause = X_UY_AcctNavCC_CCFil.COLUMNNAME_UY_AcctNavCC_ID + "=" + this.get_ID();
		
		List<MAcctNavCCCCFil> values = new Query(getCtx(), I_UY_AcctNavCC_CCFil.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
	}
	
	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}

	/***
	 * Elimina datos anteriores producto de la ejecucion de la consulta.
	 * OpenUp Ltda. Issue #273 
	 * @author Gabriel Vila - 01/02/2013
	 * @see
	 */
	private void deleteData() {
		try{
			
			String action = " DELETE FROM " + I_UY_AcctNavCC_Main.Table_Name + " cascade " +
							" WHERE uy_acctnavcc_id = " + this.get_ID();
			
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM " + I_UY_AcctNavCC_Account.Table_Name + " cascade " +
					" WHERE uy_acctnavcc_id = " + this.get_ID();
	
			DB.executeUpdateEx(action,null);

		
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Setea posiciones por periodo.
	 * OpenUp Ltda. Issue #273 
	 * @author Gabriel Vila - 01/02/2012
	 * @see
	 */
	private void setPeriodos(){
		
		int posicion = 0;
		for (int i = this.getC_Period_ID_From(); i <= this.getC_Period_ID_To(); i++){
			posicion++;
			hashPosicionXPeriodo.put(i, posicion);
			
			// Si el reporte no es moneda nacional
			if (!this.getCurrencyType().equalsIgnoreCase("MN")){
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
			throw new AdempiereException("El período posible a considerar NO puede ser mayor a un año");
		}
	}

}
