package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MTRMic;

public class PTRPrintMic extends SvrProcess{
	
	private int micID = 0;
	private int copies = 0;
	private String directPrint = "";
	private String printOriginales = "";
	private boolean printType = true; //para indicar si se utiliza el tipo de impresion o se imprime originales y copias simultaneamente
	
	
	public PTRPrintMic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_TR_Mic_ID")){
					if (para[i].getParameter() != null) this.micID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("IsDirectPrint")){
					if (para[i].getParameter() != null) this.directPrint = ((String)para[i].getParameter());
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("IsPrinted")){
					if (para[i].getParameter() != null) this.printOriginales = ((String)para[i].getParameter());
					
					this.printType = false;
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("Copies")){
					if (para[i].getParameter() != null) this.copies = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		
		String sql = "", printType = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int adProcessID = 0;
		BigDecimal copies = Env.ZERO;
		MPInstance instance = null, instance2 = null, instance3 = null, instance4 = null, instance5 = null, instance6 = null, instance7 = null, instance8 = null, instance9 = null;
		ProcessInfo pi = null, pi2 = null, pi3 = null, pi4 = null, pi5 = null, pi6 = null, pi7 = null, pi8 = null, pi9 = null;
		MPInstancePara para = null;
		//ProcessCtl worker = null;
		
		try{
			
			MTRMic hdr = new MTRMic(getCtx(),this.micID, null); //instancio modelo del cabezal de documento
			
			String msg = hdr.validateMic();
			
			if(msg!=null) throw new AdempiereException (msg);
			
			//if(this.printType){
				
			printType = hdr.getPrintFormatType(); //obtengo tipo de impresion (originales o copias)
			
			copies = hdr.getCopies(); //obtengo cantidad de copias

			//ReportCtl.startDocumentPrint(ReportEngine.INVOICE, hdr.get_ID(), true);
			
			
			
			//se imprime primer hoja de formulario
			adProcessID = this.getPrintProcessID(true);
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de impresion para primer hoja de MIC/DTA");
			}
			
			//instance = new MPInstance(Env.getCtx(), adProcessID, 0);
			instance = new MPInstance(getCtx(), adProcessID, 0);
			instance.saveEx();

			pi = new ProcessInfo ("MicHeader", adProcessID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());		
			
			if(this.directPrint.equalsIgnoreCase("Y")){
				pi.setPrintPreview(false);				
			} else pi.setPrintPreview(true);
						
			para = new MPInstancePara(instance, 10);
			para.setParameter("UY_TR_Mic_ID", new BigDecimal(this.micID));
			para.saveEx();
			
			para = new MPInstancePara(instance, 20);
			para.setParameter("PrintFormatType", printType);
			para.saveEx();
			
			para = new MPInstancePara(instance, 30);
			para.setParameter("Copies", copies);
			para.saveEx();

			para = new MPInstancePara(instance, 40);
			para.setParameter("PrintOriginales", this.printOriginales);
			para.saveEx();

			para = new MPInstancePara(instance, 50);
			para.setParameter("PrintOption", this.printType);
			para.saveEx();

			para = new MPInstancePara(instance, 60);
			para.setParameter("cantCopias", this.copies);
			para.saveEx();

			//se imprimen las continuaciones
			adProcessID = this.getPrintProcessID(false);
			if (adProcessID <= 0){
				throw new Exception("No se pudo obtener ID del proceso de impresion para continuacion de MIC/DTA");
			}
			
			sql = "select uy_tr_miccont_id" +
			      " from uy_tr_miccont" +
				  " where uy_tr_mic_id = " + this.micID +
				  " order by sheet asc";
						
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
		
			int cont = 0;
			
			while (rs.next()){

				cont++;
				
				if (cont == 1){
					instance2 = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance2.saveEx();

					pi2 = new ProcessInfo ("MicCont", adProcessID);
					pi2.setAD_PInstance_ID (instance2.getAD_PInstance_ID());
					
					if(this.directPrint.equalsIgnoreCase("Y")){
						pi2.setPrintPreview(false);
					} else pi2.setPrintPreview(true);
					
					para = new MPInstancePara(instance2, 10);
					para.setParameter("UY_TR_MicCont_ID", new BigDecimal(rs.getInt("uy_tr_miccont_id")));
					para.saveEx();
		
					para = new MPInstancePara(instance2, 20);
					para.setParameter("PrintFormatType", printType);
					para.saveEx();
					
					para = new MPInstancePara(instance2, 30);
					para.setParameter("Copies", copies);
					para.saveEx();

					para = new MPInstancePara(instance2, 40);
					para.setParameter("PrintOriginales", this.printOriginales);
					para.saveEx();

					para = new MPInstancePara(instance2, 50);
					para.setParameter("PrintOption", this.printType);
					para.saveEx();

					para = new MPInstancePara(instance2, 60);
					para.setParameter("cantCopias", this.copies);
					para.saveEx();
					
				}
				else if (cont == 2){
					instance3 = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance3.saveEx();

					pi3 = new ProcessInfo ("MicCont2", adProcessID);
					pi3.setAD_PInstance_ID (instance3.getAD_PInstance_ID());
					
					if(this.directPrint.equalsIgnoreCase("Y")){
						pi3.setPrintPreview(false);
					} else pi3.setPrintPreview(true);
					
					para = new MPInstancePara(instance3, 10);
					para.setParameter("UY_TR_MicCont_ID", new BigDecimal(rs.getInt("uy_tr_miccont_id")));
					para.saveEx();
		
					para = new MPInstancePara(instance3, 20);
					para.setParameter("PrintFormatType", printType);
					para.saveEx();
					
					para = new MPInstancePara(instance3, 30);
					para.setParameter("Copies", copies);
					para.saveEx();

					para = new MPInstancePara(instance3, 40);
					para.setParameter("PrintOriginales", this.printOriginales);
					para.saveEx();

					para = new MPInstancePara(instance3, 50);
					para.setParameter("PrintOption", this.printType);
					para.saveEx();

					para = new MPInstancePara(instance3, 60);
					para.setParameter("cantCopias", this.copies);
					para.saveEx();
				}
				else if (cont == 3){
					instance4 = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance4.saveEx();

					pi4 = new ProcessInfo ("MicCont3", adProcessID);
					pi4.setAD_PInstance_ID (instance4.getAD_PInstance_ID());
					
					if(this.directPrint.equalsIgnoreCase("Y")){
						pi4.setPrintPreview(false);
					} else pi4.setPrintPreview(true);
					
					para = new MPInstancePara(instance4, 10);
					para.setParameter("UY_TR_MicCont_ID", new BigDecimal(rs.getInt("uy_tr_miccont_id")));
					para.saveEx();
		
					para = new MPInstancePara(instance4, 20);
					para.setParameter("PrintFormatType", printType);
					para.saveEx();
					
					para = new MPInstancePara(instance4, 30);
					para.setParameter("Copies", copies);
					para.saveEx();

					para = new MPInstancePara(instance4, 40);
					para.setParameter("PrintOriginales", this.printOriginales);
					para.saveEx();

					para = new MPInstancePara(instance4, 50);
					para.setParameter("PrintOption", this.printType);
					para.saveEx();

					para = new MPInstancePara(instance4, 60);
					para.setParameter("cantCopias", this.copies);
					para.saveEx();
				}
				else if (cont == 4){
					instance5 = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance5.saveEx();

					pi5 = new ProcessInfo ("MicCont4", adProcessID);
					pi5.setAD_PInstance_ID (instance5.getAD_PInstance_ID());
					
					if(this.directPrint.equalsIgnoreCase("Y")){
						pi5.setPrintPreview(false);
					} else pi5.setPrintPreview(true);
					
					para = new MPInstancePara(instance5, 10);
					para.setParameter("UY_TR_MicCont_ID", new BigDecimal(rs.getInt("uy_tr_miccont_id")));
					para.saveEx();
		
					para = new MPInstancePara(instance5, 20);
					para.setParameter("PrintFormatType", printType);
					para.saveEx();
					
					para = new MPInstancePara(instance5, 30);
					para.setParameter("Copies", copies);
					para.saveEx();

					para = new MPInstancePara(instance5, 40);
					para.setParameter("PrintOriginales", this.printOriginales);
					para.saveEx();

					para = new MPInstancePara(instance5, 50);
					para.setParameter("PrintOption", this.printType);
					para.saveEx();

					para = new MPInstancePara(instance5, 60);
					para.setParameter("cantCopias", this.copies);
					para.saveEx();
				}
				else if (cont == 5){
					instance6 = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance6.saveEx();

					pi6 = new ProcessInfo ("MicCont5", adProcessID);
					pi6.setAD_PInstance_ID (instance6.getAD_PInstance_ID());
					
					if(this.directPrint.equalsIgnoreCase("Y")){
						pi6.setPrintPreview(false);
					} else pi6.setPrintPreview(true);
					
					para = new MPInstancePara(instance6, 10);
					para.setParameter("UY_TR_MicCont_ID", new BigDecimal(rs.getInt("uy_tr_miccont_id")));
					para.saveEx();
		
					para = new MPInstancePara(instance6, 20);
					para.setParameter("PrintFormatType", printType);
					para.saveEx();
					
					para = new MPInstancePara(instance6, 30);
					para.setParameter("Copies", copies);
					para.saveEx();

					para = new MPInstancePara(instance6, 40);
					para.setParameter("PrintOriginales", this.printOriginales);
					para.saveEx();

					para = new MPInstancePara(instance6, 50);
					para.setParameter("PrintOption", this.printType);
					para.saveEx();

					para = new MPInstancePara(instance6, 60);
					para.setParameter("cantCopias", this.copies);
					para.saveEx();
				}
				else  if (cont == 6){
					instance7 = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance7.saveEx();

					pi7 = new ProcessInfo ("MicCont6", adProcessID);
					pi7.setAD_PInstance_ID (instance7.getAD_PInstance_ID());
					
					if(this.directPrint.equalsIgnoreCase("Y")){
						pi7.setPrintPreview(false);
					} else pi7.setPrintPreview(true);
					
					para = new MPInstancePara(instance7, 10);
					para.setParameter("UY_TR_MicCont_ID", new BigDecimal(rs.getInt("uy_tr_miccont_id")));
					para.saveEx();
		
					para = new MPInstancePara(instance7, 20);
					para.setParameter("PrintFormatType", printType);
					para.saveEx();
					
					para = new MPInstancePara(instance7, 30);
					para.setParameter("Copies", copies);
					para.saveEx();

					para = new MPInstancePara(instance7, 40);
					para.setParameter("PrintOriginales", this.printOriginales);
					para.saveEx();

					para = new MPInstancePara(instance7, 50);
					para.setParameter("PrintOption", this.printType);
					para.saveEx();

					para = new MPInstancePara(instance7, 60);
					para.setParameter("cantCopias", this.copies);
					para.saveEx();
					
				} else  if (cont == 7){
					instance8 = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance8.saveEx();

					pi8 = new ProcessInfo ("MicCont7", adProcessID);
					pi8.setAD_PInstance_ID (instance8.getAD_PInstance_ID());
					
					if(this.directPrint.equalsIgnoreCase("Y")){
						pi8.setPrintPreview(false);
					} else pi8.setPrintPreview(true);
					
					para = new MPInstancePara(instance8, 10);
					para.setParameter("UY_TR_MicCont_ID", new BigDecimal(rs.getInt("uy_tr_miccont_id")));
					para.saveEx();
		
					para = new MPInstancePara(instance8, 20);
					para.setParameter("PrintFormatType", printType);
					para.saveEx();
					
					para = new MPInstancePara(instance8, 30);
					para.setParameter("Copies", copies);
					para.saveEx();

					para = new MPInstancePara(instance8, 40);
					para.setParameter("PrintOriginales", this.printOriginales);
					para.saveEx();

					para = new MPInstancePara(instance8, 50);
					para.setParameter("PrintOption", this.printType);
					para.saveEx();

					para = new MPInstancePara(instance8, 60);
					para.setParameter("cantCopias", this.copies);
					para.saveEx();
					
				} else {
					instance9 = new MPInstance(Env.getCtx(), adProcessID, 0);
					instance9.saveEx();

					pi9 = new ProcessInfo ("MicCont8", adProcessID);
					pi9.setAD_PInstance_ID (instance9.getAD_PInstance_ID());
					
					if(this.directPrint.equalsIgnoreCase("Y")){
						pi9.setPrintPreview(false);
					} else pi9.setPrintPreview(true);
					
					para = new MPInstancePara(instance9, 10);
					para.setParameter("UY_TR_MicCont_ID", new BigDecimal(rs.getInt("uy_tr_miccont_id")));
					para.saveEx();
		
					para = new MPInstancePara(instance9, 20);
					para.setParameter("PrintFormatType", printType);
					para.saveEx();
					
					para = new MPInstancePara(instance9, 30);
					para.setParameter("Copies", copies);
					para.saveEx();

					para = new MPInstancePara(instance9, 40);
					para.setParameter("PrintOriginales", this.printOriginales);
					para.saveEx();

					para = new MPInstancePara(instance9, 50);
					para.setParameter("PrintOption", this.printType);
					para.saveEx();

					para = new MPInstancePara(instance9, 60);
					para.setParameter("cantCopias", this.copies);
					para.saveEx();
				}
				
			}				

			try {
				Env.getCtx().remove("pi2");	
			} catch (Exception e) {
				// No hago nada
			}

			try {
				Env.getCtx().remove("pi3");	
			} catch (Exception e) {
				// No hago nada
			}

			try {
				Env.getCtx().remove("pi4");	
			} catch (Exception e) {
				// No hago nada
			}

			try {
				Env.getCtx().remove("pi5");	
			} catch (Exception e) {
				// No hago nada
			}

			try {
				Env.getCtx().remove("pi6");	
			} catch (Exception e) {
				// No hago nada
			}
			
			try {
				Env.getCtx().remove("pi7");	
			} catch (Exception e) {
				// No hago nada
			}
			
			try {
				Env.getCtx().remove("pi8");	
			} catch (Exception e) {
				// No hago nada
			}
			
			try {
				Env.getCtx().remove("pi9");	
			} catch (Exception e) {
				// No hago nada
			}

			
			if (pi2 != null){
				Env.getCtx().put("pi2", pi2);	
			}
			if (pi3 != null){
				Env.getCtx().put("pi3", pi3);	
			}
			if (pi4 != null){
				Env.getCtx().put("pi4", pi4);	
			}
			if (pi5 != null){
				Env.getCtx().put("pi5", pi5);	
			}
			if (pi6 != null){
				Env.getCtx().put("pi6", pi6);	
			}
			if (pi7 != null){
				Env.getCtx().put("pi7", pi7);	
			}
			if (pi8 != null){
				Env.getCtx().put("pi8", pi8);	
			}
			if (pi9 != null){
				Env.getCtx().put("pi9", pi9);	
			}
			
			ReportStarter starter = new ReportStarter();
			starter.startProcess(getCtx(), pi, null);
			
			return "OK";
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	/***
	 * Obtiene y retorna id del proceso de impresion de MIC/DTA recibiendo como 
	 * parametro un boolean que indica si es formulario cabezal o continuacion.
	 * OpenUp Ltda. Issue #1629
	 * @author Nicolas Sarlabos - 08/04/2014
	 * @see
	 * @return
	 */
	private int getPrintProcessID(boolean isHeader) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try{
			
			if(isHeader){
				
				if(this.directPrint.equalsIgnoreCase("Y")){
					
					sql = " select ad_process_id " +
							  " from ad_process " +
							  " where lower(value)='uy_rtr_micheader'";	
					
					
				} else {
					
					sql = " select ad_process_id " +
							  " from ad_process " +
							  " where lower(value)='uy_rtr_micheader_preview'";						
					
				}
						
				
			} else {
				
				if(this.directPrint.equalsIgnoreCase("Y")){
					
					sql = " select ad_process_id " +
							  " from ad_process " +
							  " where lower(value)='uy_rtr_miccont'";					
					
				} else {
					
					sql = " select ad_process_id " +
							  " from ad_process " +
							  " where lower(value)='uy_rtr_miccont_preview'";			
					
				}							
				
			}	
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			if (rs.next()){
				value = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;

	}


}
