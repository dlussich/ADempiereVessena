package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MPayment;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MMovBancariosLine extends X_UY_MovBancariosLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8762515735294627705L;

	public MMovBancariosLine(Properties ctx, int UY_MovBancariosLine_ID,
			String trxName) {

		super(ctx, UY_MovBancariosLine_ID, trxName);
		
		if (UY_MovBancariosLine_ID == 0)
		{
			setDocStatus(MPayment.DOCSTATUS_Drafted);
			setuy_capitalamt(Env.ZERO);
			setuy_interesesamt(Env.ZERO);
			setuy_totalamt(Env.ZERO);
			setDateTrx (new Timestamp(System.currentTimeMillis()));
			setDateAcct (this.getDateTrx());
			setDueDate(this.getDateTrx());
			setProcessed(false);
		}
	}

	public MMovBancariosLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}


	//OpenUp M.R. 06-09-2011 se crea metodo para traer el mediopago desde la linea del movbancario
	public static MMovBancariosLine getFromMedioPago(int uyMediosPagoID, String trxName) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		MMovBancariosLine value = null;

		try {
			sql = "SELECT uy_movbancariosline_id " + " FROM " + X_UY_MovBancariosLine.Table_Name + " WHERE uy_mediospago_id =?";

			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, uyMediosPagoID);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = new MMovBancariosLine(Env.getCtx(), rs.getInt(1), trxName);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return value;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// Obtengo modelo del cabezal
		MMovBancariosHdr hdr = new MMovBancariosHdr(getCtx(), this.getUY_MovBancariosHdr_ID(), get_TrxName());

		// Setea tendertype segun tipo que tiene la forma de pago
		MPaymentRule rule = new MPaymentRule(getCtx(), this.getUY_PaymentRule_ID(), null);
		if (rule.get_ID() > 0){
			if (rule.getpaymentruletype().equalsIgnoreCase(X_UY_PaymentRule.PAYMENTRULETYPE_CONTADO)){
				this.setTenderType(TENDERTYPE_Cash);
			}
			else{
				this.setTenderType(TENDERTYPE_Check);
			}
		}
				
		if (newRecord){
			// Si no tengo padre, aviso con exception
			if (hdr.get_ID() <= 0) 
				throw new AdempiereException("Debo indicar ID del cabezal de esta linea de transaccion bancaria.");
			
			this.setDateTrx(hdr.getDateTrx());
			this.setDateAcct(hdr.getDateAcct());
			if (this.getDueDate() == null) this.setDueDate(this.getDateTrx());
		}
		
		//OpenUp. Nicolas Sarlabos. 15/08/2013. #1224
		MDocType doc = new MDocType(getCtx(),hdr.getC_DocType_ID(),null);

		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("camcheqter")){
				// Valido que me hayan digitado numero de cheque
				if ((this.getCheckNo() == null) || (this.getCheckNo().trim().equalsIgnoreCase(""))){
					throw new AdempiereException("Debe ingresar Numero de Cheque.");
				}
			//OpUp. Nicolas Sarlabos. 22/02/2016. #5264.	
			} else if (doc.getValue().equalsIgnoreCase("depticket")){
				
				MCashConfig conf = MCashConfig.forClient(getCtx(), get_TrxName());
				
				if(conf==null) throw new AdempiereException("No se obtuvieron parametros de caja para la empresa actual");
							
				if(this.get_ValueAsInt("QtyCount")!=conf.getCantTickets()) 
					throw new AdempiereException("Cantidad de tickets debe ser " + conf.getCantTickets());				
				
				if(this.getuy_totalamt().compareTo(Env.ZERO)<=0) throw new AdempiereException("Importe debe ser mayor a cero");			
				
			}
			//Fin OpUp. #5264.
		}
		//Fin OpenUp. #1224

		return true;
	}

	/***
	 * Actualizo cabezal segun documento
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 6, 2015
	 */
	private void updateHeaderAmt(){
		
		String action = "";
		
		try {
			MMovBancariosHdr header = (MMovBancariosHdr)this.getUY_MovBancariosHdr();
			MDocType doc = (MDocType)header.getC_DocType();
			
			if (doc.getValue() == null) return;
			if (doc.getValue().equalsIgnoreCase("")) return;
			
			if ((doc.getValue().equalsIgnoreCase("bcodepcheqpropio")) || (doc.getValue().equalsIgnoreCase("bcodepcheqtercero"))){
				action = " update uy_movbancarioshdr set uy_total_manual = (select sum(coalesce(uy_totalamt,0)) from uy_movbancariosline where uy_movbancarioshdr_id =" +
						 header.get_ID() + ") where uy_movbancarioshdr_id = " + header.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;
		this.updateHeaderAmt();
		
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {

		if (!success) return success;
		this.updateHeaderAmt();
		
		return true;

	}

	
}
