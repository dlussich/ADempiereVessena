/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MClient;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.beans.ReportOpenAmt;

/**
 * @author OpenUp. Gabriel Vila. 03/11/2011. Issue #902.
 * Informe y Proceso de Saldos Pendientes en formato Report View.
 */
public class RCtaCteOpenAmtRV extends SvrProcess {

	private ReportOpenAmt openAmtFilters = new ReportOpenAmt();
	
	public RCtaCteOpenAmtRV() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("fechaHasta")) this.openAmtFilters.dateTo = (Timestamp)para[i].getParameter();
				if (name.equalsIgnoreCase("UY_TipoMonedaReporte")) this.openAmtFilters.tipoMoneda = (String)para[i].getParameter();
				if (name.equalsIgnoreCase("C_Currency_ID")) this.openAmtFilters.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("tipoReporteSaldoPend")) this.openAmtFilters.tipoReporte = (String)para[i].getParameter();
				
				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					if (para[i].getParameter() != null) this.openAmtFilters.cBPGroupID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("IsDueDate")){
					if (para[i].getParameter() != null) this.openAmtFilters.isDueDate = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}
				
				if (name.equalsIgnoreCase("PartnerType")) 
					this.openAmtFilters.partnerType = (String)para[i].getParameter();
				
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter() != null) this.openAmtFilters.customerID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_BPartner_ID_P")){
					if (para[i].getParameter() != null) this.openAmtFilters.vendorID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("SalesRep_ID")){
					if (para[i].getParameter() != null) this.openAmtFilters.salesRepID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("UY_Collector_ID")){
					if (para[i].getParameter() != null) this.openAmtFilters.collectorID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("UY_CanalVentas_ID")){
					if (para[i].getParameter() != null) this.openAmtFilters.canalVentaID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("UY_Departamentos_ID")){
					if (para[i].getParameter() != null) this.openAmtFilters.departamentoID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("UY_Localidades_ID")){
					if (para[i].getParameter() != null) this.openAmtFilters.localidadID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}

				if (name.equalsIgnoreCase("AD_User_ID")) this.openAmtFilters.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Client_ID")) this.openAmtFilters.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Org_ID")) this.openAmtFilters.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}
		
		if (this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_TMN)){
			MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
			this.openAmtFilters.cCurrencyID = client.getAcctSchema().getC_Currency_ID();
		}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		RCtaCteOpenAmt report = new RCtaCteOpenAmt(this.openAmtFilters);
		report.setWaiting(this.getProcessInfo().getWaiting());
		report.execute();
		
		return "OK";
	}

}
