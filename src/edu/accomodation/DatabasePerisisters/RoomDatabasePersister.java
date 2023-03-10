package edu.accomodation.DatabasePerisisters;

import edu.accomodation.DBHandling.DBConnection;
import edu.accomodation.DatabaseTablesRepresentations.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Klasa, która odpowiada za wykonywanie operacji na tabeli "Rooms" bazy danych
 */
public class RoomDatabasePersister {
    /**
     * Obiekt DBConnection służący do nawiązywania połączenia z bazą danych.
     */
    DBConnection dbConn = new DBConnection();

    /**
     * Obiekt Connection używany do wykonywania instrukcji SQL
     */
    Connection conn = dbConn.getConnection();


    /**
     * Metoda zwracająca listę pokoi danego hotelu na podstawie jego ID
     * @param ID id hotelu
     * @return matchesRooms lista pokoi danego hotelu
     * @throws SQLException jeśli wystąpi błąd dostępu do bazy danych.
     */
    public List<Room> listRoomsByHotelId(int ID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT room_number, type, max_occupancy, price_per_night, id_hotel FROM Rooms WHERE id_hotel = ?");
        stmt.setInt(1, ID);
        ResultSet rs = stmt.executeQuery();
        List<Room> matchesRooms = new ArrayList<>();

        while(rs.next()){
            Room listedRoom = new Room();
            listedRoom.setRoom_number(rs.getInt("room_number"));
            listedRoom.setType(rs.getString("type"));
            listedRoom.setMax_occupancy(rs.getInt("max_occupancy"));
            listedRoom.setPrice_per_night(rs.getInt("price_per_night"));
            listedRoom.setId_hotel(rs.getInt("id_hotel"));
            matchesRooms.add(listedRoom);
        }

        return matchesRooms;
    }


    /**
     * Metoda zwracająca listę wszystkich pokoi z nazwą hotelu i lokalizacją.
     * @return  allRooms lista wszystkich danych o pokojach z nazwą hotelu i lokalizacją.
     * @throws SQLException jeśli wystąpi błąd dostępu do bazy danych.
     */
    public List<Room> listAllRoomsWithHotelNameAndLocalization() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select h.name, a.city, r.room_number, r.max_occupancy, r.price_per_night from rooms r\n" +
                " inner join Hotels h on r.id_hotel = h.id_hotel\n" +
                " inner join Address a on h.id_address = a.id_address\n" +
                " order by city;");

        ResultSet rs = stmt.executeQuery();
        List<Room> allRooms = new ArrayList<>();

        while(rs.next()) {
            Room listedRoom = new Room();
            listedRoom.setHotelName(rs.getString(1));
            listedRoom.setCity(rs.getString(2));
            listedRoom.setRoom_number(rs.getInt(3));
            listedRoom.setMax_occupancy(rs.getInt(4));
            listedRoom.setPrice_per_night(rs.getInt(5));
            allRooms.add(listedRoom);
        }

        return allRooms;
    }

    /**
     * Metoda zwraca listę pokoi spełniających podane przez użytkownika parametry.
     * @param city - miasto, w którym poszukiwany jest pokój
     * @param dateFrom - data rozpoczęcia rezerwacji
     * @param dateTo - data końca rezerwacji
     * @param maxOccupancy - maksymalna liczba osób jaką pokój może pomieścić
     * @return result - zwraca listę pokoi (obiektów Room) spełniających podane parametry
     * @throws SQLException - w przypadku błędu połączenia z bazą danych
     */
    public List<Room> listRoomsDependsOfUserParameters(String city, Date dateFrom, Date dateTo, int maxOccupancy) throws SQLException {
        String sql = "select distinct h.name, a.city, r.room_number, r.max_occupancy, r.price_per_night\n" +
                     "                from rooms r\n" +
                     "                inner join Hotels h on r.id_hotel = h.id_hotel\n" +
                     "                inner join Address a on h.id_address = a.id_address\n" +
                     "                left join Rooms_reserved rr on r.room_number = rr.room_number\n" +
                     "                left join Reservations res on res.id_reservation = rr.id_reservation\n" +
                     "\n" +
                     "                where ((? not between res.check_in_date and res.check_out_date) and (? not between res.check_in_date and res.check_out_date)\n" +
                     "                AND res.check_in_date not between ? and ?\n" +
                     "                AND res.check_out_date not between ? and ?\n" +
                     "                OR (res.check_in_date is null or res.check_out_date is null))\n" +
                     "                and a.city = ?\n" +
                     "                and r.max_occupancy >= ?\n" +
                     "                \n" +
                     "                order by r.price_per_night;";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setDate(1, dateFrom);
        stmt.setDate(2, dateTo);
        stmt.setDate(3, dateFrom);
        stmt.setDate(4, dateTo);
        stmt.setDate(5, dateFrom);
        stmt.setDate(6, dateTo);
        stmt.setString(7, city);
        stmt.setInt(8, maxOccupancy);

        ResultSet rs = stmt.executeQuery();
        List<Room> result = new ArrayList<>();

        while(rs.next()) {
            Room listedRoom = new Room();
            listedRoom.setHotelName(rs.getString(1));
            listedRoom.setCity(rs.getString(2));
            listedRoom.setRoom_number(rs.getInt(3));
            listedRoom.setMax_occupancy(rs.getInt(4));
            listedRoom.setPrice_per_night(rs.getInt(5));
            result.add(listedRoom);
        }

        return result;

    }
}
