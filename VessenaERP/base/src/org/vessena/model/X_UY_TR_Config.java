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

/** Generated Model for UY_TR_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Config extends PO implements I_UY_TR_Config, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150515L;

    /** Standard Constructor */
    public X_UY_TR_Config (Properties ctx, int UY_TR_Config_ID, String trxName)
    {
      super (ctx, UY_TR_Config_ID, trxName);
      /** if (UY_TR_Config_ID == 0)
        {
			setCityCodeWay (false);
// N
			setControlTripValue (false);
// N
			setConvertedAmt (false);
// N
			setIsOrderValeFlete (true);
// Y
			setIsVehiculo (false);
// N
			setIsYearIncluded (false);
// N
			setPrintInvoiceFooter (true);
// Y
			setUY_TR_Config_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Config (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Config[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_Client_ID_Aux.
		@param AD_Client_ID_Aux AD_Client_ID_Aux	  */
	public void setAD_Client_ID_Aux (int AD_Client_ID_Aux)
	{
		set_Value (COLUMNNAME_AD_Client_ID_Aux, Integer.valueOf(AD_Client_ID_Aux));
	}

	/** Get AD_Client_ID_Aux.
		@return AD_Client_ID_Aux	  */
	public int getAD_Client_ID_Aux () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Client_ID_Aux);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CityCodeWay.
		@param CityCodeWay CityCodeWay	  */
	public void setCityCodeWay (boolean CityCodeWay)
	{
		set_Value (COLUMNNAME_CityCodeWay, Boolean.valueOf(CityCodeWay));
	}

	/** Get CityCodeWay.
		@return CityCodeWay	  */
	public boolean isCityCodeWay () 
	{
		Object oo = get_Value(COLUMNNAME_CityCodeWay);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ControlTripValue.
		@param ControlTripValue ControlTripValue	  */
	public void setControlTripValue (boolean ControlTripValue)
	{
		set_Value (COLUMNNAME_ControlTripValue, Boolean.valueOf(ControlTripValue));
	}

	/** Get ControlTripValue.
		@return ControlTripValue	  */
	public boolean isControlTripValue () 
	{
		Object oo = get_Value(COLUMNNAME_ControlTripValue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Converted Amount.
		@param ConvertedAmt 
		Converted Amount
	  */
	public void setConvertedAmt (boolean ConvertedAmt)
	{
		set_Value (COLUMNNAME_ConvertedAmt, Boolean.valueOf(ConvertedAmt));
	}

	/** Get Converted Amount.
		@return Converted Amount
	  */
	public boolean isConvertedAmt () 
	{
		Object oo = get_Value(COLUMNNAME_ConvertedAmt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set IsOrderValeFlete.
		@param IsOrderValeFlete 
		IsOrderValeFlete
	  */
	public void setIsOrderValeFlete (boolean IsOrderValeFlete)
	{
		set_Value (COLUMNNAME_IsOrderValeFlete, Boolean.valueOf(IsOrderValeFlete));
	}

	/** Get IsOrderValeFlete.
		@return IsOrderValeFlete
	  */
	public boolean isOrderValeFlete () 
	{
		Object oo = get_Value(COLUMNNAME_IsOrderValeFlete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsVehiculo.
		@param IsVehiculo IsVehiculo	  */
	public void setIsVehiculo (boolean IsVehiculo)
	{
		set_Value (COLUMNNAME_IsVehiculo, Boolean.valueOf(IsVehiculo));
	}

	/** Get IsVehiculo.
		@return IsVehiculo	  */
	public boolean isVehiculo () 
	{
		Object oo = get_Value(COLUMNNAME_IsVehiculo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsYearIncluded.
		@param IsYearIncluded IsYearIncluded	  */
	public void setIsYearIncluded (boolean IsYearIncluded)
	{
		set_Value (COLUMNNAME_IsYearIncluded, Boolean.valueOf(IsYearIncluded));
	}

	/** Get IsYearIncluded.
		@return IsYearIncluded	  */
	public boolean isYearIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsYearIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Password.
		@param Password 
		Password of any length (case sensitive)
	  */
	public void setPassword (String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Password.
		@return Password of any length (case sensitive)
	  */
	public String getPassword () 
	{
		return (String)get_Value(COLUMNNAME_Password);
	}

	/** Set Pic1_ID.
		@param Pic1_ID Pic1_ID	  */
	public void setPic1_ID (int Pic1_ID)
	{
		if (Pic1_ID < 1) 
			set_Value (COLUMNNAME_Pic1_ID, null);
		else 
			set_Value (COLUMNNAME_Pic1_ID, Integer.valueOf(Pic1_ID));
	}

	/** Get Pic1_ID.
		@return Pic1_ID	  */
	public int getPic1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pic1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PrintInvoiceFooter.
		@param PrintInvoiceFooter PrintInvoiceFooter	  */
	public void setPrintInvoiceFooter (boolean PrintInvoiceFooter)
	{
		set_Value (COLUMNNAME_PrintInvoiceFooter, Boolean.valueOf(PrintInvoiceFooter));
	}

	/** Get PrintInvoiceFooter.
		@return PrintInvoiceFooter	  */
	public boolean isPrintInvoiceFooter () 
	{
		Object oo = get_Value(COLUMNNAME_PrintInvoiceFooter);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set QtyDecimal.
		@param QtyDecimal QtyDecimal	  */
	public void setQtyDecimal (int QtyDecimal)
	{
		set_Value (COLUMNNAME_QtyDecimal, Integer.valueOf(QtyDecimal));
	}

	/** Get QtyDecimal.
		@return QtyDecimal	  */
	public int getQtyDecimal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyDecimal);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Config.
		@param UY_TR_Config_ID UY_TR_Config	  */
	public void setUY_TR_Config_ID (int UY_TR_Config_ID)
	{
		if (UY_TR_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Config_ID, Integer.valueOf(UY_TR_Config_ID));
	}

	/** Get UY_TR_Config.
		@return UY_TR_Config	  */
	public int getUY_TR_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Config_ID);
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