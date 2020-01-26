/**
 * 
 */
package com.ggon.darleneJ.product.domain;

import com.ggon.darleneJ.common.domain.entity.Entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @FileName  : ProductAttributeValue.java
 * @Project   : DarleneJ
 * @since     : Jan 27, 2020
 * @author    : ggon
 * 
 */
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAttributeValue extends Entity{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter private String value = "";
	public static final ProductAttributeValue NullProductAttributeValue = new ProductAttributeValue("");

	private ProductAttributeValue(long id, String value) {
		super(id);
		this.value = value;
	}
	
	private ProductAttributeValue(String value) {
		this.value = value;
	}
	
	
	public ProductAttributeValue loadProductAttributeValue(long id,String value) {
		return new ProductAttributeValue(id,value);
	}
	
	public ProductAttributeValue newProductAttributeValue(String value) {
		return new ProductAttributeValue(value);
	}
	
	public ProductAttributeValue nullnewProductAttributeValue() {
		return new ProductAttributeValue("");
	}
	
	public ProductAttributeValue clone() {
		if(NullProductAttributeValue == this) return NullProductAttributeValue;
		return new ProductAttributeValue(this.id,this.value);
	}
}
