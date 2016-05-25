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

/** Generated Model for UY_RT_MovementDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_MovementDetail extends PO implements I_UY_RT_MovementDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160308L;

    /** Standard Constructor */
    public X_UY_RT_MovementDetail (Properties ctx, int UY_RT_MovementDetail_ID, String trxName)
    {
      super (ctx, UY_RT_MovementDetail_ID, trxName);
      /** if (UY_RT_MovementDetail_ID == 0)
        {
			setUY_RT_MovementDetail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_MovementDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_MovementDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Org getAD_Org_To() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Org)MTable.get(getCtx(), org.compiere.model.I_AD_Org.Table_Name)
			.getPO(getAD_Org_ID_To(), get_TrxName());	}

	/** Set AD_Org_ID_To.
		@param AD_Org_ID_To AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To)
	{
		set_Value (COLUMNNAME_AD_Org_ID_To, Integer.valueOf(AD_Org_ID_To));
	}

	/** Get AD_Org_ID_To.
		@return AD_Org_ID_To	  */
	public int getAD_Org_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Org_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cantidad.
		@param Cantidad Cantidad	  */
	public void setCantidad (BigDecimal Cantidad)
	{
		set_Value (COLUMNNAME_Cantidad, Cantidad);
	}

	/** Get Cantidad.
		@return Cantidad	  */
	public BigDecimal getCantidad () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cantidad);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set codigoarticulo.
		@param codigoarticulo codigoarticulo	  */
	public void setcodigoarticulo (String codigoarticulo)
	{
		set_Value (COLUMNNAME_codigoarticulo, codigoarticulo);
	}

	/** Get codigoarticulo.
		@return codigoarticulo	  */
	public String getcodigoarticulo () 
	{
		return (String)get_Value(COLUMNNAME_codigoarticulo);
	}

	/** Set codigoarticulopadre.
		@param codigoarticulopadre codigoarticulopadre	  */
	public void setcodigoarticulopadre (String codigoarticulopadre)
	{
		set_Value (COLUMNNAME_codigoarticulopadre, codigoarticulopadre);
	}

	/** Get codigoarticulopadre.
		@return codigoarticulopadre	  */
	public String getcodigoarticulopadre () 
	{
		return (String)get_Value(COLUMNNAME_codigoarticulopadre);
	}

	/** Set codigobarras.
		@param codigobarras codigobarras	  */
	public void setcodigobarras (String codigobarras)
	{
		set_Value (COLUMNNAME_codigobarras, codigobarras);
	}

	/** Get codigobarras.
		@return codigobarras	  */
	public String getcodigobarras () 
	{
		return (String)get_Value(COLUMNNAME_codigobarras);
	}

	/** Set codigoservicio.
		@param codigoservicio codigoservicio	  */
	public void setcodigoservicio (int codigoservicio)
	{
		set_Value (COLUMNNAME_codigoservicio, Integer.valueOf(codigoservicio));
	}

	/** Get codigoservicio.
		@return codigoservicio	  */
	public int getcodigoservicio () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigoservicio);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codigotipodetalle.
		@param codigotipodetalle codigotipodetalle	  */
	public void setcodigotipodetalle (int codigotipodetalle)
	{
		set_Value (COLUMNNAME_codigotipodetalle, Integer.valueOf(codigotipodetalle));
	}

	/** Get codigotipodetalle.
		@return codigotipodetalle	  */
	public int getcodigotipodetalle () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_codigotipodetalle);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set descripcionarticulo.
		@param descripcionarticulo descripcionarticulo	  */
	public void setdescripcionarticulo (String descripcionarticulo)
	{
		set_Value (COLUMNNAME_descripcionarticulo, descripcionarticulo);
	}

	/** Get descripcionarticulo.
		@return descripcionarticulo	  */
	public String getdescripcionarticulo () 
	{
		return (String)get_Value(COLUMNNAME_descripcionarticulo);
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

	/** Set descuento.
		@param descuento descuento	  */
	public void setdescuento (BigDecimal descuento)
	{
		set_Value (COLUMNNAME_descuento, descuento);
	}

	/** Get descuento.
		@return descuento	  */
	public BigDecimal getdescuento () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_descuento);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set fechaservicio.
		@param fechaservicio fechaservicio	  */
	public void setfechaservicio (Timestamp fechaservicio)
	{
		set_Value (COLUMNNAME_fechaservicio, fechaservicio);
	}

	/** Get fechaservicio.
		@return fechaservicio	  */
	public Timestamp getfechaservicio () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechaservicio);
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

	/** Set importeunitario.
		@param importeunitario importeunitario	  */
	public void setimporteunitario (BigDecimal importeunitario)
	{
		set_Value (COLUMNNAME_importeunitario, importeunitario);
	}

	/** Get importeunitario.
		@return importeunitario	  */
	public BigDecimal getimporteunitario () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_importeunitario);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set medidaventa.
		@param medidaventa medidaventa	  */
	public void setmedidaventa (String medidaventa)
	{
		set_Value (COLUMNNAME_medidaventa, medidaventa);
	}

	/** Get medidaventa.
		@return medidaventa	  */
	public String getmedidaventa () 
	{
		return (String)get_Value(COLUMNNAME_medidaventa);
	}

	/** Set montoicms.
		@param montoicms montoicms	  */
	public void setmontoicms (BigDecimal montoicms)
	{
		set_Value (COLUMNNAME_montoicms, montoicms);
	}

	/** Get montoicms.
		@return montoicms	  */
	public BigDecimal getmontoicms () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montoicms);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set MontoIVA.
		@param MontoIVA MontoIVA	  */
	public void setMontoIVA (BigDecimal MontoIVA)
	{
		set_Value (COLUMNNAME_MontoIVA, MontoIVA);
	}

	/** Get MontoIVA.
		@return MontoIVA	  */
	public BigDecimal getMontoIVA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MontoIVA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set numeroservicio.
		@param numeroservicio numeroservicio	  */
	public void setnumeroservicio (String numeroservicio)
	{
		set_Value (COLUMNNAME_numeroservicio, numeroservicio);
	}

	/** Get numeroservicio.
		@return numeroservicio	  */
	public String getnumeroservicio () 
	{
		return (String)get_Value(COLUMNNAME_numeroservicio);
	}

	/** Set PorcentajeIVA.
		@param PorcentajeIVA PorcentajeIVA	  */
	public void setPorcentajeIVA (BigDecimal PorcentajeIVA)
	{
		set_Value (COLUMNNAME_PorcentajeIVA, PorcentajeIVA);
	}

	/** Get PorcentajeIVA.
		@return PorcentajeIVA	  */
	public BigDecimal getPorcentajeIVA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PorcentajeIVA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set tasaicms.
		@param tasaicms tasaicms	  */
	public void settasaicms (BigDecimal tasaicms)
	{
		set_Value (COLUMNNAME_tasaicms, tasaicms);
	}

	/** Get tasaicms.
		@return tasaicms	  */
	public BigDecimal gettasaicms () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_tasaicms);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set tipotributosalida.
		@param tipotributosalida tipotributosalida	  */
	public void settipotributosalida (String tipotributosalida)
	{
		set_Value (COLUMNNAME_tipotributosalida, tipotributosalida);
	}

	/** Get tipotributosalida.
		@return tipotributosalida	  */
	public String gettipotributosalida () 
	{
		return (String)get_Value(COLUMNNAME_tipotributosalida);
	}

	/** Set UY_RT_MovementDetail.
		@param UY_RT_MovementDetail_ID UY_RT_MovementDetail	  */
	public void setUY_RT_MovementDetail_ID (int UY_RT_MovementDetail_ID)
	{
		if (UY_RT_MovementDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_MovementDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_MovementDetail_ID, Integer.valueOf(UY_RT_MovementDetail_ID));
	}

	/** Get UY_RT_MovementDetail.
		@return UY_RT_MovementDetail	  */
	public int getUY_RT_MovementDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_MovementDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RT_Movement getUY_RT_Movement() throws RuntimeException
    {
		return (I_UY_RT_Movement)MTable.get(getCtx(), I_UY_RT_Movement.Table_Name)
			.getPO(getUY_RT_Movement_ID(), get_TrxName());	}

	/** Set UY_RT_Movement.
		@param UY_RT_Movement_ID UY_RT_Movement	  */
	public void setUY_RT_Movement_ID (int UY_RT_Movement_ID)
	{
		if (UY_RT_Movement_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_Movement_ID, Integer.valueOf(UY_RT_Movement_ID));
	}

	/** Get UY_RT_Movement.
		@return UY_RT_Movement	  */
	public int getUY_RT_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Movement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}