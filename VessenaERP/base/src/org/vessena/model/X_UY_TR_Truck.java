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

/** Generated Model for UY_TR_Truck
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_Truck extends PO implements I_UY_TR_Truck, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150826L;

    /** Standard Constructor */
    public X_UY_TR_Truck (Properties ctx, int UY_TR_Truck_ID, String trxName)
    {
      super (ctx, UY_TR_Truck_ID, trxName);
      /** if (UY_TR_Truck_ID == 0)
        {
			setIsOwn (true);
// Y
			setIsPledged (false);
// N
			setIsPurchased (false);
// N
			setIsSold (false);
// N
			setQtyKmHubo (0);
// 0
			setTruckMode (null);
// INTERNACIONAL
			setUY_TR_Truck_ID (0);
			setUY_TR_TruckType_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_Truck (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_Truck[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set anio.
		@param anio anio	  */
	public void setanio (String anio)
	{
		set_Value (COLUMNNAME_anio, anio);
	}

	/** Get anio.
		@return anio	  */
	public String getanio () 
	{
		return (String)get_Value(COLUMNNAME_anio);
	}

	/** Set Arrastre.
		@param Arrastre Arrastre	  */
	public void setArrastre (BigDecimal Arrastre)
	{
		set_Value (COLUMNNAME_Arrastre, Arrastre);
	}

	/** Get Arrastre.
		@return Arrastre	  */
	public BigDecimal getArrastre () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Arrastre);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set C_BPartner_ID_Aux.
		@param C_BPartner_ID_Aux C_BPartner_ID_Aux	  */
	public void setC_BPartner_ID_Aux (int C_BPartner_ID_Aux)
	{
		set_Value (COLUMNNAME_C_BPartner_ID_Aux, Integer.valueOf(C_BPartner_ID_Aux));
	}

	/** Get C_BPartner_ID_Aux.
		@return C_BPartner_ID_Aux	  */
	public int getC_BPartner_ID_Aux () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID_Aux);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner_I() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID_P(), get_TrxName());	}

	/** Set C_BPartner_ID_P.
		@param C_BPartner_ID_P C_BPartner_ID_P	  */
	public void setC_BPartner_ID_P (int C_BPartner_ID_P)
	{
		set_Value (COLUMNNAME_C_BPartner_ID_P, Integer.valueOf(C_BPartner_ID_P));
	}

	/** Get C_BPartner_ID_P.
		@return C_BPartner_ID_P	  */
	public int getC_BPartner_ID_P () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID_P);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ChasisNo.
		@param ChasisNo ChasisNo	  */
	public void setChasisNo (String ChasisNo)
	{
		set_Value (COLUMNNAME_ChasisNo, ChasisNo);
	}

	/** Get ChasisNo.
		@return ChasisNo	  */
	public String getChasisNo () 
	{
		return (String)get_Value(COLUMNNAME_ChasisNo);
	}

	/** Set DateAction.
		@param DateAction DateAction	  */
	public void setDateAction (Timestamp DateAction)
	{
		set_Value (COLUMNNAME_DateAction, DateAction);
	}

	/** Get DateAction.
		@return DateAction	  */
	public Timestamp getDateAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAction);
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
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set DueDate2.
		@param DueDate2 DueDate2	  */
	public void setDueDate2 (Timestamp DueDate2)
	{
		set_Value (COLUMNNAME_DueDate2, DueDate2);
	}

	/** Get DueDate2.
		@return DueDate2	  */
	public Timestamp getDueDate2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate2);
	}

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set Height.
		@param Height Height	  */
	public void setHeight (BigDecimal Height)
	{
		set_Value (COLUMNNAME_Height, Height);
	}

	/** Get Height.
		@return Height	  */
	public BigDecimal getHeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Height);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set IsPledged.
		@param IsPledged IsPledged	  */
	public void setIsPledged (boolean IsPledged)
	{
		set_Value (COLUMNNAME_IsPledged, Boolean.valueOf(IsPledged));
	}

	/** Get IsPledged.
		@return IsPledged	  */
	public boolean isPledged () 
	{
		Object oo = get_Value(COLUMNNAME_IsPledged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Purchased.
		@param IsPurchased 
		Organization purchases this product
	  */
	public void setIsPurchased (boolean IsPurchased)
	{
		set_Value (COLUMNNAME_IsPurchased, Boolean.valueOf(IsPurchased));
	}

	/** Get Purchased.
		@return Organization purchases this product
	  */
	public boolean isPurchased () 
	{
		Object oo = get_Value(COLUMNNAME_IsPurchased);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sold.
		@param IsSold 
		Organization sells this product
	  */
	public void setIsSold (boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	/** Get Sold.
		@return Organization sells this product
	  */
	public boolean isSold () 
	{
		Object oo = get_Value(COLUMNNAME_IsSold);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MotorNo.
		@param MotorNo MotorNo	  */
	public void setMotorNo (String MotorNo)
	{
		set_Value (COLUMNNAME_MotorNo, MotorNo);
	}

	/** Get MotorNo.
		@return MotorNo	  */
	public String getMotorNo () 
	{
		return (String)get_Value(COLUMNNAME_MotorNo);
	}

	/** Set MTOPAdhesiveNo.
		@param MTOPAdhesiveNo MTOPAdhesiveNo	  */
	public void setMTOPAdhesiveNo (String MTOPAdhesiveNo)
	{
		set_Value (COLUMNNAME_MTOPAdhesiveNo, MTOPAdhesiveNo);
	}

	/** Get MTOPAdhesiveNo.
		@return MTOPAdhesiveNo	  */
	public String getMTOPAdhesiveNo () 
	{
		return (String)get_Value(COLUMNNAME_MTOPAdhesiveNo);
	}

	/** Set MTOPDate.
		@param MTOPDate MTOPDate	  */
	public void setMTOPDate (Timestamp MTOPDate)
	{
		set_Value (COLUMNNAME_MTOPDate, MTOPDate);
	}

	/** Get MTOPDate.
		@return MTOPDate	  */
	public Timestamp getMTOPDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MTOPDate);
	}

	/** Set MTOPNo.
		@param MTOPNo MTOPNo	  */
	public void setMTOPNo (String MTOPNo)
	{
		set_Value (COLUMNNAME_MTOPNo, MTOPNo);
	}

	/** Get MTOPNo.
		@return MTOPNo	  */
	public String getMTOPNo () 
	{
		return (String)get_Value(COLUMNNAME_MTOPNo);
	}

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	/** Set Observaciones2.
		@param Observaciones2 Observaciones2	  */
	public void setObservaciones2 (String Observaciones2)
	{
		set_Value (COLUMNNAME_Observaciones2, Observaciones2);
	}

	/** Get Observaciones2.
		@return Observaciones2	  */
	public String getObservaciones2 () 
	{
		return (String)get_Value(COLUMNNAME_Observaciones2);
	}

	/** Set PadronNo.
		@param PadronNo PadronNo	  */
	public void setPadronNo (String PadronNo)
	{
		set_Value (COLUMNNAME_PadronNo, PadronNo);
	}

	/** Get PadronNo.
		@return PadronNo	  */
	public String getPadronNo () 
	{
		return (String)get_Value(COLUMNNAME_PadronNo);
	}

	/** Set Permisa.
		@param Permisa Permisa	  */
	public void setPermisa (String Permisa)
	{
		set_Value (COLUMNNAME_Permisa, Permisa);
	}

	/** Get Permisa.
		@return Permisa	  */
	public String getPermisa () 
	{
		return (String)get_Value(COLUMNNAME_Permisa);
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

	/** Set Pic2_ID.
		@param Pic2_ID Pic2_ID	  */
	public void setPic2_ID (int Pic2_ID)
	{
		if (Pic2_ID < 1) 
			set_Value (COLUMNNAME_Pic2_ID, null);
		else 
			set_Value (COLUMNNAME_Pic2_ID, Integer.valueOf(Pic2_ID));
	}

	/** Get Pic2_ID.
		@return Pic2_ID	  */
	public int getPic2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pic2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pic3_ID.
		@param Pic3_ID Pic3_ID	  */
	public void setPic3_ID (int Pic3_ID)
	{
		if (Pic3_ID < 1) 
			set_Value (COLUMNNAME_Pic3_ID, null);
		else 
			set_Value (COLUMNNAME_Pic3_ID, Integer.valueOf(Pic3_ID));
	}

	/** Get Pic3_ID.
		@return Pic3_ID	  */
	public int getPic3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pic3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PicPlate_ID.
		@param PicPlate_ID PicPlate_ID	  */
	public void setPicPlate_ID (int PicPlate_ID)
	{
		if (PicPlate_ID < 1) 
			set_Value (COLUMNNAME_PicPlate_ID, null);
		else 
			set_Value (COLUMNNAME_PicPlate_ID, Integer.valueOf(PicPlate_ID));
	}

	/** Get PicPlate_ID.
		@return PicPlate_ID	  */
	public int getPicPlate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PicPlate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PurchaseDate.
		@param PurchaseDate 
		PurchaseDate
	  */
	public void setPurchaseDate (Timestamp PurchaseDate)
	{
		set_Value (COLUMNNAME_PurchaseDate, PurchaseDate);
	}

	/** Get PurchaseDate.
		@return PurchaseDate
	  */
	public Timestamp getPurchaseDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PurchaseDate);
	}

	/** PurchaseType AD_Reference_ID=1000380 */
	public static final int PURCHASETYPE_AD_Reference_ID=1000380;
	/** COMPRA = COMPRA */
	public static final String PURCHASETYPE_COMPRA = "COMPRA";
	/** LEASING = LEASING */
	public static final String PURCHASETYPE_LEASING = "LEASING";
	/** Set PurchaseType.
		@param PurchaseType PurchaseType	  */
	public void setPurchaseType (String PurchaseType)
	{

		set_Value (COLUMNNAME_PurchaseType, PurchaseType);
	}

	/** Get PurchaseType.
		@return PurchaseType	  */
	public String getPurchaseType () 
	{
		return (String)get_Value(COLUMNNAME_PurchaseType);
	}

	/** Set QtyKm.
		@param QtyKm 
		QtyKm
	  */
	public void setQtyKm (int QtyKm)
	{
		set_Value (COLUMNNAME_QtyKm, Integer.valueOf(QtyKm));
	}

	/** Get QtyKm.
		@return QtyKm
	  */
	public int getQtyKm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyKmHubo.
		@param QtyKmHubo QtyKmHubo	  */
	public void setQtyKmHubo (int QtyKmHubo)
	{
		set_Value (COLUMNNAME_QtyKmHubo, Integer.valueOf(QtyKmHubo));
	}

	/** Get QtyKmHubo.
		@return QtyKmHubo	  */
	public int getQtyKmHubo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyKmHubo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** RailType AD_Reference_ID=1000379 */
	public static final int RAILTYPE_AD_Reference_ID=1000379;
	/** UNO = UNO */
	public static final String RAILTYPE_UNO = "UNO";
	/** DOS = DOS */
	public static final String RAILTYPE_DOS = "DOS";
	/** Set RailType.
		@param RailType RailType	  */
	public void setRailType (String RailType)
	{

		set_Value (COLUMNNAME_RailType, RailType);
	}

	/** Get RailType.
		@return RailType	  */
	public String getRailType () 
	{
		return (String)get_Value(COLUMNNAME_RailType);
	}

	/** Set SoldDate.
		@param SoldDate SoldDate	  */
	public void setSoldDate (Timestamp SoldDate)
	{
		set_Value (COLUMNNAME_SoldDate, SoldDate);
	}

	/** Get SoldDate.
		@return SoldDate	  */
	public Timestamp getSoldDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SoldDate);
	}

	/** Set SuctaDate.
		@param SuctaDate SuctaDate	  */
	public void setSuctaDate (Timestamp SuctaDate)
	{
		set_Value (COLUMNNAME_SuctaDate, SuctaDate);
	}

	/** Get SuctaDate.
		@return SuctaDate	  */
	public Timestamp getSuctaDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SuctaDate);
	}

	/** Set SuctaDesc.
		@param SuctaDesc SuctaDesc	  */
	public void setSuctaDesc (String SuctaDesc)
	{
		set_Value (COLUMNNAME_SuctaDesc, SuctaDesc);
	}

	/** Get SuctaDesc.
		@return SuctaDesc	  */
	public String getSuctaDesc () 
	{
		return (String)get_Value(COLUMNNAME_SuctaDesc);
	}

	/** Set SuctaNo.
		@param SuctaNo SuctaNo	  */
	public void setSuctaNo (String SuctaNo)
	{
		set_Value (COLUMNNAME_SuctaNo, SuctaNo);
	}

	/** Get SuctaNo.
		@return SuctaNo	  */
	public String getSuctaNo () 
	{
		return (String)get_Value(COLUMNNAME_SuctaNo);
	}

	/** Set TacografoNo.
		@param TacografoNo TacografoNo	  */
	public void setTacografoNo (String TacografoNo)
	{
		set_Value (COLUMNNAME_TacografoNo, TacografoNo);
	}

	/** Get TacografoNo.
		@return TacografoNo	  */
	public String getTacografoNo () 
	{
		return (String)get_Value(COLUMNNAME_TacografoNo);
	}

	/** Set Tara.
		@param Tara Tara	  */
	public void setTara (BigDecimal Tara)
	{
		set_Value (COLUMNNAME_Tara, Tara);
	}

	/** Get Tara.
		@return Tara	  */
	public BigDecimal getTara () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tara);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TermometroNo.
		@param TermometroNo TermometroNo	  */
	public void setTermometroNo (String TermometroNo)
	{
		set_Value (COLUMNNAME_TermometroNo, TermometroNo);
	}

	/** Get TermometroNo.
		@return TermometroNo	  */
	public String getTermometroNo () 
	{
		return (String)get_Value(COLUMNNAME_TermometroNo);
	}

	/** TruckMode AD_Reference_ID=1000381 */
	public static final int TRUCKMODE_AD_Reference_ID=1000381;
	/** NACIONAL = NACIONAL */
	public static final String TRUCKMODE_NACIONAL = "NACIONAL";
	/** INTERNACIONAL = INTERNACIONAL */
	public static final String TRUCKMODE_INTERNACIONAL = "INTERNACIONAL";
	/** Set TruckMode.
		@param TruckMode TruckMode	  */
	public void setTruckMode (String TruckMode)
	{

		set_Value (COLUMNNAME_TruckMode, TruckMode);
	}

	/** Get TruckMode.
		@return TruckMode	  */
	public String getTruckMode () 
	{
		return (String)get_Value(COLUMNNAME_TruckMode);
	}

	/** Set TruckNo.
		@param TruckNo 
		TruckNo
	  */
	public void setTruckNo (String TruckNo)
	{
		set_ValueNoCheck (COLUMNNAME_TruckNo, TruckNo);
	}

	/** Get TruckNo.
		@return TruckNo
	  */
	public String getTruckNo () 
	{
		return (String)get_Value(COLUMNNAME_TruckNo);
	}

	public I_UY_TR_Driver getUY_TR_Driver() throws RuntimeException
    {
		return (I_UY_TR_Driver)MTable.get(getCtx(), I_UY_TR_Driver.Table_Name)
			.getPO(getUY_TR_Driver_ID(), get_TrxName());	}

	/** Set UY_TR_Driver.
		@param UY_TR_Driver_ID UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID)
	{
		if (UY_TR_Driver_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Driver_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Driver_ID, Integer.valueOf(UY_TR_Driver_ID));
	}

	/** Get UY_TR_Driver.
		@return UY_TR_Driver	  */
	public int getUY_TR_Driver_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Driver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Driver_ID_2.
		@param UY_TR_Driver_ID_2 UY_TR_Driver_ID_2	  */
	public void setUY_TR_Driver_ID_2 (int UY_TR_Driver_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_Driver_ID_2, Integer.valueOf(UY_TR_Driver_ID_2));
	}

	/** Get UY_TR_Driver_ID_2.
		@return UY_TR_Driver_ID_2	  */
	public int getUY_TR_Driver_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Driver_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Mark getUY_TR_Mark() throws RuntimeException
    {
		return (I_UY_TR_Mark)MTable.get(getCtx(), I_UY_TR_Mark.Table_Name)
			.getPO(getUY_TR_Mark_ID(), get_TrxName());	}

	/** Set UY_TR_Mark.
		@param UY_TR_Mark_ID UY_TR_Mark	  */
	public void setUY_TR_Mark_ID (int UY_TR_Mark_ID)
	{
		if (UY_TR_Mark_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Mark_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Mark_ID, Integer.valueOf(UY_TR_Mark_ID));
	}

	/** Get UY_TR_Mark.
		@return UY_TR_Mark	  */
	public int getUY_TR_Mark_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Mark_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_MarkModel getUY_TR_MarkModel() throws RuntimeException
    {
		return (I_UY_TR_MarkModel)MTable.get(getCtx(), I_UY_TR_MarkModel.Table_Name)
			.getPO(getUY_TR_MarkModel_ID(), get_TrxName());	}

	/** Set UY_TR_MarkModel.
		@param UY_TR_MarkModel_ID UY_TR_MarkModel	  */
	public void setUY_TR_MarkModel_ID (int UY_TR_MarkModel_ID)
	{
		if (UY_TR_MarkModel_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_MarkModel_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_MarkModel_ID, Integer.valueOf(UY_TR_MarkModel_ID));
	}

	/** Get UY_TR_MarkModel.
		@return UY_TR_MarkModel	  */
	public int getUY_TR_MarkModel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_MarkModel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck.
		@param UY_TR_Truck_ID UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID)
	{
		if (UY_TR_Truck_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Truck_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_Truck_ID, Integer.valueOf(UY_TR_Truck_ID));
	}

	/** Get UY_TR_Truck.
		@return UY_TR_Truck	  */
	public int getUY_TR_Truck_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck_ID_2.
		@param UY_TR_Truck_ID_2 UY_TR_Truck_ID_2	  */
	public void setUY_TR_Truck_ID_2 (int UY_TR_Truck_ID_2)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_2, Integer.valueOf(UY_TR_Truck_ID_2));
	}

	/** Get UY_TR_Truck_ID_2.
		@return UY_TR_Truck_ID_2	  */
	public int getUY_TR_Truck_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Truck_ID_Aux.
		@param UY_TR_Truck_ID_Aux UY_TR_Truck_ID_Aux	  */
	public void setUY_TR_Truck_ID_Aux (int UY_TR_Truck_ID_Aux)
	{
		set_Value (COLUMNNAME_UY_TR_Truck_ID_Aux, Integer.valueOf(UY_TR_Truck_ID_Aux));
	}

	/** Get UY_TR_Truck_ID_Aux.
		@return UY_TR_Truck_ID_Aux	  */
	public int getUY_TR_Truck_ID_Aux () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Truck_ID_Aux);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TruckType getUY_TR_TruckType() throws RuntimeException
    {
		return (I_UY_TR_TruckType)MTable.get(getCtx(), I_UY_TR_TruckType.Table_Name)
			.getPO(getUY_TR_TruckType_ID(), get_TrxName());	}

	/** Set UY_TR_TruckType.
		@param UY_TR_TruckType_ID UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID)
	{
		if (UY_TR_TruckType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TruckType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TruckType_ID, Integer.valueOf(UY_TR_TruckType_ID));
	}

	/** Get UY_TR_TruckType.
		@return UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TruckType_ID);
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
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}