package org.openup.cfe.coverter;

import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCountry;
import org.compiere.model.MDocType;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSequence;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.openup.cfe.CFEConverter;
import org.openup.cfe.CFEGenericData;
import org.openup.cfe.CFEMessages;
import org.openup.cfe.CfeUtils;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CAEDataType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Emisor;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ItemRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType.ERem.Encabezado;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType.ERem.Encabezado.Totales;
import org.openup.model.MDepartamentos;
import org.openup.model.MDgiCae;
import org.openup.model.MLocalidades;
import org.openup.model.MTRPackageType;
import org.openup.model.MTRRemito;
import org.openup.model.MTRTrip;

public class MTRRemitoCFEConverter extends CFEConverter {

	private MTRRemito mtrRemito; 
	
	public MTRRemitoCFEConverter(Properties ctx, PO po, String trxName) {
		super(ctx, po, trxName);
		mtrRemito = (MTRRemito) getPo();
	}
	
	private void validateCFETypeForRemito() {
		MDocType docType = (MDocType) mtrRemito.getC_DocType();
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
	 * OpenUp Ltda. Issue #5678 
	 * @author Raul Capecce - 28/03/2016
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
		MDocType doc = MDocType.get(getCtx(), mtrRemito.getC_DocType_ID());
		MSequence sec = new MSequence(getCtx(), doc.getDefiniteSequence_ID(), getTrxName());
		if(sec.getPrefix() != null) {
			/*   3 */ idDocRem.setSerie(sec.getPrefix());
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_003);
		}
		if(mtrRemito.getDocumentNo() != null){ // Se obtiene nro de cae directamente del documentNo
			// Se quita serie del número para enviar
			String documentNo = mtrRemito.getDocumentNo();
			documentNo = documentNo.replaceAll("[^0-9]", ""); // Expresión regular para quitar todo lo que no es número
			
			String docno = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(documentNo), 7, "0");
			BigInteger numero = new BigInteger(docno);
			/*   4 */ idDocRem.setNro(numero);
		}
		else throw new AdempiereException(CFEMessages.IDDOC_004);
		if (mtrRemito.getDateTrx() != null){
			/*   5 */ idDocRem.setFchEmis(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mtrRemito.getDateTrx(), false));
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_005);
		}
		/*   6 */ idDocRem.setTipoTraslado(BigInteger.ONE); // TODO: Por ahora VENTA, Se habla con GV, mas adelante agregar check al tipo de documento
		/*   7-15 - Tipo de obligatoriedad 0 (No corresponde)*/

		
		//  AREA: Emisor
		MOrgInfo orgInfo = MOrgInfo.get(getCtx(), mtrRemito.getAD_Org_ID(), getTrxName());
		if (orgInfo == null) throw new AdempiereException(CFEMessages.EMISOR_ORG);

		if (orgInfo.getDUNS() == null) throw new AdempiereException(CFEMessages.EMISOR_040);
		/* 40  */ emisor.setRUCEmisor(orgInfo.getDUNS());
		if (orgInfo.getrznsoc() == null) throw new AdempiereException(CFEMessages.EMISOR_041);
		/* 41  */ emisor.setRznSoc(orgInfo.getrznsoc());
		MOrg mOrg = MOrg.get(getCtx(), orgInfo.getAD_Org_ID());
		if (mOrg != null && mOrg.getName() != null) { 
			/* 42  */ emisor.setNomComercial(MOrg.get(getCtx(), orgInfo.getAD_Org_ID()).getName());
		}
		/* 43  */ emisor.setGiroEmis(orgInfo.getgirotype());
		/* 44   - Tipo de obligatoriedad 3 (dato opcional) */
		/* 45  */ emisor.setCorreoEmisor(orgInfo.getEMail());
		MWarehouse casa = MWarehouse.get(getCtx(), orgInfo.getDropShip_Warehouse_ID());
		/* 46  */ emisor.setEmiSucursal(casa.getName()); 
		try {
			/* 47  */ emisor.setCdgDGISucur(BigInteger.valueOf(Long.valueOf(orgInfo.get_ValueAsString("TaxNo"))));
		}catch(Exception ex){
			throw new AdempiereException(CFEMessages.EMISOR_047);
		}

		MLocation mLocation = (MLocation) orgInfo.getC_Location();
		if (mLocation == null || mLocation.getAddress1() == null) throw new AdempiereException(CFEMessages.EMISOR_048); 
		/* 48  */ emisor.setDomFiscal(mLocation.getAddress1());
		MLocalidades mLocalidades = (MLocalidades) mLocation.getUY_Localidades();
		if (mLocalidades == null || mLocalidades.getName() == null) throw new AdempiereException(CFEMessages.EMISOR_049);
		/* 49  */ emisor.setCiudad(mLocalidades.getName());
		MDepartamentos mDepartamentos = (MDepartamentos) mLocation.getUY_Departamentos();
		if (mDepartamentos == null || mDepartamentos.getName() == null) throw new AdempiereException(CFEMessages.EMISOR_050);
		/* 50  */ emisor.setDepartamento(mDepartamentos.getName());


		//  Area: Receptor
		MBPartner partner =  MBPartner.get(getCtx(), mtrRemito.getC_BPartner_ID_To());
		MBPartnerLocation partnerLocation = new MBPartnerLocation(getCtx(), mtrRemito.getC_BPartner_Location_ID(), getTrxName());

		encabezado.setReceptor(CFEGenericData.loadRemitoReceptor(partner, mtrRemito.getDocumentNo(), getCtx()));
		
		//  Area: Totales Encabezado
		
		totales.setCantLinDet(1); // Como es un remito de un CRT, tiene siempre solo una linea
	}
	
	/***
	 * Carga la informacion de las lineas para el eRemito
	 * 
	 * Pagina 25-30: B - Detalle de Productos o Servicios
	 * 
	 * OpenUp Ltda. Issue #5678 
	 * @author Raul Capecce - 28/03/2016
	 */
	private void loadDetalleProductosOServicios_eRemito() {
		CFEDefType objCfe = getObjCFE();
		
		objCfe.getERem().setDetalle(new CFEDefType.ERem.Detalle());
		List<ItemRem> lineas = objCfe.getERem().getDetalle().getItem();

		ItemRem objLine = new ItemRem();
		lineas.add(objLine);

		objLine.setNroLinDet(1);

		MTRTrip mtrTrip = (MTRTrip) mtrRemito.getUY_TR_Trip();
		
		/*   2 - Tipo de obligatoriedad 3 (dato opcional) */
		/*   3 - Tipo de obligatoriedad 3 (dato opcional) */
		
		/*   4 - Tipo de obligatoriedad 2 (dato condicional pero no se especifica condición) */
		
		/*   7 */ objLine.setNomItem(mtrTrip.getM_Product().getName());
		/*   8 */ objLine.setDscItem(mtrTrip.getProductType());
		/*   9 */ objLine.setCantidad(mtrRemito.getbultos());
		/*  10 */ objLine.setUniMed(((MTRPackageType)mtrTrip.getUY_TR_PackageType()).getName());
		
	}
	
	/***
	 * Carga la informacion referente al CAE correspondiente a cada CFE
	 * 
	 * Pagina 37: G - Constancia Auorizacion para Emision de CFE (CAE)
	 * 
	 * OpenUp Ltda. Issue #5678
	 * @author Raul Capecce	- 29/03/2016
	 */
	private void loadCAE() {
		CFEDefType objCfe = getObjCFE();
		
		CAEDataType caeDataType = new CAEDataType();
		objCfe.getERem().setCAEData(caeDataType);
		

		int idCae = ((MDocType) mtrRemito.getC_DocType()).get_ValueAsInt("UY_DGI_CAE_ID");
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
	 * OpenUp Ltda. Issue #5678
	 * @author Raul Capecce - 29/03/2016
	 */
	private void loadTimestamp() {

		CFEDefType objCfe = getObjCFE();
		
		objCfe.getERem().setTmstFirma(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mtrRemito.getDateTrx(), true));
	}
	
}
