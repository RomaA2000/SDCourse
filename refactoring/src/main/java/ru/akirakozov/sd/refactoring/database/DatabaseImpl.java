package ru.akirakozov.sd.refactoring.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseImpl implements DataBase {
    private final String path;

    public DatabaseImpl(final String path) { this.path = path; }

    protected void databaseQuery(SQLConsumer consumer) throws SQLException {
        try (Connection c = DriverManager.getConnection(path)) {
            Statement stmt = c.createStatement();
            consumer.accept(stmt);
            stmt.close();
        }
    }
}
