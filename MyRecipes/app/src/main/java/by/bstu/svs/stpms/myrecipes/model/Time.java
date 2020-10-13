package by.bstu.svs.stpms.myrecipes.model;

import lombok.Getter;

@Getter
public class Time {

    private int hours;
    private int minutes;

    public Time (int hours, int minutes) throws TimeFormatException {
        setHours(hours);
        setMinutes(minutes);
    }

    public void setHours(int hours) throws TimeFormatException {

        if (hours < 0 || hours > 23) {
            throw new TimeFormatException("Hours should be between 0 and 23; provided " + hours);
        }
        this.hours = hours;
    }

    public void setMinutes(int minutes) throws TimeFormatException {
        if (minutes < 0 || minutes > 59) {
            throw new TimeFormatException("Hours should be between 0 and 59; provided " + minutes);
        }
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }
}
