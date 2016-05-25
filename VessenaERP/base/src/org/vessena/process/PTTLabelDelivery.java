/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Sep 24, 2015
*/
package org.openup.process;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.PrintService;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.openup.model.MDeliveryPoint;
import org.openup.model.MTTConfig;
import org.openup.model.MTTSeal;
import org.openup.model.MTTSealLoad;

import jzebra.PrintRaw;
import jzebra.PrintServiceMatcher;

/**
 * org.openup.process - PTTLabelDelivery
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Sep 24, 2015
*/
public class PTTLabelDelivery extends SvrProcess {

	//private MTTDelivery model = null;
	
	private MTTSealLoad model = null;
	
	/***
	 * Constructor.
	*/
	public PTTLabelDelivery() {
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		//this.model = new MTTDelivery(getCtx(), this.getRecord_ID(), get_TrxName());
		this.model = new MTTSealLoad(getCtx(), this.getRecord_ID(), get_TrxName());

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
			
			printerName = "zebra";
			ps = PrintServiceMatcher.findPrinter(printerName);
			if (ps == null) {
				throw new AdempiereException("No es posible detectar la Impresora de Etiquetas");
			}

			MTTSeal seal = (MTTSeal)this.model.getUY_TT_Seal();
			MDeliveryPoint delPoint = new MDeliveryPoint(getCtx(), seal.getUY_DeliveryPoint_ID_To(), null);
			if (delPoint.getSubAgencyNo() != null){
				if (!delPoint.getSubAgencyNo().equalsIgnoreCase("")){

					String idTarjeta = "";
					idTarjeta = config.getEmpCodeRedPagos();

					Date deliveryDate = new Date(model.getDateTrx().getTime());
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
					idTarjeta += sdf.format(deliveryDate);
					String subAgencia = delPoint.getSubAgencyNo().trim();
					subAgencia = org.apache.commons.lang.StringUtils.leftPad(subAgencia, 3, "0");
					idTarjeta += subAgencia;
					String qty = String.valueOf(seal.getQtyBook());
					qty = org.apache.commons.lang.StringUtils.leftPad(qty, 4, "0");
					idTarjeta += qty;
					
					String telefono = delPoint.getTelephone();
					if (telefono == null) telefono = "";
					String direccion = delPoint.getAddress1();
					if (direccion == null) direccion = "";
					direccion = delPoint.getName() + " - " + direccion;
					
					String labelCode = "CT~~CD,~CC^~CT~\n " +
							"^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR2,2~SD15^JUS^LRN^CI0^XZ\n" +
							"^XA\n" +
							"^MMT\n" +
							"^PW559\n" +
							"^LL0240\n" +
							"^LS0\n" +
							"^BY3,3,30^FT496,194^BCI,,Y,N\n" +
							"^FD>;" + idTarjeta + "^FS\n" +
							"^FT538,96^A0I,20,19^FH\\^FDCant. sobres^FS\n" +
							"^FT274,93^A0I,23,21^FH\\^FDTelefono^FS\n" +
							"^FT435,96^A0I,20,38^FH\\^FD" + qty + "^FS\n" +
							"^FT194,93^A0I,25,24^FH\\^FD" + telefono + "^FS\n" +
							"^FT531,131^A0I,25,33^FH\\^FD" + direccion + "^FS\n" +
							"^FT508,42^A0I,34,19^FH\\^FDServicio: Italcred entrega de tarjetas. Tel:24020398^FS\n" +
							"^PQ1,0,1,Y^XZ\n";
					
					PrintRaw p = new PrintRaw(ps, labelCode);			
					p.print();
				}
			}
				
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return "OK";
		
	}

}
