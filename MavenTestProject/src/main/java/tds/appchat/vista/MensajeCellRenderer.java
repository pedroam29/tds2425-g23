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
import tds.appchat.modelo.Mensaje3;
import tds.appchat.modelo.Usuario;

public class MensajeCellRenderer extends JPanel
		implements ListCellRenderer<Mensaje>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel nameLabel;
	private JLabel imageLabel;
	private JLabel messageLabel;
	private JButton botonAgregarContacto;

	private JPanel panelBoton;
	
	public MensajeCellRenderer() {
		setLayout(new BorderLayout(5, 5));

		nameLabel = new JLabel();
		imageLabel = new JLabel();
		messageLabel = new JLabel();

		JPanel panelTexto = new JPanel(new BorderLayout());
		panelBoton = new JPanel(new BorderLayout());
		
		panelTexto.add(nameLabel, BorderLayout.NORTH);
		panelTexto.add(messageLabel, BorderLayout.SOUTH);
		
		
		
		
		add(panelBoton, BorderLayout.EAST);
		add(panelTexto, BorderLayout.CENTER);

		
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		//Si es un mensaje a un grupo es que es un grupo, por tanto se pondrá una imagen de un grupo
		if (mensaje.isMensajeGrupo()) {
			//TODO: Cambiar la funcion del reescalado de fotos
			imageLabel.setIcon(new ImageIcon(AppChat.obtenerImagenPerfilUrl(40, 40,  mensaje.getGrupo().getUrlImagen())));
			messageLabel.setText(mensaje.getTexto());
			nameLabel.setText(mensaje.getGrupo().getNombre());
		} else {
			Usuario usr = AppChat.getUnicaInstancia().obtenerUsuarioDesdeMensaje(mensaje);
			//En caso de que no sea un grupo se realizará por el usuario del otro extremo.
			imageLabel.setIcon(new ImageIcon(AppChat.getUnicaInstancia().obtenerUsuarioDesdeMensaje(mensaje).getImagen()));
			add(imageLabel, BorderLayout.WEST);
			messageLabel.setText(mensaje.getTexto());
			
			if (AppChat.getUnicaInstancia().esUsuarioContacto(usr)){			{
				nameLabel.setText(usr.getTelefono());
				panelBoton.add(botonAgregarContacto, BorderLayout.CENTER);
				botonAgregarContacto.setText("Add");
				botonAgregarContacto.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						VentanaRegistrarContactoDeMensaje v = new VentanaRegistrarContactoDeMensaje(usr.getTelefono());
						v.setVisible(true);
					}
				});
			}
		}
		
			
		

		}
		new ImageIcon(MensajeCellRenderer.class.getResource("/"));	
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
	
	protected class VentanaRegistrarContactoDeMensaje extends JFrame {
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JTextField textField_nombre;
		private JTextField textField_telf;

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
			textField_telf.setColumns(10);
			
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
					ContactoIndividual contacto = AppChat.getUnicaInstancia().crearContacto(textField_nombre.getText(), textField_telf.getText());
					if(contacto!=null) {
						JOptionPane.showMessageDialog(VentanaRegistrarContactoDeMensaje.this, "Contacto añadido exitosamente", "Info",
								JOptionPane.INFORMATION_MESSAGE);
					}else if(!AppChat.getUnicaInstancia().existeTelefono(textField_telf.getText())){
						JOptionPane.showMessageDialog(VentanaRegistrarContactoDeMensaje.this, "El contacto no se pudo añadir porque no existe el número de telefono", "Error",
								JOptionPane.ERROR_MESSAGE);
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

