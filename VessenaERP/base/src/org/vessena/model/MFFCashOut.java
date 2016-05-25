/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MAttachment;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_C_Invoice;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;

/**
 * @author Nicolas
 *
 */
public class MFFCashOut extends X_UY_FF_CashOut implements DocAction, IDynamicWF {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	public boolean isVoidAuto = false;
	public String msgVoidAuto = "";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7536155129872198864L;

	/**
	 * @param ctx
	 * @param UY_FF_CashOut_ID
	 * @param trxName
	 */
	public MFFCashOut(Properties ctx, int UY_FF_CashOut_ID, String trxName) {
		super(ctx, UY_FF_CashOut_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFCashOut(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
		
		MDocType doc = (MDocType) this.getC_DocType();
		
		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("cashrepay")){
				//verifico adjuntos
				this.validateAttachment();		
				
				//si hay salida de FF impacto en trazabilidad de la misma
				if(this.getUY_FF_CashOut_ID_1() > 0){
					
					MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName());
					
					MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
					track.setUY_FF_CashOut_ID(cash.get_ID());
					track.setDateTrx(new Timestamp(System.currentTimeMillis()));
					track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
					track.setDescription("Se aprueba rendicion de fondo fijo N° " + this.getDocumentNo());
					track.saveEx();					
				}				
				
			} else if(doc.getValue().equalsIgnoreCase("cashout")){
				
				MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
				track.setUY_FF_CashOut_ID(this.get_ID());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se aprueba la salida de fondo fijo");
				track.saveEx();			
			}
		}
		
		// Refresco atributos
		this.setProcessed(true);
		this.setDocStatus(DocumentEngine.STATUS_Approved);
		this.setDocAction(DocAction.ACTION_Complete);
				
		return true;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
				
		//valido existencia de comentarios
		if(this.getobservaciones()==null || this.getobservaciones().equalsIgnoreCase("")) throw new AdempiereException("Debe ingresar motivo de rechazo en el campo de comentarios");
		
		MDocType doc = new MDocType(getCtx(), this.getC_DocType_ID(), get_TrxName());
		if (doc.getValue() != null){
			if(doc.getValue().equalsIgnoreCase("cashout")){
				
				MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
				track.setUY_FF_CashOut_ID(this.get_ID());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se rechaza la salida de fondo fijo");
				track.setobservaciones(this.getobservaciones());
				track.saveEx();	

			} else if(doc.getValue().equalsIgnoreCase("cashrepay")){
				
				//si hay salida de FF impacto en trazabilidad de la misma
				if(this.getUY_FF_CashOut_ID_1() > 0){
					
					MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName());
					
					MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
					track.setUY_FF_CashOut_ID(cash.get_ID());
					track.setDateTrx(new Timestamp(System.currentTimeMillis()));
					track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
					track.setDescription("Se rechaza rendicion de fondo fijo N° " + this.getDocumentNo());
					track.setobservaciones(this.getobservaciones());
					track.saveEx();					
				}				
			}
		}
		
		this.setProcessed(true);
		this.setDocStatus(DocumentEngine.STATUS_NotApproved);
		this.setDocAction(DocAction.ACTION_Void);
		
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MDocType doc = (MDocType) this.getC_DocType();
		
		if(newRecord || (!newRecord && is_ValueChanged("Description"))) {
			
			if(this.getDescription()!=null) this.setDescription(this.getDescription().toUpperCase());
		}
		
		if(doc.getValue()!=null){
			
			if(doc.getValue().equalsIgnoreCase("cashrepay")){
				
				if(newRecord || (!newRecord &&is_ValueChanged("UY_FF_CashOut_ID_1"))){

					if(this.getUY_FF_CashOut_ID_1() > 0){

						MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName());
						this.setAmount(cash.getGrandTotal()); //seteo importe de salida de caja
						this.setDescription(cash.getDescription());
						this.setUY_FF_Branch_ID(cash.getUY_FF_Branch_ID());
						this.setUY_POSection_ID(cash.getUY_POSection_ID());
						this.setC_Currency_ID(cash.getC_Currency_ID());

					} else this.setAmount(Env.ZERO);

				}					
				
			}		
			
		}	
		
		return true;
	}
	
	public boolean requestIt() {
		
		if(this.getGrandTotal().compareTo(Env.ZERO) <= 0) throw new AdempiereException("El importe total debe ser mayor a cero");
		
		MDocType doc = (MDocType) this.getC_DocType();
		
		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("cashrepay")){

				String msgInvNum = this.getNumInvoices();				
				boolean ok = ADialog.ask(-1, null, msgInvNum);	//pido confirmacion de numeros de facturas

				if(ok){

					this.validateAttachment(); //verifico adjuntos
					
					//si hay salida de FF verifico importes e impacto en trazabilidad de la misma
					if(this.getUY_FF_CashOut_ID_1() > 0){					

						MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName());

						MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
						track.setUY_FF_CashOut_ID(cash.get_ID());
						track.setDateTrx(new Timestamp(System.currentTimeMillis()));
						track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
						track.setDescription("Se solicita rendicion de fondo fijo N° " + this.getDocumentNo());
						track.saveEx();					
					}
					
				} else throw new AdempiereException("Ha seleccionado cancelar la solicitud. Verifique los numeros de facturas");
				
			} else if(doc.getValue().equalsIgnoreCase("cashout")){
				
				MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
				track.setUY_FF_CashOut_ID(this.get_ID());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se solicita salida de fondo fijo");
				track.saveEx();				
			}
		}	
			
		// Refresco atributos
		this.setProcessed(true);
		this.setDocStatus(DocumentEngine.STATUS_Requested);
		this.setDocAction(DocAction.ACTION_Approve);		
		
		return true;
	}

	/***
	 * Metodo que pide verificacion de numeros de facturas a generarse.
	 * OpenUp Ltda. Issue #1419
	 * @author Nicolas Sarlabos - 23/01/2014
	 * @see
	 * @return
	 */
	private String getNumInvoices() {
		
		String value = "Se generaran las facturas con los siguientes numeros, por favor verifique \n";
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;			
		
		try{
			
			sql = "select distinct factura" +
                  " from uy_ff_cashoutline" +
                  " where uy_ff_cashout_id = " + this.get_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
		    	value += rs.getString("factura") + "\n";
		    	
		    }		    
		    		    			
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
		return value;
		
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		MDocType doc = (MDocType) this.getC_DocType();

		if(doc.getValue()!=null){

			if(doc.getValue().equalsIgnoreCase("cashrepay")){ //si es rendicion de FF y tengo salida de FF cargo datos desde la misma

				if(newRecord || (!newRecord && is_ValueChanged("UY_FF_CashOut_ID_1"))){

					this.deleteOldLines();

					if(this.getUY_FF_CashOut_ID_1() > 0) this.loadCashData();	

				}

			}		
		}		
		
		return true;
	}
	
	/***
	 * Metodo elimina las lineas de detalle en caso que se seleccione una nueva salida de caja.
	 * OpenUp Ltda. Issue #1419
	 * @author Nicolas Sarlabos - 18/10/2013
	 * @see
	 * @return
	 */
	public void deleteOldLines(){
		
		String sql = "delete from uy_ff_cashoutline where uy_ff_cashout_id = " + this.get_ID();
		DB.executeUpdate(sql,get_TrxName());
				
	}
	
	/***
	 * Metodo que carga los datos de las lineas de la salida de caja seleccionada.
	 * OpenUp Ltda. Issue #1419
	 * @author Nicolas Sarlabos - 19/12/2013
	 * @see
	 * @return
	 */
	public void loadCashData(){
		
		MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName());
		
		//cargo datos de las lineas		
		List<MFFCashOutLine> lines = cash.getLines();
		
		for ( MFFCashOutLine cLine: lines){
			
			MFFCashOutLine l = new MFFCashOutLine(getCtx(),0,get_TrxName());
			
			l.setUY_FF_CashOut_ID(this.get_ID());
			l.setfactura(1);
			l.setM_Product_ID(cLine.getM_Product_ID());
			l.setQtyEntered(cLine.getQtyEntered());
			l.setQtyInvoiced(cLine.getQtyInvoiced());
			l.setC_Activity_ID_1(cLine.getC_Activity_ID_1());
			l.setPriceEntered(cLine.getPriceEntered());
			l.setPriceActual(cLine.getPriceActual());
			l.setLineNetAmt(cLine.getLineNetAmt());
			l.setC_Tax_ID(cLine.getC_Tax_ID());
			l.setTaxAmt(cLine.getTaxAmt());
			l.setLineTotalAmt(cLine.getLineTotalAmt());
			l.setApprovedBy(cLine.getApprovedBy());
			l.saveEx();			
		}		
		
	}
	
	/***
	 * Metodo que devuelve true si hay al menos 1 archivo adjunto.
	 * OpenUp Ltda. Issue #1419
	 * @author Nicolas Sarlabos - 18/10/2013
	 * @see
	 * @return
	 */
	public void validateAttachment(){

		String sql = "";

		//obtengo cantidad de adjuntos obligatorios, dependiendo de la cantidad de facturas a generarse
		sql = "select count (distinct l.factura)" +
				" from uy_ff_cashoutline l" +
				" inner join uy_ff_cashout hdr on l.uy_ff_cashout_id = hdr.uy_ff_cashout_id" +
				" where hdr.uy_ff_cashout_id = " + this.get_ID();
		int cantadjuntos = DB.getSQLValueEx(get_TrxName(), sql);

		if (cantadjuntos > 0) {
			// Obtengo ID de attachment
			sql = " select ad_attachment_id " +
					" from ad_attachment " +
					" where ad_table_id =" + I_UY_FF_CashOut.Table_ID + 
					" and record_id = " + this.get_ID();
			int attID = DB.getSQLValueEx(get_TrxName(), sql);
			// Instancio modelo de attachment
			if (attID > 0){
				MAttachment attachment = new MAttachment(getCtx(), attID, null);
				if (attachment.getEntryCount() < cantadjuntos) throw new AdempiereException ("Debe adjuntar " + cantadjuntos + " comprobantes.");

			} else if (cantadjuntos > 0) throw new AdempiereException ("Debe adjuntar " + cantadjuntos + " comprobantes.");
		}
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
		
		this.loadApproverUsers(); //se llama al metodo que carga el campo de nombres de todos los usuarios autorizadores de las lineas
				
		MDocType doc = (MDocType) this.getC_DocType();
		
		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("cashrepay")){
				this.validateAttachment();	
				this.generateInvoice();
				this.updateReplenish(false); //se actualiza documento de reposicion de FF
				
				//si hay salida de FF impacto en trazabilidad de la misma
				if(this.getUY_FF_CashOut_ID_1() > 0){
					
					MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName());
					
					MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
					track.setUY_FF_CashOut_ID(cash.get_ID());
					track.setDateTrx(new Timestamp(System.currentTimeMillis()));
					track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
					track.setDescription("Se completa la rendicion de fondo fijo N° " + this.getDocumentNo());
					track.setobservaciones(this.getMessageInvTracking()); //muestro numeros de facturas creadas
					track.saveEx();					
				}
				
				ADialog.info(0,null,this.getMessage()); //obtengo mensaje armado de numeros de facturas creadas y lo muestro
				
			} else if (doc.getValue().equalsIgnoreCase("cashout")){
				this.updateReplenish(true); //se actualiza documento de reposicion de FF	
				
				MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
				track.setUY_FF_CashOut_ID(this.get_ID());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se completa la salida de fondo fijo");
				track.saveEx();	
			}
		}		
				
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_Void);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}
	
	/***
	 * Metodo que actualiza el documento de reposicion de FF insertando o actualizando una linea para este documento.
	 * Recibe como parametro un boolean que indica si el documento es una salida de FF.
	 * OpenUp Ltda. Issue #1426
	 * @author Nicolas Sarlabos - 27/12/2013
	 * @see
	 * @return
	 */	
	private void updateReplenish(boolean isCashOut) {

		//obtengo documento de reposicion en curso para la sucursal y moneda actual
		MFFReplenish replenish = MFFReplenish.forBranchCurrency(getCtx(), this.getUY_FF_Branch_ID(), this.getC_Currency_ID(), get_TrxName());

		if(replenish==null) throw new AdempiereException("No se encontro documento de reposicion de fondo fijo en curso, por favor verifique");

		if(isCashOut || (!isCashOut && this.getUY_FF_CashOut_ID_1() <= 0)){ //si es salida de FF o rendicion sin salida, inserto directamente como linea de reposicion

			MFFReplenishLine line = new MFFReplenishLine(getCtx(),0,get_TrxName());
			line.setUY_FF_Replenish_ID(replenish.get_ID());
			line.setDateTrx(this.getDateTrx());
			line.setRecord_ID(this.get_ID());
			line.setAD_Table_ID(I_UY_FF_CashOut.Table_ID);
			line.setC_DocType_ID(this.getC_DocType_ID());
			line.setDocumentNo(this.getDocumentNo());
			line.setChargeName(MFFCashOut.getChargeNames(this.get_ID()));
			line.setAD_User_ID(this.getAD_User_ID());
			line.setAmount(this.getGrandTotal());
			line.setDescription(this.getDescription());
			line.setApprovedBy(this.getApprovedBy());
			line.saveEx();	
						
		} else if (!isCashOut && this.getUY_FF_CashOut_ID_1() > 0){ //si es rendicion de FF con salida de caja, actualizo la linea de reposicion

			MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName()); //obtengo la salida asociada a la rendicion actual

			//obtengo la linea de reposicion asociada a la salida de FF
			MFFReplenishLine rline = MFFReplenishLine.forTableReplenishLine(getCtx(), cash.getC_DocType_ID(), X_UY_FF_CashOut.Table_ID, 
					cash.get_ID(), 0, get_TrxName());			

			if(rline!=null){
				
				//si el doc de reposicion en curso es el mismo que el de la salida de FF actualizo la linea. Si no, debo insertar una linea de devolucion de 
				//salida de caja en la reposicion en curso y otra linea para la rendicion
				if(replenish.get_ID()==rline.getUY_FF_Replenish_ID()){	
					
					rline.setDateTrx(this.getDateTrx());
					rline.setRecord_ID(this.get_ID());
					rline.setAD_Table_ID(I_UY_FF_CashOut.Table_ID);
					rline.setC_DocType_ID(this.getC_DocType_ID());
					rline.setDocumentNo(this.getDocumentNo());
					rline.setChargeName(MFFCashOut.getChargeNames(this.get_ID()));
					rline.setAD_User_ID(this.getAD_User_ID());
					rline.setAmount(this.getGrandTotal());
					rline.setDescription(this.getDescription());
					rline.setApprovedBy(this.getApprovedBy());
					rline.saveEx();	
					
				} else {
					
					//creo devolucion de salida de FF
					MDocType returnDoc = MDocType.forValue(getCtx(), "cashreturn", get_TrxName());
					
					if(returnDoc==null) throw new AdempiereException("Error al obtener documento Devolucion Salida Fondo Fijo");
					
					MFFCashOutReturn ret = new MFFCashOutReturn(getCtx(),0,get_TrxName());
					ret.setC_DocType_ID(returnDoc.get_ID());
					ret.setDateTrx(new Timestamp(System.currentTimeMillis()));
					ret.setUY_FF_Branch_ID(cash.getUY_FF_Branch_ID());
					ret.setAD_User_ID(cash.getAD_User_ID());
					ret.setUY_POSection_ID(cash.getUY_POSection_ID());
					ret.setUY_FF_CashOut_ID(cash.get_ID());
					ret.setC_Currency_ID(cash.getC_Currency_ID());
					ret.setAmount(cash.getGrandTotal());
					ret.setDescription("GENERADO AUTOMATICAMENTE POR DOCUMENTO DE RENDICION N° " + this.getDocumentNo());
					ret.setProcessing(false);
					ret.setProcessed(false);
					ret.setDocStatus(DocumentEngine.STATUS_Drafted);
					ret.setDocAction(DocumentEngine.ACTION_Complete);
					ret.saveEx();
					
					//completo la devolucion de fondo fijo
					try {
						if(!ret.processIt(DocumentEngine.ACTION_Complete)){
							throw new AdempiereException("Error al completar devolucion de salida de fondo fijo N° " + ret.getDocumentNo());
						}
					} catch (Exception e) {
						throw new AdempiereException(e.getMessage());
					}
					
					//inserto linea de reposicion en el doc actual para esta rendicion de FF
					MFFReplenishLine line = new MFFReplenishLine(getCtx(),0,get_TrxName());
					line.setUY_FF_Replenish_ID(replenish.get_ID());
					line.setDateTrx(new Timestamp(System.currentTimeMillis()));
					line.setRecord_ID(this.get_ID());
					line.setAD_Table_ID(I_UY_FF_CashOut.Table_ID);
					line.setC_DocType_ID(this.getC_DocType_ID());
					line.setDocumentNo(this.getDocumentNo());
					line.setChargeName(MFFCashOut.getChargeNames(this.get_ID()));
					line.setAD_User_ID(this.getAD_User_ID());
					line.setAmount(this.getGrandTotal());
					line.setDescription(this.getDescription());
					line.setApprovedBy(this.getApprovedBy());
					line.saveEx();					
				}	

			} else throw new AdempiereException("Imposible actualizar documento de reposicion, no se encontro la linea de reposicion para la salida de caja N° " + cash.getDocumentNo());

		}		
	}

	/***
	 * Metodo que devuelve un string con el total de centros de costo de este documento.
	 * OpenUp Ltda. Issue #1426
	 * @author Nicolas Sarlabos - 27/12/2013
	 * @see
	 * @return
	 */	
	public static String getChargeNames(int cashID) {
		
		String value = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;			
		
		try{
			
			sql = "select distinct name" +
                  " from c_activity" +
                  " where c_activity_id in (select c_activity_id_1::integer from uy_ff_cashoutline where uy_ff_cashout_id = " + cashID + ")";
			
			pstmt = DB.prepareStatement (sql, null);
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
		    	if(value==null){
		    		
		    		value = rs.getString("name");
		    		
		    	} else {
		    		
		    		value += "/" + rs.getString("name");	    		
		    		
		    	}	    	
		    	
		    }			
		    		    			
		}catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;	
		
	}

	/***
	 * Metodo que genera las facturas de FF en estado borrador.
	 * OpenUp Ltda. Issue #1420
	 * @author Nicolas Sarlabos - 23/12/2013
	 * @see
	 * @return
	 */
	private void generateInvoice() {
		
		String sql = "";
		int fact = 0;
		int factAux = 0;
		MInvoice hdr = null;
		MBPartner partner = MBPartner.forValue(getCtx(), "fondofijo", get_TrxName());
		MDocType doc = MDocType.forValue(getCtx(), "factfondofijo", get_TrxName());
		MPaymentTerm term = MPaymentTerm.forValue(getCtx(), "Fondo Fijo", get_TrxName());
		int locationID = 0;
		MInvoiceLine line = null;
		MFFCashOutLine cLine = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
				
		try{
			
			if(doc==null) throw new AdempiereException("Error al obtener documento Factura Fondo Fijo");
			if(term==null) throw new AdempiereException("Error al obtener termino de pago Fondo Fijo");
			if(partner==null) throw new AdempiereException("Error al obtener proveedor Fondo Fijo");
			
			sql = "select c_bpartner_location_id" +
			      " from c_bpartner_location" +
				  " where c_bpartner_id = " + partner.get_ID();
			locationID = DB.getSQLValueEx(get_TrxName(), sql);
			
			if(locationID <= 0) throw new AdempiereException("Error al obtener direccion del proveedor Fondo Fijo");
						
			sql = "select uy_ff_cashoutline_id, factura" +
			      " from uy_ff_cashoutline" +
			      " where uy_ff_cashout_id = " + this.get_ID() +
			      " order by factura"; 
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    
		    	factAux = rs.getInt("factura");
		    	
		    	// Si hay cambio de factura
				if (factAux != fact){
					
					if(hdr!=null) this.updateInvoiceAmount(hdr); //actualizo los campos de importe subtotal y total
					
					fact = factAux;
					
					hdr = new MInvoice(getCtx(),0,get_TrxName()); //creo nuevo cabezal
					hdr.setUY_FF_CashOut_ID(this.get_ID());
					hdr.setC_BPartner_ID(partner.get_ID());
					hdr.setC_BPartner_Location_ID(locationID);					
					hdr.setDateInvoiced(new Timestamp(System.currentTimeMillis()));
					hdr.setDateAcct(new Timestamp(System.currentTimeMillis()));
					hdr.setDescription(this.getDescription());
					hdr.setC_DocType_ID(doc.get_ID());
					hdr.setC_DocTypeTarget_ID(doc.get_ID());
					hdr.setC_Currency_ID(this.getC_Currency_ID());
					hdr.setpaymentruletype("CR");
					hdr.setC_PaymentTerm_ID(term.get_ID());
					hdr.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
					hdr.setIsSOTrx(false);
					hdr.setDocStatus(DocumentEngine.STATUS_Drafted);
					hdr.setDocAction(DocumentEngine.ACTION_Complete);
					hdr.saveEx();									
					
				}
				
				cLine = new MFFCashOutLine(getCtx(),rs.getInt("uy_ff_cashoutline_id"),get_TrxName());
				
				line = new MInvoiceLine(getCtx(),0,get_TrxName());
				line.setC_Invoice_ID(hdr.get_ID());
				line.setM_Product_ID(cLine.getM_Product_ID());
				line.setPriceActual(cLine.getPriceActual());
				line.setQtyEntered(cLine.getQtyEntered());
				line.setQtyInvoiced(cLine.getQtyInvoiced());
				line.setC_Activity_ID_1(cLine.getC_Activity_ID_1());
				line.setPriceEntered(cLine.getPriceEntered());
				line.setLineNetAmt(cLine.getLineNetAmt());
				line.setC_Tax_ID(cLine.getC_Tax_ID());
				line.setTaxAmt(cLine.getTaxAmt());
				line.setLineTotalAmt(cLine.getLineTotalAmt());
				line.saveEx();	    	
				//seteo el nro de documento desde la linea de la rendicion de FF
				hdr.setDocumentNoAux(Integer.toString(cLine.getfactura()));
				hdr.setDocumentNo(Integer.toString(cLine.getfactura()));
				hdr.saveEx();
				
					    	
		    }
		    			
		} catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}			
	}

	/***
	 * Metodo que actualiza los campos de subtotal y total de la factura de Fondo Fijo.
	 * OpenUp Ltda. Issue #1420
	 * @author Nicolas Sarlabos - 24/12/2013
	 * @see
	 * @return
	 */
	private void updateInvoiceAmount(MInvoice hdr) {
		
		String sql = "";
		BigDecimal subTotal = Env.ZERO;
		BigDecimal total = Env.ZERO;

		sql = "select coalesce(sum(l.linenetamt),0) as subtotal" +
				" from c_invoiceline l" +
				" where c_invoice_id = " + hdr.get_ID();
		subTotal = DB.getSQLValueBDEx(get_TrxName(), sql);

		sql = "select coalesce(sum(l.linetotalamt),0) as total" +
				" from c_invoiceline l" +
				" where c_invoice_id = " + hdr.get_ID();
		total = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		hdr.setTotalLines(subTotal);
		hdr.setGrandTotal(total);
		hdr.saveEx();			
			
	}
	
	/***
	 * Metodo que devuelve un string de numeros de facturas generadas.
	 * OpenUp Ltda. Issue #1419
	 * @author Nicolas Sarlabos - 02/01/2014
	 * @see
	 * @return
	 */
	private String getMessage(){
		
		String value = "Se generaron las facturas con los siguientes numeros: \n";
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;			
		
		try{
			
			sql = "select distinct documentno" +
                  " from c_invoice" +
                  " where uy_ff_cashout_id = " + this.get_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
		    	value += rs.getString("documentno") + "\n";
		    	
		    }			
		    		    			
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}				
		
		return value;
	}
	
	/***
	 * Metodo que devuelve un string de numeros de facturas generadas para insertar en trazabilidad.
	 * OpenUp Ltda. Issue #1825
	 * @author Nicolas Sarlabos - 22/01/2014
	 * @see
	 * @return
	 */
	private String getMessageInvTracking(){
		
		String value = "Se generaron las siguientes facturas: ";
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;			
		
		try{
			
			sql = "select distinct documentno" +
                  " from c_invoice" +
                  " where uy_ff_cashout_id = " + this.get_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
		    	if(rs.isLast()){
		    		
		    		value += rs.getString("documentno");
		    		
		    	} else value += rs.getString("documentno") + " - ";
		    			    	
		    }			
		    		    			
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}				
		
		return value;
	}


	/***
	 * Metodo que carga el campo de usuarios autorizadores de todas las lineas.
	 * OpenUp Ltda. Issue #1425
	 * @author Nicolas Sarlabos - 23/12/2013
	 * @see
	 * @return
	 */
	private void loadApproverUsers() {
		
		String value = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;			
		
		try{
			
			sql = "select distinct res.name" +
                  " from uy_posection sec" +
                  " inner join uy_popolicyuser usr on sec.uy_posection_id = usr.uy_posection_id" +
                  " inner join ad_wf_responsible res on usr.ad_wf_responsible_id = res.ad_wf_responsible_id" +
                  " where usr.isactive = 'Y'" +
                  " and usr.nivel = '1' and sec.c_activity_id_1 in (select c_activity_id_1 from uy_ff_cashoutline where uy_ff_cashout_id = " + this.getUY_FF_CashOut_ID() + ")";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
		    	if(value==null){
		    		
		    		value = rs.getString("name");
		    		
		    	} else {
		    		
		    		value += "/" + rs.getString("name");	    		
		    		
		    	}	    	
		    	
		    }			
		    
		    if(value!= null) this.setApprovedBy(value);
			
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
	}

	/***
	 * Obtiene y retorna lineas de esta salida de caja o rendicion.
	 * OpenUp Ltda. Issue #1419
	 * @author Nicolas Sarlabos - 17/10/2013
	 * @see
	 * @return
	 */
	public List<MFFCashOutLine> getLines(){

		String whereClause = X_UY_FF_CashOutLine.COLUMNNAME_UY_FF_CashOut_ID + "=" + this.get_ID();

		List<MFFCashOutLine> lines = new Query(getCtx(), I_UY_FF_CashOutLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna importe total de lineas de este documento.
	 * OpenUp Ltda. Issue #1418
	 * @author Nicolas Sarlabos - 17/10/2013
	 * @see
	 * @return
	 */
	public BigDecimal getTotalAmount(){
		
		String sql = "select coalesce(sum(linetotalamt),0) from uy_ff_cashoutline where uy_ff_cashout_id = " + this.get_ID();
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

		return amount;
	}

	@Override
	public boolean voidIt() {
		
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		this.validateVoid(); //se valida la anulacion dependiendo del tipo de documento actual

		MDocType doc = (MDocType) this.getC_DocType();

		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("cashrepay")){
	
				//si hay salida de FF impacto en trazabilidad de la misma
				if(this.getUY_FF_CashOut_ID_1() > 0){

					MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName());

					MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
					track.setUY_FF_CashOut_ID(cash.get_ID());
					track.setDateTrx(new Timestamp(System.currentTimeMillis()));
					track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
					track.setDescription("Se anula la rendicion de fondo fijo N° " + this.getDocumentNo());
					track.saveEx();					
				}				

			} else if(doc.getValue().equalsIgnoreCase("cashout")){

				MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
				track.setUY_FF_CashOut_ID(this.get_ID());
				track.setDateTrx(new Timestamp(System.currentTimeMillis()));
				track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
				track.setDescription("Se anula la salida de fondo fijo");
				
				if(this.isVoidAuto && !this.msgVoidAuto.equalsIgnoreCase("")){ //cargo comentarios si es anulacion desde devolucion 
					
					track.setobservaciones(this.msgVoidAuto);					
				}
				
				track.saveEx();			
			}
		}

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;		

		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);

		return true;
	}

	/***
	 * Valida que no exista una rendicion o devolucion de FF en estado diferente a VOID
	 * para permitir anular la salida de FF actual. Para el caso de una rendicion se valida que
	 * no existan facturas de FF en estado completo.
	 * OpenUp Ltda. Issue #1418
	 * @author Nicolas Sarlabos - 20/12/2013
	 * @see
	 * @return
	 */	
	private void validateVoid() {
		
		String sql = "";
		int ID = 0;
		MFFReplenish replenish = null;
		MFFReplenishLine rline = null;
		
		MDocType doc = new MDocType(getCtx(),this.getC_DocType_ID(),get_TrxName());
		
		//realizo validacion segun tipo de documento
		if(doc.getValue()!=null){
			
			if(doc.getValue().equalsIgnoreCase("cashout")){
				
				//se buscan rendiciones
				MDocType docRepay = MDocType.forValue(getCtx(), "cashrepay", get_TrxName());

				if(docRepay==null) throw new AdempiereException("Error al obtener documento de Rendicion de Fondo Fijo");

				sql = "select uy_ff_cashout_id" +
						" from uy_ff_cashout" +
						" where uy_ff_cashout_id_1 = " + this.get_ID() +
						" and docstatus <> 'VO' and c_doctype_id = " + docRepay.get_ID();
				ID = DB.getSQLValueEx(get_TrxName(), sql);

				if(ID > 0) {

					MFFCashOut cash = new MFFCashOut(getCtx(),ID,get_TrxName()); 

					throw new AdempiereException("Imposible anular, existe la rendicion N° " + cash.getDocumentNo() + 
							" asociada a esta salida. Debe primero anular o eliminar la rendicion para luego anular la salida");
				}
				
				//si NO es una anulacion automatica, realizo la siguiente verificacion
				if(!this.isVoidAuto){
					//se buscan devoluciones
					sql = "select uy_ff_cashoutreturn_id" +
							" from uy_ff_cashoutreturn" +
							" where uy_ff_cashout_id = " + this.get_ID() +
							" and docstatus <> 'VO'";
					ID = DB.getSQLValueEx(get_TrxName(), sql);

					if(ID > 0) {

						MFFCashOutReturn cashRet = new MFFCashOutReturn(getCtx(),ID,get_TrxName()); 

						throw new AdempiereException("Imposible anular, existe la devolucion N° " + cashRet.getDocumentNo() + 
								" asociada a esta salida. Debe primero anular o eliminar la devolucion para luego anular la salida");
					}	
				}

				//obtengo linea de reposicion 
				rline = MFFReplenishLine.forTableReplenishLine(getCtx(), this.getC_DocType_ID(), X_UY_FF_CashOut.Table_ID, this.get_ID(), 0, get_TrxName());

				if(rline!=null){

					replenish = new MFFReplenish(getCtx(),rline.getUY_FF_Replenish_ID(),get_TrxName()); //instancio cabezal de doc de reposicion

					if(!replenish.getDocStatus().equalsIgnoreCase("CO")) { //solo si la reposicion no esta completa borro la linea

						rline.deleteEx(true); //borro la linea del doc de reposicion (no hace falta verificar que este en borrador porque existe control al completar la reposicion)

					} else throw new AdempiereException("Imposible anular por estar la salida en el documento de reposicion N° " + 					
							replenish.getDocumentNo() + " en estado completo");						
				}					
				
			} else if (doc.getValue().equalsIgnoreCase("cashrepay")){		
				
				//obtengo linea de reposicion 
				rline = MFFReplenishLine.forTableReplenishLine(getCtx(), this.getC_DocType_ID(), X_UY_FF_CashOut.Table_ID, this.get_ID(), 0, get_TrxName());

				//si la rendicion esta en un doc de reposicion....
				if(rline!=null){

					replenish = new MFFReplenish(getCtx(),rline.getUY_FF_Replenish_ID(),get_TrxName()); //instancio cabezal de doc de reposicion

					if(!replenish.getDocStatus().equalsIgnoreCase("CO")) { //solo si la reposicion no esta completa borro la linea

						if(this.getUY_FF_CashOut_ID_1() > 0){ //si la rendicion tiene salida de caja debo actualizar la linea (ir un paso atras), si no la borro directo
							
							MFFCashOut salida = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName()); //instancio la salida de caja
							
							rline.setDateTrx(salida.getDateTrx());
							rline.setRecord_ID(salida.get_ID());
							rline.setAD_Table_ID(I_UY_FF_CashOut.Table_ID);
							rline.setC_DocType_ID(salida.getC_DocType_ID());
							rline.setDocumentNo(salida.getDocumentNo());
							rline.setChargeName(MFFCashOut.getChargeNames(salida.get_ID()));
							rline.setAD_User_ID(salida.getAD_User_ID());
							rline.setAmount(salida.getGrandTotal());
							rline.setDescription(salida.getDescription());
							rline.setApprovedBy(salida.getApprovedBy());
							rline.saveEx();
							
						} else rline.deleteEx(true);						

					} else throw new AdempiereException("Imposible anular por estar la rendicion en el documento de reposicion N° " + 					
							replenish.getDocumentNo() + " en estado completo");					

				} else { //si la rendicion no esta en una reposicion entonces debo buscar facturas y anularlas

					//se buscan facturas asociadas a la rendicion actual
					List<MInvoice> invoices = this.getFFInvoiceForCashRepay();

					for (MInvoice inv: invoices){

						if(inv.getDocStatus().equalsIgnoreCase("DR")) throw new AdempiereException("Imposible anular, existe la factura N° " + inv.getDocumentNo() + 
								" en estado BORRADOR. Debe eliminar la factura antes de anular la rendicion.");

						if(inv.getDocStatus().equalsIgnoreCase("IP")) throw new AdempiereException("Imposible anular, existe la factura N° " + inv.getDocumentNo() + 
								" en estado EN PROCESO. Debe completar la factura antes de anular la rendicion.");

						if(inv.getDocStatus().equalsIgnoreCase("CO")){

							//obtengo linea de reposicion 
							rline = MFFReplenishLine.forTableReplenishLine(getCtx(), inv.getC_DocTypeTarget_ID(), X_C_Invoice.Table_ID, inv.get_ID(), 0, get_TrxName());

							if(rline!=null){

								replenish = new MFFReplenish(getCtx(),rline.getUY_FF_Replenish_ID(),get_TrxName()); //instancio cabezal de doc de reposicion

								if(!replenish.getDocStatus().equalsIgnoreCase("CO")) {

									if(!inv.processIt(DocumentEngine.ACTION_Void)){
										throw new AdempiereException("Error al anular factura N° " + inv.getDocumentNo());
									}
									
									inv.saveEx();

								} else throw new AdempiereException("Imposible anular la factura N° " + inv.getDocumentNo() + " asociada a esta rendicion por estar en el documento de reposicion N° " + 					
										replenish.getDocumentNo() + " en estado completo");											
							}
						}								
					}				    

					//al final debo actualizar el doc de reposicion
					//obtengo documento de reposicion en curso para la sucursal y moneda actual
					replenish = MFFReplenish.forBranchCurrency(getCtx(), this.getUY_FF_Branch_ID(), this.getC_Currency_ID(), get_TrxName());

					if(this.getUY_FF_CashOut_ID_1() > 0){ //si la rendicion tiene salida de caja debo insertar una linea para la misma

						MFFCashOut salida = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID_1(),get_TrxName()); //instancio la salida de caja

						MFFReplenishLine line = new MFFReplenishLine(getCtx(),0,get_TrxName());
						line.setUY_FF_Replenish_ID(replenish.get_ID());
						line.setDateTrx(salida.getDateTrx());
						line.setRecord_ID(salida.get_ID());
						line.setAD_Table_ID(I_UY_FF_CashOut.Table_ID);
						line.setC_DocType_ID(salida.getC_DocType_ID());
						line.setDocumentNo(salida.getDocumentNo());
						line.setChargeName(MFFCashOut.getChargeNames(salida.get_ID()));
						line.setAD_User_ID(salida.getAD_User_ID());
						line.setAmount(salida.getGrandTotal());
						line.setDescription(salida.getDescription());
						line.setApprovedBy(salida.getApprovedBy());
						line.saveEx();								
					}							

				}
			}			
		}
	}

	/***
	 * Compara los importes por centros de costo de la salida de caja y de la rendicion
	 * y devuelve TRUE si el documento se puede aprobar directamente.
	 * OpenUp Ltda. Issue #1419
	 * @author Nicolas Sarlabos - 28/01/2014
	 * @see
	 * @return
	 */	
	private boolean verifyApproveDirect() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		BigDecimal amtSalida = Env.ZERO;
		BigDecimal amtRendicion = Env.ZERO;
		int count  = 0;
		boolean value = false;

		try{

			//obtengo centros de costo a recorrer para la salida de fondo fijo de esta rendicion
			sql = "select distinct c_activity_id_1 from uy_ff_cashoutline where uy_ff_cashout_id = " + this.getUY_FF_CashOut_ID_1();

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				sql = "select coalesce(sum(linetotalamt),0)" +
				      " from uy_ff_cashoutline" +
					  " where uy_ff_cashout_id = " + this.getUY_FF_CashOut_ID_1() +
					  " and c_activity_id_1::numeric = " + rs.getInt("c_activity_id_1");
				
				amtSalida = DB.getSQLValueBDEx(get_TrxName(), sql); //obtengo importe total de lineas de salida para el CCostos actual
				
				sql = "select coalesce(sum(linetotalamt),0)" +
					      " from uy_ff_cashoutline" +
						  " where uy_ff_cashout_id = " + this.get_ID() +
						  " and c_activity_id_1::numeric = " + rs.getInt("c_activity_id_1");
					
				amtRendicion = DB.getSQLValueBDEx(get_TrxName(), sql); //obtengo importe total de lineas de rendicion para el CCosto actual
				
				//si el importe de la rendicion es mayor al de la salida, aumento contador
				if(amtRendicion.compareTo(amtSalida) > 0) count ++;
				
			}
			
			if(count == 0) value = true;
						

		} catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;			
	}
	
	/***
	 * Obtiene y retorna las facturas de fondo fijo para una rendicion.
	 * OpenUp Ltda. Issue #1766
	 * @author Nicolas Sarlabos - 09/01/2014
	 * @see
	 * @return
	 */
	public List<MInvoice> getFFInvoiceForCashRepay(){

		String whereClause = X_C_Invoice.COLUMNNAME_UY_FF_CashOut_ID + "=" + this.get_ID() + " AND " + X_C_Invoice.COLUMNNAME_DocStatus + " <> 'VO'";

		List<MInvoice> invoices = new Query(getCtx(), I_C_Invoice.Table_Name, whereClause, get_TrxName())
		.list();

		return invoices;
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
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDynamicWFResponsibleID(MWFNode node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setApprovalInfo(int AD_WF_Responsible_ID, String textMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWFActivityDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWFActivityHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsParcialApproved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processAutomaticApproval() {
		
		MDocType doc = (MDocType) this.getC_DocType();
		
		if(doc.getValue()!=null){
			if(doc.getValue().equalsIgnoreCase("cashrepay")){
				if(this.verifyApproveDirect()){
					
					this.approveIt(); //se aprueba automaticamente la rendicion de FF
					
				}				
			}
		}		
	}

	@Override
	public void processAutomaticComplete() {
		// TODO Auto-generated method stub
		
	}

}
