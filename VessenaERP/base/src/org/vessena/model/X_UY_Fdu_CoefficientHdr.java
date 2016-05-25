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

/** Generated Model for UY_Fdu_CoefficientHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_CoefficientHdr extends PO implements I_UY_Fdu_CoefficientHdr, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130111L;

    /** Standard Constructor */
    public X_UY_Fdu_CoefficientHdr (Properties ctx, int UY_Fdu_CoefficientHdr_ID, String trxName)
    {
      super (ctx, UY_Fdu_CoefficientHdr_ID, trxName);
      /** if (UY_Fdu_CoefficientHdr_ID == 0)
        {
			setUY_Fdu_CoefficientHdr_ID (0);
			setvalidity (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_CoefficientHdr (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_CoefficientHdr[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_CloneCoefficient.
		@param UY_CloneCoefficient 
		UY_CloneCoefficient
	  */
	public void setUY_CloneCoefficient (String UY_CloneCoefficient)
	{
		set_Value (COLUMNNAME_UY_CloneCoefficient, UY_CloneCoefficient);
	}

	/** Get UY_CloneCoefficient.
		@return UY_CloneCoefficient
	  */
	public String getUY_CloneCoefficient () 
	{
		return (String)get_Value(COLUMNNAME_UY_CloneCoefficient);
	}

	/** Set UY_Fdu_CoefficientHdr.
		@param UY_Fdu_CoefficientHdr_ID UY_Fdu_CoefficientHdr	  */
	public void setUY_Fdu_CoefficientHdr_ID (int UY_Fdu_CoefficientHdr_ID)
	{
		if (UY_Fdu_CoefficientHdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CoefficientHdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_CoefficientHdr_ID, Integer.valueOf(UY_Fdu_CoefficientHdr_ID));
	}

	/** Get UY_Fdu_CoefficientHdr.
		@return UY_Fdu_CoefficientHdr	  */
	public int getUY_Fdu_CoefficientHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_CoefficientHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set validity.
		@param validity validity	  */
	public void setvalidity (Timestamp validity)
	{
		set_Value (COLUMNNAME_validity, validity);
	}

	/** Get validity.
		@return validity	  */
	public Timestamp getvalidity () 
	{
		return (Timestamp)get_Value(COLUMNNAME_validity);
	}
}