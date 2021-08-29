package cc.i9mc.watchnmslreport.commands;

import cc.i9mc.watchnmslreport.BukkitReport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            return true;
        }

        BukkitReport.getInstance().setStaffs(BukkitReport.getInstance().getReportManager().getAllStaffs());

        return false;
    }
}
