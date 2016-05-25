/**
 * PActionReservaPedido.java
 * 06/01/2011
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MReservaPedidoHdr;

/**
 * OpenUp.
 * PActionReservaPedido
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 06/01/2011
 */
public class PActionReservaPedido extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PActionReservaPedido() {
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
			MReservaPedidoHdr model = new MReservaPedidoHdr(getCtx(), this.recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para proceso de accion en Reserva de Pedido.";
			
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
