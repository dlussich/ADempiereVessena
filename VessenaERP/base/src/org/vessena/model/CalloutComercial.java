/**
 * CalloutComercial.java
 * 17/12/2010
 */
package org.openup.model;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MPromotion;
import org.adempiere.model.MPromotionReward;
import org.compiere.apps.ADialog;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * CalloutComercial
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 17/12/2010
 */
public class CalloutComercial extends CalloutEngine {

	/**
	 * Constructor
	 */
	public CalloutComercial() {
	}


	/**
	 * OpenUp.	
	 * Descripcion : Obtiene Reward de Promocion segun cliente-producto-cantidad-fecha.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 17/12/2010
	 */
	public String setProductPromotionReward(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		try{
			
			if (value==null) return "";
			
			if (mField.getColumnName().equals("C_UOM_ID")){
				try{
					value = (BigDecimal)mTab.getValue("QtyEntered");	
				}
				catch (Exception e){
					value = new BigDecimal((Integer)mTab.getValue("QtyEntered"));
				}
			}
			
			BigDecimal newValue = Env.ZERO;
			try{
				newValue = (BigDecimal)value;	
			}
			catch (Exception e){
				newValue = new BigDecimal((Integer)value);
			}
			
			if (newValue.compareTo(Env.ZERO)<=0) return "";
			
			if (mTab.getValue("C_Order_ID")==null) return "";
			if (mTab.getValue("M_Product_ID")==null) return "";
			
			int cOrderID = (Integer)mTab.getValue("C_Order_ID");
			
			MOrder order = new MOrder(ctx, cOrderID, null);
			if (order.get_ID() <= 0) return "";

			//mTab.dataRefresh();

			BigDecimal importeLinea = (BigDecimal)mTab.getValue(X_C_OrderLine.COLUMNNAME_LineNetAmt);

			if (importeLinea.compareTo(Env.ZERO)<=0) return "";
			
			// Obtengo factor de conversion de UM-UV
			BigDecimal factorUOM = Env.ONE;
			if ((Integer)mTab.getValue("C_UOM_ID") != 100) factorUOM = MUOMConversion.getProductRateFrom(ctx, (Integer)mTab.getValue("M_Product_ID"), (Integer)mTab.getValue("C_UOM_ID"));
		
			MPromotionReward reward = MPromotion.getReward(order.getC_BPartner_ID(), (Integer)mTab.getValue("M_Product_ID"), newValue.multiply(factorUOM), order.getDateOrdered(), importeLinea);
			
			if (reward != null){
				
				if (reward.getAmount().compareTo(Env.ZERO)>0){
					mTab.setValue(X_C_OrderLine.COLUMNNAME_Discount, reward.getAmount());
					mTab.setValue("uy_promodiscountmax", reward.getAmount().subtract((BigDecimal)mTab.getValue("flatdiscount")));
					mTab.setValue("uy_promodiscount", reward.getAmount().subtract((BigDecimal)mTab.getValue("flatdiscount")));
				}
				
				if (reward.getQty().compareTo(Env.ZERO)>0){
					BigDecimal cantBonifUM = reward.getQty();
					//BigDecimal factorUOM = MUOMConversion.getProductRateFrom(ctx, (Integer)mTab.getValue("M_Product_ID"), (Integer)mTab.getValue("C_UOM_ID"));
					BigDecimal cantBonifUV = cantBonifUM.divide(factorUOM, 2, RoundingMode.HALF_UP); 

					if (cantBonifUM.compareTo(factorUOM)<0){
						mTab.setValue("uy_bonificaregla", Env.ZERO);
						return "Cantidad Bonificada no cumple con Factor de Unidad de Venta seleccionada.";
					}
					
					if (cantBonifUV.compareTo(cantBonifUM)<0){
						long parteEntera = (long)cantBonifUV.doubleValue();
						double fract = cantBonifUV.doubleValue() - parteEntera;
						if (fract > 0) {
							mTab.setValue("uy_bonificaregla", Env.ZERO);
							return "Cantidad Bonificada no cumple con Factor de Unidad de Venta seleccionada.";
						}
					}
					
					mTab.setValue("uy_bonificaregla", cantBonifUV);
				}
			}
			
			// Refresca pestaña actual
			//mTab.dataRefresh();
			
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo obtener información para procesar promociones", e);
			return e.getMessage();
		}
		return "";
	}

	/**
	 * OpenUp.	
	 * Descripcion : Validaciones y seteos al cambiar valor de cantidad reservada en proceso de 
	 * Generacion de Reservas.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldvalue
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 02/01/2011
	 */
	public String reserveQtyChange(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldvalue){

		try{
			if (value==null) return "";
			
			BigDecimal newValue = Env.ZERO;
			BigDecimal qtyOrdered = Env.ZERO;
			BigDecimal qtyBonif = Env.ZERO;
			
			try{
				newValue = (BigDecimal)value;	
			}
			catch (Exception e){
				newValue = new BigDecimal((Integer)value);
			}
			
			try{
				qtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");	
			}
			catch (Exception e){
				qtyOrdered = new BigDecimal((Integer)mTab.getValue("QtyOrdered"));
			}
			
			try{
				qtyBonif = (BigDecimal)mTab.getValue("uy_bonificaregla");	
			}
			catch (Exception e){
				qtyBonif = new BigDecimal((Integer)mTab.getValue("uy_bonificaregla"));
			}

			if (newValue.compareTo(Env.ZERO)<=0){
				if (oldvalue==null) return "";
			}

			if (mTab.getValue("C_Order_ID")==null) return "";
			
			BigDecimal qtyTotal = qtyOrdered.add(qtyBonif);
			
			// Valido cantidad reservada menor/igual a cantidad pendiente del pedido mas bonificaciones
			if (newValue.compareTo(qtyTotal)>0){
				mTab.setValue("QtyEntered", oldvalue);
				return "La cantidad reservada no puede ser mayor que la cantidad del pedido mas bonificaciones.";
			}
			
			// Valido cantidad reservada menor/igual a cantidad disponible de producto
			MReserveProduct rProd = new MReserveProduct(ctx, (Integer)mTab.getValue("UY_Reserve_Product_ID"), null);
			if (rProd.get_ID() <= 0) return "No se pudo obtener informacion del Producto asociado al Detalle.";
			
			MProduct prod = new MProduct(ctx, rProd.getM_Product_ID(), null);
			
			// Obtengo diferencia entre cantidad reservada anterior y la nueva cantidad digitada
			BigDecimal reservaOld = (oldvalue == null) ? Env.ZERO : (BigDecimal)oldvalue;
			BigDecimal difReservas = reservaOld.subtract(newValue);
			BigDecimal difReservasUV = difReservas;
			
			// Si el disponible final del producto alcanza para cubrir la diferencia de reserva (teniendo en cuanta UOM)
			int idUOM = (Integer)mTab.getValue("C_UOM_ID");
			BigDecimal cantidadDisponibleProd = rProd.getuy_qtyonhand_after();
			if (idUOM == 100){
				cantidadDisponibleProd = rProd.getuy_qtyonhand_after().multiply(prod.getFactorUVDefualt());
				difReservasUV = difReservas.divide(prod.getFactorUVDefualt(), 2, RoundingMode.HALF_UP);
			}
			
			BigDecimal cantidadDispoFinal = cantidadDisponibleProd.add(difReservas);
			if (cantidadDispoFinal.compareTo(Env.ZERO)<0) {
				mTab.setValue("QtyEntered", oldvalue);
				return "La cantidad reservada no puede ser mayor que la cantidad disponible del Producto.";
			}
			
			// Seteo nuevos valores de pendiente y disponible del producto y del detail
			//BigDecimal cantidadPendiente = (BigDecimal)mTab.getValue(X_UY_Reserve_Detail.COLUMNNAME_uy_qtypending);
//			cantidadPendiente = cantidadPendiente.add(difReservas);
			//mTab.setValue(X_UY_Reserve_Detail.COLUMNNAME_uy_qtypending, cantidadPendiente);

			//rProd.setuy_qtypending(rProd.getuy_qtypending().add(difReservasUV));
			
			BigDecimal cantidadDispoDetail = (BigDecimal)mTab.getValue(X_UY_Reserve_Detail.COLUMNNAME_uy_qtyonhand_after);
			cantidadDispoDetail = cantidadDispoDetail.add(difReservas);
			mTab.setValue(X_UY_Reserve_Detail.COLUMNNAME_uy_qtyonhand_after, cantidadDispoDetail);

			//mTab.dataRefresh();
			
			// Actualizo valores del producto
			rProd.setQtyEntered(rProd.getQtyEntered().subtract(difReservasUV));
			rProd.setuy_qtyonhand_after(rProd.getuy_qtyonhand_after().add(difReservasUV));
			
			//OpenUp Nicolas Sarlabos issue #321
			//seteo el estado de la reserva
			if(rProd.getuy_qtypending().compareTo(rProd.getQtyEntered())==0){
				
				rProd.setuy_reserve_status("3");
						
			}else if(rProd.getuy_qtypending().compareTo(rProd.getQtyEntered())>0 && rProd.getQtyEntered().compareTo(Env.ZERO)>0){
				
				rProd.setuy_reserve_status("2");
								
			}else rProd.setuy_reserve_status("1");
			//fin OpenUp #321
			
			rProd.saveEx();
							
			//mTab.getTableModel().dataRefreshAll();
			
			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "Error en validaciones al cambiar la cantidad reservada.", e);
			return e.getMessage();
		}		
	}

	/***
	 * Al seleccionar una orden de fabricacion en la orden de entrega, debo traer y mostrar
	 * todos los datos asociadas a la misma. 
	 * OpenUp Ltda. Issue #80 
	 * @author Gabriel Vila - 22/10/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setDeliveryManufOrder(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		int uyManufOrderID = (Integer)value;
		if (uyManufOrderID <= 0) return "";
		
		MManufOrder manord = new MManufOrder(ctx, uyManufOrderID, null);
		mTab.setValue(X_UY_BudgetDelivery.COLUMNNAME_C_Activity_ID, manord.getC_Activity_ID());
		mTab.setValue(X_UY_BudgetDelivery.COLUMNNAME_serie, manord.getserie());
		mTab.setValue(X_UY_BudgetDelivery.COLUMNNAME_UY_Budget_ID, manord.getUY_Budget_ID());
		
		return "";
	}
	
	/***
	 * Al seleccionar un producto en la linea del presupuesto de venta cargo el precio correspondiente a la cantidad 1 
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 21/11/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";
		mTab.setValue("C_Charge_ID", null);

		boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, WindowNo, "C_BPartner_ID");
		BigDecimal Qty = Env.ONE;
		MProductPricing pp = new MProductPricing (M_Product_ID.intValue(), C_BPartner_ID, Qty, IsSOTrx);
		//
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		pp.setM_PriceList_ID(M_PriceList_ID);

		Timestamp invoiceDate = Env.getContextAsDate(ctx, WindowNo, "DateTrx");
		/** PLV is only accurate if PL selected in header */
		int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
		if ( M_PriceList_Version_ID == 0 && M_PriceList_ID > 0)
		{
			String sql = "SELECT plv.M_PriceList_Version_ID "
					+ "FROM M_PriceList_Version plv "
					+ "WHERE plv.M_PriceList_ID=? "					
					+ " AND plv.ValidFrom <= ? "
					+ "ORDER BY plv.ValidFrom DESC";
			

			M_PriceList_Version_ID = DB.getSQLValueEx(null, sql, M_PriceList_ID, invoiceDate);
			if ( M_PriceList_Version_ID > 0 )
				Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", M_PriceList_Version_ID );
		}

		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);


		pp.setPriceDate(invoiceDate);
		//		
		mTab.setValue("Price1", pp.getPriceList());
		
		return "";
	}	
	
	/***
	 * Al seleccionar una orden de fabricacion en la factura de venta, debo traer y mostrar
	 * el vendedor, regla y termino de pago del presupuesto asociado a la orden.
	 * OpenUp Ltda. Issue #80 
	 * @author Nicolas Sarlabos - 03/12/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromBudget(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		int uyManufOrderID = (Integer)value;
		if (uyManufOrderID <= 0) return "";
		
		MManufOrder manord = new MManufOrder(ctx, uyManufOrderID, null);
		MBudget budget = new MBudget (ctx, manord.getUY_Budget_ID(), null);
		//seteo el presupuesto
		mTab.setValue(X_C_Invoice.COLUMNNAME_UY_Budget_ID, budget.get_ID());
		
		//seteo el vendedor del presupuesto a la factura
		mTab.setValue(X_C_Invoice.COLUMNNAME_SalesRep_ID, budget.getSalesRep_ID());
		
		//seteo la regla y el termino de pago
		mTab.setValue(X_C_Invoice.COLUMNNAME_C_PaymentTerm_ID, budget.getC_PaymentTerm_ID());
		
		MPaymentTerm term = new MPaymentTerm(ctx,budget.getC_PaymentTerm_ID(), null);
		MPaymentRule payrule = new MPaymentRule(ctx,term.getUY_PaymentRule_ID(), null);
		mTab.setValue(X_C_Invoice.COLUMNNAME_paymentruletype, payrule.getpaymentruletype());
		
		return "";
	}
	
	/***
	 * Al seleccionar un cliente en un presupuesto traigo sus datos.
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 20/01/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setPartnerBudget(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		int partnerID = (Integer)value;
		if (partnerID <= 0) return "";
		
		MBPartner partner = new MBPartner(ctx, partnerID, null);
		
		//seteo lista de precios
		mTab.setValue(X_UY_Budget.COLUMNNAME_M_PriceList_ID, partner.getM_PriceList_ID());
		
		//seteo el vendedor
		mTab.setValue(X_UY_Budget.COLUMNNAME_SalesRep_ID, partner.getSalesRep_ID());
		
		//seteo el termino de pago
		mTab.setValue(X_UY_Budget.COLUMNNAME_C_PaymentTerm_ID, partner.getC_PaymentTerm_ID());
		
		return "";
	}
	
	/***
	 * Calcula importe en moneda del cabezal en la linea de pago contado
	 * OpenUp Ltda. Issue # 
	 * @author Nicolas Sarlabos - 28/01/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setAmtMN(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		// OpenUp. Nicolas Sarlabos. 23/04/2013. #767
		if (value == null) return "";
		
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		int StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
		int cCurrency = (Integer) mTab.getValue(X_UY_InvoiceCashPayment.COLUMNNAME_C_Currency_ID);
		
		BigDecimal total = ((BigDecimal) mTab.getValue(X_UY_InvoiceCashPayment.COLUMNNAME_CurrencyRate)).multiply((BigDecimal) mTab.getValue(X_UY_InvoiceCashPayment.COLUMNNAME_Amount));
		
		int invoiceID = Env.getContextAsInt (ctx, WindowNo, 0, "C_Invoice_ID"); //obtengo el ID de factura del cabezal
		if (invoiceID > 0){
			MInvoice invoice = new MInvoice(ctx, invoiceID, null);
			BigDecimal rate = (BigDecimal) mTab.getValue(X_UY_InvoiceCashPayment.COLUMNNAME_CurrencyRate);
			if (rate == null) rate = Env.ONE;
			else if (rate.compareTo(Env.ZERO) == 0) rate = Env.ONE;
			if (invoice.getC_Currency_ID() != 142 && cCurrency == 142){
				total = ((BigDecimal) mTab.getValue(X_UY_InvoiceCashPayment.COLUMNNAME_Amount)).divide(rate, StdPrecision, RoundingMode.HALF_UP);
			} else if(invoice.getC_Currency_ID() == 142 && cCurrency != 142){
				total = ((BigDecimal) mTab.getValue(X_UY_InvoiceCashPayment.COLUMNNAME_Amount)).multiply(rate);
								
			}
		}
		// Fin OpenUp
		
		//seteo el importe en moneda nacional
		mTab.setValue(X_UY_InvoiceCashPayment.COLUMNNAME_PayAmt, total.setScale(StdPrecision, RoundingMode.HALF_UP));
			
		return "";
	}
	
	/***
	 * Calcula importe en la linea de pago contado segun tipo de cambio
	 * OpenUp Ltda. Issue #592 
	 * @author Nicolas Sarlabos - 20/03/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setAmt(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		//OpenUp. Nicolas Sarlabos. 23/04/2013. #767
		if (value == null) return "";
		
		BigDecimal grandTotal = Env.ZERO;
		BigDecimal total = Env.ZERO;
		int cCurrency = (Integer) mTab.getValue(X_UY_InvoiceCashPayment.COLUMNNAME_C_Currency_ID);
		
		int invoiceID = Env.getContextAsInt (ctx, WindowNo, 0, "C_Invoice_ID"); //obtengo el ID de factura del cabezal
		MInvoice inv = new MInvoice (ctx,invoiceID,null);
				
		if(inv.get_ID()>0) grandTotal = inv.getGrandTotal(); 
		
		if (grandTotal == Env.ZERO) return "";
		
		int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
		int StdPrecision = MPriceList.getStandardPrecision(ctx, M_PriceList_ID);
				
		BigDecimal rate = ((BigDecimal) mTab.getValue(X_UY_InvoiceCashPayment.COLUMNNAME_CurrencyRate)); //OpenUp. Nicolas Sarlabos. 15/04/2013. #706
		if (rate == null) rate = Env.ONE;
				
		if (cCurrency != 142 && inv.getC_Currency_ID() == 142){
			
			if(rate.compareTo(Env.ZERO) > 0){
				total = (grandTotal.divide(rate,RoundingMode.HALF_UP));
			}
			
		} 
		else if (cCurrency == 142 && inv.getC_Currency_ID() != 142) {
			
			total = (grandTotal.multiply(rate));
			
		} 
		else{
			
			total = (grandTotal.multiply(rate));
		}
				
		//seteo el importe en moneda de la factura
		mTab.setValue(X_UY_InvoiceCashPayment.COLUMNNAME_Amount, total.setScale(StdPrecision, RoundingMode.HALF_UP));
		//Fin OpenUp.	
		return "";
	}
	
	/***
	 * Al cambiar la cedula de identidad debo obtener datos del cliente desde
	 * base del Financial.
	 * OpenUp Ltda. Issue #550 
	 * @author Gabriel Vila - 15/03/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setCI(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		Connection con = null;
		ResultSet rs = null;
		
		if (value == null) return "";

		String cedula = value.toString().trim();
		
		if (cedula.equalsIgnoreCase("")) return "";
		
		try{
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select stapenom,stcallenro,isnull(stdepto,'') as stdepto, " +
						 " isnull(clitel,'') as clitel, isnull(clicelular,'') as clicelular, " +
						 " stnrotarj " +
					     " from q_clientes " +
					     " where clicod=" + cedula;
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				mTab.setValue(X_C_Order.COLUMNNAME_Name, rs.getString("stapenom"));
				mTab.setValue(X_C_Order.COLUMNNAME_Direction, rs.getString("stcallenro") + " " + rs.getString("stdepto"));
				mTab.setValue(X_C_Order.COLUMNNAME_Telephone, rs.getString("clitel"));
				mTab.setValue(X_C_Order.COLUMNNAME_Mobile, rs.getString("clicelular"));
				mTab.setValue(X_C_Order.COLUMNNAME_Mobile, rs.getString("clicelular"));
				BigDecimal nrotarj = rs.getBigDecimal("stnrotarj");
				if (nrotarj != null){
					mTab.setValue(X_C_Order.COLUMNNAME_CardNumber, nrotarj.toString());	
				}
				
			}
			else{
				throw new AdempiereException("No hay registro de Cliente con ese Numero de Cedula.");
			}
			
			rs.close();
			con.close();		
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		
		return "";
	}
	
	/***
	 * 
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 15/03/2013
	 * @see
	 * @param fduData
	 * @return
	 * @throws Exception
	 */
	private Connection getFDUConnection(MFduConnectionData fduData) throws Exception {
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			if(fduData != null){
				
				connectString = "jdbc:sqlserver://" + fduData.getserver_ip() + "\\" + fduData.getServer() + 
								";databaseName=" + fduData.getdatabase_name() + ";user=" + fduData.getuser_db() + 
								";password=" + fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, fduData.getuser_db(), fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}
	
	/***
	 * Al cambiar la moneda, en caso que sea extranjera, tengo que cargar tasa de cambio
	 * para fecha de documento.
	 * OpenUp Ltda. Issue #539 
	 * @author Gabriel Vila - 15/03/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCurrency(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int cCurrency = ((Integer)value).intValue();
		if (cCurrency <= 0){
			mTab.setValue(X_C_Invoice.COLUMNNAME_CurrencyRate, Env.ONE);
			return "";
		}
	
		Timestamp fecha = null;
		BigDecimal dividerate = Env.ONE;
		//OpenUp. Nicolas Sarlabos. 27/05/2013. #883 
		int invoiceID = Env.getContextAsInt (ctx, WindowNo, 0, "C_Invoice_ID"); //obtengo el ID de factura del cabezal
		MInvoice inv = new MInvoice (ctx,invoiceID,null);
			
		if(inv.get_ID()>0 && inv.getDateInvoiced()!=null){
			
			fecha = inv.getDateInvoiced();
			
		}else fecha = new Timestamp (System.currentTimeMillis());
		
		if (cCurrency != 142){
			dividerate = MConversionRate.getDivideRate(142, cCurrency, fecha, 0, 
					((Integer)mTab.getValue(X_C_Invoice.COLUMNNAME_AD_Client_ID)).intValue(), 
					((Integer)mTab.getValue(X_C_Invoice.COLUMNNAME_AD_Org_ID)).intValue());
		}else {
			
			dividerate = MConversionRate.getRate(inv.getC_Currency_ID(), 142, fecha, 0, 
					((Integer)mTab.getValue(X_C_Invoice.COLUMNNAME_AD_Client_ID)).intValue(), 
					((Integer)mTab.getValue(X_C_Invoice.COLUMNNAME_AD_Org_ID)).intValue());			
			
		}
		
		if (cCurrency == inv.getC_Currency_ID()) dividerate = Env.ONE;
		//Fin OpenUp. #883
		if (dividerate == null) dividerate = Env.ONE;
				
		mTab.setValue(X_C_Invoice.COLUMNNAME_CurrencyRate, dividerate);
				
		return "";
	}	
	
	/***
	 * Al cambiar la moneda, en caso que sea extranjera, tengo que cargar tasa de cambio
	 * para fecha de documento.
	 * OpenUp Ltda. Issue #767 
	 * @author Nicolas Sarlabos - 23/04/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCurrency2(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
	
		Timestamp fecha = null;
		MInvoice inv = null;
		BigDecimal dividerate = Env.ZERO;
		
		int invoiceID = Env.getContextAsInt (ctx, WindowNo, 0, "C_Invoice_ID"); //obtengo el ID de factura del cabezal
		inv = new MInvoice (ctx,invoiceID,null);
		
		if(inv.get_ID()>0 && inv.getDateInvoiced()!=null){
			
			fecha = inv.getDateInvoiced();
			
		}else fecha = (Timestamp)mTab.getValue(X_C_Invoice.COLUMNNAME_DateInvoiced);
						
		if (mTab.getField(X_C_Invoice.COLUMNNAME_CurrencyRate) == null){
			return "";
		}
		
		int cCurrency = ((Integer)value).intValue();
		if (cCurrency <= 0){
			mTab.setValue(X_C_Invoice.COLUMNNAME_CurrencyRate, Env.ONE);
			return "";
		}

		if (cCurrency == inv.getC_Currency_ID()){
			mTab.setValue(X_C_Invoice.COLUMNNAME_CurrencyRate, Env.ONE);
			return "";
		} else {
			
			dividerate = MConversionRate.getRate(inv.getC_Currency_ID(), 142, fecha, 0, 
					((Integer)mTab.getValue(X_C_Invoice.COLUMNNAME_AD_Client_ID)).intValue(), 
					((Integer)mTab.getValue(X_C_Invoice.COLUMNNAME_AD_Org_ID)).intValue());
			
		} 

		if (dividerate == null) dividerate = Env.ZERO;
		
		mTab.setValue(X_C_Invoice.COLUMNNAME_CurrencyRate, dividerate);
		
		return "";
	}		
	
	/***
	 * Setea la tasa de cambio de la moneda de carga al modificarse la moneda de carga o de factura.
	 * OpenUp Ltda. Issue #1623 
	 * @author Nicolas Sarlabos - 25/02/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCurrencyProforma(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocTypeTarget_ID"); //obtengo el ID de tipo de documento
		
		MDocType doc = new MDocType(ctx,docID,null); //instancio documento
		
		if(doc.getValue()!=null && doc.getValue().equalsIgnoreCase("fileprofinvoice")){ //solo para documento "Proforma Expediente"
			
			Timestamp fecha = null;
			BigDecimal dividerate = Env.ONE;
			
			int cur1 = (Integer)mTab.getValue("C_Currency_ID");
			int cur2 = (Integer)mTab.getValue("C_Currency2_ID");
			
			if(cur1 == cur2){
				
				mTab.setValue(X_C_Invoice.COLUMNNAME_CurrencyRate, dividerate);
				
				return "";
				
			} else {
				
				int invoiceID = Env.getContextAsInt (ctx, WindowNo, 0, "C_Invoice_ID"); //obtengo el ID de factura del cabezal
				MInvoice inv = new MInvoice (ctx,invoiceID,null);
				
				if(inv.get_ID()>0 && inv.getDateInvoiced()!=null){
					
					fecha = inv.getDateInvoiced();
					
				} else fecha = new Timestamp (System.currentTimeMillis());
						
				dividerate = MConversionRate.getDivideRate(cur2, cur1, fecha, 0, 
						((Integer)mTab.getValue(X_C_Invoice.COLUMNNAME_AD_Client_ID)).intValue(), 
						((Integer)mTab.getValue(X_C_Invoice.COLUMNNAME_AD_Org_ID)).intValue());				
				
				if (dividerate == null) dividerate = Env.ONE;
				
				mTab.setValue(X_C_Invoice.COLUMNNAME_CurrencyRate, dividerate);				
				
			}				

		}		
				
		return "";
	}	
	
	/***
	 * Al seleccionar una factura en ventana de Devolucion Contado seteo campos
	 * OpenUp Ltda. #720
	 * @author Nicolas Sarlabos - 16/04/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromInvoice(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		int invoiceID = (Integer)value;
		if (invoiceID <= 0) return "";
		
		MInvoice inv = new MInvoice(ctx, invoiceID, null);
		
		//seteo lista de precios
		mTab.setValue(X_C_Invoice.COLUMNNAME_M_PriceList_ID, inv.getM_PriceList_ID());
			
		//seteo direccion de socio de negocio
		mTab.setValue(X_C_Invoice.COLUMNNAME_C_BPartner_Location_ID, inv.getC_BPartner_Location_ID());
		
		return "";
	}
	
	/***
	 * Al seleccionar un producto en la linea de entrega muestro cantidad entregada y pendiente
	 * OpenUp Ltda. #955
	 * @author Nicolas Sarlabos - 06/06/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setQtyFromManufLine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		int manuflineID = (Integer)value;
		if (manuflineID <= 0) return "";
		
		MManufLine ml = new MManufLine(ctx, manuflineID, null);
		
		//seteo cantidad entregada
		mTab.setValue(X_UY_BudgetDeliveryLine.COLUMNNAME_QtyDelivered, ml.getQtyDelivered());
			
		//seteo cantidad pendiente		
		BigDecimal qtyPend = ml.getQty().subtract(ml.getQtyDelivered()); 
		
		mTab.setValue(X_UY_BudgetDeliveryLine.COLUMNNAME_QtyToDeliver, qtyPend);
		
		return "";
	}
	
	/***
	 * Al seleccionar un diseño para entrega en la OF se sugiere la cantidad especificada en el cabezal.
	 * OpenUp Ltda. #709
	 * @author Nicolas Sarlabos - 18/06/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setQtyDesign(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		String design = (String)value;
		
		int orderID = Env.getContextAsInt (ctx, WindowNo, 0, "UY_ManufOrder_ID"); //obtengo el ID de orden de fabricacion
		MManufOrder order = new MManufOrder (ctx,orderID,null);
		
		BigDecimal qty = Env.ZERO;
		
		if(!design.equalsIgnoreCase("")){
			
			if(design.equalsIgnoreCase("D1")){
				
				qty = order.getQtyDesign1();
				
			} else if(design.equalsIgnoreCase("D2")){
				
				qty = order.getQtyDesign2();
			
			} else if(design.equalsIgnoreCase("D3")){
				
				qty = order.getQtyDesign3();
			}			
			
		}
		
		//seteo cantidad		
		mTab.setValue(X_UY_ManufDelivery.COLUMNNAME_Qty, qty);
		
		return "";
	}
	
	/***
	 * Al seleccionar una lista de precios se setea la moneda de la misma.
	 * OpenUp Ltda. #4420
	 * @author Nicolas Sarlabos - 16/06/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setCurrencyPriceList (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_PriceList_ID = 0;
		
		if(mField.getColumnName().equalsIgnoreCase("M_PriceList_ID")){
			
			M_PriceList_ID = (Integer) mTab.getValue("M_PriceList_ID");
			if (M_PriceList_ID == null || M_PriceList_ID.intValue()== 0)
				return "";
			
			MPriceList list = new MPriceList (ctx, M_PriceList_ID, null);
			
			if(list!=null && list.get_ID()>0) mTab.setValue("C_Currency_ID", list.getC_Currency_ID());					
			
		} else if (mField.getColumnName().equalsIgnoreCase("M_PriceList_ID_2")){
			
			M_PriceList_ID = (Integer) mTab.getValue("M_PriceList_ID_2");
			if (M_PriceList_ID == null || M_PriceList_ID.intValue()== 0)
				return "";
			
			MPriceList list = new MPriceList (ctx, M_PriceList_ID, null);
			
			if(list!=null && list.get_ID()>0) {
				
				mTab.setValue("C_Currency_ID_2", list.getC_Currency_ID());
				mTab.setValue("PricePrecision", list.getPricePrecision());
			}
			
		}
		
		return "";
	}
	
	/***
	 * Al ingresar un codigo de barras de producto se cargan sus datos.
	 * OpenUp Ltda. #4435
	 * @author Nicolas Sarlabos - 18/06/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromUpcScan (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		if (value == null) return "";
				
		String upc = (String) value;
		
		if (upc.equalsIgnoreCase("")) return "";
		
		MProduct prod = MProduct.forUPC(ctx, upc, null);//obtengo producto por UPC
				
		if(prod!=null && prod.get_ID()>0){ //si encuentro producto
			
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_M_Product_ID, prod.get_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_IsActive2, prod.isActive());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_Value, prod.getValue());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_Name, prod.getName());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_Description, prod.getDescription());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_C_UOM_ID, prod.getC_UOM_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_Linea_Negocio_ID, prod.getUY_Linea_Negocio_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_ProductGroup_ID, prod.get_ValueAsInt("UY_ProductGroup_ID"));
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_Familia_ID, prod.getUY_Familia_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_SubFamilia_ID, prod.getUY_SubFamilia_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_C_TaxCategory_ID, prod.getC_TaxCategory_ID());
					
		} else { //si NO encuentro producto
			
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_IsVerified, false);
			
			ADialog.info(0,null,"No existe producto asociado a este codigo de barras");	
		}
		
		return "";
	}
	
	/***
	 * Al ingresar un codigo de barras de producto se cargan sus datos.
	 * OpenUp Ltda. #4435
	 * @author Nicolas Sarlabos - 18/06/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromProductScan (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		if (mTab.getValueAsBoolean("IsVerified") == true) return "";
		
		if (value == null) return "";
				
		int prodID = (Integer)value;
		if (prodID <= 0) return "";
		
		MProduct prod = new MProduct(ctx, prodID, null);
				
		if(prod!=null && prod.get_ID()>0){ //si encuentro producto
			
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_M_Product_ID, prod.get_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_IsActive2, prod.isActive());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_Value, prod.getValue());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_Name, prod.getName());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_Description, prod.getDescription());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_C_UOM_ID, prod.getC_UOM_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_Linea_Negocio_ID, prod.getUY_Linea_Negocio_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_ProductGroup_ID, prod.get_ValueAsInt("UY_ProductGroup_ID"));
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_Familia_ID, prod.getUY_Familia_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_SubFamilia_ID, prod.getUY_SubFamilia_ID());
			mTab.setValue(X_UY_RT_ConfirmProdScan.COLUMNNAME_C_TaxCategory_ID, prod.getC_TaxCategory_ID());
					
		} 
		
		return "";
	}
	
	/***
	 * Al seleccionar una lista de precios en ventana de Carga de Lista de Precios, se cargan los datos de la lista.
	 * OpenUp Ltda. #4403
	 * @author Nicolas Sarlabos - 30/06/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromPriceList (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		if (value == null) return "";
				
		int listID = (Integer)value;
		if (listID <= 0) return "";
		
		MPriceList list = new MPriceList(ctx, listID, null);//instancio la lista de precios
				
		if(list!=null && list.get_ID()>0){
			
			mTab.setValue(X_UY_PriceLoad.COLUMNNAME_C_Currency_ID, list.getC_Currency_ID());
			mTab.setValue(X_UY_PriceLoad.COLUMNNAME_Name, list.getName());
			mTab.setValue(X_UY_PriceLoad.COLUMNNAME_IsTaxIncluded, list.isTaxIncluded());
							
		} 
		
		return "";
	}
	
	/**
	 * En ventana de carga de lista de precios, al indicar moneda
	 * se carga la lista de venta por defecto según la org actaul
	 * OpenUp Ltda. #5389
	 * @author SBT - 29/01/2016 
	 */
	public String setSalePriceList (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		if (value == null) return "";
				
		int currID = (Integer)value;
		if (currID <= 0) return "";
				
		if(currID > 0){			
			int orgID = Integer.valueOf(mTab.getValue(X_UY_PriceLoad.COLUMNNAME_AD_Org_ID).toString());
			if (orgID>0){
				MPriceList lstVta = MPriceList.getPricListForOrg(ctx,orgID, currID,null);
				if(lstVta!= null)mTab.setValue(X_UY_PriceLoad.COLUMNNAME_M_PriceList_ID_2, lstVta.get_ID());
			}
							
		} 
		
		return "";
	}
	
	/***
	 * En ventana de carga de lista de precios, al seleccionar un producto se cargan sus datos.
	 * OpenUp Ltda. #4403
	 * @author Nicolas Sarlabos - 30/06/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromPriceLoadLine (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		if (value == null) {
			
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_ProdCode, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_Name, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_Description, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_C_UOM_ID, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_Linea_Negocio_ID, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_ProductGroup_ID, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_Familia_ID, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_SubFamilia_ID, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_C_TaxCategory_ID, null);			
			
			return "";
		}
				
		int prodID = (Integer)value;
		if (prodID <= 0) return "";
		
		MProduct prod = new MProduct(ctx, prodID, null);
				
		if(prod!=null && prod.get_ID()>0){ //si encuentro producto
			
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_ProdCode, prod.getValue());
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_Name, prod.getName());
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_Description, prod.getDescription());
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_C_UOM_ID, prod.getC_UOM_ID());
			if(prod.getUY_Linea_Negocio_ID()>0) {
				mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_Linea_Negocio_ID, prod.getUY_Linea_Negocio_ID());
			} else mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_Linea_Negocio_ID, null);
			
			if(prod.get_ValueAsInt("UY_ProductGroup_ID")>0) {
				mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_ProductGroup_ID, prod.get_ValueAsInt("UY_ProductGroup_ID"));
			} else mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_ProductGroup_ID, null);
			
			if(prod.getUY_Familia_ID()>0){
				mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_Familia_ID, prod.getUY_Familia_ID());
			} else mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_Familia_ID, null);
			
			if(prod.getUY_SubFamilia_ID()>0){
				mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_SubFamilia_ID, prod.getUY_SubFamilia_ID());
			} else mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_UY_SubFamilia_ID, null);
			
			if(prod.getC_TaxCategory_ID()>0){
				mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_C_TaxCategory_ID, prod.getC_TaxCategory_ID());
			} else mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_C_TaxCategory_ID, null);
					
		} 
		
		return "";
	}
	
	/***
	 * Scaneo de codigo de barras de producto para impresion de etiquetas.
	 * OpenUp Ltda. Issue #4470.
	 * @author Nicolas Sarlabos - 06/07/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String scanLabel(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		//String accountNo = replaceFirstCerosForAccountNo(value.toString().trim());
		//Integer nrocta = new Integer(value.toString());
		mTab.setValue(X_UY_LabelPrintScan.COLUMNNAME_ScanText, value.toString());		
		
		if (mTab.dataSave(true)){
			mTab.dataNew(false);
		}
		else{
			mTab.setValue(X_UY_LabelPrintScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}
	
	
	/***
	 * Scaneo de codigo de barras de producto para la modificacion de precios y creacion de listas.
	 * OpenUp Ltda. Issue 
	 * @author Emiliano Bentancor - 27/11/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String scanBarCode(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		//String accountNo = replaceFirstCerosForAccountNo(value.toString().trim());
		//Integer nrocta = new Integer(value.toString());
		mTab.setValue(X_UY_PriceUpdateScan.COLUMNNAME_ScanText, value.toString());		
		
		if (mTab.dataSave(true)){
			mTab.dataNew(false);
		}else{
			mTab.setValue(X_UY_PriceUpdateScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}

	
}
