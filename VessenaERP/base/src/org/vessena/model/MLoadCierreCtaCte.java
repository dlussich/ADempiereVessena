/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 13/09/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.Waiting;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MActivity;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.model.MGLCategory;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.model.X_GL_Category;
import org.compiere.model.X_GL_Journal;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.beans.AuxWorkCellXLS;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;
import jxl.Workbook;


/**
 * org.openup.model - MLoadCierreCtaCte
 * OpenUp Ltda. Issue #29 
 * Description: Carga masiva de cierre de cuenta corriente y generacion de asiento contable.
 * @author Gabriel Vila - 13/09/2012
 * @see
 */
public class MLoadCierreCtaCte extends X_UY_LoadCierreCtaCte {

	Workbook workBook = null;
	Sheet sheet = null;
	
	List<MJournalLine> journalLines = new ArrayList<MJournalLine>();

	int rowCount = 0;
	int linesOK = 0;
	int linesFailed = 0;

	private Waiting waiting = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4183804170977807462L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_LoadCierreCtaCte_ID
	 * @param trxName
	 */
	public MLoadCierreCtaCte(Properties ctx, int UY_LoadCierreCtaCte_ID,
			String trxName) {
		super(ctx, UY_LoadCierreCtaCte_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLoadCierreCtaCte(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}


	/***
	 * Ejecuta proceso de carga de informacion desde planilla excel.
	 * OpenUp Ltda. Issue #32 
	 * @author Hp - 17/09/2012
	 * @see
	 */
	public void executeProcess(){
		
		// Elimino inconsistencias viejas
		this.deleteOldIssues();
		
		// Valido formato de planilla
		this.validateFile();
		
		// Cargo filas
		this.loadData();	

		if (!this.isFailed()){
			this.setProcessed(true);
			this.setFailed(false);
		}
		else{
			this.setFailed(true);
		}
		
		this.saveEx();
		
	}


	/***
	 * Elimina inconsistencias viejas de este proceso
	 * OpenUp Ltda. Issue #29 
	 * @author Gabriel Vila - 13/09/2012
	 * @see
	 */
	private void deleteOldIssues() {

		try{
		
			this.showHelp("Eliminando inconsistencias anteriores...");
			
			String action = " DELETE FROM " + X_UY_XLSIssue.Table_Name +
							" WHERE ad_table_id =" + Table_ID +
							" AND record_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	
	/***
	 * Valida el formato del archivo a procesar.
	 * OpenUp Ltda. Issue #29 
	 * @author Gabriel Vila - 13/09/2012
	 * @see
	 */
	private void validateFile() {

		try{

			this.showHelp("Validando consistencia del archivo...");
			
			if ((this.getFileName() == null) || (this.getFileName().equalsIgnoreCase(""))) {
				throw new AdempiereException("Debe indicar archivo a procesar.");
			}

			File file = new File(this.getFileName());

			if (!file.exists()) {
				throw new AdempiereException("El archivo indicado no existe.");
			}

			// Abro workbook
			this.workBook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primera hoja
			this.sheet = this.workBook.getSheet(0);

			if (this.sheet.getColumns() < 1) {
				throw new AdempiereException("La primer hoja de la planilla Excel no tiene columnas.");
			}

			if (this.sheet.getRows() < 1) {
				throw new AdempiereException("La primer hoja de la planilla Excel no tiene filas.");
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}


	/***
	 * Recorre filas y celdas del archivo excel. Valida el contenido y genera lineas de asiento 
	 * diario en memoria.
	 * OpenUp Ltda. Issue #29 
	 * @author Gabriel Vila - 13/09/2012
	 * @see
	 */
	private void loadData() {
		
		Cell cell = null;
		this.rowCount = this.sheet.getRows();
		this.setFailed(false);
		Timestamp auxFecha = null, fechaAsiento = null;
		BigDecimal totalDebe = Env.ZERO, totalHaber = Env.ZERO;
		boolean tengoUnValorCelda = false;

		DateFormat formatter ; 
        Date date ; 
        formatter = new SimpleDateFormat("dd/MM/yy");

        int precision = 4;
		
		// Iniciamos en Fila 2 (la primer Fila es el titulo), corte por fecha de asiento
		for (int recorrido = 1; recorrido < this.rowCount; recorrido++) {

			this.showHelp("Cargando fila " + recorrido + " de " + this.rowCount);
			
			tengoUnValorCelda = false;
			
			try {
				// Valido Fecha Asiento ubicado columna A que es 0
				cell = this.sheet.getCell(0, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
			        date = (Date)formatter.parse(cell.getContents()); 
			        fechaAsiento = new Timestamp(date.getTime());					
					//fechaAsiento = Timestamp.valueOf(cell.getContents());
					tengoUnValorCelda = true;
				}				
			}
			catch (Exception e){
				this.setFailed(true);
				MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
				CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Cuenta Debe Invalida", null);		
			}
			
			try {
				
				if (recorrido == 1){
					auxFecha = fechaAsiento;
				}
				else{
					if ((!fechaAsiento.equals(auxFecha)) && (!this.isFailed())){
						this.generateSimpleJournal(auxFecha, totalDebe, totalHaber);
						totalDebe = Env.ZERO;
						totalHaber = Env.ZERO;
						auxFecha = fechaAsiento;
						this.journalLines = new ArrayList<MJournalLine>();
						tengoUnValorCelda = false;
					}
				}
				
				MJournalLine journalLine = new MJournalLine(getCtx(), 0, get_TrxName());
				MElementValue debe = null, haber = null;
				MActivity ccosto1 = null, ccosto2 = null, ccosto3 = null;
				BigDecimal importe = Env.ZERO;
				
				// Valido Cuenta Debe ubicado columna B que es 1
				cell = this.sheet.getCell(1, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
					tengoUnValorCelda = true;
					debe = MElementValue.forValue(getCtx(), cell.getContents().trim(), null);
					if ((debe == null) || (debe.get_ID() <= 0)){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Cuenta Debe Invalida", null);		
					}
				}
			
				// Valido Cuenta Haber ubicado columna C que es 2
				cell = this.sheet.getCell(2, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
					tengoUnValorCelda = true;
					haber = MElementValue.forValue(getCtx(), cell.getContents().trim(), null);
					if ((haber == null) || (haber.get_ID() <= 0)){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Cuenta Haber Invalida", null);		
					}
				}
			
				// Valido CCosto 1 ubicado columna D que es 3
				cell = this.sheet.getCell(3, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
					tengoUnValorCelda = true;
					ccosto1 = MActivity.forValue(getCtx(), cell.getContents().trim(), null);
					if ((ccosto1 == null) || (ccosto1.get_ID() <= 0)){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Centro de Costo 1 Invalido", null);		
					}
					else{
						// Valido que ccosto1 sea de categoria 1
						MCategoriaCCostos categCC1 = new MCategoriaCCostos(getCtx(), ccosto1.getUY_Categoria_CCostos_ID(), null);
						
						if (categCC1.get_ID() <= 0){
							this.setFailed(true);
							MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
							CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Centro de Costo 1 NO tiene Categoria Asociada", null);		
						}
						
						if (!categCC1.getValue().equalsIgnoreCase("1")){
							this.setFailed(true);
							MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
							CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Centro de Costo 1 NO pertenece a Categoria 1", null);		
						}
					}
				}

				// Valido CCosto 2 ubicado columna D que es 4
				cell = this.sheet.getCell(4, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
					tengoUnValorCelda = true;
					ccosto2 = MActivity.forValue(getCtx(), cell.getContents().trim(), null);
					if ((ccosto2 == null) || (ccosto2.get_ID() <= 0)){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Centro de Costo 2 Invalido", null);		
					}
					else{
						// Valido que ccosto2 sea de categoria 2
						MCategoriaCCostos categCC2 = new MCategoriaCCostos(getCtx(), ccosto2.getUY_Categoria_CCostos_ID(), null);
						if (!categCC2.getValue().equalsIgnoreCase("2")){
							this.setFailed(true);
							MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
							CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Centro de Costo 2 NO pertenece a Categoria 2", null);		
						}
					}
				}

				// Valido CCosto 3 ubicado columna D que es 5
				cell = this.sheet.getCell(5, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
					tengoUnValorCelda = true;
					ccosto3 = MActivity.forValue(getCtx(), cell.getContents().trim(), null);
					if ((ccosto3 == null) || (ccosto3.get_ID() <= 0)){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Centro de Costo 3 Invalido", null);		
					}
					else{
						// Valido que ccosto3 sea de categoria 3
						MCategoriaCCostos categCC3 = new MCategoriaCCostos(getCtx(), ccosto3.getUY_Categoria_CCostos_ID(), null);
						if (!categCC3.getValue().equalsIgnoreCase("3")){
							this.setFailed(true);
							MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
							CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Centro de Costo 3 NO pertenece a Categoria 3", null);		
						}
					}					
				}

				// Proceso concepto ubicado columna G que es 6
				cell = this.sheet.getCell(6, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
					tengoUnValorCelda = true;
					journalLine.setDescription(cell.getContents().trim());
				}
				
				// Valido y proceso moneda ubicado columna H que es 7
				cell = this.sheet.getCell(7, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
					tengoUnValorCelda = true;
					String moneda = cell.getContents().trim();
					if ( (!moneda.equalsIgnoreCase("0")) && (!moneda.equalsIgnoreCase("1"))
							&& (!moneda.equalsIgnoreCase("2"))){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Moneda Invalida.(0 = Pesos, 1 = Dolares)", null);		
					}
					else{
						if (moneda.equalsIgnoreCase("0")) journalLine.setC_Currency_ID(142); // pesos uruguayos 
						else if (moneda.equalsIgnoreCase("1")) journalLine.setC_Currency_ID(100); // dolares
						else if (moneda.equalsIgnoreCase("2")) journalLine.setC_Currency_ID(118); // pesos argentinos
					}
				}
				else{
					if (tengoUnValorCelda){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Falta indicar Moneda (0 = Pesos, 1 = Dolares)", null);		
					}
				}
				
				// Valido importe ubicado columna I que es 8
				cell = this.sheet.getCell(8, recorrido);
				if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
					tengoUnValorCelda = true;
					importe = new BigDecimal(cell.getContents().trim().replace(",", ""));
				    importe = importe.setScale(precision);
				}
				else{
					if (tengoUnValorCelda){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),"Falta indicar Importe", null);		
					}
				}

				// Verifico si tengo tipo de cambio ubicado columna J que es 9
				BigDecimal currencyRate = Env.ONE;
				
				try{
					cell = this.sheet.getCell(9, recorrido);	
				}
				catch(Exception e){
					cell = null;
				}
				
				if (cell != null){
					if ((cell.getContents() != null) && (!cell.getContents().equalsIgnoreCase(""))){
						tengoUnValorCelda = true;
						currencyRate = new BigDecimal(cell.getContents().trim().replace(",", ""));
						currencyRate = currencyRate.setScale(3);
						
						// Si viene moneda nacional
						if (journalLine.getC_Currency_ID() == 142){
							// Si tengo tipo de cambio, la moneda es la moneda de la cuenta que tenga
							// y ademas el importe si viene en otra moneda hago traduccion segun este tipo de cambio.
							if (currencyRate.compareTo(Env.ONE) != 0){
								MElementValue ctaAux = null;
								if (debe != null) ctaAux = debe;
								else if (haber != null) ctaAux = haber;
								journalLine.setC_Currency_ID(ctaAux.getC_Currency_ID());
								importe = importe.divide(currencyRate, precision, RoundingMode.HALF_UP);
							}
						}
					}
				}

				// Al final valido que tenga seteado cuentas
				if (tengoUnValorCelda){
					// Valido que no tenga ninguna cuenta
					if ((debe == null) && (haber == null)){
						this.setFailed(true);
						MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
						CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), "", "Falta definir Cuenta Contable", null);		
					}
				}
				
				if (!tengoUnValorCelda) continue;
				
				if (!this.isFailed()){

					journalLine.setAD_Org_ID(this.getAD_Org_ID());
					journalLine.setC_ConversionType_ID(114);
					if ((journalLine.getC_Currency_ID() != 142) && (currencyRate.compareTo(Env.ONE) == 0)){
						currencyRate = MConversionRate.getRate(journalLine.getC_Currency_ID(), 142, 
												 auxFecha, 114, this.getAD_Client_ID(), this.getAD_Org_ID());
						
						if (currencyRate == null){
							currencyRate = Env.ONE;
							this.setFailed(true);
							MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), 
							CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), "", 
							"Falta definir Tasa de Cambio Dolares - Pesos Uruguayos para la fecha : " + auxFecha.toString(), null);
						}
						else{
							journalLine.setCurrencyRate(currencyRate);	
						}
					}
					else{
						journalLine.setCurrencyRate(currencyRate);
					}
					
					journalLine.setDateAcct(auxFecha);

					if (ccosto1 != null) journalLine.setC_Activity_ID_1(ccosto1.get_ID());
					if (ccosto2 != null) journalLine.setC_Activity_ID_2(ccosto2.get_ID());
					if (ccosto3 != null) journalLine.setC_Activity_ID_3(ccosto3.get_ID());

					// Si tengo dos cuentas en la misma linea tengo que hacer dos journallines
					if ((debe != null) && (haber != null)){
						// Linea al debe
						journalLine.setC_ElementValue_ID(debe.get_ID());
						journalLine.setAmtSourceDr(importe);
						totalDebe = totalDebe.add(importe.multiply(currencyRate).setScale(4, RoundingMode.HALF_UP));
						this.journalLines.add(journalLine);
						
						// Nueva linea al haber
						MJournalLine jLineHaber = new MJournalLine(getCtx(), 0, get_TrxName());
						jLineHaber.setDescription(journalLine.getDescription());
						jLineHaber.setC_Currency_ID(journalLine.getC_Currency_ID());
						jLineHaber.setAD_Org_ID(journalLine.getAD_Org_ID());
						jLineHaber.setC_ConversionType_ID(journalLine.getC_ConversionType_ID());
						jLineHaber.setCurrencyRate(journalLine.getCurrencyRate());
						jLineHaber.setDateAcct(journalLine.getDateAcct());
						jLineHaber.setC_Activity_ID_1(journalLine.getC_Activity_ID_1());
						jLineHaber.setC_Activity_ID_2(journalLine.getC_Activity_ID_2());
						jLineHaber.setC_Activity_ID_3(journalLine.getC_Activity_ID_3());
						jLineHaber.setC_ElementValue_ID(haber.get_ID());
						jLineHaber.setAmtSourceCr(importe);
						totalHaber = totalHaber.add(importe.multiply(currencyRate).setScale(4, RoundingMode.HALF_UP));
						this.journalLines.add(jLineHaber);
					}
					else{
						if (debe != null){
							journalLine.setC_ElementValue_ID(debe.get_ID());
							journalLine.setAmtSourceDr(importe);
							totalDebe = totalDebe.add(importe.multiply(currencyRate).setScale(4, RoundingMode.HALF_UP));
						}
						else if (haber != null){
							journalLine.setC_ElementValue_ID(haber.get_ID());
							journalLine.setAmtSourceCr(importe);
							totalHaber = totalHaber.add(importe.multiply(currencyRate).setScale(4, RoundingMode.HALF_UP));
						}
						this.journalLines.add(journalLine);
					}
				}
				
			} 
			catch (Exception e) {
				//Errores no contemplados
				this.setFailed(true);
				MXLSIssue.Add(getCtx(), Table_ID, this.get_ID(), this.getFileName(), this.sheet.getName(), String.valueOf(recorrido) ,"Excepcion",e.getMessage(), null);
//				throw new AdempiereException(e);
			}
		}

		if (this.workBook != null){
			this.workBook.close();
		}
		
		// Proceso ultimo asiento
		if (!this.isFailed()){
			this.generateSimpleJournal(auxFecha, totalDebe, totalHaber);	
		}
		else{
			throw new AdempiereException("Planilla con errores. Verifique las Inconsistencias.");
		}
	}

	/***
	 * Genera asiento diario simple y lo completa.
	 * OpenUp Ltda. Issue #29 
	 * @author Gabriel Vila - 14/09/2012
	 * @see
	 */
	private void generateSimpleJournal(Timestamp dateAcct, BigDecimal totalDebe, BigDecimal totalHaber) {

		/*
		if (totalDebe.compareTo(totalHaber) != 0){
			throw new AdempiereException("Total Debitos no es igual a Total Creditos : " + totalDebe + " - " + totalHaber);
		}
		*/
		
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		MDocType doc = MJournal.getSimpleJournalDocType(getCtx(), null);
		
		if ((doc == null) || (doc.get_ID() <= 0)){
			throw new AdempiereException("No se pudo obtener Documento para Asiento Diario Simple");
		}
		
		MJournal journal = new MJournal(getCtx(), 0, get_TrxName());
		journal.setAD_Org_ID(this.getAD_Org_ID());
		journal.setC_AcctSchema_ID(schema.get_ID());
		journal.setC_DocType_ID(doc.get_ID());
		journal.setDescription("Carga Masiva Asiento Diario : " + this.getDocumentNo());
		journal.setDateDoc(this.getDateTrx());
		journal.setDateAcct(dateAcct);
		journal.setC_Currency_ID(schema.getC_Currency_ID());
		journal.setC_ConversionType_ID(114);
		journal.setCurrencyRate(Env.ONE);
		journal.setTotalDr(totalDebe);
		journal.setTotalCr(totalHaber);
		journal.setGL_Category_ID(MGLCategory.getDefault(getCtx(), X_GL_Category.CATEGORYTYPE_Manual).get_ID());
		journal.setUY_LoadCierreCtaCte_ID(this.get_ID());
		journal.saveEx();
		
		// Seteo id del journal a las linas y las inserto
		for (MJournalLine line : this.journalLines){
			line.setGL_Journal_ID(journal.get_ID());
			line.saveEx();
		}

		// Completo asiento
		if (MSysConfig.getBooleanValue("UY_JOURNAL_COMPLETE_ONLOAD", false, this.getAD_Client_ID())){
			if (!journal.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException(journal.getProcessMsg());
			}
		}
			
	}

	
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}

	/***
	 * Obtiene y retorna lista de asientos generador en esta carga.
	 * OpenUp Ltda. Issue #29 
	 * @author Gabriel Vila - 15/11/2012
	 * @see
	 * @return
	 */
	public List<MJournal> getJournalsByStatus(String docStatus, String trxName) {
		
		String whereStatus = " AND DocStatus='" + docStatus + "' ";
		if (docStatus.equalsIgnoreCase("DR")){
			whereStatus = " AND DocStatus IN('IN','DR') ";
		}
		
		String whereClause = X_GL_Journal.COLUMNNAME_UY_LoadCierreCtaCte_ID + "=" + this.get_ID() +
							 whereStatus;
		
		List<MJournal> lines = new Query(getCtx(), I_GL_Journal.Table_Name, whereClause, trxName)
		.list();
		
		return lines;
		
	}
}
