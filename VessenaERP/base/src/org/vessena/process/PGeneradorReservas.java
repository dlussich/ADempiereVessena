/**
 * PGeneradorReservas.java
 * 28/11/2010
 */
package org.openup.process;

import java.util.logging.Level;
import org.compiere.process.SvrProcess;
import org.openup.model.MReserveFilter;

/**
 * OpenUp.
 * PGeneradorReservas
 * Descripcion : Proceso de Generacion de Reservas. Parte de una tabla temporal donde se guardo una reserva en grilla.
 * Este proceso se encarga de generar las reservas en tablas finales.
 * @author Gabriel Vila
 * Fecha : 28/11/2010
 */
public class PGeneradorReservas extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PGeneradorReservas() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Guardo ID del registro a considerar
		recordID = getRecord_ID();
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MReserveFilter model = new MReserveFilter(getCtx(), this.recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo.";
			
			// OpenUp. Gabriel Vila. 05/10/2011. Issue #894.
			// Asocio ventana con modelo.
			model.setWindow(this.getProcessInfo().getWindow());
			// Fin Issue #894.
			
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


	
}
