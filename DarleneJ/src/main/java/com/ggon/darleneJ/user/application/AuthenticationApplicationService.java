package com.ggon.darleneJ.user.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ggon.darleneJ.common.application.IApplicationSessionService;
import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.port.adapter.persistence.IUserMapper;

@Service
public class AuthenticationApplicationService {

	private static final String LOGGINED_USER_KEY = "currentUser";
	
	@Autowired
	private IUserMapper userMapper;
	
	@Autowired
	private IApplicationSessionService session;
	
	
	public boolean isLogined() {
		return session.getIfExists(LOGGINED_USER_KEY) != null ? true : false;
	}
	
	public User getCurrentUserIfExists() {
		return (User)session.getIfExists(LOGGINED_USER_KEY);
	}
	
	private void throwExceptionWhenAlreadyLogin() throws AlreadyLoginException{
		User user = (User)getCurrentUserIfExists();
		if(null != user) throw new AlreadyLoginException("사용자(" + user.getEmail() + ")가 이미 로그인 했습니다.");
	}
	
	private void afterLoginSuccess(User user) {
		session.add(LOGGINED_USER_KEY, user);
	}
	
	public User login(String email, String pwd) throws LoginFailException, Exception{
		throwExceptionWhenAlreadyLogin();
		User user = userMapper.login(email, pwd);
		if(null == user) throw new LoginFailException("사용자(" + email + ") 로그인 실패"); 
		afterLoginSuccess(user);
		return user;
	}
	
	
}
