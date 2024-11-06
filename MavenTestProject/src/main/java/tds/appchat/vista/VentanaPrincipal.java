package tds.appchat.vista;

import java.awt.EventQueue;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import tds.BubbleText;
import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.MensajeCellRenderer;
import tds.appchat.modelo.Usuario;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
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
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Contacto 1", "Contacto 2"}));
		panelNorte.add(comboBox);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/enviar-mensaje.png")));
		panelNorte.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Buscar");
		btnNewButton_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/buscar.png")));
		panelNorte.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Contactos");
		btnNewButton_2.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/personas.png")));
		panelNorte.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Premium");
		panelNorte.add(btnNewButton_3);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelNorte.add(horizontalGlue);
		
		JLabel lblNewLabel = new JLabel("NombreUsuario");
		panelNorte.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("IconoUsuario");
		panelNorte.add(lblNewLabel_1);
		
		JPanel panelMensajes = new JPanel();
		contentPane.add(panelMensajes, BorderLayout.WEST);
		panelMensajes.setLayout(new BorderLayout(0, 0));
		
		JList<Mensaje> lista = new JList<Mensaje>();
		lista.setCellRenderer(new MensajeCellRenderer());
		DefaultListModel<Mensaje> modelo = new DefaultListModel<Mensaje>();
		
		List<Mensaje> mensajes = AppChat.getUnicaInstancia().obtenerChatsRecientesUsuario();						
		//Conversión manual de List<Mensaje> a DefaultListModel<Mensaje> 
		for(Mensaje mensaje: mensajes) {
			modelo.addElement(mensaje);
		}
		lista.setModel(modelo);
		
		panelMensajes.add(new JScrollPane(lista), BorderLayout.NORTH);
		
		JPanel panelChatActual = new JPanel();
		contentPane.add(panelChatActual, BorderLayout.CENTER);
		panelChatActual.setLayout(new BorderLayout(0, 0));
		
		JPanel enviar = new JPanel();
		panelChatActual.add(enviar, BorderLayout.SOUTH);
		enviar.setLayout(new BoxLayout(enviar, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		enviar.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Enviar");
		enviar.add(btnNewButton_4);
		
		JPanel chat = new JPanel();
		panelChatActual.add(chat, BorderLayout.CENTER);
		chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
		chat.setSize(400,700);
		chat.setMinimumSize(new Dimension(400,700));
		chat.setMaximumSize(new Dimension(400,700));
		chat.setPreferredSize(new Dimension(400,700));
		//Appchat.obtenerMensajesChat(usuario)
		
		BubbleText burbuja;
		burbuja=new BubbleText(chat,"Hola grupo!!", Color.GREEN, "J.Ramón", BubbleText.SENT);
		chat.add(burbuja);
		
		BubbleText burbuja2;
		burbuja2=new BubbleText(chat,
		"Hola, ¿Está seguro de que la burbuja usa varias lineas si es necesario?",
		Color.LIGHT_GRAY, "Alumno", BubbleText.RECEIVED);
		chat.add(burbuja2);
		
		
	}

}
