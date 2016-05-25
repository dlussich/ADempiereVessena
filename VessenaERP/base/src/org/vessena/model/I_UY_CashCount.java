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

/** Generated Interface for UY_CashCount
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_CashCount 
{

    /** TableName=UY_CashCount */
    public static final String Table_Name = "UY_CashCount";

    /** AD_Table_ID=1000918 */
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

    /** Column name amt1 */
    public static final String COLUMNNAME_amt1 = "amt1";

	/** Set amt1	  */
	public void setamt1 (BigDecimal amt1);

	/** Get amt1	  */
	public BigDecimal getamt1();

    /** Column name amt2 */
    public static final String COLUMNNAME_amt2 = "amt2";

	/** Set amt2	  */
	public void setamt2 (BigDecimal amt2);

	/** Get amt2	  */
	public BigDecimal getamt2();

    /** Column name amt3 */
    public static final String COLUMNNAME_amt3 = "amt3";

	/** Set amt3	  */
	public void setamt3 (BigDecimal amt3);

	/** Get amt3	  */
	public BigDecimal getamt3();

    /** Column name amt4 */
    public static final String COLUMNNAME_amt4 = "amt4";

	/** Set amt4	  */
	public void setamt4 (BigDecimal amt4);

	/** Get amt4	  */
	public BigDecimal getamt4();

    /** Column name amt5 */
    public static final String COLUMNNAME_amt5 = "amt5";

	/** Set amt5	  */
	public void setamt5 (BigDecimal amt5);

	/** Get amt5	  */
	public BigDecimal getamt5();

    /** Column name amt6 */
    public static final String COLUMNNAME_amt6 = "amt6";

	/** Set amt6	  */
	public void setamt6 (BigDecimal amt6);

	/** Get amt6	  */
	public BigDecimal getamt6();

    /** Column name amt7 */
    public static final String COLUMNNAME_amt7 = "amt7";

	/** Set amt7	  */
	public void setamt7 (BigDecimal amt7);

	/** Get amt7	  */
	public BigDecimal getamt7();

    /** Column name amt8 */
    public static final String COLUMNNAME_amt8 = "amt8";

	/** Set amt8	  */
	public void setamt8 (BigDecimal amt8);

	/** Get amt8	  */
	public BigDecimal getamt8();

    /** Column name amt9 */
    public static final String COLUMNNAME_amt9 = "amt9";

	/** Set amt9	  */
	public void setamt9 (BigDecimal amt9);

	/** Get amt9	  */
	public BigDecimal getamt9();

    /** Column name AmtCajas */
    public static final String COLUMNNAME_AmtCajas = "AmtCajas";

	/** Set AmtCajas	  */
	public void setAmtCajas (BigDecimal AmtCajas);

	/** Get AmtCajas	  */
	public BigDecimal getAmtCajas();

    /** Column name AmtCajas2 */
    public static final String COLUMNNAME_AmtCajas2 = "AmtCajas2";

	/** Set AmtCajas2	  */
	public void setAmtCajas2 (BigDecimal AmtCajas2);

	/** Get AmtCajas2	  */
	public BigDecimal getAmtCajas2();

    /** Column name AmtChequeRem */
    public static final String COLUMNNAME_AmtChequeRem = "AmtChequeRem";

	/** Set AmtChequeRem	  */
	public void setAmtChequeRem (BigDecimal AmtChequeRem);

	/** Get AmtChequeRem	  */
	public BigDecimal getAmtChequeRem();

    /** Column name AmtChequeRem2 */
    public static final String COLUMNNAME_AmtChequeRem2 = "AmtChequeRem2";

	/** Set AmtChequeRem2	  */
	public void setAmtChequeRem2 (BigDecimal AmtChequeRem2);

	/** Get AmtChequeRem2	  */
	public BigDecimal getAmtChequeRem2();

    /** Column name AmtChequeTeo */
    public static final String COLUMNNAME_AmtChequeTeo = "AmtChequeTeo";

	/** Set AmtChequeTeo	  */
	public void setAmtChequeTeo (BigDecimal AmtChequeTeo);

	/** Get AmtChequeTeo	  */
	public BigDecimal getAmtChequeTeo();

    /** Column name AmtChequeTeo2 */
    public static final String COLUMNNAME_AmtChequeTeo2 = "AmtChequeTeo2";

	/** Set AmtChequeTeo2	  */
	public void setAmtChequeTeo2 (BigDecimal AmtChequeTeo2);

	/** Get AmtChequeTeo2	  */
	public BigDecimal getAmtChequeTeo2();

    /** Column name amtcobrocredito */
    public static final String COLUMNNAME_amtcobrocredito = "amtcobrocredito";

	/** Set amtcobrocredito	  */
	public void setamtcobrocredito (BigDecimal amtcobrocredito);

	/** Get amtcobrocredito	  */
	public BigDecimal getamtcobrocredito();

    /** Column name amtcobrocredito2 */
    public static final String COLUMNNAME_amtcobrocredito2 = "amtcobrocredito2";

	/** Set amtcobrocredito2	  */
	public void setamtcobrocredito2 (BigDecimal amtcobrocredito2);

	/** Get amtcobrocredito2	  */
	public BigDecimal getamtcobrocredito2();

    /** Column name AmtCofre */
    public static final String COLUMNNAME_AmtCofre = "AmtCofre";

	/** Set AmtCofre	  */
	public void setAmtCofre (BigDecimal AmtCofre);

	/** Get AmtCofre	  */
	public BigDecimal getAmtCofre();

    /** Column name AmtCofre2 */
    public static final String COLUMNNAME_AmtCofre2 = "AmtCofre2";

	/** Set AmtCofre2	  */
	public void setAmtCofre2 (BigDecimal AmtCofre2);

	/** Get AmtCofre2	  */
	public BigDecimal getAmtCofre2();

    /** Column name amtcredito */
    public static final String COLUMNNAME_amtcredito = "amtcredito";

	/** Set amtcredito	  */
	public void setamtcredito (BigDecimal amtcredito);

	/** Get amtcredito	  */
	public BigDecimal getamtcredito();

    /** Column name amtcredito2 */
    public static final String COLUMNNAME_amtcredito2 = "amtcredito2";

	/** Set amtcredito2	  */
	public void setamtcredito2 (BigDecimal amtcredito2);

	/** Get amtcredito2	  */
	public BigDecimal getamtcredito2();

    /** Column name AmtFinal */
    public static final String COLUMNNAME_AmtFinal = "AmtFinal";

	/** Set AmtFinal	  */
	public void setAmtFinal (BigDecimal AmtFinal);

	/** Get AmtFinal	  */
	public BigDecimal getAmtFinal();

    /** Column name AmtFondoFijo */
    public static final String COLUMNNAME_AmtFondoFijo = "AmtFondoFijo";

	/** Set AmtFondoFijo	  */
	public void setAmtFondoFijo (BigDecimal AmtFondoFijo);

	/** Get AmtFondoFijo	  */
	public BigDecimal getAmtFondoFijo();

    /** Column name AmtFondoFijo2 */
    public static final String COLUMNNAME_AmtFondoFijo2 = "AmtFondoFijo2";

	/** Set AmtFondoFijo2	  */
	public void setAmtFondoFijo2 (BigDecimal AmtFondoFijo2);

	/** Get AmtFondoFijo2	  */
	public BigDecimal getAmtFondoFijo2();

    /** Column name AmtInitial */
    public static final String COLUMNNAME_AmtInitial = "AmtInitial";

	/** Set AmtInitial	  */
	public void setAmtInitial (BigDecimal AmtInitial);

	/** Get AmtInitial	  */
	public BigDecimal getAmtInitial();

    /** Column name amtventas */
    public static final String COLUMNNAME_amtventas = "amtventas";

	/** Set amtventas	  */
	public void setamtventas (BigDecimal amtventas);

	/** Get amtventas	  */
	public BigDecimal getamtventas();

    /** Column name amtventas2 */
    public static final String COLUMNNAME_amtventas2 = "amtventas2";

	/** Set amtventas2	  */
	public void setamtventas2 (BigDecimal amtventas2);

	/** Get amtventas2	  */
	public BigDecimal getamtventas2();

    /** Column name AmtVentasBasico */
    public static final String COLUMNNAME_AmtVentasBasico = "AmtVentasBasico";

	/** Set AmtVentasBasico	  */
	public void setAmtVentasBasico (BigDecimal AmtVentasBasico);

	/** Get AmtVentasBasico	  */
	public BigDecimal getAmtVentasBasico();

    /** Column name AmtVentasCarnes */
    public static final String COLUMNNAME_AmtVentasCarnes = "AmtVentasCarnes";

	/** Set AmtVentasCarnes	  */
	public void setAmtVentasCarnes (BigDecimal AmtVentasCarnes);

	/** Get AmtVentasCarnes	  */
	public BigDecimal getAmtVentasCarnes();

    /** Column name AmtVentasCarnes2 */
    public static final String COLUMNNAME_AmtVentasCarnes2 = "AmtVentasCarnes2";

	/** Set AmtVentasCarnes2	  */
	public void setAmtVentasCarnes2 (BigDecimal AmtVentasCarnes2);

	/** Get AmtVentasCarnes2	  */
	public BigDecimal getAmtVentasCarnes2();

    /** Column name AmtVentasExento */
    public static final String COLUMNNAME_AmtVentasExento = "AmtVentasExento";

	/** Set AmtVentasExento	  */
	public void setAmtVentasExento (BigDecimal AmtVentasExento);

	/** Get AmtVentasExento	  */
	public BigDecimal getAmtVentasExento();

    /** Column name AmtVentasMinimo */
    public static final String COLUMNNAME_AmtVentasMinimo = "AmtVentasMinimo";

	/** Set AmtVentasMinimo	  */
	public void setAmtVentasMinimo (BigDecimal AmtVentasMinimo);

	/** Get AmtVentasMinimo	  */
	public BigDecimal getAmtVentasMinimo();

    /** Column name AmtVentasPercibido */
    public static final String COLUMNNAME_AmtVentasPercibido = "AmtVentasPercibido";

	/** Set AmtVentasPercibido	  */
	public void setAmtVentasPercibido (BigDecimal AmtVentasPercibido);

	/** Get AmtVentasPercibido	  */
	public BigDecimal getAmtVentasPercibido();

    /** Column name AmtVentasTotal */
    public static final String COLUMNNAME_AmtVentasTotal = "AmtVentasTotal";

	/** Set AmtVentasTotal	  */
	public void setAmtVentasTotal (BigDecimal AmtVentasTotal);

	/** Get AmtVentasTotal	  */
	public BigDecimal getAmtVentasTotal();

    /** Column name AmtVentasVegetales */
    public static final String COLUMNNAME_AmtVentasVegetales = "AmtVentasVegetales";

	/** Set AmtVentasVegetales	  */
	public void setAmtVentasVegetales (BigDecimal AmtVentasVegetales);

	/** Get AmtVentasVegetales	  */
	public BigDecimal getAmtVentasVegetales();

    /** Column name amtvtadevenvases */
    public static final String COLUMNNAME_amtvtadevenvases = "amtvtadevenvases";

	/** Set amtvtadevenvases	  */
	public void setamtvtadevenvases (BigDecimal amtvtadevenvases);

	/** Get amtvtadevenvases	  */
	public BigDecimal getamtvtadevenvases();

    /** Column name amtvtapagodeservicio */
    public static final String COLUMNNAME_amtvtapagodeservicio = "amtvtapagodeservicio";

	/** Set amtvtapagodeservicio	  */
	public void setamtvtapagodeservicio (BigDecimal amtvtapagodeservicio);

	/** Get amtvtapagodeservicio	  */
	public BigDecimal getamtvtapagodeservicio();

    /** Column name amtvtatarjeta */
    public static final String COLUMNNAME_amtvtatarjeta = "amtvtatarjeta";

	/** Set amtvtatarjeta	  */
	public void setamtvtatarjeta (BigDecimal amtvtatarjeta);

	/** Get amtvtatarjeta	  */
	public BigDecimal getamtvtatarjeta();

    /** Column name amtvtatarjeta2 */
    public static final String COLUMNNAME_amtvtatarjeta2 = "amtvtatarjeta2";

	/** Set amtvtatarjeta2	  */
	public void setamtvtatarjeta2 (BigDecimal amtvtatarjeta2);

	/** Get amtvtatarjeta2	  */
	public BigDecimal getamtvtatarjeta2();

    /** Column name amtvtatktalimentacion */
    public static final String COLUMNNAME_amtvtatktalimentacion = "amtvtatktalimentacion";

	/** Set amtvtatktalimentacion	  */
	public void setamtvtatktalimentacion (BigDecimal amtvtatktalimentacion);

	/** Get amtvtatktalimentacion	  */
	public BigDecimal getamtvtatktalimentacion();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

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

    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/** Set Rate.
	  * Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate);

	/** Get Rate.
	  * Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate();

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name DateAction */
    public static final String COLUMNNAME_DateAction = "DateAction";

	/** Set DateAction	  */
	public void setDateAction (Timestamp DateAction);

	/** Get DateAction	  */
	public Timestamp getDateAction();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

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

    /** Column name IvaVentasBasico */
    public static final String COLUMNNAME_IvaVentasBasico = "IvaVentasBasico";

	/** Set IvaVentasBasico	  */
	public void setIvaVentasBasico (BigDecimal IvaVentasBasico);

	/** Get IvaVentasBasico	  */
	public BigDecimal getIvaVentasBasico();

    /** Column name IvaVentasCarnes */
    public static final String COLUMNNAME_IvaVentasCarnes = "IvaVentasCarnes";

	/** Set IvaVentasCarnes	  */
	public void setIvaVentasCarnes (BigDecimal IvaVentasCarnes);

	/** Get IvaVentasCarnes	  */
	public BigDecimal getIvaVentasCarnes();

    /** Column name IvaVentasCarnes2 */
    public static final String COLUMNNAME_IvaVentasCarnes2 = "IvaVentasCarnes2";

	/** Set IvaVentasCarnes2	  */
	public void setIvaVentasCarnes2 (BigDecimal IvaVentasCarnes2);

	/** Get IvaVentasCarnes2	  */
	public BigDecimal getIvaVentasCarnes2();

    /** Column name IvaVentasExento */
    public static final String COLUMNNAME_IvaVentasExento = "IvaVentasExento";

	/** Set IvaVentasExento	  */
	public void setIvaVentasExento (BigDecimal IvaVentasExento);

	/** Get IvaVentasExento	  */
	public BigDecimal getIvaVentasExento();

    /** Column name IvaVentasMinimo */
    public static final String COLUMNNAME_IvaVentasMinimo = "IvaVentasMinimo";

	/** Set IvaVentasMinimo	  */
	public void setIvaVentasMinimo (BigDecimal IvaVentasMinimo);

	/** Get IvaVentasMinimo	  */
	public BigDecimal getIvaVentasMinimo();

    /** Column name IvaVentasPercibido */
    public static final String COLUMNNAME_IvaVentasPercibido = "IvaVentasPercibido";

	/** Set IvaVentasPercibido	  */
	public void setIvaVentasPercibido (BigDecimal IvaVentasPercibido);

	/** Get IvaVentasPercibido	  */
	public BigDecimal getIvaVentasPercibido();

    /** Column name IvaVentasVegetales */
    public static final String COLUMNNAME_IvaVentasVegetales = "IvaVentasVegetales";

	/** Set IvaVentasVegetales	  */
	public void setIvaVentasVegetales (BigDecimal IvaVentasVegetales);

	/** Get IvaVentasVegetales	  */
	public BigDecimal getIvaVentasVegetales();

    /** Column name montodolares */
    public static final String COLUMNNAME_montodolares = "montodolares";

	/** Set montodolares	  */
	public void setmontodolares (BigDecimal montodolares);

	/** Get montodolares	  */
	public BigDecimal getmontodolares();

    /** Column name montopesos */
    public static final String COLUMNNAME_montopesos = "montopesos";

	/** Set montopesos	  */
	public void setmontopesos (BigDecimal montopesos);

	/** Get montopesos	  */
	public BigDecimal getmontopesos();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Quebranto */
    public static final String COLUMNNAME_Quebranto = "Quebranto";

	/** Set Quebranto	  */
	public void setQuebranto (BigDecimal Quebranto);

	/** Get Quebranto	  */
	public BigDecimal getQuebranto();

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

    /** Column name UY_CashCount_ID */
    public static final String COLUMNNAME_UY_CashCount_ID = "UY_CashCount_ID";

	/** Set UY_CashCount	  */
	public void setUY_CashCount_ID (int UY_CashCount_ID);

	/** Get UY_CashCount	  */
	public int getUY_CashCount_ID();
}
