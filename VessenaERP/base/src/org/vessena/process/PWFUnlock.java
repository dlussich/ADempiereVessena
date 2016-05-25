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

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 *  Workflow unlock process, basicaly change workflow status to complete and processed 
 *
 *	@author OpenUP FL 24/02/2011 issue #327
 */
public class PWFUnlock extends SvrProcess {
	
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

		log.info("Workflow unlock");

		String SQL=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;

		// Activity unlock
		SQL="UPDATE ad_wf_activity SET wfstate='CC', processed='Y' WHERE ad_wf_activity_id IN (SELECT ad_wf_activity_id FROM ad_wf_activity WHERE ad_wf_activity.wfstate='OR')";
		try {
			pstmt=DB.prepareStatement (SQL, null);
			pstmt.executeUpdate();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}
		
		// Process unlock
		SQL="UPDATE ad_wf_process SET wfstate='CC', processed='Y' WHERE ad_wf_process_id IN (SELECT ad_wf_process_id FROM ad_wf_process WHERE wfstate='OR')";
		try {
			pstmt=DB.prepareStatement (SQL, null);
			pstmt.executeUpdate();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs,pstmt);
			rs= null; 
			pstmt= null;
		}	

		return("Unlocked");		// TODO: translate
	}	//	doIt

}
