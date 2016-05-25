package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MHRIrpf extends X_UY_HRIrpf {

	private static final long serialVersionUID = 1L;

	public MHRIrpf(Properties ctx, int UY_HRIrpf_ID, String trxName) {
		super(ctx, UY_HRIrpf_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MHRIrpf(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//se validan importes
		if (this.getamtbpc().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Valor BPC debe ser mayor a 0");
		if (this.getamtbpcanual().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Valor BPC Anual debe ser mayor a 0");
			
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		
	//al cambiarse los valores BPC y BPC Anual, se recalculan los valores de las lineas en las escalas
	try {
		
		//PARA ESCALA DE RENTAS PARA CALCULO DE IMPUESTO
		sql = "SELECT uy_hrirpflineimpuesto_id FROM uy_hrirpflineimpuesto WHERE uy_hrirpf_id=" + this.get_ID();
		pstmt = DB.prepareStatement(sql, null);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			
			int ID = rs.getInt("uy_hrirpflineimpuesto_id");
			MHRIrpfLineImpuesto line = new MHRIrpfLineImpuesto(getCtx(),ID,get_TrxName());
			BigDecimal desde = new BigDecimal(line.getdesde());
			BigDecimal hasta = new BigDecimal(line.gethasta());
			
			
			//se toma el valor de BPC dependiendo si la linea esta marcada como ANUAL o no
			if (!line.isanual()){
						
				DB.executeUpdateEx("UPDATE uy_hrirpflineimpuesto SET bpcdesde=" + desde.multiply(this.getamtbpc()) +
						" WHERE uy_hrirpflineimpuesto_id=" + ID, get_TrxName());
				
				DB.executeUpdateEx("UPDATE uy_hrirpflineimpuesto SET bpchasta=" + hasta.multiply(this.getamtbpc()) +
						" WHERE uy_hrirpflineimpuesto_id=" + ID, get_TrxName());
				

			} else {

				DB.executeUpdateEx("UPDATE uy_hrirpflineimpuesto SET bpcdesde=" + desde.multiply(this.getamtbpcanual()) +
						" WHERE uy_hrirpflineimpuesto_id=" + ID, get_TrxName());
				
				DB.executeUpdateEx("UPDATE uy_hrirpflineimpuesto SET bpchasta=" + hasta.multiply(this.getamtbpcanual()) +
						" WHERE uy_hrirpflineimpuesto_id=" + ID, get_TrxName());
			
			}
			
								
		}
		
		//PARA ESCALA DE RENTAS PARA DEDUCCIONES
		sql = "SELECT uy_hrirpflinededuccion_id FROM uy_hrirpflinededuccion WHERE uy_hrirpf_id=" + this.get_ID();
		pstmt = DB.prepareStatement(sql, null);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			
			int ID = rs.getInt("uy_hrirpflinededuccion_id");
			MHRIrpfLineDeduccion line = new MHRIrpfLineDeduccion(getCtx(),ID,get_TrxName());
			BigDecimal desde = new BigDecimal(line.getdesde());
			BigDecimal hasta = new BigDecimal(line.gethasta());
			
			//se toma el valor de BPC dependiendo si la linea esta marcada como ANUAL o no
			if (!line.isanual()){

				DB.executeUpdateEx("UPDATE uy_hrirpflinededuccion SET bpcdesde=" + desde.multiply(this.getamtbpc()) +
						" WHERE uy_hrirpflinededuccion_id=" + ID, get_TrxName());
				
				DB.executeUpdateEx("UPDATE uy_hrirpflinededuccion SET bpchasta=" + hasta.multiply(this.getamtbpc()) +
						" WHERE uy_hrirpflinededuccion_id=" + ID, get_TrxName());

			} else {

				DB.executeUpdateEx("UPDATE uy_hrirpflinededuccion SET bpcdesde=" + desde.multiply(this.getamtbpcanual()) +
						" WHERE uy_hrirpflinededuccion_id=" + ID, get_TrxName());
				
				DB.executeUpdateEx("UPDATE uy_hrirpflinededuccion SET bpchasta=" + hasta.multiply(this.getamtbpcanual()) +
						" WHERE uy_hrirpflinededuccion_id=" + ID, get_TrxName());
				
			}

		}
								
	} catch (Exception e) {
		throw new AdempiereException(e.getMessage());
	} finally {
		DB.close(rs, pstmt);
		rs = null;
		pstmt = null;
	}
	
		return true;
	}

}
