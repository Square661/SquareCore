package commands.player;

import com.Square.RetronixFreeze.Main;
import com.Square.RetronixFreeze.functions.RankFunctions;
import com.Square.RetronixFreeze.functions.VanishFunctions;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class List implements CommandExecutor {
    private final Main plugin;
    private final LuckPerms api = LuckPermsProvider.get();
    private final VanishFunctions vanishFunctions;

    public List(LuckPerms luckPerms, Main plugin) {
        this.plugin = plugin;
        this.vanishFunctions = new VanishFunctions(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (cmd.getName().equalsIgnoreCase("list")) {
            Player[] players = plugin.getServer().getOnlinePlayers().toArray(new Player[0]);
            int onlineCount = 0;

            for (Player player : players) {
                if (vanishFunctions.isVanished(player)) {
                    onlineCount++;
                } else if (sender.hasPermission("sqc.vanish.seevanished")) {
                    onlineCount++;
                }
            }

            sender.sendMessage(ChatColor.YELLOW + "Players online (" + onlineCount + "):");

            for (Player player : players) {
                String displayName = RankFunctions.rankColourCode(player) + player.getName();
                String prefix = api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
                if (prefix == null) {
                    prefix = "&7";
                }
                prefix = prefix.replace("&", "§");

                if (vanishFunctions.isVanished(player)) {
                    if (sender.hasPermission("sqc.vanish.seevanished")) {
                        displayName += ChatColor.GRAY + " [Vanished]";
                        sender.sendMessage(ChatColor.GRAY + "• " + prefix + " " + ChatColor.RESET + displayName);
                    }
                } else {
                    sender.sendMessage(ChatColor.GRAY + "• " + prefix + " " + ChatColor.RESET + displayName);
                }
            }

            return true;
        }
        return true;
    }
}





















//            Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
//            sender.sendMessage(ChatColor.YELLOW + "Players online (" + players.length + "):");
//            // TODO - Make this display the player's name in the same colour as their rank.
//            // TODO - Possibly check their rank and then set the colour of the name accordingly based on a switch statement which can be in their main class and accessible from all classes.
//
//            for (Player player : players) {
//                String prefix = api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
//                player.sendMessage(ChatColor.YELLOW + "Players online (" + players.length + "):");
//                prefix = prefix.replace("&", "§");
//                sender.sendMessage(prefix + " " + player.getName());
//
//
//                return true;
//            }
//        }
//        return true;
//    }
//
