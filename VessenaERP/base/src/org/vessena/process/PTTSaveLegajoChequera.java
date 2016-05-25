/**
 * 
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MUser;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MTTCard;
import org.openup.model.MTTChequeraLine;
import org.openup.util.ItalcredSystem;

/**
 * @author gbrust
 *
 */
public class PTTSaveLegajoChequera extends SvrProcess {

	
	public PTTSaveLegajoChequera() {
		
	}

	
	@Override
	protected void prepare() {		

	}

	
	@Override
	protected String doIt() throws Exception {

		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ItalcredSystem system = new ItalcredSystem();

		try{		

			String sql = "SELECT DISTINCT uy_tt_card_id" +
						 " FROM uy_tt_card" + 
						 " WHERE uy_tt_card_id IN (SELECT uy_tt_card_id " +
						 "						   FROM uy_tt_comunicacard sc" + 
						 " 						   INNER JOIN uy_tt_comunica cc on sc.uy_tt_comunica_id = cc.uy_tt_comunica_id" + 
						 " 						   WHERE cc.docstatus='CO') " +
						 " and islegajochecksaved='N'";					    

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				MTTCard card = new MTTCard(this.getCtx(), rs.getInt("UY_TT_Card_ID"), null);
				
				//Clase necesaria				
				MTTChequeraLine chequeraLine = new MTTChequeraLine(this.getCtx(), card.getUY_TT_ChequeraLine_ID(), null);								
				
				if(chequeraLine.get_ID() > 0){
					//Datos a guardar
					String usuario = new MUser(this.getCtx(), this.getAD_User_ID(), null).getName();
					String cedula = card.getCedula();
														
					String observacion = "Chequera Clin Cash 6,9 y 12 Cuotas – $" + chequeraLine.getAmount() + ". Válido hasta " + df.format(chequeraLine.getDueDate()) + ". " + chequeraLine.getName();
					
					String familiaLegajo = "72";
					Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());					
					
					//Guardo la linea en el legajo					
					system.newLegajo(usuario, cedula, observacion, familiaLegajo, fechaCreacion);
					card.setIsLegajoCheckSaved(true);
					card.saveEx();
				}
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		

		return "Ok";
	}

}
