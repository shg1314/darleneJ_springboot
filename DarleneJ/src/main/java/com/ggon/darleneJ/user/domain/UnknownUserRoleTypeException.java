package com.ggon.darleneJ.user.domain;

public class UnknownUserRoleTypeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -414502780314520179L;

	UnknownUserRoleTypeException(String msg){
		super(msg);
	}
}
