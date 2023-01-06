package UserHandling;

import DBHandling.DBConnection;

import javax.xml.transform.Result;
import java.net.ConnectException;
import java.sql.*;

public class User {
    public User(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String name, char[] password) {
        this.login = login;
        this.password = password;
    }


    private String login;

    private String name;
    private char[] password;

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public char[] getPassword() {
        return password;
    }

    public void setLogin(String login) throws SQLException {
        Statement stmt = createOwnStatement();
        ResultSet rs = stmt.executeQuery("SELECT login from users where login = " + login);
        this.login = rs.getString("login");
    }

    public void setName(String login) throws SQLException {
        DBConnection dbConn = new DBConnection();
        Connection conn = dbConn.getConnection();

        String insertString = "SELECT firstName from users where login = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(insertString)) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                this.name = rs.getString(1);
            }
        }

    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public boolean isUserExist() throws SQLException {
        Statement stmt = createOwnStatement();

        ResultSet rs = stmt.executeQuery("SELECT login FROM USERS");

        while (rs.next()) {
            if (login.equals(rs.getString("login"))) {
                return true;
            }
        }
        return false;
    }

    public void addUserToDb() throws SQLException {
        DBConnection dbConn = new DBConnection();
        Connection conn = dbConn.getConnection();

        String insertString = "INSERT INTO USERS (login, email, firstName, lastName, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertString)) {
            pstmt.setString(1, login);
            pstmt.executeUpdate();
        }
    }

    private Statement createOwnStatement() throws SQLException {
        DBConnection dbConn = new DBConnection();
        Connection conn = dbConn.getConnection();
        return conn.createStatement();
    }

}
