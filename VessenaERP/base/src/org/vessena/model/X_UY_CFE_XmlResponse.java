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

/** Generated Model for UY_CFE_XmlResponse
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_XmlResponse extends PO implements I_UY_CFE_XmlResponse, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151222L;

    /** Standard Constructor */
    public X_UY_CFE_XmlResponse (Properties ctx, int UY_CFE_XmlResponse_ID, String trxName)
    {
      super (ctx, UY_CFE_XmlResponse_ID, trxName);
      /** if (UY_CFE_XmlResponse_ID == 0)
        {
			setUY_CFE_XmlResponse_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_XmlResponse (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_XmlResponse[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set UY_CFE_XmlResponse.
		@param UY_CFE_XmlResponse_ID UY_CFE_XmlResponse	  */
	public void setUY_CFE_XmlResponse_ID (int UY_CFE_XmlResponse_ID)
	{
		if (UY_CFE_XmlResponse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_XmlResponse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_XmlResponse_ID, Integer.valueOf(UY_CFE_XmlResponse_ID));
	}

	/** Get UY_CFE_XmlResponse.
		@return UY_CFE_XmlResponse	  */
	public int getUY_CFE_XmlResponse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_XmlResponse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set XMLResponse.
		@param XMLResponse XMLResponse	  */
	public void setXMLResponse (String XMLResponse)
	{
		set_Value (COLUMNNAME_XMLResponse, XMLResponse);
	}

	/** Get XMLResponse.
		@return XMLResponse	  */
	public String getXMLResponse () 
	{
		return (String)get_Value(COLUMNNAME_XMLResponse);
	}
}