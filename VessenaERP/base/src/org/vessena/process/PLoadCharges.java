/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/09/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCharge;
import org.compiere.model.MElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * org.openup.process - PLoadCharges
 * OpenUp Ltda. Issue # 19
 * Description: Carga inicial de cargos asociados a cuentas contables de un determinado
 * esquema contable.
 * @author Gabriel Vila - 05/09/2012
 * @see
 */
public class PLoadCharges extends SvrProcess {

	int cElementID = 0;
	boolean deleteOld = true;
	
	
	/**
	 * Constructor.
	 */
	public PLoadCharges() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue #19 
	 * @author Gabriel Vila - 05/09/2012
	 * @see
	 */
	@Override
	protected void prepare() {
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("DeleteOld")) this.deleteOld = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				if (name.equalsIgnoreCase("C_Element_ID")) this.cElementID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue #19 
	 * @author Gabriel Vila - 05/09/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
	
		try{
			
			// Si el usuario indico que deben eliminarse los cargos automaticos existentes, lo hago ahora
			if (this.deleteOld) this.deleteOldCharges();
			
			// Obtengo cuentas contables a considerar segun parametros seleccionados por el usuario
			List<MElementValue> cuentas = MElementValue.forElement(this.cElementID, true, getCtx(), get_TrxName());
			
			for (MElementValue cuenta: cuentas){
				cuenta.setAccountInfo();
			}
		
			return "OK";
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Elimino cargos automaticos existentes segun modelo.
	 * OpenUp Ltda. Issue #19 
	 * @author Gabriel Vila - 07/09/2012
	 * @see
	 */
	private void deleteOldCharges() {

		try{
			
			// Obtengo cargos automaticos existentes
			List<MCharge> charges = MCharge.getAutomaticCharges(getCtx(), null);
			
			for (MCharge charge: charges){
				charge.setIsManual(true);
				charge.deleteEx(true);
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

}
