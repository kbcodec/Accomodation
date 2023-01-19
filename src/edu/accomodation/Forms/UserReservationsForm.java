package edu.accomodation.Forms;

import edu.accomodation.DatabaseTablesRepresentations.User;
import edu.accomodation.Interfaces.IFormLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UserReservationsForm extends JFrame implements IFormLayout {

    private JPanel MainP;
    private JPanel titlePanel;
    private JPanel imagePanel;
    private JTable reservationsTable;
    private User loggedUser;

    public UserReservationsForm(String title, User loggedUser) throws HeadlessException, IOException {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.loggedUser = loggedUser;
        addBanner(imagePanel);
    }
}
