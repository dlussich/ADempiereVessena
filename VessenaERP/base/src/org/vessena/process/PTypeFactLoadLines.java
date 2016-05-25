/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 23/11/2014
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.model.MTax;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFactWildCard;
import org.openup.model.MTypeFact;
import org.openup.model.MTypeFactLine;
import org.openup.model.X_UY_DocTypeFact;

import com.fathzer.soft.javaluator.DoubleEvaluator;

/**
 * org.openup.process - PTypeFactLoadLines
 * OpenUp Ltda. Issue #3315 
 * Description: Carga cuentas calculadas en documento de registracion de asiento tipo.
 * @author Gabriel Vila - 23/11/2014
 * @see
 */
public class PTypeFactLoadLines extends SvrProcess {

	private MTypeFact typeFact = null; 
	
	/**
	 * Constructor.
	 */
	public PTypeFactLoadLines() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 23/11/2014
	 * @see
	 */
	@Override
	protected void prepare() {

		this.typeFact = new MTypeFact(getCtx(), this.getRecord_ID(), null);
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 23/11/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			DB.executeUpdateEx(" delete from uy_typefactline where uy_typefact_id =" + this.typeFact.get_ID() + " and IsCalculated='Y' ", null);
			
			// Porcentajes de Impuestos basico y minimo
			MTax basico = MTax.forValue(getCtx(), "basico", null);
			MTax minimo = MTax.forValue(getCtx(), "minimo", null);
			
			// Tasa de cambio
			BigDecimal tc = this.typeFact.getCurrencyRate();
			if (tc == null) tc = Env.ONE;
			else if (tc == Env.ZERO) tc = Env.ONE;
			
			// Lista de comodines de asiento tipo que no son del sistema
			List<MFactWildCard> wcards = MDocType.getTypeFactWildCards(getCtx(), null);
			
			sql = " select * from uy_doctypefact " +
				  " where c_doctype_id =? " +
				  " and calculate is not null " +
				  " and isactive='Y' " +
				  " order by value ";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.typeFact.getC_DocType_ID_2());
			
			rs = pstmt.executeQuery();
			
			// Recorro cada cuenta calculada
			while (rs.next()){

				// Calculo monto de esta cuenta
				
				String calculo = rs.getString(X_UY_DocTypeFact.COLUMNNAME_Calculate);
				if (calculo != null){
					// Reemplazo comodines del sistema
					calculo = calculo.replaceAll("@IVA_BASICO@", basico.getRate().toString().trim());
					calculo = calculo.replaceAll("@IVA_MIN@", minimo.getRate().toString().trim());
					calculo = calculo.replaceAll("@TC@", tc.toString().trim());
					
					// Reemplazo comodines que no son del sistema
					for (MFactWildCard wcard: wcards){
						calculo = calculo.replaceAll("@" + wcard.getValue() + "@", wcard.getAmt().toString().trim());
					}
					
					// Reemplazo montos de codigos de fila que NO MANEJAN SOCIO DE NEGOCIO
					calculo = this.setRowValues(calculo, rs.getString("value").trim());

					// Proceso el calculo considerando que puede tener cuentas que manejan socio de negocio y por lo tanto explotan por socio
					if (!this.setAperturaCalculo(calculo, rs.getString("value").trim(), rs.getInt("c_elementvalue_id"), rs.getString("isdebit"), 
							rs.getString("calculate"), rs.getInt("uy_doctypefact_id"))){
						
						// Si no proceso nada por apertura, proceso el calculo como venia
						this.processCalculo(calculo, rs.getString("value").trim(), rs.getInt("c_elementvalue_id"), rs.getString("isdebit"), 
								rs.getString("calculate"), rs.getInt("uy_doctypefact_id"), 0);
						
					}
					
					
				}
			}
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return "OK";
		
	}

	
	/***
	 * Reemplaza montos totales de codigos de fila en este calculo automatico de cuenta. 
	 * OpenUp Ltda. Issue #3315 
	 * @author Gabriel Vila - 23/11/2014
	 * @see
	 * @param calculo
	 * @param value
	 * @return String
	 */
	private String setRowValues(String calculo, String value) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String retorno = calculo;
		
		try{
			
			sql = " select a.* " +
				  " from ctb_typefact_sumvalue a " +
				  " inner join uy_typefact hdr on a.uy_typefact_id = hdr.uy_typefact_id " +
				  " inner join uy_doctypefact doctf on (hdr.c_doctype_id_2 = doctf.c_doctype_id and a.value = doctf.value) " +
				  " inner join c_elementvalue ev on doctf.c_elementvalue_id = ev.c_elementvalue_id " +
				  " where ev.managebpartner='N' " +
				  " and a.uy_typefact_id =? " +
				  " and a.ad_client_id =? " +
				  " and a.value <>? "; 
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.typeFact.get_ID());
			pstmt.setInt(2, this.typeFact.getAD_Client_ID());
			pstmt.setString(3, value);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				// Reemplazo monto para este codigo de fila
				calculo = calculo.replaceAll("@" + rs.getString("value")  + "@", rs.getBigDecimal("totallines").setScale(2, RoundingMode.HALF_UP).toString().trim());
			}
			
			retorno = calculo;
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return retorno;
		
	}


	/***
	 * Procesa el calculo considerando posibles aperturas como por ejemplo socio de negocio. 
	 * OpenUp Ltda. Issue #3315 
	 * @author Gabriel Vila - 23/11/2014
	 * @see
	 * @param calculo
	 * @param value
	 * @return String
	 */
	private boolean setAperturaCalculo(String calculo, String value, int cElementValueID, String debit, String calculate, int uyDocTypeFactID) {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean retorno = false;
		
		try{
			
			sql = " select a.* " +
				  " from ctb_typefact_sumvalue_bp a " +
				  " inner join uy_typefact hdr on a.uy_typefact_id = hdr.uy_typefact_id " +
				  " inner join uy_doctypefact doctf on (hdr.c_doctype_id_2 = doctf.c_doctype_id and a.value = doctf.value) " +
				  " inner join c_elementvalue ev on doctf.c_elementvalue_id = ev.c_elementvalue_id " +
				  " where ev.managebpartner='Y' " +
				  " and a.uy_typefact_id =? " +
				  " and a.ad_client_id =? " +
				  " and a.value <>? " +
				  " order by a.c_bpartner_id, a.value ";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, this.typeFact.get_ID());
			pstmt.setInt(2, this.typeFact.getAD_Client_ID());
			pstmt.setString(3, value);
			
			rs = pstmt.executeQuery();

			String calculoBP = calculo;
			int cBPartnerIDAux = 0 ;
			HashMap<String, Integer> hashValues = new HashMap<String, Integer>();
			
			while (rs.next()){
				
				retorno = true;
				
				// Corte de control por socio de negocio
				if (rs.getInt("c_bpartner_id") != cBPartnerIDAux){
					if (cBPartnerIDAux > 0){
						// Proceso el calculo para el socio de negocio anterio.
						this.processCalculo(calculoBP, value, cElementValueID, debit, calculate, uyDocTypeFactID, cBPartnerIDAux);
						calculoBP = calculo;
					}
					cBPartnerIDAux = rs.getInt("c_bpartner_id");
				}
				
				String keyHash = rs.getString("value").trim() + "_" + String.valueOf(cBPartnerIDAux).trim(); 
				
				// Reemplazo monto para este codigo de fila
				if (!hashValues.containsKey(keyHash)){
					if (calculo.contains("@" + rs.getString("value")  + "@")){
						calculoBP = calculoBP.replaceAll("@" + rs.getString("value")  + "@", rs.getBigDecimal("totallines").setScale(2, RoundingMode.HALF_UP).toString().trim());
					}
					hashValues.put(keyHash,1);
				}
				
			}

			// Proceso ultimo socio de negocio
			if (cBPartnerIDAux > 0){
				this.processCalculo(calculoBP, value, cElementValueID, debit, calculate, uyDocTypeFactID, cBPartnerIDAux);
				calculoBP = calculo;
				
			}
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return retorno;
		
	}

	
	/***
	 * Procesa calculo.
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 17/04/2015
	 * @see
	 * @param calculo
	 * @param value
	 * @param cElementValueID
	 * @param debit
	 * @param calculate
	 * @param uyDocTypeFactID
	 */
	private void processCalculo(String calculo, String value, int cElementValueID, String debit, String calculate, int uyDocTypeFactID, int cBPartnerID){
		
		try {

			// Ahora que tengo el string del calculo con valores, obtengo el resultado del mismo.
			Double result = new DoubleEvaluator().evaluate(calculo);					
			BigDecimal amt = BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_UP);

			if (amt != null){

				// Modelo de cuenta contable 
				MElementValue ev = new MElementValue(getCtx(), cElementValueID, null);
				
				// Intento obtener cuenta asociada a este calculo ya existente como linea de asiento
				MTypeFactLine tfline = null;
				if (cBPartnerID <= 0){
					tfline = this.typeFact.getLineForValue(value);
				}
				else{
					tfline = this.typeFact.getLineForValueBP(value, cBPartnerID);
				}
				
				// Si ya existe actualizo monto y si no existe la creo ahora.
				if (tfline != null){
					tfline.setAmt(amt);
				}
				else{
					tfline = new MTypeFactLine(getCtx(), 0, get_TrxName());
					tfline.setUY_TypeFact_ID(this.typeFact.get_ID());
					tfline.setC_ElementValue_ID(cElementValueID);
					tfline.setIsDebit((debit.equalsIgnoreCase("Y")) ? true : false);
					tfline.setAmt(amt);
					tfline.setValue(value);
					tfline.setCalculate(calculate);
					tfline.setIsCalculated(true);
					
					if (cBPartnerID > 0){
						tfline.setC_BPartner_ID(cBPartnerID);	
					}
					
					tfline.setUY_DocTypeFact_ID(uyDocTypeFactID);
				}
				
				tfline.setManageBPartner(ev.get_ValueAsBoolean("ManageBPartner"));
				tfline.setManageDocument(ev.get_ValueAsBoolean("ManageDocument"));
				tfline.setManageDateTrx(ev.get_ValueAsBoolean("ManageDateTrx"));
				tfline.setManageDueDate(ev.get_ValueAsBoolean("ManageDueDate"));
				
				tfline.saveEx();
			}
			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
}
