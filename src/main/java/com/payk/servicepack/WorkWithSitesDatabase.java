package com.payk.servicepack;

import com.payk.entety.CurrentSite;
import com.payk.exceptions.NoDataInDatabase;
import org.jsoup.nodes.Element;

import java.sql.*;

/**
 * Created by 1 on 21.06.2017.
 */

public class WorkWithSitesDatabase
{

    private static final Connection connection = ConnectToDatabase.getConnection();

    static synchronized void writeToDatabase(CurrentSite currentSite)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO cites.main (site, statys, matherSite, dateOfInsert) VALUES (?,?,?,?)");
            statement.setString(1, currentSite.getSiteName());
            statement.setInt(2,currentSite.getStatys());

            if (currentSite.getMatherSite() == null)
            {
                statement.setObject(3,null);
            }
            else
            {
                statement.setInt(3, currentSite.getMatherSite());
            }
            statement.setTimestamp(4, currentSite.getTimestamp());
            statement.executeUpdate();

            statement.close();
        }
        catch (SQLException e)
        {
            //ConsoleHelper.print("this site alredy in database");
        }
    }
    public static synchronized void showDataBase(int limit)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cites.main limit ?");
            statement.setInt(1, limit);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                System.out.println(rs.getString(2));
            }

            statement.close();
        }
        catch (SQLException e)
        {
           ConsoleHelper.print("can`t show database");
        }
    }
    public static synchronized void changeMarkerOfURLToCompliteAndStopTimer(CurrentSite currentSite)
    {
        try
        {
            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "UPDATE cites.main " +
                            "SET statys = 2, " +
                            "done_dateTime = CURRENT_TIMESTAMP," +
                            "working_time = TIMESTAMPDIFF(second,start_woking_date, current_timestamp()) " +
                            "WHERE site = ?");
            statement.setString(1, currentSite.getSiteName());

            statement.executeUpdate();

            statement.close();

             /*Statement statement = connection.createStatement();
             StringBuilder request = new StringBuilder();
             request.append("UPDATE cites.main SET ");
             request.append(" statys = 2 ");
             request.append(" WHERE ");
             request.append(" site='").append(sqlEscape(url)).request.append("'");*/
        }
        catch (SQLException e)
        {
            ConsoleHelper.print("error in changeMarkerOfURLToCompliteAndStopTimer");
            changeMarkerOfURLtoError(currentSite);
        }
    }
    public static synchronized void changeMarkerOfURLtoError(CurrentSite currentSite)
    {

        try
        {
            PreparedStatement statement =
                    connection.prepareStatement("UPDATE cites.main SET statys = 9 WHERE site= ?");
            statement.setString(1, currentSite.getSiteName());

            statement.executeUpdate();

            statement.close();
        }
        catch (SQLException e)
        {
            ConsoleHelper.print("error in changeMarkerOfURLtoError");
        }
    }
    public static synchronized void changeMarkerOfURLisWorkingAndStartTimer(CurrentSite currentSite)
    {

        try
        {
            PreparedStatement statement =
                    connection.prepareStatement("" +
                            "UPDATE cites.main " +
                            "SET statys = 1, start_woking_date = current_timestamp() " +
                            "WHERE site= ?");
            statement.setString(1, currentSite.getSiteName());

            statement.executeUpdate();

            statement.close();
        }
        catch (SQLException e)
        {
            ConsoleHelper.print("error in changeMarkerOfURLisWorkingAndStartTimer");
        }
    }

    public static synchronized CurrentSite getNextSiteForWorking() throws NoDataInDatabase
    {
        CurrentSite currentSite = new CurrentSite();
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cites.main WHERE statys = 0 limit 1");
            ResultSet rs = statement.executeQuery();
            currentSite = makeSiteFromDB(rs);
            rs.close();
        }
        catch (SQLException e)
        {
            System.out.println("can`t make statement in getNextSiteForWorking");
        }

        if (currentSite == null)
        {
            try
            {
                Statement statement = connection.createStatement();
                String request = "SELECT * FROM cites.main WHERE statys = 1 limit 1";
                ResultSet rs = statement.executeQuery(request);

                currentSite = makeSiteFromDB(rs);

                rs.close();
                statement.close();
            }
            catch (SQLException e)
            {
                ConsoleHelper.print("can`t make statement in getNextSiteForWorking");
            }
        }
        return currentSite;
    }

    public static synchronized void clearTable()
    {
        try
        {
            ConsoleHelper.print("Are you sure want clear data? Press y/n");
            String answer = ConsoleHelper.getConsole();

            if (answer.equals("y"))
            {
                ConsoleHelper.print("clearing ....");
                Statement statement = connection.createStatement();
                String request = "TRUNCATE TABLE cites.main";
                statement.executeUpdate(request);
            }
            else
            {
                ConsoleHelper.print("Clering aborted");
            }
        }
        catch (SQLException e)
        {
            ConsoleHelper.print("can`t make statement in clearTable");
        }

    }

    public static synchronized int getSiteIdByUrl(CurrentSite currentSite)
    {
        int rez = 0;
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cites.main WHERE site= ?");
            statement.setString(1, currentSite.getSiteName());
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                rez = rs.getInt(1);
            }

            statement.close();
            rs.close();
        }
        catch (SQLException e)
        {
            System.out.println("can`t make statement in getSiteIdByUrl");
        }

        return rez;
    }

    public static synchronized void chengeStatysOfOldSite()
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cites.main WHERE statys = 1 LIMIT 1");
            statement.executeQuery();
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println("error in change statys");
        }
    }

    private static synchronized CurrentSite makeSiteFromDB(ResultSet rs) throws SQLException,NoDataInDatabase
    {
        CurrentSite currentSite = new CurrentSite();
        rs.next();

            currentSite.setSiteName(rs.getString(2));
            currentSite.setStatys(1); //statys working
            currentSite.setMatherSite(rs.getInt(4));
            java.util.Date date = new java.util.Date();
            currentSite.setTimestamp(rs.getTimestamp(5));
        rs.close();

        if (currentSite.getSiteName() == null)
        {
            currentSite = null;
            throw new NoDataInDatabase();
        }
        return currentSite;
    }

    public static synchronized void closeConnection()
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

