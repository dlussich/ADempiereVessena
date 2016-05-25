/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 25/05/2012
 */
 
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPO;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MUOMConversion;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MRFQGenFilter
 * OpenUp Ltda. Issue #93
 * Description: Modelo de Filtros para el proceso de Generacion de Solicitudes
 * de Cotizaciones de Compra. 
 * @author Gabriel vila - 25/05/2012
 * @see
 */
public class MRFQGenFilter extends X_UY_RFQGen_Filter implements DocAction {

	private static final long serialVersionUID = -3698688168589627071L;
	private String processMsg = null;
	private boolean justPrepared = false;

	HashMap<Integer, Integer> hashMacroXProd = new HashMap<Integer, Integer>(); 
	HashMap<Integer, MRFQMacroReq> hashMacro = new HashMap<Integer, MRFQMacroReq>();
	HashMap<Integer, HashMap<Integer, Integer>> hashProdsXMacro = new HashMap<Integer, HashMap<Integer,Integer>>();
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQGen_Filter_ID
	 * @param trxName
	 */
	public MRFQGenFilter(Properties ctx, int UY_RFQGen_Filter_ID, String trxName) {
		super(ctx, UY_RFQGen_Filter_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQGenFilter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @param action
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
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
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
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

		// Valido cantidad de proveedores por producto segun politica de compra
		if (!this.validateVendors()){
			return DocAction.STATUS_Invalid;
		}
		
		// Genero solicitudes de cotizacion consolidadas por proveedor
		this.generateRFQRequests();
		
		// Seteo macro solicitudes en estado : Esperando confirmacion (WC)
		this.setWaitingMacroRequests();
		
		// Actualizo informacion de las solicitudes de compra que finalmente se utilizaron en este proceso
		this.updatePORequests();
		
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
	 * Completa macro solicitudes
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 12/11/2012
	 * @see
	 * @return
	 */
	private boolean setWaitingMacroRequests() {
		
		try{
			for (MRFQMacroReq macro: hashMacro.values()){
				macro.setDocStatus(DOCSTATUS_WaitingConfirmation);
				macro.setDocAction(DOCACTION_Complete);
			}
			
			return true;
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Valida cantidad de proveedores por producto segun politica de compra.
	 * OpenUp Ltda. Issue #93. 
	 * @author Gabriel Vila - 07/11/2012
	 * @see
	 * @return
	 */
	private boolean validateVendors() {

		// Recorro proveedores para cada producto 
		List<MRFQGenProd> genprods = this.getProducts();
		
		for (MRFQGenProd genprod: genprods){
			
			List<MRFQGenVendor> genvendors = genprod.getVendors();
			
			if (genvendors.size() < genprod.getQtyQuote()){
				MProduct prod = new MProduct(getCtx(), genprod.getM_Product_ID(), null);
				this.processMsg = "Según Politica de Compra, no hay suficiente numero de Proveedores para el producto : " 
								  + prod.getValue() + " - " + prod.getName();
				return false;
			}
		}
		
		return true;
	}

	/***
	 * Actualiza informacion en las solicitudes de compra que finalmente se utilizaron en este proceso.
	 * OpenUp Ltda. Issue #93. 
	 * @author Gabriel Vila - 20/06/2012
	 * @see
	 */
	private void updatePORequests() {

		try{
			
			// Obtengo requisiciones utilizadas en este proceso
			MRFQGenReq[] reqs = this.getRFQGenRequisitions();
			for(int i = 0; i < reqs.length; i++) {
				if (reqs[i].isSelected()){
					MRequisitionLine rline = new MRequisitionLine(getCtx(), reqs[i].getM_RequisitionLine_ID(), get_TrxName());
					rline.setUY_RFQGen_Filter_ID(this.get_ID());
				}
			}
			
		}
		catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
		
	}

	/***
	 * Obtiene y retorna requisiciones utilizadas en este proceso.
	 * OpenUp Ltda. Issue #93. 
	 * @author Gabriel Vila - 20/06/2012
	 * @see
	 * @return
	 */
	private MRFQGenReq[] getRFQGenRequisitions() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MRFQGenReq> list = new ArrayList<MRFQGenReq>();
		
		try{
			sql ="SELECT req.uy_rfqgen_req_id " + 
 		  	" FROM uy_rfqgen_req req " +
 		  	" INNER JOIN uy_rfqgen_prod prod on req.uy_rfqgen_prod_id = prod.uy_rfqgen_prod_id " +
		  	" WHERE prod.uy_rfqgen_filter_id =?";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MRFQGenReq value = new MRFQGenReq(Env.getCtx(), rs.getInt(1), get_TrxName());
				list.add(value);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return list.toArray(new MRFQGenReq[list.size()]);
	}

	/***
	 * Genera solicitudes de cotizacion segun proveedores, articulos y solicitudes seleccionadas
	 * por el usuario en este proceso.
	 * Se generara una unica solicitud de cotizacion por proveedor consolidada.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 20/06/2012
	 * @see
	 */
	private void generateRFQRequests() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			// Obtengo la informacion de proveedores, articulos y solicitudes de compra indicadas por el usuario
			// de manera que me queden ordenadas por proveedor - articulo.
			sql = " select genvend.c_bpartner_id, genprod.m_product_category_id, genprod.m_product_id, " +
				  " genprod.qtyrequired, " +
				  " genprod.c_uom_id, genprod.c_currency_id, max(genreq.daterequired) as daterequired " +
				  " from uy_rfqgen_vendor genvend " +
				  " inner join uy_rfqgen_prod genprod on genvend.uy_rfqgen_prod_id = genprod.uy_rfqgen_prod_id " +
				  " inner join uy_rfqgen_req genreq on genprod.uy_rfqgen_prod_id = genreq.uy_rfqgen_prod_id " +
				  " where genprod.uy_rfqgen_filter_id =? " +
				  " and genprod.qtyrequired > 0 " +
				  " and genvend.isselected='Y' " +
				  " and genreq.isselected='Y' " +
				  " group by genvend.c_bpartner_id, genprod.m_product_category_id, genprod.m_product_id, " +
				  " genprod.qtyrequired, " +
				  " genprod.c_uom_id, genprod.c_currency_id " +
				  " order by genvend.c_bpartner_id, genprod.m_product_category_id ";
			
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, this.get_ID());

			rs = pstmt.executeQuery();

			int cBPartnerID = 0, mProductCategoryID = 0;
			MRFQRequisition rfqReq = null;
			
			while (rs.next()) {
			
				// Corte por proveedor y categoria de producto
				if ((rs.getInt("c_bpartner_id") != cBPartnerID)
						|| (rs.getInt("m_product_category_id") != mProductCategoryID)){

					if (rfqReq != null){
						
						// Valido y seteo macro para esta solicitud
						this.setMacroRequest(rfqReq);

						// Completo soicitud de cotizacion generada para este proveedor
						if (!rfqReq.processIt(ACTION_Complete)){
							throw new AdempiereException(rfqReq.getProcessMsg());
						}
						
						// Genero una cotizacion para el proveedor asociada a esta solicitud
						// y la dejo en borrador para que el proveedor la responda.
						rfqReq.generateQuoteVendor();

					}
					
					rfqReq = new MRFQRequisition(getCtx(), 0, get_TrxName());
					cBPartnerID = rs.getInt("c_bpartner_id");
					mProductCategoryID = rs.getInt("m_product_category_id");
					int cCurrencyID = rs.getInt("c_currency_id");
					rfqReq.setC_DocType_ID(MDocType.forValue(getCtx(), "rfqrequisition", null).get_ID());
					rfqReq.setDescription("Automatica. Generacion de Solicitud de Cotizacion.");
					rfqReq.setUY_RFQGen_Filter_ID(this.get_ID());
					rfqReq.setC_BPartner_ID(cBPartnerID);
					rfqReq.setAD_User_ID(this.getUpdatedBy());
					rfqReq.setSendEMail(true);
					rfqReq.setUY_POPolicy_ID(this.getUY_POPolicy_ID());
					rfqReq.setC_Currency_ID(cCurrencyID);
					rfqReq.saveEx();
					
				}

				MRFQRequisitionLine rfqReqLine = new MRFQRequisitionLine(getCtx(), 0, get_TrxName());
				rfqReqLine.setUY_RFQ_Requisition_ID(rfqReq.get_ID());
				rfqReqLine.setM_Product_ID(rs.getInt("m_product_id"));
				rfqReqLine.setC_UOM_ID(rs.getInt("c_uom_id"));
				rfqReqLine.setQtyRequired(rs.getBigDecimal("qtyrequired"));
				rfqReqLine.setDateRequired(rs.getTimestamp("daterequired"));
				rfqReqLine.saveEx();
				
			}

			// Ultimo proveedor
			if (rfqReq != null){
				
				// Valido y seteo macro para esta solicitud
				this.setMacroRequest(rfqReq);
				
				// Completo soicitud de cotizacion generada para este proveedor
				if (!rfqReq.processIt(ACTION_Complete)){
					throw new AdempiereException(rfqReq.getProcessMsg());
				}
				
				// Genero una cotizacion para el proveedor asociada a esta solicitud
				// y la dejo en borrador para que el proveedor la responda.
				rfqReq.generateQuoteVendor();
				
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		
	}

	/***
	 * Valida macro solicitudes y setea una para solicitud recibida.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 11/11/2012
	 * @see
	 * @param rfqReq
	 */
	private void setMacroRequest(MRFQRequisition rfqReq) {
		
		String exceptionMessage = "No se puede generar una Macro Solicitud con productos diferentes en sus solicitudes.";
		
		try{
			// Debo validar por cada producto de esta solicitud, si estan ya en alguna macro, 
			// en caso de estar deben coincidir los productos de esta solicitud con los de la macro.
			List<MRFQRequisitionLine> reqlines = rfqReq.getLines(get_TrxName());
			
			if (reqlines.size() <= 0) 
				throw new AdempiereException("La solicitud de cotizacion no tiene lineas");
			
			boolean firstItem = true;
			MRFQMacroReq macro = null;
			Timestamp maxDateRequired = null;
			
			for (MRFQRequisitionLine reqline: reqlines){
				if (firstItem){
					firstItem = false;
					// Si ya tengo una macro para este primer producto
					if (hashMacroXProd.containsKey(reqline.getM_Product_ID())){
						macro = hashMacro.get(hashMacroXProd.get(reqline.getM_Product_ID()));
					}
				}
				else{
					// Si ya tengo macro para un articulo
					if (macro != null){
						// Verifico que este producto tambien pertenezca a la misma macro
						if (!hashProdsXMacro.get(macro.get_ID()).containsKey(reqline.getM_Product_ID())){
							throw new AdempiereException(exceptionMessage);
						}							
					}
					else{
						// Verifico si este producto ya tiene una macro si es asi no sirve
						// ya que el primer producto no tiene
						if (hashMacroXProd.containsKey(reqline.getM_Product_ID())){
							throw new AdempiereException(exceptionMessage);
						}
					}
				}
				
				if (maxDateRequired == null){
					maxDateRequired = reqline.getDateRequired();
				}					
				else{
					if (maxDateRequired.after(reqline.getDateRequired())){
						maxDateRequired = reqline.getDateRequired();
					}
				}
					
			}
			
			// Si tengo macro, valido que la cantidad de productos de la macro sea igual a la cantidad
			// de productos de esta solicitud
			if (macro != null){
				if (hashProdsXMacro.get(macro.get_ID()).size() != reqlines.size()) {
					throw new AdempiereException(exceptionMessage);
				}
			}
			else{
				// No tenia ningun macro para los productos de esta solicitud.
				// Macro nueva
				macro = new MRFQMacroReq(getCtx(), 0, get_TrxName());
				macro.setDefaultDocType();
				macro.setDateTrx(this.getDateTrx());
				macro.setDescription("Automatico desde Generacion de Cotizacion.");
				macro.setUY_RFQGen_Filter_ID(this.get_ID());
				macro.setIsApproved(false);
				macro.setDueDate(maxDateRequired);
				macro.setDocStatus(DOCSTATUS_WaitingConfirmation);
				macro.setAD_User_ID(this.getAD_User_ID());
				macro.saveEx();
				
				// Guardo macro en memoria
				hashMacro.put(macro.get_ID(), macro);
				hashProdsXMacro.put(macro.get_ID(), new HashMap<Integer, Integer>());
				for (MRFQRequisitionLine reqline: reqlines){
					hashMacroXProd.put(reqline.getM_Product_ID(), macro.get_ID());
					hashProdsXMacro.get(macro.get_ID()).put(reqline.getM_Product_ID(), reqline.getM_Product_ID());
				}				
			}
			
			// Solitud recibida por parametro se asocia a macro como una linea
			MRFQMacroReqLine macroline = new MRFQMacroReqLine(getCtx(), 0, get_TrxName());
			macroline.setUY_RFQ_MacroReq_ID(macro.get_ID());
			macroline.setUY_RFQ_Requisition_ID(rfqReq.get_ID());
			macroline.setIsApproved(false);
			macroline.saveEx();
			
			// Guardo asociacion macro y linea de macro con la solicitud
			rfqReq.setUY_RFQ_MacroReq_ID(macro.get_ID());
			rfqReq.setUY_RFQ_MacroReqLine_ID(macroline.get_ID());
			
		}
		
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {
		// Before Void
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 25/05/2012
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean applyIt() {

		this.loadData();
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	/***
	 * Obtiene y carga informacion de requisiciones a considerar en este proceso.
	 * Consolida por articulo.
	 * OpenUp Ltda. Issue #93 
	 * @author Hp - 18/06/2012
	 * @see
	 * @return
	 */
	private void loadData(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			MAcctSchema schema = client.getAcctSchema();
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			MPOPolicy policy = new MPOPolicy(getCtx(), this.getUY_POPolicy_ID(), null);
			
			String whereFiltros = "";

			if (this.getUY_POSection_ID() > 0) whereFiltros += " AND req.uy_posection_id =" + this.getUY_POSection_ID();
			//if (this.getM_Product_Category_ID() > 0) whereFiltros += " AND sectcat.m_product_category_id =" + this.getM_Product_Category_ID();
			if (this.getM_Product_Category_ID() > 0) whereFiltros += " AND prod.m_product_category_id =" + this.getM_Product_Category_ID();
			if (this.getuy_datepromised_from() != null) whereFiltros += " AND req.daterequired >='" + this.getuy_datepromised_from() + "'";
			if (this.getuy_datepromised_to() != null) whereFiltros += " AND req.daterequired <='" + this.getuy_datepromised_to() + "'";
			
			// Filtro de productos
			String whereProducts = "";
			List<MRFQGenFilterProd> prodfilters = this.getProductFilters();
			if (prodfilters != null){
				if (prodfilters.size() > 0){
					whereProducts = " AND line.m_product_id in " +
							"(select m_product_id from uy_rfqgen_filter_prod " +
							" where uy_rfqgen_filter_id =" + this.get_ID() + ")";
				}
			}
			
			sql = " select distinct req.m_requisition_id,  line.m_requisitionline_id, line.m_product_id, line.qty, coalesce(line.c_uom_id,0) as c_uom_id, " +
				  " req.ad_user_id, req.daterequired, req.datedoc, line.priceactual, line.c_currency_id, line.dividerate, prod.m_product_category_id " +
				  " from m_requisition req " +
				  " inner join m_requisitionline line on req.m_requisition_id = line.m_requisition_id " +
				  //" inner join uy_posectioncategory sectcat on req.uy_posection_id = sectcat.uy_posection_id " +
				  " inner join m_product prod on line.m_product_id = prod.m_product_id " +
				  " where req.ad_client_id =? " + 
				  " and req.ad_org_id =? " +
				  " and req.pouser_id =" + this.getAD_User_ID() +
				  " and req.docstatus='CO' " +
				  " and line.m_requisitionline_id not in ( " +
				  " select genreq.m_requisitionline_id " +
				  " from uy_rfqgen_req genreq " +
				  " inner join uy_rfqgen_prod genprod on genreq.uy_rfqgen_prod_id = genprod.uy_rfqgen_prod_id " +
				  " inner join uy_rfqgen_filter genfilt on genprod.uy_rfqgen_filter_id = genfilt.uy_rfqgen_filter_id " +
				  " where genfilt.docstatus='CO' and genreq.isselected='Y') " + whereFiltros + whereProducts +
				  " order by line.m_product_id ";
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setInt(2, this.getAD_Org_ID());

			rs = pstmt.executeQuery();

			int mProductID = 0;
			MRFQGenProd genProd = null;
			MProduct prod = null;
			BigDecimal sumQty = Env.ZERO, maxPrice = Env.ZERO;
			boolean hayInfo = false;
			
			while (rs.next()) {
				
				hayInfo = true;
				
				// Corte por producto
				if (rs.getInt("m_product_id") != mProductID){
					
					if (genProd != null){
						genProd.setQtyRequired(genProd.getQtyRequired().add(sumQty));
						genProd.setPriceActual(maxPrice);
						genProd.setTotalAmt(genProd.getQtyRequired().multiply(genProd.getPriceActual()).setScale(2, RoundingMode.HALF_UP));
					
						// Cantidad de cotizaciones segun total y politica de compra (solo si tengo un total)
						if (genProd.getTotalAmt().compareTo(Env.ONE) >= 0){
							genProd.setQtyQuote(policy.getRangeForAmount(today, genProd.getTotalAmt(), schema.getC_Currency_ID(), null).getQty());	
						}
						else{
							genProd.setQtyQuote(0);
						}
						
						genProd.saveEx();
					}
					
					prod = new MProduct(getCtx(), rs.getInt("m_product_id"), null);
					sumQty = Env.ZERO;
					maxPrice = Env.ZERO;
					mProductID = rs.getInt("m_product_id");
					genProd = new MRFQGenProd(getCtx(), 0, get_TrxName());
					genProd.setUY_RFQGen_Filter_ID(this.get_ID());
					genProd.setM_Product_ID(mProductID);
					genProd.setM_Product_Category_ID(rs.getInt("m_product_category_id"));
					genProd.setC_UOM_ID(prod.getC_UOM_ID());
					genProd.setQtyRequired(Env.ZERO);
					genProd.setC_Currency_ID(schema.getC_Currency_ID());
					genProd.saveEx();
					
					// Carga proveedores de este producto en tab correspondiente
					this.loadProductVendors(genProd, prod.getPOVendors());
				}

				// Llevo cantidad en unidad de medida del producto
				BigDecimal lineQty = rs.getBigDecimal("qty");
				if (rs.getInt("c_uom_id") > 0){
					if (prod.getC_UOM_ID() != rs.getInt("c_uom_id")){
						lineQty = lineQty.multiply(MUOMConversion.getProductRateTo(getCtx(), mProductID, rs.getInt("c_uom_id")));
					}
				}
				
				// Consolido cantidad del producto solicitada
				sumQty = sumQty.add(lineQty);
				
				// Me quedo con el mayor precio indicado para este articulo en sus solicitudes
				BigDecimal linePrice = rs.getBigDecimal("priceactual");
				if (rs.getInt("c_currency_id") != schema.getC_Currency_ID()){
					linePrice = linePrice.multiply(MConversionRate.getRate(rs.getInt("c_currency_id"), schema.getC_Currency_ID(), today, 0, this.getAD_Client_ID(), 0)).setScale(2, RoundingMode.HALF_UP); 
				}				
				if (maxPrice.compareTo(linePrice) < 0){
					maxPrice = linePrice;
				}
				
				// Save requisicion en tab del proceso
				MRFQGenReq genReq = new MRFQGenReq(getCtx(), 0, get_TrxName());
				genReq.setUY_RFQGen_Prod_ID(genProd.get_ID());
				genReq.setM_Requisition_ID(rs.getInt("m_requisition_id"));
				genReq.setM_RequisitionLine_ID(rs.getInt("m_requisitionline_id"));
				genReq.setC_UOM_ID(prod.getC_UOM_ID());
				genReq.setQtyRequired(lineQty);
				genReq.setIsSelected(true);
				genReq.setDateRequired(rs.getTimestamp("daterequired"));
				genReq.setDateTrx(rs.getTimestamp("datedoc"));
				genReq.setAD_User_ID(rs.getInt("ad_user_id"));
				genReq.saveEx();
				
			}

			// Save ultimo producto
			if (genProd != null){
				genProd.setQtyRequired(genProd.getQtyRequired().add(sumQty));
				genProd.setPriceActual(maxPrice);
				genProd.setTotalAmt(genProd.getQtyRequired().multiply(genProd.getPriceActual()).setScale(2, RoundingMode.HALF_UP));
				
				// Cantidad de cotizaciones segun total y politica de compra (solo si tengo un total)
				if (genProd.getTotalAmt().compareTo(Env.ONE) >= 0){
					genProd.setQtyQuote(policy.getRangeForAmount(today, genProd.getTotalAmt(), schema.getC_Currency_ID(), null).getQty());	
				}
				else{
					genProd.setQtyQuote(0);
				}
				
				genProd.saveEx();
			}
			
			// Si la select no trajo registros para procesar, aviso
			if (!hayInfo){
				throw new AdempiereException("No se obtuvieron solicitudes de compra a procesar segun valores de Filtros.\n" +
											 "Verifique que usted sea Encargado de Compras de algun Sector.");
			}
			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	/***
	 * Carga informacion de proveedores de este producto en tab correspondiente.
	 * OpenUp Ltda. Issue #1034 
	 * @author Gabriel Vila - 18/06/2012
	 * @see
	 * @param genProd
	 * @param poVendors
	 */
	private void loadProductVendors(MRFQGenProd genProd, List<MProductPO> poVendors) {
		
		try{
			// Recorro proveedores y guardo 
			for(MProductPO poVendor: poVendors){
				
				MRFQGenVendor genVendor = new MRFQGenVendor(getCtx(), 0, get_TrxName());
				genVendor.setUY_RFQGen_Prod_ID(genProd.get_ID());
				genVendor.setC_BPartner_ID(poVendor.getC_BPartner_ID());
				genVendor.setIsSelected(true);
				genVendor.saveEx();
			}
		}
		catch(Exception ex){
			throw new AdempiereException(ex);
		}
	}

	/***
	 * Obtiene y retorna productos de este proceso de generacion.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 07/11/2012
	 * @see
	 * @return
	 */
	public List<MRFQGenProd> getProducts(){
		
		String whereClause = X_UY_RFQGen_Prod.COLUMNNAME_UY_RFQGen_Filter_ID + "=" + this.get_ID();
		
		List<MRFQGenProd> lines = new Query(getCtx(), I_UY_RFQGen_Prod.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.list();
		
		return lines;
		
	}

	/***
	 * Obtiene y retorna filtro de productos para esta generacion
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 12/12/2012
	 * @see
	 * @return
	 */
	public List<MRFQGenFilterProd> getProductFilters(){
		
		String whereClause = X_UY_RFQGen_Filter_Prod.COLUMNNAME_UY_RFQGen_Filter_ID + "=" + this.get_ID();
		
		List<MRFQGenFilterProd> lines = new Query(getCtx(), I_UY_RFQGen_Filter_Prod.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.list();
		
		return lines;
		
	}

	/***
	 * Envia emails con solicitud de cotizaciones a los proveedores de las cotizaciones
	 * generadas por este proceso.
	 * OpenUp Ltda. Issue #583 
	 * @author Gabriel Vila - 17/05/2013
	 * @see
	 */
	public void sendMailToVendors() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " select uy_quotevendor_id from uy_rfq_requisition " +
				  " where uy_rfqgen_filter_id =" + this.get_ID();
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){

				MQuoteVendor qv = new MQuoteVendor(getCtx(), rs.getInt(1), null);
				qv.sendEmail(true);
				
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
	}

}
