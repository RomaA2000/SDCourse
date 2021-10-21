package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class QueryServletTest extends ServletTest {
    public void testQuery(String command, String body) {
        List<String> list = new ArrayList<>();
        list.add("<html><body>" + System.lineSeparator()
                + body + System.lineSeparator()
                + "</body></html>" + System.lineSeparator());
        super.templateResponseTest(list, (HttpServletRequest request) -> {
                    Mockito.when(request.getParameter("command")).thenReturn(command);
                }, (HttpServletRequest request, HttpServletResponse response) -> {
                    try {
                        new QueryServlet(getQueryExecuter()).doGet(request, response);
                    } catch (IOException e) {
                        Assertions.fail("Exception: ", e);
                    }
                },
                (String str, StringWriter writer) -> writer.toString().equals(str));
    }

    @Test
    public void testMin() {
        testQuery("min", "<h1>Product with min price: </h1>");
    }

    @Test
    public void testMax() {
        testQuery("max", "<h1>Product with max price: </h1>");
    }

    @Test
    public void testCount() {
        testQuery("count", "Number of products: " + System.lineSeparator() + "0");
    }

    @Test
    public void testSum() {
        testQuery("sum", "Summary price: " + System.lineSeparator() + "0");
    }
}
