package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartnerProduct;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MDiscountRuleVersion extends X_UY_DiscountRule_Version {
	
	private ArrayList<MProduct> m_prods = new ArrayList<MProduct>();
	private ArrayList<MDiscountRuleLine> m_rules = new ArrayList<MDiscountRuleLine>();
	private ArrayList<MDiscountRuleGroup> m_groups = new ArrayList<MDiscountRuleGroup>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 4315093831432152951L;

	public MDiscountRuleVersion(Properties ctx, int UY_DiscountRule_Version_ID, String trxName) {
		super(ctx, UY_DiscountRule_Version_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiscountRuleVersion(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/***
	 * Calcula el Precio OC y Precio Final
	 * 
	 * @author E. Bentancor - 17/03/2016
	 * @see
	 * @return
	 */
	/***
	 * actualiza registros en la tabla C_BPartner_Product. Obtiene la versión
	 * vigente de la lista que recibe por parámetro y si el prov. tiene reglas
	 * de negocio, las aplica a cada prod. OpenUp Ltda. Issue #5609
	 * 
	 * @author E. Bentancor - 07/04/2016
	 * @see
	 * @param ctx
	 * @param cbpartner_id
	 *            : proveedor
	 * @param priceList_id
	 *            : lista de precio
	 * @param date
	 *            : fecha para vigencia
	 * @param trxName
	 * @return
	 */
	public void impactarSobreVersionVigente(int cbpartner_id, int priceList_id, Timestamp date, int drID) {
		// impacto registros en la C_BPartner_Product de los productos de la versión vigente que cumplen con los filtros
		MDiscountRule dr = new MDiscountRule(getCtx(), this.getUY_DiscountRule_ID(), get_TrxName());
		int plId = dr.getM_PriceList_ID();
		if(plId > 0){
			m_groups = this.getGroups();
			
			if(m_groups != null && m_groups.size() > 0){
				for (MDiscountRuleGroup groupAux : m_groups) {
					m_prods = groupAux.getApplicateProds(getCtx(), priceList_id, date, get_TrxName());
					
					m_rules = this.getRules(groupAux.get_ID()); // rules a aplicar
					if (m_prods != null && m_rules != null && m_prods.size() > 0 && m_rules.size() > 0) {
						for (MProduct prodAux : m_prods) {
							

							MBPartnerProduct mbpp = MBPartnerProduct.forBPProduct(getCtx(), cbpartner_id, prodAux.get_ID(), get_TrxName());

							if (mbpp != null) { // encontró registro, actualizo
								// OBS: si no encontró algo anda mal, pues cuando al prod se le asigna un precio se debe impactar en esta tabla
								// limpia los campos relativos a las rules
								mbpp.clearCondNegocio();
								mbpp.saveEx();

								// calcula el % que aplica para cada tipo de dto
								BigDecimal dOp, dFEF, dFFF, dAP, dRet, dBS; // % de dto para cada tipo de dto
								dOp = dFEF = dFFF = dAP = dRet = dBS = BigDecimal.ZERO;
								BigDecimal kOp, kFEF, kFFF, kAP, kRet, kBS; // factores multiplicadores para cada tipo de dto
								kOp = kFEF = kFFF = kAP = kRet = kBS = BigDecimal.ONE;
								BigDecimal bsQty1, bsQty2, bsQtyAux;
								bsQty1 = bsQty2 = BigDecimal.ONE;

								for (MDiscountRuleLine rAux : m_rules) {
									BigDecimal disc = rAux.getDiscount();
									BigDecimal k = BigDecimal.ONE.add(disc.divide(BigDecimal.valueOf(-100)));

									if (rAux.isOperativo()) kOp = kOp.multiply(k);
									if (rAux.isFinancEnFact()) kFEF = kFEF.multiply(k);
									if (rAux.isFinancFueraFact()) kFFF = kFFF.multiply(k);
									if (rAux.isAlPago()) kAP = kAP.multiply(k);
									if (rAux.isRetono()) {
										kRet = kRet.multiply(k);
										mbpp.setUY_IsRetorno(true); // actualmente en desuso
									}
									if (rAux.isBonificS()) mbpp.setUY_IsBonifS(true);
									if (rAux.isBonificC()) mbpp.setUY_IsBonifC(true);
									
									if(rAux.isBonificS()){
										BigDecimal kAux = new BigDecimal(100);
										bsQty1 = rAux.getQtySource1();
										bsQty2 = rAux.getQtySource2();
										bsQtyAux = bsQty1.add(bsQty2);
										bsQtyAux = mbpp.getPrice().multiply(bsQty1).setScale(2, RoundingMode.HALF_UP).divide(bsQtyAux, 2, RoundingMode.HALF_UP);
										bsQtyAux = bsQtyAux.multiply(kAux).divide(mbpp.getPrice(), 2, RoundingMode.HALF_UP);
										kBS = bsQtyAux.divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
									}

								}
								// Setea descuentos

						
								dOp = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kOp))).multiply(BigDecimal.valueOf(100));
								mbpp.setUY_DtoOperativo(dOp);

								dFEF = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kFEF))).multiply(BigDecimal.valueOf(100));
								mbpp.setUY_DtoFinancFact(dFEF);

								dFFF = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kFFF))).multiply(BigDecimal.valueOf(100));
								mbpp.setUY_DtoFinancFueraFact(dFFF);

								dAP = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kAP))).multiply(BigDecimal.valueOf(100));
								mbpp.setUY_DtoFianancAlPago(dAP);

								dRet = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kRet))).multiply(BigDecimal.valueOf(100));
								mbpp.setUY_PorcentajeRetorno(dRet);

								//obtiene la precisión de la lista de precios origen, si no la encuentra setea 2
								int round= 2;
								MPriceList pl = new MPriceList(getCtx(), plId, get_TrxName());
								if(pl!=null) round = pl.getPricePrecision();
								
								// impacta dtos sobre los precios
								mbpp.setPricePO(kBS.multiply(kOp).multiply(kFEF).multiply(mbpp.getPrice()).setScale(round, RoundingMode.HALF_UP)); // sólo los que aplican a la orden de compra
								mbpp.setPriceCostFinal(kBS.multiply(kOp).multiply(kFEF.multiply(kFFF.multiply(kAP.multiply(kRet)))).multiply(mbpp.getPrice())
										.setScale(round, RoundingMode.HALF_UP));

								mbpp.saveEx();
								
							}
						}
					} else {
						// TODO: TIRAR AVISOS no hay productos o no hay reglas para aplicar?
					}
				}
				
			}
		}else{
			throw new AdempiereException("La condicion no tiene lista de precios asociada");
		}	
	}
	
	/***
	 * Calcula el Precio OC y Precio Final
	 * 
	 * @author E. Bentancor - 17/03/2016
	 * @see
	 * @param ctx
	 * @param cbpartner_id
	 *            : proveedor
	 * @param priceList_id
	 *            : lista de precio
	 * @param date
	 *            : fecha para vigencia
	 * @param trxName
	 * @return
	 */
	public void loadPOCyPF(MPriceLoadLine line, int plId) {
		// impacto registros en la C_BPartner_Product de los productos de la versión vigente que cumplen con los filtros
		

		if(m_groups != null && m_groups.size() > 0){
			MDiscountRuleGroup groupAux = m_groups.get(0);
			m_rules = this.getRules(groupAux.get_ID()); // rules a aplicar
			if (m_rules != null && m_rules.size() > 0) {
				// calcula el % que aplica para cada tipo de dto
				BigDecimal dOp, dFEF, dFFF, dAP, dRet, dBS; // % de dto para cada tipo de dto
				dOp = dFEF = dFFF = dAP = dRet = dBS = BigDecimal.ZERO;
				BigDecimal kOp, kFEF, kFFF, kAP, kRet, kBS; // factores multiplicadores para cada tipo de dto
				kOp = kFEF = kFFF = kAP = kRet = kBS = BigDecimal.ONE;
				BigDecimal bsQty1, bsQty2, bsQtyAux;
				bsQty1 = bsQty2 = BigDecimal.ONE;

				for (MDiscountRuleLine rAux : m_rules) {
					BigDecimal disc = rAux.getDiscount();
					BigDecimal k = BigDecimal.ONE.add(disc.divide(BigDecimal.valueOf(-100)));

					if (rAux.isOperativo()) kOp = kOp.multiply(k);
					if (rAux.isFinancEnFact()) kFEF = kFEF.multiply(k);
					if (rAux.isFinancFueraFact()) kFFF = kFFF.multiply(k);
					if (rAux.isAlPago()) kAP = kAP.multiply(k);
					if (rAux.isRetono()) {
						kRet = kRet.multiply(k);
					}
					if(rAux.isBonificS()){
						BigDecimal kAux = new BigDecimal(100);
						bsQty1 = rAux.getQtySource1();
						bsQty2 = rAux.getQtySource2();
						bsQtyAux = bsQty1.add(bsQty2);
						bsQtyAux = line.getPriceList().multiply(bsQty1).setScale(2, RoundingMode.HALF_UP).divide(bsQtyAux, 2, RoundingMode.HALF_UP);
						bsQtyAux = bsQtyAux.multiply(kAux).divide(line.getPriceList(), 2, RoundingMode.HALF_UP);
						kBS = bsQtyAux.divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
					}

				}
				// Setea descuentos

		
				dOp = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kOp))).multiply(BigDecimal.valueOf(100));

				dFEF = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kFEF))).multiply(BigDecimal.valueOf(100));

				dFFF = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kFFF))).multiply(BigDecimal.valueOf(100));

				dAP = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kAP))).multiply(BigDecimal.valueOf(100));

				dRet = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kRet))).multiply(BigDecimal.valueOf(100));
				
				dBS = (BigDecimal.ONE.add(BigDecimal.valueOf(-1).multiply(kBS))).multiply(BigDecimal.valueOf(100));

				//obtiene la precisión de la lista de precios origen, si no la encuentra setea 2
				int round= 2;
				MPriceList pl = new MPriceList(getCtx(), plId, get_TrxName());
				if(pl!=null) round = pl.getPricePrecision();
				
				// impacta dtos sobre los precios
				line.set_ValueOfColumn("pricepo", kBS.multiply(kOp).multiply(kFEF).multiply(line.getPriceList()).setScale(round, RoundingMode.HALF_UP)); // sólo los que aplican a la orden de compra
				line.set_ValueOfColumn("pricecostfinal", kBS.multiply(kOp).multiply(kFEF.multiply(kFFF.multiply(kAP.multiply(kRet)))).multiply(line.getPriceList())
						.setScale(round, RoundingMode.HALF_UP));
			}
		}
	}
	
	/***
	 * Obtiene y retorna las reglas OpenUp Ltda. Issue #5690
	 * 
	 * @author E. Bentancor - 07/04/2015
	 * @return
	 */
	public ArrayList<MDiscountRuleLine> getRules(int groupID) {

		if (m_prods != null) {
			// set_TrxName(m_prods, get_TrxName());
			// return m_rules;
		}
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM UY_DiscountRuleLine WHERE UY_DiscountRule_Group_ID = " + groupID;
		try {
			pstmt = DB.prepareStatement(sql, null); // todo: check no trx
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MDiscountRuleLine rule = new MDiscountRuleLine(getCtx(), rs.getInt("UY_DiscountRuleLine_ID"), get_TrxName());
				m_rules.add(rule);
			}
			return m_rules;
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}
	
	
	/***
	 * Obtiene y retorna los Grupos de Reglas OpenUp Ltda. Issue #5690
	 * 
	 * @author E. Bentancor - 07/04/2016
	 * @see
	 * @return
	 */
	public ArrayList<MDiscountRuleGroup> getGroups() {

		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "select UY_DiscountRule_Group_ID from UY_DiscountRule_Group"
						+ " where UY_DiscountRule_Version_ID = " + this.get_ID()
						+ " order by created asc";
		try {
			pstmt = DB.prepareStatement(sql, null); // todo: check no trx
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MDiscountRuleGroup group = new MDiscountRuleGroup(getCtx(), rs.getInt("UY_DiscountRule_Group_ID"), get_TrxName());
				m_groups.add(group);
			}
			return m_groups;
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
	

}
