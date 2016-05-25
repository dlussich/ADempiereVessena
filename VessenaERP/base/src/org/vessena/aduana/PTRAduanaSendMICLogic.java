/**
 * 
 */
package org.openup.aduana;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MCountry;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTROperativeSite;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.aduana.ConsultaRespuestaMic.CrtStatus;
import org.openup.aduana.ConsultaRespuestaMic.LineImgStatus;
import org.openup.aduana.ConsultaRespuestaMic.MicStatus;
import org.openup.aduana.conexion.DAERespuesta;
import org.openup.aduana.mic.dto.DAE;
import org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto;
import org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Lineas.Linea.Asociaciones;
import org.openup.model.MCiudad;
import org.openup.model.MTRAduana;
import org.openup.model.MTRCrt;
import org.openup.model.MTRDriver;
import org.openup.model.MTRDua;
import org.openup.model.MTRDuaLink;
import org.openup.model.MTRMic;
import org.openup.model.MTRMicCont;
import org.openup.model.MTRMicCountry;
import org.openup.model.MTRMicLine;
import org.openup.model.MTRMicTask;
import org.openup.model.MTRPackageType;
import org.openup.model.MTRTransOrder;
import org.openup.model.MTRTrip;
import org.openup.model.MTRTruck;

/**
 * @author Nicolas
 *
 */
public class PTRAduanaSendMICLogic {
	
	Properties ctx = null;
	String trx = null;
	
	private static int acuConocimientoNumeroSecuencial;
	
	public enum TipoOperacion {
		Alta,
		Modificacion,
		Baja
	}
	public enum TipoOperacionImagen {
		Alta,
		Baja
	}

	/**
	 * 
	 */
	public PTRAduanaSendMICLogic() {
		// TODO Auto-generated constructor stub
	}
	
	public PTRAduanaSendMICLogic(Properties ctx, String trxName) {
		
		this.ctx = ctx;
		this.trx = trxName;
		
	}
	
	public String execute(Properties ctx,MTRMic mic, String trxName){
		
		String respuesta = "";
		MTRMic micConsulta = null;
		int contEnvios = 1;
		
		String msg = mic.validateMic();
		
		if(msg!=null) throw new AdempiereException (msg);
		
		// Para que el before save no cambie la propiedad micstatus en este caso se establece una variable que indique si se esta en el envio a aduana
		MTRMic.setEnviandoAduana(true);
		
		// Envio del mic solamente cuando se trata de una modificacion
		if (mic.getMicStatus().equalsIgnoreCase(MicStatus.MODIFICAR.toString())) {
			SendSoap(mic, true);
			micConsulta = new MTRMic(ctx, mic.getUY_TR_Mic_ID(), trxName);
			respuesta += "Envio " + contEnvios++ + ":\n" + micConsulta.getResponseText();
		}
		
		//verificacion de codigos de imagen de CRTs
		if(!verifyImgCrt(mic)) {
			SendSoap(mic, false);
			micConsulta = new MTRMic(ctx, mic.getUY_TR_Mic_ID(), trxName);
			respuesta += "Envio " + contEnvios++ + ":\n" + micConsulta.getResponseText();
		}
		
		SendSoap(mic, false);
		micConsulta = new MTRMic(ctx, mic.getUY_TR_Mic_ID(), trxName);
		respuesta += "Envio " + contEnvios++ + ":\n" + micConsulta.getResponseText();
		
		mic.setResponseText(respuesta);
		mic.saveEx(trxName);
		
		MTRMic.setEnviandoAduana(false);
		
		//elimino de la tabla auxiliar las lineas para los CRT que se dieron de baja
		DB.executeUpdateEx("delete from uy_tr_mictask where crtstatus = '" + ConsultaRespuestaMic.CrtStatus.DESVINCULADO.toString() + "'", trxName);
		
		return null;
	}
	
	private boolean verifyImgCrt(MTRMic mic) {
		
		MTRCrt crt = null;
		boolean value = true;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MTRMicCountry country = null;


		MicStatus tipoOp = MicStatus.valueOf(mic.getMicStatus());
		
		// Si no se ha enviado, o si esta marcado para eliminar, no necesita ser enviado dos veces
		if (tipoOp == MicStatus.SINENVIAR || tipoOp == MicStatus.ELIMINAR || mic.isDrop()) {
			return true;
		}
		
		try {

			if(mic.getUY_TR_Crt_ID() > 0){

				crt = new MTRCrt(ctx,mic.getUY_TR_Crt_ID(),trx);

				if(!crt.getcodigo().equalsIgnoreCase(mic.getCrtImgNum1())) value = false;			
			}

			//recorro las continuaciones
			sql = "select uy_tr_miccont_id, uy_tr_crt_id, uy_tr_crt_id_1" +
					" from uy_tr_miccont" +
					" where uy_tr_mic_id = " + mic.get_ID() +
					" order by sheet asc";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){

				MTRMicCont micCont = new MTRMicCont(ctx,  rs.getInt("uy_tr_miccont_id"), trx);
				
				if(rs.getInt("uy_tr_crt_id") > 0) {
					
					crt = new MTRCrt(ctx,rs.getInt("uy_tr_crt_id"),trx);
					if(!crt.getcodigo().equalsIgnoreCase(micCont.getCrtImgNum1())) value = false;					
					
				}
				
				if(rs.getInt("uy_tr_crt_id_1") > 0) {
					
					crt = new MTRCrt(ctx,rs.getInt("uy_tr_crt_id_1"),trx);
					if(!crt.getcodigo().equalsIgnoreCase(micCont.getCrtImgNum2())) value = false;
					
				}
			}
									

		}
		catch (Exception e)	{
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
		return value;
		
	}

	protected String SendSoap(MTRMic mic, boolean soloMicModificacion){

		String sql = "", rut = "";
		MOrgInfo orgInfo = null;
		MOrg org = null;
		Date fechaEmision = null;
		String nroMIC = "";
		String paisEmision = "";
		TipoOperacion tipoOperacion = TipoOperacion.Alta;
		boolean isARG = false;

		DAERespuesta objRespuesta;


		// Variables DAE
		DAE dae = new DAE();
		DAE.Objeto objDae = new DAE.Objeto();
		DAE.Objeto.Manifiestos.Manifiesto objMic = new DAE.Objeto.Manifiestos.Manifiesto();

		try {

			// Datos del documento genérico de aduana
			orgInfo = MOrgInfo.get(ctx, Env.getAD_Org_ID(ctx), trx);
			if (orgInfo != null){
				rut = orgInfo.getDUNS();
			}

			org = MOrg.get(ctx, Env.getAD_Org_ID(ctx));

			dae.setObjeto(objDae);
			dae.setTipoDocumento("4"); // Tipo: Rut
			dae.setIdDocumento(rut);
			dae.setFechaHoraDocumentoElectronico(getXMLGregorianCalendar(new Date()));
			dae.setCodigoIntercambio("WS_MANIFIESTO"); // Código de intercambio correspondiente al Manifiesto de Carga
			
			// Concepto - Santiago De Pena informa que para el envio de manifiestos terrestres esta mal poner el idTrn porque no es necesario y puede traer errores.
			// OpenUp Ltda.
			// @author Raul Capecce - 08/12/2014
			//dae.setNroTransaccion(String.valueOf(Envio.generateIdTransaccionAduana(Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx), trx)));

			// Agregando mic al documento genérico
			objDae.setManifiestos(new DAE.Objeto.Manifiestos());
			objDae.getManifiestos().getManifiesto().add(objMic);

			// Carga de datos del Manifiesto
			MCiudad ciudad = null;
			MCountry country = null;
			MTRAduana aduana = null;
			MTRTruck truck = null;
			MTROperativeSite site = null;
			MBPartner partner = null;
			MTRTransOrder order = null;

			/* 1 */ String transporteTipo = "7"; // Tipo 7: Transporte Terrestre
			/* 2 */ String manifiestoTipo = "";
			/* 3 */ String manifiestoNumero = "";
			/* 4 */ String recinto = "";
			/* 5 */ Date fechaArriboDate = new Date();
			/* 5 */ long fechaArribo = new Long((new SimpleDateFormat("yyyyMMdd")).format(fechaArriboDate));
			/* 7 */ String agenteTransportistaTipoDocumento = "4"; // Tipo 4: RUC
			/* 8 */ String agenteTransportistaDocumento = rut;
			/* 16 */ String observacion = "";
			/* 21 */ String manifiestoOriginalNumero = "";
			/* 22 */ String agenteTransportistaNombre = org.getName().toUpperCase();
			/* 23 */ String agenteTransportistaPais = "";
			/* 24 */ String agenteTransportistaDireccion = "";
			/* 25 */ String lugarPartida ="";
			/* 26 */ String lugarDestino = "";
			/* 27 */ String aduanaIngresoCodigo = "";
			/* 28 */ String aduanaSalidaCodigo = "";
			/* 29 */ Date fechaPasajeFrontera = new Date();
			/* 30 */ String tractoraMatricula = "";
			/* 31 */ String remolqueMatricula = "";
			/* 32 */ String semiRemolqueMatricula = "";				
			/* 33 */ String transportistaEfectivo = "";
			/* 34 */ String agenteTransportistaRepresentanteTipoDoc = "";
			/* 35 */ String agenteTransportistaRepresentanteDoc = "";
			/* 36 */ String paisOrigenCodigo = "";
			/* 37 */ String paisDestinoCodigo = "";
			/* 38 */ String transportistaEfectivoRepresentanteTipoDoc = ""; 
			/* 39 */ String transportistaEfectivoRepresentanteDoc = "";
			/* 40 */ String transportistaEfectivoTipoDocumento = "";
			/* 41 */ String transportistaEfectivoDocumento = "";
			/* 42 */ String remolqueDuenoTipoDocumento = "";
			/* 43 */ String remolqueDuenoDocumento = "";
			/* 44 */ String lastre = "";
			/* 45 */ String conductorNombre = "";
			/* 46 */ String conductorTipoDocumento = "";
			/* 47 */ String conductorDocumento = "";
			/* 48 */ Date fechaLlegadaPrevista = new Date();
			/* 49 */ long plazoOrigenDestino = (long) 0;
			/* 50 */ String rutaDescripcion = "";
			/* 51 */ String ciudadPartidaCodigo = "";
			/* 52 */ String ciudadSalidaCodigo = "";
			/* 53 */ String ciudadEntradaCodigo = "";
			/* 54 */ String aduanaEntradaCodigo = "";
			/* 55 */ String lugarOperativoEntradaCodigo = "";
			/* 56 */ String ciudadDestinoCodigo = "";
			/* 57 */ String aduanaDestinoCodigo = "";
			/* 58 */ String lugarOperativoDestinoCodigo = "";
			/* 59 */ String paisTrasbordoCodigo = ""; //NO VA
			/* 60 */ String ciudadTrasbordoCodigo = ""; //NO VA
			/* 61 */ String aduanaTrasbordoCodigo = ""; //NO VA
			/* 62 */ String lugarOperativoTrasbordoCodigo = "";//NO VA
			/* 63 */ String transitoAduanero = "";
			/* 64 */ String agenteTransportistaRepresentanteDocExtranjero = "";//NO VA
			/* 65 */ String transportistaEfectivoRepresentanteDocExtranjero = ""; //NO VA
			/* 66 */ BigDecimal medioTransporteCapacidadArrastre = new BigDecimal(0);
			/* 67 */ long medioTransporteAnoFabricacion = (long) 0;
			/* 68 */ String documentoPrecedenteTipo = "30"; // Tipo: Manifiesto
			//			/* 69 */ String micPrecedenteTipoTransporte = ""; //NO VA
			//			/* 70 */ String micPrecedenteTipo = ""; //NO VA
			//			/* 71 */ String micPrecedenteNumero = ""; //NO VA
			//			/* 72 */ String micPrecedenteRecinto = ""; //NO VA
			//			/* 73 */ long micPrecedenteFechaArribo = (long) 0; //NO VA
			/* 75 */ String micCelularSMS = "";

			//SETEO DE VARIABLES-------------------------------------------------------------------------------------------------
			
			//si el estado es SIN ENVIAR, fecha de cruce y llegada deben ser la fecha actual
			if (MicStatus.valueOf(mic.getMicStatus())==MicStatus.SINENVIAR){
				
				Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
				mic.setDateAction(today);
				mic.setDateFinish(today);
				mic.saveEx(trx);				
				
			}
			
			if(mic.getType()!=null) manifiestoTipo = mic.getType();			
			if(mic.getNroMic()!=null) manifiestoNumero = mic.getNroMic().replace(" ","");
			if(mic.getRecinto()!=null) recinto = mic.getRecinto();
			if(mic.getDateFinish()!=null) fechaArriboDate = mic.getDateFinish();
			if(fechaArriboDate!=null) {

				fechaArribo = new Long((new SimpleDateFormat("yyyyMMdd")).format(fechaArriboDate));
				fechaLlegadaPrevista = fechaArriboDate;
			}

			if(mic.getobservaciones()!=null) observacion = mic.getobservaciones();

			if(mic.getNumero()!=null) manifiestoOriginalNumero = mic.getNumero().replace(" ", "");
			
			sql = "select coalesce(c.iso_code,'')" +
					" from ad_orginfo inf" +
					" inner join c_location loc on inf.c_location_id = loc.c_location_id" +
					" inner join c_country c on loc.c_country_id = c.c_country_id" +
					" where inf.ad_org_id = " + org.get_ID();
			agenteTransportistaPais = DB.getSQLValueStringEx(trx, sql);

			sql = "select coalesce(loc.address1,'')" +
					" from ad_orginfo inf" +
					" inner join c_location loc on inf.c_location_id = loc.c_location_id" +
					" inner join c_country c on loc.c_country_id = c.c_country_id" +
					" where inf.ad_org_id = " + org.get_ID();
			agenteTransportistaDireccion = DB.getSQLValueStringEx(trx, sql);

			country = new MCountry(ctx,mic.getC_Country_ID(),trx);
			if(country.get_ID() > 0) paisOrigenCodigo = country.get_ValueAsString("iso_code");

			country = new MCountry(ctx,mic.getC_Country_ID_1(),trx);
			if(country!=null) {

				String code = country.get_ValueAsString("iso_code");				

				if(code!=null && !code.equalsIgnoreCase("")){

					paisDestinoCodigo = code;

					if(code.equalsIgnoreCase("032")) isARG = true;					

				}

			}

			ciudad = new MCiudad(ctx,mic.getUY_Ciudad_ID_2(),trx);
			if(ciudad.get_ID() > 0) {

				lugarPartida = ciudad.getName();

				country = new MCountry(ctx,ciudad.getC_Country_ID(),trx);

				/*if(isARG)*/ ciudadPartidaCodigo = country.getCountryCode() + " " + ciudad.getCode();
			}

			ciudad = new MCiudad(ctx,mic.getUY_Ciudad_ID_3(),trx);
			if(ciudad.get_ID() > 0){

				lugarDestino = ciudad.getName();

				country = new MCountry(ctx,ciudad.getC_Country_ID(),trx);

				/*if(isARG)*/ ciudadDestinoCodigo = country.getCountryCode() + " " + ciudad.getCode();
			}

			if(manifiestoTipo.equalsIgnoreCase("0") || manifiestoTipo.equalsIgnoreCase("2")){

				aduana = new MTRAduana(ctx,mic.getUY_TR_Aduana_ID(),trx);
				if(aduana.get_ID() > 0) aduanaIngresoCodigo = aduana.getValue();

			}						

			if(manifiestoTipo.equalsIgnoreCase("1") || manifiestoTipo.equalsIgnoreCase("2")){

				aduana = new MTRAduana(ctx,mic.getUY_TR_Aduana_ID_1(),trx);
				if(aduana.get_ID() > 0) aduanaSalidaCodigo = aduana.getValue();				
			}			

			if(mic.getDateAction()!=null) fechaPasajeFrontera = mic.getDateAction();

			//TRACTOR
			truck = new MTRTruck(ctx,mic.getUY_TR_Truck_ID(),trx);
			
			if(truck!=null){
				
				if(truck.getValue()!=null) {
					
					tractoraMatricula = truck.getValue().replace(" ", "");
					tractoraMatricula = tractoraMatricula.replace("-", "");
				}
				
				//si el arrastre es invalido, lo tomo del vehiculo y actualizo el mic
				if(mic.getWeight().compareTo(Env.ZERO)<=0){
					
					medioTransporteCapacidadArrastre = truck.getArrastre().setScale(1, RoundingMode.HALF_UP);
					
					DB.executeUpdateEx("update uy_tr_mic set weight = " + truck.getArrastre() + " where uy_tr_mic_id = " + mic.get_ID(), trx);
					
				} else medioTransporteCapacidadArrastre = mic.getWeight().setScale(1, RoundingMode.HALF_UP);			
				
				// Transporte en toneladasa
				//medioTransporteCapacidadArrastre.divide(new BigDecimal(1000));
				
				//si el anio de fabricacion es vacio, lo tomo del vehiculo y actualizo el mic
				if(mic.getYearFrom()!=null && !mic.getYearFrom().equalsIgnoreCase("")){
					
					medioTransporteAnoFabricacion = Long.parseLong(mic.getYearFrom());
					
				} else {
					
					if(truck.getanio()!=null && !truck.getanio().equalsIgnoreCase("")){
						
						medioTransporteAnoFabricacion = Long.parseLong(truck.getanio());
						
						DB.executeUpdateEx("update uy_tr_mic set yearfrom = '" + truck.getanio() + "' where uy_tr_mic_id = " + mic.get_ID(), trx);
						
					} else throw new AdempiereException("El año de fabricacion del vehiculo es vacio");					
					
				}			

				if(truck.isOwn()){

					//agenteTransportistaDocumento = rut;
					//agenteTransportistaNombre = org.getName().toUpperCase();

					/*sql = "select coalesce(c.iso_code,'')" +
							" from ad_orginfo inf" +
							" inner join c_location loc on inf.c_location_id = loc.c_location_id" +
							" inner join c_country c on loc.c_country_id = c.c_country_id" +
							" where inf.ad_org_id = " + org.get_ID();
					agenteTransportistaPais = DB.getSQLValueStringEx(trx, sql);

					sql = "select coalesce(loc.address1,'')" +
							" from ad_orginfo inf" +
							" inner join c_location loc on inf.c_location_id = loc.c_location_id" +
							" inner join c_country c on loc.c_country_id = c.c_country_id" +
							" where inf.ad_org_id = " + org.get_ID();
					agenteTransportistaDireccion = DB.getSQLValueStringEx(trx, sql);*/

					//if(org.getDescription()!=null && !org.getDescription().equalsIgnoreCase("")) transportistaEfectivo = org.getDescription().toUpperCase();
					
					agenteTransportistaRepresentanteTipoDoc = "4";
					agenteTransportistaRepresentanteDoc = rut;
					
					transportistaEfectivoRepresentanteTipoDoc = "4";
					transportistaEfectivoRepresentanteDoc = rut;
										
					transportistaEfectivoTipoDocumento = "4";
					transportistaEfectivoDocumento = rut;	
					

				} else{

					if(truck.getC_BPartner_ID() > 0){ //obtengo el permisionario del tractor

						partner = new MBPartner(ctx,truck.getC_BPartner_ID(),trx);

						//agenteTransportistaDocumento = partner.getDUNS();
						//agenteTransportistaDocumento = rut;

						/*if(partner.getName2()!=null && !partner.getName2().equalsIgnoreCase("")) {

							//agenteTransportistaNombre = org.getName().toUpperCase();
							transportistaEfectivo = org.getName().toUpperCase();
						}*/

						/*sql = "select coalesce(c.iso_code,'')" +
								" from c_bpartner_location bl" +
								" inner join c_location loc on bl.c_location_id = loc.c_location_id" +
								" inner join c_country c on loc.c_country_id = c.c_country_id" +
								" where bl.isbillto = 'Y' and bl.c_bpartner_id = " + partner.get_ID();
						agenteTransportistaPais = DB.getSQLValueStringEx(trx, sql);

						sql = "select coalesce(loc.address1,'')" +
								" from c_bpartner_location bl" +
								" inner join c_location loc on bl.c_location_id = loc.c_location_id" +
								" inner join c_country c on loc.c_country_id = c.c_country_id" +
								" where bl.isbillto = 'Y' and bl.c_bpartner_id = " + partner.get_ID();
						agenteTransportistaDireccion = DB.getSQLValueStringEx(trx, sql);*/
						
						agenteTransportistaRepresentanteTipoDoc = "4";											
						agenteTransportistaRepresentanteDoc = rut;

						if(partner.getDocumentType()!=null && !partner.getDocumentType().equalsIgnoreCase("")){

							if(partner.getDocumentType().equalsIgnoreCase("CNPJ") || partner.getDocumentType().equalsIgnoreCase("OTRO")){

								transportistaEfectivoTipoDocumento = partner.get_ValueAsString("DocType1");
								transportistaEfectivoDocumento = partner.get_ValueAsString("DocNo1");

								transportistaEfectivoRepresentanteTipoDoc = partner.get_ValueAsString("DocType2");
								transportistaEfectivoRepresentanteDoc = partner.get_ValueAsString("DocNo2");						

								transportistaEfectivoRepresentanteDocExtranjero = partner.get_ValueAsString("DocNo2");								

							} else if (partner.getDocumentType().equalsIgnoreCase("RUT")){
									
								transportistaEfectivoTipoDocumento = "4";
								transportistaEfectivoDocumento = partner.getDUNS();

								transportistaEfectivoRepresentanteTipoDoc = "4";
								transportistaEfectivoRepresentanteDoc = partner.getDUNS();						

								transportistaEfectivoRepresentanteDocExtranjero = partner.getDUNS();	
								
							} 
						}														
				
					}					
				}

				//instancio la orden de transporte
				if(!mic.isLastre()){
					
					order = new MTRTransOrder(ctx, mic.getUY_TR_TransOrder_ID(), trx);					
					
				} else order = new MTRTransOrder(ctx, mic.getUY_TR_TransOrder_ID_1(), trx);
												
				if(order.getUY_TR_Driver_ID() > 0){

					MTRDriver driver = new MTRDriver(ctx,order.getUY_TR_Driver_ID(),trx);

					sql = "select firstname || ' ' || firstsurname" +
							" from uy_tr_driver" +
							" where uy_tr_driver_id = " + driver.get_ID();
					conductorNombre = DB.getSQLValueStringEx(trx, sql);

					if(conductorNombre!=null && !conductorNombre.equalsIgnoreCase("")) conductorNombre = conductorNombre.toUpperCase();

					if(driver.gettipodoc()!=null && !driver.gettipodoc().equalsIgnoreCase("")){

						String tipo = driver.gettipodoc();

						if(tipo.equalsIgnoreCase("CI")){

							conductorTipoDocumento = "2";				

						} else if(tipo.equalsIgnoreCase("PP")){

							conductorTipoDocumento = "6";

						} else if(tipo.equalsIgnoreCase("RUT")){

							conductorTipoDocumento = "4";
						}					
					}				
					
					//mando documento de chofer sin espacios, puntos ni guiones
					if(driver.getNationalCode() != null && !driver.getNationalCode().equalsIgnoreCase("")) { 
						
						String doc = driver.getNationalCode();
						doc = doc.replace(".", "");
						doc = doc.replace("-", "");
						doc = doc.replace(" ", "");
												
						conductorDocumento = doc;					
					}
				}			

			}

			//REMOLQUE
			truck = new MTRTruck(ctx,mic.getUY_TR_Truck_ID_2(),trx);
			
			if(truck!=null){

				if(truck.getValue()!=null){
					
					remolqueMatricula = truck.getValue().replace(" ", "");
					remolqueMatricula = remolqueMatricula.replace("-", "");
					
				}
					
				
				/*if(mic.isRemolque()){

					remolqueMatricula = truck.getValue();

				} else if (mic.isSemiRemolque()){

					semiRemolqueMatricula = truck.getValue();
				}*/

				if(truck.isOwn()){

					remolqueDuenoTipoDocumento = "4";
					remolqueDuenoDocumento = rut;				

				}else{

					if(truck.getC_BPartner_ID() > 0){

						partner = new MBPartner(ctx,truck.getC_BPartner_ID(),trx);

						remolqueDuenoTipoDocumento = partner.get_ValueAsString("DocType1");
						remolqueDuenoDocumento = partner.get_ValueAsString("DocNo1");
					}					
				}				
			}	

			if(mic.isLastre()){

				lastre = "S";

			} else lastre = "N";

			plazoOrigenDestino = mic.getDiference();

			if(mic.getObservaciones4()!=null) rutaDescripcion = mic.getObservaciones4();			

			site = new MTROperativeSite(ctx,mic.getUY_TR_OperativeSite_ID(),trx);
			if(site.get_ID() > 0 && isARG) lugarOperativoEntradaCodigo = site.getValue();

			site = new MTROperativeSite(ctx,mic.getUY_TR_OperativeSite_ID_1(),trx);
			if(site.get_ID() > 0 && isARG) lugarOperativoDestinoCodigo = site.getValue();

			ciudad = new MCiudad(ctx,mic.getUY_Ciudad_ID_4(),trx);
			if(ciudad.get_ID() > 0) {

				country = new MCountry(ctx,ciudad.getC_Country_ID(),trx);

				/*if(isARG)*/ ciudadEntradaCodigo = country.getCountryCode() + " " + ciudad.getCode();				
			}

			ciudad = new MCiudad(ctx,mic.getUY_Ciudad_ID_5(),trx);
			if(ciudad.get_ID() > 0) {

				country = new MCountry(ctx,ciudad.getC_Country_ID(),trx);

				/*if(isARG)*/ ciudadSalidaCodigo = country.getCountryCode() + " " + ciudad.getCode();					
			}

			aduana = new MTRAduana(ctx,mic.getUY_TR_Aduana_ID_2(),trx);
			if(aduana.get_ID() > 0 && isARG) aduanaEntradaCodigo = aduana.getValue();				

			aduana = new MTRAduana(ctx,mic.getUY_TR_Aduana_ID_3(),trx);
			if(aduana.get_ID() > 0 && isARG) aduanaDestinoCodigo = aduana.getValue();

			if(mic.isInTransit()){

				transitoAduanero = "S";

			} else transitoAduanero = "N";	
			
			if(mic.getMobile()!=null && !mic.getMobile().equalsIgnoreCase("")) micCelularSMS = mic.getMobile();

			// Si el campo IsDrop esta seteado, se recorre el manifiesto y todos sus conocimientos para setearlos para borrar
//			if (mic.isDrop()){
//				MarcarParaEliminar(mic, trx);
//			}
			
			// Seleccionar tipo de operación
			MicStatus tipoOp = MicStatus.valueOf(mic.getMicStatus());
			// Fin Seleccionar tipo de operación


			if (mic.isDrop()) {
				tipoOp = MicStatus.ELIMINAR;
			}

			switch(tipoOp){
			case SINENVIAR:
				objMic.setTransporteTipo(transporteTipo);
				objMic.setManifiestoTipo(manifiestoTipo);
				objMic.setFechaArribo(fechaArribo);
				objMic.setRegistroTipo("A"); // Operación de alta de MIC
				objMic.setAgenteTransportistaTipoDocumento(agenteTransportistaTipoDocumento);
				objMic.setAgenteTransportistaDocumento(agenteTransportistaDocumento);
				objMic.setObservacion(observacion);
				objMic.setManifiestoOriginalNumero(manifiestoOriginalNumero);
				objMic.setAgenteTransportistaNombre(agenteTransportistaNombre);
				objMic.setAgenteTransportistaPais(agenteTransportistaPais);
				objMic.setAgenteTransportistaDireccion(agenteTransportistaDireccion);
				objMic.setLugarPartida(lugarPartida); //Aduana: En la practica en los servidores de produccion, es el nombre del lugar // Aduana: Santiago informa que son los codigos de las ciudades y no el nombre
				objMic.setLugarDestino(lugarDestino); //Aduana: En la practica en los servidores de produccion, es el nombre del lugar // Aduana: Santiago informa que son los codigos de las ciudades y no el nombre
				objMic.setAduanaIngresoCodigo(aduanaIngresoCodigo);
				objMic.setAduanaSalidaCodigo(aduanaSalidaCodigo);
				objMic.setFechaPasajeFrontera(getXMLGregorianCalendar(fechaPasajeFrontera));
				objMic.setTractoraMatricula(tractoraMatricula);
				objMic.setRemolqueMatricula(remolqueMatricula);
				objMic.setSemiRemolqueMatricula(semiRemolqueMatricula);
				objMic.setTransportistaEfectivo(transportistaEfectivo);
				objMic.setAgenteTransportistaRepresentanteTipoDoc(agenteTransportistaRepresentanteTipoDoc);
				objMic.setAgenteTransportistaRepresentanteDoc(agenteTransportistaRepresentanteDoc);
				objMic.setPaisOrigenCodigo(paisOrigenCodigo);
				objMic.setPaisDestinoCodigo(paisDestinoCodigo);
				objMic.setTransportistaEfectivoRepresentanteTipoDoc(transportistaEfectivoRepresentanteTipoDoc);
				objMic.setTransportistaEfectivoRepresentanteDoc(transportistaEfectivoRepresentanteDoc);
				objMic.setTransportistaEfectivoTipoDocumento(transportistaEfectivoTipoDocumento);
				objMic.setTransportistaEfectivoDocumento(transportistaEfectivoDocumento);
				objMic.setRemolqueDuenoTipoDocumento(remolqueDuenoTipoDocumento);
				objMic.setRemolqueDuenoDocumento(remolqueDuenoDocumento);
				objMic.setLastre(lastre);
				objMic.setConductorNombre(conductorNombre);
				objMic.setConductorTipoDocumento(conductorTipoDocumento);
				objMic.setConductorDocumento(conductorDocumento);
				objMic.setFechaLlegadaPrevista(getXMLGregorianCalendar(fechaLlegadaPrevista));
				objMic.setPlazoOrigenDestino(plazoOrigenDestino);
				objMic.setRutaDescripcion(rutaDescripcion);
				objMic.setCiudadPartidaCodigo(ciudadPartidaCodigo);
				objMic.setCiudadSalidaCodigo(ciudadSalidaCodigo);
				objMic.setCiudadEntradaCodigo(ciudadEntradaCodigo);
				objMic.setAduanaEntradaCodigo(aduanaEntradaCodigo);
				objMic.setLugarOperativoEntradaCodigo(lugarOperativoEntradaCodigo);
				objMic.setCiudadDestinoCodigo(ciudadDestinoCodigo);
				objMic.setAduanaDestinoCodigo(aduanaDestinoCodigo);
				objMic.setLugarOperativoDestinoCodigo(lugarOperativoDestinoCodigo);
				objMic.setPaisTrasbordoCodigo(paisTrasbordoCodigo);
				objMic.setCiudadTrasbordoCodigo(ciudadTrasbordoCodigo);
				objMic.setAduanaTrasbordoCodigo(aduanaTrasbordoCodigo);
				objMic.setLugarOperativoTrasbordoCodigo(lugarOperativoTrasbordoCodigo);
				objMic.setTransitoAduanero(transitoAduanero);
				objMic.setAgenteTransportistaRepresentanteDocExtranjero(agenteTransportistaRepresentanteDocExtranjero);
				objMic.setTransportistaEfectivoRepresentanteDocExtranjero(transportistaEfectivoRepresentanteDocExtranjero);
				objMic.setMedioTransporteCapacidadArrastre(medioTransporteCapacidadArrastre);
				objMic.setMedioTransporteAnoFabricacion(medioTransporteAnoFabricacion);
				objMic.setDocumentoPrecedenteTipo(documentoPrecedenteTipo);				
				//				objMic.setMicPrecedenteTipoTransporte(micPrecedenteTipoTransporte);
				//				objMic.setMicPrecedenteTipo(micPrecedenteTipo);
				//				objMic.setMicPrecedenteNumero(micPrecedenteNumero);
				//				objMic.setMicPrecedenteRecinto(micPrecedenteRecinto);
				//				objMic.setMicPrecedenteFechaArribo(micPrecedenteFechaArribo);
				if (micCelularSMS != null) objMic.setMicCelularSMS(micCelularSMS);
				
				break;
			case MODIFICAR:
				objMic.setTransporteTipo(transporteTipo); 
				objMic.setManifiestoTipo(manifiestoTipo);
				objMic.setManifiestoNumero(manifiestoNumero);
				objMic.setRecinto(recinto);
				objMic.setFechaArribo(fechaArribo);
				objMic.setRegistroTipo("M"); // Operación de modificación de MIC
				objMic.setAgenteTransportistaTipoDocumento(agenteTransportistaTipoDocumento);
				objMic.setAgenteTransportistaDocumento(agenteTransportistaDocumento);
				objMic.setObservacion(observacion);
				objMic.setOperacionTipo("B"); // Campo 20 - Modificación de fecha de descarga
				objMic.setManifiestoOriginalNumero(manifiestoOriginalNumero);
				objMic.setAgenteTransportistaNombre(agenteTransportistaNombre);
				objMic.setAgenteTransportistaPais(agenteTransportistaPais);
				objMic.setAgenteTransportistaDireccion(agenteTransportistaDireccion);
				objMic.setLugarPartida(lugarPartida); //Aduana: En la practica en los servidores de produccion, es el nombre del lugar // Aduana: Santiago informa que son los codigos de las ciudades y no el nombre
				objMic.setLugarDestino(lugarDestino); //Aduana: En la practica en los servidores de produccion, es el nombre del lugar // Aduana: Santiago informa que son los codigos de las ciudades y no el nombre
				objMic.setAduanaIngresoCodigo(aduanaIngresoCodigo);
				objMic.setAduanaSalidaCodigo(aduanaSalidaCodigo);
				objMic.setFechaPasajeFrontera(getXMLGregorianCalendar(fechaPasajeFrontera));
				objMic.setTractoraMatricula(tractoraMatricula);
				objMic.setRemolqueMatricula(remolqueMatricula);
				objMic.setSemiRemolqueMatricula(semiRemolqueMatricula);
				objMic.setTransportistaEfectivo(transportistaEfectivo);
				objMic.setAgenteTransportistaRepresentanteTipoDoc(agenteTransportistaRepresentanteTipoDoc);
				objMic.setAgenteTransportistaRepresentanteDoc(agenteTransportistaRepresentanteDoc);
				objMic.setPaisOrigenCodigo(paisOrigenCodigo);
				objMic.setPaisDestinoCodigo(paisDestinoCodigo);
				objMic.setTransportistaEfectivoRepresentanteTipoDoc(transportistaEfectivoRepresentanteTipoDoc);
				objMic.setTransportistaEfectivoRepresentanteDoc(transportistaEfectivoRepresentanteDoc);
				objMic.setTransportistaEfectivoTipoDocumento(transportistaEfectivoTipoDocumento);
				objMic.setTransportistaEfectivoDocumento(transportistaEfectivoDocumento);
				objMic.setRemolqueDuenoTipoDocumento(remolqueDuenoTipoDocumento);
				objMic.setRemolqueDuenoDocumento(remolqueDuenoDocumento);
				objMic.setLastre(lastre);
				objMic.setConductorNombre(conductorNombre);
				objMic.setConductorTipoDocumento(conductorTipoDocumento);
				objMic.setConductorDocumento(conductorDocumento);
				objMic.setFechaLlegadaPrevista(getXMLGregorianCalendar(fechaLlegadaPrevista));
				objMic.setPlazoOrigenDestino(plazoOrigenDestino);
				objMic.setRutaDescripcion(rutaDescripcion);
				objMic.setCiudadPartidaCodigo(ciudadPartidaCodigo);
				objMic.setCiudadSalidaCodigo(ciudadSalidaCodigo);
				objMic.setCiudadEntradaCodigo(ciudadEntradaCodigo);
				objMic.setAduanaEntradaCodigo(aduanaEntradaCodigo);
				objMic.setLugarOperativoEntradaCodigo(lugarOperativoEntradaCodigo);
				objMic.setCiudadDestinoCodigo(ciudadDestinoCodigo);
				objMic.setAduanaDestinoCodigo(aduanaDestinoCodigo);
				objMic.setLugarOperativoDestinoCodigo(lugarOperativoDestinoCodigo);
				objMic.setPaisTrasbordoCodigo(paisTrasbordoCodigo);
				objMic.setCiudadTrasbordoCodigo(ciudadTrasbordoCodigo);
				objMic.setAduanaTrasbordoCodigo(aduanaTrasbordoCodigo);
				objMic.setLugarOperativoTrasbordoCodigo(lugarOperativoTrasbordoCodigo);
				objMic.setTransitoAduanero(transitoAduanero);
				objMic.setAgenteTransportistaRepresentanteDocExtranjero(agenteTransportistaRepresentanteDocExtranjero);
				objMic.setTransportistaEfectivoRepresentanteDocExtranjero(transportistaEfectivoRepresentanteDocExtranjero);
				objMic.setMedioTransporteCapacidadArrastre(medioTransporteCapacidadArrastre);
				objMic.setMedioTransporteAnoFabricacion(medioTransporteAnoFabricacion);
				objMic.setDocumentoPrecedenteTipo(documentoPrecedenteTipo);
				//				objMic.setMicPrecedenteTipoTransporte(micPrecedenteTipoTransporte);
				//				objMic.setMicPrecedenteTipo(micPrecedenteTipo);
				//				objMic.setMicPrecedenteNumero(micPrecedenteNumero);
				//				objMic.setMicPrecedenteRecinto(micPrecedenteRecinto);
				//				objMic.setMicPrecedenteFechaArribo(micPrecedenteFechaArribo);
				if (micCelularSMS != null) objMic.setMicCelularSMS(micCelularSMS);

				break;
			case ELIMINAR:
				objMic.setTransporteTipo(transporteTipo); 
				objMic.setManifiestoTipo(manifiestoTipo);
				objMic.setManifiestoNumero(manifiestoNumero); 
				objMic.setRecinto(recinto);
				objMic.setFechaArribo(fechaArribo);
				objMic.setRegistroTipo("B"); // Operación de baja de MIC

				break;
			case ENVIADO: // No se realizan operaciones sobre el mic, las operaciones se aplican sobre sus nodos hijos
				objMic.setTransporteTipo(transporteTipo); 
				objMic.setManifiestoTipo(manifiestoTipo);
				objMic.setManifiestoNumero(manifiestoNumero);
				objMic.setRecinto(recinto);
				objMic.setFechaArribo(fechaArribo);
				break;
			}

			if (tipoOp != MicStatus.ELIMINAR && !soloMicModificacion) {
				// Recorrer CRTs del MIC actual
				recorridaCRT(mic,objMic,tipoOperacion);
			}

			// Recorro los paises de paso del mic actual
			cargarPaisesDePaso(mic, objMic);
			
			//cargarDatosPruebaMIC(objMic);

			String strMicToSend = ConsultaRespuestaMic.firmarMic(dae, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx), trx, ctx);

			// uy_tr_crt_id
			DB.executeUpdateEx("UPDATE UY_TR_Mic SET result = '" + strMicToSend +"' WHERE uy_tr_mic_id = " + mic.getUY_TR_Mic_ID(), trx);

			objRespuesta = Envio.EnviarMensajeAduana(strMicToSend, Envio.Operacion.EnviarMic, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx), trx);

			ConsultaRespuestaMic.consultarRespuestaAsincronica(objRespuesta, mic,Env.getAD_Client_ID(ctx),Env.getAD_Org_ID(ctx), trx, ctx, -1);


		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		return null;
	}

	private void cargarPaisesDePaso(MTRMic mic, Manifiesto objMic){
		org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto.PaisesDePaso paisesDePaso = new org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto.PaisesDePaso();
		org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto.PaisesDePaso.PaisDePaso paisDePaso = null;
		
		MTRMicCountry country = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MCiudad ciudad = null;
		MTRAduana aduana = null;
		MTROperativeSite lugarOperativo = null;
		
		String sql = "select micC.uy_tr_miccountry_id as uy_tr_miccountry_id, cC.iso_code as iso_code" +
				" from uy_tr_miccountry as micC inner join c_country cC on micC.c_country_id = cC.c_country_id " +
				" where uy_tr_mic_id = " + mic.get_ID() +
				" order by uy_tr_miccountry_id";

		try {
			
			pstmt = DB.prepareStatement (sql, trx);
			rs = pstmt.executeQuery ();

			while (rs.next()){
				if (rs.getInt("uy_tr_miccountry_id") > 0){
					country = new MTRMicCountry(ctx, rs.getInt("uy_tr_miccountry_id"), trx);
					paisDePaso = new org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto.PaisesDePaso.PaisDePaso();
					
					if (country.getStatus().equalsIgnoreCase(CrtStatus.ENALTA.toString()) || country.getStatus().equalsIgnoreCase(CrtStatus.ENBAJA.toString())){
	
						paisDePaso.setPaisDePasoNumeroSecuencial(country.getNumero());
						paisDePaso.setPaisDePasoCodigo(rs.getString("iso_code"));
						
						ciudad = new MCiudad(ctx, country.getUY_Ciudad_ID(), trx);
						paisDePaso.setCiudadEntradaCodigo(ciudad.getCode());
						aduana = new MTRAduana(ctx, country.getUY_TR_Aduana_ID(), trx);
						paisDePaso.setAduanaDeEntradaCodigo(aduana.getValue());
						lugarOperativo = new MTROperativeSite(ctx, country.getUY_TR_OperativeSite_ID(), trx);
						paisDePaso.setLugarOperativoEntradaCodigo(lugarOperativo.getValue());
						
						ciudad = new MCiudad(ctx, country.getUY_Ciudad_ID_1(), trx);
						paisDePaso.setCiudadSalidaCodigo(ciudad.getCode());
						aduana = new MTRAduana(ctx, country.getUY_TR_Aduana_ID_1(), trx);
						paisDePaso.setAduanaDeEntradaCodigo(aduana.getValue());
						lugarOperativo = new MTROperativeSite(ctx, country.getUY_TR_OperativeSite_ID_1(), trx);
						paisDePaso.setLugarOperativoEntradaCodigo(lugarOperativo.getValue());
						
						// Seteo el tipo de operacion
						if (country.getStatus().equalsIgnoreCase(CrtStatus.ENALTA.toString())) {
							paisDePaso.setRegistroTipo("A");
							paisDePaso.setPaisDePasoNumeroSecuencial(country.getNumero());
						} else if (country.getStatus().equalsIgnoreCase(CrtStatus.ENBAJA.toString())) {
							paisDePaso.setRegistroTipo("B");
							paisDePaso.setPaisDePasoNumeroSecuencial(country.getNumero());
						}
						paisesDePaso.getPaisDePaso().add(paisDePaso);
					
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (paisesDePaso.getPaisDePaso().size() != 0) {
			objMic.setPaisesDePaso(paisesDePaso);
		}
		
	}
	
	private void cargarDatosPruebaMIC(Manifiesto objMic) {
		objMic.setAgenteTransportistaTipoDocumento("4");
		objMic.setAgenteTransportistaDocumento("213374740016");
		objMic.setManifiestoOriginalNumero("13AR035583H");
		objMic.setAgenteTransportistaNombre("ARDOINO SA");
		objMic.setAgenteTransportistaPais("858");
		objMic.setAgenteTransportistaDireccion("Montevideo");
		objMic.setLugarPartida("GUALEUGUAYCHU - ARGENTINA");
		objMic.setLugarDestino("MONTEVIDEO - ROU");
		objMic.setAduanaIngresoCodigo("006");
		objMic.setTractoraMatricula("NTP4331");
		objMic.setRemolqueMatricula("STP1137");
		objMic.setTransportistaEfectivo("Ardoino SA");
		objMic.setAgenteTransportistaRepresentanteTipoDoc("");
		objMic.setAgenteTransportistaDocumento("");
		objMic.setPaisOrigenCodigo("032");
		objMic.setPaisDestinoCodigo("858");
		objMic.setTransportistaEfectivoRepresentanteTipoDoc("");
		objMic.setTransportistaEfectivoRepresentanteDoc("");
		objMic.setTransportistaEfectivoTipoDocumento("4");
		objMic.setTransportistaEfectivoDocumento("213374740016");
		objMic.setRemolqueDuenoTipoDocumento("4");
		objMic.setRemolqueDuenoDocumento("213374740016");
		objMic.setLastre("N");
		objMic.setConductorNombre("JAVIER PEREZ");
		objMic.setConductorTipoDocumento("2");
		objMic.setConductorDocumento("39884134");
		objMic.setPlazoOrigenDestino((long)002);
		objMic.setRutaDescripcion("CHUY URUGUAY CHUI BRASIL");
		objMic.setCiudadPartidaCodigo("San jo");
		objMic.setCiudadSalidaCodigo("UY CHY");
		objMic.setCiudadEntradaCodigo("UY CHY");
		objMic.setAduanaEntradaCodigo("BR");
		objMic.setLugarOperativoEntradaCodigo("BR");
		objMic.setCiudadDestinoCodigo("UY CHY");
		objMic.setAduanaDestinoCodigo("BR");
		objMic.setLugarOperativoDestinoCodigo("BR");
		objMic.setPaisTrasbordoCodigo("");
		objMic.setCiudadTrasbordoCodigo("");
		objMic.setAduanaTrasbordoCodigo("");
		objMic.setLugarOperativoTrasbordoCodigo("");
		objMic.setTransitoAduanero("N");
		objMic.setAgenteTransportistaRepresentanteDocExtranjero("");
		objMic.setTransportistaEfectivoRepresentanteDocExtranjero("");
		objMic.setMedioTransporteCapacidadArrastre(new BigDecimal(45));
		objMic.setMedioTransporteAnoFabricacion((long)1998);
		objMic.setDocumentoPrecedenteTipo("");
		objMic.setMicPrecedenteTipoTransporte("");
		objMic.setMicPrecedenteTipo("");
		objMic.setMicPrecedenteNumero("");

	}

	/***
	 * OpenUp Ltda. Issue #2372
	 * @author Nicolas Sarlabos - 29/07/2014
	 * Metodo que recorre los CRT del MIC y carga sus datos en las variables del objeto a enviar.
	 */
	protected void recorridaCRT(MTRMic mic, DAE.Objeto.Manifiestos.Manifiesto objMic, TipoOperacion tipoOp){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			acuConocimientoNumeroSecuencial = 0;

			//cargo CRT del cabezal
			DTOMIcContCrt dtoMicContCrt = new DTOMIcContCrt();
			dtoMicContCrt.setSecNroCrt(mic.getSecNoCrt());
			dtoMicContCrt.setNroLinea(mic.getSecNoLine1());
			dtoMicContCrt.setNroImg(mic.getSecNoImg1());
			dtoMicContCrt.setCrtStatus(CrtStatus.valueOf(mic.getCrtStatus1()));
			dtoMicContCrt.setCrtLineStatus(LineImgStatus.valueOf(mic.getCrtLineStatus1()));
			dtoMicContCrt.setCrtImgStatus(LineImgStatus.valueOf(mic.getCrtImgStatus1()));
			dtoMicContCrt.setNroCrtImgVinculada(mic.getCrtImgNum1());
			if(mic.getUY_TR_Crt_ID() > 0) loadConocimiento(mic.getUY_TR_Crt_ID(), mic, objMic, tipoOp, 0, true, false, dtoMicContCrt);
			mic.setCrtImgNum1(dtoMicContCrt.getNroCrtImgVinculada());
			mic.saveEx();

			//recorro las continuaciones
			sql = "select uy_tr_miccont_id, uy_tr_crt_id, uy_tr_crt_id_1" +
					" from uy_tr_miccont" +
					" where uy_tr_mic_id = " + mic.get_ID() +
					" order by sheet asc";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){
				MTRMicCont micCont = new MTRMicCont(ctx,  rs.getInt("uy_tr_miccont_id"), trx);

				if(rs.getInt("uy_tr_crt_id") > 0) {
					DTOMIcContCrt dtoContCrt = new DTOMIcContCrt();
					dtoContCrt.setSecNroCrt(micCont.getSecNoCrt());
					dtoContCrt.setNroLinea(micCont.getSecNoLine1());
					dtoContCrt.setNroImg(micCont.getSecNoImg1());
					dtoContCrt.setCrtStatus(CrtStatus.valueOf(micCont.getCrtStatus1()));
					dtoContCrt.setCrtLineStatus(LineImgStatus.valueOf(micCont.getCrtLineStatus1()));
					dtoContCrt.setCrtImgStatus(LineImgStatus.valueOf(micCont.getCrtImgStatus1()));
					dtoContCrt.setNroCrtImgVinculada(micCont.getCrtImgNum1());
					loadConocimiento(rs.getInt("uy_tr_crt_id"), mic, objMic, tipoOp, rs.getInt("uy_tr_miccont_id"), false, false, dtoContCrt); //mando cargar el primer CRT
					micCont.setCrtImgNum1(dtoContCrt.getNroCrtImgVinculada());
					micCont.saveEx();
				}
				if(rs.getInt("uy_tr_crt_id_1") > 0) {
					DTOMIcContCrt dtoContCrt = new DTOMIcContCrt();
					dtoContCrt.setSecNroCrt(micCont.getSecNoCrt2());
					dtoContCrt.setNroLinea(micCont.getSecNoLine2());
					dtoContCrt.setNroImg(micCont.getSecNoImg2());
					dtoContCrt.setCrtStatus(CrtStatus.valueOf(micCont.getCrtStatus2()));
					dtoContCrt.setCrtLineStatus(LineImgStatus.valueOf(micCont.getCrtLineStatus2()));
					dtoContCrt.setCrtImgStatus(LineImgStatus.valueOf(micCont.getCrtImgStatus2()));
					dtoContCrt.setNroCrtImgVinculada(micCont.getCrtImgNum2());
					loadConocimiento(rs.getInt("uy_tr_crt_id_1"), mic, objMic, tipoOp, rs.getInt("uy_tr_miccont_id"), false, false, dtoContCrt); //mando cargar el segundo CRT
					micCont.setCrtImgNum2(dtoContCrt.getNroCrtImgVinculada());
					micCont.saveEx();
				}

			}
			
			// Recorro los crt para eliminar que estan en la tabla MicTask
			sql = "select uy_tr_mictask_id, uy_tr_crt_id from uy_tr_mictask where uy_tr_mic_id = " + mic.get_ID();
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){
				if(rs.getInt("uy_tr_mictask_id") > 0) {
					DTOMIcContCrt dtoContCrt = new DTOMIcContCrt();
					MTRMicTask mTask = new MTRMicTask(ctx, rs.getInt("uy_tr_mictask_id"), trx);
					
					dtoContCrt.setNroCrtImgVinculada(mTask.getCrtImgNum());
					dtoContCrt.setSecNroCrt(mTask.getSecNoCrt());
					dtoContCrt.setCrtLineStatus(LineImgStatus.valueOf(mTask.getCrtLineStatus()));
					dtoContCrt.setCrtImgStatus(LineImgStatus.valueOf(mTask.getCrtImgStatus()));
					dtoContCrt.setCrtStatus(CrtStatus.valueOf(mTask.getCrtStatus()));
					loadConocimiento(rs.getInt("uy_tr_crt_id"), mic, objMic, tipoOp, 0, false, true, dtoContCrt); //mando cargar el segundo CRT
					
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

	protected void loadConocimiento(int idCrt, MTRMic mic, DAE.Objeto.Manifiestos.Manifiesto objMic, TipoOperacion tipoOperacionCrt, int contID, boolean isHeader, boolean isCrtForDelete, DTOMIcContCrt dtoContCrt){
		DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento objCrt = new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento();
		if (objMic.getConocimientos() == null ) objMic.setConocimientos(new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos());

		String sql = "", value = "";
		MTRCrt crt = null;
		MTRMicCont cont = null;
		MTRTrip trip = null;
		MBPartner partner = null;

		acuConocimientoNumeroSecuencial++;

		/*
		 *  TABLA: Conocimientos de Embarque (YCGCON)
		 *  Conocimientos
		 */
		/* 6 */ int conocimientoNumeroSecuencial = acuConocimientoNumeroSecuencial; // Si es un alta es un incremental
		/* 7 */ String conocimientoOriginalNumero = "";
		/* 16 */ String consignatarioNombre = "";//VERIFICAR
		/* 20 */ String remitenteTipoDocumento = "";
		/* 21 */ String remitenteDocumento = "";
		/* 22 */ String remitenteNombre = "";
		/* 23 */ String remitenteDireccion = "";
		/* 24 */ String destinatarioTipoDocumento = "";
		/* 25 */ String destinatarioDocumento = "";
		/* 26 */ String destinatarioNombre = "";
		/* 27 */ String destinatarioDireccion = "";
		/* 28 */ String mercaderiaDestinoFinal = "";
		/* 29 */ String mercaderiaPeligrosa = "";
		/* 30 */ BigDecimal fleteMonto = new BigDecimal(0);
		/* 31 */ BigDecimal seguroMonto = new BigDecimal(0);
		/* 32 */ String consolidado = "";
		/* 33 */ String fraccionado = "";
		
		//OpenUp. Nicolas Sarlabos. 05/05/2015. #3911. Obtengo linea de CRT para este CRT y MIC
		MTRMicLine line = MTRMicLine.forCRT(ctx, idCrt, mic.get_ID(), trx);
		//Fin OpenUp.

		if(isHeader){

			crt = new MTRCrt(ctx,idCrt,trx);

			if(crt.get_ID() > 0){

				trip = new MTRTrip(ctx,crt.getUY_TR_Trip_ID(),trx);

				conocimientoOriginalNumero = crt.getNumero().replace(" ", "");

				if(trip.isDangerous()){

					mercaderiaPeligrosa = "S";

				} else mercaderiaPeligrosa = "N";

				fleteMonto = mic.getAmount().setScale(2, RoundingMode.HALF_UP);
				seguroMonto = mic.getSeguro().setScale(2, RoundingMode.HALF_UP);

				if(trip.isConsolidated()){

					consolidado = "S";

				} else consolidado = "N";

				if(mic.getpesoBruto().compareTo(crt.getWeight()) < 0){

					fraccionado = "S";				

				} else fraccionado = "N"; 
			}			

		} else if (!isHeader && !isCrtForDelete){

			cont = new MTRMicCont(ctx, contID, trx);

			if(cont.get_ID() > 0){

				//si el CRT recibido por parametro es el primero de la continuacion
				if(cont.getUY_TR_Crt_ID() == idCrt){

					crt = new MTRCrt(ctx,idCrt,trx);

					if(crt.get_ID() > 0){

						trip = new MTRTrip(ctx,crt.getUY_TR_Trip_ID(),trx);

						conocimientoOriginalNumero = crt.getNumero().replace(" ", "");

						if(trip.isDangerous()){

							mercaderiaPeligrosa = "S";

						} else mercaderiaPeligrosa = "N";

						fleteMonto = cont.getAmount().setScale(2, RoundingMode.HALF_UP);
						seguroMonto = cont.getSeguro().setScale(2, RoundingMode.HALF_UP);

						if(trip.isConsolidated()){

							consolidado = "S";

						} else consolidado = "N";

						if(mic.getpesoBruto().compareTo(crt.getWeight()) < 0){

							fraccionado = "S";				

						} else fraccionado = "N"; 						
					}					

				} else if(cont.getUY_TR_Crt_ID_1() == idCrt){ //si el CRT recibido por parametro es el segundo de la continuacion

					crt = new MTRCrt(ctx,idCrt,trx);

					if(crt.get_ID() > 0){

						trip = new MTRTrip(ctx,crt.getUY_TR_Trip_ID(),trx);

						conocimientoOriginalNumero = crt.getNumero().replace(" ", "");

						if(trip.isDangerous()){

							mercaderiaPeligrosa = "S";

						} else mercaderiaPeligrosa = "N";

						fleteMonto = cont.getAmount2().setScale(2, RoundingMode.HALF_UP);
						seguroMonto = cont.getSeguro2().setScale(2, RoundingMode.HALF_UP);

						if(trip.isConsolidated()){

							consolidado = "S";

						} else consolidado = "N";

						if(cont.getpesoBruto2().compareTo(crt.getWeight()) < 0){

							fraccionado = "S";				

						} else fraccionado = "N"; 						
					}					
				}			

			}			
		}	

		if(crt != null && trip != null){

			//se cargan datos del remitente
			partner = new MBPartner(ctx,trip.getC_BPartner_ID_To(),trx);

			if(partner!=null){

				if(partner.getName2()!=null && !partner.getName2().equalsIgnoreCase("")) 
					
					remitenteNombre = partner.getName2().toUpperCase();
					remitenteNombre = remitenteNombre.replace("'", "");

				if(partner.getDUNS()!=null && !partner.getDUNS().equalsIgnoreCase("")){

					remitenteTipoDocumento = "4";
					remitenteDocumento = partner.getDUNS();				

				}

				sql = "select coalesce(loc.address1,null)" +
						" from c_bpartner_location loc" +
						" inner join uy_tr_trip t on loc.c_bpartner_location_id = t.c_bpartner_location_id_2" +
						" where t.uy_tr_trip_id = " + trip.get_ID();
				value = DB.getSQLValueStringEx(trx, sql);

				if(value != null) {
					
					remitenteDireccion = value;
					remitenteDireccion = remitenteDireccion.replace("'", " ");
				}

			}

			//se cargan datos del destinatario
			partner = new MBPartner(ctx,trip.getC_BPartner_ID_From(),trx);

			if(partner!=null){

				if(partner.getName2()!=null && !partner.getName2().equalsIgnoreCase("")) {

					destinatarioNombre = partner.getName2().toUpperCase();
					destinatarioNombre = destinatarioNombre.replace("'", " "); 

					consignatarioNombre = destinatarioNombre;
				}

				if(mic.getType() != null && mic.getType().equalsIgnoreCase("0")){

					destinatarioTipoDocumento = "4";

					if(line!=null && line.get_ID()>0){

						if(line.getDUNS()!=null && !line.getDUNS().equalsIgnoreCase("")){

							destinatarioDocumento = line.getDUNS();

						} else if (partner.getDUNS()!=null && !partner.getDUNS().equalsIgnoreCase("")) destinatarioDocumento = partner.getDUNS();				

					}
				}

				sql = "select coalesce(loc.address1,null)" +
						" from c_bpartner_location loc" +
						" inner join uy_tr_trip t on loc.c_bpartner_location_id = t.c_bpartner_location_id" +
						" where t.uy_tr_trip_id = " + trip.get_ID();
				value = DB.getSQLValueStringEx(trx, sql);

				if(value != null){
					
					destinatarioDireccion = value;
					destinatarioDireccion = destinatarioDireccion.replace("'", " ");
				}
				
			}	

			MCiudad city = new MCiudad(ctx,trip.getUY_Ciudad_ID_1(),trx);
			mercaderiaDestinoFinal = city.getName();

		}					

		if (dtoContCrt.getCrtStatus() == CrtStatus.ENMODIFICACION || dtoContCrt.getCrtStatus() == CrtStatus.ENBAJA || dtoContCrt.getCrtStatus() == CrtStatus.VINCULADO){
			// Si no es un alta, ya se cuenta con un numero de secuencial
			conocimientoNumeroSecuencial = Integer.valueOf(dtoContCrt.getSecNroCrt());	
		}

		switch (dtoContCrt.getCrtStatus()){
		case ENALTA:
			objCrt.setConocimientoNumeroSecuencial(conocimientoNumeroSecuencial);
			objCrt.setConocimientoOriginalNumero(conocimientoOriginalNumero.toUpperCase());
			objCrt.setRegistroTipo("A");
			objCrt.setConsignatarioNombre(consignatarioNombre);
			objCrt.setRemitenteTipoDocumento(remitenteTipoDocumento);
			objCrt.setRemitenteDocumento(remitenteDocumento);
			objCrt.setRemitenteNombre(remitenteNombre);
			objCrt.setRemitenteDireccion(remitenteDireccion);
			objCrt.setDestinatarioTipoDocumento(destinatarioTipoDocumento);
			objCrt.setDestinatarioDocumento(destinatarioDocumento);
			objCrt.setDestinatarioNombre(destinatarioNombre);
			objCrt.setDestinatarioDireccion(destinatarioDireccion);
			objCrt.setMercaderiaDestinoFinal(mercaderiaDestinoFinal);
			objCrt.setMercaderiaPeligrosa(mercaderiaPeligrosa);
			objCrt.setFleteMonto(fleteMonto);
			objCrt.setSeguroMonto(seguroMonto);
			objCrt.setConsolidado(consolidado);
			objCrt.setFraccionado(fraccionado);

			break;
		case ENMODIFICACION:
			objCrt.setConocimientoNumeroSecuencial(conocimientoNumeroSecuencial);
			objCrt.setConocimientoOriginalNumero(conocimientoOriginalNumero.toUpperCase());
			objCrt.setRegistroTipo("M");
			objCrt.setConsignatarioNombre(consignatarioNombre);
			objCrt.setRemitenteTipoDocumento(remitenteTipoDocumento);
			objCrt.setRemitenteDocumento(remitenteDocumento);
			objCrt.setRemitenteNombre(remitenteNombre);
			objCrt.setRemitenteDireccion(remitenteDireccion);
			objCrt.setDestinatarioTipoDocumento(destinatarioTipoDocumento);
			objCrt.setDestinatarioDocumento(destinatarioDocumento);
			objCrt.setDestinatarioNombre(destinatarioNombre);
			objCrt.setDestinatarioDireccion(destinatarioDireccion);
			objCrt.setMercaderiaDestinoFinal(mercaderiaDestinoFinal);
			objCrt.setMercaderiaPeligrosa(mercaderiaPeligrosa);
			objCrt.setFleteMonto(fleteMonto);
			objCrt.setSeguroMonto(seguroMonto);
			objCrt.setConsolidado(consolidado);
			objCrt.setFraccionado(fraccionado);

			break;
		case ENBAJA:
			objCrt.setConocimientoNumeroSecuencial(conocimientoNumeroSecuencial);
			objCrt.setConocimientoOriginalNumero(conocimientoOriginalNumero.toUpperCase());
			objCrt.setRegistroTipo("B");

			break;
		case VINCULADO:
			objCrt.setConocimientoNumeroSecuencial(conocimientoNumeroSecuencial);
			objCrt.setConocimientoOriginalNumero(conocimientoOriginalNumero.toUpperCase());

			break;
		case DESVINCULADO:
			// No se quiere operar con el crt actual

			break;
		}

		if (dtoContCrt.getCrtStatus() == CrtStatus.ENALTA || dtoContCrt.getCrtStatus() == CrtStatus.ENBAJA || dtoContCrt.getCrtStatus() == CrtStatus.ENMODIFICACION || dtoContCrt.getCrtStatus() == CrtStatus.VINCULADO){
			// Si es una alta baja o modificación opero con las lineas e imágenes, o si esta vinculado pero las lineas o imagenes no

			boolean tieneLineasParaCargar = false;
			boolean tieneImgParaCargar = false;
			
			// Cargar la linea del conocimiento
			tieneLineasParaCargar = loadLineaConocimiento(mic, cont, crt, objCrt, dtoContCrt, isHeader);

			// Cargar el código de la imagen del conocimiento
			tieneImgParaCargar = loadImagen(crt, objCrt, dtoContCrt);
			
			if(dtoContCrt.getCrtStatus() == CrtStatus.ENALTA 
					|| dtoContCrt.getCrtStatus() == CrtStatus.ENBAJA 
					|| dtoContCrt.getCrtStatus() == CrtStatus.ENMODIFICACION 
					||  tieneLineasParaCargar 
					|| tieneImgParaCargar) {
				// Agrego el crt al objeto mic
				objMic.getConocimientos().getConocimiento().add(objCrt);
			}

		}
	}

	protected boolean loadLineaConocimiento(MTRMic mic, MTRMicCont cont, MTRCrt crt, DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento objCrt, DTOMIcContCrt dtoContCrt, boolean isHeader){
		boolean tieneLineasParaCargar = false;
		DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Lineas.Linea objLineaCrt = new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Lineas.Linea();
		if (objCrt.getLineas() == null) objCrt.setLineas(new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Lineas());
		

		String sql = "";
		MTRTrip trip = null;

		/*
		 * TABLA: "LINEAS de Conocimiento de Embarque (YCGLIN)"
		 * Linea del CRT
		 */
//		/* 7 */ long lineaNumero = acuConocimientoNumeroSecuencial;
		
		// TODO: Se establece la linea en 1 debido a que solo habra una linea por CRT
		/* 7 */ long lineaNumero = 1;
		
		
		/* 9 */ BigDecimal pesoBruto = new BigDecimal(0);
		/* 10 */ String bultoTipo = "";
		/* 11 */ BigDecimal bultoCantidad = new BigDecimal(0);
		/* 13 */ String mercaderiaDescripcion = "";
		/* 14 */ String depositoFinalCodigo = "";
		/* 15 */ String mercaderiaPeligrosaCodigo = ""; //va a tabla de mercaderia peligrosa
		/* 16 */ String operacionTipo = "";
		/* 17 */ String actaTexto = "";
		/* 18 */ long actaNumero = 0;
		/* 19 */ BigDecimal valorDeclaradoImporte = new BigDecimal(0);
		/* 20 */ String valorDeclaradoMoneda = "";

		if(isHeader){

			if(crt.get_ID() > 0){

				trip = new MTRTrip(ctx,crt.getUY_TR_Trip_ID(),trx);

				pesoBruto = mic.getpesoBruto().setScale(3, RoundingMode.HALF_UP);

				if(mic.getUY_TR_PackageType_ID() > 0){

					MTRPackageType pack = new MTRPackageType(ctx, mic.getUY_TR_PackageType_ID(), trx);
					bultoTipo = pack.getValue();				

				}

				bultoCantidad = mic.getQtyPackage().setScale(3, RoundingMode.HALF_UP);

				if(trip.getM_Product_ID() > 0){

					MProduct prod = new MProduct(ctx,trip.getM_Product_ID(),trx);
					mercaderiaDescripcion = prod.getName();			

				}

				//operacionTipo = tipoOperacionLineaCrt.toString();
				valorDeclaradoImporte = mic.getImporte().setScale(2, RoundingMode.HALF_UP);

				sql = "select coalesce(code,'') from c_currency where c_currency_id = " + mic.getC_Currency_ID();
				valorDeclaradoMoneda = DB.getSQLValueStringEx(trx, sql);				

			}			

		} else{

			if(cont.get_ID() > 0){

				//si el CRT recibido por parametro es el primero de la continuacion
				if(cont.getUY_TR_Crt_ID() == crt.get_ID()){

					trip = new MTRTrip(ctx,crt.getUY_TR_Trip_ID(),trx);

					pesoBruto = cont.getpesoBruto().setScale(3, RoundingMode.HALF_UP);

					if(cont.getUY_TR_PackageType_ID() > 0){

						MTRPackageType pack = new MTRPackageType(ctx, cont.getUY_TR_PackageType_ID(), trx);
						bultoTipo = pack.getValue();				

					}

					bultoCantidad = cont.getQtyPackage().setScale(3, RoundingMode.HALF_UP);

					if(trip.getM_Product_ID() > 0){

						MProduct prod = new MProduct(ctx,trip.getM_Product_ID(),trx);
						mercaderiaDescripcion = prod.getName();			

					}

					//operacionTipo = tipoOperacionLineaCrt.toString();
					valorDeclaradoImporte = cont.getImporte().setScale(2, RoundingMode.HALF_UP);

					sql = "select coalesce(code,'') from c_currency where c_currency_id = " + cont.getC_Currency_ID();
					valorDeclaradoMoneda = DB.getSQLValueStringEx(trx, sql);	


				} else if(cont.getUY_TR_Crt_ID_1() == crt.get_ID()){ //si el CRT recibido por parametro es el segundo de la continuacion

					trip = new MTRTrip(ctx,crt.getUY_TR_Trip_ID(),trx);

					pesoBruto = cont.getpesoBruto2().setScale(3, RoundingMode.HALF_UP);

					if(cont.getUY_TR_PackageType_ID_1() > 0){

						MTRPackageType pack = new MTRPackageType(ctx, cont.getUY_TR_PackageType_ID_1(), trx);
						bultoTipo = pack.getValue();				

					}

					bultoCantidad = cont.getQtyPackage2().setScale(3, RoundingMode.HALF_UP);

					if(trip.getM_Product_ID() > 0){

						MProduct prod = new MProduct(ctx,trip.getM_Product_ID(),trx);
						mercaderiaDescripcion = prod.getName();			

					}

					//operacionTipo = tipoOperacionLineaCrt.toString();
					valorDeclaradoImporte = cont.getImporte2().setScale(2, RoundingMode.HALF_UP);

					sql = "select coalesce(code,'') from c_currency where c_currency_id = " + cont.getC_Currency2_ID();
					valorDeclaradoMoneda = DB.getSQLValueStringEx(trx, sql);				

				}			

			}			
		}				

		switch (dtoContCrt.getCrtStatus()){
		case ENALTA:
			objLineaCrt.setLineaNumero(lineaNumero);
			objLineaCrt.setRegistroTipo("A");
			objLineaCrt.setPesoBruto(pesoBruto);
			objLineaCrt.setBultoTipo(bultoTipo);
			objLineaCrt.setBultoCantidad(bultoCantidad);
			objLineaCrt.setMercaderiaDescripcion(mercaderiaDescripcion);
			objLineaCrt.setDepositoFinalCodigo(depositoFinalCodigo);
			objLineaCrt.setMercaderiaPeligrosaCodigo(mercaderiaPeligrosaCodigo);
			objLineaCrt.setOperacionTipo(operacionTipo);
			objLineaCrt.setActaTexto(actaTexto);
			objLineaCrt.setActaNumero(actaNumero);
			objLineaCrt.setValorDeclaradoImporte(valorDeclaradoImporte);
			objLineaCrt.setValorDeclaradoMoneda(valorDeclaradoMoneda);
			tieneLineasParaCargar = true;
			
			break;
		case ENMODIFICACION:
			objLineaCrt.setLineaNumero(lineaNumero);
			if (dtoContCrt.getCrtLineStatus() == LineImgStatus.CARGADO){
				objLineaCrt.setRegistroTipo("M");
			} else if (dtoContCrt.getCrtLineStatus() == LineImgStatus.SINCARGAR){
				objLineaCrt.setRegistroTipo("A");
			}
			objLineaCrt.setPesoBruto(pesoBruto);
			objLineaCrt.setBultoTipo(bultoTipo);
			objLineaCrt.setBultoCantidad(bultoCantidad);
			objLineaCrt.setMercaderiaDescripcion(mercaderiaDescripcion);
			objLineaCrt.setDepositoFinalCodigo(depositoFinalCodigo);
			objLineaCrt.setMercaderiaPeligrosaCodigo(mercaderiaPeligrosaCodigo);
			objLineaCrt.setOperacionTipo(operacionTipo);
			objLineaCrt.setActaTexto(actaTexto);
			objLineaCrt.setActaNumero(actaNumero);
			objLineaCrt.setValorDeclaradoImporte(valorDeclaradoImporte);
			objLineaCrt.setValorDeclaradoMoneda(valorDeclaradoMoneda);
			tieneLineasParaCargar = true;
			
			break;
		case ENBAJA:
			if (dtoContCrt.getCrtLineStatus() == LineImgStatus.CARGADO) {
				objLineaCrt.setLineaNumero(lineaNumero);
				objLineaCrt.setRegistroTipo("B");
				tieneLineasParaCargar = true;
			}

			break;
		case VINCULADO:
			// Por si el CRT se cargo pero dio un error al cargar la linea, reviso que este cargada
			if (dtoContCrt.getCrtLineStatus() == LineImgStatus.SINCARGAR) {
				objLineaCrt.setLineaNumero(lineaNumero);
				objLineaCrt.setRegistroTipo("A");
				objLineaCrt.setPesoBruto(pesoBruto);
				objLineaCrt.setBultoTipo(bultoTipo);
				objLineaCrt.setBultoCantidad(bultoCantidad);
				objLineaCrt.setMercaderiaDescripcion(mercaderiaDescripcion);
				objLineaCrt.setDepositoFinalCodigo(depositoFinalCodigo);
				objLineaCrt.setMercaderiaPeligrosaCodigo(mercaderiaPeligrosaCodigo);
				objLineaCrt.setOperacionTipo(operacionTipo);
				objLineaCrt.setActaTexto(actaTexto);
				objLineaCrt.setActaNumero(actaNumero);
				objLineaCrt.setValorDeclaradoImporte(valorDeclaradoImporte);
				objLineaCrt.setValorDeclaradoMoneda(valorDeclaradoMoneda);
				tieneLineasParaCargar = true;
			} else if (dtoContCrt.getCrtLineStatus() == LineImgStatus.CARGADO) {
				objLineaCrt.setLineaNumero(lineaNumero);
			}
			break;
		}
		
		// OpenUp. Raul Capecce. issue #3321 16/07/2015
		// En caso de que falle la asociación, se deshabilita pasando a false este system configurator
		if (MSysConfig.getBooleanValue("UY_ADUANA_ASOCIACION", false, Env.getAD_Client_ID(ctx))){
			// TODO: Descomentar: Asociacion del Dua en la Linea
			// Cargo la asociación en la linea del CRT
			switch (dtoContCrt.getCrtStatus()) {
			//case ENALTA: Para la asociación se requiere que el CRT ya esté asociado
			case VINCULADO:
			case ENMODIFICACION:
			case ENBAJA:
				if (loadAsociacionDua(objLineaCrt, mic, cont, crt)) {
					tieneLineasParaCargar = true;
				}
			}
		}
		
		
		
		if (tieneLineasParaCargar){
			objCrt.getLineas().getLinea().add(objLineaCrt);
		}
		return tieneLineasParaCargar;
	}
	
	// OpenUp. Raul Capecce. issue #3321 09/04/2015
	// Carga las asociaciones para este CRT
	protected boolean loadAsociacionDua(DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Lineas.Linea objLineaCrt, MTRMic mic, MTRMicCont cont, MTRCrt crt){
		boolean tieneAsociacionesParaCargar = false;
		String sql = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Lineas.Linea.Asociaciones.Asociacion objAsociacion;
		
		try{
		
			// Seleccionar todas las asociaciones en la UY_TR_DuaLink para este CRT
			sql = "SELECT * FROM UY_TR_DuaLink WHERE UY_TR_Crt_ID = " + crt.getUY_TR_Crt_ID();
			pstmt = DB.prepareStatement (sql, trx);
			rs = pstmt.executeQuery ();
			
			while (rs.next()){
				if (rs.getInt("UY_TR_Crt_ID") > 0 && rs.getInt("UY_TR_DuaLink_ID") > 0){
					

					MTRDuaLink duaLink = new MTRDuaLink(ctx, rs.getInt("UY_TR_DuaLink_ID"), trx);
					MTRDua dua = new MTRDua(ctx, duaLink.getUY_TR_Dua_ID(), trx);
					
					// Determino si se tiene que enviar la asociacion en la linea o no
					// Segun el estado del objDuaLink y el check isDrop
					String statusAsoc = duaLink.getStatusAsociation();
					if (
							(statusAsoc.equalsIgnoreCase(CrtStatus.ENALTA.toString()) && !duaLink.isDrop()) ||
							(statusAsoc.equalsIgnoreCase(CrtStatus.ENMODIFICACION.toString()) && duaLink.isDrop()) ||
							(statusAsoc.equalsIgnoreCase(CrtStatus.ENBAJA.toString()) && duaLink.isDrop()) ||
							(statusAsoc.equalsIgnoreCase(CrtStatus.VINCULADO.toString()) && duaLink.isDrop())
							) {
					
						objAsociacion = new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Lineas.Linea.Asociaciones.Asociacion();
						objAsociacion.setDocumentoCodigo("19"); // Codigo de documento: DUA
						objAsociacion.setDocumentoNumero(dua.getNumeroDua());
						objAsociacion.setDocumentoAduanaCodigo(dua.getCodigoAduana());
						objAsociacion.setDocumentoAno(dua.getAnioDua());
						objAsociacion.setDocumentoLinea(duaLink.getNumeroSerieItemDua().toString()); // TODO: Revisar bigdecimal a tostring
						
						objAsociacion.setBultoCantidad(duaLink.getCantidadBultosAsociacion());
						objAsociacion.setBultoTipo(duaLink.gettipobulto());
						
						// UltAsoc no se especifica en el mapeo de los nombres viejos del webservice a los nombres nuevos
						
						
						// Establezco el tipo de operacion: Alta, baja, Modificacion, Vinculado.
						// Segun el estado del objDuaLink vinculado
						if (duaLink.isDrop()) {
							objAsociacion.setRegistroTipo("B");
						} else if (statusAsoc.equalsIgnoreCase(CrtStatus.ENALTA.toString())) {
							objAsociacion.setRegistroTipo("A");
						} else if (statusAsoc.equalsIgnoreCase(CrtStatus.ENMODIFICACION.toString())) {
							objAsociacion.setRegistroTipo("M");
						}
						
						
						
						if (objLineaCrt.getAsociaciones() == null) {
							objLineaCrt.setAsociaciones(new Asociaciones());
						}
						objLineaCrt.getAsociaciones().getAsociacion().add(objAsociacion);
						
						tieneAsociacionesParaCargar = true;
					}
				}
			}
			
		} catch (Exception ex){
			throw new AdempiereException("Asociación: " + ex.getMessage());
		}
		return tieneAsociacionesParaCargar;
	} // Fin #3321

	protected boolean loadImagen(MTRCrt crt, DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento objCrt, DTOMIcContCrt dtoContCrt){
		boolean tieneImgParaCargar = false;
		DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Imagenes.Imagen objImg1 = new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Imagenes.Imagen();
		DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Imagenes.Imagen objImg2 = new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Imagenes.Imagen();
		if (objCrt.getImagenes() == null) objCrt.setImagenes(new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Imagenes());
		

		/*
		 * TABLA: Imagen de conocimientos (YCGIMA)
		 * El código de la imagen para la linea del CRT 
		 */
		/* 7 */ int imagenNumeroSecuencial = acuConocimientoNumeroSecuencial;
		/* 8 */ long imagenNumero = 0;
		
		long imagenNumero_crt = 0;

		if(crt != null) {

			if(crt.getcodigo()!=null && !crt.getcodigo().equalsIgnoreCase("")){

				imagenNumero_crt = Long.parseLong(crt.getcodigo());
				imagenNumero = Long.parseLong(dtoContCrt.getNroCrtImgVinculada());

			} else throw new AdempiereException("No existe codigo de imagen asociado al CRT N° " + crt.getNumero());

		}

		switch (dtoContCrt.getCrtStatus()) {
		case ENALTA:
			objImg1.setImagenNumeroSecuencial(imagenNumeroSecuencial);
			dtoContCrt.setNroCrtImgVinculada(String.valueOf(imagenNumero_crt));
			objImg1.setImagenNumero(imagenNumero_crt);
			objImg1.setRegistroTipo("A");
			objCrt.getImagenes().getImagen().add(objImg1);
			tieneImgParaCargar = true;
			
			break;
		case ENBAJA:
			if (dtoContCrt.getCrtImgStatus() == LineImgStatus.CARGADO) {
				objImg1.setImagenNumeroSecuencial(imagenNumeroSecuencial);
				objImg1.setImagenNumero(imagenNumero);
				objImg1.setRegistroTipo("B");
				objCrt.getImagenes().getImagen().add(objImg1);
				tieneImgParaCargar = true;
			}
			
			break;
		case ENMODIFICACION:
			/*
			 * Aduana actualmente (08/2014) no soporta la modificacion de una imagen,
			 * por lo tanto hay que primero borrar la imagen anterior para despues agregar la nueva.
			 * Esto se realiza en dos envios.
			 */
			if (dtoContCrt.getCrtImgStatus() == LineImgStatus.CARGADO) {
				if (imagenNumero != imagenNumero_crt) {
					// Si el numerio que esta en el crt del manifiesto es distinto al de la tabla del crt, es porque hay que actualizar el numero en el mic
					// Borro la imagen acutal
					
					objImg1.setImagenNumeroSecuencial(imagenNumeroSecuencial);
					objImg1.setImagenNumero(imagenNumero);
					objImg1.setRegistroTipo("B");
					objCrt.getImagenes().getImagen().add(objImg1);
					tieneImgParaCargar = true;
				}
			} else if (dtoContCrt.getCrtImgStatus() == LineImgStatus.SINCARGAR) {
				// No esta cargado, lo doy de alta
				objImg1.setImagenNumeroSecuencial(imagenNumeroSecuencial);
				dtoContCrt.setNroCrtImgVinculada(String.valueOf(imagenNumero_crt));
				objImg1.setImagenNumero(imagenNumero_crt);
				objImg1.setRegistroTipo("A");
				objCrt.getImagenes().getImagen().add(objImg1);
				tieneImgParaCargar = true;
			}
			
			break;
		case VINCULADO:
			if (dtoContCrt.getCrtImgStatus() == LineImgStatus.CARGADO) {
				if (imagenNumero != imagenNumero_crt) {
					// Si el numerio que esta en el crt del manifiesto es distinto al de la tabla del crt, es porque hay que actualizar el numero en el mic
					// Borro la imagen acutal
					
					objImg1.setImagenNumeroSecuencial(imagenNumeroSecuencial);
					objImg1.setImagenNumero(imagenNumero);
					objImg1.setRegistroTipo("B");
					objCrt.getImagenes().getImagen().add(objImg1);
					tieneImgParaCargar = true;
				}
			} else if (dtoContCrt.getCrtImgStatus() == LineImgStatus.SINCARGAR) {
				objImg1.setImagenNumeroSecuencial(imagenNumeroSecuencial);
				dtoContCrt.setNroCrtImgVinculada(String.valueOf(imagenNumero_crt));
				objImg1.setImagenNumero(imagenNumero_crt);
				objImg1.setRegistroTipo("A");
				objCrt.getImagenes().getImagen().add(objImg1);
				tieneImgParaCargar = true;
			}
			
			break;
		}

		return tieneImgParaCargar;
	}

	//	protected DAE.Objeto.Manifiestos getManifiestos(int idMIC) {
	//		DAE.Objeto.Manifiestos ret = new DAE.Objeto.Manifiestos();
	//		ArrayList<Integer> idManifiestos = new ArrayList<Integer>();
	//
	//		String rut = "";
	//		MOrgInfo orgInfo = null;
	//		DAE dae = new DAE();
	//		DAE.Objeto objMic = new DAE.Objeto();
	//
	//		try {
	//			
	//			orgInfo =  MOrgInfo.get(ctx,Env.getAD_Org_ID(ctx),this.trx);
	//			if(orgInfo!=null){
	//				rut = orgInfo.getDUNS();
	//			}
	//			
	//			// DAE (Documento Aduanero Electrï¿½nico)
	//			dae.setObjeto(objMic);
	//			// dae.setSignature(signature);
	//			dae.setTipoDocumento(String.valueOf(4)); // Short, 4 es un nï¿½mero rut, dato proveido por Santiago De Pena, Concepto, Aduana
	//			dae.setIdDocumento(rut); // BigInteger, numero de rut de la empresa transportista, dato proveido por Santiago De Pena, Concepto, Aduana
	//			dae.setFechaHoraDocumentoElectronico(getXMLGregorianCalendar(new Date())); // XMLGregorianCalendar, fecha de envio del XML, dato proveido por Santiago De Pena, Concepto, Aduana
	//			dae.setCodigoIntercambio("WS_MANIFIESTO"); // String, especifica que lo que va en el tag objeto es un objeto de imagen para crt.
	//
	//			
	//			for (Integer idManifiesto : idManifiestos) {
	//				ret.getManifiesto().add(this.getManifiesto(idManifiesto));
	//			}
	//		} catch (Exception ex) {
	//
	//		}
	//		return ret;
	//	}
	//	
	//	protected DAE.Objeto.Manifiestos.Manifiesto getManifiesto(Integer idManifiesto){
	//		DAE.Objeto.Manifiestos.Manifiesto ret = new DAE.Objeto.Manifiestos.Manifiesto();
	//		ret.setTransporteTipo(null);
	//		ret.setManifiestoTipo(null);
	//		ret.setManifiestoNumero(null);
	//		ret.setRecinto(null);
	//		ret.setFechaArribo(0);
	//		ret.setRegistroTipo(null);
	//		ret.setAgenteTransportistaTipoDocumento(null);
	//		ret.setAgenteTransportistaDocumento(null);
	//		ret.setLugarPartidaCodigo(null);
	//		ret.setLugarDestinoCodigo(null);
	//		ret.setUltimoPuerto(null);
	//		ret.setMedioTransporteMatricula(null);
	//		ret.setMedioTransporteNacionalidad(null);
	//		ret.setObservacion(null);
	//		ret.setMedioTransporteNombre(null);
	//		ret.setOperacionTipo(null);
	//		ret.setManifiestoOriginalNumero(null);
	//		ret.setAgenteTransportistaNombre(null);
	//		ret.setAgenteTransportistaPais(null);
	//		ret.setAgenteTransportistaDireccion(null);
	//		ret.setLugarPartida(null);
	//		ret.setLugarDestino(null);
	//		ret.setAduanaIngresoCodigo(null);
	//		ret.setAduanaSalidaCodigo(null);
	//		ret.setFechaPasajeFrontera(null);
	//		ret.setTractoraMatricula(null);
	//		ret.setRemolqueMatricula(null);
	//		ret.setSemiRemolqueMatricula(null);
	//		ret.setTransportistaEfectivo(null);
	//		ret.setAgenteTransportistaRepresentanteTipoDoc(null);
	//		ret.setAgenteTransportistaRepresentanteDoc(null);
	//		ret.setPaisOrigenCodigo(null);
	//		ret.setPaisDestinoCodigo(null);
	//		ret.setTransportistaEfectivoRepresentanteTipoDoc(null);
	//		ret.setTransportistaEfectivoRepresentanteDoc(null);
	//		ret.setTransportistaEfectivoTipoDocumento(null);
	//		ret.setTransportistaEfectivoDocumento(null);
	//		ret.setRemolqueDuenoTipoDocumento(null);
	//		ret.setRemolqueDuenoDocumento(null);
	//		ret.setLastre(null);
	//		ret.setConductorNombre(null);
	//		ret.setConductorTipoDocumento(null);
	//		ret.setConductorDocumento(null);
	//		ret.setFechaLlegadaPrevista(null); // Gregorian Calendar
	//		ret.setPlazoOrigenDestino((long)0);
	//		ret.setRutaDescripcion(null);
	//		ret.setCiudadPartidaCodigo(null);
	//		ret.setCiudadSalidaCodigo(null);
	//		ret.setCiudadEntradaCodigo(null);
	//		ret.setAduanaEntradaCodigo(null);
	//		ret.setLugarOperativoEntradaCodigo(null);
	//		ret.setCiudadDestinoCodigo(null);
	//		ret.setAduanaDestinoCodigo(null);
	//		ret.setLugarOperativoDestinoCodigo(null);
	//		ret.setPaisTrasbordoCodigo(null);
	//		ret.setCiudadTrasbordoCodigo(null);
	//		ret.setAduanaTrasbordoCodigo(null);
	//		ret.setLugarOperativoTrasbordoCodigo(null);
	//		ret.setTransitoAduanero(null);
	//		ret.setAgenteTransportistaRepresentanteDocExtranjero(null);
	//		ret.setTransportistaEfectivoRepresentanteDocExtranjero(null);
	//		ret.setMedioTransporteCapacidadArrastre(null); // Big Decimal
	//		
	//		
	//		return ret;
	//	}
	//
	//	protected DAE.Objeto.Manifiestos.Manifiesto.Conocimientos getConocimientos(Integer idManifiesto){
	//		DAE.Objeto.Manifiestos.Manifiesto.Conocimientos ret = new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos();
	//		ArrayList<Integer> idConocimientos = new ArrayList<Integer>();
	//		// Load here!!!
	//		for (Integer idConocimiento : idConocimientos){
	//			ret.getConocimiento().add(this.getConocimiento(idConocimiento));
	//		}
	//		
	//		return ret;
	//	}
	//	
	//	protected DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento getConocimiento(Integer idManifiesto){
	//		DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento ret = new DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento();
	//		
	//		return ret;
	//	}
	//	
	
	protected void MarcarParaEliminar(MTRMic mic, String trxName){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			mic.setMicStatus(MicStatus.ELIMINAR.toString());
			if (mic.getUY_TR_Crt_ID() > 0){
				mic.setCrtStatus1(CrtStatus.ENBAJA.toString());
			}
			mic.saveEx();
			
			//recorro las continuaciones
			sql = "select uy_tr_miccont_id, uy_tr_crt_id, uy_tr_crt_id_1" +
					" from uy_tr_miccont" +
					" where uy_tr_mic_id = " + mic.get_ID() +
					" order by sheet asc";
	
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
	
			while (rs.next()){
				MTRMicCont micCont = new MTRMicCont(ctx,  rs.getInt("uy_tr_miccont_id"), trx);
	
				if(rs.getInt("uy_tr_crt_id") > 0) {
					micCont.setCrtStatus1(CrtStatus.ENBAJA.toString());
				}
				if(rs.getInt("uy_tr_crt_id_1") > 0) {
					micCont.setCrtStatus2(CrtStatus.ENBAJA.toString());
				}
				
				micCont.saveEx();
			}
		}catch(Exception ex){
			throw new AdempiereException(ex.getMessage());
		}

	}
	
	protected XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws Exception {
		// * Current timestamp in XML format *
		GregorianCalendar calendarioGreg = new GregorianCalendar();
		XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
		Calendar cal = Calendar.getInstance();
		if (date != null){
			cal.setTime(date);
			xmlGregCal.setYear(cal.get(Calendar.YEAR));
			xmlGregCal.setMonth(cal.get(Calendar.MONTH) + 1);
			xmlGregCal.setDay(cal.get(Calendar.DAY_OF_MONTH));
			xmlGregCal.setHour(cal.get(Calendar.HOUR_OF_DAY));
			xmlGregCal.setMinute(cal.get(Calendar.MINUTE));
			xmlGregCal.setSecond(cal.get(Calendar.SECOND));
		}
		
		return xmlGregCal;
	}


}
