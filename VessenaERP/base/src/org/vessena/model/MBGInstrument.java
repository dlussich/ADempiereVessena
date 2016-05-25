package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MRegion;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.openup.util.OpenUpUtils;

/* (non-Javadoc)
 * @see org.compiere.process.DocAction#getApprovalAmt()
 * OpenUp Ltda. Issue # 
 * @author Andrea Odriozola - 15/07/2015
 * @see
 * @return
 */
public class MBGInstrument extends X_UY_BG_Instrument implements DocAction {
	private final int MBGMARKETTYPE_ID = 1000000;
	private String processMsg = null;
	private boolean justPrepared = false;

	static final long serialVersionUID = -5935196321638022562L;

	public MBGInstrument(Properties ctx, int UY_BG_Instrument_ID, String trxName) {
		super(ctx, UY_BG_Instrument_ID, trxName);
	}

	public MBGInstrument(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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
		// Todo bien
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

		//	Re-Check
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

	@Override
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

	@Override
	protected boolean beforeSave(boolean newRecord) {
	
		if(!OpenUpUtils.isOpen(MBGMARKETTYPE_ID)){
			throw new AdempiereException("Periodo cerrado");
		}
		if(newRecord){
			try {
				if (this.getType().equalsIgnoreCase(TYPE_FORWARD)){
					if (this.getC_Region_ID() <= 0){
						throw new AdempiereException("Debe indicar Localidad para Instrumentos del Tipo FORWARD.");
					}
				}else if(this.getType().equalsIgnoreCase(TYPE_OPCIONES)){
					if(this.getPriceEntered().equals(BigDecimal.ZERO)){
						throw new AdempiereException("Debe indicar Localidad para Instrumentos del Tipo FORWARD.");
					}
				}
				
				// Genero el codigo de instrumento para el trader
				MProduct prod  = (MProduct)this.getM_Product();
				MRegion region = (MRegion)this.getC_Region();
				MPeriod period = (MPeriod)this.getC_Period();
				
				String codeInst = prod.getName().toUpperCase().substring(0, 3);
				
				if ((region != null) && (region.get_ID() >0)){//si es FW prod,lugar
					codeInst = codeInst + "." + region.get_Value("Code").toString().trim();	
				}else{//si es OP prod,precio,accion
					String price = String.valueOf(this.getPriceEntered().intValue());
					codeInst = codeInst + "." + price + "." + this.getAction();
				}
				
				codeInst = codeInst + "." + period.getName().toUpperCase();
				
				this.setCode(codeInst.toUpperCase());
				
				//Valores por defecto		
				this.setC_DocType_ID(MDocType.forValue(getCtx(), "bginstrument", get_TrxName()).get_ID());
				this.setC_Currency_ID(100);
				this.setUY_BG_MarketType_ID(MBGMARKETTYPE_ID);//Agropecurio por defecto
				//
				this.setC_UOM_ID(prod.getC_UOM_ID());
				this.setUY_BG_PackingMode_ID(prod.get_ValueAsInt("UY_BG_PackingMode_ID"));
				this.setUY_BG_Quality_ID(prod.get_ValueAsInt("UY_BG_Quality_ID"));	
				MBGProdType clasifProd = new MBGProdType(getCtx(),prod.get_ValueAsInt("UY_BG_ProdType_ID") ,null); //--> clasificacion del producto
				this.setUY_BG_Retention_ID(clasifProd.getUY_BG_Retention_ID());
				//Seteo fecha actual
				Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
				this.setDateTrx(today);
				int i = getBolsa();
				if(0<i){
					this.set_Value("UY_BG_Bursa_ID", i);
				}
				//Verifico si existe un instrumento con digo codigo, activo del dia de hoy
				if(verifyKey()){
					throw new AdempiereException("Ya se existe Instrumento "+codeInst);
				}
				
			} 
			catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			}
		}

		return true;
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
	
	public void processAutomaticComplete() {
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
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
	}
	
	
	
	/**Retorno true si existe instrumento con Producto, Mes/Anio, Localidad (FORWARD) o
	 * retorno true  si existe instrumento con Producto, Mes/Anio, Precio, Accion (OPCION)
	 * Del dia de hoy
	 * OpenUp Ltda Issue#
	 * @author SBouissa 28/7/2015
	 * @return
	 */
	private boolean verifyKey() {

		String whereClause = X_UY_BG_Instrument.COLUMNNAME_IsActive+ " = 'Y'"
				+ " AND " + X_UY_BG_Instrument.COLUMNNAME_Code + " = '" + this.getCode()+"'"
				+ " AND "+ X_UY_BG_Instrument.COLUMNNAME_DateTrx + " = '" + this.getDateTrx()+"'";
		
		MBGInstrument model=null;
		model = new Query(getCtx(),I_UY_BG_Instrument.Table_Name, whereClause, get_TrxName()).first();
		if(model!= null){
			return true;
		}
		return false;
	
	}
	
	/**Actualiza el valor qty de los instrumentos del día (depende de los contratos creados
	 * sobre dicho insturmento)
	 * OpenUp Ltda Issue#
	 * @author SBouissa 30/7/2015
	 * @return
	 */
	public static boolean updateVolumenNegociado() {
		try{
			String sql=""; int no=0;
			sql = "UPDATE UY_BG_Instrument i"
						 +" SET qty ="
						 + " (SELECT COALESCE(SUM(a.volume),0) FROM UY_BG_Contract a "
						 + " WHERE a.UY_BG_Instrument_ID = i.UY_BG_Instrument_ID)"
				+ " WHERE  to_char(i.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD')";						
			no = DB.executeUpdate(sql, null);
			return no>0;
		}catch(Exception e){
			e.getMessage();
		}		
		return false;
	}
	
	/** Actualizo los valores de todos los instrumentos de hoy
	 * OpenUp Ltda Issue#
	 * @author SBouissa 31/7/2015
	 * @return
	 */
	public static boolean updateVolumenPrecio() {
		try{
//			Update qtyOP,qtySold,
			String sql=""; int no=0;
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
						 + ", qtySales ="
						 	+ " (SELECT COALESCE(SUM(a.volume),0) FROM UY_BG_Offer a "
						 		+ " WHERE a.UY_BG_Instrument_ID = i.UY_BG_Instrument_ID"
						 			+ " AND to_char(a.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD') "
						 			+ " AND a.buysell = '"+MBGOffer.BUYSELL_VENTA+"')"
						 + ", PriceSales = "
						 	+ "(SELECT MAX(a.priceentered) FROM UY_BG_Offer a "
						 		+ " WHERE a.UY_BG_Instrument_ID = i.UY_BG_Instrument_ID  "
						 			+ " AND to_char(a.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD') "
						 			+ " AND a.buysell = '"+MBGOffer.BUYSELL_VENTA+"')"
					 + " WHERE to_char(i.datetrx,'YYYYMMDD') = to_char(now(),'YYYYMMDD')";		
			//_------------------------
			no = DB.executeUpdate(sql, null);
			return no>0;
		}catch(Exception e){
			e.getMessage();
		}			
		return false;
	}
	
}
