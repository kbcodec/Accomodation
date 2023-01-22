package edu.accomodation.MapHandling;

import javax.swing.*;
import java.awt.*;


/*
 * Klasa ustanawiająca parametry dla oznaczenia Hotelu na mapie
 */
public class ButtonHotelWaypoint extends JButton {

    /**
     * Konstruktor ustawia parametry przycisku takie jak wygląd, kursor, ikonę oraz rozmiar
     */
    public ButtonHotelWaypoint() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setIcon(new ImageIcon("imgs/hotel.png"));
        setBorder(null);
        setSize(new Dimension(24, 24));
    }
}
