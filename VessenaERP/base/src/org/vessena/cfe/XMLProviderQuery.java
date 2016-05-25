package org.openup.cfe;

import java.util.Properties;

public abstract class XMLProviderQuery {
	private Properties ctx;
	private String trxName;
	private int ad_client;
	
	
	public XMLProviderQuery(int ad_client, Properties ctx, String trxName) {
		this.ctx = ctx;
		this.trxName = trxName;
		this.ad_client = ad_client;
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

	public abstract void queryCFEs();
	
}
