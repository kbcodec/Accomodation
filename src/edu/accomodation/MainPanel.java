package edu.accomodation;

import edu.accomodation.UserHandling.User;
import edu.accomodation.UserHandling.UserController;
import edu.accomodation.Waypoints.MyWaypoint;
import edu.accomodation.Waypoints.WaypointRender;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainPanel extends JFrame {
    private JPanel MainP;
    private JPanel titlePanel;

    //MENU PANEL COMPONENTS
    private JPanel menuPanel;
    private JButton MPmenuLoginButton;
    private JButton MPmenuMapButton;

    //CONTEXT PANELS
    private JTabbedPane contextPanels;
    private JPanel loginPanel;
    private JPanel mapPanel;
    private JPanel registerPanel;

    //LOGIN PANEL COMPONENTS
    private JTextField LPloginTf;
    private JButton LPregisterButton;
    private JButton LPloginButton;
    private JPasswordField LPpasswordPf;
    private JPanel imagePanel;

    //REGISTER PANEL COMPONENTS
    private JButton RPregisterButton;
    private JTextField RPfirstNameTf;
    private JTextField RPemailTf;
    private JTextField RPlastNameTf;
    private JTextField RPloginTf;
    private JPasswordField RPconfirmPasswordPf;
    private JPasswordField RPpasswordPf;
    private JPanel hotelPanel;
    private JLabel HPhotelName;
    private JLabel HPhotelDesc;
    private JLabel HPhotelCategory;
    private JLabel HPhotelWebPage;
    private JLabel HPhotelImg;
    private JButton HPprevBtn;
    private JButton HPnextBtn;


    private JXMapViewer jxMapViewerSaved = null;

    private User user;


    public MainPanel()  {

    }



    //BUTTONS IN MENU CLICK HANDLERS
    private void MPloginButtonClick() {contextPanels.setSelectedIndex(0);};

    private void MPmapButtonClick() throws SQLException {

        createMapPanel();

    };

    private void createMapPanel() throws SQLException {
        if(jxMapViewerSaved == null) {
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

            List<Hotel> hotelLists = new HotelDatabasePersister().listHotels();
            List<JLabel> hotelLabels = new ArrayList<>();
            hotelLabels.add(HPhotelName);
            hotelLabels.add(HPhotelWebPage);
            hotelLabels.add(HPhotelCategory);
            hotelLabels.add(HPhotelDesc);
            hotelLabels.add(HPhotelImg);
            Set<MyWaypoint> waypoints = new HashSet<>();

            for (Hotel hotel:hotelLists
            ) {
                MyWaypoint dw = new MyWaypoint(new GeoPosition(hotel.getLatitude(), hotel.getLongitude()), hotel, contextPanels, hotelLabels, HPnextBtn, HPprevBtn);
                waypoints.add(dw);
            }

            initWaypoint(waypoints, jxMapViewer);



            jxMapViewerSaved = jxMapViewer;

            mapPanel.add(jxMapViewer);
            mapPanel.setPreferredSize(new Dimension(100, 100));
            contextPanels.setSelectedIndex(1);
        } else {
            contextPanels.setSelectedIndex(1);
        }



    }

    private void initWaypoint(Set<MyWaypoint> waypoints, JXMapViewer jxMapViewer) {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypoints);
        jxMapViewer.setOverlayPainter(wp);
        for(MyWaypoint d: waypoints) {
            jxMapViewer.add(d.getButton());
        }
    }


    //LOGIN PANEL BUTTONS CLICK HANDLERS
    private void LPloginButtonClick() {
        String loginFromTf = LPloginTf.getText();
        char[] passwordFromPf = LPpasswordPf.getPassword();

        try {
            user = new UserController().readUser(loginFromTf);
            if(BCrypt.checkpw(String.valueOf(passwordFromPf), user.getPassword()))
            {
                JOptionPane.showMessageDialog(loginPanel, "Logowanie powiodło się", "Dostęp przyznany", JOptionPane.INFORMATION_MESSAGE);
                MPmenuLoginButton.setEnabled(false);
                MPmenuLoginButton.setText("Witaj " + user.getFirstName());
                createMapPanel();
            } else {
                JOptionPane.showMessageDialog(loginPanel, "Nieprawidłowe dane", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(loginPanel, "Nieprawidłowe dane", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void LPregisterButtonClick() {contextPanels.setSelectedIndex(2);}

    //REGISTER PANEL BUTTONS CLICK HANDLERS
    private void createUserButtonClick() throws SQLException {

        String firstNameFromTf = RPfirstNameTf.getText();
        String lastNameFromTf = RPlastNameTf.getText();
        String emailFromTf = RPemailTf.getText();
        String loginFromTf = RPloginTf.getText();
        char[] passwordFromTf = RPpasswordPf.getPassword();
        char[] passwordConfirmed = RPconfirmPasswordPf.getPassword();


        //TODO zrobic walidacje pol w oddzielnych funkcjach

        if (isUservalid(firstNameFromTf, lastNameFromTf, loginFromTf, String.valueOf(passwordFromTf), String.valueOf(passwordConfirmed), emailFromTf)) {
            try {
                User checkingUser = new UserController().checkUser(loginFromTf, emailFromTf);
                JOptionPane.showMessageDialog(registerPanel,
                        "Użytkownik o podanym email/loginie już istnieje, spróbuj ponownie.",
                        "Niepowodzenie",
                        JOptionPane.ERROR_MESSAGE);
                checkingUser = null;
            }
            catch (SQLException e) {
                new UserController().addUser(loginFromTf, String.valueOf(passwordFromTf), emailFromTf, firstNameFromTf, lastNameFromTf);
                JOptionPane.showMessageDialog(registerPanel,
                        "Udał się utworzyć użytkownika, teraz możesz się zalgować do aplikacji.",
                        "Powodzenie",
                        JOptionPane.INFORMATION_MESSAGE);
                contextPanels.setSelectedIndex(0);
            }
        };

    }

    public MainPanel(String title) throws HeadlessException, IOException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        //LEFT MENU BUTTONS LISTENERS

        MPmenuLoginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MPmenuLoginButton.isEnabled())
                    MPloginButtonClick();
            }
        });
        MPmenuMapButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    MPmapButtonClick();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        //LOGIN PANEL BANER

        BufferedImage img = ImageIO.read(new File("BGM_v2/imgs/header-image.jpg"));
        imagePanel.setBackground(new Color(0, 0, 0, 0));
        imagePanel.setOpaque(false);
        imagePanel.setLayout(new GridLayout());
        imagePanel.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        imagePanel.add(new JLabel(new ImageIcon(img)));

        //LOGIN PANEL BUTTONS LISTENERS
        LPloginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LPloginButtonClick();
            }
        });

        LPregisterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LPregisterButtonClick();
            }
        });

        //REGISTER PANEL BUTTONS LISTENERS

        RPregisterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    createUserButtonClick();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



    }

    private boolean isUservalid(String firstName, String lastName, String login, String password, String confirmPassword, String email) {
        if(isFieldLongEnough(firstName, lastName, login)) {
            if(isEmailValid(email)) {
                if(isPasswordValid(password)) {
                    if(arePasswordMatches(password, confirmPassword)) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(registerPanel,
                                "Podane hasła nie są takie same, spróbuj ponownie.",
                                "Niepowodzenie",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(registerPanel,
                            "Hasło ma nieprawidłowy format (<8-20> znaków\n-jedna wielka litera\n-jeden znak specjalny\n-jedna cyfra\nspróbuj ponownie.",
                            "Niepowodzenie",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(registerPanel,
                        "Adres email ma nieprawidłowy format, spróbuj ponownie.",
                        "Niepowodzenie",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(registerPanel,
                    "Długość każdego pola musi wnosić conajmniej 5 znaków, spróbuj ponownie.",
                    "Niepowodzenie",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    private boolean isEmailValid (String email) {
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean arePasswordMatches(String password, String confirmPassword) {
        return Objects.equals(password, confirmPassword);
    }

    private boolean isPasswordValid(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern  pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    private boolean isFieldLongEnough(String firstName, String lastName, String login) {
        return firstName.length() >= 5 && lastName.length() >= 5 && login.length() >= 5;
    }

}
