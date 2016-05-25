/**
 * 
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.util.DB;

/**
 * @author Matias
 * Issue #3205
 */
public class MInvoiceFlete extends X_UY_Invoice_Flete {

	/**
	 * 
	 */
	private static final long serialVersionUID = 18922199117865508L;

	/**
	 * @param ctx
	 * @param UY_Invoice_Flete_ID
	 * @param trxName
	 */
	public MInvoiceFlete(Properties ctx, int UY_Invoice_Flete_ID, String trxName) {
		super(ctx, UY_Invoice_Flete_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoiceFlete(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		try {

			MProduct prod = MProduct.forValue(getCtx(), "invflete", null);
			if ((prod == null) || (prod.get_ID()<=0)){
				throw new AdempiereException("Falta parametrizar producto: invflete.");
			}
			
			this.setM_Product_ID(prod.get_ID());
			
			if (this.getamtallocated().compareTo(this.getamtopen()) > 0){
				throw new AdempiereException("El monto a afectar es mayor al saldo del vale flete.");
			}

			if ((!newRecord) && (is_ValueChanged(COLUMNNAME_C_Invoice_ID_Flete))){

				String message = this.controlarRepetidos(); 
				
				if (message != null) throw new AdempiereException(message);
			}
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}

		return true;
	}

	/***
	 * Controla que el usuario no elija dos veces el mismo flete en este documento.
	 * OpenUp Ltda. Issue #3205 
	 * @author Gabriel Vila - 11/11/2014
	 * @see
	 * @return
	 */
	private String controlarRepetidos() {
		
		String respuesta = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// Si me trae alguna fila es porque ya existe ese flete vale con en la factura 
			sql = "SELECT c_invoice_id_flete, c_invoice_id "
				+ " FROM uy_invoice_flete"
				+ " WHERE c_invoice_id_flete= ?"
				+ " AND c_invoice_id= ?";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.getC_Invoice_ID_Flete());
			pstmt.setInt(2, this.getC_Invoice_ID());
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				respuesta = "Ese número de vale flete ya esta ingresado en este documento.";
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return respuesta;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (!success) return false;
		
		this.updateHeader();
		
		return true;
	}


	/***
	 * Actualiza totales del documento
	 * OpenUp Ltda. Issue #3205 
	 * @author Gabriel Vila - 13/11/2014
	 * @see
	 */
	private void updateHeader() {
		
		try {
			
			String sql = "UPDATE C_Invoice i"
					+ " SET AmtAux="
						+ "((SELECT COALESCE(SUM(round(l.amtallocated / coalesce(l.currencyrate,1),2)),0) FROM uy_invoice_flete l WHERE i.C_Invoice_ID=l.C_Invoice_ID) + "
					+ " (SELECT COALESCE(SUM(round(il.linenetamt / coalesce(il.currencyrate,1),2)),0) FROM c_invoiceline il WHERE i.C_Invoice_ID=il.C_Invoice_ID)) "
					+ " WHERE C_Invoice_ID=?";
			
			DB.executeUpdateEx(sql, new Object[]{this.getC_Invoice_ID()}, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	@Override
	protected boolean afterDelete(boolean success) {

		if (!success) return false;
		
		this.updateHeader();
				
		return true;
	}
	
	
	
}
