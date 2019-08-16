/**
 * 
 */
package com.ggon.darleneJ.auth.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.domain.AccessToken;
import com.ggon.darleneJ.auth.domain.AuthUser;

/**
 * @FileName  : RestAuthenticationController.java
 * @Project   : DarleneJ
 * @since     : Aug 4, 2019
 * @author    : ggon
 * 
 */
@RestController
public class RestAuthenticationController {
	public final static String USER_ATT_NAME = "crrentUser";
	
	@Autowired
	private AuthenticationApplicationService authService;
	
	
	@GetMapping("/crrUser")
	public ResponseEntity<?> getCurrentUser() {
		if(authService.isLogined()==false) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message");
		AccessToken accToken =authService.getCurrentLoginUserTokenIfExists();
		return (accToken==null) ? null : ResponseEntity.ok(accToken.getUser());
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
		AuthUser user = null;
		ResponseEntity<?> resEntity = null;
		try {
			AccessToken accToken = authService.login((String)body.get("loginId"),(String)body.get("pwd"));
			user = accToken.getUser();
			resEntity = ResponseEntity.ok(user);
		}catch(RuntimeException ex) {
			String err = "Error Message:" + ex.getMessage();
			resEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
		}
		return resEntity;
	}
}
