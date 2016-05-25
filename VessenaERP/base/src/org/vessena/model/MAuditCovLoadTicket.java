/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author SBouissa
 *
 */
public class MAuditCovLoadTicket extends X_UY_Audit_Cov_LoadTicket {

	private String CVenta = "1"; //
	private String CPedido = "2";
	private String CDevolucion ="3"; //
	private String CEstadoCta ="4";
	private String CExentoIVA ="5";
	private String CCanje ="6";
	private String CIventario ="7";
	private String CCajera ="8";
	private String CGift ="9";
	private String CPagoCaja ="10";
	private String CFactura ="11";
	private String CNoGenerado ="12";
	private String CConsulta ="13";
	private String CApGaveta ="14";
	private String CCredDevolucion ="15";
	private String CFondeo ="16";
	private String CRetiro ="17";
	private String CZ ="18";
	private String CIngresoPersonal = "19";
	private String CNoDefinido = "20";
	
	/**
	 * @param ctx
	 * @param UY_Audit_Cov_LoadTicket_ID
	 * @param trxName
	 */
	public MAuditCovLoadTicket(Properties ctx, int UY_Audit_Cov_LoadTicket_ID,
			String trxName) {
		super(ctx, UY_Audit_Cov_LoadTicket_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAuditCovLoadTicket(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public void updateCountCabezalAudit(String tipoLinea, String totalAPagar) {
		if (tipoLinea.equals("19")){
			System.out.println("TIKET Z");
		}
		switch (Integer.valueOf(tipoLinea)) {
		case 1:	
			this.setqtyventas((this.getqtyventas().add(new BigDecimal(1))));
			this.setamtventas((this.getamtventas().add(new BigDecimal(totalAPagar))));
			break;
		case 2:	//al parecer no se usa!!!!!!!!!!!!
			this.setqtypedido((this.getqtypedido().add(new BigDecimal(1))));
			this.setamtpedido((this.getamtpedido().add(new BigDecimal(totalAPagar))));
			break;
		case 3: 
			this.setqtydevolucion((this.getqtydevolucion().add(new BigDecimal(1))));
			this.setamtdevolucion((this.getamtdevolucion().add(new BigDecimal(totalAPagar))));
			break;
		case 4: 
			this.setqtyestadocta((this.getqtyestadocta().add(new BigDecimal(1))));
			this.setamtestadocta((this.getamtestadocta().add(new BigDecimal(totalAPagar))));
			break;
		case 5: // NO SE USA
			this.setqtyexentoiva((this.getqtyexentoiva().add(new BigDecimal(1))));
			this.setamtexentoiva((this.getamtexentoiva().add(new BigDecimal(totalAPagar))));
			break;
		case 6: 
			this.setqtycanje((this.getqtycanje().add(new BigDecimal(1))));
			this.setamtcanje((this.getamtcanje().add(new BigDecimal(totalAPagar))));
			break;
		case 7: 
			this.setqtyinventario((this.getqtycanje().add(new BigDecimal(1))));
			this.setamtinventario((this.getamtinventario().add(new BigDecimal(totalAPagar))));
			break;
		case 8: 
			this.setqtycajera((this.getqtycajera().add(new BigDecimal(1))));
			this.setamtcajera((this.getamtcajera().add(new BigDecimal(totalAPagar))));
			break;
		case 9: // NO SE USA
			this.setqtygift((this.getqtygift().add(new BigDecimal(1))));
			this.setamtgift((this.getamtgift().add(new BigDecimal(totalAPagar))));
			break;
		case 10: 
			this.setqtypagocaja((this.getqtygift().add(new BigDecimal(1))));
			//this.setamtpagocaja((this.getamtpagocaja().add(new BigDecimal(totalAPagar))));
			break;
		case 11: 
			this.setqtyfactura((this.getqtyfactura().add(new BigDecimal(1))));
			//this.setamtfactura((this.getamtfactura().add(new BigDecimal(totalAPagar))));
			break;
		case 12: // NO SE USA ??
			this.setqtynogenerado((this.getqtynogenerado().add(new BigDecimal(1))));
			this.setamtnogenerado((this.getamtnogenerado().add(new BigDecimal(totalAPagar))));
			break;
		case 13: // NO SE USA ??
			this.setqtyconsulta((this.getqtyconsulta().add(new BigDecimal(1))));
			this.setamtconsulta((this.getamtconsulta().add(new BigDecimal(totalAPagar))));
			break;
		case 14: 
			this.setqtyconsulta((this.getqtyconsulta().add(new BigDecimal(1))));
			this.setamtconsulta((this.getamtconsulta().add(new BigDecimal(totalAPagar))));
			break;
			
		case 15: 
			this.setqtyapgaveta((this.getqtyapgaveta().add(new BigDecimal(1))));
			this.setamtapgaveta((this.getamtapgaveta().add(new BigDecimal(totalAPagar))));
			break;
			
		case 16: 
			this.setqtyceditodevolucion((this.getqtyceditodevolucion().add(new BigDecimal(1))));
			this.setamtceditodevolucion((this.getamtceditodevolucion().add(new BigDecimal(totalAPagar))));
			break;
		case 17: 
			this.setqtyfondeo((this.getqtyfondeo().add(new BigDecimal(1))));
			//this.setamtfondeo((this.getamtfondeo().add(new BigDecimal(totalAPagar))));
			break;			
		case 18: 
			this.setqtyretiro((this.getqtyretiro().add(new BigDecimal(1))));
			//this.setamtretiro((this.getamtretiro().add(new BigDecimal(totalAPagar))));
			break;			
		case 19:
			this.setqtyzprestamoefect((this.getqtyzprestamoefect().add(new BigDecimal(1))));
			this.setamtzprestamoefect((this.getamtzprestamoefect().add(new BigDecimal(totalAPagar))));
			break;
			
		case 20: 
			this.setqtyingredopersonalretiroprod((this.getqtyingredopersonalretiroprod().add(new BigDecimal(1))));
			this.setamtingredopersonalretiroprod((this.getamtingredopersonalretiroprod().add(new BigDecimal(totalAPagar))));
			break;
//			this.setqtynodefinido((this.getqtynodefinido().add(new BigDecimal(1))));
//			break;
		default:
			break;
		}
		
	}
	
	//se aumenta total retiro a raiz de linea con codigo 97
	public void updateMontoTotalRetiro(String monto) {
		this.setamtretiro((this.getamtretiro().add(new BigDecimal(monto))));
		if(this.getllinearetiro()!=null){
			int cantLineas = Integer.valueOf(this.getllinearetiro());
			cantLineas = cantLineas + 1;
			this.setllinearetiro(String.valueOf(cantLineas));
		}else{
			this.setllinearetiro("1");
		}
	}

	//se aumenta total pago caja a raiz de linea con codigo 52
	public void updateMontoTotalPagoCaja(String monto) {
		//this.setamtpagocaja((this.getamtpagocaja().add(new BigDecimal(monto))));
	}

	//se aumenta total nota credito a raiz de linea con codigo 92
	public void updateMontoTotalNtaCredito(String monto) {
		this.setamtceditodevolucion((this.getamtceditodevolucion().add(new BigDecimal(monto))));	
	}

	public void updateMontoTotalFactura(String monto) {
		this.setamtfactura((this.getamtfactura().add(new BigDecimal(monto))));
		
	}

	//se aumenta total fondeo a raiz de linea con codigo 95
	public void updateMontoTotalFondeo(String monto) {
		this.setamtfondeo((this.getamtfondeo().add(new BigDecimal(monto))));
		if(this.getllineafondeo()!=null){
			int cantLineas = Integer.valueOf(this.getllineafondeo());
			cantLineas = cantLineas + 1;
			this.setllineafondeo(String.valueOf(cantLineas));
		}else{
			this.setllineafondeo("1");
		}
	}

	//Diferentes tipos de VENTAS
	
		//Venta tarjeta (37)
		public void updateLineaTarjeta(String monto){
			if(this.getlventastarjeta()!=null){
				String cantMonto[] = this.getlventastarjeta().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventastarjeta((Integer.valueOf(cantMonto[0])+1) +"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventastarjeta("1#"+montoA);
			}
		}
		//Venta tarjeta offline (85)
		public void updateLineaTarjetaOffline(String monto) {
			if(this.getlventastarjetaofline()!=null){
				String cantMonto[] = this.getlventastarjetaofline().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventastarjetaofline((Integer.valueOf(cantMonto[0])+1) +"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventastarjetaofline("1#"+montoA);
			}
			
		}
		//Ventas ticket Luncheon (40)
		public void updateLineaLuncheon(String monto) {
			if(this.getlventasluncheon()!=null){
				String cantMonto[] = this.getlventasluncheon().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventasluncheon((Integer.valueOf(cantMonto[0])+1) +"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventasluncheon("1#"+montoA);
			}
		}
		//Ventas cheque (38)
		public void updateLineaCheque(String monto) {
			if(this.getlventascheque()!=null){
				String cantMonto[] = this.getlventascheque().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventascheque((Integer.valueOf(cantMonto[0])+1) +"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventascheque("1#"+montoA);
			}		
		}
		//Ticket alimentación ( 90 )
		public void updateLineaTAlimentacion(String monto) {
			if(this.getlventastalimentacion()!=null){
				String cantMonto[] = this.getlventastalimentacion().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventastalimentacion((Integer.valueOf(cantMonto[0])+1) +"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventastalimentacion("1#"+montoA);
			}
			
		}
		//Venta en efectivo (9)
		public void updateLineaPagoEfectivo(String monto, String cambio,String valorMoneda,int hdrId,boolean pesos) {
			MCovTicketHeader cabezal = new MCovTicketHeader(getCtx(),hdrId,get_TrxName());
			if(pesos){			
				BigDecimal totalTkt = cabezal.gettotalapagar();
				BigDecimal entrego = new BigDecimal(monto); 
				BigDecimal vuelto = new BigDecimal(cambio);
				
				if(totalTkt.equals(entrego.subtract(vuelto))){
					System.out.println(totalTkt.toString()+"--"+entrego.subtract(vuelto).toString());
				}else{
					System.out.println("No es igual: "+totalTkt.toString()+"--"+entrego.subtract(vuelto).toString());
				}
				
				if(this.getlventaenefectivo()!=null){
					String cantMonto[] = this.getlventaenefectivo().split("#");
					BigDecimal montoA = new BigDecimal(cantMonto[1]); 
					BigDecimal montoC = new BigDecimal(cantMonto[2]); 
					BigDecimal valorIngreso = new BigDecimal(valorMoneda);

					montoC = montoC.add(valorIngreso);

					montoA = montoA.add(new BigDecimal(monto));
	
					montoA = montoA.subtract(new  BigDecimal(cambio));
					this.setlventaenefectivo((Integer.valueOf(cantMonto[0])+1)+"#"+montoA+"#"+montoC);
				}else{
					BigDecimal montoA = new BigDecimal(monto);
					BigDecimal montoC = new BigDecimal(valorMoneda);
					montoA = montoA.subtract(new  BigDecimal(cambio));
					this.setlventaenefectivo("1#"+montoA+"#"+montoC);
				}
			}else{
				if(this.getlventaenefectivo()!=null){
					String cantMonto[] = this.getlventaenefectivo().split("#");
					BigDecimal montoA = new BigDecimal(cantMonto[1]); 
					BigDecimal montoC = new BigDecimal(cantMonto[2]); 
					BigDecimal valorIngreso = new BigDecimal(0);

					montoC = montoC.add(valorIngreso);

					montoA = montoA.add(new BigDecimal(0));
	
					montoA = montoA.subtract(new BigDecimal(cambio));
					this.setlventaenefectivo((Integer.valueOf(cantMonto[0])+1)+"#"+montoA+"#"+montoC);
				}else{
					BigDecimal montoA = new BigDecimal(0);
					BigDecimal montoC = new BigDecimal(0);
					montoA = montoA.subtract(new  BigDecimal(cambio));
					this.setlventaenefectivo("1#"+montoA+"#"+montoC);
				}
			}

			
		}
		
		
		//Venta cuenta corriente
		public void updateLineaPagoCtaCte(String monto,String cambio) {
			BigDecimal entrego = new BigDecimal (monto);
			entrego=entrego.subtract(new BigDecimal(cambio));
			if(this.getlventasclientesfidelizacion()!=null){
				String cantMonto[] = this.getlventasclientesfidelizacion().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(entrego);
				this.setlventasclientesfidelizacion((Integer.valueOf(cantMonto[0])+1)+"#"+montoA);
			}else{
				BigDecimal montoA = entrego;
				this.setlventasclientesfidelizacion("1#"+montoA);
			}
			
		}
		//Ventas efectivo sodexo (9) y (10)
		public void updateLineaEfectivoSodexo(String monto) {
			if(this.getlventasefectivosodexo()!=null){
				String cantMonto[] = this.getlventasefectivosodexo().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventasefectivosodexo((Integer.valueOf(cantMonto[0])+1)+"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventasefectivosodexo("1#"+montoA);
			}			
		}

		public void updateLineaTarjetaManual(String monto) {
			if(this.getlventastarjetaofline()!=null){
				String cantMonto[] = this.getlventastarjetaofline().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventastarjetaofline((Integer.valueOf(cantMonto[0])+1)+"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventastarjetaofline("1#"+montoA);
			}
			
		}

		public void updateLineaTarjetaCuota(String monto) {
			if(this.getlventastarjetacuota()!=null){
				String cantMonto[] = this.getlventastarjetacuota().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventastarjetacuota((Integer.valueOf(cantMonto[0])+1)+"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventastarjetacuota("1#"+montoA);
			}
			
		}

		public void updateLineaDevolucionEnvase(String monto) {
			if(this.getldevenvases()!=null){
				String cantMonto[] = this.getldevenvases().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setldevenvases((Integer.valueOf(cantMonto[0])+1)+"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setldevenvases("1#"+montoA);
			}
			
		}

		public void updateLineaVtaEfectivoDolares(String monto) {
			if(this.getlventaefectivodolares()!=null){
				String cantMonto[] = this.getlventaefectivodolares().split("#");
				BigDecimal montoA = new BigDecimal(cantMonto[1]); 
				montoA = montoA.add(new BigDecimal(monto));
				this.setlventaefectivodolares((Integer.valueOf(cantMonto[0])+1)+"#"+montoA);
			}else{
				BigDecimal montoA = new BigDecimal(monto);
				this.setlventaefectivodolares("1#"+montoA);
			}
						
		}

}
