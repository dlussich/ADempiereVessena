package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.  issue #735
 * MUpdOrderFilter
 * Descripcion :
 * @author Nicol�s Sarlab�s
 * Fecha : 01/07/2011
 */
public class MUpdInvoiceFilter extends X_UY_NoteCreditApprov_Filter implements DocAction {
	
private String processMsg = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 986866045115613632L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_UpdOrder_Filter_ID
	 * @param trxName
	 */
	public MUpdInvoiceFilter(Properties ctx, int UY_NoteCreditApprov_Filter_ID,
			String trxName) {
		super(ctx, UY_NoteCreditApprov_Filter_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUpdInvoiceFilter(Properties ctx, ResultSet rs, String trxName) {
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
			return this.loadInvoices();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete)||action.equalsIgnoreCase(DocumentEngine.ACTION_Approve))// OpenUp issue #880 Nicolas Sarlabos 28/09/2011
			return this.updateInvoices();
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
	 * OpenUp.	issue #735
	 * Descripcion : Obtiene y carga en tabla del proceso, los documentos del tipo nota de credito o factura
	 * por diferencia de precio y apoyo, seg�n la elecci�n del usuario.
	 * @author  Nicolas Sarlabos 
	 * Fecha : 01/07/2011
	 */
	private boolean loadInvoices(){
		String sql = "", insert = "";
		boolean result = true;
		int idInv = 0;
		
		try
		{
						
			// Obtengo numero de transaccion
			int idDocNroTrans = Integer.parseInt(MSysConfig.getValue("UY_DOCID_NROTRANSACCION", Env.getAD_Client_ID(Env.getCtx())));
			int nroTrans = Integer.parseInt(MSequence.getDocumentNo(idDocNroTrans, null, false, null));
			
			// Condicion de Filtros
			String whereFiltros = "", whereDocStatus = "";

			if (this.getDateFrom()!=null) whereFiltros += " AND hdr.dateinvoiced >='" + this.getDateFrom() + "'";
			if (this.getDateTo()!=null) whereFiltros += " AND hdr.dateinvoiced <='" + this.getDateTo() + "'";
			if (this.getC_BPartner_ID()>0) whereFiltros += " AND hdr.c_bpartner_id =" + this.getC_BPartner_ID();
			
			//OpenUp issue #880 Nicolas Sarlabos 28/09/2011
			// Filtro de tipo de documento seleccionado por el usuario, si desea procesar nota de credito o factura
			if(this.getuy_doctype().equalsIgnoreCase("NC")){
				String id = "SELECT c_doctype_id FROM c_doctype WHERE docbasetype='ARC' AND docsubtypeso='NP'"; 
				idInv = DB.getSQLValue(get_TrxName(), id);
			}else{
				String id = "SELECT c_doctype_id FROM c_doctype WHERE docbasetype='ARI' AND docsubtypeso='FP'"; 
				idInv = DB.getSQLValue(get_TrxName(), id);
			}
			//fin OpenUp issue #880
			
			// Filtro de docstatus depende de tipo de accion seleccionada por el usuario, si desea aprobar � compleatar notas
			if (this.getuy_docaction_update().equalsIgnoreCase(DocumentEngine.ACTION_Approve))
				whereDocStatus = " AND hdr.docstatus='RQ' ";
	    	if (this.getuy_docaction_update().equalsIgnoreCase(DocumentEngine.ACTION_Complete))
				whereDocStatus = " AND hdr.docstatus='AP' ";
			
	    	
			
			insert = "INSERT INTO UY_NoteCreditApprov_Doc(updated, uy_notecreditapprov_doc_id, uy_notecreditapprov_filter_id, updatedby, created, createdby, isactive, documentno, ad_client_id, ad_org_id, c_invoice_id, dateinvoiced, c_bpartner_id, c_order_id, totallines, grandtotal, description, uy_procesar)" ;
			sql    = " SELECT hdr.updated, nextid(1000937, 'N'), "+ this.get_ID()+  ", hdr.updatedby, hdr.created, hdr.createdby, hdr.isactive, hdr.documentno, hdr.ad_client_id, hdr.ad_org_id, hdr.c_invoice_id,hdr.dateinvoiced,hdr.c_bpartner_id,hdr.c_order_id,hdr.totallines,hdr.grandtotal,description,'Y'" +  
				     " FROM c_invoice hdr " +
				     " WHERE hdr.c_doctypetarget_id =" + idInv + whereFiltros + whereDocStatus;

			log.info(sql);
					
			if(DB.executeUpdate(insert + sql, null)>0){
				
				// Actualizo atributos de filtros solamente si la consulta retorn� registros
				// OpenUp issue #880 Nicolas Sarlabos 28/09/2011
				if (this.getuy_docaction_update().equalsIgnoreCase(DocumentEngine.ACTION_Approve)){
					this.setDocAction(DocumentEngine.ACTION_Approve);
					this.setDocStatus(DocumentEngine.STATUS_Requested);
				}else{
					   this.setDocAction(DocumentEngine.ACTION_Complete);
					   this.setDocStatus(DocumentEngine.STATUS_Approved);
				}
				
				this.set_ValueOfColumn("uy_nrotrx", nroTrans);
				this.saveEx();
			}
			else{
				
				throw new Exception("No hay documentos para aprobar/completar");
			}
		
			
		}
		
		catch (Exception e) {
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			return false;
		}
		
		return result;
		
	}

	
	/**
	 * OpenUp.	issue #735
	 * Descripcion : Aprueba o Completa los documentos seleccionados por el usuario
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 01/07/2011
	 */
	private boolean updateInvoices() {
				
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean result = true;
		
		try{
			
			sql = "SELECT c_invoice_id " +
				  "FROM UY_NoteCreditApprov_Doc WHERE uy_procesar='Y' AND UY_NoteCreditApprov_filter_ID = " + this.get_ID();
				  
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			//recorre los registros de la tabla
			while (rs.next()){
				
				MInvoice inv = new MInvoice(getCtx(),rs.getInt("c_invoice_id"),null);
				
				//si la acci�n elegida por el usuario es aprobar...
				if(this.getuy_docaction_update().equalsIgnoreCase(DocumentEngine.ACTION_Approve)){
					//OpenUp issue #880 Nicolas Sarlabos 28/09/2011, se modifican los mensajes de error para contemplar los dos casos
					if(!inv.processIt(DocumentEngine.ACTION_Approve)){
						if(this.getuy_doctype().equalsIgnoreCase("NC")){
								throw new Exception("Imposible aprobar la nota de credito: "+inv.getDocumentNo());
						}else{
								throw new Exception("Imposible aprobar la factura: "+inv.getDocumentNo());
						}
					}
					inv.saveEx();
					
				//si la acci�n elegida por el usuario es completar...
				}else if(this.getuy_docaction_update().equalsIgnoreCase(DocumentEngine.ACTION_Complete)){
					//OpenUp issue #880 Nicolas Sarlabos 28/09/2011, se modifican los mensajes de error para contemplar los dos casos
					if(!inv.processIt(DocumentEngine.ACTION_Complete)){
						if(this.getuy_doctype().equalsIgnoreCase("NC")){
								throw new Exception("Imposible Completar la nota de credito: "+inv.getDocumentNo());
						}else{
								throw new Exception("Imposible Completar la factura: "+inv.getDocumentNo());
						}
					}
								
					inv.saveEx();
					//OpenUp issue #880 Nicolas Sarlabos 28/09/2011,se imprime el documento
					Env.getCtx().put("parmInvoice", inv.getC_Invoice_ID());
					ReportCtl.startDocumentPrint(ReportEngine.INVOICE, inv.getC_Invoice_ID(), true);
					
				}
							
			}
			
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
		
		if(result){
				
			this.setDocAction(DocumentEngine.ACTION_None);
			// OpenUp issue #880 Nicolas Sarlabos 28/09/2011
			if (this.getuy_docaction_update().equalsIgnoreCase(DocumentEngine.ACTION_Approve)){
				this.setDocStatus(DocumentEngine.STATUS_Approved);
			}else this.setDocStatus(DocumentEngine.STATUS_Completed);
			this.setProcessed(true);
			this.saveEx();
		}
				
		return result;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

}
