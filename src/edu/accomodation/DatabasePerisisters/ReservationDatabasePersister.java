package edu.accomodation.DatabasePerisisters;

import com.mindfusion.common.DateTime;
import edu.accomodation.DBHandling.DBConnection;

import java.sql.*;

/*
 * Klasa, która współdziała z bazą danych w celu odczytywania i pobierania informacji o rezerwacjach
 */
public class ReservationDatabasePersister {

    /**
     * Obiekt DBConnection służący do nawiązywania połączenia z bazą danych.
     */
    DBConnection dbConn = new DBConnection();

    /**
     * Obiekt Connection używany do wykonywania instrukcji SQL
     */
    Connection conn = dbConn.getConnection();

    /**
     * Metoda do dodawania nowej rezerwacji do tabeli "Reservations".
     * @param id_user identyfikator użytkownika ktory dokonuje rezerwacji
     * @param check_in_date data zakwaterowania
     * @param check_out_date data wykwaterowania
     * @param total_price całkowita cena rezerwacji
     * @throws SQLException, jeśli wystąpi błąd bazy danych
     */
    public void addReservation (int id_user, Date check_in_date, Date check_out_date, double total_price) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Reservations (id_user, check_in_date, check_out_date, total_price) VALUES (?, ?, ?, ?)");
        stmt.setInt(1, id_user);
        stmt.setDate(2, check_in_date);
        stmt.setDate(3, check_out_date);
        stmt.setDouble(4, total_price);

        stmt.executeUpdate();
    }

    /**
     * Metoda do pobierania ID ostatniej rezerwacji dodanej do tabeli "Reservations"
     * @return int reprezentujący ID ostatniej rezerwacji dodanej do tabeli
     * @throws SQLException jeśli wystąpi błąd dostępu do bazy danych
     */
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
