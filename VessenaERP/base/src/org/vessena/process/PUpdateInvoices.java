package org.openup.process;

import org.compiere.process.SvrProcess;
import java.util.logging.Level;


import org.openup.model.MUpdInvoiceFilter;

/**
 * OpenUp. issue #735
 * PUpdateOrders
 * Descripcion : Proceso que aprueba y completa facturas y notas de credito por diferencia y apoyo.
 * @author Nicolas Sarlabos
 * Fecha : 01/07/2011
 */
public class PUpdateInvoices extends SvrProcess{

	
private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PUpdateInvoices() {
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
			MUpdInvoiceFilter model = new MUpdInvoiceFilter(getCtx(), this.recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para este proceso.";
			
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
