package org.openup.dgi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class Util {
	public static String getUyTrDigitalsignature(String campoFiltro, int ad_client_id, int ad_org_id, String trxName)
			throws AdempiereException {
		// Obteniendo datos de la firma de seguridad
		String sql = "";
		String campoRet = "";

		sql = "select " + campoFiltro + " from uy_tr_digitalsignature"
				+ " where ad_client_id = " + ad_client_id
				+ " and  ad_org_id = " + ad_org_id;
		campoRet = DB.getSQLValueStringEx(trxName, sql);

		if (campoRet == null || campoRet.equalsIgnoreCase(""))
			throw new AdempiereException("");
		return campoRet;
	}
	
	public static String readFromFile(String path){
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String ret = "";

		try {
			archivo = new File(path);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			String linea;
			while ((linea = br.readLine()) != null)
				ret += linea;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static void writeToFile(String path, String strXml) {

		Writer out = null; 
		try {
			out = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(path), "UTF-8")
				);
			out.write(strXml);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
