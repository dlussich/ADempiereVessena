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
import org.openup.model.MDiscountRule;
import org.openup.model.MDiscountRuleProd;

/**UY_Discount_Rule - Header de Condiciones de Negocio
 * Proceso para cargar productos desde la versión vigente de la lista de precios seleccionada, con 
 * los filtros sección/rubro/flia/subflia
 * @author IN
 *
 */
public class PLoadFilteredProds extends SvrProcess {
	
	private int discountRuleID = 0;
	private MDiscountRule header;
	

	/**
	 * 
	 */
	public PLoadFilteredProds() {
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
				if (name.equalsIgnoreCase("UY_DiscountRule_ID"))
					this.discountRuleID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}
		if (discountRuleID>0) header = new MDiscountRule(getCtx(), discountRuleID, get_TrxName());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if (header != null) {
			int seccion, rubro, flia, subflia;
			seccion = header.getUY_Linea_Negocio_ID();
			rubro = header.getUY_ProductGroup_ID();
			flia = header.getUY_Familia_ID();
			subflia = header.getUY_SubFamilia_ID();     
			
			
			String sql = "SELECT M_Product_ID, priceList FROM M_ProductPrice "
					+ "WHERE M_PriceList_Version_ID = "
					+ header.getM_PriceList_Version_ID();
			String filtros = "";
			if (seccion > 0) {
				filtros = "AND M_Product_ID IN (SELECT M_Product_ID FROM M_Product ";
				filtros += "WHERE UY_Linea_Negocio_ID= " + seccion + " ";
				if (rubro > 0) {
					filtros += "  AND UY_ProductGroup_ID= " + rubro + " ";
					if (flia > 0) {
						filtros += " AND UY_Familia_ID= " + flia + " ";
						if (subflia > 0) {
							filtros += " AND UY_SubFamilia_ID= " + subflia
									+ " ";
						}
					}
				}
				filtros +=")";
			}
			//sql.concat(filtros);

			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try {
				
				// DELETE EXISTING ROWS
				String sqlDelete= "DELETE FROM UY_DiscountRuleProd WHERE UY_DiscountRule_ID= " + header.get_ID();
				DB.executeUpdateEx(sqlDelete, get_TrxName());	
				//LOAD FILTERED PRODS
				pstmt = DB.prepareStatement(sql.concat(filtros).toString(), null);
				rs = pstmt.executeQuery();
				while (rs.next()) {

					MDiscountRuleProd line = new MDiscountRuleProd(getCtx(), 0,
							get_TrxName());
					line.setUY_DiscountRule_ID(header.get_ID());
					line.setM_Product_ID(rs.getInt("M_Product_ID"));
					//Comentado por E. Bentancor #5690
//					line.setPriceList(rs.getBigDecimal("priceList")); 
//					line.setIsSelected(true);// puede que no se use, pensar
					//Fin #5690
					line.saveEx(get_TrxName());
				}

			} catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}
		return null;
	}

}
