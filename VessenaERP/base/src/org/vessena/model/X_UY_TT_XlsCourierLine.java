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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_XlsCourierLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_XlsCourierLine extends PO implements I_UY_TT_XlsCourierLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150813L;

    /** Standard Constructor */
    public X_UY_TT_XlsCourierLine (Properties ctx, int UY_TT_XlsCourierLine_ID, String trxName)
    {
      super (ctx, UY_TT_XlsCourierLine_ID, trxName);
      /** if (UY_TT_XlsCourierLine_ID == 0)
        {
			setAccountNo (null);
			setAddress1 (null);
			setDateStart (new Timestamp( System.currentTimeMillis() ));
			setlocalidad (null);
			setName (null);
			setPieza (null);
			setRetirementNumber (null);
			setUY_TT_CardStatus_ID (0);
			setUY_TT_DeliveryPointStatus_ID (0);
			setUY_TT_XlsCourier_ID (0);
			setUY_TT_XlsCourierLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_XlsCourierLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_XlsCourierLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
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

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
	}

	/** Set DateStart.
		@param DateStart DateStart	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get DateStart.
		@return DateStart	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
	}

	/** Set DeliveryNumber.
		@param DeliveryNumber DeliveryNumber	  */
	public void setDeliveryNumber (String DeliveryNumber)
	{
		set_Value (COLUMNNAME_DeliveryNumber, DeliveryNumber);
	}

	/** Get DeliveryNumber.
		@return DeliveryNumber	  */
	public String getDeliveryNumber () 
	{
		return (String)get_Value(COLUMNNAME_DeliveryNumber);
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

	/** Set Name 2.
		@param Name2 
		Additional Name
	  */
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Pieza AD_Reference_ID=1000321 */
	public static final int PIEZA_AD_Reference_ID=1000321;
	/** Tarjeta de Credito = TARJETA */
	public static final String PIEZA_TarjetaDeCredito = "TARJETA";
	/** Resumen de Cuenta = RESUMEN */
	public static final String PIEZA_ResumenDeCuenta = "RESUMEN";
	/** Set Pieza.
		@param Pieza Pieza	  */
	public void setPieza (String Pieza)
	{

		set_Value (COLUMNNAME_Pieza, Pieza);
	}

	/** Get Pieza.
		@return Pieza	  */
	public String getPieza () 
	{
		return (String)get_Value(COLUMNNAME_Pieza);
	}

	/** Set RetirementNumber.
		@param RetirementNumber RetirementNumber	  */
	public void setRetirementNumber (String RetirementNumber)
	{
		set_Value (COLUMNNAME_RetirementNumber, RetirementNumber);
	}

	/** Get RetirementNumber.
		@return RetirementNumber	  */
	public String getRetirementNumber () 
	{
		return (String)get_Value(COLUMNNAME_RetirementNumber);
	}

	/** Set ReturnDate.
		@param ReturnDate ReturnDate	  */
	public void setReturnDate (Timestamp ReturnDate)
	{
		set_Value (COLUMNNAME_ReturnDate, ReturnDate);
	}

	/** Get ReturnDate.
		@return ReturnDate	  */
	public Timestamp getReturnDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ReturnDate);
	}

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException
    {
		return (I_UY_R_Reclamo)MTable.get(getCtx(), I_UY_R_Reclamo.Table_Name)
			.getPO(getUY_R_Reclamo_ID(), get_TrxName());	}

	/** Set UY_R_Reclamo.
		@param UY_R_Reclamo_ID UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID)
	{
		if (UY_R_Reclamo_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, Integer.valueOf(UY_R_Reclamo_ID));
	}

	/** Get UY_R_Reclamo.
		@return UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_CardStatus getUY_TT_CardStatus() throws RuntimeException
    {
		return (I_UY_TT_CardStatus)MTable.get(getCtx(), I_UY_TT_CardStatus.Table_Name)
			.getPO(getUY_TT_CardStatus_ID(), get_TrxName());	}

	/** Set UY_TT_CardStatus.
		@param UY_TT_CardStatus_ID UY_TT_CardStatus	  */
	public void setUY_TT_CardStatus_ID (int UY_TT_CardStatus_ID)
	{
		if (UY_TT_CardStatus_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_CardStatus_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_CardStatus_ID, Integer.valueOf(UY_TT_CardStatus_ID));
	}

	/** Get UY_TT_CardStatus.
		@return UY_TT_CardStatus	  */
	public int getUY_TT_CardStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_CardStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_DeliveryPointStatus getUY_TT_DeliveryPointStatus() throws RuntimeException
    {
		return (I_UY_TT_DeliveryPointStatus)MTable.get(getCtx(), I_UY_TT_DeliveryPointStatus.Table_Name)
			.getPO(getUY_TT_DeliveryPointStatus_ID(), get_TrxName());	}

	/** Set UY_TT_DeliveryPointStatus.
		@param UY_TT_DeliveryPointStatus_ID UY_TT_DeliveryPointStatus	  */
	public void setUY_TT_DeliveryPointStatus_ID (int UY_TT_DeliveryPointStatus_ID)
	{
		if (UY_TT_DeliveryPointStatus_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_DeliveryPointStatus_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_DeliveryPointStatus_ID, Integer.valueOf(UY_TT_DeliveryPointStatus_ID));
	}

	/** Get UY_TT_DeliveryPointStatus.
		@return UY_TT_DeliveryPointStatus	  */
	public int getUY_TT_DeliveryPointStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_DeliveryPointStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_DelPointRetReasons getUY_TT_DelPointRetReasons() throws RuntimeException
    {
		return (I_UY_TT_DelPointRetReasons)MTable.get(getCtx(), I_UY_TT_DelPointRetReasons.Table_Name)
			.getPO(getUY_TT_DelPointRetReasons_ID(), get_TrxName());	}

	/** Set UY_TT_DelPointRetReasons.
		@param UY_TT_DelPointRetReasons_ID UY_TT_DelPointRetReasons	  */
	public void setUY_TT_DelPointRetReasons_ID (int UY_TT_DelPointRetReasons_ID)
	{
		if (UY_TT_DelPointRetReasons_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_DelPointRetReasons_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_DelPointRetReasons_ID, Integer.valueOf(UY_TT_DelPointRetReasons_ID));
	}

	/** Get UY_TT_DelPointRetReasons.
		@return UY_TT_DelPointRetReasons	  */
	public int getUY_TT_DelPointRetReasons_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_DelPointRetReasons_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_ReturnReasons getUY_TT_ReturnReasons() throws RuntimeException
    {
		return (I_UY_TT_ReturnReasons)MTable.get(getCtx(), I_UY_TT_ReturnReasons.Table_Name)
			.getPO(getUY_TT_ReturnReasons_ID(), get_TrxName());	}

	/** Set UY_TT_ReturnReasons.
		@param UY_TT_ReturnReasons_ID UY_TT_ReturnReasons	  */
	public void setUY_TT_ReturnReasons_ID (int UY_TT_ReturnReasons_ID)
	{
		if (UY_TT_ReturnReasons_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_ReturnReasons_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_ReturnReasons_ID, Integer.valueOf(UY_TT_ReturnReasons_ID));
	}

	/** Get UY_TT_ReturnReasons.
		@return UY_TT_ReturnReasons	  */
	public int getUY_TT_ReturnReasons_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ReturnReasons_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_XlsCourier getUY_TT_XlsCourier() throws RuntimeException
    {
		return (I_UY_TT_XlsCourier)MTable.get(getCtx(), I_UY_TT_XlsCourier.Table_Name)
			.getPO(getUY_TT_XlsCourier_ID(), get_TrxName());	}

	/** Set UY_TT_XlsCourier.
		@param UY_TT_XlsCourier_ID UY_TT_XlsCourier	  */
	public void setUY_TT_XlsCourier_ID (int UY_TT_XlsCourier_ID)
	{
		if (UY_TT_XlsCourier_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_XlsCourier_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_XlsCourier_ID, Integer.valueOf(UY_TT_XlsCourier_ID));
	}

	/** Get UY_TT_XlsCourier.
		@return UY_TT_XlsCourier	  */
	public int getUY_TT_XlsCourier_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_XlsCourier_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_XlsCourierLine.
		@param UY_TT_XlsCourierLine_ID UY_TT_XlsCourierLine	  */
	public void setUY_TT_XlsCourierLine_ID (int UY_TT_XlsCourierLine_ID)
	{
		if (UY_TT_XlsCourierLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_XlsCourierLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_XlsCourierLine_ID, Integer.valueOf(UY_TT_XlsCourierLine_ID));
	}

	/** Get UY_TT_XlsCourierLine.
		@return UY_TT_XlsCourierLine	  */
	public int getUY_TT_XlsCourierLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_XlsCourierLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vinculo.
		@param Vinculo Vinculo	  */
	public void setVinculo (String Vinculo)
	{
		set_Value (COLUMNNAME_Vinculo, Vinculo);
	}

	/** Get Vinculo.
		@return Vinculo	  */
	public String getVinculo () 
	{
		return (String)get_Value(COLUMNNAME_Vinculo);
	}
}