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

/** Generated Model for UY_HRProcesoNomEmpleados
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_HRProcesoNomEmpleados extends PO implements I_UY_HRProcesoNomEmpleados, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120323L;

    /** Standard Constructor */
    public X_UY_HRProcesoNomEmpleados (Properties ctx, int UY_HRProcesoNomEmpleados_ID, String trxName)
    {
      super (ctx, UY_HRProcesoNomEmpleados_ID, trxName);
      /** if (UY_HRProcesoNomEmpleados_ID == 0)
        {
			setC_BPartner_ID (0);
			setUY_HRProcesoNomEmpleados_ID (0);
			setUY_HRProcesoNomina_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRProcesoNomEmpleados (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRProcesoNomEmpleados[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set UY_HRProcesoNomEmpleados.
		@param UY_HRProcesoNomEmpleados_ID UY_HRProcesoNomEmpleados	  */
	public void setUY_HRProcesoNomEmpleados_ID (int UY_HRProcesoNomEmpleados_ID)
	{
		if (UY_HRProcesoNomEmpleados_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRProcesoNomEmpleados_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRProcesoNomEmpleados_ID, Integer.valueOf(UY_HRProcesoNomEmpleados_ID));
	}

	/** Get UY_HRProcesoNomEmpleados.
		@return UY_HRProcesoNomEmpleados	  */
	public int getUY_HRProcesoNomEmpleados_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRProcesoNomEmpleados_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/*public I_UY_HRProcesoNomina getUY_HRProcesoNomina() throws RuntimeException
    {
		return (I_UY_HRProcesoNomina)MTable.get(getCtx(), I_UY_HRProcesoNomina.Table_Name)
			.getPO(getUY_HRProcesoNomina_ID(), get_TrxName());	}*/

	/** Set UY_HRProcesoNomina.
		@param UY_HRProcesoNomina_ID UY_HRProcesoNomina	  */
	public void setUY_HRProcesoNomina_ID (int UY_HRProcesoNomina_ID)
	{
		if (UY_HRProcesoNomina_ID < 1) 
			set_Value (COLUMNNAME_UY_HRProcesoNomina_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRProcesoNomina_ID, Integer.valueOf(UY_HRProcesoNomina_ID));
	}

	/** Get UY_HRProcesoNomina.
		@return UY_HRProcesoNomina	  */
	public int getUY_HRProcesoNomina_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRProcesoNomina_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}