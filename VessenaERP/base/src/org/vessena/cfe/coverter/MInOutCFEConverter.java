package org.openup.cfe.coverter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCountry;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MSequence;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.openup.cfe.CFEConverter;
import org.openup.cfe.CFEGenericData;
import org.openup.cfe.CFEMessages;
import org.openup.cfe.CfeUtils;
import org.openup.cfe.CfeUtils.CfeType;
import org.openup.dgi.CfeConverter;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CAEDataType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Emisor;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ItemDetFact;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType.ERem.Encabezado;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ItemRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType.ERem.Encabezado.Totales;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ItemRem.CodItem;
import org.openup.model.MDepartamentos;
import org.openup.model.MDgiCae;
import org.openup.model.MLocalidades;

public class MInOutCFEConverter extends CFEConverter {

	MInOut mInOut = (MInOut) getPo();
	
	public MInOutCFEConverter(Properties ctx, PO po, String trxName) {
		super(ctx, po, trxName);
		
	}
	
	private void validateCFETypeForRemito() {
		MDocType docType = (MDocType) mInOut.getC_DocType();
		boolean isCfe = docType.get_ValueAsBoolean("isCFE");
		String cfeType = docType.get_ValueAsString("cfeType");
		
		if (!isCfe) throw new AdempiereException(CFEMessages.IDDOC_002);
		
		// Tipo de CFE Remito
		if (!cfeType.equalsIgnoreCase("181")) throw new AdempiereException("CFE Error: Tipo de CFE no corresponde, se requiere eRemito");
		
	}

	@Override
	public void loadCFE() {
		
		validateCFETypeForRemito();
		
		CFEDefType objCfe = new CFEDefType();
		setObjCFE(objCfe);
		objCfe.setERem(new CFEDefType.ERem());
		
		
		loadEncabezado_eRemito();
		loadDetalleProductosOServicios_eRemito();
		loadCAE();
		loadTimestamp();
		
	}
	
	/***
	 * Carga la informacion del cabezal para el eRemito
	 * 
	 * Pagina 13-24: A - Encabezado
	 * 
	 * OpenUp Ltda. Issue #5560 
	 * @author Raul Capecce - 03/03/2016
	 */
	private void loadEncabezado_eRemito() {
		CFEDefType objCfe = getObjCFE();
		
		Encabezado encabezado = new Encabezado();
		objCfe.getERem().setEncabezado(encabezado);
		
		
		IdDocRem idDocRem = new IdDocRem();
		Emisor emisor = new Emisor();
		ReceptorRem receptor = new ReceptorRem();
		Totales totales = new Totales();
		
		encabezado.setIdDoc(idDocRem);
		encabezado.setEmisor(emisor);
		encabezado.setReceptor(receptor);
		encabezado.setTotales(totales);
		
		/*   1 */ objCfe.setVersion("1.0");
		
		
		//  AREA: Identificacion del Comprobante
		/*   2 */ idDocRem.setTipoCFE(BigInteger.valueOf(181));
		MDocType doc = MDocType.get(getCtx(), mInOut.getC_DocType_ID());
		MSequence sec = new MSequence(getCtx(), doc.getDefiniteSequence_ID(), getTrxName());
		if(sec.getPrefix() != null) {
			/*   3 */ idDocRem.setSerie(sec.getPrefix());
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_003);
		}
		if(mInOut.getDocumentNo() != null){ // Se obtiene nro de cae directamente del documentNo
			// Se quita serie del número para enviar
			String documentNo = mInOut.getDocumentNo();
			documentNo = documentNo.replaceAll("[^0-9]", ""); // Expresión regular para quitar todo lo que no es número
			
			String docno = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(documentNo), 7, "0");
			BigInteger numero = new BigInteger(docno);
			/*   4 */ idDocRem.setNro(numero);
		}
		else throw new AdempiereException(CFEMessages.IDDOC_004);
		if (mInOut.getDateOrdered() != null){
			/*   5 */ idDocRem.setFchEmis(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInOut.getDateOrdered(), false));
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_005);
		}
		/*   6 */ idDocRem.setTipoTraslado(BigInteger.ONE); // TODO: Por ahora VENTA, Se habla con GV, mas adelante agregar check al tipo de documento
		/*   7-15 - Tipo de obligatoriedad 0 (No corresponde)*/

		
		//  AREA: Emisor
		MOrgInfo orgInfo = MOrgInfo.get(getCtx(), mInOut.getAD_Org_ID(), getTrxName());
		encabezado.setEmisor(CFEGenericData.loadEmisor(orgInfo, getCtx()));
		
		//  Area: Receptor
		MBPartner partner =  MBPartner.get(getCtx(), mInOut.getC_BPartner_ID());
		MBPartnerLocation partnerLocation = new MBPartnerLocation(getCtx(), mInOut.getC_BPartner_Location_ID(), getTrxName());
		

		encabezado.setReceptor(CFEGenericData.loadRemitoReceptor(partner, mInOut.getDocumentNo(), getCtx()));
		
		//  Area: Totales Encabezado
		//  Requerido solo campo 126, el resto de los campos de Totales Encabezado no corresponden.

		// C126 - Validar que no exeda la cantidad de lineas admitida por cada tipo de CFE
		// eTicket (solo y con NC y ND): <= 700
		// Otros CFE: <= 200
//		if (invoiceLines.size() > 200) throw new AdempiereException(CFEMessages.TOTALES_126_2);
//		totales.setCantLinDet(invoiceLines.size());
		
		/* 126 */ // TODO: Calcular cantidad de lineas
		
		
		
	}

	/***
	 * Carga la informacion de las lineas para el eRemito
	 * 
	 * Pagina 25-30: B - Detalle de Productos o Servicios
	 * 
	 * OpenUp Ltda. Issue #5560 
	 * @author Raul Capecce - 03/03/2016
	 */
	private void loadDetalleProductosOServicios_eRemito() {
		CFEDefType objCfe = getObjCFE();
		
		Encabezado encabezado = new Encabezado();
		objCfe.getERem().setEncabezado(encabezado);
		
		ArrayList<MInOutLine> mInOutLines = new ArrayList<MInOutLine>(Arrays.asList(mInOut.getLines()));
		objCfe.getERem().setDetalle(new CFEDefType.ERem.Detalle());
		List<ItemRem> lineas = objCfe.getERem().getDetalle().getItem();

		int position = 0;
		for (MInOutLine mInOutLine : mInOutLines) {
			ItemRem objLine = new ItemRem();
			lineas.add(objLine);
			
			/* 1  */ objLine.setNroLinDet(position ++);
			
			MProduct mProduct = new MProduct(getCtx(), mInOutLine.getM_Product_ID(), getTrxName());
			CodItem codItem = null;
			
			
			
			// Codigo interno del Cliente
			if (mProduct.getValue() != null){
				codItem = new CodItem(); 
				/* 2  */ codItem.setTpoCod("INT1");
				/* 3  */ codItem.setCod(mProduct.getValue());
				objLine.getCodItem().add(codItem);
			}
			
			// Codigo EAN
			if (mProduct.getUPC() != null){
				codItem = new CodItem();
				/* 2  */ codItem.setTpoCod("EAN");
				/* 3  */ codItem.setCod(mProduct.getUPC());
				objLine.getCodItem().add(codItem);
			}

			MTaxCategory mTaxCategory = (MTaxCategory) mProduct.getC_TaxCategory();
			if (mTaxCategory == null) throw new AdempiereException(CFEMessages.DETALLE_004.replace("{{documentNo}}", mInOut.getDocumentNo()));
			MTax tax = mTaxCategory.getDefaultTax();
			if(tax.getValue().equalsIgnoreCase("minimo")){
				/* 4  */ objLine.setIndFact(BigInteger.valueOf(2));
			} else if(tax.getValue().equalsIgnoreCase("basico")){
				/* 4  */ objLine.setIndFact(BigInteger.valueOf(3));
			} else if(tax.getValue().equalsIgnoreCase("exento")){
				/* 4  */ objLine.setIndFact(BigInteger.valueOf(1));
			} else if(tax.getValue().equalsIgnoreCase("expoasi")){
				/* 4  */ objLine.setIndFact(BigInteger.valueOf(1));
			} else {
				throw new AdempiereException(CFEMessages.DETALLE_004.replace("{{documentNo}}", mInOut.getDocumentNo()));
			}
			
			/* 7  */ objLine.setNomItem(mInOutLine.getProduct().getName());
			/* 8  */ objLine.setDscItem(mInOutLine.getProduct().getDescription());
			/* 9  */ objLine.setCantidad(mInOutLine.getQtyEntered());
			/* 10 */ objLine.setUniMed(mInOutLine.getProduct().getUOMSymbol());
			
			
		}
	}
	
	

	/***
	 * Carga la informacion referente al CAE correspondiente a cada CFE
	 * 
	 * Pagina 37: G - Constancia Auorizacion para Emision de CFE (CAE)
	 * 
	 * OpenUp Ltda. Issue #5560
	 * @author Raul Capecce	- 03/03/2016
	 */
	private void loadCAE() {
		CFEDefType objCfe = getObjCFE();
		
		CAEDataType caeDataType = new CAEDataType();
		objCfe.getERem().setCAEData(caeDataType);
		

		int idCae = ((MDocType) mInOut.getC_DocType()).get_ValueAsInt("UY_DGI_CAE_ID");
		if (idCae == 0) throw new AdempiereException(CFEMessages.CAE_NODEF);
		MDgiCae mDgiCae = new MDgiCae(getCtx(), idCae, getTrxName());
		
		caeDataType.setCAEID(mDgiCae.getdocumentoNumero().toBigInteger());
		caeDataType.setDNro(mDgiCae.getnumeroInicio().toBigInteger());
		caeDataType.setHNro(mDgiCae.getnumeroFin().toBigInteger());
		caeDataType.setFecVenc(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mDgiCae.getfechaVencimiento(), false));//mDgiCae.getfechaVencimiento() Emi
		
	}
	

	 /***
	  * Cargar la fecha y hora del documento
	  * 
	  * Pagina 38: H - Fecha y Hora de Firma Electronica Avanzada del Comprobante
	  * 
	  * OpenUp Ltda. Issue #5560
	  * @author Raul Capecce - 03/03/2016
	  */
	private void loadTimestamp() {

		CFEDefType objCfe = getObjCFE();
		
		objCfe.getERem().setTmstFirma(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInOut.getDateOrdered(), true));
	}
	
	
}
