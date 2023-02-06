package org.example;

/**
 * Hello world!
 */

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Map<String, Object>> config = new Yaml().load(Files.newInputStream(Paths.get("application.yaml")));

        var sqlServerConfig = config.get("sqlServer");
        var sqlServerDatabaseConfig = new SqlServerDatabase.SqlServerDatabaseConfig(
                (String) sqlServerConfig.get("host"),
                (int) sqlServerConfig.get("port"),
                (String) sqlServerConfig.get("database"),
                (String) sqlServerConfig.get("username"),
                (String) sqlServerConfig.get("password")
        );
        var sqlServerDatabase = new SqlServerDatabase(sqlServerDatabaseConfig);

        var postgresConfig = config.get("postgres");
        PostgresDatabase.PostgresDatabaseConfig postgresDatabaseConfig = new PostgresDatabase.PostgresDatabaseConfig(
                (String) postgresConfig.get("host"),
                (int) postgresConfig.get("port"),
                (String) postgresConfig.get("database"),
                (String) postgresConfig.get("username"),
                (String) postgresConfig.get("password")
        );
        var postgresDatabase = new PostgresDatabase(postgresDatabaseConfig);

        postgresDatabase.dropDatabases("ci%");
        sqlServerDatabase.dropDatabases("ci%");
    }
}