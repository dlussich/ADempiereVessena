/**
 * MAsignaTransporteHdr.java
 * 02/12/2010
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.InfoDocumentoBean;

/**
 * OpenUp.
 * MAsignaTransporteHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 02/12/2010
 */
public class MAsignaTransporteHdr extends X_UY_AsignaTransporteHdr implements
		DocAction {

	private String processMsg = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4677969909477296960L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_AsignaTransporteHdr_ID
	 * @param trxName
	 */
	public MAsignaTransporteHdr(Properties ctx, int UY_AsignaTransporteHdr_ID,
			String trxName) {
		super(ctx, UY_AsignaTransporteHdr_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAsignaTransporteHdr(Properties ctx, ResultSet rs, String trxName) {
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
			return this.procesarAsignacion();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete))
			return this.completarAsignacion();
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
	 * Descripcion : Setea estado a Procesado.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 02/01/2011
	 */
	private boolean procesarAsignacion() {
		this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
		this.setDocAction(DocumentEngine.ACTION_Complete);
		//this.saveEx();
		return true;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Setea estado a Completado
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 02/01/2011
	 */
	private boolean completarAsignacion() {
	
		// Valido que no se complete un atr que no tiene facturas asociadas
		MAsignaTransporteFact[] facts;
		try {
		
			facts = this.getFacturas();
	
		} catch (Exception e) {
			this.processMsg = e.getMessage();
			return false;
		}
		
		if (facts.length > 0){
			this.setDocAction(DocumentEngine.ACTION_None);
			this.setDocStatus(DocumentEngine.STATUS_Completed);
			this.setProcessed(true);
			//this.saveEx();			
		}
		
		return true;

	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna facturas asignadas a este transporte.
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 14/02/2011
	 */
	public MAsignaTransporteFact[] getFacturas() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MAsignaTransporteFact> list = new ArrayList<MAsignaTransporteFact>();
		
		try{
			sql ="SELECT uy_asignatransportefact_id " + 
 		  	" FROM " + X_UY_AsignaTransporteFact.Table_Name + 
		  	" WHERE uy_asignatransportehdr_id =?"; 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_AsignaTransporteHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MAsignaTransporteFact value = new MAsignaTransporteFact(Env.getCtx(), rs.getInt(1), null);
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
		
		return list.toArray(new MAsignaTransporteFact[list.size()]);		
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna informacion de otros documentos asociados a esta
	 * asignacion de transporte.
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 15/02/2011
	 */
	public InfoDocumentoBean[] getOtrosDocumentos() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<InfoDocumentoBean> list = new ArrayList<InfoDocumentoBean>();
		
		try{
			// Recibos de Cobranza, Devoluciones directas
			sql =" SELECT c_doctype_id, documentno, datetrx, c_bpartner_id " + 
 		  	" FROM C_Payment " + 
		  	" WHERE uy_asignatransportehdr_id =? " +
		  	" AND DocStatus IN('CO','CL')" +
		  	" UNION " +
		  	" SELECT c_doctype_id, documentno, dateacct as datetrx, c_bpartner_id " + 
 		  	" FROM M_InOut " + 
		  	" WHERE uy_asignatransportehdr_id =? " +
		  	" AND C_DocType_ID = 1000059 " +
		  	" AND DocStatus IN('CO','CL')";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_AsignaTransporteHdr_ID());
			pstmt.setInt(2, this.getUY_AsignaTransporteHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				InfoDocumentoBean value = new InfoDocumentoBean();
				value.C_BPartner_ID = rs.getInt("c_bpartner_id");
				value.C_DocType_ID = rs.getInt("c_doctype_id");
				value.DateTrx = rs.getTimestamp("datetrx");
				value.DocumentNo = rs.getString("documentno");
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
		
		return list.toArray(new InfoDocumentoBean[list.size()]);		
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
}
