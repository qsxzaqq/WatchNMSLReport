package cc.i9mc.watchnmslreport.threads;

import cc.i9mc.watchnmslreport.BukkitReport;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReportThread implements Runnable {
    private static ArrayList<String> cheatReason = new ArrayList<>(Arrays.asList("killaura", "ka", "杀戮", "杀戮光环", "autoclicker", "ac", "连点", "连点器", "speed", "bhop", "timer", "加速", "fly", "飞行", "scaffold", "自动搭路"));
    private final Player reporter;
    private final OfflinePlayer player;
    private final String reason;

    public ReportThread(Player reporter, OfflinePlayer player, String reason) {
        this.reporter = reporter;
        this.player = player;
        this.reason = reason;

        Bukkit.getScheduler().runTaskAsynchronously(BukkitReport.getInstance(), this);
    }

    private static boolean isCheatReason(String reasons) {
        for (String reason : reasons.split(" ")) {
            if (cheatReason.contains(reason.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public void run() {
        if (reason.length() > 72) {
            reporter.sendMessage("§c举报原因过长, 请重新填写.");
            return;
        }

        Long time = System.currentTimeMillis();

        if (BukkitReport.getInstance().getReportManager().hasReport(player.getName(), reporter.getName(), false)) {
            reporter.sendMessage("§a您已举报过玩家 " + player.getName() + ", 请等待管理员处理...");
        } else {
            reporter.sendMessage("§a已成功举报该玩家.");

            if (!player.isOnline()) {
                reporter.sendMessage("§e该玩家不在线, 是否打错ID?");
            }

            if (isCheatReason(this.reason)) {
                //检测反作弊
            }

            BukkitReport.getInstance().getMessageChannel().broadcastReport(player.getName(), reporter, reason, time, false);
        }
    }
}
