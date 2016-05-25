package org.openup.process;

import java.util.Properties;

import jxl.Sheet;

import org.compiere.util.CLogger;
import org.openup.beans.AuxWorkCellXLS;

public abstract class PLoadInherit {
	Sheet hoja=null;
	String fileName = null;
	AuxWorkCellXLS utiles;
	Properties ctx;
	Integer table_ID;
	Integer record_ID;	
	CLogger	log ;
	boolean errorRepetidos;
	
	public PLoadInherit(Properties pctx,Integer ptable_ID,Integer precord_ID,CLogger plog,String pfileName, boolean pErrorRepetidos){
		log=plog ;
		record_ID=precord_ID;
		table_ID =ptable_ID;
		fileName=pfileName;
		this.ctx =pctx;
		errorRepetidos=pErrorRepetidos;
	}
		

	public abstract String procesar() throws Exception;
	

}
