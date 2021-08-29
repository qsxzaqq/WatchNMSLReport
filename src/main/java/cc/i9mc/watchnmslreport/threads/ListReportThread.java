package cc.i9mc.watchnmslreport.threads;

import cc.i9mc.watchnmslreport.BukkitReport;
import cc.i9mc.watchnmslreport.database.Report;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ListReportThread implements Runnable {
    private final CommandSender sender;

    public ListReportThread(CommandSender sender) {
        this.sender = sender;
        Bukkit.getScheduler().runTaskAsynchronously(BukkitReport.getInstance(), this);
    }

    public void run() {

        for (Report report : BukkitReport.getInstance().getReportManager().getAllReports()) {
            this.sender.sendMessage("§a- §bID " + report.getID() + "§a ------- 于服务器 §e" + report.getServerID());
            this.sender.sendMessage("§a 举报者: " + report.getReporter() + " 于 " + formatTime(report.getTime()));
            this.sender.sendMessage("§c 被举报玩家 " + report.getPlayer() + " 原因 " + report.getReason());
        }

    }

    private static String formatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }
}
