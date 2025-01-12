package tds.appchat.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotonNuevo extends JButton {
		//TODO: Obtener colores de controlador para así tener el mismo color / Paleta de colores en todo el proyecto
		//Color que llevará el botón por defecto
		private Color color = new Color(160, 255, 100);
		private Color colorPresionado = new Color(89, 229, 53);
		
		
	    public BotonNuevo(String texto) {
	        super(texto);
	        // Establecer un borde vacío para que no se dibuje el borde predeterminado
	        setBorder(BorderFactory.createEmptyBorder());
	        setPreferredSize(new Dimension(160, 35));
	        setFocusPainted(false);  // Evitar el borde de enfoque al hacer clic
	        setContentAreaFilled(false); // Evitar que se rellene el área del botón con el color de fondo
	    }
	    
	    
	    @Override
	    protected void paintComponent(Graphics g) {
	        if (getModel().isPressed()) {
	            g.setColor(colorPresionado);  // Color de fondo cuando está presionado
	        } else {
	            g.setColor(color);  // Color de fondo normal
	        }
	        // Dibujar el fondo redondeado
	        g.fillRoundRect(0, 0, getWidth(), getHeight(), 45, 45);  // Los dos últimos parámetros son el radio de los bordes

	        super.paintComponent(g);  // Dibuja el texto sobre el botón
	    }

}
