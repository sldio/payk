package com.payk.servicepack;

import java.io.IOException;
import java.sql.SQLException;

import com.payk.entety.CurrentSite;
import com.payk.entety.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by 1 on 21.06.2017.
 */
public class Parser implements Runnable
{
    private CurrentSite currentSite;
    private String url;

    public Parser()
    {

    }
    public Parser(String url)
    {
        this.url = url;
        currentSite = CurrentSite.makeNewCurrentSite(url, null);
        WorkWithSitesDatabase.writeToDatabase(currentSite);
        WorkWithSitesDatabase.changeMarkerOfURLisWorkingAndStartTimer(currentSite);
        currentSite.setStatys(1);
        getAllHrefAndSaveBody();
    }

    public static synchronized Element getBodyFromSite(String url)
    {
        Document document = null;
        try
        {
            document = Jsoup.connect(url).get();
            Element body = document.body();
            ConsoleHelper.print("get body from " + url + " done!");
        }
        catch (IOException e)
        {
            ConsoleHelper.print("error in getBodyFromSite");
        }
        catch (IllegalArgumentException er)
        {
            ConsoleHelper.print("something wrong in site " + url);
        }
        return document;
    }

    public synchronized void getAllHrefAndSaveBody()
    {
        Document document;
        Page page = null;

        String siteURL = currentSite.getSiteName();
        Integer idURL = WorkWithSitesDatabase.getSiteIdByUrl(currentSite);

            try
            {
                document = Jsoup.connect(siteURL).get();
                System.out.println("connect to " + siteURL + " is ok " + idURL);
                System.out.println(document.title());

                Elements listOfElements = document.getElementsByTag("a");
                for (Element elem : listOfElements)
                {
                    String href = elem.attr("href");

                    if (href.contains("http"))
                    {
                        CurrentSite currentSiteForWrite = CurrentSite.makeNewCurrentSite(href, idURL);
                        WorkWithSitesDatabase.writeToDatabase(currentSiteForWrite);

                        /*int siteId = WorkWithSitesDatabase.getSiteIdByUrl(currentSiteForWrite);
                        Element siteBody = Jsoup.connect(href).get().body();
                        page = new Page(siteId, siteBody.toString());

                        try
                        {
                            WorkWithPagesDatabase.writeNewSiteBody(page);
                        }
                        catch (SQLException e)
                        {
                            WorkWithPagesDatabase.updateSiteBody(page);
                        }*/
                    }
                    else
                    {
                        href = siteURL + href;
                        CurrentSite currentSiteForWrite = CurrentSite.makeNewCurrentSite(href, idURL);
                        WorkWithSitesDatabase.writeToDatabase(currentSiteForWrite);

                        /*int siteId = WorkWithSitesDatabase.getSiteIdByUrl(currentSiteForWrite);
                        Element siteBody = Jsoup.connect(href).get().body();
                        page = new Page(siteId, siteBody.toString());

                        try
                        {
                            WorkWithPagesDatabase.writeNewSiteBody(page);
                        }
                        catch (SQLException e)
                        {
                            WorkWithPagesDatabase.updateSiteBody(page);
                        }*/
                    }
                }

                WorkWithSitesDatabase.changeMarkerOfURLToCompliteAndStopTimer(currentSite);
            } catch (IOException e)
            {
                WorkWithSitesDatabase.changeMarkerOfURLtoError(currentSite);
                System.out.println("can`t connect to " + siteURL + " id " + idURL);
            }
    }

    public void makeSiteForParse()
    {
        this.currentSite = CurrentSite.getSiteFromDatabase();
        WorkWithSitesDatabase.changeMarkerOfURLisWorkingAndStartTimer(this.currentSite);
    }

    public void run()
    {
        makeSiteForParse();
        getAllHrefAndSaveBody();
    }
}
