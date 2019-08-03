/**
 * 
 */
package com.ggon.darleneJ.configuration.session;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ggon.darleneJ.common.application.IApplicationSessionService;

/**
 * @FileName  : HttpSessionService.java
 * @Project   : DarleneJ
 * @since     : Aug 4, 2019
 * @author    : ggon
 * 
 */
@Configuration
public class HttpSessionService implements IApplicationSessionService{
	@Autowired
	HttpSession session;
	
	@Override
	public void add(String name, Object obj) throws IllegalArgumentException{
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		if(null == obj) new IllegalArgumentException("obj is null");
		session.setAttribute(name, obj);
	}

	@Override
	public Object getIfExists(String name) throws IllegalArgumentException{
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		return session.getAttribute(name);
	}

	@Override
	public void removeIfExists(String name) throws IllegalArgumentException {
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		session.setAttribute(name, null);
	}
}
