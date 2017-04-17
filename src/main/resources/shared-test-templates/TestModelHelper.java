package com.${companyName}.${productName}.model;

import com.${companyName}.${productName}.model.exception.SystemLoggedException;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(JMockit.class)
public class TestModelHelper {

    @Test
    public void testGetCalendar() {
        Calendar calendar = ModelHelper.getCalendarUsing("2016-02-05");
        Assert.assertEquals(2016, calendar.get(Calendar.YEAR));
        Assert.assertEquals(1, calendar.get(Calendar.MONTH));
        Assert.assertEquals(5, calendar.get(Calendar.DATE));
    }

    @Test
    public void testGetDate() {
        Date date = ModelHelper.getDateUsing("2016-02-05");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Assert.assertEquals(2016, calendar.get(Calendar.YEAR));
        Assert.assertEquals(1, calendar.get(Calendar.MONTH));
        Assert.assertEquals(5, calendar.get(Calendar.DATE));
    }

    @Test
    public void testGetStringDateWithSpecifiedTime() {
        Date date = ModelHelper.getDateUsing("2016-02-05", 13, 10, 30);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        Assert.assertEquals(2016, calendar.get(Calendar.YEAR));
        Assert.assertEquals(1, calendar.get(Calendar.MONTH));
        Assert.assertEquals(5, calendar.get(Calendar.DATE));
        Assert.assertEquals(13, calendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(10, calendar.get(Calendar.MINUTE));
        Assert.assertEquals(30, calendar.get(Calendar.SECOND));
    }

    @Test
    public void testGetDateWithSpecifiedTime() {
        Date testDate = ModelHelper.getDateUsing("2016-02-05", 13, 10, 30);
        Date date = ModelHelper.getDateUsing(testDate, 15, 0, 45);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        Assert.assertEquals(2016, calendar.get(Calendar.YEAR));
        Assert.assertEquals(1, calendar.get(Calendar.MONTH));
        Assert.assertEquals(5, calendar.get(Calendar.DATE));
        Assert.assertEquals(15, calendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, calendar.get(Calendar.MINUTE));
        Assert.assertEquals(45, calendar.get(Calendar.SECOND));
    }

    @Test
    public void testGetStringCalendarWithSpecifiedTime() {
        Calendar calendar = ModelHelper.getCalendarUsing("2016-02-05", 13, 10, 30);
        Assert.assertEquals(2016, calendar.get(Calendar.YEAR));
        Assert.assertEquals(1, calendar.get(Calendar.MONTH));
        Assert.assertEquals(5, calendar.get(Calendar.DATE));
        Assert.assertEquals(13, calendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(10, calendar.get(Calendar.MINUTE));
        Assert.assertEquals(30, calendar.get(Calendar.SECOND));
    }

    @Test
    public void testGetCalendarWithSpecifiedTime() {
        Date testDate = ModelHelper.getDateUsing("2016-02-05", 13, 10, 30);
        Calendar calendar = ModelHelper.getCalendarUsing(testDate, 15, 0, 45);

        Assert.assertEquals(2016, calendar.get(Calendar.YEAR));
        Assert.assertEquals(1, calendar.get(Calendar.MONTH));
        Assert.assertEquals(5, calendar.get(Calendar.DATE));
        Assert.assertEquals(15, calendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, calendar.get(Calendar.MINUTE));
        Assert.assertEquals(45, calendar.get(Calendar.SECOND));
    }

    @Test
    public void testGetStringCalendarBadDate() {
        try {
            ModelHelper.getCalendarUsing("xxx");
            Assert.fail("Should have failed");
        }
        catch (SystemLoggedException sle) {
            System.out.println(sle.getMessage());
        }
    }

    @Test
    public void testGetCalendarBadDate() {
        try {
            ModelHelper.getCalendarUsing("xxx", 1,1,1);
            Assert.fail("Should have failed");
        }
        catch (SystemLoggedException sle) {
            System.out.println(sle.getMessage());
        }
    }
}
