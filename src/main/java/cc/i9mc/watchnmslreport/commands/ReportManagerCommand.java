package cc.i9mc.watchnmslreport.commands;

import cc.i9mc.watchnmslreport.BukkitReport;
import cc.i9mc.watchnmslreport.threads.ListReportThread;
import cc.i9mc.watchnmslreport.threads.ProcessReportThread;
import cc.i9mc.watchnmslreport.threads.ReportTeleportThread;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportManagerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!BukkitReport.getInstance().getStaffs().containsKey(sender.getName())) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("§c仅玩家可以执行此命令.");
            return true;
        }

        if (args.length == 0) {
            BukkitReport.getInstance().getManageGUICreator().openManageGUI((Player)sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "ok":
                if (args.length < 2) {
                    sender.sendMessage("§a/wnm ok <举报ID> 将举报标记为已处理");
                    return true;
                }

                new ProcessReportThread(sender, args[1]);
                return true;
            case "tp":
                if (args.length < 2) {
                    sender.sendMessage("§a/wnm tp <举报ID> 传送到被举报玩家");
                    return true;
                }

                new ReportTeleportThread((Player) sender, args[1]);
                return true;
            case "list":
                new ListReportThread(sender);

                new ReportTeleportThread((Player) sender, args[1]);
                return true;
        }

        return false;
    }
}
