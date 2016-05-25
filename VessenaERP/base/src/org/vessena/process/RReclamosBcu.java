package org.openup.process;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFduConnectionData;
import org.openup.model.MRReclamosBcu;
import org.openup.util.OpenUpUtils;

public class RReclamosBcu extends SvrProcess {
	
	private MRReclamosBcu informe = null;
	
	private MPeriod periodoDesde = null;
	private MPeriod periodoHasta = null;
	

	public RReclamosBcu() {
		
	}

	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();
		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("C_Period_ID")){	
					if(para[i].getParameter() != null){
						int periodoDesdeID = ((BigDecimal)para[i].getParameter()).intValueExact();
						
						this.periodoDesde = new MPeriod(this.getCtx(), periodoDesdeID, null);
					}
					if(para[i].getParameter_To() != null){
						int periodoHastaID = ((BigDecimal)para[i].getParameter_To()).intValueExact();
						
						this.periodoHasta = new MPeriod(this.getCtx(), periodoHastaID, null);
					}				
				}		
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		//Elimino instancias viejas del reporte solo para el usuario que esta ahora
		this.showHelp("Elimando datos anteriores...");
		this.deleteInstanciasViejas();
		
		this.showHelp("Cargando datos...");		
		
		//Creo el modelo de MRReclamoBcu con los datos basicos
		this.crearModeloInforme();
		
		//Se carga una tabla temporal con las los reclamos validos, dado que si existe mas de un reclamo con el mismo tema para un mismo cierre, debo quedarme con el que tiene menos dias de resolucion
		this.crearTablaTemporal();
		
		//Cargo la cantidad de clientes de italcred
		this.cargarCantidadClientes();
		
		//Cargo la cantidad de reclamos, el importe, y la cantidad de raclamos por cliente
		this.cargarCantidadMonto();
		
		//Cargo los datos de las respuestas
		this.cargarRespuestas();
		
		//Se cargan las que estan en espera de solucion
		this.cargarEsperaSolucion();
		
		//Cargo los promedios
		this.cargarPromedios();
		
		
		return "ok";
	}
	
	
	

	private void deleteInstanciasViejas(){
		
		try{		
			//Borro los datos de reportes anteriores para este usuario
			String sql = "DELETE FROM UY_R_ReclamosBcu ";//WHERE AD_User_ID = " + this.getAD_User_ID();
			DB.executeUpdateEx(sql,null);
						
			//borro los datos de la tabla auxiliar que contiene los reclamos validos para este usuario
			sql = "DELETE FROM UY_R_MoldeReclamoValido"; //WHERE AD_User_ID = " + this.getAD_User_ID();
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)		{
			throw new AdempiereException (e);		
		}
	}

	
	private void crearModeloInforme(){
		
		MPeriod periodAcutal = MPeriod.getFirstInYear(this.getCtx(), new Timestamp(System.currentTimeMillis()), Env.getAD_Org_ID(this.getCtx()));
		MPeriod primerPeriod = null;
		
		String periodoInformado = "";
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try {					
			String sql = "select c_period_id from c_period order by startdate";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			rs = pstmt.executeQuery ();	
			
			if(rs.next()){
				primerPeriod = new MPeriod(this.getCtx(), rs.getInt("c_period_id"), null);				
			}		
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		} finally{
			DB.close(rs, pstmt);				
		}		
				
		this.informe = new MRReclamosBcu(this.getCtx(), 0, this.get_TrxName());
		this.informe.setAD_User_ID(this.getAD_User_ID());
		this.informe.setCodigoInstitucion("852");
		this.informe.setInstitucion("Italcred - Verendy S.A");
		this.informe.setServicio("Tarjeta de Crédito");
		
		if(periodoDesde != null && periodoHasta != null){
			periodoInformado = this.periodoDesde.getName() + " - " + this.periodoHasta.getName();			
		}else{
			if(periodoDesde != null && periodoHasta == null){
				periodoInformado = this.periodoDesde.getName() + " - " + periodAcutal.getName();
			}else{
				if(periodoDesde == null && periodoHasta != null){
					periodoInformado = primerPeriod.getName() + " - " + this.periodoHasta.getName();	
				}
			}
		}		
		this.informe.setRangoPeriodo(periodoInformado);
				
		this.informe.saveEx();
		
	}

	
	private void crearTablaTemporal(){
				
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String where = "";
		if(periodoDesde != null && periodoHasta != null){
			where = " and r.datetrx between '" + this.periodoDesde.getStartDate() + "' and '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";			
		}else{
			if(periodoDesde != null && periodoHasta == null){
				where = " and r.datetrx >= '" + this.periodoDesde.getStartDate() + "'";
			}else{
				if(periodoDesde == null && periodoHasta != null){
					where = " and r.datetrx <= '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";	
				}
			}
		}
		
		try {					
			String sql = "with temp as(" +
						 " 		select r.accountno as cuenta, (select max(lmd.datetrx)" +
						 " 									   from uy_fdu_logisticmonthdates lmd" +
						 " 									   inner join uy_fdu_grupocc grp on lmd.uy_fdu_grupocc_id = grp.uy_fdu_grupocc_id" +							   
						 " 									   and cast(grp.value as int) = re.grpctacte" +
					     " 									   and datetrx <= r.datetrx) as cierre," + 
					     " 		r.uy_r_cause_id as tema, date_part('day'::text, (r.assigndateto- r.datetrx)) as diasResolucion" +					     
					     " 		from vuy_detallereclamo r" +
					     "		inner join uy_r_reclamo re on re.uy_r_reclamo_id = r.uy_r_reclamo_id "+			     
					     " 		inner join uy_r_cause c on r.uy_r_cause_id = c.uy_r_cause_id" +
					     " 		where r.informabcu = 'SI'" +
					     " 		and re.grpctacte <> 0" +
					     		where +
					     " )" +
					     " select cuenta, cierre, tema, min(diasResolucion) as diasResolucion" +
					     " from temp" +
					     " group by cuenta, cierre, tema";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			rs = pstmt.executeQuery ();	
									
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while(rs.next()){
				
				this.showHelp("Paso 1/5: " + rowCount++ + " de " + totalRowCount);
				
				ResultSet rs2 = null;
				PreparedStatement pstmt2 = null;
				
				try {					
					String sql2 = "select r.uy_r_reclamo_id as reclamo" +
								  " from vuy_detallereclamo r" +
								  "	inner join uy_r_reclamo re on re.uy_r_reclamo_id = r.uy_r_reclamo_id "+
								  //" inner join uy_r_gestion gest on r.uy_r_reclamo_id = gest.uy_r_reclamo_id and gest.reclamoresuelto = 'Y'" +
								  " where r.informabcu = 'SI'" +
								  where +
								  " and r.uy_r_cause_id = " + rs.getInt("tema") +
								  " and r.accountno = '" + rs.getString("cuenta") + "'" +								  
								  " and (select max(lmd.datetrx)" +
								  "		 from uy_fdu_logisticmonthdates lmd" +
								  " 	 inner join uy_fdu_grupocc grp on lmd.uy_fdu_grupocc_id = grp.uy_fdu_grupocc_id" +							   
								  " 	 and cast(grp.value as int) = re.grpctacte" +
								  " 	 and datetrx <= r.datetrx) = '" + rs.getTimestamp("cierre") + "'" +								  
								  " and date_part('day'::text, (r.assigndateto- r.datetrx)) = " + rs.getInt("diasResolucion");
					
					
					pstmt2 = DB.prepareStatement(sql2, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
					rs2 = pstmt2.executeQuery ();	
											
					if(rs2.next()){						
						
						DB.executeUpdateEx("INSERT INTO UY_R_MoldeReclamoValido VALUES(" + rs2.getInt("reclamo") + "," + this.getAD_User_ID() + ")", null);						
					}				
					
				} catch (Exception e) {
					DB.close(rs2, pstmt2);	
					throw new AdempiereException(e);

				} finally{
					DB.close(rs2, pstmt2);				
				}		
			}				
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);

		} finally{
			DB.close(rs, pstmt);				
		}		
	}

	
	private void cargarCantidadClientes() {
		Connection con = null;
		ResultSet rs = null;
		

		try {

			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			
			String sql = "select [TOTAL CUENTA PADRON]" +
					 "	FROM FinancialPro.[dbo].[q_Total_Cuentas_Padron] ";
		
			rs = stmt.executeQuery(sql);

			if(rs.next()){
				this.informe.setClientes(rs.getBigDecimal("TOTAL CUENTA PADRON"));
			}			
			
			rs.close();
			con.close();

		} catch (Exception e) {

			throw new AdempiereException(e);

		} finally {

			if (con != null) {
				try {
					if (rs != null) {
						if (!rs.isClosed())
							rs.close();
					}
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
		
	}
	
	
	private void cargarCantidadMonto(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String where = "";
		if(periodoDesde != null && periodoHasta != null){
			where = " and r.datetrx between '" + this.periodoDesde.getStartDate() + "' and '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";			
		}else{
			if(periodoDesde != null && periodoHasta == null){
				where = " and r.datetrx >= '" + this.periodoDesde.getStartDate() + "'";
			}else{
				if(periodoDesde == null && periodoHasta != null){
					where = " and r.datetrx <= '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";	
				}
			}
		}
		
		try {					
			String sql = "select count(r.accountno) as Cantidad, sum(importe) as importe" +
						 " from vuy_detallereclamo r" +
						 " inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id "+// and rv.ad_user_id = " + this.getAD_User_ID() +
						// " inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" +						
						 " where r.informabcu = 'SI'"+
						 where;
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			rs = pstmt.executeQuery ();	

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while(rs.next()){
				
				this.showHelp("Paso 2/5: " + rowCount++ + " de " + totalRowCount);
				
				this.informe.setCantidadReclamos(rs.getBigDecimal("Cantidad"));
				this.informe.setAmount(rs.getBigDecimal("importe"));
				
				this.informe.saveEx();
			}				
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);

		} finally{
			DB.close(rs, pstmt);				
		}	
	}
	
	
	private void cargarRespuestas(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String where = "";
		if(periodoDesde != null && periodoHasta != null){
			where = " and r.datetrx between '" + this.periodoDesde.getStartDate() + "' and '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";			
		}else{
			if(periodoDesde != null && periodoHasta == null){
				where = " and r.datetrx >= '" + this.periodoDesde.getStartDate() + "'";
			}else{
				if(periodoDesde == null && periodoHasta != null){
					where = " and r.datetrx <= '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";	
				}
			}
		}
		
		try {	
			
			//Primero Dias de resolucion desde 0 hasta menor o igual a 2 dias
			
			String sql ="  select r.resueltofavor as TipoResolucion, count(r.uy_r_reclamo_id) as Respuestas" +
					"  from vuy_detallereclamo r" +
					"  inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" +
					"  inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id "+					//"  where g.reclamoresuelto = 'Y'" +
					"  and r.informabcu='SI'" +
					"  and r.assigndateto <= " + "'" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'"+	
					 where +
					 " and date_part('day'::text, (r.assigndateto- r.datetrx)) <= 2" +
					 " group by r.resueltofavor"; 
						/* "select g.resueltotype as TipoResolucion, count(r.uy_r_reclamo_id) as Respuestas" +
						 " from uy_r_reclamo r" +
						 " inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id and rv.ad_user_id = " + this.getAD_User_ID() +
						 " inner join uy_r_gestion g on r.uy_r_reclamo_id = g.uy_r_reclamo_id" +
						 " inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" +
						 " where g.reclamoresuelto = 'Y'" +
						 //" --and r.uy_r_reclamo_id = 1120899" +
						 " and rec.informabcu = 'Y'" +	
						 where +
						 " and date_part('day'::text, age(r.assigndateto, r.datetrx)) <= 2" +
						 " group by g.resueltotype";*/
						 
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			rs = pstmt.executeQuery ();	

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while(rs.next()){
				
				this.showHelp("Paso 3/5: " + rowCount++ + " de " + totalRowCount);
				
				if(rs.getString("TipoResolucion").equalsIgnoreCase("EMPRESA")){
					this.informe.setInstMenor2Dias(rs.getBigDecimal("Respuestas"));
				}else{
					if(rs.getString("TipoResolucion").equalsIgnoreCase("CLIENTE")){
						this.informe.setCliMenor2Dias(rs.getBigDecimal("Respuestas"));
					}
				}				
				this.informe.saveEx();
			}
			
			
			//Ahora dias de resolucion mayor que 2 y menor o igual que 15
			sql = "  select r.resueltofavor as TipoResolucion, count(r.uy_r_reclamo_id) as Respuestas" +
					"  from vuy_detallereclamo r" +
					"  inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" +
					" inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id "+
					"  and r.informabcu='SI'" +
					"  and r.assigndateto <= " + "'" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'"+	
					 where +
					 " and (date_part('day'::text, (r.assigndateto- r.datetrx)) > 2 and date_part('day'::text, (r.assigndateto- r.datetrx)) <= 15)" +
					 " group by r.resueltofavor";
					/*"select g.resueltotype as TipoResolucion, count(r.uy_r_reclamo_id) as Respuestas" +
					 " from uy_r_reclamo r" +
					 " inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id and rv.ad_user_id = " + this.getAD_User_ID() +
					 " inner join uy_r_gestion g on r.uy_r_reclamo_id = g.uy_r_reclamo_id" +
					 " inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" +
					 " where g.reclamoresuelto = 'Y'" +
					 //" --and r.uy_r_reclamo_id = 1120899" +
					 " and rec.informabcu = 'Y'" +	
					 where +
					 " and (date_part('day'::text, age(r.assigndateto, r.datetrx)) > 2 and date_part('day'::text, age(r.assigndateto, r.datetrx)) <= 15)" +
					 " group by g.resueltotype";*/
					 
		
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			rs = pstmt.executeQuery ();	
			
			while(rs.next()){
				
				if (rs.getString("TipoResolucion") != null){
					if(rs.getString("TipoResolucion").equalsIgnoreCase("EMPRESA")){
						this.informe.setInstMenor15Dias(rs.getBigDecimal("Respuestas"));
					}else{
						if(rs.getString("TipoResolucion").equalsIgnoreCase("CLIENTE")){
							this.informe.setCliMenor15Dias(rs.getBigDecimal("Respuestas"));
						}
					}				
					this.informe.saveEx();
				}
				
			}
			
			//Ahora dias de resolucion mayor que 15 dias
			sql =   "  select r.resueltofavor as TipoResolucion, count(r.uy_r_reclamo_id) as Respuestas" +
					"  from vuy_detallereclamo r" +
					"  inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" +
					" inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id "+
					"  and r.informabcu='SI'" +
					"  and r.assigndateto <= " + "'" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'"+	
					 where +
					 " and date_part('day'::text, (r.assigndateto- r.datetrx)) > 15" +
					 " group by r.resueltofavor";
					/*"select g.resueltotype as TipoResolucion, count(r.uy_r_reclamo_id) as Respuestas" +
					 " from uy_r_reclamo r" +
					 " inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id and rv.ad_user_id = " + this.getAD_User_ID() +
					 " inner join uy_r_gestion g on r.uy_r_reclamo_id = g.uy_r_reclamo_id" +
					 " inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" +
					 " where g.reclamoresuelto = 'Y'" +
					 //" --and r.uy_r_reclamo_id = 1120899" +
					 " and rec.informabcu = 'Y'" +	
					 where +
					 " and date_part('day'::text, age(r.assigndateto, r.datetrx)) > 15" +
					 " group by g.resueltotype";*/
					 
		
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			rs = pstmt.executeQuery ();	
			
			while(rs.next()){
				
				if(rs.getString("TipoResolucion") != null){
					if(rs.getString("TipoResolucion").equalsIgnoreCase("EMPRESA")){
						this.informe.setInstMayor15Dias(rs.getBigDecimal("Respuestas"));
					}else{
						if(rs.getString("TipoResolucion").equalsIgnoreCase("CLIENTE")){
							this.informe.setCliMayor15Dias(rs.getBigDecimal("Respuestas"));
						}
					}				
					this.informe.saveEx();
				}				
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);

		} finally{
			DB.close(rs, pstmt);				
		}	
	}
	
		
	private void cargarEsperaSolucion(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String where = "";
		if(periodoDesde != null && periodoHasta != null){
			where = " and r.datetrx between '" + this.periodoDesde.getStartDate() + "' and '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";			
		}else{
			if(periodoDesde != null && periodoHasta == null){
				where = " and r.datetrx >= '" + this.periodoDesde.getStartDate() + "'";
			}else{
				if(periodoDesde == null && periodoHasta != null){
					where = " and r.datetrx <= '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";	
				}
			}
		}
		
		try {				
			String sql =
						"select count(r.accountno) as Respuestas "+ 
						" from vuy_detallereclamo r"+ 
						" where r.informabcu='SI'"+
						" and r.resueltofavor IS NULL"+
						" and r.assigndateto IS NULL"+  
						 where +
						" union"+
						" select count(r.accountno)"+
						" from vuy_detallereclamo r"+
						" inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id"+
						" where r.resueltofavor IS NOT NULL"+
						" and r.assigndateto > " + "'" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'"+ 
						where;
			
						/*"select count(r.uy_r_reclamo_id) as Respuestas" +						
						 " from uy_r_reclamo r" +
						 " inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id and rv.ad_user_id = " + this.getAD_User_ID() +
						 " inner join uy_r_gestion g on r.uy_r_reclamo_id = g.uy_r_reclamo_id" +
						 " inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" +
						 " where g.reclamoresuelto = 'N'" +
						 " and rec.informabcu = 'Y'" +
						 where;*/		
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			
			rs = pstmt.executeQuery ();	
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while(rs.next()){
				
				this.showHelp("Paso 4/5: " + rowCount++ + " de " + totalRowCount);
				
				this.informe.setEsperaSolucion(this.informe.getEsperaSolucion().add(rs.getBigDecimal("Respuestas")));
				if(rs.isFirst()){
					this.informe.setCantidadReclamos(this.informe.getCantidadReclamos().add(rs.getBigDecimal("Respuestas")));					
				}				
				this.informe.saveEx();
			}
			
			this.informe.setReclamosPorClientes(this.informe.getCantidadReclamos().divide(this.informe.getClientes(), MathContext.DECIMAL32));
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);

		} finally{
			DB.close(rs, pstmt);				
		}	
	}
	
	
	private void cargarPromedios(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String where = "";
		if(periodoDesde != null && periodoHasta != null){
			where = " and r.datetrx between '" + this.periodoDesde.getStartDate() + "' and '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";			
		}else{
			if(periodoDesde != null && periodoHasta == null){
				where = " and r.datetrx >= '" + this.periodoDesde.getStartDate() + "'";
			}else{
				if(periodoDesde == null && periodoHasta != null){
					where = " and r.datetrx <= '" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'";	
				}
			}
		}
		
		try {			
			String sql = "with temp as(" +
					     " 		select r.resueltofavor as TipoResolucion, date_part('day'::text, (r.assigndateto- r.datetrx)) as dias, 1 as veces" +
					     " 		from vuy_detallereclamo r" + 
					     " 		inner join UY_R_MoldeReclamoValido rv on r.uy_r_reclamo_id = rv.uy_r_reclamo_id"+ //and rv.ad_user_id = " + this.getAD_User_ID() +
					    // " 		inner join uy_r_gestion g on r.uy_r_reclamo_id = g.uy_r_reclamo_id" + 
					    // " 		inner join uy_r_cause rec on r.uy_r_cause_id = rec.uy_r_cause_id" + 
					     " 		where  r.informabcu = 'SI'" + 
					     "		and r.assigndateto is not null"+
					     "      and r.assigndateto <= " + "'" + OpenUpUtils.sumaTiempo(this.periodoHasta.getEndDate(), Calendar.HOUR_OF_DAY, 23) + "'"+	
					     		where +
					     " 		group by r.resueltofavor, r.assigndateto, r.datetrx" +
					     " )" +
					     " select tipoResolucion, sum(dias)/sum(veces) as Promedio from temp" +
					     " group by tiporesolucion";
									
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, this.get_TrxName());
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while(rs.next()){
				
				this.showHelp("Paso 5/5: " + rowCount++ + " de " + totalRowCount);
				
				if(rs.getString("tipoResolucion") != null){
					if(rs.getString("tipoResolucion").equalsIgnoreCase("EMPRESA")){
						this.informe.setPromedioInstitucion(rs.getBigDecimal("Promedio").setScale(0, RoundingMode.HALF_UP));										
					}else{
						if(rs.getString("tipoResolucion").equalsIgnoreCase("CLIENTE")){
							this.informe.setPromedioCliente(rs.getBigDecimal("Promedio").setScale(0, RoundingMode.HALF_UP));							
						}
					}				
								
					this.informe.saveEx();
				}				
			}				
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);

		} finally{
			DB.close(rs, pstmt);				
		}	
	}
	
	
	
	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text){
		if (this.getProcessInfo().getWaiting() != null){
			this.getProcessInfo().getWaiting().setText(text);
		}			
	}
	private Connection getConnection() throws Exception {

		Connection retorno = null;

		String connectString = "";
		try {

			MFduConnectionData conn = MFduConnectionData.forFduFileID(getCtx(),
					1000016, null);

			if (conn != null) {

				connectString = "jdbc:sqlserver://" + conn.getserver_ip()
						+ "\\" + conn.getServer() + ";databaseName="
						+ conn.getdatabase_name() + ";user="
						+ conn.getuser_db() + ";password="
						+ conn.getpassword_db();

				retorno = DriverManager.getConnection(connectString,
						conn.getuser_db(), conn.getpassword_db());
			}

		} catch (Exception e) {
			throw e;
		}

		return retorno;
	}


}
