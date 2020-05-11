package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        StringBuilder str = new StringBuilder();
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            str.append("<tr><td>")
                    .append(uuid)
                    .append("</td><td>")
                    .append(Config.get().getStorage().get(uuid).getFullName())
                    .append("</td></tr>");
        } else {
            for (Resume resume : Config.get().getStorage().getAllSorted()) {
                str.append("<tr><td>")
                        .append(resume.getUuid())
                        .append("</td><td>")
                        .append(resume.getFullName())
                        .append("</td></tr>");
            }
        }

        response.getWriter().write("" +
                "<html>\n" +
                "<head>\n" +
                "<title>Resumes search</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Resumes</h2>\n" +
                "<p>Result:</p>\n" +
                "<table style=\"width:50%\";  border= \"1px solid black\" >\n" +
                "  <tr>\n" +
                "    <th>Resume ID</th>\n" +
                "    <th>Full name</th> \n" +
                "  </tr>\n" +
                str.toString() +
                "</table>\n" +
                "</body>\n" +
                "</html>");
    }
}