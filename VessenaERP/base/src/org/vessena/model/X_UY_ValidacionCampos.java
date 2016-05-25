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

/** Generated Model for UY_ValidacionCampos
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_ValidacionCampos extends PO implements I_UY_ValidacionCampos, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_ValidacionCampos (Properties ctx, int UY_ValidacionCampos_ID, String trxName)
    {
      super (ctx, UY_ValidacionCampos_ID, trxName);
      /** if (UY_ValidacionCampos_ID == 0)
        {
			setUY_ValidacionCampos_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ValidacionCampos (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ValidacionCampos[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
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

	/** Set uy_c_bank_iscaja_id.
		@param uy_c_bank_iscaja_id uy_c_bank_iscaja_id	  */
	public void setuy_c_bank_iscaja_id (int uy_c_bank_iscaja_id)
	{
		set_Value (COLUMNNAME_uy_c_bank_iscaja_id, Integer.valueOf(uy_c_bank_iscaja_id));
	}

	/** Get uy_c_bank_iscaja_id.
		@return uy_c_bank_iscaja_id	  */
	public int getuy_c_bank_iscaja_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bank_iscaja_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_c_bank_iscajame_id.
		@param uy_c_bank_iscajame_id uy_c_bank_iscajame_id	  */
	public void setuy_c_bank_iscajame_id (int uy_c_bank_iscajame_id)
	{
		set_Value (COLUMNNAME_uy_c_bank_iscajame_id, Integer.valueOf(uy_c_bank_iscajame_id));
	}

	/** Get uy_c_bank_iscajame_id.
		@return uy_c_bank_iscajame_id	  */
	public int getuy_c_bank_iscajame_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bank_iscajame_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_c_bank_iscajamn_id.
		@param uy_c_bank_iscajamn_id uy_c_bank_iscajamn_id	  */
	public void setuy_c_bank_iscajamn_id (int uy_c_bank_iscajamn_id)
	{
		set_Value (COLUMNNAME_uy_c_bank_iscajamn_id, Integer.valueOf(uy_c_bank_iscajamn_id));
	}

	/** Get uy_c_bank_iscajamn_id.
		@return uy_c_bank_iscajamn_id	  */
	public int getuy_c_bank_iscajamn_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bank_iscajamn_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_c_bankaccount_propio_id.
		@param uy_c_bankaccount_propio_id uy_c_bankaccount_propio_id	  */
	public void setuy_c_bankaccount_propio_id (int uy_c_bankaccount_propio_id)
	{
		set_Value (COLUMNNAME_uy_c_bankaccount_propio_id, Integer.valueOf(uy_c_bankaccount_propio_id));
	}

	/** Get uy_c_bankaccount_propio_id.
		@return uy_c_bankaccount_propio_id	  */
	public int getuy_c_bankaccount_propio_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bankaccount_propio_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_c_bankaccount_propiome_id.
		@param uy_c_bankaccount_propiome_id uy_c_bankaccount_propiome_id	  */
	public void setuy_c_bankaccount_propiome_id (int uy_c_bankaccount_propiome_id)
	{
		set_Value (COLUMNNAME_uy_c_bankaccount_propiome_id, Integer.valueOf(uy_c_bankaccount_propiome_id));
	}

	/** Get uy_c_bankaccount_propiome_id.
		@return uy_c_bankaccount_propiome_id	  */
	public int getuy_c_bankaccount_propiome_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bankaccount_propiome_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_c_bankaccount_propiomn_id.
		@param uy_c_bankaccount_propiomn_id uy_c_bankaccount_propiomn_id	  */
	public void setuy_c_bankaccount_propiomn_id (int uy_c_bankaccount_propiomn_id)
	{
		set_Value (COLUMNNAME_uy_c_bankaccount_propiomn_id, Integer.valueOf(uy_c_bankaccount_propiomn_id));
	}

	/** Get uy_c_bankaccount_propiomn_id.
		@return uy_c_bankaccount_propiomn_id	  */
	public int getuy_c_bankaccount_propiomn_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bankaccount_propiomn_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_c_bankaccount_terceros_id.
		@param uy_c_bankaccount_terceros_id uy_c_bankaccount_terceros_id	  */
	public void setuy_c_bankaccount_terceros_id (int uy_c_bankaccount_terceros_id)
	{
		set_Value (COLUMNNAME_uy_c_bankaccount_terceros_id, Integer.valueOf(uy_c_bankaccount_terceros_id));
	}

	/** Get uy_c_bankaccount_terceros_id.
		@return uy_c_bankaccount_terceros_id	  */
	public int getuy_c_bankaccount_terceros_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bankaccount_terceros_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_c_bankaccount_tercerosme_id.
		@param uy_c_bankaccount_tercerosme_id uy_c_bankaccount_tercerosme_id	  */
	public void setuy_c_bankaccount_tercerosme_id (int uy_c_bankaccount_tercerosme_id)
	{
		set_Value (COLUMNNAME_uy_c_bankaccount_tercerosme_id, Integer.valueOf(uy_c_bankaccount_tercerosme_id));
	}

	/** Get uy_c_bankaccount_tercerosme_id.
		@return uy_c_bankaccount_tercerosme_id	  */
	public int getuy_c_bankaccount_tercerosme_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bankaccount_tercerosme_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_c_bankaccount_tercerosmn_id.
		@param uy_c_bankaccount_tercerosmn_id uy_c_bankaccount_tercerosmn_id	  */
	public void setuy_c_bankaccount_tercerosmn_id (int uy_c_bankaccount_tercerosmn_id)
	{
		set_Value (COLUMNNAME_uy_c_bankaccount_tercerosmn_id, Integer.valueOf(uy_c_bankaccount_tercerosmn_id));
	}

	/** Get uy_c_bankaccount_tercerosmn_id.
		@return uy_c_bankaccount_tercerosmn_id	  */
	public int getuy_c_bankaccount_tercerosmn_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_c_bankaccount_tercerosmn_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ValidacionCampos.
		@param UY_ValidacionCampos_ID UY_ValidacionCampos	  */
	public void setUY_ValidacionCampos_ID (int UY_ValidacionCampos_ID)
	{
		if (UY_ValidacionCampos_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ValidacionCampos_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ValidacionCampos_ID, Integer.valueOf(UY_ValidacionCampos_ID));
	}

	/** Get UY_ValidacionCampos.
		@return UY_ValidacionCampos	  */
	public int getUY_ValidacionCampos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ValidacionCampos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}