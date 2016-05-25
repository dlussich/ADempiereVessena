/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 16/11/2014
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MTRLoadMonitorLine
 * OpenUp Ltda. Issue #1625 
 * Description: Modelo para linea de documento de seguimiento de cargas en modulo de transporte.
 * @author Gabriel Vila - 16/11/2014
 * @see
 */
public class MTRLoadMonitorLine extends X_UY_TR_LoadMonitorLine {

	private static final long serialVersionUID = -2866743423485217897L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_LoadMonitorLine_ID
	 * @param trxName
	 */
	public MTRLoadMonitorLine(Properties ctx, int UY_TR_LoadMonitorLine_ID,
			String trxName) {
		super(ctx, UY_TR_LoadMonitorLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadMonitorLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// Si es una nueva linea y la misma se genera automaticamente, salgo sin problemas.
		if ((newRecord) && (!this.isManual())){
			return true;
		}
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		MTRConfigRound round = MTRConfigRound.forConfig(getCtx(), param.get_ID(), get_TrxName());
		
		if (round==null) throw new AdempiereException("No se obtuvieron parametros de precision decimal para la empresa actual");
		
		MTRTrip trip = (MTRTrip) this.getUY_TR_Trip();		
		MTRCrt crt = (MTRCrt)this.getUY_TR_Crt();
		MTRLoadMonitor header = (MTRLoadMonitor)this.getUY_TR_LoadMonitor();
		MTRTransOrder order = null;
		
		if(header.getUY_TR_TransOrder_ID()>0) {
			
			order = new MTRTransOrder(getCtx(),header.getUY_TR_TransOrder_ID(),get_TrxName());
			
			if(order.isLastre()){
				
				log.saveError(null, "No es posible cargar CRT ya que la orden de transporte seleccionada es un LASTRE.");
				return false;
				
			}
		}		

		// Si no es newrecord y me cambian el valor de seleccionado
		if ((!newRecord) && (is_ValueChanged(COLUMNNAME_IsSelected))){
			// Si es un movimiento de carga y la linea es NO MANUAL no puedo estar tocando el valor de seleccionado.
			if ((header.isLoadProduct()) && (!this.isManual())){
				log.saveError(null, "No es posible modificar selección en CRT que ya existe en la orden de transporte asociada a este movimiento.");
				return false;
			}
		}
		
		if(newRecord || is_ValueChanged("UY_TR_Trip_ID")){
			
			this.setC_Currency_ID(crt.getC_Currency_ID());
			
			for (MTRLoadMonitorLine line : ((MTRLoadMonitor) this.getUY_TR_LoadMonitor()).getLines()) {	
				
				if(line.getUY_TR_Crt_ID() == this.getUY_TR_Crt_ID()){
					log.saveError(null, "El CRT seleccionado ya se encuentra asociado a este Documento");
					return false;
				}
				
				MTRTrip actualTrip = (MTRTrip) line.getUY_TR_Trip();
				
				/*
				if(!actualTrip.getTripType().equalsIgnoreCase(trip.getTripType()) 
						|| actualTrip.isInTransit()!=trip.isInTransit()){
					
					log.saveError(null, "Todos los CRT a cargar en este documento deben ser del mismo tipo, IMPORTACION o EXPORTACION");
					return false;
				}
				*/
					
			}
			
		}		
		
		//Verifico cantidad de bultos a ingresar
		if(newRecord || is_ValueChanged("QtyPackage")){	
			
			if(this.getQtyPackage().compareTo(Env.ZERO)<=0){
				log.saveError(null, "La cantidad de bultos debe ser mayor a cero.");
				return false;
			}
			
			BigDecimal qtyPackageTotal = crt.getQtyPackage();
			MInvoice proforma = null;
			if (crt.getC_Invoice_ID() > 0){
				proforma = (MInvoice)crt.getC_Invoice();
				qtyPackageTotal = proforma.getuy_cantbultos();
			}
						
			String sql = "SELECT COALESCE(sum(otline.qtypackage), 0)" +
                         " FROM uy_tr_transorderline otline" +
                         " WHERE otline.uy_tr_crt_id = " + crt.get_ID() +
                         " AND ((otline.endtrackstatus = 'N' AND otline.isdelivered = 'N') or (otline.endtrackstatus = 'Y' AND otline.isdelivered = 'Y'))" +
                         " AND  otline.uy_tr_transorderline_id <> " + this.getUY_TR_TransOrderLine_ID();
			
			BigDecimal qtyUsed = DB.getSQLValueBDEx(null, sql);
			
			if(qtyUsed==null) qtyUsed = Env.ZERO;
			
			BigDecimal qtyDisponible = qtyPackageTotal.subtract(qtyUsed); //obtengo bultos disponibles

			/*
			if(this.getQtyPackage().compareTo(qtyDisponible) > 0){
				log.saveError(null, "Cantidad de bultos ingresada supera los " + qtyDisponible.setScale(0, RoundingMode.HALF_UP) + " bultos disponibles para este CRT");
				return false;
			}
			*/
		
		}
		
		// Si estoy descargando mercaderias
		if (header.isUnloadProduct()){
			// Si des-seleccione este CRT, dejo los valores originales.
			if (!newRecord && is_ValueChanged(COLUMNNAME_IsSelected)){
				if (!this.isSelected()){
					this.setAmount(this.getAmount2());
					this.setProductAmt(this.getProductAmt2());
					this.setQtyPackage(this.getQtyPackage2());
					this.setVolume(this.getVolume2());
					this.setWeight(this.getWeightAux());
					this.setWeight2(this.getWeight2Aux());
				}
			}
		}
		
		if(is_ValueChanged("Weight")){
			
			this.setWeight(this.getWeight().setScale(round.getWeight(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("Weight2")){
			
			this.setWeight2(this.getWeight2().setScale(round.getWeight2(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("Volume")){
			
			this.setVolume(this.getVolume().setScale(round.getVolume(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("QtyPackage")){
			
			this.setQtyPackage(this.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("ProductAmt")){
			
			this.setProductAmt(this.getProductAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP));
			
		}
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return false;
		
		this.updateHeader();
		
		return true;

	}

	@Override
	protected boolean afterDelete(boolean success) {

		if (!success) return false;
		
		this.updateHeader();
		
		return true;

	}
	

	/***
	 * Actualiza totales del documento
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/12/2014
	 * @see
	 */
	private void updateHeader() {
		
		try {
			
			String sql = " update uy_tr_loadmonitor hdr " + 
			             " set TotalAmt = " +
						 	" (select coalesce(sum(round(line.ProductAmt,2)),0) " +
						 	" from uy_tr_loadmonitorline line where hdr.uy_tr_loadmonitor_id = line.uy_tr_loadmonitor_id ) " +
						 ", TotalWeight =" +
						 	" (select coalesce(sum(round(line.Weight,2)),0) " +
						 	" from uy_tr_loadmonitorline line where hdr.uy_tr_loadmonitor_id = line.uy_tr_loadmonitor_id ) " +
					     " where uy_tr_loadmonitor_id =? ";
			
			DB.executeUpdateEx(sql, new Object[]{this.getUY_TR_LoadMonitor_ID()}, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
}
