/**
 * 
 */
package com.ggon.darleneJ.common.web.session;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ggon.darleneJ.common.application.AlreadyExistsException;
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
	public void add(String name, Object obj) {
		Object o = session.getAttribute(name);
		if(null != o) throw new AlreadyExistsException("alread exists key(" + name + ")");
		if(null == obj) new IllegalArgumentException("obj is null");
		session.setAttribute(name, obj);
	}

	@Override
	public Object getIfExists(String name) {
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		return session.getAttribute(name);
	}

	@Override
	public void removeIfExists(String name) {
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		session.setAttribute(name, null);
	}
	
	@Override
	public void addIfExistsReplace(String name, Object obj) {
		if(null == name || name.isEmpty() == true) new IllegalArgumentException("name is null or empty");
		if(null == obj) new IllegalArgumentException("obj is null");
		session.setAttribute(name, obj);
	}
}
