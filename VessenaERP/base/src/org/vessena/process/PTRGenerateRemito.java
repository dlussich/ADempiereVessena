/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MCountry;
import org.compiere.model.MDocType;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.openup.model.MCiudad;
import org.openup.model.MTRRemito;
import org.openup.model.MTRTransOrder;
import org.openup.model.MTRTransOrderLine;
import org.openup.model.MTRTrip;

/**
 * @author Nicolas
 *
 */
public class PTRGenerateRemito extends SvrProcess {
	
	private int orderID = 0;
	private boolean isPrinted = false;
	private boolean masive = false;
	private boolean completeRemit = false;
	private int copies = 2;

	/**
	 * 
	 */
	public PTRGenerateRemito() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_TR_TransOrder_ID")){
					this.orderID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("IsPrinted")){
					String printed = ((String)para[i].getParameter());
					
					if(printed.equalsIgnoreCase("Y")){
						this.isPrinted = true;
					} else this.isPrinted = false;
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("MasiveAction")){
					String massive = ((String)para[i].getParameter());
					
					if(massive.equalsIgnoreCase("Y")){
						this.masive = true;
					} else this.masive = false;
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("CompleteRemit")){
					String complete = ((String)para[i].getParameter());
					
					if(complete.equalsIgnoreCase("Y")){
						this.completeRemit = true;
					} else this.completeRemit = false;
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("Copies")){
					String cop = ((String)para[i].getParameter());
					
					if(cop.equalsIgnoreCase("2")){
						this.copies = 2;
					} else this.copies = 4;
										
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
	
		String sql = "";
		Trx trans = null;
		MTRTrip trip = null;
		MTRTransOrder order = null;
		
		try{
			
			// Genero nueva transaccion
			String trxNameAux = Trx.createTrxName();
			trans = Trx.get(trxNameAux, true);
			
			order = new MTRTransOrder(getCtx(),this.orderID,trxNameAux);
			
			//elimino remitos anteriores para la orden de transporte
			DB.executeUpdateEx("delete from uy_tr_remito cascade where uy_tr_transorder_id = " + order.get_ID(), trxNameAux);
					
			//verifico que todos los clientes involucrados tengan direccion en el expediente
			this.verifyBPLocation(true); //exportador
			this.verifyBPLocation(false); //importador
			
			List<MTRTransOrderLine> oLines = order.getLines(); //obtengo lineas de OT
			
			if(oLines.size() <= 0) throw new AdempiereException("No se obtuvieron lineas para la Orden de Transporte N° " + order.getDocumentNo());
			
			//comienza generacion de remitos
			for (MTRTransOrderLine line: oLines){
				
				// Obtengo modelo de expediente
				trip = (MTRTrip)line.getUY_TR_Trip();
				
				// Armo cadenas de ciudades origen y destino.
				// Cuando la ciudad es uruguaya hago CIUDAD/PAIS
				// Cuando la ciudad no es uruguaya hago CIUDAD/DEPARTAMENTO/PAIS
				String origen = "", destino = "";
				
				// Origen
				if (trip.getUY_Ciudad_ID() > 0){
					
					MCiudad ciudadOrigen = new MCiudad(getCtx(), trip.getUY_Ciudad_ID(), null);
					// Si es ciudad uruguaya
					if (ciudadOrigen.getC_Country_ID() == 336){
						origen = ciudadOrigen.getName().toUpperCase() + " - " + "URUGUAY";
					}
					else{
						// Ciudad no es uruguaya
						MCountry pais = (MCountry)ciudadOrigen.getC_Country();
						// Departamento
						sql = " select dep.name " +
							  " from uy_departamentos dep " +
							  " inner join uy_localidades loc on dep.uy_departamentos_id = loc.uy_departamentos_id " +
							  " where loc.c_country_id =? " +
							  " and lower(loc.name) like '" + ciudadOrigen.getName().toLowerCase() + "%'";
						String depto = DB.getSQLValueString(trxNameAux, sql, + pais.get_ID());
						if (depto == null) 
							depto = "";
						else
							depto = depto.toUpperCase() + " - ";
							
						origen = ciudadOrigen.getName().toUpperCase() + " - " + depto + pais.getName().toUpperCase();
					}
				}
				
				// Destino
				if (trip.getUY_Ciudad_ID_1() > 0){
					
					MCiudad ciudadDestino = new MCiudad(getCtx(), trip.getUY_Ciudad_ID_1(), null);
					// Si es ciudad uruguaya
					if (ciudadDestino.getC_Country_ID() == 336){
						destino = ciudadDestino.getName().toUpperCase() + " - " + "URUGUAY";
					}
					else{
						// Ciudad no es uruguaya
						MCountry pais = (MCountry)ciudadDestino.getC_Country();
						// Departamento
						sql = " select dep.name " +
						      " from uy_departamentos dep " +
						      " inner join uy_localidades loc on dep.uy_departamentos_id = loc.uy_departamentos_id " +
						      " where loc.c_country_id =? " +
						      " and lower(loc.name) like '" + ciudadDestino.getName().toLowerCase() + "%'";
						String depto = DB.getSQLValueString(trxNameAux, sql, + pais.get_ID());
						if (depto == null) 
							depto = "";
						else
							depto = depto.toUpperCase() + " - ";
							
						destino = ciudadDestino.getName().toUpperCase() + " - " + depto + pais.getName().toUpperCase();
					}
				}
				
				//Instancio nuevo modelo de remito de mercaderias
				MTRRemito remito = new MTRRemito(getCtx(), 0, trxNameAux);
				remito.setDateTrx(new Timestamp(System.currentTimeMillis()));
				remito.setC_DocType_ID(MDocType.forValue(getCtx(), "remitotrans", null).get_ID());
				remito.setUY_TR_TransOrder_ID(this.orderID);
				remito.setUY_TR_TransOrderLine_ID(line.get_ID());
				remito.setUY_TR_Trip_ID(trip.get_ID());
				remito.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
				remito.setC_BPartner_ID_To(trip.getC_BPartner_ID_From()); //importador
				remito.setC_BPartner_ID_From(trip.getC_BPartner_ID_To()); //exportador
				remito.setM_Product_ID(trip.getM_Product_ID());
				//remito.setC_BPartner_Location_ID_1(trip.getC_BPartner_Location_ID_2()); //direccion exportador
				
				//OpenUp. Nicolas Sarlabos. 05/04/2016. #5719. Se carga direccion de descarga del importador.
				sql = " select c_bpartner_location_id" +
						" from c_bpartner_location" +
						" where c_bpartner_id = " + trip.getC_BPartner_ID_From() +
						" and IsShipTo = 'Y' and IsActive = 'Y'";
				
				int locID = DB.getSQLValueEx(getName(), sql);
				
				if(locID > 0) remito.setC_BPartner_Location_ID(locID); //direccion descarga del importador
				//Fin OpenUp.				
								
				if(order.getRemolque_ID() > 0) remito.setUY_TR_Truck_ID_Aux(order.getRemolque_ID());	// Remolque
				if(order.getTractor_ID() > 0) remito.setUY_TR_Truck_ID(order.getTractor_ID());  // Tractor
				if(order.getUY_TR_Driver_ID() > 0) remito.setUY_TR_Driver_ID(order.getUY_TR_Driver_ID());	//Chofer			
							
				remito.setbultos(line.getQtyPackage());
				remito.setWeight2(line.getWeight()); // Bruto
				remito.setWeight(line.getWeight2()); // Neto			
				remito.setOrigen(origen);
				remito.setDestino(destino);
				
				remito.saveEx(trxNameAux);
				
				//si se requiere completar remito automaticamente...
				if(this.completeRemit){

					//se completa el remito
					if(!remito.processIt(DocAction.ACTION_Complete)) 
						throw new AdempiereException("Error al completar remito N° " + remito.getDocumentNo());

				}
				
			}
			
			trans.close();
			
			//si se selecciono la impresion automatica se procede
			if(this.isPrinted){
				
				MPInstancePara para = null;
				ResultSet rs = null;
				PreparedStatement pstmt = null;	
							
				int adProcessID = this.getPrintProcessID();
				if (adProcessID <= 0){
					throw new Exception("No se pudo obtener ID del proceso de impresion de remitos de mercaderia");
				}
				
				sql = "select uy_tr_remito_id" +
				      " from uy_tr_remito" +
					  " where uy_tr_transorder_id = " + order.get_ID() +
					  " order by uy_tr_remito_id";
				
				pstmt = DB.prepareStatement (sql, get_TrxName());
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					MPInstance instance = new MPInstance(getCtx(), adProcessID, 0);
					instance.saveEx();

					ProcessInfo pi = new ProcessInfo ("Remito", adProcessID);
					pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
					
					para = new MPInstancePara(instance, 10);
					para.setParameter("UY_TR_Remito_ID", new BigDecimal(rs.getInt("uy_tr_remito_id")));
					para.saveEx();
					
					para = new MPInstancePara(instance, 20);
					para.setParameter("Copies", this.copies);
					para.saveEx();
					
					para = new MPInstancePara(instance, 30);
					para.setParameter("MasiveAction", this.masive);
					para.saveEx();
					
					ReportStarter starter = new ReportStarter();
					starter.startProcess(getCtx(), pi, null);					
					
				}				
			}			
			
			return "OK.";
			
		}catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}		
		
	}

	private void verifyBPLocation(boolean isExpo) {
		
		String sql = "", bpType = "";
		MBPartner partner = null;
		MTRTrip trip = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try {
			
			if(isExpo){
				
				sql = "select bp.c_bpartner_id,trip.uy_tr_trip_id" +
		                  " from uy_tr_transorderline ol" +
		                  " inner join uy_tr_trip trip on ol.uy_tr_trip_id = trip.uy_tr_trip_id" +
		                  " inner join c_bpartner bp on trip.c_bpartner_id_to = bp.c_bpartner_id" +
		                  " where trip.c_bpartner_location_id_2 is null and ol.uy_tr_transorder_id = " + this.orderID;
				
				bpType = "exportador";				
				
			} else {
				
				sql = "select bp.c_bpartner_id,trip.uy_tr_trip_id" +
		                  " from uy_tr_transorderline ol" +
		                  " inner join uy_tr_trip trip on ol.uy_tr_trip_id = trip.uy_tr_trip_id" +
		                  " inner join c_bpartner bp on trip.c_bpartner_id_from = bp.c_bpartner_id" +
		                  " where trip.c_bpartner_location_id is null and ol.uy_tr_transorder_id = " + this.orderID;
				
				bpType = "importador";				
				
			}
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		   	while (rs.next()){
		   		
		   		partner = new MBPartner(getCtx(),rs.getInt("c_bpartner_id"),get_TrxName());
		   		trip = new MTRTrip(getCtx(),rs.getInt("uy_tr_trip_id"),get_TrxName());
		   		
		   		throw new AdempiereException("El " + bpType + " '" + partner.getName() + "' no tiene direccion asignada en el expediente N° " + trip.getDocumentNo());	   		
		   		
		   	}		
			
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
	}

	/***
	 * Obtiene y retorna id del proceso de impresion de Remito.
	 * OpenUp Ltda.
	 * @author Nicolas Sarlabos - 06/01/2015
	 * @see
	 * @return
	 */
	private int getPrintProcessID() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int value = 0;

		try{
			
			sql = " select ad_process_id " +
					" from ad_process " +
					" where lower(value)='uy_rtr_remito'";	

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			if (rs.next()){
				value = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return value;

	}

}
