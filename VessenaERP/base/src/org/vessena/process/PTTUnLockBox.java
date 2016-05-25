/**
 * 
 */
package org.openup.process;


import java.math.BigDecimal;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author gbrust
 *
 */
public class PTTUnLockBox extends SvrProcess {

	
	private int boxID = 0;
	
	
	public PTTUnLockBox() {
		
	}

	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){				
				if (name.equalsIgnoreCase("UY_TT_Box_ID")){
					this.boxID = ((BigDecimal)para[i].getParameter()).intValueExact();				
				}				
			}
		}

	}

	
	@Override
	protected String doIt() throws Exception {	
		
		if(this.boxID > 0){
			try{		

				String sql = "UPDATE UY_TT_Box SET Processing = 'N' WHERE UY_TT_Box_ID = " + this.boxID;					    

				DB.executeUpdateEx(sql, this.get_TrxName());

			}
			catch (Exception e){
				throw new AdempiereException(e);
			}
			
		}else throw new AdempiereException("No se seleccion� caja para desbloquear");
		
		
		return "Ok";
	}

}
