/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 28/11/2014
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.util.DB;

/**
 * org.openup.model - MOrderFlete
 * OpenUp Ltda. Issue #3205 
 * Description: Modelo de linea donde gestionar las ordenes de compra flete a matar en un documento de Factura de Vale Flete
 * @author Gabriel Vila - 28/11/2014
 * @see
 */
public class MOrderFlete extends X_UY_Order_Flete {

	private static final long serialVersionUID = -5429275262988238537L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Order_Flete_ID
	 * @param trxName
	 */
	public MOrderFlete(Properties ctx, int UY_Order_Flete_ID, String trxName) {
		super(ctx, UY_Order_Flete_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MOrderFlete(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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
				throw new AdempiereException("El monto a afectar es mayor al saldo de la orden de compra flete.");
			}

			if ( (newRecord) || ((!newRecord) && (is_ValueChanged(COLUMNNAME_C_Order_ID)))){

				String message = this.controlarRepetidos(); 
				
				if (message != null) throw new AdempiereException(message);
			}
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}

		return true;
	}

	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return false;
		
		this.updateHeader();
		
		return true;

	}

	@Override
	protected boolean afterDelete(boolean success) {

		if (!success) return false;
		
		this.updateHeader();
		
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

			// Si me trae alguna fila es porque ya existe esa orden de compra flete en la factura 
			sql = "SELECT c_order_id, c_invoice_id "
				+ " FROM uy_order_flete"
				+ " WHERE c_order_id= ?"
				+ " AND c_invoice_id= ?";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.getC_Order_ID());
			pstmt.setInt(2, this.getC_Invoice_ID());
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				respuesta = "Ese número de orden de compra flete ya esta ingresado en este documento.";
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return respuesta;
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
					+ " (SELECT COALESCE(SUM(round(l.amtallocated / coalesce(l.currencyrate,1),2)),0) FROM uy_order_flete l WHERE i.C_Invoice_ID=l.C_Invoice_ID) + "
					+ " (SELECT COALESCE(SUM(round(il.linenetamt / coalesce(il.currencyrate,1),2)),0) FROM c_invoiceline il WHERE i.C_Invoice_ID=il.C_Invoice_ID)) "
					+ " WHERE C_Invoice_ID=?";
			
			DB.executeUpdateEx(sql, new Object[]{this.getC_Invoice_ID()}, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
}
