package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;


public class MEstandarProducciones extends X_UY_Estandar_Producciones{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4425223288619167460L;


	public MEstandarProducciones(Properties ctx, int UY_Estandar_Producciones_ID,
			String trxName) {
		super(ctx, UY_Estandar_Producciones_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		//se calcula productividad teorica
		String sql = "";
		BigDecimal qty = Env.ZERO;
		//BigDecimal finalQty = Env.ZERO;
		
		//OpenUp. Guillermo Brust. 22/08/2012 ISSUE #9. version 2.5.1
		//Se hace la suma de cantidad de opererios directos mas la mitad de cantidad de operarios indirectos
		
		/*int operarios = this.getUY_Cantidad_Operarios_Dir() + this.getUY_Cantidad_Operarios_Ind();
								
		if (operarios > 0 && this.getuy_unidades_hora() > 0){
			
			BigDecimal uH = new BigDecimal(this.getuy_unidades_hora());
			BigDecimal op = new BigDecimal(operarios);
			BigDecimal res = uH.divide(op,2,BigDecimal.ROUND_HALF_UP);
			*/
		
		BigDecimal uH = new BigDecimal(this.getuy_unidades_hora());		
		BigDecimal opDirecto = new BigDecimal(this.getUY_Cantidad_Operarios_Dir());
		BigDecimal opIndirecto = new BigDecimal(this.getUY_Cantidad_Operarios_Ind());
		
		BigDecimal operarios = opDirecto.add(opIndirecto.divide(new BigDecimal(2)));		
		
						
		if ((operarios.compareTo(BigDecimal.ZERO) > 0) && (uH.compareTo(BigDecimal.ZERO) > 0)){		
			
			//fin ISSUE #9
			
			BigDecimal res = uH.divide(operarios, 1, BigDecimal.ROUND_HALF_UP);
						
			this.setuy_product_teorica(res);
					
		}else this.setuy_product_teorica(Env.ZERO);
		
		//se calcula unidades x hora x operario 
		//OpenUp Nicolas Sarlabos #1042 09/07/2012
		if (this.getM_Product_ID() > 0) {

			sql = "select coalesce(sum(l.qtybom),0)" +
				  " from pp_product_bomline l" +
				  " inner join pp_product_bom hdr on l.pp_product_bom_id=hdr.pp_product_bom_id" +
				  " inner join m_product prod on hdr.m_product_id=prod.m_product_id" +
				  " where hdr.m_product_id=" + this.getM_Product_ID() + " and l.m_product_id in (select pl.m_product_id" + 
				  " from uy_parametrosstdprodline pl" + 
				  " inner join uy_parametrosstdprodhdr phdr on pl.uy_parametrosstdprodhdr_id=phdr.uy_parametrosstdprodhdr_id" +
				  " where phdr.m_product_category_id=prod.m_product_category_id)";

			//OpenUp. Nicolas Sarlabos. 28/10/2012.
			qty = DB.getSQLValueBDEx(get_TrxName(), sql);
			if(qty.compareTo(Env.ZERO)>0){
				qty = Env.ONE.divide(qty, 2,RoundingMode.HALF_UP);
				this.setUY_UnidHoraOp(qty);
			}
			//Fin OpenUp	
			
		}
		//fin OpenUp Nicolas Sarlabos #1042 09/07/2012
			
		//OpenUp Nicolas Sarlabos 19/01/2012
		//se controla que no se repitan registros con igual producto/linea
		if(newRecord){
			sql = "SELECT count(m_product_id) FROM UY_Estandar_Producciones" + " WHERE m_product_id=" + this.getM_Product_ID() + " AND s_resource_id="
					+ this.getS_Resource_ID();

			int res = DB.getSQLValue(get_TrxName(), sql);

			if (res > 0) throw new AdempiereException("Ya existe un registro para éste producto y línea");
			//fin OpenUp Nicolas Sarlabos 19/01/2012
		}
		
		return true;
		
	}
	
	

}
