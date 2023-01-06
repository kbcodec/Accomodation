package edu.accomodation.Waypoints;

import javax.swing.*;
import java.awt.*;

public class ButtonWaypoint extends JButton {
    public ButtonWaypoint() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setIcon(new ImageIcon("BGM_v2/src/edu/accomodation/Waypoints/hotel.png"));
        setBorder(null);
        setSize(new Dimension(24, 24));
    }
}
