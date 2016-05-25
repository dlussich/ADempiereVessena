package org.openup.cfe.coverter;

import java.math.BigInteger;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSequence;
import org.compiere.model.PO;
import org.openup.cfe.CFEConverter;
import org.openup.cfe.CFEGenericData;
import org.openup.cfe.CFEMessages;
import org.openup.cfe.CfeUtils;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType.EResg.Encabezado;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.IdDocResg;
import org.openup.model.MResguardo;


/**
 * OpenUp Ltda - #5729 - 26/04/2016
 * @author Raul Capecce
 */
public class MResguardoCFEConverter extends CFEConverter {

	MResguardo mResguardo = (MResguardo) getPo();
	
	public MResguardoCFEConverter(Properties ctx, PO po, String trxName) {
		super(ctx, po, trxName);
		
	}

	private void validateCFETypeForResguardo() {
		MDocType docType = (MDocType) mResguardo.getC_DocType();
		boolean isCfe = docType.get_ValueAsBoolean("isCFE");
		String cfeType = docType.get_ValueAsString("cfeType");
		
		if (!isCfe) throw new AdempiereException(CFEMessages.IDDOC_002);
		
		// Tipo de CFE Remito
		if (!cfeType.equalsIgnoreCase("182")) throw new AdempiereException("CFE Error: Tipo de CFE no corresponde, se requiere eResguardo");
		
	}

	@Override
	public void loadCFE() {
		validateCFETypeForResguardo();
		
		CFEDefType objCfe = new CFEDefType();
		setObjCFE(objCfe);
		objCfe.setEResg(new CFEDefType.EResg());
		
		loadEncabezado_eRemito();
		
		
	}
	
	/***
	 * Carga la informacion del cabezal para el eRemito
	 * 
	 * Pagina 13-24: A - Encabezado
	 * 
	 * OpenUp Ltda. Issue #5729
	 * @author Raul Capecce - 06/04/2016
	 */
	private void loadEncabezado_eRemito() {
		CFEDefType objCfe = getObjCFE();
		
		Encabezado encabezado = new Encabezado();
		objCfe.getEResg().setEncabezado(encabezado);
		
		IdDocResg idDocResg = new IdDocResg();
		
		encabezado.setIdDoc(idDocResg);
		

		/*   1 */ objCfe.setVersion("1.0");
		
		//  AREA: Identificacion del Comprobante
		/*   2 */ idDocResg.setTipoCFE(BigInteger.valueOf(181));
		MDocType doc = MDocType.get(getCtx(), mResguardo.getC_DocType_ID());
		MSequence sec = new MSequence(getCtx(), doc.getDefiniteSequence_ID(), getTrxName());
		if(sec.getPrefix() != null) {
			/*   3 */ idDocResg.setSerie(sec.getPrefix());
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_003);
		}
		if(mResguardo.getDocumentNo() != null){ // Se obtiene nro de cae directamente del documentNo
			// Se quita serie del número para enviar
			String documentNo = mResguardo.getDocumentNo();
			documentNo = documentNo.replaceAll("[^0-9]", ""); // Expresión regular para quitar todo lo que no es número
			
			String docno = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(documentNo), 7, "0");
			BigInteger numero = new BigInteger(docno);
			/*   4 */ idDocResg.setNro(numero);
		}
		else throw new AdempiereException(CFEMessages.IDDOC_004);
		if (mResguardo.getDateTrx() != null){
			/*   5 */ idDocResg.setFchEmis(CfeUtils.Timestamp_to_XmlGregorianCalendar_OnlyDate(mResguardo.getDateTrx(), false));
		} else {
			throw new AdempiereException(CFEMessages.IDDOC_005);
		}
		/*   6-15 - Tipo de obligatoriedad 0 (No corresponde)*/
		
		//  AREA: Emisor
		MOrgInfo orgInfo = MOrgInfo.get(getCtx(), mResguardo.getAD_Org_ID(), getTrxName());
		encabezado.setEmisor(CFEGenericData.loadEmisor(orgInfo, getCtx()));
		
		//  Area: Receptor
		MBPartner partner =  MBPartner.get(getCtx(), mResguardo.getC_BPartner_ID());
		encabezado.setReceptor(CFEGenericData.loadResguardoReceptor(partner, mResguardo.getDocumentNo(), getCtx()));	

	}

}
