package org.openup.cfe;

import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.openup.cfe.provider.migrate.MigrateXMLProviderConverter;
import org.openup.cfe.provider.migrate.MigrateXMLProviderQuery;
import org.openup.cfe.provider.sisteco.SistecoXMLProviderConverter;
import org.openup.cfe.provider.sisteco.SistecoXMLProviderQuery;
import org.openup.model.MCFEProviderConfig;

public class XMLProviderConverterFactory {

	private XMLProviderConverterFactory() {
		
	}

	/**
	 * Obtiene el proveedor de envio facturacion electronica definido en UY_CFE_ProviderConfig
	 * @param mCfeProviderConfig: Configuración de proveedores de facturacion electronica 
	 * @param cfeDtos: Los documentos a enviar
	 * @param ad_client: Compañia
	 * @param ctx: Contexto
	 * @param trxName: Transaccion
	 * @return Proveedor de envio de CFE, puede retornal null si en la linea no esta seleccionado ninguno
	 * @throws Si no hay linea definida en la tabla, lanza excepcion informando de que no encuentra configuracion
	 */
	public static XMLProviderConverter getXMLProciderConverter(MCFEProviderConfig mCfeProviderConfig, ArrayList<InterfaceCFEDTO> cfeDtos, int ad_client, Properties ctx, String trxName) {
		
		if (mCfeProviderConfig.getProviderAgent().equalsIgnoreCase(MCFEProviderConfig.PROVIDERAGENT_InvoiCy)) {
			return new MigrateXMLProviderConverter(cfeDtos, ad_client, ctx, trxName);
		} else if (mCfeProviderConfig.getProviderAgent().equalsIgnoreCase(MCFEProviderConfig.PROVIDERAGENT_Sisteco)) {
			return new SistecoXMLProviderConverter(cfeDtos, ad_client, ctx, trxName);
		}
		
		return null;
		
	}
	
	/**
	 * Obtiene el proveedor de envio de facturación electronica definido en UY_CFE_ProviderConfig
	 * Necesario para realizar la consulta de CFEs enviados
	 * @param mCfeProviderConfig Configuracion de proveedores de facturacion electronica
	 * @param ad_client: Compañia
	 * @param ctx: Contexto
	 * @param trxName Transaccion
	 * @return Proveedor de envio de CFE para realizar la consulta, puede retornar null si en la linea no esta seleccionado ninguno
	 */
	public static XMLProviderQuery getXMLProviderQuery(MCFEProviderConfig mCfeProviderConfig, int ad_client, Properties ctx, String trxName) {
		
		if (mCfeProviderConfig.getProviderAgent().equalsIgnoreCase(MCFEProviderConfig.PROVIDERAGENT_InvoiCy)) {
			return new MigrateXMLProviderQuery(ad_client, ctx, trxName);
		} else if (mCfeProviderConfig.getProviderAgent().equalsIgnoreCase(MCFEProviderConfig.PROVIDERAGENT_Sisteco)) {
			return new SistecoXMLProviderQuery(ad_client, ctx, trxName);
		}
		
		return null;
	}
	
}
