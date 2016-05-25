/**
 * PVerPedidosGenReserva.java
 * 11/01/2011
 */
package org.openup.process;

import java.util.logging.Level;

import org.compiere.process.SvrProcess;
import org.openup.model.MReserveFilter;

/**
 * OpenUp.
 * PVerPedidosGenReserva
 * Descripcion : Proceso que obtiene y carga pedidos par filtro en generacion de reserva.
 * Estos pedidos contemplan los filtros precargados por el usuario.
 * @author Gabriel Vila
 * Fecha : 11/01/2011
 */
public class PVerPedidosGenReserva extends SvrProcess {

	private int recordID = 0;
	
	/**
	 * Constructor
	 */
	public PVerPedidosGenReserva() {
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
			MReserveFilter model = new MReserveFilter(getCtx(), this.recordID, get_TrxName());
			if (model.get_ID() <= 0) return "No se pudo obtener modelo para filtros ingresados.";
			
			// Si no puedo salgo con el texto del error.
			if(!model.getPedidosFiltros()){
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


}
