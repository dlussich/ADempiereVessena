/**
 * MAsignaTransporteLine.java
 * 02/12/2010
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * OpenUp.
 * MAsignaTransporteLine
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 02/12/2010
 */
public class MAsignaTransporteLine extends X_UY_AsignaTransporteLine implements
		DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4540341167216422953L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_AsignaTransporteLine_ID
	 * @param trxName
	 */
	public MAsignaTransporteLine(Properties ctx,
			int UY_AsignaTransporteLine_ID, String trxName) {
		super(ctx, UY_AsignaTransporteLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAsignaTransporteLine(Properties ctx, ResultSet rs, String trxName) {
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
	 * @see org.compiere.process.DocAction#getDocAction()
	 */
	@Override
	public String getDocAction() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocStatus()
	 */
	@Override
	public String getDocStatus() {
		// TODO Auto-generated method stub
		return null;
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
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	protected boolean beforeDelete() {
		
		// Valido que la linea de la reserva que se quiere eliminar no este facturada
		// y que esta asignacion no este completa.
		
		MAsignaTransporteHdr atrHdr = new MAsignaTransporteHdr(getCtx(), this.getUY_AsignaTransporteHdr_ID(), get_TrxName());
		if (atrHdr.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
			throw new AdempiereException("No se pueden eliminar lineas de esta Asignacion de Transporte " +
										" ya que la misma esta en Estado de Documento = Completada.");
		}
		
		MReservaPedidoHdr reserva = new MReservaPedidoHdr(getCtx(), this.getUY_ReservaPedidoHdr_ID(), get_TrxName());
		if (reserva.get_ID() > 0){
			MInvoice invoice = reserva.getInvoice();
			if (invoice != null){
				throw new AdempiereException("No se puede eliminar linea asociada a la Reserva : " + reserva.getDocumentNo() +
						" ya que la misma esta facturada con el numero : " + invoice.getDocumentNo());
			}
		}
		
		return true;
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
	 * @see org.compiere.process.DocAction#setDocStatus(java.lang.String)
	 */
	@Override
	public void setDocStatus(String newStatus) {
		// TODO Auto-generated method stub

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

	public static boolean contieneReservaPedido(int mReservaPedidoHdrID, String trxName){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean value = false;
		
		try{
			sql ="SELECT UY_ReservaPedidoHdr_ID " + 
 		  	" FROM " + X_UY_AsignaTransporteLine.Table_Name + 
		  	" WHERE UY_ReservaPedidoHdr_ID =?"; 
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, mReservaPedidoHdrID);
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = true;
			}
		}
		catch (Exception e)
		{
			value = true;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;		
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
}
