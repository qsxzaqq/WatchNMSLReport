package cc.i9mc.watchnmslreport.messagechannel;

public class MessageProcessor {
   /* public void onReceiveBroadcastReport(String cheater, String reporter, String reason, String server) {
        String[] message = new String[]{"§e------------------------------", "§e玩家 §a" + reporter + " §e在服务器 §d" + server + " §e举报了玩家 §c" + cheater, "§e举报原因:  §a" + reason, "§e------------------------------"};

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("wreport.wpt")) {
                player.sendMessage(message);
            }
        }

    }

    public void onReceiveBroadcastProcessed(String cheater, String reporter, String admin) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(reporter);
        if (offlinePlayer != null) {
            if (offlinePlayer.isOnline()) {
                TitleUtil.sendTitle(offlinePlayer.getPlayer(), 10, 20, 10, "", "§a你对玩家 §c" + cheater + " §a的举报已被 §b" + admin + " §a处理");
                offlinePlayer.getPlayer().sendMessage("§a你对玩家 §c" + cheater + " §a的举报已被 §b" + admin + " §a处理");
            }

        }
    }*/
}
