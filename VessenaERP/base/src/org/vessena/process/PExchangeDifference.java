/**
 * PExchangeDifference.java
 * 12/04/2011
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MExchangeDiffHdr;

/**
 * OpenUp.
 * PExchangeDifference
 * Descripcion : Proceso de Diferencia de Cambio
 * @author Gabriel Vila
 * Fecha : 12/04/2011
 */
public class PExchangeDifference extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PExchangeDifference() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MExchangeDiffHdr model = new MExchangeDiffHdr(getCtx(), this.recordID, get_TrxName());

			if (model.get_ID() <= 0) return "No se pudo obtener modelo.";
			
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
