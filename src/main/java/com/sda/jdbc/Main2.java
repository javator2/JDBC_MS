package com.sda.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main2 {
    public static void main(String[] args) throws SQLException {

        Connection connection = Database.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(Main.addNewBook());

        Database.closeConnetion();
        statement.close();

    }
}
