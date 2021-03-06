package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.compiere.apps.AEnv;
import org.compiere.grid.ed.VLocator;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MLocator;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.Query;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

public class VCreateFromPOUI extends CreateFromPO implements ActionListener, VetoableChangeListener {

	private static final int WINDOW_CUSTOMER_RETURN = 53097;

	private static final int WINDOW_RETURN_TO_VENDOR = 53098;

	private VCreateFromDialog dialog;

	public VCreateFromPOUI(GridTab mTab)
	{
		super(mTab);
		//OpenUp SBT 11/10/2015 Issue#5028 Se agrega variable de entorna necesaria al momento de obtener lineas de orden seleccionada
		Env.setContext(Env.getCtx(), "m_InOut_ID_OpUp", mTab.getRecord_ID());
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
	}   //  VCreateFrom
	
	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	//
	private JLabel bPartnerLabel = new JLabel();
	private VLookup bPartnerField;
	
	private JLabel orderLabel = new JLabel();
	private JComboBox orderField = new JComboBox();
	
   	private JCheckBox sameWarehouseCb = new JCheckBox();
	private JLabel locatorLabel = new JLabel();
	private VLocator locatorField = new VLocator();
	
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
		
		//  load Locator
		MLocatorLookup locator = new MLocatorLookup(Env.getCtx(), p_WindowNo);
		locatorField = new VLocator ("M_Locator_ID", true, false, true,	locator, p_WindowNo);
		sameWarehouseCb.setSelected(true);
		sameWarehouseCb.addActionListener(this);

		initBPartner(false);
		bPartnerField.addVetoableChangeListener(this);
				
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
    	boolean isRMAWindow = ((getGridTab().getAD_Window_ID() == WINDOW_RETURN_TO_VENDOR) || (getGridTab().getAD_Window_ID() == WINDOW_CUSTOMER_RETURN)); 
    	
    	bPartnerLabel.setText(Msg.getElement(Env.getCtx(), "C_BPartner_ID"));
    	orderLabel.setText("Orden de Compra");
    	locatorLabel.setText(Msg.translate(Env.getCtx(), "M_Locator_ID"));
    	sameWarehouseCb.setText(Msg.getMsg(Env.getCtx(), "FromSameWarehouseOnly", true));
    	sameWarehouseCb.setToolTipText(Msg.getMsg(Env.getCtx(), "FromSameWarehouseOnly", false));
    	      
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);

    	parameterStdPanel.add(bPartnerLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	if (bPartnerField != null)
    		parameterStdPanel.add(bPartnerField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	if (! isRMAWindow) {
        	parameterStdPanel.add(orderLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        	parameterStdPanel.add(orderField,  new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
        			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

        
    	}
    	
    	parameterStdPanel.add(locatorLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(locatorField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(sameWarehouseCb, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
      
    	
    }   //  jbInit

	/*************************************************************************/

	private boolean 	m_actionActive = false;
	
	/**
	 *  Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.config("Action=" + e.getActionCommand());
		
		if (m_actionActive)
			return;
		m_actionActive = true;
		log.config("Action=" + e.getActionCommand());
		//  Order
		if (e.getSource().equals(orderField))
		{
			KeyNamePair pp = (KeyNamePair)orderField.getSelectedItem();
			int C_Order_ID = 0;
			if (pp != null)
				C_Order_ID = pp.getKey();
				loadOrder(C_Order_ID, false, locatorField.getValue()!=null?((Integer)locatorField.getValue()).intValue():0);
		}
		
		else if (e.getSource().equals(sameWarehouseCb))
		{
			initBPOrderDetails(((Integer)bPartnerField.getValue()).intValue(), false);
		}
	
		m_actionActive = false;
	}   //  actionPerformed

	/**
	 *  Change Listener
	 *  @param e event
	 */
	@Override
	public void vetoableChange (PropertyChangeEvent e)
	{
		log.config(e.getPropertyName() + "=" + e.getNewValue());

		//  BPartner - load Order/Invoice/Shipment
		if (e.getPropertyName().equals("C_BPartner_ID"))
		{
			int C_BPartner_ID = ((Integer)e.getNewValue()).intValue();
			initBPOrderDetails (C_BPartner_ID, false);
		}
		dialog.tableChanged(null);
	}   //  vetoableChange
	
	/**************************************************************************
	 *  Load BPartner Field
	 *  @param forInvoice true if Invoices are to be created, false receipts
	 *  @throws Exception if Lookups cannot be initialized
	 */
	protected void initBPartner (boolean forInvoice) throws Exception
	{
		//  load BPartner
		int AD_Column_ID = 3499;        //  C_Invoice.C_BPartner_ID
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		bPartnerField = new VLookup ("C_BPartner_ID", true, false, true, lookup);
		//
		int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
		bPartnerField.setValue(new Integer(C_BPartner_ID));

		//  initial loading
		initBPOrderDetails(C_BPartner_ID, forInvoice);
	}   //  initBPartner

	/**
	 *  Load PBartner dependent Order/Invoice/Shipment Field.
	 *  @param C_BPartner_ID BPartner
	 *  @param forInvoice for invoice
	 */
	@Override
	protected void initBPOrderDetails (int C_BPartner_ID, boolean forInvoice)
	{
		log.config("C_BPartner_ID=" + C_BPartner_ID);
		KeyNamePair pp = new KeyNamePair(0,"");
		//  load PO Orders - Closed, Completed
		orderField.removeActionListener(this);
		orderField.removeAllItems();
		orderField.addItem(pp);
		
		ArrayList<KeyNamePair> list = loadOrderData(C_BPartner_ID, forInvoice, sameWarehouseCb.isSelected());
		for(KeyNamePair knp : list)
			orderField.addItem(knp);
		
		orderField.setSelectedIndex(0);
		orderField.addActionListener(this);
		dialog.pack();
		
	}   //  initBPartnerOIS
	
	/**
	 *  Load Data - Order
	 *  @param C_Order_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 *  @param M_Locator_ID
	 */
	protected void loadOrder (int C_Order_ID, boolean forInvoice, int M_Locator_ID)
	{
		loadTableOIS(getOrderData(C_Order_ID, forInvoice, M_Locator_ID));
	}   //  LoadOrder
	
		
	/**
	 *  Load Order/Invoice/Shipment data into Table
	 *  @param data data
	 */
	protected void loadTableOIS (Vector<?> data)
	{
		//  Remove previous listeners
		dialog.getMiniTable().getModel().removeTableModelListener(dialog);
		//  Set Model
		DefaultTableModel model = new DefaultTableModel(data, getOISColumnNames());
		model.addTableModelListener(dialog);
		dialog.getMiniTable().setModel(model);
		// 
		
		configureMiniTable(dialog.getMiniTable());
	}   //  loadOrder
	
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
		// Set custom cell editor to enable editing locators
		MiniTable swingTable = (MiniTable) miniTable;
		TableColumn col = swingTable.getColumn(3);
		col.setCellEditor(new InnerLocatorTableCellEditor());
	}

	/**
	 * Custom cell editor for setting locator from minitable.
	 *
	 * @author Daniel Tamm
	 *
	 */
	public class InnerLocatorTableCellEditor extends AbstractCellEditor implements TableCellEditor {

		/**
		 *
		 */
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

	}
	
}
