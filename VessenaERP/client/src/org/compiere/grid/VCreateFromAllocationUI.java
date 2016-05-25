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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.compiere.apps.AEnv;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MLocator;
import org.compiere.model.Query;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

public class VCreateFromAllocationUI extends CreateFromAllocation implements ActionListener, VetoableChangeListener {

	private VCreateFromDialog dialog;

	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	public VCreateFromAllocationUI(GridTab gridTab) {
		super(gridTab);
		log.info(getGridTab().toString());
		
		dialog = new VCreateFromDialog(this, getGridTab().getWindowNo(), true);
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
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
	
	@ Override
	public void actionPerformed(ActionEvent e) {
		log.config("Action=" + e.getActionCommand());
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
		// TODO Auto-generated method stub
		dialog.tableChanged(null);
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
		
		// Get C_BPartner_ID from the current tab
		int c_bpartner_id = ((Integer)getGridTab().getValue("C_BPartner_ID")).intValue();
		int c_currency_id =((Integer)getGridTab().getValue("C_Currency_ID")).intValue();
		String dateacct =((Timestamp)getGridTab().getValue("DateAcct")).toString();
		
		// Load data
		loadData(c_bpartner_id,c_currency_id,dateacct);
		
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

	protected void loadData(int c_bpartner_id,int c_currency_id,String dateacct)
	{
		loadTableOIS(getData(c_bpartner_id,c_currency_id,dateacct));
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
				BigDecimal amount=(BigDecimal) dialog.getMiniTable().getValueAt(i,6);
				if (amount.equals(BigDecimal.ZERO)) {
					BigDecimal openAmt=(BigDecimal) dialog.getMiniTable().getValueAt(i,5);
					if (openAmt!=null) {
						amount=openAmt;
						dialog.getMiniTable().setValueAt((Object) amount,i,6);
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
		TableColumn col = swingTable.getColumn(5);
		
		// TODO: used just to review
		col.setCellEditor(new InnerTableCellEditor());
		
		// col.setCellEditor(new InnerLocatorTableCellEditor());
	}

	/**
	 * Custom cell editor for setting locator from minitable.
	 *
	 * @author OpenUp FL
	 *
	 */
	public class InnerTableCellEditor extends AbstractCellEditor implements TableCellEditor {

		/**
		 *
		 */
		private static final long serialVersionUID = -7143484413792778213L;

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
	
	
	// TODO: used just to review
	/*public class InnerLocatorTableCellEditor extends AbstractCellEditor implements TableCellEditor {

		private static final long serialVersionUID = -7143484413792778213L;
		KeyNamePair currentValue;
		JTextField 	editor;

		public Object getCellEditorValue() {
			String locatorValue = editor.getText();
			MLocator loc = null;
			try {
				// Lookup locator using value
				loc = new Query(Env.getCtx(), MLocator.Table_Name, "value=?", null)
									.setParameters(new Object[]{locatorValue})
									.setClient_ID()
									.first();
				// Set new keyNamePair for minitable
				currentValue = getLocatorKeyNamePair(loc.get_ID());

			} catch (Exception e) {
				String message = Msg.getMsg(Env.getCtx(), "Invalid") + " " + editor.getText();
				JOptionPane.showMessageDialog(null, message);
			}
			return(currentValue);

		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {

			currentValue = (KeyNamePair)value;
			editor = new JTextField();
			editor.setText(currentValue.getName());
			return(editor);

		}

	}*/


}
