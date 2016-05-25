/**
 * MReservaPedidoHdr.java
 * 29/11/2010
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.model.MLocator;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MStorage;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MReservaPedidoHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/11/2010
 */
public class MReservaPedidoHdr extends X_UY_ReservaPedidoHdr implements
		DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4595840416997353222L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * Constructor
	 * @param ctx
	 * @param UY_ReservaPedidoHdr_ID
	 * @param trxName
	 */
	public MReservaPedidoHdr(Properties ctx, int UY_ReservaPedidoHdr_ID, String trxName) {
		
		super(ctx, UY_ReservaPedidoHdr_ID, trxName);
		
		//  New
		if (UY_ReservaPedidoHdr_ID == 0)
		{
			setDocStatus(DocumentEngine.STATUS_Drafted);
			setDocAction (DocumentEngine.ACTION_Complete);
			setProcessing(false);
			setProcessed(false);
			setDateTrx(new Timestamp(System.currentTimeMillis()));
			setdatereserved(new Timestamp(System.currentTimeMillis()));
		}

		
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MReservaPedidoHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
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

		setDateTrx(new Timestamp(System.currentTimeMillis()));
		setdatereserved(new Timestamp(System.currentTimeMillis()));
		
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
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
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
		
		log.info(toString());
		
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null) return false;

		this.setDescription("************ RESERVA CANCELADA ************");
		
		// Obtengo y recorro lineas de la reserva
		MReservaPedidoLine[] lines;
		try {
			lines = this.getLines(get_TrxName());
		} catch (Exception e) {
			this.processMsg = "No fue posible obtener las Lineas de la Reserva en la CANCELACION.";
			return false;
		}

		for (int i=0; i<lines.length; i++){

			MReservaPedidoLine line = lines[i];
			MProduct product = new MProduct(getCtx(), line.getM_Product_ID(), get_TrxName());
			
			// Obtengo locator segun setinstance
			int M_Locator_ID = 0; 
			if (line.getM_AttributeSetInstance_ID() != 0)
				M_Locator_ID = MStorage.getM_Locator_ID (line.getM_Warehouse_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), line.getQtyReserved(), get_TrxName());

			//	Si no obtuve locator, me quedo con el default del producto o sino del almacen
			if (M_Locator_ID == 0)
			{
				MWarehouse wh = MWarehouse.get(getCtx(), line.getM_Warehouse_ID());
				M_Locator_ID = product.getM_Locator_ID();
				if (M_Locator_ID!=0) {
					MLocator locator = new MLocator(getCtx(), product.getM_Locator_ID(), get_TrxName());
					//product has default locator defined but is not from the order warehouse
					if(locator.getM_Warehouse_ID()!=wh.get_ID()) {
						M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
					}
				} else {
					M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
				}
			}

			//	Update Storage con cantidad reservada en signo negativo
			if (!MStorage.add(getCtx(), line.getM_Warehouse_ID(), M_Locator_ID, 
				line.getM_Product_ID(), 
				line.getM_AttributeSetInstance_ID(), line.getM_AttributeSetInstance_ID(),
				Env.ZERO, line.getQtyReserved().negate(), Env.ZERO, get_TrxName())) {

				this.processMsg = "No fue posible eliminar Reservas en la CANCELACION.";
				return false;

			}

			// Update reserva de linea de pedido
			MOrderLine oLine = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
			if (oLine.getQtyReserved().compareTo(line.getQtyReserved())>=0) {
				oLine.setQtyReserved(oLine.getQtyReserved().add(line.getQtyReserved().negate()));
				if (!oLine.save(get_TrxName())){
					this.processMsg = "No fue posible eliminar Reservas en la CANCELACION.";
					return false;
				}
			}
			
		}

		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null) return false;
		
		this.setProcessed(true);
		this.setDocStatus(STATUS_Voided);
		this.setDocAction(DOCACTION_None);
		this.saveEx();
		
		return true;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna lineas de reserva de pedido.
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 28/12/2010
	 */
	public MReservaPedidoLine[] getLines(String trxName) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MReservaPedidoLine> list = new ArrayList<MReservaPedidoLine>();
		
		try{
			sql ="SELECT uy_reservapedidoline_id " + 
 		  	" FROM " + X_UY_ReservaPedidoLine.Table_Name + 
		  	" WHERE uy_reservapedidohdr_id =?";
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, this.getUY_ReservaPedidoHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MReservaPedidoLine value = new MReservaPedidoLine(Env.getCtx(), rs.getInt(1), trxName);
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
		
		return list.toArray(new MReservaPedidoLine[list.size()]);		
	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna la suma de bultos de las lineas de esta reserva
	 * Se consideran aquellos productos que tienen unidad de venta distinto de la unidad de medida.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 17/03/2011
	 */
	public BigDecimal getCantidadBultos(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO;
		
		try{
			sql ="SELECT coalesce(SUM(qtyentered),0) as cantidad " + 
 		  	" FROM " + X_UY_ReservaPedidoLine.Table_Name + 
		  	" WHERE " + X_UY_ReservaPedidoLine.COLUMNNAME_UY_ReservaPedidoHdr_ID + "=?" +
		  	" AND " + X_UY_ReservaPedidoLine.COLUMNNAME_C_UOM_ID + "<>100";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_ReservaPedidoHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = rs.getBigDecimal(1);
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
		return value;		
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna lineas de reserva de pedido que tengan cantidad reservada.
	 * Se puede dar que haya lineas con cantidad reservada en cero luego de haber sido anuladas.
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 28/12/2010
	 */
	public MReservaPedidoLine[] getLinesWithQtyReserved() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MReservaPedidoLine> list = new ArrayList<MReservaPedidoLine>();
		
		try{
			sql ="SELECT uy_reservapedidoline_id " + 
 		  	" FROM " + X_UY_ReservaPedidoLine.Table_Name + 
		  	" WHERE uy_reservapedidohdr_id =?" +
		  	" AND qtyreserved > 0";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_ReservaPedidoHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MReservaPedidoLine value = new MReservaPedidoLine(Env.getCtx(), rs.getInt(1), null);
				list.add(value);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return list.toArray(new MReservaPedidoLine[list.size()]);		
	}
	
	/**
	 * 
	 * OpenUp. issue #853	
	 * Descripcion : Retorna la suma de los importes de las lineas de la reserva
	 * @return
	 * @author  Nicolas Sarlabos, Nicolas Garcia issue #821
	 * Fecha : 19/09/2011,01/11/2011
	 */
	public BigDecimal getTotalLines(){
		
		String sql = "";
		BigDecimal total = Env.ZERO;
		
		sql =  " SELECT coalesce(((res.qtyentered-coalesce(res.uy_bonificaregla,0))),0) as qty, " +
			   " ord.priceentered as price " +	
			   " FROM uy_reservapedidoline res " +
			   " INNER JOIN c_orderline ord ON res.c_orderline_id = ord.c_orderline_id " +
			   " WHERE res.uy_reservapedidohdr_id="	+ this.get_ID();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				BigDecimal monto = rs.getBigDecimal("qty").multiply(rs.getBigDecimal("price"));
				monto = monto.setScale(2, RoundingMode.HALF_UP);
				total = total.add(monto);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
				
		return total;	
		
	}

	/**
	 * Obtiene y retorna modelo de factura asociada a esta reserva.
	 * En caso de no tener factura asociada se retorna null.
	 * OpenUp Ltda. Issue # 964
	 * @author Gabriel Vila - 22/02/2012
	 * @see http://1.1.20.123:86/eventum/view.php?id=964
	 * @return MInvoice. Factura asociada o null.
	 */
	public MInvoice getInvoice(){
		
		String sql = "";
		MInvoice invoice = null;
		
		sql =  " SELECT c_invoice_id " +
			   " FROM c_invoice " +
			   " WHERE uy_reservapedidohdr_id="	+ this.get_ID() + 
			   " AND docstatus='CO'";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				invoice = new MInvoice(getCtx(), rs.getInt(1), get_TrxName());
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
				
		return invoice;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
}
