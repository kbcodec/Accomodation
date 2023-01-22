package edu.accomodation.Forms;

import edu.accomodation.DatabaseTablesRepresentations.User;
import edu.accomodation.Interfaces.IFormLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Klasa reprezentuje menu główne po zalogowaniu.
 * Zawiera przyciski do wylogowania, wyświetlenia mapy hoteli, wyjścia z programu,
 * wyszukania hotelu oraz przeglądnięcia swoich rezerwacji.
 */
public class MainMenuLogged extends JFrame implements IFormLayout {
    private JPanel titlePanel;
    private JPanel menuContainer;
    private JButton logOutButton;
    private JButton showMapButton;
    private JButton findButton;
    private JButton exitButton;
    private JPanel MainP;
    private JPanel imagePanel;
    private JButton userReservationsBtn;

    private User loggedUser;

    public MainMenuLogged(String title, User loggedUser) throws HeadlessException, IOException {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.loggedUser = loggedUser;

        addBanner(imagePanel);

        logOutButton.addActionListener(e -> {
            try {
                logOut(loggedUser);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        showMapButton.addActionListener(e -> {
            try {
                showMap(loggedUser);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        exitButton.addActionListener(e -> exit());
        findButton.addActionListener(e -> openSearchForm(loggedUser));
        userReservationsBtn.addActionListener(e -> {
            try {
                checkYourReservations(loggedUser);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    /**
     * Metoda checkYourReservations otwiera okno z informacjami o rezerwacjach zalogowanego użytkownika.
     * @param loggedUser Obiekt klasy User reprezentujący zalogowanego użytkownika
     */
    private void checkYourReservations(User loggedUser) throws IOException {
        new UserReservationsForm("Twoje rezerwacje", loggedUser).setVisible(true);
        this.dispose();
    }

    /**
     * Metoda logOut wylogowuje użytkownika i przekierowuje do menu głównego.
     * @param user Obiekt klasy User reprezentujący zalogowanego użytkownika
     */
    void logOut(User user) throws IOException {
        user = null;
        this.dispose();
        new MainMenu("Accomodation").setVisible(true);
    }

    /**
     * Metoda służy do zamknięcia okna aplikacji
     */
    void exit() {
        this.dispose();
    }

    /**
     * Metoda wyświetlenia mapy hoteli.
     * @param user Obiekt klasy User reprezentujący zalogowanego użytkownika
     */
    void showMap(User user) throws SQLException {
        new MapPanel("Mapa hoteli", user).setVisible(true);
        this.setVisible(false);
    }

    /**
     * Metoda otwarcia formularza wyszukiwania hotelu.
     * @param user Obiekt klasy User reprezentujący zalogowanego użytkownika
     */
    void openSearchForm(User user) {
        new FindHotelForm("Znajdź pokój", user).setVisible(true);
        this.setVisible(false);
    }
}
