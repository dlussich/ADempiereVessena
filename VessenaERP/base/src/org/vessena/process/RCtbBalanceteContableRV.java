/**
 * RCtbBalanceteContable.java
 * 04/10/2010
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp.
 * RCtbBalanceteContable
 * Descripcion : Reporte de Balancete de Cuentas Contables
 * @author Gabriel Vila
 * Fecha : 04/10/2010
 */
public class RCtbBalanceteContableRV extends SvrProcess {

	private Timestamp fechaHasta = null;
	
	private int idCuentaDesde =0;
	private int idCuentaHasta =0;
	
	private int idEmpresa = 0;
	private int idOrganizacion = 0;
	private Long idUsuario = new Long(0);
	private String idReporte = "";
	
	ProcessInfoParameter paramIDCta = null;
	

	private static final String TABLA_MOLDE = "UY_MOLDE_RCtbBalanceteContable";
	
	
	/**
	 * Constructor
	 */
	public RCtbBalanceteContableRV() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		// Parametro para titulo del reporte
		ProcessInfoParameter paramTituloReporte = null;

		// Parametros para fechas
		ProcessInfoParameter paramEndDate = null;
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
				if (name.equalsIgnoreCase("EndDate")){
					paramEndDate = para[i]; 
				}
				if (name.equalsIgnoreCase("tituloReporte")){
					paramTituloReporte = para[i];
				}
				if (name.equalsIgnoreCase("fecreporte")){
					this.fechaHasta = (Timestamp)para[i].getParameter();
					para[i].setParameter(this.fechaHasta);
				}
				if (name.equalsIgnoreCase("C_ElementValue_ID")){
					if (para[i].getParameter()!=null){
						this.idCuentaDesde =  ((BigDecimal)para[i].getParameter()).intValueExact();
						//para[i].setParameter(new Integer(0));
					}
						
					if (para[i].getParameter_To()!=null){
						this.idCuentaHasta =  ((BigDecimal)para[i].getParameter_To()).intValueExact();
						//para[i].setParameter_To(new Integer(9999999));
					}
				}
				if (name.equalsIgnoreCase("idusuario")){
					this.idUsuario = ((BigDecimal)para[i].getParameter()).longValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.idOrganizacion = ((BigDecimal)para[i].getParameter()).intValueExact();
				}				
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(this.idUsuario);
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "Balancete Cuentas Contables";
		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);

		// Si tengo parametros de fechas para mostrar en el reporte
		if (paramEndDate!=null) paramEndDate.setParameter(this.fechaHasta);
		
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String salida="";
		//Instacio logica.
		RCtbBalanceteContableLogica logica=new RCtbBalanceteContableLogica(log, idCuentaDesde, idCuentaHasta, idEmpresa, idOrganizacion, idUsuario, idReporte,fechaHasta);
		
		//llamo al metodo que prepara la tabla para que luego el reporte en Jasper haga la select.
		salida= logica.loadModelTable();
		
		updateParametrosIDCuentas();
		
		return salida;
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Actualiza los rangos de ElementValue para que sean iguales a los cargados en la tabla
	 * @author  Nicolas Garcia
	 * Fecha : 10/06/2011
	 */
	private void updateParametrosIDCuentas() {
		
		Integer newIDCtaDesde = DB.getSQLValue(null, "SELECT MIN(C_ElementValue_ID) FROM " + TABLA_MOLDE + " WHERE idReporte = '" + idReporte + "'");
		Integer newIDCtaHasta = DB.getSQLValue(null, "SELECT MAX(C_ElementValue_ID) FROM " + TABLA_MOLDE + " WHERE idReporte = '" + idReporte + "'");
	
		String action = "UPDATE AD_PInstance_Para SET p_number = " + newIDCtaDesde +
						" , p_number_to = " + newIDCtaHasta +
						" WHERE AD_PInstance_ID = " + this.getAD_PInstance_ID() +
						" AND lower(ParameterName) = 'c_elementvalue_id'";
		
		DB.executeUpdate(action,null);
	}

	
}
