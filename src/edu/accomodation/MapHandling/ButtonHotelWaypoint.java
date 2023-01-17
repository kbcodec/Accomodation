package edu.accomodation.MapHandling;

import javax.swing.*;
import java.awt.*;

public class ButtonHotelWaypoint extends JButton {
    public ButtonHotelWaypoint() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setIcon(new ImageIcon("BGM_v2/src/edu/accomodation/Waypoints/hotel.png"));
        setBorder(null);
        setSize(new Dimension(24, 24));
    }
}
