/**
 * 
 */
package org.openup.process;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;


/**OpenUp Ltda Issue#4907 
 * Proceso para realizar consulta desde WS - RT
 * @author SBT 14/10/2015
 *
 */
public class PRTPriceListVersionIDForClient extends SvrProcess {

	private int m_priceListID = 0;
	/**
	 * 
	 */
	public PRTPriceListVersionIDForClient() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("M_PriceList_ID")){
					if (para[i].getParameter() != null) this.m_priceListID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}			
		}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		if(this.m_priceListID==0)return "-1"; // REtorno -1 cuando hay error
		try{
			MPriceList pl = new MPriceList(getCtx(), m_priceListID, null);
			MPriceListVersion mplv = pl.getVersionVigente(new Timestamp(System.currentTimeMillis()));
			if(null==mplv)return "0"; // Retorno 0 cuando no se encontro para esa versionID 
			return String.valueOf(mplv.get_ID()); 
		}catch(Exception e){
			throw new AdempiereException("-1");
		}
	
	}

}
