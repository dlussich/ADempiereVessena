package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AWindow;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MRecuentoConf extends X_UY_RecuentoConf implements DocAction {

	private static final long serialVersionUID = -2355844264568963730L;
	private String processMsg;
	private boolean justPrepared;
	private AWindow window;

	public MRecuentoConf(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MRecuentoConf(Properties ctx, int UY_RecuentoConf_ID, String trxName) {
		super(ctx, UY_RecuentoConf_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (newRecord) {
			if (!loadLines()) {
				return false;
			}
		}
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		MRecuentoDef def = new MRecuentoDef(getCtx(), this.getUY_RecuentoDef_ID(), null);

		if (hayRecuentosDR()) {
			throw new AdempiereException("Los recuentos asociados a la definicion " + def.getDocumentNo() + " no estan completos");

		}

		this.setUser1_ID(def.getCreatedBy());

		return super.beforeSave(newRecord);
	}

	private boolean hayRecuentosDR() {

		int control = DB.getSQLValue(null, "SELECT COUNT(uy_recuentohdr_id) FROM uy_recuentohdr WHERE docstatus='DR' AND uy_recuentodef_id="
				+ this.getUY_RecuentoDef_ID());

		if (control > 0) {
			return true;
		}

		return false;
	}

	@Override
	public String completeIt() {

		// Re-Check
		if (!this.justPrepared) {
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status)) return status;
		}

		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null) throw new AdempiereException(processMsg);
		;

		// Timing After Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null) throw new AdempiereException(processMsg);

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessed(true);
		this.saveEx();
		return DocAction.STATUS_Completed;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction())) setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null) return false;

		if (DocAction.STATUS_Closed.equals(getDocStatus()) || DocAction.STATUS_Reversed.equals(getDocStatus())
				|| DocAction.STATUS_Voided.equals(getDocStatus())) {
			this.processMsg = "Document Closed: " + getDocStatus();
			setDocAction(DocAction.ACTION_None);
			return false;
		}

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null) throw new AdempiereException(processMsg);

		DB.executeUpdateEx("Update uy_recuentodef SET docstatus='VO' WHERE uy_recuentodef_id=" + this.getUY_RecuentoDef_ID(), get_TrxName());
		DB.executeUpdateEx("Update uy_recuentohdr SET docstatus='VO' WHERE uy_recuentodef_id=" + this.getUY_RecuentoDef_ID(), get_TrxName());

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		this.saveEx();
		return true;
	}

	private boolean loadLines() {

		// Borro instancias viejas
		// DB.executeUpdate("DELETE FROM  UY_RecuentoConfLine WHERE  UY_RecuentoConf_ID="
		// + this.get_ID(), this.get_TrxName());

		// Busco los cabezales
		int docR1 = DB.getSQLValue(null, "SELECT c_doctype_id FROM c_doctype  WHERE docbasetype ='RE1'");
		int docR2 = DB.getSQLValue(null, "SELECT c_doctype_id FROM c_doctype  WHERE docbasetype ='RE2'");
		int docR3 = DB.getSQLValue(null, "SELECT c_doctype_id FROM c_doctype  WHERE docbasetype ='RE3'");

		if (docR1 < 1 || docR2 < 1 || docR3 < 1) {
			throw new AdempiereException("No se encuentra definido los tipos de documento, Pongase en contacto con un administrador.");
		}

		int R1 = DB.getSQLValue(null, "SELECT uy_recuentohdr_id FROM uy_recuentohdr WHERE docstatus='CO' AND c_doctype_id=" + docR1 + " AND uy_recuentodef_id="
				+ this.getUY_RecuentoDef_ID());
		int R2 = DB.getSQLValue(null, "SELECT uy_recuentohdr_id FROM uy_recuentohdr WHERE docstatus='CO' AND c_doctype_id=" + docR2 + " AND uy_recuentodef_id="
				+ this.getUY_RecuentoDef_ID());
		int R3 = DB.getSQLValue(null, "SELECT uy_recuentohdr_id FROM uy_recuentohdr WHERE docstatus='CO' AND c_doctype_id=" + docR3 + " AND uy_recuentodef_id="
				+ this.getUY_RecuentoDef_ID());

		int sequenceID = DB.getSQLValue(null, "SELECT ad_sequence_id FROM ad_sequence WHERE name ='UY_RecuentoConfLine'");
		int usuarioID = Env.getAD_User_ID(Env.getCtx());

		String InsertSQL = "INSERT INTO UY_RecuentoConfLine SELECT nextid("
				+ sequenceID
				+ ",'N'),"
				+ this.getAD_Client_ID()
				+ ","
				+ this.getAD_Org_ID()
				+ ",'Y',"
				+ "now(),"
				+ usuarioID
				+ ",now(),"
				+ usuarioID
				+ ",defLine.m_product_id,"
				+ this.get_ID()
				+ ",defLine.m_warehouse_id,defLine.m_locator_id,defLine.name,defLine.value2,R1.uy_recuentohdr_id,R1.qty_quarantine,R1.qty_blocked,R1.qty_approved,R2.uy_recuentohdr_id,"
				+ "R2.qty_quarantine,R2.qty_blocked,R2.qty_approved,R3.uy_recuentohdr_id,R3.qty_quarantine,R3.qty_blocked,R3.qty_approved,R3.description, "
				+ "COALESCE(R3.qty_approved,R1.qty_approved)-defLine.qty_approved,COALESCE(R3.qty_quarantine,R1.qty_quarantine)-defLine.qty_quarantine "
				+ ",COALESCE(R3.qty_blocked,R1.qty_blocked)-defLine.qty_blocked,defLine.qty_approved,defLine.qty_quarantine,defLine.qty_blocked,"
				+ "defline.m_product_category_id,defline.uy_familia_id" //OpenUp Nicolas Sarlabos #983 21/02/2012,se inserta categoria y famila de producto
				+ " FROM uy_recuentodefprod  defLine "
				+ " join uy_recuentoline R1 ON defLine.m_product_id=R1.m_product_id AND defLine.m_locator_id=R1.m_locator_id AND R1.uy_recuentohdr_id=" + R1
				+ " join uy_recuentoline R2 ON defLine.m_product_id=R2.m_product_id AND defLine.m_locator_id=R2.m_locator_id AND R2.uy_recuentohdr_id=" + R2
				+ " left join uy_recuentoline R3 ON defLine.m_product_id=R3.m_product_id AND defLine.m_locator_id=R3.m_locator_id AND R3.uy_recuentohdr_id="
				+ R3 + " WHERE defLine.uy_recuentodef_id=" + this.getUY_RecuentoDef_ID();

		DB.executeUpdateEx(InsertSQL, get_TrxName());
		return true;
	}

	public MRecuentoConfLine[] getLines(String trxName) {

		return getLinesToSql("SELECT UY_RecuentoConfLine_ID " + " FROM " + I_UY_RecuentoConfLine.Table_Name + " WHERE uy_recuentoconf_id =" + get_ID(), trxName);
	}

	public MRecuentoConfLine[] getLinesParaAjustar(String trxName) {

		String sql = "SELECT UY_RecuentoConfLine_ID " + " FROM " + I_UY_RecuentoConfLine.Table_Name
				+ " WHERE (qty_approved!=0 OR qty_quarantine!=0 OR qty_blocked!=0) AND uy_recuentoconf_id =" + get_ID();

		return getLinesToSql(sql, trxName);
	}

	private MRecuentoConfLine[] getLinesToSql(String sql, String trxName) {

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MRecuentoConfLine> list = new ArrayList<MRecuentoConfLine>();

		try {

			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MRecuentoConfLine value = new MRecuentoConfLine(Env.getCtx(), rs.getInt("UY_RecuentoConfLine_ID"), trxName);
				list.add(value);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return list.toArray(new MRecuentoConfLine[list.size()]);
	}

	// OpenUp. Gabriel Vila. 05/10/2011. Issue #894.
	// Get - Set de la ventana que puede contener o no al proceso.
	public AWindow getWindow() {
		return this.window;
	}

	public void setWindow(AWindow value) {
		this.window = value;
	}
	// Fin Issue #894.

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
}
