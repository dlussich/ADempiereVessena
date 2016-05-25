/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MInOut;
import org.compiere.process.DocumentEngine;

/**
 * @author Kacike
 *
 */
public class MAsignaTransporteDevol extends X_UY_AsignaTransporteDevol {

	@Override
	protected boolean beforeDelete() {

		// Obtengo modelo de devolucion
		MInOut devol = new MInOut(getCtx(), this.getM_InOut_ID(), get_TrxName());
		if (devol != null){
			// DesAsigno devolucion con la asignacion.
			devol.setUY_AsignaTransporteHdr_ID(0);
			devol.setM_Shipper_ID(0);
			devol.setShipDate(null);
			devol.setDocStatus(DocumentEngine.STATUS_Requested);
			devol.setDocAction(DocumentEngine.ACTION_Asign);
			devol.saveEx();
		}
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6523373410761809253L;

	/**
	 * @param ctx
	 * @param UY_AsignaTransporteDevol_ID
	 * @param trxName
	 */
	public MAsignaTransporteDevol(Properties ctx, int UY_AsignaTransporteDevol_ID, String trxName) {
		super(ctx, UY_AsignaTransporteDevol_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAsignaTransporteDevol(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
