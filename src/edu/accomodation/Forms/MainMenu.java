package edu.accomodation.Forms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MainMenu extends JFrame{
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
    }

    private void showMap() throws SQLException {
        this.setVisible(false);
        new MapPanel("Mapa hoteli", null).setVisible(true);
    }

    private void createUser() throws IOException {
        this.dispose();
        new RegisterForm("Rejestracja").setVisible(true);
    }

    void addBanner(JPanel imagePanel) throws IOException {
        BufferedImage img = ImageIO.read(new File("imgs/header-image.jpg"));
        imagePanel.setBackground(new Color(0, 0, 0, 0));
        imagePanel.setOpaque(false);
        imagePanel.setLayout(new GridLayout());
        imagePanel.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        imagePanel.add(new JLabel(new ImageIcon(img)));
    }

    void openLoginForm() throws IOException {
        this.dispose();
        new LoginForm("Logowanie").setVisible(true);
    }
    void exit(){
        this.dispose();
    }

}
