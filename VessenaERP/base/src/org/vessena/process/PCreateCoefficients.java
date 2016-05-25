/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFduCoefficientIpc;
import org.openup.model.MFduCoefficientLine;

/**
 * @author gbrust
 *
 */
public class PCreateCoefficients extends SvrProcess {
	
	private int UY_Fdu_CoefficientHdr_ID;

	/**
	 * 
	 */
	public PCreateCoefficients() {
		// TODO Auto-generated constructor stub
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
				if (name.equalsIgnoreCase("UY_Fdu_CoefficientHdr_ID")){
					if (para[i].getParameter()!=null)
						this.UY_Fdu_CoefficientHdr_ID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}			
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		//Se borran todas las lineas procesadas anteriormente
		DB.executeUpdateEx("DELETE FROM UY_Fdu_CoefficientIpc WHERE UY_Fdu_CoefficientHdr_ID=" + this.UY_Fdu_CoefficientHdr_ID, null);
		
		//OpenUp Ltda. Guillermo Brust. 14/01/2013
		//OpenUp Ltda. Actualizado Leonardo Boccone. 28/02/2013 #1019
		//Se realiza primero la taza mensual (T.E.M) a partir de la taza anual (T.E.A)
		//TEM = ((1 + (TEA / 100)) ^ 1/12 -1) . 100)
		//A partir de la TEM obtenemos el IPC
		//IPC = ((TEM / 100) . 1.22) / (1 - (1 / ((1 + ((TEM / 100) . 1.22)) ^ cantCuotas))
		
		BigDecimal tea = Env.ZERO;
		BigDecimal tem = Env.ZERO;
		BigDecimal temMasIva = Env.ZERO;
		
		BigDecimal coeficiente = Env.ZERO;		
		
		List<MFduCoefficientLine> coefLines = MFduCoefficientLine.getMFduCoefficientLineForHdrId(getCtx(), this.UY_Fdu_CoefficientHdr_ID);
		
		for (MFduCoefficientLine coefline : coefLines) {
			
			for (int i = 2; i <= 24; i++) { //i = cantidad de cuotas
				
				MFduCoefficientIpc ipc = new MFduCoefficientIpc(getCtx(), 0, get_TrxName());
				
				if(coefline.getCuota(i).compareTo(Env.ZERO) > 0){
									
					double o = 1, p = 12, exp = o/p;
					tea = coefline.getCuota(i).divide(new BigDecimal(100));				
					//tem = BigDecimal.valueOf(Math.pow((new BigDecimal(1).add(tea).doubleValue()), exp)).subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
					//temMasIva = tem.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(1.22));									
					//coeficiente = temMasIva.divide(new BigDecimal(1).subtract(new BigDecimal(1).divide((new BigDecimal(1).add(temMasIva)).pow(i), 4, RoundingMode.HALF_UP)), 4, RoundingMode.HALF_UP);
					
					tem = BigDecimal.valueOf(Math.pow((new BigDecimal(1).add(tea).doubleValue()), exp)).subtract(new BigDecimal(1)).multiply(new BigDecimal(100));
					
					temMasIva = tem.multiply(new BigDecimal(1.22)).divide(new BigDecimal(100));
										
					coeficiente = temMasIva.divide(new BigDecimal(1).subtract(new BigDecimal(1).divide((new BigDecimal(1).add(temMasIva)).pow(i), 8, RoundingMode.HALF_UP)), 8, RoundingMode.HALF_UP);
													
					ipc.setUY_Fdu_CoefficientHdr_ID(coefline.getUY_Fdu_CoefficientHdr_ID());
					ipc.setUY_Fdu_CoefficientLine_ID(coefline.get_ID());
					ipc.settem(tem);
					ipc.setipc(coeficiente);
					ipc.setCuotaNo(String.valueOf(i));					
					ipc.saveEx();					
				}
				else{
					ipc.setUY_Fdu_CoefficientHdr_ID(coefline.getUY_Fdu_CoefficientHdr_ID());
					ipc.setUY_Fdu_CoefficientLine_ID(coefline.get_ID());
					ipc.settem(Env.ZERO);
					ipc.setipc(Env.ZERO);
					ipc.setCuotaNo(String.valueOf(i));					
					ipc.saveEx();	
							
				}
			}		
		}
		
		
		return "ok";
	}

}
