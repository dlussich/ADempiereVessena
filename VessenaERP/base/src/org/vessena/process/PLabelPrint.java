package org.openup.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MLabelPrintLine;
import datamaxoneil.connection.ConnectionBase;
import datamaxoneil.connection.Connection_Serial;
import datamaxoneil.connection.Connection_Serial.StopBits;
import datamaxoneil.printer.DocumentDPL;
import datamaxoneil.printer.ParametersDPL;
import datamaxoneil.printer.configuration.GeneralConfiguration.Handshake;
import datamaxoneil.printer.configuration.GeneralConfiguration.Parity;

/**
 * 
 * org.openup.process - PLabelPrint OpenUp Ltda. Issue # 2048 Description:
 * Creacione impresion de etiquetas par a impresora datamax oneil E-Class
 * MarkIII
 * 
 * @author Leonardo Boccone - 25/04/2014
 * @see
 */
public class PLabelPrint extends SvrProcess {

	private int LabelPrint_ID = 0;
	private List<MLabelPrintLine> lineas = new ArrayList<MLabelPrintLine>();

	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {

				if (name.equalsIgnoreCase("UY_LabelPrint_ID")) {
					if (para[i].getParameter() != null) {
						this.LabelPrint_ID = ((BigDecimal) para[i]
								.getParameter()).intValueExact();
					}
				}

			}
		}

	}

	@Override
	protected String doIt() throws Exception {
		System.out.println("doIt");
		this.execute();
		
		return "OK";
	}

	/*
	 * Utilizamos las clases del jar labelPrint para crear el documento y la
	 * conexion atraves del puerto serial que nos da el jar RXTXcomm
	 */

	private void execute() {

		/*
		// creamos documento y parametros
		DocumentDPL docDPL = new DocumentDPL();
		ParametersDPL paramDPL = new ParametersDPL();
		// creamos la conexion
		ConnectionBase btConn = Connection_Serial.createClient("COM1", 9600,
				Parity.None, 8, StopBits.One, Handshake.None);
		try {

			// abrimos conexion
			btConn.open();
		// traemos las lineas por el id del cabezal
		lineas = MLabelPrintLine.getLineas(getCtx(), this.LabelPrint_ID,
				get_TrxName());

		for (MLabelPrintLine l : lineas) {
			// traemos el producto de cada linea
			MProduct prod = new MProduct(getCtx(), l.getM_Product_ID(),
					get_TrxName());
			String size = prod.get_Value("labelprinttype").toString();

			if (size.equalsIgnoreCase("Grande")) {
				String cambio = "Para cambio conserve la etiqueta";
				
				// OpenUp. 06/10/2014. #
				//String nombre = "Nombre: " + prod.getName();
				//String talle = "Talle: ";
				
				String nombre = " " + manejodeNombre(prod.getName());
				String talle = " ";
				
				// Fin OpenUp. #

					int qtyPrint = l.getQty().intValue();
					
						docDPL.clear();
						// creamos textos de la etiqueta
						docDPL.writeTextScalable(cambio, "00", 190, 570);
						docDPL.writeTextScalable(cambio, "00", 190, 40);
						docDPL.writeTextScalable(nombre, "00", 160, 570);
						docDPL.writeTextScalable(nombre, "00", 160, 40);

						paramDPL.setFontHeight(12);
						paramDPL.setFontWidth(12);
						paramDPL.setRotate(ParametersDPL.Rotation.Rotate_90);
						docDPL.writeTextScalable(talle, "00", 170, 470,
								paramDPL);
						docDPL.writeTextScalable(talle, "00", 170, 990,
								paramDPL);
						paramDPL.setRotate(ParametersDPL.Rotation.Rotate_0);

						// creamos codigos de barras de las etiquetas
						// EAN Code 128
						paramDPL.setIsUnicode(false);
						paramDPL.setWideBarWidth(2);
						paramDPL.setNarrowBarWidth(1);
						paramDPL.setSymbolHeight(90);
						docDPL.writeBarCode("E", prod.getUPC(), 20, 600,
								paramDPL);

						// EAN Code 128
						paramDPL.setIsUnicode(false);
						paramDPL.setWideBarWidth(2);
						paramDPL.setNarrowBarWidth(1);
						paramDPL.setSymbolHeight(90);
						docDPL.writeBarCode("E", prod.getUPC(), 20, 90,
								paramDPL);

						byte[] documentData = docDPL.getDocumentData();
						// imprimimos
					for (int i = 0; i < qtyPrint; i++) {
						btConn.write(documentData);

					}
					
				} 

			 else if (size.equalsIgnoreCase("Mediano")) {
					
				 System.out.println("Mediano");
					int qtyPrint = l.getQty().intValue();
					String nombre = manejodeNombre(prod.getName().trim());
					String primera = "";
					if(nombre.length()>=16){
						primera = nombre.substring(0, 16);		
					}
					else{
						primera = nombre;
					}
	
					String segunda="";
					if(nombre.length()>16){
					  segunda = nombre.substring(16);
					}																	
						docDPL.clear();						
						
						docDPL.writeTextInternalBitmapped(primera, 0,  165, 80);
						docDPL.writeTextInternalBitmapped(segunda, 0, 145, 80);
						
						docDPL.writeTextInternalBitmapped(primera, 0,  165, 405);
						docDPL.writeTextInternalBitmapped(segunda, 0, 145, 405);
						
						docDPL.writeTextInternalBitmapped(primera, 0,  165, 740);
						docDPL.writeTextInternalBitmapped(segunda, 0, 145, 740);
						
						// creamos codigos de barras de las etiquetas
						// EAN Code 128
						paramDPL.setIsUnicode(false);
						paramDPL.setWideBarWidth(1);
						paramDPL.setNarrowBarWidth(1);
						paramDPL.setSymbolHeight(120);
						docDPL.writeBarCode("Q", prod.getUPC(),0,760,
								paramDPL);

						// EAN Code 128
						paramDPL.setIsUnicode(false);
						paramDPL.setWideBarWidth(1);
						paramDPL.setNarrowBarWidth(1);
						paramDPL.setSymbolHeight(120);
						docDPL.writeBarCode("Q", prod.getUPC(), 0, 100,
								paramDPL);

						// EAN Code 128
						paramDPL.setIsUnicode(false);
						paramDPL.setWideBarWidth(1);
						paramDPL.setNarrowBarWidth(1);
						paramDPL.setSymbolHeight(120);
						docDPL.writeBarCode("Q", prod.getUPC(), 0, 425,
								paramDPL);

						byte[] documentData = docDPL.getDocumentData();
						// imprimimos
					for (int i = 0; i < qtyPrint; i++) {
						btConn.write(documentData);

					}
		

				
			}
			 else if (size.equalsIgnoreCase("Chico")) {
				 		
				 		String cod= prod.getUPC().substring(1);
				 		
						int qtyPrint = l.getQty().intValue();
							
							docDPL.clear();
														
							paramDPL.setRotate(ParametersDPL.Rotation.Rotate_90);
							// creamos codigos de barras de las etiquetas	
							paramDPL.setIsUnicode(false);
							paramDPL.setWideBarWidth(2);
							paramDPL.setNarrowBarWidth(1);
							paramDPL.setSymbolHeight(80);
							docDPL.writeBarCode("d",cod,130,50,paramDPL);
							
							paramDPL.setIsUnicode(false);
							paramDPL.setWideBarWidth(2);
							paramDPL.setNarrowBarWidth(1);
							paramDPL.setSymbolHeight(80);
							docDPL.writeBarCode("d",cod,130,200,paramDPL);
							
							paramDPL.setIsUnicode(false);
							paramDPL.setWideBarWidth(2);
							paramDPL.setNarrowBarWidth(1);
							paramDPL.setSymbolHeight(80);
							docDPL.writeBarCode("d",cod,130,350, paramDPL);
							
							paramDPL.setIsUnicode(false);
							paramDPL.setWideBarWidth(2);
							paramDPL.setNarrowBarWidth(1);
							paramDPL.setSymbolHeight(80);
							docDPL.writeBarCode("d",cod,130,500,paramDPL);
							
							paramDPL.setIsUnicode(false);
							paramDPL.setWideBarWidth(2);
							paramDPL.setNarrowBarWidth(1);
							paramDPL.setSymbolHeight(80);
							docDPL.writeBarCode("d",cod,130,650,paramDPL);

							paramDPL.setIsUnicode(false);
							paramDPL.setWideBarWidth(2);
							paramDPL.setNarrowBarWidth(1);
							paramDPL.setSymbolHeight(80);
							docDPL.writeBarCode("d",cod,130,800, paramDPL);
							
							paramDPL.setIsUnicode(false);
							paramDPL.setWideBarWidth(2);
							paramDPL.setNarrowBarWidth(1);
							paramDPL.setSymbolHeight(80);
							docDPL.writeBarCode("d",cod,130,950, paramDPL);
					
							byte[] documentData = docDPL.getDocumentData();
							// imprimimos
						for (int i = 0; i < qtyPrint; i++) {
							btConn.write(documentData);

							
						}
			 
			 }
		}
		// cerramos conexion
		btConn.setIsClosing(true);
		// break;
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
			if (e.getCause() != null) {
				System.out.println(e.getCause().getMessage());
			}
		}
		*/

	}
/**
 * Metodo para comprimi el nombre del producto y asi entre en la descripcion de la etiqueta mediana
 * OpenUp Ltda. Issue # 2504
 * @author Leonardo Boccone - 04/08/2014
 * @see
 * @param name
 * @return
 */
	private String manejodeNombre(String name) {
		String nuevo = name;
		if(name.contains("Hombre")||name.contains("hombre")){
			nuevo = nuevo.replace("Hombre", "H");
			nuevo = nuevo.replace("hombre", "H");
		}
		if(name.contains("Adulto")||name.contains("adulto")){
			nuevo = nuevo.replace("Adulto", "Ad");
			nuevo = nuevo.replace("adulto", "Ad");
		}
		else if(name.contains("Homb")||name.contains("homb")){
			nuevo = nuevo.replace("Homb", "H");
			nuevo = nuevo.replace("homb", "H");
		}
		if(name.contains("Dama")||name.contains("dama")){
			nuevo = nuevo.replace("Dama", "D");
			nuevo = nuevo.replace("dama", "D");
		}
		if(name.contains("color")||name.contains("col.")){
			nuevo = nuevo.replace("color", "");
			nuevo = nuevo.replace("col.", "");
		}
		if(name.contains("capitoneado")){
			nuevo = nuevo.replace("capitoneado", "capi");
		}
		if(name.contains("microfibra")){
			nuevo = nuevo.replace("microfibra", "micro");
		}
		if(name.contains("Impermeable")||name.contains("impermeable")){
			nuevo = nuevo.replace("Impermeable", "Imp");
			nuevo = nuevo.replace("impermeable", "Imp");
		}
		if(name.contains("Pantalón")||name.contains("Pantalon")||name.contains("pantalon")){
			nuevo = nuevo.replace("Pantalón", "Pan");
			nuevo = nuevo.replace("Pantalon", "Pan");
			nuevo = nuevo.replace("pantalon", "Pan");
		}	
		if(name.contains("Sombrero")||name.contains("sombrero")){
			nuevo = nuevo.replace("Sombrero", "Som");
			nuevo = nuevo.replace("sombrero", "Som");
		}	
		if(name.contains("Combinado")||name.contains("combinado")){
			nuevo = nuevo.replace("Combinado", "Com");
			nuevo = nuevo.replace("combinado", "com");
		}
		if(name.contains("Zapato")||name.contains("zapato")){
			nuevo = nuevo.replace("Zapato", "Zap");
			nuevo = nuevo.replace("zapato", "zap");
		}
		if(name.contains("Chaleco")||name.contains("chaleco")){
			nuevo = nuevo.replace("Chaleco", "Cha");
			nuevo = nuevo.replace("chaleco", "cha");
		}
		if(name.contains("Bombacha")||name.contains("bombacha")){
			nuevo = nuevo.replace("Bombacha", "Bomb");
			nuevo = nuevo.replace("bombacha", "Bomb");
		}
		if(name.contains("Tranquera")||name.contains("tranquera")){
			nuevo = nuevo.replace("Tranquera", "Tran");
			nuevo = nuevo.replace("tranquera", "Tran");
		}
		if(name.contains("Campo")||name.contains("campo")){
			nuevo = nuevo.replace("Campo", "Cam");
			nuevo = nuevo.replace("campo", "Cam");
		}
		if(name.contains("Chupin")||name.contains("chupin")){
			nuevo = nuevo.replace("Chupin", "Chu");
			nuevo = nuevo.replace("chupin", "Chu");
		}
		if(name.contains(" de ")){
			nuevo = nuevo.replace(" de ", " ");
		}
		if(name.contains("á")||name.contains("é")||name.contains("í")||name.contains("ó")||name.contains("ú")){
			nuevo = nuevo.replace("á", "a");
			nuevo = nuevo.replace("é", "e");
			nuevo = nuevo.replace("í", "i");
			nuevo = nuevo.replace("ó", "o");
			nuevo = nuevo.replace("ú", "u");
		}
		if(nuevo.length()>34){
			nuevo = nuevo.replace(" ", "");
		}
		
		return nuevo;
	}
}
