package cc.i9mc.watchnmslreport.commands;

import cc.i9mc.watchnmslreport.BungeeReport;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BungeeReloadCommand extends Command {
    public BungeeReloadCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){
            return;
        }

        BungeeReport.getInstance().setStaffs(BungeeReport.getInstance().getReportManager().getAllStaffs());
    }
}
