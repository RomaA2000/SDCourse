package ru.akirakozov.sd.refactoring.html;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class HtmlBuilderTest {
    private static final HtmlBuilder htmlBuilder = new HtmlBuilderImpl();

    @Mock
    ResultSet rs;

    @BeforeEach
    public void openMocks() { MockitoAnnotations.openMocks(this); }

    @Test
    public void productHtmlTest() throws SQLException {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(rs.getString("name")).thenReturn("phone");
        Mockito.when(rs.getInt("price")).thenReturn(1000);
        Mockito.when(rs.next()).thenReturn(true, false);
        htmlBuilder.productHtml(printWriter, Optional.of("header test"), rs);
        Assertions.assertEquals(writer.toString(),
                "<html><body>\n" +
                "header test\n" +
                "phone\t1000</br>\n" +
                "</body></html>\n");
    }

    @Test
    public void productHtmlError() throws SQLException {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(rs.next()).thenThrow(new SQLException());
        Assertions.assertThrows(SQLException.class, () -> htmlBuilder.productHtml(printWriter, Optional.of("header test"), rs));
    }

    @Test
    public void infoHtmlHtmlTest() throws SQLException {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(rs.getInt(1)).thenReturn(1000);
        Mockito.when(rs.next()).thenReturn(true, false);
        htmlBuilder.infoHtml(printWriter, Optional.of("header test"), rs);
        Assertions.assertEquals(writer.toString(),
                "<html><body>\n" +
                        "header test\n" +
                        "1000\n" +
                        "</body></html>\n");
    }

    @Test
    public void infoHtmlHtmlError() throws SQLException {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(rs.next()).thenThrow(new SQLException());
        Assertions.assertThrows(SQLException.class, () -> htmlBuilder.infoHtml(printWriter, Optional.of("header test"), rs));
    }
}
