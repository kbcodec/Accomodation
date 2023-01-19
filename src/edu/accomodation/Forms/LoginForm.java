package edu.accomodation.Forms;

import edu.accomodation.DatabaseTablesRepresentations.User;
import edu.accomodation.DatabasePerisisters.UserPerisister;
import edu.accomodation.Interfaces.IFormLayout;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class LoginForm extends JFrame implements IFormLayout {
    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel menuContainer;
    private JButton logInButton;
    private JButton backButton;
    private JPanel imagePanel;
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private User loggedUser;

    public LoginForm(String title) throws HeadlessException, IOException {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        addBanner(imagePanel);

        backButton.addActionListener(e -> {
            try {
                backToMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        logInButton.addActionListener(e -> {
            loggedUser = new User();
            logInUser();
        });
    }

    void backToMenu() throws IOException {
        this.dispose();
        new MainMenu("Accomodation").setVisible(true);
    }

    void logInUser() {
        String loginFromTf = loginTextField.getText();
        char[] passwordFromPf = passwordTextField.getPassword();

        try {
            loggedUser = new UserPerisister().readUser(loginFromTf);
            if(BCrypt.checkpw(String.valueOf(passwordFromPf), loggedUser.getPassword()))
            {
                JOptionPane.showMessageDialog(MainP, "Logowanie powiodło się", "Dostęp przyznany", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                new MainMenuLogged("Accomodation", loggedUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(MainP, "Nieprawidłowe dane", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(MainP, "Nieprawidłowe dane", "Spróbuj ponownie", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
