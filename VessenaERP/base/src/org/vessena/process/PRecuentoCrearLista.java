
package org.openup.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInventoryLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MRecuentoDef;



// org.openup.process.PRecuentoCrearLista
// OpenUp. Nicolas Garcia. 07/10/2011. Creado para Issue #889.
public class PRecuentoCrearLista extends SvrProcess {

	/** Physical Inventory Parameter		*/
	private int p_MRecuentoDefID = 0;
	/** Physical Inventory					*/
	private MRecuentoDef m_RecuentoDef = null;
	/** Locator Parameter			*/
	private int p_M_Locator_ID = 0;
	/** Locator Parameter			*/
	private String p_LocatorValue = null;
	/** Product Parameter			*/
	private String p_ProductValue = null;
	/** Product Category Parameter	*/
	private int p_M_Product_Category_ID = 0;
	/** Qty Range Parameter			*/
	private String p_QtyRange = null;
	/** Update to What			*/
	private boolean p_InventoryCountSetZero = false;
	/** Delete Parameter			*/
	private boolean p_DeleteOld = false;

	private static String TABLA_LINEAS = "UY_RecuentoDefProd";

	/** Inventory Line				*/
	private MInventoryLine m_line = null;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			;
			else if (name.equals("M_Locator_ID")) p_M_Locator_ID = para[i].getParameterAsInt();
			else if (name.equals("LocatorValue")) p_LocatorValue = (String) para[i].getParameter();
			else if (name.equals("ProductValue")) p_ProductValue = (String) para[i].getParameter();
			else if (name.equals("M_Product_Category_ID")) p_M_Product_Category_ID = para[i].getParameterAsInt();
			else if (name.equals("QtyRange")) p_QtyRange = (String) para[i].getParameter();
			else if (name.equals("InventoryCountSet")) p_InventoryCountSetZero = "Z".equals(para[i].getParameter());
			else if (name.equals("DeleteOld")) p_DeleteOld = "Y".equals(para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_MRecuentoDefID = getRecord_ID();
	} // prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt() throws Exception {
		String insertFromSelect = "";
		try {
		m_RecuentoDef = new MRecuentoDef(getCtx(), p_MRecuentoDefID, get_TrxName());

		if (m_RecuentoDef.get_ID() == 0) throw new AdempiereSystemError("Not found: M_Inventory_ID=" + p_MRecuentoDefID);
		if (m_RecuentoDef.isProcessed()) throw new AdempiereSystemError("@M_Inventory_ID@ @Processed@");

		if (p_DeleteOld) {
			DB.executeUpdate("DELETE FROM " + TABLA_LINEAS + " WHERE uy_recuentodef_id =" + p_MRecuentoDefID, this.get_TrxName());
		}

			insertFromSelect = getSQL(m_RecuentoDef);

			DB.executeUpdateEx(insertFromSelect, get_TrxName());

		} catch (Exception e) {

			log.log(Level.SEVERE, "error sql:" + insertFromSelect + " " + e.toString());

			throw new AdempiereException("Error por producto repetido");
		}

		return "Terminado OK";
	}

	private String getSQL(MRecuentoDef mMRecuentoDef) {

		int sequenceID = DB.getSQLValue(null, "SELECT ad_sequence_id FROM ad_sequence WHERE name ='" + TABLA_LINEAS + "'");
		int usuarioID = this.getAD_User_ID();

		String salida = "INSERT INTO " + TABLA_LINEAS + " SELECT nextid(" + sequenceID + ",'N')," + this.getAD_Client_ID() + "," + Env.getAD_Org_ID(getCtx())
				+ ",'Y'," + "now()," + usuarioID + ",now()," + usuarioID + "," + "  m_product.m_product_id," + mMRecuentoDef.get_ID() + ","
				+ mMRecuentoDef.getM_Warehouse_ID()
				+ ",m_product.m_locator_id,m_product.value,m_product.name,COALESCE(lock.qty,0),COALESCE(quar.qty,0),COALESCE(app.qty,0),"
				+ "m_product.m_product_category_id,m_product.uy_familia_id"; //OpenUp Nicolas Sarlabos #983 21/02/2012,se obtiene categoria y famila de producto
		String where = getWhere();

		salida += " FROM m_product LEFT JOIN stk_lock_prodxwarxloc lock ON lock.m_product_id=m_product.m_product_id AND " + "lock.m_locator_id="
				+ p_M_Locator_ID + " LEFT JOIN stk_quar_prodxwarxloc quar ON quar.m_product_id=m_product.m_product_id AND quar.m_locator_id=" + p_M_Locator_ID
				+ " LEFT JOIN stk_app_prodxwarxloc app ON app.m_product_id=m_product.m_product_id AND app.m_locator_id=" + p_M_Locator_ID;

		if (p_QtyRange != null && !p_QtyRange.equalsIgnoreCase("")) {



			if (p_QtyRange.equalsIgnoreCase("=")) {
				where += " AND COALESCE(stk_pend_prodxwarxloc.qty,0)=0 ";
			} else if (p_QtyRange.equalsIgnoreCase("<")) {
				where += " AND COALESCE(stk_pend_prodxwarxloc.qty,0)<0 ";
			} else if (p_QtyRange.equalsIgnoreCase(">")) {
				where += " AND COALESCE(stk_pend_prodxwarxloc.qty,0)>0 ";
			} else if (p_QtyRange.equalsIgnoreCase("N")) {
				where += " AND COALESCE(stk_pend_prodxwarxloc.qty,0)!=0 ";
			}

		}

		// salida += " FROM m_product ";



		return salida + where;
	}

	private String getWhere() {

		String salida = " WHERE m_product.isactive='Y' AND m_product.ad_client_id=" + this.getAD_Client_ID();

		if (p_M_Locator_ID > 0) salida += " AND m_product.m_locator_id=" + p_M_Locator_ID;

		if (p_M_Product_Category_ID > 0) salida += " AND m_product.m_product_category_id=" + p_M_Product_Category_ID;

		if (p_ProductValue != null && !p_ProductValue.equalsIgnoreCase(""))
			salida += " AND LOWER(m_product.value) LIKE '" + p_ProductValue.toLowerCase() + "%'";

		return salida + "ORDER BY m_product.value";
	}


}