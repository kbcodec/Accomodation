package edu.accomodation;

import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.model.Appointment;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    private Calendar calendar;
    private DateTime dateFrom;
    private DateTime dateTo;

    public Utility(Calendar calendar, DateTime dateFrom, DateTime dateTo) {
        this.calendar = calendar;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

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

    public void removeProgresses() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
