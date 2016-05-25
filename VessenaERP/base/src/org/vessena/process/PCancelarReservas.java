package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MCancelReservationFilter;

/**
 * OpenUp. issue #908
 * PUpdateOrders
 * Descripcion : Proceso que cancela reservas masivamente .
 * @author Nicolas Sarlabos
 * Fecha : 06/12/2011
 */
public class PCancelarReservas extends SvrProcess{
	
	private int recordID = 0;

	@Override
	protected String doIt() throws Exception {
		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MCancelReservationFilter model = new MCancelReservationFilter(getCtx(), this.recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para filtros ingresados.";
			
			// Si no puedo salgo con el texto del error.
			if(!model.processIt(model.getDocAction())){
				mensajeRetorno = model.getProcessMsg();
				throw new Exception(model.getProcessMsg());
			}

			// Confirmo cambios en DB
			commit();

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			rollback();
			throw e;
		}
		return mensajeRetorno;
	}

	@Override
	protected void prepare() {
		// Guardo ID del registro a considerar
		recordID = getRecord_ID();
		
	}

}
