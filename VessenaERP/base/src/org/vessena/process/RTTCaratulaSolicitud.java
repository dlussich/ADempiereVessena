/**
 * 
 */
package org.openup.process;

import org.compiere.util.TimeUtil;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFduConnectionData;
import org.openup.model.MTTCard;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;

import java.sql.Connection;

/**
 * @author sylvie.bouissa
 *
 */
public class RTTCaratulaSolicitud extends SvrProcess {

	// Variables para recibir filtros del reporte
		//private Timestamp fechaHoy;
		private int uyTTCardID = 0, adUserID = 0;
		private MTTCard mCuenta = null;	
		//private String idReporte = ""; 
		private static final String TABLA_MOLDE = "UY_Molde_TTCaratulaSolicitud";
	
	/**
	 * 
	 */
	public RTTCaratulaSolicitud() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		// Parametro para id de reporte
		//ProcessInfoParameter paramIDReporte = null;
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_TT_Card_ID")){
					uyTTCardID = ((BigDecimal)para[i].getParameter()).intValueExact(); 
				}			
			}
		}
	
		// Usuario
		this.adUserID = getAD_User_ID();
	
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if(this.uyTTCardID>0){
			this.mCuenta = new MTTCard(getCtx(), this.uyTTCardID, null);
			if(this.mCuenta!=null){
				// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
				this.deleteInstanciasViejas();

				// Cargo informacion a reportear en tabla molde
				this.getData();
				
				// Retorno con exito.
				return "ok";
			}
				
		}
		return "no";
	}

	/**
	 * Delete de registros de instancias viejas de este reporte para el usuario conectado
	 */
	private void deleteInstanciasViejas() {
		String sql = "";
		try{
			sql = " DELETE FROM " + TABLA_MOLDE + 
				  " WHERE ad_user_id =" + this.adUserID;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	
	/**
	 * OpenUp. Sylvie Bouissa. 05/02/2015. Issue #3273.
	 * @throws Exception 
	 */
	private void getData() throws Exception{
		
		//Necesario para conectarme a financialpro sqlserver
		Connection con = null;
		ResultSet rs = null;
		//Trx trans = null;
		String insert = ""; String sql=null;
		try {			
			
			//MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			MFduConnectionData fduData = new MFduConnectionData(Env.getCtx(), 1000011, null);
			con = this.getFDUConnection(fduData);

			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);	

			//Se obtienen los datos "extras" para el titular de la cuenta para luego ser guardados en la tabla molde
			 
			 sql =  " SELECT TOP 1 " +
					" SolVenCod,TPlCuenta, GrpCtaCte," +
					" CASE WHEN (DATEDIFF(year,CliNacio,GETDATE())>150) THEN NULL ELSE CliNacio END AS CliNacio," +
					" CASE WHEN CliEstCiv ='C' THEN 'CASADO/A' " +
					      " WHEN CliEstCiv ='U' THEN 'VIUDO/A' " +
					      " WHEN CliEstCiv ='D' THEN 'DIVORCIADO/A' ELSE 'SOLTERO/A' END AS CliEstCiv," +
					" ISNULL(CliHijos,0) AS CliHijos,CliViviend," +
					" CASE WHEN CliVehicul=0 THEN 'NO' WHEN  CliVehicul=1 THEN 'SI' END AS CliVehicul," +
					" ISNULL(CliOtCred,'-') AS CliOtCred," +
					" ref1_nombre,ref1_apellido,ref1_vinculo,ref1_telefono,ref2_nombre,ref2_apellido,ref2_vinculo,ref2_telefono," +
					" ocupacion,justificacion,empresa,dom_laboral,dom_laboral_nro,dom_laboral_loc,tel_laboral," +
					" CASE WHEN (DATEDIFF(year,ingreso_laboral,GETDATE())>150)" +
						" THEN NULL ELSE ingreso_laboral END AS ingreso_laboral,cargo," +
					" ISNULL(nominal,0) AS nominal,ISNULL(liquido,0) AS liquido,ConyCed,ConyNom1,ConyCliApe," +
					" CASE WHEN (DATEDIFF(year,ConyNacim,GETDATE())>150) THEN NULL ELSE ConyNacim END AS ConyNacim,ocupacion,justificacion "+
					" from q_tarjplas_adempiere_new " +
					" where TPlCuenta = "+this.mCuenta.getAccountNo()+" ";
					;
			
			rs = stmt.executeQuery(sql);
					
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			while (rs.next()) {
				String tellab = rs.getString("tel_laboral");
				if(tellab!=null){
					tellab=rs.getString("tel_laboral").trim();
				}
				String codv = rs.getString("SolVenCod").trim();
				if(codv.length()>0){
					codv =rs.getString("SolVenCod").trim();
				}else{
					codv=null;
				}
				String telr1 = rs.getString("ref1_telefono").trim();
				int telref1 = 0;
				if(telr1!=null){
					try{
						telref1 = Integer.valueOf(telref1);
					}catch(Exception e){
						
					}
				}
				String telr2 = rs.getString("ref2_telefono").trim();
				int telref2 = 0;
				if(telr1!=null){
					try{
						telref2 = Integer.valueOf(telr2);
					}catch(Exception e){
						
					}
				}
				 insert = new String ("INSERT INTO "+TABLA_MOLDE+ " (ad_user_id,uy_tt_card_id,codvendedor" + // ID UY_TT_Card y AD_User_ID
						",gpocierre,clinacio,cliestciv,clicanthijos,clivivienda,clivehiculo,cliotcred," + //Datos cliente
						" ref1_nombre,ref1_apellido,ref1_vinculo,ref1_telefono,ref1_domicilio," +//referencia 1
						" ref2_nombre,ref2_apellido,ref2_vinculo,ref2_telefono,ref2_domicilio," +//regerencia 2
						" cliempresa,clidomlaboral,clidomlaboralnro,cliloclaboral,clitellaboral,clifchinglaboral," + //datoslabroales
						" clicargolaboral,cliliquidolaboral,clinominallaboral," + // idem ant
						" conynom1,conycliape,conynacim,conycedid,FchDeImpresion,ocupacion,justificacion) " +//datos del cony
						"VALUES ("+ this.adUserID +","+ this.uyTTCardID + 
						"," + codv +
						//","+((rs.getString("SolVenCod")!=null)?rs.getString("SolVenCod").trim():null)+
						","+((rs.getInt("GrpCtaCte")>0)?"'"+rs.getInt("GrpCtaCte")+"'":null)+ //grupocierre GprCtaCte
						","+((rs.getTimestamp("CliNacio")!=null)?"'"+rs.getTimestamp("CliNacio")+"'": null) +						
						","+((rs.getString("CliEstCiv")!=null) ?"'"+ rs.getString("CliEstCiv").trim()+"'" : null)+
						","+ rs.getInt("CliHijos")+
						","+((rs.getString("CliViviend")!=null) ?"'"+rs.getString("CliViviend").trim()+"'":null)+
						","+((rs.getString("CliVehicul")!=null)?"'"+rs.getString("CliVehicul").trim()+"'":null)+
						","+((rs.getString("CliOtCred")!=null)? "'"+rs.getString("CliOtCred").trim()+"'":null)+
						","+((rs.getString("ref1_nombre")!=null)? "'"+rs.getString("ref1_nombre").trim()+"'":null)+
						","+((rs.getString("ref1_apellido")!=null)?"'"+rs.getString("ref1_apellido").trim()+"'":null)+
						","+((rs.getString("ref1_vinculo")!=null)?"'"+rs.getString("ref1_vinculo").trim()+"'":null)+
						//","+((rs.getString("ref1_telefono")!=null)?rs.getString("ref1_telefono").trim():null) +
						","+String.valueOf(telref1)+
						","+null+//falta dato direccion ref1
						","+((rs.getString("ref2_nombre")!=null)?"'"+rs.getString("ref2_nombre").trim()+"'":null)+
						","+((rs.getString("ref2_apellido")!=null)?"'"+rs.getString("ref2_apellido").trim()+"'":null)+
						","+((rs.getString("ref2_vinculo")!=null)?"'"+rs.getString("ref2_vinculo").trim()+"'":null) +
						//","+((rs.getString("ref2_telefono")!=null)?rs.getString("ref2_telefono").trim():null) +
						","+String.valueOf(telref2)+
						","+null+//falta dato direccon ref1
						","+((rs.getString("empresa")!=null?"'"+rs.getString("empresa").trim()+"'":null)+
						","+((rs.getString("dom_laboral")!=null)?"'"+rs.getString("dom_laboral").trim()+"'":null)+
						","+((rs.getString("dom_laboral_nro")!=null)?"'"+rs.getString("dom_laboral_nro").trim()+"'":null)+						
						","+((rs.getString("dom_laboral_loc")!=null)?"'"+rs.getString("dom_laboral_loc").trim()+"'":null)+
						","+(!tellab.equals("")?tellab:null)+
						//"',"+((rs.getString("tel_laboral")!=null || !rs.getString("tel_laboral").trim().equals(""))?rs.getString("tel_laboral").trim():null)+
						","+((rs.getTimestamp("ingreso_laboral")!=null)?"'"+rs.getTimestamp("ingreso_laboral")+"'":null)+
						","+((rs.getString("cargo")!=null)?"'"+rs.getString("cargo").trim()+"'":null)+
						","+((rs.getBigDecimal("nominal").equals(new BigDecimal(0)))?null:rs.getBigDecimal("nominal"))+
						//","+rs.getObject("nominal")+
						","+((rs.getBigDecimal("liquido").equals(new BigDecimal(0)))?null:rs.getBigDecimal("liquido"))+
						//","+ rs.getObject("liquido")+
						","+((rs.getString("ConyNom1")!=null)?"'"+rs.getString("ConyNom1").trim()+"'":null)+
						","+((rs.getString("ConyCliApe")!=null)?"'"+rs.getString("ConyCliApe").trim()+"'":null)+
						//","+rs.getString("ConyNom1")+","+rs.getString("ConyCliApe")+
						","+((rs.getTimestamp("ConyNacim")!=null)?"'"+rs.getTimestamp("ConyNacim")+"'":null)+
								//","+ rs.getObject("ConyNacim")+","+rs.getObject("ConyCed")+","
						","+((rs.getInt("ConyCed")>0)?rs.getInt("ConyCed"):null)+
						",'"+today+
						"',"+((rs.getString("ocupacion")!=null)?"'"+rs.getString("ocupacion").trim()+"'":null)+
						
						","+((rs.getString("justificacion")!=null)?"'"+rs.getString("justificacion").trim()+"'":null)+")"));
						
						
			}
			
			insert = insert.replace(",,", ",null,");
			log.log(Level.INFO, insert );
			DB.executeUpdate(insert, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
			throw e;
		}
	}
		
		
	
	
	/***
	 * Obtiene conexion a base de Financial.
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

}
