/**
 * MCreditFilter.java
 * 08/02/2011
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MCreditFilter
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 08/02/2011
 */
public class MCreditFilter extends X_UY_Credit_Filter implements DocAction {

	private static final long serialVersionUID = 7482085039900613904L;
	
	private String processMsg = null;

	//private final String LINEA_APROBADA = "TOTAL";
	//private final String LINEA_PARCIAL = "PARCIAL";
	private final String LINEA_NO_APROBADA = "NINGUNO";

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_Credit_Filter_ID
	 * @param trxName
	 */
	public MCreditFilter(Properties ctx, int UY_Credit_Filter_ID, String trxName) {
		super(ctx, UY_Credit_Filter_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCreditFilter(Properties ctx, ResultSet rs, String trxName) {
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
			return this.loadOrdersNotApproved();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete))
			return this.applyApprovement();
		else
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
	 * Descripcion : Carga informacion de Pedidos de Venta no aprobados crediticiamente.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 08/02/2011
	 */
	private boolean loadOrdersNotApproved() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;
		
		try
		{
			log.info("Iniciando carga de pedidos de venta no aprobados crediticiamente...");

			// Obtengo numero de transaccion
			int idDocNroTrans = Integer.parseInt(MSysConfig.getValue("UY_DOCID_NROTRANSACCION", Env.getAD_Client_ID(Env.getCtx())));
			int nroTrans = Integer.parseInt(MSequence.getDocumentNo(idDocNroTrans, null, false, null));
		
			// Condicion de Filtros
			String whereFiltros = "";

			if (this.getuy_dateordered_from()!=null) whereFiltros += " AND hdr.dateordered >='" + this.getuy_dateordered_from() + "'";
			if (this.getuy_dateordered_to()!=null) whereFiltros += " AND hdr.dateordered <='" + this.getuy_dateordered_to() + "'";
			if (this.getC_BPartner_ID()>0) whereFiltros += " AND hdr.c_bpartner_id =" + this.getC_BPartner_ID();
			if (this.getUY_CanalVentas_ID()>0) whereFiltros += " AND cli.uy_canalventas_id =" + this.getUY_CanalVentas_ID();
			if (this.getuy_datepromised_from()!=null) whereFiltros += " AND hdr.datepromised >='" + this.getuy_datepromised_from() + "'";
			if (this.getuy_datepromised_to()!=null) whereFiltros += " AND hdr.datepromised <='" + this.getuy_datepromised_to() + "'";
			if (this.getC_Order_ID()>0) whereFiltros += " AND hdr.c_order_id =" + this.getC_Order_ID();
			if (this.getAD_User_ID()>0) whereFiltros += " AND hdr.salesrep_id =" + this.getAD_User_ID();
			
			sql = " SELECT hdr.c_bpartner_id, hdr.c_bpartner_location_id, hdr.dateordered, hdr.c_order_id, " +
				  " hdr.datepromised, hdr.grandtotal, hdr.SalesRep_ID " +
				  " FROM c_order hdr " +
				  " INNER JOIN c_bpartner cli ON hdr.c_bpartner_id = cli.c_bpartner_id " +
				  " WHERE hdr.ad_client_id = ?" +
				  " AND hdr.isactive='Y'" +
				  " AND hdr.docstatus IN('DR','IN','IP') " +
				  " AND hdr.issotrx='Y'" +
				  " AND uy_credit_approved='N'" +
				  " AND hdr.grandtotal > 0 " +
				  whereFiltros +
				  " ORDER BY hdr.c_bpartner_id ";
	
			log.info(sql);
			
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, this.getAD_Client_ID());
			rs = pstmt.executeQuery();

			int cBPartnerID = -1, cBPartnerIDAux = -1;
			MCreditPartner credPartner = null;
		
			while (rs.next())
			{	
				cBPartnerIDAux = rs.getInt("C_BPartner_ID");
				
				// Corte de control por cliente
				if (cBPartnerIDAux != cBPartnerID){
					// Si tengo informacion previa en modelo de credito de cliente, hago el save
					if (credPartner != null){
						credPartner.saveEx(get_TrxName());
						credPartner = null;
					}
					cBPartnerID = cBPartnerIDAux;
					
					MBPartner bp = new MBPartner(getCtx(), cBPartnerID, get_TrxName());
					
					credPartner = new MCreditPartner(getCtx(), 0, get_TrxName());
					credPartner.setUY_Credit_Filter_ID(this.getUY_Credit_Filter_ID());
					credPartner.setC_BPartner_ID(cBPartnerID);
					credPartner.setso_creditlimt(bp.getSO_CreditLimit());
					credPartner.setIsApproved(false);
					credPartner.setuy_approval_amt(Env.ZERO);
					credPartner.setuy_creditlimt_doc(bp.getuy_creditlimit_doc());
					credPartner.setuy_ctacte_saldo(bp.getuy_credit_openamt());
					credPartner.setuy_ctadoc_saldo(bp.getuy_credit_openamt_doc());
					credPartner.setuy_nrotrx(nroTrans);
					credPartner.setuy_ctacte_vencido(bp.getDueAmt());
					credPartner.setuy_credit_status(LINEA_NO_APROBADA);
					// Save para que me genere el ID
					credPartner.saveEx();
					
					//OpenUp. Nicolas Sarlabos. 30/08/2013. #1206. Se reemplaza ad_sequence_id quemado por el obtenido en consulta sql.
					sql = "select ad_sequence_id from ad_sequence where lower(name) like 'uy_credit_openamt'";
					int sequenceID = DB.getSQLValueEx(get_TrxName(), sql);
					
					// Inserto detalle de saldo abierto  
					String insert = "INSERT INTO UY_Credit_OpenAmt " +
							"(uy_credit_openamt_id, uy_credit_partner_id, ad_client_id, ad_org_id, isactive, " +
							"created, createdby, updated, updatedby, " +
							"c_bpartner_id, c_doctype_id, c_invoice_id, dateinvoiced, " +
							"duedate, uy_nrotrx, processed, openamt) ";

					insert += " SELECT nextid(" + sequenceID + ",'N')," + credPartner.getUY_Credit_Partner_ID() + "," + credPartner.getAD_Client_ID() + "," + 
								 credPartner.getAD_Org_ID() + ",'Y', now()," + credPartner.getCreatedBy() + ", now()," + credPartner.getUpdatedBy() + ", " +
							     " c_bpartner_id, alloc_amtopen_noncero.c_doctype_id, record_id, docdate, duedate, 0, 'Y', " +
							     " case when coalesce(doc.allocationbehaviour,'N')='PAY' " + 
							     " then (amtopen * -1) else amtopen end as amtopen " +
							     " from alloc_amtopen_noncero " +
							     " inner join c_doctype doc on alloc_amtopen_noncero.c_doctype_id = doc.c_doctype_id " +
							     " where c_bpartner_id = " + cBPartnerID + 
							     " order by docdate ";
							     
					DB.executeUpdate(insert, get_TrxName());
					//Fin OpenUp. #1206
				}
			
				// Acumulo saldo a aprobar para este cliente
				credPartner.setuy_approval_amt(credPartner.getuy_approval_amt().add(rs.getBigDecimal("GrandTotal")));
				
				// Instancio y save de modelo de pedido a aprobar
				MCreditOrder creditOrd = new MCreditOrder(getCtx(), 0, get_TrxName());
				creditOrd.setUY_Credit_Partner_ID(credPartner.getUY_Credit_Partner_ID());
				creditOrd.setC_BPartner_ID(cBPartnerID);
				creditOrd.setC_BPartner_Location_ID(rs.getInt("C_BPartner_Location_ID"));
				creditOrd.setC_Order_ID(rs.getInt("C_Order_ID"));
				creditOrd.setDateOrdered(rs.getTimestamp("DateOrdered"));
				creditOrd.setDatePromised(rs.getTimestamp("DatePromised"));
				creditOrd.setAD_User_ID(rs.getInt("SalesRep_ID"));
				creditOrd.setTotalLines(rs.getBigDecimal("GrandTotal"));
				creditOrd.setuy_nrotrx(nroTrans);
				creditOrd.setIsApproved(false);
				creditOrd.saveEx(get_TrxName());
			}
			
			// Save de ultimo modelo de credito de cliente
			if (credPartner != null){
				credPartner.saveEx(get_TrxName());
			}
			
			rs.close();
			pstmt.close();

			// Actualizo atributos de filtros
			this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
			this.setDocAction(DocumentEngine.ACTION_Complete);
			this.set_ValueOfColumn("uy_nrotrx", nroTrans);
			this.saveEx();
			
			log.info("Fin carga Pedidos de Venta no Aprobados.");
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			result = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return result;
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Aplicacion final del resultado de la Aprobacion Crediticia Manual.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 09/02/2011
	 */
	private boolean applyApprovement() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean result = true;

		try{
			// Obtengo lineas del detalle de la reserva tal que tenga cantidad reservada mayor a cero
			sql = "SELECT ord.* " +
				  " FROM UY_Credit_Order ord " +
				  " INNER JOIN UY_Credit_Partner part ON ord.UY_Credit_Partner_ID = part.UY_Credit_Partner_ID " +
				  " WHERE part.UY_Credit_Filter_ID =? " +
				  " AND ord.IsApproved = 'Y'";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.getUY_Credit_Filter_ID());
			rs = pstmt.executeQuery ();

			while (rs.next()){
				int cOrderID = rs.getInt("C_Order_ID");
				MOrder orderHdr = new MOrder(getCtx(), cOrderID, get_TrxName());
				
				MUser user = new MUser(getCtx(), getAD_User_ID(), get_TrxName());
				
				orderHdr.setIsApproved(true);
				orderHdr.setIsCreditApproved(true);
				orderHdr.setuy_credit_approved(true);
				orderHdr.setuy_credit_message("Aprobado Manualmente por : " + user.getName() + " (ID Aprob:" + this.getUY_Credit_Filter_ID() + ")");
				orderHdr.setDocStatus(STATUS_Drafted);
				orderHdr.setDocAction(ACTION_Complete);
				orderHdr.saveEx(get_TrxName());
				
				// Completo Pedido
				if (orderHdr.processIt(DocAction.ACTION_Complete))
					orderHdr.saveEx(get_TrxName());
				else{
					result = false;
					throw new Exception("No se pudo completar Pedido : " + orderHdr.getDocumentNo() + ", " + orderHdr.getProcessMsg());
				}
			}
			
			// Marco proceso como completado
			this.setDocAction(DocumentEngine.ACTION_Close);
			this.setDocStatus(DocumentEngine.STATUS_Completed);
			this.setProcessed(true);
			this.saveEx(get_TrxName());
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			this.processMsg = e.getMessage();
			result = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return result;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

}
