package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MRFduCajaControl;

public class RFduCajaControl extends SvrProcess {

	private Timestamp fechaControl;
	private int uyFduCurrencyID;
	private int uyFduActivityID;
	private static String TABLA = "UY_RFdu_CajaControl";
	private static String GL_JOURNAL = "GL_Journal";
	private static String GL_JOURNALLINE = "GL_Journalline";

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("fecha")) {
					this.fechaControl = (Timestamp) para[i].getParameter();
				}
				if (name.equalsIgnoreCase("C_Currency_ID")) {
					if (name.equalsIgnoreCase("C_Currency_ID")) {
						if(para[i].getParameter()==null){
							
							this.uyFduCurrencyID = 0;
						}
						else{
							this.uyFduCurrencyID = ((BigDecimal) para[i].getParameter())
									.intValueExact();
							
						}
					
				}
				if (name.equalsIgnoreCase("C_Activity_ID")) {
					if(para[i].getParameter()==null){
						
						this.uyFduActivityID = 0;
					}
					else{
						this.uyFduActivityID = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
					
				}
			}
		}
		}
			

	}

	@Override
	protected String doIt() throws Exception {

		this.deleteInstanciasViejasReporte();

		this.execute();

		return "OK";
	}

	/**
	 * 
	 * OpenUp Ltda. Issue # 1932 Limpiamos la tabla molde de los datos
	 * anteriores
	 * 
	 * @author Leonardo Boccone - 12/03/2014
	 * @see
	 */

	private void deleteInstanciasViejasReporte() {

		// limpiamos la tabla molde de sus datos anteriores
		String sql = "";

		try {
			this.showHelp("Eliminando datos anteriores...");
			sql = "TRUNCATE " + TABLA;
			DB.executeUpdateEx(sql, null);
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	private void showHelp(String text) {
		if (this.getProcessInfo().getWaiting() != null) {
			this.getProcessInfo().getWaiting().setText(text);
		}
	}

	private void execute() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Collection<MRFduCajaControl> aux= new ArrayList<MRFduCajaControl>();
		HashMap<String, MRFduCajaControl> listaControl = new HashMap<String, MRFduCajaControl>();

		try {

			String whereFiltros = "";

			if (this.uyFduActivityID > 0) whereFiltros += " AND jl.C_Activity_ID_1 =" + this.uyFduActivityID;
			if (this.uyFduCurrencyID > 0)whereFiltros += " AND j.C_Currency_ID =" + this.uyFduCurrencyID;
						
			sql = "SELECT  j.dateacct, jl.description, jl.amtacctdr,jl.amtacctcr, jl.dateacct, e.Value, jl.amtsourcedr, jl.amtsourcecr, jl.C_Activity_ID_1, j.C_Currency_ID " +
					" FROM " + GL_JOURNAL + " " + " j"
					+ " INNER JOIN "
					+ GL_JOURNALLINE 				
					+ " jl on j.GL_JOURNAL_id = jl.GL_JOURNAL_id"
					+ " INNER JOIN "
					+ "C_ElementValue" 				
					+ " e on e.C_ElementValue_id = jl.C_ElementValue_id "
					+ " WHERE j.docstatus='CO' "
					+ " and j.dateacct ='"
					+ this.fechaControl + "'"
					+ " and j.isAsientoCaja ='Y'"				
					+ whereFiltros;

			pstmt = DB.prepareStatement(sql, null);						
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				MRFduCajaControl cajaControl = new MRFduCajaControl(getCtx(),0,get_TrxName());
				cajaControl.setC_Activity_ID(rs.getInt("C_Activity_ID_1"));
				cajaControl.setC_Currency_ID(rs.getInt("C_Currency_ID"));
				
				String key = cajaControl.getC_Currency_ID() + "_"+ cajaControl.getC_Activity();				
				

				if (!listaControl.containsKey(key)) {			
				
					listaControl.put(key, cajaControl);						
				
					
				}
				
			}	
			
			aux=listaControl.values();			
			for(MRFduCajaControl m : aux){				
				m.setfecha(this.fechaControl);
				m.updateSaldosFinales();
				m.saveEx();
			}
				
			

		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally 
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}


}
