/**
 * 
 */
package com.ggon.darleneJ.configuration.controller;

import java.util.Enumeration; 

import javax.servlet.http.HttpServletRequest; 

import org.springframework.core.MethodParameter; 
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest; 
import org.springframework.web.method.support.HandlerMethodArgumentResolver; 
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ggon.darleneJ.common.web.controller.*;

/**
 * @FileName  : CustomMapArgumentResolver.java
 * @Project   : DarleneJ
 * @since     : Aug 2, 2019
 * @author    :  https://addio3305.tistory.com/75 [흔한 개발자의 개발 노트]
 * 
 */
public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver {
	
	@Override public boolean supportsParameter(MethodParameter parameter) {
		return CommandMap.class.isAssignableFrom(parameter.getParameterType()); 
	} 
	
	@Override public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		CommandMap commandMap = new CommandMap();
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(); 
		Enumeration<?> enumeration = request.getParameterNames(); 
		String key = null; String[] values = null;
		while(enumeration.hasMoreElements()){ 
			key = (String) enumeration.nextElement();
			values = request.getParameterValues(key); 
			if(values != null){ 
				commandMap.put(key, (values.length > 1) ? values:values[0] );
			} 
		}
		return commandMap; 
	}
}
