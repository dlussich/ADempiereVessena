/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.I_UY_Molde_PromCons;
import org.openup.model.MMoldePromCons;
import org.openup.model.X_UY_Molde_PromCons;

/**
 * @author Nicolas
 *
 */
public class RTransPromedioConsumo extends SvrProcess{
	
	
	private int driverID = 0;
	private int truckID = 0;	
	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int adUserID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;	
	private String idReporte = "";	
	private static final String TABLA_MOLDE = "UY_MOLDE_PromCons";

	/**
	 * 
	 */
	public RTransPromedioConsumo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		// Obtengo parametros y los recorro
				ProcessInfoParameter[] para = getParameter();
				for (int i = 0; i < para.length; i++)
				{
					String name = para[i].getParameterName().trim();
					if (name!= null){

						if (name.equalsIgnoreCase("UY_TR_Truck_ID")){
							if (para[i].getParameter()!=null)
								this.truckID = ((BigDecimal)para[i].getParameter()).intValueExact();
						}

						if (name.equalsIgnoreCase("UY_TR_Driver_ID")){
							if (para[i].getParameter()!=null)
								this.driverID = ((BigDecimal)para[i].getParameter()).intValueExact();
						}
						
						if (name.equalsIgnoreCase("DateTrx")){
							this.fechaDesde = (Timestamp)para[i].getParameter();
							this.fechaHasta = (Timestamp)para[i].getParameter_To();
						}
						
						if (name.equalsIgnoreCase("AD_User_ID")){
							this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
						}
						
						if (name.equalsIgnoreCase("AD_Client_ID")){
							this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
						}

						if (name.equalsIgnoreCase("AD_Org_ID")){
							this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
						}				
					}
				}
				
				// Obtengo id para este reporte
				this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));
		
	}

	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		this.showHelp("Cargando registros");
		this.loadMovimientos();
		
		//se cargan datos de promedios
		this.updateData();
		
		this.showHelp("Iniciando Vista Previa");
		
		return "ok";
	}
	
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id = " + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	/**
	 * OpenUp. #1610	
	 * Descripcion : Carga de datos de consumos en tabla molde.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 05/12/2013
	 */
	private void loadMovimientos(){
		
		String insert = "", sql = "";
		String whereFiltros = "";		
		
		try{
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (uy_molde_promcons_id, ad_client_id, ad_org_id, created, updated, createdby, updatedby, ad_user_id, idreporte," +
					" fecreporte, datetrx, uy_tr_truck_id, uy_tr_driver_id, " + 
					" factura, kilometros, c_bpartner_id, litros, totalamt, costamt, isfulltank) ";					
		
			if(this.driverID > 0) whereFiltros += " AND uy_tr_driver_id = " + this.driverID;
			
			sql = "SELECT nextid(1001979,'N'), ad_client_id, ad_org_id, current_date, current_date, " + this.adUserID + "," + this.adUserID + "," + this.adUserID + ", '" + this.idReporte + "', current_date, datetrx, uy_tr_truck_id, uy_tr_driver_id, factura, " +
			      " kilometros, c_bpartner_id, litros, totalamt, (totalamt/litros) as costamt, case when isfulltank = 'Y' then 1 else 0 end as isfulltank " +
				  " FROM uy_tr_fuel " +
			      " WHERE docstatus = 'CO' AND uy_tr_truck_id = " + this.truckID + " AND datetrx >= '" + this.fechaDesde + "' AND datetrx <= '" + this.fechaHasta + 
			      "' AND ad_client_id = " + this.adClientID + " AND ad_org_id = " + this.adOrgID + whereFiltros + " ORDER BY datetrx ASC, kilometros ASC";
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdateEx(insert + sql, null);			
			
		} catch (Exception e){
			throw new AdempiereException(e);			
		}		
		
	}
	
	/**
	 * OpenUp.	#1610
	 * Descripcion : Actualiza tabla molde cargando los datos de promedios de consumo.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 06/12/2013
	 */
	private void updateData(){
		
		BigDecimal prom = Env.ZERO;
		MMoldePromCons ultimaCompleta = null;
						
		MMoldePromCons[] promLines = this.getLines(); //obtengo lista de registros insertados
		MMoldePromCons primerLinea = promLines[0]; //obtengo primer linea
		//MMoldePromCons segundaLinea = promLines[1]; //obtengo segunda linea
		
		BigDecimal totalLitros = promLines[1].getLitros();
		
		//se calculan y cargan promedios actuales
		for (int i = 1; i < promLines.length; i++){

			MMoldePromCons anterior = promLines[i-1];
			if(anterior.getIsFullTank() > 0) ultimaCompleta = anterior;

			MMoldePromCons actual = promLines[i];
			//if(actual.getIsFullTank() > 0) ultimaCompleta = actual;

			if((actual.getIsFullTank() + anterior.getIsFullTank())>1){

				if(actual.getLitros().compareTo(Env.ZERO) > 0){

					prom = (actual.getKilometros().subtract(anterior.getKilometros())).divide(actual.getLitros(), 2, RoundingMode.HALF_UP);

					actual.setPromedioActual(prom);
					actual.saveEx();				

				}
			}

			//si en el registro anterior no se completo el tanque el calculo es diferente
			if(anterior.getIsFullTank() == 0){

				if((actual.getIsFullTank() + ultimaCompleta.getIsFullTank())>1){

					if(actual.getLitros().compareTo(Env.ZERO) > 0){

						prom = (actual.getKilometros().subtract(ultimaCompleta.getKilometros())).divide(actual.getLitros(), 2, RoundingMode.HALF_UP);

						actual.setPromedioActual(prom);
						actual.saveEx();

					}
				}				

			}		
			
		}
		
		//se calculan y cargan promedios acumulados
		for (int i = 2; i < promLines.length; i++){
			
			MMoldePromCons actual = promLines[i];
			
			BigDecimal km = (actual.getKilometros().subtract(primerLinea.getKilometros()));
			
			totalLitros = totalLitros.add(actual.getLitros());
			
			if(totalLitros.compareTo(Env.ZERO) > 0){
				
				prom = km.divide(totalLitros, 2, RoundingMode.HALF_UP);
				
				actual.setPromedioAcumulado(prom);
				actual.saveEx();				
			}		
			
		}		
	}
	
	/***
	 * Obtiene y retorna lineas de tabla molde de este reporte.
	 * OpenUp Ltda. Issue #1610
	 * @author Nicolas Sarlabos - 06/12/2013
	 * @see
	 * @return
	 */
	public MMoldePromCons[] getLines(){

		String whereClause = X_UY_Molde_PromCons.COLUMNNAME_idReporte + "= '" + this.idReporte + "'" + " AND ad_user_id = " + this.adUserID;

		List<MMoldePromCons> list = new Query(getCtx(), I_UY_Molde_PromCons.Table_Name, whereClause, get_TrxName()).setOrderBy(I_UY_Molde_PromCons.COLUMNNAME_DateTrx).
		setOrderBy(I_UY_Molde_PromCons.COLUMNNAME_Kilometros).list();

		return list.toArray(new MMoldePromCons[list.size()]);
	}
	
	private void showHelp(String message){
		
		if (this.getProcessInfo().getWaiting() != null)
			this.getProcessInfo().getWaiting().setText(message);
		
	}

}
