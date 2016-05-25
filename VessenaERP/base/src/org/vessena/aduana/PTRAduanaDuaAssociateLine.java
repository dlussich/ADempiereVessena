package org.openup.aduana;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.aduana.ConsultaRespuestaMic.CrtStatus;
import org.openup.model.MTRDua;
import org.openup.model.MTRDuaCrt;
import org.openup.model.MTRDuaLine;
import org.openup.model.MTRDuaLink;
import org.openup.model.MTRDuaMic;

public class PTRAduanaDuaAssociateLine extends SvrProcess {

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
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MTRDua dua;
		MTRDuaLink duaLink;
		MTRDuaLine duaLine;
		MTRDuaCrt duaCrt;
		MTRDuaMic duaMic;
		int duaLineId;
		int duaCrtId;
		BigDecimal cantidadBultos;
		BigDecimal pesoNeto;
		BigDecimal pesoBruto;
		BigDecimal valorMercaderia;
		BigDecimal cantidadTmp = null;
		
		
		
		
		
		// Revisar que solo haya un crt del mic selecionado
		
		sql = "SELECT COUNT(*) FROM UY_TR_DuaCrt WHERE isSelected = 'Y' AND UY_TR_DuaMic_ID = " + this.duaMicId;
		cantidadTmp = new BigDecimal( DB.getSQLValueStringEx(get_TrxName(), sql));
		if (cantidadTmp.floatValue() != 1) throw new AdempiereException("Debe seleccionar solamente un CRT del MIC para asociar");
		
		// Revisar que solo haya una linea del dua seleccionada
		
		sql = "SELECT COUNT(*) FROM UY_TR_DuaLine WHERE isSelected = 'Y' AND UY_TR_Dua_ID = " + this.duaId;
		cantidadTmp = new BigDecimal( DB.getSQLValueStringEx(get_TrxName(), sql));
		if (cantidadTmp.floatValue() != 1) throw new AdempiereException("Debe seleccionar solamente una linea del DUA para asociar");
		
		
		
		
		
		// Obtengo dua, duaLine, duaCrt y duaMic
		
		sql = "SELECT UY_TR_DuaCrt_ID FROM UY_TR_DuaCrt WHERE isSelected = 'Y' AND UY_TR_DuaMic_ID = " + this.duaMicId;
		duaCrtId = Integer.valueOf(DB.getSQLValue(get_TrxName(), sql));
		duaCrt = new MTRDuaCrt(getCtx(), duaCrtId, get_TrxName());
		sql = "SELECT UY_TR_DuaLine_ID FROM UY_TR_DuaLine WHERE isSelected = 'Y' AND UY_TR_Dua_ID = " + this.duaId;
		duaLineId = Integer.valueOf(DB.getSQLValue(get_TrxName(), sql));
		duaLine = new MTRDuaLine(getCtx(), duaLineId, get_TrxName());
		
		duaMic = new MTRDuaMic(getCtx(), duaCrt.getUY_TR_DuaMic_ID(), get_TrxName());
		dua = new MTRDua(getCtx(), duaLine.getUY_TR_Dua_ID(), get_TrxName());
		
		
		
		
		// Revisar que se haya ingresado una cantidad correcta de bultos a asociar
		try{
			sql = "SELECT valueNumber FROM UY_TR_DuaMic WHERE UY_TR_DuaMic_ID = " + this.duaMicId;
			cantidadBultos = new BigDecimal(DB.getSQLValueStringEx(get_TrxName(), sql));
		} catch (Exception ex){
			throw new AdempiereException("Debe ingresar la cantidad de bultos para asociar");
		}
		if (cantidadBultos.floatValue() == 0) throw new AdempiereException("Debe ingresar la cantidad de bultos para asociar");
		
		// Revisar que se haya ingresado una cantidad correcta de peso bruto a asociar
		try{
			sql = "SELECT pesoBruto FROM UY_TR_DuaMic WHERE UY_TR_DuaMic_ID = " + this.duaMicId;
			pesoBruto = new BigDecimal(DB.getSQLValueStringEx(get_TrxName(), sql));
		} catch (Exception ex){
			throw new AdempiereException("Debe ingresar un peso bruto válido");
		}
		if (pesoBruto.floatValue() == 0) throw new AdempiereException("Debe ingresar un peso bruto válido");
		
		// Revisar que se haya ingresado una cantidad correcta de peso neto a asociar
		try{
			sql = "SELECT pesoNeto FROM UY_TR_DuaMic WHERE UY_TR_DuaMic_ID = " + this.duaMicId;
			pesoNeto = new BigDecimal(DB.getSQLValueStringEx(get_TrxName(), sql));
		} catch (Exception ex){
			throw new AdempiereException("Debe ingresar un peso neto válido");
		}
		if (pesoNeto.floatValue() == 0) throw new AdempiereException("Debe ingresar un peso neto válido");

		// Revisar que se haya ingresado un valor de mercadería correcto a asociar
		try{
			sql = "SELECT valorMercaderia FROM UY_TR_DuaMic WHERE UY_TR_DuaMic_ID = " + this.duaMicId;
			valorMercaderia = new BigDecimal(DB.getSQLValueStringEx(get_TrxName(), sql));
		} catch (Exception ex){
			throw new AdempiereException("Debe ingresar un valor de mercadería correcto");
		}
		if (valorMercaderia.floatValue() == 0) throw new AdempiereException("Debe ingresar un valor de mercadería correcto");
		

		
		
		
		// Reviso si la linea de asocacion para este dua-mic ya existe
		sql = "SELECT COUNT(*) FROM UY_TR_DuaLink"
				+ " WHERE UY_TR_Dua_ID = " + dua.getUY_TR_Dua_ID()
				+ " AND UY_TR_Crt_ID = " + duaCrt.getUY_TR_Crt_ID();
		
		if (Integer.valueOf(DB.getSQLValueStringEx(get_TrxName(), sql)) == 0){
			// Nuevo dua
			duaLink = new MTRDuaLink(getCtx(), 0, get_TrxName());
			duaLink.setStatusAsociation(MTRDuaLink.STATUSASOCIATION_ENALTA);
		} else {
			// Actualizo dua existente
			sql = "SELECT UY_TR_DuaLink_ID FROM UY_TR_DuaLink"
					+ " WHERE UY_TR_Dua_ID = " + dua.getUY_TR_Dua_ID()
					+ " AND UY_TR_Crt_ID = " + duaCrt.getUY_TR_Crt_ID();
			duaLink = new MTRDuaLink(getCtx(), Integer.valueOf(DB.getSQLValueStringEx(get_TrxName(), sql)), get_TrxName());
			
			
			
			
			
			// Seteo los estados para dar de alta desde el proceso que envia el Mic al sistema Lucia
			switch(CrtStatus.valueOf(duaLink.getStatusAsociation())){
			case ENALTA:
			case DESVINCULADO:
				duaLink.setStatusAsociation(MTRDuaLink.STATUSASOCIATION_ENALTA);
				break;
			case VINCULADO:
			case ENBAJA:
			case ENMODIFICACION:
				duaLink.setStatusAsociation(MTRDuaLink.STATUSASOCIATION_ENMODIFICACION);
				break;
			}
			
			/* 
			 * Como estoy editando una asociación que ya esta creada,
			 * tengo que deshacerla (dentro de la transacion) para dejar el duaLine y el duaCrt
			 * con la cantidad de bultos sin esta linea para poder contabilizar
			 * los bultos y las otras cantidades correctamente
			 */
			
			duaLine.setCantidadBultosRestantes(duaLine.getCantidadBultosRestantes().add(duaLink.getCantidadBultosAsociacion()));
			duaLine.setpesoBrutoRestante(duaLine.getpesoBrutoRestante().add(duaLink.getPesoBrutoAsociacion()));
			duaLine.setpesoNetoRestante(duaLine.getpesoNetoRestante().add(duaLink.getPesoNetoAsociacion()));
			dua.setvalorMercaderiaRestante(dua.getvalorMercaderiaRestante().add(duaLink.getValorMercaderiaAsociacion()));
			
			duaCrt.setCantidadBultosRestantes(duaCrt.getCantidadBultosRestantes().add(duaLink.getCantidadBultosAsociacion()));
			duaCrt.setpesoBrutoRestante(duaCrt.getpesoBrutoRestante().add(duaLink.getPesoBrutoAsociacion()));
			duaCrt.setpesoNetoRestante(duaCrt.getpesoNetoRestante().add(duaLink.getPesoNetoAsociacion()));
			duaCrt.setvalorMercaderiaRestante(duaCrt.getvalorMercaderiaRestante().add(duaLink.getValorMercaderiaAsociacion()));
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// Revisar que las canitdades ingresadas en la pestaña del mic no sobrepasen los valores del dua y del crt
		
		if (cantidadBultos.compareTo(duaLine.getCantidadBultosRestantes()) > 0) throw new AdempiereException("La cantidad de bultos ingresada supera la cantidad de bultos restante de la linea del dua");
		if (cantidadBultos.compareTo(duaCrt.getCantidadBultosRestantes()) > 0) throw new AdempiereException("La cantidad de bultos ingresada supera la cantidad de bultos restantes del crt");
		if (pesoBruto.compareTo(duaLine.getpesoBrutoRestante()) > 0) throw new AdempiereException("El peso bruto ingresado supera el peso bruto restante de la linea del dua");
		if (pesoBruto.compareTo(duaCrt.getpesoBrutoRestante()) > 0) throw new AdempiereException("El peso bruto ingresardo supera el peso bruto restante del crt");
		if (pesoNeto.compareTo(duaLine.getpesoNetoRestante()) > 0) throw new AdempiereException("El peso neto ingresado supera el peso neto restante de la linea del dua");
		if (pesoNeto.compareTo(duaCrt.getpesoNetoRestante()) > 0) throw new AdempiereException("El peso neto ingresado supera el peso neto restante del crt");
		if (valorMercaderia.compareTo(dua.getvalorMercaderiaRestante()) > 0) throw new AdempiereException("El valor de la mercadería ingreada supera el valor de mercadería restante del dua");
		if (valorMercaderia.compareTo(duaCrt.getvalorMercaderiaRestante()) > 0) throw new AdempiereException("El valor de la mercadería ingresada supera el valor de mercadería restante del crt");
		if (!duaCrt.getTipoBulto().trim().equalsIgnoreCase(duaLine.getTipoBulto().trim())) throw new AdempiereException("Los tipos de bultos de la linea y del crt no coinciden");
		
		
		
		
		// Resto bultos de la asociacion a las lineas del dua

		cantidadTmp = duaLine.getCantidadBultosRestantes().subtract(cantidadBultos);
		duaLine.setCantidadBultosRestantes(cantidadTmp);
		
		cantidadTmp = duaLine.getpesoNetoRestante().subtract(pesoNeto);
		duaLine.setpesoNetoRestante(cantidadTmp);
		
		cantidadTmp = duaLine.getpesoBrutoRestante().subtract(pesoBruto);
		duaLine.setpesoBrutoRestante(cantidadTmp);
		
		cantidadTmp = dua.getvalorMercaderiaRestante().subtract(valorMercaderia);
		dua.setvalorMercaderiaRestante(cantidadTmp);
		
		
		
		
		
		// Resto bultos de la asociacion al crt
		
		cantidadTmp = duaCrt.getCantidadBultosRestantes().subtract(cantidadBultos);
		duaCrt.setCantidadBultosRestantes(cantidadTmp);
		
		cantidadTmp = duaCrt.getpesoBrutoRestante().subtract(pesoBruto);
		duaCrt.setpesoBrutoRestante(cantidadTmp);
		
		cantidadTmp = duaCrt.getpesoNetoRestante().subtract(pesoNeto);
		duaCrt.setpesoNetoRestante(cantidadTmp);
		
		cantidadTmp = duaCrt.getvalorMercaderiaRestante().subtract(valorMercaderia);
		duaCrt.setvalorMercaderiaRestante(cantidadTmp);
		
		
		
		
		
		// Desmarco la linea del dua y el crt que estaban marcados para asociar
		duaCrt.setIsSelected(false);
		duaLine.setIsSelected(false);
		
		
		
		
		
		
		// Setear la linea de DuaLink
		duaLink.setUY_TR_Dua_ID(duaLine.getUY_TR_Dua().getUY_TR_Dua_ID());
		duaLink.setUY_TR_DuaCrt_ID(duaCrt.getUY_TR_DuaCrt_ID());
		duaLink.setUY_TR_DuaLine_ID(duaLine.getUY_TR_DuaLine_ID());
		duaLink.setUY_TR_Crt_ID(duaCrt.getUY_TR_Crt_ID());
		//duaLink.setnumeromicdna(new BigDecimal(duaMic.getUY_TR_Mic().getNroDNA())); Por si no tiene numero de dna
		duaLink.setNumeroSerieItemDua(duaLine.getNumeroSerie());
		duaLink.setCantidadBultosAsociacion(cantidadBultos);
		duaLink.setPesoBrutoAsociacion(pesoBruto);
		duaLink.setPesoNetoAsociacion(pesoNeto);
		duaLink.setValorMercaderiaAsociacion(valorMercaderia);
		duaLink.settipobulto(duaCrt.getTipoBulto());
		duaLink.setdescripcion(duaLine.getdescripcion() + " - " + duaLine.getdescripcion2() + " - " + duaLine.getdescripcion3() + " - " + duaLine.getdescripcion4() + " - " + duaLine.getdescripcion5());
		duaLink.saveEx(get_TrxName());
		
//		// Seteo en el CRT que ya está asociado
//		duaCrt.setIsSelected(true);
		
		
		
		// Guardo todas las lineas
		duaCrt.saveEx(get_TrxName());
		duaLine.saveEx(get_TrxName());
		duaMic.saveEx(get_TrxName());
		duaLink.saveEx(get_TrxName());
		dua.saveEx(get_TrxName());
		
		
		return "OK";
	}

}
