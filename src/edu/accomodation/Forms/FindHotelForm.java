package edu.accomodation.Forms;

import edu.accomodation.DatabasePerisisters.AddressDatabasePersister;
import edu.accomodation.DatabasePerisisters.HotelDatabasePersister;
import edu.accomodation.DatabasePerisisters.RoomDatabasePersister;
import edu.accomodation.DatabaseTablesRepresentations.Room;
import edu.accomodation.DatabaseTablesRepresentations.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FindHotelForm extends JFrame{
    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel parametersPanel;
    private JComboBox questsNumber;
    private JComboBox dayFrom;
    private JComboBox yearFrom;
    private JComboBox monthFrom;
    private JComboBox yearTo;
    private JComboBox monthTo;
    private JComboBox dayTo;
    private JTable hotelsTable;
    private JScrollPane hotelsFindContainer;
    private JButton searchBtn;
    private JComboBox cityCb;
    private User user;
    private List<Room> roomList;

    public FindHotelForm(String title, User user) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.user = user;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (user == null) {
                    try {
                        new MainMenu("Menu").setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        new MainMenuLogged("Menu", user).setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        try {
            insertCityNamesToComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            showAllHotelsList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        searchBtn.addActionListener(e -> {
            try {
                updateTable(hotelsTable);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        hotelsTable.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                openHotelView(hotelsTable.getSelectedRow());
            }
        });

    }

    private void openHotelView(int selectedRow) {
        if(hotelsTable.getSelectedRow() != -1){
            String hotelName = (String)hotelsTable.getValueAt(selectedRow, 0);
            try {
                int hotelId = new HotelDatabasePersister().getHotelIdByName(hotelName);
                new HotelPanel("Hotel",user, hotelId).setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showAllHotelsList() throws SQLException {
        roomList = new RoomDatabasePersister().listAllRoomsWithHotelNameAndLocalization();
        DefaultTableModel model = new DefaultTableModel(null, new String[] {"nazwa", "lokalizacja", "numer pokoju", "max osób", "cena"});
        hotelsTable.setModel(model);
        model.setRowCount(roomList.size());

        searchBtn.setText("ZNAJDŹ HOTEL (znaleziono wyników: " + roomList.size());

        assignDataToTable(hotelsTable, roomList);
    }

    private void insertCityNamesToComboBox() throws SQLException {
        List<String> cities = new AddressDatabasePersister().listCitiesFromAddresses();
        for (String city : cities) {
            cityCb.addItem(city);
        }
    }

    private List<Room> listSpecificRooms() throws SQLException {
        List<Room> specificRoomsList = new ArrayList<>();
        String userCity = (String)cityCb.getSelectedItem();
        int userMaxOccupancy = Integer.parseInt((String) questsNumber.getSelectedItem());
        LocalDateTime userDateFrom = LocalDateTime.of(
                Integer.parseInt((String) yearFrom.getItemAt(yearFrom.getSelectedIndex())),
                monthFrom.getSelectedIndex() + 1,
                dayFrom.getSelectedIndex() + 1, 12, 0);
        LocalDateTime userDateTo = LocalDateTime.of(
                Integer.parseInt((String) yearTo.getItemAt(yearTo.getSelectedIndex())),
                monthTo.getSelectedIndex() + 1,
                dayTo.getSelectedIndex() +1, 10, 0);

        Date userDateFromSql = Date.valueOf(userDateFrom.toLocalDate());
        Date userDateToSql = Date.valueOf(userDateTo.toLocalDate());

        try {
            specificRoomsList = new RoomDatabasePersister().listRoomsDependsOfUserParameters(userCity, userDateFromSql, userDateToSql, userMaxOccupancy);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        searchBtn.setText("ZNAJDŹ HOTEL (znaleziono wyników: " + specificRoomsList.size());

        return specificRoomsList;

    }

    private void updateTable(JTable table) throws SQLException {
        List<Room> specificRoomsList = listSpecificRooms();
        DefaultTableModel model = new DefaultTableModel(null, new String[] {"nazwa", "lokalizacja", "numer pokoju", "max osób", "cena"});
        table.setModel(model);
        model.setRowCount(specificRoomsList.size());

        assignDataToTable(table, specificRoomsList);
    }

    private void assignDataToTable(JTable table, List<Room> list) {
        for(int iter = 0; iter < list.size(); iter++) {
            table.setValueAt(list.get(iter).getHotelName(), iter, 0);
            table.setValueAt(list.get(iter).getCity(), iter, 1);
            table.setValueAt(list.get(iter).getRoom_number(), iter, 2);
            table.setValueAt(list.get(iter).getMax_occupancy(), iter, 3);
            table.setValueAt(list.get(iter).getPrice_per_night(), iter,4);
        }
    }

}
