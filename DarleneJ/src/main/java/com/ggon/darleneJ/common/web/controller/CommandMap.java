package com.ggon.darleneJ.common.web.controller;

import java.util.HashMap; import java.util.Map; import java.util.Map.Entry; import java.util.Set; 

/**
 * @FileName  : CommandMap.java
 * @Project   : DarleneJ
 * @since     : Aug 2, 2019
 * @author    : https://addio3305.tistory.com/75?category=772645 [흔한 개발자의 개발 노트]
 *	
 */
public class CommandMap {
	private Map<String,Object> map = new HashMap<String,Object>();
	
	public Object get(String key){
		return map.get(key);
	}
	
	public void put(String key, Object value){
		map.put(key, value);
	}
	
	public Object remove(String key){
		return map.remove(key);
	}
	
	public boolean containsKey(String key){
		return map.containsKey(key);
	}
	
	public boolean containsValue(Object value){
		return map.containsValue(value);
	}
	
	public void clear(){
		map.clear();
	}
	
	public Set<Entry<String, Object>> entrySet(){
		return map.entrySet();
	}
	
	public Set<String> keySet(){
		return map.keySet();
	}
	
	public boolean isEmpty(){
		return map.isEmpty();
	}
	
	public void putAll(Map<? extends String, ?extends Object> m){
		map.putAll(m);
	}
	
	public Map<String,Object> getMap(){
		return map;
	}
}



