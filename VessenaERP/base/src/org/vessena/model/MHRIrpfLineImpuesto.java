package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

public class MHRIrpfLineImpuesto extends X_UY_HRIrpfLineImpuesto {

	private static final long serialVersionUID = 1L;

	public MHRIrpfLineImpuesto(Properties ctx, int UY_HRIrpfLineImpuesto_ID, String trxName) {
		super(ctx, UY_HRIrpfLineImpuesto_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MHRIrpfLineImpuesto(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//instancio cabezal
		MHRIrpf hdr = new MHRIrpf (getCtx(),this.getUY_HRIrpf_ID(),null);
		BigDecimal desde = new BigDecimal (this.getdesde());
		BigDecimal hasta = new BigDecimal (this.gethasta());
		
		//se validan porcentajes e importes
		if (this.getdesde() < 0) throw new AdempiereException ("No se permite valor negativo");
		if (this.gethasta() < 0) throw new AdempiereException ("No se permite valor negativo");
		if (this.getporcentaje().compareTo(Env.ZERO) < 0) throw new AdempiereException ("No se permite valor negativo");
		
		//se controla secuencias de valores "Desde" y "Hasta"
		if (this.gethasta() <= this.getdesde()) throw new AdempiereException ("Valor Hasta debe ser mayor a valor Desde");
		
		/*if(newRecord){
			String sql = "SELECT max(hasta) from uy_hrirpflineimpuesto WHERE uy_hrirpf_id=" + hdr.get_ID();
			int maxHasta = DB.getSQLValueEx(null, sql);

			if (maxHasta > 0) if (this.getdesde() <= maxHasta) throw new AdempiereException ("Valor Desde debe ser mayor a " + maxHasta);
		}*/
					
		//se setean importes BPC desde y hasta, segun si la linea es anual o no
		if (!this.isanual()) {
									
				this.setbpcdesde(hdr.getamtbpc().multiply(desde));
				this.setbpchasta(hdr.getamtbpc().multiply(hasta));
										
		} else {
			
			this.setbpcdesde(hdr.getamtbpcanual().multiply(desde));
			this.setbpchasta(hdr.getamtbpcanual().multiply(hasta));
			
			
		}
		

		return true;
	}

}
