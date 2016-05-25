/**
 * VCreateFromPPOrderUI.java
 * 06/04/2011
 */
package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.table.DefaultTableModel;

import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.GridTab;
import org.compiere.model.MColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_M_Product;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.X_PP_Order;

/**
 * OpenUp.
 * VCreateFromPPOrderUI
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 06/04/2011
 */
public class VCreateFromPPOrderUI extends CreateFromPPOrder implements
		ActionListener, VetoableChangeListener {


	private VCreateFromDialog dialog;
	private int p_WindowNo;
	private CLogger log = CLogger.getCLogger(getClass());
	
	protected CLabel labelProducto = new CLabel("Producto");
	protected VLookup lookupProducto;
	
	protected CLabel labelDocType = new CLabel("Tipo de Documento");
	protected VLookup lookupDocType;

	protected CLabel labelPPOrder = new CLabel("Orden de Proceso");
	protected VLookup lookupPPOrder;
	
	private CLabel dateFromLabel = new CLabel("Fecha Orden de Proceso");
	protected VDate dateFromField = new VDate("DateFrom", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateFrom"));
	private CLabel dateToLabel = new CLabel("-");
	protected VDate dateToField = new VDate("DateTo", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateTo"));

	
	
	/**
	 * Constructor
	 * @param gridTab
	 */
	public VCreateFromPPOrderUI(GridTab gridTab) {

		super(gridTab);

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
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		log.config("Action=" + arg0.getActionCommand());
		if ( arg0.getActionCommand().equals(ConfirmPanel.A_REFRESH) )
		{
			Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
			loadData();
			dialog.tableChanged(null);
			Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		}
	}

	/* (non-Javadoc)
	 * @see java.beans.VetoableChangeListener#vetoableChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
	}

	
	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		super.dynInit();
		
		//Refresh button
		CButton refreshButton = ConfirmPanel.createRefreshButton(false);
		refreshButton.setMargin(new Insets (1, 10, 0, 10));
		refreshButton.setDefaultCapable(true);
		refreshButton.addActionListener(this);
		dialog.getConfirmPanel().addButton(refreshButton);
		dialog.getRootPane().setDefaultButton(refreshButton);
				
		if (getGridTab().getValue(X_C_Order.COLUMNNAME_C_Order_ID) == null)
		{
			ADialog.error(0, dialog, "SaveErrorRowNotFound");
			return false;
		}
		
		dialog.setTitle(getTitle());
		
		lookupProducto = new VLookup(X_M_Product.COLUMNNAME_M_Product_ID, false, false, true,
									 MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 
									 MColumn.getColumn_ID(X_M_Product.Table_Name, X_M_Product.COLUMNNAME_M_Product_ID), DisplayType.Search));
		labelProducto.setLabelFor(lookupProducto);
		
		//OpenUp Nicolas Garcia 15/05/2011 Issue #632
		lookupDocType = new VLookup(X_PP_Order.COLUMNNAME_C_DocType_ID, false, false, true,
			    					MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 
			    					MColumn.getColumn_ID(X_PP_Order.Table_Name,X_PP_Order.COLUMNNAME_C_DocType_ID), DisplayType.Table));
		labelDocType.setLabelFor(lookupDocType);
		//fin Niclas

		lookupPPOrder = new VLookup(X_PP_Order.COLUMNNAME_PP_Order_ID, false, false, true,
								    MLookupFactory.get (Env.getCtx(), p_WindowNo, 0,	
								  MColumn.getColumn_ID("UY_ValidacionCampos", "pp_order_ventas_id"), DisplayType.Table));	
								    /*MColumn.getColumn_ID(X_PP_Order.Table_Name, X_PP_Order.COLUMNNAME_PP_Order_ID), DisplayType.Table));*/
		
		
		
		labelPPOrder.setLabelFor(lookupPPOrder);
		
		loadData();
		
		return true;
	}   //  dynInit

	
	/**
	 *  Static Init.
	 *  @throws Exception
	 */
    private void jbInit() throws Exception
    {

    	dateFromLabel.setLabelFor(dateFromField);
    	dateFromField.setToolTipText(Msg.translate(Env.getCtx(), "DateFrom"));
    	dateToLabel.setLabelFor(dateToField);
    	dateToField.setToolTipText(Msg.translate(Env.getCtx(), "DateTo"));

    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	
    	CPanel parameterDataPanel = new CPanel();
    	parameterDataPanel.setLayout(new GridBagLayout());
    	parameterPanel.add(parameterDataPanel, BorderLayout.CENTER);
    	
    	parameterDataPanel.add(labelProducto, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(lookupProducto, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	//OpenUp Nicolas Garcia 15/05/2011 Issue #632
    	parameterDataPanel.add(labelDocType, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(lookupDocType, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0)); 
    	
    	//fin Nicolas
    	
    	parameterDataPanel.add(labelPPOrder, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(lookupPPOrder, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0)); 

    	parameterDataPanel.add(dateFromLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(dateFromField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterDataPanel.add(dateToLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(dateToField, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    }   //  jbInit

    
	protected void loadData()
	{
		//OpenUp Nicolas Garcia 15/05/2011 Issue #632
		loadTableOIS(getData(lookupProducto.getValue(), lookupPPOrder.getValue(),lookupDocType.getValue(), dateFromField.getValue(), dateToField.getValue())); 
		//fin Nicolas
	}
	
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
	}

	public void showWindow()
	{
		dialog.setVisible(true);
	}
	
	public void closeWindow()
	{
		dialog.dispose();
	}

}
