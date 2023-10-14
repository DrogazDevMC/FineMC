package nl.drogaz.finemc.framework.database;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

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
                    + "tier TEXT DEFAULT 'I',"
                    + "shards INTEGER DEFAULT 0,"
                    + "rank TEXT DEFAULT 'Prisoner'"
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
            preparedStatement.setString(2, "I");
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, "Prisoner");
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

    public void setData(Player player, String column, String value) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET " + column + " = ? WHERE uuid = ?")) {
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }
}
