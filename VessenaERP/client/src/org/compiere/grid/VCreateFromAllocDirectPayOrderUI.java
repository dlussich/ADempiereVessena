/**
 * @author OpenUp SBT Issue#  16/11/2015 10:39:00
 */
package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.table.DefaultTableModel;

import org.compiere.apps.AEnv;
import org.compiere.model.GridTab;
import org.compiere.model.X_C_Payment;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * @author OpenUp SBT Issue #5052  16/11/2015 10:39:00
 *
 */
public class VCreateFromAllocDirectPayOrderUI extends CreateFromAllocDirectPayOrder implements ActionListener,
VetoableChangeListener{

	private VCreateFromDialog dialog;
	private int p_WindowNo;
	private CLogger log = CLogger.getCLogger(getClass());
	
	public VCreateFromAllocDirectPayOrderUI(GridTab gridTab) {
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
	
	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		dialog.tableChanged(null);
		
	}

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
		//Cliente
		int ad_client_id = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_AD_Client_ID)).intValue();
		//Proveedor
		int c_bpartner_id = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_BPartner_ID)).intValue();
		//ID Orden de pago
		int c_payment_id = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_Payment_ID)).intValue();
		//Fecha de orden de pago
		Timestamp dateTrx =((Timestamp)getGridTab().getValue(X_C_Payment.COLUMNNAME_DateTrx));
		//
		String issotrx = getGridTab().getValue(X_C_Payment.COLUMNNAME_IsReceipt).toString().trim();
		issotrx = (issotrx.equalsIgnoreCase("true")) ? "Y" : "N";
		//Moneda
		int c_currency_id = ((Integer)getGridTab().getValue(X_C_Payment.COLUMNNAME_C_Currency_ID)).intValue();
		
		// Load data
		loadData(ad_client_id, issotrx, c_bpartner_id, c_currency_id, dateTrx, c_payment_id);
		
		return true;
	}   //  dynInit

	/**
	 * 
	 * @author OpenUp SBT Issue#  16/11/2015 11:13:00
	 * @param ad_client_id
	 * @param issotrx
	 * @param c_bpartner_id
	 * @param c_currency_id
	 * @param dateTrx
	 * @param cPaymentID
	 */
	protected void loadData(int ad_client_id, String issotrx, int c_bpartner_id, 
			int c_currency_id, Timestamp dateTrx, int cPaymentID)
	{
		loadTableOIS(getData(ad_client_id, issotrx, c_bpartner_id, dateTrx, c_currency_id, cPaymentID));
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
	 * 
	 * @author OpenUp SBT Issue#  16/11/2015 11:41:46
	 * @throws Exception
	 */
	private void jbInit() throws Exception
    {
    	
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	
    	CPanel parameterBankPanel = new CPanel();
    	parameterBankPanel.setLayout(new GridBagLayout());
    	parameterPanel.add(parameterBankPanel, BorderLayout.CENTER);
    	
    }   //  jbInit
	
	public void showWindow()
	{
		dialog.setSize(650, 450);
		dialog.setVisible(true);
	}
	
	public void closeWindow()
	{
		dialog.dispose();
	}
}
