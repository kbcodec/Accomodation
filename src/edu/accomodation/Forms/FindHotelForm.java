package edu.accomodation.Forms;

import edu.accomodation.DatabasePerisisters.HotelDatabasePersister;
import edu.accomodation.DatabasePerisisters.RoomDatabasePersister;
import edu.accomodation.DatabaseTablesRepresentations.Room;
import edu.accomodation.DatabaseTablesRepresentations.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FindHotelForm extends JFrame{
    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel parametersPanel;
    private JTextField cityTf;
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

        try {
            showAllHotelsList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        hotelsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    openHotelView(hotelsTable.getSelectedRow());
                }
            }
        });

    }

    private void openHotelView(int selectedRow) {
        String hotelName = (String)hotelsTable.getValueAt(selectedRow, 0);
        try {
            int hotelId = new HotelDatabasePersister().getHotelIdByName(hotelName);
            new HotelPanel("Hotel",user, hotelId).setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void showAllHotelsList() throws SQLException {
        roomList = new RoomDatabasePersister().listAllRoomsWithHotelNameAndLocalization();
        DefaultTableModel model = new DefaultTableModel(null, new String[] {"nazwa", "lokalizacja", "numer pokoju", "max osób", "cena"});
        hotelsTable.setModel(model);
        model.setRowCount(roomList.size());

        for(int iter = 0; iter < roomList.size(); iter++) {
            hotelsTable.setValueAt(roomList.get(iter).getHotelName(), iter, 0);
            hotelsTable.setValueAt(roomList.get(iter).getCity(), iter, 1);
            hotelsTable.setValueAt(roomList.get(iter).getRoom_number(), iter, 2);
            hotelsTable.setValueAt(roomList.get(iter).getMax_occupancy(), iter, 3);
            hotelsTable.setValueAt(roomList.get(iter).getPrice_per_night(), iter,4);
        }
    }

}
