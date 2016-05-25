package org.openup.aduana;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MTRCrt;
import org.openup.model.MTRDuaCrt;
import org.openup.model.MTRDuaLine;
import org.openup.model.MTRDuaMic;
import org.openup.model.MTRMic;
import org.openup.model.MTRMicCont;
import org.openup.model.MTRPackageType;

//OpenUp. Raul Capecce. issue #3321 23/03/2015
//Carga de CRTs para el MIC agregado al DUA
public class PTRAduanaDuaGetCrts extends SvrProcess{

	private int micId;
	private int duaId;
	private int duaMicId;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] parameters = getParameter();
		for (int i = 0; i < parameters.length; i++) {
			String name = parameters[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("uy_tr_mic_id")) {
					this.micId = (parameters[i].getParameter() != null) ? ((BigDecimal) parameters[i].getParameter()).intValue() : 0;
				} else if (name.equalsIgnoreCase("uy_tr_dua_id")){
					this.duaId = (parameters[i].getParameter() != null) ? ((BigDecimal) parameters[i].getParameter()).intValue() : 0;
				} else if (name.equalsIgnoreCase("UY_TR_DuaMic_ID")){
					this.duaMicId = (parameters[i].getParameter() != null) ? ((BigDecimal) parameters[i].getParameter()).intValue() : 0; 
				}
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		MTRMic mic;
		MTRDuaCrt duaCrt;
		MTRDuaMic duaMic;
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		// Chequeo que solo haya un solo mic en la tabla
		// Un mic tiene que poder asociarse con varios duas,
		// Para esto tengo que mantener los bultos y pesos restantes.
		sql = "SELECT COUNT(*) FROM UY_TR_DuaMic WHERE UY_TR_Mic_ID = " + this.micId + " AND isExecuted = 'Y'";
		if (Integer.valueOf(DB.getSQLValue(get_TrxName(), sql)) != 0) throw new AdempiereException("Mic ya seleccionado para asociar");
		
		// Cargando CRT del cabezal del MIC
		mic = new MTRMic(getCtx(), this.micId, get_TrxName());
		duaCrt = new MTRDuaCrt(getCtx(), 0, get_TrxName());
		duaMic = new MTRDuaMic(getCtx(), this.duaMicId, get_TrxName());
		
		// Seteo para que solo se ejecute una vez este proceso por MIC obtenido
		duaMic.setIsExecuted(true);
		duaMic.saveEx(get_TrxName());
		
		duaCrt.setUY_TR_DuaMic_ID(this.duaMicId);
		duaCrt.setUY_TR_Mic_ID(this.micId);
		duaCrt.setUY_TR_Crt_ID(mic.getUY_TR_Crt_ID());
		duaCrt.setdescripcion(mic.getDescription());
		
		// Si no tiene tipo de bulto el crt en la continuación aviso y no cargo los crts
		if (mic.getUY_TR_PackageType_ID() == 0){
			MTRCrt crtTmp = new MTRCrt(getCtx(), mic.getUY_TR_Crt_ID(), get_TrxName());
			throw new AdempiereException(crtTmp.getNumero() + ": No tiene tipo de bulto asociado, necesario para la asociación del DUA");
		}
		
		duaCrt.setTipoBulto(mic.getUY_TR_PackageType().getValue());
		duaCrt.setCantidadBultos(mic.getQtyPackage());
		duaCrt.setCantidadBultosRestantes(mic.getQtyPackage());
		duaCrt.setpesoBruto(mic.getpesoBruto());
		duaCrt.setpesoBrutoRestante(mic.getpesoBruto());
		duaCrt.setpesoNeto(mic.getpesoNeto());
		duaCrt.setpesoNetoRestante(mic.getpesoNeto());
		duaCrt.setvalorMercaderia(mic.getImporte());
		duaCrt.setvalorMercaderiaRestante(mic.getImporte());
		duaCrt.setNumeroDna(mic.getNroMic());
		duaCrt.saveEx(get_TrxName());
		
		
		// Recorrinedo las continuaciones
		sql = "select uy_tr_miccont_id, uy_tr_crt_id, uy_tr_crt_id_1" +
				" from uy_tr_miccont" +
				" where uy_tr_mic_id = " + this.micId +
				" order by sheet asc";

		pstmt = DB.prepareStatement (sql, null);
		rs = pstmt.executeQuery ();

		while (rs.next()){
			MTRMicCont micCont = new MTRMicCont(getCtx(),  rs.getInt("uy_tr_miccont_id"), get_TrxName());

			if(rs.getInt("uy_tr_crt_id") > 0) {
				duaCrt = new MTRDuaCrt(getCtx(), 0, get_TrxName());
				
				duaCrt.setUY_TR_DuaMic_ID(this.duaMicId);
				duaCrt.setUY_TR_Mic_ID(this.micId);
				duaCrt.setUY_TR_Crt_ID(micCont.getUY_TR_Crt_ID());
				duaCrt.setdescripcion(micCont.getDescription());
				
				// Si no tiene tipo de bulto el crt en la continuación aviso y no cargo los crts
				if (micCont.getUY_TR_PackageType_ID() == 0){
					MTRCrt crtTmp = new MTRCrt(getCtx(), micCont.getUY_TR_Crt_ID(), get_TrxName());
					throw new AdempiereException(crtTmp.getNumero() + ": No tiene tipo de bulto asociado, necesario para la asociación del DUA");
				}
				
				duaCrt.setTipoBulto(micCont.getUY_TR_PackageType().getValue());
				duaCrt.setCantidadBultos(micCont.getQtyPackage());
				duaCrt.setCantidadBultosRestantes(micCont.getQtyPackage());
				duaCrt.setpesoBruto(micCont.getpesoBruto());
				duaCrt.setpesoBrutoRestante(micCont.getpesoBruto());
				duaCrt.setpesoNeto(micCont.getpesoNeto());
				duaCrt.setpesoNetoRestante(micCont.getpesoNeto());
				duaCrt.setvalorMercaderia(micCont.getImporte());
				duaCrt.setvalorMercaderiaRestante(micCont.getImporte());
				duaCrt.setNumeroDna(mic.getNroMic());
				
				duaCrt.saveEx(get_TrxName());
			} 
			if(rs.getInt("uy_tr_crt_id_1") > 0) {
				duaCrt = new MTRDuaCrt(getCtx(), 0, get_TrxName());
				
				duaCrt.setUY_TR_DuaMic_ID(this.duaMicId);
				duaCrt.setUY_TR_Mic_ID(this.micId);
				duaCrt.setUY_TR_Crt_ID(micCont.getUY_TR_Crt_ID_1());
				duaCrt.setdescripcion(micCont.getDescription());
				
				// Si no tiene tipo de bulto el crt en la continuación aviso y no cargo los crts
				if (micCont.getUY_TR_PackageType_ID_1() == 0){
					MTRCrt crtTmp = new MTRCrt(getCtx(), micCont.getUY_TR_Crt_ID_1(), get_TrxName());
					throw new AdempiereException(crtTmp.getNumero() + ": No tiene tipo de bulto asociado, necesario para la asociación del DUA");
				}
				MTRPackageType packageType = new MTRPackageType(getCtx(), micCont.getUY_TR_PackageType_ID_1(), get_TrxName());
				duaCrt.setTipoBulto(packageType.getValue());
				
				duaCrt.setCantidadBultos(micCont.getQtyPackage2());
				duaCrt.setCantidadBultosRestantes(micCont.getQtyPackage2());
				duaCrt.setpesoBruto(micCont.getpesoBruto2());
				duaCrt.setpesoBrutoRestante(micCont.getpesoBruto2());
				duaCrt.setpesoNeto(micCont.getpesoNeto2());
				duaCrt.setpesoNetoRestante(micCont.getpesoNeto2());
				duaCrt.setvalorMercaderia(micCont.getImporte2());
				duaCrt.setvalorMercaderiaRestante(micCont.getImporte2());
				duaCrt.setNumeroDna(mic.getNroMic());
				
				duaCrt.saveEx(get_TrxName());
			}
		}
		
		
		return "CRTs cargados";
	}
	
	// OpenUp. Raul Capecce. issue #3321 23/03/2015
	// Carga los CRTs del MIC agregado al DUA
	protected void loadCRTsFromMic(){
		
	}

}
