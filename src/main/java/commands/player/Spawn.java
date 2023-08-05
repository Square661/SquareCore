package commands.player;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        // Sends the player to the custom spawn location set by /setspawn
        if(cmd.getName().equalsIgnoreCase("spawn")) {
            Player player = (Player) sender;
            if(player.hasPermission("sqc.spawn")) {
                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(ChatColor.GREEN + "Teleported to spawn.");
                return true;
            }
            else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
        }


        return false;
    }
}
