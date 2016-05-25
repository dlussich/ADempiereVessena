/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/11/2012
 */
package org.compiere.grid;

import java.awt.BorderLayout;
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

import javax.swing.table.DefaultTableModel;
import org.compiere.apps.AEnv;
import org.compiere.model.GridTab;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.openup.model.X_UY_Resguardo;

/**
 * org.compiere.grid - VCreateFromRetentionInvUI
 * OpenUp Ltda. Issue #100 
 * Description: Seleccion de facturas para resguardos.
 * @author Gabriel Vila - 19/11/2012
 * @see
 */
public class VCreateFromRetentionInvUI extends CreateFromRetentionInvoice
		implements ActionListener, VetoableChangeListener {

	private VCreateFromDialog dialog;
	private int p_WindowNo;
	private CLogger log = CLogger.getCLogger(getClass());
	
	/**
	 * Constructor.
	 * @param gridTab
	 */
	public VCreateFromRetentionInvUI(GridTab gridTab) {

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
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/11/2012
	 * @see
	 * @param evt
	 * @throws PropertyVetoException
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {

		dialog.tableChanged(null);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/11/2012
	 * @see
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		log.config("Action=" + e.getActionCommand());
	}

	@Override
	public boolean dynInit() throws Exception {

		super.dynInit();
		
		dialog.setTitle(getTitle());

		int ad_client_id = ((Integer)getGridTab().getValue(X_UY_Resguardo.COLUMNNAME_AD_Client_ID)).intValue();
		int c_bpartner_id = ((Integer)getGridTab().getValue(X_UY_Resguardo.COLUMNNAME_C_BPartner_ID)).intValue();
		int uy_resguardo_id = ((Integer)getGridTab().getValue(X_UY_Resguardo.COLUMNNAME_UY_Resguardo_ID)).intValue();
		Timestamp dateTrx =((Timestamp)getGridTab().getValue(X_UY_Resguardo.COLUMNNAME_DateTrx));
		String issotrx = getGridTab().getValue(X_UY_Resguardo.COLUMNNAME_IsSOTrx).toString().trim();
		issotrx = (issotrx.equalsIgnoreCase("true")) ? "Y" : "N";
		int c_currency_id = ((Integer)getGridTab().getValue(X_UY_Resguardo.COLUMNNAME_C_Currency_ID)).intValue();

		// Load data
		loadData(ad_client_id, issotrx, c_bpartner_id, c_currency_id, dateTrx, uy_resguardo_id);
		
		return true;

	}

    private void jbInit() throws Exception
    {
    	
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	
    	CPanel parameterBankPanel = new CPanel();
    	parameterBankPanel.setLayout(new GridBagLayout());
    	parameterPanel.add(parameterBankPanel, BorderLayout.CENTER);
    	
    }   //  jbInit

	protected void loadData(int ad_client_id, String issotrx, int c_bpartner_id, 
							int c_currency_id, Timestamp dateTrx, int uyResguardoID)
	{
		loadTableOIS(getData(ad_client_id, issotrx, c_bpartner_id, dateTrx, c_currency_id, uyResguardoID));
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
				total = total.add((BigDecimal) amount);
				count++;
			}
		}
	
		dialog.setStatusLine(count, Msg.getMsg(Env.getCtx(), "Sum") + "  " + format.format(total));
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
