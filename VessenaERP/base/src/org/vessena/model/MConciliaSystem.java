package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

public class MConciliaSystem extends X_UY_ConciliaSystem {

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if(!newRecord){
			if(this.isManual() && this.getUY_Conciliated_ID()>0) throw new AdempiereException ("Imposible seleccionar este documento por estar ya conciliado");
			if(this.isSameGrid() && this.getUY_Conciliated_ID()>0) throw new AdempiereException ("Imposible seleccionar este documento por estar ya conciliado");
			if(this.isManual() && this.isError()) throw new AdempiereException ("Imposible conciliar documento por estar marcado como ERROR");
			if(this.isSameGrid() && this.isError()) throw new AdempiereException ("Imposible conciliar documento por estar marcado como ERROR");
			if(this.getUY_Conciliated_ID()>0 && this.isError()) throw new AdempiereException ("Imposible marcar como ERROR, el documento ya fue conciliado");
			if(this.isManual() && this.isSameGrid()) throw new AdempiereException ("Debe marcar el documento para conciliar en la misma o diferente grilla, pero no ambas opciones");
		}
		
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 830097207851488029L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ConciliaSystem_ID
	 * @param trxName
	 */
	public MConciliaSystem(Properties ctx, int UY_ConciliaSystem_ID, String trxName) {
		super(ctx, UY_ConciliaSystem_ID, trxName);
	
	}
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MConciliaSystem(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		MConciliation hdr = (MConciliation) this.getUY_Conciliation(); //instancio cabezal
		hdr.setamt1(hdr.getTotalSystem()); //seteo importe parcial de movimientos del sistema
		hdr.setDiference(hdr.getamt1().subtract(hdr.getamt2()));
		hdr.saveEx();
		
		return true;
	}

}
