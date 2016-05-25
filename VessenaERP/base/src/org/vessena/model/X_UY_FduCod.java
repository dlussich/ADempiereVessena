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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_FduCod
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_FduCod extends PO implements I_UY_FduCod, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140502L;

    /** Standard Constructor */
    public X_UY_FduCod (Properties ctx, int UY_FduCod_ID, String trxName)
    {
      super (ctx, UY_FduCod_ID, trxName);
      /** if (UY_FduCod_ID == 0)
        {
			setName (null);
			setUY_FduCod_ID (0);
			setUY_FduFile_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_FduCod (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FduCod[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ApplyIva.
		@param ApplyIva ApplyIva	  */
	public void setApplyIva (boolean ApplyIva)
	{
		set_Value (COLUMNNAME_ApplyIva, Boolean.valueOf(ApplyIva));
	}

	/** Get ApplyIva.
		@return ApplyIva	  */
	public boolean isApplyIva () 
	{
		Object oo = get_Value(COLUMNNAME_ApplyIva);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** CalculateType AD_Reference_ID=1000197 */
	public static final int CALCULATETYPE_AD_Reference_ID=1000197;
	/** Totalizadores = TT */
	public static final String CALCULATETYPE_Totalizadores = "TT";
	/** Consumos = CN */
	public static final String CALCULATETYPE_Consumos = "CN";
	/** Ajustes = AJ */
	public static final String CALCULATETYPE_Ajustes = "AJ";
	/** Set CalculateType.
		@param CalculateType 
		CalculateType
	  */
	public void setCalculateType (String CalculateType)
	{

		set_Value (COLUMNNAME_CalculateType, CalculateType);
	}

	/** Get CalculateType.
		@return CalculateType
	  */
	public String getCalculateType () 
	{
		return (String)get_Value(COLUMNNAME_CalculateType);
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CreditoContado.
		@param CreditoContado CreditoContado	  */
	public void setCreditoContado (boolean CreditoContado)
	{
		set_Value (COLUMNNAME_CreditoContado, Boolean.valueOf(CreditoContado));
	}

	/** Get CreditoContado.
		@return CreditoContado	  */
	public boolean isCreditoContado () 
	{
		Object oo = get_Value(COLUMNNAME_CreditoContado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Cuotas AD_Reference_ID=1000359 */
	public static final int CUOTAS_AD_Reference_ID=1000359;
	/** 1 = 1 */
	public static final String CUOTAS_1 = "1";
	/** 2 = 2 */
	public static final String CUOTAS_2 = "2";
	/** 3 = 3 */
	public static final String CUOTAS_3 = "3";
	/** 4 = 4 */
	public static final String CUOTAS_4 = "4";
	/** 5 = 5 */
	public static final String CUOTAS_5 = "5";
	/** 6 = 6 */
	public static final String CUOTAS_6 = "6";
	/** 7 = 7 */
	public static final String CUOTAS_7 = "7";
	/** 8 = 8 */
	public static final String CUOTAS_8 = "8";
	/** 9 = 9 */
	public static final String CUOTAS_9 = "9";
	/** 10 = 10 */
	public static final String CUOTAS_10 = "10";
	/** 11 = 11 */
	public static final String CUOTAS_11 = "11";
	/** 12 = 12 */
	public static final String CUOTAS_12 = "12";
	/** 13 = 13 */
	public static final String CUOTAS_13 = "13";
	/** Set Cuotas.
		@param Cuotas 
		Cuotas
	  */
	public void setCuotas (String Cuotas)
	{

		set_Value (COLUMNNAME_Cuotas, Cuotas);
	}

	/** Get Cuotas.
		@return Cuotas
	  */
	public String getCuotas () 
	{
		return (String)get_Value(COLUMNNAME_Cuotas);
	}

	/** Cuotas_2 AD_Reference_ID=1000359 */
	public static final int CUOTAS_2_AD_Reference_ID=1000359;
	/** 1 = 1 */
	public static final String CUOTAS_2_1 = "1";
	/** 2 = 2 */
	public static final String CUOTAS_2_2 = "2";
	/** 3 = 3 */
	public static final String CUOTAS_2_3 = "3";
	/** 4 = 4 */
	public static final String CUOTAS_2_4 = "4";
	/** 5 = 5 */
	public static final String CUOTAS_2_5 = "5";
	/** 6 = 6 */
	public static final String CUOTAS_2_6 = "6";
	/** 7 = 7 */
	public static final String CUOTAS_2_7 = "7";
	/** 8 = 8 */
	public static final String CUOTAS_2_8 = "8";
	/** 9 = 9 */
	public static final String CUOTAS_2_9 = "9";
	/** 10 = 10 */
	public static final String CUOTAS_2_10 = "10";
	/** 11 = 11 */
	public static final String CUOTAS_2_11 = "11";
	/** 12 = 12 */
	public static final String CUOTAS_2_12 = "12";
	/** 13 = 13 */
	public static final String CUOTAS_2_13 = "13";
	/** Set Cuotas_2.
		@param Cuotas_2 Cuotas_2	  */
	public void setCuotas_2 (String Cuotas_2)
	{

		set_Value (COLUMNNAME_Cuotas_2, Cuotas_2);
	}

	/** Get Cuotas_2.
		@return Cuotas_2	  */
	public String getCuotas_2 () 
	{
		return (String)get_Value(COLUMNNAME_Cuotas_2);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set IsTotalizador.
		@param IsTotalizador 
		IsTotalizador
	  */
	public void setIsTotalizador (boolean IsTotalizador)
	{
		set_Value (COLUMNNAME_IsTotalizador, Boolean.valueOf(IsTotalizador));
	}

	/** Get IsTotalizador.
		@return IsTotalizador
	  */
	public boolean isTotalizador () 
	{
		Object oo = get_Value(COLUMNNAME_IsTotalizador);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set OperationCode.
		@param OperationCode 
		OperationCode
	  */
	public void setOperationCode (String OperationCode)
	{
		set_Value (COLUMNNAME_OperationCode, OperationCode);
	}

	/** Get OperationCode.
		@return OperationCode
	  */
	public String getOperationCode () 
	{
		return (String)get_Value(COLUMNNAME_OperationCode);
	}

	/** Set Parent Key.
		@param ParentValue 
		Key if the Parent
	  */
	public void setParentValue (String ParentValue)
	{
		set_Value (COLUMNNAME_ParentValue, ParentValue);
	}

	/** Get Parent Key.
		@return Key if the Parent
	  */
	public String getParentValue () 
	{
		return (String)get_Value(COLUMNNAME_ParentValue);
	}

	/** Set UY_FduCod_ID.
		@param UY_FduCod_ID 
		UY_FduCod_ID
	  */
	public void setUY_FduCod_ID (int UY_FduCod_ID)
	{
		if (UY_FduCod_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FduCod_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FduCod_ID, Integer.valueOf(UY_FduCod_ID));
	}

	/** Get UY_FduCod_ID.
		@return UY_FduCod_ID
	  */
	public int getUY_FduCod_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduCod_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}