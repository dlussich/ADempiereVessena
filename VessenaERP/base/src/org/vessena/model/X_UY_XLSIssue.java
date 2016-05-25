/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_XLSIssue
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_XLSIssue extends PO implements I_UY_XLSIssue, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130923L;

    /** Standard Constructor */
    public X_UY_XLSIssue (Properties ctx, int UY_XLSIssue_ID, String trxName)
    {
      super (ctx, UY_XLSIssue_ID, trxName);
      /** if (UY_XLSIssue_ID == 0)
        {
			setAD_Table_ID (0);
			setErrorMsg (null);
			setRecord_ID (0);
			setUY_XLSIssue_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_XLSIssue (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UY_XLSIssue[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set cell.
		@param cell cell	  */
	public void setcell (String cell)
	{
		set_Value (COLUMNNAME_cell, cell);
	}

	/** Get cell.
		@return cell	  */
	public String getcell () 
	{
		return (String)get_Value(COLUMNNAME_cell);
	}

	/** Set Content.
		@param ContentText Content	  */
	public void setContentText (String ContentText)
	{
		set_Value (COLUMNNAME_ContentText, ContentText);
	}

	/** Get Content.
		@return Content	  */
	public String getContentText () 
	{
		return (String)get_Value(COLUMNNAME_ContentText);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set sheet.
		@param sheet sheet	  */
	public void setsheet (String sheet)
	{
		set_Value (COLUMNNAME_sheet, sheet);
	}

	/** Get sheet.
		@return sheet	  */
	public String getsheet () 
	{
		return (String)get_Value(COLUMNNAME_sheet);
	}

	/** TypeLoadXLS AD_Reference_ID=1000096 */
	public static final int TYPELOADXLS_AD_Reference_ID=1000096;
	/** Carga de Productos = PR */
	public static final String TYPELOADXLS_CargaDeProductos = "PR";
	/** Novedades de Nomina = NN */
	public static final String TYPELOADXLS_NovedadesDeNomina = "NN";
	/** Devoluciones = DV */
	public static final String TYPELOADXLS_Devoluciones = "DV";
	/** Grupo Clientes Promociones = GR */
	public static final String TYPELOADXLS_GrupoClientesPromociones = "GR";
	/** Set TypeLoadXLS.
		@param TypeLoadXLS TypeLoadXLS	  */
	public void setTypeLoadXLS (String TypeLoadXLS)
	{

		set_Value (COLUMNNAME_TypeLoadXLS, TypeLoadXLS);
	}

	/** Get TypeLoadXLS.
		@return TypeLoadXLS	  */
	public String getTypeLoadXLS () 
	{
		return (String)get_Value(COLUMNNAME_TypeLoadXLS);
	}

	/** Set UY_XLSIssue.
		@param UY_XLSIssue_ID UY_XLSIssue	  */
	public void setUY_XLSIssue_ID (int UY_XLSIssue_ID)
	{
		if (UY_XLSIssue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_XLSIssue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_XLSIssue_ID, Integer.valueOf(UY_XLSIssue_ID));
	}

	/** Get UY_XLSIssue.
		@return UY_XLSIssue	  */
	public int getUY_XLSIssue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_XLSIssue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}