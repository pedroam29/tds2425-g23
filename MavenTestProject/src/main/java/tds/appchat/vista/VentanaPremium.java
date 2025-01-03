package tds.appchat.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Descuento;

public class VentanaPremium extends JFrame {
	public VentanaPremium() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 486);
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 10, 0, 10, 0, 0, 0, 10, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblPremium = new JLabel("PREMIUM");
		lblPremium.setFont(new Font("Dialog", Font.PLAIN, 58));
		GridBagConstraints gbc_lblPremium = new GridBagConstraints();
		gbc_lblPremium.insets = new Insets(0, 0, 5, 5);
		gbc_lblPremium.gridx = 2;
		gbc_lblPremium.gridy = 1;
		panel.add(lblPremium, gbc_lblPremium);
		
		JLabel lblPagoAnualCuota = new JLabel("Pago anual, cuota de:");
		lblPagoAnualCuota.setFont(new Font("Dialog", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPagoAnualCuota = new GridBagConstraints();
		gbc_lblPagoAnualCuota.insets = new Insets(0, 0, 5, 5);
		gbc_lblPagoAnualCuota.gridx = 2;
		gbc_lblPagoAnualCuota.gridy = 3;
		panel.add(lblPagoAnualCuota, gbc_lblPagoAnualCuota);
		
		
		//Obtención del precio a pagar:
		
		//La label mostrará el precio original
		String precioOriginal = new String(Double.toString(AppChat.getUnicaInstancia().obtenerPrecioPremium()) + "€");
		
		JLabel lblPrecioOriginal = new JLabel(precioOriginal);
		lblPrecioOriginal.setForeground(new Color(0, 0, 0));
		GridBagConstraints gbc_lblao = new GridBagConstraints();
		gbc_lblao.insets = new Insets(0, 0, 5, 5);
		gbc_lblao.gridx = 2;
		gbc_lblao.gridy = 4;
		panel.add(lblPrecioOriginal, gbc_lblao);
		
		//Obtener descuento
		
		Descuento descuento = AppChat.getUnicaInstancia().obtenerDescuento();
		//Descuento descuento = null;
		//En caso de que exista un descuento
		//El precio actual se tachará y se pondrá el precio rebajado debajo
		//TODO: Ajustar tamaño de los precios
		if (descuento != null){
			lblPrecioOriginal.setText("<html><strike>" + precioOriginal + "</strike></html>");			
			String precioDescontado = new String(Double.toString(descuento.calcularDescuento(AppChat.getUnicaInstancia().obtenerPrecioPremium())) + "€");
			JLabel lblPreciodescuento = new JLabel(precioDescontado);
			
			lblPreciodescuento.setForeground(new Color(154, 205, 50));
			GridBagConstraints gbc_lblPreciodescuento = new GridBagConstraints();
			gbc_lblPreciodescuento.insets = new Insets(0, 0, 5, 5);
			gbc_lblPreciodescuento.gridx = 2;
			gbc_lblPreciodescuento.gridy = 5;
			panel.add(lblPreciodescuento, gbc_lblPreciodescuento);
		}
		
		JButton btnPagarPremium = new JButton("Realizar Pago");
		btnPagarPremium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Suponer que el pago se realiza perfectamente
				AppChat.getUnicaInstancia().convertirPremium();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 7;
		panel.add(btnPagarPremium, gbc_btnNewButton);
	}
}
