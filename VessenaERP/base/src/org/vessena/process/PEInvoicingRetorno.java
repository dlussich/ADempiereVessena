package org.openup.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.util.IteratorIterable;
import org.openup.model.MCFEInvoice;
import org.openup.model.X_UY_CFE_Invoice;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * 
 * org.openup.dgi.process - PEInvoicingRetorno OpenUp Ltda. Issue # 2208
 * Description:
 * 
 * @author Leonardo Boccone - 04/07/2014
 * @see
 */
public class PEInvoicingRetorno extends SvrProcess {

	String rutaSalida;

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		getRuta();
		checkFiles();

		return "OK";
	}

	private void checkFiles() throws IOException, JDOMException {
		File salida = new File(rutaSalida);

		File historico = new File("C:\\Historico");
		if (!historico.exists()) {
			if (!historico.mkdir()) {
				throw new AdempiereException();
			}
		}
		String[] files = salida.list();

		for (int i = 0; i < files.length; i++) {
			if (files[i].startsWith("RET") && files[i].endsWith("xml")) {
				MCFEInvoice status = new MCFEInvoice(this.getCtx(), 0,this.get_TrxName());
				File source = new File(salida + "\\" + files[i]);
				File dest = new File(historico + "\\"+ "OK"+ files[i]);
				if (retornoOK(source, status)) {
					copyFile(source, dest);
					source.delete();
				}
				 status.saveEx();	
			}

		}
	

	}

	/**
	 * Chequeo el archivo de retorno y actualizo los datos ne la tabla
	 * UY_CFE_Invoice penUp Ltda. Issue #2208
	 * 
	 * @author Leonardo Boccone - 04/07/2014
	 * @see
	 * @param source
	 * @param status
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	private boolean retornoOK(File source, MCFEInvoice status)
			throws JDOMException, IOException {
		boolean respuesta = false;
		String serie = "";
		String numero = "";
		List<String> errores = new ArrayList<String>();

		if (source.canRead()) {
			// Se crea un SAXBuilder para poder parsear el archivo
			SAXBuilder builder = new SAXBuilder();

			// Se crea el documento a traves del archivo
			Document document = (Document) builder.build(source);

			// Se obtiene la raiz 'tables'
			Element rootNode = document.getRootElement();

			// Se obtiene la lista de hijos de la raiz'
			IteratorIterable<Content> list = rootNode.getDescendants();

			while (list.hasNext()) {
				Element aux = list.next().getParentElement();
				List<Element> children = aux.getChildren();
				for (Element e : children) {
					if (e.getName() != null) {
						if (e.getName().equalsIgnoreCase("CFEStatus")) {

							if (e.getValue().trim().startsWith("1")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_1_NoProcesado);
							} else if (e.getValue().trim().startsWith("2")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_2_EnProcesamiento);
							} else if (e.getValue().trim().startsWith("3")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_3_Rechazado);
							} else if (e.getValue().trim().startsWith("4")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_4_EnEspera);
							} else if (e.getValue().trim().startsWith("5")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_5_Autorizado);
								respuesta = true;
							} else if (e.getValue().trim().startsWith("6")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_6_Anulado);

							} else if (e.getValue().trim().startsWith("8")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_8_RechazadoPorDGI);

							} else if (e.getValue().trim().startsWith("9")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_9_EnDigitacion);
							}

						}
						if (e.getName().equalsIgnoreCase("CFEErrDesc")) {
							if (!errores.contains(e.getValue())) {
								errores.add(e.getValue());

							}

						}
						if (e.getName().equalsIgnoreCase("CFENro")) {
							numero = e.getValue().trim();
						}
						if (e.getName().equalsIgnoreCase("CFESerie")) {
							serie = e.getValue().trim();
						}

					}
				}
			}

		} else {
			throw new AdempiereException("el archivo " + source.getName()
					+ " no se puede leer");
		}
		for (String s : errores) {
			if(status.getDescription()==null){
				status.setDescription(s);
			}
			else{
				status.setDescription((status.getDescription()+ " - "+ s));
			}
			
		}

		MInvoice invoice = MInvoice.getforNDocument(status.getCtx(), serie+ numero, status.get_TrxName());
		status.setC_Invoice_ID(invoice.get_ID());
		return respuesta;
	}

	/**
	 * Metodo para copiar archivos de un lugar a otro OpenUp Ltda. Issue #2208
	 * 
	 * @author Leonardo Boccone - 04/07/2014
	 * @see
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File destFile)
			throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel origen = null;
		FileChannel destino = null;
		try {
			origen = new FileInputStream(sourceFile).getChannel();
			destino = new FileOutputStream(destFile).getChannel();

			long count = 0;
			long size = origen.size();
			while ((count += destino.transferFrom(origen, count, size - count)) < size)
				;
		} finally {
			if (origen != null) {
				origen.close();
			}
			if (destino != null) {
				destino.close();
			}
		}
	}

	/**
	 * traigo la ruta de la ventana de parametrizacion donde estan los archivos
	 * de retorno OpenUp Ltda. Issue #2208
	 * 
	 * @author Leonardo Boccone - 04/07/2014
	 * @see
	 */
	private void getRuta() {
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		sql = "select * from uy_cfe";
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rutaSalida = rs.getString("salida");

			}
		} catch (Exception e) {

			throw new AdempiereException(e);

		}

	}

}
