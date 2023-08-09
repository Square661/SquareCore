package commands.admin;

import com.Square.RetronixFreeze.SQL.MySQL;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLCheck implements CommandExecutor {
    private final MySQL SQL;

    public SQLCheck(MySQL SQL) {
        this.SQL = SQL;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Connection conn = SQL.getConnection();
        // Player must have scq.admin permission to use this command
        if (!sender.hasPermission("sqc.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("checksql")) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.GREEN + "Checking SQL connection...");
            // check if the connection is closed
            if (SQL.isConnected()) {
                player.sendMessage(ChatColor.GREEN + "Connection is open.");
            } else if (!SQL.isConnected() || conn == null) {
                player.sendMessage(ChatColor.RED + "Connection is closed. Debug Code (#6006)");
            }

            String command = null;
            if (args.length <= 0) {
                player.sendMessage(ChatColor.GREEN + "Please use the command /checksql clearplaytime to clear the playtime table.");
                return true;
            } else {
                command = args[0];
            }

            if (command.equalsIgnoreCase("clearplaytime")) {
                // Clear all the data from the playtime table
                try {
                    PreparedStatement statement = conn.prepareStatement("DELETE FROM playtime");
                    statement.executeUpdate();
                    sender.sendMessage(ChatColor.GREEN + "Successfully cleared the playtime table.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.RED + "An error occurred while attempting to clear the playtime table.");
                }
            } else if (args.length <= 0) {
                try {
                    if (conn.isClosed()) {
                        sender.sendMessage(ChatColor.RED + "Connection is closed. Please restart the plugin or issue the command /initSQL to attempt a connection with the database");
                    } else {
                        sender.sendMessage(ChatColor.GREEN + "Connection with the database is open. Please use the command /closeSQL to attempt to disconnect from the database.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        }
        return true;
    }
}
