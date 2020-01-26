/**
 * 
 */
package com.ggon.darleneJ.Attribute.domain;

import com.ggon.darleneJ.common.domain.entity.Entity;
import com.ggon.darleneJ.user.domain.User;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @FileName  : Attribute.java
 * @Project   : DarleneJ
 * @since     : Dec 28, 2019
 * @author    : ggon
 * 
 */
@ToString
@EqualsAndHashCode(of = {"id", "name"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attribute extends Entity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4506830760218967440L;
	@Getter private String name;
	@Getter private String valueType;
	
	public Attribute(long id,String name, String valueType) {
		super(id);
		this.name = name;
		this.valueType = valueType;
	}
	

}
