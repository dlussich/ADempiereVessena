package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.X_C_BankAccount;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class CalloutMovBancarios extends CalloutEngine {

	public String setHeaderTotalForCRate(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		// Si no tengo divideRate o es Cero, salgo sin hacer nada.
		if (mTab.getValue("DivideRate")==null) return "";
		if (((BigDecimal)mTab.getValue("DivideRate")).compareTo(Env.ZERO)<=0) return "";

		BigDecimal montoBase = (BigDecimal)mTab.getValue("uy_totalmn");
		BigDecimal divideRate = (BigDecimal)mTab.getValue("DivideRate");
		
		// Seteo valor de total aplicando la tasa de cambio con segun monto base
		if (montoBase!=null && divideRate!=null)
			mTab.setValue("uy_totalme", montoBase.divide(divideRate, 2, RoundingMode.HALF_UP));
		
		return "";
	}
	
	public String setHeaderTotalConIntereses(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		if (value == null) return "";

		if (mTab.getValue("C_DocType_ID") == null) return "";
		
		// Obtengo tipo de documento actual
		int docTypeID = (Integer)mTab.getValue("C_DocType_ID");
		String docBaseType = MDocType.getDocBaseType(docTypeID);
		
		// Si estoy en documento de descuento de cheques de terceros
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DescuentoChequesTerceros)){
			// Si no tengo divideRate o es Cero, salgo sin hacer nada.
			if (mTab.getValue("uy_intereses")==null) return "";

			BigDecimal subTotal = (BigDecimal)mTab.getValue("uy_totalmn");
			BigDecimal intereses = (BigDecimal)mTab.getValue("uy_intereses");
			
			// Seteo valor de total = subtotal - intereses
			mTab.setValue("uy_totalme", subTotal.subtract(intereses));			
		}
		else if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_ValesBancarios)){
			// Si no tengo divideRate o es Cero, salgo sin hacer nada.
			if (mTab.getValue("uy_intereses")==null) return "";

			BigDecimal subTotal = (BigDecimal)mTab.getValue("uy_SubTotal");
			BigDecimal intereses = (BigDecimal)mTab.getValue("uy_intereses");
			mTab.setValue("uy_total_manual", (BigDecimal)mTab.getValue("uy_SubTotal"));	 //OpenUp M.R. 30-03-2011 Issue #473 Seteo Total Provisorio para prevenir que vale tenga importe cero
			
			// Seteo valor de total = subtotal - intereses
			mTab.setValue("uy_total_manual", subTotal.subtract(intereses));			
		}
		// OpenUp 23-03-2011 Issue# 473 agrego linea para realizar validacion de pago de vale bancario 
		else if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_PagoValeBancario)){
			// Aca hago el calculo de los intereses del vale bancario + subtotal 
			if (mTab.getValue("uy_intereses")==null) return "";

			BigDecimal subTotal = (BigDecimal)mTab.getValue("uy_SubTotal");
			BigDecimal intereses = (BigDecimal)mTab.getValue("uy_intereses");
			// Seteo valor de total = subtotal + intereses
			BigDecimal subintereses = subTotal.add(intereses);
			mTab.setValue("uy_total_manual", (BigDecimal)mTab.getValue("uy_SubTotal"));	//OpenUp M.R. 30-03-2011 Issue #473 Seteo Total Provisorio para prevenir que vale tenga importe cero		
			BigDecimal resto =  ((BigDecimal)mTab.getValue("uy_totalmn")).subtract((BigDecimal)mTab.getValue("totcobrado")); 
			if (resto.compareTo(subTotal)< 0){// OpenUp M.R. 01-04-2011 Issue #473 modifico comparacion, para que permita agregar intereses, cuando la suma del subtotal + intereses supere el monto toal del vale
				mTab.setValue("uy_total_manual",null);
				mTab.setValue("uy_SubTotal",null); 
				mTab.setValue("uy_intereses",null);
				return("Valor a pagar NO puede superar a saldo pendiente del vale");
			}
			mTab.setValue("uy_total_manual",subintereses);
		}		
		return "";
	}// Fin OpenUp

	public String setHeaderTotalDesdeManual(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		if (mTab.getValue("C_DocType_ID") == null) return "";
		
		// Obtengo tipo de documento actual
		int docTypeID = (Integer)mTab.getValue("C_DocType_ID");
		MDocType doc = new MDocType(ctx, docTypeID, null);
		String docBaseType = doc.getDocBaseType();
		
		// Si estoy en documento de transferencia bancaria
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_Transferencias)){
			// Si no tengo total manual, salgo sin hacer nada.
			if (mTab.getValue("uy_total_manual")==null) return "";

			BigDecimal totalManual = Env.ZERO;
			
			try{
				totalManual = new BigDecimal((Integer)mTab.getValue("uy_total_manual"));	
			}
			catch(Exception e){
				totalManual = (BigDecimal)mTab.getValue("uy_total_manual");
			}
			
			if (doc.getValue() != null){
				if (!doc.getValue().equalsIgnoreCase("transfdir")){
					mTab.setValue("uy_totalme", totalManual);
				}
			}
			else{
				mTab.setValue("uy_totalme", totalManual);	
			}
						
		}
				
		return "";
	}
	

	public String CheckNo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {		
		
		
		mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_uy_total_manual,null);
		// mTab.setValue(MMovBancariosHdr.COLUMNNAME_C_Currency_ID,null); OpenUp M.R. 15-09-2011 Issue#652 comento lineas, porque es necesario tener la moneda seteada, para realizar validaciones
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_C_BPartner_ID,null);
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_Date1,null);	
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_DueDate,null);
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_oldstatus,null);
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_estado,null);
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_C_BankAccount_ID,null);		
		// No change needed with no value. Defensive  
		if (value==null)  { 
			return("");
		}
		Integer bankAccount_ID=(Integer)mTab.getValue(X_C_BankAccount.COLUMNNAME_C_BankAccount_ID);
		if( bankAccount_ID==null){
			return ("Seleccione una cuenta bancaria");
		}
		
		// Get the id
		String CheckNo=(String) value.toString();
		
		// Create the object
		MMediosPago mediosPago=null;
		
		// Get document type id
		Integer C_DocType_ID=(Integer) mTab.getValue("C_DocType_ID");			// TODO: use model COLUMNAME
		
		// Check for a valid document type. Defensive
		if ((C_DocType_ID==null)||(C_DocType_ID.equals(0))) {
			return("");
		}
		
		//MARIO REYES OPENUP 21-3-11
		String  estados ="";
		//OpenUp M.R. 16-06-2011 Issue #700 modifico para setear filtros de sql
		String Inner = "";
		String cBank = "";
		MDocType doc = new MDocType(ctx, C_DocType_ID, null);
		String docBaseType = doc.getDocBaseType();
		if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_RechazoChequesPropios)){
		 
			estados = " AND a.estado = 'PAG'";
			//OpenUp M.R. 16-06-2011 Issue #700 seteo inner join  y cuenta bancaria
			/*
			 * Inner =
			 * "INNER JOIN  UY_MovBancariosLine b on a.UY_MediosPago_ID = b.UY_MediosPago_ID  "
			 * +
			 * "INNER JOIN  UY_MovBancariosHdr c on b.UY_MovBancariosHdr_ID = C.UY_MovBancariosHdr_ID  AND c.DocStatus = 'CO' "
			 * ; cBank = ", c.C_BankAccount_ID";
			 */
		}
		else {
			if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_RechazoChequesTerceros)){
						 
				estados = " AND a.estado IN ('DEP','DES','CAR')";
				//OpenUp M.R. 16-06-2011 Issue #700 seteo inner join  y cuenta bancaria
				Inner = "LEFT JOIN  UY_MovBancariosLine b on a.UY_MediosPago_ID = b.UY_MediosPago_ID  " +
		        		"LEFT JOIN  UY_MovBancariosHdr c on b.UY_MovBancariosHdr_ID = C.UY_MovBancariosHdr_ID  AND c.DocStatus = 'CO' " ;
						cBank = ", c.C_BankAccount_ID";
			}
			else {
			//OpenUp M.R. 16-06-2011 Issue #700 seteo estado del documento llamado
				if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_CobroConformes)){
					MMediosPago mpCC = new MMediosPago(Env.getCtx(), ((Integer)value).intValue(), null);
					if (mpCC.get_ID() > 0) 
						CheckNo = mpCC.getCheckNo();
					estados = " AND a.estado = 'CAR' ";
				}

			else {
				return("");
			}
		}
		}
		//Fin OpenUp	, c.C_BankAccount_ID
			
		// Build the SQL String, statatement and resultSet objects the get the object
		// Aca agrego el parametro estados para filtrar documentos
		//OpenUp M.R. 16-06-2011 Issue #700 aca modifique la consulta para traer el conforme, ya que no necesito los inner join 
		String sql="SELECT  a.*" + cBank +" FROM UY_MediosPago a "+
					Inner + 
				   "WHERE a.checkno=? and a.C_BankAccount_ID=?" + estados ; 
				     
		PreparedStatement pstmt=null;								 
		ResultSet rs=null;
		BigDecimal depositado =BigDecimal.ZERO;
		try {
			
			pstmt=DB.prepareStatement(sql,null);		// Create the statement
			pstmt.setString(1,CheckNo);	
			pstmt.setInt(2, bankAccount_ID);
			rs = pstmt.executeQuery();
			
			// Read the first record and get a new object
			if (rs.next()) {
				mediosPago=new MMediosPago(ctx, rs, null);
				depositado = rs.getBigDecimal("C_BankAccount_ID");
			}
		}
		catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
		finally {										// Close and reset the statemente and recordset
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		// Defensive
		if (mediosPago==null) {
			// Reset fields to defualts values
			
			return("");
		}
		
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_uy_total_manual, mediosPago.getPayAmt());
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_C_Currency_ID, mediosPago.getC_Currency_ID());
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_C_BPartner_ID, mediosPago.getC_BPartner_ID());
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_Date1 ,mediosPago.getDateTrx ());
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_DueDate ,mediosPago.getDueDate ());
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_oldstatus, mediosPago.getestado()); // Agrego campo para mantener estado del cheque anterior
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_UY_MediosPago_ID, mediosPago.getUY_MediosPago_ID());
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_estado, mediosPago.getestado());
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_UY_C_BankAccount_From_ID, depositado );
				
		
		return("");		
	}
	// OpenUp M.R. 23-03-2011 issue# 473 Agrego nueva consulta para traer datos necesario para calculo de valores a pagar
public String setInfoPagoVale(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {		
		
		
				
		// No change needed with no value. Defensive  
		if (value==null)  { 
			return("");
		}
		Integer bankAccount_ID=(Integer)mTab.getValue(X_C_BankAccount.COLUMNNAME_C_BankAccount_ID);
		if( bankAccount_ID==null){
			return ("Seleccione una cuenta bancaria");
		}
		
		// Get the id
		int UY_NumVale=(Integer) mTab.getValue("UY_NumVale");
		
		// Create the object
		MMovBancariosHdr vale=null;
		
		// Get document type id
		Integer C_DocType_ID=(Integer) mTab.getValue("C_DocType_ID");			// TODO: use model COLUMNAME
		
		// Check for a valid document type. Defensive
		if ((C_DocType_ID==null)||(C_DocType_ID.equals(0))) {
			return("");
		}
		
			
			
		// Build the SQL String, statatement and resultSet objects the get the object
		// Aca agrego el parametro estados para filtrar documentos
		String sql="SELECT * FROM UY_MovBancariosHdr WHERE UY_MovBancariosHdr_ID=? and C_BankAccount_ID=?" ; 
		PreparedStatement pstmt=null;								 
		ResultSet rs=null;
		try {
			pstmt=DB.prepareStatement(sql,null);		// Create the statement
			pstmt.setInt(1,UY_NumVale);	
			pstmt.setInt(2, bankAccount_ID);
			rs = pstmt.executeQuery();
			
			// Read the first record and get a new object
			if (rs.next()) {
				vale=new MMovBancariosHdr(ctx, rs, null);		
			}
		}
		catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
		finally {										// Close and reset the statemente and recordset
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//OpenUp M.R. 01-04-2011 Issue#473 Comento Linea para modificar datos de suma obtenida
		// OpenUp M.R. 23-03-2011 issue#473 Agrego sql para traer el monto pagado del vale bancarios
		
		//OpenUp Nicolas Garcia 26-04-2011 Issue #624 Se Comenta linea y se agrega linea.
		String sql1="SELECT COALESCE(SUM(uy_SubTotal),0)as cobrado FROM UY_MovBancariosHdr WHERE UY_NumVale=? and C_BankAccount_ID=? AND DocStatus in ('CO','CL')" ; 
		//String sql1="SELECT COALESCE(SUM(uy_SubTotal),0)as cobrado FROM UY_MovBancariosHdr WHERE UY_NumVale=? and C_BankAccount_ID=? AND DocStatus in ('CO','CL')" ;
		// Fin N.G
		
		PreparedStatement pstmt1=null;								 
		ResultSet rs1=null;
		BigDecimal cobrado = BigDecimal.ZERO;
		try {
			pstmt1=DB.prepareStatement(sql1,null);		// Create the statement
			pstmt1.setString(1,((Integer)UY_NumVale).toString());	
			pstmt1.setInt(2, bankAccount_ID);
			rs1 = pstmt1.executeQuery();
			
			// Read the first record and get a new object
			
			if (rs1.next()) {
				cobrado = rs1.getBigDecimal("cobrado");	
				
			}
		}
		catch (Exception e) {
			log.log(Level.SEVERE, sql1, e);
		}
		finally {										// Close and reset the statemente and recordset
			DB.close(rs1, pstmt1);
			rs1 = null; pstmt1 = null;
		}
		// Defensive
		if (vale==null) {
			// Reset fields to defualts values
			
			return("");
		}
		
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_isamortizable, vale.isamortizable());
		
		//OpenUp Nicolas Garcia 26-04-2011 Issue #624 Se cambia vale.getuy_total_manual por vale.getUY_SubTotal
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_uy_totalmn, vale.getUY_SubTotal());// Valor Total del Vale 
		// Fin N.G
		
		mTab.setValue(MMovBancariosHdr.COLUMNNAME_totcobrado, cobrado);// Monto Amortizado
		//BigDecimal resto = vale.getuy_totalmn().subtract(cobrado);  // //OpenUp M.R. 30-03-2011 Issue #473 comento variable no utilizada
	
		return("");		
	}
	// Fin OpenUp


	/***
	 * Al cambiar la cuenta bancaria tengo que actualizar la moneda.
	 * OpenUp Ltda. Issue #869 
	 * @author Gabriel Vila - 24/05/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeBankAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
	
		int cBankAccountID = ((Integer)value).intValue();
		
		if (cBankAccountID <= 0) return "";
	
		MBankAccount ba = new MBankAccount(Env.getCtx(), cBankAccountID, null);
		
		if (ba.get_ID() > 0){
			mTab.setValue(X_UY_CashMove.COLUMNNAME_C_Currency_ID, ba.getC_Currency_ID());
		}
		
		return "";
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Para cambio de cheques, necesito buscar por UY_MediosPago_ID. Una vez que tengo ese ID debo setear el nro de cheque
	 * asociado para poder disparar el Callout que tiene el campo de cheque.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 22/03/2011
	 */
	public String setMedioPagoInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {		
		
		// No change needed with no value. Defensive  
		if (value==null)  { 
			
			return "";
		}
		// OpenUp Nicolas Garcia 23/02/20011 #issue 561
		Integer idMediosPago = (Integer)value;
		MMediosPago medioPago = new MMediosPago(ctx, idMediosPago, null);
		if(medioPago!=null){
			
			//Set Parametros
			if(mTab.getValue(X_UY_MovBancariosHdr.COLUMNNAME_C_BankAccount_ID)==null)
				mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_C_BankAccount_ID , medioPago.getC_BankAccount_ID());
			
			/*
			String cheque=medioPago.getCheckNo();
			if(mTab.getValue(X_UY_MovBancariosHdr.COLUMNNAME_CheckNo)!=null){
				if(!(mTab.getValue(X_UY_MovBancariosHdr.COLUMNNAME_CheckNo).equals(cheque.toString()))){
					mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_CheckNo, cheque.toString());
				}
			}
			else{
				mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_CheckNo, cheque.toString());
			}
			*/
			
			mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_C_BPartner_ID, medioPago.getC_BPartner_ID());
			mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_Date1 ,medioPago.getDateTrx ());
			mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_DueDate ,medioPago.getDueDate ());				
			mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_uy_total_manual, medioPago.getPayAmt());
			
			if(mTab.getValue(X_UY_MovBancariosHdr.COLUMNNAME_C_Currency_ID)==null)
				mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_C_Currency_ID, medioPago.getC_Currency_ID());				
	
			mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_oldstatus, medioPago.getestado()); // Agrego campo para mantener estado del cheque anterior
			mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_estado, medioPago.getestado());
			// mTab.setValue(X_UY_MovBancariosHdr.COLUMNNAME_CheckNo, medioPago.getCheckNo()); OpenUp M.R. 15-09-2011 Comento lineas para poder setear checkno desde la ventana
	
		}
		//end Nicolas 
		return("");		
	}
	
	/**
	 * OpenUp.	#676
	 * Descripcion : Para Conciliacion Bancaria es necesario setear las tolerancias en importe y dias de la cuenta bancaria seleccionada.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 17/04/2013
	 */
	public String setAccountTolerance(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int accountID = (Integer)value;
		if (accountID <= 0) return "";
		
		MBankAccount account = new MBankAccount(ctx, accountID, null);
		
		//seteo tolerancia
		mTab.setValue(X_UY_Conciliation.COLUMNNAME_Tolerance, account.getTolerance());
		//OpenUp. Nicolas Sarlabos. 16/05/2013. #837. se carga tolerancia en importe para la cuenta bancaria
		mTab.setValue(X_UY_Conciliation.COLUMNNAME_ToleranceAmount, account.getToleranceAmount());
		//Fin OpenUp.
			
		return ("");
			
	}
	
	/**
	 * OpenUp.	#802
	 * Descripcion : En la ventana de mantenimiento de Cargo Contable, al seleccionar un concepto de flujo de fondos debo
	 * setear el tipo del concepto de flujo, permitiendo ser cambiado por el usuario.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 08/05/2013
	 */
	public String setTypeCashFlow(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
		
		int conceptID = (Integer)value;
		if (conceptID <= 0) return "";
		
		MCashFlowConcept concept = new MCashFlowConcept(ctx, conceptID, null);
		
		//seteo tipo de concepto
		mTab.setValue(X_UY_Cargo.COLUMNNAME_tipo, concept.gettipo());
			
		return ("");
			
	}
	
	/**
	 * OpenUp.	#760
	 * Descripcion : En la ventana de Registro de Cargo Bancario, al ingresar un cargo debo
	 * setear el campo "IsDebit", para indicar si es al debe o al haber, permitiendo cambiar el valor al usuario.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 10/05/2013
	 */
	public String setFromCharge(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
	
		if (value == null) return "";
	
		int cargoID = (Integer)value;
		if (cargoID <= 0) return "";
	
		String sql = "select uy_cargoline_id" +
				" from uy_cargoline" +
				" where uy_cargo_id=" + cargoID;
		int cargoLineID = DB.getSQLValueEx(null, sql);
	
		if(cargoLineID > 0){
	
			MCargoLine cargoLine = new MCargoLine(ctx, cargoLineID, null);
	
			//seteo campo al DEBE/HABER
			mTab.setValue(X_UY_CargoRegisterLine.COLUMNNAME_IsDebit, cargoLine.isDebit());
		}
	
		return ("");
	
	}
	
	/***
	 * Metodo que guarda el cabezal de conciliacion cuando se modifica un check
	 * de la grilla del sistema o del banco, para actualziar los totalizadores parciales.
	 * OpenUp Ltda. Issue #1487
	 * @author Nicolas Sarlabos - 06/11/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String saveConciliation(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";
	
		mTab.dataSave(true);
				
		return "";
	}
	
	/***
	 * Metodo que obtiene y setea el string de usuarios autorizantes para el centro de costo
	 * ingresado en la linea de salida de fondo fijo.
	 * OpenUp Ltda. Issue #1278
	 * @author Nicolas Sarlabos - 19/12/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setApproverUser(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {

		if (value == null) return "";

		int ccostoID = (Integer)value;
		if (ccostoID <= 0) return "";

		String valor = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;			

		try{

			sql = "select distinct a.description from ad_user a" +
	                  " inner join uy_popolicyuser pu on a.ad_user_id = pu.ad_user_id" +
	                  " inner join uy_posection sect on pu.uy_posection_id = sect.uy_posection_id" +
	                  " where a.isactive = 'Y' and sect.c_activity_id_1 = '" + ccostoID +
	                  "' and pu.nivel = '1'";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			while(rs.next()){

				if(valor==null){

					valor = rs.getString("description");

				} else {

					valor += "/" + rs.getString("description");	    		

				}	  
			}
			
			if(valor != null){
				
				mTab.setValue("ApprovedBy", valor);
				
			} else throw new AdempiereException("No se obtuvieron usuarios autorizantes para el centro de costos ingresado");

		}catch (Exception e){
			throw new AdempiereException(e);
		}finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return "";

	}
	
	/***
	 * Metodo que setea los campos del cabezal a partir de la salida de fondo fijo seleccionada.
	 * Dependiendo del documento actual se setean determinados campos.
	 * OpenUp Ltda. Issue #1278
	 * @author Nicolas Sarlabos - 19/12/2013 
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromCashOut(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int cashID = (Integer)value;
		if (cashID <= 0) return "";

		MFFCashOut cash = new MFFCashOut(ctx, cashID, null);

		int docID = Env.getContextAsInt(ctx, WindowNo, WindowNo, "C_DocType_ID");

		MDocType doc = new MDocType(ctx, docID, null);

		if(doc.getValue()!=null){

			if(doc.getValue().equalsIgnoreCase("cashrepay")){ //si es rendicion de FF

				mTab.setValue("Description", cash.getDescription());
				mTab.setValue("UY_FF_Branch_ID", cash.getUY_FF_Branch_ID());
				mTab.setValue("UY_POSection_ID", cash.getUY_POSection_ID());
				mTab.setValue("C_Currency_ID", cash.getC_Currency_ID());
				mTab.setValue("Amount", cash.getGrandTotal());

			} else if(doc.getValue().equalsIgnoreCase("cashreturn")){ //si es devolucion de FF

				mTab.setValue("UY_FF_Branch_ID", cash.getUY_FF_Branch_ID());
				mTab.setValue("UY_POSection_ID", cash.getUY_POSection_ID());
				mTab.setValue("C_Currency_ID", cash.getC_Currency_ID());
				mTab.setValue("Amount", cash.getGrandTotal());
				
			} else throw new AdempiereException("Error al obtener tipo de documento");	
		} 

		return "";		

	}	

	/***
	 * Al seleccionar una libreta de medios de pago, se setea el medio de pago en la emisión.
	 * OpenUp Ltda. Issue #5009
	 * @author gabriel - Nov 6, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setMedioPago(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
	
		if (value == null) return "";

		int reamID = (Integer)value;
		if (reamID <= 0) return "";

		MCheckReam ream = new MCheckReam(ctx, reamID, null);
		
		mTab.setValue(X_UY_MediosPago.COLUMNNAME_UY_PaymentRule_ID, ream.getUY_PaymentRule_ID());

		return "";		
	}
	
	/***
	 * Al seleccionar un cheque en una remesa de cheques (grilla de cobranzas), se cargan los datos del mismo.
	 * OpenUp Ltda. Issue #5258
	 * @author Nicolas Sarlabos - 14/01/2016
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setFromMedioPago(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {
		
		if (value == null) return "";
	
		int medioID = ((Integer)value).intValue();
		
		if (medioID <= 0) return "";
	
		MMediosPago mp = new MMediosPago(Env.getCtx(), medioID, null);
		
		if (mp.get_ID() > 0){
			
			mTab.setValue(X_UY_CashRemittanceCharge.COLUMNNAME_C_Currency_ID, mp.getC_Currency_ID());
			mTab.setValue(X_UY_CashRemittanceCharge.COLUMNNAME_Amount, mp.getPayAmt());
			mTab.setValue(X_UY_CashRemittanceCharge.COLUMNNAME_C_BankAccount_ID, mp.getC_BankAccount_ID());
			mTab.setValue(X_UY_CashRemittanceCharge.COLUMNNAME_DueDate, mp.getDueDate());
			mTab.setValue(X_UY_CashRemittanceCharge.COLUMNNAME_C_BPartner_ID, mp.getC_BPartner_ID());			
			
		}
		
		return "";
	}
	
}

