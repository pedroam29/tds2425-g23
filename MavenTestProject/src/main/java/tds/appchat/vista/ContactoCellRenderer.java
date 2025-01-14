package tds.appchat.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;

public class ContactoCellRenderer extends JPanel
	implements ListCellRenderer<Contacto>{
	
	private JLabel nombreLabel;
	private JLabel imageLabel;
	
	public ContactoCellRenderer() {
	    setLayout(new BorderLayout(20, 20));
	    
	    //2 elementos
	    nombreLabel = new JLabel();
	    imageLabel = new JLabel();
	    
	    add(nombreLabel, BorderLayout.CENTER);
	    add(imageLabel, BorderLayout.WEST);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto contacto, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		nombreLabel.setText(contacto.getNombre());
		
		if (contacto instanceof Grupo) {
			//Si es un grupo, además se añadirá un 
			Grupo g = (Grupo) contacto;
			imageLabel.setIcon(new ImageIcon(AppChat.obtenerImagenPerfilUrl(40,40,g.getUrlImagen())));
			JLabel iconoGrupo = new JLabel(new ImageIcon(ContactoCellRenderer.class.getResource("/imagenes/flecha-inv.png")));			
		} else if (contacto instanceof ContactoIndividual) {
			ContactoIndividual c = (ContactoIndividual) contacto;
			imageLabel.setIcon(new ImageIcon(AppChat.obtenerImagenPerfilUrl(40,40,c.getUrlImagen())));
		}
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
			 nombreLabel.setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
			 nombreLabel.setForeground(list.getForeground());
		}

		setOpaque(true);
		return this;
	}	
}
