package com.ggon.darleneJ.user.domain;


public class UserNullValueException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2529421711573527943L;

	UserNullValueException(String msg){
		super(msg);
	}
}
