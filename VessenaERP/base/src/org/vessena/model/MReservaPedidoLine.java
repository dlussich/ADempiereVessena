package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MUOMConversion;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

public class MReservaPedidoLine extends X_UY_ReservaPedidoLine {

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// OpenUp. Nicolas Garcia. 27/10/2011. Issue #821.

		/*
		 * if (!newRecord) return true; // Valido que no haya una linea con este
		 * mismo producto en la reserva String sql = " SELECT m_product_id " +
		 * " FROM uy_reservapedidoline " + " WHERE uy_reservapedidohdr_id =" +
		 * this.getUY_ReservaPedidoHdr_ID() + " AND m_product_id =" +
		 * this.getM_Product_ID(); int result = DB.getSQLValue(get_TrxName(),
		 * sql); if (result > 0){ MProduct prod = new MProduct(getCtx(),
		 * this.getM_Product_ID(), get_TrxName()); throw new
		 * AdempiereException("Ya existe una linea de Reserva para el producto : "
		 * + prod.getValue() + " - " + prod.getName()); }
		 */
		 //Fin Issue #821
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2777112610553326333L;

	public MReservaPedidoLine(Properties ctx, int UY_ReservaPedidoLine_ID,
			String trxName) {
		super(ctx, UY_ReservaPedidoLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MReservaPedidoLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Baja cantidad reservada original de esta linea de reserva segun
	 * diferencia recibida por parametro.
	 * @param diferenciaCantidad
	 * @author  Gabriel Vila 
	 * Fecha : 23/02/2011.
	 * Modificaciones : 
	 * 		22/07/2011. Issue #744. Se agrega parametro donde se recibe el modelo que genera esta diferencia o null sino corresponde.
	 * 								Se agrega parametro donde se recibe la fecha en que se genera esta diferencia o null si toma fecha actual.
	 * @throws Exception 
	 */
	public void anularCantidadReservada(BigDecimal diferenciaCantidad, PO modelGenerador, Timestamp movementDate) throws Exception{

		BigDecimal qtyBonificaSimple = this.getuy_bonificaregla();
		if (qtyBonificaSimple == null) qtyBonificaSimple = Env.ZERO;
		
		// Linea de Reserva
		if (diferenciaCantidad.compareTo(this.getQtyEntered().add(qtyBonificaSimple))>0){
			throw new Exception("La cantidad de reserva a anular supera la cantidad original.");
		}
		this.setQtyEntered(this.getQtyEntered().subtract(diferenciaCantidad));

		
		BigDecimal qtyReserved = MUOMConversion.convertProductFrom (getCtx(), this.getM_Product_ID(), 
								 this.getC_UOM_ID(), diferenciaCantidad);
		if (qtyReserved == null) qtyReserved = diferenciaCantidad;
		
		this.setQtyReserved(this.getQtyReserved().subtract(qtyReserved));

		this.saveEx(get_TrxName());
		
		BigDecimal diferenciaUM = MUOMConversion.convertProductFrom (getCtx(), this.getM_Product_ID(), 
				 this.getC_UOM_ID(), diferenciaCantidad);
		if (diferenciaUM == null) diferenciaUM = diferenciaCantidad;
		
		// Me aseguro diferencia positiva
		if (diferenciaUM.compareTo(Env.ZERO) < 0) diferenciaUM = diferenciaUM.negate();
		
		// Linea de Pedido
		MOrderLine oline = new MOrderLine(getCtx(), this.getC_OrderLine_ID(), get_TrxName());
		oline.setQtyReserved(oline.getQtyReserved().subtract(diferenciaUM).add(qtyBonificaSimple));
		
		// OpenUp. Gabriel Vila. 22/07/2011. Issue #744.
		// No estaba grabando los cambios en la linea del pedido por lo tanto no lo dejaba pendiente por la diferencia.
		// Tambien debo afectar el stock ya que me varia el reservado
		oline.saveEx(get_TrxName());
		
		// Bajo estado de stock reservado por la diferencia en unidades
		String message = null;
		MReservaPedidoHdr resHdr = new MReservaPedidoHdr(getCtx(), this.getUY_ReservaPedidoHdr_ID(), get_TrxName());
		if (modelGenerador == null) modelGenerador = resHdr;
		if (movementDate == null) movementDate = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY); 

		int statusReservedID = MStockStatus.getStatusReservedID(null);
		message = MStockTransaction.add(resHdr, resHdr, this.getM_Warehouse_ID(), 0, this.getM_Product_ID(), 
				this.getM_AttributeSetInstance_ID(), statusReservedID, movementDate, diferenciaUM.negate(), 0, null);
		if (message != null) throw new Exception(message);
		
		// Subo estado de stock pendiente por la diferencia en unidades
		int statusPendingID = MStockStatus.getStatusPendingID(null);
		MOrder orderHdr = new MOrder(resHdr.getCtx(), resHdr.getC_Order_ID(), resHdr.get_TrxName());
		message = MStockTransaction.add(resHdr, orderHdr, this.getM_Warehouse_ID(), 0, this.getM_Product_ID(), 
				this.getM_AttributeSetInstance_ID(), statusPendingID, movementDate, diferenciaUM.subtract(qtyBonificaSimple), 0, null);
		if (message != null) throw new Exception(message);

		// Fin Issue #744
		
		
	}
	
	/**
	 * 
	 * OpenUp. issue #853	
	 * Descripcion : Retorna el importe de esa linea en la reserva.
	 * Recibe como parametros el c_orderline_id y la qtyentered de la linea de reserva del pedido.
	 * @return
	 * @author  Nicolas Sarlabos, Nicolas Garcia issue #821 
	 * Fecha : 19/09/2011, 01/11/2011
	 */
	public BigDecimal getLineNetAmt(int Id, BigDecimal qty) {
				
		String sql ="";
		BigDecimal amt = Env.ZERO;
		BigDecimal total = Env.ZERO;
		
		if(Id > 0 && qty != null){
					
			sql = " SELECT priceentered" + //OpenUp Nicolas Sarlabos 18/11/2011
		     " FROM c_orderline" +
		     " WHERE c_orderline_id =" + Id;
			
			amt = DB.getSQLValueBD(get_TrxName(),sql);
					
			if(amt != null){
			
				if(amt.compareTo(Env.ZERO) > 0 && qty.compareTo(Env.ZERO) > 0){
						
					total = amt.multiply(qty);
				
				}
			}
		}
				
		return total;
		
								
	}
	
}
