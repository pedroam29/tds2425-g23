package tds.appchat.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Mensaje;

public class MensajeCellRenderer extends JPanel
		implements ListCellRenderer<Mensaje>{
	private JLabel nameLabel;
	private JLabel imageLabel;
	private JLabel messageLabel;
	
	public MensajeCellRenderer() {
		setLayout(new BorderLayout(5, 5));

		nameLabel = new JLabel();
		imageLabel = new JLabel();
		messageLabel = new JLabel();

		JPanel panelTexto = new JPanel(new BorderLayout());
		panelTexto.add(nameLabel, BorderLayout.NORTH);
		panelTexto.add(messageLabel, BorderLayout.SOUTH);
		
		add(imageLabel, BorderLayout.WEST);
		add(panelTexto, BorderLayout.CENTER);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		//Necesario: Obtener 
	
		nameLabel.setText(mensaje.getTlfEmisor());
		messageLabel.setText(mensaje.getTexto());

		// Set background and foreground based on selection
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
			//panelTexto.setBackground
			
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		return this;
	}
	
	
}
