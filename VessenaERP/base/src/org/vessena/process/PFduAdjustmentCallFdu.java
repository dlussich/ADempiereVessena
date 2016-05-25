/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MFduAdjCallFduLine;
import org.openup.model.MFduAdjustmentCallFdu;
import org.openup.model.MFduCargo;
import org.openup.model.MFduCargoImporte;
import org.openup.model.MFduCargoModelo;
import org.openup.model.MFduCargoMotLlamada;
import org.openup.model.MFduCod;
import org.openup.model.MFduModeloLiquidacion;
import org.openup.model.MFduProcessDate;
import org.openup.util.ItalcredSystem;

/**
 * @author gbrust
 *
 */
public class PFduAdjustmentCallFdu extends SvrProcess {
	
	private MFduAdjustmentCallFdu mFduAdjustmentCallFdu = null;
	private Timestamp fechaCorrida = null;
	private MFduCargo cargo = null;

	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_Fdu_AdjustmentCallFdu_ID"))
					this.mFduAdjustmentCallFdu = new MFduAdjustmentCallFdu(this.getCtx(), ((BigDecimal)para[i].getParameter()).intValueExact(), this.get_TrxName());
			}
		}
		
		if(this.mFduAdjustmentCallFdu != null && this.mFduAdjustmentCallFdu.get_ID() > 0) this.fechaCorrida = this.mFduAdjustmentCallFdu.getC_Period().getStartDate();
		
		//Obtengo el codigo de aviso de mora
		MFduCod cod = MFduCod.getMFduCodForValue(this.getCtx(), null, "361");		
		
		//instancio el cargo que voy a aplicar, aca tengo el importe que debo aplicar
		this.cargo = MFduCargo.forCodigoIDAndMaxValidate(this.getCtx(), cod.get_ID(), this.fechaCorrida, this.get_TrxName());
	}

	
	@Override
	protected String doIt() throws Exception {	
		
		//Borro lineas para este cabezal.
		this.borrarInstanciasViejas();
					
		//Obtengo cuentas a aplicar
		this.obtenerCuentasLlamadasAFdu();
		
		return "Ok";
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 21/11/2013. ISSUE#
	 * Método que borra los registros de la UY_Fdu_AdjCallFduLine que contengan el uy_fdu_adjustmentcallfdu_id asociado a este proceso
	 * 
	 * */
	private void borrarInstanciasViejas(){		
		
		try{
			DB.executeUpdateEx("DELETE FROM UY_Fdu_AdjCallFduLine WHERE UY_Fdu_AdjustmentCallFdu_ID = " + this.mFduAdjustmentCallFdu.get_ID(), null);		
			
		}catch (Exception e){
			throw new AdempiereException (e);		
		}	
	}
	
	
	/***
	 * OpenUp. Guillermo Brust. 21/11/2013. ISSUE#
	 * Método que devuelve concatenado los codigo de motivo de llamadas que no aplican el cargo
	 * 
	 * 
	 * */
	 private String getCodigosMotivosNoAplican(){

		 String retorno = "";

		 for (MFduCargoMotLlamada motivo : MFduCargoMotLlamada.forCargoID(this.getCtx(), this.cargo.get_ID())) {		 			 
			 
			 retorno += "'" + motivo.getValue() + "',";
		 }

		 retorno = retorno.substring(0, (retorno.length())-1);

		 return retorno;
	 }

	 
	 
	 /**
	 * OpenUp. Guillermo Brust. 21/10/2013. ISSUE #
	 * Método que obtiene los datos de las cuentas que se procesan para realizar el ajuste por llamadas a Fdu
	 * 
	 * ***/
	 private void obtenerCuentasLlamadasAFdu(){		 
	 
		 Connection con = null;
		 ResultSet rs = null;

		 try {

			 con = new ItalcredSystem().getConnectionToSqlServer();
			 Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			 String sql = "select q.NRO_CUENTA as Cuenta, socios.CliCod as Cedula, socios.STNroTarj as Tarjeta," +
					 	  " CASE WHEN socios.PTCod = '72' THEN '00072' ELSE '00001' END as Producto, '0' + SUBSTRING(RTRIM(socios.GAFCod), 1, 4) as Afinidad," +
					 	  "socios.MLCod as ModeloLiquidacion, q.Fecha as Fecha, q.DESC_MOTIVO as MotivoLlamada" +
					 	  " from FinancialPro.dbo.q_detalle_as010 q" +
					 	  " inner join FinancialPro.dbo.q_clientes_adempiere socios on q.NRO_CUENTA = socios.STNroCta and socios.STDeriNro = '0'" +
					 	  " where q.COD_MOTIVO NOT IN (" + this.getCodigosMotivosNoAplican() + ")" +
					 	  " and q.Fecha between '" + this.mFduAdjustmentCallFdu.getC_Period().getStartDate() + "' and '" + this.mFduAdjustmentCallFdu.getC_Period().getEndDate() + "'";

			 rs = stmt.executeQuery(sql);

			 while (rs.next()) {
				 
				 int fduProductosID = DB.getSQLValue(null, "select UY_Fdu_Productos_ID from UY_Fdu_Productos " +
														   " where value='" + rs.getString("Producto") + "'" +
														   " and UY_FduFile_ID= 1000008");	

				 int fduAfinidadID = DB.getSQLValue(null, "select UY_Fdu_Afinidad_ID from UY_Fdu_Afinidad " +
														  " where value='" + rs.getString("Afinidad") + "'" +
														  " and UY_Fdu_Productos_ID = " + fduProductosID +
														  " and UY_FduFile_ID= 1000008");
				 
				 MFduAdjCallFduLine line = new MFduAdjCallFduLine(this.getCtx(), 0, this.get_TrxName());
				 line.setUY_Fdu_AdjustmentCallFdu_ID(this.mFduAdjustmentCallFdu.get_ID());				 
				 line.setAccountNo(rs.getString("Cuenta"));
				 line.setFechaLlamada(rs.getTimestamp("Fecha"));
				 line.setobservaciones(rs.getString("MotivoLlamada"));				 
				 line.setCedula(rs.getString("Cedula"));
				 line.setNroTarjetaTitular(rs.getString("Tarjeta"));
				 line.setUY_Fdu_Productos_ID(fduProductosID);
				 line.setUY_Fdu_Afinidad_ID(fduAfinidadID);
				 
				 int modeloLiquidacionID = MFduModeloLiquidacion.getMFduModeloLiquidacionForValue(this.getCtx(), this.get_TrxName(), rs.getString("ModeloLiquidacion")).get_ID();
				 line.setUY_Fdu_ModeloLiquidacion_ID(modeloLiquidacionID);
				 
				 if(fduProductosID > 0 && fduAfinidadID > 0){
					 MFduCargoImporte cargoImporte = MFduCargoImporte.forCargoProductoAfinidad(this.getCtx(), this.get_TrxName(), this.cargo.get_ID(), fduProductosID, fduAfinidadID);
					 if(cargoImporte != null) line.setImporte(cargoImporte.getAmount());
				 }				 
				 
				 MFduCod codFdu = new MFduCod(getCtx(), this.cargo.getUY_FduCod_ID(), this.get_TrxName());
				 line.setCodigoAjuste(codFdu.getOperationCode());	
				 
				 //Verifico que la cuenta tenga un IT valido para aplicar el ajuste
				 MFduCargoModelo cargoModelo = MFduCargoModelo.forCargoAndModelo(this.getCtx(), this.get_TrxName(), this.cargo.get_ID(), line.getUY_Fdu_ModeloLiquidacion_ID());
				 
				 if(cargoModelo != null && cargoModelo.get_ID() > 0){
					 line.setIsValid(true);					 
				 }else line.setIsValid(false);
				 
				 
				 line.saveEx();			 
			 }
			 rs.close();
			 con.close();

		 } catch (Exception e) {
			 throw new AdempiereException(e);
		 } 
		 finally {				
			 if (con != null){
				 try {
					 if (rs != null){
						 if (!rs.isClosed()) rs.close();
					 }
					 if (!con.isClosed()) con.close();
				 } catch (SQLException e) {
					 throw new AdempiereException(e);
				 }
			 }
		 }
	 }

}
