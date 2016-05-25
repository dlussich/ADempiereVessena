package org.openup.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp. 28/05/2015. Issue #743
 * Proceso: impresion de código de etiquetas.
 * @author INes Fernandez.
 *
 */
public class RLabelCode extends SvrProcess {
	private int adClientID = 0, adOrgID=0, adUserID = 0;
	private int m_inout_id=0;  

	private static final String TABLA_MOLDE = "UY_Molde_LabelCode";

	public RLabelCode() {
	}

	@Override
	protected void prepare() {
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		//hay un único param, pero queda dentro del for, para reusar en impresión de etiq desde confirmación de orden de proceso.
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("m_inout_id") && para[i].getParameter()!=null)
					this.m_inout_id= para[i].getParameterAsInt();
			}	
		}
		this.adClientID = getAD_Client_ID();
		this.adUserID = getAD_User_ID();
}

	@Override
	protected String doIt() throws Exception {
		cleanTable();
		populateTable();
		return "ok";
	}
	
	
	/**
	 * Delete de registros anteriores
	 */
	private void cleanTable(){
		String sql = "";
		try{
			sql = "DELETE FROM " + TABLA_MOLDE;
			DB.executeUpdate(sql, null);
		}
		catch (Exception e){
			log.log(Level.SEVERE, sql, e);
		}
	}

	private void populateTable() throws Exception {
		MInOut header = new MInOut(getCtx(), m_inout_id, get_TrxName());
		MInOutLine[] lines = header.getLines();
		if(lines!=null){
			for(MInOutLine lineAux: lines){
				MProduct prod = new MProduct(getCtx(), lineAux.getM_Product_ID(), get_TrxName());
				if(prod.get_ValueAsBoolean("uy_istrackable")){
					int q = lineAux.get_ValueAsInt("nopackages");
					for (int i = 1; i<=q; i++) insert(lineAux);
				}
			}
		}
	}

private void insert(MInOutLine line) throws Exception{
		String sql="";
		int m_inout_id;
		int m_inoutline_id;
		int m_product_id;
		BigDecimal qtyentered;
		int c_uom_id;
		int nopackages;
		if(line!=null){
			m_inout_id = line.getM_InOut_ID();
			m_inoutline_id = line.getM_InOutLine_ID();
			m_product_id = line.getM_Product_ID();
			qtyentered = line.getQtyEntered();
			c_uom_id = line.getC_UOM_ID();
			nopackages = line.get_ValueAsInt("nopackages");
			
			try{
				sql = "INSERT INTO " +TABLA_MOLDE+ " (ad_org_id, ad_client_id, ad_user_id, "
						+ "m_inout_id, m_inoutline_id, m_product_id, qtyEntered, c_uom_id, nopackages) VALUES ( " 
						+this.adOrgID+ "," +this.adClientID+","+this.adUserID +"," 
						+m_inout_id+ "," +m_inoutline_id+ ","+m_product_id+"," +qtyentered+","+c_uom_id+","+nopackages+")";
							
				log.log(Level.INFO, sql);
				DB.executeUpdate(sql, null);
			}
			catch (Exception e){
				log.log(Level.SEVERE, sql, e);
				throw e;
			}
		}		
	}
	
}
