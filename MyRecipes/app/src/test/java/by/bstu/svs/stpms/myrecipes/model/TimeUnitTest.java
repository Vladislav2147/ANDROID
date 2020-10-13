package by.bstu.svs.stpms.myrecipes.model;

import org.junit.Assert;
import org.junit.Test;

public class TimeUnitTest {

    @Test
    public void validTimeToConstructorTest() throws TimeFormatException {

        int validHours = 23;
        int validMinutes = 0;

        Time time = new Time(validHours, validMinutes);

        Assert.assertEquals("23:00", time.toString());

    }

    @Test(expected = TimeFormatException.class)
    public void invalidMinutesToConstructorThrowsTimeFormatExceptionTest() throws TimeFormatException {

        int hours = 23;
        int invalidMinutes = 60;

        new Time(hours, invalidMinutes);

    }

    @Test(expected = TimeFormatException.class)
    public void invalidHoursToConstructorThrowsTimeFormatExceptionTest() throws TimeFormatException {

        int invalidHours = 70;
        int minutes = 60;

        Time time = new Time(invalidHours, minutes);

    }
}
