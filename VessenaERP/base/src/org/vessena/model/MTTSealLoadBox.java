/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTTSealLoadBox extends X_UY_TT_SealLoadBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167854504875836683L;

	/**
	 * @param ctx
	 * @param UY_TT_SealLoadBox_ID
	 * @param trxName
	 */
	public MTTSealLoadBox(Properties ctx, int UY_TT_SealLoadBox_ID,
			String trxName) {
		super(ctx, UY_TT_SealLoadBox_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTSealLoadBox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected boolean beforeDelete() {
		
		((MTTBox) this.getUY_TT_Box()).setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
		return true;
	}

	/***
	 * Método que devuelve un modelo de la MTTBox, a partir de un UY_TT_SealLoad_ID y un UY_TT_Box_ID.
	 * Esto es para comprobar que la caja es usada a una carga de bolsin.
	 * OpenUp Ltda. Issue #1256
	 * @author Guillermo Brust - 30/08/2013
	 * @see
	 * @return
	 */
	public static boolean existsBoxForSealLoad(Properties ctx, String trxName, int UY_TT_SealLoad_ID, int UY_TT_Box_ID){
				
		String whereClause = X_UY_TT_SealLoadBox.COLUMNNAME_UY_TT_SealLoad_ID + " = " + UY_TT_SealLoad_ID + "" +							
							" AND " + X_UY_TT_SealLoadBox.COLUMNNAME_UY_TT_Box_ID + " = " + UY_TT_Box_ID;
		
		MTTSealLoadBox model = new Query(ctx, I_UY_TT_SealLoadBox.Table_Name, whereClause, trxName)
		.first();
		
		
		return model != null;
	}
	
	
	/***
	 * Método que devuelve un modelo de la MTTBox, a partir de un UY_TT_SealLoad_ID, siempre y cuando este en estado CERRADO o en CARGA DE BOLSIN y que sea para RETENER.
	 * OpenUp Ltda. Issue #1256
	 * @author Guillermo Brust - 02/09/2013
	 * @see
	 * @return
	 */
	public static MTTBox getBoxRetainedCloseAndEnCarga(Properties ctx, String trxName, int UY_TT_SealLoad_ID, String retainedStatus){
				
		String whereClause = X_UY_TT_SealLoadBox.COLUMNNAME_UY_TT_SealLoad_ID + " = " + UY_TT_SealLoad_ID + "" +							
							" AND " + X_UY_TT_SealLoadBox.COLUMNNAME_IsRetained + " = 'Y' " +
							((retainedStatus != null) ? " AND " + X_UY_TT_SealLoadBox.COLUMNNAME_RetainedStatus + "='" + retainedStatus + "'" : "");
		
		MTTSealLoadBox model = new Query(ctx, I_UY_TT_SealLoadBox.Table_Name, whereClause, trxName)
		.first();
		
		if(model != null){
			MTTBox box = (MTTBox) model.getUY_TT_Box();
			if(box.getBoxStatus().equals(X_UY_TT_Box.BOXSTATUS_Cerrado) || box.getBoxStatus().equals(X_UY_TT_Box.BOXSTATUS_CargaBolsin)) return box;
			else return null;
			
		}else return null;		
		
	}

	@Override
	protected boolean beforeSave(boolean newRecord) throws AdempiereException{
		
		//Primero verifico que ya no exista la caja guardada en esta grilla
		if(existsBoxForSealLoad(this.getCtx(), this.get_TrxName(), this.getUY_TT_SealLoad_ID(), this.getUY_TT_Box_ID())){
			throw new AdempiereException("Ya existe esta caja ingresada en esta grilla");
		}
		
		MTTBox box = (MTTBox) this.getUY_TT_Box();
		
		// Verifico que esta caja no este siendo utilizada en otro proceso que no sea carga de bolsin o que este cerrada
		if (box.getBoxStatus() != null){
			if ( (!box.getBoxStatus().equalsIgnoreCase(X_UY_TT_Box.BOXSTATUS_CargaBolsin))
					&& (!box.getBoxStatus().equalsIgnoreCase(X_UY_TT_Box.BOXSTATUS_Cerrado))
					&& (!box.getBoxStatus().equalsIgnoreCase(X_UY_TT_Box.BOXSTATUS_Nuevo))){
				
				// Si esta siendo utlizada en una recepcion de cuentas
				if (box.getBoxStatus().equalsIgnoreCase(X_UY_TT_Box.BOXSTATUS_RecepcionCuentas)){
					throw new AdempiereException(" La Caja seleccionada esta siendo utlizada en una RECEPCION de CUENTAS.\n" +
												 " Debe COMPLETAR la RECEPCION antes de utilizar la Caja en una Carga de Bolsin.");
				}
				else if (box.getBoxStatus().equalsIgnoreCase(X_UY_TT_Box.BOXSTATUS_Reubicacion)){
					throw new AdempiereException(" La Caja seleccionada esta siendo utlizada en un proceso de REUBICACION.\n" +
							 " Debe COMPLETAR la REUBICACION antes de utilizar la Caja en una Carga de Bolsin.");
				}

			}
		}
		
		
		//Acá tengo que verificar que si quieren guardar una caja de retencion y ya existe una caja de RETENCIÓN en estado CERRADA y
		//con DISPONIBILIDAD no se deje guardar una nueva con las mismas caracteristicas.		
		
		//Esto es solo cuando quiero grabar una nueva linea de caja de retencion que necesito validar
		if(box.isRetained()){
						
			String whereClause = X_UY_TT_SealLoadBox.COLUMNNAME_UY_TT_SealLoad_ID + " = " + this.getUY_TT_SealLoad_ID() + "" +							
								" AND " + X_UY_TT_SealLoadBox.COLUMNNAME_IsRetained + " = 'Y'" +
								" AND " + X_UY_TT_SealLoadBox.COLUMNNAME_UY_TT_Box_ID + " = " + this.getUY_TT_Box_ID();							
								

			List<MTTSealLoadBox> cajasGrilla = new Query(this.getCtx(), I_UY_TT_SealLoadBox.Table_Name, whereClause, this.get_TrxName())
			.list();
			
			//Acá ya tengo una lista con las cajas de retencion en la grilla de cajas para esta carga de bolsin
			for (MTTSealLoadBox mttSealLoadBox : cajasGrilla) {
				
				//Acá verifico que si existe aunque sea una caja de retencion con disponibilidad no deje guardar una caja
				if(((MTTBox) mttSealLoadBox.getUY_TT_Box()).isComplete()) throw new AdempiereException("Ya existe una caja de Retención con disponibilidad de espacio para esta carga de bolsín");						
			}
		}
		
		//Acá dejo toda caja que se guarde en la grilla de cajas para esta carga, en estado CARGA DE BOLSIN
		box.setBoxStatus(MTTBox.BOXSTATUS_CargaBolsin);
		box.saveEx();
		
		return true;
	}
	/**
	 * 
	 * OpenUp Ltda. Issue # 
	 * @author leonardo.boccone - 22/09/2014
	 * @see	  Metodo que devuelve las cajas utilizadas en la carga de bolsin	
	 * @param ctx
	 * @param uy_TT_SealLoad_ID
	 * @param get_TrxName
	 * @return
	 */
	public static List<MTTSealLoadBox> forSealLoad(Properties ctx,int uy_TT_SealLoad_ID, String get_TrxName) {
		
		String whereClause = X_UY_TT_SealLoadBox.COLUMNNAME_UY_TT_SealLoad_ID + "=" + uy_TT_SealLoad_ID;
		
		List<MTTSealLoadBox> lines = new Query(ctx, I_UY_TT_SealLoadBox.Table_Name, whereClause, get_TrxName).list();
		
		return lines;
	}


}
