package com.vladimir.java.database;

import com.vladimir.java.models.Column;
import com.vladimir.java.models.Item;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class DataBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Column> columns;
    private final String dataBaseName;
    private List<Item> fields = new ArrayList<>();

    public DataBase(String dataBaseName, List<String> tableFields) {
        this.dataBaseName = dataBaseName;
        this.columns = setColumns(tableFields);
    }

    public void addNewField(List<String> values) {
        fields.add(new Item(fields.size(), values));
    }

    private void addNewField(int id,List<String> values) {
        fields.add(id, new Item(id, values));
    }

    public void editField(int id, List<String> values) {
        removeElementById(id);
        addNewField(id, values);

        updateIndexInFields();
    }

    private List<Column> setColumns(List<String> fieldsNames) {
        List<Column> result = new ArrayList<>();

        for (int i = 0; i < fieldsNames.size(); i++) {
            String fieldName = fieldsNames.get(i);
            result.add(new Column(i,  fieldName));
        }

        return result;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Item> getRows() {
        return fields;
    }

    public void removeElementById(int userOutput) {
        if (userOutput >= fields.size())
            return;
        fields.remove(userOutput);

        updateIndexInFields();
    }

    private void updateIndexInFields() {
        List<Item> newFields = new ArrayList<>();

        for (int i = 0; i < fields.size(); i++) {
            Item field = fields.get(i);

            List<String> values = field.values();
            values.remove(0);
            values.add(0, String.valueOf(i));

            newFields.add(new Item(i, values));
        }

        fields = newFields;
    }

    public String getName() {
        return dataBaseName;
    }
}
