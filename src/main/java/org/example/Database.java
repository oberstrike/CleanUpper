package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Database {
    protected String url;
    protected String username;
    protected String password;

    public Database(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void dropDatabases(String databaseNamePattern) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (Statement selectStatement = connection.createStatement()) {
                ResultSet resultSet = selectStatement.executeQuery(getSelectQuery(databaseNamePattern));
                while (resultSet.next()) {
                    String databaseName = resultSet.getString(getDatabaseNameColumn());
                    try (Statement dropStatement = connection.createStatement()) {
                        dropStatement.executeUpdate(getDropQuery(databaseName));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract String getSelectQuery(String databaseNamePattern);

    abstract String getDatabaseNameColumn();

    abstract String getDropQuery(String databaseName);
}