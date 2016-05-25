package org.openup.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MUser;
import org.compiere.process.SvrProcess;
import org.openup.model.MTTCard;
import org.openup.model.MTTHand;
import org.openup.util.OpenUpUtils;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class PTTHandVale extends SvrProcess {
	
	MTTHand entrega = null;
	
	
	
	@Override
	protected void prepare() {
		try{
			this.entrega = new MTTHand(getCtx(), this.getRecord_ID(), get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}

	@Override
	protected String doIt() throws Exception {
			if(entrega.getUY_TT_Card_ID()>0){
				MTTCard card = new MTTCard(getCtx(),entrega.getUY_TT_Card_ID(),get_TrxName());
				if(card!=null){
					card.setIsValeSigned(true);//se guarda en la tarjeta que se firma el vale
					card.saveEx();
					impactoFinanciaFirmaVale(card); //Se necesitan los dato para impactar en financial 11-12-2014
					entrega.setisValeSignature(true);
					entrega.setIsNeedCheckVale(false);
					entrega.setCardAction("confirmar");
					entrega.setobservaciones("Firma Confirmada");
					entrega.saveEx();

			}
			}
			
			
		
		return "OK";
	}

	
	/** Metodo para impactar en financial pro cuando se entrega tarjeta en sucursal
	 * @author sylvie.bouissa OpenUp 11/02/2015 Issue#3273
	 * 
	 * @param card
	 * Tabla a impactar PROMPAG2
	 */
	public void impactoFinanciaFirmaVale(MTTCard card) {
		Connection con = null;
		Statement stmt = null;
		int idConexionFinancial = 1000011;
		//esta es la conexion al sql server, a
		try {
			con = OpenUpUtils.getConnectionToSqlServer(idConexionFinancial);
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false); //esto para iniciar una transaccion
		
				
					
			String sql = "";
			SimpleDateFormat dfConHora = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss.sss");
			SimpleDateFormat dfSinHora = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
			int idPdLinCorrelativo = obtenerIdentificadorTablaLegajos(con,stmt,"Lin.Pdp Comentarios");
			String pdpdTextValeFirmado = "VALE FIRMADO"; //Constante que indico anibal
			String PdPCConValeFirmado = "A61";
			String usuario = new MUser(getCtx(), this.getAD_User_ID(), this.get_TrxName()).getName().toUpperCase();
			if(usuario.length() > 10 ){
				usuario = usuario.substring(0, 9);
			}
			if(idPdLinCorrelativo>0){
					
					sql = "INSERT INTO PROMPAG2 (EmpCod, SolNro, PdpNro, PdpLin, PdpLinFec, PdpText, PdpLFReal, PdpLinCli," +
							" PdpLinUsrI, PdpCCon, PdpVenCod) VALUES (" +
							  10 + "," + //EmpCod
							  0 + "," + //SolNro
							  0 + "," + //PdpNro
							  idPdLinCorrelativo + "," + //PdpLin
							  "'" + dfConHora.format(card.getUpdated()) + "'," + //PdpLinFec
							  "'" +pdpdTextValeFirmado+ "'," + //PdpText  
							  "'" + dfSinHora.format(card.getUpdated()) + "'," + //PdpLFReal
							  "'" + card.getCedula() + "'," + //PdpLinCli
							  "'" + usuario + "'," +//PdpLinUsrI
							  "'" + PdPCConValeFirmado +"'," +//PdPCCon
							  "''" + //PdpVenCod --nada
							  ")";		 
				
				stmt.executeUpdate(sql);
				//Luego de hacer todo, recien ahi termino la transaccion
		    	con.commit(); //This commits the transaction and starts a new one.
				stmt.close(); //This turns off the transaction.	    			
				con.close();
				
			}else{
				new AdempiereException("No se logro obtener el usuario para guardar el legajo");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * OpenUp. Sylvie Bouissa. 11/02/2015. ISSUE # 3273
	 * Se actualiza el ID de la tabla NUME en sqlserver correspondiente a la descripcion nroDesc
	 * Se obtiene el ID para el nuevo registro a guardar en la tabla PROMPAG2 en sqlserver 
	 * NroDsc corresponde al la descripcion
	 * */
	private int obtenerIdentificadorTablaLegajos(Connection con, Statement stmt,String nroDesc){
		
		int retorno = 0;
		ResultSet rs = null;			
		//'Lin.Pdp Comentarios'
		try {		
			//Modifico el ID para crear uno nuevo.
			stmt.executeUpdate("update NUME set NroUlt = (NroUlt + 1) where NroDsc = '"+nroDesc+"' and EmpCod = 1");			
			
			//Ahora obtengo ese valor que modifique			
			rs = stmt.executeQuery("select NroUlt from NUME where NroDsc = '"+nroDesc+"' and EmpCod = 1");

			while (rs.next()) {				
				retorno = rs.getInt("NroUlt");
			}
			
			rs.close();			
			
		} catch (Exception e) {					
			throw new AdempiereException(e);
		} 
		finally {			
			if (con != null){				
				try {					
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}					
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}	
		return retorno;
	}
	
}
