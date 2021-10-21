package ru.akirakozov.sd.refactoring.database;

import java.sql.SQLException;
import java.sql.Statement;

public interface DataBase {
    @FunctionalInterface
    interface SQLConsumer {
        void accept(Statement t) throws SQLException;
    }

    void databaseQuery(SQLConsumer consumer) throws SQLException;
}
