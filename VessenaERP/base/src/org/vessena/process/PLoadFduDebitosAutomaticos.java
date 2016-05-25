/**
 * 
 */
package org.openup.process;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.net.ntp.TimeStamp;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MFduAutomaticsDebits;
import org.openup.model.MFduLoadDebits;
import org.openup.model.MXLSIssue;

/**
 * @author gbrust
 *
 */
public class PLoadFduDebitosAutomaticos extends SvrProcess {

	private MFduLoadDebits loadDebits = null;
	String fileName = null;
	Workbook workbook=null;	
	
	
	public PLoadFduDebitosAutomaticos() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void prepare() {

		this.loadDebits = new MFduLoadDebits(getCtx(), this.getRecord_ID(), null);
		fileName = this.loadDebits.getFileName();

	}

	@Override
	protected String doIt() throws Exception {
		//Borro errores anteriores
		deleteOldError();

		String s=validacionXLSInicial();
		//Validacion inicial
		if(!(s.equals(""))){
			return s;
		}
		
		s= this.readXLS();
		if(s.equals("Proceso Terminado OK")){
			this.loadDebits.setProcessed(true);
			this.loadDebits.saveEx();
		}
		return s;
	}
	
	private void deleteOldError()throws Exception{

		String sql = "DELETE FROM uy_xlsissue WHERE record_id IN(SELECT UY_Fdu_LoadDebits_ID FROM UY_Fdu_LoadDebits WHERE createdby="+Env.getAD_User_ID(Env.getCtx())+") " +
				"OR record_id="+getRecord_ID()+" AND createdby="+Env.getAD_User_ID(Env.getCtx());

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}

	private String validacionXLSInicial(){


		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(this.getCtx(),this.getTable_ID(),this.getRecord_ID(), fileName,
					"", "", "", "El nombre de la planilla excel esta vacio",
					null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(this.getCtx(),this.getTable_ID(),this.getRecord_ID(), fileName,
					"", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}


		try {

			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			for (int i = 0; i < this.loadDebits.getsheets(); i++) {
				Sheet hoja = workbook.getSheet(i);
				if (hoja.getColumns() < 1) {
					MXLSIssue.Add(this.getCtx(),this.getTable_ID(),this.getRecord_ID(),fileName,"Tarj.Blindada","","","La primer hoja de la planilla Excel no tiene columnas",null);
					return ("La hoja número " + i + " de la planilla Excel no tiene columnas");
				}
			}				
			
		}catch (Exception e) {
			MXLSIssue.Add(this.getCtx(),this.getTable_ID(),this.getRecord_ID(),fileName,"","","","Error al abrir planilla (TRY) "+e.toString(),null);
			return ("Error al abrir planilla (TRY)");
		}

		return"";
	}

	private String readXLS() throws Exception {	
		
		boolean ok = true;
		for (int i = 0; i < this.loadDebits.getsheets() && ok; i++) {
			Sheet hoja = workbook.getSheet(i);			
			ok = this.readSheet(hoja);
		}
		
		if (workbook!=null){
			workbook.close();
		}
		
		if(ok) return "Proceso Terminado OK";
		
		return "";	
	}
	
	private boolean readSheet(Sheet hoja) throws Exception {
		
		Cell cell = null;
		//instancio clase auxiliar
		AuxWorkCellXLS utiles = new AuxWorkCellXLS(getCtx(), getTable_ID(), getRecord_ID(),null, hoja,null);
		
		for (int i = 1; i < hoja.getRows(); i++) {
			try {

				//Se lee Nro de Cuenta en columna A que es 0***************************
				cell = (hoja.getCell(0, i));
				String cuenta =utiles.getStringFromCell(cell);
				
				//Se lee importe en columna F que es 5***************************
				cell = (hoja.getCell(5, i));				
				BigDecimal importe = Env.ZERO;
				String imp = utiles.getStringFromCell(cell);
				if(imp!=null && !imp.equalsIgnoreCase("")){
					imp = imp.replace("-","");
					imp = replaceChar(imp);
					importe = new BigDecimal(imp);
				}
				
				//Se lee Nro de Comercio en columna G que es 6***************************
				cell = (hoja.getCell(6, i));
				String comercio =utiles.getStringFromCell(cell);
				
				//Se lee Nro de Comercio en columna H que es 7***************************
				cell = (hoja.getCell(7, i));
				String fechaString =utiles.getStringFromCell(cell);
								
				if((cuenta != null && !cuenta.equals("")) && (importe != null)){
															
					MFduAutomaticsDebits debitos = new MFduAutomaticsDebits(getCtx(), 0, get_TrxName());
					debitos.setUY_Fdu_LoadDebits_ID(this.getRecord_ID());
					debitos.setNroComercio(comercio);
					debitos.setNroCta(cuenta);
					debitos.setImporte(importe);
					
					Timestamp date = Timestamp.valueOf(fechaString + " 00:00:00.0");
					debitos.setpresentationdate(date);
					
					debitos.saveEx();					
					
				}
				
			}catch (Exception e){
				MXLSIssue.Add(this.getCtx(),this.getTable_ID(),this.getRecord_ID(),fileName, hoja.getName(),String.valueOf(i) ,"","Error al leer linea",null);
			}
			
		}
		
		return true;
	}
	
	
	private String replaceChar(String cadena) {
		
		if(cadena!=null && !cadena.equalsIgnoreCase("")){
			
			char punto = '.';
			char coma = ',';

			for(int i=cadena.length()-1;i>=0;i--){

				if(cadena.charAt(i)==punto) {
					cadena=cadena.replace(",", "");
					i=-1;

				} else if(cadena.charAt(i)==coma){
					cadena=cadena.replace(",", "+");
					cadena=cadena.replace(".", "");
					cadena=cadena.replace("+", ".");
					i=-1;
				}

			}
		}
			
		return cadena;
	}

	
}
