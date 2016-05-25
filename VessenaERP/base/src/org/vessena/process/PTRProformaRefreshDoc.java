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
public class PTRProformaRefreshDoc extends SvrProcess {
	
	private int invoiceID = 0;

	/**
	 * 
	 */
	public PTRProformaRefreshDoc() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		invoiceID = getRecord_ID();

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Operacion realizada con exito.";

		try{
			// Instancio modelo
			MInvoice model = new MInvoice(getCtx(), invoiceID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";

			//model.refreshFromProforma();

			// Confirmo cambios en DB
			//commitEx();

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


