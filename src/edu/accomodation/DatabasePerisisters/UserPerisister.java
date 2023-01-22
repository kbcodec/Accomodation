package edu.accomodation.DatabasePerisisters;

import edu.accomodation.DBHandling.DBConnection;
import edu.accomodation.DatabaseTablesRepresentations.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserPerisister {

    /**
     * Obiekt DBConnection służący do nawiązywania połączenia z bazą danych.
     */
    DBConnection dbConn = new DBConnection();

    /**
     * Obiekt Connection używany do wykonywania instrukcji SQL
     */
    Connection conn = dbConn.getConnection();


    /**
     * Metoda pobierająca użytkownika o podanym loginie z bazy danych
     * @param login login użytkownika
     * @return listedUser Obiekt klasy User
     * @throws SQLException w przypadku błędu z bazą danych
     */
    public User readUser(String login) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id_user, login, password, email, firstName, lastName from Users where login = ?");
        stmt.setString(1, login);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        User listedUser = new User();
        listedUser.setId(rs.getInt("id_user"));
        listedUser.setLogin(rs.getString("login"));
        listedUser.setPassword(rs.getString("password"));
        listedUser.setEmail(rs.getString("email"));
        listedUser.setFirstName(rs.getString("firstName"));
        listedUser.setLastName(rs.getString("lastName"));

        return listedUser;
    }

    /**
     * Metoda sprawdzająca czy podany login lub email już istnieje w bazie danych.
     * @param login login użytkownika
     * @param email email użytkownika
     * @return listedUser - Obiekt klasy User, jeśli znaleziono użytkownika o podanym loginie lub emailu, w przeciwnym razie null
     * @throws SQLException w przypadku błędu z bazą danych
     */
    public User checkUser(String login, String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id_user, login, password, email, firstName, lastName from Users where login = ? OR email = ?");
        stmt.setString(1, login);
        stmt.setString(2, email);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        User listedUser = new User();
        listedUser.setId(rs.getInt("id_user"));
        listedUser.setLogin(rs.getString("login"));
        listedUser.setPassword(rs.getString("password"));
        listedUser.setEmail(rs.getString("email"));
        listedUser.setFirstName(rs.getString("firstName"));
        listedUser.setLastName(rs.getString("lastName"));

        return listedUser;
    }

    /**
     * Metoda dodająca nowego użytkownika do bazy danych do tabeli "Users"
     * @param login login użytkownika
     * @param password hasło użytkownika (zostanie zaszyfrowane za pomocą BCrypt)
     * @param email email użytkownika
     * @param firstName imię użytkownika
     * @param lastName nazwisko
     */
    public void addUser(String login, String password, String email, String firstName, String lastName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (login, password, email, firstName, lastName) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, login);
        stmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
        stmt.setString(3, email);
        stmt.setString(4, firstName);
        stmt.setString(5, lastName);
        stmt.executeUpdate();
    }

}
