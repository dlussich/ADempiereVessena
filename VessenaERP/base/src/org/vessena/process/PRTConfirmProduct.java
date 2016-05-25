/**
 * 
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MRTConfirmProdScan;

/**
 * @author Nicolas
 *
 */
public class PRTConfirmProduct extends SvrProcess {
	
	private int scanID = 0;

	/**
	 * 
	 */
	public PRTConfirmProduct() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		this.scanID = this.getRecord_ID();

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
	
		String mensajeRetorno = "Confirmacion exitosa.";

		try{
			// Instancio modelo
			MRTConfirmProdScan model = new MRTConfirmProdScan(getCtx(), scanID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";

			model.confirmProduct();


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
