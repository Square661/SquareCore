package commands.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealthCheck implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
            if (cmd.getName().equalsIgnoreCase("checkhealth")) {
                if (args.length == 0) { // No argument provided, check sender's health
                    if (!(sender instanceof Player)) { // Command was sent from console
                        sender.sendMessage(ChatColor.RED + "You must specify a player to use this command from console!");
                        return true;
                    }
                    Player player = (Player) sender;
                    double health = player.getHealth();
                    sender.sendMessage(ChatColor.GREEN + "Your health is " + health + " hearts.");
                } else { // Player name provided, check their health
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) { // Player not found
                        sender.sendMessage(ChatColor.RED + "Player not found!");
                        return true;
                    }
                    double health = target.getHealth();
                    sender.sendMessage(ChatColor.GREEN + target.getName() + "'s health is " + health + " hearts.");
                }
                return true;
            }
            return false;
        }
    }
