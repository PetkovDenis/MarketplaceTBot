package ru.ws.marketplace.handler.date;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class DateHandler {

    public Calendar getStartDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 0);
        return calendar;
    }

    public Calendar getEndDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 30);
        return calendar;
    }

}
