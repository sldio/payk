package com.payk.servicepack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 1 on 22.06.2017.
 */
public class ConsoleHelper
{
    public static void print(String massege)
    {
        System.out.println(massege);
    }

    public static boolean askYesNo()
    {
        boolean rez = false;
        print("press y - to continue, n - to abort");
        String input = getConsole();
        switch (input)
        {
            case "y": rez = true;break;
            case "n": rez = false; break;
            default: print("wrong input data");
        }

        return rez;
    }
    public static boolean tryArgs(String arg)
    {
        if (arg != null)
        {
            print("begin workin with " + arg);
            return true;
        }
        else return false;
    }
    public static boolean tryURL(String url)
    {
        Pattern patern = Pattern.compile("^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$");
        Matcher matcher = patern.matcher(url);
        if (matcher.matches())
        {
            //print("URL is ok");
            return true;
        }
        else
        {
            //print("Try new URL. " + url);
            return false;
        }
    }
    public static String getConsole()
    {
        String rez = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            rez = reader.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return rez;
    }
}
