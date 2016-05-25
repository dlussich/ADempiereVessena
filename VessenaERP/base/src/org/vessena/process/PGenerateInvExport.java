/**
 * 
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;

/**
 * @author Nicolas
 *
 */
public class PGenerateInvExport extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * 
	 */
	public PGenerateInvExport() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		recordID = getRecord_ID();
		
	}

	@Override
	protected String doIt() throws Exception {
	
		String mensajeRetorno = "Factura de exportacion generada con exito.";

		try{
			// Instancio modelo
			MInvoice model = new MInvoice(getCtx(), recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";
				
			model.generateInvExport();
		
			// Confirmo cambios en DB
			commitEx();

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
