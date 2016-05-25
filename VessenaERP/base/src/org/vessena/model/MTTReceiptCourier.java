/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;



/**
 * @author Nicolas
 *
 */
public class MTTReceiptCourier extends X_UY_TT_ReceiptCourier implements DocAction{

	/**
	 * 
	 */
	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = 5210222733561706618L;

	/**
	 * @param ctx
	 * @param UY_TT_ReceiptCourier_ID
	 * @param trxName
	 */
	public MTTReceiptCourier(Properties ctx, int UY_TT_ReceiptCourier_ID,
			String trxName) {
		super(ctx, UY_TT_ReceiptCourier_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTReceiptCourier(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MTTSeal seal = MTTSeal.forValue(getCtx(), get_TrxName(), this.getPrecinto()); //instancio el precinto
		
		if(seal != null) { //si el precinto existe
			
			MDeliveryPoint point = new MDeliveryPoint(getCtx(),seal.getUY_DeliveryPoint_ID_To(),get_TrxName()); //instancio punto de distribucion destino del precinto
			
			if(this.getUY_DeliveryPoint_ID() != seal.getUY_DeliveryPoint_ID_To()){ //si el punto de dist. ingresado es distinto al del precinto muestro error
				
				throw new AdempiereException ("El precinto '" + this.getPrecinto() + "' no corresponde al punto de distribucion '" + point.getName() + "'");
			
			} else if (!seal.getSealStatus().equalsIgnoreCase("CERRADO")) throw new AdempiereException ("El precinto no se encuentra en estado CERRADO"); //verifico estado del precinto
			
			this.setUY_TT_Seal_ID(seal.get_ID()); //guardo el ID del precinto
			this.setQtyBook(seal.getQtyBook()); //seteo cantidad enviada			
			
		} else throw new AdempiereException ("No existe precinto con codigo '" + this.getPrecinto() + "'");
				
		return true;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
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

		this.loadData();

		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		
		return true;
	}
	
	public void loadData(){
		
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			sql = "select c.uy_tt_card_id,l.name,c.address1,c.localidad,c.postal, c.uy_r_reclamo_id " +
                  " from uy_tt_sealload s" +
                  " inner join uy_tt_sealloadline l on s.uy_tt_sealload_id=l.uy_tt_sealload_id" +
                  " inner join uy_tt_card c on l.uy_tt_card_id=c.uy_tt_card_id" +
                  " where s.uy_tt_seal_id = " + this.getUY_TT_Seal_ID() +
                  " order by l.uy_tt_sealload_id, l.updated";
			
			pstmt = DB.prepareStatement(sql, null); 
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				MTTReceiptCourierLine line = new MTTReceiptCourierLine(getCtx(),0,get_TrxName());
				line.setUY_TT_ReceiptCourier_ID(this.get_ID());
				line.setUY_TT_Card_ID(rs.getInt("uy_tt_card_id"));
				line.setName(rs.getString("name"));
				line.setAddress1(rs.getString("address1"));
				line.setlocalidad(rs.getString("localidad"));
				line.setPostal(rs.getString("postal"));
				line.setIsValid(true);
				line.setUY_R_Reclamo_ID(rs.getInt("uy_r_reclamo_id"));
				line.saveEx();				
			}		
			
			List<MTTReceiptCourierLine> lines = this.getValidLines(); //obtengo lineas
			
			if(lines.size() <= 0) throw new AdempiereException("No hay lineas para procesar");
			
		}catch (Exception e) {
			throw new AdempiereException(e.toString());
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}		
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		this.updateAccounts();
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}

	/***
	 * Actualiza estado de cuentas. Tiene en cuenta procesos para subagencias.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 22, 2015
	 */
	public void updateAccounts(){
		
		try {

			int count = 0;
			boolean tieneSubAgencias = false;
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			MDeliveryPoint dpActual = (MDeliveryPoint)this.getUY_DeliveryPoint();
			
			if (dpActual.get_ID() == config.getUY_DeliveryPoint_ID()) tieneSubAgencias = true;
					
			List<MTTReceiptCourierLine> lines = this.getValidLines(); //obtengo lineas marcadas para procesar
			
			if(lines.size() <= 0) throw new AdempiereException("No hay lineas seleccionadas para procesar");
			
			MTTCardStatus status = MTTCardStatus.forValue(getCtx(), get_TrxName(), "recepcionada");
			
			if(status == null) throw new AdempiereException("No se encontro el estado 'RECEPCIONADA'");
			
			MTTCardStatus enviada = MTTCardStatus.forValue(getCtx(), null, "enviada");
			MTTCardStatus pendiente = MTTCardStatus.forValue(getCtx(), null, "pendrec");
			
			for (MTTReceiptCourierLine rLine: lines){			
				
				int cardID = rLine.getUY_TT_Card_ID();
				
				MTTCard card = new MTTCard(getCtx(), cardID, get_TrxName());
				
				if(!enviada.equals(card.getUY_TT_CardStatus())&&!pendiente.equals(card.getUY_TT_CardStatus())){
					throw new AdempiereException("la cuenta nº: "+card.getAccountNo()+" no tiene un estado valido para ser recepcionada por el punto");
				}
				
				// Actualizo cuenta solamente si no es un punto de distribución central con subagencias
				if (!tieneSubAgencias){

					card.setUY_TT_CardStatus_ID(status.get_ID()); //cambio estado de la tarjeta
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
					card.setUY_DeliveryPoint_ID_Actual(this.getUY_DeliveryPoint_ID()); //cambio el punto de distribucion actual de la tarjeta
					card.setDateAssign(new Timestamp(System.currentTimeMillis()));
					card.setDiasActual(0);
					card.saveEx();
					
					//seteo a null el ID de precinto
					DB.executeUpdateEx("update uy_tt_card set uy_tt_seal_id = null where uy_tt_card_id = " + cardID, get_TrxName()); 
					
					// Quito cuenta de precinto
					DB.executeUpdateEx(" delete from uy_tt_sealcard where uy_tt_card_id =" + card.get_ID(), get_TrxName());
				}
				
				// Tracking cuenta (siempre hago el tracking de recepcionada)
				MTTCardTracking cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
				cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
				cardTrack.setAD_User_ID(this.getAD_User_ID());
				cardTrack.setDescription("Recepcionada en : " + dpActual.getName());
				cardTrack.setUY_TT_Card_ID(card.get_ID());
				cardTrack.setUY_TT_CardStatus_ID(status.get_ID());
				cardTrack.setUY_DeliveryPoint_ID_Actual(dpActual.get_ID());
				cardTrack.saveEx();

				// Si es punto de distribucion central que envía cuentas a subagencia
				if (tieneSubAgencias){

					// Tracking en cuenta ahora como enviada a subagencia
					MTTCardTracking cardTrack2 = new MTTCardTracking(getCtx(), 0, get_TrxName());
					cardTrack2.setDateTrx(new Timestamp(System.currentTimeMillis()));
					cardTrack2.setAD_User_ID(this.getAD_User_ID());
					String subAgencia = card.getSubAgencia();
					if (subAgencia == null) subAgencia = "";
					cardTrack2.setDescription("Enviada a SubAgencia: " + subAgencia);
					cardTrack2.setUY_TT_Card_ID(card.get_ID());
					cardTrack2.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
					cardTrack2.setUY_TT_Seal_ID(card.getUY_TT_Seal_ID());
					cardTrack2.saveEx();
				}
				
				count ++; //incremento contador de lineas
							
			}	
			
			this.setQtyCount(count); //seteo cantidad de recibidas

		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

	}
	
	/***
	 * Obtiene y retorna lineas marcadas como recibido.
	 * OpenUp Ltda. Issue #1378
	 * @author Nicolas Sarlabos - 09/10/2013
	 * @see
	 * @return
	 */
	public List<MTTReceiptCourierLine> getValidLines(){

		String whereClause = X_UY_TT_ReceiptCourierLine.COLUMNNAME_UY_TT_ReceiptCourier_ID + "=" + this.get_ID() + " AND " + X_UY_TT_ReceiptCourierLine.COLUMNNAME_IsValid + " = 'Y'";

		List<MTTReceiptCourierLine> lines = new Query(getCtx(), I_UY_TT_ReceiptCourierLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
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
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
