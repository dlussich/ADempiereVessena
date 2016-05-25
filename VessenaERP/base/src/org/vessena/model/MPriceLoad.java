/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerProduct;
import org.compiere.model.MClient;
import org.compiere.model.MDiscountSchema;
import org.compiere.model.MDocType;
import org.compiere.model.MOrg;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MTax;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.model.X_C_BPartner_Product;
import org.compiere.model.X_M_DiscountSchema;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.retail.MRTRetailInterface;

/**
 * @author Nicolas
 *
 */
public class MPriceLoad extends X_UY_PriceLoad implements DocAction{
	
	Sheet hoja=null;
	String fileName = null;
	
	Workbook workbook=null;
	Integer tope =0;
	int linesOK=0;
	int linesMal=0;
	AuxWorkCellXLS utiles;
	Properties ctx;
	Integer table_ID;
	Integer record_ID;	
	CLogger	log ;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1054860200928820899L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
//	private Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);


	/**
	 * @param ctx
	 * @param UY_PriceLoad_ID
	 * @param trxName
	 */
	public MPriceLoad(Properties ctx, int UY_PriceLoad_ID, String trxName) {
		super(ctx, UY_PriceLoad_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPriceLoad(Properties ctx, ResultSet rs, String trxName) {
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
		
		if(newRecord || is_ValueChanged("ValidFrom")){

			Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			if(this.getValidFrom().compareTo(today)<0) throw new AdempiereException("La fecha de vigencia no puede ser menor a la actual");

		}
		
		return true;
	}

	@Override
	public boolean applyIt() {

		try{

			this.deleteOldData();
			this.deleteOldError();
			this.loadData();
			
			if(!this.hayErrores()){
				this.setDocAction(DocAction.ACTION_Complete);
				this.setDocStatus(DocumentEngine.STATUS_Applied);
			} else {
				this.deleteOldData();
				throw new AdempiereException ("Carga finalizada con errores");
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}

		return true;
	}
	
	private void deleteOldData() throws Exception{

		String sql = "DELETE FROM uy_priceloadline WHERE uy_priceload_id = " + this.get_ID();

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	private void deleteOldError()throws Exception{

		String sql = "DELETE FROM uy_xlsissue WHERE ad_table_id = " + X_UY_PriceLoad.Table_ID +
				" AND record_id = " + this.get_ID();

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}
	
	/***
	 * Carga planilla excel.
	 * OpenUp Ltda. Issue #4403
	 * @author Nicolas Sarlabos - 30/06/2015
	 * @throws Exception 
	 * @see
	 */
	private void loadData() throws Exception{
		
		fileName = this.getFileName(); //cargo nombre de archivo xls

		String s=validacionXLSInicial();
		//Validacion inicial
		if(!(s.equals(""))){
			throw new AdempiereException (s);
		}
		
		this.readXLS();
									
	}
	
	private String validacionXLSInicial(){

		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(getCtx(), get_Table_ID(), this.get_ID(), fileName,
					"", "", "", "El nombre de la planilla excel esta vacio",
					null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(getCtx(), get_Table_ID(), this.get_ID(), fileName,
					"", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}

		try {

			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(getCtx(),get_Table_ID(),this.get_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}

			if (hoja.getRows() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}
		}catch (Exception e) {
			MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","Error al abrir planilla (TRY) "+e.toString(),null);
			return ("Error al abrir planilla (TRY)");
		}

		return"";
	}
	
	private String readXLS() throws Exception {
		
		String message = "";
		Cell cell = null;
		tope = hoja.getRows();
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(getCtx(), get_Table_ID(), get_ID(),null, hoja,null);
		MPriceLoadLine line = null;
		int cantVacias = 0;
		MProduct prodUPC = null;
		MProduct prodCode = null;
		MProduct prodFinal = null;
		
		for (int recorrido = 3; recorrido < tope; recorrido++) {
			
			boolean isRepeat = false;
			
			prodUPC = null;
			prodCode = null;
			prodFinal = null;
			
			message = "";	
			
			String codProd = "";
			
			String msgProdVacio = "";
			String prodVacio = null;
			
			//String upcVacio = null;
			String msgUpcVacio = "";
			String upcInvalido = null;
			String msgUpcInvalido = "";
			
			//String codigoProdVacio = null;
			//String msgCodigoProdVacio = "";
			
			String precioVacio = null;
			String msgPrecioVacio = "";
			
			String productoRepetido = null;
			String msgProdRepetido = "";
			//String precioInvalido = null;
			//String msgPrecioInvalido = "";			
			
			try{
				
				//Se lee Codigo de Producto para el Proveedor en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				String codProv=utiles.getStringFromCell(cell);
				if(codProv!=null && !codProv.equalsIgnoreCase("")){
					codProv = codProv.trim();
				} 
				//-----------------------------------------------------------------------------
				
				//Se lee Codigo de Barras en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String upc=utiles.getStringFromCell(cell);
				if(upc!=null && !upc.equalsIgnoreCase("")){
					
					upc = upc.trim();
					
					//intento obtener el producto desde el UPC
					boolean validUPC = this.validateUPC(upc);
					
					if(validUPC){
						
						prodUPC = MProduct.forUPC(getCtx(), upc, get_TrxName());
						
						if(prodUPC!=null){
							
							MPriceLoadLine loadLine = MPriceLoadLine.forHdrAndProduct(getCtx(), this.get_ID(), prodUPC.get_ID(), get_TrxName());
							
							if(loadLine!=null){ //si ya existe una linea procesada para este producto
								
								message="Producto repetido: " + prodUPC.getValue() + " - " + prodUPC.getName();
								productoRepetido = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
								msgProdRepetido = message;
								
								isRepeat = true;		
								upc = null;
								
							}
							
						}					
									
					} else {
						
						message="Codigo de barras asociado a mas de un producto";
						upcInvalido = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
						msgUpcInvalido = message;						
					}					
					
				} else {
					upc=null;
					message="Codigo de barras vacio";
					//upcVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgUpcVacio = message;
				}	
				//-----------------------------------------------------------------------------
				
				//Si no hay UPC se lee Codigo Interno de Producto en columna C que es 2***************************
				if(prodUPC == null){					

					cell = (hoja.getCell(2, recorrido));
					codProd=utiles.getStringFromCell(cell);
					if(codProd!=null && !codProd.equalsIgnoreCase("")){

						codProd = codProd.trim();

						prodCode = MProduct.forValue(getCtx(), codProd, get_TrxName());//obtengo producto por value

					} else {

						codProd=null;

						if(!msgUpcVacio.equalsIgnoreCase("")){

							message="Codigo de barras y codigo interno vacios";
							prodVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgProdVacio = message;						

						} 				

					}	
				}
				//-----------------------------------------------------------------------------
				
				//Se lee Descripcion en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				String desc=utiles.getStringFromCell(cell);
				if(desc!=null && !desc.equalsIgnoreCase("")){
					desc = desc.trim();					
				}				
				//------------------------------------------------------------------------------
				
				//Se lee Precio de Compra en columna E que es 4***************************
				cell = (hoja.getCell(4, recorrido));
				BigDecimal price = Env.ZERO;
				String amt=utiles.getStringFromCell(cell);
				if(amt==null || amt.equalsIgnoreCase("")){
					price=null;
					message="Precio vacio";
					precioVacio = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
					msgPrecioVacio = message;
				
				} else {
					
					try {
						
						amt = amt.replace(",", "");
						price = new BigDecimal(amt);		
						
						/*if(price.compareTo(Env.ZERO)<=0){
							price=null;
							message="Precio debe ser mayor a cero";
							precioInvalido = CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow());
							msgPrecioInvalido = message;							
						}*/
						
					} catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Error al obtener Precio de Compra",null);
						message="Error al obtener Precio de Compra";
						price=null;						
					}					
				}
				//------------------------------------------------------------------------------------------------------------------
				
				//OpenUp. Nicolas Sarlabos. 18/03/2016.
				//Se lee Precio de Venta en columna F que es 5***************************
				cell = (hoja.getCell(5, recorrido));
				BigDecimal priceSO = Env.ZERO;
				String amtSO=utiles.getStringFromCell(cell);
				if(amtSO!=null && !amtSO.equalsIgnoreCase("")){
					
					try {
						
						amtSO = amtSO.replace(",", "");
						priceSO = new BigDecimal(amtSO);
						
					} catch (Exception e){
						MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Error al obtener Precio de Venta",null);
						message="Error al obtener Precio de Venta";
						priceSO=null;						
					}					
				} else priceSO = Env.ZERO;			
				//--------------------------------------------------------------------------------------------------------------------------------------------
				//Fin OpenUp.
				
				if(price!=null && !isRepeat){

					line = new MPriceLoadLine(getCtx(), 0, null);
					line.setUY_PriceLoad_ID(this.get_ID());
					if(upc!=null) line.setUPC(upc);
					line.setPriceList(price);
					line.setCode(codProv);
					
					if(priceSO.compareTo(Env.ZERO) > 0) line.setNewPrice(priceSO);//OpenUp. Nicolas Sarlabos. 18/03/2016.
					
					if(prodUPC!=null && prodCode!=null){
						
						if(prodUPC.get_ID() == prodCode.get_ID()) prodFinal = new MProduct(getCtx(),prodUPC.get_ID(),get_TrxName());						
						
					} else if (prodUPC!=null && prodCode==null){
						
						prodFinal = new MProduct(getCtx(),prodUPC.get_ID(),get_TrxName());					
						
					} else if(prodUPC==null && prodCode!=null) {
						
						prodFinal = new MProduct(getCtx(),prodCode.get_ID(),get_TrxName());
						
					} 
					
					if(prodFinal!=null){
						
						line.setM_Product_ID(prodFinal.get_ID());					
						line.setProdCode(prodFinal.getValue());
						line.setName(prodFinal.getName());
						line.setDescription(prodFinal.getDescription());
						line.setC_UOM_ID(prodFinal.getC_UOM_ID());
						line.setC_TaxCategory_ID(prodFinal.getC_TaxCategory_ID());
						line.setUY_Linea_Negocio_ID(prodFinal.getUY_Linea_Negocio_ID());
						line.setUY_ProductGroup_ID(prodFinal.get_ValueAsInt("UY_ProductGroup_ID"));
						line.setUY_Familia_ID(prodFinal.getUY_Familia_ID());
						line.setUY_SubFamilia_ID(prodFinal.getUY_SubFamilia_ID());
						line.setSuccess(true);
						
						// Obtengo precio de venta de este producto, puede venir en cero
						//MPriceListVersion version = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_2(), null);
						
						int pLVId = MPriceListVersion.forPriceList(getCtx(), this.getM_PriceList_ID_2(), null).get_ID();
						MPriceListVersion version = new MPriceListVersion(getCtx(), pLVId, get_TrxName());						
						
						MProductPrice prodPrice = null;
						if ((version != null) && (version.get_ID() > 0)){
							prodPrice = MProductPrice.forVersionProduct(getCtx(), version.get_ID(), prodFinal.get_ID(), null);
						}
						if ((prodPrice != null) && (prodPrice.get_ID() > 0)){
							line.setPriceSOList(prodPrice.getPriceList());
							BigDecimal margin = Env.ZERO;
							if ((line.getPriceList() != null) && (line.getPriceList().compareTo(Env.ZERO) > 0)){
								margin = ((line.getNewPrice().multiply(Env.ONEHUNDRED).setScale(0, RoundingMode.HALF_UP)).divide(line.getPriceList(), 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
							}
							line.setMargin(margin);
						}
						
						//#5408 E. Bentancor
						PreparedStatement pstmt = null, pstmt2 = null;
						ResultSet rs = null, rs2 = null;
//						MBPartnerProduct bp = this.getPartnerProductLine(
//								this.getC_BPartner_ID(), line.getM_Product_ID());// obtengo linea para proveedor y producto actuales
						
//						if(bp != null){
							//if(this.getRulesByPriceList(this.getM_PriceList_ID()) != null){
							if(this.get_Value("UY_DiscountRule_ID") != null && this.get_ValueAsInt("UY_DiscountRule_ID") > 0){
								MDiscountRule rules = new MDiscountRule(getCtx(), this.get_ValueAsInt("UY_DiscountRule_ID"), get_TrxName());
								rules.loadPOCyPF(line);
							}else{
								line.set_ValueOfColumn("pricepo", line.getPriceList()); // sólo los que aplican a la orden de compra
								line.set_ValueOfColumn("pricecostfinal", line.getPriceList());
							}
						
						
						try{
							String qry = " select round(b.linetotalamt/b.qtyentered, 2) from c_invoice a join c_invoiceLine b " +
											" on a.c_invoice_id = b.c_invoice_id where a.issotrx = 'N' " +
											" and a.docstatus = 'CO' and a.c_bpartner_id = " + this.getC_BPartner_ID() +
											" and b.m_product_id = " + prodFinal.get_ID() +
											" order by a.datevendor desc";
							pstmt = DB.prepareStatement (qry, get_TrxName());
							rs = pstmt.executeQuery ();
							if(rs.next()){
								line.set_ValueOfColumn("PriceInvoiced", rs.getBigDecimal(1));
							}
						
							//E. Bentancor #5693
							String qry2 = "select margin2, margin3, margin4 from uy_priceloadline"
											+ " where m_product_id = " + prodFinal.get_ID()
											+ " order by created desc";
							pstmt2 = DB.prepareStatement (qry2, get_TrxName());
							rs2 = pstmt2.executeQuery ();

							if(rs2.next() && priceSO.compareTo(Env.ZERO)<=0 && rs2.getFloat(2) > 0){
								//Comentado por E. Bentancor #5693
								//line.setNewPrice(prodPrice.getPriceList());//OpenUp. Nicolas Sarlabos. 18/03/2016.
								line.set_ValueOfColumn("Margin3", new BigDecimal(rs2.getFloat(2)).setScale(2, RoundingMode.HALF_UP));
								if(rs2.getFloat(1)>0){
									line.set_ValueOfColumn("Margin2", new BigDecimal(rs2.getFloat(1)).setScale(2, RoundingMode.HALF_UP));
								}
								if(rs2.getFloat(3) > 0){
									line.set_ValueOfColumn("Margin4", new BigDecimal(rs2.getFloat(3)).setScale(2, RoundingMode.HALF_UP));
								}
								if(line.get_Value("PriceCostFinal") != null){
									BigDecimal pcf = new BigDecimal(line.get_ValueAsString("PriceCostFinal"));
									BigDecimal rate = new BigDecimal(1 + (rs2.getFloat(1)/100));
									line.setNewPrice(pcf.multiply(rate).setScale(0, RoundingMode.HALF_UP));
								}
								//Fin #5693
							}else{
								if(line.get_Value("NewPrice") == null){
									line.setNewPrice(line.getPriceSOList());
								}
									
								BigDecimal np = new BigDecimal(line.get_ValueAsString("NewPrice"));
								if(line.get_Value("PricePO") != null){
									BigDecimal po = new BigDecimal(line.get_ValueAsString("PricePO"));
									BigDecimal margin2 = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(po, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
									line.set_ValueOfColumn("Margin2", margin2);
								}
								
								if(line.get_Value("PriceCostFinal") != null){
									BigDecimal pcf = new BigDecimal(line.get_ValueAsString("PriceCostFinal"));
									BigDecimal margin3 = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pcf, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
									line.set_ValueOfColumn("Margin3", margin3);
								}
								
								if(line.get_Value("PriceInvoiced") != null){
									BigDecimal pInv = new BigDecimal(line.get_ValueAsString("PriceInvoiced"));
									BigDecimal margin4 = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pInv, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
									line.set_ValueOfColumn("Margin4", margin4);
								}
								
							}
						}catch (Exception e)
						{
							throw new AdempiereException(e.getMessage());
						}
						finally
						{
							DB.close(rs, pstmt);
							rs = null; pstmt = null;
							DB.close(rs2, pstmt2);
							rs2 = null; pstmt2 = null;
						}
						line.saveEx();
						//FIN #5408 E. Bentancor
					} 
					else {
						
						if(desc!=null && !desc.equalsIgnoreCase("")) {
							line.setName(desc);
							line.setDescription(desc);
						}
						
						line.setC_UOM_ID(100);
						
						//Comentado por E. Bentancor Issue #5689
						/*MTax tax = MTax.forValue(getCtx(), "basico", get_TrxName());
						
						if(tax!=null && tax.get_ID()>0){
							
							line.setC_TaxCategory_ID(tax.getC_TaxCategory_ID());
							
						} else throw new AdempiereException("No se obtuvo tasa de impuesto 'BASICO'");*/
						//Fin Issue #5689
						
						if(price != null){
							if(this.get_Value("UY_DiscountRule_ID") != null && this.get_ValueAsInt("UY_DiscountRule_ID") > 0){
								MDiscountRule rules = new MDiscountRule(getCtx(), this.get_ValueAsInt("UY_DiscountRule_ID"), get_TrxName());
								rules.loadPOCyPF(line);
							}else{
								line.set_ValueOfColumn("pricepo", line.getPriceList()); // sólo los que aplican a la orden de compra
								line.set_ValueOfColumn("pricecostfinal", line.getPriceList());
							}
						}
						
						if(codProd != null) line.setProdCode(codProd);
						
						line.setSuccess(false);		
					}
					
					line.saveEx();
				}	
				
				if(upc != null || codProd != null || price != null){
					
					//si tengo al menos un campo No vacio entonces inserto todos los errores encontrados
					//if(upcVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), upcVacio, "", msgUpcVacio,null);
					//if(codigoProdVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), codigoProdVacio, "", msgCodigoProdVacio,null);
					if(prodVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), prodVacio, "", msgProdVacio,null);
					if(precioVacio!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), precioVacio, "", msgPrecioVacio,null);				
					
					if(upcInvalido!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), upcInvalido, upc, msgUpcInvalido,null);
					
					if(productoRepetido!=null) MXLSIssue.Add(getCtx(),get_Table_ID(),get_ID(),fileName, hoja.getName(), productoRepetido, upc, msgProdRepetido,null);	

				}
				
				if(upc==null && price==null) cantVacias ++; //aumento contador de filas vacias
							
				if(cantVacias == 10) recorrido = tope; //permito un maximo de 10 lineas vacias leidas, superado ese tope salgo del FOR
				
			} catch (Exception e) {
				//Errores no contemplados
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);
			}			
			
		}
		
		if (workbook!=null){
			workbook.close();
		}

		if(message.equalsIgnoreCase("")) message="Proceso Finalizado OK";

		return message;		
		
	}	
	
	/***
	 * Verifica que el UPC no este asociado a mas de un producto.
	 * OpenUp Ltda. Issue #4403
	 * @author Nicolas Sarlabos - 30/06/2015
	 * @see
	 * @return
	 */
	private boolean validateUPC(String upc) {
		
		String sql = "select count(uy_productupc_id)" +
				" from uy_productupc" +
				" where upc = '" + upc + "'";
		int count = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(count > 1) return false;		
		
		return true;
	}

	public boolean hayErrores(){

		String sql = "select uy_xlsissue_id from uy_xlsissue where ad_table_id = " + get_Table_ID() + " and record_id = " + this.get_ID();
		int errors = DB.getSQLValueEx(get_TrxName(), sql);
				
		if(errors > 0) return true;

		return false;	

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
		
		if(this.getLines(null).size() <= 0) throw new AdempiereException("No hay lineas para procesar");
		
		//No permito completar el documento si existen errores
		if(this.hayErrores()) throw new AdempiereException("Imposible completar documento, verifique errores");	
		
		this.processLinesNotSuccess();//proceso lineas de productos NO encontrados
		this.processDoc();//proceso todas las lineas e impacto en lista de precios	
		
		// Proceso actualizacion de lista de precios de venta
		MClient cte = new MClient(getCtx(), this.getAD_Client_ID(), null);
		//SBT 28/01/2016 Issue @5386 Se debe contemplar este tipo de cliente porque opera con mas de una lista de venta
		if(cte.getName().equals("Planeta")){
			this.processSOPriceLists(true);
		}else{
			this.processSOPriceLists(false);//falta testear 28/01/2016 - SBT
			//this.processSOPriceList();
		}
				
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
	 * Procesa lineas del documento para , creando o actualizando listas de precios y productos.
	 * OpenUp Ltda. Issue #4403.
	 * @author Nicolas Sarlabos - 01/07/2015
	 * @see
	 * @return
	 */
	private void processLinesNotSuccess() {
		
		MProduct prod = null;
		MProductUpc prodUpc = null;
		
		try{
			
			List<MPriceLoadLine> lines = this.getLines(" and success = 'N'"); //obtengo lineas de productos NO encontrados
			
			for(MPriceLoadLine line : lines){
				
				if(line.getM_Product_ID()>0){
					
					if(line.getUPC() != null && !line.getUPC().equalsIgnoreCase("")){  //le agrego el codigo de barras al producto seleccionado
						
						prod = (MProduct)line.getM_Product();
						
						//obtengo modelo para UPC
						MProductUpc pu = MProductUpc.forUPC(getCtx(), line.getUPC(), get_TrxName());
						
						if(pu==null){
							
							prodUpc = new MProductUpc(getCtx(), 0, get_TrxName());
							prodUpc.setM_Product_ID(prod.get_ID());
							prodUpc.setUPC(line.getUPC());
							prodUpc.saveEx();						
							
						} else throw new AdempiereException("El codigo de barras '" + line.getUPC() + "' asociado al producto '" + prod.getValue() + "' ya existe en el sistema");						
										
					}				
					
				} else { //se crea nuevo producto
					
					prod = new MProduct(getCtx(),0,get_TrxName());
					
					if (line.getProdCode() != null){
						if (!line.getProdCode().equalsIgnoreCase("")){
							prod.setValue(line.getProdCode());		
						}
					}
					
					prod.setName(line.getName());
					prod.setDescription(line.getDescription());
					prod.setC_UOM_ID(line.getC_UOM_ID());
					prod.setUY_Linea_Negocio_ID(line.getUY_Linea_Negocio_ID());
					prod.set_ValueOfColumn("UY_ProductGroup_ID", line.getUY_ProductGroup_ID());
					prod.setUY_Familia_ID(line.getUY_Familia_ID());
					prod.setUY_SubFamilia_ID(line.getUY_SubFamilia_ID());
					prod.setC_TaxCategory_ID(line.getC_TaxCategory_ID());
					prod.saveEx();
					
					if(line.getUPC() != null && !line.getUPC().equalsIgnoreCase("")){  //le agrego el codigo de barras al producto seleccionado
						
						//obtengo modelo para UPC
						MProductUpc pu = MProductUpc.forUPC(getCtx(), line.getUPC(), get_TrxName());
						
						if(pu==null){
							
							prodUpc = new MProductUpc(getCtx(), 0, get_TrxName());
							prodUpc.setM_Product_ID(prod.get_ID());
							prodUpc.setUPC(line.getUPC());
							prodUpc.saveEx();						
							
						} else throw new AdempiereException("El codigo de barras '" + line.getUPC() + "' asociado al producto '" + prod.getValue() + "' ya existe en el sistema");				
											
					}
					
					//DB.executeUpdateEx("update uy_priceloadline set m_product_id = " + prod.get_ID() + " where uy_priceloadline_id = " + line.get_ID(), get_TrxName());
					line.setM_Product_ID(prod.get_ID());
					line.saveEx();					
				}				
				
			}			
			
		} catch (Exception e) {
			
			throw new AdempiereException(e.getMessage());
		}	
		
	}
	
	/***
	 * Procesa todas las lineas del documento, impactando en listas de precios.
	 * OpenUp Ltda. Issue #4403.
	 * @author Nicolas Sarlabos - 01/07/2015
	 * @see
	 * @return
	 */
	private void processDoc() {

		MPriceList list = null;
		// MPriceListVersion versionActual = null;
		MPriceListVersion versionVigente = null;
		MPriceListVersion newVersion = null;
		int round = 2;
		String sql = "", insert = "";

		Timestamp today = TimeUtil.trunc(
				new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

		Timestamp dateValid = TimeUtil.trunc(this.getValidFrom(),
				TimeUtil.TRUNC_DAY);

		try {

			if (this.getM_PriceList_ID() > 0) { // si se selecciono lista de precios, solo creo nueva version de la misma

				list = (MPriceList) this.getM_PriceList();

				round = list.getPricePrecision();// obtengo precision de la lista de precios

				//obtengo la unica version vigente de la lista seleccionada
				versionVigente = list.getVersionVigente(this.getValidFrom());

				if(versionVigente != null && versionVigente.get_ID()>0){

					// creo nueva version
					newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName());
					newVersion.setM_PriceList_ID(list.get_ID());
					newVersion.setName(dateValid.toString());
					newVersion.setM_DiscountSchema_ID(versionVigente.getM_DiscountSchema_ID());
					newVersion.setValidFrom(dateValid);
					
					if(!dateValid.after(today)){
						newVersion.setIsActive(true);
					} else newVersion.setIsActive(false);
					
					newVersion.saveEx();
					
					// seteo inactiva las versiones anteriores, si corresponde
					if(!dateValid.after(today) && dateValid.compareTo(versionVigente.getValidFrom())>=0){
						DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id <> " + newVersion.get_ID() +
								" and m_pricelist_id = " + newVersion.getM_PriceList_ID() + " and isactive = 'Y'", get_TrxName());
						
						if(newVersion.getM_PriceList().isSOPriceList()){
							String qry = "update C_BPartner_Product b"+
												" set priceSOList = a.pricelist,"+
												" validFrom2 = a.dateaction, "+
												" m_pricelist_version_id_2 = a.m_pricelist_version_id,"+
												" margin = case when (b.priceCostFinal = 0 or b.priceCostFinal is null) " +
												 " then 0 else round((a.pricelist*100/b.priceCostFinal)-100, 2) end"+
											" from m_productprice a"+
											" where b.m_product_id = a.m_product_id "+
											" and a.m_pricelist_version_id = " + newVersion.get_ID();
							DB.executeUpdateEx(qry, get_TrxName());
						}
						
						
					}
						
				} else throw new AdempiereException("Error al obtener ultima version activa para la lista seleccionada");

			} else { // si no se selecciono lista, debo crear lista y version nuevas

				// creo nueva lista segun datos del cabezal
				list = new MPriceList(getCtx(), 0, get_TrxName());
				list.setName(this.getName());
				list.setC_Currency_ID(this.getC_Currency_ID());
				list.setPricePrecision(2);
				list.setIsSOPriceList(false);
				list.setIsTaxIncluded(this.isTaxIncluded());
				list.saveEx();

				//#5647 E. Bentancor
				List<MBPartner> partnerNSons = MBPartner.getSons(getCtx(), get_TrxName(), this.getC_BPartner_ID());
				partnerNSons.add(new MBPartner(getCtx(), this.getC_BPartner_ID(), get_TrxName()));
				
				for(MBPartner partner : partnerNSons){
					//crea nuevo registo en UY_Vendor_PriceList
					MVendorPriceList vpl = new MVendorPriceList(this.getCtx(), 0, this.get_TrxName());
					vpl.setC_BPartner_ID(partner.get_ID());
					vpl.setM_PriceList_ID(list.get_ID());
					vpl.saveEx();
				}
				

				// creo nueva version para la lista
				newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName()); // creo lista
				newVersion.setM_PriceList_ID(list.get_ID());
				newVersion.setName(dateValid.toString());

				MDiscountSchema schema = this.getSchemaForName("Estandar");

				newVersion.setM_DiscountSchema_ID(schema.get_ID());
				newVersion.setValidFrom(dateValid);
				if(!dateValid.after(today)){
					newVersion.setIsActive(true);
				} else newVersion.setIsActive(false);
				
				newVersion.saveEx();
			}

			List<MPriceLoadLine> lines = this.getLines(null); // obtengo todas las lineas de productos

			for (MPriceLoadLine line : lines) { // recorro lineas e inserto en la nueva version

				Timestamp dateAction = today;

				//si tengo version vigente, debo comparar precios para ver si cambio la fecha de ultimo cambio del mismo
				if(versionVigente!=null){ 

					MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), versionVigente.get_ID(), line.getM_Product_ID(), get_TrxName());

					if(pprice!=null && pprice.get_ID()>0){

						if(pprice.getPriceList().compareTo(line.getPriceList())==0)
							dateAction = (Timestamp) pprice.get_Value("DateAction");

					}

				}

				// inserto precio en version de lista
				insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated,"
						+ "updatedby,pricelist,pricestd,pricelimit,dateaction)";

				sql = "select "
						+ newVersion.get_ID()
						+ ","
						+ line.getM_Product_ID()
						+ ","
						+ line.getAD_Client_ID()
						+ ","
						+ line.getAD_Org_ID()
						+ ",'Y',now(),"
						+ Env.getAD_User_ID(getCtx())
						+ ",now(),"
						+ Env.getAD_User_ID(getCtx())
						+ ","
						+ line.getPriceList().setScale(round,
								RoundingMode.HALF_UP)
						+ ","
						+ line.getPriceList().setScale(round,
								RoundingMode.HALF_UP)
						+ ","
						+ line.getPriceList().setScale(round,
								RoundingMode.HALF_UP) + ",'" + dateAction + "'";

				DB.executeUpdateEx(insert + sql, get_TrxName());

				// Si la nueva versiï¿½n es la vigente, impacta en datos del proveedor asociados al producto actual
				//E. Bentancor -- Modificado para que la fecha de vigencia no sea despues que hoy
				if (versionVigente==null || (!TimeUtil.trunc(newVersion.getValidFrom(),TimeUtil.TRUNC_DAY).after(today) && dateValid.compareTo(versionVigente.getValidFrom())>=0)) {// entra en vigencia

					//#5647 E. Bentancor
					List<MBPartner> partnerNSons = MBPartner.getSons(getCtx(), get_TrxName(), this.getC_BPartner_ID());
					partnerNSons.add(new MBPartner(getCtx(), this.getC_BPartner_ID(), get_TrxName()));
					
					for(MBPartner partner : partnerNSons){
						
						MBPartnerProduct bp = this.getPartnerProductLine(
								partner.get_ID(), line.getM_Product_ID());// obtengo linea para proveedor y producto actuales

						if (bp != null) { // lo actualiza

							bp.setVendorProductNo(line.getCode());
							bp.setPrice(line.getPriceList());
							//setea precio final y para OC. Luego se afectan con las reglas que apliquen
							bp.setPricePO(line.getPriceList());
							bp.setPriceCostFinal(line.getPriceList());
							bp.setM_PriceList_Version_ID(newVersion.get_ID());
							bp.setC_Currency_ID(this.getC_Currency_ID());
							bp.setValidFrom(dateValid);
							bp.saveEx();

						} else { // lo crea

							bp = new MBPartnerProduct(getCtx(), 0, get_TrxName());
							bp.setC_BPartner_ID(partner.get_ID());
							bp.setM_Product_ID(line.getM_Product_ID());
							bp.setVendorProductNo(line.getCode());
							bp.setPrice(line.getPriceList());
							//setea precio final y para OC. Luego se afectan con las reglas que apliquen
							bp.setPricePO(line.getPriceList());
							bp.setPriceCostFinal(line.getPriceList());
							bp.setM_PriceList_Version_ID(newVersion.get_ID());
							bp.setC_Currency_ID(this.getC_Currency_ID());
							bp.setValidFrom(dateValid);
							bp.saveEx();
						}
						//TODO: CHECK
						//Impacta las Condiciones de Negocio que aplican a esta lista
						//MDiscountRule rules = this.getRulesByPriceList(this.getM_PriceList_ID());
						if(this.get_Value("UY_DiscountRule_ID") != null && this.get_ValueAsInt("UY_DiscountRule_ID") > 0){
							MDiscountRule rules = new MDiscountRule(getCtx(), this.get_ValueAsInt("UY_DiscountRule_ID"), get_TrxName());
							rules.impactarSobreVersionVigente(bp.getC_BPartner_ID(), newVersion.getM_PriceList_ID(), today, rules.get_ID());
						}
					}
				}
				
				// Impacto precio de venta de esta linea en caso de haber sido modificado
				
			}

			// asocia la nueva lista al proveedor
			if (this.getM_PriceList_ID() <= 0)
				DB.executeUpdateEx(
						"update c_bpartner set po_pricelist_id = "
								+ list.get_ID() + " where c_bpartner_id = "
								+ this.getC_BPartner_ID(), get_TrxName());

		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());
		}

	}

	/***
	 * Retorna modelo de esquema segun nombre recibido.
	 * OpenUp Ltda. Issue #4403.
	 * @author Nicolas Sarlabos - 01/07/2015
	 * @see
	 * @return
	 */
	private MDiscountSchema getSchemaForName(String name) {
		
		String whereClause = X_M_DiscountSchema.COLUMNNAME_Name + "='" + name + "'";
		
		MDiscountSchema sch = new Query(getCtx(), I_M_DiscountSchema.Table_Name, whereClause, get_TrxName())
		.setClient_ID()
		.first();	
		
		return sch;
	}
	
	/***
	 * Retorna modelo de linea de proveedor asociado al producto, segun proveedor y producto recibidos.
	 * OpenUp Ltda. Issue #4403.
	 * @author Nicolas Sarlabos - 02/07/2015
	 * @see
	 * @return
	 */
	private MBPartnerProduct getPartnerProductLine(int partnerID, int productID) {
		
		String whereClause = X_C_BPartner_Product.COLUMNNAME_C_BPartner_ID + "=" + partnerID + " and " + 
				X_C_BPartner_Product.COLUMNNAME_M_Product_ID + "=" + productID;
		
		MBPartnerProduct model = new Query(getCtx(), I_C_BPartner_Product.Table_Name, whereClause, get_TrxName())
		.setClient_ID()
		.first();	
		
		return model;
	}

	/***
	 * Retorna el total de lineas procesadas en este documento.
	 * OpenUp Ltda. Issue #4403.
	 * @author Nicolas Sarlabos - 01/07/2015
	 * @see
	 * @return
	 */
	public List<MPriceLoadLine> getLines(String where){
		
		String whereClause = X_UY_PriceLoadLine.COLUMNNAME_UY_PriceLoad_ID + "=" + this.get_ID();
		
		if(where != null && !where.equalsIgnoreCase("")) whereClause = whereClause + where; 
		
		List<MPriceLoadLine> lines = new Query(getCtx(), I_UY_PriceLoadLine.Table_Name, whereClause, get_TrxName()).list();
		
		return lines;
		
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
		// TODO Auto-generated method stub
		return false;
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
	
	
	/***
	 * Retorna las condiciones de negocio que aplican a la lista de precios que recibe por parï¿½metro.
	 * OpenUp Ltda. Issue #4527.
	 * @author INes Fernandez - 16/07/2015
	 * @see
	 * @return
	 */
	public MDiscountRule getRulesByPriceList(int priceList_id){
		MDiscountRule dr = null;
		String sql = "SELECT UY_DiscountRule_id FROM UY_DiscountRule"
				+ " WHERE UY_DiscountRule.IsActive = 'Y'"
				+ " AND UY_DiscountRule.M_PriceList_ID = " + priceList_id;
				
		int rulesID = DB.getSQLValueEx(get_TrxName(), sql);
		if (rulesID>0) dr = new MDiscountRule(getCtx(), rulesID, get_TrxName());
		return dr;
		
	}

	/***
	 * Obtiene y retorna lineas que hayan sufrido un cambio en el precio de venta
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 19, 2015
	 * @return
	 */
	public List<MPriceLoadLine> getLinesDifference(){

		String whereClause = X_UY_PriceLoadLine.COLUMNNAME_UY_PriceLoad_ID + "=" + this.get_ID() + 
				" and " + X_UY_PriceLoadLine.COLUMNNAME_DifferenceAmt + " <> 0";

		List<MPriceLoadLine> lines = new Query(getCtx(), I_UY_PriceLoadLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas que hayan sufrido un cambio en el precio de venta y
	 *  ademas esten seleccionadas para precio dif
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 19, 2015
	 * @return
	 */
	public List<MPriceLoadLine> getLinesDifferenceChecked(){

		String whereClause = X_UY_PriceLoadLine.COLUMNNAME_UY_PriceLoad_ID + "=" + this.get_ID() + 
				" AND " + X_UY_PriceLoadLine.COLUMNNAME_DifferenceAmt + " <> 0"
				+ " AND "+ X_UY_PriceLoadLine.COLUMNNAME_IsSelected +" = 'Y' ";

		List<MPriceLoadLine> lines = new Query(getCtx(), I_UY_PriceLoadLine.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * En caso de haber modificaciones en los precios de venta de productos, debo generar nueva version
	 * de lista de precios de venta.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 19, 2015
	 */
//	private void processSOPriceList() {
//		
//		MPriceListVersion oldVersion = null, newVersion = null;
//		String sql = "", insert = "";
//		
//		try{
//			
//			// Obtengo lineas con diferencia de precio en lista destino
//			List<MPriceLoadLine> lines = this.getLinesDifference();
//			
//			// Si no tengo lineas que hayan sufrido cambios no haga nada
//			if (lines.size() <= 0) return;
//			
//			MPriceList lista = new MPriceList(getCtx(), this.getM_PriceList_ID_2(), null);
//			
//			Date deliveryDate = (Date)this.getDateTrx();
//			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
//			String nombreVersion = lista.getName() + "_" + sdf.format(deliveryDate);
//			
//			int pLVId = MPriceListVersion.forPriceList(getCtx(), this.getM_PriceList_ID_2(), null).get_ID();
//			oldVersion = new MPriceListVersion(getCtx(), pLVId, get_TrxName());
//			
//			//oldVersion = new MPriceListVersion(getCtx(),this.getM_PriceList_Version_ID_2(), get_TrxName()); 
//			
//			newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName()); //creo nueva version de lista
//			newVersion.setM_PriceList_ID(this.getM_PriceList_ID_2());
//			newVersion.setName(nombreVersion);
//			newVersion.setM_DiscountSchema_ID(oldVersion.getM_DiscountSchema_ID());
//			newVersion.setValidFrom(this.getValidFrom());
//			newVersion.setIsActive(true);
//			newVersion.saveEx();				
//
//			//clono la version
//			insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
//					"updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";
//
//			sql = "select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + 
//					", pricelist, pricestd, pricelimit, uy_factor, uy_pricelistunidad, dateaction" +
//					" from m_productprice" +
//					" where m_pricelist_version_id = " + oldVersion.get_ID();
//
//			DB.executeUpdateEx(insert + sql, get_TrxName());			
//
//			boolean processOK = false;
//			
//			for(MPriceLoadLine line : lines){
//
//				String active = "Y";
//				if(!line.isActive()) active = "N";
//				
//				if(line.getDifferenceAmt().compareTo(Env.ZERO)!=0) {
//
//					processOK = true;
//					
//					//verifico si existe el producto en la lista
//					MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), newVersion.get_ID(), line.getM_Product_ID(), get_TrxName());
//					
//					if (pprice != null){ //si el producto existe lo actualizo 
//						
//						sql = "update m_productprice set isactive = '" + active + "',pricelist = " + line.getNewPrice() + ",pricestd = " + line.getNewPrice() +
//								",pricelimit = " + line.getNewPrice() + ",dateaction = '" + this.getValidFrom() + "'" +
//								" where m_pricelist_version_id = " + newVersion.get_ID() + " and m_product_id = " + line.getM_Product_ID();
//						
//						DB.executeUpdateEx(sql, get_TrxName());							
//						
//					} 
//					else { //si el producto no existe, creo nuevo registro
//						
//						insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
//								"updatedby,pricelist,pricestd,pricelimit,dateaction)";
//
//						sql = "select " + newVersion.get_ID() + "," + line.getM_Product_ID() + "," + line.getAD_Client_ID() + "," + line.getAD_Org_ID() + ",'" + 
//						       active + "',now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + "," + line.getNewPrice() + 
//						       "," + line.getNewPrice() + "," + line.getNewPrice() + ",'" + this.getValidFrom() + "'";					
//
//						DB.executeUpdateEx(insert + sql, get_TrxName());							
//						
//					}
//					
//					MProduct prod = (MProduct)line.getM_Product();
//					
//					// Impacto en tabla de interfase
//					MRTInterfaceProd.insertInterface(prod, this.getC_Currency_ID(), line.getNewPrice(), getCtx(), get_TrxName());	
//					
//					// Marco precio cambiado en producto para impresion de etiquetas de precio
//					MProductPrintLabel plabel = MProductPrintLabel.forProduct(getCtx(), prod.get_ID(), get_TrxName());
//					if ((plabel == null) || (plabel.get_ID() <= 0)){
//						plabel = new MProductPrintLabel(getCtx(), 0, get_TrxName());
//						plabel.setM_Product_ID(prod.get_ID());
//						plabel.saveEx();
//					}
//				}
//			}			
//			
//			if (processOK){
//				Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
//				Timestamp validFrom = TimeUtil.trunc(this.getValidFrom(), TimeUtil.TRUNC_DAY);
//				
//				// Si la fecha de vigencia es HOY entonces desactivo las versiones anteriores
//				if(validFrom.compareTo(today)==0) 
//					DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id <> " + newVersion.get_ID() +
//							" and m_pricelist_id = " + newVersion.getM_PriceList_ID()  + " and validfrom <= '" + today + "'", get_TrxName());
//			}
//		
//		}
//		catch (Exception e)
//		{
//			throw new AdempiereException(e.getMessage());
//		}
//
//	}

	/**En caso de haber modificaciones en los precios de venta de productos, debo generar nueva version y actualizar
	 * 
	 * Por cada producto (encontrado o no encontrado), se actualizará la lista de venta seleccionada en el cabezal, 
	 * Excepto cuando se chequkea la columna IsSelected
	 * Si para un producto NO se checkea la nueva columna IsSelected, entonces se actualizan todas las listas de venta
	 *  por sucursal con el mismo nuevo precio de venta.
	 * Si para un producto SI se checkea la nueva columna IsSelected, entonces se actualizan solo las organizaciones 
	 * indicadas en la nueva tabla para ese producto
	 * En este caso, si se desea actualizar en la lista de venta seleccionada en el cabezal también se debe seleccionar.
	 * 
	 * @author OpenUp SBT Issue #5386  28/1/2016 15:31:22
	 */
	private void processSOPriceLists(boolean multiOrg) {	
		try{
			
			// Obtengo lineas con diferencia de precio en lista destino
			List<MPriceLoadLine> lines = this.getLinesDifference();
			if(multiOrg){
				// Obtemgo lines con diferencia de precio que están seleccionadas con precio diferencial
				List<MPriceLoadLine> linesCheck = this.getLinesDifferenceChecked();
				// Si no tengo lineas que hayan sufrido cambios no haga nada
				if (lines.size() <= 0 && linesCheck.size() <= 0) return;
				
				int[] sucursales = null;
				//si hay diferencia entonces tengo linesa sin chequear corresponde actualizar en 
				//cada lista de precio de venta de cada sucursal activa
				if(lines.size()>linesCheck.size()){
					sucursales = MPriceLoadLineOrg.getOrgs(this.get_ID(),get_TrxName());
				}else{
				//si es la misma cantidad entonces tengo que obtener las org que participan 
				//para no crear versiones de lista que no corresponden	
					sucursales = MPriceLoadLineOrg.getOrgsLoad(this.get_ID(),get_TrxName());
				}
				
				//Recorro las sucursales y proceso
				if(null!=sucursales){
					for(int i = 0;i<sucursales.length;i++){
						procesarPorSucursal(sucursales[i],lines,multiOrg);
					}
				}
			}else{
				procesarPorSucursal(0,lines,multiOrg);
			}
					
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}

	}

	/**
	 * 
	 * @author OpenUp SBT Issue#  28/1/2016 18:20:56
	 * @param m_OrgID
	 * @param lines
	 * @param multiOrg
	 */
	private void procesarPorSucursal(int m_OrgID, List<MPriceLoadLine> lines, boolean multiOrg ) {
		MPriceListVersion oldVersion = null, 
		newVersion = null;
		String sql = "", insert = "";
		MPriceList lista = null;
		if(multiOrg){
			 lista = MPriceList.getPricListForOrg(getCtx(),m_OrgID,this.getC_Currency_ID(),null);
			 MOrg org = new MOrg(getCtx(), m_OrgID, null);
				if(0>=lista.get_ID()) 
					throw new AdempiereException("No existe lista de venta para la organización "+ org.getName());
		}else{
			 lista = new MPriceList(getCtx(), this.getM_PriceList_ID_2(), null);
			 
		}

		Date deliveryDate = (Date)this.getDateTrx();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
		String nombreVersion = lista.getName() + "_" + sdf.format(deliveryDate);
		
		int pLVId = MPriceListVersion.forPriceList(getCtx(),lista.get_ID(), null).get_ID();
		
		oldVersion = new MPriceListVersion(getCtx(), pLVId, get_TrxName());
				
		newVersion = new MPriceListVersion(getCtx(), 0, get_TrxName()); //creo nueva version de lista
		newVersion.setM_PriceList_ID(lista.get_ID());
		newVersion.setName(nombreVersion);
		newVersion.setM_DiscountSchema_ID(oldVersion.getM_DiscountSchema_ID());
		newVersion.setValidFrom(this.getValidFrom());
		newVersion.setIsActive(true);
		newVersion.saveEx();				

		//clono la version
		insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
				"updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";

		sql = "select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + 
				", pricelist, pricestd, pricelimit, uy_factor, uy_pricelistunidad, dateaction" +
				" from m_productprice" +
				" where m_pricelist_version_id = " + oldVersion.get_ID();

		DB.executeUpdateEx(insert + sql, get_TrxName());			

		boolean processOK = false;
		
		for(MPriceLoadLine line : lines){

			//Controlo para los casos en que es multi organización si corresponde agregar o no
			if(multiOrg && line.isSelected()){
				MPriceLoadLineOrg lineOrg = MPriceLoadLineOrg.forLoadLineAndOrg(getCtx(),line.get_ID(),m_OrgID,get_TrxName());
				if (null==lineOrg) continue;
			}
			
			String active = "Y";
			if(!line.isActive()) active = "N";
			
			if(line.getDifferenceAmt().compareTo(Env.ZERO)!=0) {

				processOK = true;
				
				//verifico si existe el producto en la lista
				MProductPrice pprice = MProductPrice.forVersionProduct(getCtx(), newVersion.get_ID(), line.getM_Product_ID(), get_TrxName());
				
				if (pprice != null){ //si el producto existe lo actualizo 
					
					sql = "update m_productprice set isactive = '" + active + "',pricelist = " + line.getNewPrice() + ",pricestd = " + line.getNewPrice() +
							",pricelimit = " + line.getNewPrice() + ",dateaction = '" + this.getValidFrom() + "'" +
							" where m_pricelist_version_id = " + newVersion.get_ID() + " and m_product_id = " + line.getM_Product_ID();
					
					DB.executeUpdateEx(sql, get_TrxName());							
					
				} 
				else { //si el producto no existe, creo nuevo registro
					
					insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
							"updatedby,pricelist,pricestd,pricelimit,dateaction)";

					sql = "select " + newVersion.get_ID() + "," + line.getM_Product_ID() + "," + line.getAD_Client_ID() + "," + line.getAD_Org_ID() + ",'" + 
					       active + "',now()," + Env.getAD_User_ID(getCtx()) + ",now()," + Env.getAD_User_ID(getCtx()) + "," + line.getNewPrice() + 
					       "," + line.getNewPrice() + "," + line.getNewPrice() + ",'" + this.getValidFrom() + "'";					

					DB.executeUpdateEx(insert + sql, get_TrxName());							
					
				}
				
				MProduct prod = (MProduct)line.getM_Product();
				
				// Impacto en tabla de interfase
				MRTInterfaceProd.insertInterface(prod, this.getC_Currency_ID(), line.getNewPrice(), getCtx(), get_TrxName());	
				
				// Marco precio cambiado en producto para impresion de etiquetas de precio
				MProductPrintLabel plabel = MProductPrintLabel.forProduct(getCtx(), prod.get_ID(), get_TrxName());
				if ((plabel == null) || (plabel.get_ID() <= 0)){
					plabel = new MProductPrintLabel(getCtx(), 0, get_TrxName());
					plabel.set_ValueOfColumn("C_Currency_ID", this.getC_Currency_ID());//SBT 08/04/2016 Issue #5733
					plabel.setM_Product_ID(prod.get_ID());
					plabel.saveEx();
				}
				//Impacto si corresponde en tabla de interfaceo para Balanzas - SBT 18-02-2016 Issue #5351
				if(prod.get_Value("UsaBalanza")!=null){
					if(prod.get_ValueAsBoolean("UsaBalanza")){
						//OpenUp SBT 04/04/2016 se evia el codigo interno ya que es el unico que corresponde enviar a las balanzas
						//MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), "0", prod.get_ID(), get_TrxName());
						MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), prod.getValue(), prod.get_ID(), get_TrxName());
					}	
				}
				
			}
		}			
		
		if (processOK){
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			Timestamp validFrom = TimeUtil.trunc(this.getValidFrom(), TimeUtil.TRUNC_DAY);
			
			// Si la fecha de vigencia es HOY entonces desactivo las versiones anteriores
			if(!validFrom.after(today) && validFrom.compareTo(oldVersion.getValidFrom())>=0){
				DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id <> " + newVersion.get_ID() +
						" and m_pricelist_id = " + newVersion.getM_PriceList_ID()  + " and isactive = 'Y'", get_TrxName());
				
				if(newVersion.getM_PriceList().isSOPriceList()){
					String qry = "update C_BPartner_Product b"+
										" set priceSOList = a.pricelist,"+
										" validFrom2 = a.dateaction, "+
										" m_pricelist_version_id_2 = a.m_pricelist_version_id,"+
										" margin = case when (b.priceCostFinal = 0 or b.priceCostFinal is null) " +
										 " then 0 else round((a.pricelist*100/b.priceCostFinal)-100, 2) end"+
									" from m_productprice a"+
									" where b.m_product_id = a.m_product_id "+
									" and a.m_pricelist_version_id = " + newVersion.get_ID();
					DB.executeUpdateEx(qry, get_TrxName());
				}
			}		
		}
		
	}
	
	
}
