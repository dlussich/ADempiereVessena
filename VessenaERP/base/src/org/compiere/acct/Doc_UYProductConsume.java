/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 25/07/2014
 */
package org.compiere.acct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MProduct;
import org.compiere.model.X_M_Product_Acct;
import org.compiere.util.Env;
import org.openup.model.I_UY_ProductConsume;
import org.openup.model.MProductConsume;
import org.openup.model.MProductConsumeLine;
import org.openup.model.MStockConsume;
import org.openup.model.MStockConsumeLine;

/**
 * org.compiere.acct - Doc_UYProductConsume
 * OpenUp Ltda. Issue #1405 
 * Description: Contabilizacion de consumos de stock de productos.
 * @author Gabriel Vila - 25/07/2014
 * @see
 */
public class Doc_UYProductConsume extends Doc {

	/**
	 * Constructor.
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UYProductConsume(MAcctSchema[] ass, Class<?> clazz,
			ResultSet rs, String defaultDocumentType, String trxName) {
		super(ass, clazz, rs, defaultDocumentType, trxName);
	}
	
	
	/**
	 * Constructor
	 * @param ass
	 *            accounting schemata
	 * @param rs
	 *            record
	 * @param trxName
	 *            trx
	 */
	public Doc_UYProductConsume(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MProductConsume.class, rs, null, trxName);
	}


	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/07/2014
	 * @see
	 * @return
	 */
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MProductConsume) getPO()).getMovementDate());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/07/2014
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 25/07/2014
	 * @see
	 * @param as
	 * @return
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		Fact fact = new Fact(this, as, Fact.POST_Actual);
		ArrayList<Fact> facts = new ArrayList<Fact>();

		MProductConsume header = (MProductConsume)getPO();
		List<MProductConsumeLine> lines = header.getLines();
		
		FactLine fl = null;
		
		// DR - Lineas - Cuenta consumo de stock (sube)
		// CR - Lineas - Cuenta compras (baja)
		for (MProductConsumeLine line: lines){

			// Obtengo cuentas contables
			MProduct prod = (MProduct)line.getM_Product();
			X_M_Product_Acct prodAcct = prod.getProductAccounting();

			if (prodAcct == null){
				p_Error = "No se pudo obtener cuentas para el producto : " + prod.getName();
				fact = null;
				facts.add(fact);
				return facts;
			}
			
			MAccount acctProdCompras = MAccount.get(getCtx(), prodAcct.getP_InventoryClearing_Acct());
			MAccount acctProdConsumo = MAccount.get(getCtx(), prodAcct.getConsume_Acct_ID());
			
			//OpenUp Santiago Evans 18/03/2016 issue #5182
			// Se busca, en caso de que no tenga cuentas configuradas en la contabilidad del producto
			// las cuentas configuradas en la categoría del mismo
			if(acctProdCompras == null){
				prodAcct = prod.getProductCategoryAccounting();
				acctProdCompras = MAccount.get(getCtx(), prodAcct.getP_InventoryClearing_Acct());
			}
			
			if(acctProdConsumo.get_ID() == 0){
				prodAcct = prod.getProductCategoryAccounting();
				acctProdConsumo = MAccount.get(getCtx(), prodAcct.getConsume_Acct_ID());
			}
			
			
			// Si tengo cuenta de consumo hago asiento, sino asumo que es un producto que no activa stock y por lo tanto no hago asiento.
			if ((acctProdConsumo != null) && (acctProdConsumo.get_ID() > 0)){

				// Obtengo partidas consumidas por esta linea de stock
				List<MStockConsumeLine> consLines = MStockConsumeLine.getLines(getCtx(), I_UY_ProductConsume.Table_ID, line.get_ID(), getPO().get_TrxName());
				
				if (consLines != null){

					// Para cada partida consumida por este pro
					for (MStockConsumeLine consLine: consLines){

						MStockConsume consHdr = (MStockConsume)consLine.getUY_StockConsume();
						BigDecimal amtSource = consHdr.getAmtSource().multiply(consLine.getMovementQty()).setScale(2, RoundingMode.HALF_UP);
						
						// DR
						fl = fact.createLine(null, acctProdConsumo, consHdr.getC_Currency_ID(), amtSource, null, header.getC_DocType_ID(), header.getDocumentNo());
						
						if (fl != null){
							fl.setC_Activity_ID(header.getC_Activity_ID());
							fl.setC_Activity_ID_1(header.getC_Activity_ID());
							fl.setM_Product_ID(line.getM_Product_ID());
							fl.saveEx();
						}

						// CR 
						fl = fact.createLine(null, acctProdCompras, consHdr.getC_Currency_ID(), null, amtSource, header.getC_DocType_ID(), header.getDocumentNo());

						if (fl != null){
							fl.setC_Activity_ID(header.getC_Activity_ID());
							fl.setC_Activity_ID_1(header.getC_Activity_ID());
							fl.setM_Product_ID(line.getM_Product_ID());
							fl.saveEx();
						}
					
					} // for
				}
			}
			
		}
		
		// Redondeo.
		fact.createRounding(header.getC_DocType_ID(), header.getDocumentNo(), 0, 0, 0, 0);

		facts.add(fact);
		return facts;
		
	}

}
