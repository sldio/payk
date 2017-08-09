package com.payk.servicepack;

import com.payk.enums.InfoEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 04.08.2017.
 */
public class PrepareInfoFromDatabase
{

    private static final Connection connection = ConnectToDatabase.getConnection();

    public static void main(String ... args)
    {
        showDataFromDatabaseByCommand();
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            ConsoleHelper.print("error in info");
        }
    }
    public static int getNumberOfSites()
    {
        long start = System.currentTimeMillis();
        int rez = 0;
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT count(id) AS numberLines from cites.main");
            ResultSet rs = statement.executeQuery();
            rs.next();
            rez = rs.getInt("numberLines");

            statement.close();
            rs.close();
        }
        catch(SQLException e)
        {
            ConsoleHelper.print("error in getNumberOfSites");
        }
        System.out.println("timer = " + (System.currentTimeMillis() - start));
        return  rez;
    }
    public static int getNumberOfSitesWithErrors()
    {
        long start = System.currentTimeMillis();
        int rez = 0;
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT count(*) AS numberLines from cites.main where statys = 9");
            ResultSet rs = statement.executeQuery();
            rs.next();
            rez = rs.getInt("numberLines");

            statement.close();
            rs.close();
        }
        catch(SQLException e)
        {
            ConsoleHelper.print("error in getNumberOfSitesWithErrors");
        }
        System.out.println("timer = " + (System.currentTimeMillis() - start));
        return  rez;
    }
    public static int getAverageProcessingTime()
    {
        long start = System.currentTimeMillis();
        int rez = 0;
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT AVG(working_time) AS avarageTime FROM cites.main");
            ResultSet rs = statement.executeQuery();
            rs.next();
            rez = rs.getInt("avarageTime");

            statement.close();
            rs.close();
        }
        catch(SQLException e)
        {
            ConsoleHelper.print("error in getNumberOfSitesWithErrors");
        }
        System.out.println("timer = " + (System.currentTimeMillis() - start));
        return  rez;
    }

    public static void showAllStatys()
    {
        long start = System.currentTimeMillis();

        try
        {
            PreparedStatement statement = connection.prepareStatement("select statys, count(*) from cites.main group by statys");
            ResultSet rs = statement.executeQuery();
            ConsoleHelper.print("statys 0 - just added to database, 1 - not complit, 2 - complit, 9 - errors");
            while(rs.next())
            {
                ConsoleHelper.print("statys - " + rs.getInt(1) + " --> " + rs.getInt(2));
            }

            rs.close();
            statement.close();
        }
        catch(SQLException e)
        {
            ConsoleHelper.print("error in getNumberOfSitesWithErrors");
        }
        System.out.println("timer = " + (System.currentTimeMillis() - start));
    }

    public static void showAllPagesFromSite(String url)
    {
        try
        {
            long start = System.currentTimeMillis();
            //PreparedStatement statement = connection.prepareStatement("SELECT count(site) AS numberOfPages from cites.main where site like ?");
            String tmp = "%" + url + "%";
            //statement.setString(1, tmp);
            //ResultSet rs = statement.executeQuery();
            //rs.next();
            //ConsoleHelper.print(String.valueOf(rs.getInt("numberOfPages")));
            //ConsoleHelper.print("Print it?");

            //if (ConsoleHelper.askYesNo())
            //{
                PreparedStatement statement = connection.prepareStatement("SELECT * from cites.main where site like ?");
                statement.setString(1, tmp);
                ResultSet rs = statement.executeQuery();

                while (rs.next())
                {
                    ConsoleHelper.print("url - " + rs.getString(2));
                }
            //}

            rs.close();
            statement.close();
            System.out.println("timer = " + (System.currentTimeMillis() - start));
        }
        catch(SQLException e)
        {
            ConsoleHelper.print("error in showAllPagesFromSite");
        }
    }

    private static InfoEnum getCommand(String inputCommand)
    {
        InfoEnum rez = InfoEnum.WORKING;
            switch (inputCommand)
            {
                case "1": rez = InfoEnum.TOTAL_NUMBER_OF_PAGES; break;
                case "2": rez = InfoEnum.TOTAL_NUMBER_OF_ERRORS; break;
                case "3": rez = InfoEnum.AVEREGE_PROCESSING_TIME_FOR_PAGES; break;
                case "4": rez = InfoEnum.SHOW_ALL_STATYS;break;
                case "5": rez = InfoEnum.SHOW_PAGES_FROM_SITE;break;
                case "6": rez = InfoEnum.EXIT; break;
                default: ConsoleHelper.print("wrong command");
            }
        return rez;
    }

    private static void showDataFromDatabaseByCommand()
    {
        InfoEnum command = InfoEnum.WORKING;
        while (!InfoEnum.EXIT.equals(command))
        {
            ConsoleHelper.print("____________________________________________________________________________________________________________");
            ConsoleHelper.print("Press 1 - TOTAL_NUMBER_OF_PAGES, " +
                    "2 - TOTAL_NUMBER_OF_ERRORS, " +
                    "3 - AVEREGE_PROCESSING_TIME_FOR_PAGES, " +
                    "4 - SHOW_ALL_STATYS, " +
                    "5 - SHOW_PAGES_FROM_SITE, " +
                    "6 - EXIT");
            command = getCommand(ConsoleHelper.getConsole());
            switch (command)
            {
                case TOTAL_NUMBER_OF_PAGES:
                    ConsoleHelper.print("Number of pages in database - " + getNumberOfSites());
                    break;
                case TOTAL_NUMBER_OF_ERRORS:
                    ConsoleHelper.print("Number of pages with errors - " + getNumberOfSitesWithErrors());
                    break;
                case AVEREGE_PROCESSING_TIME_FOR_PAGES:
                    ConsoleHelper.print("Average processing time - " + getAverageProcessingTime());
                    break;
                case SHOW_ALL_STATYS:
                    showAllStatys();
                    break;
                case SHOW_PAGES_FROM_SITE:
                    ConsoleHelper.print("input site url");
                    showAllPagesFromSite(ConsoleHelper.getConsole());
                    break;
                case EXIT:
                    ConsoleHelper.print("Programm is finnishing working ...");
                    break;
            }
        }
    }
}
