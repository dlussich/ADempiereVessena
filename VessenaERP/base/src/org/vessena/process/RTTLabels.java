/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Sep 3, 2015
*/
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.PrintService;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.openup.model.MRReclamo;
import org.openup.model.MTTCard;
import org.openup.model.MTTConfig;

import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;

/**
 * org.openup.process - RTTLabels
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Sep 3, 2015
*/
public class RTTLabels extends SvrProcess {

	private String tipoImpresion = null;
	private MTTCard card = null;
	
	/***
	 * Constructor.
	*/
	public RTTLabels() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParameter();

		int idCard = 0;
		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){					
						
				if (name.equalsIgnoreCase("PrintDoc")){
					if (para[i].getParameter()!=null){
						this.tipoImpresion = (String)para[i].getParameter();
					}
				}
				else if (name.equalsIgnoreCase("UY_TT_Card_ID")){
					idCard = ((BigDecimal)para[i].getParameter()).intValueExact(); 
				}			
			}
		}

		// Obtengo modelo de cuenta tracking
		if (idCard > 0){
			card = new MTTCard(getCtx(), idCard, null);
		}
	
	}

	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String printerName = "zebra"; // This should match your printer name
		PrintService ps = null;

		try {
			MTTConfig config = MTTConfig.forValue(getCtx(), null, "tarjeta");
			MRReclamo incidencia = (MRReclamo)this.card.getUY_R_Reclamo();
			
			printerName = "zebra";
			ps = PrintServiceMatcher.findPrinter(printerName);
			if (ps == null) {
				throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
			}

			if ((this.tipoImpresion.equalsIgnoreCase("ETISOBRE")) || (this.tipoImpresion.equalsIgnoreCase("TODAS"))){

				String idTarjeta = "";
				idTarjeta = config.getEmpCodeRedPagos();

				Timestamp today = new Timestamp(System.currentTimeMillis());
				Date deliveryDate = new Date(today.getTime());
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
				idTarjeta += sdf.format(deliveryDate);
				String subAgencia = this.card.getSubAgencia();
				subAgencia = org.apache.commons.lang.StringUtils.leftPad(subAgencia, 3, "0");
				idTarjeta += subAgencia;
				String nroinc = incidencia.getDocumentNo();
				nroinc = org.apache.commons.lang.StringUtils.leftPad(nroinc, 16, "0");
				idTarjeta += nroinc;
				
				String cedula = this.card.getCedula();
				if (cedula == null) cedula = "";
				
				String labelCode =  "CT~~CD,~CC^~CT~\n" +
						"^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
						"^XA\n" +
						"^MMT\n" +
						"^PW559\n" +
						"^LL0240\n" +
						"^LS0\n" +
						"^BY2,3,40^FT546,80^BCI,,Y,N\n" +
						"^FD>;" + incidencia.getDocumentNo() + "^FS\n" +
						"^BY2,3,59^FT468,173^BCI,,Y,N\n" +
						"^FD>;" + idTarjeta + "^FS\n" +
						"^FT337,74^A0I,17,16^FH\\^FDNombre:^FS\n" +
						"^FT337,41^A0I,17,16^FH\\^FDCedula:^FS\n" +
						"^FT264,74^A0I,20,19^FH\\^FD" + this.card.getName() + "^FS\n" +
						"^FT264,41^A0I,17,16^FH\\^FD" + cedula + "^FS\n" +
						"^FT336,106^A0I,17,16^FH\\^FDCuenta:^FS\n" +
						"^FT264,109^A0I,17,16^FH\\^FD" + this.card.getAccountNo() + "^FS\n" +
						"^PQ1,0,1,Y^XZ\n";
				
				PrintRaw p = new PrintRaw(ps, labelCode);			
				p.print();
			}
			
			if ((this.tipoImpresion.equalsIgnoreCase("ETICONTRATO")) || (this.tipoImpresion.equalsIgnoreCase("TODAS"))){

				String labelCode = "CT~~CD,~CC^~CT~\n" +
						"^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
						"^XA\n" +
						"^MMT\n" +
						"^PW559\n" +
						"^LL0240\n" +
						"^LS0\n" +
						"^BY4,3,57^FT127,87^BCN,,Y,N\n" +
						"^FD>;" + this.card.getsolnro() + "^FS\n" +
						"^FT10,167^A0N,23,24^FH\\^FDNombre:^FS\n" +
						"^FT10,200^A0N,23,24^FH\\^FDC.I.:^FS\n" +
						"^FT57,200^A0N,23,24^FH\\^FD" + this.card.getCedula() + "^FS\n" +
						"^FT100,167^A0N,23,24^FH\\^FD" +this.card.getName() + "^FS\n" +
						"^PQ1,0,1,Y^XZ\n";
				
				PrintRaw p = new PrintRaw(ps, labelCode);			
				p.print();
			}
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		return "OK";
	}

}
