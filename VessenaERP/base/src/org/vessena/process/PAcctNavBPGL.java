/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 07/11/2014
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
import org.openup.model.MAcctNavCC;
import org.openup.model.MAcctNavCCAccount;
import org.openup.model.MAcctNavCCBP;
import org.openup.model.MAcctNavCCJournal;
import org.openup.model.MAcctNavCCMain;


/**
 * org.openup.process - PAcctNavBPGL OpenUp Ltda. Issue #2978 Description:
 * Proceso para carga de socios de negocio y asientos manuales como detalle del
 * saldo de una determinada cuenta contable.
 * 
 * @author Gabriel Vila - 07/11/2014
 * @see
 */
public class PAcctNavBPGL extends SvrProcess {

	private MAcctNavCCAccount navAccount = null;
	private MAcctNavCCMain navMain = null;
	private MAcctNavCC navCC = null;
	private MAcctNavCCBP navBP = null;
	private MAcctNavCCJournal navJournal = null;

	private static final String TABLA_MOLDE_ACCTNAVCC_BP = "UY_MOLDE_AcctNavCC_BP";
	private static final String TABLA_MOLDE_ACCTNAVCC_GL = "UY_MOLDE_AcctNavCC_GL";

	public HashMap<Integer, Integer> hashPosicionXPeriodo = new HashMap<Integer, Integer>();
	public HashMap<Integer, BigDecimal> hashTasaCambioXPeriodo = new HashMap<Integer, BigDecimal>();

	private boolean hasBP = false, hasGL = false;

	private String idReporte = null;

	/**
	 * Constructor.
	 */
	public PAcctNavBPGL() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare() OpenUp Ltda. Issue #
	 * 
	 * @author Gabriel Vila - 07/11/2014
	 * 
	 * @see
	 */
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
			this.navAccount = new MAcctNavCCAccount(getCtx(), recordID, null);
			this.navMain = new MAcctNavCCMain(getCtx(),this.navAccount.getUY_AcctNavCC_Main_ID(), null);
			this.navCC = new MAcctNavCC(getCtx(),this.navMain.getUY_AcctNavCC_ID(), null);
			this.idReporte = UtilReportes.getReportID(new Long(this.getAD_User_ID()));
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt() OpenUp Ltda. Issue #
	 * 
	 * @author Gabriel Vila - 07/11/2014
	 * 
	 * @see
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try {

			this.deleteData();

			this.setPeriodos();

			this.loadData();

			this.updateData();

			

			
		} catch (Exception e) {
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
									if (this.hasBP) apan.setSelectedTabIndex(4); // select parent first
									else if (this.hasGL) apan.setSelectedTabIndex(7); // select parent first										
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
	 * @author Matias Cabajal - 10/11/2014
	 * @see
	 */
	private void deleteData() {
		try {

			String action = "";

			action = " DELETE FROM " + TABLA_MOLDE_ACCTNAVCC_BP
					+ " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);

			action = " DELETE FROM " + TABLA_MOLDE_ACCTNAVCC_GL
					+ " WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action, null);

			action = " DELETE FROM uy_acctnavcc_bp WHERE uy_acctnavcc_account_id ="
					+ this.navAccount.get_ID();
			DB.executeUpdateEx(action, null);

			action = " DELETE FROM uy_acctnavcc_journal WHERE uy_acctnavcc_account_id ="
					+ this.navAccount.get_ID();
			DB.executeUpdateEx(action, null);

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Setea posiciones por periodo. OpenUp Ltda. Issue #2978
	 * 
	 * @author Matias Carbajal - 11/11/2014
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
	 * @author Matias Carbajal - 11/11/2014
	 * @see
	 */
	private void loadData() {

		String insert = "", sql = "";

		try {

			insert = "INSERT INTO "
					+ TABLA_MOLDE_ACCTNAVCC_BP
					+ " (c_elementvalue_id, c_bpartner_id, c_period_id, idreporte, "
					+ " ad_user_id, saldomn)";

			sql = " select fa.account_id, fa.c_bpartner_id, fa.c_period_id, "
					+ "'"
					+ this.idReporte
					+ "',"
					+ this.getAD_User_ID()
					+ ", "
					+ " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn "
					+ " from fact_acct fa "
					+ " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id "
					+ " where fa.c_period_id >="
					+ this.navCC.getC_Period_ID_From()
					+ " and fa.c_period_id <="
					+ this.navCC.getC_Period_ID_To()
					+ " and fa.ad_table_id not in (select ad_table_id from ad_table where name in('UY_YearEndIntegral','UY_YearEndOpen','UY_YearEndAcumulate','UY_YearEndResult')) "
					+ " and fa.c_bpartner_id is not null "
					+ " and ev.c_element_id in (select c_element_id from c_element where isnaturalaccount='Y' and ad_client_id ="
					+ this.getAD_Client_ID()
					+ ") "
					+ " and fa.account_id ="
					+ this.navAccount.getC_ElementValue_ID()
					+ " and fa.c_activity_id_1 =" + this.navAccount.getC_Activity_ID_1() 
					+ " group by fa.account_id, fa.c_bpartner_id , fa.c_period_id "
					+ " order by fa.account_id, fa.c_bpartner_id, fa.c_period_id asc ";

			DB.executeUpdateEx(insert + sql, null);

			insert = "INSERT INTO "
					+ TABLA_MOLDE_ACCTNAVCC_GL
					+ " (c_elementvalue_id, gl_journal_id, c_period_id, idreporte, "
					+ " ad_user_id, saldomn)";

			sql = " select fa.account_id, journal.gl_journal_id, fa.c_period_id, "
					+ "'"
					+ this.idReporte
					+ "',"
					+ this.getAD_User_ID()
					+ ", "
					+ " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn "
					+ " from fact_acct fa "
					+ " inner join gl_journal journal on fa.record_id = journal.gl_journal_id "
					+ " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id "
					+ " where fa.c_period_id >="
					+ this.navCC.getC_Period_ID_From()
					+ " and fa.c_period_id <="
					+ this.navCC.getC_Period_ID_To()
					+ " and fa.ad_table_id = 224 "
					+ " and ev.c_element_id in (select c_element_id from c_element where isnaturalaccount='Y' and ad_client_id ="
					+ this.getAD_Client_ID()
					+ ") "
					+ " and fa.account_id ="
					+ this.navAccount.getC_ElementValue_ID()
					+ " and fa.c_activity_id_1 =" + this.navAccount.getC_Activity_ID_1()
					+ " group by fa.account_id, journal.gl_journal_id, fa.c_period_id "
					+ " order by fa.account_id, journal.gl_journal_id, fa.c_period_id asc ";

			DB.executeUpdateEx(insert + sql, null);

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/***
	 * Actualiza totales por periodos en detalle de socio de negocio y asientos
	 * simples. OpenUp Ltda. Issue #2978
	 * 
	 * @author Matias Carbajal - 11/11/2014
	 * @see
	 */
	private void updateData() {
		try {

			this.updateBP();

			this.updateJournal();
		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	private void updateBP() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			sql = " SELECT * " + " FROM UY_MOLDE_AcctNavCC_BP "
					+ " WHERE ad_user_id =? "
					+ " ORDER BY c_bpartner_id, c_period_id asc ";

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta y periodo
			int bpartnerID = 0, cPeriodID = 0, seqNo = 0;

			BigDecimal amt = Env.ZERO;

			// Recorro registros y voy actualizando
			while (rs.next()) {

				this.hasBP = true;

				this.showHelp("Procesando SNeg " + rowCount++ + " de "
						+ totalRowCount);

				if (rs.getInt("c_bpartner_id") != bpartnerID) {

					// Si no estoy en primer registro actualizo anterior
					if (rowCount > 1) {
						this.updateBPRow(cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					seqNo++;
					this.insertBPRow(rs.getInt("parent_id"),
							rs.getInt("c_elementvalue_id"),
							rs.getInt("c_bpartner_id"), seqNo);

					bpartnerID = rs.getInt("c_bpartner_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				} else {
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID) {
						this.updateBPRow(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}

				amt = amt.add(rs.getBigDecimal("saldomn"));
			}

			// Ultimo registro
			if (cPeriodID > 0) {
				this.updateBPRow(cPeriodID, amt);
			}

			// Cambio dinamicamente los headers de la grilla (totales segundo
			// nivel) correspondiente a periodos.
			// GridTab gridTab =
			// ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			// this.changePeriodsHeaders(gridTab);

		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	private void updateJournal() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			sql = " SELECT * " + " FROM uy_molde_acctnavcc_gl "
					+ " WHERE ad_user_id =? "
					+ " ORDER BY gl_journal_id, c_period_id asc ";

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta y periodo
			int glJournalID = 0, cPeriodID = 0, seqNo = 0;

			BigDecimal amt = Env.ZERO;

			// Recorro registros y voy actualizando
			while (rs.next()) {

				this.hasGL = true;

				this.showHelp("Procesando Asiento " + rowCount++ + " de "
						+ totalRowCount);

				if (rs.getInt("gl_journal_id") != glJournalID) {

					// Si no estoy en primer registro actualizo anterior
					if (rowCount > 1) {
						this.updateGLRow(cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					seqNo++;
					this.insertGLRow(rs.getInt("parent_id"),
							rs.getInt("c_elementvalue_id"),
							rs.getInt("gl_journal_id"), seqNo);

					glJournalID = rs.getInt("gl_journal_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				} else {
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID) {
						this.updateGLRow(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}

				amt = amt.add(rs.getBigDecimal("saldomn"));
			}

			// Ultimo registro
			if (cPeriodID > 0) {
				this.updateGLRow(cPeriodID, amt);
			}

			// Cambio dinamicamente los headers de la grilla (totales segundo
			// nivel) correspondiente a periodos.
			// GridTab gridTab =
			// ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			// this.changePeriodsHeaders(gridTab);

		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	private void insertGLRow(int parentID, int cElementValueID,int glJournalID, int seqNo) {
		try {

			int processID = MProcess.getProcess_ID("UY_RCtb_AcctNavBPGL", null);

			String parentName = new MElementValue(Env.getCtx(), parentID, null)
					.getName();

			this.navJournal = new MAcctNavCCJournal(getCtx(), 0, null);
			this.navJournal.setUY_AcctNavCC_Account_ID(this.navAccount.get_ID());
			this.navJournal.setC_ElementValue_ID(cElementValueID);
			this.navJournal.setparentname(parentName);
			//this.navJournal.setC_BPartner_ID(cBPartnerID);
			this.navJournal.setGL_Journal_ID(glJournalID);
			this.navJournal.setAD_Process_ID(processID);

			this.navJournal.saveEx();

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

	}

	/***
	 * Actualiza datos del detalle de un socio de negocio. OpenUp Ltda. Issue
	 * #116
	 * 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateBPRow(int cPeriodID, BigDecimal amt) {

		try {

			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);

			// Monto segun tipo de moneda
			// Dolares
			if (this.navCC.getCurrencyType().equalsIgnoreCase("ME")) {
				amt = amt.divide(this.hashTasaCambioXPeriodo.get(cPeriodID), 2,
						RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.navCC.getCurrencyType().equalsIgnoreCase("MM")) {
				amt = amt.divide(this.hashTasaCambioXPeriodo.get(cPeriodID), 2,
						RoundingMode.HALF_UP).divide(new BigDecimal(1000), 2,
						RoundingMode.HALF_UP);
			}

			if (posicion == 1)
				this.navBP.setamt1(amt);
			else if (posicion == 2)
				this.navBP.setamt2(amt);
			else if (posicion == 3)
				this.navBP.setamt3(amt);
			else if (posicion == 4)
				this.navBP.setamt4(amt);
			else if (posicion == 5)
				this.navBP.setamt5(amt);
			else if (posicion == 6)
				this.navBP.setamt6(amt);
			else if (posicion == 7)
				this.navBP.setamt7(amt);
			else if (posicion == 8)
				this.navBP.setamt8(amt);
			else if (posicion == 9)
				this.navBP.setamt9(amt);
			else if (posicion == 10)
				this.navBP.setamt10(amt);
			else if (posicion == 11)
				this.navBP.setamt11(amt);
			else if (posicion == 12)
				this.navBP.setamt12(amt);
			else if (posicion == 13)
				this.navBP.setamt13(amt);

			this.navBP.saveEx();

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/***
	 * Actualiza datos del detalle de un socio de negocio. OpenUp Ltda. Issue
	 * #116
	 * 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateGLRow(int cPeriodID, BigDecimal amt) {

		try {

			// Posicion del periodo
			int posicion = this.hashPosicionXPeriodo.get(cPeriodID);

			// Monto segun tipo de moneda
			// Dolares
			if (this.navCC.getCurrencyType().equalsIgnoreCase("ME")) {
				amt = amt.divide(this.hashTasaCambioXPeriodo.get(cPeriodID), 2,
						RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.navCC.getCurrencyType().equalsIgnoreCase("MM")) {
				amt = amt.divide(this.hashTasaCambioXPeriodo.get(cPeriodID), 2,
						RoundingMode.HALF_UP).divide(new BigDecimal(1000), 2,
						RoundingMode.HALF_UP);
			}

			if (posicion == 1)
				this.navJournal.setamt1(amt);
			else if (posicion == 2)
				this.navJournal.setamt2(amt);
			else if (posicion == 3)
				this.navJournal.setamt3(amt);
			else if (posicion == 4)
				this.navJournal.setamt4(amt);
			else if (posicion == 5)
				this.navJournal.setamt5(amt);
			else if (posicion == 6)
				this.navJournal.setamt6(amt);
			else if (posicion == 7)
				this.navJournal.setamt7(amt);
			else if (posicion == 8)
				this.navJournal.setamt8(amt);
			else if (posicion == 9)
				this.navJournal.setamt9(amt);
			else if (posicion == 10)
				this.navJournal.setamt10(amt);
			else if (posicion == 11)
				this.navJournal.setamt11(amt);
			else if (posicion == 12)
				this.navJournal.setamt12(amt);
			else if (posicion == 13)
				this.navJournal.setamt13(amt);

			this.navJournal.saveEx();

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/***
	 * Despliega avance en ventana splash OpenUp Ltda. Issue #116
	 * 
	 * @author Matias Carbajal- 11/11/2011
	 * @see
	 * @param text
	 */
	private void showHelp(String text) {

		if (this.getProcessInfo().getWaiting() != null) {
			this.getProcessInfo().getWaiting().setText(text);
		}

	}

	/***
	 * Inserta nuevo registro para detalle por socio de negocio. OpenUp Ltda.
	 * Issue #2081
	 * 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType
	 * @param seqNo
	 */
	private void insertBPRow(int parentID, int cElementValueID,
			int cBPartnerID, int seqNo) {

		try {

			int processID = MProcess.getProcess_ID("UY_RCtb_AcctNavProd", null);

			String parentName = new MElementValue(Env.getCtx(), parentID, null)
					.getName();

			int seqNoParent = 0;

			this.navBP = new MAcctNavCCBP(getCtx(), 0, null);
			this.navBP.setUY_AcctNavCC_BP_ID(this.navCC.get_ID());
			this.navBP.setUY_AcctNavCC_BP_ID(this.navMain.get_ID());
			this.navBP.setUY_AcctNavCC_Account_ID(this.navAccount.get_ID());
			this.navBP.setC_ElementValue_ID(cElementValueID);
			this.navBP.setparentname(parentName);
			this.navBP.setC_BPartner_ID(cBPartnerID);
			this.navBP.setAD_Process_ID(processID);
			

			this.navBP.saveEx();
			
			this.navBP.setRecord_ID(this.navBP.get_ID());
			
			this.navBP.saveEx();
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

	}

}
