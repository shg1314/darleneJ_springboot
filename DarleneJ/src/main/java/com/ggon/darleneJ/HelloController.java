/**
 * 
 */
package com.ggon.darleneJ;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * @FileName  : HelloController.java
 * @Project   : DarleneJ
 * @since     : Dec 27, 2019
 * @author    : ggon
 * 
 */
@Controller
public class HelloController {

	@RequestMapping(value="/index.do", method=RequestMethod.GET)
	public String hello() {
		return "index.html";
	}
}
