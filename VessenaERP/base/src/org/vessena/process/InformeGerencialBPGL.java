/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 16/04/2014
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
import org.openup.model.MGerencialJournal;
import org.openup.model.MGerencialMain;

/**
 * org.openup.process - InformeGerencialBPGL
 * OpenUp Ltda. Issue #2081 
 * Description: Dada una cuenta del plan, carga detalle por socio de negocio y asientos siemples.
 * @author Gabriel Vila - 16/04/2014
 * @see
 */
public class InformeGerencialBPGL extends SvrProcess {

	private MGerencialMain gerMain = null;
	private MGerencial gerencial = null;
	private MGerencialAccount gerAccount = null;
	private MGerencialBP gerBP = null;
	private MGerencialJournal gerJournal = null;
	
	
	private String idReporte = null;
	
	private static final String TABLA_MOLDE_NAV_BP = "UY_MOLDE_InformeGerencial_4";
	private static final String TABLA_MOLDE_NAV_JOURNAL = "UY_MOLDE_InformeGerencial_5";
	
	public HashMap<Integer, Integer> hashPosicionXPeriodo = new HashMap<Integer, Integer>();
	public HashMap<Integer, BigDecimal> hashTasaCambioXPeriodo = new HashMap<Integer, BigDecimal>();

	private boolean hasBP = false, hasGL = false;
	
	
	/**
	 * Constructor.
	 */
	public InformeGerencialBPGL() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/04/2014
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
			
			this.gerAccount = new MGerencialAccount(getCtx(), recordID, null);
			this.gerMain = new MGerencialMain(getCtx(), gerAccount.getUY_Gerencial_Main_ID(), null);
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
	 * @author Gabriel Vila - 16/04/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try{
		
			// this.getProcessInfo().getWindow().getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			this.deleteData();
			
			this.setPeriodos();
			
			this.loadData();
			
			this.updateData();
			
				
			DB.executeUpdateEx(" update uy_gerencial_bp set record_id = uy_gerencial_bp_id " +
					           " where uy_gerencial_main_id =" + this.gerMain.get_ID(), null);
			
			DB.executeUpdateEx(" update uy_gerencial_journal set record_id = uy_gerencial_journal_id " +
			           		   " where uy_gerencial_main_id =" + this.gerMain.get_ID(), null);
			
		}
		
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		/*
		finally{
			this.getProcessInfo().getWindow().setCursor(Cursor.getDefaultCursor());
		}
		*/
		
		try {
			for (int i = 0; i < AWindow.getWindows().length; i++){
				if (AWindow.getWindows()[i] != null){
					if (AWindow.getWindows()[i] instanceof AWindow){
						AWindow awin = (AWindow)AWindow.getWindows()[i];
						if (awin.getAD_Window_ID() == 1000219){
							if (awin.getWindowNo() > 0){
								APanel apan = awin.getAPanel();
								if (apan != null){
									if (this.hasBP) apan.setSelectedTabIndex(3); // select parent first
									else if (this.hasGL) apan.setSelectedTabIndex(6); // select parent first										
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

			String action = " DELETE FROM " + TABLA_MOLDE_NAV_BP +
					 " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_JOURNAL +
					 " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM uy_gerencial_bp " + 
					 " WHERE uy_gerencial_account_id =" + this.gerAccount.get_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM uy_gerencial_journal " + 
					 " WHERE uy_gerencial_account_id =" + this.gerAccount.get_ID();
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
			throw new AdempiereException("El período posible a considerar NO puede ser mayor a un año");
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
			
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_BP + " (parent_id, c_elementvalue_id, c_bpartner_id, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";

			this.showHelp("Totales SNeg...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + this.getAD_User_ID() + ", " +			
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.gerencial.getC_Period_ID_From() + " and fa.c_period_id <=" + this.gerencial.getC_Period_ID_To() +
				  " and fa.ad_table_id not in(1000663,1000662,1000664,1000665) " +				  
				  " and fa.c_bpartner_id is not null " +
				  //" and evig.c_element_id = 1000008 " + original SBT 20022015
				  " and evig.c_element_id = " + this.gerencial.get_ValueAsInt("C_Element_ID")+ // reemplazo ORIGINAL SBT 20022015
				  " and evig.c_elementvalue_id = " + this.gerMain.getC_ElementValue_ID() +				  
				  " and fa.account_id =" + this.gerAccount.getC_ElementValue_ID() +
				  " group by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id , fa.c_period_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);

			
			this.showHelp("Totales Asientos Diarios...");
			
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_JOURNAL + " (parent_id, c_elementvalue_id, gl_journal_id, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";
			
			sql = " select evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + this.getAD_User_ID() + ", " +			
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join gl_journal journal on fa.record_id = journal.gl_journal_id " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.gerencial.getC_Period_ID_From() + " and fa.c_period_id <=" + this.gerencial.getC_Period_ID_To() +
				  " and fa.ad_table_id = 224 " +
				//" and evig.c_element_id = 1000008 " + ORIGINAL SBT 20022015
				  " and evig.c_element_id = " + this.gerencial.get_ValueAsInt("C_Element_ID")+ // reemplazo ORIGINAL 20022015
				  " and evig.c_elementvalue_id = " + this.gerMain.getC_ElementValue_ID() +				  
				  " and fa.account_id =" + this.gerAccount.getC_ElementValue_ID() +
				  " group by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);

			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Actualiza totales por periodos en detalle de socio de negocio y asientos simples.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/04/2014
	 * @see
	 */
	private void updateData(){
		
		try {
			
			this.updateBP();
			
			this.updateJournal();
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Actualiza información de detalle de una cuenta del plan abierta por socio de negocio.
	 * OpenUp Ltda. Issue #2081 
	 * @author Gabriel Vila - 15/04/2014
	 * @see
	 */
	private void updateBP(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_4 " + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY c_bpartner_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta y periodo
			int bpartnerID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.hasBP = true;
				
				this.showHelp("Procesando SNeg " + rowCount++ + " de " + totalRowCount);
				
				if (rs.getInt("c_bpartner_id") != bpartnerID){

					// Si no estoy en primer registro actualizo anterior
					if (rowCount > 1){
						this.updateBPRow(cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					seqNo++;
					this.insertBPRow(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("c_bpartner_id"), seqNo);
					
					bpartnerID = rs.getInt("c_bpartner_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						this.updateBPRow(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			if (cPeriodID > 0){
				this.updateBPRow(cPeriodID, amt);	
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
	 * Actualiza información de detalle de una cuenta del plan abierta por socio de negocio.
	 * OpenUp Ltda. Issue #2081 
	 * @author Gabriel Vila - 15/04/2014
	 * @see
	 */
	private void updateJournal(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_5 " + 
			      " WHERE ad_user_id =? " +
				  " ORDER BY gl_journal_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta y periodo
			int glJournalID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.hasGL = true;
				
				this.showHelp("Procesando Asiento " + rowCount++ + " de " + totalRowCount);
				
				if (rs.getInt("gl_journal_id") != glJournalID){

					// Si no estoy en primer registro actualizo anterior
					if (rowCount > 1){
						this.updateGLRow(cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					seqNo++;
					this.insertGLRow(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("gl_journal_id"), seqNo);
					
					glJournalID = rs.getInt("gl_journal_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						this.updateGLRow(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			if (cPeriodID > 0){
				this.updateGLRow(cPeriodID, amt);	
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
	 * Inserta nuevo registro para detalle por socio de negocio.
	 * OpenUp Ltda. Issue #2081 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType
	 * @param seqNo
	 */
	private void insertBPRow(int parentID, int cElementValueID, int cBPartnerID, int seqNo) {
		
		try{
		
			int processID = MProcess.getProcess_ID("UY_RCtb_InfGerProd", null);
			
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			
			int seqNoParent = 0;
			
			this.gerBP = new MGerencialBP(getCtx(), 0, null);
			this.gerBP.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerBP.setUY_Gerencial_Main_ID(this.gerMain.get_ID());
			this.gerBP.setUY_Gerencial_Account_ID(this.gerAccount.get_ID());
			this.gerBP.setParent_ID(parentID);
			this.gerBP.setC_ElementValue_ID(cElementValueID);
			this.gerBP.setparentname(parentName);
			this.gerBP.setC_BPartner_ID(cBPartnerID);
			this.gerBP.setseqnoparent(seqNoParent);
			this.gerBP.setSeqNo(seqNo);
			this.gerBP.setAD_Process_ID(processID);
			
			this.gerBP.saveEx();

			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	
	/***
	 * Inserta nuevo registro para detalle por asiento simple.
	 * OpenUp Ltda. Issue #2081 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType
	 * @param seqNo
	 */
	private void insertGLRow(int parentID, int cElementValueID, int glJournalID, int seqNo) {
		
		try{
		
			int processID = MProcess.getProcess_ID("UY_RCtb_InfGerCC", null);
			
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			
			int seqNoParent = 0;
			
			this.gerJournal = new MGerencialJournal(getCtx(), 0, null);
			this.gerJournal.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerJournal.setUY_Gerencial_Main_ID(this.gerMain.get_ID());
			this.gerJournal.setUY_Gerencial_Account_ID(this.gerAccount.get_ID());
			this.gerJournal.setParent_ID(parentID);
			this.gerJournal.setC_ElementValue_ID(cElementValueID);
			this.gerJournal.setparentname(parentName);
			this.gerJournal.setGL_Journal_ID(glJournalID);
			this.gerJournal.setseqnoparent(seqNoParent);
			this.gerJournal.setSeqNo(seqNo);
			this.gerJournal.setAD_Process_ID(processID);
			
			this.gerJournal.saveEx();

			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	
	/***
	 * Actualiza datos del detalle de un socio de negocio.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateBPRow(int cPeriodID, BigDecimal amt) {
		
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
			
			if (posicion == 1) this.gerBP.setamt1(amt); 
			else if (posicion == 2) this.gerBP.setamt2(amt);
			else if (posicion == 3) this.gerBP.setamt3(amt);
			else if (posicion == 4) this.gerBP.setamt4(amt);
			else if (posicion == 5) this.gerBP.setamt5(amt);
			else if (posicion == 6) this.gerBP.setamt6(amt);
			else if (posicion == 7) this.gerBP.setamt7(amt);
			else if (posicion == 8) this.gerBP.setamt8(amt);
			else if (posicion == 9) this.gerBP.setamt9(amt);
			else if (posicion == 10) this.gerBP.setamt10(amt);
			else if (posicion == 11) this.gerBP.setamt11(amt);
			else if (posicion == 12) this.gerBP.setamt12(amt);
			else if (posicion == 13) this.gerBP.setamt13(amt);
			
			this.gerBP.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	
	/***
	 * Actualiza datos del detalle de un socio de negocio.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateGLRow(int cPeriodID, BigDecimal amt) {
		
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
			
			if (posicion == 1) this.gerJournal.setamt1(amt); 
			else if (posicion == 2) this.gerJournal.setamt2(amt);
			else if (posicion == 3) this.gerJournal.setamt3(amt);
			else if (posicion == 4) this.gerJournal.setamt4(amt);
			else if (posicion == 5) this.gerJournal.setamt5(amt);
			else if (posicion == 6) this.gerJournal.setamt6(amt);
			else if (posicion == 7) this.gerJournal.setamt7(amt);
			else if (posicion == 8) this.gerJournal.setamt8(amt);
			else if (posicion == 9) this.gerJournal.setamt9(amt);
			else if (posicion == 10) this.gerJournal.setamt10(amt);
			else if (posicion == 11) this.gerJournal.setamt11(amt);
			else if (posicion == 12) this.gerJournal.setamt12(amt);
			else if (posicion == 13) this.gerJournal.setamt13(amt);
			
			this.gerJournal.saveEx();
			
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
