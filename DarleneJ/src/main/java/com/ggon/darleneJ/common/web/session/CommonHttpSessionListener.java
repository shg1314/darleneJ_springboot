/**
 * 
 */
package com.ggon.darleneJ.common.web.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * @FileName  : CommonHttpSessionListener.java
 * @Project   : DarleneJ
 * @since     : Aug 7, 2019
 * @author    : ggon
 * 
 */
@WebListener
public class CommonHttpSessionListener implements HttpSessionListener{


    @Override
    public void sessionCreated(HttpSessionEvent se) {
    	se.getSession().setMaxInactiveInterval(5*60);
    	//todo 
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	//todo
    }
}
