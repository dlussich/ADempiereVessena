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

/** Generated Model for UY_POPolicyUser
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_POPolicyUser extends PO implements I_UY_POPolicyUser, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140306L;

    /** Standard Constructor */
    public X_UY_POPolicyUser (Properties ctx, int UY_POPolicyUser_ID, String trxName)
    {
      super (ctx, UY_POPolicyUser_ID, trxName);
      /** if (UY_POPolicyUser_ID == 0)
        {
			setUY_POPolicyRange_ID (0);
			setUY_POPolicyUser_ID (0);
			setUY_POSection_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_POPolicyUser (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_POPolicyUser[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException
    {
		return (org.compiere.model.I_AD_WF_Responsible)MTable.get(getCtx(), org.compiere.model.I_AD_WF_Responsible.Table_Name)
			.getPO(getAD_WF_Responsible_ID(), get_TrxName());	}

	/** Set Workflow Responsible.
		@param AD_WF_Responsible_ID 
		Responsible for Workflow Execution
	  */
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	/** Get Workflow Responsible.
		@return Responsible for Workflow Execution
	  */
	public int getAD_WF_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** nivel AD_Reference_ID=1000208 */
	public static final int NIVEL_AD_Reference_ID=1000208;
	/** Nivel 1 = 1 */
	public static final String NIVEL_Nivel1 = "1";
	/** Nivel 2 = 2 */
	public static final String NIVEL_Nivel2 = "2";
	/** Set nivel.
		@param nivel nivel	  */
	public void setnivel (String nivel)
	{

		set_Value (COLUMNNAME_nivel, nivel);
	}

	/** Get nivel.
		@return nivel	  */
	public String getnivel () 
	{
		return (String)get_Value(COLUMNNAME_nivel);
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

	public I_UY_POPolicyRange getUY_POPolicyRange() throws RuntimeException
    {
		return (I_UY_POPolicyRange)MTable.get(getCtx(), I_UY_POPolicyRange.Table_Name)
			.getPO(getUY_POPolicyRange_ID(), get_TrxName());	}

	/** Set UY_POPolicyRange.
		@param UY_POPolicyRange_ID UY_POPolicyRange	  */
	public void setUY_POPolicyRange_ID (int UY_POPolicyRange_ID)
	{
		if (UY_POPolicyRange_ID < 1) 
			set_Value (COLUMNNAME_UY_POPolicyRange_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POPolicyRange_ID, Integer.valueOf(UY_POPolicyRange_ID));
	}

	/** Get UY_POPolicyRange.
		@return UY_POPolicyRange	  */
	public int getUY_POPolicyRange_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POPolicyRange_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_POPolicyUser.
		@param UY_POPolicyUser_ID UY_POPolicyUser	  */
	public void setUY_POPolicyUser_ID (int UY_POPolicyUser_ID)
	{
		if (UY_POPolicyUser_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_POPolicyUser_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_POPolicyUser_ID, Integer.valueOf(UY_POPolicyUser_ID));
	}

	/** Get UY_POPolicyUser.
		@return UY_POPolicyUser	  */
	public int getUY_POPolicyUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POPolicyUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_POSection getUY_POSection() throws RuntimeException
    {
		return (I_UY_POSection)MTable.get(getCtx(), I_UY_POSection.Table_Name)
			.getPO(getUY_POSection_ID(), get_TrxName());	}

	/** Set UY_POSection.
		@param UY_POSection_ID UY_POSection	  */
	public void setUY_POSection_ID (int UY_POSection_ID)
	{
		if (UY_POSection_ID < 1) 
			set_Value (COLUMNNAME_UY_POSection_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POSection_ID, Integer.valueOf(UY_POSection_ID));
	}

	/** Get UY_POSection.
		@return UY_POSection	  */
	public int getUY_POSection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POSection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}