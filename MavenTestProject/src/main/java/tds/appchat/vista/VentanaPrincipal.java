package tds.appchat.vista;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
import javax.swing.ComboBoxEditor;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.Component;
import java.awt.Dimension;

import javax.security.auth.callback.TextOutputCallback;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.FlowLayout;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldEnviar;
	
	
	
	private Usuario usuarioSeleccionado = null;
	private Grupo grupoSeleccionado = null;
	private boolean esGrupo = false;
	
	private JPanel chat;
	private JLabel lblNombreUsuarioChat;
	private JPanel panel_InfoUsuario;
	private JButton btnAgregarusuariodesconocido;
	private JLabel icononogramaGrupo;
	//Modelo y lista para los mensajes
	JList<Mensaje> lista;
	DefaultListModel<Mensaje> modelo;
		

	
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
		if (m.isMensajeGrupo())
			abrirChat(m.getGrupo());
		else
			abrirChat(AppChat.getUnicaInstancia().obtenerUsuarioDesdeMensaje(m));
	}
	
	private void abrirChat(Usuario u) {
		lblNombreUsuarioChat.setText(u.getTelefono());
		usuarioSeleccionado = u;
		grupoSeleccionado = null;
		esGrupo = false;
		if (AppChat.getUnicaInstancia().esUsuarioContacto(u)) {
			lblNombreUsuarioChat.setText(AppChat.getUnicaInstancia().obtenerContactoUsuario(u).getNombre());
			btnAgregarusuariodesconocido.setVisible(false);
		} else {
			btnAgregarusuariodesconocido.setVisible(true);
		}
			
		abrirConversacion(AppChat.getUnicaInstancia().obtenerConversacion(u));
	}
	/**
	 * Al igual que la obtención de una conversacion mediante un teléfono
	 * se debe poder abrir el chat cuando se selecciona un contacto, sea 
	 * 
	 * @param Contacto
	 */
	private void abrirChat(Contacto c){
		btnAgregarusuariodesconocido.setVisible(false);
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
			Color color = AppChat.getUnicaInstancia().esUsuarioEmisor(m) ? Color.GREEN: Color.LIGHT_GRAY;
			BubbleText b;
			if (m.esTextoEmoticono()) {
				b = new BubbleText(chat, m.getEmoticono() , color, m.getEmisor().getNombre() , tipo, 24); 
			} else {
				b = new BubbleText(chat, m.getTexto() , color, m.getEmisor().getNombre() , tipo); 
			}
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
		setBounds(100, 100, 905, 612);
		BubbleText.noZoom();
		getContentPane().setBackground(new Color(255, 255, 255));
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(Color.WHITE);
		contentPane.add(panelNorte, BorderLayout.NORTH);
	
		JComboBox<Contacto> comboBox = new JComboBox<Contacto>();
		//Para que se pueda editar.
		comboBox.setEditable(true);
		comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		Contacto [] contactosArray = AppChat.getUnicaInstancia().contactosUsuarioActualArray();
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		comboBox.setModel(new DefaultComboBoxModel<Contacto>(contactosArray));
		panelNorte.add(comboBox);
		
		BotonGeneral btnEnviarSup = new BotonGeneral("Enviar");
		btnEnviarSup.setForeground(SystemColor.activeCaptionText);
		
		btnEnviarSup.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/enviar-correo-pressed.png")));
		
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
		
		BotonGeneral btnBuscar = new BotonGeneral("Buscar");
		btnBuscar.setForeground(SystemColor.activeCaptionText);
		btnBuscar.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/buscar.png")));
		btnBuscar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaBuscar ventanaBuscar = new VentanaBuscar();
				ventanaBuscar.setVisible(true);
			}
		});
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelNorte.add(horizontalStrut);
		
		panelNorte.add(btnBuscar);
		
		BotonGeneral btnContactos = new BotonGeneral("Contactos");
		btnContactos.setForeground(SystemColor.activeCaptionText);
		btnContactos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaContactos vContactos = new VentanaContactos();
				vContactos.setVisible(true);
				dispose();
			}
		});
		btnContactos.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/personas.png")));
		panelNorte.add(btnContactos);
		
		JButton btnPremium = new BotonGeneral("Premium");
		btnPremium.setForeground(SystemColor.activeCaptionText);
		
		
		btnPremium.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				//Se abre la vetnana premium
				//TODO: Hacer que esta ventana se quede dormida
				VentanaPremium v = new VentanaPremium();
				v.setVisible(true);
			}
		});
		
		panelNorte.add(btnPremium);
		btnPremium.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/moneda.png")));
		
		BotonGeneral btnAjustes = new BotonGeneral("Log Out");
		btnAjustes.setForeground(SystemColor.activeCaptionText);
		btnAjustes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AppChat.getUnicaInstancia().logoutUsuario();
				VentanaLogin v = new VentanaLogin();
				v.setVisible(true);
				dispose();
			}
		});
		btnAjustes.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/cerrar-sesion.png")));

		panelNorte.add(btnAjustes);
		
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
		panelMensajes.setPreferredSize(new Dimension(250, 100));
		lista = new JList<Mensaje>();
		lista.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(new BorderLayout(0,0));
		
		panelChatActual.add(panel, BorderLayout.NORTH);
		
		//JButton btnExportarPDF = new JButton();
		BotonImagen btnExportarPDF = new BotonImagen(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/pdf.png")));
		panel.add(btnExportarPDF, BorderLayout.EAST);
		
		panel_InfoUsuario = new JPanel();
		panel_InfoUsuario.setBackground(SystemColor.text);
		panel.add(panel_InfoUsuario, BorderLayout.WEST);
		panel_InfoUsuario.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAgregarusuariodesconocido = new BotonImagen(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/agregar_usuario.png")));
		btnAgregarusuariodesconocido.addActionListener(new ActionListener() {
			
			@Override
				public void actionPerformed(ActionEvent e) {
					if (esGrupo || usuarioSeleccionado == null)
						return;
					VentanaRegistrarContacto ventana = new VentanaRegistrarContacto(usuarioSeleccionado.getTelefono());
					
					ventana.setVisible(true);
					//Una vez se haya añadido un grupo, cuando se cierre la ventana de añadir contactos, se
					//actualizará la lista de contactos.
					ventana.addWindowListener(new WindowAdapter() {
	                    public void windowClosing(WindowEvent we) {
	                    	actualizarListaMensajes();
	                		ventana.setVisible(false);
	                    }
	                });
				}
		});
		
		lblNombreUsuarioChat = new JLabel();
		panel_InfoUsuario.add(lblNombreUsuarioChat);
		panel_InfoUsuario.add(btnAgregarusuariodesconocido);
		//panel_InfoUsuario.add(icononogramaGrupo);
		
		//icononogramaGrupo.setVisible(false);
		btnAgregarusuariodesconocido.setVisible(false);
		//icononogramaGrupo.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/personas-128")));
		
		btnExportarPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Si no es premium
				if (!AppChat.getUnicaInstancia().esUsuarioActualPremium())
					JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se puede acceder a esto sin ser Premium", "Info",
							JOptionPane.ERROR_MESSAGE);
				else {
					List<Mensaje> mensajes;
					String nombre;
					if (esGrupo) {
						nombre = grupoSeleccionado.getNombre();
						mensajes = AppChat.getUnicaInstancia().obtenerConversacionGrupo(grupoSeleccionado);
					} else if (usuarioSeleccionado != null) {
						mensajes = AppChat.getUnicaInstancia().obtenerConversacion(usuarioSeleccionado);
						nombre = (AppChat.getUnicaInstancia().esTelefonoContacto(usuarioSeleccionado.getTelefono()) ?
								AppChat.getUnicaInstancia().obtenerNombreContactoDesdeTelefono(usuarioSeleccionado.getTelefono())
								:usuarioSeleccionado.getTelefono());
					} else {
						return;
					}
					VentanaPDF v = new VentanaPDF(mensajes, nombre);
					v.setVisible(true);
				}
					
			}
		});
		
		///////////////////
		////   Panel   ////
		///////////////////
		
		chat = new JPanel();
		chat.setBackground(SystemColor.text);
		
		JScrollPane scrollPane = new JScrollPane(chat);
		panelChatActual.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
		chat.setSize(400,700);
		chat.setMinimumSize(new Dimension(400,700));
		chat.setMaximumSize(new Dimension(400,700));
		chat.setPreferredSize(new Dimension(400,700));
		
		JPanel enviar = new JPanel();
		panelChatActual.add(enviar, BorderLayout.SOUTH);
		enviar.setLayout(new BoxLayout(enviar, BoxLayout.X_AXIS));
				

		//JButton btnEmoticono = new JButton(":)");
		//BotonImagen btnEmoticono = new BotonImagen(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/contento.png")));
		//btnEmoticono.setPressedIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/contento-pressed.png")));
		//enviar.add(btnEmoticono);
		
		BotonImagen btnEmoticono = new BotonImagen(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/contento.png")));
		btnEmoticono.setPressedIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/contento-pressed.png")));
		enviar.add(btnEmoticono);
		
		textFieldEnviar = new JTextField();
		enviar.add(textFieldEnviar);
		textFieldEnviar.setColumns(10);
				
        JPopupMenu emojiMenu = new JPopupMenu();
        emojiMenu.setLayout(new GridLayout(2, 4, 5, 5)); 
        
        
        for (int i = 0; i < BubbleText.MAXICONO; i++) { 
            ImageIcon emojiIcon = BubbleText.getEmoji(i); 
            final int emojiId = i;
            JButton emojiButtonMenu = new JButton(emojiIcon);
            emojiButtonMenu.setFocusable(false);
            emojiButtonMenu.setBorder(BorderFactory.createEmptyBorder());
            emojiButtonMenu.setContentAreaFilled(false);

            // Acción al seleccionar un emoticono
            emojiButtonMenu.addActionListener(e -> {
            	if (!esGrupo && usuarioSeleccionado == null)
            		return;
            	
            	if (esGrupo)
            	AppChat.getUnicaInstancia().enviarMensajeGrupo(grupoSeleccionado, emojiId);
            		else
            	AppChat.getUnicaInstancia().enviarMensaje(usuarioSeleccionado, emojiId);
                
            	BubbleText emojiBubble = new BubbleText(chat, emojiId, Color.GREEN, AppChat.getUnicaInstancia().getNombreUsuarioActual(), BubbleText.SENT, 24);
                chat.add(emojiBubble); // Añadir la burbuja al chat
                chat.revalidate();
                chat.repaint();
                emojiMenu.setVisible(false); // Cerrar el menú
                
            });
            emojiMenu.add(emojiButtonMenu);
        }
        
        //Cuando se hace click que se abra
        btnEmoticono.addActionListener(e -> {
            emojiMenu.show(btnEmoticono, btnEmoticono.getWidth() / 2, btnEmoticono.getHeight() / 2);
        });
        
        BotonImagen btnEnviar = new BotonImagen(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/enviar-correo.png")));
        btnEnviar.setPressedIcon(new ImageIcon(VentanaPrincipal.class.getResource("/imagenes/enviar-correo-pressed.png")));
		//JButton btnEnviar = new JButton("Enviar");
		
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
					BubbleText b;
					//Si es un grupo lo que está abierto
					if(esGrupo)
						AppChat.getUnicaInstancia().enviarMensajeGrupo(grupoSeleccionado, texto);
					else if (usuarioSeleccionado != null)
						AppChat.getUnicaInstancia().enviarMensaje(usuarioSeleccionado, texto);
					else
						return;
					
					//Se crea 
					b = new BubbleText(chat, texto , Color.GREEN, AppChat.getUnicaInstancia().getNombreUsuarioActual() , BubbleText.SENT); 
					chat.add(b);
					//Se limpia 
					textFieldEnviar.setText("");
					//Se actualiza la lista de mensajes.
					actualizarListaMensajes();
				}
			}
		});
		
		 
		enviar.add(btnEnviar);		
	}
	
	

}