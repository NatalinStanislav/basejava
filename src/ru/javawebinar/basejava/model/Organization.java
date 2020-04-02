package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final Map<TimePeriod, Position> periodMap;

    public static class TimePeriod {
        private final LocalDate startDate;
        private final LocalDate endDate;

        public TimePeriod(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return startDate + " - " + endDate;
        }
    }

    public static class Position {
        private final String title;
        private final String description;

        public Position(String title, String description) {
            this.title = title;
            this.description = description;
        }

        @Override
        public String toString() {
            return title + "\n" + description;
        }
    }


    public Organization(String name, String url, Map<TimePeriod, Position> periodMap) {
        Objects.requireNonNull(periodMap, "periodMap must not be null");
        this.homePage = new Link(name, url);
        this.periodMap = periodMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) &&
                periodMap.equals(that.periodMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, periodMap);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", periodMap=" + periodMap +
                '}';
    }
}