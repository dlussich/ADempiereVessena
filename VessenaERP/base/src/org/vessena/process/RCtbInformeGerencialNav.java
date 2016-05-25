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
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MElementValue;
import org.compiere.model.MInvoice;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MGerencial;
import org.openup.model.MGerencialAccount;
import org.openup.model.MGerencialBP;
import org.openup.model.MGerencialCC;
import org.openup.model.MGerencialCCDet;
import org.openup.model.MGerencialDetail;
import org.openup.model.MGerencialJournal;
import org.openup.model.MGerencialMain;
import org.openup.model.MGerencialProd;
import org.openup.model.MGerencialProdDet;

/**
 * org.openup.process - RCtbInformeGerencialNav
 * OpenUp Ltda. Issue #116 
 * Description: Proceso para generacion de Informe Gerencial Navegable. 
 * @author Gabriel Vila - 18/12/2012
 * @see
 */
public class RCtbInformeGerencialNav extends SvrProcess {

	private InformeGerencial report = null;
	private int uyGerencialID = 0;
	private int opcionInforme = 0;
	private MGerencial gerencial = null;
	private HashMap<Integer, String> hashPeriodTitles = new HashMap<Integer, String>();
	
	private MGerencialAccount gerencialAccount = null;
	private MGerencialBP gerencialBP = null;
	private MGerencialDetail gerencialDet = null;
	private MGerencialJournal gerencialJournal = null;
	private MGerencialProd gerencialProd = null;
	private MGerencialCC gerencialCC = null;
	
	
	/**
	 * Constructor.
	 */
	public RCtbInformeGerencialNav() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 18/12/2012
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_Gerencial_ID")){
					if (para[i].getParameter()!=null)
						this.uyGerencialID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				
				if (name.equalsIgnoreCase("Option")){
					if (para[i].getParameter()!=null)
						this.opcionInforme = ((BigDecimal)para[i].getParameter()).intValueExact();
				}				
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 18/12/2012
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		gerencial = new MGerencial(getCtx(), this.uyGerencialID, null);
		
		// Ejecuto proceso que a igual que el reporte jasper, me carga la informacion en tablas temporales
//		this.report = new InformeGerencial(getAD_User_ID(), getAD_Client_ID(), 
//				gerencial.getC_Period_ID_From(), 
//				gerencial.getC_Period_ID_To(), gerencial.getCurrencyType(), null);
		int auxElementID = gerencial.get_ValueAsInt("C_Element_ID");
		this.report = new InformeGerencial(getAD_User_ID(), getAD_Client_ID(), 
				gerencial.getC_Period_ID_From(), 
				gerencial.getC_Period_ID_To(), gerencial.getCurrencyType(), null, auxElementID);
		
		this.report.setWaiting(this.getProcessInfo().getWaiting());
		this.report.setGerencialAWindow(this.getProcessInfo().getWindow());
		this.report.execute(this.opcionInforme);

		// Obtengo titulos de periodos
		this.getPeriodsTitles();
		
		// Cargo Informacion para nevegar
		this.loadData();
		
		return "OK";

	}

	/***
	 * Carga información en las distintas tablas.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 19/12/2012
	 * @see
	 */
	private void loadData() {

		try{
			
			this.loadMain();

			// Si opion de informe es cargar todo ahora, lo hago.
			if (this.opcionInforme == 0){
				// Cargo pestaña de cuentas contables del plan de cuentas. Segundo Nivel.
				this.loadAccounts();
				
				// Cargo pestaña de proveedores por cuenta contables de egresos. Tercer Nivel.
				this.loadBPartners();
				
				// Carga pestaña de asientos diarios por cuenta contable. Tercer Nivel
				this.loadJournals();
				
				// Cargo pestaña de productos por proveedor. Cuarto Nivel.
				this.loadProducts();
				
				// Carga pestaña de centros de costo por asiento diario. Cuarto Nivel
				this.loadCCostos();
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Carga detalle de asientos de una cuenta contable.
	 * Tercer Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 21/12/2012
	 * @see
	 */
	private void loadJournals() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_5 " + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY parent_id, c_elementvalue_id, gl_journal_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id), socio de negocio y periodo
			int parentID = 0, cElementValueID = 0, glJournalID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Asiento " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuentas y asiento
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("gl_journal_id") != glJournalID)){
					
					// Si no estoy en primer registro actualizo asiento anterior
					if (rowCount > 1){
						this.updateJournal(cPeriodID, amt);
					}

					// Inserto nuevo asiento
					seqNo++;
					this.insertJournal(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("gl_journal_id"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					glJournalID = rs.getInt("gl_journal_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateJournal(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			this.updateJournal(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	 * Carga detalle de proveedores asociados a una cuenta contable del plan. Tercer Nivel.
	 * Tanto proveedores como asientos se muestran como dos grillas juntas dentro de la pestaña de detalles.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 */
	private void loadBPartners() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_4 " + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY parent_id, c_elementvalue_id, c_bpartner_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id), socio de negocio y periodo
			int parentID = 0, cElementValueID = 0, cBPartnerID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Lineas SNeg. " + rowCount++ + " de " + totalRowCount);
				
				// Al cambiar cuenta se crea un nuevo registro de detalle.
				if ((rs.getInt("parent_id") != parentID) || (rs.getInt("c_elementvalue_id") != cElementValueID)){
					insertDetail(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"));
				}
				
				// Corte por cuentas y socio de negocio
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("c_bpartner_id") != cBPartnerID)){
					
					// Si no estoy en primer registro actualizo proveedor anterior
					if (rowCount > 1){
						this.updateBP(cPeriodID, amt);
					}

					// Inserto nuevo proveedor
					seqNo++;
					this.insertBP(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("c_bpartner_id"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					cBPartnerID = rs.getInt("c_bpartner_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateBP(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			this.updateBP(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	 * Nuevo registro de detalle (que luego incluira grilla de proveedores y asientos) para una
	 * determinada cuenta. Tercer Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 21/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 */
	private void insertDetail(int parentID, int cElementValueID) {

		try{
			
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);

			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");
			
			if ((germain == null) || (germain.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + parentID +  " en Primer Nivel.");
			
			this.gerencialDet = new MGerencialDetail(getCtx(), 0, null);
			this.gerencialDet.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialDet.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialDet.setUY_Gerencial_Account_ID(geraccount.get_ID());
			this.gerencialDet.setParent_ID(parentID);
			this.gerencialDet.setparentname(parentName);
			this.gerencialDet.setC_ElementValue_ID(cElementValueID);
			
			this.gerencialDet.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}

		
	}

	/***
	 * Carga detalle de cuentas contables del plan de cuenta asociadas a las cuentas del informe.
	 * Tanto proveedores como asientos se muestran como dos grillas juntas dentro de la pestaña de detalles. 
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 */
	private void loadAccounts() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_3 " + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY parent_id, c_elementvalue_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id) y periodo
			int parentID = 0, cElementValueID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Procesando " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuentas
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)){
					
					// Si no estoy en primer registro actualizo cuenta anterior
					if (rowCount > 1){
						this.updateAccount(cPeriodID, amt);
					}

					// Inserto nuevo registro en temporal final
					seqNo++;
					this.insertAccount(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), 
									   rs.getString("accounttype"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateAccount(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			this.updateAccount(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	 * Carga totales. Primer Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 19/12/2012
	 * @see
	 */
	private void loadMain() {

		try{
		
			int processID = MProcess.getProcess_ID("UY_RCtb_InfGerCtaPlan", null);
			
			DB.executeUpdateEx(" delete from uy_gerencial_main cascade where uy_gerencial_id=" + this.gerencial.get_ID(), null);
			
			String insert = " insert into uy_gerencial_main (uy_gerencial_main_id, uy_gerencial_id," +
					"ad_client_id, ad_org_id, isactive, updated, updatedby, created, createdby, " +
					"parent_id, c_elementvalue_id, amt1, amt2, amt3, amt4, amt5, amt6, amt7, " +
					//"amt8, amt9, amt10, amt11, amt12, amt13, accounttype, seqnoparent, seqno, colorselector, ad_process_id) "; 
					"amt8, amt9, amt10, amt11, amt12, amt13, accounttype, seqnoparent, seqno, ad_process_id, colorbgselector) ";

			String sql = " select nextid(1001591,'N')," + this.uyGerencialID + "," + getAD_Client_ID() + "," +
						 "0, 'Y','" + gerencial.getUpdated() + "'," + gerencial.getUpdatedBy() + ",'" +
						 gerencial.getCreated() + "'," + gerencial.getCreatedBy() + ", molde.parent_id, " +
						 "molde.c_elementvalue_id, molde.amt1, molde.amt2, molde.amt3, molde.amt4, " +
						 "molde.amt5, molde.amt6, molde.amt7, molde.amt8, molde.amt9, molde.amt10, molde.amt11, " +
						 "molde.amt12, molde.amt13, molde.accounttype, molde.seqnoparent, molde.seqno, " +
						 //" case when molde.accounttype = 'EGRESOS' then 'DARK_BLUE' else 'THIN_BLUE' end, " + processID +
						 processID + ", 'WHITE' " +
						 " from uy_molde_informegerencial molde " +
						 " where ad_user_id =" + getAD_User_ID() +
						 " order by molde.seqnoparent, molde.seqno ";

			DB.executeUpdateEx(insert + sql, null);
			
			// Actualizo campo record_id de la tabla para que pueda procesar con doble-click
			DB.executeUpdate(" update uy_gerencial_main set record_id = uy_gerencial_main_id where uy_gerencial_id =" + this.uyGerencialID, null);
			
			// Cargo registros de totales por cuenta padre
			this.loadMainParents();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}		
	}

	/***
	 * A modo visual doy de alta registros de totales por cuenta padre en el primer nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 19/12/2012
	 * @see
	 */
	private void loadMainParents() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
			sql =" select parent_id, seqnoparent, sum(amt1) amt1, sum(amt2) amt2, sum(amt3) amt3, " +
				 " sum(amt4) amt4, sum(amt5) amt5, sum(amt6) amt6, sum(amt7) amt7, sum(amt8) amt8, sum(amt9) amt9, " +
				 " sum(amt10) amt10, sum(amt11) amt11, sum(amt12) amt12, sum(amt13) amt13 " +
				 " from uy_gerencial_main " +
				 " where uy_gerencial_id =? " +
				 " group by parent_id, seqnoparent " +
				 " order by seqnoparent";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.gerencial.get_ID());
			
			rs = pstmt.executeQuery ();
		
			
			
			while (rs.next()){
				MGerencialMain germain = new MGerencialMain(getCtx(), 0, null);
				germain.setUY_Gerencial_ID(this.gerencial.get_ID());
				germain.setParent_ID(rs.getInt("parent_id"));
				
				MElementValue ev = new MElementValue(getCtx(), rs.getInt("parent_id"), null);
				germain.setparentname(ev.getName());
				
				germain.setseqnoparent(rs.getInt("seqnoparent"));
				germain.setSeqNo(-1);
				germain.setamt1(rs.getBigDecimal("amt1"));
				germain.setamt2(rs.getBigDecimal("amt2"));
				germain.setamt3(rs.getBigDecimal("amt3"));
				germain.setamt4(rs.getBigDecimal("amt4"));
				germain.setamt5(rs.getBigDecimal("amt5"));
				germain.setamt6(rs.getBigDecimal("amt6"));
				germain.setamt7(rs.getBigDecimal("amt7"));
				germain.setamt8(rs.getBigDecimal("amt8"));
				germain.setamt9(rs.getBigDecimal("amt9"));
				germain.setamt10(rs.getBigDecimal("amt10"));
				germain.setamt11(rs.getBigDecimal("amt11"));
				germain.setamt12(rs.getBigDecimal("amt12"));
				germain.setamt13(rs.getBigDecimal("amt13"));

				germain.saveEx();
			}
			
			// Cambio dinamicamente los headers de la grilla (totales primer nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//GridTab gt = this.getProcessInfo().getWindow().getAPanel().getGridTab(1);
			//this.changePeriodsHeaders(gt);

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
	 * Cambia dinamicamente el texto de los headers de los campos de una determinada pestaña.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 19/12/2012
	 * @see
	 * @param gridTab
	 */
	@SuppressWarnings("unused")
	private void changePeriodsHeaders(GridTab gridTab){
		
		try{

			//gridTab.getTableModel().close(true);
			
			GridField[] fields = gridTab.getFields();
			
			for (int i = 0; i < fields.length; i++){
				GridField field = fields[i];
				
				if (field.getVO().ColumnName.equalsIgnoreCase("amt1")){
					field.getVO().Header = hashPeriodTitles.get(1).toString().toUpperCase();
					field.updateContext();
				}
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt2")){
					field.getVO().Header = hashPeriodTitles.get(2).toString().toUpperCase();  
				}
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt3")){
					field.getVO().Header = hashPeriodTitles.get(3).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt4")){
					field.getVO().Header = hashPeriodTitles.get(4).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt5")){
					field.getVO().Header = hashPeriodTitles.get(5).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt6")){
					field.getVO().Header = hashPeriodTitles.get(6).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt7")){
					field.getVO().Header = hashPeriodTitles.get(7).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt8")){
					field.getVO().Header = hashPeriodTitles.get(8).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt9")){
					field.getVO().Header = hashPeriodTitles.get(9).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt10")){
					field.getVO().Header = hashPeriodTitles.get(10).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt11")){
					field.getVO().Header = hashPeriodTitles.get(11).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt12")){
					field.getVO().Header = hashPeriodTitles.get(12).toString().toUpperCase();  
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt13")){
					field.getVO().Header = hashPeriodTitles.get(13).toString().toUpperCase();  
				}
			}

			// Refresco tab para que tome los cambios
			gridTab.dataRefresh();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		
	}
	
	/***
	 * Obtiene titulos de periodos desde molde.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 19/12/2012
	 * @see
	 */
	private void getPeriodsTitles(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
			
			hashPeriodTitles = new HashMap<Integer, String>();
			
			sql =" select coalesce(period1,'') as period1, coalesce(period2,'') as period2, coalesce(period3,'') as period3," +
				 " coalesce(period4,'') as period4, coalesce(period5,'') as period5, coalesce(period6,'') as period6," +
				 " coalesce(period7,'') as period7, coalesce(period8,'') as period8, coalesce(period9,'') as period9," +	
				 " coalesce(period10,'') as period10, coalesce(period11,'') as period11, coalesce(period12,'') as period12," +
				 " coalesce(period13,'') as period13 " +				 
				 " from uy_molde_informegerencial " +
				 " where ad_user_id = ? ";	
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getAD_User_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				hashPeriodTitles.put(1, rs.getString("period1"));
				hashPeriodTitles.put(2, rs.getString("period2"));
				hashPeriodTitles.put(3, rs.getString("period3"));
				hashPeriodTitles.put(4, rs.getString("period4"));
				hashPeriodTitles.put(5, rs.getString("period5"));
				hashPeriodTitles.put(6, rs.getString("period6"));
				hashPeriodTitles.put(7, rs.getString("period7"));
				hashPeriodTitles.put(8, rs.getString("period8"));
				hashPeriodTitles.put(9, rs.getString("period9"));
				hashPeriodTitles.put(10, rs.getString("period10"));
				hashPeriodTitles.put(11, rs.getString("period11"));
				hashPeriodTitles.put(12, rs.getString("period12"));
				hashPeriodTitles.put(13, rs.getString("period13"));
			}

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
	 * Despliega avance en ventana splash
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 * @param text
	 */
	private void showHelp(String text){
		if (this.getProcessInfo().getWaiting() != null){
			this.getProcessInfo().getWaiting().setText(text);
		}
		if (this.getProcessInfo().getWindow() != null){
			this.getProcessInfo().getWindow().setWaitingMessage(text);
		}
	}


	/***
	 * Inserta nuevo registro en tabla de cuentas del plan por cuenta de informe.
	 * Segundo Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param accountType
	 * @param seqNo
	 */
	private void insertAccount(int parentID, int cElementValueID, String accountType, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();
			
			int seqNoParent = 0;
			
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);
			
			if ((germain == null) || (germain.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + parentID +  " en Primer Nivel.");
			
			this.gerencialAccount = new MGerencialAccount(getCtx(), 0, null);
			this.gerencialAccount.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialAccount.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialAccount.setParent_ID(parentID);
			this.gerencialAccount.setparentname(parentName);
			this.gerencialAccount.setC_ElementValue_ID(cElementValueID);
			this.gerencialAccount.setAccountType(accountType);
			this.gerencialAccount.setseqnoparent(seqNoParent);
			this.gerencialAccount.setSeqNo(seqNo);
			
			this.gerencialAccount.saveEx();
			
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	/**
	 * Inserta registro de proveedor para cuenta contable.
	 * Tercer Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cBPartnerID
	 * @param seqNo
	 */
	private void insertBP(int parentID, int cElementValueID, int cBPartnerID, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();

			int seqNoParent = 0;
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);

			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");
			
			this.gerencialBP = new MGerencialBP(getCtx(), 0, null);
			this.gerencialBP.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialBP.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialBP.setUY_Gerencial_Account_ID(geraccount.get_ID());
			this.gerencialBP.setUY_Gerencial_Detail_ID(this.gerencialDet.get_ID());
			this.gerencialBP.setParent_ID(parentID);
			this.gerencialBP.setC_ElementValue_ID(cElementValueID);
			this.gerencialBP.setparentname(parentName);
			this.gerencialBP.setC_BPartner_ID(cBPartnerID);
			this.gerencialBP.setseqnoparent(seqNoParent);
			this.gerencialBP.setSeqNo(seqNo);
			
			this.gerencialBP.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	/***
	 * Inserta informacion de asiento en grilla. Tercer Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 21/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param glJournalID
	 * @param seqNo
	 */
	private void insertJournal(int parentID, int cElementValueID, int glJournalID, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();

			int seqNoParent = 0;

			// Obtengo ID del registro del tercer nivel para el detalle
			MGerencialDetail gerDetail = MGerencialDetail.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			
			// Si no tengo detalle puede ser porque es una cuenta que al no tener proveedor no le fue creado un detalle
			// Creo el registro de detalle ahora
			if (gerDetail == null){
				insertDetail(parentID, cElementValueID);
			}
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);
		
			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");

			this.gerencialJournal = new MGerencialJournal(getCtx(), 0, null);
			this.gerencialJournal.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialJournal.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialJournal.setUY_Gerencial_Account_ID(geraccount.get_ID());
			
			if (gerDetail == null){
				this.gerencialJournal.setUY_Gerencial_Detail_ID(this.gerencialDet.get_ID());
			}
			else{
				this.gerencialJournal.setUY_Gerencial_Detail_ID(gerDetail.get_ID());	
			}		
			
			this.gerencialJournal.setParent_ID(parentID);
			this.gerencialJournal.setC_ElementValue_ID(cElementValueID);
			this.gerencialJournal.setparentname(parentName);
			this.gerencialJournal.setGL_Journal_ID(glJournalID);
			this.gerencialJournal.setseqnoparent(seqNoParent);
			this.gerencialJournal.setSeqNo(seqNo);
			
			this.gerencialJournal.saveEx();

			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	
	/***
	 * Actualiza datos de una cuenta del plan asociada a una cuenta informe.
	 * Segundo Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateAccount(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.report.hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.gerencial.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.gerencial.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) this.gerencialAccount.setamt1(amt); 
			else if (posicion == 2) this.gerencialAccount.setamt2(amt);
			else if (posicion == 3) this.gerencialAccount.setamt3(amt);
			else if (posicion == 4) this.gerencialAccount.setamt4(amt);
			else if (posicion == 5) this.gerencialAccount.setamt5(amt);
			else if (posicion == 6) this.gerencialAccount.setamt6(amt);
			else if (posicion == 7) this.gerencialAccount.setamt7(amt);
			else if (posicion == 8) this.gerencialAccount.setamt8(amt);
			else if (posicion == 9) this.gerencialAccount.setamt9(amt);
			else if (posicion == 10) this.gerencialAccount.setamt10(amt);
			else if (posicion == 11) this.gerencialAccount.setamt11(amt);
			else if (posicion == 12) this.gerencialAccount.setamt12(amt);
			else if (posicion == 13) this.gerencialAccount.setamt13(amt);
			
			this.gerencialAccount.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	
	/***
	 * Actualizo importe de un proveedor segun periodo.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 21/12/2012
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateBP(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.report.hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.gerencial.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.gerencial.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) this.gerencialBP.setamt1(amt); 
			else if (posicion == 2) this.gerencialBP.setamt2(amt);
			else if (posicion == 3) this.gerencialBP.setamt3(amt);
			else if (posicion == 4) this.gerencialBP.setamt4(amt);
			else if (posicion == 5) this.gerencialBP.setamt5(amt);
			else if (posicion == 6) this.gerencialBP.setamt6(amt);
			else if (posicion == 7) this.gerencialBP.setamt7(amt);
			else if (posicion == 8) this.gerencialBP.setamt8(amt);
			else if (posicion == 9) this.gerencialBP.setamt9(amt);
			else if (posicion == 10) this.gerencialBP.setamt10(amt);
			else if (posicion == 11) this.gerencialBP.setamt11(amt);
			else if (posicion == 12) this.gerencialBP.setamt12(amt);
			else if (posicion == 13) this.gerencialBP.setamt13(amt);
			
			this.gerencialBP.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	
	/***
	 * Actualizo importe de un asiento segun periodo.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 21/12/2012
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateJournal(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.report.hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.gerencial.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.gerencial.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) this.gerencialJournal.setamt1(amt); 
			else if (posicion == 2) this.gerencialJournal.setamt2(amt);
			else if (posicion == 3) this.gerencialJournal.setamt3(amt);
			else if (posicion == 4) this.gerencialJournal.setamt4(amt);
			else if (posicion == 5) this.gerencialJournal.setamt5(amt);
			else if (posicion == 6) this.gerencialJournal.setamt6(amt);
			else if (posicion == 7) this.gerencialJournal.setamt7(amt);
			else if (posicion == 8) this.gerencialJournal.setamt8(amt);
			else if (posicion == 9) this.gerencialJournal.setamt9(amt);
			else if (posicion == 10) this.gerencialJournal.setamt10(amt);
			else if (posicion == 11) this.gerencialJournal.setamt11(amt);
			else if (posicion == 12) this.gerencialJournal.setamt12(amt);
			else if (posicion == 13) this.gerencialJournal.setamt13(amt);
			
			this.gerencialJournal.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	/***
	 * Carga detalle de productos asociados a un proveedor. Cuarto Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 18/02/2013
	 * @see
	 */
	private void loadProducts() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_6 " + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY parent_id, c_elementvalue_id, c_bpartner_id, m_product_id, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id), socio de negocio, producto y periodo
			int parentID = 0, cElementValueID = 0, cBPartnerID = 0, mProductID = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("Lineas Prod. " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuentas, socio de negocio y producto
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("c_bpartner_id") != cBPartnerID)
						|| (rs.getInt("m_product_id") != mProductID)){
					
					// Si no estoy en primer registro actualizo producto anterior
					if (rowCount > 1){
						this.updateProduct(cPeriodID, amt);
					}

					// Inserto nuevo producto
					seqNo++;
					this.insertProduct(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("c_bpartner_id"), 
									   rs.getInt("m_product_id"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					cBPartnerID = rs.getInt("c_bpartner_id");
					mProductID = rs.getInt("m_product_id");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateProduct(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			this.updateProduct(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	 * Actualizo importe de un producto segun periodo.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 18/02/2013
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateProduct(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.report.hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.gerencial.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.gerencial.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) this.gerencialProd.setamt1(amt); 
			else if (posicion == 2) this.gerencialProd.setamt2(amt);
			else if (posicion == 3) this.gerencialProd.setamt3(amt);
			else if (posicion == 4) this.gerencialProd.setamt4(amt);
			else if (posicion == 5) this.gerencialProd.setamt5(amt);
			else if (posicion == 6) this.gerencialProd.setamt6(amt);
			else if (posicion == 7) this.gerencialProd.setamt7(amt);
			else if (posicion == 8) this.gerencialProd.setamt8(amt);
			else if (posicion == 9) this.gerencialProd.setamt9(amt);
			else if (posicion == 10) this.gerencialProd.setamt10(amt);
			else if (posicion == 11) this.gerencialProd.setamt11(amt);
			else if (posicion == 12) this.gerencialProd.setamt12(amt);
			else if (posicion == 13) this.gerencialProd.setamt13(amt);
			
			this.gerencialProd.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	
	/**
	 * Inserta registro de producto para un determinado proveedor.
	 * Cuartro Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 18/02/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param cBPartnerID
	 * @param seqNo
	 */
	private void insertProduct(int parentID, int cElementValueID, int cBPartnerID, int mProductID, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();

			int seqNoParent = 0;
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);
			// Obtengo ID del registro de tercer nivel para proveedor y cuenta informe
			MGerencialBP gerBP = MGerencialBP.forAccountBP(getCtx(), this.gerencial.get_ID(), parentID, cBPartnerID, null);
			
			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");
			
			this.gerencialProd = new MGerencialProd(getCtx(), 0, null);
			this.gerencialProd.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialProd.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialProd.setUY_Gerencial_Account_ID(geraccount.get_ID());
			this.gerencialProd.setParent_ID(parentID);
			this.gerencialProd.setC_ElementValue_ID(cElementValueID);
			this.gerencialProd.setparentname(parentName);
			this.gerencialProd.setC_BPartner_ID(cBPartnerID);
			this.gerencialProd.setM_Product_ID(mProductID);
			this.gerencialProd.setUY_Gerencial_BP_ID(gerBP.get_ID());
			this.gerencialProd.setseqnoparent(seqNoParent);
			this.gerencialProd.setSeqNo(seqNo);
			
			this.gerencialProd.saveEx();
			
			// Cargo detalle de este producto
			this.loadProductDetails();
			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}

	
	/***
	 * Carga detalle de centros de costo de un asiento de una cuenta contable.
	 * Cuarto Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 18/02/2013
	 * @see
	 */
	private void loadCCostos() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_7 " + 
				  " WHERE ad_user_id =? " +
				  " ORDER BY parent_id, c_elementvalue_id, gl_journal_id, c_activity_id_1, c_period_id asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();

			// Corte por cuenta informe (parent_id), cuenta plan (c_elementvalue_id), asiento, centro de costo y periodo
			int parentID = 0, cElementValueID = 0, glJournalID = 0, cActivityID1 = 0, cPeriodID = 0, seqNo = 0;
			
			BigDecimal amt = Env.ZERO;
			
			// Recorro registros y voy actualizando
			while (rs.next()){
				
				this.showHelp("CCosto " + rowCount++ + " de " + totalRowCount);
				
				// Corte por cuentas, asiento y centro de costo
				if ((rs.getInt("parent_id") != parentID) 
						|| (rs.getInt("c_elementvalue_id") != cElementValueID)
						|| (rs.getInt("gl_journal_id") != glJournalID)
						|| (rs.getInt("c_activity_id_1") != cActivityID1)){
					
					// Si no estoy en primer registro actualizo asiento anterior
					if (rowCount > 1){
						this.updateCCosto(cPeriodID, amt);
					}

					// Inserto nuevo ccosto
					seqNo++;
					this.insertCCosto(rs.getInt("parent_id"), rs.getInt("c_elementvalue_id"), rs.getInt("gl_journal_id"), 
									  rs.getInt("c_activity_id_1"), seqNo);
					
					parentID = rs.getInt("parent_id");
					cElementValueID = rs.getInt("c_elementvalue_id");
					glJournalID = rs.getInt("gl_journal_id");
					cActivityID1 = rs.getInt("c_activity_id_1");
					cPeriodID = rs.getInt("c_period_id");
					amt = Env.ZERO;
				}
				else{
					// Corte por periodo
					if (rs.getInt("c_period_id") != cPeriodID){
						
						this.updateCCosto(cPeriodID, amt);
						cPeriodID = rs.getInt("c_period_id");
						amt = Env.ZERO;
					}
				}
				amt = amt.add(rs.getBigDecimal("saldomn"));
			}
			
			// Ultimo registro
			this.updateCCosto(cPeriodID, amt);

			// Cambio dinamicamente los headers de la grilla (totales segundo nivel) correspondiente a periodos.
			//GridTab gridTab = ((org.compiere.grid.GridController)((VTabbedPane)((CPanel)this.getProcessInfo().getWindow().getAPanel().getComponents()[0]).getComponent(0)).getComponents()[4]).getMTab();
			//this.changePeriodsHeaders(gridTab);
			
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
	 * Actualizo centro de costo de un asiento segun periodo.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 18/02/2013
	 * @see
	 * @param cPeriodID
	 * @param amt
	 */
	private void updateCCosto(int cPeriodID, BigDecimal amt) {
		
		try{
			
			// Posicion del periodo
			int posicion = this.report.hashPosicionXPeriodo.get(cPeriodID);
			
			// Monto segun tipo de moneda
			// Dolares
			if (this.gerencial.getCurrencyType().equalsIgnoreCase("ME")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP);
			}
			// Miles de dolares
			else if (this.gerencial.getCurrencyType().equalsIgnoreCase("MM")){
				amt = amt.divide(this.report.hashTasaCambioXPeriodo.get(cPeriodID), 2, RoundingMode.HALF_UP)
						.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
			}
			
			if (posicion == 1) this.gerencialCC.setamt1(amt); 
			else if (posicion == 2) this.gerencialCC.setamt2(amt);
			else if (posicion == 3) this.gerencialCC.setamt3(amt);
			else if (posicion == 4) this.gerencialCC.setamt4(amt);
			else if (posicion == 5) this.gerencialCC.setamt5(amt);
			else if (posicion == 6) this.gerencialCC.setamt6(amt);
			else if (posicion == 7) this.gerencialCC.setamt7(amt);
			else if (posicion == 8) this.gerencialCC.setamt8(amt);
			else if (posicion == 9) this.gerencialCC.setamt9(amt);
			else if (posicion == 10) this.gerencialCC.setamt10(amt);
			else if (posicion == 11) this.gerencialCC.setamt11(amt);
			else if (posicion == 12) this.gerencialCC.setamt12(amt);
			else if (posicion == 13) this.gerencialCC.setamt13(amt);
			
			this.gerencialCC.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}	
	
	/***
	 * Inserta informacion de un centro de costo de un asiento. Cuarto Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 18/02/2013
	 * @see
	 * @param parentID
	 * @param cElementValueID
	 * @param glJournalID
	 * @param cActivityID1
	 * @param seqNo
	 */
	private void insertCCosto(int parentID, int cElementValueID, int glJournalID, int cActivityID1, int seqNo) {
		
		try{
		
			String parentName = new MElementValue(Env.getCtx(), parentID, null).getName();

			int seqNoParent = 0;

			// Obtengo ID del registro del tercer nivel para el detalle
			MGerencialDetail gerDetail = MGerencialDetail.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			
			// Si no tengo detalle puede ser porque es una cuenta que al no tener proveedor no le fue creado un detalle
			// Creo el registro de detalle ahora
			if (gerDetail == null){
				insertDetail(parentID, cElementValueID);
			}
			
			// Obtengo ID del registro del segundo nivel para la cuenta contable
			MGerencialAccount geraccount = MGerencialAccount.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, cElementValueID, null);
			// Obtengo ID del registro del primer nivel para la cuenta informe
			MGerencialMain germain = MGerencialMain.forAccountID(getCtx(), this.gerencial.get_ID(), parentID, null);
			// Obtengo ID del registro de tercer nivel para cuenta - asiento.
			MGerencialJournal gerjour = MGerencialJournal.forAccountJournal(getCtx(), this.gerencial.get_ID(), parentID, glJournalID, null);
			
			if ((geraccount == null) || (geraccount.get_ID() <= 0))
				throw new AdempiereException("No se pudo obtener ID para cuenta : " + cElementValueID +  " en Segundo Nivel.");

			this.gerencialCC = new MGerencialCC(getCtx(), 0, null);
			this.gerencialCC.setUY_Gerencial_ID(this.gerencial.get_ID());
			this.gerencialCC.setUY_Gerencial_Main_ID(germain.get_ID());
			this.gerencialCC.setUY_Gerencial_Account_ID(geraccount.get_ID());
			this.gerencialCC.setParent_ID(parentID);
			this.gerencialCC.setC_ElementValue_ID(cElementValueID);
			this.gerencialCC.setparentname(parentName);
			this.gerencialCC.setGL_Journal_ID(glJournalID);
			this.gerencialCC.setC_Activity_ID_1(cActivityID1);
			this.gerencialCC.setUY_Gerencial_Journal_ID(gerjour.get_ID());
			this.gerencialCC.setseqnoparent(seqNoParent);
			this.gerencialCC.setSeqNo(seqNo);
			
			this.gerencialCC.saveEx();
			
			// Cargo detalle de este departamento
			this.loadCCDetails();


			
		}
		catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		
	}
	
	
	/***
	 * Carga detalle de productos. Quinto Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 02/03/2013
	 * @see
	 */
	private void loadProductDetails() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_8 " + 
				  " WHERE ad_user_id =? " +
				  " AND parent_id =" + this.gerencialProd.getParent_ID() +
				  " AND c_elementvalue_id =" + this.gerencialProd.getC_ElementValue_ID() +
				  " AND c_bpartner_id =" + this.gerencialProd.getC_BPartner_ID() +
				  " AND m_product_id =" + this.gerencialProd.getM_Product_ID() +
				  " ORDER BY dateacct asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			//rs.last();
			//int totalRowCount = rs.getRow(), rowCount = 0;
			//rs.beforeFirst();

			// Recorro registros y voy actualizando
			while (rs.next()){
				
				//this.showHelp("Detalle Prod. " + rowCount++ + " de " + totalRowCount);

				MGerencialProdDet det = new MGerencialProdDet(getCtx(), 0, null);
				det.setUY_Gerencial_ID(this.gerencialProd.getUY_Gerencial_ID());
				det.setUY_Gerencial_Main_ID(this.gerencialProd.getUY_Gerencial_Main_ID());
				det.setUY_Gerencial_Account_ID(this.gerencialProd.getUY_Gerencial_Account_ID());
				det.setParent_ID(this.gerencialProd.getParent_ID());
				det.setC_ElementValue_ID(this.gerencialProd.getC_ElementValue_ID());
				det.setparentname(this.gerencialProd.getparentname());
				det.setacctname(this.gerencialProd.getacctname());
				det.setC_BPartner_ID(this.gerencialProd.getC_BPartner_ID());
				det.setM_Product_ID(this.gerencialProd.getM_Product_ID());
				det.setAccountType(this.gerencialProd.getAccountType());
				det.setUY_Gerencial_Prod_ID(this.gerencialProd.get_ID());
				det.setAD_Table_ID(rs.getInt("ad_table_id"));
				det.setRecord_ID(rs.getInt("record_id"));
				det.setC_DocType_ID(rs.getInt("c_doctype_id"));
				det.setDocumentNo(rs.getString("documentno"));
				det.setDateAcct(rs.getTimestamp("dateacct"));
				det.setC_Period_ID(rs.getInt("c_period_id"));
				det.setAmt(rs.getBigDecimal("saldomn"));
				
				BigDecimal qty = rs.getBigDecimal("qty");
				if (qty.compareTo(Env.ZERO) == 0) qty = Env.ONE;
				det.setQty(qty);
				
				// Para facturas tengo que obtener la cantidad directamente desde el comprobante y no desde la contabilidad
				if (det.getAD_Table_ID() == 318){
					qty = MInvoice.getProductTotalQty(getCtx(), det.getRecord_ID(), det.getM_Product_ID(), null);
					if (qty != null){
						if (qty.compareTo(Env.ZERO) == 0) qty = Env.ONE;
						det.setQty(qty);
					}
				}
				
				BigDecimal price = det.getAmt().divide(det.getQty(), 3, RoundingMode.HALF_UP);
				det.setPrice(price);
				det.saveEx();
			}
			
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
	 * Carga detalle de departamentos. Quinto Nivel.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 04/03/2013
	 * @see
	 */
	private void loadCCDetails() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM uy_molde_informegerencial_9 " + 
				  " WHERE ad_user_id =? " +
				  " AND parent_id =" + this.gerencialCC.getParent_ID() +
				  " AND c_elementvalue_id =" + this.gerencialCC.getC_ElementValue_ID() +
				  " AND gl_journal_id =" + this.gerencialCC.getGL_Journal_ID() +
				  " AND c_activity_id_1 =" + this.gerencialCC.getC_Activity_ID_1() +
				  " ORDER BY dateacct asc ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.getAD_User_ID());

			rs = pstmt.executeQuery ();

			//rs.last();
			//int totalRowCount = rs.getRow(), rowCount = 0;
			//rs.beforeFirst();

			// Recorro registros y voy actualizando
			while (rs.next()){
				
				//this.showHelp("Detalle Prod. " + rowCount++ + " de " + totalRowCount);

				MGerencialCCDet det = new MGerencialCCDet(getCtx(), 0, null);
				det.setUY_Gerencial_ID(this.gerencialCC.getUY_Gerencial_ID());
				det.setUY_Gerencial_Main_ID(this.gerencialCC.getUY_Gerencial_Main_ID());
				det.setUY_Gerencial_Account_ID(this.gerencialCC.getUY_Gerencial_Account_ID());
				det.setParent_ID(this.gerencialCC.getParent_ID());
				det.setC_ElementValue_ID(this.gerencialCC.getC_ElementValue_ID());
				det.setparentname(this.gerencialCC.getparentname());
				det.setacctname(this.gerencialCC.getacctname());
				det.setGL_Journal_ID(this.gerencialCC.getGL_Journal_ID());
				det.setC_Activity_ID_1(this.gerencialCC.getC_Activity_ID_1());
				det.setAccountType(this.gerencialCC.getAccountType());
				det.setUY_Gerencial_CC_ID(this.gerencialCC.get_ID());
				det.setAD_Table_ID(rs.getInt("ad_table_id"));
				det.setRecord_ID(rs.getInt("record_id"));
				det.setC_DocType_ID(rs.getInt("c_doctype_id"));
				det.setDocumentNo(rs.getString("documentno"));
				det.setDateAcct(rs.getTimestamp("dateacct"));
				det.setC_Period_ID(rs.getInt("c_period_id"));
				det.setAmt(rs.getBigDecimal("saldomn"));
				det.saveEx();
			}
			
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

	
}
