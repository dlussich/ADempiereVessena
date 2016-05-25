/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalLine;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;

/**
 *  Post GL Journal Documents.
 *  <pre>
 *  Table:              GL_Journal (224)
 *  Document Types:     GLJ
 *  </pre>
 *  @author Jorg Janke
 *  @version  $Id: Doc_GLJournal.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_GLJournal extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_GLJournal (MAcctSchema[] ass, ResultSet rs, String trxName)
	{
		super(ass, MJournal.class, rs, null, trxName);
	}	//	Doc_GL_Journal

	/** Posting Type				*/
	private String			m_PostingType = null;
	private int				m_C_AcctSchema_ID = 0;
	
	/**
	 *  Load Specific Document Details
	 *  @return error message or null
	 */
	protected String loadDocumentDetails ()
	{
		MJournal journal = (MJournal)getPO();
		m_PostingType = journal.getPostingType();
		m_C_AcctSchema_ID = journal.getC_AcctSchema_ID();
			
		//	Contained Objects
		p_lines = loadLines(journal);
		log.fine("Lines=" + p_lines.length);
		return null;
	}   //  loadDocumentDetails


	/**
	 *	Load Invoice Line
	 *	@param journal journal
	 *  @return DocLine Array
	 */
	private DocLine[] loadLines(MJournal journal)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MJournalLine[] lines = journal.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MJournalLine line = lines[i];
			DocLine docLine = new DocLine (line, this); 
			//  --  Source Amounts
			docLine.setAmount (line.getAmtSourceDr(), line.getAmtSourceCr());
			//  --  Converted Amounts
			docLine.setConvertedAmt (m_C_AcctSchema_ID, line.getAmtAcctDr(), line.getAmtAcctCr());
			//  --  Account
			MAccount account = line.getAccount();
			docLine.setAccount (account);
			//	--	Organization of Line was set to Org of Account
			
			// OpenUp. Gabriel Vila. 20/11/2012. Issue #29
			// Me aseguro que vaya bien la fecha de contabilizacion
			docLine.setDateAcct(journal.getDateAcct());
			docLine.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(), docLine.getDateAcct(), 0));
			// Fin OpenUp.
			
			list.add(docLine);
			
		}
		//	Return Array
		int size = list.size();
		DocLine[] dls = new DocLine[size];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	
	/**************************************************************************
	 *  Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *  @return positive amount, if total invoice is bigger than lines
	 */
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		
		// OpenUp. Gabriel Vila. 15/11/2012. Issue #29.
		// Comento porque para asientos tengo un redondeo posible en el createfact
		
		/*
		StringBuffer sb = new StringBuffer (" [");
		//  Lines
		for (int i = 0; i < p_lines.length; i++)
		{
			// OpenUp. Gabriel Vila. 07/06/2011. Issue #714
			// Comento y cambio lineas para obtener balanceo en moneda del esquema contable.

			//retValue = retValue.add(p_lines[i].getAmtSource());
			//sb.append("+").append(p_lines[i].getAmtSource());

			retValue = retValue.add(p_lines[i].getAmtAcct());
			sb.append("+").append(p_lines[i].getAmtAcct());
			// Fin OpenUp.
		}
		sb.append("]");
		//
		log.fine(toString() + " Balance=" + retValue + sb.toString());
		
		*/

		// Fin OpenUp 
		
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  GLJ.
	 *  (only for the accounting scheme, it was created)
	 *  <pre>
	 *      account     DR          CR
	 *  </pre>
	 *  @param as acct schema
	 *  @return Fact
	 */
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		ArrayList<Fact> facts = new ArrayList<Fact>();
		//	Other Acct Schema
		if (as.getC_AcctSchema_ID() != m_C_AcctSchema_ID)
			return facts;
		
		// OpenUp. Gabriel Vila. 07/06/2011. Issue #714
		// Seteo moneda multicurrency ya que puede darse esta situacion.
		this.setIsMultiCurrency(true);
		// Fin OpenUp.
		
		MJournal journal = (MJournal)this.p_po; // OpenUp. Gabriel Vila. 02/03/2013. Issue #447.
		
		//  create Fact Header
		Fact fact = new Fact (this, as, m_PostingType);

		//  GLJ
		if (getDocumentType().equals(DOCTYPE_GLJournal))
		{
			//  account     DR      CR
			for (int i = 0; i < p_lines.length; i++)
			{
				if (p_lines[i].getC_AcctSchema_ID () == as.getC_AcctSchema_ID ())
				{
					
					// OpenUp. Gabriel Vila. 07/06/2011. Issue #714
					// Comento y hago asiento poniendo la moneda original de la linea y no la del esquema
					
					/*FactLine line = fact.createLine (p_lines[i],
									p_lines[i].getAccount (),
									getC_Currency_ID(),
									p_lines[i].getAmtSourceDr (),
									p_lines[i].getAmtSourceCr ());*/
					
					FactLine line = fact.createLine (p_lines[i],
							p_lines[i].getAccount (),
							p_lines[i].getC_Currency_ID(),
							p_lines[i].getAmtSourceDr (),
							p_lines[i].getAmtSourceCr (), journal.getC_DocType_ID(), journal.getDocumentNo());
					// Fin OpenUp
					
					// OpenUp. Gabriel Vila. 17/09/2012. Issue #32.
					// Contabilizar asiento diario con 3 categorias de centros de costo.
					MJournalLine journalLine = (MJournalLine)p_lines[i].p_po;
					if (line != null){
						if (journalLine.get_ID() > 0){
							if (journalLine.getC_Activity_ID() > 0)
								line.setC_Activity_ID(journalLine.getC_Activity_ID());
							if (journalLine.getC_Activity_ID_1() > 0)
								line.setC_Activity_ID_1(journalLine.getC_Activity_ID_1());
							if (journalLine.getC_Activity_ID_2() > 0)
								line.setC_Activity_ID_2(journalLine.getC_Activity_ID_2());
							if (journalLine.getC_Activity_ID_3() > 0)
								line.setC_Activity_ID_3(journalLine.getC_Activity_ID_3());
							
							line.saveEx();
						}
					}
					// Fin OpenUp Issue #32.
				}
			}	//	for all lines
			
			// Redondeo.
			fact.createRounding(journal.getC_DocType_ID(), journal.getDocumentNo(), 0, 0, 0, 0);
		}
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}
		//
		facts.add(fact);
		return facts;
	}   //  createFact

}   //  Doc_GLJournal
