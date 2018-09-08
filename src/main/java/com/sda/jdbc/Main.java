package com.sda.jdbc;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static final String URL = "jdbc:mysql://localhost/library?useSSL=false&serverTimezone=UTC";

    private static final String username = "root";
    private static final String password = "pAtelkA007";

    public static String addNewBook() {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        System.out.println("Podaj tytuł: ");
        String title = scanner.nextLine();
        System.out.println("Podaj autora: ");
        String author = scanner.nextLine();
        System.out.println("Podaj ISBN: ");
        String isbn = scanner.nextLine();
        System.out.println("Podaj kategorie ksiazki: ");
        String category = scanner.nextLine();
        System.out.println("Podaj ilość stron ksiazki: ");
        int pageCount = scanner.nextInt();
        System.out.println("Podaj wydawnictwo: ");
        String publisher = scanner.next();
        System.out.println("Podaj cene za ksiazke: ");
        double price = scanner.nextDouble();


        String sqlInsert = "INSERT INTO books (title, author, isbn, category, page_count, publisher, price) VALUES (" +
                "'" + title + "'," +
                "'" + author + "'," +
                "'" + isbn + "'," +
                "'" + category + "'," +
                "" + pageCount + "," +
                "'" + publisher + "'," +
                price +
                ");";
        return sqlInsert;
    }


    public static void main(String[] args) {
        Connection connection = null; //połączenie z baza danych
        Statement statement = null; //wykonanie zapytania

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //rejestracja sterownika, czytanie do pamieci
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, username, password);
            String sql = "select * from books";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            //String sqlInsert ="delete from books where isbn='978-00-511-7839-4'";
           /* String sqlInsert = "INSERT INTO books (title, author, isbn, category, page_count, publisher, price) VALUES (" +
                    "'Java Programowanie praktyczne od podstaw'," +
                    "'Krzysztof Barteczko'," +
                    "'978-00-511-7839-4'," +
                    "'programowanie java'," +
                    "486," +
                    "'PWN'," +
                    "59.00" +
                    ");";*/
            statement.executeUpdate(addNewBook());


            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));

            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now));
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into books values (?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, "Tytuł nowej ksiazki");
            preparedStatement.setString(2, "Jack Nicolson");
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, "123-12-34");
            preparedStatement.setString(5, "programowanie");
            preparedStatement.setInt(6, 1152);
            preparedStatement.setString(7, "pwn");
            preparedStatement.setInt(8, 522);
            preparedStatement.setInt(9, 1);

            //preparedStatement.execute();


            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int pageCount = resultSet.getInt("page_count");
                String title = resultSet.getString("title");
                Double price = resultSet.getDouble("price");
                System.out.println(title + ", liczba stron: " + pageCount+", cena: "+price);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }


    }
}
