package com.payk.servicepack;

import java.util.Scanner;

/**
 * Created by 1 on 14.07.2017.
 */
public class KeyboardMonitor implements Runnable
{
    private static boolean needExit = false;

    public void run()
    {
        System.out.println("for exiting press y");

        do
        {
            Scanner scanner = new Scanner(System.in);
            String inputFromKeyboard = scanner.next();

            if (inputFromKeyboard.equals("y"))
            {
                needExit = true;
            }
        }
        while(!needExit);
    }

    public boolean isExitActive()
    {
        return needExit;
    }
}
