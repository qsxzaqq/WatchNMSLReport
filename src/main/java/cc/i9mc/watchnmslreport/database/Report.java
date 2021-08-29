package cc.i9mc.watchnmslreport.database;

import java.util.Random;

public class Report {
    private static char[] keyword = "0123456789abcdef".toCharArray();
    private final String id;
    private final String player;
    private final String reporter;
    private final String reason;
    private final long time;
    private final String serverID;
    private final boolean isStaffReport;

    public Report(String id, String player, String reporter, String reason, long time, String serverID, boolean isStaffReport) {
        this.id = id;
        this.player = player;
        this.reporter = reporter;
        this.reason = reason;
        this.time = time;
        this.serverID = serverID;
        this.isStaffReport = isStaffReport;
    }

    public static String generateID() {
        StringBuilder builder = new StringBuilder("#");
        Random ran = new Random();

        for (int i = 0; i < 8; ++i) {
            builder.append(keyword[ran.nextInt(keyword.length)]);
        }

        return builder.toString();
    }

    public String getID() {
        return this.id;
    }

    public String getPlayer() {
        return this.player;
    }

    public String getReporter() {
        return this.reporter;
    }

    public String getReason() {
        return this.reason;
    }

    public long getTime() {
        return this.time;
    }

    public String getServerID() {
        return this.serverID;
    }
    
    public boolean isStaffReport(){
        return this.isStaffReport;
    }
}
