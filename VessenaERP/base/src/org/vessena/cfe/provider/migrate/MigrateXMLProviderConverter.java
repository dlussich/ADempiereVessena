package org.openup.cfe.provider.migrate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.openup.cfe.CFEMessages;
import org.openup.cfe.CfeUtils;
import org.openup.cfe.InterfaceCFEDTO;
import org.openup.cfe.XMLProviderConverter;
import org.openup.cfe.CfeUtils.CfeType;
import org.openup.cfe.provider.migrate.dto.CFEInvoiCyCollectionType;
import org.openup.cfe.provider.migrate.dto.CFEInvoiCyType;
import org.openup.cfe.provider.migrate.dto.CFEInvoiCyType.Emisor;
import org.openup.cfe.provider.migrate.dto.CFEInvoiCyType.IdDoc;
import org.openup.cfe.provider.migrate.dto.CFEInvoiCyType.Receptor;
import org.openup.cfe.provider.migrate.dto.CFEInvoiCyType.Totales;
import org.openup.cfe.provider.migrate.dto.EnvioCFE;
import org.openup.cfe.provider.migrate.dto.EnvioCFERetorno;
import org.openup.cfe.provider.migrate.dto.ListaCFERetornoType;
import org.openup.cfe.provider.migrate.dto.TipMonType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.DscRcgGlobal;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.DscRcgGlobal.DRGItem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocFact;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocTck;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ItemDetFact;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ItemRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.MediosPago;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Referencia;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.RetPerc;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.SubTotInfo;
import org.openup.model.MCFEDataDocument;
import org.openup.model.MCFEDataEnvelope;
import org.openup.model.MCFEDocCFE;
import org.openup.model.MCFEInvoicySRspCFE;
import org.openup.model.MCFEInvoicySRspCFEMsg;
import org.openup.model.MCFEInvoicySend;

import com.google.common.collect.BiMap;

/**
 * Clase encargada de generar el dto de Invoicy a partir de el dto de DGI que se utiliza como dto genérico
 * 
 * @author Raúl Capecce
 * @see http://www.invoicy.com.uy/ES/Invoicy/news/ version 1.9.9.0 Siguiendo el documento Manual_de_Integracion_XML_InvoiCy_v1.9.9.0.pdf
 */
public class MigrateXMLProviderConverter extends XMLProviderConverter {

	List<CFEInvoiCyType> invoicyDtos;
	ArrayList<InterfaceCFEDTO> genericDtos;
	EnvioCFE envioCfe;
	
	public MigrateXMLProviderConverter(ArrayList<InterfaceCFEDTO> genericDtos, int ad_client, Properties ctx, String trxName) {
		super(genericDtos, ad_client, ctx, trxName);
		
		this.genericDtos = genericDtos;
		
		envioCfe = new EnvioCFE();
		envioCfe.setCFE(new CFEInvoiCyCollectionType());
		invoicyDtos = envioCfe.getCFE().getCFEItem();
		
		for (InterfaceCFEDTO cfeDto : genericDtos) {
			invoicyDtos.add(mapDto(cfeDto.getCFEDTO()));
		}
		
	}

	/**
	 * Mapea el dto de DGI que se toma como genérico al dto de Invoicy
	 * @param genericDto dto de DGI utilizado de forma genérica
	 * @return dto de Invoicy cargados con los datos del dto genérico
	 */
	protected CFEInvoiCyType mapDto (CFEDefType genericDto) {
		CFEInvoiCyType invoicyDto = new CFEInvoiCyType();
		int cfeType = getCfeTypeGenericDto(genericDto);
		
		loadIdDoc(cfeType, genericDto, invoicyDto);
		loadDatosEmisor(cfeType, genericDto, invoicyDto);
		loadDatosReceptor(cfeType, genericDto, invoicyDto);
		loadTotales(cfeType, genericDto, invoicyDto);
		loadItems(cfeType, genericDto, invoicyDto);
		loadSubTotalesInfo(cfeType, genericDto, invoicyDto);
		loadDscRcgGlobal(cfeType, genericDto, invoicyDto);
		loadMediosPago(cfeType, genericDto, invoicyDto);
		loadReferencia(cfeType, genericDto, invoicyDto);
		
		
		
		return invoicyDto;
	}
	
	/**
	 * Obtengo el código de CFE del dto genérico
	 * @param genericDto dto genérico
	 * @return int con el tipo de cfe
	 */
	protected int getCfeTypeGenericDto(CFEDefType genericDto) {
		
		int cfeType = 0;
		
		// Evaluo Factura, NC y ND
		try {
			cfeType = genericDto.getEFact().getEncabezado().getIdDoc().getTipoCFE().intValue();
		} catch (Exception e) { }
		
		// Si no es ninguno de los anteriores -> Evaluo si es Ticket, NC y ND
		if (cfeType == 0) {
			try {
				cfeType = genericDto.getETck().getEncabezado().getIdDoc().getTipoCFE().intValue();
			} catch (Exception e) { }
		}
		
		// Si no es ninguno de los anteriores -> Evaluo si es Remito
		if (cfeType == 0) {
			try {
				cfeType = genericDto.getERem().getEncabezado().getIdDoc().getTipoCFE().intValue();
			} catch (Exception e) { }
		}
		
		// Si no es ninguno de los anteriores -> Evaluo si es Resguardo
		if (cfeType == 0) {
			try {
				cfeType = genericDto.getEResg().getEncabezado().getIdDoc().getTipoCFE().intValue();
			} catch (Exception e) { }
		}
		
		return cfeType;
	}
	
	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * @see Manual_de_Integracion_XML_InvoiCy_v1.9.9.0.pdf
	 * Pagina 8 Seccion Identificacion del CFE
	 */
	protected void loadIdDoc(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		IdDoc invoicyIdDoc = new IdDoc();
		invoicyDto.setIdDoc(invoicyIdDoc);
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes(); 
		
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
			
			if (genericDto.getEFact().getEncabezado() != null && genericDto.getEFact().getEncabezado().getIdDoc() != null) {
			
				IdDocFact genericIdDoc = genericDto.getEFact().getEncabezado().getIdDoc();
				
				/*   9 */ invoicyIdDoc.setCFETipoCFE(BigInteger.valueOf(cfeType));
				/*  10 */ invoicyIdDoc.setCFESerie(genericIdDoc.getSerie());
				/*  11 */ invoicyIdDoc.setCFENro(genericIdDoc.getNro());
				/*  12    Dato no obligatorio */
				/*  13    Dato no obligatorio */
				/*  14    Dato no obligatorio */
				/*  15 */ invoicyIdDoc.setCFEFchEmis(genericIdDoc.getFchEmis());
				/*  16    Dato no obligatorio */
				/*  17    Dato no obligatorio */
				/*  18 */ invoicyIdDoc.setCFEMntBruto(genericIdDoc.getMntBruto());
				/*  19 */ invoicyIdDoc.setCFEFmaPago(genericIdDoc.getFmaPago());
				/*  20 */ invoicyIdDoc.setCFEFchVenc(genericIdDoc.getFchVenc());
				/*  21    Dato no obligatorio aca, es obligatorio en Remitos */
				/*  22    Dato no obligatorio */
				/*  23    Dato no obligatorio */
				/*  24    Dato no obligatorio */
				/*  25    Dato no obligatorio */
				/*  26    Dato no obligatorio aca, es obligatorio en Ticket*/
				/*  27    Dato no obligatorio */
				/*  28    Dato no obligatorio */
				/*  29    Dato no obligatorio */
				/*  30    Dato no obligatorio */
				/*  31    Dato no obligatorio */
				/*  32    Dato no obligatorio aca, obligatorio para Factura NC y ND de exportación */
				/*  33    Dato no obligatorio aca, obligatorio para tipos de comprobante de exportación */
				/*  34    Dato no obligatorio aca, obligatorio para tipos de comprobante de exportación */
				/*  35    Dato no obligatorio */
				/*  36    Dato no obligatorio */
				/*  37    Dato no obligatorio */
				/*  38    Dato no obligatorio */
				/*  39    Dato no obligatorio */
				
			}
		} else if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))
				) {
			IdDocTck genericIdDoc = genericDto.getETck().getEncabezado().getIdDoc();
			
			/*   9 */ invoicyIdDoc.setCFETipoCFE(BigInteger.valueOf(cfeType));
			/*  10 */ invoicyIdDoc.setCFESerie(genericIdDoc.getSerie());
			/*  11 */ invoicyIdDoc.setCFENro(genericIdDoc.getNro());
			/*  12    Dato no obligatorio */
			/*  13    Dato no obligatorio */
			/*  14    Dato no obligatorio */
			/*  15 */ invoicyIdDoc.setCFEFchEmis(genericIdDoc.getFchEmis());
			/*  16    Dato no obligatorio */
			/*  17    Dato no obligatorio */
			/*  18 */ invoicyIdDoc.setCFEMntBruto(genericIdDoc.getMntBruto());
			/*  19 */ invoicyIdDoc.setCFEFmaPago(genericIdDoc.getFmaPago());
			/*  20 */ invoicyIdDoc.setCFEFchVenc(genericIdDoc.getFchVenc());
			/*  21    Dato no obligatorio aca, es obligatorio en Remitos */
			/*  22    Dato no obligatorio */
			/*  23    Dato no obligatorio */
			/*  24    Dato no obligatorio */
			/*  25    Dato no obligatorio */
			/*  26    Dato no obligatorio aca, es obligatorio en Ticket*/
			/*  27    Dato no obligatorio */
			/*  28    Dato no obligatorio */
			/*  29    Dato no obligatorio */
			/*  30    Dato no obligatorio */
			/*  31    Dato no obligatorio */
			/*  32    Dato no obligatorio aca, obligatorio para Factura NC y ND de exportación */
			/*  33    Dato no obligatorio aca, obligatorio para tipos de comprobante de exportación */
			/*  34    Dato no obligatorio aca, obligatorio para tipos de comprobante de exportación */
			/*  35    Dato no obligatorio */
			/*  36    Dato no obligatorio */
			/*  37    Dato no obligatorio */
			/*  38    Dato no obligatorio */
			/*  39    Dato no obligatorio */
			
		} else if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eRemito))
				) {
			IdDocRem genericIdDoc = genericDto.getERem().getEncabezado().getIdDoc();
			
			/*   9 */ invoicyIdDoc.setCFETipoCFE(BigInteger.valueOf(cfeType));
			/*  10 */ invoicyIdDoc.setCFESerie(genericIdDoc.getSerie());
			/*  11 */ invoicyIdDoc.setCFENro(genericIdDoc.getNro());
			/*  12    Dato no obligatorio */
			/*  13    Dato no obligatorio */
			/*  14    Dato no obligatorio */
			/*  15 */ invoicyIdDoc.setCFEFchEmis(genericIdDoc.getFchEmis());
			/*  16    Dato no obligatorio */
			/*  17    Dato no obligatorio */
			/*  18    Dato no obligatorio en remitos*/
			/*  19    Dato no obligatorio en remitos */
			/*  20    Dato no obligatorio en remitos */
			/*  21 */ invoicyIdDoc.setCFETipoTraslado(genericIdDoc.getTipoTraslado());
			/*  22    Dato no obligatorio */
			/*  23    Dato no obligatorio */
			/*  24    Dato no obligatorio */
			/*  25    Dato no obligatorio */
			/*  26    Dato no obligatorio aca, es obligatorio en Ticket*/
			/*  27    Dato no obligatorio */
			/*  28    Dato no obligatorio */
			/*  29    Dato no obligatorio */
			/*  30    Dato no obligatorio */
			/*  31    Dato no obligatorio */
			/*  32    Dato no obligatorio aca, obligatorio para Factura NC y ND de exportación */
			/*  33    Dato no obligatorio aca, obligatorio para tipos de comprobante de exportación */
			/*  34    Dato no obligatorio aca, obligatorio para tipos de comprobante de exportación */
			/*  35    Dato no obligatorio */
			/*  36    Dato no obligatorio */
			/*  37    Dato no obligatorio */
			/*  38    Dato no obligatorio */
			/*  39    Dato no obligatorio */
			
		} // TODO: Agregar para soportar otros tipos de CFE
	}
	
	
	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * @see Manual_de_Integracion_XML_InvoiCy_v1.9.9.0.pdf
	 * Pagina 12 Seccion Datos del Emisor
	 */
	protected void loadDatosEmisor(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		Emisor invoicyEmisor = new Emisor();
		invoicyDto.setEmisor(invoicyEmisor);
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes(); 
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
			
			if (genericDto.getEFact().getEncabezado() != null && genericDto.getEFact().getEncabezado().getEmisor() != null) {
			
				org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Emisor genericEmisor = genericDto.getEFact().getEncabezado().getEmisor();
				
				/*  41 */ invoicyEmisor.setEmiRznSoc(genericEmisor.getRznSoc());
				/*  42 */ invoicyEmisor.setEmiComercial(genericEmisor.getNomComercial());
				/*  43    Dato no obligatorio */
				if (genericEmisor.getTelefono() != null && genericEmisor.getTelefono().size() > 0) {
					/*  44 */ invoicyEmisor.setEmiTelefono(genericEmisor.getTelefono().get(0));
				}
				if (genericEmisor.getTelefono() != null && genericEmisor.getTelefono().size() > 1) {
					/*  45 */ invoicyEmisor.setEmiTelefono2(genericEmisor.getTelefono().get(1));
				}
				/*  46 */ invoicyEmisor.setEmiCorreoEmisor(genericEmisor.getCorreoEmisor());
				/*  47 */ invoicyEmisor.setEmiSucursal(genericEmisor.getEmiSucursal());
				/*  48 */ invoicyEmisor.setEmiDomFiscal(genericEmisor.getDomFiscal());
				/*  49 */ invoicyEmisor.setEmiCiudad(genericEmisor.getCiudad());
				/*  50 */ invoicyEmisor.setEmiDepartamento(genericEmisor.getDepartamento());
				/*  51    Dato no obligatorio */
			
			}
		} else if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))
				) {
			if (genericDto.getETck().getEncabezado() != null && genericDto.getETck().getEncabezado().getEmisor() != null) {
				
				org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Emisor genericEmisor = genericDto.getETck().getEncabezado().getEmisor();
				
				/*  41 */ invoicyEmisor.setEmiRznSoc(genericEmisor.getRznSoc());
				/*  42 */ invoicyEmisor.setEmiComercial(genericEmisor.getNomComercial());
				/*  43    Dato no obligatorio */
				if (genericEmisor.getTelefono() != null && genericEmisor.getTelefono().size() > 0) {
					/*  44 */ invoicyEmisor.setEmiTelefono(genericEmisor.getTelefono().get(0));
				}
				if (genericEmisor.getTelefono() != null && genericEmisor.getTelefono().size() > 1) {
					/*  45 */ invoicyEmisor.setEmiTelefono2(genericEmisor.getTelefono().get(1));
				}
				/*  46 */ invoicyEmisor.setEmiCorreoEmisor(genericEmisor.getCorreoEmisor());
				/*  47 */ invoicyEmisor.setEmiSucursal(genericEmisor.getEmiSucursal());
				/*  48 */ invoicyEmisor.setEmiDomFiscal(genericEmisor.getDomFiscal());
				/*  49 */ invoicyEmisor.setEmiCiudad(genericEmisor.getCiudad());
				/*  50 */ invoicyEmisor.setEmiDepartamento(genericEmisor.getDepartamento());
				/*  51    Dato no obligatorio */
			
			}
		} else if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eRemito))
				) {
			if (genericDto.getERem().getEncabezado() != null && genericDto.getERem().getEncabezado().getEmisor() != null) {
				
				org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Emisor genericEmisor = genericDto.getERem().getEncabezado().getEmisor();
				
				/*  41 */ invoicyEmisor.setEmiRznSoc(genericEmisor.getRznSoc());
				/*  42 */ invoicyEmisor.setEmiComercial(genericEmisor.getNomComercial());
				/*  43    Dato no obligatorio */
				if (genericEmisor.getTelefono() != null && genericEmisor.getTelefono().size() > 0) {
					/*  44 */ invoicyEmisor.setEmiTelefono(genericEmisor.getTelefono().get(0));
				}
				if (genericEmisor.getTelefono() != null && genericEmisor.getTelefono().size() > 1) {
					/*  45 */ invoicyEmisor.setEmiTelefono2(genericEmisor.getTelefono().get(1));
				}
				/*  46 */ invoicyEmisor.setEmiCorreoEmisor(genericEmisor.getCorreoEmisor());
				/*  47 */ invoicyEmisor.setEmiSucursal(genericEmisor.getEmiSucursal());
				/*  48 */ invoicyEmisor.setEmiDomFiscal(genericEmisor.getDomFiscal());
				/*  49 */ invoicyEmisor.setEmiCiudad(genericEmisor.getCiudad());
				/*  50 */ invoicyEmisor.setEmiDepartamento(genericEmisor.getDepartamento());
				/*  51    Dato no obligatorio */
			
			}
		}
	}

	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * @see Manual_de_Integracion_XML_InvoiCy_v1.9.9.0.pdf
	 * Pagina 13 Seccion Datos del Receptor
	 */
	protected void loadDatosReceptor(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		Receptor invoicyReceptor = new Receptor();
		invoicyDto.setReceptor(invoicyReceptor);
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes();
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
			
			if (genericDto.getEFact().getEncabezado() != null && genericDto.getEFact().getEncabezado().getReceptor() != null) {
				
				org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorFact genericReceptorFact = genericDto.getEFact().getEncabezado().getReceptor();
				
				/*  53 */ invoicyReceptor.setRcpTipoDocRecep(genericReceptorFact.getTipoDocRecep());
				/*  54    Dato no obligatorio aca, obligatorio cuando el documento es 4 - Otro */
				/*  55 */ invoicyReceptor.setRcpCodPaisRecep(genericReceptorFact.getCodPaisRecep());
				/*  56 */ invoicyReceptor.setRcpDocRecep(genericReceptorFact.getDocRecep());
				/*  57 */ invoicyReceptor.setRcpRznSocRecep(genericReceptorFact.getRznSocRecep());
				/*  58 */ invoicyReceptor.setRcpDirRecep(genericReceptorFact.getDirRecep());
				/*  59 */ invoicyReceptor.setRcpCiudadRecep(genericReceptorFact.getCiudadRecep());
				/*  60 */ invoicyReceptor.setRcpDeptoRecep(genericReceptorFact.getDeptoRecep());
				if (genericReceptorFact.getCP() != null) {
					/*  61 */ invoicyReceptor.setRcpCP(genericReceptorFact.getCP().toString());
				}
				/*  62    Dato no obligatorio */
				/*  63 */ invoicyReceptor.setRcpInfAdiRecep(genericReceptorFact.getInfoAdicional());
				/*  64 */ invoicyReceptor.setRcpDirPaisRecep(genericReceptorFact.getPaisRecep());
				/*  65 */ invoicyReceptor.setRcpDstEntregaRecep(genericReceptorFact.getLugarDestEnt());
				/*  66    Dato no obligatorio */
				
			}
		} else if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))) {
			
			if (genericDto.getETck().getEncabezado() != null && genericDto.getETck().getEncabezado().getReceptor() != null) {
				
				org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorTck genericReceptorFact = genericDto.getETck().getEncabezado().getReceptor();
				
				/*  53 */ invoicyReceptor.setRcpTipoDocRecep(genericReceptorFact.getTipoDocRecep());
				/*  54    Dato no obligatorio aca, obligatorio cuando el documento es 4 - Otro */
				/*  55 */ invoicyReceptor.setRcpCodPaisRecep(genericReceptorFact.getCodPaisRecep());
				
				// En eTicket revisa que se este mandando el numero de documento del receptor en otro campo
				String recepNumDoc = "";
				if (genericReceptorFact.getDocRecep() != null) {
					recepNumDoc = genericReceptorFact.getDocRecep();
				} else {
					recepNumDoc = genericReceptorFact.getDocRecepExt();
				}
				/*  56 */ invoicyReceptor.setRcpDocRecep(recepNumDoc);
				
				
				/*  57 */ invoicyReceptor.setRcpRznSocRecep(genericReceptorFact.getRznSocRecep());
				/*  58 */ invoicyReceptor.setRcpDirRecep(genericReceptorFact.getDirRecep());
				/*  59 */ invoicyReceptor.setRcpCiudadRecep(genericReceptorFact.getCiudadRecep());
				/*  60 */ invoicyReceptor.setRcpDeptoRecep(genericReceptorFact.getDeptoRecep());
				if (genericReceptorFact.getCP() != null) {
					/*  61 */ invoicyReceptor.setRcpCP(genericReceptorFact.getCP().toString());
				}
				/*  62    Dato no obligatorio */
				/*  63 */ invoicyReceptor.setRcpInfAdiRecep(genericReceptorFact.getInfoAdicional());
				/*  64 */ invoicyReceptor.setRcpDirPaisRecep(genericReceptorFact.getPaisRecep());
				/*  65 */ invoicyReceptor.setRcpDstEntregaRecep(genericReceptorFact.getLugarDestEnt());
				/*  66    Dato no obligatorio */
				
			}
		} else if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eRemito))) {
			
			if (genericDto.getERem().getEncabezado() != null && genericDto.getERem().getEncabezado().getReceptor() != null) {
				
				org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorRem genericReceptorRem = genericDto.getERem().getEncabezado().getReceptor();
				
				/*  53 */ invoicyReceptor.setRcpTipoDocRecep(genericReceptorRem.getTipoDocRecep());
				/*  54    Dato no obligatorio aca, obligatorio cuando el documento es 4 - Otro */
				/*  55 */ invoicyReceptor.setRcpCodPaisRecep(genericReceptorRem.getCodPaisRecep());
				
				// En eTicket revisa que se este mandando el numero de documento del receptor en otro campo
				String recepNumDoc = "";
				if (genericReceptorRem.getDocRecep() != null) {
					recepNumDoc = genericReceptorRem.getDocRecep();
				} else {
					recepNumDoc = genericReceptorRem.getDocRecepExt();
				}
				/*  56 */ invoicyReceptor.setRcpDocRecep(recepNumDoc);
				
				
				/*  57 */ invoicyReceptor.setRcpRznSocRecep(genericReceptorRem.getRznSocRecep());
				/*  58 */ invoicyReceptor.setRcpDirRecep(genericReceptorRem.getDirRecep());
				/*  59 */ invoicyReceptor.setRcpCiudadRecep(genericReceptorRem.getCiudadRecep());
				/*  60 */ invoicyReceptor.setRcpDeptoRecep(genericReceptorRem.getDeptoRecep());
				if (genericReceptorRem.getCP() != null) {
					/*  61 */ invoicyReceptor.setRcpCP(genericReceptorRem.getCP().toString());
				}
				/*  62    Dato no obligatorio */
				/*  63 */ invoicyReceptor.setRcpInfAdiRecep(genericReceptorRem.getInfoAdicional());
				/*  64 */ invoicyReceptor.setRcpDirPaisRecep(genericReceptorRem.getPaisRecep());
				/*  65 */ invoicyReceptor.setRcpDstEntregaRecep(genericReceptorRem.getLugarDestEnt());
				/*  66    Dato no obligatorio */
				
			}
		}
	}
	
	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * Pagina 17 Seccion Montos Totales del CFE
	 */
	protected void loadTotales(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		Totales invoicyTotales = new Totales();
		invoicyDto.setTotales(invoicyTotales);
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes();
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
			
			if (genericDto.getEFact().getEncabezado() != null && genericDto.getEFact().getEncabezado().getTotales() != null) {
				
				org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Totales genericTotales = genericDto.getEFact().getEncabezado().getTotales();
				
				try {
					/*  75 */ invoicyTotales.setTotTpoMoneda(TipMonType.valueOf(genericTotales.getTpoMoneda().toString()));
				} catch (Exception e) {
					throw new AdempiereException("CFE OpenUp-Migrate Provider Error: Area Monto Totales del CFE (75) - Error al obtener la moneda del DTO Generico");
				}
				/*  76 */ invoicyTotales.setTotTpoCambio(genericTotales.getTpoCambio());
				/*  77 */ invoicyTotales.setTotMntNoGrv(genericTotales.getMntNoGrv());
				/*  78 */ invoicyTotales.setTotMntExpoyAsim(genericTotales.getMntExpoyAsim());
				/*  79 */ invoicyTotales.setTotMntImpuestoPerc(genericTotales.getMntImpuestoPerc());
				/*  80 */ invoicyTotales.setTotMntIVaenSusp(genericTotales.getMntIVaenSusp());
				/*  81 */ invoicyTotales.setTotMntNetoIvaTasaMin(genericTotales.getMntNetoIvaTasaMin());
				/*  82 */ invoicyTotales.setTotMntNetoIVATasaBasica(genericTotales.getMntNetoIVATasaBasica());
				/*  83 */ invoicyTotales.setTotMntNetoIVAOtra(genericTotales.getMntNetoIVAOtra());
				/*  84 */ invoicyTotales.setTotIVATasaMin(genericTotales.getIVATasaMin());
				/*  85 */ invoicyTotales.setTotIVATasaBasica(genericTotales.getIVATasaBasica());
				/*  86 */ invoicyTotales.setTotMntIVATasaMin(genericTotales.getMntIVATasaMin());
				/*  87 */ invoicyTotales.setTotMntIVATasaBasica(genericTotales.getMntIVATasaBasica());
				/*  88 */ invoicyTotales.setTotMntIVAOtra(genericTotales.getMntIVAOtra());
				/*  89 */ invoicyTotales.setTotMntTotal(genericTotales.getMntTotal());
				/*  90 */ invoicyTotales.setTotMntTotRetenido(genericTotales.getMntTotRetenido());
				/*  91    Dato no obligatorio */
				
				
				/* 115 */ invoicyTotales.setTotMntPagar(genericTotales.getMntPagar());
				
				
			}
		} else if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))) {
			
			if (genericDto.getETck().getEncabezado() != null && genericDto.getETck().getEncabezado().getTotales() != null) {
				
				org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Totales genericTotales = genericDto.getETck().getEncabezado().getTotales();
				
				try {
					/*  75 */ invoicyTotales.setTotTpoMoneda(TipMonType.valueOf(genericTotales.getTpoMoneda().toString()));
				} catch (Exception e) {
					throw new AdempiereException("CFE OpenUp-Migrate Provider Error: Area Monto Totales del CFE (75) - Error al obtener la moneda del DTO Generico");
				}
				/*  76 */ invoicyTotales.setTotTpoCambio(genericTotales.getTpoCambio());
				/*  77 */ invoicyTotales.setTotMntNoGrv(genericTotales.getMntNoGrv());
				/*  78 */ invoicyTotales.setTotMntExpoyAsim(genericTotales.getMntExpoyAsim());
				/*  79 */ invoicyTotales.setTotMntImpuestoPerc(genericTotales.getMntImpuestoPerc());
				/*  80 */ invoicyTotales.setTotMntIVaenSusp(genericTotales.getMntIVaenSusp());
				/*  81 */ invoicyTotales.setTotMntNetoIvaTasaMin(genericTotales.getMntNetoIvaTasaMin());
				/*  82 */ invoicyTotales.setTotMntNetoIVATasaBasica(genericTotales.getMntNetoIVATasaBasica());
				/*  83 */ invoicyTotales.setTotMntNetoIVAOtra(genericTotales.getMntNetoIVAOtra());
				/*  84 */ invoicyTotales.setTotIVATasaMin(genericTotales.getIVATasaMin());
				/*  85 */ invoicyTotales.setTotIVATasaBasica(genericTotales.getIVATasaBasica());
				/*  86 */ invoicyTotales.setTotMntIVATasaMin(genericTotales.getMntIVATasaMin());
				/*  87 */ invoicyTotales.setTotMntIVATasaBasica(genericTotales.getMntIVATasaBasica());
				/*  88 */ invoicyTotales.setTotMntIVAOtra(genericTotales.getMntIVAOtra());
				/*  89 */ invoicyTotales.setTotMntTotal(genericTotales.getMntTotal());
				/*  90 */ invoicyTotales.setTotMntTotRetenido(genericTotales.getMntTotRetenido());
				/*  91    Dato no obligatorio */
				
				
				/* 115 */ invoicyTotales.setTotMntPagar(genericTotales.getMntPagar());
				
				
			}
		}
	}
	
	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * Pagina 22 Seccion Detalle del CFE
	 */
	protected void loadItems(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		invoicyDto.setDetalle(new CFEInvoiCyType.Detalle());
		List<CFEInvoiCyType.Detalle.Item> invoicyItems = invoicyDto.getDetalle().getItem();
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes();
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))
				
				) {
			
			List<ItemDetFact> genericItems = null;
			
			if (cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))
				) {
				if (genericDto.getEFact().getDetalle() == null || genericDto.getEFact().getDetalle().getItem().size() == 0) {
					throw new AdempiereException(CFEMessages.INVOICY_DOCNOLINES);
				} else {
					genericItems = genericDto.getEFact().getDetalle().getItem();
				}
			} else if (cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))
				) {
				if (genericDto.getETck().getDetalle() == null || genericDto.getETck().getDetalle().getItem().size() == 0) {
					throw new AdempiereException(CFEMessages.INVOICY_DOCNOLINES);
				} else  {
					genericItems = genericDto.getETck().getDetalle().getItem();
				}
			}
			
			
			
			
			if (genericItems != null) {	
				for (ItemDetFact itemDetFact : genericItems) {
					
					/* 117 */CFEInvoiCyType.Detalle.Item invoicyItem = new CFEInvoiCyType.Detalle.Item();
					invoicyItems.add(invoicyItem);
					/* 118 */CFEInvoiCyType.Detalle.Item.CodItem codItem = new CFEInvoiCyType.Detalle.Item.CodItem();
					invoicyItem.setCodItem(codItem);
					
					
					for (ItemDetFact.CodItem genericCodItem : itemDetFact.getCodItem()) {
						/* 119 */CFEInvoiCyType.Detalle.Item.CodItem.CodItemItem codItemItem = new CFEInvoiCyType.Detalle.Item.CodItem.CodItemItem();
						codItem.getCodItemItem().add(codItemItem);
						
						/* 120 */ codItemItem.setIteCodiTpoCod(genericCodItem.getTpoCod());
						/* 121 */ codItemItem.setIteCodiCod(genericCodItem.getCod());
					}
					
					/* 122 */ invoicyItem.setIteIndFact(itemDetFact.getIndFact().intValue());
					/* 123 */ invoicyItem.setIteIndAgenteResp(itemDetFact.getIndAgenteResp());
					/* 124 */ invoicyItem.setIteNomItem(itemDetFact.getNomItem());
					/* 125 */ invoicyItem.setIteDscItem(itemDetFact.getDscItem());
					/* 126 */ invoicyItem.setIteCantidad(itemDetFact.getCantidad());
					/* 127 */ invoicyItem.setIteUniMed(itemDetFact.getUniMed());
					/* 128 */ invoicyItem.setItePrecioUnitario(itemDetFact.getPrecioUnitario());
					/* 129 */ invoicyItem.setIteDescuentoPct(itemDetFact.getDescuentoPct());
					/* 130 */ invoicyItem.setIteDescuentoMonto(itemDetFact.getDescuentoMonto());
					
					for (ItemDetFact.SubDescuento subDescuento : itemDetFact.getSubDescuento()) {
						CFEInvoiCyType.Detalle.Item.SubDescuento.SubDescuentoItem invoicySubDescuentoItem = new CFEInvoiCyType.Detalle.Item.SubDescuento.SubDescuentoItem();
						/* 133 */ invoicySubDescuentoItem.setSubDescDescTipo(subDescuento.getDescTipo());
						/* 134 */ invoicySubDescuentoItem.setSubDescDescVal(subDescuento.getDescVal());
						
						invoicyItem.getSubDescuento().getSubDescuentoItem().add(invoicySubDescuentoItem);
					}
					
					/* 135 */ invoicyItem.setIteRecargoPct(itemDetFact.getRecargoPct());
					/* 136 */ invoicyItem.setIteRecargoMnt(itemDetFact.getRecargoMnt());
					
					for (ItemDetFact.SubRecargo subRecargo : itemDetFact.getSubRecargo()) {
						CFEInvoiCyType.Detalle.Item.SubRecargo.SubRecargoItem invoicySubRecargoItem = new CFEInvoiCyType.Detalle.Item.SubRecargo.SubRecargoItem();
						/* 139 */ invoicySubRecargoItem.setSubRecaRecargoTipo(subRecargo.getRecargoTipo());
						/* 140 */ invoicySubRecargoItem.setSubRecaRecargoVal(subRecargo.getRecargoVal());
						
						invoicyItem.getSubRecargo().getSubRecargoItem().add(invoicySubRecargoItem);
					}
					
					for (RetPerc retPerc : itemDetFact.getRetencPercep()) {
						CFEInvoiCyType.Detalle.Item.RetencPercep.RetencPercepItem invoicyRetPerc = new CFEInvoiCyType.Detalle.Item.RetencPercep.RetencPercepItem();
						/* 143 */ invoicyRetPerc.setIteRetPercCodRet(retPerc.getCodRet());
						/* 144    Dato no obligatorio */
						if (retPerc.getValRetPerc() != null) {
							/* 145 */ invoicyRetPerc.setIteRetPercTasa(retPerc.getValRetPerc().toBigInteger());
						}
						/* 146 */ invoicyRetPerc.setIteRetPercMntSujetoaRet(retPerc.getMntSujetoaRet());
						/* 147 */ invoicyRetPerc.setIteRetPercValRetPerc(retPerc.getValRetPerc());
						/* 148    TODO: REVISAR: El dto no lo tiene pero Migrage Si */
						
						invoicyItem.getRetencPercep().getRetencPercepItem().add(invoicyRetPerc);
					}
					
					/* 149 */ invoicyItem.setIteMontoItem(itemDetFact.getMontoItem());
					
				}
			}
			
		} else if (cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eRemito))) {
			List<ItemRem> remItems = null;
			if (genericDto.getERem().getDetalle() == null || genericDto.getERem().getDetalle().getItem().size() == 0) {
				throw new AdempiereException(CFEMessages.INVOICY_DOCNOLINES);
			} else {
				remItems = genericDto.getERem().getDetalle().getItem();
			}
			
			
			if (remItems != null) {	
				for (ItemRem itemRem : remItems) {
					
					/* 117 */CFEInvoiCyType.Detalle.Item invoicyItem = new CFEInvoiCyType.Detalle.Item();
					invoicyItems.add(invoicyItem);
					/* 118 */CFEInvoiCyType.Detalle.Item.CodItem codItem = new CFEInvoiCyType.Detalle.Item.CodItem();
					invoicyItem.setCodItem(codItem);
					
					
					for (ItemRem.CodItem remCodItem : itemRem.getCodItem()) {
						/* 119 */CFEInvoiCyType.Detalle.Item.CodItem.CodItemItem codItemItem = new CFEInvoiCyType.Detalle.Item.CodItem.CodItemItem();
						codItem.getCodItemItem().add(codItemItem);
						
						/* 120 */ codItemItem.setIteCodiTpoCod(remCodItem.getTpoCod());
						/* 121 */ codItemItem.setIteCodiCod(remCodItem.getCod());
					}
					
					if (itemRem.getIndFact() != null) {
						/* 122 */ invoicyItem.setIteIndFact(itemRem.getIndFact().intValue());
					}
					/* 123    Dato no presente en el detalle de un remito */
					/* 124 */ invoicyItem.setIteNomItem(itemRem.getNomItem());
					/* 125 */ invoicyItem.setIteDscItem(itemRem.getDscItem());
					/* 126 */ invoicyItem.setIteCantidad(itemRem.getCantidad());
					/* 127 */ invoicyItem.setIteUniMed(itemRem.getUniMed());
					/* 128    Dato no presente en el detalle de un remito */
					/* 129    Dato no presente en el detalle de un remito */
					/* 130    Dato no presente en el detalle de un remito */
					
					// Lista de getSubDescuento()
					/* 133    Dato no presente en el detalle de un remito */
					/* 134    Dato no presente en el detalle de un remito */
						
					
					
					/* 135    Dato no presente en el detalle de un remito */
					/* 136    Dato no presente en el detalle de un remito */
					
					// Lista de getSubRecargo()
					/* 139    Dato no presente en el detalle de un remito */ 
					/* 140    Dato no presente en el detalle de un remito */
					
					
					// Lista de getRetencPercep()
					/* 143    Dato no presente en el detalle de un remito */
					/* 144    Dato no obligatorio */
					/* 145    Dato no presente en el detalle de un remito */
					/* 146    Dato no presente en el detalle de un remito */
					/* 147    Dato no presente en el detalle de un remito */
					/* 148    Dato no presente en el detalle de un remito */
					/* 149    Dato no presente en el detalle de un remito */
					
				}
			}
		}
	}
	
	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * Pagina 26 Seccion Subtotales Informativos
	 */
	protected void loadSubTotalesInfo(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		invoicyDto.setSubTotInfo(new CFEInvoiCyType.SubTotInfo());
		List<CFEInvoiCyType.SubTotInfo.STIItem> invoicySubTotales = invoicyDto.getSubTotInfo().getSTIItem();
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes();
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))
				) {
			
			List<SubTotInfo.STIItem> genericSubTotInfo = null;
			
			if (
					cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))
					) {
				if (genericDto.getEFact().getSubTotInfo() != null && genericDto.getEFact().getSubTotInfo().getSTIItem() != null) {
					genericSubTotInfo = genericDto.getEFact().getSubTotInfo().getSTIItem();
				}
			} else if (
					cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))
					) {
				
			}
			
			if (genericSubTotInfo != null) {
				
				for (SubTotInfo.STIItem subTotInfo : genericSubTotInfo) {
					CFEInvoiCyType.SubTotInfo.STIItem invoicySubTotInfo = new CFEInvoiCyType.SubTotInfo.STIItem();
					invoicySubTotales.add(invoicySubTotInfo);
					
					/* 152 */ invoicySubTotInfo.setSubTotNroSTI(subTotInfo.getNroSTI());
					/* 153 */ invoicySubTotInfo.setSubTotGlosaSTI(subTotInfo.getGlosaSTI());
					/* 154 */ invoicySubTotInfo.setSubTotOrdenSTI(subTotInfo.getOrdenSTI());
					/* 155 */ invoicySubTotInfo.setSubTotValSubtotSTI(subTotInfo.getValSubtotSTI());
					
				}
			}
		}
	}
	
	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * Pagina 26 Seccion Descuentos y Recargos Globales
	 */
	protected void loadDscRcgGlobal(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		invoicyDto.setDscRcgGlobal(new CFEInvoiCyType.DscRcgGlobal());
		List<CFEInvoiCyType.DscRcgGlobal.DRGItem> invoicyDscRcgGlobal = invoicyDto.getDscRcgGlobal().getDRGItem();
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes();
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))) {
			
			List<DRGItem> drgItems = null; 
			
			if (
					cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
				if (genericDto.getEFact().getDscRcgGlobal() != null) {
					drgItems = genericDto.getEFact().getDscRcgGlobal().getDRGItem();
				}
			} else if (
					cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
				if (genericDto.getETck().getDscRcgGlobal() != null) {
					drgItems = genericDto.getETck().getDscRcgGlobal().getDRGItem();
				}
			}
			
			if (drgItems != null) {
				
				for (DscRcgGlobal.DRGItem dscRcgGlobal : drgItems) {
					/* 157 */ CFEInvoiCyType.DscRcgGlobal.DRGItem invoicyDscRcgGlob = new CFEInvoiCyType.DscRcgGlobal.DRGItem();
					invoicyDscRcgGlobal.add(invoicyDscRcgGlob);
					
					/* 158 */ invoicyDscRcgGlob.setDscRcgNroLinDR(dscRcgGlobal.getNroLinDR());
					/* 159 */ invoicyDscRcgGlob.setDscRcgTpoMovDR(dscRcgGlobal.getTpoMovDR());
					/* 160 */ invoicyDscRcgGlob.setDscRcgTpoDR(dscRcgGlobal.getTpoDR());
					/* 161 */ invoicyDscRcgGlob.setDscRcgCodDR(dscRcgGlobal.getCodDR());
					/* 162 */ invoicyDscRcgGlob.setDscRcgGlosaDR(dscRcgGlobal.getGlosaDR());
					/* 163 */ invoicyDscRcgGlob.setDscRcgValorDR(dscRcgGlobal.getValorDR());
					if (dscRcgGlobal.getIndFactDR() != null) {
						/* 164 */ invoicyDscRcgGlob.setDscRcgIndFactDR(dscRcgGlobal.getIndFactDR().intValue());
					}
					
				}
			}
		}
	}
	
	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * Pagina 27 Seccion Medios de Pago
	 */
	protected void loadMediosPago(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		invoicyDto.setMediosPago(new CFEInvoiCyType.MediosPago());
		List<CFEInvoiCyType.MediosPago.MediosPagoItem> invoicyMediosPago = invoicyDto.getMediosPago().getMediosPagoItem();
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes();
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
			
			List<MediosPago.MedioPago> genericMedioPago = null;
			
			if (
					cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
				if (genericDto.getEFact().getMediosPago() != null) {
					genericMedioPago = genericDto.getEFact().getMediosPago().getMedioPago();
				}
			} else if (
					cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))) {
				if (genericDto.getETck().getMediosPago() != null) {
					genericMedioPago = genericDto.getETck().getMediosPago().getMedioPago();
				}
			}
			
			if (genericMedioPago != null) {
			
				for (MediosPago.MedioPago medioPago : genericMedioPago) {
					CFEInvoiCyType.MediosPago.MediosPagoItem invoicyMP = new CFEInvoiCyType.MediosPago.MediosPagoItem();
					invoicyMediosPago.add(invoicyMP);
					
					/* 167 */ invoicyMP.setMedPagNroLinMP(medioPago.getNroLinMP());
					/* 168 */ invoicyMP.setMedPagCodMP(medioPago.getCodMP());
					/* 169 */ invoicyMP.setMedPagGlosaMP(medioPago.getGlosaMP());
					/* 170 */ invoicyMP.setMedPagOrdenMP(medioPago.getOrdenMP());
					/* 171 */ invoicyMP.setMedPagValorPago(medioPago.getValorPago());
					
				}
			}
		}
	}
	
	/**
	 * 
	 * @param cfeType Identifica al tipo de documento electrónico
	 * @param genericDto dto genérico
	 * @param invoicyDto dgo de Invoicy
	 * Pagina 27 Seccion Referencia
	 */
	protected void loadReferencia(int cfeType, CFEDefType genericDto, CFEInvoiCyType invoicyDto) {
		invoicyDto.setReferencia(new CFEInvoiCyType.Referencia());
		List<CFEInvoiCyType.Referencia.ReferenciaItem> invoicyReferencias = invoicyDto.getReferencia().getReferenciaItem();
		BiMap<String, CfeType> cfeTypes = CfeUtils.getCfeTypes();
		
		if (
				cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
				|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))) {
			
			List<Referencia.Referencia1> genericReferencias = null;
			
			if (
					cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_NC))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eFactura_ND))) {
				if (genericDto.getEFact().getReferencia() != null) {	
					genericReferencias = genericDto.getEFact().getReferencia().getReferencia1();
				}
			} else if (
					cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_NC))
					|| cfeType == Integer.valueOf(cfeTypes.inverse().get(CfeUtils.CfeType.eTicket_ND))) {
				if (genericDto.getETck().getReferencia() != null) {	
					genericReferencias = genericDto.getETck().getReferencia().getReferencia1();
				}
			}
				
			if (genericReferencias != null) {
				
				for (Referencia.Referencia1 genericRef : genericReferencias) {
					/* 173 */ CFEInvoiCyType.Referencia.ReferenciaItem invoicyReferenciaItem = new CFEInvoiCyType.Referencia.ReferenciaItem();
					invoicyReferencias.add(invoicyReferenciaItem);
					
					/* 174 */ invoicyReferenciaItem.setRefNroLinRef(genericRef.getNroLinRef());
					/* 175 */ invoicyReferenciaItem.setRefIndGlobal(genericRef.getIndGlobal());
					/* 176 */ invoicyReferenciaItem.setRefTpoDocRef(genericRef.getTpoDocRef());
					/* 177 */ invoicyReferenciaItem.setRefSerie(genericRef.getSerie());
					/* 178 */ invoicyReferenciaItem.setRefNroCFERef(genericRef.getNroCFERef());
					/* 179 */ invoicyReferenciaItem.setRefRazonRef(genericRef.getRazonRef());
					/* 180 */ invoicyReferenciaItem.setRefFechaCFEref(genericRef.getFechaCFEref());
					
				}
			}
		}
	}
	
	@Override
	public void sendCFE() {
		
		try {
			
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			
			MigrateCommunication migrateCommunication = new MigrateCommunication(envioCfe, getAd_client(), getCtx(), get_TrxName());
			migrateCommunication.Send();
			
			
			MCFEDataEnvelope mCfeDataEnvelope = new MCFEDataEnvelope(getCtx(), 0, get_TrxName());
			mCfeDataEnvelope.setProviderAgent(MCFEDataEnvelope.PROVIDERAGENT_InvoiCy);
			mCfeDataEnvelope.saveEx();

			MCFEInvoicySend mCfeInvoicySend = new MCFEInvoicySend(getCtx(), 0, get_TrxName());
			mCfeInvoicySend.setUY_CFE_DataEnvelope_ID(mCfeDataEnvelope.get_ID());
			mCfeInvoicySend.setDateTrx(currentTimestamp);
			mCfeInvoicySend.setXMLSent(migrateCommunication.getGeneratedXml());
			mCfeInvoicySend.setXMLResponded(migrateCommunication.getResponseXml());
			mCfeInvoicySend.saveEx();
			

			List<ListaCFERetornoType.CFE> listaCfeRet = migrateCommunication.getEnvioCfeRetorno().getListaCFE().getCFE();
			for (ListaCFERetornoType.CFE cfeRet : listaCfeRet) {
				
				String valueDoc = cfeRet.getCFESerie() + cfeRet.getCFENro();
				MCFEDocCFE docCfe = null;
				for (InterfaceCFEDTO inCfeDto : getCfeDtos()) {
					PO docPo = (PO) inCfeDto;
					if (docPo.get_ValueAsString("documentNo").equalsIgnoreCase(valueDoc)) {
						docCfe = new MCFEDocCFE(getCtx(), 0, get_TrxName());
						docCfe.setAD_Table_ID(docPo.get_Table_ID());
						docCfe.setRecord_ID(docPo.get_ID());
						docCfe.setUY_CFE_DataEnvelope_ID(mCfeDataEnvelope.get_ID());
						try {
							docCfe.setC_DocType_ID(BigDecimal.valueOf(docPo.get_ValueAsInt("C_DocTypeTarget_ID")));
							docCfe.setDocumentNo(docPo.get_ValueAsString("documentNo"));
						} catch (Exception e2) {}
						docCfe.saveEx();
					}
				}
				
				
				if (docCfe != null) {
					
					MCFEInvoicySRspCFE invoicyCfeResp = new MCFEInvoicySRspCFE(getCtx(), 0, get_TrxName());
					invoicyCfeResp.setCFETipo(toBigDecimal(cfeRet.getCFETipo()));
					invoicyCfeResp.setCFESerie(cfeRet.getCFESerie());
					invoicyCfeResp.setCFENro(toBigDecimal(cfeRet.getCFENro()));
					invoicyCfeResp.setCFEStatus(toBigDecimal(cfeRet.getCFEStatus()));
					invoicyCfeResp.setCFEEstadoAcuse(toBigDecimal(cfeRet.getCFEEstadoAcuse()));
					invoicyCfeResp.setCFEMsgCod(toBigDecimal(cfeRet.getCFEMsgCod()));
					invoicyCfeResp.setCFEMsgDsc(cfeRet.getCFEMsgDsc());
					invoicyCfeResp.setCFERepImpressa(cfeRet.getCFERepImpressa());
					invoicyCfeResp.setCFENumReferencia(toBigDecimal(cfeRet.getCFENumReferencia()));
					invoicyCfeResp.setCFECodigoSeguridad(cfeRet.getCFECodigoSeguridad());
					
					//invoicyCfeResp.setUY_CFE_InvoicySend_ID(mCfeInvoicySend.getUY_CFE_InvoicySend_ID());
					invoicyCfeResp.setUY_CFE_DocCFE_ID(docCfe.get_ID());
					invoicyCfeResp.saveEx();
					
					List<ListaCFERetornoType.CFE.Erros.ErrosItem> cfeErrorItems = cfeRet.getErros().getErrosItem();
					for (ListaCFERetornoType.CFE.Erros.ErrosItem cfeErrorItem : cfeErrorItems) {
						MCFEInvoicySRspCFEMsg objResp = new MCFEInvoicySRspCFEMsg(getCtx(), 0, get_TrxName());
						objResp.setUY_CFE_InvoicySRspCFE_ID(invoicyCfeResp.get_ID());
						
						objResp.setCFEErrCod(toBigDecimal(cfeErrorItem.getCFEErrCod()));
						objResp.setCFEErrDesc(cfeErrorItem.getCFEErrDesc());
						
						objResp.saveEx();
					}
				}
				
			}
			
			
			for (InterfaceCFEDTO cfeDto : genericDtos) {
				cfeDto.setDataEnvelope(mCfeDataEnvelope);
			}
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
	/**
	 * OpenUp Ltda - #5270 - 28/01/2016 - Raúl Capecce
	 * @param val
	 * @return
	 */
	protected BigDecimal toBigDecimal(BigInteger val) {
		if (val != null) return BigDecimal.valueOf(val.longValue());
		else return null;
	}
	
	protected BigDecimal toBigDecimal(Integer val) {
		if (val != null) return BigDecimal.valueOf(val.longValue());
		else return null;
	}
	
}
