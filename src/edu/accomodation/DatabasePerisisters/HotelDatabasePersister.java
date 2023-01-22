package edu.accomodation.DatabasePerisisters;

import edu.accomodation.DBHandling.DBConnection;
import edu.accomodation.DatabaseTablesRepresentations.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    /*
    * Klasa, która współdziała z bazą danych w celu odczytywania i pobierania informacji o hotelach
    */
public class HotelDatabasePersister {

    /**
     * Obiekt DBConnection służący do nawiązywania połączenia z bazą danych.
     */
    DBConnection dbConn = new DBConnection();

    /**
     * Obiekt Connection używany do wykonywania instrukcji SQL
     */
    Connection conn = dbConn.getConnection();


    /**
     * Odczytuje pojedynczy hotel z bazy danych o podanym ID.
     * @param ID identyfikator hotelu do odczytania
     * @return listedHotel obiekt Hotel zawierający wszystkie szczegóły z tabeli „Hotels”
     * @throws SQLException, jeśli wystąpi błąd bazy danych
     */
    public Hotel readHotel(int ID) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("SELECT id_hotel, name, description, type, category, id_address, phone, web_page, email, LEFT(location_on_map, CHARINDEX(',', location_on_map)-1)  AS latitude, RIGHT(location_on_map, Len(location_on_map) - CHARINDEX(',', location_on_map)) as longitude, max_guest, num_of_rooms, picture1, picture2, picture3 FROM Hotels WHERE id_hotel = ?");
        stmt.setInt(1, ID);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        Hotel listedHotel = new Hotel();

        listedHotel.setId_hotel(rs.getInt("id_hotel"));
        listedHotel.setName(rs.getString("name"));
        listedHotel.setDescription(rs.getString("description"));
        listedHotel.setType(rs.getString("type"));
        listedHotel.setCategory(rs.getString("category"));
        listedHotel.setId_address(rs.getInt("id_address"));
        listedHotel.setPhone(rs.getString("phone"));
        listedHotel.setWeb(rs.getString("web_page"));
        listedHotel.setEmail(rs.getString("email"));
        listedHotel.setLatitude(rs.getDouble("latitude"));
        listedHotel.setLongitude(rs.getDouble("longitude"));
        listedHotel.setMax_quest(rs.getInt("max_guest"));
        listedHotel.setNum_of_rooms(rs.getInt("num_of_rooms"));
        listedHotel.setPicture1(rs.getString("picture1"));
        listedHotel.setPicture2(rs.getString("picture2"));
        listedHotel.setPicture3(rs.getString("picture3"));
        listedHotel.setAddress(new AddressDatabasePersister().readAddress(rs.getInt("id_address")));


        return listedHotel;
    }



    /**
     * Odczytuje pojedynczy hotel z bazy danych o podanej nazwie
     * @param hotelName nazwa hotelu do odczytania
     * @return result ID hotelu z tabeli „Hotels”
     * @throws SQLException, jeśli wystąpi błąd bazy danych
     */
    public int getHotelIdByName(String hotelName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id_hotel FROM Hotels WHERE name = ?");
        stmt.setString(1, hotelName);

        ResultSet rs = stmt.executeQuery();
        int result = 0;

        while(rs.next()) {
            result = rs.getInt("id_hotel");
        }
        return result;
    }

    /**
     * Wyświetla listę wszystkich hoteli z bazy danych.
     * @return matchesHotels lista obiektów Hotels zawierających wszystkie hotele z bazy danych
     * @throws SQLException, jeśli wystąpi błąd bazy danych
     */
    public List<Hotel> listHotels() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id_hotel, name, description, type, category, id_address, phone, web_page, email, LEFT(location_on_map, CHARINDEX(',', location_on_map)-1)  AS latitude, RIGHT(location_on_map, Len(location_on_map) - CHARINDEX(',', location_on_map)) as longitude, max_guest, num_of_rooms, picture1, picture2, picture3 FROM Hotels;");

        ResultSet rs = stmt.executeQuery();
        List<Hotel> matchesHotels = new ArrayList<>();

        while(rs.next()) {
            Hotel listedHotel = new Hotel();
            listedHotel.setId_hotel(rs.getInt("id_hotel"));
            listedHotel.setName(rs.getString("name"));
            listedHotel.setDescription(rs.getString("description"));
            listedHotel.setType(rs.getString("type"));
            listedHotel.setCategory(rs.getString("category"));
            listedHotel.setId_address(rs.getInt("id_address"));
            listedHotel.setPhone(rs.getString("phone"));
            listedHotel.setWeb(rs.getString("web_page"));
            listedHotel.setEmail(rs.getString("email"));
            listedHotel.setLatitude(rs.getDouble("latitude"));
            listedHotel.setLongitude(rs.getDouble("longitude"));
            listedHotel.setMax_quest(rs.getInt("max_guest"));
            listedHotel.setNum_of_rooms(rs.getInt("num_of_rooms"));
            listedHotel.setPicture1(rs.getString("picture1"));
            listedHotel.setPicture2(rs.getString("picture2"));
            listedHotel.setPicture3(rs.getString("picture3"));
            listedHotel.setAddress(new AddressDatabasePersister().readAddress(rs.getInt("id_address")));
            matchesHotels.add(listedHotel);
        }

        return matchesHotels;
    }
}
