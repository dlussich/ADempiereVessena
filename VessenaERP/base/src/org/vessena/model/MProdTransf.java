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
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_UY_Confirmorderhdr;
import org.compiere.model.MConfirmorderhdr;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.MPPOrderBOMLine;

/**
 * org.openup.OpenUp371 - MProdTransf
 * OpenUp Ltda. Issue #4126 
 * Description: Clase para el header de las ordenes de produccion de transformación.
 * Define el input para las Ordenes de Trabajo que tengan más de un producto como output. 
 * @author INes Fernandez - 05/2015
  */
public class MProdTransf extends X_UY_ProdTransf implements DocAction {

	
	private static final long serialVersionUID = 8136629947632268988L;

	private String processMsg = null;
	private boolean justPrepared = false;
	private MProdTransfOut[] m_lines;
	private MPPOrderBOMLine[] m_BOMlines;

	
	/**
	 * @param ctx
	 * @param UY_ProdTransf_ID
	 * @param trxName
	 */
	public MProdTransf(Properties ctx, int UY_ProdTransf_ID, String trxName) {
		super(ctx, UY_ProdTransf_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProdTransf(Properties ctx, ResultSet rs, String trxName) {
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
		
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		
		//Convert DocType to Target
		if (getC_DocType_ID() != getC_DocTypeTarget_ID() )
		{
			//	New or in Progress/Invalid
			if (DOCSTATUS_Drafted.equals(getDocStatus()) 
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| getC_DocType_ID() == 0) 
			{
				setC_DocType_ID(getC_DocTypeTarget_ID());
			}
		}	//	convert DocType
		
		
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
		
		MDocType doc = new MDocType(this.getCtx(), this.getC_DocTypeTarget_ID(), this.get_TrxName());
		
		if(doc.getValue()!=null && !doc.getValue().equalsIgnoreCase("ordenreb")){
			
			//	check output
			MProdTransfOut[] lines =  getOutputLines("");
			
			if (lines == null || lines.length == 0)
			{
				throw new AdempiereException("Debe indicarse producto de salida");
			}		
		}
		
		//controla que la sumatoria de kg de prods de output no supere los kg del input
		if(this.isMultipleOutput()){
			/*MProdTransfOut[] outputLines =this.getOutputLines("");
			BigDecimal outputKG =BigDecimal.ZERO;
			for(MProdTransfOut aux : outputLines){
				
				outputKG = outputKG.add(aux.getQtyOrdered());
			}
			if(this.getQtyOrdered().compareTo(outputKG)<0){
				throw new AdempiereException("Atencion: los kg de producto de salida superan los de producto de entrada");
			}*/
					
		} else {
			
			MProdTransfInput[] inputLines =this.getInputLines("");
			
			if(inputLines.length <= 0) throw new AdempiereException("Se deben indicar los insumos para este tipo de orden");		
			
		}
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		//Refresco atributos
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

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		if(this.getConfHdrLines(" AND DocStatus <> 'VO'").length > 0) 
			throw new AdempiereException("Imposible anular, existen confirmaciones de trabajo en estado completo o borrador");

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
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Closed);
//		this.setProcessing(false);
//		this.setProcessed(true);
		
		return true;
	}
	
	public String closeTechnical(PO poDocSource) {

		this.setUY_CerroConfirmacion(true);
		
		if (poDocSource == null) poDocSource = this;

		Timestamp ahora = new Timestamp(System.currentTimeMillis());

		// Caso de una confirmacion
		if (poDocSource.get_TableName().equalsIgnoreCase("UY_Confirmorderhdr")) {

			this.setDateFinish(((MConfirmorderhdr) poDocSource).getDateFinish());
			this.setDescription("Cerrada por confirmacion: " + ((MConfirmorderhdr) poDocSource).getDocumentNo());
		} else {

			this.setDateFinish(ahora);
			this.setDescription("Cerrada por Cierre Tecnico");
		}

		// Mensaje de salida
		String message = "";

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "SELECT m_attributesetinstance_id,uy_stockstatus_id,m_warehouse_id,M_product_id, sum(movementqty*sign)as acumulado "
					+ "FROM uy_stocktransaction  WHERE record_affected_id=? AND ad_table_affected_id=? AND uy_stockstatus_id=? "
					+ "GROUP BY uy_stockstatus_id,m_warehouse_id,M_product_id, m_attributesetinstance_id";

			int stockstatusID = MStockStatus.getStatusReservedID(null);

			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());
			pstmt.setInt(2, this.get_Table_ID());
			pstmt.setInt(3, stockstatusID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int attributesetinstanceID = rs.getInt("m_attributesetinstance_id");
				int warehouseID = rs.getInt("m_warehouse_id");
				int productID = rs.getInt("M_product_id");
				BigDecimal acumulado = rs.getBigDecimal("acumulado");

				if (acumulado.compareTo(Env.ZERO) > 0) {// defensivo
					String auxmessage = MStockTransaction.add(poDocSource, this, warehouseID, 0, productID, attributesetinstanceID, stockstatusID, ahora,
							acumulado.multiply(new BigDecimal(-1)), 0, null);
				if (auxmessage != null) message += auxmessage;

					// Chequeo disponibilidad de este producto una vez realizados los movimientos de stock
				
					// Disponibilidad a fecha del movimiento
					auxmessage = MStockTransaction.checkAvailability(warehouseID, 0, productID, 0, ahora, 
							poDocSource.getAD_Client_ID(), poDocSource.get_TrxName());
					if (auxmessage != null) message += auxmessage;
					
					// Disponibilidad a hoy
					auxmessage = MStockTransaction.checkAvailability(warehouseID, 0, productID, 0, 
							TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY), 
							poDocSource.getAD_Client_ID(), poDocSource.get_TrxName());
					if (auxmessage != null) message += auxmessage;
					
				}

			}

			this.saveEx();

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			message = e.toString();
			return message;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (message.equalsIgnoreCase("")) message = null;
		
		return message;
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

		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
		
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
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		this.setC_DocType_ID(this.getC_DocTypeTarget_ID());
		//ctrol de fechas: HOY <= fecha programada <= fecha prometida
		
		//Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		Timestamp dateOrdered = TimeUtil.trunc(this.getDateOrdered(), TimeUtil.TRUNC_DAY);
		Timestamp dateProgramada = TimeUtil.trunc((Timestamp)this.get_Value("Date1"), TimeUtil.TRUNC_DAY);
		Timestamp datePromised = TimeUtil.trunc(this.getDatePromised(), TimeUtil.TRUNC_DAY);
		
		if(dateOrdered.compareTo(datePromised)>0) //inutil para la logica, util para el msg al usu
			throw new AdempiereException("La Fecha Prometida no puede ser anterior a la Fecha de la Orden");
		if (dateProgramada.compareTo(datePromised)>0) 
			throw new AdempiereException("La Fecha Programada no puede ser posterior a la Fecha Prometida");
		if (dateOrdered.compareTo(dateProgramada)>0)
			throw new AdempiereException("La Fecha Programada no puede ser anterior a la Fecha de la Orden");
		

		if(this.isMultipleOutput()){
			
			if(this.getM_Warehouse_ID() <=0) throw new AdempiereException("El almacén origen es obligatorio.");
			if(newRecord || is_ValueChanged("QtyEntered") || is_ValueChanged("C_UOM_ID") || is_ValueChanged("M_Product_ID") || is_ValueChanged("M_Warehouse_ID")){		

				BigDecimal QtyOrdered = Env.ZERO;

				BigDecimal QtyEntered = this.getQtyEntered();
				BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(getCtx(), this.getC_UOM_ID()), BigDecimal.ROUND_HALF_UP);

				if (this.getQtyEntered().compareTo(QtyEntered1) != 0)
				{
					QtyEntered = QtyEntered1;
				}
				QtyOrdered = MUOMConversion.convertProductFrom (getCtx(), this.getM_Product_ID(), this.getC_UOM_ID(), QtyEntered);
				if (QtyOrdered == null) QtyOrdered = QtyEntered;
				
				this.setQtyOrdered(QtyOrdered);
				//control de stock contra el almacen seteado
				Timestamp now = new Timestamp(System.currentTimeMillis());
				MWarehouse warehouse = new MWarehouse(this.getCtx(), this.getM_Warehouse_ID(), this.get_TrxName());
				
				MLocator locator = MLocator.getDefault(warehouse);
				if (locator == null) throw new AdempiereException("El almacén no tiene asociada una ubicación");
				
				
				BigDecimal stock = MStockTransaction.getQtyAvailable(this.getM_Warehouse_ID(), locator.getM_Locator_ID(), this.getM_Product_ID(), 
						0, 0, now, this.get_TrxName());
				if(stock.compareTo(QtyOrdered)<0) throw new AdempiereException("No hay stock suficiente en el almacén seleccionado");
			}
			
			
			if(!newRecord && (is_ValueChanged("M_Product_ID") || is_ValueChanged("QtyOrdered"))){//se actualizo prod, UOM o QtyEntered
				//ctrl que el prod no este en alguna de las lineas
				String where = "AND M_Product_ID = "+ this.getM_Product_ID();
				if( this.getOutputLines(where)!=null && this.getOutputLines(where).length>0){
					throw new AdempiereException("El producto esta en una de las lineas de salida, para continuar elimine la linea");
				}//TODO: CHECK NICOLAS SARLABOS
				//controla que la sumatoria de kg de prods de output no supere los kg del input
//				MProdTransfOut[] outputLines =this.getOutputLines("");
//				BigDecimal outputKG =BigDecimal.ZERO;
//				for(MProdTransfOut aux : outputLines){
//					outputKG = outputKG.add(aux.getQtyOrdered());
//				}
//				if(this.getQtyOrdered().compareTo(outputKG)<0){//TODO: ESTE QtyOrdered es el actual o el de la DB?? ********
//					throw new AdempiereException("Atencion: los kg de producto de salida superan los de producto de entrada");
//				}
			}
			
			
		}
 		return super.beforeSave(newRecord);
	}

	
	/**
	 * Get Output Lines 
	 * @param whereClause starting with AND
	 * @return lines
	 */
	public MProdTransfOut[] getOutputLines (String whereClause){
		String whereClauseFinal = "UY_ProdTransf_ID=?";
		if (whereClause != null) whereClauseFinal += whereClause;
		List<MProdTransfOut> list = new Query(getCtx(), I_UY_ProdTransfOut.Table_Name, whereClauseFinal, get_TrxName())
		.setParameters(this.get_ID())
		.setOrderBy(I_UY_ProdTransfOut.COLUMNNAME_Line).list();
		return list.toArray(new MProdTransfOut[list.size()]);
	} // getLines
	
	/**
	 * Get Output Lines 
	 * @param whereClause starting with AND
	 * @return lines
	 */
	public MProdTransfInput[] getInputLines (String whereClause){
		String whereClauseFinal = "UY_ProdTransf_ID=?";
		if (whereClause != null) whereClauseFinal += whereClause;
		List<MProdTransfInput> list = new Query(getCtx(), I_UY_ProdTransfInput.Table_Name, whereClauseFinal, get_TrxName())
		.setParameters(this.get_ID()).list();
		return list.toArray(new MProdTransfInput[list.size()]);
	} // getLines


	/**
	 * Get BOM Lines of PP Order
	 * @param requery
	 * @return Order BOM Lines
	 */
	public MPPOrderBOMLine[] getLines(boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_BOMlines;
		}
		String whereClause = MPPOrderBOMLine.COLUMNNAME_PP_Order_ID+"=?";
		List<MPPOrderBOMLine> list = new Query(getCtx(), MPPOrderBOMLine.Table_Name, whereClause, get_TrxName())
										.setParameters(new Object[]{getUY_ProdTransf_ID()})
										.setOrderBy(MPPOrderBOMLine.COLUMNNAME_Line)
										.list();
		m_BOMlines = list.toArray(new MPPOrderBOMLine[list.size()]);
		return m_BOMlines;
	}
	
	/**
	 * Get Output Lines 
	 * @param whereClause starting with AND
	 * @return lines
	 */
	public MConfirmorderhdr[] getConfHdrLines (String whereClause){
		String whereClauseFinal = "UY_ProdTransf_ID=?";
		if (whereClause != null) whereClauseFinal += whereClause;
		List<MConfirmorderhdr> list = new Query(getCtx(), I_UY_Confirmorderhdr.Table_Name, whereClauseFinal, get_TrxName())
		.setParameters(this.get_ID()).list();
		return list.toArray(new MConfirmorderhdr[list.size()]);
	}
	
	/**
	 * Get Order BOM Lines
	 * @return Order BOM Lines
	 */
	public MPPOrderBOMLine[] getLines()
	{
		return getLines(true);
	}
	
	//Método para preguntar si tiene más de un tipo de producto en el output
	//ToDo: **HARDCODED** agregar un campo isMultipleOutput, esto es una solución provisoria.
	public boolean isMultipleOutput(){
		
		MDocType target = new MDocType(getCtx(), this.getC_DocTypeTarget_ID(), get_TrxName());
		
		if(target.getValue().equals("ordencorte") /*|| target.getValue().equals("ordenreb")*/) return true; 
		
		return false;
	}
	
}
