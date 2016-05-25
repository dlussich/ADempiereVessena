/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 30/06/2013
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

/**
 * org.openup.process - PReclamoSetSigns
 * OpenUp Ltda. Issue #1091 
 * Description: Setea señales de semaforo de incidencias que hayan llegado a la fecha de cambio.
 * @author Gabriel Vila - 30/06/2013
 * @see
 */
public class PReclamoSetSigns extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PReclamoSetSigns() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 30/06/2013
	 * @see
	 */
	@Override
	protected void prepare() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 30/06/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String action = "";
		
		try{
			
			Timestamp today = new Timestamp(System.currentTimeMillis());
			
			sql = " select recsign.uy_r_reclamo_id, recsign.reclamosigntype, recsign.uy_r_reclamosign_id, " +
				  " sign.ad_image_id, sign.seqno, rec.datetrx, coalesce(rec.vencido,'N') as vencido, " +
				  " coalesce(cause.deadline,0) as deadline " +
				  " from uy_r_reclamosign recsign " +
				  " inner join uy_r_sign sign on recsign.uy_r_sign_id = sign.uy_r_sign_id " +
				  " inner join uy_r_reclamo rec on recsign.uy_r_reclamo_id = rec.uy_r_reclamo_id " +
				  " inner join uy_r_cause cause on rec.uy_r_cause_id = cause.uy_r_cause_id " +
				  " where recsign.isexecuted = 'N' " +
				  " and recsign.startdate <=? " +
				  " order by recsign.startdate ";
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setTimestamp(1, today);			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				// Actualizo Semaforo de esta incidencia en Bandeja de Entrada segun tipo de señal
				action = " update uy_r_inbox " +
						 " set trackimage_id =" + rs.getInt("ad_image_id") + ", " +
						 " seqno =" + rs.getInt("seqno") +
						 " where uy_r_reclamo_id =" + rs.getInt("uy_r_reclamo_id");

				if (rs.getString("reclamosigntype") != null){
					if (rs.getString("reclamosigntype").equalsIgnoreCase("CRITICO")){
						action = " update uy_r_inbox " +
								 " set criticalimage_id =" + rs.getInt("ad_image_id") + ", "  +
								 " criticalseqno =" + rs.getInt("seqno") +
								 " where uy_r_reclamo_id =" + rs.getInt("uy_r_reclamo_id");
					}
				}
				
				DB.executeUpdateEx(action, get_TrxName());
				
				// Actualizo esta señal de incidencia
				action = " update uy_r_reclamosign " +
						 " set isexecuted ='Y', " +
						 " enddate ='" + today + "' " + 
						 " where uy_r_reclamosign_id =" + rs.getInt("uy_r_reclamosign_id");
				DB.executeUpdateEx(action, get_TrxName());
				
				// Verifico si esta incidencia esta vencida segun dias desde su creacion contra plazo final del tema
				if (rs.getString("vencido").equalsIgnoreCase("N")){

					Timestamp fecIni = TimeUtil.trunc(rs.getTimestamp("datetrx"), TimeUtil.TRUNC_DAY);
					Timestamp fecHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);		
					int cantDias = TimeUtil.getDaysBetween(fecIni, fecHoy);
					if (rs.getInt("deadline") < cantDias){
						int diasvencido = cantDias - rs.getInt("deadline");
						action = " update uy_r_reclamo " +
								 " set vencido ='Y'" + ", "  +
								 " diasvencido =" + diasvencido +
								 " where uy_r_reclamo_id =" + rs.getInt("uy_r_reclamo_id");
					}
				}
			}
			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return "OK";
	}

}
