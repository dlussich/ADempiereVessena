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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.compiere.acct.Doc;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.GridTab;
import org.compiere.model.MColumn;
import org.compiere.model.MDocType;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.openup.model.MMediosPago;
import org.openup.model.MMovBancariosHdr;

public class VCreateFromBancoMedioPagoUI extends CreateFromBancoMedioPago
		implements ActionListener, VetoableChangeListener {

	private VCreateFromDialog dialog;

	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	private JLabel bankAccountLabel = new JLabel();
	protected VLookup bankAccountField;
	
	private CLabel checkNoLabel = new CLabel(Msg.translate(Env.getCtx(), "CheckNo"));
	protected CTextField checkNoField = new CTextField(10);
	
	private CLabel amtFromLabel = new CLabel(Msg.translate(Env.getCtx(), "PayAmt"));
	protected VNumber amtFromField = new VNumber("AmtFrom", false, false, true, DisplayType.Amount, Msg.translate(Env.getCtx(), "AmtFrom"));
	private CLabel amtToLabel = new CLabel("-");
	protected VNumber amtToField = new VNumber("AmtTo", false, false, true, DisplayType.Amount, Msg.translate(Env.getCtx(), "AmtTo"));
	
	protected CLabel BPartner_idLabel = new CLabel(Msg.translate(Env.getCtx(), "BPartner"));
	protected VLookup bPartnerLookup;
	
	private CLabel dateFromLabel = new CLabel(Msg.translate(Env.getCtx(), "DateTrx"));
	protected VDate dateFromField = new VDate("DateFrom", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateFrom"));
	private CLabel dateToLabel = new CLabel("-");
	protected VDate dateToField = new VDate("DateTo", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateTo"));

	private CLabel fecVencFromLabel = new CLabel("Fecha Vencimiento");
	protected VDate fecVencFromField = new VDate("fecVencFrom", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "fecVencFrom"));
	private CLabel fecVencToLabel = new CLabel("-");
	protected VDate fecVencToField = new VDate("fecVencTo", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "fecVencTo"));
	
	private JLabel estadoChequeLabel = new JLabel();
	protected VLookup estadoChequeField;
	
	public VCreateFromBancoMedioPagoUI(GridTab gridTab) {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		log.config("Action=" + e.getActionCommand());
		if ( e.getActionCommand().equals(ConfirmPanel.A_REFRESH) )
		{
			Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
			loadCheques();
			dialog.tableChanged(null);
			Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		}
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt)
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

		//String moduloLogistico = MSysConfig.getValue("UY_MOD_LOGISTICO", Env.getAD_Client_ID(Env.getCtx()));
		
		//Refresh button
		CButton refreshButton = ConfirmPanel.createRefreshButton(false);
		refreshButton.setMargin(new Insets (1, 10, 0, 10));
		refreshButton.setDefaultCapable(true);
		refreshButton.addActionListener(this);
		dialog.getConfirmPanel().addButton(refreshButton);
		dialog.getRootPane().setDefaultButton(refreshButton);
				
		if (getGridTab().getValue("UY_MovBancariosHdr_ID") == null)
		{
			ADialog.error(0, dialog, "SaveErrorRowNotFound");
			return false;
		}
		
		dialog.setTitle(getTitle());

		
		MLookup lookupBanco = null;
		MLookup lookupEstado = null;


		
		String valorEstado = "";
		
		Integer idMovBancariosHdr = (Integer)getGridTab().getValue("UY_MovBancariosHdr_ID");
		
		int idDocType = 0;
		int C_BankAccount_ID = 0;
		MMovBancariosHdr banHdr = null;
		
		if (idMovBancariosHdr.intValue()>0){
			banHdr = new MMovBancariosHdr(Env.getCtx(), idMovBancariosHdr.intValue(), null);
			idDocType = banHdr.getC_DocType_ID();
			C_BankAccount_ID = banHdr.getC_BankAccount_ID();
		}
		else{
			idDocType = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_DocType_ID");
			C_BankAccount_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BankAccount_ID");
		}

		int AD_Column_ID = this.getBankValidationColumnID(idDocType);
		lookupBanco = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Table);

		
		MDocType doc = new MDocType(Env.getCtx(), idDocType, null);
		if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_Banco_ConciliacionChequesPropios)){
			bankAccountField = new VLookup ("C_BankAccount_ID", true, true, true, lookupBanco);
			int adColEstadoConc = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_estadocheque_concilia");
			lookupEstado = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, adColEstadoConc, DisplayType.List);
			
			bankAccountField.setValue(new Integer(C_BankAccount_ID));
			valorEstado = "ENT";
			
		}
		else if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoChequeTerceros) || 
				 doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_Banco_DescuentoChequesTerceros)){
			bankAccountField = new VLookup ("C_BankAccount_ID", true, false, true, lookupBanco);
			int adColEstadoDCT = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_estadocheque_depter");
			lookupEstado = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, adColEstadoDCT, DisplayType.List);
			valorEstado = "CAR";
		}
		else if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_Banco_Transferencias)){
			bankAccountField = new VLookup ("C_BankAccount_ID", true, false, true, lookupBanco);
			int adColEstadoTra = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_estadocheque_transf");
			lookupEstado = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, adColEstadoTra, DisplayType.List);
			valorEstado = "EMI";
		}
		else{
			bankAccountField = new VLookup ("C_BankAccount_ID", true, false, true, lookupBanco);
			lookupEstado = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, MColumn.getColumn_ID(MMediosPago.Table_Name, MMediosPago.COLUMNNAME_estado), DisplayType.List);
		}
		
		estadoChequeField = new VLookup (MMediosPago.COLUMNNAME_estado,true,false,true,lookupEstado);
		if (!valorEstado.equalsIgnoreCase("")) estadoChequeField.setValue(valorEstado);
		estadoChequeField.addActionListener(this);
		
		bPartnerLookup = new VLookup("C_BPartner_ID", false, false, true,
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 3499, DisplayType.Search));
		BPartner_idLabel.setLabelFor(bPartnerLookup);
		
		//Timestamp date = Env.getContextAsDate(Env.getCtx(), p_WindowNo, I_UY_MovBancariosHdr.COLUMNNAME_DateTrx);
		//dateToField.setValue(date);
		//fecVencToField.setValue(date);
	
		//bankAccount = new MBankAccount(Env.getCtx(), C_BankAccount_ID, null);
		
		loadCheques();
		
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
    	bankAccountLabel.setText(Msg.translate(Env.getCtx(), "C_BankAccount_ID"));
    	estadoChequeLabel.setText("Estado Cheque");
    	checkNoLabel.setLabelFor(checkNoField);

    	dateFromLabel.setLabelFor(dateFromField);
    	dateFromField.setToolTipText(Msg.translate(Env.getCtx(), "DateFrom"));
    	dateToLabel.setLabelFor(dateToField);
    	dateToField.setToolTipText(Msg.translate(Env.getCtx(), "DateTo"));

    	fecVencFromLabel.setLabelFor(fecVencFromField);
    	fecVencFromField.setToolTipText("Fecha Vencimiento Desde");
    	fecVencToLabel.setLabelFor(fecVencToField);
    	fecVencToField.setToolTipText("Fecha Vencimiento Hasta");
    	
    	amtFromLabel.setLabelFor(amtFromField);
    	amtFromField.setToolTipText(Msg.translate(Env.getCtx(), "AmtFrom"));
    	amtToLabel.setLabelFor(amtToField);
    	amtToField.setToolTipText(Msg.translate(Env.getCtx(), "AmtTo"));
    	
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	
    	CPanel parameterBankPanel = new CPanel();
    	parameterBankPanel.setLayout(new GridBagLayout());
    	parameterPanel.add(parameterBankPanel, BorderLayout.CENTER);
    	
    	parameterBankPanel.add(bankAccountLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	if (bankAccountField != null)
    		parameterBankPanel.add(bankAccountField, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	/*parameterBankPanel.add(documentTypeLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	if(documentTypeField!= null)
    		parameterBankPanel.add(documentTypeField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));*/

    	parameterBankPanel.add(estadoChequeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	if(estadoChequeField!=null)
    		parameterBankPanel.add(estadoChequeField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0)); 

    	parameterBankPanel.add(BPartner_idLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    		parameterBankPanel.add(bPartnerLookup, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));   	
    
    	parameterBankPanel.add(checkNoLabel, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterBankPanel.add(checkNoField, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	parameterBankPanel.add(amtFromLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterBankPanel.add(amtFromField, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterBankPanel.add(amtToLabel, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterBankPanel.add(amtToField, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	parameterBankPanel.add(dateFromLabel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterBankPanel.add(dateFromField, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterBankPanel.add(dateToLabel, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterBankPanel.add(dateToField, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	parameterBankPanel.add(fecVencFromLabel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterBankPanel.add(fecVencFromField, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterBankPanel.add(fecVencToLabel, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterBankPanel.add(fecVencToField, new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	
    }   //  jbInit

	protected void loadCheques()
	{
		loadTableOIS(getChequesData(bankAccountField.getValue(), checkNoField.getText(), bPartnerLookup.getValue(), dateFromField.getValue(), dateToField.getValue(),
				amtFromField.getValue(), amtToField.getValue(), estadoChequeField.getValue(), fecVencFromField.getValue(), fecVencToField.getValue()));
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
	
	/**
	 *  List total amount
	 */
	public void info()
	{
		DecimalFormat format = DisplayType.getNumberFormat(DisplayType.Amount);

		BigDecimal total = new BigDecimal(0.0);
		int rows = dialog.getMiniTable().getRowCount();
		int count = 0;
		for (int i = 0; i < rows; i++)
		{
			if (((Boolean)dialog.getMiniTable().getValueAt(i, 0)).booleanValue())
			{
				total = total.add((BigDecimal)dialog.getMiniTable().getValueAt(i, 5));
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
	
	/* Segun tipo de documento en bancos, retorno el ID de la columna de validacion para el banco. */
	private int getBankValidationColumnID(int doctypeID){
		
		//int idColumn = MColumn.getColumn_ID(I_UY_MovBancariosHdr.Table_Name, I_UY_MovBancariosHdr.COLUMNNAME_C_BankAccount_ID);
		int idColumn = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_c_bankaccount_propio_id");
	
		MDocType doc = new MDocType(Env.getCtx(), doctypeID, null);
		String docBaseType = doc.getDocBaseType();
		
		if ((docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoChequeTerceros))
				|| (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DescuentoChequesTerceros))
				|| (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CambioChequeTercero)))
			idColumn = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_c_bankaccount_terceros_id");
		
		return idColumn;
	}
}
