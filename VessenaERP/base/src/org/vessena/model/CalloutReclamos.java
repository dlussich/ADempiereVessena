/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/02/2013
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MTax;
import org.compiere.util.Env;

/**
 * org.openup.model - CalloutReclamos
 * OpenUp Ltda. Issue #285 
 * Description: Gestion de reclamos
 * @author Gabriel Vila - 05/02/2013
 * @see
 */
public class CalloutReclamos extends CalloutEngine {

	/**
	 * Constructor.
	 */
	public CalloutReclamos() {
	}
	
	/***
	 * En ingreso de reclamos, al indicar un tema, se debe cargar punto de resolucion y area.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 05/02/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCause(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int causeID = ((Integer)value).intValue();
		
		if (causeID <= 0) return "";

		MRCause cause = new MRCause(Env.getCtx(), causeID, null);
		
		if (cause.get_ID() > 0){

			if (cause.getUY_R_Area_ID() > 0)
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_UY_R_Area_ID, cause.getUY_R_Area_ID());

			if (cause.getUY_R_PtoResolucion_ID() > 0)
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_UY_R_PtoResolucion_ID, cause.getUY_R_PtoResolucion_ID());
			
			
			if (cause.getPriorityBase() != null){
				if (!cause.getPriorityBase().equalsIgnoreCase("")){
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_PriorityBase, cause.getPriorityBase());
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_PriorityManual, cause.getPriorityBase());					
				}
			}
		}
		
		return "";
	}

	/***
	 * Al cambiar la cedula de identidad debo obtener datos del cliente desde
	 * base del Financial.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 06/03/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setCI(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		Connection con = null;
		ResultSet rs = null;

		
		if (value == null) return "";

		String cedula = value.toString().trim();
		
		if (cedula.equalsIgnoreCase("")) return "";

		String retorno = "";
		
		try{
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select stapenom,stcallenro,isnull(stdepto,'') as stdepto, " +
						 " isnull(clitel,'NO TIENE') as clitel, isnull(clicelular,'NO TIENE') as clicelular, " +
						 " isnull(stcodpos,0) as stcodpos, isnull(stnrocta,0) as stnrocta, isnull(climail,'NO TIENE - SOLICITAR !!') as climail, " +
						 " stnrotarj, stvenctarj, isnull(stlimcred,0) as stlimcred,  isnull(lccod,'') as producto " +
					     " from q_clientes_adempiere " +
					     " where clicod=" + cedula;
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				
				int codPostal = rs.getInt("stcodpos");
				int nroCuenta = rs.getInt("stnrocta");
				
				String strCodPostal = (codPostal > 0) ? String.valueOf(codPostal) : "NO TIENE";				
				String strNroCuenta = (nroCuenta > 0) ? String.valueOf(nroCuenta) : "NO TIENE";
				String apto = "";
				if (rs.getString("stdepto") != null){
					if (!rs.getString("stdepto").trim().equalsIgnoreCase("")){
						apto = " - Apto.: " + rs.getString("stdepto");
					}
				}
	
				String email = rs.getString("climail");
				if ((email == null) || (email.trim().equalsIgnoreCase(""))){
					email = "NO TIENE - SOLICITAR !!" ;
				}
				
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_CustomerName, rs.getString("stapenom"));
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Direction, rs.getString("stcallenro") + apto); 
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Telephone, rs.getString("clitel"));
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Mobile, rs.getString("clicelular"));
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Postal, strCodPostal);
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_AccountNo, strNroCuenta);
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_EMail, email);
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_NombreTitular, rs.getString("stapenom"));
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_NroTarjetaTitular, rs.getString("stnrotarj"));
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_DueDateTitular, rs.getString("stvenctarj"));
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_LimCreditoTitular, rs.getBigDecimal("stlimcred"));
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_ProductoAux, rs.getString("producto"));
				
			}
			else{
				throw new AdempiereException("No hay registro de Cliente con ese Numero de Cedula.");
			}
			
			rs.close();
			con.close();		
			
			// Valido si este cliente tiene incidencias pendientes. En caso de tenerlas aviso.
			if (MRReclamo.havePendings(ctx, cedula)){
				//ADialog.warn(0, null, null, "Este Cliente tiene Incidencias Pendientes");
				retorno = "Este Cliente tiene Incidencias Pendientes";
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
		
		return retorno;
	}
	
	
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
	 * Al cambiar el check de Incidencia Interna debo refrescar tipo, tema y subtemas.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 17/04/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeInternalIssue(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_uy_r_subcause_id_1, -1);
		mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_uy_r_subcause_id_2, -1);
		mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_uy_r_subcause_id_3, -1);
		mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_uy_r_subcause_id_4, -1);
		mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_UY_R_Cause_ID, -1);
		mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_UY_R_Type_ID, -1);
		
		mTab.dataSave(true);
		
		return "";
	}
	
	/***
	 * Al cambiar a la accion de la gestion actualizo el valor del campo ReclamoAccionType para que sea mas
	 * facil ocultar o mostrar campos en la pestaña.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 24/06/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeGestionAction(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int uyRActionID = (Integer)value; 
		if (uyRActionID <= 0) return "";
		
		MRAction action = new MRAction(ctx, uyRActionID, null); 
		
		mTab.setValue(X_UY_R_Gestion.COLUMNNAME_ReclamoAccionType, action.getValue().trim());
		
		//mTab.dataSave(true);
		
		return "";
	}	
	
	/***
	 * Al cambiar a la accion de la notificacion actualizo el valor del campo ReclamoAccionType para que sea mas
	 * facil ocultar o mostrar campos en la pestaña.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 24/06/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeNotificaAction(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int uyRActionID = (Integer)value; 
		if (uyRActionID <= 0) return "";
		
		MRAction action = new MRAction(ctx, uyRActionID, null); 
		
		mTab.setValue(X_UY_R_Notification.COLUMNNAME_NotificationActionType, action.getValue().trim());
		
		//mTab.dataSave(true);
		
		return "";
	}	
	/***
	 * En la gestion de inciencias, al momento de cargarse solicitud de ajustes, 
	 * al cambiar motivo de ajuste se debe setear si este motivo aplica iva o no.
	 * OpenUp Ltda. Issue #740 
	 * @author Gabriel Vila - 18/04/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeAjusteType(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int ajusteID = ((Integer)value).intValue();
		
		if (ajusteID <= 0) return "";

		MRAjuste ajuste = new MRAjuste(Env.getCtx(), ajusteID, null);
 
		mTab.setValue(X_UY_R_HandlerAjuste.COLUMNNAME_AplicaIVA, ajuste.isAplicaIVA());
		mTab.setValue(X_UY_R_HandlerAjuste.COLUMNNAME_DrCr, ajuste.getDrCr());
		
		return "";
	}
	
	
	/***
	 * Al modificar monto o cantidad cuotas de una linea de solicitud de ajuste,
	 * debo setear impuesto, neto y total.
	 * OpenUp Ltda. Issue #740 
	 * @author Gabriel Vila - 18/04/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setAmt(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		BigDecimal amt = Env.ZERO, qty = Env.ZERO;
		boolean aplicaIVA = true;
		
		if (mField.getColumnName().equals(X_UY_R_HandlerAjuste.COLUMNNAME_Amount)){
			amt = (BigDecimal) value;
			qty = ((BigDecimal)mTab.getValue(X_UY_R_HandlerAjuste.COLUMNNAME_QtyQuote));
			aplicaIVA = (Boolean)mTab.getValue(X_UY_R_HandlerAjuste.COLUMNNAME_AplicaIVA);
		}			
		else if (mField.getColumnName().equals(X_UY_R_HandlerAjuste.COLUMNNAME_QtyQuote)){
			qty = (BigDecimal) value;
			amt = ((BigDecimal)mTab.getValue(X_UY_R_HandlerAjuste.COLUMNNAME_Amount)).setScale(2, BigDecimal.ROUND_HALF_UP);
			aplicaIVA = (Boolean)mTab.getValue(X_UY_R_HandlerAjuste.COLUMNNAME_AplicaIVA);
		}
		else if (mField.getColumnName().equals(X_UY_R_HandlerAjuste.COLUMNNAME_AplicaIVA)){
			aplicaIVA = (Boolean)value;
			qty = ((BigDecimal)mTab.getValue(X_UY_R_HandlerAjuste.COLUMNNAME_QtyQuote));
			amt = ((BigDecimal)mTab.getValue(X_UY_R_HandlerAjuste.COLUMNNAME_Amount)).setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		int taxID = 1000007;  // Excento
		if (aplicaIVA){
			taxID = 1000004; // Basico
		}
		mTab.setValue(X_UY_R_HandlerAjuste.COLUMNNAME_C_Tax_ID, taxID);
		MTax tax = new MTax(ctx, taxID, null);
		
		BigDecimal netAmt = amt.multiply(qty).setScale(2, RoundingMode.HALF_UP);
		BigDecimal taxAmt = netAmt.multiply(tax.getRate().divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP));
		BigDecimal totalAmt = netAmt.add(taxAmt);
		
		mTab.setValue(X_UY_R_HandlerAjuste.COLUMNNAME_LineNetAmt, netAmt);
		mTab.setValue(X_UY_R_HandlerAjuste.COLUMNNAME_TaxAmt, taxAmt);
		mTab.setValue(X_UY_R_HandlerAjuste.COLUMNNAME_LineTotalAmt, totalAmt);
		
		return "";
	}

	/***
	 * Al cambiar la cedula de identidad debo obtener datos del cliente desde
	 * base del Financial. Se puede dar el caso que una persona este mas de una vez
	 * en la tabla de clientes, ya sea como titular o como derivado de n tarjetas.
	 * OpenUp Ltda. Issue #112 
	 * @author Gabriel Vila - 03/07/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setCICuenta(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		Connection con = null;
		ResultSet rs = null;

		
		if (value == null) return "";

		String cedula = value.toString().trim();
		
		if (cedula.equalsIgnoreCase("")) return "";

		String retorno = "";
		
		try{
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select stapenom,stcallenro,isnull(stdepto,'') as stdepto, " +
						 " isnull(clitel,'NO TIENE') as clitel, isnull(clicelular,'NO TIENE') as clicelular, " +
						 " isnull(stcodpos,0) as stcodpos, isnull(stnrocta,0) as stnrocta, isnull(climail,'NO TIENE - SOLICITAR !!') as climail, " +
						 " stnrotarj, stvenctarj, isnull(stlimcred,0) as stlimcred,  isnull(lccod,'') as producto, " +
						 " isnull(stderinro,0) as sttiposoc, isnull(nombre,'') as nombreapellido, " +
						 " GrpCtaCte, GAFCOD, MLCod, Tasas, Cargos, Parametros " +
					     " from q_clientes_adempiere " +
					     " where clicod=" + cedula +
					     " order by sttiposoc ";
			
			rs = stmt.executeQuery(sql);
			
			boolean firstRecord = true;
			boolean hayDatos = false;
			int contCuentas = 0;
			int defaultCedulaCuentaID = -1;
			
			while (rs.next()){
				
				hayDatos = true;

				// En el primer registro para esta cedula despliego los datos generales y comunes al cliente
				// tanto si es titular como derivado.
				if (firstRecord){
					firstRecord = false;

					int codPostal = rs.getInt("stcodpos");
					
					String strCodPostal = (codPostal > 0) ? String.valueOf(codPostal) : "NO TIENE";
					String apto = "";
					if (rs.getString("stdepto") != null){
						if (!rs.getString("stdepto").trim().equalsIgnoreCase("")){
							apto = " - Apto.: " + rs.getString("stdepto");
						}
					}
					
					String email = rs.getString("climail");
					if ((email == null) || (email.trim().equalsIgnoreCase(""))){
						email = "NO TIENE - SOLICITAR !!" ;
					}

					String celular = rs.getString("clicelular");
					if ((celular == null) || (celular.trim().equalsIgnoreCase(""))){
						celular = "NO TIENE" ;
					}

					String telefono = rs.getString("clitel");
					if ((telefono == null) || (telefono.trim().equalsIgnoreCase(""))){
						telefono = "NO TIENE" ;
					}
					
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_CustomerName, rs.getString("stapenom"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Direction, rs.getString("stcallenro") + apto); 
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Telephone, telefono);
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Mobile, celular);
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Postal, strCodPostal);
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_EMail, email);
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Name, rs.getString("nombreapellido"));
				}
				
				if (rs.getInt("stnrocta") > 0){
					
					String accountNo = String.valueOf(rs.getInt("stnrocta")).trim();
					
					MRCedulaCuenta cedcta = MRCedulaCuenta.forCedulaCuenta(ctx, cedula, accountNo, null);
					if ((cedcta == null) || (cedcta.get_ID() <= 0)){
						cedcta = new MRCedulaCuenta(ctx, 0, null);
						cedcta.setValue(cedula);
						cedcta.setAccountNo(accountNo);
						cedcta.saveEx();
					}
					
					defaultCedulaCuentaID = cedcta.get_ID();
					contCuentas++;
				}
			}
			
			rs.close();
			con.close();
			
			if (!hayDatos){
				throw new AdempiereException("No hay registro de Cliente con ese Numero de Cedula.");
			}

			mTab.getField("UY_R_CedulaCuenta_ID").refreshLookup();
			
			if (contCuentas > 1){
				retorno = "Cliente con mas de una CUENTA. Por favor SELECCIONE LA CORRECTA !!!\n";
			}
			else{
				mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_UY_R_CedulaCuenta_ID, defaultCedulaCuentaID);
			}
			
			// Valido si este cliente tiene incidencias pendientes. En caso de tenerlas aviso.
			if (MRReclamo.havePendings(ctx, cedula)){
				//ADialog.warn(0, null, null, "Este Cliente tiene Incidencias Pendientes");
				retorno += "Cliente con Incidencias Pendientes de Resolucion";
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
		
		return retorno;
	}

	
	/***
	 * Al cambiar la cuenta del cliente debo obtener datos del cliente desde
	 * base del Financial. Se puede dar el caso que una persona este mas de una vez
	 * en la tabla de clientes, ya sea como titular o como derivado de n tarjetas.
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 15/07/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeCuenta(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		Connection con = null;
		ResultSet rs = null;

		
		if (value == null) return "";

		int idCuenta = (Integer)value;
		
		if (idCuenta<= 0) return "";

		String retorno = "";
		
		try{
			
			// Debo guardar los datos de la pestaña para que pueda obtener el ID del reclamo
			mTab.dataSave(true);
			
			MRCedulaCuenta cedulacuenta = new MRCedulaCuenta(ctx, idCuenta, null);
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);
			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			String sql = " select stapenom, stnrotarj, stvenctarj, isnull(stlimcred,0) as stlimcred,  isnull(lccod,'') as producto, " +
						 " isnull(stderinro,0) as sttiposoc, isnull(nombre,'') as nombreapellido, " +
						 " GrpCtaCte, GAFCOD, MLCod, Tasas, Cargos, Parametros, DigitoVerif, " +
						 " isnull(validez,0) as validez, isnull(gafnom,'') as gafnom, coeficiente, " +
						 " letracompra, isnull(envioresumen,'N') as envioresumen, isnull(envioext,'N') as envioext, " +
						 " isnull(boletinada,'N') as boletinada, isnull(renovacion,'N') as renovacion, " +
						 " stcoefade, stcxservbo " +
					     " from q_clientes_adempiere " +
					     " where stnrocta =" + cedulacuenta.getAccountNo();  
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){

				if (rs.getInt("sttiposoc") == 0){
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_NombreTitular, rs.getString("stapenom"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_NroTarjetaTitular, rs.getString("stnrotarj"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_DueDateTitular, rs.getString("stvenctarj"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_LimCreditoTitular, rs.getBigDecimal("stlimcred"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_ProductoAux, rs.getString("producto"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_GrpCtaCte, rs.getInt("GrpCtaCte"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_GAFCOD, rs.getInt("GAFCOD"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_MLCod, rs.getString("MLCod"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Tasas, rs.getInt("Tasas"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Cargos, rs.getInt("Cargos"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Parametros, rs.getInt("Parametros"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_DigitoVerificador, rs.getInt("DigitoVerif"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_GAFNOM, rs.getString("gafnom"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Coeficiente, rs.getBigDecimal("coeficiente"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_LetraCompra, rs.getString("letracompra"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_EnvioResumen, rs.getString("envioresumen"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_EnvioExt, rs.getString("envioext"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Boletinada, rs.getString("boletinada"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Renovacion, rs.getString("renovacion"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_CoefAdelanto, rs.getBigDecimal("stcoefade"));
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_PorcBonif, rs.getBigDecimal("stcxservbo"));
					
					String vigencia ="";
					if (rs.getInt("validez") == 12){
						vigencia = "Anual";
					}
					else if (rs.getInt("validez") == 36){
						vigencia = "Trienal";
					}
					mTab.setValue(X_UY_R_Reclamo.COLUMNNAME_Validez, vigencia);
				}

				// Guardo datos de todo socio asi sea titular para mostrar en grilla ahora no solo es para mostrar derivados.
				MRDerivado derivado = new MRDerivado(ctx, 0, null);
				derivado.setUY_R_Reclamo_ID((Integer)mTab.getValue(X_UY_R_Reclamo.COLUMNNAME_UY_R_Reclamo_ID));
				derivado.setAccountNo(cedulacuenta.getAccountNo());
				derivado.setName(rs.getString("stapenom"));
				derivado.setNroTarjetaTitular(rs.getString("stnrotarj"));
				derivado.setDueDateTitular(rs.getString("stvenctarj"));
				derivado.setGrpCtaCte(rs.getInt("GrpCtaCte"));
				derivado.setGAFCOD(rs.getInt("GAFCOD"));
				derivado.setMLCod(rs.getString("MLCod"));
				derivado.setTipoSocio(rs.getInt("sttiposoc"));
				derivado.setDigitoVerificador(rs.getInt("DigitoVerif"));
				derivado.saveEx();
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
		
		return retorno;
	}

	/***
	 * Cuando escribo comentarios en gestiones y notificaciones, no se porque a veces
	 * al hacer click en el boton de ejecutar accion, me borra el comentario !!!
	 * OpenUp Ltda. Issue #281 
	 * @author Hp - 17/07/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String updateDescription(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) 
			return "";
		
		mTab.setValue("Description", " ");
		mTab.dataSave(true);
		
		return "";
	}
	
	/***
	 * Setea campos de informacion asociados a subagencia seleccionada.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 27, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setSubAgenciaInfo(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {

		if (value == null) return "";
		
		int id = (Integer)value;
		
		if (id<= 0) return "";

		// Instancio modelo de punto de distribucion correspondiente a subagencia seleccionada
		MDeliveryPoint delPoint = new MDeliveryPoint(ctx, id, null);
		
		if (delPoint.get_ID() <= 0) return "";
		
		mTab.setValue(X_UY_R_Gestion.COLUMNNAME_Address2, delPoint.getAddress1());
		
		if (delPoint.getUY_Departamentos_ID() > 0) mTab.setValue(X_UY_R_Gestion.COLUMNNAME_UY_Departamentos_ID, delPoint.getUY_Departamentos_ID());
		if (delPoint.getUY_Localidades_ID() > 0) mTab.setValue(X_UY_R_Gestion.COLUMNNAME_UY_Localidades_ID, delPoint.getUY_Localidades_ID());
		
		mTab.setValue(X_UY_R_Gestion.COLUMNNAME_Telephone, delPoint.getTelephone());
		mTab.setValue(X_UY_R_Gestion.COLUMNNAME_DeliveryTime, delPoint.getDeliveryTime());
		mTab.setValue(X_UY_R_Gestion.COLUMNNAME_DeliveryTime2, delPoint.getDeliveryTime2());
		mTab.setValue(X_UY_R_Gestion.COLUMNNAME_DeliveryTime3, delPoint.getDeliveryTime3());
		
		return "";

	}
		
	
}
