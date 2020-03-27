package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListTextSection extends TextSection {
    private List<String> infoList;

    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        this.infoList = infoList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : infoList) {
            sb.append(str).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTextSection that = (ListTextSection) o;
        return Objects.equals(infoList, that.infoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infoList);
    }
}
