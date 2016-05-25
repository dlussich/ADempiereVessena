/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 08/08/2013
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - CalloutDeliveryTracking
 * OpenUp Ltda. Issue #1173 
 * Description: Callouts para modulo de tracking de envios (tarjetas, resumenes de cuenta, etc).
 * @author Gabriel Vila - 08/08/2013
 * @see
 */
public class CalloutDeliveryTracking extends CalloutEngine {

	/**
	 * Constructor.
	 */
	public CalloutDeliveryTracking() {
	}

	/***
	 * Cambio de valor de Precinto Recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 08/08/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeSeal(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int modelID = ((Integer)value).intValue();
		
		if (modelID <= 0) return "";

		MTTSeal model = new MTTSeal(Env.getCtx(), modelID, null);
 
		mTab.setValue(X_UY_TT_Receipt.COLUMNNAME_UY_DeliveryPoint_ID_From, model.getUY_DeliveryPoint_ID());
		mTab.setValue(X_UY_TT_Receipt.COLUMNNAME_QtyBook, model.getQtyBook());
		mTab.setValue(X_UY_TT_Receipt.COLUMNNAME_QtyCount, model.getQtyCount());
		
		return "";
	}

	
	/***
	 * Scaneo de Acuse de Recibo 
	 * OpenUp Ltda. Issue #2056 
	 * @author Gabriel Vila - 02/04/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String acuseScan(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";

		Integer uyHandID = new Integer(value.toString());
		mTab.setValue(X_UY_TT_Acuse.COLUMNNAME_ScanText, uyHandID.toString());
		
		mTab.dataSave(true);
		
		return "";
	}

	/***
	 * Scaneo de Cuenta en la Recepcion de Cuentas.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 26/08/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String receiptAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		//String accountNo = replaceFirstCerosForAccountNo(value.toString().trim());
		//Integer nrocta = new Integer(value.toString());
		Integer nrocta = new Integer(value.toString().trim());
		mTab.setValue(X_UY_TT_ReceiptScan.COLUMNNAME_ScanText, nrocta.toString());
		
		
		if (mTab.dataSave(true)){
			mTab.dataNew(false);
		}
		else{
			mTab.setValue(X_UY_TT_ReceiptScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}
	
	/***
	 * Traigo datos de cuenta al ingresar la misma en la ventana de proceso de acuse. 
	 * OpenUp Ltda. Issue #2056 
	 * @author Gabriel Vila - 02/04/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeAcuseAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		
		mTab.dataSave(true);
		
		return "";
	}

	
	/***
	 * Scaneo de cuenta en la recarga de caja.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 13/11/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String boxLoadAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";

		Integer nrocta = new Integer(value.toString());
		mTab.setValue(X_UY_TT_BoxLoadScan.COLUMNNAME_ScanText, nrocta.toString());
		
		
		if (mTab.dataSave(true)){
			mTab.dataNew(false);
		}
		else{
			mTab.setValue(X_UY_TT_BoxLoadScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}
	
	/***
	 * Scaneo de Cuenta en Unificacion de CardCarriers.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 26/08/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String unifyAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";

		if (mTab.dataSave(true)){
			mTab.dataNew(false);	
		}
		else{
			mTab.setValue(X_UY_TT_UnifyScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}


	/***
	 * Scaneo de Cuenta en el proceso de Comunicacion a Usuario.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 06/10/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String comunicaAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";

		Integer nrocta = new Integer(value.toString());
		mTab.setValue(X_UY_TT_ComunicaScan.COLUMNNAME_ScanText, nrocta.toString());
		
		
		if (mTab.dataSave(true)){
			mTab.dataNew(false);
		}
		else{
			mTab.setValue(X_UY_TT_ComunicaScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}

	/***
	 * Scaneo de Cuenta en el proceso de Impresion Vale.
	 * OpenUp Ltda. Issue #3273 (Mejora) 
	 * @author Sylvie Bouissa - 06/01/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String retprintvaleAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		value = replaceFirstCerosForAccountNo(String.valueOf(value));
		if (value.toString().trim().equalsIgnoreCase("")) return "";
       
		Integer nrocta = new Integer(value.toString().trim());
		mTab.setValue(X_UY_TT_RetPrintValeScan.COLUMNNAME_ScanText, nrocta.toString());
		
		
		if (mTab.dataSave(true)){
			mTab.dataNew(false);
		}
		else{
			mTab.setValue(X_UY_TT_RetPrintValeScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}
	
	
	/***
	 * En recepcion de bolsines se selecciona caja a utilizarse.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 28/08/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String receiptBox(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int boxID = (Integer)value;
		if (boxID <= 0) return "";

		MTTBox box = new MTTBox(ctx, boxID, null);
		
		if (box.isDestiny()){
			mTab.setValue(X_UY_TT_ReceiptBox.COLUMNNAME_UY_DeliveryPoint_ID, box.getUY_DeliveryPoint_ID_To());
		}
		
		mTab.setValue(X_UY_TT_ReceiptBox.COLUMNNAME_IsRetained, box.isRetained());
		mTab.setValue(X_UY_TT_ReceiptBox.COLUMNNAME_UnificaCardCarrier, box.isUnificaCardCarrier());
		mTab.setValue(X_UY_TT_ReceiptBox.COLUMNNAME_ComunicaUsuario, box.isComunicaUsuario());
		
		if (box.isRetained()){
			mTab.setValue(X_UY_TT_ReceiptBox.COLUMNNAME_RetainedStatus, box.getRetainedStatus());
		}
		
		return "";
	}


	/***
	 * En unificacion de cardcarriers se selecciona caja a utilizarse.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 13/09/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String unifyBox(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int boxID = (Integer)value;
		if (boxID <= 0) return "";

		MTTBox box = new MTTBox(ctx, boxID, null);

		if (box.isDestiny()){
			mTab.setValue(X_UY_TT_UnifyBox.COLUMNNAME_UY_DeliveryPoint_ID, box.getUY_DeliveryPoint_ID_To());
		}
		
		mTab.setValue(X_UY_TT_UnifyBox.COLUMNNAME_IsRetained, box.isRetained());
		mTab.setValue(X_UY_TT_UnifyBox.COLUMNNAME_UnificaCardCarrier, box.isUnificaCardCarrier());
		mTab.setValue(X_UY_TT_UnifyBox.COLUMNNAME_ComunicaUsuario, box.isComunicaUsuario());
		
		if (box.isRetained()){
			mTab.setValue(X_UY_TT_UnifyBox.COLUMNNAME_RetainedStatus, box.getRetainedStatus());
		}

		return "";
	}


	/***
	 * En proceso de Comunicacion a Usuario se selecciona caja a utilizarse.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 06/10/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String comunicaBox(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int boxID = (Integer)value;
		if (boxID <= 0) return "";

		MTTBox box = new MTTBox(ctx, boxID, null);

		if (box.isDestiny()){
			mTab.setValue(X_UY_TT_ComunicaBox.COLUMNNAME_UY_DeliveryPoint_ID, box.getUY_DeliveryPoint_ID_To());
		}
		
		mTab.setValue(X_UY_TT_ComunicaBox.COLUMNNAME_IsRetained, box.isRetained());
		mTab.setValue(X_UY_TT_ComunicaBox.COLUMNNAME_ComunicaUsuario, box.isComunicaUsuario());
		
		if (box.isRetained()){
			mTab.setValue(X_UY_TT_ComunicaBox.COLUMNNAME_RetainedStatus, box.getRetainedStatus());
		}

		return "";
	}

	
	/***
	 * Cambio de valor de Nombre de Titular, Direccion, Localidad, Codigo Postal, Fecha de Vencimiento, Limite de Credito y Modo de Entrega
	 * a partir del cambio de la Cuenta Escaneada.
	 * OpenUp Ltda. Issue #1256 
	 * @author Guillermo Brust - 26/08/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String loadSealAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		String accountNo = value.toString();
		
		if (accountNo.equals("")) return "";		
		
		if (mTab.dataSave(true)){
			mTab.dataNew(false);	
		}
		else{
			mTab.setValue(X_UY_TT_SealLoadScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}
	
	
	public String loadRetentionAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		String accountNo = value.toString();
		
		if (accountNo.equals("")) return "";		
		
		if (mTab.dataSave(true)){
			mTab.dataNew(false);	
		}
		else{
			mTab.setValue(X_UY_TT_RetentionScan.COLUMNNAME_ScanText, null);
		}
		
		return "";
	}
	
	
	/***
	 * Se debe validar segun el campo de codigo de precinto, si el mismo existe, verificar si tiene el mismo punto de distribucion de origen y destino que el cabezal de la carga y que sea propio,
	 * y en este caso se setea el valor oculto del UY_TT_Seal_ID con este existe, si no pasa la validacion se manda un mensaje de error como una exception
	 * en el caso de que no exista se debe crear un nuevo modelo de MTTSeal y setearlo en el campo oculto UY_TT_Seal_ID
	 * OpenUp Ltda. Issue #1256 
	 * @author Guillermo Brust - 29/08/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setSealID(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) throws AdempiereException {
		
		if (value == null) return "";

		String searchKey = value.toString();
		
		if (searchKey.equals("")) return "";	
		
		mTab.dataSave(true);
		
		return "";
	}
	
	
	/***
	 * Se cargan algunos datos de la caja en la MTTSealLoadBox
	 * OpenUp Ltda. Issue #1256 
	 * @author Guillermo Brust - 30/08/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setSealLoadBox(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) throws AdempiereException {
		
		if (value == null) return "";
		
		int boxID = (Integer)value;
		if (boxID <= 0) return "";
		
		//Controlo que no me guarden la misma caja dos veces
		if(MTTSealLoadBox.existsBoxForSealLoad(ctx, null, ((Integer) mTab.getValue(X_UY_TT_SealLoadBox.COLUMNNAME_UY_TT_SealLoad_ID)).intValue(), boxID)) {
			throw new AdempiereException("Caja ya ingresada en la Grilla");
		}

		MTTBox box = new MTTBox(ctx, boxID, null);		
		mTab.setValue(X_UY_TT_SealLoadBox.COLUMNNAME_IsRetained, box.isRetained());
		if (box.isRetained()){
			mTab.setValue(X_UY_TT_SealLoadBox.COLUMNNAME_RetainedStatus, box.getRetainedStatus());
		}

		
		return "";
	}

	
	/***
	 * Cambio en check de plastico seleccionado.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 26/08/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeSelectedPlastic(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		
		mTab.dataSave(true);
		
		return "";
	}	
	
	
	/***
	 * Cambio en check de punto de distribucion seleccionado.
	 * OpenUp Ltda. Issue #1256 
	 * @author Guillermo Brust - 11/09/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeSelectedDeliveryPoint(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		if (value.toString().trim().equalsIgnoreCase("")) return "";
		
		mTab.dataSave(true);
		
		return "";
	}
	
	
	/***
	 * Al cambiar la cedula de identidad debo obtener datos del cliente desde
	 * base del Financial. Se puede dar el caso que una persona este mas de una vez
	 * en la tabla de clientes, ya sea como titular o como derivado de n tarjetas.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 16/09/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setCICuenta(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		
		if (value == null) return "";

		String cedula = value.toString().trim();
		
		if (cedula.equalsIgnoreCase("")) return "";

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String retorno = "";
		
		try{
			
			int uyDeliveryPoint = -1; 
			if (mTab.getValue(X_UY_TT_Hand.COLUMNNAME_UY_DeliveryPoint_ID) != null){
				uyDeliveryPoint = (Integer)mTab.getValue(X_UY_TT_Hand.COLUMNNAME_UY_DeliveryPoint_ID);
			}
			
			sql = " select distinct card.accountno " +
				  " from uy_tt_card card " +
				  " inner join uy_tt_cardplastic pl on card.uy_tt_card_id = pl.uy_tt_card_id " +
				  " where card.uy_deliverypoint_id_actual =? " +
				  " and card.uy_tt_cardstatus_id not in (select uy_tt_cardstatus_id from uy_tt_cardstatus where endtrackstatus='Y') " +
				  " and pl.cedula =? "; 
				
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, uyDeliveryPoint);
			pstmt.setString(2, cedula);
			rs = pstmt.executeQuery();
			
			boolean hayDatos = false;
			int contCuentas = 0;
			int defaultCedulaCuentaID = -1;
			
			while (rs.next()){
				
				hayDatos = true;

				String accountNo = rs.getString(1);
				
				MRCedulaCuenta cedcta = MRCedulaCuenta.forCedulaCuenta(ctx, cedula, accountNo, null);
				if ((cedcta == null) || (cedcta.get_ID() <= 0)){
					cedcta = new MRCedulaCuenta(ctx, 0, null);
					cedcta.setValue(cedula);
					cedcta.setAccountNo(accountNo);
					cedcta.saveEx();
				}
				
				defaultCedulaCuentaID = cedcta.get_ID();
				contCuentas++;
			}

			if (!hayDatos){
				throw new AdempiereException("No hay Cuentas para Entregar asociadas a esta Cedula y Sucursal.");
			}

			//mTab.getField("ExecuteAction3").updateContext();

			mTab.getField("UY_R_CedulaCuenta_ID").refreshLookup();

			if (contCuentas > 1){
				retorno = "Cliente con mas de una CUENTA. Por favor SELECCIONE LA CORRECTA !!!\n";
			}
			else{
				mTab.setValue(X_UY_TT_Hand.COLUMNNAME_UY_R_CedulaCuenta_ID, defaultCedulaCuentaID);
			}
			
			mTab.dataRefresh();
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return retorno;
	}

	
	/***
	 * Al cambiar la cuenta del cliente, busco y obtengo tracking abierto para dicha cuenta.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 16/09/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCuenta(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int idCuenta = (Integer)value;
		
		if (idCuenta<= 0) return "";

		mTab.dataSave(true);
		
		return "";
		
	}

	
	/***
	 * Al cambiar tipo de sms debo mostrar el texto del mismo.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 10/10/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeSMS(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int idSMS = (Integer)value;
		
		if (idSMS <= 0) return "";

		MTTSMS sms = new MTTSMS(ctx, idSMS, null);
		
		if (sms.get_ID() > 0) {
			mTab.setValue(X_UY_TT_ActionCard.COLUMNNAME_Description, sms.getDescription());
		}
		
		return "";
		
	}	

	/***
	 * Al cambiar tipo de mensaje tracking debo mostrar el texto del mismo.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 10/10/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeMensaje(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int idMsj = (Integer)value;
		
		if (idMsj <= 0) return "";

		MTTMensaje msj = new MTTMensaje(ctx, idMsj, null);
		
		if (msj.get_ID() > 0) {
			mTab.setValue(X_UY_TT_ActionCard.COLUMNNAME_Description, msj.getDescription());
		}
		
		return "";
		
	}	
	
	/***
	 * Setea el nombre del receptor de la tarjeta a partir la cedula
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust. 09/10/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeName2(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		MTTCardPlastic plastic = MTTCardPlastic.forCedula(Env.getCtx(), ((Integer) mTab.getValue(X_UY_TT_Hand.COLUMNNAME_UY_TT_Card_ID)).intValue(), value.toString(), null);
		
		if(plastic != null){
			mTab.setValue(X_UY_TT_Hand.COLUMNNAME_Name2, plastic.getName());
		}else{
			mTab.setValue(X_UY_TT_Hand.COLUMNNAME_Name2, "");
		}		
	
		mTab.setValue(X_UY_TT_Hand.COLUMNNAME_Telephone, "");
		mTab.setValue(X_UY_TT_Hand.COLUMNNAME_Mobile, "");
		mTab.setValue(X_UY_TT_Hand.COLUMNNAME_EMail, "");
		mTab.setValue(X_UY_TT_Hand.COLUMNNAME_Vinculo, "");
		mTab.setValue(X_UY_TT_Hand.COLUMNNAME_TipoSoc, 3);
 
		return "";
	}
	
	/***
	 * Metodo que setea destino y direccion actual en gestion de incidencias.
	 * OpenUp Ltda. Issue #1383
	 * @author Nicolas Sarlabos - 14/10/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setForIncidence(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
		int pointID = (Integer)value;
		
		if(pointID <= 0) return "";

		if(pointID == 1000035 || pointID == 1000037) mTab.dataSave(true); //si la accion es cambio de destino y/o direccion
				
		return "";
	}
	
	/***
	 * Metodo que quita ceros a la izquierda al momento de leer las cuentad por medio del escanner
	 * OpenUp Issue # 3142
	 */
	private String replaceFirstCerosForAccountNo(String value){
		String valor = value.replace(" ", "");
		try{
			String accountNo = valor.replaceFirst ("^0*", "");
			return accountNo;
		}catch(Exception e){
			e.toString();
		}
		return valor;
	}

	/***
	 * Setea datos del documento de verificacion de entrega
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Oct 2, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setDeliveryConf(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null){

			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_UY_R_Reclamo_ID, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_Cedula, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_AccountNo, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_Name, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_UY_DeliveryPoint_ID, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired1, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired2, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired3, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired4, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired5, false);
			
			return "";
		}
		if (value.toString().trim().equalsIgnoreCase("")){

			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_UY_R_Reclamo_ID, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_Cedula, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_AccountNo, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_Name, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_UY_DeliveryPoint_ID, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired1, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired2, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired3, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired4, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired5, false);
			
			return "";
		}

		String incidenciaNo = value.toString().trim();
		MRReclamo incidencia = MRReclamo.forDocumentNo(ctx, incidenciaNo, null); 

		if ((incidencia != null) && (incidencia.get_ID() > 0)){
			
			MTTCard card = MTTCard.forIncidencia(ctx, incidencia.get_ID(), null);
			if ((card == null) || (card.get_ID() <= 0)){
				throw new AdempiereException("No existe tracking de cuenta activo asociado a este numero de incidencia.");
			}
			
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_UY_R_Reclamo_ID, incidencia.get_ID());
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_Cedula, incidencia.getCedula());
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_AccountNo, incidencia.getAccountNo());
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_Name, incidencia.getCustomerName());
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_UY_DeliveryPoint_ID, card.getUY_DeliveryPoint_ID_Actual());
			
			if (card.getCardAction().equalsIgnoreCase(X_UY_TT_Card.CARDACTION_Nueva)){
				mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired1, true);				
			}
			else{
				mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired1, false);
			}
			
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired2, card.isSolValeSN());
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired3, card.isCedulaSN());
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired4, card.isRecSueSN());
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired5, card.isConsDomSN());
			
		}
		else{
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_UY_R_Reclamo_ID, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_Cedula, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_AccountNo, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_Name, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_UY_DeliveryPoint_ID, null);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired1, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired2, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired3, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired4, false);
			mTab.setValue(X_UY_TT_DeliveryConf.COLUMNNAME_IsRequired5, false);

			throw new AdempiereException("No existe una inicidencia abierta con ese numero.");
		}
		
		return "";
	}

	
	/***
	 * Setea datos del documento de recepcion de contrato
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Oct 2, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setContractInfo(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null){

			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_UY_R_Reclamo_ID, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_Cedula, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_AccountNo, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_Name, null);
			
			return "";
		}
		if (value.toString().trim().equalsIgnoreCase("")){

			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_UY_R_Reclamo_ID, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_Cedula, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_AccountNo, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_Name, null);
			
			return "";
		}

		String solNro = value.toString().trim();
		
		MTTCard card = MTTCard.forSolNro(ctx, solNro, null); 

		if ((card != null) && (card.get_ID() > 0)){
			
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_UY_R_Reclamo_ID, card.getUY_R_Reclamo_ID());
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_Cedula, card.getCedula());
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_AccountNo, card.getAccountNo());
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_Name, card.getName());
		}
		else{

			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_UY_R_Reclamo_ID, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_Cedula, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_AccountNo, null);
			mTab.setValue(X_UY_TT_Contract.COLUMNNAME_Name, null);

			throw new AdempiereException("No existe tracking de cuenta activo asociado a este numero de contrato.");
		}
		
		return "";
	}

	/***
	 * Setea datos de caja y ubicacion en entrega de contrato segun id de contrato recibido
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Oct 15, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setContractHandBox(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int id = (Integer)value;
		
		if (id <= 0) return "";

		MTTContract model = new MTTContract(ctx, id, null);
		if (model.get_ID() > 0){
			mTab.setValue(X_UY_TT_ContractHand.COLUMNNAME_UY_TT_Box_ID, model.getUY_TT_Box_ID());
			mTab.setValue(X_UY_TT_ContractHand.COLUMNNAME_LocatorValue, model.getLocatorValue());
			mTab.setValue(X_UY_TT_ContractHand.COLUMNNAME_UY_R_Reclamo_ID, model.getUY_R_Reclamo_ID_2());
		}
		
		return "";
		
	}

	
}
