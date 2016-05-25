/**
 * 
 */
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


/**
 * @author IN Clase en que se definen las Condiciones de Negocio con el Prov -
 *         UY_DiscountRule, Header
 * 
 *
 */
public class MDiscountRule extends X_UY_DiscountRule {

	private String processMsg = null;
	private boolean justPrepared = false;
	private ArrayList<MProduct> m_prods = new ArrayList<MProduct>();
	private ArrayList<MDiscountRuleLine> m_rules = new ArrayList<MDiscountRuleLine>();

	private static final long serialVersionUID = -1251038039101422350L;

	/**
	 * @param ctx
	 * @param UY_DiscountRule_ID
	 * @param trxName
	 */
	public MDiscountRule(Properties ctx, int UY_DiscountRule_ID, String trxName) {
		super(ctx, UY_DiscountRule_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiscountRule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	//Comentado Para Borrar E. Bentancor #5690
	/*@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {

		if (!this.justPrepared) {
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,	ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// TODO: check startDate or HOY
		impactarSobreVersionVigente(this.getC_BPartner_ID(), this.getM_PriceList_ID(), this.getStartDate());

		// la nueva cond de negocio entra en vigencia, pues se crea con fecha de hoy.
		// Si hay anteriores se inactivan las anteriores

		MPriceList pl = new MPriceList(getCtx(), this.getM_PriceList_ID(), get_TrxName());
		if (pl != null) {
			int vVigente_ID = pl.getVersionVigente(getStartDate()).getM_PriceList_Version_ID();
			if (vVigente_ID > 0) {

				String sql = "UPDATE UY_DiscountRule SET IsActive = 'N' "
						+ " WHERE M_PriceList_ID = " + this.getM_PriceList_ID()
						+ " AND UY_DiscountRule_ID <> " + this.get_ID();

				DB.executeUpdateEx(sql, this.get_TrxName());
			}
		}

		// Timing After Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,	ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);

		return DocAction.STATUS_Completed;
	}
	*/
	//FIN Comentado Para Borrar E. Bentancor #5690

	/***
	 * actualiza registros en la tabla C_BPartner_Product. Obtiene la versión
	 * vigente de la lista que recibe por parámetro y si el prov. tiene reglas
	 * de negocio, las aplica a cada prod. OpenUp Ltda. Issue #4527
	 * 
	 * @author INes Fernandez - 16/07/2015
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
		
		int drVerId = this.getDRVerId();
		if(drVerId > 0){
			MDiscountRuleVersion drVersion = new MDiscountRuleVersion(getCtx(), drVerId, get_TrxName());
			drVersion.impactarSobreVersionVigente(cbpartner_id, priceList_id, date, drID);
		}else{
			if(this.getNoOfValidVer() == 1){
				drVerId = this.activateDRVersion();
				MDiscountRuleVersion drVersion = new MDiscountRuleVersion(getCtx(), drVerId, get_TrxName());
				drVersion.impactarSobreVersionVigente(cbpartner_id, priceList_id, date, drID);
			}else{
				throw new AdempiereException("No hay una version activa para la condicion");
			}
		}
		
		//Comentado para Borrar - E. Bentancor  #5690
//		impactarSobreVersionVigente(int cbpartner_id, int priceList_id, Timestamp date)
		
		/*m_prods = this.getApplicateProds(getCtx(), priceList_id, date, get_TrxName());
		
		m_rules = this.getRules(); // rules a aplicar
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
					MPriceList pl = new MPriceList(getCtx(), this.getM_PriceList_ID(), get_TrxName());
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
		}*/

	}
	
	
	/***
	 * Trae la version de Reglas Vigentes
	 * 
	 * @author E. Bentancor - 07/04/2016
	 * @see
	 * @param trxName
	 * @return
	 */
	private int getDRVerId(){
		int plVId = 0;
		String sql = "select UY_DiscountRule_Version_ID from UY_DiscountRule_Version "
						+ " where isValid = 'Y' and UY_DiscountRule_ID = " + this.get_ID();
		
		DB.getSQLValue(get_TrxName(), sql);
		
		return plVId;
	}
	
	/***
	 * Activa lista solo cuando existe una sola version
	 * Retorna el id de la version activada
	 * 
	 * @author E. Bentancor - 07/04/2016
	 * @see
	 * @param trxName
	 * @return
	 */
	private int activateDRVersion(){
		int drVersionID = 0;
		if(this.getNoOfValidVer()>1) throw new AdempiereException("No se puede activar regla automaticamente, hay mas de una version!");

		String sql = "select UY_DiscountRule_Version_ID from UY_DiscountRule_Version "
						+ "where isValid = 'Y' and UY_DiscountRule_ID = " + this.get_ID();
		
		drVersionID = DB.getSQLValue(get_TrxName(), sql);
		if(drVersionID > 0){
			MDiscountRuleVersion drVersion = new MDiscountRuleVersion(getCtx(), drVersionID, get_TrxName());
			drVersion.setIsValid(true);
		}
		return drVersionID;
	}
	
	/***
	 * Trae la version de Reglas Vigentes
	 * 
	 * @author E. Bentancor - 07/04/2016
	 * @see
	 * @param trxName
	 * @return
	 */
	private int getNoOfValidVer(){
		int plVId = 0;
		String sql = "select count(*) from UY_DiscountRule_Version where isValid = 'N' and UY_DiscountRule_ID = " + this.get_ID();
		
		DB.getSQLValue(get_TrxName(), sql);
		
		return plVId;
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
	public void loadPOCyPF(MPriceLoadLine line) {
		// impacto registros en la C_BPartner_Product de los productos de la versión vigente que cumplen con los filtros
		
		int drVerId = this.getDRVerId();
		if(drVerId > 0){
			MDiscountRuleVersion drVersion = new MDiscountRuleVersion(getCtx(), drVerId, get_TrxName());
			drVersion.loadPOCyPF(line, this.getM_PriceList_ID());
		}else{
			if(this.getNoOfValidVer() == 1){
				drVerId = this.activateDRVersion();
				MDiscountRuleVersion drVersion = new MDiscountRuleVersion(getCtx(), drVerId, get_TrxName());
				drVersion.loadPOCyPF(line, this.getM_PriceList_ID());
			}else{
				throw new AdempiereException("No hay una version vigente para la condicion");
			}
		}
		
		/* Comentado para Borrar - E. Bentancor  #5690
		//m_prods = this.getApplicateProds(getCtx(), priceList_id, date, get_TrxName());
		m_rules = this.getRules(); // rules a aplicar
		if (m_rules.size() > 0) {

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
				MPriceList pl = new MPriceList(getCtx(), this.getM_PriceList_ID(), get_TrxName());
				if(pl!=null) round = pl.getPricePrecision();
				
				// impacta dtos sobre los precios
				line.set_ValueOfColumn("pricepo", kBS.multiply(kOp).multiply(kFEF).multiply(line.getPriceList()).setScale(round, RoundingMode.HALF_UP)); // sólo los que aplican a la orden de compra
				line.set_ValueOfColumn("pricecostfinal", kBS.multiply(kOp).multiply(kFEF.multiply(kFFF.multiply(kAP.multiply(kRet)))).multiply(line.getPriceList())
						.setScale(round, RoundingMode.HALF_UP));
				
			
		} else {
			// TODO: TIRAR AVISOS no hay productos o no hay reglas para aplicar?
		}*/

	}

	//Comentado Para Borrar E. Bentancor #5690
	/*@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}*/
	//FIN Comentado Para Borrar E. Bentancor #5690

	// // Trae los prods filtrados en la grilla asociada a este header
	// // //***********EN DESUSO************
	// public ArrayList<MProduct> getProdsFromDiscountRuleProd() {
	// if (m_prods != null) {
	// // set_TrxName(m_prods, get_TrxName());
	// return m_prods;
	// }
	// ResultSet rs = null;
	// PreparedStatement pstmt = null;
	// String sql =
	// "SELECT UY_Product_ID FROM UY_DiscountRuleProd WHERE UY_DiscountRule_ID= "
	// + this.get_ID() + " AND IsSelected = 'Y'";
	// try {
	// pstmt = DB.prepareStatement(sql, null); // todo: check no trx
	// rs = pstmt.executeQuery();
	// while (rs.next()) {
	// MProduct prod = new MProduct(getCtx(),
	// rs.getInt("UY_Product_ID"), get_TrxName());
	// m_prods.add(prod);
	// }
	// return m_prods;
	// } catch (Exception e) {
	// throw new AdempiereException(e.getMessage());
	// } finally {
	// DB.close(rs, pstmt);
	// rs = null;
	// pstmt = null;
	// }
	// }

	/***
	 * Obtiene y retorna las reglas OpenUp Ltda. Issue #4527
	 * 
	 * @author INes Fernandez - 16/07/2015
	 * @see
	 * @param ctx
	 * @param date
	 *            : fecha para la que se consulta
	 * @param trxName
	 * @return
	 */
	public ArrayList<MDiscountRuleLine> getRules() {

		if (m_prods != null) {
			// set_TrxName(m_prods, get_TrxName());
			// return m_rules;
		}
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM UY_DiscountRuleLine WHERE UY_DiscountRule_ID= " + this.get_ID();
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
	 * Obtiene y retorna los prods de la version VIGENTE (a la fecha que recibe
	 * por param) de la lista de precios que recibe por parám, que pasan los
	 * filtros seteados para esta cond de negocio. OpenUp Ltda. Issue #4527
	 * 
	 * @author INes Fernandez - 16/07/2015
	 * @see
	 * @param ctx
	 * @param date
	 *            : fecha para la que se consulta
	 * @param trxName
	 * @return
	 */
	public ArrayList<MProduct> getApplicateProds(Properties ctx, int priceList_id, Timestamp date, String trxName) {
		// ArrayList<MProduct> prods = new ArrayList<MProduct>();
		MPriceList priceList = new MPriceList(ctx, this.getM_PriceList_ID(), trxName);
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

	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//E. Bentancor Issue #5690
		//Valida que la lista no este asociada a otra condicion
		String sql = "select 1 from UY_DiscountRule where m_pricelist_id = " + this.getM_PriceList_ID();
		
		if(DB.getSQLValue(get_TrxName(), sql) == 1){
			throw new AdempiereException("La lista seleccionada ya esta asocidad a otra condicion");
		}
		
		return true;
	}	
	
	
}
