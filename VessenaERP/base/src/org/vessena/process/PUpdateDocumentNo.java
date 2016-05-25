package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;


/**
 * org.openup.process - PUpdateDocumentNo
 * OpenUp Ltda. Issue # 5411
 * Emiliano Bentancor
 * @author Hp - 14/11/2012
 * @see
 */
public class PUpdateDocumentNo extends SvrProcess{

	private String docType;
	private String isSOTrx;
	private int partnerId;
	private String documentNo;
	private String documentNoAux;
	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++){
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("C_Doctype_ID")){
					this.docType = para[i].getParameter().toString();
				}else if (name.equalsIgnoreCase("C_BPartner_ID")){
					this.partnerId = Integer.valueOf(para[i].getParameter().toString());
				}else if (name.equalsIgnoreCase("DocumentNo")){
					this.documentNo = para[i].getParameter().toString();
				}else if (name.equalsIgnoreCase("DocumentNoAux")){
					this.documentNoAux = para[i].getParameter().toString();
				}else if (name.equalsIgnoreCase("IsSOTrx")){
					this.isSOTrx = para[i].getParameter().toString();
				}
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		if(docType.equals("Factura Proveedor")){
			this.updateInvoice();
		}else if(docType.equals("Recibo Proveedor")){
			this.updatePayment();
		}
		return "El Dcoumento Nro.: "+this.documentNo+" fue cambiado a " + this.documentNoAux;
	}
	
	private void updatePayment(){
		try{
			
			String action = "update c_payment set documentNo = '" + this.documentNoAux + "'"+
								" where documentNo = '" + this.documentNo + "'"+
								" and c_bpartner_id = " + this.partnerId +
								" and isreceipt = '" + this.isSOTrx + "'" +
								" and docstatus = 'CO'";
			
			if(DB.executeUpdateEx(action, get_TrxName()) == 0){
				throw new AdempiereException("No se encontro el documento nro.: "+ this.documentNo + " o no esta completado!");
			};
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}
	
	private void updateInvoice(){
		try{
			
			String action = "update c_invoice set documentNo = '" + this.documentNoAux+ "'," +
								" documentNoAux = '" + this.documentNoAux + "'"+
								" where documentNo = '" + this.documentNo + "'"+
								" and c_bpartner_id = " + this.partnerId +
								" and issotrx = '" + this.isSOTrx + "'" +
								" and docstatus = 'CO'";
			
			if(DB.executeUpdateEx(action, get_TrxName()) == 0){
				throw new AdempiereException("No se encontro el documento nro.: "+ this.documentNo + " o no esta completado!");
			};
			
			String action2 = "update Uy_allocdirectPayment set documentNo = '" + this.documentNoAux+ "'" +
								" where c_invoice_id = (select c_invoice_id from c_invoice "+
									" where documentNo = '" + this.documentNoAux + "'"+
									" and c_bpartner_id = " + this.partnerId +
									" and issotrx = '" + this.isSOTrx + "'" +
									" and docstatus = 'CO')";

			if(DB.executeUpdateEx(action2, get_TrxName()) == 0){
				throw new AdempiereException("No se encontro el documento nro.: "+ this.documentNo + " en la tabla de alocaciones!");
			};

			String action3 = "update UY_AllocationInvoice set documentNo = '" + this.documentNoAux+ "'" +
								" where c_invoice_id = (select c_invoice_id from c_invoice "+
									" where documentNo = '" + this.documentNoAux + "'"+
									" and c_bpartner_id = " + this.partnerId +
									" and issotrx = '" + this.isSOTrx + "'" +
									" and docstatus = 'CO')";
			
			if(DB.executeUpdateEx(action3, get_TrxName()) == 0){
				throw new AdempiereException("No se encontro el documento nro.: "+ this.documentNo + " en la tabla de alocaciones!");
			};
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}
}


