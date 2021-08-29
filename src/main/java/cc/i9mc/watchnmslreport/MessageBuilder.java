//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cc.i9mc.watchnmslreport;

import cc.i9mc.watchnmslreport.database.Report;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.entity.Player;

public class MessageBuilder {

    public static void sendPunishSuggest(Player player, Report report) {
        String reason = report.getReason();
        TextComponent prefix = new TextComponent("§b§l-----------------------------");
        TextComponent staff_reason = new TextComponent("§b处罚原因: §c" + reason);
        TextComponent ipban = new TextComponent(TextComponent.fromLegacyText("§b志愿者 §a" + report.getReporter() + " §b对玩家 §c" + report.getPlayer() + " §b的处罚申请  "));
        TextComponent processReport = new TextComponent(TextComponent.fromLegacyText("§6『§l查看处罚记录§6』"));
        processReport.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§a点击查看处罚记录")));
        processReport.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/equery " + report.getPlayer()));
        TextComponent staff_info = new TextComponent(ipban, processReport);
        ipban = new TextComponent(TextComponent.fromLegacyText(" §c§l--> 踢出 "));
        processReport = new TextComponent(TextComponent.fromLegacyText(" §e踢出该玩家"));
        processReport.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§a玩家 §e" + report.getPlayer() + " §a将被 §c踢出游戏")));
        processReport.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/ekick " + report.getPlayer() + " " + reason));
        TextComponent template_kick = new TextComponent(ipban, processReport);
        ipban = new TextComponent(TextComponent.fromLegacyText(" §c§l--> 封禁 "));
        processReport = new TextComponent(TextComponent.fromLegacyText(" §e一天"));
        processReport.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§a玩家 §e" + report.getPlayer() + " §a将被 §c封禁 一天")));
        processReport.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/eban " + report.getPlayer() + " 1d " + reason));
        TextComponent button_2 = new TextComponent(TextComponent.fromLegacyText(" §e七天"));
        button_2.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§a玩家 §e" + report.getPlayer() + " §a将被 §c封禁 七天")));
        button_2.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/eban " + report.getPlayer() + " 7d " + reason));
        TextComponent button_3 = new TextComponent(TextComponent.fromLegacyText(" §e三十天"));
        button_3.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§a玩家 §e" + report.getPlayer() + " §a将被 §c封禁 三十天")));
        button_3.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/eban " + report.getPlayer() + " 30d " + reason));
        TextComponent button_4 = new TextComponent(TextComponent.fromLegacyText(" §e永久"));
        button_4.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§a玩家 §e" + report.getPlayer() + " §a将被 §c永久封禁")));
        button_4.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/eban " + report.getPlayer() + " " + reason));
        TextComponent template_ban = new TextComponent(ipban, processReport, button_2, button_3, button_4);
        BaseComponent suffix;
        if (report.getID().equals("#NULL")) {
            suffix = prefix.duplicate();
        } else {
            processReport = new TextComponent(TextComponent.fromLegacyText("§a『§l标记处罚已处理§a』"));
            processReport.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("§a点击标记此处罚已处理")));
            processReport.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/wnm ok " + report.getID()));
            suffix = new TextComponent(prefix, processReport);
        }

        player.spigot().sendMessage(prefix);
        player.spigot().sendMessage(staff_info);
        player.spigot().sendMessage(staff_reason);
        player.spigot().sendMessage(prefix);
        player.spigot().sendMessage(template_kick);
        player.spigot().sendMessage(template_ban);
        player.spigot().sendMessage(suffix);

    }
}
