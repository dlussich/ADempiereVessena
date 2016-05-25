package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MHRConcept;

public class MHRConceptoLine extends X_UY_HRConceptoLine {

	private static final long serialVersionUID = 1L;

	public MHRConceptoLine(Properties ctx, int UY_HRConceptoLine_ID,
			String trxName) {
		super(ctx, UY_HRConceptoLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MHRConceptoLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);			
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if (newRecord || (!newRecord && is_ValueChanged("HR_Concept_ID"))){
			//se valida no repetir conceptos en la lista de conceptos de nomina
			String sql = "SELECT count(hr_concept_id) FROM uy_hrconceptoline WHERE hr_concept_id=" + this.getHR_Concept_ID()
					+ " AND uy_hrnovedades_id=" + this.getUY_HRNovedades_ID();
			int res = DB.getSQLValueEx(get_TrxName(), sql);

			if (res > 0) throw new AdempiereException("Ya existe un registro para este concepto");		
		
		}
			
		MHRConcept con = new MHRConcept (getCtx(),this.getHR_Concept_ID(),get_TrxName()); //instancio modelo del concepto
		
		if(con.isTransport() && this.getUY_HRLoadDriver_ID() <= 0) throw new AdempiereException("No se pueden ingresar conceptos de tipo transporte en forma manual");	
		
		String type = con.getColumnType(); //obtengo el tipo de columna
		
		//se controlan los datos ingresados dependiendo del tipo de concepto elegido
		//si tipo de concepto es IMPORTE
		if(type.equalsIgnoreCase("A")){
			
			if (this.getAmount().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Importe debe ser mayor a 0");
			if (this.getQty().compareTo(Env.ZERO) > 0 || this.getporcentaje().compareTo(Env.ZERO) > 0) throw new AdempiereException("El único campo permitido es: IMPORTE");
			
			//si tipo de concepto es CANTIDAD
		} else if (type.equalsIgnoreCase("Q")){
			
			if (this.getQty().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Cantidad debe ser mayor a 0");
			if (this.getAmount().compareTo(Env.ZERO) > 0 || this.getporcentaje().compareTo(Env.ZERO) > 0) throw new AdempiereException("El único campo permitido es: CANTIDAD");
			
			if(con.getlimite() > 0){
				
				BigDecimal limite = new BigDecimal(con.getlimite());
				
				if(this.getQty().compareTo(limite) > 0) throw new AdempiereException("La cantidad no puede ser mayor a " + '"' + limite + '"');
				
			}
						
			//si tipo de concepto es PORCENTAJE
		} else if (type.equalsIgnoreCase("P")){
			
			if (this.getporcentaje().compareTo(Env.ZERO) <= 0) throw new AdempiereException("Porcentaje debe ser mayor a 0");
			if (this.getAmount().compareTo(Env.ZERO) > 0 || this.getQty().compareTo(Env.ZERO) > 0) throw new AdempiereException("El único campo permitido es: PORCENTAJE");
						
			//si tipo de concepto es SI/NO
		}/* else if (type.equalsIgnoreCase("K")){
						
			if (this.getAmount().compareTo(Env.ZERO) > 0 || this.getQty().compareTo(Env.ZERO) > 0 || this.getporcentaje().compareTo(Env.ZERO) > 0) throw new AdempiereException("No se debe ingresar ningún valor para éste concepto");
						
		} */
		

		
		
		return true;
	}
	
	/**
	 * Metodo que devuelve la linea de concepto para ese empleado,liquidacion y concepto
	 * OpenUp Ltda. Issue #986 
	 * @author Nicolas Sarlabos - 14/05/2012
	 * @see
	 * @param partnerID
	 * @return
	 */	
	public static MHRConceptoLine forProcessConcept(int partnerID, int conceptID, int processID, String trxName) {
		
		MHRConceptoLine line = null;
		
		String sql = "SELECT uy_hrconceptoline_id " +
					 " FROM uy_hrconceptoline l " +
                     " INNER JOIN uy_hrnovedades n ON l.uy_hrnovedades_id=n.uy_hrnovedades_id " + 
                     " WHERE n.isactive = 'Y' AND n.c_bpartner_id=" + partnerID + " AND n.uy_hrprocess_id=" + processID +
                     " AND l.hr_concept_id=" + conceptID;
		
		int lineID = DB.getSQLValueEx(trxName, sql);
		
		if(lineID > 0) line = new MHRConceptoLine(Env.getCtx(),lineID,trxName);
		
		return line;
	
	}

	@Override
	protected boolean beforeDelete() {
		
		if(this.getUY_HRLoadDriver_ID() > 0){
			
			MHRLoadDriver load = new MHRLoadDriver(getCtx(),this.getUY_HRLoadDriver_ID(),get_TrxName());
			
			throw new AdempiereException("Imposible borrar la linea por estar asociada al documento de carga N° " + load.getDocumentNo());		
			
		}		
		
		return true;
	}
	
	

}
