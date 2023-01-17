package edu.accomodation.Waypoints;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.ThemeType;
import edu.accomodation.*;
import edu.accomodation.UserHandling.User;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MyWaypoint extends DefaultWaypoint {
    private Hotel hotel;
    private JButton button;
    private JTabbedPane contextPanel;
    private List<JLabel> hotelLabels;
    private JButton nextBtn;
    private JButton prevBtn;
    private JList<Room> roomsList;
    private JPanel calendarPanel;
    private Calendar calendar;
    private List<JComboBox> comboBoxes;
    private JButton reserveBtn;

    private User loggedUser;

    public MyWaypoint(GeoPosition geoPosition) {
    }

    public JButton getButton() {
        return button;
    }

    int actualIter = 0;

    public MyWaypoint(GeoPosition coord, Hotel hotel, JTabbedPane contextPanel, List<JLabel> hotelLabels, JButton nextBtn, JButton prevBtn, JList<Room> rooms, JPanel calendarPanel, List<JComboBox> comboBoxes, JButton reserveBtn, User loggedUser) {
        super(coord);
        this.hotel = hotel;
        this.contextPanel = contextPanel;
        this.hotelLabels = hotelLabels;
        this.prevBtn = prevBtn;
        this.nextBtn = nextBtn;
        this.roomsList = rooms;
        this.calendarPanel = calendarPanel;
        this.comboBoxes = comboBoxes;
        this.reserveBtn = reserveBtn;
        this.loggedUser = loggedUser;

        initButton();
    }

    public MyWaypoint(GeoPosition coord, Hotel hotel, JTabbedPane contextPanel, List<JLabel> hotelLabels, JButton nextBtn, JButton prevBtn, JList<Room> rooms, JPanel calendarPanel, List<JComboBox> comboBoxes, JButton reserveBtn) {
        super(coord);
        this.hotel = hotel;
        this.contextPanel = contextPanel;
        this.hotelLabels = hotelLabels;
        this.prevBtn = prevBtn;
        this.nextBtn = nextBtn;
        this.roomsList = rooms;
        this.calendarPanel = calendarPanel;
        this.comboBoxes = comboBoxes;
        this.reserveBtn = reserveBtn;

        initButton();
    }



    private void initButton() {

        button = new ButtonWaypoint();

        for(ActionListener al: button.getActionListeners()) {
            button.removeActionListener(al);
        }

        if(loggedUser != null) {
            button = new ButtonWaypoint();
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hotelClick();

                    System.out.println("ZALOGOWANY");

                    for(ActionListener al : reserveBtn.getActionListeners()) {
                        reserveBtn.removeActionListener(al);
                    }
                    reserveBtn.setEnabled(true);
                    reserveBtn.setText("Dokonaj rezerwacji pokoju.");
                    reserveBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            LocalDateTime dateFrom = LocalDateTime.of(Integer.parseInt((String) comboBoxes.get(3).getItemAt(comboBoxes.get(3).getSelectedIndex())), comboBoxes.get(2).getSelectedIndex() + 1, comboBoxes.get(1).getSelectedIndex() + 1, 1, 0);
                            LocalDateTime dateTo = LocalDateTime.of(Integer.parseInt((String) comboBoxes.get(6).getItemAt(comboBoxes.get(6).getSelectedIndex())), comboBoxes.get(5).getSelectedIndex() + 1, comboBoxes.get(4).getSelectedIndex() + 1, 1, 0);

                            Utility utility = new Utility(calendar, new DateTime(dateFrom), new DateTime(dateTo));

                        }
                    });

                }
            });
        } else {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hotelClick();

                    System.out.println("NIEZALOGOWANY");

                    for(ActionListener al : reserveBtn.getActionListeners()) {
                        reserveBtn.removeActionListener(al);
                    }

                    reserveBtn.setEnabled(false);
                    reserveBtn.setText("Zaloguj się aby dokonać rejestracji");

                }
            });
        }

    }

    private void hotelClick() {


        contextPanel.setSelectedIndex(3);
        Object[] myArr = hotelLabels.toArray();
        JLabel hotelName = (JLabel)myArr[0];
        JLabel hotelWeb = (JLabel)myArr[1];
        JLabel hotelCat = (JLabel)myArr[2];
        JLabel hotelDesc = (JLabel)myArr[3];
        JLabel hotelImg = (JLabel)myArr[4];

        hotelName.setText(hotel.getName());
        hotelWeb.setText(hotel.getWeb());
        hotelCat.setText(hotel.getCategory());
        hotelDesc.setText("<html><p text-align=\"justify\">" + hotel.getDescription() + "</p></html>");

        Image image1;
        Image image2;
        Image image3;
        Image dimg1 = null;
        Image dimg2 = null;
        Image dimg3 = null;
        List<Image> images = new ArrayList<>();


        //TODO zrobic kalendarz i liste pokoi dla kazdego z hoteli

        try {
            URL url1 = new URL(hotel.getPicture1());
            URL url2 = new URL(hotel.getPicture2());
            URL url3 = new URL(hotel.getPicture3());
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
            dimg1 = image1.getScaledInstance(hotelImg.getWidth(), hotelImg.getHeight(), Image.SCALE_SMOOTH);
            dimg2 = image2.getScaledInstance(hotelImg.getWidth(), hotelImg.getHeight(), Image.SCALE_SMOOTH);
            dimg3 = image3.getScaledInstance(hotelImg.getWidth(), hotelImg.getHeight(), Image.SCALE_SMOOTH);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        images.add(dimg1);
        images.add(dimg2);
        images.add(dimg3);

        hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(actualIter < 2) {
                    actualIter++;
                    hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                } else {
                    actualIter = 0;
                    hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                }
            }
        });

        prevBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(actualIter > 0) {
                    actualIter--;
                    hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                } else {
                    actualIter = 2;
                    hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                }
            }
        });


        DefaultListModel<Room> model = new DefaultListModel<>();
        List<Room> rooms = new ArrayList<>();
        roomsList.setModel(model);
        try {
            rooms = new RoomDatabasePersister().listRoomsByHotelId(hotel.getId_hotel());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        for (Room r :
                rooms) {
            model.addElement(r);
        }

        //KALENDARZ



        calendar = new Calendar();
        calendar.setTheme(ThemeType.Light);

        calendarPanel.setLayout(new BorderLayout());
        calendarPanel.add(calendar, BorderLayout.CENTER);

        for(ActionListener al: reserveBtn.getActionListeners()) {
            reserveBtn.removeActionListener(al);
        }

    }


}
