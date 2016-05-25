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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_RetentionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_RetentionLine extends PO implements I_UY_TT_RetentionLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131003L;

    /** Standard Constructor */
    public X_UY_TT_RetentionLine (Properties ctx, int UY_TT_RetentionLine_ID, String trxName)
    {
      super (ctx, UY_TT_RetentionLine_ID, trxName);
      /** if (UY_TT_RetentionLine_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setIsValid (false);
			setUY_TT_Retention_ID (0);
			setUY_TT_RetentionLine_ID (0);
			setUY_TT_RetentionScan_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_RetentionLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_RetentionLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address 1.
		@param Address1 
		Address line 1 for this location
	  */
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Address 1.
		@return Address line 1 for this location
	  */
	public String getAddress1 () 
	{
		return (String)get_Value(COLUMNNAME_Address1);
	}

	/** Set CardAction.
		@param CardAction CardAction	  */
	public void setCardAction (String CardAction)
	{
		set_Value (COLUMNNAME_CardAction, CardAction);
	}

	/** Get CardAction.
		@return CardAction	  */
	public String getCardAction () 
	{
		return (String)get_Value(COLUMNNAME_CardAction);
	}

	/** Set CardDestination.
		@param CardDestination CardDestination	  */
	public void setCardDestination (String CardDestination)
	{
		set_Value (COLUMNNAME_CardDestination, CardDestination);
	}

	/** Get CardDestination.
		@return CardDestination	  */
	public String getCardDestination () 
	{
		return (String)get_Value(COLUMNNAME_CardDestination);
	}

	/** Set Credit limit.
		@param CreditLimit 
		Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit)
	{
		set_Value (COLUMNNAME_CreditLimit, CreditLimit);
	}

	/** Get Credit limit.
		@return Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (String DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public String getDueDate () 
	{
		return (String)get_Value(COLUMNNAME_DueDate);
	}

	/** Set GAFCOD.
		@param GAFCOD GAFCOD	  */
	public void setGAFCOD (int GAFCOD)
	{
		set_Value (COLUMNNAME_GAFCOD, Integer.valueOf(GAFCOD));
	}

	/** Get GAFCOD.
		@return GAFCOD	  */
	public int getGAFCOD () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GAFCOD);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GAFNOM.
		@param GAFNOM GAFNOM	  */
	public void setGAFNOM (String GAFNOM)
	{
		set_Value (COLUMNNAME_GAFNOM, GAFNOM);
	}

	/** Get GAFNOM.
		@return GAFNOM	  */
	public String getGAFNOM () 
	{
		return (String)get_Value(COLUMNNAME_GAFNOM);
	}

	/** Set GrpCtaCte.
		@param GrpCtaCte GrpCtaCte	  */
	public void setGrpCtaCte (int GrpCtaCte)
	{
		set_Value (COLUMNNAME_GrpCtaCte, Integer.valueOf(GrpCtaCte));
	}

	/** Get GrpCtaCte.
		@return GrpCtaCte	  */
	public int getGrpCtaCte () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GrpCtaCte);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set InvalidText.
		@param InvalidText InvalidText	  */
	public void setInvalidText (String InvalidText)
	{
		set_Value (COLUMNNAME_InvalidText, InvalidText);
	}

	/** Get InvalidText.
		@return InvalidText	  */
	public String getInvalidText () 
	{
		return (String)get_Value(COLUMNNAME_InvalidText);
	}

	/** Set IsDestroy.
		@param IsDestroy 
		IsDestroy
	  */
	public void setIsDestroy (boolean IsDestroy)
	{
		set_Value (COLUMNNAME_IsDestroy, Boolean.valueOf(IsDestroy));
	}

	/** Get IsDestroy.
		@return IsDestroy
	  */
	public boolean isDestroy () 
	{
		Object oo = get_Value(COLUMNNAME_IsDestroy);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set localidad.
		@param localidad localidad	  */
	public void setlocalidad (String localidad)
	{
		set_Value (COLUMNNAME_localidad, localidad);
	}

	/** Get localidad.
		@return localidad	  */
	public String getlocalidad () 
	{
		return (String)get_Value(COLUMNNAME_localidad);
	}

	/** Set MLCod.
		@param MLCod MLCod	  */
	public void setMLCod (String MLCod)
	{
		set_Value (COLUMNNAME_MLCod, MLCod);
	}

	/** Get MLCod.
		@return MLCod	  */
	public String getMLCod () 
	{
		return (String)get_Value(COLUMNNAME_MLCod);
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

	/** Set NotDeliverableAction.
		@param NotDeliverableAction NotDeliverableAction	  */
	public void setNotDeliverableAction (String NotDeliverableAction)
	{
		set_Value (COLUMNNAME_NotDeliverableAction, NotDeliverableAction);
	}

	/** Get NotDeliverableAction.
		@return NotDeliverableAction	  */
	public String getNotDeliverableAction () 
	{
		return (String)get_Value(COLUMNNAME_NotDeliverableAction);
	}

	/** Set ZIP.
		@param Postal 
		Postal code
	  */
	public void setPostal (String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get ZIP.
		@return Postal code
	  */
	public String getPostal () 
	{
		return (String)get_Value(COLUMNNAME_Postal);
	}

	/** Set ProductoAux.
		@param ProductoAux ProductoAux	  */
	public void setProductoAux (String ProductoAux)
	{
		set_Value (COLUMNNAME_ProductoAux, ProductoAux);
	}

	/** Get ProductoAux.
		@return ProductoAux	  */
	public String getProductoAux () 
	{
		return (String)get_Value(COLUMNNAME_ProductoAux);
	}

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException
    {
		return (I_UY_TT_Box)MTable.get(getCtx(), I_UY_TT_Box.Table_Name)
			.getPO(getUY_TT_Box_ID(), get_TrxName());	}

	/** Set UY_TT_Box.
		@param UY_TT_Box_ID UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID)
	{
		if (UY_TT_Box_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Box_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Box_ID, Integer.valueOf(UY_TT_Box_ID));
	}

	/** Get UY_TT_Box.
		@return UY_TT_Box	  */
	public int getUY_TT_Box_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_Box_ID_1.
		@param UY_TT_Box_ID_1 
		UY_TT_Box_ID_1
	  */
	public void setUY_TT_Box_ID_1 (int UY_TT_Box_ID_1)
	{
		set_Value (COLUMNNAME_UY_TT_Box_ID_1, Integer.valueOf(UY_TT_Box_ID_1));
	}

	/** Get UY_TT_Box_ID_1.
		@return UY_TT_Box_ID_1
	  */
	public int getUY_TT_Box_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException
    {
		return (I_UY_TT_Card)MTable.get(getCtx(), I_UY_TT_Card.Table_Name)
			.getPO(getUY_TT_Card_ID(), get_TrxName());	}

	/** Set UY_TT_Card.
		@param UY_TT_Card_ID UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID)
	{
		if (UY_TT_Card_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Card_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Card_ID, Integer.valueOf(UY_TT_Card_ID));
	}

	/** Get UY_TT_Card.
		@return UY_TT_Card	  */
	public int getUY_TT_Card_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Card_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Retention getUY_TT_Retention() throws RuntimeException
    {
		return (I_UY_TT_Retention)MTable.get(getCtx(), I_UY_TT_Retention.Table_Name)
			.getPO(getUY_TT_Retention_ID(), get_TrxName());	}

	/** Set UY_TT_Retention.
		@param UY_TT_Retention_ID UY_TT_Retention	  */
	public void setUY_TT_Retention_ID (int UY_TT_Retention_ID)
	{
		if (UY_TT_Retention_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Retention_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Retention_ID, Integer.valueOf(UY_TT_Retention_ID));
	}

	/** Get UY_TT_Retention.
		@return UY_TT_Retention	  */
	public int getUY_TT_Retention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Retention_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_RetentionLine.
		@param UY_TT_RetentionLine_ID UY_TT_RetentionLine	  */
	public void setUY_TT_RetentionLine_ID (int UY_TT_RetentionLine_ID)
	{
		if (UY_TT_RetentionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_RetentionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_RetentionLine_ID, Integer.valueOf(UY_TT_RetentionLine_ID));
	}

	/** Get UY_TT_RetentionLine.
		@return UY_TT_RetentionLine	  */
	public int getUY_TT_RetentionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_RetentionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_RetentionScan getUY_TT_RetentionScan() throws RuntimeException
    {
		return (I_UY_TT_RetentionScan)MTable.get(getCtx(), I_UY_TT_RetentionScan.Table_Name)
			.getPO(getUY_TT_RetentionScan_ID(), get_TrxName());	}

	/** Set UY_TT_RetentionScan.
		@param UY_TT_RetentionScan_ID UY_TT_RetentionScan	  */
	public void setUY_TT_RetentionScan_ID (int UY_TT_RetentionScan_ID)
	{
		if (UY_TT_RetentionScan_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_RetentionScan_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_RetentionScan_ID, Integer.valueOf(UY_TT_RetentionScan_ID));
	}

	/** Get UY_TT_RetentionScan.
		@return UY_TT_RetentionScan	  */
	public int getUY_TT_RetentionScan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_RetentionScan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}