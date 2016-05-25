/**
 * PAsignaTransporte.java
 * 02/01/2011
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MAsignaTransporteHdr;

/**
 * OpenUp.
 * PAsignaTransporte
 * Descripcion : Proceso para Asignacion de Transportista.
 * @author Gabriel Vila
 * Fecha : 02/01/2011
 */
public class PAsignaTransporte extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PAsignaTransporte() {
		// TODO Auto-generated constructor stub
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
			MAsignaTransporteHdr model = new MAsignaTransporteHdr(getCtx(), this.recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para Asignacion de Transporte.";
			
			// Si no puedo salgo con el texto del error.
			if(!model.processIt(model.getDocAction())){
				mensajeRetorno = model.getProcessMsg();
				throw new Exception(model.getProcessMsg());
			}

			// Salvo 
			model.saveEx();
		
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
