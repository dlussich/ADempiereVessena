package org.openup.aduana;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSysConfig;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.aduana.conexion.DAERespuesta;
import org.openup.aduana.conexion.DAERespuesta.Respuesta;
import org.openup.aduana.conexion.DAERespuesta.Respuesta.Referencia;
import org.openup.aduana.conexion.DAERespuesta.RespuestaTipo;
import org.openup.aduana.mic.dto.DAE;
import org.openup.model.MTRCrt;
import org.openup.model.MTRDuaLink;
import org.openup.model.MTRMic;
import org.openup.model.MTRMicCont;
import org.openup.model.MTRMicCountry;
import org.openup.model.MTRMicTask;

public class ConsultaRespuestaMic {

	public enum MicStatus {
		SINENVIAR,
		ELIMINAR,
		ENVIADO,
		MODIFICAR
	}
	public enum CrtStatus {
		DESVINCULADO,
		ENBAJA,
		ENALTA,
		ENMODIFICACION,
		VINCULADO
	}
	public enum LineImgStatus {
		CARGADO,
		SINCARGAR
	}
	
	public ConsultaRespuestaMic() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Procesando respuesta del mic: Aca se evalua la correctitud de la
	 * solicitud enviada y si es una respuesta sincr贸nica o asincr贸nica
	 */
	public static void consultarRespuestaAsincronica(DAERespuesta objRespuesta, MTRMic mic, int ad_client_id, int ad_org_id, String trxName, Properties ctx, int intentosRestantes) throws Exception{
	
		DAERespuesta.Respuesta respuesta;
		DAERespuesta objNuevaRespuesta;
		
		// Si es la primera pasada, va a la base a buscar la cantidad de iteraciones
		if (intentosRestantes == -1){
			String sqlIntentosRestantes = "select QtyCount from uy_tr_webservice"
					+ " where ad_client_id = " + ad_client_id
					+ " and  ad_org_id = " + ad_org_id;
			try{
				intentosRestantes = Integer.valueOf(DB.getSQLValueStringEx(trxName, String.valueOf(sqlIntentosRestantes)));
			}catch (Exception ex){
				intentosRestantes = 5;
			}
		}
		
		if (objRespuesta!=null){
			/*
			 * Si objRespuesta NO ES NULL, entonces es el PRIMER ENVIO del MIC
			 */
			if (objRespuesta.getRespuestas().size() > 0){
				respuesta = objRespuesta.getRespuestas().get(0);
	//			if (respuesta.getCodigo().equalsIgnoreCase("IM00")){
	//				// Operaci贸n exitosa
	//				DB.executeUpdateEx("UPDATE UY_TR_Mic SET responseText = '" + respuesta.getAyuda() + "', inProcess = '" + "N" +"' WHERE uy_tr_mic_id = " + micID, trxName);
	//				for (Respuesta.Referencia referencia : respuesta.getReferencias()){
	//					if (referencia.getCodigo().equalsIgnoreCase("ManifiestoNumero")){
	//						MTRMic mic = new MTRMic(ctx, micID, trxName);
	//						mic.setNroMic(referencia.getValor());
	//						mic.saveEx();
	//					}
	//				}
	//			}else if (respuesta.getCodigo().equalsIgnoreCase("DD13")){
				if (respuesta.getCodigo().equalsIgnoreCase("DD13")){
					/* Respuesta asincr贸nica */
					
					if (intentosRestantes > 0){
						/*
						 * Si a煤n no se ha superado el l铆mite de consultas parametrizado,
						 * consulto nuevamente.
						 */
						objNuevaRespuesta = consultarDaeManifiesto(mic, ad_client_id, ad_org_id, trxName, ctx);
						
						// Llamada recursiva, descuento uno al contador de intentos restante
						consultarRespuestaAsincronica(objNuevaRespuesta, mic, ad_client_id, ad_org_id, trxName, ctx, --intentosRestantes);
						
					}else{
						/* 
						 * Si ya se reintent贸 consultar el estado de la operaci贸n mas de lo parametrizado,
						 * se informa al usuario que la operaci贸n se est谩 procesando y que reintente luego.
						 * Se setea el atributo "inProgress" = "y" para que hasta que no consulte y obtenga un resultado como procesado, no deje editar los datos del mic
						*/
						DB.executeUpdateEx("UPDATE UY_TR_Mic SET responseText = '" + respuesta.getAyuda() + "', inProcess = '" + "Y" + "' WHERE uy_tr_mic_id = " + mic.getUY_TR_Mic_ID(), trxName);
					}
				} else {
					procesarRespuesta(objRespuesta, mic, ad_client_id, ad_org_id, trxName, ctx);
				}
	//			}else{
	//				// Error al llenar los datos, se informa del error
	//				
	//				// Se recorren todos los mensajes de error
	//				String msjsError = respuesta.getCodigo().replace("'", "") + "\n" +
	//						respuesta.getDescripcion().replace("'", "") + "\n" +
	//						respuesta.getAyuda().replace("'", "") + "\n\n";
	//				for (Respuesta.Referencia referencia : respuesta.getReferencias()){
	//					msjsError += "\t" + referencia.getCodigo().replace("'", "") + ": " + referencia.getValor().replace("'", "") + "\n";
	//				}
	//				
	//				String sqlError = "UPDATE UY_TR_Mic SET responseText = '" + msjsError + "', inProcess = '" + "N" + "' WHERE uy_tr_mic_id = " + micID;
	//				DB.executeUpdateEx(sqlError, trxName);
	//			}
			} else {
				// Si la respuesta esta vacia lo informo
				DB.executeUpdateEx("UPDATE UY_TR_Mic SET responseText = '" + "Sin respuesta de Aduana" + "' WHERE uy_tr_mic_id = " + mic.getUY_TR_Mic_ID(), trxName);
			}
		}else{
			/*
			 * Si objRespuesta ES NULL, entonces se quiere consultar un MIC que YA SE ENVI脫
			 */
			objNuevaRespuesta = consultarDaeManifiesto(mic, ad_client_id, ad_org_id, trxName, ctx);
			consultarRespuestaAsincronica(objNuevaRespuesta, mic, ad_client_id, ad_org_id, trxName, ctx, --intentosRestantes);
		}
	}

	protected static void procesarRespuesta(DAERespuesta daeRespuesta, MTRMic mic, int ad_client_id, int ad_org_id, String trxName, Properties ctx) throws Exception{
		
		String respuestaStr = "";
		
		boolean micStatusCorrecto = false;
		boolean micEliminado = false;
		
		
		// Verifico que se tenga que tener un tag xml para el mic
		if (mic.getMicStatus().equalsIgnoreCase(MicStatus.SINENVIAR.toString()) || mic.getMicStatus().equalsIgnoreCase(MicStatus.MODIFICAR.toString()) || mic.getMicStatus().equalsIgnoreCase(MicStatus.ELIMINAR.toString())){
			if (daeRespuesta.getRespuestaManifiesto() == null){
				micStatusCorrecto = false;
				//respuestaStr = daeRespuesta.getRespuestas().get(0).getCodigo() + " - " + daeRespuesta.getRespuestas().get(0).getDescripcion();
			} else {
				if (daeRespuesta.getRespuestaManifiesto().getCodigo().equalsIgnoreCase("1450")){
					// Codigo de exito
					MicStatus micStatus = null;
					if (!mic.isDrop()) {
						// Si no esta marcado para eliminar
						Respuesta respuestaMic = daeRespuesta.getRespuestaManifiesto();
						micStatusCorrecto = true;
						//micStatus = MicStatus.ENVIADO;
						mic.setMicStatus(MicStatus.ENVIADO.toString());
						mic.setNroMic(respuestaMic.getReferencia("ManifiestoNumero").getValor());
						mic.setRecinto(respuestaMic.getReferencia("Recinto").getValor());
						mic.saveEx(trxName);
					} else {
						// Si esta marcado para eliminar
						micStatusCorrecto = true;
						//micStatus = MicStatus.SINENVIAR;
						mic.setMicStatus(MicStatus.SINENVIAR.toString());
						mic.saveEx(trxName);
						micEliminado = true;
					}
					// El status lo seteo por sql porque lo filtra el before save del saveEx
					//DB.executeUpdateEx("UPDATE uy_tr_mic SET micStatus='" + micStatus.toString() + "' where uy_tr_mic_id = " + mic.getUY_TR_Mic_ID(), trxName);
				} else {
					micStatusCorrecto = false;
					//respuestaStr = daeRespuesta.getRespuestaManifiesto().getCodigo() + " - " + daeRespuesta.getRespuestaManifiesto().getDescripcion();
				}
			}
		} else {
			// No se tiene que checkear el mic
			micStatusCorrecto = true;
		}
		
		if (!micStatusCorrecto){
			// Si no es correcto el status del mic entonces guardo el error
			//DB.executeUpdateEx("UPDATE UY_TR_Mic SET responseText = '" + respuestaStr +"' WHERE uy_tr_mic_id = " + micID, trxName);
		} else {
			// Si es correcto el status del mic, sigo procesando los crt
			String sql = "";
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try{
				// Cargo el conocimiento de cabezal
				if(mic.getUY_TR_Crt_ID() > 0){
					MTRCrt crt = new MTRCrt(ctx, mic.getUY_TR_Crt_ID(), trxName);
					DTOMIcContCrt dtoMicCrt = new DTOMIcContCrt();
					if (crt != null){
						dtoMicCrt.setId(mic.getUY_TR_Crt_ID());
						dtoMicCrt.setConocimientoOriginalNumero(crt.getNumero());
						dtoMicCrt.setCrtStatus(CrtStatus.valueOf(mic.getCrtStatus1()));
						dtoMicCrt.setCrtLineStatus(LineImgStatus.valueOf(mic.getCrtLineStatus1()));
						dtoMicCrt.setCrtImgStatus(LineImgStatus.valueOf(mic.getCrtImgStatus1()));
						dtoMicCrt.setSecuencial(mic.getSecNoCrt());
						dtoMicCrt.setNroCrtImgVinculada(mic.getCrtImgNum1());
						
						procesarRespuestaConocimiento(daeRespuesta, dtoMicCrt, ctx, trxName, micEliminado);
						
						mic.setCrtStatus1(dtoMicCrt.getCrtStatus().toString());
						mic.setCrtLineStatus1(dtoMicCrt.getCrtLineStatus().toString());
						mic.setCrtImgStatus1(dtoMicCrt.getCrtImgStatus().toString());
						mic.setSecNoCrt(dtoMicCrt.getSecuencial());
						mic.setCrtImgNum1(dtoMicCrt.getNroCrtImgVinculada());
						mic.saveEx(trxName);
						DB.executeUpdateEx("update uy_tr_mic set crtstatus1 = '" + dtoMicCrt.getCrtStatus().toString() + "' where uy_tr_mic_id = " + mic.getUY_TR_Mic_ID(), trxName);
						
					}
				}
				//recorro las continuaciones
				sql = "select uy_tr_miccont_id, uy_tr_crt_id, uy_tr_crt_id_1" +
						" from uy_tr_miccont" +
						" where uy_tr_mic_id = " + mic.get_ID() +
						" order by sheet asc";

				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();

				while (rs.next()){
					MTRMicCont micCont = new MTRMicCont(ctx,  rs.getInt("uy_tr_miccont_id"), trxName);
					
					if(rs.getInt("uy_tr_crt_id") > 0) {
						MTRCrt crt = new MTRCrt(ctx, rs.getInt("uy_tr_crt_id"), trxName);
						DTOMIcContCrt dtoMicCrt = new DTOMIcContCrt();
						if (crt != null){
							dtoMicCrt.setId(rs.getInt("uy_tr_crt_id"));
							dtoMicCrt.setConocimientoOriginalNumero(crt.getNumero());
							dtoMicCrt.setCrtStatus(CrtStatus.valueOf(micCont.getCrtStatus1()));
							dtoMicCrt.setCrtLineStatus(LineImgStatus.valueOf(micCont.getCrtLineStatus1()));
							dtoMicCrt.setCrtImgStatus(LineImgStatus.valueOf(micCont.getCrtImgStatus1()));
							dtoMicCrt.setSecuencial(micCont.getSecNoCrt());
							dtoMicCrt.setNroCrtImgVinculada(micCont.getCrtImgNum1());
							
							procesarRespuestaConocimiento(daeRespuesta, dtoMicCrt, ctx, trxName, micEliminado);
							
							micCont.setCrtStatus1(dtoMicCrt.getCrtStatus().toString());
							micCont.setCrtLineStatus1(dtoMicCrt.getCrtLineStatus().toString());
							micCont.setCrtImgStatus1(dtoMicCrt.getCrtImgStatus().toString());
							micCont.setSecNoCrt(dtoMicCrt.getSecuencial());
							micCont.setCrtImgNum1(dtoMicCrt.getNroCrtImgVinculada());
							micCont.saveEx(trxName);
							DB.executeUpdateEx("update uy_tr_miccont set crtstatus1 = '" + dtoMicCrt.getCrtStatus().toString() + "' where uy_tr_miccont_id = " + rs.getInt("uy_tr_miccont_id"), trxName);
							
						}
					}
					if(rs.getInt("uy_tr_crt_id_1") > 0) {
						MTRCrt crt = new MTRCrt(ctx, rs.getInt("uy_tr_crt_id_1"), trxName);
						DTOMIcContCrt dtoMicCrt = new DTOMIcContCrt();
						if (crt != null){
							dtoMicCrt.setId(rs.getInt("uy_tr_crt_id_1"));
							dtoMicCrt.setConocimientoOriginalNumero(crt.getNumero());
							dtoMicCrt.setCrtStatus(CrtStatus.valueOf(micCont.getCrtStatus2()));
							dtoMicCrt.setCrtLineStatus(LineImgStatus.valueOf(micCont.getCrtLineStatus2()));
							dtoMicCrt.setCrtImgStatus(LineImgStatus.valueOf(micCont.getCrtImgStatus2()));
							dtoMicCrt.setSecuencial(micCont.getSecNoCrt2());
							dtoMicCrt.setNroCrtImgVinculada(micCont.getCrtImgNum2());
							
							procesarRespuestaConocimiento(daeRespuesta, dtoMicCrt, ctx, trxName, micEliminado);
							
							micCont.setCrtStatus2(dtoMicCrt.getCrtStatus().toString());
							micCont.setCrtLineStatus2(dtoMicCrt.getCrtLineStatus().toString());
							micCont.setCrtImgStatus2(dtoMicCrt.getCrtImgStatus().toString());
							micCont.setSecNoCrt2(dtoMicCrt.getSecuencial());
							micCont.setCrtImgNum2(dtoMicCrt.getNroCrtImgVinculada());
							micCont.saveEx(trxName);
							DB.executeUpdateEx("update uy_tr_miccont set crtstatus2 = '" + dtoMicCrt.getCrtStatus().toString() + "' where uy_tr_miccont_id = " + rs.getInt("uy_tr_miccont_id"), trxName);
							
						}
					}
				}
				
				
				// Recorro los CRT marcados para eliminar en la tabla UY_TR_MicTask
				sql = "SELECT UY_TR_MicTask_ID FROM UY_TR_MicTask WHERE UY_TR_Mic_ID = " + mic.get_ID();
				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();

				while (rs.next()){
					if (rs.getInt("UY_TR_MicTask_ID") > 0){
						MTRMicTask mTask = new MTRMicTask(ctx, rs.getInt("UY_TR_MicTask_ID"), trxName);
						MTRCrt crt = new MTRCrt(ctx, mTask.getUY_TR_Crt_ID(), trxName);
						DTOMIcContCrt dtoMicCrt = new DTOMIcContCrt();
						
						dtoMicCrt.setId(mTask.getUY_TR_Crt_ID());
						dtoMicCrt.setConocimientoOriginalNumero(crt.getNumero());
						dtoMicCrt.setCrtStatus(CrtStatus.valueOf(mTask.getCrtStatus()));
						dtoMicCrt.setCrtLineStatus(LineImgStatus.valueOf(mTask.getCrtLineStatus()));
						dtoMicCrt.setCrtImgStatus(LineImgStatus.valueOf(mTask.getCrtImgStatus()));
						dtoMicCrt.setSecuencial(mTask.getSecNoCrt());
						dtoMicCrt.setNroCrtImgVinculada(mTask.getCrtImgNum());
						
						procesarRespuestaConocimiento(daeRespuesta, dtoMicCrt, ctx, trxName, micEliminado);
						
						mTask.setCrtStatus(dtoMicCrt.getCrtStatus().toString());
						mTask.setCrtLineStatus(dtoMicCrt.getCrtLineStatus().toString());
						mTask.setCrtImgStatus(dtoMicCrt.getCrtImgStatus().toString());
						mTask.setSecNoCrt(dtoMicCrt.getSecuencial());
						mTask.setCrtImgNum(dtoMicCrt.getNroCrtImgVinculada());
						mTask.saveEx();
						
					}
				}

				// Recorro los paises de paso si tene
				sql = "select micC.uy_tr_miccountry_id as uy_tr_miccountry_id, cC.iso_code as iso_code" +
						" from uy_tr_miccountry as micC inner join c_country cC on micC.c_country_id = cC.c_country_id " +
						" where uy_tr_mic_id = " + mic.get_ID() +
						" order by uy_tr_miccountry_id";
				
				pstmt = DB.prepareStatement (sql, trxName);
				rs = pstmt.executeQuery ();
				MTRMicCountry country = null;

				while (rs.next()){
					if (rs.getInt("uy_tr_miccountry_id") > 0){
						country = new MTRMicCountry(ctx, rs.getInt("uy_tr_miccountry_id"), trxName);
						String sqlUpdateStatus = "UPDATE uy_tr_micCountry SET status = ':statusPaisDePaso:' WHERE uy_tr_micCountry_ID = " + country.getUY_TR_MicCountry_ID();
						if (micEliminado){
							sqlUpdateStatus = sqlUpdateStatus.replace(":statusPaisDePaso:", CrtStatus.ENALTA.toString());
							DB.executeUpdateEx(sqlUpdateStatus, trxName);
						} else {
							for(Respuesta respActual : daeRespuesta.getRespuestas()){
								// "RespuestaTipo" = "S" : Pais de paso
								if (respActual.getReferencia("RespuestaTipo").getValor().equalsIgnoreCase("S") && respActual.getReferencia("PaisDePasoNumeroSecuencial").getValor().equalsIgnoreCase(String.valueOf(country.getNumero()))){
									if (respActual.getCodigo().equalsIgnoreCase("1450")){
										// Update por sql, en el saveEx se disparan controles que revisan el estado del pais de paso y disparan nuevamente el envio
										if (country.getStatus().equalsIgnoreCase(CrtStatus.ENALTA.toString())){
											sqlUpdateStatus = sqlUpdateStatus.replace(":statusPaisDePaso:", CrtStatus.VINCULADO.toString());
											DB.executeUpdateEx(sqlUpdateStatus, trxName);
										} else if (country.getStatus().equalsIgnoreCase(CrtStatus.ENBAJA.toString())){
											sqlUpdateStatus = sqlUpdateStatus.replace(":statusPaisDePaso:", CrtStatus.DESVINCULADO.toString());
											DB.executeUpdateEx(sqlUpdateStatus, trxName);
										}
									}
								}
							}
						}
					}
				}
			}catch (Exception e)
			{
				throw new AdempiereException(e.getMessage());
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
			
			
		}
		// Guardo el manifiesto
		mic.saveEx();
		
		// Recorro los errores para mostrar
		respuestaStr = "";
		for (Respuesta resp : daeRespuesta.getRespuestas()){
			//if (!resp.getTipo().equalsIgnoreCase("S")){
				respuestaStr += (resp.getCodigo() + " - " + resp.getTipo() + " - " + resp.getDescripcion() + " - " + resp.getAyuda() + "\n").replace("'", " ");
			//}
		}
		if (respuestaStr.length() > 20000){
			respuestaStr = respuestaStr.substring(0, 19999);
		}
		DB.executeUpdateEx("UPDATE UY_TR_Mic SET responseText = '" + respuestaStr +"' WHERE uy_tr_mic_id = " + mic.getUY_TR_Mic_ID(), trxName);
		
	}
	
	protected static void procesarRespuestaConocimiento(DAERespuesta daeRespuesta, DTOMIcContCrt dtoCrt, Properties ctx, String trxName, boolean micEliminado){
		ArrayList<Respuesta> respuestasCrt = daeRespuesta.getRespuestasConocimientos(RespuestaTipo.Conocimientos);
		ArrayList<Respuesta> respuestasLinea = daeRespuesta.getRespuestasConocimientos(RespuestaTipo.Lineas);
		ArrayList<Respuesta> respuestasImagen = daeRespuesta.getRespuestasConocimientos(RespuestaTipo.Imagenes);
		
		Respuesta respuestaCrt = null;
		Respuesta respuestaLinea = null;
		Respuesta respuestaImagen = null;
		MTRCrt crt = new MTRCrt(ctx, dtoCrt.getId() ,trxName);
		
		boolean crtStatusCorrecto = false;
		CrtStatus crtStatus = dtoCrt.getCrtStatus();
		
		// Si el check isDrop se marco, entonces el manifiesto se elimino y el sistema Lucia internamente elimino todo su contenido sin dar respuesta
		if (micEliminado){
			// Si se elimino el manifiesto, marco como no enviado los crt lineas e imagenes que contiene
			dtoCrt.setCrtStatus(CrtStatus.ENALTA);
			dtoCrt.setCrtLineStatus(LineImgStatus.SINCARGAR);
			dtoCrt.setCrtImgStatus(LineImgStatus.SINCARGAR);
			
			// OpenUp. Raul Capecce. issue #3321 16/07/2015
			// En caso de que falle la asociaci髇, se deshabilita pasando a false este system configurator
			if (MSysConfig.getBooleanValue("UY_ADUANA_ASOCIACION", false, Env.getAD_Client_ID(ctx))){
				// OpenUp. Raul Capecce. issue #3321 13/04/2015
				// Eliminar las Asociaciones vinculadas en el sistema lucia y eliminar crts en la tabla micTask
				// TODO: Marcar Asociaciones como no vinculadas
				String sqlDesAsoc = "UPDATE UY_TR_DuaLink SET statusAsociation = '" + CrtStatus.ENALTA + "' WHERE UY_TR_Crt_ID = " + dtoCrt.getId();
				DB.executeUpdateEx(sqlDesAsoc, trxName);
				// Fin #3321
			}
		} else {
			// No se marco el mic para eliminar, sigue curso normal
			for (Respuesta respActual : respuestasCrt){
				if (crtStatus == CrtStatus.ENALTA  || crtStatus == CrtStatus.ENMODIFICACION){
					if (respActual.getReferencia("ConocimientoOriginalNumero").getValor().equalsIgnoreCase(dtoCrt.getConocimientoOriginalNumero().replace(" ", ""))){
						respuestaCrt = respActual;
					}
				} else if (crtStatus == CrtStatus.ENBAJA){
					if (respActual.getReferencia("ConocimientoNumeroSecuencial").getValor().equalsIgnoreCase(dtoCrt.getSecuencial())){
						respuestaCrt = respActual;
					}
				}
			}
			if (respuestaCrt != null){
				if (respuestaCrt.getCodigo().equalsIgnoreCase("1450")){
					// Si la respuesta correspondiente al conocimiento actual es correcta, actualizo su status en el dto y proceso las respuestas de su imagen y linea
					if (crtStatus == CrtStatus.ENALTA || crtStatus == CrtStatus.ENMODIFICACION || crtStatus == CrtStatus.VINCULADO){
						dtoCrt.setCrtStatus(CrtStatus.VINCULADO);
					} else if (crtStatus == CrtStatus.ENBAJA || crtStatus == CrtStatus.DESVINCULADO) {
						dtoCrt.setCrtStatus(CrtStatus.DESVINCULADO);
					}
					dtoCrt.setSecuencial(respuestaCrt.getReferencia("ConocimientoNumeroSecuencial").getValor());
					crtStatusCorrecto = true;
					// Se revisa status
				} else {
					crtStatusCorrecto = false;
				}
			} else {
				crtStatusCorrecto = true;
			}
			if (crtStatusCorrecto){
				// Obtengo la respuesta de la linea del conocimiento
				for (Respuesta respActual : respuestasLinea){
					if (crtStatus == CrtStatus.ENALTA  || crtStatus == CrtStatus.ENMODIFICACION){
						if (respActual.getReferencia("ConocimientoOriginalNumero").getValor().equalsIgnoreCase(dtoCrt.getConocimientoOriginalNumero().replace(" ", ""))){
							respuestaLinea = respActual;
						}
					} else if (crtStatus == CrtStatus.ENBAJA || crtStatus == CrtStatus.DESVINCULADO) {
						if (respActual.getReferencia("ConocimientoNumeroSecuencial").getValor().equalsIgnoreCase(dtoCrt.getSecuencial())){
							respuestaLinea = respActual;
						}
					}
				}
				if (respuestaLinea != null){
					if (respuestaLinea.getCodigo().equalsIgnoreCase("1450")){
						if (dtoCrt.getCrtStatus() == CrtStatus.ENMODIFICACION || dtoCrt.getCrtStatus() == CrtStatus.ENALTA || dtoCrt.getCrtStatus() == CrtStatus.VINCULADO) {
							dtoCrt.setCrtLineStatus(LineImgStatus.CARGADO);
						} else if (dtoCrt.getCrtStatus() == CrtStatus.ENBAJA || dtoCrt.getCrtStatus() == CrtStatus.DESVINCULADO) {
							dtoCrt.setCrtLineStatus(LineImgStatus.SINCARGAR);
						}
					}
				}
	
				// Obtengo la respuesta de la imagen del conocimiento
				for (Respuesta respActual : respuestasImagen){
					if (crtStatus == CrtStatus.ENALTA  || crtStatus == CrtStatus.ENMODIFICACION){
						if (respActual.getReferencia("ConocimientoOriginalNumero").getValor().equalsIgnoreCase(dtoCrt.getConocimientoOriginalNumero().replace(" ", ""))){
							respuestaImagen = respActual;
						}
					} else if (crtStatus == CrtStatus.ENBAJA || crtStatus == CrtStatus.DESVINCULADO || crtStatus == CrtStatus.VINCULADO) {
						String conNroSec = respActual.getReferencia("ConocimientoNumeroSecuencial").getValor();
						if (conNroSec.equalsIgnoreCase(dtoCrt.getSecuencial())){
							respuestaImagen = respActual;
						}
					}
				}
				if (respuestaImagen != null){
					if (respuestaImagen.getCodigo().equalsIgnoreCase("1450")){
						if (dtoCrt.getCrtImgStatus()== LineImgStatus.CARGADO) {
							dtoCrt.setCrtImgStatus(LineImgStatus.SINCARGAR);
						} else if (dtoCrt.getCrtImgStatus()== LineImgStatus.SINCARGAR) {
							dtoCrt.setCrtImgStatus(LineImgStatus.CARGADO);
							if (dtoCrt.getNroCrtImgVinculada() != crt.getcodigo()){
								dtoCrt.setNroCrtImgVinculada(crt.getcodigo());
							}
						}
						
					
					}
				}

				// OpenUp. Raul Capecce. issue #3321 16/07/2015
				// En caso de que falle la asociaci髇, se deshabilita pasando a false este system configurator
				if (MSysConfig.getBooleanValue("UY_ADUANA_ASOCIACION", false, Env.getAD_Client_ID(ctx))){
					// Obtengo la respuesta de la asociaci髇 de la linea
					procesarRespuestaAsociacion(daeRespuesta, dtoCrt, ctx, trxName);
				}
				
			}
		}
	}
	
	protected static void procesarRespuestaAsociacion(DAERespuesta daeRespuesta, DTOMIcContCrt dtoCrt, Properties ctx, String trxName){
		ArrayList<Respuesta> respuestasAsociaciones = daeRespuesta.getRespuestasConocimientos(RespuestaTipo.Asociaciones);
		
		try {
			
			for (Respuesta respActual : respuestasAsociaciones) {
				if (respActual.getCodigo().equalsIgnoreCase("1450")) {
					String crtNumero = respActual.getReferencia("ConocimientoOriginalNumero").getValor();
					String sqlIdCrt = "SELECT UY_TR_DuaLink_ID FROM UY_TR_DuaLink duaLink INNER JOIN UY_TR_Crt crt ON crt.UY_TR_Crt_ID = duaLink.UY_TR_Crt_ID WHERE crt.numero = '" + crtNumero + "'";
					
					PreparedStatement pstmt = DB.prepareStatement (sqlIdCrt, trxName);
					ResultSet rs = pstmt.executeQuery ();
					if (rs.next()) {
						MTRDuaLink mtrDuaLink = new MTRDuaLink(ctx, Integer.valueOf(rs.getString("UY_TR_DuaLink_ID")), trxName);
//						if (respActual.getReferencia("RespuestaTipo").getValor().equalsIgnoreCase("A")) {
//							mtrDuaLink.setStatusAsociation(CrtStatus.VINCULADO.toString());
//						} else if (respActual.getReferencia("RespuestaTipo").getValor().equalsIgnoreCase("B")) {
//							mtrDuaLink.setStatusAsociation(CrtStatus.ENALTA.toString());
//						} else if (respActual.getReferencia("RespuestaTipo").getValor().equalsIgnoreCase("M")) {
//							mtrDuaLink.setStatusAsociation(CrtStatus.VINCULADO.toString());
//						}
						if (mtrDuaLink.getStatusAsociation().equalsIgnoreCase(CrtStatus.ENALTA.toString())) {
							mtrDuaLink.setStatusAsociation(CrtStatus.VINCULADO.toString());
							mtrDuaLink.setIsDrop(false);
						} else if (
								mtrDuaLink.getStatusAsociation().equalsIgnoreCase(CrtStatus.ENMODIFICACION.toString()) ||
								mtrDuaLink.getStatusAsociation().equalsIgnoreCase(CrtStatus.VINCULADO.toString())
								) {
							if (mtrDuaLink.isDrop()) {
								mtrDuaLink.setStatusAsociation(CrtStatus.ENALTA.toString());
								mtrDuaLink.setIsDrop(false);
							} else {
								mtrDuaLink.setStatusAsociation(CrtStatus.VINCULADO.toString());
							}
						}
						mtrDuaLink.saveEx();
					}
					
				}
			}
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	/*
	protected static void procesarRespuesta2(DAERespuesta daeRespuesta, int micID, int ad_client_id, int ad_org_id, String trxName, Properties ctx) throws Exception{
		MTRMic mic = new MTRMic(ctx, micID, trxName);
		String respuestaStr = "";
		
		Respuesta respuestaMic = daeRespuesta.getRespuestaManifiesto();
		
		if (mic.getMicStatus() != MicStatus.ENVIADO.toString() && respuestaMic == null){
			// Si la respuesta mic no existe
			respuestaStr = daeRespuesta.getRespuestas().get(0).getCodigo() + " - " + daeRespuesta.getRespuestas().get(0).getAyuda();
		} else {
			// Se procesa respuesta del mic
			if (mic.getMicStatus() == MicStatus.ENVIADO.toString() && respuestaMic.getCodigo().equalsIgnoreCase("1450")){
				// Se da de alta correctamente, c贸digo 1450 = Operaci贸n Exitosa
				
				// Se guarda el estado de 茅xito de la operaci贸n
				mic.setMicStatus(MicStatus.ENVIADO.toString());
				mic.setNroMic(respuestaMic.getReferencia("ManifiestoNumero").getValor());
				mic.setRecinto(respuestaMic.getReferencia("Recinto").getValor());
				mic.saveEx();
				
				respuestaStr = "";
				
				// Obtengo el conocimiento que est谩 en el cabezal
				if(mic.getUY_TR_Crt_ID() > 0) {
					MTRCrt crt = new MTRCrt(ctx, mic.getUY_TR_Crt_ID(), trxName);
					Respuesta respCrt = getConocimiento(crt.getNumero().replace(" ", ""), daeRespuesta);
					if (respCrt != null){
						if (respCrt.getCodigo().equalsIgnoreCase("1450")){
							if (mic.getCrtStatus1().toUpperCase() == CrtStatus.ENALTA.toString() || mic.getCrtStatus1().toUpperCase() == CrtStatus.ENMODIFICACION.toString()){
								mic.setCrtStatus1(CrtStatus.VINCULADO.toString());
								mic.setSecNoCrt(respCrt.getReferencia("ConocimientoNumeroSecuencial").getValor());
								// Proceso la respuesta de la linea y de la imagen
								ArrayList<Respuesta> respLineas = getLinea(crt.getNumero().replace(" ", ""), daeRespuesta);
								ArrayList<Respuesta> respImagenes = getImagen(crt.getNumero().replace(" ", ""), daeRespuesta);
								if (mic.getCrtStatus1().equalsIgnoreCase(CrtStatus.ENALTA.toString()) || mic.getCrtStatus1().equalsIgnoreCase(CrtStatus.ENBAJA.toString())){
									Respuesta respLinea = respLineas.get(0);
									if (respLinea.getCodigo().equalsIgnoreCase("1450")){
										mic.setCrtLineStatus1(LineImgStatus.OK.toString());
									} else {
										respuestaStr += "Error Imagen: " + respLinea.getCodigo() + " - " + respLinea.getDescripcion() + "\n";
										mic.setCrtLineStatus1(LineImgStatus.ERROR.toString());
									}
									Respuesta respImagen = respImagenes.get(0);
									if (respImagen.getCodigo().equalsIgnoreCase("1450")){
										mic.setCrtImgStatus1(LineImgStatus.OK.toString());
									} else {
										respuestaStr += "Error Imagen: " + respImagen.getCodigo() + " - " + respImagen.getDescripcion() + "\n";
										mic.setCrtLineStatus1(LineImgStatus.ERROR.toString());
									}
								} else if (mic.getCrtStatus1().equalsIgnoreCase(CrtStatus.ENMODIFICACION.toString())){
									LineImgStatus resultLinea = LineImgStatus.OK;
									for (Respuesta respLinea : respLineas){
										if (!respLinea.getCodigo().equals("1450")){
											resultLinea = LineImgStatus.ERROR;
											respuestaStr += "Error Linea: " + respLinea.getCodigo() + " - " + respLinea.getDescripcion() + "\n";
										}
									}
									mic.setCrtLineStatus1(resultLinea.toString());
									
									LineImgStatus resultImg = LineImgStatus.OK;
									for (Respuesta respImg : respImagenes){
										if (!respImg.getCodigo().equalsIgnoreCase("1450")){
											resultImg = LineImgStatus.ERROR;
											respuestaStr += "Error Imagen: " + respImg.getCodigo() + " - " + respImg.getDescripcion() + "\n";
										}
									}
									mic.setCrtImgStatus1(resultImg.toString());
								}
								
							}
						} else {
							respuestaStr += "Error CRT " + mic.getUY_TR_Crt_ID() + ": " + respCrt.getCodigo() + " - " + respCrt.getDescripcion() + "\n";
						}
					}
				}
				//recorro las continuaciones
				String sqlIdCrt = "select uy_tr_miccont_id, uy_tr_crt_id, uy_tr_crt_id_1" +
						" from uy_tr_miccont" +
						" where uy_tr_mic_id = " + mic.get_ID() +
						" order by sheet asc";
				CPreparedStatement pstmt = DB.prepareStatement (sqlIdCrt, null);
				ResultSet rs = pstmt.executeQuery ();
//				while (rs.next()){
//					MTRMicCont micCont = new MTRMicCont(ctx,  rs.getInt("uy_tr_miccont_id"), trxName);
//					if(rs.getInt("uy_tr_crt_id") > 0) {
//						//:Raulo - Continuaci贸n n - CRT 1
//						MTRCrt crt = new MTRCrt(ctx, rs.getInt("uy_tr_crt_id"), trxName);
//						Respuesta respCrt = getConocimiento(crt.getNumero().replace(" ", ""), daeRespuesta);
//						if (respCrt != null){
//							if (respCrt.getCodigo().equalsIgnoreCase("1450")){
//								if (micCont.getCrtStatus1().toUpperCase() == CrtStatus.ENALTA.toString() || micCont.getCrtStatus1().toUpperCase() == CrtStatus.ENMODIFICACION.toString()){
//									micCont.setCrtStatus1(CrtStatus.VINCULADO.toString());
//									micCont.setSecNoCrt(respCrt.getReferencia("ConocimientoNumeroSecuencial").getValor());
//									// Proceso la respuesta de la linea y de la imagen
//									ArrayList<Respuesta> respLineas = getLinea(crt.getNumero().replace(" ", ""), daeRespuesta);
//									ArrayList<Respuesta> respImagenes = getImagen(crt.getNumero().replace(" ", ""), daeRespuesta);
//									if (micCont.getCrtStatus1().equalsIgnoreCase(CrtStatus.ENALTA.toString()) || micCont.getCrtStatus1().equalsIgnoreCase(CrtStatus.ENBAJA.toString())){
//									}
//								}
//							}
//						}
//					}
//					if(rs.getInt("uy_tr_crt_id_1") > 0) {
//						//:Raulo - Continuaci贸n n - CRT 2
//						MTRCrt crt = new MTRCrt(ctx, rs.getInt("uy_tr_crt_id"), trxName);
//						Respuesta respCrt = getConocimiento(crt.getNumero().replace(" ", ""), daeRespuesta);
//						if (respCrt != null){
//							if (respCrt.getCodigo().equalsIgnoreCase("1450")){
//								if (micCont.getCrtStatus2().toUpperCase() == CrtStatus.ENALTA.toString() || micCont.getCrtStatus2().toUpperCase() == CrtStatus.ENMODIFICACION.toString()){
//									micCont.setCrtStatus2(CrtStatus.VINCULADO.toString());
//									micCont.setSecNoCrt2(respCrt.getReferencia("ConocimientoNumeroSecuencial").getValor());
//									// Proceso la respuesta de la linea y de la imagen
//									ArrayList<Respuesta> respLineas = getLinea(crt.getNumero().replace(" ", ""), daeRespuesta);
//									ArrayList<Respuesta> respImagenes = getImagen(crt.getNumero().replace(" ", ""), daeRespuesta);
//									if (micCont.getCrtStatus2().equalsIgnoreCase(CrtStatus.ENALTA.toString()) || micCont.getCrtStatus2().equalsIgnoreCase(CrtStatus.ENBAJA.toString())){
//									}
//								}
//							}
//						}
//					}
//				}
				
			} else {
				// Error al dar de alta el cabezal del mic
				respuestaStr = "Error en la informaci贸n del cabezal del manifiesto\n\n" + respuestaMic.getCodigo() + "\n" + respuestaMic.getDescripcion() +respuestaMic.getAyuda();
			}
		}
		// Guardo los mensajes de respuesta
//		respuestaStr = "";
//		for (Respuesta resp : daeRespuesta.getRespuestas()){
//			respuestaStr += resp.getCodigo() + " - " + resp.getAyuda() + "\n";
//			for (Referencia ref : resp.getReferencias()){
//				//respuestaStr += "\t" + ref.getCodigo() + " - " + ref.getValor();
//			}
//		}
		
		DB.executeUpdateEx("UPDATE UY_TR_Mic SET result = '" + respuestaStr +"' WHERE uy_tr_mic_id = " + micID, trxName);
	}
	*/
	
	protected static Respuesta getConocimiento(String conocimientoOriginalNumero, DAERespuesta daeRespuesta){
		Respuesta ret = null;
		ArrayList<Respuesta> respuestasConocimiento = daeRespuesta.getRespuestasConocimientos(RespuestaTipo.Conocimientos);
		for (Respuesta resp : respuestasConocimiento){
			if (resp.getReferencia("ConocimientoOriginalNumero").getCodigo().equalsIgnoreCase(conocimientoOriginalNumero)){
				ret = resp;
			}
		}
		return ret;
	}
	protected static ArrayList<Respuesta> getLinea(String conocimientoOriginalNumero, DAERespuesta daeRespuesta){
		ArrayList<Respuesta> ret = new ArrayList<DAERespuesta.Respuesta>();
		ArrayList<Respuesta> respuestasLinea = daeRespuesta.getRespuestasConocimientos(RespuestaTipo.Lineas);
		for (Respuesta resp : respuestasLinea){
			if (resp.getReferencia("ConocimientoOriginalNumero").getCodigo().equalsIgnoreCase(conocimientoOriginalNumero)){
				ret.add(resp);
			}
		}
		return ret;
	}
	protected static ArrayList<Respuesta> getImagen(String conocimientoOriginalNumero, DAERespuesta daeRespuesta){
		ArrayList<Respuesta> ret = new ArrayList<DAERespuesta.Respuesta>();
		ArrayList<Respuesta> respuestasImagen = daeRespuesta.getRespuestasConocimientos(RespuestaTipo.Imagenes);
		for (Respuesta resp : respuestasImagen){
			if (resp.getReferencia("ConocimientoOriginalNumero").getCodigo().equalsIgnoreCase(conocimientoOriginalNumero)){
				ret.add(resp);
			}
		}
		return ret;
	}
	
	protected static DAERespuesta consultarDaeManifiesto(MTRMic mic, int ad_client_id, int ad_org_id, String trxName, Properties ctx) throws Exception{
		DAE objConsulta = new DAE();
		DAERespuesta objNuevaRespuesta = null; 
		String rut = "";
		String nroTransaccion = "";
		

		MOrgInfo orgInfo = MOrgInfo.get(ctx, ad_org_id, trxName);
		if (orgInfo != null){
			rut = orgInfo.getDUNS();
		}
		
		if (mic != null && mic.getTransactionCode() != null){
			nroTransaccion = mic.getTransactionCode();
		}
		
		
		objConsulta.setTipoDocumento("4"); // Es un RUT
		objConsulta.setIdDocumento(rut);
		objConsulta.setNroTransaccion(nroTransaccion);
		String strXmlMic = ConsultaRespuestaMic.firmarMic(objConsulta, ad_client_id, ad_org_id, trxName, ctx);
		
		return Envio.EnviarMensajeAduana(strXmlMic, Envio.Operacion.EnviarMic, ad_client_id, ad_org_id, trxName);
		
	}
	
	public static String getUyTrDigitalsignature(String campoFiltro, int ad_client_id, int ad_org_id, String trxName)
			throws AdempiereException {
		// Obteniendo datos de la firma de seguridad
		String sql = "";
		String campoRet = "";

		sql = "select " + campoFiltro + " from uy_tr_digitalsignature"
				+ " where ad_client_id = " + ad_client_id
				+ " and  ad_org_id = " + ad_org_id;
		campoRet = DB.getSQLValueStringEx(trxName, sql);

		if (campoRet == null || campoRet.equalsIgnoreCase(""))
			throw new AdempiereException("");
		return campoRet;
	}

	// * * * Generating XML * * *

	public static String firmarMic(DAE dae, int ad_client_id, int ad_org_id, String trxName, Properties ctx) throws Exception{
		java.io.Writer swDae = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(DAE.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(dae, swDae);
		String strMicToSend = swDae.toString();
		
		String idFileKeyStore = ConsultaRespuestaMic.getUyTrDigitalsignature("BinaryFile_ID",  ad_client_id, ad_org_id, trxName);
		String passKeyStore = ConsultaRespuestaMic.getUyTrDigitalsignature("KeystorePass",  ad_client_id, ad_org_id, trxName);
		String passPrivateKey = ConsultaRespuestaMic.getUyTrDigitalsignature("PrivateKeyPass",  ad_client_id, ad_org_id, trxName);
	
		return Firma.FirmarEmbedXML(strMicToSend, idFileKeyStore, passKeyStore, passPrivateKey, ctx, trxName);
	}
	
}
