/**
 * MProcesoFactmasHdr.java
 * 28/12/2010
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AWindow;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

/**
 * OpenUp.
 * MProcesoFactmasHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 28/12/2010
 */
public class MProcesoFactmasHdr extends X_UY_ProcesoFactmasHdr implements
		DocAction {

	private static final long serialVersionUID = -7235325002536301669L;
	private String processMsg = null;
	private boolean justPrepared = false;
	private static final int UNIDAD_MEDIDA_DEFAULT = 100;
	private static final int ivaExcento = 1000002;	
	
	private AWindow window = null;
	
	/**
	 * Constructor
	 * @param ctx
	 * @param UY_ProcesoFactmasHdr_ID
	 * @param trxName
	 */
	public MProcesoFactmasHdr(Properties ctx, int UY_ProcesoFactmasHdr_ID,
			String trxName) {
		super(ctx, UY_ProcesoFactmasHdr_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProcesoFactmasHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	// OpenUp. Gabriel Vila. 05/10/2011. Issue #894.
	// Get - Set de la ventana que puede contener o no al proceso.
	public AWindow getWindow() {
		return this.window;
	}

	public void setWindow(AWindow value) {
		this.window = value;
	}// Fin Issue #894.

	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		//	Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			this.processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		
		if (!procesoFacturasMasivo())
		{
			return DocAction.STATUS_Invalid;
		}

		// Refresco estados
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Ejecuta proceso de generacion masiva de facturas y el movimiento de stock correspondiente.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/12/2010
	 */
	private boolean procesoFacturasMasivo() {
	
		boolean procesoOK = true;
		Trx trans = null;
		
		try{
			
			this.showText("Iniciando Proceso");
			this.showText("Obteniendo lineas a procesar");
			
			// Obtengo lineas de proceso de facturas
			MProcesoFactmasLine[] lines = this.getLines();
			
			int rowCount = 0;
			String avance = "";
			
			// Para cada linea
			for (int i=0; i<lines.length; i++){
				MProcesoFactmasLine line = lines[i];
			
				rowCount++;
				avance = rowCount + " de " + lines.length;
				
				showText(avance + ": Creando Transaccion");
				
				// Genero nueva transaccion
				String trxNameAux = Trx.createTrxName();
				trans = Trx.get(trxNameAux, true);

				this.showText(avance + ": Datos Reserva");
				
				// Obtengo cabezal de reserva asociado a esta linea de proceso
				MReservaPedidoHdr resHdr = new MReservaPedidoHdr(getCtx(), line.getUY_ReservaPedidoHdr_ID(), trxNameAux);

				this.showText(avance + ": Datos Pedido");
				
				// Obtengo cabezal de pedido asociado a cabezal de reserva
				MOrder orderHdr = new MOrder(getCtx(), resHdr.getC_Order_ID(), trxNameAux);
				
				this.showText(avance + ": Datos Cliente");
				
				// Cliente de la transaccion
				MBPartner cliente = new MBPartner(getCtx(), orderHdr.getC_BPartner_ID(), trxNameAux);

				this.showText(avance + ": Datos ATR");
				
				// Transporte del proceso
				MAsignaTransporteHdr transpHdr = new MAsignaTransporteHdr(getCtx(), this.getUY_AsignaTransporteHdr_ID(), trxNameAux);
				
				this.showText(avance + ": Inicia entrega");
				
				// Genero cabezal de entrega para esta reserva
				MInOut inoutHdr = new MInOut(orderHdr, 0, this.getDateTrx());
				inoutHdr.setUY_ReservaPedidoHdr_ID(resHdr.getUY_ReservaPedidoHdr_ID());
				inoutHdr.setAD_Org_ID(this.getAD_Org_ID());
				inoutHdr.saveEx(trxNameAux);
				
				this.showText(avance + ": Inicia factura");
				
				MInvoice invoiceHdr = new MInvoice(inoutHdr, this.getDateTrx());
				
				// OpenUp. Gabriel Vila. 03/02/2011.
				// Ahora puedo generar remitos
				if (this.get_Value("uy_tipogeneracion").toString().equalsIgnoreCase("REM")){
					MDocType mdoc= MDocType.forValue(getCtx(), "remito",get_TrxName());
					if(mdoc!=null && mdoc.get_ID()>0 ){
						invoiceHdr.setC_DocTypeTarget_ID(mdoc.get_ID()); // ID del doc de Remito
					}
					else{
						throw new AdempiereException("No se encontro tipo de documento remito");
					}				
					invoiceHdr.setPosted(true); // Si es remito no contabiliza
				}
				// Fin OpenUp.
				
				invoiceHdr.setC_PaymentTerm_ID(cliente.getC_PaymentTerm_ID());
				invoiceHdr.setUY_AsignaTransporteHdr_ID(transpHdr.getUY_AsignaTransporteHdr_ID());
				invoiceHdr.setUY_ReservaPedidoHdr_ID(new BigDecimal(resHdr.getUY_ReservaPedidoHdr_ID()));
				invoiceHdr.setC_BPartner_Location_ID(orderHdr.getC_BPartner_Location_ID());
				invoiceHdr.saveEx(trxNameAux);
				
				line.set_ValueOfColumn("C_Invoice_ID", invoiceHdr.getC_Invoice_ID());
				line.set_ValueOfColumn("M_InOut_ID", inoutHdr.getM_InOut_ID());
				
				this.showText(avance + ": Lineas reserva");
				
				// Obtengo lineas de la reserva asociada a esta linea de proceso				
				MReservaPedidoLine[] resLines = resHdr.getLines(null);  

				BigDecimal cantidadBultos = Env.ZERO;
				
				int contadorInOutLines = 0, contLinRes = 0;
				
				// Para cada linea de reserva
				for (int j=0; j<resLines.length; j++){
					MReservaPedidoLine resLine = resLines[j];

					contLinRes++;
					this.showText(avance + ": Lineas res. " + contLinRes + " de " + resLines.length);
					
					// Puedo tener cantidades reservadas en cero ya que fueron anuladas totalmente. Me cubro.
					if (resLine.getQtyReserved().compareTo(Env.ZERO)>0){
						// Obtengo linea de pedida asociada a linea de reserva
						MOrderLine orderLine = new MOrderLine(getCtx(), resLine.getC_OrderLine_ID(), trxNameAux);
						
						// Genero linea de entrega
						MInOutLine inoutLine = new MInOutLine(inoutHdr);
						inoutLine.setOrderLine(orderLine, 0, resLine.getQtyReserved());
						inoutLine.setM_Product_ID(orderLine.getM_Product_ID());
						inoutLine.setC_OrderLine_ID(resLine.getC_OrderLine_ID());
						inoutLine.setC_UOM_ID(orderLine.getC_UOM_ID());
						inoutLine.setM_AttributeSetInstance_ID(orderLine.getM_AttributeSetInstance_ID());
						inoutLine.setM_Warehouse_ID(orderLine.getM_Warehouse_ID());
						inoutLine.setMovementQty(resLine.getQtyReserved());
						inoutLine.setQty(resLine.getQtyReserved());
						inoutLine.setQtyEntered(resLine.getQtyEntered());

						inoutLine.setUY_BonificaReglaUM(resLine.getUY_BonificaReglaUM());
						inoutLine.setuy_bonificaregla(resLine.getuy_bonificaregla());

						//this.getWindow().setWaitingMessage(avance + ": Guarda linea entrega");
						
						inoutLine.saveEx(trxNameAux);

						contadorInOutLines++;
						
						// Cantidad de bultos automaticos cuando no es unidad de medida
						if (resLine.getC_UOM_ID() != UNIDAD_MEDIDA_DEFAULT)
						// OpenUp. Nicolas Garcia. 01/11/2011. Issue #821.
						// Se agrega a bultos la bonificacion simple	
							cantidadBultos = cantidadBultos.add(resLine.getQtyEntered().add(resLine.getuy_bonificaregla()));
						
												
						// Obtengo factor de conversion de UM-UV
						BigDecimal factorUOM = Env.ONE;
						if (orderLine.getC_UOM_ID() != 100) factorUOM = MUOMConversion.getProductRateFrom(getCtx(), orderLine.getM_Product_ID(), orderLine.getC_UOM_ID());
						
						if (factorUOM == null){
							MProduct prod = new MProduct(getCtx(), orderLine.getM_Product_ID(), null);
							MUOM uom = new MUOM(getCtx(), orderLine.getC_UOM_ID(), null);
							procesoOK = false;
							throw new Exception("El Producto : " + prod.getValue() + " - " + prod.getName() + "\n" + 
							" no tiene FACTOR DE CONVERSION DEFINIDO entre la unidad de medida y la unidad de venta (" + uom.getUOMSymbol() + ")");
						}
						
						// Genero linea de factura
						MInvoiceLine invoiceLine = new MInvoiceLine(invoiceHdr);
						
						//invoiceLine.setOrderLine(orderLine);
						invoiceLine.setShipLine(inoutLine);

						invoiceLine.setC_UOM_ID(orderLine.getC_UOM_ID());
						invoiceLine.setM_AttributeSetInstance_ID(orderLine.getM_AttributeSetInstance_ID());
						invoiceLine.setM_InOutLine_ID(inoutLine.getM_InOutLine_ID());
						invoiceLine.setM_Product_ID(orderLine.getM_Product_ID());
						invoiceLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());

						invoiceLine.setQty(resLine.getQtyReserved());
						invoiceLine.setQtyEntered(resLine.getQtyEntered());
						invoiceLine.setQtyInvoiced(resLine.getQtyReserved().subtract(resLine.getUY_BonificaReglaUM()));

						invoiceLine.setuy_bonificaregla(resLine.getuy_bonificaregla());
						invoiceLine.setUY_BonificaReglaUM(resLine.getUY_BonificaReglaUM());

						invoiceLine.setPriceActual(orderLine.getPriceActual());
						invoiceLine.setPriceEntered(orderLine.getPriceEntered());
						invoiceLine.setPriceLimit(orderLine.getPriceLimit());
						invoiceLine.setPriceList(orderLine.getPriceList());
						invoiceLine.setPrice(orderLine.getPriceActual());
						invoiceLine.setFlatDiscount(orderLine.getFlatDiscount());
						invoiceLine.setuy_promodiscount(orderLine.getuy_promodiscount());
						invoiceLine.setuy_printprice(orderLine.getPriceList().multiply(factorUOM));
						

						if (orderLine.isUY_EsBonificCruzada()) {
							invoiceLine.setUY_EsBonificCruzada(true);

							invoiceLine.setQtyEntered(Env.ZERO);
							invoiceLine.setQtyInvoiced(Env.ZERO);

							invoiceLine.setQtyInvoiced(Env.ZERO);
							invoiceLine.setPriceActual(Env.ZERO);
							invoiceLine.setPriceEntered(Env.ZERO);
							invoiceLine.setPriceLimit(Env.ZERO);
							invoiceLine.setPriceList(Env.ZERO);
							invoiceLine.setPrice(Env.ZERO);
							invoiceLine.set_ValueOfColumn("flatdiscount", Env.ZERO);
							invoiceLine.set_ValueOfColumn("uy_promodiscount", Env.ZERO);
							invoiceLine.set_ValueOfColumn("uy_printprice", Env.ZERO);
							invoiceLine.setLineNetAmt(Env.ZERO);
							invoiceLine.setTaxAmt(Env.ZERO);
							invoiceLine.setC_Tax_ID(ivaExcento);
						}
					//Fin Issue #821
						// OpenUp. Gabriel Vila. 03/02/2011
						// Si es un remito no tengo precio ni importes en linea
						if (this.get_Value("uy_tipogeneracion").toString().equalsIgnoreCase("REM")){
							invoiceLine.setPriceActual(Env.ZERO);
							invoiceLine.setPriceEntered(Env.ZERO);
							invoiceLine.setPriceLimit(Env.ZERO);
							invoiceLine.setPriceList(Env.ZERO);
							invoiceLine.setPrice(Env.ZERO);
							invoiceLine.set_ValueOfColumn("flatdiscount", Env.ZERO);
							invoiceLine.set_ValueOfColumn("uy_promodiscount", Env.ZERO);
							invoiceLine.set_ValueOfColumn("uy_printprice", Env.ZERO);
							invoiceLine.setLineNetAmt(Env.ZERO);
							invoiceLine.setTaxAmt(Env.ZERO);
							invoiceLine.setC_Tax_ID(ivaExcento);
						}
						// Fin OpenUp.
						
						//this.getWindow().setWaitingMessage(avance + ": Guarda linea factura");
						
						invoiceLine.saveEx(trxNameAux);
					}
					
				}
			
				// Si tengo lineas de entrega generadas para esta reserva.
				// Puedo tener una reserva con sus lineas en cantidad=cero por anulacion y 
				// por lo tanto no genera entrega/factura.
				if (contadorInOutLines > 0){
					
					this.showText(avance + ": Completa entrega");
					
					// Completo InOut
					if (inoutHdr.processIt(DocAction.ACTION_Complete))
						inoutHdr.saveEx(trxNameAux);
					else{
						procesoOK = false;
						throw new Exception("No se pudo completar Entrega para Reserva : " + resHdr.getDocumentNo() + "\n" + inoutHdr.getProcessMsg());
					}						
					
					this.showText(avance + ": Completa factura");
					
					// Completo Factura
					if (invoiceHdr.processIt(DocAction.ACTION_Complete)) {
						invoiceHdr.setC_BPartner_Location_ID(orderHdr.getC_BPartner_Location_ID());

						// OpenUp. Gabriel Vila. 24/03/2011.Issue #487
						// Me aseguro que la cantidad de bultos quede asociada a la linea de la factura
						invoiceHdr.setuy_cantbultos(cantidadBultos);
						invoiceHdr.setuy_cantbultos_manual(Env.ZERO);
						// Fin OpenUp.
						
						invoiceHdr.saveEx(trxNameAux);
					}					
					else{
						procesoOK = false;
						throw new Exception("No se pudo completar Factura para Reserva : " + resHdr.getDocumentNo() + "\n" + invoiceHdr.getProcessMsg());
					}
					
					this.showText(avance + ": Inicia asociacion fact-atr");
					
					// Genero nueva asignacion de factura al transporte, asociando esta factura
					MAsignaTransporteFact transpFact = new MAsignaTransporteFact(invoiceHdr);
					transpFact.setUY_AsignaTransporteHdr_ID(transpHdr.getUY_AsignaTransporteHdr_ID());
					transpFact.setUY_ReservaPedidoHdr_ID(resHdr.getUY_ReservaPedidoHdr_ID());
					transpFact.setuy_cantbultos(cantidadBultos);
					transpFact.setuy_cantbultos_manual(Env.ZERO);
					transpFact.saveEx(trxNameAux);

					this.showText(avance + ": Guarda asociacion fact-atr");
					
					// Guardo cambios en linea de proceso
					line.saveEx(trxNameAux);
					
					this.showText(avance + ": Commit");
					
					// Commit
					//trans.commit();
					trans.close();

					this.showText(avance + ": Inicia Impresion");
					
					// Imprimo factura
					try{
						Env.getCtx().put("parmInvoice", invoiceHdr.getC_Invoice_ID());					
						ReportCtl.startDocumentPrint(ReportEngine.INVOICE, invoiceHdr.getC_Invoice_ID(), true);

						// Pausa de 3 segundos preventiva para impresion masiva
						java.lang.Thread.sleep(3000);
						
						this.showText(avance + ": Fin Impresion");
						
					}
					catch (Exception e){
						log.log(Level.SEVERE, "Error al imprimir factura : " + invoiceHdr.getC_Invoice_ID(), e);
					}
				}
				else{	// No se generaron lineas de entrega/factura para esta reserva
					if (trans!=null){
						this.showText(avance + ": Rollback");
						trans.rollback();
					}
				}
			}
		}
		catch (Exception e){
			if (trans!=null){
				this.showText("Rollback");
				trans.rollback();
			}
			procesoOK = false;
			log.log(Level.SEVERE, "Error en proceso masivo de facturas.", e);
			this.processMsg = e.getMessage();
		}
		
		this.showText("Fin Proceso");
		
		return procesoOK;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocStatus()
	 */
	@Override
	public String getDocStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#setDocStatus(java.lang.String)
	 */
	@Override
	public void setDocStatus(String newStatus) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna array de lineas de proceso de facturacion masiva.
	 * @param mPromotionID
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 28/12/2010
	 */
	public MProcesoFactmasLine[] getLines() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MProcesoFactmasLine> list = new ArrayList<MProcesoFactmasLine>();
		
		try{
			sql ="SELECT uy_procesofactmasline_id " + 
 		  	" FROM " + X_UY_ProcesoFactmasLine.Table_Name + 
		  	" WHERE uy_procesofactmashdr_id =?"; 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_ProcesoFactmasHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MProcesoFactmasLine value = new MProcesoFactmasLine(Env.getCtx(), rs.getInt(1), null);
				list.add(value);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return list.toArray(new MProcesoFactmasLine[list.size()]);		
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	private void showText(String message){
		
		if (this.getWindow() != null){
			this.getWindow().setWaitingMessage("Iniciando Reserva");	
		}
	}
}
