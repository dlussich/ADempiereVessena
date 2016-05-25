/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/10/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MProduct;
import org.compiere.model.X_M_Product_Acct;
import org.compiere.util.DB;

/**
 * org.openup.model - MProvisionLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 05/10/2012
 * @see
 */
public class MProvisionLine extends X_UY_ProvisionLine {

	private static final long serialVersionUID = -3064114438582908286L;

	private MProvision provisionParent = null;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ProvisionLine_ID
	 * @param trxName
	 */
	public MProvisionLine(Properties ctx, int UY_ProvisionLine_ID,
			String trxName) {
		super(ctx, UY_ProvisionLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProvisionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Obtiene y retorna saldo pendiente de esta linea de provision.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 12/10/2012
	 * @see
	 * @return
	 */
	public BigDecimal getAmtOpen(String trxName){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = this.getAmtSourceAverage();
		
		try{
			sql = " SELECT coalesce(amtopen,0) as amtopen " + 
		 		  " FROM alloc_provisionlineamtopen " +
				  " WHERE ad_client_id =?" +
				  " AND uy_provisionline_id =?";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setInt(2, this.get_ID());
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal(1);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;

	}

	/***
	 * Obtiene y retorna cabezal de esta linea de provision.
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 12/10/2012
	 * @see
	 * @return
	 */
	public MProvision getParent() {

		if (this.provisionParent != null) return this.provisionParent;
		return new MProvision(getCtx(), this.getUY_Provision_ID(), get_TrxName());
		
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Si actualizan producto debo traer la cuenta correspondiente al mismo
		if ((!newRecord && is_ValueChanged(COLUMNNAME_M_Product_ID)) ||
				(newRecord && this.getC_ElementValue_ID() <= 0)){
			MProduct prod = new MProduct(getCtx(), this.getM_Product_ID(), null);
			X_M_Product_Acct prodacct = prod.getProductAccounting();
			if (prodacct != null) {
				MAccount cuenta = MAccount.get(getCtx(), prodacct.getP_InventoryClearing_Acct());
				this.setC_ElementValue_ID(cuenta.getAccount().get_ID());
			}
		}
		
		// Si no es nueva linea debo verificar que no este asociada a un factura, ya
		// que se permite reactivar provisiones y se controla por linea que este o no 
		// este asociada a factura.
		if (!newRecord){
			if (this.isLineInvoiced()){
				throw new AdempiereException("No esta permitido modificar esta linea de Provision ya que esta siendo referenciada en Facturas.");
			}
		}
		
		return true;
	}

	
	/***
	 * Verifica si una linea de provision esta asociada a una factura.
	 * OpenUp Ltda. Issue #100 
	 * @author Gabriel Vila - 23/11/2012
	 * @see
	 * @return
	 */
	private boolean isLineInvoiced() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean value = false;
		
		try{
			
			sql = " select line.uy_invoice_provision_id " +
				  " from uy_invoice_provision line " +
				  " inner join c_invoice inv on line.c_invoice_id = inv.c_invoice_id " +
				  " where inv.docstatus !='VO' " +
				  " and line.uy_provisionline_id =? ";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = true;
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return value;
	}

}
