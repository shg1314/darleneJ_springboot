/**
 * 
 */
package com.ggon.darleneJ.product.port.adapter.persistence;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ggon.darleneJ.product.domain.ProductAttribute;
import com.ggon.darleneJ.user.domain.User;

/**
 * @FileName  : IProductAttributeMapper.java
 * @Project   : DarleneJ
 * @since     : Dec 30, 2019
 * @author    : ggon
 * 
 */
@Mapper
public interface IProductAttributeMapper {
	public long add(@Param("attId") long attId,@Param("proId") long proId,
			@Param("isCompulsory") boolean isCompulsory, @Param("extraPrice") BigDecimal extraPrice);
	
	//public long add(@Param("attid") long attid, @Param("isCompulsory") boolean isCompulsory, 
			//@Param("extraPrice") BigDecimal extraPrice, @Param("possibleValues") String possibleValues);
	
	public long updatePossibleValues(@Param("id") long id, @Param("possibleValues") String possibleValues);
	
	public long updateExtraPrice(@Param("id") long id, @Param("extraPrice") BigDecimal extraPrice);
	
	public long updateIsCompulsory(@Param("id") long id, @Param("isCompulsory") boolean isCompulsory);
	
	public long update(@Param("id") long id, @Param("name") String name,@Param("type") String type,@Param("isCompulsory") boolean isCompulsory,
			@Param("extraPrice") BigDecimal extraPrice, @Param("possibleValues") String possibleValues);
	
	public long softDelete(@Param("id") long id);
	
	public long hardDelete(@Param("id") long id);
	
	public long getTotalCount();
	
	public List<ProductAttribute> get(@Param("offset") long offset,@Param("limit") int limit);
	
	public List<ProductAttribute> getAll();
	
	public List<ProductAttribute> searchingByName(@Param("name") String name);
}
