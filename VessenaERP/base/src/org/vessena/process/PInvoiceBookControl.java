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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MInvoiceBookControl;
import org.openup.model.MNavecomFilter;

/**
 *  Populate uy_invoicebookcontrol tables 
 *
 *	@author OpenUP FL 09/05/2011 issue #122
 */
public class PInvoiceBookControl extends SvrProcess {
	
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
		MInvoiceBookControl invoiceBookControl= new MInvoiceBookControl(getCtx(),this.getRecord_ID(),get_TrxName());
		
		if (invoiceBookControl==null) {
			throw new IllegalArgumentException("No se puede processar, no se pudo leer objeto con el id "+this.getRecord_ID()); 	// TODO: transalte
		}

		if (!invoiceBookControl.processIt()) {
			throw new IllegalArgumentException("Ocurrio un error y no se pudo processar, id " +this.getRecord_ID());				// TODO: translate
		}

		return("Procesado");																										// TODO: translate
	}	//	doIt
}
