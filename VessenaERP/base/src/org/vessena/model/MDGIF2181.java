/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/08/2013
 */
package org.openup.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConversionRate;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTax;
import org.compiere.model.MYear;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.util.OpenUpUtils;

/**
 * org.openup.model - MDGIF2181
 * OpenUp Ltda. Issue # 
 * Description: Modelo de cabezal de proceso del formato 2181 para DGI
 * @author Gabriel Vila - 20/08/2013
 * @see
 */
public class MDGIF2181 extends X_UY_DGI_F2181 {

	private static final long serialVersionUID = 5478858604011707046L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_DGI_F2181_ID
	 * @param trxName
	 */
	public MDGIF2181(Properties ctx, int UY_DGI_F2181_ID, String trxName) {
		super(ctx, UY_DGI_F2181_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDGIF2181(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y carga modelo con documentos a considerar en el proceso F2181 de la DGI
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/08/2013
	 * @see
	 * @return
	 */
	public boolean getData(){
		
		boolean hayCompras = false, hayVentas = false;

		// Instancio periodo para obtener rango de fechas para el proceso
		MPeriod period = (MPeriod)this.getC_Period();

		// Fecha final del periodo con hora 23:59:59 ya que considero el campo CREATED de la invoice para el proceso.
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Timestamp fechaHasta = Timestamp.valueOf(df.format(period.getEndDate()));
		
		// Elimino datos anteriores
		this.deleteData();
		
		// Cargo documentos de compra
		hayCompras = this.getDataCompras(period.getStartDate(), fechaHasta);
		
		// Cargo documentos de venta
		hayVentas = this.getDataVentas(period.getStartDate(), fechaHasta);
		
		return (hayCompras | hayVentas);
		
	}
	
	/***
	 * Obtiene y carga modelo de documentos de compra segun rango de fechas recibido.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/08/2013
	 * @see
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	private boolean getDataCompras(Timestamp startDate, Timestamp endDate){

		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean hayInfo = false;
		
		HashMap<String, String> hashRut = new HashMap<String, String>();
		
		try{
			
			sql.append(" select inv.c_invoice_id, inv.documentno, coalesce(inv.datevendor,inv.dateinvoiced) as dateinvoiced, " +
					   " inv.c_doctype_id, inv.c_bpartner_id, " +
					   " bp.name as fantasia, bp.name2 as razonsocial,bp.duns as rut, inv.c_currency_id, " +
					   " inv.currencyrate, c_tax.c_tax_id, doc.docbasetype, invtax.taxamt as monto " +
					   " from c_invoice inv " +
					   " inner join c_invoicetax invtax on inv.c_invoice_id = invtax.c_invoice_id " +
					   " inner join c_tax on invtax.c_tax_id = c_tax.c_tax_id " +
					   " inner join c_doctype doc ON inv.c_doctype_id = doc.c_doctype_id " +
					   " inner join c_bpartner bp on inv.c_bpartner_id = bp.c_bpartner_id " +
					   " where inv.dateinvoiced between ? and ? " +
					   " and inv.docstatus = 'CO' " +
					   " and inv.issotrx = 'N' " +
					   " and doc.docbasetype in ('API','APC') " +
					   " and doc.allocationbehaviour is not null " +
					   " and c_tax.value='basico' "); 
			sql.append(" union ");
			sql.append(" select inv.c_invoice_id, inv.documentno, coalesce(inv.datevendor,inv.dateinvoiced) as dateinvoiced, " +
					   " inv.c_doctype_id, inv.c_bpartner_id, " +
					   " bp.name as fantasia, bp.name2 as razonsocial,bp.duns as rut, inv.c_currency_id, " +
					   " inv.currencyrate, c_tax.c_tax_id, doc.docbasetype, invtax.taxamt as monto " +
					   " from c_invoice inv " +
					   " inner join c_invoicetax invtax on inv.c_invoice_id = invtax.c_invoice_id " +
					   " inner join c_tax on invtax.c_tax_id = c_tax.c_tax_id " +
					   " inner join c_doctype doc ON inv.c_doctype_id = doc.c_doctype_id " +
					   " inner join c_bpartner bp on inv.c_bpartner_id = bp.c_bpartner_id " +
					   " where inv.dateinvoiced between ? and ? " +
					   " and inv.docstatus = 'CO' " +
					   " and inv.issotrx = 'N' " +
					   " and doc.docbasetype in ('API','APC') " +
					   " and doc.allocationbehaviour is not null " +
					   " and c_tax.value='minimo' ");
			
			boolean includeExento = MSysConfig.getBooleanValue("UY_DGI_F2181_INCLUDE_EXENTO", true, this.getAD_Client_ID());
			
			if (includeExento){

				sql.append(" union ");
				
				sql.append(" select inv.c_invoice_id, inv.documentno, coalesce(inv.datevendor,inv.dateinvoiced) as dateinvoiced, " +
						   " inv.c_doctype_id, inv.c_bpartner_id, " +
						   " bp.name as fantasia, bp.name2 as razonsocial,bp.duns as rut, inv.c_currency_id, " +
						   " inv.currencyrate, c_tax.c_tax_id, doc.docbasetype, sum(invline.linenetamt) as monto " +
						   " from c_invoice inv " +
						   " inner join c_invoiceline invline on inv.c_invoice_id = invline.c_invoice_id " +
						   " inner join c_tax on invline.c_tax_id = c_tax.c_tax_id " +
						   " inner join c_doctype doc ON inv.c_doctype_id = doc.c_doctype_id " +
						   " inner join c_bpartner bp on inv.c_bpartner_id = bp.c_bpartner_id " +
						   " where inv.dateinvoiced between ? and ? " +
						   " and inv.docstatus = 'CO' " +
						   " and inv.issotrx = 'N' " +
						   " and doc.docbasetype in ('API','APC') " +
						   " and doc.allocationbehaviour is not null " +
						   " and c_tax.value='exento' " +
						   " group by inv.c_invoice_id, inv.documentno, inv.dateinvoiced, inv.c_doctype_id, inv.c_bpartner_id, " +
						   " bp.name, bp.name2, bp.duns, inv.c_currency_id, inv.currencyrate, c_tax.c_tax_id, doc.docbasetype " +
						   " having sum(invline.linenetamt) >= 1 " +
						   " order by c_tax_id, fantasia, dateinvoiced, c_doctype_id, c_invoice_id ");
			}
			
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setTimestamp(1, startDate);
			pstmt.setTimestamp(2, endDate);
			pstmt.setTimestamp(3, startDate);
			pstmt.setTimestamp(4, endDate);
			
			if (includeExento){
				pstmt.setTimestamp(5, startDate);
				pstmt.setTimestamp(6, endDate);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				hayInfo = true;
				
				// Valido RUT 
				if ((rs.getString("rut") == null) || (rs.getString("rut").equalsIgnoreCase(""))){
					String partner = String.valueOf(rs.getInt("c_bpartner_id"));
					if (!hashRut.containsKey(partner)){
						MDGIF2181Issue issue =  new MDGIF2181Issue(getCtx(), 0, null);
						issue.setUY_DGI_F2181_ID(this.get_ID());
						issue.setDescription("Socio de Negocio : " + rs.getString("fantasia") + " NO tiene RUT definido");
						issue.saveEx();

						hashRut.put(partner, partner);
					}
					continue;
				}
				
				// Si el Rut no es valido contra regla de validacion MOD 11.
				if (!OpenUpUtils.validateRUT(rs.getString("rut").trim())){
					String partner = String.valueOf(rs.getInt("c_bpartner_id"));
					if (!hashRut.containsKey(partner)){
						MDGIF2181Issue issue =  new MDGIF2181Issue(getCtx(), 0, null);
						issue.setUY_DGI_F2181_ID(this.get_ID());
						issue.setDescription("Socio de Negocio : " + rs.getString("fantasia") + " tiene RUT INVALIDO (" + rs.getString("rut").trim() + ")");
						issue.saveEx();
						hashRut.put(partner, partner);
					}
					continue;
				}
				
				// Moneda, tasa de cambio y monto
				int currencyID = rs.getInt("c_currency_id");
				BigDecimal currencyRate = Env.ONE;
				BigDecimal monto = rs.getBigDecimal("monto");

				if (currencyID != 142){
					if (rs.getBigDecimal("currencyrate") != null){
						currencyRate = rs.getBigDecimal("currencyrate");
					}
					else{
						Timestamp fechaRate = TimeUtil.trunc(rs.getTimestamp("dateinvoiced"), TimeUtil.TRUNC_DAY);
						currencyRate = MConversionRate.getRate(currencyID, 142, fechaRate, 0, this.getAD_Client_ID(), 0);
						if (currencyRate == null){
							throw new AdempiereException("No se pudo obtener Tasa de Cambio para Fecha : " + fechaRate);
						}
					}
					monto = monto.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP);
				}
				
				// Si el documento es del tipo nota de credito, doy vuelta el signo del monto.
				if (rs.getString("docbasetype").equalsIgnoreCase("APC")){
					monto = monto.negate();
				}

				// Instancio modelo
				MDGIF2181Line line = new MDGIF2181Line(getCtx(), 0, null);
				line.setUY_DGI_F2181_ID(this.get_ID());
				line.setC_Invoice_ID(rs.getInt("c_invoice_id"));
				line.setDateInvoiced(rs.getTimestamp("dateinvoiced"));
				line.setC_DocType_ID(rs.getInt("c_doctype_id"));
				line.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				line.setName(rs.getString("fantasia"));
				line.setName2(rs.getString("razonsocial"));
				line.setC_Currency_ID(currencyID);
				line.setCurrencyRate(currencyRate);
				line.setDUNS(rs.getString("rut"));
				line.setC_Tax_ID(rs.getInt("c_tax_id"));
				line.setAmount(monto.setScale(0, RoundingMode.HALF_UP));
				line.setDocumentNo(rs.getString("documentno"));
				line.setDocBaseType(rs.getString("docbasetype"));
				
				// Linea Rubro 5. Valor depende del tipo de documento y del tipo de impuesto.
				MTax impuesto = (MTax)line.getC_Tax();
				String rubro ="";
				if (impuesto.getValue().equalsIgnoreCase("basico")){
					rubro = "505";
				}
				else if (impuesto.getValue().equalsIgnoreCase("minimo")){
					rubro = "506";
				}
				else if (impuesto.getValue().equalsIgnoreCase("exento")){
					rubro = "504";
				}

				line.setRubroDGI(rubro);

				line.saveEx();
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return hayInfo;

	}
	
	/***
	 * Obtiene y carga modelo de documentos de venta segun rango de fechas recibido.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/08/2013
	 * @see
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	private boolean getDataVentas(Timestamp startDate, Timestamp endDate){
		
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean hayInfo = false;
		
		HashMap<String, String> hashRut = new HashMap<String, String>();
		
		try{
			
			sql.append(" select inv.c_invoice_id, inv.documentno, inv.dateinvoiced, inv.c_doctypetarget_id, inv.c_bpartner_id, " +
					   "bp.name as fantasia, bp.name2 as razonsocial,bp.duns as rut, inv.c_currency_id, " +
					   " inv.currencyrate, c_tax.c_tax_id, doc.docbasetype, invtax.taxamt as monto " +
					   " from c_invoice inv " +
					   " inner join c_invoicetax invtax on inv.c_invoice_id = invtax.c_invoice_id " +
					   " inner join c_tax on invtax.c_tax_id = c_tax.c_tax_id " +
					   " inner join c_doctype doc ON inv.c_doctypetarget_id = doc.c_doctype_id " +
					   " inner join c_bpartner bp on inv.c_bpartner_id = bp.c_bpartner_id " +
					   " where inv.dateinvoiced between ? and ? " +
					   " and inv.docstatus = 'CO' " +
					   " and inv.issotrx = 'Y' " +
					   " and doc.docbasetype in ('ARI','ARC') " +
					   " and doc.allocationbehaviour is not null " +
					   " and c_tax.value='basico' "); 
			sql.append(" union ");
			sql.append(" select inv.c_invoice_id, inv.documentno, inv.dateinvoiced, inv.c_doctypetarget_id, inv.c_bpartner_id, " +
					   " bp.name as fantasia, bp.name2 as razonsocial,bp.duns as rut, inv.c_currency_id, " +
					   " inv.currencyrate, c_tax.c_tax_id, doc.docbasetype, invtax.taxamt as monto " +
					   " from c_invoice inv " +
					   " inner join c_invoicetax invtax on inv.c_invoice_id = invtax.c_invoice_id " +
					   " inner join c_tax on invtax.c_tax_id = c_tax.c_tax_id " +
					   " inner join c_doctype doc ON inv.c_doctypetarget_id = doc.c_doctype_id " +
					   " inner join c_bpartner bp on inv.c_bpartner_id = bp.c_bpartner_id " +
					   " where inv.dateinvoiced between ? and ? " +
					   " and inv.docstatus = 'CO' " +
					   " and inv.issotrx = 'Y' " +
					   " and doc.docbasetype in ('ARI','ARC') " +
					   " and doc.allocationbehaviour is not null " +
					   " and c_tax.value='minimo' ");
			sql.append(" union ");
			sql.append(" select pay.c_payment_id as c_invoice_id, pay.documentno, pay.datetrx as dateinvoiced, " +
					   " pay.c_doctype_id as c_doctypetarget_id, pay.c_bpartner_id, " +
					   " bp.name as fantasia, bp.name2 as razonsocial, bp.duns as rut, pay.c_currency_id, " +
					   " pay.currencyrate,(select c_tax_id from c_tax where ad_client_id=pay.ad_client_id and value='basico') as c_tax_id, doc.docbasetype, pay.discountamt as monto " +
					   " from c_payment pay " + 
					   " inner join c_doctype doc ON pay.c_doctype_id = doc.c_doctype_id " + 
					   " inner join c_bpartner bp on pay.c_bpartner_id = bp.c_bpartner_id " +
					   " where pay.datetrx between ? and ?  " +  
					   " and pay.docstatus = 'CO' " +  
					   " and pay.isreceipt = 'Y' " +
					   " and pay.discountamt >= 10 " +
					" order by c_tax_id, fantasia, dateinvoiced, c_invoice_id ");
			
			pstmt = DB.prepareStatement(sql.toString(), null);

			pstmt.setTimestamp(1, startDate);
			pstmt.setTimestamp(2, endDate);
			pstmt.setTimestamp(3, startDate);
			pstmt.setTimestamp(4, endDate);
			pstmt.setTimestamp(5, startDate);
			pstmt.setTimestamp(6, endDate);
			
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				hayInfo = true;
				
				// Valido RUT 
				if ((rs.getString("rut") == null) || (rs.getString("rut").equalsIgnoreCase(""))){
					String partner = String.valueOf(rs.getInt("c_bpartner_id"));
					if (!hashRut.containsKey(partner)){
						MDGIF2181Issue issue =  new MDGIF2181Issue(getCtx(), 0, null);
						issue.setUY_DGI_F2181_ID(this.get_ID());
						issue.setDescription("Socio de Negocio : " + rs.getString("fantasia") + " NO tiene RUT definido");
						issue.saveEx();

						hashRut.put(partner, partner);
					}
					continue;
				}
				
				// Si el Rut no es valido contra regla de validacion MOD 11.
				if (!OpenUpUtils.validateRUT(rs.getString("rut").trim())){
					String partner = String.valueOf(rs.getInt("c_bpartner_id"));
					if (!hashRut.containsKey(partner)){
						MDGIF2181Issue issue =  new MDGIF2181Issue(getCtx(), 0, null);
						issue.setUY_DGI_F2181_ID(this.get_ID());
						issue.setDescription("Socio de Negocio : " + rs.getString("fantasia") + " tiene RUT INVALIDO (" + rs.getString("rut").trim() + ")");
						issue.saveEx();
						hashRut.put(partner, partner);
					}
					continue;
				}
				
				// Moneda, tasa de cambio y monto
				int currencyID = rs.getInt("c_currency_id");
				BigDecimal currencyRate = Env.ONE;
				BigDecimal monto = rs.getBigDecimal("monto");

				if (currencyID != 142){
					if ( (rs.getBigDecimal("currencyrate") != null) 
							&& (rs.getBigDecimal("currencyrate").compareTo(Env.ZERO) > 0)){
						currencyRate = rs.getBigDecimal("currencyrate");
					}
					else{
						Timestamp fechaRate = TimeUtil.trunc(rs.getTimestamp("dateinvoiced"), TimeUtil.TRUNC_DAY);
						currencyRate = MConversionRate.getRate(currencyID, 142, fechaRate, 0, this.getAD_Client_ID(), 0);
						if (currencyRate == null){
							throw new AdempiereException("No se pudo obtener Tasa de Cambio para Fecha : " + fechaRate);
						}
					}
					monto = monto.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP);
				}
				
				// Si el documento es del tipo nota de credito o recibo de cobro, doy vuelta el signo del monto.
				if (rs.getString("docbasetype").equalsIgnoreCase("ARC")){
					monto = monto.negate();
				}
				
				//Si el documento es un recibo de cobro con descuento, descrimino ese descuento sin IVA e invierto el signo
				if (rs.getString("docbasetype").equalsIgnoreCase("ARR")){
					
					/*
					BigDecimal bg = new BigDecimal(1.22);
					BigDecimal bg1 = new BigDecimal(0.22);
					monto = ((monto.divide(bg).multiply(bg1)));
					monto = monto.negate();
					*/
					monto = monto.divide(new BigDecimal(1.22), 2, RoundingMode.HALF_UP);
					monto = monto.multiply(new BigDecimal(0.22)).setScale(0, RoundingMode.HALF_UP);
					monto = monto.negate();
				}

				// Instancio modelo
				MDGIF2181Line line = new MDGIF2181Line(getCtx(), 0, null);
				line.setUY_DGI_F2181_ID(this.get_ID());
				line.setC_Invoice_ID(rs.getInt("c_invoice_id"));
				line.setDateInvoiced(rs.getTimestamp("dateinvoiced"));
				line.setC_DocType_ID(rs.getInt("c_doctypetarget_id"));
				line.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				line.setName(rs.getString("fantasia"));
				line.setName2(rs.getString("razonsocial"));
				line.setC_Currency_ID(currencyID);
				line.setCurrencyRate(currencyRate);
				line.setDUNS(rs.getString("rut"));
				line.setC_Tax_ID(rs.getInt("c_tax_id"));
				line.setAmount(monto.setScale(0, RoundingMode.HALF_UP));
				line.setDocumentNo(rs.getString("documentno"));
				line.setDocBaseType(rs.getString("docbasetype"));				

				// Linea Rubro 5. Valor depende del tipo de documento y del tipo de impuesto.
				MTax impuesto = (MTax)line.getC_Tax();
				String rubro ="";
				if (impuesto.getValue().equalsIgnoreCase("basico")){
					rubro = "502";
				}
				else if (impuesto.getValue().equalsIgnoreCase("minimo")){
					rubro = "503";
				}

				line.setRubroDGI(rubro);
				
				line.saveEx();

			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return hayInfo;

	}

	/***
	 * Elimina informacion procesada anteriormente.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/08/2013
	 * @see
	 */
	private void deleteData(){
		
		try {
			
			// Antes de cargar me aseguro de eliminar registros anteriores
			String action = " delete from uy_dgi_f2181_line " +
					        " where uy_dgi_f2181_id =" + this.get_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
			
			action = " delete from uy_dgi_f2181_issue " +
			        " where uy_dgi_f2181_id =" + this.get_ID();
	
			DB.executeUpdateEx(action, get_TrxName());
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Obtiene y retorna lista con lineas del modelo.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/08/2013
	 * @see
	 * @return
	 */
	private List<MDGIF2181Line> getLines(){
		
		String whereClause = X_UY_DGI_F2181_Line.COLUMNNAME_UY_DGI_F2181_ID + "=" + this.get_ID();
		
		List<MDGIF2181Line> lines = new Query(getCtx(), I_UY_DGI_F2181_Line.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
	/***
	 * Generacion de archivo plano utilizando los documentos previamente cargados.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/08/2013
	 * @see
	 */
	public void generateTXT() {

		
		BufferedWriter bw = null;

		try {
			// Recorro lineas con los documentos a considerar.
			List<MDGIF2181Line> lines = this.getLines();

			if (lines.size() <= 0) return;

			// Rut Empresa
			MOrgInfo orgInfo = MOrgInfo.get(getCtx(), this.getAD_Org_ID(), null);
			String rutClient = orgInfo.getDUNS();
			if ((rutClient == null) || (rutClient.equalsIgnoreCase(""))){
				throw new AdempiereException("Falta definir RUT de la Empresa");
			}
			rutClient = org.apache.commons.lang.StringUtils.leftPad(rutClient, 12, "0");

			// Periodo
			MPeriod period = (MPeriod)this.getC_Period();
			MYear year = (MYear)period.getC_Year();
			String month = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(period.getPeriodNo()), 2, "0");
			String literalPeriodo = String.valueOf(year.getYearAsInt()) + month; 
			
			// Creo archivo plano
			String fileName = this.createFile();
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "ASCII"));
			
			// Recorro lineas a procesar
			for(MDGIF2181Line line: lines){

				String cadena = "";
				cadena += rutClient + ";02181;" + literalPeriodo + ";";
				
				// Rut del Socio de Negocio
				String rutPartner = org.apache.commons.lang.StringUtils.leftPad(line.getDUNS(), 12, "0");
				cadena += rutPartner + ";"; 
								
				// Periodo del comprobante
				MPeriod periodInv = MPeriod.get(getCtx(), line.getDateInvoiced(), 0);
				MYear yearInv = (MYear)periodInv.getC_Year();
				String monthInv = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(periodInv.getPeriodNo()), 2, "0");				
				String literalPeriodInv = String.valueOf(yearInv.getYearAsInt()) + monthInv;
				cadena += literalPeriodInv + ";";

				// Linea Rubro 5. Valor depende del tipo de documento y del tipo de impuesto.
				cadena += line.getRubroDGI() + ";";
				
				// Importe. Doce Digitos, completo con CEROS a la izquierda, sin decimales, y si es negativo el 
				// simbolo de menos (-) se pone siempre primero. (Ej: -00000000359)
				BigDecimal monto = line.getAmount();
				String montoStr ="";
				if (monto.compareTo(Env.ZERO) >= 0){
					montoStr = String.valueOf(monto);
					montoStr = org.apache.commons.lang.StringUtils.leftPad(montoStr, 12, "0");
				}
				else{
					// Monto negativo, tengo que hacer trampita para poner el signo de menos delante de los ceros
					montoStr = String.valueOf(monto.negate());
					montoStr = "-" + org.apache.commons.lang.StringUtils.leftPad(montoStr, 11, "0");
				}
				
				cadena += montoStr + ";";
				
				// Guardo linea en TXT
				if (cadena != null){
					this.saveInFile(cadena, bw);
				}
			}
			
			// Cierro buffer
			bw.flush();
			bw.close();
			bw = null;
			
			// Abro archivo plano
			Runtime.getRuntime().exec("notepad.exe " + fileName); 
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			if (bw != null){
				try {
					bw.flush();
					bw.close();
				} 
				catch (Exception e2) {
				}
			}
		}
		
	}
	
	/***
	 * Crea archivo fisico. Retorna nombre del archivo creado.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/08/2013
	 * @see
	 * @return
	 */
	private String createFile() {	

		String fileName = null;
		
		try {
			
			String filePath = System.getProperty("user.home") + "/Desktop";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
			
			fileName = filePath + "\\DGI_2181_" + df.format(new Timestamp(System.currentTimeMillis())) + ".txt";
			
			File file = new File (fileName);
			
			file.createNewFile();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return fileName;
	}	
	

	/***
	 * Guarda linea en archivo.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 20/08/2013
	 * @see
	 * @param cadena
	 * @param bf
	 * @throws Exception
	 */
	private void saveInFile(String cadena,BufferedWriter bf) throws Exception { 		
		
		try {
			if(cadena != null && bf != null){
				String ln = System.getProperty("line.separator");
				bf.append(cadena + ln);										
			}

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} 
	}

}
