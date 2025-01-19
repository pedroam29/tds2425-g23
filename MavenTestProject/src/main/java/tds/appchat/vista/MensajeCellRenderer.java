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

public class MensajeCellRenderer extends JPanel
		implements ListCellRenderer<Mensaje>{

		private static final long serialVersionUID = 1L;
		
		private JLabel labelIcono = new JLabel();
	    private JLabel labelNombre = new JLabel();
	    private JLabel labelTexto = new JLabel();

	    public MensajeCellRenderer() {
			setLayout(new BorderLayout(10, 10));
			//Se añade una separación entre los elementos
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			// Inicialización de los componentes
			
			JPanel textPanel = new JPanel(new BorderLayout());
			textPanel.setOpaque(false);
			add(textPanel, BorderLayout.CENTER);
			
			
			//Nombre / Telefono
			labelNombre = new JLabel();
			labelNombre.setFont(new Font("Arial", Font.BOLD, 14));
			textPanel.add(labelNombre, BorderLayout.NORTH);
			//Cuerpo del mensaje
			labelTexto = new JLabel();
			labelTexto.setFont(new Font("Arial", Font.PLAIN, 12));
			textPanel.add(labelTexto, BorderLayout.CENTER);
			
			//Icono:
			labelIcono = new JLabel();
			add(labelIcono, BorderLayout.WEST);
	    }

	    @Override
	    public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
	                                                  boolean isSelected, boolean cellHasFocus) {
	        if (mensaje.isMensajeGrupo()) {
	        	labelIcono.setIcon(new ImageIcon(AppChat.obtenerImagenPerfilUrl(40, 40, mensaje.getGrupo().getUrlImagen())));
	            labelNombre.setText(mensaje.getGrupo().getNombre());
	            labelTexto.setText(mensaje.getTexto());
	        } else {
	            Usuario usr = AppChat.getUnicaInstancia().obtenerUsuarioDesdeMensaje(mensaje);
	            labelIcono.setIcon(new ImageIcon(usr.getImagen().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
	            labelNombre.setText(usr.getTelefono());
	            labelTexto.setText(mensaje.getTexto());

	            if (AppChat.getUnicaInstancia().esUsuarioContacto(usr)) {
	                ContactoIndividual c = (ContactoIndividual) AppChat.getUnicaInstancia().obtenerContactoUsuario(usr);
	                labelNombre.setText(c.getNombre());
	            }
	        }

	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
	        
	        //Para que los elementos queden mas separados entre ellos se va a insertar un CompoundBorder
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BotonGeneral.COLOR_INV),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
	        return this;
	    }
}

