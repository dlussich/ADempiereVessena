/**
 * 
 */
package org.openup.process;

import org.compiere.model.MDocType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MDeliveryPoint;
import org.openup.model.MRReclamo;
import org.openup.model.MTTCard;
import org.openup.model.MTTCardStatus;
import org.openup.model.MTTCardTracking;
import org.openup.model.MTTControlSettings;
import org.openup.model.MTTDelPointRetReasons;
import org.openup.model.MTTDelivery;
import org.openup.model.MTTDeliveryPointStatus;
import org.openup.model.MTTReturnReasons;
import org.openup.model.MTTWebCourier;
import org.openup.model.MTTWebCourierLine;
import org.adempiere.exceptions.AdempiereException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * @author gbrust
 *
 */
public class PLoadShipmentsData extends SvrProcess {

	//private WebClient wb = new WebClient(BrowserVersion.CHROME_16);
	private WebClient wbMp = new WebClient(BrowserVersion.FIREFOX_24);
	private WebClient wb = new WebClient(BrowserVersion.CHROME);	
	private MDeliveryPoint courier = null;
	private boolean isManual = false;
	private HashMap<Integer, MTTCard> hashIncidencias = new HashMap<Integer, MTTCard>();
	MTTWebCourier hdr = null;
	
	public PLoadShipmentsData() {
		
		//BasicConfigurator.configure();	
		
		//Configuraciones iniciales
		wb = new WebClient(BrowserVersion.CHROME);
		wbMp = new WebClient(BrowserVersion.FIREFOX_24);
		//wb = new WebClient(BrowserVersion.CHROME_16); 
		// OpenUp. Sylvie - 16/10/14 cambio que dejo leo para subir... 
	}
	
	
	@Override
	protected void prepare() {		
		
		//Recibo parametros
		ProcessInfoParameter[] para = getParameter();		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){				
				if (name.equalsIgnoreCase("UY_DeliveryPoint_ID")){
					int courierID = ((BigDecimal)para[i].getParameter()).intValueExact();
					this.courier = 	new MDeliveryPoint(this.getCtx(), courierID, this.get_TrxName());
				}		
				if (name.equalsIgnoreCase("isManual")){
					this.isManual = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
				}
			}
		}		
	}

	
	@Override
	protected String doIt() throws Exception {
		
		try {
			Timestamp inicio = new Timestamp(System.currentTimeMillis());
			System.out.println("WEBCOURIER - Inicio: " + inicio);
			
			if(this.isManual){
				if(this.courier.getValue().equals("marcopostal")) this.getDataMarcoPostal();
				else if(this.courier.getValue().equals("plaza")) this.getDataPlazaCorreo();
				else if(this.courier.getValue().equals("interpost")) this.getDataInterpost();
			
			}else{
				this.getDataMarcoPostal();
				this.getDataPlazaCorreo();
				this.getDataInterpost();
			}
			
			this.incidenciasFaltantes();
			
			Timestamp fin = new Timestamp(System.currentTimeMillis());
			System.out.println("WEBCOURIER - Fin: " + fin);

		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AdempiereException(e);
		}
						
		return "Ok";
	}
	
	
	private void showHelp(String text){
		if (this.getProcessInfo().getWaiting() != null){
			this.getProcessInfo().getWaiting().setText(text);
		}			
	}
	
	
	/**
	 * MARCO POSTAL
	 * 
	 * */
	private void getDataMarcoPostal() throws Exception{
		this.loginMarcoPostal();
		this.viewShipmentsMarcoPostal();
	}	
	
	/**
	 * PLAZA CORREO
	 * 
	 * */
	private void getDataPlazaCorreo() throws Exception{
		this.loginPlazaCorreo();
		this.viewShipmentsPlazaCorreo();
	}	
	
	
	/**
	 * INTERPOST
	 * 
	 * */
	private void getDataInterpost() throws Exception{
		this.loginInterpost();
		this.viewShipmentsInterpost();
	}	
	
	private HtmlPage loginMarcoPostal() throws Exception {	
		
		HtmlPage resultPage = null;
		System.out.println("WEBCOURIER - Iniciando Login a MarcoPostal");
		this.showHelp("Iniciando Login a MarcoPostal...");
		
		try {
			
			//wb.getCache().clear();
			//wbMp.getOptions().setCssEnabled(true);
			//wbMp.getOptions().setJavaScriptEnabled(true);			
			
			wbMp.getOptions().setUseInsecureSSL(true);
					
			HtmlPage p = (HtmlPage) wbMp.getPage("http://www.marcopostal.com.uy/user/login");
			
			//Me logueo
			HtmlForm f = (HtmlForm) p.getElementById("user-login");
			HtmlTextInput username = (HtmlTextInput) f.getInputByName("name");
			HtmlPasswordInput pass = (HtmlPasswordInput) f.getInputByName("pass");
			HtmlSubmitInput submit = (HtmlSubmitInput) f.getInputByName("op");
			username.setValueAttribute("04096");
			pass.setValueAttribute("1Italcred");		 
			resultPage = (HtmlPage) submit.click();
					
			System.out.println("WEBCOURIER - Logueado a MarcoPostal");
			this.showHelp("Logueado a MarcoPostal");
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}		
		
		return resultPage;	
	}
	
	
	private HtmlPage viewShipmentsMarcoPostal() throws Exception{	
						
		//wb.getOptions().setCssEnabled(true);
		//wb.getOptions().setJavaScriptEnabled(true);			
		
		//Voy a la pagina de los envios
		System.out.println("Entrando en pagina de envios...");
		this.showHelp("Entrando en página de envíos...");
		
		HtmlPage p = (HtmlPage) wbMp.getPage("http://www.marcopostal.com.uy/user/114/envios");
		
		//Aca creo el modelo del cabezal
		this.hdr = new MTTWebCourier(this.getCtx(), 0, this.get_TrxName());
		this.hdr.setUY_DeliveryPoint_ID(this.courier.get_ID());
		this.hdr.setDateTrx(new Timestamp(System.currentTimeMillis()));
		this.hdr.setC_DocType_ID(MDocType.forValue(this.getCtx(), "webcourier", null).get_ID());
		
					
		//Obtengo todos los levantes pendientes de MARCO POSTAL			
		HashMap<String, String> levantes = this.courier.getLevantesPendientes();
		
		if(levantes.size() > 0) this.hdr.saveEx();
		else throw new AdempiereException("No existen levantes pendientes de este Courier");
		
		for (String levante: levantes.keySet()) {

			//Filtro por numero de levante
			System.out.println("Filtrando por numero de levante...");
			this.showHelp("Filtrando por número de levante...");
			
			HtmlForm f = (HtmlForm) p.getElementById("views-exposed-form-user-ships-page");
			HtmlTextInput levanteInput = (HtmlTextInput) f.getInputByName("field_pickup_number_value");		
			//HtmlSubmitInput submit = (HtmlSubmitInput) f.getElementById("edit-submit-user-ships"); //Se obtiebe por Id en vez de por f.getElementsByTagName
			HtmlSubmitInput submit = (HtmlSubmitInput) f.getInputByValue("Aplicar"); //Se obtiebe por Id en vez de por f.getElementsByTagName
			levanteInput.setValueAttribute(levante);				
			p = (HtmlPage) submit.click();
			
			int pagActual = 1;
			int pagFinal = 1;		

			while(pagActual <= pagFinal){
				
				System.out.println("Leyendo pagina " + pagActual + "...");
				this.showHelp("Leyendo página " + pagActual + "...");
				
				//Voy a la grilla donde se encuentran los datos
				HtmlTable table = (HtmlTable) p.getFirstByXPath("//table[@class='views-table cols-10']");
				if(table!=null){//Ini Issue # 3220 06-11-2014 se controla que para el levante el resultado no sea vacío.

				HtmlTableBody tbody = (HtmlTableBody) table.getBodies().get(0);		
							
				List<HtmlTableRow> filas = tbody.getHtmlElementsByTagName("tr");				
				
				MTTWebCourierLine line = null;
				
				for (int i = 0; i < filas.size(); i++) {				
					
					int registro = i + 1;
					System.out.println("Registro " + registro + "...");	
					this.showHelp("Página " + pagActual + " - Registro " + registro + "...");
					
					//Creo un nuevo modelo
					line = new MTTWebCourierLine(getCtx(), 0, this.get_TrxName());
					line.setUY_TT_WebCourier_ID(this.hdr.get_ID());
					line.setLevante(levante);
					line.setPieza("TARJETA");
					
					List<HtmlTableDataCell> columnas = filas.get(i).getHtmlElementsByTagName("td");		
					
					//Aca debo preguntar si existe un estado para este envio guardado en adempiere, y ademas si este estado guardado
					//es esta definido como que esta en poder del courier o no.
					String documentNoIncidencia = columnas.get(3).getChildNodes().get(0).getNodeValue();
					MTTCard card = null;					
					MTTCardTracking cardTrack = null;
					
					if(documentNoIncidencia != null && !(documentNoIncidencia.trim().equals(""))) {// Se agrega control porque en el courier hay cuentas con valor vacío - S.Bouissa 10-11-2014 Issue # 3220
						documentNoIncidencia = documentNoIncidencia.trim();
						documentNoIncidencia = documentNoIncidencia.replaceFirst ("^0*", ""); // Se agrega control porque en el courier hay cuentas con ceros al inicio - S.Bouissa 10-11-2014 Issue # 3220
												

						MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), documentNoIncidencia, null);
						if (incidencia == null) incidencia = new MRReclamo(getCtx(), 0, null); 
						
						if (incidencia.get_ID() > 0){
							if (!this.hashIncidencias.containsKey(incidencia.get_ID())){
								this.hashIncidencias.put(incidencia.get_ID(), null);	
							}
							line.setUY_R_Reclamo_ID(incidencia.get_ID());
						}
						
						//Se agrega codigo para verificar que si el estado que leo en la web es entregado, si la cuenta tenia el estado entregado no hago nada, si no tenia el estado entregado lo actualizo 
						boolean isInCourier = true;
						String estadoCourier = columnas.get(2).getChildNodes().get(0).getNodeValue().trim();
						MTTDeliveryPointStatus status = MTTDeliveryPointStatus.getMTTDeliveryPointStatusForEstado(getCtx(), get_TrxName(), this.courier.get_ID(), estadoCourier.trim());
										
						if(status != null && status.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "entregada").get_ID()){					
							
							MTTCard cardAux = null;
							
							if (incidencia.get_ID() > 0){
								cardAux = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(), hdr.getUY_DeliveryPoint_ID(), this.get_TrxName());	
							}

							if(cardAux != null){
								if(cardAux.getUY_TT_CardStatus_ID() == status.getUY_TT_CardStatus_ID()){
									isInCourier = false;										
								}
						    //OpenUp Issue# 3447 - Sylvie Bouissa 19-01-2015
							//Se agrega condicion porque sino sigue con el proceso y no corresponde	ya que la cuenta se proceso anteriormente
							}else{ 
								isInCourier = false;
							}
						}else{
							isInCourier = MTTWebCourierLine.getIsInCourierForIncidencia(getCtx(), incidencia.get_ID(), levante, this.get_TrxName());
							if(isInCourier){
								MTTCard cardAux = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(), hdr.getUY_DeliveryPoint_ID(),this.get_TrxName());
								if(cardAux==null){
									isInCourier = false;
								}
							//OpenUp Issue# 3447 - Sylvie Bouissa 19-01-2015	
							//Se agrega condición ya que si la cuenta tiene como pto actual el courier que estoy procesando es necesario se tenga en cuenta esta cuenta.	
							}else{
								MTTCard cardAux = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(),this.get_TrxName());
								if(cardAux != null){
									if(cardAux.getUY_DeliveryPoint_ID_Actual() == this.courier.get_ID()){
										isInCourier = true;
									}
								}
							}
						}				
						//Fin OpenUp.		
						
						if(isInCourier){							
							for (int j = 0; j < columnas.size(); j++) {								
								
								switch (j) {
									case 0:									
										String envio = columnas.get(j).getChildNodes().get(0).getNextSibling().getChildNodes().get(0).getNodeValue().trim();
										line.setDeliveryNo(envio);
										break;
									case 1:	
										int bordeFecha = columnas.get(j).getChildNodes().get(0).getNextSibling().getAttributes().item(3).getNodeValue().indexOf("T");
										String fechaEnvio = columnas.get(j).getChildNodes().get(0).getNextSibling().getAttributes().item(3).getNodeValue().substring(0, bordeFecha) + " 00:00:00";
										line.setDateDelivery(Timestamp.valueOf(fechaEnvio));
										break;
									case 2:
										estadoCourier = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();
										status = MTTDeliveryPointStatus.getMTTDeliveryPointStatusForEstado(getCtx(), get_TrxName(), this.courier.get_ID(), estadoCourier);
										MTTDelPointRetReasons motivo = null;
										
										if(status != null){
											line.setUY_TT_CardStatus_ID(status.getUY_TT_CardStatus_ID());
											line.setUY_TT_DeliveryPointStatus_ID(status.get_ID());
											
											if(estadoCourier.equals("Entregado")){										
												//Aca voy a obtener el nombre y cedula de quien recibe
												HtmlAnchor ver = (HtmlAnchor) columnas.get(j + 7).getChildNodes().get(0).getNextSibling();
												HtmlPage resultPage = (HtmlPage) ver.openLinkInNewWindow();	

												java.lang.Thread.sleep(5000);
																							
												HtmlTableDataCell celda = (HtmlTableDataCell) resultPage.getFirstByXPath("//table[@class='pickup-data']/tbody/tr/td");
												if(celda.getChildNodes().get(0).getNodeValue().trim().split(":").length > 1) {
													line.setName2(celda.getChildNodes().get(0).getNodeValue().trim().split(":")[1]);
												}												
												if(celda.getChildNodes().get(2).getNodeValue().trim().split(":").length > 1){
													line.setCedula(celda.getChildNodes().get(2).getNodeValue().trim().split(":")[1]);
												}															
												
											}else if(estadoCourier.toUpperCase().equals("DEVUELTO") || estadoCourier.toUpperCase().equals("MOTIVADA") || estadoCourier.toUpperCase().equals("CON AVISO")){
												//Aca voy a obtener el motivo
												HtmlAnchor ver = (HtmlAnchor) columnas.get(j + 7).getChildNodes().get(0).getNextSibling();
												HtmlPage resultPage = (HtmlPage) ver.openLinkInNewWindow();
												
												java.lang.Thread.sleep(5000);
												
												HtmlTableDataCell celda = (HtmlTableDataCell) resultPage.getFirstByXPath("//table[@class='views-table cols-5']/tbody/tr/td[@class='views-field views-field-field-state-motivation']");
												String motivoCourier = celda.getChildNodes().get(0).getNodeValue().trim();
												
												//Obtengo el mapeo de motivos, del courier y de Italcred
												motivo = MTTDelPointRetReasons.forCourierIDAndMotivo(this.getCtx(), this.get_TrxName(), this.courier.get_ID(), motivoCourier);
												
												if(motivo != null){
													line.setUY_TT_DelPointRetReasons_ID(motivo.get_ID());
													line.setUY_TT_ReturnReasons_ID(motivo.getUY_TT_ReturnReasons_ID());
												
												}else{
													//Guardo faltante de parametrizacion en el log
													String msj = "Falta parametrizar motivo de devolución: " + motivoCourier;
													
													//Primero pregunto si ya no guarde este mensaje para esta carga
													if(MTTControlSettings.forMensaje(getCtx(), msj, this.hdr.get_ID(), this.get_TrxName()) == null){
														
														MTTControlSettings log = new MTTControlSettings(getCtx(), 0, this.get_TrxName());
														log.setDateTrx(new Timestamp(System.currentTimeMillis()));
														log.setUY_TT_WebCourier_ID(this.hdr.get_ID());
														log.setMessage(msj);
														
														log.saveEx();									
													}			
												}
											}							
											
											//Y a parte tengo que modificar el estado de la tarjeta si la cuenta tiene como destino el courier que estoy procesando
											boolean cuentaValida = MTTCard.existsIncidenciaAndDeliveryPoint(this.getCtx(), this.get_TrxName(), incidencia.get_ID(), this.courier.get_ID());
											if(cuentaValida){
												
												card = MTTCard.forIncidenciaLevante(getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(), this.get_TrxName());
												if(card != null && card.get_ID() > 0){
													
													//Si el estado de la cuenta es EN COORDINACION y el motivo no esta parametrizado, no se realiza ninguna actualizacion
													if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "devuelta").get_ID()){
														
														if(motivo == null){
															break;
														}
													}
													
													//Verifico el estado que tiene la tarjeta, si es el mismo que se esta leyendo en esta lectura, no debo actualizar los trackings										
													if(card.getUY_TT_CardStatus_ID() == status.getUY_TT_CardStatus_ID()){
														break;
													}
													
													//Actualizo estado de tarjeta
													card.setUY_TT_CardStatus_ID(status.getUY_TT_CardStatus_ID());
													card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
													card.saveEx();
													
													// Tracking cuenta
													cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
													cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
													cardTrack.setAD_User_ID(this.getAD_User_ID());
													cardTrack.setDescription("Lectura " + this.courier.getName() + ": " + ((MTTCardStatus) card.getUY_TT_CardStatus()).getName());												
													cardTrack.setUY_TT_Card_ID(card.get_ID());
													cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
													cardTrack.setUY_DeliveryPoint_ID_Actual(this.courier.get_ID());
													//cardTrack.saveEx();
													
												}											
											}								
										
										}else{
											//Guardo faltante de parametrizacion en el log
											String msj = "Falta parametrizar estado de tarjeta: " + estadoCourier;
											
											//Primero pregunto si ya no guarde este mensaje para esta carga
											if(MTTControlSettings.forMensaje(getCtx(), msj, this.hdr.get_ID(), this.get_TrxName()) == null){
												
												MTTControlSettings log = new MTTControlSettings(getCtx(), 0, this.get_TrxName());
												log.setDateTrx(new Timestamp(System.currentTimeMillis()));
												log.setUY_TT_WebCourier_ID(this.hdr.get_ID());
												log.setMessage(msj);
												
												log.saveEx();									
											}			
										}									
																		
										break;
									case 3:
										if (card != null){
											line.setAccountNo(card.getAccountNo());	
										}
										else{
											MTTCard cardAcct = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), get_TrxName());
											if (cardAcct != null) line.setAccountNo(cardAcct.getAccountNo());
										}
										break;
									case 4:
										String nombre = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();
										line.setName(nombre);
										break;
									case 5:
										String direccion = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();
										line.setAddress1(direccion);
										break;
									case 6:	
										String localidad = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();
										line.setlocalidad(localidad);
										break;
									case 7:	
										if(columnas.get(j).getChildNodes().get(0).getNextSibling() != null){
											int bordeFechaMov = columnas.get(j).getChildNodes().get(0).getNextSibling().getAttributes().item(3).getNodeValue().indexOf("T");
											String fechaMov = columnas.get(j).getChildNodes().get(0).getNextSibling().getAttributes().item(3).getNodeValue().substring(0, bordeFechaMov) + " 00:00:00";
											line.setDateTrx(Timestamp.valueOf(fechaMov));
										}
										break;
				
									default:
										break;
								}
							}	
							line.saveEx();		
							
							//Aca termino el tracking
							if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "entregada").get_ID()){
								if(cardTrack != null){
									if(line.getName2() != null && line.getCedula() != null){
										cardTrack.setobservaciones("Entregado a " + line.getName2() + " CI: " + line.getCedula());										
									}else if(line.getName2() != null && line.getCedula() == null){
										cardTrack.setobservaciones("Entregado a " + line.getName2());
									}else if(line.getName2() == null && line.getCedula() != null){
										cardTrack.setobservaciones("Entregado a CI: " + line.getCedula());
									}else{
										cardTrack.setobservaciones("No hay datos de receptor");
									}
									//cardTrack.saveEx();
								}
								
								if(card != null) card.closeTrackingDelivered(null);
						
							}else if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "devuelta").get_ID()){
								if(cardTrack != null){
									if(line.getUY_TT_ReturnReasons_ID() > 0){
										cardTrack.setobservaciones("Devuelto por: " + ((MTTDelPointRetReasons) line.getUY_TT_DelPointRetReasons()).getName());
									}else{
										cardTrack.setobservaciones("No hay motivo de devolución");
									}
									//cardTrack.saveEx();
								}
							}
							
							if(cardTrack != null){
								cardTrack.saveEx();
								
								if(card != null){
									//Ahora pregunto si el estado que reporta el courier es un estado que marca que ya no esta mas en manos de el
									if(!((MTTDeliveryPointStatus) line.getUY_TT_DeliveryPointStatus()).isInCourier()){
										
										this.modificarPendientes(incidencia.get_ID(), levante);
									}	
								}		
							}
						}					
					}										
				}
				}//Fin Issue # 3220 06-11-2014
				//Cambio de pagina
				System.out.println("Cambiando pagina...");
				this.showHelp("Cambiando página...");
				
				//Siempre agrego uno a la pagina actual, asi sale del while
				pagActual++;
				
				//Solo si hay paginado
				if(p.getByXPath("//ul[@class='pager']").size() > 0){
					
					HtmlUnorderedList ulPaginado = (HtmlUnorderedList) p.getByXPath("//ul[@class='pager']").get(0);
					
					if(ulPaginado != null){
						
						if(ulPaginado.getByXPath("//li[@class='pager-next']").size() > 0){
							
							HtmlListItem listenItem = (HtmlListItem) ulPaginado.getByXPath("//li[@class='pager-next']").get(0);
							
							if(listenItem != null){
								
								HtmlAnchor htmlAnchor = (HtmlAnchor) listenItem.getFirstChild();
								
								if(htmlAnchor != null){
									
									if(htmlAnchor.getTextContent().trim().contains("siguiente")){						
										p = (HtmlPage) htmlAnchor.click();										
										pagFinal++;							
									}
								}							
							}		
						}								
					}
				}
			}
		}	
						
		return p;	
	}
	
	
	private HtmlPage loginPlazaCorreo() throws Exception {	
		
		HtmlPage resultPage = null;
		System.out.println("WEBCOURIER - Iniciando Login a Plaza");
		this.showHelp("Iniciando Login a Plaza...");

		try {
			//wb.setUseInsecureSSL(true);
			//wb.setThrowExceptionOnScriptError(false); ver tema de .jar utilizados
			
			wb.getOptions().setUseInsecureSSL(true);
			
			HtmlPage p = (HtmlPage) wb.getPage("https://tracking.plazacorreo.com.uy/");
			
			//Me logueo
			HtmlForm f = (HtmlForm) p.getElementById("form1");
			HtmlTextInput username = (HtmlTextInput) f.getInputByName("Login1$UserName");
			HtmlPasswordInput pass = (HtmlPasswordInput) f.getInputByName("Login1$Password");
			HtmlSubmitInput submit = (HtmlSubmitInput) f.getInputByName("Login1$LoginButton");
			username.setValueAttribute("Italcred-OP");
			pass.setValueAttribute("Italcredop1328");		 
			resultPage = (HtmlPage) submit.click();
			
			System.out.println("WEBCOURIER - Logueado a Plaza");
			this.showHelp("Logueado a Plaza...");
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return resultPage;	
	}
	

	private HtmlPage viewShipmentsPlazaCorreo() {

		try {
			Timestamp inicio = new Timestamp(System.currentTimeMillis());
			System.out.println("WEBCOURIER - Iniciando Lectura Plaza : " + inicio);
			//wb.setThrowExceptionOnScriptError(false);	 ver tema de .jar utilizados		
			wb.getOptions().setCssEnabled(true);
			wb.getOptions().setJavaScriptEnabled(true);				
							
			//Voy a la pagina de los envios
			System.out.println("Entrando en pagina de envios...");
			this.showHelp("Entrando en página de envíos...");
			
			HtmlPage p = (HtmlPage) wb.getPage("https://tracking.plazacorreo.com.uy/Busqueda.aspx");	
			
			//Aca creo el modelo del cabezal
			this.hdr = new MTTWebCourier(this.getCtx(), 0, this.get_TrxName());
			this.hdr.setUY_DeliveryPoint_ID(this.courier.get_ID());
			this.hdr.setDateTrx(new Timestamp(System.currentTimeMillis()));
			this.hdr.setC_DocType_ID(MDocType.forValue(this.getCtx(), "webcourier", null).get_ID());
				
			//Obtengo todos los levantes pendientes de PLAZA CORREO	
			HashMap<String, String> levantes = this.courier.getLevantesPendientes();		
			
			if(levantes.size() > 0) this.hdr.saveEx();
			else throw new AdempiereException("No existen levantes pendientes de este Courier");
			
			for (String levante: levantes.keySet()) {
				
				//En el caso de Plaza tambien necesito la fecha en que se realiza el levante.
				String fechaLevante = levantes.get(levante);
				System.out.println(levante);
				
				//Aca pasa de que la pagina tiene filtrado por pieza, y en el caso de las tarjetas hay una pieza para tarjetas repartidas en montevideo,
				//y otra pieza para tarjetas repartidas en interior, y dado que no se puede filtrar por mas de una pieza a la vez, se deberea realizar el proceso
				//de guardado de lineas dos veces, una para cada pieza.
				
				wb.getOptions().setCssEnabled(true);
				wb.getOptions().setJavaScriptEnabled(true);				
				
				wb.getOptions().setUseInsecureSSL(true);
				
				p = (HtmlPage) wb.getPage("https://tracking.plazacorreo.com.uy/Busqueda.aspx");
				
				if(p!=null){
					this.NewLines(p, this.hdr, levante, fechaLevante, "");
				}
					
				/*
							
				//Tengo dejar el navegador como estaba antes, ya que en el metodo anterior sufre algunos cambios que genera error al princio del mismo metodo
				wb.getOptions().setCssEnabled(true);
				wb.getOptions().setJavaScriptEnabled(true);
				p = (HtmlPage) wb.getPage("https://tracking.plazacorreo.com.uy/Busqueda.aspx");
				
				this.NewLines(p, this.hdr, levante, fechaLevante, "Tarj. Interior");	
				
				*/
				//}//testings	
			}	

			Timestamp fin = new Timestamp(System.currentTimeMillis());
			System.out.println("WEBCOURIER - Fin Lectura Plaza : " + fin);
			
			return p;			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	
	}


	private void NewLines(HtmlPage p, MTTWebCourier hdr, String levante, String fechaLevante, String pieza) throws Exception {
		//Filtro por numero de levante
		System.out.println("Filtrando por numero de levante...");
		this.showHelp("Filtrando por número de levante...");
		
		HtmlForm f = (HtmlForm) p.getElementById("form1");					
		HtmlTextInput checkNroRetiro = (HtmlTextInput) f.getInputByName("ASPxRoundPanel1$ASPxRadioButtonList1$RB3");
		p = (HtmlPage) checkNroRetiro.click();
		
		//if(p.getElementById("ASPxRoundPanel1_cboPiezas_DDD_L_LBI18T0") == null) throw new AdempiereException("No es posible realizar la lectura de los datos, es posible que la estructura del sitio haya cambiado");
		//if(p.getElementById("ASPxRoundPanel1_cboPiezas_DDD_L_LBI19T0") == null) throw new AdempiereException("No es posible realizar la lectura de los datos, es posible que la estructura del sitio haya cambiado");
		
		//if(pieza.equals("Tarj. Interior")) p = (HtmlPage) ((HtmlTableDataCell) p.getElementById("ASPxRoundPanel1_cboPiezas_DDD_L_LBI18T0")).click();
		//else if(pieza.equals("Tarj. Montevideo")) p = (HtmlPage) ((HtmlTableDataCell) p.getElementById("ASPxRoundPanel1_cboPiezas_DDD_L_LBI19T0")).click();
		
		//Prueba pieza ITALCRED
		//else if(pieza.equals("Tarj. Montevideo")) p = (HtmlPage) ((HtmlTableDataCell) p.getElementById("ASPxRoundPanel1_cboPiezas_DDD_L_LBI15T0")).click();
				
		f = (HtmlForm) p.getElementById("form1");
		HtmlTextInput fechaInput = (HtmlTextInput) f.getInputByName("ASPxRoundPanel1$ASPxRoundPanel3$dtpDFecha");			
		HtmlTextInput busqueda = (HtmlTextInput) f.getInputByName("ASPxRoundPanel1$txtTexto");				
		busqueda.setValueAttribute(levante);
		fechaInput.setValueAttribute(fechaLevante);
		
		HtmlSubmitInput submitInput = (HtmlSubmitInput) f.getInputByName("ASPxRoundPanel1$bBuscar");
		p = (HtmlPage) submitInput.click();	
		
		p.asText();
									
		//Exporto archivo excel
		InputStream download= null;			
		FileInputStream in = null;
        FileOutputStream out = null;			
		
        //HtmlTable table = (HtmlTable) p.getFirstByXPath("//table[@id='ASPxRoundPanel1_bExportar']");
        //OpenUp sylvie Bouissa Issue #3392
        //Se sustituye linea anterior ya que cambia el id de la tabla y se agrega consulta por si no hay datos
        HtmlTable table = (HtmlTable) p.getFirstByXPath("//table[@id='ASPxRoundPanel1']");		
        HtmlTable table2 = (HtmlTable) p.getFirstByXPath("//table[@id='ASPxRoundPanel2_grilla']");
        if(table2 == null){
        	System.out.println("No hay datos para el levante "+levante+"...");
        }
		System.out.println("Bajando archivo...");
		download = table.click().getWebResponse().getContentAsStream();
										
		// write the inputStream to a FileOutputStream
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String fechaActual = df.format(new Timestamp(System.currentTimeMillis()));	
		
		String rutaArchivo = "";
		
		boolean isWindows = false;
		String os = System.getProperty("os.name");
		if (os != null){
			if (os.toLowerCase().trim().contains("win")){
				isWindows = true;
			}
		}
		
		if (isWindows){
			if(new File("\\CourierData").isDirectory() && new File("\\CourierData").exists()) rutaArchivo = "\\CourierData\\levante_" + levante + "_" + fechaActual + ".xls";	
			else{
				if(new File("\\CourierData").mkdir()) rutaArchivo = "\\CourierData\\levante_" + levante + "_" + fechaActual + ".xls";	
				else throw new AdempiereException("No es puede crear el directorio temporal");
			}    
		}
		else{
			
			if(new File("//home//gabriel//openup").isDirectory() && new File("//home//gabriel//openup").exists()){
				rutaArchivo = "//home//gabriel//openup//levante_" + levante + "_" + fechaActual + ".xls";	
			}
			else{
				if(new File("//home//gabriel//openup").mkdir()) rutaArchivo = "//home//gabriel//openup//levante_" + levante + "_" + fechaActual + ".xls";	
				else throw new AdempiereException("No es puede crear el directorio temporal");
			}
			 

			/*
			if(new File("//home//openup//CourierData").isDirectory() && new File("//home//openup//CourierData").exists()) rutaArchivo = "//home//openup//CourierData//levante_" + levante + "_" + fechaActual + ".xls";	
			else{
				if(new File("//home//openup//CourierData").mkdir()) rutaArchivo = "//home//openup//CourierData//levante_" + levante + "_" + fechaActual + ".xls";	
				else throw new AdempiereException("No es puede crear el directorio temporal");
			}
			*/
			   
		}
				
		File file = new File(rutaArchivo);	
		OutputStream xls = new FileOutputStream(file);
 
		int read = 0;
		byte[] bytes = new byte[1024];
 
		while ((read = download.read(bytes)) != -1) {
			xls.write(bytes, 0, read);
		}
 			
		System.out.println("Cambiando extension a archivo");		//Esto es un engaño, de otra manera es imposible leer los datos
		
		int index = file.getAbsolutePath().indexOf(".");		       
        String nombre = file.getAbsolutePath().substring(0, index);		        
        File nuevo = new File(nombre + ".html");
        @SuppressWarnings("deprecation")
		URL url = nuevo.toURL();
        
        in = new FileInputStream(file);
        out = new FileOutputStream(nuevo);
		 
		read = 0;
		bytes = new byte[1024];
 
		while ((read = in.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}		
			        
		System.out.println("Leyendo archivo...");
        //Ahora se debe leer el mismo para guardar los datos en la base de datos	
		wb.getOptions().setCssEnabled(false);
		wb.getOptions().setJavaScriptEnabled(false);
        
		HtmlPage pageData = (HtmlPage) wb.getPage(url);		
		
		if(pageData.getFirstByXPath("//table[@id='ASPxRoundPanel2_grilla']") == null){
			//Cierro Streams
			
			//wb.closeAllWindows();
			
			xls.flush();
			xls.close();
			download.close();	
			in.close();
			out.flush();
		    out.close(); 	    
		   	    
		    java.lang.Thread.sleep(5000);
		    
		    //Borro Archivos. GABRIEL COMENTO PARA VER ARCHIVOS.
		    /*
		    file.setWritable(true);
		    file.delete();	    
		    nuevo.setWritable(true);
		    nuevo.delete();
		    */	    
		    
		    //Me salgo del metodo.
			return;
		}
		
        HtmlTable tableData = (HtmlTable) pageData.getFirstByXPath("//table[@id='ASPxRoundPanel2_grilla']");		        
        HtmlTableBody tbody = (HtmlTableBody) tableData.getBodies().get(0);					
		List<HtmlTableRow> filas = tbody.getHtmlElementsByTagName("tr");				
		
		MTTWebCourierLine line = null;
		//Issue 3642
		String mgeErrorInicial = "";
		
		for (int i = 1; i < filas.size(); i++) {			
			
			System.out.println("Registro " + i + "...");
			this.showHelp("Única Página - Registro " + i + "...");
			
			
			List<HtmlTableDataCell> columnas = filas.get(i).getHtmlElementsByTagName("td");		
			
			//Aca debo preguntar si existe un estado para este envio guardado en adempiere, y ademas si este estado guardado
			//es esta definido como que esta en poder del courier o no.			
			String docNoIncidencia = columnas.get(1).getChildNodes().get(0).getNodeValue();
			MTTCard card = null;					
			MTTCardTracking cardTrack = null;
			
			if (docNoIncidencia == null || docNoIncidencia.equals("")) 
				continue;
			docNoIncidencia = docNoIncidencia.trim();
				
			
			MTTCard validateCard = null;
			MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), docNoIncidencia, null);
			if (incidencia == null) incidencia = new MRReclamo(getCtx(), 0, null);
			
			if (incidencia.get_ID() > 0){
				validateCard = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(), null);	
			}

			if ((validateCard == null) || (validateCard.get_ID() <= 0))
					continue;
			
			//Creo un nuevo modelo
			line = new MTTWebCourierLine(getCtx(), 0, this.get_TrxName());
			line.setUY_TT_WebCourier_ID(this.hdr.get_ID());
			line.setLevante(levante);
			line.setPieza("TARJETA");
			
			if (incidencia.get_ID() > 0){
				if (!this.hashIncidencias.containsKey(incidencia.get_ID())){
					this.hashIncidencias.put(incidencia.get_ID(), null);	
				}
				line.setUY_R_Reclamo_ID(incidencia.get_ID());
			}
			
			if(docNoIncidencia != null) {
				
				//OpenUp. Guillermo Brust. 13/11/2013. ISSUE#
				//Se agrega codigo para verificar que si el estado que leo en la web es entregado, si la cuenta tenia el estado entregado no hago nada, si no tenia el estado entregado lo actualizo 
				boolean isInCourier = true;
				String estadoCourier = columnas.get(9).getChildNodes().get(0).asText();
				MTTDeliveryPointStatus status = MTTDeliveryPointStatus.getMTTDeliveryPointStatusForEstado(getCtx(), get_TrxName(), this.courier.get_ID(), estadoCourier.trim());
				
				// Estados aceptados
				int idStEntregada = MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "entregada").get_ID();
				int idStDevuelta = MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "devuelta").get_ID();
				
				if(status != null && ((status.getUY_TT_CardStatus_ID() == idStEntregada) || (status.getUY_TT_CardStatus_ID() == idStDevuelta))){					
									
					MTTCard cardAux = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(), this.get_TrxName());
					
					if(cardAux != null){
						if(cardAux.getUY_TT_CardStatus_ID() == status.getUY_TT_CardStatus_ID()){
							isInCourier = false;										
						}
					}				
				}else{
					isInCourier = MTTWebCourierLine.getIsInCourierForIncidencia(getCtx(), incidencia.get_ID(), levante.trim(), this.get_TrxName());
					
					//OpenUp Issue# 3642 - Sylvie Bouissa 24-02-2015	
					//Se agrega condición ya que si la cuenta tiene como pto actual el courier que estoy procesando es necesario se tenga en cuenta esta cuenta.
					if(isInCourier){
						MTTCard cardAux = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(),this.get_TrxName());
						if(cardAux==null || status==null){
							isInCourier = false;
							if(status==null){
								mgeErrorInicial = "Falta parametrizar estado '"+estadoCourier+"' asociado a la incidencia: " + docNoIncidencia;
							}
						}
					}else{
						MTTCard cardAux = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(),this.get_TrxName());
						if(cardAux != null && status!=null){
							if(cardAux.getUY_DeliveryPoint_ID_Actual() == this.courier.get_ID()){
								isInCourier = true;
							}
						}else{
							if(status==null){
								mgeErrorInicial = "Falta parametrizar estado '"+estadoCourier+"' que esta asociado a la incidencia " + docNoIncidencia;
							}else if(cardAux==null){
								mgeErrorInicial = "Incidencia " + docNoIncidencia + " con nro. de levante " + levante + ", no encontrada en el correo";
							}
						}
					} //Fin Issue #3642
				}
				//Fin OpenUp.			
				
				if(isInCourier){							
					for (int j = 0; j < columnas.size(); j++) {						
						
						switch (j) {
							case 0:									
								String comprobante = columnas.get(j).getChildNodes().get(0).asText();
								line.setDeliveryNo(comprobante);
								break;
							case 1:	
								if (card != null){
									line.setAccountNo(card.getAccountNo());	
								}
								else{
									MTTCard cardAcct = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), get_TrxName());
									if (cardAcct != null) line.setAccountNo(cardAcct.getAccountNo());
								}
								break;
							case 2:	
								String[] fechaEntera = columnas.get(j).getChildNodes().get(0).asText().split("/");
								if(fechaEntera.length > 2){
									String fecha = fechaEntera[2] + "-" + fechaEntera[1] + "-" + fechaEntera[0] + " 00:00:00";
									line.setDateDelivery(Timestamp.valueOf(fecha));
								}									
								break;
							case 3:	
								fechaEntera = columnas.get(j).getChildNodes().get(0).asText().split("/");
								if(fechaEntera.length > 2){
									String fecha = fechaEntera[2] + "-" + fechaEntera[1] + "-" + fechaEntera[0] + " 00:00:00";
									line.setDateTrx(Timestamp.valueOf(fecha));
								}									
								break;
							case 4:	
								String name = columnas.get(j).getChildNodes().get(0).asText();
								line.setName(name);
								break;
							case 5:	
								String direccion = columnas.get(j).getChildNodes().get(0).asText();
								line.setAddress1(direccion);
								break;
							case 6:	
								String localidad = columnas.get(j).getChildNodes().get(0).asText();
								line.setlocalidad(localidad);
								break;
							case 9:	
								estadoCourier = columnas.get(j).getChildNodes().get(0).asText();
								status = MTTDeliveryPointStatus.getMTTDeliveryPointStatusForEstado(getCtx(), get_TrxName(), this.courier.get_ID(), estadoCourier);
								
								if(status != null){
																	
									line.setUY_TT_CardStatus_ID(status.getUY_TT_CardStatus_ID());
									line.setUY_TT_DeliveryPointStatus_ID(status.get_ID());
									
									//Y a parte tengo que modificar el estado de la tarjeta si la cuenta tiene como destino el courier que estoy procesando
									boolean cuentaValida = MTTCard.existsIncidenciaAndDeliveryPoint(this.getCtx(), this.get_TrxName(), incidencia.get_ID(), this.courier.get_ID());
									if(cuentaValida){
										card = MTTCard.forIncidenciaLevante(getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(), this.get_TrxName());
										if(card != null && card.get_ID() > 0){										
											
											//Si el estado de la cuenta es EN COORDINACION y el motivo no esta parametrizado, no se realiza ninguna actualizacion
											if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "devuelta").get_ID()){
												
												String motivoCourier = columnas.get(10).getChildNodes().get(0).asText();												
												//Obtengo el mapeo de motivos, del courier y de Italcred
												MTTDelPointRetReasons motivo = MTTDelPointRetReasons.forCourierIDAndMotivo(this.getCtx(), this.get_TrxName(), this.courier.get_ID(), motivoCourier);
																								
												if(motivo == null){
													break;
												}
											}
											
											//Verifico el estado que tiene la tarjeta, si es el mismo que se esta leyendo en esta lectura, no debo actualizar los trackings										
											if(card.getUY_TT_CardStatus_ID() == status.getUY_TT_CardStatus_ID()){
												break;
											}
											
											//Actualizo estado de tarjeta
											card.setUY_TT_CardStatus_ID(status.getUY_TT_CardStatus_ID());
											card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
											card.saveEx();

											// Tracking cuenta
											cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
											cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
											cardTrack.setAD_User_ID(this.getAD_User_ID());
											cardTrack.setDescription("Lectura " + this.courier.getName() + ": " + ((MTTCardStatus) card.getUY_TT_CardStatus()).getName());												
											cardTrack.setUY_TT_Card_ID(card.get_ID());
											cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
											cardTrack.setUY_DeliveryPoint_ID_Actual(this.courier.get_ID());
											//cardTrack.saveEx();
										}			
									}							
								
								}else{
									//Guardo faltante de parametrizacion en el log
									String msj = "Falta parametrizar estado de tarjeta: " + estadoCourier;
									
									//Primero pregunto si ya no guarde este mensaje para esta carga
									if(MTTControlSettings.forMensaje(getCtx(), msj, this.hdr.get_ID(), this.get_TrxName()) == null){
										
										MTTControlSettings log = new MTTControlSettings(getCtx(), 0, this.get_TrxName());
										log.setDateTrx(new Timestamp(System.currentTimeMillis()));
										log.setUY_TT_WebCourier_ID(this.hdr.get_ID());
										log.setMessage(msj);
										
										log.saveEx();									
									}													
								}								
								break;								
							case 10:
								String motivoCourier = columnas.get(j).getChildNodes().get(0).asText();
								
								//Obtengo el mapeo de motivos, del courier y de Italcred
								MTTDelPointRetReasons motivo = MTTDelPointRetReasons.forCourierIDAndMotivo(this.getCtx(), this.get_TrxName(), this.courier.get_ID(), motivoCourier);
								
								if(motivo != null){
									line.setUY_TT_DelPointRetReasons_ID(motivo.get_ID());
									line.setUY_TT_ReturnReasons_ID(motivo.getUY_TT_ReturnReasons_ID());
								
								}else{
									//Guardo faltante de parametrizacion en el log
									String msj = "Falta parametrizar motivo de devolución: " + motivoCourier;
									
									//Primero pregunto si ya no guarde este mensaje para esta carga
									if(MTTControlSettings.forMensaje(getCtx(), msj, this.hdr.get_ID(), this.get_TrxName()) == null){
										
										MTTControlSettings log = new MTTControlSettings(getCtx(), 0, this.get_TrxName());
										log.setDateTrx(new Timestamp(System.currentTimeMillis()));
										log.setUY_TT_WebCourier_ID(this.hdr.get_ID());
										log.setMessage(msj);
										
										log.saveEx();									
									}								
								}							
								break;
							case 11:
								DomNode receptorNode = columnas.get(j).getChildNodes().get(0);
								if(receptorNode != null){
									line.setName2(receptorNode.asText());
								}								
								break;
							case 12:
								DomNode cedulaNode = columnas.get(j).getChildNodes().get(0);
								if(cedulaNode != null){
									line.setCedula(cedulaNode.asText());
								}								
								break;
								
							default:
								break;
						}							
					}	
					line.saveEx();
					
					//Aca termino el tracking
					if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "entregada").get_ID()){
						if(cardTrack != null){
							if(line.getName2() != null && line.getCedula() != null){
								cardTrack.setobservaciones("Entregado a " + line.getName2() + " CI: " + line.getCedula());										
							}else if(line.getName2() != null && line.getCedula() == null){
								cardTrack.setobservaciones("Entregado a " + line.getName2());
							}else if(line.getName2() == null && line.getCedula() != null){
								cardTrack.setobservaciones("Entregado a CI: " + line.getCedula());
							}else{
								cardTrack.setobservaciones("No hay datos de receptor");
							}
							//cardTrack.saveEx();
						}
						
						//Solo si existe una cuenta valida se cierra el tracking
						if(card != null) card.closeTrackingDelivered(null);
				
					}else if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "devuelta").get_ID()){
						if(cardTrack != null){
							if(line.getUY_TT_ReturnReasons_ID() > 0){
								cardTrack.setobservaciones("Devuelto por: " + ((MTTDelPointRetReasons) line.getUY_TT_DelPointRetReasons()).getName());
							}else{
								cardTrack.setobservaciones("No hay motivo de devolución");
							}
							//cardTrack.saveEx();
						}
					}

					if(cardTrack != null){
						cardTrack.saveEx();
						
						if(card != null){
							//Ahora pregunto si el estado que reporta el courier es un estado que marca que ya no esta mas en manos de el
							if(!((MTTDeliveryPointStatus) line.getUY_TT_DeliveryPointStatus()).isInCourier()){
								
								this.modificarPendientes(incidencia.get_ID(), levante);
							}	
						}		
					}	
				//Sylvie Bouissa #3642 - Se agrega codigo para indicar en las inconsistencias de esta corrida que no se procesa porque el estado 
				}else{
					if(!mgeErrorInicial.equals("")){
						MTTControlSettings log = new MTTControlSettings(getCtx(), 0, this.get_TrxName());
						log.setDateTrx(new Timestamp(System.currentTimeMillis()));
						log.setUY_TT_WebCourier_ID(this.hdr.get_ID());
						log.setMessage(mgeErrorInicial);
						
						log.saveEx();
					}
				}
			}
		}

		//Cierro Streams
		
		//wb.closeAllWindows();
		
		
		xls.flush();
		xls.close();
		download.close();	
		in.close();
		out.flush();
	    out.close(); 	    
	   	    
	    java.lang.Thread.sleep(5000);
	    
	    //Borro Archivos
	    file.setWritable(true);
	    file.delete();	    
	    nuevo.setWritable(true);
	    nuevo.delete();	  
		
	}
	
		
	/**
	 * OpenUp. Guillermo Brust. 30/09/2013. ISSUE #1348
	 * Login a la sitio web del courier InterPost
	 * 
	 * **/
	private HtmlPage loginInterpost() throws Exception {	
		
		HtmlPage resultPage = null;
		
		System.out.println("WEBCOURIER - Iniciando Login a InterPost");
		this.showHelp("Iniciando Login a InterPost...");
		
		HtmlPage p = (HtmlPage) wb.getPage("http://www.interpost.com.uy/");
		
		//Me logueo
		HtmlForm f = (HtmlForm) p.getElementById("div_form").getChildNodes().get(0).getNextSibling();		
		HtmlTextInput username = (HtmlTextInput) f.getInputByName("usuario");
		HtmlPasswordInput pass = (HtmlPasswordInput) f.getInputByName("contrasenia");
		HtmlSubmitInput submit = (HtmlSubmitInput) f.getInputByValue("Enviar");
		username.setValueAttribute("1803");
		pass.setValueAttribute("lNpB0jj2kg");		 
		resultPage = (HtmlPage) submit.click();
				
		System.out.println("Logueado!");
		this.showHelp("Logueado a InterPost...");
		
		return resultPage;
	}
	
	
	private HtmlPage viewShipmentsInterpost() throws Exception{	
		
		//Voy a la pagina de los envios
		System.out.println("Entrando en pagina de envios...");
		this.showHelp("Entrando en pagina de envios...");
		HtmlPage p = (HtmlPage) wb.getPage("http://www.interpost.com.uy/admin/index.php");
		
		//Aca creo el modelo del cabezal
	    this.hdr = new MTTWebCourier(this.getCtx(), 0, this.get_TrxName());
		this.hdr.setUY_DeliveryPoint_ID(this.courier.get_ID());
		this.hdr.setDateTrx(new Timestamp(System.currentTimeMillis()));
		this.hdr.setC_DocType_ID(MDocType.forValue(this.getCtx(), "webcourier", null).get_ID());		
					
		//Obtengo todos los levantes pendientes de INTERPOST	
		HashMap<String, String> levantes = this.courier.getLevantesPendientes();
		
		if(levantes.size() > 0) this.hdr.saveEx();
		else throw new AdempiereException("No existen levantes pendientes de este Courier");
		
		for (String levante: levantes.keySet()) {	
			
			//A partir del segundo levante vuelvo a entrar a la pagina, porque se caia sino
			p = (HtmlPage) wb.getPage("http://www.interpost.com.uy/admin/index.php");
						
			//Filtro por numero de levante
			System.out.println("Filtrando por numero de levante...");
			HtmlForm f = (HtmlForm) ((HtmlTable) p.getElementById("div_body_logueado").getFirstByXPath("//table")).getBodies().get(0).getHtmlElementsByTagName("tr").get(1).getHtmlElementsByTagName("td").get(0).getHtmlElementsByTagName("form").get(0);
			
			//OpenUp. Guillermo Brust. 30/10/2013. ISSUE #1452
			//Se filtra por numero de cuenta que en este caso va a ser numero de levante (solo para interpost)
			HtmlSelect select = f.getSelectByName("por");
			p = (HtmlPage) select.getOptionByValue("4").click();
			f = (HtmlForm) ((HtmlTable) p.getElementById("div_body_logueado").getFirstByXPath("//table")).getBodies().get(0).getHtmlElementsByTagName("tr").get(1).getHtmlElementsByTagName("td").get(0).getHtmlElementsByTagName("form").get(0);
			//Fin OpenUp.			
			
			HtmlTextInput levanteInput = (HtmlTextInput) f.getInputByValue("");		
			HtmlSubmitInput submit = (HtmlSubmitInput) f.getInputByValue("Buscar");
			levanteInput.setValueAttribute(levante);				
			p = (HtmlPage) submit.click();	
						
			int pagActual = 1;
			int pagFinal = 1;
			//ini openup Sylvie 04/10/2014 Issue #3218 Se agrega control cuando para el levante no hay datos en la web del courier
			if(p.getElementById("div_body_logueado").getByXPath("Div").size() > 2){ 
			while(pagActual <= pagFinal){
												
				System.out.println("Leyendo pagina " + pagActual + "...");	
				this.showHelp("Leyendo página " + pagActual + "...");
				
				//Voy a la grilla donde se encuentran los datos				
				HtmlTable table = ((HtmlDivision) p.getElementById("div_body_logueado").getByXPath("Div").get(2)).getFirstByXPath("table");
				HtmlTableBody tbody = (HtmlTableBody) table.getBodies().get(0);		
							
				List<HtmlTableRow> filas = tbody.getHtmlElementsByTagName("tr");				
				
				MTTWebCourierLine line = null;
				
				for (int i = 1; i < filas.size(); i++) { //empiezo de la fila 1 y no de la 0 dado que la primera es la fila de titulos		
					
					int registro = i;
					System.out.println("Registro " + registro + "...");	
					this.showHelp("Página " + pagActual + " - Registro " + registro + "...");
					
					//Creo un nuevo modelo
					line = new MTTWebCourierLine(getCtx(), 0, this.get_TrxName());
					line.setUY_TT_WebCourier_ID(this.hdr.get_ID());
					line.setLevante(levante);
					line.setPieza("TARJETA");
					
					List<HtmlTableDataCell> columnas = filas.get(i).getHtmlElementsByTagName("td");		
					
					//Aca debo preguntar si existe un estado para este envio guardado en adempiere, y ademas si este estado guardado
					//es esta definido como que esta en poder del courier o no.
					String docNoIncidencia = "";
					MTTCard card = null;					
					MTTCardTracking cardTrack = null;					
					
					DomNode node = columnas.get(1).getChildNodes().get(0);
					if(node != null){						
						docNoIncidencia = node.getNodeValue().trim();

						System.out.println("Incidencia: " + docNoIncidencia + "...");
						
						MRReclamo incidencia = MRReclamo.forDocumentNo(getCtx(), docNoIncidencia, null);
						if (incidencia == null) incidencia = new MRReclamo(getCtx(), 0, null); 
						
						if (incidencia.get_ID() > 0){
							if (!this.hashIncidencias.containsKey(incidencia.get_ID())){
								this.hashIncidencias.put(incidencia.get_ID(), null);	
							}
							line.setUY_R_Reclamo_ID(incidencia.get_ID());
						}
						
						//OpenUp. Guillermo Brust. 13/11/2013. ISSUE#
						//Se agrega codigo para verificar que si el estado que leo en la web es entregado, si la cuenta tenia el estado entregado no hago nada, si no tenia el estado entregado lo actualizo 
						boolean isInCourier = true;
						String estadoCourier = columnas.get(6).getChildNodes().get(0).getNodeValue().trim();
						MTTDeliveryPointStatus status = MTTDeliveryPointStatus.getMTTDeliveryPointStatusForEstado(getCtx(), get_TrxName(), this.courier.get_ID(), estadoCourier.trim());
										
						if(status != null && status.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "entregada").get_ID()){					
							//busco la cuenta por numero de cuenta, levante y el punto actual debe ser el currier				
							//MTTCard cardAux = MTTCard.forAccountOpen(this.getCtx(), cuenta, this.get_TrxName());
							
							MTTCard cardAux = null;
							
							if (incidencia.get_ID() > 0){
								cardAux = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(),this.get_TrxName());	
							}
							if(cardAux != null){
								if(cardAux.getUY_TT_CardStatus_ID() == status.getUY_TT_CardStatus_ID()){
									isInCourier = false;										
								}
							}				
						}else{
							//Modificacion para que tome las cuentas por levante cuenta y deliverypoint
							//isInCourier = MTTWebCourierLine.getIsInCourierForAccount(getCtx(), cuenta, levante, this.get_TrxName());
							MTTCard cardAux = null;
							if (incidencia.get_ID() > 0){
								cardAux = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(),this.get_TrxName());	
							}
							if(cardAux==null){
								isInCourier= false;
							}
							else{
								isInCourier=true;
							}
						}				
						//Fin OpenUp.	
						
						if(isInCourier){							
							for (int j = 0; j < columnas.size(); j++) {								
								
								switch (j) {
									case 0:	
										String fecha = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();									
										@SuppressWarnings("deprecation")
										Timestamp dateDelivery = new Timestamp(Integer.parseInt(fecha.split("/")[2]) - 1900, Integer.parseInt(fecha.split("/")[1]) -1, Integer.parseInt(fecha.split("/")[0]), 0, 0, 0, 0);
										line.setDateDelivery(dateDelivery);
										break;							
									case 2:
										String nombre = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();
										line.setName(nombre);
										break;
									case 3:
										String direccion = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();
										line.setAddress1(direccion);
										break;
									case 4:
										String localidad = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();
										line.setlocalidad(localidad);
										break;
									case 5:
										if (card != null){
											line.setAccountNo(card.getAccountNo());	
										}
										else{
											MTTCard cardAcct = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), get_TrxName());
											if (cardAcct != null) line.setAccountNo(cardAcct.getAccountNo());
										}
										break;
									case 6:
										estadoCourier = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();
										status = MTTDeliveryPointStatus.getMTTDeliveryPointStatusForEstado(getCtx(), get_TrxName(), this.courier.get_ID(), estadoCourier);
										MTTDelPointRetReasons motivo = null;
										
										if(status != null){
											line.setUY_TT_CardStatus_ID(status.getUY_TT_CardStatus_ID());
											line.setUY_TT_DeliveryPointStatus_ID(status.get_ID());
											
											if(estadoCourier.toUpperCase().trim().equals("ENTREGADO")){		
												//Obtengo el nombre y cedula de quien recibe
												//openUp Sylvie Bouissa 22/10/2014 Issue (No esta creada)
												// Es necesario controlar que no sea null el campo ya que si el courier
												// no indica nombre o cedula del receptor de la tarjeta
												// se caer al consultar por este valor
												if((columnas.get(8).getChildNodes().get(0))!=null){
													String celda = columnas.get(8).getChildNodes().get(0).getNodeValue().trim();
													
														if(celda.split("-").length > 1) {
															line.setName2(celda.split("-")[0]);
															line.setCedula(celda.split("-")[1]);		
														}
													
												}
												
																												
												
											}else if(estadoCourier.toUpperCase().equals("DEVUELTO") || estadoCourier.toUpperCase().equals("MOTIVADA") || estadoCourier.toUpperCase().equals("CON AVISO")){
												//Aca voy a obtener el motivo
											    String motivoCourier = columnas.get(7).getChildNodes().get(0).getNodeValue().trim();
												
												//Obtengo el mapeo de motivos, del courier y de Italcred
											    motivo = MTTDelPointRetReasons.forCourierIDAndMotivo(this.getCtx(), this.get_TrxName(), this.courier.get_ID(), motivoCourier);
												
												if(motivo != null){
													line.setUY_TT_DelPointRetReasons_ID(motivo.get_ID());
													line.setUY_TT_ReturnReasons_ID(motivo.getUY_TT_ReturnReasons_ID());
												
												}else{
													//Guardo faltante de parametrizacion en el log
													String msj = "Falta parametrizar motivo de devolución: " + motivoCourier;
													
													//Primero pregunto si ya no guarde este mensaje para esta carga
													if(MTTControlSettings.forMensaje(getCtx(), msj, this.hdr.get_ID(), this.get_TrxName()) == null){
														
														MTTControlSettings log = new MTTControlSettings(getCtx(), 0, this.get_TrxName());
														log.setDateTrx(new Timestamp(System.currentTimeMillis()));
														log.setUY_TT_WebCourier_ID(this.hdr.get_ID());
														log.setMessage(msj);
														
														log.saveEx();									
													}			
												}
											}			
											
											//Y a parte tengo que modificar el estado de la tarjeta si la cuenta tiene como destino el courier que estoy procesando
											boolean cuentaValida = MTTCard.existsIncidenciaAndDeliveryPoint(this.getCtx(), this.get_TrxName(), incidencia.get_ID(), this.courier.get_ID());
											if(cuentaValida){
												//busco la cuenta por numero de cuenta y tambien por levante
												//card = MTTCard.forAccountOpen(getCtx(), cuenta, this.get_TrxName());
												card = MTTCard.forIncidenciaLevante(this.getCtx(), incidencia.get_ID(), levante.trim(),hdr.getUY_DeliveryPoint_ID(),this.get_TrxName());
												if(card != null && card.get_ID() > 0){
																									
													//Si el estado de la cuenta es EN COORDINACION y el motivo no esta parametrizado, no se realiza ninguna actualizacion
													if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "devuelta").get_ID()){
														
														if(motivo == null){
															break;
														}
													}
													
													//Verifico el estado que tiene la tarjeta, si es el mismo que se esta leyendo en esta lectura, no debo actualizar los trackings										
													if(card.getUY_TT_CardStatus_ID() == status.getUY_TT_CardStatus_ID()){
														break;
													}
													
													//Actualizo estado de tarjeta
													card.setUY_TT_CardStatus_ID(status.getUY_TT_CardStatus_ID());
													card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
													card.saveEx();
													
													// Tracking cuenta
													cardTrack = new MTTCardTracking(getCtx(), 0, get_TrxName());
													cardTrack.setDateTrx(new Timestamp(System.currentTimeMillis()));
													cardTrack.setAD_User_ID(this.getAD_User_ID());
													cardTrack.setDescription("Lectura " + this.courier.getName() + ": " + ((MTTCardStatus) card.getUY_TT_CardStatus()).getName());												
													cardTrack.setUY_TT_Card_ID(card.get_ID());
													cardTrack.setUY_TT_CardStatus_ID(card.getUY_TT_CardStatus_ID());
													cardTrack.saveEx();
												}											
											}								
										
										}else{
											//Guardo faltante de parametrizacion en el log
											String msj = "Falta parametrizar estado de tarjeta: " + estadoCourier;
											
											//Primero pregunto si ya no guarde este mensaje para esta carga
											if(MTTControlSettings.forMensaje(getCtx(), msj, this.hdr.get_ID(), this.get_TrxName()) == null){
												
												MTTControlSettings log = new MTTControlSettings(getCtx(), 0, this.get_TrxName());
												log.setDateTrx(new Timestamp(System.currentTimeMillis()));
												log.setUY_TT_WebCourier_ID(this.hdr.get_ID());
												log.setMessage(msj);
												
												log.saveEx();									
											}			
										}			
																		
										break;									
									case 9:	
										String fechaModEstado = columnas.get(j).getChildNodes().get(0).getNodeValue().trim();									
										@SuppressWarnings("deprecation")
										Timestamp dateTrx = new Timestamp(Integer.parseInt(fechaModEstado.split("/")[2]) - 1900, Integer.parseInt(fechaModEstado.split("/")[1]) -1, Integer.parseInt(fechaModEstado.split("/")[0]), 0, 0, 0, 0);
										
										line.setDateTrx(dateTrx);
										break;
				
									default:
										break;
								}
							}	
							line.saveEx();					

							//Aca termino el tracking
							if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "entregada").get_ID()){
								if(cardTrack != null){
									if(line.getName2() != null && line.getCedula() != null){
										cardTrack.setobservaciones("Entregado a " + line.getName2() + " CI: " + line.getCedula());										
									}else if(line.getName2() != null && line.getCedula() == null){
										cardTrack.setobservaciones("Entregado a " + line.getName2());
									}else if(line.getName2() == null && line.getCedula() != null){
										cardTrack.setobservaciones("Entregado a CI: " + line.getCedula());
									}else{
										cardTrack.setobservaciones("No hay datos de receptor");
									}
									//cardTrack.saveEx();
								}
								
								if(card != null) card.closeTrackingDelivered(null);
						
							}else if(line.getUY_TT_CardStatus_ID() == MTTCardStatus.forValue(this.getCtx(), this.get_TrxName(), "devuelta").get_ID()){
								if(cardTrack != null){
									if(line.getUY_TT_ReturnReasons_ID() > 0){
										cardTrack.setobservaciones("Devuelto por: " + ((MTTReturnReasons) line.getUY_TT_ReturnReasons()).getName());
									}else{
										cardTrack.setobservaciones("No hay motivo de devolución");
									}
									//cardTrack.saveEx();
								}
							}
							
							if(cardTrack != null){
								cardTrack.saveEx();
								
								if(card != null){
									//Ahora pregunto si el estado que reporta el courier es un estado que marca que ya no esta mas en manos de el
									if(!((MTTDeliveryPointStatus) line.getUY_TT_DeliveryPointStatus()).isInCourier()){
										
										this.modificarPendientes(incidencia.get_ID(), levante);
									}	
								}							
							}				
						}						
					}					
				}
				//Cambio de pagina
				System.out.println("Cambiando pagina...");
				this.showHelp("Cambiando pagina...");
				
				//Siempre agrego uno a la pagina actual, asi sale del while
				pagActual++;
				
				//Solo si hay paginado
				if(p.getByXPath("//Div[@class='paginado']").size() > 0){
					
					HtmlDivision divPaginado = (HtmlDivision) p.getByXPath("//Div[@class='paginado']").get(1);
					
					if(divPaginado != null){
						
						@SuppressWarnings("unchecked")
						List<HtmlAnchor> listaAnchors = (List<HtmlAnchor>) divPaginado.getByXPath("//a[@class='link_palabra_pg']");
						
						for (HtmlAnchor htmlAnchor : listaAnchors) {
							
							if(htmlAnchor.getTextContent().equals("Siguiente")){						
								p = (HtmlPage) htmlAnchor.click();								
								pagFinal++;
								break;
							}
						}
					}
				}	
			}
			}//fin openup Sylvie 04/10/2014 Issue #3218
		}	
						
		return p;	
	}
	
	
	/**
	 *OpenUp. Guillermo Brust. 10/09/2013. ISSUE #1300
	 *Método que desasocia el envio de la cuenta si el estado del envio retornado por el courier es uno de los parametrizados como   
	 * Se pone dentro de la misma transaccion, dado que esto se hacer en cada linea guardada, y si se cae el proceso, no deberia realizarse para ninguna.
	 **/
	private void modificarPendientes(int uyRReclamoID, String levante){
						
		//Obtengo el envio a partir del punto de distribucion y el numero de levante
		MTTDelivery delivery = MTTDelivery.forDelvieryPointAndLevante(getCtx(), this.courier.get_ID(), levante, get_TrxName());
		
		if(delivery != null){
			
			//Obtengo la tarjeta a partir de la cuenta y del envio
			MTTCard card = MTTCard.forIncidenciaAndDeliveryAndStatus(getCtx(), uyRReclamoID, delivery.get_ID(), this.get_TrxName());
							
			if(card != null){			
				if(card.getUY_TT_Delivery_ID() > 0){
					DB.executeUpdateEx("UPDATE UY_TT_Card SET UY_TT_Delivery_ID = NULL WHERE UY_TT_Card_ID = " + card.get_ID(), this.get_TrxName());
				}					
			}		
		} 
		
	}
	
	
	/**
	 *OpenUp. Guillermo Brust. 08/10/2013. ISSUE #1374
	 *Método que verifica si existen cuentas en levantes pendientes que no se encuentren en el tracking web del courier procesado.
	 **/
	private void incidenciasFaltantes(){
		
		try {

			//Obtengo todos los levantes pendientes
			HashMap<Integer, MTTCard> pendientes = this.courier.getIncidenciasPendientes();
					
			for (Integer pendiente: pendientes.keySet()) {			
				
				boolean leida = false;
				for (Integer incidenciaLeida: this.hashIncidencias.keySet()){
					//OpenUp SBT Issue #5061 19/11/2015 Se cambia igual por equals ya que es un obj y sino la comp siempre da false			
					if(pendiente.equals(incidenciaLeida)){
						leida = true;
						break;
					}
				}
				
				//Si la incidencia pendiente no esta leida, se agrega un mensaje de faltante de parametrizacion
				if(!leida){
					MRReclamo incidencia = new MRReclamo(getCtx(), pendiente, null);
					MTTCard card = MTTCard.forIncidencia(getCtx(), incidencia.get_ID(), null);
					
					MTTCardStatus cardStatus = (MTTCardStatus)card.getUY_TT_CardStatus();
					if (!cardStatus.isEndTrackStatus()){

						String levante = card.getLevante();
						if (levante == null) levante = "0";
						
						String msj = "Incidencia " + incidencia.getDocumentNo() + " con levante " + levante + " no encontrados en tracking web";
						
						//Primero pregunto si ya no guarde este mensaje para esta carga
						if(MTTControlSettings.forMensaje(getCtx(), msj, this.hdr.get_ID(), this.get_TrxName()) == null){
							
							MTTControlSettings log = new MTTControlSettings(getCtx(), 0, this.get_TrxName());
							log.setDateTrx(new Timestamp(System.currentTimeMillis()));
							log.setUY_TT_WebCourier_ID(this.hdr.get_ID());
							log.setMessage(msj);
							
							log.saveEx();									
						}					
					}
				}
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
}
