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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_TR_MicCont
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_MicCont extends PO implements I_UY_TR_MicCont, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141126L;

    /** Standard Constructor */
    public X_UY_TR_MicCont (Properties ctx, int UY_TR_MicCont_ID, String trxName)
    {
      super (ctx, UY_TR_MicCont_ID, trxName);
      /** if (UY_TR_MicCont_ID == 0)
        {
			setUY_TR_MicCont_ID (0);
			setUY_TR_Mic_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_MicCont (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_MicCont[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AcumuladoBultos.
		@param AcumuladoBultos AcumuladoBultos	  */
	public void setAcumuladoBultos (BigDecimal AcumuladoBultos)
	{
		set_Value (COLUMNNAME_AcumuladoBultos, AcumuladoBultos);
	}

	/** Get AcumuladoBultos.
		@return AcumuladoBultos	  */
	public BigDecimal getAcumuladoBultos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AcumuladoBultos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AcumuladoPeso.
		@param AcumuladoPeso AcumuladoPeso	  */
	public void setAcumuladoPeso (BigDecimal AcumuladoPeso)
	{
		set_Value (COLUMNNAME_AcumuladoPeso, AcumuladoPeso);
	}

	/** Get AcumuladoPeso.
		@return AcumuladoPeso	  */
	public BigDecimal getAcumuladoPeso () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AcumuladoPeso);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount2.
		@param Amount2 Amount2	  */
	public void setAmount2 (BigDecimal Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Amount2);
	}

	/** Get Amount2.
		@return Amount2	  */
	public BigDecimal getAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set C_Currency2_ID.
		@param C_Currency2_ID C_Currency2_ID	  */
	public void setC_Currency2_ID (int C_Currency2_ID)
	{
		if (C_Currency2_ID < 1) 
			set_Value (COLUMNNAME_C_Currency2_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency2_ID, Integer.valueOf(C_Currency2_ID));
	}

	/** Get C_Currency2_ID.
		@return C_Currency2_ID	  */
	public int getC_Currency2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Consignatario.
		@param Consignatario Consignatario	  */
	public void setConsignatario (String Consignatario)
	{
		set_Value (COLUMNNAME_Consignatario, Consignatario);
	}

	/** Get Consignatario.
		@return Consignatario	  */
	public String getConsignatario () 
	{
		return (String)get_Value(COLUMNNAME_Consignatario);
	}

	/** Set Consignatario2.
		@param Consignatario2 Consignatario2	  */
	public void setConsignatario2 (String Consignatario2)
	{
		set_Value (COLUMNNAME_Consignatario2, Consignatario2);
	}

	/** Get Consignatario2.
		@return Consignatario2	  */
	public String getConsignatario2 () 
	{
		return (String)get_Value(COLUMNNAME_Consignatario2);
	}

	/** Set CrtImgNum1.
		@param CrtImgNum1 CrtImgNum1	  */
	public void setCrtImgNum1 (String CrtImgNum1)
	{
		set_Value (COLUMNNAME_CrtImgNum1, CrtImgNum1);
	}

	/** Get CrtImgNum1.
		@return CrtImgNum1	  */
	public String getCrtImgNum1 () 
	{
		return (String)get_Value(COLUMNNAME_CrtImgNum1);
	}

	/** Set CrtImgNum2.
		@param CrtImgNum2 CrtImgNum2	  */
	public void setCrtImgNum2 (String CrtImgNum2)
	{
		set_Value (COLUMNNAME_CrtImgNum2, CrtImgNum2);
	}

	/** Get CrtImgNum2.
		@return CrtImgNum2	  */
	public String getCrtImgNum2 () 
	{
		return (String)get_Value(COLUMNNAME_CrtImgNum2);
	}

	/** CrtImgStatus1 AD_Reference_ID=1000445 */
	public static final int CRTIMGSTATUS1_AD_Reference_ID=1000445;
	/** SINCARGAR = SINCARGAR */
	public static final String CRTIMGSTATUS1_SINCARGAR = "SINCARGAR";
	/** CARGADO = CARGADO */
	public static final String CRTIMGSTATUS1_CARGADO = "CARGADO";
	/** Set CrtImgStatus1.
		@param CrtImgStatus1 CrtImgStatus1	  */
	public void setCrtImgStatus1 (String CrtImgStatus1)
	{

		set_Value (COLUMNNAME_CrtImgStatus1, CrtImgStatus1);
	}

	/** Get CrtImgStatus1.
		@return CrtImgStatus1	  */
	public String getCrtImgStatus1 () 
	{
		return (String)get_Value(COLUMNNAME_CrtImgStatus1);
	}

	/** CrtImgStatus2 AD_Reference_ID=1000445 */
	public static final int CRTIMGSTATUS2_AD_Reference_ID=1000445;
	/** SINCARGAR = SINCARGAR */
	public static final String CRTIMGSTATUS2_SINCARGAR = "SINCARGAR";
	/** CARGADO = CARGADO */
	public static final String CRTIMGSTATUS2_CARGADO = "CARGADO";
	/** Set CrtImgStatus2.
		@param CrtImgStatus2 CrtImgStatus2	  */
	public void setCrtImgStatus2 (String CrtImgStatus2)
	{

		set_Value (COLUMNNAME_CrtImgStatus2, CrtImgStatus2);
	}

	/** Get CrtImgStatus2.
		@return CrtImgStatus2	  */
	public String getCrtImgStatus2 () 
	{
		return (String)get_Value(COLUMNNAME_CrtImgStatus2);
	}

	/** CrtLineStatus1 AD_Reference_ID=1000446 */
	public static final int CRTLINESTATUS1_AD_Reference_ID=1000446;
	/** SINCARGAR = SINCARGAR */
	public static final String CRTLINESTATUS1_SINCARGAR = "SINCARGAR";
	/** CARGADO = CARGADO */
	public static final String CRTLINESTATUS1_CARGADO = "CARGADO";
	/** Set CrtLineStatus1.
		@param CrtLineStatus1 CrtLineStatus1	  */
	public void setCrtLineStatus1 (String CrtLineStatus1)
	{

		set_Value (COLUMNNAME_CrtLineStatus1, CrtLineStatus1);
	}

	/** Get CrtLineStatus1.
		@return CrtLineStatus1	  */
	public String getCrtLineStatus1 () 
	{
		return (String)get_Value(COLUMNNAME_CrtLineStatus1);
	}

	/** CrtLineStatus2 AD_Reference_ID=1000446 */
	public static final int CRTLINESTATUS2_AD_Reference_ID=1000446;
	/** SINCARGAR = SINCARGAR */
	public static final String CRTLINESTATUS2_SINCARGAR = "SINCARGAR";
	/** CARGADO = CARGADO */
	public static final String CRTLINESTATUS2_CARGADO = "CARGADO";
	/** Set CrtLineStatus2.
		@param CrtLineStatus2 CrtLineStatus2	  */
	public void setCrtLineStatus2 (String CrtLineStatus2)
	{

		set_Value (COLUMNNAME_CrtLineStatus2, CrtLineStatus2);
	}

	/** Get CrtLineStatus2.
		@return CrtLineStatus2	  */
	public String getCrtLineStatus2 () 
	{
		return (String)get_Value(COLUMNNAME_CrtLineStatus2);
	}

	/** CrtStatus1 AD_Reference_ID=1000444 */
	public static final int CRTSTATUS1_AD_Reference_ID=1000444;
	/** VINCULADO = VINCULADO */
	public static final String CRTSTATUS1_VINCULADO = "VINCULADO";
	/** ENALTA = ENALTA */
	public static final String CRTSTATUS1_ENALTA = "ENALTA";
	/** ENMODIFICACION = ENMODIFICACION */
	public static final String CRTSTATUS1_ENMODIFICACION = "ENMODIFICACION";
	/** ENBAJA = ENBAJA */
	public static final String CRTSTATUS1_ENBAJA = "ENBAJA";
	/** DESVINCULADO = DESVINCULADO */
	public static final String CRTSTATUS1_DESVINCULADO = "DESVINCULADO";
	/** Set CrtStatus1.
		@param CrtStatus1 CrtStatus1	  */
	public void setCrtStatus1 (String CrtStatus1)
	{

		set_Value (COLUMNNAME_CrtStatus1, CrtStatus1);
	}

	/** Get CrtStatus1.
		@return CrtStatus1	  */
	public String getCrtStatus1 () 
	{
		return (String)get_Value(COLUMNNAME_CrtStatus1);
	}

	/** CrtStatus2 AD_Reference_ID=1000444 */
	public static final int CRTSTATUS2_AD_Reference_ID=1000444;
	/** VINCULADO = VINCULADO */
	public static final String CRTSTATUS2_VINCULADO = "VINCULADO";
	/** ENALTA = ENALTA */
	public static final String CRTSTATUS2_ENALTA = "ENALTA";
	/** ENMODIFICACION = ENMODIFICACION */
	public static final String CRTSTATUS2_ENMODIFICACION = "ENMODIFICACION";
	/** ENBAJA = ENBAJA */
	public static final String CRTSTATUS2_ENBAJA = "ENBAJA";
	/** DESVINCULADO = DESVINCULADO */
	public static final String CRTSTATUS2_DESVINCULADO = "DESVINCULADO";
	/** Set CrtStatus2.
		@param CrtStatus2 CrtStatus2	  */
	public void setCrtStatus2 (String CrtStatus2)
	{

		set_Value (COLUMNNAME_CrtStatus2, CrtStatus2);
	}

	/** Get CrtStatus2.
		@return CrtStatus2	  */
	public String getCrtStatus2 () 
	{
		return (String)get_Value(COLUMNNAME_CrtStatus2);
	}

	/** Set descripcion.
		@param descripcion descripcion	  */
	public void setdescripcion (String descripcion)
	{
		set_Value (COLUMNNAME_descripcion, descripcion);
	}

	/** Get descripcion.
		@return descripcion	  */
	public String getdescripcion () 
	{
		return (String)get_Value(COLUMNNAME_descripcion);
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

	/** Set Destinatario.
		@param Destinatario Destinatario	  */
	public void setDestinatario (String Destinatario)
	{
		set_Value (COLUMNNAME_Destinatario, Destinatario);
	}

	/** Get Destinatario.
		@return Destinatario	  */
	public String getDestinatario () 
	{
		return (String)get_Value(COLUMNNAME_Destinatario);
	}

	/** Set Destinatario2.
		@param Destinatario2 Destinatario2	  */
	public void setDestinatario2 (String Destinatario2)
	{
		set_Value (COLUMNNAME_Destinatario2, Destinatario2);
	}

	/** Get Destinatario2.
		@return Destinatario2	  */
	public String getDestinatario2 () 
	{
		return (String)get_Value(COLUMNNAME_Destinatario2);
	}

	/** Set Importe.
		@param Importe Importe	  */
	public void setImporte (BigDecimal Importe)
	{
		set_Value (COLUMNNAME_Importe, Importe);
	}

	/** Get Importe.
		@return Importe	  */
	public BigDecimal getImporte () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Importe);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Importe2.
		@param Importe2 Importe2	  */
	public void setImporte2 (BigDecimal Importe2)
	{
		set_Value (COLUMNNAME_Importe2, Importe2);
	}

	/** Get Importe2.
		@return Importe2	  */
	public BigDecimal getImporte2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Importe2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Location comment.
		@param LocationComment 
		Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment)
	{
		set_Value (COLUMNNAME_LocationComment, LocationComment);
	}

	/** Get Location comment.
		@return Additional comments or remarks concerning the location
	  */
	public String getLocationComment () 
	{
		return (String)get_Value(COLUMNNAME_LocationComment);
	}

	/** Set LocationComment2.
		@param LocationComment2 LocationComment2	  */
	public void setLocationComment2 (String LocationComment2)
	{
		set_Value (COLUMNNAME_LocationComment2, LocationComment2);
	}

	/** Get LocationComment2.
		@return LocationComment2	  */
	public String getLocationComment2 () 
	{
		return (String)get_Value(COLUMNNAME_LocationComment2);
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

	/** Set Observaciones3.
		@param Observaciones3 Observaciones3	  */
	public void setObservaciones3 (String Observaciones3)
	{
		set_Value (COLUMNNAME_Observaciones3, Observaciones3);
	}

	/** Get Observaciones3.
		@return Observaciones3	  */
	public String getObservaciones3 () 
	{
		return (String)get_Value(COLUMNNAME_Observaciones3);
	}

	/** Set Observaciones4.
		@param Observaciones4 Observaciones4	  */
	public void setObservaciones4 (String Observaciones4)
	{
		set_Value (COLUMNNAME_Observaciones4, Observaciones4);
	}

	/** Get Observaciones4.
		@return Observaciones4	  */
	public String getObservaciones4 () 
	{
		return (String)get_Value(COLUMNNAME_Observaciones4);
	}

	/** Set pesoBruto.
		@param pesoBruto pesoBruto	  */
	public void setpesoBruto (BigDecimal pesoBruto)
	{
		set_Value (COLUMNNAME_pesoBruto, pesoBruto);
	}

	/** Get pesoBruto.
		@return pesoBruto	  */
	public BigDecimal getpesoBruto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoBruto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set pesoBruto2.
		@param pesoBruto2 pesoBruto2	  */
	public void setpesoBruto2 (BigDecimal pesoBruto2)
	{
		set_Value (COLUMNNAME_pesoBruto2, pesoBruto2);
	}

	/** Get pesoBruto2.
		@return pesoBruto2	  */
	public BigDecimal getpesoBruto2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoBruto2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set pesoNeto.
		@param pesoNeto pesoNeto	  */
	public void setpesoNeto (BigDecimal pesoNeto)
	{
		set_Value (COLUMNNAME_pesoNeto, pesoNeto);
	}

	/** Get pesoNeto.
		@return pesoNeto	  */
	public BigDecimal getpesoNeto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoNeto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set pesoNeto2.
		@param pesoNeto2 pesoNeto2	  */
	public void setpesoNeto2 (BigDecimal pesoNeto2)
	{
		set_Value (COLUMNNAME_pesoNeto2, pesoNeto2);
	}

	/** Get pesoNeto2.
		@return pesoNeto2	  */
	public BigDecimal getpesoNeto2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_pesoNeto2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set precinto.
		@param precinto precinto	  */
	public void setprecinto (String precinto)
	{
		set_Value (COLUMNNAME_precinto, precinto);
	}

	/** Get precinto.
		@return precinto	  */
	public String getprecinto () 
	{
		return (String)get_Value(COLUMNNAME_precinto);
	}

	/** Set Precinto2.
		@param Precinto2 Precinto2	  */
	public void setPrecinto2 (String Precinto2)
	{
		set_Value (COLUMNNAME_Precinto2, Precinto2);
	}

	/** Get Precinto2.
		@return Precinto2	  */
	public String getPrecinto2 () 
	{
		return (String)get_Value(COLUMNNAME_Precinto2);
	}

	/** Set QtyPackage.
		@param QtyPackage QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage)
	{
		set_Value (COLUMNNAME_QtyPackage, QtyPackage);
	}

	/** Get QtyPackage.
		@return QtyPackage	  */
	public BigDecimal getQtyPackage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPackage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyPackage2.
		@param QtyPackage2 QtyPackage2	  */
	public void setQtyPackage2 (BigDecimal QtyPackage2)
	{
		set_Value (COLUMNNAME_QtyPackage2, QtyPackage2);
	}

	/** Get QtyPackage2.
		@return QtyPackage2	  */
	public BigDecimal getQtyPackage2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPackage2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Remitente.
		@param Remitente Remitente	  */
	public void setRemitente (String Remitente)
	{
		set_Value (COLUMNNAME_Remitente, Remitente);
	}

	/** Get Remitente.
		@return Remitente	  */
	public String getRemitente () 
	{
		return (String)get_Value(COLUMNNAME_Remitente);
	}

	/** Set Remitente2.
		@param Remitente2 Remitente2	  */
	public void setRemitente2 (String Remitente2)
	{
		set_Value (COLUMNNAME_Remitente2, Remitente2);
	}

	/** Get Remitente2.
		@return Remitente2	  */
	public String getRemitente2 () 
	{
		return (String)get_Value(COLUMNNAME_Remitente2);
	}

	/** Set SecNoCrt.
		@param SecNoCrt SecNoCrt	  */
	public void setSecNoCrt (String SecNoCrt)
	{
		set_Value (COLUMNNAME_SecNoCrt, SecNoCrt);
	}

	/** Get SecNoCrt.
		@return SecNoCrt	  */
	public String getSecNoCrt () 
	{
		return (String)get_Value(COLUMNNAME_SecNoCrt);
	}

	/** Set SecNoCrt2.
		@param SecNoCrt2 SecNoCrt2	  */
	public void setSecNoCrt2 (String SecNoCrt2)
	{
		set_Value (COLUMNNAME_SecNoCrt2, SecNoCrt2);
	}

	/** Get SecNoCrt2.
		@return SecNoCrt2	  */
	public String getSecNoCrt2 () 
	{
		return (String)get_Value(COLUMNNAME_SecNoCrt2);
	}

	/** Set SecNoImg1.
		@param SecNoImg1 SecNoImg1	  */
	public void setSecNoImg1 (String SecNoImg1)
	{
		set_Value (COLUMNNAME_SecNoImg1, SecNoImg1);
	}

	/** Get SecNoImg1.
		@return SecNoImg1	  */
	public String getSecNoImg1 () 
	{
		return (String)get_Value(COLUMNNAME_SecNoImg1);
	}

	/** Set SecNoImg2.
		@param SecNoImg2 SecNoImg2	  */
	public void setSecNoImg2 (String SecNoImg2)
	{
		set_Value (COLUMNNAME_SecNoImg2, SecNoImg2);
	}

	/** Get SecNoImg2.
		@return SecNoImg2	  */
	public String getSecNoImg2 () 
	{
		return (String)get_Value(COLUMNNAME_SecNoImg2);
	}

	/** Set SecNoLine1.
		@param SecNoLine1 SecNoLine1	  */
	public void setSecNoLine1 (String SecNoLine1)
	{
		set_Value (COLUMNNAME_SecNoLine1, SecNoLine1);
	}

	/** Get SecNoLine1.
		@return SecNoLine1	  */
	public String getSecNoLine1 () 
	{
		return (String)get_Value(COLUMNNAME_SecNoLine1);
	}

	/** Set SecNoLine2.
		@param SecNoLine2 SecNoLine2	  */
	public void setSecNoLine2 (String SecNoLine2)
	{
		set_Value (COLUMNNAME_SecNoLine2, SecNoLine2);
	}

	/** Get SecNoLine2.
		@return SecNoLine2	  */
	public String getSecNoLine2 () 
	{
		return (String)get_Value(COLUMNNAME_SecNoLine2);
	}

	/** Set Seguro.
		@param Seguro Seguro	  */
	public void setSeguro (BigDecimal Seguro)
	{
		set_Value (COLUMNNAME_Seguro, Seguro);
	}

	/** Get Seguro.
		@return Seguro	  */
	public BigDecimal getSeguro () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Seguro);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Seguro2.
		@param Seguro2 Seguro2	  */
	public void setSeguro2 (BigDecimal Seguro2)
	{
		set_Value (COLUMNNAME_Seguro2, Seguro2);
	}

	/** Get Seguro2.
		@return Seguro2	  */
	public BigDecimal getSeguro2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Seguro2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set sheet.
		@param sheet sheet	  */
	public void setsheet (int sheet)
	{
		set_Value (COLUMNNAME_sheet, Integer.valueOf(sheet));
	}

	/** Get sheet.
		@return sheet	  */
	public int getsheet () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_sheet);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SubtotalBultos.
		@param SubtotalBultos SubtotalBultos	  */
	public void setSubtotalBultos (BigDecimal SubtotalBultos)
	{
		set_Value (COLUMNNAME_SubtotalBultos, SubtotalBultos);
	}

	/** Get SubtotalBultos.
		@return SubtotalBultos	  */
	public BigDecimal getSubtotalBultos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SubtotalBultos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SubtotalPeso.
		@param SubtotalPeso SubtotalPeso	  */
	public void setSubtotalPeso (BigDecimal SubtotalPeso)
	{
		set_Value (COLUMNNAME_SubtotalPeso, SubtotalPeso);
	}

	/** Get SubtotalPeso.
		@return SubtotalPeso	  */
	public BigDecimal getSubtotalPeso () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SubtotalPeso);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalAntBultos.
		@param TotalAntBultos TotalAntBultos	  */
	public void setTotalAntBultos (BigDecimal TotalAntBultos)
	{
		set_Value (COLUMNNAME_TotalAntBultos, TotalAntBultos);
	}

	/** Get TotalAntBultos.
		@return TotalAntBultos	  */
	public BigDecimal getTotalAntBultos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAntBultos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalAntPeso.
		@param TotalAntPeso TotalAntPeso	  */
	public void setTotalAntPeso (BigDecimal TotalAntPeso)
	{
		set_Value (COLUMNNAME_TotalAntPeso, TotalAntPeso);
	}

	/** Get TotalAntPeso.
		@return TotalAntPeso	  */
	public BigDecimal getTotalAntPeso () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAntPeso);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_TR_Border getUY_TR_Border() throws RuntimeException
    {
		return (I_UY_TR_Border)MTable.get(getCtx(), I_UY_TR_Border.Table_Name)
			.getPO(getUY_TR_Border_ID(), get_TrxName());	}

	/** Set UY_TR_Border.
		@param UY_TR_Border_ID UY_TR_Border	  */
	public void setUY_TR_Border_ID (int UY_TR_Border_ID)
	{
		if (UY_TR_Border_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Border_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Border_ID, Integer.valueOf(UY_TR_Border_ID));
	}

	/** Get UY_TR_Border.
		@return UY_TR_Border	  */
	public int getUY_TR_Border_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Border_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Border_ID_1.
		@param UY_TR_Border_ID_1 
		UY_TR_Border_ID_1
	  */
	public void setUY_TR_Border_ID_1 (int UY_TR_Border_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Border_ID_1, Integer.valueOf(UY_TR_Border_ID_1));
	}

	/** Get UY_TR_Border_ID_1.
		@return UY_TR_Border_ID_1
	  */
	public int getUY_TR_Border_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Border_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Crt_ID_1.
		@param UY_TR_Crt_ID_1 UY_TR_Crt_ID_1	  */
	public void setUY_TR_Crt_ID_1 (int UY_TR_Crt_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_Crt_ID_1, Integer.valueOf(UY_TR_Crt_ID_1));
	}

	/** Get UY_TR_Crt_ID_1.
		@return UY_TR_Crt_ID_1	  */
	public int getUY_TR_Crt_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_MicCont.
		@param UY_TR_MicCont_ID UY_TR_MicCont	  */
	public void setUY_TR_MicCont_ID (int UY_TR_MicCont_ID)
	{
		if (UY_TR_MicCont_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MicCont_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_MicCont_ID, Integer.valueOf(UY_TR_MicCont_ID));
	}

	/** Get UY_TR_MicCont.
		@return UY_TR_MicCont	  */
	public int getUY_TR_MicCont_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_MicCont_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Mic getUY_TR_Mic() throws RuntimeException
    {
		return (I_UY_TR_Mic)MTable.get(getCtx(), I_UY_TR_Mic.Table_Name)
			.getPO(getUY_TR_Mic_ID(), get_TrxName());	}

	/** Set UY_TR_Mic.
		@param UY_TR_Mic_ID UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID)
	{
		if (UY_TR_Mic_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Mic_ID, Integer.valueOf(UY_TR_Mic_ID));
	}

	/** Get UY_TR_Mic.
		@return UY_TR_Mic	  */
	public int getUY_TR_Mic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Mic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException
    {
		return (I_UY_TR_PackageType)MTable.get(getCtx(), I_UY_TR_PackageType.Table_Name)
			.getPO(getUY_TR_PackageType_ID(), get_TrxName());	}

	/** Set UY_TR_PackageType.
		@param UY_TR_PackageType_ID UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID)
	{
		if (UY_TR_PackageType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_PackageType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_PackageType_ID, Integer.valueOf(UY_TR_PackageType_ID));
	}

	/** Get UY_TR_PackageType.
		@return UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_PackageType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_PackageType_ID_1.
		@param UY_TR_PackageType_ID_1 UY_TR_PackageType_ID_1	  */
	public void setUY_TR_PackageType_ID_1 (int UY_TR_PackageType_ID_1)
	{
		set_Value (COLUMNNAME_UY_TR_PackageType_ID_1, Integer.valueOf(UY_TR_PackageType_ID_1));
	}

	/** Get UY_TR_PackageType_ID_1.
		@return UY_TR_PackageType_ID_1	  */
	public int getUY_TR_PackageType_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_PackageType_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}