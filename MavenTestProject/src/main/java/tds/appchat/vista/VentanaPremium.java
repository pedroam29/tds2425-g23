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
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;

public class VentanaPremium {

	private JFrame frame;
	private JTextField textfieldTelefono;
	private JPasswordField textfieldPassword;
	private JTextField txtPremium;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPremium window = new VentanaPremium();
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
	public VentanaPremium() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		txtPremium = new JTextField();
		txtPremium.setSize(new Dimension(15, 15));
		txtPremium.setFont(new Font("Lato Heavy", Font.BOLD, 12));
		txtPremium.setText("PREMIUM");
		frame.getContentPane().add(txtPremium, BorderLayout.CENTER);
		txtPremium.setColumns(10);
		
		
	}

}