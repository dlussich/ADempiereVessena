/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/04/2013
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MRHandlerAjuste
 * OpenUp Ltda. Issue #737 
 * Description: Modelo de solicitud de ajuste en gestion de incidencias.
 * @author Gabriel Vila - 18/04/2013
 * @see
 */
public class MRHandlerAjuste extends X_UY_R_HandlerAjuste {

	private static final long serialVersionUID = -6679094079019419723L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_HandlerAjuste_ID
	 * @param trxName
	 */
	public MRHandlerAjuste(Properties ctx, int UY_R_HandlerAjuste_ID,
			String trxName) {
		super(ctx, UY_R_HandlerAjuste_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRHandlerAjuste(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if (!newRecord && (is_ValueChanged(COLUMNNAME_IsConfirmed) || is_ValueChanged(COLUMNNAME_IsRejected))){
			return true;
		}
		
		// No puedo ingresar o modificar solicitudes de ajuste que son gestionadas por otro usuario
		MRGestion gestion = (MRGestion)this.getUY_R_Gestion();
		if (Env.getAD_User_ID(Env.getCtx()) != gestion.getReceptor_ID()){
			throw new AdempiereException("Usted no tiene permisos para Modificar Solicitudes de Ajuste gestionadas por otro Usuario.");
		}

		// Guardo importes en moneda nacional 
		MClient client = new MClient(Env.getCtx(), gestion.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		if (this.getC_Currency_ID() == schema.getC_Currency_ID()){
			this.setTaxAmtAcct(this.getTaxAmt());
			this.setLineNetAmtAcct(this.getLineNetAmt());
			this.setLineTotalAmtAcct(this.getLineTotalAmt());
			this.setCurrencyRate(Env.ONE);
		}
		else{
			Timestamp fechaRate = TimeUtil.trunc(gestion.getDateTrx(), TimeUtil.TRUNC_DAY);
			BigDecimal currencyRate = MConversionRate.getRate(this.getC_Currency_ID(), schema.getC_Currency_ID(), 
							fechaRate, 0, gestion.getAD_Client_ID(), 0).setScale(3, RoundingMode.HALF_UP);
			if (currencyRate == null){
				throw new AdempiereException("No se pudo obtener Tasa de Cambio para Moneda : " + this.getC_Currency_ID() + 
						                     ", Fecha : " + fechaRate);
			}
			this.setTaxAmtAcct(this.getTaxAmt().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
			this.setLineNetAmtAcct(this.getLineNetAmt().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
			this.setLineTotalAmtAcct(this.getLineNetAmtAcct().add(this.getTaxAmtAcct()));
			this.setCurrencyRate(currencyRate);
		}

		return true;
	}

	@Override
	protected boolean beforeDelete() {

		// No puedo eliminar solicitudes de ajuste ingresadas por otro usuario
		MRGestion gestion = (MRGestion)this.getUY_R_Gestion();
		if (Env.getAD_User_ID(Env.getCtx()) != gestion.getReceptor_ID()){
			throw new AdempiereException("Usted no tiene permisos para Eliminar Solicitudes de Ajuste gestionadas por otro Usuario.");
		}

		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success)
			return success;
		
		this.updateTotalesGestion();
		
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if (!success)
			return success;
		
		this.updateTotalesGestion();
		
		return true;
	}

	/***
	 * Actualiza totales de la gestion que contiene a esta solicitud de ajuste.
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 17/07/2013
	 * @see
	 */
	private void updateTotalesGestion() {

		try{

			String sql = " select coalesce(sum(hand.linetotalamtacct),0) as valor " +
					     " from uy_r_handlerajuste hand " +
					     " inner join uy_r_ajuste aju on hand.uy_r_ajuste_id = aju.uy_r_ajuste_id " +
					     " where hand. uy_r_gestion_id =" + this.getUY_R_Gestion_ID() +
					     " and aju.drcr='DB'";
			BigDecimal totalDebe = DB.getSQLValueBD(get_TrxName(), sql);
			
			sql = " select coalesce(sum(hand.linetotalamtacct),0) as valor " +
				  " from uy_r_handlerajuste hand " +
				  " inner join uy_r_ajuste aju on hand.uy_r_ajuste_id = aju.uy_r_ajuste_id " +
				  " where hand. uy_r_gestion_id =" + this.getUY_R_Gestion_ID() +
				  " and aju.drcr='CR'";
			BigDecimal totalHaber = DB.getSQLValueBD(get_TrxName(), sql);

			
			String action = " update uy_r_gestion " +
							" set totallines = (select coalesce(sum(linenetamtacct),0) from uy_r_handlerajuste where uy_r_gestion_id =" + this.getUY_R_Gestion_ID() + "), " +
							" grandtotal = (select coalesce(sum(linetotalamtacct),0) from uy_r_handlerajuste where uy_r_gestion_id =" + this.getUY_R_Gestion_ID() + "), " +
							" totalamt =" + totalDebe.subtract(totalHaber) +
							" where uy_r_gestion_id =" + this.getUY_R_Gestion_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	
}
