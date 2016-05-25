/**
 * MReserveProduct.java
 * 10/12/2010
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MReserveProduct
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 10/12/2010
 */
 // OpenUp. Nicolas Garcia. 16/11/2011. Issue #821. Se toca la gran mayoria de la clase
public class MReserveProduct extends X_UY_Reserve_Product {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5933656664151681845L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_Reserve_Product_ID
	 * @param trxName
	 */
	public MReserveProduct(Properties ctx, int UY_Reserve_Product_ID,
			String trxName) {
		super(ctx, UY_Reserve_Product_ID, trxName);

		if (UY_Reserve_Product_ID==0){
			setuy_qtyonhand_before(Env.ZERO);
			setuy_qtyonhand_after(Env.ZERO);
			setQtyEntered(Env.ZERO);
			setQtyOrdered(Env.ZERO);
			setuy_qtypending(Env.ZERO);
		}
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MReserveProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que actualiza cantidades Issue #821.
	 * @param cantAReservar
	 * @param cantPedido
	 * @param cantPendiente
	 * @author  Nicolas Garcia
	 * Fecha : 27/10/2011
	 */
	public void actualizarCantidades(BigDecimal cantAReservar, BigDecimal cantPedido, BigDecimal cantPendiente, BigDecimal factorLinea) {

		BigDecimal divisor = Env.ONE;

		if (factorLinea.compareTo(Env.ONE) == 0) {
			divisor = this.getuy_factor();
		}
		
		this.setQtyEntered(this.getQtyEntered().add(cantAReservar.divide(divisor, 2, RoundingMode.DOWN)));
		
		this.setQtyOrdered(this.getQtyOrdered().add(cantPedido.divide(divisor, 2, RoundingMode.DOWN)));
		
		this.setuy_qtypending(this.getuy_qtypending().add(cantPendiente.divide(divisor, 2, RoundingMode.DOWN)));
		
						
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		this.setuy_qtyonhand_after(getUY_QtyOnHand_AfterUM().divide(getuy_factor(), 2, RoundingMode.DOWN));

		if (this.getQtyEntered().compareTo(Env.ZERO) < 0) this.setQtyEntered(Env.ZERO);
		if (this.getuy_qtyonhand_after().compareTo(Env.ZERO) < 0) this.setuy_qtyonhand_after(Env.ZERO);
		if (this.getuy_qtyonhand_before().compareTo(Env.ZERO) < 0) this.setuy_qtyonhand_before(Env.ZERO);

		// Acomodo estados
		if (this.getuy_qtypending().compareTo(this.getQtyEntered()) == 0) {

			this.setuy_reserve_status("3");

		} else if (this.getQtyEntered().compareTo(Env.ZERO) > 0 && this.getQtyEntered().compareTo(this.getQtyOrdered()) < 0
				&& this.getQtyEntered().compareTo(Env.ZERO) >= 0) {

			this.setuy_reserve_status("2");

		} else
			this.setuy_reserve_status("1");
		return super.beforeSave(newRecord);
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que devuelve un ReserveProduct Issue #821
	 * @param reserveFilterID
	 * @param productID
	 * @param wareHouseID
	 * @param trxName
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 09/11/2011
	 */
	public static MReserveProduct getReserveProduct(int reserveFilterID, int productID, int wareHouseID, String trxName) {

		MReserveProduct prod = null;

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "SELECT * FROM uy_reserve_product WHERE m_warehouse_id=" + wareHouseID + "  AND uy_reserve_filter_id=" + reserveFilterID
					+ " AND m_product_id=" + productID;

			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				return new MReserveProduct(Env.getCtx(), rs, trxName);
			}

		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return prod;
	}

}
