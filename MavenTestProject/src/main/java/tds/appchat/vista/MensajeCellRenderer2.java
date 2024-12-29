package tds.appchat.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import tds.appchat.modelo.Mensaje;

public class MensajeCellRenderer2 extends JPanel
implements ListCellRenderer<Mensaje>{
	private JLabel emisorLabel;
	private JLabel receptorLabel;
	private JLabel textLabel;
	
	public MensajeCellRenderer2() {
		setLayout(new BorderLayout(5, 5));

		emisorLabel = new JLabel();
		receptorLabel = new JLabel();
		textLabel = new JLabel();
		
		JPanel panelTexto = new JPanel(new BorderLayout());
		panelTexto.add(emisorLabel, BorderLayout.WEST);
		panelTexto.add(receptorLabel, BorderLayout.EAST);
		add(panelTexto, BorderLayout.NORTH);
		add(textLabel, BorderLayout.CENTER);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
			boolean isSelected, boolean cellHasFocus) {
		emisorLabel.setText(mensaje.getNombreEmisor());
		receptorLabel.setText(mensaje.getNombreReceptor());
		textLabel.setText(mensaje.getTexto());

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
