package edu.accomodation.UserHandling;

import edu.accomodation.DBHandling.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserController {
    DBConnection dbConn = new DBConnection();
    Connection conn = dbConn.getConnection();

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
