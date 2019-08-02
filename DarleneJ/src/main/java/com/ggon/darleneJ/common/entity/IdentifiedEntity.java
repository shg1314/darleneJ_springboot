package com.ggon.darleneJ.common.entity;

import java.io.Serializable;

public class IdentifiedEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1166131012203851341L;
	static final long INVALID_ID = -1;
	protected long id=INVALID_ID;
	
	public IdentifiedEntity() {
		
	}
	
	public IdentifiedEntity(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) throws IdentifiedEntityAlreadyHasIDException,IdentifiedEntityIllegalArgumentException {
		if(id <= INVALID_ID ) throw new IdentifiedEntityIllegalArgumentException("invalid id(" + String.valueOf(this.id) + ")");
		throwWhenIdNotSetterAble();
		this.id = id;
	}
	
	protected void throwWhenIdNotSetterAble() throws IdentifiedEntityAlreadyHasIDException{
		if(this.id != INVALID_ID) throw new IdentifiedEntityAlreadyHasIDException("id(" + String.valueOf(this.id) + ")값이 이미 있습니다.");
	}
}
