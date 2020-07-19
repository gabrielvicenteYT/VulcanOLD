package me.frep.vulcan;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.manager.EventManager;
import me.frep.vulcan.checks.Check;
import me.frep.vulcan.checks.combat.killaura.*;
import me.frep.vulcan.checks.combat.reach.ReachA;
import me.frep.vulcan.checks.combat.reach.ReachB;
import me.frep.vulcan.checks.player.badpackets.*;
import me.frep.vulcan.commands.AlertsCommand;
import me.frep.vulcan.commands.VulcanCommand;
import me.frep.vulcan.data.DataManager;
import me.frep.vulcan.data.events.DataEvents;
import me.frep.vulcan.utilities.UtilConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Vulcan extends JavaPlugin {

    public static Vulcan instance;
    public static List<Check> checks = new ArrayList<>();
    public static List<Player> alertsEnabled = new ArrayList<>();
    private DataManager dataManager = new DataManager();

    @Override
    public void onEnable() {
        instance = this;
        PacketEvents.load();
        PacketEvents.start(this);
        UtilConfig.getInstance().generateConfig();
        UtilConfig.getInstance().generateChecksConfig();
        registerCommands();
        enableAlerts();
        registerChecks();
        reloadPlayerData();
    }

    @Override
    public void onDisable() {
        PacketEvents.stop();
        instance = null;
    }

    private void registerChecks() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        EventManager em = PacketEvents.getAPI().getEventManager();
        checks.add(new ReachA());
        em.registerListener(new ReachA());
        checks.add(new ReachB());
        pm.registerEvents(new ReachB(), this);
        checks.add(new KillAuraA());
        em.registerListener(new KillAuraA());
        checks.add(new KillAuraB());
        em.registerListener(new KillAuraB());
        checks.add(new KillAuraC());
        em.registerListener(new KillAuraC());
        checks.add(new KillAuraD());
        em.registerListener(new KillAuraD());
        checks.add(new KillAuraE());
        em.registerListener(new KillAuraE());
        checks.add(new KillAuraF());
        em.registerListener(new KillAuraF());
        checks.add(new KillAuraG());
        em.registerListener(new KillAuraG());
        checks.add(new KillAuraH());
        em.registerListener(new KillAuraH());
        checks.add(new KillAuraI());
        em.registerListener(new KillAuraI());
        checks.add(new KillAuraJ());
        em.registerListener(new KillAuraJ());
        checks.add(new KillAuraK());
        em.registerListener(new KillAuraK());
        checks.add(new KillAuraL());
        em.registerListener(new KillAuraL());
        checks.add(new KillAuraM());
        em.registerListener(new KillAuraM());
        checks.add(new KillAuraN());
        em.registerListener(new KillAuraN());
        checks.add(new KillAuraO());
        em.registerListener(new KillAuraO());
        checks.add(new KillAuraP());
        em.registerListener(new KillAuraP());
        checks.add(new KillAuraQ());
        em.registerListener(new KillAuraQ());
        checks.add(new KillAuraR());
        em.registerListener(new KillAuraR());
        checks.add(new KillAuraS());
        em.registerListener(new KillAuraS());
        checks.add(new BadPacketsA());
        em.registerListener(new BadPacketsA());
        checks.add(new BadPacketsB());
        em.registerListener(new BadPacketsB());
        checks.add(new BadPacketsC());
        em.registerListener(new BadPacketsC());
        checks.add(new BadPacketsD());
        em.registerListener(new BadPacketsD());
        checks.add(new BadPacketsE());
        em.registerListener(new BadPacketsE());
        checks.add(new BadPacketsF());
        em.registerListener(new BadPacketsF());

        em.registerListener(new DataEvents());
        pm.registerEvents(new DataEvents(), this);
    }

    private void registerCommands() {
        getCommand("vulcan").setExecutor(new VulcanCommand());
        getCommand("alerts").setExecutor(new AlertsCommand());
    }

    private void enableAlerts() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasPermission("vulcan.alerts")) {
                alertsEnabled.add(p);
            }
        }
    }

    private void reloadPlayerData() {
        dataManager.getDataPlayers().clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            dataManager.createPlayerData(p);
        }
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}