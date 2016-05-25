package org.openup.cfe;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.model.MCFEMessageLog;
import org.openup.model.MCFEMessageLogCFEDocs;
import org.openup.model.MCFEProviderConfig;

public class CFEManager {
	
	private MCFEProviderConfig mCfeProviderConfig;
	private Properties ctx;
	private String trxName;
	
	private int ad_client;

	private ArrayList<InterfaceCFEDTO> inCfeDtos; 
	
	public CFEManager(int ad_client, Properties ctx, String trxName) {
		if(MSysConfig.getBooleanValue("UY_CFE_ENABLE_SEND", false, ad_client)){
			mCfeProviderConfig = MCFEProviderConfig.getProviderConfig(ctx, trxName);
			if (mCfeProviderConfig == null) throw new AdempiereException("Error XMLProviderConverterFactory: No se ha definido proveedor para facturación electrónica");
			
			
			inCfeDtos = new ArrayList<InterfaceCFEDTO>();
			this.ctx = ctx;
			this.trxName = trxName;
			this.ad_client = ad_client;
		}
	}
	
	public void addCFE(InterfaceCFEDTO inCfeDto) {
		if(MSysConfig.getBooleanValue("UY_CFE_ENABLE_SEND", false, ad_client)){
			// Valido que el documento sea un documento a enviar
			if (inCfeDto.isDocumentoElectronico()) {
				inCfeDtos.add(inCfeDto);
			}
		}
	}
	
	public void SendCFE() {
		
		if(MSysConfig.getBooleanValue("UY_CFE_ENABLE_SEND", false, ad_client)){
			// Envio un sobre si hay documentos cargados
			if (inCfeDtos.size() > 0) {
			
				try {
					XMLProviderConverter xmlProviderConverter = XMLProviderConverterFactory.getXMLProciderConverter(mCfeProviderConfig, inCfeDtos, ad_client, ctx, trxName);
					
					// Si tengo definido en la configuracion algún proveedor de facturación electrónica realizo el envío
					if (xmlProviderConverter != null) {
						xmlProviderConverter.sendCFE();
					}
				} catch (Exception e) {
					if (mCfeProviderConfig.iscatchExceptions()) {
						
						MCFEMessageLog mCfeMessageLog = new MCFEMessageLog(ctx, 0, trxName);
						mCfeMessageLog.setMessage(e.getMessage());
						mCfeMessageLog.setDateTrx(new Timestamp(System.currentTimeMillis()));
						mCfeMessageLog.saveEx();
						
						for (InterfaceCFEDTO inCfeDto : inCfeDtos) {
							MCFEMessageLogCFEDocs mcfeMessageLogCFEDocs = new MCFEMessageLogCFEDocs(ctx, 0, trxName);
							PO poCfe = (PO) inCfeDto;
							
							mcfeMessageLogCFEDocs.setAD_Table_ID(poCfe.get_Table_ID());
							mcfeMessageLogCFEDocs.setRecord_ID(poCfe.get_ID());
							try {
								mcfeMessageLogCFEDocs.setC_DocType_ID(BigDecimal.valueOf(poCfe.get_ValueAsInt("C_DocTypeTarget_ID")));
								mcfeMessageLogCFEDocs.setDocumentNo(poCfe.get_ValueAsString("documentNo"));
							} catch (Exception e2) {}
							
							mcfeMessageLogCFEDocs.setUY_CFE_MessageLog_ID(mCfeMessageLog.get_ID());
							mcfeMessageLogCFEDocs.saveEx();
						}
					} else {
						throw(new AdempiereException(e));
					}
				}
			}
		}
	}
	
	/**
	 * Checkea si hay algún CFE para consultar su estado y si es así consulta en el proveedor de facturación electrónica parametrizado
	 * @param ctx Contexto del sistema
	 * @param trxName Transacción de Adempiere utilizada
	 * @param ad_client Compañia actual
	 */
	public static void queryCFEs(Properties ctx, String trxName, int ad_client) {
		MCFEProviderConfig mCfeProviderConfig = null;		
		if(MSysConfig.getBooleanValue("UY_CFE_ENABLE_SEND", false, ad_client)){
			try {
				mCfeProviderConfig = MCFEProviderConfig.getProviderConfig(ctx, trxName);
				if (mCfeProviderConfig == null) throw new AdempiereException("Error XMLProviderConverterFactory: No se ha definido proveedor para facturación electrónica");
				XMLProviderQuery xmlProviderQuery = XMLProviderConverterFactory.getXMLProviderQuery(mCfeProviderConfig, ad_client, ctx, trxName);
				
				// Si tengo definido en la configuracion algún proveedor de facturación electrónica realizo el envío
				if (xmlProviderQuery != null) {
					xmlProviderQuery.queryCFEs();
				}
			} catch (Exception e) {
					
				MCFEMessageLog mCfeMessageLog = new MCFEMessageLog(ctx, 0, trxName);
				mCfeMessageLog.setMessage(e.getMessage());
				mCfeMessageLog.setDateTrx(new Timestamp(System.currentTimeMillis()));
				mCfeMessageLog.saveEx();
				
			}
		}
	}

}
