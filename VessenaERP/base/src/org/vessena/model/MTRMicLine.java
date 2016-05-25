/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolas
 *
 */
public class MTRMicLine extends X_UY_TR_MicLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2363617812451943352L;

	/**
	 * @param ctx
	 * @param UY_TR_MicLine_ID
	 * @param trxName
	 */
	public MTRMicLine(Properties ctx, int UY_TR_MicLine_ID, String trxName) {
		super(ctx, UY_TR_MicLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMicLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) {	

		/*MTRCrt crt = null;

		MTRMic hdr = new MTRMic(getCtx(),this.getUY_TR_Mic_ID(),get_TrxName());

		if(this.getUY_TR_Crt_ID()==hdr.getUY_TR_Crt_ID()){

			//se dispara metodo para baja de CRT en aduana, si resulta exitoso se borran los campos del CRT
			//y se retorna TRUE

			crt = new MTRCrt(getCtx(),this.getUY_TR_Crt_ID(),get_TrxName());

			if(hdr.getCrtStatus1().equalsIgnoreCase("ENBAJA") || hdr.getCrtStatus1().equalsIgnoreCase("VINCULADO") || hdr.getCrtStatus1().equalsIgnoreCase("ENMODIFICACION")){

				DB.executeUpdateEx("update uy_tr_mic set crtstatus1 = 'ENBAJA' where uy_tr_mic_id = " + hdr.get_ID(), get_TrxName());

				//disparo clase para envio a aduana
				PTRAduanaSendMICLogic logic = new PTRAduanaSendMICLogic(getCtx(),get_TrxName());
				logic.execute(getCtx(), hdr ,get_TrxName());	

				// Se cargan nuevamente los datos del cabezal para cargar los datos que establece el metodo de envio a Aduana
				hdr = new MTRMic(getCtx(),this.getUY_TR_Mic_ID(),get_TrxName());

				if(hdr.getCrtStatus1().equalsIgnoreCase("DESVINCULADO")){

					hdr.setUY_TR_Crt_ID(0);
					hdr.setImporte(Env.ZERO);
					hdr.setSeguro(Env.ZERO);
					hdr.setQtyPackage(Env.ZERO);
					hdr.setpesoBruto(Env.ZERO);
					hdr.setUY_TR_Border_ID(0);
					hdr.setLocationComment(null);
					hdr.setAmount(Env.ZERO);
					hdr.setUY_TR_PackageType_ID(0);
					hdr.setpesoNeto(Env.ZERO);
					hdr.setRemitente(null);
					hdr.setDestinatario(null);
					hdr.setConsignatario(null);
					hdr.setObservaciones2(null);
					hdr.setPrecinto2(null);
					hdr.setprecinto(null);
					hdr.setDescription(null);
					hdr.setObservaciones3(null);

					hdr.saveEx();

				} else ADialog.info(0,null,"No se pudo dar de baja el CRT '" + crt.getNumero() + "'. Verifique el mensaje de error en la pestaña 'Envio Aduana'"); 

			} else if(hdr.getCrtStatus1().equalsIgnoreCase("ENALTA") || hdr.getCrtStatus1().equalsIgnoreCase("DESVINCULADO")){

				hdr.setUY_TR_Crt_ID(0);
				hdr.setImporte(Env.ZERO);
				hdr.setSeguro(Env.ZERO);
				hdr.setQtyPackage(Env.ZERO);
				hdr.setpesoBruto(Env.ZERO);
				hdr.setUY_TR_Border_ID(0);
				hdr.setLocationComment(null);
				hdr.setAmount(Env.ZERO);
				hdr.setUY_TR_PackageType_ID(0);
				hdr.setpesoNeto(Env.ZERO);
				hdr.setRemitente(null);
				hdr.setDestinatario(null);
				hdr.setConsignatario(null);
				hdr.setObservaciones2(null);
				hdr.setPrecinto2(null);
				hdr.setprecinto(null);
				hdr.setDescription(null);
				hdr.setObservaciones3(null);

				hdr.saveEx();					
			}				

		}*/
		
		return true;	
		
	}
	
	/***
	 * Obtiene y retorna una linea de CRT en MIC/DTA segun ID de linea de OT recibido.
	 * OpenUp Ltda. Issue #3093
	 * @author Nicolas Sarlabos - 26/11/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRMicLine forOrderLine(Properties ctx, int oLineID, String trxName){
				
		String whereClause = X_UY_TR_MicLine.COLUMNNAME_UY_TR_TransOrderLine_ID + "=" + oLineID;
		
		MTRMicLine line = new Query(ctx, I_UY_TR_MicLine.Table_Name, whereClause, trxName)
		.first();
				
		return line;
	}
	
	/***
	 * Obtiene y retorna una linea de CRT en MIC/DTA segun ID de CRT y MIC recibidos.
	 * OpenUp Ltda. 
	 * @author Nicolas Sarlabos - 08/01/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRMicLine forCRT(Properties ctx, int crtID, int micID, String trxName){
				
		String whereClause = X_UY_TR_MicLine.COLUMNNAME_UY_TR_Crt_ID + "=" + crtID + " AND " + X_UY_TR_MicLine.COLUMNNAME_UY_TR_Mic_ID + "=" + micID;
		
		MTRMicLine line = new Query(ctx, I_UY_TR_MicLine.Table_Name, whereClause, trxName)
		.first();
				
		return line;
	}
	
	/***
	 * Obtiene y retorna id del proceso de envio de MIC/DTA a aduana
	 * OpenUp Ltda. Issue #2592
	 * @author Nicolas Sarlabos - 04/08/2014
	 * @see
	 * @return
	 */
	/*private int getAduanaSendProcessID() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try{
			
			sql = " select ad_process_id " +
					" from ad_process " +
					" where lower(value)='uy_ptr_aduanasendmic'";			

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

	}*/



}
