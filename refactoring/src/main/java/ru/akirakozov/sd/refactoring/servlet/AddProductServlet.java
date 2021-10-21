package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.product.Product;
import ru.akirakozov.sd.refactoring.queries.QueryExecuter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractServlet {

    public AddProductServlet(QueryExecuter queryExecuter) {
        super(queryExecuter);
    }

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        queryExecuter.add(new Product(name, price));
        response.getWriter().println("OK");
    }
}
