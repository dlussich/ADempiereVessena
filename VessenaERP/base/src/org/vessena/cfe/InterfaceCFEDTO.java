package org.openup.cfe;

import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.model.MCFEDataDocument;
import org.openup.model.MCFEDataEnvelope;

public interface InterfaceCFEDTO {
	public CFEDefType getCFEDTO();
	public void onSendCFEResponse(CFEResponse cfeResponse);
	public void onVoidCFEResponse(CFEResponse cfeResponse);
	public void onQueryCFEResponse(CFEResponse cfeResponse);
	
	public boolean isDocumentoElectronico();
	
	public void setDataEnvelope(MCFEDataEnvelope mCfeDataEnvelope);
	
	/*
	 * OpenUp. Raul Capecce. 15/01/2016. ISSUE #5270
	 * Se reestructura interface y se agregan nuevos metodos
	 */
//	public void setCFEDataDocument(MCFEDataDocument mCfeDataDocument);
//	public void setCFEDataEnvelope(MCFEDataEnvelope mCfeDataEnvelope);
	
	
}
