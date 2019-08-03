package com.ggon.darleneJ.user.web;

import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

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
import com.ggon.darleneJ.user.application.AuthenticationApplicationService;
import com.ggon.darleneJ.user.domain.User;

@Controller
public class UserController {
	
	public final static String USER_ATT_NAME = "crrentUser";
	
	@Autowired
	private AuthenticationApplicationService userService;
	
	@Autowired
	private ObjectMapper json; 
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String login(Model model) throws Exception{
		//User user = userService.login(email,pwd);
		//model.addAttribute("user", user);
		return "darleneJ/users/login";
	}
	
	private void afterloginSuccess(User user, Model model) throws JsonProcessingException {
		String userJson = json.writeValueAsString(user);
		model.addAttribute("user", userJson);
	}
	
	@RequestMapping(value="/users/login.do", method=RequestMethod.POST)
	public String login(@RequestParam(value="email") String email, @RequestParam(value="pwd") String pwd , Model model) throws Exception{
		if(null== userService.getCurrentUserIfExists()) {
			User user = userService.login(email,pwd);
			if(null!=user)
				afterloginSuccess(user,model);
		}
		
		return "darleneJ/users/userInfotest";//todo
	}
}
