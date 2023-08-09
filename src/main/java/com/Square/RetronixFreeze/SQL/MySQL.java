package com.Square.RetronixFreeze.SQL;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class MySQL {

    private String host = "localhost";
    private String port = "3306";
    private String database = "squarecore";
    private String username = "root";
    private String password = "";

    private Connection connection;


    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false",
                    username, password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Connection getConnection() {
        return connection;
    }


    public void createTable() {
        // If table player_data doesn't exist, create it
        try {
            Connection conn = getConnection();
            String sql = "CREATE TABLE IF NOT EXISTS player_data (id INT NOT NULL AUTO_INCREMENT, uuid VARCHAR(36) NOT NULL, username VARCHAR(16) NOT NULL, ip VARCHAR(16) NOT NULL, login_time DATETIME NOT NULL, logout_time DATETIME, PRIMARY KEY (id))";
            conn.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createPlayer(Player player) {
        String UUID = player.getUniqueId().toString();
        String username = player.getName();
        String ip = player.getAddress().getAddress().getHostAddress();
        ZonedDateTime currentTimeGMT = ZonedDateTime.now(ZoneId.of("GMT"));
        Timestamp loginTime = Timestamp.from(currentTimeGMT.toInstant());

        try {
            Connection conn = getConnection();
            // Check if player exists in database
            String sql = "INSERT INTO player_data (uuid, username, ip, login_time, logout_time) VALUES (?, ?, ?, ?, '1970-01-01 00:00:00')";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, UUID);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, ip);
            preparedStatement.setTimestamp(4, loginTime);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean playerExists(Player player) {
        String UUID = player.getUniqueId().toString();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM player_data WHERE uuid = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, UUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void updatePlayer(Player player, String joinorleave) {
        // Update the player's IP address, if String inorout = in, then update their login time, if it equals out, update their logout time
        String UUID = player.getUniqueId().toString();
        String ip = player.getAddress().getAddress().getHostAddress();
        ZonedDateTime currentTimeGMT = ZonedDateTime.now(ZoneId.of("GMT"));
        Timestamp time = Timestamp.from(currentTimeGMT.toInstant());

        if (joinorleave.equals("join")) {
            try {
                Connection conn = getConnection();
                String sql = "UPDATE player_data SET ip = ?, login_time = ? WHERE uuid = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, ip);
                preparedStatement.setTimestamp(2, time);
                preparedStatement.setString(3, UUID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (joinorleave.equals("leave")) {
            try {
                Connection conn = getConnection();
                String sql = "UPDATE player_data SET logout_time = ? WHERE uuid = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setTimestamp(1, time);
                preparedStatement.setString(2, UUID);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public UUID getUUID(String player) {
        // Search the database for the player's name, if found, return their UUID, if not, return null
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM player_data WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, player);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return UUID.fromString(resultSet.getString("uuid"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public String getIP(String player) {
        // Search the database for the player's name, if found, return their IP, if not, return null
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM player_data WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, player);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("ip");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}





