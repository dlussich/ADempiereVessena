/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Jesus Garcia - GlobalQSS Colombia                                 *
* - Carlos Ruiz  - GlobalQSS Colombia                                 *
* Sponsors:                                                           *
* - Gustavo Vega - CybepERP Ecuador                                   *
**********************************************************************/
package org.eevolution.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.eevolution.model.MHRConcept;
import org.eevolution.model.MHRMovement;
import org.eevolution.model.X_I_HR_Movement;

/**
 *	Import Payroll Movements from I_HR_Movement
 *
 * 	@author 	GlobalQSS/jjgq
 */
public class ImportPayrollMovement extends SvrProcess
{
	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	
	/**	Delete old Imported				*/
	private boolean			m_deleteOldImported = false;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_AD_Client_ID = getAD_Client_ID();
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws Exception
	{
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;
		
		//	****	Prepare	****

		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE I_HR_Movement "
				+ "WHERE I_IsImported='Y'").append(clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.info("Delete Old Imported =" + no);
		}

		//	Set Client, Org, IsActive, Created/Updated
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET AD_Client_ID = COALESCE (AD_Client_ID, ").append(m_AD_Client_ID).append("),"
			+ " AD_Org_ID = COALESCE (AD_Org_ID, 0),"
			+ " IsActive = COALESCE (IsActive, 'Y'),"
			+ " Created = COALESCE (Created, SysDate),"
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " Updated = COALESCE (Updated, SysDate),"
			+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
			+ " I_ErrorMsg = ' ',"
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Reset=" + no);
		
		//	Set Mandatory Process
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET HR_Process_ID=(SELECT HR_Process_ID FROM HR_Process p"
			+ " WHERE I_HR_Movement.ProcessName=p.Name AND p.DocStatus IN ('DR', 'IP') AND I_HR_Movement.AD_Client_ID=p.AD_Client_ID) "
			+ "WHERE HR_Process_ID IS NULL AND ProcessName IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("HR_Process=" + no);
		//
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid HR Process,' "
			+ "WHERE HR_Process_ID IS NULL AND ProcessName IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("Invalid HR Process=" + no);

		//	Set Mandatory BPartner
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner bp"
			+ " WHERE I_HR_Movement.BPartner_Value=bp.Value AND bp.IsEmployee = 'Y' AND I_HR_Movement.AD_Client_ID=bp.AD_Client_ID) "
			+ "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("BPartner=" + no);
		//
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid BPartner,' "
			+ "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("Invalid BPartner=" + no);
		
		sql = new StringBuffer ("UPDATE I_HR_Movement "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory BPartner Value,' "
				+ "WHERE BPartner_Value IS NULL"
				+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No Mandatory BPartner Value=" + no);

		//	Concept
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			  + "SET HR_Concept_ID=(SELECT HR_Concept_ID FROM HR_Concept c"
			  + " WHERE I_HR_Movement.ConceptValue=c.Value AND c.IsManual='Y' AND c.IsActive='Y' AND c.Type!='E' AND I_HR_Movement.AD_Client_ID=c.AD_Client_ID) "
			  + "WHERE HR_Concept_ID IS NULL AND ConceptValue IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set Concept from Value=" + no);
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Concept, ' "
			  + "WHERE HR_Concept_ID IS NULL AND ConceptValue IS NOT NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Concept=" + no);
		
		//	Mandatories
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory Process Name,' "
			+ "WHERE ProcessName IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No Mandatory Process Name=" + no);
		//
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory Concept Value,' "
			+ "WHERE ConceptValue IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No Mandatory Concept Value=" + no);
		//
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory ValidFrom,' "
			+ "WHERE ValidFrom IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No Mandatory ValidFrom=" + no);
		//
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory BPartner Value,' "
			+ "WHERE BPartner_Value IS NULL"
			+ " AND I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No Mandatory BPartner Value=" + no);

		// seek already existing movements -> key process+bpartner+concept+validfrom
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			  + "SET HR_Movement_ID=(SELECT HR_Movement_ID FROM HR_Movement m"
			  + " WHERE m.HR_Process_ID=I_HR_Movement.HR_Process_ID AND"
			  + " m.C_BPartner_ID=I_HR_Movement.C_BPartner_ID AND"
			  + " m.HR_Concept_ID=I_HR_Movement.HR_Concept_ID AND"
			  + " TRUNC(m.ValidFrom)=TRUNC(I_HR_Movement.ValidFrom))"
			  + "WHERE HR_Movement_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set current existing movements=" + no);

		commitEx();
		
		//	-------------------------------------------------------------------
		int noInserthrm = 0;
		int noUpdatehrm = 0;

		//	Go through Records
		log.fine("start inserting/updating ...");
		sql = new StringBuffer ("SELECT * FROM I_HR_Movement WHERE I_IsImported='N'")
			.append(clientCheck);
		PreparedStatement pstmt_setImported = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//	Set Imported = Y
			pstmt_setImported = DB.prepareStatement
				("UPDATE I_HR_Movement SET I_IsImported='Y', HR_Movement_ID=?, "
				+ "Updated=SysDate, Processed='Y' WHERE I_HR_Movement_ID=?", get_TrxName());

			//
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_I_HR_Movement imp = new X_I_HR_Movement(getCtx(), rs, get_TrxName());
				int I_HR_Movement_ID = imp.getI_HR_Movement_ID();
				int HR_Movement_ID = imp.getHR_Movement_ID();
				boolean newMovement = HR_Movement_ID == 0;
				log.fine("I_HR_Movement_ID=" + I_HR_Movement_ID + ", HR_Movement_ID=" + HR_Movement_ID);

				MHRMovement hrmovement = null; 
				//	HR Movement
				if (newMovement)			//	Insert new HR Movement
				{
					hrmovement = new MHRMovement(imp);
					if (hrmovement.save())
					{
						HR_Movement_ID = hrmovement.getHR_Movement_ID();
						log.finer("Insert HR Movement");
						noInserthrm++;
					}
					else
					{
						StringBuffer sql0 = new StringBuffer ("UPDATE I_HR_Movement i "
							+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Insert HR Movement failed"))
							.append("WHERE I_HR_Movement_ID=").append(I_HR_Movement_ID);
						DB.executeUpdate(sql0.toString(), get_TrxName());
						continue;
					}
				} else {
					hrmovement = new MHRMovement(getCtx(), HR_Movement_ID, get_TrxName());
					MHRConcept hrconcept = new MHRConcept(getCtx(), hrmovement.getHR_Concept_ID(), get_TrxName());
					
					// set corresponding values
					hrmovement.setAmount(null);
					hrmovement.setQty(null);
					hrmovement.setServiceDate(null);
					hrmovement.setTextMsg(null);
					if (hrconcept.getColumnType().equals(MHRConcept.COLUMNTYPE_Quantity)){				// Concept Type
						hrmovement.setQty(imp.getQty());
					} else if (hrconcept.getColumnType().equals(MHRConcept.COLUMNTYPE_Amount)){
						hrmovement.setAmount(imp.getAmount());
						//OpenUp Nicolas Sarlabos 19/04/2012, se comenta codigo con errores innecesario
					}/* else if (hrconcept.getColumnType().equals(MHRConcept.COLUMNTYPE_Date)){
						hrmovement.setServiceDate(imp.getServiceDate());
					} else if (hrconcept.getColumnType().equals(MHRConcept.COLUMNTYPE_Text)){
						hrmovement.setTextMsg(imp.getTextMsg());
					}*/
					//Fin OpenUp Nicolas 19/04/2012
					if (hrmovement.save())
					{
						noUpdatehrm++;
					}
					else
					{
						StringBuffer sql0 = new StringBuffer ("UPDATE I_HR_Movement i "
							+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Update HR Movement failed"))
							.append("WHERE I_HR_Movement_ID=").append(I_HR_Movement_ID);
						DB.executeUpdate(sql0.toString(), get_TrxName());
						continue;
					}
				}
				
				//	Update I_HR_Movement
				pstmt_setImported.setInt(1, HR_Movement_ID);
				pstmt_setImported.setInt(2, I_HR_Movement_ID);
				no = pstmt_setImported.executeUpdate();
				//
				commitEx();
			}	//	for all I_HR_Movement
			//
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
			DB.close(pstmt_setImported);
			pstmt_setImported = null;
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_HR_Movement "
			+ "SET I_IsImported='N', Updated=SysDate "
			+ "WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		addLog (0, null, new BigDecimal (noInserthrm), "@HR_Movement_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noUpdatehrm), "@HR_Movement_ID@: @Updated@");
		return "";
	}	//	doIt
	

}	//	ImportPayrollMovement
