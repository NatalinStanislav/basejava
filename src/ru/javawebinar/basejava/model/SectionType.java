package ru.javawebinar.basejava.model;

import java.util.Arrays;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static SectionType[] firstFourValues() {
        SectionType[] array = Arrays.copyOfRange(SectionType.values(), 0, 4);
        return array;
    }
}