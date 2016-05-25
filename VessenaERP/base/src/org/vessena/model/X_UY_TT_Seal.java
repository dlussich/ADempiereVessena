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

/** Generated Model for UY_TT_Seal
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_Seal extends PO implements I_UY_TT_Seal, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130910L;

    /** Standard Constructor */
    public X_UY_TT_Seal (Properties ctx, int UY_TT_Seal_ID, String trxName)
    {
      super (ctx, UY_TT_Seal_ID, trxName);
      /** if (UY_TT_Seal_ID == 0)
        {
			setAD_User_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setIsComplete (false);
// N
			setIsConfirmed (false);
// N
			setIsOwn (false);
			setSealStatus (null);
			setSealType (null);
			setUY_DeliveryPoint_ID (0);
			setUY_TT_Seal_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_Seal (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_Seal[")
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

	/** Set Complete.
		@param IsComplete 
		It is complete
	  */
	public void setIsComplete (boolean IsComplete)
	{
		set_Value (COLUMNNAME_IsComplete, Boolean.valueOf(IsComplete));
	}

	/** Get Complete.
		@return It is complete
	  */
	public boolean isComplete () 
	{
		Object oo = get_Value(COLUMNNAME_IsComplete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Confirmed.
		@param IsConfirmed 
		Assignment is confirmed
	  */
	public void setIsConfirmed (boolean IsConfirmed)
	{
		set_Value (COLUMNNAME_IsConfirmed, Boolean.valueOf(IsConfirmed));
	}

	/** Get Confirmed.
		@return Assignment is confirmed
	  */
	public boolean isConfirmed () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfirmed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsOwn.
		@param IsOwn IsOwn	  */
	public void setIsOwn (boolean IsOwn)
	{
		set_Value (COLUMNNAME_IsOwn, Boolean.valueOf(IsOwn));
	}

	/** Get IsOwn.
		@return IsOwn	  */
	public boolean isOwn () 
	{
		Object oo = get_Value(COLUMNNAME_IsOwn);
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

	/** Set Quantity book.
		@param QtyBook 
		Book Quantity
	  */
	public void setQtyBook (int QtyBook)
	{
		set_Value (COLUMNNAME_QtyBook, Integer.valueOf(QtyBook));
	}

	/** Get Quantity book.
		@return Book Quantity
	  */
	public int getQtyBook () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyBook);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (int QtyCount)
	{
		set_ValueNoCheck (COLUMNNAME_QtyCount, Integer.valueOf(QtyCount));
	}

	/** Get Quantity count.
		@return Counted Quantity
	  */
	public int getQtyCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** SealCountType AD_Reference_ID=1000333 */
	public static final int SEALCOUNTTYPE_AD_Reference_ID=1000333;
	/** Plasticos = PLASTICO */
	public static final String SEALCOUNTTYPE_Plasticos = "PLASTICO";
	/** Cuentas = CUENTA */
	public static final String SEALCOUNTTYPE_Cuentas = "CUENTA";
	/** Set SealCountType.
		@param SealCountType SealCountType	  */
	public void setSealCountType (String SealCountType)
	{

		set_Value (COLUMNNAME_SealCountType, SealCountType);
	}

	/** Get SealCountType.
		@return SealCountType	  */
	public String getSealCountType () 
	{
		return (String)get_Value(COLUMNNAME_SealCountType);
	}

	/** SealStatus AD_Reference_ID=1000322 */
	public static final int SEALSTATUS_AD_Reference_ID=1000322;
	/** Cerrado = CERRADO */
	public static final String SEALSTATUS_Cerrado = "CERRADO";
	/** Pendiente Envio = PENDIENTE ENVIO */
	public static final String SEALSTATUS_PendienteEnvio = "PENDIENTE ENVIO";
	/** Recepcionado = RECEPCIONADO */
	public static final String SEALSTATUS_Recepcionado = "RECEPCIONADO";
	/** Recepcion de Cuentas = RECEPCUENTA */
	public static final String SEALSTATUS_RecepcionDeCuentas = "RECEPCUENTA";
	/** Carga Bolsin = CARGABOLSIN */
	public static final String SEALSTATUS_CargaBolsin = "CARGABOLSIN";
	/** Set SealStatus.
		@param SealStatus SealStatus	  */
	public void setSealStatus (String SealStatus)
	{

		set_Value (COLUMNNAME_SealStatus, SealStatus);
	}

	/** Get SealStatus.
		@return SealStatus	  */
	public String getSealStatus () 
	{
		return (String)get_Value(COLUMNNAME_SealStatus);
	}

	/** Set SealType.
		@param SealType SealType	  */
	public void setSealType (String SealType)
	{
		set_Value (COLUMNNAME_SealType, SealType);
	}

	/** Get SealType.
		@return SealType	  */
	public String getSealType () 
	{
		return (String)get_Value(COLUMNNAME_SealType);
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

	/** Set UY_DeliveryPoint_ID_To.
		@param UY_DeliveryPoint_ID_To UY_DeliveryPoint_ID_To	  */
	public void setUY_DeliveryPoint_ID_To (int UY_DeliveryPoint_ID_To)
	{
		set_Value (COLUMNNAME_UY_DeliveryPoint_ID_To, Integer.valueOf(UY_DeliveryPoint_ID_To));
	}

	/** Get UY_DeliveryPoint_ID_To.
		@return UY_DeliveryPoint_ID_To	  */
	public int getUY_DeliveryPoint_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Delivery getUY_TT_Delivery() throws RuntimeException
    {
		return (I_UY_TT_Delivery)MTable.get(getCtx(), I_UY_TT_Delivery.Table_Name)
			.getPO(getUY_TT_Delivery_ID(), get_TrxName());	}

	/** Set UY_TT_Delivery.
		@param UY_TT_Delivery_ID UY_TT_Delivery	  */
	public void setUY_TT_Delivery_ID (int UY_TT_Delivery_ID)
	{
		if (UY_TT_Delivery_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Delivery_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Delivery_ID, Integer.valueOf(UY_TT_Delivery_ID));
	}

	/** Get UY_TT_Delivery.
		@return UY_TT_Delivery	  */
	public int getUY_TT_Delivery_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Delivery_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_Seal.
		@param UY_TT_Seal_ID UY_TT_Seal	  */
	public void setUY_TT_Seal_ID (int UY_TT_Seal_ID)
	{
		if (UY_TT_Seal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Seal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Seal_ID, Integer.valueOf(UY_TT_Seal_ID));
	}

	/** Get UY_TT_Seal.
		@return UY_TT_Seal	  */
	public int getUY_TT_Seal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Seal_ID);
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