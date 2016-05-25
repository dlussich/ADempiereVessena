package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCharge;
import org.compiere.model.MClient;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MTRClearing;
import org.openup.model.MTRInvoiceTire;
import org.openup.model.MTRTire;
import org.openup.model.MTRTireDrop;

/***
 * Clase para contabilización de Baja de Neumaticos en Transporte.
 * org.compiere.acct - Doc_UYTRTireDrop
 * OpenUp Ltda. Issue #5183 
 * Description: 
 * @author sevans - Feb 26, 2016
 */
public class Doc_UYTRTireDrop extends Doc {

	/**
	 * 
	 * OpenUp. issue #5183	
	 * Descripcion : 
	 * @return
	 * @author  Santiago Evans 
	 * Fecha : 26/2/2016
	 */
	private MTRTireDrop	TireDrop = null;
	
	
	public Doc_UYTRTireDrop(MAcctSchema[] ass, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(ass, clazz, rs, defaultDocumentType, trxName);
	}

	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */	
	public Doc_UYTRTireDrop(MAcctSchema[] ass, ResultSet rs, String trxName) {
		super(ass, MTRTireDrop.class, rs, null, trxName);
	}	
	
	@Override
	protected String loadDocumentDetails() {
		setDateDoc(((MTRTireDrop) getPO()).getDateTrx());		
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {

		Fact fact = new Fact(this, as, Fact.POST_Actual);
		ArrayList<Fact> facts = new ArrayList<Fact>();
		MAccount account = null;
		int prodID = 0;
		
		
		// Modelo desde PO
		this.TireDrop = (MTRTireDrop)this.getPO();	
		
		int tireID = this.TireDrop.getUY_TR_Tire_ID();
		
 		MTRTire tire =  new MTRTire(getCtx(), tireID, getTrxName());
		//Traigo el id de producto
		try {
			prodID = this.getMProductID(tire.getUY_TR_TireMark_ID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		MProduct prod = new MProduct(getCtx(), prodID, getTrxName());
		
		// Obtengo esquema para saber cual es la moneda nacional.
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		//Obtengo la cuenta
		MAccount acc = null;
		MAccount accConsumo = null;
		//Controlo si se da de baja por venta o rotura
		if (this.TireDrop.getMotivo().equalsIgnoreCase("venta") ){
			acc = prod.getAccount("p_inventoryclearing_acct", as);
			accConsumo = prod.getAccount("p_cogs_acct", as);
			//controlo que haya cuentas asociadas
			if(acc != null){
				if(accConsumo != null){
					//controlo que el precio no sea 0
					if(tire.getPrice().add(tire.getPrice2()).compareTo(BigDecimal.ZERO) > 0){
						//Se suma el valor del neumatico mas el de los recauchutajes			
						FactLine fl = fact.createLine(null, acc, schema.getC_Currency_ID(), null, tire.getPrice().add(tire.getPrice2()) , this.getC_DocType_ID(), this.getDocumentNo());
						fl.setAmtAcctCr(tire.getPrice());
						fact.add(fl);			
						fl = fact.createLine(null, accConsumo, schema.getC_Currency_ID(), tire.getPrice().add(tire.getPrice2()), null,  this.getC_DocType_ID(), this.getDocumentNo());
						fl.setAmtAcctDr(tire.getPrice());
						fact.add(fl);
					}
				} else throw new AdempiereException ("No hay una cuenta de costo de ventas asignada al neumatico");
			}else throw new AdempiereException ("No hay una cuenta de compras asignada al neumatico");
			
		}else{
			acc = prod.getAccount("p_inventoryclearing_acct", as);
			accConsumo = prod.getAccount("Consume_Acct_ID", as);
			//controlo que haya cuentas asociadas
			if(acc != null){	
				if(accConsumo != null){				
					//controlo que el precio no sea 0
					if(tire.getPrice().add(tire.getPrice2()).compareTo(BigDecimal.ZERO) > 0){			
						//Se suma el valor del neumatico mas el de los recauchutajes
						FactLine fl = fact.createLine(null, acc, schema.getC_Currency_ID(), null, tire.getPrice().add(tire.getPrice2()) , this.getC_DocType_ID(), this.getDocumentNo());
						fl.setAmtAcctCr(tire.getPrice());
						fact.add(fl);			
						fl = fact.createLine(null, accConsumo, schema.getC_Currency_ID(), tire.getPrice().add(tire.getPrice2()), null,  this.getC_DocType_ID(), this.getDocumentNo());
						fl.setAmtAcctDr(tire.getPrice());
						fact.add(fl);
					}
				} else throw new AdempiereException ("No hay una cuenta de consumo de activos asignada al neumatico");
			} else throw new AdempiereException ("No hay una cuenta de compras asignada al neumatico");
		}
		
		facts.add(fact);
		return facts;
	}
	
	private int getMProductID(int tireMark) throws SQLException{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int resp = 0;
		String sql = "select m_product_id from uy_tr_invoicetire where uy_tr_tiremark_id = " + tireMark + ";";
		pstmt = DB.prepareStatement (sql, null);
		rs = pstmt.executeQuery();
		if(rs.next())
			resp = rs.getInt("m_product_id");
		
		return resp;
		
		
	}
	

}
