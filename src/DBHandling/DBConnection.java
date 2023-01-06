package DBHandling;

import java.sql.*;

public class DBConnection {
    private Connection conn;

    public DBConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://bgmbookingserver.database.windows.net:1433;database=bookingDB;user=bookingadmin@bgmbookingserver;password=VbWdLRs4bAdG8$M;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=request");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return conn;
    }




}