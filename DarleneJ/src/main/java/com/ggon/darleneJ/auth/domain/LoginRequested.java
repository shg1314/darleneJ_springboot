/**
 * 
 */
package com.ggon.darleneJ.auth.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.ggon.darleneJ.common.domain.entity.DomainEvent;

import lombok.Getter;

/**
 * @FileName  : LoginRequested.java
 * @Project   : DarleneJ
 * @since     : Aug 7, 2019
 * @author    : ggon
 * 
 */
public class LoginRequested extends DomainEvent {
	@Getter private String loginId;
	@Getter private String pwd;
	
	
}
