package tds.appchat.vista;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.DocumentException;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Mensaje;
import java.awt.Color;

public class VentanaPDF extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_nombre;
	private JTextField textField_telf;

	/**
	 * Create the frame.
	 */
	public VentanaPDF(List<Mensaje> mensaje) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentPane.add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{5, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblImagen = new JLabel();
		GridBagConstraints gbc_lblAa = new GridBagConstraints();
		gbc_lblAa.gridwidth = 2;
		gbc_lblAa.insets = new Insets(0, 0, 5, 5);
		gbc_lblAa.gridx = 2;
		gbc_lblAa.gridy = 1;
		
		lblImagen.setIcon(null);
		panel_1.add(lblImagen, gbc_lblAa);
		
		
		JLabel lblNewLabel_1 = new JLabel("Ruta");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_nombre = new JTextField();
		GridBagConstraints gbc_textField_nombre = new GridBagConstraints();
		gbc_textField_nombre.gridwidth = 2;
		gbc_textField_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_textField_nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_nombre.gridx = 2;
		gbc_textField_nombre.gridy = 3;
		panel_1.add(textField_nombre, gbc_textField_nombre);
		textField_nombre.setColumns(10);
		

		JButton btnAceptar = new BotonGeneral("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 2;
		gbc_btnAceptar.gridy = 5;
		panel_1.add(btnAceptar, gbc_btnAceptar);
		
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				//Comprobar si la ruta es válida:
				String ruta = textField_nombre.getText();
				if (ruta != null) {
					AppChat.getUnicaInstancia().esRutaValida(ruta);
					try {
						AppChat.getUnicaInstancia().convertirPDF(ruta, mensaje);
					} catch (DocumentException e1) {
						JOptionPane.showMessageDialog(VentanaPDF.this, "No se ha podido crear el pdf");
					}
				}
				//Si la ruta es válida se 
			}
		});
						
		JButton btnCancelar = new BotonGeneral("Cancelar");
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
	}

}
