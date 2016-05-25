package org.openup.process;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.openup.beans.AuxWorkCellXLS;
import org.openup.model.MGruposClientes;
import org.openup.model.MGruposClientesAsociados;
import org.openup.model.MXLSIssue;

public class PXLSPromotionGroup {

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

	/*public PXLSPromotionGroup(Properties pctx, Integer ptableID, Integer precordID, CLogger plog, String pfileName, boolean pErrorRepetidos) {
		super(pctx, ptableID, precordID, plog, pfileName, pErrorRepetidos);
		// TODO Auto-generated constructor stub
	}*/
	
	public PXLSPromotionGroup(Properties pctx, Integer ptable_ID, Integer precord_ID, CLogger plog, String pfileName, boolean pErrorRepetidos) {
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

	/**
	 * Realiza las validaciones iniciales del documento XLS 
	* OpenUp Ltda. Issue #965
	* @author Nicolas Sarlabos - 23/02/2012
	* @see
	* @return
	 */

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

	/**
	 * Recorre el XLS y va cargando las tablas
	* OpenUp Ltda. Issue #965
	* @author Nicolas Sarlabos - 23/02/2012
	* @see
	* @return
	* @throws Exception
	 */

	private String readXLS() throws Exception {
		//carga de datos a usar en memoria
		HashMap<String, Integer> clientes = this.getCodClientes(); //Obtengo referencias validas para tipos de ajuste
		//instancio clase auxiliar
		utiles = new AuxWorkCellXLS(ctx, table_ID, record_ID, fileName, hoja, log);
		int recorrido = 0;
		String group_value = "";
		String group_name = "";
		MGruposClientes hdr = null;
		Cell cell = null;
		tope = hoja.getRows();
		String trxAux = Trx.createTrxName();
		Trx trans = Trx.get(trxAux, true);

		try {

			//TODO ARRANCA EN FILA 3
			for (recorrido = 2; recorrido < tope; recorrido++) {

				//Se lee codigo del grupo Ubicado en columna A que es 0***************************
				cell = hoja.getCell(0, recorrido);
				String codGrupo = utiles.getStringFromCell(cell);

				//Se pregunta si es null
				if (codGrupo == null) {
					//Mensaje
					utiles.addMsg(cell, "Código de grupo vacío");
				}
				//---------------------------------------------------------------

				//Se lee nombre del grupo ubicado en columna B que es 1***************************
				cell = hoja.getCell(1, recorrido);
				String nomGrupo = utiles.getStringFromCell(cell);

				//Se pregunta si es null
				if (nomGrupo == null) {
					//Mensaje
					utiles.addMsg(cell, "Nombre de grupo vacío");
				}
				//-----------------------------------------------------------------------------

				if (codGrupo != null && nomGrupo != null) {

					//si el codigo de grupo es distinto al actual -> creo nuevo cabezal
					if (!codGrupo.equalsIgnoreCase(group_value)) {

						group_value = codGrupo;
						group_name = nomGrupo;

						//Creo cabezal
						hdr = new MGruposClientes(ctx, 0, trxAux);
						hdr.setValue(group_value);
						hdr.setName(group_name);
						hdr.saveEx(trxAux);
					}

				}

				//Se lee codigo de cliente ubicado en columna C que es 2***************************
				String codCliente = utiles.getStringFromCell(hoja.getCell(2, recorrido));
				if (codCliente != null) {
					codCliente = utiles.getStringFromCell(hoja.getCell(2, recorrido)).toUpperCase().trim();
					if (!isValidClient(codCliente, clientes)) {
						utiles.addMsg(hoja.getCell(2, recorrido), "Código de cliente inválido");
						codCliente = null;
					}

				} else
					utiles.addMsg(hoja.getCell(2, recorrido), "No se ingresó código de cliente");
				//-----------------------------------------------------------------------------

				if (codGrupo != null && nomGrupo != null && codCliente != null) {

					//obtengo ID del cliente...
					Integer clientID = this.searchID(recorrido, hoja.getCell(2, recorrido), clientes, "Cliente");

					MGruposClientesAsociados line = new MGruposClientesAsociados(ctx, 0, trxAux);
					line.setC_BPartner_ID(clientID);
					line.setIsActive(true);
					line.setUY_GruposClientes_ID(hdr.get_ID());
					line.saveEx(trxAux);

				}
			}//fin FOR


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

	/**
	 * Obtiene hashmap con los codigos de clientes
	 * OpenUp Ltda. Issue #965
	 * @author Nicolas Sarlabos - 23/02/2012
	 * @see
	 * @return
	 * @throws Exception
	 */

	private HashMap<String, Integer> getCodClientes() throws Exception {

		//Cargo codigos de clientes validos 
		HashMap<String, Integer> salida = new HashMap<String, Integer>();

		String sql = "SELECT c_bpartner_id, value FROM c_bpartner WHERE iscustomer='Y' AND isactive='Y'";

		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			// Si existe resultado
			while (rs.next()) {
				salida.put(rs.getString("value").toUpperCase().trim(), rs.getInt("c_bpartner_id"));

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

	/**
	 * Valida que exista el codigo del cliente
	 * OpenUp Ltda. Issue #965
	 * @author Nicolas Sarlabos - 23/02/2012
	 * @see
	 * @return
	 * @throws Exception
	 */

	private boolean isValidClient(String codigo, HashMap<String, Integer> clientes) {

		if (clientes.containsKey(codigo)) {
			return true;
		} else
			return false;

	}

	/**
	 * Obtiene el ID del cliente
	 * OpenUp Ltda. Issue #965
	 * @author Nicolas Sarlabos - 23/02/2012
	 * @see
	 * @param recorrido
	 * @param cell
	 * @param lista
	 * @param nombreRecorrida
	 * @return
	 * @throws Exception
	 */

	private Integer searchID(int recorrido, Cell cell, HashMap<String, Integer> lista, String nombreRecorrida) throws Exception {

		String auxNomb = utiles.getStringFromCell(cell);
		Integer salida = null;

		//Se pregunta si es null
		if (auxNomb != null) {
			//Valido que el nombre exista
			salida = lista.get(auxNomb.toUpperCase().trim());

			if (salida == null) {
				//Mensaje ID no encontrado
				utiles.addMsg(cell, nombreRecorrida + " no encontrado");
			}

		}

		return salida;
	}



}
