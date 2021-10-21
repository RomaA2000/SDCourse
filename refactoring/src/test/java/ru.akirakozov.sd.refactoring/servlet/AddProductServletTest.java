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

public class AddProductServletTest extends ServletTest {
    @Test
    public void doGetNoExcept() {
        List<String> list = new ArrayList<>();
        list.add("OK");
        super.templateResponseTest(list, (HttpServletRequest request) -> {
                    Mockito.when(request.getParameter("name")).thenReturn("banana");
                    Mockito.when(request.getParameter("price")).thenReturn("10");
                }, (HttpServletRequest request, HttpServletResponse response) -> {
                    try {
                        new AddProductServlet(getQueryExecuter()).doGet(request, response);
                    } catch (IOException e) {
                        Assertions.fail("Exception: ", e);
                    }
                },
                (String str, StringWriter writer) -> writer.toString().contains(str));
    }
}