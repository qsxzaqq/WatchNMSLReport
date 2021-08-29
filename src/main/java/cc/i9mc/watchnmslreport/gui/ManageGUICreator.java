package cc.i9mc.watchnmslreport.gui;

import cc.i9mc.watchnmslreport.BukkitReport;
import cc.i9mc.watchnmslreport.database.Report;
import cc.i9mc.watchnmslreport.threads.ManageGUIThread;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class ManageGUICreator {
    public void openManageGUI(Player player) {
        this.openManageGUI(player, 1);
    }

    public void openManageGUI(Player player, int page) {
        new ManageGUIThread(this, player, page);
    }

    public ItemStack getPrePageItem(int page) {
        ItemStack item = new ItemStack(Material.GLOWSTONE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§b§l<< 上一页 " + (page - 1));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getNextPageItem(int page) {
        ItemStack item = new ItemStack(Material.GLOWSTONE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§b§l" + (page + 1) + " 下一页 >>");
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getPageItem(int page) {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§b§l第 " + page + " 页");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, page, true);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getReportItem(Report rpt) {
        if(rpt.isStaffReport()){
            ItemStack item = (new Wool(DyeColor.LIGHT_BLUE)).toItemStack(1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6§l举报 " + rpt.getID());
            LinkedList<String> lore = new LinkedList<>();
            lore.add("");
            lore.add("§b待处理玩家: §c" + rpt.getPlayer());
            lore.add("§b提交志愿者: §e" + rpt.getReporter());
            lore.add("§b原因: §e" + rpt.getReason());
            lore.add("§b所在服务器: §e" + rpt.getServerID());
            lore.add("§b举报时间: §e" + this.formatTime(rpt.getTime()));
            lore.add("");
            lore.add("§b这是一条志愿者提交的处理申请");
            lore.add("");
            lore.add("§a左键处罚 §b§l| §d右键清除");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }

        ItemStack item = new Wool(DyeColor.YELLOW).toItemStack(1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§l举报 " + rpt.getID());
        LinkedList<String> lore = new LinkedList<>();
        lore.add("");
        lore.add("§b被举报玩家: §c" + rpt.getPlayer());
        lore.add("§b举报者: §e" + rpt.getReporter());
        lore.add("§b原因: §e" + rpt.getReason());
        lore.add("§b所在服务器: §e" + rpt.getServerID());
        lore.add("§b举报时间: §e" + this.formatTime(rpt.getTime()));
        lore.add("");
        lore.add("§a左键传送 §b§l| §d右键处理");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public int getPage(Inventory inv) {
        return inv.getItem(49) != null && inv.getItem(49).getType() == Material.PAPER ? inv.getItem(49).getItemMeta().getEnchantLevel(Enchantment.DURABILITY) : 1;
    }

    private String formatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }
}
