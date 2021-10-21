package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractServlet extends HttpServlet {
    protected abstract void doGetImpl(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;

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
