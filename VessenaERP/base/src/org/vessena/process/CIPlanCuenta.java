/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 16/12/2013
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MElement;
import org.compiere.model.MElementValue;
import org.compiere.model.MTree_Base;
import org.compiere.model.MTree_Node;
import org.compiere.model.X_C_ElementValue;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * org.openup.process - CIPlanCuenta
 * OpenUp Ltda. Issue #1722 
 * Description: Carga inicial de arbol de cuentas para determinado elemento y esquema contable.
 * @author Gabriel Vila - 16/12/2013
 * @see
 */
public class CIPlanCuenta extends SvrProcess {

	private MElement element = null;
	private int cElementID = 0;
	private boolean isLoad = false;
	
	/**
	 * Constructor.
	 */
	public CIPlanCuenta() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/12/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("C_Element_ID")){
					this.cElementID = ((BigDecimal)para[i].getParameter()).intValueExact();
					this.element = new MElement(getCtx(), cElementID, get_TrxName());
				}
				if (name.equalsIgnoreCase("IsLoad")){
					this.isLoad = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;					
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/12/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		if (this.isLoad){
			this.loadAccounts();
		}
		else{
			this.orderAccounts();
		}
			
		
		return "OK";
	}

	private void orderAccounts() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			
			// Elimino nodos de arbol
			DB.executeUpdateEx(" delete from ad_treenode cascade where ad_tree_id =" + element.getAD_Tree_ID(), get_TrxName());
			
			MTree_Base tree = new MTree_Base(getCtx(), element.getAD_Tree_ID(), get_TrxName());
			
			sql = " select * from c_elementvalue " +
				  " where c_element_id =" + element.get_ID() +
				  " order by parent_id, c_elementvalue_id "; 
				
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();
			
			int parentIDAux = -1; int seqNo = 1;
			
			while (rs.next()) {
				
				if (rs.getInt("parent_id") != parentIDAux){
					parentIDAux = rs.getInt("parent_id");
					seqNo = 1;
				}
				
				MTree_Node tn = new MTree_Node(tree, rs.getInt("c_elementvalue_id"));
				tn.setParent_ID(parentIDAux);
				tn.setSeqNo(seqNo);
				tn.saveEx();
				
				seqNo++;
				
			}

			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());

		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}

	/***
	 * Carga cuentas en tabla C_ElementValue
	 * OpenUp Ltda. Issue #1722 
	 * @author Gabriel Vila - 16/12/2013
	 * @see
	 */
	private void loadAccounts(){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			
			// Elimina por diccionario las cuentas contables actuales asociados con el elemento indicado por el usuario
			List<MElementValue> cuentas = element.getElementValues();
			
			if(cuentas != null){
				for (MElementValue cuenta: cuentas){
					cuenta.deleteEx(true);
				}
			}

			//String capitulo = null, capituloAux = null;
			
			sql = " select * from ci_cuenta order by capitulo, codcta "; 
				
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				boolean isSummary = (rs.getString("escapitulo").equalsIgnoreCase("Y")) ? true : false;
				
				MElementValue ev = new MElementValue(getCtx(), 0, get_TrxName());
				ev.setAD_Client_ID(this.getAD_Client_ID());
				ev.setAD_Org_ID(0);
				ev.setValue(rs.getString("codcta"));
				ev.setName(rs.getString("nomcta"));
				ev.setDescription(ev.getName());
				ev.setAccountType(rs.getString("tipocta"));
				ev.setAccountSign(X_C_ElementValue.ACCOUNTSIGN_Natural);
				ev.setIsDocControlled(false);
				ev.setC_Element_ID(element.get_ID());
				ev.setIsSummary(isSummary);
				ev.setPostActual(true);
				ev.setPostBudget(true);
				ev.setPostEncumbrance(true);
				ev.setPostStatistical(true);
				ev.setIsBankAccount(false);
				ev.setIsForeignCurrency(false);
				ev.setAlias(ev.getValue());
				ev.setIsProvisionable(false);
				
				if (rs.getString("capitulo") != null){
					if (!rs.getString("capitulo").equalsIgnoreCase("0")){
						
						MElementValue evParent = MElementValue.forLastValue(getCtx(), rs.getString("capitulo"), get_TrxName());
						if (evParent != null){
							if (evParent.get_ID() > 0){
								ev.set_ValueOfColumn("Parent_ID", evParent.get_ID());		
							}
						}
					}
					else{
						ev.set_ValueOfColumn("Parent_ID", 0);
					}
				}
				
				ev.saveEx();
			}

			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());

		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}
}
