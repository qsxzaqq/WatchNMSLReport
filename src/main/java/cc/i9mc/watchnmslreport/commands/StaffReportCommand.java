package cc.i9mc.watchnmslreport.commands;

import cc.i9mc.watchnmslreport.BukkitReport;
import cc.i9mc.watchnmslreport.threads.StaffReportThread;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c仅玩家可以执行此命令.");
            return true;
        }

        Player player = (Player) sender;

        if (!BukkitReport.getInstance().getStaffs().containsKey(sender.getName())) {
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§7/staff <玩家> <原因>   §b向管理员提交对玩家的处罚申请");
            return true;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        if (offlinePlayer == null) {
            player.sendMessage("§c该玩家不存在.");
            return true;
        }

        String reason;
        if (args.length <= 1) {
            player.sendMessage("§c请提供处罚原因.");
        } else {
            StringBuilder builder = new StringBuilder();

            for (int i = 1; i < args.length; ++i) {
                builder.append(args[i]);
                builder.append(' ');
            }

            reason = builder.toString();
            new StaffReportThread(player, offlinePlayer, reason);
        }
        return false;
    }
}
