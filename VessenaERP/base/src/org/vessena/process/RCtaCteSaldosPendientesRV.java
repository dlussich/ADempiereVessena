/**
 * RCtaCteSaldosPendientesRV.java
 * 13/06/2011
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MCurrency;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * OpenUp.
 * RCtaCteSaldosPendientes
 * Descripcion :
 * @author Mario Reyes
 * Fecha : 13/06/2011
 */
public class RCtaCteSaldosPendientesRV extends SvrProcess {

	private Timestamp fechaHasta = null;
	private boolean esClientes = true;
	private String tipoMoneda = "";
	private int codigoMoneda = 0;
	private int codigoGrupo = 0;
	//private String tipoCtaReporte = "";
	//private BigDecimal codigoSocioNegocio = null;
	
	private String nombreEmpresa = "";
	private String nombreUsuario = "";

	private int idEmpresa = -1;
	private int idUsuario = -1;
	private int idOrganizacion = 0;
	
	private int codigoCliente = -1;
	private int codigoProveedor = -1;
	
	private String idReporte = "";
	
	private static final String TIPO_MONEDA_SMN = "SMN";
	private static final String TIPO_MONEDA_SME = "SME";
	private static final String TIPO_MONEDA_TMN = "TMN";
	private static final String TIPO_MONEDA_TME = "TME";
	//private static final String CTA_ABIERTA = "CA";
	//private static final String CTA_DOCUMENTADA = "CD";
	//private static final String CTA_CORRIENTE_CLIENTE = "CL";
	//private static final String CTA_CORRIENTE_PROVEEDOR = "PR";

	private static final String TABLA_MOLDE = "UY_MOLDE_RAdmSaldosPend";
	
	//private Hashtable<String, SaldoSocioNegocioBean> saldos = new Hashtable<String, SaldoSocioNegocioBean>();

	
	/**
	 * Constructor
	 */
	public RCtaCteSaldosPendientesRV() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected String doIt() throws Exception {
		RCtaCteSaldosPendientesLogica logica = new RCtaCteSaldosPendientesLogica(codigoMoneda, idEmpresa, idUsuario, idOrganizacion, log, esClientes, codigoCliente, codigoProveedor, idReporte, TIPO_MONEDA_SME, TIPO_MONEDA_SMN, TIPO_MONEDA_TMN, TIPO_MONEDA_TME, TABLA_MOLDE, fechaHasta, get_TrxName(), nombreEmpresa, nombreUsuario, tipoMoneda, codigoGrupo);
		
		return logica.Proceso();
	}

	@Override
	protected void prepare() {

		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		// Parametro para titulo del reporte
		ProcessInfoParameter paramTituloReporte = null;
		// Parametro para moneda
		ProcessInfoParameter paramMonedaReporte = null;
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
				if (name.equalsIgnoreCase("tituloReporte")){
					paramTituloReporte = para[i];
				}
				if (name.equalsIgnoreCase("fechaHasta")){
					this.fechaHasta = (Timestamp)para[i].getParameter();
					para[i].setParameter(this.fechaHasta);
				}
				if (name.equalsIgnoreCase("UY_TipoMonedaReporte")){
					this.tipoMoneda = (String)para[i].getParameter();
				}
				if (name.equalsIgnoreCase("C_Currency_ID")){
					paramMonedaReporte = para[i];
				}
				if (name.equalsIgnoreCase("empresaReporte")){
					this.nombreEmpresa= (String)para[i].getParameter();
				}
				if (name.equalsIgnoreCase("usuarioReporte")){
					this.nombreUsuario= (String)para[i].getParameter();
				}
				if (name.equalsIgnoreCase("tipoMoneda")){
					this.tipoMoneda= (String)para[i].getParameter();
				}
				if (name.equalsIgnoreCase("AD_User_ID")){
					this.idUsuario = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.idEmpresa = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_BP_Group_ID")){
					this.codigoGrupo = ((BigDecimal)para[i].getParameter()).intValueExact();
					
					if (this.codigoGrupo == 1000002) this.esClientes = false;
					
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					this.idOrganizacion = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.codigoCliente = ((BigDecimal)para[i].getParameter()).intValue();
							this.esClientes = true;
						}						
					}						
				}
				if (name.equalsIgnoreCase("C_BPartner_ID_P")){
					if (para[i].getParameter()!=null){
						if (!para[i].getParameter().toString().trim().equalsIgnoreCase("")){
							this.codigoProveedor = ((BigDecimal)para[i].getParameter()).intValue();
							this.esClientes = false;
						}						
					}						
				}
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(Long.valueOf(this.idUsuario));
		
		// Seteo titulo del reporte segun tipo cta corriente
		String tituloReporte = "";
		if (this.esClientes) tituloReporte = "Saldos Pendientes - Deudores";
		else tituloReporte = "Saldos Pendientes - Proveedores";
		
		// Si tengo parametro para titulo de reporte
		if (paramTituloReporte!=null) paramTituloReporte.setParameter(tituloReporte);

		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
		
		// Si el tipo de moneda seleccionado por el usuario en el filtro del reporte es para moneda nacional
		if (this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_SMN) || this.tipoMoneda.equalsIgnoreCase(TIPO_MONEDA_TMN)){
			// Obtengo codigo de moneda nacional para la empresa
			this.codigoMoneda = this.getIDMonedaNacional(this.idEmpresa);			
		}
		else{
			// Moneda extranjera, tomo el id de moneda directamente del filtro segun seleccion del usuario
			if (paramMonedaReporte!=null) 
				this.codigoMoneda = paramMonedaReporte.getParameterAsInt();
			else
				this.codigoMoneda = 100; // Me cubro de errores y pongo dolares a mano
		}
	
		// Seteo parametro moneda del reporte enviando la descripcion de la moneda seleccionada por el usuario
		if (paramMonedaReporte!=null)
			paramMonedaReporte.setParameter(this.getDescripcionMoneda(this.codigoMoneda));
	}

	/* Carga comprobantes iniciales en tabla molde*/
		
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	

	/* Obtiene id de moneda nacional para la empresa actual*/
	private Integer getIDMonedaNacional(Integer idEmpresa){
		MClient client = new MClient(getCtx(), idEmpresa, null);
		MAcctSchema schema = client.getAcctSchema();

		return schema.getC_Currency_ID();
	}
	
	private String getDescripcionMoneda(int idMoneda){
		MCurrency model = new MCurrency(getCtx(), idMoneda ,get_TrxName());
		return model.getDescription();
	}
	
	/* Actualiza saldos pendientes y acumulados de los registros de la temporal*/
		
	/* Recorro hash con saldos previamente calculados y hago el update correspondiente
	 * en la tabla temporal.
	 */
	
	/* Elimino registros de tabla temporal con saldo pendiente cero. No deberia haber ningun registro pero me cubro de errores. */
	
}
