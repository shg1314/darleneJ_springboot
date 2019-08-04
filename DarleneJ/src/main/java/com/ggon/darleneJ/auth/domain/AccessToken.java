/**
 * 
 */
package com.ggon.darleneJ.auth.domain;

import com.ggon.darleneJ.user.domain.User;

/**
 * @FileName  : AccessToken.java
 * @Project   : DarleneJ
 * @since     : Aug 4, 2019
 * @author    : ggon
 * 
 */
public class AccessToken {
	private AuthUser authuser = null;
	
	private AccessToken() {
		
	}
	
	private AccessToken(AuthUser authuser) {
		if(null == authuser) throw new IllegalArgumentException("user is null");
		this.authuser = authuser;
	}
	
	static public AccessToken newToken(AuthUser authuser) {
		return new AccessToken(authuser);
	}
	
	public AuthUser getUser() { return authuser; }
}
