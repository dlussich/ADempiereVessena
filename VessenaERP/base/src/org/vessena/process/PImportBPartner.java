/**
 * 
 */
package org.openup.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MClientesMeAcct;

/**
 * @author Nicolas
 *
 */
public class PImportBPartner extends SvrProcess {

	/**
	 * 
	 */
	public PImportBPartner() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
	
		String s = importData();
		
		return s;
	}
	
	private String importData() {
		
		String message = "Proceso finalizado OK", sql = "";
		ResultSet rs = null;
		Statement stmt = null;
		MBPartner partner = null;
		Connection con = null;
		int partnerID = 0;		
				
		try{
			
			con = this.getConnectionToServer();
			stmt = con.createStatement();
			
			sql = "select * from c_bpartner where iscustomer='Y' and isvendor='N' and issalesrep='N' and isemployee='N' and isactive='Y' and ad_client_id = 1000005 and ad_org_id = 1000005" +
			      " and c_bpartner_id not in (1011356,1012647,1010724,1010736,1010740,1010727,1010741,1012163,1011029,1011553,1011820,1011255,1012098,1012936,1012371,1012820,1012907,1012650," +
                  " 1012804,1010928,1012549,1012097,1012597,1012647,1010749) order by name";

			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				partnerID = rs.getInt("c_bpartner_id");
				
				partner = new MBPartner(getCtx(),0,get_TrxName()); //nuevo cliente a crear
				
				String value = validateValue(rs.getString("value")); //obtengo un value valido
				
				System.out.print(value);
				System.out.println();
				
				partner.setValue(value);
				partner.setName(rs.getString("name"));
				partner.setName2(rs.getString("name2"));
				partner.setDUNS(rs.getString("duns"));
				partner.setRating(rs.getString("rating"));
				partner.setC_BP_Group_ID(rs.getInt("c_bp_group_id"));
				partner.setFirstSale(rs.getTimestamp("firstsale"));
				partner.setuy_prioridadgenent(rs.getInt("uy_prioridadgenent"));
				partner.setInvoiceRule(rs.getString("invoicerule"));
				partner.setDeliveryRule(rs.getString("deliveryrule"));
				partner.setDeliveryViaRule(rs.getString("deliveryviarule"));
				partner.setM_DiscountSchema_ID(1000003);
				partner.setFlatDiscount(rs.getBigDecimal("flatdiscount"));
				partner.setPaymentRule(rs.getString("paymentrule"));
				partner.setSO_CreditLimit(rs.getBigDecimal("so_creditlimit"));
				partner.setuy_creditlimit_doc(rs.getBigDecimal("uy_creditlimit_doc"));
				partner.setuy_credit_action(rs.getString("uy_credit_action"));
				partner.setIsCustomer(true);
				
				//seteo canal de ventas
				if(rs.getInt("uy_canalventas_id")==1000004){
					//no seteo nada
				} else if(rs.getInt("uy_canalventas_id")==1000005){
					partner.setUY_CanalVentas_ID(1000004);
				} else if(rs.getInt("uy_canalventas_id")==1000006 || rs.getInt("uy_canalventas_id")==1000007){
					partner.setUY_CanalVentas_ID(1000005);
				} else if(rs.getInt("uy_canalventas_id")==1000008){
					partner.setUY_CanalVentas_ID(1000006);
				} else if(rs.getInt("uy_canalventas_id")==1000010){
					partner.setUY_CanalVentas_ID(1000008);
				} else if(rs.getInt("uy_canalventas_id")==1000009){
					partner.setUY_CanalVentas_ID(1000007);	
				} else partner.setUY_CanalVentas_ID(rs.getInt("uy_canalventas_id"));		
				
				//seteo lista de precios
				partner.setM_PriceList_ID(1000013);
				/*int list = rs.getInt("m_pricelist_id");
				
				if(list > 0){
					
					if(list==1000002) list=1000000;
					if(list==1000003) list=1000000;
					if(list==1000004) list=1000000;
					if(list==1000005) list=1000000;
					if(list==1000007) list=1000000;
					if(list==1000008) list=1000000;
					if(list==1000009) list=1000000;
					if(list==1000010) list=1000000;
					if(list==1000011) list=1000000;
					if(list==1000012) list=1000000;
					if(list==1000013) list=1000000;
					if(list==1000014) list=1000000;
					if(list==1000015) list=1000000;
					if(list==1000016) list=1000000;
					if(list==1000017) list=1000000;
					if(list==1000018) list=1000000;
					if(list==1000019) list=1000000;
					if(list==1000020) list=1000000;
					if(list==1000021) list=1000000;
					if(list==1000022) list=1000000;
					if(list==1000023) list=1000000;
					if(list==1000030) list=1000000;
					
					partner.setM_PriceList_ID(list);					
				}*/				
				
				//seteo termino de pago
				int term = rs.getInt("c_paymentterm_id");
				
				if(term > 0){
					
					if(term==1000001) term=1000029;
					//if(term==1000002) term=1000002;
					//if(term==1000004) term=1000000;
					if(term==1000006) term=1000007;
					if(term==1000009) term=1000031;
					if(term==1000010) term=1000021;
					if(term==1000011) term=1000030;
					if(term==1000012) term=1000020;
					//if(term==1000013) term=1000000;
					if(term==1000014) term=1000010;
					if(term==1000015) term=1000011;
					if(term==1000016) term=1000028;
					if(term==1000017) term=1000018;
					if(term==1000018) term=1000019;
					if(term==1000020) term=1000008;
					if(term==1000021) term=1000022;
					if(term==1000022) term=1000024;
					//if(term==1000023) term=1000000;
					if(term==1000024) term=1000009;
					if(term==1000026) term=1000032;
					if(term==1000027) term=1000026;
					if(term==1000029) term=1000027;
					if(term==1000030) term=1000025;
					if(term==1000032) term=1000033;
					
					partner.setC_PaymentTerm_ID(term);
									
				}				
				
				//seteo el vendedor
				int rep = rs.getInt("salesrep_id");

				if(rep > 0){

					if(rep==1002959){
						partner.setSalesRep_ID(1001101);
					} else if(rep==1002960){
						partner.setSalesRep_ID(1001105);
					} else if(rep==1002961) partner.setSalesRep_ID(1001104);

				}				
				
				partner.saveEx(); //guardo el cliente
				
				//importo localizaciones
				importBPLocation(partner.get_ID(),partnerID, con);
				
				//importo contabilidad en ME
				importAccountME(partner.get_ID(),partnerID, con);
				
				//seteo contabilidad del cliente
				/*MBPCustomerAcct acct = new MBPCustomerAcct(getCtx(),0,get_TrxName());
				
				acct.setC_BPartner_ID(partner.get_ID());
				acct.setC_AcctSchema_ID(1000005);
				acct.setC_Receivable_Acct(1000306);
				acct.setC_Prepayment_Acct(1000305);
				acct.setC_Receivable_Services_Acct(1000307);
				acct.saveEx();*/				
				
			}
			
			stmt.close();
			con.close();			
			
		} catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}				
		
		return message;
	}
	
	private String validateValue(String val) {
		
		String value = val;
		
		MBPartner partner = MBPartner.forValue(getCtx(), val, get_TrxName());
		
		if(partner != null){
			if(partner.get_ID()>0){
				
				value = val + "0";
				
				return validateValue(value);
			}			
		}
		
		return value;
	}

	private void importAccountME(int newPartnerID, int partnerID, Connection con) {
		
		ResultSet rs = null;
		Statement stmt = null;
		
		try{
			
			stmt = con.createStatement();
			
			String sql = "select * from uy_clientesme_acct where c_bpartner_id = " + partnerID;

			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				MClientesMeAcct acct = new MClientesMeAcct(getCtx(),0,get_TrxName());
				
				acct.setC_BPartner_ID(newPartnerID);
				acct.setC_AcctSchema_ID(1000005);
				acct.setC_Currency_ID(rs.getInt("c_currency_id"));
				acct.setC_Receivable_Acct(rs.getInt("c_receivable_acct"));
				acct.setC_Receivable_Services_Acct(rs.getInt("c_receivable_services_acct"));
				acct.setC_Prepayment_Acct(rs.getInt("c_prepayment_acct"));
				acct.saveEx();				
				
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
	
		}			
		
	}

	public void importBPLocation (int newPartnerID, int partnerID, Connection con){

		MLocation loc = null;
		MBPartnerLocation bloc = null;
		ResultSet rs = null;
		Statement stmt = null;
				
		try{
			
			stmt = con.createStatement();
			
			String sql = "select * from c_bpartner_location where c_bpartner_id = " + partnerID;

			rs = stmt.executeQuery(sql);

			while(rs.next()){
				
				if(rs.getInt("c_location_id")>0) loc = importLocation(rs.getInt("c_location_id"),con);
				
				bloc = new MBPartnerLocation(getCtx(),0,get_TrxName()); //instancio nueva localizacion
				
				bloc.setC_BPartner_ID(newPartnerID);
				bloc.setName(rs.getString("name"));
				bloc.setUY_GLN(rs.getString("uy_gln"));
				bloc.setPhone(rs.getString("phone"));
				bloc.setPhone2(rs.getString("phone2"));
				bloc.setFax(rs.getString("fax"));
				
				if(rs.getString("isshipto").equalsIgnoreCase("Y")){
					bloc.setIsShipTo(true);					
				} else bloc.setIsShipTo(false);
				
				if(rs.getString("isbillto").equalsIgnoreCase("Y")){
					bloc.setIsBillTo(true);					
				} else bloc.setIsBillTo(false);
				
				if(rs.getString("ispayfrom").equalsIgnoreCase("Y")){
					bloc.setIsPayFrom(true);					
				} else bloc.setIsPayFrom(false);
				
				if(rs.getString("isremitto").equalsIgnoreCase("Y")){
					bloc.setIsRemitTo(true);					
				} else bloc.setIsRemitTo(false);

				//bloc.setC_SalesRegion_ID(rs.getInt("c_salesregion_id"));
				
				//se cargan las zonas de reparto
				int zona = rs.getInt("uy_zonareparto_id");
				
				if(zona > 0){ //modifico zona segun ID de vessena
					
					if(zona==1000034) zona=1000000;
					if(zona==1000035) zona=1000001;
					if(zona==1000006) zona=1000005;
					if(zona==1000007) zona=1000006;
					if(zona==1000008) zona=1000007;
					if(zona==1000009) zona=1000008;
					if(zona==1000010) zona=1000009;
					if(zona==1000000) zona=1000010;
					if(zona==1000012) zona=1000011;
					if(zona==1000013) zona=1000012;
					if(zona==1000014) zona=1000013;
					if(zona==1000015) zona=1000014;
					if(zona==1000016) zona=1000015;
					if(zona==1000017) zona=1000016;
					if(zona==1000018) zona=1000017;
					if(zona==1000019) zona=1000018;
					if(zona==1000020) zona=1000019;
					if(zona==1000021) zona=1000020;
					if(zona==1000022) zona=1000021;
					if(zona==1000023) zona=1000022;
					if(zona==1000024) zona=1000023;
					if(zona==1000025) zona=1000024;
					if(zona==1000026) zona=1000025;
					if(zona==1000027) zona=1000026;
					if(zona==1000028) zona=1000027;
					if(zona==1000029) zona=1000028;
					if(zona==1000030) zona=1000029;
					if(zona==1000031) zona=1000030;
					if(zona==1000032) zona=1000031;
					if(zona==1000033) zona=1000032;
					if(zona==1000005) zona=1000033;
					if(zona==1000011) zona=1000034;
					if(zona==1000001) zona=1000035;
					if(zona==1000037) zona=1000036;
					if(zona==1000038) zona=1000037;
					if(zona==1000039) zona=1000012;
					if(zona==1000040) zona=1000038;
					if(zona==1000042) zona=1000039;		
					
					bloc.setUY_ZonaReparto_ID(zona);
				}
								
				if(loc.get_ID()>0) bloc.setC_Location_ID(loc.get_ID());
				
				bloc.saveEx();						

			}			


		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
	
		}		

	}	
	
	public MLocation importLocation (int locationID, Connection con){

		MLocation loc = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try{
			
			stmt = con.createStatement();
			
			String sql = "select * from c_location where c_location_id = " + locationID;

			rs = stmt.executeQuery(sql);

			if(rs.next()){

				loc = new MLocation(getCtx(),0,get_TrxName()); //instancio nueva direccion

				loc.setAddress1(rs.getString("address1"));
				loc.setAddress2(rs.getString("address2"));
				loc.setCity(rs.getString("city"));
				loc.setPostal(rs.getString("postal"));
				loc.setC_Country_ID(rs.getInt("c_country_id"));
				loc.setC_Region_ID(rs.getInt("c_region_id"));
				loc.setRegionName(rs.getString("regionname"));
				loc.setAddress3(rs.getString("address3"));
				loc.setAddress4(rs.getString("address4"));
				loc.setUY_Localidades_ID(rs.getInt("uy_localidades_id"));
				loc.setUY_Departamentos_ID(rs.getInt("uy_departamentos_id"));

				loc.saveEx();						

			}			


		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
	
		}		

		return loc;

	}

	public Connection getConnectionToServer() throws Exception{
		
		Connection retorno = null;
		String connectString = "jdbc:postgresql://1.1.20.123:5433/alianzur";
		String user = "adempiere";
		String password = "opUP2012";

		retorno = DriverManager.getConnection(connectString, user, password);	
		
		return retorno;
	}

}
