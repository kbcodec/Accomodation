package edu.accomodation.Forms;

import edu.accomodation.UserHandling.User;
import edu.accomodation.UserHandling.UserController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterForm extends JFrame{
    private JPanel registerPanel;
    private JTextField loginTextField;
    private JButton registerButton;
    private JPasswordField passwordPasswordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailTextField;
    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel menuContainer;
    private JButton backButton;

    public RegisterForm(String title) throws HeadlessException, IOException {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createUser();
                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        backButton.addActionListener(e -> {
            try {
                backToMenu();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    void backToMenu() throws IOException {
        this.dispose();
        new MainMenu("Accomodation").setVisible(true);
    }

    void createUser() throws SQLException, IOException {
        String firstNameFromTf = firstNameTextField.getText();
        String lastNameFromTf = lastNameTextField.getText();
        String emailFromTf = emailTextField.getText();
        String loginFromTf = loginTextField.getText();
        char[] passwordFromTf = passwordPasswordField.getPassword();
        char[] passwordConfirmed = confirmPasswordField.getPassword();


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
                this.dispose();
                new LoginForm("Logowanie").setVisible(true);
            }
        };
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
