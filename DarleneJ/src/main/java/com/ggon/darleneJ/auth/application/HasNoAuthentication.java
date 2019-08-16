/**
 * 
 */
package com.ggon.darleneJ.auth.application;

/**
 * @FileName  : HasNoAuthentication.java
 * @Project   : DarleneJ
 * @since     : Aug 10, 2019
 * @author    : ggon
 * 
 */
public class HasNoAuthentication extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HasNoAuthentication(String msg) {
		super(msg);
	}
}
