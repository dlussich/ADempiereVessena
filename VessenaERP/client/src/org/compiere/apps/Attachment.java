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
package org.compiere.apps;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pdf.Document;
import org.adempiere.pdf.viewer.PDFViewerBean;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MSysConfig;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextArea;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.python.modules.synchronize;


import au.com.southsky.jfreesane.SaneDevice;
import au.com.southsky.jfreesane.SaneException;
import au.com.southsky.jfreesane.SaneOption;
import au.com.southsky.jfreesane.SaneSession;


//morenaimports
import SK.gnome.capabilities.sane.SaneActivity;
import SK.gnome.sane.*;
import SK.gnome.morena.Morena;

//
/**
 *  Attachment Viewer
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Attachment.java,v 1.4 2006/08/10 01:00:27 jjanke Exp $
 */
public final class Attachment extends CDialog
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2497487523050526742L;

	/**
	 *	Constructor.
	 *	loads Attachment, if ID <> 0
	 *  @param frame frame
	 *  @param WindowNo window no
	 *  @param AD_Attachment_ID attachment
	 *  @param AD_Table_ID table
	 *  @param Record_ID record key
	 *  @param trxName transaction
	 */
	public Attachment(Frame frame, int WindowNo, int AD_Attachment_ID,
		int AD_Table_ID, int Record_ID, String trxName)
	{
		super (frame, Msg.getMsg(Env.getCtx(), "Attachment"), true);
		//	needs to be modal otherwise APanel does not recongize change.
		log.config("ID=" + AD_Attachment_ID
			+ ", Table=" + AD_Table_ID + ", Record=" + Record_ID);
		//
		m_WindowNo = WindowNo;
		//
		try
		{
			staticInit();
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}
		//	Create Model
		if (AD_Attachment_ID == 0)
			m_attachment = new MAttachment (Env.getCtx(), AD_Table_ID, Record_ID, trxName);
		else
			m_attachment = new MAttachment (Env.getCtx(), AD_Attachment_ID, trxName);
		loadAttachments();
		//
		try
		{
			AEnv.showCenterWindow(frame, this);
		}
		catch (Exception e)
		{
		}
		text.requestFocus();
	}	//	Attachment

	/**	Window No				*/
	private int				m_WindowNo;
	/** Attachment				*/
	private MAttachment		m_attachment;
	/** Change					*/
	private boolean			m_change = false;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(Attachment.class);
	
	//
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CTextArea text = new CTextArea();
	private CButton bOpen = new CButton();
	private CButton bSave = new CButton();
	private CPanel northPanel = new CPanel();
	private CButton bLoad = new CButton();
	private BorderLayout northLayout = new BorderLayout();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private CPanel toolBar = new CPanel(new FlowLayout(FlowLayout.LEADING, 5,5));
	private CButton bDelete = new CButton();
	private CButton bDeleteAll = null;
	private CComboBox cbContent = new CComboBox();
	private JSplitPane centerPane = new JSplitPane();
	//
	private CPanel graphPanel = new CPanel(new BorderLayout());
	private GImage gifPanel = new GImage();
	private JScrollPane gifScroll = new JScrollPane (gifPanel);
	private PDFViewerBean pdfViewer = Document.getViewer();
	private CTextArea info = new CTextArea();
	
	
	//openUP scanner button
	private CButton bScann = new CButton();
	private CTextArea nombrArch = new CTextArea();
	//fin openup scanner button

	/**
	 *	Static setup.
	 *  <pre>
	 *  - northPanel
	 *      - toolBar
	 *      - title
	 *  - centerPane [split]
	 * 		- graphPanel (left)
	 *		  	- gifScroll - gifPanel
	 *			- pdfViewer
	 *  	- text (right)
	 *  - confirmPanel
	 *  </pre>
	 *  @throws Exception
	 */
	void staticInit() throws Exception
	{
		mainPanel.setLayout(mainLayout);
		mainLayout.setHgap(5);
		mainLayout.setVgap(5);
		this.getContentPane().add(mainPanel);
		
		northPanel.setLayout(northLayout);
		northPanel.add(toolBar, BorderLayout.CENTER);
		toolBar.add(bLoad);
		toolBar.add(bDelete);
		toolBar.add(bSave);
		toolBar.add(bOpen);
		//openUP scanner Button
		toolBar.add(bScann);
		//fin openUP scanner Button
		//openUP scanner Texto para nombre escaneo
		toolBar.add(nombrArch);
		//fin openUP scanner Texto para nombre escaneo
		toolBar.add(cbContent);
		
		mainPanel.add(northPanel, BorderLayout.NORTH);

		
		
		//
		bOpen.setEnabled(false);
		bOpen.setIcon(Env.getImageIcon("Editor24.gif"));
		bOpen.setMargin(new Insets(0, 2, 0, 2));
		bOpen.setToolTipText(Msg.getMsg(Env.getCtx(), "Open"));
		bOpen.addActionListener(this);
		//
		bSave.setEnabled(false);
		bSave.setIcon(Env.getImageIcon("Export24.gif"));
		bSave.setMargin(new Insets(0, 2, 0, 2));
		bSave.setToolTipText(Msg.getMsg(Env.getCtx(), "AttachmentSave"));
		bSave.addActionListener(this);
		//
		bLoad.setIcon(Env.getImageIcon("Import24.gif"));
		bLoad.setMargin(new Insets(0, 2, 0, 2));
		bLoad.setToolTipText(Msg.getMsg(Env.getCtx(), "Load"));
		bLoad.addActionListener(this);
		//
		//openUP scann button
		if(Env.getImageIcon("Scann24.gif")!=null){
			bScann.setIcon(Env.getImageIcon("Scann24.gif"));
		}else{
			bScann.setIcon(Env.getImageIcon("Copy24.gif"));
		}
		bScann.setMargin(new Insets(0, 2, 0, 2));
		bScann.setToolTipText(Msg.getMsg(Env.getCtx(), "Image Scanner"));
		bScann.addActionListener(this);
		
		//fin openUP scann button
		
		bDelete.setIcon(Env.getImageIcon("Delete24.gif"));
		bDelete.setMargin(new Insets(0, 2, 0, 2));
		bDelete.setToolTipText(Msg.getMsg(Env.getCtx(), "Delete"));
		bDelete.addActionListener(this);
		//
		Dimension size = cbContent.getPreferredSize();
		size.width = 200;
		cbContent.setPreferredSize(size);
		//openup
		nombrArch.setToolTipText(Msg.getMsg(Env.getCtx(), "Image Scanner Name"));
		nombrArch.setPreferredSize(size);
		//openup
	//	cbContent.setToolTipText(text);
		cbContent.addActionListener(this);
		cbContent.setLightWeightPopupEnabled(false);	//	Acrobat Panel is heavy
		//
		text.setBackground(AdempierePLAF.getInfoBackground());
		text.setPreferredSize(new Dimension(200, 200));
		//
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.addActionListener(this);
		bDeleteAll = ConfirmPanel.createDeleteButton(true);
		
		bDeleteAll.addActionListener(this);
		//
		info.setText("-");
		info.setReadWrite(false);
		
		graphPanel.add(info, BorderLayout.CENTER);
		//
		mainPanel.add(centerPane, BorderLayout.CENTER);
		centerPane.add(graphPanel, JSplitPane.LEFT);
		centerPane.add(text, JSplitPane.RIGHT);
		centerPane.setResizeWeight(.75);	//	more to graph
		
		if(MSysConfig.getValue("SCANNER_DEVICE_NAME",Env.getAD_Client_ID(Env.getCtx()))==null){
			bScann.hide();
			nombrArch.hide();
		}
	}	//	jbInit

	
	/**
	 * 	Dispose
	 */
	public void dispose ()
	{
		pdfViewer = null;
		super.dispose ();
	}	//	dispose
	
	/**
	 *	Load Attachments
	 */
	private void loadAttachments()
	{
		log.config("");
		//	Set Text/Description
		String sText = m_attachment.getTextMsg();
		if (sText == null)
			text.setText("");
		else
			text.setText(sText);

		//	Set Combo
		int size = m_attachment.getEntryCount();
		for (int i = 0; i < size; i++)
			cbContent.addItem(m_attachment.getEntryName(i));
		if (size > 0)
			cbContent.setSelectedIndex(0);
		else
			displayData(0);
	}	//	loadAttachment

	/**
	 *  Display gif or jpg in gifPanel
	 * 	@param index index
	 */
	private void displayData (int index)
	{
		MAttachmentEntry entry = m_attachment.getEntry(index); 
		log.config("Index=" + index + " - " + entry);
		
		//	Reset UI
		gifPanel.setImage(null);
		graphPanel.removeAll();
		//
		bDelete.setEnabled(false);
		bOpen.setEnabled(false);
		bSave.setEnabled(false);

		Dimension size = null;
		//	no attachment
		if (entry == null || entry.getData() == null)
		{
			info.setText("-");
		}
		else
		{
			bOpen.setEnabled(true);
			bSave.setEnabled(true);
			bDelete.setEnabled(true);
			log.config(entry.toStringX());
			//
			info.setText(entry.toStringX());
			if (entry.isPDF() && pdfViewer != null)
			{
				try
				{
					pdfViewer.loadPDF(entry.getInputStream());
					pdfViewer.setScale(50);
					size = pdfViewer.getPreferredSize();
				//	size.width = Math.min(size.width, 400);
				//	size.height = Math.min(size.height, 400);
					//
					graphPanel.add(pdfViewer, BorderLayout.CENTER);
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, "(pdf)", e);
				}
			}
			else if (entry.isGraphic())
			{
				//  Can we display it
				Image image = Toolkit.getDefaultToolkit().createImage(entry.getData());
				if (image != null)
				{
					gifPanel.setImage(image);
					size = gifPanel.getPreferredSize();
					if (size.width == -1 && size.height == -1)
					{
						log.log(Level.SEVERE, "Invalid Image");
					}
					else
					{
						//	size.width += 40;
						//	size.height += 40;
						graphPanel.add(gifScroll, BorderLayout.CENTER);
					}
				}
				else
					log.log(Level.SEVERE, "Could not create image");
			}
		}
		if (graphPanel.getComponentCount() == 0)
		{
			graphPanel.add(info, BorderLayout.CENTER);
		}

		log.config("Size=" + size);
	//	graphPanel.setPreferredSize(size);
	//	centerPane.setDividerLocation(size.width+30);
	//	size.width += 100;
	//	size.height += 100;
	//	centerPane.setPreferredSize(size);
		pack();
	}   //  displayData


	/**
	 * 	Get File Name with index
	 *	@param index index
	 *	@return file name or null
	 */
	private String getFileName (int index)
	{
		String fileName = null;
		if (cbContent.getItemCount() > index)
			fileName = (String)cbContent.getItemAt(index);
		return fileName;
	}	//	getFileName

	/**
	 *	Action Listener
	 *  @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
	//	log.config(e.getActionCommand());
		//	Save and Close
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			String newText = text.getText();
			if (newText == null)
				newText = "";
			String oldText = m_attachment.getTextMsg();
			if (oldText == null)
				oldText = "";
			if (!m_change)
				m_change = !newText.equals(oldText);
			if (newText.length() > 0 || m_attachment.getEntryCount() > 0)
			{
				if (m_change)
				{	
					m_attachment.setBinaryData(new byte[0]); // ATTENTION! HEAVY HACK HERE... Else it will not save :(
					m_attachment.setTextMsg(text.getText());
					m_attachment.save();
				}
			}
			else
				m_attachment.delete(true);
			dispose();
		}
		//	Cancel
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
		//	Delete Attachment
		else if (e.getSource() == bDeleteAll)
		{
			deleteAttachment();
			dispose();
		}
		//	Delete individual entry and Return
		else if (e.getSource() == bDelete)
			deleteAttachmentEntry();
		//	Show Data
		else if (e.getSource() == cbContent)
			displayData (cbContent.getSelectedIndex());
		//	Load Attachment
		else if (e.getSource() == bLoad)
			loadFile();
		//	Open Attachment
		else if (e.getSource() == bSave)
			saveAttachmentToFile();
		//	Open Attachment
		else if (e.getSource() == bOpen)
		{
			if (!openAttachment())
				saveAttachmentToFile();
		}
		//openUP scanner button
		else if (e.getSource() == bScann){
			procesarEscanneo();
			//System.out.println("Boton para escanner");
		}
		//fin openUP scanner button
	}	//	actionPerformed

	private void procesarEscannoMorena(){
//		Manager manager=Manager.getInstance();
//		List devices=Manager.listDevices();
//		Device device=devices.get(0);
//		if (device instanceof Scanner)  {
//		  // scanner specific code 
//		}
//		else if (device instanceof Camera) {
//		  // camera specific code 
//		}
//		
//		Scanner scanner = (Scanner) device;
//	    scanner.setMode(Scanner.RGB_8);
//	    scanner.setResolution(75);
//	    scanner.setFrame(100, 100, 500, 500);
//	    
//	    BufferedImage bimage = SynchronousHelper.scanImage(device);
//	    // make the necessary image processing or conversion
//	    File imageFile = SynchronousHelper.scanFile(device);
//	    // make the image processing or save the file in the repository
	}
	private void  procesarEscanneo() {
		SaneSession session=null;
		try {
			//String nameFile = text.getText().trim();
			String nameFile=nombrArch.getText().trim();
			//String nameFile = String.valueOf(cbContent.getSelectedItem());//.getItem().toSting();
			if(!nameFile.isEmpty()){
				//System.out.println(nameFile.toString());
				String  ipSaneServer = MSysConfig.getValue("SCANNER_DOMAINNAME_IP",Env.getAD_Client_ID(Env.getCtx())); //SCANNER_DOMAINNAME_IP
				String  portSaneServer = MSysConfig.getValue("SCANNER_DOMAINNAME_PORT",Env.getAD_Client_ID(Env.getCtx()));
				String  deviceName = MSysConfig.getValue("SCANNER_DEVICE_NAME",Env.getAD_Client_ID(Env.getCtx()));
				
				InetAddress ia = InetAddress.getByName(ipSaneServer);
				//InetAddress ia = InetAddress.getByName("192.168.150.129");
				if(ipSaneServer!=null && portSaneServer!=null && deviceName!=null){
					session = SaneSession.withRemoteSane(ia, Integer.valueOf(portSaneServer));
					//session = SaneSession.withRemoteSane(ia,6566);
					
					if(session!=null){				
						try {
							List<SaneDevice> devices = session.listDevices();
							SaneDevice otro = session.getDevice(deviceName);				
							//SaneDevice otro = session.getDevice("brother3:net1;dev0");
							otro.open();
							List<SaneOption>lst = otro.listOptions();
//							SaneOption option = otro.getOption("tl-x");
//							double actualValue = option.setFixedValue(97.5);
//							SaneOption option2 = otro.getOption("tl-y");
//							double actualValuer = option2.setFixedValue(97.5);
							BufferedImage imagea = otro.acquireImage();
							//File filea = new File("ejemplos.jpg");
							File filea = new File(nameFile+".jpg");
							ImageIO.write(imagea, "jpg", filea);
							guardarArchivo(filea);
							
						} catch (SaneException e) {
							e.printStackTrace();
						}		
					}else{
						text.setText("No se econtro esacnner");
					}
				}else{
					text.setText("No se ha parametrizado escanner");
				}
				
			}else{
				text.setText("Ingrese nombre para el documento");
				throw new AdempiereException("Ingrese nombre para el documento");
			}
			
		} catch(UnknownHostException e){
				e.printStackTrace();
		
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
	}


	private void guardarArchivo(File file) {
		// TODO Auto-generated method stub
		if(file!=null){
			int cnt = m_attachment.getEntryCount();
			
			//update
			boolean add = true;
			for (int i = 0; i < cnt; i++) 
			{
				if (m_attachment.getEntryName(i).equals(file.getName()))
				{
					if (m_attachment.updateEntry(i, file)) 
					{
						cbContent.setSelectedItem(file.getName());
						m_change = true;
					}
					add = false;
					break;
				}
			}

			if (add)
			{
				//new
				if (m_attachment.addEntry(file))
				{
					cbContent.addItem(file.getName());
					cbContent.setSelectedIndex(cbContent.getItemCount()-1);
					m_change = true;
				}
			}
		}
		
	}


	/**************************************************************************
	 *	Load file for attachment
	 */
	private void loadFile()
	{
		log.info("");
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle(Msg.getMsg(Env.getCtx(), "AttachmentNew"));
		chooser.setMultiSelectionEnabled(true);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;
		//
		File[] files = chooser.getSelectedFiles();
		for (File file : files) {
			int cnt = m_attachment.getEntryCount();
			
			//update
			boolean add = true;
			for (int i = 0; i < cnt; i++) 
			{
				if (m_attachment.getEntryName(i).equals(file.getName()))
				{
					if (m_attachment.updateEntry(i, file)) 
					{
						cbContent.setSelectedItem(file.getName());
						m_change = true;
					}
					add = false;
					break;
				}
			}

			if (add)
			{
				//new
				if (m_attachment.addEntry(file))
				{
					cbContent.addItem(file.getName());
					cbContent.setSelectedIndex(cbContent.getItemCount()-1);
					m_change = true;
				}
			}
		}
	}	//	getFileName

	/**
	 *	Delete entire Attachment
	 */
	private void deleteAttachment()
	{
		log.info("");
		if (ADialog.ask(m_WindowNo, this, "AttachmentDelete?"))
			m_attachment.delete(true);
	}	//	deleteAttachment

	/**
	 *	Delete Attachment Entry
	 */
	private void deleteAttachmentEntry()
	{
		log.info("");
		int index = cbContent.getSelectedIndex();
		String fileName = getFileName(index);
		if (fileName == null)
			return;
		//
		if (ADialog.ask(m_WindowNo, this, "AttachmentDeleteEntry?", fileName))
		{
			if (m_attachment.deleteEntry(index))
				cbContent.removeItemAt(index);
			m_change = true;
		}
	}	//	deleteAttachment


	/**
	 *	Save Attachment to File
	 */
	private void saveAttachmentToFile()
	{
		int index = cbContent.getSelectedIndex();
		log.info("index=" + index);
		if (m_attachment.getEntryCount() < index)
			return;

		String fileName = getFileName(index);
		String ext = fileName.substring (fileName.lastIndexOf('.'));
		log.config( "Ext=" + ext);

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle(Msg.getMsg(Env.getCtx(), "AttachmentSave"));
		File f = new File(fileName);
		chooser.setSelectedFile(f);
		//	Show dialog
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;
		File saveFile = chooser.getSelectedFile();
		if (saveFile == null)
			return;

		log.config("Save to " + saveFile.getAbsolutePath());
		m_attachment.getEntryFile(index, saveFile);
	}	//	saveAttachmentToFile

	/**
	 *	Open the temporary file with the application associated with the extension in the file name
	 *	@return true if file was opened with third party application
	 */
	private boolean openAttachment ()
    {
        int index = cbContent.getSelectedIndex();
        byte[] data = m_attachment.getEntryData(index);
        if (data == null)
            return false;
        
        try 
        {
            String fileName = System.getProperty("java.io.tmpdir") +
					System.getProperty("file.separator") +
					m_attachment.getEntryName(index);
            File tempFile = new File(fileName);
            m_attachment.getEntryFile(index, tempFile);
        
            if (Env.isWindows())
            {
            //	Runtime.getRuntime().exec ("rundll32 url.dll,FileProtocolHandler " + url);
                Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL \"" + tempFile + "\"");
            //	p.waitFor();
                return true;
            }
            else if (Env.isMac())
            {
            	String [] cmdArray = new String [] {"open", tempFile.getAbsolutePath()};
            	Process p = Runtime.getRuntime ().exec (cmdArray);
            //	p.waitFor();
                return true;
            }
            else	//	other OS. originally nothing here. add the following codes
            {
            	try {
            		Desktop desktop = null;
            		if (Desktop.isDesktopSupported()) {
            			desktop = Desktop.getDesktop();
            			File file = new File(tempFile.getAbsolutePath());
            			desktop.open(file);
            			return true;
            		}               
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
            }
        } 
        catch (Exception e) 
        {
        	log.log(Level.SEVERE, "", e);
        }
        return false;
    }    //    openFile

	
	/**************************************************************************
	 *  Graphic Image Panel
	 */
	class GImage extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4991225210651641722L;

		/**
		 *  Graphic Image
		 */
		public GImage()
		{
			super();
		}   //  GImage

		/** The Image           */
		private Image 			m_image = null;

		/**
		 *  Set Image
		 *  @param image image
		 */
		public void setImage (Image image)
		{
			m_image = image;
			if (m_image == null)
				return;

			MediaTracker mt = new MediaTracker(this);
			mt.addImage(m_image, 0);
			try {
				mt.waitForID(0);
			} catch (Exception e) {}
			Dimension dim = new Dimension(m_image.getWidth(this), m_image.getHeight(this));
			this.setPreferredSize(dim);
		}   //  setImage

		/**
		 *  Paint
		 *  @param g graphics
		 */
		public void paint (Graphics g)
		{
			Insets in = getInsets();
			if (m_image != null)
				g.drawImage(m_image, in.left, in.top, this);
		}   //  paint

		/**
		 *  Update
		 *  @param g graphics
		 */
		public void update (Graphics g)
		{
			paint(g);
		}   //  update
	}	//	GImage

	
	/**
	 * 	Test PDF Viewer
	 *	@param args ignored
	 */
	public static void main (String[] args)
	{
	}	//	main
	
}	//	Attachment
