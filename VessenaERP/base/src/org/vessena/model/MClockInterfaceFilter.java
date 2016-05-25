package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MRemuneration;
import org.compiere.model.MSequence;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.MHRPeriod;

/**
 * 
 * OpenUp.   issue #776
 * MManageClockFilter
 * Descripcion :
 * @author Nicolas Sarlabos
 * Fecha : 12/07/2011
 */
public class MClockInterfaceFilter extends X_UY_ClockInterface_Filter implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;
	
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private static final String ENTRADA = "E";
	private static final String SALIDA_INTERNA = "SE";
	private static final String ENTRADA_INTERNA = "EE";
	private static final String SALIDA = "S";
	
	private static final long serialVersionUID = 986866045115613632L;

	private ArrayList<MClockInterfaceDetail> marcas = new ArrayList<MClockInterfaceDetail>();
	
	private boolean tengoMarcaEntrada = false;
	private boolean tengoMarcaEntradaInterna = false;
	private boolean tengoMarcaSalida = false;
	private boolean tengoMarcaSalidaInterna = false;

	private boolean aprobacion = true;
	private String observaciones = "";
	
	//VARIABLES PARA CALCULOS DE CONCEPTOS
	private long totalMinutosTrabajados = 0; //almacena el total de minutos trabajados
	private int minutosTrabajadosDiarios = 0; //almacena el minutos trabajados diarios
	private int totalDiasTrabajados = 0; //almacena el total de dias trabajados
	private long minutosExtraDiarios = 0; //almacena cantidad de minutos extra por dia
	private long minutosExtraTotales = 0; //almacena cantidad de minutos extra totales del empleado
	private long totalMinutosNocturnos = 0; //almacena el total de minutos nocturnos
	private long minutosExtraNocturnos = 0; //almacena cantidad de minutos extra noturnos del empleado
	private int minsExtraDesde = 0; //almacena el parametro de nomina del mismo nombre
	private int minsExtraHasta = 0; //almacena el parametro de nomina del mismo nombre
	private int minsExtraEquiv = 0; //almacena el parametro de nomina del mismo nombre
	private Timestamp horaTeoEntrada = null; //almacena la hora teorica de entrada
	private Timestamp horaTeoSalida = null; //almacena la hora teorica de salida
	private BigDecimal horasJornada = Env.ZERO; //almacena las horas de jornada laboral
	private Timestamp iniHoraNocturna = null; //almacena el parametro de nomina del mismo nombre
	private Timestamp finHoraNocturna = null; //almacena el parametro de nomina del mismo nombre
	private int minutosDescanso = 0; //almacena los minutos de descanso
	private int minsDesHorarioCorrido = 0; //almacena los minutos de descanso para el horario corrido
	private long totalMinutosNoTrabajados = 0; ////almacena total de minutos no trabajados
	private int minutosNoTrabajadosDiarios = 0; //almacena el minutos no trabajados diarios
	private int totalInasistencias = 0; //almacena el total de inasistencias del empleado
	private Timestamp horaEntrada = null;
	private Timestamp horaSalidaInt = null;
	private Timestamp horaEntradaInt = null;
	private Timestamp horaSalida = null;
	
	/**
	 * Constructor
	 * @param ctx
	 * @param UY_UpdOrder_Filter_ID
	 * @param trxName
	 */
	public MClockInterfaceFilter(Properties ctx, int UY_ClockInterface_Filter_ID,
			String trxName) {
		super(ctx, UY_ClockInterface_Filter_ID, trxName);
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MClockInterfaceFilter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

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

		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Verifico que haya lineas de marcas
		if (this.getDetailLines().size() <= 0){
			this.processMsg = "No hay lineas de Marcas. No se puede completar la transaccion.";
			return DocAction.STATUS_Invalid;
		}
		
		// Verifica marcas aprobadas por el usuario (incluye agregadas manualmente) 
		this.verify(true);
		
		// Guarda marcas procesadas
		this.processMarks();
		
		// Genera novedades
		this.generarNovedades();

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
	
	/**
	 * Metodo que genera novedades de nomina de reloj
	 * OpenUp Ltda. Issue # 
	 * @author Nicolas Sarlabos - 11/05/2012
	 * @see
	 */

	private void generarNovedades() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int processID = this.getUY_HRProcess_ID();
		MBPartner partner = null;
		MHRNovedades novedad = null;
		MRemuneration remuneration = null;
		MHRParametros param = null;
				
		try{
			
			//obtengo ID del tipo de documento Novedad Nomina
			sql = "SELECT c_doctype.c_doctype_id FROM c_doctype WHERE docbasetype='HRN'";
			int docID = DB.getSQLValueEx(get_TrxName(), sql); 
			
			//obtengo datos de parametros generales de nomina
			param = MHRParametros.forClient(this.getAD_Client_ID(),get_TrxName());
			
			if(param != null){
				this.minsExtraDesde = param.getminsExtraDesde();
				this.minsExtraHasta = param.getminsExtraHasta();
				this.minsExtraEquiv = param.getminsequivextra();
				this.minsDesHorarioCorrido = param.getminsDesHorarioCorrido();
				this.iniHoraNocturna = param.getinihoranocturna();
				this.finHoraNocturna = param.getfinhoranocturna();
			
			} else throw new AdempiereException ("No se obtuvo informacion de Parametros Generales de Nomina para la empresa actual");
			
			//obtengo las marcas procesadas en este documento
			sql = " SELECT uy_marcasrelojprocesadas_id, c_bpartner_id, t.value, fechahora " +
					" FROM uy_marcasrelojprocesadas p " + 
					" INNER JOIN uy_tipomarcareloj t ON p.uy_tipomarcareloj_id=t.uy_tipomarcareloj_id " +
					" WHERE uy_clockinterface_filter_id=? " +
					" ORDER BY c_bpartner_id, fechahora, t.value ";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());
						
			int cBpartnerID = 0;	
			rs = pstmt.executeQuery ();

			while(rs.next()){
				
				// Corte por empleado
				if (rs.getInt("c_bpartner_id") != cBpartnerID){
															
					//si ya tengo minutos trabajados llamo a metodo para generar o actualizar la linea de novedad del concepto
					if (this.totalMinutosTrabajados > 0){
						this.crearLineasNovedades(novedad,remuneration); //creo lineas de novedades
						this.initCicloCargaNovedades(); //llamo a metodo para resetear variables
					}
					
					//si existe cabezal de novedades para este empleado y liquidacion, entonces lo utilizo
					novedad = MHRNovedades.forProcessEmployee(rs.getInt("c_bpartner_id"),processID,get_TrxName());

					//si no se encontro cabezal de novedades para este empleado y liquidacion, entonces lo creo ahora
					if(novedad == null){
						novedad = new MHRNovedades(getCtx(),0,get_TrxName()); 
						novedad.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
						novedad.setUY_HRProcess_ID(processID);
						novedad.setC_DocType_ID(docID);
						novedad.saveEx();
					}
					
					partner = new MBPartner(getCtx(),novedad.getC_BPartner_ID(),get_TrxName()); //instancio el empleado
					
					this.getHorasTeoricas(novedad.getC_BPartner_ID()); //obtengo las horas de entrada y salida teoricas del empleado actual
					
					if(this.horaTeoEntrada == null) throw new AdempiereException ("El empleado " + partner.getValue() + " - " + partner.getName() + " no tiene hora de entrada ingresada");
					if(this.horaTeoSalida == null) throw new AdempiereException ("El empleado " + partner.getValue() + " - " + partner.getName() + " no tiene hora de salida ingresada");
					
					//obtengo datos del tipo de remuneracion...
					remuneration = this.getTipoRemuneracion(novedad.getC_BPartner_ID(),get_TrxName());
										 
					if(remuneration != null){
						
						this.horasJornada = remuneration.getStandardHours(); //seteo las horas de jornada laboral
						
						if(this.horasJornada.compareTo(Env.ZERO) <= 0) throw new AdempiereException ("No se ha ingresado las horas de jornada para tipo de remuneracion " + remuneration.getName());
						
						if(remuneration.gettipo_horario().equalsIgnoreCase("CRT")){
							this.minutosDescanso = remuneration.getbreakminutes(); //seteo los minutos de descanso si el horario es cortado
						}else this.minutosDescanso = this.minsDesHorarioCorrido; //si el horario es corrido se toma el valor del parametro gral. de nomina
									
					} else throw new AdempiereException ("El empleado " + partner.getValue() + " - " + partner.getName() + " no tiene tipo de remuneracion asociado");
														
					cBpartnerID = rs.getInt("c_bpartner_id"); //cargo nuevo ID de empleado a recorrer
								
				}
				
				// Obtengo tipo de marca
				String tipoMarca = rs.getString("value");
				
				//si es marca de ENTRADA solamente la guardo en memoria y verifico
				if(tipoMarca.equalsIgnoreCase(ENTRADA)){
					
					this.minutosExtraDiarios = 0; //reinicio variable de minutos extra diarios
					this.minutosTrabajadosDiarios = 0; //reinicio variable de minutos trabajados diarios
					this.minutosNoTrabajadosDiarios = 0; //reinicio variable de minutos no trabajados diarios

					this.horaEntrada = rs.getTimestamp("fechahora");
					
					Timestamp iniNocturno = TimeUtil.copyDate(this.iniHoraNocturna,this.horaEntrada);
					long iniNoct = iniNocturno.getTime();
					//Timestamp finNocturno = TimeUtil.copyDate(this.finHoraNocturna,this.horaSalida);
					
					//verifico contra la hora teorica de entrada
					long entradaReal = this.horaEntrada.getTime();
										
					//como el campo horaTeoEntrada posee fecha 1970-01-01 entonces tengo que crear una nueva fecha Timestamp que 
					//contenga la fecha del campo horaEntrada + la hora del campo horaTeoEntrada, y comparar contra esta 
					Timestamp entradaTeo = TimeUtil.copyDate(this.horaTeoEntrada, this.horaEntrada);
					long newEntradaTeo = entradaTeo.getTime();
					
					long resta = newEntradaTeo - entradaReal;
					if(resta/(1000*60) > 360){
						newEntradaTeo = TimeUtil.addDaysWithHour(entradaTeo,-1).getTime();
					}
															
					if(entradaReal < newEntradaTeo){  //si el empleado entro antes de su hora teorica de ingreso
						
						long diferencia = newEntradaTeo - entradaReal;
						int minutos = (int) (diferencia / (1000 * 60));
						//permito un maximo a sumar de 360 minutos extra (6 horas)
						if(minutos <= 360){
							
							double horasExtra = new Double(minutos)/60; //obtengo horas extra
							int horasEnteras = (int) horasExtra; //obtengo cantidad entera de horas

							BigDecimal fraccion = (new BigDecimal(horasExtra - horasEnteras).multiply(new BigDecimal(60))).setScale(0,RoundingMode.HALF_UP);

							if(fraccion.compareTo(Env.ZERO) > 0){
								if(fraccion.compareTo(new BigDecimal(this.minsExtraDesde)) >= 0 && fraccion.compareTo(new BigDecimal(this.minsExtraHasta)) <= 0){

									//determino si corresponde a horas extra nocturna o comun
									if(this.horaEntrada.compareTo(iniNocturno) >= 0){

										this.minutosExtraNocturnos = this.minutosExtraNocturnos + this.minsExtraEquiv;

									} else {

										this.minutosExtraDiarios = this.minutosExtraDiarios + this.minsExtraEquiv;

									}

								} else if(fraccion.compareTo(new BigDecimal(this.minsExtraHasta)) > 0){

									if(this.horaEntrada.compareTo(iniNocturno) >= 0){

										this.minutosExtraNocturnos = this.minutosExtraNocturnos + 60;

									} else {

										this.minutosExtraDiarios = this.minutosExtraDiarios + 60;

									}

								}
							}

							if(horasEnteras > 0){
								if(this.horaEntrada.compareTo(iniNocturno) >= 0){ //acumulo minutos extra de las horas enteras

									this.minutosExtraNocturnos = this.minutosExtraNocturnos + (horasEnteras * 60);
									
								} else if(newEntradaTeo <= iniNoct ) {
									
									this.minutosExtraDiarios = this.minutosExtraDiarios + (horasEnteras * 60);
									

								} else {

									long fraccionLong = 0;
									long diff = newEntradaTeo - iniNoct;
									this.minutosExtraNocturnos = this.minutosExtraNocturnos + (diff/(1000*60)); //acumulo las horas extra nocturnas

									long d = iniNoct - entradaReal;
									if(fraccion.compareTo(Env.ZERO) > 0) fraccionLong = (fraccion).longValue();
									this.minutosExtraDiarios = this.minutosExtraDiarios + (d/(1000*60)) - fraccionLong; //acumulo minutos extra diarios
								

								}
							}	
							

						} else if(minutos > 360){ //acumulo el maximo (360 minutos) de minutos extra

							if(this.horaEntrada.compareTo(iniNocturno) >= 0){

								this.minutosExtraNocturnos = this.minutosExtraNocturnos + 360;

							} else {
								
								this.minutosExtraDiarios = this.minutosExtraDiarios + 360;
								
							}
						}

						this.horaEntrada = TimeUtil.copyDate(this.horaTeoEntrada, this.horaEntrada); //si marco entrada antes de hora, entonces se toma la hora teorica de entrada
										
					} else if(entradaReal > newEntradaTeo){  //si el empleado entro pasada su hora teorica de ingreso
						
						long diferencia = entradaReal - newEntradaTeo;
						this.minutosNoTrabajadosDiarios = (int) (this.minutosNoTrabajadosDiarios + (diferencia / (1000 * 60))); //sumo al contador de minutos NO trabajados diario 
												
					} 
							
				}
				
				//si es marca de SALIDA INTERNA almaceno la cantidad de minutos, haciendo la diferencia entre
				//la SALIDA INTERNA y ENTRADA
				if(tipoMarca.equalsIgnoreCase(SALIDA_INTERNA)){
					
					this.horaSalidaInt = rs.getTimestamp("fechahora");
					
					long entrada = this.horaEntrada.getTime();
					long salidaInt = rs.getTimestamp("fechahora").getTime();
					
					long diferencia = salidaInt - entrada;
					this.minutosTrabajadosDiarios = (int) (this.minutosTrabajadosDiarios + (diferencia / (1000 * 60))); //sumo al contador de minutos trabajados diarios
													
				}
				
				//si es marca de ENTRADA INTERNA
				if(tipoMarca.equalsIgnoreCase(ENTRADA_INTERNA)){
					
					this.horaEntradaInt = rs.getTimestamp("fechahora");
					
					long entradaInt = this.horaEntradaInt.getTime();
					long salidaInt = this.horaSalidaInt.getTime();
					long diferencia = entradaInt - salidaInt;
					
					//si el horario es corrido el tiempo de descanso es PAGO, por lo tanto controlo y sumo/resto horas 
					if(remuneration.gettipo_horario().equalsIgnoreCase("CRR")){
												
						if((diferencia / (1000 * 60)) <= this.minutosDescanso){ //si no se excedio de su tiempo de descanso...
							
							this.minutosTrabajadosDiarios = (int) (this.minutosTrabajadosDiarios + this.minutosDescanso); //sumo al contador de minutos trabajados diarios
							this.horaEntradaInt = TimeUtil.addMinutess(this.horaSalidaInt,this.minutosDescanso);
							
						} else {  //si se excedio de su tiempo de descanso...
							
							int descuento = (int) (diferencia / (1000 * 60)) - this.minutosDescanso;
							this.minutosNoTrabajadosDiarios = (int) (this.minutosNoTrabajadosDiarios + descuento); //sumo al contador de minutos NO trabajados diarios
							this.minutosTrabajadosDiarios =  this.minutosTrabajadosDiarios + this.minutosDescanso;
																				
						}
										
					} else { //si el horario es cortado el tiempo de descanso es NO PAGO, por lo tanto controlo y resto horas
																	
						if((diferencia / (1000 * 60)) > this.minutosDescanso){ //si se excedio de su tiempo de descanso...
						
							int descuento = (int) (diferencia / (1000 * 60)) - this.minutosDescanso;
							this.minutosNoTrabajadosDiarios = this.minutosNoTrabajadosDiarios + descuento + this.minutosDescanso;
																								
						} else if((diferencia / (1000 * 60)) <= this.minutosDescanso){ //si descanso menos o igual que el tiempo correspondiente..
							
							this.minutosNoTrabajadosDiarios = this.minutosNoTrabajadosDiarios + this.minutosDescanso;
							this.horaEntradaInt = TimeUtil.addMinutess(this.horaSalidaInt,this.minutosDescanso);
							
						}
										
					}
								
				}
				
				//si es marca de SALIDA almaceno la cantidad de minutos, haciendo la diferencia entre
				//la SALIDA y ENTRADA INTERNA
				if(tipoMarca.equalsIgnoreCase(SALIDA)){
					
					this.totalDiasTrabajados += 1; //sumo 1 dia de trabajo
					
					this.horaSalida = rs.getTimestamp("fechahora");
					
					Timestamp iniNocturno = TimeUtil.copyDate(this.iniHoraNocturna,this.horaEntrada);
					Timestamp finNocturno = TimeUtil.copyDate(this.finHoraNocturna,this.horaSalida);
					long finNoct = finNocturno.getTime();
					long iniNoct = iniNocturno.getTime();
										
					long entradaInt = this.horaEntradaInt.getTime();
					long salidaReal = rs.getTimestamp("fechahora").getTime();
					
					//como el campo horaTeoSalida posee fecha 1970-01-01 entonces tengo que crear una nueva fecha Timestamp que 
					//contenga la fecha del campo horaSalida + la hora del campo horaTeoSalida, y comparar contra esta 
					long newSalidaTeo = TimeUtil.copyDate(this.horaTeoSalida, this.horaSalida).getTime();
										
					/*long resta = newEntradaTeo - entradaReal;
					if(resta/(1000*60) > 360){
						newEntradaTeo = TimeUtil.addDaysWithHour(entradaTeo,-1).getTime();
					}*/
					
					if(salidaReal > newSalidaTeo){  //si el empleado salio despues de su hora teorica de salida
						
						this.horaSalida  = TimeUtil.copyDate(this.horaTeoSalida, this.horaSalida); //si marco salida despues de hora, entonces se toma la hora teorica de salida
						
						long dif = salidaReal - newSalidaTeo;
						long salida = this.horaSalida.getTime();
											
						long diferencia = salida - entradaInt;
						this.minutosTrabajadosDiarios = (int) (this.minutosTrabajadosDiarios + (diferencia / (1000 * 60))); //sumo al contador de minutos trabajados diarios
																					
						//permito un maximo a sumar de 360 minutos extra (6 horas)
						long minutos = (dif / (1000 * 60));
						
						if(minutos <= 360){
							
							double horasExtra = new Double(minutos)/60; //obtengo horas extra
							int horasEnteras = (int) horasExtra; //obtengo cantidad entera de horas
							
							BigDecimal fraccion = (new BigDecimal(horasExtra - horasEnteras).multiply(new BigDecimal(60))).setScale(0,RoundingMode.HALF_UP);
							
							//acumulo minutos correspondientes a la fraccion	
							if(fraccion.compareTo(Env.ZERO) > 0){
								if(fraccion.compareTo(new BigDecimal(this.minsExtraDesde)) >= 0 && fraccion.compareTo(new BigDecimal(this.minsExtraHasta)) <= 0){

									//determino si corresponde a horas extra nocturna o comun
									if(salidaReal <= finNocturno.getTime()){

										this.minutosExtraNocturnos = this.minutosExtraNocturnos + this.minsExtraEquiv;
									
									} else {

										this.minutosExtraDiarios = this.minutosExtraDiarios + this.minsExtraEquiv;
									
									}

								} else if(fraccion.compareTo(new BigDecimal(this.minsExtraHasta)) > 0){

									if(this.horaSalida.compareTo(finNocturno) <= 0){

										this.minutosExtraNocturnos = this.minutosExtraNocturnos + 60;
									
									} else { 

										this.minutosExtraDiarios = this.minutosExtraDiarios + 60;
									
									}

								}
							}
							
							//acumulo minutos correspondientes a la parte entera
							if(horasEnteras > 0){
								if(salidaReal <= finNocturno.getTime()){

									this.minutosExtraNocturnos = this.minutosExtraNocturnos + (horasEnteras * 60);

								}  else if(newSalidaTeo >= finNoct ) {
									
									this.minutosExtraDiarios = this.minutosExtraDiarios + (horasEnteras * 60);
								
								} else {

									long fraccionLong = 0;
									long diff = finNoct - newSalidaTeo;
									this.minutosExtraNocturnos = this.minutosExtraNocturnos + (diff/(1000*60)); //acumulo las horas extra nocturnas

									long d = salidaReal - finNoct;
									if(fraccion.compareTo(Env.ZERO) > 0) fraccionLong = (fraccion).longValue();
									this.minutosExtraDiarios = this.minutosExtraDiarios + (d/(1000*60)) - fraccionLong; //acumulo minutos extra diarios

								}
							}
													
						} else if(minutos > 360){ //acumulo el maximo (360 minutos) de minutos extra
							
							if(this.horaSalida.compareTo(finNocturno) <= 0){
								
								this.minutosExtraNocturnos = this.minutosExtraNocturnos + 360;
							
							} else {
								
								this.minutosExtraDiarios = this.minutosExtraDiarios + 360;
								
							}
						}
						
												
					} else if(salidaReal < newSalidaTeo){  //si el empleado salio antes de su hora teorica de salida
									
						long dif = newSalidaTeo - salidaReal; //diferencia entre salida teorica y salida real
						//long salida = this.horaSalida.getTime();
						long diff =  salidaReal - entradaInt; //diferencia entre salida REAL y entrada interna
												
						this.minutosTrabajadosDiarios = (int) (this.minutosTrabajadosDiarios + (diff / (1000 * 60))); //sumo al contador de minutos trabajados diario
						this.minutosNoTrabajadosDiarios = (int) (this.minutosNoTrabajadosDiarios + (dif / (1000 * 60))); //sumo al contador de minutos NO trabajados diario
												
					} else if(salidaReal == newSalidaTeo){ //si el empleado salio en hora
						
						long salida = this.horaSalida.getTime();
						long diff =  salida - entradaInt; //diferencia entre salida REAL y entrada interna
						this.minutosTrabajadosDiarios = (int) (this.minutosTrabajadosDiarios + (diff / (1000 * 60))); //sumo al contador de minutos trabajados diario
						
					}
					
					//controlo horas nocturnas
					long entrada = this.horaEntrada.getTime();
					long salida = this.horaSalida.getTime();
					
					long d = iniNoct - entrada;
										
					if((d/(1000*60)) > 360){
											
						iniNocturno = TimeUtil.addDaysWithHour(iniNocturno,-1);
					}
				
					if (this.horaEntrada.compareTo(iniNocturno) <= 0 && this.horaSalida.compareTo(finNocturno) <= 0){
						
						long diff = salida - entrada;
						long difHoras = iniNocturno.getTime() - entrada; 
						this.totalMinutosNocturnos = (this.totalMinutosNocturnos + (diff/(1000*60))) - (difHoras/(1000*60));
						
					} else if(this.horaEntrada.compareTo(iniNocturno) >= 0 && this.horaSalida.compareTo(finNocturno) >= 0){
						
						long diff = finNocturno.getTime() - entrada;
						this.totalMinutosNocturnos = (this.totalMinutosNocturnos + (diff/(1000*60)));
										
					}
					
					this.totalMinutosTrabajados = this.totalMinutosTrabajados + this.minutosTrabajadosDiarios; //acumulo en total de minutos trabajados del empleado
					this.totalMinutosNoTrabajados = this.totalMinutosNoTrabajados + this.minutosNoTrabajadosDiarios; //acumulo en total de minutos NO trabajados del empleado
					this.minutosExtraTotales = this.minutosExtraTotales + this.minutosExtraDiarios; //acumulo en total de minutos extras del empleado
					this.controlDiario(remuneration); //invoco metodo para controlar posibles errores de aproximacion en los calculos
					
				}
						
			}
			
			//Fin del loop, me aseguro procesar el ultimo empleado
			if (this.totalMinutosTrabajados > 0){
				this.crearLineasNovedades(novedad,remuneration); //creo lineas de novedades
			}
					
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}
	
	/**
	 * Reinicia valores de variables
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 11/05/2012
	 * @see
	 */
	
	private void initCicloCargaNovedades(){
		totalMinutosTrabajados = 0;
		totalMinutosNoTrabajados = 0;
		totalDiasTrabajados = 0;
		totalMinutosNocturnos = 0;
		minutosExtraNocturnos = 0;
		minutosExtraTotales = 0;
		totalInasistencias = 0;
		//horaTeoEntrada = null;
		//horaTeoSalida = null;
		horaEntrada = null;
		horaSalidaInt = null;
		horaEntradaInt = null;
		horaSalida = null;
	
	}
	
	/**
	 * 
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 11/05/2012
	 * @see
	 */
	
	private void crearLineasNovedades(MHRNovedades novedad,MRemuneration rem){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MHRConceptoLine conceptoLine = null;
		MHRRemunerationGroup remGroup = null;
		String groupName = "";
		
		if(novedad != null && rem != null){
			
			try{

				remGroup = new MHRRemunerationGroup(getCtx(),rem.getUY_HRRemunerationGroup_ID(),get_TrxName()); //instancio el grupo de remuneracion
				groupName = remGroup.getValue();

				//obtengo ID de conceptos de interfaz de reloj
				sql = "SELECT hr_concept_diastrab_id,hr_concept_hrstrab_id,hr_concept_hrsextra_id,hr_concept_hrsnoct_id,hr_concept_hrsnotrab_id,hr_concept_inasist_id,hr_concept_hrsextranoct_id " + 
						" FROM uy_hrconceptoreloj cr " +
						" INNER JOIN uy_hrparametros p ON cr.uy_hrparametros_id=p.uy_hrparametros_id " +
						" WHERE p.isactive='Y' AND p.ad_client_id=? ";

				pstmt = DB.prepareStatement (sql, get_TrxName());
				pstmt.setInt(1, this.getAD_Client_ID());
				rs = pstmt.executeQuery ();

				//cargo las novedades en caso que correspondan
				if(rs.next()){

					//PARA DIAS TRABAJADOS
					if(groupName.equalsIgnoreCase("Jornalero")){
						if(this.totalDiasTrabajados > 0){

							int idConceptoDiasTrabajados = rs.getInt("hr_concept_diastrab_id");
							conceptoLine = MHRConceptoLine.forProcessConcept(novedad.getC_BPartner_ID(), idConceptoDiasTrabajados, novedad.getUY_HRProcess_ID(), get_TrxName());

							//si no existe una linea para este concepto, entonces la creo
							if(conceptoLine == null){
								conceptoLine = new MHRConceptoLine(getCtx(),0,get_TrxName());
								conceptoLine.setHR_Concept_ID(idConceptoDiasTrabajados);
								conceptoLine.setUY_HRNovedades_ID(novedad.getUY_HRNovedades_ID());
								conceptoLine.setQty(new BigDecimal(totalDiasTrabajados));

							} else { //si ya existe una linea para este concepto, entonces actualizo la cantidad
								conceptoLine.setQty(conceptoLine.getQty().add(new BigDecimal(totalDiasTrabajados)));

							}

							conceptoLine.saveEx();
						}
					}

					//PARA HORAS TRABAJADAS
					if(groupName.equalsIgnoreCase("Por hora")){
						if(this.totalMinutosTrabajados > 0){
							int idConceptoHorasTrabajadas = rs.getInt("hr_concept_hrstrab_id");
							conceptoLine = MHRConceptoLine.forProcessConcept(novedad.getC_BPartner_ID(), idConceptoHorasTrabajadas, novedad.getUY_HRProcess_ID(), get_TrxName());

							BigDecimal totalHoras = (new BigDecimal(this.totalMinutosTrabajados).divide(new BigDecimal(60),2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP); //convierto minutos a horas 

							//si no existe una linea para este concepto, entonces la creo
							if(conceptoLine == null){
								conceptoLine = new MHRConceptoLine(getCtx(),0,get_TrxName());
								conceptoLine.setHR_Concept_ID(idConceptoHorasTrabajadas);
								conceptoLine.setUY_HRNovedades_ID(novedad.getUY_HRNovedades_ID());
								conceptoLine.setQty(totalHoras);

							} else { //si ya existe una linea para este concepto, entonces actualizo la cantidad
								conceptoLine.setQty(conceptoLine.getQty().add(totalHoras));

							}

							conceptoLine.saveEx();
						}
					}

					//PARA HORAS EXTRA
					if(this.minutosExtraTotales > 0){
						int idConceptoHorasExtra = rs.getInt("hr_concept_hrsextra_id");
						conceptoLine = MHRConceptoLine.forProcessConcept(novedad.getC_BPartner_ID(), idConceptoHorasExtra, novedad.getUY_HRProcess_ID(), get_TrxName());

						BigDecimal horasExtra = new BigDecimal(this.minutosExtraTotales).divide(new BigDecimal(60),2, RoundingMode.HALF_UP); //convierto minutos a horas 

						//si no existe una linea para este concepto, entonces la creo
						if(conceptoLine == null){
							conceptoLine = new MHRConceptoLine(getCtx(),0,get_TrxName());
							conceptoLine.setHR_Concept_ID(idConceptoHorasExtra);
							conceptoLine.setUY_HRNovedades_ID(novedad.getUY_HRNovedades_ID());
							conceptoLine.setQty(horasExtra);

						} else { //si ya existe una linea para este concepto, entonces actualizo la cantidad
							conceptoLine.setQty(conceptoLine.getQty().add(horasExtra));

						}

						conceptoLine.saveEx();
					}

					//PARA HORAS NO TRABAJADAS
					if(this.totalMinutosNoTrabajados > 0){
						int idConceptoHorasNoTrab = rs.getInt("hr_concept_hrsnotrab_id");
						conceptoLine = MHRConceptoLine.forProcessConcept(novedad.getC_BPartner_ID(), idConceptoHorasNoTrab, novedad.getUY_HRProcess_ID(), get_TrxName());

						BigDecimal horasNoTrab = new BigDecimal(this.totalMinutosNoTrabajados).divide(new BigDecimal(60),2, RoundingMode.HALF_UP);

						//si no existe una linea para este concepto, entonces la creo
						if(conceptoLine == null){
							conceptoLine = new MHRConceptoLine(getCtx(),0,get_TrxName());
							conceptoLine.setHR_Concept_ID(idConceptoHorasNoTrab);
							conceptoLine.setUY_HRNovedades_ID(novedad.getUY_HRNovedades_ID());
							conceptoLine.setQty(horasNoTrab);

						} else { //si ya existe una linea para este concepto, entonces actualizo la cantidad
							conceptoLine.setQty(conceptoLine.getQty().add(horasNoTrab));

						}

						conceptoLine.saveEx();
					}

					//PARA HORAS NOCTURNAS
					if(this.totalMinutosNocturnos > 0){
						int idConceptoHorasNocturnas = rs.getInt("hr_concept_hrsnoct_id");
						conceptoLine = MHRConceptoLine.forProcessConcept(novedad.getC_BPartner_ID(), idConceptoHorasNocturnas, novedad.getUY_HRProcess_ID(), get_TrxName());

						BigDecimal horasNocturnas = new BigDecimal(this.totalMinutosNocturnos).divide(new BigDecimal(60),2, RoundingMode.HALF_UP); //convierto minutos a horas 

						//si no existe una linea para este concepto, entonces la creo
						if(conceptoLine == null){
							conceptoLine = new MHRConceptoLine(getCtx(),0,get_TrxName());
							conceptoLine.setHR_Concept_ID(idConceptoHorasNocturnas);
							conceptoLine.setUY_HRNovedades_ID(novedad.getUY_HRNovedades_ID());
							conceptoLine.setQty(horasNocturnas);

						} else { //si ya existe una linea para este concepto, entonces actualizo la cantidad
							conceptoLine.setQty(conceptoLine.getQty().add(horasNocturnas));

						}

						conceptoLine.saveEx();

					}

					//PARA INASISTENCIAS
					if(this.totalInasistencias > 0){
						int idConceptoInasistencias = rs.getInt("hr_concept_inasist_id");
						conceptoLine = MHRConceptoLine.forProcessConcept(novedad.getC_BPartner_ID(), idConceptoInasistencias, novedad.getUY_HRProcess_ID(), get_TrxName());

						BigDecimal value = new BigDecimal(this.totalInasistencias); 

						//si no existe una linea para este concepto, entonces la creo
						if(conceptoLine == null){
							conceptoLine = new MHRConceptoLine(getCtx(),0,get_TrxName());
							conceptoLine.setHR_Concept_ID(idConceptoInasistencias);
							conceptoLine.setUY_HRNovedades_ID(novedad.getUY_HRNovedades_ID());
							conceptoLine.setQty(value);

						} else { //si ya existe una linea para este concepto, entonces actualizo la cantidad
							conceptoLine.setQty(conceptoLine.getQty().add(value));

						}

						conceptoLine.saveEx();

					}

					//PARA HORAS EXTRA NOCTURNAS
					if(this.minutosExtraNocturnos > 0){
						int idConceptoHorasExtraNoct = rs.getInt("hr_concept_hrsextranoct_id");
						conceptoLine = MHRConceptoLine.forProcessConcept(novedad.getC_BPartner_ID(), idConceptoHorasExtraNoct, novedad.getUY_HRProcess_ID(), get_TrxName());

						BigDecimal horasExtraNocturnas = new BigDecimal(this.minutosExtraNocturnos).divide(new BigDecimal(60),2, RoundingMode.HALF_UP); //convierto minutos a horas

						//si no existe una linea para este concepto, entonces la creo
						if(conceptoLine == null){
							conceptoLine = new MHRConceptoLine(getCtx(),0,get_TrxName());
							conceptoLine.setHR_Concept_ID(idConceptoHorasExtraNoct);
							conceptoLine.setUY_HRNovedades_ID(novedad.getUY_HRNovedades_ID());
							conceptoLine.setQty(horasExtraNocturnas);

						} else { //si ya existe una linea para este concepto, entonces actualizo la cantidad
							conceptoLine.setQty(conceptoLine.getQty().add(horasExtraNocturnas));

						}

						conceptoLine.saveEx();

					}

				}


			}catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			}finally {
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
		}
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
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {
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
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {

		return null;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		
		// Proceso segun accion
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare)){
			
			this.loadMarks(); //Carga marcas
			this.verify(false); //Verifica las marcas
			
			this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
			this.setDocAction(DocumentEngine.ACTION_Complete);
			this.saveEx(get_TrxName());
			}
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete)){
			
			// Verifico que haya lineas de marcas
			if (this.getDetailLines().size() <= 0) throw new AdempiereException("No hay lineas de Marcas. No se puede completar la transaccion.");
					
			this.verify(true); //Vuelve a verificar marcas
			this.processMarks(); //Procesa las marcas
			this.generarNovedades(); // Genera novedades
			
			this.setDocAction(DocumentEngine.ACTION_None);
			this.setDocStatus(DocumentEngine.STATUS_Completed);
			this.setProcessed(true);
			this.saveEx(get_TrxName());
		}
		
			return true;
				
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
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	/**
	 * OpenUp.	issue #776
	 * Descripcion : Obtiene y carga en tabla del proceso, las marcas de reloj según los filtros elegidos.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 12/07/2011
	 */
	private void loadMarks(){

		String sql = "", insert = "";
		
		try
		{
			// Condicion de Filtros
			String whereFiltros = "";

			if(this.getC_BPartner_ID()>0) whereFiltros += " AND hdr.c_bpartner_id =" + this.getC_BPartner_ID();
			
			// Obtengo fechas de inicio y fin del periodo de liquidacion
			MHRProcess hrProcess = new MHRProcess(getCtx(), this.getUY_HRProcess_ID(), null);
			MHRPeriod hrPeriod = new MHRPeriod(getCtx(), hrProcess.getHR_Period_ID(), null);
			this.fechaInicio = hrPeriod.getStartDate();
			this.fechaFin = hrPeriod.getEndDate();

			// Me aseguro que fecha fin tenga hora 23:59:59 para considerar las marcas del ultimo dia del periodo
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.fechaFin);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 0);
			java.util.Date fec1 = cal.getTime();
			this.fechaFin = new Timestamp(fec1.getTime());
			
			// Inserto directo para agilizar
			MSequence seq = MSequence.get(getCtx(), this.get_TableName(), null);
			
			insert = "INSERT INTO UY_ClockInterface_Detail " +
					 " (updated, uy_clockinterface_detail_id, uy_clockinterface_filter_id, uy_marcasreloj_id, updatedby, created, createdby, isactive, " +
					 " nrolegajo, ad_client_id, ad_org_id, tipomarca_id, fechahora, c_bpartner_id, text, filename, manual, original_line,processed, uy_procesar) " ;
			sql    = " SELECT hdr.updated, nextid(" + seq.getAD_Sequence_ID() + ", 'N'), " + this.get_ID() +  ", hdr.uy_marcasreloj_id, " +
					 " hdr.updatedby, hdr.created, hdr.createdby, hdr.isactive, hdr.nrotarjeta, hdr.ad_client_id, hdr.ad_org_id, hdr.tipomarca_id, " +
					 " hdr.fechahora,hdr.c_bpartner_id,hdr.text,hdr.filename,hdr.manual,hdr.original_line,'N','N' " +  
				     " FROM uy_marcasreloj hdr " +                                                 
				     " WHERE hdr.fechahora BETWEEN '" + this.fechaInicio + "' AND '" + this.fechaFin + "' " +
				     " AND hdr.processed = 'N' " + whereFiltros +
				     " ORDER BY hdr.c_bpartner_id, hdr.fechahora, hdr.tipomarca_id ";
			
					
			if(DB.executeUpdate(insert + sql, get_TrxName())<=0) throw new Exception("No hay marcas para procesar");
	
				
		}
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
	}

	
	/**
	 * OpenUp.	issue #776
	 * Descripcion : Procesa las marcas de reloj seleccionadas por el usuario
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 12/07/2011
	 */
	private void processMarks() {
				
		String sql = "",insert = "";
		String update = "";
		
		try{
			
			MSequence seq = MSequence.get(getCtx(), "UY_MarcasRelojProcesadas", null);
			
			insert = "INSERT INTO UY_MarcasRelojProcesadas(ad_client_id,ad_org_id,c_bpartner_id,created,createdby,isactive,updated,updatedby," +
					"uy_marcasrelojprocesadas_id,uy_marcasreloj_id,uy_clockinterface_detail_id, uy_clockinterface_filter_id,uy_procesar,nrotarjeta," +
					"fechahora,uy_tipomarcareloj_id,manual,original_line,text)" ;
			   sql = "SELECT hdr.ad_client_id,hdr.ad_org_id,hdr.c_bpartner_id,hdr.created,hdr.createdby,hdr.isactive,hdr.updated,hdr.updatedby," +
			   		" nextid(" + seq.getAD_Sequence_ID() +  ",'N'),hdr.uy_marcasreloj_id,hdr.uy_clockinterface_detail_id,hdr.uy_clockinterface_filter_id,hdr.uy_procesar," +
			   		" hdr.nrolegajo,hdr.fechahora,hdr.tipomarca_id,hdr.manual,hdr.original_line,hdr.text " +
				     "FROM UY_ClockInterface_Detail hdr " +
				     "WHERE uy_procesar='Y' AND UY_ClockInterface_Filter_ID = " + this.get_ID();
			
			update = "UPDATE UY_MarcasReloj SET processed = 'Y' " +
			         "WHERE uy_marcasreloj_id IN (SELECT coalesce(uy_marcasreloj_id,0) FROM UY_ClockInterface_Detail " +
                     "WHERE UY_ClockInterface_Filter_ID = " + this.get_ID() + ")";  
				
			DB.executeUpdateEx(insert + sql,get_TrxName());

			DB.executeUpdateEx(update,get_TrxName());	
			
		}
		catch (Exception e)
		{
			throw new AdempiereException (e.getMessage());
		}
	}
	
	/**
	 * Verifica las marcas y sugiere aprobacion
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 09/05/2012
	 * @see
	 * @param onlyApproved
	 * @return
	 */
	
	private void verify(boolean checked){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		
		try{

			sql = " SELECT uy_clockinterface_detail_id, c_bpartner_id, t.value, fechahora, text as observaciones, uy_procesar " +
				  " FROM UY_ClockInterface_Detail d " +
				  " INNER JOIN uy_tipomarcareloj t ON d.tipomarca_id=t.uy_tipomarcareloj_id " +
				  " WHERE uy_procesar =? " + 
				  " AND UY_ClockInterface_Filter_ID =? " +
				  " ORDER BY c_bpartner_id, fechahora, t.value ";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setString(1, ((checked)? "Y" : "N"));
			pstmt.setInt(2, this.get_ID());

			int cBpartnerID = 0;						
			MClockInterfaceDetail detail = null;
			
			// Las marcas se procesan por ciclos. 
			// Cada ciclo empieza con la marca ENTRADA, sigue con SALIDA_INT, ENTRADA_INT y cierra con SALIDA.
			rs = pstmt.executeQuery ();
			while(rs.next()){
				
				detail = new MClockInterfaceDetail(getCtx(), rs.getInt("uy_clockinterface_detail_id"), get_TrxName());
			
				// Corte por empleado
				if (rs.getInt("c_bpartner_id") != cBpartnerID){
				
					// Actualizo aprobacion y observaciones de marcas que tenia antes del corte de empleado
					// Si tengo marca de entrada de ciclo anterior, no apruebo ciclo anterior ya que me faltaron marcas
					if (tengoMarcaEntrada){
						if (this.aprobacion){
							this.aprobacion = false;
							this.observaciones = "Faltan Marcas";
						}
						updateMarcas();
					}
					
					// Inicializo para nuevo ciclo
					this.initCicloVerificacion();
					cBpartnerID = rs.getInt("c_bpartner_id");
				}

				// Agrego marca a verificar en array para luego ser actualizada
				marcas.add(detail);
				
				// Tipo de marca
				String tipoMarca = rs.getString("value");
				
				// Si esta marca es comienzo de ciclo (ENTRADA)
				if (tipoMarca.equalsIgnoreCase(ENTRADA)){

					// Por default todo bien
					detail.setuy_procesar(true);
					detail.setText("");
					
					// Si ya tengo una marca de entrada y NO tengo marcas internas
					// solamente tengo que considerar la primer marca de entrada y no esta.
					// EJ : 08/05/2012 09:00 1-ENTRADA   (ESTA LA CONSIDERO)
					//      08/05/2012 09:01 1-ENTRADA   (ESTA LA DESCARTO)					
 					if (tengoMarcaEntrada && !tengoMarcaEntradaInterna && !tengoMarcaSalidaInterna){
 						detail.setuy_procesar(false);
 						detail.setText("Marca de Entrada Duplicada");
 					}
					
 					// Si ya tengo una marca de entrada y ademas tengo marcas intermedias
					// no apruebo ninguna marca del ciclo y comienzo nuevo ciclo ahora
 					if (tengoMarcaEntrada && (tengoMarcaEntradaInterna || tengoMarcaSalidaInterna)){
 						if (this.aprobacion){
 							this.aprobacion = false;
 							this.observaciones = "Faltan Marcas";
 						}
 						updateMarcas();
 						initCicloVerificacion();

 						marcas.add(detail);
 						detail.setuy_procesar(true);
 						detail.setText("");
 					}
					
					tengoMarcaEntrada = true;
				}

				// Si esta marca es salida interna
				if (tipoMarca.equalsIgnoreCase(SALIDA_INTERNA)){

					// Por default todo bien
					detail.setuy_procesar(true);
					detail.setText("");
					
					// Si ya vengo con ciclo no aprobado, no apruebo
					if (!this.aprobacion){
 						detail.setuy_procesar(this.aprobacion);
 						detail.setText(this.observaciones);
					}
					else{
						
						// Si no tengo marca de entrada, no apruebo
						if (!tengoMarcaEntrada){
							this.aprobacion = false;
							this.observaciones = "Falta Marca de Entrada"; 
	 						detail.setuy_procesar(this.aprobacion);
	 						detail.setText(this.observaciones);
						}
						else{
							// Si ya tengo marca de salida interna no apruebo
							if (this.tengoMarcaSalidaInterna){
								this.aprobacion = false;
								this.observaciones = "Marca de Salida Interna Duplicada"; 
		 						detail.setuy_procesar(this.aprobacion);
		 						detail.setText(this.observaciones);
							}
						}
						
					}
					this.tengoMarcaSalidaInterna = true;
				}

				// Si esta marca es entrada interna
				if (tipoMarca.equalsIgnoreCase(ENTRADA_INTERNA)){

					// Por default todo bien
					detail.setuy_procesar(true);
					detail.setText("");
					
					// Si ya vengo con ciclo no aprobado, no apruebo
					if (!this.aprobacion){
 						detail.setuy_procesar(this.aprobacion);
 						detail.setText(this.observaciones);
					}
					else{
						
						// Si no tengo marca de entrada, no apruebo
						if (!tengoMarcaEntrada){
							this.aprobacion = false;
							this.observaciones = "Falta Marca de Entrada"; 
	 						detail.setuy_procesar(this.aprobacion);
	 						detail.setText(this.observaciones);
						}
						else{
							// Tengo marca de entrada, verifico si tengo marca de salida interna
							if (!this.tengoMarcaSalidaInterna){
								this.aprobacion = false;
								this.observaciones = "Falta Marca de Salida Interna"; 
		 						detail.setuy_procesar(this.aprobacion);
		 						detail.setText(this.observaciones);
							}
							else{
								// Si ya tengo marca de salida interna no apruebo
								if (this.tengoMarcaEntradaInterna){
									this.aprobacion = false;
									this.observaciones = "Marca de Entrada Interna Duplicada"; 
			 						detail.setuy_procesar(this.aprobacion);
			 						detail.setText(this.observaciones);
								}
							}

						}
					}
					this.tengoMarcaEntradaInterna = true;
				}
				
				// Si esta marca es SALIDA (cierre de ciclo)
				if (tipoMarca.equalsIgnoreCase(SALIDA)){

					// Por default todo bien
					detail.setuy_procesar(true);
					detail.setText("");
					
					// Si ya vengo con ciclo no aprobado, no apruebo
					if (!this.aprobacion){
 						detail.setuy_procesar(this.aprobacion);
 						detail.setText(this.observaciones); 						
					}
					else{
						// Si no tengo marca de entrada, no apruebo
						if (!tengoMarcaEntrada){
							this.aprobacion = false;
							this.observaciones = "Falta Marca de Entrada"; 
	 						detail.setuy_procesar(this.aprobacion);
	 						detail.setText(this.observaciones);
						}
						else{
							// Tengo marca de entrada, verifico si tengo marca de salida interna
							if (!this.tengoMarcaSalidaInterna){
								this.aprobacion = false;
								this.observaciones = "Falta Marca de Salida Interna"; 
		 						detail.setuy_procesar(this.aprobacion);
		 						detail.setText(this.observaciones);
							}
							else{
								// Tengo marca de entrada, de salida interna, y ahora verifico si tengo la de entrada interna
								if (!this.tengoMarcaEntradaInterna){
									this.aprobacion = false;
									this.observaciones = "Falta Marca de Entrada Interna"; 
			 						detail.setuy_procesar(this.aprobacion);
			 						detail.setText(this.observaciones);
								}								
							}
						}
					}
					
					// Como la marca de salida es de cierre de ciclo tengo que actualizar marcas e inicializar
					updateMarcas();
					initCicloVerificacion();
					
					this.tengoMarcaSalida = true;
				}
			}
			
			// Fin de loop, verifico ultimo ciclo
			if (cBpartnerID > 0){
				if (!tengoMarcaSalida){
					if (this.aprobacion){
						this.aprobacion = false;
						this.observaciones = "Falta Marca de Salida.";
					}
					updateMarcas();
				}
			}
	
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}


	/**
	 * Recorre array con modelos de detalle de interface de reloj y actualiza valores de los mismos.
	 * OpenUp Ltda. Issue # 986.
	 * @author Hp - 09/05/2012
	 * @see
	 */
	private void updateMarcas() {
		
		for (MClockInterfaceDetail detail: marcas){

			String observaAux = detail.getText();
			boolean apruebaAux = detail.isuy_procesar();
			
			detail.setuy_procesar(this.aprobacion);
			detail.setText(this.observaciones);
			
			// Si este ciclo esta aprobado, pero una linea en particular no lo esta, respeto la linea.
			if (this.aprobacion && !apruebaAux){
				detail.setuy_procesar(apruebaAux);
				detail.setText(observaAux);
			}
			
			detail.saveEx();
		}
	}

	
	/**
	 * Inicio atributos para comenzar un nuevo ciclo de verificacion de marcas.
	 * OpenUp Ltda. Issue # 986
	 * @author Hp - 09/05/2012
	 * @see
	 */
	private void initCicloVerificacion(){
		tengoMarcaEntrada = false;
		tengoMarcaEntradaInterna = false;
		tengoMarcaSalida = false;
		tengoMarcaSalidaInterna = false;
		aprobacion = true;
		observaciones = "";
		marcas = new ArrayList<MClockInterfaceDetail>();
	}
	
	/**
	 * Obtiene y retorna lista de Empleados a procesar. Considera si tengo empleados en el filtro.
	 * OpenUp Ltda. Issue # 986 
	 * @author Hp - 23/04/2012
	 * @see
	 * @return List<MBPartner>. Lista de empleados.
	 */
	private List<MClockInterfaceDetail> getDetailLines() {

		StringBuilder whereClause = new StringBuilder(X_UY_ClockInterface_Detail.COLUMNNAME_uy_clockinterface_filter_ID + "=" + this.get_ID());

		List<MClockInterfaceDetail> details = new Query(getCtx(), I_UY_ClockInterface_Detail.Table_Name, whereClause.toString(), get_TrxName())
			.setClient_ID()
			.setOnlyActiveRecords(true)
			.list();

		return details;
	}
	
	/**
	 * Metodo que setea variables globales de hora teorica de entrada y salida del empleado actual
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 14/05/2012
	 * @see
	 * @param partnerID
	 */
	
	private void getHorasTeoricas(int partnerID){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT startingtime,finishingtime " +
                     " FROM hr_employee e " +
                     " INNER JOIN c_bpartner p ON e.c_bpartner_id=p.c_bpartner_id " +
                     " WHERE p.isactive='Y' AND e.isactive='Y' AND p.c_bpartner_id=? ";
		
		try{
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, partnerID);
			rs = pstmt.executeQuery ();
			
			if(rs.next()){
				
				this.horaTeoEntrada = rs.getTimestamp("startingtime");
				this.horaTeoSalida = rs.getTimestamp("finishingtime");
								
			}
				
			
		}catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
			
	}
	
	/**
	 * Metodo que obtiene el tipo de remuneracion del empleado
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 14/05/2012
	 * @see
	 * @param partnerID
	 * @return
	 */
		
	private MRemuneration getTipoRemuneracion(int partnerID,String trxName){
		
	MRemuneration rem = null;
		
		String sql = " SELECT c_remuneration_id " +
                     " FROM uy_bpremuneracion r " + 
                     " INNER JOIN c_bpartner p ON r.c_bpartner_id=p.c_bpartner_id " +
                     " WHERE p.isactive='Y' AND r.isactive='Y' AND p.c_bpartner_id=" + partnerID;  
		
		int remID = DB.getSQLValueEx(trxName, sql);
		
		if(remID > 0) rem = new MRemuneration(Env.getCtx(),remID,trxName);
		
		return rem;
		
	}


	/**
	 * Metodo para corregir posibles errores en calculos
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 17/05/2012
	 * @see
	 */
	
	private void controlDiario(MRemuneration rem){
		
			BigDecimal mtd = new BigDecimal(this.minutosTrabajadosDiarios);
			BigDecimal mntd = new BigDecimal(this.minutosNoTrabajadosDiarios);
			BigDecimal totalReal = mtd.add(mntd); 	
						
			if(totalReal.compareTo(this.horasJornada.multiply(new BigDecimal(60)))<0){
				
				BigDecimal diff = (this.horasJornada.multiply(new BigDecimal(60))).subtract(totalReal);   
				this.minutosNoTrabajadosDiarios = this.minutosNoTrabajadosDiarios + diff.intValue(); //sumo la diferencia al contador de minutos NO trabajados
								
			}
			
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
