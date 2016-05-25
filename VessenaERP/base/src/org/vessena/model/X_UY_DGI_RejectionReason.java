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

/** Generated Model for UY_DGI_RejectionReason
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DGI_RejectionReason extends PO implements I_UY_DGI_RejectionReason, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150611L;

    /** Standard Constructor */
    public X_UY_DGI_RejectionReason (Properties ctx, int UY_DGI_RejectionReason_ID, String trxName)
    {
      super (ctx, UY_DGI_RejectionReason_ID, trxName);
      /** if (UY_DGI_RejectionReason_ID == 0)
        {
			setUY_DGI_RejectionReason_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DGI_RejectionReason (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DGI_RejectionReason[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CodigoMotivo.
		@param CodigoMotivo CodigoMotivo	  */
	public void setCodigoMotivo (String CodigoMotivo)
	{
		set_Value (COLUMNNAME_CodigoMotivo, CodigoMotivo);
	}

	/** Get CodigoMotivo.
		@return CodigoMotivo	  */
	public String getCodigoMotivo () 
	{
		return (String)get_Value(COLUMNNAME_CodigoMotivo);
	}

	/** Set DetalleRechazo.
		@param DetalleRechazo DetalleRechazo	  */
	public void setDetalleRechazo (String DetalleRechazo)
	{
		set_Value (COLUMNNAME_DetalleRechazo, DetalleRechazo);
	}

	/** Get DetalleRechazo.
		@return DetalleRechazo	  */
	public String getDetalleRechazo () 
	{
		return (String)get_Value(COLUMNNAME_DetalleRechazo);
	}

	/** Set GlosaMotivo.
		@param GlosaMotivo GlosaMotivo	  */
	public void setGlosaMotivo (String GlosaMotivo)
	{
		set_Value (COLUMNNAME_GlosaMotivo, GlosaMotivo);
	}

	/** Get GlosaMotivo.
		@return GlosaMotivo	  */
	public String getGlosaMotivo () 
	{
		return (String)get_Value(COLUMNNAME_GlosaMotivo);
	}

	public I_UY_DGI_Envelope getUY_DGI_Envelope() throws RuntimeException
    {
		return (I_UY_DGI_Envelope)MTable.get(getCtx(), I_UY_DGI_Envelope.Table_Name)
			.getPO(getUY_DGI_Envelope_ID(), get_TrxName());	}

	/** Set UY_DGI_Envelope.
		@param UY_DGI_Envelope_ID UY_DGI_Envelope	  */
	public void setUY_DGI_Envelope_ID (int UY_DGI_Envelope_ID)
	{
		if (UY_DGI_Envelope_ID < 1) 
			set_Value (COLUMNNAME_UY_DGI_Envelope_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DGI_Envelope_ID, Integer.valueOf(UY_DGI_Envelope_ID));
	}

	/** Get UY_DGI_Envelope.
		@return UY_DGI_Envelope	  */
	public int getUY_DGI_Envelope_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DGI_Envelope_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DGI_RejectionReason.
		@param UY_DGI_RejectionReason_ID UY_DGI_RejectionReason	  */
	public void setUY_DGI_RejectionReason_ID (int UY_DGI_RejectionReason_ID)
	{
		if (UY_DGI_RejectionReason_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DGI_RejectionReason_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DGI_RejectionReason_ID, Integer.valueOf(UY_DGI_RejectionReason_ID));
	}

	/** Get UY_DGI_RejectionReason.
		@return UY_DGI_RejectionReason	  */
	public int getUY_DGI_RejectionReason_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DGI_RejectionReason_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}