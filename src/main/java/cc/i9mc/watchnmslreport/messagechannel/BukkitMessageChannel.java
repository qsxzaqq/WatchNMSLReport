package cc.i9mc.watchnmslreport.messagechannel;

import cc.i9mc.pluginchannel.BukkitChannel;
import cc.i9mc.pluginchannel.bukkit.PBukkitChannelTask;
import cc.i9mc.watchnmslreport.BukkitReport;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

public class BukkitMessageChannel {
    public void broadcastReport(String player, Player reporter, String reason, Long time, boolean staff) {
        //被举报人，举报人，理由，时间
        PBukkitChannelTask.createTask()
                .channel(BukkitChannel.getInst().getBukkitChannel())
                .command("WatchNMSLReport", "report", player, reporter.getName(), reason, String.valueOf(time), String.valueOf(staff))
                .sender(reporter)
                .run();
    }

    public void jumpServer(Player player, String serverID) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(serverID);
            player.sendPluginMessage(BukkitReport.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception ignored) {
        }
    }
}
