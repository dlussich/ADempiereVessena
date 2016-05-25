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

/** Generated Model for UY_R_AjusteActionLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_AjusteActionLine extends PO implements I_UY_R_AjusteActionLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130816L;

    /** Standard Constructor */
    public X_UY_R_AjusteActionLine (Properties ctx, int UY_R_AjusteActionLine_ID, String trxName)
    {
      super (ctx, UY_R_AjusteActionLine_ID, trxName);
      /** if (UY_R_AjusteActionLine_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_R_AjusteAction_ID (0);
			setUY_R_AjusteActionLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_AjusteActionLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_R_AjusteActionLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_ValueNoCheck (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_ValueNoCheck (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
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

	/** Set DateReclamo.
		@param DateReclamo DateReclamo	  */
	public void setDateReclamo (Timestamp DateReclamo)
	{
		set_Value (COLUMNNAME_DateReclamo, DateReclamo);
	}

	/** Get DateReclamo.
		@return DateReclamo	  */
	public Timestamp getDateReclamo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateReclamo);
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

	/** Set IsRejected.
		@param IsRejected IsRejected	  */
	public void setIsRejected (boolean IsRejected)
	{
		set_Value (COLUMNNAME_IsRejected, Boolean.valueOf(IsRejected));
	}

	/** Get IsRejected.
		@return IsRejected	  */
	public boolean isRejected () 
	{
		Object oo = get_Value(COLUMNNAME_IsRejected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	/** Set QtyQuote.
		@param QtyQuote QtyQuote	  */
	public void setQtyQuote (BigDecimal QtyQuote)
	{
		set_Value (COLUMNNAME_QtyQuote, QtyQuote);
	}

	/** Get QtyQuote.
		@return QtyQuote	  */
	public BigDecimal getQtyQuote () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyQuote);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Receptor_ID.
		@param Receptor_ID Receptor_ID	  */
	public void setReceptor_ID (int Receptor_ID)
	{
		if (Receptor_ID < 1) 
			set_Value (COLUMNNAME_Receptor_ID, null);
		else 
			set_Value (COLUMNNAME_Receptor_ID, Integer.valueOf(Receptor_ID));
	}

	/** Get Receptor_ID.
		@return Receptor_ID	  */
	public int getReceptor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Receptor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_AjusteAction getUY_R_AjusteAction() throws RuntimeException
    {
		return (I_UY_R_AjusteAction)MTable.get(getCtx(), I_UY_R_AjusteAction.Table_Name)
			.getPO(getUY_R_AjusteAction_ID(), get_TrxName());	}

	/** Set UY_R_AjusteAction.
		@param UY_R_AjusteAction_ID UY_R_AjusteAction	  */
	public void setUY_R_AjusteAction_ID (int UY_R_AjusteAction_ID)
	{
		if (UY_R_AjusteAction_ID < 1) 
			set_Value (COLUMNNAME_UY_R_AjusteAction_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_AjusteAction_ID, Integer.valueOf(UY_R_AjusteAction_ID));
	}

	/** Get UY_R_AjusteAction.
		@return UY_R_AjusteAction	  */
	public int getUY_R_AjusteAction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_AjusteAction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_AjusteActionLine.
		@param UY_R_AjusteActionLine_ID UY_R_AjusteActionLine	  */
	public void setUY_R_AjusteActionLine_ID (int UY_R_AjusteActionLine_ID)
	{
		if (UY_R_AjusteActionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_AjusteActionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_AjusteActionLine_ID, Integer.valueOf(UY_R_AjusteActionLine_ID));
	}

	/** Get UY_R_AjusteActionLine.
		@return UY_R_AjusteActionLine	  */
	public int getUY_R_AjusteActionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_AjusteActionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Ajuste getUY_R_Ajuste() throws RuntimeException
    {
		return (I_UY_R_Ajuste)MTable.get(getCtx(), I_UY_R_Ajuste.Table_Name)
			.getPO(getUY_R_Ajuste_ID(), get_TrxName());	}

	/** Set UY_R_Ajuste.
		@param UY_R_Ajuste_ID UY_R_Ajuste	  */
	public void setUY_R_Ajuste_ID (int UY_R_Ajuste_ID)
	{
		if (UY_R_Ajuste_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Ajuste_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Ajuste_ID, Integer.valueOf(UY_R_Ajuste_ID));
	}

	/** Get UY_R_Ajuste.
		@return UY_R_Ajuste	  */
	public int getUY_R_Ajuste_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Ajuste_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_AjusteRequest getUY_R_AjusteRequest() throws RuntimeException
    {
		return (I_UY_R_AjusteRequest)MTable.get(getCtx(), I_UY_R_AjusteRequest.Table_Name)
			.getPO(getUY_R_AjusteRequest_ID(), get_TrxName());	}

	/** Set UY_R_AjusteRequest.
		@param UY_R_AjusteRequest_ID UY_R_AjusteRequest	  */
	public void setUY_R_AjusteRequest_ID (int UY_R_AjusteRequest_ID)
	{
		if (UY_R_AjusteRequest_ID < 1) 
			set_Value (COLUMNNAME_UY_R_AjusteRequest_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_AjusteRequest_ID, Integer.valueOf(UY_R_AjusteRequest_ID));
	}

	/** Get UY_R_AjusteRequest.
		@return UY_R_AjusteRequest	  */
	public int getUY_R_AjusteRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_AjusteRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_AjusteRequestLine getUY_R_AjusteRequestLine() throws RuntimeException
    {
		return (I_UY_R_AjusteRequestLine)MTable.get(getCtx(), I_UY_R_AjusteRequestLine.Table_Name)
			.getPO(getUY_R_AjusteRequestLine_ID(), get_TrxName());	}

	/** Set UY_R_AjusteRequestLine.
		@param UY_R_AjusteRequestLine_ID UY_R_AjusteRequestLine	  */
	public void setUY_R_AjusteRequestLine_ID (int UY_R_AjusteRequestLine_ID)
	{
		if (UY_R_AjusteRequestLine_ID < 1) 
			set_Value (COLUMNNAME_UY_R_AjusteRequestLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_AjusteRequestLine_ID, Integer.valueOf(UY_R_AjusteRequestLine_ID));
	}

	/** Get UY_R_AjusteRequestLine.
		@return UY_R_AjusteRequestLine	  */
	public int getUY_R_AjusteRequestLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_AjusteRequestLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Canal getUY_R_Canal() throws RuntimeException
    {
		return (I_UY_R_Canal)MTable.get(getCtx(), I_UY_R_Canal.Table_Name)
			.getPO(getUY_R_Canal_ID(), get_TrxName());	}

	/** Set UY_R_Canal.
		@param UY_R_Canal_ID UY_R_Canal	  */
	public void setUY_R_Canal_ID (int UY_R_Canal_ID)
	{
		if (UY_R_Canal_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Canal_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Canal_ID, Integer.valueOf(UY_R_Canal_ID));
	}

	/** Get UY_R_Canal.
		@return UY_R_Canal	  */
	public int getUY_R_Canal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Canal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_HandlerAjuste getUY_R_HandlerAjuste() throws RuntimeException
    {
		return (I_UY_R_HandlerAjuste)MTable.get(getCtx(), I_UY_R_HandlerAjuste.Table_Name)
			.getPO(getUY_R_HandlerAjuste_ID(), get_TrxName());	}

	/** Set UY_R_HandlerAjuste.
		@param UY_R_HandlerAjuste_ID UY_R_HandlerAjuste	  */
	public void setUY_R_HandlerAjuste_ID (int UY_R_HandlerAjuste_ID)
	{
		if (UY_R_HandlerAjuste_ID < 1) 
			set_Value (COLUMNNAME_UY_R_HandlerAjuste_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_HandlerAjuste_ID, Integer.valueOf(UY_R_HandlerAjuste_ID));
	}

	/** Get UY_R_HandlerAjuste.
		@return UY_R_HandlerAjuste	  */
	public int getUY_R_HandlerAjuste_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_HandlerAjuste_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}