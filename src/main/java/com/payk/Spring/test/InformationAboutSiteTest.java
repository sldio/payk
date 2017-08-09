package com.payk.Spring.test;

import com.payk.Spring.test.service.InformationAboutSiteService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by 1 on 01.08.2017.
 */
public class InformationAboutSiteTest
{
    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Resource
    private InformationAboutSiteService informationAboutSiteService;

    @Before(value = "true")
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void testSaveSiteInfo() throws Exception {
        informationAboutSiteService.addInformationAboutSite(SiteInfoUtil.createInfo());
    }
}
