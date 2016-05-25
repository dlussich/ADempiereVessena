/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/08/2013
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MAttachment;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MPeriod;
import org.compiere.model.MProcess;
import org.compiere.model.MQuery;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.util.ItalcredSystem;

/**
 * org.openup.model - MTTCard
 * OpenUp Ltda. Issue #1182 
 * Description: Modelo para seguimiento de tarjetas en tracking.
 * @author Gabriel Vila - 01/08/2013
 * @see
 */
public class MTTCard extends X_UY_TT_Card implements IDynamicTask {

	private static final long serialVersionUID = 4149638875191956186L;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Card_ID
	 * @param trxName
	 */
	public MTTCard(Properties ctx, int UY_TT_Card_ID, String trxName) {
		super(ctx, UY_TT_Card_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Metodo que valida si esta tarjeta es entregable o no. Ademas se setea la accion en caso de no 
	 * ser entregable.
	 * OpenUp Ltda. Issue #1184 
	 * @author Guillermo Brust - 01/08/2013
	 * @see
	 * @return
	 */
	public boolean validateDelivery(){	
		
		//variables necesarias
		StringBuilder notValidText = new StringBuilder();
		ItalcredSystem con = new ItalcredSystem();		
		
		MTTConfig config = MTTConfig.forValue(this.getCtx(), this.get_TrxName(), "tarjeta");
		
		// Obtengo modelo de liquidacion actual para esta cuenta desde financial
		String mlCodFin = con.getModeloLiquidacion(this.getAccountNo());
		if (mlCodFin != null){
			if (this.getMLCod() != null) {
				if (!this.getMLCod().equalsIgnoreCase(mlCodFin)){
					this.setMLCod(mlCodFin);
				}
			}
			else{
				this.setMLCod(mlCodFin);
			}
		}
		
		MFduModeloLiquidacion mlcod = MFduModeloLiquidacion.getMFduModeloLiquidacionForValue(this.getCtx(), this.get_TrxName(), this.getMLCod());
		
		//Por ahora es distribuible la tarjeta
		this.setIsDeliverable(true);
		this.setIsRetained(false);
				
		//Caso de retencion por tener modelo de liquidacion parametrizado para retencion
		if(mlcod == null){
			this.setIsDeliverable(false);
			this.setIsRetained(true);
			notValidText.append("Modelo de liquidacion no parametrizado. ");
		
		}else if(mlcod.isRetenido()){
			this.setIsDeliverable(false);
			this.setIsRetained(true);
			if(mlcod.getName().equals("")) notValidText.append(mlcod.getValue()).append(" - Modelo de liquidacion no válido. ");	
			else notValidText.append(mlcod.getValue()).append(" - ").append(mlcod.getName()).append(". ");			
		}		
		
		//Caso de retencion por tener convenio activo o pendiente
		if(con.isConvenioActivoOPendiente(this.getAccountNo())){
			this.setIsDeliverable(false);
			this.setIsRetained(true);
			notValidText.append("Cuenta con convenio activo o pendiente. ");		
		}
		
		//Caso de retencion por tener igual o mas dias de mora a la fecha de hoy que el minimo parametrizado
		int diasMora = con.getDiasDeMora(this.getAccountNo());
		if(diasMora >= config.getMora()){
			this.setIsDeliverable(false);
			this.setIsRetained(true);
			notValidText.append("Dias de mora por encima del minimo parametrizado. (").append(diasMora).append("). ");
		}
		
		if(!this.isDeliverable()){
			this.setNotDeliverableAction("RETENER");
			this.setNotValidText(notValidText.toString());
		}
		
		this.saveEx();
		
		return this.isDeliverable();
		
		//this.setIsDeliverable(false);
		//return false;
	}
	
	/***
	 * Método que devuelve un modelo de la MTTCard, a partir de un value, ademas tiene que estar en estado igual al del parametro
	 * OpenUp Ltda. Issue #1256
	 * @author Guillermo Brust - 26/08/2013
	 * @see
	 * @return
	 */
	public static MTTCard forAccountNo(Properties ctx, String trxName, String accountNo){
				
		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + " = '" + accountNo + "'" +													
							" AND " + X_UY_TT_Card.COLUMNNAME_IsActive + " = 'Y'";
		
		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.first();		
		
		return model;
	}

	
	/***
	 * Método que devuelve un modelo de la MTTCard, a partir de un value.
	 * Obtiene el registro mas antiguo para cuenta recibida, que no este en un estado final.
	 * OpenUp Ltda. Issue #2306
	 * @author Gabriel Vila - 25/06/2014.
	 * @see
	 * @return
	 */
	public static MTTCard forAccountOpenFirstAndStatus(Properties ctx, String trxName, String accountNo, int uyTTCardStatusID){
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + "='" + accountNo + "'" +
				" AND uy_tt_cardstatus_id =" + uyTTCardStatusID +
				" AND uy_tt_card_id not in (select uy_tt_card_id from uy_tt_acuse where docstatus='CO') ";
		
		MTTCard model = null;
				
		model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id asc ")
		.first();
		
		return model;

	}
	
	
	/***
	 * Método que devuelve un modelo de la MTTCard a partir de un incidente.
	 * OpenUp Ltda. Issue #1173
	 * @author Gabriel Vila - 15/10/2013
	 * @see
	 * @return
	 */
	public static MTTCard forReclamo(Properties ctx, String trxName, int reclamoID){
				
		String whereClause = X_UY_TT_Card.COLUMNNAME_UY_R_Reclamo_ID + " =" + reclamoID;
		
		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.first();		
		
		return model;
	}
	
	
	/***
	 * Método que devuelve un valor booleano, a partir de un value, 
	 * ademas tiene que tener como punto de distribucion destino el pasado por parametro
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 23/09/2013
	 * @see
	 * @return
	 */
	public static boolean existsIncidenciaAndDeliveryPoint(Properties ctx, String trxName, int uyRReclamoID, int deliveryPointID){
				
		String whereClause = X_UY_TT_Card.COLUMNNAME_UY_R_Reclamo_ID + " = " + uyRReclamoID +	
							 " AND " + X_UY_TT_Card.COLUMNNAME_UY_DeliveryPoint_ID_Actual + " = " + deliveryPointID +
							 " AND " + X_UY_TT_Card.COLUMNNAME_IsActive + " = 'Y'";
		
		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.first();
		
		return model != null;
	}


	/***
	 * Retorna modelo para cuenta y punto actual
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 23/09/2013
	 * @see
	 * @return
	 */
	public static MTTCard forIncidenciaAndDeliveryPoint(Properties ctx, String trxName, int uyRReclamoID, int deliveryPointID){
				
		String whereClause = X_UY_TT_Card.COLUMNNAME_UY_R_Reclamo_ID + " =" + uyRReclamoID +	
							 " AND " + X_UY_TT_Card.COLUMNNAME_UY_DeliveryPoint_ID_Actual + " = " + deliveryPointID +
							 " AND " + X_UY_TT_Card.COLUMNNAME_IsActive + " = 'Y'";
		
		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.first();
		if(model != null){
			if(model.isOpenForTracking()){
				return model;
			}
		}		
		return model;
	}


	/***
	 * Retorna modelo para cuenta y punto actual que este abierto para traquing --> Se crea metodo porque el anterior retorna el primero
	 * OpenUp Ltda. Issue # 3436
	 * @author Sylvie Bouissa - 05/01/2015
	 * @see
	 * @return
	 */
	public static MTTCard forAccountNoAndDeliveryPointOpenForTracking(Properties ctx, String trxName, String accountNo, int deliveryPointID){
				
		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + " = '" + accountNo + "'" +	
							 " AND " + X_UY_TT_Card.COLUMNNAME_UY_DeliveryPoint_ID_Actual + " = " + deliveryPointID +
							 " AND " + X_UY_TT_Card.COLUMNNAME_IsActive + " = 'Y'";
		
		MTTCard model=null;
		List<MTTCard> lines = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.list();
		if (lines != null){
			for (MTTCard line: lines){
				if (line.isOpenForTracking()){
					model = line;
					break;
				}
			}
		}			
		return model;
	}
	/***
	 * Obtiene y retorna modelo segun cuenta recibida.
	 * OpenUp Ltda. Issue #1173. 
	 * @author Gabriel Vila - 27/08/2013
	 * @see
	 * @param ctx
	 * @param accountNo
	 * @param trxName
	 * @return
	 */
	public static MTTCard forAccountOpen(Properties ctx, String accountNo, String trxName){
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + "='" + accountNo + "'";
		
		MTTCard model = null;
				
		List<MTTCard> lines = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.list();
						
		if (lines != null){
			for (MTTCard line: lines){
				// Solo retorno modelo si tiene un estado abierto para tracking
				MTTCardStatus status = (MTTCardStatus)line.getUY_TT_CardStatus();
				if (!status.isEndTrackStatus()){
					model = line;
					break;
				}
			}
		}
		
		return model;
		
	}
/***Openup Sylvie Bouissa 22/10/2014 #Issue 3162 
 * Retorna la ultima cuenta abierta y no recepcionada 
 * 03/10/2014 además se controla que estado NO sea pendiente de envio (value: "pendenvio") -- Issue #3204
 * forAccountOpenAndUnreceipted
 */
	// INI #Issue 3162 
	public static MTTCard forAccountOpenAndUnreceipted(Properties ctx, String scantText, String trxName, Boolean fromFDU){

		if (scantText == null) return null;
		
		// Las cuentas ahora se scanean de la siguiente manera:
		// 1. Cuando en el punto cero llegan desde FDU lo que se escanea es el numero de cuenta
		// 2. En cualquier otro momento lo que se escanea es el ID de la incidencia asociada al seguimiento de la cuenta.
		//    Se imprimen etiquetas con el numero de incidencia para el tracking a partir del punto cero.
		String whereClause = "";

		if(fromFDU){
			whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + "='" + scantText.trim() + "'";
			whereClause += " AND " + X_UY_TT_Card.COLUMNNAME_UY_TT_CardStatus_ID + " = " + MTTCardStatus.forValue(ctx, trxName, "pendrec").get_ID();
		}
		else{
			// Obtengo modelo de incidencia segun numero de la misma
			MRReclamo incidencia = MRReclamo.forDocumentNo(ctx, scantText.trim(), trxName);
			
			// Si no obtuve incidencia, retorno null
			if (incidencia == null) return null;
			
			whereClause = X_UY_TT_Card.COLUMNNAME_UY_R_Reclamo_ID + "=" + incidencia.get_ID();
		}
		
		MTTCard model = null;
				
		List<MTTCard> lines = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.list();
						
		if (lines != null){
			for (MTTCard line: lines){
				// Solo retorno modelo si tiene un estado abierto para tracking , si no esta recepcionada y si tiene id de estado !!!
				MTTCardStatus status = (MTTCardStatus)line.getUY_TT_CardStatus();
				if (!status.isEndTrackStatus() && (!line.isReceived() && line.getUY_TT_CardStatus_ID()>0)){
					//openup Sylvie Bouissa 19/03/2015 --Issue #3804 se agrega control de que si se escanean 
					//cuentas desde fdu, si la misma esta pendiente de recepcion se retorna dicha cuenta
					if(fromFDU && status.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(ctx, trxName, "pendrec").get_ID()){
						model = line;
						break;
					}
					//openup Sylvie 03/11/2014 -- Issue #3204 se controla que el estado no sea PENDIENTE DE ENVIO
					if(line.getUY_TT_CardStatus_ID() != MTTCardStatus.forValue(ctx, trxName, "pendenvio").get_ID()){
						//openup Sylvie Bouissa 19/03/2015 --Issue #3804 se agrega control de que la cuenta no este recepcionada 
						if(line.getUY_TT_CardStatus_ID() != MTTCardStatus.forValue(ctx, trxName, "recepcionada").get_ID()){
							model = line;
							break;
						}
						
					}
					
				}
			}
		}
		
		return model;
		
	}
	// FIN #Issue 3162
	
	/***
	 * Obtiene y retorna modelo segun cuenta recibida.
	 * OpenUp Ltda. Issue #1173. 
	 * @author Gabriel Vila - 27/08/2013
	 * @see
	 * @param ctx
	 * @param accountNo
	 * @param UY_DeliveryPoint_ID 
	 * @param trxName
	 * @return
	 */
	public static MTTCard forIncidenciaLevante(Properties ctx, int uyRReclamoID, String levante, int UY_DeliveryPoint_ID, String trxName){

		MTTCard model = null;
		
		try {

			String whereClause = X_UY_TT_Card.COLUMNNAME_UY_R_Reclamo_ID + "=" + uyRReclamoID + 
					 			" AND " + X_UY_TT_Card.COLUMNNAME_UY_DeliveryPoint_ID_Actual + "=" + UY_DeliveryPoint_ID;
		
			model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName).first();
						
			if (model != null){
				
				boolean valid = false;
				
				// Solo retorno modelo si tiene un estado abierto para tracking
				MTTCardStatus status = (MTTCardStatus)model.getUY_TT_CardStatus();
				if (!status.isEndTrackStatus()){
					if (model.getLevante() != null){ 
						//SBT 17-10-2014 Agregaría control para verificar que el id de estado no sea nullo (por los casos que veo en la BD)----
						//SBT se agrega el control que se detalla anteriormente 30-10-2014 Issue # 3142
						if (model.getLevante().equalsIgnoreCase(levante) && (model.getUY_TT_CardStatus_ID()>0)){
							valid = true;
						}
					}
				}
				
				if (!valid) 
					model = null;
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return model;

	}

	
	/***
	 * Obtiene y retorna modelo segun cedula y numero de tarjeta recibidos.
	 * OpenUp Ltda. Issue #2056 
	 * @author Gabriel Vila - 03/04/2014
	 * @see
	 * @param ctx
	 * @param cedula
	 * @param nroTarjeta
	 * @param trxName
	 * @return
	 */
	public static MTTCard forCedulaTarjeta(Properties ctx, String cedula, String nroTarjeta, String trxName){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		MTTCard model = null;
		
		try{
			
			sql = " select card.uy_tt_card_id " +
				  " from uy_tt_card card " +
				  " inner join uy_tt_cardplastic cp on card.uy_tt_card_id = cp.uy_tt_card_id " +
				  " inner join uy_tt_cardstatus st on card.uy_tt_cardstatus_id = st.uy_tt_cardstatus_id " +
				  " where cp.cedula =? " +
				  " and (cp.value =? or cp.nrotarjetanueva=? ) " +
				  " and st.value ='entregada' " +
				  " order by card.uy_tt_card_id desc ";
 			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, cedula);
			pstmt.setString(2, nroTarjeta);
			pstmt.setString(3, nroTarjeta);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				model = new MTTCard(ctx, rs.getInt(1), trxName);
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return model;

	}
	
	
	
	/***
	 * Retorna si esta cuenta tiene un estado apto para hacer Tracking de la misma.
	 * Por ejemplo si la cuenta con este ID de modelo ya participo en un tracking y quedo 
	 * en estado entregada o destruida, no es posible aplicarle mas tracking.
	 * OpenUp Ltda. Issue #1173. 
	 * @author Hp - 29/08/2013
	 * @see
	 * @return
	 */
	public boolean isOpenForTracking(){
		
		try {
			MTTCardStatus status = (MTTCardStatus)this.getUY_TT_CardStatus();
			return (!status.isEndTrackStatus());
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Obtiene y retorna lineas con informacion de tarjetas (plasticos) asociadas a esta cuenta.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public List<MTTCardPlastic> getPlastics(){
		
		String whereClause = X_UY_TT_CardPlastic.COLUMNNAME_UY_TT_Card_ID + "=" + this.get_ID();
		
		List<MTTCardPlastic> lines = new Query(getCtx(), I_UY_TT_CardPlastic.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
	/***
	 * OpenUp. Guillermo Brust. 08/10/2013. ISSUE #
	 * Método que devuelve el plastico asociado a la cédula pasada por parametro.
	 * 
	 */
	public MTTCardPlastic getPlastic(String cedula){
		
		String whereClause = X_UY_TT_CardPlastic.COLUMNNAME_UY_TT_Card_ID + "=" + this.get_ID() +
				             " AND " + X_UY_TT_CardPlastic.COLUMNNAME_Cedula + " = '" + cedula + "'";
		
		MTTCardPlastic line = new Query(getCtx(), I_UY_TT_CardPlastic.Table_Name, whereClause, get_TrxName())
		.first();
		
		return line;
	}
	
	
	/***
	 * Obtiene y retorna lineas con informacion de tarjetas (plasticos) asociadas a esta cuenta que
	 * no este seleccionadas.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public List<MTTCardPlastic> getNotSelectedPlastics(){
		
		String whereClause = X_UY_TT_CardPlastic.COLUMNNAME_UY_TT_Card_ID + "=" + this.get_ID() +
				" AND " + X_UY_TT_CardPlastic.COLUMNNAME_IsSelected + "='N' ";
		
		List<MTTCardPlastic> lines = new Query(getCtx(), I_UY_TT_CardPlastic.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}	

	
	/***
	 * Obtiene y retorna plasticos de esta cuenta que fueron notificados por FDU.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 27/09/2013
	 * @see
	 * @return
	 */
	public List<MTTCardPlastic> getSentPlastics(){
		
		String whereClause = X_UY_TT_CardPlastic.COLUMNNAME_UY_TT_Card_ID + "=" + this.get_ID() +
				" AND " + X_UY_TT_CardPlastic.COLUMNNAME_IsSent + "='Y' ";
		
		List<MTTCardPlastic> lines = new Query(getCtx(), I_UY_TT_CardPlastic.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}	

	
	/***
	 * Obtiene y retorna plasticos de esta cuenta que fueron notificados por FDU y no fueron seleccionados
	 * en recepcion.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 27/09/2013
	 * @see
	 * @return
	 */
	public List<MTTCardPlastic> getSentPlasticsNotSelected(){
		
		String whereClause = X_UY_TT_CardPlastic.COLUMNNAME_UY_TT_Card_ID + "=" + this.get_ID() +
				" AND " + X_UY_TT_CardPlastic.COLUMNNAME_IsSent + "='Y' " +
				" AND " + X_UY_TT_CardPlastic.COLUMNNAME_IsSelected + "='N' ";
		
		List<MTTCardPlastic> lines = new Query(getCtx(), I_UY_TT_CardPlastic.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}	

	/***
	 * Obtiene y retorna plastico del titular de esta cuenta.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 27/09/2013
	 * @see
	 * @return
	 */
	public MTTCardPlastic getTitularSentPlastic(){

		String whereClause = X_UY_TT_CardPlastic.COLUMNNAME_UY_TT_Card_ID + "=" + this.get_ID() +
				" AND " + X_UY_TT_CardPlastic.COLUMNNAME_IsSent + "='Y' " +
				" AND " + X_UY_TT_CardPlastic.COLUMNNAME_IsTitular + "='Y' ";
		
		MTTCardPlastic model = new Query(getCtx(), I_UY_TT_CardPlastic.Table_Name, whereClause, get_TrxName())
		.first();
		
		return model;

	}
	
	
	/***
	 * Obtiene y retorna las MTTCard que estan dentro del bolsin y estan en estado PENDIENTE DE ENVIO
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 09/09/2013
	 * @see
	 * @return
	 */
	public static List<MTTCard> forSealAndStatus(Properties ctx, int sealID, int cardStatus){
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_UY_TT_Seal_ID + "=" + sealID + 
							" AND " + X_UY_TT_Card.COLUMNNAME_UY_TT_CardStatus_ID + "=" + cardStatus;
		
		List<MTTCard> lines = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, null)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.list();
		
		return lines;
	}
	
	/***
	 * Obtiene y retorna modelo según cuenta, envio y estado
	 * OpenUp Ltda. Issue # 
	 * @author Guillermo Brust - 11/09/2013
	 * @see
	 * @param ctx
	 * @param accountNo
	 * @param trxName
	 * @return
	 */
	public static MTTCard forIncidenciaAndDeliveryAndStatus(Properties ctx, int uyRReclamoID, int deliveryID, String trxName){
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_UY_R_Reclamo_ID + "=" + uyRReclamoID +
							 " AND " + X_UY_TT_Card.COLUMNNAME_UY_TT_Delivery_ID + " = " + deliveryID;
		
		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.first();
					
		return model;
	}

	
	/***
	 * Obtiene y retorna modelo según cuenta y estado
	 * OpenUp Ltda. Issue # 
	 * @author Guillermo Brust - 11/09/2013
	 * @see
	 * @param ctx
	 * @param accountNo
	 * @param trxName
	 * @return
	 */
	public static MTTCard forAccountAndStatus(Properties ctx, String accountNo, int uyTTCardStatusID, String trxName){
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + "='" + accountNo + "'" +
							 " AND " + X_UY_TT_Card.COLUMNNAME_UY_TT_CardStatus_ID + " = " + uyTTCardStatusID;
		
		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
		.first();
					
		return model;
	}

	
	/***
	 * Genera una nueva incidencia de tracking.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 */
	public void generateIncidencia(){

		try {
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY); 
			
			MRReclamo incidencia = new MRReclamo(getCtx(), 0, get_TrxName());
			incidencia.setDefaultDocType();
			incidencia.setDateTrx(today);
			incidencia.setUY_R_Type_ID(MRType.forValue(getCtx(), "tracking", null).get_ID());
			
			MRCause cause = MRCause.forValue(getCtx(), "tracking", null);

			incidencia.setUY_R_Cause_ID(cause.get_ID());
			incidencia.setPriorityBase(cause.getPriorityBase());
			incidencia.setPriorityManual(cause.getPriorityBase());
			
			// Openup. Sylvie Bouissa 20/19/2014 #Issue 3142 
			// para controlar card action se estaba tomando el atributo tipo de tarjeta (this.getCardType()) y retornaba por ejemplo 'TITULAR' 
			//por ende nunca validaba la reimpresión por lo tanto para las tarjetas que eran reimpresion se generaban dos 
			//incidencias una NUEVA y otra REIMPRESION.
			String valueSubCause ="nueva";
			//String valorActual = this.getCardType().trim();
			String actualCardAction = this.getCardAction().trim();
			if(actualCardAction.equalsIgnoreCase(CARDACTION_Reimpresion)){
				valueSubCause = "reimpresion";
			}else if(actualCardAction.equalsIgnoreCase(CARDACTION_Renovacion)){
				valueSubCause = "renovacion";
			}
//			if (this.getCardType().equalsIgnoreCase(CARDACTION_Reimpresion)){
//				valueSubCause = "reimpresion";
//			}
//			else if (this.getCardType().equalsIgnoreCase(CARDACTION_Renovacion)){
//				valueSubCause = "renovacion";
//			}
			
			incidencia.setuy_r_subcause_id_1(MRSubCause.forValue(getCtx(), valueSubCause, null).get_ID());
			incidencia.setisinmediate(false);
			incidencia.setDescription("Tracking de Tarjetas.");
			incidencia.setUY_R_Canal_ID(MRCanal.forValue(getCtx(), "tracking", null).get_ID());
			incidencia.setReceptor_ID(MUser.forName(getCtx(), "adempiere", null).get_ID());
			incidencia.setUY_R_Area_ID(MRArea.forValue(getCtx(), "tracking", null).get_ID());
			incidencia.setUY_R_PtoResolucion_ID(MRPtoResolucion.forValue(getCtx(), "tracking", null).get_ID());
			incidencia.setIsInternalIssue(false);
			incidencia.setNotificationVia(X_UY_R_Reclamo.NOTIFICATIONVIA_Celular);
			incidencia.setAccountNo(this.getAccountNo());
			incidencia.setUY_R_CanalNotifica_ID(MRCanal.forValue(getCtx(), "tracking", null).get_ID());
			incidencia.setIsObserver(false);
			incidencia.setReclamoResuelto(false);
			incidencia.setReclamoNotificado(false);
			incidencia.setNombreTitular(this.getName());
			incidencia.setNroTarjetaTitular(this.getValue());
			incidencia.setDueDateTitular(this.getDueDate());
			incidencia.setLimCreditoTitular(this.getCreditLimit());
			incidencia.setIsPreNotificacion(false);
			incidencia.setUY_R_ActionType_ID(MRActionType.IDforValue(getCtx(), "clinotifica", null));
			incidencia.setGrpCtaCte(this.getGrpCtaCte());
			incidencia.setGAFCOD(this.getGAFCOD());
			incidencia.setGAFNOM(this.getGAFNOM());
			incidencia.setMLCod(this.getMLCod());
			incidencia.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(), today, 0));
			incidencia.setDocStatus(X_UY_R_Reclamo.DOCSTATUS_Drafted);
			incidencia.setDocAction(X_UY_R_Reclamo.DOCACTION_Apply);

			incidencia.setCedula(this.getCedula());
			incidencia.setCustomerName(this.getName());
			incidencia.setDirection(this.getAddress1());
			incidencia.setEMail(this.getEMail());
			incidencia.setTelephone(this.getTelephone());
			incidencia.setMobile(this.getMobile());
			incidencia.setPostal(this.getPostal());
			
			MRCedulaCuenta cedcta = MRCedulaCuenta.forCedulaCuenta(getCtx(), this.getCedula(), this.getAccountNo(), null);
			if ((cedcta == null) || (cedcta.get_ID() <= 0)){
				cedcta = new MRCedulaCuenta(getCtx(), 0, null);
				cedcta.setValue(this.getCedula());
				cedcta.setAccountNo(this.getAccountNo());
				cedcta.saveEx();
			}
			
			incidencia.setUY_R_CedulaCuenta_ID(cedcta.get_ID());
			
			incidencia.saveEx();
			
			cedcta.setUY_R_Reclamo_ID(incidencia.get_ID());
			cedcta.setInternalCode(incidencia.getDocumentNo());
			cedcta.saveEx();
			

			// Tarjetas
			for (MTTCardPlastic plastic : this.getPlastics()) {
				MRDerivado derivado = new MRDerivado(getCtx(), 0, get_TrxName());
				derivado.setUY_R_Reclamo_ID(incidencia.get_ID());
				derivado.setName(plastic.getName());
				derivado.setAccountNo(this.getAccountNo());
				derivado.setNroTarjetaTitular(plastic.getValue());
				derivado.setDueDateTitular(plastic.getDueDate());
				derivado.setGrpCtaCte(this.getGrpCtaCte());
				derivado.setGAFCOD(plastic.getGAFCOD());
				derivado.setMLCod(plastic.getMLCod());
				derivado.setTipoSocio(plastic.getTipoSocio());
				derivado.saveEx();
			}

			// Aplico inciencia
			if (!incidencia.processIt(DocAction.ACTION_Apply)){
				throw new AdempiereException("No fue posible crear una nueva Incidencia asociada a este Tracking.");
			}
			
			// La dejo en gestion
			incidencia.setStatusReclamo(X_UY_R_Reclamo.STATUSRECLAMO_EnGestion);
			incidencia.setAssignTo_ID(incidencia.getReceptor_ID());
			incidencia.setAssignDateFrom(incidencia.getDateTrx());
			incidencia.saveEx();
			
			// Tracking gestion
			MRTracking track = new MRTracking(getCtx(), 0, get_TrxName());
			track.setDateTrx(new Timestamp(System.currentTimeMillis()));
			track.setAD_User_ID(incidencia.getAssignTo_ID());
			track.setDescription("Inicio de Gestion");
			track.setUY_R_Reclamo_ID(incidencia.get_ID());		
			track.saveEx();

			String action = "";
			
			// Actualizo bandeja de entrada
			action = " update uy_r_inbox set assignto_id =" + incidencia.getAssignTo_ID() + "," +
							" dateassign = '" + incidencia.getAssignDateFrom() + "'," +
							" statusreclamo ='" + incidencia.getStatusReclamo() + "', " +
							" statustarea ='CUR' " +
							" where uy_r_reclamo_id = " + incidencia.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			// Me actualizo la incidencia
			this.setUY_R_Reclamo_ID(incidencia.get_ID());
			this.saveEx();
			
			// Tracking en cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(incidencia.getReceptor_ID());			
			cardTrack.setDescription("Se Genera Inicidencia: " + incidencia.getDocumentNo());
			cardTrack.setUY_TT_Card_ID(this.get_ID());
			cardTrack.setUY_TT_CardStatus_ID(this.getUY_TT_CardStatus_ID());
			cardTrack.saveEx();
			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Impresion de cardcarrier con preview opcional
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 30/09/2013
	 * @see
	 * @return
	 */
	public boolean printCardCarrier(int windowNo, boolean impVale, boolean impCaratulaSol) {
		
		try {

			boolean A4 = true;
			
			String descPrint = " Esta Cuenta requiere IMPRESION DE CARD-CARRIER." + "\n";
			
			if (this.isMaster()){
				descPrint += " Coloque Hoja Card-Carrier de MASTERCARD en la Impresora para Continuar." + "\n";
			}
			else{
				descPrint += " Coloque Hoja Card-Carrier de ITALCRED en la Impresora para Continuar." + "\n";
				if (!this.getCardAction().equalsIgnoreCase(X_UY_TT_Card.CARDACTION_Nueva)){
					A4 = false;
				}
			}
			if(this.getCardDestination().equalsIgnoreCase("DOMICILIO") && impCaratulaSol==true){
				descPrint += " Esta Cuenta requiere IMPRESION DE CARATULA DE SOLICITUD." + "\n ";
			}
			
			if(this.get_ValueAsString("IsValeSigned").trim().equals("false") && impVale == true){
				descPrint += " Esta Cuenta requiere IMPRESION DE VALE." + "\n";
		}
			
			
			ADialog.info (0, null, descPrint);
			
			int processID = 1000286;
			if (A4) processID = 1000287;
			
			MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo ("CardCarrier", processID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("UY_TT_Card_ID", new BigDecimal(this.get_ID()));
			para.saveEx();
			
			ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
			worker.start();     

		} 
		catch (Exception e) {
			throw new AdempiereException();
		}
		
		return true;
	}
	
	/**
	 * Impresión de caratula de solicitud para cuentas que tienen destino domicilio
	 * OpenUp Lda. Issue# 3273 
	 * @author Sylvie Bouissa 08-12-2014
	 */
public boolean printCaratulaSolicitud(int windowNo, boolean hayCajaImpVale) {
		//se agrega la variable boolean porque depende si hay caja para imp de vale o no si se imprime o no el vale en la recepcion
		try {
			
			String descPrint = " Esta Cuenta requiere IMPRESION DE CARATULA DE SOLICITUD." + "\n";
			//Si no se imprime caratula de solicitud, sino se muestra aviso en card carrier
			if(!(this.isNeedPrint())){
				//si no hay caja de impresion de vale se va a mostrar mensaje en la impresion de vale
				if(this.get_ValueAsString("IsValeSigned").trim().equals("false") && hayCajaImpVale == false){
					//descPrint += " Esta Cuenta requiere IMPRESION DE VALE." + "\n";
					//Ahora se acumulan los mensajes para el ultima  impresion
				}else{
					//Se muestra dialogo
					ADialog.info (0, null, descPrint);
				}
				
			}
			
			int processID = MProcess.getProcess_ID("10000014", null); //AD_process UY_RTT_CaratulaSolicitud
//			int processID = 1010358; //AD_process UY_RTT_CaratulaSolicitud - base testing openup_mejora
//			int processID = 1010363; //AD_process UY_RTT_CaratulaSolicitud - base openup
			MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo ("CardCarrier", processID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("UY_TT_Card_ID", new BigDecimal(this.get_ID()));
			para.saveEx();
			
			ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
			worker.start();     

		} 
		catch (Exception e) {
			throw new AdempiereException();
		}
		
		return true;
	}


/**
 * Impresión de vale, usado para cuenta que no tienen vale firmado
 * OpenUp Lda. Issue# 3273 
 * @author Sylvie Bouissa 10-12-2014
 * @param imprimoCaratS 
 * @param printCardcarrier 
 */
public boolean printValeToSign(int windowNo, boolean imprimiCaratS) {
	
	try {
		
		String descPrint = " Esta Cuenta requiere IMPRESION DE VALE." + "\n";
		//si no se imprime card carrier y el destino es domicilio sino se muestra mensaje en card carrier
		if(!(this.isNeedPrint()) && (this.getCardDestination().equalsIgnoreCase("DOMICILIO"))){
			//Si el vale no esta firmado y no hay caja; además se debe mostrar mensaje de imp de vale
			if(imprimiCaratS == true ){
				descPrint =descPrint + " Esta Cuenta requiere IMPRESION DE CARATULA DE SOLICITUD." + "\n";				
			}
			ADialog.info (0, null, descPrint);		
		}
		
		int processID = MProcess.getProcess_ID("10000015", null);
	//	int processID = 1010359; //AD_process UY_RTT_CardVale -base testin openup_mejora
	//	int processID = 1010364; //AD_process UY_RTT_CardVale -base produccion openup
		
		MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
		instance.saveEx();

		ProcessInfo pi = new ProcessInfo ("ImpresionVale", processID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
		
		MPInstancePara para = new MPInstancePara(instance, 10);
		para.setParameter("UY_TT_Card_ID", new BigDecimal(this.get_ID()));
		para.saveEx();
		
		ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
		worker.start();     

	} 
	catch (Exception e) {
		throw new AdempiereException();
	}
	
	return true;
}
	
	/***
	 * Cierra Tracking de Cuenta cuando se Entrega la misma al cliente.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 09/10/2013
	 * @see
	 */
	public void closeTrackingDelivered(Timestamp dateDelivered){
		
		try {

			// Estado entregado
			this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), get_TrxName(), "entregada").get_ID());
			
			
			if (dateDelivered != null){
				this.setDateDelivered(dateDelivered);
				this.setDateLastRun(dateDelivered);
			}
			else{
				this.setDateDelivered(new Timestamp(System.currentTimeMillis()));
				this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			}
			
			this.setIsDeliverable(true);
			this.setIsRetained(false);
			this.setIsRequested(false);
			this.saveEx();
			
			// Marco Incidencia Resuelta.  
			// La incidencia se gestiona automaticamente y se deja como resuelta. Pasandose a Canal de Tracking para luego cerrarse
			// completamente al momento de recibir los acuse de recibo.
			MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();
			// Seteo area y punto de resolucion de la incidencia
			MRArea area = MRArea.forValue(getCtx(), "tracking", null);
			MRPtoResolucion ptoresol = MRPtoResolucion.forValue(getCtx(), "tracking", null);
			reclamo.setUY_R_Area_ID(area.get_ID());
			reclamo.setUY_R_PtoResolucion_ID(ptoresol.get_ID());
			reclamo.saveEx();
			
			
			MRGestion gestion = new MRGestion(getCtx(), 0, get_TrxName());
			gestion.setUY_R_Reclamo_ID(reclamo.get_ID());
			gestion.setReclamoAccionType(X_UY_R_Gestion.RECLAMOACCIONTYPE_NotificacionACliente);
			gestion.setDescription("Gestion Automatica desde Modulo de Tracking de Tarjetas. Tarjetas Entregadas al Cliente.");
			gestion.setDateTrx(new Timestamp(System.currentTimeMillis()));
			gestion.setReceptor_ID(MUser.forName(getCtx(), "adempiere", null).get_ID());
			gestion.setReclamoResuelto(true);
			gestion.setResueltoType(X_UY_R_Gestion.RESUELTOTYPE_Cliente);
			gestion.setIsExecuted(true);
			gestion.setDateExecuted(gestion.getDateTrx());
			gestion.setUY_R_Action_ID(1000002);
			gestion.processAction();
			
			// Impacto legajo
			reclamo.setLegajoFinancial(false, false, null);

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	/***
	 * Cierra Tracking de cuenta cuando se Destruye la cuenta.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 09/10/2013
	 * @see
	 */
	public void closeTrackingDestroyed(){

		try {
			
			// Estado destruida
			this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), get_TrxName(), "destruida").get_ID());
			this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			this.saveEx();

			// Cierro Incidencia.
			MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();
			MRGestion gestion = new MRGestion(getCtx(), 0, get_TrxName());
			gestion.setUY_R_Reclamo_ID(reclamo.get_ID());
			gestion.setReclamoAccionType(X_UY_R_Gestion.RECLAMOACCIONTYPE_CerrarReclamo);
			gestion.setDescription("Gestion Automatica desde Modulo de Tracking de Tarjetas. Tarjetas Destruidas.");
			gestion.setDateTrx(new Timestamp(System.currentTimeMillis()));
			gestion.setReceptor_ID(MUser.forName(getCtx(), "adempiere", null).get_ID());
			gestion.setReclamoResuelto(true);
			gestion.setResueltoType(X_UY_R_Gestion.RESUELTOTYPE_Cliente);
			gestion.setIsExecuted(true);
			gestion.setDateExecuted(gestion.getDateTrx());
			gestion.setUY_R_Action_ID(1000006);
			gestion.processAction();

			// Impacto legajo
			reclamo.setLegajoFinancial(false, true, null);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Ejecuta accion de cambio de direccion que se inicio en una gestion de incidencia.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 15/10/2013
	 * @see
	 * @param gestion
	 */
	public void executeChangeAddress(MRGestion gestion) {
		
		boolean cambioOK = true;
		
		try {
		
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta"); 
			
			// Obtengo punto de distribucion de Casa central
			MDeliveryPoint dpCentral = MDeliveryPoint.forValue(getCtx(), "casacentral", null);

			// Obtengo punto actual
			MDeliveryPoint dpActual = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID_Actual(), null);

			MTTCardStatus stTransito = MTTCardStatus.forValue(getCtx(), get_TrxName(), "enviada");
			MTTCardStatus stPendEnvio = MTTCardStatus.forValue(getCtx(), get_TrxName(), "pendenvio");
			
			// Si esta cuenta tiene como destino RedPagos y esta en estado pendiente de envio o en transito, no debo 
			// permitir cambio de dirección o destino.
			if ((this.getUY_TT_CardStatus_ID() == stTransito.get_ID()) || (this.getUY_TT_CardStatus_ID() == stPendEnvio.get_ID())){
				if ((this.getUY_DeliveryPoint_ID() == config.getUY_DeliveryPoint_ID())
						|| (this.getUY_DeliveryPoint_ID_Actual() == config.getUY_DeliveryPoint_ID())){

					throw new AdempiereException("No es posible cambiar destino o dirección a cuenta con destino Red Pagos "
							+ "y que se encuentra en Tránsito o Pendiente de Envío.");
				}
			}
			
			
			
			// Obtengo incidencia
			MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();

			// Seteo area y punto de resolucion de la incidencia
			MRArea area = MRArea.forValue(getCtx(), "tracking", null);
			MRPtoResolucion ptoresol = MRPtoResolucion.forValue(getCtx(), "tracking", null);
			reclamo.setUY_R_Area_ID(area.get_ID());
			reclamo.setUY_R_PtoResolucion_ID(ptoresol.get_ID());
			reclamo.saveEx();
			
			// Si el punto actual es igual al nuevo destino y ambos son sucursales
			if (dpActual.get_ID() > 0){
				if (dpActual.get_ID() == gestion.getUY_DeliveryPoint_ID_To()){
					if (dpActual.isOwn()){
						cambioOK = false;

						// Cambio estado de cuenta a recepcionada
						this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
						this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						this.saveEx();
					}
				}
			}

			if (cambioOK){
				// Si elige nuevo destino sucursal
				if (gestion.getNuevoDestino().equalsIgnoreCase("S")){
				
					this.setIsAddressChanged(false);
					
					// Si no tengo punto actual
					if (dpActual.get_ID() <= 0){
						
						// Si esta cuenta NO esta en un precinto, significa que o no fue recepcionada en central o esta en central y aun no fue cargada en un bolsin.
						if (this.getUY_TT_Seal_ID() <= 0){
							this.setUY_DeliveryPoint_ID(gestion.getUY_DeliveryPoint_ID_To()); // Guardo nuevo destino
							this.setUY_DeliveryPoint_ID_To(gestion.getUY_DeliveryPoint_ID_To()); // Guardo nuevo destino
							this.setUY_DeliveryPoint_ID_Req(gestion.getUY_DeliveryPoint_ID_To()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
							this.setIsRequested(true);
						}
						else{
							// Esta en un bolsin por lo tanto no tengo por ahora como frenarla y va a ir al viejo destino
							this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
							this.setUY_DeliveryPoint_ID_To(gestion.getUY_DeliveryPoint_ID_To()); // Guardo nuevo destino
							this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
							this.setIsRequested(true);
						}
					}
					else{
						// Tengo punto actual
						
						// Si el punto actual es casa central
						if (dpActual.get_ID() == dpCentral.get_ID()){
							
							// Si esta cuenta NO esta en un precinto, significa que esta en central y aun no fue cargada en un bolsin.
							if (this.getUY_TT_Seal_ID() <= 0){
								this.setUY_DeliveryPoint_ID(gestion.getUY_DeliveryPoint_ID_To()); // Guardo nuevo destino
								this.setUY_DeliveryPoint_ID_To(gestion.getUY_DeliveryPoint_ID_To()); // Guardo nuevo destino
								this.setUY_DeliveryPoint_ID_Req(gestion.getUY_DeliveryPoint_ID_To()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
								this.setIsRequested(true);
							}
							else{
								// Esta en un bolsin por lo tanto no tengo por ahora como frenarla y va a ir al viejo destino
								this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
								this.setUY_DeliveryPoint_ID_To(gestion.getUY_DeliveryPoint_ID_To()); // Guardo nuevo destino
								this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
								this.setIsRequested(true);
							}
						}
						else{
							// La cuenta actualmente esta en un correo o sucursal
							this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
							this.setUY_DeliveryPoint_ID_To(gestion.getUY_DeliveryPoint_ID_To()); // Guardo nuevo destino
							this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
							this.setIsRequested(true);

							// Cambio estado de cuenta a distribucion
							if (!dpActual.isOwn()){
								this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "distribucion").get_ID());	
							}
							else{
								// Si es sucursal la dejo recepcionada para que puedan meterla en un bolsin para casa central. Issue #2482
								this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
							}
							
							this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						}
					}
					
					this.setCardDestination(CARDDESTINATION_Sucursal);				
					this.setDescription(gestion.getDescription()); //OpenUp. Nicolas Sarlabos. 29/10/2013. #1473. Seteo comentarios desde la gestion
					
					if (gestion.getUY_DeliveryPoint_ID_SubAge() > 0){
						MDeliveryPoint delSubAge = new MDeliveryPoint(getCtx(), gestion.getUY_DeliveryPoint_ID_SubAge(), null);
						this.setSubAgencia(delSubAge.getSubAgencyNo());
					}
					
					this.saveEx();
				}
				// RedPagos
				else if (gestion.getNuevoDestino().equalsIgnoreCase("R")){
					
					// Si no tengo indicada subagencia salgo con error
					if (gestion.getUY_DeliveryPoint_ID_SubAge() <= 0){
						throw new AdempiereException("Debe indicar SubAgencia Destino");
					}
					
					this.setIsAddressChanged(false);

					// Destino RedPagos y subagencia
					MDeliveryPoint delPointSubAgencia = new MDeliveryPoint(getCtx(), gestion.getUY_DeliveryPoint_ID_SubAge(), null);

					// Si no tengo punto actual
					if (dpActual.get_ID() <= 0){
						// Si esta cuenta NO esta en un precinto, significa que o no fue recepcionada en central o esta en central y aun no fue cargada en un bolsin.
						if (this.getUY_TT_Seal_ID() <= 0){

							this.setUY_DeliveryPoint_ID(config.getUY_DeliveryPoint_ID()); // Guardo nuevo destino
							this.setUY_DeliveryPoint_ID_To(config.getUY_DeliveryPoint_ID()); // Guardo nuevo destino
							this.setUY_DeliveryPoint_ID_Req(config.getUY_DeliveryPoint_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
							this.setIsRequested(true);
							this.setSubAgencia(delPointSubAgencia.getSubAgencyNo());
						}
						else{
							// Esta en un bolsin por lo tanto no tengo por ahora como frenarla y va a ir al viejo destino
							this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
							this.setUY_DeliveryPoint_ID_To(config.getUY_DeliveryPoint_ID()); // Guardo nuevo destino
							this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
							this.setIsRequested(true);
							this.setSubAgencia(delPointSubAgencia.getSubAgencyNo());
						}
					}
					else{
						// Tengo punto actual
						
						// Si el punto actual es casa central
						if (dpActual.get_ID() == dpCentral.get_ID()){
							
							// Si esta cuenta NO esta en un precinto, significa que esta en central y aun no fue cargada en un bolsin.
							if (this.getUY_TT_Seal_ID() <= 0){
								this.setUY_DeliveryPoint_ID(config.getUY_DeliveryPoint_ID()); // Guardo nuevo destino
								this.setUY_DeliveryPoint_ID_To(config.getUY_DeliveryPoint_ID()); // Guardo nuevo destino
								this.setUY_DeliveryPoint_ID_Req(config.getUY_DeliveryPoint_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
								this.setIsRequested(true);
								this.setSubAgencia(delPointSubAgencia.getSubAgencyNo());
							}
							else{
								// Esta en un bolsin por lo tanto no tengo por ahora como frenarla y va a ir al viejo destino
								this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
								this.setUY_DeliveryPoint_ID_To(config.getUY_DeliveryPoint_ID()); // Guardo nuevo destino
								this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
								this.setIsRequested(true);
								this.setSubAgencia(delPointSubAgencia.getSubAgencyNo());
							}
						}
						else{
							// La cuenta actualmente esta en un correo o sucursal
							this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
							this.setUY_DeliveryPoint_ID_To(config.getUY_DeliveryPoint_ID()); // Guardo nuevo destino
							this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
							this.setIsRequested(true);
							this.setSubAgencia(delPointSubAgencia.getSubAgencyNo());							

							// Cambio estado de cuenta a distribucion
							if (!dpActual.isOwn()){
								this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "distribucion").get_ID());	
							}
							else{
								// Si es sucursal la dejo recepcionada para que puedan meterla en un bolsin para casa central. Issue #2482
								this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
							}
							
							this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						}
					}
					
					this.setCardDestination(CARDDESTINATION_RedPagos);				
					this.setDescription(gestion.getDescription()); //OpenUp. Nicolas Sarlabos. 29/10/2013. #1473. Seteo comentarios desde la gestion
					this.saveEx();
					
				}
				else{
					// Direccion
					
					// Marco cuenta con cambio de direccion
					this.setIsAddressChanged(true);
					this.setDateAddressChanged(new Timestamp(System.currentTimeMillis()));
					this.setAddressOld(this.getAddress1());
					this.setTipoDomicilioOld(this.getTipoDomicilio());
					
					// Particular, laboral, garante u otro
					if (gestion.getNuevoDestino().equalsIgnoreCase("P")) this.setTipoDomicilio(TIPODOMICILIO_Particular);
					else if (gestion.getNuevoDestino().equalsIgnoreCase("L")) this.setTipoDomicilio(TIPODOMICILIO_Laboral);					
					else if (gestion.getNuevoDestino().equalsIgnoreCase("G")) this.setTipoDomicilio(TIPODOMICILIO_Garante);
					else if (gestion.getNuevoDestino().equalsIgnoreCase("O")) this.setTipoDomicilio(TIPODOMICILIO_Otro);
					
					this.setPostal(gestion.getPostal());
					
					String calle = gestion.getCalle();
					if (calle == null) calle = "";
					
					String nroPuerta = gestion.getNroPuerta();
					if (nroPuerta == null){
						nroPuerta = "";
					}
					else{
						nroPuerta = " " + nroPuerta;
					}

					String bloque = gestion.getBloque();
					if (bloque == null){
						bloque = "";
					}
					else{
						bloque = " Bloque " + bloque;
					}
					
					String torre = gestion.getTorre();
					if (torre == null){
						torre = "";
					}
					else{
						torre = " Torre " + torre;
					}
					
					String nroApto = gestion.getNroApto();
					if (nroApto == null){
						nroApto = "";
					}
					else{
						nroApto = " Apto. " + nroApto;
					}
						
					String manzana = gestion.getManzana();
					if (manzana == null){
						manzana = "";
					}
					else{
						manzana =" Mz. " + manzana;
						
					}
					
					String solar = gestion.getSolar();
					if (solar == null){
						solar = "";
					}
					else{
						solar = " Solar " + solar;
					}
					

					String esquina = gestion.getEsquina();
					if (esquina == null){
						esquina = "";
					}
					else{
						esquina = " Esquina " + esquina;
					}
					
					String calle1 = gestion.getCalle1();
					if (calle1 == null){
						calle1 = "";
					}
					else{
						calle1 =" Entre " + calle1;
					}

					String calle2 = gestion.getCalle2();
					if (calle2 == null){
						calle2 = "";
					}
					else{
						calle2 =" y " + calle2;
					}

					this.setAddress1((calle + nroPuerta + bloque + torre + nroApto + manzana + solar +
									  esquina + calle1 + calle2).toUpperCase().trim());
					
					String newCodPostal = gestion.getPostal();
					if (newCodPostal != null){
						if (!newCodPostal.equalsIgnoreCase("")){
							if (!this.getPostal().equalsIgnoreCase(newCodPostal)){
								this.setPostalOld(this.getPostal());
								this.setPostal(newCodPostal);
							}
						}
					}

					MDeliveyPointPostal ppostal = MDeliveyPointPostal.forLocalidadPostal(getCtx(), this.getPostal(), null);
					if (ppostal == null){
						throw new AdempiereException("No se pudo obtener Correo Destino para ese Codigo Postal.");
					}
					MDeliveryPoint dpDestino = (MDeliveryPoint)ppostal.getUY_DeliveryPoint();
					
					/*
					MTTPostalGrp postGrp = MTTPostalGrp.forPostalLiqGrp(getCtx(), this.getPostal(), this.getMLCod(), String.valueOf(this.getGrpCtaCte()).trim(), null);
					if (postGrp != null){
						if (postGrp.getUY_DeliveryPoint_ID() > 0){
							this.setUY_DeliveryPoint_ID(postGrp.getUY_DeliveryPoint_ID());
							this.setUY_DeliveryPoint_ID_To(postGrp.getUY_DeliveryPoint_ID());
						}
						else{
							throw new AdempiereException("No se pudo obtener Correo para Codigo Postal, Modelo de Liquidacion y Grupo Cta.Cte.");
						}
					}
					else{
						throw new AdempiereException("No se pudo obtener Correo para Codigo Postal, Modelo de Liquidacion y Grupo Cta.Cte.");
					}
					*/

					// Si NO tengo punto actual
					if (dpActual.get_ID() <= 0){

						// Si esta cuenta NO esta en un precinto, significa que o no fue recepcionada en central o esta en central y aun no fue cargada en un bolsin.
						if (this.getUY_TT_Seal_ID() <= 0){
							this.setUY_DeliveryPoint_ID(dpDestino.get_ID()); // Guardo nuevo destino
						}
						else{
							// La cuenta esta en casa central en un precinto.
							// Si esta yendo a un correo la dejo asi, y luego le informo al correo del cambio de destino.
							// Si esta yendo a una sucursal, la tengo que requerir para central
							MDeliveryPoint dpAux = (MDeliveryPoint)this.getUY_DeliveryPoint();
							if (dpAux != null){
								if (!dpAux.isPostOffice()){
									this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
									this.setUY_DeliveryPoint_ID_To(dpDestino.get_ID()); // Guardo nuevo destino
									this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
									this.setIsRequested(true);
									this.setIsAddressChanged(false);
									DB.executeUpdateEx("update uy_tt_card set DateAddressChanged = null where uy_tt_card_id="+ this.get_ID(), get_TrxName());
								}
							}
						}

					}
					else{
						// Tengo punto actual
						// Si estoy en casa central
						if (dpActual.get_ID() == dpCentral.get_ID()){
							
							// Si esta cuenta NO esta en un precinto, significa que esta en central y aun no fue cargada en un bolsin.
							if (this.getUY_TT_Seal_ID() <= 0){
								this.setUY_DeliveryPoint_ID(dpDestino.get_ID()); // Guardo nuevo destino
							}
							else{
								// La cuenta esta en casa central en un precinto.
								// Si esta yendo a un correo la dejo asi, y luego le informo al correo del cambio de destino.
								// Si esta yendo a una sucursal, la tengo que requerir para central
								MDeliveryPoint dpAux = (MDeliveryPoint)this.getUY_DeliveryPoint();
								if (dpAux != null){
									if (!dpAux.isPostOffice()){
										this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
										this.setUY_DeliveryPoint_ID_To(dpDestino.get_ID()); // Guardo nuevo destino
										this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
										this.setIsRequested(true);
										this.setIsAddressChanged(false);
										DB.executeUpdateEx("update uy_tt_card set DateAddressChanged = null where uy_tt_card_id="+ this.get_ID(), get_TrxName());
									}
								}
							}
						}
						else{
							// Cuenta esta en sucursal o correo
							// Si esta en sucursal
							if (!dpActual.isPostOffice()){
								this.setUY_DeliveryPoint_ID(dpCentral.get_ID());  // Destino Casa Central ya que tiene que venir para central primero
								this.setUY_DeliveryPoint_ID_To(dpDestino.get_ID()); // Guardo nuevo destino
								this.setUY_DeliveryPoint_ID_Req(dpCentral.get_ID()); // El solicitante es Casa Central ya que primero tiene que pasar por ahi
								this.setIsRequested(true);
								this.setIsAddressChanged(false);
								DB.executeUpdateEx("update uy_tt_card set DateAddressChanged = null where uy_tt_card_id="+ this.get_ID(), get_TrxName());
							}
							// OpenUp. Leonardo Boccone. 17/09/2014. #2947
							//Agrego este else para que  cambie el estado solo cuando el punto actual es correo
							else{
								// Cambio estado de cuenta a distribucion
								//openup Sylvie 03/11/2014 Issue # 3142 se agrega control para no cambiar estado cuando el plastico está entregado
								if(this.getUY_TT_CardStatus_ID() != (MTTCardStatus.forValue(getCtx(), null, "entregada").get_ID())){
									this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "distribucion").get_ID());
									this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
								}
								
							}
							
						}					
					}
					
					this.setCardDestination(CARDDESTINATION_DomicilioParticular);

					// Nueva localidad
					this.setLocalidadOld(this.getlocalidad());
					this.setlocalidad(gestion.getlocalidad());
					
					this.setNeedPrint(true);
					this.setDescription(gestion.getDescription()); //OpenUp. Nicolas Sarlabos. 29/10/2013. #1473. Seteo comentarios desde la gestion
					this.saveEx();

				}
			}

			// Accion de cambio de destino para que quede bien el tracking
			MTTAction action = MTTAction.forValue(getCtx(), "destino", null);
			this.setUY_TT_Action_ID(action.get_ID());
			this.setDateAction(new Timestamp(System.currentTimeMillis()));
			this.saveEx();

			
			// Elimino incidencia de la bandeja de entrada
			DB.executeUpdateEx("delete from uy_r_inbox where uy_r_reclamo_id=" + reclamo.get_ID(), get_TrxName());
			
			MDeliveryPoint dpSol = (MDeliveryPoint)this.getUY_DeliveryPoint();
			
			// Tracking cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(gestion.getReceptor_ID());
			cardTrack.setDescription("Accion Ejecutada. Nuevo Destino y/o Direccion.");
			cardTrack.setobservaciones(" Nuevo Destino : " + dpSol.getName());
			cardTrack.setUY_TT_Card_ID(this.get_ID());
			cardTrack.setUY_TT_CardStatus_ID(this.getUY_TT_CardStatus_ID());
			cardTrack.setUY_DeliveryPoint_ID_Actual(this.getUY_DeliveryPoint_ID_Actual());
			if (this.getUY_TT_Box_ID() > 0){
				cardTrack.setUY_TT_Box_ID(this.getUY_TT_Box_ID());
				if (this.getLocatorValue() > 0) cardTrack.setLocatorValue(this.getLocatorValue());
			}
			if (this.getUY_TT_Seal_ID() > 0){
				cardTrack.setUY_TT_Seal_ID(this.getUY_TT_Seal_ID());
			}
			
			cardTrack.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	
	/***
	 * Obtiene y retorna una lista de modelos de MTTCard, para aquellas que tengan el campo de isLegajoSaved = 'N' y el estado distinto de pendiente de recepción
	 * OpenUp Ltda. Issue #1434
	 * @author Guillermo Brust - 21/10/2013
	 * @see
	 * @return
	 */
	public static List<MTTCard> getCardsForSaveLegajo(Properties ctx, String trxName){
		
		int pendRecepcionStatus = MTTCardStatus.forValue(ctx, trxName, "pendrec").get_ID();
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_IsLegajoSaved + " = 'N'" +
				   " AND " + X_UY_TT_Card.COLUMNNAME_UY_TT_CardStatus_ID + " <> " + pendRecepcionStatus;
		
		List<MTTCard> lines = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName)
		.list();
		
		return lines;
	}

	@Override
	public void setComment(String comment) {
	}

	@Override
	public void setAttachment(MAttachment attachment) {
	}

	//Metodo para poder filtrar tanto por dato cedula o tipode tarjeta
	@Override
	public void changeMQuery(MQuery query) {

		try {
			
			if (query == null) return;
			String whereC = "";
			for (int i = 0; i < query.getRestrictionCount(); i++){
				if (query.getInfoName(i) != null){
					if (query.getInfoName(i).toLowerCase().trim().equalsIgnoreCase("cedula")){
						
						String operator ="", code ="";

						if (query.getOperator(i) != null){
							operator = query.getOperator(i).toString();
						}
						if (query.getCode(i) != null){
							code = query.getCode(i).toString();
						}
						
						if (!operator.equalsIgnoreCase("") && !code.equalsIgnoreCase("")){
							//String whereClause = " uy_tt_card_id in (select distinct uy_tt_card_id from uy_tt_cardplastic where cedula " + operator + " '" + code + "')";
							if(whereC.length()>0){
								whereC += " AND uy_tt_card_id in (select distinct uy_tt_card_id from uy_tt_cardplastic where cedula " + operator + " '" + code + "')";
							}else{
								whereC = " uy_tt_card_id in (select distinct uy_tt_card_id from uy_tt_cardplastic where cedula " + operator + " '" + code + "')";
							}
							//query.addRestriction(whereClause, false, 0);
						}
					}
					else if(query.getInfoName(i).toLowerCase().trim().equalsIgnoreCase("cardtype"))
					{
						String operator ="", code ="";

						if (query.getOperator(i) != null){
							operator = query.getOperator(i).toString();
						}
						if (query.getCode(i) != null){
							code = query.getCode(i).toString();
						}
						
						if (!operator.equalsIgnoreCase("") && !code.equalsIgnoreCase("")){
							//String whereClause = " uy_tt_card_id in (select distinct uy_tt_card_id from uy_tt_cardplastic where cardtype " + operator + " '" + code + "')";
							if(whereC.length()>0){
								whereC += " AND uy_tt_card_id in (select distinct uy_tt_card_id from uy_tt_cardplastic where cardtype " + operator + " '" + code + "')";
							}else{
								whereC = " uy_tt_card_id in (select distinct uy_tt_card_id from uy_tt_cardplastic where cardtype " + operator + " '" + code + "')";
							}
							//query.addRestriction(whereClause, false, 0);
						}
					}
					
					else if (query.getInfoName(i).toLowerCase().trim().equalsIgnoreCase("nrodoc")){
						
						String operator ="", code ="";

						if (query.getOperator(i) != null){
							operator = query.getOperator(i).toString();
						}
						if (query.getCode(i) != null){
							code = query.getCode(i).toString();
						}
						
						if (!operator.equalsIgnoreCase("") && !code.equalsIgnoreCase("")){
							if(whereC.length()>0){
								whereC += " AND uy_tt_card.uy_r_reclamo_id in (select uy_r_reclamo_id from uy_r_reclamo where documentno like '" + code + "')";
							}else{
								whereC = " uy_tt_card.uy_r_reclamo_id in (select uy_r_reclamo_id from uy_r_reclamo where documentno like '" + code + "')";
							}
						}
					}

				}
			}
			query.addRestriction(whereC, false, 0);
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}	

	
	/***
	 * Ejecuta accion de NO cambio de direccion que se inicio en una gestion de incidencia.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 15/10/2013
	 * @see
	 * @param gestion
	 */
	public void executeNoChangeAddress(MRGestion gestion) {

		try {
			
			// Dejo cuenta en estado recepcionada o pendiente de envio segun tenga o no una caja asociada
			
			/*openUp 03/11/2014 - Sylvie Bouissa Issue #3206 
			 Se comentan las siguientes líneas que setea estado en "pendenvio", porque según lo coordinado entre Leo y Vanina en la peticion #2947
			 2- Mantener destino actual: No cambiar por defecto el estado a recepcionada.
			 si bien no se cambia el estado a recepcionada se está seteando estado a "PENDIENTE DE ENVÍO" lo que debe es dejar el estado como estaba
			*/
//			if (this.getUY_TT_Seal_ID() > 0){ //-- Consulta si está en un bolsín no si esta en una caja !! -  Sylvie Bouissa
//				this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "pendenvio").get_ID());				
//				this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
//			}
			//openUp 03/11/2014 - Sylvie Bouissa Issue #3206 
			//Acá se consulta si esta en una caja .. pero se debe dejar el estado como está, 
			//si no está en una caja luego se consulta si esta en un correo para setear en "EN DISTRIBUCION" si corresponde.
			if(this.getUY_TT_Box_ID()>0){
//				this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "pendenvio").get_ID());
				this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			}
			else{
				// Recepcionada por defecto
				// OpenUp. Leonardo Boccone. 17/09/2014. #2947
				//Cometo estas dos lineas para que no cambie el estado a recepcionada por defecto.
				//this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
				//this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				
				// Si tengo punto actual y el mismo es un correo, dejo la cuenta en distribucion
				if (this.getUY_DeliveryPoint_ID_Actual() > 0){
					MDeliveryPoint dpActual = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID_Actual(), null);
					if (dpActual.isPostOffice()){
						//openup Sylvie 03/11/2014 Issue # 3142 se agrega control para no cambiar estado cuando el plastico está entregado
						if(this.getUY_TT_CardStatus_ID() != (MTTCardStatus.forValue(getCtx(), null, "entregada").get_ID())){
							this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "distribucion").get_ID());
							this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						}						
					}
				}
			}
			this.saveEx();
			
			// Obtengo incidencia
			MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();
			
			// Seteo area y punto de resolucion de la incidencia
			MRArea area = MRArea.forValue(getCtx(), "tracking", null);
			MRPtoResolucion ptoresol = MRPtoResolucion.forValue(getCtx(), "tracking", null);
			reclamo.setUY_R_Area_ID(area.get_ID());
			reclamo.setUY_R_PtoResolucion_ID(ptoresol.get_ID());
			reclamo.saveEx();

			
			// Elimino incidencia de la bandeja de entrada
			DB.executeUpdateEx("delete from uy_r_inbox where uy_r_reclamo_id=" + reclamo.get_ID(), get_TrxName());
			
			MDeliveryPoint dpSol = (MDeliveryPoint)this.getUY_DeliveryPoint();
			
			// Tracking cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(gestion.getReceptor_ID());
			cardTrack.setDescription("Accion Ejecutada. Mantiene Destino/Domicilio actual.");
			cardTrack.setobservaciones("Destino Actual : " + dpSol.getName());
			cardTrack.setUY_TT_Card_ID(this.get_ID());
			cardTrack.setUY_TT_CardStatus_ID(this.getUY_TT_CardStatus_ID());
			cardTrack.setUY_DeliveryPoint_ID_Actual(this.getUY_DeliveryPoint_ID_Actual());
			if (this.getUY_TT_Box_ID() > 0){
				cardTrack.setUY_TT_Box_ID(this.getUY_TT_Box_ID());
				if (this.getLocatorValue() > 0) cardTrack.setLocatorValue(this.getLocatorValue());
			}
			if (this.getUY_TT_Seal_ID() > 0){
				cardTrack.setUY_TT_Seal_ID(this.getUY_TT_Seal_ID());
			}
			
			cardTrack.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	
	/***
	 * Ejecuta accion que implica una verificacion de cuenta.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 05/11/2013
	 * @see
	 * @param gestion
	 */
	public void executeVerifyAction(MRGestion gestion) {

		try {
			
			// Dejo cuenta en estado recepcionada o pendiente de envio segun tenga o no una caja asociada
			if (this.getUY_TT_Seal_ID() > 0){
				this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "pendenvio").get_ID());
				this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
			}
			else{
				// Recepcionada por defecto
				this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "recepcionada").get_ID());
				this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				
				// Si tengo punto actual y el mismo es un correo, dejo la cuenta en distribucion
				if (this.getUY_DeliveryPoint_ID_Actual() > 0){
					MDeliveryPoint dpActual = new MDeliveryPoint(getCtx(), this.getUY_DeliveryPoint_ID_Actual(), null);
					if (dpActual.isPostOffice()){
						this.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "distribucion").get_ID());
						this.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					}
				}
				
			}
			this.setInVerification(true);
			this.setUY_R_AccionPtoResol_ID(gestion.getUY_R_AccionPtoResol_ID());
			this.saveEx();
			
			// Obtengo incidencia
			MRReclamo reclamo = (MRReclamo)this.getUY_R_Reclamo();
			
			// Seteo area y punto de resolucion de la incidencia
			MRArea area = MRArea.forValue(getCtx(), "tracking", null);
			MRPtoResolucion ptoresol = MRPtoResolucion.forValue(getCtx(), "tracking", null);
			reclamo.setUY_R_Area_ID(area.get_ID());
			reclamo.setUY_R_PtoResolucion_ID(ptoresol.get_ID());
			reclamo.saveEx();

			
			// Elimino incidencia de la bandeja de entrada
			DB.executeUpdateEx("delete from uy_r_inbox where uy_r_reclamo_id=" + reclamo.get_ID(), get_TrxName());
			
			MDeliveryPoint dpSol = (MDeliveryPoint)this.getUY_DeliveryPoint();
			
			String description = "Accion Ejecutada. ";
			MRAccionPtoResol actionResol = (MRAccionPtoResol)gestion.getUY_R_AccionPtoResol();
			if (actionResol != null){
				description += actionResol.getName();
			}
			
			// Tracking cuenta
			MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
			cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
			cardTrack.setAD_User_ID(gestion.getReceptor_ID());
			cardTrack.setDescription(description);
			cardTrack.setobservaciones("Destino Actual : " + dpSol.getName());
			cardTrack.setUY_TT_Card_ID(this.get_ID());
			cardTrack.setUY_TT_CardStatus_ID(this.getUY_TT_CardStatus_ID());
			cardTrack.setUY_DeliveryPoint_ID_Actual(this.getUY_DeliveryPoint_ID_Actual());
			if (this.getUY_TT_Box_ID() > 0){
				cardTrack.setUY_TT_Box_ID(this.getUY_TT_Box_ID());
				if (this.getLocatorValue() > 0) cardTrack.setLocatorValue(this.getLocatorValue());
			}
			if (this.getUY_TT_Seal_ID() > 0){
				cardTrack.setUY_TT_Seal_ID(this.getUY_TT_Seal_ID());
			}
			
			cardTrack.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	/**
	 * 
	 * OpenUp Ltda. Issue # 2958
	 * @author leonardo.boccone - 19/09/2014
	 * @see Metodo para traer cuenta por numero de cuenta y numero de tarjeta
	 * @param ctx
	 * @param accountNo
	 * @param cardNo
	 * @param trxName
	 * @return
	 */
	public static MTTCard forAccountNoandCardNo(Properties ctx,String accountNo, String cardNo, String trxName) {
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + "='"+ accountNo + "'";

		MTTCard model = null;

		List<MTTCard> lines = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName).setOrderBy(" uy_tt_card.uy_tt_card_id desc ").list();

		if (lines != null) {
			for (MTTCard line : lines) {
				List<MTTCardPlastic> plastics = line.getPlastics();
					//solo retorno el modelo si el nuemero de tarjeta esta dentro de los plasticos de la cuenta.
					for (MTTCardPlastic plastic : plastics) {
						if(plastic.getNroTarjetaNueva()!=null){
							if(plastic.getValue().equalsIgnoreCase(cardNo)){
								model = line;
								break;
							}
						}					
					}
			}
		}

		return model;
	}
	/**
	 * 
	 * OpenUp Ltda. Issue # 2958
	 * @author leonardo.boccone - 19/09/2014
	 * @see se crea metodo para traer cuenta por numero de cuenta y precinto
	 * @param ctx
	 * @param accountNo
	 * @param uy_TT_Seal_ID
	 * @param get_TrxName
	 * @return
	 */
	public static MTTCard forAccountOpenandSeal(Properties ctx, String accountNo, int uy_TT_Seal_ID, String get_TrxName) {
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + "='" + accountNo + "'" + " AND "
				+ X_UY_TT_Card.COLUMNNAME_UY_TT_Seal_ID + " = " + uy_TT_Seal_ID;

		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, get_TrxName).first();
		if(model != null){
			if (model.isOpenForTracking()) {
				return model;
			}
		}
		return model;
	}
	/**
	 * 
	 * OpenUp Ltda. Issue # 2958
	 * @author leonardo.boccone - 19/09/2014
	 * @see Se trae cuenta por numero de cuenta y cedula
	 * @param ctx
	 * @param accountNo
	 * @param cedula
	 * @param get_TrxName
	 * @return
	 */
	public static MTTCard forAccountOpenandCI(Properties ctx, String accountNo, String cedula, String get_TrxName) {
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + "='" + accountNo + "'" + " AND "
				+ X_UY_TT_Card.COLUMNNAME_Cedula + " = " + cedula;

		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, get_TrxName).first();
		if(model != null){
			if (model.isOpenForTracking()) {
				return model;
			}
		}
		
		return model;
	}
	/**
	 * 
	 * OpenUp Ltda. Issue # 2958
	 * @author leonardo.boccone - 22/09/2014
	 * @see    Traer cuenta por numero de cuenta delivert Point y cajas utilizadas en la carga del bolsin
	 * @param ctx
	 * @param accountNo
	 * @param uy_DeliveryPoint_ID_From
	 * @param boxes
	 * @param get_TrxName
	 * @return
	 */
	public static MTTCard forAccountDeliveryPointandBoxes(Properties ctx,
			String accountNo, int uy_DeliveryPoint_ID_From,
			List<MTTSealLoadBox> boxes, String get_TrxName) {

		String whereClause = X_UY_TT_Card.COLUMNNAME_AccountNo + "='"
				+ accountNo + "'" + " AND "
				+ X_UY_TT_Card.COLUMNNAME_UY_DeliveryPoint_ID_Actual + " = "
				+ uy_DeliveryPoint_ID_From;

		MTTCard model = null;// new Query(ctx, I_UY_TT_Card.Table_Name,
								// whereClause, get_TrxName).first();
		List<MTTCard> lines = new Query(ctx, I_UY_TT_Card.Table_Name,
				whereClause, get_TrxName).setOrderBy(
				" uy_tt_card.uy_tt_card_id desc ").list();
		for (MTTCard card : lines) {
			if (card.isOpenForTracking()) {
				for (MTTSealLoadBox box : boxes) {
					MTTBox aux = new MTTBox(ctx, box.getUY_TT_Box_ID(),
							get_TrxName);
					if (card.getUY_TT_Box_ID() == aux.get_ID()) {
						model = card;
						break;
					}

				}
			}
		}
		return model;
	}
	
	// INI Openup Sylvie Bouissa 01-12-2014 Issue# 3273 MEJORA 

	/**
	 * OpenUp Ltda. Issue # 3273
	 * @author Sylvie.Bouissa - 01/12/2014
	 * @see    Seteo los días que se coordina con el cliente para la entrega de la tarjeta
	 * @param dias (string obtenido desde fdu en el campo CliEntDia)
	 * @return
	 
	 */
	public void ingresarDias(String dias) {
		// TODO Auto-generated method stub
		try{
			if(!dias.equals("")){
				if(dias.length()==1){
					this.setDiaEntregaAlternativa(Integer.valueOf(dias));
				}else {
					int valor = Integer.parseInt(dias);
					for(int i = 0; i < dias.length(); i++){
						int numero = 0; 
						numero = valor % 10; // obtengo el último caracter
						valor = valor / 10; // quito el caracter que obtuve
						this.setDiaEntregaAlternativa(numero);
					}
				}		
			}
		}catch(Exception e){
			throw new AdempiereException("Valor dias de entrega al cliente inválido");
		}
		
	}
	
	private void setDiaEntregaAlternativa(Integer numero) {
		// TODO Auto-generated method stub
		if(numero>0){
			switch(numero){
			case 1:
				this.set_ValueOfColumn("Lunes", true);
				break;
			case 2:
				this.set_ValueOfColumn("Martes", true);
				break;
			case 3:
				this.set_ValueOfColumn("Miercoles", true);
				break;
			case 4:
				this.set_ValueOfColumn("Jueves", true);
				break;
			case 5:
				this.set_ValueOfColumn("Viernes", true);
				break;
			case 6:
				this.set_ValueOfColumn("Sabado", true);
				break;
			}
		}
	}
	
	// FIN Openup Sylvie Bouissa 01-12-2014 Issue# 3273 MEJORA 
	
	//INI Openup Sylvie Bouissa 02-12-2014 Issue# 3273 MEJORA 
	public void parsearDias(String dias) {
		String formato = "";
		try{
			if(!dias.equals("")&&dias.length()>0){
				char[] diasEntrega = dias.toCharArray();
				for(int i=0;i<diasEntrega.length;i++){ 
					String d =String.valueOf(diasEntrega[i]);
					int valor = Integer.parseInt(d);
					if(formato.length()>0){
						formato += ","+addDia(valor);
					}else{
						formato += addDia(valor);//this.setDiaEntregaAlternativa(numero);
					}
				}
				if(formato.length()>0){
					this.set_Value("DiasEntrega", formato);
				}
				//if()System.out.println(retorno);
			}
		}catch(Exception e){
			throw new AdempiereException("Valor dias de entrega al cliente inválido");
		}
	}

	private String addDia(int numero) {
		// TODO Auto-generated method stub
		if(numero>0){
			switch(numero){
			case 1:
				return "Lu";
			case 2:
				return "Ma";
			case 3:
				return "Mi";
			case 4:
				return "Ju";
			case 5:
				return "Vi";
			case 6:
				return "Sa";
			}
		}
		return "";
	}
	//FIN Openup Sylvie Bouissa 02-12-2014 Issue# 3273 MEJORA 
	

	/***
	 * Obtiene y retorna seguimiento de cuenta segun id de incidencia recibido.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 12, 2015
	 * @param ctx
	 * @param uyRReclamoID
	 * @param trxName
	 * @return
	 */
	public static MTTCard forIncidencia(Properties ctx,	int uyRReclamoID, String trxName){
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_UY_R_Reclamo_ID + "=" + uyRReclamoID;
		
		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName).first();

		return model;
		
	}

	
	/***
	 * Obtiene y retorna seguimiento de cuenta segun numero de contrato asociado 
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 12, 2015
	 * @param ctx
	 * @param uyRReclamoID
	 * @param trxName
	 * @return
	 */
	public static MTTCard forSolNro(Properties ctx,	String value, String trxName){
		
		String whereClause = X_UY_TT_Card.COLUMNNAME_solnro + "='" + value + "'";
		MTTCard model = new Query(ctx, I_UY_TT_Card.Table_Name, whereClause, trxName).first();

		return model;
		
	}

	
	/**
	 * Comentario desde Git 24-09-2015 16:27 hs
	 * Segundo intento 16:30 SBT
	 * Tercer intento 16:35 SBT
	 */
	
}
