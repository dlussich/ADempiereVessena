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

/** Generated Model for UY_CashCount
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashCount extends PO implements I_UY_CashCount, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160226L;

    /** Standard Constructor */
    public X_UY_CashCount (Properties ctx, int UY_CashCount_ID, String trxName)
    {
      super (ctx, UY_CashCount_ID, trxName);
      /** if (UY_CashCount_ID == 0)
        {
			setC_Currency_ID (0);
// 142
			setC_DocType_ID (0);
			setDateAction (new Timestamp( System.currentTimeMillis() ));
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// AY
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setPosted (false);
// N
			setProcessed (false);
			setUY_CashCount_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashCount (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashCount[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amt1.
		@param amt1 amt1	  */
	public void setamt1 (BigDecimal amt1)
	{
		set_Value (COLUMNNAME_amt1, amt1);
	}

	/** Get amt1.
		@return amt1	  */
	public BigDecimal getamt1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt2.
		@param amt2 amt2	  */
	public void setamt2 (BigDecimal amt2)
	{
		set_Value (COLUMNNAME_amt2, amt2);
	}

	/** Get amt2.
		@return amt2	  */
	public BigDecimal getamt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt3.
		@param amt3 amt3	  */
	public void setamt3 (BigDecimal amt3)
	{
		set_Value (COLUMNNAME_amt3, amt3);
	}

	/** Get amt3.
		@return amt3	  */
	public BigDecimal getamt3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt4.
		@param amt4 amt4	  */
	public void setamt4 (BigDecimal amt4)
	{
		set_Value (COLUMNNAME_amt4, amt4);
	}

	/** Get amt4.
		@return amt4	  */
	public BigDecimal getamt4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt5.
		@param amt5 amt5	  */
	public void setamt5 (BigDecimal amt5)
	{
		set_Value (COLUMNNAME_amt5, amt5);
	}

	/** Get amt5.
		@return amt5	  */
	public BigDecimal getamt5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt6.
		@param amt6 amt6	  */
	public void setamt6 (BigDecimal amt6)
	{
		set_Value (COLUMNNAME_amt6, amt6);
	}

	/** Get amt6.
		@return amt6	  */
	public BigDecimal getamt6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt7.
		@param amt7 amt7	  */
	public void setamt7 (BigDecimal amt7)
	{
		set_Value (COLUMNNAME_amt7, amt7);
	}

	/** Get amt7.
		@return amt7	  */
	public BigDecimal getamt7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt8.
		@param amt8 amt8	  */
	public void setamt8 (BigDecimal amt8)
	{
		set_Value (COLUMNNAME_amt8, amt8);
	}

	/** Get amt8.
		@return amt8	  */
	public BigDecimal getamt8 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt8);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt9.
		@param amt9 amt9	  */
	public void setamt9 (BigDecimal amt9)
	{
		set_Value (COLUMNNAME_amt9, amt9);
	}

	/** Get amt9.
		@return amt9	  */
	public BigDecimal getamt9 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt9);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtCajas.
		@param AmtCajas AmtCajas	  */
	public void setAmtCajas (BigDecimal AmtCajas)
	{
		set_Value (COLUMNNAME_AmtCajas, AmtCajas);
	}

	/** Get AmtCajas.
		@return AmtCajas	  */
	public BigDecimal getAmtCajas () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtCajas);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtCajas2.
		@param AmtCajas2 AmtCajas2	  */
	public void setAmtCajas2 (BigDecimal AmtCajas2)
	{
		set_Value (COLUMNNAME_AmtCajas2, AmtCajas2);
	}

	/** Get AmtCajas2.
		@return AmtCajas2	  */
	public BigDecimal getAmtCajas2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtCajas2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtChequeRem.
		@param AmtChequeRem AmtChequeRem	  */
	public void setAmtChequeRem (BigDecimal AmtChequeRem)
	{
		set_Value (COLUMNNAME_AmtChequeRem, AmtChequeRem);
	}

	/** Get AmtChequeRem.
		@return AmtChequeRem	  */
	public BigDecimal getAmtChequeRem () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtChequeRem);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtChequeRem2.
		@param AmtChequeRem2 AmtChequeRem2	  */
	public void setAmtChequeRem2 (BigDecimal AmtChequeRem2)
	{
		set_Value (COLUMNNAME_AmtChequeRem2, AmtChequeRem2);
	}

	/** Get AmtChequeRem2.
		@return AmtChequeRem2	  */
	public BigDecimal getAmtChequeRem2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtChequeRem2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtChequeTeo.
		@param AmtChequeTeo AmtChequeTeo	  */
	public void setAmtChequeTeo (BigDecimal AmtChequeTeo)
	{
		set_Value (COLUMNNAME_AmtChequeTeo, AmtChequeTeo);
	}

	/** Get AmtChequeTeo.
		@return AmtChequeTeo	  */
	public BigDecimal getAmtChequeTeo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtChequeTeo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtChequeTeo2.
		@param AmtChequeTeo2 AmtChequeTeo2	  */
	public void setAmtChequeTeo2 (BigDecimal AmtChequeTeo2)
	{
		set_Value (COLUMNNAME_AmtChequeTeo2, AmtChequeTeo2);
	}

	/** Get AmtChequeTeo2.
		@return AmtChequeTeo2	  */
	public BigDecimal getAmtChequeTeo2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtChequeTeo2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcobrocredito.
		@param amtcobrocredito amtcobrocredito	  */
	public void setamtcobrocredito (BigDecimal amtcobrocredito)
	{
		set_Value (COLUMNNAME_amtcobrocredito, amtcobrocredito);
	}

	/** Get amtcobrocredito.
		@return amtcobrocredito	  */
	public BigDecimal getamtcobrocredito () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcobrocredito);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcobrocredito2.
		@param amtcobrocredito2 amtcobrocredito2	  */
	public void setamtcobrocredito2 (BigDecimal amtcobrocredito2)
	{
		set_Value (COLUMNNAME_amtcobrocredito2, amtcobrocredito2);
	}

	/** Get amtcobrocredito2.
		@return amtcobrocredito2	  */
	public BigDecimal getamtcobrocredito2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcobrocredito2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtCofre.
		@param AmtCofre AmtCofre	  */
	public void setAmtCofre (BigDecimal AmtCofre)
	{
		set_Value (COLUMNNAME_AmtCofre, AmtCofre);
	}

	/** Get AmtCofre.
		@return AmtCofre	  */
	public BigDecimal getAmtCofre () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtCofre);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtCofre2.
		@param AmtCofre2 AmtCofre2	  */
	public void setAmtCofre2 (BigDecimal AmtCofre2)
	{
		set_Value (COLUMNNAME_AmtCofre2, AmtCofre2);
	}

	/** Get AmtCofre2.
		@return AmtCofre2	  */
	public BigDecimal getAmtCofre2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtCofre2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcredito.
		@param amtcredito amtcredito	  */
	public void setamtcredito (BigDecimal amtcredito)
	{
		set_Value (COLUMNNAME_amtcredito, amtcredito);
	}

	/** Get amtcredito.
		@return amtcredito	  */
	public BigDecimal getamtcredito () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcredito);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcredito2.
		@param amtcredito2 amtcredito2	  */
	public void setamtcredito2 (BigDecimal amtcredito2)
	{
		set_Value (COLUMNNAME_amtcredito2, amtcredito2);
	}

	/** Get amtcredito2.
		@return amtcredito2	  */
	public BigDecimal getamtcredito2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcredito2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtFinal.
		@param AmtFinal AmtFinal	  */
	public void setAmtFinal (BigDecimal AmtFinal)
	{
		set_Value (COLUMNNAME_AmtFinal, AmtFinal);
	}

	/** Get AmtFinal.
		@return AmtFinal	  */
	public BigDecimal getAmtFinal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtFinal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtFondoFijo.
		@param AmtFondoFijo AmtFondoFijo	  */
	public void setAmtFondoFijo (BigDecimal AmtFondoFijo)
	{
		set_Value (COLUMNNAME_AmtFondoFijo, AmtFondoFijo);
	}

	/** Get AmtFondoFijo.
		@return AmtFondoFijo	  */
	public BigDecimal getAmtFondoFijo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtFondoFijo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtFondoFijo2.
		@param AmtFondoFijo2 AmtFondoFijo2	  */
	public void setAmtFondoFijo2 (BigDecimal AmtFondoFijo2)
	{
		set_Value (COLUMNNAME_AmtFondoFijo2, AmtFondoFijo2);
	}

	/** Get AmtFondoFijo2.
		@return AmtFondoFijo2	  */
	public BigDecimal getAmtFondoFijo2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtFondoFijo2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtInitial.
		@param AmtInitial AmtInitial	  */
	public void setAmtInitial (BigDecimal AmtInitial)
	{
		set_Value (COLUMNNAME_AmtInitial, AmtInitial);
	}

	/** Get AmtInitial.
		@return AmtInitial	  */
	public BigDecimal getAmtInitial () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtInitial);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtventas.
		@param amtventas amtventas	  */
	public void setamtventas (BigDecimal amtventas)
	{
		set_Value (COLUMNNAME_amtventas, amtventas);
	}

	/** Get amtventas.
		@return amtventas	  */
	public BigDecimal getamtventas () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtventas);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtventas2.
		@param amtventas2 amtventas2	  */
	public void setamtventas2 (BigDecimal amtventas2)
	{
		set_Value (COLUMNNAME_amtventas2, amtventas2);
	}

	/** Get amtventas2.
		@return amtventas2	  */
	public BigDecimal getamtventas2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtventas2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVentasBasico.
		@param AmtVentasBasico AmtVentasBasico	  */
	public void setAmtVentasBasico (BigDecimal AmtVentasBasico)
	{
		set_Value (COLUMNNAME_AmtVentasBasico, AmtVentasBasico);
	}

	/** Get AmtVentasBasico.
		@return AmtVentasBasico	  */
	public BigDecimal getAmtVentasBasico () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVentasBasico);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVentasCarnes.
		@param AmtVentasCarnes AmtVentasCarnes	  */
	public void setAmtVentasCarnes (BigDecimal AmtVentasCarnes)
	{
		set_Value (COLUMNNAME_AmtVentasCarnes, AmtVentasCarnes);
	}

	/** Get AmtVentasCarnes.
		@return AmtVentasCarnes	  */
	public BigDecimal getAmtVentasCarnes () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVentasCarnes);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVentasCarnes2.
		@param AmtVentasCarnes2 AmtVentasCarnes2	  */
	public void setAmtVentasCarnes2 (BigDecimal AmtVentasCarnes2)
	{
		set_Value (COLUMNNAME_AmtVentasCarnes2, AmtVentasCarnes2);
	}

	/** Get AmtVentasCarnes2.
		@return AmtVentasCarnes2	  */
	public BigDecimal getAmtVentasCarnes2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVentasCarnes2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVentasExento.
		@param AmtVentasExento AmtVentasExento	  */
	public void setAmtVentasExento (BigDecimal AmtVentasExento)
	{
		set_Value (COLUMNNAME_AmtVentasExento, AmtVentasExento);
	}

	/** Get AmtVentasExento.
		@return AmtVentasExento	  */
	public BigDecimal getAmtVentasExento () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVentasExento);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVentasMinimo.
		@param AmtVentasMinimo AmtVentasMinimo	  */
	public void setAmtVentasMinimo (BigDecimal AmtVentasMinimo)
	{
		set_Value (COLUMNNAME_AmtVentasMinimo, AmtVentasMinimo);
	}

	/** Get AmtVentasMinimo.
		@return AmtVentasMinimo	  */
	public BigDecimal getAmtVentasMinimo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVentasMinimo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVentasPercibido.
		@param AmtVentasPercibido AmtVentasPercibido	  */
	public void setAmtVentasPercibido (BigDecimal AmtVentasPercibido)
	{
		set_Value (COLUMNNAME_AmtVentasPercibido, AmtVentasPercibido);
	}

	/** Get AmtVentasPercibido.
		@return AmtVentasPercibido	  */
	public BigDecimal getAmtVentasPercibido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVentasPercibido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVentasTotal.
		@param AmtVentasTotal AmtVentasTotal	  */
	public void setAmtVentasTotal (BigDecimal AmtVentasTotal)
	{
		set_Value (COLUMNNAME_AmtVentasTotal, AmtVentasTotal);
	}

	/** Get AmtVentasTotal.
		@return AmtVentasTotal	  */
	public BigDecimal getAmtVentasTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVentasTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtVentasVegetales.
		@param AmtVentasVegetales AmtVentasVegetales	  */
	public void setAmtVentasVegetales (BigDecimal AmtVentasVegetales)
	{
		set_Value (COLUMNNAME_AmtVentasVegetales, AmtVentasVegetales);
	}

	/** Get AmtVentasVegetales.
		@return AmtVentasVegetales	  */
	public BigDecimal getAmtVentasVegetales () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtVentasVegetales);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtadevenvases.
		@param amtvtadevenvases amtvtadevenvases	  */
	public void setamtvtadevenvases (BigDecimal amtvtadevenvases)
	{
		set_Value (COLUMNNAME_amtvtadevenvases, amtvtadevenvases);
	}

	/** Get amtvtadevenvases.
		@return amtvtadevenvases	  */
	public BigDecimal getamtvtadevenvases () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtadevenvases);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtapagodeservicio.
		@param amtvtapagodeservicio amtvtapagodeservicio	  */
	public void setamtvtapagodeservicio (BigDecimal amtvtapagodeservicio)
	{
		set_Value (COLUMNNAME_amtvtapagodeservicio, amtvtapagodeservicio);
	}

	/** Get amtvtapagodeservicio.
		@return amtvtapagodeservicio	  */
	public BigDecimal getamtvtapagodeservicio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtapagodeservicio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtatarjeta.
		@param amtvtatarjeta amtvtatarjeta	  */
	public void setamtvtatarjeta (BigDecimal amtvtatarjeta)
	{
		set_Value (COLUMNNAME_amtvtatarjeta, amtvtatarjeta);
	}

	/** Get amtvtatarjeta.
		@return amtvtatarjeta	  */
	public BigDecimal getamtvtatarjeta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtatarjeta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtatarjeta2.
		@param amtvtatarjeta2 amtvtatarjeta2	  */
	public void setamtvtatarjeta2 (BigDecimal amtvtatarjeta2)
	{
		set_Value (COLUMNNAME_amtvtatarjeta2, amtvtatarjeta2);
	}

	/** Get amtvtatarjeta2.
		@return amtvtatarjeta2	  */
	public BigDecimal getamtvtatarjeta2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtatarjeta2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtvtatktalimentacion.
		@param amtvtatktalimentacion amtvtatktalimentacion	  */
	public void setamtvtatktalimentacion (BigDecimal amtvtatktalimentacion)
	{
		set_Value (COLUMNNAME_amtvtatktalimentacion, amtvtatktalimentacion);
	}

	/** Get amtvtatktalimentacion.
		@return amtvtatktalimentacion	  */
	public BigDecimal getamtvtatktalimentacion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtvtatktalimentacion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
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
	/** Request = RQ */
	public static final String DOCACTION_Request = "RQ";
	/** Asign = AS */
	public static final String DOCACTION_Asign = "AS";
	/** Pick = PK */
	public static final String DOCACTION_Pick = "PK";
	/** Recive = RV */
	public static final String DOCACTION_Recive = "RV";
	/** Apply = AY */
	public static final String DOCACTION_Apply = "AY";
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

	/** Set IvaVentasBasico.
		@param IvaVentasBasico IvaVentasBasico	  */
	public void setIvaVentasBasico (BigDecimal IvaVentasBasico)
	{
		set_Value (COLUMNNAME_IvaVentasBasico, IvaVentasBasico);
	}

	/** Get IvaVentasBasico.
		@return IvaVentasBasico	  */
	public BigDecimal getIvaVentasBasico () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IvaVentasBasico);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IvaVentasCarnes.
		@param IvaVentasCarnes IvaVentasCarnes	  */
	public void setIvaVentasCarnes (BigDecimal IvaVentasCarnes)
	{
		set_Value (COLUMNNAME_IvaVentasCarnes, IvaVentasCarnes);
	}

	/** Get IvaVentasCarnes.
		@return IvaVentasCarnes	  */
	public BigDecimal getIvaVentasCarnes () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IvaVentasCarnes);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IvaVentasCarnes2.
		@param IvaVentasCarnes2 IvaVentasCarnes2	  */
	public void setIvaVentasCarnes2 (BigDecimal IvaVentasCarnes2)
	{
		set_Value (COLUMNNAME_IvaVentasCarnes2, IvaVentasCarnes2);
	}

	/** Get IvaVentasCarnes2.
		@return IvaVentasCarnes2	  */
	public BigDecimal getIvaVentasCarnes2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IvaVentasCarnes2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IvaVentasExento.
		@param IvaVentasExento IvaVentasExento	  */
	public void setIvaVentasExento (BigDecimal IvaVentasExento)
	{
		set_Value (COLUMNNAME_IvaVentasExento, IvaVentasExento);
	}

	/** Get IvaVentasExento.
		@return IvaVentasExento	  */
	public BigDecimal getIvaVentasExento () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IvaVentasExento);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IvaVentasMinimo.
		@param IvaVentasMinimo IvaVentasMinimo	  */
	public void setIvaVentasMinimo (BigDecimal IvaVentasMinimo)
	{
		set_Value (COLUMNNAME_IvaVentasMinimo, IvaVentasMinimo);
	}

	/** Get IvaVentasMinimo.
		@return IvaVentasMinimo	  */
	public BigDecimal getIvaVentasMinimo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IvaVentasMinimo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IvaVentasPercibido.
		@param IvaVentasPercibido IvaVentasPercibido	  */
	public void setIvaVentasPercibido (BigDecimal IvaVentasPercibido)
	{
		set_Value (COLUMNNAME_IvaVentasPercibido, IvaVentasPercibido);
	}

	/** Get IvaVentasPercibido.
		@return IvaVentasPercibido	  */
	public BigDecimal getIvaVentasPercibido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IvaVentasPercibido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IvaVentasVegetales.
		@param IvaVentasVegetales IvaVentasVegetales	  */
	public void setIvaVentasVegetales (BigDecimal IvaVentasVegetales)
	{
		set_Value (COLUMNNAME_IvaVentasVegetales, IvaVentasVegetales);
	}

	/** Get IvaVentasVegetales.
		@return IvaVentasVegetales	  */
	public BigDecimal getIvaVentasVegetales () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_IvaVentasVegetales);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set montodolares.
		@param montodolares montodolares	  */
	public void setmontodolares (BigDecimal montodolares)
	{
		set_Value (COLUMNNAME_montodolares, montodolares);
	}

	/** Get montodolares.
		@return montodolares	  */
	public BigDecimal getmontodolares () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montodolares);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set montopesos.
		@param montopesos montopesos	  */
	public void setmontopesos (BigDecimal montopesos)
	{
		set_Value (COLUMNNAME_montopesos, montopesos);
	}

	/** Get montopesos.
		@return montopesos	  */
	public BigDecimal getmontopesos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montopesos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Quebranto.
		@param Quebranto Quebranto	  */
	public void setQuebranto (BigDecimal Quebranto)
	{
		set_Value (COLUMNNAME_Quebranto, Quebranto);
	}

	/** Get Quebranto.
		@return Quebranto	  */
	public BigDecimal getQuebranto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Quebranto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_CashCount.
		@param UY_CashCount_ID UY_CashCount	  */
	public void setUY_CashCount_ID (int UY_CashCount_ID)
	{
		if (UY_CashCount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashCount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashCount_ID, Integer.valueOf(UY_CashCount_ID));
	}

	/** Get UY_CashCount.
		@return UY_CashCount	  */
	public int getUY_CashCount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}