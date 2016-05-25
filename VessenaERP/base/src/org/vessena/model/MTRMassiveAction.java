/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 08/04/2015
 */
package org.openup.model;

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
import java.util.List;
import java.util.Properties;

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
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.aduana.ConsultaRespuestaMic;
import org.openup.aduana.Envio;
import org.openup.aduana.Firma;
import org.openup.aduana.PTRAduanaSendMICLogic;
import org.openup.aduana.conexion.DAERespuesta;
import org.openup.aduana.crt.dto.DAE;

/**
 * org.openup.model - MTRMassiveAction
 * OpenUp Ltda. Issue #3996 
 * Description: Modelo de documento de envio masivo de mics.
 * @author Gabriel Vila - 08/04/2015
 * @see
 */
public class MTRMassiveAction extends X_UY_TR_MassiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768070152767983504L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_MassiveAction_ID
	 * @param trxName
	 */
	public MTRMassiveAction(Properties ctx, int UY_TR_MassiveAction_ID,
			String trxName) {
		super(ctx, UY_TR_MassiveAction_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMassiveAction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y despliega información de MICS a procesar segun filtros previamente indicados por el usuario.
	 * OpenUp Ltda. Issue #3996 
	 * @author Gabriel Vila - 20/04/2015
	 * @see
	 */
	public void loadData(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			DB.executeUpdateEx( " delete from UY_TR_MassiveActionLine where uy_tr_massiveaction_id =" + this.get_ID(), null);
			
			String whereConditions = "mic.datetrx between ? and ? ";
			
			sql = " select mic.datetrx, mic.uy_tr_mic_id, mic.uy_tr_transorder_id, " +
				  " case when coalesce(mic.tractor_id,0) > 0 then mic.tractor_id else mic.remolque_id end as tractor_id, " +
				  " ori.uy_ciudad_id as origen, dest.uy_ciudad_id as destino " +	
				  " from uy_tr_mic mic " +
				  " inner join uy_tr_transorder ot on mic.uy_tr_transorder_id = ot.uy_tr_transorder_id " +
				  " left outer join uy_ciudad ori on ot.uy_ciudad_id = ori.uy_ciudad_id " +
				  " left outer join uy_ciudad dest on ot.uy_ciudad_id_1 = dest.uy_ciudad_id " +
				  " where mic.docstatus not in ('DR', 'VO') " +
				  " and mic.nromic is null " +
				  " and " + whereConditions + 
				  " order by mic.datetrx ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setTimestamp(1, this.getStartDate());
			pstmt.setTimestamp(2, this.getEndDate());
									
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				MTRMassiveActionLine line = new MTRMassiveActionLine(getCtx(), 0, get_TrxName());
				line.setUY_TR_MassiveAction_ID(this.get_ID());
				line.setIsExecuted(false);
				line.setTractor_ID(rs.getInt("tractor_id"));
				line.setUY_TR_Mic_ID(rs.getInt("uy_tr_mic_id"));
				line.setUY_Ciudad_ID(rs.getInt("origen"));
				line.setUY_Ciudad_ID_1(rs.getInt("destino"));
				line.setDateTrx(rs.getTimestamp("datetrx"));
				line.setIsSelected(false);
				line.setUY_TR_TransOrder_ID(rs.getInt("uy_tr_transorder_id"));
				line.saveEx();
			}
			
			this.setLoaded(true);
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}

	/***
	 * Metodo que realiza el envío de mics seleccionados a la aduana.
	 * OpenUp Ltda. Issue #3996 
	 * @author Gabriel Vila - 20/04/2015
	 * @see
	 */
	public void sendMics() {

		Trx trans = null;
		
		try {
		
			// Obtengo y recorro mics seleccionados para enviar
			List<MTRMassiveActionLine> lines = this.getSelectedLines();
			
			for (MTRMassiveActionLine line: lines){

				// Linea no ejecutada aun
				if (!line.isExecuted()){

					// Genero nueva transaccion
					String trxNameAux = Trx.createTrxName();
					trans = Trx.get(trxNameAux, true);
					
					MTRMic mic = new MTRMic(getCtx(), line.getUY_TR_Mic_ID(), trxNameAux);

					// Mic no enviado exitoso aun
					if (!mic.isSentOK()){

						PTRAduanaSendMICLogic logic = new PTRAduanaSendMICLogic(getCtx(), trxNameAux);
						logic.execute(getCtx(), mic, trxNameAux);
						trans.close();
						
						line.setNroDNA(mic.getNroMic());
						line.setDescription(mic.getResponseText());
						line.setIsExecuted(mic.isSentOK());
						line.saveEx();
						
					}
				}
			}
			
		} 
		catch (Exception e) {
			if (trans != null){
				try {
					trans.rollback();
				} catch (Exception e2) {
					// No hago nada
				}
			}
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Obtiene y retorna lineas seleccionadas para procesar.
	 * OpenUp Ltda. Issue #3996
	 * @author Gabriel Vila - 20/04/2015
	 * @see
	 * @return
	 */
	public List<MTRMassiveActionLine> getSelectedLines(){
		
		String whereClause = X_UY_TR_MassiveActionLine.COLUMNNAME_UY_TR_MassiveAction_ID + "=" + this.get_ID() +
				" AND " + X_UY_TR_MassiveActionLine.COLUMNNAME_IsSelected + "='Y' ";
		
		List<MTRMassiveActionLine> lines = new Query(getCtx(), I_UY_TR_MassiveActionLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
	}

	
	/***
	 * Envvío de CRTS seleccionados y no procesados anteriormente.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/04/2015
	 * @see
	 */
	public void sendCrts() {

		Trx trans = null;
		
		try {
		
			// Obtengo y recorro mics seleccionados para enviar
			List<MTRMassiveActionLine> lines = this.getSelectedLines();
			
			for (MTRMassiveActionLine line: lines){

				// Linea no ejecutada aun
				if (!line.isExecuted()){

					// Genero nueva transaccion
					String trxNameAux = Trx.createTrxName();
					trans = Trx.get(trxNameAux, true);
					
					MTRCrt crt = new MTRCrt(getCtx(), line.getUY_TR_Crt_ID(), trxNameAux);

					// Crt sin imagen aun
					if ((crt.getcodigo() == null)){

						this.SendSoap(crt);
						trans.close();
						
						line.setDescription(crt.getResponseText());
						
						if (crt.getcodigo() != null){
							line.setImageNo(crt.getcodigo());	
						}
						
					}
					else{
						line.setImageNo(crt.getcodigo());	
					}

					if (line.getImageNo() != null)
					{
						line.setIsExecuted(true);
					}
					
					line.saveEx();
					
				}
			}
			
		} 
		catch (Exception e) {
			if (trans != null){
				try {
					trans.rollback();
				} catch (Exception e2) {
					// No hago nada
				}
			}
			throw new AdempiereException(e);
		}
		
	}

	
	private String SendSoap(MTRCrt crt){
		
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
			imgCrt.setContenidoBase64(getPdfCRT(crt.get_ID())); // String, archivo de la imagen del crt creado desde Adempiere.
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
			DB.executeUpdateEx("UPDATE uy_tr_crt SET result = '" + strCrtToSend +"' WHERE uy_tr_crt_id = " + crt.get_ID(), get_TrxName());
			
			objRespuesta = Envio.EnviarMensajeAduana(strCrtToSend, Envio.Operacion.EnviarCrt, this.getAD_Client_ID(), Env.getAD_Org_ID(getCtx()), get_TrxName());
			
			// Verificar los posibles errores, aca se setea el código de la imagen retornado por Lucia cuando todo sale bien.
			DAERespuesta.Respuesta respuesta = objRespuesta.getRespuestas().get(0);
			
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
								+ " WHERE uy_tr_crt_id = " + crt.get_ID(), get_TrxName());
						
						//seteo nro de imagen de CRT, si el mismo se encuentra en un MIC sin enviar a aduana
						this.updateMicNroCrt(crt.get_ID(), referencia.getValor());
					}
				}
			}else{
				// Si se obtiene otro código, ocurrió algo y se muestra el inconveniente
				DB.executeUpdateEx("UPDATE uy_tr_crt "
						+ "SET isValid = '" + "N" + "'"
						+ ", result = '" + strCrtToSend + "'"
						+ ", responseText = '" + respuesta.getDescripcion() + "\n" + respuesta.getAyuda() + "'"
						+ " WHERE uy_tr_crt_id = " + crt.get_ID(), get_TrxName());
			}
			
			return null;
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

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

	protected String getPdfCRT(int crtID){
		
		try{

			int processID = this.getPrintProcessID(); //obtengo ID de processo de imppresion Jasper	
			
			if(processID <= 0) throw new AdempiereException("No se pudo obtener proceso de impresion de CRT");
			
			MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo ("PrintCrt", processID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("UY_TR_Crt_ID", crtID);
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
	
}
