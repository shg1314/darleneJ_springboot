package com.ggon.darleneJ.common.domain.entity;

public class IdentifiedEntityAlreadyHasIDException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8726098351484489065L;

	public IdentifiedEntityAlreadyHasIDException(String msg) {
		super(msg);
	}
}
