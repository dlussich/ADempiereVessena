/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/09/2013
 */
package org.openup.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.openup.model.MDeliveryPoint;
import org.openup.model.MDeliveyPointPostal;
import org.openup.model.MFduConnectionData;
import org.openup.model.MTTCard;
import org.openup.model.MTTCardPlastic;
import org.openup.model.MTTCardStatus;
import org.openup.model.MTTConfig;
import org.openup.model.MTTConfigCardAction;
import org.openup.model.MTTLoadPlastic;
import org.openup.model.MTTLoadPlasticLine;
import org.openup.model.X_UY_TT_Card;
import org.openup.util.ItalcredSystem;

/**
 * org.openup.process - PTTLoadPlastics
 * OpenUp Ltda. Issue #1173 
 * Description: Carga de plasticos reportados por FDU para ser recepcionados
 * a futuro.
 * @author Gabriel Vila - 23/09/2013
 * @see
 */
public class PTTLoadPlastics extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PTTLoadPlastics() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/09/2013
	 * @see
	 */
	@Override
	protected void prepare() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 23/09/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		Connection con = null;
		ResultSet rs = null;
		String sql = "";
		
		Trx trans = null;
		
		try {			
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);

			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);	
			
			sql =   " select CONVERT(datetime,a.CliEntHorD,1) as CLiHoraDesde, cast((a.CliEntHorH) as datetime) as CliHoraHasta, " +
					" a.*, b.locnom as CliLocalidad, em.empnom as SucNom, can.candsc as CanalNom, camp.CampDescri as CampaniaNom, " +
					" isnull(solenvtarsuc,-1) as solenvtarsuc, isnull(solrageid,-1) as solrageid, " + 
					" cast('1999-01-01' as datetime) as ComparoFechas " +
					" from q_tarjplas_adempiere a " +
					" left join q_localidad b on (a.clientloc = b.LocCod) " +
					" left join empresa em on (a.sucursal = em.empcod ) " +
					" left join canales can on (a.canal = can.cancod) " +
					" left join Campania camp on (a.campania = camp.CampCod) " +
					" where a.tppreceso >= cast('2014-01-05' as datetime) " +
					//" and a.solenvtarsuc = 8 " +
					" order by a.tplcuenta ";
			
			rs = stmt.executeQuery(sql);

			String trxNameAux = null;
			boolean firstRow = true, firstPlastic = true;
			String accountNoAux = "";
			MTTLoadPlastic header = null;
			MTTCard card = null;
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			while (rs.next()) {
				
				// Verifico si este registro de la tarjplas ya fue procesado en nuestra base
				// Si fue procesado, no lo considero y voy al registro siguiente.
				if (this.existeEnBase(String.valueOf(rs.getInt("tplnro")).trim(), String.valueOf(rs.getBigDecimal("tpltarjnro")).trim())){
					continue;
				}
				
				// Corte de control por Numero de Cuenta
				if (!String.valueOf(rs.getInt("tplcuenta")).trim().equalsIgnoreCase(accountNoAux)){

					// En el primer registro creo el cabezal de carga de plasticos
					if (firstRow){
						firstRow = false;
						header = new MTTLoadPlastic(getCtx(), 0, null);
						header.setDateTrx(today);
						header.setDefaultDocType();
						header.saveEx();
					}
					
					// Si ya estoy en una transaccion, hago commit y empiezo una nueva
					if (trans != null){
						trans.close();
					}
					
					// Genero nueva transaccion
					trxNameAux = Trx.createTrxName();
					trans = Trx.get(trxNameAux, true);
					
					String accountNo = String.valueOf(rs.getInt("tplcuenta")).trim(); 
					String cardNo = String.valueOf(rs.getBigDecimal("tpltarjnro")).trim();
					
					// Se agrega el numero de tarjeta al chequeo para contemplar la posibilidad que vengan dos recepciones para la misma cuenta.
					// Verifico si ya tengo un seguimiento pendiente de recepcion para esta cuenta.
					card = MTTCard.forAccountNoandCardNo(getCtx(), accountNo,cardNo, trxNameAux);
					
					// Si no tengo creo ahora un nuevo seguimiento para esta cuenta
					if (card == null){
					
						// Instancia cuenta y valido que la misma este apta para recepcionarse
						ItalcredSystem it = new ItalcredSystem();
						card = it.getCustomerData(getCtx(), accountNo, trxNameAux);
						
						if (card == null){
							trans.rollback();
							continue;
						}
						
						if (rs.getBigDecimal("TplNro") != null){
							card.setTplNro(String.valueOf(rs.getBigDecimal("TplNro")).trim());
						}

						if (rs.getBigDecimal("CliCod") != null){
							card.setclicod(String.valueOf(rs.getBigDecimal("CliCod")).trim());
						}
						
						if (rs.getInt("solenvtarsuc") == 0){
							card.setCardDestination(X_UY_TT_Card.CARDDESTINATION_DomicilioParticular);
							
							if ((card.getlocalidad() != null) && (card.getPostal() != null)){
								MDeliveyPointPostal ppostal = MDeliveyPointPostal.forLocalidadPostal(getCtx(), card.getPostal(), null);
								if (ppostal != null){
									MDeliveryPoint dp = (MDeliveryPoint)ppostal.getUY_DeliveryPoint();
									card.setUY_DeliveryPoint_ID(dp.get_ID());
								}
							}
						}
						else if (rs.getInt("solenvtarsuc") == 8){
							card.setCardDestination(X_UY_TT_Card.CARDDESTINATION_RedPagos);
							card.setSubAgencia(String.valueOf(rs.getInt("solrageid")).trim());
							
							// TEMPORAL RED PAGOS.
							// Comento y sustituyo
							
							//card.setUY_DeliveryPoint_ID(config.getUY_DeliveryPoint_ID());
							
							MDeliveryPoint delPointSubAgencia = MDeliveryPoint.forSubAgencyNo(getCtx(), card.getSubAgencia(), null);
							if ((delPointSubAgencia == null) || (delPointSubAgencia.get_ID() <= 0)){
								card.setUY_DeliveryPoint_ID(config.getUY_DeliveryPoint_ID());
							}
							else{
								card.setUY_DeliveryPoint_ID(delPointSubAgencia.get_ID());
							}
							
							// Fin TEMPORAL
							
						}
						else{
							card.setCardDestination(X_UY_TT_Card.CARDDESTINATION_Sucursal);
							MDeliveryPoint dp = MDeliveryPoint.forInternalCode(getCtx(), String.valueOf(rs.getInt("solenvtarsuc")), null);
							if ((dp == null) || (dp.get_ID() <= 0)){
								throw new AdempiereException("No existe Punto de Distribucion con Codigo Interno = " + rs.getInt("solenvtarsuc"));
							}
							card.setUY_DeliveryPoint_ID(dp.get_ID());
						}
						
						
						//--> Primer cambio para mejora 3273 --> Cod 129 puede ser nueava o reimpresion (129_S -->NUEVA; 129_N -->REIMPRESION)
						MTTConfigCardAction cardAction = null;
						if (String.valueOf(rs.getInt("movcod")).trim().equals("129")){
							cardAction = MTTConfigCardAction.forInternalCode(getCtx(), config.get_ID(), 
									String.valueOf(rs.getInt("movcod")).trim()+"_"+String.valueOf(rs.getString("cuenta_nueva").trim()), trxNameAux);
						}else{
							cardAction = MTTConfigCardAction.forInternalCode(getCtx(), config.get_ID(), 
									String.valueOf(rs.getInt("movcod")).trim(), trxNameAux);

						}						
						
						if (cardAction == null){
							card.setIsDeliverable(false);
							card.setIsRetained(true);
							card.setNotValidText("Codigo de Necesidad no definido : " + String.valueOf(rs.getInt("movcod")).trim());	
						}
						else{
							card.setIsDeliverable(true);
							card.setIsRetained(false);
							card.setCardAction(cardAction.getCardAction());
						}
						
						// INI Openup Sylvie Bouissa 28-11-2014 Issue# 3273 MEJORA 
						//Seteo si la cuenta tiene vale firmado o no 
						if (rs.getString("vale_firmado").toUpperCase().trim().equals("S")){
							card.set_ValueOfColumn("IsValeSigned", true);
						}else if (rs.getString("vale_firmado").toUpperCase().trim().equals("N")){
							card.set_ValueOfColumn("IsValeSigned", false);
						}		
						// FIN  Openup Sylvie Bouissa 28-11-2014 Issue# 3273 MEJORA 
						
			// INI Openup Sylvie Bouissa 01-12-2014 Issue# 3273 MEJORA 
						//Seteo los datos de dirección alternativo SI la cuenta es nueva
						if(card.getCardAction().equals(X_UY_TT_Card.CARDACTION_Nueva)){
							if(rs.getString("CliEntDia") != null){
								card.parsearDias(rs.getString("CliEntDia").trim());
								card.ingresarDias(rs.getString("CliEntDia").trim());
							}
							if(rs.getString("CliEntHorD")!= null && !(rs.getString("CliEntHorD").equals(""))){
								card.set_ValueOfColumn("HoraEntrega1", rs.getTimestamp("CliHoraDesde").toString());
							}
							if(rs.getString("CliEntHorH")!= null && !(rs.getString("CliEntHorH").equals(""))){
								card.set_ValueOfColumn("HoraEntrega2", rs.getTimestamp("CliHoraHasta").toString());
							}
							if(rs.getString("CliEntDom")!= null){
								card.set_ValueOfColumn("Calle2",rs.getString("CliEntDom").trim().toUpperCase());
							}
							if(rs.getString("CliEntNro")!= null){
								card.set_ValueOfColumn("NroPuerta2",rs.getString("CliEntNro").trim());
							}
							if(rs.getString("CliEntEsq")!= null){
								card.set_ValueOfColumn("Esquina2",rs.getString("CliEntEsq").trim().toUpperCase());
							}
							if(rs.getString("CliLocalidad")!= null){
								card.set_ValueOfColumn("Localidad2",rs.getString("CliLocalidad").trim().toUpperCase());
							}
							if(rs.getString("CliEntCP")!= null){
								card.set_ValueOfColumn("Postal2",rs.getString("CliEntCP").trim());
							}
							if(rs.getString("CliEntObs")!= null){
								card.set_ValueOfColumn("Description2",rs.getString("CliEntObs").trim().toUpperCase());
							}
						}
						
						//Se guardan los datos de solicitud, tanto el código como el nombre o la descripcion según corresponda
						//(se realizan joins en la consulta por cada tabla o vista)
						int suc = rs.getInt("sucursal");
						if(suc > 0){
							card.set_ValueOfColumn("SucCod", rs.getInt("sucursal"));
							card.set_ValueOfColumn("SucNom", rs.getString("SucNom").trim());
						}
						if(rs.getString("vendedor") != null){
							card.set_ValueOfColumn("Vendedor", rs.getString("vendedor").trim());
						}
						int canal = rs.getInt("canal");
						if(canal > 0){
							card.set_ValueOfColumn("CanalCod", rs.getInt("canal"));
							card.set_ValueOfColumn("CanalNom", rs.getString("CanalNom").trim());
						}
						int camp = rs.getInt("campania");
						if(camp> 0){
							card.set_ValueOfColumn("CampaniaCod", rs.getInt("campania"));
							card.set_ValueOfColumn("CampaniaNom", rs.getString("CampaniaNom").trim()); 
							
						}
										

						// Se guardan las fechas históricas, se compara con fecha
						// formateada en la consulta ya que en los datos aparece fecha 1753-01-01 en este caso
						// Vanina pidio que sea null 
						
						if(rs.getTimestamp("f_solicitud")!=null && rs.getTimestamp("f_solicitud").after(rs.getTimestamp("ComparoFechas"))){
							card.set_ValueOfColumn("FchSolicitud", rs.getTimestamp("f_solicitud"));
						}
						if(rs.getTimestamp("f_credito")!=null && rs.getTimestamp("f_credito").after(rs.getTimestamp("ComparoFechas"))){
							card.set_ValueOfColumn("FchCredito", rs.getTimestamp("f_credito"));
						}
						
						if(rs.getTimestamp("f_observado")!=null && rs.getTimestamp("f_observado").after(rs.getTimestamp("ComparoFechas"))){
							card.set_ValueOfColumn("FchObservado", rs.getTimestamp("f_observado"));
						}
						
						if(rs.getTimestamp("f_aprobado")!=null && rs.getTimestamp("f_aprobado").after(rs.getTimestamp("ComparoFechas"))){
							card.set_ValueOfColumn("FchAprobado", rs.getTimestamp("f_aprobado"));
						}
						
						if(rs.getTimestamp("f_activado")!=null && rs.getTimestamp("f_activado").after(rs.getTimestamp("ComparoFechas"))){
							card.set_ValueOfColumn("FchActivado", rs.getTimestamp("f_activado"));
						}
						
						if(rs.getTimestamp("f_embozado")!=null && rs.getTimestamp("f_embozado").after(rs.getTimestamp("ComparoFechas"))){
							card.set_ValueOfColumn("FchEmbozado", rs.getTimestamp("f_embozado"));	
						}				
						
			// FIN Openup Sylvie Bouissa 01-12-2014 Issue# 3273 MEJORA 
						
						// Seteo si es mastercard o no.
						if (rs.getInt("ptcod") == 1){
							card.setIsMaster(true);
							card.setNeedPrint(true);
						}
						else{
							card.setIsMaster(false);
						}

						// Cuenta pendiente de recepcion
						card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "pendrec").get_ID());
						card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
						
						// Genero nueva incidencia
						card.generateIncidencia();
						
						card.saveEx();
					}

					accountNoAux = accountNo;
					
					firstPlastic = true;
				}
				
				card.setCliDigCtrl(rs.getInt("CliDigCtrl"));
				card.setsolnro(String.valueOf(rs.getInt("SolNro")));
				card.setSolEnvRes(rs.getString("SolEnvRes"));
				card.setSolEnvExt(rs.getString("SolEnvExt"));
				
				String calleNro = rs.getString("TplCalleNr");
				if (calleNro == null) calleNro = "";
				String dpto = rs.getString("TplDpto");
				if (dpto == null) dpto = "";
				
				card.setTplCalleNr(calleNro);
				card.setTplDpto(dpto);
				
				// Marco plastico enviado para cuenta actual
				String cedulaSinDigitoVerif = rs.getString("tpltelsoci").trim();
				cedulaSinDigitoVerif = cedulaSinDigitoVerif.substring(0, cedulaSinDigitoVerif.length() -1);
				if (cedulaSinDigitoVerif.startsWith("0")){
					cedulaSinDigitoVerif = cedulaSinDigitoVerif.substring(1, cedulaSinDigitoVerif.length());
					if (cedulaSinDigitoVerif.startsWith("0")){
						cedulaSinDigitoVerif = cedulaSinDigitoVerif.substring(1, cedulaSinDigitoVerif.length());
					}
				}
				
				MTTCardPlastic plastic = MTTCardPlastic.forCedula(getCtx(), card.get_ID(), cedulaSinDigitoVerif, trxNameAux);
				if (plastic == null){
					card.setIsDeliverable(false);
					card.setIsRetained(true);
					card.setNotValidText(" No se pudo obtener informacion de FinancialPro para Cuenta : " + card.getAccountNo() + 
										 " y Cedula : " + cedulaSinDigitoVerif);
				}
				else{
					plastic.setIsSent(true);
					//OpenUp. Sylvie Bouissa 20/10/2014 #Issue 3142 -> se modifica la obtención del nro de tjta a bigdecimal.
					//plastic.setNroTarjetaNueva(String.valueOf(rs.getInt("tpltarjnro")).trim()); // Tarjeta nueva -->ERROR obteniendo nrotarjeta por int
					plastic.setNroTarjetaNueva(String.valueOf(rs.getBigDecimal("tpltarjnro")).trim()); // Tarjeta nueva
					plastic.saveEx();
					
					if (plastic.getTipoSocio() == 0){
						card.setNroTarjetaNueva(plastic.getNroTarjetaNueva());
					}
				}
				
				// Realizo la verificacion de direccion que tiene FDU contra los datos
				// de direccion que tiene Financial.
				// Como todos los plasticos de una misma cuenta tienen la misma direccion
				// en FDU, hago la verificacion para el primer plastico procesado por cuenta.
				if (firstPlastic){
					firstPlastic = false;
					
					boolean diff = false;
					
					// Verifico si codigo postal y/o direccion es distinto
					String codPostalFDU = String.valueOf(rs.getInt("tplcodpost")).trim();
					if (card.getPostal() != null){
						if (!card.getPostal().trim().toLowerCase().equalsIgnoreCase(codPostalFDU.toLowerCase())){
							diff = true;
						}
					}
					else{
						card.setPostal(codPostalFDU);
					}
					
					if (card.getAddress1() != null){
						
						String direccionFDU = rs.getString("tplcallenr").trim().toLowerCase().replaceAll(" ", "");
						direccionFDU = direccionFDU.replaceAll("\\.", "");
						
						String direccionFIN = card.getAddress1().trim().toLowerCase().replaceAll(" ", "");
						direccionFIN = direccionFIN.replaceAll("\\.", "");
						
						direccionFDU = direccionFDU.replaceAll(",", "");
						direccionFIN = direccionFIN.replaceAll(",", "");
						
						if (!direccionFDU.equalsIgnoreCase(direccionFIN)){
							diff = true;
						}
					}
					else{
						card.setAddress1(rs.getString("tplcallenro").trim());
					}
					
					if (diff){
						card.setNeedPrint(true);
					}
					
				}
				
				
				// Guardo linea de carga de plastico
				MTTLoadPlasticLine line = new MTTLoadPlasticLine(getCtx(), 0, trxNameAux);
				line.setUY_TT_LoadPlastic_ID(header.get_ID());
				line.setUY_TT_Card_ID(card.get_ID());
				line.setTipoSocio(rs.getInt("tplautoriz"));
				if (plastic != null){
					line.setUY_TT_CardPlastic_ID(plastic.get_ID());
				}
				line.setAccountNo(accountNoAux);
				line.setCedula(cedulaSinDigitoVerif);
				line.setMonthFrom(rs.getInt("tplvigdesa"));
				line.setYearFrom(rs.getInt("tplvigdesm"));
				line.setMonthTo(rs.getInt("tplvighasa"));
				line.setYearTo(rs.getInt("tplvighasm"));
				line.setName(rs.getString("tplnomsoci").trim());
				line.setGAFCOD(rs.getInt("gafcod"));
				line.setNroTarjetaNueva(String.valueOf(rs.getBigDecimal("tpltarjnro")).trim());
				
				String nroTarjAnterior = String.valueOf(rs.getBigDecimal("tpltarjant"));
				if (nroTarjAnterior != null) nroTarjAnterior = nroTarjAnterior.trim();
				
				line.setNroTarjetaAnterior(nroTarjAnterior);
				line.setAddress1(rs.getString("tplcallenr").trim());
				line.setPostal(String.valueOf(rs.getInt("tplcodpost")).trim());
				line.setPiso(rs.getString("tplpiso"));
				line.setDpto(rs.getString("tpldpto"));
				line.setInternalCode(String.valueOf(rs.getInt("tplnro")).trim());
				line.setDateReceived(today);
				
				line.saveEx();
				
				if (card.is_Changed()){
					card.saveEx();
				}
				
			}					

			if (card != null){
				if (card.getUY_TT_CardStatus_ID() > 0){
					card.setUY_TT_CardStatus_ID(MTTCardStatus.forValue(getCtx(), null, "pendrec").get_ID());
					card.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				}
			}
			
			// Si tengo transaccion, hago commit
			if (trans != null){
				trans.close();
			}
			
			rs.close();
			con.close();			

		} 
		catch (Exception e) {
			if (trans!=null){
				trans.rollback();
			}
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) 
							rs.close();
					}
					if (!con.isClosed()) 
						con.close();
				} 
				catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
		return "OK";
		
	}


	/***
	 * Obtiene conexion a base de Financial.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 23/09/2013
	 * @see
	 * @param fduData
	 * @return
	 * @throws Exception
	 */
	private Connection getFDUConnection(MFduConnectionData fduData) throws Exception {
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			if(fduData != null){
				
				connectString = "jdbc:sqlserver://" + fduData.getserver_ip() + "\\" + fduData.getServer() + 
								";databaseName=" + fduData.getdatabase_name() + ";user=" + fduData.getuser_db() + 
								";password=" + fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, fduData.getuser_db(), fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}

	
	/***
	 * Verifica si para datos de tarjplas recibidos, ya hay un registro procesado en nuestra base.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 07/11/2013
	 * @see
	 * @param internalCode
	 * @param nrotarjetanueva
	 * @return
	 */
	private boolean existeEnBase(String internalCode, String nrotarjetanueva){
	
		String sql = null;
		boolean result = false;
		try {
			
			sql = " select internalcode " +
				  " from vuy_tt_loadplastic_key " +
				  "	where internalcode ='" + internalCode + "' " +
				  "	and nrotarjetanueva ='" + nrotarjetanueva + "'";
			
			String value =  DB.getSQLValueStringEx(null, sql);
			if (value != null) 
				result = true;
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return result;
	}
	
	
}
