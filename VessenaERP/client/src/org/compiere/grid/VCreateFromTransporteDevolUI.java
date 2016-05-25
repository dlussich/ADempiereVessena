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
import org.compiere.model.X_M_InOut;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.openup.model.MReserveFilter;
import org.openup.model.X_UY_Reserve_Filter;

public class VCreateFromTransporteDevolUI extends CreateFromTransporteDevol
implements ActionListener, VetoableChangeListener{
	
	private VCreateFromDialog dialog;

	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	protected CLabel labelCliente = new CLabel("Cliente");
	protected VLookup lookupCliente;

	protected CLabel labelDevol = new CLabel("Devolución");
	protected VLookup lookupDevol;
	
	private CLabel dateFromLabel = new CLabel("Fecha Devolución");
	protected VDate dateFromField = new VDate("DateFrom", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateFrom"));
	private CLabel dateToLabel = new CLabel("-");
	protected VDate dateToField = new VDate("DateTo", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateTo"));
	
	protected CLabel labelZonaRep1 = new CLabel("Zona Reparto (1)");
	protected VLookup lookUpZonaRep1;

	protected CLabel labelZonaRep2 = new CLabel("Zona Reparto (2)");
	protected VLookup lookUpZonaRep2;

	protected CLabel labelZonaRep3 = new CLabel("Zona Reparto (3)");
	protected VLookup lookUpZonaRep3;
	
	/**
	 * Constructor
	 * @param gridTab
	 */
	public VCreateFromTransporteDevolUI(GridTab gridTab) {

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
	public void actionPerformed(ActionEvent e) {
		log.config("Action=" + e.getActionCommand());
		if ( e.getActionCommand().equals(ConfirmPanel.A_REFRESH) )
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
		
		lookupCliente = new VLookup("C_BPartner_ID", false, false, true,
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 3499, DisplayType.Search));
		labelCliente.setLabelFor(lookupCliente);

		lookupDevol = new VLookup(X_M_InOut.COLUMNNAME_M_InOut_ID, false, false, true,
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, MColumn.getColumn_ID(X_M_InOut.Table_Name, X_M_InOut.COLUMNNAME_M_InOut_ID), DisplayType.Search));
		labelDevol.setLabelFor(lookupDevol);

		lookUpZonaRep1 = new VLookup(X_UY_Reserve_Filter.COLUMNNAME_uy_zonareparto_filtro1, false, false, true,
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, MColumn.getColumn_ID(MReserveFilter.Table_Name, X_UY_Reserve_Filter.COLUMNNAME_uy_zonareparto_filtro1), DisplayType.Table));
		labelZonaRep1.setLabelFor(lookUpZonaRep1);

		lookUpZonaRep2 = new VLookup(X_UY_Reserve_Filter.COLUMNNAME_uy_zonareparto_filtro2, false, false, true,
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, MColumn.getColumn_ID(MReserveFilter.Table_Name, X_UY_Reserve_Filter.COLUMNNAME_uy_zonareparto_filtro2), DisplayType.Table));
		labelZonaRep2.setLabelFor(lookUpZonaRep2);

		lookUpZonaRep3 = new VLookup(X_UY_Reserve_Filter.COLUMNNAME_uy_zonareparto_filtro3, false, false, true,
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, MColumn.getColumn_ID(MReserveFilter.Table_Name, X_UY_Reserve_Filter.COLUMNNAME_uy_zonareparto_filtro3), DisplayType.Table));
		labelZonaRep3.setLabelFor(lookUpZonaRep3);

		
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

    	dateFromLabel.setLabelFor(dateFromField);
    	dateFromField.setToolTipText(Msg.translate(Env.getCtx(), "DateFrom"));
    	dateToLabel.setLabelFor(dateToField);
    	dateToField.setToolTipText(Msg.translate(Env.getCtx(), "DateTo"));

    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	
    	CPanel parameterDataPanel = new CPanel();
    	parameterDataPanel.setLayout(new GridBagLayout());
    	parameterPanel.add(parameterDataPanel, BorderLayout.CENTER);
    	
    	parameterDataPanel.add(labelCliente, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(lookupCliente, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	parameterDataPanel.add(labelDevol, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(lookupDevol, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0)); 

    	parameterDataPanel.add(dateFromLabel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(dateFromField, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterDataPanel.add(dateToLabel, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(dateToField, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	
    	parameterDataPanel.add(labelZonaRep1, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(lookUpZonaRep1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	parameterDataPanel.add(labelZonaRep2, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(lookUpZonaRep2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	parameterDataPanel.add(labelZonaRep3, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterDataPanel.add(lookUpZonaRep3, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	
    }   //  jbInit

	protected void loadData()
	{
		loadTableOIS(getData(lookupCliente.getValue(), lookupDevol.getValue(), dateFromField.getValue(), dateToField.getValue(), 
						     lookUpZonaRep1.getValue(), lookUpZonaRep2.getValue(), lookUpZonaRep3.getValue()));
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
