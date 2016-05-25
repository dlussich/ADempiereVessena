package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MRecuentoHdr extends X_UY_RecuentoHdr implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6990313199007927422L;

	public MRecuentoHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		MRecuentoDef def = (MRecuentoDef) this.getUY_RecuentoDef();

		if (def != null) {
			setUser1_ID(def.getCreatedBy());
			setfecdoc(def.getDateTrx());
		}
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		int docR3 = DB.getSQLValue(null, "SELECT c_doctype_id FROM c_doctype  WHERE docbasetype ='RE3'");
		if (newRecord && getC_DocType_ID() != docR3) {

			if (this.loadLines() < 1) {
				throw new AdempiereException("El documento no tiene lineas");
			}
		}

		return super.afterSave(newRecord, success);
	}

	public MRecuentoHdr(Properties ctx, int UY_RecuentoHdr_ID, String trxName) {
		super(ctx, UY_RecuentoHdr_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MRecuentoHdr(MRecuentoDef def, String trxName) {
		super(def.getCtx(), 0, trxName);

		setDocStatus("DR");
		setDocAction("CO");
		setUser1_ID(def.getCreatedBy());
		setfecdoc(def.getDateTrx());
		setUY_RecuentoDef_ID(def.getUY_RecuentoDef_ID());
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
	public String completeIt() {

		int docR3 = DB.getSQLValue(null, "SELECT c_doctype_id FROM c_doctype  WHERE docbasetype ='RE3'");

		// Si no estoy complentando el recuento 3.
		if (this.getC_DocType_ID() != docR3) {

			// Al completar,verifico que el otro tipo de documento este cerrado
			int recuentoB = crearTercerRecuento();
			// Si el recuento B esta completo
			if (recuentoB > 0) {

				MRecuentoHdr hdr = new MRecuentoHdr((MRecuentoDef) this.getUY_RecuentoDef(), get_TrxName());
				hdr.setC_DocType_ID(docR3);
				hdr.saveEx(this.get_TrxName());

				// Si para el tercer recuento no hay lineas, se borra el mismo.
				if (hdr.loadLinesRecuento3(this.get_ID(), recuentoB) < 1) {

					hdr.delete(true, get_TrxName());
				}

			}
		}
		this.setDocStatus("CO");
		this.setDocAction("--");
		this.setProcessed(true);
		this.saveEx();
		return STATUS_Completed;
	}

	private int crearTercerRecuento() {

		return DB.getSQLValue(get_TrxName(), "SELECT uy_recuentohdr_id FROM uy_recuentohdr WHERE docstatus='CO' AND uy_recuentodef_id="
				+ this.getUY_RecuentoDef_ID() + " AND uy_recuentohdr_id!=" + this.get_ID());

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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	public int loadLines() {

		int sequenceID = DB.getSQLValue(null, "SELECT ad_sequence_id FROM ad_sequence WHERE name ='UY_RecuentoLine'");
		int usuarioID = Env.getAD_User_ID(Env.getCtx());

		String SQL = "INSERT INTO UY_RecuentoLine SELECT nextid(" + sequenceID + ",'N')," + this.getAD_Client_ID() + "," + this.getAD_Org_ID() + ",'Y',"
				+ "now()," + usuarioID + ",now()," + usuarioID + ", m_product_id," + this.getUY_RecuentoHdr_ID()
				//OpenUp Nicolas Sarlabos #983 21/02/2012,se inserta categoria y famila de producto
				+ ", m_warehouse_id,m_locator_id,name,value2,0,0,0,'',m_product_category_id,uy_familia_id FROM UY_RecuentoDefProd WHERE UY_RecuentoDef_ID="
				+ this.getUY_RecuentoDef_ID();

		return DB.executeUpdateEx(SQL, get_TrxName());

	}

	public int loadLinesRecuento3(int recuentoA, int recuentoB) {

		int sequenceID = DB.getSQLValue(null, "SELECT ad_sequence_id FROM ad_sequence WHERE name ='UY_RecuentoLine'");
		int usuarioID = Env.getAD_User_ID(Env.getCtx());

		MRecuentoDef def = (MRecuentoDef) this.getUY_RecuentoDef();
		int porciento = def.getporcento();

		String SQL = "INSERT INTO UY_RecuentoLine SELECT nextid("
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
				+ ", a.m_product_id,"
				+ this.get_ID()
				+ ", a.m_warehouse_id,a.m_locator_id,a.name,a.value2,0,0,0,"
				+ "	CASE WHEN (a.qty_approved!= b.qty_approved OR a.qty_quarantine!=b.qty_quarantine OR a.qty_blocked !=b.qty_blocked )"
				+ "	THEN 'Ajuste por diferencia Recuento 1 y 2' ELSE 'Ajuste por porcentaje definido' END, a.m_product_category_id,a.uy_familia_id "//OpenUp Nicolas Sarlabos #983 21/02/2012,se inserta categoria y famila de producto
				+ "FROM uy_recuentoline a "
				+ "	LEFT JOIN uy_recuentoline b ON  b.uy_recuentohdr_id="
				+ recuentoA
				+ " AND b.m_product_id=a.m_product_id "
				+ "	JOIN uy_recuentodefprod prod ON prod.m_product_id=a.m_product_id AND prod.uy_recuentodef_id="
				+ this.getUY_RecuentoDef_ID()
				+ " WHERE a.uy_recuentohdr_id="
				+ recuentoB
				+ " AND  "
				+ "(a.qty_approved!= b.qty_approved OR a.qty_quarantine!=b.qty_quarantine OR  a.qty_blocked !=b.qty_blocked OR "
				+ "(round((a.qty_approved-prod.qty_approved)* 100/(CASE WHEN prod.qty_approved=0 THEN 1 ELSE prod.qty_approved END),2)NOT between -"
				+ porciento
				+ " AND "
				+ porciento
				+ ")OR(round((a.qty_quarantine -prod.qty_quarantine)* 100/(CASE WHEN prod.qty_quarantine=0 THEN 1 ELSE prod.qty_quarantine END),2)NOT between -"
				+ porciento + " AND " + porciento
				+ ")OR(round((a.qty_blocked -prod.qty_blocked )* 100/(CASE WHEN prod.qty_blocked =0 THEN 1 ELSE prod.qty_blocked END),2)NOT between -"
				+ porciento + " AND " + porciento + "))";

		return DB.executeUpdateEx(SQL, get_TrxName());

	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

}
