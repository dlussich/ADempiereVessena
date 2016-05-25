/**
 * MInvoiceBook.java
 * 29/03/2011
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;

/**
 * OpenUp.
 * MCheckBook
 * Descripcion :
 * @author Mario Reyes
 * Fecha : 20/07/2011
 */
public class MCheckBookControl extends X_UY_CheckBookControl implements DocAction {
	
	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7836398863738909053L;
	//OpenUp Nicolas Sarlabos #966 17/02/2012
	private static final String TABLA_MOLDE_AUX = "UY_Molde_CheckBookControl"; //tabla molde auxiliar

	//fin OpenUp Nicolas Sarlabos #966 17/02/2012

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InvoiceBook_ID
	 * @param trxName
	 */
	public MCheckBookControl(Properties ctx, int UY_CheckBook_ID, String trxName) {
		super(ctx, UY_CheckBook_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCheckBookControl(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 	Called before Save for Pre-Save Operation
	 * 	@param newRecord new record
	 *	@return true if record can be saved
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)	{

		// Get the parent, it will be used many times
		MCheckBook checkBook=this.getParent();

		// Only one can not processed at time
		if (this.notProcessed()!=0) {
			throw new IllegalArgumentException("Hay otros controles de chequeras no procesados, es necesario procesarlos o eliminarlos antes de continuar");				// TODO: translate
		}

		// Dates controls
		// If the date start is not set, set it with the default
		if (this.getDateStart()==null) {
			this.setDateStart(this.getDefaultDateStart());
		}

		// Start date before book start
		if (this.getDateStart().compareTo(checkBook.getDateStart())<0) {
			throw new IllegalArgumentException("Fecha de inicio del control anterior a la fecha de inicio de uso de libreta");				// TODO: translate
		}

		// Control finish date before control start date
		if (this.getDateFinish().compareTo(this.getDateStart())<0) {
			throw new IllegalArgumentException("Fecha de fin de control debe ser superior a "+this.getDateStart());							// TODO: translate
		}

		// Dates outside due date
		if ((this.getDateStart().compareTo(checkBook.getDueDate())>0)&&(this.getDateFinish().compareTo(checkBook.getDueDate())>0)) {
			throw new IllegalArgumentException("Rango de fechas del control posterior a la fecha de vencimietno de la libreta");			// TODO: translate
		}

		// Numbers controls
		// If the start is not set, set it with the default
		if (this.getDocumentNoStart()==0) {
			this.setDocumentNoStart(this.getDefaultDocumentNoStart());
		}

		// Start number outside range
		if (!((this.getDocumentNoStart()>=checkBook.getDocumentNoStart())&&(this.getDocumentNoStart()<=checkBook.getDocumentNoEnd()))) {
			throw new IllegalArgumentException("Numero de inicio fuera de rango de numeracion de libreta");									// TODO: translate
		}

		// End number must be great or equlas the start
		if (!(this.getDocumentNoEnd()>this.getDocumentNoStart())) {
			throw new IllegalArgumentException("Numero de fin debe ser superior a "+this.getDocumentNoStart());		// TODO: translate
		}

		// End number outside range
		if (!((this.getDocumentNoEnd()>=checkBook.getDocumentNoStart())&&(this.getDocumentNoEnd()<=checkBook.getDocumentNoEnd()))) {
			throw new IllegalArgumentException("Numero de fin fuera de rango de numeracion de libreta");									// TODO: translate
		}

		return(true);
	}	//	beforeSave

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @return
	 * @author  Mario Reyes 
	 * Fecha : 20/07/2011
	 */
	private int notProcessed() {

		int notProcessed=DB.getSQLValueEx(get_TrxName(),"SELECT count(*) FROM uy_checkbookcontrol WHERE processed='N' AND uy_checkbook_id=? AND uy_checkbookcontrol_id<>?",this.getUY_CheckBook_ID(),this.getUY_CheckBookControl_ID());

		return(notProcessed);
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @return
	 * @author  Mario Reyes 
	 * Fecha : 20/07/2011
	 */
	private Timestamp getDefaultDateStart() {

		Timestamp defaultDateStart=DB.getSQLValueTSEx(get_TrxName(),"SELECT max(uy_checkbookcontrol.datefinish) + interval '1 day' FROM uy_checkbookcontrol WHERE uy_checkbook_id="+this.getUY_CheckBook_ID()+" AND uy_checkbookcontrol_id<>"+this.getUY_CheckBookControl_ID());

		// If not default start return the parent start, otherwise the number should be incremented
		if (defaultDateStart==null) {
			defaultDateStart=this.getParent().getDateStart();
		}  

		return(defaultDateStart);
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @return
	 * @author  Mario Reyes 
	 * Fecha : 20/07/2011
	 */
	private int getDefaultDocumentNoStart() {

		int defaultDocumentNoStart=DB.getSQLValueEx(get_TrxName(),"SELECT max(uy_checkbookcontrol.documentnoend)+1 FROM uy_checkbookcontrol WHERE uy_checkbook_id="+this.getUY_CheckBook_ID()+" AND uy_checkbookcontrol_id<>"+this.getUY_CheckBookControl_ID());

		// If not default start return the parent start, otherwise the number should be incremented
		if (defaultDocumentNoStart<=0) {
			defaultDocumentNoStart=this.getParent().getDocumentNoStart();
		}  

		return(defaultDocumentNoStart);
	}

	/**
	 * Get the invoice book, the parent
	 */
	public MCheckBook getParent()
	{
		return(new MCheckBook(getCtx(),getUY_CheckBook_ID(), get_TrxName()));
	}	//	getParent

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/**
	 * Set the counter
	 */
	public void setCounter() {

		int counter=DB.getSQLValueEx(get_TrxName(),"SELECT count(uy_checkbookgap.uy_checkbookgap_id) FROM uy_checkbookgap WHERE uy_checkbookgap.uy_checkbookcontrol_id="+this.getUY_CheckBookControl_ID());
		if (counter>0) {
			this.setCounter(counter);
		}
		else {
			this.setCounter(0);
		}
	}

	/**
	 * Set not used
	 */
	public void setNotUsed() {

		int notUsed=DB.getSQLValueEx(get_TrxName(),"SELECT count(uy_checkbooknotused.uy_checkbooknotused_id) FROM uy_checkbooknotused WHERE uy_checkbooknotused.uy_checkbookcontrol_id="+this.getUY_CheckBookControl_ID());
		if (notUsed>0) {
			this.setNotUsed(notUsed);
		}
		else {
			this.setNotUsed(0);
		}
	}
	
	/**
	 * Metodo que carga los datos de cheques mediante el boton Procesar.
	 * OpenUp Ltda. Issue #3069.
	 * @author Nicolas Sarlabos - 27/11/2015
	 * @see
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public void loadData() {
		
		try{
			
			this.setGaps();		
			this.setTypes();
			
			this.setCounter();
			this.setNotUsed();			
			
		} catch (Exception e){
			
			throw new AdempiereException(e.getMessage());
			
		}		
		
	}

	/**
	 * Add gaps
	 */
	public void setGaps() throws Exception {

		//OpenUp Nicolas Sarlabos #966 17/02/2012
		MCheckBook check = new MCheckBook(getCtx(), getUY_CheckBook_ID(), null);

		String SQL = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		//  Delete previous records
		DB.executeUpdateEx("DELETE FROM uy_checkbookgap WHERE uy_checkbookcontrol_id=" + this.getUY_CheckBookControl_ID(), get_TrxName());
		DB.executeUpdateEx("DELETE FROM uy_checkbooknotused WHERE uy_checkbookcontrol_id=" + this.getUY_CheckBookControl_ID(), get_TrxName());
		
		try {

			//consulta para obtener cheques con el "checkno" en formato texto, luego a los cancelados se les debera quitar el texto "VOID-"
			//para poder considerarlos en el rango elegido 
			//OpenUp Nicolas Sarlabos #966 20/03/2012
			SQL = " SELECT uy_mediospago.uy_mediospago_id, uy_mediospago.c_bpartner_id, coalesce(uy_mediospago.checkno,'') as checkno "
					+ " FROM   uy_mediospago " + " WHERE  c_bankaccount_id =" + check.getC_BankAccount_ID()
					+ " AND uy_mediospago.docstatus not in ('RE','VO') AND uy_mediospago.tipomp='PRO'" + " AND uy_mediospago.datetrx between (date '"
					+ this.getDateStart() + "' - interval '1 day') " + " AND '" + this.getDateFinish() + "' "
					+ " ORDER BY uy_mediospago.checkno, uy_mediospago.datetrx";
			//Fin OpenUp Nicolas Sarlabos #966 20/03/2012

			pstmt = DB.prepareStatement(SQL, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				BigDecimal nro = Env.ZERO;
				String checkno = rs.getString("checkno");
								
				//OpenUp Nicolas Sarlabos #966 20/03/2012
				checkno = checkno.replace(".", ""); //se eliminan los puntos
				checkno = checkno.replace(" ", ""); //se elimina espacios en blanco
				checkno = checkno.replace("-", ""); //se elimina espacios guiones
				checkno = checkno.replace("VOID", ""); //se elimina VOID
				
				//OpenUp. Nicolas Sarlabos. #3069. Se agrega control de excepcion.
				try {

					nro = new BigDecimal(checkno);					

				} catch (Exception e){

					throw new AdempiereException("Error al convertir numero de cheque: " + rs.getString("checkno"));				

				}
				//Fin OpenUp.			
		
				//se insertan los datos en la tabla molde con el checkno ya convertido a entero
				SQL = "INSERT INTO " + TABLA_MOLDE_AUX
						+ " (ad_client_id, ad_org_id, ad_user_id, uy_checkbookcontrol_id, uy_mediospago_id, checkno, c_bpartner_id) VALUES("
						+ Env.getAD_Client_ID(getCtx()) + "," + Env.getAD_Org_ID(getCtx()) + "," + Env.getAD_User_ID(Env.getCtx()) + ","
						+ this.getUY_CheckBookControl_ID() + ","
						+ rs.getInt("uy_mediospago_id") + "," + nro + ","
						+ rs.getInt("c_bpartner_id") + ")";

				DB.executeUpdateEx(SQL, get_TrxName());
				//Fin OpenUp Nicolas Sarlabos #966 20/03/2012


			}

			//se recorre la tabla molde
			SQL = "SELECT uy_mediospago_id, c_bpartner_id, checkno FROM " + TABLA_MOLDE_AUX + " WHERE  checkno >= " + this.getDocumentNoStart()
					+ " AND checkno <= " + this.getDocumentNoEnd() + " AND uy_checkbookcontrol_id=" + this.getUY_CheckBookControl_ID() + " ORDER BY checkno";

			//fin OpenUp Nicolas Sarlabos #966 17/02/2012

			pstmt=DB.prepareStatement (SQL,get_TrxName());

			MCheckBook checkBook=this.getParent();

			int inicio=this.getDocumentNoStart();
			int fin = this.getDocumentNoEnd();
			int copies=checkBook.getCopies();
			int UY_CheckBookControl_ID=this.getUY_CheckBookControl_ID();

			rs=pstmt.executeQuery();

			int actual = inicio;

			while (rs.next()) {
				for (int i = actual; i <= fin; i++) {
					int checkno = rs.getInt("checkno");
					if (checkno == i) {
						MCheckBookGap checkBookGap = new MCheckBookGap(getCtx(), 0, get_TrxName());
						checkBookGap.setUY_CheckBookControl_ID(UY_CheckBookControl_ID);
						checkBookGap.setUY_MediosPago_ID(rs.getInt(1));
						checkBookGap.setC_BPartner_ID(rs.getInt(2));
						checkBookGap.setDocumentNoStart(inicio);
						checkBookGap.setDocumentNoEnd(fin + copies - 1);
						checkBookGap.saveEx();

						actual = i + 1;
						i = fin;
						
					} else {
						MCheckBookNotUsed checkBooknotused=new MCheckBookNotUsed(getCtx(),0,get_TrxName());
						checkBooknotused.setUY_CheckBookControl_ID(UY_CheckBookControl_ID);
						checkBooknotused.setDocumentNoNotUsed(i);
						checkBooknotused.saveEx();
						
						actual = i + 1;

					}

				}//fin for
			}//fin while
			if ((actual) <= fin) {
				for (int i = actual; i <= fin; i++) {
					MCheckBookNotUsed checkBooknotused = new MCheckBookNotUsed(getCtx(), 0, get_TrxName());
					checkBooknotused.setUY_CheckBookControl_ID(UY_CheckBookControl_ID);
					checkBooknotused.setDocumentNoNotUsed(i);
					checkBooknotused.saveEx();
				}
			}
			
			//elimino registros de la tabla molde auxiliar
			DB.executeUpdateEx("DELETE FROM " + TABLA_MOLDE_AUX + " WHERE uy_checkbookcontrol_id=" + this.getUY_CheckBookControl_ID(), get_TrxName());
			
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}
	}

	/**
	 * Add types
	 */
	public void setTypes() throws Exception {

		String SQL= "SELECT uy_mediospago.c_doctype_id, min(uy_checkbookgap.documentnostart) as documentnostart, max(uy_checkbookgap.documentnoend) as documentnoend, sum(uy_checkbookgap.documentnoend-uy_checkbookgap.documentnostart+1) as counter " +
				"FROM   uy_checkbookgap "
				+ "		LEFT JOIN uy_mediospago ON uy_mediospago.uy_mediospago_id=uy_checkbookgap.uy_mediospago_id "
				+ "WHERE  uy_checkbookgap.uy_checkbookcontrol_id=? " + "GROUP BY uy_mediospago.c_doctype_id";

		ResultSet rs=null;
		PreparedStatement pstmt=null;

		//  Delete previous records
		DB.executeUpdateEx("DELETE FROM uy_checkbooktype WHERE uy_checkbooktype.uy_checkbookcontrol_id="+this.getUY_CheckBookControl_ID(),get_TrxName());

		try {
			pstmt=DB.prepareStatement (SQL,get_TrxName());
			pstmt.setInt(1,this.getUY_CheckBookControl_ID());

			int UY_CheckBookControl_ID=this.getUY_CheckBookControl_ID();

			rs=pstmt.executeQuery();
			while (rs.next()) {

				MCheckBookType checkBookType=new MCheckBookType(getCtx(),0,get_TrxName());
				checkBookType.setUY_CheckBookControl_ID(UY_CheckBookControl_ID);
				checkBookType.setC_DocType_ID(rs.getInt(1));
				checkBookType.setDocumentNoStart(rs.getInt(2));
				checkBookType.setDocumentNoEnd(rs.getInt(3));
				checkBookType.setCounter(rs.getInt(4));
				checkBookType.saveEx();
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}
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
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Se implementa metodo completeIt
	 * OpenUp Ltda. Issue #966 
	 * @author Nicolas Sarlabos - 28/03/2012
	 * @see
	 * @param action
	 * @return
	 * @throws Exception
	 */
	
	@Override
	public String completeIt() {

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

		//this.load(get_TrxName());

		if (this.getControl() == 0) {

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String description, sql, error = "";
			error = "Existen documentos sin descripción de nulidad";
			sql = "Select description from uy_checkbooknotused where uy_checkbookcontrol_id = " + this.getUY_CheckBookControl_ID();

			try {

				pstmt = DB.prepareStatement(sql, null); // Create the statement
				rs = pstmt.executeQuery();

				// Read the first record and get a new object
				while (rs.next()) {
					description = rs.getString("description");
					if (description.equalsIgnoreCase("")){
						throw new Exception(error);
					}

				}

			} catch (Exception e) {
				throw new AdempiereException(error);
			}			
		}
		
		MCheckBook checkBook = this.getParent();
		if (!(checkBook.isProcessed())) {
			checkBook.setProcessed(true);
			checkBook.saveEx();
			this.setDocStatus(DOCSTATUS_Completed);
		}

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

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
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
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
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

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
