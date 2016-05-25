package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

public class MCancelReservationFilter extends X_UY_CancelReservationFilter
		implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String processMsg = "";

	public MCancelReservationFilter(Properties ctx,
			int UY_CancelReservationFilter_ID, String trxName) {
		super(ctx, UY_CancelReservationFilter_ID, trxName);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return null;
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
	public boolean processIt(String action) throws Exception {
		// Proceso segun accion
		this.processMsg = "";
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare))
			return this.loadReserves();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete))
			return this.cancelReserves();
		else
			return true;
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

	/**
	 * 
	 * OpenUp. issue #908 
	 * Descripcion : Obtiene y carga en tabla del proceso las reservas, segun los filtros seleccionados.
	 * @return
	 * @author Nicolas Sarlabos 
	 * Fecha : 06/12/2011
	 */

	private boolean loadReserves() {
		String sql = "", insert = "";
		
		try {
			
			// Condicion de Filtros
			String whereFiltros = "";

			if (this.getuy_datereserved_from() != null)whereFiltros += " AND hdr.datereserved >='" + this.getuy_datereserved_from() + "'";
			if (this.getuy_datereserved_to() != null)whereFiltros += " AND hdr.datereserved <='" + this.getuy_datereserved_to() + "'";
			if (this.getC_BPartner_ID() > 0)whereFiltros += " AND hdr.c_bpartner_id =" + this.getC_BPartner_ID();
			if (this.getSalesRep_ID() > 0)whereFiltros += " AND bp.salesrep_id =" + this.getSalesRep_ID();
			if (this.getUY_ZonaReparto_ID() > 0)whereFiltros += " AND bp.uy_zonareparto_id =" + this.getUY_ZonaReparto_ID();
			
			int sequenceID = DB.getSQLValue(null, "SELECT ad_sequence_id FROM ad_sequence WHERE name ='UY_CancelReservationDetail'");

			insert = "INSERT INTO UY_CancelReservationDetail(updated, uy_cancelreservationdetail_id, uy_cancelreservationfilter_id, updatedby, created, createdby, isactive, ad_client_id, ad_org_id, uy_reservapedidohdr_id, datereserved, c_bpartner_id, c_order_id, name, uy_zonareparto_id, salesrep_id, uy_procesar)";
			//OpenUp Nicolas Sarlabos #908 24/02/2012,se corrige problema con la zona de reparto
			sql = " SELECT hdr.updated, nextid(" + sequenceID + ", 'N'), "
					+ this.get_ID()
					+ ", hdr.updatedby, hdr.created, hdr.createdby, hdr.isactive, hdr.ad_client_id, hdr.ad_org_id, hdr.uy_reservapedidohdr_id,hdr.datereserved,bp.c_bpartner_id,ord.c_order_id,coalesce(bp.name,bp.name2) as name,loc.uy_zonareparto_id,bp.salesrep_id,'Y'"
					+ " FROM uy_reservapedidohdr hdr"
					+ " INNER JOIN c_bpartner bp ON hdr.c_bpartner_id=bp.c_bpartner_id"
					+ " INNER JOIN c_order ord ON hdr.c_order_id=ord.c_order_id"
					+ " INNER JOIN c_bpartner_location loc on ord.c_bpartner_location_id=loc.c_bpartner_location_id"
					+ " WHERE hdr.docstatus='CO' AND ord.docstatus='CO' AND hdr.uy_reservapedidohdr_id not in(select uy_reservapedidohdr_id from uy_asignatransporteline)"
					+ whereFiltros;
			//fin OpenUp Nicolas Sarlabos #908 24/02/2012

			log.info(sql);

			// Si hay reservas...
			if (DB.executeUpdateEx(insert + sql,get_TrxName()) > 0) {

				updateTable(get_TrxName()); //OpenUp Nicolas Sarlabos #908 24/02/2012,se insertaran los importes y bultos de las reservas

				// Actualizo atributos de filtros
				this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
				this.setDocAction(DocumentEngine.ACTION_Complete);
				this.saveEx(get_TrxName());
			} else {
					throw new Exception("No hay reservas para cancelar");
			}

		}

		catch (Exception e) {
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			throw new AdempiereException(processMsg);
			
		}

		return true;

	}

	/**
	 * Actualiza tabla insertando los valores calculados para importe y cantidad de bultos de cada reserva 
	* OpenUp Ltda. Issue #908
	* @author Nicolas Sarlabos - 24/02/2012
	* @see
	* @throws Exception
	 */

	private void updateTable(String trxName) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = "SELECT uy_reservapedidohdr_id,uy_cancelreservationdetail_id FROM UY_CancelReservationDetail WHERE UY_CancelReservationFilter_ID = "
					+ this.get_ID();

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				MReservaPedidoHdr res = new MReservaPedidoHdr(getCtx(), rs.getInt("uy_reservapedidohdr_id"), get_TrxName());

				if (res != null) {

					BigDecimal amount = res.getTotalLines();
					BigDecimal bultos = res.getCantidadBultos();

					sql = "UPDATE UY_CancelReservationDetail SET amount=" + amount + ",bultos=" + bultos + " WHERE uy_cancelreservationdetail_id="
							+ rs.getInt("uy_cancelreservationdetail_id");

					DB.executeUpdateEx(sql, get_TrxName());

				}

			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	/**
	 * 
	 * OpenUp. issue #908 
	 * Descripcion : Cancela las reservas seleccionadas
	 * @return
	 * @author Nicolas Sarlabos 
	 * Fecha : 06/12/2011
	 */

	private boolean cancelReserves() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
			
		try {

			sql = "SELECT uy_reservapedidohdr_id" +
				  " FROM UY_CancelReservationDetail" +
				  " WHERE uy_procesar='Y' AND UY_CancelReservationFilter_ID = " + this.get_ID();

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			// recorre los registros de la tabla
			while (rs.next()) {

				try {
					MReservaPedidoHdr res = new MReservaPedidoHdr(getCtx(), rs.getInt("uy_reservapedidohdr_id"), get_TrxName());
					// Si el estado de la reserva es CO, entonces se procede a anularla
					if (res.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)) {
						if (!res.processIt(DocumentEngine.ACTION_Void))
							this.processMsg += "Imposible anular la reserva: " + res.getDocumentNo() + " \n";
						else
							res.saveEx(get_TrxName());
					}

				} catch (Exception e) {
					log.log(Level.SEVERE, sql, e);
					throw e;
				}
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if(this.processMsg!=""){
			this.processMsg += "\n" + "NINGUNA RESERVA HA SIDO CANCELADA";
			throw new AdempiereException(processMsg);
		}
		
		this.setDocAction(DocumentEngine.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessed(true);
		this.saveEx(get_TrxName());

		return true;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

}
