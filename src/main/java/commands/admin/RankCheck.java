package commands.admin;

import com.Square.RetronixFreeze.Main;
import com.Square.RetronixFreeze.functions.RankFunctions;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RankCheck implements CommandExecutor {
    private final LuckPerms api = LuckPermsProvider.get();
    private final Main plugin;

    public RankCheck(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("rank")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;
        String primaryGroup = api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();

        if (args.length == 0) {
            sendPlayerRankInfo(player, player.getName(), primaryGroup);
        } else if (args.length == 1) {
            String targetPlayer = args[0];
            Player targetPlayerObj = plugin.getServer().getPlayer(targetPlayer);
            if (targetPlayerObj == null) {
                sender.sendMessage(ChatColor.RED + "Player " + targetPlayer + " is not online or does not exist.");
                return true;
            }
            String targetPrimaryGroup = api.getUserManager().getUser(targetPlayerObj.getUniqueId()).getPrimaryGroup();
            sendPlayerRankInfo(player, targetPlayerObj.getName(), targetPrimaryGroup);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            String targetPlayer = args[1];
            String newRank = args[2];
            Player targetPlayerObj = plugin.getServer().getPlayer(targetPlayer);

            if (targetPlayerObj == null) {
                sender.sendMessage(ChatColor.RED + "Player " + targetPlayer + " is not online or does not exist.");
                return true;
            }

            if (!player.hasPermission("sqc.rankcheck.set")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to set ranks!");
                return true;
            }

            // Call the setRank function from RankFunctions here
            plugin.rankFunctions.setRank(targetPlayerObj, newRank);
            return true;
        }

            sender.sendMessage(ChatColor.RED + "Incorrect usage! Use /rank set <player> <rank>");
            return true;
    }

    private void sendPlayerRankInfo(Player sender, String targetPlayer, String primaryGroup) {
        String prefix = api.getUserManager().getUser(targetPlayer).getCachedData().getMetaData().getPrefix();
        String suffix = api.getUserManager().getUser(targetPlayer).getCachedData().getMetaData().getSuffix();

        // ... Other info retrieval ...

        sender.sendMessage(ChatColor.RED + "--------------------");
        sender.sendMessage("Player » " + ChatColor.RESET + ChatColor.YELLOW + targetPlayer);
        sender.sendMessage("Rank » " + ChatColor.YELLOW + Objects.requireNonNull(api.getGroupManager().getGroup(primaryGroup)).getDisplayName());
        sender.sendMessage("Prefix » " + ChatColor.RESET + prefix);
        sender.sendMessage("Suffix » " + ChatColor.RESET + suffix);
        // ... Other info display ...
        sender.sendMessage(ChatColor.RED + "--------------------");
    }
}
