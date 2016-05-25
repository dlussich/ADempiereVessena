package org.openup.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSequence;
import org.compiere.model.MTax;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.util.IteratorIterable;
import org.openup.dgi.EnvioCFE;
import org.openup.dgi.EnvioCFE.CFE;
import org.openup.dgi.TipMonType;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.Detalle;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.DscRcgGlobal;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.Emisor;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.IdDoc;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.MediosPago;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.Receptor;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.Referencia;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.SubTotInfo;
import org.openup.dgi.EnvioCFE.CFE.CFEItem.Totales;
import org.openup.model.MCFEInvoice;
import org.openup.model.X_UY_CFE_Invoice;

public class ChargeEInvoice {
	
	
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private String rutaSalida;
	private String rutaEntrada;
	private String codigo;
	int contador = 0;
	EnvioCFE envio;
	
	/**
	 * Recibo una factura para enviarla
	 * Ltda. Issue #2208
	 * 
	 * @author Leonardo Boccone - 24/07/2014
	 * @param string 
	 * @param properties 
	 * @throws IOException 
	 * @see
	 */
	public String SendEInvoice(MInvoice invoice){
		EnvioCFE.CFE cfe = new EnvioCFE.CFE();
		ChargeHead(invoice.getCtx(), invoice.get_TrxName());
		chargeInvoice(invoice.getCtx(),invoice,cfe,invoice.get_TrxName());
		envio.setCFE(cfe);
		try {
			try {
				Serializar(invoice.getCtx(),invoice.get_TrxName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codigo;
		
	}
	/**
	 * Cargo el cabezal del envio con los datos de la empresa en DGI OpenUp
	 * Ltda. Issue #2208
	 * 
	 * @author Leonardo Boccone - 29/05/2014
	 * @param string 
	 * @param properties 
	 * @see
	 */
	public void ChargeHead(Properties properties, String string) {
		envio = new EnvioCFE();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		sql = "select * from uy_cfe";
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if(!rs.next()){
				throw new AdempiereException("Falta parametrizacion de rutas y codigo");
			}
			else{

				EnvioCFE.Encabezado encabezado = new EnvioCFE.Encabezado();
				
				encabezado.setEmpCK(rs.getString("codcomunicacion"));
				encabezado.setEmpCodigo(rs.getString("codempresa").trim());
				encabezado.setEmpPK(rs.getString("coddesocio"));
				rutaEntrada = rs.getString("entrada");
				rutaSalida = rs.getString("salida");

				envio.setEncabezado(encabezado);
			}
		} catch (Exception e) {

			throw new AdempiereException(e);

		}
	}

	/**
	 * Cargamos todos los datos para completar la factura electronica recoriendo
	 * la invoice y sus lineas OpenUp Ltda. Issue # 2208
	 * 
	 * @author Leonardo Boccone - 29/05/2014
	 * @param fechaFin 
	 * @param fechaInicio 
	 * @param string 
	 * @param properties 
	 * @see
	 */
	public void ChargeBody(Properties Ctx, String TrxName, Timestamp fechaInicio, Timestamp fechaFin) {

		EnvioCFE.CFE cfe = new EnvioCFE.CFE();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		List<MInvoice> invoices = MInvoice.getInvoceforDates(Ctx,this.fechaInicio, this.fechaFin, TrxName);
	
	
		try {
			for (MInvoice invoice : invoices) {
				chargeInvoice(Ctx,invoice,cfe,TrxName);
				
			}
			
			envio.setCFE(cfe);

		} catch (Exception e) {

			throw new AdempiereException(e);

		}

	}

	private void chargeInvoice(Properties ctx, MInvoice invoice, CFE cfe, String trxName) {
		
		
		MInvoiceLine[] lines = invoice.getLines();
		
		EnvioCFE.CFE.CFEItem cfeitem = new EnvioCFE.CFE.CFEItem();	
		cfeitem.setIdDoc(cargaridDoc(invoice));
		cfeitem.setEmisor(cargarEmisor(invoice));
		cfeitem.setReceptor(cargarReceptor(invoice));	
		cfeitem.setTotales(cargarTotales(invoice,lines));
		cfeitem.setMediosPago(cargarMedios(invoice,lines));
												
				cfeitem.setDetalle(cargarDetalle(invoice,lines));
				cfeitem.setSubTotInfo(cargarSubTotInfo(invoice,lines));
				cfeitem.setDscRcgGlobal(cargarDscRcgGlobal(invoice,lines));						
				cfeitem.setReferencia(cargarReferencia(invoice,lines));
				
			cfe.getCFEItem().add(cfeitem);
		
	}
	
	private IdDoc cargaridDoc(MInvoice invoice) {
		try{	
		EnvioCFE.CFE.CFEItem.IdDoc idDoc = new EnvioCFE.CFE.CFEItem.IdDoc();
		MDocType doc = MDocType.get(invoice.getCtx(), invoice.getC_DocType_ID());
		BigInteger doctype = new BigInteger(doc.get_Value("CfeType").toString());
		idDoc.setCFETipoCFE(doctype);// Tipo CFE 111 E-Factura
		
		 MSequence sec = new MSequence(invoice.getCtx(),doc.getDefiniteSequence_ID(),invoice.get_TrxName());
		 if(sec.getPrefix()!=null){
			 idDoc.setCFESerie(sec.getPrefix());// Serie del CFE
		 }
		 else{
				throw new AdempiereException("falta serie: "+ invoice.getDateInvoiced());
			}
	
		if(invoice.getDocumentNo()!=null){
			//invoice.getDocumentNo().trim()
			String docno = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(sec.getCurrentNext()), 7, "0");
			BigInteger numero = new BigInteger(docno);
			idDoc.setCFENro(numero);// Número de CFE 7 digitos
		}
		else{
			throw new AdempiereException("falta numero de factura de fecha: "+ invoice.getDateInvoiced());
		}
		if(invoice.getDateInvoiced()!=null){
			Date femi= new Date(invoice.getDateInvoiced().getTime());
			XMLGregorianCalendar dateinvoiced = DatatypeFactory.newInstance().newXMLGregorianCalendar(femi.toString());
			idDoc.setCFEFchEmis(dateinvoiced);// Fecha Emision Contable del CFE
			
		}
		else{
			//throw new AdempiereException("falta payment type en factura Nº: "+ invoice.getDocumentNo());
		}
		
		
		/*Date feini= new Date(fechaInicio.getTime());
		XMLGregorianCalendar inicialdate = DatatypeFactory.newInstance().newXMLGregorianCalendar(feini.toString());
		idDoc.setCFEPeriodoDesde(inicialdate);// Periodo de Facturacion -
									// Desde
									// (AAAA-MM-DD)
		Date fefin= new Date(fechaFin.getTime());
		XMLGregorianCalendar finaldate = DatatypeFactory.newInstance().newXMLGregorianCalendar(fefin.toString());
		idDoc.setCFEPeriodoHasta(finaldate);// Periodo Facturacion -
									// Hasta
									// (AAAA-MM-DD)
		*/
		idDoc.setCFEMntBruto(BigInteger.valueOf(0));// Indica el uso de Montos Brutos
								// en
								// Detalle (IVA inc.)
		if(invoice.getpaymentruletype().equalsIgnoreCase("CR")){
			idDoc.setCFEFmaPago(BigInteger.valueOf(2));// Forma de Pago 1: contado, 2:
			// credito
		}
		else if(invoice.getpaymentruletype().equalsIgnoreCase("CO")){
			idDoc.setCFEFmaPago(BigInteger.valueOf(1));// Forma de Pago 1: contado, 2:
			// credito
		}
		else{
			throw new AdempiereException("falta payment type en factura Nº: "+ invoice.getDocumentNo());
		}
		if(invoice.getDueDate()!=null){
			Date duedata= new Date(invoice.getDueDate().getTime());
			XMLGregorianCalendar duedate = DatatypeFactory.newInstance().newXMLGregorianCalendar(duedata.toString());
			idDoc.setCFEFchVenc(duedate);// Fecha de vencimiento			
		}
		
		idDoc.setCFETipoTraslado(null);// Indicador Tipo de traslado de bienes 1- venta, 2-traslado interno	(solo para e-remito)
		idDoc.setCFEAdenda("");// Adenda al CFE
		//Impresora
		idDoc.setCFEImpresora("Brother");
		idDoc.setCFEImp("S");
		idDoc.setCFEImpFormato(BigInteger.valueOf(2));
		idDoc.setCFEImpCantidad(BigInteger.valueOf(1));
		
		idDoc.setCFEEfectivo(invoice.getGrandTotal());// Efectivo pago
		idDoc.setCFEPesoTotal(invoice.getGrandTotal());// Peso Total
		
		idDoc.setCFECambio(Env.ZERO);// Cambio
		idDoc.setCFEAcimaUI("");//Solo para E-tickets Indicador que define si la nota es mayor de 10.000. Informar: -S: Sí -N: No
		idDoc.setCFENumReferencia(String.valueOf(invoice.getC_Invoice_ID()));// Número identificador del CFE definido por el ERP
		idDoc.setCAEApodo("");// Apodo del rango del CAE
		
		return idDoc;
	} catch (Exception e) {

	throw new AdempiereException(e);
	}
	}
				
	private Emisor cargarEmisor(MInvoice invoice) {
		try {
			EnvioCFE.CFE.CFEItem.Emisor emisor = new EnvioCFE.CFE.CFEItem.Emisor();
			MOrgInfo orginfo = MOrgInfo.get(invoice.getCtx(), invoice.getAD_Org_ID(),
					invoice.get_TrxName());
			MLocation loc = MLocation.get(invoice.getCtx(), orginfo.getC_Location_ID(),
					invoice.get_TrxName());

			// Nombre o Razon Social del Emisor
			if(orginfo.getrznsoc()!=null){
				emisor.setEmiRznSoc(orginfo.getrznsoc());
			}
			else{
				//throw new AdempiereException("falta Razon Social en factura Nº: "+ invoice.getDocumentNo());
			}
			emisor.setEmiComercial(MOrg.get(invoice.getCtx(), orginfo.getAD_Org_ID()).getName());// Nombre Comercial
			// Giro Comercial del Emisor Relevante para el CFE
			emisor.setEmiGiroEmis(orginfo.getgirotype());
			emisor.setEmiTelefono(orginfo.getPhone());// Teléfono Emisor
			emisor.setEmiTelefono2(orginfo.getPhone2());// Teléfono Emisor 2
			// Correo Elect. de contacto en empresa del emisor
			emisor.setEmiCorreoEmisor(orginfo.getEMail());
			// Nombre de la Casa Principal /Sucursal
			MWarehouse casa = MWarehouse.get(invoice.getCtx(), orginfo.getDropShip_Warehouse_ID()); 
			emisor.setEmiSucursal(casa.getName());
			
			if(loc.getAddress1()!=null){
				emisor.setEmiDomFiscal(loc.getAddress1());// Domicilio Fiscal
			}
			else{
				//throw new AdempiereException("falta Domicilio Fiscal en factura Nº: "+ invoice.getDocumentNo());
			}
			if(loc.getUY_Localidades()!=null){
				emisor.setEmiCiudad(loc.getUY_Localidades().getName());// Ciudad
			}
			else{
				//throw new AdempiereException("falta Ciudad en factura Nº: "+ invoice.getDocumentNo());
			}
			if(loc.getUY_Departamentos()!=null){
				emisor.setEmiDepartamento(loc.getUY_Departamentos().getName());// Departamento
			}
			else{
				//throw new AdempiereException("falta Departamento en factura Nº: "+ invoice.getDocumentNo());
			}

			return emisor;

		} catch (Exception e) {

			throw new AdempiereException(e);
		}
	}

	private Receptor cargarReceptor(MInvoice invoice) {
		try {
			EnvioCFE.CFE.CFEItem.Receptor receptor = new EnvioCFE.CFE.CFEItem.Receptor();
			MBPartner partner = MBPartner.get(invoice.getCtx(),
					invoice.getC_BPartner_ID());
			MLocation location = MLocation.getBPLocation(invoice.getCtx(),invoice.getC_BPartner_Location_ID(), invoice.get_TrxName());

			if (partner.getDUNS() != null) {
				receptor.setRcpTipoDocRecep(2);// Tipo de Documento del Receptor
				receptor.setRcpDocRecep(partner.getDUNS());// Nº Documento
															// Receptor
			} else if (partner.get_Value("cedula") != null) {
				receptor.setRcpTipoDocRecep(1);// Tipo de Documento del Receptor
				receptor.setRcpDocRecep(partner.get_Value("cedula").toString());// Nº
																				// Documento
																				// Receptor

			} else {
				receptor.setRcpTipoDocRecep(4);
				receptor.setRcpDocRecep("El receptor de la factura nº: "
						+ invoice.getDocumentNo()
						+ " no tiene ni cedula ni RUT");
				/*throw new AdempiereException("El receptor de la factura nº: "
						+ invoice.getDocumentNo()
						+ " no tiene ni cedula ni RUT");*/
			}

			receptor.setRcpCodPaisRecep(location.getCountry().getCountryCode());// Código País

			receptor.setRcpRznSocRecep(partner.getName2());// Nombre o
															// Denominación
															// Receptor

			receptor.setRcpDirRecep(location.getAddress1());// Domicilio del
															// Receptor

			receptor.setRcpCiudadRecep(location.getUY_Localidades().getName());// Ciudad Receptor

			receptor.setRcpDeptoRecep(location.getUY_Departamentos().getName());// Departamento
															// Receptor

			receptor.setRcpCP(location.getUY_Localidades().getzipcode());// Código Postal

			if(partner.get_Value("email")!=null){
				receptor.setRcpCorreoRecep(partner.get_Value("email").toString());// Correo
				// Receptor
			}
		

			return receptor;

		} catch (Exception e) {

			throw new AdempiereException(e);
		}
	}

	private Totales cargarTotales(MInvoice invoice, MInvoiceLine[] lines) {

		try {
			EnvioCFE.CFE.CFEItem.Totales totales = new EnvioCFE.CFE.CFEItem.Totales();
			totales.setTotTpoMoneda(TipMonType.valueOf(invoice.getCurrencyISO()));// Tipo// moneda// transacción														
			if(!totales.getTotTpoMoneda().equals(TipMonType.UYU)){
				totales.setTotTpoCambio(invoice.getCurrencyRate());// Tipo de Cambio
			}			
			else{
				totales.setTotTpoCambio(null);// Tipo de Cambio
			}
			
			// Monto No Gravado, menos desc. glb. mas recargos glv (de items no
			// gravados)
			

			// Tot.Mnt exportacion y asimilados menos desc. glb. mas rec. glb.
			// (correspondientes a esos items)
			totales.setTotMntExpoyAsim(Env.ZERO);
			totales.setTotMntImpuestoPerc(Env.ZERO);
			totales.setTotMntNetoIVATasaBasica(Env.ZERO);
			totales.setTotMntNetoIvaTasaMin(Env.ZERO);
			totales.setTotMntNetoIVAOtra(Env.ZERO);
			totales.setTotMntIVATasaMin(Env.ZERO);
			totales.setTotMntIVATasaBasica(Env.ZERO);
			totales.setTotMntIVAOtra(Env.ZERO);
			totales.setTotMntImpuestoPerc(Env.ZERO);
			totales.setTotMntNoGrv(Env.ZERO);
						
			for (int l = 0; l < lines.length; l++) {
			MTax tax= MTax.get(invoice.getCtx(), lines[l].getC_Tax_ID());
			if(tax == null){
				throw new AdempiereException("la linea "+lines[l].get_ID() + " no tiene c_tax_id");
			}
						
			//totales.setTotMntIVaenSusp(Env.ZERO);// Total Monto - IVA em suspenso
			
			// Monto Neto IVA Tasa Minima: Suma items grv a tasa min. menos
			// desc. glb. mas rec. glb. (a esos items). Si indic. montos brutos
			// = 1 entonces dividir por (1 + tasa min. vig.)
			if(tax.getValue().equalsIgnoreCase("minimo")){		
				totales.setTotMntNetoIvaTasaMin(totales.getTotMntNetoIvaTasaMin().add(lines[l].getLineNetAmt()));	
				totales.setTotMntIVATasaMin(totales.getTotMntIVATasaMin().add(lines[l].getTaxAmt()));// Total IVA – Tasa Mínima
			}	
			// Monto Neto IVA Tasa Basica: Suma items grv a tasa basica menos
			// desc. glb. mas rec. glb. (a esos items). Si indic. montos brutos
			// = 1 entonces dividir por (1 + tasa basica vig.)
			else if(tax.getValue().equalsIgnoreCase("basico")){				
				totales.setTotMntNetoIVATasaBasica(totales.getTotMntNetoIVATasaBasica().add(lines[l].getLineNetAmt()));	
				totales.setTotMntIVATasaBasica(totales.getTotMntIVATasaBasica().add(lines[l].getTaxAmt()));// Total IVA – Tasa Básica
			}
			else if(tax.getValue().equalsIgnoreCase("exento")){			
				totales.setTotMntNoGrv(totales.getTotMntNoGrv().add(lines[l].getLineTotalAmt()));
				
			}
			else{				
				totales.setTotMntNetoIVAOtra(totales.getTotMntNetoIVAOtra().add(lines[l].getLineNetAmt()));// Total Monto Neto – IVA otra tasa
				totales.setTotMntIVAOtra(totales.getTotMntIVAOtra().add(lines[l].getTaxAmt()));// Total IVA – Otra tasa
			}
				
			}
			
			totales.setTotIVATasaMin(MTax.forValue(invoice.getCtx(), "minimo",invoice.get_TrxName()).getRate().setScale(3));// Tasa Mínima IVA
			totales.setTotIVATasaBasica(MTax.forValue(invoice.getCtx(), "basico",
					invoice.get_TrxName()).getRate().setScale(3));// Tasa Basica IVA
						
			//totales.setTotMntTotRetenido(Env.ZERO);// Total Monto Retenido		

				EnvioCFE.CFE.CFEItem.Totales.RetencPercepTot retencperceptot = new EnvioCFE.CFE.CFEItem.Totales.RetencPercepTot();
					/*EnvioCFE.CFE.CFEItem.Totales.RetencPercepTot.RetencPercepTotItem retencperceptotitem = new EnvioCFE.CFE.CFEItem.Totales.RetencPercepTot.RetencPercepTotItem();// Código
					/*																																						// Retención/Percepción
					retencperceptotitem.setRetPercCodRet("");// Código de retención /
												// percepción (nro form + nro
												// lin)
					retencperceptotitem.setRetPercValRetPerc(Env.ZERO);// Valor de la
														// retención/percepción
					totales.setTotMntImpuestoPerc(totales.getTotMntImpuestoPerc().add(retencperceptotitem.getRetPercValRetPerc()));
					retencperceptot.getRetencPercepTotItem().add(retencperceptotitem);*/
					
			totales.setRetencPercepTot(retencperceptot);// Tabla de Retencion/Percepcion
			// Total Monto - Impuesto percibido
			//totales.setTotMontoNF(Env.ZERO);// Monto no Facturable
			
			//validacion de Total Monto Total
			BigDecimal validacionTotal = validacionTotal(totales);			
			if(invoice.getGrandTotal().setScale(1, RoundingMode.HALF_UP).compareTo(validacionTotal.setScale(1, RoundingMode.HALF_UP))==0){
				totales.setTotMntTotal(invoice.getGrandTotal());// Total Monto Total
			}
			else{
				throw new AdempiereException("error en validar Total Monto Total");
			}
			//validacion de Total a Pagar
			BigDecimal validacionPagar = validacionPagar(totales);
			if(invoice.getGrandTotal().compareTo(validacionPagar)==0){
				totales.setTotMntPagar(invoice.getGrandTotal());// Monto total a pagar
			}else{
				throw new AdempiereException("error en validar Monto total a pagar");
			}
			

			return totales;
			
		} catch (Exception e) {

			throw new AdempiereException(e);
		}
	}
	
	/**
	 * Validaciones que realiza DGI sobre los totales
	 * OpenUp Ltda. Issue # 2208
	 * @author Leonardo Boccone - 18/06/2014
	 * @see
	 * @param totales
	 * @return
	 */
	private BigDecimal validacionPagar(Totales totales) {
		BigDecimal validacionPagar = Env.ZERO;
		if(totales.getTotMontoNF()==null){
			totales.setTotMontoNF(Env.ZERO);
		}
		validacionPagar=validacionPagar.add(totales.getTotMontoNF());//"TotMontoNF" 
		if(totales.getTotMntTotRetenido()==null){
			totales.setTotMntTotRetenido(Env.ZERO);
		}
		validacionPagar=validacionPagar.add(totales.getTotMntTotRetenido());//"TotMntTotRetenido"
		validacionPagar=validacionPagar.add(totales.getTotMntTotal());//"TotMntTotal"
		return validacionPagar;
	}
	/**
	 * Validaciones que realiza DGI sobre los totales
	 * OpenUp Ltda. Issue # 2208
	 * @author Leonardo Boccone - 18/06/2014
	 * @see
	 * @param totales
	 * @return
	 */
	private BigDecimal validacionTotal(Totales totales) {
		
		BigDecimal validacionTotal= Env.ZERO;
		if(totales.getTotMntIVaenSusp()==null){
			totales.setTotMntIVaenSusp(Env.ZERO);
		}		
		validacionTotal = validacionTotal.add(totales.getTotMntIVaenSusp());//"TotMntIVaenSusp"
		if(totales.getTotMntNetoIvaTasaMin()==null){
			totales.setTotMntNetoIvaTasaMin(Env.ZERO);
		}	
		validacionTotal = validacionTotal.add(totales.getTotMntNetoIvaTasaMin());//"TotMntNetoIvaTasaMin"
		if(totales.getTotMntNetoIVATasaBasica()==null){
			totales.setTotMntNetoIVATasaBasica(Env.ZERO);
		}		
		
		validacionTotal = validacionTotal.add(totales.getTotMntNetoIVATasaBasica());//"TotMntNetoIVATasaBasica"
		if(totales.getTotMntNetoIVAOtra()==null){
			totales.setTotMntNetoIVAOtra(Env.ZERO);
		}		
		validacionTotal = validacionTotal.add(totales.getTotMntNetoIVAOtra());//"TotMntNetoIVAOtra"
		
		if(totales.getTotMntIVATasaMin()==null){
			totales.setTotMntIVATasaMin(Env.ZERO);
		}		
		validacionTotal = validacionTotal.add(totales.getTotMntIVATasaMin());//"TotMntIVATasaMin"
		if(totales.getTotMntIVATasaBasica()==null){
			totales.setTotMntIVATasaBasica(Env.ZERO);
		}		
		
		validacionTotal = validacionTotal.add(totales.getTotMntIVATasaBasica());//"TotMntIVATasaBasica"
		if(totales.getTotMntIVAOtra()==null){
			totales.setTotMntIVAOtra(Env.ZERO);
		}	
		validacionTotal = validacionTotal.add(totales.getTotMntIVAOtra());//"TotMntIVAOtra"
		
		if(totales.getTotMntImpuestoPerc()==null){
			totales.setTotMntImpuestoPerc(Env.ZERO);
		}		
		validacionTotal = validacionTotal.add(totales.getTotMntImpuestoPerc());//"TotMntImpuestoPerc"
		if(totales.getTotMntExpoyAsim()==null){
			totales.setTotMntExpoyAsim(Env.ZERO);
		}		
		validacionTotal = validacionTotal.add(totales.getTotMntExpoyAsim());//"TotMntExpoyAsim"
		
		if(totales.getTotMntNoGrv()==null){
			totales.setTotMntNoGrv(Env.ZERO);
		}		
		validacionTotal = validacionTotal.add(totales.getTotMntNoGrv());//"TotMntNoGrv"
		
		return validacionTotal;
	}

	private Detalle cargarDetalle(MInvoice invoice, MInvoiceLine[] lines) {
		try{
			
		EnvioCFE.CFE.CFEItem.Detalle detalle = new EnvioCFE.CFE.CFEItem.Detalle();// Detalle del CFE
		
		for (int l = 0; l < lines.length; l++) {	
			
		EnvioCFE.CFE.CFEItem.Detalle.Item detalleitem = new EnvioCFE.CFE.CFEItem.Detalle.Item();	
		/*EnvioCFE.CFE.CFEItem.Detalle.Item.CodItem detalleitemcoditem = new EnvioCFE.CFE.CFEItem.Detalle.Item.CodItem();// CodificaciondelItem
			EnvioCFE.CFE.CFEItem.Detalle.Item.CodItem.CodItemItem detalleitemcoditemitem = new EnvioCFE.CFE.CFEItem.Detalle.Item.CodItem.CodItemItem();
			//detalleitemcoditemitem.setIteCodiCod("");// Codigo de Item, para la Codificacion TpoCod
			//detalleitemcoditemitem.setIteCodiTpoCod("");// Tipo de código
			detalleitemcoditem.getCodItemItem().add(detalleitemcoditemitem);
		detalleitem.setCodItem(detalleitemcoditem);// Codificacion del Item*/
		
		
		MTax tax = new MTax(invoice.getCtx(),lines[l].getC_Tax_ID(),invoice.get_TrxName());
		if(tax.getValue().equalsIgnoreCase("minimo")){
			detalleitem.setIteIndFact(2);
		}
		else if(tax.getValue().equalsIgnoreCase("basico")){
			detalleitem.setIteIndFact(3);
		}
		else if(tax.getValue().equalsIgnoreCase("exento")){
			detalleitem.setIteIndFact(1);
		}
		else {
			throw new AdempiereException("Tasa en linea de invoice nº "+invoice.getDocumentNo()+ "no valida");
		}
		/*
		* Indicador de Facturacion: 
		* 1: Exento deIVA 
		* 2: Gravado a Tasa Mínima 
		* 3: Gravado aTasa Básica 
		* 4. Gravado a "Otra Tasa" 
		* 6:Producto o servicio no facturable 
		* 7:Producto o servicio no facturablenegativo
		*/
		detalleitem.setIteIndAgenteResp("");// Indicador Agente/Responsable
		detalleitem.setIteNomItem(lines[l].getProduct().getName());// Nombre del ítem
		detalleitem.setIteDscItem(lines[l].getProduct().getDescription());// Descripción Adicional
		detalleitem.setIteCantidad(lines[l].getQtyInvoiced());// Cantidad
		detalleitem.setIteUniMed(lines[l].getProduct().getUOMSymbol());// Unidad de medida
		detalleitem.setItePrecioUnitario(lines[l].getPriceActual().setScale(2, RoundingMode.HALF_UP));// Precio unitario
		
		if(lines[l].getFlatDiscount()!=null && lines[l].getFlatDiscount().compareTo(Env.ZERO)>0){			
			BigDecimal descuento = lines[l].getPriceActual().subtract(lines[l].getPriceEntered());			
			detalleitem.setIteDescuentoMonto(descuento);// Monto Descuento
			detalleitem.setIteDescuentoPct(lines[l].getFlatDiscount());// Descuento en %
		}
				
			/*EnvioCFE.CFE.CFEItem.Detalle.Item.SubDescuento itemsubdesuento = new EnvioCFE.CFE.CFEItem.Detalle.Item.SubDescuento();
				EnvioCFE.CFE.CFEItem.Detalle.Item.SubDescuento.SubDescuentoItem itemsubdesuentoitem = new EnvioCFE.CFE.CFEItem.Detalle.Item.SubDescuento.SubDescuentoItem();
				//itemsubdesuentoitem.setSubDescDescTipo(BigInteger.valueOf(0));// Indica si esta en 1 = $ o 2 =%
				//itemsubdesuentoitem.setSubDescDescVal(Env.ZERO);// Subdescuento – valor
			itemsubdesuento.getSubDescuentoItem().add(itemsubdesuentoitem);
		detalleitem.setSubDescuento(itemsubdesuento);// Distribucion del
														// Descuento*/
	
			//detalleitem.setIteRecargoPct(Env.ZERO);// Recargo en %
			//detalleitem.setIteRecargoMnt(Env.ZERO);// Totaliza los recargos otorgados al item
			
			/*EnvioCFE.CFE.CFEItem.Detalle.Item.SubRecargo subrecargo = new EnvioCFE.CFE.CFEItem.Detalle.Item.SubRecargo();
				EnvioCFE.CFE.CFEItem.Detalle.Item.SubRecargo.SubRecargoItem subrecargoitem = new EnvioCFE.CFE.CFEItem.Detalle.Item.SubRecargo.SubRecargoItem();
				//subrecargoitem.setSubRecaRecargoTipo(BigInteger.valueOf(0));// Indica si esta en 1 = $ o 2 = %
				//subrecargoitem.setSubRecaRecargoVal(Env.ZERO);// Subrecargo – valor
			subrecargo.getSubRecargoItem().add(subrecargoitem);
		detalleitem.setSubRecargo(subrecargo);*/
		
			/*EnvioCFE.CFE.CFEItem.Detalle.Item.RetencPercep retper = new EnvioCFE.CFE.CFEItem.Detalle.Item.RetencPercep();
				EnvioCFE.CFE.CFEItem.Detalle.Item.RetencPercep.RetencPercepItem retperitem = new EnvioCFE.CFE.CFEItem.Detalle.Item.RetencPercep.RetencPercepItem();
				//retperitem.setIteRetPercCodRet("");// Código de Retenciones/Percepciones
				//retperitem.setIteRetPercMntSujetoaRet(Env.ZERO);// Monto sujeto a retención/percepción
				//retperitem.setIteRetPercTasa(BigInteger.valueOf(0));// Tasa vigente a la fecha del comprobante
				//retperitem.setIteRetPercValRetPerc(Env.ZERO);// Valor de la retención/percepción
				retper.getRetencPercepItem().add(retperitem);
			detalleitem.setRetencPercep(retper);*/
		detalleitem.setIteMontoItem(lines[l].getLineNetAmt());// Monto por Linea de Detalle. Corresponde al Monto Neto, a menos que MntBruto Indique lo Contrario
		detalle.getItem().add(detalleitem);
		}
		return detalle;
		} catch (Exception e) {

			throw new AdempiereException(e);
			}
	}
	
	private SubTotInfo cargarSubTotInfo(MInvoice invoice,
			MInvoiceLine[] lines) {
		try{
		EnvioCFE.CFE.CFEItem.SubTotInfo subtotinfo = new EnvioCFE.CFE.CFEItem.SubTotInfo();
			/*EnvioCFE.CFE.CFEItem.SubTotInfo.STIItem stiitem = new EnvioCFE.CFE.CFEItem.SubTotInfo.STIItem();// Subtotales Informativos
			//stiitem.setSubTotGlosaSTI("");// Titulo del Subtotal
			//stiitem.setSubTotNroSTI(0);// Número Subtotal
			//stiitem.setSubTotOrdenSTI(0);// Ubicación para Impresión
			//stiitem.setSubTotValSubtotSTI(Env.ZERO);// Valor de la línea de subtotal
		subtotinfo.getSTIItem().add(stiitem);*/
		return subtotinfo;
		} catch (Exception e) {

			throw new AdempiereException(e);
			}
	}
	
	private DscRcgGlobal cargarDscRcgGlobal(MInvoice invoice,
			MInvoiceLine[] lines) {
		try{
	// Descuentos y/o Recargos que aumentan o disminuyen la base del impuesto
		EnvioCFE.CFE.CFEItem.DscRcgGlobal dscrcg = new EnvioCFE.CFE.CFEItem.DscRcgGlobal();
		/*EnvioCFE.CFE.CFEItem.DscRcgGlobal.DRGItem drgitem = new EnvioCFE.CFE.CFEItem.DscRcgGlobal.DRGItem();
			//drgitem.setDscRcgCodDR(BigInteger.valueOf(0));// Código del Descuento/Recargo
			//drgitem.setDscRcgGlosaDR("");// Descripcion del Descuento o Recargo
			//drgitem.setDscRcgNroLinDR(0);// N° de línea o N° Secuencial
			//drgitem.setDscRcgTpoDR(BigInteger.valueOf(0));// Tipo Descuento o Recargo
			//drgitem.setDscRcgTpoMovDR("");// Tipo de movimiento
			//drgitem.setDscRcgValorDR(Env.ZERO);// Valor del Descuento o Recargo
			/*drgitem.setDscRcgIndFactDR(0);/* Indicador de facturación
									 * 1: Exento de IVA 
									 * 2: Gravado a Tasa Mínima 
									 * 3: Gravado a Tasa Básica 
									 * 4. Gravado a "Otra Tasa" 
									 * 6: Producto o servicio no facturable 
									 * 7: Producto o servicio no facturable negativo
									 
		dscrcg.getDRGItem().add(drgitem);*/
		return dscrcg;
		} catch (Exception e) {

			throw new AdempiereException(e);
			}
	}
	
	private MediosPago cargarMedios(MInvoice invoice, MInvoiceLine[] lines) {
		try{
		EnvioCFE.CFE.CFEItem.MediosPago mediospago = new EnvioCFE.CFE.CFEItem.MediosPago();
	
			/*EnvioCFE.CFE.CFEItem.MediosPago.MediosPagoItem mediopagoitem = new EnvioCFE.CFE.CFEItem.MediosPago.MediosPagoItem();
				//mediopagoitem.setMedPagCodMP(BigInteger.valueOf(0));// Código del Medio de Pago
				//mediopagoitem.setMedPagGlosaMP("");// Especificación del M. de Pago
				//mediopagoitem.setMedPagNroLinMP(0);// N° Línea o N° Secuencial
				//mediopagoitem.setMedPagOrdenMP(BigInteger.valueOf(0));// Ubicación para la impresión
				//mediopagoitem.setMedPagValorPago(Env.ZERO);// Valor del Medio de Pago
			mediospago.getMediosPagoItem().add(mediopagoitem);	*/	
		return mediospago;
		} catch (Exception e) {

			throw new AdempiereException(e);
			}
	}
	
	private Referencia cargarReferencia(MInvoice invoice,
			MInvoiceLine[] lines) {
		
	try{
		// Identificacion de otros documentos Referenciados por Documento
		EnvioCFE.CFE.CFEItem.Referencia ref = new EnvioCFE.CFE.CFEItem.Referencia();
			/*EnvioCFE.CFE.CFEItem.Referencia.ReferenciaItem refitem = new EnvioCFE.CFE.CFEItem.Referencia.ReferenciaItem();
				//refitem.setRefAccion("");// Acción sobre el CFE de referencia
				//refitem.setRefFechaCFEref(null);// Fecha CFE de referencia
				//refitem.setRefIndGlobal(BigInteger.valueOf(0));// Indica que se esta Referenciando un Conjunto de Documentos
				//refitem.setRefNroCFERef(BigInteger.valueOf(0));// Número del CFE de referencia
				//refitem.setRefNroLinRef(0);// Numero Secuencial de Linea de Referencia
				//refitem.setRefRazonRef("");// Razon Explicita por la que se Referencia el Documento
				//refitem.setRefSerie("");// Serie del CFE de referencia
				//refitem.setRefTpoDocRef(BigInteger.valueOf(0));// Tipo CFE de referencia
			ref.getReferenciaItem().add(refitem);*/
		return ref;
	} catch (Exception e) {

	throw new AdempiereException(e);
	}


	}

	/**
	 * Genero el xml con los datos previamente cargados en la ruta especificada en la ventana de parametrizacion 
	 * OpenUp Ltda. Issue # 2208
	 * @author Leoanrdo Boccone - 30/05/2014
	 * @param TrxName 
	 * @param Ctx 
	 * @see
	 * @throws JAXBException
	 * @throws IOException 
	 */
	public void Serializar(Properties Ctx, String TrxName) throws JAXBException, IOException {

	
		try {
			JAXBContext context = JAXBContext.newInstance(EnvioCFE.class);
		    Marshaller m = context.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    
		    MSequence sec = MSequence.get(Ctx, "CFE_ENV");
		    int numero = DB.getSQLValue(TrxName, "select nextid("+sec.get_ID()+",'N')");
		    // Write to File
		    String numerosecuencia = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(numero), 5, "0");
		    String numerocodempresa = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(envio.getEncabezado().getEmpCodigo().trim()), 5, "0");
		    codigo = "ENV"+numerocodempresa+numerosecuencia+".xml";
		    String nuevo = rutaEntrada+codigo;
		   
		    StringWriter sw = new StringWriter();			 
		    m.marshal(envio, sw);
		    
		    String corrected = sw.toString();
		    corrected = correct(corrected);
		    		
		    
		    java.io.FileWriter fw = new java.io.FileWriter(nuevo);
		    fw.write(corrected);
		    fw.close();
		    
		    //m.marshal(aa, new File(nuevo));

		} catch (JAXBException e) {
			
			throw new AdempiereException(e);
		}

	}
	
	private String correct(String corrected) {
		corrected = corrected.replace(" xmlns=\"www.invoicy.com.uy\"", "");
		if(corrected.contains("á")||corrected.contains("é")||corrected.contains("í")||corrected.contains("ó")||corrected.contains("ú")){
			 corrected.replace("á", "a");
			corrected = corrected.replace("é", "e");
			corrected = corrected.replace("í", "i");
			corrected = corrected.replace("ó", "o");
			corrected = corrected.replace("ú", "u");
		}
		return corrected;
	}
	public boolean IncomingInvoice(String codigo, MCFEInvoice status) {

		File invoice = new File(rutaSalida + "RET" + codigo);
		try {
			if (invoice.exists()) {

				if(retorno(invoice, status)){
					return true;
				}
				else{
					return false;
				}

				

			} else {

				if(tryagain(codigo, status)){
					return true;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean tryagain(String codigo2, MCFEInvoice status) throws Exception {
		while(contador<240){
			contador++;
			Thread.sleep(1000);
			if(contador==240){
				throw new AdempiereException("Falta Respuesta");
				
			}
			if(this.IncomingInvoice(codigo,status)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
		
	}
	/**
	 * Chequeo si el retorno es ok OpenUp Ltda. Issue #2208
	 * 
	 * @author Leonardo Boccone - 04/07/2014
	 * @see
	 * @param source
	 * @param status 
	 * @return
	 * @throws Exception 
	 */
	private boolean retorno(File source, MCFEInvoice status) throws Exception {
		boolean respuesta = false;
		List<String> errores=new ArrayList<String>();
		int cfestatus = 0;

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
						if (e.getName().equalsIgnoreCase("CFEStatus") && cfestatus==0) {
							cfestatus++;
							if (e.getValue().trim().startsWith("1")) {
								tryagain(codigo,status);
							}
							else if (e.getValue().trim().startsWith("2")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_2_EnProcesamiento);		
								respuesta = true;					
							} 
							else if (e.getValue().trim().startsWith("3")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_3_Rechazado);		
							} 
							else if (e.getValue().trim().startsWith("4")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_4_EnEspera);		
								respuesta = true;
							} 
							else if (e.getValue().trim().startsWith("5")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_5_Autorizado);		
								respuesta = true;
							} 
							else if (e.getValue().trim().startsWith("6")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_6_Anulado);
								
							}
							else if (e.getValue().trim().startsWith("8")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_8_RechazadoPorDGI);
								
							}
							else if (e.getValue().trim().startsWith("9")) {
								tryagain(codigo,status);
							} 
							else if (e.getValue().trim().startsWith("0")) {
								status.setCFEStatusType(X_UY_CFE_Invoice.CFESTATUSTYPE_3_Rechazado);
							} 
							else {
								throw new AdempiereException("Codigo de Respuesta Invalido");
							}
							
						}
						if (e.getName().equalsIgnoreCase("CFEErrDesc")) {
							if(!errores.contains(e.getValue())){
								errores.add(e.getValue());
								
							}
							
						}
						
					}
				}
			}

		} else {
			throw new AdempiereException("el archivo " + source.getName()
					+ " no se puede leer");
		}
		for(String s : errores){
			if(status.getDescription()==null){
				status.setDescription(s);
			}
			else{
				if(status.getDescription().length()<100){
					status.setDescription(status.getDescription()+" - "+s);
				}				
			}
			
		}
		status.saveEx();
		return respuesta;
	}


}
