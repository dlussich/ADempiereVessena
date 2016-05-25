package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MPayment;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MLinePayment;
import org.openup.model.MMediosPago;

public class PProcessReciboPago extends SvrProcess {

	private int paymentID = 0;
	
	@Override
	protected String doIt() throws Exception {
		
		String sql ="";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String message = "Proceso finalizado."; 
		
		try
		{		
			// Cabezal del recibo
			MPayment pago= new MPayment(getCtx(), this.paymentID ,get_TrxName());
			
			// Si voy a hacer una reversion
			if (pago.getDocAction().equalsIgnoreCase(MPayment.ACTION_Reverse_Correct)){
				
				if(!pago.processIt(MPayment.ACTION_Reverse_Correct)) 
					throw new Exception (pago.getProcessMsg());
					//return "@Error@" + pago.getProcessMsg();
				pago.setProcessed(true);
				pago.setDocAction(DocAction.ACTION_None);
				pago.setDocStatus(MPayment.DOCSTATUS_Voided);
				pago.setPosted(true);
				pago.saveEx();
				commit();
				return message;
			}

			// Si voy a hacer una anulacion
			if (pago.getDocAction().equalsIgnoreCase(MPayment.ACTION_Void)){
				
				if(!pago.processIt(MPayment.ACTION_Void)) 
					throw new Exception (pago.getProcessMsg());
					//return "@Error@" + pago.getProcessMsg();
				pago.setProcessed(true);
				pago.setDocAction(DocAction.ACTION_None);
				pago.setDocStatus(MPayment.DOCSTATUS_Voided);
				pago.setPosted(true);
				pago.saveEx();
				commit();
				return message;
			}

			
			// Si voy a hacer un cierre
			if (pago.getDocAction().equalsIgnoreCase(MPayment.ACTION_Close)){
				
				if(!pago.processIt(MPayment.ACTION_Close)) 
					throw new Exception (pago.getProcessMsg());
					//return "@Error@" + pago.getProcessMsg();
				pago.setProcessed(true);
				pago.setPosted(true);
				pago.saveEx();
				commit();
				return message;
			}

			
			// Obtengo lineas del recibo
			sql = "SELECT * FROM UY_LinePayment WHERE C_Payment_ID =?";
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, this.paymentID);
			rs = pstmt.executeQuery ();

			// Recorro lineas del recibo
			while (rs.next ())
			{
				count++;
				
				// Refresco estados de la linea
				MLinePayment linea = new MLinePayment(getCtx(), rs.getInt("UY_LinePayment_ID") ,get_TrxName());
				linea.setProcessed(true);
				linea.setDocStatus(MPayment.DOCSTATUS_Completed);

				// Si el recibo es de cobranza
				if (pago.isReceipt()){
					// Si esta linea de recibo no es efectivo, genero un medio de pago asociado
					if (!linea.getTenderType().equalsIgnoreCase(MPayment.TENDERTYPE_Cash)){
						MMediosPago mediopago = new MMediosPago(getCtx(), 0 ,get_TrxName());
						mediopago.setDateTrx(linea.getDateTrx());
						mediopago.setDateAcct(linea.getDateTrx());
						mediopago.setDueDate(linea.getDueDate());
						mediopago.setC_DocType_ID(1000044);
						mediopago.setC_BankAccount_ID(linea.getC_BankAccount_ID());
						mediopago.setC_BPartner_ID(pago.getC_BPartner_ID());
						mediopago.setCheckNo(linea.getDocumentNo());
						mediopago.setC_Currency_ID(pago.getC_Currency_ID());
						mediopago.setPayAmt(linea.getPayAmt());
						mediopago.settipomp("TER");
						mediopago.setestado("CAR");
						mediopago.setDocStatus(MPayment.STATUS_Completed);
						mediopago.setPosted(true);
						mediopago.setProcessed(true);
						mediopago.saveEx();
						linea.setUY_MediosPago_ID(mediopago.getUY_MediosPago_ID());
					}
				}
				else{
					// Estoy en un recibo de pago.
					// El estado de los cheques pasa de EMI o CAR a ENT (entregado).
					// Si esta linea de recibo no es efectivo, genero un medio de pago asociado
					if (!linea.getTenderType().equalsIgnoreCase(MPayment.TENDERTYPE_Cash)){
						MMediosPago mediopago = new MMediosPago(getCtx(), linea.getUY_MediosPago_ID() ,get_TrxName());
						//OpenUp M.R. 08-09-2011 Aca modifico para actualizar estado, y estado anterior de medios de pago
						if (mediopago!=null){
							if (mediopago.gettipomp().equalsIgnoreCase("TER")) {
								mediopago.setestado("ENT");
								mediopago.setoldstatus("CAR");
								mediopago.saveEx();
							} else {

								mediopago.setestado("ENT");
								mediopago.setoldstatus("EMI");
								mediopago.saveEx();
							}

						}
					}
				}
//Fin OpenUp
				// Actualizo linea de recibo
				linea.saveEx();
			}
			
			if (count<=0){
				message = "Error. Recibo sin lineas.";
				throw new Exception(message);
			}
			
			// Actualizo cabezal del recibo
			if(!pago.processIt(MPayment.ACTION_Complete)) 
				throw new Exception (pago.getProcessMsg());
				//return "@Error@" + pago.getProcessMsg();
			//pago.setProcessed(true);
			//pago.setPosted(false);
			
			//pago.saveEx();
			
			//commit();
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			rollback();
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return message;
	}

	@Override
	protected void prepare() {
		this.paymentID = getRecord_ID();
	}
}
