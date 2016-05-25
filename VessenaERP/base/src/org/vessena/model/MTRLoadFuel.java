/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MActivity;
import org.compiere.model.MBPartner;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPriceList;
import org.compiere.model.MTax;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.AuxWorkCellXLS;

/**
 * @author Nicolas
 *
 */
public class MTRLoadFuel extends X_UY_TR_LoadFuel implements DocAction{
	Sheet hoja=null;
	String fileName = null;

	Workbook workbook=null;
	Integer tope =0;
	int linesOK=0;
	int linesMal=0;
	AuxWorkCellXLS utiles;
	Properties ctx;
	Integer table_ID;
	Integer record_ID;	
	CLogger	log ;
	
	private String processMsg = null;
	private boolean justPrepared = false;
	
	private int vendorID = 0;
	private int currencyID = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3382873120668792518L;

	/**
	 * @param ctx
	 * @param UY_TR_LoadFuel_ID
	 * @param trxName
	 */
	public MTRLoadFuel(Properties ctx, int UY_TR_LoadFuel_ID, String trxName) {
		super(ctx, UY_TR_LoadFuel_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadFuel(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		try {

			this.deleteOldData();
			this.deleteOldError();
			this.loadData();	
		
			if(!this.hayErrores()){
				this.setDocAction(DocAction.ACTION_Complete);
				this.setDocStatus(DocumentEngine.STATUS_Applied);
			} else {
				this.deleteOldData();
				throw new AdempiereException ("Carga finalizada con errores");
			}

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		return true;
	}

	/***
	 * Carga planilla de consumos de combustible.
	 * OpenUp Ltda. Issue #1609
	 * @author Nicolas Sarlabos - 27/11/2013
	 * @throws Exception 
	 * @see
	 */
	private void loadData() throws Exception{
		
		fileName = this.getFileName(); //cargo nombre de archivo xls
		
		//Borro errores anteriores
		//deleteOldError();
		
		String s=validacionXLSInicial();
		//Validacion inicial
		if(!(s.equals(""))){
			throw new AdempiereException (s);
		}
		
		this.readXLS();
									
	}
	
	private void deleteOldError()throws Exception{//TODO:verificar!!!

		String sql = "DELETE FROM uy_xlsissue WHERE ad_table_id = " + X_UY_TR_LoadFuel.Table_ID +
				" AND record_id = " + this.get_ID();

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	private void deleteOldData() throws Exception{

		String sql = "DELETE FROM uy_tr_loadfuelline WHERE uy_tr_loadfuel_id = " + this.get_ID();

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	private String validacionXLSInicial(){


		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(getCtx(), get_Table_ID(), this.get_ID(), fileName,
					"", "", "", "El nombre de la planilla excel esta vacio",
					null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(getCtx(), get_Table_ID(), this.get_ID(), fileName,
					"", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}

		try {

			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(getCtx(),get_Table_ID(),this.get_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}

			if (hoja.getRows() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}
		}catch (Exception e) {
			MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","Error al abrir planilla (TRY) "+e.toString(),null);
			return ("Error al abrir planilla (TRY)");
		}

		return"";
	}
	
	private String readXLS() throws Exception {

		String message = "";
		Cell cell = null;
		tope = hoja.getRows();
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(getCtx(), get_Table_ID(), get_ID(),null, hoja,null);
		MTRLoadFuelLine line = null;
		int cantVacias = 0;
		

		for (int recorrido = 3; recorrido < tope; recorrido++) {
			
			message = "";
			
			String fechaCargaVacio = null;
			String msgFechaCargaVacio = "";
			
			String proveedorVacio = null;
			String msgProveedorVacio = "";
			
			String proveedorNoExiste = null;
			String msgProveedorNoExiste = "";
						
			String vehiculoVacio = null;
			String msgVehiculoVacio = "";	
			
			String vehiculoNoExiste = null;
			String msgVehiculoNoExiste = "";
			
			//String conductorVacio = null;
			//String msgConductorVacio = "";
			
			//String conductorNoExiste = null;
			//String msgConductorNoExiste = "";
			
			String boletaVacio = null;
			String msgBoletaVacio = "";
			
			String kmVacio = null;
			String msgKmVacio = "";
			
			String kmInvalido = null;
			String msgKmInvalido = "";
			
			String litrosVacio = null;
			String msgLitrosVacio = "";
			
			String importeVacio = null;
			String msgImporteVacio = "";	
						
			String litrosInvalido = null;
			String msgLitrosInvalido = "";
			
			String monedaInvalido = null;
			String msgMonedaInvalido = "";
			
			String monedaVacio = null;
			String msgMonedaVacio = "";
			
			String importeInvalido = null;
			String msgImporteInvalido = "";
			
			try {

				//Se lee Fecha de Carga en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				Timestamp fechaCarga = null;
				String fchCar=utiles.getStringFromCell(cell);
				if(fchCar!=null){
					try{
						fchCar=fchCar.trim();
						fechaCarga=utiles.formatStringDate(fchCar);
						String f = fechaCarga.toString();
						if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
							MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
							message="Fecha de carga con formato incorrecto";

						}

					}catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						message="Fecha de carga con formato incorrecto";
						fechaCarga=null;
					}
				} else {
					fechaCarga=null;
					message="Fecha de carga vacia";
					fechaCargaVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgFechaCargaVacio = message;
				}
				//---------------------------------------------------------------
				
				//Se lee Codigo de Proveedor en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				int provID = 0;
				String proveedor=utiles.getStringFromCell(cell);
				if(proveedor==null || proveedor.equalsIgnoreCase("")){
					proveedor=null;
					message="Codigo de proveedor vacio";
					proveedorVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgProveedorVacio = message;
				} else {
						
					proveedor = proveedor.trim();
					
					//valido que exista el proveedor
					MBPartner partner = MBPartner.forValue(getCtx(), proveedor, get_TrxName());
					
					if(partner != null){
						
						provID = partner.get_ID();
						
					} else {
						
						message="Codigo de proveedor no existe";
						proveedorNoExiste = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
						msgProveedorNoExiste = message;						
					}										
				}
				//-----------------------------------------------------------------------------
			
				//Se lee Matricula en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				int truckID = 0;
				String matricula=utiles.getStringFromCell(cell);
				if(matricula==null || matricula.equalsIgnoreCase("")){
					matricula=null;
					message="Matricula vacio";
					vehiculoVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgVehiculoVacio = message;
				} else {
					
					matricula = matricula.trim();
					
					//valido que exista la matricula
					MTRTruck truck = MTRTruck.forValue(getCtx(), matricula, get_TrxName());
					
					if(truck != null){
						
						truckID = truck.get_ID();
						
					} else {
						
						message="Matricula no existe";
						vehiculoNoExiste = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
						msgVehiculoNoExiste = message;						
					}					
					
				}
				//-----------------------------------------------------------------------------
								
				//Se lee Codigo de Conductor en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				int driverID = 0;
				String conductor=utiles.getStringFromCell(cell);
				if(conductor!=null && !conductor.equalsIgnoreCase("")){
	
					//valido que exista el conductor
					MTRDriver driver = MTRDriver.forValue(getCtx(), conductor, get_TrxName());
					
					if(driver != null){
						
						driverID = driver.get_ID();
						
					} /*else {
						
						message="Codigo conductor no existe";
						conductorNoExiste = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
						msgConductorNoExiste = message;
											
					}*/			
					
				} /*else {
					
					conductor=null;
					message="Codigo de conductor vacio";
					conductorVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgConductorVacio = message;					
				}*/
				//-----------------------------------------------------------------------------
				
				//Se lee Nro de Boleta en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				String boleta=utiles.getStringFromCell(cell);
				if(boleta!=null && !boleta.equalsIgnoreCase("")){
					boleta = boleta.trim();
				} else {
					boleta=null;
					message="Nro de boleta vacio";
					boletaVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgBoletaVacio = message;
				}
				//-----------------------------------------------------------------------------
				
				//Se lee Kilometros en columna F que es 5***************************
				cell = (hoja.getCell(5, recorrido));
				BigDecimal kilometros = Env.ZERO;
				String km=utiles.getStringFromCell(cell);
				if(km!=null && !km.equalsIgnoreCase("")){
					
					try {
						km = km.replace(",", "");
						kilometros = new BigDecimal(km);	
						
						if(kilometros.compareTo(Env.ZERO)<=0){
							message="Kilometros debe ser mayor a cero";
							kmInvalido = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgKmInvalido = message;							
						}
						
					} catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Error al obtener Kilometros",null);
						message="Error al obtener Kilometros";
						kilometros=Env.ZERO;						
					}					
					
				} else {
					km=null;
					message="Kilometraje vacio";
					kmVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgKmVacio = message;					
				}
				//-----------------------------------------------------------------------------
				
				//Se lee Litros en columna G que es 6***************************
				cell = (hoja.getCell(6, recorrido));
				BigDecimal litros = Env.ZERO;
				String lts=utiles.getStringFromCell(cell);
				if(lts==null || lts.equalsIgnoreCase("")){
					litros=null;
					message="Litros vacio";
					litrosVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgLitrosVacio = message;					

				} else {

					try {
						
						lts = lts.replace(",", "");
						litros = new BigDecimal(lts);
						
						if(litros.compareTo(Env.ZERO)<=0){
							litros=null;
							message="Litros debe ser mayor a cero";
							litrosInvalido = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgLitrosInvalido = message;							
						}

					} catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Error al obtener Litros",null);
						message="Error al obtener Litros";
						litros=null;						
					}					

				}			
				//-----------------------------------------------------------------------------
				
				//Se lee Completa Tanque en columna H que es 7***************************
				cell = (hoja.getCell(7, recorrido));
				Boolean tank = false;
				String tanque=utiles.getStringFromCell(cell);
				if(tanque!=null && !tanque.equalsIgnoreCase("")){
					
					String tanqueAux = tanque.toUpperCase().trim();
					
					if(tanqueAux.equalsIgnoreCase("S")){
						
						tank = true;
						
					} else if(tanqueAux.equalsIgnoreCase("N")){
						
						tank = false;						
						
					} else {
						
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Dato invalido. Debe ser S o N.",null);
						message="Dato invalido. Debe ser S o N.";						
					}					
					
				}
				//-----------------------------------------------------------------------------
				
				//Se lee Moneda en columna I que es 8***************************
				cell = (hoja.getCell(8, recorrido));
				String moneda=utiles.getStringFromCell(cell);
				int currencyID = 0;
				
				if(moneda==null || moneda.equalsIgnoreCase("")){
					moneda=null;
					message="Moneda vacio";
					monedaVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgMonedaVacio = message;
				} else {
					
					moneda = moneda.trim();
					
					//valido que exista la moneda
					MCurrency cur = MCurrency.get(getCtx(), moneda);
					
					if(cur != null && cur.isActive()){
						
						currencyID = cur.get_ID();
						
					} else {
						
						message="Moneda no existe o esta inactiva";
						monedaInvalido = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
						msgMonedaInvalido = message;						
					}					
					
				}
				//--------------------------------------------------------------------------------------------				
				
				//Se lee Importe Total en columna J que es 9***************************
				cell = (hoja.getCell(9, recorrido));
				BigDecimal importe = Env.ZERO;
				String amt=utiles.getStringFromCell(cell);
				if(amt==null || amt.equalsIgnoreCase("")){
					importe=null;
					message="Importe vacio";
					importeVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgImporteVacio = message;
				
				} else {
					
					try {
						amt = amt.replace(",", "");
						importe = new BigDecimal(amt);		
						
						if(importe.compareTo(Env.ZERO)<=0){
							importe=null;
							message="Importe debe ser mayor a cero";
							importeInvalido = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgImporteInvalido = message;							
						}
						
					} catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Error al obtener Importe",null);
						message="Error al obtener Importe";
						importe=null;						
					}					
				}
				//------------------------------------------------------------------------------------------------------------------

				if(fechaCarga!=null && provID > 0 && truckID > 0 && boleta != null && km != null && litros!=null && currencyID > 0 && importe!=null && message.equalsIgnoreCase("")){
					
					line = new MTRLoadFuelLine(getCtx(), 0, null);
					line.setUY_TR_LoadFuel_ID(this.get_ID());
					line.setDateTrx(fechaCarga);
					line.setC_BPartner_ID(provID);
					line.setUY_TR_Truck_ID(truckID);
					if(driverID > 0) line.setUY_TR_Driver_ID(driverID);
					if(boleta != null) line.setfactura(boleta);
					line.setKilometros(kilometros.intValue());
					line.setLitros(litros);
					line.setIsFullTank(tank);
					line.setC_Currency_ID(currencyID);
					line.setTotalAmt(importe);				
					line.saveEx(null);

				} 
				
				if(fechaCarga!=null || provID > 0 || truckID > 0 || boleta!=null || km!=null || litros!=null || currencyID > 0 || importe!=null){
					
					//si tengo al menos un campo No vacio entonces inserto todos los errores encontrados
					if(fechaCargaVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), fechaCargaVacio, "", msgFechaCargaVacio,null);
					if(proveedorVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), proveedorVacio, "", msgProveedorVacio,null);
					//if(conductorVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), conductorVacio, "", msgConductorVacio,null);
					if(boletaVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), boletaVacio, "", msgBoletaVacio,null);
					if(vehiculoVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), vehiculoVacio, "", msgVehiculoVacio,null);
					if(kmVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), kmVacio, "", msgKmVacio,null);
					if(litrosVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), litrosVacio, "", msgLitrosVacio,null);
					if(monedaVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), monedaVacio, "", msgMonedaVacio,null);
					if(importeVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), importeVacio, "", msgImporteVacio,null);	
					
					if(proveedorNoExiste!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), proveedorNoExiste, proveedor, msgProveedorNoExiste,null);
					if(vehiculoNoExiste!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), vehiculoNoExiste, matricula, msgVehiculoNoExiste,null);
					//if(conductorNoExiste!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), conductorNoExiste, conductor, msgConductorNoExiste,null);
					
					if(kmInvalido!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), kmInvalido, km, msgKmInvalido,null);
					if(litrosInvalido!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), litrosInvalido, lts, msgLitrosInvalido,null);
					if(monedaInvalido!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), monedaInvalido, moneda, msgMonedaInvalido,null);
					if(importeInvalido!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), importeInvalido, amt, msgImporteInvalido,null);
				}
				
				if(fechaCarga==null && provID <= 0 && truckID <= 0 && litros==null && importe==null) cantVacias ++; //aumento contador de filas vacias
							
				if(cantVacias == 10) recorrido = tope; //permito un maximo de 10 lineas vacias leidas, superado ese tope salgo del FOR
				
			} catch (Exception e) {
				//Errores no contemplados
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

			}
		
		}				

		if (workbook!=null){
			workbook.close();
		}

		if(message.equalsIgnoreCase("")) message="Proceso Finalizado OK";

		return message;
	}
	
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(this.getLines().size() <= 0) throw new AdempiereException("No hay registros correctos para procesar");
		
		//No permito completar el documento si existen errores
		if(this.hayErrores()) throw new AdempiereException("Imposible completar documento, verifique errores");		
		
		this.verifyVendor();//verifica y setea el proveedor
		this.verifyCurrency();//verifica y setea la moneda
		this.generateDoc(); //genero y completo documentos	
		this.generateInvoice(); //genero y completo factura proveedor sin OC
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}
	
	/***
	 * Verifica cantidad de proveedores en la planilla y obtiene el proveedor unico.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 02/06/2015
	 * @see
	 * @return
	 */	
	private void verifyVendor() {
		
		String sql = "select count(distinct(c_bpartner_id))" +
		             " from uy_tr_loadfuelline" +
				     " where uy_tr_loadfuel_id = " + this.get_ID();
		int count = DB.getSQLValue(get_TrxName(), sql);
		
		if(count == 1){
			
			//obtengo el unico proveedor
			sql =  "select distinct(c_bpartner_id)" +
		           " from uy_tr_loadfuelline" +
				   " where uy_tr_loadfuel_id = " + this.get_ID();
			int partnerID = DB.getSQLValue(get_TrxName(), sql);
			
			this.vendorID = partnerID;			
			
		} else if (count > 1) {
			
			throw new AdempiereException("Imposible continuar. Existe mas de 1 proveedor en esta planilla");		
			
		} else if (count <= 0) throw new AdempiereException("Imposible continuar. No se obtuvieron lineas de consumo en el documento actual");	
		
	}

	/***
	 * Verifica cantidad de monedas en la planilla y obtiene la unica.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 02/06/2015
	 * @see
	 * @return
	 */	
	private void verifyCurrency() {
		
		String sql = "select count(distinct(c_currency_id))" +
		             " from uy_tr_loadfuelline" +
				     " where uy_tr_loadfuel_id = " + this.get_ID();
		int count = DB.getSQLValue(get_TrxName(), sql);
		
		if(count == 1){
			
			//obtengo el unico proveedor
			sql =  "select distinct(c_currency_id)" +
		           " from uy_tr_loadfuelline" +
				   " where uy_tr_loadfuel_id = " + this.get_ID();
			int curID = DB.getSQLValue(get_TrxName(), sql);
			
			this.currencyID = curID;			
			
		} else if (count > 1) {
			
			throw new AdempiereException("Imposible continuar. Existe mas de 1 moneda en esta planilla");		
			
		} else if (count <= 0) throw new AdempiereException("Imposible continuar. No se obtuvieron lineas de consumo en el documento actual");	
		
	}
	
	/***
	 * Genera y completa los documentos de Consumo de Combustible desde las lineas de la carga.
	 * OpenUp Ltda. Issue #1609
	 * @author Nicolas Sarlabos - 29/11/2013
	 * @see
	 * @return
	 */	
	private void generateDoc() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
				
		try {		
					
			sql = "select * from uy_tr_loadfuelline where uy_tr_loadfuel_id = " + this.get_ID();
						
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
				//Creo el modelo del cabezal de documento
				MTRFuel hdr = new MTRFuel(this.getCtx(), 0, this.get_TrxName());
				hdr.setIsManual(false);
				hdr.setUY_TR_LoadFuel_ID(this.get_ID());
				hdr.setC_DocType_ID(MDocType.forValue(this.getCtx(), "fuel", null).get_ID());
				hdr.setDateTrx(new Timestamp(System.currentTimeMillis()));
				hdr.setDateOperation(rs.getTimestamp("datetrx"));
				hdr.setC_BPartner_ID(rs.getInt("c_bpartner_ID"));
				hdr.setUY_TR_Truck_ID(rs.getInt("uy_tr_truck_id"));
				hdr.setUY_TR_Driver_ID(rs.getInt("uy_tr_driver_id"));
				hdr.setfactura(rs.getString("factura"));
				hdr.setKilometros(rs.getInt("kilometros"));
				hdr.setLitros(rs.getBigDecimal("litros"));
				hdr.setTotalAmt(rs.getBigDecimal("totalamt"));
				hdr.setC_Currency_ID(rs.getInt("c_currency_id"));
				
				String fullTank = rs.getString("isfulltank");
				
				if(fullTank.equalsIgnoreCase("Y")){
					hdr.setIsFullTank(true);
				} else hdr.setIsFullTank(false);
								
				hdr.saveEx();	
				
				//completo el documento
				if (!hdr.processIt(DocumentEngine.ACTION_Complete)){
					throw new AdempiereException(hdr.getProcessMsg());
				}				
		    } 			  			
			
		} catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
	}
	
	/***
	 * Retorna lineas procesadas de esta carga.
	 * OpenUp Ltda. Issue #1609
	 * @author Nicolas Sarlabos - 28/11/2013
	 * @see
	 * @return
	 */
	public List<MTRLoadFuelLine> getLines(){
		
		String whereClause = X_UY_TR_LoadFuelLine.COLUMNNAME_UY_TR_LoadFuel_ID + "=" + this.get_ID();
		
		List<MTRLoadFuelLine> lines = new Query(getCtx(), I_UY_TR_LoadFuelLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
	}
	
	/***
	 * Metodo que genera una factura asociada a esta carga de consumos de combustible
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 02/06/2015
	 * @see
	 */
	private void generateInvoice() {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MTRTruck truck = null;
		int truckID = 0;
		MInvoiceLine line = null;
		MActivity cCosto = null;
		BigDecimal amount = Env.ZERO;

		try {
	
			MDocType doc = MDocType.forValue(getCtx(), "factprovsinoc", null);
			MBPartner partner = new MBPartner(getCtx(), this.vendorID, get_TrxName());
			MCurrency currency = new MCurrency(getCtx(), this.currencyID, get_TrxName());			
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			String documentNo = this.getDocInvoice(today);

			// Seteo cabezal de factura
			MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
			invoice.setIsSOTrx(false);
			invoice.setBPartner(partner);
			invoice.setDateInvoiced(today);
			invoice.setDueDate(this.getDueDate());
			invoice.setDateVendor(today);			
			invoice.setDateAcct(today);
			invoice.setDescription("Carga de Consumos de Combustible Numero : " + this.getDocumentNo());
			invoice.setC_DocType_ID(doc.get_ID());
			invoice.setC_DocTypeTarget_ID(doc.get_ID());
			invoice.setC_Currency_ID(currency.get_ID());
					
			MPriceList list = MPriceList.getDefault(getCtx(), false, currency.getISO_Code());
			
			if(list == null) throw new AdempiereException("No se obtuvo lista de precios de compra predeterminada para la moneda " + currency.getCurSymbol());
			
			invoice.setM_PriceList_ID(list.get_ID());				
			invoice.setpaymentruletype("CR");
			invoice.setC_PaymentTerm_ID(MPaymentTerm.forValue(getCtx(), "credito", null).get_ID());
			invoice.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
			invoice.setDocStatus(DocumentEngine.STATUS_Drafted);
			invoice.setDocAction(DocumentEngine.ACTION_Complete);
			invoice.setDocumentNoAux(documentNo);
			invoice.setDocumentNo(documentNo);
			invoice.saveEx();
			
			// Obtengo modelo de producto correspondiente a combustible filtrando por linea de negocio combustible
			String sql = " select m_product_id from m_product " +
					     " where uy_linea_negocio_id = (select uy_linea_negocio_id from uy_linea_negocio where value='combustible')";
			int mProductID = DB.getSQLValue(null, sql);
			if (mProductID <= 0){
				throw new AdempiereException("No se pudo obtener el producto correspondiente para combustibles.");
			}
			
			// Impuesto exento por defecto
			MTax tax = MTax.forValue(getCtx(), "exento", null);

			//debo recorrer haciendo corte de control por VEHICULO
			sql = "select uy_tr_truck_id, totalamt" +
			      " from uy_tr_loadfuelline" +
				  " where uy_tr_loadfuel_id = " + this.get_ID() +
				  " order by uy_tr_truck_id";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				
				int truck_id = rs.getInt("UY_TR_Truck_ID");
				
				//corte de control por Vehiculo
				if(truckID != truck_id) {
					
					if(line!=null) {
						
						line.setPriceActual(amount);
						line.setPriceEntered(amount);
						line.setLineNetAmt(amount);
						line.setLineTotalAmt(amount);						
						
						line.saveEx();
					}
					
					amount = Env.ZERO;
					
					truckID = truck_id;
					
					truck = new MTRTruck(getCtx(), truck_id, get_TrxName());
					
					// Obtengo Centro de costo del vehiculo
					cCosto =  new MActivity(getCtx(), truck.getC_Activity_ID(), get_TrxName());
					
					line = new MInvoiceLine(getCtx(), 0, get_TrxName());
					line.setC_Invoice_ID(invoice.get_ID());
					line.setM_Product_ID(mProductID);					
					line.setQtyEntered(Env.ONE);
					line.setQtyInvoiced(Env.ONE);
					if(cCosto!=null && cCosto.get_ID()>0) line.setC_Activity_ID(cCosto.get_ID());
					line.setC_Tax_ID(tax.get_ID());
					line.setTaxAmt(Env.ZERO);
					
															
				}
				
				amount = amount.add(rs.getBigDecimal("totalamt"));										
				
			}   
			
			line.setPriceActual(amount);
			line.setPriceEntered(amount);
			line.setLineNetAmt(amount);
			line.setLineTotalAmt(amount);						
			
			line.saveEx();

			// Completo factura
			if (!invoice.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException("No se pudo generar Factura Asociada a este Consumo : \n" + invoice.getProcessMsg());
			}
			
			// Todo bien, asocio factura con este consumo de combustible
			this.setC_Invoice_ID(invoice.get_ID());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	private String getDocInvoice(Timestamp date) {
		
		String value = "", day = "", month = "", year = "";
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date); 
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH) + 1;
		int anio = cal.get(Calendar.YEAR);
		
		day = String.valueOf(dia);
		month = String.valueOf(mes);
		year = String.valueOf(anio);

		value = day + month + year;
		
		return value;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hayErrores(){
		
		String sql = "select uy_xlsissue_id from uy_xlsissue where ad_table_id = " + get_Table_ID() + " and record_id = " + this.get_ID();
		int errors = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(errors > 0) return true;
		
		return false;	
		
	}
	
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
