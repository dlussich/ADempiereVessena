/**
 * PCompleteConfirmaOrdenProd.java
 * 13/09/2010
 */
package org.openup.process;

import java.util.logging.Level;
import org.compiere.model.MPayment;
import org.compiere.process.SvrProcess;
import org.compiere.model.MConfirmorderhdr;

/**
 * OpenUp.
 * PCompleteConfirmaOrdenProd
 * Descripcion : Proceso que se disapara el completarse una Confirmacion de Orden de Produccion.
 * @author Gabriel Vila
 * Fecha : 13/09/2010
 */
public class PCompleteConfirmaOrdenProd extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PCompleteConfirmaOrdenProd() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Proceso finalizado con exito.";
		
		try{
			// Instancio modelo
			MConfirmorderhdr model = new MConfirmorderhdr(getCtx(), recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para proceso de factura masivo.";
			
			// Completo registro de este modelo. 
			// Si no puedo salgo con el texto del error.
			if(!model.processIt(MPayment.ACTION_Complete)){
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

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Guardo ID del registro a considerar
		recordID = getRecord_ID();
	}

}
