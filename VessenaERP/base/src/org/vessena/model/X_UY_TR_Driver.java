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

/** Generated Model for UY_TR_Driver
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Driver extends PO implements I_UY_TR_Driver, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150115L;

    /** Standard Constructor */
    public X_UY_TR_Driver (Properties ctx, int UY_TR_Driver_ID, String trxName)
    {
      super (ctx, UY_TR_Driver_ID, trxName);
      /** if (UY_TR_Driver_ID == 0)
        {
			setFirstName (null);
			setFirstSurname (null);
			setIsEmployee (false);
			setUY_TR_Driver_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Driver (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Driver[")
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set DateAction.
		@param DateAction DateAction	  */
	public void setDateAction (Timestamp DateAction)
	{
		set_Value (COLUMNNAME_DateAction, DateAction);
	}

	/** Get DateAction.
		@return DateAction	  */
	public Timestamp getDateAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAction);
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
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

	/** Set DocNo2.
		@param DocNo2 DocNo2	  */
	public void setDocNo2 (String DocNo2)
	{
		set_Value (COLUMNNAME_DocNo2, DocNo2);
	}

	/** Get DocNo2.
		@return DocNo2	  */
	public String getDocNo2 () 
	{
		return (String)get_Value(COLUMNNAME_DocNo2);
	}

	/** DocType2 AD_Reference_ID=1000432 */
	public static final int DOCTYPE2_AD_Reference_ID=1000432;
	/** CI = CI */
	public static final String DOCTYPE2_CI = "CI";
	/** DNI = DNI */
	public static final String DOCTYPE2_DNI = "DNI";
	/** RG = RG */
	public static final String DOCTYPE2_RG = "RG";
	/** CPF = CPF */
	public static final String DOCTYPE2_CPF = "CPF";
	/** Set DocType2.
		@param DocType2 DocType2	  */
	public void setDocType2 (String DocType2)
	{

		set_Value (COLUMNNAME_DocType2, DocType2);
	}

	/** Get DocType2.
		@return DocType2	  */
	public String getDocType2 () 
	{
		return (String)get_Value(COLUMNNAME_DocType2);
	}

	/** Set DriverCharge.
		@param DriverCharge DriverCharge	  */
	public void setDriverCharge (String DriverCharge)
	{
		set_Value (COLUMNNAME_DriverCharge, DriverCharge);
	}

	/** Get DriverCharge.
		@return DriverCharge	  */
	public String getDriverCharge () 
	{
		return (String)get_Value(COLUMNNAME_DriverCharge);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set FirstName.
		@param FirstName FirstName	  */
	public void setFirstName (String FirstName)
	{
		set_Value (COLUMNNAME_FirstName, FirstName);
	}

	/** Get FirstName.
		@return FirstName	  */
	public String getFirstName () 
	{
		return (String)get_Value(COLUMNNAME_FirstName);
	}

	/** Set FirstSurname.
		@param FirstSurname FirstSurname	  */
	public void setFirstSurname (String FirstSurname)
	{
		set_Value (COLUMNNAME_FirstSurname, FirstSurname);
	}

	/** Get FirstSurname.
		@return FirstSurname	  */
	public String getFirstSurname () 
	{
		return (String)get_Value(COLUMNNAME_FirstSurname);
	}

	/** Set Employee.
		@param IsEmployee 
		Indicates if  this Business Partner is an employee
	  */
	public void setIsEmployee (boolean IsEmployee)
	{
		set_Value (COLUMNNAME_IsEmployee, Boolean.valueOf(IsEmployee));
	}

	/** Get Employee.
		@return Indicates if  this Business Partner is an employee
	  */
	public boolean isEmployee () 
	{
		Object oo = get_Value(COLUMNNAME_IsEmployee);
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

	/** Set National Code.
		@param NationalCode National Code	  */
	public void setNationalCode (String NationalCode)
	{
		set_Value (COLUMNNAME_NationalCode, NationalCode);
	}

	/** Get National Code.
		@return National Code	  */
	public String getNationalCode () 
	{
		return (String)get_Value(COLUMNNAME_NationalCode);
	}

	/** Set nationality.
		@param nationality nationality	  */
	public void setnationality (String nationality)
	{
		set_Value (COLUMNNAME_nationality, nationality);
	}

	/** Get nationality.
		@return nationality	  */
	public String getnationality () 
	{
		return (String)get_Value(COLUMNNAME_nationality);
	}

	/** Set NroCarne.
		@param NroCarne NroCarne	  */
	public void setNroCarne (String NroCarne)
	{
		set_Value (COLUMNNAME_NroCarne, NroCarne);
	}

	/** Get NroCarne.
		@return NroCarne	  */
	public String getNroCarne () 
	{
		return (String)get_Value(COLUMNNAME_NroCarne);
	}

	/** Set Numero.
		@param Numero Numero	  */
	public void setNumero (String Numero)
	{
		set_Value (COLUMNNAME_Numero, Numero);
	}

	/** Get Numero.
		@return Numero	  */
	public String getNumero () 
	{
		return (String)get_Value(COLUMNNAME_Numero);
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

	/** Set 2nd Phone.
		@param Phone2 
		Identifies an alternate telephone number.
	  */
	public void setPhone2 (String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get 2nd Phone.
		@return Identifies an alternate telephone number.
	  */
	public String getPhone2 () 
	{
		return (String)get_Value(COLUMNNAME_Phone2);
	}

	/** Set SecondName.
		@param SecondName SecondName	  */
	public void setSecondName (String SecondName)
	{
		set_Value (COLUMNNAME_SecondName, SecondName);
	}

	/** Get SecondName.
		@return SecondName	  */
	public String getSecondName () 
	{
		return (String)get_Value(COLUMNNAME_SecondName);
	}

	/** Set SecondSurname.
		@param SecondSurname SecondSurname	  */
	public void setSecondSurname (String SecondSurname)
	{
		set_Value (COLUMNNAME_SecondSurname, SecondSurname);
	}

	/** Get SecondSurname.
		@return SecondSurname	  */
	public String getSecondSurname () 
	{
		return (String)get_Value(COLUMNNAME_SecondSurname);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set tipo.
		@param tipo tipo	  */
	public void settipo (String tipo)
	{
		set_Value (COLUMNNAME_tipo, tipo);
	}

	/** Get tipo.
		@return tipo	  */
	public String gettipo () 
	{
		return (String)get_Value(COLUMNNAME_tipo);
	}

	/** tipodoc AD_Reference_ID=1000432 */
	public static final int TIPODOC_AD_Reference_ID=1000432;
	/** CI = CI */
	public static final String TIPODOC_CI = "CI";
	/** DNI = DNI */
	public static final String TIPODOC_DNI = "DNI";
	/** RG = RG */
	public static final String TIPODOC_RG = "RG";
	/** CPF = CPF */
	public static final String TIPODOC_CPF = "CPF";
	/** Set tipodoc.
		@param tipodoc tipodoc	  */
	public void settipodoc (String tipodoc)
	{

		set_Value (COLUMNNAME_tipodoc, tipodoc);
	}

	/** Get tipodoc.
		@return tipodoc	  */
	public String gettipodoc () 
	{
		return (String)get_Value(COLUMNNAME_tipodoc);
	}

	/** Set UY_TR_Driver.
		@param UY_TR_Driver_ID UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID)
	{
		if (UY_TR_Driver_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Driver_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Driver_ID, Integer.valueOf(UY_TR_Driver_ID));
	}

	/** Get UY_TR_Driver.
		@return UY_TR_Driver	  */
	public int getUY_TR_Driver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Driver_ID);
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