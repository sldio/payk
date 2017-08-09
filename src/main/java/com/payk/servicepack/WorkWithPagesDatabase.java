package com.payk.servicepack;

import com.payk.entety.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 1 on 02.08.2017.
 */
public class WorkWithPagesDatabase
{

    private static final Connection connection = ConnectToDatabase.getConnection();

    static synchronized void writeNewSiteBody(Page page) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO cites.pages (page_id, page_body, link_count) VALUES (?,?,?)");
        statement.setInt(1, page.getPage_id());
        statement.setString(2, page.getBody());
        statement.setInt(3, 1);

        statement.execute();
        statement.close();
    }

    public static Integer getPageLinkCount(int pageId)
    {
        Integer rez = null;
        try
        {
            PreparedStatement statement = connection.prepareStatement
                    ("select * from cites.pages where page_id = ?");
            statement.setInt(1, pageId);
            ResultSet rs = statement.executeQuery();
            rez = rs.getInt(3);
            statement.close();
            rs.close();
        }
        catch (SQLException e)
        {
            ConsoleHelper.print("error in updateSiteBody");
        }

        return rez;
    }

    public static synchronized void updateSiteBody(Page page)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement
                    ("update cites.pages set page_body = ?, link_count = (link_count + 1) where page_id = ?");
            if (page.getBody() == null)
            {
                statement.setString(1, null);
            } else
            {
                statement.setString(1, page.getBody());
            }

            statement.setInt(2, page.getPage_id());

            statement.execute();
            statement.close();
        }
        catch (SQLException e)
        {
            ConsoleHelper.print("error in updateSiteBody");
        }

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
