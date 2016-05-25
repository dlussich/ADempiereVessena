/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 19/04/2014
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.APanel;
import org.compiere.apps.AWindow;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MGerencial;
import org.openup.model.MGerencialAccount;
import org.openup.model.MGerencialCC;
import org.openup.model.MGerencialCCDet;
import org.openup.model.MGerencialJournal;
import org.openup.model.MGerencialMain;

/**
 * org.openup.process - InformeGerencialCCDet
 * OpenUp Ltda. Issue #2081 
 * Description: Detalle de centro de costo por asiento simple
 * @author Gabriel Vila - 19/04/2014
 * @see
 */
public class InformeGerencialCCDet extends SvrProcess {

	private MGerencialMain gerMain = null;
	private MGerencial gerencial = null;
	private MGerencialAccount gerAccount = null;
	private MGerencialJournal gerJournal = null;
	private MGerencialCC gerCC = null;
	
	private String idReporte = null;
	
	private static final String TABLA_MOLDE_NAV_CCDET = "UY_MOLDE_InformeGerencial_9";

	
	
	/**
	 * Constructor.
	 */
	public InformeGerencialCCDet() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 19/04/2014
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
		
			this.gerCC = new MGerencialCC(getCtx(), recordID, null);
			this.gerJournal = (MGerencialJournal)this.gerCC.getUY_Gerencial_Journal();
			this.gerAccount = (MGerencialAccount)this.gerJournal.getUY_Gerencial_Account(); 
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
	 * @author Gabriel Vila - 19/04/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		this.deleteData();
		
		this.loadData();
		
		this.updateData();
		
		try {
			for (int i = 0; i < AWindow.getWindows().length; i++){
				if (AWindow.getWindows()[i] != null){
					if (AWindow.getWindows()[i] instanceof AWindow){
						AWindow awin = (AWindow)AWindow.getWindows()[i];
						if (awin.getAD_Window_ID() == 1000219){
							if (awin.getWindowNo() > 0){
								APanel apan = awin.getAPanel();
								if (apan != null){
									apan.setSelectedTabIndex(8); // select parent first	
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

			String action = " DELETE FROM " + TABLA_MOLDE_NAV_CCDET +
					 		" WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM uy_gerencial_ccdet " + 
					 " WHERE uy_gerencial_main_id =" + this.gerMain.get_ID();
			DB.executeUpdateEx(action,null);
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
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
			
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_CCDET + " (parent_id, c_elementvalue_id, gl_journal_id, c_activity_id_1, c_period_id, idreporte, " +
					" ad_user_id, ad_table_id, record_id, c_doctype_id, documentno, dateacct, saldomn) ";
			
			this.showHelp("Detalles Departamento...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + this.getAD_User_ID() + ", " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct, " +					
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join gl_journal journal on fa.record_id = journal.gl_journal_id " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.gerencial.getC_Period_ID_From() + " and fa.c_period_id <=" + this.gerencial.getC_Period_ID_To() +
				  " and fa.ad_table_id = 224 " +
				  " and fa.c_activity_id_1 is not null " +
			   // " and evig.c_element_id = 1000008 " + ---ORIGINAL SBT 20022015
				  " and evig.c_element_id = " + this.gerencial.get_ValueAsInt("C_Element_ID")+ // reemplazo ORIGINAL 20022015
				  " and evig.c_elementvalue_id = " + this.gerMain.getC_ElementValue_ID() +				  
				  " and fa.account_id =" + this.gerAccount.getC_ElementValue_ID() +
				  " and fa.record_id =" + this.gerJournal.getGL_Journal_ID() +
				  " and fa.c_activity_id_1 = " + this.gerCC.getC_Activity_ID_1() +
				  " group by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct " +				  
				  " order by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, fa.dateacct asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Actualiza información de detalle de cuentas ahora abriendola en periodos horizontales.
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
				  " FROM " + TABLA_MOLDE_NAV_CCDET + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY dateacct asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Detalle Prod. " + rowCount++ + " de " + totalRowCount);

				MGerencialCCDet det = new MGerencialCCDet(getCtx(), 0, null);
				det.setUY_Gerencial_ID(this.gerCC.getUY_Gerencial_ID());
				det.setUY_Gerencial_Main_ID(this.gerCC.getUY_Gerencial_Main_ID());
				det.setUY_Gerencial_Account_ID(this.gerCC.getUY_Gerencial_Account_ID());
				det.setParent_ID(this.gerCC.getParent_ID());
				det.setC_ElementValue_ID(this.gerCC.getC_ElementValue_ID());
				det.setparentname(this.gerCC.getparentname());
				det.setacctname(this.gerCC.getacctname());
				det.setGL_Journal_ID(this.gerCC.getGL_Journal_ID());
				det.setC_Activity_ID_1(this.gerCC.getC_Activity_ID_1());
				det.setAccountType(this.gerCC.getAccountType());
				det.setUY_Gerencial_CC_ID(this.gerCC.get_ID());
				det.setAD_Table_ID(rs.getInt("ad_table_id"));
				det.setRecord_ID(rs.getInt("record_id"));
				det.setC_DocType_ID(rs.getInt("c_doctype_id"));
				det.setDocumentNo(rs.getString("documentno"));
				det.setDateAcct(rs.getTimestamp("dateacct"));
				det.setC_Period_ID(rs.getInt("c_period_id"));
				det.setAmt(rs.getBigDecimal("saldomn"));
				det.saveEx();
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
