package edu.accomodation.MapHandling;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.model.Appointment;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa służy do aktualizowania postępu rezerwacji pokoju.
 */
public class Utility {

    /** obiekt kalendarza, w którym będzie aktualizowany postęp rezerwacji */
    private Calendar calendar;

    /** data rozpoczęcia rezerwacji */
    private DateTime dateTo;

    /**  data zakończenia rezerwacji */
    private DateTime dateFrom;

    /**
     * Konstruktor przypisujący dane rezerwacji
     */
    public Utility(Calendar calendar, DateTime dateFrom, DateTime dateTo) {
        this.calendar = calendar;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    /**
     * Metoda odpowiedzialna za aktualizację postępu rezerwacji
     * @param roomNumber - numer rezerwowanego pokoju
     */
    public void updateProgress(Integer roomNumber) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Appointment app = new Appointment();
                app.setStartTime(dateFrom);
                app.setEndTime(dateTo);
                app.setHeaderText("Rezerwacja pokoju " + roomNumber);
                calendar.getSchedule().getItems().add(app);
            }
        });
    }
}
