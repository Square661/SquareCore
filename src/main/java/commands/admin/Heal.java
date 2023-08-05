package commands.admin;

import com.Square.RetronixFreeze.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

    private final Main plugin;

    public Heal(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if (cmd.getName().equalsIgnoreCase("heal")) {
            if(!sender.hasPermission("sqc.heal")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            if (args.length <= 0) {
                Player player = (Player) sender;
                player.setHealth(player.getMaxHealth());
                player.sendMessage(ChatColor.GREEN + "You have been healed!");

            }
            else if (args.length == 1) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player != null) {
                    player.setHealth(player.getMaxHealth());
                    if(sender instanceof Player) {
                        player.sendMessage(ChatColor.GREEN + "You have been healed by " + sender.getName() + "!");
                    }
                    else {
                        player.sendMessage(ChatColor.GREEN + "You have been healed by console!");
                    }
                    sender.sendMessage(ChatColor.GREEN + "You have healed " + player.getName() + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found.");
                }
                return true;
            } else {
                return false;
            }
        }
        return true;
    }




    }


