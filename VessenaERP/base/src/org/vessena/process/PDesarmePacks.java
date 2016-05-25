package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MConfirmorderhdr;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPOrderBOM;
import org.eevolution.model.MPPOrderBOMLine;
import org.openup.model.MStockStatus;
import org.openup.model.MStockTransaction;

// org.openup.process.PDesarmePacks
// OpenUp. Nicolas Garcia. 06/09/2011. Issue #825.
public class PDesarmePacks extends SvrProcess {

	private MPPOrder ppOrder = null;
	private BigDecimal cantidadDesarmar = Env.ZERO;
	String message = "";
	
	@Override
	protected String doIt() throws Exception {
		
		// Valido que la cantidad a desarmar sea mayor a cero
		if (cantidadDesarmar.compareTo(Env.ZERO) <= 0) return "ERROR: La cantidad no puede ser cero";

		if (ppOrder == null) return "ERROR: Debe seleccionar una orden de proceso";

		// busco id
		int idPpOrderBom = DB.getSQLValue(null, "SELECT pp_order_bom_id FROM pp_order_bom WHERE pp_order_id=?", ppOrder.get_ID());

		if (idPpOrderBom <= 0) return "ERROR: No se pudo realizar la solicitud (error referente a ppOrderBomID)";

		MPPOrderBOM ppOrderBom = new MPPOrderBOM(getCtx(), idPpOrderBom, null);

		//Traigo las confirmaciones asociadas
		MConfirmorderhdr[] confList = MPPOrder.getAssociatedConfirmation("DocStatus='CO' AND pp_order_ID=" + ppOrder.get_ID(), this.get_TrxName());
		
		MConfirmorderhdr confirmacion = null;

		// Caso alianzur, el unico caso valido es que para esa orden solo tenga
		// una confirmacion
		int cant = confList.length;

		if (cant > 1) return "ERROR: No hay ninguna confirmacion COMPLETA asociada a la orden de produccion " + ppOrder.getDocumentNo();
		else if (cant < 1) return "ERROR#1: Hay mas de una confirmacion COMPLETA asociada a la orden de produccion " + ppOrder.getDocumentNo()
				+ ", pongase en contacto con soporte";
		else
			confirmacion = confList[0];
		
		// Defensivo
		if (confirmacion == null) return "ERROR: No hay ninguna confirmacion COMPLETA asociada a la orden de produccion " + ppOrder.getDocumentNo();

		// Busco la cantidad disponible
		String sql = "SELECT sum(movementqty*sign) as Cantidad FROM uy_stocktransaction " + "WHERE ad_client_id=" + Env.getAD_Client_ID(getCtx())
				+ " AND ad_table_id=" + confirmacion.get_Table_ID() + "AND record_id=" + confirmacion.get_ID() + " AND m_product_id="
				+ confirmacion.getM_Product_ID() + " AND m_warehouse_id=" + confirmacion.getUY_Almacendestino_ID();

		BigDecimal cantidadDisponible = DB.getSQLValueBD(get_TrxName(), sql);

		if (cantidadDisponible == null) return "ERROR#2: Problema al obtener la cantidad disponible, pongase en contacto con Soporte";

		// valido cantidad que quiere devolver sea igual o meno que la producida
		if (cantidadDisponible.compareTo(cantidadDesarmar) < 0)
			return "ERROR: La cantidad disponible para desarmar es " + cantidadDisponible.intValue() + " No es posible desarmar lo slicitado";

		// Busco Lineas
		MPPOrderBOMLine[] lines = ppOrderBom.getLines();

		int idStockStatus = MStockStatus.getStatusApprovedID(null);
		Timestamp fecha = new Timestamp(System.currentTimeMillis());

		// Recorro Lineas
		for (int i = 0; i < lines.length; i++) {

			int idAlmacen = lines[i].getM_Warehouse_ID();
			int idProducto = lines[i].getM_Product_ID();

			// Cantidad requerida por unidad por la cantidad a desarmar
			BigDecimal cantidad = lines[i].getQtyBOM().multiply(cantidadDesarmar);

			// Sube aprobado del componente
			this.addMsg(MStockTransaction.add(confirmacion, confirmacion, idAlmacen, 0, idProducto, 0, idStockStatus, fecha, cantidad, lines[i].getPP_Order_BOMLine_ID(), null));

		}

		// Bajo stock del producto fabricado
		this.addMsg(MStockTransaction.add(confirmacion, confirmacion, confirmacion.getUY_Almacendestino_ID(), 0, confirmacion
				.getM_Product_ID(), 0, idStockStatus, fecha, cantidadDesarmar.multiply(new BigDecimal(-1)), 0, null));


		if (!message.equals("")) {
			this.addLog("ERROR: " + message);
			Trx trans = Trx.get(get_TrxName(), true);
			trans.rollback();
			return "Error al ejecutar el proceso";
		}
	
		return "Proceso terminado";
	}

	@Override
	protected void prepare() {
		
		//Obtengo parametros
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();

			// Orden de proceso
			if (name.equalsIgnoreCase("PP_Order_ID")) {
				if (para[i].getParameter() != null) {
					if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")) {
						int ppOrderID = ((BigDecimal) para[i].getParameter()).intValue();
						if (ppOrderID > 0) {
							ppOrder = new MPPOrder(getCtx(), ppOrderID, get_TrxName());
						}

					}
				}
			}

			// Cantidad a desarmar
			if (name.equalsIgnoreCase("Qty")) {
				if (para[i].getParameter() != null) {
					if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")) {
						cantidadDesarmar = ((BigDecimal) para[i].getParameter());
					}
				}
			}
		}
	}

	private void addMsg(String msg) {
		if (msg != null) {
			this.message += msg;
		}
	}
}
