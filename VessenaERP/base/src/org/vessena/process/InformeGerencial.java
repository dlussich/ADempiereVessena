/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/12/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AWindow;
import org.compiere.apps.Waiting;
import org.compiere.model.MConversionRate;
import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.process - InformeGerencial
 * OpenUp Ltda. Issue #116 
 * Description: Carga de temporales con informacion para Informe Gerencial.
 * Esta logica sirve tanto para el reporte como para el navegador.
 * @author Gabriel Vila - 18/12/2012
 * @see
 */
public class InformeGerencial {

	private static final String TABLA_MOLDE_AUX = "UY_MOLDE_InformeGerencial_2";
	private static final String TABLA_MOLDE = "UY_MOLDE_InformeGerencial";
	private static final String TABLA_MOLDE_NAV_CTA = "UY_MOLDE_InformeGerencial_3";
	private static final String TABLA_MOLDE_NAV_BP = "UY_MOLDE_InformeGerencial_4";
	private static final String TABLA_MOLDE_NAV_JOURNAL = "UY_MOLDE_InformeGerencial_5";
	private static final String TABLA_MOLDE_NAV_PROD = "UY_MOLDE_InformeGerencial_6";
	private static final String TABLA_MOLDE_NAV_CC = "UY_MOLDE_InformeGerencial_7";
	private static final String TABLA_MOLDE_NAV_PRODDET = "UY_MOLDE_InformeGerencial_8";
	private static final String TABLA_MOLDE_NAV_CCDET = "UY_MOLDE_InformeGerencial_9";
	
	private int cPeriodFromID = 0;
	private int cPeriodToID = 0;
	private int adUserID = 0;
	private int adClientID = 0;
	private String currencyType = "";
	
	private String idReporte = "";

	public HashMap<Integer, Integer> hashPosicionXPeriodo = new HashMap<Integer, Integer>();
	public HashMap<Integer, BigDecimal> hashTasaCambioXPeriodo = new HashMap<Integer, BigDecimal>();

	private Waiting waiting = null;
	private AWindow gerencialAWindow = null;
	
	private int opcionInforme = 0;
	
	//Agregado peticion # 3615
	private int cElementID = 0;
	/**
	 * Constructor.
	 */
	public InformeGerencial(int adUserID, int adClientID, int periodFromID, int periodToID, 
							String curType, String idReporte) {

		this.cPeriodFromID = periodFromID;
		this.cPeriodToID = periodToID;
		this.currencyType = curType;
		this.adUserID = adUserID;
		this.adClientID = adClientID;
		
		if (idReporte == null){
			this.idReporte = UtilReportes.getReportID(new Long(adUserID));
		}
		else{
			this.idReporte = idReporte;
		}
		
	}
	/**
	 * Constructor creado para testear informe gerencial "generico"
	 */
	public InformeGerencial(int adUserID, int adClientID, int periodFromID, int periodToID, 
			String curType, String idReporte, int elementID) {
		//C_Element_ID
		this.cElementID = elementID;
		this.cPeriodFromID = periodFromID;
		this.cPeriodToID = periodToID;
		this.currencyType = curType;
		this.adUserID = adUserID;
		this.adClientID = adClientID;
			
		if (idReporte == null){
			this.idReporte = UtilReportes.getReportID(new Long(adUserID));
		}
		else{
			this.idReporte = idReporte;
		}

	}

	public AWindow getGerencialAWindow(){
		return this.gerencialAWindow;
	}
	
	public void setGerencialAWindow(AWindow value){
		this.gerencialAWindow = value;
	}
	
	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}

	
	/***
	 * Ejecuta informe segun opcion de informe: 0 = cargar todo, 1 = cargar ahora solo le pestaña de totales.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 15/04/2014
	 * @see
	 * @param opcionInforme
	 */
	public void execute(int opcionInforme){

		try{
			
			this.opcionInforme = opcionInforme;
			
			// Elimina datos anteriores para este usuario
			this.deleteData();
			
			// Seteo posicion de periodos
			this.setPeriodos();
			
			// Carga datos iniciales en tabla temporal
			this.loadData();
			
			// Actualiza datos de temporal 
			this.updateData();
			
			// Actualiza titulos de periodos para el reporte
			this.updatePeriodsTitle();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Elimina datos anteriores para usuario que esta procesando el reporte.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 */
	private void deleteData() {
		try{
			
			String action = " DELETE FROM " + TABLA_MOLDE_AUX +
							" WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM " + TABLA_MOLDE +
					 " WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_CTA +
					 " WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);
		
			action = " DELETE FROM " + TABLA_MOLDE_NAV_BP +
					 " WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_JOURNAL +
					 " WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM " + TABLA_MOLDE_NAV_PROD +
					 " WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);
			
			action = " DELETE FROM " + TABLA_MOLDE_NAV_CC +
					 " WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_PRODDET +
					 " WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);

			action = " DELETE FROM " + TABLA_MOLDE_NAV_CCDET +
					 " WHERE ad_user_id =" + adUserID;
			DB.executeUpdateEx(action,null);
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	/***
	 * Carga temporal con informacion inicial del reporte.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 */
	private void loadData() {

		String insert ="", sql = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE_AUX + " (accounttype, parent_id, c_elementvalue_id, seqno, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";

			this.showHelp("Totales Cuentas informe...");
			
			sql = " select evig.accounttype, acctparent.parent_id, evig.c_elementvalue_id, " +
				  " acctparent.seqno, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + adUserID + ", " +				  	
				  "(sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
   				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				// " inner join vuy_infoger_acctparent acctparent on evig.c_elementvalue_id = acctparent.node_id " +   ORIGINAL
				  " inner join vuy_infoger_acctparent_ev acctparent on evig.c_elementvalue_id = acctparent.node_id "  + // vuy_infoger_acctparent_ev  suplanto ORIGINAL
				  " where fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID +
				  " and fa.ad_table_id not in(1000663,1000662,1000664,1000665) " +
				//" and evig.c_element_id = 1000008 " + ---ORIGINAL SBT
				  " and evig.c_element_id = " +this.cElementID+ // suplanto ORIGINAL SBT
				  " group by evig.accounttype, acctparent.parent_id, evig.c_elementvalue_id, " +
				  " acctparent.seqno, fa.c_period_id " +
				  " order by evig.accounttype asc, acctparent.parent_id asc, evig.c_elementvalue_id asc , fa.c_period_id asc ";
			
			DB.executeUpdateEx(insert + sql, null);
			

			// Si el informe solo se pide para cargar el total, salgo aca.
			if (this.opcionInforme == 1) return;
			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por cuenta del plan
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_CTA + " (parent_id, c_elementvalue_id, accounttype, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";

			this.showHelp("Totales Cuentas Plan...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, ev.accounttype, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + adUserID + ", " +
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID +
				  " and fa.ad_table_id not in(1000663,1000662,1000664,1000665) " +	
			    //" and evig.c_element_id = 1000008 " +  ---ORIGINAL SBT
				  " and evig.c_element_id = "+this.cElementID+ // suplanto ORIGINAL SBT
				  " group by evig.c_elementvalue_id, fa.account_id, ev.accounttype, fa.c_period_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por socio de negocio
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_BP + " (parent_id, c_elementvalue_id, c_bpartner_id, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";

			this.showHelp("Totales SNeg...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + adUserID + ", " +			
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID +
				  " and fa.ad_table_id not in(1000663,1000662,1000664,1000665) " +				  
				  " and fa.c_bpartner_id is not null " +
				  //" and evig.accounttype = 'E' " +
			   // " and evig.c_element_id = 1000008 " + -- ORIGINAL SBT
				  " and evig.c_element_id = " +this.cElementID+ // suplanto ORIGINAL SBT
				  " group by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id , fa.c_period_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);


			this.showHelp("Totales Asientos Diarios...");
			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por asiento diario
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_JOURNAL + " (parent_id, c_elementvalue_id, gl_journal_id, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";
			
			sql = " select evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + adUserID + ", " +			
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join gl_journal journal on fa.record_id = journal.gl_journal_id " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID +
				  " and fa.ad_table_id = 224 " +
				//" and evig.c_element_id = 1000008 " + -- ORIGINAL SBT
				  " and evig.c_element_id = " +this.cElementID+ // suplanto ORIGINAL SBT
				  " group by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);

			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por producto
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_PROD + " (parent_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";

			this.showHelp("Totales Producto...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + adUserID + ", " +			
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID +
				  " and fa.ad_table_id not in(1000663,1000662,1000664,1000665) " +				  
				  " and fa.c_bpartner_id is not null " +
				  " and fa.m_product_id is not null " +
			   // " and evig.c_element_id = 1000008 " + -- ORIGINAL SBT
				  " and evig.c_element_id = " +this.cElementID+ // suplanto ORIGINAL SBT
				  " group by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id , fa.m_product_id, fa.c_period_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);


			// Para informe gerencial navegable, inserto en este momento en tabla molde de detalle por producto
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_PRODDET + " (parent_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id, idreporte, " +
					" ad_user_id, ad_table_id, record_id, c_doctype_id, documentno, dateacct, saldomn, qty)";

			this.showHelp("Detalles Producto...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + adUserID + ", " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct, " +
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn, " +
				  " sum(coalesce(fa.qty,1)) as cantidad " +
				  " from fact_acct fa " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID +
				  " and fa.ad_table_id not in(1000663,1000662,1000664,1000665) " +				  
				  " and fa.c_bpartner_id is not null " +
				  " and fa.m_product_id is not null " + 
			   // " and evig.c_element_id = 1000008 " + -- ORIGINAL SBT
				  " and evig.c_element_id = " +this.cElementID+ // suplanto ORIGINAL SBT
				  " group by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id," +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct " +
				  " order by evig.c_elementvalue_id, fa.account_id, fa.c_bpartner_id, fa.m_product_id, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
			
			// Para informe gerencial navegable, inserto en este momento en tabla molde por ccosto
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_CC + " (parent_id, c_elementvalue_id, gl_journal_id, c_activity_id_1, c_period_id, idreporte, " +
					" ad_user_id, saldomn)";
			
			this.showHelp("Totales Departamento...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + adUserID + ", " +			
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join gl_journal journal on fa.record_id = journal.gl_journal_id " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID +
				  " and fa.ad_table_id = 224 " +
				  " and fa.c_activity_id_1 is not null " +
			   // " and evig.c_element_id = 1000008 " + -- ORIGINAL SBT
				  " and evig.c_element_id = " +this.cElementID+ // suplanto ORIGINAL SBT
				  " group by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id " +
				  " order by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id asc ";
				
			DB.executeUpdateEx(insert + sql, null);


			// Para informe gerencial navegable, inserto en este momento en tabla molde por detalle de ccosto
			insert = "INSERT INTO " + TABLA_MOLDE_NAV_CCDET + " (parent_id, c_elementvalue_id, gl_journal_id, c_activity_id_1, c_period_id, idreporte, " +
					" ad_user_id, ad_table_id, record_id, c_doctype_id, documentno, dateacct, saldomn) ";
			
			this.showHelp("Detalles Departamento...");
			
			sql = " select evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, " +
				  "'" + this.idReporte + "'," + adUserID + ", " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct, " +					
				  " (sum(fa.amtacctcr) - sum(amtacctdr)) as saldomn " +
				  " from fact_acct fa " +
				  " inner join gl_journal journal on fa.record_id = journal.gl_journal_id " +
				  " inner join c_elementvalue ev on fa.account_id = ev.c_elementvalue_id " +
				  " inner join uy_account_map map on ev.c_elementvalue_id = map.c_elementvalue_id " +
				  " inner join c_elementvalue evig on map.account_id = evig.c_elementvalue_id " +
				  " where fa.c_period_id >=" + this.cPeriodFromID + " and fa.c_period_id <=" + this.cPeriodToID +
				  " and fa.ad_table_id = 224 " +
				  " and fa.c_activity_id_1 is not null " +
			   // " and evig.c_element_id = 1000008 " + -- ORIGINAL SBT
				  " and evig.c_element_id = " +this.cElementID+ // suplanto ORIGINAL SBT
				  " group by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, " +
				  " fa.ad_table_id, fa.record_id, fa.c_doctype_id, fa.documentno, fa.dateacct " +				  
				  " order by evig.c_elementvalue_id, fa.account_id, journal.gl_journal_id, fa.c_activity_id_1, fa.c_period_id, fa.dateacct asc ";
				
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}


	/***
	 * Cargo datos en tabla molde del reporte.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 */
	private void updateData() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE_AUX +
				  " WHERE idreporte=? " +
				  " ORDER BY c_elementvalue_id asc, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta y periodo
			int cElementValueID = 0, cPeriodID = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Procesando " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuenta
				if (rs.getInt("c_elementvalue_id") != cElementValueID){
					
					// Si no estoy en primer registro actualizo cuenta anterior
					if (rowCount > 1){
						this.updateMoldeRecord(cElementValueID, cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					this.newMoldeRecord(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), 
									    rs.getString("accounttype"), rs.getInt("seqno"));
					
					cElementValueID = rs.getInt("c_elementvalue_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
					
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateMoldeRecord(cElementValueID, cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			this.updateMoldeRecord(cElementValueID, cPeriodID, amt);

		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		
	}

	/***
	 * Actualiza registro de temporal del reporte.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateMoldeRecord(int cElementValueID, int cPeriodID, BigDecimal amt) {
	
		try{
			
			// Posicion del periodo
			int posicion = hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.currencyType.equalsIgnoreCase("ME")){
				amt = amt.divide(hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.currencyType.equalsIgnoreCase("MM")){
				amt = amt.divide(hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			String action = " UPDATE " + TABLA_MOLDE + 
							" SET amt" + String.valueOf(posicion) + "=" + amt +
							" WHERE idreporte='" + this.idReporte + "'" +
							" AND c_elementvalue_id=" + cElementValueID;
			
			DB.executeUpdateEx(action, null);
							
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Inserta nuevo registro en temporal del reporte.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType 
	 * @param parentName
	 * @param acctName
	 */
	private void newMoldeRecord(int parentID, int cElementValueID, String accountType, int seqNo) {
		
		try{
		
			String accountTypeName = "INGRESOS";
			if (accountType != null){
				if (!accountType.equalsIgnoreCase("R")){
					accountTypeName = "EGRESOS";
				}
			}
			
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			String acctName = new MElementValue(Env.getCtx(), cElementValueID, null).getName();
			
			// Obtengo secuencia del padre
			String sql = "select seqno from ad_treenode where ad_tree_id = 1000146 and node_id =?";
			int seqNoParent = DB.getSQLValue(null, sql, parentID);
			
			String action = " INSERT INTO " + TABLA_MOLDE + " (parent_id, c_elementvalue_id, parentname, acctname, " +
							"idreporte, ad_user_id, accounttype, SeqNo, SeqNoParent) " +
		  			        " VALUES(" + parentID + "," + cElementValueID + ",'" + parentName + "','" + acctName + "','" +
							this.idReporte + "'," + adUserID + ",'" + accountTypeName + "'," +
							seqNo +	"," + seqNoParent + ")";
			
			DB.executeUpdateEx(action, null);
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}
		if (this.getGerencialAWindow() != null){
			this.gerencialAWindow.setWaitingMessage(text);
		}
	}
	
	
	/***
	 * Setea posiciones por periodo.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 */
	private void setPeriodos(){
		
		int posicion = 0;
		for (int i = this.cPeriodFromID; i <= this.cPeriodToID; i++){
			posicion++;
			hashPosicionXPeriodo.put(i, posicion);
			
			// Si el reporte no es moneda nacional
			if (!this.currencyType.equalsIgnoreCase("MN")){
				// Guardo Tasa de cambio de ultimo dia del periodo
				MPeriod period = new MPeriod(Env.getCtx(), i, null);
				BigDecimal rate = MConversionRate.getRate(100, 142, period.getEndDate(), 0, adClientID, 0);	
				if (rate == null){
					throw new AdempiereException("No se pudo obtener Tasa de Cambio para la fecha : " + period.getEndDate());
				}
				hashTasaCambioXPeriodo.put(i, rate);
			}
			
		}
		
		// Si tengo mas de 13 periodos aviso con exception
		if (posicion > 13){
			throw new AdempiereException("El período posible a considerar NO puede ser mayor a un año");
		}
	}

	
	
	
	
	/***
	 * Actualiza titulos de periodos en temporal para poder mostrarlos por nombre en el reporte.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 */
	private void updatePeriodsTitle(){
		
		try{
			String per1 = "", per2 = "", per3 = "", per4 = "", per5 = "", per6 = "", per7 = "", per8 = "";
			String per9 = "", per10 = "", per11 = "", per12 = "", per13 = "";		
			
			Object[] keys = hashPosicionXPeriodo.keySet().toArray();
			
			for (int i = 0; i < keys.length; i++){
				
				int posicicion = hashPosicionXPeriodo.get(keys[i]);
				String periodName = new MPeriod(Env.getCtx(), (Integer)keys[i], null).getName();
				
				if (posicicion == 1) per1 = periodName;
				if (posicicion == 2) per2 = periodName;
				if (posicicion == 3) per3 = periodName;
				if (posicicion == 4) per4 = periodName;
				if (posicicion == 5) per5 = periodName;
				if (posicicion == 6) per6 = periodName;
				if (posicicion == 7) per7 = periodName;
				if (posicicion == 8) per8 = periodName;
				if (posicicion == 9) per9 = periodName;
				if (posicicion == 10) per10 = periodName;
				if (posicicion == 11) per11 = periodName;
				if (posicicion == 12) per12 = periodName;
				if (posicicion == 13) per13 = periodName;
				
			}
			
			String action = "update " + TABLA_MOLDE + 
					        " set period1='" + per1 + "'," +
					        " period2='" + per2 + "'," +   
					        " period3='" + per3 + "'," +
					        " period4='" + per4 + "'," +
					        " period5='" + per5 + "'," +
					        " period6='" + per6 + "'," +
					        " period7='" + per7 + "'," +
					        " period8='" + per8 + "'," +
					        " period9='" + per9 + "'," +
					        " period10='" + per10 + "'," +
					        " period11='" + per11 + "'," +
					        " period12='" + per12 + "'," +
					        " period13='" + per13 + "' " +
					        " where idreporte='" + this.idReporte + "'";
			DB.executeUpdateEx(action, null);
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}
	
}
