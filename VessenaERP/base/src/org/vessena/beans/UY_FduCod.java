package org.openup.beans;

public class UY_FduCod {
	
	private int uy_fducod_id;	
	private String value;	
	private int c_elementvalue_id;
	private int c_elementvalue_id_cr;
	private int c_currency_id;
	private String curvalue;
	private String grupccvalue;
	private int grupocc_id;	
	private String sucvalue;
	private int ccdepartamento;
	private String prodvalue;
	private int ccproducto;
	private String affvalue;
	private int ccafinidad;
		
	
	public UY_FduCod(int uy_fducod_id, String value, int c_elementvalue_id, int c_elementvalue_id_cr, int c_currency_di, String curvalue,
			         String grupccvalue, int grupocc_id, String sucvalue, int ccdepartamento, String prodvalue, int ccproducto, String affvalue, int ccafinidad) {
		super();
		this.uy_fducod_id = uy_fducod_id;
		this.value = value;
		this.c_elementvalue_id = c_elementvalue_id;
		this.c_elementvalue_id_cr = c_elementvalue_id_cr;
		this.c_currency_id = c_currency_di;
		this.curvalue = curvalue;
		this.grupccvalue = grupccvalue;
		this.grupocc_id = grupocc_id;
		this.sucvalue = sucvalue;
		this.ccdepartamento = ccdepartamento;
		this.prodvalue = prodvalue;
		this.ccproducto = ccproducto;
		this.affvalue = affvalue;
		this.ccafinidad = ccafinidad;
		
		
	}
	
	public UY_FduCod(int uy_fducod_id, String value, int c_elementvalue_id, int c_elementvalue_id_cr, int c_currency_di, String curvalue,
	         String sucvalue, int ccdepartamento, String prodvalue, int ccproducto, String affvalue, int ccafinidad) {
			super();
			this.uy_fducod_id = uy_fducod_id;
			this.value = value;
			this.c_elementvalue_id = c_elementvalue_id;
			this.c_elementvalue_id_cr = c_elementvalue_id_cr;
			this.c_currency_id = c_currency_di;
			this.curvalue = curvalue;
			this.sucvalue = sucvalue;
			this.ccdepartamento = ccdepartamento;
			this.prodvalue = prodvalue;
			this.ccproducto = ccproducto;
			this.affvalue = affvalue;
			this.ccafinidad = ccafinidad;


}
	
	public int getUy_fducod_id() {
		return uy_fducod_id;
	}
	
	public int getGrupocc_id() {
		return grupocc_id;
	}
	
	public String getValue() {
		return value;
	}
	public int getC_elementvalue_id() {
		return c_elementvalue_id;
	}
	public int getC_elementvalue_id_cr() {
		return c_elementvalue_id_cr;
	}
	public int getC_currency_id() {
		return c_currency_id;
	}
	public String getCurvalue() {
		return curvalue;
	}
	public String getGrupccvalue() {
		return grupccvalue;
	}
	public String getSucvalue() {
		return sucvalue;
	}
	public int getCcdepartamento() {
		return ccdepartamento;
	}
	public String getProdvalue() {
		return prodvalue;
	}
	public int getCcproducto() {
		return ccproducto;
	}
	public String getAffvalue() {
		return affvalue;
	}
	public int getCcafinidad() {
		return ccafinidad;
	}
	public int getUy_fdu_grupocc_id() {
		return uy_fdu_grupocc_id;
	}
	private int uy_fdu_grupocc_id;
	

}
