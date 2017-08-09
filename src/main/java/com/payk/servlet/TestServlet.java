package com.payk.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 1 on 07.08.2017.
 */

@WebServlet("/start")
public class TestServlet extends HttpServlet
{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String myRequest = request.getParameter("parser");
        PrintWriter printWriter = response.getWriter();
        if (myRequest.equals("Start"))
            {
                printWriter.print("you want to start scaning");
            }
            if (myRequest.equals("Stop"))
            {
                printWriter.print("you wont nothing");
            }

            printWriter.close();
    }
}
