/**
 * 
 */
package org.compiere.apps.search;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.grid.ed.VComboBox;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MRole;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextArea;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.jdesktop.swingx.JXTaskPane;
import org.zkoss.zk.ui.event.Events;

/**
 * @author SBT 21/10/2015
 *
 */
public class InfoMProductRT extends Info{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4389120835462617231L;

	public InfoMProductRT(Frame frame, boolean modal, int WindowNo,
			int M_Warehouse_ID, int M_PriceList_ID, String value,
			boolean multiSelection, String whereClause) {
		
		super (frame, modal, WindowNo, "p", "M_Product_ID", multiSelection, whereClause);

		//Original -->super(frame, modal, WindowNo, tableName, keyColumn, multiSelection, whereClause);
		// TODO Auto-generated constructor stub
		//super (frame, modal, WindowNo, "C_BPartner", "C_BPartner_ID", multiSelection, whereClause);
		log.info(value);
		setTitle(Msg.getMsg(Env.getCtx(), "InfoProduct"));
		//m_isSOTrx = isSOTrx;
		//
		statInit();
		//initInfo (value, 0, 0);//es del original de mproduct
		initInfo (value, 0,whereClause);//es de cbparnet
		setListeners();//Se setea los eventos a los combo seccion y rubro
		//
		int no = p_table.getRowCount();
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		//	AutoQuery
		if (value != null && value.length() > 0)
			executeQuery();
		p_loadedOK = true;
		//	Focus
		fieldName.requestFocus();
		//parameterPanel.setPreferredSize(new Dimension (INFO_WIDTH, parameterPanel.getPreferredSize().height));
		Dimension s1= p_table.getPreferredSize();
		int ancho = (int) s1.getWidth();
		this.setPreferredSize(new Dimension(550, 484)); 

		AEnv.positionCenterWindow(frame, this);
	}//	InfoMProductRT

	

	private void setListeners() {
		// TODO Auto-generated method stub
		MyActionListener actionListener = new MyActionListener();
		pickSeccion.setEditable(true);
		pickSeccion.addActionListener(actionListener);
		pickRubro.setEditable(true);
		pickRubro.addActionListener(actionListener);
		/*   cb.setEditable(true);
    	// Create and register listener
    	MyActionListener actionListener = new MyActionListener();
    	cb.addActionListener(actionListener);*/
	}



	//INIT VARIANLES SBT
	int largoCampos = 10; //SBT
	/** SQL From				*/
	private static final String s_productFrom =	
		"M_Product p"
		+ " LEFT OUTER JOIN UY_ProductUpc upc ON (p.M_Product_ID = upc.M_Product_ID AND upc.IsActive='Y')"	//;
		+ " LEFT OUTER JOIN UY_Linea_Negocio ln ON (p.UY_Linea_Negocio_ID=ln.UY_Linea_Negocio_ID AND ln.IsActive='Y')"
		+ " LEFT OUTER JOIN UY_ProductGroup pg ON (p.UY_ProductGroup_ID=pg.UY_ProductGroup_ID AND pg.IsActive='Y')"
		+ " LEFT OUTER JOIN UY_Familia f ON (p.UY_Familia_ID=f.UY_Familia_ID AND f.IsActive='Y')"
		+ " LEFT OUTER JOIN UY_SubFamilia sf ON (p.UY_SubFamilia_ID=sf.UY_SubFamilia_ID)";
	//Codigo
	private CLabel labelValue = new CLabel();
	private CTextField fieldValue = new CTextField(largoCampos);
	//Nombre
	private CLabel labelName = new CLabel();
	private CTextField fieldName = new CTextField(largoCampos);
	//Codigo de barra --> Hacemos referencia a la tabla UY_ProductUpc
	private CLabel labelUPC = new CLabel();
	private CTextField fieldUPC = new CTextField(largoCampos);
	//private VComboBox picKUPC = new VComboBox();-->Toma mucho timepo cargar el combo por la cantidad de datos
	//Almacen
	private CLabel labelWarehouse = new CLabel();
	private VComboBox pickWarehouse = new VComboBox();
	//CBPartner --> NO SE UTILIZA
	private CLabel labelVendor = new CLabel();
	private CTextField fieldVendor = new CTextField(largoCampos);
	//Seccion
	private CLabel labelSeccion = new CLabel();
	private VComboBox pickSeccion = new VComboBox();
	//Rubro
	private CLabel labelRubro = new CLabel();
	private VComboBox pickRubro = new VComboBox();
	//Familia
	private CLabel labelFamilia = new CLabel();
	private VComboBox pickFamilia = new VComboBox();
	//SubFamilia
	private CLabel labelSubFamilia = new CLabel();
	private VComboBox pickSubFamilia = new VComboBox();
	
	JXTaskPane warehouseStockPanel = new JXTaskPane();
	
    CPanel tablePanel = new CPanel();
    
    MiniTable warehouseTbl = new MiniTable();
    
    String m_sqlWarehouse;
    
    MiniTable substituteTbl = new MiniTable();
    
    String m_sqlSubstitute;
    
    MiniTable relatedTbl = new MiniTable();
    
    String m_sqlRelated;

    int mWindowNo = 0;

	//FIN VARIABLES SBT
	//COLUMNAS Unica tabla
	/**  Array of Column Info    */
	private static Info_Column[] s_partnerLayout = {
		new Info_Column(" ", "p.M_Product_ID", IDColumn.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Value"), "p.Value", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Name"), "p.Name", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "UY_Linea_Negocio_ID"), "ln.Name AS Seccion", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "UY_ProductGroup_ID"), "pg.Name AS Rubro", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "UY_Familia_ID"), "f.Name AS Famila", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "UY_SubFamilia_ID"), "sf.Name as SubFamilia", String.class)
		//Msg.translate(Env.getCtx(), "UY_Linea_Negocio_ID")
	};
	
	/**
	 * Inicializo las variables 
	 * @author OpenUp SBT Issue#4940  21/10/2015
	 */
	private void statInit()
	{
		//Codigo
		labelValue.setText(Msg.getMsg(Env.getCtx(), "Value"));
		fieldValue.setBackground(AdempierePLAF.getInfoBackground());
		fieldValue.addActionListener(this);
		//Name
		labelName.setText(Msg.getMsg(Env.getCtx(), "Name"));
		fieldName.setBackground(AdempierePLAF.getInfoBackground());
		fieldName.addActionListener(this);
		//Codigo de barra
		labelUPC.setText(Msg.getMsg(Env.getCtx(), "UPC"));
		fieldUPC.setBackground(AdempierePLAF.getInfoBackground());
		fieldUPC.addActionListener(this);
		//Almacen  OPR AHORA NO SE VA A UTILIZAR 22/10-2015
		labelWarehouse.setText(Msg.getMsg(Env.getCtx(), "Warehouse"));
		pickWarehouse.setBackground(AdempierePLAF.getInfoBackground());
		//Proveedor POR AHORA NO SE VA A UTILIZAR LLEVA MAS LOGICA  22/10-2015
		labelVendor.setText(Msg.translate(Env.getCtx(), "Vendor"));
		fieldVendor.setBackground(AdempierePLAF.getInfoBackground());
		fieldVendor.addActionListener(this);
		//Seccion
		labelSeccion.setText(Msg.translate(Env.getCtx(), "UY_Linea_Negocio_ID"));
		pickSeccion.setBackground(AdempierePLAF.getInfoBackground());
		pickSeccion.setPreferredSize(new Dimension(200, 23));
		
		//Rubro
		labelRubro.setText(Msg.translate(Env.getCtx(), "UY_ProductGroup_ID"));
		pickRubro.setBackground(AdempierePLAF.getInfoBackground());
		pickRubro.setPreferredSize(new Dimension(200, 23));
		//Familia
		labelFamilia.setText(Msg.translate(Env.getCtx(), "UY_Familia_ID"));
		pickFamilia.setBackground(AdempierePLAF.getInfoBackground());
		pickFamilia.setPreferredSize(new Dimension(200, 23));
		//SubFamilia POR AHORA NO SE VA A UTILIZAR  22/10-2015
		labelSubFamilia.setText(Msg.translate(Env.getCtx(), "UY_SubFamilia_ID"));
		pickSubFamilia.setBackground(AdempierePLAF.getInfoBackground());
		pickSubFamilia.setPreferredSize(new Dimension(200, 23));
		
		//		Line 1
		parameterPanel.setLayout(new ALayout());
		parameterPanel.add(labelName, new ALayoutConstraint(0,0));
		parameterPanel.add(fieldName, null);
		parameterPanel.add(labelSeccion, null);
		parameterPanel.add(pickSeccion, null);
		
		//		Line 2   parameterPanel.add(labelValue, new ALayoutConstraint(0,0));
		parameterPanel.add(labelUPC, new ALayoutConstraint(1,0));
		parameterPanel.add(fieldUPC, null);
		parameterPanel.add(labelRubro, null);
		parameterPanel.add(pickRubro, null);		
		
		//		Line 3
		parameterPanel.add(labelValue, new ALayoutConstraint(2,0));
		parameterPanel.add(fieldValue, null);
		parameterPanel.add(labelFamilia, null);
		parameterPanel.add(pickFamilia, null);
		//parameterPanel.setSize(200,10);
		//POR ahora los siguientes filtros no se van a agregar 22/10-2015
//		parameterPanel.add(labelSubFamilia,null );
//		parameterPanel.add(pickSubFamilia, null);
//		parameterPanel.add(labelWarehouse, null);
//		parameterPanel.add(pickWarehouse, null);
//		parameterPanel.add(labelVendor, null);
//		parameterPanel.add(fieldVendor, null);

		//Begin - fer_luck @ centuryon
				//add taskpane
//		fieldDescription.setBackground(AdempierePLAF.getInfoBackground());
//		fieldDescription.setEditable(false);
//		fieldDescription.setPreferredSize(new Dimension(INFO_WIDTH - 100, 100));
	
	}
	
	/**
	 * 
	 * @author OpenUp SBT Issue#  21/10/2015 
	 * @param value
	 * @param M_Warehouse_ID
	 * @param whereClause
	 */
	private void initInfo(String value, int M_Warehouse_ID,String whereClause) {
		//	Pick init
		fillPicks();
		//		Set Warehouse
//		if (M_Warehouse_ID == 0)
//			M_Warehouse_ID = Env.getContextAsInt(Env.getCtx(), "#M_Warehouse_ID");
//		if (M_Warehouse_ID != 0)
//			setWarehouse (M_Warehouse_ID);
		
		//	Create Grid
		StringBuffer where = new StringBuffer();
		where.append( "p.IsActive='Y'");
		if (whereClause != null && whereClause.length() > 0)
			where.append(" AND ").append(whereClause);
		//Tabla de productos+ç

		prepareTable(s_partnerLayout, s_productFrom,where.toString(),"p.Value");

		//  Set Value
		if (value == null)
			value = "%";
		if (!value.endsWith("%"))
			value += "%";
	
		//	Put query string in Name if not numeric
		if (value.equals("%"))
			fieldName.setText(value);
		//	No Numbers entered
		else if ((value.indexOf('0')+value.indexOf('1')+value.indexOf('2')+value.indexOf('3')+value.indexOf('4') +value.indexOf('5')
			+value.indexOf('6')+value.indexOf('7')+value.indexOf('8')+value.indexOf('9')) == -10)
		{
			if (value.startsWith("%"))
				fieldName.setText(value);
			else
				fieldName.setText("%" + value);
		}
		//	Number entered
		else
			fieldValue.setText(value);
			
	}
	
//	@Override
//	protected String getSQLWhere() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	protected void setParameters(PreparedStatement pstmt, boolean forCount)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}

	/**
	 *	Set Warehouse
	 *
	 * 	@param M_Warehouse_ID warehouse
	 */
	private void setWarehouse(int M_Warehouse_ID)
	{
		for (int i = 0; i < pickWarehouse.getItemCount(); i++)
		{
			KeyNamePair kn = (KeyNamePair)pickWarehouse.getItemAt(i);
			if (kn.getKey() == M_Warehouse_ID)
			{
				pickWarehouse.setSelectedIndex(i);
				return;
			}
		}
	}	//	setWarehouse
	
	
	/**
	 *	Agregar info a cada combo
	 *
	 * @author SBT 21/10/2015
	 */
	private void fillPicks ()
	{
		//UPC's
		String SQL = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//Seccion
			SQL = MRole.getDefault().addAccessSQL (
					"SELECT UY_Linea_Negocio_ID, Name "
					+ "FROM UY_Linea_Negocio "
					+ "WHERE IsActive='Y'",
						"UY_Linea_Negocio", MRole.SQL_NOTQUALIFIED, MRole.SQL_RO)
					+ " ORDER BY Name";
				pickSeccion.addItem(new KeyNamePair (0, ""));
				pstmt = DB.prepareStatement(SQL, null);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					KeyNamePair kn = new KeyNamePair
						(rs.getInt("UY_Linea_Negocio_ID"), rs.getString("Name"));
					pickSeccion.addItem(kn);
				}
				DB.close(rs, pstmt);
				
				//Rubro
				SQL = MRole.getDefault().addAccessSQL (
						"SELECT UY_ProductGroup_ID, Name "
						+ "FROM UY_ProductGroup "
						+ "WHERE IsActive='Y'",
							"UY_ProductGroup", MRole.SQL_NOTQUALIFIED, MRole.SQL_RO)
						+ " ORDER BY Name";
				pickRubro.addItem(new KeyNamePair (0, ""));
				pstmt = DB.prepareStatement(SQL, null);
				rs = pstmt.executeQuery();
				while (rs.next())
					{
						KeyNamePair kn = new KeyNamePair
							(rs.getInt("UY_ProductGroup_ID"), rs.getString("Name"));
						pickRubro.addItem(kn);
					}
				DB.close(rs, pstmt);
				
				//Familia
				SQL = MRole.getDefault().addAccessSQL (
						"SELECT UY_Familia_ID, Name "
						+ "FROM UY_Familia "
						+ "WHERE IsActive='Y'",
							"UY_Familia", MRole.SQL_NOTQUALIFIED, MRole.SQL_RO)
						+ " ORDER BY Name";
				pickFamilia.addItem(new KeyNamePair (0, ""));
				pstmt = DB.prepareStatement(SQL, null);
				rs = pstmt.executeQuery();
				while (rs.next())
						{
							KeyNamePair kn = new KeyNamePair
								(rs.getInt("UY_Familia_ID"), rs.getString("Name"));
							pickFamilia.addItem(kn);
						}
				DB.close(rs, pstmt);
					
				//SubFamilia
				SQL = MRole.getDefault().addAccessSQL (
						"SELECT UY_SubFamilia_ID, Name "
						+ "FROM UY_SubFamilia "
						+ "WHERE IsActive='Y'",
							"UY_SubFamilia", MRole.SQL_NOTQUALIFIED, MRole.SQL_RO)
						+ " ORDER BY Name";
				pickSubFamilia.addItem(new KeyNamePair (0, ""));
				pstmt = DB.prepareStatement(SQL, null);
				rs = pstmt.executeQuery();
				while (rs.next())
						{
							KeyNamePair kn = new KeyNamePair
								(rs.getInt("UY_SubFamilia_ID"), rs.getString("Name"));
							pickSubFamilia.addItem(kn);
						}
				DB.close(rs, pstmt);
				
			//	Warehouse
			SQL = MRole.getDefault().addAccessSQL (
				"SELECT M_Warehouse_ID, Value || ' - ' || Name AS ValueName "
				+ "FROM M_Warehouse "
				+ "WHERE IsActive='Y'",
					"M_Warehouse", MRole.SQL_NOTQUALIFIED, MRole.SQL_RO)
				+ " ORDER BY Value";
			pickWarehouse.addItem(new KeyNamePair (0, ""));
			pstmt = DB.prepareStatement(SQL, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair kn = new KeyNamePair
					(rs.getInt("M_Warehouse_ID"), rs.getString("ValueName"));
				pickWarehouse.addItem(kn);
			}
			DB.close(rs, pstmt);
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, SQL, e);
			setStatusLine(e.getLocalizedMessage(), true);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}	//	fillPicks RT
	
	/**************************************************************************
	 *	Construct SQL Where Clause and define parameters
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return SQL WHERE clause
	 */
	@Override
	protected String getSQLWhere()
	{
		StringBuffer where = new StringBuffer();
		
		//	Product Attribute Search
//		if (m_pAttributeWhere != null)
//		{
//			where.append(m_pAttributeWhere);
//			return where.toString();
//		}

		//  => Value
		String value = fieldValue.getText().toUpperCase();
		if (!(value.equals("") || value.equals("%")))
			where.append(" AND UPPER(p.Value) LIKE ?");

		//  => Name
		String name = fieldName.getText().toUpperCase();
		if (!(name.equals("") || name.equals("%")))
			where.append(" AND UPPER(p.Name) LIKE ?");

		//  => UPC
		String upc = fieldUPC.getText().toUpperCase();
		if (!(upc.equals("") || upc.equals("%")))
			where.append(" AND UPPER(upc.UPC) LIKE ?");
		
		
		//	Seccion
		int UY_Linea_Negocio_ID = 0;
		KeyNamePair ln = (KeyNamePair)pickSeccion.getSelectedItem();
		if (ln != null)
			UY_Linea_Negocio_ID = ln.getKey();
		if (UY_Linea_Negocio_ID != 0)
			where.append(" AND ln.UY_Linea_Negocio_ID=?");
		
		//	Rubro
		int UY_ProductGroup_ID = 0;
		KeyNamePair pg = (KeyNamePair)pickRubro.getSelectedItem();
		if (pg != null)
			UY_ProductGroup_ID = pg.getKey();
		if (UY_ProductGroup_ID != 0)
			where.append(" AND pg.UY_ProductGroup_ID=?");
		
		//	Familia
		int UY_Familia_ID = 0;
		KeyNamePair f = (KeyNamePair)pickFamilia.getSelectedItem();
		if (f != null)
			UY_Familia_ID = f.getKey();
		if (UY_Familia_ID != 0)
			where.append(" AND f.UY_Familia_ID=?");	
		
		//	Subamilia
		int UY_SubFamilia_ID = 0;
		KeyNamePair sf = (KeyNamePair)pickSubFamilia.getSelectedItem();
		if (sf != null)
			UY_SubFamilia_ID = sf.getKey();
		if (UY_SubFamilia_ID != 0)
			where.append(" AND sf.UY_SubFamilia_ID=?");
		
		
		
		return where.toString();
	}	//	getSQLWhere RT
	
	
	/**
	 *  Set Parameters for Query
	 *  (as defined in getSQLWhere)
	 *@Override
	 * @param pstmt pstmt
	 *  @param forCount for counting records
	 * @throws SQLException
	 */
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;

		//  => Warehouse
		int M_Warehouse_ID = 0;
		KeyNamePair wh = (KeyNamePair)pickWarehouse.getSelectedItem();
		if (wh != null)
			M_Warehouse_ID = wh.getKey();
		if (!forCount)	//	parameters in select
		{
			for (int i = 0; i < p_layout.length; i++)
			{
				if (p_layout[i].getColSQL().indexOf('?') != -1){
					
					// Gabriel Vila. 18/08/2011. Issue #781.
					// Para funciones de calculo de stock debo mandar warehouse=null cuando el warehouse es CERO
					//pstmt.setInt(index++, M_Warehouse_ID);
					if (M_Warehouse_ID > 0) 
						pstmt.setInt(index++, M_Warehouse_ID);
					else 
						pstmt.setObject(index++, null);
					// Fin #781.
				}
					
			}
		}
		log.fine("M_Warehouse_ID=" + M_Warehouse_ID + " (" + (index-1) + "*)");

		//  => Value
		String value = fieldValue.getText().toUpperCase();
		if (!(value.equals("") || value.equals("%")))
		{
			if (!value.endsWith("%"))
				value += "%";
			pstmt.setString(index++, value);
			log.fine("Value: " + value);
		}

		//  => Name
		String name = fieldName.getText().toUpperCase();
		if (!(name.equals("") || name.equals("%")))
		{
			if (!name.endsWith("%"))
				name += "%";
			pstmt.setString(index++, name);
			log.fine("Name: " + name);
		}

		//  => UPC
		String upc = fieldUPC.getText().toUpperCase();
		if (!(upc.equals("") || upc.equals("%")))
		{
			if (!upc.endsWith("%"))
				upc += "%";
			pstmt.setString(index++, upc);
			log.fine("UPC: " + upc);
		}

		// Seccion
		int UY_Linea_Negocio_ID = 0;
		KeyNamePair ln = (KeyNamePair)pickSeccion.getSelectedItem();
		if (ln != null)
			UY_Linea_Negocio_ID = ln.getKey();
		if (UY_Linea_Negocio_ID != 0)
		{
			pstmt.setInt(index++, UY_Linea_Negocio_ID);
			log.fine("UY_Linea_Negocio_ID=" + UY_Linea_Negocio_ID);
		}
		
		//	Rubro
		int UY_ProductGroup_ID = 0;
		KeyNamePair pg = (KeyNamePair)pickRubro.getSelectedItem();
		if (pg != null)
			UY_ProductGroup_ID = pg.getKey();
		if (UY_ProductGroup_ID != 0){
			pstmt.setInt(index++, UY_ProductGroup_ID);
			log.fine("UY_ProductGroup_ID=" + UY_ProductGroup_ID);
		}	
		
		//	Familia
		int UY_Familia_ID = 0;
		KeyNamePair f = (KeyNamePair)pickFamilia.getSelectedItem();
		if (f != null)
			UY_Familia_ID = f.getKey();
		if (UY_Familia_ID != 0){
			pstmt.setInt(index++, UY_Familia_ID);
			log.fine("UY_Familia_ID=" + UY_Familia_ID);	
		}
		//	Subamilia
		int UY_SubFamilia_ID = 0;
		KeyNamePair sf = (KeyNamePair)pickSubFamilia.getSelectedItem();
		if (sf != null)
			UY_SubFamilia_ID = sf.getKey();
		if (UY_SubFamilia_ID != 0){
			pstmt.setInt(index++, UY_SubFamilia_ID);
			log.fine("UY_SubFamilia_ID=" + UY_SubFamilia_ID);
		}

	}   //  setParameters RT
	
	private void fillRubro(int lineaNegocioID){

		int UY_Linea_Negocio_ID = 0;
		KeyNamePair ln = (KeyNamePair)pickSeccion.getSelectedItem();
		if (ln != null)
			UY_Linea_Negocio_ID = ln.getKey();
		String SQL = "";
		if (UY_Linea_Negocio_ID != 0){
			SQL = "SELECT UY_ProductGroup_ID, Name "
					+ " FROM UY_ProductGroup "
					+ " WHERE IsActive='Y' AND UY_Linea_Negocio_ID = "+UY_Linea_Negocio_ID;
		}else {
			SQL = "SELECT UY_ProductGroup_ID, Name "
					+ " FROM UY_ProductGroup "
					+ " WHERE IsActive='Y' ";
		}	
		SQL =MRole.getDefault().addAccessSQL (SQL,"UY_ProductGroup", 
				MRole.SQL_NOTQUALIFIED, MRole.SQL_RO)+ " ORDER BY Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{		
			pstmt = DB.prepareStatement(SQL, null);
			rs = pstmt.executeQuery();
			pickRubro.removeAllItems();
			pickRubro.addItem(new KeyNamePair (0, ""));
			while (rs.next()){				
				KeyNamePair kn = new KeyNamePair
					(rs.getInt("UY_ProductGroup_ID"), rs.getString("Name"));
				pickRubro.addItem(kn);
			}
			
		}catch (SQLException e){
			log.log(Level.SEVERE, SQL, e);
			setStatusLine(e.getLocalizedMessage(), true);
		}finally{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}
	
	private void fillFamilia(int productGroupIF){
		Dimension dse = pickSeccion.getSize();
		Dimension dru = pickRubro.getSize();
		
		int UY_ProductGroup_ID = 0;
		KeyNamePair pg = (KeyNamePair)pickRubro.getSelectedItem();
		if (pg != null)
			UY_ProductGroup_ID = pg.getKey();
		String SQL = "";
		if(UY_ProductGroup_ID!=0){
			SQL = "SELECT UY_Familia_ID, Name "
						+ " FROM UY_Familia "
						+ " WHERE IsActive='Y' AND UY_ProductGroup_ID = "+UY_ProductGroup_ID;
		}else{
			SQL = "SELECT UY_Familia_ID, Name "
					+ " FROM UY_Familia "
					+ " WHERE IsActive='Y' ";
		}
		SQL =MRole.getDefault().addAccessSQL (SQL,
							"UY_Familia", MRole.SQL_NOTQUALIFIED, MRole.SQL_RO)
						+ " ORDER BY Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{		
			
			pstmt = DB.prepareStatement(SQL, null);
			rs = pstmt.executeQuery();
			pickFamilia.removeAllItems();
			pickFamilia.addItem(new KeyNamePair (0, ""));
			while (rs.next()){				
				KeyNamePair kn = new KeyNamePair
					(rs.getInt("UY_Familia_ID"), rs.getString("Name"));
				pickFamilia.addItem(kn);
			}			
		}catch (SQLException e){
			log.log(Level.SEVERE, SQL, e);
			setStatusLine(e.getLocalizedMessage(), true);
		}finally{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}
	class MyActionListener implements ActionListener {
		  Object oldItem;
		  @Override
		  public void actionPerformed(ActionEvent evt) {
		    JComboBox cb = (JComboBox) evt.getSource();
		    Object newItem = cb.getSelectedItem();

		    if(newItem!=null){
//		    	boolean same = newItem.equals(oldItem);
//			    oldItem = newItem;
			    if ("comboBoxEdited".equals(evt.getActionCommand())) {
			      // User has typed in a string; only possible with an editable combobox
			    } else if ("comboBoxChanged".equals(evt.getActionCommand())) {
			    	Object source = evt.getSource();
					if(source == pickSeccion){
						fillRubro(0);
					}else if(source == pickRubro){
						fillFamilia(0);
					}
			    }
		    }
		    
		  }
	}

		
}
