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
import tds.appchat.modelo.Contacto;
import tds.appchat.modelo.ContactoCellRenderer;
import tds.appchat.modelo.ContactoIndividual;
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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaContactos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel gridBagLayoutVentana;
	private JTextField textField;
	private JTextField txtListaDeContactos;

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
	 * Create the frame.
	 */
	public VentanaContactos() {
		
		int a;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		
		JScrollPane scrollPanelContactos = new JScrollPane();
		GridBagConstraints gbc_scrollPanelContactos = new GridBagConstraints();
		gbc_scrollPanelContactos.gridheight = 2;
		gbc_scrollPanelContactos.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPanelContactos.fill = GridBagConstraints.BOTH;
		gbc_scrollPanelContactos.gridx = 1;
		gbc_scrollPanelContactos.gridy = 2;
		gridBagLayoutVentana.add(scrollPanelContactos, gbc_scrollPanelContactos);
		
		JList<Contacto> listaContactos = new JList<Contacto>();
		listaContactos.setCellRenderer(new ContactoCellRenderer());
		DefaultListModel<Contacto> modeloContactos = new DefaultListModel<Contacto>();
		
        /*modeloContactos.addElement(new ContactoIndividual("Carlos", "332", null));
        modeloContactos.addElement(new ContactoIndividual("Simón","331" ,null));
        modeloContactos.addElement(new ContactoIndividual("Lucia", "330" ,null));
		*/
		for(Contacto contacto : AppChat.getUnicaInstancia().contactosUsuarioActual()) {
			modeloContactos.addElement(contacto);
		}
		listaContactos.setModel(modeloContactos);
		
		scrollPanelContactos.setViewportView(listaContactos);
		
		JButton button = new JButton(">>");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 2;
		gridBagLayoutVentana.add(button, gbc_button);
		
		
		JScrollPane scrollPanellGrupos = new JScrollPane();
		GridBagConstraints gbc_scrollPanellGrupos = new GridBagConstraints();
		gbc_scrollPanellGrupos.gridheight = 2;
		gbc_scrollPanellGrupos.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPanellGrupos.fill = GridBagConstraints.BOTH;
		gbc_scrollPanellGrupos.gridx = 3;
		gbc_scrollPanellGrupos.gridy = 2;
		gridBagLayoutVentana.add(scrollPanellGrupos, gbc_scrollPanellGrupos);
		
		JList list_1 = new JList();
		scrollPanellGrupos.setViewportView(list_1);
		
		JButton button_1 = new JButton("<<");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 2;
		gbc_button_1.gridy = 3;
		gridBagLayoutVentana.add(button_1, gbc_button_1);
		
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
			}
		});
		panelBotonInsertarContacto.add(botonInsertarContacto);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 5;
		gridBagLayoutVentana.add(panel, gbc_panel);
		
		JButton btnAadirGrupo = new JButton("Añadir Grupo");
		panel.add(btnAadirGrupo);
		
	}

}

