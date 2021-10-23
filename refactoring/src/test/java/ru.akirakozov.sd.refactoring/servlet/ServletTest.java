package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.database.DataBase;
import ru.akirakozov.sd.refactoring.database.DatabaseImpl;
import ru.akirakozov.sd.refactoring.html.HtmlBuilderImpl;
import ru.akirakozov.sd.refactoring.queries.QueryExecuter;
import ru.akirakozov.sd.refactoring.queries.QueryExecuterImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ServletTest {
    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    protected static DataBase getDatabase() {
        return new DatabaseImpl("jdbc:sqlite:src/test/resources/test.db");
    }

    protected static QueryExecuter getQueryExecuter() {
        return new QueryExecuterImpl(getDatabase(), new HtmlBuilderImpl());
    }

    protected static void updateDB(String query) {
        try {
            getDatabase().databaseQuery(statement -> {
                statement.executeUpdate(query);
                statement.close();
            });
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    protected void testResponse(Consumer<StringWriter> test) {
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            Mockito.when(response.getWriter()).thenReturn(writer);

            test.accept(stringWriter);

            Mockito.verify(response).setContentType("text/html");
            Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @BeforeAll
    public static void setupDB() {
        updateDB("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    @AfterAll
    public static void dropTable() {
        updateDB("DROP TABLE IF EXISTS PRODUCT");
    }

    @BeforeEach
    public void clearDB() {
        updateDB("DELETE FROM PRODUCT");
    }

    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    public void templateResponseTest(List<String> assertions, Consumer<HttpServletRequest> mocker, BiConsumer<HttpServletRequest, HttpServletResponse> servletDoGet, BiFunction<String, StringWriter, Boolean> checker) {
        testResponse((StringWriter writer) -> {
            mocker.accept(request);
            servletDoGet.accept(request, response);

            assertions.forEach((String str) ->
                    Assertions.assertTrue(checker.apply(str, writer))
            );
        });
    }
}