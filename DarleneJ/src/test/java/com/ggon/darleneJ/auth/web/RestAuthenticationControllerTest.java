/**
 * 
 */
package com.ggon.darleneJ.auth.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.domain.AccessToken;
import com.ggon.darleneJ.auth.domain.AuthUser;
import com.ggon.darleneJ.common.application.IApplicationSessionService;
import com.ggon.darleneJ.common.web.controller.CommonController;
import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.domain.UserRoleType;

import junit.framework.Assert;

/**
 * @FileName  : AuthenticationControllerTest.java
 * @Project   : DarleneJ
 * @since     : Aug 8, 2019
 * @author    : ggon
 * 
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RestAuthenticationController.class)
//@ComponentScan({"com.ggon.darleneJ.auth.application","com"})
//@EnableAutoConfiguration(exclude = {IApplicationSessionService.class})
//@WebAppConfiguration
public class RestAuthenticationControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	AuthenticationApplicationService authService;
	
	@Autowired
	ObjectMapper json;
	
	@Test
	public void loginWithValideEmailAndPWDThenGetUser() throws Exception{
		
		String email = "test@test.com";
		String pwd = "test";
		HashMap<String,String> map = new HashMap<String,String>();
		
		
		User user = User.loadUser(1,email,"test",UserRoleType.CUSTOMER,LocalDateTime.now(),LocalDateTime.now());
		AuthUser aUser = AuthUser.authUser(user);
		AccessToken token = AccessToken.newToken(aUser);
		
		when(authService.login(email, pwd)).thenReturn(token);
		
		
		map.put("loginId", email);
		map.put("pwd", pwd);
		
		String loginJson = json.writeValueAsString(map);
		
		
		MockHttpServletResponse response = (MockHttpServletResponse) mvc.perform(
				post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(loginJson)).andReturn().getResponse();
		
		String tmp =response.getContentAsString();
		int s =response.getStatus();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				json.writeValueAsString(aUser));
		
		
	}
}

