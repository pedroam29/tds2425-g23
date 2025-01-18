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
import tds.appchat.modelo.Mensaje3;
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
	
	///
	// Usuario de la conversacion
	///
//	private Contacto contactoSeleccionado = null;
//	private String telefonoSeleccionado = null;
//	private boolean contactoActualConocido = false;
	
	private Usuario usuarioSeleccionado = null;
	private Grupo grupoSeleccionado = null;
	private boolean esGrupo = false;
	////
	// Elementos de modificacion dinámica del chat
	////
	private JPanel chat;
	JLabel lblNombreUsuarioChat;
	
	
	//Modelo y lista para los mensajes
	JList<Mensaje> lista;
	DefaultListModel<Mensaje> modelo;
		

//	private void actualizarInformacionContactoActual(String telefono) {
////		if(AppChat.getUnicaInstancia().existeTelefono(telefono)) {
////			actualizarInformacionContactoActual(AppChat.getUnicaInstancia().obtenerContactoDesdeTelefono(telefono));
////			return;
////		}
//		//Se supondrá que si se ha llamado a esta función es porque no se encuentra en contactos
//		telefonoSeleccionado = telefono;
//		contactoSeleccionado = null;
//		contactoActualConocido = false;
//	}
//	private void actualizarInformacionContactoActual(Contacto c) {
//		contactoSeleccionado = c;
//		telefonoSeleccionado = (c instanceof ContactoIndividual) ? ((ContactoIndividual) c).getTelefono() : "";
//		contactoActualConocido = true;
//	}
	
	/**
	 * A partir de un mensaje se abre una conversacion en la ventana.
	 * Una conversación consta de la parte norte y center del panel
	 * de mensajería.
	 * 
	 * La responsabilidad de esto es actualizar el nombre y foto de perfil
	 * de la conversacion que se acaba de abrir y que se muestren los
	 * mensajes de la conversación.
	 * 
	 * En el panel central aparecerán los mensajes de la conversación
	 */
	private void abrirChat(Mensaje m){
		//Se obtiene el teléfono del otro participante de la conexión.
		abrirChat(AppChat.getUnicaInstancia().obtenerUsuarioDesdeMensaje(m));
	}
	
	private void abrirChat(Usuario u) {
		lblNombreUsuarioChat.setText(u.getTelefono());
		usuarioSeleccionado = u;
		grupoSeleccionado = null;
		esGrupo = false;
		if (AppChat.getUnicaInstancia().esUsuarioContacto(u))
			lblNombreUsuarioChat.setText(AppChat.getUnicaInstancia().obtenerContactoUsuario(u).getNombre());
		
		abrirConversacion(AppChat.getUnicaInstancia().obtenerConversacion(u));
	}
	/**
	 * Al igual que la obtención de una conversacion mediante un teléfono
	 * se debe poder abrir el chat cuando se selecciona un contacto, sea 
	 * 
	 * @param Contacto
	 */
	private void abrirChat(Contacto c){
		lblNombreUsuarioChat.setText(c.getNombre());
		if (c instanceof Grupo) {
			grupoSeleccionado = (Grupo) c;
			usuarioSeleccionado = null;
			esGrupo = true;
			abrirConversacion(AppChat.getUnicaInstancia().obtenerConversacionGrupo((Grupo) c));
		} else {
			grupoSeleccionado = null;
			usuarioSeleccionado = ((ContactoIndividual) c).getUsuario();
			abrirConversacion(AppChat.getUnicaInstancia().obtenerConversacion(((ContactoIndividual) c).getUsuario()));
		
		}
	}
	
	/**
	 * Del mismo modo se debe poder abrir un chat que se tiene con un número
	 * de teléfono, este puede o puede no estar registrado
	 * 
	 * @param telefono
	 */
//	private void abrirChat(String telefono) {
//		if (AppChat.getUnicaInstancia().esTelefonoContacto(telefono)) {
//			abrirChat(AppChat.getUnicaInstancia().obtenerContactoDesdeTelefono(telefono));
//		}
//	
//		lblNombreUsuarioChat.setText(telefono);
//		abrirConversacion(AppChat.getUnicaInstancia().obtenerConversacionDesdeTelefono(telefono));
//		actualizarInformacionContactoActual(telefono);
//	}
	
	/**
	 * Toma como parámetro una lista de mensaje y se insertan
	 * como bubble test en el panel del chat
	 *  
	 * @param mensaje
	 */
	private void abrirConversacion(List<Mensaje> mensajes) {
		//Se borra el chat que está actual
		chat.removeAll();
		for (Mensaje m : mensajes) {
			
			//Se comprueba si se es emisor o receptor, en ese casos se pondrá de un color u otro
			int tipo = AppChat.getUnicaInstancia().esUsuarioEmisor(m) ? BubbleText.SENT : BubbleText.RECEIVED;
			Color color = AppChat.getUnicaInstancia().esUsuarioEmisor(m) ? Color.GREEN: Color.GRAY;
			
			//TODO: Hacer que salga el nombre de contacto o el número de teléfono según sea necesario
			BubbleText b = new BubbleText(chat, m.getTexto() , color, m.getEmisor().getNombre() , tipo); 
			chat.add(b);
		}
		//Se vuelve a pintar
		chat.revalidate();
        chat.repaint();
	}
	
	/**
	 * Se actualiza el modelo de mensajes:
	 */
	public void actualizarListaMensajes() {
		modelo = new DefaultListModel<Mensaje>();
		//Conversión manual de List<Mensaje> a DefaultListModel<Mensaje> 
		for(Mensaje mensaje: AppChat.getUnicaInstancia().obtenerChatsRecientes())
			modelo.addElement(mensaje);
		lista.setModel(modelo);
		//lista.setCellRenderer(new MensajeCellRenderer());
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
				//Se selecciona el objeto del combobox
				Object obj = comboBox.getSelectedItem(); 
				if (obj != null) {
					//Se supondrá que lo que se escriba será un teléfono
					if (obj instanceof String)
						//En caso de que sea un String, se 
						if (AppChat.getUnicaInstancia().existeTelefono((String) obj))
							abrirChat(AppChat.getUnicaInstancia().obtenerUsuarioDesdeTelefono((String) obj));
						else {
							JOptionPane.showMessageDialog(VentanaPrincipal.this, "El numero insertado no está en la base de datos", "Info",
									JOptionPane.INFORMATION_MESSAGE);
						}
						//Es necesario hacer que diga que el teléfono no existe
					else if (obj instanceof Contacto) 
						//Se abre el chat para el contacto seleccionado
						abrirChat((Contacto) obj);
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
		
		//Crear una funcion que devuelva una Image y sea AppChat.getUnicaInstancia.getFotoPerfilUsuarioActual()
		
		//JLabel lblIcono = new JLabel("");
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
		
//		modelo = new DefaultListModel<Mensaje>();
//		//Conversión manual de List<Mensaje> a DefaultListModel<Mensaje> 
//		for(Mensaje mensaje: AppChat.getUnicaInstancia().obtenerChatsRecientesUsuario())
//			modelo.addElement(mensaje);
//		lista.setModel(modelo);
		
		actualizarListaMensajes();
		
		lista.addListSelectionListener(new ListSelectionListener() {
			/**
			 * Cuando se selecciona un valor de la lista este tiene que mostrar
			 * sus mensajes en el panel pantalla, para ello llama a la función abrir
			 * chat
			 * 
			 */
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Mensaje m = lista.getSelectedValue();
				System.out.println("Mensaje obtenido de la lista: \n" + m);
				//TODO: Puede que este if sobre
				if (m != null)
					abrirChat(m);
			}
		});
		
		panelMensajes.add(new JScrollPane(lista), BorderLayout.CENTER);
		
		JPanel panelChatActual = new JPanel();
		contentPane.add(panelChatActual, BorderLayout.CENTER);
		panelChatActual.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panelChatActual.add(panel, BorderLayout.NORTH);


		
		lblNombreUsuarioChat = new JLabel();
		panel.add(lblNombreUsuarioChat);
		
		///////////////////
		////   Panel   ////
		///////////////////
		
		chat = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane(chat);
		panelChatActual.add(scrollPane, BorderLayout.CENTER);
		
		
		chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
		chat.setSize(400,700);
		chat.setMinimumSize(new Dimension(400,700));
		chat.setMaximumSize(new Dimension(400,700));
		chat.setPreferredSize(new Dimension(400,700));
		
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
				//Que haya una conversacion abierta, de esta manera 
				String texto = textFieldEnviar.getText();
				if (!texto.isEmpty()) {
					//Se comprueba que hay algo en la línea de texto
					//En este caso hay que hacer un envío, si es a un teléfono
					//o a un grupo.
					//Una forma es saber si es conocido el contacto actual, de no serlo:
					//TODO: Que se borre el textfield
					BubbleText b;
					//Si es un grupo lo que está abierto
					if(esGrupo) {
						AppChat.getUnicaInstancia().enviarMensajeGrupo(grupoSeleccionado, texto);
					} else {
						AppChat.getUnicaInstancia().enviarMensaje(usuarioSeleccionado, texto);
					}
//					
//					if (!contactoActualConocido) {
//						//Se envía por teléfono
//						//TODO: eliminar sysout
//						System.out.println("El telefono seleccionado es: " + telefonoSeleccionado);
//						AppChat.getUnicaInstancia().enviarMensaje(telefonoSeleccionado, texto);
//					} else {
//						//De otra manera se enviará por contacto
//						AppChat.getUnicaInstancia().enviarMensaje(contactoSeleccionado.get, texto);
//					}
					//AppChat.getUnicaInstancia().enviarMensaje(contactoSeleccionado, getName());
					//Se añadirá el mensaje a los contactos
					b = new BubbleText(chat, texto , Color.GREEN, AppChat.getUnicaInstancia().getNombreUsuarioActual() , BubbleText.SENT); 
					chat.add(b);
					//Se debería sustituir el mensaje actual para este
					//Si esta no es el primero de la lista, que se cambie.
					actualizarListaMensajes();
				}
			}
		});
		enviar.add(btnEnviar);
		
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