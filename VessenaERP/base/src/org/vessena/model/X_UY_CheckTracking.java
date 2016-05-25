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

/** Generated Model for UY_CheckTracking
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CheckTracking extends PO implements I_UY_CheckTracking, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CheckTracking (Properties ctx, int UY_CheckTracking_ID, String trxName)
    {
      super (ctx, UY_CheckTracking_ID, trxName);
      /** if (UY_CheckTracking_ID == 0)
        {
			setAD_Table_ID (0);
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setRecord_ID (0);
			setUY_CheckTracking_ID (0);
			setUY_MediosPago_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CheckTracking (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CheckTracking[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CheckOldStatus AD_Reference_ID=1000011 */
	public static final int CHECKOLDSTATUS_AD_Reference_ID=1000011;
	/** Emitido = EMI */
	public static final String CHECKOLDSTATUS_Emitido = "EMI";
	/** Entregado = ENT */
	public static final String CHECKOLDSTATUS_Entregado = "ENT";
	/** Rechazado = REC */
	public static final String CHECKOLDSTATUS_Rechazado = "REC";
	/** Conciliado = CON */
	public static final String CHECKOLDSTATUS_Conciliado = "CON";
	/** Cartera = CAR */
	public static final String CHECKOLDSTATUS_Cartera = "CAR";
	/** Depositado = DEP */
	public static final String CHECKOLDSTATUS_Depositado = "DEP";
	/** Transferido = TRA */
	public static final String CHECKOLDSTATUS_Transferido = "TRA";
	/** Descontado = DES */
	public static final String CHECKOLDSTATUS_Descontado = "DES";
	/** Pago = PAG */
	public static final String CHECKOLDSTATUS_Pago = "PAG";
	/** Cambiado = CAM */
	public static final String CHECKOLDSTATUS_Cambiado = "CAM";
	/** Cobrado = COB */
	public static final String CHECKOLDSTATUS_Cobrado = "COB";
	/** Set CheckOldStatus.
		@param CheckOldStatus CheckOldStatus	  */
	public void setCheckOldStatus (String CheckOldStatus)
	{

		set_Value (COLUMNNAME_CheckOldStatus, CheckOldStatus);
	}

	/** Get CheckOldStatus.
		@return CheckOldStatus	  */
	public String getCheckOldStatus () 
	{
		return (String)get_Value(COLUMNNAME_CheckOldStatus);
	}

	/** CheckStatus AD_Reference_ID=1000011 */
	public static final int CHECKSTATUS_AD_Reference_ID=1000011;
	/** Emitido = EMI */
	public static final String CHECKSTATUS_Emitido = "EMI";
	/** Entregado = ENT */
	public static final String CHECKSTATUS_Entregado = "ENT";
	/** Rechazado = REC */
	public static final String CHECKSTATUS_Rechazado = "REC";
	/** Conciliado = CON */
	public static final String CHECKSTATUS_Conciliado = "CON";
	/** Cartera = CAR */
	public static final String CHECKSTATUS_Cartera = "CAR";
	/** Depositado = DEP */
	public static final String CHECKSTATUS_Depositado = "DEP";
	/** Transferido = TRA */
	public static final String CHECKSTATUS_Transferido = "TRA";
	/** Descontado = DES */
	public static final String CHECKSTATUS_Descontado = "DES";
	/** Pago = PAG */
	public static final String CHECKSTATUS_Pago = "PAG";
	/** Cambiado = CAM */
	public static final String CHECKSTATUS_Cambiado = "CAM";
	/** Cobrado = COB */
	public static final String CHECKSTATUS_Cobrado = "COB";
	/** Set CheckStatus.
		@param CheckStatus CheckStatus	  */
	public void setCheckStatus (String CheckStatus)
	{

		set_Value (COLUMNNAME_CheckStatus, CheckStatus);
	}

	/** Get CheckStatus.
		@return CheckStatus	  */
	public String getCheckStatus () 
	{
		return (String)get_Value(COLUMNNAME_CheckStatus);
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Pick = PK */
	public static final String DOCACTION_Pick = "PK";
	/** Asign = AS */
	public static final String DOCACTION_Asign = "AS";
	/** Request = RQ */
	public static final String DOCACTION_Request = "RQ";
	/** Recive = RV */
	public static final String DOCACTION_Recive = "RV";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CheckTracking.
		@param UY_CheckTracking_ID UY_CheckTracking	  */
	public void setUY_CheckTracking_ID (int UY_CheckTracking_ID)
	{
		if (UY_CheckTracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CheckTracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CheckTracking_ID, Integer.valueOf(UY_CheckTracking_ID));
	}

	/** Get UY_CheckTracking.
		@return UY_CheckTracking	  */
	public int getUY_CheckTracking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckTracking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException
    {
		return (I_UY_MediosPago)MTable.get(getCtx(), I_UY_MediosPago.Table_Name)
			.getPO(getUY_MediosPago_ID(), get_TrxName());	}

	/** Set Medios de Pago.
		@param UY_MediosPago_ID Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID)
	{
		if (UY_MediosPago_ID < 1) 
			set_Value (COLUMNNAME_UY_MediosPago_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MediosPago_ID, Integer.valueOf(UY_MediosPago_ID));
	}

	/** Get Medios de Pago.
		@return Medios de Pago	  */
	public int getUY_MediosPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MediosPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}