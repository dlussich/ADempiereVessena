package org.openup.util;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.openup.model.MDeliveryPoint;
import org.openup.model.MDeliveyPointPostal;
import org.openup.model.MTTCard;
import org.openup.model.MTTCardPlastic;
import org.openup.model.MTTConfig;
import org.openup.model.X_UY_TT_Card;
import org.openup.model.X_UY_TT_CardPlastic;


public class ItalcredSystem extends ExternalSystem {

	public ItalcredSystem() {
	}
	
	/**
	 * OpenUp. Guillermo Brust. 01/08/2013. ISSUE # 1168
	 * Se guarda una linea en la tabla de legajos de Financial, segun los parametros recibidos
	 * */
	public void newLegajo(String usuario, String cedula, String observacion, String familiaLegajo, Timestamp fechaCreacion) {
		
		//OpenUp. Guillermo Brust. 31/07/2013. ISSUE # 1167
		//Se guardan lineas en el legajo.
		//Se debe usar una transaccion
		Connection con = null;
		String sql = "";
		try {			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false); //esto para iniciar una transaccion
			
			//obtengo el nuevo identficador para la nueva linea que vaya a guardar en el sqlserver
	    	int tablaLegajoID = this.obtenerIdentificadorTablaLegajos(con, stmt);   
	    	
	    	if(tablaLegajoID > 0){
	    		//guardo la linea del ajuste en sqlserver con el ID que obtuve
	    		SimpleDateFormat dfConHora = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.sss");
	    		SimpleDateFormat dfSinHora = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
	    		
	    		if(usuario.length() > 10) usuario = usuario.substring(0, 9);				
				if(observacion.length() > 100)observacion = observacion.substring(0, 99) ;				
				
				sql = "INSERT INTO PROMPAG2 (EmpCod, SolNro, PdpNro, PdpLin, PdpLinFec, PdpText, PdpLFReal, PdpLinCli, PdpLinUsrI, PdpCCon, PdpVenCod)" + 
					  " VALUES (" +
					  10 + "," + //EmpCod
					  0 + "," + //SolNro
					  0 + "," + //PdpNro
					  tablaLegajoID + "," + //PdpLin
					  "'" + dfConHora.format(fechaCreacion) + "'," + //PdpLinFec					
					  "'" + observacion + "'," + //PdpText			  
					  "'" + dfSinHora.format(fechaCreacion) + "'," + //PdpLFReal
					  "'" + cedula + "'," + //PdpLinCli
					  "'" + usuario + "'," + //PdpLinUsrI
					  "'" + familiaLegajo + "'," + //PDpCCon
					  "''" +
					  ")";		 
				
				stmt.executeUpdate(sql);		
	    		
	    	}else{
	    		throw new AdempiereException("No se logró obtener identificador unico para grabar Legajos");
	    	} 	
		    
		    //Luego de hacer todo, recien ahi termino la transaccion
	    	con.commit(); //This commits the transaction and starts a new one.
			stmt.close(); //This turns off the transaction.	    			
			con.close();

		} catch (Exception e) {
			try {
				if(!con.isClosed()){
					con.rollback();
				}				
			} catch (Exception e2) {
				throw new AdempiereException(e);
			}			
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){				
				try {						
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
	}
	

	
	/**
	 * OpenUp. Guillermo Brust. 01/08/2013. ISSUE # 1168
	 * Se obtiene un ID para el nuevo registro a guardar en la tabla de legajos en sqlserver
	 * */
	private int obtenerIdentificadorTablaLegajos(Connection con, Statement stmt){
		
		int retorno = 0;
		ResultSet rs = null;			
		
		try {		
			//Modifico el ID para crear uno nuevo.
			stmt.executeUpdate("update NUME set NroUlt = NroUlt + 1 where NroDsc = 'Lin.Pdp Comentarios' and EmpCod = 1");			
			
			//Ahora obtengo ese valor que modifique			
			rs = stmt.executeQuery("select NroUlt from NUME where NroDsc = 'Lin.Pdp Comentarios' and EmpCod = 1");

			while (rs.next()) {				
				retorno = rs.getInt("NroUlt");
			}
			
			rs.close();			
			
		} catch (Exception e) {					
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){				
				try {					
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}					
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}	
		return retorno;
	}
	
	/**
	 * OpenUp. Guillermo Brust. 23/08/2013. ISSUE #
	 * Metodo que consulta la base de datos FinancialPro de Italcred, y devuelve un valor booleano.
	 * En el caso que la cuenta que ingresa por parametro tiene un convenio activo o pendiente devuelve TRUE de lo contrario devuelve FALSE
	 * 
	 * */
	public boolean isConvenioActivoOPendiente(String accountNo){
		
		return false;
		
		/*
		boolean retorno = false;
		
		Connection con = null;
		ResultSet rs = null;
		
		try {			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	
			
			String sql = "";
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
			}					

			rs.close();
			con.close();			

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}		
		return retorno;
		*/
	}
	
	
	
	/**
	 * OpenUp. Guillermo Brust. 23/08/2013. ISSUE #
	 * Metodo que consulta la base de datos FinancialPro de Italcred, y devuelve un valor Entero.
	 * El valor que devuele es la cantidad de dias de mora que tiene hasta la fecha actual, el cliente con el numero de cuenta que ingresa por parametro.
	 * 
	 * */
	public int getDiasDeMora(String accountNo){
		
	    int retorno = 0;
		
		Connection con = null;
		ResultSet rs = null;
		
		try {			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	
			
			String sql = "select Dias" + 
						 " from FinancialPro.dbo.ATRASOS_HIST" + 
						 " where nrocta = '" + accountNo + "'"  +
						 " and Fecha = (SELECT CONVERT (date, SYSDATETIME()))";
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				retorno = rs.getInt("Dias");
			}					

			rs.close();
			con.close();			

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		return retorno;
	}
	
	/***
	 * Obtiene modelo de liquidacion desde Financial para una cuenta.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 27/03/2014
	 * @see
	 * @param accountNo
	 * @return
	 */
	public String getModeloLiquidacion(String accountNo){
		
	    String retorno = null;
		
		Connection con = null;
		ResultSet rs = null;
		
		try {			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	
			
			String sql = " select MLCod " + 
						 " from q_clientes_adempiere " + 
						 " where stnrocta =" + accountNo;
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				retorno = rs.getString("MLCod");
			}					

			rs.close();
			con.close();			

		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		return retorno;
	}
	
	
	
	/**
	 * OpenUp. Guillermo Brust. 26/08/2013. ISSUE #1256
	 * Metodo que consulta la base de datos FinancialPro de Italcred, y devuelve un modelo de la MTTCard
	 * 	 * 
	 * */
	public MTTCard getCustomerData(Properties ctx, String accountNo, String trxName){
		
		MTTCard model = null;
		
		Connection con = null;
		ResultSet rs = null;
		
		int countPlastic = 0;
		
		try{
			con = this.getConnectionToSqlServer();

			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select clicod, stapenom, stnrotarj, stvenctarj, isnull(stlimcred,0) as stlimcred,  isnull(lccod,'') as producto, " +
						 " isnull(stderinro,0) as sttiposoc, isnull(nombre,'') as nombreapellido, isnull(stcodpos,0) as stcodpos, " +
						 " isnull(stlocalida,'') as localidad, GrpCtaCte, GAFCOD, MLCod, Tasas, Cargos, Parametros, DigitoVerif, " +
						 " isnull(validez,0) as validez, isnull(gafnom,'') as gafnom, coeficiente, " +
						 " isnull(clitel,'NO TIENE') as clitel, isnull(clicelular,'NO TIENE') as clicelular, " +						 
						 " letracompra, isnull(envioresumen,'N') as envioresumen, isnull(envioext,'N') as envioext, " +
						 " isnull(boletinada,'N') as boletinada, isnull(renovacion,'N') as renovacion, " +
						 " stcoefade, stcxservbo, stcallenro, isnull(stdepto,'') as stdepto, " +
						 " isnull(climail,'NO TIENE - SOLICITAR !!') as climail " +
					     " from q_clientes_adempiere " +
					     " where stnrocta =" + accountNo + 
					     " order by stderinro ";
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){

				boolean isTitular = false;
				
				if (rs.getInt("sttiposoc") == 0){
					
					isTitular = true;
					
					model = new MTTCard(ctx, 0, trxName);
					model.setAccountNo(accountNo);
					model.setCedula(String.valueOf(rs.getInt("clicod")));					
					model.setName(rs.getString("stapenom"));
					model.setGAFCOD(rs.getInt("GAFCOD"));
					model.setGAFNOM(rs.getString("gafnom"));
					model.setMLCod(rs.getString("MLCod"));
					model.setCardType(X_UY_TT_Card.CARDTYPE_Titular);
					model.setDueDate(rs.getString("stvenctarj"));
					model.setCreditLimit(rs.getBigDecimal("stlimcred"));
					model.setNroTarjetaTitular(rs.getString("stnrotarj"));
					model.setProductoAux(rs.getString("producto"));
					model.setGrpCtaCte(rs.getInt("GrpCtaCte"));
					model.setPostal(String.valueOf(rs.getInt("stcodpos")));
					model.setlocalidad(rs.getString("localidad"));
					model.setAddress1(rs.getString("stcallenro").replaceAll("/", " "));					
					model.setCardAction(X_UY_TT_Card.CARDACTION_Nueva);
					model.setIsDeliverable(true);
					model.setIsRetained(false);
					model.setLink_ID(1);
					
					String email = rs.getString("climail");
					if ((email == null) || (email.trim().equalsIgnoreCase(""))){
						email = "NO TIENE - SOLICITAR !!" ;
					}
					
					model.setEMail(email);
					model.setTelephone(rs.getString("clitel"));
					model.setMobile(rs.getString("clicelular"));

					model.saveEx();
					
					// Datos de direccion desde nueva tabla de Financial: CliDirDa)
					this.setCardAddress(model);
					
				}

				// Guardo datos de todo socio asi sea titular
				MTTCardPlastic line = new MTTCardPlastic(ctx, 0, trxName);
				line.setUY_TT_Card_ID(model.get_ID());				
				line.setValue(rs.getString("stnrotarj"));
				line.setCedula(String.valueOf(rs.getInt("clicod")));
				line.setName(rs.getString("stapenom"));
				line.setGAFCOD(rs.getInt("GAFCOD"));
				line.setGAFNOM(rs.getString("gafnom"));
				line.setMLCod(rs.getString("MLCod"));
				line.setDueDate(rs.getString("stvenctarj"));
				line.setPostal(String.valueOf(rs.getInt("stcodpos")));
				line.setlocalidad(rs.getString("localidad"));
				line.setCardType((isTitular) ? X_UY_TT_CardPlastic.CARDTYPE_Titular : X_UY_TT_CardPlastic.CARDTYPE_Derivado);
				String apto = "";
				if (rs.getString("stdepto") != null){
					if (!rs.getString("stdepto").trim().equalsIgnoreCase("")){
						apto = " - Apto.: " + rs.getString("stdepto");
					}
				}
				line.setAddress1(rs.getString("stcallenro") + apto);
				line.setCreditLimit(rs.getBigDecimal("stlimcred"));
				line.setIsTitular(isTitular);
				line.setTipoSocio(rs.getInt("sttiposoc"));
				line.saveEx();
				
				countPlastic++;
			}
			
			rs.close();
			con.close();

			// Seteo cantidad de plasticos y card carriers que tiene esta cuenta.
			if (model != null){
				model.setQtyPlastic(countPlastic);
				if (countPlastic <= 0) model.setQtyCarrier(0);
				else if (countPlastic <= 2) model.setQtyCarrier(1); 
				else if (countPlastic <= 4) model.setQtyCarrier(2);
				else if (countPlastic == 5) model.setQtyCarrier(3);
				model.setQtyCarrierCounted(0);
				model.saveEx();
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
		return model;
		
	}
	

	/***
	 * Obtiene y retorna lista de localidades y codigos postales desde Financial.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 23/09/2013
	 * @see
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public List<MDeliveyPointPostal> getDeliveryPointPostals(Properties ctx, String trxName){
		
		Connection con = null;
		ResultSet rs = null;
		
		List<MDeliveyPointPostal> lines = new ArrayList<MDeliveyPointPostal>();		

		try{
			
			con = this.getConnectionToSqlServer();

			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select loccod, locnom, locdepto, loccp " +
					     " from q_localidad " +
					     " order by locdepto, loccod ";
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){

				MDeliveyPointPostal postal = new MDeliveyPointPostal(ctx, 0, trxName);
				postal.setNomDepto(rs.getString("locdepto"));
				postal.setCodLocalidad(rs.getString("loccod"));
				postal.setlocalidad(rs.getString("locnom"));
				postal.setPostal(rs.getString("loccp"));
				
				lines.add(postal);
			}
			
			rs.close();
			con.close();
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
		return lines;

	}
	
	/***
	 * Metodo que verifica la existencia en base de Financial de la cuenta recibida como parametro.
	 * base del Financial.
	 * OpenUp Ltda. Issue #1363
	 * @author Nicolas Sarlabos - 02/10/2013
	 * @see
	 * @return
	 */
	public boolean accountExists(Properties ctx, String accountNo, String trxName) {
		
		boolean result = false;		
		Connection con = null;
		ResultSet rs = null;
		
		if(accountNo == null) return result;
		
		try{
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select isnull(stnrocta,0) as stnrocta " +
					     " from q_clientes_adempiere " +
					     " where stnrocta = " + accountNo;
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				
				if(rs.getInt("stnrocta") > 0) result = true;
			}
					
			rs.close();
			con.close();			
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		
		return result;
	}	
	
	/***
	 * Metodo que verifica la correspondencia entre la cedula y cuenta recibidas por parametro, y si pertenece a un titular.
	 * base del Financial.
	 * OpenUp Ltda. Issue #1363
	 * @author Nicolas Sarlabos - 02/10/2013
	 * @see
	 * @return
	 */
	public String existsCIForAccount(Properties ctx, String accountNo, String cedula, String trxName) {
		
		String result = null;		
		Connection con = null;
		ResultSet rs = null;
		
		if(accountNo == null || cedula == null) return result;
		
		try{
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			int account = new Integer(accountNo);

			String sql = " select isnull(stderinro,0) as stderinro " +
						 " from q_clientes_adempiere " +
						 " where clicod = " + cedula + " and stnrocta = " + account;
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				
				if(rs.getInt("stderinro") > 0) result = "La cuenta '" + accountNo + "' y cedula '" + cedula + "' no pertenecen a un titular sino a un derivado";
			
			} else result = "La cedula '" + cedula + "' no corresponde a la cuenta '" + accountNo + "'"; 
					
			rs.close();
			con.close();			
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		
		return result;
	}
	
	/***
	 * Seteo datos de direccion de cuenta.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 29/10/2013
	 * @see
	 * @param card
	 */
	public void setCardAddress(MTTCard card){
	
		Connection con = null;
		ResultSet rs = null;

		try{
			
			int solNro = this.getAccountSolNro(card.getAccountNo());
			if (solNro > 0){
				String tipoDir = this.getTipoDirSolNro(solNro);
				if (tipoDir != null){
					if ((tipoDir.equalsIgnoreCase("T")) || (tipoDir.equalsIgnoreCase("O"))) tipoDir = "P";
					
					con = this.getConnectionToSqlServer();
					Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

					String sql = " select top 1 clidrcalle, clidrnpta, clidrnapto, clidrcp, clidrcloc, clidrblock " +
							     " from clidirda where CliCod =" + card.getCedula() +
							     " and clidrtipo = '" + tipoDir + "' " +
							     " order by CliDrFch desc";
					
					rs = stmt.executeQuery(sql);

					if (rs.next()){
						
						String value = null;
						
						String calle = rs.getString("clidrcalle");
						if (calle != null) value = calle.trim();
						
						String nroPuerta = rs.getString("clidrnpta");
						if (nroPuerta != null){
							if (!nroPuerta.trim().equalsIgnoreCase("0"))
								value += " " + nroPuerta.trim();
						}

						String nroApto = rs.getString("clidrnapto");
						if (nroApto != null){
							if (!nroApto.trim().equalsIgnoreCase("0"))
								value += " " + nroApto.trim();
						}

						String block = rs.getString("clidrblock");
						if (block != null){
							if (!block.trim().equalsIgnoreCase("0"))
								value += " " + block.trim();
						}
						
						if (value != null){
							card.setAddress1(value);
						}
						
						String codPostal = rs.getString("clidrcp");
						if (codPostal != null){
							codPostal = codPostal.trim();
							card.setPostal(codPostal);
						}
						
						String codLocalidad = rs.getString("clidrcloc");
						if (codLocalidad != null){
							codLocalidad = codLocalidad.trim();
							sql = " select distinct localidad from uy_deliveypointpostal where codlocalidad ='" + codLocalidad + "' ";
							String nomLocalidad = DB.getSQLValueStringEx(null, sql, new Object[]{});
							if (nomLocalidad != null){
								card.setlocalidad(nomLocalidad);
							}
						}
					} 
							
					rs.close();
					con.close();			

				}

			}

			if (card.is_Changed()){
				card.saveEx();
			}
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}

	}
	
	/***
	 * Setea modo de envio de cuenta recibida.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 29/10/2013
	 * @see
	 * @param card
	 */
	public void setCardDeliveryMode(Properties ctx, MTTCard card){
		
		Connection con = null;
		ResultSet rs = null;

		try{
			
			MTTConfig config = MTTConfig.forValue(ctx, null, "tarjeta");
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select isnull(solenvtarsuc,-1) as solenvtarsuc, isnull(solrageid,-1) as solrageid " +
				     	 " from q_tarjplas_adempiere " +
				     	 " where TplNro =" + card.getTplNro() + 
				     	 " and CliCod =" + card.getclicod();
			
			rs = stmt.executeQuery(sql);

			if (rs.next()){

				if (rs.getInt("solenvtarsuc") == 0){
					card.setCardDestination(X_UY_TT_Card.CARDDESTINATION_DomicilioParticular);
					this.setCardAddress(card);
				}
				else if (rs.getInt("solenvtarsuc") == 8){
					card.setCardDestination(X_UY_TT_Card.CARDDESTINATION_RedPagos);
					card.setSubAgencia(String.valueOf(rs.getInt("solrageid")));
					
					// TEMPORAL RED PAGOS.
					// Comento y sustituyo
					
					//card.setUY_DeliveryPoint_ID(config.getUY_DeliveryPoint_ID());
					
					MDeliveryPoint delPointSubAgencia = MDeliveryPoint.forSubAgencyNo(ctx, card.getSubAgencia(), null);
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
					MDeliveryPoint dp = MDeliveryPoint.forInternalCode(ctx, String.valueOf(rs.getInt("solenvtarsuc")), null);
					if ((dp == null) || (dp.get_ID() <= 0)){
						throw new AdempiereException("No existe Sucursal con Codigo Interno = " + rs.getInt("solenvtarsuc"));
					}
					card.setUY_DeliveryPoint_ID(dp.get_ID());
				}
				
			} 
					
			rs.close();
			con.close();			

			if (card.is_Changed()){
				card.saveEx();
			}
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}

	}
	
	
	/***
	 * Obtiene numero de solicitud para una cuenta desde Financial.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 18/10/2013
	 * @see
	 * @param accountNo
	 * @return
	 */
	public int getAccountSolNro(String accountNo){
		
		Connection con = null;
		ResultSet rs = null;
		
		int value = -1;
		
		try{
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select max(stsoliori) as solori from socios1 where stnrocta=" + accountNo;
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				value = rs.getInt("solori");
			} 
					
			rs.close();
			con.close();			
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		
		return value;
	}
	
	
	/***
	 * Obtiene Tipo de Direccion segun numero de solicitud desde Financial.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 18/10/2013
	 * @see
	 * @param accountNo
	 * @return
	 */
	public String getTipoDirSolNro(int solNro){
		
		Connection con = null;
		ResultSet rs = null;
		
		String value = null;
		
		try{
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select solenvtar from solicitu where solnro =" + solNro + " and solstatus='K' and solgrupo=7";
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				value = rs.getString("solenvtar");
				if (value != null) value = value.trim();
			} 
					
			rs.close();
			con.close();			
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		
		return value;
	}
	
	/***
	 * Obtiene Direccion (concatenando campos) desde Financial para cedula y tipo de direccion.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 18/10/2013
	 * @see
	 * @param accountNo
	 * @return
	 */
	public String getFinancialAddress(String cedula, String tipoDireccion){
		
		Connection con = null;
		ResultSet rs = null;
		
		String value = null;
		
		try{
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select top 1 clidrcalle, clidrnpta, clidrnapto, clidrcp " +
					     " from clidirda where CliCod =" + cedula +
					     " and clidrtipo = '" + tipoDireccion + "' " +
					     " order by CliDrFch desc";
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				String calle = rs.getString("clidrcalle");
				if (calle != null) value = calle.trim();
				
				String nroPuerta = rs.getString("clidrnpta");
				if (nroPuerta != null) value += " " + nroPuerta.trim();

				String nroApto = rs.getString("clidrnapto");
				if (nroApto != null){
					if (!nroApto.trim().equalsIgnoreCase("0"))
						value += " " + nroApto.trim();
				}

			} 
					
			rs.close();
			con.close();			
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		
		return value;
	}
	
	/***
	 * Obtiene codigo postal desde Financial para cedula y tipo de direccion.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 18/10/2013
	 * @see
	 * @param accountNo
	 * @return
	 */
	public String getFinancialPostal(String cedula, String tipoDireccion){
		
		Connection con = null;
		ResultSet rs = null;
		
		String value = null;
		
		try{
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select top 1 clidrcp " +
					     " from clidirda where CliCod =" + cedula +
					     " and clidrtipo = '" + tipoDireccion + "' " +
					     " order by CliDrFch desc";
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				value = rs.getString("clidrcp");
			} 
					
			rs.close();
			con.close();			
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		
		return value;
	}	
	
	/**
	 * OpenUp. Guillermo Brust. 24/06/2013. ISSUE #1068
	 * Se guarda el ajuste en la tabla de ajustes del sqlserver llamada AjuCtaCte
	 * */
	public void guardarAjusteSQLServer(int tablaAjusteID, Object[] data, Connection con, Statement stmt){
						
		String sql = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.sss");
		SimpleDateFormat dfSinHora = new SimpleDateFormat("yyyy-dd-MM 00:00:00.000");
				
		try {				
			String cedula = data[0].toString();
			String cuenta = data[1].toString();
			String tarjeta = data[2].toString();
			Integer moneda = (Integer) data[3];
			BigDecimal importe = (BigDecimal) data[4];
			String codAjuste = data[5].toString();
			String usuarioAjuste = data[6].toString();
			String observaciones = data[7].toString();
			String usuarioAutorizador = data[8].toString();
			Timestamp fechaAjuste = (Timestamp) data[9];
			Timestamp fechaConfirmacion = (Timestamp) data[10];
			int erpAjuste = (Integer) data[11];			
					
			if(observaciones.length() > 120) observaciones = observaciones.substring(0, 119);	
			
			sql = "INSERT INTO AjuCtaCt (EmpCod, AjuNro, CliCod, AjuNCta, AjuDVer, AjuNTarj, MovCod, PTCod, AjuFecEmi," + 
				  " MndCod, AjuImport, AjuDebCre, AjuFecha, CAjCod, AjuTipo, AjuAfeSdo, AjuAplica, AjuMesAnt, AjuConf, AjuGen," +
				  " AjuFecConf, AjuUsu, AjuBaja, AjDvNroC, AjDvNroR, AjDvErr, AjDvObs, AjDvCodErr, AjuOrigen, AjuObs, AjuUsuAut, AjuFHAut," +
				  " AjuUsuAnu, AjuAnuObs, AjuFecHora, MotAjuCod, AjuFecCie, ERPAjuNro, ERPDAjuCod) " + 
				  " VALUES (" +
				  10 + "," + //EmpCod
				  tablaAjusteID + "," + //AjuNro
				  cedula + "," + //CliCod
				  cuenta + "," + //AjuNCta
				  "(select distinct STDigVerNr from q_cuentas_clientes_new where STNroCta = '" + cuenta + "')," + //AjuDVer
				  tarjeta + "," + //AjuNTarj
				  (moneda == 142 ? 351 : 353) + "," + //MovCod
				  "(select distinct PTCod from q_cuentas_clientes_new where STNroCta = '" + cuenta + "')," + //PTCod				 
				  "'1753-01-01 00:00:00'" + "," + //AjuFecEmi
				  (moneda == 142 ? 0 : 1)  + "," + //MndCod
				  importe + "," + //AjuImport
				  "(select DAjuDebCre from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + codAjuste + "')," + //AjuDebCre				  
				  "'" + dfSinHora.format(fechaAjuste) + "'," + //AjuFecha
				  "(select CAjCod from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + codAjuste + "')," + //CAjCod
				  "(select DAjuTipo from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + codAjuste + "')," + //AjuTipo
				  "(select DAjuAfeSdo from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + codAjuste + "')," + //AjuAfeSdo
				  "CASE WHEN (select DAjuAfeSdo from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + codAjuste + "') = 0 THEN 0 " + 
				  " ELSE (select DAjuApSAnt from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + codAjuste + "') END," +  //AjuAplica
				  "(select DAjuMesAnt from FinancialPro.dbo.DEFAJUST where DAjuCod = '" + codAjuste + "')," + //AjuMesAnt				  
				  "'N'," + //AjuConf
				  0 + "," + //AjuGen
				  "'1753-01-01 00:00:00'" + "," + //AjuFecConf.
				  "'" + usuarioAjuste + "', " + //AjuUsu
				  "'1753-01-01 00:00:00'" + "," + //AjuBaja				 			  
				  0 + "," + //AjDvNroC
				  0 + "," + //AjDvNroR
				  0 + "," + //AjDvErr
				  0 + "," + //AjDvObs
				  0 + "," + //AjDvCodErr
				  0 + "," + //AjuOrigen	
				  "'" + observaciones + "'," + //AjuObs
				  "'" + usuarioAutorizador + "', " + //AjuUsuAut
				  "'" + dfSinHora.format(fechaConfirmacion) + "', " + //AjuFHAut					  
				  "''" + "," + //AjuUsuAnu
				  "''" + "," + //AjuAnuObs
				  "'" + df.format(fechaAjuste) + "'," + //AjuFecHora
				  0 + "," + //MotAjuCod				 
				  "'1753-01-01 00:00:00'," + //AjuFecCie
				  erpAjuste + "," +  //ERPAjuNro
				  "'" + codAjuste + "'" + //ERPDAjuCod
				  ")";		 
			
			stmt.executeUpdate(sql);		
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}	
	
	
	/**
	 * OpenUp. Guillermo Brust. 24/06/2013. ISSUE #1068
	 * Se obtiene un ID para el nuevo registro a guardar en la tabla de ajustes en sqlserver
	 * Se pasa la Conexion por parametro para que desde el metodo que se llame a este metodo se pueda manejar una transaccion
	 * 
	 * */
	public int obtenerIdentificadorTablaAjuste(Connection con, Statement stmt){
		
		int retorno = 0;
		ResultSet rs = null;			
		
		try {		
			//Modifico el ID para crear uno nuevo.
			stmt.executeUpdate("UPDATE FinancialPro.dbo.NUMERAFD SET NumFUltNro = NumFUltNro + 1 WHERE NumFCod = 14");			
			
			//Ahora obtengo ese valor que modifique			
			rs = stmt.executeQuery("select NumFUltNro from FinancialPro.dbo.NUMERAFD where NumFCod = 14");

			while (rs.next()) {				
				retorno = rs.getInt("NumFUltNro");
			}
			
			rs.close();			
			
		} catch (Exception e) {					
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){				
				try {					
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}					
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}	
		return retorno;
	}

	/***
	 * Obtiene si la cliente ya firmo la solucitud de tarjeta.
	 * OpenUp Ltda. Issue #2275
	 * @author Leonardo Boccone - 11/06/2014
	 * @see
	 * @param accountNo, cedula
	 * @return boolean
	 */	
	public boolean getSignatureConfirmation(String accountNo, String cedula){
		
		Connection con = null;
		ResultSet rs = null;
		
		String value = "";
		
		try{
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select SolValeSN as vale   from dbo.q_SolicitudAdempiere where STNroCta =" + accountNo + " AND Cedula= "+cedula;
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				value = rs.getString("vale");
			} 
					
			rs.close();
			con.close();			
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		if(value.equalsIgnoreCase("N")){
			return false;
		}
		else{
			return true;
		}
	}
	
	/***
	 * Setea documentos que debe imprimirse para una determinada cuenta recibida.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 27, 2015
	 * @param ctx
	 * @param card
	 */
	public void setPrintDocs(Properties ctx, MTTCard card){

		Connection con = null;
		ResultSet rs = null;

		try{
			
			// Seteo si imprime anexo A solamente dependiendo si la necesidad es nueva
			if (card.getCardAction().equalsIgnoreCase(X_UY_TT_Card.CARDACTION_Nueva)){
				card.setPrintDoc1(true);
			}
			else{
				card.setPrintDoc1(false);
			}
			
			// Etiqueta de sobre siempre va
			card.setPrintDoc3(true);
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			String sql = " select isnull(CedulaSN,'N') as CedulaSN, isnull(RecSueSN,'N') as RecSueSN, " +
					     " isnull(ConsDomSN,'N') as ConsDomSN, isnull(SolValeSN,'N') as SolValeSN, " +
					     " isnull(SolEnvRes,'N') as SolEnvRes, isnull(SolEnvExt,'N') as SolEnvExt, " +
					     " isnull(TplCalleNr, '') as TplCalleNr, isnull(TplDpto, '') as TplDpto, SolNro " +
				     	 " from q_tarjplas_adempiere " +
				     	 " where TplNro =" + card.getTplNro() + 
				     	 " and CliCod =" + card.getclicod();
			
			rs = stmt.executeQuery(sql);

			// Por defecto no se imprimen estos docs
			card.setPrintDoc2(false);  // Anexo b
			card.setPrintDoc4(false);  // Etiqueta Contrato
			
			
			if (rs.next()){

				card.setCedulaSN((rs.getString("CedulaSN").trim().equalsIgnoreCase("N")) ? false : true);
				card.setRecSueSN((rs.getString("RecSueSN").trim().equalsIgnoreCase("N")) ? false : true);
				card.setConsDomSN((rs.getString("ConsDomSN").trim().equalsIgnoreCase("N")) ? false : true);
				card.setSolValeSN((rs.getString("SolValeSN").trim().equalsIgnoreCase("N")) ? false : true);
				card.setsolnro(String.valueOf(rs.getInt("SolNro")));
				card.setSolEnvRes(rs.getString("SolEnvRes"));
				card.setSolEnvExt(rs.getString("SolEnvExt"));
				card.setTplCalleNr(rs.getString("TplCalleNr"));
				card.setTplDpto(rs.getString("TplDpto"));

				// Anexo B
				if ((rs.getString("CedulaSN").trim().equalsIgnoreCase("N")) || (rs.getString("RecSueSN").trim().equalsIgnoreCase("N"))
						|| (rs.getString("ConsDomSN").trim().equalsIgnoreCase("N"))){
				
					card.setPrintDoc2(true); // Anexo B
				}
				
				if (rs.getString("SolValeSN").trim().equalsIgnoreCase("N")){
					card.setPrintDoc4(true);
				}
			}
					
			rs.close();
			con.close();			

			card.saveEx();
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
	}		

	/***
	 * Obtiene y guarda servicios asociados a una determinada cuenta recibida.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 27, 2015
	 * @param ctx
	 * @param card
	 */
	public void setServiciosCuenta(Properties ctx, MTTCard card){

		Connection con = null;
		ResultSet rs = null;

		try{
			
			con = this.getConnectionToSqlServer();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			String sql = " select "
					+ " qq.CliCod, qq.STNroCta, pda.DAUCod, dba.DAUNombre, pda.PDAImp, pda.PDAFchCre, pda.PDATitNom "
					+ " from dbo.PRESDEBA pda, dbo.DEBAUTO dba, q_cuentas_clientes_new qq "
					+ " where pda.DAUCod=dba.DAUCod "
					+ " and pda.CliCod=qq.CliCod and PDAStatus='C' "
					+ " and (PDANCta=qq.STNroCta or PDANCta=0) "
					+ " and qq.clicod =" + card.getclicod() 
					+ " and qq.STNroCta =" + card.getAccountNo();
			
			rs = stmt.executeQuery(sql);

			while (rs.next()){
				
			}
					
			rs.close();
			con.close();			
			
		}	
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}		
		}
		
	}
	
}
