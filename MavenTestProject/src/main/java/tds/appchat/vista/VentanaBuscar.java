package tds.appchat.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.MensajeCellRenderer;
import tds.appchat.modelo.MensajeCellRenderer2;
import tds.appchat.modelo.Usuario;

public class VentanaBuscar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTexto;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton button;
	private JPanel panelMensajes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBuscar frame = new VentanaBuscar();
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
	public VentanaBuscar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBuscar = new JPanel();
		contentPane.add(panelBuscar, BorderLayout.NORTH);
		panelBuscar.setLayout(new BoxLayout(panelBuscar, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(VentanaBuscar.class.getResource("/imagenes/lupa.png")));
		panelBuscar.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panelBuscar.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{91, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		txtTexto = new JTextField();
		txtTexto.setBorder(new TitledBorder(null, "Texto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtTexto.setToolTipText("texto");
		GridBagConstraints gbc_txtTexto = new GridBagConstraints();
		gbc_txtTexto.gridwidth = 3;
		gbc_txtTexto.insets = new Insets(0, 0, 5, 0);
		gbc_txtTexto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTexto.gridx = 0;
		gbc_txtTexto.gridy = 1;
		panel_1.add(txtTexto, gbc_txtTexto);
		txtTexto.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBorder(new TitledBorder(null, "Tel\u00E9fono", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 2;
		panel_1.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBorder(new TitledBorder(null, "Contacto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		panel_1.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		button = new JButton("Buscar");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = 2;
		gbc_button.gridy = 2;
		panel_1.add(button, gbc_button);
		
		
		
		
		JPanel panelMensajes = new JPanel();
		contentPane.add(panelMensajes, BorderLayout.CENTER);
		panelMensajes.setLayout(new BorderLayout(20, 20));
		
		JList<Mensaje> lista = new JList<Mensaje>();
		lista.setCellRenderer(new MensajeCellRenderer2());
		DefaultListModel<Mensaje> modelo = new DefaultListModel<Mensaje>();
		
		Usuario Juan = new Usuario("Juan");
		Usuario Maria = new Usuario("Maria");
		Mensaje m1 = new Mensaje("Hola", Juan, Maria);
		Mensaje m2 = new Mensaje("Adios", Maria, Juan);
		List<Mensaje> mensajes = new LinkedList<>();	
		mensajes.add(m1);
		mensajes.add(m2);			
		//Conversión manual de List<Mensaje> a DefaultListModel<Mensaje> 
		for(Mensaje mensaje: mensajes) {
			modelo.addElement(mensaje);
		}
		lista.setModel(modelo);
		
		panelMensajes.add(new JScrollPane(lista), BorderLayout.CENTER);
		
	}

}
