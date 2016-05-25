/**
 * ValidatorOpenUp.java
 * 01/04/2011
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.acct.FactLine;
import org.compiere.apps.ADialog;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Movement;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MBankAccount;
import org.compiere.model.MCash;
import org.compiere.model.MClient;
import org.compiere.model.MConfirmOrderline;
import org.compiere.model.MConfirmorderhdr;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalBatch;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUOMConversion;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_AllocationHdr;
import org.compiere.model.X_C_Cash;
import org.compiere.model.X_C_Conversion_Rate;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_C_Payment;
import org.compiere.model.X_Fact_Acct;
import org.compiere.model.X_GL_Journal;
import org.compiere.model.X_GL_JournalBatch;
import org.compiere.model.X_M_InOut;
import org.compiere.model.X_M_Inventory;
import org.compiere.model.X_M_Movement;
import org.compiere.model.X_UY_Confirmorderhdr;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.MPPOrderBOMLine;
import org.eevolution.model.X_PP_Order;
import org.jruby.ext.socket.RubyUNIXSocket.LibCSocket.sockaddr_un.header;
import org.openup.beans.StockCost;
import org.openup.beans.StockIdentification;

import com.Verisign.payment.c;

/**
 * OpenUp.
 * ValidatorOpenUp
 * Descripcion : ModelValidator de OpenUp.
 * @author Gabriel Vila
 * Fecha : 01/04/2011
 */
public class ValidatorOpenUp implements ModelValidator {

	
	private int m_AD_Client_ID = 0;
	
	
	/**
	 * Constructor
	 */
	public ValidatorOpenUp() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.ModelValidationEngine, org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		
		if (client != null) m_AD_Client_ID = client.getAD_Client_ID();

		// DocActions Validations
		engine.addDocValidate(X_GL_JournalBatch.Table_Name, this);
		engine.addDocValidate(X_GL_Journal.Table_Name, this);
		engine.addDocValidate(X_C_Payment.Table_Name, this);
		engine.addDocValidate(X_C_Invoice.Table_Name, this);
		engine.addDocValidate(X_M_InOut.Table_Name, this);
		engine.addDocValidate(X_C_Cash.Table_Name, this);
		engine.addDocValidate(X_UY_ReservaPedidoHdr.Table_Name, this);
		engine.addDocValidate(X_C_Order.Table_Name, this);
		engine.addDocValidate(X_M_Movement.Table_Name, this);
		engine.addDocValidate(X_M_Inventory.Table_Name, this);
		engine.addDocValidate(X_PP_Order.Table_Name, this);
		engine.addDocValidate(X_UY_Confirmorderhdr.Table_Name, this);
		engine.addDocValidate(X_UY_MediosPago.Table_Name, this);
		engine.addDocValidate(X_UY_StockAdjustment.Table_Name, this);
		engine.addDocValidate(X_UY_RecuentoConf.Table_Name, this);
		engine.addDocValidate(X_UY_MovBancariosHdr.Table_Name, this);
		engine.addDocValidate(X_UY_ManufOrder.Table_Name, this);
		engine.addDocValidate(X_UY_ProductConsume.Table_Name, this);
		engine.addDocValidate(X_UY_ProdTransf.Table_Name, this);

		// DB Validations
		engine.addModelChange(X_Fact_Acct.Table_Name, this);
		engine.addModelChange(X_C_Conversion_Rate.Table_Name, this);
		engine.addModelChange(X_UY_MovBancariosLine.Table_Name, this);
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#getAD_Client_ID()
	 */
	@Override
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	}


	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#login(int, int, int)
	 */
	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#docValidate(org.compiere.model.PO, int)
	 */
	@Override
	public String docValidate(PO po, int timing) {

		if (po.get_TableName().equalsIgnoreCase(X_GL_JournalBatch.Table_Name))
			return docValidate((MJournalBatch) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_GL_Journal.Table_Name))
			return docValidate((MJournal) po, timing);
		
		if (po.get_TableName().equalsIgnoreCase(X_C_Payment.Table_Name))
			return docValidate((MPayment) po, timing);
		
		if (po.get_TableName().equalsIgnoreCase(X_C_Invoice.Table_Name))
			return docValidate((MInvoice) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_C_AllocationHdr.Table_Name))
			return docValidate((MAllocationHdr) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_M_InOut.Table_Name))
			return docValidate((MInOut) po, timing);
		
		if (po.get_TableName().equalsIgnoreCase(X_C_Cash.Table_Name))
			return docValidate((MCash) po, timing);
				
		if (po.get_TableName().equalsIgnoreCase(X_UY_ReservaPedidoHdr.Table_Name))
			return docValidate((MReservaPedidoHdr) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_C_Order.Table_Name))
			return docValidate((MOrder) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_PP_Order.Table_Name))
			return docValidate((MPPOrder) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_UY_Confirmorderhdr.Table_Name))
			return docValidate((MConfirmorderhdr) po, timing);
		
		if (po.get_TableName().equalsIgnoreCase(X_UY_MediosPago.Table_Name))
			return docValidate((MMediosPago) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_UY_MovBancariosHdr.Table_Name))
			return docValidate((MMovBancariosHdr) po, timing);
		
		if (po.get_TableName().equalsIgnoreCase(X_M_Inventory.Table_Name))
			return docValidate((MInventory) po, timing);
		
		if (po.get_TableName().equalsIgnoreCase(X_M_Movement.Table_Name))
			return docValidate((MMovement) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_UY_StockAdjustment.Table_Name))
			return docValidate((MStockAdjustment) po, timing);
		
		if (po.get_TableName().equalsIgnoreCase(X_UY_RecuentoConf.Table_Name)) 
			return docValidate((MRecuentoConf) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_UY_ManufOrder.Table_Name)) 
			return docValidate((MManufOrder) po, timing);

		if (po.get_TableName().equalsIgnoreCase(X_UY_ProductConsume.Table_Name)) 
			return docValidate((MProductConsume) po, timing);
		
		if (po.get_TableName().equalsIgnoreCase(X_UY_ProdTransf.Table_Name)) 
			return docValidate((MProdTransf) po, timing);

		
		return null;
	}


	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#modelChange(org.compiere.model.PO, int)
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception {

		if (po.get_TableName().equalsIgnoreCase(X_Fact_Acct.Table_Name))
			return modelChange((FactLine) po, type);

		if (po.get_TableName().equalsIgnoreCase(X_C_Conversion_Rate.Table_Name))
			return modelChange((MConversionRate) po, type);

		if (po.get_TableName().equalsIgnoreCase(X_UY_MovBancariosLine.Table_Name))
			return modelChange((MMovBancariosLine) po, type);
	
		return null;
	}

	/**
	 * OpenUp.	
	 * Descripcion : DB change on fact_acct.
	 * @param model
	 * @param type
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 01/04/2011
	 */
	public String modelChange(FactLine model, int type) throws Exception {

		if ((type == TYPE_BEFORE_NEW) || ((type == TYPE_BEFORE_CHANGE))) {
			
			// Moneda nacional 
			MClient client = new MClient(Env.getCtx(), model.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();

			int idMonedaNacional = schema.getC_Currency_ID();
			
			// Moneda de la cuenta de la linea contable
			MElementValue account= new MElementValue(model.getCtx(), model.getAccount_ID(), model.get_TrxName());
			if (account.isForeignCurrency())
				model.setUY_AccNat_Currency_ID(new BigDecimal(account.getC_Currency_ID()));
			else
				model.setUY_AccNat_Currency_ID(new BigDecimal(idMonedaNacional));
			
			// Si la moneda desde y hasta son distintas
			if (model.getC_Currency_ID() != model.getUY_AccNat_Currency_ID().intValueExact()){
				
				// Si la moneda nativa de la cuenta es moneda nacional
				if (model.getUY_AccNat_Currency_ID().intValueExact()==idMonedaNacional){
					// Tasa de cambio muliplicadora	si es nuevo
					if (type == TYPE_BEFORE_NEW){
						model.setDivideRate(MConversionRate.getRate(model.getC_Currency_ID(), model.getUY_AccNat_Currency_ID().intValueExact(), 
								model.getDateAcct(), 0, model.getAD_Client_ID(), model.getAD_Org_ID()));
					}
					
					model.setUY_AmtNativeCr(model.getAmtAcctCr());
					model.setUY_AmtNativeDr(model.getAmtAcctDr());					
				}
				else{
					// Si no vengo con tasa de cambio manual
					if ((model.getDivideRate() == null) || (model.getDivideRate().compareTo(Env.ZERO) == 0)){
						// Tasa de cambio divisora
						model.setDivideRate(MConversionRate.getDivideRate(model.getC_Currency_ID(), model.getUY_AccNat_Currency_ID().intValueExact(), 
																		  model.getDateAcct(), 0, model.getAD_Client_ID(), model.getAD_Org_ID()));
					}
					if (model.getDivideRate().compareTo(Env.ZERO)>0){
						// Montos en moneda nativa de la cuenta segun tasa de cambio
						model.setUY_AmtNativeCr(model.getAmtSourceCr().divide(model.getDivideRate(),2, RoundingMode.HALF_UP));
						model.setUY_AmtNativeDr(model.getAmtSourceDr().divide(model.getDivideRate(),2, RoundingMode.HALF_UP));					
					}
				}
			}
			else{
				// Mismas monedas, entonces tasa de cambio = 1
				model.setDivideRate(Env.ONE);
				model.setUY_AmtNativeCr(model.getAmtSourceCr());
				model.setUY_AmtNativeDr(model.getAmtSourceDr());					
			}
		}		
		return null;
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : DB change on C_Conversion_Rate
	 * @param model
	 * @param type
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 01/04/2011
	 */
	public String modelChange(MConversionRate model, int type) throws Exception {

		if (type == TYPE_AFTER_NEW) {
			
			// Si es una tasa cargada automaticamente (o sea no es manual), salgo sin hacer nada.
			if (!((Boolean)model.get_Value("ismanual"))) 
				return null;
			
			// Para una tasa de cambio ingresada para una moneda origen A 
			// y una moneda destino B, genero un nuevo registro para la operacion inversa.
			MConversionRate newTasa = new MConversionRate(model.getCtx(), 0, model.get_TrxName());
			newTasa.setC_Currency_ID(model.getC_Currency_ID_To());
			newTasa.setC_Currency_ID_To(model.getC_Currency_ID());
			newTasa.setValidFrom(model.getValidFrom());
			newTasa.setValidTo(model.getValidTo());
			newTasa.setMultiplyRate(model.getDivideRate());
			newTasa.setDivideRate(model.getMultiplyRate());
			newTasa.setC_ConversionType_ID(model.getC_ConversionType_ID());
			newTasa.set_ValueOfColumn("ismanual", "N");
			newTasa.setAD_Client_ID(model.getAD_Client_ID());
			newTasa.setAD_Org_ID(model.getAD_Org_ID());
			newTasa.save();
		}

		return null;
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : DB change on C_PaymentAllocate
	 * @param model
	 * @param type
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 01/04/2011
	 */
	public String modelChange(MPaymentAllocate model, int type) throws Exception {
		
		MPayment payment = new MPayment(model.getCtx(), model.getC_Payment_ID(), model.get_TrxName());
		if (payment.getC_Payment_ID() <= 0) return null;
		
		if (type == TYPE_AFTER_NEW) {
			payment.setOverUnderAmt(model.getRemainingAmt());
			payment.saveEx();
		}		
		else if (type == TYPE_BEFORE_DELETE ) {
			payment.setOverUnderAmt(payment.getOverUnderAmt().add(model.getAmount()));
			payment.saveEx();
		}
		
		return null;
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Verifica que este cargado el tipo de cambio del dia actual para moneda
	 * del model recibido por parametro.
	 * @param po. Modelo.
	 * @param cCurrencyID. int: ID de moneda a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 05/04/2011
	 */
	private String verifyConversionRate(PO model, int cCurrencyID, Timestamp fecha){//OpenUp M.R. 18-08-2011 Issue#650 Agrego parametro de fecha para la comprobacion de la tasa de cambio	

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String value = null, sql = "";
		
		try
		{
			// Moneda nacional 
			MClient client = new MClient(Env.getCtx(), model.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();

			int currencyFrom = schema.getC_Currency_ID();
			int currencyTo = cCurrencyID;

			if (currencyFrom == 0) currencyFrom = currencyTo;
			
			if (currencyTo == currencyFrom) return null;
			
			sql=" SELECT dividerate " +
					   " FROM C_Conversion_Rate " +
					   " WHERE ad_client_id = ? " +
					   " AND c_currency_id = ? " +
					   " AND c_currency_id_to = ? " +
					   " AND validfrom =  ? " + 
					   " AND validto = validfrom";

			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, model.getAD_Client_ID());
			pstmt.setInt(2, currencyFrom);
			pstmt.setTimestamp(4,  fecha);
			pstmt.setInt(3, currencyTo);
			rs = pstmt.executeQuery ();

			if (!rs.next ()){//OpenUp M.R. 18-08-2011 Issue#650 agrego parentesis olvidado para que no muestre el mensaje sin error
				value = "Debe ingresarse la Tasa de Cambio para la fecha de la Transacción.";
				ADialog.info(0,null,null, value.toString());
			}// Fin OpenUp
		}
		catch (Exception e)
		{
			CLogger.getCLogger(MConversionRate.class).log(Level.SEVERE, sql, e);
			ADialog.info(0,null,null, e.toString());
			//Fin OpenUp
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : DB change on UY_MovBancariosLine 
	 * @param model
	 * @param type
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 01/04/2011
	 */
	public String modelChange(MMovBancariosLine model, int type) throws Exception {
		
		if ((type == TYPE_AFTER_NEW)||(type == TYPE_AFTER_DELETE)||(type == TYPE_AFTER_CHANGE) ) {

			BigDecimal importeLinea = model.getuy_totalamt();
			
			// Si estoy eliminando la linea de pago  
			if (type == TYPE_AFTER_DELETE){
				// El importe es cero para que no lo considere en el calculo para el subtotal del recibo de pago
				importeLinea=new BigDecimal(0);
			}

			MMovBancariosHdr header = new MMovBancariosHdr(model.getCtx(), model.getUY_MovBancariosHdr_ID(), model.get_TrxName());
			
			// Si no es una transferencia bancaria
			if (!MDocType.getDocBaseType(header.getC_DocType_ID()).equalsIgnoreCase(Doc.DOCTYPE_Banco_Transferencias)){
				BigDecimal subTotal = this.getSubTotalMovBancarios(model.getUY_MovBancariosHdr_ID(), model.getUY_MovBancariosLine_ID()).add(importeLinea); 
				if (header.getuy_intereses()==null) header.setuy_intereses(Env.ZERO);
				header.setuy_totalme(subTotal.subtract(header.getuy_intereses()));
				header.saveEx();
			}
		}
		
		return null;
	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna subtotal de un movimiento bancario.
	 * @param headerID
	 * @param lineID
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 01/04/2011
	 */
	private BigDecimal getSubTotalMovBancarios (int headerID, int lineID)
	{
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal subTotal = new BigDecimal(0);		
		
		try{			
			// Obtengo suma de totales de lineas
			sql = "SELECT COALESCE(SUM(uy_totalamt),0) as subtotal " +
				  " FROM UY_MovBancariosLine "  +
				  " WHERE UY_MovBancariosHdr_ID =?" + 
				  " AND UY_MovBancariosLine_ID <>?";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, headerID);
			pstmt.setInt(2, lineID);
			rs = pstmt.executeQuery ();
			if (rs.next()) subTotal = rs.getBigDecimal("subtotal");
		}
		catch (Exception e)
		{
			CLogger.getCLogger(MMovBancariosLine.class).log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return subTotal;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Validacion de DocAction en Ordenes.
	 * @param ordHdr
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 10/03/2011
	 */
	private String docValidate(MOrder ordHdr, int timing)
	{
		String message = null;

		//OpenUp #897 Nicolas Sarlabos 12/10/2011
		//no se permite anular orden de compra si la misma tiene una recepcion
		if (timing == TIMING_BEFORE_VOID){
			if (!ordHdr.isSOTrx()){
				String msg = ordHdr.haveReceptions();
				if (msg!=null)
					message = msg; 
					
			}
		}
				
		// Manejo de Stock. Al completar, reactivar o anular una orden de venta.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE 
					|| timing == TIMING_AFTER_VOID || timing == TIMING_AFTER_CLOSE) {

			MOrderLine[] lines = ordHdr.getLines(true, null);

			for (int i=0; i<lines.length; i++){
				
				
				BigDecimal qty = lines[i].getQtyOrdered();
				// OpenUp. Gabriel Vila. 26/08/2011. Issue #858
				if (ordHdr.isSOTrx()) {
					if (lines[i].getQtyReserved() != null) {
						if (lines[i].getQtyReserved().compareTo(Env.ZERO) >= 0) qty = qty.subtract(lines[i].getQtyReserved());
					}
					if (lines[i].getQtyInvoiced() != null) {
						if (lines[i].getQtyInvoiced().compareTo(Env.ZERO) >= 0) qty = qty.subtract(lines[i].getQtyInvoiced());
					}
				}
				// Fin Issue #858.
				
				// Me aseguro cantidad positiva
				if (qty.compareTo(Env.ZERO) < 0) qty = qty.multiply(new BigDecimal(-1));
				
				// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
				if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
					qty = qty.multiply(new BigDecimal(-1));
				}
				
				int statusStockID = 0;
				
				// Si es venta o compra
				if (ordHdr.isSOTrx()){
					// El estado de stock a mover depende de si las ordenes de venta
					// reservan stock al completarse o quedan pendientes
					if (MSysConfig.getBooleanValue("UY_SALEORDER_RESERVE_STOCK", false, ordHdr.getAD_Client_ID()))
						statusStockID = MStockStatus.getStatusReservedID(null);
					else
						statusStockID = MStockStatus.getStatusPendingID(null);
					
					message = MStockTransaction.add(ordHdr, null, lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 
													lines[i].getM_AttributeSetInstance_ID(), statusStockID, ordHdr.getDateOrdered(), qty,
													lines[i].getC_OrderLine_ID(), null);

					if (message != null) return message;					
				}
				else{
					// OpenUp Nicolas Sarlabos 26/06/2012 #1033 
					// Para compras, cuando se cierra la compra doy de baja estado transito pendiente
					if (timing == TIMING_AFTER_CLOSE){
						qty = lines[i].getQtyReserved();  //obtengo la cantidad reservada(pendiente para recibir)
						qty = qty.multiply(new BigDecimal(-1));
					}
					//Fin OpenUp Nicolas Sarlabos 26/06/2012 #1033
					if (MSysConfig.getBooleanValue("UY_PORDER_TRANSIT_STOCK", true, ordHdr.getAD_Client_ID())){
						statusStockID = MStockStatus.getStatusTransitID(null);
						message = MStockTransaction.add(ordHdr, null, lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 
								lines[i].getM_AttributeSetInstance_ID(), statusStockID, ordHdr.getDateOrdered(), qty, 
								lines[i].getC_OrderLine_ID(), null);
						if (message != null) return message;					
					}	
				}
				
				// Chequeo disponibilidad solo si el pedido mueve reservado
				if (MSysConfig.getBooleanValue("UY_SALEORDER_RESERVE_STOCK", false, ordHdr.getAD_Client_ID())){

					// Disponibilidad a fecha de movimiento
					message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), 0, 
							lines[i].getM_Product_ID(), 0, ordHdr.getDateOrdered(), ordHdr.getAD_Client_ID(), ordHdr.get_TrxName());
					if (message != null) return message;

					// Disponibilidad a hoy
					message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), 0, 
							lines[i].getM_Product_ID(), 0, 
							TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
							ordHdr.getAD_Client_ID(), ordHdr.get_TrxName());
					if (message != null) return message;
					
				}
				
			}			
		}
		return message;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Validacion de DocAction en Ordenes de Proceso.
	 * @param ppOrdHdr
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 21/03/2011
	 */
	private String docValidate(MPPOrder ppOrdHdr, int timing){

		String message = null;

		// Manejo de Stock. Al procesar, reactivar o anular.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE || timing == TIMING_AFTER_VOID) {
			
			MPPOrderBOMLine[] lines = ppOrdHdr.getLines(true);

			for (int i=0; i<lines.length; i++){
				
				BigDecimal qty = lines[i].getQtyReserved();

				// Me aseguro cantidad positiva
				if (qty.compareTo(Env.ZERO) < 0) qty = qty.multiply(new BigDecimal(-1));
				
				// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
				if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
					qty = qty.multiply(new BigDecimal(-1));
				}
		
				// muevo estado reservado 
				int statusReservedID = MStockStatus.getStatusReservedID(null);
				message = MStockTransaction.add(ppOrdHdr, null, lines[i].getM_Warehouse_ID(), 
						lines[i].getM_Locator_ID(), lines[i].getM_Product_ID(), 
						lines[i].getM_AttributeSetInstance_ID(), statusReservedID, ppOrdHdr.getDateOrdered(), qty,
						lines[i].getPP_Order_BOMLine_ID(), null);
				if (message != null) return message;
				
				// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock
				
				// Disponibilidad a fecha de movimiento
				message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
						lines[i].getM_Product_ID(), lines[i].getM_AttributeSetInstance_ID(), ppOrdHdr.getDateOrdered(), 
						ppOrdHdr.getAD_Client_ID(), ppOrdHdr.get_TrxName());
				if (message != null) return message;
				
				// Disponibilidad a hoy
				message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
						lines[i].getM_Product_ID(), lines[i].getM_AttributeSetInstance_ID(), 
						TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
						ppOrdHdr.getAD_Client_ID(), ppOrdHdr.get_TrxName());
				if (message != null) return message;

			}
		}
		
		return message;
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Validacion de DocAction en Reservas de Stock.
	 * @param resHdr
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 06/01/2011
	 */
	private String docValidate(MReservaPedidoHdr resHdr, int timing){
		
		String message = null;
		
		// Manejo de Stock. Al completar, reactivar o anular.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE || timing == TIMING_AFTER_VOID) {
			
			MReservaPedidoLine[] lines = resHdr.getLines(resHdr.get_TrxName());

			for (int i=0; i<lines.length; i++){
				
				BigDecimal qty = lines[i].getQtyReserved();

				// Me aseguro cantidad positiva
				if (qty.compareTo(Env.ZERO) < 0) qty = qty.multiply(new BigDecimal(-1));
				
				// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
				if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
					qty = qty.multiply(new BigDecimal(-1));
				}
		
				// Subo estado de stock reservado 
				int statusReservedID = MStockStatus.getStatusReservedID(null);
				message = MStockTransaction.add(resHdr, null, lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 
						lines[i].getM_AttributeSetInstance_ID(), statusReservedID, resHdr.getdatereserved(), qty,
						lines[i].getUY_ReservaPedidoLine_ID(), null);
				
				if (message != null) return message;
				
				// Bajo estado de stock pendiente que se genero en la orden de venta
				int statusPendingID = MStockStatus.getStatusPendingID(null);
				MOrder orderHdr = new MOrder(resHdr.getCtx(), resHdr.getC_Order_ID(), resHdr.get_TrxName());
				message = MStockTransaction.add(resHdr, orderHdr, lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 
						lines[i].getM_AttributeSetInstance_ID(), statusPendingID, resHdr.getdatereserved(), 
						qty.subtract(lines[i].getUY_BonificaReglaUM()).multiply(new BigDecimal(-1)),
						lines[i].getUY_ReservaPedidoLine_ID(), null);
				
				if (message != null) return message;
				
				// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock
				
				// Disponibilidad a fecha del movimiento
				message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), 0, 
						lines[i].getM_Product_ID(), lines[i].getM_AttributeSetInstance_ID(), resHdr.getdatereserved(), 
						resHdr.getAD_Client_ID(), resHdr.get_TrxName());
				if (message != null) return message;
				
				// Disponibilidad a hoy
				message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), 0, 
						lines[i].getM_Product_ID(), lines[i].getM_AttributeSetInstance_ID(), 
						TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
						resHdr.getAD_Client_ID(), resHdr.get_TrxName());
				if (message != null) return message;
				
			}
		}
		
		if ( (timing == TIMING_BEFORE_VOID) || (timing == TIMING_BEFORE_REACTIVATE)){
			// Verifico si hay alguna linea de asignacion de transporte con 
			// este hdr de reserva y que no este en estado VOID
			if (MAsignaTransporteLine.contieneReservaPedido(resHdr.getUY_ReservaPedidoHdr_ID(), resHdr.get_TrxName())){
				message = "No es posible CANCELAR esta Reserva ya que se encuentra Asignada a Un Transporte.";
			}
			
		}
		
		return message;
	}

	/**
	 * OpenUp.	Validacion de DocAction en Journal Batch.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 05/04/2011
	 */
	private String docValidate(MJournalBatch model, int timing){
		
		String validacion = null;
		
		if (timing==TIMING_BEFORE_COMPLETE || timing == TIMING_BEFORE_POST){
			validacion = this.verifyConversionRate(model, model.getC_Currency_ID(), model.getDateDoc());//OpenUp M.R. 18-08-2011 Issue#650 Agrego parametro de fecha para la comprobacion de la tasa de cambio		
		}
		return validacion;
	}

	/**
	 * OpenUp.	Validacion de DocAction en Journal.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 05/04/2011
	 */
	private String docValidate(MJournal model, int timing){
		
		String validacion = null;
		
		if (timing==TIMING_BEFORE_COMPLETE || timing == TIMING_BEFORE_POST){
			validacion = this.verifyConversionRate(model, model.getC_Currency_ID(), model.getDateAcct());//OpenUp M.R. 18-08-2011 Issue#650 Agrego parametro de fecha para la comprobacion de la tasa de cambio		
		}
		return validacion;
	}
		
	/**
	 * OpenUp.	Validacion de DocAction en Payment.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 05/04/2011
	 */
	private String docValidate(MPayment model, int timing){
		
		String message = null;

		// Si estoy completando o ( estoy anulando y es un recibo de pago), entonces
		// guardo tracking de los cheques del recibo. Si estoy anulando y es un recibo de cobranza no llevo tracking ya que 
		// los cheques de tercero se eliminan en el modelo.
		if ((timing == TIMING_AFTER_COMPLETE) || (timing == TIMING_AFTER_VOID) || (timing == TIMING_AFTER_REACTIVATE)) {
		
			// Obtengo y recorro lineas del recibo
			MLinePayment lines[] = model.getLines(model.get_TrxName());
			for (int i=0; i < lines.length; i++){
				
				if (lines[i].getUY_MediosPago_ID() > 0) {
					MMediosPago mp = new MMediosPago(model.getCtx(), lines[i].getUY_MediosPago_ID(), model.get_TrxName());
					if (mp.get_ID() > 0){
			
						// Nuevo item en trazabilidad del medio de pago
						MCheckTracking track = new MCheckTracking(model.getCtx(), 0, model.get_TrxName());
						track.setUY_MediosPago_ID(lines[i].getUY_MediosPago_ID());
						track.setC_BPartner_ID(model.getC_BPartner_ID());
						track.setC_DocType_ID(model.getC_DocType_ID());
						track.setAD_Table_ID(model.get_Table_ID());
						track.setRecord_ID(model.get_ID());
						track.setDocumentNo(model.getDocumentNo());
						track.setDateTrx(model.getDateTrx());
						
						if (timing == TIMING_AFTER_COMPLETE)
							track.setDocAction(DocumentEngine.ACTION_Complete);
						if (timing == TIMING_AFTER_VOID)
							track.setDocAction(DocumentEngine.ACTION_Void);
						if (timing == TIMING_AFTER_REACTIVATE)
							track.setDocAction(DocumentEngine.ACTION_ReActivate);
						
						track.setCheckStatus(mp.getestado());
						track.setCheckOldStatus(mp.getoldstatus());
						if (track.getCheckOldStatus() == null) track.setCheckOldStatus(track.getCheckStatus());
						if (track.getCheckOldStatus().equalsIgnoreCase("")) track.setCheckOldStatus(track.getCheckStatus());
						track.saveEx();

					}
					else{
						// Al anular 
						if ((timing == TIMING_AFTER_COMPLETE) || (timing == TIMING_AFTER_VOID && !model.isReceipt())){
							message = "No se pudo obtener medio de pago con identificador : " + lines[i].getUY_MediosPago_ID();	
						}
					}
				}
				
				// OpenUp. Gabriel Vila. 13/05/2013. Issue #822.
				// Para recibos que mueven cuentas bancarias, actualizo tabla sumarizada de estados de cuenta bancario.
				if (lines[i].getC_BankAccount_ID() > 0) {
					if (timing == TIMING_AFTER_COMPLETE){
						MSUMAccountStatus sumba = new MSUMAccountStatus(model.getCtx(), 0, model.get_TrxName());
						sumba.setC_BankAccount_ID(lines[i].getC_BankAccount_ID());
						sumba.setDateTrx(model.getDateTrx());
						sumba.setC_DocType_ID(model.getC_DocType_ID());
						sumba.setDocumentNo(model.getDocumentNo());
						sumba.setAD_Table_ID(I_C_Payment.Table_ID);
						sumba.setRecord_ID(model.get_ID());
						sumba.setDueDate(lines[i].getDueDate());
						sumba.setC_BPartner_ID(model.getC_BPartner_ID());
						sumba.setDescription(model.getDescription());
						
						if (lines[i].getUY_MediosPago_ID() > 0) {
							MMediosPago mp = new MMediosPago(model.getCtx(), lines[i].getUY_MediosPago_ID(), model.get_TrxName());
							if (mp.get_ID() > 0){
								sumba.setCheckNo(mp.getCheckNo());
								sumba.setDueDate(mp.getDueDate());
								sumba.setStatus(mp.getestado());
								sumba.setUY_MediosPago_ID(mp.get_ID());
								
								if ((sumba.getDescription() == null) || (sumba.getDescription().equalsIgnoreCase(""))){
									sumba.setDescription(mp.getMicr());
								}
							}
						}
							
						sumba.setC_Currency_ID(model.getC_Currency_ID());
						sumba.setC_BPartner_ID(model.getC_BPartner_ID());
						sumba.setamtdocument(lines[i].getPayAmt());
						if (model.isReceipt()){
							sumba.setAmtSourceCr(lines[i].getPayAmt());
							sumba.setAmtSourceDr(Env.ZERO);
							sumba.setAmtAcctDr(Env.ZERO);
						}
						else{
							sumba.setAmtSourceDr(lines[i].getPayAmt());
							sumba.setAmtSourceCr(Env.ZERO);
							sumba.setAmtAcctCr(Env.ZERO);
						}
						
						BigDecimal currencyRate = Env.ONE;
						MBankAccount ba = (MBankAccount)lines[i].getC_BankAccount();
						if (ba.getC_Currency_ID() != model.getC_Currency_ID()){
							currencyRate = MConversionRate.getRate(model.getC_Currency_ID(), ba.getC_Currency_ID(), model.getDateTrx(), 0, model.getAD_Client_ID(), 0);
							if (currencyRate == null) throw new AdempiereException("No se encuentra tasa de cambio para fecha : " + model.getDateTrx());
						}
						
						sumba.setCurrencyRate(currencyRate);
						if (currencyRate.compareTo(Env.ONE) > 0){
							sumba.setAmtAcctDr(sumba.getAmtSourceDr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
							sumba.setAmtAcctCr(sumba.getAmtSourceCr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
						}
						else{
							sumba.setAmtAcctDr(sumba.getAmtAcctDr());
							sumba.setAmtAcctCr(sumba.getAmtAcctCr());
						}
						
						sumba.saveEx();
					}
					else if (timing == TIMING_AFTER_VOID || timing == TIMING_AFTER_REACTIVATE){
						String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_C_Payment.Table_ID +
										" and record_id = " + model.get_ID();
						DB.executeUpdateEx(action, model.get_TrxName());
					}
				}
				// Fin OpenUp. Issue #822.
				
			}		
		}
		
		if (timing == TIMING_BEFORE_POST){
			message = this.verifyConversionRate(model, model.getC_Currency_ID(), model.getDateTrx());//OpenUp M.R. 18-08-2011 Issue#650 Agrego parametro de fecha para la comprobacion de la tasa de cambio		
		}
		
		return message;
	}
	
	/**
	 * OpenUp.	Validacion de DocAction en Invoice.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 05/04/2011
	 */
	private String docValidate(MInvoice model, int timing){

		String message = null;
		
		if ((timing == TIMING_AFTER_COMPLETE) || (timing == TIMING_AFTER_VOID)){
			
			MDocType doc = new MDocType(model.getCtx(), model.getC_DocTypeTarget_ID(), null);
			
			// Proceso lineas contado
			if (model.getpaymentruletype() != null){
				if (model.getpaymentruletype().equalsIgnoreCase(X_UY_PaymentRule.PAYMENTRULETYPE_CONTADO)){
					
					if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_ARInvoice) || 
							doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){

						// Obtengo y recorro lineas de contado
						List<MInvoiceCashPayment> lines = model.getCashPaymentLines(model.get_TrxName());
						for (MInvoiceCashPayment line: lines){

							if (line.getUY_MediosPago_ID() > 0) {

								MMediosPago mp = (MMediosPago)line.getUY_MediosPago();

								if (mp != null){

									// Nueva Trazabilidad del medio de pago
									MCheckTracking track = new MCheckTracking(model.getCtx(), 0, model.get_TrxName());
									track.setUY_MediosPago_ID(mp.get_ID());
									track.setC_BPartner_ID(model.getC_BPartner_ID());
									track.setC_DocType_ID(model.getC_DocTypeTarget_ID());
									track.setAD_Table_ID(model.get_Table_ID());
									track.setRecord_ID(model.get_ID());
									track.setDocumentNo(model.getDocumentNo());
									track.setDateTrx(model.getDateInvoiced());
									if (timing == TIMING_AFTER_COMPLETE)
										track.setDocAction(DocumentEngine.ACTION_Complete);
									if (timing == TIMING_AFTER_VOID)
										track.setDocAction(DocumentEngine.ACTION_Void);
									track.setCheckStatus(mp.getestado());
									track.setCheckOldStatus(mp.getoldstatus());
									if (track.getCheckOldStatus() == null) track.setCheckOldStatus(track.getCheckStatus());
									if (track.getCheckOldStatus().equalsIgnoreCase("")) track.setCheckOldStatus(track.getCheckStatus());
									track.saveEx();
								}
							}
							
							// Para documentos que mueven cuentas bancarias, actualizo tabla sumarizada de estados de cuenta bancario.
							if (line.getC_BankAccount_ID() > 0) {
								if (timing == TIMING_AFTER_COMPLETE){
									MSUMAccountStatus sumba = new MSUMAccountStatus(model.getCtx(), 0, model.get_TrxName());
									sumba.setC_BankAccount_ID(line.getC_BankAccount_ID());
									sumba.setDateTrx(TimeUtil.trunc(model.getDateInvoiced(),TimeUtil.TRUNC_DAY));
									sumba.setC_DocType_ID(model.getC_DocTypeTarget_ID());
									sumba.setDocumentNo(model.getDocumentNo());
									sumba.setAD_Table_ID(I_C_Invoice.Table_ID);
									sumba.setRecord_ID(model.get_ID());
									sumba.setC_BPartner_ID(model.getC_BPartner_ID());
									sumba.setDescription(model.getDescription());
									
									if (line.getUY_MediosPago_ID() > 0) {
										MMediosPago mp = (MMediosPago)line.getUY_MediosPago();
										sumba.setCheckNo(mp.getCheckNo());
										sumba.setDueDate(TimeUtil.trunc(mp.getDueDate(),TimeUtil.TRUNC_DAY));
										sumba.setStatus(mp.getestado());
										sumba.setUY_MediosPago_ID(mp.get_ID());
									}
									else{
										sumba.setDueDate(TimeUtil.trunc(line.getDueDate(),TimeUtil.TRUNC_DAY));
									}
									
									int cCurrencyID = line.getC_Currency_ID();
									if (cCurrencyID <= 0){
										cCurrencyID = model.getC_Currency_ID();
									}
									
									sumba.setC_Currency_ID(cCurrencyID);
									sumba.setamtdocument(line.getAmount());
									if (model.isSOTrx()){
										sumba.setAmtSourceCr(line.getAmount());
										sumba.setAmtSourceDr(Env.ZERO);
										sumba.setAmtAcctDr(Env.ZERO);
									}
									else{
										sumba.setAmtSourceDr(line.getAmount());
										sumba.setAmtSourceCr(Env.ZERO);
										sumba.setAmtAcctCr(Env.ZERO);
									}
									
									BigDecimal currencyRate = Env.ONE;
									MBankAccount ba = (MBankAccount)line.getC_BankAccount();
									if (ba.getC_Currency_ID() != model.getC_Currency_ID()){
										currencyRate = line.getCurrencyRate();
									}
									sumba.setCurrencyRate(currencyRate);
									if (currencyRate.compareTo(Env.ONE) > 0){
										sumba.setAmtAcctDr(sumba.getAmtSourceDr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
										sumba.setAmtAcctCr(sumba.getAmtSourceCr().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
									}
									else{
										sumba.setAmtAcctDr(sumba.getAmtAcctDr());
										sumba.setAmtAcctCr(sumba.getAmtAcctCr());
									}
									
									sumba.saveEx();
								}
								else if (timing == TIMING_AFTER_VOID || timing == TIMING_AFTER_REACTIVATE){
									String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_C_Invoice.Table_ID +
													" and record_id = " + model.get_ID();
									DB.executeUpdateEx(action, model.get_TrxName());
								}
							}
						}
					}
				}
			}

		}


		if (timing == TIMING_AFTER_COMPLETE) {
			
			MDocType doc = new MDocType(model.getCtx(), model.getC_DocTypeTarget_ID(), null);				
			if (doc.get_ID() > 0){
				// Al completar un remito me aseguro de dejar precios, importes e impuestos en cero.
				if (doc.getName().trim().equalsIgnoreCase("Remito")){
					DB.executeUpdateEx("update c_invoiceline set priceactual = 0, linenetamt =0, priceentered=0, " +
							         "pricelimit = 0 where c_invoice_id =" + model.getC_Invoice_ID(), model.get_TrxName());
					DB.executeUpdateEx("update c_invoice set grandtotal = 0, totallines = 0 " +
									 " where c_invoice_id =" + model.getC_Invoice_ID(), model.get_TrxName());
					DB.executeUpdateEx("update c_invoicetax set taxbaseamt = 0 where c_invoice_id =" + model.getC_Invoice_ID(), model.get_TrxName());
				}
			}
			
			// OpenUp. Gabriel Vila. 24/07/2014. Issue #1405.
			// Si gestiono partidas de stock y consumos
			if (MSysConfig.getBooleanValue("UY_STOCK_HANDLE_CONSUME", false, model.getAD_Client_ID())){

				// Si es una invoice de compra o devolucion
				if (!model.isSOTrx()){
				
					MClient client = new MClient(model.getCtx(), model.getAD_Client_ID(), null);
					MAcctSchema sch = client.getAcctSchema();
					
					// Obtengo y recorro lineas de documento
					MInvoiceLine[] lines = model.getLines(false);
					for (int i = 0; i < lines.length; i++) {
						
						MInvoiceLine line = lines[i];
						
						// Si esta linea de invoice esta asociada a una linea de entrega
						if (line.getM_InOutLine_ID() > 0){
							
							// Actualizo costos en la partida de stock generada por la entrega y en la ficha de stock
							StockCost stkCostInfo = new StockCost();
							stkCostInfo.cCurrencyID = model.getC_Currency_ID();
							stkCostInfo.currencyRate = model.getCurrencyRate();
							stkCostInfo.amtSource = line.getPriceEntered();
							stkCostInfo.amtAcct = line.getPriceEntered();

							if (stkCostInfo.cCurrencyID != sch.getC_Currency_ID()){
								if (model.getCurrencyRate() != null){
									if (model.getCurrencyRate().compareTo(Env.ONE) > 0){
										stkCostInfo.amtAcct = line.getPriceEntered().multiply(model.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP);	
									}
								}
							}
							
							message = MStockConsume.updateConsumeCost(model.getCtx(), model.getAD_Client_ID(), I_M_InOut.Table_ID, line.getM_InOutLine_ID(), stkCostInfo, model.get_TrxName());
							
							if (message != null) return message; // Si la actualizacion de costos de partida da una mensaje, lo retorno ahora.
						}
						
					} // for
				}
			}
			// Fin Issue #1405
		}
		
		if (timing == TIMING_BEFORE_VOID){
			if ( (model.getDocStatusReason() == null) || (model.getDocStatusReason().equalsIgnoreCase("")))
				if (model.isSOTrx())
					message = "No se puede Anular Documento sin poner el MOTIVO.";
		}
		
		if (timing == TIMING_BEFORE_POST){
			message = this.verifyConversionRate(model, model.getC_Currency_ID(), model.getDateInvoiced());//OpenUp M.R. 18-08-2011 Issue#650 Agrego parametro de fecha para la comprobacion de la tasa de cambio	
		}
		return message;
	}

	/**
	 * OpenUp.	Validacion de DocAction en Medios de Pago. 
	 * @param model
	 * @param timing
	 * @return
	 */
	private String docValidate(MMediosPago model, int timing){

		String message = null;

		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE 
			|| timing == TIMING_AFTER_VOID) {
		
			// OpenUp. Gabriel Vila. 12/04/2013. Issue #684.
			// Si este cheque esta asociado a una resma, verifico si la resma debe quedar marcada como totalmente emitida.
			if (timing == TIMING_AFTER_COMPLETE){
				if (model.getUY_CheckReam_ID() > 0){
					MCheckReam ream = (MCheckReam)model.getUY_CheckReam();
					ream.verifyEmitted();
				}
			}
			// Fin OpenUp. Issue #684.
			
			// Nuevo item en trazabilidad del medio de pago
			MCheckTracking track = new MCheckTracking(model.getCtx(), 0, model.get_TrxName());
			track.setUY_MediosPago_ID(model.getUY_MediosPago_ID());
			track.setC_BPartner_ID(model.getC_BPartner_ID());
			track.setC_DocType_ID(model.getC_DocType_ID());
			track.setAD_Table_ID(model.get_Table_ID());
			track.setRecord_ID(model.get_ID());
			track.setDocumentNo(model.getDocumentNo());
			track.setDateTrx(model.getDateTrx());
			
			if (timing == TIMING_AFTER_COMPLETE)
				track.setDocAction(DocumentEngine.ACTION_Complete);
			if (timing == TIMING_AFTER_VOID)
				track.setDocAction(DocumentEngine.ACTION_Void);
			if (timing == TIMING_AFTER_REACTIVATE)
				track.setDocAction(DocumentEngine.ACTION_ReActivate);
			
			track.setCheckStatus(model.getestado());
			track.setCheckOldStatus(model.getoldstatus());
			if (track.getCheckOldStatus() == null) track.setCheckOldStatus(track.getCheckStatus());
			if (track.getCheckOldStatus().equalsIgnoreCase("")) track.setCheckOldStatus(track.getCheckStatus());
			track.saveEx();
		}

		
		if (timing == TIMING_BEFORE_POST){
			message = this.verifyConversionRate(model, model.getC_Currency_ID(), model.getDateTrx());		
		}
		
		return message;
	}

	/**
	 * OpenUp.	Validacion de DocAction en Transacciones bancarias. 
	 * @param model
	 * @param timing
	 * @return
	 */
	private String docValidate(MMovBancariosHdr model, int timing){

		String message = null;

		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE 
			|| timing == TIMING_AFTER_VOID) {
		
			// Si tengo un medio de pago asociado directamente con este cabezal 
			if (model.getUY_MediosPago_ID() > 0){
				// Nuevo item en trazabilidad del medio de pago
				MCheckTracking track = new MCheckTracking(model.getCtx(), 0, model.get_TrxName());
				track.setUY_MediosPago_ID(model.getUY_MediosPago_ID());
				track.setC_BPartner_ID(model.getC_BPartner_ID());
				track.setC_DocType_ID(model.getC_DocType_ID());
				track.setAD_Table_ID(model.get_Table_ID());
				track.setRecord_ID(model.get_ID());
				track.setDocumentNo(model.getDocumentNo());
				track.setDateTrx(model.getDateTrx());

				if (timing == TIMING_AFTER_COMPLETE)
					track.setDocAction(DocumentEngine.ACTION_Complete);
				if (timing == TIMING_AFTER_VOID)
					track.setDocAction(DocumentEngine.ACTION_Void);
				if (timing == TIMING_AFTER_REACTIVATE)
					track.setDocAction(DocumentEngine.ACTION_ReActivate);
				
				MMediosPago mp = new MMediosPago(model.getCtx(), model.getUY_MediosPago_ID(), model.get_TrxName());
				track.setCheckStatus(mp.getestado());
				track.setCheckOldStatus(mp.getoldstatus());
				if (track.getCheckOldStatus() == null) track.setCheckOldStatus(track.getCheckStatus());
				if (track.getCheckOldStatus().equalsIgnoreCase("")) track.setCheckOldStatus(track.getCheckStatus());
				track.saveEx();
			}
			
			// Obtengo y recorro lineas del movimiento bancario
			MMovBancariosLine lines[] = model.getLinesArray(model.get_TrxName());
			for (int i=0; i < lines.length; i++){
				if (lines[i].getUY_MediosPago_ID() > 0) {
					MMediosPago mp = new MMediosPago(model.getCtx(), lines[i].getUY_MediosPago_ID(), model.get_TrxName());
					if (mp.get_ID() > 0){
			
						// Nuevo item en trazabilidad del medio de pago
						MCheckTracking trackLine = new MCheckTracking(model.getCtx(), 0, model.get_TrxName());
						trackLine.setUY_MediosPago_ID(lines[i].getUY_MediosPago_ID());
						trackLine.setC_BPartner_ID(model.getC_BPartner_ID());
						trackLine.setC_DocType_ID(model.getC_DocType_ID());
						trackLine.setAD_Table_ID(model.get_Table_ID());
						trackLine.setRecord_ID(model.get_ID());
						trackLine.setDocumentNo(model.getDocumentNo());
						trackLine.setDateTrx(model.getDateTrx());
						
						if (timing == TIMING_AFTER_COMPLETE)
							trackLine.setDocAction(DocumentEngine.ACTION_Complete);
						if (timing == TIMING_AFTER_VOID)
							trackLine.setDocAction(DocumentEngine.ACTION_Void);
						if (timing == TIMING_AFTER_REACTIVATE)
							trackLine.setDocAction(DocumentEngine.ACTION_ReActivate);
						
						trackLine.setCheckStatus(mp.getestado());
						trackLine.setCheckOldStatus(mp.getoldstatus());
						if (trackLine.getCheckOldStatus().equalsIgnoreCase("")) trackLine.setCheckOldStatus(trackLine.getCheckStatus());
						trackLine.saveEx();
					}
					else{
						message = "No se pudo obtener medio de pago con identificador : " + lines[i].getUY_MediosPago_ID();
					}
				}
			}		
			
		}

		if (timing == TIMING_BEFORE_POST){
			message = this.verifyConversionRate(model, model.getC_Currency_ID(), model.getDateTrx());		
		}
		
		return message;
	}

	
	/**
	 * OpenUp.	Validacion de DocAction en Allocation.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 05/04/2011
	 */
	private String docValidate(MAllocationHdr model, int timing){
		
		String validacion = null;
		
		if (timing == TIMING_BEFORE_COMPLETE || timing == TIMING_BEFORE_POST){
			validacion = this.verifyConversionRate(model, model.getC_Currency_ID(), model.getDateTrx());//OpenUp M.R. 18-08-2011 Issue#650 Agrego parametro de fecha para la comprobacion de la tasa de cambio		
		}
		return validacion;
	}
	
	/**
	 * OpenUp.	Validacion de DocAction en Entrega/Recepcion.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 05/04/2011
	 */
	private String docValidate(MInOut model, int timing){
		
		String message = null;
		
		// Manejo de Stock. Al completar, reactivar o anular.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE 
			|| timing == TIMING_AFTER_VOID) {

			Timestamp fechaMovimiento = model.getMovementDate(); 
			
			MInOutLine[] lines = model.getLines(true);
			for (int i=0; i<lines.length; i++){

				MDocType doc = new MDocType(model.getCtx(), model.getC_DocType_ID(), model.get_TrxName());
				if (doc.getC_DocType_ID() <= 0) return null;
				
				BigDecimal qty = lines[i].getMovementQty();

				// Entrada de Material ya sea por recepciones o devoluciones de clientes
				if (doc.getDocBaseType().equalsIgnoreCase("MMR")){

					// Me aseguro cantidad positiva
					if (qty.compareTo(Env.ZERO) < 0) qty = qty.multiply(new BigDecimal(-1));

					// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
					if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
						
						//OpenUp Nicolas Sarlabos issue #859
						if (!model.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)) return null;
						//fin OpenUp #859
						qty = qty.multiply(new BigDecimal(-1));
						//fechaMovimiento = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
					}

					// Subo fisico segun estado que viene en la linea (sino viene ninguno asumo aprobado por defecto)
					int idLineStockStatus = lines[i].getUY_StockStatus_ID();
					if (idLineStockStatus <= 0) idLineStockStatus = MStockStatus.getStatusApprovedID(null);
					message = MStockTransaction.add(model, null, lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
							lines[i].getM_Product_ID(),	lines[i].getM_AttributeSetInstance_ID(), idLineStockStatus, 
							fechaMovimiento, qty, lines[i].getM_InOutLine_ID(), null);
					if (message != null) return message;
					
					// Si es compra 
					if (!model.isSOTrx()){
						if (MSysConfig.getBooleanValue("UY_PORDER_TRANSIT_STOCK", true, model.getAD_Client_ID())){
							// Si tengo orden de compra asociada
							if (lines[i].getC_OrderLine_ID() > 0){
								// Muevo estado transito de la orden de compra asociada con signo contrario
								MOrderLine oline = new MOrderLine(model.getCtx(), lines[i].getC_OrderLine_ID(), null);
								MOrder ohdr = new MOrder(model.getCtx(), oline.getC_Order_ID(), null);
								int idStatusInTransit = MStockStatus.getStatusTransitID(null);
								message = MStockTransaction.add(model, ohdr, lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
										lines[i].getM_Product_ID(),	lines[i].getM_AttributeSetInstance_ID(), idStatusInTransit, 
										fechaMovimiento, qty.multiply(new BigDecimal(-1)), lines[i].getM_InOutLine_ID(), null);
								if (message != null) return message;
							}	
						}
					}
				}
				// Salida de Material ya sea por entregas o devoluciones a proveedores
				else if (doc.getDocBaseType().equalsIgnoreCase("MMS")){
					
					// Me aseguro cantidad negativa
					if (qty.compareTo(Env.ZERO) > 0) qty = qty.multiply(new BigDecimal(-1));

					// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
					if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
						qty = qty.multiply(new BigDecimal(-1));
						//fechaMovimiento = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
					}

					// Segun venta o compra
					if (model.isSOTrx()){

						// Muevo stock fisico aprobado
						int idStatusApproved = MStockStatus.getStatusApprovedID(null);
						message = MStockTransaction.add(model, null, lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
								lines[i].getM_Product_ID(),	lines[i].getM_AttributeSetInstance_ID(), idStatusApproved, 
								fechaMovimiento, qty, lines[i].getM_InOutLine_ID(), null);
						if (message != null) return message;
						
						// Muevo stock documentado entregado con signo contrario
						/*int idStatusDelivered = MStockStatus.getStatusDeliveredID(null);
						message = MStockTransaction.add(model, null, lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
								lines[i].getM_Product_ID(),	lines[i].getM_AttributeSetInstance_ID(), idStatusDelivered, 
								model.getMovementDate(), qty.multiply(new BigDecimal(-1)));
						if (message != null) return message;*/
						
						// Si hay reserva de stock previa, Muevo stock reservado con mismo signo
						if (model.getUY_ReservaPedidoHdr_ID() > 0){
							MReservaPedidoHdr resHdr = new MReservaPedidoHdr(model.getCtx(), model.getUY_ReservaPedidoHdr_ID(), null);
							int idStatusReserved = MStockStatus.getStatusReservedID(null);
							message = MStockTransaction.add(model, resHdr, lines[i].getM_Warehouse_ID(), 0, 
									lines[i].getM_Product_ID(),	lines[i].getM_AttributeSetInstance_ID(), idStatusReserved, 
									fechaMovimiento, qty, lines[i].getM_InOutLine_ID(), null);
							if (message != null) return message;
						}
						
					}
					else{
						// Muevo stock segun estado que viene en la linea
						int idLineStockStatus = lines[i].getUY_StockStatus_ID();
						if (idLineStockStatus <= 0) idLineStockStatus = MStockStatus.getStatusApprovedID(null);
						message = MStockTransaction.add(model, null, lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
								lines[i].getM_Product_ID(),	lines[i].getM_AttributeSetInstance_ID(), idLineStockStatus, 
								fechaMovimiento, qty, lines[i].getM_InOutLine_ID(), null);
						if (message != null) return message;
					}
				}
				
				// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock

				// Disponibilidad a fecha del movimiento
				message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
						lines[i].getM_Product_ID(), 0, fechaMovimiento, model.getAD_Client_ID(), model.get_TrxName());
		
				if (message != null) return message;
				
				// Disponibilidad a hoy
				message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
						lines[i].getM_Product_ID(), 0, 
						TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
						model.getAD_Client_ID(), model.get_TrxName());
		
				if (message != null) return message;
			}
		}
		//OpenUp #836 Nicolas Sarlabos 14/09/2011
		//se obliga a seleccionar un motivo de anulacion para las Devoluciones Coordinadas de Clientes
		MDocType docType = new MDocType(model.getCtx(), model.getC_DocType_ID(), null);
		if(docType.getDocBaseType().equalsIgnoreCase("MMR")){
			if(docType.getDocSubTypeSO() != null){	
				if(docType.getDocSubTypeSO().equalsIgnoreCase("RM") && docType.get_ID()==1000066){
			
					if (timing == TIMING_BEFORE_VOID){
						if ( (model.getDocStatusReason() == null) || (model.getDocStatusReason().equalsIgnoreCase("")))
							if (model.isSOTrx())
								message = "No se puede Anular Documento sin poner el MOTIVO.";
					}
				}
						
			}
			
		}	
		//fin OpenUp
		
		if (timing == TIMING_BEFORE_POST) message = this.verifyConversionRate(model, model.getC_Currency_ID(), model.getMovementDate());	
				
		return message;
	}

	
	/**
	 * OpenUp.	Validacion de DocAction en Inventario Fisico.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 17/05/2011
	 */
	private String docValidate(MInventory model, int timing){
		
		String message = null;
		
		// Manejo de Stock. Al completar, reactivar o anular.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE 
			|| timing == TIMING_AFTER_VOID) {

			MDocType doc = new MDocType(model.getCtx(), model.getC_DocType_ID(), model.get_TrxName());
			if (doc.getC_DocType_ID() <= 0) return null;
			if (doc.getDocBaseType().equalsIgnoreCase("MMI")){

				MInventoryLine[] lines = model.getLines(true);
				
				for (int i=0; i<lines.length; i++){
					
					BigDecimal qty = lines[i].getQtyCount().subtract(lines[i].getQtyBook());
					
					// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
					if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
						qty = qty.multiply(new BigDecimal(-1));
					}
					
					message = MStockTransaction.add(model, null, 0, lines[i].getM_Locator_ID(), lines[i].getM_Product_ID(), 
													lines[i].getM_AttributeSetInstance_ID(), lines[i].getUY_StockStatus_ID(), model.getMovementDate(), 
													qty, lines[i].getM_InventoryLine_ID(), null);
					
					if (message != null) return message;
					
					// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock
					
					// Disponibilidad a la fecha del movimiento
					message = MStockTransaction.checkAvailability(0, lines[i].getM_Locator_ID(), 
							lines[i].getM_Product_ID(), 0, model.getMovementDate(), model.getAD_Client_ID(), model.get_TrxName());
					
					if (message != null) return message;
					
					// Disponibilidad a hoy
					message = MStockTransaction.checkAvailability(0, lines[i].getM_Locator_ID(), 
							lines[i].getM_Product_ID(), 0, 
							TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
							model.getAD_Client_ID(), model.get_TrxName());
					
					if (message != null) return message;
					
				}
			}
		}
				
		return message;
	}


	/**
	 * OpenUp.	Validacion de DocAction en Movimientos de Materiales.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 17/05/2011
	 */
	private String docValidate(MMovement model, int timing){
		
		String message = null;
		
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE || timing == TIMING_AFTER_VOID) {

			MDocType doc = new MDocType(model.getCtx(), model.getC_DocType_ID(), model.get_TrxName());
			if (doc.getC_DocType_ID() <= 0) return null;
			if (doc.getDocBaseType().equalsIgnoreCase("MMM")){
				MMovementLine[] lines = model.getLines(true);
				for (int i=0; i<lines.length; i++){
					
					BigDecimal qty = lines[i].getMovementQty();

					// Me aseguro cantidad positiva
					if (qty.compareTo(Env.ZERO) < 0) qty = qty.multiply(new BigDecimal(-1));
					
					// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
					if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
						qty = qty.multiply(new BigDecimal(-1));
					}
					
					// 1. Muevo stock en ubicacion desde
					message = MStockTransaction.add(model, null, 0, lines[i].getM_Locator_ID(), lines[i].getM_Product_ID(), 
							lines[i].getM_AttributeSetInstance_ID(), lines[i].getUY_StockStatus_ID(), model.getMovementDate(), 
							qty.multiply(new BigDecimal(-1)), lines[i].getM_MovementLine_ID(), null);

					if (message != null) return message;

					// 2. Muevo stock en ubicacion hasta
					message = MStockTransaction.add(model, null, 0, lines[i].getM_LocatorTo_ID(), lines[i].getM_Product_ID(), 
							lines[i].getM_AttributeSetInstance_ID(), lines[i].getUY_StockStatus_To_ID(), model.getMovementDate(), 
							qty, lines[i].getM_MovementLine_ID(), null);
					
					if (message != null) return message;
					
					
					// OpenUp. Gabriel Vila. 24/07/2014. Issue #1405.
					// Si gestiono partidas de stock y consumos
					if (MSysConfig.getBooleanValue("UY_STOCK_HANDLE_CONSUME", false, model.getAD_Client_ID())){
						
						// En movimientos se consume y al mismo tiempo es generan partidas nuevas por cada consumo fifo
						// Por ello debo actualizar la identificacion de las partidas generadas ya que me quedan con el identificador del desde y no del hasta.
						StockIdentification stkIdent = new StockIdentification();
						stkIdent.mWarehouseID = new MLocator(model.getCtx(), lines[i].getM_LocatorTo_ID(), null).getM_Warehouse_ID();
						stkIdent.mLocatorID = lines[i].getM_LocatorTo_ID();
						stkIdent.uyStockStatusID = lines[i].getUY_StockStatus_To_ID();
						
						message = MStockConsume.updateConsumeIdentificacion(model.getCtx(), model.getAD_Client_ID(), I_M_Movement.Table_ID, lines[i].getM_MovementLine_ID(), stkIdent, model.get_TrxName());
						if (message != null) return message;
						
					}
					// Fin OpenUp. Issue #1405.
					
					// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock
					
					// Disponibilidad a la fecha del movimiento
					message = MStockTransaction.checkAvailability(0, lines[i].getM_Locator_ID(), 
							lines[i].getM_Product_ID(), 0, model.getMovementDate(), model.getAD_Client_ID(), model.get_TrxName());
					if (message != null) return message;
					
					// Disponibilidad a hoy
					message = MStockTransaction.checkAvailability(0, lines[i].getM_Locator_ID(), 
							lines[i].getM_Product_ID(), 0, 
							TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
							model.getAD_Client_ID(), model.get_TrxName());
					if (message != null) return message;

				}
			}
		}
				
		return message;
	}

	
	/**
	 * OpenUp.	Validacion de DocAction en Cash.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 05/04/2011
	 */
	private String docValidate(MCash model, int timing){
		
		String validacion = null;
		
		if (timing == TIMING_BEFORE_COMPLETE || timing == TIMING_BEFORE_POST){
			validacion = this.verifyConversionRate(model, model.getC_Currency_ID(),model.getStatementDate());//OpenUp M.R. 18-08-2011 Issue#650 Agrego parametro de fecha para la comprobacion de la tasa de cambio	
		}
		return validacion;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Validacion de DocAction en Confirmacion de Ordenes de Proceso.
	 * Modificado: #824 Nicolas Garcia 18/08/2011 Ahora el almacen de las lineas se toma desde las mismas
	 * Actualizado issue #934-28/11/2011
	 * @param confPPHdr
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 21/03/2011
	 */
	private String docValidate(MConfirmorderhdr confPPHdr, int timing){

		String message = null;
		MPPOrder ppOrder = null;

		// Stock.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE || timing == TIMING_AFTER_VOID) {

			// Muevo stock para el producto confirmado en el cabezal
			BigDecimal qtyHdr = confPPHdr.getQtyDelivered();
			if (qtyHdr.compareTo(Env.ZERO) < 0) qtyHdr = qtyHdr.multiply(new BigDecimal(-1));
			
			if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID))
				qtyHdr = qtyHdr.multiply(new BigDecimal(-1));

			int idStockStatus = confPPHdr.getUY_StockStatus_ID();
			if (idStockStatus <= 0) idStockStatus = MStockStatus.getStatusApprovedID(null);
			
			if(!MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, confPPHdr.getAD_Client_ID())){ //si no es orden de transformacion
				
				// Obtengo datos de orden de proceso asociada a esta confirmacion
				ppOrder = new MPPOrder(confPPHdr.getCtx(), confPPHdr.getPP_Order_ID(), confPPHdr.get_TrxName());
								
				message = MStockTransaction.add(confPPHdr, ppOrder, confPPHdr.getUY_Almacendestino_ID(), 0, confPPHdr.getM_Product_ID(),
						  0, idStockStatus, confPPHdr.getDateTrx(), qtyHdr, 0, null);
				
				if (message != null) return message;

				boolean llevarResrvasACero = false;

				//OpenUp Nicolas Sarlabos 11/07/2012 #1043
				if (timing != TIMING_AFTER_VOID) {
					// Si se entrego lo mismo o mas de lo ordenado
					if (confPPHdr.getQtyOrdered().compareTo(confPPHdr.getQtyDelivered()) <= 0) {
						llevarResrvasACero = true;
					}
					// Si se marco cerrar la orden desde la confirmacion
					if (confPPHdr.isUY_CerroConfirmacion()) {
						llevarResrvasACero = true;
					}
				}
				//Fin OpenUp Nicolas Sarlabos 11/07/2012 #1043
				// Muevo stock para lineas  
				MConfirmOrderline[] lines = confPPHdr.getLines();
				for (int i=0; i<lines.length; i++){
					
					// Cantidad consumida Aprobado
					BigDecimal qty = lines[i].getUY_ConsumoTotal();

					if (qty.compareTo(Env.ZERO) < 0) qty = qty.multiply(new BigDecimal(-1));
					
					if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
						qty = qty.multiply(new BigDecimal(-1));
					}

					if (!llevarResrvasACero) {
						// Muevo reservado
						// Si la linea no es manual
						if (!lines[i].ismanual()) {
							//OpenUp Nicolas Sarlabos 11/07/2012 #1043
							BigDecimal qtyBOM = lines[i].getQtyBOM();
							if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
								qtyBOM = qtyBOM.multiply(new BigDecimal(-1));
							}
													
							int idStatusReserved = MStockStatus.getStatusReservedID(null);
							message = MStockTransaction.add(confPPHdr, ppOrder, lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 0, idStatusReserved,
									confPPHdr.getDateTrx(), qtyBOM.multiply(confPPHdr.getQtyDelivered()).multiply(new BigDecimal(-1)),
									lines[i].getUY_ConfirmOrderline_ID(), null);
							if (message != null) return message;
							//Fin OpenUp Nicolas Sarlabos 11/07/2012 #1043
						}
					}
					// Muevo fisico
					int idStockStatusLine = lines[i].getUY_StockStatus_ID();
					if (idStockStatusLine <= 0) idStockStatusLine = MStockStatus.getStatusApprovedID(null);
					
					message = MStockTransaction.add(confPPHdr, ppOrder, lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 0, idStockStatusLine,
							confPPHdr.getDateTrx(), qty.multiply(new BigDecimal(-1)), lines[i].getUY_ConfirmOrderline_ID(), null);
					if (message != null) return message;
					
					if (!llevarResrvasACero) {
						
						// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock			
						
						// Disponibilidad en fecha de transaccion
						message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 0, confPPHdr.getDateTrx(),
								confPPHdr.getAD_Client_ID(), confPPHdr.get_TrxName());
						if (message != null) return message;
						
						// Disponibilidad a hoy
						message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 0, 
								TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY),
								confPPHdr.getAD_Client_ID(), confPPHdr.get_TrxName());
						if (message != null) return message;
						
					}
				}

				if (llevarResrvasACero) {
					message = ppOrder.closeTechnical(confPPHdr);
					if (message != null) return message;
				}			
				
			} else { //si es orden de transformacion
				//TODO: refactor using isMultipleOutput()
				MProdTransf prodTransfOrder = new MProdTransf(confPPHdr.getCtx(), confPPHdr.getProdTransfOrder().get_ID(), confPPHdr.get_TrxName());
				
				MDocType doc = new MDocType(confPPHdr.getCtx(), prodTransfOrder.getC_DocTypeTarget_ID(), confPPHdr.get_TrxName());
				
				if(doc!=null && doc.get_ID()>0){
					
					if(doc.getValue()!=null){
						
						if(doc.getValue().equalsIgnoreCase("ordencorte")){
							
							boolean llevarResrvasACero = false;
							
							//OpenUp Nicolas Sarlabos 11/07/2012 #1043
							if (timing != TIMING_AFTER_VOID) {
								// Si se entrego lo mismo o mas de lo ordenado
								if (this.outputReached(prodTransfOrder,confPPHdr)) {
									llevarResrvasACero = true;
								}
								// Si se marco cerrar la orden desde la confirmacion
								if (confPPHdr.isUY_CerroConfirmacion()) {
									llevarResrvasACero = true;
								}
							}
							
							// Muevo stock para lineas  
							MConfirmOrderline[] outLines = confPPHdr.getLines();
							
							idStockStatus = MStockStatus.getStatusApprovedID(null);
							
							for(MConfirmOrderline ol : outLines){
								
								// Cantidad consumida Aprobado
								BigDecimal qty = (BigDecimal) ol.get_Value("Weight");
								MProduct prodOut = (MProduct)ol.getM_Product();
								BigDecimal rollos = new BigDecimal(ol.get_ValueAsInt("UY_NumRollos"));

								if (qty.compareTo(Env.ZERO) < 0) qty = qty.multiply(new BigDecimal(-1));
								
								if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
									qty = qty.multiply(new BigDecimal(-1));
								}
								
								idStockStatus = ol.getUY_StockStatus_ID();
								if (idStockStatus <= 0) idStockStatus = MStockStatus.getStatusApprovedID(null);
							
								message = MStockTransaction.add(confPPHdr, prodTransfOrder, ol.get_ValueAsInt("M_Warehouse_ID_1"), 0, prodOut.get_ID(), 0, idStockStatus,
										confPPHdr.getDateTrx(), qty.multiply(rollos), ol.getUY_ConfirmOrderline_ID(), null);							
								
								if (message != null) return message;						
	
								if (!llevarResrvasACero) {
									
									// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock			
									
									// Disponibilidad en fecha de transaccion
									message = MStockTransaction.checkAvailability(ol.get_ValueAsInt("M_Warehouse_ID_1"), 0, ol.getM_Product_ID(), 0, confPPHdr.getDateTrx(),
											confPPHdr.getAD_Client_ID(), confPPHdr.get_TrxName());
									if (message != null) return message;
									
									// Disponibilidad a hoy
									message = MStockTransaction.checkAvailability(ol.get_ValueAsInt("M_Warehouse_ID_1"), 0, ol.getM_Product_ID(), 0, 
											TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY),
											confPPHdr.getAD_Client_ID(), confPPHdr.get_TrxName());
									if (message != null) return message;
									
								}						
							}
							
							if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
								
								//subo reserva del producto de entrada
								int idStatusReserved = MStockStatus.getStatusReservedID(null);
								message = MStockTransaction.add(confPPHdr, prodTransfOrder, prodTransfOrder.getM_Warehouse_ID(), -1, 
										prodTransfOrder.getM_Product_ID(), 0, idStatusReserved, prodTransfOrder.getDateTrx(), prodTransfOrder.getQtyOrdered(), -1, null);
								if (message!=null) return message;	
								
								// Chequeo disponibilidad de este producto
								message = MStockTransaction.checkAvailability(prodTransfOrder.getM_Warehouse_ID(),-1, 
									  	prodTransfOrder.getM_Product_ID(), 0, prodTransfOrder.getDateTrx(), prodTransfOrder.getAD_Client_ID(), confPPHdr.get_TrxName());
								if (message != null) return message;						
								
							}
							
							//movimiento de producto de entrada
							BigDecimal qtyInput = MConfirmOrderBobbin.totalWeightInput(confPPHdr.getCtx(), confPPHdr.get_ID(), null);
							BigDecimal qtyLost = MConfirmOrderBobbin.totalWeightLost(confPPHdr.getCtx(), confPPHdr.get_ID(), null);
												
							if(qtyInput.compareTo(Env.ZERO)>0){
								
								MWarehouse warehouseFrom =(MWarehouse)confPPHdr.getM_Warehouse();
								
								if(warehouseFrom!=null && warehouseFrom.get_ID()>0){
									
									MLocator locatorFrom = MLocator.getDefault(warehouseFrom);
									
									if(locatorFrom!=null && locatorFrom.get_ID()>0){	
										
										if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
											qtyInput = qtyInput.multiply(new BigDecimal(-1));
										}
										
										message = MStockTransaction.add(confPPHdr, prodTransfOrder, warehouseFrom.get_ID(), locatorFrom.get_ID(), 
												confPPHdr.getM_Product_ID(), 0, idStockStatus, confPPHdr.getDateTrx(),  qtyInput.multiply(new BigDecimal(-1)), 0, null);
										if (message != null) return message;								
										
									}								
								}								
							}								

							//movimiento de Desperdicio
							if(qtyLost.compareTo(Env.ZERO)>0){
								
								MWarehouse warehouseDesperdicio = MWarehouse.forValue(Env.getCtx(), "Desperdicio", null);
								
								if(warehouseDesperdicio!=null && warehouseDesperdicio.get_ID()>0){
									
									MLocator locatorDesperdicio = MLocator.getDefault(warehouseDesperdicio);
									
									if(locatorDesperdicio!=null && locatorDesperdicio.get_ID()>0){	
										
										if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
											qtyLost = qtyLost.multiply(new BigDecimal(-1));
										}
										
										idStockStatus = MStockStatus.getStatusQuarantineID(null);
										message = MStockTransaction.add(confPPHdr, prodTransfOrder, warehouseDesperdicio.get_ID(), locatorDesperdicio.get_ID(), 
												confPPHdr.getM_Product_ID(), 0, idStockStatus, confPPHdr.getDateTrx(),  qtyLost, 0, null);
										if (message != null) return message;								
										
									}								
								}								
							}					
							
							if (llevarResrvasACero) {
								message = prodTransfOrder.closeTechnical(confPPHdr);
								if (message != null) return message;
							}					
							
						} else { //si la orden NO es corte
							
							MProduct prodHdr = (MProduct)confPPHdr.getM_Product();
							
							/*if(!doc.getValue().equalsIgnoreCase("ordenreb")){
								
								//si la UM ingresada no es igual a la UM de stock del producto
								if(confPPHdr.getC_UOM_ID() != prodHdr.getC_UOM_ID()){
									
									BigDecimal factor = MUOMConversion.getProductRateFrom(confPPHdr.getCtx(), prodHdr.get_ID(), confPPHdr.getC_UOM_ID());
									
									if(factor==null) throw new AdempiereException("No se obtuvo tasa de conversion");
									
									qtyHdr = confPPHdr.getQtyDelivered().multiply(factor);
									
								}
								
								if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
									qtyHdr = qtyHdr.multiply(new BigDecimal(-1));
								}
								
								message = MStockTransaction.add(confPPHdr, prodTransfOrder, confPPHdr.getUY_Almacendestino_ID(), 0, confPPHdr.getM_Product_ID(),
										  0, idStockStatus, confPPHdr.getDateTrx(), qtyHdr, 0, null);
								
								if (message != null) return message;						
								
							}*/			

							boolean llevarResrvasACero = false;
			
							//OpenUp Nicolas Sarlabos 11/07/2012 #1043
							if (timing != TIMING_AFTER_VOID) {
								
								if(!doc.getValue().equalsIgnoreCase("ordenreb")){
									
									// Si se entrego lo mismo o mas de lo ordenado
									if (confPPHdr.getQtyOrdered().compareTo(confPPHdr.getQtyDelivered()) <= 0) {
										llevarResrvasACero = true;
									}						
									
								}
						
								// Si se marco cerrar la orden desde la confirmacion
								if (confPPHdr.isUY_CerroConfirmacion()) {
									llevarResrvasACero = true;
								}
							}
							//Fin OpenUp Nicolas Sarlabos 11/07/2012 #1043
							// Muevo stock para lineas  
							MConfirmOrderline[] lines = confPPHdr.getLines();
							for (int i=0; i<lines.length; i++){
								
								BigDecimal factor = null;
								
								// Cantidad consumida Aprobado
								BigDecimal qty = lines[i].getUY_ConsumoTotal();
								MProduct prod = (MProduct)lines[i].getM_Product();
								
								//si la UM ingresada no es igual a la UM de stock del producto
								if(lines[i].getC_UOM_ID() != prod.getC_UOM_ID()){
									
									factor = MUOMConversion.getProductRateFrom(confPPHdr.getCtx(), prod.get_ID(), lines[i].getC_UOM_ID());
									
									if(factor==null) throw new AdempiereException("No hay factor de conversion entre la UM indicada y la UM de stock del producto");
									
									qty = lines[i].getUY_ConsumoTotal().multiply(factor);
									
								}			
								
								// Cantidad requerida
								BigDecimal qtyReq = lines[i].getQtyRequired();
																
								//si la UM ingresada no es igual a la UM de stock del producto
								if(lines[i].getC_UOM_ID() != prod.getC_UOM_ID()) qtyReq = lines[i].getQtyRequired().multiply(factor);								

								if (qty.compareTo(Env.ZERO) < 0) qty = qty.multiply(new BigDecimal(-1));
								
							

								//if (llevarResrvasACero) {
									// Muevo reservado
									// Si la linea no es manual
									if (!lines[i].ismanual()) {
										//OpenUp Nicolas Sarlabos 11/07/2012 #1043
										//BigDecimal qtyBOM = lines[i].getQtyBOM();
										if ((timing != TIMING_AFTER_REACTIVATE) && (timing != TIMING_AFTER_VOID)) {
											qtyReq = qtyReq.multiply(new BigDecimal(-1));
										}
																
										int idStatusReserved = MStockStatus.getStatusReservedID(null);
										message = MStockTransaction.add(confPPHdr, prodTransfOrder, lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 0, idStatusReserved,
												confPPHdr.getDateTrx(), qtyReq, lines[i].getUY_ConfirmOrderline_ID(), null);
										if (message != null) return message;
										//Fin OpenUp Nicolas Sarlabos 11/07/2012 #1043
									}
								//}
									
									
									
									
								// Muevo fisico
								int idStockStatusLine = lines[i].getUY_StockStatus_ID();
								if (idStockStatusLine <= 0) idStockStatusLine = MStockStatus.getStatusApprovedID(null);
								
								if ((timing != TIMING_AFTER_REACTIVATE) && (timing != TIMING_AFTER_VOID)) {
									qty = qty.multiply(new BigDecimal(-1));
								}
								
								message = MStockTransaction.add(confPPHdr, prodTransfOrder, lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 0, idStockStatusLine,
										confPPHdr.getDateTrx(), qty, lines[i].getUY_ConfirmOrderline_ID(), null);
								if (message != null) return message;
								
								if (!llevarResrvasACero) {
									
									// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock			
									
									// Disponibilidad en fecha de transaccion
									message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 0, confPPHdr.getDateTrx(),
											confPPHdr.getAD_Client_ID(), confPPHdr.get_TrxName());
									if (message != null) return message;
									
									// Disponibilidad a hoy
									message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), 0, lines[i].getM_Product_ID(), 0, 
											TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY),
											confPPHdr.getAD_Client_ID(), confPPHdr.get_TrxName());
									if (message != null) return message;
									
								}
							}
							
							if (!doc.getValue().equalsIgnoreCase("ordencorte") && !doc.getValue().equalsIgnoreCase("ordenreb")) {
								
								MConfirmOrderBobbin[] bLines = confPPHdr.getLinesBobbinUsed("");
								for (MConfirmOrderBobbin bLine : bLines){
									
									BigDecimal qty = bLine.getWeight().subtract(bLine.getUY_Devolucion());
									
									//doy de baja los kg para el almacen de origen de las bobinas usadas
									qty = qty.multiply(new BigDecimal(-1));
									
									if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
										qty = qty.multiply(new BigDecimal(-1));
									}
									
									message = MStockTransaction.add(confPPHdr, prodTransfOrder, bLine.getM_Warehouse_ID(), 0, bLine.getM_Product_ID(), 0, idStockStatus,
											confPPHdr.getDateTrx(), qty, bLine.get_ID(), null);
									if (message != null) return message;	
									
									//movimiento de desperdicio
									BigDecimal desperdicio = (BigDecimal)bLine.get_Value("UY_Desperdicio");
									
									if(desperdicio.compareTo(Env.ZERO)>0){
																			
										if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
											desperdicio = desperdicio.multiply(new BigDecimal(-1));
										}									
										
										MWarehouse warehouseDesp = MWarehouse.forValue(confPPHdr.getCtx(), "Desperdicio", null);
										MLocator locatorDesp = warehouseDesp.getDefaultLocator();
										int stockStatusID = MStockStatus.getStatusQuarantineID(null);
										message = MStockTransaction.add(confPPHdr, prodTransfOrder, warehouseDesp.get_ID(), locatorDesp.get_ID(), bLine.getM_Product_ID(), 0, stockStatusID,
												confPPHdr.getDateTrx(), desperdicio, 0, null);
										
										if (message != null) return message;									
									}							
									
									//movimiento de refile
									BigDecimal refile = (BigDecimal)bLine.get_Value("UY_RefileYDescarne");	
									
									if(refile.compareTo(Env.ZERO)>0){
																			
										if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
											refile = refile.multiply(new BigDecimal(-1));
										}									
										
										MWarehouse warehouseRef = MWarehouse.forValue(confPPHdr.getCtx(), "Refile", null);
										MLocator locatorRef = warehouseRef.getDefaultLocator();
										int stockStatusID = MStockStatus.getStatusQuarantineID(null);
										message = MStockTransaction.add(confPPHdr, prodTransfOrder, warehouseRef.get_ID(), locatorRef.get_ID(), bLine.getM_Product_ID(), 0, stockStatusID,
												confPPHdr.getDateTrx(), refile, 0, null);
										
										if (message != null) return message;									
									}		
									
									
									//subo reservado si es una anulacion
									if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
										
										BigDecimal qtyReq = bLine.getQtyOrdered();
										
										int idStatusReserved = MStockStatus.getStatusReservedID(null);
										message = MStockTransaction.add(confPPHdr, prodTransfOrder, bLine.getM_Warehouse_ID(), 0, bLine.getM_Product_ID(), 0, idStatusReserved,
												confPPHdr.getDateTrx(), qtyReq, bLine.getUY_ConfirmOrderBobbin_ID(), null);
										if (message != null) return message;									
										
									}									
									
								}	
								
								MConfOrderBobbinProd[] prodLines = confPPHdr.getLinesBobbinProd("");
								
								for(MConfOrderBobbinProd line : prodLines){
																		
									//doy de alta los kg para el almacen destino de las bobionas producidas
									BigDecimal qtyMov = line.getWeight().multiply(line.getQtyEntered());
									
									if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
										qtyMov = qtyMov.multiply(new BigDecimal(-1));
									}									
									
									idStockStatus = MStockStatus.getStatusApprovedID(null);
									message = MStockTransaction.add(confPPHdr, prodTransfOrder, confPPHdr.get_ValueAsInt("UY_Almacendestino_ID"), 0, confPPHdr.getM_Product_ID(), 0, idStockStatus,
											confPPHdr.getDateTrx(), qtyMov, line.get_ID(), null);
									if (message != null) return message;							
									
								}
								
							}
							
							if(doc.getValue().equalsIgnoreCase("ordenreb")){
								
								//movimientos de cambio de almacen
								idStockStatus = MStockStatus.getStatusApprovedID(null);
								BigDecimal qtyMov = Env.ZERO;
								
								MConfirmOrderBobbinReb[] rebLines = confPPHdr.getLinesBobbinReb("");
								
								for(MConfirmOrderBobbinReb line : rebLines){
																	
									//doy de baja los kg para el almacen de origen de las bobinas usadas
									qtyMov = line.getWeight().multiply(new BigDecimal(-1));
									
									if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
										qtyMov = qtyMov.multiply(new BigDecimal(-1));
									}
									
									message = MStockTransaction.add(confPPHdr, prodTransfOrder, line.getM_Warehouse_ID(), 0, confPPHdr.getM_Product_ID(), 0, idStockStatus,
											confPPHdr.getDateTrx(), qtyMov, line.get_ID(), null);
									if (message != null) return message;							
									
								}
								
								MConfOrderBobbinProd[] prodLines = confPPHdr.getLinesBobbinProd("");
								
								for(MConfOrderBobbinProd line : prodLines){
																		
									//doy de alta los kg para el almacen destino de las bobionas producidas
									qtyMov = line.getWeight().multiply(line.getQtyEntered());
									
									if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
										qtyMov = qtyMov.multiply(new BigDecimal(-1));
									}									
									
									message = MStockTransaction.add(confPPHdr, prodTransfOrder, confPPHdr.get_ValueAsInt("UY_Almacendestino_ID"), 0, confPPHdr.getM_Product_ID(), 0, idStockStatus,
											confPPHdr.getDateTrx(), qtyMov, line.get_ID(), null);
									if (message != null) return message;							
									
								}								
								
								//movimiento de Desperdicio
								BigDecimal desperdicio = (BigDecimal)confPPHdr.get_Value("UY_Desperdicio");
								
								if(desperdicio.compareTo(Env.ZERO)>0){
									
									qtyMov = (BigDecimal)confPPHdr.get_Value("UY_Desperdicio");
									
									if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
										qtyMov = qtyMov.multiply(new BigDecimal(-1));
									}									
									
									MWarehouse warehouseDesp = MWarehouse.forValue(confPPHdr.getCtx(), "Desperdicio", null);
									MLocator locatorDesp = warehouseDesp.getDefaultLocator();
									int stockStatusID = MStockStatus.getStatusQuarantineID(null);
									message = MStockTransaction.add(confPPHdr, prodTransfOrder, warehouseDesp.get_ID(), locatorDesp.get_ID(), prodHdr.get_ID(), 0, stockStatusID,
											confPPHdr.getDateTrx(), qtyMov, 0, null);
									
									if (message != null) return message;									
								}
															
							}							

							if (llevarResrvasACero) {
								message = prodTransfOrder.closeTechnical(confPPHdr);
								if (message != null) return message;
							}										
							
						}					
						
					}					
					
				} else throw new AdempiereException("Error al obtener tipo de documento");			
				
			}						

		}
		
		return message;
	}

	/**
	 * OpenUp.	Devuelve true si la cantidad entregada es mayor o igual a la ordenada para ordenes de corte.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Ines Fernandez - Nicolas Sarlabos. Issue #4125
	 * Fecha : 06/08/2015
	 */

	private boolean outputReached(MProdTransf prodTransfOrder, MConfirmorderhdr confPPHdr) {
		
		String sql = "";
		
		sql = "select coalesce(sum(qtydelivered),0)" +
		      " from uy_confirmorderline" +
			  " where uy_confirmorderhdr_id = " + confPPHdr.get_ID();
		
		BigDecimal qtyDelivered = DB.getSQLValueBDEx(confPPHdr.get_TrxName(), sql);
		
		if(qtyDelivered.compareTo(prodTransfOrder.getQtyOrdered())>=0) return true;
				
		return false;
	}

	/**
	 * OpenUp.	Validacion de DocAction en MRecuentoConf.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Nicolas Garcia. Issue #889
	 * Fecha : 07/10/2011
	 */
	private String docValidate(MRecuentoConf model, int timing) {

		String message = null;

		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE || timing == TIMING_AFTER_VOID) {

			MRecuentoConfLine[] lines = model.getLinesParaAjustar(model.get_TrxName());

			int stockAprobado = MStockStatus.getStatusApprovedID(null);
			int stockCuarentena = MStockStatus.getStatusQuarantineID(null);
			int stockRechazado = MStockStatus.getStatusBblockedID(null);

			Timestamp fechaMovimiento = model.getfecdoc();

			int cant = lines.length;

			for (int i = 0; i < lines.length; i++) {

				model.getWindow().setWaitingMessage("Procesando " + i + " de " + cant);

				BigDecimal aprobado = lines[i].getqty_approved();
				BigDecimal cuarentena = lines[i].getqty_quarantine();
				BigDecimal rechazado = lines[i].getqty_blocked();



				if (message != null) return message;


				// Para anulaciones o reactivaciones debo enviar la cantidad
				// en signo contrario para darla vuelta
				if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)) {
					aprobado = aprobado.multiply(new BigDecimal(-1));
					cuarentena = cuarentena.multiply(new BigDecimal(-1));
					rechazado = rechazado.multiply(new BigDecimal(-1));
				} else {

					// Si cambio la cantidad en libros desde que se genero la
					// definicion de recuento hasta el momento de realizar el
					// movimiento de stock
					message = lines[i].ValidarCantidades();

				}

				// 1. Aprobado
				if (aprobado.compareTo(Env.ZERO) != 0) {

					message = MStockTransaction.add(model, model, lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), lines[i].getM_Product_ID(), 0,
							stockAprobado, fechaMovimiento, aprobado, lines[i].getUY_RecuentoConfLine_ID(), null);

					if (message != null) return message;
				}

				// 2. Muevo stock Cuarentena
				if (cuarentena.compareTo(Env.ZERO) != 0) {

					message = MStockTransaction.add(model, model, lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), lines[i].getM_Product_ID(), 0,
							stockCuarentena, fechaMovimiento, cuarentena, lines[i].getUY_RecuentoConfLine_ID(), null);

					if (message != null) return message;
				}

				// 3. Muevo stock Bloqueado(Rechazado)
				if (rechazado.compareTo(Env.ZERO) != 0) {

					message = MStockTransaction.add(model, model, lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), lines[i].getM_Product_ID(), 0,
							stockRechazado, fechaMovimiento, rechazado, lines[i].getUY_RecuentoConfLine_ID(), null);

					if (message != null) return message;
				}

				if (message != null) return message;
				
				// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock
				
				// Disponibilidad a fecha del movimiento
				message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
						lines[i].getM_Product_ID(), 0, fechaMovimiento, 
						model.getAD_Client_ID(), model.get_TrxName());
				if (message != null) return message;
			
				// Disponibilidad a hoy
				message = MStockTransaction.checkAvailability(lines[i].getM_Warehouse_ID(), lines[i].getM_Locator_ID(), 
						lines[i].getM_Product_ID(), 0, 
						TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
						model.getAD_Client_ID(), model.get_TrxName());
				if (message != null) return message;

			}
		}

		return message;
	}

	/**
	 * OpenUp.	Validacion de DocAction en StockAdjustment.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila. Issue #829 
	 * Fecha : 04/08/2011
	 */
	private String docValidate(MStockAdjustment model, int timing){

		String message = null;
		
		// Manejo de Stock. Al completar, reactivar o anular.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_VOID) {

			MStockAdjustmentLine[] lines = model.getLines(model.get_TrxName());

			for (int i=0; i<lines.length; i++){
				
				BigDecimal qty = lines[i].getMovementQty();

				// Para anulaciones debo enviar la cantidad en signo contrario para darla vuelta
				if (timing == TIMING_AFTER_VOID){
					qty = qty.multiply(new BigDecimal(-1));
				}
		
				// Muevo estado de stock  
				message = MStockTransaction.add(model, null, 0, lines[i].getM_Locator_ID(), lines[i].getM_Product_ID(), 
											    lines[i].getM_AttributeSetInstance_ID(), model.getUY_StockStatus_ID(), model.getMovementDate(), 
											    qty, lines[i].getUY_StockAdjustmentLine_ID(), null);
				
				if (message != null) return message;
				
				// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock
				
				// Disponibilidad a fecha del movimiento
				message = MStockTransaction.checkAvailability(0, lines[i].getM_Locator_ID(), 
						lines[i].getM_Product_ID(), lines[i].getM_AttributeSetInstance_ID(), model.getMovementDate(), 
						model.getAD_Client_ID(), model.get_TrxName());
				if (message != null) return message;
				
				// Disponibilidad a hoy
				message = MStockTransaction.checkAvailability(0, lines[i].getM_Locator_ID(), 
						lines[i].getM_Product_ID(), lines[i].getM_AttributeSetInstance_ID(), 
						TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
						model.getAD_Client_ID(), model.get_TrxName());
				if (message != null) return message;

			}
		}
		
		return message;
	}

	
	/**
	 * OpenUp.	Validacion de DocAction en Ordenes de Fabricacion con Presupuesto.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 09/06/2013
	 */
	private String docValidate(MManufOrder model, int timing){
		
		String message = null;
		
		// Manejo de Stock. Al completar, reactivar o anular.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE 
			|| timing == TIMING_AFTER_VOID) {

			Timestamp fechaMovimiento = model.getDateTrx(); 
			
			List<MManufLine> lines = model.getLines();

			for (MManufLine line: lines){

				MDocType doc = new MDocType(model.getCtx(), model.getC_DocType_ID(), model.get_TrxName());
				if (doc.getC_DocType_ID() <= 0) return null;
				
				BigDecimal qty = line.getQty();
					
				// Me aseguro cantidad negativa
				if (qty.compareTo(Env.ZERO) > 0) qty = qty.multiply(new BigDecimal(-1));

				// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
				if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
					qty = qty.multiply(new BigDecimal(-1));
				}

				// Muevo stock fisico aprobado
				int idStatusApproved = MStockStatus.getStatusApprovedID(null);
				message = MStockTransaction.add(model, null, model.getM_Warehouse_ID(), model.getM_Locator_ID(), 
						line.getM_Product_ID(),	0, idStatusApproved, fechaMovimiento, qty, line.getUY_ManufLine_ID(), null);

				if (message != null) return message;
				
				
				// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock

				// Disponibilidad a fecha del movimiento
				message = MStockTransaction.checkAvailability(model.getM_Warehouse_ID(), model.getM_Locator_ID(), 
						  	line.getM_Product_ID(), 0, fechaMovimiento, model.getAD_Client_ID(), model.get_TrxName());
		
				if (message != null) return message;
				
				// Disponibilidad a hoy
				message = MStockTransaction.checkAvailability(model.getM_Warehouse_ID(), model.getM_Locator_ID(), 
							line.getM_Product_ID(), 0, 
							TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
							model.getAD_Client_ID(), model.get_TrxName());
		
				if (message != null) return message;
			}
		}
				
		return message;
	}

	
	
	/**
	 * OpenUp.	Validacion de DocAction en Consumo de Stock de productos.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 23/07/2014
	 */
	private String docValidate(MProductConsume model, int timing){
		
		String message = null;
		
		// Manejo de Stock. Al completar, reactivar o anular.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE 
			|| timing == TIMING_AFTER_VOID) {

			Timestamp fechaMovimiento = model.getMovementDate(); 
			
			List<MProductConsumeLine> lines = model.getLines();

			for (MProductConsumeLine line: lines){

				MDocType doc = new MDocType(model.getCtx(), model.getC_DocType_ID(), null);
				if (doc.getC_DocType_ID() <= 0) return null;
				
				BigDecimal qty = line.getMovementQty();
					
				// Me aseguro cantidad negativa
				if (qty.compareTo(Env.ZERO) > 0) qty = qty.negate();

				// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
				if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
					qty = qty.negate();
				}

				// Muevo stock 
				message = MStockTransaction.add(model, null, model.getM_Warehouse_ID(), line.getM_Locator_ID(), 
						line.getM_Product_ID(),	0, model.getUY_StockStatus_ID(), fechaMovimiento, qty, line.get_ID(), null);

				if (message != null) return message;
				
				// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock

				// Disponibilidad a fecha del movimiento
				message = MStockTransaction.checkAvailability(model.getM_Warehouse_ID(), line.getM_Locator_ID(), 
						  	line.getM_Product_ID(), 0, fechaMovimiento, model.getAD_Client_ID(), model.get_TrxName());
		
				if (message != null) return message;
				
				// Disponibilidad a hoy
				message = MStockTransaction.checkAvailability(model.getM_Warehouse_ID(), line.getM_Locator_ID(), 
							line.getM_Product_ID(), 0, 
							TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
							model.getAD_Client_ID(), model.get_TrxName());
		
				if (message != null) return message;
			}
		}
				
		return message;
	}
	
	
	/**
	 * OpenUp.	Validacion de DocAction en Ordenes de Proceso	.
	 * Descripcion :
	 * @param model
	 * @param timing
	 * @return
	 * @author  INes Fernandez	 
	 * Fecha : 27/04/2015
	 */
	private String docValidate(MProdTransf model, int timing){
		
		String message = null;
		
		// Manejo de Stock. Al completar, las cantidades de input quedan reservadas.
		if (timing == TIMING_AFTER_COMPLETE || timing == TIMING_AFTER_REACTIVATE 
			|| timing == TIMING_AFTER_VOID) {

			Timestamp fechaMovimiento = model.getDateTrx(); 
						
				MDocType doc = new MDocType(model.getCtx(), model.getC_DocType_ID(), model.get_TrxName());
				if (doc.getC_DocType_ID() <= 0) return null;				
				
				if(doc.getValue()!=null){
					
					if(doc.getValue().equalsIgnoreCase("ordencorte")){
						
						BigDecimal qty = model.getQtyOrdered();
						int productId = model.getM_Product_ID();

						// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
						if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
							qty = qty.multiply(new BigDecimal(-1));
							
						}
						
						// Chequeo disponibilidad de este producto
						message = MStockTransaction.checkAvailability(model.getM_Warehouse_ID(),-1 /*model.getM_Locator_ID()*/, 
							  	productId, 0, fechaMovimiento, model.getAD_Client_ID(), model.get_TrxName());
						if (message != null) return message;
						
//						//Bajo stock aprobado
//						int idStatusApproved = MStockStatus.getStatusApprovedID(null);
//						message = MStockTransaction.add(model, null, model.getM_Warehouse_ID(), -1/*model.getM_Locator_ID()*/, 
//								productId,	0, idStatusApproved, fechaMovimiento, new BigDecimal(-1).multiply(qty), -1/*line.getUY_ManufLine_ID()*/, null);
//
//						if (message != null) return message;
						//Sube stock reservado
						int idStatusReserved = MStockStatus.getStatusReservedID(null);
						message = MStockTransaction.add(model, null, model.getM_Warehouse_ID(), -1, 
								productId, 0, idStatusReserved, fechaMovimiento, qty, -1, null);
						if (message!=null) return message;		
						
						
					} else {
							
							MProdTransfInput[] inputLines = model.getInputLines(""); //obtengo lineas de insumos
							
							//recorro lineas de insumos
							for(MProdTransfInput line:inputLines){
								
								BigDecimal qty = line.getQtyEntered();
								int productId = line.getM_Product_ID();
								
								MProduct prod = (MProduct)line.getM_Product();
								
								//si la UM ingresada no es igual a la UM de stock del producto
								if(line.getC_UOM_ID() != prod.getC_UOM_ID()){
									
									BigDecimal factor = MUOMConversion.getProductRateFrom(model.getCtx(), productId, line.getC_UOM_ID());
									
									if(factor==null) throw new AdempiereException("No hay factor de conversion entre la UM indicada y la UM de stock del producto");
									
									qty = line.getQtyEntered().multiply(factor);
									
								}								
								
								// Para anulaciones o reactivaciones debo enviar la cantidad en signo contrario para darla vuelta
								if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){
									qty = qty.multiply(new BigDecimal(-1));

								}

								// Chequeo disponibilidad de este producto
								message = MStockTransaction.checkAvailability(line.getM_Warehouse_ID(),-1 /*model.getM_Locator_ID()*/, 
										productId, 0, fechaMovimiento, model.getAD_Client_ID(), model.get_TrxName());
								if (message != null) return message;

								//								//Bajo stock aprobado
								//								int idStatusApproved = MStockStatus.getStatusApprovedID(null);
								//								message = MStockTransaction.add(model, null, model.getM_Warehouse_ID(), -1/*model.getM_Locator_ID()*/, 
								//										productId,	0, idStatusApproved, fechaMovimiento, new BigDecimal(-1).multiply(qty), -1/*line.getUY_ManufLine_ID()*/, null);
								//
								//								if (message != null) return message;
								//Sube stock reservado
								int idStatusReserved = MStockStatus.getStatusReservedID(null);
								message = MStockTransaction.add(model, null, line.getM_Warehouse_ID(), -1, 
										productId, 0, idStatusReserved, fechaMovimiento, qty, -1, null);
								if (message!=null) return message;									

							}
					
					}

				}	

		}
		return message;
	}



}
