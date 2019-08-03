/**
 * 
 */
package com.ggon.darleneJ.user.application;

/**
 * @FileName  : LoginFailException.java
 * @Project   : DarleneJ
 * @since     : Aug 4, 2019
 * @author    : ggon
 * 
 */
public class LoginFailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3926886836824246514L;

	
	public LoginFailException(String msg) {
		super(msg);
	}
}
