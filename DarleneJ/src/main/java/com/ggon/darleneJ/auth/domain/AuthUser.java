/**
 * 
 */
package com.ggon.darleneJ.auth.domain;

import com.ggon.darleneJ.user.domain.User;
import com.ggon.darleneJ.user.domain.UserRoleType;

/**
 * @FileName  : AuthUser.java
 * @Project   : DarleneJ
 * @since     : Aug 4, 2019
 * @author    : ggon
 * 
 */
public class AuthUser {
	User user = null;
	
	private AuthUser() {
		
	}
	
	private AuthUser(User user) {
		if(null == user) throw new IllegalArgumentException("user is null");
		this.user = user;
	}
	
	static public AuthUser authUser(User user) {
		return new AuthUser(user);
	}

	public long getId() { return user.getId();}
	public String getName() { return user.getName();}
	public String getEmail() {return user.getEmail();}
	public UserRoleType getUserRoleType() {return user.getRole();}

}
