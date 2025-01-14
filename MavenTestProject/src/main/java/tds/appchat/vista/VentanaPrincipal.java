package tds.appchat.vista;

import java.awt.EventQueue;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tds.BubbleText;
//import tds.BubbleText;
import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
import tds.appchat.vista.MensajeCellRenderer;
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

import javax.security.auth.callback.TextOutputCallback;
import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldEnviar;

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
	
	//Contacto que estará por pantalla
	private Contacto contactoSeleccionado = null;
	private String telefonoSeleccionado = null;
	//Panel en el que irán los mensajes
	private JPanel chat;
	
	//Modelo y lista para los mensajes
	JList<Mensaje> lista;
	DefaultListModel<Mensaje> modelo;
		
	/**
	 * Toma como parámetro un Mensaje del cell renderer
	 * y se toman todos los mensajes de la conversacion y se 
	 * ponen como bubble test.
	 * 
	 * No es necesario especificar si es grupo
	 * 
	 * @param mensaje
	 */
	private void abrirChat(List<Mensaje> mensajes) {
		chat.removeAll();
		
		for (Mensaje m : mensajes) {
			//Al ser un grupo, solo habrá mensajes enviados
			int tipo = AppChat.getUnicaInstancia().esMensajeEmisor(m) ? BubbleText.SENT : BubbleText.RECEIVED;
			Color color = AppChat.getUnicaInstancia().esMensajeEmisor(m) ? Color.GREEN: Color.GRAY;
			
			BubbleText b = new BubbleText(chat, m.getTexto() , color, m.getTlfEmisor() , tipo); 
			chat.add(b);
		}
		chat.revalidate();
        chat.repaint();
	}
	
	public void actualizarListaMensajes() {
		modelo = new DefaultListModel<Mensaje>();
		//Conversión manual de List<Mensaje> a DefaultListModel<Mensaje> 
		for(Mensaje mensaje: AppChat.getUnicaInstancia().obtenerChatsRecientesUsuario()) {
			modelo.addElement(mensaje);
		}
		lista.setModel(modelo);
		lista.setCellRenderer(new MensajeCellRenderer());
	}
	
	public void ponerMensajePrimero() {
		
	}
	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 854, 577);
		BubbleText.noZoom();
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.X_AXIS));
	
		JComboBox<Contacto> comboBox = new JComboBox<Contacto>();
		//Para que se pueda editar.
		comboBox.setEditable(true);
		
		Contacto [] contactosArray = AppChat.getUnicaInstancia().contactosUsuarioActualArray();
		comboBox.setModel(new DefaultComboBoxModel<Contacto>(contactosArray));
		panelNorte.add(comboBox);
		
		JButton btnEnviarSup = new JButton("Enviar");
		btnEnviarSup.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/enviar-mensaje.png")));
		
		btnEnviarSup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = comboBox.getSelectedItem(); 
				if (obj != null) {
					//Se supondrá que lo que se escriba será un teléfono
					if (obj instanceof String)
						//
						if (AppChat.getUnicaInstancia().existeTelefono((String) obj)) {
							abrirChat(AppChat.getUnicaInstancia().obtenerConversacionDesdeTelefono((String) obj));
							telefonoSeleccionado = (String) obj;
							contactoSeleccionado = null;
						}
						else {
							JOptionPane.showMessageDialog(VentanaPrincipal.this, "El numero insertado no está en la base de datos", "Info",
									JOptionPane.INFORMATION_MESSAGE);
						}
						//Es necesario hacer que diga que el teléfono no existe
					if (obj instanceof Contacto) {
						contactoSeleccionado = (Contacto) obj;
						abrirChat(AppChat.getUnicaInstancia().obtenerConversacionDesdeContacto((Contacto) obj));
						
						telefonoSeleccionado = (contactoSeleccionado instanceof ContactoIndividual) ? 
								((ContactoIndividual) contactoSeleccionado).getTelefono() :
								null;
						
						
					}
				//Se abre el char para este contacto
				}
				//En caso de que no haya nada seleccionado no se hará nada
			}
		});
		panelNorte.add(btnEnviarSup);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/buscar.png")));
		btnBuscar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaBuscar ventanaBuscar = new VentanaBuscar();
				ventanaBuscar.setVisible(true);
			}
		});
		
		panelNorte.add(btnBuscar);
		
		JButton btnContactos = new JButton("Contactos");
		btnContactos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaContactos vContactos = new VentanaContactos();
				vContactos.setVisible(true);
				
			}
		});
		btnContactos.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/personas.png")));
		panelNorte.add(btnContactos);
		
		JButton btnPremium = new JButton("Premium");
		btnPremium.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				//Se abre la vetnana premium
				//TODO: Hacer que esta ventana se quede dormida
				VentanaPremium v = new VentanaPremium();
				v.setVisible(true);
			}
		});
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelNorte.add(horizontalGlue_1);
		panelNorte.add(btnPremium);
		
		JButton btnAjustes = new JButton("Log Out");
		btnAjustes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppChat.getUnicaInstancia().logoutUsuario();
				VentanaLogin v = new VentanaLogin();
				v.setVisible(true);
				dispose();
			}
		});
		panelNorte.add(btnAjustes);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelNorte.add(horizontalGlue);
		
		JLabel lblNewLabel = new JLabel(AppChat.getUnicaInstancia().getNombreUsuarioActual());
		panelNorte.add(lblNewLabel);
		
		//TODO: Hacer que en la esquina salga la foto de perfil
		//Crear una funcion que devuelva una Image y sea AppChat.getUnicaInstancia.getFotoPerfilUsuarioActual()
		
		JLabel lblIcono = new JLabel("");
		JButton btnIcono = new JButton();
		btnIcono.setIcon(new ImageIcon(AppChat.getUnicaInstancia().getImagenUsuarioActual()));
        btnIcono.setContentAreaFilled(false);  // Eliminar el fondo
        btnIcono.setBorderPainted(false); 
		btnIcono.setFocusable(false);
		panelNorte.add(btnIcono);
		//lblIcono.setIcon(new ImageIcon(AppChat.getUnicaInstancia().getUnicaInstancia().getImagenUsuarioActual()));
		//panelNorte.add(lblIcono);
		
		JPanel panelMensajes = new JPanel();
		contentPane.add(panelMensajes, BorderLayout.WEST);
		panelMensajes.setLayout(new BorderLayout(0, 0));
		
		lista = new JList<Mensaje>();
		lista.setCellRenderer(new MensajeCellRenderer());
		modelo = new DefaultListModel<Mensaje>();
	
		//Conversión manual de List<Mensaje> a DefaultListModel<Mensaje> 
		for(Mensaje mensaje: AppChat.getUnicaInstancia().obtenerChatsRecientesUsuario()) {
			modelo.addElement(mensaje);
		}
		
		lista.setModel(modelo);
		lista.setCellRenderer(new MensajeCellRenderer());
		
		lista.addListSelectionListener(new ListSelectionListener() {
			/**Cuando se selecciona un valor de la lista este tiene que mostrar
			 * sus mensajes en el panel pantalla, para ello llama a la función abrir
			 * chat
			 * 
			 */
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Mensaje m = lista.getSelectedValue();
				//TODO: Puede que este if sobre
				if (m != null){
					abrirChat(AppChat.getUnicaInstancia().obtenerConversacionDesdeMensaje(m));
					telefonoSeleccionado = AppChat.getUnicaInstancia().esMensajeEmisor(m) ? m.getTlfReceptor() : m.getTlfEmisor();
					contactoSeleccionado = AppChat.getUnicaInstancia().obtenerContactoDesdeTelefono(telefonoSeleccionado);
				}
				
			}
		});
		
		panelMensajes.add(new JScrollPane(lista), BorderLayout.CENTER);
		
		JPanel panelChatActual = new JPanel();
		contentPane.add(panelChatActual, BorderLayout.CENTER);
		panelChatActual.setLayout(new BorderLayout(0, 0));
		
		JPanel enviar = new JPanel();
		panelChatActual.add(enviar, BorderLayout.SOUTH);
		enviar.setLayout(new BoxLayout(enviar, BoxLayout.X_AXIS));
		
		textFieldEnviar = new JTextField();
		enviar.add(textFieldEnviar);
		textFieldEnviar.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		
		//AppChat.getUnicaInstancia().enviarMensaje(null, textFieldEnviar.getText());
		
		btnEnviar.addActionListener(new ActionListener() {
			@Override
			/**
			 * Cuando se envía un mensaje, se envía al controlador, se añade el bubble text 
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
				//Para envíar un mesaje será necesario: obtener texto del textField
				//Que haya un contacto seleccionado
				String texto = textFieldEnviar.getText();
				if (!texto.isEmpty() && contactoSeleccionado != null) {
					//Controlador enviará el mensaje
					//AppChat.getUnicaInstancia().enviarMensaje(contactoSeleccionado, getName());
					AppChat.getUnicaInstancia().enviarMensaje(telefonoSeleccionado, texto);
					//Se añadirá el mensaje a los contactos
					BubbleText b = new BubbleText(chat, texto , Color.GREEN, "Emisor", BubbleText.SENT); 
					chat.add(b);
					//Se debería sustituir el mensaje actual para este
					
				}
			}
		});
		enviar.add(btnEnviar);
		
		JScrollPane scrollPane = new JScrollPane();
		panelChatActual.add(scrollPane, BorderLayout.CENTER);
		
		chat = new JPanel();
		panelChatActual.add(chat, BorderLayout.CENTER);
		
		chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
		chat.setSize(400,700);
		chat.setMinimumSize(new Dimension(400,700));
		chat.setMaximumSize(new Dimension(400,700));
		chat.setPreferredSize(new Dimension(400,700));
//		panelConversacionActual = new JPanel();
//		panelChatActual.add(panelConversacionActual, BorderLayout.CENTER);
//		
//		panelConversacionActual.setLayout(new BoxLayout(panelChatActual,BoxLayout.Y_AXIS));
//		panelConversacionActual.setSize(400,700);
//		panelConversacionActual.setMinimumSize(new Dimension(400,700));
//		panelConversacionActual.setMaximumSize(new Dimension(400,700));
//		panelConversacionActual.setPreferredSize(new Dimension(400,700));
//		//Appchat.obtenerMensajesChat(usuario)
//		
//		
		
	}

}