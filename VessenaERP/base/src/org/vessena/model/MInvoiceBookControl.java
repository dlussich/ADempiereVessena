/**
 * MInvoiceBook.java
 * 29/03/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.DB;
import org.openup.model.MInvoiceBook;
import org.openup.model.MInvoiceBookGap;
import org.openup.model.MInvoiceBookType;

/**
 * OpenUp.
 * MCheckBook
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/03/2011
 */
public class MInvoiceBookControl extends X_UY_InvoiceBookControl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836398863738909053L;
	private boolean repiteNro = false;
	private MInvoiceBook invoiceBook = null; //OpenUp. Nicolas Sarlabos. 04/02/2013. Agrego variable global.

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InvoiceBook_ID
	 * @param trxName
	 */
	public MInvoiceBookControl(Properties ctx, int UY_InvoiceBook_ID, String trxName) {
		super(ctx, UY_InvoiceBook_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoiceBookControl(Properties ctx, ResultSet rs, String trxName) {
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
		MInvoiceBook invoiceBook=this.getParent();
		
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
		if (this.getDateStart().compareTo(invoiceBook.getDateStart())<0) {
			throw new IllegalArgumentException("Fecha de inicio del control anterior a la fecha de inicio de uso de libreta");				// TODO: translate
		}

		// Control finish date before control start date
		if (this.getDateFinish().compareTo(this.getDateStart())<0) {
			throw new IllegalArgumentException("Fecha de fin de control debe ser superior a "+this.getDateStart());							// TODO: translate
		}
		
		// Dates outside due date
		if ((this.getDateStart().compareTo(invoiceBook.getDueDate())>0)&&(this.getDateFinish().compareTo(invoiceBook.getDueDate())>0)) {
			throw new IllegalArgumentException("Rango de fechas del control posterior a la fecha de vencimietno de la libreta");			// TODO: translate
		}

		// Numbers controls
		// If the start is not set, set it with the default
		if (this.getDocumentNoStart()==0) {
			this.setDocumentNoStart(this.getDefaultDocumentNoStart());
		}
		
		// Start number outside range
		if (!((this.getDocumentNoStart()>=invoiceBook.getDocumentNoStart())&&(this.getDocumentNoStart()<=invoiceBook.getDocumentNoEnd()))) {
			throw new IllegalArgumentException("Numero de inicio fuera de rango de numeracion de libreta");									// TODO: translate
		}
		
		// End number must be great or equlas the start
		if (!(this.getDocumentNoEnd()>this.getDocumentNoStart())) {
			throw new IllegalArgumentException("Numero de fin debe ser superior a "+this.getDocumentNoStart());		// TODO: translate
		}
		
		// End number outside range
		if (!((this.getDocumentNoEnd()>=invoiceBook.getDocumentNoStart())&&(this.getDocumentNoEnd()<=invoiceBook.getDocumentNoEnd()))) {
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
		
		int notProcessed=DB.getSQLValueEx(get_TrxName(),"SELECT count(*) FROM uy_invoicebookcontrol WHERE processed='N' AND uy_invoicebook_id=? AND uy_invoicebookcontrol_id<>?",this.getUY_InvoiceBook_ID(),this.getUY_InvoiceBookControl_ID());
		
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
		
		Timestamp defaultDateStart=DB.getSQLValueTSEx(get_TrxName(),"SELECT max(uy_invoicebookcontrol.datefinish) + interval '1 day' FROM uy_invoicebookcontrol WHERE uy_invoicebook_id="+this.getUY_InvoiceBook_ID()+" AND uy_invoicebookcontrol_id<>"+this.getUY_InvoiceBookControl_ID());
		
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
		
		int defaultDocumentNoStart=DB.getSQLValueEx(get_TrxName(),"SELECT max(uy_invoicebookcontrol.documentnoend)+1 FROM uy_invoicebookcontrol WHERE uy_invoicebook_id="+this.getUY_InvoiceBook_ID()+" AND uy_invoicebookcontrol_id<>"+this.getUY_InvoiceBookControl_ID());
		
		// If not default start return the parent start, otherwise the number should be incremented
		if (defaultDocumentNoStart<=0) {
			defaultDocumentNoStart=this.getParent().getDocumentNoStart();
		}  

		return(defaultDocumentNoStart);
	}

	/**
	 * Get the invoice book, the parent
	 */
	public MInvoiceBook getParent()
	{
		return(new MInvoiceBook(getCtx(),getUY_InvoiceBook_ID(), get_TrxName()));
	}	//	getParent
	
	/**
	 * Process the control
	 * @throws Exception 
	 */
	public boolean processIt() throws Exception {
		
		// Get the parent, it will be used many times
		//OpenUp. Nicolas Sarlabos. 04/02/2013. Obtengo libreta de facturas y seteo si es con vias de igual numeracion.
		invoiceBook=this.getParent();
		repiteNro = invoiceBook.isUY_RepiteNumero();
		//Fin OpenUp.

		// set gaps and types
		this.setGaps();		
		this.setTypes();
		
		// Set counter and not used
		this.setCounter();
		this.setNotUsed();
		
		// Save before reloading
		this.saveEx();

		// Reload the record to update virtual fields
		this.load(get_TrxName());
		if (this.getControl()==0) {
			this.setProcessed(true);
			this.saveEx();
			
			//MInvoiceBook invoiceBook=this.getParent();
			if (!(invoiceBook.isProcessed())) {
				invoiceBook.setProcessed(true);
				invoiceBook.saveEx();
			}
		}

		return(true);
	}
	
	/**
	 * Set the counter
	 */
	public void setCounter() {
		
		int counter=DB.getSQLValueEx(get_TrxName(),"SELECT sum(uy_invoicebooktype.counter) FROM uy_invoicebooktype WHERE uy_invoicebooktype.uy_invoicebookcontrol_id="+this.getUY_InvoiceBookControl_ID());
		if (counter>0) {
			//OpenUp. Nicolas Sarlabos. 04/02/2013. Si la libreta es con vias de igual numeracion, el contador se multiplica por la cant. de vias.
			if(repiteNro){
				this.setCounter(counter*(invoiceBook.getCopies()));
			} else this.setCounter(counter);
			//Fin OpenUp.
		}
		else {
			this.setCounter(0);
		}
	}

	/**
	 * Set not used
	 */
	public void setNotUsed() {

		int notUsed=DB.getSQLValueEx(get_TrxName(),"SELECT count(uy_invoicebooknotused.*) FROM uy_invoicebooknotused WHERE uy_invoicebooknotused.uy_invoicebookcontrol_id="+this.getUY_InvoiceBookControl_ID());
		if (notUsed>0) {
			//OpenUp. Nicolas Sarlabos. 04/02/2013. Si la libreta es con vias de igual numeracion, el contador de no usados se multiplica por la cant. de vias.
			if(repiteNro){
				this.setNotUsed(notUsed*(invoiceBook.getCopies()));
			} else this.setNotUsed(notUsed);
			//Fin OpenUp.
		}
		else {
			this.setNotUsed(0);
		}
	}

	/**
	 * Add gaps
	 */
	public void setGaps() throws Exception {
		//OpenUp. Nicolas Sarlabos. 29/07/2011. #715
		//Es necesario crear campo "datetimeinvoiced" para poder diferenciar los documentos
		//de un día tomando en cuenta la hora de la facturación, ya que el campo "dateinvoiced" no contiene la hora
		String SQL= "SELECT c_invoice.c_invoice_id " +
					"FROM   c_invoice "+
					"WHERE  c_invoice.docstatus IN('CO','CL','VO') AND c_invoice.issotrx='Y' AND c_invoice.datetimeinvoiced>=? AND c_invoice.datetimeinvoiced<=? "+
					"ORDER BY c_invoice.datetimeinvoiced,c_invoice.documentno";
		//Fin OpenUp

		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		//  Delete previous records
		//OpenUp. Nicolas Sarlabos. 22/07/2011. #715
		DB.executeUpdateEx("DELETE uy_invoicebooktype WHERE uy_invoicebooktype.uy_invoicebookcontrol_id="+this.getUY_InvoiceBookControl_ID(),get_TrxName());
		//Fin OpenUp
		DB.executeUpdateEx("DELETE uy_invoicebookgap WHERE uy_invoicebookgap.uy_invoicebookcontrol_id="+this.getUY_InvoiceBookControl_ID(),get_TrxName());
		
		try {
			pstmt=DB.prepareStatement (SQL,get_TrxName());
			pstmt.setTimestamp(1,this.getDateStart());
			pstmt.setTimestamp(2,this.getDateFinish());

			//MInvoiceBook invoiceBook=this.getParent();
			
			int documentNo=this.getDocumentNoStart();
			int copies=invoiceBook.getCopies();
			int UY_InvoiceBookControl_ID=this.getUY_InvoiceBookControl_ID();
			
			rs=pstmt.executeQuery();
			while (rs.next()) {
				
				MInvoiceBookGap invoiceBookGap=new MInvoiceBookGap(getCtx(),0,get_TrxName());
				invoiceBookGap.setUY_InvoiceBookControl_ID(UY_InvoiceBookControl_ID);
				invoiceBookGap.setC_Invoice_ID(rs.getInt(1));
				invoiceBookGap.setDocumentNoStart(documentNo);
				//OpenUp. Nicolas Sarlabos. 04/02/2013. Si la libreta es con vias de igual numeracion, el nro final es el mismo que el inicial.
				//Si no son de igual numeracion, el nro final es = (inicial + copias - 1)
				if(repiteNro){
					invoiceBookGap.setDocumentNoEnd(documentNo);
				} else invoiceBookGap.setDocumentNoEnd(documentNo+copies-1);
				
				invoiceBookGap.saveEx();
				//Si la libreta es con vias de igual numeracion, se obtiene el siguiente nro sumando 1
				//Si no son de igual numeracion, se obtiene sumandole la cant de copias
				if(repiteNro){
					documentNo+=1;					
				} else documentNo+=copies;
				//Fin OpenUp. 04/03/2013
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

	/**
	 * Add types
	 */
	public void setTypes() throws Exception {
		
		String SQL= "SELECT c_invoice.c_doctype_id, min(uy_invoicebookgap.documentnostart) as documentnostart, max(uy_invoicebookgap.documentnoend) as documentnoend, sum(uy_invoicebookgap.documentnoend-uy_invoicebookgap.documentnostart+1) as counter " +
					"FROM   uy_invoicebookgap "+
					"		LEFT JOIN c_invoice ON c_invoice.c_invoice_id=uy_invoicebookgap.c_invoice_id "+
					"WHERE  uy_invoicebookgap.uy_invoicebookcontrol_id=? "+
					"GROUP BY c_invoice.c_doctype_id";

		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		//  Delete previous records
		DB.executeUpdateEx("DELETE uy_invoicebooktype WHERE uy_invoicebooktype.uy_invoicebookcontrol_id="+this.getUY_InvoiceBookControl_ID(),get_TrxName());
		
		try {
			pstmt=DB.prepareStatement (SQL,get_TrxName());
			pstmt.setInt(1,this.getUY_InvoiceBookControl_ID());
			
			int UY_InvoiceBookControl_ID=this.getUY_InvoiceBookControl_ID();

			rs=pstmt.executeQuery();
			while (rs.next()) {
				
				MInvoiceBookType invoiceBookType=new MInvoiceBookType(getCtx(),0,get_TrxName());
				invoiceBookType.setUY_InvoiceBookControl_ID(UY_InvoiceBookControl_ID);
				invoiceBookType.setC_DocType_ID(rs.getInt(1));
				invoiceBookType.setDocumentNoStart(rs.getInt(2));
				invoiceBookType.setDocumentNoEnd(rs.getInt(3));
				invoiceBookType.setCounter(rs.getInt(4));
				invoiceBookType.saveEx();
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
}
