/**
 * 
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MFFCashOutLine extends X_UY_FF_CashOutLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7908684169922083553L;

	/**
	 * @param ctx
	 * @param UY_FF_CashOutLine_ID
	 * @param trxName
	 */
	public MFFCashOutLine(Properties ctx, int UY_FF_CashOutLine_ID,
			String trxName) {
		super(ctx, UY_FF_CashOutLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFCashOutLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MFFCashOut hdr = new MFFCashOut(getCtx(), this.getUY_FF_CashOut_ID(),get_TrxName());
		MDocType doc = new MDocType(getCtx(),hdr.getC_DocType_ID(),get_TrxName());
		
		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("cashrepay")){
				
				if(this.getfactura() <= 0) throw new AdempiereException ("El n° de comprobante debe ser mayor a cero");
				
			}
		}
		
		if(this.getLineTotalAmt().compareTo(Env.ZERO) <= 0) throw new AdempiereException ("El importe debe ser mayor a cero");		
				
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		MFFCashOut hdr = (MFFCashOut) this.getUY_FF_CashOut(); //instancio cabezal
		hdr.setGrandTotal(hdr.getTotalAmount()); //seteo importe total del cabezal
		hdr.saveEx();
		
		//seteo campo de Autorizante
		String users = this.getApproverUser();
		
		if(users != null){
			
			this.setApprovedBy(users);
			
		} else throw new AdempiereException("No se obtuvieron usuarios autorizantes para el centro de costos ingresado");		
		
		return true;
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 19/12/2013. ISSUE #1278
	 * Obtiene y devuelve un string con los usuarios autorizantes para el centro de costos actual.
	 * 
	 * */	
	private String getApproverUser() {
		
		String value = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;			
		
		try{
			
			sql = "select distinct a.description from ad_user a" +
                  " inner join uy_popolicyuser pu on a.ad_user_id = pu.ad_user_id" +
                  " inner join uy_posection sect on pu.uy_posection_id = sect.uy_posection_id" +
                  " where a.isactive = 'Y' and sect.c_activity_id_1 = '" + this.getC_Activity_ID_1() +
                  "' and pu.nivel = '1'";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
		    	if(value==null){
		    		
		    		value = rs.getString("description");
		    		
		    	} else {
		    		
		    		value += "/" + rs.getString("description");	    		
		    		
		    	}	    	
		    	
		    }			
			
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
		return value;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		
		MFFCashOut hdr = (MFFCashOut) this.getUY_FF_CashOut(); //instancio cabezal
		hdr.setGrandTotal(hdr.getTotalAmount()); //seteo importe total del cabezal
		hdr.saveEx();
				
		return true;
	}

	
	

}
