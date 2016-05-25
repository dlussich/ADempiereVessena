package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.eevolution.model.MHRPayroll;
import org.eevolution.model.MHRPeriod;

public class MHRProcess extends X_UY_HRProcess {

	private static final long serialVersionUID = 1L;

	public MHRProcess(Properties ctx, int UY_HRProcess_ID, String trxName) {
		super(ctx, UY_HRProcess_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MHRProcess(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 21/06/2013. #1053		
		String sql = "";
		
		//OpenUp. Nicolas Sarlabos. 01/04/2015. #3237.
		if(!newRecord){
			
			if(is_ValueChanged("Name") || is_ValueChanged("HR_Payroll_ID") || is_ValueChanged("HR_Period_ID") || is_ValueChanged("IsAnual")
					|| is_ValueChanged("IsActive")){
				
				MHRProcesoNomina pn = MHRProcesoNomina.forProcess(getCtx(), this.get_ID(), get_TrxName()); //obtengo liquidacion de nomina si la hay
				
				if(pn!=null && pn.get_ID()>0) throw new AdempiereException("No se pueden modificar los datos de esta liquidacion, por estar asociada a la liquidacion" +
						" de nomina N° " + pn.getDocumentNo());			
				
			}		
			
		}
		//Fin OpenUp.
		
		MHRPeriod period = new MHRPeriod (getCtx(),this.getHR_Period_ID(),get_TrxName()); //obtengo periodo
		MHRPayroll payroll = MHRPayroll.forValue(getCtx(), "1000002"); //obtengo tipo de liquidacion "Aguinaldo"

		if(this.getHR_Payroll_ID()==payroll.get_ID()){ //si es una liquidacion de aguinaldo

			sql = "select uy_hrprocess_id" +
					" from uy_hrprocess p" +
					" where hr_payroll_id = " + payroll.get_ID() + " and hr_period_id = " + this.getHR_Period_ID() +
					" and p.ad_client_id = " + this.getAD_Client_ID() + " and ad_org_id = " + this.getAD_Org_ID() +
					" and p.uy_hrprocess_id <> " + this.get_ID();
			int ID = DB.getSQLValueEx(get_TrxName(), sql);

			if(ID>0) throw new AdempiereException ("Ya existe una liquidacion de aguinaldo para el periodo: " + period.getName());

		}
		//Fin OpenUp. #1053

		//se validan fechas
		if (this.getdatepayment().compareTo(this.getDateTrx()) < 0) throw new AdempiereException ("La Fecha de Pago debe ser mayor o igual a: " + '"' + this.getDateTrx() + '"');
		//se setea la fecha contable = fecha del documento
		this.setDateAcct(this.getDateTrx());
		
		return true;
	}

	

}
