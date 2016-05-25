/**
 * RCtbSaldoCCostos.java
 * 14/03/2011
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MCategoriaCCostos;

/**
 * OpenUp.
 * RCtbSaldoCCostos
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 14/03/2011
 */
public class RCtbSaldoCCostos extends SvrProcess {

	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;

	private int cPeriodID_Desde = 0;
	private int cPeriodID_Hasta = 0;
	private int idCategoriaCCosto = 0;
	private int idCCosto = 0;
	private int idCuentaDesde = 0;
	private int idCuentaHasta = 0;
	
	private int idEmpresa = 0;
	private Long idUsuario = new Long(0);
	private int idOrganizacion = 0;
	private String idReporte = "";
		
	private static final String TABLA_MOLDE = "UY_Molde_SaldoCCostos";
	
	/**
	 * Constructor
	 */
	public RCtbSaldoCCostos() {
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
		
			String name = para[i].getParameterName().trim();
			
			if (name != null) {

				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i];
				}				
				if (name.equalsIgnoreCase("AD_User_ID")) {
					this.idUsuario = ((BigDecimal) para[i].getParameter()).longValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")) {
					this.idOrganizacion = ((BigDecimal) para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")) {
					this.idEmpresa = ((BigDecimal) para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_Period_ID")) {
					if (para[i].getParameter() != null) {
						this.cPeriodID_Desde = ((BigDecimal) para[i].getParameter()).intValueExact();
						this.cPeriodID_Hasta = ((BigDecimal) para[i].getParameter_To()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("UY_Categoria_CCostos_ID")) {
					if (para[i].getParameter() != null) {
						this.idCategoriaCCosto = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("C_Activity_ID")) {
					if (para[i].getParameter() != null) {
						this.idCCosto = ((BigDecimal) para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("C_ElementValue_ID")) {
					if (para[i].getParameter() != null) {
						this.idCuentaDesde = ((BigDecimal) para[i].getParameter()).intValueExact();
						this.idCuentaHasta = ((BigDecimal) para[i].getParameter_To()).intValueExact();
					}
				}
			}
			
		}

		// set el id del reporte con el igual al id del usuario
		this.idReporte = UtilReportes.getReportID(this.idUsuario);

		// Si tengo parametro para idreporte, lo seteo
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
		
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		this.deleteInstanciasViejasReporte();
		this.validoRangoFechaPeriodos();
		this.loadData();
		//this.calculateData();
		
		return "OK";
	}

	/**
	 * OpenUp.	
	 * Descripcion : Delete de instancias anteriores de este usuario en el reporte. 
	 * @author  Gabriel Vila 
	 * Fecha : 14/03/2011
	 */
	private void deleteInstanciasViejasReporte(){
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.idUsuario;
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	/**
	 * OpenUp.	
	 * Descripcion : Valida rango de fechas ingresado por usuario en parametros del reporte.
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 14/03/2011
	 */
	private void validoRangoFechaPeriodos() throws Exception {

		MPeriod periodoDesde = new MPeriod(getCtx(), this.cPeriodID_Desde, null);
		MPeriod periodoHasta = new MPeriod(getCtx(), this.cPeriodID_Hasta, null);
		
		if (periodoDesde.get_ID() <= 0) throw new Exception("Periodo Desde no es Valido.");
		if (periodoHasta.get_ID() <= 0) throw new Exception("Periodo Hasta no es Valido.");
		
		if (periodoHasta.getEndDate().before(periodoDesde.getStartDate()))
			throw new Exception("Periodo Hasta Debe ser MAYOR/IGUAL que el Periodo Desde.");
	
		if ((this.cPeriodID_Hasta - this.cPeriodID_Desde) > 12)
			throw new Exception("El periodo no puede ser mayor a un año.");
		
		this.fechaDesde = periodoDesde.getStartDate();
		this.fechaHasta = periodoHasta.getEndDate();
		
	}

	/**
	 * OpenUp.	
	 * Descripcion : Carga tabla molde con informacion necesaria para el reporte.
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 14/03/2011
	 */
	private void loadData() throws Exception {

		String insert = "";
		String sql = "";

		try {

			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, idreporte, " +
			 "ad_user_id, uy_categoria_ccostos_id, c_activity_id, c_elementvalue_id, c_period_id, " +
			 "grandtotal) "; 		
			
			
			if (this.idCategoriaCCosto <= 0){
				sql = getSQLCategoria("1") + " UNION " + getSQLCategoria("2") + " UNION " + getSQLCategoria("3") +
						" order by c_period_id, uy_categoria_ccostos_id ";
			}
			else {
				MCategoriaCCostos categcc = new MCategoriaCCostos(getCtx(), this.idCategoriaCCosto, get_TrxName());
				sql = getSQLCategoria(categcc.getValue()) + " order by c_period_id, uy_categoria_ccostos_id ";
			}		

			log.info(insert + sql);

			DB.executeUpdate(insert + sql, null);

		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
			throw e;
		}
	}
	
	private String getSQLCategoria(String valueCateg) {
		
		String whereFiltros = " WHERE facct.ad_client_id =" + this.idEmpresa +
				  " AND facct.ad_org_id =" + this.idOrganizacion +
				  " AND facct.dateacct between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "'"; 
		
		if (this.idCategoriaCCosto > 0)
			whereFiltros += " AND ccosto.uy_categoria_ccostos_id =" + this.idCategoriaCCosto;
		
		if (this.idCCosto > 0)
			whereFiltros += " AND facct.c_activity_id_" + valueCateg + "=" + this.idCCosto;			

		if (this.idCuentaDesde>0) 
			whereFiltros += " AND facct.account_id>=" + this.idCuentaDesde;
	
		if (this.idCuentaHasta>0) 
			whereFiltros += " AND facct.account_id<=" + this.idCuentaHasta;

		// Armo SQL
		String sql = " SELECT facct.ad_client_id, facct.ad_org_id,'" + this.idReporte + "'," + this.idUsuario + "," +
			  " ccosto.uy_categoria_ccostos_id,facct.c_activity_id_" + valueCateg + ", facct.account_id," +
			  		" facct.c_period_id, coalesce(SUM(coalesce(facct.amtacctdr,0) - coalesce(facct.amtacctcr,0)),0) as saldo " +
			  " from fact_acct facct " +
			  " inner join c_activity ccosto on facct.c_activity_id_" + valueCateg + " = ccosto.c_activity_id " +
			  whereFiltros +
			  " GROUP BY facct.ad_client_id, facct.ad_org_id, ccosto.uy_categoria_ccostos_id, facct.c_activity_id_" + valueCateg + ", facct.account_id, facct.c_period_id ";
		
		return sql;
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Calcula y actualiza informacion para el reporte en los campos de tabla molde.
	 * @author  Gabriel Vila 
	 * Fecha : 14/03/2011
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void calculateData() throws Exception{
	
		String sql = "", update = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			// Obtengo id, fechas, etc., de periodos a considerar
			sql ="SELECT c_period_id, startdate, enddate " + 
 		  	" FROM c_period " +
 		  	" WHERE c_period_id >= " + this.cPeriodID_Desde + 
 		  	" AND c_period_id <= " + this.cPeriodID_Hasta +
 		  	" ORDER BY c_period_id ";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
		
			// Recorro periodos ordenados
			int contadorMes = 0;
			while (rs.next()){
				contadorMes++;
				
				update = " UPDATE " + TABLA_MOLDE +
				" SET mes" + contadorMes + "=" + 
								" (SELECT coalesce(SUM(coalesce(facct.amtacctdr,0) - coalesce(facct.amtacctcr,0)),0) " +
								" FROM fact_acct facct " +
								" INNER JOIN c_activity ccosto on facct.c_activity_id = ccosto.c_activity_id " +
								" WHERE facct.ad_client_id =" + this.idEmpresa +
								" AND facct.ad_org_id =" + this.idOrganizacion +
								" AND facct.c_activity_id is not null " +
								" AND ccosto.uy_categoria_ccostos_id = " + TABLA_MOLDE + ".uy_categoria_ccostos_id " +
								" AND facct.c_activity_id = " + TABLA_MOLDE + ".c_activity_id " +
								" AND facct.account_id = " + TABLA_MOLDE + ".c_elementvalue_id " +
								" AND facct.dateacct BETWEEN '" + rs.getTimestamp("startdate") + "'" +
								" AND '" + rs.getTimestamp("enddate") + "')" +
				" WHERE idReporte='" + this.idReporte + "'";

				DB.executeUpdate(update, null);
				
				// Actualizo titulo de columnas del reporte
				/*MPeriod periodo = new MPeriod(getCtx(), rs.getInt("c_period_id"), null);
				if (periodo != null){
					update = " UPDATE AD_PrintFormatItem set printname='" + periodo.getName() + "'" +
							 " WHERE name='mes" + contadorMes + "'" +
							 " AND AD_PrintFormat_ID IN " + 
							 " (SELECT AD_PrintFormat_ID FROM AD_PrintFormat WHERE ad_table_id IN " + 
							 " (select ad_table_id from ad_table where upper(name)='" + TABLA_MOLDE.toUpperCase() + "'))";
					DB.executeUpdate(update, get_TrxName());
				}*/
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}
}
