package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MHRParametros extends X_UY_HRParametros {

	private static final long serialVersionUID = 1L;

	public MHRParametros(Properties ctx, int UY_HRParametros_ID, String trxName) {
		super(ctx, UY_HRParametros_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MHRParametros(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String sql = "";

		//se impide ingresar mas de 1 registro para la misma empresa
		if (newRecord) {

			sql = "SELECT count(ad_client_id) FROM uy_hrparametros WHERE ad_client_id=" + this.getAD_Client_ID();
			int res = DB.getSQLValueEx(null, sql);

			if (res > 0) throw new AdempiereException("Ya existe registro para ésta empresa");

		}

		//se validan porcentajes e importes
		if (this.getporcjubpersonal().compareTo(Env.ZERO) <= 0 || this.getporcjubpersonal().compareTo(Env.ONEHUNDRED) > 0)
			throw new AdempiereException("% Aporte Jub. Personal debe ser entre 1 y 100");
		if (this.getporcjubpatronal().compareTo(Env.ZERO) <= 0 || this.getporcjubpatronal().compareTo(Env.ONEHUNDRED) > 0)
			throw new AdempiereException("% Aporte Jub. Patronal debe ser entre 1 y 100");
		if (this.getporcfonpatronal().compareTo(Env.ZERO) <= 0 || this.getporcfonpatronal().compareTo(Env.ONEHUNDRED) > 0)
			throw new AdempiereException("% FONASA Patronal debe ser entre 1 y 100");
		if (this.getminapfonasa().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Mínimo Aporte Adicional FONASA debe ser mayor a 0");
		if (this.getporcfrlpersonal().compareTo(Env.ZERO) <= 0 || this.getporcfrlpersonal().compareTo(Env.ONEHUNDRED) > 0)
			throw new AdempiereException("% FRL Personal debe ser entre 1 y 100");
		if (this.getporcfrlpatronal().compareTo(Env.ZERO) <= 0 || this.getporcfrlpatronal().compareTo(Env.ONEHUNDRED) > 0)
			throw new AdempiereException("% FRL Patronal debe ser entre 1 y 100");
		if (this.getsalariomin().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Salario Mínimo Nacional debe ser mayor a 0");
		if (this.gettopebps().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Tope Aportación BPS debe ser mayor a 0");
		if (this.getamtcuotamutual().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Cuota Mutual debe ser mayor a 0");
		if (this.getporcminliquido().compareTo(Env.ZERO) <= 0) throw new AdempiereException("% Mínimo Líquido A Cobrar debe ser mayor a 0");
		if (this.getcoefhoraextra().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Coeficiente Multiplicador Hora Extra debe ser mayor a 0");
		if (this.getporcinchoranocturna().compareTo(Env.ZERO) <= 0) throw new AdempiereException("% Incremento Hora Nocturna debe ser mayor a 0"); 
		if (this.getcoeffaltajustificada().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Coeficiente Falta Justificada debe ser mayor a 0");
		
		if(is_ValueChanged("ImporteJornal")){
			
			//refresco importe de jornal en remuneraciones de empleados CHOFERES jornaleros
			sql = "update uy_bpremuneracion set amount = " + this.getImporteJornal() +
                  " where isactive = 'Y' and uy_bpremuneracion_id in (select rem.uy_bpremuneracion_id" +
                  " from c_bpartner bp" +
                  " inner join uy_bpremuneracion rem on bp.c_bpartner_id = rem.c_bpartner_id" +
                  " inner join c_remuneration crem on rem.c_remuneration_id = crem.c_remuneration_id" + 
                  " inner join uy_hrremunerationgroup rg on crem.uy_hrremunerationgroup_id = rg.uy_hrremunerationgroup_id" +
                  " where bp.uy_tr_driver_id > 0 and bp.isactive = 'Y' and rg.value = 'Jornalero')";
			
			DB.executeUpdateEx(sql, get_TrxName());
			
			//refresco importe de jornal en conceptos de cuadro de codigos
			sql = "update uy_hrcuadrocodigoline set amount = " + this.getImporteJornal() + ", totalamt = factor * " + this.getImporteJornal() +
                  " where uy_hrcuadrocodigoline_id in (select uy_hrcuadrocodigoline_id" + 
                  " from uy_hrcuadrocodigoline l" +
                  " inner join hr_concept con on l.hr_concept_id = con.hr_concept_id" +
                  " where con.isupdatepay = 'Y')";
			
			DB.executeUpdateEx(sql, get_TrxName());		
			
		}
		
		if(is_ValueChanged("ViaticoNacional")){
			
			//refresco importe de Viatico Nacional en conceptos de cuadro de codigos
			sql = "update uy_hrcuadrocodigoline set amount = " + this.getViaticoNacional() + ", totalamt = factor * " + this.getViaticoNacional() +
                  " where uy_hrcuadrocodigoline_id in (select uy_hrcuadrocodigoline_id" + 
                  " from uy_hrcuadrocodigoline l" +
                  " inner join hr_concept con on l.hr_concept_id = con.hr_concept_id" +
                  " where con.isviaticonacional = 'Y')";
			
			DB.executeUpdateEx(sql, get_TrxName());			
			
		}
		
		if(is_ValueChanged("ViaticoExtranjero")){
			
			//refresco importe de Viatico Extranjero en conceptos de cuadro de codigos
			sql = "update uy_hrcuadrocodigoline set amount = " + this.getViaticoExtranjero() + ", totalamt = factor * " + this.getViaticoExtranjero() +
                  " where uy_hrcuadrocodigoline_id in (select uy_hrcuadrocodigoline_id" + 
                  " from uy_hrcuadrocodigoline l" +
                  " inner join hr_concept con on l.hr_concept_id = con.hr_concept_id" +
                  " where con.isviaticoextranjero = 'Y')";
			
			DB.executeUpdateEx(sql, get_TrxName());				
			
		}

		return true;
	}
	
	
	/**
	 * Metodo que devuelve el MHRParametros para la empresa recibida por parametro
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 15/05/2012
	 * @see
	 * @param partnerID
	 * @return
	 */	
	public static MHRParametros forClient(int clientID, String trxName) {
		
		MHRParametros p = null;
		
		String sql = "SELECT uy_hrparametros_id FROM uy_hrparametros WHERE ad_client_id=" + clientID;
		
		int paramID = DB.getSQLValueEx(trxName, sql);
		
		if(paramID > 0) p = new MHRParametros(Env.getCtx(),paramID,trxName);
		
		return p;
	
	}

}
