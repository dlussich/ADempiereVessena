package org.openup.dgi;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.CFEDefType;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.EnvioCFE;
import org.openup.dgi.efactura.dto.uy.gub.dgi.cfe.EnvioCFE.Caratula;

public class PTRPrueba2 extends SvrProcess {

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
		
		//CfeConverter cfeConverter = new CfeConverter(getCtx(), new MInvoice(getCtx(), 1008919, get_TrxName()), get_TrxName());
		
		SobreCFE sobreCFE = new SobreCFE(getCtx(), get_TrxName());
		sobreCFE.addCFE(new MInvoice(getCtx(), 1008921, get_TrxName()));//1017983
		sobreCFE.generateEnvelopeToDGI();
		
		
//		Caratula caratula = new Caratula();
//		EnvioCFE envioCFE = new EnvioCFE();
//		cfeConverter.loadCFE();
//		
//		CFEDefType cfe = cfeConverter.getObjCfe();
//		envioCFE.getCFE().add(cfe);
//		envioCFE.setCaratula(caratula);
//		envioCFE.setVersion("1.0");
//		caratula.setVersion("1.0");
//		caratula.setCantCFE(1);
//		caratula.setIdemisor(BigInteger.valueOf(1));
//		
//		
//		caratula.setRUCEmisor("213374740016");
//		caratula.setRutReceptor("214844360018");
//		
//		
//		MInvoice mInv = new MInvoice(getCtx(), 1008919, get_TrxName());
//		
//		caratula.setFecha(CfeConverter.Timestamp_to_XmlGregorianCalendar_OnlyDate(mInv.getDateInvoiced(), true));
//		caratula.setX509Certificate(CaratulaConverter.getPublicCer(getCtx(), mInv.getAD_Client_ID(), mInv.getAD_Org_ID(), get_TrxName()));
//		
//		
//		java.io.Writer swCfe1 = new StringWriter();
//		JAXBContext context1 = JAXBContext.newInstance(EnvioCFE.class);
//		Marshaller m1 = context1.createMarshaller();
//		m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//		m1.marshal(envioCFE, swCfe1);
//		String xmlCfe = swCfe1.toString();
//		
//		
//		
//		
//		
//		// Guardar archivo temporal
//		FileWriter fichero = null;
//        PrintWriter pw = null;
//        try
//        {
//            fichero = new FileWriter("/tmp/prueba.xml");
//            pw = new PrintWriter(fichero);
// 
//            //for (int i = 0; i < xmlCfe.length(); i++)
//            pw.println(xmlCfe);
// 
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//           try {
//           // Nuevamente aprovechamos el finally para 
//           // asegurarnos que se cierra el fichero.
//           if (null != fichero)
//              fichero.close();
//           } catch (Exception e2) {
//              e2.printStackTrace();
//           }
//        }
		
		
		return "OK";
	}

}
