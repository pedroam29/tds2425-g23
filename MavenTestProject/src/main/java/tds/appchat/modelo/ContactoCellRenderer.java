package tds.appchat.modelo;

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

public class ContactoCellRenderer extends JPanel
	implements ListCellRenderer<Contacto>{
	
	private JLabel nombreLabel;
	
	public ContactoCellRenderer() {
	    setLayout(new BorderLayout(20, 20));
	    nombreLabel = new JLabel();
	    add(nombreLabel, BorderLayout.CENTER);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto contacto, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		nombreLabel.setText(contacto.getNombre());
		
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
