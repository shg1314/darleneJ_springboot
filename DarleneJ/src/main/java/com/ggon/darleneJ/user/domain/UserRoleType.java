package com.ggon.darleneJ.user.domain;

public enum UserRoleType {
	UNKNOWN{
	      public String toString() {
	          return "Unknown User Role Type";
	      }
	},
	CUSTOMER{
	      public String toString() {
	          return "customer";
	      }
	},
	ADMIN{
	      public String toString() {
	          return "admin";
	      }
	}, MAINTAINER{
	      public String toString() {
	          return "maintainer";
	      }
	};
	
	public static boolean isCustomer(UserRoleType role) {
		return UserRoleType.CUSTOMER == role;
	}
	public static boolean isAdmin(UserRoleType role) {
		return UserRoleType.ADMIN == role;
	}
	public static boolean isMaintainer(UserRoleType role) {
		return UserRoleType.MAINTAINER == role;
	}
}
