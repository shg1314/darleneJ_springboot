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
	private static final String CURRENT_USER_ATT_NAME = "currentUser";
	private static final String REDIRECT_URL_ATT_NAME = "destination";
	
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

        HttpSession httpSession = request.getSession();

        if (httpSession.getAttribute(CURRENT_USER_ATT_NAME) == null) {
            saveDestination(request);
            response.sendRedirect("/users/login");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HttpSession httpSession = request.getSession();
        ModelMap modelMap = modelAndView.getModelMap();
        Object user =  modelMap.get("user");

        if (user != null) {
            httpSession.setAttribute(CURRENT_USER_ATT_NAME, user);

            Object destination = httpSession.getAttribute(REDIRECT_URL_ATT_NAME);
            response.sendRedirect(destination != null ? (String) destination : "/");
        }
    }
}
