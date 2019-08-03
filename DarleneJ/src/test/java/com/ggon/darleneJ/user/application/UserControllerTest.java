/**
 * 
 */
package com.ggon.darleneJ.user.application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @FileName  : UserControllerTest.java
 * @Project   : DarleneJ
 * @since     : Aug 2, 2019
 * @author    : ggon
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	//@Autowired
	//private MockMvc mvc;
	
	//@Autowired
	//UserController userCtrl;
	
	@Test
	public void loginWithValideEmailAndPWDThenGetUser() throws Exception{
		final String email = "test@test.com";
		final String pwd = "test";
		
/*
		mvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email",email)
				.param("pwd", pwd)
				).andExpect(jsonPath("$.user.name").value("test"));
				*/
	}
	
}
