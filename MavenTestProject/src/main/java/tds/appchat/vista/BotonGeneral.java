package tds.appchat.vista;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;


public class BotonGeneral extends JButton {
	
	public static Color COLOR = new Color(229, 203, 76);
	public static Color COLOR_INV = new Color(75, 101, 229);
	
	public BotonGeneral() {
		super();
        setBackground(COLOR);
        setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 12));
        setForeground(SystemColor.activeCaptionText);
        setBorderPainted(false);
	}
	public BotonGeneral(String txt) {
        super(txt);
        //setBackground(SystemColor.control);
        setBackground(COLOR);
        setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 12));
        setForeground(SystemColor.activeCaptionText);
        setBorderPainted(false);
    }
}
