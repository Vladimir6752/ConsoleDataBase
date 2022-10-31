package com.vladimir.java.console;

import com.vladimir.java.database.DataBase;
import com.vladimir.java.models.Column;
import com.vladimir.java.models.Item;

import java.util.List;

public class TableFormatter {
    public static String parseTable(DataBase database) {
        String lineSpacing = lengthTable(database);
        return lineSpacing +
               parseColumns(database) +
               parseRows(database, lineSpacing) +
               lineSpacing;
    }

    private static String lengthTable(DataBase database) {
        StringBuilder result = new StringBuilder();

        for (char c : parseColumns(database).toCharArray()) {
            result.append(c != '|' ? "-" : "+");
        }

        return result.deleteCharAt(result.length() - 1).append('\n').toString();
    }

    private static String parseColumns(DataBase database) {
        List<Column> columns = database.getColumns();

        return String.format(getColumnsFormat(columns), getColumnsContent(columns));
    }

    private static String parseRows(DataBase database, String lineSpacing) {
        StringBuilder result = new StringBuilder();

        String rowFormat = getRowFormat(database.getColumns().size());

        for (Item row : database.getRows()) {
            result
                    .append(lineSpacing)
                    .append(String.format(rowFormat, getRowContent(row)));
        }

        return result.toString();
    }

    private static Object[] getRowContent(Item row) {
        return row.values().toArray();
    }

    private static String getColumnsFormat(List<Column> columns) {
        return "|   Id|" + "%20s|".repeat(columns.size()) + "\n";
    }

    private static String getRowFormat(int rows) {
        return "|%5s|" + "%20s|".repeat(rows) + "\n";
    }

    private static Object[] getColumnsContent(List<Column> columns) {
        return columns.stream().map(Column::name).toArray();
    }
}
