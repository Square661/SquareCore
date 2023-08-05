package commands.admin;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        // Sets the spawn location to the player's current location
        if(cmd.getName().equalsIgnoreCase("setspawn")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(player.hasPermission("sqc.setspawn")) {
                    World world = player.getWorld();
                    // TODO: Set spawn location to include yaw and pitch
                    player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                    player.sendMessage(ChatColor.GREEN + "Spawn location set to your current location.");
                    player.sendMessage(ChatColor.AQUA + "X: " + player.getLocation().getBlockX() + " Y: " + player.getLocation().getBlockY() + " Z: " + player.getLocation().getBlockZ() + " World: " + world.getName());
                }
                else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                }
                return true;
            }
        }


        return false;
    }
}
