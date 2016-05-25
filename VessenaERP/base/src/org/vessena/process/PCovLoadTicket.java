/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 26/02/2015
 */
package org.openup.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;

import net.sf.jasperreports.engine.type.LineSpacingEnum;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto.Conocimientos.Conocimiento.Lineas;
import org.openup.model.MAuditCovLoadTicket;
import org.openup.model.MCFEInvoice;
import org.openup.model.MCovLoadTicket;
import org.openup.model.MCovTicketHeader;
import org.openup.model.MCovTicketLine;
import org.openup.model.MCovTicketLineClienteCC;
import org.openup.model.MCovTicketLineCtaCte;
import org.openup.model.MCovTicketLineEfectivo;
import org.openup.model.MCovTicketLineFactura;
import org.openup.model.MCovTicketLineFondeo;
import org.openup.model.MCovTicketLineLuncheon;
import org.openup.model.MCovTicketLinePagoServ;
import org.openup.model.MCovTicketLineRetiro;
import org.openup.model.MCovTicketLineTarjeta;
import org.openup.model.MCovTicketType;
import org.openup.model.MLogFile;
import org.openup.util.Converter;

import sun.security.util.BigInt;

/**
 * org.openup.process - PCovLoadTicket
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 26/02/2015
 * @see
 */
public class PCovLoadTicket extends SvrProcess {

	private MAuditCovLoadTicket mAudit = null;
	private MCovLoadTicket model = null;

	
	private BigDecimal sumaLineas = new BigDecimal(0);
	private BigDecimal sumoCabezales = new BigDecimal(0);
	
	private int cantCabezal=0;
	private int cantLineas=0;
	private int cantTotal=0;
	
	private String cabezalActual="";
	//private int countconsistenciaL4 = 0;
	//private int countconsistenciaC = 0;
	private boolean lineVenta = false;
	
	private String rutaOrigen;
	private String rutaDestino;
	private String fchCabezal;
	/**
	 * Constructor.
	 */
	public PCovLoadTicket() {

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 26/02/2015
	 * @see
	 */
	@Override
	protected void prepare() {
		//C:\Adempiere\covadongaOrigen
		//C:\Adempiere\covadongaDestino
		rutaOrigen = getRutaOrigen();  
		rutaDestino = getRutaDestin(); 
		this.model = new MCovLoadTicket(getCtx(), this.getRecord_ID(), null);
		//copiarArchivos();

	}
	private void copiarArchivos() {
		File salida = new File(rutaOrigen);

		File historico = new File(rutaDestino);
		if (!historico.exists()) {
			if (!historico.mkdir()) {
				throw new AdempiereException();
			}
		}
		String[] files = salida.list();

		for (int i = 0; i < files.length; i++) {
			if (files[i].startsWith("SalidaPazos")){
				//MCFEInvoice status = new MCFEInvoice(this.getCtx(), 0,this.get_TrxName());
				File source = new File(salida + "\\" + files[i]);
				File dest = new File(historico + "\\"+ "OK"+ files[i]);
				//if (retornoOK(source, status)) {
					try {
						copyFile(source, dest);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//source.delete();
				//}
				// status.saveEx();	
			}

		}
	}
	
	/**
	 * Metodo para copiar archivos de un lugar a otro OpenUp Ltda. Issue #2208
	 * 
	 * @author Leonardo Boccone - 04/07/2014
	 * @see
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File destFile)
			throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}
//		try {
			
//			InputStream in = new FileInputStream(sourceFile);
//			OutputStream out = new FileOutputStream(destFile);
//			byte[] buf = new byte[1024];
//			int len;
//			 
//			while ((len = in.read(buf)) > 0) {
//			  out.write(buf, 0, len);
//			}
//			File rename = new File("_"+sourceFile.getName().toString());
//			sourceFile.renameTo(rename);
//			in.close();
//			out.close();
			if (!destFile.exists()) {
				destFile.createNewFile();
			}

			FileChannel origen = null;
			FileChannel destino = null;
			try {
				origen = new FileInputStream(sourceFile).getChannel();
				destino = new FileOutputStream(destFile).getChannel();

				long count = 0;
				long size = origen.size();
				while ((count += destino.transferFrom(origen, count, size - count)) < size)
					;
			} finally {
				if (origen != null) {
					origen.close();
				}
				if (destino != null) {
					destino.close();
				}
			}
	}

	/**
	 * 
	 * @author Sylvie Bouissa - 17/04/2015
	 * @see
	 */
		private String getRutaDestin() {
		// TODO Auto-generated method stub
		return "C:\\Adempiere\\covadongaDestino";
	}

		/**
		 * 
		 * @author Sylvie Bouissa - 17/04/2015
		 * @see
		 */
		private String getRutaOrigen() {
			String sql = "";
			ResultSet rs = null;
			PreparedStatement pstmt = null;

			return "C:\\Adempiere\\covadongaOrigen";
//			sql = "select * from uy_cfe";
//			try {
//				pstmt = DB.prepareStatement(sql, null);
//				rs = pstmt.executeQuery();
//				while (rs.next()) {
//					rutaSalida = rs.getString("salida");
//
//				}
//			} catch (Exception e) {
//
//				throw new AdempiereException(e);
//
//			}

		}

		/* (non-Javadoc)
		 * @see org.compiere.process.SvrProcess#doIt()
		 * OpenUp Ltda. Issue # 
		 * @author Gabriel Vila - 26/02/2015
		 * @see
		 * @return
		 * @throws Exception
		 */
		@Override
		protected String doIt() throws Exception {
			File salida = null; File historico = null;
			String retorno = "";
			if(!rutaOrigen.equals("") && !rutaDestino.equals("")){
				salida = new File(rutaOrigen);
				
				historico = new File(rutaDestino);
				if (!historico.exists()) {
					if (!historico.mkdir()) {
						throw new AdempiereException();
					}
				}
				String[] files = salida.list();

				for (int i = 0; i < files.length; i++) {
					if (files[i].startsWith("SalidaPazos")){
						//MCFEInvoice status = new MCFEInvoice(this.getCtx(), 0,this.get_TrxName());
						File source = new File(salida + "\\" + files[i]);
						File dest = new File(historico + "\\"+ "Procesado_"+ files[i]);
						if(source!=null){
							retorno = procesarArchivo(source);
							if(retorno.equals("OK")){
								
								//if (retornoOK(source, status)) {
								try {
									verResultado(dest);
									copyFile(source, dest);	
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									retorno = e.getMessage();
								}
							}	
						}
						
						
					}

				}
				
			}
			return retorno;
		}
	
//	@Override
//	protected String doIt() throws Exception {		
//		mAudit = new MAuditCovLoadTicket(getCtx(), 0, get_TrxName());
//		mAudit.setStartTime(new Timestamp (System.currentTimeMillis())); //Seteo fecha y hora de comienzo
//		mAudit.setName("Auditoria"+mAudit.getStartTime());
//		String fileName = this.model.getFileName();
//		//fileName = fileName.replaceAll(regex, replacement)
//		String fileNameStr[] =fileName.split("\\\\");
//		mAudit.setFileName(fileNameStr[fileNameStr.length-1]);
//		File archivo = new File (fileName);
//		FileReader fr = new FileReader (archivo);
//		BufferedReader br = new BufferedReader(fr);
//		MCovTicketHeader mConH = null;
//		int cont = 0;
//		String line = br.readLine();
//		while(line != null){
//			try{
//				cont ++;
//				System.out.println("Linea num: "+cont);
//				String tipo = line.substring(0,1);
//				if(cont==14872){
//					System.out.println("");
//				}
//				if (tipo.equalsIgnoreCase("C")){			// Es cabezal
//					cantCabezal++;
//					mConH = parseHeader(line,String.valueOf(cont));			
//				} else if (tipo.equalsIgnoreCase("L")){		// Es linea
//					cantLineas++;
//					parseLine(line, mConH.get_ID(),String.valueOf(cont));
//					System.out.println("asd");
//				}			
//			}catch(Exception ex){
//				System.out.println(ex.getMessage());
//				throw new AdempiereException(ex.getMessage());
//			}
//			line = br.readLine();
//			cantTotal++;
//		}
//		verResultado();
//		return "OK";
//	}
	/**
	 * Metodo que procesa un archivo 
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 28/04/2015
	 * @param archivo (Archivo SalidaPazos a ser procesado)
	 */
	private String procesarArchivo(File archivo){
		String error="";
		try{
			mAudit = new MAuditCovLoadTicket(getCtx(), 0, get_TrxName());
			mAudit.setStartTime(new Timestamp (System.currentTimeMillis())); //Seteo fecha y hora de comienzo
			mAudit.setName("Auditoria"+mAudit.getStartTime());
			String fileName = this.model.getFileName();
			//fileName = fileName.replaceAll(regex, replacement)
			String fileNameStr[] =archivo.getName().split("\\\\");
			mAudit.setFileName(fileNameStr[fileNameStr.length-1]);
			mAudit.setDescription("Origen: "+archivo.getAbsolutePath());
			///File archivo = new File (fileName);
			FileReader fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			MCovTicketHeader mConH = null;
			int cont = 0;
			String line = br.readLine();
			while(line != null){
				try{
					cont ++;
					System.out.println("Linea num: "+cont);
					String tipo = line.substring(0,1);
					if (tipo.equalsIgnoreCase("C")){			// Es cabezal
						cantCabezal++;
						mConH = parseHeader(line,String.valueOf(cont));			
					} else if (tipo.equalsIgnoreCase("L")){		// Es linea
						cantLineas++;
						parseLine(line, mConH.get_ID(),String.valueOf(cont));
						//System.out.println("asd");
					}			
				}catch(Exception ex){
					System.out.println(ex.getMessage());
					throw new AdempiereException(ex.getMessage());
				}
				line = br.readLine();
				cantTotal++;
			}
			//verResultado();
			return "OK";
		}catch(Exception e){
			error = e.getMessage();
			throw new AdempiereException(e.getMessage());
		}
		//return "ERROR "+error;
	}
	private void verResultado(File destino) {
		BigDecimal consistencia = new BigDecimal(0);
		consistencia = this.sumoCabezales.subtract(this.sumaLineas);
		mAudit.setpath(destino.getAbsolutePath());
		mAudit.settotallinesfile(String.valueOf(cantTotal));
		mAudit.settotalheaders(String.valueOf(cantCabezal));
		mAudit.setTotalLines(String.valueOf(cantLineas));
		mAudit.setEndTime(new Timestamp (System.currentTimeMillis())); //Seteo fecha y hora de comienzo
		mAudit.setCov_LoadTicket_ID(model.get_ID());
		mAudit.setamtconsistenciavtas(consistencia);
		mAudit.setlventaenefectivo((MCovTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","1",model.get_ID(),get_TrxName())).subtract(MCovTicketLineEfectivo.calcularCambioMoneda(getCtx(),"2","1",model.get_ID(),get_TrxName())).toString());
		// sodexo
		mAudit.setlventasefectivosodexo(MCovTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","10",model.get_ID(),get_TrxName()).toString());
		// cheques
		mAudit.setlventascheque(MCovTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","3",model.get_ID(),get_TrxName()).toString());
		// tajeta manual
		mAudit.setlventascheque(MCovTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","14",model.get_ID(),get_TrxName()).toString());
		// Devolucion de envases
		mAudit.setlventascheque(MCovTicketLineEfectivo.calcularVentaMonedaCodigoMdioPago(getCtx(),"1","13",model.get_ID(),get_TrxName()).toString());

		
		mAudit.saveEx();
		//System.out.println("Diferencia "+consistencia+" C:"+cantCabezal+" L:"+cantLineas+" Total:"+cantTotal);
		//System.out.println("C_1"+countconsistenciaC+" L_4"+countconsistenciaL4);
		
	}


	private MCovTicketHeader parseHeader(String line, String posicion){
		MCovTicketHeader ret = new MCovTicketHeader(getCtx(), 0, get_TrxName());
		String lineSplit[] = line.split("#");
		
		MCovTicketType tktType = MCovTicketType.forValueAndHeader(getCtx(), lineSplit[1], "Y", get_TrxName());
		try{
			cabezalActual = lineSplit[1]; //guardo el cabezal que estoy procesando 
					
			if(tktType!=null){
				ret.setCov_TicketType_ID(tktType.get_ID()); //pos1
			}else{
				//crear lineas con detalles
			}
			ret.setCov_LoadTicket_ID(model.get_ID());
			ret.setcodigocaja(lineSplit[2]); //pos2
			ret.setnumeroticket(lineSplit[3]); //pos3
			if(!lineSplit[4].equals("")){
				ret.setcodigocajera(lineSplit[4]);//pos4
			}			
			String fecha =lineSplit[5]; //pos5
			ret.settimestampticket(Converter.convertirYYYYMMddHHMMss(fecha));
			fchCabezal = fecha.substring(0,8);
			//			int largo = fecha.length();
			//			System.out.println(largo);
			//			String anio = fecha.substring(0, 4);
			//			String mes = fecha.substring(4,6);
			//			String dia = fecha.substring(6,8);
			//			String hra = fecha.substring(8,10);
			//			String min = fecha.substring(10,12);
			//			String seg = fecha.substring(12,14);
			//			ret.settimestampticket(Timestamp.valueOf(anio+"-"+mes+"-"+dia+" "+hra+":"+min+":"+seg));
			//ret.setestadotiquet(estadotiquet);
			ret.setestadoticket(lineSplit[6]); //pos6
			ret.setcantidadarticulos(Integer.valueOf(lineSplit[7]));//pos7
			ret.settotalapagar(new BigDecimal(lineSplit[8])); //pos8
			ret.settipocliente(lineSplit[9]); //pos9
			ret.setcantidadlineas(Integer.valueOf(lineSplit[10])); //pos10
						
			if(lineSplit[1].equals("1")){// se trata de un tiket de venta
				if(!lineSplit[11].equals("0")){ //si es cero es un ticket normal sino se deben guardar los siguientes datos
					if (lineSplit[11] !=null) ret.setcodigocajadevolucion(Integer.valueOf(lineSplit[10]));
					if (lineSplit[12] !=null) ret.setnumeroticketdevolucion(Integer.valueOf(lineSplit[12]));
				}
			
				lineVenta = true;
			//	this.countconsistenciaC ++;
				
			//	this.sumoCabezales = this.sumoCabezales.add(new BigDecimal(lineSplit[8])); //+ Double.valueOf(lineSplit[8]);
			}else{
				lineVenta = false;
			}
			ret.setnumerolinefile(posicion);
			ret.saveEx(get_TrxName());
			mAudit.updateCountCabezalAudit(lineSplit[1],lineSplit[8]);
			
		}catch(Exception e){
			
		}

		return ret;
	}
		
	private void parseLine(String line, int headerId,String numerofila){
		String lineSplit[] = line.split("#");
		
		//if (lineSplit[2].equalsIgnoreCase("1")){
			//saveLine(headerId, lineSplit[4], lineSplit[5], lineSplit[6], lineSplit[14]);  
			// Según documentación: 6 = Precio, 14 = PrecioDescuentoTotal" 
		//}
	//	if (!lineSplit[2].equals("4")&&!lineSplit[2].equals("5")){
//		}else if(lineSplit[2].equalsIgnoreCase("52")){
//		mAudit.updateMontoTotalPagoCaja(lineSplit[5]);
//		}else if (lineSplit[2].equalsIgnoreCase("85")){ //Venta Tarjeta Offline
//		System.out.println("ACA PARAMOS");
//		mAudit.updateLineaTarjetaOffline(lineSplit[8]);
//		}else if (lineSplit[2].equalsIgnoreCase("3")){ //Venta CHEQUES
//		mAudit.updateLineaCheque(lineSplit[8]);
			if(lineSplit[2].equals("1")){//Linea de venta
				MCovTicketLine lineTktVenta = new MCovTicketLine(getCtx(),0,get_TrxName());
				lineTktVenta.setearDatosLineaItemVta(lineSplit,fchCabezal,headerId);
				lineTktVenta.saveEx();
				//saveLine(headerId, lineSplit[4], lineSplit[5], lineSplit[6], lineSplit[14],lineSplit[2],codMedioPago);  
				// Según documentación: 6 = Precio sin iva, 14 = PrecioDescuentoTotal" 
			}else if(lineSplit[2].equalsIgnoreCase("97")){
				MCovTicketLineRetiro lineaRetiro = new MCovTicketLineRetiro(getCtx(),0,get_TrxName());
				lineaRetiro.setearDatosLineaRetiro(lineSplit,fchCabezal,headerId);
				lineaRetiro.saveEx();
				
				if(lineSplit[5].equalsIgnoreCase("1")){ // codigo moneada (1=pesos)	
					if(lineSplit[4].equalsIgnoreCase("1")){//Codigo medio pago (efectivo)
						mAudit.updateMontoTotalRetiro(lineSplit[6]);
					}
				}

			}else if(lineSplit[2].equalsIgnoreCase("92")){// Liena Factura 
				MCovTicketLineFactura lineFactura = new MCovTicketLineFactura(getCtx(),0,get_TrxName());
				lineFactura.setDatosLineaFactura(lineSplit,fchCabezal,headerId);
				lineFactura.saveEx();
				if(cabezalActual.equals("11")){ //Cabezal factura 11
					mAudit.updateMontoTotalFactura(lineSplit[10]);
				}else if(cabezalActual.equals("16")){
					mAudit.updateMontoTotalNtaCredito(lineSplit[10]);
				}
			}else if(lineSplit[2].equalsIgnoreCase("95")){
				MCovTicketLineFondeo lineFondeo = new MCovTicketLineFondeo(getCtx(),0,get_TrxName());
				lineFondeo.setDatosLineaFondeo(lineSplit,fchCabezal,headerId);
				lineFondeo.saveEx();
				mAudit.updateMontoTotalFondeo(lineSplit[6]);
				// ---------------Tipos de ventas 
			}else if (lineSplit[2].equalsIgnoreCase("37")){ //Venta Tarjeta
				MCovTicketLineTarjeta lineTjta = new MCovTicketLineTarjeta(getCtx(), 0, get_TrxName());
				lineTjta.setDatosLineTarjeta(lineSplit,fchCabezal,headerId);
				lineTjta.saveEx();
				if(lineSplit[5].equals("1")){ // Codigo Moneda --1pesos
					if(lineSplit[14].equals("1")){
						mAudit.updateLineaTarjeta(lineSplit[8]);
					}else{
						mAudit.updateLineaTarjetaCuota(lineSplit[8]);
					}
				}

			}else if (lineSplit[2].equalsIgnoreCase("40")){ //Venta Lunchon
				MCovTicketLineLuncheon lineLuncheon = new MCovTicketLineLuncheon(getCtx(),0,get_TrxName());
				lineLuncheon.setDatosLineLuncheon(lineSplit,fchCabezal,headerId);
				lineLuncheon.saveEx();
				mAudit.updateLineaLuncheon(lineSplit[8]);

			}else if (lineSplit[2].equalsIgnoreCase("90")){ //Venta ticket alimentacion
				mAudit.updateLineaTAlimentacion(lineSplit[8]);
			}else if (lineSplit[2].equalsIgnoreCase("9")){  //Venta venta efectivo
				
				MCovTicketLineEfectivo lineEfectvio = new MCovTicketLineEfectivo(getCtx(),0,get_TrxName());
				lineEfectvio.setearDatosLineaEfectivo(lineSplit,fchCabezal,headerId);
				lineEfectvio.saveEx();
				MCovTicketHeader cab = (MCovTicketHeader) lineEfectvio.getCov_Ticket_Header();				
				if(lineSplit[5].equals("1")){ //CodicoMoneda 1=pesos
					if (lineSplit[4].equals("1")){// Medio de pago EFECTIVO !!!!
						//if(lineSplit[12].equals("0")){// Tipo operacion VENTA
							//if(lineSplit[11].equals("0")){ // si es 0 venta si es uno devolucio No
						if(cab.getestadoticket().equals("F") && (cab.getCov_TicketType().getcodigo().equals("3") || cab.getCov_TicketType().getcodigo().equals("1"))){
							mAudit.updateLineaPagoEfectivo(lineSplit[8],lineSplit[10],lineSplit[6],headerId,true);
						}
						//}
							
						//}
//					}else if (lineSplit[4].equals("2")) { // Tarjeta no tiene tratamiento efectivo
//						System.out.println("TARJETAAAAAAAAAAAA");
					}else if(lineSplit[4].equals("10")){//Ticket medio de pago efectivo SODEXO
						System.out.println("SODEXO");
						mAudit.updateLineaEfectivoSodexo(lineSplit[8]);
					}else if(lineSplit[4].equals("3")){ // cheques
						mAudit.updateLineaCheque(lineSplit[8]);
						System.out.println("CHEQUES");
					}else if(lineSplit[4].equals("14")){// tajeta manual
						System.out.println("TARJETA MANUAL");
						mAudit.updateLineaTarjetaManual(lineSplit[8]);
//					}else if (lineSplit[4].equals("6")) {// tarjeta cuota no tiene tratamiento efectivo
//						System.out.println("TARJETA CUOTA");
					}else if (lineSplit[4].equals("13")){ // Devolucion de envases
						mAudit.updateLineaDevolucionEnvase(lineSplit[8]);						
					}
				}if(lineSplit[5].equals("2")){ //CodicoMoneda 2=dolares
					mAudit.updateLineaVtaEfectivoDolares(lineSplit[8]);
					System.out.println();
					if (lineSplit[4].equals("1")){// Medio de pago EFECTIVO !!!!
						if(cab.getestadoticket().equals("F") && (cab.getCov_TicketType().getcodigo().equals("3") || cab.getCov_TicketType().getcodigo().equals("1"))){
							mAudit.updateLineaPagoEfectivo(lineSplit[8],lineSplit[10],lineSplit[6],headerId,false);
						}
					}
				}
				
				
			}else if (lineSplit[2].equalsIgnoreCase("104")){  //Venta cuenta corriente
				MCovTicketLineCtaCte lcc = new MCovTicketLineCtaCte(getCtx(),0,get_TrxName());
				lcc.setearDatosLineaCC(lineSplit,fchCabezal,headerId);
				lcc.saveEx();
				if(lineSplit[5].equals("1")){	//pesos
					
					mAudit.updateLineaPagoCtaCte(lineSplit[8],lineSplit[10]);
				}
				
			}else if (lineSplit[2].equalsIgnoreCase("99")){  //Venta cuenta corriente
				MCovTicketLineClienteCC lClienteCC = new MCovTicketLineClienteCC(getCtx(),0,get_TrxName());
				lClienteCC.setearDatosLineaClienteCC(lineSplit,fchCabezal,headerId);
				lClienteCC.saveEx();
			}else if (lineSplit[2].equalsIgnoreCase("47")){
				MCovTicketLinePagoServ lPgoServ = new MCovTicketLinePagoServ(getCtx(),0,get_TrxName());
				lPgoServ.setearDatosPagoServicios(lineSplit,fchCabezal,headerId,numerofila);
				lPgoServ.saveEx();
			}else if (lineSplit[2].equals("94")){
				System.out.println("--------------------94");
			}else {//Se guardan las restantes lineas en el log de lineas UY_LogFile
				MLogFile logF = new MLogFile(getCtx(),0,get_TrxName());
				logF.setCov_Ticket_Header_ID(headerId);
				logF.setdatofila(line);
				logF.setDescription("TipoLinea: "+lineSplit[2]);
				logF.setnumerofila(numerofila);
				logF.setName(mAudit.getFileName());
				logF.setregistropadre(headerId);
				logF.saveEx();

			}
		//	this.countconsistenciaL4 ++;
//		}else{
//			if(lineSplit[2].equals("4") && lineVenta){
//				this.countconsistenciaL4 ++;
//				this.sumaLineas =this.sumaLineas.add(new BigDecimal(lineSplit[4]));// + Double.valueOf(lineSplit[4]);
//			}
//		}
	}
	
//	private void saveLine(int headerId, String codProd, String qProd, String unitPrice, String totalLine,String tipoLinea,String codMedioPago){
//		MCovTicketLine objLine = new MCovTicketLine(getCtx(), 0, get_TrxName());
//		
//		objLine.setCov_Ticket_Header_ID(headerId);
//		
//		if(!(tipoLinea.equals("1"))){
//			codProd = "0"; qProd="0";unitPrice="0";unitPrice="0";
//		}
//		if(!codProd.equals("")){
//			objLine.setcodigoarticulo(codProd);
//		}
//		if(!qProd.equals("")){
//			objLine.setcantidad(Integer.valueOf(qProd));
//		}
//		if(!unitPrice.equals("")){
//			objLine.setpreciounitario(new BigDecimal(Double.valueOf(unitPrice)));
//		}
//		if(!totalLine.equals("")){
//			objLine.setpreciodescuentototal(new BigDecimal(Double.valueOf(totalLine)));
//		}
//		if(!tipoLinea.equals("")){
//			objLine.set_ValueOfColumn("TipoLinea", tipoLinea);			
//		}
//		if(!codMedioPago.equals("")){
//			objLine.set_ValueOfColumn("CodigoMedioDePago", codMedioPago);
//		}
//
//		objLine.saveEx(get_TrxName());
//	}
	

}
