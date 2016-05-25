package org.openup.aduana;

import java.io.StringWriter;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

public class PTRAduana extends SvrProcess {

	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private BigInteger docType;

	public PTRAduana() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("StartDate")) {
					this.fechaInicio = (Timestamp) para[i].getParameter();
					this.fechaFin = (Timestamp) para[i].getParameter_To();
				}
				if (name.equalsIgnoreCase("DocTypeName")) {
					this.docType = BigInteger.valueOf(para[i]
							.getParameterAsInt());
				}
			}
		}
	}

	@Override
	protected String doIt() throws Exception {

		String strMicToSend = ""; // String with XML return
		org.openup.aduana.mic.conexion.test.WsMensManifiestoExecute micToSend = null;

		try {

			// * * * Creating CRTs * * *

			// * * * Creating CRT * * *

			// * * * Sending CRT * * *

			// * * * Creating DAE * * *
			org.openup.aduana.mic.dto.DAE dae = new org.openup.aduana.mic.dto.DAE();

			// * * * Setting DAE's elements * * *
			dae.setTipoDocumento("4");
			dae.setIdDocumento("123456");
			dae.setCodigoIntercambio("WS_MANIFIESTO");

			// * Current timestamp in XML format *
			// Create a Gregorian calendar
			GregorianCalendar calendarioGreg = new GregorianCalendar();
			// Create a XML Gregorian calendar
			XMLGregorianCalendar currentDate = null;

			currentDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(calendarioGreg);

			dae.setFechaHoraDocumentoElectronico(currentDate);

			org.openup.aduana.mic.dto.DAE.Objeto objeto = new org.openup.aduana.mic.dto.DAE.Objeto();
			org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos manifiestos = new org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos();
			org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto manifiesto = new org.openup.aduana.mic.dto.DAE.Objeto.Manifiestos.Manifiesto();

			manifiestos.getManifiesto().add(manifiesto);
			objeto.setManifiestos(manifiestos);
			dae.setObjeto(objeto);

			// * * * Generating XML * * *
			java.io.Writer swDae = new StringWriter();
			JAXBContext context = JAXBContext
					.newInstance(org.openup.aduana.mic.dto.DAE.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(dae, swDae);
			strMicToSend = swDae.toString();

			// * * * Sending MIC * * *
			micToSend = new org.openup.aduana.mic.conexion.test.WsMensManifiestoExecute();
			micToSend.setIn(strMicToSend);
			

		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		return "OK";
	}

}
