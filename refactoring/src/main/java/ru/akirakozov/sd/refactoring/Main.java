package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.database.DataBase;
import ru.akirakozov.sd.refactoring.database.DatabaseImpl;
import ru.akirakozov.sd.refactoring.html.HtmlBuilder;
import ru.akirakozov.sd.refactoring.html.HtmlBuilderImpl;
import ru.akirakozov.sd.refactoring.queries.QueryExecuter;
import ru.akirakozov.sd.refactoring.queries.QueryExecuterImpl;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        DataBase db = new DatabaseImpl("jdbc:sqlite:test.db");
        HtmlBuilder html = new HtmlBuilderImpl();

        db.databaseQuery(statement -> {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            statement.executeUpdate(sql);
            statement.close();
        });
        Server server = new Server(8081);

        QueryExecuter queryExecuter = new QueryExecuterImpl(db, html);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(queryExecuter)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(queryExecuter)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(queryExecuter)), "/query");

        server.start();
        server.join();
    }
}
