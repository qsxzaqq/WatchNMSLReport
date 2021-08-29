package cc.i9mc.watchnmslreport.threads;

import cc.i9mc.watchnmslreport.BukkitReport;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class StaffReportThread implements Runnable {
    private final Player reporter;
    private final OfflinePlayer player;
    private final String reason;

    public StaffReportThread(Player reporter, OfflinePlayer player, String reason) {
        this.reporter = reporter;
        this.player = player;
        this.reason = reason;

        Bukkit.getScheduler().runTaskAsynchronously(BukkitReport.getInstance(), this);
    }

    public void run() {
        if (reason.length() > 72) {
            reporter.sendMessage("§c举报原因过长, 请重新填写.");
            return;
        }

        Long time = System.currentTimeMillis();

        if (BukkitReport.getInstance().getReportManager().hasReport(player.getName(), reporter.getName(), true)) {
            reporter.sendMessage("§a您已举报过玩家 " + player.getName() + ", 请等待管理员处理...");
        } else {
            reporter.sendMessage("§a已成功举报该玩家.");

            if (!player.isOnline()) {
                reporter.sendMessage("§e该玩家不在线, 是否打错ID?");
            }

            BukkitReport.getInstance().getMessageChannel().broadcastReport(player.getName(), reporter, reason, time, true);
        }
    }
}
