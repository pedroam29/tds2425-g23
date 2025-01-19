package tds.appchat.vista;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.DocumentException;

import tds.appchat.controlador.AppChat;
import tds.appchat.modelo.Mensaje;
import java.awt.Color;
import javax.swing.JTextField;

public class VentanaPDF extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String rutaSeleccionada;
    private JTextField textField;

    /**
     * Create the frame.
     */
    public VentanaPDF(List<Mensaje> mensaje, String nombreReceptor) {
    	setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        contentPane.add(panel_1, BorderLayout.CENTER);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{5, 0, 0, 0, 0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);

        JLabel lblImagen = new JLabel();
        GridBagConstraints gbc_lblAa = new GridBagConstraints();
        gbc_lblAa.gridwidth = 2;
        gbc_lblAa.insets = new Insets(0, 0, 5, 5);
        gbc_lblAa.gridx = 2;
        gbc_lblAa.gridy = 1;

        lblImagen.setIcon(null);
        panel_1.add(lblImagen, gbc_lblAa);
        
	    JButton btnSeleccionarRuta = new BotonImagen(new ImageIcon(VentanaPDF.class.getResource("/imagenes/carpeta.png")));
	            btnSeleccionarRuta.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    JFileChooser fileChooser = new JFileChooser();
	                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	                    
	                    int result = fileChooser.showOpenDialog(VentanaPDF.this);
	                    
	                    if (result == JFileChooser.APPROVE_OPTION) {
	                        File selectedFile = fileChooser.getSelectedFile();
	                        rutaSeleccionada = selectedFile.getAbsolutePath();
	                        textField.setText(rutaSeleccionada + File.separator + nombreReceptor + ".pdf");
	                    }
	                }
	            });

        JLabel lblNewLabel_1 = new JLabel("Ruta");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 3;
        panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
        
        JPanel panelRuta = new JPanel(new BorderLayout());
        panelRuta.setBackground(Color.WHITE);
        panelRuta.add(btnSeleccionarRuta, BorderLayout.EAST);
        GridBagConstraints gbc_panelRuta = new GridBagConstraints();
        gbc_panelRuta.gridwidth = 2;
        gbc_panelRuta.insets = new Insets(0, 0, 5, 5);
        gbc_panelRuta.fill = GridBagConstraints.BOTH;
        gbc_panelRuta.gridx = 2;
        gbc_panelRuta.gridy = 3;
        panel_1.add(panelRuta, gbc_panelRuta);
        
        textField = new JTextField();
        panelRuta.add(textField, BorderLayout.CENTER);
        textField.setColumns(10);
        textField.setEditable(false);
        
        JButton btnAceptar = new BotonGeneral("Aceptar");
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
        gbc_btnAceptar.gridx = 2;
        gbc_btnAceptar.gridy = 5;
        panel_1.add(btnAceptar, gbc_btnAceptar);

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
                    boolean res;
                	try {
                        res = AppChat.getUnicaInstancia().convertirPDF(rutaSeleccionada, nombreReceptor , mensaje);
                    } catch (DocumentException e1) {
                    	res = false;
                        JOptionPane.showMessageDialog(VentanaPDF.this, "No se ha podido crear el PDF");
                        return;
                    }

					if (!res) {
                    	JOptionPane.showMessageDialog(VentanaPDF.this, "No se ha podido crear el PDF");
                    } else {
                    	JOptionPane.showMessageDialog(VentanaPDF.this, "PDF creado con exito");
                    	dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaPDF.this, "Por favor, selecciona una ruta válida");
                }
            }
        });

        JButton btnCancelar = new BotonGeneral("Cancelar");
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.gridx = 3;
        gbc_btnCancelar.gridy = 5;
        panel_1.add(btnCancelar, gbc_btnCancelar);

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
