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
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MRecioptBookControl
 * Descripcion :
 * @author Mario Reyes 
 * Fecha : 22/07/2011
 */
public class MReceiptBookControl extends X_UY_ReceiptBookControl implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836398863738909053L;
	//OpenUp Nicolas Sarlabos #1055 26/06/2013
	private static final String TABLA_MOLDE_AUX = "UY_Molde_ReceiptBookControl"; //tabla molde auxiliar
	//fin OpenUp Nicolas Sarlabos #1055

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_ReceiptBook_ID
	 * @param trxName
	 */
	public MReceiptBookControl(Properties ctx, int UY_ReceiptBook_ID, String trxName) {
		super(ctx, UY_ReceiptBook_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MReceiptBookControl(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	Called before Save for Pre-Save Operation
	 * 	@param newRecord new record
	 *	@return true if record can be saved
	 */
	protected boolean beforeSave(boolean newRecord)	{

		// Get the parent, it will be used many times
		MReceiptBook receiptBook=this.getParent();

		// Only one can not processed at time
		if (this.notProcessed()!=0) {
			throw new IllegalArgumentException("Hay otros controles diarios no procesados, es necesario procesarlos o eliminarlos antes de continuar");				// TODO: translate
		}

		// Dates controls
		// If the date start is not set, set it with the default
		if (this.getDateStart()==null) {
			this.setDateStart(this.getDefaultDateStart());
		}

		// Start date before book start
		if (this.getDateStart().compareTo(receiptBook.getDateStart())<0) {
			throw new IllegalArgumentException("Fecha de inicio del control anterior a la fecha de inicio de uso de libreta");				// TODO: translate
		}

		// Control finish date before control start date
		if (this.getDateFinish().compareTo(this.getDateStart())<0) {
			throw new IllegalArgumentException("Fecha de fin de control debe ser superior a "+this.getDateStart());							// TODO: translate
		}
		
		// Dates outside due date
		if ((this.getDateStart().compareTo(receiptBook.getDueDate())>0)&&(this.getDateFinish().compareTo(receiptBook.getDueDate())>0)) {
			throw new IllegalArgumentException("Rango de fechas del control posterior a la fecha de vencimiento de la libreta");			// TODO: translate
		}

		// Numbers controls
		// If the start is not set, set it with the default
		if (this.getDocumentNoStart()==0) {
			this.setDocumentNoStart(this.getDefaultDocumentNoStart());
		}
		
		// Start number outside range
		if (!((this.getDocumentNoStart()>=receiptBook.getDocumentNoStart())&&(this.getDocumentNoStart()<=receiptBook.getDocumentNoEnd()))) {
			throw new IllegalArgumentException("Numero de inicio fuera de rango de numeracion de libreta");									// TODO: translate
		}
		
		// End number must be great or equlas the start
		if (!(this.getDocumentNoEnd()>this.getDocumentNoStart())) {
			throw new IllegalArgumentException("Numero de fin debe ser superior a "+this.getDocumentNoStart());		// TODO: translate
		}
		
		// End number outside range
		if (!((this.getDocumentNoEnd()>=receiptBook.getDocumentNoStart())&&(this.getDocumentNoEnd()<=receiptBook.getDocumentNoEnd()))) {
			throw new IllegalArgumentException("Numero de fin fuera de rango de numeracion de libreta");									// TODO: translate
		}
		
		return(true);
	}	//	beforeSave

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @return
	 * @author  FL 
	 * Fecha : 10/05/2011
	 */
	private int notProcessed() {
		
		int notProcessed=DB.getSQLValueEx(get_TrxName(),"SELECT count(*) FROM uy_receiptbookcontrol WHERE processed='N' AND uy_receiptbook_id=? AND uy_receiptbookcontrol_id<>?",this.getUY_ReceiptBook_ID(),this.getUY_ReceiptBookControl_ID());
		
		return(notProcessed);
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @return
	 * @author  FL 
	 * Fecha : 10/05/2011
	 */
	private Timestamp getDefaultDateStart() {
		
		Timestamp defaultDateStart=DB.getSQLValueTSEx(get_TrxName(),"SELECT max(uy_receiptbookcontrol.datefinish) + interval '1 day' FROM uy_receiptbookcontrol WHERE uy_receiptbook_id="+this.getUY_ReceiptBook_ID()+" AND uy_receiptbookcontrol_id<>"+this.getUY_ReceiptBookControl_ID());
		
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
	 * @author  FL 
	 * Fecha : 10/05/2011
	 */
	private int getDefaultDocumentNoStart() {
		
		int defaultDocumentNoStart=DB.getSQLValueEx(get_TrxName(),"SELECT max(uy_receiptbookcontrol.documentnoend)+1 FROM uy_receiptbookcontrol WHERE uy_receiptbook_id="+this.getUY_ReceiptBook_ID()+" AND uy_receiptbookcontrol_id<>"+this.getUY_ReceiptBookControl_ID());
		
		// If not default start return the parent start, otherwise the number should be incremented
		if (defaultDocumentNoStart<=0) {
			defaultDocumentNoStart=this.getParent().getDocumentNoStart();
		}  

		return(defaultDocumentNoStart);
	}

	/**
	 * Get the invoice book, the parent
	 */
	public MReceiptBook getParent()
	{
		return(new MReceiptBook(getCtx(),getUY_ReceiptBook_ID(), get_TrxName()));
	}	//	getParent
	
	/**
	 * Process the control
	 * @throws Exception 
	 */
	public boolean processIt() throws Exception {

		// set gaps and types
		this.setGaps();		
		this.setTypes();
		
		// Set counter and not used
		this.setCounter();
		this.setNotUsed();
		this.setDocStatus(DOCSTATUS_InProgress); //OpenUp M.R. 21-09-2011 Issue#721 seteo estado del documento a en proceso, para despue completar
		this.setDocAction(DOCACTION_Complete);

		// Save before reloading
		this.saveEx();

//OpenUp M.R. 21-09-2011 Issue#721 comento lineas
		// Reload the record to update virtual fields
		/*
		 * this.load(get_TrxName()); if (this.getControl()==0) {
		 * this.setProcessed(true); this.saveEx();
		 * 
		 * MReceiptBook receiptBook=this.getParent(); if
		 * (!(receiptBook.isProcessed())) { receiptBook.setProcessed(true);
		 * receiptBook.saveEx(); } }
		 */
//Fin OpenUp
		return(true);
	}
	
	/**
	 * Set the counter
	 */
	public void setCounter() {
		
		int counter=DB.getSQLValueEx(get_TrxName(),"SELECT count(uy_receiptbookgap.uy_receiptbookgap_id) FROM uy_receiptbookgap WHERE uy_receiptbookgap.uy_receiptbookcontrol_id="+this.getUY_ReceiptBookControl_ID());
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
		
		int notUsed=DB.getSQLValueEx(get_TrxName(),"SELECT count(uy_receiptbooknotused.*) FROM uy_receiptbooknotused WHERE uy_receiptbooknotused.uy_receiptbookcontrol_id="+this.getUY_ReceiptBookControl_ID());
		if (notUsed>0) {
			this.setNotUsed(notUsed);
		}
		else {
			this.setNotUsed(0);
		}
	}

	/**
	 * Add gaps
	 */
	public void setGaps() throws Exception {
		
		//OpenUp. Nicolas Sarlabos. 26/06/2013. #1055
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		String SQL = "";
		
		//  Delete previous records
		//OpenUp Nicolas Sarlabos #721 2/11/2011 se corrige sql
		DB.executeUpdateEx("DELETE FROM uy_receiptbookgap WHERE uy_receiptbookgap.uy_receiptbookcontrol_id="+this.getUY_ReceiptBookControl_ID(),get_TrxName());
		DB.executeUpdateEx("DELETE FROM uy_receiptbooknotused WHERE uy_receiptbooknotused.uy_receiptbookcontrol_id="+this.getUY_ReceiptBookControl_ID(),get_TrxName());
		//fin OpenUp Nicolas Sarlabos #721 2/11/2011 
		
		try {

			SQL= "SELECT c_payment.c_payment_id, c_payment.c_bpartner_id, c_payment.documentno " +
					"FROM   c_payment "+
					"WHERE  c_payment.docstatus = 'CO' AND c_payment.isreceipt='Y' "+
					" AND c_payment.datetrx between (date '"+ this.getDateStart() +"' - interval '1 day') " +
					" AND '" + this.getDateFinish() + "' " +
					"ORDER BY c_payment.documentno";
			
			pstmt = DB.prepareStatement(SQL, get_TrxName());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				BigDecimal nro = Env.ZERO;
				String documentno = rs.getString("documentno");
				documentno = documentno.replace(".", ""); //se eliminan los puntos
				documentno = documentno.replace(" ", ""); //se elimina espacios en blanco
				documentno = documentno.replace("-", ""); //se elimina guiones
				
				//OpenUp. Nicolas Sarlabos. #3068. Se agrega control de excepcion.
				try {

					nro = new BigDecimal(documentno);					

				} catch (Exception e){

					throw new AdempiereException("Error al convertir numero de recibo: " + rs.getString("documentno"));				

				}
				//Fin OpenUp.

				//se insertan los datos en la tabla molde con el documentno ya convertido a entero
				SQL = "INSERT INTO " + TABLA_MOLDE_AUX
						+ " (ad_client_id, ad_org_id, ad_user_id, uy_receiptbookcontrol_id, c_payment_id, documentno, c_bpartner_id) VALUES("
						+ Env.getAD_Client_ID(Env.getCtx()) + "," + Env.getAD_Org_ID(Env.getCtx()) + "," + Env.getAD_User_ID(Env.getCtx()) + ","
						+ this.get_ID() + "," + rs.getInt("c_payment_id") + "," + nro + "," + rs.getInt("c_bpartner_id") + ")";

				DB.executeUpdateEx(SQL, get_TrxName());
			}
			
			//se recorre la tabla molde
			SQL = "SELECT c_payment_id, c_bpartner_id, documentno FROM " + TABLA_MOLDE_AUX + " WHERE  documentno >= " + this.getDocumentNoStart()
					+ " AND documentno <= " + this.getDocumentNoEnd() + " AND uy_receiptbookcontrol_id=" + this.getUY_ReceiptBookControl_ID() + " ORDER BY documentno asc";
			
			pstmt=DB.prepareStatement (SQL,get_TrxName());

			MReceiptBook receiptBook=this.getParent();

			//int documentNo=this.getDocumentNoStart();
			int copies=receiptBook.getCopies();
			int UY_ReceiptBookControl_ID=this.getUY_ReceiptBookControl_ID();
			int inicio=this.getDocumentNoStart();
			int fin = this.getDocumentNoEnd();

			rs=pstmt.executeQuery();

			int actual = inicio;

			for (int i= actual; i <= fin; i++ ){
							
				while (rs.next()) {

					int paymentID = rs.getInt("documentno");
					if (paymentID==actual){
						MReceiptBookGap receiptBookGap=new MReceiptBookGap(getCtx(),0,get_TrxName());
						receiptBookGap.setUY_ReceiptBookControl_ID(UY_ReceiptBookControl_ID);
						receiptBookGap.setC_Payment_ID(rs.getInt(1));
						receiptBookGap.setC_BPartner_ID(rs.getInt(2));
						receiptBookGap.setDocumentNoStart(actual);
						receiptBookGap.setDocumentNoEnd(actual+copies-1);
						receiptBookGap.saveEx();

						actual = actual+1;
						

					}
					else{
						MReceiptBookNotUsed receiptBooknotused=new MReceiptBookNotUsed(getCtx(),0,get_TrxName());
						receiptBooknotused.setUY_ReceiptBookControl_ID(UY_ReceiptBookControl_ID);
						receiptBooknotused.setDocumentNoNotUsed(actual);
						receiptBooknotused.saveEx();
						
						MReceiptBookGap receiptBookGap=new MReceiptBookGap(getCtx(),0,get_TrxName());
						receiptBookGap.setUY_ReceiptBookControl_ID(UY_ReceiptBookControl_ID);
						receiptBookGap.setC_Payment_ID(rs.getInt(1));
						receiptBookGap.setC_BPartner_ID(rs.getInt(2));
						receiptBookGap.setDocumentNoStart(paymentID);
						receiptBookGap.setDocumentNoEnd(paymentID+copies-1);
						receiptBookGap.saveEx();

						actual = actual+2;							

					}

				}//fin while
			}//fin for
			if( (actual) <= fin){
				for (int i= actual; i <= fin; i++ ){
					MReceiptBookNotUsed receiptBooknotused=new MReceiptBookNotUsed(getCtx(),0,get_TrxName());
					receiptBooknotused.setUY_ReceiptBookControl_ID(UY_ReceiptBookControl_ID);
					receiptBooknotused.setDocumentNoNotUsed(i);
					receiptBooknotused.saveEx();
			}
		}
			
			DB.executeUpdateEx("DELETE FROM " + TABLA_MOLDE_AUX + " WHERE uy_receiptbookcontrol_id = " + this.getUY_ReceiptBookControl_ID(),get_TrxName());
			//Fin OpenUp. #1055
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
		
		String SQL= "SELECT c_payment.c_doctype_id, min(uy_receiptbookgap.documentnostart) as documentnostart, max(uy_receiptbookgap.documentnoend) as documentnoend, sum(uy_receiptbookgap.documentnoend-uy_receiptbookgap.documentnostart+1) as counter " +
					"FROM   uy_receiptbookgap "+
					"		LEFT JOIN c_payment ON c_payment.c_payment_id=uy_receiptbookgap.c_payment_id "+
					"WHERE  uy_receiptbookgap.uy_receiptbookcontrol_id=? "+
					"GROUP BY c_payment.c_doctype_id";

		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		//  Delete previous records
		//OpenUp Nicolas Sarlabos #721 2/11/2011 se corrige sql
		DB.executeUpdateEx("DELETE FROM uy_receiptbooktype WHERE uy_receiptbooktype.uy_receiptbookcontrol_id="+this.getUY_ReceiptBookControl_ID(),get_TrxName());
		//fin OpenUp Nicolas Sarlabos #721 2/11/2011 
		try {
			pstmt=DB.prepareStatement (SQL,get_TrxName());
			pstmt.setInt(1,this.getUY_ReceiptBookControl_ID());
			
			
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
//OpenUp M.R. 21-09-2011 Issue#721 se implementan metodos desde el modelo al impementar DocAction en la clase
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	public boolean completeIt(String action) throws Exception {


		this.load(get_TrxName());
		if (this.getControl() == 0) {

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String description, sql, error = "";
			error = "Aun existen documentos sin descripción de nulidad";
			sql = "Select description from uy_receiptbooknotused where uy_receiptbookcontrol_id = " + this.getUY_ReceiptBookControl_ID();
			
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


			this.setProcessed(true);
			this.saveEx();

			MReceiptBook receiptBook = this.getParent();
			if (!(receiptBook.isProcessed())) {
				receiptBook.setProcessed(true);
				receiptBook.saveEx();
				this.setDocStatus(DOCSTATUS_Completed);
			}

		this.setDocStatus(DOCSTATUS_Completed);
		this.setDocAction(DOCACTION_None);
		this.saveEx();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.setGaps();		
		this.setTypes();
		
		// Set counter and not used
		this.setCounter();
		this.setNotUsed();
		this.setDocStatus(DOCSTATUS_InProgress);
		this.setDocAction(DOCACTION_Complete);

		// Save before reloading
		this.saveEx();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return null;
	}
	//Fin OpenUp

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}
}
