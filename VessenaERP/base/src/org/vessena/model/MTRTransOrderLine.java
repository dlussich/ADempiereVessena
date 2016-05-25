/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.aduana.ConsultaRespuestaMic;


/**
 * @author gbrust
 *
 */
public class MTRTransOrderLine extends X_UY_TR_TransOrderLine {

	/**
	 * 
	 */
	public boolean isFromTrip = false;
	private static final long serialVersionUID = -5484901758296918569L;

	/**
	 * @param ctx
	 * @param UY_TR_TransOrderLine_ID
	 * @param trxName
	 */
	public MTRTransOrderLine(Properties ctx, int UY_TR_TransOrderLine_ID,
			String trxName) {
		super(ctx, UY_TR_TransOrderLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		MTRTripQty tripQty = null;
		MTRInvoiceFreightAmt freightAmt = null;
		MTRCrt crt = (MTRCrt)this.getUY_TR_Crt();
		MInvoice inv = null;
		
		if(crt.getC_Invoice_ID()>0) inv = (MInvoice)crt.getC_Invoice();
		
		if ((!newRecord) && is_ValueChanged(COLUMNNAME_IsReleased)){
			return true;
		}
		
		//Se guarda el total en la orden de transporte
		MTRTransOrder order = new MTRTransOrder (getCtx(), this.getUY_TR_TransOrder_ID(), get_TrxName());
		
		BigDecimal newAmt = this.getMontoTotalLineas();	
		
		order.setTotalAmt(newAmt);			
		order.saveEx();		
		
		//refresco MIC/DTA si es necesario
		if(!isFromTrip){
			
			MTRMic hdr = MTRMic.forOT(getCtx(), this.getUY_TR_TransOrder_ID(), get_TrxName());
			
			if(hdr != null && hdr.get_ID() > 0){
				
				MTRMicLine mLine = MTRMicLine.forOrderLine(getCtx(), this.get_ID(), get_TrxName()); //obtengo linea de CRT en MIC
				
				this.refreshMic(false,newRecord,mLine);
				
			}		
		}	
		
		//inserto o actualizo en tablas de distribucion de expediente y proforma
		if(newRecord){
			
			//obtengo registros de distribucion en expediente y proforma, para esta OT y CRT
			tripQty = MTRTripQty.forOTCrt(getCtx(),this.getUY_TR_TransOrder_ID(), this.getUY_TR_Crt_ID(),get_TrxName()); 
			freightAmt = MTRInvoiceFreightAmt.forOTCrt(this.getUY_TR_TransOrder_ID(), this.getUY_TR_Crt_ID(), get_TrxName());			
			
			if(tripQty!=null && tripQty.get_ID()>0){
				
				tripQty.setUY_TR_Trip_ID(this.getUY_TR_Trip_ID());
				tripQty.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
				tripQty.setUY_TR_TransOrderLine_ID(this.getUY_TR_TransOrderLine_ID());
				tripQty.setUY_TR_Crt_ID(this.getUY_TR_Crt_ID());
				tripQty.setWeight(this.getWeight());
				tripQty.setWeight2(this.getWeight2());
				tripQty.setVolume(this.getVolume());
				tripQty.setQtyPackage(this.getQtyPackage());
				tripQty.setC_Currency_ID(this.getC_Currency_ID());
				tripQty.setProductAmt(this.getProductAmt());
				tripQty.setUY_TR_LoadMonitorLine_ID(this.getUY_TR_LoadMonitorLine_ID());
				tripQty.saveEx();			
				
			} else {
				
				tripQty = new MTRTripQty(getCtx(), 0, get_TrxName());
				tripQty.setUY_TR_Trip_ID(this.getUY_TR_Trip_ID());
				tripQty.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
				tripQty.setUY_TR_TransOrderLine_ID(this.getUY_TR_TransOrderLine_ID());
				tripQty.setUY_TR_Crt_ID(this.getUY_TR_Crt_ID());
				tripQty.setWeight(this.getWeight());
				tripQty.setWeight2(this.getWeight2());
				tripQty.setVolume(this.getVolume());
				tripQty.setQtyPackage(this.getQtyPackage());
				tripQty.setC_Currency_ID(this.getC_Currency_ID());
				tripQty.setProductAmt(this.getProductAmt());
				tripQty.setUY_TR_LoadMonitorLine_ID(this.getUY_TR_LoadMonitorLine_ID());
				tripQty.saveEx();						
			}
			
			if(inv!=null){
				
				if(freightAmt!=null && freightAmt.get_ID()>0){
					
					freightAmt.setC_Invoice_ID(inv.get_ID());
					freightAmt.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
					freightAmt.setUY_TR_TransOrderLine_ID(this.getUY_TR_TransOrderLine_ID());
					freightAmt.setUY_TR_Crt_ID(this.getUY_TR_Crt_ID());
					freightAmt.setWeight(this.getWeight());
					freightAmt.setWeight2(this.getWeight2());
					freightAmt.setVolume(this.getVolume());
					freightAmt.setQtyPackage(this.getQtyPackage());
					freightAmt.setC_Currency_ID(this.getC_Currency_ID());
					freightAmt.setC_Currency2_ID(inv.getC_Currency_ID());
					freightAmt.setProductAmt(this.getProductAmt());
					freightAmt.setUY_TR_LoadMonitorLine_ID(this.getUY_TR_LoadMonitorLine_ID());
					freightAmt.setAmount(this.getAmount());
					freightAmt.saveEx();				
					
				} else {
					
					freightAmt = new MTRInvoiceFreightAmt(getCtx(), 0, get_TrxName());
					freightAmt.setC_Invoice_ID(inv.get_ID());
					freightAmt.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
					freightAmt.setUY_TR_TransOrderLine_ID(this.getUY_TR_TransOrderLine_ID());
					freightAmt.setUY_TR_Crt_ID(this.getUY_TR_Crt_ID());
					freightAmt.setWeight(this.getWeight());
					freightAmt.setWeight2(this.getWeight2());
					freightAmt.setVolume(this.getVolume());
					freightAmt.setQtyPackage(this.getQtyPackage());
					freightAmt.setC_Currency_ID(this.getC_Currency_ID());
					freightAmt.setC_Currency2_ID(inv.getC_Currency_ID());
					freightAmt.setProductAmt(this.getProductAmt());
					freightAmt.setUY_TR_LoadMonitorLine_ID(this.getUY_TR_LoadMonitorLine_ID());
					freightAmt.setAmount(this.getAmount());
					freightAmt.saveEx();		
					
				}			
				
			}			
			
		} else {
			
			tripQty = MTRTripQty.forOTLine(getCtx(),this.get_ID(),get_TrxName()); //obtengo registro para esta linea de OT en registros de distribucion del expediente
			freightAmt = MTRInvoiceFreightAmt.forOTLine(getCtx(),this.get_ID(),get_TrxName()); //obtengo registro para esta linea de OT en registros de distribucion del proforma		
			
			if(tripQty!=null && tripQty.get_ID()>0){
				
				tripQty.setUY_TR_Trip_ID(this.getUY_TR_Trip_ID());
				tripQty.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
				tripQty.setUY_TR_TransOrderLine_ID(this.getUY_TR_TransOrderLine_ID());
				tripQty.setUY_TR_Crt_ID(this.getUY_TR_Crt_ID());
				tripQty.setWeight(this.getWeight());
				tripQty.setWeight2(this.getWeight2());
				tripQty.setVolume(this.getVolume());
				tripQty.setQtyPackage(this.getQtyPackage());
				tripQty.setC_Currency_ID(this.getC_Currency_ID());
				tripQty.setProductAmt(this.getProductAmt());
				tripQty.setUY_TR_LoadMonitorLine_ID(this.getUY_TR_LoadMonitorLine_ID());
				tripQty.saveEx();				
			}	
			
			if(inv!=null){
				
				if(freightAmt!=null && freightAmt.get_ID()>0){

					freightAmt.setC_Invoice_ID(inv.get_ID());
					freightAmt.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
					freightAmt.setUY_TR_TransOrderLine_ID(this.getUY_TR_TransOrderLine_ID());
					freightAmt.setUY_TR_Crt_ID(this.getUY_TR_Crt_ID());
					freightAmt.setWeight(this.getWeight());
					freightAmt.setWeight2(this.getWeight2());
					freightAmt.setVolume(this.getVolume());
					freightAmt.setQtyPackage(this.getQtyPackage());
					freightAmt.setC_Currency_ID(this.getC_Currency_ID());
					freightAmt.setC_Currency2_ID(inv.getC_Currency_ID());
					freightAmt.setProductAmt(this.getProductAmt());
					freightAmt.setUY_TR_LoadMonitorLine_ID(this.getUY_TR_LoadMonitorLine_ID());
					freightAmt.setAmount(this.getAmount());
					freightAmt.saveEx();

				}
			}
			
		}
				
		return true;
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 10/01/2014. ISSUE #1625
	 * Método que obtiene y devuelve el peso en kg disponible como limite para el ingreso en esta linea
	 * @param trip 
	 *  
	 * **/
	/*private BigDecimal getWeightAvailable(MTRTrip trip){
				
		BigDecimal retorno = new BigDecimal(0);	
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "select coalesce(sum(otline.weight),0) as total" +
			      " from uy_tr_trip exp" +
                  " inner join uy_tr_transorderline otline on exp.uy_tr_trip_id = otline.uy_tr_trip_id" + 
                  " inner join uY_tr_transorder ot on otline.uy_tr_transorder_id = ot.uy_tr_transorder_id" + 
                  " where ot.docstatus <> 'DR' and otline.uy_tr_trip_id = " + this.getUY_TR_Trip_ID() +
                  " and otline.uy_tr_transorderline_id <> " + this.getUY_TR_TransOrderLine_ID();
			
			BigDecimal totalWeight = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			retorno = (trip.getWeight().subtract(totalWeight)).setScale(2, RoundingMode.HALF_UP);
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return retorno;
	}*/

	@Override
	protected boolean afterDelete(boolean success) {
		
		//OpenUp. Guillermo Brust. 08/01/2014. ISSUE #1625
		//Se elimina el registro de la trazabilidad en la pestania de orden de transporte en la ventana de gestion de expediente
		if(this.getUY_TR_TransOrder_ID() > 0){
			
			MTRTripOrder tripOrder = MTRTripOrder.forTripIDAndOrderID(this.getCtx(), this.getUY_TR_Trip_ID(), this.getUY_TR_TransOrder_ID(), this.get_TrxName());
			if(tripOrder!=null) tripOrder.deleteEx(true);			
		}
		//Fin OpenUp.	
		
		
		//Se agrega el importe del expediente asociado aca al total de esta orden de transporte
		MTRTransOrder order = (MTRTransOrder) this.getUY_TR_TransOrder();
		BigDecimal totalAmt = order.getTotalAmt();
		BigDecimal newAmt = totalAmt.subtract(this.getProductAmt());
		
		order.setTotalAmt(newAmt);
		order.saveEx();
		
		MTRTripQty tripQty = MTRTripQty.forOTLine(getCtx(),this.get_IDOld(),get_TrxName()); //obtengo registro en tabla de distribucion del expediente para esta linea de OT
		if(tripQty!=null && tripQty.get_ID()>0) DB.executeUpdateEx("delete from uy_tr_tripqty where uy_tr_tripqty_id = " + tripQty.get_ID(), get_TrxName()); //elimino el registro
		
		MTRInvoiceFreightAmt freightAmt = MTRInvoiceFreightAmt.forOTLine(getCtx(),this.get_IDOld(),get_TrxName()); //obtengo registro en tabla de distribucion de la proforma para esta linea de OT
		if(freightAmt!=null && freightAmt.get_ID()>0) DB.executeUpdateEx("delete from uy_tr_invoicefreightamt where uy_tr_invoicefreightamt_id = " + freightAmt.get_ID(), get_TrxName()); //elimino el registro
				
		return true;
	}

	@Override
	protected boolean beforeDelete() {		

		MTRMicLine mLine = MTRMicLine.forOrderLine(getCtx(), this.get_ID(), get_TrxName()); //obtengo linea de CRT en MIC
		
		//si se obtuvo una linea de CRT, entonces existe un MIC para refrescar
		if(mLine != null && mLine.get_ID() > 0) this.refreshMic(true,false,mLine);
		
		return true;
	}

	
	/**
	 * OpenUp. Nicolas Sarlabos. 26/11/2014. #3093
	 * Método que actualiza el MIC, realizando el alta, baja o modificacion de CRT.
	 *   
	 * **/
	private void refreshMic(boolean IsDelete, boolean newRecord, MTRMicLine mLine) {

		try{
			
			MTRMic hdr = null;
			MTRMicCont cont = null;
			
			//instancio cabezal de MIC	
			if(mLine!=null && mLine.get_ID()>0){
				
				hdr = (MTRMic)mLine.getUY_TR_Mic(); 
				
			} else hdr = MTRMic.forOT(getCtx(), this.getUY_TR_TransOrder_ID(), get_TrxName());
			
			//si es una baja
			if(IsDelete){

				if(mLine.getUY_TR_Crt_ID()==hdr.getUY_TR_Crt_ID()){ //si es el CRT del cabezal		
					
					//si esta enviado a aduana
					if(hdr.getCrtStatus1().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.VINCULADO.toString()) || hdr.getCrtStatus1().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.ENMODIFICACION.toString())){ 

						//guardo datos para posterior envio de la baja a aduana en tabla auxiliar
						this.createMicTask(hdr.get_ID(),mLine.getUY_TR_Crt_ID(),hdr.getCrtImgNum1(),hdr.getCrtImgStatus1(),hdr.getCrtLineStatus1(),hdr.getSecNoCrt());

					} 	

					//vacio los campos del CRT		
					this.cleanCrtHdr(hdr);
					mLine.deleteEx(true, get_TrxName()); //borro linea de CRT en MIC

					//si existen continuaciones, debo mover el CRT de la ultima continuacion hacia el cabezal
					cont = hdr.getMicCont(); //obtengo, si la hay, la ultima continuacion del MIC

					if(cont != null && cont.get_ID() > 0){

						if(cont.getUY_TR_Crt_ID_1() > 0){ //muevo el 2o CRT de la continuacion

							//seteo los campos 
							this.moveCrtToHdr(cont,hdr,2);		
							//borro los campos del CRT 2 movido en la continuacion
							this.cleanCrtCont(cont);

						} else { //muevo el 1er CRT de la continuacion

							//seteo los campos 
							this.moveCrtToHdr(cont,hdr,1);									
							//borro la continuacion
							cont.deleteEx(true, get_TrxName());			
						}

					}
					
				} else { //si no es el CRT del cabezal
					
					cont = hdr.getMicCont(); //obtengo, si la hay, la ultima continuacion del MIC
					
					MTRMicCont actual = MTRMicCont.forMicLine(mLine); //obtengo la continuacion actual
					
					//si la continuacion actual es la ultima del MIC
					if(cont.get_ID()==actual.get_ID()){
						
						if(mLine.getUY_TR_Crt_ID()==actual.getUY_TR_Crt_ID_1()){ //si es el CRT 2 de la continuacion
							
							//si esta enviado a aduana
							if(actual.getCrtStatus2().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.VINCULADO.toString()) || actual.getCrtStatus2().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.ENMODIFICACION.toString())){ 

								//guardo datos para posterior envio de la baja a aduana en tabla auxiliar
								this.createMicTask(hdr.get_ID(),mLine.getUY_TR_Crt_ID(),actual.getCrtImgNum2(),actual.getCrtImgStatus2(),actual.getCrtLineStatus2(),actual.getSecNoCrt2());

							} 
							
							this.cleanCrtCont(actual);
							mLine.deleteEx(true, get_TrxName()); //borro linea de CRT en MIC							
							
						} else if (mLine.getUY_TR_Crt_ID()==actual.getUY_TR_Crt_ID()){ //si es el CRT 1 de la continuacion
							
							//si hay CRT 2, debo mover el CRT 2 al CRT 1 en la continuacion actual
							//si esta enviado a aduana
							if(actual.getCrtStatus1().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.VINCULADO.toString()) || actual.getCrtStatus1().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.ENMODIFICACION.toString())){ 
							
								//guardo datos para posterior envio de la baja a aduana en tabla auxiliar
								this.createMicTask(hdr.get_ID(),mLine.getUY_TR_Crt_ID(),actual.getCrtImgNum1(),actual.getCrtImgStatus1(),actual.getCrtLineStatus1(),actual.getSecNoCrt());
							}
							
							//si hay CRT 2
							if(actual.getUY_TR_Crt_ID_1()>0){
								
								//seteo los campos desde el CRT 2 hacia el CRT 1
								this.moveCrtCont(cont,2,actual,1);									
								//borro los campos del CRT 2
								this.cleanCrtCont(actual);
							
								//si no hay CRT 2	
							} else 	actual.deleteEx(true, get_TrxName()); //borro la continuacion						
							
							mLine.deleteEx(true, get_TrxName()); //borro linea de CRT en MIC								
						}						
						
					} else { //si la continuacion actual no es la ultima del MIC
						
						//debo mover el ultimo CRT del MIC a la continuacion actual, luego de eliminar el CRT
						if(mLine.getUY_TR_Crt_ID()==actual.getUY_TR_Crt_ID_1()){ //si es el CRT 2 de la continuacion
							
							//si esta enviado a aduana
							if(actual.getCrtStatus2().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.VINCULADO.toString()) || actual.getCrtStatus2().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.ENMODIFICACION.toString())){ 

								//guardo datos para posterior envio de la baja a aduana en tabla auxiliar
								this.createMicTask(hdr.get_ID(),mLine.getUY_TR_Crt_ID(),actual.getCrtImgNum2(),actual.getCrtImgStatus2(),actual.getCrtLineStatus2(),actual.getSecNoCrt2());

							} 
							
							//seteo los campos desde el CRT 2 de la ultima cont. hacia el CRT 2 de la cont. actual
							if(cont.getUY_TR_Crt_ID_1()>0){
								
								this.moveCrtCont(cont,2,actual,2);													
								//borro los campos del CRT 2 de la ultima continuacion
								this.cleanCrtCont(cont);
								
							//seteo los campos desde el CRT 1 de la ultima cont. hacia el CRT 2 de la cont. actual	
							} else {
								
								this.moveCrtCont(cont,1,actual,2);																
								cont.deleteEx(true, get_TrxName());	//borro la ultima continuacion										
							}						
	
							mLine.deleteEx(true, get_TrxName()); //borro linea de CRT en MIC			
							
						} else if (mLine.getUY_TR_Crt_ID()==actual.getUY_TR_Crt_ID()){ //si es el CRT 1 de la continuacion
							
							//si esta enviado a aduana
							if(actual.getCrtStatus1().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.VINCULADO.toString()) || actual.getCrtStatus1().equalsIgnoreCase(ConsultaRespuestaMic.CrtStatus.ENMODIFICACION.toString())){ 
								
								//guardo datos para posterior envio de la baja a aduana en tabla auxiliar
								this.createMicTask(hdr.get_ID(),mLine.getUY_TR_Crt_ID(),actual.getCrtImgNum1(),actual.getCrtImgStatus1(),actual.getCrtLineStatus1(),actual.getSecNoCrt());
							}
							
							//seteo los campos desde el CRT 2 de la ultima cont. hacia el CRT 1 de la cont. actual
							if(cont.getUY_TR_Crt_ID_1()>0){
								
								this.moveCrtCont(cont,2,actual,1);							
								//borro los campos del CRT 2 de la ultima continuacion
								this.cleanCrtCont(cont);
								
							//seteo los campos desde el CRT 1 de la ultima cont. hacia el CRT 1 de la cont. actual	
							} else {
								
								this.moveCrtCont(cont,1,actual,1);												
								cont.deleteEx(true, get_TrxName());	//borro la ultima continuacion									
							}								
							
							mLine.deleteEx(true, get_TrxName()); //borro linea de CRT en MIC									
						}							
					}
				}

			//si es un alta o modificacion	
			} else{
				
				//nuevo registro
				if(newRecord){
					
					//verifico si ya existe una linea de CRT en el MIC, si existe una linea para este CRT, actualizo sus datos
					//si no existe creo una nueva linea
					MTRMicLine line = MTRMicLine.forCRT(getCtx(), this.getUY_TR_Crt_ID(), hdr.get_ID(), get_TrxName());
					
					if(line!=null && line.get_ID()>0){
						
						if(line.getUY_TR_Crt_ID()==hdr.getUY_TR_Crt_ID()){ //si es el CRT del cabezal

							hdr.setpesoBruto(this.getWeight());
							hdr.setpesoNeto(this.getWeight2());
							hdr.setQtyPackage(this.getQtyPackage());
							hdr.setImporte(this.getProductAmt());
							hdr.setAmount(this.getAmount());
							hdr.saveEx(get_TrxName());

						} else {

							if (mLine != null){
								MTRMicCont actual = MTRMicCont.forMicLine(mLine); //obtengo la continuacion actual

								if(line.getUY_TR_Crt_ID()==actual.getUY_TR_Crt_ID()){ //si es el primer CRT de la continuacion

									actual.setpesoBruto(this.getWeight());
									actual.setpesoNeto(this.getWeight2());
									actual.setQtyPackage(this.getQtyPackage());
									actual.setImporte(this.getProductAmt());
									actual.setAmount(this.getAmount());
									actual.saveEx(get_TrxName());							

								} else if (line.getUY_TR_Crt_ID()==actual.getUY_TR_Crt_ID_1()){ //si es el segundo CRT de la continuacion

									actual.setpesoBruto2(this.getWeight());
									actual.setpesoNeto2(this.getWeight2());
									actual.setQtyPackage2(this.getQtyPackage());
									actual.setImporte2(this.getProductAmt());
									actual.setAmount2(this.getAmount());
									actual.saveEx(get_TrxName());										
								}					
							}

						}						
						
					} else {
						
						//creo nueva linea de CRT en MIC
						MTRTransOrder order = (MTRTransOrder)this.getUY_TR_TransOrder();
						MTRTrip trip = (MTRTrip)this.getUY_TR_Trip();
						
						MTRMicLine micLine = new MTRMicLine(getCtx(),0,get_TrxName());
						micLine.setUY_TR_Mic_ID(hdr.get_ID());
						micLine.setUY_TR_Trip_ID(trip.get_ID());
						micLine.setReferenceNo(trip.getReferenceNo());
						micLine.setUY_TR_Crt_ID(this.getUY_TR_Crt_ID());
						micLine.setUY_TR_TransOrderLine_ID(this.get_ID());
						
						if(trip.getTripType().equalsIgnoreCase("IMPORTACION")){ //se abre por cliente DESTINO

							micLine.setC_BPartner_ID(trip.getC_BPartner_ID_From());

						} else if(trip.getTripType().equalsIgnoreCase("EXPORTACION")){ //se abre por cliente ORIGEN

							micLine.setC_BPartner_ID(trip.getC_BPartner_ID_To());							
						}

						micLine.saveEx();
						
						if(hdr.getUY_TR_Crt_ID()<=0){
							
							hdr.loadCrtHdr(order,this);						
							
						} else hdr.loadCrtCont(order, this);							
					}					
				
				} else { //modificacion	
					
					if(mLine!=null && mLine.get_ID()>0){
						
						if(mLine.getUY_TR_Crt_ID()==hdr.getUY_TR_Crt_ID()){ //si es el CRT del cabezal

							hdr.setpesoBruto(this.getWeight());
							hdr.setpesoNeto(this.getWeight2());
							hdr.setQtyPackage(this.getQtyPackage());
							hdr.setImporte(this.getProductAmt());
							hdr.setAmount(this.getAmount());
							hdr.saveEx(get_TrxName());

						} else {

							MTRMicCont actual = MTRMicCont.forMicLine(mLine); //obtengo la continuacion actual

							if(mLine.getUY_TR_Crt_ID()==actual.getUY_TR_Crt_ID()){ //si es el primer CRT de la continuacion

								actual.setpesoBruto(this.getWeight());
								actual.setpesoNeto(this.getWeight2());
								actual.setQtyPackage(this.getQtyPackage());
								actual.setImporte(this.getProductAmt());
								actual.setAmount(this.getAmount());
								actual.saveEx(get_TrxName());							

							} else if (mLine.getUY_TR_Crt_ID()==actual.getUY_TR_Crt_ID_1()){ //si es el segundo CRT de la continuacion

								actual.setpesoBruto2(this.getWeight());
								actual.setpesoNeto2(this.getWeight2());
								actual.setQtyPackage2(this.getQtyPackage());
								actual.setImporte2(this.getProductAmt());
								actual.setAmount2(this.getAmount());
								actual.saveEx(get_TrxName());										
							}					

						}			
					}

				}
				
			}

			this.updateTotals(hdr); //actualizo totales de pesos y bultos en continuaciones	

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 

	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 02/12/2014. #3093
	 * Método que mueve los campos de CRT de una continuacion hacia el CRT de otra continuacion.
	 *   
	 * **/
	private void moveCrtCont(MTRMicCont contFrom, int crtFrom, MTRMicCont contTo, int crtTo) {
		
		if (crtFrom == 2 && crtTo == 1){
			
			contTo.setUY_TR_Crt_ID(contFrom.getUY_TR_Crt_ID_1());
			contTo.setImporte(contFrom.getImporte2());
			contTo.setC_Currency_ID(contFrom.getC_Currency2_ID());
			contTo.setSeguro(contFrom.getSeguro2());
			contTo.setQtyPackage(contFrom.getQtyPackage2());
			contTo.setpesoBruto(contFrom.getpesoBruto2());
			contTo.setUY_TR_Border_ID(contFrom.getUY_TR_Border_ID_1());
			contTo.setLocationComment(contFrom.getLocationComment2());
			contTo.setAmount(contFrom.getAmount2());
			contTo.setUY_TR_PackageType_ID(contFrom.getUY_TR_PackageType_ID_1());
			contTo.setpesoNeto(contFrom.getpesoNeto2());
			contTo.setRemitente(contFrom.getRemitente2());
			contTo.setDestinatario(contFrom.getDestinatario2());
			contTo.setConsignatario(contFrom.getConsignatario2());
			contTo.setObservaciones2(contFrom.getObservaciones4());
			contTo.setprecinto(contFrom.getPrecinto2());
			contTo.setDescription(contFrom.getdescripcion());
			//contTo.setObservaciones3(contFrom.getObservaciones3());
			contTo.setCrtImgNum1(contFrom.getCrtImgNum2());
			contTo.setCrtImgStatus1(contFrom.getCrtImgStatus2());
			contTo.setCrtLineStatus1(contFrom.getCrtLineStatus2());
			contTo.setCrtStatus1(contFrom.getCrtStatus2());		
			contTo.setSecNoCrt(contFrom.getSecNoCrt2());			
		
		} else if (crtFrom == 2 && crtTo == 2){
			
			contTo.setUY_TR_Crt_ID_1(contFrom.getUY_TR_Crt_ID_1());
			contTo.setImporte2(contFrom.getImporte2());
			contTo.setC_Currency2_ID(contFrom.getC_Currency2_ID());
			contTo.setSeguro2(contFrom.getSeguro2());
			contTo.setQtyPackage2(contFrom.getQtyPackage2());
			contTo.setpesoBruto2(contFrom.getpesoBruto2());
			contTo.setUY_TR_Border_ID_1(contFrom.getUY_TR_Border_ID_1());
			contTo.setLocationComment2(contFrom.getLocationComment2());
			contTo.setAmount2(contFrom.getAmount2());
			contTo.setUY_TR_PackageType_ID_1(contFrom.getUY_TR_PackageType_ID_1());
			contTo.setpesoNeto2(contFrom.getpesoNeto2());
			contTo.setRemitente2(contFrom.getRemitente2());
			contTo.setDestinatario2(contFrom.getDestinatario2());
			contTo.setConsignatario2(contFrom.getConsignatario2());
			contTo.setObservaciones4(contFrom.getObservaciones4());
			contTo.setPrecinto2(contFrom.getPrecinto2());
			contTo.setdescripcion(contFrom.getdescripcion());
			contTo.setObservaciones3(contFrom.getObservaciones3());
			contTo.setCrtImgNum2(contFrom.getCrtImgNum2());
			contTo.setCrtImgStatus2(contFrom.getCrtImgStatus2());
			contTo.setCrtLineStatus2(contFrom.getCrtLineStatus2());
			contTo.setCrtStatus2(contFrom.getCrtStatus2());		
			contTo.setSecNoCrt2(contFrom.getSecNoCrt2());		
			
		} else if (crtFrom == 1 && crtTo == 2){
			
			contTo.setUY_TR_Crt_ID_1(contFrom.getUY_TR_Crt_ID());
			contTo.setImporte2(contFrom.getImporte());
			contTo.setC_Currency2_ID(contFrom.getC_Currency_ID());
			contTo.setSeguro2(contFrom.getSeguro());
			contTo.setQtyPackage2(contFrom.getQtyPackage());
			contTo.setpesoBruto2(contFrom.getpesoBruto());
			contTo.setUY_TR_Border_ID_1(contFrom.getUY_TR_Border_ID());
			contTo.setLocationComment2(contFrom.getLocationComment());
			contTo.setAmount2(contFrom.getAmount());
			contTo.setUY_TR_PackageType_ID_1(contFrom.getUY_TR_PackageType_ID());
			contTo.setpesoNeto2(contFrom.getpesoNeto());
			contTo.setRemitente2(contFrom.getRemitente());
			contTo.setDestinatario2(contFrom.getDestinatario());
			contTo.setConsignatario2(contFrom.getConsignatario());
			contTo.setObservaciones4(contFrom.getObservaciones2());
			contTo.setPrecinto2(contFrom.getprecinto());
			contTo.setdescripcion(contFrom.getDescription());
			//contTo.setObservaciones3(cont.getObservaciones3());
			contTo.setCrtImgNum2(contFrom.getCrtImgNum1());
			contTo.setCrtImgStatus2(contFrom.getCrtImgStatus1());
			contTo.setCrtLineStatus2(contFrom.getCrtLineStatus1());
			contTo.setCrtStatus2(contFrom.getCrtStatus1());		
			contTo.setSecNoCrt2(contFrom.getSecNoCrt());		
			
		} else if (crtFrom == 1 && crtTo == 1){
			
			contTo.setUY_TR_Crt_ID(contFrom.getUY_TR_Crt_ID());
			contTo.setImporte(contFrom.getImporte());
			contTo.setC_Currency_ID(contFrom.getC_Currency_ID());
			contTo.setSeguro(contFrom.getSeguro());
			contTo.setQtyPackage(contFrom.getQtyPackage());
			contTo.setpesoBruto(contFrom.getpesoBruto());
			contTo.setUY_TR_Border_ID(contFrom.getUY_TR_Border_ID());
			contTo.setLocationComment(contFrom.getLocationComment());
			contTo.setAmount(contFrom.getAmount());
			contTo.setUY_TR_PackageType_ID(contFrom.getUY_TR_PackageType_ID());
			contTo.setpesoNeto(contFrom.getpesoNeto());
			contTo.setRemitente(contFrom.getRemitente());
			contTo.setDestinatario(contFrom.getDestinatario());
			contTo.setConsignatario(contFrom.getConsignatario());
			contTo.setObservaciones2(contFrom.getObservaciones2());
			contTo.setprecinto(contFrom.getprecinto());
			contTo.setDescription(contFrom.getDescription());
			//contTo.setObservaciones3(cont.getObservaciones3());
			contTo.setCrtImgNum1(contFrom.getCrtImgNum1());
			contTo.setCrtImgStatus1(contFrom.getCrtImgStatus1());
			contTo.setCrtLineStatus1(contFrom.getCrtLineStatus1());
			contTo.setCrtStatus1(contFrom.getCrtStatus1());		
			contTo.setSecNoCrt(contFrom.getSecNoCrt());		
			
		}

		contTo.saveEx(get_TrxName());
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 02/12/2014. #3093
	 * Método que mueve los campos de CRT de una continuacion hacia el CRT del cabezal.
	 *   
	 * **/
	private void moveCrtToHdr(MTRMicCont cont, MTRMic hdr, int nroCRT) {
		
		if(nroCRT == 1){
			
			hdr.setUY_TR_Crt_ID(cont.getUY_TR_Crt_ID());
			hdr.setC_Currency_ID(cont.getC_Currency_ID());
			hdr.setImporte(cont.getImporte());
			hdr.setSeguro(cont.getSeguro());
			hdr.setQtyPackage(cont.getQtyPackage());
			hdr.setpesoBruto(cont.getpesoBruto());
			hdr.setUY_TR_Border_ID(cont.getUY_TR_Border_ID());
			hdr.setLocationComment(cont.getLocationComment());
			hdr.setAmount(cont.getAmount());
			hdr.setUY_TR_PackageType_ID(cont.getUY_TR_PackageType_ID());
			hdr.setpesoNeto(cont.getpesoNeto());
			hdr.setRemitente(cont.getRemitente());
			hdr.setDestinatario(cont.getDestinatario());
			hdr.setConsignatario(cont.getConsignatario());
			hdr.setObservaciones2(cont.getObservaciones2());
			hdr.setPrecinto2(cont.getprecinto());
			hdr.setDescription(cont.getDescription());
			hdr.setObservaciones3(cont.getObservaciones3());
			hdr.setCrtImgNum1(cont.getCrtImgNum1());
			hdr.setCrtImgStatus1(cont.getCrtImgStatus1());
			hdr.setCrtLineStatus1(cont.getCrtLineStatus1());
			hdr.setCrtStatus1(cont.getCrtStatus1());
			hdr.setSecNoCrt(cont.getSecNoCrt());
			
		} else if(nroCRT == 2){
			
			hdr.setUY_TR_Crt_ID(cont.getUY_TR_Crt_ID_1());
			hdr.setC_Currency_ID(cont.getC_Currency2_ID());
			hdr.setImporte(cont.getImporte2());
			hdr.setSeguro(cont.getSeguro2());
			hdr.setQtyPackage(cont.getQtyPackage2());
			hdr.setpesoBruto(cont.getpesoBruto2());
			hdr.setUY_TR_Border_ID(cont.getUY_TR_Border_ID_1());
			hdr.setLocationComment(cont.getLocationComment2());
			hdr.setAmount(cont.getAmount2());
			hdr.setUY_TR_PackageType_ID(cont.getUY_TR_PackageType_ID_1());
			hdr.setpesoNeto(cont.getpesoNeto2());
			hdr.setRemitente(cont.getRemitente2());
			hdr.setDestinatario(cont.getDestinatario2());
			hdr.setConsignatario(cont.getConsignatario2());
			hdr.setObservaciones2(cont.getObservaciones4());
			hdr.setPrecinto2(cont.getPrecinto2());
			hdr.setDescription(cont.getdescripcion());
			hdr.setObservaciones3(cont.getObservaciones3());
			hdr.setCrtImgNum1(cont.getCrtImgNum2());
			hdr.setCrtImgStatus1(cont.getCrtImgStatus2());
			hdr.setCrtLineStatus1(cont.getCrtLineStatus2());
			hdr.setCrtStatus1(cont.getCrtStatus2());		
			hdr.setSecNoCrt(cont.getSecNoCrt2());	
			
		}		

		hdr.saveEx(get_TrxName());		
		
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 28/11/2014. #3093
	 * Método que crea inserta un CRT para baja en tabla auxiliar.
	 *   
	 * **/
	private void createMicTask(int micID, int crtID, String imgNum, String imgStatus, String lineStatus, String secNoCrt) {
		
		MTRMicTask mTask = new MTRMicTask(getCtx(),0,get_TrxName());
		
		mTask.setUY_TR_Mic_ID(micID);
		mTask.setUY_TR_Crt_ID(crtID);
		mTask.setCrtImgNum(imgNum);
		mTask.setCrtImgStatus(imgStatus);
		mTask.setCrtLineStatus(lineStatus);
		mTask.setCrtStatus(ConsultaRespuestaMic.CrtStatus.ENBAJA.toString());
		mTask.setSecNoCrt(secNoCrt);
		mTask.saveEx(get_TrxName());
		
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 28/11/2014. #3093
	 * Método que borra los campos del CRT del cabezal del MIC.
	 *   
	 * **/
	private void cleanCrtHdr(MTRMic hdr) {

		if(hdr!=null && hdr.get_ID()>0){

			hdr.setImporte(Env.ZERO);
			hdr.setSeguro(Env.ZERO);
			hdr.setQtyPackage(Env.ZERO);
			hdr.setpesoBruto(Env.ZERO);
			hdr.setLocationComment(null);
			hdr.setAmount(Env.ZERO);
			hdr.setpesoNeto(Env.ZERO);
			hdr.setRemitente(null);
			hdr.setDestinatario(null);
			hdr.setConsignatario(null);
			hdr.setObservaciones2(null);
			hdr.setPrecinto2(null);
			hdr.setDescription(null);
			hdr.setObservaciones3(null);
			hdr.setCrtImgNum1("");
			hdr.setCrtImgStatus1(ConsultaRespuestaMic.LineImgStatus.SINCARGAR.toString());
			hdr.setCrtLineStatus1(ConsultaRespuestaMic.LineImgStatus.SINCARGAR.toString());
			hdr.setCrtStatus1(ConsultaRespuestaMic.CrtStatus.ENALTA.toString());		
			hdr.saveEx(get_TrxName());	
			
			DB.executeUpdateEx("update uy_tr_mic set uy_tr_crt_id = null, uy_tr_border_id = null, uy_tr_packagetype_id = null where uy_tr_mic_id = " + hdr.get_ID(), get_TrxName());

		}

	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 28/11/2014. #3093
	 * Método que borra los campos del CRT 2 de la continuacion del MIC.
	 *   
	 * **/
	private void cleanCrtCont(MTRMicCont cont) {

		if(cont!=null && cont.get_ID()>0){
			
			cont.setImporte2(Env.ZERO);
			cont.setSeguro2(Env.ZERO);
			cont.setQtyPackage2(Env.ZERO);
			cont.setpesoBruto2(Env.ZERO);
			cont.setLocationComment2(null);
			cont.setAmount2(Env.ZERO);
			cont.setpesoNeto2(Env.ZERO);
			cont.setRemitente2(null);
			cont.setDestinatario2(null);
			cont.setConsignatario2(null);
			cont.setObservaciones4(null);
			cont.setPrecinto2(null);
			cont.setdescripcion(null);
			cont.setObservaciones3(null);
			cont.setCrtImgNum2("");
			cont.setCrtImgStatus2(ConsultaRespuestaMic.LineImgStatus.SINCARGAR.toString());
			cont.setCrtLineStatus2(ConsultaRespuestaMic.LineImgStatus.SINCARGAR.toString());
			cont.setCrtStatus2(ConsultaRespuestaMic.CrtStatus.ENALTA.toString());
			cont.saveEx(get_TrxName());
			
			DB.executeUpdateEx("update uy_tr_miccont set uy_tr_crt_id_1 = null, uy_tr_border_id_1 = null, uy_tr_packagetype_id_1 = null where uy_tr_miccont_id = " + cont.get_ID(), get_TrxName());
					

		}

	}

	/**
	 * OpenUp. Nicolas Sarlabos. 28/11/2014. #3093
	 * Método que actualiza los totalizadores y numeros de hoja en las continuaciones de MIC.
	 *   
	 * **/
	private void updateTotals(MTRMic mic) {
								
		List<MTRMicCont> lines = mic.getCont(); //obtengo lista de continuaciones de MIC
		MTRMicCont anterior = null;
		
		//recorro continuaciones y voy actualizando los totales de pesos y bultos
		for (MTRMicCont cont: lines){
			
			//si es la hoja 2, primer continuacion
			if(anterior == null){

				cont.setTotalAntBultos(mic.getQtyPackage()); //seteo bultos hoja 1
				cont.setTotalAntPeso(mic.getpesoBruto()); //seteo peso bruto hoja 1

				cont.setAcumuladoBultos(cont.getSubtotalBultos().add(cont.getTotalAntBultos())); //seteo acumulado bultos
				cont.setAcumuladoPeso(cont.getSubtotalPeso().add(cont.getTotalAntPeso())); //seteo acumulado peso
				
				cont.setsheet(2);
				cont.saveEx();
				
				anterior = cont;
				
			} else { //si es la hoja 3 o mayor, hay una anterior
				
				cont.setTotalAntBultos(anterior.getAcumuladoBultos()); //seteo bultos acumulado hoja anterior
				cont.setTotalAntPeso(anterior.getAcumuladoPeso()); //seteo peso acumulado hoja anterior

				cont.setAcumuladoBultos(cont.getSubtotalBultos().add(cont.getTotalAntBultos())); //seteo acumulado bultos
				cont.setAcumuladoPeso(cont.getSubtotalPeso().add(cont.getTotalAntPeso())); //seteo acumulado peso	
				
				cont.setsheet(anterior.getsheet()+1);
				cont.saveEx();
				
				anterior = cont;			
				
			}			
		}		
	}


	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTransOrderLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {		
					
			if ((!newRecord) && is_ValueChanged(COLUMNNAME_IsReleased)){
				return true;
			}

			MTRCrt crt = (MTRCrt)this.getUY_TR_Crt();

			if(newRecord || is_ValueChanged("uy_tr_trip_id")) this.setC_Currency_ID(crt.getC_Currency_ID());

			// Valido que toda cantidad y monto no supere los valores del crt.
			if (this.getQtyPackage() != null){
				if (crt.getQtyPackage() == null){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene cantidad de bultos.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene cantidad de bultos.");
				}
				if (this.getQtyPackage().compareTo(crt.getQtyPackage()) > 0){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"La cantidad de bultos de la linea supera la cantidad de bultos del CRT.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"La cantidad de bultos de la linea supera la cantidad de bultos del CRT.");
				}
			}

			if (this.getWeight() != null){
				if (crt.getWeight() == null){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene valor para Peso Bruto.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene valor para Peso Bruto.");
				}
				if (this.getWeight().compareTo(crt.getWeight()) > 0){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El Peso Bruto de la linea supera el valor actual del CRT.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El Peso Bruto de la linea supera el valor actual del CRT.");
				}
			}

			if (this.getWeight2() != null){
				if (crt.getWeight2() == null){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene valor para Peso Neto.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene valor para Peso Neto.");
				}
				if (this.getWeight2().compareTo(crt.getWeight2()) > 0){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El Peso Neto de la linea supera el valor actual del CRT.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El Peso Neto de la linea supera el valor actual del CRT.");
				}
			}

			if (this.getVolume() != null){
				if (crt.getVolume() == null){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene valor para Volumen.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene valor para Volumen.");
				}
				if (this.getVolume().compareTo(crt.getVolume()) > 0){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El valor de Volumen de la linea supera el valor actual del CRT.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El valor de Volumen de la linea supera el valor actual del CRT.");
				}
			}

			if (this.getProductAmt() != null){
				if (crt.getTotalAmt() == null){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene valor de mercaderia.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El CRT no tiene valor de mercaderia.");
				}
				if (this.getProductAmt().compareTo(crt.getTotalAmt()) > 0){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El valor de Mercaderia de la linea supera el valor actual del CRT.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El valor de Mercaderia de la linea supera el valor actual del CRT.");
				}
			}

			if (this.getAmount() != null){

				BigDecimal amt1 = crt.getamt1();
				BigDecimal amt2 = crt.getamt2();

				if (amt1 == null) amt1 = Env.ZERO;
				if (amt2 == null) amt2 = Env.ZERO;

				BigDecimal amtTotal = amt1.add(amt2);

				if (this.getAmount().compareTo(amtTotal) > 0){
					log.saveError(null, "No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El valor del Flete de la linea supera el valor actual del CRT.");
					throw new AdempiereException("No es posible guardar linea de transporte para el CRT: " + crt.getNumero() + "\n" +
							"El valor del Flete de la linea supera el valor actual del CRT.");
				}
			}

			/*

		//Verifico cantidad de bultos a ingresar
		if(newRecord || is_ValueChanged("QtyPackage")){	

			if(this.getQtyPackage().compareTo(Env.ZERO)<=0) throw new AdempiereException("La cantidad de bultos debe ser mayor a cero");

			BigDecimal qtyPackageTotal = crt.getQtyPackage();
			MInvoice proforma = null;
			if (crt.getC_Invoice_ID() > 0){
				proforma = (MInvoice)crt.getC_Invoice();
				qtyPackageTotal = proforma.getuy_cantbultos();
			}

			String sql = "SELECT COALESCE(sum(otline.qtypackage), 0)" +
                         " FROM uy_tr_transorderline otline" +
                         " WHERE otline.uy_tr_crt_id = " + crt.get_ID() +
                         " AND ((otline.endtrackstatus = 'N' AND otline.isdelivered = 'N') or (otline.endtrackstatus = 'Y' AND otline.isdelivered = 'Y'))" +
                         " AND  otline.uy_tr_transorderline_id <> " + this.get_ID();

			BigDecimal qtyUsed = DB.getSQLValueBDEx(null, sql);

			if(qtyUsed==null) qtyUsed = Env.ZERO;

			//BigDecimal qtyDisponible = qtyPackageTotal.subtract(qtyUsed); //obtengo bultos disponibles

			//if(this.getQtyPackage().compareTo(qtyDisponible)>0) throw new AdempiereException("Cantidad de bultos ingresada supera los " + qtyDisponible.setScale(0, RoundingMode.HALF_UP) + " bultos disponibles para este CRT");			

		}

			 */	
		
		return true;	
	}
	
	
	/***
	 * 
	 * 
	 * 
	 * */
	private BigDecimal getMontoTotalLineas(){
		
		BigDecimal retorno = new BigDecimal(0);	
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "select coalesce(sum(coalesce(productamt,0)),0) as total " +
				  " from uy_tr_transorderline" +
				  " where uy_tr_transorder_id = " + this.getUY_TR_TransOrder_ID();
						  
			
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				retorno = rs.getBigDecimal("total");			
			}			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return retorno;
	}
	
	

	/**
	 * OpenUp. Guillermo Brust. 13/12/2013. ISSUE #1625
	 * Se retorna un modelo de la MTRTransOrderLine tal que tenga el expediente y la orden de transporte pasado por parametro.
	 * 
	 * **/
	public static MTRTransOrderLine forTransOrderIDAndTripID(Properties ctx, String trxName, int transOrderID, int tripID){
		
		String whereClause = X_UY_TR_TransOrderLine.COLUMNNAME_UY_TR_TransOrder_ID + " = " + transOrderID + 
					" AND " + X_UY_TR_TransOrderLine.COLUMNNAME_UY_TR_Trip_ID + " = " + tripID;
		
		return new Query(ctx, I_UY_TR_TransOrderLine.Table_Name, whereClause, trxName).first();
				
	}
	
	/**
	 * OpenUp. Guillermo Brust. 13/12/2013. ISSUE #1625
	 * Se retorna un modelo de la MTRTransOrderLine tal que tenga el expediente y la orden de transporte pasado por parametro.
	 * 
	 * **/
	public static MTRTransOrderLine forTransOrderAndCrt(Properties ctx, String trxName, int transOrderID, int crtID){
		
		String whereClause = X_UY_TR_TransOrderLine.COLUMNNAME_UY_TR_TransOrder_ID + " = " + transOrderID + 
					" AND " + X_UY_TR_TransOrderLine.COLUMNNAME_UY_TR_Crt_ID + " = " + crtID;
		
		return new Query(ctx, I_UY_TR_TransOrderLine.Table_Name, whereClause, trxName).first();
				
	}
	
	/**
	 * OpenUp. Guillermo Brust. 13/12/2013. ISSUE #1625
	 * Se retorna una lista de este modelo de la MTRTransOrderLine tal que tenga el expediente pasado por parametro, y la orden de trasporte asociada este en estado aplicado.
	 * 
	 * **/
	public static List<MTRTransOrder> getTransOrderForTripID(Properties ctx, String trxName, int tripID){
		
		List<MTRTransOrder> retorno = new ArrayList<MTRTransOrder>();
		
		String whereClause = X_UY_TR_TransOrderLine.COLUMNNAME_UY_TR_Trip_ID + " = " + tripID;
		
		List<MTRTransOrderLine> lineas = new Query(ctx, I_UY_TR_TransOrderLine.Table_Name, whereClause, trxName).list();
		
		for (MTRTransOrderLine mtrTransOrderLine : lineas) {
			
			if(mtrTransOrderLine.getUY_TR_TransOrder().getDocStatus().equalsIgnoreCase(X_UY_TR_TransOrder.DOCACTION_Apply)){
				
				for (MTRTransOrder guardada : retorno) {
					
					if(guardada.get_ID() != mtrTransOrderLine.getUY_TR_TransOrder_ID()){
						retorno.add((MTRTransOrder) mtrTransOrderLine.getUY_TR_TransOrder());	
					}					
				}						
			}
		}
		
		return retorno;
				
	}

}
