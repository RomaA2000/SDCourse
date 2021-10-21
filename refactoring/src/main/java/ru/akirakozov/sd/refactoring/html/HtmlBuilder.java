package ru.akirakozov.sd.refactoring.html;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class HtmlBuilder {
    @FunctionalInterface
    public interface WriterConsumer {
        void accept(PrintWriter t) throws SQLException;
    }

    public static void defaultHtml(PrintWriter printer, WriterConsumer bodyBuilder) throws SQLException {
        printer.println("<html><body>");
        bodyBuilder.accept(printer);
        printer.println("</body></html>");
    }

    public static void productHtml(PrintWriter printer, Optional<String> header, ResultSet rs) throws SQLException {
        defaultHtml(printer, bodyBuilder -> {
            header.ifPresent(bodyBuilder::println);
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                bodyBuilder.println(name + "\t" + price + "</br>");
            }
        });
    }

    public static void buildHtml(PrintWriter printer, Optional<String> header, ResultSet rs) throws SQLException {
        defaultHtml(printer, bodyBuilder -> {
            header.ifPresent(bodyBuilder::println);
            if (rs.next()) {
                bodyBuilder.println(rs.getInt(1));
            }
        });
    }
}
