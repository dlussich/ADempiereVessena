/**
 * 
 */
package org.compiere.grid;

import java.util.HashMap;
import java.util.logging.Level;

import org.compiere.model.GridTab;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_Payment;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.openup.model.I_UY_AsignaTransporteHdr;

/**
 * @author Hp
 *
 */
public class VCreateFromFactory4 {
	
	/**	Static Logger	*/
	private static CLogger 	s_log = CLogger.getCLogger (VCreateFromFactory4.class);

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

		// OpenUp Nicolas Sarlabos Issue#854 Creo createfrom para traer solicitudes de devoluciones no asignadas
		s_registeredClasses.put(I_UY_AsignaTransporteHdr.Table_ID, VCreateFromTransporteDevolUI.class);
		// Fin OpenUp.
		
		// OpenUp. Sylvie Bouissa. 16/11/2015. Issue #5052
		s_registeredClasses.put(X_C_Payment.Table_ID, VCreateFromAllocDirectPayOrderUI.class);
		// Fin OpenUp
		
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
		
		//Init OpenUp SBouissa Issue #5052 16/11/2015
		// OpenUP 16/11/2015, special tratement for alloctaions, get the table id relative to the tab instead of the window
		if (AD_Table_ID==I_C_Payment.Table_ID) {
			AD_Table_ID = mTab.getAD_Table_ID();
				// Defencive, if not allocation then reset to base table again
			if (AD_Table_ID!=I_C_AllocationHdr.Table_ID) {
				AD_Table_ID = Env.getContextAsInt(Env.getCtx(), mTab.getWindowNo(), "BaseTable_ID");
			}
		}//FIN OpenUp SBouissa Issue #5052 16/11/2015
		
		
		
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
