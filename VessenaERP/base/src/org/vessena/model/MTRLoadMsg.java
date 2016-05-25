/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 24/02/2015
 */
package org.openup.model;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MJob;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.EMail;

/**
 * org.openup.model - MTRLoadMsg
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo de envio de mensajes de carga.
 * @author Gabriel Vila - 24/02/2015
 * @see
 */
public class MTRLoadMsg extends X_UY_TR_LoadMsg {

	private static final long serialVersionUID = -989557295785322467L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_LoadMsg_ID
	 * @param trxName
	 */
	public MTRLoadMsg(Properties ctx, int UY_TR_LoadMsg_ID, String trxName) {
		super(ctx, UY_TR_LoadMsg_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadMsg(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Carga lineas con datos a seleccionar para el envio de mensajes.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 24/02/2015
	 * @see
	 */
	public void loadData() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			DB.executeUpdateEx( " delete from uy_tr_loadmsgline where uy_tr_loadmsg_id =" + this.get_ID(), null);
			DB.executeUpdateEx( " delete from uy_tr_loadmsguser where uy_tr_loadmsg_id =" + this.get_ID(), null);
			DB.executeUpdateEx( " delete from uy_tr_loadmsglog where uy_tr_loadmsg_id =" + this.get_ID(), null);
			
			String whereConditions = "ot.datetrx between ? and ? ";
			
			sql = " select ot.datetrx, ot.uy_tr_transorder_id, trip.c_bpartner_id_from, trip.c_bpartner_id_to, tline.uy_tr_trip_id, tline.uy_tr_crt_id, tline.uy_tr_transorderline_id " +
				  " from uy_tr_transorder ot " +
				  " inner join uy_tr_transorderline tline on ot.uy_tr_transorder_id = tline.uy_tr_transorder_id " +
				  " inner join uy_tr_trip trip on tline.uy_tr_trip_id = trip.uy_tr_trip_id " +
				  " where tline.isreleased='N' " +
				  " and " + whereConditions + 
				  " order by trip.c_bpartner_id_from ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setTimestamp(1, this.getStartDate());
			pstmt.setTimestamp(2, this.getEndDate());
									
			rs = pstmt.executeQuery();

			int cBPartnerID = 0;
			
			while (rs.next()){
				MTRLoadMsgLine line = new MTRLoadMsgLine(getCtx(), 0, get_TrxName());
				line.setUY_TR_LoadMsg_ID(this.get_ID());
				line.setDateTrx(rs.getTimestamp("datetrx"));
				line.setUY_TR_TransOrder_ID(rs.getInt("uy_tr_transorder_id"));
				line.setUY_TR_TransOrderLine_ID(rs.getInt("uy_tr_transorderline_id"));
				
				line.setC_BPartner_ID_From(rs.getInt("c_bpartner_id_to")); // Estan al reves porque en la ventana de expediente quedaron al reves.
				line.setC_BPartner_ID_To(rs.getInt("c_bpartner_id_from"));
				
				line.setUY_TR_Trip_ID(rs.getInt("uy_tr_trip_id"));
				line.setUY_TR_Crt_ID(rs.getInt("uy_tr_crt_id"));
				line.setIsSelected(false);
				
				line.saveEx();
				
				// Corte de control por cliente para carga de contactos
				if (rs.getInt("c_bpartner_id_from") != cBPartnerID){

					cBPartnerID = rs.getInt("c_bpartner_id_from");
					
					// Guardo contactos de envio para este cliente y dejo seleccionados aquellos que tienen Posicion = Importaciones.
					
					// Obtengo contactos de este cliente
					MUser[] users = MUser.getOfBPartner(getCtx(), cBPartnerID, null);
					for (int i = 0; i < users.length; i++){
						
						MUser user = users[i];
						
						MTRLoadMsgUser msguser = new MTRLoadMsgUser(getCtx(), 0, get_TrxName());
						msguser.setUY_TR_LoadMsg_ID(this.get_ID());
						msguser.setC_BPartner_ID(cBPartnerID);
						
						msguser.setAD_User_ID(user.get_ID());
						msguser.setEMail(user.getEMail());
						
						MJob job = (MJob)user.getC_Job();
						if (job != null){
							if (job.get_ID() > 0){
								if (job.get_ValueAsBoolean("IsNeeded")){
									msguser.setIsSelected(true);
								}
							}
						}
						
						msguser.saveEx();
					}
				}
			}
			
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
	 * Envio de emails por cada linea seleccionada de este modelo.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 24/02/2015
	 * @see
	 */
	public void sendEmails() {
		
		try {
			
			// Tipo de mensaje
			MTRLoadMsgType msgtype = (MTRLoadMsgType)this.getUY_TR_LoadMsgType();
			
			// Obtengo lineas seleccionadas para envio de emails
			List<MTRLoadMsgLine> lines = this.getSelectedLines();
			
			// Envio mail por cada contacto seleccionado de cada importador de cada linea seleccionada
			for (MTRLoadMsgLine line: lines){
				
				// Obtengo contactos de este cliente, seleccionados para envío de emails.
				List<MTRLoadMsgUser> msgusers = this.getSelectedUsersForPartner(line.getC_BPartner_ID_To());
				for (MTRLoadMsgUser msguser: msgusers){

					if ((msguser.getEMail() == null) || msguser.getEMail().trim().equalsIgnoreCase("")){
						continue;
					}
					
					if (!msguser.getEMail().contains("@")){
						continue;
					}
					
					String referenceNo = ((MTRTrip)line.getUY_TR_Trip()).getReferenceNo();
					if (referenceNo == null) referenceNo = "--";
					
					String nameImportador = new MBPartner(getCtx(), line.getC_BPartner_ID_To(), null).getName2();
					String nameExportador = new MBPartner(getCtx(), line.getC_BPartner_ID_From(), null).getName2();
					
					StringBuilder body = new StringBuilder();			
					String subject = "Comas Arocena S.A. - Situación de Cargas: " + referenceNo; 
					String to = msguser.getEMail().trim();
					
					body.append(msgtype.getMailText());

					body = new StringBuilder(body.toString().replaceAll("#FACTURA#", referenceNo));
					body = new StringBuilder(body.toString().replaceAll("#IMPORTADOR#", nameImportador));			
					body = new StringBuilder(body.toString().replaceAll("#EXPORTADOR#", nameExportador));
					
					String message = this.sendEmail(body.toString(), subject, to, true, null);
					
					
					// Log de envio
					MTRLoadMsgLog msglog = new MTRLoadMsgLog(getCtx(), 0, null);
					msglog.setUY_TR_LoadMsg_ID(this.get_ID());
					msglog.setC_BPartner_ID(line.getC_BPartner_ID_To());
					msglog.setAD_User_ID(msguser.getAD_User_ID());
					msglog.setEMail(msguser.getEMail());
					msglog.setUY_TR_TransOrder_ID(line.getUY_TR_TransOrder_ID());
					msglog.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
					msglog.setUY_TR_Trip_ID(line.getUY_TR_Trip_ID());
					msglog.setUY_TR_TransOrderLine_ID(line.getUY_TR_TransOrderLine_ID());
					
					if (message != null){
						
						msglog.setIsSent(true);

						// Refreso orden de transporte
						MTRTransOrderLine otline = (MTRTransOrderLine)line.getUY_TR_TransOrderLine();
						if (msgtype.isReleased()){
							otline.setIsReleased(true);
						}
						else{
							otline.setIsReleased(false);
						}
						otline.saveEx();
						
						line.setIsSent(true);
						line.saveEx();
					}
					else{
						
						msglog.setIsSent(false);
						msglog.setDescription(message);
					}
					
					msglog.saveEx();
				}
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Obtiene y retorna lista de lineas seleccionadas de este modelo.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 24/02/2015
	 * @see
	 * @return
	 */
	public List<MTRLoadMsgLine> getSelectedLines() {
		
		String whereClause = X_UY_TR_LoadMsgLine.COLUMNNAME_UY_TR_LoadMsg_ID + "=" + this.get_ID() +
					" and " + X_UY_TR_LoadMsgLine.COLUMNNAME_IsSelected + "='Y' " +
					" and " + X_UY_TR_LoadMsgLine.COLUMNNAME_IsSent + "='N' ";
		
		List<MTRLoadMsgLine> lines = new Query(getCtx(), I_UY_TR_LoadMsgLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
	}
	
	
	/***
	 * Obtiene y retorna usuarios (contactos) seleccionados para un determinado importador recibido.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 25/02/2015
	 * @see
	 * @param cBPartnerID
	 * @return
	 */
	public List<MTRLoadMsgUser> getSelectedUsersForPartner(int cBPartnerID){
		
		String whereClause = X_UY_TR_LoadMsgUser.COLUMNNAME_UY_TR_LoadMsg_ID + "=" + this.get_ID() +
				" and " + X_UY_TR_LoadMsgUser.COLUMNNAME_C_BPartner_ID + "=" + cBPartnerID +
				" and " + X_UY_TR_LoadMsgUser.COLUMNNAME_IsSelected + "='Y' ";
	
		List<MTRLoadMsgUser> lines = new Query(getCtx(), I_UY_TR_LoadMsgUser.Table_Name, whereClause, get_TrxName()).list();
	
		return lines;
	}

	/***
	 * Envio de email con mensaje de carga.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 25/02/2015
	 * @see
	 * @param body
	 * @param subject
	 * @param to
	 * @param html
	 * @param attachment
	 */
	private String sendEmail(String body, String subject, String to, boolean html, File attachment){

		String message = null;
		
		try{
			if (to == null) return message;
			if (to.trim().equalsIgnoreCase("")) return message;

			MTRConfig config = MTRConfig.forValue(getCtx(), "general", null);
			MClient client = MClient.get(getCtx(), this.getAD_Client_ID());
			
			if ((config.getEMail() == null) || (config.getEMail().equalsIgnoreCase(""))){
				throw new AdempiereException("Falta configurar Direccion de Envio de Email");
			}
			
			EMail email = new EMail (client, config.getEMail(), to, subject, body, html);
			email.createAuthenticator(config.getEMail(), config.getPassword());

			if (attachment != null){
				email.addAttachment(attachment);	
			}
			
			if (email != null){
				String msg = email.send();
				if (!EMail.SENT_OK.equalsIgnoreCase(msg)){
					message = "No se puedo enviar email a : " + to + ": " + msg;
				}
			}
			
		}

		catch (Exception e){
			message = e.getMessage();
		}
		
		return message;
	}

}
