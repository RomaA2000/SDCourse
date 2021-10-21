package ru.akirakozov.sd.refactoring.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String path;

    @FunctionalInterface
    public interface SQLConsumer {
        void accept(Statement t) throws SQLException;
    }

    public Database(final String path) { this.path = path; }

    protected void databaseQuery(SQLConsumer consumer) throws SQLException {
        try (Connection c = DriverManager.getConnection(path)) {
            Statement stmt = c.createStatement();
            consumer.accept(stmt);
            stmt.close();
        }
    }
}
