package org.example;

import lombok.Value;

class PostgresDatabase extends Database {
    public PostgresDatabase(PostgresDatabaseConfig config) {
        super("jdbc:postgresql://" + config.host + ":" + config.port + "/" + config.database, config.username, config.password);
    }

    @Override
    protected String getSelectQuery(String databaseNamePattern) {
        return "SELECT datname FROM pg_database WHERE datname LIKE '" + databaseNamePattern + "'";
    }

    @Override
    protected String getDatabaseNameColumn() {
        return "datname";
    }

    @Override
    protected String getDropQuery(String databaseName) {
        return "DROP DATABASE " + databaseName;
    }

    @Value
    static class PostgresDatabaseConfig {
        String host;
        int port;
        String database;
        String username;
        String password;
    }

}
