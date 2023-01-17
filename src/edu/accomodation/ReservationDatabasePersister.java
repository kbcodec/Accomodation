package edu.accomodation;

import com.mindfusion.common.DateTime;
import edu.accomodation.DBHandling.DBConnection;

import java.sql.*;

public class ReservationDatabasePersister {
    DBConnection dbConn = new DBConnection();
    Connection conn = dbConn.getConnection();

    public void addReservation (int id_user, Date check_in_date, Date check_out_date, double total_price) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Reservations (id_user, check_in_date, check_out_date, total_price) VALUES (?, ?, ?, ?)");
        stmt.setInt(1, id_user);
        stmt.setDate(2, check_in_date);
        stmt.setDate(3, check_out_date);
        stmt.setDouble(4, total_price);

        stmt.executeUpdate();
    }

    public int getReservationId() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT TOP 1 id_reservation FROM reservations ORDER BY id_reservation DESC");
        stmt.executeQuery();
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt("id_reservation");
    }

//    public Reservation getReservationsFromRoom(int roomId) {
//        PreparedStatement stmt = conn.prepareStatement("SELECT ")
//    }
}
