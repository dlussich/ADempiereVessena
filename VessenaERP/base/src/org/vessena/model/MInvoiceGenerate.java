package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPeriod;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.openup.util.OpenUpConnectionCGM;

public class MInvoiceGenerate extends X_UY_InvoiceGenerate implements DocAction {
	
	private String processMsg = null;
	private boolean justPrepared = false;

	public MInvoiceGenerate(Properties ctx, int UY_InvoiceGenerate_ID, String trxName) {
		super(ctx, UY_InvoiceGenerate_ID, trxName);
		
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
		this.CalculateCommission();
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
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
	
		
		
		
		
		CompleteInvoices();
		
		
		
		
		
		
		
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
	
	public void CompleteInvoices() {
		
		// Elimino los errores para este MInvoiceGenerate
		DB.executeUpdate("DELETE FROM UY_InvoiceGenerateLog WHERE UY_InvoiceGenerate_ID = " + this.getUY_InvoiceGenerate_ID(), get_TrxName());

		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());

		
		Connection conn = OpenUpConnectionCGM.getConnectionToSqlServer();
		Statement stmtUpdate = null;
		try {
			conn.setAutoCommit(false); //esto para iniciar una transaccion
			stmtUpdate = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		} catch (Exception e) { }
		
		
		PreparedStatement pstmt = DB.prepareStatement("SELECT UY_InvoiceGenerateLine_ID FROM UY_InvoiceGenerateLine WHERE UY_InvoiceGenerate_ID = " + get_ID() + " AND isSelected = 'Y'", get_TrxName());
		try {
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MInvoiceGenerateLine mInvoiceGenerateLine = new MInvoiceGenerateLine(getCtx(), rs.getInt("UY_InvoiceGenerateLine_ID"), get_TrxName());
				MInvoice mInvoice = (MInvoice) mInvoiceGenerateLine.getC_Invoice();
				if (mInvoice != null) {

					String trxAuxName = Trx.createTrxName();
					Trx trxAux = Trx.get(trxAuxName, true);
					
					mInvoice.set_TrxName(trxAuxName);
					
					try {
						
						if (!mInvoice.processIt(MInvoice.DOCACTION_Complete)) {
							logError("Error al procesar factura " + mInvoice.getDocumentNo());
							trxAux.rollback();
							trxAux.close();
						} else {
							setCompleteItDateToInteface(stmtUpdate, mInvoiceGenerateLine, trxAuxName, currentTime);
							trxAux.close();
						}
					} catch (Exception e) {
						logError(e.getMessage());
						trxAux.rollback();
						trxAux.close();
					}
					
					
					
				}
			}
			
			
			stmtUpdate.close();
			conn.commit();
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setCompleteItDateToInteface(Statement stmt, MInvoiceGenerateLine mInvL, String trxAuxName, Timestamp currentTimestamp) throws SQLException {
		PreparedStatement pstmt = DB.prepareStatement("SELECT UY_InvoiceGenerateDetail_ID FROM UY_InvoiceGenerateDetail WHERE UY_InvoiceGenerateLine_ID = " + mInvL.get_ID(), trxAuxName);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			MInvoiceGenerateDetail mInvD = new MInvoiceGenerateDetail(getCtx(), rs.getInt("UY_InvoiceGenerateDetail_ID"), trxAuxName);
			int idOpenUp_pago = mInvD.getpago_id();
			
			stmt.executeUpdate("UPDATE openup_pagos SET procesadoAd360 = '" + currentTimestamp + "' WHERE pago_id = " + idOpenUp_pago);
		}
	}

	public void CalculateCommission() {
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int cantPagosProcesados = 0;
		int cantErrores = 0;
		Trx trxAux = null;
		String trxAuxName = null;
		MBPartner mbPartner = null;
		MCurrency mCurrency = null;
		MInvoiceGenerateLine mInvoiceGenerateLine = null;
		MInvoiceGenerateDetail mInvoiceGenerateDetail = null;
		ArrayList<MInvoiceGenerateDetail> mInvoiceGenerateDetails = new ArrayList<MInvoiceGenerateDetail>();
		
		// Elimino todo datos originado en una corrida anterior
		this.deletePreviousPayments();
		
		
		if (getStartDate() == null || getEndDate() == null) throw new AdempiereException("Ingrese fecha de desde y fecha hasta");
		
		// Reviso si hay alguna empresa para filtrar
		String idEmpresaFiltro = "";
		boolean filtrarPorEmpresa = false;
		try {
			pstmt = DB.prepareStatement("SELECT bp.value FROM UY_InvoiceGenerate_BP AS ig INNER JOIN C_BPartner AS bp ON ig.C_BPartner_ID = bp.C_BPartner_ID WHERE UY_InvoiceGenerate_ID = " + get_ID(), get_TrxName());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if (filtrarPorEmpresa) idEmpresaFiltro += ", ";
				idEmpresaFiltro += rs.getInt("value");
				filtrarPorEmpresa = true;
			}
		} catch (SQLException e) {
			throw new AdempiereException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		// Aviso de clientes con cobros que no estan definido en adempiere
		this.validateCustomers();
				
		
		String sqlGetPagos = "SELECT det.UY_InvoiceGenerateDetail_ID "
						   + " FROM UY_InvoiceGenerateDetail det "
				           + " INNER JOIN c_bpartner bp on (det.empresa_id = CAST(bp.value as numeric(10,0))) "
						   + " WHERE det.procesadoAd360 IS NULL "
						   + " AND det.fecha BETWEEN '" + getStartDate() + "' AND '" + getEndDate() + "'"
						   + (filtrarPorEmpresa ? " AND det.empresa_id IN (" + idEmpresaFiltro + ")": "") 
						   + " AND det.UY_InvoiceGenerateLine_ID IS NULL " 
						   + "ORDER BY det.empresa_id, det.moneda_id";
		pstmt = DB.prepareStatement (sqlGetPagos, get_TrxName());

		try {
			rs = pstmt.executeQuery ();
			while (rs.next()){
				
				try {
					
					// Recorro los pagos
					boolean conErrores = false;
	
					if (trxAux == null) {
						// Genero nueva transaccion
						trxAuxName = Trx.createTrxName();
						trxAux = Trx.get(trxAuxName, true);
						
						// Establezco esta transaccion para todos los detalles no procesados
						for (MInvoiceGenerateDetail detail : mInvoiceGenerateDetails) {
							detail.set_TrxName(trxAuxName);
						}
					}
					
					mInvoiceGenerateDetail = new MInvoiceGenerateDetail(getCtx(), rs.getInt("UY_InvoiceGenerateDetail_ID"), trxAuxName);
					
					
					// Obtengo Cliente
					int mbPartner_id = -1;
					try {
						mbPartner_id = Integer.valueOf( DB.getSQLValueString(get_TrxName(), "SELECT C_BPartner_ID FROM C_BPartner WHERE value = '" + mInvoiceGenerateDetail.getempresa_id() + "'"));
					} catch (Exception e) {
						mbPartner_id = -1;
					}
					
					if (mbPartner_id == -1) {
						// Cliente no existe, logueo y no proceso ese pago
						logError("Empresa " + mInvoiceGenerateDetail.getempresa_id() + " no encontrada en cobro " + mInvoiceGenerateDetail.getpago_id());
						conErrores = true;
						cantErrores++;
					}
					
					
					
//					if (conErrores) {
//						trxAux.rollback();
//						trxAux.close();
//						trxAux = null;
//						mInvoiceGenerateDetails.clear();
//					} else {
					if (!conErrores) {
						// Proceso el pago - Corte de control (empresa, moneda)
						
						
						MBPartner currentMbPartner = new MBPartner(getCtx(), mbPartner_id, get_TrxName());
						MCurrency currentMCurrency = (MCurrency) mInvoiceGenerateDetail.getC_Currency();
						
						if (mbPartner == null) mbPartner = currentMbPartner;
						if (mCurrency == null) mCurrency = currentMCurrency;
						
						
						
						if (mbPartner.get_ID() != currentMbPartner.get_ID() || mCurrency.get_ID() != currentMCurrency.get_ID()) {
							
							System.out.println(mbPartner.getValue());
							
							try {
								proccessLine(mbPartner, mCurrency, mInvoiceGenerateDetails, trxAuxName);
							} catch (Exception e) {
								logError(e.getMessage());
								if (trxAux != null) {
									trxAux.rollback();
								}
							}
							
							mbPartner = currentMbPartner;
							mCurrency = currentMCurrency;
							mInvoiceGenerateDetails.clear();
							trxAux.close();
							trxAux = null;
						}
						
						/*
						 * Raúl Capecce - OpenUp Ltda
						 * #5272 - Solicitan que no se tengan en cuenta los pagos negativos para el proceso de facturación masiva
						 * 2015-12-30
						 * 
						 * Raúl Capecce - OpenUp Ltda
						 * #5357 - Solicitan que ahora si se tengan en cuenta los pagos negativos para el proceso de facturación masiva
						 * 2016-01-22
						 */
						MInvoiceGenerateDetail mInvDetTmp = new MInvoiceGenerateDetail(getCtx(), rs.getInt("UY_InvoiceGenerateDetail_ID"), trxAuxName);
						//if (mInvoiceGenerateDetail.getmonto().intValue() < 0) {
						//	logError("No se procesa pago " + mInvDetTmp.getpago_id() + " por tener monto negativo");
						//} else {
							mInvoiceGenerateDetails.add(mInvDetTmp);
						//}
						
					}
				} catch (Exception e) {
					logError(e.getMessage());
					trxAux.rollback();
					trxAux.close();
					trxAux = null;
					mInvoiceGenerateDetails.clear();
					mbPartner = null;
					mCurrency = null;
				}
			}
			if (mInvoiceGenerateDetails.size() > 0) { // Recorro los faltantes
				try {
					if (trxAux == null) {
						// Genero nueva transaccion
						trxAuxName = Trx.createTrxName();
						trxAux = Trx.get(trxAuxName, true);
					}
					// Establezco esta transaccion para todos los detalles no procesados
					for (MInvoiceGenerateDetail detail : mInvoiceGenerateDetails) {
						detail.set_TrxName(trxAuxName);
					}
					
					proccessLine(mbPartner, mCurrency, mInvoiceGenerateDetails, trxAuxName);
				} catch (Exception e) {
					trxAux.rollback();
				} finally {
					if (trxAux != null) {
						trxAux.close();
						trxAux = null;
					}
				}
			}
		} catch (SQLException e) {
			throw new AdempiereException(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

 		
//		throw new AdempiereException("Cant procesados: " + cantPagosProcesados + " - Cant errores: " + cantErrores);
		
	}

	/***
	 * Valido y aviso clientes no definidos en adempiere y que tiene cobros en la tabla de interface
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Jan 27, 2016
	 */
	private void validateCustomers() {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {

			sql = " select distinct(empresa_id) "
					+ " from uy_invoicegeneratedetail "
					+ " where empresa_id not in (select cast(value as numeric(10,0)) from c_bpartner) "
					+ " order by empresa_id";
			
			pstmt = DB.prepareStatement(sql, get_TrxName());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				logError("Empresa " + rs.getInt(1) + " no definida en el sistema.");				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		
	}

	/**
	 * Registra inconvenientes en el procesado
	 * #4868 - 2015-11-27
	 * @param msj Mensaje a logear
	 * @author Raul Capecce
	 */
	protected void logError(String msj) {
		MInvoiceGenerateLog mInvoiceGenerateLog = new MInvoiceGenerateLog(getCtx(), 0, get_TrxName());
		mInvoiceGenerateLog.setUY_InvoiceGenerate_ID(this.get_ID());
		mInvoiceGenerateLog.setMessage(msj);
		mInvoiceGenerateLog.saveEx();
		
		System.out.println(msj);
	}
	
	/**
	 * 
	 * Genero MInvoiceGenerateLine y MInvoice para la pareja MBPartner - MCurrency
	 * #4868 - 2015-11-27
	 * @author Raul Capecce
	 * @param mbPartner Cliente
	 * @param mCurrency Moneda
	 * @param mInvoiceGenerateDetails ArrayList de lineas de Pago para la pareja MBPartner - MCurrency
	 * @param trxAuxName Transaccion auxiliar para hacer rollbacks independientes
	 * @throws SQLException 
	 */
	protected void proccessLine(MBPartner mbPartner, MCurrency mCurrency, ArrayList<MInvoiceGenerateDetail> mInvoiceGenerateDetails, String trxAuxName) throws SQLException {
		
		MInvoiceGenerateLine mInvoiceGenerateLine = null; //new MInvoiceGenerateLine(getCtx(), 0, trxAuxName);
		BigDecimal subTotalLinea = Env.ZERO;
		boolean montoPositivo = false;
		BigDecimal impuestos = Env.ZERO;
		MTax mTax = null;
		
		ArrayList<MInvoiceGenerateDetail> mInvDSinFranjas = new ArrayList<MInvoiceGenerateDetail>();
		
		System.out.println("Procesando Lineas : " + mInvoiceGenerateDetails.size());
		
		for (MInvoiceGenerateDetail mInvoiceGenerateDetail : mInvoiceGenerateDetails) {
		
			BigDecimal monto = mInvoiceGenerateDetail.getmonto();
			
			
			// Obtengo porcentaje segun sus franjas
			BigDecimal porcentaje = Env.ZERO;
			int diasAtraso = mInvoiceGenerateDetail.getdias_atraso();
			PreparedStatement p2 = DB.prepareStatement("SELECT * FROM UY_BP_Delay WHERE C_BPartner_ID = " + mbPartner.get_ID(), trxAuxName);
			ResultSet rs2 = p2.executeQuery();
			boolean hayFranja = false;
			while (rs2.next()) {
				MBPDelay mbpDelay = new MBPDelay(getCtx(), rs2.getInt("UY_BP_Delay_ID"), trxAuxName);
				if (isContained(diasAtraso, mbpDelay)) {
					porcentaje = rs2.getBigDecimal("porcentaje");
					hayFranja = true;
				}
			}

			rs2.close();
			p2.close();
			
			if (!hayFranja) {
				logError("No hay una franja definida para el pago " + mInvoiceGenerateDetail.getpago_id() + " con " + mInvoiceGenerateDetail.getdias_atraso() + " días de atraso para " + mbPartner.getName() + " " + mCurrency.getCurSymbol());
				mInvDSinFranjas.add(mInvoiceGenerateDetail);
			} else {
				subTotalLinea = subTotalLinea.add(monto.multiply(porcentaje).divide(Env.ONEHUNDRED));
				mInvoiceGenerateDetail.setPercentage(porcentaje);
				mInvoiceGenerateDetail.saveEx();
				System.out.println("Porcentaje: " + porcentaje + " - Monto: " + monto + " - Dias: " + diasAtraso + " - Moneda: " + mInvoiceGenerateDetail.getC_Currency().getCurSymbol());
			}
		}
		
		
		// Elimino de la lista los pagos que no tienen franja
		mInvoiceGenerateDetails.removeAll(mInvDSinFranjas);
		
		
		// Evaluo si el monto es 0, logueo y tiro excepción
		if (subTotalLinea.intValue() == 0) {
			String msjErr = "El pago para el cliente " + mbPartner.getName() + " con la moneda " + mCurrency.getCurSymbol() + " es 0, no se genera factura ni nota de crédito";
			mInvoiceGenerateDetails.clear(); // Elimino los pagos de este cliente-moneda para que se procese el siguietne
			throw new AdempiereException(msjErr);
		}
		

		// Evaluo si genero Factura o Nota de Credito
		montoPositivo = subTotalLinea.intValue() >= 0;
		
		
		
		
		
		// Obtengo el Producto seleccionado en el cabezal para obtener el impuesto y calcular el Total
		try {

			MTaxCategory mTaxCategory = (MTaxCategory) getM_Product().getC_TaxCategory();
			mTax = mTaxCategory.getDefaultTax();
			if (mTax == null) throw new AdempiereException();
			impuestos = mTax.calculateTax(subTotalLinea, false, 2);
		} catch (Exception e) {
			logError("Impuesto no establecido para producto " + getM_Product().getName());
			throw new AdempiereException();
		}
		
		
		
		
		// Instancio si ya existe una MInvoiceGenerateLine para la pareja C_BPartner - C_Currency para la MInvoiceGenerate actual
		String sqlGetMInvoiceGenerateLine = "SELECT UY_InvoiceGenerateLine_ID FROM UY_InvoiceGenerateLine WHERE UY_InvoiceGenerate_ID = " + this.getUY_InvoiceGenerate_ID() + " AND C_Currency_ID = " + mCurrency.get_ID() + " AND C_BPartner_ID = " + mbPartner.get_ID();
		PreparedStatement pIg = DB.prepareStatement(sqlGetMInvoiceGenerateLine, trxAuxName);
		ResultSet rsIg = pIg.executeQuery();
		int mInvGenLine_id = 0;
		if (rsIg.next()) {
			// OpenUp Ltda - Raul Capecce - #5328 - 18/01/2016
			// Debido a este cambio, se puede necesitar crear un MInvGenLine nuevo pese a que exista
			// porque la factura ya esté completado
			MInvoiceGenerateLine mInvGenLine = new MInvoiceGenerateLine(getCtx(), mInvGenLine_id, trxAuxName);
			MInvoice mInv = (MInvoice) mInvGenLine.getC_Invoice();
			if (mInv == null || ( mInv != null && mInv.getDocStatus() != DOCSTATUS_Completed)) {
				mInvGenLine_id = rsIg.getInt("UY_InvoiceGenerateLine_ID");
			}
		}
		// Fin Instanciar MInvoiceGenerateLine
		
		
		if (mInvGenLine_id == 0) {
			
			// Obtengo el tipo de documento correspondiente
			MDocType doc;
			if (montoPositivo) doc = MDocType.forValue(getCtx(), "custinvoice", get_TrxName());
			else doc = MDocType.forValue(getCtx(), "customernc", get_TrxName());
			
			// Creo UY_InvoiceGenerateLine
			mInvoiceGenerateLine = new MInvoiceGenerateLine(getCtx(), 0, trxAuxName);
			mInvoiceGenerateLine.setUY_InvoiceGenerate_ID(this.get_ID());
			mInvoiceGenerateLine.setC_DocType_ID(doc.get_ID());
			mInvoiceGenerateLine.setC_BPartner_ID(mbPartner.get_ID());
			mInvoiceGenerateLine.setC_Currency_ID(mCurrency.getC_Currency_ID());
			mInvoiceGenerateLine.setDateTrx(getDateTrx());
			mInvoiceGenerateLine.setTotalLines(mInvoiceGenerateLine.getTotalLines().add(subTotalLinea)); // Subtotal
			mInvoiceGenerateLine.setGrandTotal(mInvoiceGenerateLine.getTotalLines().add(impuestos));
			// /Creo UY_InvoiceGenerateLine
			
			
			// Creo factura _______________________________________________________
			MInvoice mInvoice = new MInvoice(getCtx(), 0, trxAuxName);
			
			
			mInvoice.setC_DocType_ID(doc.get_ID());
			mInvoice.setC_DocTypeTarget_ID(doc.get_ID());
			
			MPaymentTerm payTerm = (MPaymentTerm) mbPartner.getC_PaymentTerm();
			mInvoice.setC_PaymentTerm_ID(payTerm.get_ID());
			try {
				if(payTerm.get_Value("paymentruletype")!=null){
					mInvoice.setpaymentruletype(payTerm.get_ValueAsString("paymentruletype"));			
				}
			} catch (Exception e) {
				
			}
			mInvoice.setC_BPartner_ID(mbPartner.get_ID());
			mInvoice.setIsSOTrx(true);
			
			mInvoice.saveEx();
			
			
			
			// Si el monto es negativo, lo hago positivo
			if (!montoPositivo) {
				subTotalLinea = subTotalLinea.multiply(BigDecimal.valueOf(-1));
				impuestos = impuestos.multiply(BigDecimal.valueOf(-1));
			}
			
			
			
			// /Creo factura ______________________________________________________
			// Creo Linea Factura _________________________________________________
			MInvoiceLine mInvoiceLine = new MInvoiceLine(mInvoice);
			mInvoiceLine.setQtyEntered(BigDecimal.valueOf(1));
			mInvoiceLine.setQtyInvoiced(BigDecimal.valueOf(1));
			mInvoiceLine.setPriceActual(subTotalLinea);//SBT
			mInvoiceLine.setPriceList(subTotalLinea);//SBT
			mInvoiceLine.setPriceEntered(subTotalLinea);
			mInvoiceLine.setLineNetAmt(subTotalLinea);
			mInvoiceLine.setPriceLimit(Env.ONE);//SBT
			mInvoiceLine.setLineTotalAmt(subTotalLinea.add(impuestos));
			mInvoiceLine.setM_Product_ID(getM_Product_ID());
			
			if (mTax != null) mInvoiceLine.setC_Tax_ID(mTax.get_ID());
			mInvoiceLine.setTaxAmt(impuestos);
			
			mInvoiceLine.saveEx();
			
			mInvoiceGenerateLine.setC_Invoice_ID(mInvoice.get_ID());
			mInvoiceGenerateLine.saveEx();
			
			// /Creo Linea Factura ________________________________________________
			
		} else {
			// Si la MInvoiceGenerateLine ya existe le actualizo los valores
			mInvoiceGenerateLine = new MInvoiceGenerateLine(getCtx(), mInvGenLine_id, trxAuxName);
			MInvoice mInvoice = (MInvoice) mInvoiceGenerateLine.getC_Invoice();
			MInvoiceLine[] lines = mInvoice.getLines();
			MInvoiceLine mInvoiceLine = null;
			if (lines.length > 0) {
				mInvoiceLine = lines[0];
			}
			
			// Actualizo MInvoiceGenerateLine
			subTotalLinea = subTotalLinea.add(mInvoiceGenerateLine.getTotalLines());
			mInvoiceGenerateLine.setTotalLines(subTotalLinea);
			mInvoiceGenerateLine.setGrandTotal(mInvoiceGenerateLine.getTotalLines().add(impuestos));
			
			
			// Actualizo MInvoiceLine
			mInvoiceLine.setQtyEntered(BigDecimal.valueOf(1));
			mInvoiceLine.setQtyInvoiced(BigDecimal.valueOf(1));
			mInvoiceLine.setPriceActual(mInvoiceGenerateLine.getTotalLines());//SBT
			mInvoiceLine.setPriceList(mInvoiceGenerateLine.getTotalLines());//SBT
			mInvoiceLine.setPriceEntered(mInvoiceGenerateLine.getTotalLines());
			mInvoiceLine.setLineNetAmt(mInvoiceGenerateLine.getTotalLines());
			mInvoiceLine.setPriceLimit(Env.ONE);//SBT
			mInvoiceLine.setLineTotalAmt(mInvoiceGenerateLine.getGrandTotal());
			mInvoiceLine.setM_Product_ID(getM_Product_ID());
			
			if (mTax != null) mInvoiceLine.setC_Tax_ID(mTax.get_ID());
			mInvoiceLine.setTaxAmt(impuestos);
			
			mInvoiceLine.saveEx();
			
			
		}
		
		// Establezco a cada MInvoiceGenerateDetail, que MInvoiceGenerateLine le corresponde
		for (MInvoiceGenerateDetail mInvoiceGenerateDetail : mInvoiceGenerateDetails) {
			mInvoiceGenerateDetail.setUY_InvoiceGenerateLine_ID(mInvoiceGenerateLine.get_ID());
			mInvoiceGenerateDetail.saveEx();
		}
		
		
	}
	
	protected MInvoiceGenerateLine getMInvoiceGenerateLine(MBPartner mbPartner, MCurrency mCurrency) throws SQLException {
		String sqlMInvoiceGenerateLine = "SELECT UY_InvoiceGenerateLine_ID FROM UY_InvoiceGenerateLine WHERE C_BPartner_ID = " + mbPartner.get_ID() + " AND C_Currency_ID = " + mCurrency.get_ID();
		PreparedStatement pstmt = DB.prepareStatement (sqlMInvoiceGenerateLine, get_TrxName());
		ResultSet rs = pstmt.executeQuery ();
		
		if (rs.next()) {
			return new MInvoiceGenerateLine(getCtx(), rs.getInt("UY_InvoiceGenerateLine_ID"), get_TrxName());
		}
		
		return null;
	}
	
	protected boolean isContained(int diasAtraso, MBPDelay mbpDelay) {
		if (mbpDelay.getQtyFrom().compareTo(BigDecimal.valueOf(diasAtraso)) <= 0 && mbpDelay.getQtyTo().compareTo(BigDecimal.valueOf(diasAtraso)) >= 0)
			return true;
		
		return false;
	}
	
//	protected MCurrency getMCurrencyForCurSymbol(String curSymbol) throws SQLException {
//		String sqlMCurrency = "SELECT C_Currency_ID FROM C_Currency WHERE lower(curSymbol) = lower(" + curSymbol + ")";
//		PreparedStatement pstmt = DB.prepareStatement (sqlMCurrency, get_TrxName());
//		ResultSet rs = pstmt.executeQuery ();
//		
//		if (rs.next()) {
//			return new MCurrency(getCtx(), rs.getInt("C_Currency_ID"), get_TrxName());
//		}
//		
//		return null;
//	}

	/**
	 * OpenUp Ltda - Raul Capecce - #5328 - 18/01/2016
	 * Elimina los pagos contemplados en este proceso de facturación masiva que no estén con factura completa
	 */
	protected void deletePreviousPayments() {

		try {

			// Logs de errores
			String action = "DELETE FROM UY_InvoiceGenerateLog WHERE UY_InvoiceGenerate_ID = " + this.getUY_InvoiceGenerate_ID();
			DB.executeUpdateEx(action, get_TrxName());

			// Desasocio pagos de la tabla intermedia con facturas originadas en corrida anterior
			action = "UPDATE UY_InvoiceGenerateDetail "
					+ "SET UY_InvoiceGenerateLine_ID = null "
					+ "WHERE UY_InvoiceGenerateLine_ID IN ( "
					+ "    SELECT UY_InvoiceGenerateLine_ID "
					+ "    FROM UY_InvoiceGenerateLine "
					+ "    WHERE UY_InvoiceGenerate_ID = " + this.get_ID()
					+ "    AND C_Invoice_ID IN (SELECT C_Invoice_ID FROM C_Invoice WHERE docStatus <> 'CO') "
					+ ")";
			DB.executeUpdateEx(action, get_TrxName());			

			// Elimino facturas en Borrador originadas en corrida anterior
			action = " delete from UY_InvoiceGenerateLine "
				   + " where UY_InvoiceGenerate_ID =" + this.get_ID()
				   + " and C_Invoice_ID IN (SELECT C_Invoice_ID FROM C_Invoice WHERE docStatus <> 'CO')";
			DB.executeUpdateEx(action, get_TrxName());
			
			Trx trx = Trx.get(get_TrxName(), false);
			trx.commit();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
	
}
