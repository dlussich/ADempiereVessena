/**
 * InfoMedioPago.java
 * 17/03/2011
 */
package org.compiere.apps.search;

import java.awt.Frame;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.compiere.acct.Doc;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MColumn;
import org.compiere.model.MDocType;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CLabel;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.openup.model.I_UY_MovBancariosHdr;
import org.openup.model.MMediosPago;
import org.openup.model.X_UY_MediosPago;

/**
 * OpenUp.
 * InfoMedioPago
 * Descripcion : Ventana de Informacion de Medios de Pago. Permite seleccionar un registro.
 * @author Gabriel Vila
 * Fecha : 17/03/2011
 */
public class InfoMedioPago extends Info {

	private static final long serialVersionUID = 5550733934685665946L;
	//OpenUp M.R. 15-09-2011 Issue#652 Seteo docBasetype para obtener tipo de documento
	private String docBaseType = "";
	
	/**
	 * Constructor
	 * @param frame
	 * @param modal
	 * @param WindowNo
	 * @param value
	 * @param multiSelection
	 * @param whereClause
	 */
	public InfoMedioPago(Frame frame, boolean modal, int WindowNo, String value, boolean multiSelection, String whereClause)
	{
		super (frame, modal, WindowNo, X_UY_MediosPago.Table_Name, X_UY_MediosPago.COLUMNNAME_UY_MediosPago_ID, multiSelection, whereClause);
		
		setTitle("Informacion de Medios de Pago");

		statInit();
		initInfo (value, whereClause);
		
		int no = p_table.getRowCount();
		
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		
		//	AutoQuery
		if (value != null && value.length() > 0)
			executeQuery();
		p_loadedOK = true;

		//	Focus
		//fieldValue.requestFocus();
		AEnv.positionCenterWindow(frame, this);
	}

	
	/**  Array of Column Info    */
	private static Info_Column[] infoLayout = {
		new Info_Column(" ", "UY_MediosPago.uy_mediospago_id", IDColumn.class),
		new Info_Column("Cheque      ", "UY_MediosPago.checkno", String.class),
		new Info_Column("Cuenta Bancaria                ", "banco.Description", String.class),
		new Info_Column("Socio de Negocio               ", "bp.Name2", String.class),
		new Info_Column("Importe               ", "UY_MediosPago.payamt", BigDecimal.class), //OpenUp. Nicolas Sarlabos. 15/08/2013. #1220. Se agrega columna de importe.
		new Info_Column("ID MedioPago", "UY_MediosPago.uy_mediospago_id", Integer.class)
	};

	
	protected CLabel bankAccountLabel = new CLabel("Cuenta Bancaria            ");
	protected VLookup bankAccountField;
	
	protected CLabel bPartnerLabel = new CLabel("Socio de Negocio              ");
	protected VLookup bPartnerField;

	private CLabel estadoLabel = new CLabel("Estado     ");
	protected VLookup estadoField;

	private CLabel dateFromLabel = new CLabel("Fecha Emision");
	protected VDate dateFromField = new VDate("DateFrom", false, false, true, DisplayType.Date, "Fecha Desde");
	private CLabel dateToLabel = new CLabel("-");
	protected VDate dateToField = new VDate("DateTo", false, false, true, DisplayType.Date, "Fecha Hasta");

	private CLabel dueDateFromLabel = new CLabel("Fecha Vencimiento");
	protected VDate dueDateFromField = new VDate("dueDateFrom", false, false, true, DisplayType.Date, "Vencimiento Desde");
	private CLabel dueDateToLabel = new CLabel("-");
	protected VDate dueDateToField = new VDate("dueDateTo", false, false, true, DisplayType.Date, "Vencimiento Hasta");
	
	private CLabel checkNoLabel = new CLabel("Numero ");
	protected CTextField checkNoField = new CTextField(10);

	
	/**
	 *	Static Setup - add fields to parameterPanel
	 */
	private void statInit()
	{

		MLookup lookupBanco = null;
		MLookup lookupEstado = null;

		int AD_Column_ID = this.getBankValidationColumnID(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_DocType_ID"));
		lookupBanco = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Table);
		bankAccountField = new VLookup ("C_BankAccount_ID", true, false, true, lookupBanco);

		int adColEstado = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_estadoscheques");
		lookupEstado = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, adColEstado, DisplayType.List);
		estadoField = new VLookup (MMediosPago.COLUMNNAME_estado,true,false,true,lookupEstado);
		estadoField.setValue("ENT");
		estadoField.addActionListener(this);
		
		bPartnerField = new VLookup("C_BPartner_ID", false, false, true,
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 3499, DisplayType.Search));
		bPartnerLabel.setLabelFor(bPartnerField);

		//
		parameterPanel.setLayout(new ALayout());
		//
		parameterPanel.add(bankAccountLabel, new ALayoutConstraint(0,0));
		parameterPanel.add(bankAccountField, null);
		parameterPanel.add(estadoLabel, null);
		parameterPanel.add(estadoField, null);
		//
		parameterPanel.add(bPartnerLabel, new ALayoutConstraint(1,0));
		parameterPanel.add(bPartnerField, null);
		parameterPanel.add(checkNoLabel, null);
		parameterPanel.add(checkNoField, null);
		//		
		parameterPanel.add(dateFromLabel, new ALayoutConstraint(2,0));
		parameterPanel.add(dateFromField, null);
		parameterPanel.add(dateToLabel, null);
		parameterPanel.add(dateToField, null);
		//		
		parameterPanel.add(dueDateFromLabel, new ALayoutConstraint(3,0));
		parameterPanel.add(dueDateFromField, null);
		parameterPanel.add(dueDateToLabel, null);
		parameterPanel.add(dueDateToField, null);
		
	}	//	statInit

	/**
	 *	Dynamic Init
	 *  @param value value
	 *  @param whereClause where clause
	 */
	private void initInfo(String value, String whereClause)
	{
		
		String from = X_UY_MediosPago.Table_Name +
			" INNER JOIN C_BankAccount banco ON UY_MediosPago.C_BankAccount_ID = banco.C_BankAccount_ID " +
			" INNER JOIN C_BPartner bp ON UY_MediosPago.C_BPartner_ID = bp.C_BPartner_ID ";
		
		StringBuffer where = new StringBuffer();
		where.append("uy_mediospago.IsActive='Y' AND uy_mediospago.checkno is not null");
		
		/*if (whereClause != null && whereClause.length() > 0)
			where.append(" AND ").append(whereClause);*/

		prepareTable(infoLayout, from, where.toString(), X_UY_MediosPago.Table_Name + "." + X_UY_MediosPago.COLUMNNAME_CheckNo);
		
	}	//	initInfo

	
	/**
	 *	Construct SQL Where Clause and define parameters.
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return WHERE clause
	 */
	protected String getSQLWhere()
	{
		StringBuffer sql = new StringBuffer(" AND UY_MediosPago.Processed='Y'" + " AND UY_MediosPago.DocStatus IN ('CO','CL') AND UY_MediosPago.PayAmt<>0"); 
		
		if (bankAccountField.getValue() != null) sql.append(" AND UY_MediosPago.C_BankAccount_ID = ?");
		if (estadoField.getValue() != null) sql.append(" AND UY_MediosPago.estado = ?");
		if (bPartnerField.getValue() != null) sql.append(" AND UY_MediosPago.C_BPartner_ID=?");
		if (!checkNoField.getText().equalsIgnoreCase("")) sql.append(" AND UPPER(UY_MediosPago.CheckNo) LIKE ?");

		if (dateFromField.getValue() != null || dateToField.getValue() != null)
		{
			Timestamp from = (Timestamp) dateFromField.getValue();
			Timestamp to = (Timestamp) dateToField.getValue();
			if (from == null && to != null)
				sql.append(" AND TRUNC(UY_MediosPago.DateTrx) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(UY_MediosPago.DateTrx) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(UY_MediosPago.DateTrx) BETWEEN ? AND ?");
		}

		if (dueDateFromField.getValue() != null || dueDateToField.getValue() != null)
		{
			Timestamp from = (Timestamp) dueDateFromField.getValue();
			Timestamp to = (Timestamp) dueDateFromField.getValue();
			if (from == null && to != null)
				sql.append(" AND TRUNC(UY_MediosPago.DueDate) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(UY_MediosPago.DueDate) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(UY_MediosPago.DueDate) BETWEEN ? AND ?");
		}

		// OpenUp. Mario Reyes. 15/09/2011. Issue #652.
		// Para cambio de cheques propios tengra que agregar esta condicion
		if (this.docBaseType.equalsIgnoreCase("DCP")) {
			sql.append(" AND UY_MediosPago.tipomp='PRO' ");
		}

		if (this.docBaseType.equalsIgnoreCase("DCT")) {
			sql.append(" AND UY_MediosPago.tipomp='TER' ");
		}
		// Fin Issue #652

		return sql.toString();
	}	//	getSQLWhere

	/**
	 *  Set Parameters for Query.
	 *  (as defined in getSQLWhere)
	 *  @param pstmt pstmt
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;

		if (bankAccountField.getValue() != null)
		{
			Integer bankAccountID = (Integer) bankAccountField.getValue();
			pstmt.setInt(index++, bankAccountID.intValue());
		}

		if (estadoField.getValue() != null)
		{
			String estado = (String) estadoField.getValue();
			pstmt.setString(index++, estado);
		}

		if (bPartnerField.getValue() != null)
		{
			Integer valueID = (Integer) bPartnerField.getValue();
			pstmt.setInt(index++, valueID.intValue());
		}

		if (!checkNoField.getText().equalsIgnoreCase("")){
			pstmt.setString(index++, getSQLText(checkNoField.getText()));
		}
		
		if (dateFromField.getValue() != null || dateToField.getValue() != null)
		{
			Timestamp from = (Timestamp) dateFromField.getValue();
			Timestamp to = (Timestamp) dateToField.getValue();

			if (from == null && to != null)
				pstmt.setTimestamp(index++, to);
			else if (from != null && to == null)
				pstmt.setTimestamp(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}
		
		if (dueDateFromField.getValue() != null || dueDateToField.getValue() != null)
		{
			Timestamp from = (Timestamp) dueDateFromField.getValue();
			Timestamp to = (Timestamp) dueDateFromField.getValue();

			if (from == null && to != null)
				pstmt.setTimestamp(index++, to);
			else if (from != null && to == null)
				pstmt.setTimestamp(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}
	}   //  setParameters

	/*************************************************************************/

	/**
	 *  Save Selection Details
	 *  Get Location/Partner Info
	 */
	public void saveSelectionDetail()
	{
		int row = p_table.getSelectedRow();
		
		if (row == -1) return;

		Object data = p_table.getModel().getValueAt(row, 1);
		
		//  publish for Callout to read
		Integer ID = getSelectedRowKey();
		Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, "UY_MediosPago_ID", ID == null ? "0" : ID.toString());
		Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, "CheckNo", data == null ? "" : String.valueOf(data).trim());

	}   //  saveSelectionDetail


	/**************************************************************************
	 *	Show History
	 */
//	protected void showHistory()
//	{
//		log.info("");
//		Integer C_BPartner_ID = getSelectedRowKey();
//		if (C_BPartner_ID == null)
//			return;
//		InvoiceHistory ih = new InvoiceHistory (this, C_BPartner_ID.intValue(), 
//			0, 0, 0);
//		ih.setVisible(true);
//		ih = null;
//	}	//	showHistory

	/**
	 *	Has History
	 *  @return true
	 */
	protected boolean hasHistory()
	{
		return true;
	}	//	hasHistory

	/**
	 *	Zoom
	 */
//	protected void zoom()
//	{
//		log.info( "InfoBPartner.zoom");
//		Integer C_BPartner_ID = getSelectedRowKey();
//		if (C_BPartner_ID == null)
//			return;
//	//	AEnv.zoom(MBPartner.Table_ID, C_BPartner_ID.intValue(), true);	//	SO
//
//		MQuery query = new MQuery("C_BPartner");
//		query.addRestriction("C_BPartner_ID", MQuery.EQUAL, C_BPartner_ID);
//		query.setRecordCount(1);
//		int AD_WindowNo = getAD_Window_ID("C_BPartner", true);	//	SO
//		zoom (AD_WindowNo, query);
//	}	//	zoom

	/**
	 *	Has Zoom
	 *  @return true
	 */
//	protected boolean hasZoom()
//	{
//		return true;
//	}	//	hasZoom

	/**
	 *	Customize
	 */
	protected void customize()
	{
		log.info( "InfoBPartner.customize");
	}	//	customize

	/**
	 *	Has Customize
	 *  @return false
	 */
	protected boolean hasCustomize()
	{
		return false;	//	for now
	}	//	hasCustomize

	
	/* Segun tipo de documento en bancos, retorno el ID de la columna de validacion para el banco. */
	private int getBankValidationColumnID(int doctypeID){
		
		int idColumn = MColumn.getColumn_ID(I_UY_MovBancariosHdr.Table_Name, I_UY_MovBancariosHdr.COLUMNNAME_C_BankAccount_ID);
		
		MDocType doc = new MDocType(Env.getCtx(), doctypeID, null);
		this.docBaseType = doc.getDocBaseType();  // OpenUp M.R. 15-09-2011 Issue #652 aca seteo el docbasetype para traer el tipo de documento.
		
		if ((docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DepositoChequeTerceros))
				|| (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DescuentoChequesTerceros))
				|| (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CambioChequeTercero))
				|| (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_RechazoChequesTerceros)))
			idColumn = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_c_bankaccount_terceros_id");
		
		/*int idColumn = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_c_bankaccount_propio_id");
	
		switch (doctypeID){
		case 1000046:  // Deposito Cheque Terceros
			idColumn = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_c_bankaccount_terceros_id");
			break;
		case 1000049:  // Descuento Cheque Terceros
			idColumn = MColumn.getColumn_ID("UY_ValidacionCampos", "uy_c_bankaccount_terceros_id");
			break;
		}*/
		
		return idColumn;
	}
	
	/**
	 *  Get SQL WHERE parameter
	 *  @param f field
	 *  @return Upper case text with % at the end
	 */
	private String getSQLText (String text)
	{
		String s = text.toUpperCase();
		if (!s.endsWith("%"))
			s += "%";
		log.fine( "String=" + s);
		return s;
	}   //  getSQLText

	
}	//	InfoBPartner
