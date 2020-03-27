package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class PositionSection extends TextSection {
    private List<Position> sectionList;

    public List<Position> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Position> sectionList) {
        this.sectionList = sectionList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Position section : sectionList) {
            sb.append(section).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionSection that = (PositionSection) o;
        return Objects.equals(sectionList, that.sectionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionList);
    }
}
