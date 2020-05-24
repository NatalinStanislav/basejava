package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                                .append(position.getDescription() == null ? "" : position.getDescription())
                                .append("<br/>");
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
        }
        return section;
    }

    public static String orgNameToText(OrganizationSection section, int index) {
        if (section == null || section.getOrganizations() == null || index >= section.getOrganizations().size())
            return "";
        return section.getOrganizations().get(index).getHomePage().getName();
    }

    public static String orgUrlToText(OrganizationSection section, int index) {
        if (section == null || section.getOrganizations() == null || index >= section.getOrganizations().size())
            return "";
        return section.getOrganizations().get(index).getHomePage().getUrl();
    }

    public static String orgPositionToText(OrganizationSection section, int indexOrg, int indexPos) {
        if (section == null || section.getOrganizations() == null || indexOrg >= section.getOrganizations().size())
            return "";
        return section.getOrganizations().get(indexOrg).getPositions().get(indexPos).getTitle();
    }

    public static LocalDate orgPositionStartDate(OrganizationSection section, int indexOrg, int indexPos) {
        if (section == null || section.getOrganizations() == null || indexOrg >= section.getOrganizations().size())
            return null;
        return section.getOrganizations().get(indexOrg).getPositions().get(indexPos).getStartDate();
    }

    public static LocalDate orgPositionEndDate(OrganizationSection section, int indexOrg, int indexPos) {
        if (section == null || section.getOrganizations() == null || indexOrg >= section.getOrganizations().size())
            return null;
        if (section.getOrganizations().get(indexOrg).getPositions().get(indexPos).getEndDate().equals(DateUtil.NOW))
            return LocalDate.now();
        return section.getOrganizations().get(indexOrg).getPositions().get(indexPos).getEndDate();
    }

    public static String orgPositionDescriptionToText(OrganizationSection section, int indexOrg, int indexPos) {
        if (section == null || section.getOrganizations() == null || indexOrg >= section.getOrganizations().size())
            return "";
        return section.getOrganizations().get(indexOrg).getPositions().get(indexPos).getDescription();
    }

    public static boolean isOrgSection(SectionType type) {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
            case ACHIEVEMENT:
            case QUALIFICATIONS:
            default:
                return false;
            case EXPERIENCE:
            case EDUCATION:
                return true;
        }
    }

    public static List<Organization> getOrgList(Resume resume, SectionType type) {
        if (resume.getSection(type) == null) {
            return null;
        }
        return ((OrganizationSection) (resume.getSection(type))).getOrganizations();
    }

    public static int orgIndex(ArrayList<Organization> list, Link link) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getHomePage().equals(link)) {
                return i;
            }
        }
        return -1;
    }
}

