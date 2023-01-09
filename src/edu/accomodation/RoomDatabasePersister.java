package edu.accomodation;

import edu.accomodation.DBHandling.DBConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDatabasePersister {
    DBConnection dbConn = new DBConnection();
    Connection conn = dbConn.getConnection();

    public Room readRoom(int ID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT room_number, type, max_occupancy, price_per_night, id_hotel FROM Rooms WHERE room_number = ?");
        stmt.setInt(1, ID);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        Room listedRoom = new Room();

        listedRoom.setRoom_number(rs.getInt("room_number"));
        listedRoom.setType(rs.getString("type"));
        listedRoom.setMax_occupancy(rs.getInt("max_occupancy"));
        listedRoom.setPrice_per_night(rs.getInt("price_per_night"));
        listedRoom.setId_hotel(rs.getInt("id_hotel"));
        listedRoom.setHotel(new HotelDatabasePersister().readHotel(rs.getInt("id_hotel")));

        return listedRoom;
    }

    public List<Room> listRooms() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT room_number, type, max_occupancy, price_per_night, id_hotel FROM Rooms");

        ResultSet rs = stmt.executeQuery();
        List<Room> matchesRooms = new ArrayList<>();

        while(rs.next()){
            Room listedRoom = new Room();
            listedRoom.setRoom_number(rs.getInt("room_number"));
            listedRoom.setType(rs.getString("type"));
            listedRoom.setMax_occupancy(rs.getInt("max_occupancy"));
            listedRoom.setPrice_per_night(rs.getInt("price_per_night"));
            listedRoom.setId_hotel(rs.getInt("id_hotel"));
            listedRoom.setHotel(new HotelDatabasePersister().readHotel(rs.getInt("id_hotel")));
            matchesRooms.add(listedRoom);
        }

        return matchesRooms;
    }

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
}
