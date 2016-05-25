/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 27/08/2013
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * org.openup.process - PFixDuplicatePartner
 * OpenUp Ltda. Issue #1260 
 * Description: Proceso para unificar dos socios de negocio en uno.
 * @author Gabriel Vila - 27/08/2013
 * @see
 */
public class PFixDuplicatePartner extends SvrProcess {

	int cBPartnerID_From = 0;
	int cBPartnerID_To = 0;
	
	/**
	 * Constructor.
	 */
	public PFixDuplicatePartner() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 27/08/2013
	 * @see
	 */
	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("C_BPartner_ID_From")) this.cBPartnerID_From = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("C_BPartner_ID")) this.cBPartnerID_To = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 27/08/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			// Obtengo locations de cada uno de los partners.
			// Valido ademas que no tengan mas de una location, sino el proceso es inviable.
			MBPartner partnerFrom = new MBPartner(getCtx(), this.cBPartnerID_From, null);
			MBPartnerLocation[] locsFrom = partnerFrom.getLocations(true);
			if (locsFrom.length != 1){
				throw new AdempiereException("No es posible continuar con el Proceso ya que el Socio de Negocio a Borrar debe tener UNA Sucursal.");
			}
			MBPartner partnerTo = new MBPartner(getCtx(), this.cBPartnerID_To, null);
			MBPartnerLocation[] locsTo = partnerTo.getLocations(true);
			if (locsTo.length != 1){
				throw new AdempiereException("No es posible continuar con el Proceso ya que el Socio de Negocio a Unificar debe tener UNA Sucursal.");
			}
			
			sql = " select c.relname " +
				  " from pg_class c, pg_attribute a, pg_type t " +
				  " where lower(a.attname)='c_bpartner_id' " +
				  " and (c.relname != 'c_bpartner' and lower(c.relname) not like 'uy_molde%' " +
				  " and lower(c.relname) not like 'w_%' and lower(c.relname) not like 'c_bp%') " +
				  " and a.attnum > 0 " +
				  " and a.attrelid = c.oid " +
				  " and a.atttypid = t.oid " +
				  " and c.relkind ='r' " +
				  " order by  c.relname"; 
 			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
		
			rs = pstmt.executeQuery ();
			
			String action = "";
			
			while (rs.next()){

				action = " update " + rs.getString(1) + " set c_bpartner_id =" + this.cBPartnerID_To + 
								" where c_bpartner_id =" + this.cBPartnerID_From;
				DB.executeUpdateEx(action, null);

				// Intento actualizar partner_location en esta tabla.
				// Puede dar error porque en esta tabla no tengo el campo de location.
				// Es normal, asi que no hago nada en caso de errores.
				try {
					if (locsFrom.length == 1 && locsTo.length == 1){
						action = " update " + rs.getString(1) + " set c_bpartner_location_id =" + locsTo[0].get_ID() + 
								" where c_bpartner_location_id =" + locsFrom[0].get_ID();
						DB.executeUpdateEx(action, null);
					}
				} 
				catch (Exception e) {
					System.out.println("Tabla " + rs.getString(1) + " no tiene campo c_bpartner_location_id.");
				}
			}
			
			action = " delete from c_bpartner cascade where c_bpartner_id =" + this.cBPartnerID_From;
			DB.executeUpdateEx(action, null);

			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return "OK";

	}

}
