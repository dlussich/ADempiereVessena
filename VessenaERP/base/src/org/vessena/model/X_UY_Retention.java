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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_Retention
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Retention extends PO implements I_UY_Retention, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151223L;

    /** Standard Constructor */
    public X_UY_Retention (Properties ctx, int UY_Retention_ID, String trxName)
    {
      super (ctx, UY_Retention_ID, trxName);
      /** if (UY_Retention_ID == 0)
        {
			setConceptPorc (Env.ZERO);
// 100
			setConceptValue (null);
			setName (null);
			setUY_Retention_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_Retention (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Retention[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ConceptPorc.
		@param ConceptPorc ConceptPorc	  */
	public void setConceptPorc (BigDecimal ConceptPorc)
	{
		set_Value (COLUMNNAME_ConceptPorc, ConceptPorc);
	}

	/** Get ConceptPorc.
		@return ConceptPorc	  */
	public BigDecimal getConceptPorc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConceptPorc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** ConceptValue AD_Reference_ID=1000210 */
	public static final int CONCEPTVALUE_AD_Reference_ID=1000210;
	/** Total = TOT */
	public static final String CONCEPTVALUE_Total = "TOT";
	/** Subtotal = SUB */
	public static final String CONCEPTVALUE_Subtotal = "SUB";
	/** Iva = IVA */
	public static final String CONCEPTVALUE_Iva = "IVA";
	/** Set Concept Value.
		@param ConceptValue 
		Value of the Concept
	  */
	public void setConceptValue (String ConceptValue)
	{

		set_Value (COLUMNNAME_ConceptValue, ConceptValue);
	}

	/** Get Concept Value.
		@return Value of the Concept
	  */
	public String getConceptValue () 
	{
		return (String)get_Value(COLUMNNAME_ConceptValue);
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

	/** Set IsUnidadIndexada.
		@param IsUnidadIndexada IsUnidadIndexada	  */
	public void setIsUnidadIndexada (boolean IsUnidadIndexada)
	{
		set_Value (COLUMNNAME_IsUnidadIndexada, Boolean.valueOf(IsUnidadIndexada));
	}

	/** Get IsUnidadIndexada.
		@return IsUnidadIndexada	  */
	public boolean isUnidadIndexada () 
	{
		Object oo = get_Value(COLUMNNAME_IsUnidadIndexada);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Max Amount.
		@param MaxAmt 
		Maximum Amount in invoice currency
	  */
	public void setMaxAmt (BigDecimal MaxAmt)
	{
		set_Value (COLUMNNAME_MaxAmt, MaxAmt);
	}

	/** Get Max Amount.
		@return Maximum Amount in invoice currency
	  */
	public BigDecimal getMaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Rate.
		@param Rate 
		Rate or Tax or Exchange
	  */
	public void setRate (BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	/** Get Rate.
		@return Rate or Tax or Exchange
	  */
	public BigDecimal getRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_ValidCombination getR_Retention_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getR_Retention_Acct(), get_TrxName());	}

	/** Set R_Retention_Acct.
		@param R_Retention_Acct R_Retention_Acct	  */
	public void setR_Retention_Acct (int R_Retention_Acct)
	{
		set_Value (COLUMNNAME_R_Retention_Acct, Integer.valueOf(R_Retention_Acct));
	}

	/** Get R_Retention_Acct.
		@return R_Retention_Acct	  */
	public int getR_Retention_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Retention_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getR_Transit_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getR_Transit_Acct(), get_TrxName());	}

	/** Set R_Transit_Acct.
		@param R_Transit_Acct R_Transit_Acct	  */
	public void setR_Transit_Acct (int R_Transit_Acct)
	{
		set_Value (COLUMNNAME_R_Transit_Acct, Integer.valueOf(R_Transit_Acct));
	}

	/** Get R_Transit_Acct.
		@return R_Transit_Acct	  */
	public int getR_Transit_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Transit_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Familia getUY_Familia() throws RuntimeException
    {
		return (I_UY_Familia)MTable.get(getCtx(), I_UY_Familia.Table_Name)
			.getPO(getUY_Familia_ID(), get_TrxName());	}

	/** Set UY_Familia.
		@param UY_Familia_ID UY_Familia	  */
	public void setUY_Familia_ID (int UY_Familia_ID)
	{
		if (UY_Familia_ID < 1) 
			set_Value (COLUMNNAME_UY_Familia_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Familia_ID, Integer.valueOf(UY_Familia_ID));
	}

	/** Get UY_Familia.
		@return UY_Familia	  */
	public int getUY_Familia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Familia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Linea_Negocio getUY_Linea_Negocio() throws RuntimeException
    {
		return (I_UY_Linea_Negocio)MTable.get(getCtx(), I_UY_Linea_Negocio.Table_Name)
			.getPO(getUY_Linea_Negocio_ID(), get_TrxName());	}

	/** Set UY_Linea_Negocio_ID.
		@param UY_Linea_Negocio_ID UY_Linea_Negocio_ID	  */
	public void setUY_Linea_Negocio_ID (int UY_Linea_Negocio_ID)
	{
		if (UY_Linea_Negocio_ID < 1) 
			set_Value (COLUMNNAME_UY_Linea_Negocio_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Linea_Negocio_ID, Integer.valueOf(UY_Linea_Negocio_ID));
	}

	/** Get UY_Linea_Negocio_ID.
		@return UY_Linea_Negocio_ID	  */
	public int getUY_Linea_Negocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Linea_Negocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ProductGroup getUY_ProductGroup() throws RuntimeException
    {
		return (I_UY_ProductGroup)MTable.get(getCtx(), I_UY_ProductGroup.Table_Name)
			.getPO(getUY_ProductGroup_ID(), get_TrxName());	}

	/** Set UY_ProductGroup.
		@param UY_ProductGroup_ID UY_ProductGroup	  */
	public void setUY_ProductGroup_ID (int UY_ProductGroup_ID)
	{
		if (UY_ProductGroup_ID < 1) 
			set_Value (COLUMNNAME_UY_ProductGroup_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProductGroup_ID, Integer.valueOf(UY_ProductGroup_ID));
	}

	/** Get UY_ProductGroup.
		@return UY_ProductGroup	  */
	public int getUY_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProductGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Retention.
		@param UY_Retention_ID UY_Retention	  */
	public void setUY_Retention_ID (int UY_Retention_ID)
	{
		if (UY_Retention_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Retention_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Retention_ID, Integer.valueOf(UY_Retention_ID));
	}

	/** Get UY_Retention.
		@return UY_Retention	  */
	public int getUY_Retention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Retention_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_SubFamilia getUY_SubFamilia() throws RuntimeException
    {
		return (I_UY_SubFamilia)MTable.get(getCtx(), I_UY_SubFamilia.Table_Name)
			.getPO(getUY_SubFamilia_ID(), get_TrxName());	}

	/** Set UY_SubFamilia.
		@param UY_SubFamilia_ID UY_SubFamilia	  */
	public void setUY_SubFamilia_ID (int UY_SubFamilia_ID)
	{
		if (UY_SubFamilia_ID < 1) 
			set_Value (COLUMNNAME_UY_SubFamilia_ID, null);
		else 
			set_Value (COLUMNNAME_UY_SubFamilia_ID, Integer.valueOf(UY_SubFamilia_ID));
	}

	/** Get UY_SubFamilia.
		@return UY_SubFamilia	  */
	public int getUY_SubFamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_SubFamilia_ID);
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