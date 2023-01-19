package edu.accomodation.MapHandling;

import javax.swing.*;
import java.awt.*;

public class ButtonHotelWaypoint extends JButton {
    public ButtonHotelWaypoint() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setIcon(new ImageIcon("imgs/hotel.png"));
        setBorder(null);
        setSize(new Dimension(24, 24));
    }
}
