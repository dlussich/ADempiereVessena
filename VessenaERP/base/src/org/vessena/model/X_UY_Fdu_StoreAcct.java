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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_Fdu_StoreAcct
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_StoreAcct extends PO implements I_UY_Fdu_StoreAcct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130926L;

    /** Standard Constructor */
    public X_UY_Fdu_StoreAcct (Properties ctx, int UY_Fdu_StoreAcct_ID, String trxName)
    {
      super (ctx, UY_Fdu_StoreAcct_ID, trxName);
      /** if (UY_Fdu_StoreAcct_ID == 0)
        {
			setGL_Journal_ID (0);
			setGL_JournalLine_ID (0);
			setTipoDato (Env.ZERO);
			setUY_FduFile_ID (0);
			setUY_FduLoad_ID (0);
			setUY_Fdu_StoreAcct_ID (0);
			setUY_Fdu_Store_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_StoreAcct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_StoreAcct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_GL_Journal getGL_Journal() throws RuntimeException
    {
		return (org.compiere.model.I_GL_Journal)MTable.get(getCtx(), org.compiere.model.I_GL_Journal.Table_Name)
			.getPO(getGL_Journal_ID(), get_TrxName());	}

	/** Set Journal.
		@param GL_Journal_ID 
		General Ledger Journal
	  */
	public void setGL_Journal_ID (int GL_Journal_ID)
	{
		if (GL_Journal_ID < 1) 
			set_Value (COLUMNNAME_GL_Journal_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Journal_ID, Integer.valueOf(GL_Journal_ID));
	}

	/** Get Journal.
		@return General Ledger Journal
	  */
	public int getGL_Journal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Journal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_GL_JournalLine getGL_JournalLine() throws RuntimeException
    {
		return (org.compiere.model.I_GL_JournalLine)MTable.get(getCtx(), org.compiere.model.I_GL_JournalLine.Table_Name)
			.getPO(getGL_JournalLine_ID(), get_TrxName());	}

	/** Set Journal Line.
		@param GL_JournalLine_ID 
		General Ledger Journal Line
	  */
	public void setGL_JournalLine_ID (int GL_JournalLine_ID)
	{
		if (GL_JournalLine_ID < 1) 
			set_Value (COLUMNNAME_GL_JournalLine_ID, null);
		else 
			set_Value (COLUMNNAME_GL_JournalLine_ID, Integer.valueOf(GL_JournalLine_ID));
	}

	/** Get Journal Line.
		@return General Ledger Journal Line
	  */
	public int getGL_JournalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_JournalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TipoDato.
		@param TipoDato 
		TipoDato
	  */
	public void setTipoDato (BigDecimal TipoDato)
	{
		set_Value (COLUMNNAME_TipoDato, TipoDato);
	}

	/** Get TipoDato.
		@return TipoDato
	  */
	public BigDecimal getTipoDato () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TipoDato);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_FduFile getUY_FduFile() throws RuntimeException
    {
		return (I_UY_FduFile)MTable.get(getCtx(), I_UY_FduFile.Table_Name)
			.getPO(getUY_FduFile_ID(), get_TrxName());	}

	/** Set UY_FduFile_ID.
		@param UY_FduFile_ID UY_FduFile_ID	  */
	public void setUY_FduFile_ID (int UY_FduFile_ID)
	{
		if (UY_FduFile_ID < 1) 
			set_Value (COLUMNNAME_UY_FduFile_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduFile_ID, Integer.valueOf(UY_FduFile_ID));
	}

	/** Get UY_FduFile_ID.
		@return UY_FduFile_ID	  */
	public int getUY_FduFile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduFile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FduLoad getUY_FduLoad() throws RuntimeException
    {
		return (I_UY_FduLoad)MTable.get(getCtx(), I_UY_FduLoad.Table_Name)
			.getPO(getUY_FduLoad_ID(), get_TrxName());	}

	/** Set UY_FduLoad.
		@param UY_FduLoad_ID UY_FduLoad	  */
	public void setUY_FduLoad_ID (int UY_FduLoad_ID)
	{
		if (UY_FduLoad_ID < 1) 
			set_Value (COLUMNNAME_UY_FduLoad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduLoad_ID, Integer.valueOf(UY_FduLoad_ID));
	}

	/** Get UY_FduLoad.
		@return UY_FduLoad	  */
	public int getUY_FduLoad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduLoad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_StoreAcct.
		@param UY_Fdu_StoreAcct_ID UY_Fdu_StoreAcct	  */
	public void setUY_Fdu_StoreAcct_ID (int UY_Fdu_StoreAcct_ID)
	{
		if (UY_Fdu_StoreAcct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_StoreAcct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_StoreAcct_ID, Integer.valueOf(UY_Fdu_StoreAcct_ID));
	}

	/** Get UY_Fdu_StoreAcct.
		@return UY_Fdu_StoreAcct	  */
	public int getUY_Fdu_StoreAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_StoreAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Fdu_Store getUY_Fdu_Store() throws RuntimeException
    {
		return (I_UY_Fdu_Store)MTable.get(getCtx(), I_UY_Fdu_Store.Table_Name)
			.getPO(getUY_Fdu_Store_ID(), get_TrxName());	}

	/** Set UY_Fdu_Store.
		@param UY_Fdu_Store_ID UY_Fdu_Store	  */
	public void setUY_Fdu_Store_ID (int UY_Fdu_Store_ID)
	{
		if (UY_Fdu_Store_ID < 1) 
			set_Value (COLUMNNAME_UY_Fdu_Store_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Fdu_Store_ID, Integer.valueOf(UY_Fdu_Store_ID));
	}

	/** Get UY_Fdu_Store.
		@return UY_Fdu_Store	  */
	public int getUY_Fdu_Store_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_Store_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}