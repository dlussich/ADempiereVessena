/**
 * 
 */
package org.compiere.grid;

import java.util.HashMap;
import java.util.logging.Level;

import org.compiere.model.GridTab;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Payment;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.openup.model.I_UY_AsignaTransporteHdr;

/**
 * @author Kacike
 *
 */
public class VCreateFromFactory3 {

	/**	Static Logger	*/
	private static CLogger 	s_log = CLogger.getCLogger (VCreateFromFactory3.class);

	/** Registered classes map (AD_Table_ID -> Class) */
	private static HashMap<Integer, Class<? extends ICreateFrom>> s_registeredClasses = null;

	/**
	 * Register custom VCreateFrom* class
	 * @param ad_table_id
	 * @param cl custom class
	 */
	public static final void registerClass(int ad_table_id, Class<? extends ICreateFrom> cl)
	{
		s_registeredClasses.put(ad_table_id, cl);
		s_log.info("Registered AD_Table_ID="+ad_table_id+", Class="+cl);
	}
	
	static
	{
		// Register defaults:
		s_registeredClasses = new HashMap<Integer, Class<? extends ICreateFrom>>();
		//CAMBIAR
		// OpenUp M.R. Issue#463 Creo createfrom para traer devoluciones
		s_registeredClasses.put(I_UY_AsignaTransporteHdr.Table_ID, VCreateFromDevolucionesUI.class);
		// Fin OpenUp.
		
		// OpenUp. Gabriel Vila. 23/11/2012. Issue #100.
		// Seleccion de resguardos desde recibo de pago
		s_registeredClasses.put(I_C_Payment.Table_ID, VCreateFromResguardoUI.class);
		// Fin OpenUP
	}
	
	/**
	 *  Factory - called from APanel
	 *  @param  mTab        Model Tab for the trx
	 *  @return JDialog
	 */
	public static ICreateFrom create (GridTab mTab)
	{
		//	dynamic init preparation
		int AD_Table_ID = Env.getContextAsInt(Env.getCtx(), mTab.getWindowNo(), "BaseTable_ID");
		
		// OpenUP FL 12/01/2011, special tratement for alloctaions, get the table id relative to the tab instead of the window
		if (AD_Table_ID==I_C_Payment.Table_ID) {
			AD_Table_ID = mTab.getAD_Table_ID();

			// Defensive, if not allocation then reset to base table again
			if (AD_Table_ID!=I_C_AllocationHdr.Table_ID) {
				AD_Table_ID = Env.getContextAsInt(Env.getCtx(), mTab.getWindowNo(), "BaseTable_ID");
			}
		}

		ICreateFrom retValue = null;
		Class<? extends ICreateFrom> cl = s_registeredClasses.get(AD_Table_ID);
		if (cl != null)
		{
			try
			{
				java.lang.reflect.Constructor<? extends ICreateFrom> ctor = cl.getConstructor(GridTab.class);
				retValue = ctor.newInstance(mTab);
			}
			catch (Throwable e)
			{
				s_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				return null;
			}
		}
		if (retValue == null)
		{
			s_log.info("Unsupported AD_Table_ID=" + AD_Table_ID);
			return null;
		}
		return retValue;
	}   //  create
}
