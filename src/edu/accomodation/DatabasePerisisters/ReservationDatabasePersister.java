package edu.accomodation.DatabasePerisisters;

import edu.accomodation.DBHandling.DBConnection;
import edu.accomodation.DatabaseTablesRepresentations.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean isReservationPossible(int room_number, Date check_in_date, Date check_out_date) throws SQLException {
        String query = "select rr.room_number, res.check_in_date, res.check_out_date\n" +
                "from Rooms_reserved rr\n" +
                "inner join Reservations res on res.id_reservation = rr.id_reservation\n" +
                "                     \n" +
                "where ((? between res.check_in_date and res.check_out_date) or (? between res.check_in_date and res.check_out_date)\n" +
                "or res.check_in_date between ? and ?\n" +
                "or res.check_out_date between ? and ?)\n" +
                "AND rr.room_number = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDate(1, check_in_date);
        stmt.setDate(2, check_out_date);
        stmt.setDate(3, check_in_date);
        stmt.setDate(4, check_out_date);
        stmt.setDate(5, check_in_date);
        stmt.setDate(6, check_out_date);
        stmt.setInt(7, room_number);

        stmt.executeQuery();
        ResultSet rs = stmt.executeQuery();
        return !rs.next();
    }

    public List<Room> getAllReservationsForLoggedUser(int userId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select h.name, a.city, r.room_number, res.check_in_date, res.check_out_date, res.id_reservation\n" +
                "from hotels h\n" +
                "inner join address a on h.id_address = a.id_address\n" +
                "inner join rooms r on h.id_hotel = r.id_hotel\n" +
                "inner join rooms_reserved rr on r.room_number = rr.room_number\n" +
                "inner join Reservations res on rr.id_reservation = res.id_reservation\n" +
                "where res.id_user = ?");
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        List<Room> matchedRooms = new ArrayList<>();
        while(rs.next()) {
            Room listedRoom = new Room();
            listedRoom.setHotelName(rs.getString(1));
            listedRoom.setCity(rs.getString(2));
            listedRoom.setRoom_number(rs.getInt(3));
            listedRoom.setCheckInDate(rs.getDate(4));
            listedRoom.setCheckOutDate(rs.getDate(5));
            listedRoom.setReservationId(rs.getInt(6));
            matchedRooms.add(listedRoom);
        }

        return matchedRooms;
    }

    public void deleteReservationRecord (int reservationId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE from Reservations where id_reservation = ?");
        stmt.setInt(1, reservationId);
        stmt.executeUpdate();
    }
}
