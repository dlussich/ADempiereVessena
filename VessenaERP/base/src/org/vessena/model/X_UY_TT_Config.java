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

/** Generated Model for UY_TT_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_Config extends PO implements I_UY_TT_Config, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160301L;

    /** Standard Constructor */
    public X_UY_TT_Config (Properties ctx, int UY_TT_Config_ID, String trxName)
    {
      super (ctx, UY_TT_Config_ID, trxName);
      /** if (UY_TT_Config_ID == 0)
        {
			setIsForzed (false);
// N
			setTopBox (0);
			setTopSeal (0);
			setUY_TT_Config_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_Config (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_Config[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set EmpCodeRedPagos.
		@param EmpCodeRedPagos EmpCodeRedPagos	  */
	public void setEmpCodeRedPagos (String EmpCodeRedPagos)
	{
		set_Value (COLUMNNAME_EmpCodeRedPagos, EmpCodeRedPagos);
	}

	/** Get EmpCodeRedPagos.
		@return EmpCodeRedPagos	  */
	public String getEmpCodeRedPagos () 
	{
		return (String)get_Value(COLUMNNAME_EmpCodeRedPagos);
	}

	/** Set IsForzed.
		@param IsForzed 
		IsForzed
	  */
	public void setIsForzed (boolean IsForzed)
	{
		set_Value (COLUMNNAME_IsForzed, Boolean.valueOf(IsForzed));
	}

	/** Get IsForzed.
		@return IsForzed
	  */
	public boolean isForzed () 
	{
		Object oo = get_Value(COLUMNNAME_IsForzed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MailText1.
		@param MailText1 MailText1	  */
	public void setMailText1 (String MailText1)
	{
		set_Value (COLUMNNAME_MailText1, MailText1);
	}

	/** Get MailText1.
		@return MailText1	  */
	public String getMailText1 () 
	{
		return (String)get_Value(COLUMNNAME_MailText1);
	}

	/** Set Mora.
		@param Mora 
		Mora
	  */
	public void setMora (int Mora)
	{
		set_Value (COLUMNNAME_Mora, Integer.valueOf(Mora));
	}

	/** Get Mora.
		@return Mora
	  */
	public int getMora () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Mora);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Password.
		@param Password 
		Password of any length (case sensitive)
	  */
	public void setPassword (String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Password.
		@return Password of any length (case sensitive)
	  */
	public String getPassword () 
	{
		return (String)get_Value(COLUMNNAME_Password);
	}

	/** Set TopBox.
		@param TopBox TopBox	  */
	public void setTopBox (int TopBox)
	{
		set_Value (COLUMNNAME_TopBox, Integer.valueOf(TopBox));
	}

	/** Get TopBox.
		@return TopBox	  */
	public int getTopBox () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TopBox);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TopSeal.
		@param TopSeal TopSeal	  */
	public void setTopSeal (int TopSeal)
	{
		set_Value (COLUMNNAME_TopSeal, Integer.valueOf(TopSeal));
	}

	/** Get TopSeal.
		@return TopSeal	  */
	public int getTopSeal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TopSeal);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID(), get_TrxName());	}

	/** Set UY_DeliveryPoint.
		@param UY_DeliveryPoint_ID UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID)
	{
		if (UY_DeliveryPoint_ID < 1) 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, Integer.valueOf(UY_DeliveryPoint_ID));
	}

	/** Get UY_DeliveryPoint.
		@return UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Area getUY_R_Area() throws RuntimeException
    {
		return (I_UY_R_Area)MTable.get(getCtx(), I_UY_R_Area.Table_Name)
			.getPO(getUY_R_Area_ID(), get_TrxName());	}

	/** Set UY_R_Area.
		@param UY_R_Area_ID UY_R_Area	  */
	public void setUY_R_Area_ID (int UY_R_Area_ID)
	{
		if (UY_R_Area_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Area_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Area_ID, Integer.valueOf(UY_R_Area_ID));
	}

	/** Get UY_R_Area.
		@return UY_R_Area	  */
	public int getUY_R_Area_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Area_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Area_ID_2.
		@param UY_R_Area_ID_2 UY_R_Area_ID_2	  */
	public void setUY_R_Area_ID_2 (int UY_R_Area_ID_2)
	{
		set_Value (COLUMNNAME_UY_R_Area_ID_2, Integer.valueOf(UY_R_Area_ID_2));
	}

	/** Get UY_R_Area_ID_2.
		@return UY_R_Area_ID_2	  */
	public int getUY_R_Area_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Area_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException
    {
		return (I_UY_R_PtoResolucion)MTable.get(getCtx(), I_UY_R_PtoResolucion.Table_Name)
			.getPO(getUY_R_PtoResolucion_ID(), get_TrxName());	}

	/** Set UY_R_PtoResolucion.
		@param UY_R_PtoResolucion_ID UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID)
	{
		if (UY_R_PtoResolucion_ID < 1) 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, Integer.valueOf(UY_R_PtoResolucion_ID));
	}

	/** Get UY_R_PtoResolucion.
		@return UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_PtoResolucion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_PtoResolucion_ID_2.
		@param UY_R_PtoResolucion_ID_2 UY_R_PtoResolucion_ID_2	  */
	public void setUY_R_PtoResolucion_ID_2 (int UY_R_PtoResolucion_ID_2)
	{
		set_Value (COLUMNNAME_UY_R_PtoResolucion_ID_2, Integer.valueOf(UY_R_PtoResolucion_ID_2));
	}

	/** Get UY_R_PtoResolucion_ID_2.
		@return UY_R_PtoResolucion_ID_2	  */
	public int getUY_R_PtoResolucion_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_PtoResolucion_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_Config.
		@param UY_TT_Config_ID UY_TT_Config	  */
	public void setUY_TT_Config_ID (int UY_TT_Config_ID)
	{
		if (UY_TT_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Config_ID, Integer.valueOf(UY_TT_Config_ID));
	}

	/** Get UY_TT_Config.
		@return UY_TT_Config	  */
	public int getUY_TT_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}