package com.ggon.darleneJ.user.domain;

public enum UserRoleType {
	UNKNOWN,
	CUSTOMER,
	ADMIN,
	MAINTAINER;
	
	
	
    public String toString(){
        switch (this) {
            case UNKNOWN: return USER_ROLE_TYPE_UNKNOWN;
            case CUSTOMER: return USER_ROLE_TYPE_CUSTOMER;
            case ADMIN: return USER_ROLE_TYPE_ADMIN;
            case MAINTAINER: return USER_ROLE_TYPE_MAINTAINER;
        }
        return null;
    }
    
	
    
	public static boolean isUnknown(UserRoleType role) {
		return UserRoleType.UNKNOWN == role;
	}
	
	public static boolean isCustomer(UserRoleType role) {
		return UserRoleType.CUSTOMER == role;
	}
	
	public static boolean isAdmin(UserRoleType role) {
		return UserRoleType.ADMIN == role;
	}
	
	public static boolean isMaintainer(UserRoleType role) {
		return UserRoleType.MAINTAINER == role;
	}
	
	public  boolean isUnknown() {
		return UserRoleType.isUnknown(this);
	}
	
	public boolean isCustomer() {
		return UserRoleType.isCustomer(this);
	}
	
	public boolean isAdmin() {
		return UserRoleType.isAdmin(this);
	}
	
	public boolean isMaintainer() {
		return UserRoleType.isMaintainer(this);
	}
	
	public boolean hasHigherAuthority(UserRoleType role) {
		if(UNKNOWN == this) return false;
		if(MAINTAINER == this) return true;
		return this.ordinal() > role.ordinal() ? true : false; 
	}
	
	public boolean hasEqualOrHigherAuthority(UserRoleType role) {
		if(UNKNOWN == this) return false;
		if(MAINTAINER == this) return true;
		return this.ordinal() >= role.ordinal() ? true : false; 
	}
	
	public static UserRoleType getEnum(String role) {
		if((null == role) || (role.isEmpty() == true)) throw new UserIllegalArgumentException("role is null or empty");
		UserRoleType roleType = UserRoleType.UNKNOWN; 
		switch (role.toLowerCase()) {
		case USER_ROLE_TYPE_CUSTOMER:
			roleType = UserRoleType.CUSTOMER;
			break;
		case USER_ROLE_TYPE_ADMIN:
			roleType = UserRoleType.ADMIN;
			break;
		case USER_ROLE_TYPE_MAINTAINER:
			roleType = UserRoleType.MAINTAINER;
			break;
		default:
			throw new UnknownUserRoleTypeException("invalid input parameter value:" + role);
		}
		return roleType;
	}
	
	public static final String USER_ROLE_TYPE_UNKNOWN = "Unknown User Role Type";
	public static final String USER_ROLE_TYPE_CUSTOMER = "customer";
	public static final String USER_ROLE_TYPE_ADMIN = "admin";
	public static final String USER_ROLE_TYPE_MAINTAINER = "maintainer";
	
}
