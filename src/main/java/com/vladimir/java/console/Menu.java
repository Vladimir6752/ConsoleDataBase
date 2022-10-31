package com.vladimir.java.console;

import com.vladimir.java.database.DataBase;
import com.vladimir.java.database.DataBaseInitializer;
import com.vladimir.java.models.Column;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Menu {
    public static final String PATH_TO_SERIALIZE = "save.ser";
    public static DataBase database;
    private static List<Column> tableReferenceColumns;

    public static void start() {
        findDataBase();

        while (database == null) {
            DataBaseInitializer dataBaseInitializer = new DataBaseInitializer();
            database = dataBaseInitializer.initNewTable();
        }
        saveTable();

        tableReferenceColumns = database.getColumns();

        printContextMenu();
    }

    private static void findDataBase() {
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH_TO_SERIALIZE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            database = (DataBase) objectInputStream.readObject();
        } catch (Exception ignored) {}
    }

    private static void printContextMenu() {
        System.out.printf("""
                             
                                   База данных %s
                +---------------------------------------------+---------------+
                |Чтобы добавить элемент в таблицу введите:    |ADD            |
                |Чтобы редактировать элемент введите:         |EDIT           |
                |Чтобы удалить элемент таблицы введите:       |DELETE         |
                |Чтобы вывести таблицу на экран введите:      |SHOW           |
                |Чтобы создать таблицу заново введите:        |CREATE         |
                +---------------------------------------------+---------------+
                |              Таблица сохраняется автоматически              |
                +---------------------------------------------+---------------+
                
                """, database.getName());
        listenUserOutput();
    }

    private static void listenUserOutput() {
        String userOutput = getUserOutput();
        switch (userOutput) {
            case "ADD" -> addTableElement();
            case "DELETE" -> removeElementById();
            case "CREATE" -> resetTable();
            case "SHOW" -> printTable();
            case "EDIT" -> editTableElement();
            default -> System.out.printf("Команда %s не существует", userOutput);
        }

        start();
    }

    private static void editTableElement() {
        System.out.print("Введите Id редактируемого элемента: ");
        int idEditedElement;

        try {
            idEditedElement = Integer.parseInt(getUserOutput());
        } catch (Exception e) {
            return;
        }

        List<String> valuesItem = getValuesItem();

        valuesItem.add(0, String.valueOf(idEditedElement));

        database.editField(idEditedElement, valuesItem);

        saveTable();
    }

    private static List<String> getValuesItem() {
        List<String> valuesItem = new ArrayList<>();

        for (Column tableReferenceColumn : tableReferenceColumns) {
            System.out.print(tableReferenceColumn.name() + ": ");
            valuesItem.add(getUserOutput());
        }

        return valuesItem;
    }

    private static void removeElementById() {
        System.out.print("Введите Id удаляемого элемента: ");
        int idRemovedElement;

        try {
            idRemovedElement = Integer.parseInt(getUserOutput());
        } catch (Exception e) {
            return;
        }

        database.removeElementById(idRemovedElement);
        saveTable();
    }

    private static void saveTable() {
        try {
            FileOutputStream outputStream = new FileOutputStream(PATH_TO_SERIALIZE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(database);
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.printf("Не удалось сохранить таблицу с ошибкой: %s", e.getMessage());
        }
    }

    private static void printTable() {
        System.out.println(TableFormatter.parseTable(database));
    }

    private static void resetTable() {
        database = null;
        saveTable();
    }

    private static void addTableElement() {
        List<String> valuesItem = getValuesItem();
        valuesItem.add(0, String.valueOf(database.getRows().size()));

        database.addNewField(valuesItem);
        saveTable();
    }

    private static String getUserOutput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
