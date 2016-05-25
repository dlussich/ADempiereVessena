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

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MInvoiceLine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_M_PromotionGroup;

/**
 *
 * @author hengsin
 *
 */
public class MPromotionGroup extends X_M_PromotionGroup {

	private static final long serialVersionUID = 4203915332775348579L;
	private MPromotionGroupLine[] m_lines;

	public MPromotionGroup(Properties ctx, int M_PromotionGroup_ID,
			String trxName) {
		super(ctx, M_PromotionGroup_ID, trxName);
	}

	public MPromotionGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/**
	 * 	Copy Lines From other PromotionGroup.
	 *	@param from other promotion group to copy form
	 *	@return number of lines copied
	 */
	public int copyLinesFrom(MPromotionGroup from) {

		MPromotionGroupLine[] fromLines = from.getLines();
		
		int count=0;
		for (int i = 0; i < fromLines.length; i++) {

			MPromotionGroupLine line = new MPromotionGroupLine (getCtx(), 0, get_TrxName());
			MPromotionGroupLine fromLine = fromLines[i];
			
			PO.copyValues (fromLine, line, fromLine.getAD_Client_ID(), fromLine.getAD_Org_ID());
			line.setM_PromotionGroup_ID(getM_PromotionGroup_ID());
			//line.set_ValueNoCheck("M_PromotionGroupLine_ID", I_ZERO);
			
			if (line.save(get_TrxName()))
				count++;
		}		
		return count;
	}

	/**
	 * 	Get Promotion Group Lines
	 * 	@param whereClause starting with AND
	 * 	@return lines
	 */
	private MPromotionGroupLine[] getLines (String whereClause)
	{
		String whereClauseFinal = "M_PromotionGroup_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MInvoiceLine> list = new Query(getCtx(), MPromotionGroupLine.Table_Name, whereClauseFinal, get_TrxName())
										.setParameters(new Object[]{getM_PromotionGroup_ID()})
										.list();
		return list.toArray(new MPromotionGroupLine[list.size()]);
	}	//	getLines

	/**
	 * 	Get  Promotion Group Lines
	 * 	@param requery
	 * 	@return lines
	 */
	public MPromotionGroupLine[] getLines (boolean requery)
	{
		if (m_lines == null || m_lines.length == 0 || requery)
			m_lines = getLines(null);
		set_TrxName(m_lines, get_TrxName());
		return m_lines;
	}	//	getLines

	/**
	 * 	Get Promotion Group Lines
	 * 	@return lines
	 */
	public MPromotionGroupLine[] getLines()
	{
		return getLines(false);
	}	//	getLines
	
}
