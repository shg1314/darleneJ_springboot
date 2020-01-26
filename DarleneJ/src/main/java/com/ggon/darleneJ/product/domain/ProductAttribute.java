/**
 * 
 */
package com.ggon.darleneJ.product.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ggon.darleneJ.Attribute.domain.Attribute;
import com.ggon.darleneJ.common.application.AlreadyExistsException;
import com.ggon.darleneJ.common.domain.entity.Entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @FileName  : ProductAttribute.java
 * @Project   : DarleneJ
 * @since     : Dec 30, 2019
 * @author    : ggon
 * 
 */
@ToString
@EqualsAndHashCode(of = {"id", "name"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAttribute extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Getter private String name;
	@Getter private String valueType = "string";
	private List<Object> possibleValues = new ArrayList<Object>();
	private ProductAttributeValue value = ProductAttributeValue.NullProductAttributeValue;
	@Getter private boolean isCompulsory = true;
	@Getter private BigDecimal extraPrice = BigDecimal.ZERO;
	
	/*
	private ProductAttribute(long id,String name, String valueType) {
		super(id);
		this.name = name;
		this.valueType = valueType;
	}
	*/
	private ProductAttribute(long id,String name, String valueType, boolean isCompulsory, BigDecimal extraPrice ,List<Object> possibleValues) {
		super(id);
		this.name = name;
		this.valueType = valueType;
		this.possibleValues = new ArrayList<Object>(possibleValues);
		this.isCompulsory = isCompulsory;
		this.extraPrice = extraPrice;
	}
	
	private ProductAttribute(String name, String valueType,boolean isCompulsory, BigDecimal extraPrice,List<Object> possibleValues) {
		this.name = name;
		this.valueType = valueType;
		this.possibleValues = new ArrayList<Object>(possibleValues);
		this.isCompulsory = isCompulsory;
		this.extraPrice = extraPrice;
	}
	
	private ProductAttribute(String name, String valueType) {
		this.name = name;
		this.valueType = valueType;
	}
		
	public ProductAttribute newProductAttribute(String name, String valueType) {
		if(name.isEmpty()) throw new IllegalArgumentException("name is emtpy");
		if(valueType.isEmpty()) throw new IllegalArgumentException("valueType is emtpy");
		return new ProductAttribute(name,valueType);
	}
	
	public ProductAttribute newProductAttribute(String name, String valueType, boolean isCompulsory, BigDecimal extraPrice, List<Object> possibleValues) {
		if(name.isEmpty()) throw new IllegalArgumentException("name is emtpy");
		if(valueType.isEmpty()) throw new IllegalArgumentException("valueType is emtpy");
		throwWhenPossibleValuesInvalidated(valueType,possibleValues);
		return new ProductAttribute(name,valueType,isCompulsory, extraPrice, possibleValues);
	}
	
	public ProductAttribute loadProductAttribute(long id, String name, String valueType,boolean isCompulsory, BigDecimal extraPrice,List<Object> possibleValues) {
		if(name.isEmpty()) throw new IllegalArgumentException("name is emtpy");
		if(valueType.isEmpty()) throw new IllegalArgumentException("valueType is emtpy");
		throwWhenPossibleValuesInvalidated(valueType,possibleValues);
		return new ProductAttribute(id,name,valueType,isCompulsory,extraPrice,possibleValues);
	}
	
	private boolean hasValue(Object value) {
		for(Object v : this.possibleValues) {
			if(v == value) return true;
		}
		return false;
	}
	
	public void compulsoryAttribute() {
		this.isCompulsory = true;
	}
	
	public void notCompulsoryAttribute() {
		this.isCompulsory = false;
	}
	
	public void updateExtraPrice(BigDecimal extraPrice) {
		this.extraPrice = extraPrice;
	}
	
	public void addValue(List<Object> possibleValues) {
		if(null == possibleValues) throw new IllegalArgumentException("possibleValues is null");
		if(possibleValues.size() == 0) throw new IllegalArgumentException("possibleValues is empty");
		for(Object o : possibleValues) {
			addValueIfNotExists(o);
		}
	}
	
	public void addValueIfNotExists(Object value) {
		try {
			addValue(value);
		}catch(Exception e) {
			
		}
	}
	
	public void addValue(Object value) {
		if(null == value) throw new IllegalArgumentException("value is null");
		if(hasValue(value)==false)
			possibleValues.add(value);
		else throw new AlreadyExistsException("the value(" + value.toString() + ") is already exists");
	}
	
	public void removeValueIfExists(Object value){
		if(null == value) throw new IllegalArgumentException("value is null");
		if(hasValue(value)==true)
			possibleValues.remove(value);
	}
	
	public boolean isValueValidated(Object value) {
		if(null == value) throw new IllegalArgumentException("value is null");
		return hasValue(value);
	}
	
	public List<Object> getPossibleValues() {
		return new ArrayList<Object>(possibleValues);
	}
	
	public void clearPossibleValues() {
		possibleValues = new ArrayList<Object>();
	}
	
	private void throwWhenPossibleIntValuesInvalidated(String valueType,List<Object> possibleValues) {
		if(valueType != "number") return;
		if(possibleValues.size()== 2) {
			long min = Long.parseLong(possibleValues.get(0).toString());
			long max = Long.parseLong(possibleValues.get(1).toString());
			
			if(min > max) throw new IllegalStateException("숫자 범위가 정보가 잘못되었습니다.min:" + String.valueOf(min) + " , max:" + String.valueOf(max));
		}else throw new IllegalStateException("숫자 범위가 정보가 잘못되었습니다. 최소값이 없거나 최대값이 없습니다.");
	}
	
	private void throwWhenPossibleValuesInvalidated(String valueType,List<Object> possibleValues) {
		if(null == possibleValues) throw new IllegalArgumentException("possibleValues is null");
		throwWhenPossibleIntValuesInvalidated(valueType, possibleValues);
	}

	private boolean isIntValueTypeValidated(String value) {
		long min = Long.parseLong(this.possibleValues.get(0).toString());
		long max = Long.parseLong(this.possibleValues.get(1).toString());
		
		long v = Long.parseLong(value.toString());
		if(v >= min && v <= max) return true;
		return false;
	}
	
	private boolean isStringValueTypeValidated(String value) {
		for(Object v : this.possibleValues) {
			if(v.toString() == value) return true;
		}
		return false;
	}
	
	public boolean isValueTypeValidated(String value) {
		if(null == this.possibleValues) return true;
		if(this.possibleValues.size() == 0) return true;
		if(this.valueType == "number") return isIntValueTypeValidated(value);
		return isStringValueTypeValidated(value);
	}
	
	public void setProductAttributeValue(ProductAttributeValue value) {
		if(isValueTypeValidated(value.getValue()) == false) throw new IllegalArgumentException("value(" + value + ")은 입력 가능한값이 아닙니다.");
		this.value = value.clone();
	}
	
	public ProductAttributeValue getProductAttributeValue() {
		return value.clone();
	}
}
