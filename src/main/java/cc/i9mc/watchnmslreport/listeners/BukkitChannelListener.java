/*
package cc.i9mc.watchnmslreport.listeners;

import cc.i9mc.pluginchannel.events.BukkitCommandEvent;
import cc.i9mc.watchnmslreport.messagechannel.MessageProcessor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BukkitChannelListener implements Listener {

    @EventHandler
    public void onCommand(BukkitCommandEvent event){
        if(!event.getString(0).equals("WatchNMSLReport")){
            return;
        }

        if(!event.getString(1).equals("NewReport")){
            return;
        }

        new MessageProcessor().onReceiveBroadcastReport(event.getString(2), event.getString(3), event.getString(4), event.getString(5));
    }
}
*/
