package edu.accomodation.Forms;

import edu.accomodation.DatabaseTablesRepresentations.Hotel;
import edu.accomodation.DatabasePerisisters.HotelDatabasePersister;
import edu.accomodation.MapHandling.HotelWaypoint;
import edu.accomodation.MapHandling.HotelWaypointRenderer;
import edu.accomodation.DatabaseTablesRepresentations.User;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapPanel extends JFrame{
    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel mapPanel;
    private JButton logOutButton;
    private JButton backButton;
    private User loggedUser;

    public MapPanel(String title, User loggedUser) throws HeadlessException, SQLException {
        super(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.loggedUser = loggedUser;
        backButton.addActionListener(e -> {
            try {
                backToMenu(loggedUser);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        logOutButton.addActionListener(e -> {
            try {
                logOut(loggedUser);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (loggedUser == null) {
                    try {
                        new MainMenu("Menu").setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        new MainMenuLogged("Menu", loggedUser).setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        createMapPanel();
    }

    void backToMenu(User user) throws IOException {
        this.setVisible(false);
        if(user == null) {
            new MainMenu("Accomodation").setVisible(true);
        } else {
            new MainMenuLogged("Accomodation", user).setVisible(true);
        }
    }

    void logOut(User user) throws IOException {
        user = null;
        this.dispose();
        new MainMenu("Accomodation").setVisible(true);
    }


    private void createMapPanel() throws SQLException {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        JXMapViewer jxMapViewer = new JXMapViewer();
        jxMapViewer.setTileFactory(tileFactory);
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        jxMapViewer.setBorder(blackLine);

        GeoPosition geo = new GeoPosition(52.069284, 19.480322);
        jxMapViewer.setAddressLocation(geo);
        jxMapViewer.setZoom(13);

        MouseInputListener mm = new PanMouseInputListener(jxMapViewer);
        jxMapViewer.addMouseListener(mm);
        jxMapViewer.addMouseMotionListener(mm);
        jxMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(jxMapViewer));

        List<Hotel> hotelList = new HotelDatabasePersister().listHotels();
        Set<HotelWaypoint> waypoints = new HashSet<>();

        for(Hotel hotel: hotelList) {
            HotelWaypoint hotelWaypoint = new HotelWaypoint(new GeoPosition(hotel.getLatitude(), hotel.getLongitude()), loggedUser, hotel.getId_hotel());
            waypoints.add(hotelWaypoint);
        }

        initWaypoint(waypoints, jxMapViewer);
        mapPanel.add(jxMapViewer);
        mapPanel.setPreferredSize(new Dimension(100, 100));
    }

    private void initWaypoint(Set<HotelWaypoint> waypoints, JXMapViewer jxMapViewer) {
        WaypointPainter<HotelWaypoint> wp = new HotelWaypointRenderer();
        wp.setWaypoints(waypoints);
        jxMapViewer.setOverlayPainter(wp);
        for(HotelWaypoint d: waypoints) {
            jxMapViewer.add(d.getButton());
        }
    }
}
