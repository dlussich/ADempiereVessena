
package org.openup.aduana.mic.conexion.produccion;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "ws_MensManifiestoSoapPort", targetNamespace = "www.aduanas.gub.uy/DAE")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WsMensManifiestoSoapPort {


    /**
     * 
     * @param parameters
     * @return
     *     returns uy.gub.aduanas.dae.WsMensManifiestoExecuteResponse
     */
    @WebMethod(operationName = "Execute", action = "www.aduanas.gub.uy/DAEaction/AWS_MENSMANIFIESTO.Execute")
    @WebResult(name = "ws_MensManifiesto.ExecuteResponse", targetNamespace = "www.aduanas.gub.uy/DAE", partName = "parameters")
    public WsMensManifiestoExecuteResponse execute(
        @WebParam(name = "ws_MensManifiesto.Execute", targetNamespace = "www.aduanas.gub.uy/DAE", partName = "parameters")
        WsMensManifiestoExecute parameters);

}
