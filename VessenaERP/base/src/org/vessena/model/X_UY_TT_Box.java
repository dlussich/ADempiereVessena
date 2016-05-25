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

/** Generated Model for UY_TT_Box
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_Box extends PO implements I_UY_TT_Box, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150106L;

    /** Standard Constructor */
    public X_UY_TT_Box (Properties ctx, int UY_TT_Box_ID, String trxName)
    {
      super (ctx, UY_TT_Box_ID, trxName);
      /** if (UY_TT_Box_ID == 0)
        {
			setAD_User_ID (0);
			setBoxStatus (null);
			setBoxType (null);
			setComunicaUsuario (false);
// N
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setImpresionVale (false);
// N
			setIsComplete (false);
// N
			setIsConfirmed (false);
// N
			setIsDestiny (false);
// N
			setUnificaCardCarrier (false);
// N
			setUY_DeliveryPoint_ID (0);
			setUY_TT_Box_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_Box (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_Box[")
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

	/** BoxStatus AD_Reference_ID=1000329 */
	public static final int BOXSTATUS_AD_Reference_ID=1000329;
	/** Cerrado = CERRADO */
	public static final String BOXSTATUS_Cerrado = "CERRADO";
	/** Recepcion Cuentas = RECEPCUENTA */
	public static final String BOXSTATUS_RecepcionCuentas = "RECEPCUENTA";
	/** Carga Bolsin = CARGABOLSIN */
	public static final String BOXSTATUS_CargaBolsin = "CARGABOLSIN";
	/** Nuevo = NUEVO */
	public static final String BOXSTATUS_Nuevo = "NUEVO";
	/** Unificacion CardCarrier = UNIFICACION */
	public static final String BOXSTATUS_UnificacionCardCarrier = "UNIFICACION";
	/** Proceso Retenidas = PROCESORETENIDAS */
	public static final String BOXSTATUS_ProcesoRetenidas = "PROCESORETENIDAS";
	/** Comunicacion Usuario = COMUNICACION */
	public static final String BOXSTATUS_ComunicacionUsuario = "COMUNICACION";
	/** Reubicacion = REUBICACION */
	public static final String BOXSTATUS_Reubicacion = "REUBICACION";
	/** Impresion Vale = IMPRESIONVALE */
	public static final String BOXSTATUS_ImpresionVale = "IMPRESIONVALE";
	/** Set BoxStatus.
		@param BoxStatus BoxStatus	  */
	public void setBoxStatus (String BoxStatus)
	{

		set_Value (COLUMNNAME_BoxStatus, BoxStatus);
	}

	/** Get BoxStatus.
		@return BoxStatus	  */
	public String getBoxStatus () 
	{
		return (String)get_Value(COLUMNNAME_BoxStatus);
	}

	/** Set BoxType.
		@param BoxType BoxType	  */
	public void setBoxType (String BoxType)
	{
		set_Value (COLUMNNAME_BoxType, BoxType);
	}

	/** Get BoxType.
		@return BoxType	  */
	public String getBoxType () 
	{
		return (String)get_Value(COLUMNNAME_BoxType);
	}

	/** Set ComunicaUsuario.
		@param ComunicaUsuario ComunicaUsuario	  */
	public void setComunicaUsuario (boolean ComunicaUsuario)
	{
		set_Value (COLUMNNAME_ComunicaUsuario, Boolean.valueOf(ComunicaUsuario));
	}

	/** Get ComunicaUsuario.
		@return ComunicaUsuario	  */
	public boolean isComunicaUsuario () 
	{
		Object oo = get_Value(COLUMNNAME_ComunicaUsuario);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set ImpresionVale.
		@param ImpresionVale ImpresionVale	  */
	public void setImpresionVale (boolean ImpresionVale)
	{
		set_Value (COLUMNNAME_ImpresionVale, Boolean.valueOf(ImpresionVale));
	}

	/** Get ImpresionVale.
		@return ImpresionVale	  */
	public boolean isImpresionVale () 
	{
		Object oo = get_Value(COLUMNNAME_ImpresionVale);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set IsDestiny.
		@param IsDestiny IsDestiny	  */
	public void setIsDestiny (boolean IsDestiny)
	{
		set_Value (COLUMNNAME_IsDestiny, Boolean.valueOf(IsDestiny));
	}

	/** Get IsDestiny.
		@return IsDestiny	  */
	public boolean isDestiny () 
	{
		Object oo = get_Value(COLUMNNAME_IsDestiny);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRetained.
		@param IsRetained IsRetained	  */
	public void setIsRetained (boolean IsRetained)
	{
		set_Value (COLUMNNAME_IsRetained, Boolean.valueOf(IsRetained));
	}

	/** Get IsRetained.
		@return IsRetained	  */
	public boolean isRetained () 
	{
		Object oo = get_Value(COLUMNNAME_IsRetained);
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

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (int QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, Integer.valueOf(QtyCount));
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

	/** RetainedStatus AD_Reference_ID=1000340 */
	public static final int RETAINEDSTATUS_AD_Reference_ID=1000340;
	/** Destruccion = DESTRUIR */
	public static final String RETAINEDSTATUS_Destruccion = "DESTRUIR";
	/** Inconsistente = INCONSISTENTE */
	public static final String RETAINEDSTATUS_Inconsistente = "INCONSISTENTE";
	/** Set RetainedStatus.
		@param RetainedStatus RetainedStatus	  */
	public void setRetainedStatus (String RetainedStatus)
	{

		set_Value (COLUMNNAME_RetainedStatus, RetainedStatus);
	}

	/** Get RetainedStatus.
		@return RetainedStatus	  */
	public String getRetainedStatus () 
	{
		return (String)get_Value(COLUMNNAME_RetainedStatus);
	}

	/** Set UnificaCardCarrier.
		@param UnificaCardCarrier UnificaCardCarrier	  */
	public void setUnificaCardCarrier (boolean UnificaCardCarrier)
	{
		set_Value (COLUMNNAME_UnificaCardCarrier, Boolean.valueOf(UnificaCardCarrier));
	}

	/** Get UnificaCardCarrier.
		@return UnificaCardCarrier	  */
	public boolean isUnificaCardCarrier () 
	{
		Object oo = get_Value(COLUMNNAME_UnificaCardCarrier);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set UY_TT_Box.
		@param UY_TT_Box_ID UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID)
	{
		if (UY_TT_Box_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Box_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Box_ID, Integer.valueOf(UY_TT_Box_ID));
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