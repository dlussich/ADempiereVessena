package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MPeriod;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;


public class MMBStoreLoadOrder extends X_UY_MB_StoreLoadOrder implements I_UY_MB_StoreLoadOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2023367114010972967L;
	

	public MMBStoreLoadOrder(Properties ctx, int UY_MB_StoreLoadOrder_ID, String trxName) {
		super(ctx, UY_MB_StoreLoadOrder_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Hace el update de los datos de la tienda y sus tablas asociadas una vez sincronizada del dispositivo
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success){
			return success;
		}
		
		if (this.isExecuted()){
			this.insertSLOWays();
			this.updateSeals();
			this.updateSotoredCash();
			this.insertSales();
			this.insertCrew();
			this.insertReturns();
			
			MStoreLoadOrder mSLO = new MStoreLoadOrder(getCtx(), this.getUY_StoreLoadOrder_ID(), get_TrxName());
			mSLO.setIsExecuted(true);
			mSLO.saveEx();
		}
			
		return true;
	}
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Inserta las rutas asociadas a la tienda
	 */
	private void insertSLOWays(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			String qry = "select uy_way_id, paxno, dev_wayid from uy_mb_storeloadorderway where UY_MB_StoreLoadOrder_ID = " + this.get_ID();
			ps = DB.prepareStatement(qry, get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				do{
					MStoreLoadOrderWay sloWay = new MStoreLoadOrderWay(getCtx(), 0, get_TrxName());
					sloWay.setUY_StoreLoadOrder_ID(this.getUY_StoreLoadOrder_ID());
					sloWay.setUY_Way_ID(rs.getInt(1));
					sloWay.setpaxno(rs.getInt(2));
					sloWay.setdev_WayID(rs.getInt(3));
					sloWay.saveEx();
				}while(rs.next());
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs, ps);
		}
	}
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Inserta las rutas asociadas al precinto
	 */
	private void updateSeals(){
	PreparedStatement ps = null, ps2 = null;
	ResultSet rs = null, rs2 = null;
	try{
		//Traigo los id de los precintos y la ruta que se le asocio en el dispositivo
		String qry = "select uy_storeloadorderseal_id, dev_wayid from uy_mb_storeloadorderseal where uy_mb_storeloadorder_id = " + this.get_ID();
		ps = DB.prepareStatement(qry, get_TrxName());
		rs = ps.executeQuery();
		if(rs.next()){
			do{
				//Traigo el ID real de la ruta creada en adempiere
				String qry2 = "select uy_storeloadorderway_id from uy_storeloadorderway where uy_storeloadorder_id = "+ 
								this.getUY_StoreLoadOrder_ID() + " and dev_wayId = " + rs.getInt(2);
				ps2 = DB.prepareStatement(qry2, get_TrxName());
				rs2 = ps2.executeQuery();
				if(rs2.next()){
					MStoreLoadOrderSeal sloSeal = new MStoreLoadOrderSeal(getCtx(), rs.getInt(1), get_TrxName());
					sloSeal.setUY_StoreLoadOrderWay_ID(rs2.getInt(1));
					sloSeal.saveEx();
				}
				
			}while(rs.next());
		}
	}catch(Exception e){
		throw new AdempiereException(e.getMessage());
	}finally{
		DB.close(rs2, ps2);
		DB.close(rs, ps);
	}
}

	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Actualiza el monto de Caja
	 */
	private void updateSotoredCash(){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			String qry = "select amt from UY_MB_StoreCash where UY_MB_StoreLoadOrder_ID = " + this.get_ID();
			ps = DB.prepareStatement(qry, get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				MStoreLoadOrder slo = new MStoreLoadOrder(getCtx(), this.getUY_StoreLoadOrder_ID(), get_TrxName());
				MStoreCash sc = new MStoreCash(getCtx(), slo.getUY_StoreCash_ID(), get_TrxName());
				sc.setAmt(rs.getBigDecimal(1));
				sc.saveEx();
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs, ps);
		}
	}
	
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Inserto las ventas
	 */
	private void insertSales(){
		PreparedStatement ps = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String qry = "select uy_mb_storelosale_id from uy_mb_storelosale where uy_mb_storeloadorder_id = " + this.get_ID();
		try{
			ps = DB.prepareStatement(qry,get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				do{
					MMBStoreLOSale mbSale = new MMBStoreLOSale(getCtx(), rs.getInt(1), get_TrxName());
					String qry2 = "select UY_StoreLoadOrderWay_ID from UY_StoreLoadOrderWay" +
									" where uy_storeloadorder_id = " + this.getUY_StoreLoadOrder_ID() +" and dev_wayId = " + mbSale.getdev_SaleID();
					ps2 = DB.prepareStatement(qry2,get_TrxName());
					rs2 = ps2.executeQuery();
					MStoreLOSale sale = new MStoreLOSale(getCtx(), 0, get_TrxName());
					sale.setUY_StoreLoadOrder_ID(this.getUY_StoreLoadOrder_ID());
					if(rs2.next()){
						sale.setUY_StoreLoadOrderWay_ID(rs2.getInt(1));
					}
					sale.setTotalAmt(mbSale.getTotalAmt());
					sale.setsaledate(mbSale.getsaledate());
					sale.saveEx();
					this.insertSaleLines(sale.get_ID(), mbSale.get_ID());
					this.insertPayLines(sale.get_ID(), mbSale.get_ID());
				}while(rs.next());
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs2, ps2);
			DB.close(rs, ps);
		}
	}
	
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Inserto las lineas de venta
	 */
	private void insertSaleLines(int saleID, int mbSaleID){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String qry = "select uy_mb_storelosaleline_id from uy_mb_storelosaleline where uy_mb_storelosale_id = " + mbSaleID;
		try{
			ps = DB.prepareStatement(qry,get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				do{
					MMBStoreLOSaleLine mbSaleLine = new MMBStoreLOSaleLine(getCtx(), rs.getInt(1), get_TrxName());
					MStoreLOSaleLine saleLine = new MStoreLOSaleLine(getCtx(), 0, get_TrxName());
					saleLine.setUY_StoreLOSale_ID(saleID);
					saleLine.setM_Product_ID(mbSaleLine.getM_Product_ID());
					saleLine.setproductqty(mbSaleLine.getproductqty());
					saleLine.setproductpu(mbSaleLine.getproductpu());
					saleLine.setLineTotalAmt(mbSaleLine.getLineTotalAmt());
					saleLine.saveEx();
				}while(rs.next());
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs, ps);
		}
	}
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Inserto las lineas de pago
	 */
	private void insertPayLines(int saleID, int mbSaleID){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String qry = "select uy_mb_storelopay_id from uy_mb_storelopay where uy_mb_storelosale_id = " + mbSaleID;
		try{
			ps = DB.prepareStatement(qry,get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				do{
					MMBStoreLOPay mbPay = new MMBStoreLOPay(getCtx(), rs.getInt(1), get_TrxName());
					MStoreLOPay pay = new MStoreLOPay(getCtx(), 0, get_TrxName());
					pay.setUY_StoreLoadOrder_ID(this.getUY_StoreLoadOrder_ID());
					pay.setUY_StoreLOSale_ID(saleID);
					pay.setC_Currency_ID(mbPay.getC_Currency_ID());
					pay.setamtusd(mbPay.getamtusd());
					pay.setamtmt(mbPay.getamtmt());
					pay.setpaytype(mbPay.getpaytype());
					pay.settransno(mbPay.gettransno());
					pay.setcreditcard(mbPay.getcreditcard());
					pay.saveEx();
				}while(rs.next());
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs, ps);
		}
	}
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Inserto la tripulacion
	 */
	private void insertCrew(){
		PreparedStatement ps = null, ps2 = null, ps3 = null;
		ResultSet rs = null, rs2 = null, rs3 = null;
		String qry = "select uy_mb_storeloadorderway_id, dev_wayId from uy_mb_storeloadorderway where uy_mb_storeloadorder_id = " + this.get_ID();
		try{
			ps = DB.prepareStatement(qry,get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				do{
					String qry2 = "select uy_mb_storelocrew_id from uy_mb_storelocrew where uy_mb_storeloadorderway_id = " + rs.getInt(1);
					ps2 = DB.prepareStatement(qry2,get_TrxName());
					rs2 = ps2.executeQuery();
					if(rs2.next()){
						do{
							String qry3 = "select UY_StoreLoadOrderWay_ID from UY_StoreLoadOrderWay" +
									" where uy_storeloadorder_id = " + this.getUY_StoreLoadOrder_ID() +" and dev_wayId = " + rs.getInt(2);
							ps3 = DB.prepareStatement(qry3,get_TrxName());
							rs3 = ps3.executeQuery();
							MMBStoreLOCrew mbCrew = new MMBStoreLOCrew(getCtx(), rs2.getInt(1), get_TrxName());
							MStoreLOCrew crew = new MStoreLOCrew(getCtx(), 0, get_TrxName());
							if(rs3.next()){
								crew.setUY_StoreLoadOrderWay_ID(rs3.getInt(1));
							}
							crew.setAD_User_ID(mbCrew.getAD_User_ID());
							crew.setcockpitboss(mbCrew.iscockpitboss());
							crew.saveEx();
						}while(rs2.next());
					}
				}while(rs.next());
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs3, ps3);
			DB.close(rs2, ps2);
			DB.close(rs, ps);
		}
	}
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Inserto las devoluciones
	 */
	private void insertReturns(){
		PreparedStatement ps = null, ps2 = null;
		ResultSet rs = null, rs2 = null;
		String qry = "select uy_mb_storeloret_id from uy_mb_storeloret where uy_mb_storeloadorder_id = " + this.get_ID();
		try{
			ps = DB.prepareStatement(qry,get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				do{
					MMBStoreLORet mbRet = new MMBStoreLORet(getCtx(), rs.getInt(1), get_TrxName());
					String qry2 = "select UY_StoreLoadOrderWay_ID from UY_StoreLoadOrderWay" +
									" where uy_storeloadorder_id = " + this.getUY_StoreLoadOrder_ID() +" and dev_wayId = " + mbRet.getdev_WayID();
					ps2 = DB.prepareStatement(qry2,get_TrxName());
					rs2 = ps2.executeQuery();
					MStoreLORet ret = new MStoreLORet(getCtx(), 0, get_TrxName());
					ret.setUY_StoreLoadOrder_ID(this.getUY_StoreLoadOrder_ID());
					if(rs2.next()){
						ret.setUY_StoreLoadOrderWay_ID(rs2.getInt(1));
					}
					ret.setamtusd(mbRet.getamtusd());
					ret.setDateDoc(mbRet.getDateDoc());
					ret.saveEx();
					this.insertRetLines(ret.get_ID(), mbRet.get_ID());
				}while(rs.next());
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs2, ps2);
			DB.close(rs, ps);
		}
	}
	
	/**
	 * Issue #5213 - Emiliano Bentancor
	 * Inserto las lineas de devoluciones
	 */
	private void insertRetLines(int retId, int mbRetId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String qry = "select uy_mb_storeloretLine_id from uy_mb_storeloretLine where uy_mb_storeloret_id = " + mbRetId;
		try{
			ps = DB.prepareStatement(qry,get_TrxName());
			rs = ps.executeQuery();
			if(rs.next()){
				do{
					MMBStoreLORetLine mbRetLine = new MMBStoreLORetLine(getCtx(), rs.getInt(1), get_TrxName());
					MStoreLORetLine retLine = new MStoreLORetLine(getCtx(), 0, get_TrxName());
					retLine.setUY_StoreLORet_ID(retId);
					retLine.setM_Product_ID(mbRetLine.getM_Product_ID());
					retLine.setproductqty(mbRetLine.getproductqty());
					retLine.setproductpu(mbRetLine.getproductpu());
					retLine.setamtusd(mbRetLine.getamtusd());
					retLine.saveEx();
				}while(rs.next());
			}
		}catch(Exception e){
			throw new AdempiereException(e.getMessage());
		}finally{
			DB.close(rs, ps);
		}
	}
}















