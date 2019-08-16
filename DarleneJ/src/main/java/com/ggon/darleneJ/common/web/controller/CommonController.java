/**
 * 
 */
package com.ggon.darleneJ.common.web.controller;



import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggon.darleneJ.common.application.IApplicationSessionService;
import com.ggon.darleneJ.configuration.auth.AuthInterceptor;

/**
 * @FileName  : CommonController.java
 * @Project   : DarleneJ
 * @since     : Aug 8, 2019
 * @author    : ggon
 * 
 */
public class CommonController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass().toString());
	
	@Autowired
	protected  IApplicationSessionService sessionService;
	
	@Autowired
	private ObjectMapper json; 
	
	public String getPreviousPage() {
		return (String)sessionService.getIfExists(AuthInterceptor.REDIRECT_URL_ATT_NAME);
	}
}
