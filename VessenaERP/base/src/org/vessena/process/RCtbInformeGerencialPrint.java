/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 27/11/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * org.openup.process - RCtbInformeGerencial
 * OpenUp Ltda. Issue #116 
 * Description: Informe Gerencial
 * @author Gabriel Vila - 27/11/2012
 * @see
 */
public class RCtbInformeGerencialPrint extends SvrProcess {

	private int cPeriodFromID = 0;
	private int cPeriodToID = 0;
	private String currencyType = "";
	private String idReporte = "";
	private boolean cargarMoldes = true;
	
	/**
	 * Constructor.
	 */
	public RCtbInformeGerencialPrint() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 27/11/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;

		// Parametro para tipo de moneda
		ProcessInfoParameter paramTipoMoneda = null;
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
				if (name.equalsIgnoreCase("C_Period_ID")){
					if (para[i].getParameter()!=null)
						this.cPeriodFromID = ((BigDecimal)para[i].getParameter()).intValueExact();
					if (para[i].getParameter_To()!=null)
						this.cPeriodToID = ((BigDecimal)para[i].getParameter_To()).intValueExact();
				}
				if (name.equalsIgnoreCase("CurrencyType")){
					paramTipoMoneda = para[i];
					if (para[i].getParameter()!=null)
						this.currencyType = (String)para[i].getParameter();
				}
				
				if (name.equalsIgnoreCase("CargarMoldes")){
					this.cargarMoldes = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}

			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(getAD_User_ID()));

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);

		// Parametro Tipo de Moneda para mostrar en el reporte
		if (paramTipoMoneda!=null)
			paramTipoMoneda.setParameter(this.currencyType);
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 27/11/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		if (this.cargarMoldes){
			InformeGerencial report = new InformeGerencial(getAD_User_ID(), getAD_Client_ID(), this.cPeriodFromID, 
					this.cPeriodToID, this.currencyType, this.idReporte);

			report.setWaiting(this.getProcessInfo().getWaiting());
			report.execute(0);
		}
		
		return "OK";

	}



	
}