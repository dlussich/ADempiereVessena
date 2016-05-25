/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * @author gbrust
 *
 */
public class MFduCoefficientLine extends X_UY_Fdu_CoefficientLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4329174713745835673L;

	/**
	 * @param ctx
	 * @param UY_Fdu_CoefficientLine_ID
	 * @param trxName
	 */
	public MFduCoefficientLine(Properties ctx, int UY_Fdu_CoefficientLine_ID,
			String trxName) {
		super(ctx, UY_Fdu_CoefficientLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCoefficientLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static List<MFduCoefficientLine> getMFduCoefficientLineForHdrId(Properties ctx, int UY_Fdu_CoefficientHdr_ID){
		
		String whereClause = X_UY_Fdu_CoefficientLine.COLUMNNAME_UY_Fdu_CoefficientHdr_ID + "=" + UY_Fdu_CoefficientHdr_ID;
		
		List<MFduCoefficientLine> lines = new Query(ctx, I_UY_Fdu_CoefficientLine.Table_Name, whereClause, null)
		.list();
		
		return lines;		
	}
	
	public void setCuota(int nroCuota, BigDecimal valor){
		
		switch (nroCuota) {
		case 2:
			this.setCuotas_2(valor);
			break;
		case 3:
			this.setcuotas_3(valor);
			break;
		case 4:
			this.setcuotas_4(valor);
			break;
		case 5:
			this.setcuotas_5(valor);
			break;
		case 6:
			this.setcuotas_6(valor);
			break;
		case 7:
			this.setcuotas_7(valor);
			break;
		case 8:
			this.setcuotas_8(valor);
			break;
		case 9:
			this.setcuotas_9(valor);
			break;
		case 10:
			this.setcuotas_10(valor);
			break;
		case 11:
			this.setcuotas_11(valor);
			break;
		case 12:
			this.setcuotas_12(valor);
			break;
		case 13:
			this.setcuotas_13(valor);
			break;
		case 14:
			this.setcuotas_14(valor);
			break;
		case 15:
			this.setcuotas_15(valor);
			break;
		case 16:
			this.setcuotas_16(valor);
			break;
		case 17:
			this.setcuotas_17(valor);
			break;
		case 18:
			this.setcuotas_18(valor);
			break;
		case 19:
			this.setcuotas_19(valor);
			break;
		case 20:
			this.setcuotas_20(valor);
			break;
		case 21:
			this.setcuotas_21(valor);
			break;
		case 22:
			this.setcuotas_22(valor);
			break;
		case 23:
			this.setcuotas_23(valor);
			break;
		case 24:
			this.setcuotas_24(valor);
			break;

		default:
			break;
		}
	}
	
	public BigDecimal getCuota(int nroCuota){
		
		switch (nroCuota) {
		case 2:
			return this.getCuotas_2();			
		case 3:
			return this.getcuotas_3();			
		case 4:
			return this.getcuotas_4();			
		case 5:
			return this.getcuotas_5();			
		case 6:
			return this.getcuotas_6();			
		case 7:
			return this.getcuotas_7();			
		case 8:
			return this.getcuotas_8();			
		case 9:
			return this.getcuotas_9();			
		case 10:
			return this.getcuotas_10();			
		case 11:
			return this.getcuotas_11();			
		case 12:
			return this.getcuotas_12();			
		case 13:
			return this.getcuotas_13();			
		case 14:
			return this.getcuotas_14();			
		case 15:
			return this.getcuotas_15();			
		case 16:
			return this.getcuotas_16();			
		case 17:
			return this.getcuotas_17();			
		case 18:
			return this.getcuotas_18();			
		case 19:
			return this.getcuotas_19();			
		case 20:
			return this.getcuotas_20();			
		case 21:
			return this.getcuotas_21();			
		case 22:
			return this.getcuotas_22();			
		case 23:
			return this.getcuotas_23();			
		case 24:
			return this.getcuotas_24();			

		default:
			return Env.ZERO;
		}
	}

}
