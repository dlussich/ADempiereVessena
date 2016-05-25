/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 13/02/2015
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.MTax;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.I_UY_CashMove;
import org.openup.model.MSUMAccountStatus;
import org.openup.model.X_UY_CashMove;

/**
 * org.openup.process - PLoadDC
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 13/02/2015
 * @see
 */
public class PLoadDC extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PLoadDC() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 13/02/2015
	 * @see
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 13/02/2015
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			DB.executeUpdateEx(" delete from aux_dc ", null);
			
			sql = " select c_payment_id " +
				  " from c_payment " +
				  " where ad_client_id = 1000006 " +
				  " and isreceipt='Y' " +
				  " and docstatus='CO' " +
				  " and c_currency_id = 100 " +
				  " and datetrx between '2015-01-01 00:00:00' and '2015-01-31 00:00:00' " +
				  " order by datetrx ";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				// Proceso facturas
				MPayment payment = new MPayment(getCtx(), rs.getInt("c_payment_id"), null);
				
				this.procesoFacturas(payment);				
				
			}
			
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
		return "OK";
	}

	private void procesoFacturas(MPayment payment) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = " select c_invoice_id, c_currency_id, amtallocated " +
				  " from UY_AllocDirectPayment " +
				  " where c_payment_id =" + payment.get_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				// Proceso impuestos
				MInvoice invoice = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), null);
				
				this.procesoImpuestos(payment, invoice);
				
			}
			
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		

		
	}

	private void procesoImpuestos(MPayment payment, MInvoice invoice) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			BigDecimal tcInvoice = MConversionRate.getRate(100, 142, invoice.getDateInvoiced(), 0, 1000006, 0);
			BigDecimal tcPayment = MConversionRate.getRate(100, 142, payment.getDateTrx(), 0, 1000006, 0);
			
			sql = " select (coalesce(taxbaseamt,0) + coalesce(taxamt,0)) as importe, c_tax_id from c_invoicetax where c_invoice_id =" + invoice.get_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			boolean hayDatos = false;
			
			
			while (rs.next()){
				
				hayDatos = true;
				
				BigDecimal amtInvoiceTaxMN = rs.getBigDecimal("importe").multiply(tcInvoice).setScale(2, RoundingMode.HALF_UP);					
				BigDecimal amtPaymentTaxMN = rs.getBigDecimal("importe").multiply(tcPayment).setScale(2, RoundingMode.HALF_UP);					

				BigDecimal amtDiff = amtPaymentTaxMN.subtract(amtInvoiceTaxMN);
				BigDecimal amtDCIVA = Env.ZERO;
				
				
				MTax tax = new MTax(getCtx(), rs.getInt("c_tax_id"), null);

				if (tax.getValue().equalsIgnoreCase("basico")){
					BigDecimal ii = amtDiff.divide(new BigDecimal(1.22), 2, RoundingMode.HALF_UP); 
					amtDCIVA = amtDiff.subtract(ii); 
					amtDiff = ii;
				}
				
				String action = " insert into aux_dc (c_payment_id, c_invoice_id, docpay, docinv, datepay, dateinv," +
						        " tcpay, tcinv, amtdc, amtiva) " +
						        " values (" + payment.get_ID() + "," + invoice.get_ID() + ",'" + payment.getDocumentNo() + "','" +
						        invoice.getDocumentNo() + "','" + payment.getDateTrx() + "','" + invoice.getDateInvoiced() + "'," +
						        tcPayment + "," + tcInvoice + "," + amtDiff + "," + amtDCIVA + ")";
				DB.executeUpdateEx(action, null);
			}

			if (!hayDatos){
				BigDecimal amtInvoiceMN = invoice.getGrandTotal().multiply(tcInvoice).setScale(2, RoundingMode.HALF_UP);					
				//BigDecimal amtPaymentMN = payment.getPayAmt().multiply(tcPayment).setScale(2, RoundingMode.HALF_UP);	
				BigDecimal amtPaymentMN = invoice.getGrandTotal().multiply(tcPayment).setScale(2, RoundingMode.HALF_UP);

				BigDecimal amtDiff = amtPaymentMN.subtract(amtInvoiceMN);
				BigDecimal amtDCIVA = Env.ZERO;
				
				String action = " insert into aux_dc (c_payment_id, c_invoice_id, docpay, docinv, datepay, dateinv," +
				        " tcpay, tcinv, amtdc, amtiva) " +
				        " values (" + payment.get_ID() + "," + invoice.get_ID() + ",'" + payment.getDocumentNo() + "','" +
				        invoice.getDocumentNo() + "','" + payment.getDateTrx() + "','" + invoice.getDateInvoiced() + "'," +
				        tcPayment + "," + tcInvoice + "," + amtDiff + "," + amtDCIVA + ")";
				DB.executeUpdateEx(action, null);

			}
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		

		
	}

}
