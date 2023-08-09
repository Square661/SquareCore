package commands.admin;

import com.Square.RetronixFreeze.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IP implements CommandExecutor {
    public Main plugin;

    public IP(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if(!player.hasPermission("sqc.ip")) {
            plugin.messages.noPermissionMessage(player);
            return true;
        }
        // /ip <player>
        if(args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /ip <player>");
            return true;
        }
        else if (args.length == 1) {
            String targetPlayerName = args[0];
            player.sendMessage(ChatColor.GREEN + "Searching for player " + targetPlayerName + " in database...");
            String targetPlayerIP = plugin.SQL.getIP(targetPlayerName);
            if (targetPlayerIP == null) {
                player.sendMessage(ChatColor.RED + "Player " + targetPlayerName + " not found in database.");
                return true;
            } else {
                player.sendMessage(ChatColor.BLUE + "Player " + targetPlayerName + " IP: " + targetPlayerIP);
                return true;
            }



        }



        return false;
    }
}
