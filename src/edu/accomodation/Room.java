package edu.accomodation;

public class Room {
    private int room_number;
    private String type;
    private int max_occupancy;
    private int price_per_night;
    private int id_hotel;
    private Hotel hotel;

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

    @Override
    public String toString() {
        String typeFormatting = String.format("%30s", type);
        String roomNumberFormatting = String.format("%10d", room_number);
        String occupancyFormatting = String.format("%10d", max_occupancy);
        return roomNumberFormatting + typeFormatting + occupancyFormatting;
    }
}
