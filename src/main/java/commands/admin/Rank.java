package commands.admin;

import com.Square.RetronixFreeze.Main;
import com.Square.RetronixFreeze.functions.RankFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rank implements CommandExecutor {
    private final Main plugin;

    public Rank(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;
        if(!player.hasPermission("sqc.rank.manage")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            String targetPlayerName = args[1];
            String targetRank = args[2];
            Player targetPlayer = plugin.getServer().getPlayer(targetPlayerName);

            if (targetPlayer == null) {
                player.sendMessage("Player " + targetPlayerName + " is not online or does not exist.");
                return true;
            }

            plugin.rankFunctions.setRank(targetPlayer, targetRank);
            ChatColor rankColor = RankFunctions.rankColourCode(targetPlayer);
            player.sendMessage(ChatColor.GREEN + "You have set " + ChatColor.YELLOW + targetPlayer.getName() + ChatColor.GREEN + "'s rank to " + rankColor + targetRank);
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "Invalid arguments. Usage: /rank set <player> <rank>");
            return true;
        }
    }
}
