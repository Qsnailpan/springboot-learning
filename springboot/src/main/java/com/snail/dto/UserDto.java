package com.snail.dto;

import com.snail.commons.Dto;

import lombok.Getter;
import lombok.Setter;


/**
 * @author snail
 * @version 1.0.0
 */
@Getter
@Setter
public class UserDto implements Dto {

	private static final long serialVersionUID = 1L;

	private Long id;
	
    private String name;
    
    private Integer age;
}
