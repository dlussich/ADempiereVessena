/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_R_Inbox
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_R_Inbox extends PO implements I_UY_R_Inbox, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140310L;

    /** Standard Constructor */
    public X_UY_R_Inbox (Properties ctx, int UY_R_Inbox_ID, String trxName)
    {
      super (ctx, UY_R_Inbox_ID, trxName);
      /** if (UY_R_Inbox_ID == 0)
        {
			setAD_User_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_R_Inbox_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_R_Inbox (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UY_R_Inbox[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AssignTo_ID.
		@param AssignTo_ID AssignTo_ID	  */
	public void setAssignTo_ID (int AssignTo_ID)
	{
		if (AssignTo_ID < 1) 
			set_Value (COLUMNNAME_AssignTo_ID, null);
		else 
			set_Value (COLUMNNAME_AssignTo_ID, Integer.valueOf(AssignTo_ID));
	}

	/** Get AssignTo_ID.
		@return AssignTo_ID	  */
	public int getAssignTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AssignTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CriticalImage_ID.
		@param CriticalImage_ID CriticalImage_ID	  */
	public void setCriticalImage_ID (int CriticalImage_ID)
	{
		if (CriticalImage_ID < 1) 
			set_Value (COLUMNNAME_CriticalImage_ID, null);
		else 
			set_Value (COLUMNNAME_CriticalImage_ID, Integer.valueOf(CriticalImage_ID));
	}

	/** Get CriticalImage_ID.
		@return CriticalImage_ID	  */
	public int getCriticalImage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CriticalImage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CriticalSeqNo.
		@param CriticalSeqNo CriticalSeqNo	  */
	public void setCriticalSeqNo (int CriticalSeqNo)
	{
		set_Value (COLUMNNAME_CriticalSeqNo, Integer.valueOf(CriticalSeqNo));
	}

	/** Get CriticalSeqNo.
		@return CriticalSeqNo	  */
	public int getCriticalSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CriticalSeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateAssign.
		@param DateAssign 
		DateAssign
	  */
	public void setDateAssign (Timestamp DateAssign)
	{
		set_Value (COLUMNNAME_DateAssign, DateAssign);
	}

	/** Get DateAssign.
		@return DateAssign
	  */
	public Timestamp getDateAssign () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAssign);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set MediumDueDate.
		@param MediumDueDate MediumDueDate	  */
	public void setMediumDueDate (Timestamp MediumDueDate)
	{
		set_Value (COLUMNNAME_MediumDueDate, MediumDueDate);
	}

	/** Get MediumDueDate.
		@return MediumDueDate	  */
	public Timestamp getMediumDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MediumDueDate);
	}

	/** Set MediumTerm.
		@param MediumTerm MediumTerm	  */
	public void setMediumTerm (int MediumTerm)
	{
		set_Value (COLUMNNAME_MediumTerm, Integer.valueOf(MediumTerm));
	}

	/** Get MediumTerm.
		@return MediumTerm	  */
	public int getMediumTerm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MediumTerm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ModoVista AD_Reference_ID=1000311 */
	public static final int MODOVISTA_AD_Reference_ID=1000311;
	/** Asignado a Mi = 0-ASIGNADO */
	public static final String MODOVISTA_AsignadoAMi = "0-ASIGNADO";
	/** Canal de Notificacion = 2-CANAL */
	public static final String MODOVISTA_CanalDeNotificacion = "2-CANAL";
	/** Observador = 4-OBSERVA */
	public static final String MODOVISTA_Observador = "4-OBSERVA";
	/** Punto de Resolucion = 1-PTORESOL */
	public static final String MODOVISTA_PuntoDeResolucion = "1-PTORESOL";
	/** Creado por Mi = 3-CREADOR */
	public static final String MODOVISTA_CreadoPorMi = "3-CREADOR";
	/** Set ModoVista.
		@param ModoVista ModoVista	  */
	public void setModoVista (String ModoVista)
	{

		throw new IllegalArgumentException ("ModoVista is virtual column");	}

	/** Get ModoVista.
		@return ModoVista	  */
	public String getModoVista () 
	{
		return (String)get_Value(COLUMNNAME_ModoVista);
	}

	/** Priority AD_Reference_ID=154 */
	public static final int PRIORITY_AD_Reference_ID=154;
	/** Medium = 3 */
	public static final String PRIORITY_Medium = "3";
	/** Urgent = 1 */
	public static final String PRIORITY_Urgent = "1";
	/** Minor = 5 */
	public static final String PRIORITY_Minor = "5";
	/** Low = 4 */
	public static final String PRIORITY_Low = "4";
	/** High = 2 */
	public static final String PRIORITY_High = "2";
	/** Inmediate = 0 */
	public static final String PRIORITY_Inmediate = "0";
	/** Set Priority.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (String Priority)
	{

		set_Value (COLUMNNAME_Priority, Priority);
	}

	/** Get Priority.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	public String getPriority () 
	{
		return (String)get_Value(COLUMNNAME_Priority);
	}

	/** PriorityManual AD_Reference_ID=154 */
	public static final int PRIORITYMANUAL_AD_Reference_ID=154;
	/** Medium = 3 */
	public static final String PRIORITYMANUAL_Medium = "3";
	/** Urgent = 1 */
	public static final String PRIORITYMANUAL_Urgent = "1";
	/** Minor = 5 */
	public static final String PRIORITYMANUAL_Minor = "5";
	/** Low = 4 */
	public static final String PRIORITYMANUAL_Low = "4";
	/** High = 2 */
	public static final String PRIORITYMANUAL_High = "2";
	/** Inmediate = 0 */
	public static final String PRIORITYMANUAL_Inmediate = "0";
	/** Set PriorityManual.
		@param PriorityManual PriorityManual	  */
	public void setPriorityManual (String PriorityManual)
	{

		set_Value (COLUMNNAME_PriorityManual, PriorityManual);
	}

	/** Get PriorityManual.
		@return PriorityManual	  */
	public String getPriorityManual () 
	{
		return (String)get_Value(COLUMNNAME_PriorityManual);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reference No.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** StatusReclamo AD_Reference_ID=1000260 */
	public static final int STATUSRECLAMO_AD_Reference_ID=1000260;
	/** Pendiente = PENDIENTE */
	public static final String STATUSRECLAMO_Pendiente = "PENDIENTE";
	/** Vencido = VENCIDO */
	public static final String STATUSRECLAMO_Vencido = "VENCIDO";
	/** Cerrado = CERRADO */
	public static final String STATUSRECLAMO_Cerrado = "CERRADO";
	/** En Curso = CURSO */
	public static final String STATUSRECLAMO_EnCurso = "CURSO";
	/** Resuelto = RESU */
	public static final String STATUSRECLAMO_Resuelto = "RESU";
	/** Nueva Incidencia = NUEVO */
	public static final String STATUSRECLAMO_NuevaIncidencia = "NUEVO";
	/** Pendiente de Gestion = GESTPEND */
	public static final String STATUSRECLAMO_PendienteDeGestion = "GESTPEND";
	/** En Gestion = GESTION */
	public static final String STATUSRECLAMO_EnGestion = "GESTION";
	/** Pendiente de Notificacion = NOTIFPEND */
	public static final String STATUSRECLAMO_PendienteDeNotificacion = "NOTIFPEND";
	/** En Notificacion = NOTIFICACION */
	public static final String STATUSRECLAMO_EnNotificacion = "NOTIFICACION";
	/** Pendiente de Canal Escrito = ESCPEND */
	public static final String STATUSRECLAMO_PendienteDeCanalEscrito = "ESCPEND";
	/** Pendiente Aprobacion = PENDAPROBACION */
	public static final String STATUSRECLAMO_PendienteAprobacion = "PENDAPROBACION";
	/** Pendiente Aprobacion Nivel 1 = PENDAPROBACION_1 */
	public static final String STATUSRECLAMO_PendienteAprobacionNivel1 = "PENDAPROBACION_1";
	/** Pendiente Aprobacion Nivel 2 = PENDAPROBACION_2 */
	public static final String STATUSRECLAMO_PendienteAprobacionNivel2 = "PENDAPROBACION_2";
	/** Aprobado = APROBADO */
	public static final String STATUSRECLAMO_Aprobado = "APROBADO";
	/** Aprobado Nivel 1 = APROBADO_1 */
	public static final String STATUSRECLAMO_AprobadoNivel1 = "APROBADO_1";
	/** Aprobado Nivel 2 = APROBADO_2 */
	public static final String STATUSRECLAMO_AprobadoNivel2 = "APROBADO_2";
	/** No Aprobado = NOAPROBADO */
	public static final String STATUSRECLAMO_NoAprobado = "NOAPROBADO";
	/** No Aprobado Nivel 1 = NOAPROBADO_1 */
	public static final String STATUSRECLAMO_NoAprobadoNivel1 = "NOAPROBADO_1";
	/** No Aprobado Nivel 2 = NOAPROBADO_2 */
	public static final String STATUSRECLAMO_NoAprobadoNivel2 = "NOAPROBADO_2";
	/** Pendiente Aprobacion Category = PENDAPROBACIONCAT */
	public static final String STATUSRECLAMO_PendienteAprobacionCategory = "PENDAPROBACIONCAT";
	/** Aprobado Categoria = APROBADO_CAT */
	public static final String STATUSRECLAMO_AprobadoCategoria = "APROBADO_CAT";
	/** No Aprobado Categoria = NOAPROBADO_CAT */
	public static final String STATUSRECLAMO_NoAprobadoCategoria = "NOAPROBADO_CAT";
	/** Pendiente Aprobacion Stock = PENDAPROBACION_STK */
	public static final String STATUSRECLAMO_PendienteAprobacionStock = "PENDAPROBACION_STK";
	/** No Aprobado por Stock = NOAPROBADO_STK */
	public static final String STATUSRECLAMO_NoAprobadoPorStock = "NOAPROBADO_STK";
	/** Set StatusReclamo.
		@param StatusReclamo StatusReclamo	  */
	public void setStatusReclamo (String StatusReclamo)
	{

		set_Value (COLUMNNAME_StatusReclamo, StatusReclamo);
	}

	/** Get StatusReclamo.
		@return StatusReclamo	  */
	public String getStatusReclamo () 
	{
		return (String)get_Value(COLUMNNAME_StatusReclamo);
	}

	/** StatusTarea AD_Reference_ID=1000278 */
	public static final int STATUSTAREA_AD_Reference_ID=1000278;
	/** No Asignada = NOA */
	public static final String STATUSTAREA_NoAsignada = "NOA";
	/** En Curso = CUR */
	public static final String STATUSTAREA_EnCurso = "CUR";
	/** Resuelta = RES */
	public static final String STATUSTAREA_Resuelta = "RES";
	/** Cerrada = CLO */
	public static final String STATUSTAREA_Cerrada = "CLO";
	/** Set StatusTarea.
		@param StatusTarea StatusTarea	  */
	public void setStatusTarea (String StatusTarea)
	{

		set_Value (COLUMNNAME_StatusTarea, StatusTarea);
	}

	/** Get StatusTarea.
		@return StatusTarea	  */
	public String getStatusTarea () 
	{
		return (String)get_Value(COLUMNNAME_StatusTarea);
	}

	/** Set TrackImage_ID.
		@param TrackImage_ID TrackImage_ID	  */
	public void setTrackImage_ID (int TrackImage_ID)
	{
		if (TrackImage_ID < 1) 
			set_Value (COLUMNNAME_TrackImage_ID, null);
		else 
			set_Value (COLUMNNAME_TrackImage_ID, Integer.valueOf(TrackImage_ID));
	}

	/** Get TrackImage_ID.
		@return TrackImage_ID	  */
	public int getTrackImage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TrackImage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Canal getUY_R_Canal() throws RuntimeException
    {
		return (I_UY_R_Canal)MTable.get(getCtx(), I_UY_R_Canal.Table_Name)
			.getPO(getUY_R_Canal_ID(), get_TrxName());	}

	/** Set UY_R_Canal.
		@param UY_R_Canal_ID UY_R_Canal	  */
	public void setUY_R_Canal_ID (int UY_R_Canal_ID)
	{
		if (UY_R_Canal_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Canal_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Canal_ID, Integer.valueOf(UY_R_Canal_ID));
	}

	/** Get UY_R_Canal.
		@return UY_R_Canal	  */
	public int getUY_R_Canal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Canal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Cause getUY_R_Cause() throws RuntimeException
    {
		return (I_UY_R_Cause)MTable.get(getCtx(), I_UY_R_Cause.Table_Name)
			.getPO(getUY_R_Cause_ID(), get_TrxName());	}

	/** Set UY_R_Cause.
		@param UY_R_Cause_ID UY_R_Cause	  */
	public void setUY_R_Cause_ID (int UY_R_Cause_ID)
	{
		if (UY_R_Cause_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Cause_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Cause_ID, Integer.valueOf(UY_R_Cause_ID));
	}

	/** Get UY_R_Cause.
		@return UY_R_Cause	  */
	public int getUY_R_Cause_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Cause_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_R_Inbox.
		@param UY_R_Inbox_ID UY_R_Inbox	  */
	public void setUY_R_Inbox_ID (int UY_R_Inbox_ID)
	{
		if (UY_R_Inbox_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_R_Inbox_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_R_Inbox_ID, Integer.valueOf(UY_R_Inbox_ID));
	}

	/** Get UY_R_Inbox.
		@return UY_R_Inbox	  */
	public int getUY_R_Inbox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Inbox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_PtoResolucion getUY_R_PtoResolucion() throws RuntimeException
    {
		return (I_UY_R_PtoResolucion)MTable.get(getCtx(), I_UY_R_PtoResolucion.Table_Name)
			.getPO(getUY_R_PtoResolucion_ID(), get_TrxName());	}

	/** Set UY_R_PtoResolucion.
		@param UY_R_PtoResolucion_ID UY_R_PtoResolucion	  */
	public void setUY_R_PtoResolucion_ID (int UY_R_PtoResolucion_ID)
	{
		if (UY_R_PtoResolucion_ID < 1) 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_PtoResolucion_ID, Integer.valueOf(UY_R_PtoResolucion_ID));
	}

	/** Get UY_R_PtoResolucion.
		@return UY_R_PtoResolucion	  */
	public int getUY_R_PtoResolucion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_PtoResolucion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException
    {
		return (I_UY_R_Reclamo)MTable.get(getCtx(), I_UY_R_Reclamo.Table_Name)
			.getPO(getUY_R_Reclamo_ID(), get_TrxName());	}

	/** Set UY_R_Reclamo.
		@param UY_R_Reclamo_ID UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID)
	{
		if (UY_R_Reclamo_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, Integer.valueOf(UY_R_Reclamo_ID));
	}

	/** Get UY_R_Reclamo.
		@return UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_r_subcause_id_1.
		@param uy_r_subcause_id_1 uy_r_subcause_id_1	  */
	public void setuy_r_subcause_id_1 (int uy_r_subcause_id_1)
	{
		set_Value (COLUMNNAME_uy_r_subcause_id_1, Integer.valueOf(uy_r_subcause_id_1));
	}

	/** Get uy_r_subcause_id_1.
		@return uy_r_subcause_id_1	  */
	public int getuy_r_subcause_id_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_r_subcause_id_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_r_subcause_id_2.
		@param uy_r_subcause_id_2 uy_r_subcause_id_2	  */
	public void setuy_r_subcause_id_2 (int uy_r_subcause_id_2)
	{
		set_Value (COLUMNNAME_uy_r_subcause_id_2, Integer.valueOf(uy_r_subcause_id_2));
	}

	/** Get uy_r_subcause_id_2.
		@return uy_r_subcause_id_2	  */
	public int getuy_r_subcause_id_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_r_subcause_id_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_r_subcause_id_3.
		@param uy_r_subcause_id_3 uy_r_subcause_id_3	  */
	public void setuy_r_subcause_id_3 (int uy_r_subcause_id_3)
	{
		set_Value (COLUMNNAME_uy_r_subcause_id_3, Integer.valueOf(uy_r_subcause_id_3));
	}

	/** Get uy_r_subcause_id_3.
		@return uy_r_subcause_id_3	  */
	public int getuy_r_subcause_id_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_r_subcause_id_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_r_subcause_id_4.
		@param uy_r_subcause_id_4 uy_r_subcause_id_4	  */
	public void setuy_r_subcause_id_4 (int uy_r_subcause_id_4)
	{
		set_Value (COLUMNNAME_uy_r_subcause_id_4, Integer.valueOf(uy_r_subcause_id_4));
	}

	/** Get uy_r_subcause_id_4.
		@return uy_r_subcause_id_4	  */
	public int getuy_r_subcause_id_4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_r_subcause_id_4);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Tarea getUY_R_Tarea() throws RuntimeException
    {
		return (I_UY_R_Tarea)MTable.get(getCtx(), I_UY_R_Tarea.Table_Name)
			.getPO(getUY_R_Tarea_ID(), get_TrxName());	}

	/** Set UY_R_Tarea.
		@param UY_R_Tarea_ID UY_R_Tarea	  */
	public void setUY_R_Tarea_ID (int UY_R_Tarea_ID)
	{
		if (UY_R_Tarea_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Tarea_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Tarea_ID, Integer.valueOf(UY_R_Tarea_ID));
	}

	/** Get UY_R_Tarea.
		@return UY_R_Tarea	  */
	public int getUY_R_Tarea_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Tarea_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_R_Type getUY_R_Type() throws RuntimeException
    {
		return (I_UY_R_Type)MTable.get(getCtx(), I_UY_R_Type.Table_Name)
			.getPO(getUY_R_Type_ID(), get_TrxName());	}

	/** Set UY_R_Type.
		@param UY_R_Type_ID UY_R_Type	  */
	public void setUY_R_Type_ID (int UY_R_Type_ID)
	{
		if (UY_R_Type_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Type_ID, Integer.valueOf(UY_R_Type_ID));
	}

	/** Get UY_R_Type.
		@return UY_R_Type	  */
	public int getUY_R_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}