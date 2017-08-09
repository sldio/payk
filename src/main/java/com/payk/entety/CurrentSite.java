package com.payk.entety;

import com.payk.exceptions.NoDataInDatabase;
import com.payk.servicepack.ConsoleHelper;
import com.payk.servicepack.WorkWithSitesDatabase;

import java.sql.Timestamp;

/**
 * Created by 1 on 21.06.2017.
 */
public class CurrentSite
{
    private String siteName;
    private int statys;
    private Integer matherSite;
    private Timestamp date;

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName(String citeName)
    {
        this.siteName = citeName;
    }

    public int getStatys()
    {
        return statys;
    }

    public void setStatys(int statys)
    {
        this.statys = statys;
    }

    public Integer getMatherSite()
    {
        return matherSite;
    }

    public void setMatherSite(Integer matherSite)
    {
        this.matherSite = matherSite;
    }

    public Timestamp getTimestamp()
    {
        return date;
    }

    public void setTimestamp(Timestamp date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        String rez = null;
        if (date == null)
        {
            rez = siteName + " " + statys + " " + " " + matherSite;
        }
        else
        {
            rez = siteName + " " + statys + " " + " " + matherSite + " " + date.toString();
        }
        return rez;
    }

    public static CurrentSite makeNewCurrentSite(String url, Integer idURL)
    {
        CurrentSite currentCite = new CurrentSite();
        currentCite.setSiteName(url);
        currentCite.setStatys(0);
        currentCite.setMatherSite(idURL);

        Timestamp date = new Timestamp(System.currentTimeMillis());
        currentCite.setTimestamp(date);

        return currentCite;
    }

    public static CurrentSite getSiteFromDatabase()
    {
        CurrentSite currentSite = null;
        try
        {
            currentSite = WorkWithSitesDatabase.getNextSiteForWorking();
        }
        catch (NoDataInDatabase e)
        {
            ConsoleHelper.print("no data in database");
        }

        return currentSite;
    }
}
