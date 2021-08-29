package cc.i9mc.watchnmslreport.database;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.gameutils.BungeeGameUtils;
import cc.i9mc.gameutils.enums.ServerType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class ReportManager {
    public boolean hasReport(String player, String reporter, boolean staff) {
        boolean reason = false;

        try {
            Connection connection = ServerType.getServerType() == ServerType.BUNGEECORD ? BungeeGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee") : BukkitGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `reportID` FROM `wnr_report` WHERE `player` = ? AND `reporter` = ? LIMIT 1");
            preparedStatement.setString(1, player);
            preparedStatement.setString(2, reporter);

            ResultSet resultSet = preparedStatement.executeQuery();

            reason = resultSet.next();

            if(staff){
                if(reason){
                    reason = resultSet.getBoolean("staff");
                }
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reason;
    }

    public void addReport(String player, String reporter, String reason, Long time, String serverId, boolean staff) {
        try {
            Connection connection = ServerType.getServerType() == ServerType.BUNGEECORD ? BungeeGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee") : BukkitGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `wnr_report` VALUES(null, ?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, Report.generateID());
            preparedStatement.setString(2, player);
            preparedStatement.setString(3, reporter);
            preparedStatement.setString(4, reason);
            preparedStatement.setLong(5, time);
            preparedStatement.setString(6, serverId);
            preparedStatement.setBoolean(7, staff);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getAllStaffs() {
        HashMap<String, Integer> results = new HashMap<>();

        try {
            Connection connection = ServerType.getServerType() == ServerType.BUNGEECORD ? BungeeGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee") : BukkitGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `wnr_permission`;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                results.put(resultSet.getString("name"), resultSet.getInt("level"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public LinkedList<Report> getAllReports() {
        LinkedList<Report> results = new LinkedList<>();

        try {
            Connection connection = ServerType.getServerType() == ServerType.BUNGEECORD ? BungeeGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee") : BukkitGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `wnr_report`;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                results.add(new Report(resultSet.getString("reportID"), resultSet.getString("player"), resultSet.getString("reporter"), resultSet.getString("reason"), resultSet.getLong("reportTime"), resultSet.getString("serverID"), resultSet.getBoolean("staff")));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public Report getReport(String id) {
        Report report = null;


        try {
            Connection connection = ServerType.getServerType() == ServerType.BUNGEECORD ? BungeeGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee") : BukkitGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `wnr_report` WHERE `reportID` = ? LIMIT 1;");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                report = new Report(resultSet.getString("reportID"), resultSet.getString("player"), resultSet.getString("reporter"), resultSet.getString("reason"), resultSet.getLong("reportTime"), resultSet.getString("serverID"), resultSet.getBoolean("staff"));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

    public void deleteReport(final String id) {
        try {
            Connection connection = ServerType.getServerType() == ServerType.BUNGEECORD ? BungeeGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee") : BukkitGameUtils.getInstance().getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `wnr_report` WHERE `reportID` = ?");
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
