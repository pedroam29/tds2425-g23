package tds.appchat.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

import tds.appchat.controlador.AppChat;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

public class VentanaLogin {

	private JFrame frame;
	private JTextField textfieldTelefono;
	private JPasswordField textfieldPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaLogin() {
		initialize();
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 588, 430);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblLogo = new JLabel("APPCHAT");
		lblLogo.setBackground(Color.WHITE);
		lblLogo.setForeground(new Color(0, 0, 0));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setFont(new Font("Dialog", Font.BOLD, 53));
		frame.getContentPane().add(lblLogo, BorderLayout.NORTH);
		
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(Color.WHITE);
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{15, 0, 0, 15, 0};
		gbl_panel_1.rowHeights = new int[]{50, 0, 0, 15, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panel_1);
		
		JLabel labelTelefono = new JLabel("TELEFONO: ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panelCentral.add(labelTelefono, gbc_lblNewLabel_1);
		
		textfieldTelefono = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		panelCentral.add(textfieldTelefono, gbc_textField);
		textfieldTelefono.setColumns(15);
		
		JLabel labelPassword = new JLabel("CONTRASEÑA: ");
		GridBagConstraints gbc_labelPassword = new GridBagConstraints();
		gbc_labelPassword.anchor = GridBagConstraints.EAST;
		gbc_labelPassword.insets = new Insets(0, 0, 5, 5);
		gbc_labelPassword.gridx = 1;
		gbc_labelPassword.gridy = 2;
		panelCentral.add(labelPassword, gbc_labelPassword);
		
		textfieldPassword = new JPasswordField();
		textfieldPassword.setColumns(15);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		panelCentral.add(textfieldPassword, gbc_passwordField);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(Color.WHITE);
		frame.getContentPane().add(panelBotones, BorderLayout.SOUTH);
		
		JButton botonRegistrar = new BotonGeneral("Registrar");
		botonRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegister registro = new VentanaRegister();
				registro.setVisible(true);
				frame.dispose();
			}
		});
		panelBotones.add(botonRegistrar);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panelBotones.add(rigidArea);
		
		JButton botonCancelar = new BotonGeneral("Cancelar");
		botonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panelBotones.add(botonCancelar);
		
		JButton botonAceptar = new BotonGeneral("Aceptar");
		botonAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Recuperar datos de pantalla
				String telefono= new String(textfieldTelefono.getText()); //textfieldtelefono.getText()
				String clave= new String(textfieldPassword.getPassword());
				//ejecutar negocio a traves de controlador
				boolean login = AppChat.getUnicaInstancia().loginUsuario(telefono, clave);	//AppChat.hacerLogin();
				if(login) {
					VentanaPrincipal principal = new VentanaPrincipal();
					principal.setVisible(true);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "El login es incorrecto");
				}
			}
		});
		panelBotones.add(botonAceptar);		
	}
}
