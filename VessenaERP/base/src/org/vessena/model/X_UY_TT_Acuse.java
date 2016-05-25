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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_Acuse
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_Acuse extends PO implements I_UY_TT_Acuse, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140402L;

    /** Standard Constructor */
    public X_UY_TT_Acuse (Properties ctx, int UY_TT_Acuse_ID, String trxName)
    {
      super (ctx, UY_TT_Acuse_ID, trxName);
      /** if (UY_TT_Acuse_ID == 0)
        {
			setAcuseType (null);
// SISTEMA
			setAD_User_ID (0);
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setProcessed (false);
			setUY_DeliveryPoint_ID (0);
			setUY_TT_Acuse_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_Acuse (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_Acuse[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Account No.
		@param AccountNo 
		Account Number
	  */
	public void setAccountNo (String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Account No.
		@return Account Number
	  */
	public String getAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_AccountNo);
	}

	/** AcuseType AD_Reference_ID=1000358 */
	public static final int ACUSETYPE_AD_Reference_ID=1000358;
	/** SISTEMA = SISTEMA */
	public static final String ACUSETYPE_SISTEMA = "SISTEMA";
	/** MANUAL = MANUAL */
	public static final String ACUSETYPE_MANUAL = "MANUAL";
	/** CORREO = CORREO */
	public static final String ACUSETYPE_CORREO = "CORREO";
	/** Set AcuseType.
		@param AcuseType AcuseType	  */
	public void setAcuseType (String AcuseType)
	{

		set_Value (COLUMNNAME_AcuseType, AcuseType);
	}

	/** Get AcuseType.
		@return AcuseType	  */
	public String getAcuseType () 
	{
		return (String)get_Value(COLUMNNAME_AcuseType);
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
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

	/** Set Cedula.
		@param Cedula Cedula	  */
	public void setCedula (String Cedula)
	{
		set_Value (COLUMNNAME_Cedula, Cedula);
	}

	/** Get Cedula.
		@return Cedula	  */
	public String getCedula () 
	{
		return (String)get_Value(COLUMNNAME_Cedula);
	}

	/** Set Cedula2.
		@param Cedula2 
		Cedula2
	  */
	public void setCedula2 (String Cedula2)
	{
		set_Value (COLUMNNAME_Cedula2, Cedula2);
	}

	/** Get Cedula2.
		@return Cedula2
	  */
	public String getCedula2 () 
	{
		return (String)get_Value(COLUMNNAME_Cedula2);
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

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Asigned = AS */
	public static final String DOCSTATUS_Asigned = "AS";
	/** Requested = RQ */
	public static final String DOCSTATUS_Requested = "RQ";
	/** Recived = RV */
	public static final String DOCSTATUS_Recived = "RV";
	/** Picking = PK */
	public static final String DOCSTATUS_Picking = "PK";
	/** Applied = AY */
	public static final String DOCSTATUS_Applied = "AY";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
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

	/** Set Mobile.
		@param Mobile Mobile	  */
	public void setMobile (String Mobile)
	{
		set_Value (COLUMNNAME_Mobile, Mobile);
	}

	/** Get Mobile.
		@return Mobile	  */
	public String getMobile () 
	{
		return (String)get_Value(COLUMNNAME_Mobile);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Name 2.
		@param Name2 
		Additional Name
	  */
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Additional Name
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** Set NroTarjetaTitular.
		@param NroTarjetaTitular NroTarjetaTitular	  */
	public void setNroTarjetaTitular (String NroTarjetaTitular)
	{
		set_Value (COLUMNNAME_NroTarjetaTitular, NroTarjetaTitular);
	}

	/** Get NroTarjetaTitular.
		@return NroTarjetaTitular	  */
	public String getNroTarjetaTitular () 
	{
		return (String)get_Value(COLUMNNAME_NroTarjetaTitular);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
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

	/** Set Telephone.
		@param Telephone Telephone	  */
	public void setTelephone (String Telephone)
	{
		set_Value (COLUMNNAME_Telephone, Telephone);
	}

	/** Get Telephone.
		@return Telephone	  */
	public String getTelephone () 
	{
		return (String)get_Value(COLUMNNAME_Telephone);
	}

	/** TipoSoc AD_Reference_ID=1000344 */
	public static final int TIPOSOC_AD_Reference_ID=1000344;
	/** Titular = 0 */
	public static final String TIPOSOC_Titular = "0";
	/** Adicional = 1 */
	public static final String TIPOSOC_Adicional = "1";
	/** Otro = 2 */
	public static final String TIPOSOC_Otro = "2";
	/** Set TipoSoc.
		@param TipoSoc 
		TipoSoc
	  */
	public void setTipoSoc (String TipoSoc)
	{

		set_Value (COLUMNNAME_TipoSoc, TipoSoc);
	}

	/** Get TipoSoc.
		@return TipoSoc
	  */
	public String getTipoSoc () 
	{
		return (String)get_Value(COLUMNNAME_TipoSoc);
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

	/** Set UY_DeliveryPoint_ID_From.
		@param UY_DeliveryPoint_ID_From UY_DeliveryPoint_ID_From	  */
	public void setUY_DeliveryPoint_ID_From (int UY_DeliveryPoint_ID_From)
	{
		set_Value (COLUMNNAME_UY_DeliveryPoint_ID_From, Integer.valueOf(UY_DeliveryPoint_ID_From));
	}

	/** Get UY_DeliveryPoint_ID_From.
		@return UY_DeliveryPoint_ID_From	  */
	public int getUY_DeliveryPoint_ID_From () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID_From);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_CedulaCuenta getUY_R_CedulaCuenta() throws RuntimeException
    {
		return (I_UY_R_CedulaCuenta)MTable.get(getCtx(), I_UY_R_CedulaCuenta.Table_Name)
			.getPO(getUY_R_CedulaCuenta_ID(), get_TrxName());	}

	/** Set UY_R_CedulaCuenta.
		@param UY_R_CedulaCuenta_ID UY_R_CedulaCuenta	  */
	public void setUY_R_CedulaCuenta_ID (int UY_R_CedulaCuenta_ID)
	{
		if (UY_R_CedulaCuenta_ID < 1) 
			set_Value (COLUMNNAME_UY_R_CedulaCuenta_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_CedulaCuenta_ID, Integer.valueOf(UY_R_CedulaCuenta_ID));
	}

	/** Get UY_R_CedulaCuenta.
		@return UY_R_CedulaCuenta	  */
	public int getUY_R_CedulaCuenta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_CedulaCuenta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_Acuse.
		@param UY_TT_Acuse_ID UY_TT_Acuse	  */
	public void setUY_TT_Acuse_ID (int UY_TT_Acuse_ID)
	{
		if (UY_TT_Acuse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Acuse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_Acuse_ID, Integer.valueOf(UY_TT_Acuse_ID));
	}

	/** Get UY_TT_Acuse.
		@return UY_TT_Acuse	  */
	public int getUY_TT_Acuse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Acuse_ID);
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

	public I_UY_TT_Hand getUY_TT_Hand() throws RuntimeException
    {
		return (I_UY_TT_Hand)MTable.get(getCtx(), I_UY_TT_Hand.Table_Name)
			.getPO(getUY_TT_Hand_ID(), get_TrxName());	}

	/** Set UY_TT_Hand.
		@param UY_TT_Hand_ID UY_TT_Hand	  */
	public void setUY_TT_Hand_ID (int UY_TT_Hand_ID)
	{
		if (UY_TT_Hand_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Hand_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Hand_ID, Integer.valueOf(UY_TT_Hand_ID));
	}

	/** Get UY_TT_Hand.
		@return UY_TT_Hand	  */
	public int getUY_TT_Hand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Hand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Vinculo AD_Reference_ID=1000320 */
	public static final int VINCULO_AD_Reference_ID=1000320;
	/** Conyuge = CONYUGE */
	public static final String VINCULO_Conyuge = "CONYUGE";
	/** Padre = PADRE */
	public static final String VINCULO_Padre = "PADRE";
	/** Madre = MADRE */
	public static final String VINCULO_Madre = "MADRE";
	/** Hermano = HERMANO */
	public static final String VINCULO_Hermano = "HERMANO";
	/** Hermana = HERMANA */
	public static final String VINCULO_Hermana = "HERMANA";
	/** Abuelos = ABUELOS */
	public static final String VINCULO_Abuelos = "ABUELOS";
	/** Tios = TIOS */
	public static final String VINCULO_Tios = "TIOS";
	/** Primos = PRIMOS */
	public static final String VINCULO_Primos = "PRIMOS";
	/** Otros = OTROS */
	public static final String VINCULO_Otros = "OTROS";
	/** Set Vinculo.
		@param Vinculo Vinculo	  */
	public void setVinculo (String Vinculo)
	{

		set_Value (COLUMNNAME_Vinculo, Vinculo);
	}

	/** Get Vinculo.
		@return Vinculo	  */
	public String getVinculo () 
	{
		return (String)get_Value(COLUMNNAME_Vinculo);
	}
}