package ru.javawebinar.basejava.model;

import java.util.List;

public class ChronoDateSection extends TextSection {
    private List<DateSection> sectionList;

    public List<DateSection> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<DateSection> sectionList) {
        this.sectionList = sectionList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DateSection section : sectionList) {
            sb.append(section).append("\n");
        }
        return sb.toString();
    }
}
