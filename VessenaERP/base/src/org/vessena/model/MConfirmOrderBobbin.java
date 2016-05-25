/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MConfirmOrderBobbin extends X_UY_ConfirmOrderBobbin {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1273725449337884796L;

	public MConfirmOrderBobbin(Properties ctx, int UY_ConfirmOrderBobbin_ID, String trxName) {
		super(ctx, UY_ConfirmOrderBobbin_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MConfirmOrderBobbin(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(!newRecord){
			
			if(this.getWeight().compareTo(Env.ZERO)<=0) throw new AdempiereException("El peso debe ser mayor a cero");			
		}
		
		if(is_ValueChanged("numero")) this.verifyTrackingNo();
		
		return true;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 07/01/2016. Issue #5281.
	 * Verifica el numero de bobina ingresado en la confirmacion.
	 */
	private void verifyTrackingNo() {
	
		/*String sql = "select lb.uy_inoutlabel_id" +
				" from uy_inoutlabel lb" +
				" inner join m_inoutline ol on lb.m_inoutline_id = ol.m_inoutline_id" +
				" inner join m_inout hdr on ol.m_inout_id = hdr.m_inout_id" +
				" where hdr.docstatus = 'CO' and ol.m_product_id = " + this.getM_Product_ID() +
				" and trim(lb.numero) = trim('" + this.getnumero() + "')";*/
		
		String sql = "select uy_inoutlabel_id" +
				" from uy_inoutlabel" +
				" where m_product_id = " + this.getM_Product_ID() +
				" and trim(numero) = trim('" + this.getnumero() + "')";

		int lineID = DB.getSQLValueEx(get_TrxName(), sql);

		if(lineID <= 0) throw new AdempiereException("El numero de bobina ingresado no existe");	
		
	}

	public static BigDecimal totalWeightInput (Properties ctx, int confHdrID, String trxName) {
		
		BigDecimal value = Env.ZERO;
		
		String sql = "select coalesce(sum(weight),0)" +
		             " from uy_confirmorderbobbin" +
				     " where uy_confirmorderhdr_id = " + confHdrID;
		
		value = DB.getSQLValueBDEx(trxName, sql);		
		
		return value;
		
	}
	
	public static BigDecimal totalWeightReturn (Properties ctx, int confHdrID, String trxName) {
		
		BigDecimal value = Env.ZERO;
		
		String sql = "select coalesce(sum(uy_devolucion),0)" +
		             " from uy_confirmorderbobbin" +
				     " where uy_confirmorderhdr_id = " + confHdrID;
		
		value = DB.getSQLValueBDEx(trxName, sql);		
		
		return value;
		
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 12/11/2015. Issue #4125.
	 * Devuelve el total de Kg de desperdicio en confirmacion de orden de corte.
	 */
	public static BigDecimal totalWeightLost(Properties ctx, int confHdrID, String trxName){
		
		BigDecimal value = Env.ZERO;
		
		String sql = "select coalesce(sum(uy_desperdicio),0)" +
		             " from uy_confirmorderbobbin" +
				     " where uy_confirmorderhdr_id = " + confHdrID;
		
		value = DB.getSQLValueBDEx(trxName, sql);
		
		return value;			
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 13/11/2015. Issue #4125.
	 * Devuelve el total de Kg de desperdicio en confirmacion de orden de corte.
	 */
	public static BigDecimal totalWeightRefile(Properties ctx, int confHdrID, String trxName){
		
		BigDecimal value = Env.ZERO;
		
		String sql = "select coalesce(sum(uy_refileydescarne),0)" +
		             " from uy_confirmorderbobbin" +
				     " where uy_confirmorderhdr_id = " + confHdrID;
		
		value = DB.getSQLValueBDEx(trxName, sql);
		
		return value;			
	}

}
