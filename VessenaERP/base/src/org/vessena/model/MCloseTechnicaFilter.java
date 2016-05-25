package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.eevolution.model.MPPOrder;

public class MCloseTechnicaFilter extends X_UY_CloseTechnica_Filter implements DocAction {

	private String processMsg = "";

	private static final long serialVersionUID = 4949388681416592611L;

	public MCloseTechnicaFilter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MCloseTechnicaFilter(Properties ctx, int UY_CloseTechnica_Filter_ID, String trxName) {
		super(ctx, UY_CloseTechnica_Filter_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor Nico
	 */

	@Override
	public boolean processIt(String action) throws Exception {
		// Proceso segun accion
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare)) return this.loadTable();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete)) return this.closeTechnical();
		else
			return true;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Cargo las lineas.
	 * @return Boolean
	 * @author  Nicolas Garcia
	 * Fecha : 08/09/2011
	 */

	private boolean loadTable() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "SELECT pp_order_id,qtydelivered,qtyordered,m_product_id,UY_PacksLiteral,dateordered FROM pp_order " + this.getWhere();

			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			boolean noLinea = true;

			while (rs.next()) {

				MCloseTechnicaLine line = new MCloseTechnicaLine(getCtx(), 0, get_TrxName());

				line.setPP_Order_ID(rs.getInt("pp_order_id"));
				line.setUY_CloseTechnica_Filter_ID(this.get_ID());
				line.setQtyDelivered(rs.getBigDecimal("qtydelivered"));
				line.setQtyOrdered(rs.getBigDecimal("qtyordered"));
				line.setM_Product_ID(rs.getInt("m_product_id"));
				line.setDateOrdered(rs.getTimestamp("dateordered"));
				line.setUY_PacksLiteral(rs.getString("UY_PacksLiteral"));

				line.saveEx();
				noLinea = false;
			}

			if (noLinea) {
				processMsg = "No hay Ordenes para los filtros ingresados";
				return false;
			}

			
			// Actualizo atributos de filtros
			this.setDocStatus(DocumentEngine.ACTION_WaitComplete);
			this.setDocAction(DocumentEngine.ACTION_Complete);

			this.saveEx();

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			return false;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return true;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Se crea la clausula Where para el filtrado.
	 * @return String
	 * @author  Nicolas Garcia
	 * Fecha : 08/09/2011
	 */
	private String getWhere() {

		String salida = "WHERE DocStatus='CL' AND uy_cerroconfirmacion='N'";


		// TODO: parametrizar c_doctype_id

		// if (this.getC_BPartner_ID() > 0) {
		// salida += " AND c_bpartner_id=" + this.getC_BPartner_ID();

		// if (this.getuy_dateordered_from() != null) {
		// salida += " AND dateordered <(date '" + this.getuy_dateordered_from()
		// + "' + interval '1 day')";
		if (this.getM_Product_ID() > 0) salida += " AND m_product_id =" + this.getM_Product_ID();

		if (this.getPP_Order_ID() > 0) salida += " AND pp_order_id =" + this.getPP_Order_ID();

		if (this.getDateFrom() != null) salida += " AND dateordered >='" + this.getDateFrom() + "'";

		if (this.getDateTo() != null) salida += " AND dateordered <(date '" + this.getDateTo() + "' + interval '1 day')";

		return salida;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :
	 * @return Boolean
	 * @author  Nicolas Garcia
	 * Fecha :07/09/2011
	 */
	private boolean closeTechnical() {

		String sql = "SELECT pp_order_id FROM uy_closetechnica_line WHERE uy_procesar='Y' AND uy_closetechnica_filter_id=" + this.get_ID();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MPPOrder ppOrder = new MPPOrder(getCtx(), rs.getInt("pp_order_id"), get_TrxName());
				ppOrder.closeTechnical(null);
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			return false;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// Actualizo atributos de filtros
		this.setDocStatus(DocumentEngine.ACTION_Complete);

		this.setDocAction(DocumentEngine.ACTION_Close);
		this.setProcessed(true);
		this.saveEx();

		return true;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return processMsg;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

}
