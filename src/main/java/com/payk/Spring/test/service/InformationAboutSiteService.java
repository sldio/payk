package com.payk.Spring.test.service;

import com.payk.Spring.test.entity.InformationAboutSite;

/**
 * Created by 1 on 01.08.2017.
 */
public interface InformationAboutSiteService
{
    InformationAboutSite addInformationAboutSite(InformationAboutSite informationAboutSite);
    void deleteInformationAboutSite(int siteId);
    InformationAboutSite getSiteInformationById(int siteId);
}
