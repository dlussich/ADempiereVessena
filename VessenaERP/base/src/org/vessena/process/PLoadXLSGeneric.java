/**
 * PLoadXLS.java
 * 04/04/2011
 */
package org.openup.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MLoadhdr_XLS;

/**
 * OpenUp.
 * PLoadXLS
 * Descripcion :Proceso para la carga generica de XLS
 * @author Nicolas Garcia
 * Fecha : 04/04/2011 actualizado 08/09/2011 issue #860
 */
//org.openup.process.PLoadXLS_Generic
public class PLoadXLSGeneric extends SvrProcess{
	
	String tipoCarga=null;
	Integer ventanaID=0;
	MLoadhdr_XLS  windows;

	@Override
	protected String doIt() throws Exception {
		deleteOldError();
		//Productos
		if(tipoCarga.equals("PR")){
			PLoadProductsXLS pr=new PLoadProductsXLS(getCtx(), getTable_ID(), getRecord_ID(), this.log,windows.getFileName(),windows.isErrorReplication());
			String salida = pr.procesar();

			if (salida.equalsIgnoreCase("Carga Finalizada Correctamente")) {
				windows.setIsActive(false);
				windows.saveEx();
			}
			return salida;
		}
		//OpenUp Nicolas Sarlabos #941 21/11/2011
		//Estandar Producciones
		if(tipoCarga.equals("EP")){
			PLoadStdProductionsXLS pr=new PLoadStdProductionsXLS(getCtx(), getTable_ID(), getRecord_ID(), this.log,windows.getFileName(),windows.isErrorReplication());
			String salida = pr.procesar();

			if (salida.equalsIgnoreCase("Carga Finalizada Correctamente")) {
				windows.setIsActive(false);
				windows.saveEx();
			}
			return salida;
		}
		//OpenUp Nicolas Sarlabos #944 10/02/2012
		//Ajuste Manual de Costos 
		if (tipoCarga.equals("AC")) {
			PLoadManualCostAdjustmentXLS pr = new PLoadManualCostAdjustmentXLS(getCtx(), getTable_ID(), getRecord_ID(), this.log, windows.getFileName(),
					windows.isErrorReplication());
			String salida = pr.procesar();

			if (salida.equalsIgnoreCase("Carga Finalizada Correctamente")) {
				windows.setIsActive(false);
				windows.saveEx();
			}
			return salida;
		}
		//Fin OpenUp Nicolas Sarlabos #944 10/02/2012
		//Carga de XLS a tabla
		if(tipoCarga.equals("CT")){
			PLoadActionNoticeXLS pr=new PLoadActionNoticeXLS(getCtx(), getTable_ID(), getRecord_ID(), this.log,windows.getFileName(),windows.isErrorReplication());
			String salida = pr.procesar();
			
			if (salida!=null && salida.equalsIgnoreCase("Carga Finalizada Correctamente")) {  //OpenUp Nicolas Sarlabos #776 29/11/2011,se controla que salida no sea null
				windows.setIsActive(false);
				windows.saveEx();
			}
			return salida;
		}
		//Traduccion txt a xls Carga reloj
		if(tipoCarga.equals("CL")){
			PLoadClockTXTtoXLS pr=new PLoadClockTXTtoXLS(getCtx(), getTable_ID(), getRecord_ID(), this.log,windows.getFileName(),windows.isErrorReplication());
			String salida = pr.procesar();

			if (salida!=null && salida.equalsIgnoreCase("Carga Finalizada Correctamente")) {  //OpenUp Nicolas Sarlabos #776 29/11/2011,se controla que salida no sea null
				windows.setIsActive(false);
				windows.saveEx();
			}
			return salida;
		}
		
		//Devoluciones
		if(tipoCarga.equals("DV")){
			PXLSCustomerReturn pr=new PXLSCustomerReturn(getCtx(), getTable_ID(), getRecord_ID(), this.log,windows.getFileName(),windows.isErrorReplication());
			String salida = pr.procesar();

			if (salida.equalsIgnoreCase("Proceso Terminado OK")) {
				windows.setIsActive(false);
				windows.saveEx();
			}
			return salida;
		}
		//OpenUp Nicolas Sarlabos #965 23/02/2012
		//Grupos de clientes promociones
		if (tipoCarga.equals("GR")) {
			PXLSPromotionGroup gr = new PXLSPromotionGroup(getCtx(), getTable_ID(), getRecord_ID(), this.log, windows.getFileName(), windows
					.isErrorReplication());
			String salida = gr.procesar();

			if (salida.equalsIgnoreCase("Proceso Terminado OK")) {
				windows.setIsActive(false);
				windows.saveEx();
			}
			return salida;
		}
		//fin OpenUp Nicolas Sarlabos #965 23/02/2012
		return null;
	}

	@Override
	protected void prepare() {
		//Cargar la ventana base
		windows=new MLoadhdr_XLS(getCtx() , getRecord_ID(), null);
		tipoCarga=windows.getTypeLoadXLS();
		ventanaID=windows.getUY_Loadhdr_XLS_ID();
		
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion : método que elimina antiguos errores
	 * @throws Exception
	 * @author  Nicolas Garcia
	 * Fecha : 04/04/2011
	 */
	private void deleteOldError()throws Exception{
		//A pedido de gabriel Se borra todo lo del usuario para ese tipo de carga al procesar
		//String sql = "DELETE FROM  uy_xlsissue WHERE record_id="+ ventanaID;
		String sql = "DELETE  FROM uy_xlsissue WHERE ad_table_id="+windows.get_Table_ID()+" AND record_id IN(SELECT uy_loadhdr_xls_id FROM uy_loadhdr_xls WHERE typeloadxls='"+tipoCarga+"' AND createdby="+Env.getAD_User_ID(Env.getCtx())+")AND createdby="+Env.getAD_User_ID(Env.getCtx())+";";
			  //sql += "DELETE FROM uy_loadhdr_xls WHERE typeloadxls='"+tipoCarga+"' AND uy_loadhdr_xls_id<>"+ventanaID+" AND createdby="+Env.getAD_User_ID(getCtx());
		
		try {
			DB.executeUpdate(sql, null);
			
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}

}
