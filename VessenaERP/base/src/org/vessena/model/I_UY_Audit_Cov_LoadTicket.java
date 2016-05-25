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

/** Generated Interface for UY_Audit_Cov_LoadTicket
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_Audit_Cov_LoadTicket 
{

    /** TableName=UY_Audit_Cov_LoadTicket */
    public static final String Table_Name = "UY_Audit_Cov_LoadTicket";

    /** AD_Table_ID=1000607 */
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

    /** Column name amtapgaveta */
    public static final String COLUMNNAME_amtapgaveta = "amtapgaveta";

	/** Set amtapgaveta	  */
	public void setamtapgaveta (BigDecimal amtapgaveta);

	/** Get amtapgaveta	  */
	public BigDecimal getamtapgaveta();

    /** Column name amtcabezalcod1 */
    public static final String COLUMNNAME_amtcabezalcod1 = "amtcabezalcod1";

	/** Set amtcabezalcod1	  */
	public void setamtcabezalcod1 (BigDecimal amtcabezalcod1);

	/** Get amtcabezalcod1	  */
	public BigDecimal getamtcabezalcod1();

    /** Column name amtcajera */
    public static final String COLUMNNAME_amtcajera = "amtcajera";

	/** Set amtcajera	  */
	public void setamtcajera (BigDecimal amtcajera);

	/** Get amtcajera	  */
	public BigDecimal getamtcajera();

    /** Column name amtcanje */
    public static final String COLUMNNAME_amtcanje = "amtcanje";

	/** Set amtcanje	  */
	public void setamtcanje (BigDecimal amtcanje);

	/** Get amtcanje	  */
	public BigDecimal getamtcanje();

    /** Column name amtceditodevolucion */
    public static final String COLUMNNAME_amtceditodevolucion = "amtceditodevolucion";

	/** Set amtceditodevolucion	  */
	public void setamtceditodevolucion (BigDecimal amtceditodevolucion);

	/** Get amtceditodevolucion	  */
	public BigDecimal getamtceditodevolucion();

    /** Column name amtconsistenciavtas */
    public static final String COLUMNNAME_amtconsistenciavtas = "amtconsistenciavtas";

	/** Set amtconsistenciavtas	  */
	public void setamtconsistenciavtas (BigDecimal amtconsistenciavtas);

	/** Get amtconsistenciavtas	  */
	public BigDecimal getamtconsistenciavtas();

    /** Column name amtconsulta */
    public static final String COLUMNNAME_amtconsulta = "amtconsulta";

	/** Set amtconsulta	  */
	public void setamtconsulta (BigDecimal amtconsulta);

	/** Get amtconsulta	  */
	public BigDecimal getamtconsulta();

    /** Column name amtdevolucion */
    public static final String COLUMNNAME_amtdevolucion = "amtdevolucion";

	/** Set amtdevolucion	  */
	public void setamtdevolucion (BigDecimal amtdevolucion);

	/** Get amtdevolucion	  */
	public BigDecimal getamtdevolucion();

    /** Column name amtestadocta */
    public static final String COLUMNNAME_amtestadocta = "amtestadocta";

	/** Set amtestadocta	  */
	public void setamtestadocta (BigDecimal amtestadocta);

	/** Get amtestadocta	  */
	public BigDecimal getamtestadocta();

    /** Column name amtexentoiva */
    public static final String COLUMNNAME_amtexentoiva = "amtexentoiva";

	/** Set amtexentoiva	  */
	public void setamtexentoiva (BigDecimal amtexentoiva);

	/** Get amtexentoiva	  */
	public BigDecimal getamtexentoiva();

    /** Column name amtfactura */
    public static final String COLUMNNAME_amtfactura = "amtfactura";

	/** Set amtfactura	  */
	public void setamtfactura (BigDecimal amtfactura);

	/** Get amtfactura	  */
	public BigDecimal getamtfactura();

    /** Column name amtfondeo */
    public static final String COLUMNNAME_amtfondeo = "amtfondeo";

	/** Set amtfondeo	  */
	public void setamtfondeo (BigDecimal amtfondeo);

	/** Get amtfondeo	  */
	public BigDecimal getamtfondeo();

    /** Column name amtgift */
    public static final String COLUMNNAME_amtgift = "amtgift";

	/** Set amtgift	  */
	public void setamtgift (BigDecimal amtgift);

	/** Get amtgift	  */
	public BigDecimal getamtgift();

    /** Column name amtingredopersonalretiroprod */
    public static final String COLUMNNAME_amtingredopersonalretiroprod = "amtingredopersonalretiroprod";

	/** Set amtingredopersonalretiroprod	  */
	public void setamtingredopersonalretiroprod (BigDecimal amtingredopersonalretiroprod);

	/** Get amtingredopersonalretiroprod	  */
	public BigDecimal getamtingredopersonalretiroprod();

    /** Column name amtinventario */
    public static final String COLUMNNAME_amtinventario = "amtinventario";

	/** Set amtinventario	  */
	public void setamtinventario (BigDecimal amtinventario);

	/** Get amtinventario	  */
	public BigDecimal getamtinventario();

    /** Column name amtlineacod4 */
    public static final String COLUMNNAME_amtlineacod4 = "amtlineacod4";

	/** Set amtlineacod4	  */
	public void setamtlineacod4 (BigDecimal amtlineacod4);

	/** Get amtlineacod4	  */
	public BigDecimal getamtlineacod4();

    /** Column name amtnodefinido */
    public static final String COLUMNNAME_amtnodefinido = "amtnodefinido";

	/** Set amtnodefinido	  */
	public void setamtnodefinido (BigDecimal amtnodefinido);

	/** Get amtnodefinido	  */
	public BigDecimal getamtnodefinido();

    /** Column name amtnogenerado */
    public static final String COLUMNNAME_amtnogenerado = "amtnogenerado";

	/** Set amtnogenerado	  */
	public void setamtnogenerado (BigDecimal amtnogenerado);

	/** Get amtnogenerado	  */
	public BigDecimal getamtnogenerado();

    /** Column name amtpagocaja */
    public static final String COLUMNNAME_amtpagocaja = "amtpagocaja";

	/** Set amtpagocaja	  */
	public void setamtpagocaja (BigDecimal amtpagocaja);

	/** Get amtpagocaja	  */
	public BigDecimal getamtpagocaja();

    /** Column name amtpedido */
    public static final String COLUMNNAME_amtpedido = "amtpedido";

	/** Set amtpedido	  */
	public void setamtpedido (BigDecimal amtpedido);

	/** Get amtpedido	  */
	public BigDecimal getamtpedido();

    /** Column name amtretiro */
    public static final String COLUMNNAME_amtretiro = "amtretiro";

	/** Set amtretiro	  */
	public void setamtretiro (BigDecimal amtretiro);

	/** Get amtretiro	  */
	public BigDecimal getamtretiro();

    /** Column name amtventas */
    public static final String COLUMNNAME_amtventas = "amtventas";

	/** Set amtventas	  */
	public void setamtventas (BigDecimal amtventas);

	/** Get amtventas	  */
	public BigDecimal getamtventas();

    /** Column name amtzprestamoefect */
    public static final String COLUMNNAME_amtzprestamoefect = "amtzprestamoefect";

	/** Set amtzprestamoefect	  */
	public void setamtzprestamoefect (BigDecimal amtzprestamoefect);

	/** Get amtzprestamoefect	  */
	public BigDecimal getamtzprestamoefect();

    /** Column name codigo */
    public static final String COLUMNNAME_codigo = "codigo";

	/** Set codigo	  */
	public void setcodigo (String codigo);

	/** Get codigo	  */
	public String getcodigo();

    /** Column name Cov_LoadTicket_ID */
    public static final String COLUMNNAME_Cov_LoadTicket_ID = "Cov_LoadTicket_ID";

	/** Set Cov_LoadTicket	  */
	public void setCov_LoadTicket_ID (int Cov_LoadTicket_ID);

	/** Get Cov_LoadTicket	  */
	public int getCov_LoadTicket_ID();

	public I_Cov_LoadTicket getCov_LoadTicket() throws RuntimeException;

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name EndTime */
    public static final String COLUMNNAME_EndTime = "EndTime";

	/** Set End Time.
	  * End of the time span
	  */
	public void setEndTime (Timestamp EndTime);

	/** Get End Time.
	  * End of the time span
	  */
	public Timestamp getEndTime();

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

    /** Column name lchequecobranza */
    public static final String COLUMNNAME_lchequecobranza = "lchequecobranza";

	/** Set lchequecobranza	  */
	public void setlchequecobranza (String lchequecobranza);

	/** Get lchequecobranza	  */
	public String getlchequecobranza();

    /** Column name ldevenvases */
    public static final String COLUMNNAME_ldevenvases = "ldevenvases";

	/** Set ldevenvases	  */
	public void setldevenvases (String ldevenvases);

	/** Get ldevenvases	  */
	public String getldevenvases();

    /** Column name llineafacturas2 */
    public static final String COLUMNNAME_llineafacturas2 = "llineafacturas2";

	/** Set llineafacturas2	  */
	public void setllineafacturas2 (String llineafacturas2);

	/** Get llineafacturas2	  */
	public String getllineafacturas2();

    /** Column name llineafondeo */
    public static final String COLUMNNAME_llineafondeo = "llineafondeo";

	/** Set llineafondeo	  */
	public void setllineafondeo (String llineafondeo);

	/** Get llineafondeo	  */
	public String getllineafondeo();

    /** Column name llinearetiro */
    public static final String COLUMNNAME_llinearetiro = "llinearetiro";

	/** Set llinearetiro	  */
	public void setllinearetiro (String llinearetiro);

	/** Get llinearetiro	  */
	public String getllinearetiro();

    /** Column name lpagodeservicio */
    public static final String COLUMNNAME_lpagodeservicio = "lpagodeservicio";

	/** Set lpagodeservicio	  */
	public void setlpagodeservicio (String lpagodeservicio);

	/** Get lpagodeservicio	  */
	public String getlpagodeservicio();

    /** Column name lventaefectivodolares */
    public static final String COLUMNNAME_lventaefectivodolares = "lventaefectivodolares";

	/** Set lventaefectivodolares	  */
	public void setlventaefectivodolares (String lventaefectivodolares);

	/** Get lventaefectivodolares	  */
	public String getlventaefectivodolares();

    /** Column name lventaenefectivo */
    public static final String COLUMNNAME_lventaenefectivo = "lventaenefectivo";

	/** Set lventaenefectivo	  */
	public void setlventaenefectivo (String lventaenefectivo);

	/** Get lventaenefectivo	  */
	public String getlventaenefectivo();

    /** Column name lventascheque */
    public static final String COLUMNNAME_lventascheque = "lventascheque";

	/** Set lventascheque	  */
	public void setlventascheque (String lventascheque);

	/** Get lventascheque	  */
	public String getlventascheque();

    /** Column name lventasclientesfidelizacion */
    public static final String COLUMNNAME_lventasclientesfidelizacion = "lventasclientesfidelizacion";

	/** Set lventasclientesfidelizacion	  */
	public void setlventasclientesfidelizacion (String lventasclientesfidelizacion);

	/** Get lventasclientesfidelizacion	  */
	public String getlventasclientesfidelizacion();

    /** Column name lventasefectivosodexo */
    public static final String COLUMNNAME_lventasefectivosodexo = "lventasefectivosodexo";

	/** Set lventasefectivosodexo	  */
	public void setlventasefectivosodexo (String lventasefectivosodexo);

	/** Get lventasefectivosodexo	  */
	public String getlventasefectivosodexo();

    /** Column name lventasluncheon */
    public static final String COLUMNNAME_lventasluncheon = "lventasluncheon";

	/** Set lventasluncheon	  */
	public void setlventasluncheon (String lventasluncheon);

	/** Get lventasluncheon	  */
	public String getlventasluncheon();

    /** Column name lventastalimentacion */
    public static final String COLUMNNAME_lventastalimentacion = "lventastalimentacion";

	/** Set lventastalimentacion	  */
	public void setlventastalimentacion (String lventastalimentacion);

	/** Get lventastalimentacion	  */
	public String getlventastalimentacion();

    /** Column name lventastarjeta */
    public static final String COLUMNNAME_lventastarjeta = "lventastarjeta";

	/** Set lventastarjeta	  */
	public void setlventastarjeta (String lventastarjeta);

	/** Get lventastarjeta	  */
	public String getlventastarjeta();

    /** Column name lventastarjetacuota */
    public static final String COLUMNNAME_lventastarjetacuota = "lventastarjetacuota";

	/** Set lventastarjetacuota	  */
	public void setlventastarjetacuota (String lventastarjetacuota);

	/** Get lventastarjetacuota	  */
	public String getlventastarjetacuota();

    /** Column name lventastarjetaofline */
    public static final String COLUMNNAME_lventastarjetaofline = "lventastarjetaofline";

	/** Set lventastarjetaofline	  */
	public void setlventastarjetaofline (String lventastarjetaofline);

	/** Get lventastarjetaofline	  */
	public String getlventastarjetaofline();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name path */
    public static final String COLUMNNAME_path = "path";

	/** Set path	  */
	public void setpath (String path);

	/** Get path	  */
	public String getpath();

    /** Column name qtyapgaveta */
    public static final String COLUMNNAME_qtyapgaveta = "qtyapgaveta";

	/** Set qtyapgaveta	  */
	public void setqtyapgaveta (BigDecimal qtyapgaveta);

	/** Get qtyapgaveta	  */
	public BigDecimal getqtyapgaveta();

    /** Column name qtycajera */
    public static final String COLUMNNAME_qtycajera = "qtycajera";

	/** Set qtycajera	  */
	public void setqtycajera (BigDecimal qtycajera);

	/** Get qtycajera	  */
	public BigDecimal getqtycajera();

    /** Column name qtycanje */
    public static final String COLUMNNAME_qtycanje = "qtycanje";

	/** Set qtycanje	  */
	public void setqtycanje (BigDecimal qtycanje);

	/** Get qtycanje	  */
	public BigDecimal getqtycanje();

    /** Column name qtyceditodevolucion */
    public static final String COLUMNNAME_qtyceditodevolucion = "qtyceditodevolucion";

	/** Set qtyceditodevolucion	  */
	public void setqtyceditodevolucion (BigDecimal qtyceditodevolucion);

	/** Get qtyceditodevolucion	  */
	public BigDecimal getqtyceditodevolucion();

    /** Column name qtyconsulta */
    public static final String COLUMNNAME_qtyconsulta = "qtyconsulta";

	/** Set qtyconsulta	  */
	public void setqtyconsulta (BigDecimal qtyconsulta);

	/** Get qtyconsulta	  */
	public BigDecimal getqtyconsulta();

    /** Column name qtydevolucion */
    public static final String COLUMNNAME_qtydevolucion = "qtydevolucion";

	/** Set qtydevolucion	  */
	public void setqtydevolucion (BigDecimal qtydevolucion);

	/** Get qtydevolucion	  */
	public BigDecimal getqtydevolucion();

    /** Column name qtyestadocta */
    public static final String COLUMNNAME_qtyestadocta = "qtyestadocta";

	/** Set qtyestadocta	  */
	public void setqtyestadocta (BigDecimal qtyestadocta);

	/** Get qtyestadocta	  */
	public BigDecimal getqtyestadocta();

    /** Column name qtyexentoiva */
    public static final String COLUMNNAME_qtyexentoiva = "qtyexentoiva";

	/** Set qtyexentoiva	  */
	public void setqtyexentoiva (BigDecimal qtyexentoiva);

	/** Get qtyexentoiva	  */
	public BigDecimal getqtyexentoiva();

    /** Column name qtyfactura */
    public static final String COLUMNNAME_qtyfactura = "qtyfactura";

	/** Set qtyfactura	  */
	public void setqtyfactura (BigDecimal qtyfactura);

	/** Get qtyfactura	  */
	public BigDecimal getqtyfactura();

    /** Column name qtyfondeo */
    public static final String COLUMNNAME_qtyfondeo = "qtyfondeo";

	/** Set qtyfondeo	  */
	public void setqtyfondeo (BigDecimal qtyfondeo);

	/** Get qtyfondeo	  */
	public BigDecimal getqtyfondeo();

    /** Column name qtygift */
    public static final String COLUMNNAME_qtygift = "qtygift";

	/** Set qtygift	  */
	public void setqtygift (BigDecimal qtygift);

	/** Get qtygift	  */
	public BigDecimal getqtygift();

    /** Column name qtyingredopersonalretiroprod */
    public static final String COLUMNNAME_qtyingredopersonalretiroprod = "qtyingredopersonalretiroprod";

	/** Set qtyingredopersonalretiroprod	  */
	public void setqtyingredopersonalretiroprod (BigDecimal qtyingredopersonalretiroprod);

	/** Get qtyingredopersonalretiroprod	  */
	public BigDecimal getqtyingredopersonalretiroprod();

    /** Column name qtyinventario */
    public static final String COLUMNNAME_qtyinventario = "qtyinventario";

	/** Set qtyinventario	  */
	public void setqtyinventario (BigDecimal qtyinventario);

	/** Get qtyinventario	  */
	public BigDecimal getqtyinventario();

    /** Column name qtynodefinido */
    public static final String COLUMNNAME_qtynodefinido = "qtynodefinido";

	/** Set qtynodefinido	  */
	public void setqtynodefinido (BigDecimal qtynodefinido);

	/** Get qtynodefinido	  */
	public BigDecimal getqtynodefinido();

    /** Column name qtynogenerado */
    public static final String COLUMNNAME_qtynogenerado = "qtynogenerado";

	/** Set qtynogenerado	  */
	public void setqtynogenerado (BigDecimal qtynogenerado);

	/** Get qtynogenerado	  */
	public BigDecimal getqtynogenerado();

    /** Column name qtypagocaja */
    public static final String COLUMNNAME_qtypagocaja = "qtypagocaja";

	/** Set qtypagocaja	  */
	public void setqtypagocaja (BigDecimal qtypagocaja);

	/** Get qtypagocaja	  */
	public BigDecimal getqtypagocaja();

    /** Column name qtypedido */
    public static final String COLUMNNAME_qtypedido = "qtypedido";

	/** Set qtypedido	  */
	public void setqtypedido (BigDecimal qtypedido);

	/** Get qtypedido	  */
	public BigDecimal getqtypedido();

    /** Column name qtyretiro */
    public static final String COLUMNNAME_qtyretiro = "qtyretiro";

	/** Set qtyretiro	  */
	public void setqtyretiro (BigDecimal qtyretiro);

	/** Get qtyretiro	  */
	public BigDecimal getqtyretiro();

    /** Column name qtyventas */
    public static final String COLUMNNAME_qtyventas = "qtyventas";

	/** Set qtyventas	  */
	public void setqtyventas (BigDecimal qtyventas);

	/** Get qtyventas	  */
	public BigDecimal getqtyventas();

    /** Column name qtyzprestamoefect */
    public static final String COLUMNNAME_qtyzprestamoefect = "qtyzprestamoefect";

	/** Set qtyzprestamoefect	  */
	public void setqtyzprestamoefect (BigDecimal qtyzprestamoefect);

	/** Get qtyzprestamoefect	  */
	public BigDecimal getqtyzprestamoefect();

    /** Column name StartTime */
    public static final String COLUMNNAME_StartTime = "StartTime";

	/** Set Start Time.
	  * Time started
	  */
	public void setStartTime (Timestamp StartTime);

	/** Get Start Time.
	  * Time started
	  */
	public Timestamp getStartTime();

    /** Column name totalheaders */
    public static final String COLUMNNAME_totalheaders = "totalheaders";

	/** Set totalheaders	  */
	public void settotalheaders (String totalheaders);

	/** Get totalheaders	  */
	public String gettotalheaders();

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Total Lines.
	  * Total of all document lines
	  */
	public void setTotalLines (String TotalLines);

	/** Get Total Lines.
	  * Total of all document lines
	  */
	public String getTotalLines();

    /** Column name totallinesfile */
    public static final String COLUMNNAME_totallinesfile = "totallinesfile";

	/** Set totallinesfile	  */
	public void settotallinesfile (String totallinesfile);

	/** Get totallinesfile	  */
	public String gettotallinesfile();

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

    /** Column name UY_Audit_Cov_LoadTicket_ID */
    public static final String COLUMNNAME_UY_Audit_Cov_LoadTicket_ID = "UY_Audit_Cov_LoadTicket_ID";

	/** Set UY_Audit_Cov_LoadTicket	  */
	public void setUY_Audit_Cov_LoadTicket_ID (int UY_Audit_Cov_LoadTicket_ID);

	/** Get UY_Audit_Cov_LoadTicket	  */
	public int getUY_Audit_Cov_LoadTicket_ID();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
