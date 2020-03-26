package ru.javawebinar.basejava.model;

public enum Contacts {
    PHONE_NUMBER("Тел.:"),
    SKYPE_ACCOUNT("Skype:"),
    EMAIL("Почта:"),
    LINKEDIN_PROFILE("Профиль LinkedIn"),
    GITHUB_PROFILE("Профиль GitHub"),
    STACKOVERFLOW_PROFILE("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    Contacts(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
