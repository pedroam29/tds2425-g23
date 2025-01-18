package tds.appchat.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
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
		
		private JLabel imageLabel = new JLabel();
	    private JLabel nameLabel = new JLabel();
	    private JLabel messageLabel = new JLabel();
	    private JButton botonAgregarContacto = new JButton("Add");

	    public MensajeCellRenderer() {
	        setLayout(new BorderLayout(5, 5));
	        JPanel panelCentral = new JPanel(new BorderLayout());
	        panelCentral.add(nameLabel, BorderLayout.NORTH);
	        panelCentral.add(messageLabel, BorderLayout.CENTER);
	        add(imageLabel, BorderLayout.WEST);
	        add(panelCentral, BorderLayout.CENTER);
	        add(botonAgregarContacto, BorderLayout.EAST);

	        botonAgregarContacto.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String telefono = (String) botonAgregarContacto.getClientProperty("telefono");
	                if (telefono != null) {
	                    VentanaRegistrarContactoDeMensaje ventana = new VentanaRegistrarContactoDeMensaje(telefono);
	                    ventana.setVisible(true);
	                }
	            }
	        });
	    }

	    @Override
	    public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
	                                                  boolean isSelected, boolean cellHasFocus) {
	        if (mensaje.isMensajeGrupo()) {
	            imageLabel.setIcon(new ImageIcon(AppChat.obtenerImagenPerfilUrl(40, 40, mensaje.getGrupo().getUrlImagen())));
	            nameLabel.setText(mensaje.getGrupo().getNombre());
	            messageLabel.setText(mensaje.getTexto());
	            botonAgregarContacto.setVisible(false);
	        } else {
	            Usuario usr = AppChat.getUnicaInstancia().obtenerUsuarioDesdeMensaje(mensaje);
	            imageLabel.setIcon(new ImageIcon(usr.getImagen().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
	            nameLabel.setText(usr.getTelefono());
	            messageLabel.setText(mensaje.getTexto());

	            if (!AppChat.getUnicaInstancia().esUsuarioContacto(usr)) {
	                botonAgregarContacto.setVisible(true);
	                botonAgregarContacto.putClientProperty("telefono", usr.getTelefono());
	            } else {
	                botonAgregarContacto.setVisible(false);
	                ContactoIndividual c = (ContactoIndividual) AppChat.getUnicaInstancia().obtenerContactoUsuario(usr);
	                nameLabel.setText(c.getNombre());
	            }
	        }

	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }

	        return this;
	    }

	protected class VentanaRegistrarContactoDeMensaje extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTextField textField_nombre;

		/**
		 * Create the frame.
		 */
		public VentanaRegistrarContactoDeMensaje(String telefono) {
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setResizable(false);
			
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(new BorderLayout(0, 0));
			
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.NORTH);
			
			JLabel lblNewLabel = new JLabel("Insertar nombre");
			panel.add(lblNewLabel);
			
			JPanel panel_1 = new JPanel();
			contentPane.add(panel_1, BorderLayout.CENTER);
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{5, 0, 0, 0, 0, 0};
			gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			
			JLabel lblNewLabel_1 = new JLabel("Nombre: ");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 1;
			panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
			
			textField_nombre = new JTextField();
			GridBagConstraints gbc_textField_nombre = new GridBagConstraints();
			gbc_textField_nombre.gridwidth = 2;
			gbc_textField_nombre.insets = new Insets(0, 0, 5, 5);
			gbc_textField_nombre.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_nombre.gridx = 2;
			gbc_textField_nombre.gridy = 1;
			panel_1.add(textField_nombre, gbc_textField_nombre);
			textField_nombre.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Teléfono: ");
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_2.gridx = 1;
			gbc_lblNewLabel_2.gridy = 3;
			panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
			
			JLabel lblTelefonoEscrito = new JLabel(telefono);
			GridBagConstraints gbc_textField_telf = new GridBagConstraints();
			gbc_textField_telf.gridwidth = 2;
			gbc_textField_telf.insets = new Insets(0, 0, 5, 5);
			gbc_textField_telf.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_telf.gridx = 2;
			gbc_textField_telf.gridy = 3;
			panel_1.add(lblTelefonoEscrito, gbc_textField_telf);
			
			/**
			 * Botón aeceptar: 
			 * 		 
			 */
			JButton btnAceptar = new JButton("Aceptar");
			GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
			gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
			gbc_btnAceptar.gridx = 2;
			gbc_btnAceptar.gridy = 5;
			panel_1.add(btnAceptar, gbc_btnAceptar);
			
							
					JButton btnCancelar = new JButton("Cancelar");
					GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
					gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
					gbc_btnCancelar.gridx = 3;
					gbc_btnCancelar.gridy = 5;
					panel_1.add(btnCancelar, gbc_btnCancelar);	
					
					btnCancelar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
			
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ContactoIndividual contacto = AppChat.getUnicaInstancia().crearContacto(textField_nombre.getText(), telefono);
					
					if(contacto!=null) {
						JOptionPane.showMessageDialog(VentanaRegistrarContactoDeMensaje.this, "Contacto añadido exitosamente", "Info",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(VentanaRegistrarContactoDeMensaje.this, "Este teléfono ya existe en el sistema", "Info",
								JOptionPane.ERROR_MESSAGE);
					}
					//dispose();
				}
			});
		}
	}
}

