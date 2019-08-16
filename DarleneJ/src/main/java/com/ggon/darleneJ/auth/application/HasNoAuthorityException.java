/**
 * 
 */
package com.ggon.darleneJ.auth.application;

/**
 * @FileName  : AuthorityException.java
 * @Project   : DarleneJ
 * @since     : Aug 10, 2019
 * @author    : ggon
 * 
 */
public class HasNoAuthorityException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HasNoAuthorityException(String msg) {
		super(msg);
	}
}
