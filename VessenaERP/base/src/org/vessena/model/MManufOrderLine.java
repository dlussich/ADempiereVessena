package org.openup.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import org.compiere.apps.ADialog;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MUOMConversion;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.MPPOrder;



/**
 * Clase construida para el issue #801
 */

public class MManufOrderLine extends X_UY_ManufOrderLine {

	public MManufOrderLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}
	public MManufOrderLine(Properties ctx, int UY_ManufOrderLine_ID,
			String trxName) {
		super(ctx, UY_ManufOrderLine_ID, trxName);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2763628540614945858L;




	
	@Override
	protected boolean beforeSave(boolean newRecord) {
	
		MPPOrder ppOrder = (MPPOrder)this.getPP_Order();
		try{
			
			BigDecimal disponibleOrden=MPPOrder.getQtyAvailableToSale(ppOrder.get_ID());//Ir a buscarlo
			
			BigDecimal qtyOrdered = this.qtyOrdered(this.getC_Order_ID(),ppOrder, disponibleOrden,get_TrxName());
			
			//Si  lo que voy a guardar es mayor a lo disponible y o menor a cero
			if((this.getQtyOrdered().compareTo(qtyOrdered)>0 || this.getQtyOrdered().compareTo(Env.ZERO)<=0)){
									
				if(getQtyOrdered().compareTo(Env.ZERO)==0){
					this.setQtyOrdered(qtyOrdered);
					//ADialog.warn(0, null, null, "La orden de proceso "+ppOrder.getDocumentNo() +" solo tiene disponible "+qtyOrdered+" para este Pedido de venta");
				}
				
						
	
				if(getQtyOrdered().compareTo(qtyOrdered)>0){
					
					if(getQtyOrdered().compareTo(Env.ZERO)==0){
						ADialog.warn(0, null, null, "La orden de proceso "+ppOrder.getDocumentNo() +" no tiene disponible para este Pedido de venta");
						return false;
						
					}else if(getQtyOrdered().compareTo(Env.ZERO)<0){
						ADialog.warn(0, null, null, "La orden de proceso "+ppOrder.getDocumentNo() +" no tiene disponible para este Pedido de venta" +
								", ustede requirio "+qtyOrdered.negate()+" Unidades de mas");
						return false;
					}else{	
						ADialog.warn(0, null, null, "La orden de proceso "+ppOrder.getDocumentNo() +" solo tiene disponible "+qtyOrdered+" para este Pedido de venta");
						this.setQtyOrdered(qtyOrdered);
					}						
				}
			}
			
			BigDecimal factor = MUOMConversion.getProductRateTo(Env.getCtx(), ppOrder.getM_Product_ID(), ppOrder.getC_UOM_ID());
			
			if (factor == null)	factor = Env.ONE;
			
			this.setQtyEntered(this.getQtyOrdered().multiply(factor));
			
		}catch (Exception e){
			return false;
		}
		

		
		
		return true;
	}
	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)  {	
		return this.actualizar();
	}

		
	@Override
	protected boolean afterDelete(boolean success) {
		return this.actualizar();
	}

	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :recorre todas las mManualOrderLine y acumula los  productos y su cantidad (QtyOrdered) y borra las
	 * MOrderLines y las vuelve a generar con los datos acumulados
	 * @return
	 * @author  Nicolas Garcia 
	 * Fecha : 22/07/2011
	 */
	private boolean actualizar(){
		//m_product , qtyordered
		HashMap<Integer, BigDecimal> acumulador = new HashMap<Integer, BigDecimal>();
		
		MOrder cOrder = new MOrder(Env.getCtx(), this.getC_Order_ID(), get_TrxName());
		
		try{
			
			cargarAcumulados(cOrder,acumulador,get_TrxName());
			guardarAcumuldo(cOrder, acumulador, get_TrxName());
			
			
		}catch(Exception e){
			ADialog.warn(0, null,null,e.toString());
			return false;
		}
			
		return true;
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que borra las mOrderLines y carga el HashMap que recibe como parametro con los productos y cantidades (QtyOrdered)
	 * @param cOrder
	 * @param acumulador
	 * @param trxName
	 * @throws Exception
	 * @author  Nicolas Garcia 
	 * Fecha : 22/07/2011
	 */
	private void cargarAcumulados(MOrder cOrder,HashMap<Integer, BigDecimal> acumulador,String trxName) throws Exception {
		
		//Obtengo lineas
		MOrderLine[] 	      orderLines= cOrder.getLines();
		
		//borro las lineas Actuales
		for(int i=0;i<orderLines.length;i++){
						
			if(orderLines[i].getM_Product_ID()==this.getM_Product_ID()){
				//Borro lineas
				orderLines[i].deleteEx(false, trxName);
			}
		}
		
		
		MManufOrderLine[] manufOrderLines = MManufOrderLine.getManufOrderLines(cOrder.getC_Order_ID(),"AND m_product_id="+this.getM_Product_ID(), trxName);
		
		for(int i=0;i<manufOrderLines.length;i++){//Acumulo //no se deberia repetir
			
			if(acumulador.containsKey(manufOrderLines[i].getM_Product_ID())){
				
				//Acumulo
				acumulador.put(manufOrderLines[i].getM_Product_ID(), acumulador.get(manufOrderLines[i].getM_Product_ID()).add(manufOrderLines[i].getQtyOrdered()));
			}else{
				
				//Agrego
				acumulador.put(manufOrderLines[i].getM_Product_ID(), manufOrderLines[i].getQtyOrdered());
			}
			
		}
	}
	
	/**
	 * 	
	 * OpenUp.	
	 * Descripcion :Metodo que recibe un hashMap y del mismo guarda orderlines apliando en una linea por unidad de venta (ej FX08)y si hay restos que 
	 * no entrar en la unidad de venta genera otra linea con este resto en unidades simple(ej Unidad)
	 * @param cOrder
	 * @param acumulador
	 * @param trxName
	 * @author  Nicolas Garcia
	 * Fecha : 22/07/2011
	 */
	private void guardarAcumuldo(MOrder cOrder,HashMap<Integer, BigDecimal>acumulador,String trxName){
				
		Integer[] keys=acumulador.keySet().toArray(new Integer[acumulador.size()]);
		 
		for(int i=0;i<keys.length;i++ ){
			
			//Traigo la unodad de venta
			int cUOMto=DB.getSQLValue(null, "SELECT COALESCE (c_uom_to_id,0) FROM c_uom_conversion WHERE isactive ='Y' " +
					"AND m_product_id=?", keys[i]);
			int cUOM =DB.getSQLValue(null, "SELECT COALESCE (c_uom_id,0) FROM m_product WHERE isactive ='Y' " +
					"AND m_product_id=?", keys[i]);
			
			
			BigDecimal factor = MUOMConversion.getProductRateFrom(Env.getCtx(), keys[i], cUOMto);
			
			if (factor == null) {
				factor = Env.ONE;
				cUOMto=cUOM;
			}
			
			//Divido la suma entre el factor, devuelve un array, el primer valor es resultado, el segundo es el resto
			MathContext mc= new MathContext(0);
			BigDecimal[] cuenta=	acumulador.get(keys[i]).divideAndRemainder(factor, mc);
			
			if(cuenta[1].compareTo(Env.ZERO)>0){
				//Guardo con el sobrante que no entra en la unidad de venta
				saveNewLine(cOrder,cUOM,cuenta[1],keys[i],trxName);
			}
			if(cuenta[0].compareTo(Env.ZERO)>0){
				//Guardo la linea en unidad de venta le paso como cantidad la cantidad total menos el resto
				saveNewLine(cOrder,cUOMto,cuenta[0],keys[i],trxName);
			}
		}
		
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que guarda una orderLine
	 * @param cOrder
	 * @param cUOMid
	 * @param QtyEntered
	 * @param mProductID
	 * @param trxName
	 * @author  Nicolas Garcia
	 * Fecha : 22/07/2011
	 */
	private void saveNewLine(MOrder cOrder,int cUOMid,BigDecimal QtyEntered,int mProductID,String trxName){
		// Creo la linea detalles
		MOrderLine linea = new MOrderLine(cOrder);
		
		//Seteo Campos
		
		//linea.setPP_Order_ID(this.getPP_Order_ID());
		linea.setM_Warehouse_ID(cOrder.getM_Warehouse_ID());
		linea.setM_Product_ID(mProductID);
		linea.setC_UOM_ID(cUOMid);
		linea.setQtyEntered(QtyEntered);

		BigDecimal factor = MUOMConversion.getProductRateFrom(Env.getCtx(), linea.getM_Product_ID(),linea.getC_UOM_ID());
		if (factor == null) factor = Env.ONE;
		linea.setQtyOrdered(linea.getQtyEntered().multiply(factor));
		
		linea.setFormatInfo(null);
		linea.saveEx(trxName);
	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Metodo que devuelve un array de MManufOrderLine asociados a una C_Order
	 * @param cOrderID
	 * @param where
	 * @param trxName
	 * @return
	 * @throws Exception
	 * @author  Nico 
	 * Fecha : 22/07/2011
	 */
	public static MManufOrderLine[] getManufOrderLines(int cOrderID,String where,String trxName ) throws Exception{
		
		
		ArrayList<MManufOrderLine> aux = new ArrayList<MManufOrderLine>();
	
		
		String sql ="SELECT uy_manuforderline_id FROM uy_manuforderline WHERE c_order_id =? "+where;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql,trxName);
			pstmt.setInt(1, cOrderID);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				aux .add(new MManufOrderLine(Env.getCtx(),rs.getInt("uy_manuforderline_id"),trxName));
			}
			
			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	
		return aux.toArray(new MManufOrderLine[aux.size()]);
	}
	

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que calcula la cantidad disponible de un producto en una PPOrder para una corder.
	 * Es por si se selecciona varias veces la misma pporde
	 * @param cOrderID
	 * @param pOrder
	 * @param disponible
	 * @param trxName
	 * @return
	 * @throws Exception
	 * @author  Nico 
	 * Fecha : 22/07/2011
	 */
	private BigDecimal qtyOrdered(int cOrderID, MPPOrder pOrder, BigDecimal disponible,String trxName) throws Exception {
		
		BigDecimal salida =disponible;
		
		//Busco las ManufOrderLines
		MManufOrderLine[] lines = MManufOrderLine.getManufOrderLines(cOrderID,"",trxName);
		
		for (int i=0;i<lines.length;i++){
			
			//Resto del disponible de una PPOrden lo que ya use en esta COrder (de lo que se esta acumulado en MManufOrderLine)
			if(lines[i].getM_Product_ID()==pOrder.getM_Product_ID() && lines[i].getPP_Order_ID()==pOrder.get_ID()
					&& lines[i].get_ID()!=this.get_ID()){
				salida=salida.subtract(lines[i].getQtyOrdered());
			}
			
		}
		
		
	return salida.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
