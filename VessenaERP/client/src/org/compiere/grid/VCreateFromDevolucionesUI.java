/**
 * VCreateFromReservaUI.java
 * 01/12/2010
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
import org.compiere.grid.ed.VCheckBox;
import org.compiere.model.GridTab;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * OpenUp.
 * VCreateFromReservaUI
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 01/12/2010
 */
public class VCreateFromDevolucionesUI extends CreateFromDevoluciones implements
		ActionListener, VetoableChangeListener {
	//OpenUp Nicolas Sarlabos issue #886 06/10/2011
	protected VCheckBox checkDevolATR = new VCheckBox();
	protected CLabel labelDevolATR = new CLabel("Solo Devoluciones de ATR");
	//fin OpenUp #886
	private VCreateFromDialog dialog;

	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	
	/**
	 * Constructor
	 * @param gridTab
	 */
	public VCreateFromDevolucionesUI(GridTab gridTab) {
		
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
	public void vetoableChange(PropertyChangeEvent arg0)
			throws PropertyVetoException {
		// TODO Auto-generated method stub

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
		
		//Refresh button
		CButton refreshButton = ConfirmPanel.createRefreshButton(false);
		refreshButton.setMargin(new Insets (1, 10, 0, 10));
		refreshButton.setDefaultCapable(true);
		refreshButton.addActionListener(this);
		dialog.getConfirmPanel().addButton(refreshButton);
		dialog.getRootPane().setDefaultButton(refreshButton);
				
		if (getGridTab().getValue("UY_AsignaTransporteHdr_ID") == null)
		{
			ADialog.error(0, dialog, "SaveErrorRowNotFound");
			return false;
		}
		
		dialog.setTitle(getTitle());
		//OpenUp Nicolas Sarlabos issue #886 06/10/2011
		checkDevolATR.setSelected(true);
		//fin OpenUp #886
		loadData();
		
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
    	//OpenUp Nicolas Sarlabos issue #886 06/10/2011 
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	
    	CPanel parameterDataPanel = new CPanel();
    	parameterDataPanel.setLayout(new GridBagLayout());
    	parameterPanel.add(parameterDataPanel, BorderLayout.CENTER);
    	    	
    	parameterDataPanel.add(labelDevolATR, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(checkDevolATR, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	//fin OpenUp #886
    	
    	
    }   //  jbInit

	protected void loadData()
	{
		loadTableOIS(getData(checkDevolATR)); //OpenUp Nicolas Sarlabos issue #886 06/10/2011 
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
