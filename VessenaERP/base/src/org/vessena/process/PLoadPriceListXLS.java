/**
 * PLoadPriceListXLS.java
 * 14/02/2011
 */
package org.openup.process;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

import org.compiere.model.MProductPrice;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import org.compiere.model.MPriceListVersion;
import org.openup.model.MXLSIssue;


/**
 * OpenUp.
 * PLoadPriceListXLS
 * Descripcion : Carga de lista de precios desde archivos exel.
 * @author Nicolas Garcia
 * Fecha : 14/02/2011
 */
public class PLoadPriceListXLS extends SvrProcess {
	int priceListVersion_id;
	MPriceListVersion priceListVersion; 
	/**
	 * Constructor
	 */
	public PLoadPriceListXLS() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		//Si el objeto existe;
		if (priceListVersion==null) {
			return("No pudo leer el pronostico simple");														// TODO: translate
		}
		
		String fileName=priceListVersion.getFileName();
		
		if ((fileName==null)||(fileName.equals(""))) {
			MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","El nombre de la planilla excel esta vacio", null);
			return("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file=new File(fileName);
		
		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","No se encontro la planilla Excel", null);
			return("No se encontro la planilla Excel");																		
		}
		// Defino el workbook
		Workbook workbook=null;
		
		try{
			// Open de workbook
			workbook = Workbook.getWorkbook(file);
			
			// Abro la priemra hoja
			Sheet hoja = workbook.getSheet(0);
			//Se vacia la lista de errores
			MXLSIssue.Delete(getCtx(), getTable_ID(),getRecord_ID(),null);
			
			if (hoja.getColumns()<1) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas", null);
				return("La primer hoja de la planilla Excel no tiene columnas");							
			}
			
			if (hoja.getRows()<1) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","La primer hoja de la planilla Excel no tiene columnas", null);
				return("La primer hoja de la planilla Excel no tiene columnas");								
			}
			
			if (hoja.getColumns()<9) {
				MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName,"","","","La planilla Excel no tiene columna J", null);
				return("La planilla Excel no tiene columna J");
			}
	
			int tope=hoja.getRows();
			ArrayList<Integer> controlrepetidos=new ArrayList<Integer>();
			for(int j=1;j<tope;j++){
				//               Columnas|,Filas_			
				String value="";
				Cell cell1=hoja.getCell(0,j);
				if(cell1.getContents()!=""){
					value=(cell1.getContents());//value
					BigDecimal priceStd;
					Cell cell2=hoja.getCell(9,j);
					
					if (cell2!=null && (cell2.getType()==CellType.NUMBER)||(cell2.getType()==CellType.NUMBER_FORMULA)){
						//priceStd
						NumberCell numberCell=(NumberCell)cell2;
						priceStd =new BigDecimal(numberCell.getValue());
	
						//Busco si existe producto
						PreparedStatement pstmt=null;
						String sql="SELECT m_product_id FROM M_Product where value=?";
						pstmt=DB.prepareStatement(sql,null);
						pstmt.setString(1,value);
						ResultSet rs = pstmt.executeQuery();
						
						
						if(rs.next()){
							// Existe producto					
							int m_product_id=rs.getInt("m_product_id");
							if (!controlrepetidos.contains(m_product_id )){
								//Cargo datos
								MProductPrice.add(m_product_id, priceListVersion_id, priceStd, getCtx());
								//Cargo controlrepetidos
								controlrepetidos.add(m_product_id );								
							}else{
							//Producto repetido
								MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell1.getColumn(),cell1.getRow()), cell1.getContents(),"Producto repetido en planilla, coordenadas", null);
							}
						}else{
								//producto no existe
							MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell1.getColumn(),cell1.getRow()), cell1.getContents(),"Producto No existe", null);
							}
						//Cierro Coneccion
						DB.close(rs, pstmt);
					}else{
						MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell2.getColumn(),cell2.getRow()), cell2.getContents(),"No tiene precio valido", null);
						}
				}else{
					//Error en celda value
					MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell1.getColumn(),cell1.getRow()), "","Codigo Producto no valido", null);
				}
			}
			
		}
			catch (Exception e) {
				log.info(e.getMessage());
				throw new Exception(e);
			}
			finally {
				// Close the workbook
				if (workbook!=null) {					// Defernsive
					workbook.close();
				}
			}
		return "Proceso Finalizado.";
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		this.priceListVersion_id = getRecord_ID();
		priceListVersion  = new MPriceListVersion (getCtx(), priceListVersion_id, null);
	}

}
