package org.openup.cfe;

import java.util.ArrayList;
import java.util.Properties;

import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;

public abstract class XMLProviderConverter {

	private ArrayList<InterfaceCFEDTO> cfeDtos;
	
	private Properties ctx;
	private String trxName;
	private int ad_client;
	
	
	public XMLProviderConverter(ArrayList<InterfaceCFEDTO> cfeDtos, int ad_client, Properties ctx, String trxName) {
		this.cfeDtos = cfeDtos;
		this.ctx = ctx;
		this.trxName = trxName;
		this.ad_client = ad_client;
	}
	
	protected ArrayList<InterfaceCFEDTO> getCfeDtos() {
		return this.cfeDtos;
	}
	
	protected Properties getCtx() {
		return ctx;
	}
	
	protected String get_TrxName() {
		return trxName;
	}
	
	protected int getAd_client() {
		return ad_client;
	}

	public abstract void sendCFE();
	
}
