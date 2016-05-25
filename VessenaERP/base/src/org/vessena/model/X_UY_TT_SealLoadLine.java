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

/** Generated Model for UY_TT_SealLoadLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_SealLoadLine extends PO implements I_UY_TT_SealLoadLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131007L;

    /** Standard Constructor */
    public X_UY_TT_SealLoadLine (Properties ctx, int UY_TT_SealLoadLine_ID, String trxName)
    {
      super (ctx, UY_TT_SealLoadLine_ID, trxName);
      /** if (UY_TT_SealLoadLine_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setIsValid (false);
// N
			setUY_TT_SealLoad_ID (0);
			setUY_TT_SealLoadLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_SealLoadLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_SealLoadLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address 1.
		@param Address1 
		Address line 1 for this location
	  */
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Address 1.
		@return Address line 1 for this location
	  */
	public String getAddress1 () 
	{
		return (String)get_Value(COLUMNNAME_Address1);
	}

	/** CardAction AD_Reference_ID=1000325 */
	public static final int CARDACTION_AD_Reference_ID=1000325;
	/** Renovacion = RENOVACION */
	public static final String CARDACTION_Renovacion = "RENOVACION";
	/** Reimpresion = REIMPRESION */
	public static final String CARDACTION_Reimpresion = "REIMPRESION";
	/** Nueva = NUEVA */
	public static final String CARDACTION_Nueva = "NUEVA";
	/** Set CardAction.
		@param CardAction CardAction	  */
	public void setCardAction (String CardAction)
	{

		set_Value (COLUMNNAME_CardAction, CardAction);
	}

	/** Get CardAction.
		@return CardAction	  */
	public String getCardAction () 
	{
		return (String)get_Value(COLUMNNAME_CardAction);
	}

	/** CardDestination AD_Reference_ID=1000326 */
	public static final int CARDDESTINATION_AD_Reference_ID=1000326;
	/** Sucursal = SUCURSAL */
	public static final String CARDDESTINATION_Sucursal = "SUCURSAL";
	/** Domicilio Particular = DOMICILIO */
	public static final String CARDDESTINATION_DomicilioParticular = "DOMICILIO";
	/** Set CardDestination.
		@param CardDestination CardDestination	  */
	public void setCardDestination (String CardDestination)
	{

		set_Value (COLUMNNAME_CardDestination, CardDestination);
	}

	/** Get CardDestination.
		@return CardDestination	  */
	public String getCardDestination () 
	{
		return (String)get_Value(COLUMNNAME_CardDestination);
	}

	/** Set Credit limit.
		@param CreditLimit 
		Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit)
	{
		set_Value (COLUMNNAME_CreditLimit, CreditLimit);
	}

	/** Get Credit limit.
		@return Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (String DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public String getDueDate () 
	{
		return (String)get_Value(COLUMNNAME_DueDate);
	}

	/** Set GAFCOD.
		@param GAFCOD GAFCOD	  */
	public void setGAFCOD (int GAFCOD)
	{
		set_Value (COLUMNNAME_GAFCOD, Integer.valueOf(GAFCOD));
	}

	/** Get GAFCOD.
		@return GAFCOD	  */
	public int getGAFCOD () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GAFCOD);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GAFNOM.
		@param GAFNOM GAFNOM	  */
	public void setGAFNOM (String GAFNOM)
	{
		set_Value (COLUMNNAME_GAFNOM, GAFNOM);
	}

	/** Get GAFNOM.
		@return GAFNOM	  */
	public String getGAFNOM () 
	{
		return (String)get_Value(COLUMNNAME_GAFNOM);
	}

	/** Set GrpCtaCte.
		@param GrpCtaCte GrpCtaCte	  */
	public void setGrpCtaCte (int GrpCtaCte)
	{
		set_Value (COLUMNNAME_GrpCtaCte, Integer.valueOf(GrpCtaCte));
	}

	/** Get GrpCtaCte.
		@return GrpCtaCte	  */
	public int getGrpCtaCte () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GrpCtaCte);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set InvalidText.
		@param InvalidText InvalidText	  */
	public void setInvalidText (String InvalidText)
	{
		set_Value (COLUMNNAME_InvalidText, InvalidText);
	}

	/** Get InvalidText.
		@return InvalidText	  */
	public String getInvalidText () 
	{
		return (String)get_Value(COLUMNNAME_InvalidText);
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set localidad.
		@param localidad localidad	  */
	public void setlocalidad (String localidad)
	{
		set_Value (COLUMNNAME_localidad, localidad);
	}

	/** Get localidad.
		@return localidad	  */
	public String getlocalidad () 
	{
		return (String)get_Value(COLUMNNAME_localidad);
	}

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, Integer.valueOf(LocatorValue));
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public int getLocatorValue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LocatorValue);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MLCod.
		@param MLCod MLCod	  */
	public void setMLCod (String MLCod)
	{
		set_Value (COLUMNNAME_MLCod, MLCod);
	}

	/** Get MLCod.
		@return MLCod	  */
	public String getMLCod () 
	{
		return (String)get_Value(COLUMNNAME_MLCod);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** NotDeliverableAction AD_Reference_ID=1000331 */
	public static final int NOTDELIVERABLEACTION_AD_Reference_ID=1000331;
	/** Destruccion = DESTRUIR */
	public static final String NOTDELIVERABLEACTION_Destruccion = "DESTRUIR";
	/** Retencion = RETENER */
	public static final String NOTDELIVERABLEACTION_Retencion = "RETENER";
	/** Set NotDeliverableAction.
		@param NotDeliverableAction NotDeliverableAction	  */
	public void setNotDeliverableAction (String NotDeliverableAction)
	{

		set_Value (COLUMNNAME_NotDeliverableAction, NotDeliverableAction);
	}

	/** Get NotDeliverableAction.
		@return NotDeliverableAction	  */
	public String getNotDeliverableAction () 
	{
		return (String)get_Value(COLUMNNAME_NotDeliverableAction);
	}

	/** Set ZIP.
		@param Postal 
		Postal code
	  */
	public void setPostal (String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get ZIP.
		@return Postal code
	  */
	public String getPostal () 
	{
		return (String)get_Value(COLUMNNAME_Postal);
	}

	/** Set ProductoAux.
		@param ProductoAux ProductoAux	  */
	public void setProductoAux (String ProductoAux)
	{
		set_Value (COLUMNNAME_ProductoAux, ProductoAux);
	}

	/** Get ProductoAux.
		@return ProductoAux	  */
	public String getProductoAux () 
	{
		return (String)get_Value(COLUMNNAME_ProductoAux);
	}

	/** Set ScanText.
		@param ScanText ScanText	  */
	public void setScanText (String ScanText)
	{
		set_Value (COLUMNNAME_ScanText, ScanText);
	}

	/** Get ScanText.
		@return ScanText	  */
	public String getScanText () 
	{
		return (String)get_Value(COLUMNNAME_ScanText);
	}

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID(), get_TrxName());	}

	/** Set UY_DeliveryPoint.
		@param UY_DeliveryPoint_ID UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID)
	{
		if (UY_DeliveryPoint_ID < 1) 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, Integer.valueOf(UY_DeliveryPoint_ID));
	}

	/** Get UY_DeliveryPoint.
		@return UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException
    {
		return (I_UY_TT_Box)MTable.get(getCtx(), I_UY_TT_Box.Table_Name)
			.getPO(getUY_TT_Box_ID(), get_TrxName());	}

	/** Set UY_TT_Box.
		@param UY_TT_Box_ID UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID)
	{
		if (UY_TT_Box_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Box_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Box_ID, Integer.valueOf(UY_TT_Box_ID));
	}

	/** Get UY_TT_Box.
		@return UY_TT_Box	  */
	public int getUY_TT_Box_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_Box_ID_1.
		@param UY_TT_Box_ID_1 
		UY_TT_Box_ID_1
	  */
	public void setUY_TT_Box_ID_1 (int UY_TT_Box_ID_1)
	{
		set_Value (COLUMNNAME_UY_TT_Box_ID_1, Integer.valueOf(UY_TT_Box_ID_1));
	}

	/** Get UY_TT_Box_ID_1.
		@return UY_TT_Box_ID_1
	  */
	public int getUY_TT_Box_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException
    {
		return (I_UY_TT_Card)MTable.get(getCtx(), I_UY_TT_Card.Table_Name)
			.getPO(getUY_TT_Card_ID(), get_TrxName());	}

	/** Set UY_TT_Card.
		@param UY_TT_Card_ID UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID)
	{
		if (UY_TT_Card_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Card_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Card_ID, Integer.valueOf(UY_TT_Card_ID));
	}

	/** Get UY_TT_Card.
		@return UY_TT_Card	  */
	public int getUY_TT_Card_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Card_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_SealLoad getUY_TT_SealLoad() throws RuntimeException
    {
		return (I_UY_TT_SealLoad)MTable.get(getCtx(), I_UY_TT_SealLoad.Table_Name)
			.getPO(getUY_TT_SealLoad_ID(), get_TrxName());	}

	/** Set UY_TT_SealLoad.
		@param UY_TT_SealLoad_ID UY_TT_SealLoad	  */
	public void setUY_TT_SealLoad_ID (int UY_TT_SealLoad_ID)
	{
		if (UY_TT_SealLoad_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_SealLoad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_SealLoad_ID, Integer.valueOf(UY_TT_SealLoad_ID));
	}

	/** Get UY_TT_SealLoad.
		@return UY_TT_SealLoad	  */
	public int getUY_TT_SealLoad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_SealLoad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_SealLoadLine.
		@param UY_TT_SealLoadLine_ID UY_TT_SealLoadLine	  */
	public void setUY_TT_SealLoadLine_ID (int UY_TT_SealLoadLine_ID)
	{
		if (UY_TT_SealLoadLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_SealLoadLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_SealLoadLine_ID, Integer.valueOf(UY_TT_SealLoadLine_ID));
	}

	/** Get UY_TT_SealLoadLine.
		@return UY_TT_SealLoadLine	  */
	public int getUY_TT_SealLoadLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_SealLoadLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_SealLoadScan getUY_TT_SealLoadScan() throws RuntimeException
    {
		return (I_UY_TT_SealLoadScan)MTable.get(getCtx(), I_UY_TT_SealLoadScan.Table_Name)
			.getPO(getUY_TT_SealLoadScan_ID(), get_TrxName());	}

	/** Set UY_TT_SealLoadScan.
		@param UY_TT_SealLoadScan_ID UY_TT_SealLoadScan	  */
	public void setUY_TT_SealLoadScan_ID (int UY_TT_SealLoadScan_ID)
	{
		if (UY_TT_SealLoadScan_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_SealLoadScan_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_SealLoadScan_ID, Integer.valueOf(UY_TT_SealLoadScan_ID));
	}

	/** Get UY_TT_SealLoadScan.
		@return UY_TT_SealLoadScan	  */
	public int getUY_TT_SealLoadScan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_SealLoadScan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}