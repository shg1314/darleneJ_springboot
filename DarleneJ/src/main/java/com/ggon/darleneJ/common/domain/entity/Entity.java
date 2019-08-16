package com.ggon.darleneJ.common.domain.entity;

public abstract class Entity extends Identifiable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7265014423806875662L;
	
	public Entity() {
		super();
	}
	
	public Entity(long id) {
		super(id);
	}
}
