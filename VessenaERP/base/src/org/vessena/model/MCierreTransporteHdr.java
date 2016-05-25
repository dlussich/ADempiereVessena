/**
 * MCierreTransporteHdr.java
 * 14/02/2011
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;


import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;

import org.compiere.model.MProduct;
import org.compiere.model.MUOMConversion;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.InfoDocumentoBean;

/**
 * OpenUp.
 * MCierreTransporteHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 14/02/2011
 */
public class MCierreTransporteHdr extends X_UY_CierreTransporteHdr implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6901192416784403271L;

	private boolean justPrepared = false;
	private String processMsg = null;
	private HashMap<Integer,BigDecimal>  hashMapProductos=null;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_CierreTransporteHdr_ID
	 * @param trxName
	 */
	public MCierreTransporteHdr(Properties ctx, int UY_CierreTransporteHdr_ID,
			String trxName) {
		super(ctx, UY_CierreTransporteHdr_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCierreTransporteHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
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
		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		/*try {
			if (!this.cantidadesValidas()) return DocAction.STATUS_Invalid;

		} catch (Exception e) {
			this.processMsg = e.getMessage();
			return DocAction.STATUS_Invalid;
		}*/

		// Si el atr asociado a este cierre ya fue utilizado en otro cierre completado.
		if (atrYaCerrada()) return DocAction.STATUS_Invalid;
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			this.processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		
		// Refresco estados
		setProcessed(true);
		setDocStatus(DocAction.STATUS_Completed);
		setDocAction(DocAction.ACTION_None);
		return DocAction.STATUS_Completed;
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
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
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
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
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
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
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
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		return DocAction.STATUS_InProgress;
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
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
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
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
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
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
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

		String action = "DELETE FROM uy_cierretransportefact WHERE uy_cierretransportehdr_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		action = "DELETE FROM uy_cierretransportedoc WHERE uy_cierretransportehdr_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		action = "DELETE FROM uy_cierretransporteprod WHERE uy_cierretransportehdr_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Elimina facturas de un determinado cierre de transporte.
	 * @param cierreTransporteHdrID
	 * @author  Gabriel Vila 
	 * Fecha : 15/02/2011
	 */	
	private void deleteFacturas(){
		String action = " DELETE FROM " + X_UY_CierreTransporteFact.Table_Name +
					    " WHERE " + X_UY_CierreTransporteFact.COLUMNNAME_UY_CierreTransporteHdr_ID + "=" + this.get_ID();
		DB.executeUpdate(action,null);
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Elimina otros documentos de un determinado cierre de transporte.
	 * @param cierreTransporteHdrID
	 * @author  Gabriel Vila 
	 * Fecha : 15/02/2011
	 */	
	private void deleteOtrosDocumentos(){
		String action = " DELETE FROM " + X_UY_CierreTransporteDoc.Table_Name +
					    " WHERE " + X_UY_CierreTransporteFact.COLUMNNAME_UY_CierreTransporteHdr_ID + "=" + this.get_ID();
		DB.executeUpdate(action,null);
	}
	

	/**
	 * OpenUp.	
	 * Descripcion : Elimina productos recibidos de un determinado cierre de transporte.
	 * @param cierreTransporteHdrID
	 * @author  Gabriel Vila 
	 * Fecha : 15/02/2011
	 */	
	private void deleteProductos(){
		String action = " DELETE FROM " + X_UY_CierreTransporteProd.Table_Name +
					    " WHERE " + X_UY_CierreTransporteFact.COLUMNNAME_UY_CierreTransporteHdr_ID + "=" + this.get_ID();
		DB.executeUpdate(action,null);
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Traer todas las mCierreTransporteFact que no fueron entregadas
	 * 				CONDICION uy_recibe_conductor ='N'"
	 * @return
	 * @author  Nicolas Garcia 
	 * Fecha : 02/05/2011
	 */
	public MCierreTransporteFact[] getFactNoEntregadas() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MCierreTransporteFact > list = new ArrayList<MCierreTransporteFact>();
		
		try{
			sql ="SELECT uy_cierretransportefact_id FROM uy_cierretransportefact  " +
					"WHERE uy_cierretransportehdr_id  =? "+ 
					"AND uy_recibe_conductor ='N'";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_CierreTransporteHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MCierreTransporteFact value = new MCierreTransporteFact(Env.getCtx(), rs.getInt("uy_cierretransportefact_id"), null);
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
		
		return list.toArray(new MCierreTransporteFact[list.size()]);		
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Traer todas las mCierreTransporteFact que no fueron entregadas
	 * 				CONDICION uy_recibe_conductor ='N'"
	 * @return
	 * @author  Nicolas Garcia 
	 * Fecha : 02/05/2011
	 */
	public MCierreTransporteDoc[] getDocuments() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MCierreTransporteDoc> list = new ArrayList<MCierreTransporteDoc>();
		
		try{
			sql ="SELECT UY_CierreTransporteDoc_ID FROM UY_CierreTransporteDoc WHERE uy_cierretransportehdr_id =?";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_CierreTransporteHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MCierreTransporteDoc value = new MCierreTransporteDoc(Env.getCtx(), rs.getInt("UY_CierreTransporteDoc_ID"), null);
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
		
		return list.toArray(new MCierreTransporteDoc[list.size()]);		
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 04/05/2011
	 */
	public MInOut[] getDevolucionesCoordinadasConfirmadas(){
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MInOut> list = new ArrayList<MInOut>();
		
		try{
			sql ="SELECT m_inout.m_inout_id FROM uy_asignatransportedevol " +
					"JOIN m_inout ON uy_asignatransportedevol.m_inout_id = m_inout.m_inout_id AND m_inout.docaction=? " +
					"WHERE uy_asignatransportedevol.uy_asignatransportehdr_id=?";
			
			pstmt = DB.prepareStatement (sql, null);
			//FIXME: Se tendra que agregar el docaction, Fabian sabe cual es.
			pstmt.setString(1, "AP");
			pstmt.setInt(2, this.getUY_AsignaTransporteHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MInOut value = new MInOut(Env.getCtx(), rs.getInt("m_inout_id"), null);
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
		
		return list.toArray(new MInOut[list.size()]);	
	}
	
	
	@SuppressWarnings("unused")
	private boolean cantidadesValidas() throws Exception{
		
		hashMapProductos=this.obtenerProductosDevueltos();
		
		if(controlFacturaRecibida() && controlDocumentos() && controlDevolucionesCoordinadas()){
			return todosProductosFueronVinculados(); 
		}
		else{
			return false;
		}
	}
	
	
	//Resuelve Devoluciones Coordiandas 
	private boolean controlDevolucionesCoordinadas()throws Exception{
		
		//busco las cierreTransporteFact no entregadas
		MInOut[] array = this.getDevolucionesCoordinadasConfirmadas();
	
		//recorro las CierreTransporteFact
		for(int i=0;array.length>i;i++){
			
			//Busco las lineas de las Devoluciones
			MInOutLine[]lineasDevoluciones = array[i].getLines();
			
			//recorro lineas
			for (int linea=0;lineasDevoluciones.length>linea;linea++){
				
				int m_product_id=lineasDevoluciones[linea].getM_Product_ID();
				BigDecimal MovementQty =lineasDevoluciones[linea].getMovementQty();
				
				if(hashMapProductos.containsKey(m_product_id)){
					//Si hay cantidad suficiente de ese producto en el hashmap para cumplir con lo de la linea
					if(hashMapProductos.get(m_product_id).compareTo(MovementQty)>=0){
						//Se resta
						hashMapProductos.put(m_product_id , (hashMapProductos.get(m_product_id).subtract(MovementQty )));
						
					}else{//Faltan unidades de ese producto
						this.processMsg = "Para el producto: "+new MProduct(getCtx(),m_product_id,null ).getValue()+" la cantidad no concuerda con"+
										  "los documentos entregados";
						return false;
					}
					
				}else{//Este producto en esta factura no fue contado ya que no se encuentra en pesta�a de productos
					this.processMsg = "Para el producto: "+new MProduct(getCtx(),m_product_id,null ).getValue()+" la cantidad no concuerda con"+
									  " los documentos entregados";
					return false;
				}
			}
			
		}
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Obtengo informacion de ATR
		MAsignaTransporteHdr atr = new MAsignaTransporteHdr(getCtx(), this.getUY_AsignaTransporteHdr_ID(), get_TrxName());
		if (atr.get_ID() > 0) this.setM_Shipper_ID(atr.getM_Shipper_ID());
		
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (!success) return success;

		// Si es una modificacion de un campo distinto al numero de asignacion
		// de transporte, salgo sin hacer nada.
		if ((!newRecord) && (!is_ValueChanged(X_UY_CierreTransporteHdr.COLUMNNAME_UY_AsignaTransporteHdr_ID))) 
			return true;
		
		// Obtengo informacion de la asignacion de transporte
		MAsignaTransporteHdr hdr = new MAsignaTransporteHdr(getCtx(), this.getUY_AsignaTransporteHdr_ID(), null);
		
		// Si no tengo datos guardo exception y salgo en false
		if (hdr.get_ID() <= 0) {
			this.processMsg = "No se obtuvo informacion para el ATR seleccionado.";
			return false;
		}
		
		// Elimina facturas anteriores, otros documentos y productos anteriores
		// para no duplicar informacion.
		this.deleteFacturas();
		this.deleteOtrosDocumentos();
		this.deleteProductos();
		
		// Carga facturas y otros documentos
		MAsignaTransporteFact[] aFacts;
		InfoDocumentoBean[] aDocs;
		try {
			aFacts = hdr.getFacturas();
			aDocs = hdr.getOtrosDocumentos();
		} catch (Exception e) {
			this.processMsg = e.getMessage();
			return false;
		}

		for (int i=0; i < aFacts.length; i++){
			MAsignaTransporteFact aFact = aFacts[i];
			MCierreTransporteFact cFact = new MCierreTransporteFact(Env.getCtx(), 0, get_TrxName());
			cFact.setUY_AsignaTransporteHdr_ID(hdr.getUY_AsignaTransporteHdr_ID());
			cFact.setUY_CierreTransporteHdr_ID(this.get_ID());
			cFact.setUY_ReservaPedidoHdr_ID(aFact.getUY_ReservaPedidoHdr_ID());
			cFact.setC_BPartner_ID(aFact.getC_BPartner_ID());
			cFact.setC_Invoice_ID(aFact.getC_Invoice_ID());
			cFact.setC_Order_ID(aFact.getC_Order_ID());
			
			MOrder orderHdr = new MOrder(Env.getCtx(), aFact.getC_Order_ID(), null);
			cFact.setC_BPartner_Location_ID(orderHdr.getC_BPartner_Location_ID());
			cFact.saveEx();
		}
		for (int i=0; i < aDocs.length; i++){
			InfoDocumentoBean aDoc = aDocs[i];
			MCierreTransporteDoc cDoc = new MCierreTransporteDoc(Env.getCtx(), 0, get_TrxName());
			cDoc.setC_BPartner_ID(aDoc.C_BPartner_ID);
			cDoc.setC_DocType_ID(aDoc.C_DocType_ID);
			cDoc.setDateTrx(aDoc.DateTrx);
			cDoc.setDocumentNo(aDoc.DocumentNo);
			cDoc.setUY_CierreTransporteHdr_ID(this.get_ID());
			cDoc.saveEx();
		}
		return true;
	}

	//Resuelve documentos asociados.
	private boolean controlDocumentos()throws Exception{
		
		MCierreTransporteDoc[] array = this.getDocuments();
		
		//Recorro documentos
		for(int i=0;array.length>i;i++){
			MDocType docType= new MDocType(getCtx(),array[i].getC_DocType_ID(),null);
			
			if (docType.getDocBaseType().equals("MMR")&& docType.getC_DocType_ID()==array[i].getC_DocType_ID()){//MMR Devoluciones Directas de Clientes
				//se asegura de que el documento sea MMR, se llama a la funcion que resuelve los MMR
				if (!docControlDevolucionesDirectas(array[i])) return false;				
			}
		}
		
		return true;
	}
	
	//Funcion que resuelve caso MMR Devolucion directa
	private boolean docControlDevolucionesDirectas(MCierreTransporteDoc dev)throws Exception{
		
		MInOut inOut =dev.getDevolucionDirecta();
		
		if(inOut!=null){
			MInOutLine []lineasInOut=inOut.getLines();
			for (int linea=0;lineasInOut.length>linea;linea++){
			
				int m_product_id=lineasInOut[linea].getM_Product_ID();
				BigDecimal MovementQty =lineasInOut[linea].getMovementQty();
			
				if(hashMapProductos.containsKey(m_product_id)){
					//Si hay cantidad suficiente de ese producto en el hashmap para cumplir con lo de la linea
					if(hashMapProductos.get(m_product_id).compareTo(MovementQty)>=0){
						//Se resta
						hashMapProductos.put(m_product_id , (hashMapProductos.get(m_product_id).subtract(MovementQty )));
						
					}else{//Faltan unidades de ese producto
						this.processMsg = "Para el producto: "+new MProduct(getCtx(),m_product_id,null ).getValue()+" la cantidad no concuerda con"+
										  " los documentos entregados";
						return false;
					}
					
				}else{//Este producto en esta factura no fue contado ya que no se encuentra en pesta�a de productos
					this.processMsg = "Para el producto: "+new MProduct(getCtx(),m_product_id,null ).getValue()+" la cantidad no concuerda con"+
									  " los documentos entregados";
					return false;
				}
			}
		}

		return true;
	}
	//Resuelve si el ATR devuelve factura
	private boolean controlFacturaRecibida()throws Exception{
		
		//busco las cierreTransporteFact no entregadas
		MCierreTransporteFact[] array = this.getFactNoEntregadas();
		
			//recorro las CierreTransporteFact
		for(int i=0;array.length>i;i++){
			
			//Busco las lineas de las facturas
			MInvoiceLine[]lineasFactura = array[i].getFactura().getLines();
			//recorro lineas
			for (int linea=0;lineasFactura.length>linea;linea++){
				
				int m_product_id=lineasFactura[linea].getM_Product_ID();
				BigDecimal qtyentered =lineasFactura[linea].getQtyInvoiced();
				
				if(hashMapProductos.containsKey(m_product_id)){
					//Si hay cantidad suficiente de ese producto en el hashmap para cumplir con lo de la linea
					if(hashMapProductos.get(m_product_id).compareTo(qtyentered)>=0){
						//Se resta
						hashMapProductos.put(m_product_id , (hashMapProductos.get(m_product_id).subtract(qtyentered )));
						
					}else{//Faltan unidades de ese producto
						this.processMsg = "Para el producto: "+new MProduct(getCtx(),m_product_id,null ).getValue()+" la cantidad no concuerda con"+
										  " los documentos entregados";
						return false;
					}
					
				}else{//Este producto en esta factura no fue contado ya que no se encuentra en pesta�a de productos
					this.processMsg = "Para el producto: "+new MProduct(getCtx(),m_product_id,null ).getValue()+" la cantidad no concuerda con"+
									  " los documentos entregados";
					return false;
				}
			}
			
		}		
		return true;
	}
	
	private HashMap<Integer,BigDecimal> obtenerProductosDevueltos()throws Exception {
		
		HashMap<Integer,BigDecimal>  hashMap=new HashMap<Integer,BigDecimal>  ();
		String sql = "SELECT m_product_id,qtyentered,c_uom_id FROM uy_cierretransporteprod " +
						"WHERE uy_cierretransportehdr_id ="+this.getUY_CierreTransporteHdr_ID();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

	
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			
			// Si existe resultado
			while (rs.next()) {
				MProduct producto =new MProduct(getCtx(),rs.getInt("m_product_id"),null);
				BigDecimal qty=rs.getBigDecimal("qtyentered");
				BigDecimal factor = MUOMConversion.getProductRateFrom(Env.getCtx(),producto.getM_Product_ID(),rs.getInt("c_uom_id"));
				
				// Controla que cantidades negativas o 0
				if (qty!= null) {
					if (qty.compareTo(BigDecimal.ZERO)<=0) {
				
					} 
					else {
						if (factor!=null) {
							
								// Si esta en una unidad distinta de medida se multiplica el factor por las unidades para
								//Tener el total de ese producto en unidades
								qty=(qty.multiply(factor));
							
						}
					}
				}
				//Si el producto ya fue ingresado
				if(hashMap.containsKey(producto.getM_Product_ID())){
					//Sumo lo que ya tenia este producto con lo nuevo leido
					hashMap.put(producto.getM_Product_ID(),(hashMap.get(producto.getM_Product_ID()).add(qty)));
					
					
				}else{
					//Si no hay registro del producto
					hashMap.put(producto.getM_Product_ID(), qty);
					
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return hashMap;
	}
		
	//Se usara para validar que en el hashmap de productos sus cantidades sean cero, de esta manera cerraria todo
	private boolean todosProductosFueronVinculados(){
		boolean salida = true;
		//Recorro hashMapProduct
		for(BigDecimal num:hashMapProductos.values()){
			
			if(num.compareTo(BigDecimal.ZERO)>0){
				this.processMsg = "Se ingresaron productos que no estan contemplados en los documentos asociados.";
				return false;//Flag de cierre			
			}
		}
		
		return salida;
	}

	/**
	 * OpenUp. Gabriel Vila. 12/07/2011. Issue #503.
	 * Verifica si el atr asociado a este cierre, no fue utilizado en otro cierre ya completado.
	 * @return
	 */
	private boolean atrYaCerrada(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = false;
	
		try {
			
			sql = " SELECT DocumentNo " +
				  " FROM UY_CierreTransporteHdr " +
				  " WHERE UY_AsignaTransporteHdr_ID =" + this.getUY_AsignaTransporteHdr_ID() +
				  " AND UY_CierreTransporteHdr_ID <>" + this.get_ID() +
				  " AND DocStatus='CO'";
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				this.processMsg = "La Asignacion de Transporte ya fue Cerrada con el Numero de Cierre : " + rs.getString(1);
				result = true;
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return result;
		
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
}
