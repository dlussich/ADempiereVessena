package org.openup.process;

import java.io.File;
import java.util.HashMap;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.compiere.process.SvrProcess;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MLoadPOrder_XLS;
import org.openup.model.POrderHdr;
import org.openup.model.POrderLine;
import org.openup.model.POrderLogic;

/**
 * OpenUp.  issue #872
 * PLoadPurchaseOrdersXLS
 * Descripcion :Proceso para la carga de ordenes de compra desde archivo XLS
 * @author Nicolas Sarlabos
 * Fecha : 08/09/2011 
Actualizado Nicolas Garcia. 13/10/2011. Issue #887.

 */
// org.openup.process.PLoadPurchaseOrdersXLS
public class PLoadPurchaseOrdersXLS extends SvrProcess{

	private MLoadPOrder_XLS loadporders_xls = null;
	private int salesRep_ID = 0;
	
	Sheet hoja=null;
	String fileName = null;
	
	Workbook workbook=null;
	int tope =0;
	int linesOK=0;
	int linesMal=0;
	String s="";
	@Override
	protected String doIt() throws Exception {
			
		//Validacion inicial
		s=validacionXLSInicial();
		if(!(s.equals(""))){
			return s;
		}
				
		HashMap<String,POrderHdr> hdr=readXLS();
		// OpenUp. Nicolas Garcia. 12/10/2011. Issue #887.
		s += POrderLogic.run(getCtx(), hdr.values(), salesRep_ID, this.getProcessInfo().getWindow(), log, get_TrxName());
		//Fin Issue #887
		loadporders_xls.set_CustomColumn("Text", s);
		loadporders_xls.save(null);
		return s;
	}

	@Override
	protected void prepare() {

		loadporders_xls = new MLoadPOrder_XLS(getCtx(), getRecord_ID(), null);
		fileName = this.loadporders_xls.getFileName();
		salesRep_ID = loadporders_xls.getSalesRep_ID();
	}
	
	/**
	 * 
	 * OpenUp. issue #872	
	 * Descripcion : Método que realiza la validación inicial del archivo XLS
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 07/09/2011
	 */
	private String validacionXLSInicial(){
		
		
		if ((fileName == null) || (fileName.equals(""))) {
			return ("El nombre de la planilla excel esta vacío");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			return ("No se encontro la planilla Excel");
		}


		try {
			// Open de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la priemra hoja
			hoja = workbook.getSheet(0);
			
			if (hoja.getColumns() < 1) {
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}

			if (hoja.getRows() < 1) {
				return ("La primer hoja de la planilla Excel no tiene filas");
			}
		}catch (Exception e) {
				return ("Error al abrir planilla");
		}
			
		return"";
	}
	
	/**
	 * 
	 * OpenUp. issue #872	
	 * Descripcion : Método que lee el archivo XLS, valida y carga en hashmap
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 07/09/2011
	 */
	private HashMap<String,POrderHdr> readXLS() {
		
		HashMap<String,POrderHdr> lista=new HashMap<String,POrderHdr>();
		//HashMap<String,POrderLine> lineas=new HashMap<String,POrderLine>();
		HashMap<String,String> controlRepetidos=new HashMap<String,String>();
		
		Cell cell = null;
		tope = hoja.getRows();
	
		//TODO ARRANCA EN FILA 3
		for (int recorrido = 2; recorrido < tope; recorrido++) {
			try {				
			
			
			POrderLine linea =new POrderLine();
			POrderHdr head= new POrderHdr();
		
			// 1-Valido Nro Orden ubicado en columna A que es 0
			cell= hoja.getCell(0, recorrido);
			if(!(head.setpoReference(cell))){
				s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + " " + cell.getContents().toString() + " Nro. Orden Vacío -- La línea no se creó \n";
				
			}
			
			// 2-Valido GLN o VALUE ubicado columna B que es 1
			cell = hoja.getCell(1, recorrido);
			if(!(head.setGln(cell))){
				s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + " " + cell.getContents().toString() + " Proveedor Vacío -- La línea no se creó \n";
				
			}
					
			// 3-Valido Fecha de Orden ubicado en columna D que es 3
			cell = hoja.getCell(3, recorrido);
			if(!(head.setFechaOrden(cell))){
				s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + " " + cell.getContents().toString() + " Fecha Orden Vacía -- La línea no se creó \n";
				
			}
			
			// 4-Valido Fecha de Entrega ubicado en columna E que es 4
			cell = hoja.getCell(4, recorrido);
			if(!(head.setFechaEntrega(cell))){
				s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + " " + cell.getContents().toString() + " Fecha Entrega Vacía -- La línea no se creó \n";
				
			}
		
			// 5-Valido Producto ubicado  columna F que es 5
			cell = hoja.getCell(5, recorrido);
			if(!(linea.setValueProducto(cell))){
				s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + " " + cell.getContents().toString() + " Producto Vacío -- La línea no se creó \n";
				
			}
			
			// 6-Valido Cantidad ubicado  columna H que es 7
			cell = hoja.getCell(7, recorrido);
			if(!(linea.setQtyEntered(cell))){
				s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + " " + cell.getContents().toString() + " Cantidad inválida -- La línea no se creó \n";
				
			}
			
			// 7-Valido UOM ubicado  columna I que es 8
			cell = hoja.getCell(8, recorrido);
			if(!(linea.setUnidadMedida(cell))){
				s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + " " + cell.getContents().toString() + " UOM Vacía -- La línea no se creó \n";
				
			}
				
				if (head.validar(linea)) {
				//Si ya esta ingresado un producto para esa orden para ese c_bpartner_location
				//Si existe cabezal
					if(lista.containsKey(head.claveCabezal())){
						head=lista.get(head.claveCabezal());
					}else{
						//Si no se crea y agrega
						lista.put(head.claveCabezal(), head);
					}
					
					// existen productos repetidos
					if(head.getLineas().contains(head.claveLinea(linea))||controlRepetidos.containsKey(head.claveLinea(linea)) ||  
							(head.getLineas().contains(head.claveLinea(linea))&& controlRepetidos.containsKey(head.claveLinea(linea)))){
						//mando mensaje
						s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + cell.getContents().toString() + " Producto Repetido!! No se agregarán productos repetidos \n";
						// Se elimina el producto
						head.remover(linea);
						//se agrega a lista de control si es que ya no esta agregada
						if(!(controlRepetidos.containsKey(head.claveLinea(linea)))){
							controlRepetidos.put(head.claveLinea(linea),"");
						}
						
					}else{
						// se agrega linea
						head.agregarLinea(linea);
					}				
					
			}
			
			} catch (Exception e) {
				//Errores no contemplados
				s+=hoja.getName() + " " + CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()) + cell.getContents().toString() + " Error al leer línea \n";
							
			}
		}
		
		if (workbook!=null){
			workbook.close();
		}
		return lista;
	}
	

}
