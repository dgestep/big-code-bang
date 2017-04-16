package com.${companyName}.${productName}.model;

import com.${companyName}.${productName}.model.exception.SystemLoggedException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utility class with common helper methods.
 *
 * @author ${codeAuthor}.
 */
public final class ModelHelper {

    /**
     * Default private constructor.
     */
    private ModelHelper() {
    }

    /**
     * Returns a Calendar object with the date set to the supplied date.
     *
     * @param dt the date in YYYY-MM-DD format.
     * @return the Calendar object.
     */
    public static Calendar getCalendarUsing(final String dt) {
        try {
            final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final Date startDt = df.parse(dt);
            final Calendar cal = new GregorianCalendar();
            cal.setTime(startDt);
            return cal;
        }
        catch (final ParseException pe) {
            throw new SystemLoggedException(pe);
        }
    }

    /**
     * Returns a Date object with the date set to the supplied date.
     *
     * @param dt the date in YYYY-MM-DD format.
     * @return the date.
     */
    public static Date getDateUsing(final String dt) {
        return getCalendarUsing(dt).getTime();
    }

    /**
     * Returns a Date object with the date set to the supplied date.
     *
     * @param dt     the date in YYYY-MM-DD format.
     * @param hour   the hour.
     * @param minute the minute.
     * @param second the second.
     * @return the date.
     */
    public static Date getDateUsing(final String dt, final int hour, final int minute, final int second) {
        return getCalendarUsing(dt, hour, minute, second).getTime();
    }

    /**
     * Returns a Calendar object with the date set to the supplied date.
     *
     * @param dt     the date in YYYY-MM-DD format.
     * @param hour   the hour.
     * @param minute the minute.
     * @param second the second.
     * @return the Calendar.
     */
    public static Calendar getCalendarUsing(final String dt, final int hour, final int minute, final int second) {
        try {
            final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final Date startDt = df.parse(dt);
            return getCalendarUsing(startDt, hour, minute, second);
        }
        catch (final ParseException pe) {
            throw new SystemLoggedException(pe);
        }
    }

    /**
     * Returns a Date object with the date set to the supplied date.
     *
     * @param dt     the date in YYYY-MM-DD format.
     * @param hour   the hour.
     * @param minute the minute.
     * @param second the second.
     * @return the date.
     */
    public static Date getDateUsing(final Date dt, final int hour, final int minute, final int second) {
        final Calendar cal = getCalendarUsing(dt, hour, minute, second);
        return cal.getTime();
    }

    /**
     * Returns a Calendar object with the date set to the supplied date.
     *
     * @param dt     the date in YYYY-MM-DD format.
     * @param hour   the hour.
     * @param minute the minute.
     * @param second the second.
     * @return the Calendar.
     */
    public static Calendar getCalendarUsing(final Date dt, final int hour, final int minute, final int second) {
        final Calendar cal = new GregorianCalendar();
        cal.setTime(dt);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal;
    }
}
