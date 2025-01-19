package tds.appchat.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class VentanaRegistrarGrupo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_nombre;
	private JTextField textField_imagen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistrarGrupo frame = new VentanaRegistrarGrupo();
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
	public VentanaRegistrarGrupo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblAddGrupo = new JLabel("AÑADIR GRUPO");
		Grupo.class.getResource("");
		lblAddGrupo.setIcon(new ImageIcon(Grupo.imagenDefault()));
		lblAddGrupo.setFont(new Font("Dialog", Font.BOLD, 30));
		panel.add(lblAddGrupo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{5, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre: ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_nombre = new JTextField();
		GridBagConstraints gbc_textField_nombre = new GridBagConstraints();
		gbc_textField_nombre.gridwidth = 2;
		gbc_textField_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_textField_nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_nombre.gridx = 2;
		gbc_textField_nombre.gridy = 2;
		panel_1.add(textField_nombre, gbc_textField_nombre);
		textField_nombre.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Imagen: ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField_imagen = new JTextField();
		GridBagConstraints gbc_textField_telf = new GridBagConstraints();
		gbc_textField_telf.gridwidth = 2;
		gbc_textField_telf.insets = new Insets(0, 0, 5, 5);
		gbc_textField_telf.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_telf.gridx = 2;
		gbc_textField_telf.gridy = 3;
		panel_1.add(textField_imagen, gbc_textField_telf);
		textField_imagen.setColumns(10);
		
		/**
		 * Botón aceptar
		 */
		JButton btnNewButton = new BotonGeneral("Aceptar");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 6;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_nombre.getText().isEmpty())
					JOptionPane.showMessageDialog(VentanaRegistrarGrupo.this, "Es necesario poner un nombre");
				else {
					try {
						AppChat.getUnicaInstancia().crearGrupo(textField_nombre.getText(), textField_imagen.getText());	
					} catch (Exception e2) {
						//Se mostrará el mensaje de error de la funcion del controlador
						JOptionPane.showMessageDialog(VentanaRegistrarGrupo.this, e2.getMessage());
					} finally {
						JOptionPane.showMessageDialog(VentanaRegistrarGrupo.this, "Grupo creado correctamente");
					}
				}
			}
		});
				
		JButton btnCancelar = new BotonGeneral("Cancelar");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 3;
		gbc_btnNewButton_1.gridy = 6;
		panel_1.add(btnCancelar, gbc_btnNewButton_1);
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//dispose();
				//Se hace así para que cuando se le de al botón cancelar también se tome como cerrado y se actualize automáticamente la lista
				 dispatchEvent(new WindowEvent(VentanaRegistrarGrupo.this, WindowEvent.WINDOW_CLOSING));
			}
		});
	}

}
