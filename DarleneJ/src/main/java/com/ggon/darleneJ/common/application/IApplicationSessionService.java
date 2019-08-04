/**
 * 
 */
package com.ggon.darleneJ.common.application;

/**
 * @FileName  : IApplicationSessionService.java
 * @Project   : DarleneJ
 * @since     : Aug 4, 2019
 * @author    : ggon
 * 
 */
public interface IApplicationSessionService {
	public void add(String name, Object obj);
	public Object getIfExists(String name);
	public void removeIfExists(String name);
}
