/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
import java.util.logging.*;

import org.compiere.model.*;
import org.compiere.process.*;
import org.compiere.util.DB;

/**
 *  Copy Order Lines
 *
 *	@author Fabian Aguilar
 *	@version $Id: ProcessPaymentGroup.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ProcessPaymentGroup extends SvrProcess
{
	/**	The Order				*/
	private int		p_UY_PaymentGroup_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
//		ProcessInfoParameter[] para = getParameter();
//		for (int i = 0; i < para.length; i++)
//		{
//			String name = para[i].getParameterName();
//			if (para[i].getParameter() == null)
//				;
//			else if (name.equals("C_Order_ID"))
//				p_C_Order_ID = ((BigDecimal)para[i].getParameter()).intValue();
//			else
//				log.log(Level.SEVERE, "Unknown Parameter: " + name);
//		}
		
		p_UY_PaymentGroup_ID=getRecord_ID();
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		String mysql="select c_payment_id from C_payment where UY_PaymentGroup_ID=?";
	
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count=0;
		try
		{
			pstmt = DB.prepareStatement (mysql, get_TrxName());
			pstmt.setInt (1, p_UY_PaymentGroup_ID);
			
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MPayment mypago= new MPayment(getCtx(), rs.getInt("C_Payment_ID") ,get_TrxName());
				//mypago.processIt("CO");
				if(mypago.isReceipt()){
					if(!mypago.processIt(MPayment.ACTION_Complete)) //si el proceso falla retorna false y uso ! para negarlo y cambiarlo a true
						return mypago.getProcessMsg(); //variable string donde se guarda el resultado del proceso
				}
				else//pago proveedor
				{

					mypago.setPosted(false);
					mypago.deleteAcct();
					

					// OpenUp. Gabriel Vila. 16/07/2010.
					// Asiento contable para el pago
					mypago.setDocAction(MPayment.DOCACTION_Complete);
					mypago.setDocStatus(MPayment.DOCSTATUS_Drafted);
					mypago.setProcessed(false);
					mypago.setProcessing(false);
					mypago.setPosted (false);
					
					if(!mypago.processIt(MPayment.ACTION_Complete)) //si el proceso falla retorna false y uso ! para negarlo y cambiarlo a true
						return mypago.getProcessMsg(); //variable string donde se guarda el resultado del proceso

					// Fin OpenUp.
					
					// OpenUp. Gabriel Vila. 16/07/2010.
					// Pago tiene que quedar como procesado
					mypago.setProcessed(true);
					// Fin OpenUp.

				}
			
				mypago.saveEx();
				count++;
			}
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, mysql, e);
	
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
//		String updatePaymentGroup="update UY_PaymentGroup set docstatus='CO', processed='Y' where UY_PaymentGroup_ID="+p_UY_PaymentGroup_ID;
//		pstmt = DB.prepareStatement (updatePaymentGroup, get_TrxName());
//		pstmt.executeUpdate();
		
		X_UY_PaymentGroup paygroup =new X_UY_PaymentGroup(getCtx(), p_UY_PaymentGroup_ID ,get_TrxName());
		paygroup.setDocStatus("CO");
		paygroup.setProcessed(true);
		paygroup.saveEx();
		//
		if(count==0){
			rollback();
			return "no existe detalle";
		}
		
		commit();//confirmo todos los cambios
		
		//Msg.translate(Env.getCtx(), "ProcessPayment")
		return "todo ok";
	}	//	doIt

}	//	CopyFromOrder
