
package org.eevolution.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MStorage;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUOM;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;


// Referenced classes of package org.eevolution.model:
// X_PP_Order_BOMLine, MPPOrder, MPPProductBOMLine, MPPProductBOM
// OpenUp. Nicolas Garcia. 17/08/2011. Issue #824.Clase descompialada para este
// caso
public class MPPOrderBOMLine extends X_PP_Order_BOMLine {
	
	public static MPPOrderBOMLine forM_Product_ID(Properties properties, int i, int j, String s) {
		// OpenUp. INes Fernandez. 26/05/2015. Contempla caso prod de transformacion. Issue #4126
		if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, Integer.parseInt(MPPOrderBOMLine.COLUMNNAME_AD_Client_ID))){
			return (MPPOrderBOMLine) (new Query(properties, "PP_Order_BOMLine", "UY_ProdTransf_ID=? AND M_Product_ID=?", s))
					.setParameters(new Object[] { Integer.valueOf(i), Integer.valueOf(j) }).firstOnly();
		}
		return (MPPOrderBOMLine) (new Query(properties, "PP_Order_BOMLine", "PP_Order_ID=? AND M_Product_ID=?", s))
				.setParameters(new Object[] { Integer.valueOf(i), Integer.valueOf(j) }).firstOnly();
	}
	//OpenUp. INes Fernandez. 26/05/2015. constructor que contempla caso prod de transformacion. Issue #4126
	public static MPPOrderBOMLine forM_Product_ID(boolean isProdTrans, Properties properties, int i, int j, String s) {
		return (MPPOrderBOMLine) (new Query(properties, "PP_Order_BOMLine", "UY_ProdTransf_ID=? AND M_Product_ID=?", s)).setParameters(
				new Object[] { Integer.valueOf(i), Integer.valueOf(j) }).firstOnly();
	}
	
	public MPPOrderBOMLine(Properties properties, int i, String s) {
		super(properties, i, s);
		m_parent = null;
		m_isExplodePhantom = false;
		m_qtyRequiredPhantom = null;
		m_qtyOnHand = null;
		m_qtyAvailable = null;
		if (i == 0)
			setDefault();
	}

	public MPPOrderBOMLine(Properties properties, ResultSet resultset, String s) {
		super(properties, resultset, s);
		m_parent = null;
		m_isExplodePhantom = false;
		m_qtyRequiredPhantom = null;
		m_qtyOnHand = null;
		m_qtyAvailable = null;
	}

	public MPPOrderBOMLine(MPPProductBOMLine mppproductbomline, int i, int j, int k, String s) {
		this(mppproductbomline.getCtx(), 0, s);
		setPP_Order_BOM_ID(j);
		setPP_Order_ID(i);
		setM_Warehouse_ID(k);
		setM_ChangeNotice_ID(mppproductbomline.getM_ChangeNotice_ID());
		setDescription(mppproductbomline.getDescription());
		setHelp(mppproductbomline.getHelp());
		setAssay(mppproductbomline.getAssay());
		setQtyBatch(mppproductbomline.getQtyBatch());
		setQtyBOM(mppproductbomline.getQtyBOM());
		setIsQtyPercentage(mppproductbomline.isQtyPercentage());
		setComponentType(mppproductbomline.getComponentType());
		setC_UOM_ID(mppproductbomline.getC_UOM_ID());
		setForecast(mppproductbomline.getForecast());
		setIsCritical(mppproductbomline.isCritical());
		setIssueMethod(mppproductbomline.getIssueMethod());
		setLeadTimeOffset(mppproductbomline.getLeadTimeOffset());
		setM_AttributeSetInstance_ID(mppproductbomline.getM_AttributeSetInstance_ID());
		setM_Product_ID(mppproductbomline.getM_Product_ID());
		setScrap(mppproductbomline.getScrap());
		setValidFrom(mppproductbomline.getValidFrom());
		setValidTo(mppproductbomline.getValidTo());
		setBackflushGroup(mppproductbomline.getBackflushGroup());
	}
	//OpenUp. INes Fernandez. 26/05/2015. Constructor que contempla caso prod de transformacion. Issue #4126
	public MPPOrderBOMLine(boolean isProdTransf, MPPProductBOMLine mppproductbomline, int i, int j, int k, String s) {
		this(mppproductbomline.getCtx(), 0, s);
		if(isProdTransf)
		{
			setPP_Order_BOM_ID(j);
			setPP_Order_ID(i); 
			set_ValueOfColumnReturningBoolean("UY_ProdTransf_ID", i);
			setM_Warehouse_ID(k);
			setM_ChangeNotice_ID(mppproductbomline.getM_ChangeNotice_ID());
			setDescription(mppproductbomline.getDescription());
			setHelp(mppproductbomline.getHelp());
			setAssay(mppproductbomline.getAssay());
			setQtyBatch(mppproductbomline.getQtyBatch());
			setQtyBOM(mppproductbomline.getQtyBOM());
			setIsQtyPercentage(mppproductbomline.isQtyPercentage());
			setComponentType(mppproductbomline.getComponentType());
			setC_UOM_ID(mppproductbomline.getC_UOM_ID());
			setForecast(mppproductbomline.getForecast());
			setIsCritical(mppproductbomline.isCritical());
			setIssueMethod(mppproductbomline.getIssueMethod());
			setLeadTimeOffset(mppproductbomline.getLeadTimeOffset());
			setM_AttributeSetInstance_ID(mppproductbomline.getM_AttributeSetInstance_ID());
			setM_Product_ID(mppproductbomline.getM_Product_ID());
			setScrap(mppproductbomline.getScrap());
			setValidFrom(mppproductbomline.getValidFrom());
			setValidTo(mppproductbomline.getValidTo());
			setBackflushGroup(mppproductbomline.getBackflushGroup());
		}
		
	}

	protected boolean beforeSave(boolean flag) {

		// OpenUp. Nicolas Garcia. 17/08/2011. Issue #824.
		// Guardo el m_Locator Correcto y actualizo el campo oculto QtyRequired
		int mLocatorID = DB.getSQLValue(null, "SELECT m_locator_id FROM m_locator WHERE m_warehouse_id =?", this.getM_Warehouse_ID());
		this.setM_Locator_ID(mLocatorID);
		this.setQtyRequired(this.getQtyRequiered());
		// Fin Issue #824

		if (!isActive())
			throw new AdempiereException("De-Activating an BOM Line is not allowed");
		if (!flag && is_ValueChanged("M_Product_ID"))
			throw new AdempiereException("Changing Product is not allowed");
		if (getLine() == 0) {
			int j;
			//OpenUp. INes Fernandez. 26/05/2015. Contempla caso prod de transformacion. Issue #4126
			if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID())){
				String s = "SELECT COALESCE(MAX(Line),0)+10 FROM PP_Order_BOMLine WHERE UY_ProdTransf_ID=?";
				j = DB.getSQLValueEx(get_TrxName(), s, new Object[] { Integer.valueOf(get_ValueAsInt("UY_ProdTransf_ID")) });
			}
			else{
				String s = "SELECT COALESCE(MAX(Line),0)+10 FROM PP_Order_BOMLine WHERE PP_Order_ID=?";
				j = DB.getSQLValueEx(get_TrxName(), s, new Object[] { Integer.valueOf(getPP_Order_ID()) });
			}
			
			setLine(j);
		}
		if (flag && "PH".equals(getComponentType())) {
			m_qtyRequiredPhantom = getQtyRequiered();
			m_isExplodePhantom = true;
			setQtyRequiered(Env.ZERO);
		}
		if (flag || is_ValueChanged("C_UOM_ID") || is_ValueChanged("QtyEntered") || is_ValueChanged("QtyRequiered")) {
			int i = MUOM.getPrecision(getCtx(), getC_UOM_ID());
			setQtyEntered(getQtyEntered().setScale(i, RoundingMode.UP));
			setQtyRequiered(getQtyRequiered().setScale(i, RoundingMode.UP));
		}
		if (is_ValueChanged("QtyDelivered") || is_ValueChanged("QtyRequiered")) {

			reserveStock();

		}
		
		return true;
	}

	protected boolean afterSave(boolean flag, boolean flag1) {
		if (!flag1) {
			return false;
		} else {
			explodePhantom();
			return true;
		}
	}

	protected boolean beforeDelete() {
		setQtyRequiered(Env.ZERO);

		reserveStock();

		return true;
	}

	private void explodePhantom() {
		
		if (m_isExplodePhantom && m_qtyRequiredPhantom != null) {
			MProduct mproduct = MProduct.get(getCtx(), getM_Product_ID());
			int i = MPPProductBOM.getBOMSearchKey(mproduct);
			if (i <= 0)
				return;
			MPPProductBOM mppproductbom = MPPProductBOM.get(getCtx(), i);
			if (mppproductbom != null) {
				MPPProductBOMLine amppproductbomline[] = mppproductbom.getLines();
				int j = amppproductbomline.length;
				for (int k = 0; k < j; k++) {
					MPPProductBOMLine mppproductbomline = amppproductbomline[k];
					MPPOrderBOMLine mpporderbomline= null;
					//OpenUp. INes Fernandez. 26/05/2015. Contempla caso prod de transformacion. Issue #4126
					if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID(), getAD_Org_ID())){
						mpporderbomline = new MPPOrderBOMLine(mppproductbomline, get_ValueAsInt("UY_ProdTransf_ID"),
								getPP_Order_BOM_ID(), getM_Warehouse_ID(), get_TrxName());
					}
					else{
						mpporderbomline = new MPPOrderBOMLine(mppproductbomline, getPP_Order_ID(), 
								getPP_Order_BOM_ID(), getM_Warehouse_ID(), get_TrxName());
					}
					mpporderbomline.setAD_Org_ID(getAD_Org_ID());
					mpporderbomline.setQtyOrdered(m_qtyRequiredPhantom);
					mpporderbomline.saveEx();
				}

			}
			m_isExplodePhantom = false;
		}
	}

	public MProduct getM_Product() {
		return MProduct.get(getCtx(), getM_Product_ID());
	}

	public MUOM getC_UOM() {
		return MUOM.get(getCtx(), getC_UOM_ID());
	}

	public MWarehouse getM_Warehouse() {
		return MWarehouse.get(getCtx(), getM_Warehouse_ID());
	}

	public BigDecimal getQtyRequiredPhantom() {
		return m_qtyRequiredPhantom == null ? Env.ZERO : m_qtyRequiredPhantom;
	}

	public MPPOrder getParent() {
		int i;
		//OpenUp. INes Fernandez. 26/05/2015. Contempla caso prod de transformacion. Issue #4126
		if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID(), getAD_Org_ID())){
			i= get_ValueAsInt("UY_ProdTransf_ID");
		}
		else i= getPP_Order_ID();
		
		if (i <= 0) {
			m_parent = null;
			return null;
		}
		if (m_parent == null || m_parent.get_ID() != i)
			m_parent = new MPPOrder(getCtx(), i, get_TrxName());
		return m_parent;
	}

	public int getPrecision() {
		return MUOM.getPrecision(getCtx(), getC_UOM_ID());
	}

	public BigDecimal getQtyMultiplier() {
		BigDecimal bigdecimal;
		if (isQtyPercentage())
			bigdecimal = getQtyBatch().divide(Env.ONEHUNDRED, 8, RoundingMode.HALF_UP);
		else
			bigdecimal = getQtyBOM();
		return bigdecimal;
	}

	public void setQtyOrdered(BigDecimal bigdecimal) {
		BigDecimal bigdecimal1 = getQtyMultiplier();
		BigDecimal bigdecimal2 = bigdecimal.multiply(bigdecimal1).setScale(8, RoundingMode.UP);
		if (isComponentType(new String[] { "CO", "PH", "PK", "BY", "CP" }))
			setQtyRequiered(bigdecimal2);
		else if (isComponentType(new String[] { "TL" }))
			setQtyRequiered(bigdecimal1);
		else
			throw new AdempiereException((new StringBuilder()).append("@NotSupported@ @ComponentType@ ").append(getComponentType()).toString());
		BigDecimal bigdecimal3 = getScrap();
		if (bigdecimal3.signum() != 0) {
			bigdecimal3 = bigdecimal3.divide(Env.ONEHUNDRED, 8, 0);
			setQtyRequiered(getQtyRequiered().divide(Env.ONE.subtract(bigdecimal3), 8, 4));
		}
	}

	public void setQtyRequiered(BigDecimal bigdecimal) {
		if (bigdecimal != null && getC_UOM_ID() != 0) {
			int i = getPrecision();
			bigdecimal = bigdecimal.setScale(i, RoundingMode.HALF_UP);
		}
		super.setQtyRequiered(bigdecimal);
	}

	public void setQtyReserved(BigDecimal bigdecimal) {
		if (bigdecimal != null && getC_UOM_ID() != 0) {
			int i = getPrecision();
			bigdecimal = bigdecimal.setScale(i, RoundingMode.HALF_UP);
		}
		super.setQtyReserved(bigdecimal);
	}

	public BigDecimal getQtyOpen() {
		return getQtyRequiered().subtract(getQtyDelivered());
	}

	private void loadStorage(boolean flag)
    {
        org.compiere.util.CPreparedStatement cpreparedstatement;
        ResultSet resultset;
        if(!flag && m_qtyOnHand != null && m_qtyAvailable != null)
            return;
        cpreparedstatement = null;
        resultset = null;
        try
        {
            cpreparedstatement = DB.prepareStatement("SELECT  bomQtyAvailable(M_Product_ID, M_Warehouse_ID, 0),bomQtyOnHand(M_Product_ID, M_Warehouse_ID, 0) FROM PP_Order_BOMLine WHERE PP_Order_BOMLine_ID=?", get_TrxName());
            DB.setParameters(cpreparedstatement, new Object[] {
                Integer.valueOf(get_ID())
            });
            resultset = cpreparedstatement.executeQuery();
            if(resultset.next())
            {
                m_qtyAvailable = resultset.getBigDecimal(1);
                m_qtyOnHand = resultset.getBigDecimal(2);
            }
        }
        catch(SQLException sqlexception)
        {
            throw new DBException(sqlexception, "SELECT  bomQtyAvailable(M_Product_ID, M_Warehouse_ID, 0),bomQtyOnHand(M_Product_ID, M_Warehouse_ID, 0) FROM PP_Order_BOMLine WHERE PP_Order_BOMLine_ID=?");
        }
        DB.close(resultset, cpreparedstatement);
        resultset = null;
        cpreparedstatement = null;

    }

	public BigDecimal getQtyAvailable() {
		loadStorage(false);
		return m_qtyAvailable;
	}

	public BigDecimal getQtyVariance() {
		BigDecimal bigdecimal; 
		//OpenUp. INes Fernandez. 26/05/2015. Contempla caso prod de transformacion. Issue #4126
		if(MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID())){
			 bigdecimal = (new Query(getCtx(), "PP_Cost_Collector", "PP_Order_BOMLine_ID=? AND UY_ProdTransf_ID=? "
			 		+ "AND DocStatus IN (?,?) AND CostCollectorType=?", get_TrxName()))
			 		.setParameters( new Object[] { Integer.valueOf(getPP_Order_BOMLine_ID()), get_ValueAsInt("UY_ProdTransf_ID")/*Integer.valueOf(getPP_Order_ID())*/, "CO", "CL", "120" })
			 		.sum("MovementQty");
		}
		else{
			bigdecimal = (new Query(getCtx(), "PP_Cost_Collector", "PP_Order_BOMLine_ID=? AND PP_Order_ID=? "
					+ "AND DocStatus IN (?,?) AND CostCollectorType=?", get_TrxName()))
					.setParameters(new Object[] { Integer.valueOf(getPP_Order_BOMLine_ID()), Integer.valueOf(getPP_Order_ID()), "CO", "CL", "120" })
					.sum("MovementQty");
		}
		return bigdecimal;
	}

	public BigDecimal getQtyOnHand() {
		loadStorage(false);
		return m_qtyOnHand;
	}

	public boolean isComponentType(String as[]) {
		String s = getComponentType();
		String as1[] = as;
		int i = as1.length;
		for (int j = 0; j < i; j++) {
			String s1 = as1[j];
			if (s.equals(s1))
				return true;
		}

		return false;
	}

	public boolean isCoProduct() {
		return isComponentType(new String[] { "CP" });
	}

	public boolean isByProduct() {
		return isComponentType(new String[] { "BY" });
	}

	public boolean isComponent() {
		return isComponentType(new String[] { "CO", "PK" });
	}

	public void addDescription(String s) {
		String s1 = getDescription();
		if (s1 == null)
			setDescription(s);
		else
			setDescription((new StringBuilder()).append(s1).append(" | ").append(s).toString());
	}

	private void setDefault() {
		setDescription("");
		setQtyDelivered(Env.ZERO);
		setQtyPost(Env.ZERO);
		setQtyReject(Env.ZERO);
		setQtyRequiered(Env.ZERO);
		setQtyReserved(Env.ZERO);
		setQtyScrap(Env.ZERO);
	}

	protected void reserveStock() {

		// OpenUp. Nicolas Garcia. 17/08/2011. Issue #824.
		// int i = getParent().getM_Warehouse_ID();
		// if (i != 0) {
		// if (i != getM_Warehouse_ID())
		// setM_Warehouse_ID(i);
		// if (getAD_Org_ID() != getAD_Org_ID())
		// setAD_Org_ID(getAD_Org_ID());
		// }
		// Fin Issue #824

		BigDecimal bigdecimal = getQtyRequiered();
		BigDecimal bigdecimal1 = bigdecimal.subtract(getQtyReserved()).subtract(getQtyDelivered());
		log.fine((new StringBuilder()).append("Line=").append(getLine()).append(" - Target=").append(bigdecimal).append(",Difference=").append(bigdecimal1)
				.append(" - Requiered=").append(getQtyRequiered()).append(",Reserved=").append(getQtyReserved()).append(",Delivered=")
				.append(getQtyDelivered()).toString());
		if (bigdecimal1.signum() == 0)
			return;
		MProduct mproduct = getM_Product();
		if (!mproduct.isStocked())
			return;
		

		// Openup. Gabriel Vila. 07/03/2011. Comento llamada a MStorage ya que usamos modelo de stock de OpenUp.
		// Dejo solo la actualizacion del campo reserved.

		/*
		BigDecimal bigdecimal2 = bigdecimal1;
		int j = getM_Locator_ID(bigdecimal2);

		if (!MStorage.add(getCtx(), getM_Warehouse_ID(), j, getM_Product_ID(), getM_AttributeSetInstance_ID(), getM_AttributeSetInstance_ID(), Env.ZERO,
				bigdecimal2, Env.ZERO, get_TrxName())) {
			throw new AdempiereException();
		} else {
			setQtyReserved(getQtyReserved().add(bigdecimal1));
			return;
		}
		*/
		setQtyReserved(getQtyReserved().add(bigdecimal1));
		return;
		// Fin OpenUp.
		
		
	}

	@SuppressWarnings("unused")
	private int getM_Locator_ID(BigDecimal bigdecimal) {
		int i = 0;
		int j = getM_AttributeSetInstance_ID();
		if (j != 0)
			i = MStorage.getM_Locator_ID(getM_Warehouse_ID(), getM_Product_ID(), j, bigdecimal, get_TrxName());
		if (i == 0)
			i = getM_Locator_ID();
		if (i == 0) {
			MLocator mlocator = MWarehouse.get(getCtx(), getM_Warehouse_ID()).getDefaultLocator();
			if (mlocator != null)
				i = mlocator.get_ID();
		}
		return i;
	}

	public String toString() {
		return (new StringBuilder()).append(getClass().getSimpleName()).append("[").append(get_ID()).append(", Product=").append(getM_Product_ID()).append(
				", ComponentType=").append(getComponentType()).append(",QtyBatch=").append(getQtyBatch()).append(",QtyRequiered=").append(getQtyRequiered())
				.append(",QtyScrap=").append(getQtyScrap()).append("]").toString();
	}



	private static final long	serialVersionUID	= 1L;
	private MPPOrder			m_parent;
	private boolean				m_isExplodePhantom;
	private BigDecimal			m_qtyRequiredPhantom;
	private BigDecimal			m_qtyOnHand;
	private BigDecimal			m_qtyAvailable;
}
