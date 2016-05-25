/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 3, 2015
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.model.MFduConnectionData;
import org.openup.model.MTTCard;

/**
 * org.openup.process - RTTAnexoA
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 3, 2015
 */
public class RTTAnexoA extends SvrProcess {

	private MTTCard card = null;

	private static final String TABLA_MOLDE = "UY_Molde_AnexoA";
	private static final String TABLA_MOLDE_SERVICIOS = "UY_Molde_AnexoA_Serv";
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		try {
			
			int idCard = 0;
			
			// Obtengo parametros y los recorro
			ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName().trim();
				if (name!= null){
					if (name.equalsIgnoreCase("UY_TT_Card_ID")){
						idCard = ((BigDecimal)para[i].getParameter()).intValueExact(); 
					}			
				}
			}
			
			// Obtengo modelo de cuenta tracking
			if (idCard > 0){
				card = new MTTCard(getCtx(), idCard, null);
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		try {
			
			this.deleteOldData();
			
			this.getData();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return "OK";
	}

	/***
	 * Elimina registros anteriores para este usuario en la tabla molde. 
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 3, 2015
	 */
	private void deleteOldData() {
		
		String action = "";
		
		try {
			
			action = " delete from " + TABLA_MOLDE + " where uy_tt_card_id =" + this.card.get_ID();
			DB.executeUpdateEx(action, null);

			action = " delete from " + TABLA_MOLDE_SERVICIOS + " where uy_tt_card_id =" + this.card.get_ID();
			DB.executeUpdateEx(action, null);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/**
	 * Obtiene información y carga la misma en la tabla temporal. 
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 3, 2015
	 */
	private void getData() {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
		
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			StringBuilder insert = new StringBuilder();
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			
			con = this.getFDUConnection(fduData);
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);	

			sql = " SELECT TOP 1 tj.SolNro, tj.CliCod, tj.SolVenCod, tj.TPlCuenta, tj.GrpCtaCte, "
					+ " CASE WHEN (DATEDIFF(year,tj.CliNacio,GETDATE())>150) THEN NULL "
					+ " ELSE tj.CliNacio END AS CliNacio, "
					+ " CASE WHEN tj.CliEstCiv ='C' THEN 'CASADO/A' "
					+ " WHEN tj.CliEstCiv ='U' THEN 'VIUDO/A' "
					+ " WHEN tj.CliEstCiv ='D' THEN 'DIVORCIADO/A' "
					+ " ELSE 'SOLTERO/A' END AS CliEstCiv, "
					+ " ISNULL(tj.CliHijos,0) AS CliHijos, tj.CliViviend, isnull(tj.grupocta,'') as grupocta, "
					+ " CASE WHEN tj.CliVehicul=0 THEN 'NO' "
					+ " WHEN tj.CliVehicul=1 THEN 'SI' "
					+ " END AS CliVehicul, "
					+ " ISNULL(tj.CliOtCred,'-') AS CliOtCred, tj.ref1_nombre, tj.ref1_apellido, tj.ref1_vinculo, tj.ref1_telefono, tj.ref2_nombre, tj.ref2_apellido, "
					+ " tj.ref2_vinculo, tj.ref2_telefono, tj.ocupacion, tj.justificacion, tj.empresa, tj.dom_laboral, tj.dom_laboral_nro, tj.dom_laboral_loc, "
					+ " tj.tel_laboral, tj.RUTempresa, tj.dom_laboral_empleado, isnull(tj.STLimCred,0) as STLimCred, "
					+ " tj.ref1_direccion, tj.ref1_celular, tj.ref2_direccion, tj.ref2_celular, "
					+ " CASE WHEN (DATEDIFF(year,tj.ingreso_laboral,GETDATE())>150) THEN NULL "
					+ " ELSE tj.ingreso_laboral END AS ingreso_laboral, "
					+ " tj.cargo, tj.f_solicitud, ISNULL(tj.nominal,0) AS nominal, ISNULL(tj.liquido,0) AS liquido, tj.ConyCed, tj.ConyNom1, tj.ConyCliApe, "
					+ " CASE WHEN (DATEDIFF(year,tj.ConyNacim,GETDATE())>150) THEN NULL "
					+ " ELSE tj.ConyNacim END AS ConyNacim, "
					+ " CASE WHEN cl.CliSexo=0 THEN 'MAS' "
					+ " WHEN cl.CliSexo=1 THEN 'FEM' "
					+ " ELSE '' END AS CliSexo, cl.CliNom1, cl.CliNom2, cl.CliApe1, Cl.CliApe2, isnull(Cl.CliACargo,0) as CliACargo, Cl.CliDirNroP, "
					+ " Cl.CliDirApto, Cl.CliDirBloc, Cl.CliDirPsje, Cl.CliECalle1, Cl.CliECalle2, Cl.CliDirManzana, "
					+ " Cl.CliDirSolar, Cl.CliDirBarr, Cl.CliDirObs "
					+ " FROM q_tarjplas_adempiere tj "
					+ " LEFT OUTER JOIN q_clientes_adempiere cl ON tj.CliCod = cl.CliCod "
					+ " WHERE TPlCuenta = " + this.card.getAccountNo();
				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				
				insert.append(" insert into " + TABLA_MOLDE + 
						" (ad_user_id, uy_tt_card_id, codvendedor, gpocierre, clinacio, cliestciv, clicanthijos, clivivienda, " +
						" clivehiculo, cliotcred, ref1_nombre, ref1_apellido, ref1_vinculo, ref1_telefono, " +
						" ref2_nombre, ref2_apellido, ref2_vinculo, ref2_telefono, " +
						" cliempresa, clidomlaboral, clidomlaboralnro, cliloclaboral, clitellaboral, clifchinglaboral," +
						" clicargolaboral, cliliquidolaboral, clinominallaboral," + 
						" conynom1, conycliape, conynacim, conycedid, FchDeImpresion, fchsolicitud, ocupacion, justificacion,"
						+ "solnro, clicod, clisexo, clinom1, clinom2, cliape1, cliape2, rutempresa, domlabemp, stlimcred," 
						+ " CliACargo, CliDirNroP, CliDirApto, CliDirBloc, CliDirPsje, CliECalle1, CliECalle2, CliDirManzana, "
					    + " CliDirSolar, CliDirBarr, CliDirObs, ref1_domicilio, ref1_celular, ref2_domicilio, ref2_celular" + ") ");
				
				insert.append(" values (" + this.getAD_User_ID() + "," + this.card.get_ID() + ",");
				insert.append("'" + rs.getString("SolVenCod").trim() + "'," + ((rs.getInt("GrpCtaCte") > 0) ? "'" + rs.getInt("GrpCtaCte") + " " + rs.getString("grupocta").trim() + "'" : null) + ",");
				insert.append(((rs.getTimestamp("CliNacio") != null) ? "'" + rs.getTimestamp("CliNacio") + "'" : null) + ",");
				insert.append(((rs.getString("CliEstCiv") != null) ? "'" + rs.getString("CliEstCiv").trim() + "'" : null) + ",");
				insert.append(rs.getInt("CliHijos") + ",");
				insert.append(((rs.getString("CliViviend") != null) ? "'" + rs.getString("CliViviend").trim() + "'" : null) + ",");			
				insert.append(((rs.getString("CliVehicul") != null) ? "'" + rs.getString("CliVehicul").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliOtCred") != null) ? "'" + rs.getString("CliOtCred").trim() + "'" : null) + ",");			
				insert.append(((rs.getString("ref1_nombre") != null) ? "'" + rs.getString("ref1_nombre").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ref1_apellido") != null) ? "'" + rs.getString("ref1_apellido").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ref1_vinculo") != null) ? "'" + rs.getString("ref1_vinculo").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ref1_telefono") != null) ? "'" + rs.getString("ref1_telefono").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ref2_nombre") != null) ? "'" + rs.getString("ref2_nombre").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ref2_apellido") != null) ? "'" + rs.getString("ref2_apellido").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ref2_vinculo") != null) ? "'" + rs.getString("ref2_vinculo").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ref2_telefono") != null) ? "'" + rs.getString("ref2_telefono").trim() + "'" : null) + ",");							
				insert.append(((rs.getString("empresa") != null) ? "'" + rs.getString("empresa").trim() + "'" : null) + ",");
				insert.append(((rs.getString("dom_laboral") != null) ? "'" + rs.getString("dom_laboral").trim() + "'" : null) + ",");
				insert.append(((rs.getString("dom_laboral_nro") != null) ? "'" + rs.getString("dom_laboral_nro").trim() + "'" : null) + ",");
				insert.append(((rs.getString("dom_laboral_loc") != null) ? "'" + rs.getString("dom_laboral_loc").trim() + "'" : null) + ",");
				insert.append(((rs.getString("tel_laboral") != null) ? "'" + rs.getString("tel_laboral").trim() + "'" : null) + ",");
				insert.append(((rs.getTimestamp("ingreso_laboral") != null) ? "'" + rs.getTimestamp("ingreso_laboral") + "'" : null) + ",");
				insert.append(((rs.getString("cargo") != null) ? "'" + rs.getString("cargo").trim() + "'" : null) + ",");
				insert.append(((rs.getBigDecimal("nominal").equals(new BigDecimal(0))) ? null : rs.getBigDecimal("nominal")) + ",");
				insert.append(((rs.getBigDecimal("liquido").equals(new BigDecimal(0))) ? null : rs.getBigDecimal("liquido")) + ",");
				insert.append(((rs.getString("ConyNom1")!=null) ? "'" + rs.getString("ConyNom1").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ConyCliApe") != null) ? "'" + rs.getString("ConyCliApe").trim() + "'" : null) + ",");
				insert.append(((rs.getTimestamp("ConyNacim") != null) ? "'" + rs.getTimestamp("ConyNacim") + "'" : null) + ",");
				insert.append(((rs.getInt("ConyCed") > 0) ? rs.getInt("ConyCed"):null) + ",");
				insert.append("'" + today + "',");
				insert.append(((rs.getTimestamp("f_solicitud") != null) ? "'" + rs.getTimestamp("f_solicitud") + "'" : null) + ",");
				insert.append(((rs.getString("ocupacion") != null) ? "'" + rs.getString("ocupacion").trim() + "'" : null) + ",");
				insert.append(((rs.getString("justificacion") != null) ? "'" + rs.getString("justificacion").trim() + "'" : null) + ",");
				insert.append(rs.getInt("SolNro") + ",");
				insert.append("'" + rs.getString("CliCod") + "',");
				insert.append(((rs.getString("CliSexo") != null) ? "'" + rs.getString("CliSexo").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliNom1") != null) ? "'" + rs.getString("CliNom1").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliNom2") != null) ? "'" + rs.getString("CliNom2").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliApe1") != null) ? "'" + rs.getString("CliApe1").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliApe2") != null) ? "'" + rs.getString("CliApe2").trim() + "'" : null) + ",");
				insert.append(((rs.getString("rutempresa") != null) ? "'" + rs.getString("rutempresa").trim() + "'" : null) + ",");
				insert.append(((rs.getString("dom_laboral_empleado") != null) ? "'" + rs.getString("dom_laboral_empleado").trim() + "'" : null) + ",");
				insert.append(((rs.getBigDecimal("stlimcred").equals(new BigDecimal(0))) ? null : rs.getBigDecimal("stlimcred")) + ",");
				insert.append(((rs.getInt("CliACargo") > 0) ? rs.getInt("CliACargo"):null) + ",");
				insert.append(((rs.getString("CliDirNroP") != null) ? "'" + rs.getString("CliDirNroP").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliDirApto") != null) ? "'" + rs.getString("CliDirApto").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliDirBloc") != null) ? "'" + rs.getString("CliDirBloc").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliDirPsje") != null) ? "'" + rs.getString("CliDirPsje").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliECalle1") != null) ? "'" + rs.getString("CliECalle1").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliECalle2") != null) ? "'" + rs.getString("CliECalle2").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliDirManzana") != null) ? "'" + rs.getString("CliDirManzana").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliDirSolar") != null) ? "'" + rs.getString("CliDirSolar").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliDirBarr") != null) ? "'" + rs.getString("CliDirBarr").trim() + "'" : null) + ",");
				insert.append(((rs.getString("CliDirObs") != null) ? "'" + rs.getString("CliDirObs").trim() + "'" : null) + ",");
				insert.append(((rs.getString("ref1_direccion") != null) ? "'" + rs.getString("ref1_direccion").trim() + "'" : null) + ",");
				insert.append(((rs.getInt("ref1_celular") > 0) ? "'" + rs.getInt("ref1_celular") + "'" : null) + ",");
				insert.append(((rs.getString("ref2_direccion") != null) ? "'" + rs.getString("ref2_direccion").trim() + "'" : null) + ",");
				insert.append(((rs.getInt("ref2_celular") > 0) ? "'" + rs.getInt("ref2_celular") + "'" : null) + ")");

			}

			rs.close();
			con.close();			

			// Ejecuto insert en table molde
			if (!insert.toString().equalsIgnoreCase("")){
				DB.executeUpdateEx(insert.toString(), null);	
			}
			
			// Cargo molde de servicios si obtuve datos de cuenta
			if (!insert.toString().equalsIgnoreCase("")){
				this.getServicios();
			}
			
		} 
		catch (Exception e) {
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

	}
	
	/***
	 * Obtiene servicios para la cuenta desde financial y guardo info en tabla temporal para el reporte.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Sep 25, 2015
	 */
	private void getServicios() {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			
			con = this.getFDUConnection(fduData);
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);	

			sql = " select * "
				+ " from q_servicios_cliente_adempiere	"
				+ " where Cuenta = " + this.card.getAccountNo();
				
			rs = stmt.executeQuery(sql);
			
			int rowCount = 0;
			
			while (rs.next()) {
				
				StringBuilder insert = new StringBuilder();
			
				rowCount++;
				
				insert.append(" insert into " + TABLA_MOLDE_SERVICIOS + 
						" (ad_user_id, uy_tt_card_id, codservicio, servicio, importe, fechaalta, beneficiario, vigencia, cedula," 
						+ "cuenta, pagina) ");
				
				insert.append(" values (" + this.getAD_User_ID() + "," + this.card.get_ID() + ",");
				insert.append("'" + rs.getInt("CodServicio") + "',");
				insert.append("'" + rs.getString("Servicio") + "',");
				
				BigDecimal importe = rs.getBigDecimal("Importe");
				if (importe == null) importe = Env.ZERO;
				insert.append(importe + ",");
				insert.append(((rs.getTimestamp("fechaalta") != null) ? "'" + rs.getTimestamp("fechaalta") + "'" : null) + ",");
				insert.append(((rs.getString("beneficiario") != null) ? "'" + rs.getString("beneficiario").trim() + "'" : null) + ",");			
				insert.append(((rs.getString("vigencia") != null) ? "'" + rs.getString("vigencia").trim() + "'" : null) + ",");
				insert.append("'" + rs.getInt("cedula") + "',");
				insert.append("'" + rs.getInt("cuenta") + "',");
				
				int pagina = 1;
				if (rowCount > 3) pagina = 2;
				
				insert.append(pagina + ")");

				// Ejecuto insert en table molde
				DB.executeUpdateEx(insert.toString(), null);	

			}

			rs.close();
			con.close();			
			
		} 
		catch (Exception e) {
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

		
	}

	/***
	 * Realiza y retorna una nueva conexion a base de datos origen de la información.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 3, 2015
	 * @param fduData
	 * @return
	 * @throws Exception
	 */
	private Connection getFDUConnection(MFduConnectionData fduData){
		
		Connection retorno = null;

		String connectString = ""; 

		try {
			
			if (fduData != null){
				
				connectString = "jdbc:sqlserver://" + fduData.getserver_ip() + "\\" + fduData.getServer() + 
								";databaseName=" + fduData.getdatabase_name() + ";user=" + fduData.getuser_db() + 
								";password=" + fduData.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, fduData.getuser_db(), fduData.getpassword_db());
			}	
			
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return retorno;
	}
	
}
