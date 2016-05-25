/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/10/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.Waiting;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPCustomerAcct;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MElementValue;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MProductAcct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.model.X_C_ElementValue;
import org.compiere.model.X_C_ValidCombination;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.util.OpenUpUtils;



/**
 * org.openup.model - MFduLoad
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 19/10/2012
 * @see
 */
public class MFduLoad extends X_UY_FduLoad {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4864063727189071163L;

	private Waiting waiting = null;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FduLoad_ID
	 * @param trxName
	 */
	public MFduLoad(Properties ctx, int UY_FduLoad_ID, String trxName) {
		super(ctx, UY_FduLoad_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduLoad(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}

	
	/***
	 * Setea documento por defecto.
	 * OpenUp Ltda. Issue #78 
	 * @author Gabriel Vila - 19/10/2012
	 * @see
	 */
	public void setDefaultDocTypeID(){
		//MDocType doc = MDocType.forValue(getCtx(), "fduload", null); 
		//Sylvie Bouissa 14-11-2014 Issue 2501 --> se detecta a partir de esta issue
		MDocType doc = MDocType.forValueAndSystem(getCtx(), "fduload", null);
		this.setC_DocType_ID(doc.get_ID());
	}
	
	/***
	 * Generacion de asientos diarios segun datos de esta carga desde FDU.
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 */
	public void generateJournals(String trxName){
		
		List<MJournalLine> journalLines = new ArrayList<MJournalLine>();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		Timestamp auxFecha = null, fechaAsiento = null;
		BigDecimal totalDebe = Env.ZERO, totalHaber = Env.ZERO;
		int lineNumber = 0;
		
		try{
				
			this.showHelp("Obteniendo lineas de carga...");
		
            			
			sql =" select fl.dateacct, fl.c_elementvalue_id as accountid , fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2,  "+
				 " fl.c_activity_id_3,fl.uy_fdu_grupocc_id,  																		  "+
		         " case when ev.issummary='Y' then (ev.name || ' - ' || cast(cod.value as varchar) || ' - ' || cod.name) 			  "+                  
		         " else fl.description                                                                                                "+     
		         " end as observacion, 'Y' as isdebit,coalesce(sum(amtsourcedr),0) as saldo                                           "+     
		         " from uy_fduloadline fl                                                                                             "+     
		         " inner join c_elementvalue ev on  fl.c_elementvalue_id = ev.c_elementvalue_id                                       "+     
		         " inner join uy_fducod cod on fl.uy_fducod_id = cod.uy_fducod_id                                                     "+     
		         " where fl.uy_fduload_id = ? and fl.uy_fducod_id <> 1000128 and fl.uy_fducod_id <> 1000127                     "+     
		         " group by fl.dateacct, fl.c_elementvalue_id, fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2,              "+     
		         " fl.c_activity_id_3, fl.uy_fdu_grupocc_id, observacion                                                              "+     
		         " union                                                                                                              "+     
		         " select fl.dateacct, fl.c_elementvalue_id_cr as accountid, fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2,"+     
		         " fl.c_activity_id_3,fl.uy_fdu_grupocc_id,                                                                           "+     
		         " case when ev.issummary='Y' then (ev.name || ' - ' || cast(cod.value as varchar) || ' - ' || cod.name)              "+     
		         " else fl.description                                                                                                "+     
		         " end as observacion, 'N' as isdebit,coalesce(sum(amtsourcecr),0) as saldo                                           "+     
		         " from uy_fduloadline fl                                                                                             "+     
		         " inner join c_elementvalue ev on  fl.c_elementvalue_id_cr = ev.c_elementvalue_id                                    "+     
		         " inner join uy_fducod cod on fl.uy_fducod_id = cod.uy_fducod_id                                                     "+     
		         " where fl.uy_fduload_id = ? and fl.uy_fducod_id <> 1000128 and fl.uy_fducod_id <> 1000127                     "+     
		         " group by fl.dateacct, fl.c_elementvalue_id_cr, fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2,           "+     
		         " fl.c_activity_id_3, fl.uy_fdu_grupocc_id, observacion                                                              "+     
		         " order by dateacct asc                                                                                              ";     
		          	                                                                                                                    
			                                                                                                                         
			/*                                                                                                                       
			sql = " select fl.dateacct, fl.c_elementvalue_id as accountid , fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2, " +
					  " fl.c_activity_id_3,fl.uy_fdu_grupocc_id, " +                                                                 
					  " case when ev.issummary='Y' then ev.name " +                                                                  
					  " else fl.description " +
					  " end as observacion, fl.uy_fducod_id, 'Y' as isdebit,coalesce(sum(amtsourcedr),0) as saldo " +
					  " from uy_fduloadline fl " +
					  " inner join c_elementvalue ev on  fl.c_elementvalue_id = ev.c_elementvalue_id " +
					  " where fl.uy_fduload_id =? and fl.uy_fducod_id <> 1000128 and fl.uy_fducod_id <> 1000127" + 
					  " group by fl.dateacct, fl.c_elementvalue_id, fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2, " +
					  " fl.c_activity_id_3, fl.uy_fdu_grupocc_id, observacion, fl.uy_fducod_id " +
					  " union " +
					  " select fl.dateacct, fl.c_elementvalue_id_cr as accountid, fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2, " +
					  " fl.c_activity_id_3,fl.uy_fdu_grupocc_id, " +
					  " case when ev.issummary='Y' then ev.name " +
					  " else fl.description " +
					  " end as observacion, fl.uy_fducod_id, 'N' as isdebit,coalesce(sum(amtsourcecr),0) as saldo " +
					  " from uy_fduloadline fl " +
					  " inner join c_elementvalue ev on  fl.c_elementvalue_id_cr = ev.c_elementvalue_id " +
					  " where fl.uy_fduload_id =? and fl.uy_fducod_id <> 1000128 and fl.uy_fducod_id <> 1000127" +
					  " group by fl.dateacct, fl.c_elementvalue_id_cr, fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2, " +
					  " fl.c_activity_id_3, fl.uy_fdu_grupocc_id, observacion, fl.uy_fducod_id ";
*/
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());			
			pstmt.setInt(2, this.get_ID());
			rs = pstmt.executeQuery();
		
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while (rs.next()){

				this.showHelp("Linea Asiento " + rowCount++ + " de " + totalRowCount);
				
				auxFecha = rs.getTimestamp("dateacct");			
				
				// Corte por fecha de asiento
				if (!auxFecha.equals(fechaAsiento)){
					
					// Genero asiento diario
					if (fechaAsiento != null)
						this.generateSimpleJournal(fechaAsiento, totalDebe, totalHaber, journalLines);
					
					totalDebe = Env.ZERO;
					totalHaber = Env.ZERO;
					journalLines = new ArrayList<MJournalLine>();
					lineNumber = 0;
					fechaAsiento = auxFecha;
				}
				
				// Tasa de cambio
				BigDecimal currencyRate = Env.ONE;
				int cCurrencyID = rs.getInt("c_currency_id");
				if (cCurrencyID != 142){
					currencyRate = MConversionRate.getRate(cCurrencyID, 142, fechaAsiento, 114, this.getAD_Client_ID(), this.getAD_Org_ID());
					if (currencyRate == null){
						throw new AdempiereException("Falta definir Tasa de Cambio Dolares - Pesos Uruguayos para la fecha : " + fechaAsiento.toString(), null);
					}
				}

				int cElementValueID = rs.getInt("accountid");
				
				// Si la cuenta es entidad acumulada, debo obtener la cuenta hija para este codigo de grupo cc y moneda
				MElementValue ev = new MElementValue(getCtx(), cElementValueID, null);
				if (ev.isSummary()){
					MElementValue evFduCod = this.getEVForGrupoCCCurrency(cElementValueID, rs.getInt("uy_fdu_grupocc_id"), cCurrencyID);
					cElementValueID = evFduCod.get_ID();
				}

				if (cElementValueID > 0){
					lineNumber++;
					MJournalLine journalLine = new MJournalLine(getCtx(), 0, get_TrxName());
					journalLine.setAD_Client_ID(this.getAD_Client_ID());
					journalLine.setAD_Org_ID(this.getAD_Org_ID());
					journalLine.setLine(lineNumber);
					journalLine.setIsGenerated(true);
					journalLine.setDescription(rs.getString("observacion"));
					journalLine.setC_ElementValue_ID(cElementValueID);
					
					boolean isDebit = (rs.getString("isdebit").equalsIgnoreCase("Y")) ? true : false;
					
					journalLine.setAmtSourceDr((isDebit) ? rs.getBigDecimal("saldo") : Env.ZERO);
					journalLine.setAmtSourceCr((isDebit) ? Env.ZERO : rs.getBigDecimal("saldo"));
					journalLine.setC_Currency_ID(cCurrencyID);
					journalLine.setC_ConversionType_ID(114);
					journalLine.setCurrencyRate(currencyRate);
					journalLine.setQty(Env.ZERO);
					journalLine.setC_ValidCombination_ID(MAccount.forElementValue(getCtx(), journalLine.getC_ElementValue_ID(), null).get_ID());
					journalLine.setProcessed(false);
					journalLine.setC_Activity_ID_1(rs.getInt("c_activity_id_1"));
					journalLine.setC_Activity_ID_2(rs.getInt("c_activity_id_2"));
					journalLine.setC_Activity_ID_3(rs.getInt("c_activity_id_3"));					
					
					if ((journalLine.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (journalLine.getAmtSourceCr().compareTo(Env.ZERO) != 0)){
						journalLines.add(journalLine);
						totalDebe = totalDebe.add(journalLine.getAmtSourceDr().multiply(currencyRate));
						totalHaber = totalHaber.add(journalLine.getAmtSourceCr().multiply(currencyRate));
					}
					
					
				}				
			}
			
			// Genero asiento diario
			if (fechaAsiento != null)
				this.generateSimpleJournal(fechaAsiento, totalDebe, totalHaber, journalLines);
			
			//Guillermo Brust 27/11/2012 ISSUE #137
			//Se guardan las fechas de las lineas procesadas
			ArrayList<Timestamp> fechas = this.journalLineDates();
			if(fechas.size() > 0 ) this.setProcessedDates(fechas, trxName);
			
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
	}
	
	/**
	 * openup 27/11/2012 Guillermo Brust 2.5.1
	 * Se obtienen las fechas que se utilizaron en esta carga de fdu
	 * @return
	 */
	private ArrayList<Timestamp> journalLineDates(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<Timestamp> retorno = new ArrayList<Timestamp>();
		
		try{		
					
			String sql = "select distinct dateacct" +
						 " from gl_journal" +
						 " where uy_fduload_id = " + this.get_ID() + 
						 " order by dateacct ";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		   	while (rs.next()){
				retorno.add(rs.getTimestamp("dateacct"));
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}
	
	private void setProcessedDates(ArrayList<Timestamp> fechas, String trxName){	
		
		for (Timestamp fecha: fechas){

			MFduProcessDate pr = MFduProcessDate.forDateAndFileID(getCtx(), fecha, this.getUY_FduFile_ID(), trxName);
			
			if(pr != null){
				pr.setIsSelected(true);				
			}else{
				pr = new MFduProcessDate(getCtx(), 0, trxName);
				pr.setUY_FduFile_ID(this.getUY_FduFile_ID());
				
				//OpenUp. Guillermo Brust. 19/06/2013. ISSUE #
				//Se setea la compañia, ya que como no se seteaba, se guardaba con 0, y de esta forma no se lograba modificar el registro
				//cuando se queria descliquear la fecha procesada. Haciendo este cambio se podra descliquear.
				
				pr.setAD_Client_ID(OpenUpUtils.getDefaultClient().get_ID());
								
				//Fin OpenUp.
				
				pr.setDateTrx(fecha);
				pr.setProcessed(false);	
				pr.setIsSelected(true);
			}
			pr.saveEx();
			
		}
		
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 26/09/2013. ISSUE #
	 * Método que genera la primera tanda de asientos para el archivo de Servicios UA, Asusasmu, etc
	 * Estos asientos se realizan por el porcentaje de retención.
	 * 
	 * **/
	
	public void generateFirstJournalsForStores(String trxName){
		
		HashMap<MJournalLine, Integer> journalLinesStore = new HashMap<MJournalLine, Integer>();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		Timestamp auxFecha = null, fechaAsiento = null;	
		BigDecimal totalDebe = Env.ZERO, totalHaber = Env.ZERO;
		int lineNumber = 0, c_act_1 = 0, c_act_2 = 0, c_act_3 = 0;
		String storeCode = "", storeName = "", storeDescription = "", descripcion = "";
		
		try{				
			this.showHelp("Obteniendo lineas de carga...");		
            			
			sql = "SELECT fl.dateacct, j.c_elementvalue_id, j.c_elementvalue_id_cr," + 
				  " j.c_elementvalue_id_cr2, fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, st.rate, fl.uy_fdu_store_id," +    
				  " st.value, st.name, st.description, sum(fl.amtsourcedr) as saldo" +    
				  " FROM uy_fduloadline fl" +  
				  " INNER JOIN uy_fdu_store st on fl.uy_fdu_store_id = st.uy_fdu_store_id" + 
				  " INNER JOIN uy_fdu_journalType j ON st.uy_fduFile_id = j.uy_fduFile_id" +  
				  " WHERE fl.uy_fduload_id = ?" + 
				  " GROUP BY fl.dateacct, j.c_elementvalue_id, j.c_elementvalue_id_cr," + 
				  " j.c_elementvalue_id_cr2, fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, st.rate, fl.uy_fdu_store_id," +   
				  " st.value, st.name, st.description" +
				  " ORDER by dateacct asc";			
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());				
			rs = pstmt.executeQuery();
		
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			//Lineas de asiento
			MJournalLine line1 = null;
			MJournalLine line2 = null;
			MJournalLine line3 = null;			
						
			// Cuentas
			MElementValue cuenta1 = null;
			MElementValue cuenta2 = null; 
			MElementValue cuenta3 = null;
			
			while (rs.next()){

				this.showHelp("Linea Asiento " + rowCount++ + " de " + totalRowCount);
				
				cuenta1 = new MElementValue(getCtx(), rs.getInt("c_elementvalue_id"), null);
				cuenta2 = new MElementValue(getCtx(), rs.getInt("c_elementvalue_id_cr"), null); 
				cuenta3 = new MElementValue(getCtx(), rs.getInt("c_elementvalue_id_cr2"), null); 
				
				c_act_1 = rs.getInt("c_activity_id_1");
				c_act_2 = rs.getInt("c_activity_id_2");	
				c_act_3 = rs.getInt("c_activity_id_3");	
				auxFecha = rs.getTimestamp("dateacct");					
				storeCode = rs.getString("value");	
				storeName = rs.getString("name");	
				storeDescription = rs.getString("description");	
				
				descripcion = storeCode + "-" + storeName + "-" + storeDescription;
				
				//OpenUp. Guillermo Brust. 23/09/2013. Se agrega codigo para obtener el Comercio a partir de su valor.
				MFduStore comercio = MFduStore.getStoreForValue(this.getCtx(), storeCode);
								
				// Corte por fecha de asiento
				if (!auxFecha.equals(fechaAsiento)){
					
					// Genero asiento diario
					if (fechaAsiento != null)
						this.generateSimpleJournalStore(fechaAsiento, totalDebe, totalHaber, journalLinesStore, 1);						
					
					totalDebe = Env.ZERO;
					totalHaber = Env.ZERO;
					journalLinesStore = new HashMap<MJournalLine, Integer>();
					lineNumber = 0;
					fechaAsiento = auxFecha;
				}			
					
				BigDecimal saldo1 = rs.getBigDecimal("saldo");				
				
				//Si el saldo es positivo el asiento se hace normal, si no se dan vuelta las cuentas y se pone el valor absuluto del saldo
				if(saldo1.compareTo(Env.ZERO) > 0){
					BigDecimal saldo2 = (saldo1.divide(new BigDecimal(100), 2)).multiply(rs.getBigDecimal("rate").setScale(2, RoundingMode.HALF_UP));
					BigDecimal saldo3 = saldo1.subtract(saldo2);
					
					// Si las cuentas que obtuve no son nulas
					if (cuenta1.get_ID() > 0 && cuenta2.get_ID() > 0 && cuenta3.get_ID() > 0) {

						line1 = this.generateSpecialJournalLine(lineNumber++, true, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldo1, c_act_1, c_act_2, c_act_3);
						line2 = this.generateSpecialJournalLine(lineNumber++, false, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, saldo2, c_act_1, c_act_2, c_act_3);
						line3 = this.generateSpecialJournalLine(lineNumber++, false, cuenta3.get_ID(), descripcion, new BigDecimal(1), 142, saldo3, c_act_1, c_act_2, c_act_3);
													
						if (line1.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(line1, comercio.get_ID());
							totalDebe = totalDebe.add(line1.getAmtSourceDr());													
						}	
						
						if (line2.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(line2, comercio.get_ID());					
							totalHaber = totalHaber.add(line2.getAmtSourceCr());
						}
						
						if (line3.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(line3, comercio.get_ID());					
							totalHaber = totalHaber.add(line2.getAmtSourceCr());
						}																
					}					
				}else{
					saldo1 = saldo1.negate();					
					BigDecimal saldo2 = (saldo1.divide(new BigDecimal(100), 2)).multiply(rs.getBigDecimal("rate").setScale(2, RoundingMode.HALF_UP));
					BigDecimal saldo3 = saldo1.subtract(saldo2);
					
					// Si las cuentas que obtuve no son nulas
					if (cuenta1.get_ID() > 0 && cuenta2.get_ID() > 0 && cuenta3.get_ID() > 0) {

						line1 = this.generateSpecialJournalLine(lineNumber++, false, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldo1, c_act_1, c_act_2, c_act_3);
						line2 = this.generateSpecialJournalLine(lineNumber++, true, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, saldo2, c_act_1, c_act_2, c_act_3);
						line3 = this.generateSpecialJournalLine(lineNumber++, true, cuenta3.get_ID(), descripcion, new BigDecimal(1), 142, saldo3, c_act_1, c_act_2, c_act_3);
						
						
						if (line1.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(line1, comercio.get_ID());
							totalDebe = totalDebe.add(line1.getAmtSourceDr());
						}	
						
						if (line2.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(line2, comercio.get_ID());				
							totalHaber = totalHaber.add(line2.getAmtSourceCr());
						}
						
						if (line3.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(line3, comercio.get_ID());				
							totalHaber = totalHaber.add(line2.getAmtSourceCr());
						}																	
					}					
				}			
			}	
			
			// Genero asiento diario
			if (fechaAsiento != null)
				this.generateSimpleJournalStore(fechaAsiento, totalDebe, totalHaber, journalLinesStore, 1);	
			
			//Guillermo Brust 27/11/2012 ISSUE #137
			//Se guardan las fechas de las lineas procesadas
			ArrayList<Timestamp> fechas = this.journalLineDates();
			if(fechas.size() > 0 ) this.setProcessedDates(fechas, trxName);
			
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
	}
	
	
	
	/**
	 * OpenUp. Guillermo Brust. 26/09/2013. ISSUE #
	 * Método que genera la segunda tanda de asientos para el archivo de Servicios UA, Asusasmu, etc
	 * Estos asientos se realizan por el porcentaje de comision.
	 * 
	 * **/
	
	public void generateSecondJournalsForStores(String trxName){
		
		HashMap<MJournalLine, Integer> journalLinesStore = new HashMap<MJournalLine, Integer>();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		Timestamp auxFecha = null, fechaAsiento = null;	
		BigDecimal totalDebe = Env.ZERO, totalHaber = Env.ZERO, impuesto = Env.ZERO;
		int lineNumber = 0, c_act_1 = 0, c_act_2 = 0, c_act_3 = 0;
		String storeCode = "", storeName = "", descripcion = "";
		
		try{				
			this.showHelp("Obteniendo lineas de carga...");		
            			
			sql = "SELECT distinct fl.dateacct, st.uy_fdu_store_id, st.value, st.name, validcomb.account_id as cuentaProveedor, validcombprod.account_id as cuentaProducto, taxacct.t_due_acct as cuentaImpuesto," +
				  " fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, co.commission / 100 as commission, ROUND((tax.rate / 100), 2) as Impuesto, sum(fl.amtsourcedr) as saldo" +
				  " FROM uy_fduloadline fl" +   
				  " INNER JOIN uy_fdu_store st on fl.uy_fdu_store_id = st.uy_fdu_store_id" +   
				  " INNER JOIN uy_fduload load on fl.uy_fduload_id = load.uy_fduload_id" + 
				  " INNER JOIN uy_fdufile fdufile on load.uy_fdufile_id = fdufile.uy_fdufile_id" +
				  //cuenta 1- asociada al proveedor
				  " INNER JOIN c_bpartner cbp on st.c_bpartner_id = cbp.c_bpartner_id" + 
				  " INNER JOIN c_bp_customer_acct cinvcu on cbp.c_bpartner_id = cinvcu.c_bpartner_id" + 
				  " INNER JOIN c_validcombination validcomb on cinvcu.c_receivable_acct = validcomb.c_validcombination_id" +
				  //cuenta 2- asociada al producto
				  " INNER JOIN uy_fdu_prodstore prodstore on st.uy_fdu_store_id = prodstore.uy_fdu_store_id" +
				  " INNER JOIN m_product_acct prodacct on prodstore.m_product_id = prodacct.m_product_id" + 
				  " INNER JOIN c_validcombination validcombprod on prodacct.p_revenue_acct = validcombprod.c_validcombination_id" + 
				  //Impuesto
				  " INNER JOIN m_product prod on prodstore.m_product_id = prod.m_product_id" +
				  " INNER JOIN c_tax tax on prod.c_taxcategory_id = tax.c_taxcategory_id" +
				  " INNER JOIN c_tax_acct taxacct on tax.c_tax_id = taxacct.c_tax_id" +
				  " INNER JOIN uy_fdu_commissions co ON co.price = fl.amtsourcedr" + 
				  " INNER JOIN uy_fdu_journalType j ON st.uy_fduFile_id = j.uy_fduFile_id" +   
				  " WHERE load.uy_fduload_id = " + this.get_ID() + 
				  " GROUP BY fl.dateacct, st.uy_fdu_store_id, st.value, st.name, validcomb.account_id, validcombprod.account_id, taxacct.t_due_acct, fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, tax.rate, co.commission" +
				  " ORDER BY fl.dateacct";		
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());				
			rs = pstmt.executeQuery();
		
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();			
			
			//Lineas de asiento
			MJournalLine debe = null;
			MJournalLine haberComision = null;
			MJournalLine haberIva = null;		
			
			// Cuentas
			MElementValue cuenta1 = null;
			MElementValue cuenta2 = null; 
			MElementValue cuenta3 = null;
			
			//saldos			
			BigDecimal montoComision = Env.ZERO;
			BigDecimal taxamt = Env.ZERO;
			
			while (rs.next()){

				this.showHelp("Linea Asiento " + rowCount++ + " de " + totalRowCount);
				
				cuenta1 = new MElementValue(getCtx(), rs.getInt("cuentaProveedor"), null);
				cuenta2 = new MElementValue(getCtx(), rs.getInt("cuentaProducto"), null); 
				cuenta3 = new MElementValue(getCtx(), rs.getInt("cuentaImpuesto"), null); 
				
				c_act_1 = rs.getInt("c_activity_id_1");
				c_act_2 = rs.getInt("c_activity_id_2");	
				c_act_3 = rs.getInt("c_activity_id_3");	
				auxFecha = rs.getTimestamp("dateacct");					
				storeCode = rs.getString("value");	
				storeName = rs.getString("name");	
				impuesto = rs.getBigDecimal("impuesto");
				
				descripcion = storeCode + "-" + storeName;
				
				//OpenUp. Guillermo Brust. 23/09/2013. Se agrega codigo para obtener el Comercio a partir de su valor.
				MFduStore comercio = MFduStore.getStoreForValue(this.getCtx(), storeCode);
								
				// Corte por fecha de asiento
				if (!auxFecha.equals(fechaAsiento)){
					
					// Genero asiento diario
					if (fechaAsiento != null)
						this.generateSimpleJournalStore(fechaAsiento, totalDebe, totalHaber, journalLinesStore, 2);						
					
					totalDebe = Env.ZERO;
					totalHaber = Env.ZERO;
					journalLinesStore = new HashMap<MJournalLine, Integer>();
					lineNumber = 0;
					fechaAsiento = auxFecha;
				}			
					
				BigDecimal saldo1 = rs.getBigDecimal("saldo");				
				montoComision = rs.getBigDecimal("saldo").multiply(rs.getBigDecimal("commission")).setScale(2, RoundingMode.HALF_UP);				
						
				taxamt = montoComision.multiply(impuesto).setScale(2, RoundingMode.HALF_UP);				
				BigDecimal saldoDebe = montoComision.add(taxamt);
								
				//Si el saldo es positivo el asiento se hace normal, si no se dan vuelta las cuentas y se pone el valor absuluto del saldo
				if(saldo1.compareTo(Env.ZERO) > 0){
										
					// Si las cuentas que obtuve no son nulas
					if (cuenta1.get_ID() > 0 && cuenta2.get_ID() > 0 && cuenta3.get_ID() > 0) {						

						// Genero las lineas para los datos anteriores y el asiento diario				
						debe = this.generateSpecialJournalLine(lineNumber++, true, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldoDebe, c_act_1, c_act_2, c_act_3);
						haberComision = this.generateSpecialJournalLine(lineNumber++, false, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, montoComision, c_act_1, c_act_2, c_act_3);
						haberIva = this.generateSpecialJournalLine(lineNumber++, false, cuenta3.get_ID(), descripcion, new BigDecimal(1), 142, taxamt, c_act_1, c_act_2, c_act_3);
													
						if (debe.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(debe, comercio.get_ID());
							totalDebe = totalDebe.add(debe.getAmtSourceDr());													
						}	
						
						if (haberComision.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(haberComision, comercio.get_ID());					
							totalHaber = totalHaber.add(haberComision.getAmtSourceCr());
						}
						
						if (haberIva.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(haberIva, comercio.get_ID());					
							totalHaber = totalHaber.add(haberIva.getAmtSourceCr());
						}																
					}					
				}else{
					saldo1 = saldo1.negate();					
					
					
					// Si las cuentas que obtuve no son nulas
					if (cuenta1.get_ID() > 0 && cuenta2.get_ID() > 0 && cuenta3.get_ID() > 0) {

						// Genero las lineas para los datos anteriores y el asiento diario				
						debe = this.generateSpecialJournalLine(lineNumber++, false, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldoDebe, c_act_1, c_act_2, c_act_3);
						haberComision = this.generateSpecialJournalLine(lineNumber++, true, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, montoComision, c_act_1, c_act_2, c_act_3);
						haberIva = this.generateSpecialJournalLine(lineNumber++, true, cuenta3.get_ID(), descripcion, new BigDecimal(1), 142, taxamt, c_act_1, c_act_2, c_act_3);
													
						if (debe.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(debe, comercio.get_ID());
							totalDebe = totalDebe.add(debe.getAmtSourceDr());													
						}	
						
						if (haberComision.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(haberComision, comercio.get_ID());					
							totalHaber = totalHaber.add(haberComision.getAmtSourceCr());
						}
						
						if (haberIva.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLinesStore.put(haberIva, comercio.get_ID());					
							totalHaber = totalHaber.add(haberIva.getAmtSourceCr());
						}																					
					}					
				}			
			}	
			
			// Genero asiento diario
			if (fechaAsiento != null)
				this.generateSimpleJournalStore(fechaAsiento, totalDebe, totalHaber, journalLinesStore, 2);	
			
			//Guillermo Brust 27/11/2012 ISSUE #137
			//Se guardan las fechas de las lineas procesadas
			ArrayList<Timestamp> fechas = this.journalLineDates();
			if(fechas.size() > 0 ) this.setProcessedDates(fechas, trxName);
			
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
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 26/09/2013. ISSUE #
	 * Método que crea los asientos diarios para los datos recogidos del archivo de Tecnixa (Prestamos)
	 * 
	 * */
	
	public void generateJournalsForTecnixa(String trxName){
		
		List<MJournalLine> journalLines = new ArrayList<MJournalLine>();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		Timestamp auxFecha = null, fechaAsiento = null;	
		BigDecimal totalDebe = Env.ZERO, totalHaber = Env.ZERO;
		int lineNumber = 0, c_act_1 = 0, c_act_2 = 0, c_act_3 = 0;
		String storeCode = "", storeName = "", storeDescription = "", descripcion = "";
		
		try{				
			this.showHelp("Obteniendo lineas de carga...");		
            			
			sql = "SELECT fl.dateacct, j.c_elementvalue_id, j.c_elementvalue_id_cr," + 
				  " j.c_elementvalue_id_cr2, fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, st.rate, fl.uy_fdu_store_id," +    
				  " st.value, st.name, st.description, sum(fl.amtsourcedr) as saldo" +    
				  " FROM uy_fduloadline fl" +  
				  " INNER JOIN uy_fdu_store st on fl.uy_fdu_store_id = st.uy_fdu_store_id" + 
				  " INNER JOIN uy_fdu_journalType j ON st.uy_fduFile_id = j.uy_fduFile_id" +  
				  " WHERE fl.uy_fduload_id = ?" + 
				  " GROUP BY fl.dateacct, j.c_elementvalue_id, j.c_elementvalue_id_cr," + 
				  " j.c_elementvalue_id_cr2, fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, st.rate, fl.uy_fdu_store_id," +   
				  " st.value, st.name, st.description" +
				  " ORDER by dateacct asc";			
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());				
			rs = pstmt.executeQuery();
		
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			//Lineas de asiento
			MJournalLine line1 = null;
			MJournalLine line2 = null;
			MJournalLine line3 = null;
			MJournalLine line4 = null;	
						
			// Cuentas
			MElementValue cuenta1 = null;
			MElementValue cuenta2 = null; 
					
			while (rs.next()){

				this.showHelp("Linea Asiento " + rowCount++ + " de " + totalRowCount);
				
				cuenta1 = new MElementValue(getCtx(), rs.getInt("c_elementvalue_id"), null);
				cuenta2 = new MElementValue(getCtx(), rs.getInt("c_elementvalue_id_cr"), null); 
								
				c_act_1 = rs.getInt("c_activity_id_1");
				c_act_2 = rs.getInt("c_activity_id_2");	
				c_act_3 = rs.getInt("c_activity_id_3");	
				auxFecha = rs.getTimestamp("dateacct");					
				storeCode = rs.getString("value");	
				storeName = rs.getString("name");	
				storeDescription = rs.getString("description");	
				
				descripcion = storeCode + "-" + storeName + "-" + storeDescription;
											
				// Corte por fecha de asiento
				if (!auxFecha.equals(fechaAsiento)){
					
					// Genero asiento diario
					if (fechaAsiento != null)
						this.generateSimpleJournal(fechaAsiento, totalDebe, totalHaber, journalLines);						
					
					totalDebe = Env.ZERO;
					totalHaber = Env.ZERO;
					journalLines = new ArrayList<MJournalLine>();
					lineNumber = 0;
					fechaAsiento = auxFecha;
				}			
					
				BigDecimal saldo1 = rs.getBigDecimal("saldo");	//total			
				
				//Si el saldo es positivo el asiento se hace normal, si no se dan vuelta las cuentas y se pone el valor absuluto del saldo
				if(saldo1.compareTo(Env.ZERO) > 0){
					BigDecimal saldo2 = (saldo1.divide(new BigDecimal(100), 2)).multiply(rs.getBigDecimal("rate").setScale(2, RoundingMode.HALF_UP)); //retencion
											
					// Si las cuentas que obtuve no son nulas
					if (cuenta1.get_ID() > 0 && cuenta2.get_ID() > 0) {

						line1 = this.generateSpecialJournalLine(lineNumber++, true, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldo1, c_act_1, c_act_2, c_act_3);
						line2 = this.generateSpecialJournalLine(lineNumber++, false, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, saldo1, c_act_1, c_act_2, c_act_3);
						line3 = this.generateSpecialJournalLine(lineNumber++, true, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, saldo2, c_act_1, c_act_2, c_act_3);
						line4 = this.generateSpecialJournalLine(lineNumber++, false, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldo2, c_act_1, c_act_2, c_act_3);
						
						
						if (line1.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLines.add(line1);
							totalDebe = totalDebe.add(line1.getAmtSourceDr());					
						}	
						
						if (line2.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLines.add(line2);		
							totalHaber = totalHaber.add(line2.getAmtSourceCr());
						}
						
						if (line3.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLines.add(line3);			
							totalDebe = totalDebe.add(line2.getAmtSourceDr());
						}
						
						if (line4.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLines.add(line4);		
							totalHaber = totalHaber.add(line2.getAmtSourceCr());
						}																
					}
					
				}else{
					saldo1 = saldo1.negate();
					BigDecimal saldo2 = (saldo1.divide(new BigDecimal(100), 2)).multiply(rs.getBigDecimal("rate").setScale(2, RoundingMode.HALF_UP));
					
					// Si las cuentas que obtuve no son nulas
					if (cuenta1.get_ID() > 0 && cuenta2.get_ID() > 0) {
						
						line1 = this.generateSpecialJournalLine(lineNumber++, true, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldo2, c_act_1, c_act_2, c_act_3);
						line2 = this.generateSpecialJournalLine(lineNumber++, false, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, saldo2, c_act_1, c_act_2, c_act_3);
						line3 = this.generateSpecialJournalLine(lineNumber++, true, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, saldo1, c_act_1, c_act_2, c_act_3);
						line4 = this.generateSpecialJournalLine(lineNumber++, false, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldo1, c_act_1, c_act_2, c_act_3);
						
						if (line1.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLines.add(line1);
							totalDebe = totalDebe.add(line1.getAmtSourceDr());					
						}	
						
						if (line2.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLines.add(line2);		
							totalHaber = totalHaber.add(line2.getAmtSourceCr());
						}
						
						if (line3.getAmtSourceDr().compareTo(Env.ZERO) != 0){
							journalLines.add(line3);		
							totalDebe = totalDebe.add(line2.getAmtSourceDr());
						}
						
						if (line4.getAmtSourceCr().compareTo(Env.ZERO) != 0){
							journalLines.add(line4);			
							totalHaber = totalHaber.add(line2.getAmtSourceCr());
						}																
					}			
				}					
			}	
			
			// Genero asiento diario
			if (fechaAsiento != null)
				this.generateSimpleJournal(fechaAsiento, totalDebe, totalHaber, journalLines);	
			
			//Guillermo Brust 27/11/2012 ISSUE #137
			//Se guardan las fechas de las lineas procesadas
			ArrayList<Timestamp> fechas = this.journalLineDates();
			if(fechas.size() > 0 ) this.setProcessedDates(fechas, trxName);
			
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
	}

	
	/**
	 * openup Guillermo Brust. 04/12/2012
	 * Generacion de Asientos producto de la generacion de factura para archivos de comercios. 
	 * @param uy_fdufule_id
	 */
	
	public void generateJournalsForInvoiceStores(int c_doctype_id, int uy_fdu_invoiceType_id){
				
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		MPeriod periodoActual = MPeriod.get(getCtx(), today, 0);
		MPeriod periodoAnterior = new MPeriod(getCtx(), periodoActual.get_ID() - 1, null);
		Timestamp fechaFactura = periodoAnterior.getEndDate();
		
		List<MJournalLine> journalLines = new ArrayList<MJournalLine>();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		Timestamp fechaAsiento = fechaFactura;	
		BigDecimal totalDebe = Env.ZERO, totalHaber = Env.ZERO;
		int lineNumber = 0, c_act_1 = 0, c_act_2 = 0, c_act_3 = 0;
		String storeCode = "", storeName = "", storeDescription = "", descripcion = "";
		
		
		try{				
			this.showHelp("Obteniendo lineas de carga...");		
            			
			sql = "SELECT st.uy_store_id, st.value, st.name, st.description," +
				  " fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, fl.amtsourcedr as saldo, co.commission" +    
				  " FROM uy_fduloadline fl" +  
				  " INNER JOIN uy_fdu_store st on fl.uy_fdu_store_id = st.uy_fdu_store_id" +  
				  " INNER JOIN uy_fdu_commissions co ON co.price = fl.amtsourcedr" +
				  " INNER JOIN uy_fdu_journalType j ON st.uy_fduFile_id = j.uy_fduFile_id" +  
				  " WHERE fl.uy_fduload_id = ?" +
				  " ORDER BY st.uy_fdu_store_id, fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3";		
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());				
			rs = pstmt.executeQuery();
		
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();			
			
			//Lineas de asiento
			MJournalLine debe = null;
			MJournalLine haberComision = null;
			MJournalLine haberIva = null;		
			
			// Cuentas
			MElementValue cuenta1 = null;
			MElementValue cuenta2 = null; //Esta es la unica cuenta que va variando por cada comercio
			MElementValue cuenta3 = null;
			
			//saldos
			BigDecimal comisionTotal = Env.ZERO;
			BigDecimal montoComision = Env.ZERO;
			BigDecimal taxamt = Env.ZERO;
			
			while (rs.next()){
				
				this.showHelp("Linea Asiento " + rowCount++ + " de " + totalRowCount);
											
				storeCode = rs.getString("value");	
				storeName = rs.getString("name");	
				storeDescription = rs.getString("description");	
				
				descripcion = storeCode + "-" + storeName + "-" + storeDescription;
				
				//Obtengo cuenta contable asociada al socio de negocio ubicado en cada tipo de factura asociada al archivo en cuestion
				int c_bpartner_id = new MFduInvoiceType(getCtx(), uy_fdu_invoiceType_id, get_TrxName()).getC_BPartner_ID();
				int c_validcombination_id = MBPCustomerAcct.getMBPCustomerAcctForCbPartner(getCtx(), c_bpartner_id).get_ID();
				int c_elementValue_id = new X_C_ValidCombination(getCtx(), c_validcombination_id, get_TrxName()).getAccount_ID();
				
				cuenta1 = new MElementValue(getCtx(), c_elementValue_id, get_TrxName());
				
				//se obtiene la cuenta contable asociada al servicio, que es un producto en la factura
				int productID = MFduProdStore.getMFduProdStoreForStore(getCtx(), rs.getInt("uy_fdu_store_id")).getM_Product_ID();
				int prodAcc = MProductAcct.getMProductAcctForProduct(getCtx(), productID).get_ID();
						
				cuenta2 = new MElementValue(getCtx(), new X_C_ValidCombination(getCtx(), prodAcc, get_TrxName()).getAccount_ID(), get_TrxName());
				
				cuenta3 = new MElementValue(getCtx(), 1003749, get_TrxName()); //iva
				
				montoComision = rs.getBigDecimal("saldo").multiply(rs.getBigDecimal("commission")).setScale(2, RoundingMode.HALF_UP);				
				comisionTotal = comisionTotal.add(montoComision);
				
				haberComision = this.generateSpecialJournalLine(lineNumber++, false, cuenta2.get_ID(), descripcion, new BigDecimal(1), 142, montoComision, c_act_1, c_act_2, c_act_3);
				
				if (haberComision.getAmtSourceCr().compareTo(Env.ZERO) != 0){
					journalLines.add(haberComision);					
					totalHaber = totalHaber.add(haberComision.getAmtSourceCr());
				}
				
				
			}
			
			MTax ivaBasico = new MTax(getCtx(), 1000004, get_TrxName());
			taxamt = ((comisionTotal.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)).multiply(ivaBasico.getRate()).setScale(2, RoundingMode.HALF_UP));
			BigDecimal saldoDebe = comisionTotal.add(taxamt);
			
			// Genero las lineas para los datos anteriores y el asiento diario					
			debe = this.generateSpecialJournalLine(lineNumber++, true, cuenta1.get_ID(), descripcion, new BigDecimal(1), 142, saldoDebe, c_act_1, c_act_2, c_act_3);			
			haberIva = this.generateSpecialJournalLine(lineNumber++, true, cuenta3.get_ID(), descripcion, new BigDecimal(1), 142, taxamt, c_act_1, c_act_2, c_act_3);
			
			if (debe.getAmtSourceDr().compareTo(Env.ZERO) != 0){
				journalLines.add(debe);
				totalDebe = totalDebe.add(debe.getAmtSourceDr());					
			}
			
			if (haberIva.getAmtSourceCr().compareTo(Env.ZERO) != 0){
				journalLines.add(haberIva);					
				totalHaber = totalHaber.add(haberIva.getAmtSourceCr());
			}
			
			this.generateSimpleJournal(fechaAsiento, totalDebe, totalHaber, journalLines);
			
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
		
	}
	
	/**
	 * OpenUp.
	 * MLoadFduFile
	 * Descripcion : Generacion asientos para codigos fdu especiales.
	 * @author Guillermo Brust
	 * ISSUE #61 - Version 2.5.1
	 * Fecha : 06/11/2012
	 */		
	
	public void generateSpecialCases108() {
		
		List<MJournalLine> journalLines = new ArrayList<MJournalLine>();

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		Timestamp auxFecha = null, fechaAsiento = null;
		BigDecimal totalDebe = Env.ZERO, totalHaber = Env.ZERO;
		int lineNumber = 0;		
		int codigo = 0;
		
		try{
		
			this.showHelp("Obteniendo lineas de carga...");
			
			sql = "select fl.uy_fduloadline_id, fl.dateacct, fl.uy_fducod_id, fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, fl.uy_fdu_grupocc_id, fl.amtsourcedr as saldo" + 
				  " from uy_fduloadline fl" + 
				  " where fl.uy_fduload_id = ?" + 
				  " and fl.uy_fducod_id in(1000120, 1000119, 1000128, 1000127)" + 
				  " group by fl.uy_fduloadline_id, fl.dateacct, fl.uy_fducod_id, fl.c_currency_id, fl.c_activity_id_1, fl.c_activity_id_2, fl.c_activity_id_3, fl.uy_fdu_grupocc_id, fl.uy_fducod_id, uy_fdu_grupocc_id, fl.amtsourcedr" + 
				  " order by fl.uy_fduloadline_id asc";

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, this.get_ID());		
			rs = pstmt.executeQuery();
		
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			BigDecimal impPesos = null;
			BigDecimal impDolares = null;
			
			while (rs.next()){		
				
				this.showHelp("Linea Asiento " + rowCount++ + " de " + totalRowCount);
				
				auxFecha = rs.getTimestamp("dateacct");	
				codigo = rs.getInt("uy_fducod_id");
				
				// Corte por fecha de asiento
				if (!auxFecha.equals(fechaAsiento)){
					
					// Genero asiento diario
					if (fechaAsiento != null)
						this.generateSimpleJournal(fechaAsiento, totalDebe, totalHaber, journalLines);
					
					totalDebe = Env.ZERO;
					totalHaber = Env.ZERO;
					journalLines = new ArrayList<MJournalLine>();
					lineNumber = 0;
					fechaAsiento = auxFecha;
				}	
				
										
				int cCurrencyID = 0;
				BigDecimal currencyRate = Env.ONE;
				MJournalLine line1 = null;
				MJournalLine line2 = null;
				MJournalLine line3 = null;
				X_C_ElementValue cElementValue = null;
				
				//OpenUp. Guillermo Brust. 06/11/2012. ISSUE #61. Version 2.5.1
				//Comentario: Lineas de asientos especiales generados por ciertos codigos, que por ahora van quemados.	

								
				//esto es para los casos del 12100 y 12200
				if(impDolares != null && impPesos != null){
					impDolares = null;
					impPesos = null;			


				}
				
				if(codigo == 1000120){ //codigo 8700 - Genera tres lineas, una para el debe y dos para el haber en distintas cuentas, el importe al debe es por el total 
					  				   //y el importe al haber de la cuentas se obtienen de dividir el total sobre 1.22 la segunda linea de haber se obtiene de multiplicar
						               //el segundo resultado por 0.22
					
					BigDecimal saldo1 = rs.getBigDecimal("saldo");
					BigDecimal saldo2 = saldo1.divide(new BigDecimal(1.22), 2);
					BigDecimal saldo3 = saldo2.multiply(new BigDecimal(0.22).setScale(2, RoundingMode.HALF_UP));	
					
					//tasa de cambio
					cCurrencyID = rs.getInt("c_currency_id");
					if (cCurrencyID != 142){
						currencyRate = MConversionRate.getRate(cCurrencyID, 142, fechaAsiento, 114, this.getAD_Client_ID(), this.getAD_Org_ID());
						if (currencyRate == null){
							throw new AdempiereException("Falta definir Tasa de Cambio Dolares - Pesos Uruguayos para la fecha : " + fechaAsiento.toString(), null);
						}
					}
					
					// Si la cuenta es entidad acumulada, debo obtener la cuenta hija para este codigo de grupo cc y moneda
					cElementValue = this.getEVForGrupoCCCurrency(1003455, rs.getInt("uy_fdu_grupocc_id"), cCurrencyID);
					
					MElementValue cuentaHaber1 = new MElementValue(getCtx(), 1003857, get_TrxName());
					
					MElementValue cuentaHaber2 = new MElementValue(getCtx(), 1003749, get_TrxName());			
					
					
					if (cElementValue.get_ID() > 0 && cuentaHaber1.get_ID() > 0
							&& cuentaHaber2.get_ID() > 0) {

						line1 = this.generateSpecialJournalLine(lineNumber++, true, cElementValue.get_ID(), cElementValue.getName(), currencyRate,
																rs.getInt("c_currency_id"), saldo1.setScale(2, RoundingMode.HALF_UP), rs.getInt("c_activity_id_1"),
																rs.getInt("c_activity_id_2"), rs.getInt("c_activity_id_3"));

						line2 = this.generateSpecialJournalLine(lineNumber++, false, cuentaHaber1.get_ID(), cuentaHaber1.getName(), currencyRate, rs.getInt("c_currency_id"),
																saldo2.setScale(2, RoundingMode.HALF_UP), rs.getInt("c_activity_id_1"), rs.getInt("c_activity_id_2"),
																rs.getInt("c_activity_id_3"));

						line3 = this.generateSpecialJournalLine(lineNumber++, false, cuentaHaber2.get_ID(), cuentaHaber2.getName(), currencyRate, rs.getInt("c_currency_id"),
																saldo3.setScale(2, RoundingMode.HALF_UP), rs.getInt("c_activity_id_1"), rs.getInt("c_activity_id_2"),
																rs.getInt("c_activity_id_3"));

						if ((line1.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line1.getAmtSourceCr().compareTo(Env.ZERO) != 0)) {
							journalLines.add(line1);
							totalDebe = totalDebe.add(line1.getAmtSourceDr().multiply(currencyRate));
							totalHaber = totalHaber.add(line1.getAmtSourceCr().multiply(currencyRate));
						}

						if ((line2.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line2.getAmtSourceCr().compareTo(Env.ZERO) != 0)) {
							journalLines.add(line2);
							totalDebe = totalDebe.add(line2.getAmtSourceDr().multiply(currencyRate));
							totalHaber = totalHaber.add(line2.getAmtSourceCr().multiply(currencyRate));
						}

						if ((line3.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line3.getAmtSourceCr().compareTo(Env.ZERO) != 0)) {
							journalLines.add(line3);
							totalDebe = totalDebe.add(line3.getAmtSourceDr().multiply(currencyRate));
							totalHaber = totalHaber.add(line3.getAmtSourceCr().multiply(currencyRate));
						}
					}

				}else if(codigo == 1000119){ //codigo 8550
					
					//tasa de cambio
					cCurrencyID = rs.getInt("c_currency_id");
					if (cCurrencyID != 142){
						currencyRate = MConversionRate.getRate(cCurrencyID, 142, fechaAsiento, 114, this.getAD_Client_ID(), this.getAD_Org_ID());
						if (currencyRate == null){
							throw new AdempiereException("Falta definir Tasa de Cambio Dolares - Pesos Uruguayos para la fecha : " + fechaAsiento.toString(), null);
						}
					}
					
					int parentDebe = 0;
					int parentHaber = 0;
					
					if(cCurrencyID == 142){
						parentDebe = 1004960;
						parentHaber = 1004959;
					}else{
						parentDebe = 1004962;
						parentHaber = 1004961;
					}
					
					// Si la cuenta es entidad acumulada, debo obtener la cuenta hija para este codigo de grupo cc y moneda
					cElementValue = this.getEVForGrupoCCCurrency(parentDebe, rs.getInt("uy_fdu_grupocc_id"), cCurrencyID);
					
					X_C_ElementValue cuentaHaber = this.getEVForGrupoCCCurrency(parentHaber, rs.getInt("uy_fdu_grupocc_id"), cCurrencyID);									
					
					
					if(cElementValue.get_ID() > 0 && cuentaHaber.get_ID() > 0){
						
						line1 = this.generateSpecialJournalLine(lineNumber++, true, cElementValue.get_ID(), cElementValue.getName(), currencyRate, rs.getInt("c_currency_id"), 
								rs.getBigDecimal("saldo"), rs.getInt("c_activity_id_1"),  rs.getInt("c_activity_id_2"),  rs.getInt("c_activity_id_3"));

						line2 = this.generateSpecialJournalLine(lineNumber++, false, cuentaHaber.get_ID() , cuentaHaber.getName(), currencyRate, rs.getInt("c_currency_id"), 
								rs.getBigDecimal("saldo"), rs.getInt("c_activity_id_1"),  rs.getInt("c_activity_id_2"),  rs.getInt("c_activity_id_3"));
						
						if ((line1.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line1.getAmtSourceCr().compareTo(Env.ZERO) != 0)){
							journalLines.add(line1);
							totalDebe = totalDebe.add(line1.getAmtSourceDr().multiply(currencyRate));
							totalHaber = totalHaber.add(line1.getAmtSourceCr().multiply(currencyRate));
						}	
						
						if ((line2.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line2.getAmtSourceCr().compareTo(Env.ZERO) != 0)){
							journalLines.add(line2);
							totalDebe = totalDebe.add(line2.getAmtSourceDr().multiply(currencyRate));
							totalHaber = totalHaber.add(line2.getAmtSourceCr().multiply(currencyRate));
						}	
					}			
					
					
				}else if(codigo == 1000128 || codigo == 1000127){ //codigo 12200 y 121000 respectivamente, esto aplica para los dos codigos
					
					if(rs.getInt("c_currency_id") == 142) impPesos = rs.getBigDecimal("saldo");
					else impDolares = rs.getBigDecimal("saldo");
					
					if(impPesos != null && impDolares != null){		
						
						//tasa de cambio
						currencyRate = impPesos.divide(impDolares, 2, RoundingMode.HALF_UP).negate();
						
						// Si la cuenta es entidad acumulada, debo obtener la cuenta hija para este codigo de grupo cc y moneda
						X_C_ElementValue cElementValuePesos = this.getEVForGrupoCCCurrency(1003455, rs.getInt("uy_fdu_grupocc_id"), 142);
						X_C_ElementValue cElementValueDolares = this.getEVForGrupoCCCurrency(1003455, rs.getInt("uy_fdu_grupocc_id"), 100);
						
						
						if(cElementValuePesos.get_ID() > 0 && cElementValueDolares.get_ID() > 0){
							
							//el importe que venga positivo es el que va al debe
							//el primero es el caso en el que el importe en pesos es positivo
							if(impPesos.compareTo(Env.ZERO) > 0){	
															
								impDolares = impDolares.negate();							
								
								line1 = this.generateSpecialJournalLine(lineNumber++, true, cElementValuePesos.get_ID(), cElementValuePesos.getName(), new BigDecimal(1), 142, 
																impPesos, rs.getInt("c_activity_id_1"),  rs.getInt("c_activity_id_2"),  rs.getInt("c_activity_id_3"));
								
								line2 = this.generateSpecialJournalLine(lineNumber++, false, cElementValueDolares.get_ID(), cElementValueDolares.getName(), currencyRate, 100, 
																impDolares, rs.getInt("c_activity_id_1"),  rs.getInt("c_activity_id_2"),  rs.getInt("c_activity_id_3"));	
								

									
								if ((line1.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line1.getAmtSourceCr().compareTo(Env.ZERO) != 0)){
									journalLines.add(line1);
									totalDebe = totalDebe.add(line1.getAmtSourceDr());
									totalHaber = totalHaber.add(line1.getAmtSourceCr());

								}	
								
								if ((line2.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line2.getAmtSourceCr().compareTo(Env.ZERO) != 0)){
									journalLines.add(line2);
									totalDebe = totalDebe.add(line2.getAmtSourceDr().multiply(currencyRate));
									totalHaber = totalHaber.add(line2.getAmtSourceCr().multiply(currencyRate));
								}
								
							
							}else{ //caso en que el importe en dolares es positivo
															
								impPesos = impPesos.negate();								
								
								line1 = this.generateSpecialJournalLine(lineNumber++, true, cElementValueDolares.get_ID(), cElementValueDolares.getName(), currencyRate, 100, 
																impDolares, rs.getInt("c_activity_id_1"),  rs.getInt("c_activity_id_2"),  rs.getInt("c_activity_id_3"));
		
								line2 = this.generateSpecialJournalLine(lineNumber++, false, cElementValuePesos.get_ID(), cElementValuePesos.getName(), new BigDecimal(1), 142, 
																impPesos, rs.getInt("c_activity_id_1"),  rs.getInt("c_activity_id_2"),  rs.getInt("c_activity_id_3"));									
								
								
								if ((line1.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line1.getAmtSourceCr().compareTo(Env.ZERO) != 0)){
									journalLines.add(line1);
									totalDebe = totalDebe.add(line1.getAmtSourceDr().multiply(currencyRate));
									totalHaber = totalHaber.add(line1.getAmtSourceCr().multiply(currencyRate));
								}	
								
								if ((line2.getAmtSourceDr().compareTo(Env.ZERO) != 0) || (line2.getAmtSourceCr().compareTo(Env.ZERO) != 0)){
									journalLines.add(line2);
									totalDebe = totalDebe.add(line2.getAmtSourceDr());
									totalHaber = totalHaber.add(line2.getAmtSourceCr());
								}
																
							}	
							
							if (currencyRate.compareTo(Env.ZERO) < 0) currencyRate = currencyRate.negate();						
										
						}					
						
					}					
				}			
			}		
			
			if(journalLines.size() > 0){
				// Genero asiento diario
				if (fechaAsiento != null) this.generateSimpleJournal(fechaAsiento, totalDebe, totalHaber, journalLines);		
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
	}
		
	
	private MJournalLine generateSpecialJournalLine(int lineNumber, boolean isDebit, int cElementValueID, String descripcion, 
			BigDecimal currencyRate, int cCurrencyID, BigDecimal saldo, int c_act_1, int c_act_2, int c_act_3){
		
		//genero una nueva linea al debe				
		MJournalLine journalLine = new MJournalLine(getCtx(), 0, get_TrxName());
		journalLine.setAD_Client_ID(this.getAD_Client_ID());
		journalLine.setAD_Org_ID(this.getAD_Org_ID());

		if (currencyRate.compareTo(Env.ZERO) < 0) currencyRate = currencyRate.negate();
		if (saldo.compareTo(Env.ZERO) < 0) saldo = saldo.negate();
		
		journalLine.setLine(lineNumber);
		journalLine.setIsGenerated(true);
		journalLine.setDescription(descripcion);
		journalLine.setC_ElementValue_ID(cElementValueID);			
		journalLine.setAmtSourceDr((isDebit) ? saldo : Env.ZERO);
		journalLine.setAmtSourceCr((isDebit) ? Env.ZERO : saldo);
		journalLine.setC_Currency_ID(cCurrencyID);
		journalLine.setC_ConversionType_ID(114);
		journalLine.setCurrencyRate(currencyRate);
		journalLine.setQty(Env.ZERO);
		journalLine.setC_ValidCombination_ID(MAccount.forElementValue(getCtx(), journalLine.getC_ElementValue_ID(), null).get_ID());
		journalLine.setProcessed(false);
		journalLine.setC_Activity_ID_1(c_act_1);
		journalLine.setC_Activity_ID_2(c_act_2);
		journalLine.setC_Activity_ID_3(c_act_3);	
		
		return journalLine;
		
	}	
	
	
	/***
	 * Obtiene y retorna cuenta contable hija de una cuenta summary recibida y que ademas
	 * pertenece al grupo CCC y moneda de un codigo fdu recibido.
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 * @param c_ElementValue_ID
	 * @param uy_FduCod_ID
	 * @return
	 */
	private MElementValue getEVForGrupoCCCurrency(int cElementValueID, int uyFduGrupoCCID, int cCurrencyID) {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MElementValue value = new MElementValue(getCtx(), 0, null);
		
		try{
			
			String whereCurrency = "";
			if (cCurrencyID == 142){
				whereCurrency = " and ((ev.c_currency_id is null) OR (ev.c_currency_id = 142)) ";
			}
			else{
				whereCurrency = " and ev.c_currency_id =" + cCurrencyID;
			}
			
			String sql = " select tn.node_id " +
					     " from ad_treenode tn " +
					     " inner join c_elementvalue ev on tn.node_id = ev.c_elementvalue_id " +
					     " where ad_tree_id = 1000130 " +
					     " and parent_id =?" + 
					     " and ev.issummary = 'N'" +
					     " and ev.uy_fdu_grupocc_id =? " + whereCurrency;
					     
			
		    pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, cElementValueID);
			pstmt.setInt(2, uyFduGrupoCCID);
			rs = pstmt.executeQuery();

			if (rs.next()){
				value = new MElementValue(getCtx(), rs.getInt(1), null);
			}
			else{
				value = this.getEVForCurrency(cElementValueID, cCurrencyID);
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
	}
	
	/***
	 * Obtiene y retorna lineas de carga ordenadas por fecha de asiento
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 * @return
	 */
	public List<MFduLoadLine> getLines(){
	
		String whereClause = X_UY_FduLoadLine.COLUMNNAME_UY_FduLoad_ID + "=" + this.get_ID();
		
		List<MFduLoadLine> lines = new Query(getCtx(), I_UY_FduLoadLine.Table_Name, whereClause, get_TrxName())
		.setOrderBy("DateAcct").list();
		
		return lines;
	}

	
	/***
	 * Genera asiento diario simple y lo completa.
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 29/10/2012
	 * @see
	 */
	private void generateSimpleJournal(Timestamp dateAcct, BigDecimal totalDebe, BigDecimal totalHaber, List<MJournalLine> journalLines) {

		if (totalDebe.compareTo(totalHaber) != 0){
			//throw new AdempiereException("Total Debitos no es igual a Total Creditos : " + totalDebe + " - " + totalHaber);
		}
		
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		MDocType doc = MJournal.getSimpleJournalDocType(getCtx(), null);
		
		if ((doc == null) || (doc.get_ID() <= 0)){
			throw new AdempiereException("No se pudo obtener Documento para Asiento Diario Simple");
		}
		
		MJournal journal = new MJournal(getCtx(), 0, get_TrxName());
		journal.setAD_Client_ID(this.getAD_Client_ID());
		journal.setAD_Org_ID(this.getAD_Org_ID());
		journal.setC_AcctSchema_ID(schema.get_ID());
		journal.setC_DocType_ID(doc.get_ID());
		journal.setDescription("Carga Cierre Cuenta Corriente : " + this.getDocumentNo());
		journal.setDateDoc(this.getDateTrx());

		//log.warning("Fecha Asiento : " + dateAcct.toString());
		//log.warning("Empresa : " + journal.getAD_Client_ID());
		//log.warning("Organizacion : " + journal.getAD_Org_ID());
		
		int cPeriodID = MPeriod.getC_Period_ID(getCtx(), dateAcct, journal.getAD_Org_ID());
		//log.warning("Periodo : " + cPeriodID);
		
		journal.setDateAcct(dateAcct);
		journal.setC_Currency_ID(schema.getC_Currency_ID());
		journal.setC_ConversionType_ID(114);
		journal.setCurrencyRate(Env.ONE);
		journal.setTotalDr(totalDebe);
		journal.setTotalCr(totalHaber);
		
		int glCategoryID = DB.getSQLValue(null, "SELECT max(gl_category_id) FROM GL_Category "
							+ "WHERE AD_Client_ID=? AND IsDefault='Y'", this.getAD_Client_ID());
		
		journal.setGL_Category_ID(glCategoryID);
		journal.setUY_FduLoad_ID(this.get_ID());
		journal.setC_Period_ID(cPeriodID);
		journal.saveEx();
		
		//log.warning("Seteando lineas");
		
		// Seteo id del journal a las linas y las inserto
		for (MJournalLine line : journalLines){
			line.setGL_Journal_ID(journal.get_ID());
			line.setAD_Client_ID(journal.getAD_Client_ID());
			line.setAD_Org_ID(journal.getAD_Org_ID());
			line.saveEx();
		}

		// Completo asiento
		if (MSysConfig.getBooleanValue("UY_JOURNAL_COMPLETE_ONLOAD", false, this.getAD_Client_ID())){
			if (!journal.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException(journal.getProcessMsg());
			}			
		}
			
	}
	
	
	/***
	 * Genera asiento diario simple para los servicos de Fdu
	 * OpenUp Ltda. Issue #1332
	 * @author Guillermo Brust - 25/09/2013
	 * @see
	 */
	private void generateSimpleJournalStore(Timestamp dateAcct, BigDecimal totalDebe, BigDecimal totalHaber, HashMap<MJournalLine, Integer> journalLinesStore, int tipoAsiento) {

		if (totalDebe.compareTo(totalHaber) != 0){
			//throw new AdempiereException("Total Debitos no es igual a Total Creditos : " + totalDebe + " - " + totalHaber);
		}
		
		MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);
		MAcctSchema schema = client.getAcctSchema();
		MDocType doc = MJournal.getSimpleJournalDocType(getCtx(), null);
		
		if ((doc == null) || (doc.get_ID() <= 0)){
			throw new AdempiereException("No se pudo obtener Documento para Asiento Diario Simple");
		}
		
		MJournal journal = new MJournal(getCtx(), 0, get_TrxName());
		journal.setAD_Client_ID(this.getAD_Client_ID());
		journal.setAD_Org_ID(this.getAD_Org_ID());
		journal.setC_AcctSchema_ID(schema.get_ID());
		journal.setC_DocType_ID(doc.get_ID());
		journal.setDescription("Carga Cierre Cuenta Corriente : " + this.getDocumentNo());
		journal.setDateDoc(this.getDateTrx());

		//log.warning("Fecha Asiento : " + dateAcct.toString());
		//log.warning("Empresa : " + journal.getAD_Client_ID());
		//log.warning("Organizacion : " + journal.getAD_Org_ID());
		
		int cPeriodID = MPeriod.getC_Period_ID(getCtx(), dateAcct, journal.getAD_Org_ID());
		//log.warning("Periodo : " + cPeriodID);
		
		journal.setDateAcct(dateAcct);
		journal.setC_Currency_ID(schema.getC_Currency_ID());
		journal.setC_ConversionType_ID(114);
		journal.setCurrencyRate(Env.ONE);
		journal.setTotalDr(totalDebe);
		journal.setTotalCr(totalHaber);
		
		int glCategoryID = DB.getSQLValue(null, "SELECT max(gl_category_id) FROM GL_Category "
							+ "WHERE AD_Client_ID=? AND IsDefault='Y'", this.getAD_Client_ID());
		
		journal.setGL_Category_ID(glCategoryID);
		journal.setUY_FduLoad_ID(this.get_ID());
		journal.setC_Period_ID(cPeriodID);
		journal.saveEx();
		
		//log.warning("Seteando lineas");
		
		// Seteo id del journal a las linas y las inserto
		for (MJournalLine line: journalLinesStore.keySet()) {
			
			line.setGL_Journal_ID(journal.get_ID());
			line.setAD_Client_ID(journal.getAD_Client_ID());
			line.setAD_Org_ID(journal.getAD_Org_ID());
			line.saveEx();
						
			//Guardo en tabla auxiliar el comercio que estoy usando para realizar asiento, y la linea de asiento
			MFduStoreAcct stAcct = new MFduStoreAcct(this.getCtx(), 0, this.get_TrxName());
			stAcct.setUY_FduFile_ID(this.getUY_FduFile_ID());
			stAcct.setUY_FduLoad_ID(this.get_ID());
			stAcct.setUY_Fdu_Store_ID(journalLinesStore.get(line));				
			stAcct.setGL_Journal_ID(journal.get_ID());
			stAcct.setGL_JournalLine_ID(line.get_ID());
			stAcct.setTipoDato(new BigDecimal(tipoAsiento));
			stAcct.saveEx();					
		}	

		// Completo asiento
		if (MSysConfig.getBooleanValue("UY_JOURNAL_COMPLETE_ONLOAD", false, this.getAD_Client_ID())){
			if (!journal.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException(journal.getProcessMsg());
			}			
		}
			
	}

	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}

	
	/***
	 * Busco cuenta hija segun moneda. 
	 * OpenUp Ltda. Issue #60 
	 * @author Gabriel Vila - 05/11/2012
	 * @see
	 * @param cElementValueID
	 * @param cCurrencyID
	 * @return
	 */
	private MElementValue getEVForCurrency(int cElementValueID, int cCurrencyID) {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MElementValue value = new MElementValue(getCtx(), 0, null);
		
		try{
			
			String whereCurrency = "";
			if (cCurrencyID == 142){
				whereCurrency = " and ((ev.c_currency_id is null) OR (ev.c_currency_id = 142)) ";
			}
			else{
				whereCurrency = " and ev.c_currency_id =" + cCurrencyID;
			}
			
			String sql = " select tn.node_id " +
					     " from ad_treenode tn " +
					     " inner join c_elementvalue ev on tn.node_id = ev.c_elementvalue_id " +
					     " where ad_tree_id = 1000130 " +
					     " and parent_id =?" + 
					     " and ev.issummary = 'N'" + whereCurrency;
			
		    pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, cElementValueID);
			rs = pstmt.executeQuery();

			if (rs.next()){
				value = new MElementValue(getCtx(), rs.getInt(1), null);
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
	}
	
	/***
	 * Devuelve 
	 * OpenUp Ltda. Issue #155
	 * @author Guillermo Brust - 26/11/2012
	 *
	 */
	public boolean mesAnteriorCargadoAndNoFacturado() {	
		
		//Obtengo la fecha del ultimo dia del mes anterior que es la fecha en la cual se debiera emitir la factura
		Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		MPeriod periodoActual = MPeriod.get(getCtx(), today, 0);
		MPeriod periodoAnterior = new MPeriod(getCtx(), periodoActual.get_ID() - 1, null);
		
		//Primero debo verificar si no tiene una factura asociado el periodo anterior
		//en el caso que se encuetre asociado pero a una anulada debo eliminar el registro
		
		if(!this.periodoFacturado(periodoAnterior)) return this.asientosCompletos(periodoAnterior);
		else return false;
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 19/09/2013. ISSUE #
	 * Metodo que devuelve si estan todos los asientos completos de todas las cargas para el periodo anterior al actual
	 * 
	 * **/	
	private boolean periodoFacturado(MPeriod periodoAnterior){
				
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean retorno = false;
		
		try{		
					
			String sql = "SELECT inv.docstatus as docstatus" +
					     " FROM uy_fdu_periodinvoiced pinv" +
					     " INNER JOIN c_invoice inv on pinv.c_invoice_id = inv.c_invoice_id" +
					     " WHERE pinv.uy_fdufile_id = " + this.getUY_FduFile_ID() + 
					     " AND pinv.c_period_id = " + periodoAnterior.get_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());			
		    rs = pstmt.executeQuery();

			if (rs.next()){
				
				if(rs.getString("docstatus").equals("CO")){
					retorno = true;					
				
				}else if(rs.getString("docstatus").equals("VO")){
					
					//Acá tengo que eliminar el registro para que se ingrese de nuevo el registro cuando se realice la factura
					MFduPeriodInvoiced pinv = MFduPeriodInvoiced.forFduFileAndPeriod(getCtx(), this.getUY_FduFile_ID(), periodoAnterior.get_ID());
					pinv.delete(true);
				}
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}

	
	/**
	 * OpenUp. Guillermo Brust. 19/09/2013. ISSUE #
	 * Metodo que devuelve si estan todos los asientos completos de todas las cargas del fdufileID para el periodo anterior al actual
	 * 
	 * **/	
	private boolean asientosCompletos(MPeriod periodoAnterior){	
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean retorno = true;
		
		try{		
					
			String sql = "SELECT j.uy_fduload_id as load, count(j.gl_journal_id) as rows" +
						 " FROM gl_journal j" +
						 " WHERE j.uy_fduload_id in (select uy_fduload_id" + 
						 " 							 from uy_fduload" + 
						 " 							 where uy_fdufile_id = " + this.getUY_FduFile_ID() +")" +
						 " AND j.dateacct between '" + periodoAnterior.getStartDate() + "' and '" + periodoAnterior.getEndDate() + "'" +
						 " AND j.docstatus <> 'CO' AND j.docstatus <> 'VO'" +
						 " GROUP BY j.uy_fduload_id";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());			
		    rs = pstmt.executeQuery();

			if (rs.next()){
							
				if(rs.getInt("load") == 0 || rs.getInt("rows") > 0) retorno = false;
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}

	/***
	 * Se busca si existe algun registro en la fduline para este mes y el archivo pasado por parametro
	 * OpenUp Ltda. Issue #155
	 * @author Guillermo Brust - 26/11/2012
	 *
	 */
	public boolean existeRegistroMesActual(int uy_fdufile_id, int c_doctype_id) {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean retorno = false;
		
		try{		
					
			String sql = "SELECT count(distinct line.uy_fduload_id ) as rows" +
						 " FROM uy_fduloadline line" +
						 " INNER JOIN uy_fduload h ON h.uy_fduload_id = line.uy_fduload_id" +
						 " WHERE (select extract(month from dateacct)) = (select extract(month from current_date))" + 
						 " AND NOT EXISTS (SELECT c_invoice_id" +
						 " FROM c_invoice" + 
						 " WHERE c_doctype_id = ?" +
						 " AND (select extract(month from dateacct)) = (select extract(month from current_date) -1 ) )" +	
						 " AND h.uy_fdufile_id = ?";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt(1, c_doctype_id);
			pstmt.setInt(2, uy_fdufile_id);
		    rs = pstmt.executeQuery();

			if (rs.next()){
				if(rs.getInt("rows") > 0) retorno = true;
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}
	
	public static MFduLoad getMFduLoadForLastMonthAndFileID(Properties ctx, int uy_fduFile_id){
		
		String whereClause = "(select extract(month from line.dateacct)) = ((select extract(month from current_date) - 1)) AND " + 
				 X_UY_FduLoad.COLUMNNAME_UY_FduFile_ID + "=" + uy_fduFile_id;
		
		MFduLoad load = new Query(ctx, I_UY_FduLoad.Table_Name, whereClause, null)
		.firstOnly();
		
		return load;
	}

	
}

