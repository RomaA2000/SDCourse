package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.queries.QueryExecuter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    public GetProductsServlet(QueryExecuter queryExecuter) {
        super(queryExecuter);
    }

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        queryExecuter.get(response.getWriter());
    }
}