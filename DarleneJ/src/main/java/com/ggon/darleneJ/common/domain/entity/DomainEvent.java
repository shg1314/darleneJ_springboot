/**
 * 
 */
package com.ggon.darleneJ.common.domain.entity;

import java.time.LocalDateTime;

/**
 * @FileName  : DomainEvent.java
 * @Project   : DarleneJ
 * @since     : Aug 7, 2019
 * @author    : ggon
 * 
 */
public abstract class DomainEvent implements IDomainEvent{

	private int version = 1;
	private LocalDateTime datetime = LocalDateTime.now();
	
	protected DomainEvent() {
		
	}
	
	protected DomainEvent(int version, LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	@Override
	public int eventVersion() {
		return this.version;
	}

	@Override
	public LocalDateTime occurredOn() {
		return this.datetime;
	}

}
