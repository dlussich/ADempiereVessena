package org.openup.cfe.provider.sisteco;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.openup.cfe.CFEMessages;
import org.openup.cfe.InterfaceCFEDTO;
import org.openup.cfe.XMLProviderConverter;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEEmpresasType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocFact;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocFactExp;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocRem;
import org.openup.model.MCFEDataEnvelope;
import org.openup.model.MCFEDocCFE;
import org.openup.model.MCFESistecoSRspCFE;

public class SistecoXMLProviderConverter extends XMLProviderConverter {

	ArrayList<InterfaceCFEDTO> genericDtos;
	private int ad_client;
	
	public SistecoXMLProviderConverter(ArrayList<InterfaceCFEDTO> genericDtos, int ad_client, Properties ctx, String trxName) {
		super(genericDtos, ad_client, ctx, trxName);

		this.ad_client = ad_client;
		
		this.genericDtos = genericDtos;
		
		for (InterfaceCFEDTO cfeDto : genericDtos) {
			//invoicyDtos.add(mapDto(cfeDto.getCFEDTO()));
		}
		
	}

	@Override
	public void sendCFE() {
		
		for (InterfaceCFEDTO cfeDto : genericDtos) {
		
			CFEDefType cfeDefType = cfeDto.getCFEDTO();
			
			// Elimino el nodo emisor y timestamp de la firma según documentación de Sisteco
			
			if (cfeDefType.getEFact() != null) {
				if (cfeDefType.getEFact().getEncabezado() != null) {
					cfeDefType.getEFact().getEncabezado().setEmisor(null);
					IdDocFact idDocFact = cfeDefType.getEFact().getEncabezado().getIdDoc();
					if (idDocFact != null) {
						idDocFact.setMntBruto(null);
					}
				}
				cfeDefType.getEFact().setTmstFirma(null);
			}
			if (cfeDefType.getEFactExp() != null) {
				if (cfeDefType.getEFactExp().getEncabezado() != null) {
					cfeDefType.getEFactExp().getEncabezado().setEmisor(null);
					IdDocFactExp idDocFact = cfeDefType.getEFactExp().getEncabezado().getIdDoc();
					if (idDocFact != null) {
						idDocFact.setMntBruto(null);
					} 
				}
				cfeDefType.getEFactExp().setTmstFirma(null);
			}
			if (cfeDefType.getERem() != null) {
				if (cfeDefType.getERem().getEncabezado() != null) {
					cfeDefType.getERem().getEncabezado().setEmisor(null);
				}
				cfeDefType.getERem().setTmstFirma(null);
			}
			if (cfeDefType.getERemExp() != null){
				if (cfeDefType.getERemExp().getEncabezado() != null) {
					cfeDefType.getERemExp().getEncabezado().setEmisor(null);
				}
				cfeDefType.getERem().setTmstFirma(null);
			}
			if (cfeDefType.getEResg() != null) {
				if (cfeDefType.getEResg().getEncabezado() != null) {
					cfeDefType.getEResg().getEncabezado().setEmisor(null);
				}
				cfeDefType.getEResg().setTmstFirma(null);
			}
			if (cfeDefType.getETck() != null) {
				if (cfeDefType.getETck().getEncabezado() != null) {
					cfeDefType.getETck().getEncabezado().setEmisor(null);
				}
				cfeDefType.getETck().setTmstFirma(null);
			}
			
			
			
			
			SistecoCommunication sistecoCommunication = new SistecoCommunication(cfeDefType, getCtx(), get_TrxName());
			sistecoCommunication.Send();
			
			// Persistir por envio de CFE
			
			SistecoResponseDTO cfeDtoSisteco = sistecoCommunication.getSistecoResponseDTO();
			
			// Si la respuesta contiene errores, lanzo una excepción
			if (cfeDtoSisteco.getStatus() != 0) {
				throw new AdempiereException(CFEMessages.CFE_ERROR_PROVEEDOR + cfeDtoSisteco.getDescripcion());
			}
			
			
			MCFEDataEnvelope mCfeDataEnvelope = new MCFEDataEnvelope(getCtx(), 0, get_TrxName());
			mCfeDataEnvelope.setProviderAgent(MCFEDataEnvelope.PROVIDERAGENT_Sisteco);
			mCfeDataEnvelope.saveEx();

			PO docPo = (PO) cfeDto;
			MCFEDocCFE docCfe = new MCFEDocCFE(getCtx(), 0, get_TrxName());
			docCfe.setAD_Table_ID(docPo.get_Table_ID());
			docCfe.setRecord_ID(docPo.get_ID());
			docCfe.setUY_CFE_DataEnvelope_ID(mCfeDataEnvelope.get_ID());
			try {
				docCfe.setC_DocType_ID(BigDecimal.valueOf(docPo.get_ValueAsInt("C_DocTypeTarget_ID")));
				docCfe.setDocumentNo(docPo.get_ValueAsString("documentNo"));
			} catch (Exception e2) {}
			docCfe.saveEx();

			
			MCFESistecoSRspCFE sistecoCfeResp = new MCFESistecoSRspCFE(getCtx(), 0, get_TrxName());
			sistecoCfeResp.setCFEStatus(String.valueOf(cfeDtoSisteco.getStatus()));
			sistecoCfeResp.setCFEDescripcion(cfeDtoSisteco.getDescripcion());
			if (sistecoCfeResp.getCFEStatus().equalsIgnoreCase("0")) {
				sistecoCfeResp.setCFETipo(BigDecimal.valueOf(cfeDtoSisteco.getTipoCFE()));
				sistecoCfeResp.setCFESerie(cfeDtoSisteco.getSerie());
				sistecoCfeResp.setCFEMro(cfeDtoSisteco.getMro());
				//sistecoCfeResp.setCFETmstFirma(cfeDtoSisteco.getTmstFirma());
				sistecoCfeResp.setCFEDigestValue(cfeDtoSisteco.getDigestValue());
				sistecoCfeResp.setCFEResolucion(String.valueOf(cfeDtoSisteco.getResolucion()));
				sistecoCfeResp.setCFEAnioResolucion(BigDecimal.valueOf(cfeDtoSisteco.getAnioResolucion()));
				sistecoCfeResp.setCFEUrlDocumentoDGI(cfeDtoSisteco.getUrlDocumentoDGI());
				sistecoCfeResp.setCFECAEID(cfeDtoSisteco.getCaeId());
				sistecoCfeResp.setCFEDNro(cfeDtoSisteco.getdNro());
				sistecoCfeResp.setCFEHNro(cfeDtoSisteco.gethNro());
				//sistecoCfeResp.setCFEFecVenc(cfeDtoSisteco.getFecVenc());
			}
			sistecoCfeResp.setUY_CFE_DocCFE_ID(docCfe.get_ID());
			sistecoCfeResp.saveEx();
			
		}
		
	}

}
