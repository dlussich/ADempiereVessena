/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 18, 2015
*/
package org.openup.process;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.openup.model.MDeliveryPoint;
import org.openup.model.MRReclamo;
import org.openup.model.MTTConfig;
import org.openup.model.MTTDelivery;
import org.openup.model.MTTDeliveryLine;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * org.openup.process - RTTIntercambioWrite
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 18, 2015
*/
public class PTTIntercambioWrite extends SvrProcess {

	MTTDelivery delivery = null;
	MTTConfig config = null;
	
	/***
	 * Constructor.
	*/
	public PTTIntercambioWrite() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		this.delivery = new MTTDelivery(getCtx(), this.getRecord_ID(), this.get_TrxName());
		
		this.config = MTTConfig.forValue(getCtx(), null, "tarjeta");
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		Workbook wb = null;
		
		try {
			
			// Si no tengo modelo no hago nada
			if ((this.delivery == null) || (this.delivery.get_ID() <= 0)){
				throw new AdempiereException("No se pudo obtener modelo de Envío de Bolsín");
			}
			
			// TEMPORAL REDPAGOS
			// Comento y sustituyo
			
			// Obtengo bolsines de este envío con destino a RedPagos
			//List<MTTDeliveryLine> lines = this.delivery.getSelectedLinesForDeliveryPoint(config.getUY_DeliveryPoint_ID());
			
			List<MTTDeliveryLine> lines = this.delivery.getSelectedLinesForParentDeliveryPoint(config.getUY_DeliveryPoint_ID());
			// FIN TEMPORAL
			
			
			if (lines.size() <= 0){
				throw new AdempiereException("No hay información en este envío para generar el archivo de intercambio.");
			}
			
			// Obtengo cuentas asociadas a este envío ordenadas por subagencia
			sql = " select uy_tt_card_id, subagencia, uy_r_reclamo_id, cedula, name, telephone, CliDigCtrl "
					+ " from uy_tt_card "
					+ " where uy_tt_delivery_id =? "
					// TEMPORAL REDPAGOS. COMENTO CONDICION
					//+ " and uy_deliverypoint_id =" + config.getUY_DeliveryPoint_ID() 
					// FIN TEMPORAL
					+ " and uy_tt_seal_id in (select uy_tt_seal_id from uy_tt_deliveryline where uy_tt_delivery_id =? " 
					+ " and isselected ='Y') "
					+ " and subagencia is not null "					
					+ " order by subagencia "; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, delivery.get_ID());
			pstmt.setInt(2, delivery.get_ID());

			rs = pstmt.executeQuery();

			// Corte por subagencia
			String subAgenciaAux = "";
			
			Date deliveryDate = (Date)delivery.getDateTrx();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			String deliveryDateST = sdf.format(deliveryDate);

			wb = new XSSFWorkbook();
	        Sheet sheet = wb.createSheet();
	        int rowCount = 0;
			
			while (rs.next()) {				
			
				if (!rs.getString("subagencia").trim().equalsIgnoreCase(subAgenciaAux)){

					Row rowHdr = sheet.createRow(rowCount);
					
					// Guardo cabezal en archivo para esta subagencia
					subAgenciaAux = rs.getString("subagencia").trim();

					// IdEmpreas. Identificador asignado por Redpagos. Largo 3
					Cell cell1 = rowHdr.createCell(0);
			        cell1.setCellValue(config.getEmpCodeRedPagos());
					
			        // Fecha. Fecha identificatoria del lote de tarjetas enviado. Largo 8
			        Cell cell2 = rowHdr.createCell(1);
			        cell2.setCellValue(deliveryDateST);
			        
			        // SubAgencia. Subagencia de destino. Largo 3
			        String agenciaCellST = subAgenciaAux;
			        if (agenciaCellST.length() < 3){
			        	agenciaCellST = org.apache.commons.lang.StringUtils.leftPad(agenciaCellST, 3, "0");
			        }
			        Cell cell3 = rowHdr.createCell(2);
			        cell3.setCellValue(agenciaCellST);
					
			        // CantidadTarjetasLote. Cantidad de tarjetas incluidas en el lote. Largo 4
			        String sqlCount = " select count(*) "
			        					+ " from uy_tt_card "
			        					+ " where uy_tt_delivery_id =" + delivery.get_ID()
			        					// TEMPORAL REDPAGOS. COMENTO CONDICION
			        					//+ " and uy_deliverypoint_id =" + config.getUY_DeliveryPoint_ID()
			        					// FIN TEMPORAL
			        					+ " and subagencia ='" + subAgenciaAux + "'";
			        					
			        int countCuentasAgencia = DB.getSQLValueEx(null, sqlCount);
			        //String countST = String.valueOf(countCuentasAgencia);
			        //countST = org.apache.commons.lang.StringUtils.leftPad(countST, 4, "0");			        
			        Cell cell4 = rowHdr.createCell(3);
			        //cell4.setCellValue(countST);
			        cell4.setCellValue(countCuentasAgencia);

			        Cell cell5 = rowHdr.createCell(4);
			        cell5.setCellValue(1);
			        
			        rowCount++;
				}
				
				// Guardo Linea en archivo

				Row rowLine = sheet.createRow(rowCount);

				// IdTarjeta. Identificador único de la tarjeta. Largo 16.
		        MRReclamo incidencia = new MRReclamo(getCtx(), rs.getInt("uy_r_reclamo_id"), null);
		        String idTarjetaST = incidencia.getDocumentNo().trim();
		        idTarjetaST = org.apache.commons.lang.StringUtils.leftPad(idTarjetaST, 16, "0");
		        Cell cell1 = rowLine.createCell(0);
		        cell1.setCellValue(idTarjetaST);
		        
				// TipoTarjeta. Se utilizará N para tarjetas nominadas e I para tarjetas innominadas. Largo 1.
		        Cell cell2 = rowLine.createCell(1);
		        cell2.setCellValue("N");
		        
				// DocumentoTarjetahabiente. Documento del tarjetahabiente. Largo 12.
		        String cedulaST = rs.getString("cedula");
		        if (cedulaST == null) cedulaST = "";
		        Cell cell3 = rowLine.createCell(2);
		        cell3.setCellValue(cedulaST + rs.getInt("CliDigCtrl"));
		        
				// NombreTarjetahabiente. Nombre completo del tarjetahabiente. Largo 60.
		        String nombreST = rs.getString("name");
		        if (nombreST == null) nombreST = "";
		        if (nombreST.length() > 60){
		        	nombreST = nombreST.substring(0, 59);
		        }
		        Cell cell4 = rowLine.createCell(3);
		        cell4.setCellValue(nombreST);
		        		        
				// TelfonoTarjetahabiente. Teléfono del tarjetahabiente. Largo 20.
		        String telefonoST = rs.getString("telephone");
		        if (telefonoST == null) telefonoST = "";
		        
		        // Mando numero de telefono como ceros porque italcred no quiere que redpagos sepa el numero 
		        telefonoST = "1";
		        
		        if (telefonoST.length() > 20){
		        	telefonoST = telefonoST.substring(0, 19);
		        }
		        Cell cell5 = rowLine.createCell(4);
		        cell5.setCellValue(telefonoST);
			
		        rowCount++;
			}

			String filePath = System.getProperty("user.home") + "/Desktop";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String fileName = filePath + "\\RP_" + delivery.getLevante().trim() + "_" + df.format(new Timestamp(System.currentTimeMillis())) + ".xlsx";

			FileOutputStream out = new FileOutputStream(fileName);
	        wb.write(out);
	        out.close();
	        
	        File fileIntercambio = new File(fileName);

	        this.sendEmail(fileIntercambio, true);
	        
			/*
			Workbook wb = new XSSFWorkbook();

	        CreationHelper factory = wb.getCreationHelper();

	        Sheet sheet = wb.createSheet();

	        Cell cell1 = sheet.createRow(3).createCell(5);
	        cell1.setCellValue("F4");

	        Cell cell2 = sheet.createRow(2).createCell(2);
	        cell2.setCellValue("C3");

	        String fname = "comments.xlsx";
	        FileOutputStream out = new FileOutputStream("/home/gabriel/Desarrollo/intercambio2.xlsx");
	        wb.write(out);
	        out.close();
	        */
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
			
			if (wb != null){
				try {
					wb.close();	
				} 
				catch (Exception e2) {
				}
			}
		}
		
		return "OK";
	}

	
	/***
	 * Envio de archivo por email a punto de distribución 
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 22, 2015
	 * @param html
	 */
	private void sendEmail(File fileIntercambio, boolean html){

		try{

			MTTConfig config = MTTConfig.forValue(this.getCtx(), this.get_TrxName(), "tarjeta");
			
			MDeliveryPoint delPoint = (MDeliveryPoint)config.getUY_DeliveryPoint();

			if ((delPoint.getEMail() == null) || (delPoint.getEMail().equalsIgnoreCase(""))){
				throw new AdempiereException("Falta parametrizar el Email del Punto de Distribución destino del Email");
			}
			
			MClient client = MClient.get(getCtx(), this.getAD_Client_ID());
			
			String subject = "Italcred. Archivo de Intercambio.";
			StringBuilder message = new StringBuilder();

			if (config.getMailText1() == null){
				message.append("Estimado Proveedor, " + "\n\n\n");
				message.append("Adjunto encontrara el Archivo de Intercambio correspondiente al numero de Levante: " + delivery.getLevante() + "\n");
				message.append("con fecha: " + delivery.getDateTrx() + "\n\n\n");
				message.append("Desde ya muchas gracias.");
			}
			else{
				message.append(config.getMailText1().trim());
			}
			
			EMail email = new EMail (client, config.getEMail(), delPoint.getEMail().trim(), subject, message.toString(), html);
			email.createAuthenticator(config.getEMail(), config.getPassword());
			email.addAttachment(fileIntercambio);
			
			if (email != null){
				String msg = email.send();
				if (EMail.SENT_OK.equals (msg))
				{
					log.info("Sent EMail " + subject + " to " + delPoint.getEMail().trim());
				}
				else
				{
					throw new AdempiereException("No se pudo enviar email con el archivo de intercambio");
				}
			}
		}

		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
	}
	
}
