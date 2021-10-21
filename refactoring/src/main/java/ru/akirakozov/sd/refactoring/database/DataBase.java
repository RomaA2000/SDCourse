package ru.akirakozov.sd.refactoring.database;

import java.sql.SQLException;
import java.sql.Statement;

public interface DataBase {
    @FunctionalInterface
    public interface SQLConsumer {
        void accept(Statement t) throws SQLException;
    }
}
