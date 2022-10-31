package com.vladimir.java.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataBaseInitializer {
    private List<String> tableFields = new ArrayList<>();
    public DataBaseInitializer() {
        System.out.println("Запуск инициализатора таблицы\n");
    }

    public DataBase initNewTable() {
        Scanner inputScanner = new Scanner(System.in);
        String tableName;

        while (true) {
            System.out.print("Введите имя таблицы: ");
            String userInput = inputScanner.nextLine();
            if (userInput.length() < 3) {
                System.out.println("Ошибка, введите корректное имя таблицы");
            } else {
                tableName = userInput;
                break;
            }
        }

        System.out.println("Поочерёдно введите поля таблицы, чтобы закончить инициализацию нажмите ENTER в строке ввода");

        while (true) {
            String userInput = inputScanner.nextLine();
            if (userInput.equals("")) break;

            tableFields.add(userInput);
        }

        if (tableFields.size() == 0) {
            System.out.println("Ошибка");
            return null;
        }

        System.out.println("Инициализирую");

        return new DataBase(tableName, tableFields);
    }
}
