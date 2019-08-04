/**
 * 
 */
package com.ggon.darleneJ.auth.application;

/**
 * @FileName  : AlreadyLoginException.java
 * @Project   : DarleneJ
 * @since     : Aug 4, 2019
 * @author    : ggon
 * 
 */
public class AlreadyLoginException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9007991120057664715L;

	public AlreadyLoginException(String msg){
		super(msg);
	}
}
