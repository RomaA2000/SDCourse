package ru.akirakozov.sd.refactoring.queries;

import ru.akirakozov.sd.refactoring.database.DataBase;
import ru.akirakozov.sd.refactoring.html.HtmlBuilder;
import ru.akirakozov.sd.refactoring.product.Product;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class QueryExecuterImpl implements QueryExecuter {
    private final DataBase db;
    private final HtmlBuilder html;

    public QueryExecuterImpl(DataBase db, HtmlBuilder html) {
        this.db = db;
        this.html = html;
    }

    @Override
    public void add(Product product) throws SQLException {
         db.databaseQuery(statement -> {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.name + "\"," + product.price + ")";
            statement.executeUpdate(sql);
        });
    }

    @Override
    public void get(PrintWriter writer) throws SQLException {
        db.databaseQuery(statement -> {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT")) {
                html.productHtml(writer, Optional.empty(), rs);
            }
        });
    }

    @Override
    public void getSum(PrintWriter writer) throws SQLException {
        db.databaseQuery(statement -> {
            try (ResultSet rs = statement.executeQuery("SELECT SUM(price) FROM PRODUCT")) {
                html.infoHtml(writer, Optional.of("Summary price: "), rs);
            }
        });
    }

    @Override
    public void getCount(PrintWriter writer) throws SQLException {
        db.databaseQuery(statement -> {
            try (ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM PRODUCT")) {
                html.infoHtml(writer, Optional.of("Number of products: "), rs);
            }
        });
    }

    @Override
    public void getMax(PrintWriter writer) throws SQLException {
        db.databaseQuery(statement -> {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1")) {
                html.productHtml(writer, Optional.of("<h1>Product with max price: </h1>"), rs);
            }
        });
    }

    @Override
    public void getMin(PrintWriter writer) throws SQLException {
        db.databaseQuery(statement -> {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1")) {
                html.productHtml(writer, Optional.of("<h1>Product with min price: </h1>"), rs);
            }
        });
    }
}
