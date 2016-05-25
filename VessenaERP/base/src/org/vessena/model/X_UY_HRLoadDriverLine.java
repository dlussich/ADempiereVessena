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

/** Generated Model for UY_HRLoadDriverLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRLoadDriverLine extends PO implements I_UY_HRLoadDriverLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140114L;

    /** Standard Constructor */
    public X_UY_HRLoadDriverLine (Properties ctx, int UY_HRLoadDriverLine_ID, String trxName)
    {
      super (ctx, UY_HRLoadDriverLine_ID, trxName);
      /** if (UY_HRLoadDriverLine_ID == 0)
        {
			setC_BPartner_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setHR_Concept_ID (0);
			setUY_HRLoadDriver_ID (0);
			setUY_HRLoadDriverLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRLoadDriverLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRLoadDriverLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtDeducible.
		@param AmtDeducible AmtDeducible	  */
	public void setAmtDeducible (BigDecimal AmtDeducible)
	{
		set_Value (COLUMNNAME_AmtDeducible, AmtDeducible);
	}

	/** Get AmtDeducible.
		@return AmtDeducible	  */
	public BigDecimal getAmtDeducible () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtDeducible);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtFeriadoBrasil.
		@param AmtFeriadoBrasil AmtFeriadoBrasil	  */
	public void setAmtFeriadoBrasil (BigDecimal AmtFeriadoBrasil)
	{
		set_Value (COLUMNNAME_AmtFeriadoBrasil, AmtFeriadoBrasil);
	}

	/** Get AmtFeriadoBrasil.
		@return AmtFeriadoBrasil	  */
	public BigDecimal getAmtFeriadoBrasil () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtFeriadoBrasil);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtHoraExtra.
		@param AmtHoraExtra AmtHoraExtra	  */
	public void setAmtHoraExtra (BigDecimal AmtHoraExtra)
	{
		set_Value (COLUMNNAME_AmtHoraExtra, AmtHoraExtra);
	}

	/** Get AmtHoraExtra.
		@return AmtHoraExtra	  */
	public BigDecimal getAmtHoraExtra () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtHoraExtra);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtJornal.
		@param AmtJornal AmtJornal	  */
	public void setAmtJornal (BigDecimal AmtJornal)
	{
		set_Value (COLUMNNAME_AmtJornal, AmtJornal);
	}

	/** Get AmtJornal.
		@return AmtJornal	  */
	public BigDecimal getAmtJornal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtJornal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtJornalExt.
		@param AmtJornalExt AmtJornalExt	  */
	public void setAmtJornalExt (BigDecimal AmtJornalExt)
	{
		set_Value (COLUMNNAME_AmtJornalExt, AmtJornalExt);
	}

	/** Get AmtJornalExt.
		@return AmtJornalExt	  */
	public BigDecimal getAmtJornalExt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtJornalExt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtLicencia.
		@param AmtLicencia AmtLicencia	  */
	public void setAmtLicencia (BigDecimal AmtLicencia)
	{
		set_Value (COLUMNNAME_AmtLicencia, AmtLicencia);
	}

	/** Get AmtLicencia.
		@return AmtLicencia	  */
	public BigDecimal getAmtLicencia () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtLicencia);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtViaticoExt.
		@param AmtViaticoExt AmtViaticoExt	  */
	public void setAmtViaticoExt (BigDecimal AmtViaticoExt)
	{
		set_Value (COLUMNNAME_AmtViaticoExt, AmtViaticoExt);
	}

	/** Get AmtViaticoExt.
		@return AmtViaticoExt	  */
	public BigDecimal getAmtViaticoExt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtViaticoExt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtViaticoNac.
		@param AmtViaticoNac AmtViaticoNac	  */
	public void setAmtViaticoNac (BigDecimal AmtViaticoNac)
	{
		set_Value (COLUMNNAME_AmtViaticoNac, AmtViaticoNac);
	}

	/** Get AmtViaticoNac.
		@return AmtViaticoNac	  */
	public BigDecimal getAmtViaticoNac () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtViaticoNac);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_HRLoadDriver getUY_HRLoadDriver() throws RuntimeException
    {
		return (I_UY_HRLoadDriver)MTable.get(getCtx(), I_UY_HRLoadDriver.Table_Name)
			.getPO(getUY_HRLoadDriver_ID(), get_TrxName());	}

	/** Set UY_HRLoadDriver.
		@param UY_HRLoadDriver_ID UY_HRLoadDriver	  */
	public void setUY_HRLoadDriver_ID (int UY_HRLoadDriver_ID)
	{
		if (UY_HRLoadDriver_ID < 1) 
			set_Value (COLUMNNAME_UY_HRLoadDriver_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRLoadDriver_ID, Integer.valueOf(UY_HRLoadDriver_ID));
	}

	/** Get UY_HRLoadDriver.
		@return UY_HRLoadDriver	  */
	public int getUY_HRLoadDriver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRLoadDriver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRLoadDriverLine.
		@param UY_HRLoadDriverLine_ID UY_HRLoadDriverLine	  */
	public void setUY_HRLoadDriverLine_ID (int UY_HRLoadDriverLine_ID)
	{
		if (UY_HRLoadDriverLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRLoadDriverLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRLoadDriverLine_ID, Integer.valueOf(UY_HRLoadDriverLine_ID));
	}

	/** Get UY_HRLoadDriverLine.
		@return UY_HRLoadDriverLine	  */
	public int getUY_HRLoadDriverLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRLoadDriverLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}