package com.payk.thread;

import com.payk.servicepack.Parser;
import com.payk.servicepack.KeyboardMonitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 1 on 14.07.2017.
 */
public class ThpoolForParser
{
    private String url;

    public ThpoolForParser(String url)
    {
        this.url = url;
        Parser parser = new Parser(url);
    }

    public ThpoolForParser()
    {

    }


    public void start()
    {
        KeyboardMonitor monitor = new KeyboardMonitor();
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        ExecutorService parserPool = Executors.newFixedThreadPool(8);

        while (!monitor.isExitActive())
        {
            Parser parser = new Parser();
            parserPool.submit(parser);

            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        parserPool.shutdown();
        System.out.println("exit from pool");
        System.out.println(parserPool.isShutdown());
    }
}
