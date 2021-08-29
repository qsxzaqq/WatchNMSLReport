package cc.i9mc.watchnmslreport.threads;

import cc.i9mc.watchnmslreport.BukkitReport;
import cc.i9mc.watchnmslreport.MessageBuilder;
import cc.i9mc.watchnmslreport.database.Report;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReportTeleportThread implements Runnable {
    private final Player player;
    private final String id;

    public ReportTeleportThread(Player sender, String id) {
        player = sender;
        this.id = id;
        Bukkit.getScheduler().runTaskAsynchronously(BukkitReport.getInstance(), this);
    }

    public void run() {
            Report rpt = BukkitReport.getInstance().getReportManager().getReport("#" + id);
            if (rpt == null) {
                player.sendMessage("§c举报不存在");
                return;
            }

            if (rpt.isStaffReport()) {
                if (BukkitReport.getInstance().getStaffs().getOrDefault(player.getName(), -1) < 2) {
                    player.sendMessage("§c你没有权限处理处罚申请.");
                    return;
                }

                MessageBuilder.sendPunishSuggest(player, rpt);
                return;
            }
            
            String server = rpt.getServerID();
            if (server == null) {
                player.sendMessage("§c被举报玩家 §6" + rpt.getPlayer() + " §c不在线.");
                player.closeInventory();
                return;
            }

            BukkitReport.getInstance().getMessageChannel().jumpServer(player, server);
            player.sendMessage("§a正在将您传送到被举报玩家所在的 §6" + server + " §a服务器.");
            player.closeInventory();

    }
}
