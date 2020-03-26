package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class TimePeriod {
    private YearMonth start;
    private YearMonth finish;

    public TimePeriod(YearMonth start, YearMonth finish) {
        this.start = start;
        this.finish = finish;
    }

    @Override
    public String toString() {
        return start + " - " + finish;
    }
}
