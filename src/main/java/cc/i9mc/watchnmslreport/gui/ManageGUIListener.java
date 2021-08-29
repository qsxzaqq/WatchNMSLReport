package cc.i9mc.watchnmslreport.gui;

import cc.i9mc.watchnmslreport.BukkitReport;

import cc.i9mc.watchnmslreport.MessageBuilder;
import cc.i9mc.watchnmslreport.database.Report;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;

public class ManageGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName() != null) {
            if (e.getInventory().getName().startsWith("§6§l举报箱")) {
                e.setCancelled(true);
                Player clicker = (Player) e.getView().getPlayer();
                if (e.getCurrentItem() == null) {
                    return;
                }

                if (e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }

                if (e.getCurrentItem().getType() == Material.WOOL) {
                    String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
                    if (itemName == null) {
                        return;
                    }

                    String id = itemName.substring(7);

                    Report rpt;
                    rpt = BukkitReport.getInstance().getReportManager().getReport(id);

                    if (rpt == null) {
                        clicker.sendMessage("§c举报 §6" + id + " §c不存在, 是否已被其他人处理?");
                        clicker.closeInventory();
                        return;
                    }

                    if (e.getAction() == InventoryAction.PICKUP_ALL) {
                        if (rpt.isStaffReport()) {
                            if (BukkitReport.getInstance().getStaffs().getOrDefault(clicker.getName(), -1) == 2) {
                                MessageBuilder.sendPunishSuggest(clicker, rpt);
                            } else {
                                clicker.sendMessage("§c你没有权限处理处罚申请.");
                            }

                            clicker.closeInventory();
                            return;
                        }


                        BukkitReport.getInstance().getMessageChannel().jumpServer(clicker, rpt.getServerID());
                        clicker.sendMessage("§a正在将您传送到被举报玩家所在的 §6" + rpt.getServerID() + " §a服务器.");
                        clicker.closeInventory();
                        return;
                    }

                    if (e.getAction() == InventoryAction.PICKUP_HALF) {

                        //BukkitReport.getInstance().getMessageChannel().broadcastProcessed(rpt.getPlayer(), rpt.getReporter(), clicker);

                        if (BukkitReport.getInstance().getStaffs().getOrDefault(clicker.getName(), -1) == 2) {
                            BukkitReport.getInstance().getReportManager().deleteReport(id);
                            BukkitReport.getInstance().getManageGUICreator().openManageGUI(clicker, BukkitReport.getInstance().getManageGUICreator().getPage(e.getInventory()));
                            clicker.sendMessage("§a举报 §6" + id + " §a已处理.");
                        } else {
                            clicker.sendMessage("§c你没有权限处理删除.");
                        }
                    }
                } else if (e.getCurrentItem().getType() == Material.GLOWSTONE) {
                    int page = e.getInventory().getItem(49).getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
                    if (e.getSlot() == 46) {
                        BukkitReport.getInstance().getManageGUICreator().openManageGUI(clicker, page - 1);
                    } else if (e.getSlot() == 52) {
                        BukkitReport.getInstance().getManageGUICreator().openManageGUI(clicker, page + 1);
                    }
                }

            }
        }
    }
}
