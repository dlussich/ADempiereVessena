/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/08/2013
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
 * org.openup.model - MRAjusteRequestLine
 * OpenUp Ltda. Issue #285 
 * Description: Modeo de linea de solicitud de ajuste
 * @author Gabriel Vila - 15/08/2013
 * @see
 */
public class MRAjusteRequestLine extends X_UY_R_AjusteRequestLine {

	private static final long serialVersionUID = 6349147344797788421L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_AjusteRequestLine_ID
	 * @param trxName
	 */
	public MRAjusteRequestLine(Properties ctx, int UY_R_AjusteRequestLine_ID,
			String trxName) {
		super(ctx, UY_R_AjusteRequestLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRAjusteRequestLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MRAjusteRequest request = (MRAjusteRequest)this.getUY_R_AjusteRequest();
		
		// Guardo importes en moneda nacional 
		MClient client = new MClient(Env.getCtx(), request.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		if (this.getC_Currency_ID() == schema.getC_Currency_ID()){
			this.setTaxAmtAcct(this.getTaxAmt());
			this.setLineNetAmtAcct(this.getLineNetAmt());
			this.setLineTotalAmtAcct(this.getLineTotalAmt());
			this.setCurrencyRate(Env.ONE);
		}
		else{
			Timestamp fechaRate = TimeUtil.trunc(request.getDateTrx(), TimeUtil.TRUNC_DAY);
			BigDecimal currencyRate = MConversionRate.getRate(this.getC_Currency_ID(), schema.getC_Currency_ID(), 
							fechaRate, 0, request.getAD_Client_ID(), 0).setScale(3, RoundingMode.HALF_UP);
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
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success)
			return success;
		
		this.updateTotalesRequest();
		
		return true;

	}


	@Override
	protected boolean afterDelete(boolean success) {
		
		if (!success)
			return success;
		
		this.updateTotalesRequest();
		
		return true;
	}

	/***
	 * Actualiza totales de la gestion que contiene a esta solicitud de ajuste.
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 17/07/2013
	 * @see
	 */
	private void updateTotalesRequest() {

		try{

			String sql = " select coalesce(sum(line.linetotalamtacct),0) as valor " +
					     " from uy_r_ajusterequestline line " +
					     " inner join uy_r_ajuste aju on line.uy_r_ajuste_id = aju.uy_r_ajuste_id " +
					     " where line.uy_r_ajusterequest_id =" + this.getUY_R_AjusteRequest_ID() +
					     " and aju.drcr='DB'";
			BigDecimal totalDebe = DB.getSQLValueBD(get_TrxName(), sql);
			
			sql = " select coalesce(sum(line.linetotalamtacct),0) as valor " +
				  " from uy_r_ajusterequestline line " +
				  " inner join uy_r_ajuste aju on line.uy_r_ajuste_id = aju.uy_r_ajuste_id " +
				  " where line.uy_r_ajusterequest_id =" + this.getUY_R_AjusteRequest_ID() +
				  " and aju.drcr='CR'";
			BigDecimal totalHaber = DB.getSQLValueBD(get_TrxName(), sql);

			
			String action = " update uy_r_ajusterequest " +
							" set totallines = (select coalesce(sum(linenetamtacct),0) from uy_r_ajusterequestline where uy_r_ajusterequest_id =" + this.getUY_R_AjusteRequest_ID() + "), " +
							" grandtotal = (select coalesce(sum(linetotalamtacct),0) from uy_r_ajusterequestline where uy_r_ajusterequest_id =" + this.getUY_R_AjusteRequest_ID() + "), " +
							" totalamt =" + totalDebe.subtract(totalHaber) +
							" where uy_r_ajusterequest_id =" + this.getUY_R_AjusteRequest_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	
}
