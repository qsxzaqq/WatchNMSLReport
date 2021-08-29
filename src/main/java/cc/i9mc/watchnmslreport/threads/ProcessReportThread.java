package cc.i9mc.watchnmslreport.threads;

import java.io.IOException;
import java.sql.SQLException;

import cc.i9mc.watchnmslreport.BukkitReport;
import cc.i9mc.watchnmslreport.database.Report;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProcessReportThread implements Runnable {
    private final CommandSender sender;
    private final String id;

    public ProcessReportThread(CommandSender sender, String id) {
        this.sender = sender;
        this.id = id;
        Bukkit.getScheduler().runTaskAsynchronously(BukkitReport.getInstance(), this);
    }

    public void run() {
        Report rpt = BukkitReport.getInstance().getReportManager().getReport(this.id);
        if (rpt == null) {
            this.sender.sendMessage("§c举报不存在");
            return;
        }

        if (BukkitReport.getInstance().getStaffs().getOrDefault(sender.getName(), -1) < 2) {
            sender.sendMessage("§c你没有权限处理删除.");
        }


        BukkitReport.getInstance().getReportManager().deleteReport(rpt.getID());
        if (!"Console".equals(rpt.getReporter())) {
            /*try {
                BukkitReport.getInstance().getMessageChannel().broadcastProcessed(rpt.getPlayer(), rpt.getReporter(), (Player)this.sender);
            } catch (IOException e) {
                e.printStackTrace();
                this.sender.sendMessage("§e警告: 广播时发生数据库错误");
            }*/
        }

        this.sender.sendMessage("§a处理举报成功.");

    }
}
