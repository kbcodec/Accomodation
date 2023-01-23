package edu.accomodation.DatabaseTablesRepresentations;

/**
 * Klasa reprezentująca tabele Room_reserved z bazy danych
 */
public class RoomReserved {
    /** unikalny identyfikator rezerwacji pokoju */
    private int id_room_reserved;

    /** unikalny identyfikator rezerwacji powiązanej z pokojem */
    private int id_reservation;

    /** numer pokoju, który jest rezerwowany */
    private int room_number;

    /** cena pokoju za dobę */
    private double price;

    public int getId_room_reserved() {
        return id_room_reserved;
    }

    public void setId_room_reserved(int id_room_reserved) {
        this.id_room_reserved = id_room_reserved;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
