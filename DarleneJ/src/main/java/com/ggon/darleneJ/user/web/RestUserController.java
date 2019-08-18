/**
 * 
 */
package com.ggon.darleneJ.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ggon.darleneJ.user.application.UserApplicationService;



/**
 * @FileName  : RestUserController.java
 * @Project   : DarleneJ
 * @since     : Aug 16, 2019
 * @author    : ggon
 * 
 */
@RestController
public class RestUserController {

	@Autowired
	private UserApplicationService userService;
	
	
}
