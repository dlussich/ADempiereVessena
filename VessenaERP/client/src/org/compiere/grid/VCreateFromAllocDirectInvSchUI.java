/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Feb 10, 2016
*/
package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.compiere.apps.AEnv;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Payment;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * org.compiere.grid - VCreateFromAllocDirectInvSchUI
 * OpenUp Ltda. Issue #5036 
 * Description: Seleccion de documentos en afectacion directa por vencimientos
 * @author gabriel - Feb 10, 2016
*/
public class VCreateFromAllocDirectInvSchUI extends CreateFromAllocDirectInvSch
		implements ActionListener, VetoableChangeListener {


	private VCreateFromDialog dialog;
	private int p_WindowNo;
	private CLogger log = CLogger.getCLogger(getClass());
	
	
	/***
	 * Constructor.
	 * @param gridTab
	*/

	public VCreateFromAllocDirectInvSchUI(GridTab gridTab) {
		
		super(gridTab);
		
		log.info(getGridTab().toString());
		
		dialog = new VCreateFromDialog(this, getGridTab().getWindowNo(), true);
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit()) return;
			jbInit();
			setInitOK(true);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
		}
		AEnv.positionCenterWindow(Env.getWindow(p_WindowNo), dialog);
	}

	/* (non-Javadoc)
	 * @see java.beans.VetoableChangeListener#vetoableChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
		dialog.tableChanged(null);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		log.config("Action=" + e.getActionCommand());
	}

	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.config("");
		
		super.dynInit();
		
		dialog.setTitle(getTitle());

		int ad_client_id = 0;int c_bpartner_id = 0;int c_payment_id = 0;Timestamp dateTrx=null;
		String issotrx="";int c_currency_id =0; int c_invoice_id = 0;int c_currency_id2 = 0;
		
		//Obtengo el tipo de documento, para cargar los datos según la ventana de origen
		int c_docType_id = ((Integer)getGridTab().getValue("C_DocType_ID")).intValue();
		if (c_docType_id==0) 
				c_docType_id = ((Integer)getGridTab().getValue("C_DocTypeTarget_ID")).intValue();
		
		MDocType doc = new MDocType(Env.getCtx(), c_docType_id, null);
		if(doc.getValue().equalsIgnoreCase("vendorncdesc")){
			ad_client_id = ((Integer)getGridTab().getValue(X_C_Invoice.COLUMNNAME_AD_Client_ID)).intValue();
			c_bpartner_id = ((Integer)getGridTab().getValue(X_C_Invoice.COLUMNNAME_C_BPartner_ID)).intValue();
			c_payment_id = 0 ;
			c_invoice_id = ((Integer)getGridTab().getValue(X_C_Invoice.COLUMNNAME_C_Invoice_ID)).intValue();
			dateTrx =((Timestamp)getGridTab().getValue(X_C_Invoice.COLUMNNAME_DateInvoiced));
			issotrx = getGridTab().getValue(X_C_Invoice.COLUMNNAME_IsSOTrx).toString().trim();
			issotrx = (issotrx.equalsIgnoreCase("true")) ? "Y" : "N";
			c_currency_id = ((Integer)getGridTab().getValue(X_C_Invoice.COLUMNNAME_C_Currency_ID)).intValue();
			if((Integer)getGridTab().getValue("C_Currency2_ID")!=null){
				c_currency_id2 = ((Integer)getGridTab().getValue("C_Currency2_ID")).intValue();
			}
			//c_currency_id2 = ((Integer)getGridTab().getValue("C_Currency2_ID")).intValue();
		}else{
			ad_client_id = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_AD_Client_ID)).intValue();
			c_bpartner_id = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_BPartner_ID)).intValue();
			c_invoice_id = 0;
			c_payment_id = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_Payment_ID)).intValue();
			dateTrx =((Timestamp)getGridTab().getValue(X_C_Payment.COLUMNNAME_DateTrx));
			issotrx = getGridTab().getValue(X_C_Payment.COLUMNNAME_IsReceipt).toString().trim();
			issotrx = (issotrx.equalsIgnoreCase("true")) ? "Y" : "N";
			c_currency_id = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_Currency_ID)).intValue();
			if((Integer)getGridTab().getValue("C_Currency2_ID")!=null){
				c_currency_id2 = ((Integer)getGridTab().getValue("C_Currency2_ID")).intValue();
			}
		}
		
		// Load data
		loadData(ad_client_id, issotrx, c_bpartner_id, c_currency_id, c_currency_id2, dateTrx, c_payment_id, c_invoice_id);
		
		return true;
	}   //  dynInit
    
	/**
	 *  Static Init.
	 *  <pre>
	 *  parameterPanel
	 *      parameterBankPanel
	 *      parameterStdPanel
	 *          bPartner/order/invoice/shopment/licator Label/Field
	 *  dataPane
	 *  southPanel
	 *      confirmPanel
	 *      statusBar
	 *  </pre>
	 *  @throws Exception
	 */
    private void jbInit() throws Exception
    {
    	
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	
    	CPanel parameterBankPanel = new CPanel();
    	parameterBankPanel.setLayout(new GridBagLayout());
    	parameterPanel.add(parameterBankPanel, BorderLayout.CENTER);
    	
    }   //  jbInit

	protected void loadData(int ad_client_id, String issotrx, int c_bpartner_id, 
							int c_currency_id, int c_currency_id2, Timestamp dateTrx, int cPaymentID, int cInvoiceID)
	{
		loadTableOIS(getData(ad_client_id, issotrx, c_bpartner_id, dateTrx, c_currency_id, c_currency_id2, cPaymentID,cInvoiceID));
	}
	
	protected void loadTableOIS (Vector<?> data)
	{
		//  Remove previous listeners
		dialog.getMiniTable().getModel().removeTableModelListener(dialog);
		
		//  Set Model
		DefaultTableModel model = new DefaultTableModel(data, getOISColumnNames());
		model.addTableModelListener(dialog);
		dialog.getMiniTable().setModel(model);
		
		configureMiniTable(dialog.getMiniTable());
	}
	
	/**
	 *  List total amount
	 */
	public void info()
	{
		
		DecimalFormat format = DisplayType.getNumberFormat(DisplayType.Amount);

		BigDecimal total = BigDecimal.ZERO;
		int rows = dialog.getMiniTable().getRowCount();
		int count = 0;
		for (int i = 0; i < rows; i++)
		{
			//  For selected lines
			if (((Boolean)dialog.getMiniTable().getValueAt(i, 0)).booleanValue()) {

				// Get the amount and set it to the open amount if equals 0
				BigDecimal amount=(BigDecimal) dialog.getMiniTable().getValueAt(i,9);
				if (amount.equals(BigDecimal.ZERO)) {
					BigDecimal openAmt=(BigDecimal) dialog.getMiniTable().getValueAt(i,8);
					if (openAmt!=null) {
						amount=openAmt;
						dialog.getMiniTable().setValueAt((Object) amount,i,9);
					}
				} 
				
				total = total.add((BigDecimal) amount);
				
				count++;
			}
		}
		dialog.setStatusLine(count, Msg.getMsg(Env.getCtx(), "Sum") + "  " + format.format(total));
		
		 
		
	}   //  infoStatement
	

	
	public void showWindow()
	{
		dialog.setVisible(true);
	}
	
	public void closeWindow()
	{
		dialog.dispose();
	}
	
	@Override
	protected void configureMiniTable(IMiniTable miniTable) {
		super.configureMiniTable(miniTable);
		
		// Set custom cell editor to enable amount
		MiniTable swingTable = (MiniTable) miniTable;
		TableColumn col = swingTable.getColumn(8);
		
		col.setCellEditor(new AllocDirectInvSchAmountCellEditor());
		
	}

	/**
	 * Custom cell editor for setting locator from minitable.
	 *
	 * @author OpenUp FL
	 *
	 */
	public class AllocDirectInvSchAmountCellEditor extends AbstractCellEditor implements TableCellEditor {

		
		/**
		 * 
		 */
		private static final long serialVersionUID = -8232561165448873816L;
		JTextField 	editor;

		public Object getCellEditorValue() {
			// Cast the text field as the value
			BigDecimal value = new BigDecimal(editor.getText());
			return(value);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			// Create a text field set its value an keep it as a property
			this.editor = new JTextField();
			this.editor.setText((String) value);
			return(this.editor);
		}

	}
	
}
