/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.FactLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MBPartner;
import org.compiere.model.MCountry;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MSequence;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.util.Converter;

/**
 * @author Nicolas
 *
 */
public class MTRCrt extends X_UY_TR_Crt implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7075464050615674317L;

	/**
	 * @param ctx
	 * @param UY_TR_Crt_ID
	 * @param trxName
	 */
	public MTRCrt(Properties ctx, int UY_TR_Crt_ID, String trxName) {
		super(ctx, UY_TR_Crt_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRCrt(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		this.loadData();
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	@Override
	protected boolean beforeDelete() {
		
		// No permito eliminar un registro en estado aplicado.
		if (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Applied) || this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed) 
				|| this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_InProgress)){
			log.saveError(null, "No es posible borrar un CRT en estado diferente a Borrador");
			return false;
		}
		
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		/*
		// Si la empresa del documento es distinta a la empresa del login, me aseguro que tome el c_doctype_id correspondiente a la empresa del documento.
		if ((newRecord) || (is_ValueChanged(COLUMNNAME_AD_Client_ID))){
			if (Env.getAD_Client_ID(getCtx()) != this.getAD_Client_ID()){
				int cDocTypeID = DB.getSQLValueEx(null, " select c_doctype_id from c_doctype where ad_client_id=" + this.getAD_Client_ID() +
														" and value='printcrt'");
				if (cDocTypeID > 0) this.setC_DocType_ID(cDocTypeID);
			}
		}
		*/
		
		//seteo el campo de moneda literal
		if(is_ValueChanged("TotalAmt") || is_ValueChanged("C_Currency_ID")){
			
			Converter conv = new Converter();
			
			MCurrency cur = new MCurrency(getCtx(),this.getC_Currency_ID(),get_TrxName());
			
			String literal = conv.getStringOfBigDecimal(this.getTotalAmt()) + " " + cur.getDescription();
					
			this.setLiteralNumber(literal);			
		}	
		
		//al modificar el campo del destinatario, copio contenido
		//a los campos de consignatario y notificar a
		if(!newRecord){
			if(is_ValueChanged("Destinatario")){

				this.setConsignatario(this.getDestinatario());
				this.setNotificar(this.getDestinatario());			
			}
		}
		
		this.setamt5(this.getamt1().add(this.getamt3()).add(this.getamt7()).add(this.getamt9()));
		
		this.setamt6(this.getamt2().add(this.getamt4()).add(this.getamt8()).add(this.getamt10()));
		
		if(is_ValueChanged("c_currency2_id") || is_ValueChanged("amt5") || is_ValueChanged("amt6")){
			
			BigDecimal amt = (this.getamt5().add(this.getamt6()));
			
			if(amt.compareTo(Env.ZERO)>0){
				
				Converter conv = new Converter();
				
				MCurrency cur = new MCurrency(getCtx(),this.getC_Currency2_ID(),get_TrxName());
				
				String literal = conv.getStringOfBigDecimal(amt);
						
				this.setFleteLiteral("Son " + cur.getDescription() + " " + literal);			
			}			
		}
		
		return true;
	}

	private void loadData() {
		
		MTRTrip trip = new MTRTrip(getCtx(),this.getUY_TR_Trip_ID(),get_TrxName());
		MInvoice inv = null;
		MTRWay way = null;
		String sql = "", ubicacion = "", value = "";
				
		if(this.getC_Invoice_ID()>0) inv = (MInvoice)this.getC_Invoice(); //obtengo proforma
		if(trip.getUY_TR_Way_ID()>0) way = (MTRWay)trip.getUY_TR_Way(); //obtengo el trayecto		
				
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		MTRConfigCrt configCrt = MTRConfigCrt.forConfig(getCtx(), param.get_ID(), get_TrxName());
		
		if (configCrt==null) throw new AdempiereException("No se obtuvieron parametros de CRT para la empresa actual");
		
		MTRConfigRound round = MTRConfigRound.forConfig(getCtx(), param.get_ID(), get_TrxName());
		
		if (round==null) throw new AdempiereException("No se obtuvieron parametros de precision decimal para la empresa actual");
					
		//se cargan datos de los clientes
		this.loadPartners(trip);
		
		//se cargan campos de importe, bultos, volumen, peso bruto y neto
		//si tengo proforma
		if(inv!=null){
			
			this.setQtyPackage(inv.getuy_cantbultos());
			this.setWeight((BigDecimal)inv.get_Value("Weight"));
			this.setWeight2((BigDecimal)inv.get_Value("Weight2"));
			this.setVolume(trip.getVolume());
			this.setTotalAmt((BigDecimal)inv.get_Value("AmtOriginal"));
			this.setC_Currency_ID(inv.get_ValueAsInt("C_Currency2_ID"));
			
		} else {
			
			this.setQtyPackage(trip.getQtyPackage());
			this.setWeight(trip.getWeight());
			this.setWeight2(trip.getWeight2());
			this.setVolume(trip.getVolume());
			this.setTotalAmt(trip.getProductAmt());
			this.setC_Currency_ID(trip.getC_Currency_ID());			
		}
		
		//se carga el campo 3 de datos del porteador y el campo 22 de declaraciones y observaciones
		if(trip.isRepresentation()){
			
			
			
			
			
			
		} else {
			
			//seteo campo 3
			value = "";
			MOrg org = MOrg.get(getCtx(), this.getAD_Org_ID());
			MOrgInfo info = MOrgInfo.get(getCtx(), org.get_ID(), get_TrxName());

			this.setPorteador2(org.getName().toUpperCase());
			
			if(info!=null && info.get_ID()>0){

				MLocation loc = (MLocation)info.getC_Location();
				
				if(loc!=null && loc.get_ID()>0){
					
					value += org.getName().toUpperCase() + "\n";
					
					if(loc.getAddress1()!=null && !loc.getAddress1().equalsIgnoreCase("")) value += loc.getAddress1().toUpperCase() + "\n";
					
					MCountry country = (MCountry)loc.getC_Country();
					
					if(country.get_ID()==139){
						
						if(loc.getUY_Departamentos_ID()>0){
							
							MDepartamentos dep = (MDepartamentos)loc.getUY_Departamentos();
							MLocalidades localidad = new MLocalidades(getCtx(),loc.getUY_Localidades_ID(),get_TrxName());
														
							value += localidad.getName() + ", " + dep.getName().toUpperCase() + ", " + country.getName().toUpperCase() + "\n";						
							
						}						
						
					} else {
						
						if(loc.getUY_Departamentos_ID()>0){
							
							MDepartamentos dep = (MDepartamentos)loc.getUY_Departamentos();
							
							value += dep.getName().toUpperCase() + ", " + country.getName().toUpperCase() + "\n";						
							
						}						
					}				
					
					value += info.getDUNS();
					
					this.setPorteador(value);					
					
				} else throw new AdempiereException("No se pudo obtener localizacion para la organizacion actual");	

			} else throw new AdempiereException("No se pudo obtener informacion de la organizacion actual");		
			
			//seteo campo 22
			sql = "select observaciones" +
			      " from uy_tr_configcrt" +
				  " where ad_client_id = " + this.getAD_Client_ID() +
				  " and ad_org_id = " + this.getAD_Org_ID();
			value = DB.getSQLValueStringEx(get_TrxName(), sql);

			if(configCrt.isPesoNeto()){ //si se debe incluir el peso neto

				//Peso neto a texto con formato . para miles y , para decimales
				BigDecimal weight2aux = this.getWeight2().setScale(round.getWeight2(), RoundingMode.HALF_UP);
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(round.getWeight2());
				df.setMinimumFractionDigits(round.getWeight2());
				df.setGroupingUsed(true);
				String result = df.format(weight2aux);

				if (result != null){
					result = result.replace(".", ";");
					result = result.replace(",", ".");
					result = result.replace(";", ",");
				}

				if(value!=null && !value.equalsIgnoreCase("")){

					this.setobservaciones(value + "\n" + "Peso Líquido: " + result + " KG");		

				} else this.setobservaciones("Peso Líquido: " + result + " KG");	

			} else { //si no se debe incluir el peso neto
				
				if(value!=null && !value.equalsIgnoreCase("")) this.setobservaciones(value);
				
			}	
			
		}		
		
		sql =  "select case when pais <> 336 then (coalesce(ciudad,null) || '/' || coalesce(estado,null) || '/' || coalesce (nombrePais,null))" +
	              " else (coalesce(ciudad,null) || '/' || coalesce (nombrePais,null)) end as ubicacion" +
	              " from" +
	              " (select *,case when pais <> 336 then (select d.name from uy_departamentos d inner join uy_localidades l on d.uy_departamentos_id = l.uy_departamentos_id where lower(ciudad) = lower(l.name) and d.c_country_id = pais)" +
	              " else '' end as estado" +
	              " from" +
	              " (select c.name as ciudad,c.c_country_id as pais,y.name as nombrePais" +
	              " from uy_tr_trip tr" + 
	              " inner join uy_ciudad c on tr.uy_ciudad_id = c.uy_ciudad_id" +
	              " inner join c_country y on c.c_country_id = y.c_country_id" +
	              " where tr.uy_tr_trip_id = " + trip.get_ID() + ") as x) as y";
		ubicacion = DB.getSQLValueStringEx(get_TrxName(), sql);
				
		sql = "select date_part('day','" + this.getDateTrx() + "'::timestamp)";
		String day = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		sql = "select date_part('month','" + this.getDateTrx() + "'::timestamp)";
		String month = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		sql = "select date_part('year','" + this.getDateTrx() + "'::timestamp)";
		String year = DB.getSQLValueStringEx(get_TrxName(), sql);
		
		String date = day + "/" + month + "/" + year;
		
		this.setLocationComment(ubicacion + " " + date);
				
		//seteo lugar, pais y plazo de entrega
		sql = "select case when pais <> 336 then (coalesce(ciudad,null) || '/' || coalesce(estado,null) || '/' || coalesce (nombrePais,null))" +
              " else (coalesce(ciudad,null) || '/' || coalesce (nombrePais,null)) end as ubicacion" +
              " from" +
              " (select *,case when pais <> 336 then (select d.name from uy_departamentos d inner join uy_localidades l on d.uy_departamentos_id = l.uy_departamentos_id where lower(ciudad) = lower(l.name) and d.c_country_id = pais)" +
              " else '' end as estado" +
              " from" +
              " (select c.name as ciudad,c.c_country_id as pais,y.name as nombrePais" +
              " from uy_tr_trip tr" + 
              " inner join uy_ciudad c on tr.uy_ciudad_id_1::int = c.uy_ciudad_id" +
              " inner join c_country y on c.c_country_id = y.c_country_id" +
              " where tr.uy_tr_trip_id = " + trip.get_ID() + ") as x) as y";
		ubicacion = DB.getSQLValueStringEx(get_TrxName(), sql);		
		
		this.setDeliveryRule(ubicacion);
		
		//se carga campo de lugar de emision
		String emision = "";
		
		int warehouseID = Env.getContextAsInt(getCtx(), 0, "M_Warehouse_ID"); //obtengo Id de almacen actual
				
		if(warehouseID > 0){
			
			MWarehouse w = new MWarehouse(getCtx(),warehouseID,get_TrxName()); //instancio almacen actual
			
			MLocation loc = new MLocation(getCtx(),w.getC_Location_ID(),get_TrxName()); //obtengo ubicacion actual
			
			if(loc!=null && loc.get_ID()>0){
				
				this.setC_Location_ID(loc.get_ID()); //guardo la ubicacion actual, para luego poder obtener pais de emision
				
				if(loc.getUY_Departamentos_ID() > 0){
					
					MDepartamentos depto = new MDepartamentos(getCtx(),loc.getUY_Departamentos_ID(),get_TrxName());
					
					emision += depto.getName();				
					
				}
				
				if(loc.getC_Country_ID() > 0){
					
					MCountry country = new MCountry(getCtx(),loc.getC_Country_ID(),get_TrxName());
					
					if(!emision.equalsIgnoreCase("")){
						
						emision += "/" + country.getName();					
						
					} else emision += country.getName();				
					
				}					
				
			} else throw new AdempiereException("No se pudo obtener ubicacion para el almacen actual");				
			
		} else throw new AdempiereException("No se pudo obtener almacen actual");	
		
		//si tengo trayecto, seteo campo 5 desde el campo predefinido del trayecto
		if(way!=null && way.get_ID()>0){
			
			if(way.getLocatorValue()!=null && !way.getLocatorValue().equalsIgnoreCase("")){
				
				this.setLocatorValue(way.getLocatorValue());				
				
			} else if(!emision.equalsIgnoreCase("")) this.setLocatorValue(emision);	
			
		} else if(!emision.equalsIgnoreCase("")) this.setLocatorValue(emision);		
		
		// Importe a texto con formato . para miles y , para decimales
		BigDecimal impaux = this.getTotalAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(round.getProductAmt());
		df.setMinimumFractionDigits(round.getProductAmt());
		df.setGroupingUsed(true);
		String result = df.format(impaux);
		
		if (result != null){
			result = result.replace(".", ";");
			result = result.replace(",", ".");
			result = result.replace(";", ",");
		}
		
		this.setImporte(result);	
		
		/*if(inv!=null && inv.getincoterms()!=null){
			this.setImporte(inv.getincoterms() + " " + result);			
		} else this.setImporte(result);*/
		
		//se cargan los importes
		if(inv!=null){

			BigDecimal nationalAmt = Env.ZERO;
			BigDecimal interAmt = Env.ZERO;
			BigDecimal subTotalAmt = inv.getTotalLines(); //obtengo el subtotal de la factura
			
			this.setC_Currency2_ID(inv.getC_Currency_ID()); //seteo moneda de flete

			if(trip.getReceiptMode().equalsIgnoreCase("AMBOS")){

				sql = "select coalesce(sum(nationalamt),0)" +
						" from c_invoiceline" +
						" where c_invoice_id = " + inv.get_ID();
				nationalAmt = DB.getSQLValueBDEx(get_TrxName(), sql);

				sql = "select coalesce(sum(internationalamt),0)" +
						" from c_invoiceline" +
						" where c_invoice_id = " + inv.get_ID();
				interAmt = DB.getSQLValueBDEx(get_TrxName(), sql);

				//EL TERRITORIO EXTRANJERO SIEMPRE ES DE ORIGEN A FRONTERA
				this.setamt1(interAmt.setScale(round.getAmount(), RoundingMode.HALF_UP)); //seteo campo de importe de flete del remitente, con el importe del terr. internacional
				this.setamt2(nationalAmt.setScale(round.getAmount(), RoundingMode.HALF_UP)); //seteo campo de importe de flete del destinatario, con el importe del terr. nacional			

			} else if (trip.getReceiptMode().equalsIgnoreCase("ORIGEN")){

				this.setamt1(subTotalAmt.setScale(round.getAmount(), RoundingMode.HALF_UP)); //seteo campo de importe de flete del remitente, con el importe subtotal de la factura			

			} else if (trip.getReceiptMode().equalsIgnoreCase("DESTINO")){

				this.setamt2(subTotalAmt.setScale(round.getAmount(), RoundingMode.HALF_UP)); //seteo campo de importe de flete del destinatario, con el importe subtotal de la factura		

			}		
			
			//se carga monto de flete externo
			//obtengo suma de importes para terr. internacional
			sql = "select coalesce(sum(internationalamt),0)" +
					" from c_invoiceline" +
					" where c_invoice_id = " + inv.get_ID();
			BigDecimal amt = DB.getSQLValueBD(get_TrxName(), sql);

			if(amt.compareTo(Env.ZERO)>0){

				// Importe a texto con formato . para miles y , para decimales
				impaux = amt.setScale(round.getAmount(), RoundingMode.HALF_UP);
				df = new DecimalFormat();
				df.setMaximumFractionDigits(round.getAmount());
				df.setMinimumFractionDigits(round.getAmount());
				df.setGroupingUsed(true);
				result = df.format(impaux);

				if (result != null){
					result = result.replace(".", ";");
					result = result.replace(",", ".");
					result = result.replace(";", ",");
				}

				MCurrency cur = new MCurrency(getCtx(),inv.getC_Currency_ID(),get_TrxName()); //instancio moneda del cabezal

				if(configCrt.isValueIncluded()){

					value = cur.getCurSymbol() + " " + result + "   VALOR INCLUIDO EN FLETE TOTAL";					

				} else value = cur.getCurSymbol() + " " + result;				

				this.setValorFleteExt(value);				

			} else {

				if(configCrt.isValueIncluded()) this.setValorFleteExt("VALOR INCLUIDO EN FLETE TOTAL");				
			}

		} else {

			if(configCrt.isValueIncluded()) this.setValorFleteExt("VALOR INCLUIDO EN FLETE TOTAL");

		}
		
		//seteo cantidad y clases de bultos
		if(trip.getProductType()!=null && !trip.getProductType().equalsIgnoreCase("")){
			
			if(trip.getDescription()!=null && !trip.getDescription().equalsIgnoreCase("")){
				
				this.setDescription(trip.getQtyPackage().setScale(0, RoundingMode.HALF_UP) + " " + trip.getDescription() + "\n" + trip.getProductType());			
				
			} else this.setDescription(trip.getQtyPackage().setScale(0, RoundingMode.HALF_UP) + "\n" + trip.getProductType());			
			
		} else {
			
			if(trip.getDescription()!=null && !trip.getDescription().equalsIgnoreCase("")){
				
				this.setDescription(trip.getQtyPackage().setScale(0, RoundingMode.HALF_UP) + " " + trip.getDescription());
				
			} else this.setDescription(trip.getQtyPackage().setScale(0, RoundingMode.HALF_UP).toString());			
				
		}

		// Si no se usa secuencia manual
		if(!this.isOldSequence()){
			
			// Obtengo de manera automatica el numero para el CRT 
			this.setCRTNumber();  
		}
		
	}
	
	/***
	 * Carga datos de clientes en el CRT.
	 * OpenUp #
	 * @author Nicolas Sarlabos - 16/04/2015
	 * @see
	 * @return
	 */
	public void loadPartners(MTRTrip trip) {
		
		String sql = "", datosCliente = "", name2 = "", dirCarga = "", ubicacion = "", docType = "", nroType = "", tributo = "";
		MBPartner partnerFrom = new MBPartner(getCtx(),trip.getC_BPartner_ID_To(),get_TrxName()); //instancio cliente ORIGEN
		MBPartner partnerTo = new MBPartner(getCtx(),trip.getC_BPartner_ID_From(),get_TrxName()); //instancio cliente DESTINO
		
		//se carga el campo de datos del remitente
		name2 = partnerFrom.getName2();
		this.setRemitente2(name2);

		sql = "select coalesce(loc.address1,null)" +
				" from c_bpartner_location loc" + 
				" inner join uy_tr_trip t on loc.c_bpartner_location_id = t.c_bpartner_location_id_2" +	
				" where t.uy_tr_trip_id = " + trip.get_ID();
		dirCarga = DB.getSQLValueStringEx(get_TrxName(), sql);

		sql = "select case when x.c_country_id = 336 then (select x.depto || '/' || x.pais)" +
				" when x.c_country_id <> 336 then (select x.loc || '/' || x.depto || '/' || x.pais) end as descarga" +
				" from" +
				" (select c.c_country_id, c.name as pais, dep.name as depto, loc.name as loc" +
				" from uy_tr_trip t" +
				" left join c_bpartner_location l on t.c_bpartner_location_id_2 = l.c_bpartner_location_id" +
				" left join c_country c on l.c_country_id = c.c_country_id" +
				" left join uy_departamentos dep on l.uy_departamentos_id = dep.uy_departamentos_id" +
				" left join uy_localidades loc on l.uy_localidades_id = loc.uy_localidades_id" +
				" where t.uy_tr_trip_id = " + trip.get_ID() + ") as x";
		ubicacion = DB.getSQLValueStringEx(get_TrxName(), sql);

		if(name2!=null && !name2.equalsIgnoreCase("")) datosCliente += name2;

		if(dirCarga!=null && !dirCarga.equalsIgnoreCase("")){

			if(!datosCliente.equalsIgnoreCase("")){

				datosCliente += "\n" + dirCarga;

			} else datosCliente += dirCarga;			
		}

		if(ubicacion!=null && !ubicacion.equalsIgnoreCase("")){

			if(!datosCliente.equalsIgnoreCase("")){

				datosCliente += "\n" + ubicacion.trim();

			} else datosCliente += ubicacion.trim();			
		}

		this.setRemitente(datosCliente);
		
		//se carga el campo de datos del destinatario, consignatario y notificar a...
		name2 = partnerTo.getName2();
		this.setDestinatario2(name2);
		
		datosCliente = "";

		sql = "select coalesce(loc.address1,null)" +
				" from c_bpartner_location loc" + 
				" inner join uy_tr_trip t on loc.c_bpartner_location_id = t.c_bpartner_location_id" +	
				" where t.uy_tr_trip_id = " + trip.get_ID();
		dirCarga = DB.getSQLValueStringEx(get_TrxName(), sql);

		sql = "select case when x.c_country_id = 336 then (select x.depto || '/' || x.pais)" +
				" when x.c_country_id <> 336 then (select x.loc || '/' || x.depto || '/' || x.pais) end as descarga" +
				" from" +
				" (select c.c_country_id, c.name as pais, dep.name as depto, loc.name as loc" +
				" from uy_tr_trip t" +
				" left join c_bpartner_location l on t.c_bpartner_location_id = l.c_bpartner_location_id" +
				" left join c_country c on l.c_country_id = c.c_country_id" +
				" left join uy_departamentos dep on l.uy_departamentos_id = dep.uy_departamentos_id" +
				" left join uy_localidades loc on l.uy_localidades_id = loc.uy_localidades_id" +
				" where t.uy_tr_trip_id = " + trip.get_ID() + ") as x";
		ubicacion = DB.getSQLValueStringEx(get_TrxName(), sql);

		docType = partnerTo.getDocumentType(); //obtengo tipo de identificacion tributaria
		nroType = partnerTo.getDUNS(); //obtengo numero de identificacion tributaria	 

		if(docType != null && nroType != null) tributo = docType + ": " + nroType;

		if(name2!=null && !name2.equalsIgnoreCase("")) datosCliente += name2;

		if(dirCarga!=null && !dirCarga.equalsIgnoreCase("")){

			if(!datosCliente.equalsIgnoreCase("")){

				datosCliente += "\n" + dirCarga;

			} else datosCliente += dirCarga;			
		}

		if(ubicacion!=null && !ubicacion.equalsIgnoreCase("")){

			if(!datosCliente.equalsIgnoreCase("")){

				datosCliente += "\n" + ubicacion.trim() + " " + tributo;

			} else datosCliente += ubicacion.trim() + " " + tributo;			
		}

		this.setDestinatario(datosCliente);
		this.setConsignatario(datosCliente);
		this.setNotificar(datosCliente);
		
		this.saveEx();

	}
	
	
	/***
	 * Obtiene y retorna un CRT segun el numero de documento recibido
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 05/08/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRCrt forDocumentNo(Properties ctx, String docNo, String trxName){
		
		String whereClause = X_UY_TR_Crt.COLUMNNAME_Numero + "='" + docNo + "'";
		
		MTRCrt crt = new Query(ctx, I_UY_TR_Crt.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return crt;
	}
	
	/***
	 * Obtiene y retorna un CRT segun el ID de proforma recibido
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 29/12/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRCrt forInvoice(Properties ctx, int invID, String trxName){
		
		String whereClause = X_UY_TR_Crt.COLUMNNAME_C_Invoice_ID + "=" + invID;
		
		MTRCrt crt = new Query(ctx, I_UY_TR_Crt.Table_Name, whereClause, trxName)
		.first();
				
		return crt;
	}
	
	/***
	 * Obtiene y retorna un CRT segun el ID de expediente recibido
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 14/04/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRCrt forTrip(Properties ctx, int tripID, String trxName){
		
		String whereClause = X_UY_TR_Crt.COLUMNNAME_UY_TR_Trip_ID + "=" + tripID;
		
		MTRCrt crt = new Query(ctx, I_UY_TR_Crt.Table_Name, whereClause, trxName)
		.first();
				
		return crt;
	}
	
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
	
		
		// Me aseguro que no se complete un CRT sin Numero
		if ((this.getNumero() == null) || (this.getNumero().equalsIgnoreCase(""))){
			this.processMsg = "Debe indicar Numero de CRT antes de Completar.";
			return DocAction.STATUS_Invalid;
		}
		
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;	
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		
		// Before reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (processMsg != null)
			return false;

		// Elimino asientos contables
		FactLine.deleteFact(I_C_Invoice.Table_ID, this.get_ID(), get_TrxName());

		// After reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***
	 * Obtiene y setea numero de CRT segun parametrizacion del modulo de transporte.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 10/01/2015
	 * @see
	 * @return
	 */
	private String setCRTNumber(){
		
		String message = null, suitNumber = "";
		
		try {

			// Me aseguro de tener expediente
			if (this.getUY_TR_Trip_ID() <= 0){
				message = "No es posible asignar Numero a este CRT: no tiene un expediente asociado.";
				return message;
			}
			
			MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
			
			if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
			
			MTRConfigCrt configCrt = MTRConfigCrt.forConfig(getCtx(), param.get_ID(), get_TrxName());
			
			if (configCrt==null) throw new AdempiereException("No se obtuvieron parametros de CRT para la empresa actual");
			
			// Obtengo paises de ciudad origen y destino del expediente.
			// En caso de utilizar el concepto de trayecto en el expediente, las ciudades origen y destino a considerar 
			// serán las del trayecto.
			MCountry countryFrom = null, countryTo = null;
			MTRTrip trip = (MTRTrip)this.getUY_TR_Trip();
			if (trip.getUY_TR_Way_ID() > 0){
				MTRWay way = (MTRWay)trip.getUY_TR_Way();
				countryFrom = (MCountry)((MCiudad)way.getUY_Ciudad()).getC_Country();
				countryTo = (MCountry)(new MCiudad(getCtx(), way.getUY_Ciudad_ID_1(), null).getC_Country());
			}
			else{
				countryFrom = (MCountry)((MCiudad)trip.getUY_Ciudad()).getC_Country();
				countryTo = (MCountry)(new MCiudad(getCtx(), trip.getUY_Ciudad_ID_1(), null).getC_Country());
			}
			
			// Codigo de Pais Origen
			String countryCode = countryFrom.getCountryCode();
			if (countryCode == null){
				message = "No se pudo obtener codigo del pais de origen.";
				return message;
			}
			
			if(configCrt.isPermit() || (!configCrt.isPermit() && !trip.getTripType().equalsIgnoreCase(X_UY_TR_Trip.TRIPTYPE_EXPORTACION))){
				// Obtengo codigo de idoneidad para los dos paises obtenidos 
				String sql = " select coalesce(code,null) " +
						" from uy_tr_suitability " +
						" where ((c_country_id = " + countryFrom.get_ID() + 
						" and c_country_id_1 = " +	countryTo.get_ID() + ") " +
						" or (c_country_id = " + countryTo.get_ID() + 
						" and c_country_id_1 = " + countryFrom.get_ID() + "))" +
						" and ad_client_id = " + this.getAD_Client_ID();

				suitNumber = DB.getSQLValueStringEx(null, sql);

				if (suitNumber == null){
					message = "No se pudo obtener codigo de idoneidad para los paises de origen y destino";
					return message;
				}
				
			} 
			
			// Segun tipo de expediente se obtiene el secuencial
			String secNumber = " ";
			
			// Expediente de Importacion
			if (trip.getTripType().equalsIgnoreCase(X_UY_TR_Trip.TRIPTYPE_IMPORTACION)){
				
				MSequence sequence = MSequence.get(getCtx(), this.getAD_Client_ID(), "UY_TR_CRT_Imp", get_TrxName());
				int seqID = MSequence.getNextID_ForName(this.getAD_Client_ID(), "UY_TR_CRT_Imp", get_TrxName());
				
				if(sequence.getPrefix() != null && !sequence.getPrefix().equalsIgnoreCase("")){
					secNumber = sequence.getPrefix() + seqID;
				} 
				else{
					secNumber = String.valueOf(seqID).trim();						
				}
				
			} 
			else if (trip.getTripType().equalsIgnoreCase(X_UY_TR_Trip.TRIPTYPE_EXPORTACION)){

				MSequence sequence = MSequence.get(getCtx(), this.getAD_Client_ID(), "UY_TR_CRT_Exp", get_TrxName());
				int seqID = MSequence.getNextID_ForName(this.getAD_Client_ID(), "UY_TR_CRT_Exp", get_TrxName());
				
				if(sequence.getPrefix() != null && !sequence.getPrefix().equalsIgnoreCase("")){
					secNumber = sequence.getPrefix() + seqID;
				} 
				else{
					secNumber = String.valueOf(seqID).trim();						
				}
				
				
			} 
			else if (trip.getTripType().equalsIgnoreCase(X_UY_TR_Trip.TRIPTYPE_NACIONAL)){

				MSequence sequence = MSequence.get(getCtx(), this.getAD_Client_ID(), "UY_TR_CRT", get_TrxName());
				int seqID = MSequence.getNextID_ForName(this.getAD_Client_ID(), "UY_TR_CRT", get_TrxName());
				
				if(sequence.getPrefix() != null && !sequence.getPrefix().equalsIgnoreCase("")){
					secNumber = sequence.getPrefix() + seqID;
				} 
				else{
					secNumber = String.valueOf(seqID).trim();						
				}
				
			}
			
			if (secNumber == null){
				message = "No se pudo obtener numero de Secuencia para CRT.";
				return message;
			}

			// Finalmente seteo numero del CRT
			if(suitNumber.equalsIgnoreCase("")){
			
				this.setNumero(countryCode.trim() + secNumber.trim());
				
			} else this.setNumero(countryCode.trim() + suitNumber.trim() + secNumber.trim());			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return message;
	}

	/***
	 * Actualiza cantidades e importes de toda linea de orden de transporte asociada a este crt.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 19/01/2015
	 * @see
	 */
	public void refreshTransOrders() {
		try {
			
			String action = " update uy_tr_transorderline set qtypackage=round(crt.qtypackage, 2), " +
							" weight =round(crt.weight, 3), " +
							" weight2 =round(crt.weight2, 3), " +
							" volume =round(crt.volume, 3), " +
							" productamt =round(crt.totalamt, 3), " +
							" amount =round(crt.amt1 + crt.amt2, 2) " +
							" from uy_tr_crt crt " +
							" where uy_tr_transorderline.uy_tr_crt_id = crt.uy_tr_crt_id " +
							" and uy_tr_transorderline.uy_tr_crt_id =" + this.get_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
			
			
			// Actualizo todo movimiento asociado a este CRT
			action = " update uy_tr_loadmonitorline set qtypackage=round(crt.qtypackage, 2), " +
					 " weight =round(crt.weight, 3), " +
					 " weight2 =round(crt.weight2, 3), " +
					 " volume =round(crt.volume, 3), " +
					 " productamt =round(crt.totalamt, 3), " +
					 " amount =round(crt.amt1 + crt.amt2, 2) " +
					 " from uy_tr_crt crt " +
					 " where uy_tr_loadmonitorline.uy_tr_crt_id = crt.uy_tr_crt_id " +
					 " and uy_tr_loadmonitorline.uy_tr_crt_id =" + this.get_ID();

			DB.executeUpdateEx(action, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/***
	 * Actualiza cantidades e importes en deposito asociados a este crt.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 19/01/2015
	 * @see
	 */
	public void refreshTRStock() {
		try {
			
			String action = " update uy_tr_stock set qtypackage=round(crt.qtypackage, 2), " +
							" weight =round(crt.weight, 3), " +
							" weight2 =round(crt.weight2, 3), " +
							" volume =round(crt.volume, 3), " +
							" productamt =round(crt.totalamt, 3), " +
							" amount =round(crt.amt1 + crt.amt2, 2) " +
							" from uy_tr_crt crt " +
							" where uy_tr_stock.uy_tr_crt_id = crt.uy_tr_crt_id " +
							" and uy_tr_stock.uy_tr_crt_id =" + this.get_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
	/***
	 * Metodo que devuelve true si el CRT actual tiene ordenes de transporte.
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 12/04/2015
	 * @see
	 */
	/*public boolean hasMoves(){
		
		String sql = "select count(o.uy_tr_transorder_id)" +
		             " from uy_tr_transorder o" +
				     " inner join uy_tr_transorderline l on o.uy_tr_transorder_id = l.uy_tr_transorder_id" +
				     " where o.docstatus = 'AY' and l.uy_tr_crt_id = " + this.get_ID();
		int count = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(count > 0) return true;		
		
		return false;		
	}*/
	
}
