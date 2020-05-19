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
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.firstFourValues()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addSection(type, SectionUtil.createSection(type, value));
            } else {
                r.getSections().remove(type);
            }
        }

        ArrayList<Organization> organizations = new ArrayList<>();
        ArrayList<Organization.Position> positions;
        for (int i = 0; i < 10; i++) {
            if (request.getParameter("expOrganizationName" + i) == null || request.getParameter("expOrganizationName" + i).equals("")) {
                break;
            }
            String orgName = request.getParameter("expOrganizationName" + i);
            String orgUrl = request.getParameter("expOrganizationLink" + i);
            Link link = new Link(orgName, orgUrl);
            positions = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                if (request.getParameter("expPosition" + i + j) == null || request.getParameter("expPosition" + i + j).equals("")) {
                    break;
                }
                String position = request.getParameter("expPosition" + i + j);
                LocalDate startDate = LocalDate.parse(request.getParameter("expStartDate" + i + j));
                LocalDate endDate = LocalDate.parse(request.getParameter("expEndDate" + i + j));
                String duties = request.getParameter("expDuties" + i + j);
                positions.add(new Organization.Position(startDate, endDate, position, duties));
            }
            organizations.add(new Organization(link, positions));
        }
        r.addSection(SectionType.EXPERIENCE, new OrganizationSection(organizations));

        organizations = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            if (request.getParameter("edOrganizationName" + i) == null || request.getParameter("edOrganizationName" + i).equals("")) {
                break;
            }
            String orgName = request.getParameter("edOrganizationName" + i);
            String orgUrl = request.getParameter("edOrganizationLink" + i);
            Link link = new Link(orgName, orgUrl);
            positions = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                if (request.getParameter("edPosition" + i + j) == null || request.getParameter("edPosition" + i + j).equals("")) {
                    break;
                }
                String position = request.getParameter("edPosition" + i + j);
                LocalDate startDate = LocalDate.parse(request.getParameter("edStartDate" + i + j));
                LocalDate endDate = LocalDate.parse(request.getParameter("edEndDate" + i + j));
                positions.add(new Organization.Position(startDate, endDate, position, null));
            }
            organizations.add(new Organization(link, positions));
        }
        r.addSection(SectionType.EDUCATION, new OrganizationSection(organizations));

        storage.update(r);
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
                storage.save(r);
                break;
            case "view":
            case "edit":
                r = storage.get(uuid);
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