package tds.appchat.vista;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotonImagen extends JButton {
	
	private ImageIcon imagenPulsado;
	private ImageIcon imagen;
	
	public BotonImagen(ImageIcon i) {
		this.imagen = i;
		this.imagenPulsado = i;
		
		setIcon(i);
		//setPressedIcon(new ImageIcon(VentanaContactos.class.getResource("/imagenes/flecha-inv-pulsada.png")));
        setContentAreaFilled(false);  	// Eliminar el fondo
        setBorderPainted(false); 		// Eliminar el borde pintado
		setFocusable(false);			// Eliminar focuseable
	}
	
	
	
	
}
