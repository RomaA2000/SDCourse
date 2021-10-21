package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.function.BiConsumer;

public abstract class AbstractServlet extends HttpServlet {
    protected abstract void doGetImpl(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;

    void getData(String commandSQL, String answer,
                 HttpServletResponse response, BiConsumer<ResultSet, HttpServletResponse> logger) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(commandSQL);
                response.getWriter().println("<html><body>");
                response.getWriter().println(answer);

                logger.accept(rs, response);

                response.getWriter().println("</body></html>");

                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            doGetImpl(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
