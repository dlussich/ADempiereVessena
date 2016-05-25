/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 05/07/2014
 */
package org.openup.process;

import java.math.BigDecimal;
import java.util.List;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MTRTruck;
import org.openup.model.MTRTruckMaintain;

/**
 * org.openup.process - PTRLoadTruckMaintains
 * OpenUp Ltda. Issue #1405 
 * Description: Proceso para copiar las tareas de mantenimiento desde un vechiculo origen a un vehiculo destino.
 * En caso de que una tarea ya exista en el destino, la misma no se copia y se mantiene la original.
 * @author Gabriel Vila - 05/07/2014
 * @see
 */
public class PTRLoadTruckMaintains extends SvrProcess {

	MTRTruck truckDestino = null, truckOrigen = null;
	
	/**
	 * Constructor.
	 */
	public PTRLoadTruckMaintains() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 05/07/2014
	 * @see
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("UY_TR_Truck_ID")){
					truckOrigen = new MTRTruck(getCtx(), ((BigDecimal)para[i].getParameter()).intValueExact(), null);
				}
			}
		}
		
		this.truckDestino = new MTRTruck(getCtx(), this.getRecord_ID(), get_TrxName());

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 05/07/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try {

			DB.executeUpdateEx("delete from uy_tr_truckmaintain where uy_tr_truck_id = " + truckDestino.get_ID(), get_TrxName());
			
			// Obtengo lista de tareas de mantenimiento del vehiculo origen
			List<MTRTruckMaintain> tareas = truckOrigen.getMaintains();
			
			for (MTRTruckMaintain tarea: tareas){
				if (!truckDestino.containsMaintain(tarea.get_ID())){
					MTRTruckMaintain tareaDestino = new MTRTruckMaintain(getCtx(), 0, get_TrxName());
					tareaDestino.setUY_TR_Truck_ID(truckDestino.get_ID());
					tareaDestino.setUY_TR_Maintain_ID(tarea.getUY_TR_Maintain_ID());
					tareaDestino.setKilometros(tarea.getKilometros());
					tareaDestino.setSeqNo(tarea.getSeqNo());//OpenUp. Nicolas Sarlabos. 20/05/2015. #4129.
					tareaDestino.saveEx();
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return "OK";
	}

}
