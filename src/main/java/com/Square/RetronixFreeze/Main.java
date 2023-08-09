package com.Square.RetronixFreeze;

import aEvent.PlayerClickListener;
import com.Square.RetronixFreeze.SQL.MySQL;
import com.Square.RetronixFreeze.functions.Messages;
import com.Square.RetronixFreeze.functions.RankFunctions;
import com.Square.RetronixFreeze.functions.VanishFunctions;
import commands.admin.*;
import commands.player.HealthCheck;
import commands.player.Help;
import commands.player.List;
import commands.player.Spawn;
import events.JoinEvent;
import events.LeaveEvent;
import events.PlayerMessageEvent;
import jdk.nashorn.internal.objects.annotations.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.*;

public class Main extends JavaPlugin {

    // Maintenance mode boolean
    public static boolean maintenance = false;

    public MySQL SQL;
    public Messages messages;
    public RankFunctions rankFunctions;
    public VanishFunctions vanishFunctions;

    @Override
    public void onEnable() {


        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[SquareCore] has been enabled");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[RF] Debug mode has been enabled.");

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        LuckPerms api = null;
        if (provider != null) {
            api = provider.getProvider();
            // Intaliase rank functions
            this.rankFunctions = new RankFunctions(api, this);

        } else {
            Bukkit.getLogger().info("LuckPerms not found!");
        }


        this.SQL = new MySQL();
        this.messages = new Messages();
        this.vanishFunctions = new VanishFunctions(this);
        try {
            SQL.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("Database connection failed.");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("Database connection failed.");
        }

        if (SQL.isConnected()) {
            Bukkit.getLogger().info("Database is connected!");
        }


        // Admin commands
        getCommand("gamemode").setExecutor(new Gamemode());
        getCommand("heal").setExecutor(new Heal(this));
        getCommand("checksql").setExecutor(new SQLCheck(SQL));
        getCommand("maintenance").setExecutor(new Maintenance(this));
        getCommand("setspawn").setExecutor(new SetSpawn());
        getCommand("rank").setExecutor(new RankCheck(this));
        getCommand("vanish").setExecutor(new Vanish(this));


        // Player commands
        getCommand("help").setExecutor(new Help(this));
        getCommand("checkhealth").setExecutor(new HealthCheck());
        getCommand("list").setExecutor(new List(LuckPermsProvider.get(), this));
        getCommand("spawn").setExecutor(new Spawn());

        // Events
        getServer().getPluginManager().registerEvents(new JoinEvent(this, SQL), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(this, SQL), this);
        getServer().getPluginManager().registerEvents(new PlayerMessageEvent(), this);

        // getServer().getPluginManager().registerEvents(new PlayerClickListener(this), this);


    }




    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SquareCore] has been disabled");

        SQL.disconnect();
        Bukkit.getLogger().info("Database has disconnected.");

    }




}

