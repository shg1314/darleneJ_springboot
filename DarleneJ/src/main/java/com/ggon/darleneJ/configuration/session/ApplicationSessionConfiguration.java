/**
 * 
 */
package com.ggon.darleneJ.configuration.session;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ggon.darleneJ.common.web.session.CommonHttpSessionListener;

/**
 * @FileName  : ApplicationSessionConfiguration.java
 * @Project   : DarleneJ
 * @since     : Aug 7, 2019
 * @author    : ggon
 * 
 */


 
import javax.servlet.http.HttpSessionListener;
 
@Configuration
public class ApplicationSessionConfiguration
{
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener()
    {
        return new ServletListenerRegistrationBean<HttpSessionListener>(new CommonHttpSessionListener());
    }
} 