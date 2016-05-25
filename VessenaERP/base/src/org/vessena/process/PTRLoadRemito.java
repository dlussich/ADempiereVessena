/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 05/05/2014
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCountry;
import org.compiere.model.MDocType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MCiudad;
import org.openup.model.MTRRemito;
import org.openup.model.MTRTransOrder;
import org.openup.model.MTRTransOrderLine;
import org.openup.model.MTRTrip;


/**
 * org.openup.process - PTRLoadRemito
 * OpenUp Ltda. Issue #1626 
 * Description: Proceso que genera un documento de Remito de Mercaderia en estado borrador.
 * @author Gabriel Vila - 05/05/2014
 * @see
 */
public class PTRLoadRemito extends SvrProcess {

	private MTRTransOrderLine transOrdLine = null;
	private int crtID = 0;
	
	/**
	 * Constructor.
	 */
	public PTRLoadRemito() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 05/05/2014
	 * @see
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_TR_Crt_ID")){
					this.crtID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
		
		this.transOrdLine = new MTRTransOrderLine(getCtx(), this.getRecord_ID(), get_TrxName());

	}

	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 05/05/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		try {
			
			if (this.transOrdLine.get_ID() <= 0){
				throw new AdempiereException("No se pudo obtener modelo de Linea de Orden de Transporte.");
			}
			
			//OpenUp. Nicolas Sarlabos. 10/09/2014. #2923. Se comenta codigo innecesario.
			// Si ya existe un remito para este expediente y crt, en estado diferente a VO, aviso y salgo
			/*String sql = " select uy_tr_remito_id " +
					     " from uy_tr_remito " +
					     " where uy_tr_trip_id =" + this.transOrdLine.getUY_TR_Trip_ID() +
					     " and uy_tr_crt_id =" + this.crtID +
					     " and docstatus <> 'VO'";
					
			int remitoID = DB.getSQLValueEx(null, sql);
			if (remitoID > 0){
				// Obtengo modelo de este remito
				MTRRemito remito = new MTRRemito(getCtx(), remitoID, null);
				throw new AdempiereException(" Ya existe un remito (numero : " + remito.getDocumentNo() + ") para este Expediente y CRT.\n" +			
											 " Debe eliminar o anular el existente para poder generar uno nuevo.");
			}

			// Si ya tengo un remito asociado a esta linea de orden de transporte
			if (this.transOrdLine.getUY_TR_Remito_ID() > 0){
				// Obtengo modelo de este remito
				MTRRemito remito = new MTRRemito(getCtx(), this.transOrdLine.getUY_TR_Remito_ID(), get_TrxName());
				
				if(remito.get_ID()>0){
					// Si el remito esta completo, aviso que no es posible generar remito cuando hay uno completo y salgo
					if (remito.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
						throw new AdempiereException(" Esta linea de Orden de Transporte ya tiene asociado un Remito Completo (numero: " + remito.getDocumentNo() + ").\n" +
												     " Debe anular el Remito actual para poder generar uno nuevo.");
					}
					// Si el remito esta en borrador, lo borro y listo
					else if (remito.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Drafted)){
						remito.deleteEx(true);
					}
					
				}
			
			}*/
			//Fin OpenUp #2923.
			
			// Obtengo modelo de expediente
			MTRTrip expediente = (MTRTrip)this.transOrdLine.getUY_TR_Trip();
			
			// Obtengo modelo de la orden de transporte
			MTRTransOrder transOrder = (MTRTransOrder)this.transOrdLine.getUY_TR_TransOrder();
			
			// Armo cadenas de ciudades origen y destino.
			// Cuando la ciudad es uruguaya hago CIUDAD/PAIS
			// Cuando la ciudad no es uruguaya hago CIUDAD/DEPARTAMENTO/PAIS
			String sql = "", origen = "", destino = "";
			
			// Origen
			if (transOrder.getUY_Ciudad_ID() > 0){
				
				MCiudad ciudadOrigen = new MCiudad(getCtx(), transOrder.getUY_Ciudad_ID(), null);
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
					String depto = DB.getSQLValueString(null, sql, + pais.get_ID());
					if (depto == null) 
						depto = "";
					else
						depto = depto.toUpperCase() + " - ";
						
					origen = ciudadOrigen.getName().toUpperCase() + " - " + depto + pais.getName().toUpperCase();
				}
			}
			
			// Destino
			if (transOrder.getUY_Ciudad_ID_1() > 0){
				
				MCiudad ciudadDestino = new MCiudad(getCtx(), transOrder.getUY_Ciudad_ID_1(), null);
				// Si es ciudad uruguaya
				if (ciudadDestino.getC_Country_ID() == 336){
					origen = ciudadDestino.getName().toUpperCase() + " - " + "URUGUAY";
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
					String depto = DB.getSQLValueString(null, sql, + pais.get_ID());
					if (depto == null) 
						depto = "";
					else
						depto = depto.toUpperCase() + " - ";
						
					destino = ciudadDestino.getName().toUpperCase() + " - " + depto + pais.getName().toUpperCase();
				}
			}

			
			// Instancio nuevo modelo de remito de mercaderias
			MTRRemito remito = new MTRRemito(getCtx(), 0, get_TrxName());
			remito.setDateTrx(new Timestamp(System.currentTimeMillis()));
			remito.setC_DocType_ID(MDocType.forValue(getCtx(), "remitotrans", null).get_ID());
			remito.setUY_TR_TransOrder_ID(this.transOrdLine.getUY_TR_TransOrder_ID());
			remito.setUY_TR_TransOrderLine_ID(this.transOrdLine.get_ID());
			remito.setUY_TR_Trip_ID(this.transOrdLine.getUY_TR_Trip_ID());
			remito.setUY_TR_Crt_ID(this.crtID);
			remito.setC_BPartner_ID_From(expediente.getC_BPartner_ID_From());
			remito.setC_BPartner_ID_To(expediente.getC_BPartner_ID_To());
			remito.setM_Product_ID(expediente.getM_Product_ID());
			remito.setUY_TR_Truck_ID_Aux(transOrder.getRemolque_ID());	// Remolque
			
			if(this.transOrdLine.getUY_TR_Truck_ID() > 0){
				
				remito.setUY_TR_Truck_ID(this.transOrdLine.getUY_TR_Truck_ID());  // Tractor
				
			} else throw new AdempiereException("No hay tractor asignado para el expediente en esta orden de transporte");
			
			if(this.transOrdLine.getUY_TR_Driver_ID() > 0){
				
				remito.setUY_TR_Driver_ID(this.transOrdLine.getUY_TR_Driver_ID());				
			
			} else throw new AdempiereException("No se obtuvo conductor asociado al tractor para el expediente en esta orden de transporte");			
			
			remito.setbultos(this.transOrdLine.getQtyPackage());
			remito.setWeight2(this.transOrdLine.getWeight()); // Bruto
			remito.setWeight(this.transOrdLine.getWeight2()); // Neto			
			remito.setOrigen(origen);
			remito.setDestino(destino);
			
			remito.saveEx();
			
			// Asocio id del remito generado a la linea de la orden de transporte
			String action = " update uy_tr_transorderline set uy_tr_remito_id =" + remito.get_ID() +
						    " where uy_tr_transorderline_id =" + this.transOrdLine.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "OK.";
	}

}
