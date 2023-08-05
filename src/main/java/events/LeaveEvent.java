package events;

import com.Square.RetronixFreeze.SQL.MySQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaveEvent implements Listener {
    private final Plugin plugin;
    private final MySQL SQL;

    public LeaveEvent(Plugin plugin, MySQL SQL) {
        this.SQL = SQL;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) throws SQLException {
        Player player = event.getPlayer();
        long loginTime = player.getMetadata("loginTime").get(0).asLong();
        long logoutTime = System.currentTimeMillis();
        long playtime = logoutTime - loginTime;

        SQL.savePlaytime(player, String.valueOf(playtime), "date");

        // Save the playtime to the database
        Connection conn = SQL.getConnection();
        long currentPlaytime = 0;
        Statement stmt = conn.createStatement();
        String query = "SELECT playtime FROM playtime WHERE username='" + player.getName() + "';";
        try (ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                currentPlaytime = rs.getLong("playtime");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculate the new playtime
        long newPlaytime = currentPlaytime + playtime;

        // Update the playtime for the player
        query = "UPDATE playtime SET playtime=" + newPlaytime + " WHERE username='" + player.getName() + "';";
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
