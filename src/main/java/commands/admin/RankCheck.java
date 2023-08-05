package commands.admin;

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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("rankcheck")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;
        String targetPlayer = args.length == 0 ? player.getName() : args[0];

        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Incorrect usage! Use /rankcheck [player]");
            return true;
        }

        String primaryGroup = Objects.requireNonNull(api.getUserManager().getUser(targetPlayer)).getPrimaryGroup();
        String prefix = Objects.requireNonNull(api.getUserManager().getUser(targetPlayer)).getCachedData().getMetaData().getPrefix();
        String suffix = Objects.requireNonNull(api.getUserManager().getUser(targetPlayer)).getCachedData().getMetaData().getSuffix();
        String permissions = api.getUserManager().getUser(targetPlayer).getCachedData().getPermissionData().getPermissionMap().toString();

        if (args.length == 0 || (args.length == 1 && player.hasPermission("sqc.rankcheck.extended"))) {
            prefix = prefix.replace("&", "§");
            suffix = suffix != null ? suffix.replace("&", "§") : "None";

            // Format a message containing their rank, prefix, suffix, and permissions
            sender.sendMessage(ChatColor.RED + "--------------------");
            sender.sendMessage("Player » " + ChatColor.RESET + ChatColor.YELLOW + targetPlayer);
            sender.sendMessage("Rank » " + ChatColor.YELLOW + Objects.requireNonNull(api.getGroupManager().getGroup(primaryGroup)).getDisplayName());
            sender.sendMessage("Prefix » " + ChatColor.RESET + prefix);
            sender.sendMessage("Suffix » " + ChatColor.RESET + suffix);
            sender.sendMessage("Permissions » " + ChatColor.YELLOW + permissions);
            sender.sendMessage(ChatColor.RED + "--------------------");
            return true;

        } else if (args.length == 1 && player.hasPermission("sqc.rankcheck")) {
            sender.sendMessage(ChatColor.YELLOW + targetPlayer + "'s rank is " + ChatColor.RESET + Objects.requireNonNull(api.getGroupManager().getGroup(primaryGroup)).getDisplayName());
            prefix = prefix.replace("&", "§");
            sender.sendMessage(ChatColor.YELLOW + "Their prefix is " + ChatColor.RESET + prefix);
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
    }
}
