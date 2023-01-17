package edu.accomodation;

import com.mindfusion.common.DateTime;
import edu.accomodation.DBHandling.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomReservedPersister {
    DBConnection dbConn = new DBConnection();
    Connection conn = dbConn.getConnection();

    public List<Reservation> getAllReservationDependsOnRoomNumber(int roomNumber) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT reservations.id_reservation, id_user, check_in_date, check_out_date, total_price FROM reservations INNER JOIN Rooms_reserved ON reservations.id_reservation = rooms_reserved.id_reservation WHERE room_number = ?");

        stmt.setInt(1, roomNumber);
        ResultSet rs = stmt.executeQuery();

        List<Reservation> matchedReservations = new ArrayList<>();
        while(rs.next()) {
            Reservation listedReservation = new Reservation();
            listedReservation.setId_reservation(rs.getInt(1));
            listedReservation.setId_user(rs.getInt("id_user"));
            listedReservation.setCheck_in_date(rs.getDate("check_in_date"));
            listedReservation.setCheck_out_date(rs.getDate("check_out_date"));
            listedReservation.setTotal_price(rs.getDouble("total_price"));
            matchedReservations.add(listedReservation);
        }
        return matchedReservations;
    }

    public void addRoomReserved(int reservationId, int roomNumber, double price) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO rooms_reserved (id_reservation, room_number, price) VALUES (?, ?, ?)");
        stmt.setInt(1, reservationId);
        stmt.setInt(2, roomNumber);
        stmt.setDouble(3, price);

        stmt.executeUpdate();
    }
}
