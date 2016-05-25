/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MDocType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MTRBudget;
import org.openup.model.MTRBudgetLine;
import org.openup.model.MTRTrip;

/**
 * @author Nicolas
 *
 */
public class PTRLoadTrip extends SvrProcess {
	
	private int lineID = 0;
	private MTRBudgetLine bLine = null;

	/**
	 * 
	 */
	public PTRLoadTrip() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_TR_BudgetLine_ID")){
					this.lineID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
		
		this.bLine = new MTRBudgetLine(getCtx(),this.lineID,get_TrxName());

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		try{

			if (this.bLine.get_ID() <= 0){
				throw new AdempiereException("No se pudo obtener modelo de Linea de Cotizacion.");
			}
			
			MTRBudget budget = (MTRBudget)this.bLine.getUY_TR_Budget();
			
			MTRTrip trip = new MTRTrip(getCtx(),0,get_TrxName());
			trip.setC_DocType_ID(MDocType.forValue(getCtx(), "trexpediente", null).get_ID());
			trip.setDateTrx(new Timestamp(System.currentTimeMillis()));
			trip.setUY_TR_Way_ID(budget.getUY_TR_Way_ID());
			trip.setIsConsolidated(this.bLine.isConsolidated());
			trip.setIsRepresentation(this.bLine.isRepresentation());
			trip.setRepresentado_ID(this.bLine.getRepresentado_ID());
			trip.setM_Product_ID(this.bLine.getM_Product_ID());
			trip.setC_Currency_ID(this.bLine.getC_Currency_ID());
			trip.setProductAmt(this.bLine.getProductAmt());
			trip.setWeight(this.bLine.getWeight());
			trip.setWeight2(this.bLine.getWeight2());
			trip.setVolume(this.bLine.getVolume());
			trip.setUY_TR_PackageType_ID(this.bLine.getUY_TR_PackageType_ID());
			trip.setQtyPackage(this.bLine.getQtyPackage());
			trip.setIncotermType(budget.getIncotermType());
			trip.setIsDangerous(this.bLine.isDangerous());
			trip.setOnuNO(this.bLine.getOnuNO());
			trip.setSatisfied(false);
			trip.setUY_TR_BudgetLine_ID(lineID);
			trip.setC_Currency_ID_2(budget.getC_Currency_ID());
			trip.setAmount(this.bLine.getAmount2());
						
			//seteo el importador y exportador
			if(budget.getPartnerType().equalsIgnoreCase("IMP")){
				
				trip.setC_BPartner_ID_From(budget.getC_BPartner_ID_From());
				
				if (budget.getC_BPartner_ID_To()>0) trip.setC_BPartner_ID_To(budget.getC_BPartner_ID_To());   
				
				trip.setReceiptMode("DESTINO");
					
			} else {
				
				trip.setC_BPartner_ID_To(budget.getC_BPartner_ID_From());
				
				if (budget.getC_BPartner_ID_To()>0) trip.setC_BPartner_ID_From(budget.getC_BPartner_ID_To());
				
				trip.setReceiptMode("ORIGEN");
			}		
			
			//seteo ciudades
			if(budget.getCityType().equalsIgnoreCase("O")){
				
				trip.setUY_Ciudad_ID(budget.getUY_Ciudad_ID());
				trip.setUY_Ciudad_ID_1(this.bLine.getUY_Ciudad_ID());				
				
			} else {
				
				trip.setUY_Ciudad_ID_1(budget.getUY_Ciudad_ID());
				trip.setUY_Ciudad_ID(this.bLine.getUY_Ciudad_ID());				
				
			}			
			
			trip.setUY_TR_Budget_ID(budget.get_ID());
			trip.saveEx();
			
			this.bLine.setUY_TR_Trip_ID(trip.get_ID());
			this.bLine.saveEx();	
			
			ADialog.info(0,null,"Expediente N° " + trip.getDocumentNo() + " creado con exito");

		} 	catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}		

		return "OK";
	}

}
