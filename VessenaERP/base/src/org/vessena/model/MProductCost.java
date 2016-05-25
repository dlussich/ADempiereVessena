package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MConversionRate;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.MPPProductBOMLine;

public class MProductCost extends X_UY_ProductCost {

	private static int currency = 142;
	private static int AD_Client_ID = 1000005;
	private static int AD_Org_ID = 1000005;

	private static HashMap<Integer, MPPProductBOMLine[]> bomProducto = new HashMap<Integer, MPPProductBOMLine[]>();


	private static final long serialVersionUID = -3798682400575141290L;

	public MProductCost(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MProductCost(Properties ctx, int UY_ProductCost_ID, String trxName) {
		super(ctx, UY_ProductCost_ID, trxName);
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que devuelve el costo de un producto en moneda por defecto para una fecha.
	 * @param productID
	 * @param costingDay
	 * @param trxName
	 * @return
	 * @throws Exception
	 * @author  Nicolas Garcia
	 * Fecha : 09/12/2011
	 */
	public static BigDecimal getProductCost(int productID, Timestamp costingDay, String trxName) throws Exception {

		// Validaciones iniciales
		if (productID <= 0) {
			throw new Exception("No se pudo guardar registro posiblemente producto invalido");
		}

		BigDecimal salida = new BigDecimal(-1);

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			if (costingDay == null) {
				costingDay = new Timestamp(System.currentTimeMillis());
			}

			Timestamp fecha = TimeUtil.trunc(costingDay, TimeUtil.TRUNC_DAY);

			// Sumo un dia
			long oneDay = 1 * 24 * 60 * 60 * 1000;
			fecha.setTime(fecha.getTime() + oneDay);

			String sql = "SELECT COALESCE (amtacct,-1) as Costo FROM uy_productcost WHERE datetrx<'" + fecha + "' AND m_product_id=" + productID
					+ " ORDER BY datetrx desc,created desc";

			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();

			// Me quedo con el primero
			if (rs.next()) {
				salida = rs.getBigDecimal("Costo");
			}

			if (salida.compareTo(Env.ZERO) < 0) {
				return new BigDecimal(-1);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return salida;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que devuelve el Objeto MProductCost de un producto en moneda por defecto para una fecha.
	 * @param productID
	 * @param costingDay
	 * @param trxName
	 * @return
	 * @throws Exception
	 * @author  Nicolas Garcia
	 * Fecha : 09/12/2011
	 */
	public static MProductCost getProductCostObject(int productID, Timestamp costingDay, String trxName, Properties ctx) throws Exception {

		// Validaciones iniciales
		if (productID <= 0) {
			throw new Exception("No se pudo guardar registro posiblemente producto invalido");
		}


		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			if (costingDay == null) {
				costingDay = new Timestamp(System.currentTimeMillis());
			}

			Timestamp fecha = TimeUtil.trunc(costingDay, TimeUtil.TRUNC_DAY);

			// Sumo un dia
			long oneDay = 1 * 24 * 60 * 60 * 1000;
			fecha.setTime(fecha.getTime() + oneDay);

			String sql = "SELECT *  FROM uy_productcost WHERE datetrx<'" + fecha + "' AND m_product_id=" + productID
					+ " ORDER BY datetrx desc,created desc";

			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();

			// Me quedo con el primero
			if (rs.next()) {
				return new MProductCost(ctx, rs, trxName);
			} else {
				//OpenUp Nicolas Sarlabos #939 03/02/2012, en caso de no encontrar costo...
				MProductCost m = new MProductCost(ctx, 0, trxName);
				m.setAmtAcct(null);
				m.setUY_TipoCosteo("NE");

				return m;
				//fin OpenUp Nicolas Sarlabos #939 03/02/2012


			}

		} catch (Exception e) {
			throw e;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}


	}

	/**
	 * OpenUp.	
	 * Descripcion :Metodo que dispara el calculo de costos de productos.
	 * @author  Nicolas Garcia
	 * Fecha : 09/12/2011
	 * 
	 * Update : Gabriel Vila. 25/06/2012. Issue #1032.
	 * Se agrego parametro que recibe un id de producto a procesar o CERO para procesar todos.
	 */
	public static void calculateCosts(int mProductID, boolean usarTipoCambioActual) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		HashMap<Integer, Integer> aRecalcular = new HashMap<Integer, Integer>();
		HashMap<Integer, BigDecimal> costoProducto = new HashMap<Integer, BigDecimal>();
		HashMap<Integer, String> metodoCosteo = new HashMap<Integer, String>();

		Timestamp inicio = new Timestamp(System.currentTimeMillis());

		try {
			
			DB.executeUpdateEx(" delete from uy_productcost ", null);
			
			// Cargo en memoria lista bom
			MProductCost.loadBomProductList();

			String whereProd = "";
			if (mProductID > 0) {
				whereProd = " AND m_product_id = " + mProductID;
			}
			
			// Selecciono los productos a calcular.
			sql = " SELECT M_Product_id " +
				  " FROM M_Product " +
				  " WHERE isActive='Y' " + whereProd +
				  " AND ad_client_ID=" + MProductCost.AD_Client_ID + 
				  " ORDER BY isbom";

			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				aRecalcular.put(rs.getInt("m_product_id"), rs.getInt("m_product_id"));
				// aRecalcular.put(1029343, 1029343);
			}

			// Disparo el calculo pasando hashMap como parametro con los
			// productos a calcular
			MProductCost.historicoCosto(costoProducto, null, aRecalcular, metodoCosteo, usarTipoCambioActual);

			Timestamp fin = new Timestamp(System.currentTimeMillis());
			System.out.println("Inicio: " + inicio);
			System.out.println("Fin: " + fin);

		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	private static void historicoCosto(HashMap<Integer, BigDecimal> costoProducto, String trx, HashMap<Integer, Integer> aRecalcular,
			HashMap<Integer, String> metodoCosteo, boolean usarTipoCambioActual) throws Exception {

		Properties ctx = Env.getCtx();
		MProduct product = null;
		boolean llamoRecursivo = false;

		
		HashMap<Integer, Integer> aRecalAPasar = new HashMap<Integer, Integer>();

		try {

			Iterator<Integer> it = aRecalcular.keySet().iterator();
			int in = aRecalcular.size();
			int ac = 0;

			while (it.hasNext()) {

				ac++;
				System.out.println("Procesando " + ac + " de " + in);

				Integer productID = it.next();
				llamoRecursivo = true;
				product = new MProduct(ctx, productID, null);

				if (product.get_ID() <= 0) {
					throw new AdempiereException("No se pudo obtener producto con ID : " + productID);
				}
				
				// Calculo costo
				BigDecimal costo = MProductCost.recursivoCostos(costoProducto, product.getM_Product_ID(), Env.ONE, 0, null, aRecalAPasar, metodoCosteo, usarTipoCambioActual);

				if (costo == null) costo = Env.ZERO;
				
				// Guardo Costo en memoria
				costoProducto.put(productID, costo);

				// Obtengo los productos bom que incluyen este producto
				Integer[] prodFath = MProduct.getFathersBom(productID);

				// Guardo en memoria padres para recalcular sus precios
				for (int i = 0; i < prodFath.length; i++) {
					aRecalAPasar.put(prodFath[i], prodFath[i]);
				}
			}

			// Si todavia tengo productos que calcular
			if (llamoRecursivo) {
				// llamo recursivo
				MProductCost.historicoCosto(costoProducto, trx, aRecalAPasar, metodoCosteo, usarTipoCambioActual);
			} else {

				// Guardo en Base de Datos
				Iterator<Integer> it2 = costoProducto.keySet().iterator();
				while (it2.hasNext()) {
					Integer productID = it2.next();
					MProductCost.add(productID, metodoCosteo.get(productID), 142, costoProducto.get(productID), "Generado Proceso", null);
				}
			}

		} catch (Exception e) {
			System.out.println("ERROR en producto : " + product.get_ID() + e);
			throw e;
		}
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Metodo que carga en memoria las Listas de materiales Activas.
	 * @author  Nicolas Garcia
	 * Fecha : 09/12/2011
	 * @throws Exception 
	 */
	private static void loadBomProductList() throws Exception {

		// Vacio lo antiguo
		bomProducto = new HashMap<Integer, MPPProductBOMLine[]>();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			sql = " SELECT bom.m_product_id as padre,bomline.*" + " FROM pp_product_bom bom"
					+ " INNER JOIN pp_product_bomline bomline ON bomline.pp_product_bom_id=bom.pp_product_bom_id"
					+ " INNER JOIN m_product m_product ON m_product.m_product_id=bom.m_product_id" + " WHERE m_product.ad_client_id ="
					+ MProductCost.AD_Client_ID + "AND m_product.isactive ='Y' " + " AND bom.IsActive = 'Y' "// +
					+ " ORDER BY bom.m_product_id asc ";

			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			int productID = 0;
			List<MPPProductBOMLine> list = null;

			while (rs.next()) {

				// Si el padre cambia
				if (productID != rs.getInt("padre")) {

					// Guardo info
					if (list != null) {
						MProductCost.bomProducto.put(productID, list.toArray(new MPPProductBOMLine[list.size()]));
					}

					list = new ArrayList<MPPProductBOMLine>();
					productID = rs.getInt("padre");
				}

				list.add(new MPPProductBOMLine(Env.getCtx(), rs, null));

			}

		} catch (Exception e) {
			throw e;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	private static void add(int productID, String tipoCosteo, int currencyID, BigDecimal costoMoneda, String Descripcion, String trxName)
			throws Exception {

		try {

			Timestamp fecha = new Timestamp(System.currentTimeMillis());

			BigDecimal costoActual = MProductCost.getProductCost(productID, fecha, null);

			// Si el costo actual es distinto al nuevo costo agrego sino salgo
			if (costoActual.compareTo(costoMoneda) != 0) {

				BigDecimal divideRate = MConversionRate.getDivideRate(currency, currencyID, fecha, 0, Env.getAD_Client_ID(Env.getCtx()), Env.getAD_Org_ID(Env
						.getCtx()));

				MProductCost prodCost = new MProductCost(Env.getCtx(), 0, trxName);

				if (prodCost.getAD_Client_ID() <= 0) {
					prodCost.setAD_Client_ID(MProductCost.AD_Client_ID);
					prodCost.setAD_Org_ID(MProductCost.AD_Org_ID);
					prodCost.setUpdatedBy(100);
				}

				prodCost.setM_Product_ID(productID);
				prodCost.setUY_TipoCosteo(tipoCosteo);
				prodCost.setC_Currency_ID(currencyID);

				if (divideRate.compareTo(Env.ZERO) == 0) {
					divideRate = Env.ONE;
				}
				prodCost.setDivideRate(divideRate);
				prodCost.setAmtAcct(costoMoneda.divide(divideRate));
				prodCost.setAmtInCurrency(costoMoneda);
				prodCost.setDescription(Descripcion);
				prodCost.setDateTrx(fecha);
				System.out.println("Guardando en tabla " + productID);
				prodCost.saveEx();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private static BigDecimal recursivoCostos(HashMap<Integer, BigDecimal> costoProducto, int productID, BigDecimal cantidad,
 int iteracion, String trxName,
			HashMap<Integer, Integer> aRecalcular, HashMap<Integer, String> metodoCosteo, boolean usarTipoCambioActual) throws Exception {

		boolean tieneHijos = false;
		BigDecimal aux = Env.ZERO;

		try {

			MPPProductBOMLine[] lines = MProductCost.bomProducto.get(productID);

			// ProcesoHijos
			if (lines != null) {
				for (int i = 0; i < lines.length; i++) {

					// OpenUp. Gabriel Vila. 03/05/2012
					// Si dentro de la formula de un producto padre tengo como hijo al propio padre,
					// lo cual es un claro error de la definicion de la formula, me cubro de no entrar en loop.
					if (lines[i].getM_Product_ID() == productID)
						continue;
					// Fin OpenUp.

					
					// el linebom en el llamado se convierte en bom
					iteracion++;
					aux = aux.add(MProductCost.recursivoCostos(costoProducto, lines[i].getM_Product_ID(), 
							(lines[i].getQtyBOM().multiply(cantidad)), iteracion, trxName, aRecalcular, metodoCosteo, usarTipoCambioActual));

					// Metodo Costeo = bom
					metodoCosteo.put(productID, "BM");
				}

				// Entra si se desea calcular el precio del producto
			} else if (!(tieneHijos) && iteracion == 0) {

				aux = aux.add(getCostoProducto(productID, cantidad, metodoCosteo, usarTipoCambioActual).setScale(9, RoundingMode.UP));

				// entra si solo se necesita el costo
			} else if (!(tieneHijos) && iteracion != 0) {

				// Se busca precio ya guarado ya que el dato se necesita para
				// calcular costo de otro articulo.
				BigDecimal costoUnidad = MProductCost.getProductCostRecursivo(costoProducto, productID, aRecalcular, trxName);

				if (costoUnidad == null) costoUnidad = Env.ZERO;
				
				if (aux.compareTo(Env.ZERO) >= 0) {
					aux = aux.add(costoUnidad.multiply(cantidad).setScale(9, RoundingMode.UP));
				} else {
					throw new Exception("Error Inesperado 1");
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {

		}

		return aux.setScale(9, RoundingMode.UP);

	}

	private static BigDecimal getProductCostRecursivo(HashMap<Integer, BigDecimal> costoProducto, int productID, 
													  HashMap<Integer, Integer> aRecalcular, String trxName) throws Exception {

		BigDecimal salida = Env.ZERO;

		// Si lo tengo en memoria
		if (costoProducto.containsKey(productID)) {
			salida = costoProducto.get(productID);
		} else {

			// Si el costo no esta ya calculado.
			salida = MProductCost.getProductCost(productID, null, trxName);

			// Si es menor a cero quiere decir que hay que calcular nuevamente
			if (salida.compareTo(Env.ZERO) <= 0) {

				salida = new BigDecimal("-99999999999");
				aRecalcular.put(productID, productID);
			}
		}
		return salida;
	}

	/***
	 * Obtiene y retorna costo de un producto segun esta logica :
	 * 1. Ultima Compra
	 * 2. Si tiene Factura de Importacion entonces costo = 0
	 * 3. Si costo = 0 entonces costo estandar (manual).
	 * OpenUp Ltda. Issue #869 
	 * @author Gabriel Vila - 14/06/2012
	 * @see
	 * @param productID
	 * @param cantidad
	 * @param metodoCosteo
	 * @param usarTipoCambioActual 
	 * @return
	 * @throws Exception
	 */
	private static BigDecimal getCostoProducto(int productID, BigDecimal cantidad, HashMap<Integer, String> metodoCosteo, boolean usarTipoCambioActual) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal salida = Env.ZERO;

		try {
			
			String fechaCosto = "c_invoice.dateinvoiced ";
			
			if (usarTipoCambioActual)				
				fechaCosto = " '" + TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY) + "' ";
			
			sql = "SELECT coalesce((SELECT currencybase(priceentered, c_currency_id," + fechaCosto + ", " + MProductCost.AD_Client_ID + ", "
					+ MProductCost.AD_Org_ID + ")),0) as costo,dateinvoiced FROM c_invoiceline "
					+ " INNER JOIN c_invoice ON c_invoice.c_invoice_id=c_invoiceline.c_invoice_id "
					+ " AND c_invoice.docstatus='CO' AND c_invoice.issotrx='N' "
					+ " WHERE c_invoice.ad_client_id = " + MProductCost.AD_Client_ID 
					+ " AND c_invoice.ad_org_id = " + MProductCost.AD_Org_ID
					+ " AND m_product_id = " + productID + " " 
					+ " ORDER BY c_invoice.dateinvoiced desc, c_invoice.c_invoice_id desc ";

			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			Timestamp fecha = null;
			// Ultima Factura
			if (rs.next()) {
				fecha = rs.getTimestamp("dateinvoiced");
				salida = (rs.getBigDecimal("costo")).multiply(cantidad).setScale(9, RoundingMode.UP);
				metodoCosteo.put(productID, "FL");
			}

			/**
			 * COSTO FACTURA IMPORTACION ULTIMA ISSUE 730
			 * 	 Si tiene precio ultima factura, y esta es de importacion se tomara como precio stander
			 */

			if (salida.compareTo(BigDecimal.ZERO) > 0) {
				// Pregunto si hay factura importacion para ese producto que sea
				// mayor a la fecha de ultam fact
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;

				sql = " SELECT c_invoice.dateinvoiced " +
					  " FROM c_invoiceline " +
					  " INNER JOIN c_invoice ON (c_invoice.c_invoice_id=c_invoiceline.c_invoice_id AND c_invoice.docstatus='CO') " + 
					  " WHERE c_invoice.ad_client_id = " + MProductCost.AD_Client_ID + 
					  " AND c_invoice.ad_org_id = " + MProductCost.AD_Org_ID +
					  " AND c_invoice.c_doctype_id = " + getDocFactImpID() +
					  " AND M_Product_ID =" + productID +						 
					  " AND dateinvoiced>='" + fecha + "'";			

				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				// Si existe uno se usa costo estander
				if (rs.next()) {
					// Cero entra al calculo de costo estander.
					salida = Env.ZERO;
				}
			}

			/**
			 * COSTO ESTANDAR
			 */
			if (salida.compareTo(BigDecimal.ZERO) <= 0) {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;

				sql = " SELECT coalesce(currentcostprice,0) as currentcostprice " +
					  " FROM m_cost " +
					  " WHERE  m_costelement_id = 1000005 " +
					  " AND  m_product_id =" + productID;

				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					salida = (rs.getBigDecimal("currentcostprice")).multiply(cantidad).setScale(9, RoundingMode.UP);
					metodoCosteo.put(productID, "CE");
				} else {
					metodoCosteo.put(productID, "NE");
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return salida;
	}

	private static int getDocFactImpID(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int salida = 0;

		try {
			
			sql = " select c_doctype_id from c_doctype where docbasetype ='AFI' and issotrx='N' ";
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				salida = rs.getInt(1);
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return salida;
	}
}
