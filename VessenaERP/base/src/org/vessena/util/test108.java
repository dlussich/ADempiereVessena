package org.openup.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.compiere.process.SvrProcess;
import org.openup.process.PLoadFduFile;


public class test108 {

	public test108() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/10/2012
	 * @see
	 * @param args
	 */
	public static void main(String[] args) {

		//PLoadFduFile ff = new PLoadFduFile();
		//ff.execute();
		
		//java.sql.Timestamp today = new java.sql.Timestamp(System.currentTimeMillis());
		
		/*
		Timestamp today = Timestamp.valueOf("2013-07-31 15:25:46"); 

		java.util.GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.setTime(today);
		System.out.println(cal.get(java.util.Calendar.DAY_OF_WEEK));
		
		Timestamp toDate = OpenUpUtils.getToDate(today, Calendar.DAY_OF_MONTH, 3, 1000006, false, false, true);

		
		System.out.println(toDate.toString());
		*/
		
		BigDecimal monto = new BigDecimal("1234.34");
		String mm = org.openup.util.OpenUpUtils.formatSeparators(monto, "#,##0.00;#,##0.00-", ",", ".");
		
		System.out.println(mm);
	}

}
