package com.payk.entety;

/**
 * Created by 1 on 02.08.2017.
 */
public class Page
{
    int page_id;
    String body;
    int link_count;

    public Page(int page_id, String body)
    {
        this.page_id = page_id;
        this.body = body;
    }

    public int getPage_id()
    {
        return page_id;
    }

    public void setPage_id(int page_id)
    {
        this.page_id = page_id;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public int getLink_count()
    {
        return link_count;
    }

    public void setLink_count(int link_count)
    {
        this.link_count = link_count;
    }
}
