package edu.accomodation;

import edu.accomodation.DBHandling.DBConnection;

import java.sql.*;

public class Hotel {

    private String name;
    private int id_hotel;
    private double latitude;
    private double longitude;
    private String category;

    //needed to connect
    static DBConnection dbConn;
    static Connection conn;
    static Statement stmt;

    //needed to query
    String query;
    ResultSet rs;

    static {
        dbConn = new DBConnection();
        conn = dbConn.getConnection();

        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public int getId_hotel() { return id_hotel; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }
    public String getCategory() {return category;}


    public Hotel(int id_hotel) throws SQLException {
        this.id_hotel = id_hotel;
        this.name = hotelName(id_hotel);
        this.latitude = latitude(id_hotel);
        this.longitude = longitude(id_hotel);
        this.category = hotelCat(id_hotel);
    }

    public Hotel() { }


    public double latitude(int id_hotel) throws SQLException {
        query = "SELECT LEFT(location_on_map, CHARINDEX(',', location_on_map)-1)  AS latitude FROM Hotels h WHERE h.id_hotel = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id_hotel);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            return rs.getFloat("latitude");
        };
        return 0.0;
    };


    public double longitude(int id_hotel) throws SQLException {
        query = "SELECT RIGHT(location_on_map, Len(location_on_map) - CHARINDEX(',', location_on_map)) as longitude FROM Hotels h WHERE h.id_hotel = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id_hotel);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            return rs.getFloat("longitude");
        };
        return 0.0;
    };


    public String hotelName(int id_hotel) throws SQLException {
        query = "SELECT name FROM Hotels h WHERE h.id_hotel = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id_hotel);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            return rs.getString("name");
        };
        return "";
    };


    public String hotelCat(int id_hotel) throws SQLException {
        query = "SELECT category FROM Hotels h WHERE h.id_hotel = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id_hotel);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            return rs.getString("category");
        };
        return "";
    };


    @Override
    public String toString() {
        return "edu.accomodation.Hotel{" +
                "name='" + name + '\'' +
                ", id_hotel=" + id_hotel +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}