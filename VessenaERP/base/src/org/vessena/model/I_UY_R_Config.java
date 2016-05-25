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

/** Generated Interface for UY_R_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_R_Config 
{

    /** TableName=UY_R_Config */
    public static final String Table_Name = "UY_R_Config";

    /** AD_Table_ID=1000555 */
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

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name FirstEmailHourInterval */
    public static final String COLUMNNAME_FirstEmailHourInterval = "FirstEmailHourInterval";

	/** Set FirstEmailHourInterval	  */
	public void setFirstEmailHourInterval (int FirstEmailHourInterval);

	/** Get FirstEmailHourInterval	  */
	public int getFirstEmailHourInterval();

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

    /** Column name MailText */
    public static final String COLUMNNAME_MailText = "MailText";

	/** Set Mail Text.
	  * Text used for Mail message
	  */
	public void setMailText (String MailText);

	/** Get Mail Text.
	  * Text used for Mail message
	  */
	public String getMailText();

    /** Column name MailText2 */
    public static final String COLUMNNAME_MailText2 = "MailText2";

	/** Set Mail Text 2.
	  * Optional second text part used for Mail message
	  */
	public void setMailText2 (String MailText2);

	/** Get Mail Text 2.
	  * Optional second text part used for Mail message
	  */
	public String getMailText2();

    /** Column name MailText3 */
    public static final String COLUMNNAME_MailText3 = "MailText3";

	/** Set Mail Text 3.
	  * Optional third text part used for Mail message
	  */
	public void setMailText3 (String MailText3);

	/** Get Mail Text 3.
	  * Optional third text part used for Mail message
	  */
	public String getMailText3();

    /** Column name MailText4 */
    public static final String COLUMNNAME_MailText4 = "MailText4";

	/** Set MailText4.
	  * MailText4
	  */
	public void setMailText4 (String MailText4);

	/** Get MailText4.
	  * MailText4
	  */
	public String getMailText4();

    /** Column name MailText5 */
    public static final String COLUMNNAME_MailText5 = "MailText5";

	/** Set MailText5.
	  * MailText5
	  */
	public void setMailText5 (String MailText5);

	/** Get MailText5.
	  * MailText5
	  */
	public String getMailText5();

    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/** Set Password.
	  * Password of any length (case sensitive)
	  */
	public void setPassword (String Password);

	/** Get Password.
	  * Password of any length (case sensitive)
	  */
	public String getPassword();

    /** Column name SecondEmailDayInterval */
    public static final String COLUMNNAME_SecondEmailDayInterval = "SecondEmailDayInterval";

	/** Set SecondEmailDayInterval	  */
	public void setSecondEmailDayInterval (int SecondEmailDayInterval);

	/** Get SecondEmailDayInterval	  */
	public int getSecondEmailDayInterval();

    /** Column name SendEMailFinish */
    public static final String COLUMNNAME_SendEMailFinish = "SendEMailFinish";

	/** Set SendEMailFinish	  */
	public void setSendEMailFinish (boolean SendEMailFinish);

	/** Get SendEMailFinish	  */
	public boolean isSendEMailFinish();

    /** Column name SendEMailFirstAdvice */
    public static final String COLUMNNAME_SendEMailFirstAdvice = "SendEMailFirstAdvice";

	/** Set SendEMailFirstAdvice	  */
	public void setSendEMailFirstAdvice (boolean SendEMailFirstAdvice);

	/** Get SendEMailFirstAdvice	  */
	public boolean isSendEMailFirstAdvice();

    /** Column name SendEMailSecondAdvice */
    public static final String COLUMNNAME_SendEMailSecondAdvice = "SendEMailSecondAdvice";

	/** Set SendEMailSecondAdvice	  */
	public void setSendEMailSecondAdvice (boolean SendEMailSecondAdvice);

	/** Get SendEMailSecondAdvice	  */
	public boolean isSendEMailSecondAdvice();

    /** Column name SendEMailStart */
    public static final String COLUMNNAME_SendEMailStart = "SendEMailStart";

	/** Set SendEMailStart	  */
	public void setSendEMailStart (boolean SendEMailStart);

	/** Get SendEMailStart	  */
	public boolean isSendEMailStart();

    /** Column name SendEMailThirdAdvice */
    public static final String COLUMNNAME_SendEMailThirdAdvice = "SendEMailThirdAdvice";

	/** Set SendEMailThirdAdvice	  */
	public void setSendEMailThirdAdvice (boolean SendEMailThirdAdvice);

	/** Get SendEMailThirdAdvice	  */
	public boolean isSendEMailThirdAdvice();

    /** Column name ThirdEmailDayInterval */
    public static final String COLUMNNAME_ThirdEmailDayInterval = "ThirdEmailDayInterval";

	/** Set ThirdEmailDayInterval	  */
	public void setThirdEmailDayInterval (int ThirdEmailDayInterval);

	/** Get ThirdEmailDayInterval	  */
	public int getThirdEmailDayInterval();

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

    /** Column name UY_R_Config_ID */
    public static final String COLUMNNAME_UY_R_Config_ID = "UY_R_Config_ID";

	/** Set UY_R_Config	  */
	public void setUY_R_Config_ID (int UY_R_Config_ID);

	/** Get UY_R_Config	  */
	public int getUY_R_Config_ID();

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
