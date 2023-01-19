package edu.accomodation.Forms;

import edu.accomodation.UserHandling.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MainMenuLogged extends JFrame{
    private JPanel titlePanel;
    private JPanel menuContainer;
    private JButton logOutButton;
    private JButton showMapButton;
    private JButton findButton;
    private JButton exitButton;
    private JPanel MainP;
    private JPanel imagePanel;

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

    }

    void addBanner(JPanel imagePanel) throws IOException {
        BufferedImage img = ImageIO.read(new File("imgs/header-image.jpg"));
        imagePanel.setBackground(new Color(0, 0, 0, 0));
        imagePanel.setOpaque(false);
        imagePanel.setLayout(new GridLayout());
        imagePanel.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        imagePanel.add(new JLabel(new ImageIcon(img)));
    }

    void logOut(User user) throws IOException {
        user = null;
        this.dispose();
        new MainMenu("Accomodation").setVisible(true);
    }

    void exit() {
        this.dispose();
    }

    void showMap(User user) throws SQLException {
        new MapPanel("Mapa hoteli", user).setVisible(true);
        this.setVisible(false);
    }
}
