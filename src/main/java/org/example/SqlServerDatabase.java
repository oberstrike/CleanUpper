package org.example;

import lombok.Builder;
import lombok.Value;

class SqlServerDatabase extends Database {
    public SqlServerDatabase(SqlServerDatabaseConfig config) {
        super("jdbc:sqlserver://" + config.getHost() + ":" + config.getPort() + ";databaseName=" + config.getDatabase(), config.getUsername(), config.getPassword());
    }

    @Override
    protected String getSelectQuery(String databaseNamePattern) {
        return "SELECT name FROM sys.databases WHERE name LIKE '" + databaseNamePattern + "'";
    }

    @Override
    protected String getDatabaseNameColumn() {
        return "name";
    }

    @Override
    protected String getDropQuery(String databaseName) {
        return "DROP DATABASE " + databaseName;
    }


    @Value
    static class SqlServerDatabaseConfig {
        String host;
        int port;
        String database;
        String username;
        String password;

    }

}