/**
 * 
 */
package com.ggon.darleneJ.auth.application;

/**
 * @FileName  : LoginFailException.java
 * @Project   : DarleneJ
 * @since     : Aug 4, 2019
 * @author    : ggon
 * 
 */
public class LoginFailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3926886836824246514L;

	
	public LoginFailException(String msg) {
		super(msg);
	}
}
