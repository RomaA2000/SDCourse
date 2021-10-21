package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.queries.QueryExecuter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {

    public QueryServlet(QueryExecuter queryExecuter) {
        super(queryExecuter);
    }

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String command = request.getParameter("command");
        switch (command) {
            case "max":
                queryExecuter.getMax(response.getWriter());
                break;
            case "min":
                queryExecuter.getMin(response.getWriter());
                break;
            case "sum":
                queryExecuter.getSum(response.getWriter());
                break;
            case "count":
                queryExecuter.getCount(response.getWriter());
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
        }
    }
}
