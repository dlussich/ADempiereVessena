package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.util.DB;

public class MRecuentoDef extends X_UY_RecuentoDef implements DocAction {

	private static final long serialVersionUID = -5718931396409446756L;

	public MRecuentoDef(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}



	public MRecuentoDef(Properties ctx, int UY_RecuentoDef_ID, String trxName) {
		super(ctx, UY_RecuentoDef_ID, trxName);
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
	public String completeIt() {

		ActualizarLineas();

		int docR1 = DB.getSQLValue(null, "SELECT c_doctype_id FROM c_doctype  WHERE docbasetype ='RE1'");
		int docR2 = DB.getSQLValue(null, "SELECT c_doctype_id FROM c_doctype  WHERE docbasetype ='RE2'");

		MRecuentoHdr hdr = new MRecuentoHdr(getCtx(), 0, get_TrxName());
		hdr.setC_DocType_ID(docR1);
		hdr.setDocStatus("DR");
		hdr.setDocAction("CO");
		hdr.setUser1_ID(this.getCreatedBy());
		hdr.setfecdoc(this.getCreated());
		hdr.setUY_RecuentoDef_ID(this.get_ID());

		hdr.saveEx(this.get_TrxName());
		
		hdr = new MRecuentoHdr(getCtx(), 0, get_TrxName());
		hdr.setC_DocType_ID(docR2);
		hdr.setDocStatus("DR");
		hdr.setDocAction("CO");
		hdr.setUser1_ID(this.getCreatedBy());
		hdr.setfecdoc(this.getCreated());
		hdr.setUY_RecuentoDef_ID(this.get_ID());

		hdr.saveEx(this.get_TrxName());



		this.setDocStatus("CO");
		this.setDocAction("--");
		this.setProcessed(true);
		this.saveEx();
		return DOCSTATUS_Completed;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Issue 889 Actualizo los registros de las lineas que fueron ingresados a mano.
	 * @author  Nicolas Garcia
	 * Fecha : 05/10/2011
	 */
	private void ActualizarLineas() {

		DB.executeUpdateEx("UPDATE uy_recuentodefprod SET "
				+ "value2= (SELECT m_Product.value FROM m_Product WHERE m_product_id=uy_recuentodefprod.m_product_id),"
				+ "name= (SELECT m_Product.name FROM m_Product WHERE m_product_id=uy_recuentodefprod.m_product_id),"
								+ " qty_blocked=COALESCE((SELECT COALESCE(l.qty ,0) FROM stk_lock_prodxwarxloc l WHERE l.m_product_id=uy_recuentodefprod.m_product_id AND l.m_locator_id=uy_recuentodefprod.m_locator_id),0) ,"
								+ " qty_quarantine=COALESCE((SELECT COALESCE(l.qty ,0) FROM stk_quar_prodxwarxloc l WHERE l.m_product_id=uy_recuentodefprod.m_product_id AND l.m_locator_id=uy_recuentodefprod.m_locator_id),0) ,"
								+ " qty_approved=COALESCE((SELECT COALESCE(l.qty ,0) FROM stk_app_prodxwarxloc l WHERE l.m_product_id=uy_recuentodefprod.m_product_id AND l.m_locator_id=uy_recuentodefprod.m_locator_id),0) "
								+ "WHERE value2 is null", get_TrxName());
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
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		return true;
	}


	@Override
	public boolean reActivateIt() {

		int cont = DB.getSQLValue(null, "SELECT Count(uy_recuentohdr_id) FROM uy_recuentohdr WHERE uy_recuentodef_id=" + get_ID());

		if (cont > 0) {
			throw new AdempiereException("Hay Recuentos asociados a esta definicion");
		}

		setProcessed(true);
		setDocStatus(DocAction.STATUS_InProgress);
		setDocAction(DocAction.ACTION_Complete);
		this.saveEx();
		return true;
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
		return true;
	}

	@Override
	public boolean voidIt() {

		int cont = DB.getSQLValue(null, "SELECT Count(uy_recuentohdr_id) FROM uy_recuentohdr WHERE uy_recuentodef_id=" + get_ID());

		if (cont > 0) {
			throw new AdempiereException("Hay Recuentos asociados a esta definicion");
		}

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		this.saveEx();
		return true;
	}



	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

}
