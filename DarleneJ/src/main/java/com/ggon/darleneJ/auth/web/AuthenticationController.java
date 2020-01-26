package com.ggon.darleneJ.auth.web;

import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.domain.AccessToken;
import com.ggon.darleneJ.auth.domain.AuthUser;
import com.ggon.darleneJ.common.web.controller.CommonController;
import com.ggon.darleneJ.user.domain.User;

@Controller
public class AuthenticationController extends CommonController{
	
	
	@Autowired
	private AuthenticationApplicationService authService;
	
	@Autowired
	private ObjectMapper json; 
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String login(Model model) {
		if(null!= authService.getCurrentLoginUserTokenIfExists()) return "redirect:/"; //todo
		return "darleneJ/users/login";
	}
	
	private void afterloginSuccess(AuthUser user, Model model, HttpServletResponse response) throws JsonProcessingException {
		//String userJson = json.writeValueAsString(user);
		logger.debug("login 성공");
		//model.addAttribute("user", userJson);
		
		addCookiAfterloginSuccess(user,response);
	}
	
	private void addCookiAfterloginSuccess(AuthUser user, HttpServletResponse response) throws JsonProcessingException {
		String userJson = json.writeValueAsString(user);
		Cookie userCookie = new Cookie("user", userJson);
		userCookie.setMaxAge(60*60*24*30);
		response.addCookie( userCookie);
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(@RequestParam(value="loginId") String loginId, @RequestParam(value="pwd") String pwd , Model model,HttpServletResponse response) {
		try {
			AccessToken accToken = authService.login(loginId,pwd);
			if(null!=accToken)
				afterloginSuccess(accToken.getUser(),model,response);
		}catch(JsonProcessingException ex) {
			//todo
		}finally {
			//todo
		}
		
		return "darleneJ/users/userInfotest";//todo
	}
	
	@RequestMapping(value="/logout.do", method=RequestMethod.POST)
	public String logout(Model model) {
		authService.logout();
		return "/index";
	}
}
