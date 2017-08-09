package com.payk.main;

import com.payk.Spring.test.service.InformationAboutSiteService;
import com.payk.exceptions.NoDataInDatabase;
import com.payk.servicepack.*;
import com.payk.thread.ThpoolForParser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by 1 on 21.06.2017.
 */
public class Main
{
    static EntityManagerFactory emf;
    static  EntityManager em;
    static private InformationAboutSiteService informationAboutSiteService;

    public static void main(String[] args)
    {
        //ThpoolForParser thpool = null;
/*        Parser parser = new Parser("http://www.i.ua/");
        parser.run();*/
        ThpoolForParser thpool = null;

        //com.payk.servicepack.WorkWithSitesDatabase.clearTable();

        if (args.length != 0)
        {
            if (ConsoleHelper.tryURL(args[0]))
            {
                thpool = new ThpoolForParser(args[0]);
                thpool.start();
            }
            else
            {
                ConsoleHelper.print("bad url from console");
            }
        }
        else
        {
            try
            {
                WorkWithSitesDatabase.getNextSiteForWorking();
                thpool = new ThpoolForParser();
                thpool.start();
            }
            catch (NoDataInDatabase er)
            {
                ConsoleHelper.print("no task in data base");
            }
        }

        //com.payk.servicepack.WorkWithSitesDatabase.clearTable(connection);*/

        WorkWithSitesDatabase.closeConnection();
        WorkWithPagesDatabase.closeConnection();
    }
}
