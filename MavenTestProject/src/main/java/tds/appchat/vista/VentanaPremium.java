package tds.appchat.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Descuento;
import tds.appchat.modelo.Premium;

import java.awt.SystemColor;
import javax.swing.UIManager;

public class VentanaPremium extends JFrame {
	public VentanaPremium() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 486);
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 0, 0, 0, 0, 10, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 10, 0, 0, 0, 0, 3, 0, 10, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblPremium = new JLabel("PREMIUM");
		lblPremium.setForeground(Color.BLACK);
		lblPremium.setFont(new Font("Dialog", Font.PLAIN, 58));
		GridBagConstraints gbc_lblPremium = new GridBagConstraints();
		gbc_lblPremium.gridwidth = 2;
		gbc_lblPremium.insets = new Insets(0, 0, 5, 5);
		gbc_lblPremium.gridx = 2;
		gbc_lblPremium.gridy = 1;
		panel.add(lblPremium, gbc_lblPremium);
		
		
		//Obtención del precio a pagar:
		
		//La label mostrará el precio original
		String precioOriginal = new String(Double.toString(AppChat.getUnicaInstancia().obtenerPrecioPremium()) + "€");
		
		//Obtener descuento
		

//		Descuento descuento = null;
//		En caso de que exista un descuento
//		El precio actual se tachará y se pondrá el precio rebajado debajo
//		//TODO: Ajustar tamaño de los precios
		
		JLabel lblPrecioOriginal = new JLabel(precioOriginal);
		
		Descuento descuento = AppChat.getUnicaInstancia().obtenerDescuento();
		if (descuento != null){
			lblPrecioOriginal.setText("<html><strike>" + precioOriginal + "</strike></html>");			
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 6;
		panel.add(label, gbc_label);

		JLabel lblPrecioDescuento = new JLabel(String.format("%.2f", Double.toString(descuento.calcularDescuento(Premium.getPrecioPremium()))));
		lblPrecioDescuento.setIcon(new ImageIcon(VentanaPremium.class.getResource("/imagenes/descuento.png")));
		lblPrecioDescuento.setForeground(new Color(154, 205, 50));
		lblPrecioDescuento.setFont(new Font("Dialog", Font.PLAIN, 58));
		GridBagConstraints gbc_lblPreciodescuento = new GridBagConstraints();
		gbc_lblPreciodescuento.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreciodescuento.gridx = 2;
		gbc_lblPreciodescuento.gridy = 6;
		panel.add(lblPrecioDescuento, gbc_lblPreciodescuento);
		
		}
		
		BotonGeneral btnPagarPremium = new BotonGeneral("Realizar Pago");
		
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 8;
		panel.add(btnPagarPremium, gbc_btnNewButton);
		
		btnPagarPremium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(AppChat.getUnicaInstancia().convertirPremium())
					JOptionPane.showMessageDialog(VentanaPremium.this,
							"Enhorabuena, es usted usuario premium hasta "
					+ AppChat.getUnicaInstancia().obtenerExpiracionPremium().toString(), "Info",
						JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(VentanaPremium.this, "No se ha podido realizar la conversión a premium", "Error",
							JOptionPane.ERROR_MESSAGE);
				dispose();
			}
		});
		
		JLabel lblFuncionalidadPremium = new JLabel("- Posibilidad de crear PDFs de conversaciones");
		lblFuncionalidadPremium.setFont(new Font("L M Sans10", Font.PLAIN, 12));
		GridBagConstraints gbc_lblPosibilidadDeCrear = new GridBagConstraints();
		gbc_lblPosibilidadDeCrear.gridwidth = 2;
		gbc_lblPosibilidadDeCrear.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosibilidadDeCrear.gridx = 2;
		gbc_lblPosibilidadDeCrear.gridy = 3;
		panel.add(lblFuncionalidadPremium, gbc_lblPosibilidadDeCrear);
		
		
		lblPrecioOriginal.setForeground(new Color(0, 0, 0));
		lblPrecioOriginal.setFont(new Font("Dialog", Font.PLAIN, 58));
		
		GridBagConstraints gbc_lblao = new GridBagConstraints();
		gbc_lblao.insets = new Insets(0, 0, 5, 5);
		gbc_lblao.gridx = 2;
		gbc_lblao.gridy = 5;
		panel.add(lblPrecioOriginal, gbc_lblao);
		
		//String precioDescontado = new String(Double.toString(descuento.calcularDescuento(AppChat.getUnicaInstancia().obtenerPrecioPremium())) + "€");
		//JLabel lblPreciodescuento = new JLabel(precioDescontado);
		

	}
}
