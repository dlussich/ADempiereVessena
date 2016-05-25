/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.openup.process;

import org.openup.model.MSimpleForecast;
import org.compiere.process.SvrProcess;

/**
 *  BOM a simple forecast 
 *
 *	@author OpenUP FL 03/02/2011 issue #149
 */
public class PSimpleForecastBOM extends SvrProcess {
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare() {

	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception {
		// Get the current id
		int UY_SimpleForecast_ID = getRecord_ID();

		log.info("BOM Simple Forecast  UY_SimpleForecast_ID=" + UY_SimpleForecast_ID);

		// The id cannot be 0  
		if (UY_SimpleForecast_ID==0) {
			throw new IllegalArgumentException("Cannot BOM, UY_SimpleForecast_ID==0");
		}
		
		// Get the object
		MSimpleForecast simpleForecast= new MSimpleForecast(getCtx(),UY_SimpleForecast_ID,null);
		
		if (simpleForecast==null) {
			throw new IllegalArgumentException("Cannot BOM, UY_SimpleForecast_ID==" + UY_SimpleForecast_ID);
		}
		
		// Call to the object BOM method
		if (!simpleForecast.BOMIt()) {
			throw new IllegalArgumentException("Cannot BOM, UY_SimpleForecast_ID==" + UY_SimpleForecast_ID);
		}

		return("BOM");
	}	//	doIt

}
