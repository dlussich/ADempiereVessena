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

/** Generated Model for UY_HRResultDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRResultDetail extends PO implements I_UY_HRResultDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120425L;

    /** Standard Constructor */
    public X_UY_HRResultDetail (Properties ctx, int UY_HRResultDetail_ID, String trxName)
    {
      super (ctx, UY_HRResultDetail_ID, trxName);
      /** if (UY_HRResultDetail_ID == 0)
        {
			setHR_Concept_ID (0);
			setSeqNo (0);
			setSuccess (false);
			setTotalAmt (Env.ZERO);
			setUY_HRResult_ID (0);
			setUY_HRResultDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRResultDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRResultDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AccountSign AD_Reference_ID=118 */
	public static final int ACCOUNTSIGN_AD_Reference_ID=118;
	/** Natural = N */
	public static final String ACCOUNTSIGN_Natural = "N";
	/** Debit = D */
	public static final String ACCOUNTSIGN_Debit = "D";
	/** Credit = C */
	public static final String ACCOUNTSIGN_Credit = "C";
	/** Set Account Sign.
		@param AccountSign 
		Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	public void setAccountSign (String AccountSign)
	{

		set_Value (COLUMNNAME_AccountSign, AccountSign);
	}

	/** Get Account Sign.
		@return Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	public String getAccountSign () 
	{
		return (String)get_Value(COLUMNNAME_AccountSign);
	}

	/** Set codigo.
		@param codigo codigo	  */
	public void setcodigo (String codigo)
	{
		set_Value (COLUMNNAME_codigo, codigo);
	}

	/** Get codigo.
		@return codigo	  */
	public String getcodigo () 
	{
		return (String)get_Value(COLUMNNAME_codigo);
	}

	/** ColumnType AD_Reference_ID=53243 */
	public static final int COLUMNTYPE_AD_Reference_ID=53243;
	/** Amount = A */
	public static final String COLUMNTYPE_Amount = "A";
	/** Quantity = Q */
	public static final String COLUMNTYPE_Quantity = "Q";
	/** SI/NO = K */
	public static final String COLUMNTYPE_SINO = "K";
	/** Porcentaje = P */
	public static final String COLUMNTYPE_Porcentaje = "P";
	/** Set Column Type.
		@param ColumnType Column Type	  */
	public void setColumnType (String ColumnType)
	{

		set_Value (COLUMNNAME_ColumnType, ColumnType);
	}

	/** Get Column Type.
		@return Column Type	  */
	public String getColumnType () 
	{
		return (String)get_Value(COLUMNNAME_ColumnType);
	}

	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException
    {
		return (org.eevolution.model.I_HR_Concept)MTable.get(getCtx(), org.eevolution.model.I_HR_Concept.Table_Name)
			.getPO(getHR_Concept_ID(), get_TrxName());	}

	/** Set Payroll Concept.
		@param HR_Concept_ID Payroll Concept	  */
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get Payroll Concept.
		@return Payroll Concept	  */
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Message.
		@param Message 
		EMail Message
	  */
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return EMail Message
	  */
	public String getMessage () 
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Success.
		@param Success Success	  */
	public void setSuccess (boolean Success)
	{
		set_Value (COLUMNNAME_Success, Boolean.valueOf(Success));
	}

	/** Get Success.
		@return Success	  */
	public boolean isSuccess () 
	{
		Object oo = get_Value(COLUMNNAME_Success);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Type AD_Reference_ID=53245 */
	public static final int TYPE_AD_Reference_ID=53245;
	/** Concept = C */
	public static final String TYPE_Concept = "C";
	/** Rule Engine = E */
	public static final String TYPE_RuleEngine = "E";
	/** Information = I */
	public static final String TYPE_Information = "I";
	/** Reference = R */
	public static final String TYPE_Reference = "R";
	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}

	public I_UY_HRCalculos getUY_HRCalculos() throws RuntimeException
    {
		return (I_UY_HRCalculos)MTable.get(getCtx(), I_UY_HRCalculos.Table_Name)
			.getPO(getUY_HRCalculos_ID(), get_TrxName());	}

	/** Set UY_HRCalculos.
		@param UY_HRCalculos_ID UY_HRCalculos	  */
	public void setUY_HRCalculos_ID (int UY_HRCalculos_ID)
	{
		if (UY_HRCalculos_ID < 1) 
			set_Value (COLUMNNAME_UY_HRCalculos_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRCalculos_ID, Integer.valueOf(UY_HRCalculos_ID));
	}

	/** Get UY_HRCalculos.
		@return UY_HRCalculos	  */
	public int getUY_HRCalculos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRCalculos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_HRResult getUY_HRResult() throws RuntimeException
    {
		return (I_UY_HRResult)MTable.get(getCtx(), I_UY_HRResult.Table_Name)
			.getPO(getUY_HRResult_ID(), get_TrxName());	}

	/** Set UY_HRResult.
		@param UY_HRResult_ID UY_HRResult	  */
	public void setUY_HRResult_ID (int UY_HRResult_ID)
	{
		if (UY_HRResult_ID < 1) 
			set_Value (COLUMNNAME_UY_HRResult_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRResult_ID, Integer.valueOf(UY_HRResult_ID));
	}

	/** Get UY_HRResult.
		@return UY_HRResult	  */
	public int getUY_HRResult_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRResult_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRResultDetail.
		@param UY_HRResultDetail_ID UY_HRResultDetail	  */
	public void setUY_HRResultDetail_ID (int UY_HRResultDetail_ID)
	{
		if (UY_HRResultDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRResultDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRResultDetail_ID, Integer.valueOf(UY_HRResultDetail_ID));
	}

	/** Get UY_HRResultDetail.
		@return UY_HRResultDetail	  */
	public int getUY_HRResultDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRResultDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Working Time.
		@param WorkingTime 
		Workflow Simulation Execution Time
	  */
	public void setWorkingTime (String WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, WorkingTime);
	}

	/** Get Working Time.
		@return Workflow Simulation Execution Time
	  */
	public String getWorkingTime () 
	{
		return (String)get_Value(COLUMNNAME_WorkingTime);
	}
}