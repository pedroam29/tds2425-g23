package tds.appchat.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Usuario;

public class MensajeCellRenderer2 extends JPanel
		implements ListCellRenderer<Mensaje>{

		private static final long serialVersionUID = 1L;
		
		
		
	    private JLabel labelNombreEmisor = new JLabel();
	    private JLabel labelNombreReceptor = new JLabel();
	    
	    private JLabel labelTexto = new JLabel();
	    
	    public MensajeCellRenderer2() {
			setLayout(new BorderLayout(10, 10));
			//Se añade una separación entre los elementos
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			// Inicialización de los componentes
			
			JPanel panelUsuarios = new JPanel(new BorderLayout());
			add(panelUsuarios, BorderLayout.NORTH);
			
			JPanel panelTexto = new JPanel(new BorderLayout());
			add(panelTexto, BorderLayout.SOUTH);
			
			JPanel panelEmisor = new JPanel(new BorderLayout());
			JPanel panelReceptor = new JPanel(new BorderLayout());
			
			panelUsuarios.add(panelEmisor, BorderLayout.EAST);
			panelUsuarios.add(panelReceptor, BorderLayout.WEST);
			
			//Nombre / Telefono
			labelNombreEmisor = new JLabel();
			labelNombreEmisor.setFont(new Font("Arial", Font.BOLD, 14));
			panelEmisor.add(labelNombreEmisor, BorderLayout.CENTER);
			
			labelNombreReceptor = new JLabel();
			labelNombreReceptor.setFont(new Font("Arial", Font.BOLD, 14));
			panelReceptor.add(labelNombreReceptor, BorderLayout.CENTER);
			
			labelTexto = new JLabel();
			panelTexto.add(labelTexto, BorderLayout.CENTER);
			
	    }

	    @Override
	    public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
	                                                  boolean isSelected, boolean cellHasFocus) {
	        	//En caso de que sea un emoticono no se hace nada
	    		if(mensaje.esTextoEmoticono())
	    			return this;
	    		
	            labelNombreEmisor.setText(mensaje.getEmisor().getNombre());
	            labelNombreReceptor.setText(mensaje.getReceptor().getNombre());
	            
	            labelTexto.setText(mensaje.getTexto());

	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
	        
	        //Para que los elementos queden mas separados entre ellos se va a insertar un CompoundBorder
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            
	        return this;
	    }
}

