/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.util.HashMap;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflow;
import org.eevolution.model.MPPOrder;

/**
 * @author Kacike
 *
 */
public class RPrdHojaProceso extends SvrProcess {

	private int PPorderid_Desde;
	private int PPorderid_Hasta;
	private String OpImprime="O";

	/**
	 * 
	 */
	public RPrdHojaProceso() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		// Valido que rango de ids sean correctos
		if (this.PPorderid_Desde > this.PPorderid_Hasta)
			throw new Exception("El ID de la orden desde no puede ser mayor que el ID de la orden Hasta.");
		
		// Si imprimo solo la orden, salgo sin hacer mas nada.
		if (OpImprime.equalsIgnoreCase("O")) return "OK";
		
		HashMap<Integer,Integer> hashRutas = new HashMap<Integer, Integer>();
		
		// Recorrer rangos de ids de pp_orders para impresion se rutas
		for (int i=this.PPorderid_Desde; i<=this.PPorderid_Hasta; i++){
			MPPOrder ppOrder = new MPPOrder(getCtx(), i, null);
			MWorkflow wflow = new MWorkflow(getCtx(), ppOrder.getAD_Workflow_ID(), null);
			
			// Si este workflow_id no fue procesado, o sea la ruta asociada no fue impresa
			if (!hashRutas.containsKey(wflow.getAD_Workflow_ID())){

				// Imprimo ruta
				if (wflow.get_ValueAsString("rutas")!=null && !wflow.get_ValueAsString("rutas").equalsIgnoreCase("")){
					Env.startBrowser(wflow.get_ValueAsString("rutas").trim());
				}

				// Agrego este workflow_ID al hash
				hashRutas.put(wflow.getAD_Workflow_ID(), wflow.getAD_Workflow_ID());
			}
		}
		
		// Si solo imprimo rutas
		if (OpImprime.equalsIgnoreCase("R"))
			throw new Exception("No se indica impresion de Ordenes de Proceso.");
		
		return "OK";
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("PP_Order_ID")) {
					if (para[i].getParameter() != null) {
						this.PPorderid_Desde = ((BigDecimal) para[i].getParameter()).intValueExact();
						this.PPorderid_Hasta = ((BigDecimal) para[i].getParameter_To()).intValueExact();
					}
				}

				if (name.equalsIgnoreCase("UY_OpImpresion")) {
					this.OpImprime = ((String) para[i].getParameter());
				}
				
			}
			
		}

	}

}
