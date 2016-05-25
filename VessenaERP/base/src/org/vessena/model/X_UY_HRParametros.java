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

/** Generated Model for UY_HRParametros
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_HRParametros extends PO implements I_UY_HRParametros, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141124L;

    /** Standard Constructor */
    public X_UY_HRParametros (Properties ctx, int UY_HRParametros_ID, String trxName)
    {
      super (ctx, UY_HRParametros_ID, trxName);
      /** if (UY_HRParametros_ID == 0)
        {
			setamtcuotamutual (Env.ZERO);
			setcoeffaltajustificada (Env.ZERO);
			setcoefhoraextra (Env.ZERO);
			setDeduccionViatExt (Env.ZERO);
			setfinhoranocturna (new Timestamp( System.currentTimeMillis() ));
			setHorasJornada (Env.ZERO);
			setImporteJornal (Env.ZERO);
			setinihoranocturna (new Timestamp( System.currentTimeMillis() ));
			setminapfonasa (Env.ZERO);
			setminsDesHorarioCorrido (0);
			setminsequivextra (0);
			setminsExtraDesde (0);
			setminsExtraHasta (0);
			setporcfonpatronal (Env.ZERO);
			setporcfonpersonal (Env.ZERO);
			setporcfrlpatronal (Env.ZERO);
			setporcfrlpersonal (Env.ZERO);
			setporcinchoranocturna (Env.ZERO);
			setporcjubpatronal (Env.ZERO);
			setporcjubpersonal (Env.ZERO);
			setporcminliquido (Env.ZERO);
			setporcpuntualidad (Env.ZERO);
			setprimaantiguedad (Env.ZERO);
			setsalariomin (Env.ZERO);
			settopebps (Env.ZERO);
			setUY_HRParametros_ID (0);
			setUY_HRTipoContribuyente_ID (0);
			setUY_HRTiposAportacion_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_HRParametros (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_HRParametros[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amtcuotamutual.
		@param amtcuotamutual amtcuotamutual	  */
	public void setamtcuotamutual (BigDecimal amtcuotamutual)
	{
		set_Value (COLUMNNAME_amtcuotamutual, amtcuotamutual);
	}

	/** Get amtcuotamutual.
		@return amtcuotamutual	  */
	public BigDecimal getamtcuotamutual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcuotamutual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set coeffaltajustificada.
		@param coeffaltajustificada coeffaltajustificada	  */
	public void setcoeffaltajustificada (BigDecimal coeffaltajustificada)
	{
		set_Value (COLUMNNAME_coeffaltajustificada, coeffaltajustificada);
	}

	/** Get coeffaltajustificada.
		@return coeffaltajustificada	  */
	public BigDecimal getcoeffaltajustificada () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_coeffaltajustificada);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set coefhoraextra.
		@param coefhoraextra coefhoraextra	  */
	public void setcoefhoraextra (BigDecimal coefhoraextra)
	{
		set_Value (COLUMNNAME_coefhoraextra, coefhoraextra);
	}

	/** Get coefhoraextra.
		@return coefhoraextra	  */
	public BigDecimal getcoefhoraextra () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_coefhoraextra);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DeduccionViatExt.
		@param DeduccionViatExt DeduccionViatExt	  */
	public void setDeduccionViatExt (BigDecimal DeduccionViatExt)
	{
		set_Value (COLUMNNAME_DeduccionViatExt, DeduccionViatExt);
	}

	/** Get DeduccionViatExt.
		@return DeduccionViatExt	  */
	public BigDecimal getDeduccionViatExt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DeduccionViatExt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set FileNameHistorical.
		@param FileNameHistorical FileNameHistorical	  */
	public void setFileNameHistorical (String FileNameHistorical)
	{
		set_Value (COLUMNNAME_FileNameHistorical, FileNameHistorical);
	}

	/** Get FileNameHistorical.
		@return FileNameHistorical	  */
	public String getFileNameHistorical () 
	{
		return (String)get_Value(COLUMNNAME_FileNameHistorical);
	}

	/** Set finhoranocturna.
		@param finhoranocturna finhoranocturna	  */
	public void setfinhoranocturna (Timestamp finhoranocturna)
	{
		set_Value (COLUMNNAME_finhoranocturna, finhoranocturna);
	}

	/** Get finhoranocturna.
		@return finhoranocturna	  */
	public Timestamp getfinhoranocturna () 
	{
		return (Timestamp)get_Value(COLUMNNAME_finhoranocturna);
	}

	/** Set HorasJornada.
		@param HorasJornada HorasJornada	  */
	public void setHorasJornada (BigDecimal HorasJornada)
	{
		set_Value (COLUMNNAME_HorasJornada, HorasJornada);
	}

	/** Get HorasJornada.
		@return HorasJornada	  */
	public BigDecimal getHorasJornada () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HorasJornada);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ImporteJornal.
		@param ImporteJornal ImporteJornal	  */
	public void setImporteJornal (BigDecimal ImporteJornal)
	{
		set_Value (COLUMNNAME_ImporteJornal, ImporteJornal);
	}

	/** Get ImporteJornal.
		@return ImporteJornal	  */
	public BigDecimal getImporteJornal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ImporteJornal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set inihoranocturna.
		@param inihoranocturna inihoranocturna	  */
	public void setinihoranocturna (Timestamp inihoranocturna)
	{
		set_Value (COLUMNNAME_inihoranocturna, inihoranocturna);
	}

	/** Get inihoranocturna.
		@return inihoranocturna	  */
	public Timestamp getinihoranocturna () 
	{
		return (Timestamp)get_Value(COLUMNNAME_inihoranocturna);
	}

	/** Set minapfonasa.
		@param minapfonasa minapfonasa	  */
	public void setminapfonasa (BigDecimal minapfonasa)
	{
		set_Value (COLUMNNAME_minapfonasa, minapfonasa);
	}

	/** Get minapfonasa.
		@return minapfonasa	  */
	public BigDecimal getminapfonasa () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_minapfonasa);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set minsDesHorarioCorrido.
		@param minsDesHorarioCorrido minsDesHorarioCorrido	  */
	public void setminsDesHorarioCorrido (int minsDesHorarioCorrido)
	{
		set_Value (COLUMNNAME_minsDesHorarioCorrido, Integer.valueOf(minsDesHorarioCorrido));
	}

	/** Get minsDesHorarioCorrido.
		@return minsDesHorarioCorrido	  */
	public int getminsDesHorarioCorrido () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_minsDesHorarioCorrido);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set minsequivextra.
		@param minsequivextra minsequivextra	  */
	public void setminsequivextra (int minsequivextra)
	{
		set_Value (COLUMNNAME_minsequivextra, Integer.valueOf(minsequivextra));
	}

	/** Get minsequivextra.
		@return minsequivextra	  */
	public int getminsequivextra () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_minsequivextra);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set minsExtraDesde.
		@param minsExtraDesde minsExtraDesde	  */
	public void setminsExtraDesde (int minsExtraDesde)
	{
		set_Value (COLUMNNAME_minsExtraDesde, Integer.valueOf(minsExtraDesde));
	}

	/** Get minsExtraDesde.
		@return minsExtraDesde	  */
	public int getminsExtraDesde () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_minsExtraDesde);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set minsExtraHasta.
		@param minsExtraHasta minsExtraHasta	  */
	public void setminsExtraHasta (int minsExtraHasta)
	{
		set_Value (COLUMNNAME_minsExtraHasta, Integer.valueOf(minsExtraHasta));
	}

	/** Get minsExtraHasta.
		@return minsExtraHasta	  */
	public int getminsExtraHasta () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_minsExtraHasta);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set porcfonpatronal.
		@param porcfonpatronal porcfonpatronal	  */
	public void setporcfonpatronal (BigDecimal porcfonpatronal)
	{
		set_Value (COLUMNNAME_porcfonpatronal, porcfonpatronal);
	}

	/** Get porcfonpatronal.
		@return porcfonpatronal	  */
	public BigDecimal getporcfonpatronal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcfonpatronal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set porcfonpersonal.
		@param porcfonpersonal porcfonpersonal	  */
	public void setporcfonpersonal (BigDecimal porcfonpersonal)
	{
		set_Value (COLUMNNAME_porcfonpersonal, porcfonpersonal);
	}

	/** Get porcfonpersonal.
		@return porcfonpersonal	  */
	public BigDecimal getporcfonpersonal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcfonpersonal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set porcfrlpatronal.
		@param porcfrlpatronal porcfrlpatronal	  */
	public void setporcfrlpatronal (BigDecimal porcfrlpatronal)
	{
		set_Value (COLUMNNAME_porcfrlpatronal, porcfrlpatronal);
	}

	/** Get porcfrlpatronal.
		@return porcfrlpatronal	  */
	public BigDecimal getporcfrlpatronal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcfrlpatronal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set porcfrlpersonal.
		@param porcfrlpersonal porcfrlpersonal	  */
	public void setporcfrlpersonal (BigDecimal porcfrlpersonal)
	{
		set_Value (COLUMNNAME_porcfrlpersonal, porcfrlpersonal);
	}

	/** Get porcfrlpersonal.
		@return porcfrlpersonal	  */
	public BigDecimal getporcfrlpersonal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcfrlpersonal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set porcinchoranocturna.
		@param porcinchoranocturna porcinchoranocturna	  */
	public void setporcinchoranocturna (BigDecimal porcinchoranocturna)
	{
		set_Value (COLUMNNAME_porcinchoranocturna, porcinchoranocturna);
	}

	/** Get porcinchoranocturna.
		@return porcinchoranocturna	  */
	public BigDecimal getporcinchoranocturna () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcinchoranocturna);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set porcjubpatronal.
		@param porcjubpatronal porcjubpatronal	  */
	public void setporcjubpatronal (BigDecimal porcjubpatronal)
	{
		set_Value (COLUMNNAME_porcjubpatronal, porcjubpatronal);
	}

	/** Get porcjubpatronal.
		@return porcjubpatronal	  */
	public BigDecimal getporcjubpatronal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcjubpatronal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set porcjubpersonal.
		@param porcjubpersonal porcjubpersonal	  */
	public void setporcjubpersonal (BigDecimal porcjubpersonal)
	{
		set_Value (COLUMNNAME_porcjubpersonal, porcjubpersonal);
	}

	/** Get porcjubpersonal.
		@return porcjubpersonal	  */
	public BigDecimal getporcjubpersonal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcjubpersonal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set porcminliquido.
		@param porcminliquido porcminliquido	  */
	public void setporcminliquido (BigDecimal porcminliquido)
	{
		set_Value (COLUMNNAME_porcminliquido, porcminliquido);
	}

	/** Get porcminliquido.
		@return porcminliquido	  */
	public BigDecimal getporcminliquido () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcminliquido);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set porcpuntualidad.
		@param porcpuntualidad porcpuntualidad	  */
	public void setporcpuntualidad (BigDecimal porcpuntualidad)
	{
		set_Value (COLUMNNAME_porcpuntualidad, porcpuntualidad);
	}

	/** Get porcpuntualidad.
		@return porcpuntualidad	  */
	public BigDecimal getporcpuntualidad () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_porcpuntualidad);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set primaantiguedad.
		@param primaantiguedad primaantiguedad	  */
	public void setprimaantiguedad (BigDecimal primaantiguedad)
	{
		set_Value (COLUMNNAME_primaantiguedad, primaantiguedad);
	}

	/** Get primaantiguedad.
		@return primaantiguedad	  */
	public BigDecimal getprimaantiguedad () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_primaantiguedad);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PrimaBseLey.
		@param PrimaBseLey PrimaBseLey	  */
	public void setPrimaBseLey (BigDecimal PrimaBseLey)
	{
		set_Value (COLUMNNAME_PrimaBseLey, PrimaBseLey);
	}

	/** Get PrimaBseLey.
		@return PrimaBseLey	  */
	public BigDecimal getPrimaBseLey () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PrimaBseLey);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set salariomin.
		@param salariomin salariomin	  */
	public void setsalariomin (BigDecimal salariomin)
	{
		set_Value (COLUMNNAME_salariomin, salariomin);
	}

	/** Get salariomin.
		@return salariomin	  */
	public BigDecimal getsalariomin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_salariomin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set topebps.
		@param topebps topebps	  */
	public void settopebps (BigDecimal topebps)
	{
		set_Value (COLUMNNAME_topebps, topebps);
	}

	/** Get topebps.
		@return topebps	  */
	public BigDecimal gettopebps () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_topebps);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_HRParametros.
		@param UY_HRParametros_ID UY_HRParametros	  */
	public void setUY_HRParametros_ID (int UY_HRParametros_ID)
	{
		if (UY_HRParametros_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_HRParametros_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_HRParametros_ID, Integer.valueOf(UY_HRParametros_ID));
	}

	/** Get UY_HRParametros.
		@return UY_HRParametros	  */
	public int getUY_HRParametros_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRParametros_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRTipoContribuyente.
		@param UY_HRTipoContribuyente_ID UY_HRTipoContribuyente	  */
	public void setUY_HRTipoContribuyente_ID (int UY_HRTipoContribuyente_ID)
	{
		if (UY_HRTipoContribuyente_ID < 1) 
			set_Value (COLUMNNAME_UY_HRTipoContribuyente_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRTipoContribuyente_ID, Integer.valueOf(UY_HRTipoContribuyente_ID));
	}

	/** Get UY_HRTipoContribuyente.
		@return UY_HRTipoContribuyente	  */
	public int getUY_HRTipoContribuyente_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRTipoContribuyente_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_HRTiposAportacion.
		@param UY_HRTiposAportacion_ID UY_HRTiposAportacion	  */
	public void setUY_HRTiposAportacion_ID (int UY_HRTiposAportacion_ID)
	{
		if (UY_HRTiposAportacion_ID < 1) 
			set_Value (COLUMNNAME_UY_HRTiposAportacion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_HRTiposAportacion_ID, Integer.valueOf(UY_HRTiposAportacion_ID));
	}

	/** Get UY_HRTiposAportacion.
		@return UY_HRTiposAportacion	  */
	public int getUY_HRTiposAportacion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_HRTiposAportacion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ViaticoExtranjero.
		@param ViaticoExtranjero ViaticoExtranjero	  */
	public void setViaticoExtranjero (BigDecimal ViaticoExtranjero)
	{
		set_Value (COLUMNNAME_ViaticoExtranjero, ViaticoExtranjero);
	}

	/** Get ViaticoExtranjero.
		@return ViaticoExtranjero	  */
	public BigDecimal getViaticoExtranjero () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ViaticoExtranjero);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ViaticoNacional.
		@param ViaticoNacional ViaticoNacional	  */
	public void setViaticoNacional (BigDecimal ViaticoNacional)
	{
		set_Value (COLUMNNAME_ViaticoNacional, ViaticoNacional);
	}

	/** Get ViaticoNacional.
		@return ViaticoNacional	  */
	public BigDecimal getViaticoNacional () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ViaticoNacional);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}