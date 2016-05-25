package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

public class MManufDelivery extends X_UY_ManufDelivery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026032213041707247L;

	public MManufDelivery(Properties ctx, int UY_ManufDelivery_ID,
			String trxName) {
		super(ctx, UY_ManufDelivery_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MManufDelivery(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String sql = "";
		BigDecimal qtyAvailable = Env.ZERO;
		BigDecimal qtyDelivered = Env.ZERO;
		BigDecimal qtyDesign = Env.ZERO;
		
		//OpenUp. Nicolas Sarlabos. 29/10/2012.
		//se controla no poder ingresar cantidades que superen las cantidades presupuestadas
		MManufOrder order = new MManufOrder(getCtx(), this.getUY_ManufOrder_ID(), null);
		
		Timestamp dateOrder = TimeUtil.trunc(order.getDateTrx(), TimeUtil.TRUNC_DAY);
		
		if(this.getQty().compareTo(Env.ZERO)<=0) throw new AdempiereException ("La cantidad ingresada debe ser mayor a cero");
		
		if(newRecord || is_ValueChanged("DatePromised")){
			if(this.getDatePromised()!=null){
				if(this.getDatePromised().compareTo(dateOrder)<0) throw new AdempiereException ("La fecha de entrega estimada no puede ser menor a la fecha de la OF");
			}
		}
		
		if(newRecord || is_ValueChanged("EndDate")){
			if(this.getEndDate()!=null){
				if(this.getEndDate().compareTo(dateOrder)<0) throw new AdempiereException ("La fecha de entrega real no puede ser menor a la fecha de la OF");
			}
		}

		//si tengo diseño
		if(this.getdesign()!=null){
							
				if(this.getdesign().equalsIgnoreCase("D1")){
					
					if(newRecord){
						//OpenUp. Nicolas Sarlabos. 15/04/2013. #708
						sql = "select coalesce(qtydesign1,0) from uy_manuforder where uy_manuforder_id=" + order.get_ID();
						qtyDesign = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP);//obtengo cantidad total del diseño
										
						sql = "select coalesce(sum(d.qty),0)" +
								" from uy_manufdelivery d" +
								" inner join uy_manuforder hdr on d.uy_manuforder_id=hdr.uy_manuforder_id" +
								" where hdr.uy_manuforder_id=" + order.get_ID() + " and d.design='D1' and d.m_product_id=" + this.getM_Product_ID();
						qtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad total entregada para el diseño 1

						qtyAvailable = qtyDesign.subtract(qtyDelivered);
						
						if(qtyAvailable.compareTo(Env.ZERO)>=0){

							if(this.getQty().compareTo(qtyAvailable)>0) throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas del diseño 1");

						}
						
					} else if (is_ValueChanged("qty")){
						
						sql = "select coalesce(qtydesign1,0) from uy_manuforder where uy_manuforder_id=" + order.get_ID();
						qtyDesign = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP);//obtengo cantidad total del diseño
										
						sql = "select coalesce(sum(d.qty),0)" +
								" from uy_manufdelivery d" +
								" inner join uy_manuforder hdr on d.uy_manuforder_id=hdr.uy_manuforder_id" +
								" where hdr.uy_manuforder_id=" + order.get_ID() + " and d.design='D1' and d.m_product_id=" + this.getM_Product_ID() +
								" and uy_manufdelivery_id <> " + this.get_ID();
						qtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad total entregada para el diseño 1

						qtyAvailable = qtyDesign.subtract(qtyDelivered);
						
						if(qtyAvailable.compareTo(Env.ZERO)>=0){

							if(this.getQty().compareTo(qtyAvailable)>0) throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas del diseño 1");

						}
						
					}

				} else if (this.getdesign().equalsIgnoreCase("D2")){

					if(newRecord){

						sql = "select coalesce(qtydesign2,0) from uy_manuforder where uy_manuforder_id=" + order.get_ID();
						qtyDesign = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP);//obtengo cantidad total del diseño

						sql = "select coalesce(sum(d.qty),0)" +
								" from uy_manufdelivery d" +
								" inner join uy_manuforder hdr on d.uy_manuforder_id=hdr.uy_manuforder_id" +
								" where hdr.uy_manuforder_id=" + order.get_ID() + " and d.design='D2' and d.m_product_id=" + this.getM_Product_ID();
						qtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad total entregada para el diseño 1

						qtyAvailable = qtyDesign.subtract(qtyDelivered);
						
						if(qtyAvailable.compareTo(Env.ZERO)>=0){

							if(this.getQty().compareTo(qtyAvailable)>0) throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas del diseño 2");

						}
						
					} else if (is_ValueChanged("qty")){
						
						sql = "select coalesce(qtydesign2,0) from uy_manuforder where uy_manuforder_id=" + order.get_ID();
						qtyDesign = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP);//obtengo cantidad total del diseño
										
						sql = "select coalesce(sum(d.qty),0)" +
								" from uy_manufdelivery d" +
								" inner join uy_manuforder hdr on d.uy_manuforder_id=hdr.uy_manuforder_id" +
								" where hdr.uy_manuforder_id=" + order.get_ID() + " and d.design='D2' and d.m_product_id=" + this.getM_Product_ID() +
								" and uy_manufdelivery_id <> " + this.get_ID();
						qtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad total entregada para el diseño 1

						qtyAvailable = qtyDesign.subtract(qtyDelivered);
						
						if(qtyAvailable.compareTo(Env.ZERO)>=0){

							if(this.getQty().compareTo(qtyAvailable)>0) throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas del diseño 2");

						}
						
					}

				} else if (this.getdesign().equalsIgnoreCase("D3")){

					if(newRecord){

						sql = "select coalesce(qtydesign3,0) from uy_manuforder where uy_manuforder_id=" + order.get_ID();
						qtyDesign = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP);//obtengo cantidad total del diseño
						
						sql = "select coalesce(sum(d.qty),0)" +
								" from uy_manufdelivery d" +
								" inner join uy_manuforder hdr on d.uy_manuforder_id=hdr.uy_manuforder_id" +
								" where hdr.uy_manuforder_id=" + order.get_ID() + " and d.design='D3' and d.m_product_id=" + this.getM_Product_ID();
						qtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad total entregada para el diseño 1

						qtyAvailable = qtyDesign.subtract(qtyDelivered);

						if(qtyAvailable.compareTo(Env.ZERO)>=0){

							if(this.getQty().compareTo(qtyAvailable)>0) throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas del diseño 3");

						}

					} else if (is_ValueChanged("qty")){

						sql = "select coalesce(qtydesign3,0) from uy_manuforder where uy_manuforder_id=" + order.get_ID();
						qtyDesign = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP);//obtengo cantidad total del diseño
						//Fin OpenUp. #708
						sql = "select coalesce(sum(d.qty),0)" +
								" from uy_manufdelivery d" +
								" inner join uy_manuforder hdr on d.uy_manuforder_id=hdr.uy_manuforder_id" +
								" where hdr.uy_manuforder_id=" + order.get_ID() + " and d.design='D3' and d.m_product_id=" + this.getM_Product_ID() +
								" and uy_manufdelivery_id <> " + this.get_ID();
						qtyDelivered = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad total entregada para el diseño 1

						qtyAvailable = qtyDesign.subtract(qtyDelivered);

						if(qtyAvailable.compareTo(Env.ZERO)>=0){

							if(this.getQty().compareTo(qtyAvailable)>0) throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas del diseño 3");

						}

					}

				}

				//no tengo diseño
		} else {

			if(newRecord){
				
				sql = "select coalesce(bl.qtyentered-coalesce(sum(md.qty),0),0)" +
						" from uy_budgetline bl" +
						" inner join uy_manufline ml on bl.uy_budgetline_id=ml.uy_budgetline_id" + 
						" left join uy_manufdelivery md on ml.m_product_id=md.m_product_id" +
						" where bl.m_product_id=" + this.getM_Product_ID() + " group by bl.qtyentered";

				qtyAvailable = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad presupuestada menos la cantidad ya ingresada para entregas

				if(qtyAvailable.compareTo(Env.ZERO)>=0){

					if(this.getQty().compareTo(qtyAvailable)>0) throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas");

				}
			} else if (is_ValueChanged("qty")){
			
				sql = "select coalesce(bl.qtyentered-coalesce(sum(md.qty),0),0)" +
						" from uy_budgetline bl" +
						" inner join uy_manufline ml on bl.uy_budgetline_id=ml.uy_budgetline_id" + 
						" left join uy_manufdelivery md on ml.m_product_id=md.m_product_id" +
						" where bl.m_product_id=" + this.getM_Product_ID() + " and uy_manufdelivery_id <> " + this.get_ID() + " group by bl.qtyentered";

				qtyAvailable = DB.getSQLValueBDEx(get_TrxName(), sql).setScale(2, RoundingMode.HALF_UP); //obtengo la cantidad presupuestada menos la cantidad ya ingresada para entregas

				if(qtyAvailable.compareTo(Env.ZERO)>=0){

					if(this.getQty().compareTo(qtyAvailable)>0) throw new AdempiereException ("La cantidad ingresada supera la cantidad disponible para entregas");

				}
			}

		}
		//Fin OpenUp.
		
		return true;
	}

	
}
