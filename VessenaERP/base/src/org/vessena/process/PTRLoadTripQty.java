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
 * @author Nicolas
 *
 */
public class PTRLoadTripQty extends SvrProcess {
	
	private int tripID = 0;

	/**
	 * 
	 */
	public PTRLoadTripQty() {
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
				if (name.equalsIgnoreCase("UY_TR_Trip_ID")){
					this.tripID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}	

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		int seqID = 0;
		
		try{		
			
			sql = "select ad_sequence_id from ad_sequence where lower(name) like 'uy_tr_tripqty'";
			seqID = DB.getSQLValueEx(get_TrxName(), sql);
			
			if(seqID > 0){
				
				//elimino registros anteriores
				DB.executeUpdateEx("delete from uy_tr_tripqty where uy_tr_trip_id = " + this.tripID, get_TrxName());
				
				//insert desde orden de transporte recibiendo ID de expediente
				sql = "insert into uy_tr_tripqty (uy_tr_tripqty_id, ad_client_id, ad_org_id, created, updated, isactive, createdby," +
						" updatedby, uy_tr_trip_id, uy_tr_transorder_id, uy_tr_transorderline_id, uy_tr_crt_id, weight, weight2, volume, qtypackage," +
						" c_currency_id, productamt, m_warehouse_id, uy_tr_loadmonitorline_id, uy_tr_stock_id)" +
						" select nextid(" + seqID + ",'N'), o.ad_client_id, o.ad_org_id, ol.created, ol.updated, 'Y', ol.createdby, ol.updatedby," +
						" ol.uy_tr_trip_id, o.uy_tr_transorder_id, ol.uy_tr_transorderline_id, ol.uy_tr_crt_id, ol.weight, ol.weight2, ol.volume," +
						" ol.qtypackage, ol.c_currency_id, ol.productamt, null, ol.uy_tr_loadmonitorline_id, null" +
						" from uy_tr_transorderline ol" +
						" inner join uy_tr_transorder o on ol.uy_tr_transorder_id = o.uy_tr_transorder_id" +
						" where o.docstatus = 'AY' and ol.uy_tr_trip_id = " + this.tripID;
				DB.executeUpdateEx(sql, get_TrxName());
				
				//insert desde stock recibiendo ID de expediente
				sql = "insert into uy_tr_tripqty (uy_tr_tripqty_id, ad_client_id, ad_org_id, created, updated, isactive, createdby, updatedby," +
						" uy_tr_trip_id, uy_tr_transorder_id, uy_tr_transorderline_id, uy_tr_crt_id, weight, weight2, volume, qtypackage, c_currency_id," +
						" productamt, m_warehouse_id, uy_tr_loadmonitorline_id, uy_tr_stock_id)" +
						" select nextid(" + seqID + ",'N'), ml.ad_client_id, ml.ad_org_id, ml.created, ml.updated, 'Y', ml.createdby, ml.updatedby," +
						" v.uy_tr_trip_id, null, null, v.uy_tr_crt_id, v.weight, v.weight2, v.volume, v.qtypackage, v.c_currency_id, v.productamt," +
						" v.m_warehouse_id, ml.uy_tr_loadmonitorline_id, stk.uy_tr_stock_id" +
						" from stktr_crtopen v" +
						" inner join uy_tr_stock stk on (v.uy_tr_trip_id = stk.uy_tr_trip_id and v.m_warehouse_id = stk.m_warehouse_id)" +
						" inner join uy_tr_loadmonitorline ml on stk.uy_tr_loadmonitorline_id = ml.uy_tr_loadmonitorline_id" +
						" where v.uy_tr_trip_id = " + this.tripID + " and v.qtypackage > 0" +
						" and stk.uy_tr_stock_id = (select max (uy_tr_stock_id) from uy_tr_stock where m_warehouse_id = v.m_warehouse_id and uy_tr_trip_id = v.uy_tr_trip_id)";
				DB.executeUpdateEx(sql, get_TrxName());				
				
			}			
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK.";
	}

}
