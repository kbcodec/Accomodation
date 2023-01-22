package edu.accomodation.DatabaseTablesRepresentations;

import com.mindfusion.common.DateTime;

import java.sql.Date;

/**
 * Klasa reprezentująca tabele "Reservations" z bazy danych
 */
public class Reservation {

    /** Unikalny identyfikator rezerwacji */
    private int id_reservation;

    /** Unikalny identyfikator klienta */
    private int id_user;

    /** data początku rezerwacji */
    private Date check_out_date;

    /** data końca rezerwacji */
    private Date check_in_date;

    /** sumaryczny koszt rezerwacji */
    private double total_price;

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(Date check_in_date) {
        this.check_in_date = check_in_date;
    }

    public Date getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(Date check_out_date) {
        this.check_out_date = check_out_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }


    /**
     * metoda, która zwraca ciąg reprezentujący rezerwacje
     * @return ciąg reprezentujący rezerwacje
     */
    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", id_user=" + id_user +
                ", check_in_date=" + check_in_date +
                ", check_out_date=" + check_out_date +
                ", total_price=" + total_price +
                '}';
    }
}
