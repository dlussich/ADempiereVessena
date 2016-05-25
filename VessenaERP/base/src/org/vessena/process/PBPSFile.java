package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrg;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MHRConcept;
import org.openup.model.MHRRemunerationGroup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PBPSFile extends SvrProcess{
	
	private String fileName = "";
	private int adClientID = 0;
	private int bpartnerID = 0;
	private int adOrgID = 0;
	private int periodID = 0;
	private String file_directory = "";
	private String periodo = "";
	private String tipoAporte = "";
	private String tipoContribuyente = "";
	private String bps = "";
	BufferedWriter bw = null;
	String msg = "";
	

	@Override
	protected void prepare() {
	
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
					
				if (name.equalsIgnoreCase("Ad_Client_ID")) {
					if (para[i].getParameter() != null) {
						this.adClientID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}

				if (name.equalsIgnoreCase("Ad_Org_ID")) {
					if (para[i].getParameter() != null) {
						this.adOrgID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("C_Period_ID")) {
					if (para[i].getParameter() != null) {
						this.periodID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				
				if (name.equalsIgnoreCase("File_Directory")) {
					if (para[i].getParameter() != null) {
						this.file_directory = ((String) para[i].getParameter());
					}
				}
				
				if (name.equalsIgnoreCase("C_BPartner_ID")) {
					if (para[i].getParameter() != null) {
						this.bpartnerID = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
			
		
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		
		createFile(); //creo archivo a utilizar
		return write(); //llamo a funcion de escritura de archivo
		
	}

	private String write() throws Exception {
		
		String cadena = "";
		String sql = "";
		String rut = "";
		String orgName = "";
		String orgAddress = "";
		String phone = "";
		String periodoInvertido = "";
		BigDecimal totalAmt = Env.ZERO;
		MOrg org = null;
		MBPartner partner = null;
		MHRRemunerationGroup remGroup = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
			
		if(fileName != null ) bw = new BufferedWriter(new FileWriter(fileName,true));
		
		MPeriod period = new MPeriod(getCtx(),this.periodID,get_TrxName()); //obtengo modelo del periodo
	
		//SE CREA EL PRIMER REGISTRO (Tipo 1 - EMPRESA)---------------------------------------------------------------
		if(this.adOrgID > 0) org = new MOrg(getCtx(), this.adOrgID, get_TrxName());
			
		//obtengo nro de RUT
		sql = "SELECT coalesce(taxid,'0') FROM ad_orginfo WHERE ad_org_id=" + this.adOrgID + " AND ad_client_id=" + this.adClientID;
		rut = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		//obtengo nombre de empresa
		orgName = org.getName().trim();
	
		//obtengo direccion de la empresa
		sql = "SELECT coalesce(address1,'|')" +
              " FROM c_location l" +
              " INNER JOIN ad_orginfo o ON l.c_location_id=o.c_location_id" +
              " WHERE o.ad_org_id=" + this.adOrgID + " AND o.ad_client_id=" + this.adClientID;
		orgAddress = DB.getSQLValueStringEx(get_TrxName(), sql).trim();
		
		//obtengo telefono de la empresa
		sql = "SELECT coalesce(phone,phone2,'0') FROM ad_orginfo WHERE ad_org_id=" + this.adOrgID + " AND ad_client_id=" + this.adClientID;
		phone = DB.getSQLValueStringEx(get_TrxName(), sql).trim();
					
		//se arma la cadena a guardar		
		cadena = "1|N|3.1|OpenUp 3.7.1|" + this.bps + "|" + rut + "|" + this.tipoAporte + "|" + orgName + "|" + orgAddress + "|" + phone;

		if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
		
		//FIN DE PRIMER REGISTRO
		
		//SE CREA EL SEGUNDO REGISTRO (Tipo 4 - CABEZAL DE NOMINA)---------------------------------------------------------------
		//se debe dar vuelta el perido, pasar de año/mes a mes/año
		if(this.periodo != null && this.periodo != ""){
			String year = periodo.substring(0,4);
			String month = periodo.substring(4,6);
			periodoInvertido = month + year;
		}
		
		//obtengo valor de tipo de contribuyente
		sql = "SELECT coalesce(t.value,'0')" +
              " FROM uy_hrparametros p" +
              " INNER JOIN uy_hrtipocontribuyente t ON p.uy_hrtipocontribuyente_id=t.uy_hrtipocontribuyente_id" +
              " WHERE p.ad_org_id=" + this.adOrgID + " AND p.ad_client_id=" + this.adClientID;
		this.tipoContribuyente = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		//obtengo monto total de nomina de todas las liquidaciones del periodo seleccionado
		totalAmt = this.getTotalAmt(period);
		
		//se arma la cadena a guardar		
		cadena = "4|" + periodoInvertido + "|" + this.tipoContribuyente + "|" + totalAmt + "||";

		if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
		
		//FIN DE SEGUNDO REGISTRO
		
		//SE INICIA CREACION DE REGISTROS PARA LOS EMPLEADOS
		//se obtienen los empleados a procesar
		
		try{
			sql = "SELECT distinct r.c_bpartner_id" +
					" FROM uy_hrresult r" +
					" INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" +
					" INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
					" INNER JOIN uy_hrprocess pro on p.uy_hrprocess_id = pro.uy_hrprocess_id" + 
					" INNER JOIN hr_period period on pro.hr_period_id = period.hr_period_id" +
					" INNER JOIN c_bpartner bp on r.c_bpartner_id=bp.c_bpartner_id" +
					" WHERE period.c_period_id=" + this.periodID + " and bp.isactive='Y' and bp.startdate <= " + "'" + period.getEndDate() + "'"+ " ORDER BY r.c_bpartner_id ASC";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				
				String date = "";
				String pais = "";
				String tipoDoc = "";
				String nroDoc = "";
				String primerNombre = "";
				String segundoNombre = "";
				String primerApellido = "";
				String segundoApellido = "";
				String fechaNac = "";
				String sexo = "";
				String nacionalidad = "";
				String acumLaboral = "";
				int tipoRem = 0;
				String fechaIng = "";
				String vinculoFunc = "";
				String codExoneracion = "";
				String compEspecial = "";
				BigDecimal diasTrabajados = Env.ZERO;
				BigDecimal horasTrabajadas = Env.ZERO;
				String seguroSalud = "";
				String causalEgreso = "";
				String fechaEgreso = "";
				BigDecimal horasSemanales = Env.ZERO; 
				BigDecimal totalConcepto1 = Env.ZERO;
				BigDecimal totalConcepto2 = Env.ZERO;
				BigDecimal totalConcepto3 = Env.ZERO;
				BigDecimal totalConcepto5 = Env.ZERO;
				BigDecimal totalConcepto6 = Env.ZERO;
				BigDecimal totalConcepto7 = Env.ZERO;
				BigDecimal totalConcepto8 = Env.ZERO;
				BigDecimal totalConcepto9 = Env.ZERO;
				BigDecimal totalConcepto41 = Env.ZERO;
				BigDecimal totalConcepto43 = Env.ZERO;
				MHRConcept con = null;
				
				partner = new MBPartner(getCtx(), rs.getInt("c_bpartner_id"), get_TrxName()); //obtengo modelo del empleado
				
				//SE CREA EL TERCER REGISTRO (Tipo 5 - PERSONAS)---------------------------------------------------------------
				
				//obtengo pais del documento del empleado
				sql = "SELECT p.value" +
                      " FROM uy_hrcodigopais p" +
                      " INNER JOIN c_bpartner b on p.uy_hrcodigopais_id=b.uy_hrcodigopais_id" +
                      " WHERE c_bpartner_id=" + partner.get_ID();
				pais = DB.getSQLValueString(get_TrxName(), sql);
				
				//obtengo tipo de documento del empleado y ajusto valores de acuerdo a los requeridos por BPS
				tipoDoc = partner.getnatcodetype();
				
				if(tipoDoc.equalsIgnoreCase("CI")){
					tipoDoc = "DO";
				}else if(tipoDoc.equalsIgnoreCase("PP")){
					tipoDoc = "PA";
				}
				
				//obtengo numero de documento del empleado
				nroDoc = partner.getNationalCode().trim();
				nroDoc = nroDoc.replace(".", "");
				nroDoc = nroDoc.replace("-", "");
				
				//obtengo nombre completo del empleado
				primerNombre = partner.getFirstName().trim();
				primerApellido = partner.getFirstSurname().trim();
				if(segundoNombre != "") segundoNombre = partner.getSecondName().trim();
				if(segundoApellido != "") segundoApellido = partner.getSecondSurname().trim();
				
				//obtengo fecha de nacimiento
				Timestamp birthDate = partner.getbirthdate();
				date = null;
				if (birthDate != null)
					date = birthDate.toString();
				
				fechaNac = date.replace("-","");
				fechaNac = fechaNac.substring(6,8) + fechaNac.substring(4,6) + fechaNac.substring(0,4);
							
				//obtengo sexo del empleado y ajusto valores de acuerdo a los requeridos por BPS
				sexo = partner.getsex();
				
				if(sexo.equalsIgnoreCase("M")){
					sexo = "1";
				}else if(sexo.equalsIgnoreCase("F")){
					sexo = "2";
				}
				
				//obtengo nacionalidad del empleado y ajusto valores de acuerdo a los requeridos por BPS
				nacionalidad = partner.getnationality();
				
				if(nacionalidad.equalsIgnoreCase("OR")){
					nacionalidad = "1";
				}else if(nacionalidad.equalsIgnoreCase("CL")){
					nacionalidad = "2";
				}else if(nacionalidad.equalsIgnoreCase("EX")){
					nacionalidad = "3";
				}
				
				//se arma la cadena a guardar		
				cadena = "5|" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + primerApellido + "|" + segundoApellido + "|" + primerNombre + "|" + segundoNombre + "|" +
						fechaNac + "|" + sexo + "|" + nacionalidad;

				if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
				
				//FIN DE TERCER REGISTRO
				
				//SE CREA EL CUARTO REGISTRO (Tipo 6 - ACTIVIDAD)---------------------------------------------------------------
				
				//obtengo acumulacion laboral del empleado
				sql = "SELECT value FROM uy_hracumulacionlaboral WHERE uy_hracumulacionlaboral_id=" + partner.getUY_HRAcumulacionLaboral_ID();
				acumLaboral = DB.getSQLValueStringEx(get_TrxName(), sql);
				
				//obtengo fecha de ingreso del empleado
				Timestamp startDate = partner.getStartDate();
				date = null;
				if (startDate != null)
					date = startDate.toString();
				
				fechaIng = date.replace("-","");
				fechaIng = fechaIng.substring(6,8) + fechaIng.substring(4,6) + fechaIng.substring(0,4);
								
				//obtengo codigo de tipo de remuneracion del empleado
				sql = "SELECT r.codigo" +
                      " FROM c_remuneration r" +
                      " INNER JOIN uy_bpremuneracion pr ON r.c_remuneration_id=pr.c_remuneration_id" +
                      " WHERE pr.c_bpartner_id=" + partner.get_ID();
				tipoRem = DB.getSQLValueEx(get_TrxName(), sql);
				
				//obtengo ID de tipo de remuneracion del empleado
				sql = "SELECT rg.uy_hrremunerationgroup_id" +
                      " FROM c_remuneration r" +
                      " INNER JOIN uy_bpremuneracion pr ON r.c_remuneration_id=pr.c_remuneration_id" +
                      " INNER JOIN uy_hrremunerationgroup rg ON r.uy_hrremunerationgroup_id=rg.uy_hrremunerationgroup_id" +
                      " WHERE pr.c_bpartner_id=" + partner.get_ID();
				int remID = DB.getSQLValueEx(get_TrxName(), sql);
				
				if(remID<=0) throw new AdempiereException ("No se pudo obtener grupo de remuneracion del empleado '" + partner.getName() + "'");
				
				remGroup = new MHRRemunerationGroup(getCtx(), remID, get_TrxName());
				
				if(remGroup.getValue().equalsIgnoreCase("Mensual")){
					
					//OpenUp. Nicolas Sarlabos. 11/06/2013. #838
					BigDecimal totalInasistencias = Env.ZERO;
					BigDecimal inasistencias = Env.ZERO;
					BigDecimal inasistenciasxseguro = Env.ZERO;
					BigDecimal faltasJustif = Env.ZERO;
					
					//obtengo cantidad de inasistencias
					con = MHRConcept.forValue(getCtx(), "7002", get_TrxName());
					if(con.get_ID()>0){
						inasistencias = getInHRProcess(partner,con,period,this.adClientID);
						if(inasistencias.compareTo(Env.ZERO)>0) totalInasistencias = totalInasistencias.add(inasistencias); 
					} else throw new AdempiereException ("No se encontro concepto 7002: Cantidad inasistencias");
										
					//obtengo cantidad de inasistencias por seguro
					con = MHRConcept.forValue(getCtx(), "3001", get_TrxName());
					if(con.get_ID()>0){
						inasistenciasxseguro = getInHRProcess(partner,con,period,this.adClientID);
						if(inasistenciasxseguro.compareTo(Env.ZERO)>0) totalInasistencias = totalInasistencias.add(inasistenciasxseguro);
					} else throw new AdempiereException ("No se encontro concepto 3001: Cantidad inasistencias Por Seguro");
					
					//obtengo cantidad de faltas justificadas
					con = MHRConcept.forValue(getCtx(), "7003", get_TrxName());
					if(con.get_ID()>0){
						faltasJustif = getInHRProcess(partner,con,period,this.adClientID);
						if(faltasJustif.compareTo(Env.ZERO)>0) totalInasistencias = totalInasistencias.add(faltasJustif);
					} else throw new AdempiereException ("No se encontro concepto 7003: Cantidad Faltas Justificadas");
						
					BigDecimal totalDiasMes = new BigDecimal(30);
					diasTrabajados = (totalDiasMes.subtract(totalInasistencias)).setScale(0, RoundingMode.HALF_UP);
					horasTrabajadas = Env.ZERO;											
					//Fin OpenUp. #838
				} else if(remGroup.getValue().equalsIgnoreCase("Jornalero")){
					
					//obtengo cantidad de jornales trabajados
					con = MHRConcept.forValue(getCtx(), "7001", get_TrxName());
					if(con.get_ID()>0){
						diasTrabajados = getNews(partner, con, period, this.adClientID);
					} else throw new AdempiereException ("No se encontro concepto 7001: Jornales Trabajados");	
						
						if(diasTrabajados.compareTo(Env.ZERO)>0){
							
							diasTrabajados = diasTrabajados.setScale(0, RoundingMode.HALF_UP);
							
						}
					
					horasTrabajadas = Env.ZERO;					
					
				} else if(remGroup.getValue().equalsIgnoreCase("Por Hora")){
					
					BigDecimal horas = Env.ZERO;
					BigDecimal dias = Env.ZERO;
					BigDecimal horasJornada = new BigDecimal(8);
					
					//obtengo cantidad de jornales trabajados
					con = MHRConcept.forValue(getCtx(), "7000", get_TrxName());
					if(con.get_ID()>0){						
						horas = getNews(partner, con, period, this.adClientID);						
					} else throw new AdempiereException ("No se encontro concepto 7000: Horas Trabajadas");

					if(horas.compareTo(Env.ZERO)>0){

						dias = horas.divide(horasJornada, 3, RoundingMode.HALF_UP);
						diasTrabajados = new BigDecimal(dias.intValue());
						
						horasTrabajadas = dias.subtract(diasTrabajados);
						horasTrabajadas = (horasTrabajadas.multiply(horasJornada)).setScale(0, RoundingMode.HALF_UP);	
						
						//si las horas trabajadas resultaron ser 8, las cambio a 0 y sumo 1 dia
						if(horasTrabajadas.compareTo(horasJornada)==0){
							
							horasTrabajadas = Env.ZERO;
							diasTrabajados = diasTrabajados.add(Env.ONE); 
							
						}

					}					
					
				}
				
				//obtengo horas semanales del tipo de remuneracion del empleado
				sql = "SELECT r.weekhours" +
                      " FROM c_remuneration r" +
                      " INNER JOIN uy_bpremuneracion pr ON r.c_remuneration_id=pr.c_remuneration_id" +
                      " WHERE pr.c_bpartner_id=" + partner.get_ID();
				horasSemanales = (DB.getSQLValueBDEx(get_TrxName(), sql)).setScale(0, RoundingMode.HALF_UP);
				
				//obtengo codigo de exoneracion del empleado
				sql = "SELECT value FROM uy_hrcodigoexoneracion WHERE uy_hrcodigoexoneracion_id=" + partner.getUY_HRCodigoExoneracion_ID();
				codExoneracion = DB.getSQLValueStringEx(get_TrxName(), sql);
					
				//obtengo vinculo funcional del empleado
				sql = "SELECT value FROM uy_hrvinculofuncional WHERE uy_hrvinculofuncional_id=" + partner.getUY_HRVinculoFuncional_ID();
				vinculoFunc = DB.getSQLValueStringEx(get_TrxName(), sql);
				
				//obtengo computo especial del empleado
				sql = "SELECT value FROM uy_hrcomputoespecial WHERE uy_hrcomputoespecial_id=" + partner.getUY_HRComputoEspecial_ID();
				compEspecial = DB.getSQLValueStringEx(get_TrxName(), sql);
													
				//obtengo seguro de salud del empleado
				sql = "SELECT value FROM uy_hrsegurosalud WHERE uy_hrsegurosalud_id=" + partner.getUY_HRSeguroSalud_ID();
				seguroSalud = DB.getSQLValueStringEx(get_TrxName(), sql);
							
				//obtengo fecha de egreso del empleado
				Timestamp endDate = partner.getEndDate();
				date = null;
				if (endDate != null)
					date = endDate.toString();
				
				if(date!=null){
					fechaEgreso = date.replace("-","");
					fechaEgreso = fechaEgreso.substring(6,8) + fechaEgreso.substring(4,6) + fechaEgreso.substring(0,4);
					
					//obtengo causal de egreso del empleado
					sql = "SELECT value FROM uy_hrmotivosegreso WHERE uy_hrmotivosegreso_id=" + partner.getUY_HRMotivosEgreso_ID();
					causalEgreso = DB.getSQLValueStringEx(get_TrxName(), sql);
									
					//si la fecha de egreso no corresponde al periodo que se esta procesando entonces no debe cargarse al archivo
					if(endDate.compareTo(period.getStartDate()) < 0 || endDate.compareTo(period.getEndDate()) > 0){
						
						causalEgreso=null;
						fechaEgreso=null;
						
					}
											
				}
									
				//se arma la cadena a guardar	
				if(causalEgreso!=null && fechaEgreso!=""){

					cadena = "6||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + fechaIng + "|" + tipoRem + "|" + horasSemanales + "|" +
							vinculoFunc + "|" + codExoneracion + "|" + compEspecial + "||||" + diasTrabajados + "|" + horasTrabajadas + "|" + 
							seguroSalud + "|" + causalEgreso + "|" + fechaEgreso;
				} else {

					cadena = "6||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + fechaIng + "|" + tipoRem + "|" + horasSemanales + "|" +
							vinculoFunc + "|" + codExoneracion + "|" + compEspecial + "||||" + diasTrabajados + "|" + horasTrabajadas + "|" 
							+ seguroSalud + "||";

				}

				if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
							
				//FIN DE CUARTO REGISTRO
				
				//SE CREA EL QUINTO REGISTRO (Tipo 7 - REMUNERACION)---------------------------------------------------------------
				
				//para concepto 1 (siempre debe mostrarse aunque sea cero)
				totalConcepto1 = calculateRemuneration("1", partner.get_ID());	
				if(totalConcepto1.compareTo(Env.ZERO)==0) { //si es 0.00 debo mostrar 0
					int x = totalConcepto1.intValue();
					totalConcepto1 = new BigDecimal(x);
				}
				cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 1 + "|" + totalConcepto1 + "||";
				if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
								
				//para concepto 2
				totalConcepto2 = calculateRemuneration("2", partner.get_ID());				
				if(totalConcepto2.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 2 + "|" + totalConcepto2 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
				
				//para concepto 3
				totalConcepto3 = calculateRemuneration("3", partner.get_ID());				
				if(totalConcepto3.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 3 + "|" + totalConcepto3 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
				
				//para concepto 5
				totalConcepto5 = calculateRemuneration("5", partner.get_ID());				
				if(totalConcepto5.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 5 + "|" + totalConcepto5 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
				
				//para concepto 6
				totalConcepto6 = calculateRemuneration("6", partner.get_ID());				
				if(totalConcepto6.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 6 + "|" + totalConcepto6 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
				
				//para concepto 7
				totalConcepto7 = calculateRemuneration("7", partner.get_ID());				
				if(totalConcepto7.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 7 + "|" + totalConcepto7 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
				
				//para concepto 8
				totalConcepto8 = calculateRemuneration("8", partner.get_ID());				
				if(totalConcepto8.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 8 + "|" + totalConcepto8 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
				
				//para concepto 9
				totalConcepto9 = calculateRemuneration("9", partner.get_ID());				
				if(totalConcepto9.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 9 + "|" + totalConcepto9 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
				
				//para concepto 41
				totalConcepto41 = calculateRemuneration("41", partner.get_ID());				
				if(totalConcepto41.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 41 + "|" + totalConcepto41 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
				
				//para concepto 43
				totalConcepto43 = calculateRemuneration("43", partner.get_ID());				
				if(totalConcepto43.compareTo(Env.ZERO)>0){
					
					cadena = "7||" + pais + "|" + tipoDoc + "|" + nroDoc + "|" + acumLaboral + "|" + 43 + "|" + totalConcepto43 + "||";
					if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
					
				}
			
				//FIN DE QUINTO REGISTRO

			}
			
			//SE CREA EL REGISTRO 12 (DATOS DE CONTACTO)--------------------------------------------------------------------------------
			String address = "";
			String name = "";
			String surname = "";
			String email = "";
			MBPartner bpartner = new MBPartner (getCtx(),this.bpartnerID,null);
						
			//obtengo direccion del empleado
			sql = "select coalesce(l.address1,l.address2,'')" +
                  " from c_bpartner_location pl" +
                  " inner join c_location l on pl.c_location_id=l.c_location_id" +
                  " where pl.c_bpartner_id=" + this.bpartnerID;
			address = DB.getSQLValueString(get_TrxName(), sql);
			
			name = bpartner.getFirstName();
			surname = bpartner.getFirstSurname();
			
			sql = "select coalesce(email,'') from c_bpartner_location where c_bpartner_id=" + this.bpartnerID;
			email = DB.getSQLValueString(get_TrxName(), sql);
						
			//se arma la cadena a guardar		
			cadena = "12||" + address + "|||" + surname + " " + name + "|" + email;

			if(cadena != null && cadena != "") this.saveInFile(cadena,bw); //escribo la linea en archivo
			
			//FIN REGISTRO 12			
						
			bw.close();
			
			msg = "Proceso Terminado OK";
				
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}				
		return msg;
	}
	
	/**
	 * Metodo que genera el archivo a utilizar
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 20/07/2012
	 * @see
	 */
	private void createFile() {
		
		String sql = "";
				
		//obtengo año y mes como string
		//sql = "SELECT startdate::character varying FROM c_period WHERE c_period_id=" + this.periodID;
		//this.periodo = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		sql = "SELECT startdate FROM c_period WHERE c_period_id=" + this.periodID;
		Timestamp startDate = DB.getSQLValueTSEx(get_TrxName(), sql);
		
		if (startDate != null)
			this.periodo = startDate.toString();
		
		this.periodo = this.periodo.replace("-","");
		this.periodo = this.periodo.substring(0,6);
		
		//obtengo valor de tipo de aportacion
		sql = "SELECT coalesce(t.value,'0')" +
              " FROM uy_hrparametros p" +
              " INNER JOIN uy_hrtiposaportacion t ON p.uy_hrtiposaportacion_id=t.uy_hrtiposaportacion_id" +
              " WHERE p.ad_org_id=" + this.adOrgID + " AND p.ad_client_id=" + this.adClientID;
		this.tipoAporte = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		//obtengo numero de empresa
		sql = "SELECT bps FROM ad_orginfo WHERE ad_org_id=" + this.adOrgID + " AND ad_client_id=" + this.adClientID ;
		this.bps = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		if(bps != null && bps != ""){
			
			if(file_directory != null && file_directory != ""){

				this.fileName = this.file_directory + "\\N_" + this.tipoAporte + "_" + this.bps + "_" + this.periodo + ".bps";

			} else throw new AdempiereException ("Ruta destino invalida");

		} else throw new AdempiereException ("No se encontro numero de empresa");
			
		//se crea el archivo a utilizar
		File file = new File (this.fileName);
		
		try {
			// A partir del objeto File se crea el archivo fisicamente
			if (!file.createNewFile()) throw new AdempiereException ("No se pudo crear el archivo. Compruebe que no existe un archivo de igual nombre en la carpeta destino.");

		} catch (IOException ex) {
			throw new AdempiereException(ex.getMessage());
			
		}
			
	}
	
	/**
	 * Metodo que obtiene el monto total de nomina del periodo recibido como parametro 
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 10/08/2012
	 * @see
	 * @return
	 */
	
	private BigDecimal getTotalAmt(MPeriod period){
		
		BigDecimal totalAmt = Env.ZERO;
				
		if(period != null){

			String sql = "SELECT coalesce(sum(rd.totalamt),0)" +
					" FROM uy_hrresult r" +
					" INNER JOIN uy_hrresultdetail rd ON r.uy_hrresult_id=rd.uy_hrresult_id" +
					" INNER JOIN uy_hrprocesonomina p ON r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" +
					" INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
					" INNER JOIN hr_concept_category cat on con.hr_concept_category_id = cat.hr_concept_category_id" + 
					" INNER JOIN uy_hrprocess pro on p.uy_hrprocess_id = pro.uy_hrprocess_id" + 
					" INNER JOIN hr_period period on pro.hr_period_id = period.hr_period_id" +
					" WHERE period.c_period_id =" + period.get_ID() + " AND (con.grabado_irpf='Y' or con.grabado_bps='Y')" + 
					" AND cat.value='1001'";

			totalAmt = DB.getSQLValueBDEx(get_TrxName(), sql);
			
		}
		
		return totalAmt;
		
	}
	
	/**
	 * Metodo que obtiene el monto total de remuneracion para determinado tipo de concepto 
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 25/03/2013
	 * @see
	 * @return
	 */
	
	private BigDecimal calculateRemuneration (String conceptValue,int partnerID) {
		
		BigDecimal total = Env.ZERO;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String sql = "SELECT coalesce(rd.totalamt,0) as totalamt,con.hr_concept_id" +
                " FROM uy_hrresult r" + 
                " INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" + 
                " INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" + 
                " INNER JOIN uy_hrprocess pro on p.uy_hrprocess_id = pro.uy_hrprocess_id" + 
                " INNER JOIN hr_period period on pro.hr_period_id = period.hr_period_id" + 
                " INNER JOIN hr_concept con on rd.hr_concept_id = con.hr_concept_id" +
                " INNER JOIN uy_hrconceptohistlaboral lab on con.uy_hrconceptohistlaboral_id = lab.uy_hrconceptohistlaboral_id" +
                " WHERE period.c_period_id=" + this.periodID + " and lab.value=" + "'" + conceptValue + "'" + " and r.c_bpartner_id=" + partnerID;
		
		//OPenUp. Nicolas Sarlabos. 03/04/2013. #629
		try{

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();

			while(rs.next()){

				MHRConcept con = new MHRConcept(getCtx(),rs.getInt("hr_concept_id"),get_TrxName()); //instancio concepto nomina

				if(!con.getAccountSign().equalsIgnoreCase("")){

					if(con.getAccountSign().equalsIgnoreCase("D")){ //si es un DEBITO entonces debe sumar

						total = total.add(rs.getBigDecimal("totalamt"));

					} else if(con.getAccountSign().equalsIgnoreCase("C")){ //si es un CREDITO entonces debe restar

						total = total.subtract(rs.getBigDecimal("totalamt"));

					}

				}
			}	


		} catch (Exception ex) {
			throw new AdempiereException(ex.getMessage());

		} finally{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	
		return total;
		
	}
	
	/**
	 * Devuelve la suma de cantidades en novedades de nomina para un empleado, concepto y periodo recibidos
	 * OpenUp Ltda. Issue #629
	 * @author Nicolas Sarlabos - 03/04/2013
	 * @param partner
	 * @param con
	 * @param period
	 * @param clientID
	 * @return
	 */
	
	private BigDecimal getNews(MBPartner partner,MHRConcept con, MPeriod period, int clientID){
		
		BigDecimal qty = Env.ZERO;

		String sql = "SELECT coalesce(sum(l.qty),0)" +
				" FROM uy_hrconceptoline l" + 
				" INNER JOIN uy_hrnovedades n ON l.uy_hrnovedades_id = n.uy_hrnovedades_id" +
				" INNER JOIN uy_hrprocess p ON n.uy_hrprocess_id = p.uy_hrprocess_id" +
				" INNER JOIN hr_period per ON p.hr_period_id = per.hr_period_id" +
				" INNER JOIN c_period period ON per.c_period_id = period.c_period_id" +
				" WHERE n.c_bpartner_id=" + partner.get_ID() + " AND l.hr_concept_id=" + con.get_ID() + 
				" AND period.c_period_id=" + period.get_ID() + " AND n.ad_client_id=" + clientID;
		
		qty = DB.getSQLValueBD(get_TrxName(), sql);
		
		return qty;
	}
	
	/**
	 * Devuelve la suma de cantidades en procesos de liquidaciones para un empleado, concepto y periodo recibidos
	 * OpenUp Ltda. Issue #629
	 * @author Nicolas Sarlabos - 03/04/2013
	 * @param partner
	 * @param con
	 * @param period
	 * @param clientID
	 * @return
	 */
	
	private BigDecimal getInHRProcess(MBPartner partner,MHRConcept con, MPeriod period, int clientID){
		
		BigDecimal qty = Env.ZERO;

		String sql = "SELECT coalesce(sum(rd.totalamt),0)" +
				     " FROM uy_hrresult r" + 
				     " INNER JOIN uy_hrresultdetail rd on r.uy_hrresult_id=rd.uy_hrresult_id" + 
				     " INNER JOIN uy_hrprocesonomina p on r.uy_hrprocesonomina_id=p.uy_hrprocesonomina_id" + 
				     " INNER JOIN uy_hrprocess pro on p.uy_hrprocess_id = pro.uy_hrprocess_id" + 
				     " INNER JOIN hr_period period on pro.hr_period_id = period.hr_period_id" + 
				     " WHERE period.c_period_id=" + period.get_ID() + " and rd.hr_concept_id=" + con.get_ID() + " and r.c_bpartner_id=" + partner.get_ID() +
				     " and p.ad_client_id=" + clientID;

		qty = DB.getSQLValueBD(get_TrxName(), sql);
		
		return qty;
	}
	
	//Fin OpenUp-	
	/**
	 * Metodo que escribe en un archivo
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 20/07/2012
	 * @see
	 * @param cadena
	 * @param bf
	 * @throws Exception
	 */
	
	private void saveInFile(String cadena,BufferedWriter bf) throws Exception { 
		
		try {

			if(cadena != null && bf != null){

				String ln = System.getProperty("line.separator");
				bf.append(cadena + ln);
										
			}


		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}

		
	}

}
