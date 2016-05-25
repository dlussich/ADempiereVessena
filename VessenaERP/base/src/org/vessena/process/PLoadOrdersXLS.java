/**
 * PLoadOrdersXLS.java
 * 09/03/2011
 */
package org.openup.process;



import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MPromotion;
import org.adempiere.model.MPromotionReward;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUOMConversion;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.beans.CabPedidoXLS;
import org.openup.beans.LineaPedidoXLS;
import org.openup.model.MLoadOrders_XLS;
import org.openup.model.MXLSIssue;



/**
 * OpenUp. PLoadOrdersXLS Descripcion :
 * 
 * @author Nicolas Fecha : 09/03/2011
 */
//org.openup.process.PLoadOrdersXLS
public class PLoadOrdersXLS extends SvrProcess {

	private MLoadOrders_XLS loadorders_xls = null;
	private static int defaultWarehouseID = 0;
	Sheet hoja=null;
	String fileName = null;
	
	Workbook workbook=null;
	int tope =0;
	int linesOK=0;
	int linesMal=0;
	
	
	
	
	protected String doIt() throws Exception {
		//Borro registros anteriores
		deleteOldError();
		
		// Cargo las ordenes
		String s=validacionXLSInicial();
		//Validacion inicial
		if(!(s.equals(""))){
			return s;
		}
		//Errores
		s=loadOrders(readXLS());
		loadorders_xls.set_CustomColumn("Text", s);
		loadorders_xls.save(null);
		return s;
	}

	@Override
	protected void prepare() {

		loadorders_xls = new MLoadOrders_XLS(getCtx(), getRecord_ID(), null);
		 fileName = this.loadorders_xls.getFileName();
		 defaultWarehouseID = getWarehouseID();
		 
		 
	}
	
	/**
	 * 
	 * OpenUp Ltda.
	 * Description: Obtiene almacen por defecto
	 * @author Nicolas Sarlabos - 24/01/2013
	 * @see
	 */
	private int getWarehouseID() {
		
		int id = 0;
		
		String sql = "select m_warehouse_id from m_warehouse where isdefault='Y'";
		id = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(id<=0){
			
			throw new AdempiereException ("No se encontro almacen predeterminado");
			
		} else return id;
	}

	private String loadOrders(HashMap<String,CabPedidoXLS> Heads) throws Exception {			
	String ms="Pedidos Incompletos:";
	
		//recorro lineas Head
		for( CabPedidoXLS head: Heads.values() ){

			if (head==null) continue;
			
			String trxAux=Trx.createTrxName();
			Trx trans = Trx.get(trxAux, true);
			
			try {
				boolean una= true;
				MOrder order = LoadHeaderFormLine(head.partnerLocation, head.fechaOrden, head.fechaEntrega, head.nroOrden, trxAux, head.getPaymentTermID(),head.porcientoDescPie);
				order.saveEx(trxAux);
				int auxLinesOK=0;
				for ( LineaPedidoXLS line: head.lista.values() ){				
					try {
						//Pregunto si existe en base de datos la combinacion cblocation, order y producto
						String mensaje=this.keyOK(head.nroOrden,head.partnerLocation.getC_BPartner_Location_ID(),line.product.getM_Product_ID());
						if(mensaje.equals("")){
		
							//Guardo linea								
							MOrderLine oline = this.LoadLine(order, trxAux,line);
							oline.saveEx(trxAux);
							auxLinesOK+=1;
							
								
							//Rollback si no tiene por lo menos una linea
							una = false;
						}else{
							// si esta seleccionada
							if(loadorders_xls.isErrorReplication()){
								// existe en base de datos la combinacion cblocation, order y producto, se mostrara los pedidos en los cuales fue cargados
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), "", "","Producto codigo "+ line.product.getValue()+" en orden "+head.nroOrden+" Ya fue ingresado en el pedido: "+ mensaje,null);
							}else{
								linesMal+=-1;
							}
						}
					} catch (Exception e) {
						MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),"", "","","","Producto "+line.product.getValue()+" "+e.getMessage(),null);
					}
						
				} // fin for lineas
				
				if (una) {
					
					//MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),"", "","","","No se pudo crear ninguna linea de Pedido de cliente "+order.getPOReference()+" por lo cual no se creo el pedido.",null);
					trans.rollback();
					trans.close();
				}else{
					// Completo la orden
					// Completo Factura
					if (order.processIt(DocAction.ACTION_Complete)) {
						
						
					}					
					else{
						MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),"", "","","","No se pudo completar Pedido : " + order.getDocumentNo() + "\n" + order.getProcessMsg(),null);
						ms+=" "+ order.getDocumentNo()+" ";
					}
					//sumo lineas ok
					linesOK+=auxLinesOK;
					order.saveEx(trxAux);
					trans.close();
				}
			} catch (Exception e) {
				//Error inesperado
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),"", "","","","Producto codigo "+e.getMessage(),null);
				trans.rollback();
				trans.close();
			}										
		
		} //end for

		if (!(ms.equals("Pedidos Incompletos:"))){
			return "Total Lineas:"+(tope-2)+"  Procesadas OK:"+linesOK+ "  Lineas con ERROR:"+((tope-2-linesOK+linesMal))+" \n" + ms;
		}
			
		return "Total Lineas:"+(tope-2)+"  Procesadas OK:"+linesOK+ "  Lineas con ERROR:"+((tope-2-linesOK+linesMal))+"\n";
	}
	
	private HashMap<String,CabPedidoXLS> readXLS() {
		//ArrayList<LineaPedidoXLS> lista= new ArrayList<LineaPedidoXLS>();
		HashMap<String,CabPedidoXLS> lista=new HashMap<String,CabPedidoXLS>();
		HashMap<String,String> controlRepetids=new HashMap<String,String>();
		
		Cell cell = null;
		tope = hoja.getRows();
	
		//TODO ARRANCA EN FILA 3
		for (int recorrido = 2; recorrido < tope; recorrido++) {
			try {
				
			
			
			LineaPedidoXLS linea =new LineaPedidoXLS(log);
			CabPedidoXLS head= new CabPedidoXLS(log);
			
			// 1-Valido GLN o VALUE ubicado columna G que es 6
			cell=hoja.getCell(6, recorrido);
			if(!(head.setPartnerLocation(cell, getCtx()))){
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"GLN o Codigo de Cliente INVALIDO",null);
			}
			// 2-Valido producto ubicado columna k que es 10
			cell=hoja.getCell(10, recorrido);
			String mensaje=linea.setProduct(cell, getCtx());
			if(!(mensaje.equals(""))){
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),mensaje,null);
			}
		

			// 3-Valido Nro Order No NULL ubicado columna a que es 0
			cell= hoja.getCell(0, recorrido);
			if(!(head.setNroOrden(cell))){
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"NroOrden Vacio",null);
			}
			
			// 4-Valido FechaOrden ubicado columna d que es 3
			cell = hoja.getCell(3, recorrido);
			if(!(head.setFechaOrden(cell))){
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Fecha Orden INVALIDA",null);
			}
		

			// 4-Valido FechaEntrega ubicado columna e que es 4
			cell = hoja.getCell(4, recorrido);
			if(!(head.setFechaEntrega(cell,head.getFechaOrden()))){  //OpenUp Nicolas Sarlabos 10/04/2012,se pasa la fecha de la orden para comparar con la de entrega
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Fecha Entrega INVALIDA",null);
			}

			// 5-Valido Cant que exista y que sea mayor a cero ubicado  columna p que es 15
			cell = hoja.getCell(15, recorrido);
			if(!(linea.setCant(cell))){
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"CANTIDAD ORDENADA MENOR A UNO O INVALIDA",null);
			}
			//Valido porcientoDescuent que exista , de no existir sera cero. ubicado  columna r que es 17
			cell = hoja.getCell(17, recorrido);
			linea.setPorcientoDescuent(cell);

				// OpenUp. Nicolas Garcia. 08/08/2011. Issue #812.
				// Termino de pago ubicado columna s que es 18
				cell = hoja.getCell(18, recorrido);
				head.paymentTerm = cell.getContents();
				// Fin Issue #812
				
				//Valido descuento al pie ubicado en columna t que es 19
				cell = hoja.getCell(19, recorrido);
				head.setDescalpie(cell);
				
			
				if (head.validar(linea)) {
				//Si ya esta ingresado un producto para esa orden para ese c_bpartner_location
				
				//Si existe cabezal
				if(lista.containsKey(head.claveCabezal())){
					head=lista.get(head.claveCabezal());
				}else{//Sino se crea y agrega
					lista.put(head.claveCabezal(), head);
				}
				
				// // existe en productos repetidos
				if(head.lista.containsKey(head.claveLinea(linea))||controlRepetids.containsKey(head.claveLinea(linea)) ||  
						(head.lista.containsKey(head.claveLinea(linea))&& controlRepetids.containsKey(head.claveLinea(linea)))){
					//mando mensaje
					MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Producto Repetido, NO será considerado",null);
					// Se elimina el producto
					head.remover(linea);
					//se agrega a lista de controlSi es que ya no esta agregada
					if(!(controlRepetids.containsKey(head.claveLinea(linea)))){
						controlRepetids.put(head.claveLinea(linea),"");
					}
					
				}else{
					// se agrega linea
					head.agregarLinea(linea);
				}				
				
			}
			
			} catch (Exception e) {
				//Errores no contemplados
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);
				
			}
		}
		
		if (workbook!=null){
			workbook.close();
		}
		return lista;
	}
	
	private String validacionXLSInicial(){
		
		
		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(getCtx(), getTable_ID(), getRecord_ID(), fileName,
					"", "", "", "El nombre de la planilla excel esta vacio",
					null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(getCtx(), getTable_ID(), getRecord_ID(), fileName,
					"", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}


		try {
			// Open de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la priemra hoja
			hoja = workbook.getSheet(0);
			
			
			
			// Se vacia la lista de errores
			// MXLSIssue.Delete(getCtx(), getTable_ID(),getRecord_ID(),null);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}

			if (hoja.getRows() < 1) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}
		}catch (Exception e) {
			MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","Error al abrir planilla (TRY)",null);
			return ("Error al abrir planilla (TRY)");
		}
			
		return"";
	}
	
	private String keyOK(String porreference, int c_bpartner_location_id, int m_product_id) throws Exception {
		// Se crea metodo para verificar si la clave esta ingresada actualmente
		// en el sistema.
		String salida = "";
		String sql = "SELECT o.documentno FROM c_order o LEFT JOIN c_orderline l ON l.c_order_id=o.c_order_id "
				+ "WHERE trim(o.poreference)=? AND o.c_bpartner_location_id=? AND l.m_product_id=? ";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, porreference);
			pstmt.setInt(2, c_bpartner_location_id);
			pstmt.setInt(3, m_product_id);
			rs = pstmt.executeQuery();

			// En caso de que devuelva algo, mostrara los pedidos para que el
			// usuario pueda revisar
			while (rs.next()) {
				salida += rs.getString("documentno") + " ";
			}

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return salida;
	}

	// OpenUp. Nicolas Garcia. 08/08/2011. Issue #812.
	// Se agrega a la funcion que reciba paymentTerm como parametro
	private MOrder LoadHeaderFormLine(MBPartnerLocation location, Timestamp fechaOrden, Timestamp fechaEntrega, String nroOrden, String trxNameAux,
			int paymentTerm,BigDecimal descPie) throws Exception {

		// Return object
		MOrder header = null;

		// Default values for partner
		MBPartner partner = null;

		if (location != null) {
			partner = new MBPartner(Env.getCtx(), location.getC_BPartner_ID(), null);

			// Save the header and line only when a partner was get
			if ((partner != null) && (location != null)) {

				if (fechaOrden != null) {

					// Create a new header, ID 0, set fields and save
					header = new MOrder(Env.getCtx(), 0, trxNameAux);// TRANSACCION

					// Reference order
					header.setPOReference(nroOrden.trim());

					// Set partner
					header.setC_BPartner_ID(partner.getC_BPartner_ID());
					header.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());

					// OpenUp. Nicolas Garcia. 08/08/2011. Issue #812.
					// Set PaymentTerm
					header.setC_PaymentTerm_ID(paymentTerm);
					// Fin Issue #812

					// Set document status and action
					header.setDocStatus(MOrder.DOCSTATUS_Drafted);
					header.setDocAction(MOrder.DOCACTION_Complete);

					// Set the user as the sales representative only if it was
					// set at the partner
					if (partner.getSalesRep_ID() != 0) { // OpenUp FL 11/01/2011
						header.setAD_User_ID(partner.getSalesRep_ID());
					}
					
					// TODO: Review, Orden de Venta EDI
					MDocType doc = MDocType.forValue(getCtx(), "salesorder", get_TrxName());
					header.setC_DocTypeTarget_ID(doc.get_ID());
					header.setC_DocType_ID(doc.get_ID());

					// Set dates
					// OpenUp. Nicolas Garcia. 07/12/2011. Issue #A PEDIDO DE
					// GABRIEL, GANDINI.
					// header.setDateOrdered(fechaOrden);
					// header.setDateAcct(fechaOrden);
					Timestamp fecha = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
					header.setDateOrdered(fecha);
					header.setDateAcct(fecha);
					// Fin Issue #A PEDIDO DE GABRIEL, GANDINI

					header.setDatePromised(fechaEntrega);

					header.set_ValueOfColumn("UY_ReservaStock", "N");

					// Set the price list of the partner
					if (partner.getM_PriceList_ID() <= 0)
						header.setM_PriceList_ID(1000002);
					else
						header.setM_PriceList_ID(partner.getM_PriceList_ID());

					// Set the sales representative only if it was set at the
					// partner
					if (partner.getSalesRep_ID() != 0) { // OpenUp FL 11/01/2011
						header.setSalesRep_ID(partner.getSalesRep_ID());
					}

					// Warehouse
					header.setM_Warehouse_ID(defaultWarehouseID);
					
					//OpenUp. Nicolas Sarlabos. 24/01/2013. Se obtiene descuento al pie de factura
					if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){

						BigDecimal Discount = Env.ZERO;

						if(descPie!=null && descPie.compareTo(Env.ZERO)>=0){  //tiene prioridad el descuento ingresado en la planilla

							Discount = descPie;
							
						} else {

							MBPartnerLocation loc = new MBPartnerLocation(getCtx(),header.getC_BPartner_Location_ID(),get_TrxName());
							Discount = loc.getDiscount(); //obtengo descuento al pie de la sucursal

							if(Discount.compareTo(Env.ZERO)<=0){ //si no obtuve descuento desde la sucursal busco en el cliente

								Discount = partner.getDiscount();	//obtengo descuento al pie del cliente	

							}

						}

						header.setDiscount(Discount);
												
					}
					//Fin OpenUp.

					// Set flags
					header.setPosted(false);
					header.setProcessed(false);

				}
			}
		}

		return (header);
	}

	private MOrderLine LoadLine(MOrder header, String trxNameAux ,LineaPedidoXLS linea)throws Exception{
		MOrderLine line=null;
		if (header!=null) {
			
			// Create a new line based on the header, set fields and save
			 line = new MOrderLine(header);
			
			line.setM_Product_ID(linea.product.getM_Product_ID());
			line.setM_Warehouse_ID(defaultWarehouseID);
			
			// Get the default factor of the producto and get the cant to avoid recordset get 
			//BigDecimal factor=product.getFactorUVDefualt();
			BigDecimal factor = MUOMConversion.getProductRateFrom(Env.getCtx(), linea.product.getM_Product_ID(), linea.product.getC_UOM_To_ID());
			
			// Controla que cantidades negativas o 0
			if (linea.cant  != null) {
				if (linea.cant.compareTo(BigDecimal.ZERO)<=0) {
					// Cantidad negativa o 0
					//setErrorMsg(rs.getInt("i_uy_ordenes_id"),"Cantidad");
				} 
				else {
					
					// Not factor found the units is one
					if (factor==null) {
						line.setC_UOM_ID(linea.product.getC_UOM_ID()); 											
						line.setQtyEntered(linea.cant );
						line.setQtyOrdered(linea.cant );
					}
					else {
						//if (cant.remainder(factor)!=BigDecimal.ZERO) {								// If there is no remainder, the division is not integer
						if (linea.cant.remainder(factor).compareTo(BigDecimal.ZERO)!=0) {								// If there is no remainder, the division is not integer							
							line.setC_UOM_ID(linea.product.getC_UOM_ID()); 											
							line.setQtyEntered(linea.cant );
							line.setQtyOrdered(linea.cant );
						}
						else {																		// If there is remainder, the division is integer
//							String uy_unidadventa=(String) product.get_Value("uy_unidadventa");		// Get unidadventa from product
//							line.setC_UOM_ID(MUOM.getIDFromSymbol(uy_unidadventa));				// Set unit of measure acording to the unidaddventa				
//							
//							line.setQtyEntered(cant.divide(factor));								// Set the quantity divided by de factor
//							line.setQtyOrdered(cant);
							line.setC_UOM_ID(linea.product.getC_UOM_To_ID());
							line.setQtyEntered(linea.cant.divide(factor));								// Set the quantity divided by de factor
							line.setQtyOrdered(linea.cant );
						}
					}
					
					// Set prices
					
					//Si el %excel no existe
					if(linea.porcientoDescuent==null || linea.porcientoDescuent.compareTo(Env.ZERO)<0 ){
						
						if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){
							
							//OpenUp. Nicolas Sarlabos. 24/01/2013, obtengo porcentaje de descuento desde cliente o grupo
							MBPartner partner = new MBPartner(getCtx(),header.getC_BPartner_ID(),get_TrxName()); //instancio modelo del cliente
							BigDecimal flatDiscount = partner.getDiscountFromPartner();
							line.setFlatDiscount(flatDiscount);
							//Fin OpenUp.
						} else
							//Se realiza normalmente, no se mostrara mensaje OJO!
							line.setFormatInfo(null);
					}else{
						//traigo la promocion
						MPromotionReward reward = MPromotion.getReward(header.getC_BPartner_ID(), line.getM_Product_ID(), line.getQtyOrdered(), header.getDateOrdered(), BigDecimal.ZERO);
						// Si existe promocion para este producto-cliente
	                     if (reward != null){//TODO VERIFICAR QUE SEA MAYOR %exel
	                             // Si porcentaje de promocion es mayor a cero y mayor e igual al %Excle y %Excel mayor = a 0
	                             if ((reward.getAmount().compareTo(Env.ZERO)>=0)&&(reward.getAmount().compareTo(linea.porcientoDescuent)>=0)
	                            		 &&(linea.porcientoDescuent.compareTo(BigDecimal.ZERO)>=0)){

	                            	 //Todo OK uso %EXCEL
	                            	 line.setFormatInfo(linea.porcientoDescuent);
	                            	 
	                             }else{
	                            	 //O el porciento de promo es cero o es menor que el %excel
	                            	 MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(linea.cellPorcientoDescuent.getColumn(),linea.cellPorcientoDescuent.getRow()), linea.cellPorcientoDescuent.getContents(),"Porcentaje descuento planilla mayor a porcentaje promocion , Maximo para cliene-producto: "+reward.getAmount(),null);
	    	                    	 line.setFormatInfo(null);
	                             }
	                             
	                     }else{
	                    	 //si tiene %exel pero no tiene promociones avisar que no se toma %exel
	                    	 if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){
	                    		 
	                    		 line.setFlatDiscount(linea.porcientoDescuent);

	                    	 } else {
	                    		 MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(linea.cellPorcientoDescuent.getColumn(),linea.cellPorcientoDescuent.getRow()), linea.cellPorcientoDescuent.getContents(),"Si bien cargo procentaje en la planilla,el producto para este cliente no cuenta con descuento por promociones",null);
	                    		 line.setFormatInfo(null);
	                    	 }
	                     }
					}
					
                     //line.setFormatInfo(null);
					
					// Save line
					
				}
			}
						
		}
					
				
		return line	;
	}

	private void deleteOldError()throws Exception{

		//String sql = "DELETE FROM  uy_xlsissue WHERE record_id="+ loadorders_xls.getUY_LoadOrders_XLS_ID();
		//A pedido de gabriel Se borran todo lo del usuario para ese tipo de carga al procesar
		
		String sql = "DELETE  FROM uy_xlsissue WHERE record_id IN(SELECT uy_loadorders_xls_id FROM uy_loadorders_xls WHERE createdby="+Env.getAD_User_ID(Env.getCtx())+") " +
				"OR record_id="+getRecord_ID()+" AND createdby="+Env.getAD_User_ID(Env.getCtx())+";" +
					"DELETE FROM uy_loadorders_xls WHERE createdby="+Env.getAD_User_ID(Env.getCtx())+" AND uy_loadorders_xls_id<>"+this.getRecord_ID();
		
		try {
			DB.executeUpdate(sql, null);
			
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
}
