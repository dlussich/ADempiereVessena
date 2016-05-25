package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.APanel;
import org.compiere.apps.AWindow;
import org.compiere.model.MInvoice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MAcctNavCC;
import org.openup.model.MAcctNavCCAccount;
import org.openup.model.MAcctNavCCProd;
import org.openup.model.MAcctNav;
import org.openup.model.MAcctNavAccount;
import org.openup.model.MAcctNavCCBP;
import org.openup.model.MAcctNavCCMain;
import org.openup.model.MAcctNavCCProdDet;
import org.openup.model.MGerencialProdDet;


/**
 * org.openup.process - PAcctNavProdDet
 * OpenUp Ltda. Issue #2978
 * Description: Dado un producto carga el detalle.
 * @author Matias Carbajal - 19/11/2014
 * @see
 */
public class PAcctNavProdDet extends SvrProcess{
	
	private MAcctNavCCMain navMain = null;
	private MAcctNavCCAccount navAccount = null;
	private MAcctNavCCBP navBP = null;
	private MAcctNavCCProd navProd = null;
	private MAcctNavCC navCC = null;
	
	private String idReporte = null;
	
	private static final String TABLA_MOLDE_NAV_PRODDET = "UY_MOLDE_AcctNavCC_ProdDet";
	
	/**
	 * 
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
		
			this.navProd = new MAcctNavCCProd(getCtx(), recordID, null);
			this.navBP = (MAcctNavCCBP)this.navProd.getUY_AcctNavCC_BP();
			this.navAccount = new MAcctNavCCAccount(getCtx(), this.navBP.getUY_AcctNavCC_Account_ID(), null);
			this.navMain = new MAcctNavCCMain(getCtx(),this.navAccount.getUY_AcctNavCC_Main_ID(), null);
			this.navCC = new MAcctNavCC(getCtx(),this.navMain.getUY_AcctNavCC_ID(), null);
			
			this.idReporte = UtilReportes.getReportID(new Long(this.getAD_User_ID()));
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}

		
	}

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
						if (awin.getAD_Window_ID() == 1000217){
							if (awin.getWindowNo() > 0){
								APanel apan = awin.getAPanel();
								if (apan != null){
									apan.setSelectedTabIndex(6); // select parent first	
								}
							}
						}
					}
				}
			}
			
						
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return "OK";
	}

	private void deleteData() {
		try{

			String action = " DELETE FROM " + TABLA_MOLDE_NAV_PRODDET +
					 		" WHERE ad_user_id =" + this.getAD_User_ID();
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM uy_acctnavcc_proddet " + 
					 " WHERE uy_acctnavcc_main_id =" + this.navMain.get_ID();
			DB.executeUpdateEx(action,null);
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}	
		
	}

	private void loadData() {
String insert = "", sql = "";
		
		try {
			
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_PRODDET + " (c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id, idreporte, " +
					" ad_user_id, ad_table_id, record_id, c_doctype_id, documentno, dateacct, saldomn, qty)";

			this.showHelp("Detalles Producto...");
			
			sql = " select fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + this.getAD_User_ID() + ", " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct, " +
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn, " +
				  " sum(coalesce(fa.qty,1)) as cantidad " +
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
				  " and fa.m_product_id =" + this.navProd.getM_Product_ID() +
				  " group by fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id," +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct " +
				  " order by fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}	
		
	}

	private void updateData() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_NAV_PRODDET + 
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

				MAcctNavCCProdDet det = new MAcctNavCCProdDet(getCtx(), 0, null);
				det.setUY_AcctNavCC_Prod_ID(this.navProd.get_ID());
				det.setUY_AcctNavCC_ID(this.navProd.getUY_AcctNavCC_ID());
				det.setUY_AcctNavCC_Main_ID(this.navProd.getUY_AcctNavCC_Main_ID());
				det.setUY_AcctNavCC_Account_ID(this.navProd.getUY_AcctNavCC_Account_ID());
				det.setC_ElementValue_ID(this.navProd.getC_ElementValue_ID());
				det.setparentname(this.navProd.getparentname());
				det.setacctname(this.navProd.getacctname());
				det.setC_BPartner_ID(this.navProd.getC_BPartner_ID());
				det.setM_Product_ID(this.navProd.getM_Product_ID());
				det.setAccountType(this.navProd.getAccountType());
				det.setAD_Table_ID(rs.getInt("ad_table_id"));
				det.setRecord_ID(rs.getInt("record_id"));
				det.setC_DocType_ID(rs.getInt("c_doctype_id"));
				det.setDocumentNo(rs.getString("documentno"));
				det.setDateAcct(rs.getTimestamp("dateacct"));
				det.setC_Period_ID(rs.getInt("c_period_id"));
				det.setAmt(rs.getBigDecimal("saldomn"));
				
				
				
				BigDecimal qty = rs.getBigDecimal("qty");
				if (qty.compareTo(Env.ZERO) == 0) qty = Env.ONE;
				det.setQty(qty);
				
				// Para facturas tengo que obtener la cantidad directamente desde el comprobante y no desde la contabilidad
				if (det.getAD_Table_ID() == 318){
					qty = MInvoice.getProductTotalQty(getCtx(), det.getRecord_ID(), det.getM_Product_ID(), null);
					if (qty != null){
						if (qty.compareTo(Env.ZERO) == 0) qty = Env.ONE;
						det.setQty(qty);
					}
				}
				
				BigDecimal price = det.getAmt().divide(det.getQty(), 3, RoundingMode.HALF_UP);
				det.setPrice(price);
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
