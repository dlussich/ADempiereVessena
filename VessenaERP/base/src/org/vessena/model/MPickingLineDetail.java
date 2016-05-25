/**
 * MPickingLineDetail.java
 * 23/02/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MPickingLineDetail
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 23/02/2011
 */
public class MPickingLineDetail extends X_UY_PickingLineDetail {

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// No se puede modificar lineas que sean bonificacion de otra linea
		if (this.isUY_EsBonificCruzada() && !newRecord) {
			throw new AdempiereException("No se puede modificar ya que esta linea - linea fue creado para cumplir una Bonificacion Cruzada");
		}

		// No se puede modificar lineas que sean padre de una bonificacion
		if (this.isUY_TieneBonificCruzada() && !newRecord) {
			throw new AdempiereException("No se puede modificar ya que esta linea - linea tiene vinculada Bonificacion Cruzada");
		}

		// No se puede modificar lineas que tengan bonificacion simple
		if (!newRecord && this.getuy_bonificaregla().compareTo(Env.ZERO) != 0) {
			//throw new AdempiereException("No se puede modificar ya que esta linea tiene vinculada una Bonificacion Simple");
		}

		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7678685133610258508L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_PickingLineDetail_ID
	 * @param trxName
	 */
	public MPickingLineDetail(Properties ctx, int UY_PickingLineDetail_ID,
			String trxName) {
		super(ctx, UY_PickingLineDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPickingLineDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
