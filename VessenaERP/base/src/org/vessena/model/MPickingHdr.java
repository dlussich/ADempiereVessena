/**
 * MPickingHdr.java
 * 03/12/2010
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MProduct;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MPickingHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 03/12/2010
 */
public class MPickingHdr extends X_UY_PickingHdr implements DocAction {

	private String processMsg = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8633373040756818325L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_PickingHdr_ID
	 * @param trxName
	 */
	public MPickingHdr(Properties ctx, int UY_PickingHdr_ID, String trxName) {
		super(ctx, UY_PickingHdr_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPickingHdr(Properties ctx, ResultSet rs, String trxName) {
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		// Proceso segun accion
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare))
			return this.procesarModelo();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete))
			return this.completarModelo();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Void))
			return this.anularModelo();
		else
			return true;
	}

	/**
	 * OpenUp. Gabriel Vila. 14/07/2011. Issue #792.
	 * Al anular un picking hago las verificaciones correspondientes.
	 * @return
	 */
	private boolean anularModelo() {
		
		// Si el ATR asociado a este picking esta facturado, no puedo anular el
		// picking.
		MAsignaTransporteHdr atr = new MAsignaTransporteHdr(getCtx(), this.getUY_AsignaTransporteHdr_ID(), get_TrxName());
		MAsignaTransporteFact[] facts;
		try {
			facts = atr.getFacturas();
			if (facts.length > 0){
				this.processMsg = "No se puede ANULAR Picking ya que la Asignacion de Transporte tiene Facturas emitidas.";
				return false;
			}
		} catch (Exception e) {
			this.processMsg = e.getMessage();
			return false;
		}
		
		this.setDocStatus(DocumentEngine.STATUS_Voided);
		this.setProcessed(true);
		this.setDocAction(DocumentEngine.ACTION_None);
		this.saveEx();
		return true;
	}


	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;

		// Si es una modificacion de un campo distinto al numero de asignacion
		// de transporte, salgo sin hacer nada.
		//&& (!is_ValueChanged(X_UY_AsignaTransporteHdr.COLUMNNAME_UY_AsignaTransporteHdr_ID)))
		if (!newRecord)  
			return true;

		
		// OpenUp. Gabriel Vila. 14/07/2011. Issue #792.
		// Cargo lineas de picking con informacion asociada al atr seleccionado. (quite el callout y lo puse en el modelo).
		try {
			this.loadPickingLines();
		} catch (Exception e) {
			return false;
		}
		return true;
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
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Setea estado a Procesado.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 02/01/2011
	 */
	private boolean procesarModelo() {
		this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
		this.setDocAction(DocumentEngine.ACTION_Complete);
		this.saveEx();
		return true;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Setea estado a Completado
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 02/01/2011
	 * @throws Exception 
	 */
	private boolean completarModelo(){

		// Al completar el Picking debo verificar anulaciones parciales o totales
		// de reservas. Esto se hace en la tan de detalle, modificando la cantidad
		// reservada. Valido aca que la cantidad ingresada no sea superior a la 
		// cantidad original.
		
		boolean result = true;
		
		try{
			MPickingLineDetail[] lineDetails = this.getLinesDetail();  

			// Para cada linea de detalle de este picking
			for (int i=0; i < lineDetails.length; i++){
				MPickingLineDetail detail = lineDetails[i];
				MProduct prod = new MProduct(getCtx(), detail.getM_Product_ID(), null);
				MReservaPedidoHdr resHdr = new MReservaPedidoHdr(getCtx(), detail.getUY_ReservaPedidoHdr_ID(), null);
				
				if (detail.getQtyEntered().compareTo(detail.getuy_qtyentered_original())>0){
					throw new Exception("Cantidad reservada para producto supera la cantidad original.\n" +
										" Reserva : " + resHdr.getDocumentNo() + "\n" + 
							            " Producto : " + prod.getValue() + " - " + prod.getName() + "\n");
					
				}

				// Me aseguro de tener la unidad de venta del producto actualizada en esta linea de reserva
				// porque a veces la cambian y esto genera problema
				MReservaPedidoLine resLine = new MReservaPedidoLine(getCtx(), detail.getUY_ReservaPedidoLine_ID(), get_TrxName());
				if (resLine.getC_UOM_ID() != 100){
					resLine.setC_UOM_ID(prod.getC_UOM_To_ID());
					resLine.saveEx(get_TrxName());
				}
				
				// Si la cantidad nueva es menor a la cantidad original
				if (detail.getQtyEntered().compareTo(detail.getuy_qtyentered_original())<0){
					// Anulo cantidad reservada de esta reserva-producto segun diferencia entre cantidad original y nueva cantidad confirmada en 
					// este picking.
					BigDecimal difCantidadReserva = detail.getuy_qtyentered_original().subtract(detail.getQtyEntered());
					
					// OpenUp. Gabriel Vila. 22/07/2011. Issue #744.
					// Le paso mas parametros al metodo que anula la cantidad reservada por una diferencia
					resLine.anularCantidadReservada(difCantidadReserva, this, this.getDateTrx());
					// Fin Issue #744						
				}
				
			}

			this.setDocAction(DocumentEngine.ACTION_None);
			this.setProcessed(true);
			this.setDocStatus(DocumentEngine.STATUS_Completed);
			this.saveEx();
			
		}
		catch (Exception e){
			result = false;
			log.log(Level.SEVERE, "Error al completar Picking.", e);
			this.processMsg = e.getMessage();
		}
		return result;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna lineas de detalle de este picking.
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 23/02/2011
	 */
	public MPickingLineDetail[] getLinesDetail() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MPickingLineDetail> list = new ArrayList<MPickingLineDetail>();
		
		try{
			sql ="SELECT uy_pickinglinedetail_id " + 
 		  	" FROM " + X_UY_PickingLineDetail.Table_Name + 
		  	" WHERE uy_pickingline_id IN " +
		  	" (SELECT uy_pickingline_id FROM uy_pickingline " +
		  	" WHERE uy_pickinghdr_id = ? )"; 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_PickingHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MPickingLineDetail value = new MPickingLineDetail(Env.getCtx(), rs.getInt(1), null);
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
		
		return list.toArray(new MPickingLineDetail[list.size()]);		
	}

	/**
	 * OpenUp. Gabriel Vila. 14/07/2011. Issue #792.
	 * Cargo lineas de picking a partir de informacion asociada al ATR seleccionado.
	 * @throws Exception Nicolas Garcia #821 16/11/2011 se modifica metodo.
	 */
	private void loadPickingLines() throws Exception{
		
		// Obtengo informacion de ATR
		MAsignaTransporteHdr atr = new MAsignaTransporteHdr(getCtx(), this.getUY_AsignaTransporteHdr_ID(), get_TrxName());
		if (atr.get_ID() <= 0) return;
		
		// Obtengo lineas para picking
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="";
		try
		{
			
			// Cargo sumarizacion de cantidades por producto-uom
			// OpenUp. Nicolas Garcia. 01/11/2011. Issue #821.
			//Se agrego a la cantidad a pickiar la bonificacion simple
			//Fin Issue #821
			sql = " SELECT rl.m_product_id, rl.c_uom_id, SUM(rl.QtyEntered+rl.uy_bonificaregla) as cantidad " +
				 " FROM UY_ReservaPedidoLine rl " +
				 " INNER JOIN UY_AsignaTransporteLine atl ON rl.UY_ReservaPedidoHdr_ID = atl.UY_ReservaPedidoHdr_ID " +
				 " WHERE atl.UY_AsignaTransporteHdr_ID = ?" +
				 " GROUP BY rl.m_product_id, rl.c_uom_id " +
 " HAVING SUM(rl.QtyEntered+rl.uy_bonificaregla)>0";
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.getUY_AsignaTransporteHdr_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				MPickingLine line = new MPickingLine(getCtx(), 0, get_TrxName());

				line.setUY_PickingHdr_ID(this.get_ID());
				line.setM_Product_ID(rs.getInt("m_product_id"));
				line.setC_UOM_ID(rs.getInt("c_uom_id"));
				line.setQtyEntered(rs.getBigDecimal("cantidad"));
				line.saveEx();
			}
			
			DB.close(rs, pstmt);
			rs = null; pstmt = null;

			// Cargo detalle de cantidades por reserva - producto - uom
			sql = " SELECT rl.UY_TieneBonificCruzada,rl.UY_EsBonificCruzada,rl.UY_ReservaPedidoHdr_ID, rl.UY_ReservaPedidoLine_ID, rl.m_product_id, rl.c_uom_id, rl.QtyEntered,(rl.QtyEntered+rl.uy_bonificaregla) as cantidad "
					+ ",rl.uy_bonificaregla,"+
				 " rl.QtyReserved, pickline.uy_pickingline_id " +
				 " FROM UY_ReservaPedidoLine rl " +
				 " INNER JOIN UY_AsignaTransporteLine atl ON rl.UY_ReservaPedidoHdr_ID = atl.UY_ReservaPedidoHdr_ID " +
				 " INNER JOIN uy_pickingline pickline on rl.m_product_id = pickline.m_product_id and rl.c_uom_id = pickline.c_uom_id " +
				 " WHERE atl.UY_AsignaTransporteHdr_ID = ? " +
				 " and pickline.uy_pickinghdr_id = ? " +
				 " order by pickline.uy_pickingline_id";
			
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.getUY_AsignaTransporteHdr_ID());
			pstmt.setInt(2, this.get_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				MPickingLineDetail lineDet = new MPickingLineDetail(getCtx(), 0, get_TrxName());
				lineDet.setM_Product_ID(rs.getInt("m_product_id"));
				lineDet.setC_UOM_ID(rs.getInt("c_uom_id"));
				lineDet.setUY_ReservaPedidoHdr_ID(rs.getInt("UY_ReservaPedidoHdr_ID"));
				lineDet.setUY_ReservaPedidoLine_ID(rs.getInt("UY_ReservaPedidoLine_ID"));
				lineDet.setQtyEntered(rs.getBigDecimal("cantidad"));
				lineDet.setQtyReserved(rs.getBigDecimal("QtyReserved"));
				lineDet.setuy_qtyentered_original(rs.getBigDecimal("cantidad"));
				lineDet.setuy_qtyreserved_original(rs.getBigDecimal("QtyReserved"));
				lineDet.setuy_bonificaregla(rs.getBigDecimal("uy_bonificaregla"));
				lineDet.setUY_PickingLine_ID(rs.getBigDecimal("uy_pickingline_id"));

				boolean tieneBonificCruzada = false;
				boolean esBonificCruzada = false;

				if (rs.getString("UY_TieneBonificCruzada").compareToIgnoreCase("Y") == 0) {
					tieneBonificCruzada = true;
				}
				if (rs.getString("UY_esBonificCruzada").compareToIgnoreCase("Y") == 0) {
					esBonificCruzada = true;
				}

				lineDet.setUY_TieneBonificCruzada(tieneBonificCruzada);
				lineDet.setUY_EsBonificCruzada(esBonificCruzada);

				lineDet.saveEx();
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			this.processMsg = e.getMessage();
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Obtengo informacion de ATR
		MAsignaTransporteHdr atr = new MAsignaTransporteHdr(getCtx(), this.getUY_AsignaTransporteHdr_ID(), get_TrxName());
		if (atr.get_ID() > 0) this.setM_Shipper_ID(atr.getM_Shipper_ID());
		
		return true;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

}
	

