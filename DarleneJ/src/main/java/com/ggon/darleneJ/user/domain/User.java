package com.ggon.darleneJ.user.domain;

import java.time.LocalDateTime; 
import lombok.*;
import com.ggon.darleneJ.user.domain.UserRoleType;
import com.ggon.darleneJ.user.domain.UserNullValueException;
import com.ggon.darleneJ.common.entity.*;

@ToString
@EqualsAndHashCode(of = {"id", "email"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends IdentifiedEntity {
	@Getter private String email = "";
	@Getter private String name ="";
	@Getter UserRoleType role = UserRoleType.UNKNOWN;
	LocalDateTime createdDate = null;
	LocalDateTime updatedDate = null;
		
	
	private User(long id, String email, String name, UserRoleType role, LocalDateTime createdDate,LocalDateTime updatedDate){
		super(id);
		this.email = email;
		this.name = name;
		this.role = role;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	
	private User(long id, String email, String name, String role, LocalDateTime createdDate,LocalDateTime updatedDate) throws UserIllegalArgumentException, UnknownUserRoleTypeException{
		super(id);
		this.email = email;
		this.name = name;
		this.role = UserRoleType.getUserRoleTypeForm(role);
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	
	private User(String email, String name, UserRoleType role){
		this.email = email;
		this.name = name;
		this.role = role;
	}
	
	public User loadUser(long id, String email, String name, UserRoleType role, LocalDateTime createdDate,LocalDateTime updatedDate) {
		return new User(id, email, name, role, createdDate, updatedDate);
	}
	
	public User loadUser(long id, String email, String name, String role, LocalDateTime createdDate,LocalDateTime updatedDate) throws UserIllegalArgumentException, UnknownUserRoleTypeException {
		return new User(id, email, name, role, createdDate, updatedDate);
	}
	
	public User newUser(String email, String name, UserRoleType role) {
		return new User(email,name,role);
	}
	
	
	boolean isCustomer() {
		return UserRoleType.isCustomer(this.role);
	}
	
	boolean isAdmin() {
		return UserRoleType.isAdmin(this.role);
	}
	
	boolean isMaintainer() {
		return UserRoleType.isMaintainer(this.role);
	}
	
	LocalDateTime creadtedDate() throws UserNullValueException{
		if(null == createdDate) throw new UserNullValueException("생성날짜값이 NULL입니다.");
		return createdDate;
	}
	
	LocalDateTime updatedDate() throws UserNullValueException{
		if(null == updatedDate) throw new UserNullValueException("변날짜값이 NULL입니다.");
		return updatedDate;
	}
}


