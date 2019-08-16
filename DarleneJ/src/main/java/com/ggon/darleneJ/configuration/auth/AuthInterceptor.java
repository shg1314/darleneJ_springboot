/**
 * 
 */
package com.ggon.darleneJ.configuration.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @FileName  : LoginInterceptor.java
 * @Project   : DarleneJ
 * @since     : Aug 3, 2019
 * @author    : ggon
 * 
 */
public class AuthInterceptor extends HandlerInterceptorAdapter  {
	public static final String REDIRECT_URL_ATT_NAME = "destination";
	
    private void saveDestination(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        if (query == null || query.equals("null")) {
            query = "";
        } else {
            query = "?" + query;
        }

        if (request.getMethod().equals("GET")) {
            request.getSession().setAttribute(REDIRECT_URL_ATT_NAME, uri + query);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        saveDestination(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {



    }
}
