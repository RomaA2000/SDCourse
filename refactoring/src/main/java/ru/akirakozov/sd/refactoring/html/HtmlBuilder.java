package ru.akirakozov.sd.refactoring.html;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface HtmlBuilder {
    @FunctionalInterface
    public interface WriterConsumer {
        void accept(PrintWriter t) throws SQLException;
    }

    public void productHtml(PrintWriter printer, Optional<String> header, ResultSet rs) throws SQLException;

    public void buildHtml(PrintWriter printer, Optional<String> header, ResultSet rs) throws SQLException;
}
