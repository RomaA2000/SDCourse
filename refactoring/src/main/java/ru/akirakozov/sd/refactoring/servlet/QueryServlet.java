package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {

    private void getDataOrdered(String commandSQL, String answer,
                                HttpServletResponse response) {
        super.getData(commandSQL,
                answer,
                response,
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
                });
    }

    private void getDataCount(String commandSQL, String answer,
                              HttpServletResponse response) {
        super.getData(commandSQL,
                answer,
                response,
                (rsOf, responseOf) -> {
                    try {
                        if (rsOf.next()) {
                            responseOf.getWriter().println(rsOf.getInt(1));
                        }
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        switch (command) {
            case "max":
                getDataOrdered("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1",
                        "<h1>Product with max price: </h1>",
                        response);
                break;
            case "min":
                getDataOrdered("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1",
                        "<h1>Product with min price: </h1>",
                        response);
                break;
            case "sum":
                getDataCount("SELECT SUM(price) FROM PRODUCT",
                        "Summary price: ",
                        response);
                break;
            case "count":
                getDataCount("SELECT COUNT(*) FROM PRODUCT",
                        "Number of products: ",
                        response);
                break;
            default:
                response.getWriter().println("Unknown command: " + command);
        }
    }
}
