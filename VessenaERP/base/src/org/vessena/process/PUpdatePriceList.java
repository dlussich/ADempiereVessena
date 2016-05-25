/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceListVersion;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class PUpdatePriceList extends SvrProcess {
	
	private int userID = 0;
	private int priceListID = 0;
	private int familiaID = 0;
	private int subFamiliaID = 0;
	private BigDecimal porcentaje = Env.ZERO;
	private BigDecimal importe = Env.ZERO;
	private boolean newVersion = false;

	/**
	 * 
	 */
	public PUpdatePriceList() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.userID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("M_PriceList_ID")){
					this.priceListID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("UY_Familia_ID")){
					
					if(para[i].getParameter()!=null) this.familiaID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("UY_SubFamilia_ID")){
					
					if(para[i].getParameter()!=null) this.subFamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("porcentaje")){
					this.porcentaje = (BigDecimal)para[i].getParameter();
				}
				if (name.equalsIgnoreCase("amount")){
					this.importe = (BigDecimal)para[i].getParameter();
				}
				if (name.equalsIgnoreCase("IsGenerated")){
					String value = (String)para[i].getParameter();
					
					if(value.equalsIgnoreCase("Y")) this.newVersion = true;
				}
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {

		String sql = "", insert = "", message = "OK";

		MPriceListVersion originalVersion = null;
		MPriceListVersion newVersion = null;

		if(this.porcentaje.compareTo(Env.ZERO)!=0 && this.importe.compareTo(Env.ZERO)!=0) throw new AdempiereException("Debe indicar porcentaje o importe, pero no ambos");
		
		if(this.porcentaje.compareTo(Env.ZERO)==0 && this.importe.compareTo(Env.ZERO)==0) throw new AdempiereException("Importe o porcentaje debe ser diferente de cero");

		sql = "select v.m_pricelist_version_id" +
				" from m_pricelist_version v" +
				" inner join m_pricelist l on v.m_pricelist_id = l.m_pricelist_id" +	
				" where l.m_pricelist_id = " + this.priceListID + " and v.isactive = 'Y'" +
				" order by v.validfrom desc";
		int versionID = DB.getSQLValueEx(get_TrxName(), sql);

		if(versionID > 0){

			originalVersion = new MPriceListVersion(getCtx(),versionID,get_TrxName()); //instancio la version original

		} else throw new AdempiereException("No se pudo obtener ultima version de la lista de precios seleccionada");

		if(this.newVersion){					

			newVersion = new MPriceListVersion(getCtx(),0,get_TrxName()); //creo nueva version de lista
			newVersion.setM_PriceList_ID(this.priceListID);
			newVersion.setName(new Timestamp(System.currentTimeMillis()).toString());
			newVersion.setM_DiscountSchema_ID(originalVersion.getM_DiscountSchema_ID());
			newVersion.setValidFrom(new Timestamp(System.currentTimeMillis()));
			newVersion.saveEx();

			insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
					"updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad)";

			sql = "select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + this.userID + ",now()," + this.userID + 
					",pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad" +
					" from m_productprice" +
					" where isactive = 'Y' and m_pricelist_version_id = " + versionID;

			DB.executeUpdateEx(insert + sql, get_TrxName());

			updatePrice(newVersion); //actualizo precios de la nueva version	
			
			//inactivo la version anterior
			DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id = " + versionID, get_TrxName());

		} else updatePrice(originalVersion); //actualizo precios de la version actual

		return message;		
		
	}
	
	private void updatePrice(MPriceListVersion version){
	
		String sql = "", where = "";

		if(this.familiaID > 0) where = " and m_product_id in (select m_product_id from m_product where uy_familia_id = " + this.familiaID + ")"; 

		if(this.subFamiliaID > 0) where = " and m_product_id in (select m_product_id from m_product where uy_familia_id = " + this.familiaID + " and uy_subfamilia_id = " + this.subFamiliaID + ")"; 

		if(this.porcentaje.compareTo(Env.ZERO)!=0){ //tengo porcentaje

			BigDecimal porcentaje = (this.porcentaje.divide(Env.ONEHUNDRED, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
			
			sql = "update m_productprice set pricelimit = pricelimit + (pricelimit * " + porcentaje + ")," +
			      " pricelist = pricelist + (pricelist * " + porcentaje + ")," +
				  " pricestd = pricestd + (pricestd * " + porcentaje + ")" +
			      " where m_pricelist_version_id = " + version.get_ID() + where;

			DB.executeUpdate(sql, get_TrxName());

		} else if (this.importe.compareTo(Env.ZERO)!=0){ //tengo importe
			
			sql = "update m_productprice set pricelimit = pricelimit + " + this.importe + "," +
			      " pricelist = pricelist + " +  this.importe + "," +
				  " pricestd = pricestd + " + this.importe +
				  " where m_pricelist_version_id = " + version.get_ID() + where;

			DB.executeUpdate(sql, get_TrxName());
			
		}				
		
	}	

}
