/**
 * 
 */
package org.openup.process;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.process.SvrProcess;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata.Type;
import uk.co.mmscomputing.device.scanner.ScannerListener;

/**
 * @author gbrust
 *
 */
public class PAttachScan extends SvrProcess implements ScannerListener {
	
	Scanner pp;

	public PAttachScan() {		
	}
	
	@Override
	protected void prepare() {		
	}
	
	@Override
	protected String doIt() throws Exception {			
		this.executeScann();		
		return "ok";
	}
	
	private void executeScann(){			
		try{
			pp = Scanner.getDevice();
		    pp.addListener(this);    
		    pp.acquire();
		    
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void update(Type type, ScannerIOMetadata metadata) {
	    if(type.equals(ScannerIOMetadata.ACQUIRED)){
	        BufferedImage image=metadata.getImage();
	        System.out.println("Have an image now!");
	        try{         
	          this.guardarAttachment(image);
	          pp.removeListener(this);
	          
	        }catch(Exception e){
	          e.printStackTrace();
	        }
	      }else if(type.equals(ScannerIOMetadata.NEGOTIATE)){
	        ScannerDevice device=metadata.getDevice();
	        try{
//	          device.setShowUserInterface(true);
//	          device.setShowProgressBar(true);
//	          device.setResolution(100);
	        }catch(Exception e){
	          e.printStackTrace();
	        }
	      }else if(type.equals(ScannerIOMetadata.STATECHANGE)){
	        System.err.println(metadata.getStateStr());
	        if(metadata.isFinished()){
	          System.exit(0);
	        }
	      }else if(type.equals(ScannerIOMetadata.EXCEPTION)){
	        metadata.getException().printStackTrace();
	      }	
	}
	
	
	private void guardarAttachment(BufferedImage image){
		
		MAttachment attach = new MAttachment(getCtx(), 0, this.get_TrxName());
		
		try {
			File archivo = new File("opup.jpg");
			ImageIO.write(image, "jpg", archivo );
			attach.addEntry(archivo);
			attach.setAD_Table_ID(this.getTable_ID());
			attach.setRecord_ID(this.getRecord_ID());
			attach.setTextMsg("Escaneo Automatico");
	         
	        attach.saveEx();
	         
	        archivo.delete();
	        
		} catch (Exception e) {
			new AdempiereException(e);
		}
       
	}	

}
