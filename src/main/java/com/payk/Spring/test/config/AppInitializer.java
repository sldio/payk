package com.payk.Spring.test.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by 1 on 01.08.2017.
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[]{
                DataConfig.class
        };
    }

    protected Class<?>[] getServletConfigClasses()
    {
        return new Class<?>[0];
    }

    protected String[] getServletMappings()
    {
        return new String[0];
    }
}
