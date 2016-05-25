/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/11/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MProduct;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MResguardoGen;
import org.openup.model.MResguardoGenDet;
import org.openup.model.MResguardoGenDoc;
import org.openup.model.MResguardoGenLine;
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
public class PSetResguardosGen extends SvrProcess {

	private int uyResguardoGenID = 0;
	
	/**
	 * Constructor.
	 */
	public PSetResguardosGen() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 21/11/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		this.uyResguardoGenID = this.getRecord_ID();
		
//		ProcessInfoParameter[] para = getParameter();
//
//		for (int i = 0; i < para.length; i++)
//		{
//			String name = para[i].getParameterName().trim();
//			if (name!= null){
//				if (name.equalsIgnoreCase("UY_ResguardoGen_ID"))
//					this.uyResguardoGenID = ((BigDecimal)para[i].getParameter()).intValueExact();
//			}
//		}
		
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
		
		BigDecimal divrate = new BigDecimal("1.00");
		BigDecimal compare = new BigDecimal("0");
		// Obtengo documentos seleccionados en este resguardo
		MResguardoGen resgGen = new MResguardoGen(getCtx(), uyResguardoGenID, get_TrxName());
		int[] arreBPs = resgGen.getBPIds();
		int size = arreBPs.length;
		int cont = 0;
		
		if (size == 0) {
			throw new AdempiereException("No hay comprobantes de compra seleccionados.");
		}

		while(cont < size){
			int resgIdTemp = cont+1;
			int partnerId = arreBPs[cont];
			//Se traen todos los documentos en el resguardo por BP
			List<MResguardoGenDoc> resginvs = resgGen.getResgGenDocByBP(partnerId);
			BigDecimal totPerInv = new BigDecimal(0);
			
			if (resginvs.size() <= 0) {
				throw new AdempiereException("No hay comprobantes de compra seleccionados para BP: " + partnerId);
			}
			
			
			MBPartner vendor = new MBPartner(getCtx(), partnerId, get_TrxName());
			List<MVendorRetention> vendrets = vendor.getRetentions(); 

			if (vendrets.size() > 0) {
				
				// Recorro cada retencion para ver si aplica o no segun total de los comprobantes seleccionados
				for (MVendorRetention vendret: vendrets){						
					int compareRes;
					
					MRetention retention = (MRetention)vendret.getUY_Retention();
					
					BigDecimal amtResguardo = Env.ZERO, amtResguardoME = Env.ZERO;
					
//					BigDecimal totAmtResguardoLine = Env.ZERO, totAmtResguardoMELine = Env.ZERO;
					
					for (MResguardoGenDoc resginv : resginvs){
						
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						
						BigDecimal invAmtResguardo = Env.ZERO, invAmtResguardoME = Env.ZERO;
						BigDecimal invMontoBase = Env.ZERO, invMontoBaseME = Env.ZERO;
						
						BigDecimal totPerInvTemp = new BigDecimal(0);
						
						// Sumo comprobantes (facturas suman y notas de credito restan) en moneda nacional y extranjera
						BigDecimal subtotalComprobantes = Env.ZERO, totalComprobantes = Env.ZERO, totalIVA = Env.ZERO;
						BigDecimal subtotalComprobantesME = Env.ZERO, totalComprobantesME = Env.ZERO, totalIVAME = Env.ZERO;
						
						// Moneda del esquema contable de la empresa
						MClient client2 = new MClient(getCtx(), resginv.getAD_Client_ID(), null);
						MAcctSchema schema2 = client2.getAcctSchema();
						
						if (resginv.getC_Currency_ID() != schema2.getC_Currency_ID()){
							divrate = new BigDecimal("0.00"); //aca va el tipo de cambio
						}
						
						MDocType doc = new MDocType(getCtx(), resginv.getC_DocType_ID(), null);
						if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){
							
							// Moneda Nacional
							subtotalComprobantes = subtotalComprobantes.add(resginv.getTotalLines());
							totalComprobantes = totalComprobantes.add(resginv.getGrandTotal());
							totalIVA = resginv.getAmtIVA();
							
							// Moneda Extranjera
							subtotalComprobantesME = subtotalComprobantesME.add(resginv.getTotalLinesSource());
							totalComprobantesME = totalComprobantesME.add(resginv.getGrandTotalSource());
							totalIVAME = resginv.getAmtIVA().multiply(divrate);
						}
						else{
							
							// Moneda Nacional
							subtotalComprobantes = subtotalComprobantes.subtract(resginv.getTotalLines());
							totalComprobantes = totalComprobantes.subtract(resginv.getGrandTotal());
							totalIVA = resginv.getAmtIVA();
											
							// Moneda Extranjera
							subtotalComprobantesME = subtotalComprobantesME.subtract(resginv.getTotalLinesSource());
							totalComprobantesME = totalComprobantesME.subtract(resginv.getGrandTotalSource());
							totalIVAME = resginv.getAmtIVA().multiply(divrate);
						}
						
						// Moneda del esquema contable de la empresa
						MClient client = new MClient(getCtx(), resginv.getAD_Client_ID(), null);
						MAcctSchema schema = client.getAcctSchema();
						
						// Convierto monto comprobantes en unidades indexadas
						MCurrency curUNI = MCurrency.get(getCtx(), "UNI");
						if (curUNI.get_ID() <= 0) throw new AdempiereException("No se pudo obtener moneda para Unidad Indexada.");
						
						//Se cabio resginvGen.getDateTrx() por resginvGen.getDateInvoiced() ya que no tenemos trxDate
						BigDecimal rateUNI = MConversionRate.getRate(schema.getC_Currency_ID(), curUNI.get_ID(), resginv.getDateInvoiced(), 0, resginv.getAD_Client_ID(), 0);
						if (rateUNI == null) rateUNI = Env.ZERO;
						
						if(retention.getUY_Linea_Negocio_ID() == 0){
							
							if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Subtotal)){
								invMontoBase = subtotalComprobantes;
								invMontoBaseME = subtotalComprobantesME;
							}
							else if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Total)){
								invMontoBase = totalComprobantes;
								invMontoBaseME = totalComprobantesME;
							}
							else if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Iva)){
								invMontoBase = totalIVA;
								invMontoBaseME = totalIVAME;
							}
			
							// Si la retencion es por unidades indexadas
							if (retention.isUnidadIndexada()){
								
								BigDecimal montoUNI = invMontoBase.multiply(rateUNI).setScale(2, RoundingMode.HALF_UP);
			
								// Si el monto de comprobantes supera o iguala el tope minimo
								if (montoUNI.compareTo(retention.getMaxAmt()) >= 0){
									invAmtResguardo = this.calculateAmtResguardo(retention, invMontoBase);
									invAmtResguardoME = this.calculateAmtResguardo(retention, invMontoBaseME);
								}
							}
							else{
								if (invMontoBase.compareTo(retention.getMaxAmt()) >= 0){
									invAmtResguardo = this.calculateAmtResguardo(retention, invMontoBase);
									invAmtResguardoME = this.calculateAmtResguardo(retention, invMontoBaseME);
								}
							}
							
							MResguardoGenDet resGenDet = new MResguardoGenDet(getCtx(), 0, get_TrxName());
							resGenDet.setAD_Client_ID(resginv.getAD_Client_ID());
							resGenDet.setAD_Org_ID(resginv.getAD_Org_ID());
							resGenDet.setUY_ResguardoGenDoc_ID(resginv.get_ID());
							resGenDet.setUY_Retention_ID(retention.get_ID());
							resGenDet.setamtretentionsource(invAmtResguardoME);
							resGenDet.setAmtRetention(invAmtResguardo.multiply(divrate));
							resGenDet.saveEx();
							
							//Emi Sumar al tot
							amtResguardo = amtResguardo.add(invAmtResguardo);
							amtResguardoME = amtResguardoME.add(invAmtResguardoME);
						}else{
							//mi nuevo metodo Tengo que generar un 
							//M_Invoice con el C_Invoice_Id y hacer get
							BigDecimal amtResguardoLine = Env.ZERO, amtResguardoMELine = Env.ZERO;
							MInvoice minv = new MInvoice(getCtx(), resginv.getC_Invoice_ID(), null);
							MInvoiceLine[] invLines = minv.getLines();
							int retLN = 0, retPG = 0, retFId = 0, retSFId = 0;
							int tam = invLines.length;
							int familyLevel = 0;
							if(retention.getUY_Linea_Negocio_ID() > 0){
								retLN = retention.getUY_Linea_Negocio_ID();
								familyLevel = 1;
								if(retention.getUY_ProductGroup_ID() > 0){
									retPG = retention.getUY_ProductGroup_ID();
									familyLevel = 2;
									if(retention.getUY_Familia_ID() > 0){
										retFId = retention.getUY_Familia_ID();
										familyLevel = 3;
										if(retention.getUY_SubFamilia_ID() > 0){
											retSFId = retention.getUY_SubFamilia_ID();
											familyLevel = 4;
										}
									}
								}								
							}
							for(int i=0; i<tam; i++){
								int lineLN = 0, linePG = 0, lineFId = 0, lineSFId = 0;
								BigDecimal montoBaseLine = Env.ZERO, montoBaseMELine = Env.ZERO;
								MProduct mProd = invLines[i].getProduct();
								int prodFamilyLevel = 0;
								if(mProd.getUY_Linea_Negocio_ID() > 0){
									lineLN = mProd.getUY_Linea_Negocio_ID();
									prodFamilyLevel = 1;
									if(mProd.get_ValueAsInt("uy_productgroup_id") > 0){
										linePG = mProd.get_ValueAsInt("uy_productgroup_id");
										prodFamilyLevel = 2;
										if(mProd.getUY_Familia_ID() > 0){
											lineFId = mProd.getUY_Familia_ID();
											prodFamilyLevel = 3;
											if(mProd.getUY_SubFamilia_ID() > 0){
												lineSFId = mProd.getUY_SubFamilia_ID();
												prodFamilyLevel = 4;
											}
										}
									}								
								}
								if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Subtotal)){
									montoBaseLine = invLines[i].getLineNetAmt();
									montoBaseMELine = invLines[i].getLineNetAmt(); 
								}
								else if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Total)){
									montoBaseLine = invLines[i].getLineTotalAmt();
									montoBaseMELine = invLines[i].getLineTotalAmt();
								}
								else if (retention.getConceptValue().equalsIgnoreCase(X_UY_Retention.CONCEPTVALUE_Iva)){
									montoBaseLine = invLines[i].getTaxAmt();
									montoBaseMELine = invLines[i].getTaxAmt(); 
								}
								//Con Nico L. se decide hacer el calculo del resguardo linea por linea
								if(familyLevel == prodFamilyLevel && retLN == lineLN && retPG == linePG
										&& retFId == lineFId && retSFId == lineSFId){
									
									amtResguardoLine = this.calculateAmtResguardo(retention, montoBaseLine);
									amtResguardoMELine = this.calculateAmtResguardo(retention, montoBaseMELine);
									
									MResguardoGenDet resGenDet = new MResguardoGenDet(getCtx(), 0, get_TrxName());
									resGenDet.setAD_Client_ID(resginv.getAD_Client_ID());
									resGenDet.setAD_Org_ID(resginv.getAD_Org_ID());
									resGenDet.setUY_ResguardoGenDoc_ID(resginv.get_ID());
									resGenDet.setUY_Retention_ID(retention.get_ID());
									resGenDet.setamtretentionsource(amtResguardoMELine);
									resGenDet.setAmtRetention(amtResguardoLine.multiply(divrate));
									resGenDet.saveEx();
									
									//Emi Hacer la lineaDet y sumar al tot
									amtResguardo = amtResguardo.add(amtResguardoLine);
									amtResguardoME = amtResguardoME.add(amtResguardoMELine);
								
								}
							}
						}
						
						//Actualizar el retAMt
						String qry = "select sum(amtretentionsource), sum(amtretention) "
										+ "from UY_ResguardoGenDet where uy_resguardogendoc_id = " + resginv.get_ID();
						
						pstmt = DB.prepareStatement (qry, get_TrxName());
						rs = pstmt.executeQuery();
						rs.next();
						
						resginv.setamtretentionsource(rs.getBigDecimal(1));
						resginv.setAmtRetention(rs.getBigDecimal(2));
						resginv.saveEx();	
				
					}
					// Genero resguardo line y aplico tasa 
					MResguardoGenLine line = new MResguardoGenLine(getCtx(), 0, get_TrxName());
					line.setUY_ResguardoGen_ID(resgGen.get_ID());
//					line.setUY_ResguardoGenDoc_ID(resginv.get_ID());
					line.setC_BPartner_ID(partnerId);
					line.setAmt(amtResguardo.multiply(divrate));
					line.setAmtSource(amtResguardoME);
					line.setUY_Retention_ID(retention.get_ID());
					line.saveEx();
				}	
				
			}
			cont++;
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
	
	/***
	 * Calcula por linea para un MInvoice y retorna monto de resguardo.
	 * OpenUp Ltda. Issue #5252 
	 * @author Emiliano Bentancor - 28/12/2015
	 * @see
	 * @param retention
	 * @param monto
	 * @return
	 */
	private BigDecimal calculateAmtResguardoPerLine(MRetention retention, BigDecimal monto){
		
		BigDecimal value = Env.ZERO;
		
		if (retention.getConceptPorc().compareTo(Env.ZERO) == 0){
			throw new AdempiereException("Porcentaje de Retencion en CERO.");
		}

		value = monto.multiply(retention.getConceptPorc()).setScale(2, RoundingMode.HALF_UP);
		
		return value;
	}	
	
}
