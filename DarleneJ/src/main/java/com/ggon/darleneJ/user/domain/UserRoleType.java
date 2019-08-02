package com.ggon.darleneJ.user.domain;

import org.assertj.core.util.Strings;

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
	public static boolean isCustomer(UserRoleType role) {
		return UserRoleType.CUSTOMER == role;
	}
	public static boolean isAdmin(UserRoleType role) {
		return UserRoleType.ADMIN == role;
	}
	public static boolean isMaintainer(UserRoleType role) {
		return UserRoleType.MAINTAINER == role;
	}
	
	public static UserRoleType getUserRoleTypeForm(String role) throws UserIllegalArgumentException, UnknownUserRoleTypeException {
		if(Strings.isNullOrEmpty(role)==true) throw new UserIllegalArgumentException("role is null or empty");
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
