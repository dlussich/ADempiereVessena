/**
 * MReserveDetail.java 10/12/2010
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MReserveDetail
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 10/12/2010
 */
  // OpenUp. Nicolas Garcia. 16/11/2011. Issue #821. Se toca la gran mayoria de la clase
public class MReserveDetail extends X_UY_Reserve_Detail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7984813062348344877L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_Reserve_Detail_ID
	 * @param trxName
	 */
	public MReserveDetail(Properties ctx, int UY_Reserve_Detail_ID, String trxName) {
		super(ctx, UY_Reserve_Detail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	// OpenUp. Nicolas Garcia. 27/10/2011. Issue #821.
	public MReserveDetail(Properties ctx, int UY_Reserve_Detail_ID, String trxName, int wareHouseID, int productID, int bpartherID, int orderID,
			int orderLineID, int UOM, int nroTrans) {
		super(ctx, UY_Reserve_Detail_ID, trxName);

		// this.setUY_Reserve_Product_ID(reserveProd.getUY_Reserve_Product_ID());
		this.setM_Warehouse_ID(wareHouseID);
		this.setM_Product_ID(productID);
		this.setC_BPartner_ID(bpartherID);
		this.setC_Order_ID(orderID);
		this.setC_OrderLine_ID(orderLineID);
		this.setC_UOM_ID(UOM);
		this.set_ValueOfColumn("uy_nrotrx", nroTrans);
		this.setDescription("");

	}

	// Fin Issue #821
	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MReserveDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp. issue #321
	 * MReserveDetail
	 * Descripcion :
	 * @author Nicolas Sarlabos
	 * Fecha : 22/09/2011
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Instancio modelos a usar.
		MOrderLine oLine = (MOrderLine) this.getC_OrderLine();
		BigDecimal correcta = oLine.getQtyOrdered().subtract(oLine.getQtyInvoiced()).subtract(oLine.getQtyReserved()).add(oLine.getUY_BonificaReglaUM());
		MReserveProduct rProd = (MReserveProduct) this.getUY_Reserve_Product();
		
		// No puedo entrar mas de lo necesario
		if (this.getQtyEntered().compareTo(correcta.divide(oLine.getuy_factor())) > 0) {
			MOrder order = new MOrder(getCtx(), oLine.getC_Order_ID(), get_TrxName());
			MProduct prod = new MProduct(getCtx(), rProd.getM_Product_ID(), get_TrxName());
			throw new AdempiereException("No es posible reservar mas de lo necesario: Pedido - " + order.getDocumentNo() + ", Producto : " + prod.getValue());
		}

		// Si no llego a cubrir bonificacion simple no reservo nada
		if (this.getC_OrderLine().getuy_bonificaregla().compareTo(Env.ZERO) > 0 && this.getC_OrderLine().getQtyEntered().compareTo(Env.ZERO) > 0) {

			if (!newRecord && this.getQtyEntered().compareTo(correcta.divide(oLine.getuy_factor())) != 0) {
				throw new AdempiereException(
						"Por definicion, en el caso de \"Bonificacion Simple\" se debe poder reservar la totalidad, es decir cantidad ordenada + bonificacion");
			}
		}

		// No se puede modificar lineas que sean bonificacion de otra linea
		if (oLine.isUY_EsBonificCruzada() && !newRecord) {
			throw new AdempiereException("No se puede modificar ya que esta linea - producto fue creado para cumplir una Bonificacion Cruzada");
		}

		if (!newRecord) {

			// No se generan bonificacion cruzada, caso simple.

			BigDecimal cantAnteriorUM = DB.getSQLValueBD(null, "SELECT qtyentered FROM uy_reserve_detail where uy_reserve_detail_id=" + this.get_ID());
			cantAnteriorUM = cantAnteriorUM.multiply(getuy_factor());

			BigDecimal cantMoverUM = (this.getQtyEntered().multiply(getuy_factor())).subtract(cantAnteriorUM);

			// si tengo stock
			if (cantAnteriorUM.add(rProd.getUY_QtyOnHand_AfterUM().subtract(this.getQtyEntered().multiply(getuy_factor()))).compareTo(Env.ZERO) >= 0) {

				rProd.setQtyEntered(rProd.getQtyEntered().add(cantMoverUM.divide(rProd.getuy_factor(), 2, RoundingMode.DOWN)));
				rProd.setUY_QtyOnHand_AfterUM(rProd.getUY_QtyOnHand_AfterUM().add(cantMoverUM.multiply(new BigDecimal("-1"))));
			} else {
				throw new AdempiereException("No hay stock disponible para esa cantidad, Disponible (Unidad Venta)=" + rProd.getuy_qtyonhand_before());
			}

		}

		// si es necesario se generan bonificaciones cruzadas
		if (this.isUY_TieneBonificCruzada() && !newRecord && is_ValueChanged(X_UY_Reserve_Detail.COLUMNNAME_QtyEntered)) {
			this.logicaSugiereBonificacionCruzada(this, "", this.getUY_Reserve_Product().getUY_Reserve_Filter_ID(), null, null, null);

		}

		// Seteo el estado de la reserva
		if (rProd.getuy_qtypending().compareTo(rProd.getQtyEntered()) == 0) {
			rProd.setuy_reserve_status("3");
		} else if (rProd.getuy_qtypending().compareTo(rProd.getQtyEntered()) > 0 && rProd.getQtyEntered().compareTo(Env.ZERO) > 0) {
			rProd.setuy_reserve_status("2");
		} else {
			rProd.setuy_reserve_status("1");
		}
		rProd.saveEx();

		return true;
	}

	/**
	 * 
	 * OpenUp. issue #321	
	 * Descripcion : Metodo que devuelve la cantidad total reservada de producto en unidades simples,
	 * para todas las lineas a excepcion de la actual
	 * @param id
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 29/09/2011
	 */


	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Obtiene los detalles de reserva hijos Issue #821
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 27/10/2011
	 * @param string 
	 */
	public MReserveDetail[] getMReserveDetailHijas(String trxName) {

		ArrayList<MReserveDetail> list = new ArrayList<MReserveDetail>();

		int idMDetailFilter = this.getUY_Reserve_Product().getUY_Reserve_Filter_ID();

		String sql = "SELECT a.* FROM uy_reserve_detail a JOIN uy_reserve_product prod ON prod.uy_reserve_product_id =a.uy_reserve_product_id "
				+ " WHERE a.c_orderline_id in(SELECT c_orderline_id from c_orderline  where c_order_id =a.c_order_id AND uy_lineapadre_id ="
				+ this.getC_OrderLine_ID() + ") AND prod.uy_reserve_filter_ID=" + idMDetailFilter;

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				list.add(new MReserveDetail(getCtx(), rs, get_TrxName()));
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return list.toArray(new MReserveDetail[list.size()]);
	}

	/**
	 * 
	 * OpenUp.	Metodo llamado desde metodo SugerirReservaBonificacionCruzada(), el mismo genera la bonificaciones cruzadas
	 * Descripcion : ISSUE #821
	 * @param detailPadre
	 * @param linePadre
	 * @author  Nicolas Garcia
	 * Fecha : 19/10/2011
	 * @param nroTrans 
	 * @param statusLineaReservaAnterior 
	 * @param product 
	 */
	public boolean logicaSugiereBonificacionCruzada(MReserveDetail detailPadre, String descripcionLinea, int reserveFilterID,
			HashMap<Integer, BigDecimal> stockDisponible, HashMap<Integer, BigDecimal> stockDisponibleControFinal, ArrayList<MReserveDetail> listaMReserveDetail) {

		// Variable de salida
		boolean salida = true;
		String descripcion = "Se reservo la totalidad de los siguientes productos bonificados Cruzados:  ";
		String descripcionLineasAux = "";
stockDisponible.get(1031027);
		// Estructuras temporales
		ArrayList<MReserveDetail> listaMReserveDetailAux = new ArrayList<MReserveDetail>();
		HashMap<Integer, BigDecimal> stockDisponibleBonif = new HashMap<Integer, BigDecimal>();

		MOrderLine linePadre = (MOrderLine) detailPadre.getC_OrderLine();

		// Obtengo Lineas bonificacion
		MOrderLine[] lista = linePadre.getLinesBonificacionCruzada();
		int total = lista.length;

		for (int i = 0; i < total; i++) {

			// Por comodidad
			MOrderLine line = lista[i];

			MProduct product = (MProduct) line.getM_Product();

			// Obrengo el disponible del producto que bonifica
			BigDecimal disponibleProducto = this.getStockDisponibleBonificado(stockDisponibleBonif, line.getM_Product_ID(), line.getM_Warehouse_ID(), product
					.getM_Locator_ID(),
					reserveFilterID, stockDisponible, stockDisponibleControFinal);

			int idProducto = line.getM_Product_ID();
			BigDecimal cantAReservar = Env.ZERO, cantPedido = Env.ZERO, cantPendiente = Env.ZERO, factorConv = line.getuy_factor();

			BigDecimal cantidadPadreUM = detailPadre.getQtyEntered().subtract(detailPadre.getuy_bonificaregla()).multiply(detailPadre.getuy_factor());

			// Si el ingreso de la cantidad es manual
			if (stockDisponible == null) {
				BigDecimal disponibleProductoPadre = this.getStockDisponibleBonificado(stockDisponibleBonif, linePadre.getM_Product_ID(), linePadre
						.getM_Warehouse_ID(), product.getM_Locator_ID(), reserveFilterID, stockDisponible, stockDisponibleControFinal);

				// Me quedo con el disponible del padre
				if (disponibleProductoPadre.compareTo(cantidadPadreUM) < 0) {
					cantidadPadreUM = disponibleProductoPadre;
					detailPadre.setQtyEntered(cantidadPadreUM);
				}
			}

			// Cantidad en unidad de venta redondeada
			BigDecimal cantidadNecesariaHijoUV = cantidadPadreUM.divide(line.getUY_BonifXPadreUnHijo(), 0, RoundingMode.DOWN).multiply(
					line.getUY_BonifCantHijo()).divide(line.getuy_factor(), 0,
					RoundingMode.DOWN);
			// Cantidad Unidad Simple
			BigDecimal cantidadNecesariaHijoUM = cantidadNecesariaHijoUV.multiply(line.getuy_factor());

			// Caso de que el disponible hijo sea lo necesario (OK) y que tenga
			// stock del producto padre
			if (cantidadNecesariaHijoUM.compareTo(disponibleProducto) <= 0
					&& cantidadPadreUM.compareTo(detailPadre.getQtyEntered().multiply(detailPadre.getuy_factor())) <= 0) {

				cantPedido = line.getQtyOrdered();
				cantAReservar = cantidadNecesariaHijoUV;
				cantPendiente = cantidadNecesariaHijoUV;

				// Dejo mensaje en detalle
				descripcion += product.getValue() + " ";
				this.consumirStockDisponible(stockDisponibleBonif, idProducto, cantAReservar.multiply(factorConv));

			} else if (cantidadNecesariaHijoUM.compareTo(disponibleProducto) > 0) {
				// Caso que el hijo no llege a lo necesario pero el padre es
				// llega 100% Ajusto padre Cantidad padre
				// En este caso ajusto el detail Padre y llamo a este metodo
				// nuevamente

				if (disponibleProducto.compareTo(Env.ZERO) > 0) {

					BigDecimal nuevaCantPadreUV = disponibleProducto.divide(line.getUY_BonifCantHijo(), 0, RoundingMode.DOWN).multiply(
							line.getUY_BonifXPadreUnHijo()).divide(linePadre.getuy_factor(), 0,
							RoundingMode.DOWN);

					BigDecimal nuevaCantPadreUM = nuevaCantPadreUV.multiply(linePadre.getuy_factor());

					BigDecimal nuevaCantBonifPadreUV = Env.ZERO;

					if (linePadre.getUY_BonifXPadreUnHijo().compareTo(Env.ZERO) > 0) {
						nuevaCantBonifPadreUV = nuevaCantPadreUM.divide(linePadre.getUY_BonifXPadreUnHijo(), 0, RoundingMode.DOWN).multiply(
								linePadre.getUY_BonifCantHijo()).divide(linePadre.getuy_factor(), 0, RoundingMode.DOWN);
					}
					BigDecimal nuevaCantBonifPadreUM = nuevaCantBonifPadreUV.multiply(linePadre.getuy_factor());

					detailPadre.setuy_bonificaregla(nuevaCantBonifPadreUV);
					detailPadre.setUY_BonificaReglaUM(nuevaCantBonifPadreUM);
					detailPadre.setQtyEntered(nuevaCantPadreUV.add(nuevaCantBonifPadreUV));

					detailPadre.setDescription(" No se reserva producto por falta de stock de " + product.getValue() + " para cumplir la bonificacion cruzada");


					this.logicaSugiereBonificacionCruzada(detailPadre, "", reserveFilterID, stockDisponibleBonif, stockDisponibleControFinal,
							listaMReserveDetail);

					// Agrego consumo de stock;
					Iterator<Integer> it = stockDisponibleBonif.keySet().iterator();

					while (it.hasNext()) {
						int l = it.next();
						stockDisponible.put(l, stockDisponibleBonif.get(l));

					}

					return true;
				} else {

					detailPadre.setuy_bonificaregla(Env.ZERO);
					detailPadre.setUY_BonificaReglaUM(Env.ZERO);
					detailPadre.setDescription(" Se reservo menor cantidad del producto ya que el producto  " + product.getValue()
							+ " no llega a cumplir la bonificacion Cruzada");

					return true;
				}

			} else {
				// TODO para pruebas
				throw new AdempiereException("Error en logica, Favor comunicarse con el administrador");
			}

			// Nueva registro en detalle de reserva
			MReserveDetail detailAux = new MReserveDetail(getCtx(), 0, get_TrxName(), detailPadre.getM_Warehouse_ID(), line.getM_Product_ID(), detailPadre
					.getC_BPartner_ID(), line.getC_Order_ID(), line.get_ID(), line.getC_UOM_ID(), detailPadre.getuy_nrotrx());

			detailAux.setQtyOrdered(cantPedido);
			detailAux.setuy_bonificaregla(cantAReservar);
			detailAux.setUY_BonificaReglaUM(cantAReservar.multiply(factorConv));
			detailAux.setuy_qtypending(cantPendiente);

			// Cantidad entrada (manual)
			detailAux.setQtyEntered(cantAReservar);

			detailAux.setDescription("Generada por Bonificacion Cruzada " + descripcionLineasAux);
			detailAux.setuy_factor(factorConv);
			detailAux.setUY_EsBonificCruzada(true);

			// Agrego detalle
			listaMReserveDetailAux.add(detailAux);
		}

		// Si voy a retornar true,pongo los objetos auxiliares como buenos
		if (salida) {
			// Actualizo datos
			actualizoDatos(reserveFilterID, listaMReserveDetail, listaMReserveDetailAux, stockDisponibleBonif, stockDisponible, detailPadre);
		}

		// Seteo descripcion
		detailPadre.setDescription(detailPadre.getDescription() + " " + descripcion);

		return salida;
	}

	private void actualizoDatos(int reserveFilterID, ArrayList<MReserveDetail> listaMReserveDetail, ArrayList<MReserveDetail> listaMReserveDetailAux,
			HashMap<Integer, BigDecimal> stockDisponibleBonif, HashMap<Integer, BigDecimal> stockDisponible, MReserveDetail detailPadre) {

		try {

			// Si es uan sugerencia de reserva inicial
			if (listaMReserveDetail != null && stockDisponible != null) {
				// Actualizo lista de detalles
				for (MReserveDetail det : listaMReserveDetailAux) {
					listaMReserveDetail.add(det);

				}
				// Agrego consumo de stock;
				Iterator<Integer> it = stockDisponibleBonif.keySet().iterator();

				while (it.hasNext()) {
					int i = it.next();
					stockDisponible.put(i, stockDisponibleBonif.get(i));

				}

			} else {
				// Caso que sea una actualizacion de este detalle

				// Borro los detalles vinculados a este detalle
				MReserveDetail[] list = this.getMReserveDetailHijas(get_TrxName());
				// En este hash se acumula cuanto tendra que valer la cantidad
				// ingresada
				HashMap<Integer, BigDecimal> acumuladoCantidades = new HashMap<Integer, BigDecimal>();

				// caso padre
				BigDecimal cantPadreEnteredAntes = DB.getSQLValueBD(null, "SELECT qtyentered FROM uy_reserve_product where uy_reserve_product_id="
						+ detailPadre.getUY_Reserve_Product_ID());

				BigDecimal factorPadre = DB.getSQLValueBD(null, "SELECT uy_factor FROM uy_reserve_product where uy_reserve_product_id="
						+ detailPadre.getUY_Reserve_Product_ID());


				if (cantPadreEnteredAntes == null) throw new AdempiereException("ERROR 0001: Consulte con el administrador");

				acumuladoCantidades.put(detailPadre.getM_Product_ID(), (detailPadre.getQtyEntered().subtract(cantPadreEnteredAntes)).multiply(factorPadre));

				for (int i = 0; i < list.length; i++) {

					for (MReserveDetail det : listaMReserveDetailAux) {
						if (det.getM_Product_ID() == list[i].getM_Product_ID()) {
							det.setUY_Reserve_Product_ID(list[i].getUY_Reserve_Product_ID());
							acumuladoCantidades.put(det.getM_Product_ID(), det.getQtyEntered().subtract(list[i].getQtyEntered()).multiply(det.getuy_factor()));
						}
					}
					list[i].delete(true);
				}

				// Agrego consumo de stock;
				Iterator<Integer> it = stockDisponibleBonif.keySet().iterator();

				while (it.hasNext()) {
					int i = it.next();

					// Caso de que sea una actualizacion.
					MReserveProduct resProd = MReserveProduct.getReserveProduct(reserveFilterID, i, this.getM_Warehouse_ID(), this.get_TrxName());

					resProd.setUY_QtyOnHand_AfterUM(resProd.getUY_QtyOnHand_AfterUM().add(acumuladoCantidades.get(i).multiply(new BigDecimal("-1"))));
					resProd.setQtyEntered(resProd.getQtyEntered().add(acumuladoCantidades.get(i).divide(resProd.getuy_factor(), 2, RoundingMode.DOWN)));

					resProd.saveEx(get_TrxName());
				}

				// Actualizo lista de detalles
				for (MReserveDetail det : listaMReserveDetailAux) {
					det.save(get_TrxName());
				}

			}
		} catch (Exception e) {
			throw new AdempiereException("ERROR 0002: " + e);
		}
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :La funcion devuelve el disponible en el proceso de carga de bonificacion.Issue #821.
	 * @param productID
	 * @param wareHouseID
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 19/10/2011
	 */
	private BigDecimal getStockDisponibleBonificado(HashMap<Integer, BigDecimal> stockDisponibleBonif, Integer productID, int wareHouseID, int locatorID,
			int reserveFilterID,
			HashMap<Integer, BigDecimal> stockDisponible, HashMap<Integer, BigDecimal> stockDisponibleControFinal) {

		BigDecimal disponible = Env.ZERO;
		// Si el producto ya fue usado en la priemra recorrida (se consumio
		// parte del disponible)
		if (stockDisponibleBonif.containsKey(productID)) {

			disponible = stockDisponibleBonif.get(productID);

		} else {

			disponible = getStockDisponible(productID, wareHouseID, locatorID, reserveFilterID, stockDisponible, stockDisponibleControFinal);
			stockDisponibleBonif.put(productID, disponible);

		}

		if (disponible.compareTo(Env.ZERO) > 0) {
			return disponible;
		}

		return Env.ZERO;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :La funcion devuelve el disponible real (Contemplando lo ya usado en este proceso) de un producto, ya sea si fue sugerido antes (Primera Pasada) o si es la primera vez.Issue #821.
	 * @param productID
	 * @param wareHouseID
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 19/10/2011
	 */
	private BigDecimal getStockDisponible(Integer productID, int wareHouseID, int locatorID, int reserveFilterID, HashMap<Integer, BigDecimal> stockDisponible,
			HashMap<Integer, BigDecimal> stockDisponibleControFinal) {

		BigDecimal disponible = Env.ZERO;

		// Si es el caso de la sugerencia automatica, tengo que tener si o si
		// los dos hashMap
		if (stockDisponible != null && stockDisponibleControFinal != null) {

			if (stockDisponible.containsKey(productID)) {
				disponible = stockDisponible.get(productID);
			} else {

				// Obtengo el disponible
				disponible = MStockTransaction.getQtyAvailable(wareHouseID, locatorID, productID, 0, 0, null, null);
				stockDisponible.put(productID, disponible);
				stockDisponibleControFinal.put(productID, disponible);
			}
			if (disponible.compareTo(Env.ZERO) > 0) {
				return disponible;
			}
		} else {
			// Caso en que sea una actualizacion de estareserve detail

			MReserveProduct resProd = MReserveProduct.getReserveProduct(reserveFilterID, productID, wareHouseID, this.get_TrxName());

			BigDecimal cantAnteriorUM = DB.getSQLValueBD(null, "SELECT qtyentered FROM uy_reserve_detail where uy_reserve_detail_id=" + this.get_ID());

			return resProd.getUY_QtyOnHand_AfterUM().add(cantAnteriorUM.multiply(getuy_factor()));
		}

		return Env.ZERO;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que acumula en el hashMap StockDisponible.Issue #821.
	 * @param productID
	 * @param cantidad
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 19/10/2011
	 * @param stockDisponibleBonif 
	 */
	private boolean consumirStockDisponible(HashMap<Integer, BigDecimal> stockDisponibleBonif, Integer productID, BigDecimal cantidad) {

		HashMap<Integer, BigDecimal> stock = stockDisponibleBonif;

		if (stock.containsKey(productID)) {

			if (stock.get(productID).compareTo(cantidad) < 0) {
				return false;
			} else {
				stock.put(productID, stock.get(productID).subtract(cantidad));
				return true;
			}

		} else {
			return true;
		}

	}
}
