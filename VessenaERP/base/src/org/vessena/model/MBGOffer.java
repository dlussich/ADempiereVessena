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
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.openup.util.OpenUpUtils;


/**OpenUp Ltda Issue#
 * @author SBouissa 29/7/2015
 *
 */
public class MBGOffer extends X_UY_BG_Offer implements DocAction {
	private final int MBGMARKETTYPE_ID = 1000000;
	private static final String EST_CERRADA = "Cerrada";
	private static final String EST_PARCIAL = "Parcial";
	private static final String EST_ABIERTA = "Abierta";
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * @author SBouissa 29/7/2015
	 * @param ctx
	 * @param UY_BG_Offer_ID
	 * @param trxName
	 */
	public MBGOffer(Properties ctx, int UY_BG_Offer_ID, String trxName) {
		super(ctx, UY_BG_Offer_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author SBouissa 29/7/2015
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGOffer(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 */
	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		//Re-Check
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**OpenUp Ltda. Issue#
	 * @author SBouissa 27-07-2015
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(!OpenUpUtils.isOpen(MBGMARKETTYPE_ID)){
			throw new AdempiereException("Periodo cerrado");
		}
		if(newRecord){			
			//Seteo fecha de hoy
			Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			this.setDateTrx(today);
			this.setAD_User_ID(getAD_User_ID());
			int bursa = getBolsa();
			if(0<bursa){
				this.setUY_BG_Bursa_ID(bursa);
			}
		}
		return true;
		//return super.beforeSave(newRecord);
	}

	/**Obtngo id de bolsa OJO que en un futuro se debe realizar otras consultas
	 * OpenUp Ltda Issue#
	 * @author SBouissa 31/7/2015
	 * @return
	 */
	private int getBolsa() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="";
		try{
			sql = "SELECT UY_BG_Bursa_ID"
	    	 		+ " FROM UY_BG_Bursa WHERE AD_User_ID = "+this.getAD_User_ID();
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("UY_BG_Bursa_ID");
			}
			pstmt.close();
	     }catch(Exception e){
	    	 e.getMessage();
	     }finally{
	    	 
	    	 try{
	    		 pstmt.close();
	    		 rs.close();
	    	 }catch(Exception e){
	    		 
	    	 }
	     }
		return 0;
	}

	/**OpenUp Ltda. Issue#
	 * @author SBouissa 27-07-2015 
	 */
	public boolean processAutomaticComplete() {
		try 
		{
			// Completo de manera automatico al aprobar documento
			if (!this.processIt(ACTION_Complete)){
				if (this.getProcessMsg() != null){
					throw new AdempiereException(this.getProcessMsg());
				}
			}
			else{
				this.saveEx();
			}
			return true;	
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		//return super.afterSave(newRecord, success);
		return updateInstrumentsValues();
	}

	private boolean updateInstrumentsValues() {
		try{
//			Update qtyOP,qtySold,
			String sql=""; int no=0;
			if(this.getBuySell().equals(MBGOffer.BUYSELL_COMPRA)){ // COMPRA
				 sql = "UPDATE UY_BG_Instrument i"
						 +" SET qtyPO ="
						 + " (SELECT COALESCE(SUM(a.volume),0) FROM UY_BG_Offer a "
						 + " WHERE a.UY_BG_Instrument_ID = i.UY_BG_Instrument_ID"
						 + " AND to_char(a.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD') "
						 		+ " AND a.buysell = '"+MBGOffer.BUYSELL_COMPRA+"')"
						 + ", PricePO = "
						 + " (SELECT MIN(a.priceentered) FROM UY_BG_Offer a "
						 + " WHERE a.UY_BG_Instrument_ID = i.UY_BG_Instrument_ID "
						 + " AND to_char(a.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD') "
						 		+ " AND a.buysell = '"+MBGOffer.BUYSELL_COMPRA+"')"
						 + " WHERE i.UY_BG_Instrument_ID = "+this.getUY_BG_Instrument_ID();
			}else if(this.getBuySell().equals(MBGOffer.BUYSELL_VENTA)){// VENTA
				 sql = "UPDATE UY_BG_Instrument i"
						 +" SET qtySales ="
						 + " (SELECT COALESCE(SUM(a.volume),0) FROM UY_BG_Offer a "
						 + " WHERE a.UY_BG_Instrument_ID = i.UY_BG_Instrument_ID"
						 + " AND to_char(a.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD') "
						 		+ " AND a.buysell = '"+MBGOffer.BUYSELL_VENTA+"')"
						 + ", PriceSales = "
						 + "(SELECT MAX(a.priceentered) FROM UY_BG_Offer a "
						 + " WHERE a.UY_BG_Instrument_ID = i.UY_BG_Instrument_ID  "
						 + " AND to_char(a.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD') "
						 		+ " AND a.buysell = '"+MBGOffer.BUYSELL_VENTA+"')"
						 + " WHERE i.UY_BG_Instrument_ID = "+this.getUY_BG_Instrument_ID();
			}
			no = DB.executeUpdate(sql, get_TrxName());
			return no==1;
		}catch(Exception e){
			e.getMessage();
		}			
		return false;
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author SBouissa 30/7/2015
	 * @return
	 */
	public String getOfferActualStatus(int idOffer, int idAdUser) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="";
		try{
			sql = "SELECT a.UY_BG_Offer_ID,"
	    	 		+ " CASE WHEN a.IsMatched = 'Y' THEN '"+MBGOffer.EST_CERRADA+"' "
	    	 		+ " WHEN a.IsMatched = 'N' AND a.Volume != a.VolumeBase THEN '"+MBGOffer.EST_PARCIAL+"'"
				    + " WHEN a.IsMatched = 'N' AND a.Volume = a.VolumeBase THEN '"+MBGOffer.EST_ABIERTA+"' "
				    		+ "END AS Estado"
	    	 		+ " FROM UY_BG_Offer a "
	    	 		+ " WHERE a.IsActive='Y' AND a.AD_User_ID ="+idAdUser+" AND a.DocStatus='CO'"
	    	 		+ " AND to_char(a.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD') "
	    	 		//+ " AND //la hora del sistema esta dentro de los parametros habilitantes"
	    	 		+ " AND a.UY_BG_Offer_ID = "+idOffer;
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getString("Estado");
			}
				pstmt.close();
	     }catch(Exception e){
	    	 e.getMessage();
	     }
		return "-";
	}

	/**Eliminar de la base de datos mis ofertas que estan en estado Abiertas y no tienen ningún contrato
	 * OpenUp Ltda Issue#
	 * @author SBouissa 30/7/2015
	 * @param ad_User_ID Usuario
	 * @param ctx Contexto
	 */
	public static void eliminarOfertasAbiertas(int ad_User_ID, Properties ctx) {
		String whereClause = X_UY_BG_Offer.COLUMNNAME_IsActive+ " = 'Y'"
				+ " AND " + X_UY_BG_Offer.COLUMNNAME_AD_User_ID + " = " + ad_User_ID +""
				+ " AND " + X_UY_BG_Offer.COLUMNNAME_isMatched + " = 'N'"
				+ " AND to_char("+X_UY_BG_Offer.COLUMNNAME_DateTrx+",'YYYYMMDD') = to_char(now(),'YYYYMMDD')";

		List<MBGOffer> lines = null;
		lines = new Query(ctx,I_UY_BG_Offer.Table_Name, whereClause, null).list();
		if(0<lines.size()){
			for(MBGOffer model : lines){
				if(null != model){
					if(model.getOfferActualStatus(model.get_ID(),ad_User_ID).equals(MBGOffer.EST_ABIERTA)){
						if(!model.existeContrato()){
							model.setPriceEntered(BigDecimal.ZERO);
							model.setVolume(BigDecimal.ZERO);
							model.updateInstrumentsValues();//antes de eliminar actualizo los contadores del instrumento correspondiente
							model.deleteEx(true);
						}
					}
				}

			}

		}
	
	}
	
	
	/**Eliminar de la base de datos UNA oferta que esta en estado Abiertas y no tienen ningún contrato
	 * Si tiene contrato se cierra la oferta 
	 * OpenUp Ltda Issue#
	 * @author SBouissa 03/8/2015
	 * @param ad_User_ID Usuario
	 * @param ctx Contexto
	 */
	public static void eliminarOfertaAbierta(int ad_User_ID, Properties ctx, int idOferta) {
		
		MBGOffer model = new MBGOffer(ctx, idOferta, null);
		if(null!=model){
			if(!model.isMatched()){
				if(!model.existeContrato() && (model.getAD_User_ID() == ad_User_ID)){
					model.setPriceEntered(BigDecimal.ZERO);
					model.setVolume(BigDecimal.ZERO);
					model.updateInstrumentsValues();//antes de eliminar actualizo los contadores del instrumento correspondiente
					model.deleteEx(true);
				}else{
					if(model.getOfferActualStatus(model.get_ID(),ad_User_ID).equals(MBGOffer.EST_PARCIAL)){						
						cerrarOfertaAbierta(ad_User_ID,ctx,idOferta);
					}
				}
			}else{
				throw new AdempiereException("La oferta esta cerrada");
			}
			
		}	
	}
	
	/**Cerrar UNA oferta que esta en estado Abiertas y tiene al menos un contrato
	 * OpenUp Ltda Issue#
	 * @author SBouissa 03/8/2015
	 * @param ad_User_ID Usuario
	 * @param ctx Contexto
	 */
	public static void cerrarOfertaAbierta(int ad_User_ID, Properties ctx, int idOferta) {
		
		MBGOffer model = new MBGOffer(ctx, idOferta, null);
		if(null!=model){
			if(!model.isMatched()){
				if(model.existeContrato() && (model.getAD_User_ID() == ad_User_ID)){					
					if(0<cerrarOfetaParcial(model.get_ID())){//cierro la oferta
						model.updateInstrumentsValues();//Debo actualizar value y price del isntrumento relacionado
					}				
				}
			}else{
				throw new AdempiereException("La oferta esta cerrada");
			}
			
		}	
	}
	
	
 /**Si la oferta esta asociada a un contrato del dia actual, se retorna true, de lo contrario false
  * OpenUp Ltda Issue#
  * @author SBouissa 31/7/2015
  * @return
  */
	private boolean existeContrato() {
		MBGContract contrato = new MBGContract(getCtx(), 0, null);
		if(null != contrato.forUY_BG_Offer(this.get_ID(),this.getBuySell())){
			return true;
		}
		return false;
	}

	public static void actualizarYCerrarOfertasParciales(int ad_User_ID,
			Properties ctx) {
		String whereClause = X_UY_BG_Offer.COLUMNNAME_IsActive+ " = 'Y'"
				+ " AND " + X_UY_BG_Offer.COLUMNNAME_AD_User_ID + " = " + ad_User_ID +""
				+ " AND " + X_UY_BG_Offer.COLUMNNAME_isMatched + " = 'N'"
				+ " AND to_char("+X_UY_BG_Offer.COLUMNNAME_DateTrx+",'YYYYMMDD') = to_char(now(),'YYYYMMDD')";

		List<MBGOffer> lines = null;
		lines = new Query(ctx,I_UY_BG_Offer.Table_Name, whereClause, null).list();
		if(0<lines.size()){
			for(MBGOffer model : lines){
				if(null != model){
					if(model.getOfferActualStatus(model.get_ID(),ad_User_ID).equals(MBGOffer.EST_PARCIAL)){
						if(0<cerrarOfetaParcial(model.get_ID())){//Cierro la offerta
							model.updateInstrumentsValues(); //Debo actualizar value y price del isntrumento relacionado
						}
						
					}
				}
		
			}
		}
	}//

	/**Seteo la oferta como cerrada e indico que fue por accón del usuario
	 * OpenUp Ltda Issue#
	 * @author SBouissa 3/8/2015
	 * @param idOffer
	 * @return
	 */
	private static int cerrarOfetaParcial(int idOffer) {
		// TODO Auto-generated method stub
		int no = -1;
		try{
			String sql = "UPDATE UY_BG_Offer o"
					+" SET "+ X_UY_BG_Offer.COLUMNNAME_isMatched + " = 'Y'"
			 		+ " ,"+X_UY_BG_Offer.COLUMNNAME_IsPanic +" = 'Y'"
			 		+ " WHERE o.UY_BG_Offer_ID = "+idOffer;
			no = DB.executeUpdate(sql, null);
			
		}catch(Exception e){
			e.getMessage();
		}
		return no;
	}
	
}
