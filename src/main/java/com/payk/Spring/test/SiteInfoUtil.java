package com.payk.Spring.test;

import com.payk.Spring.test.entity.InformationAboutSite;

/**
 * Created by 1 on 01.08.2017.
 */
public class SiteInfoUtil
{
    public static InformationAboutSite createInfo()
    {
        InformationAboutSite informationAboutSite = new InformationAboutSite();
        informationAboutSite.setId(1);
        informationAboutSite.setSiteBody("sdfsvdfgsdfv");
        informationAboutSite.setLinkCount(1);

        return informationAboutSite;
    }
}
