package edu.accomodation.Forms;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.ThemeType;
import edu.accomodation.DatabasePerisisters.HotelDatabasePersister;
import edu.accomodation.DatabasePerisisters.ReservationDatabasePersister;
import edu.accomodation.DatabasePerisisters.RoomDatabasePersister;
import edu.accomodation.DatabasePerisisters.RoomReservedPersister;
import edu.accomodation.DatabaseTablesRepresentations.Hotel;
import edu.accomodation.DatabaseTablesRepresentations.Reservation;
import edu.accomodation.DatabaseTablesRepresentations.Room;
import edu.accomodation.DatabaseTablesRepresentations.User;
import edu.accomodation.MapHandling.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HotelPanel extends JFrame{
    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel hotelContainer;
    private JPanel container;
    private JScrollPane roomListContainer;
    private JPanel variablesContainer;
    private JPanel calendarContainer;
    private JPanel imagesContainer;
    private JLabel hotelImages;
    private JButton prevBtn;
    private JButton nextBtn;
    private JComboBox peopleCountCb;
    private JComboBox dayFromCb;
    private JComboBox monthFromCb;
    private JComboBox yearFromCb;
    private JComboBox yearToCb;
    private JComboBox monthToCb;
    private JComboBox dayToCb;
    private JLabel hotelName;
    private JButton createReservationBtn;
    private JTable roomTable;
    private User loggedUser;
    private int hotelId;
    private int actualIter = 0;
    private Calendar calendar;

    public HotelPanel(String title, User loggedUser, int hotelId) throws HeadlessException, SQLException {
        super(title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.loggedUser = loggedUser;
        this.hotelId = hotelId;

        Hotel chosenHotel = new HotelDatabasePersister().readHotel(hotelId);

        hotelName.setText(chosenHotel.getName());

        prepareImages(chosenHotel);
        showListOfRooms(hotelId);
        createCalendar(calendarContainer);

        roomTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    try {
                        addReservationsToCalendar(calendar, roomTable.getSelectedRow());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });


        if(loggedUser == null) {
            createReservationBtn.setEnabled(false);
            createReservationBtn.setText("Zaloguj się aby dokonać rezerwacji.");
        } else {
            createReservationBtn.addActionListener(e -> {
                LocalDateTime dateFrom = LocalDateTime.of(
                    Integer.parseInt((String) yearFromCb.getItemAt(yearFromCb.getSelectedIndex())),
                    monthFromCb.getSelectedIndex() + 1,
                    dayFromCb.getSelectedIndex() + 1, 12, 0);
                LocalDateTime dateTo = LocalDateTime.of(
                    Integer.parseInt((String) yearToCb.getItemAt(yearToCb.getSelectedIndex())),
                    monthToCb.getSelectedIndex() + 1,
                    dayToCb.getSelectedIndex() +1, 10, 0);

                int peopleCounter = peopleCountCb.getSelectedIndex() + 1;
                try {
                    createReservation(dateFrom, dateTo, this.loggedUser);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }



    }

    void showListOfRooms(int hotelId) throws SQLException {
        List<Room> roomsList = new RoomDatabasePersister().listRoomsByHotelId(hotelId);
        DefaultTableModel model = new DefaultTableModel(null, new String[] {"numer", "opis", "max osób", "cena"});
        roomTable.setModel(model);
        model.setRowCount(roomsList.size());

        for(int iter = 0; iter < roomsList.size(); iter++) {
            roomTable.setValueAt(roomsList.get(iter).getRoom_number(), iter, 0);
            roomTable.setValueAt(roomsList.get(iter).getType(), iter, 1);
            roomTable.setValueAt(roomsList.get(iter).getMax_occupancy(), iter, 2);
            roomTable.setValueAt(roomsList.get(iter).getPrice_per_night(), iter, 3);
        }

    }

    private void createReservation(LocalDateTime dateFrom, LocalDateTime dateTo, User user) throws SQLException {
        Utility utility = new Utility(calendar, new DateTime(dateFrom), new DateTime(dateTo));
        new ReservationDatabasePersister().addReservation(user.getId(), Date.valueOf(dateFrom.toLocalDate()), Date.valueOf(dateTo.toLocalDate()), 1.);
        int lastReservationId = new ReservationDatabasePersister().getReservationId();
        new RoomReservedPersister().addRoomReserved(lastReservationId, (Integer) roomTable.getValueAt(roomTable.getSelectedRow(), 0), 1.);

        utility.updateProgress((Integer) roomTable.getValueAt(roomTable.getSelectedRow(), 0));
        JOptionPane.showMessageDialog(this, "Udana rezerwacja", "Udało sie utworzyć rezerwację, dziekujemy.", JOptionPane.INFORMATION_MESSAGE);
    }

    private void prepareImages(Hotel chosenHotel) {
        Image image1;
        Image image2;
        Image image3;
        Image dimg1 = null;
        Image dimg2 = null;
        Image dimg3 = null;
        List<Image> images = new ArrayList<>();


        //TODO zrobic kalendarz i liste pokoi dla kazdego z hoteli

        try {
            URL url1 = new URL(chosenHotel.getPicture1());
            URL url2 = new URL(chosenHotel.getPicture2());
            URL url3 = new URL(chosenHotel.getPicture3());
            URLConnection uc1 = url1.openConnection();
            uc1.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc1.connect();
            URLConnection uc2 = url2.openConnection();
            uc2.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc2.connect();
            URLConnection uc3 = url3.openConnection();
            uc3.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc3.connect();
            image1 = ImageIO.read(url1);
            image2 = ImageIO.read(url2);
            image3 = ImageIO.read(url3);
            dimg1 = image1.getScaledInstance(hotelImages.getWidth(), hotelImages.getHeight(), Image.SCALE_SMOOTH);
            dimg2 = image2.getScaledInstance(hotelImages.getWidth(), hotelImages.getHeight(), Image.SCALE_SMOOTH);
            dimg3 = image3.getScaledInstance(hotelImages.getWidth(), hotelImages.getHeight(), Image.SCALE_SMOOTH);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        images.add(dimg1);
        images.add(dimg2);
        images.add(dimg3);

        hotelImages.setIcon(new ImageIcon(images.get(actualIter)));
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(actualIter < 2) {
                    actualIter++;
                    hotelImages.setIcon(new ImageIcon(images.get(actualIter)));
                } else {
                    actualIter = 0;
                    hotelImages.setIcon(new ImageIcon(images.get(actualIter)));
                }
            }
        });

        prevBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(actualIter > 0) {
                    actualIter--;
                    hotelImages.setIcon(new ImageIcon(images.get(actualIter)));
                } else {
                    actualIter = 2;
                    hotelImages.setIcon(new ImageIcon(images.get(actualIter)));
                }
            }
        });
    }

    private void createCalendar(JPanel calendarContainer){
        calendar = new Calendar();
        calendar.setTheme(ThemeType.Light);
        calendarContainer.setLayout(new BorderLayout());
        calendarContainer.add(calendar, BorderLayout.CENTER);
    }

    private void addReservationsToCalendar(Calendar calendar, int row) throws SQLException {


        calendar.getSchedule().getAllItems().removeAll(calendar.getSchedule().getAllItems());

        List<Reservation> reservations = new RoomReservedPersister().getAllReservationDependsOnRoomNumber((Integer) roomTable.getValueAt(row, 0));
        for (int iter = 0; iter < reservations.size(); iter++) {
            Date data = reservations.get(iter).getCheck_in_date();
            Timestamp timestamp = new Timestamp(data.getTime());
            LocalDateTime ldt = timestamp.toLocalDateTime();
            DateTime dateFrom = new DateTime(ldt);
            data = reservations.get(iter).getCheck_out_date();
            timestamp = new Timestamp(data.getTime());
            ldt = timestamp.toLocalDateTime();
            DateTime dateTo = new DateTime(ldt);
            Utility utility = new Utility(calendar, dateFrom, dateTo);
            utility.updateProgress((Integer) roomTable.getValueAt(row, 0));
        }
    }

}
