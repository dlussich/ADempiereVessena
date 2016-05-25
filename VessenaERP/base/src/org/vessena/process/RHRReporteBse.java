/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MJobCategory;
import org.compiere.model.MTax;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class RHRReporteBse extends SvrProcess {
	
	private static String TABLA_MOLDE = "UY_Molde_ReporteBse";
	
	private int processID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;	
	private String idReporte = "";
	
	private BigDecimal primaAdmin = Env.ZERO; //porcentaje de prima para categoria Administracion
	private BigDecimal primaMant = Env.ZERO; //porcentaje de prima para categoria Mantenimiento
	private BigDecimal primaCond = Env.ZERO; //porcentaje de prima para categoria Conductor
	private BigDecimal primaLey = Env.ZERO; //porcentaje de prima de parametros de nomina
	
	private BigDecimal total1 = Env.ZERO; //acumulador de totales de haberes
	private BigDecimal total2 = Env.ZERO; //acumulador de subtotales
	private BigDecimal total3 = Env.ZERO; //acumulador de montos de IVA
	private BigDecimal total4 = Env.ZERO; //acumulador de totales finales
	

	/**
	 * 
	 */
	public RHRReporteBse() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
								
				if (name.equalsIgnoreCase("UY_HRProcess_ID")){
					if (para[i].getParameter()!=null){
						this.processID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				} 				
				if (name.equalsIgnoreCase("AD_User_ID")){
					if (para[i].getParameter()!=null){
						this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					if (para[i].getParameter()!=null){
						this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					if (para[i].getParameter()!=null){
						this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}		
						
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));
		
		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);
	}

	@Override
	protected String doIt() throws Exception {
	
		//Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		//Cargo en tabla molde.		
		this.loadData();
		//actualizo tabla molde
		this.updateTable();
		
		return "ok";
	}
	
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE AD_User_ID =" + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}

	private void loadData() {

		String sql = "", insert = "";
		MJobCategory categoryAdm = null;
		MJobCategory categoryMant = null;
		MJobCategory categoryCond = null;		
		BigDecimal haberes = Env.ZERO;
		int qtyPartner = 0;
		BigDecimal amt1 = Env.ZERO;
		BigDecimal amt2 = Env.ZERO;
		BigDecimal subTotal = Env.ZERO;
		BigDecimal taxAmt = Env.ZERO;
		BigDecimal total = Env.ZERO;
		BigDecimal taxRate = Env.ZERO;

		try{

			MTax tax = MTax.forValue(getCtx(), "basico", get_TrxName());

			if(tax != null && tax.get_ID() > 0){

				if(tax.getRate().compareTo(Env.ZERO)>0){

					taxRate = tax.getRate();

				} else throw new AdempiereException("La tasa debe ser mayor a cero para el tipo de impuesto BASICO");


			} else throw new AdempiereException("No se encontro tipo de impuesto BASICO");	
			
			sql = "select coalesce(primabseley,0)" +
					" from uy_hrparametros" +
					" where ad_client_id = " + this.adClientID + 
					" and ad_org_id = " + this.adOrgID;
			this.primaLey = DB.getSQLValueBDEx(get_TrxName(), sql);

			if(this.primaLey.compareTo(Env.ZERO)<=0) throw new AdempiereException("Porcentaje de BSE Ley debe ser mayor a cero en parametros generales de nomina");

			//se cargan datos para categoria ADMINISTRATIVO
			categoryAdm = MJobCategory.forCategoryName(getCtx(), "Administrativo", get_TrxName());

			if(categoryAdm == null || categoryAdm.get_ID() <= 0) throw new AdempiereException("No se encontro categoria 'Administrativo'");

			//obtengo total de haberes
			sql = "select coalesce(sum(r.amtacctdr),0)" +
					" from uy_hrprocesonomina p" +
					" inner join uy_hrresult r on p.uy_hrprocesonomina_id = r.uy_hrprocesonomina_id" +
					" inner join uy_bpremuneracion pr on r.c_bpartner_id = pr.c_bpartner_id" + 
					" inner join c_job j on pr.c_job_id = j.c_job_id" +
					" inner join c_jobcategory jc on j.c_jobcategory_id = jc.c_jobcategory_id" +
					" where p.ad_client_id = " + this.adClientID + " and p.ad_org_id = " + this.adOrgID + 
					" and p.uy_hrprocess_id = " + this.processID + " and jc.c_jobcategory_id = " + categoryAdm.get_ID();
			haberes = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			this.total1 = this.total1.add(haberes);

			//obtengo total de empleados procesados de la categoria
			sql = "select count(r.uy_hrresult_id)" +
					" from uy_hrprocesonomina p" +
					" inner join uy_hrresult r on p.uy_hrprocesonomina_id = r.uy_hrprocesonomina_id" +
					" inner join uy_bpremuneracion pr on r.c_bpartner_id = pr.c_bpartner_id" + 
					" inner join c_job j on pr.c_job_id = j.c_job_id" +
					" inner join c_jobcategory jc on j.c_jobcategory_id = jc.c_jobcategory_id" +
					" where p.ad_client_id = " + this.adClientID + " and p.ad_org_id = " + this.adOrgID + 
					" and p.uy_hrprocess_id = " + this.processID + " and jc.c_jobcategory_id = " + categoryAdm.get_ID();
			qtyPartner = DB.getSQLValueEx(get_TrxName(), sql);

			this.primaAdmin = (BigDecimal) categoryAdm.get_Value("PrimaBse"); //obtengo porcentaje de prima BSE de la categoria

			if(this.primaAdmin.compareTo(Env.ZERO)<=0) throw new AdempiereException("Porcentaje de prima BSE para la categoria '" + categoryAdm.getName() + "' debe ser mayor a cero");

			amt1 = haberes.multiply(this.primaAdmin).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);	

			amt2 = amt1.multiply(this.primaLey).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);

			subTotal = amt1.add(amt2);
			
			this.total2 = this.total2.add(subTotal);

			taxAmt = subTotal.multiply(taxRate).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP); //obtengo importe de impuesto
			
			this.total3 = this.total3.add(taxAmt);

			total = subTotal.add(taxAmt); //obtengo importe total
			
			this.total4 = this.total4.add(total);

			//inserto linea para categoria ADMINISTRATIVO
			insert = "INSERT INTO " + TABLA_MOLDE + "(ad_client_id,ad_org_id,ad_user_id,idreporte,fecreporte,uy_hrprocess_id,name,qty,amount,amt1,amt2,amt3,subtotal,taxamt,totalamt) VALUES(" + this.adClientID + "," + this.adOrgID + "," + 
					this.adUserID + ",'" + this.idReporte + "',now()," + this.processID + ",'Administrativos'," + qtyPartner + "," + 
					haberes + "," + amt1 + ",null," + amt2 + "," + subTotal + "," + taxAmt + "," + total + ")";

			log.log(Level.INFO, insert);
			DB.executeUpdateEx(insert, get_TrxName());
			
			//se cargan datos para categoria MANTENIMIENTO y CONDUCTOR
			categoryMant = MJobCategory.forCategoryName(getCtx(), "Mantenimiento", get_TrxName());

			if(categoryMant == null || categoryMant.get_ID() <= 0) throw new AdempiereException("No se encontro categoria 'Mantenimiento'");
			
			categoryCond = MJobCategory.forCategoryName(getCtx(), "Conductor", get_TrxName());

			if(categoryCond == null || categoryCond.get_ID() <= 0) throw new AdempiereException("No se encontro categoria 'Conductor'");
			
			//obtengo total de haberes
			sql = "select coalesce(sum(r.amtacctdr),0)" +
					" from uy_hrprocesonomina p" +
					" inner join uy_hrresult r on p.uy_hrprocesonomina_id = r.uy_hrprocesonomina_id" +
					" inner join uy_bpremuneracion pr on r.c_bpartner_id = pr.c_bpartner_id" + 
					" inner join c_job j on pr.c_job_id = j.c_job_id" +
					" inner join c_jobcategory jc on j.c_jobcategory_id = jc.c_jobcategory_id" +
					" where p.ad_client_id = " + this.adClientID + " and p.ad_org_id = " + this.adOrgID + 
					" and p.uy_hrprocess_id = " + this.processID + " and jc.c_jobcategory_id in (" + categoryMant.get_ID() + "," + categoryCond.get_ID() + ")";
			haberes = DB.getSQLValueBDEx(get_TrxName(), sql);
			
			this.total1 = this.total1.add(haberes);

			//obtengo total de empleados procesados de la categoria
			sql = "select count(r.uy_hrresult_id)" +
					" from uy_hrprocesonomina p" +
					" inner join uy_hrresult r on p.uy_hrprocesonomina_id = r.uy_hrprocesonomina_id" +
					" inner join uy_bpremuneracion pr on r.c_bpartner_id = pr.c_bpartner_id" + 
					" inner join c_job j on pr.c_job_id = j.c_job_id" +
					" inner join c_jobcategory jc on j.c_jobcategory_id = jc.c_jobcategory_id" +
					" where p.ad_client_id = " + this.adClientID + " and p.ad_org_id = " + this.adOrgID + 
					" and p.uy_hrprocess_id = " + this.processID + " and jc.c_jobcategory_id in (" + categoryMant.get_ID() + "," + categoryCond.get_ID() + ")";
			qtyPartner = DB.getSQLValueEx(get_TrxName(), sql);
			
			this.primaMant = (BigDecimal) categoryMant.get_Value("PrimaBse"); //obtengo porcentaje de prima BSE de la categoria

			if(this.primaMant.compareTo(Env.ZERO)<=0) throw new AdempiereException("Porcentaje de prima BSE para la categoria '" + categoryMant.getName() + "' debe ser mayor a cero");

			this.primaCond = (BigDecimal) categoryCond.get_Value("PrimaBse"); //obtengo porcentaje de prima BSE de la categoria

			if(this.primaCond.compareTo(Env.ZERO)<=0) throw new AdempiereException("Porcentaje de prima BSE para la categoria '" + categoryAdm.getName() + "' debe ser mayor a cero");
			
			if(this.primaMant.compareTo(this.primaCond) != 0) throw new AdempiereException("Los porcentajes de prima BSE para las categorias '" + categoryMant.getName() + "' y '" + categoryCond.getName() + "' deben ser iguales");
			
			amt1 = haberes.multiply(this.primaMant).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);	

			amt2 = amt1.multiply(this.primaLey).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);

			subTotal = amt1.add(amt2);
			
			this.total2 = this.total2.add(subTotal);

			taxAmt = subTotal.multiply(taxRate).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP); //obtengo importe de impuesto
			
			this.total3 = this.total3.add(taxAmt);

			total = subTotal.add(taxAmt); //obtengo importe total
			
			this.total4 = this.total4.add(total);

			//inserto linea para categoria MANTENIMIENTO y CONDUCTOR
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id,ad_org_id,ad_user_id,idreporte,fecreporte,uy_hrprocess_id,name,qty,amount,amt1,amt2,amt3,subtotal,taxamt,totalamt) VALUES(" + this.adClientID + "," + this.adOrgID + "," + 
					this.adUserID + ",'" + this.idReporte + "',now()," + this.processID + ",'Choferes y mantenimiento'," + qtyPartner + "," + 
					haberes + ",null," + amt1 + "," + amt2 + "," + subTotal + "," + taxAmt + "," + total + ")";

			log.log(Level.INFO, insert);
			DB.executeUpdateEx(insert, get_TrxName());

		} catch (Exception e) {

			throw new AdempiereException(e);

		}		
	}
	
	private void updateTable() {
			
		DB.executeUpdateEx("update " + TABLA_MOLDE + " set primabseadm = " + this.primaAdmin + ", primabsemant = " + this.primaMant + ", primabseley = " + 
		                    this.primaLey + " where idreporte = '" + this.idReporte + "'", get_TrxName());
		
		DB.executeUpdateEx("update " + TABLA_MOLDE + " set total1 = " + this.total1 + ", total2 = " + this.total2 + ", total3 = " + 
                this.total3 + ", total4 = " + this.total4 + " where idreporte = '" + this.idReporte + "'", get_TrxName());		
		
	}

}
