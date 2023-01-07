package edu.accomodation.Waypoints;

import edu.accomodation.Hotel;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class MyWaypoint extends DefaultWaypoint {
    private Hotel hotel;
    private JButton button;
    private JTabbedPane contextPanel;
    private Set<JLabel> hotelLabels;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public MyWaypoint(GeoPosition coord, Hotel hotel, JTabbedPane contextPanel, Set<JLabel> hotelLabels) {
        super(coord);
        this.hotel = hotel;
        this.contextPanel = contextPanel;
        this.hotelLabels = hotelLabels;
        initButton();
    }

    private void initButton() {
        button = new ButtonWaypoint();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contextPanel.setSelectedIndex(3);
                Object[] myArr = hotelLabels.toArray();
                JLabel hotelName = (JLabel)myArr[0];
                JLabel hotelWeb = (JLabel)myArr[1];
                JLabel hotelCat = (JLabel)myArr[2];
                JLabel hotelDesc = (JLabel)myArr[3];

                hotelName.setText(hotel.getName());
                hotelWeb.setText(hotel.getWeb());
                hotelCat.setText(hotel.getCategory());
                hotelDesc.setText(hotel.getDescription());
            }
        });
    }
}
