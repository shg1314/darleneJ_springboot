/**
 * 
 */
package com.ggon.darleneJ.user.application;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ggon.darleneJ.auth.application.AuthenticationApplicationService;
import com.ggon.darleneJ.auth.application.HasNoAuthorityException;
import com.ggon.darleneJ.auth.application.HasNoAuthentication;
import com.ggon.darleneJ.common.application.IApplicationSessionService;
import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.domain.UserException;
import com.ggon.darleneJ.user.domain.UserIllegalArgumentException;
import com.ggon.darleneJ.user.domain.UserNullValueException;
import com.ggon.darleneJ.user.domain.UserRoleType;
import com.ggon.darleneJ.user.port.adapter.persistence.IUserMapper;

/**
 * @FileName  : UserApplicationService.java
 * @Project   : DarleneJ
 * @since     : Aug 10, 2019
 * @author    : ggon
 * 
 */
@Service
public class UserApplicationService {

	@Autowired
	IUserMapper mapper;
	
	@Autowired
	AuthenticationApplicationService authService;
	
	//@Bean
	public UserApplicationService() {
		
	}
	
	public User getUser(String email, String pwd)
	{
		if(null == email || email.isEmpty()) throw new UserIllegalArgumentException("email is null or empty");
		if(null == pwd || pwd.isEmpty()) throw new UserIllegalArgumentException("password is null or empty");
		
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스권한이 없습니다.");
		return mapper.login(email,pwd);
	}
	
	public User getUser(long id) {
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스권한이 없습니다.");
		return mapper.getById(id);
	}
	
	public User getUser(String email) {
		if(null == email || email.isEmpty()) throw new UserIllegalArgumentException("email is null or empty");
		
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스권한이 없습니다.");
		return mapper.getByEmail(email);
	}
	
	public void deleteUser(long id) {
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 삭제권한이 없습니다.");
		long count = mapper.deleteById(id);
		if(count != 1 ) throw new UserException("계정(" + String.valueOf(id) + ") 삭제 실패");
	}
	
	public void deleteUser(User u) {
		if(null == u) throw new UserIllegalArgumentException("user is null");
		deleteUserByEmail(u.getEmail());
	}
	
	public void deleteUserByEmail(String email) {
		if(null == email || email.isEmpty()) throw new UserIllegalArgumentException("email is null or empty");
		
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 삭권한이 없습니다.");
		long count = mapper.deleteByEmail(email);
		if(count != 1 ) throw new UserException("계정(" + email + ") 삭제 실패");
	}
	
	public User addUser(String name, String email, String pwd, UserRoleType role) {
		if(null == name || name.isEmpty()) throw new UserIllegalArgumentException("name is null or empty");
		if(null == email || email.isEmpty()) throw new UserIllegalArgumentException("email is null or empty");
		if(null == pwd || pwd.isEmpty()) throw new UserIllegalArgumentException("password is null or empty");
		if(null == role || role == UserRoleType.UNKNOWN) throw new UserIllegalArgumentException("role is null or unknown type");
		
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 생성권한이 없습니다.");
		User user = User.newUser(email, name, role);
		try {
			long count =  mapper.insertUser(user,user.getName(),user.getEmail(),user.getRole().toString(),pwd);
			if(count != 1) throw new UserException("새로운 user(" + user.getEmail() + ") 생성 실패");
		}catch(Exception e) {
			throw new UserException("새로운 user(" + user.getEmail() + ") 생성 실패");
		}finally{
			
		}
		return user;
	}
	
	public long customerCount() {
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 권한이 없습니다.");
		return mapper.customerCount();
	}
	
	public List<User> getCustomers(long offset, int limit){
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스 권한이 없습니다.");
		return mapper.getCustomers(offset,limit);
	}
	
	public long adminCount() {
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 권한이 없습니다.");
		return mapper.adminCount();	
	}
	
	public List<User> getAdmins(long offset, int limit){
		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스 권한이 없습니다.");
		return mapper.getAdmins(offset,limit);
	}
	
    public void update(User user) {
    	if(null == user) throw new UserIllegalArgumentException("user is null");
    	if(user.isCustomer() || user.isAdmin())
    	{
    		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스 권한이 없습니다.");
    	}
    	if(user.isMaintainer()) 
    	{
    		if(authService.hasEqualOrHigherAuthority(UserRoleType.MAINTAINER)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스 권한이 없습니다.");
    	}
    	long count =  mapper.update(user.getId(),user.getName(),user.getRole().toString());
    	if(count != 1) throw new UserException("user(" + user.getEmail() + ") 정보 변경 실패");
    }
    
    public void updatePassword(User user,String pwd) {
    	if(null == user) throw new UserIllegalArgumentException("user is null");
    	if(null == pwd || pwd.isEmpty()) throw new UserIllegalArgumentException("password is null or empty");
    	if(user.isCustomer() || user.isAdmin())
    	{
    		if(authService.hasEqualOrHigherAuthority(UserRoleType.ADMIN)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스 권한이 없습니다.");
    	}
    	if(user.isMaintainer()) 
    	{
    		if(authService.hasEqualOrHigherAuthority(UserRoleType.MAINTAINER)== false) throw new HasNoAuthorityException("사용자(" + authService.getCurrentLoginAccessUserIfExists().getEmail() + ")는 액세스 권한이 없습니다.");
    	}
    	long count =  mapper.updatePassword(user.getId(),pwd);
    	if(count != 1) throw new UserException("user(" + user.getEmail() + ") 비밀번호변경 실패");
    }
    
}
