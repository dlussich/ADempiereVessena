/**
 * 
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MTRServiceOrder;

/**
 * @author Nicolas
 *
 */
public class PTRInvoiceFromSO extends SvrProcess{
	
	private String documentNo = "";

	/**
	 * 
	 */
	public PTRInvoiceFromSO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("DocumentNoAux")){
					this.documentNo = ((String)para[i].getParameter());
				}	
			}
		}		
		
	}

	@Override
	protected String doIt() throws Exception {

		String mensajeRetorno = "Factura proveedor generada con exito.";

		try{
			// Instancio modelo
			MTRServiceOrder model = new MTRServiceOrder(getCtx(), this.getRecord_ID(), get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";

			model.generateInvoice(documentNo);

			// Salvo 
			//model.saveEx();

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
