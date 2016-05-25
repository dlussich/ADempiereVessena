/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/12/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTab;
import org.compiere.model.MPeriod;
import org.compiere.model.Query;

/**
 * org.openup.model - MGerencialMain
 * OpenUp Ltda. Issue #116 
 * Description: Totales Primer Nivel en Informe Gerencial Navegable.
 * @author Gabriel Vila - 19/12/2012
 * @see
 */
public class MGerencialMain extends X_UY_Gerencial_Main implements IDynamicHeader {

	private static final long serialVersionUID = -2623974996134901633L;

	public HashMap<Integer, String> hashPosicionXPeriodo = new HashMap<Integer, String>();
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_Main_ID
	 * @param trxName
	 */
	public MGerencialMain(Properties ctx, int UY_Gerencial_Main_ID,
			String trxName) {
		super(ctx, UY_Gerencial_Main_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialMain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna modelo segun id de cuenta informe recibida.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param ctx
	 * @param uyGerencialID 
	 * @param accountID
	 * @param trxName
	 * @return
	 */
	public static MGerencialMain forAccountID(Properties ctx, int uyGerencialID, int accountID, String trxName) {

		String whereClause = X_UY_Gerencial_Main.COLUMNNAME_UY_Gerencial_ID + "=" + uyGerencialID +
							" AND " + X_UY_Gerencial_Main.COLUMNNAME_C_ElementValue_ID + "=" + accountID;
		
		MGerencialMain germain = new Query(ctx, I_UY_Gerencial_Main.Table_Name, whereClause, trxName)
		.first();
		
		return germain;
	}

	@Override
	public void setFieldHeaderText(GridFieldVO fieldVO) {
		return;
	}

	@Override
	public void setFielsdHeaderText(GridTab gTab) {

		try {
			if (!this.setPeriodos()) return;
			
			GridField[] fields = gTab.getFields();
			
			for (int i = 0; i < fields.length; i++){
				GridField field = fields[i];
				
				if (field.getVO().ColumnName.equalsIgnoreCase("amt1")){
					
					if (hashPosicionXPeriodo.get(1) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(1).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}
					//field.updateContext();
				}
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt2")){
					if (hashPosicionXPeriodo.get(2) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(2).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}
				}
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt3")){
					if (hashPosicionXPeriodo.get(3) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(3).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt4")){
					if (hashPosicionXPeriodo.get(4) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(4).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt5")){
					if (hashPosicionXPeriodo.get(5) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(5).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt6")){
					if (hashPosicionXPeriodo.get(6) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(6).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt7")){
					if (hashPosicionXPeriodo.get(7) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(7).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt8")){
					if (hashPosicionXPeriodo.get(8) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(8).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt9")){
					if (hashPosicionXPeriodo.get(9) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(9).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt10")){
					if (hashPosicionXPeriodo.get(10) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(10).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt11")){
					if (hashPosicionXPeriodo.get(11) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(11).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt12")){
					if (hashPosicionXPeriodo.get(12) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(12).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}			
				else if (field.getVO().ColumnName.equalsIgnoreCase("amt13")){
					if (hashPosicionXPeriodo.get(13) != null){
						field.getVO().Header = hashPosicionXPeriodo.get(13).toString().toUpperCase();	
					}
					else{
						field.getVO().Header ="";
					}					
				}
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	
	/***
	 * Setea posiciones por periodo.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 27/11/2012
	 * @see
	 */
	private boolean setPeriodos(){

		try {

			MGerencial gerencial = (MGerencial)this.getUY_Gerencial();
			if ((gerencial == null) || (gerencial.get_ID() <= 0)) return false;
			
			int posicion = 0;
			for (int i = gerencial.getC_Period_ID_From(); i <= gerencial.getC_Period_ID_To(); i++){
				posicion++;
				MPeriod period = new MPeriod(getCtx(), i, null);
				hashPosicionXPeriodo.put(posicion, period.getName().toUpperCase());
			}
			
			// Si tengo mas de 13 periodos aviso con exception
			if (posicion > 13){
				throw new AdempiereException("El período posible a considerar NO puede ser mayor a un año");
			}
			
			return true;
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

}
