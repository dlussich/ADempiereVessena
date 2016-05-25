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
import org.compiere.model.MConversionRate;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.MTax;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MAllocation;

/**
 * org.openup.process - PLoadDC2
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 13/02/2015
 * @see
 */
public class PLoadDC2 extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PLoadDC2() {

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

			DB.executeUpdateEx(" delete from aux_dc2 ", null);
			
			sql = " select l.c_payment_id, l.uy_allocation_id " +
				  " from uy_allocationpayment l inner join uy_allocation h on l.uy_allocation_id = h.uy_allocation_id " +
				  " where h.ad_client_id = 1000006 " +
				  " and h.docstatus='CO' " +
				  " and h.issotrx='N' " +
				  " and h.datetrx between '2015-01-01 00:00:00' and '2015-01-31 00:00:00' " +
				  " and l.c_payment_id > 0 " +
				  " and exists (select c_invoice_id from uy_allocationinvoice where uy_allocation_id = h.uy_allocation_id and c_doctype_id = 1000202) " +
				  " union " +
				  " select l.c_payment_id, l.uy_allocation_id" +
				  " from uy_allocationpayment l inner join uy_allocation h on l.uy_allocation_id = h.uy_allocation_id " +
				  " where h.ad_client_id = 1000006 " +
				  " and h.docstatus='CO' " +
				  " and h.issotrx='Y' " +
				  " and h.datetrx between '2015-01-01 00:00:00' and '2015-01-31 00:00:00' " +
				  " and l.c_payment_id > 0 ";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				// Proceso facturas
				MPayment payment = new MPayment(getCtx(), rs.getInt("c_payment_id"), null);
				MAllocation allocation = new MAllocation(getCtx(), rs.getInt("uy_allocation_id"), null); 
				
				this.procesoFacturas(payment, allocation);				
				
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

	private void procesoFacturas(MPayment payment, MAllocation allocation) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = " select c_invoice_id, amtallocated " +
				  " from uy_allocationinvoice " +
				  " where uy_allocation_id =" + allocation.get_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()){
				
				// Proceso impuestos
				MInvoice invoice = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), null);
				
				this.procesoImpuestos(payment, invoice, allocation, rs.getBigDecimal("amtallocated"));
				
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

	private void procesoImpuestos(MPayment payment, MInvoice invoice, MAllocation allocation, BigDecimal amtallocated) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			String esventa = "Y";
			if (!invoice.isSOTrx()) esventa = "N";
			
			BigDecimal tcInvoice = MConversionRate.getRate(100, 142, invoice.getDateInvoiced(), 0, 1000006, 0);
			BigDecimal tcPayment = MConversionRate.getRate(100, 142, payment.getDateTrx(), 0, 1000006, 0);
			
			if (tcInvoice == null){
				throw new AdempiereException("Falta tasa de cambio para fecha :" + invoice.getDateInvoiced());
			}

			if (tcPayment == null){
				throw new AdempiereException("Falta tasa de cambio para fecha :" + payment.getDateTrx());
			}
			
			
			BigDecimal tcDiff = tcPayment.subtract(tcInvoice);
			
			sql = " select (coalesce(taxbaseamt,0) + coalesce(taxamt,0)) as importe, c_tax_id from c_invoicetax where c_invoice_id =" + invoice.get_ID();

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			boolean hayDatos = false;
			
			
			while (rs.next()){
				
				hayDatos = true;
				
				BigDecimal amtDiff = rs.getBigDecimal("importe").multiply(tcDiff).setScale(2, RoundingMode.HALF_UP);					
				BigDecimal amtDCIVA = Env.ZERO;
				
				MTax tax = new MTax(getCtx(), rs.getInt("c_tax_id"), null);

				if (tax.getValue().equalsIgnoreCase("basico")){
					BigDecimal ii = amtDiff.divide(new BigDecimal(1.22), 2, RoundingMode.HALF_UP); 
					amtDCIVA = amtDiff.subtract(ii); 
					amtDiff = ii;
				}
				
				
				String action = " insert into aux_dc2 (c_payment_id, c_invoice_id, docpay, docinv, datepay, dateinv," +
						        " tcpay, tcinv, amtdc, amtiva, esventa) " +
						        " values (" + payment.get_ID() + "," + invoice.get_ID() + ",'" + payment.getDocumentNo() + "','" +
						        invoice.getDocumentNo() + "','" + payment.getDateTrx() + "','" + invoice.getDateInvoiced() + "'," +
						        tcPayment + "," + tcInvoice + "," + amtDiff + "," + amtDCIVA + ",'" + esventa + "')";
				DB.executeUpdateEx(action, null);
			}

			if (!hayDatos){

				//BigDecimal amtInvoiceMN = invoice.getGrandTotal().multiply(tcInvoice).setScale(2, RoundingMode.HALF_UP);					
				BigDecimal amtDiff = amtallocated.multiply(tcDiff).setScale(2, RoundingMode.HALF_UP);
				BigDecimal amtDCIVA = Env.ZERO;
				
				String action = " insert into aux_dc2 (c_payment_id, c_invoice_id, docpay, docinv, datepay, dateinv," +
				        " tcpay, tcinv, amtdc, amtiva, esventa) " +
				        " values (" + payment.get_ID() + "," + invoice.get_ID() + ",'" + payment.getDocumentNo() + "','" +
				        invoice.getDocumentNo() + "','" + payment.getDateTrx() + "','" + invoice.getDateInvoiced() + "'," +
				        tcPayment + "," + tcInvoice + "," + amtDiff + "," + amtDCIVA + ",'" + esventa + "')";
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
