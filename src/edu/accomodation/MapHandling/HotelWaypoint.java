package edu.accomodation.MapHandling;

import edu.accomodation.Forms.HotelPanel;
import edu.accomodation.UserHandling.User;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.sql.SQLException;

public class HotelWaypoint extends DefaultWaypoint {

    private JButton hotelWaypoint;
    private User loggedUser;
    private int hotelId;

    public HotelWaypoint(GeoPosition coord, User loggedUser, int hotelId) {
        super(coord);

        initButton(loggedUser, hotelId);
    }

    private void initButton(User loggedUser, int hotelId) {
        hotelWaypoint = new ButtonHotelWaypoint();

        hotelWaypoint.addActionListener(e -> {
            try {
                new HotelPanel("Rezerwacja Hotelu", loggedUser, hotelId).setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public JButton getButton() {
        return hotelWaypoint;
    }
}
