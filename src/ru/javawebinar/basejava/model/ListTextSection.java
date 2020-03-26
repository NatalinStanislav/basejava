package ru.javawebinar.basejava.model;

import java.util.List;

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
        for (String str: infoList) {
            sb.append(str).append("\n");
        }
        return sb.toString();
    }
}
