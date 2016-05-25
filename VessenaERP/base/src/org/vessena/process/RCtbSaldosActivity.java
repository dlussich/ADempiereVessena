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

/**
 * 
 * org.openup.process - RCtbSaldosActivity
 * OpenUp Ltda. Issue # 1852
 * Description: 
 * @author :Leonardo Boccone - 22/05/2014
 * @see
 */
public class RCtbSaldosActivity extends SvrProcess {


	  private Timestamp fechaDesde = null;
	  private Timestamp fechaHasta = null;
	  private int cPeriodID_Desde = 0;
	  private int cPeriodID_Hasta = 0;
	  private int idCategoriaCCosto = 0;
	  private int idCCosto = 0;
	  private int idCuentaDesde = 0;
	  private int idCuentaHasta = 0;
	  private int idEmpresa = 0;
	  private Long idUsuario = new Long(0L);
	  private int idOrganizacion = 0;
	  private String idReporte = "";
	
		//private int adClientID = 0;
		//private int adOrgID = 0;
		//private int adUserID = 0;
	  //private static final String TABLA_MOLDE = "UY_Molde_SaldoCCostos";
	  
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
					
					if (name.equalsIgnoreCase("C_ElementValue_ID")){
						if (para[i].getParameter()!=null)
							this.idCuentaDesde = ((BigDecimal)para[i].getParameter()).intValueExact();
						if (para[i].getParameter_To()!=null)
							this.idCuentaHasta = ((BigDecimal)para[i].getParameter_To()).intValueExact();
					}
	
				}
				
			}

			// set el id del reporte con el igual al id del usuario
			this.idReporte = UtilReportes.getReportID(this.idUsuario);

			// Si tengo parametro para idreporte, lo seteo
			if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
			
		}
	  
	  @Override
		protected String doIt() throws Exception {
		  
	    deleteInstanciasViejasReporte();
	    validoRangoFechaPeriodos();
	    loadData();
	    calculateData();
	    
	    return "OK";
	  }
	  
	  private void deleteInstanciasViejasReporte()
	  {
	    String sql = "";
	    try
	    {
	      sql = "DELETE FROM UY_Molde_SaldoCCostos WHERE ad_user_id =" + this.idUsuario;
	      DB.executeUpdateEx(sql, null);
	    }
	    catch (Exception e)
	    {
	      throw new AdempiereException(e);
	    }
	  }
	  
	  private void validoRangoFechaPeriodos() throws AdempiereException
	  {
	    MPeriod periodoDesde = new MPeriod(getCtx(), this.cPeriodID_Desde, null);
	    MPeriod periodoHasta = new MPeriod(getCtx(), this.cPeriodID_Hasta, null);

	    if (periodoHasta.getEndDate().before(periodoDesde.getStartDate())) {
	      throw new AdempiereException("Periodo Hasta Debe ser MAYOR/IGUAL que el Periodo Desde.");
	    }
	    if (this.cPeriodID_Hasta - this.cPeriodID_Desde > 12) {
	      throw new AdempiereException("El periodo no puede ser mayor a un año.");
	    }
	    this.fechaDesde = periodoDesde.getStartDate();
	    this.fechaHasta = periodoHasta.getEndDate();
	  }
	  
	  private void loadData()
	    throws Exception
	  {
	    String insert = "";
	    String sql = "";
	    try
	    {
	      //OpenUp. Nicolas Sarlabos. 22/10/2015. #3724. Se agrega columna de categoria de CC	
	      insert = "INSERT INTO UY_Molde_SaldoCCostos (ad_client_id, ad_org_id, idreporte, ad_user_id, c_period_id, uy_categoria_ccostos_id, c_activity_id, c_elementvalue_id, grandtotal) ";
	      //Fin OpenUp.

	      String whereFiltros = " WHERE facct.ad_client_id =" + this.idEmpresa + 
	        " AND facct.ad_org_id =" + this.idOrganizacion + 
	        " AND facct.dateacct between '" + this.fechaDesde + "' AND '" + this.fechaHasta + "'" + 
	        " AND facct.c_activity_id is not null ";
	      
	      if (this.idCategoriaCCosto > 0) {
	        whereFiltros = whereFiltros + " AND ccosto.uy_categoria_ccostos_id =" + this.idCategoriaCCosto;
	      }

	      if (this.idCCosto > 0) {
	        whereFiltros = whereFiltros + " AND facct.c_activity_id =" + this.idCCosto;
	      }	  
	    
	      int x = DB.getSQLValueEx(null, "select rownum from fact_account_tree where account_id= " + this.idCuentaDesde);
	      int y = DB.getSQLValueEx(null, "select rownum from fact_account_tree where account_id= " + this.idCuentaHasta);

	      if (this.idCuentaDesde>0) {
	  		whereFiltros = whereFiltros + " AND vv.rownum >=" + x;
	      }
	
	      if (this.idCuentaHasta>0) {
		  		whereFiltros = whereFiltros + " AND vv.rownum <=" + y;
		  }
	      //OpenUp. Nicolas Sarlabos. 22/10/2015. #3724. Se agrega columna de categoria de CC	
	      sql = " SELECT facct.ad_client_id, facct.ad_org_id,'" + this.idReporte + "'," + this.idUsuario + "," + 
	    		this.cPeriodID_Desde + ", ccosto.uy_categoria_ccostos_id, facct.c_activity_id, facct.account_id," + 
	    		"coalesce(SUM(coalesce(facct.amtacctdr,0) - coalesce(facct.amtacctcr,0)),0) as saldo " + 
	    		" from fact_acct facct " + 
	    		" inner join c_activity ccosto on facct.c_activity_id = ccosto.c_activity_id " + 
	    		" inner join c_elementvalue cta on facct.account_id = cta.c_elementvalue_id " +
	    		" inner join fact_account_tree vv on facct.account_id = vv.account_id" +
	    		whereFiltros + 
	    		" GROUP BY facct.ad_client_id, facct.ad_org_id, ccosto.uy_categoria_ccostos_id, facct.c_activity_id, facct.account_id ";
	      //Fin OpenUp.
      
	      this.log.info(insert + sql);
	      
	      DB.executeUpdate(insert + sql, null);
	    }
	    catch (Exception e)
	    {
	      this.log.log(Level.SEVERE, insert + sql, e);
	      throw e;
	    }
	  }  
	  
	  private void calculateData() throws Exception
	  {
	    String sql = "";String update = "";
	    ResultSet rs = null;
	    PreparedStatement pstmt = null;
	    try
	    {
	      sql = "SELECT c_period_id, startdate, enddate  FROM c_period  WHERE c_period_id >= " + this.cPeriodID_Desde + 
	    		  " AND c_period_id <= " + this.cPeriodID_Hasta + 
	    		  " ORDER BY c_period_id ";
	      
	      pstmt = DB.prepareStatement(sql, get_TrxName());
	      rs = pstmt.executeQuery();
	      

	      int contadorMes = 0;
	      while (rs.next())
	      {
	        contadorMes++;
	        
	        update =  " UPDATE UY_Molde_SaldoCCostos SET mes" + contadorMes + "=" + "" +
	        		" (SELECT coalesce(SUM(coalesce(facct.amtacctdr,0) - coalesce(facct.amtacctcr,0)),0) " + 
	        		" FROM fact_acct facct " + " INNER JOIN c_activity ccosto on facct.c_activity_id = ccosto.c_activity_id " + 
	        		" WHERE facct.ad_client_id =" + this.idEmpresa +
	        		" AND facct.ad_org_id =" + this.idOrganizacion + 
	        		" AND facct.c_activity_id is not null " + 
	        		" AND facct.c_activity_id = UY_Molde_SaldoCCostos.c_activity_id  AND facct.account_id = UY_Molde_SaldoCCostos.c_elementvalue_id " + 
	        		" AND facct.dateacct BETWEEN '" + rs.getTimestamp("startdate") + "'" + " AND '" + rs.getTimestamp("enddate") + "')" + 
	        		" WHERE idReporte='" + this.idReporte + "'";
	        
	        DB.executeUpdate(update, null);
	      }
	    }
	    catch (Exception e)
	    {
	      this.log.log(Level.SEVERE, sql, e);
	      throw e;
	    }
	    finally
	    {
	      DB.close(rs, pstmt);
	      rs = null;pstmt = null;
	    }
	  }
	
}

