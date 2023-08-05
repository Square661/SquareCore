package commands.player;

import com.Square.RetronixFreeze.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Help implements CommandExecutor {

    private final Main plugin;

    public Help(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "Commands:");

        for (String command : plugin.getDescription().getCommands().keySet()) {
            String description = plugin.getDescription().getCommands().get(command).get("description").toString();
            String usage = plugin.getDescription().getCommands().get(command).get("usage").toString();
            String aliases = plugin.getDescription().getCommands().get(command).get("aliases").toString();

            sender.sendMessage(ChatColor.GREEN + "/" + command + " - " + ChatColor.GRAY + usage + " - " + ChatColor.WHITE + description);
        }

        return true;
    }
}
