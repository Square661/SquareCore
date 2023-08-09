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

    }
}



