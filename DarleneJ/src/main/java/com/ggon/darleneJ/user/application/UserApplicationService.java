/**
 * 
 */
package com.ggon.darleneJ.user.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.application.HasNoAuthorityException;
import com.ggon.darleneJ.auth.application.HasNoAuthentication;
import com.ggon.darleneJ.common.application.IApplicationSessionService;
import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.domain.UserException;
import com.ggon.darleneJ.user.domain.UserRoleType;
import com.ggon.darleneJ.user.port.adapter.persistence.IUserMapper;

/**
 * @FileName  : UserApplicationService.java
 * @Project   : DarleneJ
 * @since     : Aug 10, 2019
 * @author    : ggon
 * 
 */
public class UserApplicationService {

	@Autowired
	IUserMapper mapper;
	
	@Autowired
	AuthenticationApplicationService authService;
	
	public User getUser(long id) {
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ") 생성권한이 없습니다.");
		return mapper.getById(id);
	}
	
	public void delete(long id) {
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ") 생성권한이 없습니다.");
		long count = mapper.deleteById(id);
		if(count != 1 ) throw new UserException("계정(" + String.valueOf(id) + ") 삭제 실패");
	}
	
	public void deleteByEmail(String email) {
		
	}
	
	public long createNewUser(String name, String email, String pwd, UserRoleType role) {
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ") 생성권한이 없습니다.");
		User user = User.newUser(email, name, role);
		return mapper.insert(user,pwd);
	}
	
	public List<User> getUsers(long offset, int limit){
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ") 생성권한이 없습니다.");
		return mapper.getCustomers(offset,limit);
	}
	
	/*
	private List<User> getUsersExcludeMaintainer(){
		
	}
	
	private List<User> getUsersExcludeDeletedUserOnly(){
		
	}
	
	private List<User> getUsers(){
		
	}
	*/
	
	
}
