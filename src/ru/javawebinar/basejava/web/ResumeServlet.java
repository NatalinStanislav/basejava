package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.SectionUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (request.getParameter("isNew").equals("yes")) {
            r = new Resume(uuid, fullName);
        } else {
            r = storage.get(uuid);
        }
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            if (!SectionUtil.isOrgSection(type)) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    r.addSection(type, SectionUtil.createSection(type, value));
                } else {
                    r.getSections().remove(type);
                }
            } else {
                ArrayList<Organization> organizations = new ArrayList<>();
                for (int i = 0; i < Integer.parseInt(request.getParameter(type.name() + "orgCount")); i++) {
                    ArrayList<Organization.Position> positions = new ArrayList<>();
                    String orgName = request.getParameter(type.name() + "OrganizationName" + i);
                    if (orgName.trim().length() == 0) {
                        continue;
                    }
                    String orgUrl = request.getParameter(type.name() + "OrganizationLink" + i);
                    Link link = new Link(orgName, orgUrl);
                    for (int j = 0; j < Integer.parseInt(request.getParameter(type.name() + "posCount" + i)); j++) {
                        String position = request.getParameter(type.name() + "Position" + i + j);
                        String start = request.getParameter(type.name() + "StartDate" + i + j);
                        String end = request.getParameter(type.name() + "EndDate" + i + j);
                        if (position.trim().length() == 0 || start.trim().length() == 0 || end.trim().length() == 0) {
                            continue;
                        }
                        LocalDate startDate = LocalDate.parse(start);
                        LocalDate endDate = LocalDate.parse(end);
                        String description = request.getParameter(type.name() + "Description" + i + j);
                        positions.add(new Organization.Position(startDate, endDate, position, description));
                    }
                    if (positions.size() != 0) {
                        organizations.add(new Organization(link, positions));
                    }
                }

                for (int i = 1; i < 6; i++) {
                    String orgName = request.getParameter(type.name() + "addOrganizationName" + i);
                    String orgUrl = request.getParameter(type.name() + "addOrganizationLink" + i);
                    String position = request.getParameter(type.name() + "addPosition" + i);
                    String start = request.getParameter(type.name() + "addStartDate" + i);
                    String end = request.getParameter(type.name() + "addEndDate" + i);
                    String description = request.getParameter(type.name() + "addDescription" + i);
                    if (orgName.trim().length() == 0 || position.trim().length() == 0 || start.trim().length() == 0 || end.trim().length() == 0) {
                        continue;
                    }
                    Link link = new Link(orgName, orgUrl);
                    Organization.Position pos = new Organization.Position(LocalDate.parse(start), LocalDate.parse(end), position, description);
                    int index = SectionUtil.orgIndex(organizations, link);
                    if (index != -1) {
                        organizations.get(index).getPositions().add(pos);
                    } else {
                        ArrayList<Organization.Position> positions = new ArrayList<>();
                        positions.add(pos);
                        organizations.add(new Organization(link, positions));
                    }
                }
                r.addSection(type, new OrganizationSection(organizations));
            }
        }
        if (request.getParameter("isNew").equals("yes")) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "create":
                r = new Resume("Пустое резюме");
                request.setAttribute("isNew", "yes");
                break;
            case "view":
            case "edit":
                r = storage.get(uuid);
                request.setAttribute("isNew", "no");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}