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

        SQL.updatePlayer(player, "leave");


    }



}
