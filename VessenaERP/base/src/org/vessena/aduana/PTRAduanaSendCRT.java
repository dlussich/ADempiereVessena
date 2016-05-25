package org.openup.aduana;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.codec.binary.Base64;
import org.compiere.model.MCountry;
import org.compiere.model.MLocation;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.aduana.conexion.DAERespuesta;
import org.openup.aduana.crt.dto.DAE;
import org.openup.model.MTRCrt;

public class PTRAduanaSendCRT extends SvrProcess {

	private int crtID = 0;
	private int userID = 0;
	private String isSignature = "N";

	public PTRAduanaSendCRT() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] parameters = getParameter();
		for (int i = 0; i < parameters.length; i++) {
			String name = parameters[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("uy_tr_crt_id")) {
					this.crtID = Integer.parseInt(parameters[i]
							.getParameter().toString());
				}else if (name.equalsIgnoreCase("ad_user_id")) {
					if(parameters[i].getParameter()!=null){
						this.userID = Integer.parseInt(parameters[i]
								.getParameter().toString());
					}
				}

			}
		}
		
		if(this.userID > 0) this.isSignature = "Y";
	}

	@Override
	protected String doIt() throws Exception {
		
		MTRCrt crt = new MTRCrt(getCtx(),this.crtID,get_TrxName());
		return SendSoap(crt);
		
	}
	
	protected String SendSoap(MTRCrt crt){
		
		String rut = "";
		MOrgInfo orgInfo = null;
		Date fechaEmision = null;
		String nroCRT = "";
		String paisEmision = "";
		
		DAERespuesta objRespuesta;
		
		
		// Variables DAE
		DAE dae = new DAE();
		DAE.Objeto objCrt = new DAE.Objeto();
		
		// Variables Objeto
		DAE.Objeto.Imagen imgCrt = new DAE.Objeto.Imagen();
		
		try {
			
			orgInfo =  MOrgInfo.get(getCtx(),Env.getAD_Org_ID(getCtx()),this.get_TrxName());
			if(orgInfo!=null){
				rut = orgInfo.getDUNS();
			}
			
			nroCRT = crt.getNumero();
			fechaEmision = crt.getDateTrx();
			
			int locationID = crt.getC_Location_ID();
			
			if(locationID > 0){
				
				MLocation loc = new MLocation(getCtx(),locationID,get_TrxName()); //obtengo ubicacion actual
				
				if(loc.getC_Country_ID() > 0){
					
					MCountry country = new MCountry(getCtx(),loc.getC_Country_ID(),get_TrxName());
					
					paisEmision = country.get_ValueAsString("iso_code");						
					
				}							
				
			} else throw new AdempiereException("No se pudo obtener la ubicacion desde el CRT");						
			
			// DAE (Documento Aduanero Electrï¿½nico)
			dae.setObjeto(objCrt);
			// dae.setSignature(signature);
			dae.setTipoDocumento((short) 4); // Short, 4 es un nï¿½mero rut, dato proveido por Santiago De Pena, Concepto, Aduana
			dae.setIdDocumento(new BigInteger(rut)); // BigInteger, numero de rut de la empresa transportista, dato proveido por Santiago De Pena, Concepto, Aduana
			dae.setFechaHoraDocumentoElectronico(getXMLGregorianCalendar(new Date())); // XMLGregorianCalendar, fecha de envio del XML, dato proveido por Santiago De Pena, Concepto, Aduana
			dae.setCodigoIntercambio("ImagenDNA"); // String, especifica que lo que va en el tag objeto es un objeto de imagen para crt.
			dae.setNroTransaccion(String.valueOf(Envio.generateIdTransaccionAduana(this.getAD_Client_ID(), Env.getAD_Org_ID(getCtx()), get_TrxName())));
			
			// Objeto
			objCrt.setImagen(imgCrt);

			// Imagen
			imgCrt.setTipoDocumentoEscaneado("CONC"); // String, dato proveido por Santiago De Pena, Concepto, Aduana
			imgCrt.setFechaEmision(fechaEmision.toString()); // String, Fecha emisiï¿½n del CRT, dato proveido por Santiago De Pena, Concepto, Aduana
			imgCrt.setDocIdOriginal(nroCRT); // String, Id de ese CRT, dato proveido por Santiago De Pena, Concepto, Aduana
			imgCrt.setDocPaisEmision(paisEmision); // String, desde donde se emite el CRT, Uruguay, dato proveido por Santiago De Pena, Concepto, Aduana
			imgCrt.setContenidoBase64(getPdfCRT()); // String, archivo de la imagen del crt creado desde Adempiere.
			imgCrt.setTipoArchivo("PDF"); // String, extensiï¿½n del archivo enviado, se aceptan archivos PDF y TIFF

			
			
			// * * * Generating XML * * *
			
			java.io.Writer swDae = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(DAE.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(dae, swDae);
			String strCrtToSend = swDae.toString();

			String idFileKeyStore=getUyTrDigitalsignature("BinaryFile_ID");
			String passKeyStore=getUyTrDigitalsignature("KeystorePass");
			String passPrivateKey=getUyTrDigitalsignature("PrivateKeyPass");
			
			
			strCrtToSend = Firma.FirmarEmbedXML(strCrtToSend, idFileKeyStore, passKeyStore, passPrivateKey, getCtx(), get_TrxName());

			// uy_tr_crt_id
			DB.executeUpdateEx("UPDATE uy_tr_crt SET result = '" + strCrtToSend +"' WHERE uy_tr_crt_id = " + crtID, get_TrxName());
			
			objRespuesta = Envio.EnviarMensajeAduana(strCrtToSend, Envio.Operacion.EnviarCrt, this.getAD_Client_ID(), Env.getAD_Org_ID(getCtx()), get_TrxName());
			
			// Verificar los posibles errores, aca se setea el código de la imagen retornado por Lucia cuando todo sale bien.
			DAERespuesta.Respuesta respuesta = objRespuesta.getRespuestas().get(0);
			String strRespuesta = "";
			
			if (respuesta.getCodigo().equalsIgnoreCase("IM00")){
				// Si el código es IM00, se guardó correctamente la imagen
				// Recorro los objetos Referencia para obtener el código asociado la imagen del CRT
				for (DAERespuesta.Respuesta.Referencia referencia : respuesta.getReferencias() )
				{
					if (referencia.getCodigo() != null && referencia.getCodigo().equalsIgnoreCase("NumeroDeImagen")){
						DB.executeUpdateEx("UPDATE uy_tr_crt "
								+ "SET codigo = '" + referencia.getValor() + "'" 
								+ ", DateLast = '" + new Date().toString() + "'"
								+ ", isValid = '" + "Y" + "'"
								+ ", result = '" + strCrtToSend +"'"
								+ ", responseText = '" + respuesta.getDescripcion() + "\n" + respuesta.getAyuda() + "'"
								+ " WHERE uy_tr_crt_id = " + crtID, get_TrxName());
						
						//seteo nro de imagen de CRT, si el mismo se encuentra en un MIC sin enviar a aduana
						this.updateMicNroCrt(crtID,referencia.getValor());
					}
				}
			}else{
				// Si se obtiene otro código, ocurrió algo y se muestra el inconveniente
				DB.executeUpdateEx("UPDATE uy_tr_crt "
						+ "SET isValid = '" + "N" + "'"
						+ ", result = '" + strCrtToSend + "'"
						+ ", responseText = '" + respuesta.getDescripcion() + "\n" + respuesta.getAyuda() + "'"
						+ " WHERE uy_tr_crt_id = " + crtID, get_TrxName());
			}
			
			return null;
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 01/11/2014. ISSUE #3336
	 * Método que actualiza el numero de imagen de CRT en el MIC, si este aun no fue enviada a aduana
	 * **/
	private void updateMicNroCrt(int crtID, String valor) {
		
		String sql = "";
		
		try{
		
		//actualizo cabezales de MIC	
		sql = "update uy_tr_mic" +
		      " set crtimgnum1 = '" + valor + "'" +
			  " where uy_tr_crt_id = " + crtID +
			  " and crtimgnum1 is null and micstatus = '" + ConsultaRespuestaMic.MicStatus.SINENVIAR + "'";
		
		DB.executeUpdateEx(sql, get_TrxName());
		
		//actualizo continuaciones para CRT1
		sql = "update uy_tr_miccont" +
			      " set crtimgnum1 = '" + valor + "'" +
				  " where uy_tr_crt_id = " + crtID +
				  " and crtimgnum1 is null" +
				  " and uy_tr_mic_id in (select uy_tr_mic_id from uy_tr_mic where micstatus = '" + ConsultaRespuestaMic.MicStatus.SINENVIAR + "'" + ")";
			
		DB.executeUpdateEx(sql, get_TrxName());

		//actualizo continuaciones para CRT2
		sql = "update uy_tr_miccont" +
			      " set crtimgnum2 = '" + valor + "'" +
				  " where uy_tr_crt_id_1 = " + crtID +
				  " and crtimgnum2 is null" +
				  " and uy_tr_mic_id in (select uy_tr_mic_id from uy_tr_mic where micstatus = '" + ConsultaRespuestaMic.MicStatus.SINENVIAR + "'" + ")";
			
		DB.executeUpdateEx(sql, get_TrxName());	
		
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}	
	}

	protected String getUyTrDigitalsignature(String campoFiltro)
			throws AdempiereException {
		// Obteniendo datos de la firma de seguridad
		String sql = "";
		String campoRet = "";

		sql = "select " + campoFiltro + " from uy_tr_digitalsignature"
				+ " where ad_client_id = " + this.getAD_Client_ID()
				+ " and  ad_org_id = " + Env.getAD_Org_ID(getCtx());
		campoRet = DB.getSQLValueStringEx(get_TrxName(), sql);

		if (campoRet == null || campoRet.equalsIgnoreCase(""))
			throw new AdempiereException("");
		return campoRet;
	}

	@SuppressWarnings("deprecation")
	protected XMLGregorianCalendar getXMLGregorianCalendar(Date date)
			throws Exception {
		// * Current timestamp in XML format *
		GregorianCalendar calendarioGreg = new GregorianCalendar();
		if (date != null) {
			calendarioGreg.set(date.getYear(), date.getMonth(), date.getDay());
		}
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(
				calendarioGreg);
	}
	
	protected String GenerateBase64(File fileIn) {
		Base64 base64 = new Base64();
		String encodedFile = "";

		if (fileIn != null) {

			// // File to read
			// File fileIn = new File("./files/TestDoc.pdf");
			//
			byte[] fileArray = new byte[(int) fileIn.length()];
			InputStream inputStream;

			try {
				// Input file stream
				inputStream = new FileInputStream(fileIn);
				// Load file as array of bytes
				inputStream.read(fileArray);
				// get string encode base64

				encodedFile = new String(base64.encode(fileArray));
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return encodedFile;
	}
	
	protected String getPdfCRT(){
		
		try{

			int processID = this.getPrintProcessID(); //obtengo ID de processo de imppresion Jasper	
			
			if(processID <= 0) throw new AdempiereException("No se pudo obtener proceso de impresion de CRT");
			
			MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo ("PrintCrt", processID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("UY_TR_Crt_ID", this.crtID);
			para.saveEx();
			
			para = new MPInstancePara(instance, 20);
			para.setParameter("AD_User_ID", this.userID);
			para.saveEx();
			
			para = new MPInstancePara(instance, 30);
			para.setParameter("IsSignature", this.isSignature);
			para.saveEx();		
			
			ReportStarter starter = new ReportStarter();
			
			starter.startProcess(getCtx(), pi, null);
			
			java.lang.Thread.sleep(3000);

			return GenerateBase64(pi.getPDFReport());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Obtiene y retorna id del proceso de impresion de CRT.
	 * OpenUp Ltda. Issue #2343
	 * @author Nicolas Sarlabos - 01/07/2014
	 * @see
	 * @return
	 */
	private int getPrintProcessID() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;

		try{
			
			sql = " select ad_process_id " +
					" from ad_process " +
					" where lower(value) = 'uy_rtr_crt_aduana'";			

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			if (rs.next()){
				value = rs.getInt(1);
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
		
		return value;

	}

}
