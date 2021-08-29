package cc.i9mc.watchnmslreport;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.watchnmslreport.commands.BukkitReloadCommand;
import cc.i9mc.watchnmslreport.commands.ReportCommand;
import cc.i9mc.watchnmslreport.commands.ReportManagerCommand;
import cc.i9mc.watchnmslreport.commands.StaffReportCommand;
import cc.i9mc.watchnmslreport.database.ReportManager;
import cc.i9mc.watchnmslreport.gui.ManageGUICreator;
import cc.i9mc.watchnmslreport.gui.ManageGUIListener;
import cc.i9mc.watchnmslreport.messagechannel.BukkitMessageChannel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class BukkitReport extends JavaPlugin {
    @Getter
    private static BukkitReport instance;

    @Getter
    private ReportManager reportManager;

    @Getter
    private BukkitMessageChannel messageChannel;

    @Getter
    private ManageGUICreator manageGUICreator;

    @Getter
    @Setter
    private HashMap<String, Integer> staffs;

    @Override
    public void onEnable() {
        instance = this;

        reportManager = new ReportManager();
        messageChannel = new BukkitMessageChannel();
        manageGUICreator = new ManageGUICreator();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().getPluginCommand("report").setExecutor(new ReportCommand());
        getServer().getPluginCommand("wnm").setExecutor(new ReportManagerCommand());
        getServer().getPluginCommand("staff").setExecutor(new StaffReportCommand());
        getServer().getPluginCommand("wrr").setExecutor(new BukkitReloadCommand());

        getServer().getPluginManager().registerEvents(new ManageGUIListener(), this);
        //getServer().getPluginManager().registerEvents(new BukkitChannelListener(), this);

        BukkitGameUtils.getInstance().getConnectionPoolHandler().registerDatabase("bungee");

        staffs = reportManager.getAllStaffs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
