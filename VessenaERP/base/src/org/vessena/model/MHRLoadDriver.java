/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MConversionRate;
import org.compiere.model.MJobCategory;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.MHRConcept;
import org.openup.beans.AuxWorkCellXLS;

/**
 * @author Nicolas
 *
 */
public class MHRLoadDriver extends X_UY_HRLoadDriver implements DocAction{
	
	Sheet hoja=null;
	String fileName = null;
	private BigDecimal divideRate = Env.ZERO;

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
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8410699590159970795L;

	/**
	 * @param ctx
	 * @param UY_HRLoadDriver_ID
	 * @param trxName
	 */
	public MHRLoadDriver(Properties ctx, int UY_HRLoadDriver_ID, String trxName) {
		super(ctx, UY_HRLoadDriver_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRLoadDriver(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 24/11/2014. #3214.
		/*if(newRecord){
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			BigDecimal divideRate = MConversionRate.getDivideRate(142, 100, today, 0, this.getAD_Client_ID(), this.getAD_Org_ID());
			
			if(divideRate.compareTo(Env.ZERO)==0) throw new AdempiereException("No se encontro tasa de cambio para la fecha actual");
			
			this.setCurrencyRate(divideRate);		
			
		}*/
		//Fin #3214.
		
		return true;
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
				this.loadAmtFields();
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

	private void deleteOldData() throws Exception{

		String sql = "DELETE FROM uy_hrloaddriverline WHERE uy_hrloaddriver_id = " + this.get_ID();

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	private void deleteOldError()throws Exception{

		String sql = "DELETE FROM uy_xlsissue WHERE record_id IN (SELECT uy_hrloaddriver_id FROM uy_hrloaddriver WHERE createdby="+Env.getAD_User_ID(Env.getCtx())+") " +
				"OR record_id=" + this.get_ID() +" AND createdby="+Env.getAD_User_ID(Env.getCtx());

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	/***
	 * Carga planilla de conceptos de nomina para conductores.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 14/01/2014
	 * @throws Exception 
	 * @see
	 */
	private void loadData() throws Exception{
		
		fileName = this.getFileName(); //cargo nombre de archivo xls
		
		//Borro errores anteriores
		//deleteOldError();
		
		MHRProcess hrprocess = (MHRProcess)this.getUY_HRProcess();//instancio la liquidacion
		
		Timestamp dateProcess = TimeUtil.trunc(hrprocess.getDateTrx(), TimeUtil.TRUNC_DAY);
		
		this.divideRate = MConversionRate.getDivideRate(142, 100, dateProcess, 0, this.getAD_Client_ID(), this.getAD_Org_ID());
		if(this.divideRate.compareTo(Env.ZERO)==0) throw new AdempiereException("No se encontro tasa de cambio para fecha de liquidacion");	
		
		String s=validacionXLSInicial();
		//Validacion inicial
		if(!(s.equals(""))){
			throw new AdempiereException (s);
		}
		
		this.readXLS();
									
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
		MHRLoadDriverLine line = null;
		int cantVacias = 0;
		

		for (int recorrido = 3; recorrido < tope; recorrido++) {
			
			message = "";
			
			String fechaVacio = null;
			String msgFechaVacio = "";
			
			String empleadoVacio = null;
			String msgEmpleadoVacio = "";
			
			String empleadoNoExiste = null;
			String msgEmpleadoNoExiste = "";
			
			String empleadoNoConductor = null;
			String msgEmpleadoNoConductor = "";
						
			String conceptoVacio = null;
			String msgConceptoVacio = "";	
			
			String conceptoNoExiste = null;
			String msgConceptoNoExiste = "";
			
			String conceptoNoTransporte = null;
			String msgConceptoNoTransporte = "";
						
			try {

				//Se lee Fecha en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				Timestamp fecha = null;
				String fch=utiles.getStringFromCell(cell);
				if(fch!=null){
					try{
						fch=fch.trim();
						fecha=utiles.formatStringDate(fch);
						String f = fecha.toString();
						if(f.substring(0,1).equalsIgnoreCase("0")) { //se controla que el año no comience con "0", debido al formato incorrecto de la celda
							MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
							message="Fecha con formato incorrecto";

						}

					}catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha con formato incorrecto",null);
						message="Fecha con formato incorrecto";
						fecha=null;
					}
				} else {
					fecha=null;
					message="Fecha vacia";
					fechaVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgFechaVacio = message;
				}
				//---------------------------------------------------------------
				
				//Se lee Codigo de Empleado en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				int partnerID = 0;
				String partner=utiles.getStringFromCell(cell);
				if(partner==null || partner.equalsIgnoreCase("")){
					partner=null;
					message="Codigo de empleado vacio";
					empleadoVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgEmpleadoVacio = message;
				} else {
						
					partner = partner.trim();
					
					//valido que exista el empleado
					MBPartner partnerAux = MBPartner.forValue(getCtx(), partner, get_TrxName());

					if(partnerAux != null){

						int driverID = partnerAux.get_ValueAsInt("UY_TR_Driver_ID");

						if(partnerAux.isEmployee() && driverID > 0 && partnerAux.isActive()){ //si es empleado, es conductor y esta activo

							partnerID = partnerAux.get_ID();						

						} else if (!partnerAux.isEmployee() || !partnerAux.isActive()){

							message="Codigo de empleado no existe o esta inactivo";
							empleadoNoExiste = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgEmpleadoNoExiste = message;	

						} else if (driverID <= 0){

							message="El empleado no es conductor";
							empleadoNoConductor = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgEmpleadoNoConductor = message;								
						}								

					} else {
						
						message="Codigo de empleado no existe";
						empleadoNoExiste = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
						msgEmpleadoNoExiste = message;						
					}										
				}
				//-----------------------------------------------------------------------------
			
				//Se lee Codigo de Concepto en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				int conceptID = 0;
				String concepto=utiles.getStringFromCell(cell);
				if(concepto==null || concepto.equalsIgnoreCase("")){
					concepto=null;
					message="Codigo de concepto vacio";
					conceptoVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgConceptoVacio = message;
				} else {
						
					concepto = concepto.trim();
					
					//valido que exista el proveedor
					MHRConcept conceptoAux = MHRConcept.forValue(getCtx(), concepto, get_TrxName());
					
					if(conceptoAux != null){
						
						if(conceptoAux.isTransport()){  //si es un concepto marcado como transporte padre
							
							conceptID = conceptoAux.get_ID();						
							
						} else {
							
							message="Concepto no es de tipo Transporte Padre";
							conceptoNoTransporte = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgConceptoNoTransporte = message;							
						}						
												
					} else {
						
						message="Codigo de concepto no existe";
						conceptoNoExiste = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
						msgConceptoNoExiste = message;						
					}										
				}
				//-----------------------------------------------------------------------------
					
				//Se lee Descripcion en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				String descripcion=utiles.getStringFromCell(cell);
				if(descripcion!=null && !descripcion.equalsIgnoreCase("")) {
					
					descripcion = descripcion.trim();
					
				} else {
					
					if(conceptID > 0){ //si tengo un concepto valido tomo la descripcion del mismo
						
						MHRConcept con = new MHRConcept(getCtx(),conceptID,null);
						
						descripcion = con.getDescription();					
						
					}
				}
				//-----------------------------------------------------------------------------		

				if(fecha!=null && partnerID > 0 && conceptID > 0 && message.equalsIgnoreCase("")){
					
					line = new MHRLoadDriverLine(getCtx(), 0, null);
					line.setUY_HRLoadDriver_ID(this.get_ID());
					line.setDateTrx(fecha);
					line.setC_BPartner_ID(partnerID);
					line.setHR_Concept_ID(conceptID);
					if(descripcion != null) line.setDescription(descripcion);
					line.saveEx(null);

				} 
				
				if(fecha!=null || partnerID > 0 || conceptID > 0){
					
					//si tengo al menos un campo No vacio entonces inserto todos los errores encontrados
					if(fechaVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), fechaVacio, "", msgFechaVacio,null);
					if(empleadoVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), empleadoVacio, "", msgEmpleadoVacio,null);
					if(conceptoVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), conceptoVacio, "", msgConceptoVacio,null);
						
					if(empleadoNoExiste!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), empleadoNoExiste, partner, msgEmpleadoNoExiste,null);
					if(conceptoNoExiste!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), conceptoNoExiste, concepto, msgConceptoNoExiste,null);
					
					if(conceptoNoTransporte!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), conceptoNoTransporte, concepto, msgConceptoNoTransporte,null);
					if(empleadoNoConductor!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), empleadoNoConductor, partner, msgEmpleadoNoConductor,null);
			
				}
				
				if(fecha==null && partnerID <= 0 && conceptID <= 0) cantVacias ++; //aumento contador de filas vacias
							
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
	
	/***
	 * Calcula y carga los campos de importe de otros conceptos para cada linea procesada.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 14/01/2014
	 * @see
	 * @return
	 */
	public void loadAmtFields(){
		
		String sql = "";
		
		List<MHRLoadDriverLine> lines = this.getLines(); //obtengo lista de lineas de esta carga

		for(MHRLoadDriverLine driverLine:lines){
			
			BigDecimal totalLine = Env.ZERO;

			MBPartner partner = new MBPartner(getCtx(),driverLine.getC_BPartner_ID(),get_TrxName()); //instancio empleado
			MHRConcept concept = new MHRConcept(getCtx(),driverLine.getHR_Concept_ID(),get_TrxName()); //instancio concepto

			//obtengo categoria del empleado, para poder consultar el cuadro de codigos
			sql = "select c.c_jobcategory_id" + 
					" from c_jobcategory c" +
					" inner join c_job j on c.c_jobcategory_id = j.c_jobcategory_id" +
					" inner join uy_bpremuneracion r on j.c_job_id = r.c_job_id" +
					" where r.c_bpartner_id = " + partner.get_ID();
			int jobCatID = DB.getSQLValueEx(get_TrxName(), sql);

			if(jobCatID <= 0) throw new AdempiereException("No se pudo obtener la categoria de posicion para el empleado '" + partner.getValue() + "'");

			MJobCategory category = new MJobCategory(getCtx(),jobCatID,get_TrxName()); //instancio la categoria de posicion 

			//obtengo el cuadro de codigos para el concepto y categoria de posicion
			MHRCuadroCodigo cuadro = MHRCuadroCodigo.forConceptCategory(getCtx(), driverLine.getHR_Concept_ID(), jobCatID, get_TrxName());

			if(cuadro==null) throw new AdempiereException("No se pudo obtener cuadro de codigos para el concepto '" + concept.getValue() + 
					"' y la categoria de posicion '" + category.getName() + "'");
			
			List<MHRCuadroCodigoLine> cLines = cuadro.getLines(); //obtengo lista de lineas del cuadro de codigos
			
			for(MHRCuadroCodigoLine cLine:cLines){ //recorro las lineas del cuadro de codigos

				if(cLine.getHR_Concept_ID()==1000001){ //si el concepto es JORNAL

					if(concept.getValue()!=null && concept.getValue().equalsIgnoreCase("9")){ //si el concepto es LICENCIA, debo asiganr el importe al campo importe de licencia, y no al jornal
						driverLine.setAmtLicencia(cLine.getTotalAmt());
						totalLine = totalLine.add(cLine.getTotalAmt());

					} else {
						driverLine.setAmtJornal(cLine.getTotalAmt());
						totalLine = totalLine.add(cLine.getTotalAmt());						
					}		
					
				} else if (cLine.getHR_Concept_ID()==1000002){ //si el concepto es HORAS EXTRA
					
					driverLine.setAmtHoraExtra(cLine.getTotalAmt());
					totalLine = totalLine.add(cLine.getTotalAmt());
					
				} else if(cLine.getHR_Concept_ID()==1000091){ //si el concepto es FERIADO BRASIL
					
					driverLine.setAmtFeriadoBrasil(cLine.getTotalAmt());
					totalLine = totalLine.add(cLine.getTotalAmt());
					
				} else if(cLine.getHR_Concept_ID()==1000092){ //si el concepto es VIATICO NACIONAL
					
					BigDecimal amt = cLine.getTotalAmt().multiply(this.divideRate);
					
					driverLine.setAmtViaticoNac(amt);
					totalLine = totalLine.add(amt);
					
				} else if(cLine.getHR_Concept_ID()==1000093){ //si el concepto es VIATICO EXTRANJERO
					
					driverLine.setAmtViaticoExt(cLine.getTotalAmt());
					totalLine = totalLine.add(cLine.getTotalAmt());
					
				}			
			}

			driverLine.setTotalLines(totalLine);
			driverLine.saveEx();			
		}	
		
		//seteo el campo JORNAS EXT.
		/*sql = "select coalesce(deduccionviatext,0)" +
	             " from uy_hrparametros" +
			     " where ad_client_id = " + this.getAD_Client_ID() +
			     " and ad_org_id = " + this.getAD_Org_ID();
		BigDecimal deduccion = DB.getSQLValueBDEx(get_TrxName(), sql);	
		
		if(deduccion.compareTo(Env.ZERO)<=0) throw new AdempiereException("El importe de deduccion para viatico extranjero en parametros de nomina debe ser mayor a cero");		
		
		sql = "select coalesce(viaticoextranjero,0)" +
		      " from uy_hrparametros" +
			  " where ad_client_id = " + this.getAD_Client_ID() +
			  " and ad_org_id = " + this.getAD_Org_ID();
		BigDecimal amtVExt = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		//OpenUp. Nicolas Sarlabos. 24/11/2014. #3214.
		BigDecimal totalJornasExt = amtVExt.subtract(deduccion.multiply(this.getCurrencyRate()));
		//Fin #3214.
		
		if(totalJornasExt.compareTo(Env.ZERO) > 0){
			
			sql = "update uy_hrloaddriverline" +
			      " set amtjornalext = " + totalJornasExt +
			      " where amtviaticoext > 0 and uy_hrloaddriver_id = " + this.get_ID();
					
			DB.executeUpdate(sql, get_TrxName());			
		}*/		
	}
	
	/***
	 * Metodo que calcula y carga las novedades de nomina para cada empleado y liquidacion del cabezal.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 17/01/2014
	 * @see
	 * @return
	 */
	public void loadNews(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		MHRNovedades hdr = null;
		int partnerID = 0;
		int partnerAuxID = 0;
		
		List<MHRConcept> transConcept = MHRConcept.getTransportConcept(); //obtengo lista de conceptos "transporte padre"

		try {
			//obtengo empleados a recorrer y hacer corte de control
			sql = "select distinct c_bpartner_id from uy_hrloaddriverline where uy_hrloaddriver_id = " + this.get_ID() +
					" order by c_bpartner_id";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				partnerAuxID = rs.getInt("c_bpartner_id");

				// Si hay cambio de empleado
				if (partnerAuxID != partnerID){

					partnerID = partnerAuxID;

					//obtengo cabezal de novedades si existe
					hdr = MHRNovedades.forProcessEmployee(partnerID, this.getUY_HRProcess_ID(), get_TrxName());

					//si no existe cabezal de novedades creo uno nuevo
					if(hdr == null){

						MDocType doc = MDocType.forValue(getCtx(), "novedadnom", get_TrxName());

						if(doc==null) throw new AdempiereException("Error al obtener documento Novedad Nomina");

						hdr = new MHRNovedades(getCtx(),0,get_TrxName());
						hdr.setC_BPartner_ID(partnerID);
						hdr.setUY_HRProcess_ID(this.getUY_HRProcess_ID());
						hdr.setC_DocType_ID(doc.get_ID());
						hdr.setUY_HRLoadDriver_ID(this.get_ID());
						hdr.saveEx();
						
					} else {
						
						hdr.setUY_HRLoadDriver_ID(this.get_ID());
						hdr.saveEx();
						
					}

				}

				//Creo linea de novedades para concepto JORNALES TRABAJADOS
				this.generateNew(hdr,partnerID,"amtjornal","7001","Jornales Trabajados");
				
				//Creo linea de novedades para concepto HORAS EXTRA
				this.generateNew(hdr,partnerID,"amthoraextra","7004","Cantidad Horas Extras");
				
				//Creo linea de novedades para concepto CANTIDAD VIATICO NACIONAL
				this.generateNew(hdr,partnerID,"amtviaticonac","300","Cantidad Viatico Nacional");
				
				//Creo linea de novedades para concepto CANTIDAD VIATICO EXTRANJERO
				this.generateNew(hdr,partnerID,"amtviaticoext","310","Cantidad Viatico Extranjero");
				
				//Creo linea de novedades para concepto CANTIDAD FERIADO BRASIL
				this.generateNew(hdr,partnerID,"amtferiadobrasil","320","Cantidad Feriado Brasil");
				
				//Creo linea de novedades para concepto CANTIDAD DIAS LICENCIA
				this.generateNew(hdr,partnerID,"amtlicencia","7009","Cantidad Dias Licencia");
				
				//Creo linea de novedades para concepto CANTIDAD DIAS LIBRES
				this.generateNew(hdr,partnerID,"","330","Cantidad Dias Libres");
				
				//recorro lista de conceptos transporte padre y genero lineas de novedad
				for(MHRConcept con:transConcept){
					
					sql = "select count(hr_concept_id)" +
					      " from uy_hrloaddriverline" +
						  " where c_bpartner_id = " + partnerID +
						  " and hr_concept_id = " + con.get_ID() +
						  " and uy_hrloaddriver_id = " + this.get_ID();
					
					int count = DB.getSQLValueEx(get_TrxName(), sql);
					
					if(count > 0) {
						
						//obtengo linea de novedad si existe
						MHRConceptoLine line = MHRConceptoLine.forProcessConcept(partnerID, con.get_ID(), this.getUY_HRProcess_ID(), get_TrxName());
						
						//si no existe una linea para este concepto, empleado y liquidacion, creo una nueva
						//si ya existe linea la actualizo
						if(line == null){

							line = new MHRConceptoLine(getCtx(),0,get_TrxName());
							line.setUY_HRNovedades_ID(hdr.get_ID());
							line.setHR_Concept_ID(con.get_ID());
							line.setQty(new BigDecimal(count));
							line.setUY_HRLoadDriver_ID(this.get_ID());
							line.saveEx();

						} else {

							line.setQty(new BigDecimal(count));
							line.setUY_HRLoadDriver_ID(this.get_ID());
							line.saveEx();						
						}						
										
					}
					
				}
				
			}		

		} catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}
	
	/***
	 * Metodo que crea o actualiza una linea de novedad segun los parametros recibidos.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 17/01/2014
	 * @see
	 * @return
	 */
	public void generateNew(MHRNovedades hdr, int partnerID, String column, String valueConcept, String nameConcept){
		
		String sql = "";
		MHRConcept concept = null;
		MHRConceptoLine line = null;
		
		if(!column.equalsIgnoreCase("") && !valueConcept.equalsIgnoreCase("330")){
			
			sql = "select count(uy_hrloaddriverline_id)" +
					" from uy_hrloaddriverline" +
					" where " + column + " > 0" +
					" and uy_hrloaddriver_id = " + this.get_ID() +
					" and c_bpartner_id = " + partnerID;		
			
		} else {
			
			sql = "select count(uy_hrloaddriverline_id)" +
					" from uy_hrloaddriverline" +
					" where amtlicencia <= 0 and amtjornal <= 0 and amtferiadobrasil <= 0 " +
					" and amthoraextra <= 0 and amtviaticonac <= 0 and amtviaticoext <= 0 and amtjornalext <= 0" +
					" and uy_hrloaddriver_id = " + this.get_ID() +
					" and c_bpartner_id = " + partnerID;		
			
		}
		
		int count = DB.getSQLValueEx(get_TrxName(), sql);

		if(count > 0){

			concept = MHRConcept.forValue(getCtx(), valueConcept, get_TrxName());

			if(concept!=null){			

				//obtengo linea de novedad si existe
				line = MHRConceptoLine.forProcessConcept(partnerID, concept.get_ID(), this.getUY_HRProcess_ID(), get_TrxName());

				//si no existe una linea para este concepto, empleado y liquidacion, creo una nueva
				//si ya existe linea la actualizo
				if(line == null){

					line = new MHRConceptoLine(getCtx(),0,get_TrxName());
					line.setUY_HRNovedades_ID(hdr.get_ID());
					line.setHR_Concept_ID(concept.get_ID());
					line.setQty(new BigDecimal(count));
					line.setUY_HRLoadDriver_ID(this.get_ID());
					line.saveEx();

				} else {

					line.setQty(new BigDecimal(count));
					line.setUY_HRLoadDriver_ID(this.get_ID());
					line.saveEx();						
				}						

			} else throw new AdempiereException("Error al obtener concepto '" + valueConcept + " - " + nameConcept + "'");					

		}	
		
	}
	
	public boolean hayErrores(){
		
		String sql = "select uy_xlsissue_id from uy_xlsissue where ad_table_id = " + get_Table_ID() + " and record_id = " + this.get_ID();
		int errors = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(errors > 0) return true;
		
		return false;	
		
	}
	
	/***
	 * Retorna lineas procesadas de esta carga ordenadas por empleado.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 14/01/2014
	 * @see
	 * @return
	 */
	public List<MHRLoadDriverLine> getLines(){
		
		String whereClause = X_UY_HRLoadDriverLine.COLUMNNAME_UY_HRLoadDriver_ID + "=" + this.get_ID();
		
		List<MHRLoadDriverLine> lines = new Query(getCtx(), I_UY_HRLoadDriverLine.Table_Name, whereClause, get_TrxName()).setOrderBy(X_UY_HRLoadDriverLine.COLUMNNAME_C_BPartner_ID).list();
		
		return lines;
		
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
		
		this.loadNews(); //cargo novedades de nomina
		
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

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		this.validateVoid(); //valido antes de anular
		
		DB.executeUpdateEx("delete from uy_hrconceptoline where uy_hrloaddriver_id = " + this.get_ID(), get_TrxName()); //elimino lineas de novedades cargadas por este documento
			
		this.updateNews();
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;		
		
		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		
		return true;
	}
	
	/***
	 * Metodo que actualiza los cabezales de novedades de nomina generados 
	 * por el documento actual, al anularse el mismo.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 23/01/2014
	 * @see
	 * @return
	 */
	private void updateNews() {
		
		MHRNovedades hdr = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try {
			
			String sql = "select uy_hrnovedades_id from uy_hrnovedades where uy_hrloaddriver_id = " + this.get_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				hdr = new MHRNovedades(getCtx(),rs.getInt("uy_hrnovedades_id"),get_TrxName());
				
				//obtengo lista de lineas de la novedad que fueron generadas por esta carga de planilla
				List<MHRConceptoLine> conLines = hdr.getLines(" and uy_hrloaddriver_id = " + this.get_ID());
				
				if(conLines.size() <= 0) {
					
					hdr.setUY_HRLoadDriver_ID(0);
					hdr.saveEx();
				}			
				
			}	
		
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
	}

	/***
	 * Metodo que valida la anulacion del documento actual.
	 * OpenUp Ltda. Issue #1758
	 * @author Nicolas Sarlabos - 21/01/2014
	 * @see
	 * @return
	 */
	private void validateVoid() {

		String sql = "";

		MHRProcess process = new MHRProcess(getCtx(),this.getUY_HRProcess_ID(),get_TrxName());

		sql = "select uy_hrprocesonomina_id" +
				" from uy_hrprocesonomina" +
				" where uy_hrprocess_id = " + this.getUY_HRProcess_ID() +
				" and ad_client_id = " + this.getAD_Client_ID() +
				" and ad_org_id = " + this.getAD_Org_ID() +
				" and docstatus = 'CO'";

		int processID = DB.getSQLValueEx(get_TrxName(), sql);

		if(processID > 0) throw new AdempiereException("Imposible anular, existe una liquidacion de nomina en estado completo para la liquidacion '" + process.getName() + "'");

	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
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
