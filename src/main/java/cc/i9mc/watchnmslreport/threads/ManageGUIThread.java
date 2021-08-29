package cc.i9mc.watchnmslreport.threads;

import cc.i9mc.watchnmslreport.BukkitReport;
import cc.i9mc.watchnmslreport.gui.ManageGUICreator;
import cc.i9mc.watchnmslreport.database.Report;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.SQLException;
import java.util.LinkedList;

public class ManageGUIThread implements Runnable {
    private final ManageGUICreator creator;
    private final Player player;
    private int page;

    public ManageGUIThread(ManageGUICreator creator, Player player, int page) {
        this.creator = creator;
        this.player = player;
        this.page = page;

        Bukkit.getScheduler().runTaskAsynchronously(BukkitReport.getInstance(), this);
    }

    public void run() {
        LinkedList<Report> reports = BukkitReport.getInstance().getReportManager().getAllReports();
        boolean update = true;
        if (reports.size() <= 54) {
            this.page = 1;
        }

        Inventory mainGUI = Bukkit.createInventory(null, 54, "§6§l举报箱 §a第 " + this.page + " 页");
        if (this.player.getOpenInventory() != null && this.player.getOpenInventory().getTopInventory() != null && this.player.getOpenInventory().getTopInventory().getName() != null && this.player.getOpenInventory().getTopInventory().getName().startsWith("§6§l举报箱")) {
            Inventory current = this.player.getOpenInventory().getTopInventory();
            if (this.creator.getPage(current) == this.page) {
                current.clear();
                mainGUI = current;
                update = false;
            }
        }

        if (reports.size() <= 54) {
            for (Report rpt : reports) {
                mainGUI.addItem(this.creator.getReportItem(rpt));
            }
        } else {
            int start = 45 * (this.page - 1);
            int size = reports.size() - 1;
            boolean nextPage = start + 45 <= size;
            int i = start;

            for(int max = start + 45; i < max && i <= size; ++i) {
                mainGUI.addItem(this.creator.getReportItem(reports.get(i)));
            }

            if (this.page > 1) {
                mainGUI.setItem(46, this.creator.getPrePageItem(this.page));
            }

            mainGUI.setItem(49, this.creator.getPageItem(this.page));
            if (nextPage) {
                mainGUI.setItem(52, this.creator.getNextPageItem(this.page));
            }
        }

        if (update) {
            this.player.closeInventory();
            this.player.openInventory(mainGUI);
        }

    }
}
