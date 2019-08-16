package com.ggon.darleneJ.auth.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ggon.darleneJ.auth.domain.AccessToken;
import com.ggon.darleneJ.auth.domain.AuthUser;
import com.ggon.darleneJ.common.application.IApplicationSessionService;
import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.domain.UserRoleType;
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
	
	public void throwWhenHasNoAuthentication() {
		if(false == isLogined()) throw new HasNoAuthentication("인증이 없습니다. 로그인 하세요");
	}
	
	public AccessToken getCurrentLoginUserTokenIfExists() {
		return (AccessToken)session.getIfExists(LOGGINED_USER_KEY);
	}
	
	public AuthUser getCurrentLoginAccessUserIfExists() {
		AccessToken token = (AccessToken)session.getIfExists(LOGGINED_USER_KEY);
		if(null == token) return null;
		return token.getUser();
	}
	
	private void throwExceptionWhenAlreadyLogin(){
		AccessToken token = getCurrentLoginUserTokenIfExists();
		if(null != token) throw new AlreadyLoginException("사용자(" + token.getUser().getEmail() + ")가 이미 로그인 했습니다.");
	}
	
	private AccessToken newAccessToken(User user) {
		return AccessToken.newToken(AuthUser.authUser(user));
	}
	
	private void afterLoginSuccess(AccessToken token) {
		session.add(LOGGINED_USER_KEY, token);
	}
	
	public AccessToken login(String loginId, String pwd){
		throwExceptionWhenAlreadyLogin();
		User user = userMapper.login(loginId, pwd);
		if(null == user) throw new LoginFailException("사용자(" + loginId + ") 로그인 실패"); 
		AccessToken token = newAccessToken(user);
		afterLoginSuccess(token);
		return token;
	}
	
	public void logout() {
		session.removeIfExists(LOGGINED_USER_KEY);
	}
	
	public boolean isCurrentUserAdmin() {
		throwWhenHasNoAuthentication();
		AuthUser user = getCurrentLoginAccessUserIfExists();
		return (null == user) ? false : user.getUserRoleType().isAdmin();
	}
	
	public boolean isCurrentUserCustomer() {
		throwWhenHasNoAuthentication();
		AuthUser user = getCurrentLoginAccessUserIfExists();
		return (null == user) ? false : user.getUserRoleType().isCustomer();
	}
	
	public boolean isCurrentUserMaintainer() {
		throwWhenHasNoAuthentication();
		AuthUser user = getCurrentLoginAccessUserIfExists();
		return (null == user) ? false : user.getUserRoleType().isMaintainer();
	}
	public boolean hasEqualOrHigherAuthority(UserRoleType role) {
		throwWhenHasNoAuthentication();
		AuthUser user = getCurrentLoginAccessUserIfExists();
		return (null == user) ? false : user.getUserRoleType().hasEqualOrHigherAuthority(role);
	}
	
	public boolean hasHigherAuthority(UserRoleType role) {
		throwWhenHasNoAuthentication();
		AuthUser user = getCurrentLoginAccessUserIfExists();
		return (null == user) ? false : user.getUserRoleType().hasHigherAuthority(role);
	}
}
