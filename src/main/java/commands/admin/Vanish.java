package commands.admin;

import com.Square.RetronixFreeze.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Vanish implements CommandExecutor {

    public static ArrayList<Player> vanished = new ArrayList<>();

    private final Main plugin;

    public Vanish(Main plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("sqc.vanish")) {
                if(!vanished.contains(player)) {
                    vanished.add(player);
                    player.sendMessage(ChatColor.GREEN + "You have been vanished!");
                    for (Player p : plugin.getServer().getOnlinePlayers()) {
                        if (!p.hasPermission("sqc.vanish")) {
                            p.hidePlayer(plugin, player);
                        }
                    }
                }
                else {
                    vanished.remove(player);
                    player.sendMessage(ChatColor.GREEN + "You have been unvanished!");
                    for (Player p : plugin.getServer().getOnlinePlayers()) {
                        if (!p.hasPermission("sqc.vanish")) {
                            p.showPlayer(plugin, player);
                        }
                    }
                    }
            }else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }

        }
        return true;
    }
}
