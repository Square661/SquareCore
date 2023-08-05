package commands.admin;

import com.Square.RetronixFreeze.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Maintenance implements CommandExecutor {
    public Maintenance(Main main) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        // Write a command that turns maintenance mode on, which kicks all players that do not have the sqc.staff permission.
        // If maintenance mode is already on, turn it off.
        // If maintenance mode is off, turn it on.

        if(cmd.getName().equalsIgnoreCase("maintenance")) {
            if(!sender.hasPermission("sqc.maintenance") || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            if(args.length == 0) {
                if(!Main.maintenance) {
                    sender.sendMessage(ChatColor.GREEN + "Maintenance mode has been turned on.");
                    // Start a 30 second countdown in chat using broadcastMessage and after that kick all players that do not have the sqc.staff permission.
                    for (int i = 30; i > 0; i--) {
                        Bukkit.broadcastMessage(ChatColor.RED + "Server will enter maintenance mode in " + i + " seconds.");
                    }
                    Bukkit.getServer().getOnlinePlayers().forEach((player) -> {
                        if(!player.hasPermission("sqc.staff")) {
                            player.kickPlayer(ChatColor.RED + "Server is currently in maintenance mode.");
                        }
                    });
                    // Set the boolean to true
                    Main.maintenance = true;
                    // While the boolean is true, if a player joins, kick them.
                    // If the boolean is false, let them join.

                    // TODO - Implement this into an event listener for more effective handling
                    while (Main.maintenance) {
                        Bukkit.getServer().getOnlinePlayers().forEach((player) -> {
                            if(!player.hasPermission("sqc.staff")) {
                                player.kickPlayer(ChatColor.RED + "Server is currently in maintenance mode.");
                            }
                        });
                    }

                }
                else {
                    sender.sendMessage(ChatColor.GREEN + "Maintenance mode has been turned off.");
                    // Console log
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Maintenance mode has been turned off.");
                    // Set the boolean to false
                    Main.maintenance = false;

                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }



        return false;
    }
}
