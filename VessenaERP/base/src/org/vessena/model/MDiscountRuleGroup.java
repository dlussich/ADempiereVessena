package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.util.DB;

public class MDiscountRuleGroup extends X_UY_DiscountRule_Group {
	
	private ArrayList<MProduct> m_prods = new ArrayList<MProduct>();

	/**
	 * 
	 */
	private static final long serialVersionUID = -5218889182734508076L;
	
	public MDiscountRuleGroup(Properties ctx, int UY_DiscountRule_Group_ID, String trxName) {
		super(ctx, UY_DiscountRule_Group_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiscountRuleGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * Obtiene y retorna los prods de la version VIGENTE (a la fecha que recibe
	 * por param) de la lista de precios que recibe por parám, que pasan los
	 * filtros seteados para esta cond de negocio. OpenUp Ltda. Issue #5690
	 * 
	 * @author E. Bentancor - 07/04/2015
	 * @see
	 * @param ctx
	 * @param date
	 *            : fecha para la que se consulta
	 * @param trxName
	 * @return
	 */
	public ArrayList<MProduct> getApplicateProds(Properties ctx, int priceList_id, Timestamp date, String trxName) {
		// ArrayList<MProduct> prods = new ArrayList<MProduct>();
		
		MDiscountRuleVersion drVer = new MDiscountRuleVersion(getCtx(), this.getUY_DiscountRule_Version_ID(), trxName);
		MDiscountRule dr = new MDiscountRule(getCtx(), drVer.getUY_DiscountRule_ID(), trxName);
		
		MPriceList priceList = new MPriceList(ctx, dr.getM_PriceList_ID(), trxName);
		// *************************************************************************
		// org.compiere.model.MPriceListVersion version =
		// priceList.getPriceListVersion(date);
		//org.compiere.model.MPriceListVersion version = priceList.getVersionVigente(date);
		MPriceListVersion version = priceList.getVersionVigente(date);
		int seccion = this.getUY_Linea_Negocio_ID();
		int rubro = this.getUY_ProductGroup_ID();
		int flia = this.getUY_Familia_ID();
		int subflia = this.getUY_SubFamilia_ID();

		String sql = "SELECT * FROM M_ProductPrice WHERE M_PriceList_Version_ID = " + version.get_ID();

		String filtros = "";
		if (seccion > 0) {
			filtros = " AND M_Product_ID IN (SELECT M_Product_ID FROM M_Product ";
			filtros += "WHERE UY_Linea_Negocio_ID= " + seccion + " ";
			if (rubro > 0) {
				filtros += "  AND UY_ProductGroup_ID= " + rubro + " ";
				if (flia > 0) {
					filtros += " AND UY_Familia_ID= " + flia + " ";
					if (subflia > 0) {
						filtros += " AND UY_SubFamilia_ID= " + subflia + " ";
					}
				}
			}
			filtros += ")";
		}
		
		String prodsIds = this.getProdsIds();
		if(!prodsIds.equals("")){
			filtros += " AND M_Product_ID IN (" + prodsIds + ")";
		}

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = DB.prepareStatement(sql.concat(filtros).toString(), trxName);
			rs = pstmt.executeQuery();
			int prodID;
			while (rs.next()) {
				prodID = rs.getInt("M_Product_ID");
				MProduct prod = new MProduct(ctx, prodID, trxName);
				if (prod != null)
					m_prods.add(prod);
			}
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return m_prods;
    }
	
	private String getProdsIds(){
		String prodsIds = "";
		String sql = "select M_Product_ID from UY_DiscountRule_Prod where UY_DiscountRule_Group_ID = " + this.get_ID();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			int prodID;
			while (rs.next()) {
				prodID = rs.getInt("M_Product_ID");
				prodsIds = prodsIds + prodID + ",";
			}
			if(!prodsIds.equals("")){
				prodsIds = prodsIds.substring(0, prodsIds.length()-1);
			}
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return prodsIds;
	}
	
	/** Get UY_Linea_Negocio_ID.
	@return UY_Linea_Negocio_ID	  */
	public int getUY_Linea_Negocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Linea_Negocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Get UY_ProductGroup.
	@return UY_ProductGroup	  */
	public int getUY_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProductGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Get UY_Familia.
	@return UY_Familia	  */
	public int getUY_Familia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Familia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Get UY_SubFamilia.
	@return UY_SubFamilia	  */
	public int getUY_SubFamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SubFamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

}
