package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractServlet {

    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.getData("SELECT * FROM PRODUCT", null, response,
                (rsOf, responseOf) -> {
                    try {
                        while (rsOf.next()) {
                            String name = rsOf.getString("name");
                            int price = rsOf.getInt("price");
                            responseOf.getWriter().println(name + "\t" + price + "</br>");
                        }
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}