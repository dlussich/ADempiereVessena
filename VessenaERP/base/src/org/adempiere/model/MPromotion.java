/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MUOMConversion;
import org.compiere.model.X_M_Promotion;
import org.compiere.model.X_M_PromotionLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MGruposClientes;
import org.openup.model.MGruposClientesAsociados;

/**
 *
 * @author hengsin
 *
 */
public class MPromotion extends X_M_Promotion {

	private static final long serialVersionUID = 5437777366112957770L;

	public MPromotion(Properties ctx, int M_Promotion_ID, String trxName) {
		super(ctx, M_Promotion_ID, trxName);
	}

	public MPromotion(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * OpenUp.	
	 * Descripcion : 
	 * @param cBPartnerID
	 * @param mProductID
	 * @param qtyEntered
	 * @param dateTrx
	 * @return
	 * @author  Gabriel Vila ,Modificada Nicolas Garcia 27/10/2011
	 * Fecha : 17/12/2010
	 * @throws Exception 
	 */
	public static void generarPromoSimpleYCruzada(int cBPartnerID, MOrderLine line) {

		// Reseteo datos de bonificacion de la linea
		line.setuy_bonificaregla(Env.ZERO);
		line.setUY_TieneBonificCruzada(false);

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			
			//OpenUp Nicolas Sarlabos 29/08/2012, se corrige sql
			sql = "SELECT distinct m_promotionreward.m_promotionreward_id,M_PromotionDistribution.M_PromotionDistribution_ID FROM m_promotion"
					+ " JOIN m_promotionprecondition ON m_promotionprecondition.m_promotion_id=m_promotion.m_promotion_id AND m_promotionprecondition.isactive='Y'"
					+ " JOIN uy_gruposclientes ON uy_gruposclientes.uy_gruposclientes_id=m_promotionprecondition.uy_gruposclientes_id AND uy_gruposclientes.isactive='Y'"
					// Filtro Cliente
					+ " JOIN uy_gruposclientesasociados ON uy_gruposclientesasociados.uy_gruposclientes_id=uy_gruposclientes.uy_gruposclientes_id AND uy_gruposclientesasociados.c_bpartner_id="
					+ cBPartnerID
					+ " AND uy_gruposclientesasociados.isactive='Y'"
					// Filtro tipo de promo
					+ " JOIN m_promotionreward ON m_promotionreward.m_promotion_id=m_promotion.m_promotion_id AND m_promotionreward.rewardtype in('C','S') AND  m_promotionreward.isactive='Y'"
					// Filtro producto
					+ " JOIN m_promotionline ON m_promotionline.m_promotion_id=m_promotion.m_promotion_id AND m_promotionline.isactive='Y'"
					+ " JOIN m_promotiongroup ON m_promotiongroup.m_promotiongroup_id=m_promotionline.m_promotiongroup_id AND  m_promotiongroup.isactive='Y'"
					+ " JOIN m_promotiongroupline ON m_promotiongroupline.m_promotiongroup_id=m_promotiongroup.m_promotiongroup_id AND m_promotiongroupline.m_product_id="
					+ line.getM_Product_ID()
					+ " AND m_promotiongroupline.isactive='Y' JOIN m_promotiondistribution ON m_promotiondistribution .m_promotionline_id=m_promotionline.m_promotionline_id AND m_promotiondistribution.isactive='Y' WHERE m_promotion .isactive='Y'";

			//Fin OpenUp Nicolas Sarlabos 29/08/2012
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			boolean borrar = true;

			while (rs.next()) {

				if (borrar) {
					// Borro linea ya creada
					DB.executeUpdate("DELETE FROM c_orderline WHERE uy_lineapadre_id!=0 AND uy_lineapadre_id =" + line.get_ID(), line.get_TrxName());
					borrar = false;
				}

				// Instancio objetos
				MPromotionReward descuento = new MPromotionReward(line.getCtx(), rs.getInt("m_promotionreward_id"), line.get_TrxName());
				MPromotionDistribution cantDist = new MPromotionDistribution(line.getCtx(), rs.getInt("M_PromotionDistribution_ID"), line.get_TrxName());

				// Caso "C" Bonificacion Cruzada
				if (descuento.getRewardType().compareToIgnoreCase("C") == 0) {

					MPromotion.generarBonificacionCruzada(descuento, cantDist, line);

					// Caso "S" Bonificacion Simple
				} else if (descuento.getRewardType().compareToIgnoreCase("S") == 0) {

					MPromotion.generarBonificacionSimple(descuento, cantDist, line);

				}

			}

		} catch (Exception e) {

			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	/**
	 * 
	 * OpenUp.	Issue #821
	 * Descripcion :Metodo que multiplica la cantidad de la promocionSimple por la cantidad segun condicion y la suma al campo UY_BonificarRegla
	 * @param descuento
	 * @param cantDist
	 * @param line
	 * @author  Nicolas Garcia
	 * Fecha : 17/10/2011
	 */
	private static void generarBonificacionSimple(MPromotionReward descuento, MPromotionDistribution cantDist, MOrderLine line) {

		BigDecimal cantASumar = descuento.getQty().multiply(MPromotion.cantidadAfectada(descuento, cantDist, line));
		
		// Si la linea padre esta en fundas o cajas la bonificacion simple
		// tambien lo estara
		BigDecimal factor = Env.ONE;
		MProduct prod = (MProduct) line.getM_Product();

		if (line.getC_UOM_ID() != prod.getC_UOM_ID()) {
			factor = MUOMConversion.getProductRateFrom(Env.getCtx(), prod.getM_Product_ID(), prod.getC_UOM_To_ID());
		}

		line.setuy_bonificaregla(line.getuy_bonificaregla().add(cantASumar.divide(factor, 0, RoundingMode.DOWN)));

		// Marco la cantidad de padre necesaria para un hijo
		line.setUY_BonifXPadreUnHijo(cantDist.getQty());
		line.setUY_BonifCantHijo(descuento.getQty());


	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Issue #821 Genera las lineas de las bonificaciones cruzadas
	 * @param descuento
	 * @param cantDist
	 * 
	 * @param linePadre
	 * @author  Nicolas Garcia
	 * Fecha : 27/10/2011
	 */
	private static void generarBonificacionCruzada(MPromotionReward descuento, MPromotionDistribution cantDist, MOrderLine linePadre) {

		// Instancio Cabezal
		MOrder orderHdr = new MOrder(linePadre.getCtx(), linePadre.getC_Order_ID(), linePadre.get_TrxName());

		// Creo la linea
		MOrderLine line = new MOrderLine(orderHdr);

		if (descuento.getM_Product_ID() <= 0) {

			MPromotion promo = (MPromotion) descuento.getM_Promotion();
			throw new AdempiereException("Error, Debe definir en la promicion " + promo.getName() + " el producto bonificado");
		}

		if (descuento.getQty().compareTo(Env.ZERO) <= 0) {

			MPromotion promo = (MPromotion) descuento.getM_Promotion();
			throw new AdempiereException("Error, La cantidad definida en la promicion " + promo.getName() + " debe ser mayor a cero");
		}

		// La Linea es una Bonificacion
		line.setUY_EsBonificCruzada(true);

		// Quien es la linea que disparo la creacion de esta linea
		line.setUY_LineaPadre_ID(linePadre.get_ID());

		// el prodicto bonificado
		line.setM_Product_ID(descuento.getM_Product_ID());

		// FIXME Usar el de la linea que genera esta linea?
		line.setM_Warehouse_ID(linePadre.getM_Warehouse_ID());

		MProduct prod = (MProduct) descuento.getM_Product();

		//OpenUp Nicolas Sarlabos 29/08/2012 Version 2.5.1, se corrigen problemas en promociones cruzadas
		
		// Si la linea padre esta en fundas o cajas la lineas hijas tambien lo
		// estan
		//BigDecimal factor = Env.ONE;
		//MProduct prodPadre = (MProduct) linePadre.getM_Product();
		//int uomID = prod.getC_UOM_ID();

		/*if (linePadre.getC_UOM_ID() != prodPadre.getC_UOM_ID()) {
			factor = MUOMConversion.getProductRateFrom(Env.getCtx(), prodPadre.getM_Product_ID(), prodPadre.getC_UOM_To_ID());
			uomID = prod.getC_UOM_To_ID();
		}*/

		//line.setC_UOM_ID(uomID);
		line.setC_UOM_ID(prod.getC_UOM_ID());

		BigDecimal qty = MPromotion.cantidadAfectada(descuento, cantDist, linePadre);
		if (qty.compareTo(Env.ZERO) > 0) {

			// Calculo cuanto hay que sumar
			BigDecimal cantASumar = MPromotion.cantidadAfectada(descuento, cantDist, linePadre);

			//BigDecimal redondeo = cantASumar.divide(factor, 0, RoundingMode.DOWN);

			line.setuy_bonificaregla(cantASumar);

			//Fin OpenUp Nicolas Sarlabos 29/08/2012
			line.setPriceActual(Env.ZERO);
			line.setPriceCost(Env.ZERO);
			line.setPriceEntered(Env.ZERO);
			line.setPriceLimit(Env.ZERO);
			line.setPriceList(Env.ZERO);
			line.setLineNetAmt(Env.ZERO);
			line.setPrice(Env.ZERO);
			line.setuy_promodiscountmax(Env.ZERO);
			line.setuy_promodiscount(Env.ZERO);
			line.setRRAmt(Env.ZERO);

			// Marco la cantidad de padre
			line.setUY_BonifXPadreUnHijo(cantDist.getQty());
			line.setUY_BonifCantHijo(descuento.getQty());

			// Guardo la linea
			line.saveEx(linePadre.get_TrxName());

			// Marco a la linea padre diciendo que tiene bonificacion cruzada
			linePadre.setUY_TieneBonificCruzada(true);
		}
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que retorna la cantidad de veces que se tendra que aplicar la promocion Segun condicion de la promocion(>=,<=,Factor)
	 * @param descuento
	 * @param cantDist
	 * @param line
	 * @return
	 * @author  Nicolas Garcia Issue #821.
	 * Fecha : 17/10/2011
	 */
	private static BigDecimal cantidadAfectada(MPromotionReward descuento, MPromotionDistribution cantDist, MOrderLine line) {

		// Defensivo
		BigDecimal cantidad = Env.ZERO;

		// Si la linea tiene cantidad mayor a cero
		if (line.getQtyOrdered().compareTo(Env.ZERO) > 0) {

			// Si la cantidad condicion es mayor a cero
			if (cantDist.getQty().compareTo(Env.ZERO) >= 0) {

				// Caso Factor "FC"
				if (cantDist.getOperation().compareToIgnoreCase("FC") == 0) {
					//cantidad = line.getQtyOrdered().divide(cantDist.getQty(), 0, RoundingMode.DOWN).multiply(descuento.getQty());
					cantidad = line.getQtyOrdered().divide(cantDist.getQty(), 0, RoundingMode.DOWN);					

					// Caso Factor ">="
				} else if (cantDist.getOperation().compareToIgnoreCase(">=") == 0) {
					
					if (line.getQtyOrdered().compareTo(cantDist.getQty()) >= 0) {
						cantidad = Env.ONE;
					}

					// Caso Factor "<="
				} else if (cantDist.getOperation().compareToIgnoreCase("<=") == 0) {

					if (line.getQtyOrdered().compareTo(cantDist.getQty()) <= 0) {
						cantidad = Env.ONE;
					}

				}

			}
		}
		return cantidad;
	}




	/**
	 * OpenUp.	
	 * Descripcion : Obtengo lineas de una determinada promotion.
	 * @param mPromotionID
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 20/12/2010
	 */
	public static MPromotionLine[] getPromotionLines(int mPromotionID) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MPromotionLine> list = new ArrayList<MPromotionLine>();
		
		try {
			sql = "SELECT m_promotionline_id " + " FROM " + X_M_PromotionLine.Table_Name + " WHERE m_promotion_id =?";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setLong(1, mPromotionID);

			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				MPromotionLine value = new MPromotionLine(Env.getCtx(), rs.getInt(1), null);
				list.add(value);
			}
		}
 catch (Exception e) {
			throw e;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return list.toArray(new MPromotionLine[list.size()]);
	}

	
	public static MPromotionReward getReward(int cBPartnerID, int mProductID, BigDecimal qtyEntered, Timestamp dateTrx, BigDecimal importeLinea)
			throws Exception {

		// Obtengo grupos de clientes a los cuales pertenece el cliente
		MGruposClientes[] grupoClientes = MGruposClientesAsociados.getGruposCliente(cBPartnerID);
		
		MPromotionReward reward = null;

		// Para cada grupo de cliente al cual pertenece el cliente
		for (int i = 0; i < grupoClientes.length; i++) {
			MGruposClientes grupoCliente = grupoClientes[i];
			
			// Obtengo precondiciones para el grupo de clientes y ademas vigentes para la fecha de la transaccion
			MPromotionPreCondition[] preConditions = grupoCliente.getPromotionPreConditions(dateTrx);

			// Para cada promotion precondition del grupo del cliente
			for (int j = 0; j < preConditions.length; j++) {
				
				MPromotionPreCondition preCond = preConditions[j];
				
				// Obtengo promotion asociada a la precondition
				MPromotion promotion = new MPromotion(Env.getCtx(), preCond.getM_Promotion_ID(), null);
				
				if (promotion.isActive()) {
					// Obtengo promotion lines de la promotion
					MPromotionLine[] promoLines = getPromotionLines(promotion.getM_Promotion_ID());
					
					for (int h = 0; h < promoLines.length; h++) {
						MPromotionLine pline = promoLines[h];
						
						// Obtengo group segun promo line
						//MPromotionGroup pGroup = new MPromotionGroup(Env.getCtx(), pline.getM_PromotionGroup_ID(), null);
							
						// Verifico si el producto a considerar es parte de este promotion group.
						if (MPromotionGroupLine.containsProduct(mProductID, pline.getM_PromotionGroup_ID())){
							
							// Como el producto participa en esta linea de promocion, tengo que ver si cumple con la distribution
							// Obtengo distribuciones para esta linea de promocion
							MPromotionDistribution[] distributions = pline.getDistributions();
							
							// Para cada distribution
							BigDecimal cantidadDistribAprobada = Env.ZERO;
							BigDecimal descuentoAprobado = Env.ZERO;
							
							for (int k = 0; k < distributions.length; k++){
								
								MPromotionDistribution distrib = distributions[k];
								
								// Si la cantidad de distribucion es mayor a la ultima aprobada
								if (distrib.getQty().compareTo(cantidadDistribAprobada)>0){
									// Si la distribution pasa la condicion para la cantidad ingresada
									if (distrib.validQty(qtyEntered)){
										
										cantidadDistribAprobada = distrib.getQty();
							
										if (descuentoAprobado.compareTo(Env.ZERO)>0){
											reward.setAmount(reward.getAmount().subtract(descuentoAprobado));
										}
										
										// Obtengo reward para esta distribution
										MPromotionReward rewardAux = MPromotionReward.get(Env.getCtx(), distrib.getM_PromotionDistribution_ID(), distrib.getM_Promotion_ID(), null); 
										if (reward == null){
											reward = rewardAux;
										}
										else{
											
											// Aplico descuento al importe recibido por parametro
											BigDecimal valorDesc = (importeLinea.multiply(reward.getAmount()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
											importeLinea = importeLinea.subtract(valorDesc);

											// Sumo descuentos para mostrar en pantalla
											descuentoAprobado = rewardAux.getAmount();
											reward.setAmount(reward.getAmount().add(rewardAux.getAmount()));
											reward.setQty(reward.getQty().add(rewardAux.getQty()));
										}
									}
								}
							}
						}

					}		
				}
			}		
		}		
		
		if (reward!=null) reward.set_ValueOfColumn("uy_auximporte", importeLinea);
		
		return reward;
	}
	
}
