package edu.accomodation.Forms;

import edu.accomodation.Interfaces.IFormLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Klasa reprezentująca główne okno aplikacji.
 */
public class MainMenu extends JFrame implements IFormLayout {
    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel menuContainer;
    private JButton logInButton;
    private JButton registerButton;
    private JButton showMapButton;
    private JButton findButton;
    private JButton exitButton;
    private JPanel imagePanel;

    public MainMenu(String title) throws HeadlessException, IOException {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        addBanner(imagePanel);

        logInButton.addActionListener(e -> {
            try {
                openLoginForm();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        registerButton.addActionListener(e -> {
            try {
                createUser();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        exitButton.addActionListener(e -> exit());
        showMapButton.addActionListener(e -> {
            try {
                showMap();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        findButton.addActionListener(e -> openSearchForm());
    }

    /**
     * Metoda wyświetlająca mapę hoteli.
     */
    private void showMap() throws SQLException {
        this.setVisible(false);
        new MapPanel("Mapa hoteli", null).setVisible(true);
    }

    /**
     * Metoda tworząca nowego użytkownika
     */
    private void createUser() throws IOException {
        this.dispose();
        new RegisterForm("Rejestracja").setVisible(true);
    }

    /**
     * Metoda wyświetlająca okno logowania.
     */
    void openLoginForm() throws IOException {
        this.dispose();
        new LoginForm("Logowanie").setVisible(true);
    }

    /**
     * Metoda zamykająca aplikację.
     */
    void exit(){
        this.dispose();
    }

    /**
     * Metoda wyświetlająca okno wyszukiwania hoteli.
     */
    void openSearchForm() {
        this.dispose();
        new FindHotelForm("Znajdź pokój", null).setVisible(true);
    }

}
