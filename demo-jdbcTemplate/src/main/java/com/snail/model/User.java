package com.snail.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;



/**
 *
 * @author snail
 * @version 1.0.0
 *
 */


@Getter
@Setter
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
    private String name;
	
    private Integer age;

	
    
    
    
	
}
