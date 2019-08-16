package com.ggon.darleneJ.user.domain;

import java.time.LocalDateTime; 
import lombok.*;
import com.ggon.darleneJ.user.domain.UserRoleType;
import com.ggon.darleneJ.common.domain.entity.*;
import com.ggon.darleneJ.user.domain.UserNullValueException;
import com.fasterxml.jackson.annotation.JsonGetter;

@ToString
@EqualsAndHashCode(of = {"id", "email"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6037743307575376280L;
	@Getter private String email = "";
	@Getter private String name ="";
	@Getter UserRoleType role = UserRoleType.UNKNOWN;
	LocalDateTime createdAt = null;
	LocalDateTime updatedAt = null;
		
	
	private User(long id, String email, String name, UserRoleType role, LocalDateTime createdAt,LocalDateTime updatedDate){
		super(id);
		this.email = email;
		this.name = name;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedDate;
	}
	
	private User(long id, String email, String name, String role, LocalDateTime createdAt,LocalDateTime updatedAt){
		super(id);
		this.email = email;
		this.name = name;
		this.role = UserRoleType.getEnum(role);
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	private User(String email, String name, UserRoleType role){
		this.email = email;
		this.name = name;
		this.role = role;
	}
	
	static public User loadUser(long id, String email, String name, UserRoleType role, LocalDateTime createdAt,LocalDateTime updatedAt) {
		return new User(id, email, name, role, createdAt, updatedAt);
	}
	
	static public User loadUser(long id, String email, String name, String role, LocalDateTime createdAt,LocalDateTime updatedAt) {
		return new User(id, email, name, role, createdAt, updatedAt);
	}
	
	static public User newUser(String email, String name,  UserRoleType role) {
		return new User(email,name,role);
	}
	
	public void changeName(String name) {
		if(null == name  || name.isEmpty() == true) throw new UserIllegalArgumentException("name(" + name +") is null or empty");
		this.name = name;
	}
	
	public void changeRole(UserRoleType role) {
		if(UserRoleType.isUnknown(role))  throw new UserIllegalArgumentException("unknown user role type");
		this.role = role;
	}
	
	public boolean isCustomer() {
		return UserRoleType.isCustomer(this.role);
	}
	
	public boolean isAdmin() {
		return UserRoleType.isAdmin(this.role);
	}
	
	public boolean isMaintainer() {
		return UserRoleType.isMaintainer(this.role);
	}
	
	@JsonGetter("createdAt")
	LocalDateTime createdAt(){
		if(null == createdAt) throw new UserNullValueException("생성날짜값이 NULL입니다.");
		return createdAt;
	}
	@JsonGetter("updatedAt")
	LocalDateTime updatedAt() {
		if(null == updatedAt) throw new UserNullValueException("변날짜값이 NULL입니다.");
		return updatedAt;
	}
}


