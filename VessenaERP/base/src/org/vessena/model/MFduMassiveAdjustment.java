/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.openup.util.ItalcredSystem;
import org.openup.util.OpenUpUtils;

/**
 * @author gbrust
 *
 */
public class MFduMassiveAdjustment extends X_UY_Fdu_MassiveAdjustment implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4031294963927213232L;
	
	private String processMsg = null;
	private boolean justPrepared = false;
	
	private int idConexionFinancial = 1000015;
	private ItalcredSystem italcredSystem = null;

	/**
	 * @param ctx
	 * @param UY_Fdu_MassiveAdjustment_ID
	 * @param trxName
	 */
	public MFduMassiveAdjustment(Properties ctx,
			int UY_Fdu_MassiveAdjustment_ID, String trxName) {
		super(ctx, UY_Fdu_MassiveAdjustment_ID, trxName);
		
		
		this.italcredSystem = new ItalcredSystem();
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduMassiveAdjustment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);		
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		//Re-Check
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

		//OpenUp. Guillermo Brust. 25/10/2013. ISSUE #1395
		//L�gica de completado
		this.realizarAjustes();
		//Fin OpenUp.
		
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
	
	
	/**
	 * OpenUp. Guillermo Brust. 25/10/2013. ISSUE #1395
	 * M�todo que se llama al completar el documento, y realiza para cada una de las cuentas que les debe aplicar Aviso de Mora, una linea de ajuste por este concepto
	 *  
	 * **/
	private void realizarAjustes(){
		
		
		//Se guardan los ajustes en la tabla de ajustes del Financial.	
		//Se debe usar una transaccion porque puede ser que la primer cuota se guarde un ajuste, pero en la siguientes si ocurre una error, no deberia guardar nada
		Connection con = null;
		try {
			
			//esta es la conexion al sql server, ahora apunta a una base de TESTING
			con = OpenUpUtils.getConnectionToSqlServer(idConexionFinancial);
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false); //esto para iniciar una transaccion
					
			ResultSet rs = null;
			PreparedStatement pstmt = null;		

			try {		
				
				String sql = "select distinct cedula, accountno, nrotarjetatitular, importe, codigoajuste" +
							 " from uy_fdu_massiveadjustline" + 
							 " where uy_fdu_massiveadjustment_id = " + this.get_ID() +
							 " and isvalid = 'Y'";

				pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
				rs = pstmt.executeQuery ();	
				
				
				//Recorro las lineas que tengo para realizar los ajustes
				while(rs.next()){
					
					//Matriz donde guardar los datos que se los voy a pasar al metodo que se encarga de impactar los ajustes en el Financial
		    		Object[] data = new Object[12];
		    		data[0] = rs.getString("cedula");
		    		data[1] = rs.getString("accountno");
		    		data[2] = rs.getString("nrotarjetatitular");
		    		data[3] = 142;
		    		data[4] = rs.getBigDecimal("importe");
		    		data[5] = rs.getString("codigoajuste");
		    		data[6] = this.getAD_User().getName().toUpperCase();
		    		data[7] = "Aviso de Mora";
		    		data[8] = this.getAD_User().getName().toUpperCase();
		    		data[9] = this.getDateTrx();
		    		data[10] = this.getDateTrx();
		    		data[11] = this.get_ID();		    				
	    			
	    			//obtengo el nuevo identficador para la nueva linea que vaya a guardar en el sqlserver
			    	int tablaAjusteID = this.italcredSystem.obtenerIdentificadorTablaAjuste(con, stmt);   
			    	
			    	if(tablaAjusteID > 0){
			    		//guardo la linea del ajuste en sqlserver con el ID que obtuve
				    	this.italcredSystem.guardarAjusteSQLServer(tablaAjusteID, data, con, stmt);
			    	}else{
			    		throw new AdempiereException("No se logr� obtener identificador unico para grabar ajuste");
			    	} 	    	
				}
				
			} catch (Exception e) {
				DB.close(rs, pstmt);	
				throw new AdempiereException(e);
			}
			finally{
				DB.close(rs, pstmt);
			}		
		    
		    //Luego de hacer todo, recien ahi termino la transaccion
	    	con.commit(); //This commits the transaction and starts a new one.
			stmt.close(); //This turns off the transaction.	    			
			con.close();

		} catch (Exception e) {
			try {
				if(!con.isClosed()){
					con.rollback();
				}				
			} catch (Exception e2) {
				throw new AdempiereException(e);
			}			
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){				
				try {						
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}	 	 	
	}

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 *  OpenUp. Guillermo Brust. 31/10/2013. ISSUE #
	 *  Obtiene y retorno las lineas para este cabezal. 
	 */
	public List<MFduMassiveAdjustLine> getLines(String trxName)
	{
		String whereClause = X_UY_Fdu_MassiveAdjustLine.COLUMNNAME_UY_Fdu_MassiveAdjustment_ID + " =  " + this.get_ID();
		
		List<MFduMassiveAdjustLine> list = new Query(this.getCtx(), I_UY_Fdu_MassiveAdjustLine.Table_Name, whereClause, trxName).setOrderBy(X_UY_Fdu_MassiveAdjustLine.COLUMNNAME_AccountNo).list();
		
		return list;
	}
	
	
	/**
	 *  OpenUp. Guillermo Brust. 19/11/2013. ISSUE #
	 *  Obtiene y retorno las lineas para este cabezal y con el numero de cuenta que se pasa por parametro. 
	 */
	public List<MFduMassiveAdjustLine> getLines(String trxName, String accountNo)
	{
		String whereClause = X_UY_Fdu_MassiveAdjustLine.COLUMNNAME_UY_Fdu_MassiveAdjustment_ID + " =  " + this.get_ID() +
							" AND " + X_UY_Fdu_MassiveAdjustLine.COLUMNNAME_AccountNo + " = '" + accountNo + "'";
		
		List<MFduMassiveAdjustLine> list = new Query(this.getCtx(), I_UY_Fdu_MassiveAdjustLine.Table_Name, whereClause, trxName).setOrderBy(X_UY_Fdu_MassiveAdjustLine.COLUMNNAME_AccountNo).list();
		
		return list;
	}

}
