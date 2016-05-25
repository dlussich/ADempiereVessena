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
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_HRParametros
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_HRParametros 
{

    /** TableName=UY_HRParametros */
    public static final String Table_Name = "UY_HRParametros";

    /** AD_Table_ID=1000283 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name amtcuotamutual */
    public static final String COLUMNNAME_amtcuotamutual = "amtcuotamutual";

	/** Set amtcuotamutual	  */
	public void setamtcuotamutual (BigDecimal amtcuotamutual);

	/** Get amtcuotamutual	  */
	public BigDecimal getamtcuotamutual();

    /** Column name coeffaltajustificada */
    public static final String COLUMNNAME_coeffaltajustificada = "coeffaltajustificada";

	/** Set coeffaltajustificada	  */
	public void setcoeffaltajustificada (BigDecimal coeffaltajustificada);

	/** Get coeffaltajustificada	  */
	public BigDecimal getcoeffaltajustificada();

    /** Column name coefhoraextra */
    public static final String COLUMNNAME_coefhoraextra = "coefhoraextra";

	/** Set coefhoraextra	  */
	public void setcoefhoraextra (BigDecimal coefhoraextra);

	/** Get coefhoraextra	  */
	public BigDecimal getcoefhoraextra();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DeduccionViatExt */
    public static final String COLUMNNAME_DeduccionViatExt = "DeduccionViatExt";

	/** Set DeduccionViatExt	  */
	public void setDeduccionViatExt (BigDecimal DeduccionViatExt);

	/** Get DeduccionViatExt	  */
	public BigDecimal getDeduccionViatExt();

    /** Column name FileName */
    public static final String COLUMNNAME_FileName = "FileName";

	/** Set File Name.
	  * Name of the local file or URL
	  */
	public void setFileName (String FileName);

	/** Get File Name.
	  * Name of the local file or URL
	  */
	public String getFileName();

    /** Column name FileNameHistorical */
    public static final String COLUMNNAME_FileNameHistorical = "FileNameHistorical";

	/** Set FileNameHistorical	  */
	public void setFileNameHistorical (String FileNameHistorical);

	/** Get FileNameHistorical	  */
	public String getFileNameHistorical();

    /** Column name finhoranocturna */
    public static final String COLUMNNAME_finhoranocturna = "finhoranocturna";

	/** Set finhoranocturna	  */
	public void setfinhoranocturna (Timestamp finhoranocturna);

	/** Get finhoranocturna	  */
	public Timestamp getfinhoranocturna();

    /** Column name HorasJornada */
    public static final String COLUMNNAME_HorasJornada = "HorasJornada";

	/** Set HorasJornada	  */
	public void setHorasJornada (BigDecimal HorasJornada);

	/** Get HorasJornada	  */
	public BigDecimal getHorasJornada();

    /** Column name ImporteJornal */
    public static final String COLUMNNAME_ImporteJornal = "ImporteJornal";

	/** Set ImporteJornal	  */
	public void setImporteJornal (BigDecimal ImporteJornal);

	/** Get ImporteJornal	  */
	public BigDecimal getImporteJornal();

    /** Column name inihoranocturna */
    public static final String COLUMNNAME_inihoranocturna = "inihoranocturna";

	/** Set inihoranocturna	  */
	public void setinihoranocturna (Timestamp inihoranocturna);

	/** Get inihoranocturna	  */
	public Timestamp getinihoranocturna();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name minapfonasa */
    public static final String COLUMNNAME_minapfonasa = "minapfonasa";

	/** Set minapfonasa	  */
	public void setminapfonasa (BigDecimal minapfonasa);

	/** Get minapfonasa	  */
	public BigDecimal getminapfonasa();

    /** Column name minsDesHorarioCorrido */
    public static final String COLUMNNAME_minsDesHorarioCorrido = "minsDesHorarioCorrido";

	/** Set minsDesHorarioCorrido	  */
	public void setminsDesHorarioCorrido (int minsDesHorarioCorrido);

	/** Get minsDesHorarioCorrido	  */
	public int getminsDesHorarioCorrido();

    /** Column name minsequivextra */
    public static final String COLUMNNAME_minsequivextra = "minsequivextra";

	/** Set minsequivextra	  */
	public void setminsequivextra (int minsequivextra);

	/** Get minsequivextra	  */
	public int getminsequivextra();

    /** Column name minsExtraDesde */
    public static final String COLUMNNAME_minsExtraDesde = "minsExtraDesde";

	/** Set minsExtraDesde	  */
	public void setminsExtraDesde (int minsExtraDesde);

	/** Get minsExtraDesde	  */
	public int getminsExtraDesde();

    /** Column name minsExtraHasta */
    public static final String COLUMNNAME_minsExtraHasta = "minsExtraHasta";

	/** Set minsExtraHasta	  */
	public void setminsExtraHasta (int minsExtraHasta);

	/** Get minsExtraHasta	  */
	public int getminsExtraHasta();

    /** Column name porcfonpatronal */
    public static final String COLUMNNAME_porcfonpatronal = "porcfonpatronal";

	/** Set porcfonpatronal	  */
	public void setporcfonpatronal (BigDecimal porcfonpatronal);

	/** Get porcfonpatronal	  */
	public BigDecimal getporcfonpatronal();

    /** Column name porcfonpersonal */
    public static final String COLUMNNAME_porcfonpersonal = "porcfonpersonal";

	/** Set porcfonpersonal	  */
	public void setporcfonpersonal (BigDecimal porcfonpersonal);

	/** Get porcfonpersonal	  */
	public BigDecimal getporcfonpersonal();

    /** Column name porcfrlpatronal */
    public static final String COLUMNNAME_porcfrlpatronal = "porcfrlpatronal";

	/** Set porcfrlpatronal	  */
	public void setporcfrlpatronal (BigDecimal porcfrlpatronal);

	/** Get porcfrlpatronal	  */
	public BigDecimal getporcfrlpatronal();

    /** Column name porcfrlpersonal */
    public static final String COLUMNNAME_porcfrlpersonal = "porcfrlpersonal";

	/** Set porcfrlpersonal	  */
	public void setporcfrlpersonal (BigDecimal porcfrlpersonal);

	/** Get porcfrlpersonal	  */
	public BigDecimal getporcfrlpersonal();

    /** Column name porcinchoranocturna */
    public static final String COLUMNNAME_porcinchoranocturna = "porcinchoranocturna";

	/** Set porcinchoranocturna	  */
	public void setporcinchoranocturna (BigDecimal porcinchoranocturna);

	/** Get porcinchoranocturna	  */
	public BigDecimal getporcinchoranocturna();

    /** Column name porcjubpatronal */
    public static final String COLUMNNAME_porcjubpatronal = "porcjubpatronal";

	/** Set porcjubpatronal	  */
	public void setporcjubpatronal (BigDecimal porcjubpatronal);

	/** Get porcjubpatronal	  */
	public BigDecimal getporcjubpatronal();

    /** Column name porcjubpersonal */
    public static final String COLUMNNAME_porcjubpersonal = "porcjubpersonal";

	/** Set porcjubpersonal	  */
	public void setporcjubpersonal (BigDecimal porcjubpersonal);

	/** Get porcjubpersonal	  */
	public BigDecimal getporcjubpersonal();

    /** Column name porcminliquido */
    public static final String COLUMNNAME_porcminliquido = "porcminliquido";

	/** Set porcminliquido	  */
	public void setporcminliquido (BigDecimal porcminliquido);

	/** Get porcminliquido	  */
	public BigDecimal getporcminliquido();

    /** Column name porcpuntualidad */
    public static final String COLUMNNAME_porcpuntualidad = "porcpuntualidad";

	/** Set porcpuntualidad	  */
	public void setporcpuntualidad (BigDecimal porcpuntualidad);

	/** Get porcpuntualidad	  */
	public BigDecimal getporcpuntualidad();

    /** Column name primaantiguedad */
    public static final String COLUMNNAME_primaantiguedad = "primaantiguedad";

	/** Set primaantiguedad	  */
	public void setprimaantiguedad (BigDecimal primaantiguedad);

	/** Get primaantiguedad	  */
	public BigDecimal getprimaantiguedad();

    /** Column name PrimaBseLey */
    public static final String COLUMNNAME_PrimaBseLey = "PrimaBseLey";

	/** Set PrimaBseLey	  */
	public void setPrimaBseLey (BigDecimal PrimaBseLey);

	/** Get PrimaBseLey	  */
	public BigDecimal getPrimaBseLey();

    /** Column name salariomin */
    public static final String COLUMNNAME_salariomin = "salariomin";

	/** Set salariomin	  */
	public void setsalariomin (BigDecimal salariomin);

	/** Get salariomin	  */
	public BigDecimal getsalariomin();

    /** Column name topebps */
    public static final String COLUMNNAME_topebps = "topebps";

	/** Set topebps	  */
	public void settopebps (BigDecimal topebps);

	/** Get topebps	  */
	public BigDecimal gettopebps();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UY_HRParametros_ID */
    public static final String COLUMNNAME_UY_HRParametros_ID = "UY_HRParametros_ID";

	/** Set UY_HRParametros	  */
	public void setUY_HRParametros_ID (int UY_HRParametros_ID);

	/** Get UY_HRParametros	  */
	public int getUY_HRParametros_ID();

    /** Column name UY_HRTipoContribuyente_ID */
    public static final String COLUMNNAME_UY_HRTipoContribuyente_ID = "UY_HRTipoContribuyente_ID";

	/** Set UY_HRTipoContribuyente	  */
	public void setUY_HRTipoContribuyente_ID (int UY_HRTipoContribuyente_ID);

	/** Get UY_HRTipoContribuyente	  */
	public int getUY_HRTipoContribuyente_ID();
	
    /** Column name UY_HRTiposAportacion_ID */
    public static final String COLUMNNAME_UY_HRTiposAportacion_ID = "UY_HRTiposAportacion_ID";

	/** Set UY_HRTiposAportacion	  */
	public void setUY_HRTiposAportacion_ID (int UY_HRTiposAportacion_ID);

	/** Get UY_HRTiposAportacion	  */
	public int getUY_HRTiposAportacion_ID();

    /** Column name ViaticoExtranjero */
    public static final String COLUMNNAME_ViaticoExtranjero = "ViaticoExtranjero";

	/** Set ViaticoExtranjero	  */
	public void setViaticoExtranjero (BigDecimal ViaticoExtranjero);

	/** Get ViaticoExtranjero	  */
	public BigDecimal getViaticoExtranjero();

    /** Column name ViaticoNacional */
    public static final String COLUMNNAME_ViaticoNacional = "ViaticoNacional";

	/** Set ViaticoNacional	  */
	public void setViaticoNacional (BigDecimal ViaticoNacional);

	/** Get ViaticoNacional	  */
	public BigDecimal getViaticoNacional();
}
