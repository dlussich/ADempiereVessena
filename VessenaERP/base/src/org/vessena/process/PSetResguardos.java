/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/11/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.openup.model.MResguardo;
import org.openup.model.MResguardoGen;
import org.openup.model.MResguardoGenDoc;
import org.openup.model.MResguardoInvoice;
import org.openup.model.MResguardoLine;
import org.openup.model.MRetention;
import org.openup.model.MVendorRetention;
import org.openup.model.X_UY_Retention;

/**
 * org.openup.process - PSetResguardos
 * OpenUp Ltda. Issue #100 
 * Description: 
 * @author Gabriel Vila - 21/11/2012
 * @see
 */
public class PSetResguardos extends SvrProcess {

	private int uyResguardoID = 0;
	
	/**
	 * Constructor.
	 */
	public PSetResguardos() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/11/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_Resguardo_ID"))
					this.uyResguardoID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/11/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {
		
		// Obtengo documentos seleccionados en este resguardo
		MResguardo resg = new MResguardo(getCtx(), uyResguardoID, get_TrxName());
		List<MResguardoInvoice> resginvs = resg.getInvoices();
		
		if (resginvs.size() <= 0) {
			throw new AdempiereException("No hay comprobantes de compra seleccionados.");
		}

		// Sumo comprobantes (facturas suman y notas de credito restan) en moneda nacional y extranjera
		BigDecimal subtotalComprobantes = Env.ZERO, totalComprobantes = Env.ZERO, totalIVA = Env.ZERO;
		BigDecimal subtotalComprobantesME = Env.ZERO, totalComprobantesME = Env.ZERO, totalIVAME = Env.ZERO;
		
		for (MResguardoInvoice resginv : resginvs){
			MDocType doc = new MDocType(getCtx(), resginv.getC_DocType_ID(), null);
			if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){
				
				// Moneda Nacional
				subtotalComprobantes = subtotalComprobantes.add(resginv.getTotalLines());
				totalComprobantes = totalComprobantes.add(resginv.getGrandTotal());

				// Moneda Extranjera
				subtotalComprobantesME = subtotalComprobantesME.add(resginv.getTotalLinesSource());
				totalComprobantesME = totalComprobantesME.add(resginv.getGrandTotalSource());
			}
			else{
				
				// Moneda Nacional
				subtotalComprobantes = subtotalComprobantes.subtract(resginv.getTotalLines());
				totalComprobantes = totalComprobantes.subtract(resginv.getGrandTotal());
								
				// Moneda Extranjera
				subtotalComprobantesME = subtotalComprobantesME.subtract(resginv.getTotalLinesSource());
				totalComprobantesME = totalComprobantesME.subtract(resginv.getGrandTotalSource());
			}
		}

		// IVA
		totalIVA = totalComprobantes.subtract(subtotalComprobantes);
		totalIVAME = totalIVAME.subtract(totalComprobantesME.subtract(subtotalComprobantesME));
		
		// Obtengo tipos de retencion para el proveedor de este resguardo
		MBPartner vendor = (MBPartner)resg.getC_BPartner();
		List<MVendorRetention> vendrets = vendor.getRetentions(); 

		if (vendrets.size() <= 0) {
			throw new AdempiereException("No hay Retenciones asociadas a este Proveedor.");
		}

		// Elimino lineas de resguardos existentes antes de cargar nuevas
		resg.deleteLines();
		
		// Moneda del esquema contable de la empresa
		MClient client = new MClient(getCtx(), resg.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		
		// Convierto monto comprobantes en unidades indexadas
		MCurrency curUNI = MCurrency.get(getCtx(), "UNI");
		if (curUNI.get_ID() <= 0) throw new AdempiereException("No se pudo obtener moneda para Unidad Indexada.");
		
		BigDecimal rateUNI = MConversionRate.getRate(schema.getC_Currency_ID(), curUNI.get_ID(), resg.getDateTrx(), 0, resg.getAD_Client_ID(), 0);
		if (rateUNI == null) rateUNI = Env.ZERO;
		
		// Recorro cada retencion para ver si aplica o no segun total de los comprobantes seleccionados
		for (MVendorRetention vendret: vendrets){

			MRetention retention = (MRetention)vendret.getUY_Retention();
			BigDecimal amtResguardo = Env.ZERO, amtResguardoME = Env.ZERO;
			BigDecimal montoBase = Env.ZERO, montoBaseME = Env.ZERO;
			
			if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Subtotal)){
				montoBase = subtotalComprobantes;
				montoBaseME = subtotalComprobantesME;
			}
			else if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Total)){
				montoBase = totalComprobantes;
				montoBaseME = totalComprobantesME;
			}
			else if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Iva)){
				montoBase = totalIVA;
				montoBaseME = totalIVAME;
			}

			// Si la retencion es por unidades indexadas
			if (retention.isUnidadIndexada()){
				
				BigDecimal montoUNI = montoBase.multiply(rateUNI).setScale(2, RoundingMode.HALF_UP);

				// Si el monto de comprobantes supera o iguala el tope minimo
				if (montoUNI.compareTo(retention.getMaxAmt()) >= 0){
					amtResguardo = this.calculateAmtResguardo(retention, montoBase);
					amtResguardoME = this.calculateAmtResguardo(retention, montoBaseME);
				}
			}
			else{
				if (montoBase.compareTo(retention.getMaxAmt()) >= 0){
					amtResguardo = this.calculateAmtResguardo(retention, montoBase);
					amtResguardoME = this.calculateAmtResguardo(retention, montoBaseME);
				}
			}
			
			// Genero resguardo y aplico tasa 
			MResguardoLine line = new MResguardoLine(getCtx(), 0, get_TrxName());
			line.setUY_Resguardo_ID(resg.get_ID());
			line.setAmt(amtResguardo);
			line.setAmtSource(amtResguardoME);
			line.setUY_Retention_ID(retention.get_ID());
			line.saveEx();

		}
		
		return "OK";
	}

	/***
	 * Calcula y retorna monto de resguardo segun retencion y monto aplicable.
	 * OpenUp Ltda. Issue #100 
	 * @author Gabriel Vila - 21/11/2012
	 * @see
	 * @param retention
	 * @param monto
	 * @return
	 */
	private BigDecimal calculateAmtResguardo(MRetention retention, BigDecimal monto){
		
		BigDecimal value = Env.ZERO;
		
		if (retention.getConceptPorc().compareTo(Env.ZERO) == 0){
			throw new AdempiereException("Porcentaje de Retencion en CERO.");
		}

		value = monto.multiply(retention.getConceptPorc()).setScale(2, RoundingMode.HALF_UP);
		
		return value;
	}
	
}
