package edu.accomodation.DatabaseTablesRepresentations;

import java.sql.Date;

/**
 * Klasa reprezentująca tabele Room z bazy danych
 */
public class Room {

    /** Numer pokoju */
    private int room_number;

    /** Rodzaj pokoju */
    private String type;

    /** Maksymalne obłożenie pokoju */
    private int max_occupancy;

    /** Cena za dobę pokoju */
    private int price_per_night;

    /** Identyfikator hotelu, do którego należy pokój*/
    private int id_hotel;

    /** Obiekt hotelowy, do którego należy pokój*/
    private Hotel hotel;

    /** Nazwa hotelu, do którego należy pokój */
    private String hotelName;

    /** Miasto, w którym znajduje się hotel  */
    private String city;
    private Date checkInDate;
    private Date checkOutDate;
    private int reservationId;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMax_occupancy() {
        return max_occupancy;
    }

    public void setMax_occupancy(int max_occupancy) {
        this.max_occupancy = max_occupancy;
    }

    public int getPrice_per_night() {
        return price_per_night;
    }

    public void setPrice_per_night(int price_per_night) {
        this.price_per_night = price_per_night;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public String getHotelName() {return hotelName;}
    public void setHotelName(String hotelName) {this.hotelName = hotelName;}
    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

}
