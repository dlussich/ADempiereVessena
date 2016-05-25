/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.openup.process;


import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.CellType;
import jxl.NumberCell;
import jxl.NumberFormulaCell;
import jxl.Workbook;

import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.model.MLoadhdr_XLS;
import org.openup.model.MXLSIssue;

/**
 *  Load XLS interfases from one window  
 *
 *	@author OpenUP FL 03/02/2011 issue #149
 */
//org.openup.process.PXLSCustomerReturn
public class PXLSCustomerReturn extends PLoadInherit{
	
	public PXLSCustomerReturn(Properties pctx, Integer ptableID,
			Integer precordID, CLogger plog, String pfileName,
			boolean pErrorRepetidos) {
		super(pctx, ptableID, precordID, plog, pfileName, pErrorRepetidos);
		// TODO Auto-generated constructor stub
	}

	// The start row to scan for data, start at line 2, the line 1 should have titles
	public static final int ROW_TITLE=0; 
	
	// The start row to scan for data, start at line 3, the line 2 should have titles ,the line 1 should have Instruction
	public static final int ROW_START=2; 
	
	// The maximun acepted errors at rows before stop, when many consecutive errors are detected means that this is the end 
	public static final int ROW_MAX_ERRORS=3;

	//private static final int COLUMN_START = 0;
	
	
	
	private String MovementType=MInOut.MOVEMENTTYPE_CustomerReturns;
	private boolean flagSave=true;
	
	private int UY_InOutType=1000000;
	private int C_DocType_ID=1000066;
	private Timestamp DateAcct	=new Timestamp(System.currentTimeMillis());
	private String DeliveryRule	="A";
	private String  DeliveryViaRule="P";
	private String DocAction="RQ";
	private String DocStatus="DR";
	private String FreightCostRule="I";
	private boolean  IsApproved=false;
	private boolean  IsInDispute=false;
	private boolean IsInTransit=false;
	private boolean IsPrinted=false;
	private boolean IsSOTrx=true;
	private Timestamp MovementDate	=new Timestamp(System.currentTimeMillis());
	private String PriorityRule	="5";
	
	private int C_UOM_ID=100;
	private boolean IsDescription=false;
	private boolean IsInvoiced=true;

	

	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	//protected void prepare() {
//		
//		// Get parameters
//		ProcessInfoParameter[] para = getParameter();
//		for (int i = 0; i < para.length; i++) {
//			
//			String name = para[i].getParameterName();
//			if (name.equals("fileName")) {
//				this.fileName=(String) para[i].getParameter();
//			}
////			else if (name.equals("AD_Window_ID")) {
////				this.AD_Window_ID=(Integer) para[i].getParameter();
////			}
//		}
//		
//	
	//}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	public String procesar() throws Exception {
		String trxAux=Trx.createTrxName();
		Trx trans = Trx.get(trxAux, true);
		
		
		// The file name cannot be empty o null
		if ((this.fileName==null)||(this.fileName.equals(""))) {
			MXLSIssue.Add(this.ctx,this.table_ID,this.record_ID,this.fileName,"","","","El nombre de la planilla excel esta vacio",null);	// TODO: translate
			return("El nombre de la planilla excel esta vacio");																					// TODO: translate
		}
		
		// Create the file object
		File file=new File(this.fileName);
		
		// Nothing to process if the file dont exist
		if (!file.exists()) {
			MXLSIssue.Add(this.ctx,this.table_ID,this.record_ID,this.fileName,"","","","No se encontro la planilla Excel",null);	// TODO: translate
			return("No se encontro la planilla Excel");																						// TODO: translate
		}
		
		// The workbook
		Workbook workbook=null;
		
		try{
			// Open de workbook
			workbook = Workbook.getWorkbook(file);
			
			// Get the sincronized sheet
			hoja=workbook.getSheet(0);
			
			//
			MInOut inOut=null;
			
			// Delete previouse messages
			MXLSIssue.Delete(this.ctx, this.table_ID, this.record_ID,null);
			
			// Get the system value
			int tope = MSysConfig.getIntValue("UY_MAX_LINEAS_FACTURA", 30, Env.getAD_Client_ID(Env.getCtx()));
			int rowCount=1;
			
			// OpenUp. Gabriel Vila. 22/07/2011. Issue #800
			ArrayList<Integer> inOutIDs = new ArrayList<Integer>();
			// Fin Issue #800
			
			// Loop over each row
			for (int row=ROW_START;row<hoja.getRows();row++) {

			
				
				int Product_ID						=this.getM_Product_ID(hoja.getCell(4,row));
				BigDecimal QtyEntered				=this.getQtyEntered(hoja.getCell(7,row));
				if ((QtyEntered!=null)&&(!(QtyEntered.equals(BigDecimal.ZERO)))&&(Product_ID!=-1)) {
				
						
					String POReference				=this.getPOReference(hoja.getCell(0,row));
					String Description				=this.getDescription(hoja.getCell(1,row));
					int BPartner_Location_ID		=this.getC_BPartner_Location_ID(hoja.getCell(3,row));
					int BPartner_ID					=this.getC_BPartner_ID(BPartner_Location_ID);
					int UY_InOutType_ID				=this.getUY_InOutType_ID(hoja.getCell(5,row));
					BigDecimal FlatDiscount			=this.getFlatDiscount(hoja.getCell(6,row));
					int salesRep_id					=this.getSalesRep(BPartner_ID);
					int wareHouse 					=this.getWareHouse(UY_InOutType_ID);
					int locator						=this.getmLocator(wareHouse);
					
					// If the partner and location are null skip new header
					if ((BPartner_ID!=-1)&&(BPartner_Location_ID!=-1)) {
						
						String POReference2="";
						if (inOut!=null) {
							POReference2=inOut.getPOReference();
							if (POReference2==null) {
								POReference2="";
							}
						}
						
						// If the header is not defined or the partner, location or reference change, then create a new header
						if ((locator==0)||(wareHouse==0)||(inOut==null)||(inOut.getC_BPartner_ID()!=BPartner_ID)||(inOut.getC_BPartner_Location_ID()!=BPartner_Location_ID)||(!(POReference2.equals(POReference)))) {
							
							// Create the header and set default values
							inOut=new MInOut(this.ctx,0,trxAux);
							inOut.setPOReference(POReference);
							inOut.setDescription(Description);
							inOut.setC_BPartner_ID(BPartner_ID);
						
							inOut.setC_BPartner_Location_ID(BPartner_Location_ID);	
							inOut.setMovementType(this.MovementType);
							inOut.setSalesRep_ID(salesRep_id);
							
							inOut.setDateReceived(new Timestamp(System.currentTimeMillis()));
							inOut.setShipDate(new Timestamp(System.currentTimeMillis()));
							inOut.setCreateConfirm("N");
							inOut.setCreatePackage("N");
							inOut.setSendEMail(false);
							inOut.setGenerateTo("N");
							inOut.setCreateFrom("N");
							

							inOut.setC_DocType_ID(this.C_DocType_ID);
							inOut.setDateAcct(this.DateAcct);
							inOut.setDeliveryRule(this.DeliveryRule);
							inOut.setDeliveryViaRule(this.DeliveryViaRule);
							inOut.setDocAction(this.DocAction);
							inOut.setDocStatus(this.DocStatus);
							inOut.setFreightCostRule(this.FreightCostRule);
							inOut.setIsApproved(this.IsApproved);
							inOut.setIsInDispute(this.IsInDispute);
							inOut.setIsInTransit(this.IsInTransit);
							inOut.setIsPrinted(this.IsPrinted);
							inOut.setIsSOTrx(this.IsSOTrx);
							inOut.setMovementDate(this.MovementDate);
							inOut.setMovementType(this.MovementType);
							inOut.setPriorityRule(this.PriorityRule);
							inOut.setM_Warehouse_ID(1000013);
							inOut.setUY_InOutType_ID(this.UY_InOutType);
							
							inOut.saveEx(trxAux);
							rowCount=0;				// Rest row count
							
							// OpenUp. Gabriel Vila. 22/07/2011. Issue #800
							inOutIDs.add(inOut.get_ID());
							// Fin Issue #800
						}
					}
					
					// Save the line
					if (inOut!=null) {
						
						// Copy a new header
						if (rowCount>tope) {
							inOut.setM_InOut_ID(0);
							inOut.setDocumentNo(null); //OpenUp Gabriel Vila #843 31/10/2011
							inOut.saveEx(trxAux);
							rowCount=1;				// Reset row count
						
							// OpenUp. Gabriel Vila. 22/07/2011. Issue #800
							inOutIDs.add(inOut.get_ID());
							// Fin Issue #800
						}
						
						// Create the line and set default values
						MInOutLine inOutLine=new MInOutLine(this.ctx,0,trxAux);
						inOutLine.setM_Product_ID(Product_ID);
						inOutLine.setUY_InOutType_ID(UY_InOutType_ID);
						inOutLine.setFlatDiscount(FlatDiscount);
						inOutLine.setQtyEntered(QtyEntered);	
						inOutLine.setC_UOM_ID(this.C_UOM_ID);
						inOutLine.setIsDescription(this.IsDescription);
						inOutLine.setIsInvoiced(this.IsInvoiced);
						inOutLine.setLine(row*10);
						inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
						inOutLine.setMovementQty(QtyEntered);
						inOutLine.setTargetQty(QtyEntered);
						inOutLine.setPickedQty(QtyEntered);
						
						inOutLine.setM_Warehouse_ID(wareHouse);
						inOutLine.setM_Locator_ID(locator);
						
						// Get the in/out type to set warehouse
//						MInOutType inOutType=new MInOutType(this.ctx,inOutLine.getUY_InOutType_ID(),null);
//						inOutLine.setM_Warehouse_ID(inOutType.getM_Warehouse_ID());
//						inOutLine.setM_Locator_ID(inOutType.getDefaultM_Locator_ID());

						inOutLine.saveEx(trxAux);
						rowCount++;
					}
					else {
						this.addMsg(null, "Linea no agregada, faltan datos o hay errores en datos de cabezal");
					}
				}
				else {
					//this.addMsg(null, "Linea vacia");
				}
			}//End For
			
			// OpenUp. Gabriel Vila. 22/07/2011. Issue #800
			// Al final del proceso debo dejar todas las devoluciones como solicitadas
			for (int i=0; i < inOutIDs.size(); i++){
				MInOut inOutGenerada = new MInOut(this.ctx, inOutIDs.get(i).intValue(), trxAux);
				if (inOutGenerada.get_ID() > 0)
					inOutGenerada.requestIt();
				//OpenUp Nicolas Sarlabos #800 14/09/2011
				//se guarda la devolucion
				inOutGenerada.save(trxAux);
				//fin OpenUp Nicolas Sarlabos
			}
			
			// Fin Issue #800
			
			//if not  has happened a error then save the transaction
			MLoadhdr_XLS Loadhdr_XLS = new MLoadhdr_XLS(ctx, record_ID, null);
			if(this.flagSave){
				trans.commit();
				Loadhdr_XLS.setText("Carga Finalizada Correctamente");
				Loadhdr_XLS.saveEx();
			
			}else{
				
				Loadhdr_XLS.setText("La planilla tiene errores, Favor revise y vuelva a correr el proceso");
				Loadhdr_XLS.saveEx();
				trans.rollback();
			}
		}
		catch (Exception e) {
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally {
			trans.close();
			// Close the workbook
			if (workbook!=null) {					// Defernsive
				workbook.close();				
			}
			if (trans!=null) {				// Defernsive
				trans.close();	
				trans=null;
			}			
		}
		
		return "Proceso Terminado OK";
		
	}	//	doIt
	
	
	private int getmLocator(int warehouse) {
		int s=0;
		switch (warehouse) {
		case 1000013:
			s=1000008;
			break;
		case 1000015:
			s=1000012;
			break;
		case 1000016:
			s=1000013;
			break;
		default:
			s=0;
			this.addMsg(null,"Problema inesperado con la locacion");
			break;
		}			
		return s;
	}


	private int getWareHouse(int uyInOutTypeID) {
		int s=0;
		switch (uyInOutTypeID) {
		case 1000000:
			s=1000013;
			break;
		case 1000001:
			s=1000016;
			break;
		case 1000002:
			s=1000015;
			break;
		default:
			this.addMsg(null,"Problema inesperado con el almacen");
			s=0;
			break;
		}			
		return s;
	}


	private int getSalesRep(int bPartnerID) {
		// 	
		if(bPartnerID>0){
			// if exist BpartnerLocation, then return the c_b partner_id associate to cbpartnerlocation
			MBPartner  aux=new MBPartner (this.ctx, bPartnerID, null);			
			if(aux!=null){				
				return aux.getSalesRep_ID();
			}
		}
		
		flagSave=false;
		
		return -1 ;
	}


	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell
	 * @return
	 * @author  fabian.larroca 
	 * Fecha : 27/04/2011
	 */
	private String getPOReference(Cell cell) {
		return(cell.getContents().trim());
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell
	 * @return
	 * @author  fabian.larroca 
	 * Fecha : 27/04/2011
	 */
	private String getDescription(Cell cell) {
		return(cell.getContents().trim());
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell
	 * @return
	 * @author  fabian.larroca 
	 * Fecha : 27/04/2011
	 */
	private int getC_BPartner_ID(int c_BPartner_Location_ID) {
		
		if(c_BPartner_Location_ID!=-1){
			// if exist BpartnerLocation, then return the c_b partner_id associate to cbpartnerlocation
			MBPartnerLocation aux=new MBPartnerLocation(this.ctx, c_BPartner_Location_ID, null);			
			if(aux!=null){				
				return aux.getC_BPartner_ID();
			}
			
		}
		flagSave=false;
		this.addMsg(null,"revisar vendedor de los cliente");
		
		return -1 ;
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell
	 * @param cBPartnerID
	 * @return
	 * @author  fabian.larroca 
	 * Fecha : 27/04/2011
	 */
	private int getC_BPartner_Location_ID(Cell cell) {
		String value=cell.getContents().trim().toUpperCase();
		String sql="SELECT l.c_bpartner_location_id FROM  c_bpartner_location l "
			+ "LEFT JOIN c_bpartner b ON b.c_bpartner_id=l.C_bpartner_id "
			+ "WHERE upper(trim(b.value))=? OR upper(trim(l.uy_gln))=? ";
		int c_bpartner_location_id=DB.getSQLValue(null,sql,value,value);
		if(c_bpartner_location_id==-1){
			flagSave=false;
			this.addMsg(cell, "Codigo de cliente o GLN de Locacion de Cliente no validos");
		}
		return c_bpartner_location_id;
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell
	 * @return
	 * @author  fabian.larroca 
	 * Fecha : 27/04/2011
	 */
	private int getUY_InOutType_ID(Cell cell) {

		String value=cell.getContents().trim().toUpperCase();
		if (value.isEmpty()) {
			return(this.UY_InOutType);	// TODO: at list use a constant or system property
		}

		int inOutType_ID=DB.getSQLValue(null,"SELECT UY_InOutType.UY_InOutType_ID FROM UY_InOutType WHERE upper(trim(UY_InOutType.name))=?",value);
		if (inOutType_ID==-1) {
			// Error message
			flagSave=false;
			this.addMsg(cell,"Motivo de devolucion no valido"); // TODO: translate
		}
		
		return(inOutType_ID);
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell
	 * @return
	 * @author  fabian.larroca 
	 * Fecha : 27/04/2011
	 */
	private int getM_Product_ID(Cell cell) {
		String value=cell.getContents().trim().toUpperCase();
		
		// An empty value is not valid but a meesage is not needed
		if (value.isEmpty()) {
			return(-1);
		}	
		
		int M_Product_ID=DB.getSQLValue(null,"SELECT m_product.m_product_id FROM m_product WHERE upper(trim(value))=? OR upper(trim(upc))=? OR upper(trim(upc2))=?",value,value,value);
		if (M_Product_ID==-1) {
			// Error message
			flagSave=false;
			this.addMsg(cell,"Codigo o UPC de producto no valido"); // TODO: translate
		}
		
		return(M_Product_ID);
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell
	 * @return
	 * @author  fabian.larroca 
	 * Fecha : 27/04/2011
	 */
	private BigDecimal getFlatDiscount(Cell cell) {

		if (cell.getType()==CellType.NUMBER) {
			NumberCell numberCell=(NumberCell) cell;
			return(new BigDecimal(numberCell.getValue()));
		}
		else if (cell.getType()==CellType.NUMBER_FORMULA) {
			NumberFormulaCell numberFormulaCell=(NumberFormulaCell) cell;
			return(new BigDecimal(numberFormulaCell.getValue()));
		} 
		else if (!(cell.getContents().isEmpty())){
			flagSave=false;
			this.addMsg(cell, "Descuento no valido");
			return(null);
		}

		return BigDecimal.ZERO;
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell
	 * @return
	 * @author  fabian.larroca 
	 * Fecha : 27/04/2011
	 */
	private BigDecimal getQtyEntered(Cell cell) {
		String value=cell.getContents().trim().toUpperCase();

		// An empty value is not valid but a meesage is not needed
		if (value.isEmpty()) {
			return(null);
		}			
		
		if (cell.getType()==CellType.NUMBER) {
			NumberCell numberCell=(NumberCell) cell;
			return(new BigDecimal(numberCell.getValue()));
		}
		else {
			if (cell.getType()==CellType.NUMBER_FORMULA) {
				NumberFormulaCell numberFormulaCell=(NumberFormulaCell) cell;
				return(new BigDecimal(numberFormulaCell.getValue()));
			}
		}
		flagSave=false;
		this.addMsg(cell, "Cantidad no valido");
		return null;
	}
	
	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cell, msg
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 27/04/2011
	 * */
	public  void addMsg(Cell cell, String msg){
		if(cell==null){
			MXLSIssue.Add(this.ctx,this.table_ID,this.record_ID,fileName, this.hoja.getName(), "","",msg,null);
		}else{
			MXLSIssue.Add(this.ctx,this.table_ID,this.record_ID,fileName, this.hoja.getName(), CellReferenceHelper.getCellReference(cell.getColumn(),cell.getRow()), cell.getContents(),msg,null);
		}
	}

	
	
	
}
