package com.payk.Spring.test.impl;

import com.payk.Spring.test.entity.InformationAboutSite;
import com.payk.Spring.test.repository.InformationAboutSiteRespository;
import com.payk.Spring.test.service.InformationAboutSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 1 on 01.08.2017.
 */

@Service
public class InformationAboutSiteImpl implements InformationAboutSiteService
{

    @Autowired
    InformationAboutSiteRespository siteInformationRepository;

    public InformationAboutSite addInformationAboutSite(InformationAboutSite informationAboutSite)
    {
        InformationAboutSite siteInformation = siteInformationRepository.saveAndFlush(informationAboutSite);
        return siteInformation;
    }

    public void deleteInformationAboutSite(int siteId)
    {
        siteInformationRepository.delete(siteId);
    }

    public InformationAboutSite getSiteInformationById(int siteId)
    {
        InformationAboutSite informationAboutSite = siteInformationRepository.findOne(siteId);
        return informationAboutSite;
    }
}
