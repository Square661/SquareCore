package events;

import com.Square.RetronixFreeze.SQL.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinEvent implements Listener {
    private final Plugin plugin;
    private final MySQL SQL;

    public JoinEvent(Plugin plugin, MySQL SQL) {
        this.SQL = SQL;
        this.plugin = plugin;
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Connection conn = SQL.getConnection();
        Player player = event.getPlayer();

        long loginTime = System.currentTimeMillis();
        player.setMetadata("loginTime", new FixedMetadataValue(plugin, loginTime));
        Bukkit.getLogger().severe("DEBUG - Player has joined the server and the login time has been set");

        try {
            if (conn.isClosed()) {
                Bukkit.getLogger().info("SQL closed, attempting connect");
                SQL.connect();
                Bukkit.getLogger().info("CONNECTED");
            } else {
                Bukkit.broadcastMessage(player.getAddress().getAddress().getHostAddress() + "has joined the server and the SQL connection is valid");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS player_info (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255), ip VARCHAR(255), PRIMARY KEY(id));");
            stmt.executeUpdate();
            // We have added the collums, now we need to add the values of the collums for this player, so we need to use a prepared statement

            // But lets put this in an if statement, that com.Square.RetronixFreeze.checks if the player is already in the database, and if the IP has not changed, dont do anything
            PreparedStatement check = conn.prepareStatement("SELECT * FROM player_info WHERE name = ?");
            check.setString(1, player.getName());
            ResultSet result = check.executeQuery();
            if (result.next()) {
                String ip = result.getString("ip");
                String name = result.getString("name");
                if (ip.equals(player.getAddress().getAddress().getHostAddress())) {
                    Bukkit.getLogger().info("Player " + name + " has already joined the server with IP " + ip);
                    return;
                }
            }

            PreparedStatement insert = conn.prepareStatement("INSERT INTO player_info (name, ip) VALUES (?, ?)");
            insert.setString(1, player.getName());
            insert.setString(2, player.getAddress().getAddress().getHostAddress());
            insert.executeUpdate();

            Bukkit.getLogger().info("player_info table created or already exists");
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to create player_info table: " + e.getMessage());
            return;
        }

        // Also add the current time to the Login collum, so that we can caluclate how long the player has been online in a session
        // The collum Login is already on the database
        // Just set the Login collum value for that player to the current time
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE playtime SET Login = ? WHERE name = ?");
            stmt.setString(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            stmt.setString(2, player.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed to update Login collum: " + e.getMessage());
            return;
        }


    }
}



