/**
 * 
 */
package org.openup.process;

import java.io.File;
import java.math.BigDecimal;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.openup.beans.AuxWorkCellXLS;

/**
 * @author Nicolás
 *
 */
public class PLoadConversionRate extends SvrProcess {
	
	//String fileName = "C:\\OpUp_Modelo_Conversion.xls";
	
	String fileName = null;
	Sheet hoja=null;
		
	Workbook workbook=null;
	Integer tope =0;
	AuxWorkCellXLS utiles;

	/**
	 * 
	 */
	public PLoadConversionRate() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("FileName")){
					this.fileName = ((String)para[i].getParameter());
				}
			}
			
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		this.loadData();
		
		return "OK";
	}
	
	private void loadData() throws Exception{

		String s=validacionXLSInicial();

		//Validacion inicial
		if(!(s.equals(""))){
			throw new AdempiereException (s);
		}	
			
		this.readXLS();		

	}
	
	private String validacionXLSInicial(){

		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) return ("No se encontro la planilla Excel");

		try {

			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);

			if (hoja.getColumns() < 1) return ("La primer hoja de la planilla Excel no tiene columnas");


			if (hoja.getRows() < 1) return ("La primer hoja de la planilla Excel no tiene columnas");

		}catch (Exception e) {
			return ("Error al abrir planilla (TRY)");
		}

		return"";
	}
	
	private String readXLS() throws Exception {

		String message = "";
		Cell cell = null;
		utiles = new AuxWorkCellXLS(getCtx(), getTable_ID(), getRecord_ID(),null, hoja, null);
		tope = hoja.getRows();
		MProduct prod = null;
		MUOM uomFrom = null;
		MUOM uomTo = null;
		MUOMConversion conv = null;
		int cantVacias = 0;
	
		for (int recorrido = 3; recorrido < tope; recorrido++) {

			prod = null;
			uomFrom = null;
			uomTo = null;
	
			try{

				//Se lee Codigo de Producto en columna A que es 0***************************
				cell = (hoja.getCell(0, recorrido));
				String codProd=utiles.getStringFromCell(cell);
				int prodID = 0;
				if(codProd!=null && !codProd.equalsIgnoreCase("")){

					codProd = codProd.trim();

					prod = MProduct.forValue(getCtx(), codProd, get_TrxName());//obtengo producto por value

					if(prod!=null && prod.get_ID() > 0){

						prodID = prod.get_ID();						

					} else codProd = null; 

				} else codProd = null;
				//-----------------------------------------------------------------------------

				//Se lee UM Origen en columna B que es 1***************************
				cell = (hoja.getCell(1, recorrido));
				String um1=utiles.getStringFromCell(cell);
				int um1_ID = 0;
				if(um1!=null && !um1.equalsIgnoreCase("")){
					
					um1 = um1.trim();

					uomFrom = MUOM.get(getCtx(), um1, get_TrxName());

					if(uomFrom!=null && uomFrom.get_ID() > 0){

						um1_ID = uomFrom.get_ID();						

					} else um1 = null; 
												
				} else um1 = null;
				//------------------------------------------------------------------------------------------------------------------
				
				//Se lee UM Destino en columna C que es 2***************************
				cell = (hoja.getCell(2, recorrido));
				String um2=utiles.getStringFromCell(cell);
				int um2_ID = 0;
				if(um2!=null && !um2.equalsIgnoreCase("")){
					
					um2 = um2.trim();

					uomTo = MUOM.get(getCtx(), um2, get_TrxName());

					if(uomTo!=null && uomTo.get_ID() > 0){

						um2_ID = uomTo.get_ID();						

					} else um2 = null; 
												
				} else um2 = null;
				//------------------------------------------------------------------------------------------------------------------
				
				//Se lee Tasa Divisora en columna D que es 3***************************
				cell = (hoja.getCell(3, recorrido));
				BigDecimal tasa = Env.ZERO;
				String coef=utiles.getStringFromCell(cell);
				if(coef!=null && !coef.equalsIgnoreCase("")){
					
					try {
						
						coef = coef.replace(",", "");
						tasa = new BigDecimal(coef);
						
					} catch (Exception e){
					
						throw new AdempiereException(e.getMessage());
					}					
				}
				//------------------------------------------------------------------------------------------------------------------			

				if(prodID > 0 && um1_ID > 0 && um2_ID > 0 && tasa.compareTo(Env.ZERO) > 0){

					conv = new MUOMConversion(getCtx(), 0, get_TrxName());
					conv.setM_Product_ID(prodID);
					conv.setC_UOM_ID(um1_ID);
					conv.setC_UOM_To_ID(um2_ID);
					
					BigDecimal rate1 = tasa;
					BigDecimal rate2 = Env.ZERO;
					BigDecimal one = new BigDecimal(1.0);
					
					if (rate1.doubleValue() != 0.0)	//	no divide by zero
						rate2 = one.divide(rate1, 12, BigDecimal.ROUND_HALF_UP);
					
					conv.setMultiplyRate(rate2);
					conv.setDivideRate(tasa);
					
					conv.saveEx();				

				}								

				if(codProd==null) cantVacias ++; //aumento contador de filas vacias

				if(cantVacias == 10) recorrido = tope; //permito un maximo de 10 lineas vacias leidas, superado ese tope salgo del FOR

			} catch (Exception e) {
				//Errores no contemplados
				throw new AdempiereException(e.getMessage());
			}			

		}

		if (workbook!=null){
			workbook.close();
		}

		if(message.equalsIgnoreCase("")) message="Proceso Finalizado OK";

		return message;				

	}

}
