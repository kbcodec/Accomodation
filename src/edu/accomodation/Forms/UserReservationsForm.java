package edu.accomodation.Forms;

import edu.accomodation.DatabasePerisisters.ReservationDatabasePersister;
import edu.accomodation.DatabasePerisisters.RoomReservedPersister;
import edu.accomodation.DatabaseTablesRepresentations.Room;
import edu.accomodation.DatabaseTablesRepresentations.User;
import edu.accomodation.Interfaces.IFormLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserReservationsForm extends JFrame implements IFormLayout {

    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel imagePanel;
    private JTable reservationsTable;
    private JButton deleteReservationBtn;
    private User loggedUser;

    public UserReservationsForm(String title, User loggedUser) throws HeadlessException, IOException, SQLException {
        super(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.loggedUser = loggedUser;
        addBanner(imagePanel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    try {
                        new MainMenuLogged("Menu", loggedUser).setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });

        assignDataToList(loggedUser.getId());

        deleteReservationBtn.addActionListener(e -> {
            int reservationRow = reservationsTable.getSelectedRow();
            if (reservationRow != -1) {
                int reservationId = (int) reservationsTable.getValueAt(reservationRow, 0);
                deleteReservation(reservationId);
                JOptionPane.showMessageDialog(MainP, "Udało sie usunąć rezerwację", "Potwierdzenie", JOptionPane.INFORMATION_MESSAGE);
                try {
                    refreshFrame();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                JOptionPane.showMessageDialog(MainP, "Najpierw wybierz, którą rezerwację chcesz usunąć", "Informacja", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    private void assignDataToList(int userId) throws SQLException {
        List<Room> reservedRooms = new ReservationDatabasePersister().getAllReservationsForLoggedUser(userId);
        DefaultTableModel model = new DefaultTableModel(null, new String[] {"Id","Nazwa hotelu", "Miejscowość", "Numer pokoju", "Data zakwaterowania", "Data wyjazdu"});
        reservationsTable.setModel(model);
        model.setRowCount(reservedRooms.size());

        for (int iter = 0; iter < reservedRooms.size(); iter++) {
            reservationsTable.setValueAt(reservedRooms.get(iter).getReservationId(), iter, 0);
            reservationsTable.setValueAt(reservedRooms.get(iter).getHotelName(), iter, 1);
            reservationsTable.setValueAt(reservedRooms.get(iter).getCity(), iter, 2);
            reservationsTable.setValueAt(reservedRooms.get(iter).getRoom_number(), iter, 3);
            reservationsTable.setValueAt(reservedRooms.get(iter).getCheckInDate(), iter, 4);
            reservationsTable.setValueAt(reservedRooms.get(iter).getCheckOutDate(), iter, 5);
        }
    }

    private void deleteReservation(int reservationId) {
        try {
            new RoomReservedPersister().deleteRoomReservedRecord(reservationId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Błąd podczas usuwania rezerwacji pokoju", "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        try {
            new ReservationDatabasePersister().deleteReservationRecord(reservationId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Błąd podczas usuwania rezerwacji użytkownika", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshFrame() throws SQLException, IOException {
        this.setVisible(false);
        new UserReservationsForm("Twoje rezerwacje", loggedUser).setVisible(true);
    }

}
