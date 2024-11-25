package tds.appchat.vista;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class VentanaContactos1 extends JFrame {

    private GridBagConstraints gbc_1;
    private GridBagConstraints gbc_2;
    private GridBagConstraints gbc_3;

    public VentanaContactos1() {
        setTitle("Gestión de Contactos y Grupos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        getContentPane().setLayout(new BorderLayout());

        GridBagLayout gbl_mainPanel = new GridBagLayout();
        gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_mainPanel.columnWidths = new int[]{10, 0, 0, 0, 0, 10};
        gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_mainPanel.rowHeights = new int[]{10, 0, 0, 5, 0, 0};
        JPanel mainPanel = new JPanel(gbl_mainPanel);

        // Lista de contactos
        DefaultListModel<Contacto> listaContactosModel = new DefaultListModel<Contacto>();
        JList<Contacto> listaContactos = new JList<>(listaContactosModel);
        listaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        listaContactos.setCellRenderer(new ContactoCellRenderer());
        JScrollPane contactScrollPane = new JScrollPane(listaContactos);
        
        listaContactosModel.addElement(new ContactoIndividual("Carlos", "332", null));
        listaContactosModel.addElement(new ContactoIndividual("Simón","331" ,null));
        listaContactosModel.addElement(new ContactoIndividual("Lucia", "330" ,null));
//        // Añadir algunos datos iniciales
//        listaContactosModel.addElement("contacto1");
//        listaContactosModel.addElement("contacto2");
//        listaContactosModel.addElement("grupo1");
//        listaContactosModel.addElement("contacto3");
//        listaContactosModel.addElement("grupo2");
        DefaultListModel<Contacto> listaGruposModel = new DefaultListModel<>();
        JList<Contacto> listaGrupos = new JList<Contacto>(listaGruposModel);
        listaGrupos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane groupScrollPane = new JScrollPane(listaGrupos);
        listaGrupos.setCellRenderer(new ContactoCellRenderer());
        // TODO: Cabmiar texto por imagen
        JButton botonIntroducirGrupo = new JButton(">>");
        JButton botonSacarGrupo = new JButton("<<");
        
        
        botonIntroducirGrupo.addActionListener(e -> {
            Contacto selected = listaContactos.getSelectedValue();
            if (selected != null) {
                listaGruposModel.addElement(selected);
                listaContactosModel.removeElement(selected);
            }
        });
        
        botonSacarGrupo.addActionListener(e -> {
            Contacto selected = listaGrupos.getSelectedValue();
            if (selected != null) {
                listaContactosModel.addElement(selected);
                listaGruposModel.removeElement(selected);
            }
        });

        // Configuración de GridBagLayout
        GridBagConstraints gbc;

        // Contact list
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        mainPanel.add(contactScrollPane, gbc);

        // Group list
        gbc_3 = new GridBagConstraints(); // Nueva instancia
        gbc_3.insets = new Insets(0, 0, 5, 5);
        gbc_3.gridx = 4;
        gbc_3.gridy = 1;
        gbc_3.gridheight = 2;
        gbc_3.fill = GridBagConstraints.BOTH;
        gbc_3.weightx = 0.4;
        gbc_3.weighty = 1.0;
        mainPanel.add(groupScrollPane, gbc_3);

        // Button >>
        gbc_1 = new GridBagConstraints(); // Nueva instancia
        gbc_1.insets = new Insets(0, 0, 5, 5);
        gbc_1.gridx = 3;
        gbc_1.gridy = 1;
        gbc_1.gridheight = 1;
        gbc_1.fill = GridBagConstraints.NONE;
        gbc_1.weightx = 0.1;
        gbc_1.weighty = 0.5;
        mainPanel.add(botonIntroducirGrupo, gbc_1);

        // Button <<
        gbc_2 = new GridBagConstraints(); // Nueva instancia
        gbc_2.insets = new Insets(0, 0, 5, 5);
        gbc_2.gridx = 3;
        gbc_2.gridy = 2;
        gbc_2.gridheight = 1;
        gbc_2.fill = GridBagConstraints.NONE;
        gbc_2.weightx = 0.1;
        gbc_2.weighty = 0.5;
        mainPanel.add(botonSacarGrupo, gbc_2);

        // Panel inferior con botones de añadir
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // Agregar paneles al frame
        getContentPane().add(mainPanel, BorderLayout.CENTER);
                
                Component horizontalGlue = Box.createHorizontalGlue();
                GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
                gbc_horizontalGlue.insets = new Insets(0, 0, 5, 5);
                gbc_horizontalGlue.gridx = 1;
                gbc_horizontalGlue.gridy = 4;
                mainPanel.add(horizontalGlue, gbc_horizontalGlue);
        
                // Botón para añadir contacto
                JButton addContactButton = new JButton("Añadir Contacto");
                GridBagConstraints gbc_addContactButton = new GridBagConstraints();
                gbc_addContactButton.fill = GridBagConstraints.HORIZONTAL;
                gbc_addContactButton.insets = new Insets(0, 0, 5, 5);
                gbc_addContactButton.gridx = 2;
                gbc_addContactButton.gridy = 4;
                mainPanel.add(addContactButton, gbc_addContactButton);

                JButton addGroupButton = new JButton("Añadir Grupo");
                GridBagConstraints gbc_addGroupButton = new GridBagConstraints();
                gbc_addGroupButton.fill = GridBagConstraints.HORIZONTAL;
                gbc_addGroupButton.insets = new Insets(0, 0, 5, 5);
                gbc_addGroupButton.gridx = 4;
                gbc_addGroupButton.gridy = 4;
                mainPanel.add(addGroupButton, gbc_addGroupButton);
//                addGroupButton.addActionListener(e -> {
//                    String group = JOptionPane.showInputDialog(this, "Ingrese el nombre del grupo:");
//                    if (group != null && !group.trim().isEmpty()) {
//                        listaGruposModel.addElement(group);
//                    }
//                });
//                addContactButton.addActionListener(e -> {
//                    String contact = JOptionPane.showInputDialog(this, "Ingrese el nombre del contacto:");
//                    if (contact != null && !contact.trim().isEmpty()) {
//                        listaContactosModel.addElement(contact);
//                    }
//                });
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaContactos1 app = new VentanaContactos1();
            app.setVisible(true);
        });
    }
}
