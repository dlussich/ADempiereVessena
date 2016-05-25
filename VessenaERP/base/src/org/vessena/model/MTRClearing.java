/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * @author Nicolas
 *
 */
public class MTRClearing extends X_UY_TR_Clearing implements DocAction{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 2473926579191426916L;
	
	private String processMsg = null;
	private boolean justPrepared = false;
	//private MTRConfigClearing confClearing = null;

	/**
	 * @param ctx
	 * @param UY_TR_Clearing_ID
	 * @param trxName
	 */
	public MTRClearing(Properties ctx, int UY_TR_Clearing_ID, String trxName) {
		super(ctx, UY_TR_Clearing_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRClearing(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord || is_ValueChanged("UY_TR_Driver_ID") || is_ValueChanged("UY_TR_Truck_ID")){
			
			//obtengo ultima liquidacion para el chofer y vehiculo actual
			MTRClearing clearing = this.getLastForDriverTruck(getCtx(), this.getUY_TR_Driver_ID(), this.getUY_TR_Truck_ID(), null, get_TrxName());
			
			if(clearing!=null && clearing.get_ID()>0){
				
				if(!clearing.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed))
					throw new AdempiereException("Existe la liquidacion N° " + clearing.getDocumentNo() + " sin completar para el conductor y vehiculo seleccionados");	
				
			}
			
		}
		
		/*int curFromID = 0, curToID = 0;
		BigDecimal dividerate = Env.ZERO, amt = Env.ZERO;
		
		if(newRecord || is_ValueChanged("UY_TR_Driver_ID") || is_ValueChanged("UY_TR_Truck_ID")){
			
			MTRConfig trConfig = MTRConfig.forClientID(getCtx(), this.getAD_Client_ID(), null);
			MTRConfigClearing cl = MTRConfigClearing.forConfig(getCtx(), trConfig.get_ID(), get_TrxName());
			
			if (cl==null) throw new AdempiereException("No se obtuvieron parametros de liquidacion de viaje para la empresa actual");
			
			//seteo moneda secundaria desde parametros de transporte
			if(cl.getC_Currency_ID()>0) this.setC_Currency2_ID(cl.getC_Currency_ID());
			
			if(cl.isSaldoInicial()){ //si se utiliza saldo
				
				//obtengo ultima liquidacion en estado completo para el chofer actual
				MTRClearing clearing = this.getLastForDriverTruck(getCtx(), this.getUY_TR_Driver_ID(), this.getUY_TR_Truck_ID(), get_TrxName());
				
				//si se obtuvo ultima liquidacion completa, se carga el saldo inicial, si no sera cero
				if(clearing!=null && clearing.get_ID()>0){
					
					//si liquidacion anterior tiene adelanto de sueldo
					if(clearing.getAmount3().compareTo(Env.ZERO)>0){
						
						if(this.getC_Currency3_ID()==this.getC_Currency_ID()){ //si moneda de adelanto = moneda nacional
							
							this.setSaldoAnterior(clearing.getDifferenceAmt().subtract(clearing.getAmount3()));						
							
						} else { //realizo conversion
							
							curFromID = this.getC_Currency3_ID();
							curToID = 142;
							
							dividerate = MConversionRate.getDivideRate(curFromID, curToID, this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
							
							if (dividerate == null || dividerate.compareTo(Env.ZERO)==0) throw new AdempiereException ("No se obtuvo tasa de cambio para fecha de documento");					
														
								amt = clearing.getAmount3();					
								amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);
								
								this.setSaldoAnterior(clearing.getDifferenceAmt().subtract(amt));					
							
						}
						
						if(this.getC_Currency3_ID()==this.getC_Currency2_ID()){ //si moneda de adelanto = moneda secundaria
													
							this.setSaldoAnterior2(clearing.getDifferenceAmt2().subtract(clearing.getAmount3()));					
							
						} else { //realizo conversion
							
							curFromID = this.getC_Currency3_ID();
							curToID = this.getC_Currency2_ID();
							
							BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
							BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
							
							if ((rateFrom != null) && (rateTo != null)){
								if (rateTo.compareTo(Env.ZERO) > 0) {
									dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
								}
							}
							
							amt = clearing.getAmount3();					
							amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);
							
							this.setSaldoAnterior2(clearing.getDifferenceAmt2().subtract(amt));							
							
						}
						
					} else { //si no tiene adelanto de sueldo
						
						this.setSaldoAnterior(clearing.getDifferenceAmt());
						this.setSaldoAnterior2(clearing.getDifferenceAmt2());					
					}
					
				} else {
					
					this.setSaldoAnterior(Env.ZERO);
					this.setSaldoAnterior2(Env.ZERO);
				}				
				
			}
			
		}*/	
		
		return true;
	}

	@Override
	public boolean applyIt() {
		
		//MTRConfig trConfig = MTRConfig.forClientID(getCtx(), this.getAD_Client_ID(), null);
		//MTRConfigClearing cl = MTRConfigClearing.forConfig(getCtx(), trConfig.get_ID(), get_TrxName());
		
		/*if (cl!=null && cl.get_ID()>0){
			
			this.confClearing = cl;
					
		} else throw new AdempiereException("No se obtuvieron parametros de liquidacion de viaje para la empresa actual");*/	
		
		DB.executeUpdate("delete from uy_tr_clearingline where uy_tr_clearing_id = " + this.get_ID(), get_TrxName());
		DB.executeUpdate("delete from uy_tr_clearingtruck cascade where uy_tr_clearing_id = " + this.get_ID(), get_TrxName());
			
		this.loadInvoice();
		this.loadFuelConsume();
		
		if(this.getTruckLines().size()>0){
		
			this.loadAnticipos();
			this.setTotalsGrid();
						
		} else throw new AdempiereException("No se obtuvieron lineas de gastos o consumos de combustible para esta liquidacion");
				
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);

		return true;
	}

	private void setTotalsGrid() {
		
		String sql = "";
		MTRClearingLine line = null;
		//int currencyID = 0;
		BigDecimal dif = Env.ZERO;
		
		try{
			
			/*sql = "select distinct cl.c_currency_id" +
                  " from uy_tr_configclearingline cl" +
                  " where cl.uy_tr_configclearing_id = " + this.confClearing.get_ID() + 
                  " order by cl.c_currency_id";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();*/

			//while(rs.next()){

			//currencyID = rs.getInt("C_Currency_ID");

			line = new MTRClearingLine(getCtx(), 0, get_TrxName()); //creo nueva linea
			line.setUY_TR_Clearing_ID(this.get_ID());
			line.setC_Currency_ID(this.getC_Currency_ID());

			//sumo los gastos
			sql = "select coalesce(sum(d.grandtotal),0)" +
					" from uy_tr_clearing c" +
					" inner join uy_tr_clearingtruck t on c.uy_tr_clearing_id = t.uy_tr_clearing_id" +
					" inner join uy_tr_clearingdoc d on t.uy_tr_clearingtruck_id = d.uy_tr_clearingtruck_id" +
					" where c.uy_tr_clearing_id = " + this.get_ID();

			BigDecimal amtExpense = DB.getSQLValueBDEx(get_TrxName(), sql);				

			//sumo los consumos de combustible
			/*sql = "select coalesce(sum(f.grandtotal),0)" +
					" from uy_tr_clearing c" +
					" inner join uy_tr_clearingtruck t on c.uy_tr_clearing_id = t.uy_tr_clearing_id" +
					" inner join uy_tr_clearingfuel f on t.uy_tr_clearingtruck_id = f.uy_tr_clearingtruck_id" +
					" where c.uy_tr_clearing_id = " + this.get_ID();

			BigDecimal amtFuel = DB.getSQLValueBDEx(get_TrxName(), sql);*/

			//seteo suma de gastos y combustible
			line.setExpenseAmt(amtExpense);

			//sumo los anticipos para la moneda actual
			sql = "select coalesce(sum(amtopen),0)" +
					" from uy_tr_clearingpayment" +
					" where uy_tr_clearing_id = " + this.get_ID() +
					" and isselected = 'Y'";

			BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);

			line.setAmount(amount);

			//calculo y seteo el campo de saldo final
			dif = (line.getAmount().subtract(line.getExpenseAmt()));
			if(dif.compareTo(Env.ZERO)!=0) dif = dif.setScale(2, RoundingMode.HALF_UP);

			line.setDifferenceAmt(dif);	
			line.setAmount3(dif);				

			//al final salvo la linea
			line.saveEx(get_TrxName());				
			//}			

		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}		
		
	}
	
	/*private void setTotalsHdrPrimaryCurrency() {
		
		BigDecimal totalAnticipos = Env.ZERO;
		BigDecimal totalGastos = Env.ZERO;
		BigDecimal totalFinal = Env.ZERO;
		BigDecimal dividerate = Env.ZERO;
		BigDecimal amt = Env.ZERO;
		int curFromID = 0;
		int curToID = 0;		
		
		try{
			
			List<MTRClearingLine> cLines = this.getLines();

			for (MTRClearingLine line: cLines){
				
				if(line.getC_Currency_ID()==this.getC_Currency_ID()){ //si tiene la moneda principal
					
					totalAnticipos = totalAnticipos.add(line.getAmount());
					totalGastos = totalGastos.add(line.getExpenseAmt());
					totalFinal = totalFinal.add(line.getDifferenceAmt());					
					
				} else { //si tiene moneda diferente a la principal, se realizan conversiones
					
					curFromID = line.getC_Currency_ID();
					curToID = 142;
					
					dividerate = MConversionRate.getDivideRate(curFromID, curToID, this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
					
					if (dividerate == null || dividerate.compareTo(Env.ZERO)==0) throw new AdempiereException ("No se obtuvo tasa de cambio para fecha de documento");					
					
					//convierto anticipos
					if(line.getAmount().compareTo(Env.ZERO)!=0){
						
						amt = line.getAmount();					

						amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);
						
						totalAnticipos = totalAnticipos.add(amt);							
					}
					
					//convierto gastos
					if(line.getExpenseAmt().compareTo(Env.ZERO)!=0){
						
						amt = line.getExpenseAmt();
						
						amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);	
						
						totalGastos = totalGastos.add(amt);								
					}		
					
				}	
				
			}
			
			this.setAmount(totalAnticipos);
			this.setExpenseAmt(totalGastos);
			this.setDifferenceAmt(this.getAmount().subtract(this.getExpenseAmt()));
					
			
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}			
	}
	
	private void setTotalsHdrSecondaryCurrency() {
		
		BigDecimal totalAnticipos = Env.ZERO;
		BigDecimal totalGastos = Env.ZERO;
		BigDecimal totalFinal = Env.ZERO;
		BigDecimal dividerate = Env.ZERO;
		BigDecimal amt = Env.ZERO;
		int curFromID = 0;
		int curToID = 0;		
		
		try{
			
			List<MTRClearingLine> cLines = this.getLines();

			for (MTRClearingLine line: cLines){
				
				if(line.getC_Currency_ID()==this.getC_Currency2_ID()){ //si tiene la moneda secundaria
					
					totalAnticipos = totalAnticipos.add(line.getAmount());
					totalGastos = totalGastos.add(line.getExpenseAmt());
					totalFinal = totalFinal.add(line.getDifferenceAmt());					
					
				} else { //si tiene moneda diferente a la secundaria, se realizan conversiones
					
					curFromID = line.getC_Currency_ID();
					curToID = this.getC_Currency2_ID();
					
					if (curFromID == 142){
						dividerate = MConversionRate.getDivideRate(curFromID, curToID, this.getDateTrx(), 0, this.getAD_Client_ID(), this.getAD_Org_ID());	
					}
					else{
						
						BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
						BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, this.getDateTrx(), 0, this.getAD_Client_ID(), 0);
						
						if ((rateFrom != null) && (rateTo != null)){
							if (rateTo.compareTo(Env.ZERO) > 0) {
								dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
							}
						}
					}
					
					if (dividerate == null || dividerate.compareTo(Env.ZERO)==0) throw new AdempiereException ("No se obtuvo tasa de cambio para fecha de documento");					
					
					//convierto anticipos
					if(line.getAmount().compareTo(Env.ZERO)!=0){
						
						amt = line.getAmount();					

						amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);
						
						totalAnticipos = totalAnticipos.add(amt);							
					}
					
					//convierto gastos
					if(line.getExpenseAmt().compareTo(Env.ZERO)!=0){
						
						amt = line.getExpenseAmt();
						
						amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);	
						
						totalGastos = totalGastos.add(amt);								
					}		
					
				}	
				
			}
			
			this.setAmount2(totalAnticipos);
			this.setExpenseAmt2(totalGastos);
			this.setDifferenceAmt2(this.getAmount2().subtract(this.getExpenseAmt2()));
				
			
		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}			
	}*/

	private void loadInvoice() {

		String sql = "", whereTruck = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int truckID = 0;
		MTRClearingTruck clTruck= null;
		MTRClearingDoc clDoc = null;

		try{

			MDocType docExpense = MDocType.forValue(getCtx(), "factgastoviaje", get_TrxName());
						
			if(docExpense==null) throw new AdempiereException("No se encontro tipo de documento 'Gastos de Viaje' para la empresa actual");
			
			if(this.getUY_TR_Truck_ID()>0) whereTruck = " and i.uy_tr_truck_id = " + this.getUY_TR_Truck_ID(); 
			
			sql = "select i.c_invoice_id, i.dateinvoiced, i.c_bpartner_id, i.paymentruletype, i.c_currency_id, i.totallines, i.grandtotal, i.uy_tr_truck_id" +
                  " from c_invoice i" +
                  " where i.c_doctypetarget_id = " + docExpense.get_ID() + " and i.docstatus = 'CO'" +
                  " and i.dateinvoiced between '" + this.getStartDate() + "' and '" + this.getEndDate() + "'" +
                  " and i.uy_tr_driver_id = " + this.getUY_TR_Driver_ID() + whereTruck +
                  " and i.c_currency_id = " + this.getC_Currency_ID() +
                  " and i.c_invoice_id not in (select d.c_invoice_id" + 
                  " from uy_tr_clearing c" + 
                  " inner join uy_tr_clearingtruck t on c.uy_tr_clearing_id = t.uy_tr_clearing_id" +
                  " inner join uy_tr_clearingdoc d on t.uy_tr_clearingtruck_id = d.uy_tr_clearingtruck_id" + 
                  " where c.docstatus <> 'VO')" +
                  " order by uy_tr_truck_id";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				
				int truck_id = rs.getInt("UY_TR_Truck_ID");
				
				//corte de control por Vehiculo
				if(truckID != truck_id) {
					
					truckID = truck_id;
					
					clTruck = new MTRClearingTruck(getCtx(), 0, get_TrxName());
					clTruck.setUY_TR_Clearing_ID(this.get_ID());
					clTruck.setUY_TR_Truck_ID(truckID);
					clTruck.saveEx();
				}
				
				clDoc = new MTRClearingDoc(getCtx(), 0, get_TrxName());
				clDoc.setUY_TR_ClearingTruck_ID(clTruck.get_ID());
				clDoc.setC_Invoice_ID(rs.getInt("C_Invoice_ID"));
				clDoc.setDateTrx(rs.getTimestamp("DateInvoiced"));
				clDoc.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
				clDoc.setC_Currency_ID(rs.getInt("C_Currency_ID"));
				clDoc.setTotalLines(rs.getBigDecimal("TotalLines"));
				clDoc.setGrandTotal(rs.getBigDecimal("GrandTotal"));
				if(rs.getString("paymentruletype")!=null) clDoc.setpaymentruletype(rs.getString("paymentruletype"));
				clDoc.setIsFuel(false);
								
				clDoc.saveEx();		
				
			}
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	

	}
	
	private void loadFuelConsume() {

		String sql = "";
		//String whereTruck = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int truckID = 0;
		MTRClearingTruck clTruck= null;
		MTRClearingFuel clFuel = null;

		try{
			
			//if(this.getUY_TR_Truck_ID()>0) whereTruck = " and uy_tr_truck_id = " + this.getUY_TR_Truck_ID();

			sql = "select uy_tr_fuel_id, c_invoice_id, datetrx, dateoperation, factura, c_bpartner_id, paymentruletype, c_currency_id, uy_tr_truck_id, totalamt" +
                  " from uy_tr_fuel" +
                  " where docstatus = 'CO' and uy_tr_truck_id = " + this.getUY_TR_Truck_ID() +
                  " and c_currency_id = " + this.getC_Currency_ID() +
                  " and paymentruletype = 'CR' and ismanual = 'N' and uy_tr_fuel_id not in (select f.uy_tr_fuel_id" +
                  " from uy_tr_clearing c" + 
                  " inner join uy_tr_clearingtruck t on c.uy_tr_clearing_id = t.uy_tr_clearing_id" +
                  " inner join uy_tr_clearingfuel f on t.uy_tr_clearingtruck_id = f.uy_tr_clearingtruck_id" +
                  " where c.docstatus <> 'VO' and f.isverified = 'Y')" +
                  " order by uy_tr_truck_id";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				
				truckID = rs.getInt("UY_TR_Truck_ID");
				
				clTruck = MTRClearingTruck.forHdrAndTruck(getCtx(), this.get_ID(), truckID, get_TrxName());
				
				if(clTruck==null){
					
					clTruck = new MTRClearingTruck(getCtx(), 0, get_TrxName());
					clTruck.setUY_TR_Clearing_ID(this.get_ID());
					clTruck.setUY_TR_Truck_ID(truckID);
					clTruck.saveEx();					
				}			
				
				clFuel = new MTRClearingFuel(getCtx(), 0, get_TrxName());
				clFuel.setUY_TR_ClearingTruck_ID(clTruck.get_ID());
				if(rs.getInt("C_Invoice_ID")>0) clFuel.setC_Invoice_ID(rs.getInt("C_Invoice_ID"));
				clFuel.setUY_TR_Fuel_ID(rs.getInt("UY_TR_Fuel_ID"));
				clFuel.setDateTrx(rs.getTimestamp("DateTrx"));
				clFuel.setDateOperation(rs.getTimestamp("DateOperation"));
				clFuel.setfactura(rs.getString("Factura"));
				clFuel.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
				clFuel.setC_Currency_ID(rs.getInt("C_Currency_ID"));
				clFuel.setGrandTotal(rs.getBigDecimal("TotalAmt"));
				if(rs.getString("paymentruletype")!=null) clFuel.setpaymentruletype(rs.getString("paymentruletype"));
				
				clFuel.saveEx();		
				
			}
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	

	}
	/***
	 * Obtiene y carga anticipios para conductor de esta liquidacion de viaje.
	 * OpenUp Ltda. Issue #3505
	 * @author Nicolas Sarlabos - 23/03/2015
	 * @see
	 */
	public void loadAnticipos() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MBPartner partner = null;
		
		try {
			
			MTRDriver driver = (MTRDriver)this.getUY_TR_Driver();
			
			if(driver.getC_BPartner_ID()>0){
				
				partner = (MBPartner)driver.getC_BPartner();
				
			} else throw new AdempiereException("El conductor '" + driver.getName() + "' no tiene Propietario/Provedor asociado");

			sql = " select * from alloc_paymentamtopen " +
				  " where c_bpartner_id =? " +
				  " and coalesce(amtopen,0) > 0 " +
				  " and c_currency_id = " + this.getC_Currency_ID() +
				  " and c_payment_id not in (select p.c_payment_id" + 
                  " from uy_tr_clearingpayment p" + 
                  " inner join uy_tr_clearing c on p.uy_tr_clearing_id = c.uy_tr_clearing_id" + 
                  " where (c.docstatus <> 'VO' and p.isselected = 'Y'))";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, partner.get_ID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MTRClearingPayment pay = new MTRClearingPayment(getCtx(), 0, get_TrxName());
				pay.setUY_TR_Clearing_ID(this.get_ID());
				pay.setC_Payment_ID(rs.getInt("c_payment_id"));
				pay.setDateTrx(rs.getTimestamp("datetrx"));
				pay.setC_Currency_ID(rs.getInt("c_currency_id"));
				pay.setamtdocument(rs.getBigDecimal("amtpay"));
				pay.setamtallocated(rs.getBigDecimal("amtallocated"));
				pay.setamtopen(rs.getBigDecimal("amtopen"));
				pay.setIsSelected(true);
				pay.saveEx();
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}
	
	/***
	 * Obtiene y retorna lineas de totales por moneda de esta liquidacion.
	 * OpenUp Ltda. Issue #3505 
	 * @author Nicolas Sarlabos - 08/04/2015
	 * @see
	 * @return
	 */
	public List<MTRClearingLine> getLines(){

		String whereClause = X_UY_TR_ClearingLine.COLUMNNAME_UY_TR_Clearing_ID + "=" + this.get_ID();

		List<MTRClearingLine> lines = new Query(getCtx(), I_UY_TR_ClearingLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas de vehiculo para esta liquidacion de viaje.
	 * OpenUp Ltda. Issue #3505
	 * @author Nicolas Sarlabos - 23/23/2015
	 * @see
	 * @return
	 */
	public List<MTRClearingTruck> getTruckLines(){

		String whereClause = X_UY_TR_ClearingTruck.COLUMNNAME_UY_TR_Clearing_ID + "=" + this.get_ID();

		List<MTRClearingTruck> lines = new Query(getCtx(), I_UY_TR_ClearingTruck.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lista de liquidaciones para el chofer recibido.
	 * OpenUp Ltda. Issue #3505
	 * @author Nicolas Sarlabos - 24/03/2015
	 * @see
	 * @return
	 */
	/*public List<MTRClearing> getClearings(int driverID){

		String whereClause = X_UY_TR_Clearing.COLUMNNAME_UY_TR_Driver_ID + "=" + driverID;

		List<MTRClearing> lines = new Query(getCtx(), I_UY_TR_Clearing.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}*/
	
	/***
	 * Obtiene y retorna la ultima liquidacion completa para el chofer y vehiculo recibidos.
	 * OpenUp Ltda. Issue #3505
	 * @author Nicolas Sarlabos - 23/23/2015
	 * @see
	 * @return
	 */
	public MTRClearing getLastForDriverTruck(Properties ctx, int driverID, int truckID, String docStatus, String trxName){
		
		MTRClearing model = null;
		String whereStatus = "";
		
		if(docStatus!=null && !docStatus.equalsIgnoreCase("")) whereStatus = " and docstatus = '" + docStatus + "'";
		
		String sql = "select max (uy_tr_clearing_id) from uy_tr_clearing where uy_tr_driver_id = " + driverID + 
				" and uy_tr_truck_id = " + truckID  + whereStatus;
		int clearingID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(clearingID > 0) model = new MTRClearing(getCtx(), clearingID, get_TrxName());			
		
		return model;		
	}
	
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		this.verifyLines();
		this.processCashLines();

		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);

		return DocAction.STATUS_Completed;
	}
	
	/***
	 * Procesa lineas y realiza los movimientos de cajas.
	 * OpenUp Ltda. Issue #4340
	 * @author Nicolas Sarlabos - 11/06/2015
	 * @see
	 * @return
	 */
	private void processCashLines() {
		
		MTRDriver driver = (MTRDriver)this.getUY_TR_Driver();
		MBPartner partner = (MBPartner)driver.getC_BPartner();
		MSUMAccountStatus sumba = null;
		MCurrency curr = null;
		MTRDriverCash cash = null;
		
		if(partner==null) throw new AdempiereException("No se obtuvo socio de negocio asociado al conductor actual");
		
		DB.executeUpdateEx("delete from uy_sum_accountstatus where ad_table_id = " + I_UY_TR_Clearing.Table_ID +
				" and record_id = " + this.get_ID(), get_TrxName());
		
		List<MTRClearingLine> lines = this.getLines();//obtengo lineas de liquidacion
		
		for(MTRClearingLine line : lines){
			
			curr = (MCurrency)line.getC_Currency();
			
			if(line.getC_BankAccount_ID() > 0){ //si tiene cuenta bancaria, se hizo devolucion
				
				sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
				sumba.setC_BankAccount_ID(line.getC_BankAccount_ID());
				sumba.setDateTrx(TimeUtil.trunc(this.getDateTrx(),TimeUtil.TRUNC_DAY));
				sumba.setDueDate(TimeUtil.trunc(this.getDateTrx(),TimeUtil.TRUNC_DAY));
				sumba.setC_DocType_ID(this.getC_DocType_ID());
				sumba.setDocumentNo(this.getDocumentNo());
				sumba.setAD_Table_ID(I_UY_TR_Clearing.Table_ID);
				sumba.setRecord_ID(this.get_ID());
				sumba.setC_BPartner_ID(partner.get_ID());
				sumba.setDescription(this.getDescription());
				sumba.setC_Currency_ID(line.getC_Currency_ID());
				sumba.setamtdocument(line.getAmount2());
				sumba.setAmtSourceCr(line.getAmount2());
				sumba.setAmtSourceDr(Env.ZERO);
				sumba.setAmtAcctDr(Env.ZERO);
				sumba.setCurrencyRate(line.getCurrencyRate());
				sumba.saveEx();
				
			}	
			
			//muevo la caja del chofer para la moneda actual
			cash = MTRDriverCash.forDriverCurrency(getCtx(), driver.get_ID(), line.getC_Currency_ID(), get_TrxName());//obtengo caja del chofer para esta moneda
			
			if(cash!=null && cash.get_ID()>0){
				
				//muevo para la columna GASTOS
				if(line.getExpenseAmt().compareTo(Env.ZERO)>0){

					sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
					sumba.setC_BankAccount_ID(cash.getC_BankAccount_ID());
					sumba.setDateTrx(TimeUtil.trunc(this.getDateTrx(),TimeUtil.TRUNC_DAY));
					sumba.setDueDate(TimeUtil.trunc(this.getDateTrx(),TimeUtil.TRUNC_DAY));
					sumba.setC_DocType_ID(this.getC_DocType_ID());
					sumba.setDocumentNo(this.getDocumentNo());
					sumba.setAD_Table_ID(I_UY_TR_Clearing.Table_ID);
					sumba.setRecord_ID(this.get_ID());
					sumba.setC_BPartner_ID(partner.get_ID());
					sumba.setDescription("Gastos");
					sumba.setC_Currency_ID(line.getC_Currency_ID());
					sumba.setamtdocument(line.getExpenseAmt());
					sumba.setAmtSourceDr(line.getExpenseAmt());
					sumba.setAmtSourceCr(Env.ZERO);
					sumba.setAmtAcctCr(Env.ZERO);
					sumba.setCurrencyRate(line.getCurrencyRate());
					sumba.saveEx();

				}
				
				//muevo para la columna IMPORTE DEVUELTO
				if(line.getAmount2().compareTo(Env.ZERO)>0){

					sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
					sumba.setC_BankAccount_ID(cash.getC_BankAccount_ID());
					sumba.setDateTrx(TimeUtil.trunc(this.getDateTrx(),TimeUtil.TRUNC_DAY));
					sumba.setDueDate(TimeUtil.trunc(this.getDateTrx(),TimeUtil.TRUNC_DAY));
					sumba.setC_DocType_ID(this.getC_DocType_ID());
					sumba.setDocumentNo(this.getDocumentNo());
					sumba.setAD_Table_ID(I_UY_TR_Clearing.Table_ID);
					sumba.setRecord_ID(this.get_ID());
					sumba.setC_BPartner_ID(partner.get_ID());
					sumba.setDescription("Importe Devuelto");
					sumba.setC_Currency_ID(line.getC_Currency_ID());
					sumba.setamtdocument(line.getAmount2());
					sumba.setAmtSourceDr(line.getAmount2());
					sumba.setAmtSourceCr(Env.ZERO);
					sumba.setAmtAcctCr(Env.ZERO);
					sumba.setCurrencyRate(line.getCurrencyRate());
					sumba.saveEx();

				}
				
				//muevo para la columna ADELANTO SUELDO
				if(line.getAmount3().compareTo(Env.ZERO)>0){

					sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
					sumba.setC_BankAccount_ID(cash.getC_BankAccount_ID());
					sumba.setDateTrx(TimeUtil.trunc(this.getDateTrx(),TimeUtil.TRUNC_DAY));
					sumba.setDueDate(TimeUtil.trunc(this.getDateTrx(),TimeUtil.TRUNC_DAY));
					sumba.setC_DocType_ID(this.getC_DocType_ID());
					sumba.setDocumentNo(this.getDocumentNo());
					sumba.setAD_Table_ID(I_UY_TR_Clearing.Table_ID);
					sumba.setRecord_ID(this.get_ID());
					sumba.setC_BPartner_ID(partner.get_ID());
					sumba.setDescription("Adelanto Sueldo");
					sumba.setC_Currency_ID(line.getC_Currency_ID());
					sumba.setamtdocument(line.getAmount3());
					sumba.setAmtSourceDr(line.getAmount3());
					sumba.setAmtSourceCr(Env.ZERO);
					sumba.setAmtAcctCr(Env.ZERO);
					sumba.setCurrencyRate(line.getCurrencyRate());
					sumba.saveEx();	

				}
				
			} else throw new AdempiereException("No se obtuvo caja asociada al chofer actual para la moneda " + curr.getCurSymbol());		
			
		}		
	}

	/***
	 * Verifica las lineas de la liquidacion antes de completar el documento.
	 * OpenUp Ltda. Issue #4340
	 * @author Nicolas Sarlabos - 10/06/2015
	 * @see
	 * @return
	 */
	private void verifyLines() {

		String sql = "";
		
		List<MTRClearingLine> lines = this.getLines();

		if(lines.size() <= 0) throw new AdempiereException("Imposible completar, el documento no tiene lineas");

		sql = "select uy_tr_clearingline_id" +
				" from uy_tr_clearingline" +
				" where saldofinal <> 0" +
				" and uy_tr_clearing_id = " + this.get_ID();

		int Id = DB.getSQLValueEx(get_TrxName(), sql);

		if(Id > 0) throw new AdempiereException("Imposible completar, el saldo final de todas las lineas debe ser CERO");		
		
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {

		// Before reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (this.processMsg != null)
			return false;
		
		DB.executeUpdateEx("delete from uy_sum_accountstatus where ad_table_id = " + I_UY_TR_Clearing.Table_ID +
				" and record_id = " + this.get_ID(), get_TrxName());

		// After reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (this.processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		//this.setPosted(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
