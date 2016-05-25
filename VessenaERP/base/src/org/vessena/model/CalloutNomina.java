package org.openup.model;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MRemuneration;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MHRConcept;

public class CalloutNomina extends CalloutEngine {
	
	/**
	 * Metodo que setea el nro de tarjeta del empleado seleccionado (para ingresos manuales)
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 14/06/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	
	public String setNroTarjeta(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		try{
			
			if (value==null) return "";
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_UY_ClockInterface_Detail.COLUMNNAME_UY_ClockInterface_Detail_ID)) return "";
			if (mTab.getValue(X_UY_ClockInterface_Detail.COLUMNNAME_C_BPartner_ID)==null) return "";

			String sql = "SELECT nrotarjeta FROM c_bpartner WHERE c_bpartner_id=" + (Integer)mTab.getValue("C_BPartner_ID");
			String nro = DB.getSQLValueStringEx(null, sql);

			mTab.setValue("nrolegajo",nro);
						
			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo cargar Nº de tarjeta del empleado.", e);
			return e.getMessage();
		}		
			
	}
	
	/**
	 * Metodo que setea el campo de importe en las lineas del cuadro de codigos.
	 * OpenUp Ltda. Issue #1754
	 * @author Nicolas Sarlabos - 07/01/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	
	public String setAmountCuadroLine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		if (value==null) return "";

		// Obtengo ID del concepto actual
		int conceptID = (Integer)value;
		if (conceptID <= 0) return "";

		String sql = "";
		BigDecimal amt = Env.ZERO;

		MHRConcept concept = new MHRConcept(ctx, conceptID, null); //instancio el concepto

		if(concept.isViaticoNacional()){

			sql = "SELECT coalesce(viaticonacional,0) FROM uy_hrparametros";
			amt = DB.getSQLValueBDEx(null, sql);			

			mTab.setValue("amount",amt);

		} else if(concept.isViaticoExtranjero()){

			sql = "SELECT coalesce(viaticoextranjero,0) FROM uy_hrparametros";
			amt = DB.getSQLValueBDEx(null, sql);			

			mTab.setValue("amount",amt);

		} else {

			sql = "SELECT coalesce(importejornal,0) FROM uy_hrparametros";
			amt = DB.getSQLValueBDEx(null, sql);			

			mTab.setValue("amount",amt);				
		}

		return "";

	}
	
	/**
	 * Metodo que setea el importe de jornal parametrizado al seleccionar el tipo
	 * de remuneracion "Jornalero"
	 * OpenUp Ltda. Issue #1759
	 * @author Nicolas Sarlabos - 21/01/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	
	public String setAmtJornal(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		if (value==null) return "";

		// Obtengo ID de tipo de remuneracion actual
		int remID = (Integer)value;
		if (remID <= 0) return "";
		
		MRemuneration rem = MRemuneration.forName(ctx, "Jornalero", null);
		
		if(rem!=null){
			
			if(rem.get_ID()==remID){
				
				String sql = "SELECT coalesce(importejornal,0) FROM uy_hrparametros WHERE ad_client_id = " + 
						Env.getAD_Client_ID(ctx) + " and ad_org_id = " + Env.getAD_Org_ID(ctx);

				BigDecimal amount = DB.getSQLValueBDEx(null, sql);

				mTab.setValue("amount",amount);				
				
			}			
		}

		return "";

	}

}
