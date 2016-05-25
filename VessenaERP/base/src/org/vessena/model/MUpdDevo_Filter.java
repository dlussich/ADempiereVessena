package org.openup.model;


import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MInOut;
import org.compiere.model.MSequence;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

public class MUpdDevo_Filter extends X_UY_UpdDevo_Filter implements DocAction{

	
	public MUpdDevo_Filter(Properties ctx, int UY_UpdDevo_Filter_ID,
			String trxName) {
		super(ctx, UY_UpdDevo_Filter_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUpdDevo_Filter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}


	private String processMsg = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5518444669014523081L;

	/**
	 * 
	 */
	

	

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
		return this.processMsg;
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
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare))
			return this.loadTable();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete))
			return this.updateDevoluciones();
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
	 * OpenUp.	
	 * Descripcion :Carga en la tabla UY_UpdDevo_Lineas
	 * @return Boolean
	 * @author  Nicolas Garcia
	 * Fecha : 02/06/2011
	 */
	
	private boolean loadTable() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try {

			MSequence seq = MSequence.get(getCtx(), "UY_Upddevo_Lines", null);
			
			sql = " INSERT INTO uy_upddevo_lines "+
			"(SELECT nextID(" + seq.getAD_Sequence_ID() + ",'N'),"+this.getAD_Client_ID()+","+this.getAD_Org_ID()+",'Y',NOW(),"+Env.getAD_User_ID(Env.getCtx())+",NOW(),"+Env.getAD_User_ID(Env.getCtx())+
			","+this.getUY_UpdDevo_Filter_ID()+",m_inout_id,c_bpartner_id,C_bpartner_location_id,dateordered,poreference,description,uy_inouttype_id,'N',uy_asignatransportehdr_id " +//Se agrega ATR,(caso issue #379 re-abierto)
			"FROM m_inout "+this.getWhere()+")";

			log.info(sql);
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.executeUpdate();
		
			log.info("Fin carga Pedidos de Venta para Actualizacion.");

		} catch (SQLException e) {
			log.log(Level.SEVERE, sql.toString(), e);
			processMsg=e.getMessage();
			return false;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		// Actualizo atributos de filtros
		this.setDocStatus(DocumentEngine.ACTION_WaitComplete);
		
		this.setDocAction(DocumentEngine.ACTION_Complete);
		this.saveEx();
		
		return true;
	}

	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Se crea la clausula Where para el filtrado.
	 * @return String
	 * @author  Nicolas Garcia
	 * Fecha : 02/06/2011
	 */
	private String getWhere() {
		 
		//Genero la clausula WHERE

		// OpenUp. Gabriel Vila. 25/07/2011. Issue #790.
		// Hago cambios en where de documentos
		
		/*String salida =" WHERE ((C_DocType_ID=1000066  AND DocStatus NOT IN('DR','IN') AND " +
				"(Processed IS NULL OR Processed<>'Y')) OR (C_DocType_ID=1000059 AND DocStatus NOT IN('CL','CO','RE','VO') " +
				"AND (Processed IS NULL OR Processed<>'Y')))";*/

		String salida = " WHERE ((C_DocType_ID=1000066  AND DocStatus='AP') " +
		                " OR (C_DocType_ID=1000059 AND DocStatus NOT IN('CL','CO','RE','VO')))";


		
		// Fin Issue #790.
		
		
		//TODO: parametrizar c_doctype_id
		
		if(this.getC_BPartner_ID()>0){
			salida +=" AND c_bpartner_id="+this.getC_BPartner_ID();
		}
		if(this.getC_BPartner_Location_ID()>0){
			salida +=" AND c_bpartner_location_id="+this.getC_BPartner_Location_ID();
		}
		if(this.getSalesRep_ID()>0){
			salida +=" AND salesrep_id="+this.getSalesRep_ID();
		}
		if(this.getUY_AsignaTransporteHdr_ID()>0){
			salida +=" AND UY_AsignaTransporteHdr_ID="+this.getUY_AsignaTransporteHdr_ID();
		}
		if(this.getuy_dateordered_to()!=null){
			salida +=" AND dateordered >='"+this.getuy_dateordered_to()+"'";
		}
		if(this.getuy_dateordered_from()!=null){
			salida +=" AND dateordered <(date '"+this.getuy_dateordered_from()+"' + interval '1 day')"; //Se suma un dia para incluir el dia actual.
		}
		
		
		return salida;
	}
	
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Se reutilizo y adapto metodo que Fabian Larroca habia generado en clase PCompleteInOutReturns
	 * @return Boolean
	 * @author  Nicolas Garcia
	 * Fecha : 08/06/2011
	 */
	private boolean updateDevoluciones() {
		// Get all records from product having a value
		//ORDEN pedido por leonel
		String sql ="SELECT uy_upddevo_lines.m_inout_id FROM uy_upddevo_lines LEFT JOIN m_inout dev ON dev.m_inout_id=uy_upddevo_lines.m_inout_id " +
					"WHERE uy_upddevo_lines.uy_procesar = 'Y' AND uy_upddevo_filter_id ="+this.getUY_UpdDevo_Filter_ID() +
					"ORDER BY dev.uy_asignatransportehdr_id";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String trxName = null;
		Trx trx = null;

		// This counters are used only to show the totals of processMsg=s processed or errors.
		int count=0;
		int error=0;
		
		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
		
			trxName = Trx.createTrxName();
			trx = Trx.get(trxName, true);
			
			// Just get the first record, else a null product will be processMsg=ed
			while (rs.next()){
				int M_InOut_ID=rs.getInt(1);
				
				// 0 means null
				if (M_InOut_ID!=0) {
					// Get the model
					MInOut inOut = new MInOut(Env.getCtx(),M_InOut_ID,trxName);
					
					// Null means that cannot get the model
					if (inOut.get_ID() > 0) {

						// OpenUp. Gabriel Vila. 17/08/2011. Issue #849.
						// Me aseguro que la fecha de devolucion sea la fecha de hoy
						inOut.setMovementDate(new Timestamp(System.currentTimeMillis()));
						inOut.setDateAcct(inOut.getMovementDate());
						// Fin Issue #849
						
						// Set all object to draft to avoid problems with invalid ones
						inOut.setDocStatus(MInOut.DOCSTATUS_Drafted);
						
						if (inOut.processIt(MInOut.DOCACTION_Complete)) {
							inOut.saveEx(trxName);
							count++;
						}
						else {
							error++;
							log.log(Level.SEVERE,"No se pudo completar (m_inout_id="+M_InOut_ID+")");				// TODO: translate
						}
					} 
					else {
						error++;
						log.log(Level.SEVERE,"No pudo encontrar (m_inout_id="+M_InOut_ID+")");						// TODO: translate
					}
				}
				else {
					error++;
					log.log(Level.SEVERE,"No obtuvo un indentificador correcto");									// TODO: translate
				}
				
			}
			
			// Close, the commit also should ocurr here
			trx.close();
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			if (trx!=null) {
				trx.rollback();
			}
			processMsg=e.getMessage();
			return false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		log.log(Level.INFO,count+" devoluciones confirmadas, "+error+" devoluciones con error");								// TODO: translate

		// Actualizo atributos de filtros
		this.setDocStatus(DocumentEngine.ACTION_Complete);
		
		this.setDocAction(DocumentEngine.ACTION_Close);
		this.setProcessed(true);
		this.saveEx();
		
		return true ;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
