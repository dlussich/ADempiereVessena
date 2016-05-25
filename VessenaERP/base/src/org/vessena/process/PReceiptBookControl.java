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

import java.util.logging.Level;

import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.openup.model.MReceiptBookControl;

/**
 *  Populate uy_receiptbookcontrol tables 
 *
 *	@author OpenUP FL 09/05/2011 issue #122
 */
public class PReceiptBookControl extends SvrProcess {
	
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
	
		// The id cannot be 0  
		if (this.getRecord_ID()==0) {
			throw new IllegalArgumentException("No se puede processar, el id es 0");												// TODO: translate
		}
		
		// Get the object
		MReceiptBookControl receiptBookControl= new MReceiptBookControl(getCtx(),this.getRecord_ID(),get_TrxName());
		
		if (receiptBookControl.get_ID() <= 0) {
			throw new IllegalArgumentException("No se puede processar, no se pudo leer objeto con el id "+this.getRecord_ID()); 	// TODO: transalte
		}

		/*if (!receiptBookControl.processIt(receiptBookControl.getDocAction())) {
			throw new IllegalArgumentException("Ocurrio un error y no se pudo processar, id " + this.getRecord_ID()); // TODO:
				// translate
			}
		
		return("Procesado");	
		// TODO: translate*/
		//OpenUp M.R. 21-09-2011 Issue#721 aca pregunto por estado de documento, dependiendo el estado el la accion a ejecutar
		String mensajeRetorno = "";
		try {
			if (receiptBookControl.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Drafted) || receiptBookControl.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Invalid)) {
				if (!receiptBookControl.processIt(receiptBookControl.getDocAction())) {
					mensajeRetorno = receiptBookControl.getProcessMsg();
					throw new Exception(receiptBookControl.getProcessMsg());
				}
			}

			else {//OpenUp M.R. 29-09-2011 Issue#721 agrego pregunta para validar estado del documento, cosa q no complete al procesar
				if (!receiptBookControl.completeIt(receiptBookControl.getDocAction())) {
				mensajeRetorno = receiptBookControl.getProcessMsg();
				throw new Exception(receiptBookControl.getProcessMsg());
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			rollback();
			throw e;
		}
		
		return mensajeRetorno;
	}	//	doIt
}
//Fin OpenUp