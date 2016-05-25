// Decompiled by DJ v3.11.11.95 Copyright 2009 Atanas Neshkov  Date: 05/04/2011 11:05:31
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

// Referenced classes of package org.eevolution.model:
//            I_HR_Concept, I_HR_Movement, I_HR_Process, I_I_HR_Movement

public class X_I_HR_Movement extends PO
    implements I_I_HR_Movement, I_Persistent
{

    public X_I_HR_Movement(Properties properties, int i, String s)
    {
        super(properties, i, s);
    }

    public X_I_HR_Movement(Properties properties, ResultSet resultset, String s)
    {
        super(properties, resultset, s);
    }

    protected int get_AccessLevel()
    {
        return accessLevel.intValue();
    }

    protected POInfo initPO(Properties properties)
    {
        POInfo poinfo = POInfo.getPOInfo(properties, Table_ID, get_TrxName());
        return poinfo;
    }

    public String toString()
    {
        StringBuffer stringbuffer = (new StringBuffer("X_I_HR_Movement[")).append(get_ID()).append("]");
        return stringbuffer.toString();
    }

    public void setAmount(BigDecimal bigdecimal)
    {
        set_Value("Amount", bigdecimal);
    }

    public BigDecimal getAmount()
    {
        BigDecimal bigdecimal = (BigDecimal)get_Value("Amount");
        if(bigdecimal == null)
            return Env.ZERO;
        else
            return bigdecimal;
    }

    public void setBPartner_Value(String s)
    {
        set_Value("BPartner_Value", s);
    }

    public String getBPartner_Value()
    {
        return (String)get_Value("BPartner_Value");
    }

    public I_C_BPartner getC_BPartner()
        throws RuntimeException
    {
        return (I_C_BPartner)MTable.get(getCtx(), "C_BPartner").getPO(getC_BPartner_ID(), get_TrxName());
    }

    public void setC_BPartner_ID(int i)
    {
        if(i < 1)
            set_Value("C_BPartner_ID", null);
        else
            set_Value("C_BPartner_ID", Integer.valueOf(i));
    }

    public int getC_BPartner_ID()
    {
        Integer integer = (Integer)get_Value("C_BPartner_ID");
        if(integer == null)
            return 0;
        else
            return integer.intValue();
    }

    public void setConceptValue(String s)
    {
        set_Value("ConceptValue", s);
    }

    public String getConceptValue()
    {
        return (String)get_Value("ConceptValue");
    }

    public void setDescription(String s)
    {
        set_Value("Description", s);
    }

    public String getDescription()
    {
        return (String)get_Value("Description");
    }

    public I_HR_Concept getHR_Concept()
        throws RuntimeException
    {
        return (I_HR_Concept)MTable.get(getCtx(), "HR_Concept").getPO(getHR_Concept_ID(), get_TrxName());
    }

    public void setHR_Concept_ID(int i)
    {
        if(i < 1)
            set_Value("HR_Concept_ID", null);
        else
            set_Value("HR_Concept_ID", Integer.valueOf(i));
    }

    public int getHR_Concept_ID()
    {
        Integer integer = (Integer)get_Value("HR_Concept_ID");
        if(integer == null)
            return 0;
        else
            return integer.intValue();
    }

    public I_HR_Movement getHR_Movement()
        throws RuntimeException
    {
        return (I_HR_Movement)MTable.get(getCtx(), "HR_Movement").getPO(getHR_Movement_ID(), get_TrxName());
    }

    public void setHR_Movement_ID(int i)
    {
        if(i < 1)
            set_Value("HR_Movement_ID", null);
        else
            set_Value("HR_Movement_ID", Integer.valueOf(i));
    }

    public int getHR_Movement_ID()
    {
        Integer integer = (Integer)get_Value("HR_Movement_ID");
        if(integer == null)
            return 0;
        else
            return integer.intValue();
    }

    public I_HR_Process getHR_Process()
        throws RuntimeException
    {
        return (I_HR_Process)MTable.get(getCtx(), "HR_Process").getPO(getHR_Process_ID(), get_TrxName());
    }

    public void setHR_Process_ID(int i)
    {
        if(i < 1)
            set_Value("HR_Process_ID", null);
        else
            set_Value("HR_Process_ID", Integer.valueOf(i));
    }

    public int getHR_Process_ID()
    {
        Integer integer = (Integer)get_Value("HR_Process_ID");
        if(integer == null)
            return 0;
        else
            return integer.intValue();
    }

    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getHR_Process_ID()));
    }

    public void setI_ErrorMsg(String s)
    {
        set_Value("I_ErrorMsg", s);
    }

    public String getI_ErrorMsg()
    {
        return (String)get_Value("I_ErrorMsg");
    }

    public void setI_HR_Movement_ID(int i)
    {
        if(i < 1)
            set_ValueNoCheck("I_HR_Movement_ID", null);
        else
            set_ValueNoCheck("I_HR_Movement_ID", Integer.valueOf(i));
    }

    public int getI_HR_Movement_ID()
    {
        Integer integer = (Integer)get_Value("I_HR_Movement_ID");
        if(integer == null)
            return 0;
        else
            return integer.intValue();
    }

    public void setI_IsImported(String s)
    {
        set_Value("I_IsImported", s);
    }

    public String getI_IsImported()
    {
        return (String)get_Value("I_IsImported");
    }

    public void setProcessName(String s)
    {
        set_Value("ProcessName", s);
    }

    public String getProcessName()
    {
        return (String)get_Value("ProcessName");
    }

    public void setProcessed(boolean flag)
    {
        set_Value("Processed", Boolean.valueOf(flag));
    }

    public boolean isProcessed()
    {
        Object obj = get_Value("Processed");
        if(obj != null)
        {
            if(obj instanceof Boolean)
                return ((Boolean)obj).booleanValue();
            else
                return "Y".equals(obj);
        } else
        {
            return false;
        }
    }

    public void setProcessing(boolean flag)
    {
        set_Value("Processing", Boolean.valueOf(flag));
    }

    public boolean isProcessing()
    {
        Object obj = get_Value("Processing");
        if(obj != null)
        {
            if(obj instanceof Boolean)
                return ((Boolean)obj).booleanValue();
            else
                return "Y".equals(obj);
        } else
        {
            return false;
        }
    }

    public void setQty(BigDecimal bigdecimal)
    {
        set_Value("Qty", bigdecimal);
    }

    public BigDecimal getQty()
    {
        BigDecimal bigdecimal = (BigDecimal)get_Value("Qty");
        if(bigdecimal == null)
            return Env.ZERO;
        else
            return bigdecimal;
    }

    public void setServiceDate(Timestamp timestamp)
    {
        set_Value("ServiceDate", timestamp);
    }

    public Timestamp getServiceDate()
    {
        return (Timestamp)get_Value("ServiceDate");
    }

    public void setTextMsg(String s)
    {
        set_Value("TextMsg", s);
    }

    public String getTextMsg()
    {
        return (String)get_Value("TextMsg");
    }

    public void setValidFrom(Timestamp timestamp)
    {
        set_Value("ValidFrom", timestamp);
    }

    public Timestamp getValidFrom()
    {
        return (Timestamp)get_Value("ValidFrom");
    }

    private static final long serialVersionUID = 0x132db90L;
}
