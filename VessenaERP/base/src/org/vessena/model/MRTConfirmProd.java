/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.openup.retail.MRTRetailInterface;

/**
 * @author Nicolas
 *
 */
public class MRTConfirmProd extends X_UY_RT_ConfirmProd implements DocAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3196409412035282082L;
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * @param ctx
	 * @param UY_RT_ConfirmProd_ID
	 * @param trxName
	 */
	public MRTConfirmProd(Properties ctx, int UY_RT_ConfirmProd_ID,
			String trxName) {
		super(ctx, UY_RT_ConfirmProd_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTConfirmProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt() {
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		this.updatePriceList();	

		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);

		return DocAction.STATUS_Completed;
	}
	
	/***
	 * Obtiene y retorna lineas de lectura de este documento.
	 * OpenUp Ltda. Issue #4435
	 * @author Nicolas Sarlabos - 23/06/2015
	 * @see
	 * @return
	 */
	public List<MRTConfirmProdScan> getScanLines(String where){

		String whereClause = X_UY_RT_ConfirmProdScan.COLUMNNAME_UY_RT_ConfirmProd_ID + "=" + this.get_ID();
		
		if(where != null && !where.equalsIgnoreCase("")) whereClause = whereClause + " " + where;		

		List<MRTConfirmProdScan> lines = new Query(getCtx(), I_UY_RT_ConfirmProdScan.Table_Name, whereClause, get_TrxName())
		.setOrderBy("C_Currency_ID") //SBT 07-04-2016 Issue #5733 
		.list();

		return lines;
	}

	
	/***
	 * Actualiza lista de precios con los precios de los nuevos productos creados en este documento.
	 * OpenUp Ltda. Issue #4435
	 * @author Nicolas Sarlabos - 23/06/2015
	 * @see
	 * @return
	 */
	private void updatePriceList() {
			
		String sql = "", insert = "";
		int round = 0;
		boolean isInterface = false;
		
		//SBT 07/04/2016 Issue #5733 Se agregó moneda ya que existe mas de una de lista de venta, se cambia la lógica del método 
		//Se obtienen las de productos nuevos ordenados por moneda, se recorren los productos y se actualiza haciendo corte de control con la moneda
		int currID =0; int currID_Aux = 0;MPriceListVersion versionActual=null;MPriceList list=null;
		MPriceListVersion newVersion=null;
		List<MRTConfirmProdScan> scanLines = this.getScanLines(" and isconfirmed = 'Y' and isnew = 'Y' and pricelist > 0");//obtengo lineas de lectura del documento actual, para productos nuevos
		isInterface = (MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, this.getAD_Client_ID()));
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

		for(MRTConfirmProdScan line:scanLines){
			currID_Aux = line.get_ValueAsInt("C_Currency_ID");
			//Si es la primera corrida se obtiene la lista de precio para la primer moneda o si no es la misma moneda creo la nueva version de la moneda correspondiente
			if(currID==0 || (currID_Aux != currID)){
				currID = currID_Aux;
				list = MPriceList.getDefault(getCtx(), true, currID);//obtengo lista de precios de venta por defecto

				if(list==null) throw new AdempiereException("No se pudo obtener lista de precios de venta predeterminada");
				
				round = list.getPricePrecision();//obtengo precision de la lista de precios
				
				//obtengo ultima version activa para la lista de precios
				versionActual = MPriceListVersion.forPriceList(getCtx(), list.getM_PriceList_ID(), get_TrxName());

				if(versionActual!=null && versionActual.get_ID()>0){					
					//seteo inactiva la version actual
					DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id = " + versionActual.get_ID(), get_TrxName());
					//creo nueva version
					newVersion = new MPriceListVersion(getCtx(),0,get_TrxName()); //creo nueva version de lista
					newVersion.setM_PriceList_ID(list.get_ID());
					newVersion.setName(today.toString());
					newVersion.setM_DiscountSchema_ID(versionActual.getM_DiscountSchema_ID());
					newVersion.setValidFrom(today);
					newVersion.setIsActive(true);
					newVersion.saveEx();
					
					insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
							"updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";

					sql = "select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + this.getAD_User_ID() + ",now()," + this.getAD_User_ID() + 
							",pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction" +
							" from m_productprice" +
							" where m_pricelist_version_id = " + versionActual.get_ID();

					DB.executeUpdateEx(insert + sql, get_TrxName());
				}else {
					throw new AdempiereException("Error al obtener ultima version activa para la lista seleccionada");
				}
			}
		
			//Inserto el precio del producto correspondiente en la nueva versión
			insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
					"updatedby,pricelist,pricestd,pricelimit,dateaction)";

			sql = "select " + newVersion.get_ID() + "," + line.getM_Product_ID() + "," + line.getAD_Client_ID() + "," + line.getAD_Org_ID() + ",'Y',now()," + 
					this.getAD_User_ID() + ",now()," + this.getAD_User_ID() + "," + line.getPriceList().setScale(round, RoundingMode.HALF_UP) + "," + 
					line.getPriceList().setScale(round, RoundingMode.HALF_UP) + "," + line.getPriceList().setScale(round, RoundingMode.HALF_UP) + ",'" + today + "'";

			DB.executeUpdateEx(insert + sql, get_TrxName());
			
			if (isInterface) {
				
				MProduct prod = (MProduct)line.getM_Product();
				
				//impacto en tabla de interfase
				MRTInterfaceProd.insertInterface(prod, list.getC_Currency_ID(), line.getPriceList(), getCtx(), get_TrxName());
				
				MProductPrintLabel plabel = MProductPrintLabel.forProduct(getCtx(), prod.get_ID(), get_TrxName());
				if ((plabel == null) || (plabel.get_ID() <= 0)){
					plabel = new MProductPrintLabel(getCtx(), 0, get_TrxName());
					plabel.setM_Product_ID(prod.get_ID());
					plabel.set_ValueOfColumn("C_Currency_ID", list.getC_Currency_ID());//SBT 08/04/2016 Issue #5733
					plabel.saveEx();
				}
				
				//Impacto si corresponde en tabla de interfaceo para Balanzas - SBT 18-02-2016 Issue #5351
				if(prod.get_Value("UsaBalanza")!=null){
					if(prod.get_ValueAsBoolean("UsaBalanza")){
						//OpenUp SBT 04/04/2016 se evia el codigo interno ya que es el unico que corresponde enviar a las balanzas
						MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), prod.getValue(), prod.get_ID(), get_TrxName());
					}	
				}
			}
				
		}//FIN SBT 07/04/2016 Issue #5733
		
		
//		if(scanLines.size()>0){
//			
//			MPriceList list = MPriceList.getDefault(getCtx(), true);//obtengo lista de precios de venta por defecto
//			
//			if(list==null) throw new AdempiereException("No se pudo obtener lista de precios de venta predeterminada");
//			
//			round = list.getPricePrecision();//obtengo precision de la lista de precios
//			
//			//obtengo ultima version activa para la lista de precios
//			MPriceListVersion versionActual = MPriceListVersion.forPriceList(getCtx(), list.getM_PriceList_ID(), get_TrxName());
//
//			if(versionActual!=null && versionActual.get_ID()>0){
//				
//				isInterface = (MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, this.getAD_Client_ID()));
//				
//				Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
//				
//				//seteo inactiva la version actual
//				DB.executeUpdateEx("update m_pricelist_version set isactive = 'N' where m_pricelist_version_id = " + versionActual.get_ID(), get_TrxName());
//				
//				//creo nueva version
//				MPriceListVersion newVersion = new MPriceListVersion(getCtx(),0,get_TrxName()); //creo nueva version de lista
//				newVersion.setM_PriceList_ID(list.get_ID());
//				newVersion.setName(today.toString());
//				newVersion.setM_DiscountSchema_ID(versionActual.getM_DiscountSchema_ID());
//				newVersion.setValidFrom(today);
//				newVersion.setIsActive(true);
//				newVersion.saveEx();
//				
//				insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
//						"updatedby,pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction)";
//
//				sql = "select " + newVersion.get_ID() + ",m_product_id,ad_client_id,ad_org_id,isactive,now()," + this.getAD_User_ID() + ",now()," + this.getAD_User_ID() + 
//						",pricelist,pricestd,pricelimit,uy_factor,uy_pricelistunidad,dateaction" +
//						" from m_productprice" +
//						" where m_pricelist_version_id = " + versionActual.get_ID();
//
//				DB.executeUpdateEx(insert + sql, get_TrxName());
//
//				//recorro las lineas de lectura para insertar los nuevos productos en la version nueva
//				for(MRTConfirmProdScan line : scanLines){
//					
//					insert = "INSERT INTO m_productprice (m_pricelist_version_id,m_product_id,ad_client_id,ad_org_id,isactive,created,createdby,updated," +
//							"updatedby,pricelist,pricestd,pricelimit,dateaction)";
//
//					sql = "select " + newVersion.get_ID() + "," + line.getM_Product_ID() + "," + line.getAD_Client_ID() + "," + line.getAD_Org_ID() + ",'Y',now()," + 
//							this.getAD_User_ID() + ",now()," + this.getAD_User_ID() + "," + line.getPriceList().setScale(round, RoundingMode.HALF_UP) + "," + 
//							line.getPriceList().setScale(round, RoundingMode.HALF_UP) + "," + line.getPriceList().setScale(round, RoundingMode.HALF_UP) + ",'" + today + "'";
//
//					DB.executeUpdateEx(insert + sql, get_TrxName());
//					
//					if (isInterface) {
//						
//						MProduct prod = (MProduct)line.getM_Product();
//						
//						//impacto en tabla de interfase
//						MRTInterfaceProd.insertInterface(prod, list.getC_Currency_ID(), line.getPriceList(), getCtx(), get_TrxName());
//						
//						//Impacto si corresponde en tabla de interfaceo para Balanzas - SBT 18-02-2016 Issue #5351
//						if(prod.get_Value("UsaBalanza")!=null){
//							if(prod.get_ValueAsBoolean("UsaBalanza")){
//								//OpenUp SBT 04/04/2016 se evia el codigo interno ya que es el unico que corresponde enviar a las balanzas
//								//MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), "0", prod.get_ID(), get_TrxName());
//								MRTRetailInterface.insertarProdABalanza(getCtx(), this.getAD_Client_ID(), prod.getValue(), prod.get_ID(), get_TrxName());
//							}	
//						}
//					}
//					
//				}					
//
//			} else throw new AdempiereException("Error al obtener ultima version activa para la lista seleccionada");					
//			
//		}			
		
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
