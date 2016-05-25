package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MBPartner;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

public class MInvReferFilter extends X_UY_InvReferFilter implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int ivaExcento = 1000002;	
	private String processMsg = null;

	public MInvReferFilter(Properties ctx, int UY_InvReferFilter_ID,
			String trxName) {
		super(ctx, UY_InvReferFilter_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		// Proceso segun accion
		this.processMsg = null;
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare))
			return this.loadRefers();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete))
			return this.generateInvoices();
		else
			return true;
	}

	/**
	 * 
	 * OpenUp. issue #925	
	 * Descripcion : Carga informacion de los remitos existentes
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 15/11/2011
	 */
	private boolean loadRefers() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;
		
		try
		{
			log.info("Iniciando carga de remitos...");

			// Condicion de Filtros
			String whereFiltros = "";

			if (this.getuy_dateinvoiced_from()!=null) whereFiltros += " AND hdr.dateinvoiced >='" + this.getuy_dateinvoiced_from() + "'";
			if (this.getuy_dateinvoiced_to()!=null) whereFiltros += " AND hdr.dateinvoiced <='" + this.getuy_dateinvoiced_to() + "'";
			if (this.getC_BPartner_ID()>0) whereFiltros += " AND hdr.c_bpartner_id =" + this.getC_BPartner_ID();
			if (this.getUY_CanalVentas_ID()>0) whereFiltros += " AND cli.uy_canalventas_id =" + this.getUY_CanalVentas_ID();
			if (this.getC_Invoice_ID()>0) whereFiltros += " AND hdr.c_invoice_id =" + this.getC_Invoice_ID();
			if (this.getAD_User_ID()>0) whereFiltros += " AND hdr.salesrep_id =" + this.getAD_User_ID();
			
			sql = "SELECT hdr.c_invoice_id,hdr.c_bpartner_id,hdr.c_doctypetarget_id," +
			      " hdr.documentno,hdr.dateinvoiced,(hdr.uy_cantbultos + hdr.uy_cantbultos_manual) as bultos,hdr.uy_asignatransportehdr_id,hdr.uy_reservapedidohdr_id" +
                  " FROM c_invoice hdr" +
                  " INNER JOIN c_bpartner cli on hdr.c_bpartner_id=cli.c_bpartner_id" +
                  " WHERE hdr.docstatus='CO' AND hdr.isactive='Y' AND hdr.ad_client_id=?" +
                  " AND NOT EXISTS (select fact.c_invoice_id from c_invoice fact where uy_remito_id=hdr.c_invoice_id)" + 
                  " AND hdr.c_doctypetarget_id in (select c_doctype_id from c_doctype where coalesce(docsubtypeso,'') = 'RR')" +
				    whereFiltros +
				  " ORDER BY hdr.dateinvoiced";
	
			log.info(sql);
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());
			rs = pstmt.executeQuery();
			
			MInvReferDetail refDetail = null;
		
			while (rs.next())
			{	
										
					refDetail = new MInvReferDetail(getCtx(), 0, get_TrxName());
					
					refDetail.setUY_InvReferFilter_ID(this.getUY_InvReferFilter_ID());
					refDetail.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
					refDetail.setC_DocType_ID(rs.getInt("C_DocTypeTarget_ID"));
					refDetail.setC_Invoice_ID(rs.getInt("C_Invoice_ID"));
					refDetail.setDateInvoiced(rs.getTimestamp("DateInvoiced"));
					refDetail.setbultos(rs.getInt("bultos"));
					refDetail.setUY_AsignaTransporteHdr_ID(rs.getInt("uy_asignatransportehdr_id"));
					refDetail.setUY_ReservaPedidoHdr_ID(rs.getInt("uy_reservapedidohdr_id"));
						
					// Save para que me genere el ID
					refDetail.saveEx(get_TrxName());
					
				
				}
					
			// Save de ultimo remito
			if (refDetail != null){
				refDetail.saveEx(get_TrxName());
			}
			
			rs.close();
			pstmt.close();

			// Actualizo atributos de filtros
			this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
			this.setDocAction(DocumentEngine.ACTION_Complete);
			this.saveEx();
			
			log.info("Fin carga de Remitos.");
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			result = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return result;
	}
	
	/**
	 * 
	 * OpenUp. issue #925	
	 * Descripcion : Se generan e imprimen las facturas a partir de los remitos seleccionados
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 16/11/2011
	 */
	
	private boolean generateInvoices() {
	
		boolean procesoOK = true;
		Trx trans = null;
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		
		try{
			// Obtengo remitos seleccionados
			MInvReferDetail[] lines = this.getLines();
			
			// Para cada remito
			for (int i=0; i<lines.length; i++){
				MInvReferDetail line = lines[i];
								
				// Genero nueva transaccion
				String trxNameAux = Trx.createTrxName();
				trans = Trx.get(trxNameAux, true);

				// Obtengo el remito
				MInvoice refer = new MInvoice(getCtx(), line.getC_Invoice_ID(), trxNameAux);
				
				// Obtengo cabezal de reserva de pedido
				MReservaPedidoHdr resHdr = new MReservaPedidoHdr(getCtx(), line.getUY_ReservaPedidoHdr_ID(), trxNameAux);
				
				// Obtengo cabezal de pedido asociado a cabezal de reserva
				MOrder orderHdr = new MOrder(getCtx(), resHdr.getC_Order_ID(), trxNameAux);
				
				// Obtengo cliente de la transaccion
				MBPartner cliente = new MBPartner(getCtx(), line.getC_BPartner_ID(), trxNameAux);
						
				//Creo la factura asociada al remito
				MInvoice invoiceHdr = new MInvoice(getCtx(), 0, trxNameAux);
				
				//Seteo campos de la nueva factura
				invoiceHdr.setC_PaymentTerm_ID(cliente.getC_PaymentTerm_ID());
				invoiceHdr.setUY_AsignaTransporteHdr_ID(line.getUY_AsignaTransporteHdr_ID());
				invoiceHdr.setUY_ReservaPedidoHdr_ID(new BigDecimal(resHdr.getUY_ReservaPedidoHdr_ID()));
				invoiceHdr.setC_Order_ID(orderHdr.getC_Order_ID());
				invoiceHdr.setC_BPartner_ID(cliente.getC_BPartner_ID());
				invoiceHdr.setC_BPartner_Location_ID(orderHdr.getC_BPartner_Location_ID());
				invoiceHdr.setUY_Remito_ID(new BigDecimal(line.getC_Invoice_ID()));
				invoiceHdr.setC_DocType_ID(1000002);
				invoiceHdr.setC_DocTypeTarget_ID(1000002);
				invoiceHdr.setDateInvoiced(today);
				invoiceHdr.setdatetimeinvoiced(new Timestamp(System.currentTimeMillis()));
				invoiceHdr.setuy_cantbultos(refer.getuy_cantbultos());
				invoiceHdr.setuy_cantbultos_manual(refer.getuy_cantbultos_manual());
				invoiceHdr.setSalesRep_ID(refer.getSalesRep_ID());
				invoiceHdr.setC_Currency_ID(refer.getC_Currency_ID());
				invoiceHdr.setM_PriceList_ID(refer.getM_PriceList_ID());
				invoiceHdr.setDateOrdered(refer.getDateOrdered());
				invoiceHdr.setM_InOut_ID(refer.getM_InOut_ID());
								
				invoiceHdr.saveEx(trxNameAux);
				
				// Obtengo las lineas del remito		
				MInvoiceLine[] refLines = refer.getLines();  
			
				//BigDecimal cantidadBultos = Env.ZERO;
				int contadorLines = 0;
				
				// Para cada linea del remito
				for (int j=0; j<refLines.length; j++){
					MInvoiceLine refLine = refLines[j];
				
					// Obtengo linea de pedido asociada a linea de remito
					MOrderLine orderLine = new MOrderLine(getCtx(), refLine.getC_OrderLine_ID(), trxNameAux);
					
					// Genero linea de factura
					MInvoiceLine invLine = new MInvoiceLine(invoiceHdr);
					invLine.setC_Invoice_ID(invoiceHdr.getC_Invoice_ID());
					invLine.setM_AttributeSetInstance_ID(refLine.getM_AttributeSetInstance_ID());
					invLine.setM_Product_ID(refLine.getM_Product_ID());
					invLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
					invLine.setM_InOutLine_ID(refLine.getM_InOutLine_ID());
					invLine.setC_UOM_ID(refLine.getC_UOM_ID());						
					invLine.setC_Tax_ID(refLine.getC_Tax_ID());
							
					invLine.setQtyEntered(refLine.getQtyEntered());
					invLine.setQtyInvoiced(refLine.getQtyInvoiced().subtract(refLine.getUY_BonificaReglaUM()));
					invLine.setuy_bonificaregla(refLine.getuy_bonificaregla());
					invLine.setUY_BonificaReglaUM(refLine.getUY_BonificaReglaUM());
					
					//se calculan precios segun si se eligio usar el precio original del pedido o el precio actual calculado
					if(line.getuy_precio().equalsIgnoreCase("PED")){
					
						invLine.setPriceActual(orderLine.getPriceActual());
						invLine.setPriceEntered(orderLine.getPriceEntered());
						invLine.setPriceLimit(orderLine.getPriceLimit());
						invLine.setPriceList(orderLine.getPriceList());
						invLine.setPrice(orderLine.getPriceActual());
						invLine.setFlatDiscount(orderLine.getFlatDiscount());
						invLine.setuy_promodiscount(orderLine.getuy_promodiscount());
					}else{
						invLine.setPrice(invoiceHdr.getM_PriceList_ID(), invoiceHdr.getC_BPartner_ID());
				
					}

					BigDecimal factorUOM = Env.ONE;
					if (invLine.getC_UOM_ID() != 100) factorUOM = MUOMConversion.getProductRateFrom(getCtx(), orderLine.getM_Product_ID(), orderLine.getC_UOM_ID());
					
					if (factorUOM == null){
						MProduct prod = new MProduct(getCtx(), invLine.getM_Product_ID(), null);
						MUOM uom = new MUOM(getCtx(), invLine.getC_UOM_ID(), null);
						procesoOK = false;
						throw new Exception("El Producto : " + prod.getValue() + " - " + prod.getName() + "\n" + 
						" no tiene FACTOR DE CONVERSION DEFINIDO entre la unidad de medida y la unidad de venta (" + uom.getUOMSymbol() + ")");
					}
					invLine.setuy_printprice(invLine.getPriceList().multiply(factorUOM));
	
					if (orderLine.isUY_EsBonificCruzada()) {
						invLine.setUY_EsBonificCruzada(true);

						invLine.setQtyEntered(Env.ZERO);
						invLine.setQtyInvoiced(Env.ZERO);

						invLine.setQtyInvoiced(Env.ZERO);
						invLine.setPriceActual(Env.ZERO);
						invLine.setPriceEntered(Env.ZERO);
						invLine.setPriceLimit(Env.ZERO);
						invLine.setPriceList(Env.ZERO);
						invLine.setPrice(Env.ZERO);
						invLine.set_ValueOfColumn("flatdiscount", Env.ZERO);
						invLine.set_ValueOfColumn("uy_promodiscount", Env.ZERO);
						invLine.set_ValueOfColumn("uy_printprice", Env.ZERO);
						invLine.setLineNetAmt(Env.ZERO);
						invLine.setTaxAmt(Env.ZERO);
						invLine.setC_Tax_ID(ivaExcento);
					}
					
					invLine.saveEx(trxNameAux);
					contadorLines++;
				}
			
				// Si tengo lineas de entrega para esta factura...
				if (contadorLines > 0){
					
					// Completo Factura
					if (!invoiceHdr.processIt(DocAction.ACTION_Complete)) {
						procesoOK = false;
						throw new Exception("No se pudo completar Factura para Remito : " + resHdr.getDocumentNo() + "\n" + invoiceHdr.getProcessMsg());
					}
					
					
					invoiceHdr.saveEx(trxNameAux);
					trans.close();

					// Imprimo factura
					try{
						Env.getCtx().put("parmInvoice", invoiceHdr.getC_Invoice_ID());					
						ReportCtl.startDocumentPrint(ReportEngine.INVOICE, invoiceHdr.getC_Invoice_ID(), true);

						// Pausa de 2 segundos preventiva para impresion masiva
						java.lang.Thread.sleep(1000);
										
						
					}
					catch (Exception e){
						log.log(Level.SEVERE, "Error al imprimir factura : " + invoiceHdr.getC_Invoice_ID(), e);
					}
				}
				else{	
					if (trans!=null){
						trans.rollback();
					}
				}
			}
			
			this.setDocAction(DocumentEngine.ACTION_None);
			this.setDocStatus(DocumentEngine.STATUS_Completed);
			this.setProcessed(true);
			this.saveEx(get_TrxName());
		}
		catch (Exception e){
			if (trans!=null){
				trans.rollback();
			}
			procesoOK = false;
			log.log(Level.SEVERE, "Error en proceso de generación de facturas.", e);
			this.processMsg = e.getMessage();
		}
		
		return procesoOK;
		
		
	}
	
	/**
	 * OpenUp. issue #925	
	 * Descripcion : Obtiene y retorna array de remitos seleccionados
	 * @param mPromotionID
	 * @return
	 * @throws Exception
	 * @author  Nicolas Sarlabos
	 * Fecha : 16/11/2011
	 */
	public MInvReferDetail[] getLines() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MInvReferDetail> list = new ArrayList<MInvReferDetail>();
		
		try{
			sql ="SELECT uy_invreferdetail_id" +
				 " FROM uy_invreferdetail" +
				 " WHERE uy_procesar='Y' AND uy_invreferfilter_id=?"; 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_InvReferFilter_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MInvReferDetail value = new MInvReferDetail(Env.getCtx(), rs.getInt(1), null);
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
		
		return list.toArray(new MInvReferDetail[list.size()]);		
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
