/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 28/05/2013
 */
package org.openup.process;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MUser;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MRPtoResolucion;
import org.openup.model.MRReclamo;
import org.openup.model.MRTracking;
import org.openup.model.X_UY_R_Reclamo;

/**
 * org.openup.process - PGestionarIncidencia
 * OpenUp Ltda. Issue #285 
 * Description: Proceso que se ejecuta cuando el usuario decide gestionar un incidente.
 * Marca este incidente como asignado.
 * @author Gabriel Vila - 28/05/2013
 * @see
 */
public class PGestionarIncidencia extends SvrProcess {

	MRReclamo reclamo = null;
	
	/**
	 * Constructor.
	 */
	public PGestionarIncidencia() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 28/05/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		try{
			this.reclamo = new MRReclamo(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 28/05/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		// Debo verificar que este usuario tenga permisos para gestionar esta incidencia.
		MRPtoResolucion ptoResol = (MRPtoResolucion)reclamo.getUY_R_PtoResolucion();

		// Solo pueden gestionar esta incidencia, aquellos usuarios del punto de resolucion.
		if (!ptoResol.validUser(this.getAD_User_ID())){
			throw new AdempiereException("Usted no tiene permisos para Gestionar este Incidencia." + "\n" +
										 "No pertenece al Punto de Resolucion.");
		}
		
		// Si esta incidencia esta siendo gestionada por otro usuario. pregunto si
		// desea tomarla.
		if ((reclamo.getAssignTo_ID() > 0) && (reclamo.getAssignTo_ID() != this.getAD_User_ID())){
			MUser user = new MUser(getCtx(), reclamo.getAssignTo_ID(), null);
			boolean ok = ADialog.ask(-1, null, "Gestion de Incidencia", "Esta incidencia esta siendo Gestionada por el usuario : " + user.getDescription() + "\n" +
						 "Desea Gestionarla Usted ?");
			if (!ok){
				return "OK";
			}
		}
		
		this.reclamo.setAssignTo_ID(this.getAD_User_ID());
		
		if (this.reclamo.getAssignDateFrom() == null){
			this.reclamo.setAssignDateFrom(new Timestamp (System.currentTimeMillis()));	
		}
		
		this.reclamo.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_EnGestion);
		this.reclamo.saveEx();

		// Nuevo Tracking
		MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(this.getAD_User_ID());
		track.setDescription("Inicio de Gestion");
		track.setUY_R_Reclamo_ID(reclamo.get_ID());		
		track.saveEx();

		String action = "";
		
		// Actualizo gestiones anteriores de esta incidencia como terminadas.
		action = " update uy_r_gestion set isover ='Y' " + 
				 " where uy_r_reclamo_id = " + reclamo.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// Actualizo bandeja de entrada
		action = " update uy_r_inbox set assignto_id =" + this.getAD_User_ID() + "," +
						" dateassign = '" + reclamo.getAssignDateFrom() + "'," +
						" statusreclamo ='" + reclamo.getStatusReclamo() + "', " +
						" statustarea ='CUR' " +
						" where uy_r_reclamo_id = " + reclamo.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		return "OK";
	}

}
