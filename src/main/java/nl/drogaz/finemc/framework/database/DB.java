package nl.drogaz.finemc.framework.database;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.*;

public class DB {
    private JavaPlugin plugin;
    private Connection connection;

    public DB(JavaPlugin plugin) {
        this.plugin = plugin;
        setupDatabase();
    }

    private void setupDatabase() {

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        String dbUrl = "jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/finemc.db";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbUrl);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        createTable();
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS players ("
                    + "uuid TEXT PRIMARY KEY,"
                    + "tier TEXT DEFAULT '俰',"
                    + "shards INTEGER DEFAULT 0,"
                    + "rank TEXT DEFAULT 'Prisoner'"
                    + ")");
            statement.close();

            // Create the "cells" table with inside and outside as strings
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS cells ("
                    + "uuid TEXT,"
                    + "name TEXT PRIMARY KEY,"
                    + "price INTEGER,"
                    + "hired_time TEXT,"
                    + "end_time TEXT,"
                    + "inside TEXT,"
                    + "outside TEXT"
                    + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) throws SQLException {
        //this should error if the player already exists
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid, tier, shards, rank) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, "俰");
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, "Prisoner");
            preparedStatement.executeUpdate();
        }
    }

    public void addCell(UUID uuid, String name, int price, Location inside, Location outside) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cells (uuid, name, price, hired_time, end_time, inside, outside) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, String.valueOf(uuid));
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, price);
            preparedStatement.setString(4, String.valueOf(System.currentTimeMillis()));
            preparedStatement.setString(5, String.valueOf(System.currentTimeMillis() + 604800000));
            preparedStatement.setString(6, String.valueOf(inside));
            preparedStatement.setString(7, String.valueOf(outside));
            preparedStatement.executeUpdate();
        }
    }

    public boolean playerExists(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public String getData(Player player, String column) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString(column);
        }
    }

    public List<Map<String, Object>> getAllData(String table) throws SQLException {
        List<Map<String, Object>> data = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue);
                }
                data.add(row);
            }
        }

        return data;
    }

    public void setData(Player player, String table, String column, String value) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + table + " SET " + column + " = ? WHERE uuid = ?")) {
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }
}
