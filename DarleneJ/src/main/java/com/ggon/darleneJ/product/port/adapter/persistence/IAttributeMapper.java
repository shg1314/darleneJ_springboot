/**
 * 
 */
package com.ggon.darleneJ.product.port.adapter.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @FileName  : IAttributeMapper.java
 * @Project   : DarleneJ
 * @since     : Dec 31, 2019
 * @author    : ggon
 * 
 */
@Mapper
public interface IAttributeMapper {
	public long add(@Param("name") String name, @Param("type") String type);
	
	//public long addWithPossiableValues(@Param("name") String name, @Param("type") String type, @Param("possibleValues") String possibleValues);
	
	//public long update(@Param("id") long id,@Param("possibleValues") String possibleValues);
	
	public long update(@Param("id") long id, @Param("type") String type, @Param("possibleValues") String possibleValues);
	
	
}
