package org.openup.model;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

/*
 * Autor: Oswaldo Blandon
 * Empresa: TIConsultores
 * 20/10/2015
 */
public class CalloutReqSubasta extends CalloutEngine{
/*
 * se realiza validacion de fecha propuesta y fecha de cierre debe ser 
 * mayor a la fecha actual.
 * TICONSULTORES.
 * @see
 *@param ctx
 *@param WindowNo
 *@param mTab
 * @param mField
 * @param value
 * @return
 */
	public String FechaOpen (Properties ctx, int windowno,GridTab mtab, GridField mfield, Object values)
	{
		Calendar fechaactual = new GregorianCalendar(); // se obtiene la fecha actual del sistema
		Date fechaopen = (Date) mtab.getValue("DateStart");// se obtiene la fecha del campo fecha propuesta
		Date fechaop = fechaactual.getTime();// convertimos de calender a date
		// se valida los datos ingresado por el usuario.
		if (fechaopen == null){
			return "";
		}
		else if (fechaopen.before(fechaop) || fechaopen.equals(fechaop)){
			System.out.println("Error");
			mtab.setValue("DateStart", null);
			throw new AdempiereException("La fecha de apertura debe ser mayor a la fecha actual.");
			
		}
		else {
			
			return "";
		}
	}
	
	public String FechaClose (Properties ctx, int windowno,GridTab mtab, GridField mfield, Object values)
		{
			Calendar fechaactual = new GregorianCalendar();
			Date fechaclose = (Date) mtab.getValue("DateEnd");
			Date fechaop = fechaactual.getTime();
			// se valida los datos ingresado por el usuario.
			
			if (fechaclose == null){
				return "";
			}
			
			else if (fechaclose.before(fechaop) || fechaclose.equals(fechaop)){
				System.out.println("Error");
				mtab.setValue("DateEnd", null);
				throw new AdempiereException("La fecha de cierre debe ser mayor a la fecha actual.");
				
				
				
			}
			else {
				
				return "";
			}
		
		
		
	}
	
}
