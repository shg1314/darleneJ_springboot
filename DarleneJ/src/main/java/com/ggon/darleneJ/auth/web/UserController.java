package com.ggon.darleneJ.auth.web;

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
import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.domain.AccessToken;
import com.ggon.darleneJ.auth.domain.AuthUser;
import com.ggon.darleneJ.user.domain.User;

@Controller
public class UserController {
	
	public final static String USER_ATT_NAME = "crrentUser";
	
	@Autowired
	private AuthenticationApplicationService authService;
	
	@Autowired
	private ObjectMapper json; 
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String login(Model model) {
		if(null!= authService.getCurrentLoginUserTokenIfExists()) return "redirect:/"; //todo
		return "darleneJ/users/login";
	}
	
	private void afterloginSuccess(AuthUser user, Model model) throws JsonProcessingException {
		String userJson = json.writeValueAsString(user);
		model.addAttribute("user", userJson);
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(@RequestParam(value="email") String email, @RequestParam(value="pwd") String pwd , Model model) {
		try {
			AccessToken accToken = authService.login(email,pwd);
			if(null!=accToken)
				afterloginSuccess(accToken.getUser(),model);
		}catch(JsonProcessingException ex) {
			//todo
		}finally {
			//todo
		}
		
		return "darleneJ/users/userInfotest";//todo
	}
}
