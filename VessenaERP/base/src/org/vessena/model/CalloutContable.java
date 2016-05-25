/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 23/11/2014
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;


/**
 * org.openup.model - CalloutContable
 * OpenUp Ltda. Issue #3315	 
 * Description: Contenedor de callouts contables.
 * @author Gabriel Vila - 23/11/2014
 * @see
 */
public class CalloutContable extends CalloutEngine {

	/**
	 * Constructor.
	 */
	public CalloutContable() {
	}

	
	/***
	 * Al seleccionar una cuenta contable en linea de registracion de asiento tipo, carga informacion de esta cuenta en la
	 * parametrizacion del asiento tipo. 
	 * OpenUp Ltda. Issue #3315 
	 * @author Gabriel Vila - 23/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setTypeLineInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int evID = ((Integer)value).intValue();
		
		if (evID == 0) return "";
		
		MElementValue ev = new MElementValue(ctx, evID, null);
		
		if (ev.get_ID() <= 0) return "";
		
		MTypeFact header = new MTypeFact(ctx, (Integer)mTab.getValue("UY_TypeFact_ID"), null);
		
		if (header.get_ID() <= 0) return "";
		
		MDocTypeFact dtf = MDocType.getDocTypeFact(ctx, header.getC_DocType_ID_2(), evID, null);
		
		if (dtf.get_ID() <= 0) return "";
		
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_Calculate, dtf.getCalculate());
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_IsCalculated, (dtf.getCalculate() == null) ? false : true);
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_IsDebit, dtf.isDebit());
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_UY_DocTypeFact_ID, dtf.get_ID());
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_Value, dtf.getValue());
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_ManageBPartner, ev.get_ValueAsBoolean("ManageBPartner"));
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_ManageDocument, ev.get_ValueAsBoolean("ManageDocument"));
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_ManageDateTrx, ev.get_ValueAsBoolean("ManageDateTrx"));
		mTab.setValue(X_UY_TypeFactLine.COLUMNNAME_ManageDueDate, ev.get_ValueAsBoolean("ManageDueDate"));
		
		return "";
		
	}

	
	/***
	 * Dada un moneda setea tasa de cambio.
	 * OpenUp Ltda. Issue #3315 
	 * @author Gabriel Vila - 23/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setCurrencyRate(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int curID = ((Integer)value).intValue();
		
		if (curID == 0) return "";
		
		MCurrency cur = new MCurrency(ctx, curID, null);
		
		if (cur.get_ID() <= 0) return "";

		MClient client = new MClient(ctx, Env.getAD_Client_ID(ctx), null);
		if (client.get_ID() <= 0) return "";

		int idCurrencyAcct = client.getAcctSchema().getC_Currency_ID(); 
		BigDecimal tc = Env.ONE;
		
		Timestamp fecTC = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		if (mTab.getValue("DateTrx") != null){
			fecTC = TimeUtil.trunc((Timestamp)mTab.getValue("DateTrx"), TimeUtil.TRUNC_DAY);
		}
		else if (mTab.getValue("DateInvoiced") != null){
			fecTC = TimeUtil.trunc((Timestamp)mTab.getValue("DateInvoiced"), TimeUtil.TRUNC_DAY);
		}
		else if (mTab.getValue("DateOrdered") != null){
			fecTC = TimeUtil.trunc((Timestamp)mTab.getValue("DateOrdered"), TimeUtil.TRUNC_DAY);
		}
			
		if (curID != idCurrencyAcct){
			tc = MConversionRate.getRate(curID, idCurrencyAcct, fecTC, 0, client.get_ID(), 0);
			if (tc == null){
				tc = Env.ONE;
			}
			else if (tc == Env.ZERO){
				tc = Env.ONE;
			}
		}
		
		mTab.setValue("CurrencyRate", tc);
		
		return "";
		
	}

	
}
