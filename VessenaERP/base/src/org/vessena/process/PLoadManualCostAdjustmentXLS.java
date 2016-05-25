package org.openup.process;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MCostAdjustment;
import org.openup.model.MCostAdjustmentLine;
import org.openup.model.MXLSIssue;

public class PLoadManualCostAdjustmentXLS {

	Sheet hoja = null;
	String fileName = null;

	Workbook workbook = null;
	Integer tope = 0;
	int linesOK = 0;
	int linesMal = 0;
	AuxWorkCellXLS utiles;
	Properties ctx;
	Integer table_ID;
	Integer record_ID;
	CLogger log;
	boolean errorRepetidos;

	public PLoadManualCostAdjustmentXLS(Properties pctx, Integer ptable_ID, Integer precord_ID, CLogger plog, String pfileName, boolean pErrorRepetidos) {
		log = plog;
		record_ID = precord_ID;
		table_ID = ptable_ID;
		fileName = pfileName;
		this.ctx = pctx;
		errorRepetidos = pErrorRepetidos;
	}

	public String procesar() throws Exception {

		// Validacion inicial Planilla
		String s = validacionXLSInicial();
		if (!(s.equals(""))) {
			return s;
		}

		return this.readXLS();

	}

	private String readXLS() throws Exception {

		//Carga de datos a usar en memoria
		HashMap<String, Integer> lm_product = this.getLClaves("m_product", "", false);
		HashMap<String, Integer> ref = this.getRef(); //Obtengo referencias validas para tipos de ajuste
		int recorrido = 0;
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(ctx, table_ID, record_ID, fileName, hoja, log);

		Cell cell = null;
		tope = hoja.getRows();
		String trxAux = Trx.createTrxName();
		Trx trans = Trx.get(trxAux, true);
		try {
			//Creo cabezal
			MCostAdjustment hdr = new MCostAdjustment(ctx, 0, trxAux);
			String sql = "SELECT c_doctype_id from c_doctype where docbasetype='AMC'"; //obtengo ID del tipo de documento
			int docID = DB.getSQLValue(null, sql);
			hdr.setC_DocType_ID(docID);
			hdr.setDateTrx(new Timestamp(System.currentTimeMillis()));
			hdr.setDocAction(DocumentEngine.ACTION_Complete);
			hdr.setDocStatus(DocumentEngine.STATUS_Drafted);
			hdr.saveEx(trxAux);

			//TODO ARRANCA EN FILA 3
			for (recorrido = 2; recorrido < tope; recorrido++) {

				//Se lee Value de Producto Ubicado en columna A que es 0***************************
				cell = hoja.getCell(0, recorrido);
				String codigo = utiles.getStringFromCell(cell);

				//Se pregunta si es null
				if (codigo == null) {
					//Mensaje
					utiles.addMsg(cell, "Codigo producto vacío");
				} else {
					//Valido que el codigo de producto exista
					if (isValid(codigo.toUpperCase().trim())) {
						if (isRepeated(codigo.toUpperCase().trim(), hdr)) {
							if (errorRepetidos) {
								utiles.addMsg(cell, "Ya existe un registro para éste producto");
							}
							codigo = null;

						}

					} else {
						//Mensaje codigo no existente
						utiles.addMsg(cell, "Código de producto no existe");
						codigo = null;

					}//---------------------------------------------------------------

					//Se lee Costo en columna B que es 1***************************
					BigDecimal costo = utiles.getBigDecimalFromCell(hoja.getCell(1, recorrido));
					if (costo != null) {
						if (costo.compareTo(Env.ZERO) < 0) {
							utiles.addMsg(hoja.getCell(1, recorrido), "No se acepta costo negativo");
							costo = null;
						}
					} else
						utiles.addMsg(hoja.getCell(1, recorrido), "No se ingresó costo");
					//-----------------------------------------------------------------------------

					//Se lee Tipo Ajuste en columna C que es 2***************************
					String tipo = utiles.getStringFromCell(hoja.getCell(2, recorrido));
					if (tipo != null) {
						tipo = utiles.getStringFromCell(hoja.getCell(2, recorrido)).toUpperCase().trim();
						if (!isValidRef(tipo, ref)) {
							utiles.addMsg(hoja.getCell(2, recorrido), "Tipo de ajuste inválido");
							tipo = null;
						}

					} else
						utiles.addMsg(hoja.getCell(2, recorrido), "No se ingresó tipo de ajuste");
					//-----------------------------------------------------------------------------

					if (codigo != null && costo != null && tipo != null) {

						//trxAux = Trx.createTrxName();
						//trans = Trx.get(trxAux, true);

						Integer prodId = this.searchID(recorrido, hoja.getCell(0, recorrido), lm_product, "Producto");

						MCostAdjustmentLine line = new MCostAdjustmentLine(ctx, 0, trxAux);
						line.setUY_CostAdjustment_ID(hdr.get_ID());
						line.setM_Product_ID(prodId);
						line.setAmount(costo);
						line.setC_Currency_ID(142);
						line.setuy_tipoajuste(tipo);
						line.saveEx(trxAux);

					}
				}
			}//fin FOR

			//Si no se obtuvieron lineas entonces se elimina el cabezal
			if (hdr.getLines() == 0) {

				String sql2 = "DELETE FROM uy_costadjustment WHERE uy_costadjustment_id=" + hdr.get_ID();
				DB.executeUpdateEx(sql2, trxAux);

			}

		} catch (Exception e) {
			//Errores no contemplados
			trans.rollback();
			trans.close();
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName, hoja.getName(), String.valueOf(recorrido), "", "Error al leer linea", null);

		}
		if (trans != null) {
			trans.close();
		}

		if (workbook != null) {
			workbook.close();
		}

		return "Proceso Terminado OK";
	}

	private HashMap<String, Integer> getLClaves(String nombre, String where, boolean buscarPorName) throws Exception {
		String clave = "value";
		//Por defecto se busca por value, pero podes buscar por nombre
		if (buscarPorName) {
			clave = "name";
		}
		//Cargo datos a usar validos para nombre
		HashMap<String, Integer> salida = new HashMap<String, Integer>();

		String sql = "SELECT " + nombre + "_id, " + clave + " FROM " + nombre + where;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			while (rs.next()) {
				salida.put(rs.getString(clave).toUpperCase().trim(), rs.getInt(nombre + "_id"));
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

	private HashMap<String, Integer> getRef() throws Exception {
	
		//Cargo datos a usar validos para referencias de tipos de ajuste
		HashMap<String, Integer> salida = new HashMap<String, Integer>();
		
		String sql = "SELECT ad_ref_list_id, value FROM ad_ref_list WHERE ad_reference_id="
				+ " (SELECT ad_reference_id FROM ad_reference WHERE lower(name) like 'uy_ref_tiposajustecostos')";

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			while (rs.next()) {
				salida.put(rs.getString("value").toUpperCase().trim(), rs.getInt("ad_ref_list_id"));

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

	private String validacionXLSInicial() {

		if ((fileName == null) || (fileName.equals(""))) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName, "", "", "", "El nombre de la planilla excel esta vacio", null);
			return ("El nombre de la planilla excel esta vacio");
		}
		// Creo el objeto
		File file = new File(fileName);

		// Si el objeto no existe
		if (!file.exists()) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName, "", "", "", "No se encontro la planilla Excel", null);
			return ("No se encontro la planilla Excel");
		}

		try {

			// Get de workbook
			workbook = AuxWorkCellXLS.getReadWorkbook(file);

			// Abro la primer hoja
			hoja = workbook.getSheet(0);

			// Se vacia la lista de errores
			MXLSIssue.Delete(this.ctx, this.table_ID, this.record_ID, null);

			if (hoja.getColumns() < 1) {
				MXLSIssue.Add(ctx, table_ID, record_ID, fileName, "", "", "", "La primer hoja de la planilla Excel no tiene columnas", null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}

			if (hoja.getRows() < 1) {
				MXLSIssue.Add(ctx, table_ID, record_ID, fileName, "", "", "", "La primer hoja de la planilla Excel no tiene columnas", null);
				return ("La primer hoja de la planilla Excel no tiene columnas");
			}
		} catch (Exception e) {
			MXLSIssue.Add(ctx, table_ID, record_ID, fileName, "", "", "", "Error al abrir planilla (TRY) " + e.toString(), null);
			return ("Error al abrir planilla (TRY)");
		}

		return "";
	}

	//Se valida que exista el codigo de producto
	private boolean isValid(String codigo) throws Exception {
		boolean s = false;

		String sql = "SELECT m_product_id FROM m_product WHERE value='" + codigo + "'";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			if (rs.next()) {
				s = true;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return s;
	}

	//Se valida que exista la referencia para el tipo de ajuste
	private boolean isValidRef(String tipo, HashMap<String, Integer> ref) {

		if (ref.containsKey(tipo)) {
			return true;
		} else
			return false;

	}
	//Se valida que no exista un registro para ese producto
	private boolean isRepeated(String codigo, MCostAdjustment hdr) throws Exception {
		boolean s = false;

		String sql = "SELECT m_product_id FROM m_product WHERE value='" + codigo + "'";
		int id = DB.getSQLValue(null, sql);

		String sql2 = "SELECT m_product_id FROM uy_costadjustmentline WHERE m_product_id=" + id + " AND uy_costadjustment_id=" + hdr.get_ID();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql2, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			if (rs.next()) {
				s = true;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return s;
	}

	private Integer searchID(int recorrido, Cell cell, HashMap<String, Integer> lista, String nombreRecorrida) throws Exception {

		String auxNomb = utiles.getStringFromCell(cell);
		Integer salida = null;

		//Se pregunta si es null
		if (auxNomb != null) {
			//Valido que el nombre exista
			salida = lista.get(auxNomb.toUpperCase().trim());

			if (salida == null) {
				//Mensaje categoria no encontrada
				utiles.addMsg(cell, nombreRecorrida + " no valida");
			}

		}

		return salida;
	}

}
