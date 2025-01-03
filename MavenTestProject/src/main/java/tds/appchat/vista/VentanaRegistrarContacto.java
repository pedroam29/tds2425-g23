package tds.appchat.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.ContactoIndividual;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaRegistrarContacto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_nombre;
	private JTextField textField_telf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistrarContacto frame = new VentanaRegistrarContacto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaRegistrarContacto() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Insertar nombre y teléfono");
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
		
		textField_telf = new JTextField();
		GridBagConstraints gbc_textField_telf = new GridBagConstraints();
		gbc_textField_telf.gridwidth = 2;
		gbc_textField_telf.insets = new Insets(0, 0, 5, 5);
		gbc_textField_telf.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_telf.gridx = 2;
		gbc_textField_telf.gridy = 3;
		panel_1.add(textField_telf, gbc_textField_telf);
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
					JOptionPane.showMessageDialog(VentanaRegistrarContacto.this, "Contacto añadido exitosamente", "Info",
							JOptionPane.INFORMATION_MESSAGE);
				}else if(!AppChat.getUnicaInstancia().existeTelefono(textField_telf.getText())){
					JOptionPane.showMessageDialog(VentanaRegistrarContacto.this, "El contacto no se pudo añadir porque no existe el número de telefono", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(VentanaRegistrarContacto.this, "Este teléfono ya existe en el sistema", "Info",
							JOptionPane.ERROR_MESSAGE);
				}
				//dispose();
			}
		});
	}

}
