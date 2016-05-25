package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MRFduCajaControl extends X_UY_RFdu_CajaControl{

	private static final long serialVersionUID = 7048226569555322352L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFdu_CajaControl_ID
	 * @param trxName
	 */
	public MRFduCajaControl(Properties ctx, int UY_RFdu_CajaControl_ID,
			String trxName) {
		super(ctx, UY_RFdu_CajaControl_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFduCajaControl(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Acualiza saldo anterior y saldo final con información de la gl_journal para dias anteriores.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 24/03/2014
	 * @see
	 */
	public void updateSaldosFinales() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			MFduSaldosIniciales saldos = MFduSaldosIniciales.getDate(getCtx(),this.getC_Activity_ID(),this.getC_Currency_ID(),this.getfecha(),get_TrxName());
			if (this.getC_Currency_ID()== 100){
				
				sql = " select (coalesce(sum(l.amtsourcedr),0) - coalesce(sum(l.amtsourcecr),0)) as saldo,(select (coalesce(sum(l.amtsourcedr),0) - coalesce(sum(l.amtsourcecr),0)) as saldo " + "from gl_journalline l " + " inner join gl_journal h on l.gl_journal_id = h.gl_journal_id " +" INNER JOIN "+ "C_ElementValue" +" e on e.C_ElementValue_id = l.C_ElementValue_id "+  " where h.docstatus='CO' " +" and h.dateacct >= '"+ saldos.getfecha()+"' AND h.dateacct < '"+ this.getfecha() + "'" +" and l.c_activity_id_1 ="+ this.getC_Activity_ID() +" and h.c_currency_id ="+ this.getC_Currency_ID()+" and isasientocaja='Y' "+" and l.isTotalizador= 'Y' )as saldoanterior"+ 
						  " from gl_journalline l " +
						  " inner join gl_journal h on l.gl_journal_id = h.gl_journal_id " +
						  " INNER JOIN "+
						  "C_ElementValue"+			
						  " e on e.C_ElementValue_id = l.C_ElementValue_id "+
						  " where h.docstatus='CO' " +
						  " and h.dateacct =? " +
						  " and l.c_activity_id_1 =? " +
						  " and h.c_currency_id =? " +
						  " and isasientocaja='Y' " +
						  " AND l.isTotalizador= 'Y'";				
			}
			if(this.getC_Currency_ID()== 142){
				sql = " select (coalesce(sum(l.amtsourcedr),0) - coalesce(sum(l.amtsourcecr),0)) as saldo, (select (coalesce(sum(l.amtsourcedr),0) - coalesce(sum(l.amtsourcecr),0))  " + "from gl_journalline l " + " inner join gl_journal h on l.gl_journal_id = h.gl_journal_id " +  " where h.docstatus='CO' " +" and h.dateacct >= '"+ saldos.getfecha()+"' AND h.dateacct < '"+  this.getfecha() + "'" +" and l.c_activity_id_1 ="+ this.getC_Activity_ID() +" and h.c_currency_id ="+ this.getC_Currency_ID()+" and isasientocaja='Y' "+" and l.isTotalizador= 'Y' )as saldoanterior"+
						  " from gl_journalline l " +
						  " inner join gl_journal h on l.gl_journal_id = h.gl_journal_id " +
						  " INNER JOIN "+
						  "C_ElementValue"+			
						  " e on e.C_ElementValue_id = l.C_ElementValue_id "+
						  " where h.docstatus='CO' " +
						  " and h.dateacct =? " +
						  " and l.c_activity_id_1 =? " +
						  " and h.c_currency_id =? " +
						  " and isasientocaja='Y' "+
						  " and l.isTotalizador= 'Y'";
				
			}

			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, this.getfecha());
			pstmt.setInt(2, this.getC_Activity_ID());
			pstmt.setInt(3, this.getC_Currency_ID());
		
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				BigDecimal saldoApertura = MFduSaldosIniciales.getValue(this.getC_Activity_ID(),this.getC_Currency_ID(),this.getfecha());
				if (saldoApertura == null) saldoApertura = Env.ZERO;
				this.setsaldoinicial(rs.getBigDecimal("saldoanterior").add(saldoApertura));
				
				this.setsaldo(rs.getBigDecimal("saldo"));
				this.setSaldoFinalPesos(this.getsaldo().add(this.getsaldoinicial()));
				this.saveEx();
			}
			else{
				this.setsaldoinicial(Env.ZERO);
				this.setSaldoFinalPesos(this.getsaldo());
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
