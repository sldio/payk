package com.payk.Spring.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 1 on 01.08.2017.
 */

@Entity
@Table(name = "information_about_site")
public class InformationAboutSite
{
    @Id
    @Column(name = "id_from_main_database", nullable = false)
    private int id;

    @Column(name = "site_body")
    private String siteBody;

    @Column(name = "link_count")
    private int linkCount;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getSiteBody()
    {
        return siteBody;
    }

    public void setSiteBody(String siteBody)
    {
        this.siteBody = siteBody;
    }

    public int getLinkCount()
    {
        return linkCount;
    }

    public void setLinkCount(int linkCount)
    {
        this.linkCount = linkCount;
    }
}
