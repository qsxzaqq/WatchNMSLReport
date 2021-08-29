package cc.i9mc.watchnmslreport;


import cc.i9mc.gameutils.BungeeGameUtils;
import cc.i9mc.watchnmslreport.commands.BungeeReloadCommand;
import cc.i9mc.watchnmslreport.database.ReportManager;
import cc.i9mc.watchnmslreport.messagechannel.BungeeMessageChannel;
import cc.i9mc.watchnmslreport.netty.NioClient;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashMap;

public final class BungeeReport extends Plugin {
    @Getter
    private static BungeeReport instance;

    @Getter
    private ReportManager reportManager;

    @Getter
    private static NioClient nioClient;

    @Getter
    @Setter
    private HashMap<String, Integer> staffs;

    @Override
    public void onEnable() {
        instance = this;

        reportManager = new ReportManager();
        nioClient = new NioClient();
        new Thread(nioClient::bind).start();

        getProxy().getPluginManager().registerCommand(this, new BungeeReloadCommand("wrr"));
        getProxy().getPluginManager().registerListener(this, new BungeeMessageChannel());
        BungeeGameUtils.getInstance().getConnectionPoolHandler().registerDatabase("bungee");
        BungeeGameUtils.getInstance().getPubSubListener().addChannel("Report");

        staffs = reportManager.getAllStaffs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
