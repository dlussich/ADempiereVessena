/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.X_M_PromotionDistribution;

/**
 *
 * @author hengsin
 *
 */
public class MPromotionDistribution extends X_M_PromotionDistribution {

	private static final long serialVersionUID = 1532619121257280486L;

	public MPromotionDistribution(Properties ctx,
			int M_PromotionDistribution_ID, String trxName) {
		super(ctx, M_PromotionDistribution_ID, trxName);
	}

	public MPromotionDistribution(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * OpenUp.	
	 * Descripcion : Valida que esta distribution sea valida para la cantidad recibida.
	 * @param qty
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 20/12/2010
	 */
	public boolean validQty(BigDecimal qty){
		
		boolean valid = false;
		
		// Si la operacion de esta distribution es MAYOR/IGUAL
		if (this.getOperation().equalsIgnoreCase(">=")){
			// Si la cantidad de la distribution es mayor/igual que la cantidad recibida, 
			// esta distribution es entonces valida
			if (this.getQty().compareTo(qty)<=0) valid = true;
		}
		else if (this.getOperation().equalsIgnoreCase("<=")){
			// Si la cantidad de la distribution es menor/igual que la cantidad recibida, 
			// esta distribution es entonces valida
			if (this.getQty().compareTo(qty)>=0) valid = true;
		}	
		return valid;
	}
	
}
