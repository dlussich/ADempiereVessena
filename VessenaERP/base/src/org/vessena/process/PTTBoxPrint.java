/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 26, 2015
*/
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.PrintService;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MTTConfig;
import org.openup.model.X_UY_TT_BoxCard;
import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;

/**
 * org.openup.process - PTTBoxPrint
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 26, 2015
*/
public class PTTBoxPrint extends SvrProcess {

	private String tipoImpresion = null;
	
	private int adProcessID = -1;
	
	
	/***
	 * Constructor.
	*/
	public PTTBoxPrint() {
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
						
				if (name.equalsIgnoreCase("PrintDoc")){
					if (para[i].getParameter()!=null){
						this.tipoImpresion = (String)para[i].getParameter();
					}
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
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		String printerName = "zebra"; // This should match your printer name
		PrintService ps = null;
		
		try {
			
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			
			if (this.tipoImpresion == null){
				throw new AdempiereException("No se pudo obtener tipo de documento a imprimir");
			}

			// Segun tipo de documento a imprimir
			if (this.tipoImpresion.equalsIgnoreCase("ANEXOA")){
				this.adProcessID = MProcess.getProcess_ID("UY_RTT_AnexoA", null);
			}
			else if (this.tipoImpresion.equalsIgnoreCase("ANEXOB")){
				this.adProcessID = MProcess.getProcess_ID("UY_RTT_AnexoB", null);
			}
			else if ((this.tipoImpresion.equalsIgnoreCase("ETISOBRE")) || (this.tipoImpresion.equalsIgnoreCase("ETICONTRATO"))){

				printerName = "zebra";
				ps = PrintServiceMatcher.findPrinter(printerName);
				if (ps == null) {
					throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
				}
			}
			
			int boxID = this.getRecord_ID();
			
			// Obtengo cuentas de esta caja ordenadas descendentemente por posicion
			sql = " select a.*, r.documentno, coalesce(b.subagencia,'') as subagencia, b.accountno, "
					+ " coalesce(b.cedula, '') as cedula, b.name, coalesce(b.solnro,'') as solnro "
					+ " from uy_tt_boxcard a "
					+ " inner join uy_tt_card b on a.uy_tt_card_id = b.uy_tt_card_id "
					+ " inner join uy_r_reclamo r on b.uy_r_reclamo_id = r.uy_r_reclamo_id "
					+ " where a.uy_tt_box_id =? "
					+ " order by a.uy_tt_boxcard_id desc ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, boxID);			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				boolean imprimoCuenta = false;
				
				if (this.tipoImpresion.equalsIgnoreCase("ANEXOA")){
					if (rs.getString(X_UY_TT_BoxCard.COLUMNNAME_PrintDoc1).equalsIgnoreCase("Y")){
						imprimoCuenta = true;
					}
				}
				else if (this.tipoImpresion.equalsIgnoreCase("ANEXOB")){
					if (rs.getString(X_UY_TT_BoxCard.COLUMNNAME_PrintDoc2).equalsIgnoreCase("Y")){
						imprimoCuenta = true;
					}
				}
				else if (this.tipoImpresion.equalsIgnoreCase("ETISOBRE")){

					String idTarjeta = "";
					idTarjeta = config.getEmpCodeRedPagos();

					Timestamp today = new Timestamp(System.currentTimeMillis());
					Date deliveryDate = new Date(today.getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
					idTarjeta += sdf.format(deliveryDate);
					String subAgencia = rs.getString("subagencia");
					subAgencia = org.apache.commons.lang.StringUtils.leftPad(subAgencia, 3, "0");
					idTarjeta += subAgencia;
					String nroinc = rs.getString("documentno");
					nroinc = org.apache.commons.lang.StringUtils.leftPad(nroinc, 16, "0");
					idTarjeta += nroinc;
					
					String labelCode =  "CT~~CD,~CC^~CT~\n" +
							"^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
							"^XA\n" +
							"^MMT\n" +
							"^PW559\n" +
							"^LL0240\n" +
							"^LS0\n" +
							"^BY2,3,40^FT546,80^BCI,,Y,N\n" +
							"^FD>;" + rs.getString("documentno") + "^FS\n" +
							"^BY2,3,59^FT468,173^BCI,,Y,N\n" +
							"^FD>;" + idTarjeta + "^FS\n" +
							"^FT337,74^A0I,17,16^FH\\^FDNombre:^FS\n" +
							"^FT337,41^A0I,17,16^FH\\^FDCedula:^FS\n" +
							"^FT264,74^A0I,20,19^FH\\^FD" + rs.getString("name") + "^FS\n" +
							"^FT264,41^A0I,17,16^FH\\^FD" + rs.getString("cedula") + "^FS\n" +
							"^FT336,106^A0I,17,16^FH\\^FDCuenta:^FS\n" +
							"^FT264,109^A0I,17,16^FH\\^FD" + rs.getString("accountno") + "^FS\n" +
							"^PQ1,0,1,Y^XZ\n";
					
					PrintRaw p = new PrintRaw(ps, labelCode);			
					p.print();
				}
				else if (this.tipoImpresion.equalsIgnoreCase("ETICONTRATO")){
					if (rs.getString(X_UY_TT_BoxCard.COLUMNNAME_PrintDoc4).equalsIgnoreCase("Y")){
						
						String labelCode = "CT~~CD,~CC^~CT~\n" +
								"^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
								"^XA\n" +
								"^MMT\n" +
								"^PW559\n" +
								"^LL0240\n" +
								"^LS0\n" +
								"^BY4,3,57^FT127,87^BCN,,Y,N\n" +
								"^FD>;" + rs.getString("solnro") + "^FS\n" +
								"^FT10,167^A0N,23,24^FH\\^FDNombre:^FS\n" +
								"^FT10,200^A0N,23,24^FH\\^FDC.I.:^FS\n" +
								"^FT57,200^A0N,23,24^FH\\^FD" + rs.getString("cedula") + "^FS\n" +
								"^FT100,167^A0N,23,24^FH\\^FD" + rs.getString("name") + "^FS\n" +
								"^PQ1,0,1,Y^XZ\n";

						PrintRaw p = new PrintRaw(ps, labelCode);			
						p.print();
					}
				}
				
				if (imprimoCuenta){

					MPInstance instance = new MPInstance(Env.getCtx(), this.adProcessID, 0);
					instance.saveEx();
					
					ProcessInfo pi = new ProcessInfo (this.tipoImpresion, adProcessID);
					pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
					
					MPInstancePara para = new MPInstancePara(instance, 10);
					para.setParameter("UY_TT_Card_ID", new BigDecimal(rs.getInt("uy_tt_card_id")));
					para.saveEx();
					
					ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
					worker.start();     
					
					// Pausa de 1 segundo preventiva para impresion masiva
					java.lang.Thread.sleep(3000);
					
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return "OK";
	}

}
