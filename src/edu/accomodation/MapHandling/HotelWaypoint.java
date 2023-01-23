package edu.accomodation.MapHandling;

import edu.accomodation.Forms.HotelPanel;
import edu.accomodation.DatabaseTablesRepresentations.User;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.sql.SQLException;

/*
 * Klasa reprezentująca punkt na mapie oznaczający hotel.
 */
public class HotelWaypoint extends DefaultWaypoint {

    /** przycisk reprezentujący hotel*/
    private JButton hotelWaypoint;

    /** zalogowany użytkownik */
    private User loggedUser;

    /**identyfikator hotelu */
    private int hotelId;


    /**
     * Konstruktor dodający waypoint dla hotelu na mapie.
     * @param coord - pozycja geograficzna dla waypointu
     * @param loggedUser - obecnie zalogowany użytkownik
     * @param hotelId - id hotelu, dla którego tworzony jest waypoint
     */
    public HotelWaypoint(GeoPosition coord, User loggedUser, int hotelId) {
        super(coord);

        initButton(loggedUser, hotelId);
    }


    /**
     * Inicjalizuje przycisk reprezentujący hotel, ustawia akcję po jego kliknięciu
     * @param loggedUser - obecnie zalogowany użytkownik
     * @param hotelId - id hotelu
     */
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


    /**
     * Zwraca przycisk reprezentujący hotel
     * @return - JButton
     */
    public JButton getButton() {
        return hotelWaypoint;
    }
}
