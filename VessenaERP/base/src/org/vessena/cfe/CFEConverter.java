package org.openup.cfe;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;

public abstract class CFEConverter {
	private Properties ctx;
	private PO po;
	private String trxName;
	private CFEDefType objCFE;
	
	public CFEConverter(Properties ctx, PO po, String trxName) {
		
		if (ctx == null) throw new AdempiereException("CFE Error: ctx null");
		if (po == null) throw new AdempiereException("CFE Error: po null");
		if (trxName == null) throw new AdempiereException("CFE Error: trxName null");
		
		this.ctx = ctx;
		this.po = po;
		this.trxName = trxName;
	}
	
	public CFEDefType getObjCFE() {
		return objCFE;
	}

	protected Properties getCtx() {
		return ctx;
	}

	protected void setCtx(Properties ctx) {
		this.ctx = ctx;
	}

	protected PO getPo() {
		return po;
	}

	protected void setPo(PO po) {
		this.po = po;
	}

	protected String getTrxName() {
		return trxName;
	}

	protected void setTrxName(String trxName) {
		this.trxName = trxName;
	}

	protected void setObjCFE(CFEDefType objCFE) {
		this.objCFE = objCFE;
	}
	
	public abstract void loadCFE();
}
