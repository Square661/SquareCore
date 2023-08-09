package events;

import com.Square.RetronixFreeze.functions.RankFunctions;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;


public class PlayerMessageEvent implements Listener {
    LuckPerms api = LuckPermsProvider.get();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Format the chat message to include the player's rank and username in the chat message
        Player player = event.getPlayer();
        String message = event.getMessage();
        String primaryGroup = (Objects.requireNonNull(api.getUserManager().getUser(player.getUniqueId()))).getPrimaryGroup();
        String primaryGroupMeta = Objects.requireNonNull(api.getUserManager().getUser(player.getUniqueId())).getCachedData().getMetaData().getPrefix();
        String primaryGroupName = Objects.requireNonNull(api.getGroupManager().getGroup(primaryGroup)).getName();
        if(primaryGroupMeta == null) {
            primaryGroupMeta = "&7";
        }

        String colouredPrefix = primaryGroupMeta.replace("&", "§");
        // Set their name to the colour of their rank
        ChatColor rankColor = RankFunctions.rankColourCode(player);
        if(player.hasPermission("sqc.chat.color")) {
            message = message.replace("&", "§");
        }
        event.setFormat(colouredPrefix + " " + rankColor + player.getName() + ChatColor.RESET + ": " + message);



    }
}
