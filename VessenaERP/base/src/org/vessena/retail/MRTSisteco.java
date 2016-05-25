/**
 * 
 */
package org.openup.retail;

import java.math.BigDecimal;
import java.util.Properties;

import org.compiere.model.MProduct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openup.model.MRTAction;
import org.openup.model.MRTInterfaceProd;
import org.openup.model.MRTInterfaceScales;

/**OpenUp Ltda Issue#
 * @author SBT 9/12/2015
 *
 */
public class MRTSisteco extends MRTRetailInterface {

	/**
	 * 
	 */
	public MRTSisteco() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insertCliente(Properties ctx, int mClientID, int mCBPartnerID,
			String trxName) {		
	}

	@Override
	public String insertArticulo(Properties ctx, int mClientID, int mProductID,
			String trxName) {
		// Nueva interface para este producto
		MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0, trxName);
		it.setM_Product_ID(mProductID);
		it.insertInterface(true);
		
		MProduct prod = new MProduct (ctx,mProductID,trxName);
		//Si tiene tandem asociado corresponde realizar insert en tabla de interfaceo para el tandem
		if(0<prod.get_ValueAsInt("M_Product_Tandem_ID")){
			MRTInterfaceProd.insertInterfaceFromTandem(prod,prod.get_ValueAsInt("M_Product_Tandem_ID"), "insert", ctx, trxName);
		}
		return "OK";
	}

	@Override
	public String updateArticulo(Properties ctx, int mClientID, int mProductID,
			String trxName) {
		
		// Obtengo (si existe) registro de interface para este producto que
		// aun no haya sido leido
		MRTInterfaceProd it = (MRTInterfaceProd) MRTInterfaceProd.forProduct(ctx, mProductID, trxName);
		if ((it != null) && (it.get_ID() > 0)) {
			// Si ya tengo uno, lo actualizo con datos de la definicion del producto
			it.setUY_RT_Action_ID(MRTAction.forValue(ctx, "update", trxName).get_ID());
			it.updateInterface();
		}else {
			// Actualización para este producto
			it = new MRTInterfaceProd(ctx, 0, trxName);
			it.setM_Product_ID(mProductID);
			it.insertInterface(false);
		}
		return "OK";
	}

	/* (non-Javadoc)
	 * @see org.openup.model.MRTRetailInterface#deleteArticulo(java.util.Properties, int, java.lang.String, java.lang.String)
	 */
	@Override
	public String deleteArticulo(Properties ctx, int mClientID, String prodCode,
			String trxName) {
		// TODO Auto-generated method stub
		return "NO IMPLEMENTADO";
	}

	/* (non-Javadoc)
	 * @see org.openup.model.MRTRetailInterface#updateCliente(java.util.Properties, int, int, java.lang.String)
	 */
	@Override
	public void updateCliente(Properties ctx, int mClientID, int mCBPartnerID,
			String trxName) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.openup.model.MRTRetailInterface#deleteCliente(java.util.Properties, int, int, java.lang.String)
	 */
	@Override
	public void deleteCliente(Properties ctx, int mClientID, int mCBPartnerID,
			String trxName) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.openup.model.MRTRetailInterface#getVersionAux()
	 */
	@Override
	public void getVersionAux() {
		// No corresponde en sisteco
		
	}

	@Override
	public String updateArticuloPrecio(Properties ctx, int mClientID,
			int mProductID, int cCurrencyID,BigDecimal price,int adOrgID, String trxName) {
		MProduct prod = new MProduct(ctx,mProductID,null);
		MRTInterfaceProd.insertInterface(prod, cCurrencyID, price, ctx, trxName);	
		return "Ok";
	}

	@Override
	public JSONObject getMovimiento(Properties ctx, int mClientID, 
			String trxName,int mProductID, int idCaja, String nroCupon,String fchD,String fchH) {
		// TODO Auto-generaint mProductID,ted method stub
		return null;
	}

	

	@Override
	public JSONArray getMovimientoTimestamp(Properties ctx, String trxName,
			int mClientID, int idLocal, int idCaja, String fchTicket, int idEMpresa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertUPC(Properties ctx, int mClientID, String mUPC,int mProductID,
			String trxName) {
		MRTInterfaceProd it= new MRTInterfaceProd(ctx, 0, trxName);
		it.toInterfaceRow(mProductID ,"insert", mUPC);
		return "OK";
	}

	@Override
	public String updateUPC(Properties ctx, int mClientID, String mUPC,
			int mProductID, String trxName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUPC(Properties ctx, int mClientID, String mUPC,
			int mProductID, String trxName) {
		try{
			MRTInterfaceProd it = new MRTInterfaceProd(ctx, 0 , trxName);
			it.toInterfaceRow(mProductID, "delete", mUPC );
			return "OK";
		}catch(Exception e){
			e.toString();
		}
		return "Error";
		
	}

	@Override
	public String insertProdScale(Properties ctx, int mClientID, String mUPC,
			int mProductID, String trxName) {
		MRTInterfaceScales it = new MRTInterfaceScales(ctx, 0, trxName);
		it.toInterfaceScalesRow(mProductID, mUPC);
		return "OK";
	}
	
	@Override
	public String deleteProdFromScale(Properties ctx, int mClientID,
			String mUPC, int mProductID, String trxName) {
		MRTInterfaceScales it = new MRTInterfaceScales(ctx, 0, trxName);
		it.deleteInterfaceScalesRow(mProductID, mUPC);
		return "OK";
	}

	

	



}
