// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 05/04/2011 11:07:50
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

// Referenced classes of package org.eevolution.model:
//            I_HR_Concept, I_HR_Movement, I_HR_Process

public interface I_I_HR_Movement
{

    public abstract int getAD_Client_ID();

    public abstract void setAD_Org_ID(int i);

    public abstract int getAD_Org_ID();

    public abstract void setAmount(BigDecimal bigdecimal);

    public abstract BigDecimal getAmount();

    public abstract void setBPartner_Value(String s);

    public abstract String getBPartner_Value();

    public abstract void setC_BPartner_ID(int i);

    public abstract int getC_BPartner_ID();

    public abstract I_C_BPartner getC_BPartner()
        throws RuntimeException;

    public abstract void setConceptValue(String s);

    public abstract String getConceptValue();

    public abstract Timestamp getCreated();

    public abstract int getCreatedBy();

    public abstract void setDescription(String s);

    public abstract String getDescription();

    public abstract void setHR_Concept_ID(int i);

    public abstract int getHR_Concept_ID();

    public abstract I_HR_Concept getHR_Concept()
        throws RuntimeException;

    public abstract void setHR_Movement_ID(int i);

    public abstract int getHR_Movement_ID();

    public abstract I_HR_Movement getHR_Movement()
        throws RuntimeException;

    public abstract void setHR_Process_ID(int i);

    public abstract int getHR_Process_ID();

    public abstract I_HR_Process getHR_Process()
        throws RuntimeException;

    public abstract void setI_ErrorMsg(String s);

    public abstract String getI_ErrorMsg();

    public abstract void setI_HR_Movement_ID(int i);

    public abstract int getI_HR_Movement_ID();

    public abstract void setI_IsImported(String s);

    public abstract String getI_IsImported();

    public abstract void setIsActive(boolean flag);

    public abstract boolean isActive();

    public abstract void setProcessName(String s);

    public abstract String getProcessName();

    public abstract void setProcessed(boolean flag);

    public abstract boolean isProcessed();

    public abstract void setProcessing(boolean flag);

    public abstract boolean isProcessing();

    public abstract void setQty(BigDecimal bigdecimal);

    public abstract BigDecimal getQty();

    public abstract void setServiceDate(Timestamp timestamp);

    public abstract Timestamp getServiceDate();

    public abstract void setTextMsg(String s);

    public abstract String getTextMsg();

    public abstract Timestamp getUpdated();

    public abstract int getUpdatedBy();

    public abstract void setValidFrom(Timestamp timestamp);

    public abstract Timestamp getValidFrom();

    public static final String Table_Name = "I_HR_Movement";
    public static final int Table_ID = MTable.getTable_ID("I_HR_Movement");
    public static final KeyNamePair Model = new KeyNamePair(Table_ID, "I_HR_Movement");
    public static final BigDecimal accessLevel = BigDecimal.valueOf(3L);
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    public static final String COLUMNNAME_Amount = "Amount";
    public static final String COLUMNNAME_BPartner_Value = "BPartner_Value";
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
    public static final String COLUMNNAME_ConceptValue = "ConceptValue";
    public static final String COLUMNNAME_Created = "Created";
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
    public static final String COLUMNNAME_Description = "Description";
    public static final String COLUMNNAME_HR_Concept_ID = "HR_Concept_ID";
    public static final String COLUMNNAME_HR_Movement_ID = "HR_Movement_ID";
    public static final String COLUMNNAME_HR_Process_ID = "HR_Process_ID";
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";
    public static final String COLUMNNAME_I_HR_Movement_ID = "I_HR_Movement_ID";
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";
    public static final String COLUMNNAME_IsActive = "IsActive";
    public static final String COLUMNNAME_ProcessName = "ProcessName";
    public static final String COLUMNNAME_Processed = "Processed";
    public static final String COLUMNNAME_Processing = "Processing";
    public static final String COLUMNNAME_Qty = "Qty";
    public static final String COLUMNNAME_ServiceDate = "ServiceDate";
    public static final String COLUMNNAME_TextMsg = "TextMsg";
    public static final String COLUMNNAME_Updated = "Updated";
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

}
