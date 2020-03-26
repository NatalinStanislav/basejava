package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;

public class DateSection extends TextSection {
    private String company;
    private String company_URL;
    private Map<TimePeriod, String> placesMap = new HashMap<>();

    public DateSection(String company, String company_URL) {
        this.company = company;
        this.company_URL = company_URL;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany_URL() {
        return company_URL;
    }

    public void setCompany_URL(String company_URL) {
        this.company_URL = company_URL;
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
}
