package edu.accomodation.DatabaseTablesRepresentations;

import com.mindfusion.common.DateTime;

import java.sql.Date;

public class Reservation {
    private int id_reservation;
    private int id_user;
    private Date check_in_date;
    private Date check_out_date;
    private double total_price;


    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
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

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

}
