package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Position extends TextSection {
    private String company;
    private String companyURL;
    private TimePeriod period;
    private Map<TimePeriod, String> placesMap = new HashMap<>();

    public static class TimePeriod {
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

    public Position(String company, String companyURL, YearMonth start, YearMonth finish) {
        this.company = company;
        this.companyURL = companyURL;
        this.period = new TimePeriod(start, finish);
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyURL() {
        return companyURL;
    }

    public void setCompanyURL(String companyURL) {
        this.companyURL = companyURL;
    }

    public TimePeriod getPeriod() {
        return period;
    }

    public void setPeriod(TimePeriod period) {
        this.period = period;
    }

    public Map<TimePeriod, String> getPlacesMap() {
        return placesMap;
    }

    public void setPlacesMap(Map<TimePeriod, String> placesMap) {
        this.placesMap = placesMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(company).append("\n");
        for (Map.Entry entry : placesMap.entrySet()) {
            sb.append(entry);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position section = (Position) o;
        return Objects.equals(company, section.company) &&
                Objects.equals(companyURL, section.companyURL) &&
                Objects.equals(placesMap, section.placesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, companyURL, placesMap);
    }
}
