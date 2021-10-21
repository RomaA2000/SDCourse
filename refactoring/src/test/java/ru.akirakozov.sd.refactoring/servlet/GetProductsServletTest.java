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

public class GetProductsServletTest extends ServletTest {
    @Test
    public void doGetNoExcept() {
        List<String> list = new ArrayList<>();
        list.add("<html><body>");
        list.add("</body></html>");
        super.templateResponseTest(list, (HttpServletRequest request) -> {
                    Mockito.when(request.getParameter("name")).thenReturn("banana");
                    Mockito.when(request.getParameter("price")).thenReturn("10");
                }, (HttpServletRequest request, HttpServletResponse response) -> {
                    try {
                        new GetProductsServlet(getQueryExecuter()).doGet(request, response);
                    } catch (IOException e) {
                        Assertions.fail("Exception: ", e);
                    }
                },
                (String str, StringWriter writer) -> writer.toString().contains(str));
    }
}
