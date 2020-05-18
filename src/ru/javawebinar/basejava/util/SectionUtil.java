package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

public class SectionUtil {
    public static String sectionToHTML(SectionType type, AbstractSection section) {
        StringBuilder sectionHTML = new StringBuilder();
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                sectionHTML.append(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                sectionHTML.append(String.join("<br/><br/>", ((ListSection) section).getItems()));
                break;
            case EXPERIENCE:
            case EDUCATION:
                for (Organization organization : ((OrganizationSection) section).getOrganizations()) {
                    sectionHTML.append("<a href=\"")
                            .append(organization.getHomePage().getUrl())
                            .append("\">")
                            .append(organization.getHomePage().getName())
                            .append("</a><br/>");
                    for (Organization.Position position : organization.getPositions()) {
                        sectionHTML.append("<b>c ")
                                .append(position.getStartDate())
                                .append(" по ")
                                .append(position.getEndDate().equals(DateUtil.NOW) ? "настоящее время" : position.getEndDate())
                                .append("</b> ")
                                .append(position.getTitle())
                                .append("<br/>")
                                .append(position.getDescription() == null ? "" : position.getDescription());
                    }
                    sectionHTML.append("<br/>");
                }
        }
        return sectionHTML.toString();
    }

    public static String sectionToTextArea(SectionType type, AbstractSection section) {
        StringBuilder sectionToArea = new StringBuilder();
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                if (section != null) {
                    sectionToArea.append(((TextSection) section).getContent());
                }
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                if (section != null) {
                    sectionToArea.append(String.join("\n", ((ListSection) section).getItems()));
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
        }
        return sectionToArea.toString();
    }

    public static AbstractSection createSection(SectionType type, String value) {
        AbstractSection section = null;
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                section = new TextSection(value);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                section = new ListSection(value.split("\n"));
                break;
            case EXPERIENCE:
            case EDUCATION:
        }
        return section;
    }
}

