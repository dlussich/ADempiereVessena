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
import org.openup.model.MTRTireMoveLine;
import org.openup.model.MTRTireMoveOpen;


/**
 * @author gbrust
 *
 */
public class PTRTireMoveLocate extends SvrProcess {
	
	
	private int locatorValue = 0;
	private MTRTireMove tireMove = null;

	
	public PTRTireMoveLocate() {
		
	}

	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("LocatorValue")){
					this.locatorValue = ((BigDecimal)para[i].getParameter()).intValueExact();
				}	
			}
		}	
		
		this.tireMove = new MTRTireMove(this.getCtx(), this.getRecord_ID(), this.get_TrxName());

	}


	@Override
	protected String doIt() throws Exception {
		
		if(this.tireMove.getLinesSelected().size() > 1) throw new AdempiereException("No es posible colocar mas de un neumatico a la vez");
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;			

		try {		
			
			String sql = "select uy_tr_tire_id as neumatico, uy_tr_tiremark_id as marca, uy_tr_tiremeasure_id as medida, observaciones as modelo, qty as recauchutajes, qtykm as casco, estadoActual" +
						 " from uy_tr_tiremoveopen" +
						 " where uy_tr_tiremove_id = " + this.tireMove.get_ID() +
						 " and isselected = 'Y'";

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			while(rs.next()){
				
				MTRTireMoveLine line = MTRTireMoveLine.forPosition(this.getCtx(), this.locatorValue, this.tireMove.get_ID(), this.get_TrxName());
				
				if(line != null && line.get_ID() > 0){	
					
					if(line.getUY_TR_Tire_ID() > 0) throw new AdempiereException("Ya existe un neumatico en la posicion seleccionada");

					line.setUY_TR_TireMove_ID(this.tireMove.get_ID());
					line.setLocatorValue(this.locatorValue);
					line.setUY_TR_Tire_ID(rs.getInt("Neumatico"));
					line.setUY_TR_TireMark_ID(rs.getInt("Marca"));
					//line.setUY_TR_TireModel_ID(rs.getInt("Modelo"));
					line.setUY_TR_TireMeasure_ID(rs.getInt("medida"));
					line.setobservaciones(rs.getString("modelo"));
					line.setQty(rs.getInt("Recauchutajes"));
					line.setQtyKm(rs.getBigDecimal("casco"));
					line.setEstadoActual(rs.getString("estadoActual"));
					line.setIsSelected(false);		
					line.setIsChanged(true);
					
					line.saveEx();
					
					//También sacamos el neumatico de la grilla de neumaticos libres
					MTRTireMoveOpen lineOpen = MTRTireMoveOpen.forTireID(this.getCtx(), rs.getInt("neumatico"), this.tireMove.get_ID(), this.get_TrxName());
					lineOpen.deleteEx(true);
									
				}else{
					throw new AdempiereException("No es posible encontrar la posición indicada");
				}			
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
