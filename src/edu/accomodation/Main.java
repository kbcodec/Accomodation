package edu.accomodation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        List<Room> rooms = new RoomDatabasePersister().listRoomsByHotelId(10001);
        for (Room r :
                rooms) {
            System.out.println(r.toString());
        }

        //formatka
        new MainPanel("Accomodation").setVisible(true);

    }
}
