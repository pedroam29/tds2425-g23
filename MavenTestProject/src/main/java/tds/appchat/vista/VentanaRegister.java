package tds.appchat.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import com.toedter.calendar.JDateChooser;

import tds.appchat.controlador.AppChat;

public class VentanaRegister extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labelTelefono;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JLabel labelPassword;
	private JPasswordField textFieldPassword;
	private JTextField textFieldTelefono;
	private JLabel labelConfirmarPassword;
	private JPasswordField textFieldConfirmarPassword;
	private JLabel labelFecha;
	private JLabel labelSaludo;
	private JTextArea textAreaSaludo;
	private JPanel panelBotones;
	private JButton botonCancelar;
	private JButton botonAceptar;
	private Component horizontalGlue;
	private JLabel labelImagen;
	private JTextField textFieldImagenURL;
	private JLabel labelImagenObtenida;
	private JDateChooser dateChooser;
	private JTextField textFieldEmail;
	private JLabel labelEmail;
	
	/**
	 * Create the frame.
	 */
	public VentanaRegister() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{20, 0, 128, 0, 76, 20, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 189, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel labelNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_labelNombre = new GridBagConstraints();
		gbc_labelNombre.anchor = GridBagConstraints.EAST;
		gbc_labelNombre.insets = new Insets(0, 0, 5, 5);
		gbc_labelNombre.gridx = 1;
		gbc_labelNombre.gridy = 1;
		contentPane.add(labelNombre, gbc_labelNombre);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 3;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 1;
		contentPane.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel labelApellidos = new JLabel("Apellidos:");
		GridBagConstraints gbc_labelApellidos = new GridBagConstraints();
		gbc_labelApellidos.anchor = GridBagConstraints.EAST;
		gbc_labelApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_labelApellidos.gridx = 1;
		gbc_labelApellidos.gridy = 2;
		contentPane.add(labelApellidos, gbc_labelApellidos);
		
		textFieldApellidos = new JTextField();
		GridBagConstraints gbc_textFieldApellidos = new GridBagConstraints();
		gbc_textFieldApellidos.gridwidth = 3;
		gbc_textFieldApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApellidos.gridx = 2;
		gbc_textFieldApellidos.gridy = 2;
		contentPane.add(textFieldApellidos, gbc_textFieldApellidos);
		textFieldApellidos.setColumns(10);
		
		labelTelefono = new JLabel("Teléfono:");
		GridBagConstraints gbc_labelTelefono = new GridBagConstraints();
		gbc_labelTelefono.anchor = GridBagConstraints.EAST;
		gbc_labelTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_labelTelefono.gridx = 1;
		gbc_labelTelefono.gridy = 3;
		contentPane.add(labelTelefono, gbc_labelTelefono);
		
		textFieldTelefono = new JTextField();
		GridBagConstraints gbc_textFieldTelefono = new GridBagConstraints();
		gbc_textFieldTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelefono.gridx = 2;
		gbc_textFieldTelefono.gridy = 3;
		contentPane.add(textFieldTelefono, gbc_textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		labelEmail = new JLabel("Email");
		GridBagConstraints gbc_labelEmail = new GridBagConstraints();
		gbc_labelEmail.insets = new Insets(0, 0, 5, 5);
		gbc_labelEmail.anchor = GridBagConstraints.EAST;
		gbc_labelEmail.gridx = 3;
		gbc_labelEmail.gridy = 3;
		contentPane.add(labelEmail, gbc_labelEmail);
		
		textFieldEmail = new JTextField();
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.gridx = 4;
		gbc_textFieldEmail.gridy = 3;
		contentPane.add(textFieldEmail, gbc_textFieldEmail);
		textFieldEmail.setColumns(10);
		
		labelPassword = new JLabel("Contraseña:");
		GridBagConstraints gbc_labelPassword = new GridBagConstraints();
		gbc_labelPassword.anchor = GridBagConstraints.EAST;
		gbc_labelPassword.insets = new Insets(0, 0, 5, 5);
		gbc_labelPassword.gridx = 1;
		gbc_labelPassword.gridy = 4;
		contentPane.add(labelPassword, gbc_labelPassword);
		
		textFieldPassword = new JPasswordField();
		GridBagConstraints gbc_textFieldPassword = new GridBagConstraints();
		gbc_textFieldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPassword.gridx = 2;
		gbc_textFieldPassword.gridy = 4;
		contentPane.add(textFieldPassword, gbc_textFieldPassword);
		
		labelConfirmarPassword = new JLabel("Contraseña:");
		GridBagConstraints gbc_labelConfirmarPassword = new GridBagConstraints();
		gbc_labelConfirmarPassword.anchor = GridBagConstraints.EAST;
		gbc_labelConfirmarPassword.insets = new Insets(0, 0, 5, 5);
		gbc_labelConfirmarPassword.gridx = 3;
		gbc_labelConfirmarPassword.gridy = 4;
		contentPane.add(labelConfirmarPassword, gbc_labelConfirmarPassword);
		
		textFieldConfirmarPassword = new JPasswordField();
		GridBagConstraints gbc_textFieldConfirmarPassword = new GridBagConstraints();
		gbc_textFieldConfirmarPassword.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldConfirmarPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldConfirmarPassword.gridx = 4;
		gbc_textFieldConfirmarPassword.gridy = 4;
		contentPane.add(textFieldConfirmarPassword, gbc_textFieldConfirmarPassword);
		
		labelFecha = new JLabel("Fecha:");
		GridBagConstraints gbc_labelFecha = new GridBagConstraints();
		gbc_labelFecha.anchor = GridBagConstraints.EAST;
		gbc_labelFecha.insets = new Insets(0, 0, 5, 5);
		gbc_labelFecha.gridx = 1;
		gbc_labelFecha.gridy = 5;
		contentPane.add(labelFecha, gbc_labelFecha);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 5;
		contentPane.add(dateChooser, gbc_dateChooser);
		
		labelSaludo = new JLabel("Saludo:");
		GridBagConstraints gbc_labelSaludo = new GridBagConstraints();
		gbc_labelSaludo.anchor = GridBagConstraints.EAST;
		gbc_labelSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_labelSaludo.gridx = 1;
		gbc_labelSaludo.gridy = 6;
		contentPane.add(labelSaludo, gbc_labelSaludo);
		
		textAreaSaludo = new JTextArea();
		GridBagConstraints gbc_textAreaSaludo = new GridBagConstraints();
		gbc_textAreaSaludo.gridheight = 2;
		gbc_textAreaSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaSaludo.fill = GridBagConstraints.BOTH;
		gbc_textAreaSaludo.gridx = 2;
		gbc_textAreaSaludo.gridy = 6;
		contentPane.add(textAreaSaludo, gbc_textAreaSaludo);
		
		labelImagen = new JLabel("Imagen:");
		GridBagConstraints gbc_labelImagen = new GridBagConstraints();
		gbc_labelImagen.anchor = GridBagConstraints.EAST;
		gbc_labelImagen.insets = new Insets(0, 0, 5, 5);
		gbc_labelImagen.gridx = 3;
		gbc_labelImagen.gridy = 6;
		contentPane.add(labelImagen, gbc_labelImagen);
		
		textFieldImagenURL = new JTextField();
		GridBagConstraints gbc_textFieldImagenURL = new GridBagConstraints();
		gbc_textFieldImagenURL.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldImagenURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldImagenURL.gridx = 4;
		gbc_textFieldImagenURL.gridy = 6;
		contentPane.add(textFieldImagenURL, gbc_textFieldImagenURL);
		textFieldImagenURL.setColumns(10);
		
		labelImagenObtenida = new JLabel("");
		labelImagenObtenida.setIcon(new ImageIcon(VentanaRegister.class.getResource("/imagenes/usuario.png")));
		GridBagConstraints gbc_labelImagenObtenida = new GridBagConstraints();
		gbc_labelImagenObtenida.insets = new Insets(0, 0, 5, 5);
		gbc_labelImagenObtenida.gridx = 4;
		gbc_labelImagenObtenida.gridy = 7;
		contentPane.add(labelImagenObtenida, gbc_labelImagenObtenida);
		
		panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.insets = new Insets(0, 0, 0, 5);
		gbc_panelBotones.fill = GridBagConstraints.BOTH;
		gbc_panelBotones.gridx = 2;
		gbc_panelBotones.gridy = 8;
		contentPane.add(panelBotones, gbc_panelBotones);
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		
		botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Cuando se presione cancelar se cierra la ventana
				dispose();
			}
		});
		panelBotones.add(botonCancelar);
		
		horizontalGlue = Box.createHorizontalGlue();
		panelBotones.add(horizontalGlue);
		
		//Botón aceptar: para que se acepte se tienen que pasar los requisitos
		botonAceptar = new JButton("Aceptar");
		panelBotones.add(botonAceptar);
		
		botonAceptar.addActionListener(new ActionListener() {
			
//			/**
//			 * Función que comprieba si la contraseña es acorde a las exigencias de
//			 * seguridad (número mínimo de caracteres, si hay mayúsculas ...)
//			 * @param parámetro contraseña
//			 * @return true si la contraseña es válida
//			 */
//			public boolean condicionPassword(String password)
//			{
//				return password != "";
//			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean registroPosible = true;
				//Obtener todos los campos y mandarlos al registrar del AppChat
				String nombre = textFieldNombre.getText();
				String telefono = textFieldTelefono.getText();
				String contrasena = textFieldPassword.getText();
				String contrasena2 = textFieldConfirmarPassword.getText();
				Date fechaNacimiento = dateChooser.getDate();
				String imagenPerfilUrl = textFieldImagenURL.getText();
				String saludo = textAreaSaludo.getText();
				String email = textFieldEmail.getText();
				
				
				//Solución momentánea para comprobar que los campos están llenos
				registroPosible = !((nombre == "") || (telefono == "") || (contrasena == "") ||
						(contrasena2 == "")|| (imagenPerfilUrl == "") || (saludo == "") || (email == "") || (fechaNacimiento == null));
				
				if (registroPosible) {				
					//Se realiza el registro 
					boolean registro = AppChat.getUnicaInstancia().registrarUsuario(nombre, telefono, contrasena, fechaNacimiento, imagenPerfilUrl, saludo, email);
					if (registro)
					{
						VentanaPrincipal principal = new VentanaPrincipal();
						principal.setVisible(true);
					} else 
					{
						//No se ha podido realizar el registro: AppChat lo rechaza
						JOptionPane.showMessageDialog(null, "El teléfono ya está registrado");
					}
				} else {
					//No se ha podido llamar a appChat hay campos incorrectos
					JOptionPane.showMessageDialog(null, "Hay campos incorrectos");
				}
			}
		});
		
		this.setVisible(true);
	}

}
