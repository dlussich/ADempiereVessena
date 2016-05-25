package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import org.compiere.model.MActivity;
import org.compiere.model.MMenu;
import org.compiere.model.MTree;
import org.compiere.model.MTree_Node;
import org.compiere.model.MTree_NodeMM;
import org.compiere.model.X_AD_Tree;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * 
 * OpenUp.
 * PSortMenu
 * Descripcion :Proceso creado para el caso issue #888 el cual ordena alfabeticamente el menu.
 * @author Nicolas Garcia
 * Fecha : 27/09/2011
 */

// org.openup.process.PSortMenu
public class PSortMenu extends SvrProcess {
	

	// ---------------Configuracion---------
	// ID de Carpeta Contenedora a ordenar (0 est todo)
	//private static int raiz = 0;
	private int raiz = 0;

	// Id del menu a ordenar
	//private static int menu = 10;
	private int adTreeID = 1000113;

	// ---------------Fin-------------------

	@Override
	protected String doIt() throws Exception {

		MTree base = new MTree(getCtx(), this.adTreeID, null);
		if (base.getTreeType().equalsIgnoreCase(X_AD_Tree.TREETYPE_Menu)){
			this.OrdenarHijos(raiz);	
		}
		else if (base.getTreeType().equalsIgnoreCase(X_AD_Tree.TREETYPE_Activity)){
			this.sortActivities(raiz);
		}
		
		return "ok";
	}

	
	private void sortActivities(int idPadre) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			MTree base = new MTree(getCtx(), this.adTreeID, null);
			HashMap<String, MTree_Node> carpetas = new HashMap<String, MTree_Node>();
			HashMap<String, MTree_Node> otros = new HashMap<String, MTree_Node>();

			// Obtengo las raices del menu.
			sql = "SELECT node_id FROM ad_treenode WHERE parent_id =" + idPadre + 
				  " AND ad_tree_id=" + this.adTreeID;

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				MTree_Node curNode = new MTree_Node(base, rs.getInt("node_id"));
				MActivity activity = new MActivity(getCtx(), curNode.getNode_ID(), null);

				// Si es una carpeta
				if (activity.isSummary()) {

					carpetas.put(activity.getValue(), curNode);
					this.sortActivities(curNode.getNode_ID());

				} else {
					otros.put(activity.getValue(), curNode);
				}

			}

			int acumulado = sortActivitiesFolders(0, carpetas);
			sortActivitiesFolders(acumulado, otros);

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	private int sortActivitiesFolders(int seqArranque, HashMap<String, MTree_Node> lista) {
		
		int acumulado = seqArranque;

		ArrayList<String> patients = new ArrayList<String>(lista.keySet());

		// Ordeno
		Collections.sort(patients);

		Iterator<String> i = patients.iterator();

		// Actualizo
		while (i.hasNext()) {

			acumulado = acumulado + 10;
			String actual = i.next();

			String sql = " UPDATE ad_treenode SET seqno=" + acumulado + "  WHERE node_id =" + lista.get(actual).getNode_ID() + 
						 " AND ad_tree_id=" + this.adTreeID;
			
			DB.executeUpdate(sql, get_TrxName());

		}
		return acumulado;
	}


	public void OrdenarHijos(int idPadre) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {

			MTree base = new MTree(getCtx(), this.adTreeID, null);
			HashMap<String, MTree_NodeMM> carpetas = new HashMap<String, MTree_NodeMM>();
			HashMap<String, MTree_NodeMM> otros = new HashMap<String, MTree_NodeMM>();

			// Obtengo las raices del menu.
			sql = "SELECT node_id FROM ad_treenodemm WHERE parent_id =" + idPadre + 
				  " AND ad_tree_id=" + this.adTreeID;

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			
			while (rs.next()) {

				MTree_NodeMM curNode = new MTree_NodeMM(base, rs.getInt("node_id"));
				MMenu curMenu = new MMenu(getCtx(), curNode.getNode_ID(), null);

				// Acumulados.put(curNode.getNode_ID(), curMenu);

				// Si es una carpeta
				if (curMenu.isSummary()) {

					carpetas.put(traduccion(curMenu), curNode);
					this.OrdenarHijos(curNode.getNode_ID());

				} else {

					otros.put(traduccion(curMenu), curNode);
				}

			}

			int acumulado = ordenarCarpeta(0, carpetas);
			ordenarCarpeta(acumulado, otros);

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}


	}

	private String traduccion(MMenu curMenu) {
		
		String salida = null;
		try {
			// Traduccion Mexico
			salida = curMenu.get_Translation(MMenu.COLUMNNAME_Name, "es_MX");

		} catch (Exception e) {

			try {

				// Traduccion uruguay
				salida = curMenu.get_Translation(MMenu.COLUMNNAME_Name, "es_UY");

			} catch (Exception e1) {

				// Nombre
				salida = curMenu.getName() + ";nombre";
			}
		}

		return salida;
	}

	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("AD_Tree_ID")) this.adTreeID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}
	}


	private int ordenarCarpeta(int seqArranque, HashMap<String, MTree_NodeMM> lista) {

		int acumulado = seqArranque;

		ArrayList<String> patients = new ArrayList<String>(lista.keySet());

		// Ordeno
		Collections.sort(patients);

		Iterator<String> i = patients.iterator();

		// Actualizo
		while (i.hasNext()) {

			acumulado = acumulado + 10;
			String actual = i.next();

			String sql = " UPDATE ad_treenodemm SET seqno=" + acumulado + "  WHERE node_id =" + lista.get(actual).getNode_ID() + 
						 " AND ad_tree_id=" + this.adTreeID;
			
			DB.executeUpdate(sql, get_TrxName());

		}
		return acumulado;
	}
}
