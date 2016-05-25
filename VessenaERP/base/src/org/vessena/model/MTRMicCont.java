/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.model.MBPartner;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCountry;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRMicCont extends X_UY_TR_MicCont {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3405525245410732621L;

	/**
	 * @param ctx
	 * @param UY_TR_MicCont_ID
	 * @param trxName
	 */
	public MTRMicCont(Properties ctx, int UY_TR_MicCont_ID, String trxName) {
		super(ctx, UY_TR_MicCont_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMicCont(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MTRMic hdr = new MTRMic(getCtx(),this.getUY_TR_Mic_ID(),get_TrxName());
		
		if(newRecord) {
			
			this.setSheetNumber(); //seteo numero de hoja
			
		} else {

			if (!MTRMic.isEnviandoAduana()) {
				
				if(hdr.getMicStatus().equalsIgnoreCase("ENVIADO") || hdr.getMicStatus().equalsIgnoreCase("MODIFICAR")){
					
					DB.executeUpdateEx("update uy_tr_mic set micstatus = 'MODIFICAR' where uy_tr_mic_id = " + hdr.get_ID(), get_TrxName());
						
				}
				
			}
			
		}
		
		this.setTotals(); //seteo campos totalizadores			
		
		return true;
	}
	
	@Override
	protected boolean beforeDelete() {
		
		/*MTRMic hdr = (MTRMic)this.getUY_TR_Mic();

		if(this.getUY_TR_Crt_ID() > 0){

			if(this.getCrtStatus1()!=null){

				
				if(this.getCrtStatus1().equalsIgnoreCase("ENBAJA") || this.getCrtStatus1().equalsIgnoreCase("ENMODIFICACION")
						|| this.getCrtStatus1().equalsIgnoreCase("VINCULADO")) throw new AdempiereException ("Imposible borrar la continuacion, primero debe dar de baja los conocimientos en el MIC/DTA " + hdr.getNumero());

			}		

		}
		
		if(this.getUY_TR_Crt_ID_1() > 0){

			if(this.getCrtStatus2()!=null){

				if(this.getCrtStatus2().equalsIgnoreCase("ENBAJA") || this.getCrtStatus2().equalsIgnoreCase("ENMODIFICACION")
						|| this.getCrtStatus2().equalsIgnoreCase("VINCULADO")) throw new AdempiereException ("Imposible borrar la continuacion, primero debe dar de baja los conocimientos en el MIC/DTA " + hdr.getNumero());

			}		

		}*/

		return true;
	}

	/***
	 * Setea los campos totalizadores de cantidades y pesos.
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 08/04/2014
	 */
	public void setTotals() {
	
		this.setSubtotalBultos(this.getQtyPackage().add(this.getQtyPackage2())); //seteo subtotal de bultos
		this.setSubtotalPeso(this.getpesoBruto().add(this.getpesoBruto2())); //seteo subtotal de peso bruto
				
		//si esta es la hoja 2
		if(this.getsheet()==2){

			MTRMic mic = new MTRMic(getCtx(),this.getUY_TR_Mic_ID(),get_TrxName()); //instancio el cabezal

			this.setTotalAntBultos(mic.getQtyPackage()); //seteo bultos hoja 1
			this.setTotalAntPeso(mic.getpesoBruto()); //seteo peso bruto hoja 1

			this.setAcumuladoBultos(this.getSubtotalBultos().add(this.getTotalAntBultos())); //seteo acumulado bultos
			this.setAcumuladoPeso(this.getSubtotalPeso().add(this.getTotalAntPeso())); //seteo acumulado peso

		} else if(this.getsheet()>2){ //si es la hoja 3 o mayor

			MTRMicCont cont = this.getMicCont(this.getsheet()-1); //obtengo la hoja anterior a la actual

			if(cont!=null && cont.get_ID()>0){

				this.setTotalAntBultos(cont.getAcumuladoBultos()); //seteo bultos acumulado hoja anterior
				this.setTotalAntPeso(cont.getAcumuladoPeso()); //seteo peso acumulado hoja anterior

				this.setAcumuladoBultos(this.getSubtotalBultos().add(this.getTotalAntBultos())); //seteo acumulado bultos
				this.setAcumuladoPeso(this.getSubtotalPeso().add(this.getTotalAntPeso())); //seteo acumulado peso	

			} else throw new AdempiereException("No se pudo obtener la hoja continuacion N° " + (this.getsheet()-1));	

		}	
		
	}
	
	/***
	 * Obtiene y retorna un modelo de MIC continuacion para el documento actual, recibiendo el numero de hoja por parametro.
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 08/04/2014
	 * @see
	 * @return
	 */
	public MTRMicCont getMicCont(int sheetNro){

		String whereClause = X_UY_TR_MicCont.COLUMNNAME_UY_TR_Mic_ID + "=" + this.getUY_TR_Mic_ID() + " AND " + X_UY_TR_MicCont.COLUMNNAME_sheet + "=" + sheetNro;

		MTRMicCont cont = new Query(getCtx(), I_UY_TR_MicCont.Table_Name, whereClause, get_TrxName()).first();

		return cont;
	}
	
	/***
	 * Obtiene y retorna un modelo de MIC continuacion, recibiendo por parametro la linea de CRT en MIC.
	 * OpenUp Ltda. Issue #3093
	 * @author Nicolas Sarlabos - 28/11/2014
	 * @see
	 * @return
	 */
	public static MTRMicCont forMicLine(MTRMicLine line){

		String whereClause = X_UY_TR_MicCont.COLUMNNAME_UY_TR_Mic_ID + "=" + line.getUY_TR_Mic_ID() + " AND (" + X_UY_TR_MicCont.COLUMNNAME_UY_TR_Crt_ID + "=" + line.getUY_TR_Crt_ID() + 
				" OR " + X_UY_TR_MicCont.COLUMNNAME_UY_TR_Crt_ID_1 + "=" + line.getUY_TR_Crt_ID() + ")";

		MTRMicCont cont = new Query(Env.getCtx(), I_UY_TR_MicCont.Table_Name, whereClause, null).first();

		return cont;
	}

	/***
	 * Obtiene y setea el numero de hoja.
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 08/04/2014
	 */
	private void setSheetNumber() {
		
		int sheet = 0;
		
		String sql = "select max(sheet)" +
		             " from uy_tr_miccont" +
				     " where uy_tr_mic_id = " + this.getUY_TR_Mic_ID();
		int nro = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(nro > 0){
			
			sheet = nro + 1;
			
		} else sheet = 2; //seteo nro 2 porque la 1 siempre va a ser la del cabezal
		
		this.setsheet(sheet);			
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 08/04/2014. ISSUE#1629
	 * Metodo que carga los datos para el primer CRT del formulario continuacion.
	 * @param order 
	 * 
	 * */
	public void loadCRT1(MTRTransOrderLine oLine){
		
		String paisOrigen = "", value = "";
		MCiudad ciudad = null;
		MCountry pais = null;
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		MTRMic hdr = new MTRMic(getCtx(),this.getUY_TR_Mic_ID(),get_TrxName()); //obtengo el cabezal
		MTRCrt crt = new MTRCrt(getCtx(),oLine.getUY_TR_Crt_ID(),get_TrxName()); //obtengo CRT
		MTRTrip trip = new MTRTrip(getCtx(),crt.getUY_TR_Trip_ID(),get_TrxName()); //obtengo el expediente del CRT	
		MTRWay way = new MTRWay(getCtx(),trip.getUY_TR_Way_ID(),get_TrxName()); //obtengo el trayecto
		MTRTransOrder order = new MTRTransOrder(getCtx(), oLine.getUY_TR_TransOrder_ID(), get_TrxName()); //obtengo la OT
					
		this.setUY_TR_Crt_ID(crt.get_ID()); //seteo CRT seleccionado
		this.setCrtImgNum1(crt.getcodigo()); //seteo el codigo de imagen
		this.setC_Currency_ID(crt.getC_Currency_ID()); //seteo la moneda
		this.setRemitente(crt.getRemitente()); //seteo el remitente
		this.setDestinatario(crt.getDestinatario()); //seteo el destinatario
		this.setConsignatario(crt.getConsignatario()); //seteo el consignatario
		this.setObservaciones2(hdr.getTruckData());
		this.setUY_TR_PackageType_ID(oLine.getUY_TR_PackageType_ID()); //seteo tipo de bulto
		
		if(this.getObservaciones2()==null || this.getObservaciones2().equalsIgnoreCase("")){
			
			//cargo datos del chofer
			if(order.getUY_TR_Driver_ID() > 0){

				MTRDriver driver = new MTRDriver(getCtx(),order.getUY_TR_Driver_ID(),get_TrxName());

				String driverName = driver.getFirstName() + " " + driver.getFirstSurname();
				String driverTipoDoc = driver.gettipodoc();
				String driverNumDoc = driver.getNationalCode();

				String driverData = "Chofer: " + driverName + " " + driverTipoDoc + " " + driverNumDoc;
				
				this.setObservaciones2(driverData);
			}		
			
		}
		
		 //seteo marcas y numeros de los bultos
		if(trip.getProductType()!=null) this.setDescription(trip.getProductType());
			
		//seteo aduana destino
		if(hdr.getUY_TR_Border_ID_2()>0){
			
			this.setUY_TR_Border_ID(hdr.getUY_TR_Border_ID_2());
						
		} else this.setUY_TR_Border_ID(trip.getUY_TR_Border_ID_1()); 
		
		this.setpesoBruto(oLine.getWeight()); //seteo peso bruto
		this.setpesoNeto(oLine.getWeight2()); //seteo peso neto
		this.setQtyPackage(oLine.getQtyPackage()); //seteo cantidad de bultos
		this.setImporte(oLine.getProductAmt()); //seteo valor de mercaderia
		
		//seteo valor del flete segun parametros de transporte
		//verifico si debo hacer la conversion a U$S
		if(param.isConvertedAmt()){
			
			if(crt.getC_Currency2_ID()!=100){

				BigDecimal dividerate = Env.ZERO;

				BigDecimal amt = oLine.getAmount();
				int curFromID = crt.getC_Currency2_ID();
				int curToID = 100;

				if (curFromID == 142){
					dividerate = MConversionRate.getDivideRate(curFromID, curToID, hdr.getDateTrx(), 0, this.getAD_Client_ID(), this.getAD_Org_ID());	
				}
				else{
					BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, hdr.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, hdr.getDateTrx(), 0, this.getAD_Client_ID(), 0);

					if ((rateFrom != null) && (rateTo != null)){
						if (rateTo.compareTo(Env.ZERO) > 0) {
							dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
						}
					}
				}

				if (dividerate == null || dividerate.compareTo(Env.ZERO)==0){

					ADialog.warn(0,null,"No se obtuvo tasa de cambio para fecha de documento");

				} else {

					amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);

					this.setAmount(amt);
				}		

			} else this.setAmount(oLine.getAmount());
			
		} else this.setAmount(oLine.getAmount());

		//obtengo ciudad y pais de origen
		if(trip.getUY_Ciudad_ID() > 0) {
									
			ciudad = new MCiudad(getCtx(),trip.getUY_Ciudad_ID(),get_TrxName());
						
			pais = new MCountry(getCtx(),ciudad.getC_Country_ID(),get_TrxName());
			paisOrigen = pais.getName().toUpperCase();
		}
	
		//seteo origen de las mercaderias
		if(trip.getProdSource()!=null && !trip.getProdSource().equalsIgnoreCase("")){
			
			this.setLocationComment(trip.getProdSource());
				
		} else this.setLocationComment(paisOrigen);
		
		if(way.isPrintExpo()){

			value = "";			
			
			MBPartner partner = new MBPartner(getCtx(),trip.getC_BPartner_ID_To(),get_TrxName()); //instancio el exportador

			value = "EXPORTADOR: " + partner.getName() + "\n";

			if(trip.getReferenceNo()!=null && !trip.getReferenceNo().equalsIgnoreCase("")) value += "FACTURA N°: " + trip.getReferenceNo();

			this.setObservaciones2(this.getObservaciones2() + "\n" + value);


		} else if (way.isPrintDeclaration()){
			
			value = "";
			
			if(trip.getUY_TR_Despachante_ID_3()>0){
				
				MTRDespachante desp = new MTRDespachante(getCtx(),trip.getUY_TR_Despachante_ID_3(),get_TrxName()); //obtengo representante en frontera del exportador
				
				value = "DESP: " + desp.getName() + "\n";
				
			}	
				
			if(trip.getDeclarationType()!=null && (trip.getNumero()!=null && !trip.getNumero().equalsIgnoreCase(""))){
				
				if(value.equalsIgnoreCase("")){
					
					value = trip.getDeclarationType() + ": " + trip.getNumero();					
					
				} else value += trip.getDeclarationType() + ": " + trip.getNumero();			
				
			}
			
			this.setObservaciones2(this.getObservaciones2() + "\n" + value);
			
		}	
		
		this.setCrtStatus1("ENALTA");	
		
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 08/04/2014. ISSUE#1629
	 * Metodo que carga los datos para el segundo CRT del formulario continuacion.
	 * @param order 
	 * 
	 * */
	public void loadCRT2(MTRTransOrderLine oLine){
		
		String paisOrigen = "", value = "";
		MCiudad ciudad = null;
		MCountry pais = null;
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		MTRMic hdr = new MTRMic(getCtx(),this.getUY_TR_Mic_ID(),get_TrxName()); //obtengo el cabezal
		MTRCrt crt = new MTRCrt(getCtx(),oLine.getUY_TR_Crt_ID(),get_TrxName()); //obtengo CRT
		MTRTrip trip = new MTRTrip(getCtx(),crt.getUY_TR_Trip_ID(),get_TrxName()); //obtengo el expediente del CRT		
		MTRWay way = new MTRWay(getCtx(),trip.getUY_TR_Way_ID(),get_TrxName()); //obtengo el trayecto
		MTRTransOrder order = new MTRTransOrder(getCtx(), oLine.getUY_TR_TransOrder_ID(), get_TrxName()); //obtengo la OT
						
		this.setUY_TR_Crt_ID_1(crt.get_ID()); //seteo CRT seleccionado
		this.setCrtImgNum2(crt.getcodigo()); //seteo el codigo de imagen
		this.setC_Currency2_ID(crt.getC_Currency_ID()); //seteo la moneda
		this.setRemitente2(crt.getRemitente()); //seteo el remitente
		this.setDestinatario2(crt.getDestinatario()); //seteo el destinatario
		this.setConsignatario2(crt.getConsignatario()); //seteo el consignatario
		this.setObservaciones4(hdr.getTruckData());
		this.setUY_TR_PackageType_ID_1(oLine.getUY_TR_PackageType_ID()); //seteo tipo de bulto
		
		if(this.getObservaciones4()==null || this.getObservaciones4().equalsIgnoreCase("")){
			
			//cargo datos del chofer
			if(order.getUY_TR_Driver_ID() > 0){

				MTRDriver driver = new MTRDriver(getCtx(),order.getUY_TR_Driver_ID(),get_TrxName());

				String driverName = driver.getFirstName() + " " + driver.getFirstSurname();
				String driverTipoDoc = driver.gettipodoc();
				String driverNumDoc = driver.getNationalCode();

				String driverData = "Chofer: " + driverName + " " + driverTipoDoc + " " + driverNumDoc;
				
				this.setObservaciones4(driverData);
			}		
			
		}
		
		 //seteo marcas y numeros de los bultos
		if(trip.getProductType()!=null) this.setdescripcion(trip.getProductType());
			
		//seteo aduana destino
		if(hdr.getUY_TR_Border_ID_2()>0){
			
			this.setUY_TR_Border_ID_1(hdr.getUY_TR_Border_ID_2());
						
		} else this.setUY_TR_Border_ID_1(trip.getUY_TR_Border_ID_1());
		
		this.setpesoBruto2(oLine.getWeight()); //seteo peso bruto
		this.setpesoNeto2(oLine.getWeight2()); //seteo peso neto
		this.setQtyPackage2(oLine.getQtyPackage()); //seteo cantidad de bultos
		this.setImporte2(oLine.getProductAmt()); //seteo valor de mercaderia

		//seteo valor del flete segun parametros de transporte
		//verifico si debo hacer la conversion a U$S
		if(param.isConvertedAmt()){
			
			if(crt.getC_Currency2_ID()!=100){

				BigDecimal dividerate = Env.ZERO;

				BigDecimal amt = oLine.getAmount();
				int curFromID = crt.getC_Currency2_ID();
				int curToID = 100;

				if (curFromID == 142){
					dividerate = MConversionRate.getDivideRate(curFromID, curToID, hdr.getDateTrx(), 0, this.getAD_Client_ID(), this.getAD_Org_ID());	
				}
				else{
					BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, hdr.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, hdr.getDateTrx(), 0, this.getAD_Client_ID(), 0);

					if ((rateFrom != null) && (rateTo != null)){
						if (rateTo.compareTo(Env.ZERO) > 0) {
							dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
						}
					}
				}

				if (dividerate == null || dividerate.compareTo(Env.ZERO)==0){

					ADialog.warn(0,null,"No se obtuvo tasa de cambio para fecha de documento");

				} else {

					amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);

					this.setAmount2(amt);
				}		

			} else this.setAmount2(oLine.getAmount());
			
		} else this.setAmount2(oLine.getAmount());
		
		//obtengo ciudad y pais de origen
		if(trip.getUY_Ciudad_ID() > 0) {
									
			ciudad = new MCiudad(getCtx(),trip.getUY_Ciudad_ID(),get_TrxName());
						
			pais = new MCountry(getCtx(),ciudad.getC_Country_ID(),get_TrxName());
			paisOrigen = pais.getName().toUpperCase();
		}

		//seteo origen de las mercaderias
		if(trip.getProdSource()!=null && !trip.getProdSource().equalsIgnoreCase("")){
			
			this.setLocationComment2(trip.getProdSource());
				
		} else this.setLocationComment2(paisOrigen);
		
		if(way.isPrintExpo()){

			value = "";			
			
			MBPartner partner = new MBPartner(getCtx(),trip.getC_BPartner_ID_To(),get_TrxName()); //instancio el exportador

			value = "EXPORTADOR: " + partner.getName() + "\n";

			if(trip.getReferenceNo()!=null && !trip.getReferenceNo().equalsIgnoreCase("")) value += "FACTURA N°: " + trip.getReferenceNo();

			this.setObservaciones4(this.getObservaciones4() + "\n" + value);


		} else if (way.isPrintDeclaration()){
			
			value = "";
			
			if(trip.getUY_TR_Despachante_ID_3()>0){
				
				MTRDespachante desp = new MTRDespachante(getCtx(),trip.getUY_TR_Despachante_ID_3(),get_TrxName()); //obtengo representante en frontera del exportador
				
				value = "DESP: " + desp.getName() + "\n";
				
			}	
				
			if(trip.getDeclarationType()!=null && (trip.getNumero()!=null && !trip.getNumero().equalsIgnoreCase(""))){
				
				if(value.equalsIgnoreCase("")){
					
					value = trip.getDeclarationType() + ": " + trip.getNumero();					
					
				} else value += trip.getDeclarationType() + ": " + trip.getNumero();					
			}
			
			this.setObservaciones4(this.getObservaciones4() + "\n" + value);
			
		}	
		
		this.setCrtStatus2("ENALTA");
		
	}	

}
