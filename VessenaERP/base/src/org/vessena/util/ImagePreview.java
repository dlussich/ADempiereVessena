package org.openup.util;

import javax.swing.*;
import java.beans.*;
import java.awt.*;
import java.io.File;


/**
 * OpenUp. Guillermo Brust. 30/05/2013
 * Clase auxiliar utilizada para la generacion de la vista previa en la carga de imagenes por un file dialog.
 * 
 */


public class ImagePreview extends JComponent
                          implements PropertyChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8057707099192655046L;
	
	ImageIcon thumbnail = null;
    File file = null;

    public ImagePreview(JFileChooser fc) {
    	
    	//OpenUp. Guillermo Brust. 30/05/2013. 
    	//Esta linea setea el tamanio que se dedicara en el panel de visualizacion para poner la previalicacion de la imagen, este tamanio no
    	//deberia ser mayor al establecido mas abajo en getScaledInstance
    	
        setPreferredSize(new Dimension(300, 150));
        
        //Fin OpenUp.
        
        fc.addPropertyChangeListener(this);
    }

    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }

        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > 90) {
            	
            	//OpenUp. Guillermo Brust. 30/05/2013. 
            	//Aca se define el tamanio de la imagen a mostrar en la previsualizacion.
            	//Este tamanio deberia ser menor o igual al tamanio definido en el panel contenedor.
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(300, -1,
                                                      Image.SCALE_DEFAULT));
                //Fin OpenUp.
                
            } else { //no need to miniaturize
                thumbnail = tmpIcon;
            }
        }
    }

    public void propertyChange(PropertyChangeEvent e) {
        boolean update = false;
        String prop = e.getPropertyName();

        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;

        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            file = (File) e.getNewValue();
            update = true;
        }

        //Update the preview accordingly.
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
