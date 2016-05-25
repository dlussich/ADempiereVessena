package org.openup.beans;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;
import java.io.File;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.openup.model.MXLSIssue;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;

/**
 * 
 * OpenUp.
 * AuxWorkCellXLS
 * Descripcion :
 * @author Nicolas Garcia
 * Fecha : 28/03/2011
 */
public class AuxWorkCellXLS {
	Properties ctx;
	int table_ID;
	int record_ID;
	String fileName;
	public Sheet hoja;
	CLogger	log ;
	
	public AuxWorkCellXLS(Properties ct,int tableID,int recordID,String fileNam,Sheet hoj,CLogger _log){
		ctx=ct;
		table_ID=tableID;
		record_ID=recordID;
		fileName=fileNam;
		hoja=hoj;
		log=_log;
	}
	public AuxWorkCellXLS(Properties ct,int tableID,int recordID,String fileNam,CLogger _log){
		ctx=ct;
		table_ID=tableID;
		record_ID=recordID;
		fileName=fileNam;
		hoja=null;
		log=_log;
	}
	
	public  String getStringFromCell( Cell aux)throws Exception{		

			try {				
				if (aux.getContents() != "") {
					
					return aux.getContents();
				} 				
				return null;
			}catch (Exception e) {
				return null;
			}	
	}
	
	public  void addMsg(Cell cell, String msg){
		MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),msg,null);
	}

	public BigDecimal getBigDecimalFromCell(Cell cell) {
		 {

			try {
				return BigDecimal.valueOf(Double.parseDouble(cell.getContents()));
			} catch (Exception e) {
				return null;
			}

		}
	}
	//OpenUp Nicolas Sarlabos #936 16/12/2011
	public Integer getIntFromCell(Cell cell) {
		 {

			try {
				return Integer.valueOf(Integer.parseInt(cell.getContents()));
			} catch (Exception e) {
				return null;
			}

		}
	}
	//fin OpenUp Nicolas Sarlabos #936 16/12/2011

	public static Workbook getReadWorkbook(File file) throws Exception{
		//http://code.google.com/p/shapan/source/browse/trunk/sg_market/src/report/JxlTest.java?r=257
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Latin1");
                
		// Return el workbook
		return Workbook.getWorkbook(file,ws);
		
	}
	
	public static WritableWorkbook getWriteWorkbook(File file) throws Exception{
		//http://code.google.com/p/shapan/source/browse/trunk/sg_market/src/report/JxlTest.java?r=257
		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Latin1");
                
		// Return el workbook
		
		return Workbook.createWorkbook(file,ws);
		
	}
	
	public String validacionXLSInicial()throws Exception {
		
		
		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName,
					"", "", "", "El nombre de la planilla excel esta vacio",
					null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName,
					"", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}


		try {
			
			// Get de workbook
			Workbook workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la priemra hoja
			hoja = workbook.getSheet(0);
			
			//http://code.google.com/p/shapan/source/browse/trunk/sg_market/src/report/JxlTest.java?r=257
			
			// Se vacia la lista de errores
			// MXLSIssue.Delete(ctx, table_ID,record_ID,null);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(ctx,table_ID,record_ID,fileName,"","","","La primer hoja de la planilla Excel no tiene columnas",null);
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
	
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :
	 * @param tableName
	 * @param where
	 * @param buscarPorName (Si es true busca por name)
	 * @return HashMap<String,Integer>
	 * @throws Exception
	 * @author  Nicolas Garcia 
	 * Fecha : 05/04/2011
	 */
	public HashMap<String,Integer> getIdFromValueOrName(String tableName,String where, String claveBusqueda)throws Exception{
		
		//Cargo datos a usar validos para nombre
		HashMap<String,Integer> salida=new HashMap<String,Integer> ();
		where =" WHERE ad_client_id=1000005 "+where; //TODO arreglar el quemado de 1000005
		
		String sql = "SELECT "+tableName+"_id, "+claveBusqueda+" FROM "+tableName+" "+where;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

	
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			while (rs.next()) {
				salida.put(rs.getString(claveBusqueda).toUpperCase().trim(),rs.getInt(tableName+"_id"));
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return salida;
		
	}
	
	public static Timestamp formatDateFromDateCell(Cell clave) {
		Timestamp aux=null;
		 try {
			DateCell dateCell = (DateCell) clave;
			//TODO pongo mas 1 porque no se encontro otra manera rapidamente, deja la fecha un dia atrazada	
			aux = new java.sql.Timestamp (dateCell.getDate().getTime()+86400000);
			
		} catch (Exception e) {
		
			aux= new Timestamp(System.currentTimeMillis());
		}		
		return aux;
	}
	public Timestamp formatDateFromString(String clave) throws Exception {
		String sql="SELECT '"+clave+"'::timestamp AS fecha";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
			try {
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();

				// Si existe resultado
				if (rs.next()) {
					return rs.getTimestamp("fecha");
				}
			} catch (Exception e) {
				log.info(e.getMessage());
				throw new Exception(e);
			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		return null;
		
	}
	
	/***
	 * Devuelve timestamp recibiendo un string como parametro.
	 * OpenUp Ltda. 
	 * @author Nicolas Sarlabos - 16/11/2012
	 * @see
	 * @return
	 */
	
	public Timestamp formatStringDate(String clave) throws Exception {
		
		String sql="SELECT to_timestamp('"+clave+"', 'dd/MM/yyyy') AS fecha";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
			try {
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();

				// Si existe resultado
				if (rs.next()) {
					return rs.getTimestamp("fecha");
				}
			} catch (Exception e) {
				log.info(e.getMessage());
				throw new Exception(e);
			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		return null;
		
	}
	
}
