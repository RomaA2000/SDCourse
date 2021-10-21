package ru.akirakozov.sd.refactoring.queries;

import ru.akirakozov.sd.refactoring.product.Product;

import java.io.PrintWriter;
import java.sql.SQLException;

public interface QueryExecuter {
    void add(Product product) throws SQLException;

    void get(PrintWriter writer) throws SQLException;

    void getSum(PrintWriter writer) throws SQLException;

    void getCount(PrintWriter writer) throws SQLException;

    void getMax(PrintWriter writer) throws SQLException;

    void getMin(PrintWriter writer) throws SQLException;
}
