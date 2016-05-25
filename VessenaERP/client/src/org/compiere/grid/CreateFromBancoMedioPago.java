package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.openup.model.MMediosPago;
import org.openup.model.MMovBancariosHdr;
import org.openup.model.MMovBancariosLine;

/***
 * Crea lineas para Estado de Cuenta Bancario a partir de medios de pago (cheques).
 * @author Gabriel Vila. 30/07/2010.
 * @version 
 */
public class CreateFromBancoMedioPago extends CreateFrom {

	//public MBankAccount bankAccount;
	
	/*
	 * Constructor por defecto.
	 */
	public CreateFromBancoMedioPago(GridTab gridTab) {
		super(gridTab);
		log.info(gridTab.toString());
	}

	/**
	 *  Dynamic Init
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle(Msg.translate(Env.getCtx(), "C_BankStatement_ID") + " .. " + Msg.translate(Env.getCtx(), "CreateFrom"));
		return true;
	}

	@Override
	public void info() {
		// TODO Auto-generated method stub
	}

	/**
	 *  Save Statement - Insert Data
	 *  @return true if saved
	 */
	public boolean save(IMiniTable miniTable, String trxName) {
		
		
		int UY_MovBancariosHdr_ID = ((Integer)getGridTab().getValue("UY_MovBancariosHdr_ID")).intValue();
		MMovBancariosHdr header = new MMovBancariosHdr(Env.getCtx(), UY_MovBancariosHdr_ID, trxName);
		
		log.config(header.toString());

		int bankAccountID = 0;
		int currencyID = 0;
		BigDecimal total = new BigDecimal(0);
		
		//  Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{	
				// ID de Medio de pago
				int medioPagoID = (Integer)miniTable.getValueAt(i, 11);
				MMediosPago mpago = new MMediosPago (Env.getCtx(), medioPagoID, null);
				
				// Nueva linea de movimiento bancario
				MMovBancariosLine linea = new MMovBancariosLine(Env.getCtx(), 0, trxName);
				linea.setUY_MovBancariosHdr_ID(UY_MovBancariosHdr_ID);
				linea.setUY_MediosPago_ID(medioPagoID);
				linea.setC_DocType_ID(mpago.getC_DocType_ID());
				linea.setDateTrx(mpago.getDateTrx());
				linea.setDateAcct(mpago.getDateTrx());
				linea.setC_BankAccount_ID(mpago.getC_BankAccount_ID());
				linea.setC_Currency_ID(mpago.getC_Currency_ID());
				linea.setCheckNo(mpago.getCheckNo());
				linea.set_ValueOfColumn("UY_PaymentRule_ID", mpago.get_Value("UY_PaymentRule_ID"));
				linea.setDueDate(mpago.getDueDate());
				KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 8);
				int C_BPartner_ID = pp.getKey();
				linea.setC_BPartner_ID(C_BPartner_ID);
				linea.setestado(mpago.getestado());
				linea.setuy_totalamt(mpago.getPayAmt());
				linea.setUY_PaymentRule_ID(mpago.getUY_PaymentRule_ID());
				linea.setTenderType("K");// OpenUp M.R. 30-09-2011 Issue# 652Agrego Linea para setear tendertype
		
				bankAccountID = mpago.getC_BankAccount_ID();
				currencyID = mpago.getC_Currency_ID();
				
				// Guardo linea en DB
				if (!linea.save())
					log.log(Level.SEVERE, "No se pudo guardar la linea : " + i);

			}   //   if selected
		}   //  for all rows

		// Procedo segun documento
		boolean procesarDefault = true;
		MDocType doc = (MDocType)header.getC_DocType();
		
		if (doc.getValue() != null){
			if (doc.getValue().equalsIgnoreCase("bcodepcheqpropio")){
				procesarDefault = false;
			}
		}
		
		if (procesarDefault){
			// Me aseguro de eliminar lineas ya existentes para este cabezal y de bancos distintos al de origen,
			// ya que no puedo mezclar bancos origenes
			this.deleteOldLines(UY_MovBancariosHdr_ID, bankAccountID, trxName);

			// Obtengo suma de importes de las lineas para el cabezal (las creadas ahora mas la ya existentes)
			total = this.getActualTotal(UY_MovBancariosHdr_ID, bankAccountID, trxName);
			
			// Actualizo informacion del cabezal a partir de informacion de las lineas
			header.setUY_C_BankAccount_From_ID(bankAccountID);
			header.setUY_C_Currency_From_ID(currencyID);
			
			// Si la moneda de las lineas es moneda nacional y la moneda del cabezal es moneda extranjera
			// debo desplegar tipo de cambio y conversion entre ambas monedas
			if (currencyID==this.getIDMonedaNacional(header.getAD_Client_ID()) && header.getC_Currency_ID()!=currencyID){
				header.setuy_totalmn(total);
				header.setDivideRate(MConversionRate.getDivideRate(currencyID, header.getC_Currency_ID(), 
										header.getDateAcct(), 0, 
										header.getAD_Client_ID(), header.getAD_Org_ID()));
				header.setuy_totalme(MConversionRate.convert(Env.getCtx(), total, 
										currencyID, header.getC_Currency_ID(),
										header.getDateAcct(), 0, 
										header.getAD_Client_ID(), header.getAD_Org_ID()));
				

			}
			// Si el banco destino es pesos y el origen es en moneda extranjera
			else if (currencyID!=header.getC_Currency_ID() && header.getC_Currency_ID()==this.getIDMonedaNacional(header.getAD_Client_ID())){
				header.setuy_totalme(Env.ZERO); // Dejo cero para que el usuario DIGITE manualente el importe en pesos
			}
			else
				header.setuy_totalme(total); // Seteo solo el total ya que no hay conversion en esta ventana
			
			// Si estoy en el documento descuento de cheques, tengo que actualizar el campo subtotal del cabezal
			String docBaseType = MDocType.getDocBaseType(header.getC_DocType_ID());
			if (docBaseType.equalsIgnoreCase(Doc.DOCTYPE_Banco_DescuentoChequesTerceros)){
				header.setuy_totalmn(total);
			}
			
			header.saveEx();
		}

		
		return true;
	}


	private void deleteOldLines(int UY_MovBancariosHdr_ID, int C_BankAccount_ID, String trxName){
		String sql = "DELETE FROM " + MMovBancariosLine.Table_Name + 
		" WHERE UY_MovBancariosHdr_ID=" + UY_MovBancariosHdr_ID +
		" AND C_BankAccount_ID <> " + C_BankAccount_ID;
		DB.executeUpdate(sql, trxName);
	}
	
	private BigDecimal getActualTotal(int UY_MovBancariosHdr_ID, int C_BankAccount_ID, String trxName){
		String sql = "SELECT COALESCE(SUM(uy_totalamt),0) " +
		" FROM " + MMovBancariosLine.Table_Name +
		" WHERE UY_MovBancariosHdr_ID=" + UY_MovBancariosHdr_ID +
		" AND C_BankAccount_ID = " + C_BankAccount_ID;

		return DB.getSQLValueBD(trxName, sql);
		
	}
	
	/**************************************************************************
	 *	Construct SQL Where Clause and define parameters
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return sql where clause
	 */
	public String getSQLWhere(Object BankAccount,String CheckNo, Object BPartner, Object DateFrom, Object DateTo, 
			Object AmtFrom, Object AmtTo, Object estadoCheque, Object fecVencFrom, Object fecVencTo)
	{
		//OpenUp. Nicolas Sarlabos. 11/01/2016. #4266.
		int UY_MovBancariosHdr_ID = ((Integer)getGridTab().getValue("UY_MovBancariosHdr_ID")).intValue();
		MMovBancariosHdr header = new MMovBancariosHdr(Env.getCtx(), UY_MovBancariosHdr_ID, null);
		
		StringBuffer sql = new StringBuffer(" WHERE mp.Processed='Y'"
		+ " AND mp.DocStatus IN ('CO','CL') AND mp.PayAmt<>0 AND mp.duedate <= '" + header.getDateTrx() + "'"); 
		//Fin OpenUp.
		
		if(estadoCheque!=null && estadoCheque.toString().length() > 0){
			String estado = (String)estadoCheque;
			if (!estado.equalsIgnoreCase("TRA")){
			    sql.append( " AND NOT EXISTS (SELECT * FROM UY_MovBancariosLine line " +
			    			" INNER JOIN UY_MovBancariosHdr hdr on line.uy_movbancarioshdr_id = hdr.uy_movbancarioshdr_id " + 
			    			" INNER JOIN c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
						    " WHERE mp.UY_MediosPago_ID = line.UY_MediosPago_ID AND line.UY_TotalAmt <> 0 AND hdr.DocStatus='CO' " +
						    " AND (doc.docbasetype <> 'CNF' AND doc.docbasetype <> 'DCP' AND doc.docbasetype <> 'DCT'))");
			}
		}
		else{
		    sql.append( " AND NOT EXISTS (SELECT * FROM UY_MovBancariosLine line " 
		    		//	Voided Bank Statements have 0 StmtAmt
		    			+ "WHERE mp.UY_MediosPago_ID = line.UY_MediosPago_ID AND line.UY_TotalAmt <> 0)");
		}
		
    
		//
		if (BankAccount != null)
			sql.append(" AND mp.C_BankAccount_ID = ?");
	    
		if (CheckNo.length() > 0)
			sql.append(" AND UPPER(mp.CheckNo) LIKE ?");
		//
		if (BPartner != null)
			sql.append(" AND mp.C_BPartner_ID=?");
		//
		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			if (from == null && to != null)
				sql.append(" AND TRUNC(mp.DateTrx) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(mp.DateTrx) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(mp.DateTrx) BETWEEN ? AND ?");
		}
		//
		if (fecVencFrom != null || fecVencTo != null)
		{
			Timestamp from = (Timestamp) fecVencFrom;
			Timestamp to = (Timestamp) fecVencTo;
			if (from == null && to != null)
				sql.append(" AND TRUNC(mp.DueDate) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(mp.DueDate) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(mp.DueDate) BETWEEN ? AND ?");
		}
		//
		if (AmtFrom != null || AmtTo != null)
		{
			BigDecimal from = (BigDecimal) AmtFrom;
			BigDecimal to = (BigDecimal) AmtTo;
			if (from == null && to != null)
				sql.append(" AND mp.PayAmt <= ?");
			else if (from != null && to == null)
				sql.append(" AND mp.PayAmt >= ?");
			else if (from != null && to != null)
				sql.append(" AND mp.PayAmt BETWEEN ? AND ?");
		}
		
		if(estadoCheque!=null)
			sql.append(" AND mp.estado=?");
		//if(TenderType != null && TenderType.toString().length() > 0)
		//	sql.append(" AND mp.TenderType=?");
/*		if(AuthCode.length() > 0 )
			sql.append(" AND p.R_AuthCode LIKE ?");*/

		log.fine(sql.toString());
		return sql.toString();
	}	//	getSQLWhere
	
	/**
	 *  Set Parameters for Query.
	 *  (as defined in getSQLWhere)
	 *  @param pstmt statement
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	void setParameters(PreparedStatement pstmt, boolean forCount, Object BankAccount, 
			String DocumentNo, Object BPartner, Object DateFrom, Object DateTo, 
			Object AmtFrom, Object AmtTo, Object estadoCheque, Object fecVencFrom, Object fecVencTo) 
	throws SQLException
	{
		int index = 1;
		
		if (BankAccount != null)
		{
			Integer bankAccountID = (Integer) BankAccount;
			pstmt.setInt(index++, bankAccountID.intValue());
			log.fine("BankAccount=" + bankAccountID);
		}
	
		if (DocumentNo.length() > 0)
			pstmt.setString(index++, getSQLText(DocumentNo));
		//
		if (BPartner != null)
		{
			Integer bp = (Integer) BPartner;
			pstmt.setInt(index++, bp.intValue());
			log.fine("BPartner=" + bp);
		}
		//
		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			log.fine("Date From=" + from + ", To=" + to);
			if (from == null && to != null)
				pstmt.setTimestamp(index++, to);
			else if (from != null && to == null)
				pstmt.setTimestamp(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}
		//
		if (fecVencFrom != null || fecVencTo != null)
		{
			Timestamp from = (Timestamp) fecVencFrom;
			Timestamp to = (Timestamp) fecVencTo;
			log.fine("FecVenc From=" + from + ", To=" + to);
			if (from == null && to != null)
				pstmt.setTimestamp(index++, to);
			else if (from != null && to == null)
				pstmt.setTimestamp(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}
		//
		if (AmtFrom != null || AmtTo != null)
		{
			BigDecimal from = (BigDecimal) AmtFrom;
			BigDecimal to = (BigDecimal) AmtTo;
			log.fine("Amt From=" + from + ", To=" + to);
			if (from == null && to != null)
				pstmt.setBigDecimal(index++, to);
			else if (from != null && to == null)
				pstmt.setBigDecimal(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setBigDecimal(index++, from);
				pstmt.setBigDecimal(index++, to);
			}
		}
		if(estadoCheque!=null && estadoCheque.toString().length() > 0)
			pstmt.setString(index++, (String) estadoCheque);
		/*if(TenderType!=null  && TenderType.toString().length() > 0 )
			pstmt.setString(index++, (String) TenderType);
		if(AuthCode.length() > 0 )
			pstmt.setString(index++, getSQLText(AuthCode));*/

	}   //  setParameters
	
	/**
	 *  Get SQL WHERE parameter
	 *  @param f field
	 *  @return Upper case text with % at the end
	 */
	private String getSQLText (String text)
	{
		String s = text.toUpperCase();
		if (!s.endsWith("%"))
			s += "%";
		log.fine( "String=" + s);
		return s;
	}   //  getSQLText
	
	
	protected Vector<Vector<Object>> getChequesData(Object BankAccount, String CheckNo, Object BPartner, Object DateFrom, Object DateTo, 
			Object AmtFrom, Object AmtTo, Object estadoCheque, Object fecVencFrom, Object fecVencTo)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	
		if (BankAccount == null){
			return data;
		}
		
		String sql = " SELECT mp.DateTrx, doc.name, mp.checkno, mp.C_Currency_ID, curr.ISO_Code, mp.PayAmt, " + 
					 	" currencyConvert(mp.PayAmt,mp.C_Currency_ID,ba.C_Currency_ID,mp.DateAcct,114,mp.AD_Client_ID,mp.AD_Org_ID) as ImporteMonedaBanco," +
					 	" mp.DueDate, mp.C_BPartner_ID, bp.name as bpartner, mp.tipomp, mp.estado, mp.UY_MediosPago_ID " + 
					 " FROM UY_MediosPago mp " + 
					 " INNER JOIN C_DocType doc ON mp.C_DocType_ID = doc.C_DocType_ID " +
					 " INNER JOIN C_Currency curr ON mp.C_Currency_ID = curr.C_Currency_ID " +
					 " INNER JOIN C_BankAccount ba ON mp.C_BankAccount_ID = ba.C_BankAccount_ID " +
					 " INNER JOIN C_BPartner bp ON mp.C_BPartner_ID = bp.C_BPartner_ID ";

		
		sql = sql + getSQLWhere(BankAccount, CheckNo, BPartner, DateFrom, DateTo, AmtFrom, AmtTo, estadoCheque, fecVencFrom, fecVencTo) + " ORDER BY mp.DateTrx";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			setParameters(pstmt, false, BankAccount, CheckNo, BPartner, DateFrom, DateTo, AmtFrom, AmtTo, estadoCheque, fecVencFrom, fecVencTo);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(11);
				line.add(new Boolean(false));       		//  0-Selection
				line.add(rs.getTimestamp("DateTrx"));       //  1-DateTrx
				line.add(rs.getString("name"));      		//  2-DocName
				line.add(rs.getString("checkno"));      	//  3-CheckNo
				KeyNamePair curr = new KeyNamePair(rs.getInt("C_Currency_ID"), rs.getString("ISO_Code"));
				line.add(curr);                     		//  4-Currency
				line.add(rs.getBigDecimal("PayAmt"));      	//  5-PayAmt
				line.add(rs.getBigDecimal("ImporteMonedaBanco"));      //  6-Conv Amt
				line.add(rs.getTimestamp("DueDate"));       //  7-DueDate
				KeyNamePair bpartner = new KeyNamePair(rs.getInt("C_BPartner_ID"), rs.getString("bpartner"));
				line.add(bpartner);      	//  8-BParner
				line.add(rs.getString("tipomp"));      		//  9-Tipo Medio Pago (Propio o Terceros)
				line.add(rs.getString("estado"));      		//  10-Estado (Entregado, Emitido, Cartera, etc)
				line.add(rs.getInt("UY_MediosPago_ID"));    //  11-ID
				
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return data;
	}

	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, Timestamp.class, true);     //  1-TrxDate
		miniTable.setColumnClass(2, String.class, true);    	//  2-DocName
		miniTable.setColumnClass(3, String.class, true);    	//  3-CheckNo
		miniTable.setColumnClass(4, String.class, true);        //  4-Currency
		miniTable.setColumnClass(5, BigDecimal.class, true);    //  5-PayAmt
		miniTable.setColumnClass(6, BigDecimal.class, true);    //  6-ConvAmount
		miniTable.setColumnClass(7, Timestamp.class, true);     //  7-DueDate
		miniTable.setColumnClass(8, String.class, true);    	//  8-BPartner
		miniTable.setColumnClass(9, String.class, true);    	//  9-Tipo Mp
		miniTable.setColumnClass(10, String.class, true);    	//  10-Estado
		miniTable.setColumnClass(11, Integer.class, true);    	//  11-ID
		//  Table UI
		miniTable.autoSize();
	}

	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(6);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_DocType_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "CheckNo"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_Currency_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "Amount"));
		columnNames.add(Msg.translate(Env.getCtx(), "ConvertedAmount"));
		columnNames.add(Msg.translate(Env.getCtx(), "DueDate"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "tipomp"));
		columnNames.add(Msg.translate(Env.getCtx(), "estado"));
		columnNames.add(Msg.getElement(Env.getCtx(), "UY_MediosPago_ID"));
	    
	    return columnNames;
	}
	
	/* Obtiene id de moneda nacional para la empresa actual*/
	private Integer getIDMonedaNacional(Integer idEmpresa){

		MClient client = new MClient(Env.getCtx(), idEmpresa, null);
		MAcctSchema schema = client.getAcctSchema();

		return schema.getC_Currency_ID();
	}
}
