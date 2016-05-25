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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_ClockInterface_Detail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ClockInterface_Detail extends PO implements I_UY_ClockInterface_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120509L;

    /** Standard Constructor */
    public X_UY_ClockInterface_Detail (Properties ctx, int UY_ClockInterface_Detail_ID, String trxName)
    {
      super (ctx, UY_ClockInterface_Detail_ID, trxName);
      /** if (UY_ClockInterface_Detail_ID == 0)
        {
			setfechahora (new Timestamp( System.currentTimeMillis() ));
			setFileName (null);
			setnrolegajo (null);
			setoriginal_line (null);
			settipomarca_id (0);
			setUY_ClockInterface_Detail_ID (0);
			setuy_clockinterface_filter_ID (0);
			setuy_procesar (false);
        } */
    }

    /** Load Constructor */
    public X_UY_ClockInterface_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ClockInterface_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set fechahora.
		@param fechahora fechahora	  */
	public void setfechahora (Timestamp fechahora)
	{
		set_ValueNoCheck (COLUMNNAME_fechahora, fechahora);
	}

	/** Get fechahora.
		@return fechahora	  */
	public Timestamp getfechahora () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechahora);
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_ValueNoCheck (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set manual.
		@param manual manual	  */
	public void setmanual (boolean manual)
	{
		set_ValueNoCheck (COLUMNNAME_manual, Boolean.valueOf(manual));
	}

	/** Get manual.
		@return manual	  */
	public boolean ismanual () 
	{
		Object oo = get_Value(COLUMNNAME_manual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set nrolegajo.
		@param nrolegajo nrolegajo	  */
	public void setnrolegajo (String nrolegajo)
	{
		set_ValueNoCheck (COLUMNNAME_nrolegajo, nrolegajo);
	}

	/** Get nrolegajo.
		@return nrolegajo	  */
	public String getnrolegajo () 
	{
		return (String)get_Value(COLUMNNAME_nrolegajo);
	}

	/** Set original_line.
		@param original_line original_line	  */
	public void setoriginal_line (String original_line)
	{
		set_Value (COLUMNNAME_original_line, original_line);
	}

	/** Get original_line.
		@return original_line	  */
	public String getoriginal_line () 
	{
		return (String)get_Value(COLUMNNAME_original_line);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Text.
		@param Text Text	  */
	public void setText (String Text)
	{
		set_Value (COLUMNNAME_Text, Text);
	}

	/** Get Text.
		@return Text	  */
	public String getText () 
	{
		return (String)get_Value(COLUMNNAME_Text);
	}

	/** Set tipomarca_id.
		@param tipomarca_id tipomarca_id	  */
	public void settipomarca_id (int tipomarca_id)
	{
		set_ValueNoCheck (COLUMNNAME_tipomarca_id, Integer.valueOf(tipomarca_id));
	}

	/** Get tipomarca_id.
		@return tipomarca_id	  */
	public int gettipomarca_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_tipomarca_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ClockInterface_Detail.
		@param UY_ClockInterface_Detail_ID UY_ClockInterface_Detail	  */
	public void setUY_ClockInterface_Detail_ID (int UY_ClockInterface_Detail_ID)
	{
		if (UY_ClockInterface_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ClockInterface_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ClockInterface_Detail_ID, Integer.valueOf(UY_ClockInterface_Detail_ID));
	}

	/** Get UY_ClockInterface_Detail.
		@return UY_ClockInterface_Detail	  */
	public int getUY_ClockInterface_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ClockInterface_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ClockInterface_Filter.
		@param uy_clockinterface_filter_ID UY_ClockInterface_Filter	  */
	public void setuy_clockinterface_filter_ID (int uy_clockinterface_filter_ID)
	{
		if (uy_clockinterface_filter_ID < 1) 
			set_Value (COLUMNNAME_uy_clockinterface_filter_ID, null);
		else 
			set_Value (COLUMNNAME_uy_clockinterface_filter_ID, Integer.valueOf(uy_clockinterface_filter_ID));
	}

	/** Get UY_ClockInterface_Filter.
		@return UY_ClockInterface_Filter	  */
	public int getuy_clockinterface_filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_clockinterface_filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MarcasReloj getUY_MarcasReloj() throws RuntimeException
    {
		return (I_UY_MarcasReloj)MTable.get(getCtx(), I_UY_MarcasReloj.Table_Name)
			.getPO(getUY_MarcasReloj_ID(), get_TrxName());	}

	/** Set UY_MarcasReloj.
		@param UY_MarcasReloj_ID UY_MarcasReloj	  */
	public void setUY_MarcasReloj_ID (int UY_MarcasReloj_ID)
	{
		if (UY_MarcasReloj_ID < 1) 
			set_Value (COLUMNNAME_UY_MarcasReloj_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MarcasReloj_ID, Integer.valueOf(UY_MarcasReloj_ID));
	}

	/** Get UY_MarcasReloj.
		@return UY_MarcasReloj	  */
	public int getUY_MarcasReloj_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MarcasReloj_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_procesar.
		@param uy_procesar uy_procesar	  */
	public void setuy_procesar (boolean uy_procesar)
	{
		set_Value (COLUMNNAME_uy_procesar, Boolean.valueOf(uy_procesar));
	}

	/** Get uy_procesar.
		@return uy_procesar	  */
	public boolean isuy_procesar () 
	{
		Object oo = get_Value(COLUMNNAME_uy_procesar);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}