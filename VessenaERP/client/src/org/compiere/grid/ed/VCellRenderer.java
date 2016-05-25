/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.grid.ed;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 *  Table Cell Renderer based on DisplayType
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VCellRenderer.java,v 1.4 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Teo Sarca
 * 		<li>FR [ 2866571 ] VCellRenderer: implement getters
 * 			https://sourceforge.net/tracker/?func=detail&aid=2866571&group_id=176962&atid=879335</li>
 * 		<li>FR [ 3051618 ] VCellRenderer: preferred width from field.</li>
 */
public final class VCellRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3135422746697244864L;

	/**
	 *	Constructor for Grid
	 *  @param mField field model
	 */
	public VCellRenderer(GridField mField)
	{
		this (mField.getDisplayType());
		m_columnName = mField.getColumnName();
		this.setName(m_columnName);
		m_lookup = mField.getLookup();
		m_password = mField.isEncryptedField();
		if (mField.getPreferredWidthInListView()>0) {
			int height = this.getPreferredSize().height;
			setPreferredSize(new Dimension(mField.getPreferredWidthInListView(), height));
		}
		
		// OpenUp. Gabriel Vila. 20/12/2012. Issue #210
		// Respeto FormatPattern puesto en la definicion de la columna.
		if (mField.getAD_Column_ID() > 0){
			MColumn column = new MColumn(Env.getCtx(), mField.getAD_Column_ID(), null);
			if (column.getFormatPattern() != null){
				if (!column.getFormatPattern().equalsIgnoreCase("")){
					if (mField.getDisplayType() == DisplayType.Amount){
						m_numberFormat = new DecimalFormat(column.getFormatPattern());
						//m_numberFormat.setNegativePrefix("(");
						//m_numberFormat.setNegativeSuffix(")");
					}
					else if (mField.getDisplayType() == DisplayType.Number){
						m_numberFormat = new DecimalFormat(column.getFormatPattern());
					}
				}
			}
		}
		// Fin OpenUp.
		
	}	//	VCellRenderer

	/**
	 *  Constructor for MiniGrid
	 *  @param displayType Display Type
	 */
	public VCellRenderer (int displayType)
	{
		super();
		m_displayType = displayType;
		//	Number
		if (DisplayType.isNumeric(m_displayType))
		{
			m_numberFormat = DisplayType.getNumberFormat(m_displayType);
			setHorizontalAlignment(JLabel.RIGHT);
		}
		//	Date
		else if (DisplayType.isDate(m_displayType))
			m_dateFormat = DisplayType.getDateFormat(m_displayType);
		//
		else if (m_displayType == DisplayType.YesNo)
		{
			m_check = new JCheckBox();
			m_check.setMargin(new Insets(0,0,0,0));
			m_check.setHorizontalAlignment(JLabel.CENTER);
			m_check.setOpaque(true);
		}
	}   //  VCellRenderer

	private int 				m_displayType;
	private String				m_columnName = null;
	private Lookup              m_lookup = null;
	private boolean             m_password = false;
	//
	private SimpleDateFormat 	m_dateFormat = null;
	private DecimalFormat		m_numberFormat = null;
	private JCheckBox           m_check = null;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VCellRenderer.class);
	
	/**
	 *	Get TableCell RendererComponent.
	 *  @param table table
	 *  @param value value
	 *  @param isSelected selected
	 *  @param hasFocus focus
	 *  @param row row
	 *  @param col col
	 *  @return component
	 */
	public Component getTableCellRendererComponent (JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int col)
	{
	//	log.finest(m_columnName 
	//		+ ": Value=" + (value == null ? "null" : value.toString()) 
	//		+ ", Row=" + row + ", Col=" + col);

		Component c = null;
		if (m_displayType == DisplayType.YesNo)
			c = m_check;
		else
		{	//	returns JLabel
			c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
			//c.setFont(AdempierePLAF.getFont_Field());
			c.setFont(table.getFont());
		}
	
		//  Background & Foreground
		Color bg = AdempierePLAF.getFieldBackground_Normal();
		Color fg = AdempierePLAF.getTextColor_Normal();
		//  Inactive Background
		boolean ro = !table.isCellEditable(row, col); 
		if (ro)
		{
			bg = AdempierePLAF.getFieldBackground_Inactive();
			if (isSelected && !hasFocus)
				bg = bg.darker();
		}
		
		//  Foreground
		int cCode = 0;
		//  Grid
		if (table instanceof org.compiere.grid.VTable)
			cCode = ((org.compiere.grid.VTable)table).getColorCode (row);
		//  MiniGrid
		else if (table instanceof org.compiere.minigrid.MiniTable)
			cCode = ((org.compiere.minigrid.MiniTable)table).getColorCode (row);
		//
		if (cCode == 0)
			;											//	Black
		else if (cCode < 0)
			fg = AdempierePLAF.getTextColor_Issue();		//	Red
		else
			fg = AdempierePLAF.getTextColor_OK();		//	Blue
		
		// OpenUp. Gabriel Vila. 20/12/2012. Issue #211.
		// Cambio de color segun campo selector presente o no en tabla
		if (table instanceof org.compiere.grid.VTable){
			Color fgSelector = ((GridTable)((org.compiere.grid.VTable)table).getModel()).setColumnColorSelectorForeGround(row);
			if (fgSelector != null){
				fg = fgSelector;
			}
			
			Color bgSelector = ((GridTable)((org.compiere.grid.VTable)table).getModel()).setColumnColorSelectorBackGround(row);
			if (bgSelector != null){
				bg = fgSelector;
			}
		}
		// Fin OpenUp
		
		//	Highlighted row
		if (isSelected)
		{
		//	Windows is white on blue
			bg = table.getSelectionBackground();
			fg = table.getSelectionForeground();
			if (hasFocus)
				bg = org.adempiere.apps.graph.GraphUtil.brighter(bg, .9);
		}
		
		//  Set Color
		c.setBackground(bg);
		c.setForeground(fg);
		//
	//	log.fine( "r=" + row + " c=" + col, // + " - " + c.getClass().getName(),
	//		"sel=" + isSelected + ", focus=" + hasFocus + ", edit=" + table.isCellEditable(row, col));
	//	Log.trace(7, "BG=" + (bg.equals(Color.white) ? "white" : bg.toString()), "FG=" + (fg.equals(Color.black) ? "black" : fg.toString()));

		// OpenUp. Gabriel Vila. 05/02/2013. Issue #323
		// Si tengo una imagen la despliego como tal
		if (m_displayType == DisplayType.Image){
			if (value != null){
				int imagenID = (Integer)value;
				if (imagenID == 1000022){
					c.setBackground(Color.RED);
					c.setForeground(Color.RED);
				}
				else if (imagenID == 1000023){
					c.setBackground(Color.GREEN);
					c.setForeground(Color.GREEN);
				}
				else if (imagenID == 1000024){
					c.setBackground(Color.YELLOW);
					c.setForeground(Color.YELLOW);
				}
				else if (imagenID == 1000025){
					c.setBackground(Color.GRAY);
					c.setForeground(Color.GRAY);
				}
				/*	
				MImage imagenbase = new MImage(Env.getCtx(), imagenID, null);
				//ImageIcon icono = new ImageIcon("c:\\desarrollo\\rojo.png");
				ImageIcon icono = new ImageIcon(imagenbase.getBinaryData());
				c = new JLabel(icono);
				table.setRowHeight(row, icono.getIconHeight());
				*/
				return c;
			}
		}
		// Fin OpenUp.

		//  Format it
		setValue(value);		
		return c;
		
	}	//	getTableCellRendererComponent


	/**
	 *	Format Display Value.
	 *	Default is JLabel
	 *  @param value (key)value
	 */
	protected void setValue (Object value)
	{
		String retValue = null;
		try
		{
			//  Checkbox
			if (m_displayType == DisplayType.YesNo)
			{
				if (value instanceof Boolean)
					m_check.setSelected(((Boolean)value).booleanValue());
				else
					m_check.setSelected("Y".equals(value));
				return;
			}
			else if (value == null)
				;
			//	Number
			else if (DisplayType.isNumeric(m_displayType))
				retValue = m_numberFormat.format(value);
			//	Date
			else if (DisplayType.isDate(m_displayType))
				retValue = m_dateFormat.format(value);
			//	Row ID
			else if (m_displayType == DisplayType.RowID)
				retValue = "";
			//	Lookup
			else if (m_lookup != null && (DisplayType.isLookup(m_displayType)
					|| m_displayType == DisplayType.Location
					|| m_displayType == DisplayType.Account
					|| m_displayType == DisplayType.Locator
					|| m_displayType == DisplayType.PAttribute ))
				retValue = m_lookup.getDisplay(value);
			//	Button
			else if (m_displayType == DisplayType.Button)
			{
				if ("Record_ID".equals(m_columnName))
					retValue = "#" + value + "#";
				else
					retValue = null;
			}
			//  Password (fixed string)
			else if (m_password)
				retValue = "**********";
			//	other (String ...)
			else
			{
				super.setValue(value);
				return;
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "(" + value + ") " + value.getClass().getName() , e);
			retValue = value.toString();
		}
		super.setValue(retValue);
	}	//	setValue

	/**
	 *  to String
	 *  @return String representation
	 */
	public String toString()
	{
		return "VCellRenderer[" + m_columnName 
			+ ",DisplayType=" + m_displayType + " - " + m_lookup + "]";
	}   //  toString

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		if (m_lookup != null)
			m_lookup.dispose();
		m_lookup = null;
	}	//	dispose

	public String getColumnName()
	{
		return m_columnName;
	}
	
	public Lookup getLookup()
	{
		return m_lookup;
	}

	public int getDisplayType()
	{
		return m_displayType;
	}

	public boolean isPassword()
	{
		return m_password;
	}
}	//	VCellRenderer
