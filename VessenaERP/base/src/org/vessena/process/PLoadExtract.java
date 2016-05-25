package org.openup.process;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MBankExtract;
import org.openup.model.MLoadExtract;
import org.openup.model.MXLSIssue;

public class PLoadExtract extends SvrProcess {
	
	private MLoadExtract loadExtract = null;
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
	boolean errorRepetidos;
	MLoadExtract  windows;



	@Override
	protected void prepare() {

		loadExtract = new MLoadExtract(getCtx(), getRecord_ID(), null);
		fileName = this.loadExtract.getFileName();

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

		s=this.readXLS();
		if(s.equals("Proceso Terminado OK")){
			loadExtract.setProcessed(true);
			loadExtract.saveEx();
		}
		return s;
	}

	private String validacionXLSInicial(){


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
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);

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

	private void deleteOldError()throws Exception{

		//String sql = "DELETE FROM  uy_xlsissue WHERE record_id="+ loadorders_xls.getUY_LoadOrders_XLS_ID();
		//Se borran todo lo del usuario para ese tipo de carga al procesar

		String sql = "DELETE FROM uy_xlsissue WHERE record_id IN(SELECT uy_loadextract_id FROM uy_loadextract WHERE createdby="+Env.getAD_User_ID(Env.getCtx())+") " +
				"OR record_id="+getRecord_ID()+" AND createdby="+Env.getAD_User_ID(Env.getCtx());

		try {
			DB.executeUpdate(sql, null);

		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
	}

	private String readXLS() throws Exception {

		Cell cell = null;
		tope = hoja.getRows();
		String trxAux=null;
		Trx trans = null;
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(getCtx(), getTable_ID(), getRecord_ID(),null, hoja,null);
		MBankExtract ext = null;
		MBank bank = null;
		//MBank bank = getBank(loadExtract.getC_BankAccount_ID());
		MBankAccount account = new MBankAccount (getCtx(),loadExtract.getC_BankAccount_ID(),get_TrxName());
		if (account!=null) bank = account.getBankFromAccount(account.get_ID());
		int file = bank.getFirstRowExtract(); //obtengo el numero de fila inicial del xls
		int docID = 0;
		boolean success = true;
		String warnings = "";
	
		if(file <= 0) throw new AdempiereException("El numero de primer fila de archivo para el banco " + bank.getName() + " es menor o igual a cero");
		
		if(bank.getValue()==null || bank.getValue()=="") throw new AdempiereException("El banco " + bank.getName() + " no tiene clave de busqueda asociada");
										
		//si es el banco BROU
		if(bank.getValue().equalsIgnoreCase("brou_bank")){
			
			for (int recorrido = (file-1); recorrido < tope; recorrido++) {
				
				success = true;
				warnings = "";
				
				try {

					//Se lee Fecha en columna B que es 1***************************
					cell = (hoja.getCell(1, recorrido));
					Timestamp fecha = null;
					String fch=utiles.getStringFromCell(cell);
					if(fch!=null){
						try{
								fecha=utiles.formatStringDate(fch);
						}catch (Exception e){
							fecha=null;
						}
					}
					//---------------------------------------------------------------

					//Se lee Descripcion en columna C que es 2***************************
					cell = (hoja.getCell(2, recorrido));
					String description=utiles.getStringFromCell(cell);
					//-----------------------------------------------------------------------------

					//Se lee Numero de Documento en columna D que es 3***************************
					cell = (hoja.getCell(3, recorrido));
					String documento=utiles.getStringFromCell(cell);
					if(documento==null){
						documento="0";
						
					}//-----------------------------------------------------------------------------
					
					//Se lee Numero de Sucursal en columna E que es 4***************************
					cell = (hoja.getCell(4, recorrido));
					String sucursal=utiles.getStringFromCell(cell);
					if(sucursal!=null){
						
						if(sucursal.equalsIgnoreCase("")) MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. sucursal vacio",null);
						
					}else MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. sucursal vacio",null);
					//-----------------------------------------------------------------------------
					
					//Se lee Debito en columna F que es 5***************************
					cell = (hoja.getCell(5, recorrido));
					BigDecimal debito = Env.ZERO;
					String d=utiles.getStringFromCell(cell);
					if(d!=null && !d.equalsIgnoreCase("")){
						d=d.replace("-","");
						d=replaceChar(d);
						debito = new BigDecimal(d);
					}
					
					//Se lee Credito en columna G que es 6***************************
					cell = (hoja.getCell(6, recorrido));
					BigDecimal credito = Env.ZERO;
					String c=utiles.getStringFromCell(cell);
					if(c!=null && !c.equalsIgnoreCase("")){
						c=replaceChar(c);
						credito = new BigDecimal(c);
					}
					//-----------------------------------------------------------------------------
					
					if (fecha==null){
						warnings += "Fecha vacia";
						success = false;
					}
					
					if (description==null){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Descripcion vacia";	
						}else warnings += "-Descripcion vacia";
						
						success = false;
					}
						
					if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Debito y Credito iguales a 0";
						} else warnings += "-Debito y Credito iguales a 0";
						
						success = false;
					}		
					
					if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Debito y Credito mayores a 0";
						} else warnings += "-Debito y Credito mayores a 0";
						
						success = false;
					}
						
							trxAux=Trx.createTrxName();
							trans = Trx.get(trxAux, true);
							
							ext=new MBankExtract(getCtx(),0, trxAux);
							ext.setDateTrx(fecha);
							ext.setDescription(description);
							ext.setDocumentNo(documento);
							ext.setAmtSourceCr(credito);
							ext.setAmtSourceDr(debito);
							ext.setC_Bank_ID(bank.get_ID());
							ext.setC_BankAccount_ID(loadExtract.getC_BankAccount_ID());
							ext.setC_Currency_ID(account.getC_Currency_ID());
							ext.setUY_LoadExtract_ID(loadExtract.get_ID());
							ext.setsucursal(sucursal);
							ext.setobservaciones(warnings);
							ext.setSuccess(success);
							
							docID = getDocType(bank,description);
							if(docID>0) ext.setC_DocType_ID(docID);
							
							ext.saveEx(trxAux);
					
				} catch (Exception e) {
					//Errores no contemplados
					trans.rollback();
					trans.close();
					MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

				}
				if(trans!=null){
					trans.close();
				}
			}//Fin for			
			
			
		} else if(bank.getValue().equalsIgnoreCase("nbc_bank")){ //si es el banco NBC
			
			for (int recorrido = (file-1); recorrido < tope; recorrido++) {
				
				success = true;
				warnings = "";
				
				try {

					//Se lee Numero de Sucursal en columna A que es 0***************************
					cell = (hoja.getCell(0, recorrido));
					String sucursal=utiles.getStringFromCell(cell);
					if(sucursal==null || sucursal.equalsIgnoreCase("")){
						MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Nro. sucursal vacio",null);
						
					}//-----------------------------------------------------------------------------
					
					//Se lee Fecha en columna B que es 1***************************
					cell = (hoja.getCell(1, recorrido));
					Timestamp fecha = null;
					String fch=utiles.getStringFromCell(cell);
					if(fch!=null){
						try{
								fecha=utiles.formatStringDate(fch);
						}catch (Exception e){
							fecha=null;
						}
					}
					//---------------------------------------------------------------
					
					//Se lee Fecha Valor en columna C que es 2***************************
					cell = (hoja.getCell(2, recorrido));
					Timestamp fechaV = null;
					String fchV=utiles.getStringFromCell(cell);
					if(fchV!=null){
						try{
								fechaV=utiles.formatStringDate(fchV);
						}catch (Exception e){
							fechaV=null;
						}
					} else { 
						
						MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha Valor vacia",null);
						
					}
					//---------------------------------------------------------------

					//Se lee Descripcion en columna D que es 3***************************
					cell = (hoja.getCell(3, recorrido));
					String description=utiles.getStringFromCell(cell);
					//-----------------------------------------------------------------------------

					//Se lee Numero de Documento en columna E que es 4***************************
					cell = (hoja.getCell(4, recorrido));
					String documento=utiles.getStringFromCell(cell);
					if(documento==null){
						documento="0";
					}//-----------------------------------------------------------------------------

					//Se lee Debito en columna F que es 5***************************
					cell = (hoja.getCell(5, recorrido));
					BigDecimal debito = Env.ZERO;
					String d=utiles.getStringFromCell(cell);
					if(d!=null && !d.equalsIgnoreCase("")){
						d=d.replace("-","");
						d=replaceChar(d);
						debito = new BigDecimal(d);
					}
					
					//Se lee Credito en columna G que es 6***************************
					cell = (hoja.getCell(6, recorrido));
					BigDecimal credito = Env.ZERO;
					String c=utiles.getStringFromCell(cell);
					if(c!=null && !c.equalsIgnoreCase("")){
						c=replaceChar(c);
						credito = new BigDecimal(c);
					}
					//-----------------------------------------------------------------------------
				
					if (fecha==null){
						warnings += "Fecha vacia";
						success = false;
					}
					
					if (description==null){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Descripcion vacia";	
						}else warnings += "-Descripcion vacia";
						
						success = false;
					}
						
					if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Debito y Credito iguales a 0";
						} else warnings += "-Debito y Credito iguales a 0";
						
						success = false;
					}		
					
					if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Debito y Credito mayores a 0";
						} else warnings += "-Debito y Credito mayores a 0";
						
						success = false;
					}				
					

							trxAux=Trx.createTrxName();
							trans = Trx.get(trxAux, true);

							ext=new MBankExtract(getCtx(),0, trxAux);
							ext.setsucursal(sucursal);
							ext.setDateTrx(fecha);
							ext.setDateValue(fechaV);
							ext.setDescription(description);
							ext.setDocumentNo(documento);
							ext.setAmtSourceCr(credito);
							ext.setAmtSourceDr(debito);
							ext.setC_Bank_ID(bank.get_ID());
							ext.setC_BankAccount_ID(loadExtract.getC_BankAccount_ID());
							ext.setC_Currency_ID(account.getC_Currency_ID());
							ext.setUY_LoadExtract_ID(loadExtract.get_ID());
							ext.setobservaciones(warnings);
							ext.setSuccess(success);
							
							docID = getDocType(bank,description);
							if(docID>0) ext.setC_DocType_ID(docID);
							
							ext.saveEx(trxAux);

						
					
				} catch (Exception e) {
					//Errores no contemplados
					trans.rollback();
					trans.close();
					MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

				}
				if(trans!=null){
					trans.close();
				}
			}//Fin for
		
						
		} else if(bank.getValue().equalsIgnoreCase("bandes_bank")){ //si es el banco BANDES
			
			for (int recorrido = (file-1); recorrido < tope; recorrido++) {
				
				success = true;
				warnings = "";
				
				try {

					//Se lee Fecha en columna A que es 0***************************
					cell = (hoja.getCell(0, recorrido));
					Timestamp fecha = null;
					String fch=utiles.getStringFromCell(cell);
					if(fch!=null){
						try{
								fecha=utiles.formatStringDate(fch);
						}catch (Exception e){
							fecha=null;
						}
					} 
					//---------------------------------------------------------------
					
					//Se lee Fecha Valor en columna B que es 1***************************
					cell = (hoja.getCell(1, recorrido));
					Timestamp fechaV = null;
					String fchV=utiles.getStringFromCell(cell);
					if(fchV!=null){
						try{
								fechaV=utiles.formatStringDate(fchV);
						}catch (Exception e){
							fechaV=null;
						}
					} else { 
						
						MXLSIssue.Add(getCtx(),getTable_ID(),getRecord_ID(),fileName, hoja.getName(),CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()),cell.getContents(),"Fecha Valor vacia",null);
						
					}
					//---------------------------------------------------------------
					
					//Se lee Descripcion en columna C que es 2***************************
					cell = (hoja.getCell(2, recorrido));
					String description=utiles.getStringFromCell(cell);
					//-----------------------------------------------------------------------------

					//Se lee Numero de Documento en columna D que es 3***************************
					cell = (hoja.getCell(3, recorrido));
					String documento=utiles.getStringFromCell(cell);
					if(documento==null){
						documento="0";
						
					}//-----------------------------------------------------------------------------
										
					//Se lee Debito en columna E que es 4***************************
					cell = (hoja.getCell(4, recorrido));
					BigDecimal debito = Env.ZERO;
					String d=utiles.getStringFromCell(cell);
					if(d!=null && !d.equalsIgnoreCase("")){
						d=d.replace("-","");
						d=replaceChar(d);
						debito = new BigDecimal(d);
					}
					
					//Se lee Credito en columna F que es 5***************************
					cell = (hoja.getCell(5, recorrido));
					BigDecimal credito = Env.ZERO;
					String c=utiles.getStringFromCell(cell);
					if(c!=null && !c.equalsIgnoreCase("")){
						c=replaceChar(c);
						credito = new BigDecimal(c);
					}
					//-----------------------------------------------------------------------------
					
					//Se lee Saldo en columna G que es 6***************************
					cell = (hoja.getCell(6, recorrido));
					BigDecimal saldo = Env.ZERO;
					String s=utiles.getStringFromCell(cell);
					if(s!=null && !s.equalsIgnoreCase("")){
						s=s.replace("-","");
						s=replaceChar(s);
						saldo = new BigDecimal(s);
					}
					//--------------------------------------------------------------------------------------								

					if (fecha==null){
						warnings += "Fecha vacia";
						success = false;
					}
					
					if (description==null){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Descripcion vacia";	
						}else warnings += "_Descripcion vacia";
						
						success = false;
					}
						
					if (debito.compareTo(Env.ZERO)==0 && credito.compareTo(Env.ZERO)==0){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Debito y Credito iguales a 0";
						} else warnings += "-Debito y Credito iguales a 0";
						
						success = false;
					}		
					
					if (debito.compareTo(Env.ZERO)>0 && credito.compareTo(Env.ZERO)>0){
						if(warnings.equalsIgnoreCase("")){
							warnings += "Debito y Credito mayores a 0";
						} else warnings += "-Debito y Credito mayores a 0";
						
						success = false;
					}				

							trxAux=Trx.createTrxName();
							trans = Trx.get(trxAux, true);

							ext=new MBankExtract(getCtx(),0, trxAux);
							ext.setDateTrx(fecha);
							ext.setDateValue(fechaV);
							ext.setDescription(description);
							ext.setDocumentNo(documento);
							ext.setAmtSourceCr(credito);
							ext.setAmtSourceDr(debito);
							ext.setTotalAmt(saldo);
							ext.setC_Bank_ID(bank.get_ID());
							ext.setC_BankAccount_ID(loadExtract.getC_BankAccount_ID());
							ext.setC_Currency_ID(account.getC_Currency_ID());
							ext.setUY_LoadExtract_ID(loadExtract.get_ID());
							ext.setobservaciones(warnings);
							ext.setSuccess(success);
							
							docID = getDocType(bank,description);
							if(docID>0) ext.setC_DocType_ID(docID);
							
							ext.saveEx(trxAux);						
					
				} catch (Exception e) {
					//Errores no contemplados
					trans.rollback();
					trans.close();
					MXLSIssue.Add(ctx,table_ID,record_ID,fileName, hoja.getName(),String.valueOf(recorrido) ,"","Error al leer linea",null);

				}
				if(trans!=null){
					trans.close();
				}
			}//Fin for
			
			
		}
		
		if (workbook!=null){
			workbook.close();
		}
		return "Proceso Terminado OK";
	}
	
	/**OpenUp. Nicolas Sarlabos. 14/12/2012
	 * Recorre un string y modifica puntos y comas para evitar problemas al convertir dicho string a BigDecimal
	 * @param cadena
	 * @return
	 */
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

	/**OpenUp. Nicolas Sarlabos. 23/11/2012
	 * Metodo que recibe un banco y una descripcion de movimiento del mismo
	 * y devuelve el ID del documento correspondiente segun la parametrizacion 
	 * @param bank
	 * @param description
	 * @return
	 * @throws SQLException 
	 */
	
	private int getDocType(MBank bank, String description) throws SQLException {

		int value = 0;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		if(bank!=null && description!="" && description!=null){

			try {
				sql = "SELECT description,c_doctype_Id" +
						" FROM uy_extractdocument" +
						" WHERE c_bank_id=" + bank.get_ID();

				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery ();

				while(rs.next()){

					if(description.toUpperCase().trim().contains(rs.getString("description").toUpperCase().trim())){

						value = rs.getInt("c_doctype_id");
						return value;
					}
				}

			} catch(Exception e) {
				throw new AdempiereException(e);

			} finally {
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

		}

		return value;
	}


}
