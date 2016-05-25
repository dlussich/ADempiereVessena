package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.openup.aduana.ConsultaRespuestaMic.CrtStatus;

public class MTRDuaLink extends X_UY_TR_DuaLink{

	public MTRDuaLink(Properties ctx, int UY_TR_DuaLink_ID, String trxName) {
		super(ctx, UY_TR_DuaLink_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MTRDuaLink (Properties ctx, ResultSet rs, String trxName){
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeDelete() {
		super.beforeDelete();
		
		
		// Si esta vinculado con el sistemaLucia, no se puede eliminar la asociación
		if (
				this.getStatusAsociation().equalsIgnoreCase(CrtStatus.VINCULADO.toString()) ||
				this.getStatusAsociation().equalsIgnoreCase(CrtStatus.ENBAJA.toString()) ||
				this.getStatusAsociation().equalsIgnoreCase(CrtStatus.ENMODIFICACION.toString())
				){
			throw new AdempiereException("No se puede eliminar asociación estando vinculada en el sistema Lucia");
		}
		
		
		
		
		// Resto las cantidades para que se puedan utilizar en el dua para otras asociaciones
		
		MTRDua dua = new MTRDua(getCtx(), this.getUY_TR_Dua_ID(), get_TrxName());
		MTRDuaCrt duaCrt = new MTRDuaCrt(getCtx(), this.getUY_TR_DuaCrt_ID(), get_TrxName());
		MTRDuaMic duaMic = new MTRDuaMic(getCtx(), duaCrt.getUY_TR_DuaMic_ID(), get_TrxName());
		MTRDuaLine duaLine = new MTRDuaLine(getCtx(), this.getUY_TR_DuaLine_ID(), get_TrxName());
		
		dua.setvalorMercaderiaRestante(dua.getvalorMercaderiaRestante().add(this.getValorMercaderiaAsociacion()));
		duaLine.setCantidadBultosRestantes(duaLine.getCantidadBultosRestantes().add(this.getCantidadBultosAsociacion()));
		duaLine.setpesoBrutoRestante(duaLine.getpesoBrutoRestante().add(this.getPesoBrutoAsociacion()));
		duaLine.setpesoNetoRestante(duaLine.getpesoNetoRestante().add(this.getPesoNetoAsociacion()));
		
		duaCrt.setvalorMercaderiaRestante(duaCrt.getvalorMercaderiaRestante().add(this.getValorMercaderiaAsociacion()));
		duaCrt.setCantidadBultosRestantes(duaCrt.getCantidadBultosRestantes().add(this.getCantidadBultosAsociacion()));
		duaCrt.setpesoBrutoRestante(duaCrt.getpesoBrutoRestante().add(this.getPesoBrutoAsociacion()));
		duaCrt.setpesoNetoRestante(duaCrt.getpesoNetoRestante().add(this.getPesoNetoAsociacion()));
		
		dua.saveEx(get_TrxName());
		duaCrt.saveEx(get_TrxName());
		duaMic.saveEx(get_TrxName());
		duaLine.saveEx(get_TrxName());
		
		
		
		
		return true;
	}
	

	
}
