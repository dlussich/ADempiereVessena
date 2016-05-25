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
package org.compiere.grid;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import javax.swing.Action;
import javax.swing.table.TableModel;

import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.apps.ProcessCtl;
import org.compiere.grid.ed.VCellEditor;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridTab;
import org.compiere.model.GridTable;
import org.compiere.model.MDocType;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.swing.CColumnControlButton;
import org.compiere.swing.CTable;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.jdesktop.swingx.action.BoundAction;
import org.openup.model.MRReclamo;

/**
 * Table Grid based on CTable.
 * Used in GridController
 *
 * @author  Jorg Janke
 * @version $Id: VTable.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author 	Teo Sarca, SC ARHIPAC SERVICE SRL - FR [ 1753943 ]
 */
public final class VTable extends CTable 
	implements PropertyChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2655102084935019329L;
	private final static String PACK_ALL_COMMAND = CColumnControlButton.COLUMN_CONTROL_MARKER + "packAll";
	
	
	// OpenUp. Gabriel Vila. 20/05/2013. Issue #846.
	// Indices de columnas para abrir ventanas con doble-click
	boolean doubleclickColumnsSet = false;
	int cDocTypeColumnIndex = -1;
	int recordIDColumnIndex = -1;
	int adTableIDColumnIndex = -1;
	int adProcessIDColumnIndex = -1;
	// Fin OpenUp. Issue #846
	
	/**
	 *	Default Constructor
	 */
	public VTable()
	{
		super();
		setAutoscrolls(true);
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		new VTableExcelAdapter(this); // teo_sarca - FR [ 1753943 ]
		
		getActionMap().put(PACK_ALL_COMMAND, createPackAllAction());
		
		// OpenUp. Gabriel Vila. 20/05/2013. Issue #846.
		// Agrego evento para captura de DobleClicks en rows.
		this.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			    	  VTable target = (VTable)e.getSource();			    	  
			    	  target.openWindow(target.getSelectedRow());
			         //int row = target.getSelectedRow();
			         //int column = target.getSelectedColumn();
			      }
			   }
		});		
		// Fin OpenUp.
		
	}	//	VTable
	
	
	private Action createPackAllAction() 
	{
		//TODO: localization
		BoundAction action = new BoundAction("Size All Column", PACK_ALL_COMMAND);
		action.setLongDescription("Size all column to fit content");
		action.registerCallback(this, "packAll");
		return action;
	}
	
	/**
	 * Size all column to fit content.
	 */
	public void packAll() 
	{
		autoSize(true);
	}

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VTable.class);
	
	/**
	 *  Property Change Listener for CurrentRow.
	 *  - Selects the current row if not already selected
	 *  - Required when navigating via Buttons
	 *  @param evt event
	 */
	public void propertyChange(PropertyChangeEvent evt)
	{
	//	log.config(evt);
		if (evt.getPropertyName().equals(GridTab.PROPERTY))
		{
			int row = ((Integer)evt.getNewValue()).intValue();
			int selRow = getSelectedRow();
			if (row == selRow)
				return;
			log.config(GridTab.PROPERTY + "=" + row + " from " + selRow);
			setRowSelectionInterval(row,row);
			setColumnSelectionInterval(0, 0);
		    Rectangle cellRect = getCellRect(row, 0, false);
		    if (cellRect != null)
		    	scrollRectToVisible(cellRect);
			log.config(GridTab.PROPERTY + "=" + row + " from " + selRow);
		}
	}   //  propertyChange

	/**
	 *	Get ColorCode for Row.
	 *  <pre>
	 *	If numerical value in compare column is
	 *		negative = -1,
	 *      positive = 1,
	 *      otherwise = 0
	 *  </pre>
	 *  @param row row
	 *  @return color code
	 */
	public int getColorCode (int row)
	{
		return ((GridTable)getModel()).getColorCode(row);
	}   //  getColorCode

	/**
	 *  Sort Table
	 *  @param modelColumnIndex model column sort index
	 */
	protected void sort (int modelColumnIndex)
	{
		int rows = getRowCount();
		if (rows == 0)
			return;
		//
		TableModel model = getModel();
		if (!(model instanceof GridTable))
		{
			super.sort(modelColumnIndex);
			return;
		}

		sorting = true;
		//  other sort column
		if (modelColumnIndex != p_lastSortIndex)
			p_asc = true;
		else
			p_asc = !p_asc;

		p_lastSortIndex = modelColumnIndex;
		//
		log.config("#" + modelColumnIndex
			+ " - rows=" + rows + ", asc=" + p_asc);

		((GridTable)model).sort(modelColumnIndex, p_asc);
		
		sorting = false;
		//  table model fires "Sorted" DataStatus event which causes MTab to position to row 0
	}   //  sort

	/**
	 *  Transfer focus explicitly to editor due to editors with multiple components
	 *
	 *  @param row row
	 *  @param column column
	 *  @param e event
	 *  @return true if cell is editing
	 */
	public boolean editCellAt (int row, int column, java.util.EventObject e)
	{
		if (!super.editCellAt(row, column, e))
			return false;
	//	log.fine( "VTable.editCellAt", "r=" + row + ", c=" + column);

		Object ed = getCellEditor();
		if (ed instanceof VEditor)
			((Component)ed).requestFocus();
		else if (ed instanceof VCellEditor)
		{
			ed = ((VCellEditor)ed).getEditor();
			((Component)ed).requestFocus();
		}
		return true;
	}   //  editCellAt

	/**
	 *  toString
	 *  @return String representation
	 */
	public String toString()
	{
		return new StringBuffer("VTable[")
			.append(getModel()).append("]").toString();
	}   //  toString


	/***
	 * Obtiene y guarda indices de columna de esta grilla necesarias para abrir ventanas con doble-click.
	 * OpenUp Ltda. Issue #846 
	 * @author Gabriel Vila - 20/05/2013
	 * @see
	 */
	private void setDoubleClickColumnIndex() {

		try{
			int colCount = this.getColumnCount();
            
			for (int col = 0; col  <  colCount; col++)
            {
                String colname = getColumnName(col);
                if (colname != null){
                	if (colname.equalsIgnoreCase("C_DocType_ID")){
                		this.cDocTypeColumnIndex = col;
                	}
                	else if (colname.equalsIgnoreCase("Record_ID")){
                		this.recordIDColumnIndex = col;
                	}
                	else if (colname.equalsIgnoreCase("AD_Table_ID")){
                		this.adTableIDColumnIndex = col;
                	}
                	else if (colname.equalsIgnoreCase("AD_Process_ID")){
                		this.adProcessIDColumnIndex = col;
                	}
                }
                
            }			
			
		}
		catch (Exception e){
			// No hago nada en caso de exception.
		}
		
	}

	
	/***
	 * Dada una Row de esta grilla, si en la misma se encuentran las columnas de documento y registro,
	 * se intenta desplegar ventana asociada a este documento.
	 * OpenUp Ltda. Issue #846 
	 * @author Gabriel Vila - 20/05/2013
	 * @see
	 * @param selectedRow
	 */
	private void openWindow(int selectedRow) {
		
		int cDocTypeID = -1, recordID = -1, adTableID = -1;
		
		try{
			
			if (!doubleclickColumnsSet){
				// Obtengo indices de columnas necesarias para abrir ventanas con doble-click.
				this.setDoubleClickColumnIndex();
			}
			
			// Si tengo id de proceso, ejecuto el proceso y no abro ninguna ventana.
			if (this.adProcessIDColumnIndex > 0){
				
				if (this.recordIDColumnIndex > 0){
					recordID = (Integer)getValueAt(selectedRow, this.recordIDColumnIndex);	
				}
				else{
					recordID = 0;
				}
				
				int adProcessID = (Integer)getValueAt(selectedRow, this.adProcessIDColumnIndex);

				MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
				instance.saveEx();

				ProcessInfo pi = new ProcessInfo ("ADProcessVTable", adProcessID);
				pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
				
				TableModel model = this.getModel();
				if (model instanceof GridTable){
					//((GridTable)model).get
				}
			
				MPInstancePara para = new MPInstancePara(instance, 10);
				para.setParameter("Record_ID", new BigDecimal(recordID));
				para.saveEx();
				
				ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
				worker.start();   
				
				return;
			}
			
			if (this.recordIDColumnIndex > 0){
				recordID = (Integer)getValueAt(selectedRow, this.recordIDColumnIndex);	
			}
			else{
				return;
			}
			if (this.cDocTypeColumnIndex > 0){
				cDocTypeID = (Integer)getValueAt(selectedRow, this.cDocTypeColumnIndex);	
			}
			if (this.adTableIDColumnIndex > 0){
				adTableID = (Integer)getValueAt(selectedRow, this.adTableIDColumnIndex);	
			}

			if ((cDocTypeID <= 0) && (adTableID <= 0)){
				return;
			}
			
			// Instancio modelo de documento en caso de tenerlo
			MDocType doc = new MDocType(Env.getCtx(), cDocTypeID, null);
			
			// Instancio modelo de tabla
			MTable table = new MTable(Env.getCtx(), adTableID, null);
			
			String whereClause = table.getTableName() + "_ID =" + recordID;
			AWindow poFrame = new AWindow(null);
			MQuery query = new MQuery(table.getTableName());
			query.addRestriction(whereClause);
			
			int adWindowID = table.getAD_Window_ID();
			
			PO po = table.getPO(recordID, null);
			
			if (po instanceof MRReclamo){
				((MRReclamo)po).inboxTaken();
			}
			
			if (doc.get_ID() > 0){
				if (!doc.isSOTrx()) adWindowID = table.getPO_Window_ID();
				if (doc.getAD_Window_ID() > 0) adWindowID = doc.getAD_Window_ID();
			}
			
			this.getParent().setCursor(new Cursor(Cursor.WAIT_CURSOR));
			
			boolean ok = poFrame.initWindow(adWindowID, query);
			if (ok){
				poFrame.pack(); 
				AEnv.showCenterScreen(poFrame);
				poFrame.toFront();
			}
		}
		catch (Exception e){
			// No hago nada en caso de exception.
		}
		finally{
			this.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
	}

	/***
	 * Obtiene y retorna rows seleccionadas.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 10/10/2013
	 * @see
	 * @return
	 */
	public int[] getSelection(){
		return this.getSelectedRows();
	}
	
}	//	VTable
