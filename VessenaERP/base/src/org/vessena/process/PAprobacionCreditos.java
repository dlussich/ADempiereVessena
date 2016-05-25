/**
 * PAprobacionCreditos.java
 * 08/02/2011
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MCreditFilter;

/**
 * OpenUp.
 * PAprobacionCreditos
 * Descripcion : Proceso de Aprobacion Crediticia. Procesa Filtros y luego aplica
 * aprobacion manual indicada por usuario.
 * @author Gabriel Vila
 * Fecha : 08/02/2011
 */
public class PAprobacionCreditos extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PAprobacionCreditos() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MCreditFilter model = new MCreditFilter(getCtx(), this.recordID, get_TrxName());
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

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Guardo ID del registro a considerar
		recordID = getRecord_ID();
	}

}
