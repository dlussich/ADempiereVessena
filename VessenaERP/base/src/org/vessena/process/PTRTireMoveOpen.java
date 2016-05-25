/**
 * 
 */
package org.openup.process;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MTRTireMove;
import org.openup.model.MTRTireMoveOpen;

/**
 * @author gbrust
 *
 */
public class PTRTireMoveOpen extends SvrProcess {
	
	
	private int warehouseID = 0;
	private MTRTireMove tireMove = null;

	
	public PTRTireMoveOpen() {
		
	}

	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("M_WareHouse_ID")){
					this.warehouseID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}				
			}
		}	
		
		
		this.tireMove = new MTRTireMove(this.getCtx(), this.getRecord_ID(), this.get_TrxName());

	}

	
	@Override
	protected String doIt() throws Exception {		

		ResultSet rs = null;
		PreparedStatement pstmt = null;			

		try {		
			
			String sql = "select uy_tr_tiremoveline_id as id, locatorvalue as posicion, uy_tr_tire_id as neumatico, uy_tr_tiremark_id as marca," +
			             "uy_tr_tiremeasure_id as medida, observaciones as modelo, qty as recauchutajes, qtykm as casco, estadoActual, isauxiliar" +
						 " from uy_tr_tiremoveline" +
						 " where isselected = 'Y'" +
						 " and uy_tr_tiremove_id = " + this.tireMove.get_ID();

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			while(rs.next()){
				
				//Genero una linea en la grilla de neumaticos disponibles con la informacion del neumatico y el almacen seleccionado en el proceso
				
				MTRTireMoveOpen line = new MTRTireMoveOpen(this.getCtx(), 0, this.get_TrxName());
				
				line.setUY_TR_TireMove_ID(this.tireMove.get_ID());
				line.setM_Warehouse_ID(this.warehouseID);					
				line.setUY_TR_Tire_ID(rs.getInt("Neumatico"));
				line.setUY_TR_TireMark_ID(rs.getInt("Marca"));
				line.setUY_TR_TireMeasure_ID(rs.getInt("medida"));
				line.setobservaciones(rs.getString("modelo"));
				line.setQty(rs.getInt("Recauchutajes"));
				line.setQtyKm(rs.getBigDecimal("casco"));
				line.setEstadoActual(rs.getString("estadoActual"));
				line.setIsSelected(false);
				line.setIsChanged(true);
				
				boolean auxiliar = false;
				
				if(rs.getString("isauxiliar").equalsIgnoreCase("Y")){
					
					auxiliar = true;
					
				} else if (rs.getString("isauxiliar").equalsIgnoreCase("N")) auxiliar = false;
				
				line.setIsAuxiliar(auxiliar);
				
				line.saveEx();
				
				//También sacamos el neumatico de la grilla de neumaticos asociados a este vehiculo	
				DB.executeUpdate("UPDATE UY_TR_TireMoveLine SET UY_TR_Tire_ID = null, UY_TR_TireMark_ID = null, UY_TR_TireMeasure_ID = null, observaciones = null, qty = null, qtykm = null, isselected= 'N', ischanged = 'N', estadoactual = null WHERE UY_TR_TireMoveLine_ID = " + rs.getInt("id"), this.get_TrxName());
			}		
						
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e.getMessage());
		}
		finally{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
		return "ok";
	}

}
