package cc.i9mc.watchnmslreport.messagechannel;

import cc.i9mc.gameutils.BungeeGameUtils;
import cc.i9mc.gameutils.event.bungee.BungeePubSubMessageEvent;
import cc.i9mc.gameutils.utils.JedisUtil;
import cc.i9mc.pluginchannel.events.BungeeCommandEvent;
import cc.i9mc.watchnmslreport.BungeeReport;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeMessageChannel implements Listener {
    @EventHandler
    public void onPubSub(BungeePubSubMessageEvent event){
        if(!event.getChannel().equals("Report")){
            return;
        }

        String[] strings = event.getMessage().split("§");

        if(strings.length == 5 && strings[0].equals("NewReport")){
            for(ProxiedPlayer player : BungeeGameUtils.getInstance().getProxy().getPlayers()){
                if(BungeeReport.getInstance().getStaffs().containsKey(player.getName())){
                    player.sendMessage(new TextComponent("§e------------------------------"));
                    player.sendMessage(new TextComponent("§e玩家 §a" + strings[2] + " §e在服务器 §d" + strings[4] + " §e举报了玩家 §c" + strings[1]));
                    player.sendMessage(new TextComponent("§e举报原因:  §a" + strings[3]));
                    player.sendMessage(new TextComponent("§e------------------------------"));
                }
            }

            /*for(ServerInfo serverInfo : BungeeGameUtils.getInstance().getProxy().getServers().values()){
                if(!serverInfo.getPlayers().isEmpty()){
                    PBungeeChannelTask.createTask()
                            .channel(BungeeChannel.getInstance().getBungeeChannel())
                            .target(serverInfo.getPlayers().iterator().next())
                            //作弊者 举报者 原因 子服
                            .command("WatchNMSLReport", "NewReport", , , , )
                            .run();
                }
            }*/
        }
    }

    @EventHandler
    public void onCommand(BungeeCommandEvent e) {
        if (e.getString(0).equalsIgnoreCase("WatchNMSLReport")) {
            if (e.getString(1).equalsIgnoreCase("report")) {
                ProxiedPlayer reporter = BungeeReport.getInstance().getProxy().getPlayer(e.getString(3));
                if (reporter == null) {
                    return;
                }

                String player = e.getString(2);
                String reason = e.getString(4);
                Long time = Long.valueOf(e.getString(5));

                BungeeReport.getInstance().getReportManager().addReport(player, reporter.getName(), reason, time, reporter.getServer().getInfo().getName(), Boolean.parseBoolean(e.getString(6)));
                JedisUtil.publish("Report", "NewReport§" + player + "§" + reporter.getName() + "§" + reason + "§" + reporter.getServer().getInfo().getName());
                BungeeReport.getNioClient().writeAndFlush(reporter.getName() + "§" + reporter.getServer().getInfo().getName() + "§" + player + "§" + reason + "§NewReport");
            }
        }
    }
}
