package edu.accomodation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {


//TESTS
//        Hotel hotel = new HotelDatabasePersister().readHotel(10002);
//        List<Hotel> hotels= new HotelDatabasePersister().listHotels();
//        Address address = new AddressDatabasePersister().readAddress(20002);
//        List<Address> addresses = new AddressDatabasePersister().listAddresses();
//        System.out.println(hotel.toString());
//        System.out.println(address.toString());

//        for (Address a :
//                addresses) {
//            System.out.println(a.toString());
//        }

//        for (Hotel h :
//                hotels) {
//            System.out.println(h.toString());
//        }

        //formatka
        new MainPanel("Accomodation").setVisible(true);

    }
}
