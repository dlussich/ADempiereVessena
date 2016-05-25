/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MProduct;
import org.compiere.model.MProductAcct;
import org.compiere.model.MTax;

/**
 * @author Nicolas
 *
 */
public class MTRMaintain extends X_UY_TR_Maintain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 757651394326626108L;

	/**
	 * @param ctx
	 * @param UY_TR_Maintain_ID
	 * @param trxName
	 */
	public MTRMaintain(Properties ctx, int UY_TR_Maintain_ID, String trxName) {
		super(ctx, UY_TR_Maintain_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMaintain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		this.createProduct();		
		
		return true;
	}

	/**Metodo que crea o actualiza un producto asociado a la tarea y su contabilidad.
	 * OpenUp Ltda. Issue #1621 
	 * Description: 
	 * @author Nicolas Sarlabos - 13/05/2014
	 * @param documentNo 
	 * @see
	 */
	private void createProduct() {
		
		MProductAcct ctb = null;
		MProduct prod = null;
		
		//creo o actualizo producto asociado a esta tarea de mantenimiento
		prod = new MProduct(getCtx(),this.getM_Product_ID(),get_TrxName());
		
		prod.setName(this.getName());
		prod.setC_UOM_ID(100);
		prod.setIsSummary(false);
		prod.setDiscontinued(false);
		prod.setIsStocked(false);
		prod.setIsPurchased(true);
		prod.setIsSold(false);
		prod.setProductType("I");
		
		MTax tax = MTax.forValue(getCtx(), "basico", get_TrxName()); //obtengo impuesto BASICO
		
		if(tax != null && tax.get_ID() > 0){
			
			prod.setC_TaxCategory_ID(tax.getC_TaxCategory_ID()); //seteo categoria de impuesto BASICO
			
		} else throw new AdempiereException("No se encontro tipo de impuesto BASICO");
		
		prod.saveEx(); //guardo producto
		
		this.setM_Product_ID(prod.get_ID());
		
		//creo o actualizo contabilidad del producto
		ctb = MProductAcct.getMProductAcctForProduct(getCtx(), prod.get_ID()); 
		
		if(ctb!= null && ctb.get_ID() > 0){
			
			ctb.setP_InventoryClearing_Acct(this.getPO_Acct_ID());
			ctb.setConsume_Acct_ID(this.getConsume_Acct_ID());		
			
			ctb.saveEx();
			
		} else {
			
			ctb = new MProductAcct(getCtx(),0,get_TrxName());
			
			MClient client = new MClient(getCtx(),this.getAD_Client_ID(),get_TrxName());
			MAcctSchema schema = client.getAcctSchema(); //obtengo esquema contable del cliente
			
			ctb.setC_AcctSchema_ID(schema.get_ID());			
			ctb.setM_Product_ID(prod.get_ID());
			ctb.setP_InventoryClearing_Acct(this.getPO_Acct_ID());
			ctb.setConsume_Acct_ID(this.getConsume_Acct_ID());
			
			ctb.saveEx();
			
		}
		
	}

}
