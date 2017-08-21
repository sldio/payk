package com.payk.Spring.test.repository;

import com.payk.Spring.test.entity.InformationAboutSite;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 1 on 01.08.2017.
 */


public interface InformationAboutSiteRespository extends JpaRepository<InformationAboutSite, Integer>
{

}
