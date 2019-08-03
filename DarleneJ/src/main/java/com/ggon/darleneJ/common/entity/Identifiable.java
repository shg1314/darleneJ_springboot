package com.ggon.darleneJ.common.entity;

import java.io.Serializable;

public abstract class Identifiable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1166131012203851341L;
	public static final long INVALID_ID = 0;
	protected long id = INVALID_ID;
	
	public Identifiable() {
		
	}
	
	protected Identifiable(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public static boolean isValidID(long id) {
		if(id <= INVALID_ID) return false;
		return true;
	}
	
	protected boolean canSetId() {
		return isValidID(this.id) == false ? true : false; 
	}
	
	public void setId(long id) throws IdentifiedEntityAlreadyHasIDException,IdentifiedEntityIllegalArgumentException {
		if(isValidID(id) == false) throw new IdentifiedEntityIllegalArgumentException("invalid id(" + String.valueOf(id) + ")");
		throwWhenIdNotSetterAble();
		this.id = id;
	}
	
	protected void throwWhenIdNotSetterAble() throws IdentifiedEntityAlreadyHasIDException{
		if(canSetId()==false) throw new IdentifiedEntityAlreadyHasIDException("id(" + String.valueOf(this.id) + ")값이 이미 있습니다.");
	}
	
}
