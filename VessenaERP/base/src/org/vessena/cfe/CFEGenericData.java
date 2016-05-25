package org.openup.cfe;

import java.math.BigInteger;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCountry;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MWarehouse;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.Emisor;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorRem;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.ReceptorResg;
import org.openup.model.MDepartamentos;
import org.openup.model.MLocalidades;

/**
 * org.openup.cfe - CFEGenericData
 * OpenUp Ltda. Issue #
 * Description: Clase con código genérico para varios 
 * Documento base: Formato de los CFE - Comprobante Fiscal Electronico - Version 13 26/05/2014 
 * @author Raul Capecce - 22/05/2015
 * @see
 */
public class CFEGenericData {

	/***
	 * Carga la informacion del Receptor en el cabezal para el eRemito
	 * 
	 * Pagina 13-24: A - Encabezado
	 * 
	 * OpenUp Ltda. Issue #5678 
	 * @author Raul Capecce - 29/03/2016
	 */
	public static ReceptorRem loadRemitoReceptor(MBPartner partner, String documentNo, Properties ctx) {
		ReceptorRem receptor = new ReceptorRem();
		
		// Cargo C_BPartnerLocation marcada para remito
		MBPartnerLocation partnerLocation = null;
		MBPartnerLocation[] locations = partner.getLocations(false);
		if (locations == null) throw new AdempiereException(CFEMessages.RECEPTOR_REM_NOLOCATIONDEF);
		for (MBPartnerLocation mbpl : locations) {
			if (mbpl.isRemitTo()) {
				if (partnerLocation == null) {
					partnerLocation = mbpl;
				} else {
					throw new AdempiereException(CFEMessages.RECEPTOR_REM_NOLOCATIONDEF);
				}
			}
		}
		if (partnerLocation == null) throw new AdempiereException(CFEMessages.RECEPTOR_REM_NOLOCATIONDEF);
		
		
		
		//  Area: Receptor
		
		int tipoDocRecep = 0;
		String docRecep = null;
		if (partner.getDUNS() != null) {
			if (partner.getDocumentType().equalsIgnoreCase(MBPartner.DOCUMENTTYPE_RUT)) {
				tipoDocRecep = 2;
			} else {
				tipoDocRecep = 4;
			}
			docRecep = partner.getDUNS();
		} else if (partner.get_Value("cedula") != null) {
			tipoDocRecep = 3;
			docRecep = partner.get_Value("cedula").toString();
		} else {
			tipoDocRecep = 4;
			docRecep = CFEMessages.RECEPTOR_NODOC.replace("{{documentNo}}", documentNo);
		}
		/* 60  */ receptor.setTipoDocRecep(tipoDocRecep);
		MCountry mCountry = null;
		try {
			mCountry = MCountry.get(ctx, Integer.valueOf(partnerLocation.get_Value("C_Country_ID").toString()));
		} catch (Exception e) {
			throw new AdempiereException();
		}
		if (mCountry == null) throw new AdempiereException(CFEMessages.RECEPTOR_61);
		/* 61  */ receptor.setCodPaisRecep(mCountry.getCountryCode());
		
		if (tipoDocRecep == 2 || tipoDocRecep == 3) {
			/* 62  */ receptor.setDocRecep(docRecep);
		} else if (tipoDocRecep == 4 || tipoDocRecep == 5 || tipoDocRecep == 6) {
			/* 62.1*/ receptor.setDocRecepExt(docRecep);
		}
		
		/* 63  */ receptor.setRznSocRecep(partner.getName2());String dirRecep = null;
		if (partnerLocation.getAddress1() != null) {
			if (partnerLocation.getAddress1().length() <= 70)
				dirRecep = partnerLocation.getAddress1();
			else
				dirRecep = partnerLocation.getAddress1().substring(0, 70);
		}
		/* 64  */ receptor.setDirRecep(dirRecep);
		/* 65  */ receptor.setCiudadRecep(partnerLocation.getUY_Localidades().getName());
		/* 66  */ receptor.setDeptoRecep(partnerLocation.getUY_Departamentos().getName());
		/* 66.1*/ receptor.setPaisRecep(partnerLocation.getC_Country().getName());
		try {
			/* 67  */ receptor.setCP(Integer.valueOf(partnerLocation.getUY_Localidades().getzipcode()));
		} catch (Exception ex) { }
		
			
		
		return receptor;
	}
	
	/***
	 * Carga la informacion del Receptor en el cabezal para el eResguardo
	 * 
	 * Pagina 13-24: A - Encabezado
	 * 
	 * OpenUp Ltda. Issue #5729 
	 * @author Raul Capecce - 06/04/2016
	 */
	public static ReceptorResg loadResguardoReceptor(MBPartner partner, String documentNo, Properties ctx) {
		ReceptorResg receptor = new ReceptorResg();

		// Cargo C_BPartnerLocation marcada para remito
		MBPartnerLocation partnerLocation = null;
		MBPartnerLocation[] locations = partner.getLocations(false);
		if (locations == null) throw new AdempiereException(CFEMessages.RECEPTOR_REM_NOLOCATIONDEF);
		for (MBPartnerLocation mbpl : locations) {
			if (mbpl.isRemitTo()) {
				if (partnerLocation == null) {
					partnerLocation = mbpl;
				} else {
					throw new AdempiereException(CFEMessages.RECEPTOR_REM_NOLOCATIONDEF);
				}
			}
		}
		if (partnerLocation == null) throw new AdempiereException(CFEMessages.RECEPTOR_REM_NOLOCATIONDEF);
		
		
		
		//  Area: Receptor
		
		int tipoDocRecep = 0;
		String docRecep = null;
		if (partner.getDUNS() != null) {
			if (partner.getDocumentType().equalsIgnoreCase(MBPartner.DOCUMENTTYPE_RUT)) {
				tipoDocRecep = 2;
			} else {
				tipoDocRecep = 4;
			}
			docRecep = partner.getDUNS();
		} else if (partner.get_Value("cedula") != null) {
			tipoDocRecep = 3;
			docRecep = partner.get_Value("cedula").toString();
		} else {
			tipoDocRecep = 4;
			docRecep = CFEMessages.RECEPTOR_NODOC.replace("{{documentNo}}", documentNo);
		}
		/* 60  */ receptor.setTipoDocRecep(tipoDocRecep);
		MCountry mCountry = null;
		try {
			mCountry = MCountry.get(ctx, Integer.valueOf(partnerLocation.get_Value("C_Country_ID").toString()));
		} catch (Exception e) {
			throw new AdempiereException();
		}
		if (mCountry == null) throw new AdempiereException(CFEMessages.RECEPTOR_61);
		/* 61  */ receptor.setCodPaisRecep(mCountry.getCountryCode());
		
		if (tipoDocRecep == 2 || tipoDocRecep == 3) {
			/* 62  */ receptor.setDocRecep(docRecep);
		} else if (tipoDocRecep == 4 || tipoDocRecep == 5 || tipoDocRecep == 6) {
			/* 62.1*/ receptor.setDocRecepExt(docRecep);
		}
		
		/* 63  */ receptor.setRznSocRecep(partner.getName2());String dirRecep = null;
		if (partnerLocation.getAddress1() != null) {
			if (partnerLocation.getAddress1().length() <= 70)
				dirRecep = partnerLocation.getAddress1();
			else
				dirRecep = partnerLocation.getAddress1().substring(0, 70);
		}
		/* 64  */ receptor.setDirRecep(dirRecep);
		/* 65  */ receptor.setCiudadRecep(partnerLocation.getUY_Localidades().getName());
		/* 66  */ receptor.setDeptoRecep(partnerLocation.getUY_Departamentos().getName());
		/* 66.1*/ receptor.setPaisRecep(partnerLocation.getC_Country().getName());
		try {
			/* 67  */ receptor.setCP(Integer.valueOf(partnerLocation.getUY_Localidades().getzipcode()));
		} catch (Exception ex) { }
		
			
		
		return receptor;
	}
	
	/***
	 * Carga la informacion del Emisor en el cabezal para el CFE
	 * 
	 * Pagina 13-24: A - Encabezado
	 * 
	 * OpenUp Ltda. Issue #5729
	 * @author Raul Capecce - 06/04/2016
	 */
	public static Emisor loadEmisor(MOrgInfo orgInfo, Properties ctx) {
		Emisor emisor = new Emisor();
		
		if (orgInfo == null) throw new AdempiereException(CFEMessages.EMISOR_ORG);

		if (orgInfo.getDUNS() == null) throw new AdempiereException(CFEMessages.EMISOR_040);
		/* 40  */ emisor.setRUCEmisor(orgInfo.getDUNS());
		if (orgInfo.getrznsoc() == null) throw new AdempiereException(CFEMessages.EMISOR_041);
		/* 41  */ emisor.setRznSoc(orgInfo.getrznsoc());
		MOrg mOrg = MOrg.get(ctx, orgInfo.getAD_Org_ID());
		if (mOrg != null && mOrg.getName() != null) { 
			/* 42  */ emisor.setNomComercial(MOrg.get(ctx, orgInfo.getAD_Org_ID()).getName());
		}
		/* 43  */ emisor.setGiroEmis(orgInfo.getgirotype());
		/* 44   - Tipo de obligatoriedad 3 (dato opcional) */
		/* 45  */ emisor.setCorreoEmisor(orgInfo.getEMail());
		MWarehouse casa = MWarehouse.get(ctx, orgInfo.getDropShip_Warehouse_ID());
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

		
		return emisor;
	}

}
