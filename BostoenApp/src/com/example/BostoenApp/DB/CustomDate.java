package com.example.BostoenApp.DB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by david on 24/03/2016.
 */
public class CustomDate extends Date {
    private Date date;
    private SimpleDateFormat formatter;
    private String format = "yyyy-MM-dd':'hh:mm:ss";

    /**
     *
     */
    public CustomDate() {
        Calendar c = Calendar.getInstance();
        date = c.getTime();
        formatter = new SimpleDateFormat(format);
    }


    /**
     * @param date
     */
    public CustomDate(long date) {
        this.date = new Date(date);
        formatter = new SimpleDateFormat(format);
    }


    /**
     * @param date
     */
    public CustomDate(Date date) {
        this.date = date;
        formatter = new SimpleDateFormat(format);
    }

    /**
     * @param date
     * @throws ParseException wordt opgegooid wanneer de String in een verkeerd formaat wordt opgegeven
     */
    public CustomDate(String date) throws ParseException {
        formatter = new SimpleDateFormat(format);
        this.date = formatter.parse(date);
    }

    public String toString() {
        return formatter.format(date);
    }
}
