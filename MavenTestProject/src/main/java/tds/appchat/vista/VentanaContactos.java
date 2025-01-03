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
import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.ContactoIndividual;
import tds.appchat.modelo.Grupo;
import tds.appchat.modelo.Mensaje;
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
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JViewport;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class VentanaContactos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel gridBagLayoutVentana;
	private JTextField textField;
	private JTextField txtListaDeContactos;
	
	//Grupos
	private Grupo grupoSeleccionado = null;
	private JList<ContactoIndividual> listaContactosGrupo = null;
	private DefaultListModel<ContactoIndividual> modelContactosGrupo = null;
	
	//Lista contactos
	private DefaultListModel<Contacto> modeloContactos;
	private JList<Contacto> listaContactos;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaContactos frame = new VentanaContactos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Función que se invoca cuando hay un cambio en los contactos,
	 * se creará el DefaultListModel para cada actualizacion
	 */
	private DefaultListModel<Contacto> actualizarListaContactos()
	{
		DefaultListModel<Contacto> modelo = new DefaultListModel<Contacto>();
		List<Contacto> contactos = AppChat.getUnicaInstancia().contactosUsuarioActual();
		for(Contacto contacto : contactos) {
			modelo.addElement(contacto);
		}
		return modelo;
	}
	/**
	 * Create the frame.
	 */
	public VentanaContactos() {		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 679, 486);
		gridBagLayoutVentana = new JPanel();
		gridBagLayoutVentana.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(gridBagLayoutVentana);
		GridBagLayout gbl_gridBagLayoutVentana = new GridBagLayout();
		gbl_gridBagLayoutVentana.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
		gbl_gridBagLayoutVentana.rowHeights = new int[]{10, 20, 0, 0, 5, 0, 5, 0};
		gbl_gridBagLayoutVentana.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_gridBagLayoutVentana.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayoutVentana.setLayout(gbl_gridBagLayoutVentana);
		
		JLabel lblListaDeContactos = new JLabel("Lista de contactos");
		GridBagConstraints gbc_lblListaDeContactos = new GridBagConstraints();
		gbc_lblListaDeContactos.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaDeContactos.gridx = 1;
		gbc_lblListaDeContactos.gridy = 1;
		gridBagLayoutVentana.add(lblListaDeContactos, gbc_lblListaDeContactos);
		
		JLabel lblGrupo = new JLabel("Grupo: ");
		
		GridBagConstraints gbc_lblGrupo = new GridBagConstraints();
		gbc_lblGrupo.insets = new Insets(0, 0, 5, 5);
		gbc_lblGrupo.gridx = 3;
		gbc_lblGrupo.gridy = 1;
		gridBagLayoutVentana.add(lblGrupo, gbc_lblGrupo);
		
		
		JScrollPane scrollPaneContactos = new JScrollPane();
		GridBagConstraints gbc_scrollPanelContactos = new GridBagConstraints();
		gbc_scrollPanelContactos.gridheight = 2;
		gbc_scrollPanelContactos.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPanelContactos.fill = GridBagConstraints.BOTH;
		gbc_scrollPanelContactos.gridx = 1;
		gbc_scrollPanelContactos.gridy = 2;
		gridBagLayoutVentana.add(scrollPaneContactos, gbc_scrollPanelContactos);
		
		//Inizalización de las listas
		JList<Contacto> listaContactos = new JList<Contacto>();
		listaContactos.setCellRenderer(new ContactoCellRenderer());
		modeloContactos = new DefaultListModel<Contacto>();
		//Función para actualizarListaContactos
		listaContactos.setModel(actualizarListaContactos());
		scrollPaneContactos.setViewportView(listaContactos);
		
		
		JScrollPane scrollPaneGrupos = new JScrollPane();
		GridBagConstraints gbc_scrollPanellGrupos = new GridBagConstraints();
		gbc_scrollPanellGrupos.gridheight = 2;
		gbc_scrollPanellGrupos.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPanellGrupos.fill = GridBagConstraints.BOTH;
		gbc_scrollPanellGrupos.gridx = 3;
		gbc_scrollPanellGrupos.gridy = 2;
		gridBagLayoutVentana.add(scrollPaneGrupos, gbc_scrollPanellGrupos);
		
		/**
		 * En esta función, cuando se seleccione un elemento de la jlist
		 * de contactos, si es un grupo, se abrirá en el otro jpanel la 
		 * lista con todos los grupos.
		 */
		listaContactos.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listaContactos.getSelectedValue() instanceof Grupo)
				{
					grupoSeleccionado = (Grupo) listaContactos.getSelectedValue();
					lblGrupo.setText(grupoSeleccionado.getNombre());
					
					List<ContactoIndividual> contactosGrupo = grupoSeleccionado.getMiembros();
					modelContactosGrupo = new DefaultListModel<ContactoIndividual>();
					
					for (ContactoIndividual c : contactosGrupo)
						modelContactosGrupo.addElement(c);
					
					listaContactosGrupo = new JList<ContactoIndividual>();
					listaContactosGrupo.setModel(modelContactosGrupo);
					listaContactosGrupo.setCellRenderer(new ContactoCellRenderer());
					//listaContactosGrupo = new JList<>(contactosGrupo.toArray(new ContactoIndividual[0]));
					//listaContactosGrupo.setCellRenderer(new ContactoCellRenderer());
					scrollPaneGrupos.setViewportView(listaContactosGrupo);
				}
			}
		});
		
		//Para que funcione el bloque de código del action listener
//		final Grupo grupoFinal = grupoSeleccionado;
//		final JList<ContactoIndividual> listaContactosGrupoActionListener = listaContactosGrupo;
		
		JButton botonAdd = new JButton(">>");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 2;
		gridBagLayoutVentana.add(botonAdd, gbc_button);

		botonAdd.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	//Es necesario que haya un grupo abierto
		        if (grupoSeleccionado != null && listaContactos.getSelectedValue() != null 
		        	&& listaContactos.getSelectedValue() instanceof ContactoIndividual) {
		        	
		        	ContactoIndividual contactoSeleccionado = (ContactoIndividual) listaContactos.getSelectedValue();
	                // Se utiliza la devolución de la función addMiembro
		        	if(!AppChat.getUnicaInstancia().addContactoGrupo(grupoSeleccionado, contactoSeleccionado))
		        		JOptionPane.showMessageDialog(VentanaContactos.this, "No se ha podido añadir el contacto al grupo");
	            	modelContactosGrupo.addElement(contactoSeleccionado);
		        }
		    }
		});
		
		
		JButton botonEliminar = new JButton("<<");
		GridBagConstraints gbc_botonEliminar = new GridBagConstraints();
		gbc_botonEliminar.insets = new Insets(0, 0, 5, 5);
		gbc_botonEliminar.gridx = 2;
		gbc_botonEliminar.gridy = 3;
		gridBagLayoutVentana.add(botonEliminar, gbc_botonEliminar);
		
		botonEliminar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	//Es necesario que haya un grupo abierto
		        if (grupoSeleccionado != null && listaContactosGrupo.getSelectedValue() != null 
		        		&& listaContactosGrupo.getSelectedValue() instanceof ContactoIndividual) {
		        	
		        	ContactoIndividual contactoSeleccionado = (ContactoIndividual) listaContactosGrupo.getSelectedValue();
	                
		        	if(!AppChat.getUnicaInstancia().eliminarContactoGrupo(grupoSeleccionado, contactoSeleccionado))
		        		JOptionPane.showMessageDialog(VentanaContactos.this, "No se ha podido eliminar el contacto del grupo");
            		modelContactosGrupo.removeElement(contactoSeleccionado);	            		
		        }
		    }
		});
		
		JPanel panelBotonInsertarContacto = new JPanel();
		GridBagConstraints gbc_panelBotonInsertarContacto = new GridBagConstraints();
		gbc_panelBotonInsertarContacto.insets = new Insets(0, 0, 5, 5);
		gbc_panelBotonInsertarContacto.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBotonInsertarContacto.gridx = 1;
		gbc_panelBotonInsertarContacto.gridy = 5;
		gridBagLayoutVentana.add(panelBotonInsertarContacto, gbc_panelBotonInsertarContacto);
		
		JButton botonInsertarContacto = new JButton("Añadir Contacto");
		botonInsertarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegistrarContacto ventana = new VentanaRegistrarContacto();
				ventana.setVisible(true);
				//Una vez se haya añadido un contacto, cuando se cierre la ventana de añadir contactos, se
				//actualizará la lista de contactos
				//TODO: Hacer para que también se realize cuando se le da al botón cancelar
				ventana.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                    	listaContactos.setModel(actualizarListaContactos());
                		scrollPaneContactos.setViewportView(listaContactos);
                		ventana.setVisible(false);
                    }
                });
			}
		});
		panelBotonInsertarContacto.add(botonInsertarContacto);
		
		JPanel panelBotonInsertarGrupo = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 5;
		gridBagLayoutVentana.add(panelBotonInsertarGrupo, gbc_panel);
		
		JButton btnInsertarGrupo = new JButton("Añadir Grupo");
		
		btnInsertarGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegistrarGrupo ventana = new VentanaRegistrarGrupo();
				ventana.setVisible(true);
				//Una vez se haya añadido un grupo, cuando se cierre la ventana de añadir contactos, se
				//actualizará la lista de contactos.
				ventana.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        listaContactos.setModel(actualizarListaContactos());
                		scrollPaneContactos.setViewportView(listaContactos);
                		ventana.setVisible(false);
                    }
                });
			}
		});
		
		panelBotonInsertarGrupo.add(btnInsertarGrupo);
		
	}

}

